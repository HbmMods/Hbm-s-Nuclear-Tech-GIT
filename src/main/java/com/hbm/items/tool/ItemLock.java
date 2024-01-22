package com.hbm.items.tool;

import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.util.CompatExternal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemLock extends ItemKeyPin {
	
	public double lockMod = 0.1D;
	
	public ItemLock(double mod) {
		lockMod = mod;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2) {
		
		if(this.getPins(stack) != 0) {
			TileEntity te = CompatExternal.getCoreFromPos(world, x, y, z);

			if(te != null && te instanceof TileEntityLockableBase) {
				TileEntityLockableBase tile = (TileEntityLockableBase) te;

				if(tile.isLocked())
					return false;

				tile.setPins(this.getPins(stack));
				tile.lock();
				tile.setMod(lockMod);

				world.playSoundAtEntity(player, "hbm:block.lockHang", 1.0F, 1.0F);
				stack.stackSize--;

				return true;
			}
		}

		return false;
	}

}
