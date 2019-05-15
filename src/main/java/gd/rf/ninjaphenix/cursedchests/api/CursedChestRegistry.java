package gd.rf.ninjaphenix.cursedchests.api;

import gd.rf.ninjaphenix.cursedchests.api.block.entity.CursedChestBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
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
	private static HashMap<Identifier, CursedChest> blockdataMap = new HashMap<>();

	// Prevents old worlds from crashing ( just in case anyone does update with existing chests )
	static{
		Identifier id = new Identifier("null", "null");
		blockdataMap.put(id, new CursedChest(0, id, id, new TextComponent("Error")));
	}

	/**
	 * Registers a new chest block. Currently only used by the ItemRenderer Mixin to correctly render chest blocks in inventories, otherwise they will look like vanilla chests.
	 *
	 * @param block        The identifier for the vertical chest block.
	 * @param rows          The amount of slot rows the chest should have.
	 * @param singleTexture The texture for when the chest is single.
	 * @param tallTexture   The texture for when the chest is tall.
	 * @param containerName The name of the chest inside of the container.
	 * @throws AssertionError Thrown when block is null or already registered.
	 * @since 1.0.5
	 */
	public static void registerChest(Identifier block, int rows, Identifier singleTexture, Identifier tallTexture, TranslatableComponent containerName)
	{
		assert block != null && blockdataMap.containsKey(block);
		CursedChest data = new CursedChest(9 * rows, singleTexture, tallTexture, containerName);
		blockdataMap.put(block, data);
	}

	/**
	 * Gets a new instance of a registered chest block's block entity.
	 * Used in {@link gd.rf.ninjaphenix.cursedchests.mixins.ItemDynamicRenderer} to render the chests properly in inventories.
	 *
	 * @param block The identifier for the vertical chest block.
	 * @return An instance of that block's block entity.
	 * @since 1.0.5
	 */
	public static CursedChestBlockEntity getChestBlockEntity(Identifier block)
	{
		CursedChestBlockEntity be = (CursedChestBlockEntity) Registry.BLOCK_ENTITY.get(new Identifier("cursedchests", "cursed_chest")).instantiate();
		be.setBlock(block);
		return be;
	}

	/**
	 * Gets the identifier for the texture of the given chest.
	 *
	 * @param block  The identifier for the vertical chest block.
	 * @param isTall Return tall texture if true else single texture.
	 * @since 1.2.17
	 */
	public static Identifier getChestTexture(Identifier block, boolean isTall)
	{
		assert block != null && blockdataMap.containsKey(block);
		return isTall ? blockdataMap.get(block).tallTexture : blockdataMap.get(block).singleTexture;
	}

	public static int getSlots(Identifier block)
	{
		assert block != null && blockdataMap.containsKey(block);
		return blockdataMap.get(block).slots;
	}

	public static Component getDefaultContainerName(Identifier block)
	{
		assert block != null && blockdataMap.containsKey(block);
		return blockdataMap.get(block).containerName;
	}

	static class CursedChest
	{
		final int slots;
		final Identifier singleTexture;
		final Identifier tallTexture;
		final Component containerName;

		CursedChest(int slots, Identifier singleTexture, Identifier tallTexture, Component containerName)
		{
			this.slots = slots;
			this.singleTexture = singleTexture;
			this.tallTexture = tallTexture;
			this.containerName = containerName;
		}
	}
}
