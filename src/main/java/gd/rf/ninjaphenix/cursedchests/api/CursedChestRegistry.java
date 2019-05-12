package gd.rf.ninjaphenix.cursedchests.api;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.block.entity.VerticalChestBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import java.util.HashMap;

/**
 * @author NinjaPhenix
 * @version 1.0.5
 * @since 1.0.5
 */
public class CursedChestRegistry
{
	private static HashMap<VerticalChestBlock, CursedChest> blockdataMap = new HashMap<>();


	/**
	 * Registers a new chest block. Currently only used by the ItemRenderer Mixin to correctly render chest blocks in inventories, otherwise they will look like vanilla chests.
	 *
	 * @param block         The chest block to be registered.
	 * @param rows          The amount of slot rows the chest should have.
	 * @param singleTexture The texture for when the chest is single.
	 * @param tallTexture   The texture for when the chest is tall.
	 * @param containerName The name of the chest inside of the container.
	 * @throws AssertionError Thrown when block is null or already registered.
	 * @since 1.0.5
	 */
	public static void registerChest(VerticalChestBlock block, int rows, Identifier singleTexture, Identifier tallTexture, TranslatableComponent containerName)
	{
		assert block != null && blockdataMap.containsKey(block);
		CursedChest data = new CursedChest(9 * rows, singleTexture, tallTexture, containerName);
		blockdataMap.put(block, data);
	}

	/**
	 * Gets a new instance of a registered chest block's block entity.
	 * Used in {@link gd.rf.ninjaphenix.cursedchests.mixins.ItemDynamicRenderer} to render the chests properly in inventories.
	 *
	 * @param block The vertical chest block.
	 * @return An instance of that block's block entity.
	 * @since 1.0.5
	 */
	public static VerticalChestBlockEntity getChestBlockEntity(VerticalChestBlock block)
	{
		VerticalChestBlockEntity be = (VerticalChestBlockEntity) Registry.BLOCK_ENTITY.get(new Identifier("cursedchests", "vertical_chest")).instantiate();
		be.setBlock(block);
		return be;
	}

	/**
	 * Gets the identifier for the texture of the given chest.
	 *
	 * @param block  The vertical chest block.
	 * @param isTall Return tall texture if true else single texture.
	 * @since 1.2.17
	 */
	public static Identifier getChestTexture(VerticalChestBlock block, boolean isTall)
	{
		assert block != null && blockdataMap.containsKey(block);
		return isTall ? blockdataMap.get(block).tallTexture : blockdataMap.get(block).singleTexture;
	}

	public static int getSlots(VerticalChestBlock block)
	{
		assert block != null && blockdataMap.containsKey(block);
		return blockdataMap.get(block).slots;
	}

	public static Component getDefaultContainerName(VerticalChestBlock block)
	{
		assert block != null && blockdataMap.containsKey(block);
		return blockdataMap.get(block).containerName;
	}

	static class CursedChest
	{
		public final int slots;
		public final Identifier singleTexture;
		public final Identifier tallTexture;
		public final Component containerName;

		public CursedChest(int slots, Identifier singleTexture, Identifier tallTexture, TranslatableComponent containerName)
		{
			this.slots = slots;
			this.singleTexture = singleTexture;
			this.tallTexture = tallTexture;
			this.containerName = containerName;
		}
	}
}
