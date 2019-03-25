package gd.rf.ninjaphenix.cursedchests.api.item;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sortme.ChatMessageType;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * @author NinjaPhenix
 * @version 1.0.5
 * @since 1.0.5
 */
public class ChestConversionItem extends Item implements ChestModifier
{
	private Identifier from, to;

	public ChestConversionItem(Settings settings, Identifier from, Identifier to)
	{
		super(settings);
		this.from = from;
		this.to = to;
	}

	public ChestConversionItem(Settings settings, VerticalChestBlock from, VerticalChestBlock to)
	{
		super(settings);
		this.from = Registry.BLOCK.getId(from);
		this.to = Registry.BLOCK.getId(to);
	}

	/**
	 * Ignore this, this will be gone once this method is complete.
	 * This method is called automatically when an Item implementing this interface is used on a cursed chest.
	 * Implementations of this method should:
	 *  - check if the modifier can be applied
	 *  - apply the modifier
	 *  - modify the player's inventory e.g. remove the modifier item(s)
	 *  - return if the modifier was applied or not.
	 */
	@Override public ActionResult useOnChest(World world, PlayerEntity player, Hand hand, BlockHitResult blockHitResult, BlockPos mainBlockPos, BlockPos topBlockPos)
	{
		if (world.isClient) return ActionResult.FAIL;
		ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
		VerticalChestBlock mainBlock = (VerticalChestBlock) world.getBlockState(mainBlockPos).getBlock();
		if (Registry.BLOCK.getId(mainBlock) != from) return ActionResult.FAIL;
		ItemStack handStack = player.getStackInHand(hand);
		BlockEntity mainBlockEntity = world.getBlockEntity(mainBlockPos);
		if (topBlockPos == null)
		{
			handStack.setAmount(handStack.getAmount() - 1);
			CompoundTag mainTag = new CompoundTag();
			mainBlockEntity.toTag(mainTag);
			DefaultedList<ItemStack> inventoryData = DefaultedList.create(((VerticalChestBlockEntity) mainBlockEntity).getInvSize(), ItemStack.EMPTY);
			Inventories.fromTag(mainTag, inventoryData);
			serverPlayer.sendChatMessage(mainTag.toTextComponent(), ChatMessageType.CHAT);
		}
		else
		{
			BlockEntity topBlockEntity = world.getBlockEntity(topBlockPos);
			handStack.setAmount(handStack.getAmount() - 2);
		}
		serverPlayer.sendChatMessage(new StringTextComponent("Used item on block!"), ChatMessageType.GAME_INFO);
		return ActionResult.PASS;
	}
}
