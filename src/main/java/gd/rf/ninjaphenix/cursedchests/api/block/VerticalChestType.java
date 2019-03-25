package gd.rf.ninjaphenix.cursedchests.api.block;

import net.minecraft.util.StringRepresentable;

public enum VerticalChestType implements StringRepresentable
{
	SINGLE("single"),
	TOP("top"),
	BOTTOM("bottom");

	private final String name;

	VerticalChestType(String string){ name = string; }

	public String asString(){ return name; }
}