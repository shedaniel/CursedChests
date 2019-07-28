package gd.rf.ninjaphenix.cursedchests.block.entity;

import gd.rf.ninjaphenix.cursedchests.CursedChests;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class IronVerticalChestBlockEntity extends VerticalChestBlockEntity
{
    public IronVerticalChestBlockEntity() { super(CursedChests.IRON_VERTICAL_CHEST); }

    @Override
    protected Component getContainerName() { return new TranslatableComponent("container.cursedchests.iron_chest"); }

    @Override
    public int getInvSize() { return 54; }

    @Override
    public Identifier getTexture(boolean isDouble)
    {
        if (isDouble) return new Identifier("cursedchests", "textures/entity/iron_chest/double.png");
        return new Identifier("cursedchests", "textures/entity/iron_chest/single.png");
    }
}
