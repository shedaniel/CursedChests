package gd.rf.ninjaphenix.cursedchests.sortthis;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.TextComponent;

public class ScrollableContainer  extends Container
{
	private final TextComponent containerName;
	private final Inventory inventory;
	private final int rows;
	private final int realRows;

	// todo: Make it scroll and sync inventory
	public ScrollableContainer(int syncId, PlayerInventory playerInventory, Inventory inventory, TextComponent containerName)
	{
		super(null, syncId);
		this.inventory = inventory;
		this.containerName = containerName;
		realRows = inventory.getInvSize()/9;
		rows = realRows > 6 ? 6 : realRows;
		int int_3 = (rows - 4) * 18;
		inventory.onInvOpen(playerInventory.player);

		for(int y = 0; y < rows; ++y) {
			for(int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(inventory, x + y * 9, 8 + x * 18, 18 + y * 18));
			}
		}

		for(int y = 0; y < 3; ++y) {
			for(int x = 0; x < 9; ++x) {
				this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 103 + y * 18 + int_3));
			}
		}

		for(int x = 0; x < 9; ++x) { this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 161 + int_3)); }
	}

	@Override public boolean canUse(PlayerEntity playerEntity) { return true; }

	@Override public void close(PlayerEntity player)
	{
		super.close(player);
		this.inventory.onInvClose(player);
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	@Environment(EnvType.CLIENT) int getRows() {
		return realRows;
	}

	TextComponent getDisplayName()
	{
		return containerName;
	}

}
