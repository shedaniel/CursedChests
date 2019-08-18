package ninjaphenix.cursedchests.api;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import ninjaphenix.cursedchests.api.block.CursedChestType;

public class Registries
{
    public static final SimpleRegistry<Regular> REGULAR = new SimpleRegistry();
    public static final SimpleRegistry<Base> OLD = new SimpleRegistry();

    static
    {
        Identifier nullId = new Identifier("cursedchests", "null");
        REGULAR.add(nullId, new Regular(0, new LiteralText("Error"), nullId, nullId, nullId, nullId, nullId));
        OLD.add(nullId, new Base(0, new LiteralText("Error"), nullId));
    }


    public static class Regular extends Base
    {
        protected final Identifier singleTexture;
        protected final Identifier vanillaTexture;
        protected final Identifier tallTexture;
        protected final Identifier longTexture;

        public Regular(int slots, Text containerName, Identifier blockId,
                Identifier singleTexture, Identifier vanillaTexture, Identifier tallTexture, Identifier longTexture)
        {
            super(slots, containerName, blockId);
            this.singleTexture = singleTexture;
            this.vanillaTexture = vanillaTexture;
            this.tallTexture = tallTexture;
            this.longTexture = longTexture;
        }

        public Identifier getChestTexture(CursedChestType type)
        {
            switch (type)
            {
                case BOTTOM:
                    return tallTexture;
                case LEFT:
                    return vanillaTexture;
                case FRONT:
                    return longTexture;
                default:
                    return singleTexture;
            }
        }
    }

    public static class Base
    {
        protected final int slots;
        protected final Text containerName;
        protected final Identifier blockId;

        public Base(int slots, Text containerName, Identifier blockId)
        {
            this.slots = slots;
            this.containerName = containerName;
            this.blockId = blockId;
        }

        public int getSlotCount()
        {
            return slots;
        }

        public Text getContainerName()
        {
            return containerName;
        }
    }

}
