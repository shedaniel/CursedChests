package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.api.CursedChestRegistry;
import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.entity.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks
{
	public static BlockEntityType<WoodVerticalChestBlockEntity> WOOD_VERTICAL_CHEST;
	public static BlockEntityType<IronVerticalChestBlockEntity> IRON_VERTICAL_CHEST;
	public static BlockEntityType<GoldVerticalChestBlockEntity> GOLD_VERTICAL_CHEST;
	public static BlockEntityType<DiamondVerticalChestBlockEntity> DIAMOND_VERTICAL_CHEST;
	public static BlockEntityType<ObsidianVerticalChestBlockEntity> OBSIDIAN_VERTICAL_CHEST;

	public static VerticalChestBlock wood_chest;
	public static VerticalChestBlock iron_chest;
	public static VerticalChestBlock gold_chest;
	public static VerticalChestBlock diamond_chest;
	public static VerticalChestBlock obsidian_chest;

	public static void init()
	{
		wood_chest = register(new WoodVerticalChestBlock());
		WOOD_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "wood_vertical_chest"), BlockEntityType.Builder.create(WoodVerticalChestBlockEntity::new, wood_chest).build(null));
		iron_chest = register(new IronVerticalChestBlock());
		IRON_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "iron_vertical_chest"), BlockEntityType.Builder.create(IronVerticalChestBlockEntity::new, iron_chest).build(null));
		gold_chest = register(new GoldVerticalChestBlock());
		GOLD_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "gold_vertical_chest"), BlockEntityType.Builder.create(GoldVerticalChestBlockEntity::new, gold_chest).build(null));
		diamond_chest = register(new DiamondVerticalChestBlock());
		DIAMOND_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "diamond_vertical_chest"), BlockEntityType.Builder.create(DiamondVerticalChestBlockEntity::new, diamond_chest).build(null));
		obsidian_chest = register(new ObsidianVerticalChestBlock());
		OBSIDIAN_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "obsidian_vertical_chest"), BlockEntityType.Builder.create(ObsidianVerticalChestBlockEntity::new, obsidian_chest).build(null));
	}

	private static VerticalChestBlock register(VerticalChestBlock block)
	{
		Registry.register(Registry.BLOCK, new Identifier("cursedchests", block.getName()), block);
		Registry.register(Registry.ITEM, new Identifier("cursedchests", block.getName()), new BlockItem(block, new Item.Settings().itemGroup(ItemGroup.DECORATIONS)));
		CursedChestRegistry.registerChest(block);
		return block;
	}
}
