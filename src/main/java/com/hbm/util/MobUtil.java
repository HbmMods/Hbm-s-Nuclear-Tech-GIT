package com.hbm.util;

import com.hbm.entity.mob.ai.EntityAIFireGun;
import com.hbm.items.ModItems;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

import java.util.*;

public class MobUtil {


	public static Map<Integer, List<WeightedRandomObject>> slotPoolCommon = new HashMap<>();
	public static Map<Integer, List<WeightedRandomObject>> slotPoolRanged = new HashMap<>();

	public static Map<Integer, List<WeightedRandomObject>> slotPoolAdv = new HashMap<>();
	public static Map<Integer, List<WeightedRandomObject>> slotPoolAdvRanged;
	/**Unlike the above two, the Double is interpreted as minimum soot level, instead of armor slot **/
	public static HashMap<Double, List<WeightedRandomObject>> slotPoolGuns = new HashMap<>();

	//slop pools
	public static Map<Integer, List<WeightedRandomObject>> slotPoolGunsTier1 = new HashMap<>();
	public static Map<Integer, List<WeightedRandomObject>> slotPoolGunsTier2 = new HashMap<>();
	public static Map<Integer, List<WeightedRandomObject>> slotPoolGunsTier3 = new HashMap<>();
	public static Map<Integer, List<WeightedRandomObject>> slotPoolMasks = new HashMap<>();
	public static Map<Integer, List<WeightedRandomObject>> slotPoolHelms = new HashMap<>();
	public static Map<Integer, List<WeightedRandomObject>> slotPoolTierArmor = new HashMap<>();
	public static Map<Integer, List<WeightedRandomObject>> slotPoolMelee = new HashMap<>();

	public static void intializeMobPools(){
		slotPoolCommon.put(4, createSlotPool(8000, new Object[][]{ //new slots, smooth, brushed, no wrinkles // old slots, wrinkled, rusty, not smooth
			{ModItems.gas_mask_m65, 16}, {ModItems.gas_mask_olde, 12}, {ModItems.mask_of_infamy, 8},
			{ModItems.gas_mask_mono, 8}, {ModItems.robes_helmet, 32}, {ModItems.no9, 16},
			{ModItems.cobalt_helmet, 2}, {ModItems.rag_piss, 1}, {ModItems.hat, 1}, {ModItems.alloy_helmet, 2},
			{ModItems.titanium_helmet, 4}, {ModItems.steel_helmet, 8}
		}));
		slotPoolCommon.put(3, createSlotPool(7000, new Object[][]{
			{ModItems.starmetal_plate, 1}, {ModItems.cobalt_plate, 2}, {ModItems.robes_plate, 32},
			{ModItems.jackt, 32}, {ModItems.jackt2, 32}, {ModItems.alloy_plate, 2},
			{ModItems.steel_plate, 2}
		}));
		slotPoolCommon.put(2, createSlotPool(7000, new Object[][]{
			{ModItems.zirconium_legs, 1}, {ModItems.cobalt_legs, 2}, {ModItems.steel_legs, 16},
			{ModItems.titanium_legs, 8}, {ModItems.robes_legs, 32}, {ModItems.alloy_legs, 2}
		}));
		slotPoolCommon.put(1, createSlotPool(7000, new Object[][]{
			{ModItems.robes_boots, 32}, {ModItems.steel_boots, 16}, {ModItems.cobalt_boots, 2}, {ModItems.alloy_boots, 2}
		}));
		slotPoolCommon.put(0, createSlotPool(10000, new Object[][]{
			{ModItems.pipe_lead, 30}, {ModItems.crowbar, 25}, {ModItems.geiger_counter, 20},
			{ModItems.reer_graar, 16}, {ModItems.steel_pickaxe, 12}, {ModItems.stopsign, 10},
			{ModItems.sopsign, 8}, {ModItems.chernobylsign, 6}, {ModItems.steel_sword, 15},
			{ModItems.titanium_sword, 8}, {ModItems.lead_gavel, 4}, {ModItems.wrench_flipped, 2},
			{ModItems.wrench, 20}
		}));

		slotPoolRanged.put(4, createSlotPool(12000, new Object[][]{
			{ModItems.gas_mask_m65, 16}, {ModItems.gas_mask_olde, 12}, {ModItems.mask_of_infamy, 8},
			{ModItems.gas_mask_mono, 8}, {ModItems.robes_helmet, 32}, {ModItems.no9, 16},
			{ModItems.rag_piss, 1}, {ModItems.goggles, 1}, {ModItems.alloy_helmet, 2},
			{ModItems.titanium_helmet, 4}, {ModItems.steel_helmet, 8}
		}));
		slotPoolRanged.put(3, createSlotPool(10000, new Object[][]{
			{ModItems.starmetal_plate, 1}, {ModItems.cobalt_plate, 2}, {ModItems.alloy_plate, 2}, //sadly they cant wear jackets bc it breaks it
			{ModItems.steel_plate, 8}, {ModItems.titanium_plate, 4}
		}));
		slotPoolRanged.put(2, createSlotPool(10000, new Object[][]{
			{ModItems.zirconium_legs, 1}, {ModItems.cobalt_legs, 2}, {ModItems.steel_legs, 16},
			{ModItems.titanium_legs, 8}, {ModItems.robes_legs, 32}, {ModItems.alloy_legs, 2},
		}));
		slotPoolRanged.put(1, createSlotPool(10000, new Object[][]{
			{ModItems.robes_boots, 32}, {ModItems.steel_boots, 16}, {ModItems.cobalt_boots, 2}, {ModItems.alloy_boots, 2},
			{ModItems.titanium_boots, 6}
		}));

		slotPoolGuns.put(0.3, createSlotPool(new Object[][]{
			{ModItems.gun_light_revolver, 16}, {ModItems.gun_greasegun, 8}, {ModItems.gun_maresleg, 2}
		}));
		slotPoolGuns.put(1D, createSlotPool(new Object[][]{
			{ModItems.gun_light_revolver, 6}, {ModItems.gun_greasegun, 8}, {ModItems.gun_maresleg, 4}, {ModItems.gun_henry, 6}
		}));
		slotPoolGuns.put(3D, createSlotPool(new Object[][]{
			{ModItems.gun_uzi, 10}, {ModItems.gun_maresleg, 8}, {ModItems.gun_henry, 12}, {ModItems.gun_heavy_revolver, 4}, {ModItems.gun_flaregun, 2}
		}));
		slotPoolGuns.put(5D, createSlotPool(new Object[][]{
			{ModItems.gun_am180, 6}, {ModItems.gun_uzi, 10}, {ModItems.gun_spas12, 8}, {ModItems.gun_henry_lincoln, 2}, {ModItems.gun_heavy_revolver, 12}, {ModItems.gun_flaregun, 4}, {ModItems.gun_flamer, 2}
		}));

		slotPoolAdv.put(4, createSlotPool(new Object[][]{
			{ModItems.security_helmet, 10}, {ModItems.t51_helmet, 4}, {ModItems.asbestos_helmet, 12},
			{ModItems.liquidator_helmet, 4}, {ModItems.no9, 12},
			{ModItems.hazmat_helmet, 6}
		}));
		slotPoolAdv.put(3, createSlotPool(new Object[][]{
			{ModItems.liquidator_plate, 4}, {ModItems.security_plate, 8}, {ModItems.asbestos_plate, 12},
			{ModItems.t51_plate, 4}, {ModItems.hazmat_plate, 6},
			{ModItems.steel_plate, 8}
		}));
		slotPoolAdv.put(2, createSlotPool(new Object[][]{
			{ModItems.liquidator_legs, 4}, {ModItems.security_legs, 8}, {ModItems.asbestos_legs, 12},
			{ModItems.t51_legs, 4}, {ModItems.hazmat_legs, 6},
			{ModItems.steel_legs, 8}
		}));
		slotPoolAdv.put(1, createSlotPool(new Object[][]{
			{ModItems.liquidator_boots, 4}, {ModItems.security_boots, 8}, {ModItems.asbestos_boots, 12},
			{ModItems.t51_boots, 4}, {ModItems.hazmat_boots, 6},
			{ModItems.robes_boots, 8}
		}));
		slotPoolAdv.put(0, createSlotPool(new Object[][]{
			{ModItems.pipe_lead, 20}, {ModItems.crowbar, 30}, {ModItems.geiger_counter, 20},
			{ModItems.reer_graar, 20}, {ModItems.wrench_flipped, 12}, {ModItems.stopsign, 16},
			{ModItems.sopsign, 4}, {ModItems.chernobylsign, 16},
			{ModItems.titanium_sword, 18}, {ModItems.lead_gavel, 8},
			{ModItems.wrench, 20}
		}));

		//For action block
		slotPoolGunsTier1.put(0, createSlotPool(0, new Object[][]{
			{ModItems.gun_light_revolver, 16}, {ModItems.gun_greasegun, 8}, {ModItems.gun_maresleg, 2}
		}));

		slotPoolGunsTier2.put(0, createSlotPool(0, new Object[][]{
			{ModItems.gun_uzi, 10}, {ModItems.gun_maresleg, 8}, {ModItems.gun_henry, 12}, {ModItems.gun_heavy_revolver, 4}, {ModItems.gun_flaregun, 4}, {ModItems.gun_carbine, 4}
		}));

		slotPoolGunsTier3.put(0, createSlotPool(0, new Object[][]{
			{ModItems.gun_uzi, 25}, {ModItems.gun_spas12, 20}, {ModItems.gun_carbine, 20}, {ModItems.gun_g3, 10}, {ModItems.gun_am180, 5}, {ModItems.gun_stg77, 5}
		}));

		slotPoolMasks.put(4, createSlotPool(0, new Object[][]{
			{ModItems.gas_mask_m65, 16}, {ModItems.gas_mask_mono, 8}, {ModItems.robes_helmet, 32}, {ModItems.no9, 16},
			{ModItems.rag_piss, 4}, {ModItems.goggles, 12}
		}));

		slotPoolHelms.put(4, createSlotPool(0, new Object[][]{
			{ModItems.gas_mask_m65, 16}, {ModItems.gas_mask_olde, 12}, {ModItems.mask_of_infamy, 8},
			{ModItems.gas_mask_mono, 8}, {ModItems.robes_helmet, 32}, {ModItems.no9, 16},
			{ModItems.cobalt_helmet, 2}, {ModItems.hat, 1}, {ModItems.alloy_helmet, 2},
			{ModItems.titanium_helmet, 4}, {ModItems.steel_helmet, 8}
		}));

		slotPoolTierArmor.put(4, createSlotPool(new Object[][]{
			{ModItems.gas_mask_m65, 20},
			{ModItems.gas_mask_olde, 15},
			{ModItems.steel_helmet, 25},
			{ModItems.titanium_helmet, 15},
			{ModItems.alloy_helmet, 10},
		}));

		slotPoolTierArmor.put(3, createSlotPool(new Object[][]{
			{ModItems.steel_plate, 30},
			{ModItems.titanium_plate, 20},
			{ModItems.alloy_plate, 15},
			{ModItems.cobalt_plate, 5},
			{ModItems.starmetal_plate, 5}
		}));

		slotPoolTierArmor.put(2, createSlotPool(new Object[][]{
			{ModItems.steel_legs, 30},
			{ModItems.titanium_legs, 20},
			{ModItems.alloy_legs, 15},
			{ModItems.cobalt_legs, 5},
			{ModItems.zirconium_legs, 5}
		}));

		slotPoolTierArmor.put(1, createSlotPool(new Object[][]{
			{ModItems.steel_boots, 30},
			{ModItems.robes_boots, 25},
			{ModItems.titanium_boots, 20},
			{ModItems.alloy_boots, 15},
			{ModItems.hazmat_boots, 10},
			{ModItems.cobalt_boots, 5},
		}));

		slotPoolMelee.put(0, createSlotPool(2000, new Object[][]{
			{ModItems.pipe_lead, 40}, {ModItems.crowbar, 35}, {ModItems.wrench, 30},
			{ModItems.steel_sword, 25}, {ModItems.titanium_sword, 20},
			{ModItems.reer_graar, 20}, {ModItems.stopsign, 15},
			{ModItems.lead_gavel, 12}, {ModItems.wrench_flipped, 10},
			{ModItems.sopsign, 8}, {ModItems.chernobylsign, 8}
		}));

		slotPoolAdvRanged = new HashMap<>(slotPoolAdv);
		slotPoolAdvRanged.remove(0);

	}

	public static List<WeightedRandomObject> createSlotPool(int nullWeight, Object[][] items) {
		List<WeightedRandomObject> pool = new ArrayList<>();
		pool.add(new WeightedRandomObject(null, nullWeight));
		for (Object[] item : items) {
			Object obj = item[0];
			int weight = (int) item[1];

			if (obj instanceof Item) {
				pool.add(new WeightedRandomObject(new ItemStack((Item) obj), weight));
			} else if (obj instanceof ItemStack) {		//lol just make it pass ItemStack aswell
				pool.add(new WeightedRandomObject(obj, weight));
			}
		}
		return pool;
	}
	public static List<WeightedRandomObject> createSlotPool(Object[][] items) {
		List<WeightedRandomObject> pool = new ArrayList<>();
		for (Object[] item : items) {
			Object obj = item[0];
			int weight = (int) item[1];

			if (obj instanceof Item) {
				pool.add(new WeightedRandomObject(new ItemStack((Item) obj), weight));
			} else if (obj instanceof ItemStack) {		//lol just make it pass ItemStack aswell
				pool.add(new WeightedRandomObject(obj, weight));
			}
		}
		return pool;
	}

	public static void equipFullSet(EntityLivingBase entity, Item helmet, Item chest, Item legs, Item boots) { //for brainlets (me) to add more armorsets later when i forget about how this works
		entity.setCurrentItemOrArmor(4, new ItemStack(helmet)); //p_70062_1_ is the slot number
		entity.setCurrentItemOrArmor(3, new ItemStack(chest));
		entity.setCurrentItemOrArmor(2, new ItemStack(legs));
		entity.setCurrentItemOrArmor(1, new ItemStack(boots));
	}

	public static void assignItemsToEntity(EntityLivingBase entity, Map<Integer, List<WeightedRandomObject>> slotPools, Random rand) {
		for (Map.Entry<Integer, List<WeightedRandomObject>> entry : slotPools.entrySet()) {
			int slot = entry.getKey();
			List<WeightedRandomObject> pool = entry.getValue();

			WeightedRandomObject choice = (WeightedRandomObject) WeightedRandom.getRandomItem(rand, pool); //NullPointerException sludge fix
			if (choice == null) {
				continue;
			}

			ItemStack stack = choice.asStack();
			if (stack == null || stack.getItem() == null) {
				continue;
			}

			if (stack.getItem() == ModItems.gas_mask_m65 //eyesore
				|| stack.getItem() == ModItems.gas_mask_olde
				|| stack.getItem() == ModItems.gas_mask_mono) {
				ArmorUtil.installGasMaskFilter(stack, new ItemStack(ModItems.gas_mask_filter));
			}

			entity.setCurrentItemOrArmor(slot, stack);

			//Give skeleton AI if it has a gun
			if (slot == 0 && entity instanceof EntitySkeleton && pool == slotPools.get(0)) {
				addFireTask((EntityLiving) entity);
			}
		}
	}

	// these fucking tasks keep stacking on top of themselves
	public static void addFireTask(EntityLiving entity) {
		entity.setEquipmentDropChance(0, 0); // Prevent dropping guns

		for(Object entry : entity.tasks.taskEntries) {
			EntityAITasks.EntityAITaskEntry task = (EntityAITasks.EntityAITaskEntry) entry;
			if(task.action instanceof EntityAIFireGun) return;
		}

		entity.tasks.addTask(3, new EntityAIFireGun(entity));
	}
}
