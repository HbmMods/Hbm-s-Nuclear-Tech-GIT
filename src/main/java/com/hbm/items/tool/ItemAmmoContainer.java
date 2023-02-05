package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.handler.GunConfiguration;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAmmoContainer extends Item {
	
	public static final List<Integer> configBlacklist = new ArrayList();

	public ItemAmmoContainer() {
		this.setMaxDamage(1);
		
		configBlacklist.add(BulletConfigSyncingUtil.SCHRABIDIUM_REVOLVER);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		for(ItemStack slot : player.inventory.mainInventory) {
			
			if(slot == null || !(slot.getItem() instanceof ItemGunBase)) continue;
			List<GunConfiguration> cfgs = new ArrayList();
			ItemGunBase gun = (ItemGunBase) slot.getItem();
			if(gun.mainConfig != null) cfgs.add(gun.mainConfig);
			if(gun.altConfig != null) cfgs.add(gun.altConfig);
			
			for(GunConfiguration cfg : cfgs) {
				if(cfg.config.isEmpty()) continue;
				Integer first = cfg.config.get(0);
				if(configBlacklist.contains(first)) continue;
				BulletConfiguration bullet = BulletConfigSyncingUtil.pullConfig(first);
				if(bullet == null) continue;
				if(bullet.ammo == null) continue;
				
				ItemStack ammo = bullet.ammo.toStack();
				//for belt-fed guns: 64 is main config, 1 if alt config
				//for reloaded guns: mag capacity divided by reload amount (equals one stack)
				ammo.stackSize = cfg.reloadType == cfg.RELOAD_NONE ? cfg == gun.mainConfig ? 64 : 1 : (int) Math.ceil((double) cfg.ammoCap / (double) bullet.ammoCount);
				player.inventory.addItemStackToInventory(ammo);
			}
		}
		
		stack.stackSize--;
		if(stack.stackSize <= 0)
			stack.damageItem(5, player);
		
		return stack;
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(this == ModItems.ammo_container) {
			list.add("Gives ammo for most held weapons.");
		}
	}
}
