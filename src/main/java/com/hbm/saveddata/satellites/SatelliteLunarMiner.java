package com.hbm.saveddata.satellites;

import com.hbm.itempool.ItemPoolsSatellite;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class SatelliteLunarMiner extends SatelliteMiner {

	@Override public String getType() { return "LUNAR_MINER"; }
	
	@Override
	public IChatComponent[] getInfo(World world) {
		IChatComponent[] info = super.getInfo(world);
		info[0] = new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.MINER_LUNAR.ordinal())) + ".name");
		return info;
	}
	
	static {
		SatelliteMiner.registerCargo(SatelliteLunarMiner.class, ItemPoolsSatellite.POOL_SAT_LUNAR);
	}
}
