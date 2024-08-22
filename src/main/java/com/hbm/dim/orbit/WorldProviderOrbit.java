package com.hbm.dim.orbit;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.SolarSystem;
import com.hbm.dim.trait.CelestialBodyTrait.CBT_Destroyed;
import com.hbm.lib.Library;

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

	@SideOnly(Side.CLIENT)
	protected CelestialBody getOrbitingBody() {
		return OrbitalStation.clientStation.orbiting;
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

		float semiMajorAxisKm = getOrbitingBody().semiMajorAxisKm;
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
		celestialAngle -= (float)SolarSystem.calculateSingleAngle(worldObj, par1, getOrbitingBody(), 1_000_000) / 360.0F;
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
	
}
