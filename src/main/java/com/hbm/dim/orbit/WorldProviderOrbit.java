package com.hbm.dim.orbit;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.trait.CelestialBodyTrait.CBT_Destroyed;
import com.hbm.lib.Library;
import com.hbm.util.AstronomyUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;

public class WorldProviderOrbit extends WorldProvider {

	// Orbit at an altitude that provides an hour-long realtime orbit (game time is fast so we go slow)
	// We want a consistent orbital period to prevent orbiting too slow or fast (both for player comfort and feel)
	private static final float ORBITAL_PERIOD = 3600;

	@SideOnly(Side.CLIENT)
	protected float getOrbitalAltitude(CelestialBody body) {
		return getAltitudeForPeriod(body.massKg, ORBITAL_PERIOD);
	}
	
	// r = ∛[(G x Me x T2) / (4π2)]
	private float getAltitudeForPeriod(float massKg, float period) {
		return (float)Math.cbrt((AstronomyUtil.GRAVITATIONAL_CONSTANT * massKg * (period * period)) / (4 * Math.PI * Math.PI));
	}

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenOrbit(SpaceConfig.orbitBiome), dimensionId);
	}

	@Override
	public String getDimensionName() {
		return "Orbit";
	}
	
	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderOrbit(this.worldObj);
	}

	@Override
	public void updateWeather() {
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float x, float y) {
		return Vec3.createVectorHelper(0, 0, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity camera, float partialTicks) {
		return Vec3.createVectorHelper(0, 0, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		// Stars become visible during the day beyond the orbit of Duna
		// And are fully visible during the day beyond the orbit of Jool
		float distanceStart = 20_000_000;
		float distanceEnd = 80_000_000;

		float semiMajorAxisKm = OrbitalStation.clientStation.orbiting.semiMajorAxisKm;
		float distanceFactor = MathHelper.clamp_float((semiMajorAxisKm - distanceStart) / (distanceEnd - distanceStart), 0F, 1F);

		float starBrightness = super.getStarBrightness(par1);

		return MathHelper.clamp_float(starBrightness, distanceFactor, 1F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float par1) {
		if(SolarSystem.kerbol.hasTrait(CBT_Destroyed.class))
			return 0;

		float celestialAngle = worldObj.getCelestialAngle(par1);
		float celestialPhase = (1 - (celestialAngle + 0.5F) % 1) * 2 - 1;

		return 1 - (float)Library.smoothstep(Math.abs(celestialPhase), 0.6, 0.8);
	}

	@Override
	public boolean canDoLightning(Chunk chunk) {
		return false;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return -99999;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return new SkyProviderOrbit();
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return 0.5F - ((float)SolarSystem.calculateSingleAngle(worldObj, partialTicks, OrbitalStation.clientStation.orbiting, getOrbitalAltitude(OrbitalStation.clientStation.orbiting)) / 360.0F);
	}
	
}
