package com.hbm.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModEntityList {

	private static EntityData[] array = new EntityData[0];
	private static final Map<Integer, Class<? extends Entity>> map = new HashMap<Integer, Class<? extends Entity>>();

	public static List<Integer> eggIdList = new ArrayList<Integer>();
	public static Map<Class<? extends Entity>, Integer> eggIdMap = new HashMap<Class<? extends Entity>, Integer>();

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod) {
		registerEntity(entityClass, entityName, id, mod, 80, 3, true, -1, -1, false);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int eggColor1, int eggColor2) {
		registerEntity(entityClass, entityName, id, mod, 80, 3, true, eggColor1, eggColor2, true);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		registerEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates, -1, -1, false);
	}

	public static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggColor1, int eggColor2) {
		registerEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates, eggColor1, eggColor2, true);
	}

	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggColor1, int eggColor2, boolean hasEgg) {
		EntityRegistry.registerModEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);

		if(id >= array.length) {
			EntityData[] newArray = new EntityData[id + 5];
			System.arraycopy(array, 0, newArray, 0, array.length);
			array = newArray;
		}

		if(array[id] != null)
			throw new IllegalArgumentException("ID " + id + " is already being used! Please report this error!");

		array[id] = new EntityData(entityName, id, eggColor1, eggColor2, hasEgg);
		map.put(id, entityClass);

		if(eggColor1 != -1)
			registerEntityEgg(entityClass, eggColor1, eggColor2);
	}

	public static String getName(int id) {
		EntityData data = getData(id);
		if(data == null)
			return null;

		return RefStrings.MODID + "." + data.name;
	}

	public static EntityData getData(int id) {
		if(id >= array.length)
			return null;

		return array[id];
	}

	public static boolean hasEntitiesWithEggs() {
		for(EntityData data : array) {
			if(data != null && data.hasEgg) return true;
		}

		return false;
	}

	public static Entity createEntityByID(int id, World world) {
		EntityData data = getData(id);

		if(data == null || !data.hasEgg)
			return null;

		try {
			Class<? extends Entity> cls = map.get(id);

			if(cls != null)
				return cls.getConstructor(World.class).newInstance(world);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static EntityData[] getDatasWithEggs() {
		List<EntityData> list = new LinkedList<EntityData>();
		for(Integer id : map.keySet()) {
			EntityData data = getData(id);
			if(data != null && data.hasEgg)
				list.add(data);
		}
		return list.toArray(new EntityData[list.size()]);
	}

	public static int eggIDCounter = 499;

	@SuppressWarnings("unchecked")
	public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor) {
		int id = getUniqueEntityEggId();

		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
		eggIdMap.put(entity, id);
	}

	public static ItemStack getEggFromEntity(Entity entity) {
		return new ItemStack(Items.spawn_egg, 1, eggIdMap.get(entity.getClass()));
	}

	public static int getUniqueEntityEggId() {
		while(EntityList.getClassFromID(++eggIDCounter) != null) {}
		eggIdList.add(eggIDCounter);
		return eggIDCounter;
	}

	public static class EntityData {

		public final String name;
		public final int id, eggColor1, eggColor2;
		public final boolean hasEgg;

		EntityData(String name, int id, int eggColor1, int eggColor2, boolean hasEgg) {
			this.name = name;
			this.id = id;
			this.eggColor1 = eggColor1;
			this.eggColor2 = eggColor2;
			this.hasEgg = hasEgg;
		}
	}

}