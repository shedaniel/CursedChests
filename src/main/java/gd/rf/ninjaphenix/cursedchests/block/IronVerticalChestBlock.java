package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.block.entity.IronVerticalChestBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class IronVerticalChestBlock extends VerticalChestBlock
{
	IronVerticalChestBlock() { super(FabricBlockSettings.copy(Blocks.IRON_BLOCK).build(), "iron_chest"); }
	@Override public BlockEntity createBlockEntity(BlockView blockView) { return new IronVerticalChestBlockEntity(); }
}
