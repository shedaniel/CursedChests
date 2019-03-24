package gd.rf.ninjaphenix.cursedchests.api.item;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sortme.ChatMessageType;
import net.minecraft.text.StringTextComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * @author NinjaPhenix
 * @version 1.0.5
 * @since 1.0.5
 */
public class ChestConversionItem extends Item implements ChestModifier
{
	private String from, to;

	public ChestConversionItem(Settings settings, String from, String to)
	{
		super(settings);
		this.from = from;
		this.to = to;
	}

	public ChestConversionItem(Settings settings, VerticalChestBlock from, VerticalChestBlock to)
	{
		super(settings);
		this.from = from.getName();
		this.to = to.getName();
	}

	/**
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
		if(!mainBlock.getName().equals(from)) return ActionResult.FAIL;
		ItemStack handStack = player.getStackInHand(hand);
		if (topBlockPos == null)
		{
			handStack.setAmount(handStack.getAmount() - 1);
		}
		else
		{
			handStack.setAmount(handStack.getAmount() - 2);
		}
		serverPlayer.sendChatMessage(new StringTextComponent("Used item on block!"), ChatMessageType.GAME_INFO);
		return ActionResult.PASS;
	}
}
