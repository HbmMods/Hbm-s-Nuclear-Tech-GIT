package com.hbm.dim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.dim.trait.CBT_Atmosphere.FluidEntry;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.render.shader.Shader;
import com.hbm.util.AstronomyUtil;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
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

	public String tidallyLockedTo = null;
	
	public List<CelestialBody> satellites = new ArrayList<CelestialBody>(); // moon boyes
	public CelestialBody parent = null;

	private HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();

	public String stoneTexture = "stone";
	public SolarSystem.Body type;

	@SideOnly(Side.CLIENT)
	public Shader shader;

	public float shaderScale = 1; // If the shader renders the item within the quad (not filling it entirely), scale it up from the true size

	public CelestialBody(String name) {
		this.name = name;
		this.texture = new ResourceLocation("hbm:textures/misc/space/" + name + ".png");

		nameToBodyMap.put(name, this);
	}

	public CelestialBody(String name, int id, SolarSystem.Body type) {
		this(name);
		this.dimensionId = id;
		this.canLand = true;
		this.type = type;

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

	public CelestialBody withBlockTextures(String stone, String sand, String silt, String sravel) {
		this.stoneTexture = stone;
		return this;
	}

	public CelestialBody withColor(float... color) {
		this.color = color;
		return this;
	}

	public CelestialBody withTidalLockingTo(String name) {
		tidallyLockedTo = name;
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

	public CelestialBody withShader(ResourceLocation fragmentShader) {
		return withShader(fragmentShader, 1);
	}
	
	
	public CelestialBody withShader(ResourceLocation fragmentShader, float scale) {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) return this;

		shader = new Shader(fragmentShader);
		shaderScale = scale;
		return this;
	}

	
	// /Chainables



	// Terraforming - trait overrides
	// If trait overrides exist, delete existing traits from the world, and replace them with the saved ones

	// Prefer statics over instance methods, for performance
	// but if you need to update a _different_ body (like, blowing up the sun)
	// instance members are the go

	public static void setTraits(World world, CelestialBodyTrait... traits) {
		SolarSystemWorldSavedData traitsData = SolarSystemWorldSavedData.get(world);
		
		traitsData.setTraits(getBody(world).name, traits);
	}

	public static void setTraits(World world, Map<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits) {
		setTraits(world, traits.values().toArray(new CelestialBodyTrait[traits.size()]));
	}

	public void setTraits(CelestialBodyTrait... traits) {
		SolarSystemWorldSavedData traitsData = SolarSystemWorldSavedData.get();

		traitsData.setTraits(name, traits);
	}

	public void setTraits(Map<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits) {
		setTraits(traits.values().toArray(new CelestialBodyTrait[traits.size()]));
	}

	// Gets a clone of the body traits that are SAFE for modifying
	public static HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraits(World world) {
		SolarSystemWorldSavedData traitsData = SolarSystemWorldSavedData.get(world);
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> currentTraits = traitsData.getTraits(getBody(world).name);

		if(currentTraits == null) {
			currentTraits = new HashMap<>();
			CelestialBody body = getBody(world);
			for(CelestialBodyTrait trait : body.traits.values()) {
				currentTraits.put(trait.getClass(), trait);
			}
		}

		return currentTraits;
	}

	public HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraits() {
		SolarSystemWorldSavedData traitsData = SolarSystemWorldSavedData.get();
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> currentTraits = traitsData.getTraits(name);

		if(currentTraits == null) {
			currentTraits = new HashMap<>();
			for(CelestialBodyTrait trait : traits.values()) {
				currentTraits.put(trait.getClass(), trait);
			}
		}

		return currentTraits;
	}

	public static void modifyTraits(World world, CelestialBodyTrait... traits) {
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> currentTraits = getTraits(world);
		
		for(CelestialBodyTrait trait : traits) {
			currentTraits.put(trait.getClass(), trait);
		}

		setTraits(world, currentTraits);
	}

	public void modifyTraits(CelestialBodyTrait... traits) {
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> currentTraits = getTraits();
		
		for(CelestialBodyTrait trait : traits) {
			currentTraits.put(trait.getClass(), trait);
		}

		setTraits(currentTraits);
	}

	public static void clearTraits(World world) {
		SolarSystemWorldSavedData traitsData = SolarSystemWorldSavedData.get(world);

		traitsData.clearTraits(getBody(world).name);
	}

	public void clearTraits() {
		SolarSystemWorldSavedData traitsData = SolarSystemWorldSavedData.get();

		traitsData.clearTraits(name);
	}

	// he has ceased to be
	public static void degas(World world) {
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> currentTraits = getTraits(world);

		currentTraits.remove(CBT_Atmosphere.class);

		setTraits(world, currentTraits);
	}

	public static void consumeGas(World world, FluidType fluid, double amount) {
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> currentTraits = getTraits(world);

		CBT_Atmosphere atmosphere = (CBT_Atmosphere) currentTraits.get(CBT_Atmosphere.class);
		if(atmosphere == null) return;

		int emptyIndex = -1;
		for(int i = 0; i < atmosphere.fluids.size(); i++) {
			FluidEntry entry = atmosphere.fluids.get(i);
			if(entry.fluid == fluid) {
				entry.pressure -= amount / AstronomyUtil.MB_PER_ATM;
				emptyIndex = entry.pressure <= 0 ? i : -1;
				break;
			}
		}

		if(emptyIndex >= 0) {
			atmosphere.fluids.remove(emptyIndex);

			if(atmosphere.fluids.size() == 0) {
				currentTraits.remove(CBT_Atmosphere.class);
			}
		}


		setTraits(world, currentTraits);
	}

	public static void emitGas(World world, FluidType fluid, double amount) {
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> currentTraits = getTraits(world);

		CBT_Atmosphere atmosphere = (CBT_Atmosphere) currentTraits.get(CBT_Atmosphere.class);
		if(atmosphere == null) {
			atmosphere = new CBT_Atmosphere();
			currentTraits.put(CBT_Atmosphere.class, atmosphere);
		}

		boolean hasFluid = false;
		for(FluidEntry entry : atmosphere.fluids) {
			if(entry.fluid == fluid) {
				entry.pressure += amount / AstronomyUtil.MB_PER_ATM;
				hasFluid = true;
				break;
			}
		}

		if(!hasFluid) {
			// Sort existing fluids and remove the lowest fraction
			if(atmosphere.fluids.size() >= 8) {
				atmosphere.sortDescending();
				atmosphere.fluids.remove(atmosphere.fluids.size() - 1);
			}

			atmosphere.fluids.add(new FluidEntry(fluid, amount / AstronomyUtil.MB_PER_ATM));
		}

		setTraits(world, currentTraits);
	}

	// /Terraforming



	// Static getters
	// A lot of these are member getters but without having to check the celestial body exists
	// If it doesn't exist, return the overworld as the default, may cause issues with terraforming the overworld

	private static HashMap<Integer, CelestialBody> dimToBodyMap = new HashMap<Integer, CelestialBody>();
	private static HashMap<String, CelestialBody> nameToBodyMap = new HashMap<String, CelestialBody>();

	public static Collection<CelestialBody> getAllBodies() {
		return nameToBodyMap.values();
	}

	public static Collection<CelestialBody> getLandableBodies() {
		return dimToBodyMap.values();
	}

	public static CelestialBody getBody(String name) {
		CelestialBody body = nameToBodyMap.get(name);
		return body != null ? body : dimToBodyMap.get(0);
	}

	public static CelestialBody getBody(int id) {
		CelestialBody body = dimToBodyMap.get(id);
		return body != null ? body : dimToBodyMap.get(0);
	}

	// bit of a dumb one but the other function is already used widely
	public static CelestialBody getBodyOrNull(int id) {
		return dimToBodyMap.get(id);
	}

	public static CelestialBody getBody(World world) {
		return getBody(world.provider.dimensionId);
	}

	public static CelestialBody getStar(World world) {
		return getBody(world).getStar();
	}
	
	public static CelestialBody getPlanet(World world) {
		return getBody(world).getPlanet();
	}

	public static SolarSystem.Body getEnum(World world) {
		return getBody(world).getEnum();
	}

	public static int getMeta(World world) {
		return getBody(world).getEnum().ordinal();
	}

	public static double getRotationalPeriod(World world) {
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

	public static boolean hasDefaultTrait(World world, Class<? extends CelestialBodyTrait> trait) {
		return getBody(world).hasDefaultTrait(trait);
	}
	
	public static <T extends CelestialBodyTrait> T getDefaultTrait(World world, Class<? extends T> trait) {
		return getBody(world).getDefaultTrait(trait);
	}

	// /Statics



	public String getUnlocalizedName() {
		return name;
	}

	public SolarSystem.Body getEnum() {
		return type;
	}

	public CelestialBody getStar() {
		CelestialBody body = this;
		while(body.parent != null)
			body = body.parent;

		return body;
	}

	public CelestialBody getPlanet() {
		if(this.parent == null) return this;
		CelestialBody body = this;
		while(body.parent.parent != null)
			body = body.parent;

		return body;
	}

	// Returns the day length in ticks, adjusted for the 20 minute minecraft day
	public double getRotationalPeriod() {
		return (double)rotationalPeriod * (AstronomyUtil.DAY_FACTOR / (double)AstronomyUtil.TIME_MULTIPLIER) * 20;
	}

	// Returns the year length in days, derived from semi-major axis
	public double getOrbitalPeriod() {
		double semiMajorAxis = semiMajorAxisKm * 1_000;
		double orbitalPeriod = 2 * Math.PI * Math.sqrt((semiMajorAxis * semiMajorAxis * semiMajorAxis) / (AstronomyUtil.GRAVITATIONAL_CONSTANT * parent.massKg));
		return orbitalPeriod / (double)AstronomyUtil.SECONDS_IN_KSP_DAY;
	}

	// Get the gravitational force at the surface, derived from mass and radius
	public float getSurfaceGravity() {
		float radius = radiusKm * 1000;
		return AstronomyUtil.GRAVITATIONAL_CONSTANT * massKg / (radius * radius);
	}

	// Get the power multiplier for sun based machines
	public float getSunPower() {
		float distanceAU = getPlanet().semiMajorAxisKm / AstronomyUtil.KM_IN_AU;
		return 1 / (distanceAU * distanceAU);
	}

	
	public boolean hasTrait(Class<? extends CelestialBodyTrait> trait) {
		return getTraitsUnsafe().containsKey(trait);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends CelestialBodyTrait> T getTrait(Class<? extends T> trait) {
		return (T) getTraitsUnsafe().get(trait);
	}

	// Don't modify traits returned from this!
	private HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraitsUnsafe() {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits;
		if(side == Side.CLIENT) {
			traits = SolarSystemWorldSavedData.getClientTraits(name);
		} else {
			traits = SolarSystemWorldSavedData.get().getTraits(name);
		}

		if(traits != null)
			return traits;
			
		return this.traits;
	}

	public boolean hasDefaultTrait(Class<? extends CelestialBodyTrait> trait) {
		return traits.containsKey(trait);
	}

	@SuppressWarnings("unchecked")
	public <T extends CelestialBodyTrait> T getDefaultTrait(Class<? extends T> trait) {
		return (T) traits.get(trait);
	}

	
	// Loads in the heightmap data for a given chunk
	public int[] getHeightmap(int chunkX, int chunkZ) {
		WorldServer world = DimensionManager.getWorld(dimensionId);

		// Dimension isn't already loaded, try loading it now
		if(world == null) {
			DimensionManager.initDimension(dimensionId);
			world = DimensionManager.getWorld(dimensionId);

			if(world == null) return null;
		}
		
		// Load OR generate the desired chunk
		Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
		return chunk.heightMap;
	}

}
