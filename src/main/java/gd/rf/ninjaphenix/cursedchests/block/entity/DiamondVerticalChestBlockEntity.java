package gd.rf.ninjaphenix.cursedchests.block.entity;

import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.block.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class DiamondVerticalChestBlockEntity extends VerticalChestBlockEntity
{
	public DiamondVerticalChestBlockEntity(){ super(ModBlocks.DIAMOND_VERTICAL_CHEST); }

	@Override protected Component getContainerName(){ return new TranslatableComponent("container.cursedchests.diamond_chest"); }
	@Override public int getInvSize(){ return 108; }

	@Override public Identifier getTexture(boolean isDouble)
	{
		if (isDouble) return new Identifier("cursedchests", "textures/entity/diamond_chest/double.png");
		return new Identifier("cursedchests", "textures/entity/diamond_chest/single.png");
	}
}
