package com.hbm.items.weapon.sedna.mods;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.HashBiMap;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.factory.XFactory9mm;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumModCaliber;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumModGeneric;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumModSpecial;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumModTest;
import com.hbm.items.weapon.sedna.factory.XFactory22lr;
import com.hbm.items.weapon.sedna.factory.XFactory357;
import com.hbm.items.weapon.sedna.factory.XFactory44;
import com.hbm.items.weapon.sedna.factory.XFactory45;
import com.hbm.items.weapon.sedna.factory.XFactory50;
import com.hbm.items.weapon.sedna.factory.XFactory556mm;
import com.hbm.items.weapon.sedna.factory.XFactory762mm;

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

		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.OVERRIDE_2_5.ordinal())).addDefault(new WeaponModOverride(3, 2.5F, "OVERRIDE"));
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.OVERRIDE_5.ordinal())).addDefault(new WeaponModOverride(4, 5F, "OVERRIDE"));
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.OVERRIDE_7_5.ordinal())).addDefault(new WeaponModOverride(5, 7.5F, "OVERRIDE"));
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.OVERRIDE_10.ordinal())).addDefault(new WeaponModOverride(6, 10F, "OVERRIDE"));
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.OVERRIDE_12_5.ordinal())).addDefault(new WeaponModOverride(7, 12_5F, "OVERRIDE"));
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.OVERRIDE_15.ordinal())).addDefault(new WeaponModOverride(8, 15F, "OVERRIDE"));
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_test, 1, EnumModTest.OVERRIDE_20.ordinal())).addDefault(new WeaponModOverride(9, 20F, "OVERRIDE"));

		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_generic, 1, EnumModGeneric.IRON_DAMAGE.ordinal())).addMod(ModItems.gun_pepperbox, new WeaponModGenericDamage(100));
		new WeaponModDefinition(new ItemStack(ModItems.weapon_mod_generic, 1, EnumModGeneric.IRON_DURA.ordinal())).addMod(ModItems.gun_pepperbox, new WeaponModGenericDurability(101));

		Item[] steelGuns = new Item[] {
				ModItems.gun_light_revolver, ModItems.gun_light_revolver_atlas,
				ModItems.gun_henry, ModItems.gun_henry_lincoln,
				ModItems.gun_greasegun,
				ModItems.gun_maresleg, ModItems.gun_maresleg_akimbo,
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
				ModItems.gun_uzi, ModItems.gun_uzi_akimbo,
				ModItems.gun_spas12,
				ModItems.gun_panzerschreck };
		Item[] wsteelGuns = new Item[] {
				ModItems.gun_g3, ModItems.gun_g3_zebra,
				ModItems.gun_stinger,
				ModItems.gun_chemthrower };
		Item[] ferroGuns = new Item[] {
				ModItems.gun_amat,
				ModItems.gun_m2,
				ModItems.gun_autoshotgun, ModItems.gun_autoshotgun_shredder,
				ModItems.gun_quadro };
		Item[] tcalloyGuns = new Item[] {
				ModItems.gun_lag,
				ModItems.gun_minigun,
				ModItems.gun_missile_launcher,
				ModItems.gun_tesla_cannon };
		Item[] bigmtGuns = new Item[] {
				ModItems.gun_laser_pistol, ModItems.gun_laser_pistol_pew_pew,
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
		new WeaponModDefinition(EnumModSpecial.SILENCER).addMod(new Item[] {ModItems.gun_am180, ModItems.gun_uzi, ModItems.gun_uzi_akimbo, ModItems.gun_g3, ModItems.gun_amat}, new WeaponModSilencer(ID_SILENCER));
		new WeaponModDefinition(EnumModSpecial.SCOPE).addMod(new Item[] {ModItems.gun_heavy_revolver, ModItems.gun_g3, ModItems.gun_mas36, ModItems.gun_charge_thrower}, new WeaponModScope(ID_SCOPE));
		new WeaponModDefinition(EnumModSpecial.SAW)
			.addMod(new Item[] {ModItems.gun_maresleg, ModItems.gun_double_barrel}, new WeaponModSawedOff(ID_SAWED_OFF))
			.addMod(ModItems.gun_panzerschreck, new WeaponModPanzerschreckSawedOff(ID_NO_SHIELD))
			.addMod(new Item[] {ModItems.gun_g3, ModItems.gun_g3_zebra}, new WeapnModG3SawedOff(ID_NO_STOCK));
		new WeaponModDefinition(EnumModSpecial.GREASEGUN).addMod(ModItems.gun_greasegun, new WeaponModGreasegun(ID_GREASEGUN_CLEAN));
		new WeaponModDefinition(EnumModSpecial.SLOWDOWN).addMod(new Item[] {ModItems.gun_minigun, ModItems.gun_minigun_dual}, new WeaponModSlowdown(207));
		new WeaponModDefinition(EnumModSpecial.SPEEDUP)
			.addMod(new Item[] {ModItems.gun_minigun, ModItems.gun_minigun_dual}, new WeaponModMinigunSpeedup(ID_MINIGUN_SPEED))
			.addMod(new Item[] {ModItems.gun_autoshotgun, ModItems.gun_autoshotgun_shredder}, new WeaponModShredderSpeedup(209));
		new WeaponModDefinition(EnumModSpecial.CHOKE).addMod(new Item[] {ModItems.gun_pepperbox, ModItems.gun_maresleg, ModItems.gun_double_barrel, ModItems.gun_liberator, ModItems.gun_spas12}, new WeaponModChoke(210));
		new WeaponModDefinition(EnumModSpecial.FURNITURE_GREEN).addMod(ModItems.gun_g3, new WeaponModPolymerFurniture(ID_FURNITURE_GREEN));
		new WeaponModDefinition(EnumModSpecial.FURNITURE_BLACK).addMod(ModItems.gun_g3, new WeaponModPolymerFurniture(ID_FURNITURE_BLACK));
		new WeaponModDefinition(EnumModSpecial.BAYONET)
		.addMod(ModItems.gun_mas36, new WeaponModMASBayonet(ID_MAS_BAYONET))
		.addMod(ModItems.gun_carbine, new WeaponModCarbineBayonet(ID_CARBINE_BAYONET));
		new WeaponModDefinition(EnumModSpecial.STACK_MAG).addMod(new Item[] {ModItems.gun_greasegun, ModItems.gun_uzi, ModItems.gun_uzi_akimbo, ModItems.gun_aberrator, ModItems.gun_aberrator_eott}, new WeaponModStackMag(214));
		new WeaponModDefinition(EnumModSpecial.SKIN_SATURNITE).addMod(new Item[] {ModItems.gun_uzi, ModItems.gun_uzi_akimbo}, new WeaponModUziSaturnite(ID_UZI_SATURN));
		new WeaponModDefinition(EnumModSpecial.LAS_SHOTGUN).addMod(new Item[] {ModItems.gun_lasrifle}, new WeaponModLasShotgun(ID_LAS_SHOTGUN));
		new WeaponModDefinition(EnumModSpecial.LAS_CAPACITOR).addMod(new Item[] {ModItems.gun_lasrifle}, new WeaponModLasCapacitor(ID_LAS_CAPACITOR));
		new WeaponModDefinition(EnumModSpecial.LAS_AUTO).addMod(new Item[] {ModItems.gun_lasrifle}, new WeaponModLasAuto(ID_LAS_AUTO));

		BulletConfig[] p9 = new BulletConfig[] {XFactory9mm.p9_sp, XFactory9mm.p9_fmj, XFactory9mm.p9_jhp, XFactory9mm.p9_ap};
		BulletConfig[] p45 = new BulletConfig[] {XFactory45.p45_sp, XFactory45.p45_fmj, XFactory45.p45_jhp, XFactory45.p45_ap, XFactory45.p45_du};
		BulletConfig[] p22 = new BulletConfig[] {XFactory22lr.p22_sp, XFactory22lr.p22_fmj, XFactory22lr.p22_jhp, XFactory22lr.p22_ap};
		BulletConfig[] m357 = new BulletConfig[] {XFactory357.m357_sp, XFactory357.m357_fmj, XFactory357.m357_jhp, XFactory357.m357_ap, XFactory357.m357_express};
		BulletConfig[] m44 = new BulletConfig[] {XFactory44.m44_sp, XFactory44.m44_fmj, XFactory44.m44_jhp, XFactory44.m44_ap, XFactory44.m44_express};
		BulletConfig[] r556 = new BulletConfig[] {XFactory556mm.r556_sp, XFactory556mm.r556_fmj, XFactory556mm.r556_jhp, XFactory556mm.r556_ap};
		BulletConfig[] r762 = new BulletConfig[] {XFactory762mm.r762_sp, XFactory762mm.r762_fmj, XFactory762mm.r762_jhp, XFactory762mm.r762_ap, XFactory762mm.r762_du, XFactory762mm.r762_he};
		BulletConfig[] bmg50 = new BulletConfig[] {XFactory50.bmg50_sp, XFactory50.bmg50_fmj, XFactory50.bmg50_jhp, XFactory50.bmg50_ap, XFactory50.bmg50_du, XFactory50.bmg50_he};
		new WeaponModDefinition(EnumModCaliber.P9)
			.addMod(ModItems.gun_henry, new WeaponModCaliber(300, 28, 10F, p9));
		new WeaponModDefinition(EnumModCaliber.P45)
			.addMod(ModItems.gun_henry, new WeaponModCaliber(310, 28, 10F, p45))
			.addMod(ModItems.gun_greasegun, new WeaponModCaliber(311, 24, 3F, p45))
			.addMod(ModItems.gun_uzi, new WeaponModCaliber(312, 24, 3F, p45))
			.addMod(ModItems.gun_uzi_akimbo, new WeaponModCaliber(313, 24, 3F, p45))
			.addMod(ModItems.gun_lag, new WeaponModCaliber(314, 15, 25F, p45));
		new WeaponModDefinition(EnumModCaliber.P22)
			.addMod(ModItems.gun_henry, new WeaponModCaliber(320, 28, 10F, p22))
			.addMod(ModItems.gun_uzi, new WeaponModCaliber(321, 40, 3F, p22))
			.addMod(ModItems.gun_uzi_akimbo, new WeaponModCaliber(322, 40, 3F, p22));
		new WeaponModDefinition(EnumModCaliber.M357)
			.addMod(ModItems.gun_henry, new WeaponModCaliber(330, 20, 10F, m357))
			.addMod(ModItems.gun_lag, new WeaponModCaliber(331, 15, 25F, m357));
		new WeaponModDefinition(EnumModCaliber.M44)
			.addMod(ModItems.gun_lag, new WeaponModCaliber(340, 13, 25F, m44));
		new WeaponModDefinition(EnumModCaliber.R556)
			.addMod(ModItems.gun_henry, new WeaponModCaliber(350, 10, 10F, r556))
			.addMod(ModItems.gun_carbine, new WeaponModCaliber(351, 20, 15F, r556))
			.addMod(new Item[] {ModItems.gun_minigun, ModItems.gun_minigun_dual}, new WeaponModCaliber(352, 0, 6F, r556));
		new WeaponModDefinition(EnumModCaliber.R762)
			.addMod(ModItems.gun_henry, new WeaponModCaliber(360, 8, 10F, r762))
			.addMod(ModItems.gun_g3, new WeaponModCaliber(361, 24, 5F, r762));
		new WeaponModDefinition(EnumModCaliber.BMG50)
			.addMod(ModItems.gun_henry, new WeaponModCaliber(370, 5, 10F, bmg50))
			.addMod(new Item[] {ModItems.gun_minigun, ModItems.gun_minigun_dual}, new WeaponModCaliber(371, 0, 6F, bmg50));
	}

	public static final int ID_SILENCER = 201;
	public static final int ID_SCOPE = 202;
	public static final int ID_SAWED_OFF = 203;
	public static final int ID_NO_SHIELD = 204;
	public static final int ID_NO_STOCK = 205;
	public static final int ID_GREASEGUN_CLEAN = 206;
	public static final int ID_MINIGUN_SPEED = 208;
	public static final int ID_FURNITURE_GREEN = 211;
	public static final int ID_FURNITURE_BLACK = 212;
	public static final int ID_MAS_BAYONET = 213;
	public static final int ID_UZI_SATURN = 215;
	public static final int ID_LAS_SHOTGUN = 216;
	public static final int ID_LAS_CAPACITOR = 217;
	public static final int ID_LAS_AUTO = 218;
	public static final int ID_CARBINE_BAYONET = 219;
	
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
	
	public static void onInstallStack(ItemStack gun, ItemStack mod, int cfg) {
		IWeaponMod newMod = modFromStack(gun, mod, cfg);
		if(newMod == null) return;
		newMod.onInstall(gun, mod, cfg);
	}
	
	public static void onUninstallStack(ItemStack gun, ItemStack mod, int cfg) {
		IWeaponMod newMod = modFromStack(gun, mod, cfg);
		if(newMod == null) return;
		newMod.onUninstall(gun, mod, cfg);
	}
	
	public static IWeaponMod modFromStack(ItemStack gun, ItemStack mod, int cfg) {
		if(gun == null || mod == null) return null;
		WeaponModDefinition def = stackToMod.get(new ComparableStack(mod));
		if(def == null) return null;
		IWeaponMod newMod = def.modByGun.get(new ComparableStack(gun).makeSingular()); //shift clicking causes the gun to have stack size 0!
		if(newMod == null) newMod = def.modByGun.get(null);
		return newMod;
	}
	
	public static boolean isApplicable(ItemStack gun, ItemStack mod, int cfg, boolean checkMutex) {
		IWeaponMod newMod = modFromStack(gun, mod, cfg);
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
			this(new ItemStack(ModItems.weapon_mod_generic, 1, num.ordinal()));
		}
		
		public WeaponModDefinition(EnumModSpecial num) {
			this(new ItemStack(ModItems.weapon_mod_special, 1, num.ordinal()));
		}
		
		public WeaponModDefinition(EnumModCaliber num) {
			this(new ItemStack(ModItems.weapon_mod_caliber, 1, num.ordinal()));
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
