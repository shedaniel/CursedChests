package gd.rf.ninjaphenix.cursedchests.api.item;

import gd.rf.ninjaphenix.cursedchests.api.block.CursedChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.CursedChestBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChestConversionItem extends ChestModifierItem
{
	private Identifier from, to;

	private void upgradeChest(World world, BlockPos pos, CursedChestBlockEntity entity, Direction direction)
	{
		DefaultedList<ItemStack> inventoryData = DefaultedList.create(entity.getInvSize(), ItemStack.EMPTY);
		Inventories.fromTag(entity.toTag(new CompoundTag()), inventoryData);
		world.removeBlockEntity(pos);
		world.setBlockState(pos, Registry.BLOCK.get(to).getDefaultState().with(CursedChestBlock.FACING, direction));
		BlockEntity blockEntity = world.getBlockEntity(pos);
		blockEntity.fromTag(Inventories.toTag(blockEntity.toTag(new CompoundTag()), inventoryData));
	}


	public ChestConversionItem(Identifier from, Identifier to)
	{
		super(new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(16));
		this.from = from;
		this.to = to;
	}

	public ChestConversionItem(CursedChestBlock from, CursedChestBlock to)
	{
		this(Registry.BLOCK.getId(from), Registry.BLOCK.getId(to));
	}

	@Override protected ActionResult useModifierOnChestBlock(ItemUsageContext context, CursedChestBlock mainBlock, BlockPos mainBlockPos, CursedChestBlock otherBlock, BlockPos otherBlockPos)
	{
		World world = context.getWorld();
		PlayerEntity player = context.getPlayer();
		BlockState mainBlockState = world.getBlockState(mainBlockPos);
		if (Registry.BLOCK.getId(mainBlock) != from) return ActionResult.FAIL;
		ItemStack handStack = player.getStackInHand(context.getHand());
		BlockEntity mainBlockEntity = world.getBlockEntity(mainBlockPos);
		if (mainBlockEntity == null) return ActionResult.FAIL;
		Direction rotation = mainBlockState.get(CursedChestBlock.FACING);
		if (otherBlockPos == null || (handStack.getAmount() == 1 && !player.isCreative()))
		{
			if (!world.isClient)
			{
				upgradeChest(world, mainBlockPos, (CursedChestBlockEntity) mainBlockEntity, rotation);
				handStack.subtractAmount(1);
			}
			return ActionResult.SUCCESS;
		}
		else
		{
			BlockEntity otherBlockEntity = world.getBlockEntity(otherBlockPos);
			if (otherBlockEntity == null) return ActionResult.FAIL;
			if (!world.isClient)
			{
				BlockState otherState = world.getBlockState(otherBlockPos);
				upgradeChest(world, mainBlockPos, (CursedChestBlockEntity) mainBlockEntity, rotation);
				upgradeChest(world, otherBlockPos, (CursedChestBlockEntity) otherBlockEntity, rotation);
				world.setBlockState(otherBlockPos, world.getBlockState(otherBlockPos).with(CursedChestBlock.TYPE, otherState.get(CursedChestBlock.TYPE)));
				handStack.subtractAmount(2);
			}
			return ActionResult.SUCCESS;
		}
	}

	@Override protected ActionResult useModifierOnBlock(ItemUsageContext context, Block block)
	{
		return null;
	}

	@Override protected boolean useModifierOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand)
	{
		player.sendMessage(new TextComponent("Modifier used on entity."));
		return false;
	}

	@Override protected TypedActionResult<ItemStack> useModifierInAir(World world, PlayerEntity player, Hand hand)
	{
		return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
	}
}
