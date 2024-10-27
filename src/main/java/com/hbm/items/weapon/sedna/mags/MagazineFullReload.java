package com.hbm.items.weapon.sedna.mags;

import com.hbm.items.weapon.sedna.BulletConfig;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/** Uses individual bullets which are loaded all at once */
public class MagazineFullReload extends MagazineSingleTypeBase {

	public MagazineFullReload(int index, int capacity) {
		super(index, capacity);
	}

	/** Returns true if the player has the same ammo if partially loaded, or any valid ammo if not */
	@Override
	public boolean canReload(ItemStack stack, IInventory inventory) {
		
		if(this.getAmount(stack, inventory) >= this.getCapacity(stack)) return false;

		if(inventory == null) return true;
		
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack slot = inventory.getStackInSlot(i);
			
			if(slot != null) {
				if(this.getAmount(stack, inventory) == 0) {
					for(BulletConfig config : this.acceptedBullets) {
						if(config.ammo.matchesRecipe(slot, true)) return true;
					}
				} else {
					BulletConfig config = this.getType(stack, inventory);
					if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); }
					if(config.ammo.matchesRecipe(slot, true)) return true;
				}
			}
		}
		
		return false;
	}

	/** Reloads all rounds at once. If the mag is empty, the mag's type will change to the first valid ammo type */
	@Override
	public void reloadAction(ItemStack stack, IInventory inventory) {

		if(inventory == null) {
			BulletConfig config = this.getType(stack, inventory);
			if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); } //fixing broken NBT
			this.setAmount(stack, this.capacity);
			return;
		}
		
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack slot = inventory.getStackInSlot(i);
			
			if(slot != null) {
				
				//mag is empty, assume next best type
				if(this.getAmount(stack, inventory) == 0) {
					
					for(BulletConfig config : this.acceptedBullets) {
						if(config.ammo.matchesRecipe(slot, true)) {
							this.setType(stack, config);
							int wantsToLoad = (int) Math.ceil((double) this.getCapacity(stack) / (double) config.ammoReloadCount);
							int toLoad = Math.min(wantsToLoad, slot.stackSize);
							this.setAmount(stack, Math.min(toLoad * config.ammoReloadCount, this.capacity));
							inventory.decrStackSize(i, toLoad);
							break;
						}
					}
				//mag has a type set, only load that
				} else {
					BulletConfig config = this.getType(stack, inventory);
					if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); } //fixing broken NBT

					if(config.ammo.matchesRecipe(slot, true)) {
						int alreadyLoaded = this.getAmount(stack, inventory);
						int wantsToLoad = (int) Math.ceil((double) this.getCapacity(stack) / (double) config.ammoReloadCount) - (alreadyLoaded / config.ammoReloadCount);
						int toLoad = Math.min(wantsToLoad, slot.stackSize);
						this.setAmount(stack, Math.min((toLoad * config.ammoReloadCount) + alreadyLoaded, this.capacity));
						inventory.decrStackSize(i, toLoad);
					}
				}
			}
		}
	}
}
