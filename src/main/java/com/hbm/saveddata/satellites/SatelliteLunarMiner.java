package com.hbm.saveddata.satellites;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.util.WeightedRandomObject;
import net.minecraft.item.ItemStack;

public class SatelliteLunarMiner extends SatelliteMiner {
    static {
        registerCargo(SatelliteLunarMiner.class, new WeightedRandomObject[] {
                new WeightedRandomObject(new ItemStack(ModBlocks.moon_turf, 8), 15),
                new WeightedRandomObject(new ItemStack(ModBlocks.moon_turf, 16), 15),
                new WeightedRandomObject(new ItemStack(ModItems.powder_lithium, 3), 15),
                new WeightedRandomObject(new ItemStack(ModItems.powder_iron, 3), 15),
	new WeightedRandomObject(new ItemStack(ModItems.powder_nitan_mix, 2), 10),
	new WeightedRandomObject(new ItemStack(ModItems.powder_combine_steel, 1), 8),
	new WeightedRandomObject(new ItemStack(ModItems.powder_magic, 1), 7),
	new WeightedRandomObject(new ItemStack(ModItems.powder_dineutronium, 1), 7),
	new WeightedRandomObject(new ItemStack(ModItems.ingot_technetium, 1), 10),

	new WeightedRandomObject(new ItemStack(ModItems.powder_meteorite, 1), 8)

        });
    }
}