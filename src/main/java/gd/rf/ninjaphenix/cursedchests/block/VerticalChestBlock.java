package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.block.entity.VerticalChestBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.VerticalEntityPosition;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sortme.ItemScatterer;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.List;

public abstract class VerticalChestBlock extends BlockWithEntity implements Waterloggable
{
	public enum VerticalChestType implements StringRepresentable
	{
		SINGLE("single"),
		TOP("top"),
		BOTTOM("bottom");

		private final String name;

		VerticalChestType(String string_1) { this.name = string_1;}

		public String asString() { return this.name; }
	}

	interface someInterface<T>
	{
		T method_17465(VerticalChestBlockEntity var1, VerticalChestBlockEntity var2);
		T method_17464(VerticalChestBlockEntity var1);
	}

	private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final DirectionProperty FACING = Properties.FACING_HORIZONTAL;
	public static final EnumProperty<VerticalChestType> TYPE = EnumProperty.create("type", VerticalChestType.class);
	private static final VoxelShape SINGLE_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 14, 15);
	private static final VoxelShape TOP_SHAPE = Block.createCuboidShape(1, -16, 1, 15, 14, 15);
	private static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(1, 0, 1, 15, 30, 15);
	public String name;

	private static final someInterface<Inventory> inventoryCombiner = new someInterface<Inventory>() {
		public Inventory method_17465(VerticalChestBlockEntity var1, VerticalChestBlockEntity var2) {
			return new DoubleInventory(var1, var2);
		}

		public Inventory method_17464(VerticalChestBlockEntity var1) {
			return var1;
		}
	};

	private static final someInterface<TextComponent> displayNameCombiner = new someInterface<TextComponent>() {
		@Override
		public TextComponent method_17465(VerticalChestBlockEntity var1, VerticalChestBlockEntity var2)
		{
			if(var1.hasCustomName()) return var1.getDisplayName();
			if(var2.hasCustomName()) return var2.getDisplayName();
			return new TranslatableTextComponent("container.cursedchests.generic_double").append(var1.getDisplayName());
		}

		@Override
		public TextComponent method_17464(VerticalChestBlockEntity var1) { return var1.getDisplayName(); }
	};

	public VerticalChestBlock(Settings block$Settings_1){ this(block$Settings_1, "wood_chest"); }

	public VerticalChestBlock(Settings block$Settings_1, String name)
	{
		super(block$Settings_1);
		this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH)
				                                   .with(WATERLOGGED, false)
				                                   .with(TYPE, VerticalChestType.SINGLE));
		this.name = name;
	}

	@Environment(EnvType.CLIENT) @Override public boolean hasBlockEntityBreakingRender(BlockState blockState_1) { return true; }
	@Override public BlockRenderType getRenderType(BlockState blockState_1) { return BlockRenderType.ENTITYBLOCK_ANIMATED; }
	@Override public FluidState getFluidState(BlockState state)
	{
		return state.get(WATERLOGGED) ? Fluids.WATER.getState(false) : super.getFluidState(state);
	}
	@Override protected void appendProperties(StateFactory.Builder<Block, BlockState> stateBuilder)
	{
		stateBuilder.with(FACING, TYPE, WATERLOGGED);
	}

	@Override public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, VerticalEntityPosition verticalEntityPosition_1)
	{
		VerticalChestType type = blockState_1.get(TYPE);
		switch(type)
		{
			case TOP: return TOP_SHAPE;
			case BOTTOM: return BOTTOM_SHAPE;
			default: return SINGLE_SHAPE;
		}
	}

	@Override public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState otherState, IWorld world, BlockPos pos, BlockPos otherPos)
	{
		if (state.get(WATERLOGGED)) { world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world)); }
		if(state.get(TYPE) == VerticalChestType.TOP) { if(world.getBlockState(pos.offset(Direction.DOWN)).getBlock() != this) return state.with(TYPE, VerticalChestType.SINGLE); }
		else if(state.get(TYPE) == VerticalChestType.BOTTOM) { if(world.getBlockState(pos.offset(Direction.UP)).getBlock() != this) return state.with(TYPE, VerticalChestType.SINGLE); }
		else
		{
			if(direction.getAxis().isVertical())
			{
				BlockState realOtherState = world.getBlockState(pos.offset(direction));
				if(!realOtherState.contains(TYPE)) { return state.with(TYPE, VerticalChestType.SINGLE); }
				else if(direction == Direction.UP && realOtherState.get(TYPE) == VerticalChestType.TOP) { return state.with(TYPE, VerticalChestType.BOTTOM); }
				else if(direction == Direction.DOWN && realOtherState.get(TYPE) == VerticalChestType.BOTTOM) { return state.with(TYPE, VerticalChestType.TOP); }
				else { return state.with(TYPE, VerticalChestType.SINGLE); }
			}
		}
		return super.getStateForNeighborUpdate(state, direction, otherState, world, pos, otherPos);
	}

	@Override public BlockState getPlacementState(ItemPlacementContext context)
	{
		VerticalChestType chestType_1 = VerticalChestType.SINGLE;
		Direction direction_1 = context.getPlayerHorizontalFacing().getOpposite();
		FluidState fluidState_1 = context.getWorld().getFluidState(context.getBlockPos());
		boolean sneaking = context.isPlayerSneaking();
		Direction direction_2 = context.getFacing();
		if(direction_2.getAxis().isVertical() && sneaking)
		{
			BlockState blockState_1 = context.getWorld().getBlockState(context.getBlockPos().offset(direction_2.getOpposite()));
			Direction direction_3 = blockState_1.getBlock() == this && blockState_1.get(TYPE) ==VerticalChestType.SINGLE ? blockState_1.get(FACING) : null;
			if(direction_3 != null && direction_3.getAxis() != direction_2.getAxis()) chestType_1 = direction_2 == Direction.UP ? VerticalChestType.TOP : VerticalChestType.BOTTOM;
		}
		return this.getDefaultState().with(FACING, direction_1).with(TYPE, chestType_1).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
	}

	@Override public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1)
	{
		if (itemStack_1.hasDisplayName())
		{
			BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof VerticalChestBlockEntity) ((VerticalChestBlockEntity)blockEntity_1).setCustomName(itemStack_1.getDisplayName());
		}
	}

	@Override public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1)
	{
		if (blockState_1.getBlock() != blockState_2.getBlock())
		{
			BlockEntity blockEntity_1 = world_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof Inventory)
			{
				ItemScatterer.spawn(world_1, blockPos_1, (Inventory)blockEntity_1);
				world_1.updateHorizontalAdjacent(blockPos_1, this);
			}
			super.onBlockRemoved(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
		}
	}

	@Override public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1)
	{
		if (world_1.isClient) return true;
		//NameableContainerProvider nameableContainerProvider_1 = this.createContainerProvider(blockState_1, world_1, blockPos_1);
		//if (nameableContainerProvider_1 != null)
		//{
		Inventory combined = createCombinedInventory(blockState_1, world_1, blockPos_1);
		DefaultedList<ItemStack> inventoryData = DefaultedList.create(combined.getInvSize(), ItemStack.EMPTY);
		for(int slotIndex=0; slotIndex<combined.getInvSize(); slotIndex++)
		{
			inventoryData.set(slotIndex, combined.getInvStack(slotIndex));
		}
		CompoundTag tag = Inventories.toTag(new CompoundTag(), inventoryData);
		TextComponent containerName = method_17459(blockState_1, world_1, blockPos_1, displayNameCombiner);
		ContainerProviderRegistry.INSTANCE.openContainer(new Identifier("cursedchests", "scrollcontainer"), playerEntity_1, (packetByteBuf -> {

				packetByteBuf.writeInt(combined.getInvSize());
				packetByteBuf.writeTextComponent(containerName);
				packetByteBuf.writeCompoundTag(tag);

		}));
			//playerEntity_1.openContainer(nameableContainerProvider_1);
			playerEntity_1.incrementStat(this.getOpenStat());
		//}
		return true;
	}

	private Stat<Identifier> getOpenStat() { return Stats.CUSTOM.getOrCreateStat(Stats.OPEN_CHEST); }

	private static <T> T method_17459(BlockState blockState_1, IWorld iWorld_1, BlockPos blockPos_1, someInterface<T> var_1)
	{
		BlockEntity blockEntity_1 = iWorld_1.getBlockEntity(blockPos_1);
		if (!(blockEntity_1 instanceof VerticalChestBlockEntity)) return null;
		else if (isChestBlocked(iWorld_1, blockPos_1)) return null;
		else
		{
			VerticalChestBlockEntity chestBlockEntity_1 = (VerticalChestBlockEntity) blockEntity_1;
			VerticalChestType chestType_1 = blockState_1.get(TYPE);
			if (chestType_1 == VerticalChestType.SINGLE)
			{
				return var_1.method_17464(chestBlockEntity_1);
			}
			else
			{
				BlockPos blockPos_2;
				if(chestType_1 == VerticalChestType.TOP) blockPos_2 = blockPos_1.offset(Direction.DOWN);
				else blockPos_2 = blockPos_1.offset(Direction.UP);
				BlockState blockState_2 = iWorld_1.getBlockState(blockPos_2);
				if (blockState_2.getBlock() == blockState_1.getBlock())
				{
					VerticalChestType chestType_2 = blockState_2.get(TYPE);
					if (chestType_2 != VerticalChestType.SINGLE && chestType_1 != chestType_2 && blockState_2.get(FACING) == blockState_1.get(FACING))
					{
						if (isChestBlocked(iWorld_1, blockPos_2)) return null;
						BlockEntity blockEntity_2 = iWorld_1.getBlockEntity(blockPos_2);
						if (blockEntity_2 instanceof VerticalChestBlockEntity)
						{
							VerticalChestBlockEntity chestBlockEntity_2 = chestType_1 == VerticalChestType.TOP ? chestBlockEntity_1 : (VerticalChestBlockEntity)blockEntity_2;
							VerticalChestBlockEntity chestBlockEntity_3 = chestType_1 == VerticalChestType.TOP ? (VerticalChestBlockEntity)blockEntity_2 : chestBlockEntity_1;
							return var_1.method_17465(chestBlockEntity_2, chestBlockEntity_3);
						}
					}
				}
				return var_1.method_17464(chestBlockEntity_1);
			}
		}
	}

	private static Inventory createCombinedInventory(BlockState blockState_1, World world_1, BlockPos blockPos_1) { return method_17459(blockState_1, world_1, blockPos_1, inventoryCombiner); }

	private static boolean isChestBlocked(IWorld iWorld_1, BlockPos blockPos_1)
	{
		return hasBlockOnTop(iWorld_1, blockPos_1) || hasOcelotOnTop(iWorld_1, blockPos_1);
	}

	private static boolean hasBlockOnTop(BlockView blockView_1, BlockPos blockPos_1)
	{
		BlockPos blockPos_2 = blockPos_1.up();
		return blockView_1.getBlockState(blockPos_2).isSimpleFullBlock(blockView_1, blockPos_2);
	}

	private static boolean hasOcelotOnTop(IWorld iWorld_1, BlockPos blockPos_1) {
		List<CatEntity> cats = iWorld_1.method_18467(CatEntity.class, new BoundingBox(blockPos_1.getX(),blockPos_1.getY() + 1, blockPos_1.getZ(), blockPos_1.getX() + 1, blockPos_1.getY() + 2, blockPos_1.getZ() + 1));
		if (!cats.isEmpty()) for(CatEntity catEntity_1 : cats) if(catEntity_1.isSitting()) return true;
		return false;
	}

	@Override public boolean hasComparatorOutput(BlockState blockState_1) { return true; }

	@Override public int getComparatorOutput(BlockState state, World world, BlockPos pos)
	{
		return Container.calculateComparatorOutput(createCombinedInventory(state, world, pos));
	}

	@Override public BlockState rotate(BlockState state, Rotation rotation)
	{
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override public BlockState mirror(BlockState state, Mirror mirror)
	{
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}
}
