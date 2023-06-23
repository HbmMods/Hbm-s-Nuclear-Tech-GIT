package com.hbm.items.special;

import java.util.List;

import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemTeleLink extends Item {

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		if(!player.isSneaking() && !world.isRemote) {
			
			TileEntity te = world.getTileEntity(x, y, z);

			if(!(te instanceof TileEntityMachineTeleporter)) {
				
				if(stack.stackTagCompound == null) {
					stack.stackTagCompound = new NBTTagCompound();
				}
	
				stack.stackTagCompound.setInteger("x", x);
				stack.stackTagCompound.setInteger("y", y);
				stack.stackTagCompound.setInteger("z", z);
				stack.stackTagCompound.setInteger("dim", player.dimension);
				world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[TeleLink] Set teleporter exit to " + x + ", " + y + ", " + z + "."));
				player.swingItem();
				
				return true;
				
			} else {
				
				if(!stack.hasTagCompound()) {
					world.playSoundAtEntity(player, "hbm:item.techBoop", 1.0F, 1.0F);
					player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "[TeleLink] No destination set!"));
					return false;
				}
				
				int x1 = stack.stackTagCompound.getInteger("x");
				int y1 = stack.stackTagCompound.getInteger("y");
				int z1 = stack.stackTagCompound.getInteger("z");
				int dim = stack.stackTagCompound.getInteger("dim");
				
				TileEntityMachineTeleporter tele = (TileEntityMachineTeleporter) te;

				tele.targetX = x1;
				tele.targetY = y1;
				tele.targetZ = z1;
				tele.targetDim = dim;
				
				tele.markDirty();
				world.playSoundAtEntity(player, "hbm:item.techBleep", 1.0F, 1.0F);
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "[TeleLink] Teleporters destination has been set!"));
				player.swingItem();
				return true;
			}
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if (itemstack.stackTagCompound != null) {
			list.add("X: " + itemstack.stackTagCompound.getInteger("x"));
			list.add("Y: " + itemstack.stackTagCompound.getInteger("y"));
			list.add("Z: " + itemstack.stackTagCompound.getInteger("z"));
			list.add("D: " + itemstack.stackTagCompound.getInteger("dim"));
		} else {
			list.add(EnumChatFormatting.RED + "Select exit location first!");
		}
	}

}
