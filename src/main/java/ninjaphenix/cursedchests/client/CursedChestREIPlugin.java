package ninjaphenix.cursedchests.client;

import me.shedaniel.math.api.Rectangle;
import me.shedaniel.rei.api.BaseBoundsHandler;
import me.shedaniel.rei.api.DisplayHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.util.version.VersionParsingException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import ninjaphenix.cursedchests.api.client.gui.container.ScrollableScreen;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class CursedChestREIPlugin implements REIPluginV0
{
    @Override
    public SemanticVersion getMinimumVersion() throws VersionParsingException
    {
        return SemanticVersion.parse("3.0-pre");
    }

    @Override
    public void registerBounds(DisplayHelper displayHelper)
    {
        BaseBoundsHandler handler = displayHelper.getBaseBoundsHandler();
        handler.registerExclusionZones(ScrollableScreen.class, isOnRightSide ->
        {
            MinecraftClient client = MinecraftClient.getInstance();
            ScrollableScreen screen = (ScrollableScreen) client.currentScreen;
            ArrayList<Rectangle> rv = new ArrayList<>(1);
            if (isOnRightSide && screen.hasScrollbar()) { rv.add(new Rectangle(screen.getLeft() + 172, screen.getTop(), 22, 132));}
            return rv;
        });
    }

    @Override
    public Identifier getPluginIdentifier() { return new Identifier("cursedchests", "reiplugin"); }
}
