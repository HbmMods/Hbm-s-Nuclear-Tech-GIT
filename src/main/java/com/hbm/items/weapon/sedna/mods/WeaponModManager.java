package com.hbm.items.weapon.sedna.mods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.HashBiMap;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumModTest;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * The mod manager operates by scraping upgrades from a gun, then iterating over them and evaluating the given value, passing the modified value to successive mods.
 * The way that mods stack (additive vs multiplicative) depends on the order the mod is installed in
 * 
 * @author hbm
 */
public class WeaponModManager {
	
	public static final String KEY_MOD_LIST = "KEY_MOD_LIST_";
	
	/** Mapping of mods to IDs, keep the register order consistent! */
	public static HashBiMap<Integer, IWeaponMod> idToMod = HashBiMap.create();
	/** Mapping of mod items to mod definitions */
	public static HashMap<ComparableStack, WeaponModDefinition> stackToMod = new HashMap();
	/** Map for turning individual mods back into their item form, used when uninstaling mods */
	public static HashMap<IWeaponMod, ItemStack> modToStack = new HashMap();
	
	/** Assigns the IWeaponMod instances to items */
	public static void init() {

		/* ORDER MATTERS! */
		/* CTOR contains registering to the ID_LIST, avoid reordering to prevent ID shifting! */
		IWeaponMod TEST_FIRERATE = new WeaponModTestFirerate(0);
		IWeaponMod TEST_DAMAGE = new WeaponModTestDamage(1);
		IWeaponMod TEST_MULTI = new WeaponModTestMulti(2);
		
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.FIRERATE.ordinal())).addDefault(TEST_FIRERATE);
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.DAMAGE.ordinal())).addDefault(TEST_DAMAGE);
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.MULTI.ordinal())).addDefault(TEST_MULTI);
	}
	
	public static ItemStack[] getUpgradeItems(ItemStack stack, int cfg) {
		if(!stack.hasTagCompound()) return new ItemStack[0];
		int[] modIds = stack.stackTagCompound.getIntArray(KEY_MOD_LIST + cfg);
		if(modIds.length == 0) return new ItemStack[0];
		ItemStack[] mods = new ItemStack[modIds.length];
		for(int i = 0; i < mods.length; i++) {
			IWeaponMod mod = idToMod.get(modIds[i]);
			if(mod != null) {
				mods[i] = mod != null ? modToStack.get(mod) : null;
				if(mods[i] != null) mods[i] = mods[i].copy();
			}
		}
		return mods;
	}
	
	/** Installs the supplied mods to the gun */
	public static void install(ItemStack stack, int cfg, ItemStack... mods) {
		List<IWeaponMod> toInstall = new ArrayList();
		ComparableStack gun = new ComparableStack(stack);
		
		for(ItemStack mod : mods) {
			if(mod == null) continue;
			ComparableStack comp = new ComparableStack(mod);
			WeaponModDefinition def = stackToMod.get(comp);
			if(def != null) {
				IWeaponMod forGun = def.modByGun.get(gun);
				if(forGun != null) toInstall.add(forGun); //since this code only runs for upgrading, we can just indexOf because who cares
				else {
					forGun = def.modByGun.get(null);
					if(forGun != null) toInstall.add(forGun);
				}
			}
		}
		if(toInstall.isEmpty()) return;
		toInstall.sort(modSorter);
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		int[] modIds = new int[toInstall.size()];
		for(int i = 0; i < modIds.length; i++) modIds[i] = idToMod.inverse().get(toInstall.get(i));
		stack.stackTagCompound.setIntArray(KEY_MOD_LIST + cfg, modIds);
	}
	
	/** Wipes all mods from the gun */
	public static void uninstall(ItemStack stack, int cfg) {
		if(stack.hasTagCompound()) {
			stack.stackTagCompound.removeTag(KEY_MOD_LIST + cfg);
			//no need to clean up empty stackTagCompound because gun NBT is never empty anyway
		}
	}
	
	public static boolean isApplicable(ItemStack gun, ItemStack mod, int cfg, boolean checkMutex) {
		if(gun == null || mod == null) return false; //if either stacks are null
		WeaponModDefinition def = stackToMod.get(new ComparableStack(mod));
		if(def == null) return false; //if the mod stack doesn't have a mod definition
		IWeaponMod newMod = def.modByGun.get(new ComparableStack(gun));
		if(newMod == null) newMod = def.modByGun.get(null); //if there's no per-gun mod, default to null key
		if(newMod == null) return false; //if there's just no mod applicable
		
		if(checkMutex) for(int i : gun.stackTagCompound.getIntArray(KEY_MOD_LIST + cfg)) {
			IWeaponMod iMod = idToMod.get(i);
			if(iMod != null) for(String mutex0 : newMod.getSlots()) for(String mutex1 : iMod.getSlots()) if(mutex0.equals(mutex1)) return false; //if any of the mod's slots are already taken
		}
		
		return true; //yippie!
	}
	
	public static Comparator<IWeaponMod> modSorter = new Comparator<IWeaponMod>() {

		@Override
		public int compare(IWeaponMod o1, IWeaponMod o2) {
			return o2.getModPriority() - o1.getModPriority();
		}
	};
	
	/** Scrapes all upgrades, iterates over them and evaluates the given value. The parent (i.e. holder of the base value)
	 * is passed for context (so upgrades can differentiate primary and secondary receivers for example). Passing a null
	 * stack causes the base value to be returned. */
	public static <T> T eval(T base, ItemStack stack, String key, Object parent, int cfg) {
		if(stack == null) return base;
		if(!stack.hasTagCompound()) return base;
		
		for(int i : stack.stackTagCompound.getIntArray(KEY_MOD_LIST + cfg)) {
			IWeaponMod mod = idToMod.get(i);
			if(mod != null) base = mod.eval(base, stack, key, parent);
		}
		
		return base;
	}
	
	public static class WeaponModDefinition {
		
		/** Holds the weapon mod handlers for each given gun. Key null refers to mods that apply to ALL guns that are otherwise not included. */
		public HashMap<ComparableStack, IWeaponMod> modByGun = new HashMap();
		public ItemStack stack;
		
		public WeaponModDefinition(ItemStack stack) {
			this.stack = stack;
			stackToMod.put(new ComparableStack(stack), this);
		}

		public WeaponModDefinition addMod(ItemStack gun, IWeaponMod mod) { return addMod(new ComparableStack(gun), mod); }
		public WeaponModDefinition addMod(Item gun, IWeaponMod mod) { return addMod(new ComparableStack(gun), mod); }
		public WeaponModDefinition addMod(ComparableStack gun, IWeaponMod mod) {
			modByGun.put(gun, mod);
			modToStack.put(mod, stack);
			return this;
		}
		
		public WeaponModDefinition addDefault(IWeaponMod mod) {
			return addMod((ComparableStack) null, mod);
		}
	}
}
