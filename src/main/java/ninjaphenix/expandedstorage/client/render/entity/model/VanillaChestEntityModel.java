package ninjaphenix.expandedstorage.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VanillaChestEntityModel extends SingleChestEntityModel
{
    public VanillaChestEntityModel()
    {
        super(96, 48);
        lid.addCuboid(0, -5, -14, 30, 5, 14, 0);
        lid.addCuboid(14, -2, -15, 2, 4, 1, 0);
        lid.setRotationPoint(1, 7, 15);
        base.addCuboid(0, 0, 0, 30, 10, 14, 0);
        base.setRotationPoint(1, 6, 1);
    }
}