package gd.rf.ninjaphenix.cursedchests.block.entity;

import gd.rf.ninjaphenix.cursedchests.CursedChests;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Identifier;

public class ObsidianVerticalChestBlockEntity extends VerticalChestBlockEntity
{
	public ObsidianVerticalChestBlockEntity() { super(CursedChests.OBSIDIAN_VERTICAL_CHEST); }

	@Override protected TextComponent getContainerName() { return new TranslatableTextComponent("container.cursedchests.obsidian_chest"); }
	@Override public int getInvSize() { return 108; }

	@Override public Identifier getTexture(boolean isDouble)
	{
		if(isDouble) return new Identifier("cursedchests", "textures/entity/obsidian_chest/double.png");
		return new Identifier("cursedchests", "textures/entity/obsidian_chest/single.png");
	}
}
