package com.hbm.saveddata.satellites;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;

import api.hbm.redstoneoverradio.IRORInteractive;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SatelliteDetector extends SatelliteBase {

	public List<RadiationBurst> cachedResults = new ArrayList();
	
	public static final String CMD_SURVEY = "survey";
	public static final String CMD_COUNT = "count";
	public static final String CMD_GETTYPE = "gettype";
	public static final String CMD_GETPOSITION = "getposition";
	
	public SatelliteDetector() { }

	@Override public String getType() { return "UWB_EMISSION_DETECTOR"; }
	
	@Override
	public IChatComponent[] getInfo(World world) {
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.DETECTOR.ordinal())) + ".name")
		};
	}
	
	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_SURVEY)) {
			cachedResults.clear();
			
			for(RadiationBurst burst : bursts) {
				if(world.provider.dimensionId == burst.dimension) cachedResults.add(burst);
			}
		}
		
		if(cmd[0].equals(CMD_COUNT)) {
			this.tx = "" + cachedResults.size();
			return;
		}
		
		if(cmd[0].equals(CMD_GETTYPE) && cmd.length == 2) {
			RadiationBurst burst = getBurstFromIndex(cmd[1]);
			if(burst == null) { this.tx = ""; return; }
			this.tx = "" + burst.intensity.name();
			return;
		}
		
		if(cmd[0].equals(CMD_GETPOSITION) && cmd.length == 2) {
			RadiationBurst burst = getBurstFromIndex(cmd[1]);
			if(burst == null) { this.tx = ""; return; }
			this.tx = burst.x + ";" + burst.z;
			return;
		}
	}
	
	public RadiationBurst getBurstFromIndex(String cmd) {
		if(cachedResults.size() <= 0) return null;
		int index = IRORInteractive.parseInt(cmd, 1, cachedResults.size()) - 1;
		return cachedResults.get(index);
	}
	
	public static List<RadiationBurst> bursts = new ArrayList();

	public static final int DURATION_LOW = 15 * 20;
	public static final int DURATION_MEDIUM = 20 / 2;
	public static final int DURATION_HIGH = 60 * 20;
	
	public static final double INACCURACY_LOW = 10_000;
	public static final double INACCURACY_MEDIUM = 2_500;
	public static final double INARRCURACY_HIGH = 500;
	
	public static void reportEvent(World world, int lifetime, BurstIntensity intensity, double x, double z) {
		bursts.add(new RadiationBurst(world, lifetime, intensity, (int) Math.floor(x), (int) Math.floor(z)));
	}
	
	public static void updateSystem(World world) {
		
		bursts.removeIf(b -> {
			return world.provider.dimensionId == b.dimension && world.getTotalWorldTime() > b.expiresOn;
		});
	}
	
	public static class RadiationBurst {
		
		public int dimension;
		public long expiresOn;
		public BurstIntensity intensity;
		public int x;
		public int z;
		
		public RadiationBurst(World world, int lifetime, BurstIntensity intensity, int x, int z) {
			this.dimension = world.provider.dimensionId;
			this.expiresOn = world.getTotalWorldTime() + lifetime;
			this.intensity = intensity;
			this.x = x;
			this.z = z;
			
			double inaccuracy =
					intensity == BurstIntensity.LOW ? INACCURACY_LOW :
					intensity == BurstIntensity.MEDIUM ? INACCURACY_MEDIUM :
						INARRCURACY_HIGH;

			this.x += MathHelper.clamp_double(world.rand.nextGaussian(), -1, 1) * inaccuracy;
			this.z += MathHelper.clamp_double(world.rand.nextGaussian(), -1, 1) * inaccuracy;
		}
	}
	
	public static enum BurstIntensity {
		LOW,	// mini nukes, detectable for 15 seconds
		MEDIUM,	// particle accelerators and radar, detectable only briefly for half a second 
		HIGH;	// full sized nukes, show up for a full minute
	}
}
