package com.hbm.inventory;

import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.tileentity.IUpgradeInfoProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Arrays;
import java.util.HashMap;

/*
 Steps for use:
 1. TE implements IUpgradeInfoProvider
 2. TE creates a new instance of UpgradeManagerNT
 3. Upgrades and their levels can then be pulled from there.
 */

/**
 * Upgrade system, now with caching!
 * @author BallOfEnergy1
 */
public class UpgradeManagerNT {

	public TileEntity owner;
	public ItemStack[] cachedSlots;

	private UpgradeType mutexType;
	public HashMap<UpgradeType, Integer> upgrades = new HashMap<>();

	public UpgradeManagerNT(TileEntity te) { this.owner = te; }
	@Deprecated public UpgradeManagerNT() { }
	
	public void checkSlots(ItemStack[] slots, int start, int end) { checkSlots(owner, slots, start, end); }
	@Deprecated public void checkSlots(TileEntity te, ItemStack[] slots, int start, int end) { checkSlotsInternal(te, slots, start, end); }

	private void checkSlotsInternal(TileEntity te, ItemStack[] slots, int start, int end) {

		if(!(te instanceof IUpgradeInfoProvider) || slots == null)
			return;

		ItemStack[] upgradeSlots = Arrays.copyOfRange(slots, start, end + 1);

		if(Arrays.equals(upgradeSlots, cachedSlots))
			return;

		cachedSlots = upgradeSlots.clone();

		upgrades.clear();

		for (int i = 0; i <= end - start; i++) {

			if(upgradeSlots[i] != null && upgradeSlots[i].getItem() instanceof ItemMachineUpgrade) {

				ItemMachineUpgrade item = (ItemMachineUpgrade) upgradeSlots[i].getItem();
				IUpgradeInfoProvider upgradable = (IUpgradeInfoProvider) te;

				if(upgradable.getValidUpgrades() == null)
					return;

				if (upgradable.getValidUpgrades().containsKey(item.type)) { // Check if upgrade can even be accepted by the machine.
					if (item.type.mutex) {
						if (mutexType == null) {
							upgrades.put(item.type, 1);
							mutexType = item.type;
						} else if(item.type.ordinal() > mutexType.ordinal()) {
							upgrades.remove(mutexType);
							upgrades.put(item.type, 1);
							mutexType = item.type;
						}
					} else {

						Integer levelBefore = upgrades.get(item.type);
						int upgradeLevel = (levelBefore == null ? 0 : levelBefore);
						upgradeLevel += item.tier;
						// Add additional check to make sure it doesn't go over the max.
						upgrades.put(item.type, Math.min(upgradeLevel, upgradable.getValidUpgrades().get(item.type)));
					}
				}
			}
		}
	}

	public Integer getLevel(UpgradeType type) {
		return upgrades.getOrDefault(type, 0);
	}
}
