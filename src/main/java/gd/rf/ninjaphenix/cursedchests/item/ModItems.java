package gd.rf.ninjaphenix.cursedchests.item;

import gd.rf.ninjaphenix.cursedchests.api.item.ChestConversionItem;
import gd.rf.ninjaphenix.cursedchests.block.ModBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems
{
	public static void init()
	{
		Item woodToIronUpgrade = new ChestConversionItem(new Item.Settings().itemGroup(ItemGroup.TOOLS),ModBlocks.wood_chest, ModBlocks.iron_chest);
		Registry.register(Registry.ITEM, new Identifier("cursedchests", "wti"), woodToIronUpgrade);
	}
}
