package com.hbm.main;

import com.hbm.lib.RefStrings;
import com.hbm.render.loader.HFRWavefrontObject;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ResourceManager {

	//God
	public static final IModelCustom error = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/error.obj"));
	
	////Obj TEs
	
	//Turrets
	public static final IModelCustom turret_heavy_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_heavy_base.obj"));
	public static final IModelCustom turret_heavy_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_heavy_rotor.obj"));

	public static final IModelCustom turret_spitfire_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_spitfire_base.obj"));
	public static final IModelCustom turret_spitfire_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_spitfire_rotor.obj"));
	
	public static final IModelCustom turret_cwis_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cwis_base.obj"));
	public static final IModelCustom turret_cwis_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cwis_rotor.obj"));
	
	public static final IModelCustom turret_cheapo_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_cheapo_base.obj"));
	public static final IModelCustom turret_cheapo_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_cheapo_rotor.obj"));
	
	public static final IModelCustom turret_heavy_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_heavy_gun.obj"));
	public static final IModelCustom turret_rocket_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_rocket_gun.obj"));
	public static final IModelCustom turret_light_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_light_gun.obj"));
	public static final IModelCustom turret_flamer_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_flamer_gun.obj"));
	public static final IModelCustom turret_tau_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_tau_gun.obj"));
	public static final IModelCustom turret_spitfire_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_spitfire_gun.obj"));
	public static final IModelCustom turret_cwis_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cwis_head.obj"));
	public static final IModelCustom turret_cwis_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cwis_gun.obj"));
	public static final IModelCustom turret_cheapo_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_cheapo_head.obj"));
	public static final IModelCustom turret_cheapo_gun = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turret_cheapo_gun.obj"));
	
	//Landmines
	public static final IModelCustom mine_ap = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mine_ap.obj"));
	public static final IModelCustom mine_he = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mine_he.obj"));
	public static final IModelCustom mine_fat = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mine_fat.obj"));
	
	//Pumpjack
	public static final IModelCustom pumpjack_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/pumpjack_base.obj"));
	public static final IModelCustom pumpjack_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/pumpjack_head.obj"));
	public static final IModelCustom pumpjack_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/pumpjack_rotor.obj"));
	
	//Turbofan
	public static final IModelCustom turbofan_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turbofan_body.obj"));
	public static final IModelCustom turbofan_blades = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turbofan_blades.obj"));
	
	//Selenium Engine
	public static final IModelCustom selenium_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/selenium_engine_body.obj"));
	public static final IModelCustom selenium_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/selenium_engine_rotor.obj"));
	public static final IModelCustom selenium_piston = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/selenium_engine_piston.obj"));
	
	//Press
	public static final IModelCustom press_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/press_body.obj"));
	public static final IModelCustom press_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/press_head.obj"));
	public static final IModelCustom epress_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/epress_body.obj"));
	public static final IModelCustom epress_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/epress_head.obj"));
	
	//Chemplant
	public static final IModelCustom chemplant_new = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/chemplant_main_new.obj"));
	
	//Centrifuge
	public static final IModelCustom centrifuge_new = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/centrifuge_new.obj"));
	
	//Anti Mass Spectrometer
	public static final IModelCustom ams_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_base.obj"));
	public static final IModelCustom ams_emitter = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_emitter.obj"));
	public static final IModelCustom ams_emitter_destroyed = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_emitter_destroyed.obj"));
	public static final IModelCustom ams_limiter = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_limiter.obj"));
	public static final IModelCustom ams_limiter_destroyed = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ams_limiter_destroyed.obj"));
	
	//Dark Matter Core
	public static final IModelCustom dfc_emitter = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/core_emitter.obj"));
	public static final IModelCustom dfc_receiver = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/core_receiver.obj"));
	public static final IModelCustom dfc_injector = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/core_injector.obj"));
	
	//Sphere
	public static final IModelCustom sphere_ruv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_ruv.obj"));
	public static final IModelCustom sphere_iuv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_iuv.obj"));
	public static final IModelCustom sphere_uv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_uv.obj"));
	public static final IModelCustom sphere_uv_anim = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_uv.hmf"));
	
	//Radgen
	public static final IModelCustom radgen_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rad_gen_body.obj"));
	public static final IModelCustom radgen_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rad_gen_rotor.obj"));
	
	//Small Reactor
	public static final IModelCustom reactor_small_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/reactor_small_base.obj"));
	public static final IModelCustom reactor_small_rods = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/reactor_small_rods.obj"));
	
	//Radar
	public static final IModelCustom radar_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/radar_base.obj"));
	public static final IModelCustom radar_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/radar_head.obj"));
	
	//Forcefield
	public static final IModelCustom forcefield_top = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/forcefield_top.obj"));
	
	//Shredder
	public static final IModelCustom shredder_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/shredder_body.obj"));
	public static final IModelCustom shredder_blade = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/shredder_blade.obj"));
	
	//Bombs
	public static final IModelCustom bomb_solinium = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/ufp.obj"));
	public static final IModelCustom n2 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/n2.obj"));
	public static final IModelCustom n45_globe = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/n45_globe.obj"));
	public static final IModelCustom n45_knob = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/n45_knob.obj"));
	public static final IModelCustom n45_rod = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/n45_rod.obj"));
	public static final IModelCustom n45_stand = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/n45_stand.obj"));
	public static final IModelCustom n45_chain = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/n45_chain.obj"));
	
	//Cel-Prime
	public static final IModelCustom cp_tower = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cel_prime_tower.obj"));
	public static final IModelCustom cp_terminal = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cel_prime_terminal.obj"));
	public static final IModelCustom cp_battery = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cel_prime_battery.obj"));
	public static final IModelCustom cp_tanks = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cel_prime_tanks.obj"));
	public static final IModelCustom cp_port = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/cel_prime_port.obj"));

	//Satellites
	public static final IModelCustom sat_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_base.obj"));
	public static final IModelCustom sat_radar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_radar.obj"));
	public static final IModelCustom sat_resonator = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_resonator.obj"));
	public static final IModelCustom sat_scanner = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_scanner.obj"));
	public static final IModelCustom sat_mapper = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_mapper.obj"));
	public static final IModelCustom sat_laser = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_laser.obj"));
	public static final IModelCustom sat_foeq = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_foeq.obj"));
	public static final IModelCustom sat_foeq_burning = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_foeq_burning.obj"));
	public static final IModelCustom sat_foeq_fire = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_foeq_fire.obj"));

	//SatDock
	public static final IModelCustom satDock = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_dock.obj"));
	
	//Vault Door
	public static final IModelCustom vault_cog = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vault_cog.obj"));
	public static final IModelCustom vault_frame = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vault_frame.obj"));
	public static final IModelCustom vault_teeth = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vault_teeth.obj"));
	public static final IModelCustom vault_label = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vault_label.obj"));
	
	//Blast Door
	public static final IModelCustom blast_door_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blast_door_base.obj"));
	public static final IModelCustom blast_door_tooth = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blast_door_tooth.obj"));
	public static final IModelCustom blast_door_slider = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blast_door_slider.obj"));
	public static final IModelCustom blast_door_block = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blast_door_block.obj"));
	
	//Tesla Coil
	public static final IModelCustom tesla = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/tesla.obj"));
	
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
	public static final ResourceLocation turret_ciws_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cwis_base.png");
	public static final ResourceLocation turret_ciws_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cwis_rotor.png");
	public static final ResourceLocation turret_ciws_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cwis_head.png");
	public static final ResourceLocation turret_ciws_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cwis_gun.png");
	public static final ResourceLocation turret_cheapo_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_cheapo_base.png");
	public static final ResourceLocation turret_cheapo_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_cheapo_rotor.png");
	public static final ResourceLocation turret_cheapo_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_cheapo_head.png");
	public static final ResourceLocation turret_cheapo_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_cheapo_gun.png");

	//Landmines
	public static final ResourceLocation mine_ap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_ap.png");
	public static final ResourceLocation mine_he_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_he.png");
	public static final ResourceLocation mine_shrap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_shrap.png");
	public static final ResourceLocation mine_fat_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_fat.png");
	
	//Pumpjack
	public static final ResourceLocation pumpjack_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_base.png");
	public static final ResourceLocation pumpjack_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_head.png");
	public static final ResourceLocation pumpjack_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_rotor.png");
	
	//Pumpjack
	public static final ResourceLocation turbofan_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turbofan_body.png");
	public static final ResourceLocation turbofan_blades_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turbofan_blades.png");
	
	//Selenium Engine
	public static final ResourceLocation selenium_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_body.png");
	public static final ResourceLocation selenium_piston_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_piston.png");
	public static final ResourceLocation selenium_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_rotor.png");
	
	//Press
	public static final ResourceLocation press_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/press_body.png");
	public static final ResourceLocation press_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/press_head.png");
	public static final ResourceLocation epress_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/epress_body.png");
	public static final ResourceLocation epress_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/epress_head.png");
	
	//Chemplant
	public static final ResourceLocation chemplant_new_tex = new ResourceLocation(RefStrings.MODID, "textures/models/chemplant_main_new.png");
	
	//Centrifuge
	public static final ResourceLocation centrifuge_new_tex = new ResourceLocation(RefStrings.MODID, "textures/models/centrifuge_new.png");
	public static final ResourceLocation centrifuge_gas_tex = new ResourceLocation(RefStrings.MODID, "textures/models/centrifuge_gas.png");
	
	//Anti Mass Spectrometer
	public static final ResourceLocation ams_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_base.png");
	public static final ResourceLocation ams_emitter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_emitter.png");
	public static final ResourceLocation ams_limiter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_limiter.png");
	public static final ResourceLocation ams_destroyed_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_destroyed.png");
	
	//Dark Matter Core
	public static final ResourceLocation dfc_emitter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/core_emitter.png");
	public static final ResourceLocation dfc_receiver_tex = new ResourceLocation(RefStrings.MODID, "textures/models/core_receiver.png");
	public static final ResourceLocation dfc_injector_tex = new ResourceLocation(RefStrings.MODID, "textures/models/core_injector.png");
	public static final ResourceLocation dfc_stabilizer_tex = new ResourceLocation(RefStrings.MODID, "textures/models/core_stabilizer.png");
	
	//Radgen
	public static final ResourceLocation radgen_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/rad_gen_body.png");
	
	//Small Reactor
	public static final ResourceLocation reactor_small_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/reactor_small_base.png");
	public static final ResourceLocation reactor_small_rods_tex = new ResourceLocation(RefStrings.MODID, "textures/models/reactor_small_rods.png");
	
	//Radar
	public static final ResourceLocation radar_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_base.png");
	public static final ResourceLocation radar_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_head.png");
	
	//Forcefield
	public static final ResourceLocation forcefield_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_base.png");
	public static final ResourceLocation forcefield_top_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_top.png");
	
	//Bombs
	public static final ResourceLocation bomb_solinium_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ufp.png");
	public static final ResourceLocation n2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n2.png");
	public static final ResourceLocation n45_globe_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_globe.png");
	public static final ResourceLocation n45_knob_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_knob.png");
	public static final ResourceLocation n45_rod_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_rod.png");
	public static final ResourceLocation n45_stand_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_stand.png");
	public static final ResourceLocation n45_chain_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_chain.png");
	
	//Satellites
	public static final ResourceLocation sat_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_base.png");
	public static final ResourceLocation sat_radar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_radar.png");
	public static final ResourceLocation sat_resonator_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_resonator.png");
	public static final ResourceLocation sat_scanner_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_scanner.png");
	public static final ResourceLocation sat_mapper_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_mapper.png");
	public static final ResourceLocation sat_laser_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_laser.png");
	public static final ResourceLocation sat_foeq_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_foeq.png");
	public static final ResourceLocation sat_foeq_burning_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_foeq_burning.png");
	
	//SatDock
	public static final ResourceLocation satdock_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_dock.png");
	
	//Vault Door
	public static final ResourceLocation vault_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_cog.png");
	public static final ResourceLocation vault_frame_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_frame.png");
	public static final ResourceLocation vault_label_101_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_101.png");
	public static final ResourceLocation vault_label_87_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_87.png");
	public static final ResourceLocation vault_label_106_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_106.png");
	public static final ResourceLocation stable_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_cog.png");
	public static final ResourceLocation stable_label_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_label.png");
	public static final ResourceLocation stable_label_99_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_label_99.png");
	public static final ResourceLocation vault4_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_cog.png");
	public static final ResourceLocation vault4_label_111_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_label_111.png");
	public static final ResourceLocation vault4_label_81_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_label_81.png");
	
	//Blast Door
	public static final ResourceLocation blast_door_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_base.png");
	public static final ResourceLocation blast_door_tooth_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_tooth.png");
	public static final ResourceLocation blast_door_slider_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_slider.png");
	public static final ResourceLocation blast_door_block_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_block.png");

	//Tesla Coil
	public static final ResourceLocation tesla_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tesla.png");

	
	
	////Obj Items
	
	//Shimmer Sledge
	public static final IModelCustom shimmer_sledge = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/shimmer_sledge.obj"));
	public static final IModelCustom shimmer_axe = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/shimmer_axe.obj"));
	public static final IModelCustom stopsign = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/stopsign.obj"));

	public static final IModelCustom brimstone = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/brimstone.obj"));
	public static final IModelCustom hk69 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/hk69.obj"));
	public static final IModelCustom deagle = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/deagle.obj"));
	public static final IModelCustom shotty = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/supershotty.obj"));
	
	////Texture Items

	//Shimmer Sledge
	public static final ResourceLocation shimmer_sledge_tex = new ResourceLocation(RefStrings.MODID, "textures/models/shimmer_sledge.png");
	public static final ResourceLocation shimmer_axe_tex = new ResourceLocation(RefStrings.MODID, "textures/models/shimmer_axe.png");
	public static final ResourceLocation stopsign_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/stopsign.png");
	public static final ResourceLocation sopsign_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/sopsign.png");

	public static final ResourceLocation brimstone_tex = new ResourceLocation(RefStrings.MODID, "textures/models/brimstone.png");
	public static final ResourceLocation hk69_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/hk69.png");
	public static final ResourceLocation deagle_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/deagle.png");
	
	
	
	////Obj Entities
	
	//Boxcar
	public static final IModelCustom boxcar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/boxcar.obj"));
	public static final IModelCustom duchessgambit = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/duchessgambit.obj"));
	public static final IModelCustom building = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/building.obj"));
	public static final IModelCustom rpc = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rpc.obj"));
	public static final IModelCustom tom_main = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/tom_main.obj"));
	public static final IModelCustom tom_flame = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/tom_flame.hmf"));
	
	//Bomber
	public static final IModelCustom dornier = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/dornier.obj"));
	public static final IModelCustom b29 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/b29.obj"));
	
	//Missiles
	public static final IModelCustom missileV2 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileV2.obj"));
	public static final IModelCustom missileStrong = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileGeneric.obj"));
	public static final IModelCustom missileHuge = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileHuge.obj"));
	public static final IModelCustom missileNuclear = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileNeon.obj"));
	public static final IModelCustom missileMIRV = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileMIRV.obj"));
	public static final IModelCustom missileThermo = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileThermo.obj"));
	public static final IModelCustom missileDoomsday = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileDoomsday.obj"));
	public static final IModelCustom missileTaint = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileTaint.obj"));
	public static final IModelCustom missileCarrier = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileCarrier.obj"));
	public static final IModelCustom missileBooster = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileBooster.obj"));
	public static final IModelCustom minerRocket = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/minerRocket.obj"));
	public static final IModelCustom soyuz = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/soyuz.obj"));
	public static final IModelCustom soyuz_lander = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/soyuz_lander.obj"));
	public static final IModelCustom soyuz_launcher_legs = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_legs.obj"));
	public static final IModelCustom soyuz_launcher_table = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_table.obj"));
	public static final IModelCustom soyuz_launcher_tower_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_tower_base.obj"));
	public static final IModelCustom soyuz_launcher_tower = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_tower.obj"));
	public static final IModelCustom soyuz_launcher_support_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_support_base.obj"));
	public static final IModelCustom soyuz_launcher_support = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_support.obj"));
	
	//Missile Parts
	public static final IModelCustom missile_assembly = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_assembly.obj"));
	public static final IModelCustom strut = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/strut.obj"));
	public static final IModelCustom compact_launcher = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/compact_launcher.obj"));
	public static final IModelCustom launch_table_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_base.obj"));
	public static final IModelCustom launch_table_large_pad = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_large_pad.obj"));
	public static final IModelCustom launch_table_small_pad = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_small_pad.obj"));
	public static final IModelCustom launch_table_large_scaffold_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_large_scaffold_base.obj"));
	public static final IModelCustom launch_table_large_scaffold_connector = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_large_scaffold_connector.obj"));
	public static final IModelCustom launch_table_large_scaffold_empty = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_large_scaffold_empty.obj"));
	public static final IModelCustom launch_table_small_scaffold_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_small_scaffold_base.obj"));
	public static final IModelCustom launch_table_small_scaffold_connector = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_small_scaffold_connector.obj"));
	public static final IModelCustom launch_table_small_scaffold_empty = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/launch_table/launch_table_small_scaffold_empty.obj"));

	public static final IModelCustom mp_t_10_kerosene = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_10_kerosene.obj"));
	public static final IModelCustom mp_t_10_kerosene_tec = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_10_kerosene_tec.obj"));
	public static final IModelCustom mp_t_10_solid = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_10_solid.obj"));
	public static final IModelCustom mp_t_10_xenon = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_10_xenon.obj"));
	public static final IModelCustom mp_t_15_kerosene = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_kerosene.obj"));
	public static final IModelCustom mp_t_15_kerosene_tec = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_kerosene_tec.obj"));
	public static final IModelCustom mp_t_15_kerosene_dual = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_kerosene_dual.obj"));
	public static final IModelCustom mp_t_15_kerosene_triple = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_kerosene_triple.obj"));
	public static final IModelCustom mp_t_15_solid = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_solid.obj"));
	public static final IModelCustom mp_t_15_solid_hexdecuple = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_solid_hexdecuple.obj"));
	public static final IModelCustom mp_t_15_balefire_short = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_balefire_short.obj"));
	public static final IModelCustom mp_t_15_balefire = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_balefire.obj"));
	public static final IModelCustom mp_t_15_balefire_large = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_15_balefire_large.obj"));
	public static final IModelCustom mp_t_20_kerosene = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_20_kerosene.obj"));
	public static final IModelCustom mp_t_20_kerosene_dual = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_20_kerosene_dual.obj"));
	public static final IModelCustom mp_t_20_kerosene_triple = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_20_kerosene_triple.obj"));
	public static final IModelCustom mp_t_20_solid = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_20_solid.obj"));
	public static final IModelCustom mp_t_20_solid_multi = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_t_20_solid_multi.obj"));

	public static final IModelCustom mp_s_10_flat = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_s_10_flat.obj"));
	public static final IModelCustom mp_s_10_cruise = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_s_10_cruise.obj"));
	public static final IModelCustom mp_s_10_space = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_s_10_space.obj"));
	public static final IModelCustom mp_s_15_flat = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_s_15_flat.obj"));
	public static final IModelCustom mp_s_15_thin = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_s_15_thin.obj"));
	public static final IModelCustom mp_s_15_soyuz = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_s_15_soyuz.obj"));
	public static final IModelCustom mp_s_20 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_s_20.obj"));

	public static final IModelCustom mp_f_10_kerosene = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_f_10_kerosene.obj"));
	public static final IModelCustom mp_f_10_long_kerosene = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_f_10_long_kerosene.obj"));
	public static final IModelCustom mp_f_10_15_kerosene = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_f_10_15_kerosene.obj"));
	public static final IModelCustom mp_f_15_kerosene = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_f_15_kerosene.obj"));
	public static final IModelCustom mp_f_15_hydrogen = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_f_15_hydrogen.obj"));
	public static final IModelCustom mp_f_15_20_kerosene = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_f_15_20_kerosene.obj"));
	public static final IModelCustom mp_f_20 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_f_20.obj"));
	
	public static final IModelCustom mp_w_10_he = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_10_he.obj"));
	public static final IModelCustom mp_w_10_incendiary = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_10_incendiary.obj"));
	public static final IModelCustom mp_w_10_buster = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_10_buster.obj"));
	public static final IModelCustom mp_w_10_nuclear = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_10_nuclear.obj"));
	public static final IModelCustom mp_w_10_nuclear_large = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_10_nuclear_large.obj"));
	public static final IModelCustom mp_w_10_taint = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_10_taint.obj"));
	public static final IModelCustom mp_w_15_he = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_15_he.obj"));
	public static final IModelCustom mp_w_15_incendiary = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_15_incendiary.obj"));
	public static final IModelCustom mp_w_15_nuclear = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_15_nuclear.obj"));
	public static final IModelCustom mp_w_15_boxcar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_15_boxcar.obj"));
	public static final IModelCustom mp_w_15_n2 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_15_n2.obj"));
	public static final IModelCustom mp_w_15_balefire = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_15_balefire.obj"));
	public static final IModelCustom mp_w_20 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_20.obj"));
	
	////Texture Entities
	
	//Blast
	public static final ResourceLocation fireball = new ResourceLocation(RefStrings.MODID, "textures/models/fireball.png");
	public static final ResourceLocation balefire = new ResourceLocation(RefStrings.MODID, "textures/models/balefire.png");
	
	//Boxcar
	public static final ResourceLocation boxcar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/boxcar.png");
	public static final ResourceLocation duchessgambit_tex = new ResourceLocation(RefStrings.MODID, "textures/models/duchessgambit.png");
	public static final ResourceLocation building_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/building.png");
	public static final ResourceLocation rpc_tex = new ResourceLocation(RefStrings.MODID, "textures/models/rpc.png");
	public static final ResourceLocation tom_main_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/tom_main.png");
	public static final ResourceLocation tom_flame_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/tom_flame.png");
	
	//Bomber
	public static final ResourceLocation dornier_0_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_0.png");
	public static final ResourceLocation dornier_1_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_1.png");
	public static final ResourceLocation dornier_2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_2.png");
	public static final ResourceLocation dornier_3_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_3.png");
	public static final ResourceLocation dornier_4_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_4.png");
	public static final ResourceLocation b29_0_tex = new ResourceLocation(RefStrings.MODID, "textures/models/b29_0.png");
	public static final ResourceLocation b29_1_tex = new ResourceLocation(RefStrings.MODID, "textures/models/b29_1.png");
	public static final ResourceLocation b29_2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/b29_2.png");
	public static final ResourceLocation b29_3_tex = new ResourceLocation(RefStrings.MODID, "textures/models/b29_3.png");
	
	//Missiles
	public static final ResourceLocation missileV2_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_HE.png");
	public static final ResourceLocation missileV2_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_IN.png");
	public static final ResourceLocation missileV2_CL_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_CL.png");
	public static final ResourceLocation missileV2_BU_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_BU.png");
	public static final ResourceLocation missileAA_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileAA.png");
	public static final ResourceLocation missileStrong_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_HE.png");
	public static final ResourceLocation missileStrong_EMP_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_EMP.png");
	public static final ResourceLocation missileStrong_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_IN.png");
	public static final ResourceLocation missileStrong_CL_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_CL.png");
	public static final ResourceLocation missileStrong_BU_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_BU.png");
	public static final ResourceLocation missileHuge_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileHuge_HE.png");
	public static final ResourceLocation missileHuge_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileHuge_IN.png");
	public static final ResourceLocation missileHuge_CL_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileHuge_CL.png");
	public static final ResourceLocation missileHuge_BU_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileHuge_BU.png");
	public static final ResourceLocation missileNuclear_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileNeon.png");
	public static final ResourceLocation missileMIRV_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileNeonH.png");
	public static final ResourceLocation missileEndo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileEndo.png");
	public static final ResourceLocation missileExo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileExo.png");
	public static final ResourceLocation missileDoomsday_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileDoomsday.png");
	public static final ResourceLocation missileTaint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileTaint.png");
	public static final ResourceLocation missileMicro_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicro.png");
	public static final ResourceLocation missileCarrier_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileCarrier.png");
	public static final ResourceLocation missileBooster_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileBooster.png");
	public static final ResourceLocation minerRocket_tex = new ResourceLocation(RefStrings.MODID, "textures/models/minerRocket.png");
	public static final ResourceLocation bobmazon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bobmazon.png");
	public static final ResourceLocation missileMicroBHole_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicroBHole.png");
	public static final ResourceLocation missileMicroSchrab_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicroSchrab.png");
	public static final ResourceLocation missileMicroEMP_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicroEMP.png");
	
	public static final ResourceLocation soyuz_engineblock = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/engineblock.png");
	public static final ResourceLocation soyuz_bottomstage = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/bottomstage.png");
	public static final ResourceLocation soyuz_topstage = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/topstage.png");
	public static final ResourceLocation soyuz_payload = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/payload.png");
	public static final ResourceLocation soyuz_payloadblocks = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/payloadblocks.png");
	public static final ResourceLocation soyuz_les = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/les.png");
	public static final ResourceLocation soyuz_lesthrusters = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/lesthrusters.png");
	public static final ResourceLocation soyuz_mainengines = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/mainengines.png");
	public static final ResourceLocation soyuz_sideengines = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/sideengines.png");
	public static final ResourceLocation soyuz_booster = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/booster.png");
	public static final ResourceLocation soyuz_boosterside = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/boosterside.png");
	public static final ResourceLocation soyuz_luna_engineblock = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/engineblock.png");
	public static final ResourceLocation soyuz_luna_bottomstage = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/bottomstage.png");
	public static final ResourceLocation soyuz_luna_topstage = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/topstage.png");
	public static final ResourceLocation soyuz_luna_payload = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/payload.png");
	public static final ResourceLocation soyuz_luna_payloadblocks = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/payloadblocks.png");
	public static final ResourceLocation soyuz_luna_les = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/les.png");
	public static final ResourceLocation soyuz_luna_lesthrusters = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/lesthrusters.png");
	public static final ResourceLocation soyuz_luna_mainengines = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/mainengines.png");
	public static final ResourceLocation soyuz_luna_sideengines = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/sideengines.png");
	public static final ResourceLocation soyuz_luna_booster = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/booster.png");
	public static final ResourceLocation soyuz_luna_boosterside = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_luna/boosterside.png");
	public static final ResourceLocation soyuz_authentic_engineblock = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/engineblock.png");
	public static final ResourceLocation soyuz_authentic_bottomstage = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/bottomstage.png");
	public static final ResourceLocation soyuz_authentic_topstage = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/topstage.png");
	public static final ResourceLocation soyuz_authentic_payload = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/payload.png");
	public static final ResourceLocation soyuz_authentic_payloadblocks = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/payloadblocks.png");
	public static final ResourceLocation soyuz_authentic_les = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/les.png");
	public static final ResourceLocation soyuz_authentic_lesthrusters = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/lesthrusters.png");
	public static final ResourceLocation soyuz_authentic_mainengines = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/mainengines.png");
	public static final ResourceLocation soyuz_authentic_sideengines = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/sideengines.png");
	public static final ResourceLocation soyuz_authentic_booster = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/booster.png");
	public static final ResourceLocation soyuz_authentic_boosterside = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_authentic/boosterside.png");
	public static final ResourceLocation soyuz_memento = new ResourceLocation(RefStrings.MODID, "textures/items/polaroid_memento.png");
	
	public static final ResourceLocation soyuz_lander_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_lander.png");
	public static final ResourceLocation soyuz_chute_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_chute.png");

	public static final ResourceLocation soyuz_launcher_legs_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_leg.png");
	public static final ResourceLocation soyuz_launcher_table_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_table.png");
	public static final ResourceLocation soyuz_launcher_tower_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_tower_base.png");
	public static final ResourceLocation soyuz_launcher_tower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_tower.png");
	public static final ResourceLocation soyuz_launcher_support_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_support_base.png");
	public static final ResourceLocation soyuz_launcher_support_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_support.png");
	
	//Missile Parts
	public static final ResourceLocation missile_assembly_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_assembly.png");
	public static final ResourceLocation strut_tex = new ResourceLocation(RefStrings.MODID, "textures/models/strut.png");
	public static final ResourceLocation compact_launcher_tex = new ResourceLocation(RefStrings.MODID, "textures/models/compact_launcher.png");
	public static final ResourceLocation launch_table_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table.png");
	public static final ResourceLocation launch_table_large_pad_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_large_pad.png");
	public static final ResourceLocation launch_table_small_pad_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_small_pad.png");
	public static final ResourceLocation launch_table_large_scaffold_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_large_scaffold_base.png");
	public static final ResourceLocation launch_table_large_scaffold_connector_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_large_scaffold_connector.png");
	public static final ResourceLocation launch_table_small_scaffold_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_small_scaffold_base.png");
	public static final ResourceLocation launch_table_small_scaffold_connector_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_small_scaffold_connector.png");
	
	public static final ResourceLocation mp_t_10_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_10_kerosene.png");
	public static final ResourceLocation mp_t_10_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_10_solid.png");
	public static final ResourceLocation mp_t_10_xenon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_10_xenon.png");
	public static final ResourceLocation mp_t_15_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_kerosene.png");
	public static final ResourceLocation mp_t_15_kerosene_dual_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_kerosene_dual.png");
	public static final ResourceLocation mp_t_15_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_solid.png");
	public static final ResourceLocation mp_t_15_solid_hexdecuple_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_solid_hexdecuple.png");
	public static final ResourceLocation mp_t_15_hydrogen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_hydrogen.png");
	public static final ResourceLocation mp_t_15_hydrogen_dual_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_hydrogen_dual.png");
	public static final ResourceLocation mp_t_15_balefire_short_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_balefire_short.png");
	public static final ResourceLocation mp_t_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_balefire.png");
	public static final ResourceLocation mp_t_15_balefire_large_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_balefire_large.png");
	public static final ResourceLocation mp_t_15_balefire_large_rad_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_balefire_large_rad.png");
	
	public static final ResourceLocation mp_t_20_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_kerosene.png");
	public static final ResourceLocation mp_t_20_kerosene_dual_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_kerosene_dual.png");
	public static final ResourceLocation mp_t_20_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_solid.png");
	public static final ResourceLocation mp_t_20_solid_multi_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_solid_multi.png");
	public static final ResourceLocation mp_t_20_solid_multier_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_solid_multier.png");

	public static final ResourceLocation mp_s_10_flat_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_10_flat.png");
	public static final ResourceLocation mp_s_10_cruise_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_10_cruise.png");
	public static final ResourceLocation mp_s_10_space_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_10_space.png");
	public static final ResourceLocation mp_s_15_flat_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_15_flat.png");
	public static final ResourceLocation mp_s_15_thin_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_15_thin.png");
	public static final ResourceLocation mp_s_15_soyuz_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_15_soyuz.png");

	public static final ResourceLocation mp_f_10_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene.png");
	public static final ResourceLocation mp_f_10_kerosene_camo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_camo.png");
	public static final ResourceLocation mp_f_10_kerosene_desert_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_desert.png");
	public static final ResourceLocation mp_f_10_kerosene_sky_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_sky.png");
	public static final ResourceLocation mp_f_10_kerosene_flames_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_flames.png");
	public static final ResourceLocation mp_f_10_kerosene_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_insulation.png");
	public static final ResourceLocation mp_f_10_kerosene_sleek_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_sleek.png");
	public static final ResourceLocation mp_f_10_kerosene_metal_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_metal.png");
	public static final ResourceLocation mp_f_10_kerosene_taint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_kerosene_taint.png");
	
	public static final ResourceLocation mp_f_10_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid.png");
	public static final ResourceLocation mp_f_10_solid_flames_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_flames.png");
	public static final ResourceLocation mp_f_10_solid_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_insulation.png");
	public static final ResourceLocation mp_f_10_solid_sleek_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_sleek.png");
	public static final ResourceLocation mp_f_10_solid_soviet_glory_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_soviet_glory.png");
	public static final ResourceLocation mp_f_10_solid_moonlit_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_solid_moonlit.png");
	public static final ResourceLocation mp_f_10_solid_cathedral_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_solid_cathedral.png");
	public static final ResourceLocation mp_f_10_solid_battery_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_solid_battery.png");
	public static final ResourceLocation mp_f_10_solid_duracell_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_duracell.png");

	public static final ResourceLocation mp_f_10_xenon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_xenon.png");
	public static final ResourceLocation mp_f_10_xenon_bhole_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_xenon_bhole.png");
	
	public static final ResourceLocation mp_f_10_long_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene.png");
	public static final ResourceLocation mp_f_10_long_kerosene_camo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_camo.png");
	public static final ResourceLocation mp_f_10_long_kerosene_desert_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_desert.png");
	public static final ResourceLocation mp_f_10_long_kerosene_sky_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_sky.png");
	public static final ResourceLocation mp_f_10_long_kerosene_flames_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_flames.png");
	public static final ResourceLocation mp_f_10_long_kerosene_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_insulation.png");
	public static final ResourceLocation mp_f_10_long_kerosene_sleek_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_sleek.png");
	public static final ResourceLocation mp_f_10_long_kerosene_metal_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_metal.png");
	public static final ResourceLocation mp_f_10_long_kerosene_dash_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_kerosene_dash.png");
	public static final ResourceLocation mp_f_10_long_kerosene_taint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_kerosene_taint.png");
	public static final ResourceLocation mp_f_10_long_kerosene_vap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_kerosene_vap.png");
	
	public static final ResourceLocation mp_f_10_long_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid.png");
	public static final ResourceLocation mp_f_10_long_solid_flames_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid_flames.png");
	public static final ResourceLocation mp_f_10_long_solid_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid_insulation.png");
	public static final ResourceLocation mp_f_10_long_solid_sleek_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid_sleek.png");
	public static final ResourceLocation mp_f_10_long_solid_soviet_glory_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid_soviet_glory.png");
	public static final ResourceLocation mp_f_10_long_solid_bullet_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_solid_bullet.png");
	public static final ResourceLocation mp_f_10_long_solid_silvermoonlight_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_solid_silvermoonlight.png");
	
	public static final ResourceLocation mp_f_10_15_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_15_kerosene.png");
	public static final ResourceLocation mp_f_10_15_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_15_solid.png");
	public static final ResourceLocation mp_f_10_15_hydrogen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_15_hydrogen.png");
	public static final ResourceLocation mp_f_10_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_15_balefire.png");
	
	public static final ResourceLocation mp_f_15_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene.png");
	public static final ResourceLocation mp_f_15_kerosene_camo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_camo.png");
	public static final ResourceLocation mp_f_15_kerosene_desert_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_desert.png");
	public static final ResourceLocation mp_f_15_kerosene_sky_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_sky.png");
	public static final ResourceLocation mp_f_15_kerosene_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_insulation.png");
	public static final ResourceLocation mp_f_15_kerosene_metal_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_metal.png");
	public static final ResourceLocation mp_f_15_kerosene_decorated_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_decorated.png");
	public static final ResourceLocation mp_f_15_kerosene_steampunk_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_steampunk.png");
	public static final ResourceLocation mp_f_15_kerosene_polite_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_polite.png");
	public static final ResourceLocation mp_f_15_kerosene_blackjack_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/base/mp_f_15_kerosene_blackjack.png");
	public static final ResourceLocation mp_f_15_kerosene_lambda_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_kerosene_lambda.png");
	public static final ResourceLocation mp_f_15_kerosene_minuteman_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_kerosene_minuteman.png");
	public static final ResourceLocation mp_f_15_kerosene_pip_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_kerosene_pip.png");
	public static final ResourceLocation mp_f_15_kerosene_taint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_kerosene_taint.png");
	public static final ResourceLocation mp_f_15_kerosene_yuck_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_yuck.png");
	
	public static final ResourceLocation mp_f_15_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid.png");
	public static final ResourceLocation mp_f_15_solid_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_insulation.png");
	public static final ResourceLocation mp_f_15_solid_desh_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_desh.png");
	public static final ResourceLocation mp_f_15_solid_soviet_glory_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_soviet_glory.png");
	public static final ResourceLocation mp_f_15_solid_soviet_stank_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_soviet_stank.png");
	public static final ResourceLocation mp_f_15_solid_faust_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_solid_faust.png");
	public static final ResourceLocation mp_f_15_solid_silvermoonlight_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_solid_silvermoonlight.png");
	public static final ResourceLocation mp_f_15_solid_snowy_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_solid_snowy.png");
	public static final ResourceLocation mp_f_15_solid_panorama_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_panorama.png");
	public static final ResourceLocation mp_f_15_solid_roses_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_roses.png");

	public static final ResourceLocation mp_f_15_hydrogen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_hydrogen.png");
	public static final ResourceLocation mp_f_15_hydrogen_cathedral_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_hydrogen_cathedral.png");

	public static final ResourceLocation mp_f_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_balefire.png");

	public static final ResourceLocation mp_f_15_20_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_20_kerosene.png");
	public static final ResourceLocation mp_f_15_20_kerosene_magnusson_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_20_kerosene_magnusson.png");
	public static final ResourceLocation mp_f_15_20_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_20_solid.png");
	
	public static final ResourceLocation mp_w_10_he_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_he.png");
	public static final ResourceLocation mp_w_10_incendiary_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_incendiary.png");
	public static final ResourceLocation mp_w_10_buster_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_buster.png");
	public static final ResourceLocation mp_w_10_nuclear_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_nuclear.png");
	public static final ResourceLocation mp_w_10_nuclear_large_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_nuclear_large.png");
	public static final ResourceLocation mp_w_10_taint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_taint.png");
	public static final ResourceLocation mp_w_10_cloud_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_cloud.png");
	public static final ResourceLocation mp_w_15_he_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_he.png");
	public static final ResourceLocation mp_w_15_incendiary_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_incendiary.png");
	public static final ResourceLocation mp_w_15_nuclear_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_nuclear.png");
	public static final ResourceLocation mp_w_15_nuclear_shark_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_nuclear_shark.png");
	public static final ResourceLocation mp_w_15_n2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_n2.png");
	public static final ResourceLocation mp_w_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_balefire.png");
	
	//ISBRHs
	public static final IModelCustom scaffold = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/scaffold.obj"));
	public static final IModelCustom taperecorder = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/taperecorder.obj"));
	public static final IModelCustom beam = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/beam.obj"));
	public static final IModelCustom barrel = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/barrel.obj"));
	public static final IModelCustom pole = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/pole.obj"));
	public static final IModelCustom barbed_wire = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/barbed_wire.obj"));
	public static final IModelCustom antenna_top = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/antenna_top.obj"));
	
}
