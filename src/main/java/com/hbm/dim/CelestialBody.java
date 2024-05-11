package com.hbm.dim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.util.AstronomyUtil;

import codechicken.lib.math.MathHelper;
import net.minecraft.world.World;

public class CelestialBody {
    
    /**
     * Stores planet data in a tree structure, allowing for bodies orbiting bodies
     * Unit suffixes added when they differ from SI units, for clarity
     */

    public String name;

    public int dimensionId = 0;

    public float massKg = 0;
    public float radiusKm = 0;
    public float semiMajorAxisKm = 0; // Distance to the parent body
    private int rotationalPeriod = 6 * 60 * 60; // Day length in seconds

    public double atmosphericPressureBar = 0;

    public boolean canLand = false; // does this body have an associated dimension and a solid surface?

    public int processingLevel = 0; // What level of technology can locate this body?

    public List<CelestialBody> satellites = new ArrayList<CelestialBody>(); // moon boyes

    public HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();

    public CelestialBody(String name) {
        this.name = name;
    }

    public CelestialBody(String name, int id) {
        this(name);
        this.dimensionId = id;
        this.canLand = true;

        bodyMap.put(id, this);
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

    public CelestialBody withProcessingLevel(int level) {
        this.processingLevel = level;
        return this;
    }

    public CelestialBody withSatellites(CelestialBody... bodies) {
        Collections.addAll(satellites, bodies);
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
        // CelestialBodyWorldSavedData traitsData = CelestialBodyWorldSavedData.get(world);

        // // Set the updated traits in the saved data
        // traitsData.setTraits(world.provider.dimensionId, traits);

        // // Mark the saved data as dirty to ensure changes are saved
        // traitsData.markDirty();
    }

    // /Terraforming



    // Static getters
    // A lot of these are member getters but without having to check the celestial body exists
    // If it doesn't exist, return the overworld as the default, may cause issues with terraforming the overworld

    private static HashMap<Integer, CelestialBody> bodyMap = new HashMap<Integer, CelestialBody>();

    public static CelestialBody getBodyFromDimension(int id) {
        CelestialBody body = bodyMap.get(id);
        return body != null ? body : bodyMap.get(0);
    }

    public static CelestialBody getBodyFromDimension(World world) {
        return getBodyFromDimension(world.provider.dimensionId);
    }

    public static int getRotationalPeriod(World world) {
        return getBodyFromDimension(world).getRotationalPeriod();
    }

    public static float getSemiMajorAxis(World world) {
        return getBodyFromDimension(world).semiMajorAxisKm;
    }

    public static boolean hasTrait(World world, Class<? extends CelestialBodyTrait> trait) {
        return getBodyFromDimension(world).hasTrait(trait);
    }
    
    public static <T extends CelestialBodyTrait> T getTrait(World world, Class<? extends T> trait) {
        return getBodyFromDimension(world).getTrait(trait);
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
        return 0;
    }

    // Get the gravitational force at the surface, derived from mass and radius
    public float getSurfaceGravity() {
        float radius = radiusKm * 1000;
        return AstronomyUtil.GRAVITATIONAL_CONSTANT * massKg / (radius * radius);
    }

    
	
	public boolean hasTrait(Class<? extends CelestialBodyTrait> trait) {
		return this.traits.containsKey(trait);
	}
	
	@SuppressWarnings("unchecked")
    public <T extends CelestialBodyTrait> T getTrait(Class<? extends T> trait) {
		return (T) this.traits.get(trait);
	}

}
