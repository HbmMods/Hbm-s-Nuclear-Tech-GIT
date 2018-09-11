package com.hbm.items.tool;

import com.hbm.blocks.bomb.TurretBase;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemLock extends ItemKeyPin {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2)
    {
		if(this.getPins(stack) != 0) {
			TileEntity te = world.getTileEntity(x, y, z);
			
			if(te != null && te instanceof TileEntityLockableBase) {
				TileEntityLockableBase tile = (TileEntityLockableBase)te;
				
				if(tile.isLocked())
					return false;
				
				tile.lock();
				tile.setPins(this.getPins(stack));

	        	world.playSoundAtEntity(player, "hbm:block.lockHang", 1.0F, 1.0F);
				stack.stackSize--;
				
				return true;
			}
			
			if(te != null && te instanceof TileEntityDummy) {
				
				TileEntityDummy dummy = (TileEntityDummy)te;
				TileEntity target = world.getTileEntity(dummy.targetX, dummy.targetY, dummy.targetZ);

				if(target != null && target instanceof TileEntityLockableBase) {
					TileEntityLockableBase tile = (TileEntityLockableBase)target;
					
					if(tile.isLocked())
						return false;
					
					tile.lock();
					tile.setPins(this.getPins(stack));

		        	world.playSoundAtEntity(player, "hbm:block.lockHang", 1.0F, 1.0F);
					stack.stackSize--;
					
					return true;
				}
			}
		}
		
		return false;
    }

}
