package gd.rf.ninjaphenix.cursedchests.api;

import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import javax.annotation.Nullable;

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
	 * @param itemUsageContext Information about the usage, Contains the world, player, item stack and block hit result.
	 * @param mainBlockPos The block pos of the single or bottom chest.
	 * @param topBlockPos The block pos of the top chest block or null if the chest is a single chest.
	 * @return true if the chest was modified otherwise false
	 * @since 1.0.5
	 */
	boolean useOnChest(ItemUsageContext itemUsageContext, BlockPos mainBlockPos, @Nullable BlockPos topBlockPos);
}
