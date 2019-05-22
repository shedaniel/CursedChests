package gd.rf.ninjaphenix.cursedchests.api.block;

import net.minecraft.util.StringIdentifiable;

public enum CursedChestType implements StringIdentifiable
{
	SINGLE("single"), TOP("top"), BACK("back"), RIGHT("right"), BOTTOM("bottom"), FRONT("front"), LEFT("left");

	private final String name;

	CursedChestType(String string){ name = string; }

	public CursedChestType getOpposite(CursedChestType type)
	{
		switch (type)
		{
			case FRONT:
				return BACK;
			case BACK:
				return FRONT;
			case BOTTOM:
				return TOP;
			case TOP:
				return BOTTOM;
			case LEFT:
				return RIGHT;
			case RIGHT:
				return LEFT;
			default:
				return null;
		}
	}

	public String asString(){ return name; }
}