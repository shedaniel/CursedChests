package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.api.CursedChestRegistry;
import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks
{
	public static BlockEntityType<VerticalChestBlockEntity> VERTICAL_CHEST;

	public static VerticalChestBlock wood_chest;
	public static VerticalChestBlock iron_chest;
	public static VerticalChestBlock gold_chest;
	public static VerticalChestBlock diamond_chest;
	public static VerticalChestBlock obsidian_chest;

	public static void init()
	{
		wood_chest = register(new VerticalChestBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).build()), "wood_chest", 3, new Identifier("minecraft", "textures/entity/chest/normal.png"), new Identifier("cursedchests", "textures/entity/wood_chest/double.png"), new TranslatableComponent("container.cursedchests.wood_chest"));
		iron_chest = register(new VerticalChestBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK).build()), "iron_chest", 6, new Identifier("cursedchests", "textures/entity/iron_chest/single.png"), new Identifier("cursedchests", "textures/entity/iron_chest/double.png"), new TranslatableComponent("container.cursedchests.iron_chest"));
		gold_chest = register(new VerticalChestBlock(FabricBlockSettings.copy(Blocks.GOLD_BLOCK).build()), "gold_chest", 9, new Identifier("cursedchests", "textures/entity/gold_chest/single.png"), new Identifier("cursedchests", "textures/entity/gold_chest/double.png"), new TranslatableComponent("container.cursedchests.gold_chest"));
		diamond_chest = register(new VerticalChestBlock(FabricBlockSettings.copy(Blocks.DIAMOND_BLOCK).build()), "diamond_chest", 12, new Identifier("cursedchests", "textures/entity/diamond_chest/single.png"), new Identifier("cursedchests", "textures/entity/diamond_chest/double.png"), new TranslatableComponent("container.cursedchests.diamond_chest"));
		obsidian_chest = register(new VerticalChestBlock(FabricBlockSettings.copy(Blocks.OBSIDIAN).build()), "obsidian_chest", 12, new Identifier("cursedchests", "textures/entity/obsidian_chest/single.png"), new Identifier("cursedchests", "textures/entity/obsidian_chest/double.png"), new TranslatableComponent("container.cursedchests.obsidian_chest"));
		// todo: move to api
		VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "vertical_chest"), BlockEntityType.Builder.create(VerticalChestBlockEntity::new, wood_chest, iron_chest, gold_chest, diamond_chest, obsidian_chest).
				build(null));

		Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "wood_vertical_chest"), BlockEntityType.Builder.create(VerticalChestBlockEntity::new, wood_chest).build(null));
		Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "iron_vertical_chest"), BlockEntityType.Builder.create(VerticalChestBlockEntity::new, iron_chest).build(null));
		Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "gold_vertical_chest"), BlockEntityType.Builder.create(VerticalChestBlockEntity::new, gold_chest).build(null));
		Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "diamond_vertical_chest"), BlockEntityType.Builder.create(VerticalChestBlockEntity::new, diamond_chest).build(null));
		Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "obsidian_vertical_chest"), BlockEntityType.Builder.create(VerticalChestBlockEntity::new, obsidian_chest).build(null));
	}

	private static VerticalChestBlock register(VerticalChestBlock block, String name, int rows, Identifier singleTexture, Identifier doubleTexture, TranslatableComponent containerName)
	{
		Registry.register(Registry.BLOCK, new Identifier("cursedchests", name), block);
		Registry.register(Registry.ITEM, new Identifier("cursedchests", name), new BlockItem(block, new Item.Settings().itemGroup(ItemGroup.DECORATIONS)));
		CursedChestRegistry.registerChest(new Identifier("cursedchests", name), rows, singleTexture, doubleTexture, containerName);
		return block;
	}
}
