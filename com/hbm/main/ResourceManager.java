package com.hbm.main;

import com.hbm.lib.RefStrings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ResourceManager {
	
	////Obj TEs
	
	//Turrets
	public static final IModelCustom turret_heavy_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_heavy_base.obj"));
	public static final IModelCustom turret_heavy_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_heavy_rotor.obj"));
	public static final IModelCustom turret_heavy_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_heavy_gun.obj"));

	public static final IModelCustom turret_rocket_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_rocket_gun.obj"));
	public static final IModelCustom turret_light_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_light_gun.obj"));

}
