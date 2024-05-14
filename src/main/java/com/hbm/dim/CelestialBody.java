package com.hbm.dim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.util.AstronomyUtil;

import codechicken.lib.math.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class CelestialBody {
	
	/**
	 * Stores planet data in a tree structure, allowing for bodies orbiting bodies
	 * Unit suffixes added when they differ from SI units, for clarity
	 */

	public String name;

	public int dimensionId = 0;

	public boolean canLand = false; // does this body have an associated dimension and a solid surface?

	public float massKg = 0;
	public float radiusKm = 0;
	public float semiMajorAxisKm = 0; // Distance to the parent body
	private int rotationalPeriod = 6 * 60 * 60; // Day length in seconds

	public float axialTilt = 0;

	public int processingLevel = 0; // What level of technology can locate this body?

	public ResourceLocation texture = null;
	public float[] color = new float[] {0.4F, 0.4F, 0.4F}; // When too small to render the texture

	public List<CelestialBody> satellites = new ArrayList<CelestialBody>(); // moon boyes
	public CelestialBody parent = null;

	public HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();

	public CelestialBody(String name) {
		this.name = name;
		this.texture = new ResourceLocation("hbm:textures/misc/space/" + name + ".png");

		nameToBodyMap.put(name, this);
	}

	public CelestialBody(String name, int id) {
		this(name);
		this.dimensionId = id;
		this.canLand = true;

		dimToBodyMap.put(id, this);
	}



	// Chainables for construction

	public CelestialBody withMassRadius(float kg, float km) {
		this.massKg = kg;
		this.radiusKm = km;
		return this;
	}

	public CelestialBody withSemiMajorAxis(float km) {
		this.semiMajorAxisKm = km;
		return this;
	}

	public CelestialBody withRotationalPeriod(int seconds) {
		this.rotationalPeriod = seconds;
		return this;
	}

	public CelestialBody withAxialTilt(float degrees) {
		this.axialTilt = degrees;
		return this;
	}

	public CelestialBody withProcessingLevel(int level) {
		this.processingLevel = level;
		return this;
	}

	public CelestialBody withTexture(String path) {
		this.texture = new ResourceLocation(path);
		return this;
	}

	public CelestialBody withColor(float... color) {
		this.color = color;
		return this;
	}

	public CelestialBody withSatellites(CelestialBody... bodies) {
		Collections.addAll(satellites, bodies);
		for(CelestialBody body : bodies) {
			body.parent = this;
		}
		return this;
	}

	public CelestialBody withTraits(CelestialBodyTrait... traits) {
		for(CelestialBodyTrait trait : traits) this.traits.put(trait.getClass(), trait);
		return this;
	}

	// /Chainables



	// Terraforming - trait overrides
	// If trait overrides exist, delete existing traits from the world, and replace them with the saved ones

	public static void setTraits(World world, CelestialBodyTrait... traits) {
		CelestialBodyWorldSavedData traitsData = CelestialBodyWorldSavedData.get(world);

		// // Set the updated traits in the saved data
		traitsData.setTraits( traits);

		// // Mark the saved data as dirty to ensure changes are saved
		traitsData.markDirty();
	}

	// /Terraforming



	// Static getters
	// A lot of these are member getters but without having to check the celestial body exists
	// If it doesn't exist, return the overworld as the default, may cause issues with terraforming the overworld

	private static HashMap<Integer, CelestialBody> dimToBodyMap = new HashMap<Integer, CelestialBody>();
	private static HashMap<String, CelestialBody> nameToBodyMap = new HashMap<String, CelestialBody>();

	public static CelestialBody getBody(String name) {
		CelestialBody body = nameToBodyMap.get(name);
		return body != null ? body : dimToBodyMap.get(0);
	}

	public static CelestialBody getBody(int id) {
		CelestialBody body = dimToBodyMap.get(id);
		return body != null ? body : dimToBodyMap.get(0);
	}

	public static CelestialBody getBody(World world) {
		return getBody(world.provider.dimensionId);
	}

	public static int getRotationalPeriod(World world) {
		return getBody(world).getRotationalPeriod();
	}

	public static float getSemiMajorAxis(World world) {
		return getBody(world).semiMajorAxisKm;
	}

	public static boolean hasTrait(World world, Class<? extends CelestialBodyTrait> trait) {
		return getBody(world).hasTrait(trait);
	}
	
	public static <T extends CelestialBodyTrait> T getTrait(World world, Class<? extends T> trait) {
		return getBody(world).getTrait(trait);
	}

	// /Statics



	public String getUnlocalizedName() {
		return name;
	}

	// Returns the day length in ticks, adjusted for the 20 minute minecraft day
	public int getRotationalPeriod() {
		return MathHelper.floor_double((float)rotationalPeriod * AstronomyUtil.DAY_FACTOR * 20);
	}

	// Returns the year length in days, derived from semi-major axis
	public int getOrbitalPeriod() {
		double semiMajorAxis = semiMajorAxisKm * 1_000;
		double orbitalPeriod = 2 * Math.PI * Math.sqrt((semiMajorAxis * semiMajorAxis * semiMajorAxis) / (AstronomyUtil.GRAVITATIONAL_CONSTANT * parent.massKg));
		return MathHelper.floor_double(orbitalPeriod / AstronomyUtil.SECONDS_IN_KSP_DAY);
	}

	// Get the gravitational force at the surface, derived from mass and radius
	public float getSurfaceGravity() {
		float radius = radiusKm * 1000;
		return AstronomyUtil.GRAVITATIONAL_CONSTANT * massKg / (radius * radius);
	}

	
    public boolean hasTrait(Class<? extends CelestialBodyTrait> trait) {
    	return getTraits().containsKey(trait);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends CelestialBodyTrait> T getTrait(Class<? extends T> trait) {
    	return (T) getTraits().get(trait);
    }

    private HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraits() {
        World world = DimensionManager.getWorld(dimensionId);
        HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits = CelestialBodyWorldSavedData.get(world).getTraits();

        if(traits != null)
            return traits;
        
        return this.traits;
    }

}
