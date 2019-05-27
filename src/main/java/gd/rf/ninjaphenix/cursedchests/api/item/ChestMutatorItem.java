package gd.rf.ninjaphenix.cursedchests.api.item;

import gd.rf.ninjaphenix.cursedchests.api.block.CursedChestBlock;
import gd.rf.ninjaphenix.cursedchests.api.block.CursedChestType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChestMutatorItem extends ChestModifierItem
{
	private static final Component[] modes = new Component[]{new TranslatableComponent("tooltip.cursedchests.chest_mutator.merge"), new TranslatableComponent("tooltip.cursedchests.chest_mutator.unmerge"), new TranslatableComponent("tooltip.cursedchests.chest_mutator.rotate")};

	public ChestMutatorItem()
	{
		super(new Item.Settings().stackSize(1).itemGroup(ItemGroup.TOOLS));
	}

	@Override protected ActionResult useModifierOnChestBlock(ItemUsageContext context, BlockState mainState, BlockPos mainBlockPos, BlockState otherState, BlockPos otherBlockPos)
	{
		PlayerEntity player = context.getPlayer();
		World world = context.getWorld();
		ItemStack stack = context.getItemStack();
		switch (getOrSetMode(stack))
		{
			case 0:
				if (otherState == null)
				{
					BlockPos realOtherBlockPos = mainBlockPos.offset(context.getFacing());
					BlockState realOtherState = world.getBlockState(realOtherBlockPos);
					if (realOtherState.getBlock() == mainState.getBlock() &&
						realOtherState.get(CursedChestBlock.FACING) == mainState.get(CursedChestBlock.FACING) &&
					    realOtherState.get(CursedChestBlock.TYPE) == CursedChestType.SINGLE)
					{
						if (!world.isClient)
						{
							CursedChestType mainChestType = CursedChestBlock.getChestType(mainState.get(CursedChestBlock.FACING), context.getFacing());
							CursedChestType otherChestType = CursedChestType.getOpposite(mainChestType);
							world.setBlockState(mainBlockPos, mainState.with(CursedChestBlock.TYPE, mainChestType));
							world.setBlockState(realOtherBlockPos, world.getBlockState(realOtherBlockPos).with(CursedChestBlock.TYPE, otherChestType));
						}
						return ActionResult.SUCCESS;
					}
				}
				break;
			case 1:
				if (otherState != null)
				{
					if (!world.isClient)
					{
						world.setBlockState(mainBlockPos, world.getBlockState(mainBlockPos).with(CursedChestBlock.TYPE, CursedChestType.SINGLE));
						world.setBlockState(otherBlockPos, world.getBlockState(otherBlockPos).with(CursedChestBlock.TYPE, CursedChestType.SINGLE));
					}
					return ActionResult.SUCCESS;
				}
				break;
			case 2:

				break;
			default:
				player.sendMessage(new TextComponent("Not yet implemented."));
				stack.getOrCreateTag().putByte("mode", (byte) 0);
				break;
		}
		return ActionResult.FAIL;
	}

	@Override protected TypedActionResult<ItemStack> useModifierInAir(World world, PlayerEntity player, Hand hand)
	{
		if (player.isSneaking())
		{
			ItemStack stack = player.getStackInHand(hand);
			byte mode = getOrSetMode(stack);
			if (++mode >= modes.length) { mode = 0; }
			CompoundTag tag = stack.getOrCreateTag();
			tag.putByte("mode", mode);
			if (!world.isClient)
			{
				player.addChatMessage(new TranslatableComponent("tooltip.cursedchests.tool_mode", modes[mode]), true);
			}
			return new TypedActionResult<>(ActionResult.SUCCESS, stack);
		}
		return super.useModifierInAir(world, player, hand);
	}

	@Override public void onCrafted(ItemStack stack, World world, PlayerEntity player)
	{
		super.onCrafted(stack, world, player);
		getOrSetMode(stack);
	}

	@Override public ItemStack getDefaultStack()
	{
		ItemStack stack = super.getDefaultStack();
		getOrSetMode(stack);
		return stack;
	}

	@Override public void appendItemsForGroup(ItemGroup itemGroup, DefaultedList<ItemStack> stackList)
	{
		if (this.isInItemGroup(itemGroup)) stackList.add(getDefaultStack());
	}

	private byte getOrSetMode(ItemStack stack)
	{
		CompoundTag tag = stack.getOrCreateTag();
		if (tag.containsKey("mode", 1)) return tag.getByte("mode");
		else
		{
			tag.putByte("mode", (byte) 0);
			return 0;
		}
	}
}
