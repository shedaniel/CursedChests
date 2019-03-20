package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.CursedChestClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks
{
	public static VerticalChestBlock wooden_chest;
	public static VerticalChestBlock iron_chest;
	public static VerticalChestBlock gold_chest;
	public static VerticalChestBlock diamond_chest;

	public static void init()
	{
		wooden_chest = register(new WoodVerticalChestBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).build()));
		iron_chest = register(new IronVerticalChestBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK).build()));
		gold_chest = register(new GoldVerticalChestBlock(FabricBlockSettings.copy(Blocks.GOLD_BLOCK).build()));
		diamond_chest = register(new DiamondVerticalChestBlock(FabricBlockSettings.copy(Blocks.DIAMOND_BLOCK).build()));
	}

	private static VerticalChestBlock register(VerticalChestBlock block)
	{
		Registry.register(Registry.BLOCK, new Identifier("cursedchests", block.name), block);
		Registry.register(Registry.ITEM, new Identifier("cursedchests", block.name), new BlockItem(block, new Item.Settings().itemGroup(ItemGroup.DECORATIONS)));
		return block;
	}
}
