package com.hbm.saveddata.satellites;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.util.WeightedRandomObject;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class SatelliteMinerCargoRegistry {
	private static final HashMap<String, WeightedRandomObject[]> cargo = new HashMap<String, WeightedRandomObject[]>() {{
		put(SatelliteMiner.class.getName(), new WeightedRandomObject[] {
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
		put(SatelliteLunarMiner.class.getName(), new WeightedRandomObject[] {
			new WeightedRandomObject(new ItemStack(ModBlocks.moon_turf, 48), 5),
			new WeightedRandomObject(new ItemStack(ModBlocks.moon_turf, 32), 7),
			new WeightedRandomObject(new ItemStack(ModBlocks.moon_turf, 16), 5),
			new WeightedRandomObject(new ItemStack(ModItems.powder_lithium, 3), 5),
			new WeightedRandomObject(new ItemStack(ModItems.powder_iron, 3), 5),
			new WeightedRandomObject(new ItemStack(ModItems.crystal_iron, 1), 1),
			new WeightedRandomObject(new ItemStack(ModItems.crystal_lithium, 1), 1),
		});
	}};

	/**
	 * Register cargo for specified satellite object
	 * @param o - Satellite object
	 * @param cargo - WeightedRandomObject array with loot
	 */
	public static void register(Object o, WeightedRandomObject[] cargo) {
		SatelliteMinerCargoRegistry.cargo.put(o.getClass().getName(), cargo);
	}

	/**
	 * Register cargo for specified satellite class
	 * @param c - Satellite class
	 * @param cargo - WeightedRandomObject array with loot
	 */
	public static void register(Class<?> c, WeightedRandomObject[] cargo) {
		SatelliteMinerCargoRegistry.cargo.put(c.getName(), cargo);
	}

	/**
	 * Get loot by satellite class name
	 * @param satelliteName - Satellite class name, like com.hbm.saveddata.satellites.SatelliteMiner
	 * @return - WeightedRandomObject array with loot
	 */
	public static WeightedRandomObject[] getCargo(String satelliteName) {
		if(cargo.containsKey(satelliteName)) {
			return cargo.get(satelliteName);
		}
		return new WeightedRandomObject[0];
	}
}
