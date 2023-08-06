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
					new WeightedRandomObject(new ItemStack(ModItems.powder_titanium, 2), 8),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_tungsten, 2), 7),
					new WeightedRandomObject(new ItemStack(ModItems.powder_coal, 4), 15),
					new WeightedRandomObject(new ItemStack(ModItems.powder_uranium, 2), 5),
					new WeightedRandomObject(new ItemStack(ModItems.powder_plutonium, 1), 5),
					new WeightedRandomObject(new ItemStack(ModItems.powder_thorium, 2), 7),
					new WeightedRandomObject(new ItemStack(ModItems.powder_desh_mix, 3), 5),
					new WeightedRandomObject(new ItemStack(ModItems.powder_diamond, 2), 7),
					new WeightedRandomObject(new ItemStack(Items.redstone, 5), 15),
					new WeightedRandomObject(new ItemStack(ModItems.powder_nitan_mix, 2), 5),
					new WeightedRandomObject(new ItemStack(ModItems.powder_power, 2), 5),
					new WeightedRandomObject(new ItemStack(ModItems.powder_copper, 5), 15),
					new WeightedRandomObject(new ItemStack(ModItems.powder_lead, 3), 10),
					new WeightedRandomObject(new ItemStack(ModItems.fluorite, 4), 15),
					new WeightedRandomObject(new ItemStack(ModItems.powder_lapis, 4), 10),
					new WeightedRandomObject(new ItemStack(ModItems.powder_combine_steel, 1), 1),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_aluminium, 1), 5),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_gold, 1), 5),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_phosphorus, 1), 10),
					new WeightedRandomObject(new ItemStack(ModBlocks.gravel_diamond, 1), 3),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_uranium, 1), 3),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_plutonium, 1), 3),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_trixite, 1), 1),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_starmetal, 1), 1),
					new WeightedRandomObject(new ItemStack(ModItems.crystal_lithium, 2), 4)
		});
	}
}
