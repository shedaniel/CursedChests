package gd.rf.ninjaphenix.cursedchests.api.item;

import gd.rf.ninjaphenix.cursedchests.api.block.VerticalChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChestConversionItem extends Item implements ChestModifier
{
	private VerticalChestBlock from, to;

	public ChestConversionItem(Settings settings, VerticalChestBlock from, VerticalChestBlock to)
	{
		super(settings);
		this.from = from;
		this.to = to;
	}

	@Override public ActionResult useOnChest(World world, PlayerEntity player, Hand hand, BlockHitResult blockHitResult, BlockPos mainBlockPos, BlockPos topBlockPos)
	{
		System.out.println("Item used on a block!");
		return ActionResult.PASS;
	}
}
