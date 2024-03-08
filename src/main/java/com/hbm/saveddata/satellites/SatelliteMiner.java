package com.hbm.saveddata.satellites;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.util.WeightedRandomObject;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;

public class SatelliteMiner extends Satellite {
	/**
	 * {@link WeightedRandomObject} array with loot the satellite will deliver.
	 */
	private static final HashMap<Class<? extends SatelliteMiner>, WeightedRandomObject[]> CARGO = new HashMap<>();

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
	public static void registerCargo(Class<? extends SatelliteMiner> minerSatelliteClass, WeightedRandomObject[] cargo) {
		CARGO.put(minerSatelliteClass, cargo);
	}

	/**
	 * Gets items the satellite can deliver.
	 * @return - Array of {@link WeightedRandomObject} of satellite loot.
	 */
	public WeightedRandomObject[] getCargo() {
		return CARGO.get(getClass());
	}

	static {
		registerCargo(SatelliteMiner.class, new WeightedRandomObject[] {
			new WeightedRandomObject(new ItemStack(ModItems.powder_aluminium, 3), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_iron, 3), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_titanium, 2), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_coal, 4), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_uranium, 2), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_plutonium, 1), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_thorium, 2), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_desh, 3), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_diamond, 2), 10),
					new WeightedRandomObject(new ItemStack(Items.redstone, 5), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_nitan_mix, 2), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_spark_mix, 1), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_power, 2), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_copper, 5), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_lead, 3), 10),
					new WeightedRandomObject(new ItemStack(ModItems.fluorite, 4), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_lapis, 4), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_combine_steel, 1), 8),
					new WeightedRandomObject(new ItemStack(ModItems.powder_magic, 1), 7),
					new WeightedRandomObject(new ItemStack(ModItems.powder_dineutronium, 1), 7),
					new WeightedRandomObject(new ItemStack(ModItems.ingot_technetium, 1), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_meteorite, 1), 8)

		});
	}
}
