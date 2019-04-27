package gd.rf.ninjaphenix.cursedchests.api.client.gui.container;

import com.mojang.blaze3d.platform.GlStateManager;
import gd.rf.ninjaphenix.cursedchests.api.container.ScrollableContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerProvider;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT) public class ScrollableScreen extends ContainerScreen<ScrollableContainer> implements ContainerProvider<ScrollableContainer>
{
	private static final Identifier BASE_TEXTURE = new Identifier("textures/gui/container/generic_54.png");
	private static final Identifier SCROLL_TEXTURE = new Identifier("cursedchests", "textures/gui/container/scroll.png");
	private int topRow;
	private final int rows;
	private final int realRows;
	private double progress;
	private boolean dragging;
	private TextFieldWidget searchBox;

	public ScrollableScreen(ScrollableContainer container, PlayerInventory playerInventory, TextComponent containerTitle)
	{
		super(container, playerInventory, containerTitle);
		realRows = container.getRows();
		topRow = 0;
		rows = realRows > 6 ? 6 : realRows;
		containerHeight = 114 + rows * 18;
		progress = 0;
		container.setSearchTerm("");
	}

	@Override public void init()
	{
		super.init();
		searchBox = new TextFieldWidget(font, left + 82, top + 127, 80, 8, "");
		searchBox.setMaxLength(50);
		searchBox.setHasBorder(false);
		searchBox.setVisible(realRows > 6);
		searchBox.method_1868(16777215);
		children.add(searchBox);
	}
	public static ScrollableScreen createScreen(ScrollableContainer container){ return new ScrollableScreen(container, MinecraftClient.getInstance().player.inventory, container.getDisplayName()); }
	@Override public void tick(){ searchBox.tick(); }

	@Override public void render(int mouseX, int mouseY, float float_1)
	{
		renderBackground();
		drawBackground(float_1, mouseX, mouseY);
		super.render(mouseX, mouseY, float_1);
		drawMouseoverTooltip(mouseX, mouseY);
	}

	@Override protected void drawForeground(int int_1, int int_2)
	{
		font.draw(title.getFormattedText(), 8, 6, 4210752);
		font.draw(playerInventory.getDisplayName().getFormattedText(), 8, containerHeight - 94, 4210752);
	}

	@Override protected void drawBackground(float float_1, int int_1, int int_2)
	{
		GlStateManager.color4f(1, 1, 1, 1);
		minecraft.getTextureManager().bindTexture(BASE_TEXTURE);
		int int_3 = (width - containerWidth) / 2;
		int int_4 = (height - containerHeight) / 2;
		blit(int_3, int_4, 0, 0, containerWidth, rows * 18 + 17);
		blit(int_3, int_4 + rows * 18 + 17, 0, 126, containerWidth, 96);
		if (realRows > 6)
		{
			minecraft.getTextureManager().bindTexture(SCROLL_TEXTURE);
			blit(int_3 + 172, int_4, 0, 0, 22, 132);
			blit(int_3 + 174, (int) (int_4+18 + 91*progress), 22, 0, 12, 15);
			blit(int_3 + 79, int_4 + 126, 34, 0, 90, 11);
			searchBox.render(int_1, int_2, float_1);
		}
	}

	@Override public boolean mouseScrolled(double mouseX, double mouseY, double scrollDelta)
	{
		if (realRows > 6){ setTopRow(topRow - (int) scrollDelta); return true; }
		return false;
	}

	@Override protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int mouseButton)
	{
		boolean left_up_down = mouseX < left || mouseY < top || mouseY > top + height;
		boolean right = mouseX > left + width;
		if (realRows > 6) right = (right && mouseY > top + 132) || mouseX > left + width + 18;
		return left_up_down || right;
	}

	@Override public boolean mouseDragged(double mouseX, double mouseY, int mouseButton, double deltaX, double deltaY)
	{
		boolean inYRange = mouseY > top + 18 && mouseY < top + 124;
		boolean condition = dragging&& inYRange;
		if (!condition) condition = playerInventory.getCursorStack().isEmpty() && mouseX > left + 172 && mouseX < left + 184 && inYRange;
		if (condition)
		{
			if (!dragging) dragging = true;
			progress = (mouseY - top - 18)/105;
			topRow = (int) (progress * (realRows-6));
			container.updateSlotPositions(topRow, false);
			return true;
		}
		return super.mouseDragged(mouseX, mouseY, mouseButton, deltaX, deltaY);
	}

	@Override public boolean mouseReleased(double double_1, double double_2, int int_1)
	{
		if (dragging) dragging = false;
		return super.mouseReleased(double_1, double_2, int_1);
	}

	private void setTopRow(int value)
	{
		topRow = value;
		if (topRow < 0) topRow = 0;
		else if (topRow > realRows - 6) topRow = realRows - 6;
		progress = ((double) topRow) / ((double) (realRows - 6));
		container.updateSlotPositions(topRow, false);
	}

	@Override public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if (keyCode == 256){ minecraft.player.closeContainer(); return true;}
		if (realRows > 6 && searchBox.isFocused())
		{
			String originalText = searchBox.getText();
			if (searchBox.keyPressed(keyCode, scanCode, modifiers))
			{
				if (!originalText.equals(searchBox.getText()))
				{
					progress = 0;
					topRow = 0;
					container.setSearchTerm(searchBox.getText());
				}
			}
			return true;
		}
		if (minecraft.options.keyInventory.matchesKey(keyCode, scanCode)){ minecraft.player.closeContainer(); return true; }
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override public boolean charTyped(char character, int int_1)
	{
		if (!searchBox.isFocused() && minecraft.options.keyChat.getName().contains((String.valueOf(character)))){ searchBox.changeFocus(true); return true;}
		if (!searchBox.isFocused()) return false;
		String originalText = searchBox.getText();
		if (searchBox.charTyped(character, int_1))
		{
			if (!originalText.equals(searchBox.getText()))
			{
				progress = 0;
				topRow = 0;
				container.setSearchTerm(searchBox.getText());
			}
			return true;
		}
		return false;
	}

	@Override
	public void resize(MinecraftClient client, int int_1, int int_2)
	{
		String text = searchBox.getText();
		super.resize(client, int_1, int_2);
		searchBox.setText(text);
	}
}

