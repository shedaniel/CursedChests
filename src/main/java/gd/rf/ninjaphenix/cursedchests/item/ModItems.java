package gd.rf.ninjaphenix.cursedchests.item;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
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
        registerConversionItem(ModBlocks.wood_chest, ModBlocks.iron_chest);
        registerConversionItem(ModBlocks.wood_chest, ModBlocks.gold_chest);
        registerConversionItem(ModBlocks.wood_chest, ModBlocks.diamond_chest);
        registerConversionItem(ModBlocks.wood_chest, ModBlocks.obsidian_chest);
        registerConversionItem(ModBlocks.iron_chest, ModBlocks.gold_chest);
        registerConversionItem(ModBlocks.iron_chest, ModBlocks.diamond_chest);
        registerConversionItem(ModBlocks.iron_chest, ModBlocks.obsidian_chest);
        registerConversionItem(ModBlocks.gold_chest, ModBlocks.diamond_chest);
        registerConversionItem(ModBlocks.gold_chest, ModBlocks.obsidian_chest);
        registerConversionItem(ModBlocks.diamond_chest, ModBlocks.obsidian_chest);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static Item registerConversionItem(VerticalChestBlock from, VerticalChestBlock to)
    {
        String fromName = from.getName();
        String toName = to.getName();
        Item conversionKit = new ChestConversionItem(new Item.Settings().itemGroup(ItemGroup.TOOLS), from, to);
        Registry.register(Registry.ITEM, new Identifier("cursedchests",
                fromName.substring(0, fromName.indexOf('_')) + "_to_" + toName.substring(0, toName.indexOf('_')) + "_conversion_kit"), conversionKit);
        return conversionKit;
    }
}
