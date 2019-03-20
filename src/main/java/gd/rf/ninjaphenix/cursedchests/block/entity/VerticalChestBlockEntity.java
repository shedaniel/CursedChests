package gd.rf.ninjaphenix.cursedchests.block.entity;

import gd.rf.ninjaphenix.cursedchests.block.VerticalChestBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.EnvironmentInterfaces;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.container.GenericContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

@EnvironmentInterfaces({@EnvironmentInterface(value = EnvType.CLIENT, itf = ChestAnimationProgress.class)})
public abstract class VerticalChestBlockEntity extends LootableContainerBlockEntity implements ChestAnimationProgress, Tickable
{
	private DefaultedList<ItemStack> inventory;
	private float animationAngle;
	private float lastAnimationAngle;
	private int viewerCount;
	private int ticksOpen;

	VerticalChestBlockEntity(BlockEntityType type)
	{
		super(type);
		this.inventory = DefaultedList.create(getInvSize(), ItemStack.EMPTY);
	}

	public boolean isInvEmpty()
	{
		Iterator var1 = this.inventory.iterator();
		ItemStack itemStack_1;
		do
		{
			if (!var1.hasNext()) { return true; }
			itemStack_1 = (ItemStack)var1.next();
		}
		while(itemStack_1.isEmpty());
		return false;
	}

	public void fromTag(CompoundTag compoundTag_1)
	{
		super.fromTag(compoundTag_1);
		this.inventory = DefaultedList.create(this.getInvSize(), ItemStack.EMPTY);
		if (!this.deserializeLootTable(compoundTag_1)) { Inventories.fromTag(compoundTag_1, this.inventory); }
	}

	public CompoundTag toTag(CompoundTag compoundTag_1)
	{
		super.toTag(compoundTag_1);
		if (!this.serializeLootTable(compoundTag_1)) { Inventories.toTag(compoundTag_1, this.inventory); }
		return compoundTag_1;
	}

	public void tick() {
		int int_1 = this.pos.getX();
		int int_2 = this.pos.getY();
		int int_3 = this.pos.getZ();
		++this.ticksOpen;
		this.viewerCount = recalculateViewerCountIfNecessary(this.world, this, this.ticksOpen, int_1, int_2, int_3, this.viewerCount);
		this.lastAnimationAngle = this.animationAngle;
		if (this.viewerCount > 0 && this.animationAngle == 0.0F) { this.playSound(SoundEvents.BLOCK_CHEST_OPEN); }
		if (this.viewerCount == 0 && this.animationAngle > 0.0F || this.viewerCount > 0 && this.animationAngle < 1.0F)
		{
			float float_2 = this.animationAngle;
			if (this.viewerCount > 0) { this.animationAngle += 0.1F; }
			else { this.animationAngle -= 0.1F; }
			if (this.animationAngle > 1.0F) { this.animationAngle = 1.0F; }
			if (this.animationAngle < 0.5F && float_2 >= 0.5F) { this.playSound(SoundEvents.BLOCK_CHEST_CLOSE); }
			if (this.animationAngle < 0.0F) { this.animationAngle = 0.0F; }
		}

	}

	private static int recalculateViewerCountIfNecessary(World world_1, LockableContainerBlockEntity lockableContainerBlockEntity_1, int int_1, int int_2, int int_3, int int_4, int int_5)
	{
		if (!world_1.isClient && int_5 != 0 && (int_1 + int_2 + int_3 + int_4) % 200 == 0)
		{
			int_5 = 0;
			List<PlayerEntity> list_1 = world_1.method_18467(PlayerEntity.class, new BoundingBox(int_2 - 5, int_3 - 5, int_4 - 5, int_2 + 6, int_3 + 6, int_4 + 6));
			Iterator var9 = list_1.iterator();
			while(true)
			{
				Inventory inventory_1;
				do
				{
					PlayerEntity playerEntity_1;
					do
					{
						if (!var9.hasNext()) { return int_5; }
						playerEntity_1 = (PlayerEntity)var9.next();
					}
					while(!(playerEntity_1.container instanceof GenericContainer));
					inventory_1 = ((GenericContainer)playerEntity_1.container).getInventory();
				}
				while(inventory_1 != lockableContainerBlockEntity_1 && (!(inventory_1 instanceof DoubleInventory) || !((DoubleInventory)inventory_1).isPart(lockableContainerBlockEntity_1)));
				++int_5;
			}
		} else { return int_5; }
	}

	private void playSound(SoundEvent soundEvent_1)
	{
		double double_1;
		double double_2;
		double double_3;
		VerticalChestBlock.VerticalChestType chestType_1 = this.getCachedState().get(VerticalChestBlock.TYPE);
		if(chestType_1 == VerticalChestBlock.VerticalChestType.SINGLE)
		{
			double_1 = this.pos.getX() + 0.5D;
			double_2 = this.pos.getY() + 0.5D;
			double_3 = this.pos.getZ() + 0.5D;
		}
		else if(chestType_1 == VerticalChestBlock.VerticalChestType.TOP)
		{
			double_1 = this.pos.getX() + 0.5D;
			double_2 = this.pos.getY() + 0.5D;
			double_3 = this.pos.getZ();
		}
		else
		{
			double_1 = this.pos.getX() + 0.5D;
			double_2 = this.pos.getY() + 0.5D;
			double_3 = this.pos.getZ() + 1.0D;
		}
		this.world.playSound(null, double_1, double_2, double_3, soundEvent_1, SoundCategory.BLOCK, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
	}

	public boolean onBlockAction(int int_1, int int_2)
	{
		if (int_1 == 1) { this.viewerCount = int_2; return true; } else { return super.onBlockAction(int_1, int_2); }
	}

	public void onInvOpen(PlayerEntity playerEntity_1)
	{
		if (!playerEntity_1.isSpectator())
		{
			if (this.viewerCount < 0) { this.viewerCount = 0; }
			++this.viewerCount;
			this.onInvOpenOrClose();
		}
	}

	public void onInvClose(PlayerEntity playerEntity_1)
	{
		if (!playerEntity_1.isSpectator())
		{
			--this.viewerCount;
			this.onInvOpenOrClose();
		}
	}

	private void onInvOpenOrClose()
	{
		Block block_1 = this.getCachedState().getBlock();
		if (block_1 instanceof VerticalChestBlock)
		{
			this.world.addBlockAction(this.pos, block_1, 1, this.viewerCount);
			this.world.updateNeighborsAlways(this.pos, block_1);
		}

	}

	protected DefaultedList<ItemStack> getInvStackList() { return this.inventory; }
	protected void setInvStackList(DefaultedList<ItemStack> defaultedList_1) { this.inventory = defaultedList_1; }
	@Environment(EnvType.CLIENT) public float getAnimationProgress(float float_1) { return MathHelper.lerp(float_1, this.lastAnimationAngle, this.animationAngle); }
	protected Container createContainer(int int_1, PlayerInventory playerInventory_1) { return new GenericContainer(ContainerType.GENERIC_9X6, int_1, playerInventory_1, this, getInvSize()/9); }

	public abstract Identifier getTexture(boolean isDouble);

	public static int getPlayersLookingInChestCount(BlockView blockView_1, BlockPos blockPos_1)
	{
		BlockState blockState_1 = blockView_1.getBlockState(blockPos_1);
		if (blockState_1.getBlock().hasBlockEntity())
		{
			BlockEntity blockEntity_1 = blockView_1.getBlockEntity(blockPos_1);
			if (blockEntity_1 instanceof VerticalChestBlockEntity) { return ((VerticalChestBlockEntity) blockEntity_1).viewerCount; }
		}
		return 0;
	}

	public static void copyInventory(VerticalChestBlockEntity chestBlockEntity_1, VerticalChestBlockEntity chestBlockEntity_2)
	{
		DefaultedList<ItemStack> defaultedList_1 = chestBlockEntity_1.getInvStackList();
		chestBlockEntity_1.setInvStackList(chestBlockEntity_2.getInvStackList());
		chestBlockEntity_2.setInvStackList(defaultedList_1);
	}
}
