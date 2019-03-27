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

import java.util.ArrayList;
import java.util.List;

public class ScrollableContainer extends Container
{
	private final TextComponent containerName;
	private final SidedInventory inventory;
	private final int rows;
	private final int realRows;
	private String searchTerm = "";
	private int offset = 0;
	private List<Slot> sortedSlotList;

	public ScrollableContainer(int syncId, PlayerInventory playerInventory, SidedInventory inventory, TextComponent containerName)
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
			int yPos = -2000;
			if (y < rows) yPos = 18 + y * 18;
			for (int x = 0; x < 9; ++x) addSlot(new Slot(inventory, x + y * 9, 8 + x * 18, yPos));
		}
		for (int y = 0; y < 3; ++y) for (int x = 0; x < 9; ++x) addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + int_3));
		for (int x = 0; x < 9; ++x) addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + int_3));
		updateSlotPositions(offset, true);
	}

	public SidedInventory getInventory(){ return inventory; }
	@Environment(EnvType.CLIENT) public int getRows(){ return realRows; }
	@Environment(EnvType.CLIENT) public TextComponent getDisplayName(){ return containerName; }
	@Override public boolean canUse(PlayerEntity player){ return inventory.canPlayerUseInv(player); }
	@Override public void close(PlayerEntity player){ super.close(player); inventory.onInvClose(player); }

	public void setSearchTerm(String term)
	{
		searchTerm = term.toLowerCase();
		updateSlotPositions(offset, true);
	}

	public void updateSlotPositions(int offset, boolean termChanged)
	{
		this.offset = offset;
		if(termChanged && !searchTerm.equals(""))
		{
			sortedSlotList = new ArrayList<>(slotList.subList(0, realRows*9));
			sortedSlotList.sort((a, b) ->
			{
				ItemStack stack_a = a.getStack();
				ItemStack stack_b = b.getStack();
				if (stack_a.isEmpty() && !stack_b.isEmpty()) return 1;
				else if (stack_b.isEmpty() && !stack_a.isEmpty()) return -1;
				else if (stack_a.isEmpty() && stack_b.isEmpty()) return 0;
				return stack_a.getItem().getTranslatedNameTrimmed(stack_a).getString().toLowerCase().contains(searchTerm) ? -1 :
						stack_b.getItem().getTranslatedNameTrimmed(stack_b).getString().toLowerCase().contains(searchTerm) ? 1 : 0;
			});
		}
		else if(termChanged)
		{
			sortedSlotList = new ArrayList<>(slotList.subList(0, realRows*9));
		}
		setSlotPositionsWithOffset();
	}

	private void setSlotPositionsWithOffset()
	{
		int index = 0;
		for(Slot slot : sortedSlotList)
		{
			Slot real_slot = slotList.get(slot.id);
			int x = index % 9;
			int y = (index / 9) - offset;
			real_slot.xPosition = 8 + 18 * x;
			real_slot.yPosition = (y >= rows || y < 0) ? -2000 : 18 + 18 * y;
			index++;
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
}
