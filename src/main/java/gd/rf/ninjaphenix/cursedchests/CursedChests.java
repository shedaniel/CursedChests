package gd.rf.ninjaphenix.cursedchests;

import gd.rf.ninjaphenix.cursedchests.block.ModBlocks;
import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.entity.*;
import gd.rf.ninjaphenix.cursedchests.api.container.ScrollableContainer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

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
	public static final BlockEntityType<ObsidianVerticalChestBlockEntity> OBSIDIAN_VERTICAL_CHEST = Registry.register(Registry.BLOCK_ENTITY, new Identifier("cursedchests", "obsidian_vertical_chest"),
			BlockEntityType.Builder.create(ObsidianVerticalChestBlockEntity::new).build(null));

	@Override public void onInitialize()
	{
		ModBlocks.init();
		ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier("cursedchests", "scrollcontainer"), ((syncId, identifier, player, buf) -> {
			BlockPos pos = buf.readBlockPos();
			TextComponent containerName = buf.readTextComponent();
			World world = player.getEntityWorld();
			return new ScrollableContainer(syncId, player.inventory, VerticalChestBlock.createCombinedInventory(world.getBlockState(pos), world, pos), containerName);
		}));
	}
}
