package com.hbm.items.tool;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IBomb.BombReturnCode;
import com.hbm.main.MainRegistry;
import com.hbm.util.ChatBuilder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemMultiDetonator extends Item {

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Shift right-click block to add position,");
		list.add("right-click to detonate!");
		list.add("Shift right-click in the air to clear postitions.");

		if(itemstack.getTagCompound() == null || getLocations(itemstack) == null) {
			list.add(EnumChatFormatting.RED + "No position set!");
		} else {

			int[][] locs = getLocations(itemstack);

			for(int i = 0; i < locs[0].length; i++) {

				list.add(EnumChatFormatting.YELLOW + "" + locs[0][i] + " / " + locs[1][i] + " / " + locs[2][i]);
			}
		}
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		if(player.isSneaking()) {
			addLocation(stack, x, y, z);

			if(!world.isRemote) {
				player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
						.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
						.next("] ").color(EnumChatFormatting.DARK_AQUA)
						.next("Position added!").color(EnumChatFormatting.GREEN).flush());
			}

			world.playSoundAtEntity(player, "hbm:item.techBoop", 2.0F, 1.0F);

			return true;
		}

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(stack.stackTagCompound == null || getLocations(stack) == null) {
			
			if(!world.isRemote) {
				player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
						.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
						.next("] ").color(EnumChatFormatting.DARK_AQUA)
						.next("No position set!").color(EnumChatFormatting.RED).flush());
			}
			
		} else {

			if(!player.isSneaking()) {
				int[][] locs = getLocations(stack);

				int succ = 0;

				for(int i = 0; i < locs[0].length; i++) {

					int x = locs[0][i];
					int y = locs[1][i];
					int z = locs[2][i];

					if(world.getBlock(x, y, z) instanceof IBomb) {
						
						if(!world.isRemote) {
							BombReturnCode ret = ((IBomb) world.getBlock(x, y, z)).explode(world, x, y, z);
							
							if(ret.wasSuccessful())
								succ++;

							if(GeneralConfig.enableExtendedLogging)
								MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by " + player.getDisplayName() + "!");
						}
					}
				}
				
				world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
				
				if(!world.isRemote) {
					player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
							.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
							.next("] ").color(EnumChatFormatting.DARK_AQUA)
							.next("Triggered " + succ + "/" + locs[0].length + "!").color(EnumChatFormatting.YELLOW).flush());
				}
				
			} else {

				stack.stackTagCompound.setIntArray("xValues", new int[0]);
				stack.stackTagCompound.setIntArray("yValues", new int[0]);
				stack.stackTagCompound.setIntArray("zValues", new int[0]);

				world.playSoundAtEntity(player, "hbm:item.techBoop", 2.0F, 1.0F);
				
				if(!world.isRemote) {
					player.addChatMessage(ChatBuilder.start("[").color(EnumChatFormatting.DARK_AQUA)
							.nextTranslation(this.getUnlocalizedName() + ".name").color(EnumChatFormatting.DARK_AQUA)
							.next("] ").color(EnumChatFormatting.DARK_AQUA)
							.next("Locations cleared!").color(EnumChatFormatting.RED).flush());
				}
			}
		}

		return stack;

	}

	private static void addLocation(ItemStack stack, int x, int y, int z) {

		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		int[] xs = stack.stackTagCompound.getIntArray("xValues");
		int[] ys = stack.stackTagCompound.getIntArray("yValues");
		int[] zs = stack.stackTagCompound.getIntArray("zValues");

		stack.stackTagCompound.setIntArray("xValues", ArrayUtils.add(xs, x));
		stack.stackTagCompound.setIntArray("yValues", ArrayUtils.add(ys, y));
		stack.stackTagCompound.setIntArray("zValues", ArrayUtils.add(zs, z));
	}

	private static int[][] getLocations(ItemStack stack) {

		int[] xs = stack.stackTagCompound.getIntArray("xValues");
		int[] ys = stack.stackTagCompound.getIntArray("yValues");
		int[] zs = stack.stackTagCompound.getIntArray("zValues");

		if(xs == null || ys == null || zs == null || xs.length == 0 || ys.length == 0 || zs.length == 0) {
			return null;
		}

		return new int[][] { xs, ys, zs };
	}
}
