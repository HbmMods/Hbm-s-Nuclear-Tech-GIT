package com.hbm.items.tool;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntitySolarMirror;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemMirrorTool extends Item {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fX, float fY, float fZ) {
		
		Block b = world.getBlock(x, y, z);
		
		if(b == ModBlocks.machine_solar_boiler) {
			
			int[] pos = ((BlockDummyable)b).findCore(world, x, y, z);
			
			if(pos != null && !world.isRemote) {
				
				if(!stack.hasTagCompound())
					stack.stackTagCompound = new NBTTagCompound();

				stack.stackTagCompound.setInteger("posX", pos[0]);
				stack.stackTagCompound.setInteger("posY", pos[1] + 1);
				stack.stackTagCompound.setInteger("posZ", pos[2]);
				
				player.addChatComponentMessage(new ChatComponentTranslation("solar.linked").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
			}
			
			return true;
		}
		
		if(b == ModBlocks.solar_mirror && stack.hasTagCompound()) {
			
			if(!world.isRemote) {
				TileEntitySolarMirror mirror = (TileEntitySolarMirror)world.getTileEntity(x, y, z);
				mirror.setTarget(stack.stackTagCompound.getInteger("posX"), stack.stackTagCompound.getInteger("posY"), stack.stackTagCompound.getInteger("posZ"));
			}
			
			return true;
		}
		
		return false;
    }
}
