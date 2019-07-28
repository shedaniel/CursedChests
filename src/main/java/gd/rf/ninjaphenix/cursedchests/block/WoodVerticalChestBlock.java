package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.entity.WoodVerticalChestBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class WoodVerticalChestBlock extends VerticalChestBlock
{
    WoodVerticalChestBlock() { super(FabricBlockSettings.copy(Blocks.OAK_PLANKS).build()); }

    @Override
    public BlockEntity createBlockEntity(BlockView view) { return new WoodVerticalChestBlockEntity(); }

    @Override
    public String getName() { return "wood_chest"; }
}
