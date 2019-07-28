package gd.rf.ninjaphenix.cursedchests.block;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.entity.ObsidianVerticalChestBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ObsidianVerticalChestBlock extends VerticalChestBlock
{
    ObsidianVerticalChestBlock() { super(FabricBlockSettings.copy(Blocks.OBSIDIAN).build()); }

    @Override
    public BlockEntity createBlockEntity(BlockView view) { return new ObsidianVerticalChestBlockEntity(); }

    @Override
    public String getName() { return "obsidian_chest"; }
}
