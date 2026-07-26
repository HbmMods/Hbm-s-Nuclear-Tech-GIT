package com.hbm.saveddata.satellites;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.saveddata.SatelliteSavedData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XSatelliteRegistry {
	
	public static final List<Class<? extends SatelliteBase>> satellites = new ArrayList<>();
	public static final HashMap<ComparableStack, Class<? extends SatelliteBase>> itemToClass = new HashMap<>();
	
	public static void register() {

		registerSatellite(SatelliteMapper.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.SPY));
		registerSatellite(SatelliteScanner.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.SCANNER));
		registerSatellite(SatelliteRadar.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.RADAR));
		registerSatellite(SatelliteDeathRay.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.DEATH_RAY));
		registerSatellite(SatelliteResonator.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.XENIUM_RESONATOR));
		registerSatellite(SatelliteRelay.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.XENIUM_RESONATOR));
		registerSatellite(SatelliteMiner.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.MINER_ASTRO));
		registerSatellite(SatelliteLunarMiner.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.MINER_LUNAR));
		registerSatellite(SatelliteHorizons.class, ModItems.sat_gerald);
		registerSatellite(SatellitePrecisionLaser.class, new ComparableStack(ModItems.satellite, 1, EnumSatType.PRECISION_LASER));
		

		registerSatellite(SatelliteMapper.class, ModItems.sat_mapper);
		registerSatellite(SatelliteScanner.class, ModItems.sat_scanner);
		registerSatellite(SatelliteRadar.class, ModItems.sat_radar);
		registerSatellite(SatelliteDeathRay.class, ModItems.sat_laser);
		registerSatellite(SatelliteResonator.class, ModItems.sat_resonator);
		registerSatellite(SatelliteRelay.class, ModItems.sat_foeq);
		registerSatellite(SatelliteMiner.class, ModItems.sat_miner);
		registerSatellite(SatelliteLunarMiner.class, ModItems.sat_lunar_miner);
	}

	/**
	 * Register satellite.
	 * @param sat - Satellite class
	 * @param item - Satellite item (which will be placed in a rocket)
	 */
	@Deprecated
	public static void registerSatellite(Class<? extends SatelliteBase> sat, Item item) {
		if(!itemToClass.containsKey(item) && !itemToClass.containsValue(sat)) {
			satellites.add(sat);
			itemToClass.put(new ComparableStack(item), sat);
		}
	}

	public static void registerSatellite(Class<? extends SatelliteBase> sat, ComparableStack item) {
		if(!itemToClass.containsKey(item) && !itemToClass.containsValue(sat)) {
			satellites.add(sat);
			itemToClass.put(item, sat);
		}
	}
	
	public static void orbit(World world, ItemStack stack, int freq, double x, double y, double z) {
		if(world.isRemote) return;

		SatelliteBase sat = createFromItem(stack);
		
		if(sat != null) {
			SatelliteSavedData data = SatelliteSavedData.getData(world);
			data.sats.put(freq, sat);
			sat.onOrbit(world, x, y, z);
			data.markDirty();
		}
	}
	
	public static SatelliteBase createFromId(int i) {
		try {
			return satellites.get(i).newInstance();
		} catch(Exception e) { }
		return null;
	}
	
	public static SatelliteBase createFromItem(ItemStack stack) {
		try {
			return itemToClass.get(new ComparableStack(stack).makeSingular()).newInstance();
		} catch(Exception e) { }
		return null;
	}
}
