package com.hbm.saveddata.satellites;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.util.WeightedRandomObject;
import net.minecraft.item.ItemStack;

public class SatelliteLunarMiner extends SatelliteMiner {
    static {
        registerCargo(new WeightedRandomObject[] {
                new WeightedRandomObject(new ItemStack(ModBlocks.moon_turf, 48), 5),
                new WeightedRandomObject(new ItemStack(ModBlocks.moon_turf, 32), 7),
                new WeightedRandomObject(new ItemStack(ModBlocks.moon_turf, 16), 5),
                new WeightedRandomObject(new ItemStack(ModItems.powder_lithium, 3), 5),
                new WeightedRandomObject(new ItemStack(ModItems.powder_iron, 3), 5),
                new WeightedRandomObject(new ItemStack(ModItems.crystal_iron, 1), 1),
                new WeightedRandomObject(new ItemStack(ModItems.crystal_lithium, 1), 1)
        });
    }
}