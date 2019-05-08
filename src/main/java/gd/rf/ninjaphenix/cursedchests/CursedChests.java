package gd.rf.ninjaphenix.cursedchests;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestType;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import gd.rf.ninjaphenix.cursedchests.api.client.gui.container.ScrollableScreen;
import gd.rf.ninjaphenix.cursedchests.api.container.ScrollableContainer;
import gd.rf.ninjaphenix.cursedchests.api.item.ChestModifier;
import gd.rf.ninjaphenix.cursedchests.block.ModBlocks;
import gd.rf.ninjaphenix.cursedchests.client.render.block.entity.VerticalChestBlockEntityRenderer;
import gd.rf.ninjaphenix.cursedchests.item.ModItems;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@EnvironmentInterface(itf=ClientModInitializer.class, value=EnvType.CLIENT)
public class CursedChests implements ModInitializer, ClientModInitializer
{
	@Override public void onInitialize()
	{
		ModBlocks.init();
		ModItems.init();

		ContainerProviderRegistry.INSTANCE.registerFactory(new Identifier("cursedchests", "scrollcontainer"), ((syncId, identifier, player, buf) ->
		{
			BlockPos pos = buf.readBlockPos();
			Component containerName = buf.readTextComponent();
			World world = player.getEntityWorld();
			return new ScrollableContainer(syncId, player.inventory, VerticalChestBlock.getInventoryStatic(world.getBlockState(pos), world, pos), containerName);
		}));

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
		{
			Item handItem = player.getStackInHand(hand).getItem();
			if (handItem instanceof ChestModifier) return ((ChestModifier) handItem).useOnEntity(world, player, hand, entity, hitResult);
			return ActionResult.PASS;
		});

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
		{
			Item handItem = player.getStackInHand(hand).getItem();
			if (handItem instanceof ChestModifier)
			{
				BlockPos hitBlockPos = hitResult.getBlockPos();
				if (world.getBlockState(hitBlockPos).getBlock() instanceof VerticalChestBlock)
				{
					BlockState usedChestBlockState = world.getBlockState(hitBlockPos);
					VerticalChestType type = usedChestBlockState.get(VerticalChestBlock.TYPE);
					if (type == VerticalChestType.SINGLE) return ((ChestModifier) handItem).useOnChest(world, player, hand, hitResult, hitBlockPos, null);
					else if (type == VerticalChestType.TOP) return ((ChestModifier) handItem).useOnChest(world, player, hand, hitResult, hitBlockPos.offset(Direction.DOWN), hitBlockPos);
					else if (type == VerticalChestType.BOTTOM) return ((ChestModifier) handItem).useOnChest(world, player, hand, hitResult, hitBlockPos, hitBlockPos.offset(Direction.UP));
				}
			}
			return ActionResult.PASS;
		});
	}

	@Environment(EnvType.CLIENT) @Override public void onInitializeClient()
	{
		BlockEntityRendererRegistry.INSTANCE.register(VerticalChestBlockEntity.class, new VerticalChestBlockEntityRenderer());
		ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier("cursedchests", "scrollcontainer"), ScrollableScreen::createScreen);
	}
}
