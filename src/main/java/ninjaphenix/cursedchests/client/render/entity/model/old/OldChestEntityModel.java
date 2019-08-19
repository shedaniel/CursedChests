package ninjaphenix.cursedchests.client.render.entity.model.old;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.ChestEntityModel;

public class OldChestEntityModel extends ChestEntityModel
{
    public OldChestEntityModel()
    {
        this.lid = null;
        this.hatch = null;
        this.base = new Cuboid(this, 0, 0).setTextureSize(64, 32);
        this.base.addBox(0.0F, 0.0F, 0.0F, 16, 16, 16, 0.0F);
    }

    @Override
    public void method_2799() { this.base.render(0.0625F); }
}
