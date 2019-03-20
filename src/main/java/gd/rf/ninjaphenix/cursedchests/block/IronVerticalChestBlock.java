package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.block.entity.IronVerticalChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class IronVerticalChestBlock extends VerticalChestBlock
{
	IronVerticalChestBlock(Settings blockSettings) { super(blockSettings, "iron_chest"); }
	@Override public BlockEntity createBlockEntity(BlockView blockView) { return new IronVerticalChestBlockEntity(); }
}
