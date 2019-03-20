package gd.rf.ninjaphenix.cursedchests;

import gd.rf.ninjaphenix.cursedchests.block.ModBlocks;
import gd.rf.ninjaphenix.cursedchests.block.entity.DiamondVerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.block.entity.GoldVerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.block.entity.IronVerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.block.entity.WoodVerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.sortthis.ScrollableContainer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TextComponent;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CursedChests implements ModInitializer
{
	public static final BlockEntityType<WoodVerticalChestBlockEntity> WOOD_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "wood_vertical_chest"),
			BlockEntityType.Builder.create(WoodVerticalChestBlockEntity::new).build(null));
	public static final BlockEntityType<IronVerticalChestBlockEntity> IRON_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "iron_vertical_chest"),
			BlockEntityType.Builder.create(IronVerticalChestBlockEntity::new).build(null));
	public static final BlockEntityType<GoldVerticalChestBlockEntity> GOLD_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "gold_vertical_chest"),
			BlockEntityType.Builder.create(GoldVerticalChestBlockEntity::new).build(null));
	public static final BlockEntityType<DiamondVerticalChestBlockEntity> DIAMOND_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "diamond_vertical_chest"),
			BlockEntityType.Builder.create(DiamondVerticalChestBlockEntity::new).build(null));

	@Override public void onInitialize()
	{
		ModBlocks.init();
		// TODO: expand, just testing this feature atm
		// Maybe create a dedicated
		ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier("cursedchests", "scrollcontainer"), ((syncId, identifier, player, buf) -> {
			int inventorySize = buf.readInt();
			TextComponent containerName = buf.readTextComponent();
			CompoundTag data = buf.readCompoundTag();
			DefaultedList<ItemStack> stacks =  DefaultedList.create(inventorySize, ItemStack.EMPTY);
			Inventories.fromTag(data, stacks);
			BasicInventory inventory = new BasicInventory(inventorySize);
			for(int i=0; i<inventorySize; i++)
			{
				inventory.setInvStack(i, stacks.get(i));
			}
			return new ScrollableContainer(syncId, player.inventory, inventory, containerName);
		}));
	}
}
