package ninjaphenix.cursedchests.api;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ninjaphenix.cursedchests.api.block.CursedChestType;
import ninjaphenix.cursedchests.api.block.entity.BaseChestBlockEntity;
import ninjaphenix.cursedchests.api.block.entity.CursedChestBlockEntity;

import java.util.HashMap;

/**
 * @author NinjaPhenix
 * @version 1.0.5
 * @since 1.0.5
 */
public class CursedChestRegistry
{
    private static HashMap<Identifier, CursedChest> blockDataMap = new HashMap<>();
    private static HashMap<Identifier, OldChest> oldBlockDataMap = new HashMap<>();

    // Prevents old worlds from crashing ( just in case anyone does update with existing chests )
    static
    {
        Identifier id = new Identifier("null", "null");
        blockDataMap.put(id, new CursedChest(0, new LiteralText("Error"), id, id, id, id));
        oldBlockDataMap.put(id, new OldChest(0, new LiteralText("Error")));
    }

    /**
     * Registers a new chest block.
     *
     * @param block The identifier for the vertical chest block.
     * @param rows The amount of slot rows the chest should have.
     * @param containerName The name of the chest inside of the container.
     * @param singleTexture The texture for when the chest is single style.
     * @param vanillaTexture The texture for when the chest is vanilla style.
     * @param tallTexture The texture for when the chest is tall style.
     * @param longTexture The texture for when the chest is long style.
     * @throws AssertionError Thrown when block is null or already registered.
     * @since 1.0.5
     */
    public static void registerChest(Identifier block, int rows, Text containerName, Identifier singleTexture, Identifier vanillaTexture,
            Identifier tallTexture, Identifier longTexture)
    {
        assert block != null && blockDataMap.containsKey(block);
        CursedChest data = new CursedChest(9 * rows, containerName, singleTexture, vanillaTexture, tallTexture, longTexture);
        blockDataMap.put(block, data);
    }

    /**
     * Registers a new "old" chest block.
     *
     * @param block The identifier for the vertical chest block.
     * @param rows The amount of slot rows the chest should have.
     * @param containerName The name of the chest inside of the container.
     * @throws AssertionError Thrown when block is null or already registered.
     * @since 1.0.5
     */
    public static void registerOldChest(Identifier block, int rows, Text containerName)
    {
        assert block != null && oldBlockDataMap.containsKey(block);
        OldChest data = new OldChest(9 * rows, containerName);
        oldBlockDataMap.put(block, data);
    }

    /**
     * Gets a new instance of a registered chest block's block entity.
     *
     * @param block The identifier for the vertical chest block.
     * @return An instance of that block's block entity.
     * @since 1.0.5
     * @deprecated Use "old" aware method. (just append false to end of arguments)
     */
    @Deprecated
    public static CursedChestBlockEntity getChestBlockEntity(Identifier block)
    {
        return (CursedChestBlockEntity) getChestBlockEntity(block, false);
    }

    /**
     * Gets a new instance of a registered chest block's block entity.
     *
     * @param block The identifier for the vertical chest block.
     * @return An instance of that block's block entity.
     * @since 2.4.24
     */
    public static BaseChestBlockEntity getChestBlockEntity(Identifier block, boolean old)
    {
        BaseChestBlockEntity be = (BaseChestBlockEntity)
                Registry.BLOCK_ENTITY.get(new Identifier("cursedchests", old ? "old_cursed_chest" : "cursed_chest")).instantiate();
        be.setBlock(block);
        return be;
    }

    /**
     * Gets the identifier for the texture of the given chest.
     *
     * @param block The identifier for the chest block.
     * @param type
     * @since 1.2.17
     */
    public static Identifier getChestTexture(Identifier block, CursedChestType type)
    {
        assert block != null && blockDataMap.containsKey(block);
        switch (type)
        {
            case BOTTOM:
                return blockDataMap.get(block).tallTexture;
            case LEFT:
                return blockDataMap.get(block).vanillaTexure;
            case FRONT:
                return blockDataMap.get(block).longTexture;
            default:
                return blockDataMap.get(block).singleTexture;
        }
    }

    /**
     * Gets the amount of slots for the given chest.
     *
     * @param block The identifier for the chest block.
     * @return amount of slots
     * @since 1.2.17
     */
    @Deprecated
    public static int getSlots(Identifier block)
    {
        return getSlots(block, false);
    }

    /**
     * Gets the amount of slots for the given chest.
     *
     * @param block The identifier for the chest block.
     * @param old If the block is an "old" chest or not.
     * @return amount of slots
     * @since 2.4.24
     */
    public static int getSlots(Identifier block, boolean old)
    {
        assert block != null;
        if (old)
        {
            assert oldBlockDataMap.containsKey(block);
            return oldBlockDataMap.get(block).slots;
        }
        else
        {
            assert blockDataMap.containsKey(block);
            return blockDataMap.get(block).slots;
        }
    }

    /**
     * Gets the default container name for the given chest.
     *
     * @param block The identifier for the chest block.
     * @return default container name
     * @since 1.2.17
     */
    @Deprecated
    public static Text getDefaultContainerName(Identifier block)
    {
        return getDefaultContainerName(block, false);
    }

    /**
     * Gets the default container name for the given chest.
     *
     * @param block The identifier for the chest block.
     * @param old If the block is an "old" chest or not.
     * @return default container name
     * @since 2.4.24
     */
    public static Text getDefaultContainerName(Identifier block, boolean old)
    {
        assert block != null;
        if (old)
        {
            assert oldBlockDataMap.containsKey(block);
            return oldBlockDataMap.get(block).containerName;
        }
        else
        {
            assert blockDataMap.containsKey(block);
            return blockDataMap.get(block).containerName;
        }
    }

    static class CursedChest
    {
        private final int slots;
        private final Identifier singleTexture;
        private final Identifier vanillaTexure;
        private final Identifier tallTexture;
        private final Identifier longTexture;
        private final Text containerName;

        CursedChest(int slots, Text containerName, Identifier singleTexture, Identifier vanillaTexture, Identifier tallTexture, Identifier longTexture)
        {
            this.slots = slots;
            this.singleTexture = singleTexture;
            this.vanillaTexure = vanillaTexture;
            this.tallTexture = tallTexture;
            this.longTexture = longTexture;
            this.containerName = containerName;
        }
    }

    static class OldChest
    {
        private final int slots;
        private final Text containerName;

        OldChest(int slots, Text containerName)
        {
            this.slots = slots;
            this.containerName = containerName;
        }
    }
}
