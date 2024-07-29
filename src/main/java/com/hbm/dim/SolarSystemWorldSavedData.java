package com.hbm.dim;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.dim.trait.CelestialBodyTrait;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

public class SolarSystemWorldSavedData extends WorldSavedData {

	/**
	 * Similar to CelestialBodyWorldSavedData but for anything that must be visible from
	 * other bodies, like atmospheric data or UTTER ANNIHILATION
	 */

	private static final String DATA_NAME = "SolarSystemData";

	public SolarSystemWorldSavedData(String name) {
		super(name);
	}

	private HashMap<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>> traitMap = new HashMap<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>>();
	
	public static SolarSystemWorldSavedData get() {
		return get(DimensionManager.getWorld(0));
	}

	public static SolarSystemWorldSavedData get(World world) {
		SolarSystemWorldSavedData result = (SolarSystemWorldSavedData) world.mapStorage.loadData(SolarSystemWorldSavedData.class, DATA_NAME);
		
		if(result == null) {
			world.mapStorage.setData(DATA_NAME, new SolarSystemWorldSavedData(DATA_NAME));
			result = (SolarSystemWorldSavedData) world.mapStorage.loadData(SolarSystemWorldSavedData.class, DATA_NAME);
		}
		
		return result;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		for(CelestialBody body : CelestialBody.getAllBodies()) {
			if(nbt.hasKey("b_" + body.name)) {
				NBTTagCompound data = nbt.getCompoundTag("b_" + body.name);
				HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits = new HashMap<>();

				for(Entry<String, Class<? extends CelestialBodyTrait>> entry : CelestialBodyTrait.traitMap.entrySet()) {
					if(data.hasKey(entry.getKey())) {
						try {
							CelestialBodyTrait trait = entry.getValue().newInstance();
							trait.readFromNBT(data.getCompoundTag(entry.getKey()));
							traits.put(trait.getClass(), trait);
						} catch (Exception ex) {}
					}
				}

				traitMap.put(body.name, traits);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		for(Entry<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>> entry : traitMap.entrySet()) {
			NBTTagCompound data = new NBTTagCompound();

			for(CelestialBodyTrait trait : entry.getValue().values()) {
				String name = CelestialBodyTrait.traitMap.inverse().get(trait.getClass());
				NBTTagCompound traitData = new NBTTagCompound();
				trait.writeToNBT(traitData);
				data.setTag(name, traitData);
			}
	
			nbt.setTag("b_" + entry.getKey(), data);
		}
	}

	public void setTraits(String bodyName, CelestialBodyTrait... traits) {
		if(traits.length == 0) {
			clearTraits(bodyName);
			return;
		}

		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> newTraits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();
		for(CelestialBodyTrait trait : traits) {
			newTraits.put(trait.getClass(), trait);
		}

		this.traitMap.put(bodyName, newTraits);
		
		markDirty();
	}

	public void clearTraits(String bodyName) {
		this.traitMap.remove(bodyName);
		
		markDirty();
	}

	public HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getTraits(String bodyName) {
		return traitMap.get(bodyName);
	}


	// Client sync
	private static HashMap<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>> clientTraits = new HashMap<>();

	@SideOnly(Side.CLIENT)
	public static void updateClientTraits(HashMap<String, HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>> traits) {
		clientTraits = traits;
		if(clientTraits == null) clientTraits = new HashMap<>();
	}

	@SideOnly(Side.CLIENT)
	public static HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> getClientTraits(String bodyName) {
		return clientTraits.get(bodyName);
	}
	
}
