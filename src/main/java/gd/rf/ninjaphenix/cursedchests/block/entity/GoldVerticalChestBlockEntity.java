package gd.rf.ninjaphenix.cursedchests.block.entity;

import gd.rf.ninjaphenix.cursedchests.CursedChests;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class GoldVerticalChestBlockEntity extends VerticalChestBlockEntity
{
	public GoldVerticalChestBlockEntity(){ super(CursedChests.GOLD_VERTICAL_CHEST); }

	@Override protected Component getContainerName(){ return new TranslatableComponent("container.cursedchests.gold_chest"); }
	@Override public int getInvSize(){ return 81; }

	@Override public Identifier getTexture(boolean isDouble)
	{
		if (isDouble) return new Identifier("cursedchests", "textures/entity/gold_chest/double.png");
		return new Identifier("cursedchests", "textures/entity/gold_chest/single.png");
	}
}
