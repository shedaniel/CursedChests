package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.block.entity.GoldVerticalChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class GoldVerticalChestBlock extends VerticalChestBlock
{
	GoldVerticalChestBlock(Settings block$Settings_1) { super(block$Settings_1, "gold_chest"); }
	@Override public BlockEntity createBlockEntity(BlockView blockView) { return new GoldVerticalChestBlockEntity(); }
}
