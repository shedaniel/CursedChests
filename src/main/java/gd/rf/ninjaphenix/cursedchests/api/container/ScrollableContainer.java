package gd.rf.ninjaphenix.cursedchests.api.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TextComponent;

public class ScrollableContainer extends Container
{
	private final TextComponent containerName;
	private final SidedInventory inventory;
	private final int rows;
	private final int realRows;
	private String searchTerm;
	private int offset;

	public ScrollableContainer(int syncId, PlayerInventory playerInventory, SidedInventory inventory, TextComponent containerName)
	{
		super(null, syncId);
		this.inventory = inventory;
		this.containerName = containerName;
		realRows = inventory.getInvSize()/9;
		rows = realRows > 6 ? 6 : realRows;
		searchTerm = "";
		offset = 0;
		int int_3 = (rows - 4) * 18;
		inventory.onInvOpen(playerInventory.player);
		for(int y = 0; y < realRows; ++y)
		{
			int yPos = -2000;
			if (y < rows) yPos = 18 + y * 18;
			for (int x = 0; x < 9; ++x) addSlot(new Slot(inventory, x + y * 9, 8 + x * 18, yPos));
		}
		for (int y = 0; y < 3; ++y) for (int x = 0; x < 9; ++x) addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + int_3));
		for (int x = 0; x < 9; ++x) addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + int_3));
	}

	public SidedInventory getInventory(){ return inventory; }
	@Environment(EnvType.CLIENT) public int getRows(){ return realRows; }
	@Environment(EnvType.CLIENT) public TextComponent getDisplayName(){ return containerName; }
	@Override public boolean canUse(PlayerEntity player){ return inventory.canPlayerUseInv(player); }
	@Override public void close(PlayerEntity player){ super.close(player); inventory.onInvClose(player); }

	public void setSearchTerm(String term)
	{
		searchTerm = term.toLowerCase();
		if(searchTerm.equals(""))
		{
			offset = 0;
			setSlotPositionsWithOffset();
		}
	}

	public void updateSlotPositions(int offset)
	{
		this.offset = offset;
		if(!searchTerm.equals(""))
		{
			int slotIndex = 0;
			Slot slot;
			ItemStack stack;
			int newX, newY, xPos, yPos;
			for (int y = 0; y < realRows; ++y)
			{
				for(int x = 0; x < 9; ++x)
				{
					slot = slotList.get(y*9 + x);
					stack = slot.getStack();
					if(!stack.isEmpty() && stack.getItem().getTranslatedNameTrimmed(stack).getString().toLowerCase().contains(searchTerm))
					{
						newX = (slotIndex % 9);
						xPos = 8 + newX * 18;
						newY = ((slotIndex / 9)-offset);
						yPos = 18 + newY * 18;
						if (newY >= rows || newY < 0) yPos = -2000;
						slot.xPosition = xPos;
						slot.yPosition = yPos;
						slotIndex++;
					}
					else
					{
						slot.xPosition = 0;
						slot.yPosition = -2000;
					}
				}
			}
			return;
		}
		setSlotPositionsWithOffset();
	}

	private void setSlotPositionsWithOffset()
	{
		for(int y = 0; y < realRows; ++y)
		{
			Slot slot;
			int ny = y - offset;
			int yPos = 18 + ny * 18;
			if (ny >= rows || ny < 0) yPos = -2000;
			for (int x = 0; x < 9; ++x)
			{
				slot = slotList.get(x + 9*y);
				slot.xPosition = 8 + x * 18;
				slot.yPosition = yPos;
			}
		}
	}

	@Override public ItemStack transferSlot(PlayerEntity player, int slotIndex)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = slotList.get(slotIndex);
		if (slot != null && slot.hasStack())
		{
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();
			if (slotIndex < inventory.getInvSize()){ if (!insertItem(slotStack, inventory.getInvSize(), slotList.size(), true)) return ItemStack.EMPTY; }
			else if (!insertItem(slotStack, 0, inventory.getInvSize(), false)) return ItemStack.EMPTY;
			if (slotStack.isEmpty()) slot.setStack(ItemStack.EMPTY);
			else slot.markDirty();
		}
		return stack;
	}

	@Override public void sendContentUpdates()
	{
		updateSlotPositions(offset);
		super.sendContentUpdates();
	}
}
