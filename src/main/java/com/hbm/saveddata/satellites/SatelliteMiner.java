package com.hbm.saveddata.satellites;

import com.hbm.itempool.ItemPoolsSatellite;
import com.hbm.util.WeightedRandomObject;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;

public class SatelliteMiner extends Satellite {
	/**
	 * {@link WeightedRandomObject} array with loot the satellite will deliver.
	 */
	private static final HashMap<Class<? extends SatelliteMiner>, String> CARGO = new HashMap<>();

	public long lastOp;

	public SatelliteMiner() {
		this.satIface = Interfaces.NONE;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("lastOp", lastOp);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		lastOp = nbt.getLong("lastOp");
	}

	/**
	 * Replaces cargo of the satellite.
	 * @param cargo - Array of {@link WeightedRandomObject} representing the loot that will be delivered.
	 */
	public static void registerCargo(Class<? extends SatelliteMiner> minerSatelliteClass, String cargo) {
		CARGO.put(minerSatelliteClass, cargo);
	}

	/**
	 * Gets items the satellite can deliver.
	 * @return - Array of {@link WeightedRandomObject} of satellite loot.
	 */
	public String getCargo() {
		return CARGO.get(getClass());
	}

	/**
	 * Gets the cargo key for the satellite item. If the item is not a miner satellite null is returned.
	 * @param satelliteItem - Satellite item
	 * @return - Returns {@link com.hbm.itempool.ItemPool} key or null if the item is not a mining satellite.
	 */
	public static String getCargoForItem(Item satelliteItem) {
		Class<? extends Satellite> satelliteClass = itemToClass.getOrDefault(satelliteItem, null);
		return satelliteClass != null ? CARGO.getOrDefault(satelliteClass, null) : null;
	}

	static {
		registerCargo(SatelliteMiner.class, ItemPoolsSatellite.POOL_SAT_MINER);
	}
}
