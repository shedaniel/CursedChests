package gd.rf.ninjaphenix.cursedchests.client.render;

import gd.rf.ninjaphenix.cursedchests.api.client.gui.container.ScrollableScreen;
import me.shedaniel.rei.RoughlyEnoughItemsCore;
import me.shedaniel.rei.api.BaseBoundsHandler;
import me.shedaniel.rei.api.DisplayHelper;
import me.shedaniel.rei.api.REIPlugin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CursedChestREIPlugin implements REIPlugin
{
	public void registerBounds(DisplayHelper displayHelper)
	{
		System.out.println("Registering the bounds!");
		BaseBoundsHandler handler = displayHelper.getBaseBoundsHandler();
		handler.registerExclusionZones(ScrollableScreen.class, new BaseBoundsHandler.ExclusionZoneSupplier()
		{
			@Override public List<Rectangle> apply(boolean isOnRightSide)
			{
				MinecraftClient client = MinecraftClient.getInstance();
				ScrollableScreen screen = (ScrollableScreen) client.currentScreen;

				ArrayList<Rectangle> rv = new ArrayList<>(1);
				if(isOnRightSide){ rv.add(new Rectangle(screen.getLeft() + 172, screen.getTop(), 22, 132));}
				return rv;
			}
		});

	}

	public static void registerPlugin()
	{
		RoughlyEnoughItemsCore.registerPlugin(new Identifier("cursedchests", "screenplugin"), new CursedChestREIPlugin());
	}
}
