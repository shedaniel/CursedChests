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
	 * @param itemUsageContext
	 * @param mainBlockPos The block pos of the single or bottom chest.
	 * @param topBlockPos The block pos of the top chest block or null if the chest is a single chest.
	 * @return true if the chest was modified otherwise false
	 * @since 1.0.5
	 */
	boolean useOnChest(ItemUsageContext itemUsageContext, BlockPos mainBlockPos, @Nullable BlockPos topBlockPos);
}
