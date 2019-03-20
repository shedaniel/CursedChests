package gd.rf.ninjaphenix.cursedchests.client.render.block.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import gd.rf.ninjaphenix.cursedchests.block.ModBlocks;
import gd.rf.ninjaphenix.cursedchests.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.entity.VerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.client.render.entity.model.ChestDoubleEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.ChestEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class VerticalChestBlockEntityRenderer extends BlockEntityRenderer<VerticalChestBlockEntity>
{
	private final ChestEntityModel modelSingleChest = new ChestEntityModel();
	private final ChestEntityModel modelDoubleChest = new ChestDoubleEntityModel();

	@Override public void render(VerticalChestBlockEntity blockEntity, double double_1, double double_2, double double_3, float float_1, int int_1)
	{
		GlStateManager.enableDepthTest();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		BlockState state = blockEntity.hasWorld() ? blockEntity.getCachedState() : ModBlocks.wooden_chest.getDefaultState().with(VerticalChestBlock.FACING, Direction.SOUTH).with(VerticalChestBlock.TYPE, VerticalChestBlock.VerticalChestType.SINGLE);
		VerticalChestBlock.VerticalChestType chestType = state.get(VerticalChestBlock.TYPE);
		boolean isDouble = chestType != VerticalChestBlock.VerticalChestType.SINGLE;
		if(chestType == VerticalChestBlock.VerticalChestType.TOP && int_1  < 0) return;
		ChestEntityModel chestEntityModel_1 = this.method_3562(blockEntity, int_1, isDouble);
		if (int_1 >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scalef(4.0F, isDouble ? 8.0F : 4.0F, 1.0F);
			GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		} else {
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translatef((float)double_1, (float)double_2 + 1.0F, (float)double_3 + 1.0F);
		GlStateManager.scalef(1.0F, -1.0F, -1.0F);
		float float_2 = state.get(ChestBlock.FACING).asRotation();
		if ((double)Math.abs(float_2) > 1.0E-5D) {
			GlStateManager.translatef(0.5F, 0.5F, 0.5F);
			GlStateManager.rotatef(float_2, 0.0F, 1.0F, 0.0F);
			GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
		}
		if(chestType == VerticalChestBlock.VerticalChestType.TOP) GlStateManager.translatef(0, 1, 0);
		this.method_3561(blockEntity, float_1, chestEntityModel_1);
		chestEntityModel_1.method_2799();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		if (int_1 >= 0) {
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}

	private ChestEntityModel method_3562(VerticalChestBlockEntity blockEntity, int breaking_stage, boolean isDouble)
	{
		Identifier identifier_5;
		if (breaking_stage >= 0)
		{
			identifier_5 = DESTROY_STAGE_TEXTURES[breaking_stage];
		}
		else
		{
			identifier_5 = blockEntity.getTexture(isDouble);
		}

		this.bindTexture(identifier_5);
		return isDouble ? this.modelDoubleChest : this.modelSingleChest;
	}

	private void method_3561(VerticalChestBlockEntity blockEntity_1, float float_1, ChestEntityModel chestEntityModel_1)
	{
		float float_2 = (blockEntity_1).getAnimationProgress(float_1);
		float_2 = 1.0F - float_2;
		float_2 = 1.0F - float_2 * float_2 * float_2;
		chestEntityModel_1.method_2798().pitch = -(float_2 * 1.5707964F);
	}
}