package gd.rf.ninjaphenix.cursedchests.sortthis;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TextComponent;

public class ScrollableContainer extends Container
{
	private final TextComponent containerName;
	private final Inventory inventory;
	private final int rows;
	private final int realRows;

	public ScrollableContainer(int syncId, PlayerInventory playerInventory, Inventory inventory, TextComponent containerName)
	{
		super(null, syncId);
		this.inventory = inventory;
		this.containerName = containerName;
		realRows = inventory.getInvSize()/9;
		rows = realRows > 6 ? 6 : realRows;
		int int_3 = (rows - 4) * 18;
		inventory.onInvOpen(playerInventory.player);
		for(int y = 0; y < realRows; ++y)
		{
			int yPos = 18 + y * 18;
			if(y >= rows){yPos = -2000;}
			for(int x = 0; x < 9; ++x) { this.addSlot(new Slot(inventory, x + y * 9, 8 + x * 18, yPos)); }
		}
		for(int y = 0; y < 3; ++y)
		{
			for(int x = 0; x < 9; ++x) { this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + int_3)); }
		}
		for(int x = 0; x < 9; ++x) { this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + int_3)); }
	}

	@Override public void close(PlayerEntity player)
	{
		super.close(player);
		this.inventory.onInvClose(player);
	}

	public void updateSlotPositions(int offset)
	{
		for(int y = 0; y < realRows; ++y)
		{
			int ny = (y-offset);
			int yPos = 18 + ny * 18;
			if(ny >= rows || ny < 0){yPos = -2000;}
			for(int x = 0; x < 9; ++x) { this.slotList.get(x + 9*y).yPosition = yPos; }
		}
	}

	public Inventory getInventory(){return this.inventory;}
	@Environment(EnvType.CLIENT) int getRows() { return realRows; }
	TextComponent getDisplayName() { return containerName; }
	@Override public boolean canUse(PlayerEntity player) { return this.inventory.canPlayerUseInv(player); }

	@Override public ItemStack transferSlot(PlayerEntity player, int slotIndex)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slotList.get(slotIndex);
		if (slot != null && slot.hasStack())
		{
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();
			if (slotIndex < this.inventory.getInvSize())
			{
				if (!this.insertItem(slotStack, this.inventory.getInvSize(), this.slotList.size(), true)) return ItemStack.EMPTY;
			}
			else if (!this.insertItem(slotStack, 0, this.inventory.getInvSize(), false)) return ItemStack.EMPTY;
			if (slotStack.isEmpty()) slot.setStack(ItemStack.EMPTY);
			else slot.markDirty();
		}
		return stack;
	}
}
