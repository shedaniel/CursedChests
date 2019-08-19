package ninjaphenix.cursedchests.api;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import ninjaphenix.cursedchests.api.block.CursedChestType;

public class Registries
{
    // Make these defaulted registries to nullId?
    public static final SimpleRegistry<TierData> REGULAR = new SimpleRegistry<>();
    public static final SimpleRegistry<TierData> OLD = new SimpleRegistry<>();

    static
    {
        Identifier nullId = new Identifier("cursedchests", "null");
        REGULAR.add(nullId, new TierData(0, new LiteralText("Error"), nullId, nullId, nullId, nullId, nullId));
        OLD.add(nullId, REGULAR.get(nullId));
    }

    public static class TierData
    {
        protected final int slots;
        protected final Text containerName;
        protected final Identifier singleTexture;
        protected final Identifier vanillaTexture;
        protected final Identifier tallTexture;
        protected final Identifier longTexture;
        protected final Identifier blockId;

        public TierData(int slots, Text containerName, Identifier blockId, Identifier singleTexture, Identifier vanillaTexture,
                Identifier tallTexture, Identifier longTexture)
        {
            this.slots = slots;
            this.containerName = containerName;
            this.blockId = blockId;
            this.singleTexture = singleTexture;
            this.vanillaTexture = vanillaTexture;
            this.tallTexture = tallTexture;
            this.longTexture = longTexture;
        }

        public Identifier getChestTexture(CursedChestType type)
        {
            if (type == CursedChestType.BOTTOM) return tallTexture;
            else if (type == CursedChestType.LEFT) return vanillaTexture;
            else if (type == CursedChestType.FRONT) return longTexture;
            return singleTexture;
        }

        public int getSlotCount() { return slots; }

        public Text getContainerName() { return containerName; }
    }
}
