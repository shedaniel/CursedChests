package gd.rf.ninjaphenix.cursedchests;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerType<T extends Container>
{
	private final ContainerType.Factory<T> factory;
	public ContainerType(ContainerType.Factory<T> factory)
	{
		this.factory = factory;
	}

	@Environment(EnvType.CLIENT)
	public T create(int int_1, PlayerInventory playerInventory_1)
	{
		return this.factory.create(int_1, playerInventory_1);
	}

	interface Factory<T extends Container>
	{
		@Environment(EnvType.CLIENT) T create(int var1, PlayerInventory var2);
	}
}
