package com.hbm.items.machine;

import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.CompatExternal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemMuffler extends Item {
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2) {
		TileEntity te = CompatExternal.getCoreFromPos(world, x, y, z);

		if(te != null && te instanceof TileEntityLoadedBase) {
			TileEntityLoadedBase tile = (TileEntityLoadedBase) te;
			if(!tile.muffled) {
				tile.muffled = true;
				world.playSoundAtEntity(player, "hbm:item.upgradePlug", 1.0F, 1.0F);
				stack.stackSize--;
				tile.markDirty();
				return true;
			}
		}

		return false;
	}
}
