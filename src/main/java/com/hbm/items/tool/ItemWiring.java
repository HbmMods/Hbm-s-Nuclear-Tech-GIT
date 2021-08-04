package com.hbm.items.tool;

import java.util.List;

import com.hbm.tileentity.conductor.TileEntityPylonRedWire;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemWiring extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_,
			float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (!player.isSneaking()) {
			TileEntity te = world.getTileEntity(x, y, z);

			if (te != null && te instanceof TileEntityPylonRedWire) {

				if (stack.stackTagCompound == null) {
					stack.stackTagCompound = new NBTTagCompound();

					stack.stackTagCompound.setInteger("x", x);
					stack.stackTagCompound.setInteger("y", y);
					stack.stackTagCompound.setInteger("z", z);

					if (world.isRemote)
						player.addChatMessage(new ChatComponentText(
								"Wire start"));
				} else {
					int x1 = stack.stackTagCompound.getInteger("x");
					int y1 = stack.stackTagCompound.getInteger("y");
					int z1 = stack.stackTagCompound.getInteger("z");

					if (world.getTileEntity(x1, y1, z1) != null && world.getTileEntity(x1, y1, z1) instanceof TileEntityPylonRedWire && this.isLengthValid(x, y, z, x1, y1, z1, 25)) {

						TileEntityPylonRedWire first = (TileEntityPylonRedWire) world.getTileEntity(x1, y1, z1);
						TileEntityPylonRedWire second = ((TileEntityPylonRedWire) te);
						
						first.addTileEntityBasedOnCoords(x, y, z);
						second.addTileEntityBasedOnCoords(x1, y1, z1);
						first.markDirty();
						second.markDirty();

						if (world.isRemote)
							player.addChatMessage(
									new ChatComponentText("Wire end"));

						stack.stackTagCompound = null;
					} else {
						if (world.isRemote)
							player.addChatMessage(new ChatComponentText(
									"Wire error"));
						stack.stackTagCompound = null;
					}
				}

				player.swingItem();
				return true;
			}
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if (itemstack.stackTagCompound != null) {
			list.add("Wire start x: " + itemstack.stackTagCompound.getInteger("x"));
			list.add("Wire start y: " + itemstack.stackTagCompound.getInteger("y"));
			list.add("Wire start z: " + itemstack.stackTagCompound.getInteger("z"));
		} else {
			list.add("Right-click poles to connect");
		}
	}
	
	public boolean isLengthValid(int x1, int y1, int z1, int x2, int y2, int z2, int length) {
		double l = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
		
		return l <= length;
	}

}
