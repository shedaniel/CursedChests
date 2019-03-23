package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks
{
	public static VerticalChestBlock wood_chest;
	public static VerticalChestBlock iron_chest;
	public static VerticalChestBlock gold_chest;
	public static VerticalChestBlock diamond_chest;
	public static VerticalChestBlock obsidian_chest;

	public static void init()
	{
		wood_chest = register(new WoodVerticalChestBlock());
		iron_chest = register(new IronVerticalChestBlock());
		gold_chest = register(new GoldVerticalChestBlock());
		diamond_chest = register(new DiamondVerticalChestBlock());
		obsidian_chest = register(new ObsidianVerticalChestBlock());
	}

	private static VerticalChestBlock register(VerticalChestBlock block)
	{
		Registry.register(Registry.BLOCK, new Identifier("cursedchests", block.name), block);
		Registry.register(Registry.ITEM, new Identifier("cursedchests", block.name), new BlockItem(block, new Item.Settings().itemGroup(ItemGroup.DECORATIONS)));
		return block;
	}
}
