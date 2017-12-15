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
	public static final IModelCustom turret_flamer_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_flamer_gun.obj"));
	public static final IModelCustom turret_tau_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_tau_gun.obj"));
	
	//Pumpjack
	public static final IModelCustom pumpjack_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/pumpjack_base.obj"));
	public static final IModelCustom pumpjack_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/pumpjack_head.obj"));
	public static final IModelCustom pumpjack_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/pumpjack_rotor.obj"));
	
	//Turbofan
	public static final IModelCustom turbofan_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turbofan_body.obj"));
	public static final IModelCustom turbofan_blades = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turbofan_blades.obj"));
	
	//Press
	public static final IModelCustom press_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/press_body.obj"));
	public static final IModelCustom press_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/press_head.obj"));
	
	//Anti Mass Spectrometer
	public static final IModelCustom ams_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_base.obj"));
	public static final IModelCustom ams_emitter = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_emitter.obj"));
	public static final IModelCustom ams_emitter_destroyed = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_emitter_destroyed.obj"));
	public static final IModelCustom ams_limiter = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_limiter.obj"));
	public static final IModelCustom ams_limiter_destroyed = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_limiter_destroyed.obj"));
	
	//Sphere
	public static final IModelCustom sphere_ruv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_ruv.obj"));
	public static final IModelCustom sphere_iuv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_iuv.obj"));
	
	////Textures TEs
	
	public static final ResourceLocation universal = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_.png");

	public static final ResourceLocation turret_heavy_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_heavy_base.png");
	
	public static final ResourceLocation turret_heavy_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_heavy_rotor.png");
	public static final ResourceLocation turret_heavy_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_heavy_gun.png");
	public static final ResourceLocation turret_light_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_light_rotor.png");
	public static final ResourceLocation turret_light_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_light_gun.png");
	public static final ResourceLocation turret_rocket_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_rocket_rotor.png");
	public static final ResourceLocation turret_rocket_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_rocket_gun.png");
	public static final ResourceLocation turret_flamer_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_flamer_rotor.png");
	public static final ResourceLocation turret_flamer_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_flamer_gun.png");
	public static final ResourceLocation turret_tau_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_tau_rotor.png");
	public static final ResourceLocation turret_tau_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_tau_gun.png");
	
	//Pumpjack
	public static final ResourceLocation pumpjack_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_base.png");
	public static final ResourceLocation pumpjack_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_head.png");
	public static final ResourceLocation pumpjack_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_rotor.png");
	
	//Pumpjack
	public static final ResourceLocation turbofan_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turbofan_body.png");
	public static final ResourceLocation turbofan_blades_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turbofan_blades.png");
	
	//Press
	public static final ResourceLocation press_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/press_body.png");
	public static final ResourceLocation press_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/press_head.png");
	
	//Anti Mass Spectrometer
	public static final ResourceLocation ams_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_base.png");
	public static final ResourceLocation ams_emitter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_emitter.png");
	public static final ResourceLocation ams_limiter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_limiter.png");

	////Obj Items
	
	//Shimmer Sledge
	public static final IModelCustom shimmer_sledge = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/shimmer_sledge.obj"));
	
	////Texture Items

	//Shimmer Sledge
	public static final ResourceLocation shimmer_sledge_tex = new ResourceLocation(RefStrings.MODID, "textures/models/shimmer_sledge.png");
	
	////Obj Entities
	
	//Boxcar
	public static final IModelCustom boxcar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/boxcar.obj"));
	
	////Texture Entities
	
	//Boxcar
	public static final ResourceLocation boxcar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/boxcar.png");
}
