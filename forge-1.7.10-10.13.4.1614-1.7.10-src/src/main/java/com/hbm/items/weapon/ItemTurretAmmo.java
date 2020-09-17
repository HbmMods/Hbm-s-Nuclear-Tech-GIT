package com.hbm.items.weapon;

import com.hbm.tileentity.bomb.TileEntityTurretBase;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTurretAmmo extends Item {
	
	Block turret;
	int count;
	
	public ItemTurretAmmo(Block turret, int count) {
		this.turret = turret;
		this.count = count;
	}

	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2)
    {
		if(player.isSneaking())
			return false;
		
		if(world.getBlock(x, y, z) == turret) {
			
			if(world.getTileEntity(x, y, z) instanceof TileEntityTurretBase) {
				((TileEntityTurretBase)world.getTileEntity(x, y, z)).ammo += count;
            	player.inventory.consumeInventoryItem(this);
    			world.playSoundAtEntity(player, "hbm:weapon.reloadTurret", 1.0F, 1.0F);
			}
			return true;
		}
		
		return false;
    }

}
