package com.hbm.items.weapon.sedna.mags;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemAmmoBag.InventoryAmmoBag;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.particle.SpentCasing;
import com.hbm.util.BobMathUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/** Base class for typical magazines, i.e. ones that hold bullets, shells, grenades, etc, any ammo item. Stores a single type of BulletConfigs */
public abstract class MagazineSingleTypeBase implements IMagazine<BulletConfig> {
	
	public static final String KEY_MAG_COUNT = "magcount";
	public static final String KEY_MAG_TYPE = "magtype";
	public static final String KEY_MAG_PREV = "magprev";
	public static final String KEY_MAG_AFTER = "magafter";

	public List<BulletConfig> acceptedBullets = new ArrayList();
	
	/** A number so the gun tell multiple mags apart */
	public int index;
	/** How much ammo this mag can hold */
	public int capacity;
	
	public MagazineSingleTypeBase(int index, int capacity) {
		this.index = index;
		this.capacity = capacity;
	}
	
	public MagazineSingleTypeBase addConfigs(BulletConfig... cfgs) { for(BulletConfig cfg : cfgs) acceptedBullets.add(cfg); return this; }

	@Override
	public BulletConfig getType(ItemStack stack, IInventory inventory) {
		int type = getMagType(stack, index);
		if(type >= 0 && type < BulletConfig.configs.size()) {
			BulletConfig cfg = BulletConfig.configs.get(type);
			if(acceptedBullets.contains(cfg)) return cfg;
			return acceptedBullets.get(0);
		}
		return null;
	}

	@Override
	public void setType(ItemStack stack, BulletConfig type) {
		int i = BulletConfig.configs.indexOf(type);
		if(i >= 0) setMagType(stack, index, i);
	}

	@Override
	public ItemStack getIconForHUD(ItemStack stack, EntityPlayer player) {
		BulletConfig config = this.getType(stack, player.inventory);
		if(config != null) return config.ammo.toStack();
		return null;
	}

	@Override
	public String reportAmmoStateForHUD(ItemStack stack, EntityPlayer player) {
		return getAmount(stack, player.inventory) + " / " + getCapacity(stack);
	}

	@Override
	public SpentCasing getCasing(ItemStack stack, IInventory inventory) {
		return this.getType(stack, inventory).casing;
	}

	@Override
	public void useUpAmmo(ItemStack stack, IInventory inventory, int amount) {
		if(!IMagazine.shouldUseUpTrenchie(inventory) && getCapacity(stack) != 1) return;
		this.setAmount(stack, this.getAmount(stack, inventory) - amount);
		IMagazine.handleAmmoBag(inventory, this.getType(stack, inventory), amount);
	}

	/** Returns true if the player has the same ammo if partially loaded, or any valid ammo if not */
	@Override
	public boolean canReload(ItemStack stack, IInventory inventory) {
		if(this.getAmount(stack, inventory) >= this.getCapacity(stack)) return false;
		if(inventory == null) return true;
		BulletConfig nextConfig = getFirstConfig(stack, inventory);
		return nextConfig != null;
	}
	
	public void standardReload(ItemStack stack, IInventory inventory, int loadLimit) {

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
				if(this.getAmount(stack, null) == 0) {
					
					for(BulletConfig config : this.acceptedBullets) {
						if(config.ammo.matchesRecipe(slot, true)) {
							this.setType(stack, config);
							int wantsToLoad = (int) Math.ceil((double) this.getCapacity(stack) / (double) config.ammoReloadCount);
							int toLoad = BobMathUtil.min(wantsToLoad, slot.stackSize, loadLimit);
							this.setAmount(stack, Math.min(toLoad * config.ammoReloadCount, this.capacity));
							inventory.decrStackSize(i, toLoad);
							break;
						}
					}
				//mag has a type set, only load that
				} else {
					BulletConfig config = this.getType(stack, null);
					if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); } //fixing broken NBT

					if(config.ammo.matchesRecipe(slot, true)) {
						int alreadyLoaded = this.getAmount(stack, null);
						int wantsToLoad = (int) Math.ceil((double) (this.getCapacity(stack) - alreadyLoaded) / (double) config.ammoReloadCount);
						int toLoad = BobMathUtil.min(wantsToLoad, slot.stackSize, loadLimit);
						this.setAmount(stack, Math.min((toLoad * config.ammoReloadCount) + alreadyLoaded, this.capacity));
						inventory.decrStackSize(i, toLoad);
					}
				}
				
				if(slot.getItem() == ModItems.ammo_bag) {
					InventoryAmmoBag bag = new InventoryAmmoBag(slot);
					
					for(int j = 0; j < bag.getSizeInventory(); j++) {
						ItemStack bagslot = bag.getStackInSlot(j);
						
						if(bagslot != null) {
							
							//mag is empty, assume next best type
							if(this.getAmount(stack, null) == 0) {
								
								for(BulletConfig config : this.acceptedBullets) {
									if(config.ammo.matchesRecipe(bagslot, true)) {
										this.setType(stack, config);
										int wantsToLoad = (int) Math.ceil((double) this.getCapacity(stack) / (double) config.ammoReloadCount);
										int toLoad = BobMathUtil.min(wantsToLoad, bagslot.stackSize, loadLimit);
										this.setAmount(stack, Math.min(toLoad * config.ammoReloadCount, this.capacity));
										bag.decrStackSize(j, toLoad);
										break;
									}
								}
							//mag has a type set, only load that
							} else {
								BulletConfig config = this.getType(stack, null);
								if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); } //fixing broken NBT

								if(config.ammo.matchesRecipe(bagslot, true)) {
									int alreadyLoaded = this.getAmount(stack, bag);
									int wantsToLoad = (int) Math.ceil((double) (this.getCapacity(stack) - alreadyLoaded) / (double) config.ammoReloadCount);
									int toLoad = BobMathUtil.min(wantsToLoad, bagslot.stackSize, loadLimit);
									this.setAmount(stack, Math.min((toLoad * config.ammoReloadCount) + alreadyLoaded, this.capacity));
									bag.decrStackSize(j, toLoad);
								}
							}
						}
					}
				}
			}
		}
	}
	
	/** Returns the config of the first potential loadable round, either what's already chambered or the first valid one if empty */
	public BulletConfig getFirstConfig(ItemStack stack, IInventory inventory) {
		if(inventory == null) return null;
		
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack slot = inventory.getStackInSlot(i);
			
			if(slot != null) {
				if(this.getAmount(stack, inventory) == 0) {
					for(BulletConfig config : this.acceptedBullets) {
						if(config.ammo.matchesRecipe(slot, true)) return config;
					}
				} else {
					BulletConfig config = this.getType(stack, inventory);
					if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); }
					if(config.ammo.matchesRecipe(slot, true)) return config;
				}
				
				if(slot.getItem() == ModItems.ammo_bag) {
					InventoryAmmoBag bag = new InventoryAmmoBag(slot);
					
					for(int j = 0; j < bag.getSizeInventory(); j++) {
						ItemStack bagslot = bag.getStackInSlot(j);
						
						if(bagslot != null) {
							if(this.getAmount(stack, bag) == 0) {
								for(BulletConfig config : this.acceptedBullets) {
									if(config.ammo.matchesRecipe(bagslot, true)) return config;
								}
							} else {
								BulletConfig config = this.getType(stack, bag);
								if(config == null) { config = this.acceptedBullets.get(0); this.setType(stack, config); }
								if(config.ammo.matchesRecipe(bagslot, true)) return config;
							}
						}
					}
				}
			}
		}
		
		return null;
	}
	
	@Override public void initNewType(ItemStack stack, IInventory inventory) {
		if(inventory == null) return;
		BulletConfig nextConfig = getFirstConfig(stack, inventory);
		if(nextConfig != null) {
			int i = BulletConfig.configs.indexOf(nextConfig);
			this.setMagType(stack, index, i);
		}
	}

	@Override public int getCapacity(ItemStack stack) { return capacity; }
	@Override public int getAmount(ItemStack stack, IInventory inventory) { return getMagCount(stack, index); }
	@Override public void setAmount(ItemStack stack, int amount) { setMagCount(stack, index, amount); }
	
	@Override public void setAmountBeforeReload(ItemStack stack, int amount) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_PREV + index, amount); }
	@Override public int getAmountBeforeReload(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_PREV + index); }
	@Override public void setAmountAfterReload(ItemStack stack, int amount) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_AFTER + index, amount); }
	@Override public int getAmountAfterReload(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_AFTER + index); }

	// MAG TYPE //
	public static int getMagType(ItemStack stack, int index) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_TYPE + index); }
	public static void setMagType(ItemStack stack, int index, int value) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_TYPE + index, value); }

	// MAG COUNT //
	public static int getMagCount(ItemStack stack, int index) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_COUNT + index); }
	public static void setMagCount(ItemStack stack, int index, int value) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_COUNT + index, value); }
}
