package gd.rf.ninjaphenix.cursedchests.block.entity;

import gd.rf.ninjaphenix.cursedchests.CursedChests;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Identifier;

public class IronVerticalChestBlockEntity extends VerticalChestBlockEntity
{
	public IronVerticalChestBlockEntity() { super(CursedChests.IRON_VERTICAL_CHEST); }

	@Override protected TextComponent getContainerName() { return new TranslatableTextComponent("container.cursedchests.iron_chest"); }
	@Override public int getInvSize() { return 54; }

	@Override public Identifier getTexture(boolean isDouble)
	{
		if(isDouble) return new Identifier("cursedchests", "textures/entity/iron_chest/double.png");
		return new Identifier("cursedchests", "textures/entity/iron_chest/single.png");
	}
}
