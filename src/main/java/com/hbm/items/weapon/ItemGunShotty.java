package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;
import com.hbm.util.ShadyUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGunShotty extends ItemGunBase {

	public ItemGunShotty(GunConfiguration config) {
		super(config);
	}
	
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, boolean isCurrentItem) {
		super.updateServer(stack, world, player, slot, isCurrentItem);
		
		if((player.getUniqueID().toString().equals(ShadyUtil.Dr_Nostalgia) || player.getDisplayName().equals("Tankish") || player.getDisplayName().equals("Tankish020")) &&
				this.getDelay(stack) < this.mainConfig.rateOfFire * 0.9)
			this.setDelay(stack, 0);
	}

}
