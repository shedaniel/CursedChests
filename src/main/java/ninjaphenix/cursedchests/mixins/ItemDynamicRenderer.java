package ninjaphenix.cursedchests.mixins;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ninjaphenix.cursedchests.api.block.AbstractChestBlock;
import ninjaphenix.cursedchests.api.block.CursedChestBlock;
import ninjaphenix.cursedchests.api.block.OldChestBlock;
import ninjaphenix.cursedchests.api.block.entity.CursedChestBlockEntity;
import ninjaphenix.cursedchests.api.block.entity.OldChestBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.item.ItemDynamicRenderer.class)
@Environment(EnvType.CLIENT)
public class ItemDynamicRenderer
{
    private static final CursedChestBlockEntity REGULAR_RENDER_ENTITY = new CursedChestBlockEntity();
    private static final OldChestBlockEntity OLD_RENDER_ENTITY = new OldChestBlockEntity();

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void render(ItemStack itemStack, CallbackInfo info)
    {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem)
        {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof AbstractChestBlock)
            {
                AbstractChestBlock chestBlock = (AbstractChestBlock) block;

                if (block instanceof CursedChestBlock)
                {
                    REGULAR_RENDER_ENTITY.setBlock(chestBlock.getTierId());
                    BlockEntityRenderDispatcher.INSTANCE.renderEntity(REGULAR_RENDER_ENTITY);
                    info.cancel();
                }
                else if (block instanceof OldChestBlock)
                {
                    OLD_RENDER_ENTITY.setBlock(chestBlock.getTierId());
                    BlockEntityRenderDispatcher.INSTANCE.renderEntity(OLD_RENDER_ENTITY);
                    info.cancel();
                }

            }
        }
    }
}
