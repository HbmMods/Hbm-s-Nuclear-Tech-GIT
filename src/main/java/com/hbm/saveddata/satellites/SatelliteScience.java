package com.hbm.saveddata.satellites;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class SatelliteScience extends SatelliteBase {

	@Override
	public String getType() {
		return "SCIENCE_PROBE";
	}
	
	@Override
	public IChatComponent[] getInfo(World world) {
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.SCIENCE.ordinal())) + ".name")
		};
	}
}
