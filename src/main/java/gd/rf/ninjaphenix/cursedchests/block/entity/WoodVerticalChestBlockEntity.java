package gd.rf.ninjaphenix.cursedchests.block.entity;

import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.block.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class WoodVerticalChestBlockEntity extends VerticalChestBlockEntity
{
	public WoodVerticalChestBlockEntity(){ super(ModBlocks.WOOD_VERTICAL_CHEST); }

	@Override protected Component getContainerName(){ return new TranslatableComponent("container.cursedchests.wood_chest"); }
	@Override public int getInvSize(){ return 27; }

	@Override public Identifier getTexture(boolean isDouble)
	{
		if (isDouble) return new Identifier("cursedchests", "textures/entity/wood_chest/double.png");
		return new Identifier("minecraft", "textures/entity/chest/normal.png");
	}
}
