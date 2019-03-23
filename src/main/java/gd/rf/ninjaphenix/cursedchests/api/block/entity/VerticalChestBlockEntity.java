package gd.rf.ninjaphenix.cursedchests.api.block.entity;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.container.ScrollableContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.EnvironmentInterfaces;
import net.minecraft.block.Block;
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
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.MathHelper;
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

	public VerticalChestBlockEntity(BlockEntityType type)
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

	public void fromTag(CompoundTag tag)
	{
		super.fromTag(tag);
		this.inventory = DefaultedList.create(this.getInvSize(), ItemStack.EMPTY);
		if (!this.deserializeLootTable(tag)) { Inventories.fromTag(tag, this.inventory); }
	}

	public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);
		if (!this.serializeLootTable(tag)) { Inventories.toTag(tag, this.inventory); }
		return tag;
	}

	public void tick() {
		int x = this.pos.getX();
		int y = this.pos.getY();
		int z = this.pos.getZ();
		++this.ticksOpen;
		this.viewerCount = recalculateViewerCountIfNecessary(this.world, this, this.ticksOpen, x, y, z, this.viewerCount);
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

	private static int recalculateViewerCountIfNecessary(World world, LockableContainerBlockEntity instance, int ticksOpen, int x, int y, int z, int viewerCount)
	{
		if (!world.isClient && viewerCount != 0 && (ticksOpen + x + y + z) % 200 == 0)
		{
			viewerCount = 0;
			List<PlayerEntity> list_1 = world.method_18467(PlayerEntity.class, new BoundingBox(x - 5, y - 5, z - 5, x + 6, y + 6, z + 6));
			Iterator var9 = list_1.iterator();
			while(true)
			{
				Inventory inventory_1;
				do
				{
					PlayerEntity player;
					do
					{
						if (!var9.hasNext()) { return viewerCount; }
						player = (PlayerEntity)var9.next();
					}
					while(!(player.container instanceof ScrollableContainer));
					inventory_1 = ((ScrollableContainer)player.container).getInventory();
				}
				while(inventory_1 != instance && (!(inventory_1 instanceof DoubleInventory) || !((DoubleInventory)inventory_1).isPart(instance)));
				++viewerCount;
			}
		} else { return viewerCount; }
	}

	private void playSound(SoundEvent soundEvent)
	{
		double x = this.pos.getX() + 0.5D;
		double y = this.pos.getY() + 0.5D;
		double z = this.pos.getZ();
		VerticalChestBlock.VerticalChestType chestType = this.getCachedState().get(VerticalChestBlock.TYPE);
		if(chestType == VerticalChestBlock.VerticalChestType.SINGLE) { z += 0.5D; }
		else if(chestType == VerticalChestBlock.VerticalChestType.BOTTOM) { z += 1.0D; }
		this.world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCK, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
	}

	@Override public void onInvOpen(PlayerEntity player)
	{
		if (!player.isSpectator())
		{
			if (this.viewerCount < 0) { this.viewerCount = 0; }
			++this.viewerCount;
			this.onInvOpenOrClose();
		}
	}

	@Override public void onInvClose(PlayerEntity player)
	{
		if (!player.isSpectator())
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

	@Override public boolean onBlockAction(int int_1, int int_2)
	{
		if (int_1 == 1) { this.viewerCount = int_2; return true; } else { return super.onBlockAction(int_1, int_2); }
	}
	@Override protected DefaultedList<ItemStack> getInvStackList() { return this.inventory; }
	@Override public void setInvStackList(DefaultedList<ItemStack> defaultedList_1) { this.inventory = defaultedList_1; }
	@Environment(EnvType.CLIENT) @Override public float getAnimationProgress(float float_1) { return MathHelper.lerp(float_1, this.lastAnimationAngle, this.animationAngle); }
	@Override protected Container createContainer(int int_1, PlayerInventory playerInventory_1) { return new GenericContainer(ContainerType.GENERIC_9X6, int_1, playerInventory_1, this, getInvSize()/9); }
	public abstract Identifier getTexture(boolean isDouble);
}
