package gd.rf.ninjaphenix.cursedchests.api.block.entity;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestType;
import gd.rf.ninjaphenix.cursedchests.api.container.ScrollableContainer;
import gd.rf.ninjaphenix.cursedchests.api.inventory.DoubleSidedInventory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.fabricmc.api.EnvironmentInterfaces;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import java.util.Iterator;
import java.util.List;

@EnvironmentInterfaces({@EnvironmentInterface(value = EnvType.CLIENT, itf = ChestAnimationProgress.class)})
public abstract class VerticalChestBlockEntity extends LootableContainerBlockEntity implements ChestAnimationProgress, Tickable, SidedInventory
{
	private DefaultedList<ItemStack> inventory;
	private float animationAngle;
	private float lastAnimationAngle;
	private int viewerCount;
	private int ticksOpen;
	private int[] SLOTS;

	public VerticalChestBlockEntity(BlockEntityType type)
	{
		super(type);
		inventory = DefaultedList.create(getInvSize(), ItemStack.EMPTY);
		SLOTS = new int[getInvSize()];
		for (int i = 0; i<SLOTS.length; i++){ SLOTS[i] = i; }
	}

	@Override public boolean onBlockAction(int actionId, int value){ if (actionId == 1){ viewerCount = value; return true; } else { return super.onBlockAction(actionId, value); } }
	@Override protected DefaultedList<ItemStack> getInvStackList(){ return inventory; }
	@Override public void setInvStackList(DefaultedList<ItemStack> defaultedList_1){ inventory = defaultedList_1; }
	@Environment(EnvType.CLIENT) @Override public float getAnimationProgress(float float_1){ return MathHelper.lerp(float_1, lastAnimationAngle, animationAngle); }
	@Override protected Container createContainer(int int_1, PlayerInventory playerInventory){ return null; }
	@Override public int[] getInvAvailableSlots(Direction direction){ return SLOTS; }
	@Override public boolean canInsertInvStack(int slot, ItemStack stack, Direction direction){ return this.isValidInvStack(slot, stack); }
	@Override public boolean canExtractInvStack(int slot, ItemStack stack, Direction direction){ return true; }
	public abstract Identifier getTexture(boolean isDouble);

	@Override public boolean isInvEmpty()
	{
		Iterator<ItemStack> inventoryIterator = inventory.iterator();
		ItemStack stack;
		do
		{
			if (!inventoryIterator.hasNext()) return true;
			stack = inventoryIterator.next();
		}
		while (stack.isEmpty());
		return false;
	}

	@Override public void fromTag(CompoundTag tag)
	{
		super.fromTag(tag);
		inventory = DefaultedList.create(getInvSize(), ItemStack.EMPTY);
		if (!deserializeLootTable(tag)) Inventories.fromTag(tag, inventory);
	}

	@Override public CompoundTag toTag(CompoundTag tag)
	{
		super.toTag(tag);
		if (!serializeLootTable(tag)) Inventories.toTag(tag, inventory);
		return tag;
	}

	@Override public void tick()
	{
		viewerCount = recalculateViewCount(world, this, ++ticksOpen, pos.getX(), pos.getY(), pos.getZ(), viewerCount);
		lastAnimationAngle = animationAngle;
		if (viewerCount > 0 && animationAngle == 0.0F) playSound(SoundEvents.BLOCK_CHEST_OPEN);
		if (viewerCount == 0 && animationAngle > 0.0F || viewerCount > 0 && animationAngle < 1.0F)
		{
			float float_2 = animationAngle;
			if (viewerCount > 0) animationAngle += 0.1F; else animationAngle -= 0.1F;
			animationAngle = MathHelper.clamp(animationAngle, 0, 1);
			if (animationAngle < 0.5F && float_2 >= 0.5F) playSound(SoundEvents.BLOCK_CHEST_CLOSE);
		}
	}

	private static int recalculateViewCount(World world, VerticalChestBlockEntity instance, int ticksOpen, int x, int y, int z, int viewerCount)
	{
		if (!world.isClient && viewerCount != 0 && (ticksOpen + x + y + z) % 200 == 0)
		{
			viewerCount = 0;
			List<PlayerEntity> playersInRange = world.getEntities(PlayerEntity.class, new BoundingBox(x - 5, y - 5, z - 5, x + 6, y + 6, z + 6));
			Iterator<PlayerEntity> playerIterator = playersInRange.iterator();
			while (true)
			{
				SidedInventory inventory;
				do
				{
					PlayerEntity player;
					do
					{
						if (!playerIterator.hasNext()) return viewerCount;
						player = playerIterator.next();
					}
					while (!(player.container instanceof ScrollableContainer));
					inventory = ((ScrollableContainer) player.container).getInventory();
				}
				while (inventory != instance && (!(inventory instanceof DoubleSidedInventory) || !((DoubleSidedInventory) inventory).isPart(instance)));
				viewerCount++;
			}
		}
		else return viewerCount;
	}

	private void playSound(SoundEvent soundEvent)
	{
		double z = pos.getZ();
		VerticalChestType chestType = getCachedState().get(VerticalChestBlock.TYPE);
		if (chestType == VerticalChestType.SINGLE) z += 0.5D;
		else if (chestType == VerticalChestType.BOTTOM) z += 1.0D;
		world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, z, soundEvent, SoundCategory.BLOCK, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
	}

	@Override public void onInvOpen(PlayerEntity player)
	{
		if (player.isSpectator()) return;
		++viewerCount;
		onInvOpenOrClose();
	}

	@Override public void onInvClose(PlayerEntity player)
	{
		if (player.isSpectator()) return;
		--viewerCount;
		if (viewerCount < 0) viewerCount = 0;
		onInvOpenOrClose();
	}

	private void onInvOpenOrClose()
	{
		Block block = getCachedState().getBlock();
		if (block instanceof VerticalChestBlock)
		{
			world.addBlockAction(pos, block, 1, viewerCount);
			world.updateNeighborsAlways(pos, block);
		}
	}
}
