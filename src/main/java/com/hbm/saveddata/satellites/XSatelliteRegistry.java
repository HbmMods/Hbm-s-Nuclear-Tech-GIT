package com.hbm.saveddata.satellites;

import com.google.common.collect.HashBiMap;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.saveddata.SatelliteSavedData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.HashMap;

public class XSatelliteRegistry {
	
	public static final HashBiMap<Integer, Class<? extends SatelliteBase>> idToClass = HashBiMap.create(20);
	public static final HashMap<ComparableStack, Class<? extends SatelliteBase>> itemToClass = new HashMap<>();
	
	public static void register() {

		// ID mapping
		idToClass.put(0, SatelliteMapper.class);
		idToClass.put(1, SatelliteScanner.class);
		idToClass.put(2, SatelliteRadar.class);
		idToClass.put(3, SatelliteDeathRay.class);
		idToClass.put(4, SatelliteResonator.class);
		idToClass.put(5, SatelliteRelay.class);
		idToClass.put(6, SatelliteMiner.class);
		idToClass.put(7, SatelliteLunarMiner.class);
		idToClass.put(8, SatelliteHorizons.class);
		idToClass.put(9, SatellitePrecisionLaser.class);
		idToClass.put(10, SatelliteDetector.class);
		idToClass.put(11, SatelliteRayScan.class);
		idToClass.put(12, SatelliteScience.class);

		// item to sat type mapping
		registerSatellite(SatelliteMapper.class,			new ComparableStack(ModItems.satellite, 1, EnumSatType.SPY));
		registerSatellite(SatelliteScanner.class,			new ComparableStack(ModItems.satellite, 1, EnumSatType.SCANNER));
		registerSatellite(SatelliteRadar.class,				new ComparableStack(ModItems.satellite, 1, EnumSatType.RADAR));
		registerSatellite(SatelliteDeathRay.class,			new ComparableStack(ModItems.satellite, 1, EnumSatType.DEATH_RAY));
		registerSatellite(SatelliteResonator.class,			new ComparableStack(ModItems.satellite, 1, EnumSatType.XENIUM_RESONATOR));
		registerSatellite(SatelliteRelay.class,				new ComparableStack(ModItems.satellite, 1, EnumSatType.RELAY));
		registerSatellite(SatelliteMiner.class,				new ComparableStack(ModItems.satellite, 1, EnumSatType.MINER_ASTRO));
		registerSatellite(SatelliteLunarMiner.class,		new ComparableStack(ModItems.satellite, 1, EnumSatType.MINER_LUNAR));
		registerSatellite(SatelliteHorizons.class,			ModItems.sat_gerald);
		registerSatellite(SatellitePrecisionLaser.class,	new ComparableStack(ModItems.satellite, 1, EnumSatType.PRECISION_LASER));
		registerSatellite(SatelliteDetector.class,			new ComparableStack(ModItems.satellite, 1, EnumSatType.DETECTOR));
		registerSatellite(SatelliteRayScan.class,			new ComparableStack(ModItems.satellite, 1, EnumSatType.RAY_SCAN));
		registerSatellite(SatelliteScience.class,			new ComparableStack(ModItems.satellite, 1, EnumSatType.SCIENCE));
		
		// and all the legacy crap
		registerSatellite(SatelliteMapper.class, ModItems.sat_mapper);
		registerSatellite(SatelliteScanner.class, ModItems.sat_scanner);
		registerSatellite(SatelliteRadar.class, ModItems.sat_radar);
		registerSatellite(SatelliteDeathRay.class, ModItems.sat_laser);
		registerSatellite(SatelliteResonator.class, ModItems.sat_resonator);
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
			itemToClass.put(new ComparableStack(item), sat);
		}
	}

	public static void registerSatellite(Class<? extends SatelliteBase> sat, ComparableStack item) {
		if(!itemToClass.containsKey(item) && !itemToClass.containsValue(sat)) {
			itemToClass.put(item, sat);
		}
	}
	
	public static void orbit(World world, ItemStack stack, int freq, double x, double y, double z) {
		if(world.isRemote) return;

		SatelliteSavedData data = SatelliteSavedData.getData(world);
		SatelliteBase existing = data.sats.get(freq);
		
		if(existing != null) {
			existing.onPartDelivered(world, stack);
			
		} else {
			
			SatelliteBase sat = createFromItem(stack);
			
			if(sat != null) {
				data.sats.put(freq, sat);
				sat.onOrbit(world, x, y, z);
				data.markDirty();
			}
		}
	}
	
	public static SatelliteBase createFromId(int i) {
		try {
			return idToClass.get(i).newInstance();
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
