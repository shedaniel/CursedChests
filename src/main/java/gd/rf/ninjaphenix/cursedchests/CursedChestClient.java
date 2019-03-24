package gd.rf.ninjaphenix.cursedchests;

import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.api.client.gui.container.ScrollableScreen;
import gd.rf.ninjaphenix.cursedchests.client.render.block.entity.VerticalChestBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT) public class CursedChestClient implements ClientModInitializer
{
	@Override public void onInitializeClient()
	{
		BlockEntityRendererRegistry.INSTANCE.register(VerticalChestBlockEntity.class, new VerticalChestBlockEntityRenderer());
		ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier("cursedchests", "scrollcontainer"), ScrollableScreen::createScreen);
	}
}
