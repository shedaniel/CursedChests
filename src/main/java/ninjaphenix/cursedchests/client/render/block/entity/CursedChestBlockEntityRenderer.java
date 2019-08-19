package ninjaphenix.cursedchests.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.ChestEntityModel;
import net.minecraft.client.render.entity.model.LargeChestEntityModel;
import ninjaphenix.cursedchests.api.block.entity.CursedChestBlockEntity;
import ninjaphenix.cursedchests.block.ModBlocks;
import ninjaphenix.cursedchests.client.render.entity.model.cursed.LongChestEntityModel;
import ninjaphenix.cursedchests.client.render.entity.model.cursed.TallChestEntityModel;

@Environment(EnvType.CLIENT)
public class CursedChestBlockEntityRenderer extends AbstractChestBlockEntityRenderer<CursedChestBlockEntity>
{
    public CursedChestBlockEntityRenderer()
    {
        super(new ChestEntityModel(), new LargeChestEntityModel(), new TallChestEntityModel(), new LongChestEntityModel(), ModBlocks.wood_chest);
    }

    @Override
    protected void setLidPitch(CursedChestBlockEntity blockEntity, float lidPitch, ChestEntityModel model)
    {
        float newPitch = 1.0F - blockEntity.getAnimationProgress(lidPitch);
        model.method_2798().pitch = -((1.0F - newPitch * newPitch * newPitch) * 1.5707964F);
    }
}