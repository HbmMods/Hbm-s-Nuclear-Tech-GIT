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
import java.util.Map.Entry;

public class XSatelliteRegistry {

	public static final HashBiMap<Integer, Class<? extends SatelliteBase>> idToClass = HashBiMap.create(20);
	// icons for the tracking radar GUI
	public static final HashMap<Integer, ItemStack> idToIcon = new HashMap();
	public static final HashMap<ComparableStack, Class<? extends SatelliteBase>> itemToClass = new HashMap();
	
	public static void register() {

		// Maps IDs, registers as launchable and adds sat icon
		registerLaunchable(0, SatelliteMapper.class,			new ItemStack(ModItems.satellite, 1, EnumSatType.SPY.ordinal()));
		registerLaunchable(1, SatelliteScanner.class,			new ItemStack(ModItems.satellite, 1, EnumSatType.SCANNER.ordinal()));
		registerLaunchable(2, SatelliteRadar.class,				new ItemStack(ModItems.satellite, 1, EnumSatType.RADAR.ordinal()));
		registerLaunchable(3, SatelliteDeathRay.class,			new ItemStack(ModItems.satellite, 1, EnumSatType.DEATH_RAY.ordinal()));
		registerLaunchable(4, SatelliteResonator.class,			new ItemStack(ModItems.satellite, 1, EnumSatType.XENIUM_RESONATOR.ordinal()));
		registerLaunchable(5, SatelliteRelay.class,				new ItemStack(ModItems.satellite, 1, EnumSatType.RELAY.ordinal()));
		registerLaunchable(6, SatelliteMiner.class,				new ItemStack(ModItems.satellite, 1, EnumSatType.MINER_ASTRO.ordinal()));
		registerLaunchable(7, SatelliteLunarMiner.class,		new ItemStack(ModItems.satellite, 1, EnumSatType.MINER_LUNAR.ordinal()));
		registerLaunchable(8, SatelliteHorizons.class,			new ItemStack(ModItems.sat_gerald));
		registerLaunchable(9, SatellitePrecisionLaser.class,	new ItemStack(ModItems.satellite, 1, EnumSatType.PRECISION_LASER.ordinal()));
		registerLaunchable(10, SatelliteDetector.class,			new ItemStack(ModItems.satellite, 1, EnumSatType.DETECTOR.ordinal()));
		registerLaunchable(11, SatelliteRayScan.class,			new ItemStack(ModItems.satellite, 1, EnumSatType.RAY_SCAN.ordinal()));
		registerLaunchable(12, SatelliteScience.class,			new ItemStack(ModItems.satellite, 1, EnumSatType.SCIENCE.ordinal()));
		registerLaunchable(13, SatelliteASAT.class,				new ItemStack(ModItems.satellite, 1, EnumSatType.ASAT.ordinal()));
		
		// and all the legacy crap
		registerLaunchable(SatelliteMapper.class, ModItems.sat_mapper);
		registerLaunchable(SatelliteScanner.class, ModItems.sat_scanner);
		registerLaunchable(SatelliteRadar.class, ModItems.sat_radar);
		registerLaunchable(SatelliteDeathRay.class, ModItems.sat_laser);
		registerLaunchable(SatelliteResonator.class, ModItems.sat_resonator);
		registerLaunchable(SatelliteMiner.class, ModItems.sat_miner);
		registerLaunchable(SatelliteLunarMiner.class, ModItems.sat_lunar_miner);
	}
	
	public static void registerLaunchable(int id, Class<? extends SatelliteBase> clazz, ItemStack satItem) {
		idToClass.put(id, clazz);
		idToIcon.put(id, satItem);
		registerLaunchable(clazz, new ComparableStack(satItem));
	}

	public static void registerLaunchable(Class<? extends SatelliteBase> sat, ComparableStack item) {
		if(!itemToClass.containsKey(item) && !itemToClass.containsValue(sat)) {
			itemToClass.put(item, sat);
		}
	}

	@Deprecated
	public static void registerLaunchable(Class<? extends SatelliteBase> sat, Item item) {
		if(!itemToClass.containsKey(item) && !itemToClass.containsValue(sat)) {
			itemToClass.put(new ComparableStack(item), sat);
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
	
	public static int getFreqFromTargetId(World world, String targetId) {
		if(targetId == null || targetId.isEmpty()) return -1;
		
		SatelliteSavedData data = SatelliteSavedData.getData(world);
		
		for(Entry<Integer, SatelliteBase> sat : data.sats.entrySet()) {
			if(targetId.equals(sat.getValue().getPositionData())) {
				return sat.getKey();
			}
		}
		
		return -1;
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
