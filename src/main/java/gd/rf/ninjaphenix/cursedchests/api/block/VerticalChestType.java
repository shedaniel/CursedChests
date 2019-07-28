package gd.rf.ninjaphenix.cursedchests.api.block;

import net.minecraft.util.SnakeCaseIdentifiable;

public enum VerticalChestType implements SnakeCaseIdentifiable
{
    SINGLE("single"),
    TOP("top"),
    BOTTOM("bottom");

    private final String name;

    VerticalChestType(String string) { name = string; }

    public String toSnakeCase() { return name; }
}