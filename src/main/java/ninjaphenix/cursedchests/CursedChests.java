package ninjaphenix.cursedchests;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.Bootstrap;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ninjaphenix.cursedchests.api.block.AbstractChestBlock;
import ninjaphenix.cursedchests.api.container.ScrollableContainer;
import ninjaphenix.cursedchests.block.ModBlocks;
import ninjaphenix.cursedchests.item.ModItems;

public class CursedChests implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        ModBlocks.init();
        ModItems.init();
        ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier("cursedchests", "scrollcontainer"), ((syncId, identifier, player, buf) ->
        {
            BlockPos pos = buf.readBlockPos();
            Text name = buf.readText();
            World world = player.getEntityWorld();
            return new ScrollableContainer(syncId, player.inventory, AbstractChestBlock.getInventoryStatic(world, pos), name);
        }));
    }
}
