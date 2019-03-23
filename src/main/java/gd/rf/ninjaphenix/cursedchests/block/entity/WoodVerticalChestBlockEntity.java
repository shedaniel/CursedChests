package gd.rf.ninjaphenix.cursedchests.block.entity;

import gd.rf.ninjaphenix.cursedchests.CursedChests;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Identifier;

public class WoodVerticalChestBlockEntity extends VerticalChestBlockEntity
{
	public WoodVerticalChestBlockEntity() { super(CursedChests.WOOD_VERTICAL_CHEST); }

	@Override protected TextComponent getContainerName() { return new TranslatableTextComponent("container.cursedchests.wood_chest"); }
	@Override public int getInvSize() { return 27; }

	@Override public Identifier getTexture(boolean isDouble)
	{
		if(isDouble) return new Identifier("cursedchests", "textures/entity/wood_chest/double.png");
		return new Identifier("minecraft", "textures/entity/chest/normal.png");
	}
}
