package ninjaphenix.cursedchests.api.block;

import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.BlockView;
import ninjaphenix.cursedchests.api.Registries;
import ninjaphenix.cursedchests.api.block.entity.OldChestBlockEntity;

@SuppressWarnings("deprecation")
public class OldChestBlock extends AbstractChestBlock
{
    public OldChestBlock(Settings settings) { super(settings); }

    @Override
    public BlockEntity createBlockEntity(BlockView var1) { return new OldChestBlockEntity(getTierId()); }

    @Override
    public Identifier getTierId()
    {
        Identifier blockId = Registry.BLOCK.getId(this);
        return new Identifier(blockId.getNamespace(), blockId.getPath().substring(4));
    }

    @Override
    public SimpleRegistry<? extends Registries.TierData> getRegistry() { return Registries.OLD; }

    @Override
    public BlockRenderLayer getRenderLayer() { return BlockRenderLayer.CUTOUT; }
}
