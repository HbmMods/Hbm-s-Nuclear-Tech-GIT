package com.hbm.main;

import com.hbm.animloader.AnimatedModel;
import com.hbm.animloader.Animation;
import com.hbm.animloader.ColladaLoader;
import com.hbm.lib.RefStrings;
import com.hbm.render.loader.HFRWavefrontObject;
import com.hbm.render.loader.WavefrontObjDisplayList;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ResourceManager {
	
	//public static final Shader test_shader = ShaderManager.loadShader(new ResourceLocation(RefStrings.MODID, "shaders/test_shader"));

	//God
	public static final IModelCustom error = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/error.obj"));
	
	////Obj TEs
	
	//Turrets
	public static final IModelCustom turret_chekhov = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_chekhov.obj"));
	public static final IModelCustom turret_jeremy = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_jeremy.obj"));
	public static final IModelCustom turret_tauon = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_tauon.obj"));
	public static final IModelCustom turret_richard = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_richard.obj"));
	public static final IModelCustom turret_howard = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_howard.obj"));
	public static final IModelCustom turret_maxwell = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_microwave.obj"));
	public static final IModelCustom turret_fritz = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_fritz.obj"));
	public static final IModelCustom turret_brandon = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_brandon.obj"));
	public static final IModelCustom turret_arty = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_arty.obj")).asDisplayList(); // test!
	public static final IModelCustom turret_himars = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_himars.obj")).asDisplayList();
	public static final IModelCustom turret_sentry = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_sentry.obj"));

	public static final IModelCustom turret_howard_damaged = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/turrets/turret_howard_damaged.obj"));
	
	//Heaters
	public static final IModelCustom heater_firebox = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/firebox.obj"));
	public static final IModelCustom heater_oven = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/heating_oven.obj"));
	public static final IModelCustom heater_oilburner = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/oilburner.obj"));
	public static final IModelCustom heater_electric = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/electric_heater.obj"), false);
	public static final IModelCustom heater_heatex = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/heatex.obj"));
	
	//Heat Engines
	public static final IModelCustom stirling = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/stirling.obj"));
	public static final IModelCustom sawmill = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/sawmill.obj"));
	public static final IModelCustom crucible_heat = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/crucible.obj"));
	public static final IModelCustom boiler = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/boiler.obj"));
	public static final IModelCustom boiler_burst = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/boiler_burst.obj"));
	public static final IModelCustom hephaestus = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/hephaestus.obj"));
	
	//Furnaces
	public static final IModelCustom furnace_iron = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/furnace_iron.obj"));
	public static final IModelCustom furnace_steel = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/furnace_steel.obj"));
	public static final IModelCustom combination_oven = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/combination_oven.obj"));
	
	//Landmines
	public static final IModelCustom mine_ap = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mine_ap.obj"));
	public static final IModelCustom mine_he = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mine_he.obj"));
	public static final IModelCustom mine_marelet = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/bombs/marelet.obj"));
	public static final IModelCustom mine_fat = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mine_fat.obj"));
	
	//Oil Pumps
	public static final IModelCustom derrick = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/derrick.obj"));
	public static final IModelCustom pumpjack = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/pumpjack.obj"));
	public static final IModelCustom fracking_tower = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/fracking_tower.obj"));
	
	//Refinery
	public static final IModelCustom refinery = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/refinery.obj"));
	public static final IModelCustom vacuum_distill = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/vacuum_distill.obj"));
	public static final IModelCustom refinery_exploded = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/refinery_exploded.obj"));
	public static final IModelCustom fraction_tower = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/fraction_tower.obj"));
	public static final IModelCustom fraction_spacer = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/fraction_spacer.obj"));
	public static final IModelCustom cracking_tower = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/cracking_tower.obj"));
	public static final IModelCustom catalytic_reformer = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/catalytic_reformer.obj"));
	public static final IModelCustom liquefactor = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/liquefactor.obj"));
	public static final IModelCustom solidifier = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/solidifier.obj"));
	public static final IModelCustom compressor = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/compressor.obj"));
	public static final IModelCustom coker = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/coker.obj"));
	
	public static final IModelCustom cryo_distill = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/cryo_distill.obj"));

	//Flare Stack
	public static final IModelCustom oilflare = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/flare_stack.obj"));

	//Tank
	public static final IModelCustom fluidtank = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/fluidtank.obj"));
	public static final IModelCustom fluidtank_exploded = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/fluidtank_exploded.obj"));
	public static final IModelCustom bat9000 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/bat9000.obj"));
	public static final IModelCustom orbus = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/orbus.obj"));
	
	//Turbofan
	public static final IModelCustom turbofan_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turbofan_body.obj"));
	public static final IModelCustom turbofan_blades = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/turbofan_blades.obj"));
	public static final IModelCustom turbofan = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/turbofan.obj"));

	
	public static final IModelCustom turbinegas = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/turbinegas.obj"));
	
	//Large Turbine
	public static final IModelCustom steam_engine = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/steam_engine.obj")).asDisplayList();
	public static final IModelCustom turbine = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/turbine.obj"));
	public static final IModelCustom chungus = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/chungus.obj")).asDisplayList();
	
	//Cooling Tower
	public static final IModelCustom tower_small = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/tower_small.obj"));
	public static final IModelCustom tower_large = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/tower_large.obj"));
	public static final IModelCustom tower_chimney = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/chimney.obj"));
	
	//IGen
	public static final IModelCustom igen = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/igen.obj"));
	
	//Selenium Engine
	public static final IModelCustom selenium_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/selenium_engine_body.obj"));
	public static final IModelCustom selenium_rotor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/selenium_engine_rotor.obj"));
	public static final IModelCustom selenium_piston = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/selenium_engine_piston.obj"));
	
	//Combustion Engine
	public static final IModelCustom dieselgen = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/dieselgen.obj"));
	public static final IModelCustom combustion_engine = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/combustion_engine.obj")).asDisplayList();
	
	//Press
	public static final IModelCustom press_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/press_body.obj"));
	public static final IModelCustom press_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/press_head.obj"));
	public static final IModelCustom epress_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/epress_body.obj"));
	public static final IModelCustom epress_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/epress_head.obj"));
	
	//Assembler
	public static final IModelCustom assembler_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/assembler_new_body.obj"));
	public static final IModelCustom assembler_cog = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/assembler_new_cog.obj"));
	public static final IModelCustom assembler_slider = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/assembler_new_slider.obj"));
	public static final IModelCustom assembler_arm = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/assembler_new_arm.obj"));
	public static final IModelCustom assemfac = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/assemfac.obj"));
	
	//Chemplant
	public static final IModelCustom chemplant_body = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/chemplant_new_body.obj"));
	public static final IModelCustom chemplant_spinner = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/chemplant_new_spinner.obj"));
	public static final IModelCustom chemplant_piston = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/chemplant_new_piston.obj"));
	public static final IModelCustom chemplant_fluid = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/chemplant_new_fluid.hmf"));
	public static final IModelCustom chemplant_fluidcap = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/chemplant_new_fluidcap.hmf"));
	public static final IModelCustom chemfac = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/chemfac.obj"));
	
	//Mixer
	public static final IModelCustom mixer = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/mixer.obj"));
	
	//F6 TANKS
	public static final IModelCustom tank = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/tank.obj"));
	
	//Centrifuge
	public static final IModelCustom centrifuge = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/centrifuge.obj"));
	public static final IModelCustom gascent = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/gascent.obj"));
	public static final IModelCustom silex = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/silex.obj"));
	public static final IModelCustom fel = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/fel.obj"));
	
	//Magnusson Device
	public static final IModelCustom microwave = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/microwave.obj"));
	
	//Big Man Johnson
	public static final IModelCustom autosaw = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/autosaw.obj"));
	
	//Mining Drill
	public static final IModelCustom drill_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/drill_main.obj"));
	public static final IModelCustom drill_bolt = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/drill_bolt.obj"));
	public static final IModelCustom mining_drill = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/mining_drill.obj")).asDisplayList();
	
	//Laser Miner
	public static final IModelCustom mining_laser = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/mining_laser.obj"));
	
	//Crystallizer
	public static final IModelCustom crystallizer = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/crystallizer.obj"));
	
	//Cyclotron
	public static final IModelCustom cyclotron = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/cyclotron.obj"));
	
	//RTG
	public static final IModelCustom rtg = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/rtg.obj"));

	//Waste Drum
	public static final IModelCustom waste_drum = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/drum.obj"));
	
	//Deuterium Tower
	public static final IModelCustom deuterium_tower = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/machine_deuterium_tower.obj"));
	public static final IModelCustom atmo_tower = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/atmo_tower.obj"));
	public static final IModelCustom atmo_vent = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/atmo_vent.obj"));
	
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
	
	//Fan
	public static final IModelCustom fan = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/fan.obj"));
	
	//Piston Inserter
	public static final IModelCustom piston_inserter = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/piston_inserter.obj"));
	
	//Sphere
	public static final IModelCustom sphere_ruv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_ruv.obj"));
	public static final IModelCustom sphere_iuv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_iuv.obj"));
	public static final IModelCustom sphere_uv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_uv.obj"));
	public static final IModelCustom sphere_uv_anim = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_uv.hmf"));
	
	//Meteor
	public static final IModelCustom meteor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/meteor.obj"));
	
	//Radgen
	public static final IModelCustom radgen = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/radgen.obj"));
	
	//Small Reactor
	public static final IModelCustom reactor_small_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/reactors/reactor_small_base.obj"));
	public static final IModelCustom reactor_small_rods = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/reactors/reactor_small_rods.obj"));
	
	//Breeder
	public static final IModelCustom breeder = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/reactors/breeder.obj"));
	
	//ITER
	public static final IModelCustom iter = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/reactors/iter.obj"));
	
	//Watz
	public static final IModelCustom watz = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/reactors/watz.obj"));
	public static final IModelCustom watz_pump = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/watz_pump.obj"));
	
	//FENSU
	public static final IModelCustom fensu = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/fensu.obj"));
	
	//Radar
	public static final IModelCustom radar_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/radar_base.obj"));
	public static final IModelCustom radar_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/radar_head.obj"));
	public static final IModelCustom radar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/radar.obj"));
	
	//Forcefield
	public static final IModelCustom forcefield_top = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/forcefield_top.obj"));
	
	//Shredder
	public static final IModelCustom shredder = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/shredder.obj"));

	//Bombs
	public static final IModelCustom bomb_gadget = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/TheGadget3.obj"));
	public static final IModelCustom bomb_boy = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/LilBoy1.obj"));
	public static final IModelCustom bomb_man = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/FatMan.obj")).asDisplayList();
	public static final IModelCustom bomb_mike = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/bombs/ivymike.obj"));
	public static final IModelCustom bomb_tsar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/TsarBomba.obj"));
	public static final IModelCustom bomb_prototype = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/Prototype.obj"));
	public static final IModelCustom bomb_fleija = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/Fleija.obj"));
	public static final IModelCustom bomb_solinium = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/bombs/ufp.obj"));
	public static final IModelCustom n2 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/bombs/n2.obj"));
	public static final IModelCustom bomb_multi = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/BombGeneric.obj"));
	public static final IModelCustom n45_globe = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/bombs/n45_globe.obj"));
	public static final IModelCustom n45_knob = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/bombs/n45_knob.obj"));
	public static final IModelCustom n45_rod = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/bombs/n45_rod.obj"));
	public static final IModelCustom n45_stand = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/bombs/n45_stand.obj"));
	public static final IModelCustom n45_chain = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/bombs/n45_chain.obj"));
	public static final IModelCustom fstbmb = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/bombs/fstbmb.obj"));
	public static final IModelCustom Antimatter = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/bombs/blomb.obj"));
	public static final IModelCustom dud = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/BalefireCrashed.obj"));
	
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
	
	//Solar Tower
	public static final IModelCustom solar_boiler = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/solar_boiler.obj"));
	public static final IModelCustom solar_mirror = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/machines/solar_mirror.obj"));
	
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
	
	//Doors
	public static AnimatedModel transition_seal;
	public static Animation transition_seal_anim;
	public static final WavefrontObjDisplayList fire_door = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/doors/fire_door.obj")).asDisplayList();
	
	//Tesla Coil
	public static final IModelCustom tesla = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/tesla.obj"));
	public static final IModelCustom teslacrab = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mobs/teslacrab.obj"));
	public static final IModelCustom taintcrab = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mobs/taintcrab.obj"));
	public static final IModelCustom maskman = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mobs/maskman.obj"));
	public static final IModelCustom spider = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mobs/blockspider.obj"));
	public static final IModelCustom ufo = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/mobs/ufo.obj"));
	public static final IModelCustom mini_ufo = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/mobs/mini_ufo.obj"));
	public static final IModelCustom siege_ufo = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/mobs/siege_ufo.obj"));
	
	//ZIRNOX
	public static final IModelCustom zirnox = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/zirnox.obj"));
	public static final IModelCustom zirnox_destroyed = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/zirnox_destroyed.obj"));
	
	//Belt
	public static final IModelCustom arrow = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/arrow.obj"));
	
	//Network
	public static final IModelCustom connector = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/network/connector.obj"));
	public static final IModelCustom pylon_large = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/network/pylon_large.obj"));
	public static final IModelCustom substation = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/network/substation.obj"));
	
	//Radiolysis
	public static final IModelCustom radiolysis = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/radiolysis.obj"));
	
	//Electrolyser
	public static final IModelCustom electrolyser = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/machines/electrolyser.obj"));
	
	//Belt
	public static final IModelCustom charger = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/blocks/charger.obj"));
	
	//DecoContainer (File Cabinet for now)
	public static final IModelCustom file_cabinet = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/file_cabinet.obj"));
	
	////Textures TEs
	
	public static final ResourceLocation universal = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_.png");
	public static final ResourceLocation universal_bright = new ResourceLocation(RefStrings.MODID, "textures/models/turbofan_blades.png");
	
	public static final ResourceLocation turret_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/base.png");
	public static final ResourceLocation turret_base_friendly_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/base_friendly.png");
	public static final ResourceLocation turret_carriage_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/carriage.png");
	public static final ResourceLocation turret_carriage_ciws_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/carriage_ciws.png");
	public static final ResourceLocation turret_carriage_friendly_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/carriage_friendly.png");
	public static final ResourceLocation turret_connector_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/connector.png");
	public static final ResourceLocation turret_chekhov_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/chekhov.png");
	public static final ResourceLocation turret_chekhov_barrels_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/chekhov_barrels.png");
	public static final ResourceLocation turret_jeremy_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/jeremy.png");
	public static final ResourceLocation turret_tauon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/tauon.png");
	public static final ResourceLocation turret_richard_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/richard.png");
	public static final ResourceLocation turret_howard_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/howard.png");
	public static final ResourceLocation turret_howard_barrels_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/howard_barrels.png");
	public static final ResourceLocation turret_maxwell_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/maxwell.png");
	public static final ResourceLocation turret_fritz_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/fritz.png");
	public static final ResourceLocation turret_brandon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/brandon.png");
	public static final ResourceLocation turret_arty_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/arty.png");
	public static final ResourceLocation turret_himars_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/himars.png");
	public static final ResourceLocation turret_sentry_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/sentry.png");

	public static final ResourceLocation himars_standard_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/himars_standard.png");
	public static final ResourceLocation himars_single_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/himars_single.png");

	public static final ResourceLocation turret_base_rusted= new ResourceLocation(RefStrings.MODID, "textures/models/turrets/rusted/base.png");
	public static final ResourceLocation turret_carriage_ciws_rusted = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/rusted/carriage_ciws.png");
	public static final ResourceLocation turret_howard_rusted = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/rusted/howard.png");
	public static final ResourceLocation turret_howard_barrels_rusted = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/rusted/howard_barrels.png");
	
	public static final ResourceLocation brandon_explosive = new ResourceLocation(RefStrings.MODID, "textures/models/turrets/brandon_drum.png");

	//Landmines
	public static final ResourceLocation mine_ap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_ap.png");
	//public static final ResourceLocation mine_he_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_he.png");
	public static final ResourceLocation mine_marelet_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/mine_marelet.png");
	public static final ResourceLocation mine_shrap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_shrap.png");
	public static final ResourceLocation mine_fat_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_fat.png");
	
	//Heaters
	public static final ResourceLocation heater_firebox_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/firebox.png");
	public static final ResourceLocation heater_oven_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/heating_oven.png");
	public static final ResourceLocation heater_oilburner_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/oilburner.png");
	public static final ResourceLocation heater_electric_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/electric_heater.png");
	public static final ResourceLocation heater_heatex_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/heater_heatex.png");
	
	//Heat Engines
	public static final ResourceLocation stirling_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/stirling.png");
	public static final ResourceLocation stirling_steel_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/stirling_steel.png");
	public static final ResourceLocation sawmill_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/sawmill.png");
	public static final ResourceLocation crucible_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/crucible_heat.png");
	public static final ResourceLocation boiler_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/boiler.png");
	public static final ResourceLocation hephaestus_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/hephaestus.png");
	
	//Furnaces
	public static final ResourceLocation furnace_iron_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/furnace_iron.png");
	public static final ResourceLocation furnace_steel_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/furnace_steel.png");
	public static final ResourceLocation combination_oven_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/combination_oven.png");
	
	//Oil Pumps
	public static final ResourceLocation derrick_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/derrick.png");
	public static final ResourceLocation pumpjack_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/pumpjack.png");
	public static final ResourceLocation fracking_tower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/fracking_tower.png");
	
	//Refinery
	public static final ResourceLocation refinery_tex = new ResourceLocation(RefStrings.MODID, "textures/models/refinery.png");
	public static final ResourceLocation vacuum_distill_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/vacuum_distill.png");
	public static final ResourceLocation fraction_tower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/fraction_tower.png");
	public static final ResourceLocation fraction_spacer_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/fraction_spacer.png");
	public static final ResourceLocation cracking_tower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cracking_tower.png");
	public static final ResourceLocation catalytic_reformer_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/catalytic_reformer.png");
	public static final ResourceLocation liquefactor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/liquefactor.png");
	public static final ResourceLocation solidifier_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/solidifier.png");
	public static final ResourceLocation compressor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/compressor.png");
	public static final ResourceLocation coker_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/coker.png");
	
	
	public static final ResourceLocation cryodistill_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cryo_distiller.png");

	//Flare Stack
	public static final ResourceLocation oilflare_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/flare_stack.png");
	
	//Tank
	public static final ResourceLocation tank_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank.png");
	public static final ResourceLocation tank_inner_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank/tank_inner.png");
	public static final ResourceLocation tank_label_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tank/tank_NONE.png");
	public static final ResourceLocation bat9000_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/bat9000.png");
	public static final ResourceLocation orbus_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/orbus.png");
	
	//Turbofan
	public static final ResourceLocation turbofan_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/turbofan.png");
	public static final ResourceLocation turbofan_back_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/turbofan_back.png");
	public static final ResourceLocation turbofan_afterburner_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/turbofan_afterburner.png");
	
	public static final ResourceLocation turbinegas_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/turbinegas.png");
	
	//Large Turbine
	public static final ResourceLocation steam_engine_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/steam_engine.png");
	public static final ResourceLocation turbine_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/turbine.png");
	public static final ResourceLocation chungus_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/chungus.png");
	
	//Cooling Tower
	public static final ResourceLocation tower_small_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/tower_small.png");
	public static final ResourceLocation tower_large_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/tower_large.png");
	public static final ResourceLocation tower_chimney_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/chimney.png");
	//Deuterium Tower
	public static final ResourceLocation deuterium_tower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/machine_deuterium_tower.png");
	public static final ResourceLocation atmo_tower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/machine_atmo_tower.png");
	public static final ResourceLocation atmo_vent_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/atmo_vent.png");
	
	//IGen
	public static final ResourceLocation igen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/igen.png");
	public static final ResourceLocation igen_rotor = new ResourceLocation(RefStrings.MODID, "textures/models/machines/igen_rotor.png");
	public static final ResourceLocation igen_cog = new ResourceLocation(RefStrings.MODID, "textures/models/machines/igen_cog.png");
	public static final ResourceLocation igen_arm = new ResourceLocation(RefStrings.MODID, "textures/models/machines/igen_arm.png");
	public static final ResourceLocation igen_pistons = new ResourceLocation(RefStrings.MODID, "textures/models/machines/igen_pistons.png");
	
	//Selenium Engine
	public static final ResourceLocation selenium_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_body.png");
	public static final ResourceLocation selenium_piston_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_piston.png");
	public static final ResourceLocation selenium_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_rotor.png");
	
	//Combustion Engine
	public static final ResourceLocation dieselgen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/dieselgen.png");
	public static final ResourceLocation combustion_engine_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/combustion_engine.png");
	
	//Press
	public static final ResourceLocation press_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/press_body.png");
	public static final ResourceLocation press_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/press_head.png");
	public static final ResourceLocation epress_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/epress_body.png");
	public static final ResourceLocation epress_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/epress_head.png");
	
	//Assembler
	public static final ResourceLocation assembler_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/assembler_base_new.png");
	public static final ResourceLocation assembler_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/assembler_cog_new.png");
	public static final ResourceLocation assembler_slider_tex = new ResourceLocation(RefStrings.MODID, "textures/models/assembler_slider_new.png");
	public static final ResourceLocation assembler_arm_tex = new ResourceLocation(RefStrings.MODID, "textures/models/assembler_arm_new.png");
	public static final ResourceLocation assemfac_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/assemfac.png");
	
	//Chemplant
	public static final ResourceLocation chemplant_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/chemplant_base_new.png");
	public static final ResourceLocation chemplant_spinner_tex = new ResourceLocation(RefStrings.MODID, "textures/models/chemplant_spinner_new.png");
	public static final ResourceLocation chemplant_piston_tex = new ResourceLocation(RefStrings.MODID, "textures/models/chemplant_piston_new.png");
	public static final ResourceLocation chemplant_fluid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/lavabase_small.png");
	public static final ResourceLocation chemfac_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/chemfac.png");
	
	//Mixer
	public static final ResourceLocation mixer_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/mixer.png");

	//F6 TANKS
	public static final ResourceLocation uf6_tex = new ResourceLocation(RefStrings.MODID, "textures/models/UF6Tank.png");
	public static final ResourceLocation puf6_tex = new ResourceLocation(RefStrings.MODID, "textures/models/PUF6Tank.png");
	
	//Centrifuge
	public static final ResourceLocation centrifuge_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/centrifuge.png");
	public static final ResourceLocation gascent_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/gascent.png");
	public static final ResourceLocation fel_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/fel.png");
	public static final ResourceLocation silex_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/silex.png");
	
	//Magnusson Device
	public static final ResourceLocation microwave_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/microwave.png");
	
	//Big Man Johnson
	public static final ResourceLocation autosaw_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/autosaw.png");
	
	//Mining Drill
	public static final ResourceLocation drill_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mining_drill.png");
	public static final ResourceLocation drill_bolt_tex = new ResourceLocation(RefStrings.MODID, "textures/models/textureIGenRotor.png");
	public static final ResourceLocation mining_drill_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/mining_drill.png");

	//Laser Miner
	public static final ResourceLocation mining_laser_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/mining_laser_base.png");
	public static final ResourceLocation mining_laser_pivot_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/mining_laser_pivot.png");
	public static final ResourceLocation mining_laser_laser_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/mining_laser_laser.png");
	
	//Crystallizer
	public static final ResourceLocation crystallizer_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/crystallizer.png");
	public static final ResourceLocation crystallizer_spinner_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/crystallizer_spinner.png");
	public static final ResourceLocation crystallizer_window_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/crystallizer_window.png");
	
	//Cyclotron
	public static final ResourceLocation cyclotron_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron.png");
	public static final ResourceLocation cyclotron_ashes = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron_ashes.png");
	public static final ResourceLocation cyclotron_ashes_filled = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron_ashes_filled.png");
	public static final ResourceLocation cyclotron_book = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron_book.png");
	public static final ResourceLocation cyclotron_book_filled = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron_book_filled.png");
	public static final ResourceLocation cyclotron_gavel = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron_gavel.png");
	public static final ResourceLocation cyclotron_gavel_filled = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron_gavel_filled.png");
	public static final ResourceLocation cyclotron_coin = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron_coin.png");
	public static final ResourceLocation cyclotron_coin_filled = new ResourceLocation(RefStrings.MODID, "textures/models/machines/cyclotron_coin_filled.png");
	
	//RTG
	public static final ResourceLocation rtg_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/rtg.png");
	public static final ResourceLocation rtg_cell_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/rtg_cell.png");
	public static final ResourceLocation rtg_polonium_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/rtg_polonium.png");
	
	//Waste Drum
	public static final ResourceLocation waste_drum_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/drum_gray.png");
	
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
	
	//Fan
	public static final ResourceLocation fan_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/fan.png");
	
	//Piston_Inserter
	public static final ResourceLocation piston_inserter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/piston_inserter.png");
	
	//Radgen
	public static final ResourceLocation radgen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/radgen.png");
	
	//Small Reactor
	public static final ResourceLocation reactor_small_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/reactor_small_base.png");
	public static final ResourceLocation reactor_small_rods_tex = new ResourceLocation(RefStrings.MODID, "textures/models/reactor_small_rods.png");

	//Breeder
	public static final ResourceLocation breeder_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/breeder.png");
	
	//ITER
	public static final ResourceLocation iter_glass = new ResourceLocation(RefStrings.MODID, "textures/models/iter/glass.png");
	public static final ResourceLocation iter_microwave = new ResourceLocation(RefStrings.MODID, "textures/models/iter/microwave.png");
	public static final ResourceLocation iter_motor = new ResourceLocation(RefStrings.MODID, "textures/models/iter/motor.png");
	public static final ResourceLocation iter_plasma = new ResourceLocation(RefStrings.MODID, "textures/models/iter/plasma.png");
	public static final ResourceLocation iter_rails = new ResourceLocation(RefStrings.MODID, "textures/models/iter/rails.png");
	public static final ResourceLocation iter_solenoid = new ResourceLocation(RefStrings.MODID, "textures/models/iter/solenoid.png");
	public static final ResourceLocation iter_toroidal = new ResourceLocation(RefStrings.MODID, "textures/models/iter/toroidal.png");
	public static final ResourceLocation iter_torus = new ResourceLocation(RefStrings.MODID, "textures/models/iter/torus.png");
	public static final ResourceLocation iter_torus_tungsten = new ResourceLocation(RefStrings.MODID, "textures/models/iter/torus_tungsten.png");
	public static final ResourceLocation iter_torus_desh = new ResourceLocation(RefStrings.MODID, "textures/models/iter/torus_desh.png");
	public static final ResourceLocation iter_torus_chlorophyte = new ResourceLocation(RefStrings.MODID, "textures/models/iter/torus_chlorophyte.png");
	public static final ResourceLocation iter_torus_vaporwave = new ResourceLocation(RefStrings.MODID, "textures/models/iter/torus_vaporwave.png");
	
	//Watz
	public static final ResourceLocation watz_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/watz.png");
	public static final ResourceLocation watz_pump_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/watz_pump.png");
	
	//FENSU
	public static final ResourceLocation fensu_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/fensu.png");
	
	//Radar
	public static final ResourceLocation radar_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_base.png");
	public static final ResourceLocation radar_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_head.png");
	public static final ResourceLocation radar_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/radar_base.png");
	public static final ResourceLocation radar_dish_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/radar_dish.png");
	
	//Forcefield
	public static final ResourceLocation forcefield_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_base.png");
	public static final ResourceLocation forcefield_top_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_top.png");
	
	//Shredder
	public static final ResourceLocation shredder_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/shredder.png");
	
	//Bombs
	public static final ResourceLocation bomb_gadget_tex = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_tex.png");
	public static final ResourceLocation bomb_boy_tex = new ResourceLocation(RefStrings.MODID, "textures/models/lilboy.png");
	public static final ResourceLocation bomb_man_tex = new ResourceLocation(RefStrings.MODID, "textures/models/FatMan.png");
	public static final ResourceLocation bomb_mike_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/ivymike.png");
	public static final ResourceLocation bomb_tsar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/TsarBomba.png");
	public static final ResourceLocation bomb_prototype_tex = new ResourceLocation(RefStrings.MODID, "textures/models/Prototype.png");
	public static final ResourceLocation bomb_fleija_tex = new ResourceLocation(RefStrings.MODID, "textures/models/Fleija.png");
	public static final ResourceLocation bomb_solinium_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/ufp.png");
	public static final ResourceLocation n2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/n2.png");
	public static final ResourceLocation bomb_custom_tex = new ResourceLocation(RefStrings.MODID, "textures/models/CustomNuke.png");
	public static final ResourceLocation bomb_multi_tex = new ResourceLocation(RefStrings.MODID, "textures/models/BombGeneric.png");
	public static final ResourceLocation n45_globe_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/n45_globe.png");
	public static final ResourceLocation n45_knob_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/n45_knob.png");
	public static final ResourceLocation n45_rod_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/n45_rod.png");
	public static final ResourceLocation n45_stand_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_stand.png");
	public static final ResourceLocation n45_chain_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/n45_chain.png");
	public static final ResourceLocation fstbmb_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/fstbmb.png");
	public static final ResourceLocation Antimatter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bombs/blomb_tex.png");
	public static final ResourceLocation dud_tex = new ResourceLocation(RefStrings.MODID, "textures/models/BalefireCrashed.png");
	
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
	
	//Solar Tower
	public static final ResourceLocation solar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/solar_boiler.png");
	public static final ResourceLocation solar_mirror_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/solar_mirror.png");
	
	//Blast Door
	public static final ResourceLocation blast_door_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_base.png");
	public static final ResourceLocation blast_door_tooth_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_tooth.png");
	public static final ResourceLocation blast_door_slider_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_slider.png");
	public static final ResourceLocation blast_door_block_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_block.png");
	
	//Doors
	public static final ResourceLocation transition_seal_tex = new ResourceLocation(RefStrings.MODID, "textures/models/doors/transition_seal.png");
	public static final ResourceLocation fire_door_tex = new ResourceLocation(RefStrings.MODID, "textures/models/doors/fire_door.png");

	//Tesla Coil
	public static final ResourceLocation tesla_tex = new ResourceLocation(RefStrings.MODID, "textures/models/tesla.png");
	public static final ResourceLocation teslacrab_tex = new ResourceLocation(RefStrings.MODID, "textures/entity/teslacrab.png");
	public static final ResourceLocation taintcrab_tex = new ResourceLocation(RefStrings.MODID, "textures/entity/taintcrab.png");
	public static final ResourceLocation maskman_tex = new ResourceLocation(RefStrings.MODID, "textures/entity/maskman.png");
	public static final ResourceLocation iou = new ResourceLocation(RefStrings.MODID, "textures/entity/iou.png");
	public static final ResourceLocation spider_tex = new ResourceLocation(RefStrings.MODID, "textures/entity/blockspider.png");
	public static final ResourceLocation ufo_tex = new ResourceLocation(RefStrings.MODID, "textures/entity/ufo.png");
	
	//ZIRNOX
	public static final ResourceLocation zirnox_tex = new ResourceLocation(RefStrings.MODID, "textures/models/zirnox.png");
	public static final ResourceLocation zirnox_destroyed_tex = new ResourceLocation(RefStrings.MODID, "textures/models/zirnox_destroyed.png");
	
	//Electricity
	public static final ResourceLocation connector_tex = new ResourceLocation(RefStrings.MODID, "textures/models/network/connector.png");
	public static final ResourceLocation pylon_large_tex = new ResourceLocation(RefStrings.MODID, "textures/models/network/pylon_large.png");
	public static final ResourceLocation substation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/network/substation.png");

	//Radiolysis
	public static final ResourceLocation radiolysis_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radiolysis.png");
	
	//Electrolyser
	public static final ResourceLocation electrolyser_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/electrolyser.png");
	
	//Charger
	public static final ResourceLocation charger_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/charger.png");
	
	//DecoContainer
	public static final ResourceLocation file_cabinet_tex = new ResourceLocation(RefStrings.MODID, "textures/models/file_cabinet.png");
	public static final ResourceLocation file_cabinet_steel_tex = new ResourceLocation(RefStrings.MODID, "textures/models/file_cabinet_steel.png");
	
	////Obj Items
	
	//Shimmer Sledge
	public static final IModelCustom shimmer_sledge = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/shimmer_sledge.obj"));
	public static final IModelCustom shimmer_axe = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/shimmer_axe.obj"));
	public static final IModelCustom stopsign = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/stopsign.obj"));
	public static final IModelCustom pch = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/pch.obj"));
	public static final IModelCustom gavel = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/gavel.obj"));
	public static final IModelCustom crucible = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/crucible.obj"));
	public static final IModelCustom chainsaw = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/chainsaw.obj"), false);
	public static final IModelCustom boltgun = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/boltgun.obj"));

	public static final IModelCustom hk69 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/hk69.obj"));
	public static final IModelCustom deagle = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/deagle.obj"));
	public static final IModelCustom shotty = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/supershotty.obj"));
	public static final IModelCustom ks23 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/ks23.obj"));
	public static final IModelCustom flamer = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/flamer.obj"));
	public static final IModelCustom flechette = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/flechette.obj"));
	public static final IModelCustom quadro = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/quadro.obj"));
	public static final IModelCustom sauergun = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/sauergun.obj"));
	public static final IModelCustom vortex = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/vortex.obj"));
	public static final IModelCustom thompson = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/thompson.obj"));
	public static final IModelCustom bolter = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/bolter.obj"));
	public static final IModelCustom ff_python = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/python.obj"));
	public static final IModelCustom ff_maresleg = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/maresleg.obj"));
	public static final IModelCustom ff_nightmare = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/nightmare.obj"));
	public static final IModelCustom fireext = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/fireext.obj"));
	public static final IModelCustom ar15 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/ar15.obj"));
	public static final IModelCustom stinger = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/stinger.obj"));
	public static final IModelCustom mg42 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/mg42.obj"));
	public static final IModelCustom rem700 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/rem700.obj"));
	public static final IModelCustom rem700poly = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/rem700poly.obj"));
	public static final IModelCustom rem700sat = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/rem700sat.obj"));
	public static final IModelCustom cursed_revolver = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/cursed.obj"));
	public static final IModelCustom detonator_laser = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/detonator_laser.obj"));
	public static final IModelCustom remington = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/remington.obj"));
	public static final IModelCustom spas_12 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/spas-12.obj"));
	public static final IModelCustom nightmare_dark = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/nightmare_dark.obj"));
	public static final IModelCustom glass_cannon = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/glass_cannon.obj"));
	public static final IModelCustom bio_revolver = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/bio_revolver.obj"));
	public static final IModelCustom chemthrower = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/chemthrower.obj")).asDisplayList();
	public static final IModelCustom novac = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/novac.obj"));
	public static final IModelCustom novac_scoped = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/novac_scoped.obj"));
	public static final IModelCustom m2 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/m2_browning.obj")).asDisplayList(); //large fella should be a display list
	public static final IModelCustom lunatic_sniper = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/lunatic_sniper.obj")).asDisplayList();
	public static final IModelCustom tau = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/tau.obj"));
	
	public static final IModelCustom lance = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/lance.obj"));

	public static final IModelCustom grenade_frag = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/grenade_frag.obj"));
	public static final IModelCustom grenade_aschrab = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/grenade_aschrab.obj"));

	public static final IModelCustom armor_bj = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/BJ.obj"));
	public static final IModelCustom armor_hev = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/hev.obj"));
	public static final IModelCustom armor_ajr = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/AJR.obj"));
	public static final IModelCustom armor_hat = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/hat.obj"));
	public static final IModelCustom armor_no9 = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/no9.obj"));
	public static final IModelCustom armor_goggles = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/goggles.obj"));
	public static final IModelCustom armor_fau = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/fau.obj"));
	public static final IModelCustom armor_dnt = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/dnt.obj"));
	public static final IModelCustom armor_steamsuit = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/steamsuit.obj"));
	public static final IModelCustom armor_dieselsuit = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/bnuuy.obj"));
	public static final IModelCustom armor_remnant = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/remnant.obj"));
	public static final IModelCustom armor_bismuth = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/bismuth.obj"));
	public static final IModelCustom armor_mod_tesla = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/mod_tesla.obj"));
	public static final IModelCustom armor_wings = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/murk.obj"));
	public static final IModelCustom armor_solstice = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/armor/solstice.obj"));
	public static final IModelCustom player_manly_af = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/armor/player_fem.obj"));
	
	////Texture Items

	//Shimmer Sledge
	public static final ResourceLocation shimmer_sledge_tex = new ResourceLocation(RefStrings.MODID, "textures/models/shimmer_sledge.png");
	public static final ResourceLocation shimmer_axe_tex = new ResourceLocation(RefStrings.MODID, "textures/models/shimmer_axe.png");
	public static final ResourceLocation stopsign_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/stopsign.png");
	public static final ResourceLocation sopsign_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/sopsign.png");
	public static final ResourceLocation chernobylsign_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/chernobylsign.png");
	public static final ResourceLocation pch_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/pch.png");
	public static final ResourceLocation gavel_wood = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/gavel_wood.png");
	public static final ResourceLocation gavel_lead = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/gavel_lead.png");
	public static final ResourceLocation gavel_diamond = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/gavel_diamond.png");
	public static final ResourceLocation gavel_mese = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/gavel_mese.png");
	public static final ResourceLocation crucible_hilt = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/crucible_hilt.png");
	public static final ResourceLocation crucible_guard = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/crucible_guard.png");
	public static final ResourceLocation crucible_blade = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/crucible_blade.png");
	public static final ResourceLocation chainsaw_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/chainsaw.png");
	public static final ResourceLocation boltgun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/boltgun.png");

	public static final ResourceLocation hk69_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/hk69.png");
	public static final ResourceLocation deagle_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/deagle.png");
	public static final ResourceLocation ks23_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ks23.png");
	public static final ResourceLocation shotty_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/shotty.png");
	public static final ResourceLocation flamer_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flamer.png");
	public static final ResourceLocation flechette_body = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_body.png");
	public static final ResourceLocation flechette_barrel = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_barrel.png");
	public static final ResourceLocation flechette_gren_tube = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_gren_tube.png");
	public static final ResourceLocation flechette_grenades = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_grenades.png");
	public static final ResourceLocation flechette_pivot = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_pivot.png");
	public static final ResourceLocation flechette_top = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_top.png");
	public static final ResourceLocation flechette_chamber = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_chamber.png");
	public static final ResourceLocation flechette_base = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_base.png");
	public static final ResourceLocation flechette_drum = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_drum.png");
	public static final ResourceLocation flechette_trigger = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_trigger.png");
	public static final ResourceLocation flechette_stock = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/flechette_stock.png");
	public static final ResourceLocation quadro_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/quadro.png");
	public static final ResourceLocation quadro_rocket_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/quadro_rocket.png");
	public static final ResourceLocation sauergun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/sauergun.png");
	public static final ResourceLocation vortex_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/vortex.png");
	public static final ResourceLocation thompson_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/thompson.png");
	public static final ResourceLocation bolter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/bolter.png");
	public static final ResourceLocation bolter_digamma_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/bolter_digamma.png");
	public static final ResourceLocation fireext_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/fireext_normal.png");
	public static final ResourceLocation fireext_foam_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/fireext_foam.png");
	public static final ResourceLocation fireext_sand_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/fireext_sand.png");
	public static final ResourceLocation ar15_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/carbine.png");
	public static final ResourceLocation stinger_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/stinger.png");
	public static final ResourceLocation sky_stinger_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/sky_stinger.png");
	public static final ResourceLocation mg42_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/mg42.png");
	public static final ResourceLocation rem700_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/rem700.png");
	public static final ResourceLocation rem700poly_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/rem700poly.png");
	public static final ResourceLocation rem700sat_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/rem700sat.png");
	public static final ResourceLocation detonator_laser_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/detonator_laser.png");
	public static final ResourceLocation remington_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/remington.png");
	public static final ResourceLocation spas_12_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/spas-12.png");
	public static final ResourceLocation glass_cannon_panel_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/glass_cannon_panel.png");
	public static final ResourceLocation bio_revolver_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/bio_revolver.png");
	public static final ResourceLocation chemthrower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/chemthrower.png");
	public static final ResourceLocation novac_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/novac.png");
	public static final ResourceLocation novac_scope_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/novac_scope.png");
	public static final ResourceLocation lil_pip_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/lil_pip.png");
	public static final ResourceLocation blackjack_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/blackjack.png");
	public static final ResourceLocation lent_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/lent_gun.png");
	public static final ResourceLocation red_key_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/red_key.png");
	public static final ResourceLocation m2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/m2_browning.png");
	public static final ResourceLocation lunatic_sniper_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/lunatic_sniper.png");
	public static final ResourceLocation tau_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/tau.png");
	
	public static final ResourceLocation lance_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/lance.png");

	public static final ResourceLocation ff_gold = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/gold.png");
	public static final ResourceLocation ff_gun_bright = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/gun_bright.png");
	public static final ResourceLocation ff_gun_dark = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/gun_dark.png");
	public static final ResourceLocation ff_gun_normal = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/gun_normal.png");
	public static final ResourceLocation ff_iron = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/iron.png");
	public static final ResourceLocation ff_lead = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/lead.png");
	public static final ResourceLocation ff_saturnite = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/saturnite.png");
	public static final ResourceLocation ff_schrabidium = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/schrabidium.png");
	public static final ResourceLocation ff_wood = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/wood.png");
	public static final ResourceLocation ff_wood_red = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/wood_red.png");
	public static final ResourceLocation ff_cursed = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/cursed.png");
	public static final ResourceLocation ff_nightmare_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/nightmare.png");
	public static final ResourceLocation ff_nightmare_orig_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/ff/nightmare_orig.png");

	public static final ResourceLocation grenade_mk2 = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/grenade_mk2.png");
	public static final ResourceLocation grenade_aschrab_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/grenade_aschrab.png");

	public static final ResourceLocation bj_eyepatch = new ResourceLocation(RefStrings.MODID, "textures/armor/bj_eyepatch.png");
	public static final ResourceLocation bj_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/bj_leg.png");
	public static final ResourceLocation bj_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/bj_chest.png");
	public static final ResourceLocation bj_jetpack = new ResourceLocation(RefStrings.MODID, "textures/armor/bj_jetpack.png");
	public static final ResourceLocation bj_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/bj_arm.png");

	public static final ResourceLocation hev_helmet = new ResourceLocation(RefStrings.MODID, "textures/armor/hev_helmet.png");
	public static final ResourceLocation hev_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/hev_leg.png");
	public static final ResourceLocation hev_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/hev_chest.png");
	public static final ResourceLocation hev_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/hev_arm.png");

	public static final ResourceLocation ajr_helmet = new ResourceLocation(RefStrings.MODID, "textures/armor/ajr_helmet.png");
	public static final ResourceLocation ajr_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/ajr_leg.png");
	public static final ResourceLocation ajr_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/ajr_chest.png");
	public static final ResourceLocation ajr_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/ajr_arm.png");

	public static final ResourceLocation ajro_helmet = new ResourceLocation(RefStrings.MODID, "textures/armor/ajro_helmet.png");
	public static final ResourceLocation ajro_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/ajro_leg.png");
	public static final ResourceLocation ajro_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/ajro_chest.png");
	public static final ResourceLocation ajro_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/ajro_arm.png");

	public static final ResourceLocation fau_helmet = new ResourceLocation(RefStrings.MODID, "textures/armor/fau_helmet.png");
	public static final ResourceLocation fau_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/fau_leg.png");
	public static final ResourceLocation fau_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/fau_chest.png");
	public static final ResourceLocation fau_cassette = new ResourceLocation(RefStrings.MODID, "textures/armor/fau_cassette.png");
	public static final ResourceLocation fau_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/fau_arm.png");

	public static final ResourceLocation dnt_helmet = new ResourceLocation(RefStrings.MODID, "textures/armor/dnt_helmet.png");
	public static final ResourceLocation dnt_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/dnt_leg.png");
	public static final ResourceLocation dnt_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/dnt_chest.png");
	public static final ResourceLocation dnt_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/dnt_arm.png");

	public static final ResourceLocation steamsuit_helmet = new ResourceLocation(RefStrings.MODID, "textures/armor/steamsuit_helmet.png");
	public static final ResourceLocation steamsuit_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/steamsuit_leg.png");
	public static final ResourceLocation steamsuit_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/steamsuit_chest.png");
	public static final ResourceLocation steamsuit_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/steamsuit_arm.png");

	public static final ResourceLocation dieselsuit_helmet = new ResourceLocation(RefStrings.MODID, "textures/armor/bnuuy_helmet.png");
	public static final ResourceLocation dieselsuit_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/bnuuy_leg.png");
	public static final ResourceLocation dieselsuit_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/bnuuy_chest.png");
	public static final ResourceLocation dieselsuit_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/bnuuy_arm.png");

	public static final ResourceLocation rpa_helmet = new ResourceLocation(RefStrings.MODID, "textures/armor/rpa_helmet.png");
	public static final ResourceLocation rpa_leg = new ResourceLocation(RefStrings.MODID, "textures/armor/rpa_leg.png");
	public static final ResourceLocation rpa_chest = new ResourceLocation(RefStrings.MODID, "textures/armor/rpa_chest.png");
	public static final ResourceLocation rpa_arm = new ResourceLocation(RefStrings.MODID, "textures/armor/rpa_arm.png");

	public static final ResourceLocation mod_tesla = new ResourceLocation(RefStrings.MODID, "textures/armor/mod_tesla.png");
	
	public static final ResourceLocation armor_bismuth_tex = new ResourceLocation(RefStrings.MODID, "textures/armor/bismuth.png");

	public static final ResourceLocation wings_murk = new ResourceLocation(RefStrings.MODID, "textures/armor/wings_murk.png");
	public static final ResourceLocation wings_bob = new ResourceLocation(RefStrings.MODID, "textures/armor/wings_bob.png");
	public static final ResourceLocation wings_black = new ResourceLocation(RefStrings.MODID, "textures/armor/wings_black.png");
	public static final ResourceLocation wings_solstice = new ResourceLocation(RefStrings.MODID, "textures/armor/wings_solstice.png"); 

	public static final ResourceLocation hat = new ResourceLocation(RefStrings.MODID, "textures/armor/hat.png");
	public static final ResourceLocation no9 = new ResourceLocation(RefStrings.MODID, "textures/armor/no9.png");
	public static final ResourceLocation no9_insignia = new ResourceLocation(RefStrings.MODID, "textures/armor/no9_insignia.png");
	public static final ResourceLocation goggles = new ResourceLocation(RefStrings.MODID, "textures/armor/goggles.png");
	
	public static final ResourceLocation player_manly_tex = new ResourceLocation(RefStrings.MODID, "textures/entity/player_fem.png"); 
	
	
	
	////Obj Entities
	
	//Boxcar
	public static final IModelCustom boxcar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/boxcar.obj"));
	public static final IModelCustom duchessgambit = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/duchessgambit.obj"));
	public static final IModelCustom building = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/building.obj"));
	public static final IModelCustom rpc = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rpc.obj"));
	public static final IModelCustom tom_main = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/tom_main.obj"));
	public static final IModelCustom tom_flame = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/weapons/tom_flame.hmf"));
	public static final IModelCustom nikonium = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/nikonium.obj"));
	
	//Projectiles
	public static final IModelCustom projectiles = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/projectiles/projectiles.obj"));
	public static final IModelCustom casings = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/effect/casings.obj"));
	
	//Bomber
	public static final IModelCustom dornier = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/dornier.obj"));
	public static final IModelCustom b29 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/b29.obj"));
	public static final IModelCustom Airliner = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/737.obj"));
	
	//Missiles
	public static final IModelCustom missileV2 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileV2.obj"));
	public static final IModelCustom missileStrong = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileGeneric.obj"));
	public static final IModelCustom missileHuge = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileHuge.obj"));
	public static final IModelCustom missileNuclear = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileNeon.obj"));
	public static final IModelCustom missileMIRV = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileMIRV.obj"));
	public static final IModelCustom missileThermo = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileThermo.obj"));
	public static final IModelCustom missileDoomsday = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileDoomsday.obj"));
	public static final IModelCustom missileTaint = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileTaint.obj"));
	public static final IModelCustom missileShuttle = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileShuttle.obj"));
	public static final IModelCustom missileCarrier = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileCarrier.obj"));
	public static final IModelCustom missileBooster = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missileBooster.obj"));
	public static final IModelCustom minerRocket = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/minerRocket.obj"));
	public static final IModelCustom soyuz = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/soyuz.obj")).asDisplayList();
	public static final IModelCustom soyuz_lander = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/soyuz_lander.obj")).asDisplayList();
	public static final IModelCustom soyuz_module = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/soyuz_module.obj")).asDisplayList();
	public static final IModelCustom soyuz_launcher_legs = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_legs.obj"), false).asDisplayList();
	public static final IModelCustom soyuz_launcher_table = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_table.obj"), false).asDisplayList();
	public static final IModelCustom soyuz_launcher_tower_base = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_tower_base.obj"), false).asDisplayList();
	public static final IModelCustom soyuz_launcher_tower = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_tower.obj"), false).asDisplayList();
	public static final IModelCustom soyuz_launcher_support_base = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_support_base.obj"), false).asDisplayList();
	public static final IModelCustom soyuz_launcher_support = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/launch_table/soyuz_launcher_support.obj"), false).asDisplayList();
	
	//Missile Parts
	public static final IModelCustom missile_pad = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missilePad.obj"));
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
	public static final IModelCustom mp_w_15_turbine = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_15_turbine.obj"));
	public static final IModelCustom mp_w_20 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/missile_parts/mp_w_20.obj"));
	
	//Carts
	public static final IModelCustom cart = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vehicles/cart.obj"));
	public static final IModelCustom cart_destroyer = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vehicles/cart_destroyer.obj"));
	public static final IModelCustom cart_powder = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vehicles/cart_powder.obj"));
	public static final IModelCustom train_cargo_tram = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vehicles/tram.obj"));
	public static final IModelCustom train_cargo_tram_trailer = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/vehicles/tram_trailer.obj"));
	
	////Texture Entities
	
	//Blast
	public static final ResourceLocation antimatter = new ResourceLocation(RefStrings.MODID, "textures/models/explosion/antimatter.png");
	public static final ResourceLocation fireball = new ResourceLocation(RefStrings.MODID, "textures/models/explosion/fireball.png");
	public static final ResourceLocation balefire = new ResourceLocation(RefStrings.MODID, "textures/models/explosion/balefire.png");
	public static final ResourceLocation tomblast = new ResourceLocation(RefStrings.MODID, "textures/models/explosion/tomblast.png");
	
	//Boxcar
	public static final ResourceLocation boxcar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/boxcar.png");
	public static final ResourceLocation duchessgambit_tex = new ResourceLocation(RefStrings.MODID, "textures/models/duchessgambit.png");
	public static final ResourceLocation building_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/building.png");
	public static final ResourceLocation rpc_tex = new ResourceLocation(RefStrings.MODID, "textures/models/rpc.png");
	public static final ResourceLocation tom_main_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/tom_main.png");
	public static final ResourceLocation tom_flame_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/tom_flame.png");
	public static final ResourceLocation nikonium_tex = new ResourceLocation(RefStrings.MODID, "textures/models/misc/nikonium.png");
	
	//Projectiles
	public static final ResourceLocation bullet_pistol_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/bullet_pistol.png");
	public static final ResourceLocation bullet_rifle_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/bullet_rifle.png");
	public static final ResourceLocation buckshot_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/pellet_buckshot.png");
	public static final ResourceLocation flechette_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/flechette.png");
	public static final ResourceLocation grenade_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/grenade.png");
	public static final ResourceLocation rocket_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/rocket.png");
	public static final ResourceLocation rocket_mirv_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/rocket_mirv.png");
	public static final ResourceLocation mini_nuke_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/mini_nuke.png");
	public static final ResourceLocation mini_mirv_tex = new ResourceLocation(RefStrings.MODID, "textures/models/projectiles/mini_mirv.png");
	public static final ResourceLocation casings_tex = new ResourceLocation(RefStrings.MODID, "textures/particle/casings.png");
	
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
	public static final ResourceLocation airliner_tex = new ResourceLocation(RefStrings.MODID, "textures/models/airlinerplaceholder.png");
	
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
	public static final ResourceLocation missileVolcano_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileNeonV.png");
	public static final ResourceLocation missileEndo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileEndo.png");
	public static final ResourceLocation missileExo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileExo.png");
	public static final ResourceLocation missileDoomsday_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileDoomsday.png");
	public static final ResourceLocation missileTaint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileTaint.png");
	public static final ResourceLocation missileShuttle_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileShuttle.png");
	public static final ResourceLocation missileMicro_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicro.png");
	public static final ResourceLocation missileCarrier_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileCarrier.png");
	public static final ResourceLocation missileBooster_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileBooster.png");
	public static final ResourceLocation minerRocket_tex = new ResourceLocation(RefStrings.MODID, "textures/models/minerRocket.png");
	public static final ResourceLocation bobmazon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bobmazon.png");
	public static final ResourceLocation siege_dropship_tex = new ResourceLocation(RefStrings.MODID, "textures/models/siege_dropship.png");
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

	public static final ResourceLocation soyuz_lander_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_capsule/soyuz_lander.png");
	public static final ResourceLocation soyuz_lander_rust_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_capsule/soyuz_lander_rust.png");
	public static final ResourceLocation soyuz_chute_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_capsule/soyuz_chute.png");

	public static final ResourceLocation soyuz_module_dome_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_capsule/module_dome.png");
	public static final ResourceLocation soyuz_module_lander_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_capsule/module_lander.png");
	public static final ResourceLocation soyuz_module_propulsion_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_capsule/module_propulsion.png");
	public static final ResourceLocation soyuz_module_solar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_capsule/module_solar.png");

	public static final ResourceLocation soyuz_launcher_legs_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_leg.png");
	public static final ResourceLocation soyuz_launcher_table_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_table.png");
	public static final ResourceLocation soyuz_launcher_tower_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_tower_base.png");
	public static final ResourceLocation soyuz_launcher_tower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_tower.png");
	public static final ResourceLocation soyuz_launcher_support_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_support_base.png");
	public static final ResourceLocation soyuz_launcher_support_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz_launcher/launcher_support.png");
	
	//Missile Parts
	public static final ResourceLocation missile_pad_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missilePad.png");
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
	public static final ResourceLocation mp_f_10_hydrazine_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_hydrazine.png");
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
	public static final ResourceLocation mp_f_15_solid_mimi_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_mimi.png");

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
	public static final ResourceLocation mp_w_15_nuclear_mimi_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_nuclear_mimi.png");
	public static final ResourceLocation mp_w_15_n2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_n2.png");
	public static final ResourceLocation mp_w_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_balefire.png");
	public static final ResourceLocation mp_w_15_turbine_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_turbine.png");
	
	//Carts
	public static final ResourceLocation cart_metal = new ResourceLocation(RefStrings.MODID, "textures/entity/cart_metal.png");
	public static final ResourceLocation cart_blank = new ResourceLocation(RefStrings.MODID, "textures/entity/cart_metal_naked.png");
	public static final ResourceLocation cart_wood = new ResourceLocation(RefStrings.MODID, "textures/entity/cart_wood.png");
	public static final ResourceLocation cart_destroyer_tex = new ResourceLocation(RefStrings.MODID, "textures/entity/cart_destroyer.png");
	public static final ResourceLocation cart_powder_tex = new ResourceLocation(RefStrings.MODID, "textures/blocks/block_gunpowder.png");
	public static final ResourceLocation cart_semtex_side = new ResourceLocation(RefStrings.MODID, "textures/blocks/semtex_side.png");
	public static final ResourceLocation cart_semtex_top = new ResourceLocation(RefStrings.MODID, "textures/blocks/semtex_bottom.png");
	public static final ResourceLocation train_tram = new ResourceLocation(RefStrings.MODID, "textures/models/trains/tram.png");
	public static final ResourceLocation tram_trailer = new ResourceLocation(RefStrings.MODID, "textures/models/trains/tram_trailer.png");
	
	//ISBRHs
	public static final IModelCustom scaffold = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/scaffold.obj"));
	public static final IModelCustom taperecorder = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/taperecorder.obj"));
	public static final IModelCustom beam = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/beam.obj"));
	public static final IModelCustom barrel = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/barrel.obj"));
	public static final IModelCustom pole = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/pole.obj"));
	public static final IModelCustom barbed_wire = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/barbed_wire.obj"));
	public static final IModelCustom spikes = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/spikes.obj"));
	public static final IModelCustom antenna_top = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/antenna_top.obj"));
	public static final IModelCustom conservecrate = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/conservecrate.obj"));
	public static final IModelCustom pipe = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/pipe.obj"));
	public static final IModelCustom pipe_rim = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/pipe_rim.obj"));
	public static final IModelCustom pipe_quad = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/pipe_quad.obj"));
	public static final IModelCustom pipe_frame = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/pipe_frame.obj"));
	public static final IModelCustom rtty = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/rtty.obj"));

	public static final IModelCustom deco_computer = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/puter.obj"));
	
	public static final IModelCustom rbmk_element = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rbmk/rbmk_element.obj"));
	public static final IModelCustom rbmk_reflector = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rbmk/rbmk_reflector.obj"));
	public static final IModelCustom rbmk_rods = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rbmk/rbmk_rods.obj"));
	public static final IModelCustom rbmk_crane_console = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/rbmk/crane_console.obj"));
	public static final IModelCustom rbmk_crane = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/rbmk/crane.obj"));
	public static final IModelCustom rbmk_console = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/rbmk/rbmk_console.obj"));
	public static final IModelCustom rbmk_debris = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/rbmk/debris.obj"));
	public static final ResourceLocation rbmk_crane_console_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/crane_console.png");
	public static final ResourceLocation rbmk_crane_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/rbmk_crane.png");
	public static final ResourceLocation rbmk_console_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/rbmk_control.png");
	public static final IModelCustom hev_battery = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/battery.obj"));
	public static final IModelCustom anvil = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/anvil.obj"));
	public static final IModelCustom crystal_power = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/crystals_power.obj"));
	public static final IModelCustom crystal_energy = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/crystals_energy.obj"));
	public static final IModelCustom crystal_robust = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/crystals_robust.obj"));
	public static final IModelCustom crystal_trixite = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/crystals_trixite.obj"));
	public static final IModelCustom cable_neo = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/cable_neo.obj"));
	public static final IModelCustom pipe_neo = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/pipe_neo.obj"));
	public static final IModelCustom difurnace_extension = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/difurnace_extension.obj"));
	public static final IModelCustom splitter = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/splitter.obj"));
	public static final IModelCustom rail_narrow_straight = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/rail_narrow.obj"));
	public static final IModelCustom rail_narrow_curve = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/rail_narrow_bend.obj"));
	public static final IModelCustom rail_standard_straight = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/rail_standard.obj"));
	public static final IModelCustom rail_standard_curve = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/rail_standard_bend.obj"));
	public static final IModelCustom rail_standard_ramp = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/rail_standard_ramp.obj"));
	public static final IModelCustom capacitor = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/capacitor.obj"));

	public static final IModelCustom charge_dynamite = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/charge_dynamite.obj"));
	public static final IModelCustom charge_c4 = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/blocks/charge_c4.obj"));
	
	//RBMK DEBRIS
	public static final IModelCustom deb_blank = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/projectiles/deb_blank.obj"));
	public static final IModelCustom deb_element = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/projectiles/deb_element.obj"));
	public static final IModelCustom deb_fuel = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/projectiles/deb_fuel.obj"));
	public static final IModelCustom deb_rod = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/projectiles/deb_rod.obj"));
	public static final IModelCustom deb_lid = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/projectiles/deb_lid.obj"));
	public static final IModelCustom deb_graphite = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/projectiles/deb_graphite.obj"));

	public static final IModelCustom deb_zirnox_blank = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/zirnox/deb_blank.obj"));
	public static final IModelCustom deb_zirnox_concrete = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/zirnox/deb_concrete.obj"));
	public static final IModelCustom deb_zirnox_element = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/zirnox/deb_element.obj"));
	public static final IModelCustom deb_zirnox_exchanger = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/zirnox/deb_exchanger.obj"));
	public static final IModelCustom deb_zirnox_shrapnel = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/zirnox/deb_shrapnel.obj"));
	
	public static void loadAnimatedModels(){
		transition_seal = ColladaLoader.load(new ResourceLocation(RefStrings.MODID, "models/doors/seal.dae"), true);
		transition_seal_anim = ColladaLoader.loadAnim(24040, new ResourceLocation(RefStrings.MODID, "models/doors/seal.dae"));
	}
}
