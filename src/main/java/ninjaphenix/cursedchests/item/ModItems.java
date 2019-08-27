package ninjaphenix.cursedchests.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems
{
    public static void init()
    {
        Identifier wood = new Identifier("cursedchests", "wood_chest");
        Identifier iron = new Identifier("cursedchests", "iron_chest");
        Identifier gold = new Identifier("cursedchests", "gold_chest");
        Identifier diamond = new Identifier("cursedchests", "diamond_chest");
        Identifier obsidian = new Identifier("cursedchests", "obsidian_chest");
        registerConversionItem(wood, iron);
        registerConversionItem(wood, gold);
        registerConversionItem(wood, diamond);
        registerConversionItem(wood, obsidian);
        registerConversionItem(iron, gold);
        registerConversionItem(iron, diamond);
        registerConversionItem(iron, obsidian);
        registerConversionItem(gold, diamond);
        registerConversionItem(gold, obsidian);
        registerConversionItem(diamond, obsidian);
        ChestMutatorItem chestMutator = new ChestMutatorItem();
        Registry.register(Registry.ITEM, new Identifier("cursedchests", "chest_mutator"), chestMutator);
    }

    @SuppressWarnings("UnusedReturnValue")
    private static Item registerConversionItem(Identifier from, Identifier to)
    {
        //String fromName = Registry.BLOCK.getId(from).getPath();
        //String toName = Registry.BLOCK.getId(to).getPath();
        //Item conversionKit = new ChestConversionItem(from, to);
        //Registry.register(Registry.ITEM, new Identifier("cursedchests",
        //        fromName.substring(0, fromName.indexOf('_')) + "_to_" + toName.substring(0, toName.indexOf('_')) + "_conversion_kit"), conversionKit);
        //return conversionKit;
        return new Item(new Item.Settings()); // to fix errors untill I actually implement changes
    }
}
