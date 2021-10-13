package com.hbm.items.tool;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemDyatlov extends Item {

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2) {
		
		if(!world.isRemote) {
			
			if(world.getBlock(x, y, z) instanceof RBMKBase) {
				
				RBMKBase rbmk = (RBMKBase)world.getBlock(x, y, z);
				
				int[] pos = rbmk.findCore(world, x, y, z);
				
				if(pos != null) {
					
					TileEntity te = world.getTileEntity(pos[0], pos[1], pos[2]);
					
					if(te instanceof TileEntityRBMKBase) {

						((TileEntityRBMKBase)te).meltdown();
						//((TileEntityRBMKBase)te).heat = 100000;
					}
				}
			}
		}
		
		return false;
	}
}
