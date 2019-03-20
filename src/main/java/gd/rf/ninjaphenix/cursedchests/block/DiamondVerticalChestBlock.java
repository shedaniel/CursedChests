package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.block.entity.DiamondVerticalChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class DiamondVerticalChestBlock extends VerticalChestBlock
{
	DiamondVerticalChestBlock(Settings blockSettings) { super(blockSettings, "diamond_chest"); }
	@Override public BlockEntity createBlockEntity(BlockView blockView) { return new DiamondVerticalChestBlockEntity(); }
}
