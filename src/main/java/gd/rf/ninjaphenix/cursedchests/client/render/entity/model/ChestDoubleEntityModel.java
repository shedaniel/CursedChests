package gd.rf.ninjaphenix.cursedchests.client.render.entity.model;

import net.minecraft.client.model.Cuboid;
import net.minecraft.client.render.entity.model.ChestEntityModel;

public class ChestDoubleEntityModel extends ChestEntityModel
{
	public ChestDoubleEntityModel()
	{
		this.lid = new Cuboid(this, 0, 0).setTextureSize(64, 128);
		this.lid.addBox(0.0F, -5.0F, -14.0F, 14, 5, 14, 0.0F);
		this.lid.rotationPointX = 1.0F;
		this.lid.rotationPointY = 7.0F;
		this.lid.rotationPointZ = 15.0F;
		this.lid.y -= 1; // seems to be the easiest way to transpose the top of wooden_chest
		this.hatch = new Cuboid(this, 0, 0).setTextureSize(64, 128);
		this.hatch.addBox(-1.0F, -2.0F, -15.0F, 2, 4, 1, 0.0F);
		this.hatch.rotationPointX = 8.0F;
		this.hatch.rotationPointY = 7.0F;
		this.hatch.rotationPointZ = 15.0F;
		this.hatch.y -= 1; // seems to be the easiest way to transpose the top of wooden_chest
		this.base = new Cuboid(this, 0, 19).setTextureSize(64, 128);
		this.base.addBox(0.0F, -16.0F, 0.0F, 14, 26, 14, 0.0F);
		this.base.rotationPointX = 1.0F;
		this.base.rotationPointY = 6.0F;
		this.base.rotationPointZ = 1.0F;
	}
}
