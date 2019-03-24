package gd.rf.ninjaphenix.cursedchests.client.render.block.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.block.ModBlocks;
import gd.rf.ninjaphenix.cursedchests.client.render.entity.model.ChestDoubleEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.ChestEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT) public class VerticalChestBlockEntityRenderer extends BlockEntityRenderer<VerticalChestBlockEntity>
{
	private final ChestEntityModel modelSingleChest = new ChestEntityModel();
	private final ChestEntityModel modelDoubleChest = new ChestDoubleEntityModel();

	@Override public void render(VerticalChestBlockEntity blockEntity, double x, double y, double z, float lidPitch, int breaking_stage)
	{
		GlStateManager.enableDepthTest();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		BlockState state = blockEntity.hasWorld() ? blockEntity.getCachedState() : ModBlocks.wood_chest.getDefaultState().with(VerticalChestBlock.FACING, Direction.SOUTH);
		VerticalChestBlock.VerticalChestType chestType = state.get(VerticalChestBlock.TYPE);
		boolean isDouble = chestType != VerticalChestBlock.VerticalChestType.SINGLE;
		if (chestType == VerticalChestBlock.VerticalChestType.TOP && breaking_stage  < 0) return;
		ChestEntityModel chestModel = getChestModelAndBindTexture(blockEntity, breaking_stage, isDouble);
		if (breaking_stage >= 0)
		{
			GlStateManager.matrixMode(5890);
			GlStateManager.pushMatrix();
			GlStateManager.scalef(4, isDouble ? 8 : 4, 1);
			GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
			GlStateManager.matrixMode(5888);
		}
		else GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translated(x, y + 1, z + 1);
		GlStateManager.scalef(1, -1, -1);
		float chestYaw = state.get(ChestBlock.FACING).asRotation();
		if (Math.abs(chestYaw) > 1.0E-5D)
		{
			GlStateManager.translated(0.5, 0.5, 0.5);
			GlStateManager.rotatef(chestYaw, 0, 1, 0);
			GlStateManager.translated(-0.5, -0.5, -0.5);
		}
		if (chestType == VerticalChestBlock.VerticalChestType.TOP) GlStateManager.translatef(0, 1, 0);
		setLidPitch(blockEntity, lidPitch, chestModel);
		chestModel.method_2799();
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color4f(1, 1, 1, 1);
		if (breaking_stage >= 0)
		{
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}

	private ChestEntityModel getChestModelAndBindTexture(VerticalChestBlockEntity blockEntity, int breaking_stage, boolean isDouble)
	{
		Identifier identifier_5;
		if (breaking_stage >= 0) identifier_5 = DESTROY_STAGE_TEXTURES[breaking_stage];
		else identifier_5 = blockEntity.getTexture(isDouble);
		bindTexture(identifier_5);
		return isDouble ? modelDoubleChest : modelSingleChest;
	}

	private void setLidPitch(VerticalChestBlockEntity blockEntity, float lidPitch, ChestEntityModel model)
	{
		float newPitch = 1.0F - blockEntity.getAnimationProgress(lidPitch);
		model.method_2798().pitch = -((1.0F - newPitch * newPitch * newPitch) * 1.5707964F);
	}
}