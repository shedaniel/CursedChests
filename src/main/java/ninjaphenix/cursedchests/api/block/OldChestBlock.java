package ninjaphenix.cursedchests.api.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import ninjaphenix.cursedchests.api.block.entity.OldChestBlockEntity;

public class OldChestBlock extends BaseChestBlock
{

    public OldChestBlock(Settings settings) { super(settings); }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) { return new OldChestBlockEntity(Registry.BLOCK.getId(this)); }
}
