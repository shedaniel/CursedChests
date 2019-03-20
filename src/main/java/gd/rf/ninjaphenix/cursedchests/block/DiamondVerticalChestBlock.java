package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.block.entity.DiamondVerticalChestBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class DiamondVerticalChestBlock extends VerticalChestBlock
{
	DiamondVerticalChestBlock() { super(FabricBlockSettings.copy(Blocks.DIAMOND_BLOCK).build(), "diamond_chest"); }
	@Override public BlockEntity createBlockEntity(BlockView blockView) { return new DiamondVerticalChestBlockEntity(); }
}
