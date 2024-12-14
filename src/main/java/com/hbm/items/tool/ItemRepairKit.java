package com.hbm.items.tool;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRepairKit extends Item {

	public ItemRepairKit(int dura) {
		this.setMaxStackSize(1);
		this.setMaxDamage(dura - 1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(world.isRemote) return stack;

		boolean didSomething = false;
		
		for(int i = 0; i < 9; i++) {

			ItemStack item = player.inventory.mainInventory[i];

			if(item != null && item.getItem() instanceof ItemGunBaseNT) {
				ItemGunBaseNT gun = (ItemGunBaseNT) item.getItem();
				int configs = gun.getConfigCount();
				
				for(int j = 0; j < configs; j++) {
					GunConfig cfg = gun.getConfig(item, j);
					float maxDura = cfg.getDurability(item);
					float wear = Math.min(gun.getWear(item, j), maxDura);
					if(wear > 0) {
						gun.setWear(item, j, Math.max(0F, gun.getWear(item, j) - maxDura * 0.25F));
						didSomething = true;
					}
				}
			}
		}
		
		if(didSomething) {
			if(this == ModItems.gun_kit_1) world.playSoundAtEntity(player, "hbm:item.spray", 1.0F, 1.0F);
			if(this == ModItems.gun_kit_2) world.playSoundAtEntity(player, "hbm:item.repair", 1.0F, 1.0F);
			
			stack.damageItem(1, player);
		}
		
		return stack;
	}
}
