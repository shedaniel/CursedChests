package ninjaphenix.cursedchests.api.client.gui.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.client.gui.screen.ingame.ContainerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import ninjaphenix.cursedchests.api.container.GenericContainer;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class PagedScreen extends AbstractContainerScreen<GenericContainer> implements ContainerProvider<GenericContainer>
{
    private static HashMap<Integer, ScreenSize> containerSizes;

    static
    {
        // todo: move into api so modders can declare more variants
        containerSizes = new HashMap<>();
        //Slots -> Pages, Width, Height, Blanked Slots
        containerSizes.put( 27, new ScreenSize(27, 1, 9, 3, new Identifier("minecraft", "textures/gui/container/shulker_box.png")));
        containerSizes.put( 54, new ScreenSize(54, 1, 9, 6, new Identifier("minecraft", "textures/gui/container/generic_54.png")));
        containerSizes.put( 81, new ScreenSize(81, 1, 12, 7, new Identifier("cursedchests", "textures/gui/container/12x7.png")));
        containerSizes.put(108, new ScreenSize(108, 2, 9, 6,  new Identifier("minecraft", "textures/gui/container/generic_54.png")));
        containerSizes.put(135, new ScreenSize(135, 2, 10, 7, new Identifier("cursedchests", "textures/gui/container/10x7.png")));
        containerSizes.put(162, new ScreenSize(162, 2, 12, 7, new Identifier("cursedchests", "textures/gui/container/12x7.png")));
        containerSizes.put(189, new ScreenSize(189, 3, 9, 7, new Identifier("cursedchests", "textures/gui/container/9x7.png")));
        containerSizes.put(216, new ScreenSize(216, 3, 12, 6, new Identifier("cursedchests", "textures/gui/container/12x6.png")));
    }

    private final Identifier TEXTURE;

    public PagedScreen(GenericContainer container, PlayerInventory inventory, Text name)
    {
        super(container, inventory, name);
        TEXTURE = containerSizes.get(container.getInventory().getInvSize()).texture;
    }

    @Override
    protected void drawBackground(float v, int i, int i1)
    {

    }


    public static class ScreenSize
    {
        public final int pages;
        public final int width;
        public final int height;
        public final int blankSlots;
        public final Identifier texture;

        public ScreenSize(int slots, int pages, int width, int height, Identifier texture)
        {
            this.pages = pages;
            this.width = width;
            this.height = height;
            this.texture = texture;
            this.blankSlots = slots - (pages * width * height);
        }
    }
}