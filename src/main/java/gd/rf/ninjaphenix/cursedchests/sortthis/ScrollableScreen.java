package gd.rf.ninjaphenix.cursedchests.sortthis;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerProvider;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ScrollableScreen extends ContainerScreen<ScrollableContainer> implements ContainerProvider<ScrollableContainer>
{
	private static final Identifier BASE_TEXTURE = new Identifier("textures/gui/container/generic_54.png");
	private static final Identifier SCROLL_TEXTURE = new Identifier("cursedchests", "textures/screen/container/scroll.png");
	private int topRow;
	private final int rows;
	private final int realRows;

	public static ScrollableScreen createScreen(ScrollableContainer container)
	{
		return new ScrollableScreen(container, MinecraftClient.getInstance().player.inventory, container.getDisplayName());
	}

	public ScrollableScreen(ScrollableContainer container, PlayerInventory playerInventory, TextComponent containerTitle)
	{
		super(container, playerInventory, containerTitle);
		this.realRows = container.getRows();
		this.topRow = realRows-6;
		this.rows = realRows > 6 ? 6 : realRows;
		this.height = 114 + this.rows * 18;
	}

	@Override public void render(int int_1, int int_2, float float_1)
	{
		this.drawBackground();
		super.render(int_1, int_2, float_1);
		this.drawMouseoverTooltip(int_1, int_2);
	}

	@Override protected void drawForeground(int int_1, int int_2)
	{
		this.fontRenderer.draw(this.name.getFormattedText(), 8.0F, 6.0F, 4210752);
		this.fontRenderer.draw(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.height - 96 + 2), 4210752);
	}

	@Override protected void drawBackground(float float_1, int int_1, int int_2)
	{
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.client.getTextureManager().bindTexture(BASE_TEXTURE);
		int int_3 = (this.screenWidth - this.width) / 2;
		int int_4 = (this.screenHeight - this.height) / 2;
		this.drawTexturedRect(int_3, int_4, 0, 0, this.width, this.rows * 18 + 17);
		this.drawTexturedRect(int_3, int_4 + this.rows * 18 + 17, 0, 126, this.width, 96);
		if(realRows > 6)
		{
			this.client.getTextureManager().bindTexture(SCROLL_TEXTURE);
			this.drawTexturedRect(int_3+172, int_4, 0, 0, 22, 132);
			this.drawTexturedRect(int_3+174, (int) (int_4+18 + 91*((double) topRow / (double) (realRows-5))), 22, 0, 12, 15);
		}
	}

	@Override protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int mouseButton)
	{
		boolean left_up_down = mouseX < left || mouseY < top || mouseY > top + this.height;
		boolean right = mouseX > left + this.width;
		if(realRows > 6) { right = (right && mouseY > top + 132) || mouseX > left + this.width + 18; }
		return left_up_down || right;
	}
}

