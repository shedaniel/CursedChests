package gd.rf.ninjaphenix.cursedchests.api;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;

import java.util.HashMap;

/**
 * @author      NinjaPhenix
 * @version     1.0.5
 * @since       1.0.5
 */
public class CursedChestRegistry
{
	@Environment(EnvType.CLIENT)
	private static HashMap<VerticalChestBlock, BlockEntity> block2blockEntityMap = new HashMap<>();

	public static void registerChest(VerticalChestBlock block)
	{
		assert block != null : "Tried to register a null chest block.";
		if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
		{
			if(block2blockEntityMap.containsKey(block)) { System.out.println("Chest type "+block.getName()+" has already been registered!"); return; }
			block2blockEntityMap.put(block, block.createBlockEntity(null));
		}

	}

	@Environment(EnvType.CLIENT)
	public static BlockEntity getChestBlockEntity(VerticalChestBlock block)
	{
		return block2blockEntityMap.get(block);
	}

}
