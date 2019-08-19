package ninjaphenix.cursedchests.client.render.entity.model.old;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.ChestEntityModel;

public class OldLongChestEntityModel extends ChestEntityModel
{
    public OldLongChestEntityModel()
    {
        lid = null;
        hatch = null;
        base = new Cuboid(this, 0, 0).setTextureSize(96, 48);
        base.addBox(0, 0, 0, 16, 16, 32, 0);
    }

    @Override
    public void method_2799() { base.render(0.0625F); }
}
