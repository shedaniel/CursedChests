package ninjaphenix.cursedchests.api.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class GenericContainer extends Container
{
    private final SidedInventory inventory;
    private final Text containerName;

    protected GenericContainer(int syncId, PlayerInventory playerInv, SidedInventory inv, Text name)
    {
        super(null, syncId);
        this.inventory = inv;
        this.containerName = name;
    }

    public SidedInventory getInventory() { return inventory; }

    @Environment(EnvType.CLIENT)
    public Text getDisplayName() { return containerName; }

    @Override
    public boolean canUse(PlayerEntity player) { return inventory.canPlayerUseInv(player); }

    @Override
    public void close(PlayerEntity player)
    {
        super.close(player);
        inventory.onInvClose(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int slotIndex)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slotList.get(slotIndex);
        if (slot != null && slot.hasStack())
        {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();
            if (slotIndex < inventory.getInvSize()) { if (!insertItem(slotStack, inventory.getInvSize(), slotList.size(), true)) return ItemStack.EMPTY; }
            else if (!insertItem(slotStack, 0, inventory.getInvSize(), false)) return ItemStack.EMPTY;
            if (slotStack.isEmpty()) { slot.setStack(ItemStack.EMPTY); }
            else { slot.markDirty(); }
        }
        return stack;
    }
}
