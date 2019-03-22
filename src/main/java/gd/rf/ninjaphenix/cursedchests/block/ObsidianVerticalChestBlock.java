package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.block.entity.ObsidianVerticalChestBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ObsidianVerticalChestBlock extends VerticalChestBlock
{
	ObsidianVerticalChestBlock() { super(FabricBlockSettings.copy(Blocks.OBSIDIAN).build(), "obsidian_chest"); }
	@Override public BlockEntity createBlockEntity(BlockView blockView) { return new ObsidianVerticalChestBlockEntity(); }
}
