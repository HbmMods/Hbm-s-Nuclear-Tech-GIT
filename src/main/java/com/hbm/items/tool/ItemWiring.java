package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.network.TileEntityPylonBase;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemWiring extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {

		if(!player.isSneaking()) {
			
			Block b = world.getBlock(x, y, z);
			
			if(b instanceof BlockDummyable) {
				int[] core = ((BlockDummyable)b).findCore(world, x, y, z);
				
				if(core != null) {
					x = core[0];
					y = core[1];
					z = core[2];
				}
			}
			
			TileEntity te = world.getTileEntity(x, y, z);

			if(te != null && te instanceof TileEntityPylonBase) {

				if(stack.stackTagCompound == null) {
					stack.stackTagCompound = new NBTTagCompound();

					stack.stackTagCompound.setInteger("x", x);
					stack.stackTagCompound.setInteger("y", y);
					stack.stackTagCompound.setInteger("z", z);

					if(!world.isRemote) {
						player.addChatMessage(new ChatComponentText("Wire start"));
					}
				} else if(!world.isRemote) {

					int x1 = stack.stackTagCompound.getInteger("x");
					int y1 = stack.stackTagCompound.getInteger("y");
					int z1 = stack.stackTagCompound.getInteger("z");

					if(world.getTileEntity(x1, y1, z1) instanceof TileEntityPylonBase) {

						TileEntityPylonBase first = (TileEntityPylonBase) world.getTileEntity(x1, y1, z1);
						TileEntityPylonBase second = ((TileEntityPylonBase) te);

						if(TileEntityPylonBase.canConnect(first, second)) {

							first.addConnection(x, y, z);
							second.addConnection(x1, y1, z1);
							player.addChatMessage(new ChatComponentText("Wire end"));

						} else {
							player.addChatMessage(new ChatComponentText("Wire error"));
						}

						stack.stackTagCompound = null;

					} else {

						if(!world.isRemote) {
							player.addChatMessage(new ChatComponentText("Wire error"));
						}
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
		if(itemstack.stackTagCompound != null) {
			list.add("Wire start x: " + itemstack.stackTagCompound.getInteger("x"));
			list.add("Wire start y: " + itemstack.stackTagCompound.getInteger("y"));
			list.add("Wire start z: " + itemstack.stackTagCompound.getInteger("z"));
		} else {
			list.add("Right-click poles to connect");
		}
	}
}
