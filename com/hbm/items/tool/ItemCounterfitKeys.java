package com.hbm.items.tool;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemCounterfitKeys extends Item {
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int b, float f0, float f1, float f2)
    {
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityLockableBase) {
			
			TileEntityLockableBase locked = (TileEntityLockableBase) te;
			
			if(locked.isLocked()) {
				ItemStack st = new ItemStack(ModItems.key_fake);
				ItemKeyPin.setPins(st, locked.getPins());
				
				player.inventory.setInventorySlotContents(player.inventory.currentItem, st.copy());
				
				if(!player.inventory.addItemStackToInventory(st.copy())) {
					player.dropPlayerItemWithRandomChoice(st.copy(), false);
				}
				
				player.inventoryContainer.detectAndSendChanges();
				
				player.swingItem();
				
				return true;
			}
		}
		
		return false;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("Use on a locked container to create two counterfeit keys!");
	}

}
