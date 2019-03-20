package gd.rf.ninjaphenix.cursedchests.mixins;

import gd.rf.ninjaphenix.cursedchests.block.DiamondVerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.GoldVerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.IronVerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.WoodVerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.block.entity.*;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.block.BlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.item.ItemDynamicRenderer.class)
public class ItemDynamicRenderer
{
	private VerticalChestBlockEntity WOOD_VERTICAL_CHEST = new WoodVerticalChestBlockEntity();
	private VerticalChestBlockEntity IRON_VERTICAL_CHEST = new IronVerticalChestBlockEntity();
	private VerticalChestBlockEntity GOLD_VERTICAL_CHEST = new GoldVerticalChestBlockEntity();
	private VerticalChestBlockEntity DIAMOND_VERTICAL_CHEST = new DiamondVerticalChestBlockEntity();

	@Inject(at = @At("HEAD"), method="render", cancellable=true)
	private void render(ItemStack itemStack, CallbackInfo info)
	{
		Item item = itemStack.getItem();
		if(item instanceof BlockItem)
		{
			Block block = ((BlockItem) item).getBlock();
			if(block instanceof WoodVerticalChestBlock)
			{
				BlockEntityRenderDispatcher.INSTANCE.renderEntity(WOOD_VERTICAL_CHEST); info.cancel();
			}
			else if(block instanceof IronVerticalChestBlock)
			{
				BlockEntityRenderDispatcher.INSTANCE.renderEntity(IRON_VERTICAL_CHEST); info.cancel();
			}
			else if(block instanceof GoldVerticalChestBlock)
			{
				BlockEntityRenderDispatcher.INSTANCE.renderEntity(GOLD_VERTICAL_CHEST); info.cancel();
			}
			else if(block instanceof DiamondVerticalChestBlock)
			{
				BlockEntityRenderDispatcher.INSTANCE.renderEntity(DIAMOND_VERTICAL_CHEST); info.cancel();
			}
		}

	}
}
