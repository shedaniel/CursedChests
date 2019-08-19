package ninjaphenix.cursedchests.client.render.block.entity;

import ninjaphenix.cursedchests.api.block.entity.OldChestBlockEntity;
import ninjaphenix.cursedchests.block.ModBlocks;
import ninjaphenix.cursedchests.client.render.entity.model.old.OldChestEntityModel;
import ninjaphenix.cursedchests.client.render.entity.model.old.OldLongChestEntityModel;
import ninjaphenix.cursedchests.client.render.entity.model.old.OldTallChestEntityModel;
import ninjaphenix.cursedchests.client.render.entity.model.old.OldVanillaChestEntityModel;

public class OldChestBlockEntityRenderer extends AbstractChestBlockEntityRenderer<OldChestBlockEntity>
{
    public OldChestBlockEntityRenderer()
    {
        super(new OldChestEntityModel(), new OldVanillaChestEntityModel(), new OldTallChestEntityModel(), new OldLongChestEntityModel(),
                ModBlocks.old_wood_chest);
    }
}
