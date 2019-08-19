package ninjaphenix.cursedchests.client.render.block.entity;

import net.minecraft.client.render.entity.model.LargeChestEntityModel;
import ninjaphenix.cursedchests.api.block.entity.OldChestBlockEntity;
import ninjaphenix.cursedchests.block.ModBlocks;
import ninjaphenix.cursedchests.client.render.entity.model.cursed.LongChestEntityModel;
import ninjaphenix.cursedchests.client.render.entity.model.cursed.TallChestEntityModel;
import ninjaphenix.cursedchests.client.render.entity.model.old.OldChestEntityModel;

public class OldChestBlockEntityRenderer extends AbstractChestBlockEntityRenderer<OldChestBlockEntity>
{
    public OldChestBlockEntityRenderer()
    {
        super(new OldChestEntityModel(), new LargeChestEntityModel(), new TallChestEntityModel(), new LongChestEntityModel(), ModBlocks.old_wood_chest);
    }
}
