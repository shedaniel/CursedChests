package ninjaphenix.cursedchests.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ninjaphenix.cursedchests.api.Registries;
import ninjaphenix.cursedchests.api.block.CursedChestBlock;
import ninjaphenix.cursedchests.api.block.OldChestBlock;
import ninjaphenix.cursedchests.api.block.entity.CursedChestBlockEntity;
import ninjaphenix.cursedchests.api.block.entity.OldChestBlockEntity;

public class ModBlocks
{
    public static BlockEntityType<CursedChestBlockEntity> CURSED_CHEST;
    public static BlockEntityType<OldChestBlockEntity> FULL_CURSED_CHEST;

    public static CursedChestBlock wood_chest;
    public static CursedChestBlock iron_chest;
    public static CursedChestBlock gold_chest;
    public static CursedChestBlock diamond_chest;
    public static CursedChestBlock obsidian_chest;

    public static OldChestBlock old_wood_chest;
    public static OldChestBlock old_iron_chest;
    public static OldChestBlock old_gold_chest;
    public static OldChestBlock old_diamond_chest;
    public static OldChestBlock old_obsidian_chest;

    public static void init()
    {
        // todo: move to api (wood chest)
        wood_chest = register(new CursedChestBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).build()), "wood_chest", 3,
                new TranslatableText("container.cursedchests.wood_chest"), new Identifier("minecraft", "textures/entity/chest/normal.png"),
                new Identifier("minecraft", "textures/entity/chest/normal_double.png"), new Identifier("cursedchests", "textures/entity/wood_chest/tall.png"),
                new Identifier("cursedchests", "textures/entity/wood_chest/long.png"));
        iron_chest = register(new CursedChestBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK).build()), "iron_chest", 6,
                new TranslatableText("container.cursedchests.iron_chest"), new Identifier("cursedchests", "textures/entity/iron_chest/single.png"),
                new Identifier("cursedchests", "textures/entity/iron_chest/vanilla.png"), new Identifier("cursedchests", "textures/entity/iron_chest/tall.png"),
                new Identifier("cursedchests", "textures/entity/iron_chest/long.png"));
        gold_chest = register(new CursedChestBlock(FabricBlockSettings.copy(Blocks.GOLD_BLOCK).build()), "gold_chest", 9,
                new TranslatableText("container.cursedchests.gold_chest"), new Identifier("cursedchests", "textures/entity/gold_chest/single.png"),
                new Identifier("cursedchests", "textures/entity/gold_chest/vanilla.png"), new Identifier("cursedchests", "textures/entity/gold_chest/tall.png"),
                new Identifier("cursedchests", "textures/entity/gold_chest/long.png"));
        diamond_chest = register(new CursedChestBlock(FabricBlockSettings.copy(Blocks.DIAMOND_BLOCK).build()), "diamond_chest", 12,
                new TranslatableText("container.cursedchests.diamond_chest"), new Identifier("cursedchests", "textures/entity/diamond_chest/single.png"),
                new Identifier("cursedchests", "textures/entity/diamond_chest/vanilla.png"),
                new Identifier("cursedchests", "textures/entity/diamond_chest/tall.png"),
                new Identifier("cursedchests", "textures/entity/diamond_chest/long.png"));
        obsidian_chest = register(new CursedChestBlock(FabricBlockSettings.copy(Blocks.OBSIDIAN).build()), "obsidian_chest", 12,
                new TranslatableText("container.cursedchests.obsidian_chest"), new Identifier("cursedchests", "textures/entity/obsidian_chest/single.png"),
                new Identifier("cursedchests", "textures/entity/obsidian_chest/vanilla.png"),
                new Identifier("cursedchests", "textures/entity/obsidian_chest/tall.png"),
                new Identifier("cursedchests", "textures/entity/obsidian_chest/long.png"));


        old_wood_chest = registerOld(new OldChestBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).build()), "wood_chest", 3,
                new TranslatableText("container.cursedchests.wood_chest"), new Identifier("cursedchests", "textures/entity/old_wood_chest/single.png"),
                new Identifier("minecraft", "textures/entity/chest/normal_double.png"), new Identifier("cursedchests", "textures/entity/wood_chest/tall.png"),
                new Identifier("cursedchests", "textures/entity/wood_chest/long.png"));
        // todo: move to api
        CURSED_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "cursed_chest"),
                BlockEntityType.Builder.create(CursedChestBlockEntity::new, wood_chest, iron_chest, gold_chest, diamond_chest, obsidian_chest).build(null));

        FULL_CURSED_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "old_cursed_chest"),
                //BlockEntityType.Builder.create(OldChestBlockEntity::new, old_wood_chest, old_iron_chest, old_gold_chest, old_diamond_chest, old_obsidian_chest)
                BlockEntityType.Builder.create(OldChestBlockEntity::new, old_wood_chest)
                                       .build(null));
    }

    private static OldChestBlock registerOld(OldChestBlock block, String name, int rows, TranslatableText containerName, Identifier singleTexture,
            Identifier vanillaTexture, Identifier tallTexture, Identifier longTexture)
    {
        Identifier id = new Identifier("cursedchests", "old_" + name);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registries.OLD
                .add(new Identifier("cursedchests", name), new Registries.TierData(rows * 9, containerName, id, singleTexture, vanillaTexture, tallTexture,
                        longTexture));
        return block;
    }

    private static CursedChestBlock register(CursedChestBlock block, String name, int rows, TranslatableText containerName, Identifier singleTexture,
            Identifier vanillaTexture, Identifier tallTexture, Identifier longTexture)
    {
        Identifier id = new Identifier("cursedchests", name);
        Registry.register(Registry.BLOCK, id, block);
        Registry.register(Registry.ITEM, id, new BlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registries.REGULAR.add(id, new Registries.TierData(rows * 9, containerName, id, singleTexture, vanillaTexture, tallTexture, longTexture));
        return block;
    }
}
