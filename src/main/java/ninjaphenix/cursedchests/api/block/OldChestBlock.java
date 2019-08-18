package ninjaphenix.cursedchests.api.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.BlockView;
import ninjaphenix.cursedchests.api.Registries;
import ninjaphenix.cursedchests.api.block.entity.OldChestBlockEntity;

public class OldChestBlock extends BaseChestBlock
{
    public OldChestBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view)
    {
        Identifier blockId = Registry.BLOCK.getId(this);
        return new OldChestBlockEntity(new Identifier(blockId.getNamespace(), blockId.getPath().substring(4)));
    }

    @Override
    public SimpleRegistry<? extends Registries.Base> getRegistry()
    {
        return Registries.OLD;
    }
}
