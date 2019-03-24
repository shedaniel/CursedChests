package gd.rf.ninjaphenix.cursedchests.api.item;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author      NinjaPhenix
 * @version     1.0.5
 * @since       1.0.5
 */
public interface ChestModifier
{

	/**
	 * This method is called automatically when an Item implementing this interface is used on a cursed chest.
	 * Implementations of this method should:
	 *  - check if the modifier can be applied
	 *  - apply the modifier
	 *  - modify the player's inventory e.g. remove the modifier item(s)
	 *  - return if the modifier was applied or not.
	 * @param world The world the item was used in.
	 * @param player The player who used the item.
	 * @param hand The hand that the item was used from.
	 * @param blockHitResult The hit result.
	 * @param mainBlockPos The block pos of the single or bottom chest.
	 * @param topBlockPos The block pos of the top chest block or null if the chest is a single chest.
	 * @return ActionResult
	 * @since 1.0.5
	 * @see UseBlockCallback
	 */
	default ActionResult useOnChest(World world, PlayerEntity player, Hand hand, BlockHitResult blockHitResult, BlockPos mainBlockPos, BlockPos topBlockPos){ return ActionResult.PASS; }

	/**
	 * This method is called automatically when an Item implementing this interface is used on an entity.
	 * Implementations of this method should:
	 *  - check if the modifier can be applied
	 *  - apply the modifier
	 *  - modify the player's inventory e.g. remove the modifier item(s)
	 *  - return if the modifier was applied or not.
	 * @param world The world the item was used in.
	 * @param player The player who used the item.
	 * @param hand The hand that the item was used from.
	 * @param entity  The entity which the item was used upon.
	 * @param hitResult The hit result which may be null.
	 * @return ActionResult
	 * @see UseEntityCallback
	 * @since 1.0.5
	 */
	//player, world, hand, entity, hitResult
	default ActionResult useOnEntity(World world, PlayerEntity player, Hand hand, Entity entity, EntityHitResult hitResult){ return ActionResult.PASS; }
}
