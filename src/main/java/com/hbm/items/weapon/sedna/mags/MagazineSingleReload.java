package com.hbm.items.weapon.sedna.mags;

import com.hbm.items.weapon.sedna.BulletConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/** Uses individual bullets which are loaded all at once */
public class MagazineSingleReload extends MagazineSingleTypeBase {

	public MagazineSingleReload(int index, int capacity) {
		super(index, capacity);
	}

	/** Returns true if the player has the same ammo if partially loaded, or any valid ammo if not */
	@Override
	public boolean canReload(ItemStack stack, EntityPlayer player) {
		
		if(this.getAmount(stack) >= this.getCapacity(stack)) return false;
		
		for(ItemStack slot : player.inventory.mainInventory) {
			
			if(slot != null) {
				if(this.getAmount(stack) == 0) {
					for(BulletConfig config : this.acceptedBullets) {
						if(config.ammo.matchesRecipe(slot, true)) return true;
					}
				} else {
					BulletConfig config = this.getType(stack);
					if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); }
					if(config.ammo.matchesRecipe(slot, true)) return true;
				}
			}
		}
		
		return false;
	}

	/** Reloads all rounds at once. If the mag is empty, the mag's type will change to the first valid ammo type */
	@Override
	public void reloadAction(ItemStack stack, EntityPlayer player) {
		
		for(int i = 0; i < player.inventory.mainInventory.length; i++) {
			ItemStack slot = player.inventory.mainInventory[i];
			
			if(slot != null) {
				
				//mag is empty, assume next best type
				if(this.getAmount(stack) == 0) {
					
					for(BulletConfig config : this.acceptedBullets) {
						if(config.ammo.matchesRecipe(slot, true)) {
							this.setType(stack, config);
							this.setAmount(stack, 1);
							player.inventory.decrStackSize(i, 1);
							return;
						}
					}
				//mag has a type set, only load that
				} else {
					BulletConfig config = this.getType(stack);
					if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); } //fixing broken NBT

					if(config.ammo.matchesRecipe(slot, true)) {
						int alreadyLoaded = this.getAmount(stack);
						this.setAmount(stack, alreadyLoaded + 1);
						player.inventory.decrStackSize(i, 1);
						return;
					}
				}
			}
		}
	}
}
