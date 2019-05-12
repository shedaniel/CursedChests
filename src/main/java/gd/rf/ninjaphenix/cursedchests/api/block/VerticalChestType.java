package gd.rf.ninjaphenix.cursedchests.api.block;

import net.minecraft.util.StringIdentifiable;

public enum VerticalChestType implements StringIdentifiable
{
	SINGLE("single"),
	TOP("top"),
	BOTTOM("bottom");

	private final String name;

	VerticalChestType(String string){ name = string; }

	public String asString(){ return name; }
}