package com.hbm.saveddata.satellites;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.itempool.ItemPoolsSatellite;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

import java.util.HashMap;

public class SatelliteMiner extends SatelliteBase {
	/**
	 * {@link WeightedRandomObject} array with loot the satellite will deliver.
	 */
	private static final HashMap<Class<? extends SatelliteMiner>, String> CARGO = new HashMap<>();

	public long lastOp;

	public SatelliteMiner() { }

	@Override public String getType() { return "ASTEROID_MINER"; }
	
	@Override
	public IChatComponent[] getInfo(World world) {
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.MINER_ASTRO.ordinal())) + ".name")
		};
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
	public static String getCargoForItem(ComparableStack satelliteItem) {
		Class<? extends SatelliteBase> satelliteClass = XSatelliteRegistry.itemToClass.getOrDefault(satelliteItem, null);
		return satelliteClass != null ? CARGO.getOrDefault(satelliteClass, null) : null;
	}

	static {
		registerCargo(SatelliteMiner.class, ItemPoolsSatellite.POOL_SAT_MINER);
	}
}
