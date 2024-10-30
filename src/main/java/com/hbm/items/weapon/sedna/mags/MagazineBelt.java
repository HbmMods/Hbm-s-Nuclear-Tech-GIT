package com.hbm.items.weapon.sedna.mags;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.particle.SpentCasing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MagazineBelt implements IMagazine<BulletConfig> {

	protected List<BulletConfig> acceptedBullets = new ArrayList();
	
	public MagazineBelt addConfigs(BulletConfig... cfgs) { for(BulletConfig cfg : cfgs) acceptedBullets.add(cfg); return this; }

	@Override
	public BulletConfig getType(ItemStack stack, IInventory inventory) {
		BulletConfig config = getFirstConfig(stack, inventory);
		if(this.getMagType(stack) != config.id) {
			this.setMagType(stack, config.id);
		}
		return config;
	}

	@Override
	public void useUpAmmo(ItemStack stack, IInventory inventory, int amount) {
		
		BulletConfig first = this.getFirstConfig(stack, inventory);
		
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack slot = inventory.getStackInSlot(i);
			
			if(slot != null) {
				if(first.ammo.matchesRecipe(slot, true)) {
					int toRemove = Math.min(slot.stackSize, amount);
					amount -= toRemove;
					inventory.decrStackSize(i, toRemove);
				}
			}
		}
	}

	@Override public void setType(ItemStack stack, BulletConfig type) { }
	@Override public int getCapacity(ItemStack stack) { return 0; }
	@Override public void setAmount(ItemStack stack, int amount) { }
	@Override public boolean canReload(ItemStack stack, IInventory inventory) { return false; }
	@Override public void reloadAction(ItemStack stack, IInventory inventory) { }
	@Override public void setAmountBeforeReload(ItemStack stack, int amount) { }
	@Override public int getAmountBeforeReload(ItemStack stack) { return 0; }
	@Override public void setAmountAfterReload(ItemStack stack, int amount) { }
	@Override public int getAmountAfterReload(ItemStack stack) { return 0; }

	@Override
	public int getAmount(ItemStack stack, IInventory inventory) {
		BulletConfig first = this.getFirstConfig(stack, inventory);
		int count = 0;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack slot = inventory.getStackInSlot(i);
			
			if(slot != null) {
				if(first.ammo.matchesRecipe(slot, true)) count += slot.stackSize;
			}
		}
		return count;
	}

	@Override
	public ItemStack getIconForHUD(ItemStack stack, EntityPlayer player) {
		BulletConfig first = this.getFirstConfig(stack, player.inventory);
		return first.ammo.toStack();
	}

	@Override
	public String reportAmmoStateForHUD(ItemStack stack, EntityPlayer player) {
		return "x" + getAmount(stack, player.inventory);
	}

	@Override
	public SpentCasing getCasing(ItemStack stack, IInventory invnetory) {
		return getFirstConfig(stack, invnetory).casing;
	}
	
	public BulletConfig getFirstConfig(ItemStack stack, IInventory inventory) {

		if(inventory == null) return acceptedBullets.get(0);
		
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack slot = inventory.getStackInSlot(i);
			
			if(slot != null) {
				for(BulletConfig config : this.acceptedBullets) {
					if(config.ammo.matchesRecipe(slot, true)) return config;
				}
			}
		}
		
		BulletConfig cached = BulletConfig.configs.get(this.getMagType(stack));
		return acceptedBullets.contains(cached) ? cached : acceptedBullets.get(0);
	}

	public static final String KEY_MAG_TYPE = "magtype";
	public static int getMagType(ItemStack stack) { return ItemGunBaseNT.getValueInt(stack, KEY_MAG_TYPE); }
	public static void setMagType(ItemStack stack, int value) { ItemGunBaseNT.setValueInt(stack, KEY_MAG_TYPE, value); }
}
