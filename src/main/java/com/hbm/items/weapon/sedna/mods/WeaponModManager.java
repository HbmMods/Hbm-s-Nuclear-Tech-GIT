package com.hbm.items.weapon.sedna.mods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.HashBiMap;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumModGeneric;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumModSpecial;
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
		/* CTOR contains registering to the idToMod, avoid reordering to prevent ID shifting! */
		/// TEST ///
		IWeaponMod TEST_FIRERATE = new WeaponModTestFirerate(0, "FIRERATE");
		IWeaponMod TEST_DAMAGE = new WeaponModTestDamage(1, "DAMAGE");
		IWeaponMod TEST_MULTI = new WeaponModTestMulti(2, "MULTI");
		
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.FIRERATE.ordinal())).addDefault(TEST_FIRERATE);
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.DAMAGE.ordinal())).addDefault(TEST_DAMAGE);
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.MULTI.ordinal())).addDefault(TEST_MULTI);

		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_generic, 1, EnumModGeneric.IRON_DAMAGE.ordinal())).addMod(ModItems.gun_pepperbox, new WeaponModGenericDamage(100));
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_generic, 1, EnumModGeneric.IRON_DURA.ordinal())).addMod(ModItems.gun_pepperbox, new WeaponModGenericDurability(101));

		Item[] steelGuns = new Item[] {
				ModItems.gun_light_revolver,
				ModItems.gun_light_revolver_atlas,
				ModItems.gun_henry,
				ModItems.gun_henry_lincoln,
				ModItems.gun_greasegun,
				ModItems.gun_maresleg,
				ModItems.gun_maresleg_akimbo,
				ModItems.gun_flaregun };
		Item[] duraGuns = new Item[] {
				ModItems.gun_am180,
				ModItems.gun_liberator,
				ModItems.gun_congolake,
				ModItems.gun_flamer,
				ModItems.gun_flamer_topaz };
		Item[] deshGuns = new Item[] {
				ModItems.gun_heavy_revolver,
				ModItems.gun_carbine,
				ModItems.gun_uzi,
				ModItems.gun_uzi_akimbo,
				ModItems.gun_spas12,
				ModItems.gun_panzerschreck };
		Item[] wsteelGuns = new Item[] {
				ModItems.gun_g3,
				ModItems.gun_stinger,
				ModItems.gun_chemthrower };
		Item[] ferroGuns = new Item[] {
				ModItems.gun_m2,
				ModItems.gun_autoshotgun,
				ModItems.gun_autoshotgun_shredder,
				ModItems.gun_quadro };
		Item[] tcalloyGuns = new Item[] {
				ModItems.gun_lag,
				ModItems.gun_minigun,
				ModItems.gun_missile_launcher,
				ModItems.gun_tesla_cannon };
		Item[] bigmtGuns = new Item[] {
				ModItems.gun_stg77,
				ModItems.gun_fatman,
				ModItems.gun_tau };
		Item[] bronzeGuns = new Item[] {
				ModItems.gun_lasrifle };

		new WeaponModDefinition(EnumModGeneric.STEEL_DAMAGE).addMod(steelGuns, new WeaponModGenericDamage(102));
		new WeaponModDefinition(EnumModGeneric.STEEL_DURA).addMod(steelGuns, new WeaponModGenericDurability(103));
		new WeaponModDefinition(EnumModGeneric.DURA_DAMAGE).addMod(duraGuns, new WeaponModGenericDamage(104));
		new WeaponModDefinition(EnumModGeneric.DURA_DURA).addMod(duraGuns, new WeaponModGenericDurability(105));
		new WeaponModDefinition(EnumModGeneric.DESH_DAMAGE).addMod(deshGuns, new WeaponModGenericDamage(106));
		new WeaponModDefinition(EnumModGeneric.DESH_DURA).addMod(deshGuns, new WeaponModGenericDurability(107));
		new WeaponModDefinition(EnumModGeneric.WSTEEL_DAMAGE).addMod(wsteelGuns, new WeaponModGenericDamage(108));
		new WeaponModDefinition(EnumModGeneric.WSTEEL_DURA).addMod(wsteelGuns, new WeaponModGenericDurability(109));
		new WeaponModDefinition(EnumModGeneric.FERRO_DAMAGE).addMod(ferroGuns, new WeaponModGenericDamage(110));
		new WeaponModDefinition(EnumModGeneric.FERRO_DURA).addMod(ferroGuns, new WeaponModGenericDurability(111));
		new WeaponModDefinition(EnumModGeneric.TCALLOY_DAMAGE).addMod(tcalloyGuns, new WeaponModGenericDamage(112));
		new WeaponModDefinition(EnumModGeneric.TCALLOY_DURA).addMod(tcalloyGuns, new WeaponModGenericDurability(113));
		new WeaponModDefinition(EnumModGeneric.BIGMT_DAMAGE).addMod(bigmtGuns, new WeaponModGenericDamage(114));
		new WeaponModDefinition(EnumModGeneric.BIGMT_DURA).addMod(bigmtGuns, new WeaponModGenericDurability(115));
		new WeaponModDefinition(EnumModGeneric.BRONZE_DAMAGE).addMod(bronzeGuns, new WeaponModGenericDamage(116));
		new WeaponModDefinition(EnumModGeneric.BRONZE_DURA).addMod(bronzeGuns, new WeaponModGenericDurability(117));

		new WeaponModDefinition(EnumModSpecial.SPEEDLOADER).addMod(ModItems.gun_liberator, new WeaponModLiberatorSpeedloader(200));
		new WeaponModDefinition(EnumModSpecial.SILENCER).addMod(new Item[] {ModItems.gun_uzi, ModItems.gun_uzi_akimbo, ModItems.gun_g3}, new WeaponModSilencer(ID_SILENCER));
		new WeaponModDefinition(EnumModSpecial.SCOPE).addMod(new Item[] {ModItems.gun_heavy_revolver, ModItems.gun_g3, ModItems.gun_mas36}, new WeaponModScope(ID_SCOPE));
		new WeaponModDefinition(EnumModSpecial.SAW)
			.addMod(new Item[] {ModItems.gun_maresleg, ModItems.gun_double_barrel}, new WeaponModSawedOff(ID_SAWED_OFF))
			.addMod(ModItems.gun_panzerschreck, new WeaponModPanzerschreckSawedOff(ID_NO_SHIELD))
			.addMod(ModItems.gun_g3, new WeapnModG3SawedOff(ID_NO_STOCK));
		new WeaponModDefinition(EnumModSpecial.GREASEGUN).addMod(ModItems.gun_greasegun, new WeaponModGreasegun(ID_GREASEGUN_CLEAN));
		new WeaponModDefinition(EnumModSpecial.SLOWDOWN).addMod(ModItems.gun_minigun, new WeaponModSlowdown(207));
		new WeaponModDefinition(EnumModSpecial.SPEEDUP)
			.addMod(ModItems.gun_minigun, new WeaponModMinigunSpeedup(208))
			.addMod(new Item[] {ModItems.gun_autoshotgun, ModItems.gun_autoshotgun_shredder}, new WeaponModShredderSpeedup(209));
		new WeaponModDefinition(EnumModSpecial.CHOKE).addMod(new Item[] {ModItems.gun_pepperbox, ModItems.gun_maresleg, ModItems.gun_double_barrel, ModItems.gun_liberator, ModItems.gun_spas12}, new WeaponModChoke(210));
		new WeaponModDefinition(EnumModSpecial.FURNITURE_GREEN).addMod(ModItems.gun_g3, new WeaponModPolymerFurniture(ID_FURNITURE_GREEN));
		new WeaponModDefinition(EnumModSpecial.FURNITURE_BLACK).addMod(ModItems.gun_g3, new WeaponModPolymerFurniture(ID_FURNITURE_BLACK));
		new WeaponModDefinition(EnumModSpecial.BAYONET).addMod(ModItems.gun_mas36, new WeaponModMASBayonet(ID_MAS_BAYONET));
	}

	public static final int ID_SILENCER = 201;
	public static final int ID_SCOPE = 202;
	public static final int ID_SAWED_OFF = 203;
	public static final int ID_NO_SHIELD = 204;
	public static final int ID_NO_STOCK = 205;
	public static final int ID_GREASEGUN_CLEAN = 206;
	public static final int ID_FURNITURE_GREEN = 211;
	public static final int ID_FURNITURE_BLACK = 212;
	public static final int ID_MAS_BAYONET = 213;
	
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
	
	public static boolean hasUpgrade(ItemStack stack, int cfg, int id) {
		if(!stack.hasTagCompound()) return false;
		int[] modIds = stack.stackTagCompound.getIntArray(KEY_MOD_LIST + cfg);
		for(int i = 0; i < modIds.length; i++) {
			if(modIds[i] == id) return true;
		}
		return false;
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
			if(iMod != null) for(String mutex0 : newMod.getSlots()) for(String mutex1 : iMod.getSlots()) {
				if(mutex0.equals(mutex1)) return false; //if any of the mod's slots are already taken
			}
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
		
		public WeaponModDefinition(EnumModGeneric num) {
			this.stack = new ItemStack(ModItems.weapon_mod_generic, 1, num.ordinal());
			stackToMod.put(new ComparableStack(stack), this);
		}
		
		public WeaponModDefinition(EnumModSpecial num) {
			this.stack = new ItemStack(ModItems.weapon_mod_special, 1, num.ordinal());
			stackToMod.put(new ComparableStack(stack), this);
		}

		public WeaponModDefinition addMod(ItemStack gun, IWeaponMod mod) { return addMod(new ComparableStack(gun), mod); }
		public WeaponModDefinition addMod(Item gun, IWeaponMod mod) { return addMod(new ComparableStack(gun), mod); }
		public WeaponModDefinition addMod(Item[] gun, IWeaponMod mod) { for(Item item : gun) addMod(new ComparableStack(item), mod); return this; }
		public WeaponModDefinition addMod(ComparableStack gun, IWeaponMod mod) {
			modByGun.put(gun, mod);
			modToStack.put(mod, stack);
			if(gun != null) {
				ItemGunBaseNT nt = (ItemGunBaseNT) gun.item;
				ComparableStack comp = new ComparableStack(stack);
				if(!nt.recognizedMods.contains(comp)) nt.recognizedMods.add(comp);
			}
			return this;
		}
		
		public WeaponModDefinition addDefault(IWeaponMod mod) {
			return addMod((ComparableStack) null, mod);
		}
	}
}
