package ninjaphenix.cursedchests.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Cuboid;
import net.minecraft.client.model.Model;

@Environment(EnvType.CLIENT)
public class SingleChestEntityModel extends Model
{
    protected Cuboid lid;
    protected Cuboid hatch;
    protected Cuboid base;

    public SingleChestEntityModel(int textureWidth, int textureHeight)
    {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        lid = new Cuboid(this, 0, 0);
        hatch = new Cuboid(this, 0, 0);
        base = new Cuboid(this, 0, 19);
    }

    public SingleChestEntityModel()
    {
        this(64, 64);
        lid.addBox(0, -5, -14, 14, 5, 14, 0);
        lid.setRotationPoint(1, 7, 15);
        hatch.addBox(-1, -2, -15, 2, 4, 1, 0);
        hatch.setRotationPoint(8, 7, 15);
        base.addBox(0, 0, 0, 14, 10, 14, 0);
        base.setRotationPoint(1, 6, 1);
    }

    public void render()
    {
        hatch.pitch = lid.pitch;
        lid.render(0.0625F);
        hatch.render(0.0625F);
        base.render(0.0625F);
    }

    public Cuboid getLid() { return lid; }
}