package com.hbm.blocks;

import com.hbm.blocks.BlockEnums.*;
import com.hbm.blocks.bomb.*;
import com.hbm.blocks.fluid.*;
import com.hbm.blocks.gas.*;
import com.hbm.blocks.generic.*;
import com.hbm.blocks.generic.BlockHazard.ExtDisplayEffect;
import com.hbm.blocks.machine.*;
import com.hbm.blocks.machine.albion.*;
import com.hbm.blocks.machine.pile.*;
import com.hbm.blocks.machine.rbmk.*;
import com.hbm.blocks.network.*;
import com.hbm.blocks.rail.*;
import com.hbm.blocks.test.*;
import com.hbm.blocks.turret.*;
import com.hbm.items.block.*;
import com.hbm.items.bomb.ItemPrototypeBlock;
import com.hbm.items.special.ItemOreBlock;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.DoorDecl;
import com.hbm.tileentity.machine.storage.TileEntityFileCabinet;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.Loader;

import java.util.ArrayList;

public class ModBlocks {

	public static void mainRegistry() {
		initializeBlock();
		registerBlock();
	}

	public static Block event_tester;
	public static Block obj_tester;
	public static Block test_core;
	public static Block test_charge;
	public static Block structure_anchor;

	public static Block ore_uranium;
	public static Block ore_uranium_scorched;
	public static Block ore_titanium;
	public static Block ore_sulfur;
	public static Block ore_thorium;
	public static Block ore_niter;
	public static Block ore_copper;
	public static Block ore_tungsten;
	public static Block ore_aluminium;
	public static Block ore_fluorite;
	public static Block ore_lead;
	public static Block ore_schrabidium;
	public static Block ore_beryllium;
	public static Block ore_australium;
	public static Block ore_rare;
	public static Block ore_cobalt;
	public static Block ore_cinnebar;
	public static Block ore_coltan;
	public static Block ore_alexandrite;

	public static Block ore_bedrock;
	public static Block ore_volcano;

	public static Block ore_nether_coal;
	public static Block ore_nether_smoldering;
	public static Block ore_nether_uranium;
	public static Block ore_nether_uranium_scorched;
	public static Block ore_nether_plutonium;
	public static Block ore_nether_tungsten;
	public static Block ore_nether_sulfur;
	public static Block ore_nether_fire;
	public static Block ore_nether_cobalt;
	public static Block ore_nether_schrabidium;

	public static Block ore_meteor;

	public static Block stone_gneiss;
	public static Block ore_gneiss_iron;
	public static Block ore_gneiss_gold;
	public static Block ore_gneiss_uranium;
	public static Block ore_gneiss_uranium_scorched;
	public static Block ore_gneiss_copper;
	public static Block ore_gneiss_asbestos;
	public static Block ore_gneiss_lithium;
	public static Block ore_gneiss_schrabidium;
	public static Block ore_gneiss_rare;
	public static Block ore_gneiss_gas;

	public static Block gneiss_brick;
	public static Block gneiss_tile;
	public static Block gneiss_chiseled;

	public static Block stone_depth;
	public static Block ore_depth_cinnebar;
	public static Block ore_depth_zirconium;
	public static Block ore_depth_borax;
	public static Block cluster_depth_iron;
	public static Block cluster_depth_titanium;
	public static Block cluster_depth_tungsten;

	public static Block stone_keyhole;
	public static Block stone_keyhole_meta;

	public static Block stone_depth_nether;
	public static Block ore_depth_nether_neodymium;

	public static Block stone_porous;
	public static Block stone_resource;
	public static Block stalagmite;
	public static Block stalactite;
	public static Block stone_biome;

	public static Block depth_brick;
	public static Block depth_tiles;
	public static Block depth_nether_brick;
	public static Block depth_nether_tiles;
	public static Block depth_dnt;

	public static Block basalt;
	public static Block ore_basalt;
	public static Block basalt_smooth;
	public static Block basalt_brick;
	public static Block basalt_polished;
	public static Block basalt_tiles;

	public static Block cluster_iron;
	public static Block cluster_titanium;
	public static Block cluster_aluminium;
	public static Block cluster_copper;

	public static Block ore_oil;
	public static Block ore_oil_empty;
	public static Block ore_oil_sand;
	public static Block ore_bedrock_oil;
	public static Block ore_lignite;
	public static Block ore_asbestos;
	@Deprecated public static Block ore_coal_oil;
	@Deprecated public static Block ore_coal_oil_burning;

	public static Block ore_tikite;

	public static Block block_thorium;
	public static Block block_thorium_fuel;
	public static Block block_uranium;
	public static Block block_u233;
	public static Block block_u235;
	public static Block block_u238;
	public static Block block_uranium_fuel;
	public static Block block_neptunium;
	public static Block block_polonium;
	public static Block block_mox_fuel;
	public static Block block_plutonium;
	public static Block block_pu238;
	public static Block block_pu239;
	public static Block block_pu240;
	public static Block block_pu_mix;
	public static Block block_plutonium_fuel;
	public static Block block_titanium;
	public static Block block_sulfur;
	public static Block block_niter;
	public static Block block_copper;
	public static Block block_red_copper;
	public static Block block_tungsten;
	public static Block block_aluminium;
	public static Block block_fluorite;
	public static Block block_steel;
	public static Block block_tcalloy;
	public static Block block_cdalloy;
	public static Block block_lead;
	public static Block block_bismuth;
	public static Block block_cadmium;
	public static Block block_coltan;
	public static Block block_tantalium;
	public static Block block_niobium;
	public static Block block_trinitite;
	public static Block block_waste;
	public static Block block_waste_painted;
	public static Block block_waste_vitrified;
	public static Block ancient_scrap;
	public static Block block_corium;
	public static Block block_corium_cobble;
	public static Block block_scrap;
	public static Block block_electrical_scrap;
	public static Block block_beryllium;
	public static Block block_schraranium;
	public static Block block_schrabidium;
	public static Block block_schrabidate;
	public static Block block_solinium;
	public static Block block_schrabidium_fuel;
	public static Block block_euphemium;
	public static Block block_schrabidium_cluster;
	public static Block block_euphemium_cluster;
	public static Block block_dineutronium;
	public static Block block_advanced_alloy;
	public static Block block_magnetized_tungsten;
	public static Block block_combine_steel;
	public static Block block_desh;
	public static Block block_dura_steel;
	public static Block block_starmetal;
	public static Block block_polymer;
	public static Block block_bakelite;
	public static Block block_rubber;
	public static Block block_yellowcake;
	public static Block block_insulator;
	public static Block block_fiberglass;
	public static Block block_asbestos;
	public static Block block_cobalt;
	public static Block block_lithium;
	public static Block block_zirconium;
	public static Block block_white_phosphorus;
	public static Block block_red_phosphorus;
	public static Block block_fallout;
	public static Block block_foam;
	public static Block block_coke;
	public static Block block_graphite;
	public static Block block_graphite_drilled;
	public static Block block_graphite_fuel;
	public static Block block_graphite_plutonium;
	public static Block block_graphite_rod;
	public static Block block_graphite_source;
	public static Block block_graphite_lithium;
	public static Block block_graphite_tritium;
	public static Block block_graphite_detector;
	public static Block block_boron;
	public static Block block_lanthanium;
	public static Block block_ra226;
	public static Block block_actinium;
	public static Block block_tritium;
	public static Block block_semtex;
	public static Block block_c4;
	public static Block block_smore;
	public static Block block_slag;

	public static Block block_australium;
	public static Block block_weidanium;
	public static Block block_reiium;
	public static Block block_unobtainium;
	public static Block block_daffergon;
	public static Block block_verticium;

	public static Block block_cap;

	public static Block deco_titanium;
	public static Block deco_red_copper;
	public static Block deco_tungsten;
	public static Block deco_aluminium;
	public static Block deco_steel;
	public static Block deco_rusty_steel;
	public static Block deco_lead;
	public static Block deco_beryllium;
	public static Block deco_asbestos;
	public static Block deco_rbmk;
	public static Block deco_rbmk_smooth;

	public static Block deco_emitter;
	public static Block part_emitter;
	public static Block deco_loot;
	public static Block pedestal;
	public static Block skeleton_holder;
	public static Block bobblehead;
	public static Block snowglobe;
	public static Block plushie;
	public static Block dungeon_spawner;

	public static Block gravel_obsidian;
	public static Block gravel_diamond;
	public static Block asphalt;
	public static Block asphalt_light;

	public static Block sandbags;
	public static Block wood_barrier;
	public static Block wood_structure;

	public static Block reinforced_brick;
	public static Block reinforced_ducrete;
	public static Block reinforced_glass;
	public static Block reinforced_glass_pane;
	public static Block reinforced_light;
	public static Block reinforced_sand;
	public static Block reinforced_lamp_off;
	public static Block reinforced_lamp_on;
	public static Block reinforced_laminate;
	public static Block reinforced_laminate_pane;

	public static Block lamp_tritium_green_off;
	public static Block lamp_tritium_green_on;
	public static Block lamp_tritium_blue_off;
	public static Block lamp_tritium_blue_on;

	public static Block lamp_demon;

	public static Block lantern;
	public static Block lantern_behemoth;

	public static Block spotlight_incandescent;
	public static Block spotlight_incandescent_off;
	public static Block spotlight_fluoro;
	public static Block spotlight_fluoro_off;
	public static Block spotlight_halogen;
	public static Block spotlight_halogen_off;
	public static Block spotlight_beam;
	public static Block floodlight;
	public static Block floodlight_beam;

	public static Block rebar;
	public static Block reinforced_stone;
	public static Block concrete_smooth;
	public static Block concrete_colored;
	public static Block concrete_colored_ext;
	public static Block concrete;
	public static Block concrete_asbestos;
	public static Block concrete_rebar;
	public static Block concrete_super;
	public static Block concrete_super_broken;
	public static Block ducrete_smooth;
	public static Block ducrete;
	public static Block concrete_pillar;
	public static Block brick_concrete;
	public static Block brick_concrete_mossy;
	public static Block brick_concrete_cracked;
	public static Block brick_concrete_broken;
	public static Block brick_concrete_marked;
	public static Block brick_ducrete;
	public static Block brick_obsidian;
	public static Block brick_light;
	public static Block brick_compound;
	public static Block brick_asbestos;
	public static Block brick_fire;

	public static Block lightstone;

	public static Block concrete_slab;
	public static Block concrete_double_slab;
	public static Block concrete_brick_slab;
	public static Block concrete_brick_double_slab;
	public static Block brick_slab;
	public static Block brick_double_slab;
	public static Block stones_slab;
	public static Block stones_double_slab;

	public static Block concrete_smooth_stairs;
	public static Block concrete_stairs;
	public static Block concrete_asbestos_stairs;
	public static Block ducrete_smooth_stairs;
	public static Block ducrete_stairs;
	public static Block brick_concrete_stairs;
	public static Block brick_concrete_mossy_stairs;
	public static Block brick_concrete_cracked_stairs;
	public static Block brick_concrete_broken_stairs;
	public static Block brick_ducrete_stairs;
	public static Block reinforced_stone_stairs;
	public static Block reinforced_brick_stairs;
	public static Block brick_obsidian_stairs;
	public static Block brick_light_stairs;
	public static Block brick_compound_stairs;
	public static Block brick_asbestos_stairs;
	public static Block brick_fire_stairs;
	public static Block asphalt_stairs;
	public static Block lightstone_tile_stairs;
	public static Block lightstone_bricks_stairs;

	public static Block cmb_brick;
	public static Block cmb_brick_reinforced;

	public static Block vinyl_tile;

	public static Block tile_lab;
	public static Block tile_lab_cracked;
	public static Block tile_lab_broken;

	public static Block block_meteor;
	public static Block block_meteor_cobble;
	public static Block block_meteor_broken;
	public static Block block_meteor_molten;
	public static Block block_meteor_treasure;
	public static Block meteor_polished;
	public static Block meteor_brick;
	public static Block meteor_brick_mossy;
	public static Block meteor_brick_cracked;
	public static Block meteor_brick_chiseled;
	public static Block meteor_pillar;
	public static Block meteor_spawner;
	public static Block meteor_battery;

	public static Block moon_turf;

	public static Block brick_jungle;
	public static Block brick_jungle_cracked;
	public static Block brick_jungle_fragile;
	public static Block brick_jungle_lava;
	public static Block brick_jungle_ooze;
	public static Block brick_jungle_mystic;
	public static Block brick_jungle_trap;
	public static Block brick_jungle_glyph;
	public static Block brick_jungle_circle;

	public static Block brick_red;

	public static Block deco_computer;
	public static Block deco_crt;
	public static Block deco_toaster;

	public static Block filing_cabinet;

	public static Block tape_recorder;
	public static Block steel_poles;
	public static Block pole_top;
	public static Block pole_satellite_receiver;
	public static Block steel_wall;
	public static Block steel_corner;
	public static Block steel_roof;
	public static Block steel_beam;
	public static Block steel_scaffold;
	public static Block steel_grate;
	public static Block steel_grate_wide;

	public static Block deco_pipe;
	public static Block deco_pipe_rusted;
	public static Block deco_pipe_green;
	public static Block deco_pipe_green_rusted;
	public static Block deco_pipe_red;
	public static Block deco_pipe_marked;
	public static Block deco_pipe_rim;
	public static Block deco_pipe_rim_rusted;
	public static Block deco_pipe_rim_green;
	public static Block deco_pipe_rim_green_rusted;
	public static Block deco_pipe_rim_red;
	public static Block deco_pipe_rim_marked;
	public static Block deco_pipe_framed;
	public static Block deco_pipe_framed_rusted;
	public static Block deco_pipe_framed_green;
	public static Block deco_pipe_framed_green_rusted;
	public static Block deco_pipe_framed_red;
	public static Block deco_pipe_framed_marked;
	public static Block deco_pipe_quad;
	public static Block deco_pipe_quad_rusted;
	public static Block deco_pipe_quad_green;
	public static Block deco_pipe_quad_green_rusted;
	public static Block deco_pipe_quad_red;
	public static Block deco_pipe_quad_marked;

	public static Block broadcaster_pc;
	public static Block geiger;
	public static Block hev_battery;

	public static Block fence_metal;

	public static Block sand_boron;
	public static Block sand_lead;
	public static Block sand_uranium;
	public static Block sand_polonium;
	public static Block sand_quartz;
	public static Block ash_digamma;
	public static Block glass_boron;
	public static Block glass_lead;
	public static Block glass_uranium;
	public static Block glass_trinitite;
	public static Block glass_polonium;
	public static Block glass_ash;
	public static Block glass_quartz;
	public static Block glass_polarized;

	public static Block mush;
	public static Block mush_block;
	public static Block mush_block_stem;

	public static Block glyphid_base;
	public static Block glyphid_spawner;

	public static Block plant_flower;
	public static Block plant_tall;
	public static Block plant_dead;
	public static Block reeds;
	public static Block vine_phosphor;
	public static final Material thick_foliage = new MaterialLogic(MapColor.foliageColor) {
		@Override public boolean getCanBurn()  { return true; }
		@Override public boolean isToolNotRequired() { return false; }
		@Override public int getMaterialMobility() { return 1; }
	};

	public static Block waste_earth;
	public static Block waste_mycelium;
	public static Block waste_trinitite;
	public static Block waste_trinitite_red;
	public static Block waste_log;
	public static Block waste_leaves;
	public static Block waste_planks;
	public static Block frozen_dirt;
	public static Block frozen_grass;
	public static Block frozen_log;
	public static Block frozen_planks;
	public static Block dirt_dead;
	public static Block dirt_oily;
	public static Block sand_dirty;
	public static Block sand_dirty_red;
	public static Block stone_cracked;
	public static Block burning_earth;
	public static Block tektite;
	public static Block ore_tektite_osmiridium;
	public static Block impact_dirt;

	public static Block fallout;
	public static Block foam_layer;
	public static Block sand_boron_layer;
	public static Block leaves_layer;
	public static Block oil_spill;

	public static Block sellafield_slaked;
	public static Block sellafield_bedrock;
	public static Block sellafield;
	public static Block ore_sellafield_diamond;
	public static Block ore_sellafield_emerald;
	public static Block ore_sellafield_uranium_scorched;
	public static Block ore_sellafield_schrabidium;
	public static Block ore_sellafield_radgem;

	public static Block geysir_water;
	public static Block geysir_chlorine;
	public static Block geysir_vapor;
	public static Block geysir_nether;

	public static Block flame_war;
	public static Block float_bomb;
	public static Block therm_endo;
	public static Block therm_exo;
	public static Block emp_bomb;
	public static Block det_cord;
	public static Block det_charge;
	public static Block det_nuke;
	public static Block det_miner;
	public static Block red_barrel;
	public static Block pink_barrel;
	public static Block yellow_barrel;
	public static Block vitrified_barrel;
	public static Block lox_barrel;
	public static Block taint_barrel;
	public static Block crashed_balefire;
	public static Block fireworks;
	public static Block dynamite;
	public static Block tnt;
	public static Block semtex;
	public static Block c4;
	public static Block fissure_bomb;

	public static Block charge_dynamite;
	public static Block charge_miner;
	public static Block charge_c4;
	public static Block charge_semtex;

	public static Block mine_ap;
	public static Block mine_he;
	public static Block mine_shrap;
	public static Block mine_fat;
	public static Block mine_naval;

	public static Block crate;
	public static Block crate_weapon;
	public static Block crate_lead;
	public static Block crate_metal;
	public static Block crate_red;
	public static Block crate_can;
	public static Block crate_ammo;
	public static Block crate_jungle;

	public static Block boxcar;
	public static Block boat;

	public static Block seal_frame;
	public static Block seal_controller;
	public static Block seal_hatch;

	public static Block vault_door;
	public static Block blast_door;
	public static Block sliding_blast_door;
	public static Block fire_door;
	public static Block transition_seal;
	public static Block silo_hatch;
	public static Block silo_hatch_large;

	// 1.12.2 Doors
	public static Block secure_access_door;
	public static Block large_vehicle_door;
	public static Block qe_containment;
	public static Block qe_sliding_door;
	public static Block round_airlock_door;
	public static Block sliding_seal_door;
	public static Block water_door;

	public static Block door_metal;
	public static Block door_office;
	public static Block door_bunker;
	public static Block door_red;

	public static Block barbed_wire;
	public static Block barbed_wire_fire;
	public static Block barbed_wire_poison;
	public static Block barbed_wire_acid;
	public static Block barbed_wire_wither;
	public static Block barbed_wire_ultradeath;
	public static Block spikes;

	public static Block charger;
	public static Block refueler;

	public static Block tesla;

	public static Block sat_mapper;
	public static Block sat_scanner;
	public static Block sat_radar;
	public static Block sat_laser;
	public static Block sat_foeq;
	public static Block sat_resonator;

	public static Block sat_dock;

	public static Block soyuz_capsule;
	public static Block crate_supply;

	public static Block crate_iron;
	public static Block crate_steel;
	public static Block crate_desh;
	public static Block crate_tungsten;
	public static Block crate_template;
	public static Block safe;
	public static Block mass_storage;

	public static Block nuke_gadget;
	public static Block nuke_boy;
	public static Block nuke_man;
	public static Block nuke_mike;
	public static Block nuke_tsar;
	public static Block nuke_fleija;
	public static Block nuke_prototype;
	public static Block nuke_custom;
	public static Block nuke_solinium;
	public static Block nuke_n2;
	public static Block nuke_fstbmb;
	public static Block bomb_multi;

	public static Block pump_steam;
	public static Block pump_electric;

	public static Block heater_firebox;
	public static Block heater_oven;
	public static Block heater_oilburner;
	public static Block heater_electric;
	public static Block heater_heatex;
	public static Block machine_ashpit;

	public static Block furnace_iron;
	public static Block furnace_steel;
	public static Block furnace_combination;
	public static Block machine_stirling;
	public static Block machine_stirling_steel;
	public static Block machine_stirling_creative;
	public static Block machine_sawmill;
	public static Block machine_crucible;
	public static Block machine_boiler;
	public static Block machine_industrial_boiler;

	public static Block foundry_mold;
	public static Block foundry_basin;
	public static Block foundry_channel;
	public static Block foundry_tank;
	public static Block foundry_outlet;
	public static Block machine_strand_caster;
	public static Block foundry_slagtap;
	public static Block slag;

	public static Block machine_difurnace_off;
	public static Block machine_difurnace_on;
	public static Block machine_difurnace_extension;
	public static Block machine_difurnace_rtg_off;
	public static Block machine_difurnace_rtg_on;
	//public static final int guiID_test_difurnace = 1; historical

	public static Block machine_centrifuge;
	public static Block machine_gascent;

	public static Block machine_fel;
	public static Block machine_silex;

	public static Block machine_rotary_furnace;
	public static Block machine_crystallizer;

	public static Block machine_uf6_tank;
	public static Block machine_puf6_tank;

	public static Block machine_reactor_breeding;

	public static Block machine_furnace_brick_off;
	public static Block machine_furnace_brick_on;
	public static Block machine_rtg_furnace_off;
	public static Block machine_rtg_furnace_on;

	public static Block machine_industrial_generator;

	public static Block machine_cyclotron;
	public static Block machine_exposure_chamber;

	public static Block hadron_plating;
	public static Block hadron_plating_blue;
	public static Block hadron_plating_black;
	public static Block hadron_plating_yellow;
	public static Block hadron_plating_striped;
	public static Block hadron_plating_voltz;
	public static Block hadron_plating_glass;
	public static Block hadron_coil_alloy;
	public static Block hadron_coil_gold;
	public static Block hadron_coil_neodymium;
	public static Block hadron_coil_magtung;
	public static Block hadron_coil_schrabidium;
	public static Block hadron_coil_schrabidate;
	public static Block hadron_coil_starmetal;
	public static Block hadron_coil_chlorophyte;
	public static Block hadron_coil_mese;
	public static Block hadron_power;
	public static Block hadron_power_10m;
	public static Block hadron_power_100m;
	public static Block hadron_power_1g;
	public static Block hadron_power_10g;
	public static Block hadron_diode;
	public static Block hadron_analysis;
	public static Block hadron_analysis_glass;
	public static Block hadron_access;
	public static Block hadron_core;

	public static Block pa_source;
	public static Block pa_beamline;
	public static Block pa_rfc;
	public static Block pa_quadrupole;
	public static Block pa_dipole;
	public static Block pa_detector;

	public static Block machine_electric_furnace_off;
	public static Block machine_electric_furnace_on;

	public static Block machine_microwave;

	//public static Block machine_deuterium;

	public static Block machine_battery_potato;
	public static Block machine_battery;
	public static Block machine_lithium_battery;
	public static Block machine_schrabidium_battery;
	public static Block machine_dineutronium_battery;
	public static Block machine_fensu;
	public static final int guiID_machine_fensu = 99;

	public static Block capacitor_bus;
	public static Block capacitor_copper;
	public static Block capacitor_gold;
	public static Block capacitor_niobium;
	public static Block capacitor_tantalium;
	public static Block capacitor_schrabidate;

	public static Block machine_wood_burner;

	public static Block red_wire_coated;
	public static Block red_cable;
	public static Block red_cable_classic;
	public static Block red_cable_paintable;
	public static Block red_cable_gauge;
	public static Block red_connector;
	public static Block red_pylon;
	public static Block red_pylon_medium_wood;
	public static Block red_pylon_medium_wood_transformer;
	public static Block red_pylon_medium_steel;
	public static Block red_pylon_medium_steel_transformer;
	public static Block red_pylon_large;
	public static Block substation;
	public static Block cable_switch;
	public static Block cable_detector;
	public static Block cable_diode;
	public static Block machine_detector;
	public static Block fluid_duct_neo;
	public static Block fluid_duct_box;
	public static Block fluid_duct_paintable;
	public static Block fluid_duct_gauge;
	public static Block fluid_duct_exhaust;
	public static Block fluid_duct_paintable_block_exhaust;
	public static Block fluid_valve;
	public static Block fluid_switch;
	public static Block fluid_pump;
	public static Block machine_drain;
	public static Block radio_torch_sender;
	public static Block radio_torch_receiver;
	public static Block radio_torch_counter;
	public static Block radio_torch_logic;
	public static Block radio_torch_reader;
	public static Block radio_torch_controller;
	public static Block radio_telex;
	public static Block oc_cable_paintable;

	public static Block conveyor;
	public static Block conveyor_express;
	//public static Block conveyor_classic;
	public static Block conveyor_double;
	public static Block conveyor_triple;
	public static Block conveyor_chute;
	public static Block conveyor_lift;
	public static Block crane_extractor;
	public static Block crane_inserter;
	public static Block crane_grabber;
	public static Block crane_router;
	public static Block crane_boxer;
	public static Block crane_unboxer;
	public static Block crane_splitter;
	public static Block crane_partitioner;

	public static Block drone_waypoint;
	public static Block drone_crate;
	public static Block drone_waypoint_request;
	public static Block drone_dock;
	public static Block drone_crate_provider;
	public static Block drone_crate_requester;

	public static Block pneumatic_tube;
	public static Block pneumatic_tube_paintable;

	public static Block fan;

	public static Block piston_inserter;

	public static Block chain;

	public static Block ladder_sturdy;
	public static Block ladder_iron;
	public static Block ladder_gold;
	public static Block ladder_aluminium;
	public static Block ladder_copper;
	public static Block ladder_titanium;
	public static Block ladder_lead;
	public static Block ladder_cobalt;
	public static Block ladder_steel;
	public static Block ladder_tungsten;

	public static Block trapdoor_steel;

	public static Block barrel_plastic;
	public static Block barrel_corroded;
	public static Block barrel_iron;
	public static Block barrel_steel;
	public static Block barrel_tcalloy;
	public static Block barrel_antimatter;

	public static Block machine_transformer;
	public static Block machine_transformer_dnt;

	public static Block machine_solar_boiler;
	public static Block solar_mirror;

	public static Block struct_launcher;
	public static Block struct_scaffold;
	public static Block struct_launcher_core;
	public static Block struct_launcher_core_large;
	public static Block struct_soyuz_core;
	public static Block struct_iter_core;
	public static Block struct_plasma_core;
	public static Block struct_watz_core;
	public static Block struct_icf_core;

	public static Block factory_titanium_hull;
	public static Block factory_advanced_hull;

	public static Block cm_block;
	public static Block cm_sheet;
	public static Block cm_engine;
	public static Block cm_tank;
	public static Block cm_circuit;
	public static Block cm_port;
	public static Block cm_flux;
	public static Block cm_heat;
	public static Block custom_machine;
	public static Block cm_anchor;

	public static Block pwr_fuel;
	public static Block pwr_control;
	public static Block pwr_channel;
	public static Block pwr_heatex;
	public static Block pwr_heatsink;
	public static Block pwr_neutron_source;
	public static Block pwr_reflector;
	public static Block pwr_casing;
	public static Block pwr_port;
	public static Block pwr_controller;
	public static Block pwr_block;

	public static Block fusion_conductor;
	public static Block fusion_center;
	public static Block fusion_motor;
	public static Block fusion_heater;
	public static Block fusion_hatch;
	public static Block plasma;

	public static Block iter;
	public static Block plasma_heater;

	public static Block machine_icf_press;
	public static Block icf_component;
	public static Block icf;
	public static Block icf_controller;
	public static Block icf_laser_component;
	public static Block icf_block;

	public static Block watz;
	public static Block watz_pump;

	public static Block watz_element;
	public static Block watz_cooler;
	public static Block watz_end;

	public static Block balefire;
	public static Block fire_digamma;
	public static Block digamma_matter;

	public static Block dfc_emitter;
	public static Block dfc_injector;
	public static Block dfc_receiver;
	public static Block dfc_stabilizer;
	public static Block dfc_core;

	public static Block machine_converter_he_rf;
	public static Block machine_converter_rf_he;

	public static Block machine_schrabidium_transmutator;

	public static Block machine_diesel;
	public static Block machine_combustion_engine;

	public static Block machine_shredder;

	public static Block machine_teleporter;
	public static Block teleanchor;
	public static Block field_disturber;

	public static Block machine_rtg_grey;
	public static Block machine_amgen;
	public static Block machine_geo;
	public static Block machine_minirtg;
	public static Block machine_powerrtg;
	public static Block machine_radiolysis;
	public static Block machine_hephaestus;

	public static Block machine_well;
	public static Block oil_pipe;
	public static Block machine_pumpjack;
	public static Block machine_fracking_tower;

	public static Block machine_flare;
	public static Block chimney_brick;
	public static Block chimney_industrial;

	public static Block machine_refinery;
	public static Block machine_vacuum_distill;
	public static Block machine_fraction_tower;
	public static Block fraction_spacer;
	public static Block machine_catalytic_cracker;
	public static Block machine_catalytic_reformer;
	public static Block machine_hydrotreater;
	public static Block machine_coker;
	public static Block machine_pyrooven;

	public static Block machine_boiler_off;

	public static Block machine_steam_engine;
	public static Block machine_turbine;
	public static Block machine_large_turbine;

	public static Block machine_deuterium_extractor;
	public static Block machine_deuterium_tower;

	public static Block machine_liquefactor;
	public static Block machine_solidifier;
	public static Block machine_intake;
	public static Block machine_compressor;
	public static Block machine_compressor_compact;

	public static Block machine_chungus;
	public static Block machine_condenser;
	public static Block machine_tower_small;
	public static Block machine_tower_large;
	public static Block machine_condenser_powered;

	public static Block machine_electrolyser;

	public static Block machine_excavator;
	public static Block machine_ore_slopper;
	public static Block machine_autosaw;

	public static Block machine_mining_laser;
	public static Block barricade; // a sand bag that drops nothing, for automated walling purposes

	@Deprecated public static Block machine_assembler;
	public static Block machine_assembly_machine;
	@Deprecated public static Block machine_assemfac;
	public static Block machine_arc_welder;
	public static Block machine_soldering_station;
	public static Block machine_arc_furnace;

	@Deprecated public static Block machine_chemplant;
	public static Block machine_chemical_plant;
	@Deprecated public static Block machine_chemfac;
	public static Block machine_chemical_factory;
	public static Block machine_purex;
	public static Block machine_mixer;

	public static Block machine_fluidtank;
	public static Block machine_bat9000;
	public static Block machine_orbus;

	public static Block launch_pad;
	public static Block launch_pad_rusted;
	public static Block launch_pad_large;

	public static Block machine_missile_assembly;

	public static Block compact_launcher;
	public static Block launch_table;

	public static Block soyuz_launcher;

	public static Block machine_radar;
	public static Block machine_radar_large;
	public static Block radar_screen;

	public static Block machine_turbofan;
	public static Block machine_turbinegas;
	public static Block machine_lpw2;

	public static Block press_preheater;
	public static Block machine_press;
	public static Block machine_epress;
	public static Block machine_conveyor_press;
	public static Block machine_ammo_press;

	public static Block machine_siren;

	public static Block machine_radgen;

	public static Block machine_satlinker;
	public static Block machine_keyforge;

	public static Block machine_armor_table;
	public static Block machine_weapon_table;

	public static Block reactor_research;
	public static Block reactor_zirnox;
	public static Block zirnox_destroyed;

	public static Block machine_controller;

	public static Block machine_spp_bottom;
	public static Block machine_spp_top;

	public static Block radiobox;
	public static Block radiorec;

	public static Block machine_forcefield;

	public static Block machine_waste_drum;
	public static Block machine_storage_drum;

	public static Block machine_autocrafter;
	public static Block machine_funnel;

	public static Block anvil_iron;
	public static Block anvil_lead;
	public static Block anvil_steel;
	public static Block anvil_desh;
	public static Block anvil_ferrouranium;
	public static Block anvil_saturnite;
	public static Block anvil_bismuth_bronze;
	public static Block anvil_arsenic_bronze;
	public static Block anvil_schrabidate;
	public static Block anvil_dnt;
	public static Block anvil_osmiridium;
	public static Block anvil_murky;

	public static Block turret_chekhov;
	public static Block turret_friendly;
	public static Block turret_jeremy;
	public static Block turret_tauon;
	public static Block turret_richard;
	public static Block turret_howard;
	public static Block turret_howard_damaged;
	public static Block turret_maxwell;
	public static Block turret_fritz;
	public static Block turret_arty;
	public static Block turret_himars;
	public static Block turret_sentry;
	public static Block turret_sentry_damaged;

	public static Block rbmk_rod;
	public static Block rbmk_rod_mod;
	public static Block rbmk_rod_reasim;
	public static Block rbmk_rod_reasim_mod;
	public static Block rbmk_control;
	public static Block rbmk_control_mod;
	public static Block rbmk_control_auto;
	public static Block rbmk_blank;
	public static Block rbmk_boiler;
	public static Block rbmk_reflector;
	public static Block rbmk_absorber;
	public static Block rbmk_moderator;
	public static Block rbmk_outgasser;
	public static Block rbmk_storage;
	public static Block rbmk_cooler;
	public static Block rbmk_heater;
	public static Block rbmk_console;
	public static Block rbmk_crane_console;
	public static Block rbmk_autoloader;
	public static Block rbmk_loader;
	public static Block rbmk_steam_inlet;
	public static Block rbmk_steam_outlet;
	public static Block pribris;
	public static Block pribris_burning;
	public static Block pribris_radiating;
	public static Block pribris_digamma;

	public static Block book_guide;

	public static Block rail_wood;
	public static Block rail_narrow;
	public static Block rail_highspeed;
	public static Block rail_booster;

	public static Block rail_narrow_straight;
	public static Block rail_narrow_curve;
	public static Block rail_large_straight;
	public static Block rail_large_straight_short;
	public static Block rail_large_curve;
	public static Block rail_large_curve_7;
	public static Block rail_large_curve_9;
	public static Block rail_large_ramp;
	public static Block rail_large_buffer;
	public static Block rail_large_switch;
	public static Block rail_large_switch_flipped;

	public static Block statue_elb_f;

	public static Block cheater_virus;
	public static Block cheater_virus_seed;
	public static Block crystal_virus;
	public static Block crystal_hardened;
	public static Block crystal_pulsar;
	public static Block taint;

	public static Block vent_chlorine;
	public static Block vent_cloud;
	public static Block vent_pink_cloud;
	public static Block vent_chlorine_seal;
	public static Block chlorine_gas;

	public static Block gas_radon;
	public static Block gas_radon_dense;
	public static Block gas_radon_tomb;
	public static Block gas_meltdown;
	public static Block gas_monoxide;
	public static Block gas_asbestos;
	public static Block gas_coal;
	public static Block gas_flammable;
	public static Block gas_explosive;
	public static Block vacuum;

	public static Block absorber;
	public static Block absorber_red;
	public static Block absorber_green;
	public static Block absorber_pink;
	public static Block decon;

	public static Block mud_block;
	public static Fluid mud_fluid;
	public static final Material fluidmud = (new MaterialLiquid(MapColor.adobeColor));

	public static Block acid_block;
	public static Fluid acid_fluid;
	public static final Material fluidacid = (new MaterialLiquid(MapColor.purpleColor));

	public static Block toxic_block;
	public static Fluid toxic_fluid;
	public static final Material fluidtoxic = (new MaterialLiquid(MapColor.greenColor));

	public static Block schrabidic_block;
	public static Fluid schrabidic_fluid;
	public static final Material fluidschrabidic = (new MaterialLiquid(MapColor.cyanColor));

	public static Block corium_block;
	public static Fluid corium_fluid;
	public static final Material fluidcorium = (new MaterialLiquid(MapColor.brownColor) {

		@Override
		public boolean blocksMovement() {
			return true;
		}

		@Override
		public Material setImmovableMobility() { //override access modifier
			return super.setImmovableMobility();
		}

	}.setImmovableMobility());

	public static Block volcanic_lava_block;
	public static Fluid volcanic_lava_fluid;
	public static Block rad_lava_block;
	public static Fluid rad_lava_fluid;

	public static Block sulfuric_acid_block;
	public static Fluid sulfuric_acid_fluid;

	public static Block concrete_liquid;

	public static Block volcano_core;
	public static Block volcano_rad_core;

	public static Block dummy_block_vault;
	public static Block dummy_block_blast;
	public static Block dummy_plate_compact_launcher;
	public static Block dummy_port_compact_launcher;
	public static Block dummy_plate_launch_table;
	public static Block dummy_port_launch_table;
	public static Block dummy_plate_cargo;

	public static Block ntm_dirt;

	public static Block pink_log;
	public static Block pink_planks;
	public static Block pink_slab;
	public static Block pink_double_slab;
	public static Block pink_stairs;

	// NBT Structure wand blocks
	public static Block wand_air;
	public static Block wand_loot;
	public static Block wand_jigsaw;
	public static Block wand_logic;
	public static Block wand_tandem;

	public static Block logic_block;

	public static Material materialGas = new MaterialGas();

	private static void initializeBlock() {

		event_tester = new TestEventTester(Material.iron).setBlockName("event_tester").setCreativeTab(null).setHardness(2.5F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":event_tester");
		obj_tester = new TestObjTester(Material.iron).setBlockName("obj_tester").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F);
		test_core = new TestCore(Material.iron).setBlockName("test_core").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":test_core");
		test_charge = new TestCharge(Material.iron).setBlockName("test_charge").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F);
		structure_anchor = new BlockGeneric(Material.iron).setBlockName("structure_anchor").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":structure_anchor");

		ore_uranium = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_uranium");
		ore_uranium_scorched = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_uranium_scorched");
		ore_titanium = new BlockGeneric(Material.rock).setBlockName("ore_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_titanium");
		ore_sulfur = new BlockOre(Material.rock).setBlockName("ore_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_sulfur");
		ore_thorium = new BlockGeneric(Material.rock).setBlockName("ore_thorium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_thorium");

		ore_niter = new BlockOre(Material.rock).setBlockName("ore_niter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_niter");
		ore_copper = new BlockGeneric(Material.rock).setBlockName("ore_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_copper");
		ore_tungsten = new BlockGeneric(Material.rock).setBlockName("ore_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_tungsten");
		ore_aluminium = new BlockGeneric(Material.rock).setBlockName("ore_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_aluminium");
		ore_fluorite = new BlockOre(Material.rock).setBlockName("ore_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_fluorite");
		ore_lead = new BlockGeneric(Material.rock).setBlockName("ore_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_lead");
		ore_schrabidium = new BlockOre(Material.rock, 0.1F, 0.5F).setBlockName("ore_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_schrabidium");
		ore_beryllium = new BlockGeneric(Material.rock).setBlockName("ore_beryllium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_beryllium");
		ore_lignite = new BlockOre(Material.rock).setBlockName("ore_lignite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_lignite");
		ore_asbestos = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_asbestos");
		ore_coal_oil = new BlockCoalOil(Material.rock).setBlockName("ore_coal_oil").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_coal_oil");
		ore_coal_oil_burning = new BlockCoalBurning(Material.rock).setBlockName("ore_coal_oil_burning").setCreativeTab(MainRegistry.blockTab).setLightLevel(10F/15F).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_coal_oil_burning");

		cluster_iron = new BlockCluster(Material.rock).setBlockName("cluster_iron").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":cluster_iron");
		cluster_titanium = new BlockCluster(Material.rock).setBlockName("cluster_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":cluster_titanium");
		cluster_aluminium = new BlockCluster(Material.rock).setBlockName("cluster_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":cluster_aluminium");
		cluster_copper = new BlockCluster(Material.rock).setBlockName("cluster_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":cluster_copper");

		ore_nether_coal = new BlockNetherCoal(Material.rock, false, 5, true).setBlockName("ore_nether_coal").setCreativeTab(MainRegistry.blockTab).setLightLevel(10F/15F).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_coal");
		ore_nether_smoldering = new BlockSmolder(Material.rock).setBlockName("ore_nether_smoldering").setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_smoldering");
		ore_nether_uranium = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_nether_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_uranium");
		ore_nether_uranium_scorched = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_nether_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_uranium_scorched");
		ore_nether_plutonium = new BlockGeneric(Material.rock).setBlockName("ore_nether_plutonium").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_plutonium");
		ore_nether_tungsten = new BlockGeneric(Material.rock).setBlockName("ore_nether_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_tungsten");
		ore_nether_sulfur = new BlockOre(Material.rock).setBlockName("ore_nether_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_sulfur");
		ore_nether_fire = new BlockOre(Material.rock).setBlockName("ore_nether_fire").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_fire");
		ore_nether_cobalt = new BlockOre(Material.rock).setBlockName("ore_nether_cobalt").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_cobalt");
		ore_nether_schrabidium = new BlockGeneric(Material.rock).setBlockName("ore_nether_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_schrabidium");

		ore_meteor = new BlockMeteorOre().setBlockName("ore_meteor").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);

		stone_gneiss = new BlockGeneric(Material.rock).setBlockName("stone_gneiss").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":stone_gneiss_var");
		ore_gneiss_iron = new BlockOre(Material.rock).setBlockName("ore_gneiss_iron").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_iron");
		ore_gneiss_gold = new BlockOre(Material.rock).setBlockName("ore_gneiss_gold").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_gold");
		ore_gneiss_uranium = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_gneiss_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_uranium");
		ore_gneiss_uranium_scorched = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_gneiss_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_uranium_scorched");
		ore_gneiss_copper = new BlockOre(Material.rock).setBlockName("ore_gneiss_copper").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_copper");
		ore_gneiss_asbestos = new BlockOutgas(Material.rock, true, 5, true).setBlockName("ore_gneiss_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_asbestos");
		ore_gneiss_lithium = new BlockOre(Material.rock).setBlockName("ore_gneiss_lithium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_lithium");
		ore_gneiss_schrabidium = new BlockOre(Material.rock).setBlockName("ore_gneiss_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_schrabidium");
		ore_gneiss_rare = new BlockOre(Material.rock).setBlockName("ore_gneiss_rare").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_rare");
		ore_gneiss_gas = new BlockOre(Material.rock).setBlockName("ore_gneiss_gas").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_gneiss_gas");

		gneiss_brick = new BlockGeneric(Material.rock).setBlockName("gneiss_brick").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":gneiss_brick");
		gneiss_tile = new BlockGeneric(Material.rock).setBlockName("gneiss_tile").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":gneiss_tile");
		gneiss_chiseled = new BlockPillar(Material.rock, RefStrings.MODID + ":gneiss_tile").setBlockName("gneiss_chiseled").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":gneiss_chiseled");

		stone_depth = new BlockDepth().setBlockName("stone_depth").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":stone_depth");
		ore_depth_cinnebar = new BlockDepthOre().setBlockName("ore_depth_cinnebar").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_depth_cinnebar");
		ore_depth_zirconium = new BlockDepthOre().setBlockName("ore_depth_zirconium").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_depth_zirconium");
		ore_depth_borax = new BlockDepthOre().setBlockName("ore_depth_borax").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_depth_borax");
		cluster_depth_iron = new BlockDepthOre().setBlockName("cluster_depth_iron").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":cluster_depth_iron");
		cluster_depth_titanium = new BlockDepthOre().setBlockName("cluster_depth_titanium").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":cluster_depth_titanium");
		cluster_depth_tungsten = new BlockDepthOre().setBlockName("cluster_depth_tungsten").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":cluster_depth_tungsten");
		ore_alexandrite = new BlockDepthOre().setBlockName("ore_alexandrite").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_alexandrite");

		stone_keyhole = new BlockKeyhole().setBlockName("stone_keyhole").setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":stone_keyhole");
		stone_keyhole_meta = new BlockRedBrickKeyhole(Material.rock).setCreativeTab(null).setBlockName("stone_keyhole_meta").setResistance(10_000);

		ore_bedrock = new BlockBedrockOreTE().setBlockName("ore_bedrock").setCreativeTab(null);
		ore_volcano = new BlockFissure().setBlockName("ore_volcano").setLightLevel(1F).setCreativeTab(MainRegistry.blockTab);

		depth_brick = new BlockDepth().setBlockName("depth_brick").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":depth_brick");
		depth_tiles = new BlockDepth().setBlockName("depth_tiles").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":depth_tiles");
		depth_nether_brick = new BlockDepth().setBlockName("depth_nether_brick").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":depth_nether_brick");
		depth_nether_tiles = new BlockDepth().setBlockName("depth_nether_tiles").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":depth_nether_tiles");
		depth_dnt = new BlockDepth().setBlockName("depth_dnt").setCreativeTab(MainRegistry.blockTab).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":depth_dnt");

		stone_depth_nether = new BlockDepth().setBlockName("stone_depth_nether").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":stone_depth_nether");
		ore_depth_nether_neodymium = new BlockDepthOre().setBlockName("ore_depth_nether_neodymium").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ore_depth_nether_neodymium");

		stone_porous = new BlockPorous().setBlockName("stone_porous").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":stone_porous");
		stone_resource = new BlockResourceStone().setBlockName("stone_resource").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
		stalagmite = new BlockStalagmite().setBlockName("stalagmite").setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.0F);
		stalactite = new BlockStalagmite().setBlockName("stalactite").setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.0F);
		stone_biome = new BlockBiomeStone().setBlockName("stone_biome").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);

		basalt = new BlockPillar(Material.rock, RefStrings.MODID + ":basalt_top").setBlockName("basalt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt");
		ore_basalt = new BlockOreBasalt().setBlockName("ore_basalt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_basalt");
		basalt_smooth = new BlockGeneric(Material.rock).setBlockName("basalt_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_smooth");
		basalt_brick = new BlockGeneric(Material.rock).setBlockName("basalt_brick").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_brick");
		basalt_polished = new BlockGeneric(Material.rock).setBlockName("basalt_polished").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_polished");
		basalt_tiles = new BlockGeneric(Material.rock).setBlockName("basalt_tiles").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":basalt_tiles");

		ore_australium = new BlockGeneric(Material.rock).setBlockName("ore_australium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_australium");
		ore_rare = new BlockOre(Material.rock).setBlockName("ore_rare").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_rare");
		ore_cobalt = new BlockOre(Material.rock).setBlockName("ore_cobalt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_cobalt");
		ore_cinnebar = new BlockOre(Material.rock).setBlockName("ore_cinnebar").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_cinnebar");
		ore_coltan = new BlockOre(Material.rock).setBlockName("ore_coltan").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_coltan");

		ore_oil = new BlockOre(Material.rock).setBlockName("ore_oil").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil");
		ore_oil_empty = new BlockGeneric(Material.rock).setBlockName("ore_oil_empty").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil_empty");
		ore_oil_sand = new BlockFalling(Material.sand).setBlockName("ore_oil_sand").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeSand).setHardness(0.5F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil_sand_alt");
		ore_bedrock_oil = new BlockGeneric(Material.rock).noMobSpawn().setBlockName("ore_bedrock_oil").setCreativeTab(MainRegistry.blockTab).setBlockUnbreakable().setResistance(1_000_000).setBlockTextureName(RefStrings.MODID + ":ore_bedrock_oil");

		ore_tikite = new BlockDragonProof(Material.rock).setBlockName("ore_tikite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_tikite_alt");

		block_uranium = new BlockHazard().makeBeaconable().setBlockName("block_uranium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_uranium");
		block_u233 = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_u233").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_u233");
		block_u235 = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_u235").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_u235");
		block_u238 = new BlockHazard().makeBeaconable().setBlockName("block_u238").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_u238");
		block_uranium_fuel = new BlockHazard().makeBeaconable().setBlockName("block_uranium_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_uranium_fuel");
		block_thorium = new BlockHazard().makeBeaconable().setBlockName("block_thorium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_thorium");
		block_thorium_fuel = new BlockHazard().makeBeaconable().setBlockName("block_thorium_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_thorium_fuel");
		block_neptunium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_neptunium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":block_neptunium");
		block_polonium = new BlockHotHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_polonium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_polonium");
		block_mox_fuel = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_mox_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_mox_fuel");
		block_plutonium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_plutonium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_plutonium");
		block_pu238 = new BlockHotHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_pu238").setCreativeTab(MainRegistry.blockTab).setLightLevel(5F/15F).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_pu238");
		block_pu239 = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_pu239").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_pu239");
		block_pu240 = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_pu240").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_pu240");
		block_pu_mix = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_pu_mix").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_pu_mix");
		block_plutonium_fuel = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_plutonium_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_plutonium_fuel");
		block_titanium = new BlockBeaconable(Material.iron).setBlockName("block_titanium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		block_sulfur = new BlockBeaconable(Material.iron).setBlockName("block_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_sulfur");
		block_niter = new BlockBeaconable(Material.iron).setBlockName("block_niter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_niter");
		block_copper = new BlockBeaconable(Material.iron).setBlockName("block_copper").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":block_copper");
		block_red_copper = new BlockBeaconable(Material.iron).setBlockName("block_red_copper").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(25.0F).setBlockTextureName(RefStrings.MODID + ":block_red_copper");
		block_tungsten = new BlockBeaconable(Material.iron).setBlockName("block_tungsten").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":block_tungsten");
		block_aluminium = new BlockBeaconable(Material.iron).setBlockName("block_aluminium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		block_fluorite = new BlockBeaconable(Material.iron).setBlockName("block_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_fluorite");
		block_steel = new BlockBeaconable(Material.iron).setBlockName("block_steel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		block_tcalloy = new BlockBeaconable(Material.iron).setBlockName("block_tcalloy").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(70.0F).setBlockTextureName(RefStrings.MODID + ":block_tcalloy");
		block_cdalloy = new BlockBeaconable(Material.iron).setBlockName("block_cdalloy").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(70.0F).setBlockTextureName(RefStrings.MODID + ":block_cdalloy");
		block_lead = new BlockBeaconable(Material.iron).setBlockName("block_lead").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_lead");
		block_bismuth = new BlockBeaconable(Material.iron).setBlockName("block_bismuth").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(90.0F).setBlockTextureName(RefStrings.MODID + ":block_bismuth");
		block_cadmium = new BlockBeaconable(Material.iron).setBlockName("block_cadmium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(90.0F).setBlockTextureName(RefStrings.MODID + ":block_cadmium");
		block_coltan = new BlockBeaconable(Material.iron).setBlockName("block_coltan").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_coltan");
		block_tantalium = new BlockBeaconable(Material.iron).setBlockName("block_tantalium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_tantalium");
		block_niobium = new BlockBeaconable(Material.iron).setBlockName("block_niobium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(50.0F);
		block_trinitite = new BlockHazard().makeBeaconable().setBlockName("block_trinitite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_trinitite");
		block_waste = new BlockNuclearWaste().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_waste").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_waste");
		block_waste_painted = new BlockNuclearWaste().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_waste_painted").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_waste_painted");
		block_waste_vitrified = new BlockNuclearWaste().makeBeaconable().setDisplayEffect(ExtDisplayEffect.RADFOG).setBlockName("block_waste_vitrified").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_waste_vitrified");
		ancient_scrap = new BlockOutgas(Material.iron, true, 1, true, true).setBlockName("ancient_scrap").setCreativeTab(MainRegistry.blockTab).setHardness(100.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":ancient_scrap");
		block_corium = new BlockHazard(Material.iron).setBlockName("block_corium").setCreativeTab(MainRegistry.blockTab).setHardness(100.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":block_corium");
		block_corium_cobble = new BlockOutgas(Material.iron, true, 1, true, true).setBlockName("block_corium_cobble").setCreativeTab(MainRegistry.blockTab).setHardness(100.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":block_corium_cobble");
		block_scrap = new BlockFalling(Material.sand).setBlockName("block_scrap").setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F).setStepSound(Block.soundTypeGravel).setBlockTextureName(RefStrings.MODID + ":block_scrap");
		block_electrical_scrap = new BlockFalling(Material.iron).setBlockName("block_electrical_scrap").setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F).setStepSound(Block.soundTypeMetal).setBlockTextureName(RefStrings.MODID + ":electrical_scrap_alt2");
		block_beryllium = new BlockBeaconable(Material.iron).setBlockName("block_beryllium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":block_beryllium");
		block_schraranium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_schraranium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(250.0F).setBlockTextureName(RefStrings.MODID + ":block_schraranium");
		block_schrabidium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_schrabidium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidium");
		block_schrabidate = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_schrabidate").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidate");
		block_solinium = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_solinium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_solinium");
		block_schrabidium_fuel = new BlockHazard().makeBeaconable().setDisplayEffect(ExtDisplayEffect.SCHRAB).setBlockName("block_schrabidium_fuel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidium_fuel");
		block_euphemium = new BlockBeaconable(Material.iron).setBlockName("block_euphemium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":block_euphemium");
		block_dineutronium = new BlockBeaconable(Material.iron).setBlockName("block_dineutronium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":block_dineutronium");
		block_schrabidium_cluster = new BlockRotatablePillar(Material.rock, RefStrings.MODID + ":block_schrabidium_cluster_top").setBlockName("block_schrabidium_cluster").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidium_cluster_side");
		block_euphemium_cluster = new BlockRotatablePillar(Material.rock, RefStrings.MODID + ":block_euphemium_cluster_top").setBlockName("block_euphemium_cluster").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":block_euphemium_cluster_side");
		block_advanced_alloy = new BlockBeaconable(Material.iron).setBlockName("block_advanced_alloy").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":block_advanced_alloy");
		block_magnetized_tungsten = new BlockBeaconable(Material.iron).setBlockName("block_magnetized_tungsten").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(75.0F).setBlockTextureName(RefStrings.MODID + ":block_magnetized_tungsten");
		block_combine_steel = new BlockBeaconable(Material.iron).setBlockName("block_combine_steel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_combine_steel");
		block_desh = new BlockBeaconable(Material.iron).setBlockName("block_desh").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(300.0F).setBlockTextureName(RefStrings.MODID + ":block_desh");
		block_dura_steel = new BlockBeaconable(Material.iron).setBlockName("block_dura_steel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":block_dura_steel");
		block_starmetal = new BlockBeaconable(Material.iron).setBlockName("block_starmetal").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(400.0F).setBlockTextureName(RefStrings.MODID + ":block_starmetal");
		block_polymer = new BlockBeaconable(Material.rock).setBlockName("block_polymer").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypePiston).setHardness(3.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_polymer");
		block_bakelite = new BlockBeaconable(Material.rock).setBlockName("block_bakelite").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypePiston).setHardness(3.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":block_bakelite");
		block_rubber = new BlockBeaconable(Material.rock).setBlockName("block_rubber").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypePiston).setHardness(3.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":block_rubber");
		block_yellowcake = new BlockHazardFalling().makeBeaconable().setBlockName("block_yellowcake").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeSand).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_yellowcake");
		block_insulator = new BlockRotatablePillar(Material.cloth, RefStrings.MODID + ":block_insulator_top").setBlockName("block_insulator").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeCloth).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_insulator_side");
		block_fiberglass = new BlockRotatablePillar(Material.cloth, RefStrings.MODID + ":block_fiberglass_top").setBlockName("block_fiberglass").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeCloth).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":block_fiberglass_side");
		block_asbestos = new BlockOutgas(Material.cloth, true, 5, true).setBlockName("block_asbestos").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeCloth).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":block_asbestos");
		block_cobalt = new BlockBeaconable(Material.iron).setBlockName("block_cobalt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(50.0F).setBlockTextureName(RefStrings.MODID + ":block_cobalt");
		block_lithium = new BlockLithium(Material.iron).setBlockName("block_lithium").setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_lithium");
		block_zirconium = new BlockBeaconable(Material.iron).setBlockName("block_zirconium").setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(30.0F).setBlockTextureName(RefStrings.MODID + ":block_zirconium");
		block_white_phosphorus = new BlockHazard(Material.rock).makeBeaconable().setBlockName("block_white_phosphorus").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_white_phosphorus");
		block_red_phosphorus = new BlockHazardFalling().makeBeaconable().setStepSound(Block.soundTypeSand).setBlockName("block_red_phosphorus").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_red_phosphorus");
		block_fallout = new BlockHazardFalling().setStepSound(Block.soundTypeGravel).setBlockName("block_fallout").setCreativeTab(MainRegistry.blockTab).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":ash");
		block_foam = new BlockGeneric(Material.craftedSnow).setBlockName("block_foam").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeSnow).setHardness(0.5F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":foam");
		block_coke = new BlockCoke().setBlockName("block_coke").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F);
		block_graphite = new BlockGraphite(Material.iron, 30, 5).setBlockName("block_graphite").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F);
		block_graphite_drilled = new BlockGraphiteDrilled().setBlockName("block_graphite_drilled");
		block_graphite_fuel = new BlockGraphiteFuel().setBlockName("block_graphite_fuel");
		block_graphite_plutonium = new BlockGraphiteSource().setBlockName("block_graphite_plutonium");
		block_graphite_rod = new BlockGraphiteRod().setBlockName("block_graphite_rod").setBlockTextureName(RefStrings.MODID + ":block_graphite_rod_in");
		block_graphite_source = new BlockGraphiteSource().setBlockName("block_graphite_source");
		block_graphite_lithium = new BlockGraphiteBreedingFuel().setBlockName("block_graphite_lithium");
		block_graphite_tritium = new BlockGraphiteBreedingProduct().setBlockName("block_graphite_tritium");
		block_graphite_detector = new BlockGraphiteNeutronDetector().setBlockName("block_graphite_detector");
		block_boron = new BlockBeaconable(Material.iron).setBlockName("block_boron").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_boron");
		block_lanthanium = new BlockBeaconable(Material.iron).setBlockName("block_lanthanium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_lanthanium");
		block_ra226 = new BlockHazard().makeBeaconable().setBlockName("block_ra226").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_ra226");
		block_actinium = new BlockHazard().makeBeaconable().setBlockName("block_actinium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_actinium");
		block_tritium = new BlockRotatablePillar(Material.glass, RefStrings.MODID + ":block_tritium_top").setBlockName("block_tritium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGlass).setHardness(3.0F).setResistance(2.0F).setBlockTextureName(RefStrings.MODID + ":block_tritium_side");
		block_semtex = new BlockPlasticExplosive(Material.tnt).setBlockName("block_semtex").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(2.0F).setBlockTextureName(RefStrings.MODID + ":block_semtex");
		block_c4 = new BlockPlasticExplosive(Material.tnt).setBlockName("block_c4").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(2.0F).setBlockTextureName(RefStrings.MODID + ":block_c4");
		block_smore = new BlockPillar(Material.rock, RefStrings.MODID + ":block_smore_top").setBlockName("block_smore").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_smore_side");
		block_slag = new BlockSlag(Material.rock).setBlockName("block_slag").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeStone).setHardness(2.0F).setBlockTextureName(RefStrings.MODID + ":block_slag");

		block_australium = new BlockBeaconable(Material.iron).setBlockName("block_australium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_australium");
		block_weidanium = new BlockBeaconable(Material.iron).setBlockName("block_weidanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_deprecated");
		block_reiium = new BlockBeaconable(Material.iron).setBlockName("block_reiium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_deprecated");
		block_unobtainium = new BlockBeaconable(Material.iron).setBlockName("block_unobtainium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_deprecated");
		block_daffergon = new BlockBeaconable(Material.iron).setBlockName("block_daffergon").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_deprecated");
		block_verticium = new BlockBeaconable(Material.iron).setBlockName("block_verticium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_deprecated");

		block_cap = new BlockCap().setBlockName("block_cap").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);

		deco_titanium = new BlockOre(Material.iron).noFortune().setBlockName("deco_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_titanium");
		deco_red_copper = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_red_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_red_copper");
		deco_tungsten = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_tungsten");
		deco_aluminium = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_aluminium");
		deco_steel = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_steel").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_steel");
		deco_rusty_steel = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_rusty_steel").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_rusty_steel");
		deco_lead = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_lead");
		deco_beryllium = new BlockDecoCT(Material.iron).noFortune().setBlockName("deco_beryllium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_beryllium");
		deco_asbestos = new BlockOutgas(Material.cloth, true, 5, true).noFortune().setBlockName("deco_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_asbestos");
		deco_rbmk = new BlockGeneric(Material.iron).setBlockName("deco_rbmk").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_side");
		deco_rbmk_smooth = new BlockGeneric(Material.iron).setBlockName("deco_rbmk_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_top");

		deco_emitter = new BlockEmitter().setBlockName("deco_emitter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":emitter");
		part_emitter = new PartEmitter().setBlockName("part_emitter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":part_top");
		deco_loot = new BlockLoot().setBlockName("deco_loot").setCreativeTab(null).setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		pedestal = new BlockPedestal().setBlockName("pedestal").setCreativeTab(null).setHardness(2.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":pedestal_top");
		skeleton_holder = new BlockSkeletonHolder().setBlockName("skeleton_holder").setCreativeTab(null).setHardness(2.0F).setResistance(10.0F).setBlockTextureName("soul_sand");
		bobblehead = new BlockBobble().setBlockName("bobblehead").setCreativeTab(MainRegistry.blockTab).setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		snowglobe = new BlockSnowglobe().setBlockName("snowglobe").setCreativeTab(MainRegistry.blockTab).setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":glass_boron");
		plushie = new BlockPlushie().setBlockName("plushie").setStepSound(Block.soundTypeCloth).setResistance(50_0000.0F).setCreativeTab(MainRegistry.blockTab).setHardness(0.0F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":block_fiberglass_side");
		dungeon_spawner = new DungeonSpawner().setBlockName("dungeon_spawner").setResistance(50_0000.0F).setBlockUnbreakable().setBlockTextureName(RefStrings.MODID + ":dungeon_spawner");

		gravel_obsidian = new BlockFalling(Material.iron).setBlockName("gravel_obsidian").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGravel).setHardness(5.0F).setResistance(240.0F).setBlockTextureName(RefStrings.MODID + ":gravel_obsidian");
		gravel_diamond = new BlockFalling(Material.sand).setBlockName("gravel_diamond").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGravel).setHardness(0.6F).setBlockTextureName(RefStrings.MODID + ":gravel_diamond");
		asphalt = new BlockSpeedy(Material.rock, 1.5).setBlockName("asphalt").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(120.0F).setBlockTextureName(RefStrings.MODID + ":asphalt");
		asphalt_light = new BlockSpeedy(Material.rock, 1.5).setBlockName("asphalt_light").setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(15.0F).setResistance(120.0F).setBlockTextureName(RefStrings.MODID + ":asphalt_light");

		sandbags = new BlockSandbags(Material.ground).setBlockName("sandbags").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(30.0F).setBlockTextureName(RefStrings.MODID + ":sandbags");
		wood_barrier = new BlockBarrier(Material.wood).setStepSound(Block.soundTypeWood).setBlockName("wood_barrier").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":wood_barrier");
		wood_structure = new BlockWoodStructure(Material.wood).setStepSound(Block.soundTypeWood).setBlockName("wood_structure").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":wood_barrier");

		reinforced_brick = new BlockGeneric(Material.rock).setBlockName("reinforced_brick").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(300.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_brick");
		reinforced_glass = new BlockNTMGlassCT(0, RefStrings.MODID + ":reinforced_glass", Material.rock).setBlockName("reinforced_glass").setCreativeTab(MainRegistry.blockTab).setLightOpacity(0).setHardness(2.0F).setResistance(25.0F);
		reinforced_glass_pane = new BlockNTMGlassPane(0, RefStrings.MODID + ":reinforced_glass_pane", RefStrings.MODID + ":reinforced_glass_pane_edge", Material.rock, false).setBlockName("reinforced_glass_pane").setCreativeTab(MainRegistry.blockTab).setLightOpacity(1).setHardness(2.0F).setResistance(25.0F);
		reinforced_light = new BlockGeneric(Material.rock).setBlockName("reinforced_light").setCreativeTab(MainRegistry.blockTab).setLightLevel(1.0F).setHardness(15.0F).setResistance(80.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_light");
		reinforced_sand = new BlockGeneric(Material.rock).setBlockName("reinforced_sand").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(40.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_sand");
		reinforced_lamp_off = new ReinforcedLamp(Material.rock, false).setBlockName("reinforced_lamp_off").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(80.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_lamp_off");
		reinforced_lamp_on = new ReinforcedLamp(Material.rock, true).setBlockName("reinforced_lamp_on").setHardness(15.0F).setResistance(80.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_lamp_on");
		reinforced_laminate = new BlockNTMGlassCT(1, RefStrings.MODID + ":reinforced_laminate", Material.rock, true).setBlockName("reinforced_laminate").setCreativeTab(MainRegistry.blockTab).setLightOpacity(0).setHardness(15.0F).setResistance(300.0F);
		reinforced_laminate_pane = new BlockNTMGlassPane(1, RefStrings.MODID + ":reinforced_laminate_pane", RefStrings.MODID + ":reinforced_laminate_pane_edge", Material.rock, true).setBlockName("reinforced_laminate_pane").setCreativeTab(MainRegistry.blockTab).setLightOpacity(1).setHardness(15.0F).setResistance(300.0F);

		lamp_tritium_green_off = new TritiumLamp(Material.redstoneLight, false).setBlockName("lamp_tritium_green_off").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_tritium_green_off");
		lamp_tritium_green_on = new TritiumLamp(Material.redstoneLight, true).setBlockName("lamp_tritium_green_on").setStepSound(Block.soundTypeGlass).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_tritium_green_on");
		lamp_tritium_blue_off = new TritiumLamp(Material.redstoneLight, false).setBlockName("lamp_tritium_blue_off").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_tritium_blue_off");
		lamp_tritium_blue_on = new TritiumLamp(Material.redstoneLight, true).setBlockName("lamp_tritium_blue_on").setStepSound(Block.soundTypeGlass).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_tritium_blue_on");

		lamp_demon = new DemonLamp().setBlockName("lamp_demon").setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":lamp_demon");
		lantern = new BlockLantern().setBlockName("lantern").setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		lantern_behemoth = new BlockLanternBehemoth().setBlockName("lantern_behemoth").setStepSound(Block.soundTypeMetal).setCreativeTab(null).setHardness(3.0F).setBlockTextureName(RefStrings.MODID + ":block_rust");

		spotlight_incandescent = new Spotlight(Material.iron, 2, LightType.INCANDESCENT, true).setBlockName("spotlight_incandescent").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":cage_lamp");
		spotlight_incandescent_off = new Spotlight(Material.iron, 2, LightType.INCANDESCENT, false).setBlockName("spotlight_incandescent_off").setBlockTextureName(RefStrings.MODID + ":cage_lamp_off");
		spotlight_fluoro = new SpotlightModular(Material.iron, 8, LightType.FLUORESCENT, true).setBlockName("spotlight_fluoro").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":fluorescent_lamp");
		spotlight_fluoro_off = new SpotlightModular(Material.iron, 8, LightType.FLUORESCENT, false).setBlockName("spotlight_fluoro_off").setBlockTextureName(RefStrings.MODID + ":fluorescent_lamp_off");
		spotlight_halogen = new Spotlight(Material.iron, 32, LightType.HALOGEN, true).setBlockName("spotlight_halogen").setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":flood_lamp");
		spotlight_halogen_off = new Spotlight(Material.iron, 32, LightType.HALOGEN, false).setBlockName("spotlight_halogen_off").setBlockTextureName(RefStrings.MODID + ":flood_lamp_off");
		spotlight_beam = new SpotlightBeam().setBlockName("spotlight_beam");
		floodlight = new Floodlight(Material.iron).setBlockName("floodlight").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		floodlight_beam = new FloodlightBeam().setBlockName("floodlight_beam");

		rebar = new BlockRebar().setBlockName("rebar").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":rebar");
		reinforced_stone = new BlockGeneric(Material.rock).setBlockName("reinforced_stone").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_stone");
		concrete_smooth = new BlockRadResistant(Material.rock).setBlockName("concrete_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(140.0F).setBlockTextureName(RefStrings.MODID + ":concrete");
		concrete_colored = new BlockConcreteColored(Material.rock).setBlockName("concrete_colored").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(140.0F).setBlockTextureName(RefStrings.MODID + ":concrete");
		concrete_colored_ext = new BlockConcreteColoredExt(Material.rock).setBlockName("concrete_colored_ext").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(140.0F).setBlockTextureName(RefStrings.MODID + ":concrete_colored_ext");
		concrete = new BlockGeneric(Material.rock).setBlockName("concrete").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(140.0F).setBlockTextureName(RefStrings.MODID + ":concrete_tile");
		concrete_asbestos = new BlockGeneric(Material.rock).setBlockName("concrete_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(150.0F).setBlockTextureName(RefStrings.MODID + ":concrete_asbestos");
		concrete_rebar = new BlockGeneric(Material.rock).setBlockName("concrete_rebar").setCreativeTab(MainRegistry.blockTab).setHardness(50.0F).setResistance(240.0F).setBlockTextureName(RefStrings.MODID + ":concrete_rebar");
		concrete_super = new BlockUberConcrete().setBlockName("concrete_super").setCreativeTab(MainRegistry.blockTab).setHardness(150.0F).setResistance(1000.0F);
		concrete_super_broken = new BlockFalling(Material.rock).setBlockName("concrete_super_broken").setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":concrete_super_broken");
		concrete_pillar = new BlockRotatablePillar(Material.rock, RefStrings.MODID + ":concrete_pillar_top").setBlockName("concrete_pillar").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(180.0F).setBlockTextureName(RefStrings.MODID + ":concrete_pillar_side");
		brick_concrete = new BlockGeneric(Material.rock).setBlockName("brick_concrete").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(160.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		brick_concrete_mossy = new BlockGeneric(Material.rock).setBlockName("brick_concrete_mossy").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(160.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete_mossy");
		brick_concrete_cracked = new BlockGeneric(Material.rock).setBlockName("brick_concrete_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete_cracked");
		brick_concrete_broken = new BlockGeneric(Material.rock).setBlockName("brick_concrete_broken").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(45.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete_broken");
		brick_concrete_marked = new BlockWriting(Material.rock, RefStrings.MODID + ":brick_concrete").setBlockName("brick_concrete_marked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(160.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete_marked");
		brick_obsidian = new BlockGeneric(Material.rock).setBlockName("brick_obsidian").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(120.0F).setBlockTextureName(RefStrings.MODID + ":brick_obsidian");
		brick_light = new BlockGeneric(Material.rock).setBlockName("brick_light").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":brick_light");
		brick_compound = new BlockGeneric(Material.rock).setBlockName("brick_compound").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(400.0F).setBlockTextureName(RefStrings.MODID + ":brick_compound");
		cmb_brick = new BlockGeneric(Material.rock).setBlockName("cmb_brick").setCreativeTab(MainRegistry.blockTab).setHardness(25.0F).setResistance(5000.0F).setBlockTextureName(RefStrings.MODID + ":cmb_brick");
		cmb_brick_reinforced = new BlockGeneric(Material.rock).setBlockName("cmb_brick_reinforced").setCreativeTab(MainRegistry.blockTab).setHardness(25.0F).setResistance(50000.0F).setBlockTextureName(RefStrings.MODID + ":cmb_brick_reinforced");
		brick_asbestos = new BlockOutgas(Material.rock, true, 5, true).setBlockName("brick_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":brick_asbestos");
		brick_fire = new BlockGeneric(Material.rock).setBlockName("brick_fire").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(35.0F).setBlockTextureName(RefStrings.MODID + ":brick_fire");

		ducrete_smooth = new BlockGeneric(Material.rock).setBlockName("ducrete_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(20.0F).setResistance(500.0F).setBlockTextureName(RefStrings.MODID + ":ducrete");
		ducrete = new BlockGeneric(Material.rock).setBlockName("ducrete").setCreativeTab(MainRegistry.blockTab).setHardness(20.0F).setResistance(500.0F).setBlockTextureName(RefStrings.MODID + ":ducrete_tile");
		brick_ducrete = new BlockGeneric(Material.rock).setBlockName("brick_ducrete").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(750.0F).setBlockTextureName(RefStrings.MODID + ":brick_ducrete");
		reinforced_ducrete = new BlockGeneric(Material.rock).setBlockName("reinforced_ducrete").setCreativeTab(MainRegistry.blockTab).setHardness(20.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_ducrete");

		lightstone = new BlockLightstone(Material.rock, LightstoneType.class, true, true).setBlockName("lightstone").setCreativeTab(MainRegistry.blockTab).setHardness(2F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":lightstone");

		concrete_slab = new BlockMultiSlab(null, Material.rock, concrete_smooth, concrete, concrete_asbestos, ducrete_smooth, ducrete, asphalt).setBlockName("concrete_slab").setCreativeTab(MainRegistry.blockTab);
		concrete_double_slab = new BlockMultiSlab(concrete_slab, Material.rock, concrete_smooth, concrete, concrete_asbestos, ducrete_smooth, ducrete, asphalt).setBlockName("concrete_double_slab").setCreativeTab(MainRegistry.blockTab);
		concrete_brick_slab = new BlockMultiSlab(null, Material.rock, brick_concrete, brick_concrete_mossy, brick_concrete_cracked, brick_concrete_broken, brick_ducrete).setBlockName("concrete_brick_slab").setCreativeTab(MainRegistry.blockTab);
		concrete_brick_double_slab = new BlockMultiSlab(concrete_brick_slab, Material.rock, brick_concrete, brick_concrete_mossy, brick_concrete_cracked, brick_concrete_broken, brick_ducrete).setBlockName("concrete_brick_double_slab").setCreativeTab(MainRegistry.blockTab);
		brick_slab = new BlockMultiSlab(null, Material.rock, reinforced_stone, reinforced_brick, brick_obsidian, brick_light, brick_compound, brick_asbestos, brick_fire).setBlockName("brick_slab").setCreativeTab(MainRegistry.blockTab);
		brick_double_slab = new BlockMultiSlab(brick_slab, Material.rock, reinforced_stone, reinforced_brick, brick_obsidian, brick_light, brick_compound, brick_asbestos, brick_fire).setBlockName("brick_double_slab").setCreativeTab(MainRegistry.blockTab);
		stones_slab = new BlockMultiSlabMeta(null, Material.rock, new Block[] { lightstone, lightstone }, LightstoneType.TILE.ordinal(), LightstoneType.BRICKS.ordinal()).setBlockName("stones_slab").setCreativeTab(MainRegistry.blockTab);
		stones_double_slab = new BlockMultiSlabMeta(stones_slab, Material.rock, new Block[] { lightstone, lightstone }, LightstoneType.TILE.ordinal(), LightstoneType.BRICKS.ordinal()).setBlockName("stones_double_slab").setCreativeTab(MainRegistry.blockTab);

		concrete_smooth_stairs = new BlockGenericStairs(concrete_smooth, 0).setBlockName("concrete_smooth_stairs").setCreativeTab(MainRegistry.blockTab);
		concrete_stairs = new BlockGenericStairs(concrete, 0).setBlockName("concrete_stairs").setCreativeTab(MainRegistry.blockTab);
		concrete_asbestos_stairs = new BlockGenericStairs(concrete_asbestos, 0).setBlockName("concrete_asbestos_stairs").setCreativeTab(MainRegistry.blockTab);
		ducrete_smooth_stairs = new BlockGenericStairs(ducrete_smooth, 0).setBlockName("ducrete_smooth_stairs").setCreativeTab(MainRegistry.blockTab);
		ducrete_stairs = new BlockGenericStairs(ducrete, 0).setBlockName("ducrete_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_concrete_stairs = new BlockGenericStairs(brick_concrete, 0).setBlockName("brick_concrete_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_concrete_mossy_stairs = new BlockGenericStairs(brick_concrete_mossy, 0).setBlockName("brick_concrete_mossy_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_concrete_cracked_stairs = new BlockGenericStairs(brick_concrete_cracked, 0).setBlockName("brick_concrete_cracked_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_concrete_broken_stairs = new BlockGenericStairs(brick_concrete_broken, 0).setBlockName("brick_concrete_broken_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_ducrete_stairs = new BlockGenericStairs(brick_ducrete, 0).setBlockName("brick_ducrete_stairs").setCreativeTab(MainRegistry.blockTab);
		reinforced_stone_stairs = new BlockGenericStairs(reinforced_stone, 0).setBlockName("reinforced_stone_stairs").setCreativeTab(MainRegistry.blockTab);
		reinforced_brick_stairs = new BlockGenericStairs(reinforced_brick, 0).setBlockName("reinforced_brick_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_obsidian_stairs = new BlockGenericStairs(brick_obsidian, 0).setBlockName("brick_obsidian_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_light_stairs = new BlockGenericStairs(brick_light, 0).setBlockName("brick_light_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_compound_stairs = new BlockGenericStairs(brick_compound, 0).setBlockName("brick_compound_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_asbestos_stairs = new BlockGenericStairs(brick_asbestos, 0).setBlockName("brick_asbestos_stairs").setCreativeTab(MainRegistry.blockTab);
		brick_fire_stairs = new BlockGenericStairs(brick_fire, 0).setBlockName("brick_fire_stairs").setCreativeTab(MainRegistry.blockTab);
		asphalt_stairs = new BlockSpeedyStairs(asphalt, 0, 1.5).setBlockName("asphalt_stairs").setCreativeTab(MainRegistry.blockTab);
		lightstone_tile_stairs = new BlockGenericStairs(lightstone, LightstoneType.TILE.ordinal()).setBlockName("lightstone_tile_stairs").setCreativeTab(MainRegistry.blockTab);
		lightstone_bricks_stairs = new BlockGenericStairs(lightstone, LightstoneType.BRICKS.ordinal()).setBlockName("lightstone_bricks_stairs").setCreativeTab(MainRegistry.blockTab);

		vinyl_tile = new BlockEnumMulti(Material.rock, TileType.class, true, true).setBlockName("vinyl_tile").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":vinyl_tile");

		tile_lab = new BlockOutgas(Material.rock, false, 5, true).setBlockName("tile_lab").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":tile_lab");
		tile_lab_cracked = new BlockOutgas(Material.rock, false, 5, true).setBlockName("tile_lab_cracked").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":tile_lab_cracked");
		tile_lab_broken = new BlockOutgas(Material.rock, true, 5, true).setBlockName("tile_lab_broken").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F).setBlockTextureName(RefStrings.MODID + ":tile_lab_broken");

		block_meteor = new BlockOre(Material.rock).noFortune().setBlockName("block_meteor").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor");
		block_meteor_cobble = new BlockOre(Material.rock).noFortune().setBlockName("block_meteor_cobble").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_cobble");
		block_meteor_broken = new BlockOre(Material.rock).noFortune().setBlockName("block_meteor_broken").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_crushed");
		block_meteor_molten = new BlockOre(Material.rock, true).noFortune().setBlockName("block_meteor_molten").setLightLevel(0.75F).setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_cobble_molten");
		block_meteor_treasure = new BlockMeteoriteTreasure(Material.rock).setBlockName("block_meteor_treasure").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_treasure");
		meteor_polished = new BlockGeneric(Material.rock).setBlockName("meteor_polished").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_polished");
		meteor_brick = new BlockGeneric(Material.rock).setBlockName("meteor_brick").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_brick");
		meteor_brick_mossy = new BlockGeneric(Material.rock).setBlockName("meteor_brick_mossy").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_brick_mossy");
		meteor_brick_cracked = new BlockGeneric(Material.rock).setBlockName("meteor_brick_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_brick_cracked");
		meteor_brick_chiseled = new BlockGeneric(Material.rock).setBlockName("meteor_brick_chiseled").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_brick_chiseled");
		meteor_pillar = new BlockRotatablePillar(Material.rock, RefStrings.MODID + ":meteor_pillar_top").setBlockName("meteor_pillar").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_pillar");
		meteor_spawner = new BlockCybercrab(Material.rock).setBlockName("meteor_spawner").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F);
		meteor_battery = new BlockPillar(Material.rock, RefStrings.MODID + ":meteor_power").setBlockName("meteor_battery").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":meteor_spawner_side");
		moon_turf = new BlockFalling(Material.sand).setBlockName("moon_turf").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":moon_turf");

		brick_jungle = new BlockGeneric(Material.rock).setBlockName("brick_jungle").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle");
		brick_jungle_cracked = new BlockGeneric(Material.rock).setBlockName("brick_jungle_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_cracked");
		brick_jungle_fragile = new FragileBrick(Material.rock).setBlockName("brick_jungle_fragile").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_fragile");
		brick_jungle_lava = new BlockGeneric(Material.rock).setBlockName("brick_jungle_lava").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setLightLevel(5F/15F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_lava");
		brick_jungle_ooze = new BlockOre(Material.rock).setBlockName("brick_jungle_ooze").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setLightLevel(5F/15F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_ooze");
		brick_jungle_mystic = new BlockOre(Material.rock).setBlockName("brick_jungle_mystic").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setLightLevel(5F/15F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_mystic");
		brick_jungle_trap = new TrappedBrick(Material.rock).setBlockName("brick_jungle_trap").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_trap");
		brick_jungle_glyph = new BlockGlyph(Material.rock).setBlockName("brick_jungle_glyph").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F);
		brick_jungle_circle = new BlockBallsSpawner(Material.rock).setBlockName("brick_jungle_circle").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(360.0F).setBlockTextureName(RefStrings.MODID + ":brick_jungle_circle");

		brick_red = new BlockRedBrick(Material.rock).setBlockName("brick_red").setResistance(10_000);

		deco_computer = new BlockDecoModel(Material.iron, DecoComputerEnum.class, true, false).setBlockBoundsTo(.125F, 0F, 0F, .875F, .875F, .625F).setBlockName("deco_computer").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_computer");
		deco_crt = new BlockDecoCRT(Material.iron).setBlockName("deco_crt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		deco_toaster = new BlockDecoToaster(Material.iron).setBlockName("deco_toaster").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		filing_cabinet = new BlockDecoContainer(Material.iron, DecoCabinetEnum.class, true, false, TileEntityFileCabinet.class).setBlockBoundsTo(.1875F, 0F, 0F, .8125F, 1F, .75F).setBlockName("filing_cabinet").setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");

		tape_recorder = new DecoTapeRecorder(Material.iron).setBlockName("tape_recorder").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_tape_recorder");
		steel_poles = new DecoSteelPoles(Material.iron).setBlockName("steel_poles").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_beam");
		pole_top = new DecoPoleTop(Material.iron).setBlockName("pole_top").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_pole_top");
		pole_satellite_receiver = new DecoPoleSatelliteReceiver(Material.iron).setBlockName("pole_satellite_receiver").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_satellite_receiver");
		steel_wall = new DecoBlock(Material.iron).setBlockName("steel_wall").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_wall");
		steel_corner = new DecoBlock(Material.iron).setBlockName("steel_corner").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_wall");
		steel_roof = new DecoBlock(Material.iron).setBlockName("steel_roof").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_roof");
		steel_beam = new DecoBlock(Material.iron).setBlockName("steel_beam").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_beam");
		steel_scaffold = new BlockScaffold().setBlockName("steel_scaffold").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_steel_orig");
		steel_grate = new BlockGrate(Material.iron).setBlockName("steel_grate").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F);
		steel_grate_wide = new BlockGrate(Material.iron).setBlockName("steel_grate_wide").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F);

		deco_pipe = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side", 0).setBlockName("deco_pipe").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top");
		deco_pipe_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_rusty", 0).setBlockName("deco_pipe_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_rusty");
		deco_pipe_green = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green", 0).setBlockName("deco_pipe_green").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green");
		deco_pipe_green_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green_rusty", 0).setBlockName("deco_pipe_green_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green_rusty");
		deco_pipe_red = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_red", 0).setBlockName("deco_pipe_red").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_red");
		deco_pipe_marked = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_marked", 0).setBlockName("deco_pipe_marked").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_marked");
		deco_pipe_rim = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side", 1).setBlockName("deco_pipe_rim").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top");
		deco_pipe_rim_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_rusty", 1).setBlockName("deco_pipe_rim_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_rusty");
		deco_pipe_rim_green = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green", 1).setBlockName("deco_pipe_rim_green").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green");
		deco_pipe_rim_green_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green_rusty", 1).setBlockName("deco_pipe_rim_green_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green_rusty");
		deco_pipe_rim_red = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_red", 1).setBlockName("deco_pipe_rim_red").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_red");
		deco_pipe_rim_marked = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_marked", 1).setBlockName("deco_pipe_rim_marked").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_marked");
		deco_pipe_framed = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side", 3).setBlockName("deco_pipe_framed").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top");
		deco_pipe_framed_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_rusty", 3).setBlockName("deco_pipe_framed_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_rusty");
		deco_pipe_framed_green = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green", 3).setBlockName("deco_pipe_framed_green").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green");
		deco_pipe_framed_green_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green_rusty", 3).setBlockName("deco_pipe_framed_green_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green_rusty");
		deco_pipe_framed_red = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_red", 3).setBlockName("deco_pipe_framed_red").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_red");
		deco_pipe_framed_marked = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_marked", 3).setBlockName("deco_pipe_framed_marked").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_marked");
		deco_pipe_quad = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side", 2).setBlockName("deco_pipe_quad").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top");
		deco_pipe_quad_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_rusty", 2).setBlockName("deco_pipe_quad_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_rusty");
		deco_pipe_quad_green = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green", 2).setBlockName("deco_pipe_quad_green").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green");
		deco_pipe_quad_green_rusted = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_green_rusty", 2).setBlockName("deco_pipe_quad_green_rusted").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_green_rusty");
		deco_pipe_quad_red = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_red", 2).setBlockName("deco_pipe_quad_red").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_red");
		deco_pipe_quad_marked = new BlockPipe(Material.iron, RefStrings.MODID + ":pipe_side_marked", 2).setBlockName("deco_pipe_quad_marked").setStepSound(ModSoundTypes.grate).setCreativeTab(MainRegistry.blockTab).setHardness(2.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":pipe_top_marked");

		broadcaster_pc = new PinkCloudBroadcaster(Material.iron).setBlockName("broadcaster_pc").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":broadcaster_pc");
		geiger = new GeigerCounter(Material.iron).setBlockName("geiger").setCreativeTab(MainRegistry.machineTab).setHardness(15.0F).setResistance(0.25F).setBlockTextureName(RefStrings.MODID + ":geiger");
		hev_battery = new HEVBattery(Material.iron).setBlockName("hev_battery").setCreativeTab(MainRegistry.machineTab).setLightLevel(10F/15F).setHardness(0.5F).setResistance(0.25F).setBlockTextureName(RefStrings.MODID + ":hev_battery");

		fence_metal = new BlockMetalFence(Material.iron).setBlockName("fence_metal").setCreativeTab(MainRegistry.machineTab).setHardness(15.0F).setResistance(0.25F).setBlockTextureName(RefStrings.MODID + ":fence_metal");

		ash_digamma = new BlockAshes(Material.sand).setBlockName("ash_digamma").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setResistance(150.0F).setBlockTextureName(RefStrings.MODID + ":ash_digamma");
		sand_boron = new BlockFalling(Material.sand).setBlockName("sand_boron").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_boron");
		sand_lead = new BlockFalling(Material.sand).setBlockName("sand_lead").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_lead");
		sand_uranium = new BlockFalling(Material.sand).setBlockName("sand_uranium").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_uranium");
		sand_polonium = new BlockFalling(Material.sand).setBlockName("sand_polonium").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_polonium");
		sand_quartz = new BlockFalling(Material.sand).setBlockName("sand_quartz").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_quartz");
		glass_boron = new BlockNTMGlassCT(0, RefStrings.MODID + ":glass_boron", Material.glass).setBlockName("glass_boron").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		glass_lead = new BlockNTMGlassCT(0, RefStrings.MODID + ":glass_lead", Material.glass).setBlockName("glass_lead").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		glass_uranium = new BlockNTMGlassCT(1, RefStrings.MODID + ":glass_uranium", Material.glass).setBlockName("glass_uranium").setLightLevel(5F/15F).setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		glass_trinitite = new BlockNTMGlassCT(1, RefStrings.MODID + ":glass_trinitite", Material.glass).setBlockName("glass_trinitite").setLightLevel(5F/15F).setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		glass_polonium = new BlockNTMGlassCT(1, RefStrings.MODID + ":glass_polonium", Material.glass).setBlockName("glass_polonium").setLightLevel(5F/15F).setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
		glass_ash = new BlockNTMGlassCT(1, RefStrings.MODID + ":glass_ash", Material.glass).setBlockName("glass_ash").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(3F);
		glass_quartz = new BlockNTMGlassCT(0, RefStrings.MODID + ":glass_quartz", Material.packedIce, true).setBlockName("glass_quartz").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGlass).setHardness(1.0F).setResistance(40.0F).setBlockTextureName(RefStrings.MODID + "glass_quartz");
		glass_polarized = new BlockNTMGlassCT(0, RefStrings.MODID + ":glass_polarized", Material.glass).setBlockName("glass_polarized").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);

		mush = new BlockMush(Material.plants).setBlockName("mush").setCreativeTab(MainRegistry.blockTab).setLightLevel(0.5F).setStepSound(Block.soundTypeGrass).setBlockTextureName(RefStrings.MODID + ":mush");
		mush_block = new BlockMushHuge(Material.plants).setBlockName("mush_block").setLightLevel(1.0F).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":mush_block_skin");
		mush_block_stem = new BlockMushHuge(Material.plants).setBlockName("mush_block_stem").setLightLevel(1.0F).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":mush_block_stem");
		glyphid_base = new BlockGlyphid(Material.coral).setBlockName("glyphid_base").setStepSound(Block.soundTypeCloth).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":glyphid_base");
		glyphid_spawner = new BlockGlyphidSpawner(Material.coral).setBlockName("glyphid_spawner").setStepSound(Block.soundTypeCloth).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":glyphid_eggs_alt");

		plant_flower = new BlockNTMFlower().setBlockName("plant_flower").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.0F);
		plant_tall = new BlockTallPlant().setBlockName("plant_tall").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.0F);
		plant_dead = new BlockDeadPlant().setBlockName("plant_dead").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.0F);
		reeds = new BlockReeds().setBlockName("plant_reeds").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.0F);
		vine_phosphor = new BlockHangingVine(thick_foliage).setBlockName("vine_phosphor").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGrass).setHardness(0.5F);

		waste_earth = new WasteEarth(Material.ground, true).setBlockName("waste_earth").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.6F).setBlockTextureName(RefStrings.MODID + ":waste_earth");
		waste_mycelium = new WasteEarth(Material.ground, true).setBlockName("waste_mycelium").setStepSound(Block.soundTypeGrass).setLightLevel(1F).setCreativeTab(MainRegistry.blockTab).setHardness(0.6F).setBlockTextureName(RefStrings.MODID + ":waste_mycelium_side");
		waste_trinitite = new BlockOre(Material.sand).noFortune().setBlockName("waste_trinitite").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_trinitite");
		waste_trinitite_red = new BlockOre(Material.sand).noFortune().setBlockName("waste_trinitite_red").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_trinitite_red");
		waste_log = new WasteLog(Material.wood).setBlockName("waste_log").setStepSound(Block.soundTypeWood).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(2.5F);
		waste_leaves = new WasteLeaves(Material.leaves).setBlockName("waste_leaves").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setBlockTextureName(RefStrings.MODID + ":waste_leaves");
		waste_planks = new BlockOre(Material.wood).setBlockName("waste_planks").setStepSound(Block.soundTypeWood).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_planks");
		frozen_dirt = new BlockOre(Material.ground).setBlockName("frozen_dirt").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":frozen_dirt");
		frozen_grass = new WasteEarth(Material.ground, false).setBlockName("frozen_grass").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F);
		frozen_log = new WasteLog(Material.wood).setBlockName("frozen_log").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F);
		frozen_planks = new BlockOre(Material.wood).setBlockName("frozen_planks").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":frozen_planks");
		fallout = new BlockFallout(Material.sand).setBlockName("fallout").setStepSound(Block.soundTypeGravel).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":ash");
		foam_layer = new BlockLayering(Material.snow).setBlockName("foam_layer").setStepSound(Block.soundTypeSnow).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":foam");
		sand_boron_layer = new BlockLayering(Material.sand).setBlockName("sand_boron_layer").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":sand_boron");
		leaves_layer = new BlockLayering(Material.leaves).setBlockName("leaves_layer").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":waste_leaves");
		oil_spill = new BlockLayering(Material.ground).setBlockName("oil_spill").setStepSound(Block.soundTypeSnow).setCreativeTab(MainRegistry.blockTab).setHardness(0.1F).setLightOpacity(0).setBlockTextureName(RefStrings.MODID + ":oil_spill");

		burning_earth = new WasteEarth(Material.ground, true).setBlockName("burning_earth").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.6F).setBlockTextureName(RefStrings.MODID + ":burning_earth");
		tektite = new BlockGeneric(Material.sand).setBlockName("tektite").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":tektite");
		ore_tektite_osmiridium = new BlockGeneric(Material.sand).setBlockName("ore_tektite_osmiridium").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":ore_tektite_osmiridium");
		impact_dirt = new BlockDirt(Material.ground, true).setBlockName("impact_dirt").setStepSound(Block.soundTypeGravel).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":waste_earth_bottom");
		dirt_dead = new BlockFalling(Material.ground).setBlockName("dirt_dead").setStepSound(Block.soundTypeGravel).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":dirt_dead");
		dirt_oily = new BlockFalling(Material.ground).setBlockName("dirt_oily").setStepSound(Block.soundTypeGravel).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":dirt_oily");
		sand_dirty = new BlockFalling(Material.sand).setBlockName("sand_dirty").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_dirty");
		sand_dirty_red = new BlockFalling(Material.sand).setBlockName("sand_dirty_red").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setBlockTextureName(RefStrings.MODID + ":sand_dirty_red");
		stone_cracked = new BlockFalling(Material.rock).setBlockName("stone_cracked").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":stone_cracked");

		sellafield_slaked = new BlockSellafieldSlaked(Material.rock).setBlockName("sellafield_slaked").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_slaked");
		sellafield_bedrock = new BlockSellafieldSlaked(Material.rock).setBlockName("sellafield_bedrock").setBlockUnbreakable().setResistance(6000000.0F).setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sellafield_slaked");
		sellafield = new BlockSellafield(Material.rock).setBlockName("sellafield").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_0");
		ore_sellafield_diamond = new BlockSellafieldOre(Material.rock).setBlockName("ore_sellafield_diamond").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":ore_overlay_diamond");
		ore_sellafield_emerald = new BlockSellafieldOre(Material.rock).setBlockName("ore_sellafield_emerald").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":ore_overlay_emerald");
		ore_sellafield_uranium_scorched = new BlockSellafieldOre(Material.rock).setBlockName("ore_sellafield_uranium_scorched").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":ore_overlay_uranium_scorched");
		ore_sellafield_schrabidium = new BlockSellafieldOre(Material.rock).setBlockName("ore_sellafield_schrabidium").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":ore_overlay_schrabidium");
		ore_sellafield_radgem = new BlockSellafieldOre(Material.rock).setBlockName("ore_sellafield_radgem").setStepSound(Block.soundTypeStone).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":ore_overlay_radgem");

		geysir_water = new BlockGeysir(Material.rock).setBlockName("geysir_water").setStepSound(Block.soundTypeStone).setHardness(5.0F);
		geysir_chlorine = new BlockGeysir(Material.rock).setBlockName("geysir_chlorine").setStepSound(Block.soundTypeStone).setHardness(5.0F);
		geysir_vapor = new BlockGeysir(Material.rock).setBlockName("geysir_vapor").setStepSound(Block.soundTypeStone).setHardness(5.0F);
		geysir_nether = new BlockGeysir(Material.rock).setBlockName("geysir_nether").setLightLevel(1.0F).setStepSound(Block.soundTypeStone).setHardness(2.0F);

		nuke_gadget = new NukeGadget(Material.iron).setBlockName("nuke_gadget").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":theGadget");
		nuke_boy = new NukeBoy(Material.iron).setBlockName("nuke_boy").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":lilBoy");
		nuke_man = new NukeMan(Material.iron).setBlockName("nuke_man").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":fatMan");
		nuke_mike = new NukeMike(Material.iron).setBlockName("nuke_mike").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":ivyMike");
		nuke_tsar = new NukeTsar(Material.iron).setBlockName("nuke_tsar").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":tsarBomba");
		nuke_fleija = new NukeFleija(Material.iron).setBlockName("nuke_fleija").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":fleija");
		nuke_prototype = new NukePrototype(Material.iron).setBlockName("nuke_prototype").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200).setBlockTextureName(RefStrings.MODID + ":prototype");
		nuke_custom = new NukeCustom(Material.iron).setBlockName("nuke_custom").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":custom");
		nuke_solinium = new NukeSolinium(Material.iron).setBlockName("nuke_solinium").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":nuke_solinium");
		nuke_n2 = new NukeN2(Material.iron).setBlockName("nuke_n2").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":nuke_n2");
		nuke_fstbmb = new NukeBalefire(Material.iron).setBlockName("nuke_fstbmb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":nuke_fstbmb");

		bomb_multi = new BombMulti(Material.iron).setBlockName("bomb_multi").setCreativeTab(MainRegistry.nukeTab).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":bomb_multi1");

		flame_war = new BombFlameWar(Material.iron).setBlockName("flame_war").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F).setBlockTextureName(RefStrings.MODID + ":flame_war");
		float_bomb = new BombFloat(Material.iron).setBlockName("float_bomb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F);
		therm_endo = new BombThermo(Material.iron).setBlockName("therm_endo").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F);
		therm_exo = new BombThermo(Material.iron).setBlockName("therm_exo").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F);
		emp_bomb = new BombFloat(Material.iron).setBlockName("emp_bomb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(200.0F);
		det_cord = new DetCord(Material.iron).setBlockName("det_cord").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_cord");
		det_charge = new ExplosiveCharge(Material.iron).setBlockName("det_charge").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_charge");
		det_nuke = new ExplosiveCharge(Material.iron).setBlockName("det_nuke").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_nuke");
		det_miner = new DetMiner(Material.iron, RefStrings.MODID + ":det_miner_top").setBlockName("det_miner").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_miner_side");
		red_barrel = new RedBarrel(Material.iron, true).setBlockName("red_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_red");
		pink_barrel = new RedBarrel(Material.iron, true).setBlockName("pink_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_pink");
		yellow_barrel = new YellowBarrel(Material.iron).setBlockName("yellow_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_yellow");
		vitrified_barrel = new YellowBarrel(Material.iron).setBlockName("vitrified_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_vitrified");
		lox_barrel = new RedBarrel(Material.iron, false).setBlockName("lox_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_lox");
		taint_barrel = new RedBarrel(Material.iron, false).setBlockName("taint_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":barrel_taint");
		crashed_balefire = new BlockCrashedBomb(Material.iron).setBlockName("crashed_bomb").setCreativeTab(MainRegistry.nukeTab).setBlockUnbreakable().setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":block_rust");
		fireworks = new BlockFireworks(Material.iron).setBlockName("fireworks").setCreativeTab(MainRegistry.nukeTab).setResistance(5.0F);
		charge_dynamite = new BlockChargeDynamite().setBlockName("charge_dynamite").setCreativeTab(MainRegistry.nukeTab).setResistance(1.0F);
		charge_miner = new BlockChargeMiner().setBlockName("charge_miner").setCreativeTab(MainRegistry.nukeTab).setResistance(1.0F);
		charge_c4 = new BlockChargeC4().setBlockName("charge_c4").setCreativeTab(MainRegistry.nukeTab).setResistance(1.0F);
		charge_semtex = new BlockChargeSemtex().setBlockName("charge_semtex").setCreativeTab(MainRegistry.nukeTab).setResistance(1.0F);
		mine_ap = new Landmine(Material.iron, 1.5D, 1D).setBlockName("mine_ap").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_ap");
		mine_he = new Landmine(Material.iron, 2D, 5D).setBlockName("mine_he").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_he");
		mine_shrap = new Landmine(Material.iron, 1.5D, 1D).setBlockName("mine_shrap").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_shrap");
		mine_fat = new Landmine(Material.iron, 2.5D, 1D).setBlockName("mine_fat").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_fat");
		mine_naval = new Landmine(Material.iron, 2.5D, 1D).setBlockName("mine_naval").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_naval");
		dynamite = new BlockDynamite().setBlockName("dynamite").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":dynamite");
		tnt = new BlockTNT().setBlockName("tnt_ntm").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":tnt");
		semtex = new BlockSemtex().setBlockName("semtex").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":semtex");
		c4 = new BlockC4().setBlockName("c4").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":c4");
		fissure_bomb = new BlockFissureBomb().setBlockName("fissure_bomb").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.nukeTab).setHardness(0.0F).setBlockTextureName(RefStrings.MODID + ":fissure_bomb");

		pump_steam = new MachinePump().setBlockName("pump_steam").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_copper");
		pump_electric = new MachinePump().setBlockName("pump_electric").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		heater_firebox = new HeaterFirebox().setBlockName("heater_firebox").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		heater_oven = new HeaterOven().setBlockName("heater_oven").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		heater_oilburner = new HeaterOilburner().setBlockName("heater_oilburner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		heater_electric = new HeaterElectric().setBlockName("heater_electric").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		heater_heatex = new HeaterHeatex().setBlockName("heater_heatex").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_ashpit = new MachineAshpit().setBlockName("machine_ashpit").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName("stonebrick");

		furnace_iron = new FurnaceIron().setBlockName("furnace_iron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		furnace_steel = new FurnaceSteel().setBlockName("furnace_steel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		furnace_combination = new FurnaceCombination().setBlockName("furnace_combination").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_light_alt");
		machine_stirling = new MachineStirling().setBlockName("machine_stirling").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_stirling_steel = new MachineStirling().setBlockName("machine_stirling_steel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_stirling_creative = new MachineStirling().setBlockName("machine_stirling_creative").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_sawmill = new MachineSawmill().setBlockName("machine_sawmill").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_strand_caster = new MachineStrandCaster().setBlockName("machine_strand_caster").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		machine_crucible = new MachineCrucible().setBlockName("machine_crucible").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		machine_boiler = new MachineHeatBoiler().setBlockName("machine_boiler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_copper");
		machine_industrial_boiler = new MachineHeatBoilerIndustrial().setBlockName("machine_industrial_boiler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		foundry_mold = new FoundryMold().setBlockName("foundry_mold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		foundry_basin = new FoundryBasin().setBlockName("foundry_basin").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		foundry_channel = new FoundryChannel().setBlockName("foundry_channel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		foundry_tank = new FoundryTank().setBlockName("foundry_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		foundry_outlet = new FoundryOutlet().setBlockName("foundry_outlet").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		foundry_slagtap = new FoundrySlagtap().setBlockName("foundry_slagtap").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		slag = new BlockDynamicSlag().setBlockName("slag").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":slag");

		machine_difurnace_off = new MachineDiFurnace(false).setBlockName("machine_difurnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_difurnace_on = new MachineDiFurnace(true).setBlockName("machine_difurnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		machine_difurnace_extension = new MachineDiFurnaceExtension().setBlockName("machine_difurnace_extension").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_difurnace_rtg_off = new MachineDiFurnaceRTG(false).setBlockName("machine_difurnace_rtg_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_difurnace_rtg_on = new MachineDiFurnaceRTG(true).setBlockName("machine_difurnace_rtg_on").setHardness(5.0F).setResistance(10.0F).setLightLevel(2.0F).setCreativeTab(null);

		machine_centrifuge = new MachineCentrifuge(Material.iron).setBlockName("machine_centrifuge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_gascent = new MachineGasCent(Material.iron).setBlockName("machine_gascent").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_fel = new MachineFEL(Material.iron).setBlockName("machine_fel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_silex = new MachineSILEX(Material.iron).setBlockName("machine_silex").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_rotary_furnace = new MachineRotaryFurnace(Material.iron).setBlockName("machine_rotary_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		machine_crystallizer = new MachineCrystallizer(Material.iron).setBlockName("machine_crystallizer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_crystallizer");

		machine_uf6_tank = new MachineUF6Tank(Material.iron).setBlockName("machine_uf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		machine_puf6_tank = new MachinePuF6Tank(Material.iron).setBlockName("machine_puf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		machine_reactor_breeding = new MachineReactorBreeding(Material.iron).setBlockName("machine_reactor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_reactor");

		machine_furnace_brick_off = new MachineBrickFurnace(false).setBlockName("machine_furnace_brick_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_furnace_brick_on = new MachineBrickFurnace(true).setBlockName("machine_furnace_brick_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		machine_rtg_furnace_off = new MachineRtgFurnace(false).setBlockName("machine_rtg_furnace_off").setHardness(5.0F).setResistance(10.0F);
		machine_rtg_furnace_on = new MachineRtgFurnace(true).setBlockName("machine_rtg_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);

		machine_industrial_generator = new MachineIGenerator(Material.iron).setBlockName("machine_industrial_generator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName("gold_block");
		machine_cyclotron = new MachineCyclotron(Material.iron).setBlockName("machine_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":cyclotron");
		machine_exposure_chamber = new MachineExposureChamber(Material.iron).setBlockName("machine_exposure_chamber").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		machine_radgen = new MachineRadGen(Material.iron).setBlockName("machine_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_radgen");

		hadron_plating = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating");
		hadron_plating_blue = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_blue").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_blue");
		hadron_plating_black = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_black").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_black");
		hadron_plating_yellow = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_yellow").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_yellow");
		hadron_plating_striped = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_striped").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_striped");
		hadron_plating_voltz = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_voltz").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_voltz");
		hadron_plating_glass = new BlockNTMGlass(0, RefStrings.MODID + ":hadron_plating_glass", Material.iron, true).setStepSound(Block.soundTypeMetal).setBlockName("hadron_plating_glass").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_plating_glass");
		hadron_coil_alloy = new BlockHadronCoil(Material.iron, 10).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_alloy").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_alloy");
		hadron_coil_gold = new BlockHadronCoil(Material.iron, 25).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_gold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_gold");
		hadron_coil_neodymium = new BlockHadronCoil(Material.iron, 50).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_neodymium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_neodymium");
		hadron_coil_magtung = new BlockHadronCoil(Material.iron, 100).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_magtung").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_magtung");
		hadron_coil_schrabidium = new BlockHadronCoil(Material.iron, 250).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_schrabidium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_schrabidium");
		hadron_coil_schrabidate = new BlockHadronCoil(Material.iron, 500).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_schrabidate").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_schrabidate");
		hadron_coil_starmetal = new BlockHadronCoil(Material.iron, 1000).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_starmetal").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_starmetal");
		hadron_coil_chlorophyte = new BlockHadronCoil(Material.iron, 2500).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_chlorophyte").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_chlorophyte");
		hadron_coil_mese = new BlockHadronCoil(Material.iron, 10000).setStepSound(Block.soundTypeMetal).setBlockName("hadron_coil_mese").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_coil_mese");
		hadron_power = new BlockHadronPower(Material.iron, 1000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		hadron_power_10m = new BlockHadronPower(Material.iron, 10000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power_10m").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		hadron_power_100m = new BlockHadronPower(Material.iron, 100000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power_100m").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		hadron_power_1g = new BlockHadronPower(Material.iron, 1000000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power_1g").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		hadron_power_10g = new BlockHadronPower(Material.iron, 10000000000L).setStepSound(Block.soundTypeMetal).setBlockName("hadron_power_10g").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_power");
		hadron_diode = new BlockHadronDiode(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_diode").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		hadron_analysis = new BlockHadronPlating(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_analysis").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_analysis");
		hadron_analysis_glass = new BlockNTMGlass(0, RefStrings.MODID + ":hadron_analysis_glass", Material.iron, true).setStepSound(Block.soundTypeMetal).setBlockName("hadron_analysis_glass").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_analysis_glass");
		hadron_access = new BlockHadronAccess(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_access").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_access");
		hadron_core = new BlockHadronCore(Material.iron).setStepSound(Block.soundTypeMetal).setBlockName("hadron_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":hadron_core");

		pa_source = new BlockPASource().setStepSound(Block.soundTypeMetal).setBlockName("pa_source").setHardness(5.0F).setResistance(10.0F);
		pa_beamline = new BlockPABeamline().setStepSound(Block.soundTypeMetal).setBlockName("pa_beamline").setHardness(5.0F).setResistance(10.0F);
		pa_rfc = new BlockPARFC().setStepSound(Block.soundTypeMetal).setBlockName("pa_rfc").setHardness(5.0F).setResistance(10.0F);
		pa_quadrupole = new BlockPAQuadrupole().setStepSound(Block.soundTypeMetal).setBlockName("pa_quadrupole").setHardness(5.0F).setResistance(10.0F);
		pa_dipole = new BlockPADipole().setStepSound(Block.soundTypeMetal).setBlockName("pa_dipole").setHardness(5.0F).setResistance(10.0F);
		pa_detector = new BlockPADetector().setStepSound(Block.soundTypeMetal).setBlockName("pa_detector").setHardness(5.0F).setResistance(10.0F);

		machine_electric_furnace_off = new MachineElectricFurnace(false).setBlockName("machine_electric_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_electric_furnace_on = new MachineElectricFurnace(true).setBlockName("machine_electric_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		machine_arc_furnace = new MachineArcFurnaceLarge().setBlockName("machine_arc_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_microwave = new MachineMicrowave(Material.iron).setBlockName("machine_microwave").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_microwave");

		machine_battery_potato = new MachineBattery(Material.iron, 10_000).setBlockName("machine_battery_potato").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_battery = new MachineBattery(Material.iron, 1_000_000).setBlockName("machine_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_lithium_battery = new MachineBattery(Material.iron, 50_000_000).setBlockName("machine_lithium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_schrabidium_battery = new MachineBattery(Material.iron, 25_000_000_000L).setBlockName("machine_schrabidium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_dineutronium_battery = new MachineBattery(Material.iron, 1_000_000_000_000L).setBlockName("machine_dineutronium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_fensu = new MachineFENSU(Material.iron).setBlockName("machine_fensu").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_fensu");

		capacitor_bus = new MachineCapacitorBus(Material.iron).setBlockName("capacitor_bus").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		capacitor_copper = new MachineCapacitor(Material.iron, 1_000_000L, "copper").setBlockName("capacitor_copper").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_copper");
		capacitor_gold = new MachineCapacitor(Material.iron, 5_000_000L, "gold").setBlockName("capacitor_gold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName("gold_block");
		capacitor_niobium = new MachineCapacitor(Material.iron, 25_000_000L, "niobium").setBlockName("capacitor_niobium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_niobium");
		capacitor_tantalium = new MachineCapacitor(Material.iron, 150_000_000L, "tantalium").setBlockName("capacitor_tantalium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_tantalium");
		capacitor_schrabidate = new MachineCapacitor(Material.iron, 50_000_000_000L, "schrabidate").setBlockName("capacitor_schrabidate").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_schrabidate");

		machine_wood_burner = new MachineWoodBurner(Material.iron).setBlockName("machine_wood_burner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_diesel = new MachineDiesel().setBlockName("machine_diesel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_combustion_engine = new MachineCombustionEngine().setBlockName("machine_combustion_engine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		machine_shredder = new MachineShredder(Material.iron).setBlockName("machine_shredder").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		machine_teleporter = new MachineTeleporter(Material.iron).setBlockName("machine_teleporter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		teleanchor = new MachineTeleanchor().setBlockName("teleanchor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		field_disturber = new MachineFieldDisturber().setBlockName("field_disturber").setHardness(5.0F).setResistance(200.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":field_disturber");

		machine_rtg_grey = new MachineRTG(Material.iron).setBlockName("machine_rtg_grey").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtg");
		machine_amgen = new MachineAmgen(Material.iron).setBlockName("machine_amgen").setHardness(5.0F).setResistance(10.0F);
		machine_geo = new MachineAmgen(Material.iron).setBlockName("machine_geo").setHardness(5.0F).setResistance(10.0F);
		machine_minirtg = new MachineMiniRTG(Material.iron).setBlockName("machine_minirtg").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtg_cell");
		machine_powerrtg = new MachineMiniRTG(Material.iron).setBlockName("machine_powerrtg").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtg_polonium");
		machine_radiolysis = new MachineRadiolysis(Material.iron).setBlockName("machine_radiolysis").setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		machine_hephaestus = new MachineHephaestus(Material.iron).setBlockName("machine_hephaestus").setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");

		red_wire_coated = new WireCoated(Material.iron).setBlockName("red_wire_coated").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_wire_coated");
		red_cable = new BlockCable(Material.iron).setBlockName("red_cable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":cable_neo");
		red_cable_classic = new BlockCable(Material.iron).setBlockName("red_cable_classic").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_cable_classic");
		red_cable_paintable = new BlockCablePaintable().setBlockName("red_cable_paintable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		red_cable_gauge = new BlockCableGauge().setBlockName("red_cable_gauge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		red_connector = new ConnectorRedWire(Material.iron).setBlockName("red_connector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_connector");
		red_pylon = new PylonRedWire(Material.iron).setBlockName("red_pylon").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon");
		red_pylon_medium_wood = new PylonMedium(Material.wood).setBlockName("red_pylon_medium_wood").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon");
		red_pylon_medium_wood_transformer = new PylonMedium(Material.wood).setBlockName("red_pylon_medium_wood_transformer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon");
		red_pylon_medium_steel = new PylonMedium(Material.iron).setBlockName("red_pylon_medium_steel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon");
		red_pylon_medium_steel_transformer = new PylonMedium(Material.iron).setBlockName("red_pylon_medium_steel_transformer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon");
		red_pylon_large = new PylonLarge(Material.iron).setBlockName("red_pylon_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon_large");
		substation = new Substation(Material.iron).setBlockName("substation").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":substation");
		cable_switch = new CableSwitch(Material.iron).setBlockName("cable_switch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		cable_detector = new CableDetector(Material.iron).setBlockName("cable_detector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		cable_diode = new CableDiode(Material.iron).setBlockName("cable_diode").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":cable_diode");
		machine_detector = new PowerDetector(Material.iron).setBlockName("machine_detector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_detector_off");
		fluid_duct_neo = new FluidDuctStandard(Material.iron).setBlockName("fluid_duct_neo").setStepSound(ModSoundTypes.pipe).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pipe_neo");
		fluid_duct_box = new FluidDuctBox(Material.iron).setBlockName("fluid_duct_box").setStepSound(ModSoundTypes.pipe).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fluid_duct_box");
		fluid_duct_exhaust = new FluidDuctBoxExhaust(Material.iron).setBlockName("fluid_duct_exhaust").setStepSound(ModSoundTypes.pipe).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fluid_duct_box");
		fluid_duct_paintable_block_exhaust = new FluidDuctPaintableBlockExhaust().setBlockName("fluid_duct_paintable_block_exhaust").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		fluid_duct_paintable = new FluidDuctPaintable().setBlockName("fluid_duct_paintable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		fluid_duct_gauge = new FluidDuctGauge().setBlockName("fluid_duct_gauge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		fluid_valve = new FluidValve(Material.iron).setBlockName("fluid_valve").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		fluid_switch = new FluidSwitch(Material.iron).setBlockName("fluid_switch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		fluid_pump = new FluidPump(Material.iron).setBlockName("fluid_pump").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_drain = new MachineDrain(Material.iron).setBlockName("machine_drain").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":concrete");
		radio_torch_sender = new RadioTorchSender().setBlockName("radio_torch_sender").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		radio_torch_receiver = new RadioTorchReceiver().setBlockName("radio_torch_receiver").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		radio_torch_counter = new RadioTorchCounter().setBlockName("radio_torch_counter").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtty_counter");
		radio_torch_logic = new RadioTorchLogic().setBlockName("radio_torch_logic").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		radio_torch_reader = new RadioTorchReader().setBlockName("radio_torch_reader").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtty_reader");
		radio_torch_controller = new RadioTorchController().setBlockName("radio_torch_controller").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rtty_controller");
		radio_telex = new RadioTelex().setBlockName("radio_telex").setHardness(3F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":radio_telex");

		conveyor = new BlockConveyor().setBlockName("conveyor").setHardness(2.0F).setResistance(2.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":conveyor");
		conveyor_express = new BlockConveyorExpress().setBlockName("conveyor_express").setHardness(2.0F).setResistance(2.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":conveyor_express");
		conveyor_double = new BlockConveyorDouble().setBlockName("conveyor_double").setHardness(2.0F).setResistance(2.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":conveyor_double");
		conveyor_triple = new BlockConveyorTriple().setBlockName("conveyor_triple").setHardness(2.0F).setResistance(2.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":conveyor_triple");
		conveyor_chute = new BlockConveyorChute().setBlockName("conveyor_chute").setHardness(2.0F).setResistance(2.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":conveyor");
		conveyor_lift = new BlockConveyorLift().setBlockName("conveyor_lift").setHardness(2.0F).setResistance(2.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":conveyor");
		crane_extractor = new CraneExtractor().setBlockName("crane_extractor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crane_inserter = new CraneInserter().setBlockName("crane_inserter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crane_grabber = new CraneGrabber().setBlockName("crane_grabber").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crane_router = new CraneRouter().setBlockName("crane_router").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crane_boxer = new CraneBoxer().setBlockName("crane_boxer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crane_unboxer = new CraneUnboxer().setBlockName("crane_unboxer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crane_splitter = new CraneSplitter().setBlockName("crane_splitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":crane_side");
		crane_partitioner = new CranePartitioner().setBlockName("crane_partitioner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":crane_partitioner_side");
		fan = new MachineFan().setBlockName("fan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		piston_inserter = new PistonInserter().setBlockName("piston_inserter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		drone_waypoint = new DroneWaypoint().setBlockName("drone_waypoint").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_waypoint");
		drone_crate = new DroneCrate().setBlockName("drone_crate").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		drone_waypoint_request = new DroneWaypointRequest().setBlockName("drone_waypoint_request").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_waypoint_request");
		drone_dock = new DroneDock().setBlockName("drone_dock").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_dock");
		drone_crate_provider = new DroneDock().setBlockName("drone_crate_provider").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_crate_provider");
		drone_crate_requester = new DroneDock().setBlockName("drone_crate_requester").setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":drone_crate_requester");

		pneumatic_tube = new PneumoTube().setBlockName("pneumatic_tube").setStepSound(ModSoundTypes.pipe).setHardness(0.1F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pneumatic_tube");
		pneumatic_tube_paintable = new PneumoTubePaintableBlock().setBlockName("pneumatic_tube_paintable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		chain = new BlockChain(Material.iron).setBlockName("dungeon_chain").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":chain");

		ladder_sturdy = new BlockNTMLadder().setBlockName("ladder_sturdy").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_sturdy");
		ladder_iron = new BlockNTMLadder().setBlockName("ladder_iron").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_iron");
		ladder_gold = new BlockNTMLadder().setBlockName("ladder_gold").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_gold");
		ladder_aluminium = new BlockNTMLadder().setBlockName("ladder_aluminium").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_aluminium");
		ladder_copper = new BlockNTMLadder().setBlockName("ladder_copper").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_copper");
		ladder_titanium = new BlockNTMLadder().setBlockName("ladder_titanium").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_titanium");
		ladder_lead = new BlockNTMLadder().setBlockName("ladder_lead").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_lead");
		ladder_cobalt = new BlockNTMLadder().setBlockName("ladder_cobalt").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_cobalt");
		ladder_steel = new BlockNTMLadder().setBlockName("ladder_steel").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_steel");
		ladder_tungsten = new BlockNTMLadder().setBlockName("ladder_tungsten").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":ladder_tungsten");

		trapdoor_steel = new BlockNTMTrapdoor(Material.iron).setBlockName("trapdoor_steel").setHardness(3F).setResistance(8.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":trapdoor_steel");

		barrel_plastic = new BlockFluidBarrel(Material.iron, 12000).setBlockName("barrel_plastic").setStepSound(Block.soundTypeStone).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_plastic");
		barrel_corroded = new BlockFluidBarrel(Material.iron, 6000).setBlockName("barrel_corroded").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_corroded");
		barrel_iron = new BlockFluidBarrel(Material.iron, 8000).setBlockName("barrel_iron").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_iron");
		barrel_steel = new BlockFluidBarrel(Material.iron, 16000).setBlockName("barrel_steel").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_steel");
		barrel_tcalloy = new BlockFluidBarrel(Material.iron, 24000).setBlockName("barrel_tcalloy").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_tcalloy");
		barrel_antimatter = new BlockFluidBarrel(Material.iron, 16000).setBlockName("barrel_antimatter").setStepSound(Block.soundTypeMetal).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":barrel_antimatter");

		machine_transformer = new MachineTransformer(Material.iron, 10000L, 1).setBlockName("machine_transformer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer_iron");
		machine_transformer_dnt = new MachineTransformer(Material.iron, 1000000000000000L, 1).setBlockName("machine_transformer_dnt").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer");

		machine_satlinker = new MachineSatLinker(Material.iron).setBlockName("machine_satlinker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_satlinker_side");
		machine_keyforge = new MachineKeyForge(Material.iron).setBlockName("machine_keyforge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":machine_keyforge_side");
		machine_armor_table = new BlockArmorTable(Material.iron).setBlockName("machine_armor_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);
		machine_weapon_table = new BlockWeaponTable().setBlockName("machine_weapon_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);

		machine_solar_boiler = new MachineSolarBoiler(Material.iron).setBlockName("machine_solar_boiler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_solar_boiler");
		solar_mirror = new SolarMirror(Material.iron).setBlockName("solar_mirror").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":solar_mirror");

		struct_launcher = new BlockGeneric(Material.iron).setBlockName("struct_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_launcher");
		struct_scaffold = new BlockGeneric(Material.iron).setBlockName("struct_scaffold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_scaffold");
		struct_launcher_core = new BlockStruct(Material.iron).setBlockName("struct_launcher_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_launcher_core");
		struct_launcher_core_large = new BlockStruct(Material.iron).setBlockName("struct_launcher_core_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_launcher_core_large");
		struct_soyuz_core = new BlockSoyuzStruct(Material.iron).setBlockName("struct_soyuz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":struct_soyuz_core");
		struct_iter_core = new BlockITERStruct(Material.iron).setBlockName("struct_iter_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":struct_iter_core");
		struct_plasma_core = new BlockPlasmaStruct(Material.iron).setBlockName("struct_plasma_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":struct_plasma_core");
		struct_watz_core = new BlockWatzStruct(Material.iron).setBlockName("struct_watz_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":struct_watz_core");
		struct_icf_core = new BlockICFStruct(Material.iron).setBlockName("struct_icf_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":struct_icf_core");

		factory_titanium_hull = new BlockGeneric(Material.iron).setBlockName("factory_titanium_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_titanium_hull");
		factory_advanced_hull = new BlockGeneric(Material.iron).setBlockName("factory_advanced_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_advanced_hull");

		cm_block = new BlockCM(Material.iron, EnumCMMaterials.class, true, true).setBlockName("cm_block").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_block");
		cm_sheet = new BlockCM(Material.iron, EnumCMMaterials.class, true, true).setBlockName("cm_sheet").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_sheet");
		cm_engine = new BlockCM(Material.iron, EnumCMEngines.class, true, true).setBlockName("cm_engine").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_engine");
		cm_tank = new BlockCMGlass(Material.iron, EnumCMMaterials.class, true, true).setBlockName("cm_tank").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_tank");
		cm_circuit = new BlockCM(Material.iron, EnumCMCircuit.class, true, true).setBlockName("cm_circuit").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_circuit");
		cm_port = new BlockCMPort(Material.iron, EnumCMMaterials.class, true, true).setBlockName("cm_port").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_port");
		cm_flux = new BlockCMFlux(Material.iron, RefStrings.MODID + ":cm_flux_top").setBlockName("cm_flux").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_flux_side");
		cm_heat = new BlockCMHeat(Material.iron, RefStrings.MODID +":cm_heat_top").setBlockName("cm_heat").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":cm_heat_side");
		custom_machine = new BlockCustomMachine().setBlockName("custom_machine").setCreativeTab(MainRegistry.machineTab).setLightLevel(1F).setHardness(5.0F).setResistance(10.0F);
		cm_anchor = new BlockCMAnchor().setBlockName("custom_machine_anchor").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(10.0F);

		pwr_fuel = new BlockPillarPWR(Material.iron, RefStrings.MODID + ":pwr_fuel_top").setBlockName("pwr_fuel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_fuel_side");
		pwr_control = new BlockPillarPWR(Material.iron, RefStrings.MODID + ":pwr_control_top").setBlockName("pwr_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_control_side");
		pwr_channel = new BlockPillarPWR(Material.iron, RefStrings.MODID + ":pwr_channel_top").setBlockName("pwr_channel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_channel_side");
		pwr_heatex = new BlockGenericPWR(Material.iron).setBlockName("pwr_heatex").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_heatex");
		pwr_heatsink = new BlockGenericPWR(Material.iron).setBlockName("pwr_heatsink").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_heatsink");
		pwr_neutron_source = new BlockGenericPWR(Material.iron).setBlockName("pwr_neutron_source").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_neutron_source");
		pwr_reflector = new BlockGenericPWR(Material.iron).setBlockName("pwr_reflector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_reflector");
		pwr_casing = new BlockGenericPWR(Material.iron).setBlockName("pwr_casing").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_casing");
		pwr_port = new BlockGenericPWR(Material.iron).setBlockName("pwr_port").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_port");
		pwr_controller = new MachinePWRController(Material.iron).setBlockName("pwr_controller").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":pwr_casing_blank");
		pwr_block = new BlockPWR(Material.iron).setBlockName("pwr_block").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pwr_block");

		fusion_conductor = new BlockToolConversionPillar(Material.iron).addVariant("_welded").setBlockName("fusion_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_conductor");
		fusion_center = new BlockPillar(Material.iron, RefStrings.MODID + ":fusion_center_top_alt").setBlockName("fusion_center").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_center_side_alt");
		fusion_motor = new BlockPillar(Material.iron, RefStrings.MODID + ":fusion_motor_top_alt").setBlockName("fusion_motor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_motor_side_alt");
		fusion_heater = new BlockPillar(Material.iron, RefStrings.MODID + ":fusion_heater_top").setBlockName("fusion_heater").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_heater_side");
		fusion_hatch = new FusionHatch(Material.iron).setBlockName("fusion_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_hatch");
		plasma = new BlockPlasma(Material.iron).setBlockName("plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":plasma");
		iter = new MachineITER().setBlockName("iter").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":iter");
		plasma_heater = new MachinePlasmaHeater().setBlockName("plasma_heater").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":plasma_heater");

		machine_icf_press = new MachineICFPress().setBlockName("machine_icf_press").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		icf = new MachineICF().setBlockName("icf").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		icf_component = new BlockICFComponent().setBlockName("icf_component").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":icf_component");
		icf_controller = new MachineICFController().setBlockName("icf_controller").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":icf_casing");
		icf_laser_component = new BlockICFLaserComponent().setBlockName("icf_laser_component").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.machineTab);
		icf_block = new BlockICF(Material.iron).setBlockName("icf_block").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":icf_block");

		watz_element = new BlockPillar(Material.iron, RefStrings.MODID + ":watz_element_top").setBlockName("watz_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_element_side");
		watz_cooler = new BlockPillar(Material.iron, RefStrings.MODID + ":watz_cooler_top").setBlockName("watz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_cooler_side");
		watz_end = new BlockToolConversion(Material.iron).addVariant("_bolted").setBlockName("watz_end").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_casing");
		watz = new Watz().setBlockName("watz").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		watz_pump = new WatzPump().setBlockName("watz_pump").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		balefire = new Balefire().setBlockName("balefire").setHardness(0.0F).setLightLevel(1.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":balefire");
		fire_digamma = new DigammaFlame().setBlockName("fire_digamma").setHardness(0.0F).setResistance(150F).setLightLevel(1.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":fire_digamma");
		digamma_matter = new DigammaMatter().setBlockName("digamma_matter").setBlockUnbreakable().setResistance(18000000).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":digamma_matter");

		machine_converter_he_rf = new BlockConverterHeRf(Material.iron).setBlockName("machine_converter_he_rf").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_converter_he_rf");
		machine_converter_rf_he = new BlockConverterRfHe(Material.iron).setBlockName("machine_converter_rf_he").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_converter_rf_he");

		dfc_emitter = new CoreComponent(Material.iron).setBlockName("dfc_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_emitter");
		dfc_injector = new CoreComponent(Material.iron).setBlockName("dfc_injector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_injector");
		dfc_receiver = new CoreComponent(Material.iron).setBlockName("dfc_receiver").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_receiver");
		dfc_stabilizer = new CoreComponent(Material.iron).setBlockName("dfc_stabilizer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_stabilizer");
		dfc_core = new CoreCore(Material.iron).setBlockName("dfc_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":dfc_core");

		seal_frame = new BlockGeneric(Material.iron).setBlockName("seal_frame").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":seal_frame");
		seal_controller = new BlockSeal(Material.iron).setBlockName("seal_controller").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
		seal_hatch = new BlockHatch(Material.iron).setBlockName("seal_hatch").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":seal_hatch_3");

		vault_door = new VaultDoor(Material.iron).setBlockName("vault_door").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vault_door");
		blast_door = new BlastDoor(Material.iron).setBlockName("blast_door").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":blast_door");

		sliding_blast_door = new BlockDoorGeneric(Material.iron, DoorDecl.SLIDE_DOOR).setBlockName("sliding_blast_door").setHardness(10.0F).setResistance(750.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":sliding_blast_door");
		fire_door = new BlockDoorGeneric(Material.iron, DoorDecl.FIRE_DOOR).setBlockName("fire_door").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fire_door");
		transition_seal = new BlockDoorGeneric(Material.iron, DoorDecl.TRANSITION_SEAL).setBlockName("transition_seal").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":transition_seal");
		silo_hatch = new BlockDoorGeneric(Material.iron, DoorDecl.SILO_HATCH).setBlockName("silo_hatch").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		silo_hatch_large = new BlockDoorGeneric(Material.iron, DoorDecl.SILO_HATCH_LARGE).setBlockName("silo_hatch_large").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		secure_access_door = new BlockDoorGeneric(Material.iron, DoorDecl.SECURE_ACCESS_DOOR).setBlockName("secure_access_door").setHardness(20.0F).setResistance(2_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		large_vehicle_door = new BlockDoorGeneric(Material.iron, DoorDecl.LARGE_VEHICLE_DOOR).setBlockName("large_vehicle_door").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		qe_containment = new BlockDoorGeneric(Material.iron, DoorDecl.QE_CONTAINMENT).setBlockName("qe_containment").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		qe_sliding_door = new BlockDoorGeneric(Material.iron, DoorDecl.QE_SLIDING).setBlockName("qe_sliding_door").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		round_airlock_door = new BlockDoorGeneric(Material.iron, DoorDecl.ROUND_AIRLOCK_DOOR).setBlockName("round_airlock_door").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		sliding_seal_door = new BlockDoorGeneric(Material.iron, DoorDecl.SLIDING_SEAL_DOOR).setBlockName("sliding_seal_door").setHardness(10.0F).setResistance(1_000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		water_door = new BlockDoorGeneric(Material.iron, DoorDecl.WATER_DOOR).setBlockName("water_door").setHardness(5.0F).setResistance(50.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		door_metal = new BlockModDoor(Material.iron).setBlockName("door_metal").setHardness(5.0F).setResistance(5.0F).setBlockTextureName(RefStrings.MODID + ":door_metal");
		door_office = new BlockModDoor(Material.iron).setBlockName("door_office").setHardness(10.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":door_office");
		door_bunker = new BlockModDoor(Material.iron).setBlockName("door_bunker").setHardness(10.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":door_bunker");
		door_red = new BlockModDoor(Material.iron).setBlockName("door_red").setHardness(10.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":door_red");

		barbed_wire = new BarbedWire(Material.iron).setBlockName("barbed_wire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_model");
		barbed_wire_fire = new BarbedWire(Material.iron).setBlockName("barbed_wire_fire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_fire_model");
		barbed_wire_poison = new BarbedWire(Material.iron).setBlockName("barbed_wire_poison").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_poison_model");
		barbed_wire_acid = new BarbedWire(Material.iron).setBlockName("barbed_wire_acid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_acid_model");
		barbed_wire_wither = new BarbedWire(Material.iron).setBlockName("barbed_wire_wither").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_wither_model");
		barbed_wire_ultradeath = new BarbedWire(Material.iron).setBlockName("barbed_wire_ultradeath").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire_ultradeath_model");
		spikes = new Spikes(Material.iron).setBlockName("spikes").setHardness(2.5F).setResistance(5.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":spikes");

		charger = new Charger(Material.iron).setBlockName("charger").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		refueler = new BlockRefueler(Material.iron).setBlockName("refueler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		tesla = new MachineTesla(Material.iron).setBlockName("tesla").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":tesla");

		launch_pad = new LaunchPad(Material.iron).setBlockName("launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_rusted = new LaunchPadRusted(Material.iron).setBlockName("launch_pad_rusted").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":block_rust");
		launch_pad_large = new LaunchPadLarge(Material.iron).setBlockName("launch_pad_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_radar = new MachineRadar(Material.iron).setBlockName("machine_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_radar");
		machine_radar_large = new MachineRadarLarge(Material.iron).setBlockName("machine_radar_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		radar_screen = new MachineRadarScreen(Material.iron).setBlockName("radar_screen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":block_steel");

		machine_missile_assembly = new MachineMissileAssembly(Material.iron).setBlockName("machine_missile_assembly").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_missile_assembly");
		compact_launcher = new CompactLauncher(Material.iron).setBlockName("compact_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":compact_launcher");
		launch_table = new LaunchTable(Material.iron).setBlockName("launch_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":launch_table");
		soyuz_launcher = new SoyuzLauncher(Material.iron).setBlockName("soyuz_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":soyuz_launcher");

		sat_mapper = new DecoBlock(Material.iron).setBlockName("sat_mapper").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_mapper");
		sat_radar = new DecoBlock(Material.iron).setBlockName("sat_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_radar");
		sat_scanner = new DecoBlock(Material.iron).setBlockName("sat_scanner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_scanner");
		sat_laser = new DecoBlock(Material.iron).setBlockName("sat_laser").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_laser");
		sat_foeq = new DecoBlock(Material.iron).setBlockName("sat_foeq").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_foeq");
		sat_resonator = new DecoBlock(Material.iron).setBlockName("sat_resonator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_resonator");

		sat_dock = new MachineSatDock(Material.iron).setBlockName("sat_dock").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":sat_dock");
		soyuz_capsule = new SoyuzCapsule(Material.iron).setBlockName("soyuz_capsule").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":soyuz_capsule");
		crate_supply = new BlockSupplyCrate(Material.wood).setBlockName("crate_supply").setStepSound(Block.soundTypeWood).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":crate_can");

		turret_chekhov = new TurretChekhov(Material.iron).setBlockName("turret_chekhov").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_friendly = new TurretFriendly(Material.iron).setBlockName("turret_friendly").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_jeremy = new TurretJeremy(Material.iron).setBlockName("turret_jeremy").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_tauon = new TurretTauon(Material.iron).setBlockName("turret_tauon").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_richard = new TurretRichard(Material.iron).setBlockName("turret_richard").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_howard = new TurretHoward(Material.iron).setBlockName("turret_howard").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_howard_damaged = new TurretHowardDamaged(Material.iron).setBlockName("turret_howard_damaged").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_rust");
		turret_maxwell = new TurretMaxwell(Material.iron).setBlockName("turret_maxwell").setHardness(5.0F).setResistance(60.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_fritz = new TurretFritz(Material.iron).setBlockName("turret_fritz").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_arty = new TurretArty(Material.iron).setBlockName("turret_arty").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_himars = new TurretHIMARS(Material.iron).setBlockName("turret_himars").setHardness(5.0F).setResistance(600.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_sentry = new TurretSentry().setBlockName("turret_sentry").setHardness(5.0F).setResistance(5.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		turret_sentry_damaged = new TurretSentryDamaged().setBlockName("turret_sentry_damaged").setHardness(5.0F).setResistance(5.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":block_rust");

		rbmk_rod = new RBMKRod(false).setBlockName("rbmk_rod").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_element");
		rbmk_rod_mod = new RBMKRod(true).setBlockName("rbmk_rod_mod").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_element_mod");
		rbmk_rod_reasim = new RBMKRodReaSim(false).setBlockName("rbmk_rod_reasim").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_element_reasim");
		rbmk_rod_reasim_mod = new RBMKRodReaSim(true).setBlockName("rbmk_rod_reasim_mod").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_element_reasim_mod");
		rbmk_control = new RBMKControl(false).setBlockName("rbmk_control").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_control");
		rbmk_control_mod = new RBMKControl(true).setBlockName("rbmk_control_mod").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_control_mod");
		rbmk_control_auto = new RBMKControlAuto().setBlockName("rbmk_control_auto").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_control_auto");
		rbmk_blank = new RBMKBlank().setBlockName("rbmk_blank").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_blank");
		rbmk_boiler = new RBMKBoiler().setBlockName("rbmk_boiler").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_boiler");
		rbmk_reflector = new RBMKReflector().setBlockName("rbmk_reflector").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_reflector");
		rbmk_absorber = new RBMKAbsorber().setBlockName("rbmk_absorber").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_absorber");
		rbmk_moderator = new RBMKModerator().setBlockName("rbmk_moderator").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_moderator");
		rbmk_outgasser = new RBMKOutgasser().setBlockName("rbmk_outgasser").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_outgasser");
		rbmk_storage = new RBMKStorage().setBlockName("rbmk_storage").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_storage");
		rbmk_cooler = new RBMKCooler().setBlockName("rbmk_cooler").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_cooler");
		rbmk_heater = new RBMKHeater().setBlockName("rbmk_heater").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_heater");
		rbmk_console = new RBMKConsole().setBlockName("rbmk_console").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_console");
		rbmk_crane_console = new RBMKCraneConsole().setBlockName("rbmk_crane_console").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_crane_console");
		rbmk_autoloader = new RBMKAutoloader().setBlockName("rbmk_autoloader").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":rbmk_autoloader");
		rbmk_loader = new RBMKLoader(Material.iron).setBlockName("rbmk_loader").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":rbmk_loader");
		rbmk_steam_inlet = new RBMKInlet(Material.iron).setBlockName("rbmk_steam_inlet").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":rbmk_steam_inlet");
		rbmk_steam_outlet = new RBMKOutlet(Material.iron).setBlockName("rbmk_steam_outlet").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":rbmk_steam_outlet");
		pribris = new RBMKDebris().setBlockName("pribris").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_debris");
		pribris_burning = new RBMKDebrisBurning().setBlockName("pribris_burning").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_debris_burning");
		pribris_radiating = new RBMKDebrisRadiating().setBlockName("pribris_radiating").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_debris_radiating");
		pribris_digamma = new RBMKDebrisDigamma().setBlockName("pribris_digamma").setCreativeTab(MainRegistry.machineTab).setHardness(50.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":rbmk/rbmk_debris_digamma");

		book_guide = new Guide(Material.iron).setBlockName("book_guide").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.nukeTab);

		rail_wood = new RailGeneric().setMaxSpeed(0.2F).setBlockName("rail_wood").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_wood");
		rail_narrow = new RailGeneric().setBlockName("rail_narrow").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_narrow");
		rail_highspeed = new RailGeneric().setMaxSpeed(1F).setFlexible(false).setBlockName("rail_highspeed").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_highspeed");
		rail_booster = new RailBooster().setBlockName("rail_booster").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_booster");
		rail_narrow_straight = new RailNarrowStraight().setBlockName("rail_narrow_straight").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_narrow_neo");
		rail_narrow_curve = new RailNarrowCurve().setBlockName("rail_narrow_curve").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_narrow_neo");
		rail_large_straight = new RailStandardStraight().setBlockName("rail_large_straight").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		rail_large_straight_short = new RailStandardStraightShort().setBlockName("rail_large_straight_short").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		rail_large_curve = new RailStandardCurveBase().setBlockName("rail_large_curve").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		rail_large_curve_7 = new RailStandardCurveWide7().setBlockName("rail_large_curve_7").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		rail_large_curve_9 = new RailStandardCurveWide9().setBlockName("rail_large_curve_9").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		rail_large_ramp = new RailStandardRamp().setBlockName("rail_large_ramp").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		rail_large_buffer = new RailStandardBuffer().setBlockName("rail_large_buffer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_buffer");
		rail_large_switch = new RailStandardSwitch().setBlockName("rail_large_switch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");
		rail_large_switch_flipped = new RailStandardSwitchFlipped().setBlockName("rail_large_switch_flipped").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":rail_standard_straight");

		crate = new BlockCrate(Material.wood).setBlockName("crate").setStepSound(Block.soundTypeWood).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate");
		crate_weapon = new BlockCrate(Material.wood).setBlockName("crate_weapon").setStepSound(Block.soundTypeWood).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_weapon");
		crate_lead = new BlockCrate(Material.iron).setBlockName("crate_lead").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_lead");
		crate_metal = new BlockCrate(Material.iron).setBlockName("crate_metal").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_metal");
		crate_red = new BlockCrate(Material.iron).setBlockName("crate_red").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crate_red");
		crate_can = new BlockCanCrate(Material.wood).setBlockName("crate_can").setStepSound(Block.soundTypeWood).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_can");
		crate_ammo = new BlockAmmoCrate(Material.iron).setBlockName("crate_ammo").setStepSound(Block.soundTypeMetal).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab);
		crate_jungle = new BlockJungleCrate(Material.rock).setBlockName("crate_jungle").setStepSound(Block.soundTypeStone).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_jungle");
		crate_iron = new BlockStorageCrate(Material.iron).setBlockName("crate_iron").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crate_steel = new BlockStorageCrate(Material.iron).setBlockName("crate_steel").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crate_desh = new BlockStorageCrate(Material.iron).setBlockName("crate_desh").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crate_tungsten = new BlockStorageCrate(Material.iron).setBlockName("crate_tungsten").setStepSound(Block.soundTypeMetal).setHardness(7.5F).setResistance(300.0F).setCreativeTab(MainRegistry.machineTab);
		crate_template = new BlockStorageCrate(Material.iron).setBlockName("crate_template").setStepSound(Block.soundTypeMetal).setHardness(7.5F).setResistance(300.0F).setCreativeTab(MainRegistry.machineTab);
		safe = new BlockStorageCrate(Material.iron).setBlockName("safe").setStepSound(Block.soundTypeMetal).setHardness(7.5F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
		mass_storage = new BlockMassStorage().setBlockName("mass_storage").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		boxcar = new DecoBlock(Material.iron).setBlockName("boxcar").setStepSound(Block.soundTypeMetal).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":boxcar");
		boat = new DecoBlock(Material.iron).setBlockName("boat").setStepSound(Block.soundTypeMetal).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":asphalt");

		machine_well = new MachineOilWell().setBlockName("machine_well").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_well");
		machine_pumpjack = new MachinePumpjack().setBlockName("machine_pumpjack").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_pumpjack");
		machine_fracking_tower = new MachineFrackingTower().setBlockName("machine_fracking_tower").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		oil_pipe = new BlockNoDrop(Material.iron).setBlockName("oil_pipe").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":oil_pipe");
		machine_flare = new MachineGasFlare(Material.iron).setBlockName("machine_flare").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		chimney_brick = new MachineChimneyBrick(Material.iron).setBlockName("chimney_brick").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_fire");
		chimney_industrial = new MachineChimneyIndustrial(Material.iron).setBlockName("chimney_industrial").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":concrete_colored_ext.machine");
		machine_refinery = new MachineRefinery(Material.iron).setBlockName("machine_refinery").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_refinery");
		machine_vacuum_distill = new MachineVacuumDistill(Material.iron).setBlockName("machine_vacuum_distill").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_fraction_tower = new MachineFractionTower(Material.iron).setBlockName("machine_fraction_tower").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		fraction_spacer = new FractionSpacer(Material.iron).setBlockName("fraction_spacer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_catalytic_cracker = new MachineCatalyticCracker(Material.iron).setBlockName("machine_catalytic_cracker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_catalytic_reformer = new MachineCatalyticReformer(Material.iron).setBlockName("machine_catalytic_reformer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_hydrotreater = new MachineHydrotreater(Material.iron).setBlockName("machine_hydrotreater").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_coker = new MachineCoker(Material.iron).setBlockName("machine_coker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_pyrooven = new MachinePyroOven(Material.iron).setBlockName("machine_pyrooven").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_autosaw = new MachineAutosaw().setBlockName("machine_autosaw").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_excavator = new MachineExcavator().setBlockName("machine_excavator").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_ore_slopper = new MachineOreSlopper().setBlockName("machine_ore_slopper").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_mining_laser = new MachineMiningLaser(Material.iron).setBlockName("machine_mining_laser").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_mining_laser");
		barricade = new BlockNoDrop(Material.sand).setBlockName("barricade").setHardness(1.0F).setResistance(2.5F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":barricade");
		machine_assembler = new MachineAssembler(Material.iron).setBlockName("machine_assembler").setHardness(5.0F).setResistance(30.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":machine_assembler");
		machine_assembly_machine = new MachineAssemblyMachine(Material.iron).setBlockName("machine_assembly_machine").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_assemfac = new MachineAssemfac(Material.iron).setBlockName("machine_assemfac").setHardness(5.0F).setResistance(30.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_arc_welder = new MachineArcWelder(Material.iron).setBlockName("machine_arc_welder").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_soldering_station = new MachineSolderingStation(Material.iron).setBlockName("machine_soldering_station").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_chemplant = new MachineChemplant(Material.iron).setBlockName("machine_chemplant").setHardness(5.0F).setResistance(30.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_chemical_plant = new MachineChemicalPlant(Material.iron).setBlockName("machine_chemical_plant").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_chemfac = new MachineChemfac(Material.iron).setBlockName("machine_chemfac").setHardness(5.0F).setResistance(30.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_chemical_factory = new MachineChemicalFactory(Material.iron).setBlockName("machine_chemical_factory").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_purex = new MachinePUREX(Material.iron).setBlockName("machine_purex").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_mixer = new MachineMixer(Material.iron).setBlockName("machine_mixer").setHardness(5.0F).setResistance(30.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_fluidtank = new MachineFluidTank(Material.iron).setBlockName("machine_fluidtank").setHardness(5.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_fluidtank");
		machine_bat9000 = new MachineBigAssTank9000(Material.iron).setBlockName("machine_bat9000").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_orbus = new MachineOrbus(Material.iron).setBlockName("machine_orbus").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_turbofan = new MachineTurbofan(Material.iron).setBlockName("machine_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_turbofan");
		machine_turbinegas = new MachineTurbineGas(Material.iron).setBlockName("machine_turbinegas").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_lpw2 = new MachineLPW2().setBlockName("machine_lpw2").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		press_preheater = new BlockBase(Material.iron).setBlockName("press_preheater").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":press_preheater");
		machine_press = new MachinePress(Material.iron).setBlockName("machine_press").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_press");
		machine_epress = new MachineEPress(Material.iron).setBlockName("machine_epress").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_epress");
		machine_conveyor_press = new MachineConveyorPress(Material.iron).setBlockName("machine_conveyor_press").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_ammo_press = new MachineAmmoPress().setBlockName("machine_ammo_press").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		reactor_research = new ReactorResearch(Material.iron).setBlockName("machine_reactor_small").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_reactor_small");
		reactor_zirnox = new ReactorZirnox(Material.iron).setBlockName("machine_zirnox").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		zirnox_destroyed = new ZirnoxDestroyed(Material.iron).setBlockName("zirnox_destroyed").setHardness(100.0F).setResistance(800.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_controller = new MachineReactorControl(Material.iron).setBlockName("machine_controller").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);

		machine_boiler_off = new MachineBoiler(false).setBlockName("machine_boiler_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":machine_boiler_off");

		machine_steam_engine = new MachineSteamEngine().setBlockName("machine_steam_engine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel");
		machine_turbine = new MachineTurbine(Material.iron).setBlockName("machine_turbine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_turbine");
		machine_large_turbine = new MachineLargeTurbine(Material.iron).setBlockName("machine_large_turbine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_large_turbine");
		machine_chungus = new MachineChungus(Material.iron).setBlockName("machine_chungus").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_chungus");
		machine_condenser = new MachineCondenser(Material.iron).setBlockName("machine_condenser").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":condenser");
		machine_tower_small = new MachineTowerSmall(Material.iron).setBlockName("machine_tower_small").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		machine_tower_large = new MachineTowerLarge(Material.iron).setBlockName("machine_tower_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":concrete");
		machine_condenser_powered = new MachineCondenserPowered(Material.iron).setBlockName("machine_condenser_powered").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");

		machine_deuterium_extractor = new MachineDeuteriumExtractor(Material.iron).setBlockName("machine_deuterium_extractor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_deuterium_extractor_side");
		machine_deuterium_tower = new DeuteriumTower(Material.iron).setBlockName("machine_deuterium_tower").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":concrete");

		machine_liquefactor = new MachineLiquefactor().setBlockName("machine_liquefactor").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		machine_solidifier = new MachineSolidifier().setBlockName("machine_solidifier").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		machine_intake = new MachineIntake().setBlockName("machine_intake").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		machine_compressor = new MachineCompressor().setBlockName("machine_compressor").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");
		machine_compressor_compact = new MachineCompressorCompact().setBlockName("machine_compressor_compact").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");

		machine_electrolyser = new MachineElectrolyser().setBlockName("machine_electrolyser").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":block_steel_machine");

		machine_autocrafter = new MachineAutocrafter().setBlockName("machine_autocrafter").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab);
		machine_funnel = new MachineFunnel().setBlockName("machine_funnel").setHardness(10.0F).setResistance(20.0F).setCreativeTab(MainRegistry.machineTab);

		anvil_iron = new NTMAnvil(Material.iron, NTMAnvil.TIER_IRON).setBlockName("anvil_iron").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_iron");
		anvil_lead = new NTMAnvil(Material.iron, NTMAnvil.TIER_IRON).setBlockName("anvil_lead").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_lead");
		anvil_steel = new NTMAnvil(Material.iron, NTMAnvil.TIER_STEEL).setBlockName("anvil_steel").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_steel");
		anvil_desh = new NTMAnvil(Material.iron, NTMAnvil.TIER_OIL).setBlockName("anvil_desh").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_desh");
		anvil_ferrouranium = new NTMAnvil(Material.iron, NTMAnvil.TIER_NUCLEAR).setBlockName("anvil_ferrouranium").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_ferrouranium");
		anvil_saturnite = new NTMAnvil(Material.iron, NTMAnvil.TIER_RBMK).setBlockName("anvil_saturnite").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_saturnite");
		anvil_bismuth_bronze = new NTMAnvil(Material.iron, NTMAnvil.TIER_RBMK).setBlockName("anvil_bismuth_bronze").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_bismuth_bronze");
		anvil_arsenic_bronze = new NTMAnvil(Material.iron, NTMAnvil.TIER_RBMK).setBlockName("anvil_arsenic_bronze").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_arsenic_bronze");
		anvil_schrabidate = new NTMAnvil(Material.iron, NTMAnvil.TIER_FUSION).setBlockName("anvil_schrabidate").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_schrabidate");
		anvil_dnt = new NTMAnvil(Material.iron, NTMAnvil.TIER_PARTICLE).setBlockName("anvil_dnt").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_dnt");
		anvil_osmiridium = new NTMAnvil(Material.iron, NTMAnvil.TIER_GERALD).setBlockName("anvil_osmiridium").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_osmiridium");
		anvil_murky = new NTMAnvil(Material.iron, 1916169).setBlockName("anvil_murky").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":anvil_steel");

		machine_waste_drum = new WasteDrum(Material.iron).setBlockName("machine_waste_drum").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":waste_drum");
		machine_storage_drum = new StorageDrum(Material.iron).setBlockName("machine_storage_drum").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_storage_drum");

		machine_schrabidium_transmutator = new MachineSchrabidiumTransmutator(Material.iron).setBlockName("machine_schrabidium_transmutator").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);

		machine_siren = new MachineSiren(Material.iron).setBlockName("machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_siren");

		machine_spp_bottom = new SPPBottom(Material.iron).setBlockName("machine_spp_bottom").setHardness(5.0F).setResistance(10.0F);
		machine_spp_top = new SPPTop(Material.iron).setBlockName("machine_spp_top").setHardness(5.0F).setResistance(10.0F);

		radiobox = new Radiobox(Material.iron).setBlockName("radiobox").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":radiobox");
		radiorec = new RadioRec(Material.iron).setBlockName("radiorec").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":radiorec");

		machine_forcefield = new MachineForceField(Material.iron).setBlockName("machine_forcefield").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_forcefield");

		cheater_virus = new CheaterVirus(Material.iron).setBlockName("cheater_virus").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":cheater_virus");
		cheater_virus_seed = new CheaterVirusSeed(Material.iron).setBlockName("cheater_virus_seed").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":cheater_virus_seed");
		crystal_virus = new CrystalVirus(Material.iron).setBlockName("crystal_virus").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_virus");
		crystal_hardened = new BlockGeneric(Material.iron).setBlockName("crystal_hardened").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_hardened");
		crystal_pulsar = new CrystalPulsar(Material.iron).setBlockName("crystal_pulsar").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_pulsar");
		taint = new BlockTaint(Material.iron).setBlockName("taint").setHardness(15.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":taint");

		vent_chlorine = new BlockVent(Material.iron).setBlockName("vent_chlorine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_chlorine");
		vent_cloud = new BlockVent(Material.iron).setBlockName("vent_cloud").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_cloud");
		vent_pink_cloud = new BlockVent(Material.iron).setBlockName("vent_pink_cloud").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_pink_cloud");
		vent_chlorine_seal = new BlockClorineSeal(Material.iron).setBlockName("vent_chlorine_seal").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		chlorine_gas = new BlockGasClorine().setBlockName("chlorine_gas").setHardness(0.0F).setResistance(0.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":chlorine_gas");

		gas_radon = new BlockGasRadon().setBlockName("gas_radon").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_radon");
		gas_radon_dense = new BlockGasRadonDense().setBlockName("gas_radon_dense").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_radon_dense");
		gas_radon_tomb = new BlockGasRadonTomb().setBlockName("gas_radon_tomb").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_radon_tomb");
		gas_meltdown = new BlockGasMeltdown().setBlockName("gas_meltdown").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_meltdown");
		gas_monoxide = new BlockGasMonoxide().setBlockName("gas_monoxide").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_monoxide");
		gas_asbestos = new BlockGasAsbestos().setBlockName("gas_asbestos").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_asbestos");
		gas_coal = new BlockGasCoal().setBlockName("gas_coal").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_coal");
		gas_flammable = new BlockGasFlammable().setBlockName("gas_flammable").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_flammable");
		gas_explosive = new BlockGasExplosive().setBlockName("gas_explosive").setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_explosive");
		vacuum = new BlockVacuum().setBlockName("vacuum").setResistance(1000000F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vacuum");

		absorber = new BlockAbsorber(Material.iron, 2.5F).setBlockName("absorber").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":absorber");
		absorber_red = new BlockAbsorber(Material.iron, 10F).setBlockName("absorber_red").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":absorber_red");
		absorber_green = new BlockAbsorber(Material.iron, 100F).setBlockName("absorber_green").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":absorber_green");
		absorber_pink = new BlockAbsorber(Material.iron, 10000F).setBlockName("absorber_pink").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":absorber_pink");
		decon = new BlockDecon(Material.iron).setBlockName("decon").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":decon_side");

		if (Loader.isModLoaded("OpenComputers")) {
			oc_cable_paintable = new BlockOpenComputersCablePaintable().setBlockName("oc_cable_paintable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		}

		volcano_core = new BlockVolcano().setBlockName("volcano_core").setBlockUnbreakable().setResistance(10000.0F).setCreativeTab(MainRegistry.nukeTab).setBlockTextureName(RefStrings.MODID + ":volcano_core");
		volcano_rad_core = new BlockVolcano().setBlockName("volcano_rad_core").setBlockUnbreakable().setResistance(10000.0F).setCreativeTab(MainRegistry.nukeTab).setBlockTextureName(RefStrings.MODID + ":volcano_rad_core");

		statue_elb_f = new DecoBlockAlt(Material.iron).setBlockName("#undef").setHardness(Float.POSITIVE_INFINITY).setLightLevel(1.0F).setResistance(Float.POSITIVE_INFINITY);

		mud_fluid = new MudFluid().setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(2773).setUnlocalizedName("mud_fluid");
		FluidRegistry.registerFluid(mud_fluid);
		mud_block = new MudBlock(mud_fluid, fluidmud.setReplaceable(), ModDamageSource.mudPoisoning).setBlockName("mud_block").setResistance(500F);

		acid_fluid = new AcidFluid().setDensity(2500).setViscosity(1500).setLuminosity(5).setTemperature(2773).setUnlocalizedName("acid_fluid");
		FluidRegistry.registerFluid(acid_fluid);
		acid_block = new AcidBlock(acid_fluid, fluidacid.setReplaceable(), ModDamageSource.acid).setBlockName("acid_block").setResistance(500F);

		toxic_fluid = new ToxicFluid().setDensity(2500).setViscosity(2000).setLuminosity(15).setTemperature(2773).setUnlocalizedName("toxic_fluid");
		FluidRegistry.registerFluid(toxic_fluid);
		toxic_block = new ToxicBlock(toxic_fluid, fluidtoxic.setReplaceable(), ModDamageSource.radiation).setBlockName("toxic_block").setResistance(500F);

		schrabidic_fluid = new SchrabidicFluid().setDensity(31200).setViscosity(500).setTemperature(273).setUnlocalizedName("schrabidic_fluid");
		FluidRegistry.registerFluid(schrabidic_fluid);
		schrabidic_block = new SchrabidicBlock(schrabidic_fluid, fluidschrabidic.setReplaceable(), ModDamageSource.radiation).setBlockName("schrabidic_block").setResistance(500F);

		corium_fluid = new CoriumFluid().setDensity(600000).setViscosity(12000).setLuminosity(10).setTemperature(1500).setUnlocalizedName("corium_fluid");
		FluidRegistry.registerFluid(corium_fluid);
		corium_block = new CoriumFinite(corium_fluid, fluidcorium).setBlockName("corium_block").setResistance(500F);

		volcanic_lava_fluid = new VolcanicFluid().setLuminosity(15).setDensity(3000).setViscosity(3000).setTemperature(1300).setUnlocalizedName("volcanic_lava_fluid");
		FluidRegistry.registerFluid(volcanic_lava_fluid);
		volcanic_lava_block = new VolcanicBlock(volcanic_lava_fluid, Material.lava).setBlockName("volcanic_lava_block").setResistance(500F);

		rad_lava_fluid = new RadFluid().setLuminosity(15).setDensity(3000).setViscosity(3000).setTemperature(1300).setUnlocalizedName("rad_lava_fluid");
		FluidRegistry.registerFluid(rad_lava_fluid);
		rad_lava_block = new RadBlock(rad_lava_fluid, Material.lava).setBlockName("rad_lava_block").setResistance(500F);

		sulfuric_acid_fluid = new GenericFluid("sulfuric_acid_fluid").setDensity(1840).setViscosity(1000).setTemperature(273);
		FluidRegistry.registerFluid(sulfuric_acid_fluid);
		sulfuric_acid_block = new GenericFluidBlock(sulfuric_acid_fluid, Material.water, "sulfuric_acid_still", "sulfuric_acid_flowing").setDamage(ModDamageSource.acid, 5F).setBlockName("sulfuric_acid_block").setResistance(500F);

		Fluid liquidConcrete = new GenericFluid("concrete_liquid").setViscosity(2000);
		concrete_liquid = new GenericFiniteFluid(liquidConcrete, Material.rock, "concrete_liquid", "concrete_liquid_flowing").setQuantaPerBlock(4).setBlockName("concrete_liquid").setResistance(500F);

		dummy_block_vault = new DummyBlockVault(Material.iron).setBlockName("dummy_block_vault").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_blast = new DummyBlockBlast(Material.iron).setBlockName("dummy_block_blast").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_plate_compact_launcher = new DummyBlockMachine(Material.iron, compact_launcher, false).setBounds(0, 16, 0, 16, 16, 16).setBlockName("dummy_plate_compact_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_compact_launcher = new DummyBlockMachine(Material.iron, compact_launcher, true).setBlockName("dummy_port_compact_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_plate_launch_table = new DummyBlockMachine(Material.iron, launch_table, false).setBounds(0, 16, 0, 16, 16, 16).setBlockName("dummy_plate_launch_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_launch_table = new DummyBlockMachine(Material.iron, launch_table, true).setBlockName("dummy_port_launch_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_plate_cargo = new DummyBlockMachine(Material.iron, sat_dock, false).setBounds(0, 0, 0, 16, 8, 16).setBlockName("dummy_plate_cargo").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");

		ntm_dirt = new BlockNTMDirt().setBlockName("ntm_dirt").setHardness(0.5F).setStepSound(Block.soundTypeGravel).setCreativeTab(null).setBlockTextureName("dirt");

		pink_log = new BlockPinkLog().setBlockName("pink_log").setHardness(0.5F).setStepSound(Block.soundTypeWood).setCreativeTab(null);
		pink_planks = new BlockGeneric(Material.wood).setBlockName("pink_planks").setStepSound(Block.soundTypeWood).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pink_planks");
		pink_slab = new BlockPinkSlab(false, Material.wood).setBlockName("pink_slab").setStepSound(Block.soundTypeWood).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pink_planks");
		pink_double_slab = new BlockPinkSlab(true, Material.wood).setBlockName("pink_double_slab").setStepSound(Block.soundTypeWood).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pink_planks");
		pink_stairs = new BlockGenericStairs(pink_planks, 0).setBlockName("pink_stairs").setStepSound(Block.soundTypeWood).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":pink_planks");

		wand_air = new BlockWand(Blocks.air).setBlockName("wand_air").setBlockTextureName(RefStrings.MODID + ":wand_air");
		wand_loot = new BlockWandLoot().setBlockName("wand_loot").setBlockTextureName(RefStrings.MODID + ":wand_loot");
		wand_jigsaw = new BlockWandJigsaw().setBlockName("wand_jigsaw").setBlockTextureName(RefStrings.MODID + ":wand_jigsaw");
		wand_logic = new BlockWandLogic().setBlockName("wand_logic").setBlockTextureName(RefStrings.MODID + ":wand_logic");
		wand_tandem = new BlockWandTandem().setBlockName("wand_tandem").setBlockTextureName(RefStrings.MODID + ":wand_tandem");

		logic_block = new LogicBlock().setBlockName("logic_block").setBlockTextureName(RefStrings.MODID + ":logic_block");

	}

	private static void registerBlock() {
		//Test
		GameRegistry.registerBlock(event_tester, event_tester.getUnlocalizedName());
		GameRegistry.registerBlock(obj_tester, obj_tester.getUnlocalizedName());
		GameRegistry.registerBlock(test_core, test_core.getUnlocalizedName());
		GameRegistry.registerBlock(test_charge, test_charge.getUnlocalizedName());
		GameRegistry.registerBlock(structure_anchor, structure_anchor.getUnlocalizedName());

		//Ores
		GameRegistry.registerBlock(ore_uranium, ore_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_uranium_scorched, ore_uranium_scorched.getUnlocalizedName());
		GameRegistry.registerBlock(ore_thorium, ore_thorium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_titanium, ore_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_sulfur, ore_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ore_niter, ore_niter.getUnlocalizedName());
		GameRegistry.registerBlock(ore_copper, ore_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ore_tungsten, ore_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ore_aluminium, ore_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_fluorite, ore_fluorite.getUnlocalizedName());
		GameRegistry.registerBlock(ore_beryllium, ore_beryllium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_lead, ore_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ore_oil, ItemBlockLore.class, ore_oil.getUnlocalizedName());
		GameRegistry.registerBlock(ore_oil_empty, ore_oil_empty.getUnlocalizedName());
		GameRegistry.registerBlock(ore_oil_sand, ore_oil_sand.getUnlocalizedName());
		GameRegistry.registerBlock(ore_lignite, ore_lignite.getUnlocalizedName());
		GameRegistry.registerBlock(ore_asbestos, ore_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ore_schrabidium, ItemBlockLore.class, ore_schrabidium.getUnlocalizedName());

		//Rare Minerals
		GameRegistry.registerBlock(ore_australium, ItemOreBlock.class, ore_australium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_rare, ItemOreBlock.class, ore_rare.getUnlocalizedName());
		GameRegistry.registerBlock(ore_cobalt, ore_cobalt.getUnlocalizedName());
		GameRegistry.registerBlock(ore_cinnebar, ore_cinnebar.getUnlocalizedName());
		GameRegistry.registerBlock(ore_coltan, ore_coltan.getUnlocalizedName());

		//Stone clusters
		GameRegistry.registerBlock(cluster_iron, ItemBlockBase.class, cluster_iron.getUnlocalizedName());
		GameRegistry.registerBlock(cluster_titanium, ItemBlockBase.class, cluster_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(cluster_aluminium, ItemBlockBase.class, cluster_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(cluster_copper, ItemBlockBase.class, cluster_copper.getUnlocalizedName());

		//Bedrock ores
		GameRegistry.registerBlock(ore_bedrock_oil, ore_bedrock_oil.getUnlocalizedName());

		//Nice Meme
		GameRegistry.registerBlock(ore_coal_oil, ore_coal_oil.getUnlocalizedName());
		GameRegistry.registerBlock(ore_coal_oil_burning, ore_coal_oil_burning.getUnlocalizedName());

		//Nether Ores
		GameRegistry.registerBlock(ore_nether_coal, ore_nether_coal.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_smoldering, ore_nether_smoldering.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_uranium, ore_nether_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_uranium_scorched, ore_nether_uranium_scorched.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_plutonium, ore_nether_plutonium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_tungsten, ore_nether_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_sulfur, ore_nether_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_fire, ore_nether_fire.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_cobalt, ore_nether_cobalt.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_schrabidium, ItemBlockLore.class, ore_nether_schrabidium.getUnlocalizedName());

		//Meteor Ores
		register(ore_meteor);

		//Gneiss Ores
		GameRegistry.registerBlock(ore_gneiss_iron, ore_gneiss_iron.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_gold, ore_gneiss_gold.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_uranium, ore_gneiss_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_uranium_scorched, ore_gneiss_uranium_scorched.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_copper, ore_gneiss_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_asbestos, ore_gneiss_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_lithium, ore_gneiss_lithium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_schrabidium, ItemBlockLore.class, ore_gneiss_schrabidium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_rare, ItemOreBlock.class, ore_gneiss_rare.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gneiss_gas, ore_gneiss_gas.getUnlocalizedName());

		//Depth ores
		GameRegistry.registerBlock(ore_depth_cinnebar, ItemBlockBase.class, ore_depth_cinnebar.getUnlocalizedName());
		GameRegistry.registerBlock(ore_depth_zirconium, ItemBlockBase.class, ore_depth_zirconium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_depth_borax, ItemBlockBase.class, ore_depth_borax.getUnlocalizedName());
		GameRegistry.registerBlock(cluster_depth_iron, ItemBlockBase.class, cluster_depth_iron.getUnlocalizedName());
		GameRegistry.registerBlock(cluster_depth_titanium, ItemBlockBase.class, cluster_depth_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(cluster_depth_tungsten, ItemBlockBase.class, cluster_depth_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ore_alexandrite, ItemBlockBase.class, ore_alexandrite.getUnlocalizedName());

		//Nether depth ores
		GameRegistry.registerBlock(ore_depth_nether_neodymium, ItemBlockBase.class, ore_depth_nether_neodymium.getUnlocalizedName());

		//Basalt ores
		register(ore_basalt);

		//End Ores
		GameRegistry.registerBlock(ore_tikite, ore_tikite.getUnlocalizedName());

		//Bedrock ore
		register(ore_bedrock);
		register(ore_volcano);

		//Secret
		register(stone_keyhole);
		register(stone_keyhole_meta);

		//Resource-bearing Stones
		register(stone_resource);
		register(stalagmite);
		register(stalactite);
		register(stone_biome);

		//Stone Variants
		GameRegistry.registerBlock(stone_porous, stone_porous.getUnlocalizedName());
		GameRegistry.registerBlock(stone_gneiss, stone_gneiss.getUnlocalizedName());
		GameRegistry.registerBlock(gneiss_brick, gneiss_brick.getUnlocalizedName());
		GameRegistry.registerBlock(gneiss_tile, gneiss_tile.getUnlocalizedName());
		GameRegistry.registerBlock(gneiss_chiseled, gneiss_chiseled.getUnlocalizedName());
		GameRegistry.registerBlock(stone_depth, ItemBlockBase.class, stone_depth.getUnlocalizedName());
		GameRegistry.registerBlock(depth_brick, ItemBlockBase.class, depth_brick.getUnlocalizedName());
		GameRegistry.registerBlock(depth_tiles, ItemBlockBase.class, depth_tiles.getUnlocalizedName());
		GameRegistry.registerBlock(stone_depth_nether, ItemBlockBase.class, stone_depth_nether.getUnlocalizedName());
		GameRegistry.registerBlock(depth_nether_brick, ItemBlockBase.class, depth_nether_brick.getUnlocalizedName());
		GameRegistry.registerBlock(depth_nether_tiles, ItemBlockBase.class, depth_nether_tiles.getUnlocalizedName());
		GameRegistry.registerBlock(depth_dnt, ItemBlockBase.class, depth_dnt.getUnlocalizedName());
		GameRegistry.registerBlock(basalt, basalt.getUnlocalizedName());
		GameRegistry.registerBlock(basalt_smooth, basalt_smooth.getUnlocalizedName());
		GameRegistry.registerBlock(basalt_brick, basalt_brick.getUnlocalizedName());
		GameRegistry.registerBlock(basalt_polished, basalt_polished.getUnlocalizedName());
		GameRegistry.registerBlock(basalt_tiles, basalt_tiles.getUnlocalizedName());
		//GameRegistry.registerBlock(stone_deep_cobble, ItemBlockBase.class, stone_deep_cobble.getUnlocalizedName());

		//Blocks
		GameRegistry.registerBlock(block_uranium, block_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(block_u233, block_u233.getUnlocalizedName());
		GameRegistry.registerBlock(block_u235, block_u235.getUnlocalizedName());
		GameRegistry.registerBlock(block_u238, block_u238.getUnlocalizedName());
		GameRegistry.registerBlock(block_uranium_fuel, block_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(block_neptunium, block_neptunium.getUnlocalizedName());
		GameRegistry.registerBlock(block_polonium, block_polonium.getUnlocalizedName());
		GameRegistry.registerBlock(block_mox_fuel, block_mox_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(block_plutonium, block_plutonium.getUnlocalizedName());
		GameRegistry.registerBlock(block_pu238, block_pu238.getUnlocalizedName());
		GameRegistry.registerBlock(block_pu239, block_pu239.getUnlocalizedName());
		GameRegistry.registerBlock(block_pu240, block_pu240.getUnlocalizedName());
		GameRegistry.registerBlock(block_pu_mix, block_pu_mix.getUnlocalizedName());
		GameRegistry.registerBlock(block_plutonium_fuel, block_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(block_thorium, block_thorium.getUnlocalizedName());
		GameRegistry.registerBlock(block_thorium_fuel, block_thorium_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(block_titanium, block_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(block_sulfur, block_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(block_niter, block_niter.getUnlocalizedName());
		GameRegistry.registerBlock(block_copper, block_copper.getUnlocalizedName());
		GameRegistry.registerBlock(block_red_copper, block_red_copper.getUnlocalizedName());
		GameRegistry.registerBlock(block_advanced_alloy, block_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerBlock(block_tungsten, block_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(block_aluminium, block_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(block_fluorite, block_fluorite.getUnlocalizedName());
		GameRegistry.registerBlock(block_beryllium, block_beryllium.getUnlocalizedName());
		GameRegistry.registerBlock(block_cobalt, block_cobalt.getUnlocalizedName());
		GameRegistry.registerBlock(block_steel, block_steel.getUnlocalizedName());
		GameRegistry.registerBlock(block_tcalloy, block_tcalloy.getUnlocalizedName());
		GameRegistry.registerBlock(block_cdalloy, block_cdalloy.getUnlocalizedName());
		GameRegistry.registerBlock(block_lead, block_lead.getUnlocalizedName());
		GameRegistry.registerBlock(block_bismuth, block_bismuth.getUnlocalizedName());
		GameRegistry.registerBlock(block_cadmium, block_cadmium.getUnlocalizedName());
		GameRegistry.registerBlock(block_coltan, block_coltan.getUnlocalizedName());
		GameRegistry.registerBlock(block_tantalium, block_tantalium.getUnlocalizedName());
		GameRegistry.registerBlock(block_niobium, block_niobium.getUnlocalizedName());
		GameRegistry.registerBlock(block_lithium, block_lithium.getUnlocalizedName());
		GameRegistry.registerBlock(block_zirconium, block_zirconium.getUnlocalizedName());
		GameRegistry.registerBlock(block_white_phosphorus, block_white_phosphorus.getUnlocalizedName());
		GameRegistry.registerBlock(block_red_phosphorus, block_red_phosphorus.getUnlocalizedName());
		GameRegistry.registerBlock(block_yellowcake, block_yellowcake.getUnlocalizedName());
		GameRegistry.registerBlock(block_scrap, block_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(block_electrical_scrap, block_electrical_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(block_fallout, block_fallout.getUnlocalizedName());
		GameRegistry.registerBlock(block_foam, block_foam.getUnlocalizedName());
		GameRegistry.registerBlock(block_graphite, block_graphite.getUnlocalizedName());
		register(block_coke);
		GameRegistry.registerBlock(block_graphite_drilled, block_graphite_drilled.getUnlocalizedName());
		GameRegistry.registerBlock(block_graphite_fuel, block_graphite_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(block_graphite_rod, block_graphite_rod.getUnlocalizedName());
		GameRegistry.registerBlock(block_graphite_plutonium, block_graphite_plutonium.getUnlocalizedName());
		GameRegistry.registerBlock(block_graphite_source, block_graphite_source.getUnlocalizedName());
		GameRegistry.registerBlock(block_graphite_lithium, block_graphite_lithium.getUnlocalizedName());
		GameRegistry.registerBlock(block_graphite_tritium, block_graphite_tritium.getUnlocalizedName());
		GameRegistry.registerBlock(block_graphite_detector, block_graphite_detector.getUnlocalizedName());
		GameRegistry.registerBlock(block_boron, block_boron.getUnlocalizedName());
		GameRegistry.registerBlock(block_insulator, block_insulator.getUnlocalizedName());
		GameRegistry.registerBlock(block_fiberglass, block_fiberglass.getUnlocalizedName());
		GameRegistry.registerBlock(block_asbestos, block_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(block_trinitite, block_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(block_waste, block_waste.getUnlocalizedName());
		GameRegistry.registerBlock(block_waste_painted, block_waste_painted.getUnlocalizedName());
		GameRegistry.registerBlock(block_waste_vitrified, block_waste_vitrified.getUnlocalizedName());
		GameRegistry.registerBlock(ancient_scrap, ancient_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(block_corium, block_corium.getUnlocalizedName());
		GameRegistry.registerBlock(block_corium_cobble, block_corium_cobble.getUnlocalizedName());
		GameRegistry.registerBlock(block_schraranium, ItemBlockBase.class, block_schraranium.getUnlocalizedName());
		GameRegistry.registerBlock(block_schrabidium, ItemBlockBase.class, block_schrabidium.getUnlocalizedName());
		GameRegistry.registerBlock(block_schrabidate, ItemBlockBase.class, block_schrabidate.getUnlocalizedName());
		GameRegistry.registerBlock(block_solinium, ItemBlockBase.class, block_solinium.getUnlocalizedName());
		GameRegistry.registerBlock(block_schrabidium_fuel, ItemBlockBase.class, block_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerBlock(block_euphemium, ItemBlockLore.class, block_euphemium.getUnlocalizedName());
		GameRegistry.registerBlock(block_schrabidium_cluster, ItemBlockBase.class, block_schrabidium_cluster.getUnlocalizedName());
		GameRegistry.registerBlock(block_euphemium_cluster, ItemBlockLore.class, block_euphemium_cluster.getUnlocalizedName());
		GameRegistry.registerBlock(block_dineutronium, block_dineutronium.getUnlocalizedName());
		GameRegistry.registerBlock(block_magnetized_tungsten, block_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(block_combine_steel, block_combine_steel.getUnlocalizedName());
		GameRegistry.registerBlock(block_desh, block_desh.getUnlocalizedName());
		GameRegistry.registerBlock(block_dura_steel, block_dura_steel.getUnlocalizedName());
		GameRegistry.registerBlock(block_starmetal, block_starmetal.getUnlocalizedName());
		GameRegistry.registerBlock(block_polymer, block_polymer.getUnlocalizedName());
		GameRegistry.registerBlock(block_bakelite, block_bakelite.getUnlocalizedName());
		GameRegistry.registerBlock(block_rubber, block_rubber.getUnlocalizedName());
		GameRegistry.registerBlock(block_australium, ItemOreBlock.class, block_australium.getUnlocalizedName());
		GameRegistry.registerBlock(block_weidanium, ItemOreBlock.class, block_weidanium.getUnlocalizedName());
		GameRegistry.registerBlock(block_reiium, ItemOreBlock.class, block_reiium.getUnlocalizedName());
		GameRegistry.registerBlock(block_unobtainium, ItemOreBlock.class, block_unobtainium.getUnlocalizedName());
		GameRegistry.registerBlock(block_daffergon, ItemOreBlock.class, block_daffergon.getUnlocalizedName());
		GameRegistry.registerBlock(block_verticium, ItemOreBlock.class, block_verticium.getUnlocalizedName());
		register(block_cap);
		GameRegistry.registerBlock(block_lanthanium, block_lanthanium.getUnlocalizedName());
		GameRegistry.registerBlock(block_ra226, block_ra226.getUnlocalizedName());
		GameRegistry.registerBlock(block_actinium, block_actinium.getUnlocalizedName());
		GameRegistry.registerBlock(block_tritium, block_tritium.getUnlocalizedName());
		GameRegistry.registerBlock(block_semtex, block_semtex.getUnlocalizedName());
		GameRegistry.registerBlock(block_c4, block_c4.getUnlocalizedName());
		GameRegistry.registerBlock(block_smore, block_smore.getUnlocalizedName());
		GameRegistry.registerBlock(block_slag, block_slag.getUnlocalizedName());

		//Deco Blocks
		GameRegistry.registerBlock(deco_titanium, deco_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(deco_red_copper, deco_red_copper.getUnlocalizedName());
		GameRegistry.registerBlock(deco_tungsten, deco_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(deco_aluminium, deco_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(deco_steel, deco_steel.getUnlocalizedName());
		GameRegistry.registerBlock(deco_rusty_steel, deco_rusty_steel.getUnlocalizedName());
		GameRegistry.registerBlock(deco_lead, deco_lead.getUnlocalizedName());
		GameRegistry.registerBlock(deco_beryllium, deco_beryllium.getUnlocalizedName());
		GameRegistry.registerBlock(deco_asbestos, deco_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(deco_emitter, ItemBlockBase.class, deco_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(part_emitter, ItemBlockBase.class, part_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(deco_loot, deco_loot.getUnlocalizedName());
		GameRegistry.registerBlock(pedestal, pedestal.getUnlocalizedName());
		register(skeleton_holder);
		register(dungeon_spawner);
		GameRegistry.registerBlock(bobblehead, ItemBlockMeta.class, bobblehead.getUnlocalizedName());
		GameRegistry.registerBlock(snowglobe, ItemBlockMeta.class, snowglobe.getUnlocalizedName());
		GameRegistry.registerBlock(plushie, ItemBlockBase.class, plushie.getUnlocalizedName());
		GameRegistry.registerBlock(deco_rbmk, deco_rbmk.getUnlocalizedName());
		GameRegistry.registerBlock(deco_rbmk_smooth, deco_rbmk_smooth.getUnlocalizedName());

		//Gravel
		GameRegistry.registerBlock(gravel_obsidian, ItemBlockBlastInfo.class, gravel_obsidian.getUnlocalizedName());
		GameRegistry.registerBlock(gravel_diamond, ItemBlockLore.class, gravel_diamond.getUnlocalizedName());

		//Lamps
		GameRegistry.registerBlock(lamp_tritium_green_off, lamp_tritium_green_off.getUnlocalizedName());
		GameRegistry.registerBlock(lamp_tritium_green_on, lamp_tritium_green_on.getUnlocalizedName());
		GameRegistry.registerBlock(lamp_tritium_blue_off, lamp_tritium_blue_off.getUnlocalizedName());
		GameRegistry.registerBlock(lamp_tritium_blue_on, lamp_tritium_blue_on.getUnlocalizedName());
		GameRegistry.registerBlock(lamp_demon, lamp_demon.getUnlocalizedName());
		GameRegistry.registerBlock(lantern, lantern.getUnlocalizedName());
		GameRegistry.registerBlock(lantern_behemoth, lantern_behemoth.getUnlocalizedName());
		GameRegistry.registerBlock(spotlight_incandescent, spotlight_incandescent.getUnlocalizedName());
		GameRegistry.registerBlock(spotlight_incandescent_off, spotlight_incandescent_off.getUnlocalizedName());
		GameRegistry.registerBlock(spotlight_fluoro, spotlight_fluoro.getUnlocalizedName());
		GameRegistry.registerBlock(spotlight_fluoro_off, spotlight_fluoro_off.getUnlocalizedName());
		GameRegistry.registerBlock(spotlight_halogen, spotlight_halogen.getUnlocalizedName());
		GameRegistry.registerBlock(spotlight_halogen_off, spotlight_halogen_off.getUnlocalizedName());
		GameRegistry.registerBlock(spotlight_beam, spotlight_beam.getUnlocalizedName());
		register(floodlight);
		GameRegistry.registerBlock(floodlight_beam, floodlight_beam.getUnlocalizedName());

		//Reinforced Blocks
		register(sandbags);
		register(wood_barrier);
		register(wood_structure);
		GameRegistry.registerBlock(asphalt, ItemBlockBlastInfo.class, asphalt.getUnlocalizedName());
		GameRegistry.registerBlock(asphalt_light, ItemBlockBlastInfo.class, asphalt_light.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_brick, ItemBlockBlastInfo.class, reinforced_brick.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_glass, ItemBlockBlastInfo.class, reinforced_glass.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_glass_pane, ItemBlockBlastInfo.class, reinforced_glass_pane.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_light, ItemBlockBlastInfo.class, reinforced_light.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_sand, ItemBlockBlastInfo.class, reinforced_sand.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_lamp_off, ItemBlockBlastInfo.class, reinforced_lamp_off.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_lamp_on, ItemBlockBlastInfo.class, reinforced_lamp_on.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_laminate, ItemBlockBlastInfo.class, reinforced_laminate.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_laminate_pane,ItemBlockBlastInfo.class, reinforced_laminate_pane.getUnlocalizedName());

		//Bricks
		register(rebar);
		GameRegistry.registerBlock(reinforced_stone, ItemBlockBlastInfo.class, reinforced_stone.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_ducrete, ItemBlockBlastInfo.class, reinforced_ducrete.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_smooth, ItemBlockBlastInfo.class, concrete_smooth.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_colored, ItemBlockColoredConcrete.class, concrete_colored.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_colored_ext, ItemBlockBlastInfo.class, concrete_colored_ext.getUnlocalizedName());
		GameRegistry.registerBlock(concrete, ItemBlockBlastInfo.class, concrete.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_asbestos, ItemBlockBlastInfo.class, concrete_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_rebar, ItemBlockBlastInfo.class, concrete_rebar.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_super, ItemBlockBlastInfo.class, concrete_super.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_super_broken, ItemBlockBlastInfo.class, concrete_super_broken.getUnlocalizedName());
		GameRegistry.registerBlock(ducrete_smooth, ItemBlockBlastInfo.class, ducrete_smooth.getUnlocalizedName());
		GameRegistry.registerBlock(ducrete, ItemBlockBlastInfo.class, ducrete.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_pillar, ItemBlockBlastInfo.class, concrete_pillar.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete, ItemBlockBlastInfo.class, brick_concrete.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete_mossy, ItemBlockBlastInfo.class, brick_concrete_mossy.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete_cracked, ItemBlockBlastInfo.class, brick_concrete_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete_broken, ItemBlockBlastInfo.class, brick_concrete_broken.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete_marked, ItemBlockBlastInfo.class, brick_concrete_marked.getUnlocalizedName());
		GameRegistry.registerBlock(brick_ducrete, ItemBlockBlastInfo.class, brick_ducrete.getUnlocalizedName());
		GameRegistry.registerBlock(brick_obsidian, ItemBlockBlastInfo.class, brick_obsidian.getUnlocalizedName());
		GameRegistry.registerBlock(brick_compound, ItemBlockBlastInfo.class, brick_compound.getUnlocalizedName());
		GameRegistry.registerBlock(brick_light, ItemBlockBlastInfo.class, brick_light.getUnlocalizedName());
		GameRegistry.registerBlock(brick_asbestos, brick_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(brick_fire, ItemBlockBlastInfo.class, brick_fire.getUnlocalizedName());

		//Lightstone and its stair/slab variants
		register(lightstone);
		register(lightstone_tile_stairs);
		register(lightstone_bricks_stairs);
		register(stones_slab, ItemModSlab.class);
		register(stones_double_slab, ItemModSlab.class);

		GameRegistry.registerBlock(concrete_slab, ItemModSlab.class, concrete_slab.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_double_slab, ItemModSlab.class, concrete_double_slab.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_brick_slab, ItemModSlab.class, concrete_brick_slab.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_brick_double_slab, ItemModSlab.class, concrete_brick_double_slab.getUnlocalizedName());
		GameRegistry.registerBlock(brick_slab, ItemModSlab.class, brick_slab.getUnlocalizedName());
		GameRegistry.registerBlock(brick_double_slab, ItemModSlab.class, brick_double_slab.getUnlocalizedName());

		GameRegistry.registerBlock(concrete_smooth_stairs, concrete_smooth_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_stairs, concrete_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(concrete_asbestos_stairs, concrete_asbestos_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ducrete_smooth_stairs, ducrete_smooth_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete_stairs, brick_concrete_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete_mossy_stairs, brick_concrete_mossy_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete_cracked_stairs, brick_concrete_cracked_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_concrete_broken_stairs, brick_concrete_broken_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_ducrete_stairs, brick_ducrete_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_stone_stairs, reinforced_stone_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_brick_stairs, reinforced_brick_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_obsidian_stairs, brick_obsidian_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_light_stairs, brick_light_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_compound_stairs, brick_compound_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_asbestos_stairs, brick_asbestos_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(brick_fire_stairs, brick_fire_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(ducrete_stairs, ducrete_stairs.getUnlocalizedName());
		GameRegistry.registerBlock(asphalt_stairs, asphalt_stairs.getUnlocalizedName());

		//CMB Building Elements
		GameRegistry.registerBlock(cmb_brick, ItemBlockBlastInfo.class, cmb_brick.getUnlocalizedName());
		GameRegistry.registerBlock(cmb_brick_reinforced, ItemBlockBlastInfo.class, cmb_brick_reinforced.getUnlocalizedName());

		//Tiles
		GameRegistry.registerBlock(vinyl_tile, ItemBlockBlastInfo.class, vinyl_tile.getUnlocalizedName()); //i would rather die than dip into fucking blocks with subtypes again

		GameRegistry.registerBlock(tile_lab, tile_lab.getUnlocalizedName());
		GameRegistry.registerBlock(tile_lab_cracked, tile_lab_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(tile_lab_broken, tile_lab_broken.getUnlocalizedName());

		//Other defensive stuff
		GameRegistry.registerBlock(barbed_wire, barbed_wire.getUnlocalizedName());
		GameRegistry.registerBlock(barbed_wire_fire, barbed_wire_fire.getUnlocalizedName());
		GameRegistry.registerBlock(barbed_wire_poison, barbed_wire_poison.getUnlocalizedName());
		GameRegistry.registerBlock(barbed_wire_acid, barbed_wire_acid.getUnlocalizedName());
		GameRegistry.registerBlock(barbed_wire_wither, barbed_wire_wither.getUnlocalizedName());
		GameRegistry.registerBlock(barbed_wire_ultradeath, barbed_wire_ultradeath.getUnlocalizedName());
		GameRegistry.registerBlock(spikes, spikes.getUnlocalizedName());
		GameRegistry.registerBlock(tesla, tesla.getUnlocalizedName());

		//Charger
		GameRegistry.registerBlock(charger, charger.getUnlocalizedName());
		GameRegistry.registerBlock(refueler, refueler.getUnlocalizedName());
		//GameRegistry.registerBlock(floodlight, floodlight.getUnlocalizedName());

		//Decoration Blocks
		GameRegistry.registerBlock(block_meteor, block_meteor.getUnlocalizedName());
		GameRegistry.registerBlock(block_meteor_cobble, block_meteor_cobble.getUnlocalizedName());
		GameRegistry.registerBlock(block_meteor_broken, block_meteor_broken.getUnlocalizedName());
		GameRegistry.registerBlock(block_meteor_molten, block_meteor_molten.getUnlocalizedName());
		GameRegistry.registerBlock(block_meteor_treasure, block_meteor_treasure.getUnlocalizedName());
		GameRegistry.registerBlock(meteor_polished, meteor_polished.getUnlocalizedName());
		GameRegistry.registerBlock(meteor_brick, meteor_brick.getUnlocalizedName());
		GameRegistry.registerBlock(meteor_brick_mossy, meteor_brick_mossy.getUnlocalizedName());
		GameRegistry.registerBlock(meteor_brick_cracked, meteor_brick_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(meteor_brick_chiseled, meteor_brick_chiseled.getUnlocalizedName());
		GameRegistry.registerBlock(meteor_pillar, meteor_pillar.getUnlocalizedName());
		GameRegistry.registerBlock(meteor_spawner, meteor_spawner.getUnlocalizedName());
		GameRegistry.registerBlock(meteor_battery, ItemBlockLore.class, meteor_battery.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle, brick_jungle.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle_cracked, brick_jungle_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle_fragile, brick_jungle_fragile.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle_lava, brick_jungle_lava.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle_ooze, brick_jungle_ooze.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle_mystic, brick_jungle_mystic.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle_trap, ItemTrapBlock.class, brick_jungle_trap.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle_glyph, ItemGlyphBlock.class, brick_jungle_glyph.getUnlocalizedName());
		GameRegistry.registerBlock(brick_jungle_circle, brick_jungle_circle.getUnlocalizedName());
		GameRegistry.registerBlock(brick_red, brick_red.getUnlocalizedName());
		register(deco_computer);
		register(deco_crt);
		register(deco_toaster);
		GameRegistry.registerBlock(filing_cabinet, ItemBlockBase.class, filing_cabinet.getUnlocalizedName());
		GameRegistry.registerBlock(tape_recorder, tape_recorder.getUnlocalizedName());
		GameRegistry.registerBlock(steel_poles, steel_poles.getUnlocalizedName());
		GameRegistry.registerBlock(pole_top, pole_top.getUnlocalizedName());
		GameRegistry.registerBlock(pole_satellite_receiver, pole_satellite_receiver.getUnlocalizedName());
		GameRegistry.registerBlock(steel_wall, steel_wall.getUnlocalizedName());
		GameRegistry.registerBlock(steel_corner, steel_corner.getUnlocalizedName());
		GameRegistry.registerBlock(steel_roof, steel_roof.getUnlocalizedName());
		GameRegistry.registerBlock(steel_beam, steel_beam.getUnlocalizedName());
		register(steel_scaffold);
		GameRegistry.registerBlock(steel_grate, steel_grate.getUnlocalizedName());
		register(steel_grate_wide);
		//register(scaffold_dynamic);
		GameRegistry.registerBlock(deco_pipe, ItemBlockBase.class, deco_pipe.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_rusted, ItemBlockBase.class, deco_pipe_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_green, ItemBlockBase.class, deco_pipe_green.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_green_rusted, ItemBlockBase.class, deco_pipe_green_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_red, ItemBlockBase.class, deco_pipe_red.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_marked, ItemBlockBase.class, deco_pipe_marked.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_rim, ItemBlockBase.class, deco_pipe_rim.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_rim_rusted, ItemBlockBase.class, deco_pipe_rim_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_rim_green, ItemBlockBase.class, deco_pipe_rim_green.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_rim_green_rusted, ItemBlockBase.class, deco_pipe_rim_green_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_rim_red, ItemBlockBase.class, deco_pipe_rim_red.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_rim_marked, ItemBlockBase.class, deco_pipe_rim_marked.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_framed, ItemBlockBase.class, deco_pipe_framed.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_framed_rusted, ItemBlockBase.class, deco_pipe_framed_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_framed_green, ItemBlockBase.class, deco_pipe_framed_green.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_framed_green_rusted, ItemBlockBase.class, deco_pipe_framed_green_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_framed_red, ItemBlockBase.class, deco_pipe_framed_red.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_framed_marked, ItemBlockBase.class, deco_pipe_framed_marked.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_quad, ItemBlockBase.class, deco_pipe_quad.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_quad_rusted, ItemBlockBase.class, deco_pipe_quad_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_quad_green, ItemBlockBase.class, deco_pipe_quad_green.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_quad_green_rusted, ItemBlockBase.class, deco_pipe_quad_green_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_quad_red, ItemBlockBase.class, deco_pipe_quad_red.getUnlocalizedName());
		GameRegistry.registerBlock(deco_pipe_quad_marked, ItemBlockBase.class, deco_pipe_quad_marked.getUnlocalizedName());
		register(plant_flower);
		register(plant_tall);
		register(plant_dead);
		register(reeds);
		register(vine_phosphor);
		GameRegistry.registerBlock(mush, mush.getUnlocalizedName());
		GameRegistry.registerBlock(mush_block, mush_block.getUnlocalizedName());
		GameRegistry.registerBlock(mush_block_stem, mush_block_stem.getUnlocalizedName());
		register(glyphid_base);
		register(glyphid_spawner);
		GameRegistry.registerBlock(moon_turf, moon_turf.getUnlocalizedName());

		//Waste
		GameRegistry.registerBlock(waste_earth, waste_earth.getUnlocalizedName());
		GameRegistry.registerBlock(waste_mycelium, waste_mycelium.getUnlocalizedName());
		GameRegistry.registerBlock(waste_trinitite, waste_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(waste_trinitite_red, waste_trinitite_red.getUnlocalizedName());
		GameRegistry.registerBlock(waste_log, waste_log.getUnlocalizedName());
		GameRegistry.registerBlock(waste_leaves, waste_leaves.getUnlocalizedName());
		GameRegistry.registerBlock(waste_planks, waste_planks.getUnlocalizedName());
		GameRegistry.registerBlock(frozen_grass, frozen_grass.getUnlocalizedName());
		GameRegistry.registerBlock(frozen_dirt, frozen_dirt.getUnlocalizedName());
		GameRegistry.registerBlock(frozen_log, frozen_log.getUnlocalizedName());
		GameRegistry.registerBlock(frozen_planks, frozen_planks.getUnlocalizedName());
		GameRegistry.registerBlock(dirt_dead, dirt_dead.getUnlocalizedName());
		GameRegistry.registerBlock(dirt_oily, dirt_oily.getUnlocalizedName());
		GameRegistry.registerBlock(sand_dirty, sand_dirty.getUnlocalizedName());
		GameRegistry.registerBlock(sand_dirty_red, sand_dirty_red.getUnlocalizedName());
		GameRegistry.registerBlock(stone_cracked, stone_cracked.getUnlocalizedName());
		GameRegistry.registerBlock(fallout, fallout.getUnlocalizedName());
		GameRegistry.registerBlock(foam_layer, foam_layer.getUnlocalizedName());
		GameRegistry.registerBlock(sand_boron_layer, sand_boron_layer.getUnlocalizedName());
		GameRegistry.registerBlock(leaves_layer, leaves_layer.getUnlocalizedName());
		GameRegistry.registerBlock(oil_spill, oil_spill.getUnlocalizedName());
		GameRegistry.registerBlock(burning_earth, burning_earth.getUnlocalizedName());
		GameRegistry.registerBlock(tektite, tektite.getUnlocalizedName());
		GameRegistry.registerBlock(ore_tektite_osmiridium, ore_tektite_osmiridium.getUnlocalizedName());
		GameRegistry.registerBlock(impact_dirt, impact_dirt.getUnlocalizedName());

		//RAD
		register(sellafield_slaked);
		register(sellafield_bedrock);
		register(ore_sellafield_diamond);
		register(ore_sellafield_emerald);
		register(ore_sellafield_uranium_scorched);
		register(ore_sellafield_schrabidium);
		register(ore_sellafield_radgem);
		GameRegistry.registerBlock(sellafield, ItemBlockNamedMeta.class, sellafield.getUnlocalizedName());

		//Geysirs
		GameRegistry.registerBlock(geysir_water, geysir_water.getUnlocalizedName());
		GameRegistry.registerBlock(geysir_chlorine, geysir_chlorine.getUnlocalizedName());
		GameRegistry.registerBlock(geysir_vapor, geysir_vapor.getUnlocalizedName());
		GameRegistry.registerBlock(geysir_nether, geysir_nether.getUnlocalizedName());

		//Nukes
		GameRegistry.registerBlock(nuke_gadget, nuke_gadget.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_boy, nuke_boy.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_man, nuke_man.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_mike, nuke_mike.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_tsar, nuke_tsar.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_prototype, ItemPrototypeBlock.class, nuke_prototype.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_fleija, nuke_fleija.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_solinium, nuke_solinium.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_n2, nuke_n2.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_fstbmb, nuke_fstbmb.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_custom, nuke_custom.getUnlocalizedName());

		//Generic Bombs
		GameRegistry.registerBlock(bomb_multi, bomb_multi.getUnlocalizedName());
		register(crashed_balefire);
		GameRegistry.registerBlock(fireworks, fireworks.getUnlocalizedName());
		GameRegistry.registerBlock(dynamite, dynamite.getUnlocalizedName());
		GameRegistry.registerBlock(tnt, tnt.getUnlocalizedName());
		GameRegistry.registerBlock(semtex, semtex.getUnlocalizedName());
		GameRegistry.registerBlock(c4, c4.getUnlocalizedName());
		register(fissure_bomb);

		//Turrets
		GameRegistry.registerBlock(turret_chekhov, turret_chekhov.getUnlocalizedName());
		GameRegistry.registerBlock(turret_friendly, turret_friendly.getUnlocalizedName());
		GameRegistry.registerBlock(turret_jeremy, turret_jeremy.getUnlocalizedName());
		GameRegistry.registerBlock(turret_tauon, turret_tauon.getUnlocalizedName());
		GameRegistry.registerBlock(turret_richard, turret_richard.getUnlocalizedName());
		GameRegistry.registerBlock(turret_howard, turret_howard.getUnlocalizedName());
		GameRegistry.registerBlock(turret_howard_damaged, turret_howard_damaged.getUnlocalizedName());
		GameRegistry.registerBlock(turret_maxwell, turret_maxwell.getUnlocalizedName());
		GameRegistry.registerBlock(turret_fritz, turret_fritz.getUnlocalizedName());
		//GameRegistry.registerBlock(turret_brandon, turret_brandon.getUnlocalizedName());
		GameRegistry.registerBlock(turret_arty, turret_arty.getUnlocalizedName());
		GameRegistry.registerBlock(turret_himars, turret_himars.getUnlocalizedName());
		GameRegistry.registerBlock(turret_sentry, turret_sentry.getUnlocalizedName());
		GameRegistry.registerBlock(turret_sentry_damaged, turret_sentry_damaged.getUnlocalizedName());

		//Wall-mounted Explosives
		GameRegistry.registerBlock(charge_dynamite, ItemBlockBase.class, charge_dynamite.getUnlocalizedName());
		GameRegistry.registerBlock(charge_miner, ItemBlockBase.class, charge_miner.getUnlocalizedName());
		GameRegistry.registerBlock(charge_c4, ItemBlockBase.class, charge_c4.getUnlocalizedName());
		GameRegistry.registerBlock(charge_semtex, ItemBlockBase.class, charge_semtex.getUnlocalizedName());

		//Mines
		GameRegistry.registerBlock(mine_ap, mine_ap.getUnlocalizedName());
		GameRegistry.registerBlock(mine_shrap, mine_shrap.getUnlocalizedName());
		GameRegistry.registerBlock(mine_he, mine_he.getUnlocalizedName());
		GameRegistry.registerBlock(mine_fat, mine_fat.getUnlocalizedName());
		GameRegistry.registerBlock(mine_naval, mine_naval.getUnlocalizedName());

		//Block Bombs
		GameRegistry.registerBlock(flame_war, flame_war.getUnlocalizedName());
		GameRegistry.registerBlock(float_bomb, float_bomb.getUnlocalizedName());
		GameRegistry.registerBlock(therm_endo, therm_endo.getUnlocalizedName());
		GameRegistry.registerBlock(therm_exo, therm_exo.getUnlocalizedName());
		GameRegistry.registerBlock(emp_bomb, emp_bomb.getUnlocalizedName());
		GameRegistry.registerBlock(det_cord, det_cord.getUnlocalizedName());
		GameRegistry.registerBlock(det_charge, det_charge.getUnlocalizedName());
		GameRegistry.registerBlock(det_nuke, det_nuke.getUnlocalizedName());
		GameRegistry.registerBlock(det_miner, det_miner.getUnlocalizedName());
		GameRegistry.registerBlock(red_barrel, ItemBlockLore.class, red_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(pink_barrel, ItemBlockLore.class, pink_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(lox_barrel, ItemBlockLore.class, lox_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(taint_barrel, taint_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(yellow_barrel, yellow_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(vitrified_barrel, vitrified_barrel.getUnlocalizedName());

		//Siren
		GameRegistry.registerBlock(machine_siren, machine_siren.getUnlocalizedName());

		//This Thing
		GameRegistry.registerBlock(broadcaster_pc, broadcaster_pc.getUnlocalizedName());

		//Geiger Counter
		GameRegistry.registerBlock(geiger, geiger.getUnlocalizedName());

		//HEV Battery
		GameRegistry.registerBlock(hev_battery, hev_battery.getUnlocalizedName());

		//Chainlink Fence
		GameRegistry.registerBlock(fence_metal, ItemBlockBase.class, fence_metal.getUnlocalizedName());

		//Sands, Glass
		GameRegistry.registerBlock(ash_digamma, ash_digamma.getUnlocalizedName());
		GameRegistry.registerBlock(sand_boron, sand_boron.getUnlocalizedName());
		GameRegistry.registerBlock(sand_lead, sand_lead.getUnlocalizedName());
		GameRegistry.registerBlock(sand_uranium, sand_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(sand_polonium, sand_polonium.getUnlocalizedName());
		GameRegistry.registerBlock(sand_quartz, sand_quartz.getUnlocalizedName());
		GameRegistry.registerBlock(glass_boron, glass_boron.getUnlocalizedName());
		GameRegistry.registerBlock(glass_lead, glass_lead.getUnlocalizedName());
		GameRegistry.registerBlock(glass_uranium, glass_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(glass_trinitite, glass_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(glass_polonium, glass_polonium.getUnlocalizedName());
		GameRegistry.registerBlock(glass_ash, glass_ash.getUnlocalizedName());
		GameRegistry.registerBlock(glass_quartz, glass_quartz.getUnlocalizedName());
		GameRegistry.registerBlock(glass_polarized, glass_polarized.getUnlocalizedName());

		//Silo Hatch
		GameRegistry.registerBlock(seal_frame, seal_frame.getUnlocalizedName());
		GameRegistry.registerBlock(seal_controller, seal_controller.getUnlocalizedName());
		GameRegistry.registerBlock(seal_hatch, seal_hatch.getUnlocalizedName());

		//Vault Door
		GameRegistry.registerBlock(vault_door, vault_door.getUnlocalizedName());
		GameRegistry.registerBlock(blast_door, blast_door.getUnlocalizedName());
		GameRegistry.registerBlock(fire_door, fire_door.getUnlocalizedName());
		GameRegistry.registerBlock(transition_seal, transition_seal.getUnlocalizedName());
		GameRegistry.registerBlock(silo_hatch, silo_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(silo_hatch_large, silo_hatch_large.getUnlocalizedName());
		GameRegistry.registerBlock(sliding_blast_door, sliding_blast_door.getUnlocalizedName());

		//Doors
		GameRegistry.registerBlock(door_metal, door_metal.getUnlocalizedName());
		GameRegistry.registerBlock(door_office, door_office.getUnlocalizedName());
		GameRegistry.registerBlock(door_bunker, door_bunker.getUnlocalizedName());
		GameRegistry.registerBlock(door_red, door_red.getUnlocalizedName());
		GameRegistry.registerBlock(secure_access_door, secure_access_door.getUnlocalizedName());
		GameRegistry.registerBlock(large_vehicle_door, large_vehicle_door.getUnlocalizedName());
		GameRegistry.registerBlock(qe_containment, qe_containment.getUnlocalizedName());
		GameRegistry.registerBlock(qe_sliding_door, qe_sliding_door.getUnlocalizedName());
		GameRegistry.registerBlock(round_airlock_door, round_airlock_door.getUnlocalizedName());
		GameRegistry.registerBlock(sliding_seal_door, sliding_seal_door.getUnlocalizedName());
		GameRegistry.registerBlock(water_door, water_door.getUnlocalizedName());

		//Crates
		register(crate_iron, ItemBlockStorageCrate.class);
		register(crate_steel, ItemBlockStorageCrate.class);
		register(crate_desh, ItemBlockStorageCrate.class);
		register(crate_tungsten, ItemBlockStorageCrate.class);
		register(crate_template, ItemBlockStorageCrate.class);
		register(safe, ItemBlockStorageCrate.class);
		register(mass_storage, ItemBlockStorageCrate.class);

		//Junk
		GameRegistry.registerBlock(boxcar, boxcar.getUnlocalizedName());
		GameRegistry.registerBlock(boat, boat.getUnlocalizedName());

		//Machines
		register(machine_autocrafter);
		register(machine_funnel);

		register(anvil_iron);
		register(anvil_lead);
		register(anvil_steel);
		register(anvil_desh);
		register(anvil_ferrouranium);
		register(anvil_saturnite);
		register(anvil_bismuth_bronze);
		register(anvil_arsenic_bronze);
		register(anvil_schrabidate);
		register(anvil_dnt);
		register(anvil_osmiridium);
		register(anvil_murky);

		GameRegistry.registerBlock(press_preheater, press_preheater.getUnlocalizedName());
		GameRegistry.registerBlock(machine_press, machine_press.getUnlocalizedName());
		GameRegistry.registerBlock(machine_epress, machine_epress.getUnlocalizedName());
		register(machine_conveyor_press);
		register(machine_ammo_press);
		register(pump_steam);
		register(pump_electric);
		register(heater_firebox);
		register(heater_oven);
		register(machine_ashpit);
		register(heater_oilburner);
		register(heater_electric);
		register(heater_heatex);
		register(furnace_iron);
		register(furnace_steel);
		register(furnace_combination);
		register(machine_stirling);
		register(machine_stirling_steel);
		register(machine_stirling_creative);
		register(machine_sawmill);
		register(machine_crucible);
		register(machine_strand_caster);
		register(machine_boiler);
		register(machine_industrial_boiler);
		register(foundry_mold);
		register(foundry_basin);
		register(foundry_channel);
		register(foundry_tank);
		register(foundry_outlet);
		register(foundry_slagtap);
		register(slag);
		register(machine_furnace_brick_off);
		register(machine_furnace_brick_on);
		register(machine_difurnace_off);
		register(machine_difurnace_on);
		register(machine_difurnace_extension);
		GameRegistry.registerBlock(machine_difurnace_rtg_off, machine_difurnace_rtg_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_difurnace_rtg_on, machine_difurnace_rtg_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_centrifuge, machine_centrifuge.getUnlocalizedName());
		GameRegistry.registerBlock(machine_gascent, machine_gascent.getUnlocalizedName());
		GameRegistry.registerBlock(machine_fel, machine_fel.getUnlocalizedName());
		GameRegistry.registerBlock(machine_silex, machine_silex.getUnlocalizedName());
		register(machine_rotary_furnace);
		GameRegistry.registerBlock(machine_crystallizer, machine_crystallizer.getUnlocalizedName());
		GameRegistry.registerBlock(machine_uf6_tank, machine_uf6_tank.getUnlocalizedName());
		GameRegistry.registerBlock(machine_puf6_tank, machine_puf6_tank.getUnlocalizedName());
		GameRegistry.registerBlock(machine_reactor_breeding, machine_reactor_breeding.getUnlocalizedName());
		GameRegistry.registerBlock(machine_rtg_furnace_off, machine_rtg_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_rtg_furnace_on, machine_rtg_furnace_on.getUnlocalizedName());
		register(machine_wood_burner);
		register(machine_diesel);
		register(machine_combustion_engine);
		GameRegistry.registerBlock(machine_controller, machine_controller.getUnlocalizedName());
		GameRegistry.registerBlock(reactor_research, reactor_research.getUnlocalizedName());
		GameRegistry.registerBlock(reactor_zirnox, reactor_zirnox.getUnlocalizedName());
		GameRegistry.registerBlock(zirnox_destroyed, zirnox_destroyed.getUnlocalizedName());
		GameRegistry.registerBlock(machine_industrial_generator, machine_industrial_generator.getUnlocalizedName());
		GameRegistry.registerBlock(machine_radgen, machine_radgen.getUnlocalizedName());
		GameRegistry.registerBlock(machine_cyclotron, machine_cyclotron.getUnlocalizedName());
		GameRegistry.registerBlock(machine_exposure_chamber, machine_exposure_chamber.getUnlocalizedName());
		GameRegistry.registerBlock(machine_rtg_grey, machine_rtg_grey.getUnlocalizedName());
		GameRegistry.registerBlock(machine_geo, machine_geo.getUnlocalizedName());
		GameRegistry.registerBlock(machine_amgen, machine_amgen.getUnlocalizedName());
		GameRegistry.registerBlock(machine_minirtg, machine_minirtg.getUnlocalizedName());
		GameRegistry.registerBlock(machine_powerrtg, machine_powerrtg.getUnlocalizedName());
		GameRegistry.registerBlock(machine_radiolysis, machine_radiolysis.getUnlocalizedName());
		GameRegistry.registerBlock(machine_hephaestus, machine_hephaestus.getUnlocalizedName());
		GameRegistry.registerBlock(machine_spp_bottom, machine_spp_bottom.getUnlocalizedName());
		GameRegistry.registerBlock(machine_spp_top, machine_spp_top.getUnlocalizedName());

		GameRegistry.registerBlock(hadron_plating, hadron_plating.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_plating_blue, hadron_plating_blue.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_plating_black, hadron_plating_black.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_plating_yellow, hadron_plating_yellow.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_plating_striped, hadron_plating_striped.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_plating_glass, hadron_plating_glass.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_plating_voltz, hadron_plating_voltz.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_alloy, ItemBlockBase.class, hadron_coil_alloy.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_gold, ItemBlockBase.class, hadron_coil_gold.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_neodymium, ItemBlockBase.class, hadron_coil_neodymium.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_magtung, ItemBlockBase.class, hadron_coil_magtung.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_schrabidium, ItemBlockBase.class, hadron_coil_schrabidium.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_schrabidate, ItemBlockBase.class, hadron_coil_schrabidate.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_starmetal, ItemBlockBase.class, hadron_coil_starmetal.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_chlorophyte, ItemBlockBase.class, hadron_coil_chlorophyte.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_coil_mese, ItemBlockBase.class, hadron_coil_mese.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_power, hadron_power.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_power_10m, hadron_power_10m.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_power_100m, hadron_power_100m.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_power_1g, hadron_power_1g.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_power_10g, hadron_power_10g.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_diode, hadron_diode.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_analysis, hadron_analysis.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_analysis_glass, hadron_analysis_glass.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_access, hadron_access.getUnlocalizedName());
		GameRegistry.registerBlock(hadron_core, hadron_core.getUnlocalizedName());

		register(pa_source);
		register(pa_beamline);
		register(pa_rfc);
		register(pa_quadrupole);
		register(pa_dipole);
		register(pa_detector);

		GameRegistry.registerBlock(rbmk_rod, rbmk_rod.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_rod_mod, rbmk_rod_mod.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_rod_reasim, rbmk_rod_reasim.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_rod_reasim_mod, rbmk_rod_reasim_mod.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_control, rbmk_control.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_control_mod, rbmk_control_mod.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_control_auto, rbmk_control_auto.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_blank, rbmk_blank.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_boiler, rbmk_boiler.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_reflector, rbmk_reflector.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_absorber, rbmk_absorber.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_moderator, rbmk_moderator.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_outgasser, rbmk_outgasser.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_storage, rbmk_storage.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_cooler, rbmk_cooler.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_heater, rbmk_heater.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_console, rbmk_console.getUnlocalizedName());
		GameRegistry.registerBlock(rbmk_crane_console, rbmk_crane_console.getUnlocalizedName());
		register(rbmk_autoloader);
		register(rbmk_loader);
		register(rbmk_steam_inlet);
		register(rbmk_steam_outlet);
		GameRegistry.registerBlock(pribris, pribris.getUnlocalizedName());
		GameRegistry.registerBlock(pribris_burning, pribris_burning.getUnlocalizedName());
		GameRegistry.registerBlock(pribris_radiating, pribris_radiating.getUnlocalizedName());
		GameRegistry.registerBlock(pribris_digamma, pribris_digamma.getUnlocalizedName());

		GameRegistry.registerBlock(red_cable, red_cable.getUnlocalizedName());
		GameRegistry.registerBlock(red_cable_classic, red_cable_classic.getUnlocalizedName());
		GameRegistry.registerBlock(red_cable_paintable, red_cable_paintable.getUnlocalizedName());
		register(red_cable_gauge);
		GameRegistry.registerBlock(red_wire_coated, red_wire_coated.getUnlocalizedName());
		GameRegistry.registerBlock(red_connector, ItemBlockBase.class, red_connector.getUnlocalizedName());
		GameRegistry.registerBlock(red_pylon, ItemBlockBase.class, red_pylon.getUnlocalizedName());
		register(red_pylon_medium_wood);
		register(red_pylon_medium_wood_transformer);
		register(red_pylon_medium_steel);
		register(red_pylon_medium_steel_transformer);
		GameRegistry.registerBlock(red_pylon_large, ItemBlockBase.class, red_pylon_large.getUnlocalizedName());
		GameRegistry.registerBlock(substation, ItemBlockBase.class, substation.getUnlocalizedName());
		GameRegistry.registerBlock(cable_switch, cable_switch.getUnlocalizedName());
		GameRegistry.registerBlock(cable_detector, cable_detector.getUnlocalizedName());
		GameRegistry.registerBlock(cable_diode, ItemBlockBase.class, cable_diode.getUnlocalizedName());
		GameRegistry.registerBlock(machine_detector, machine_detector.getUnlocalizedName());
		register(fluid_duct_neo);
		register(fluid_duct_box);
		register(fluid_duct_exhaust);
		register(fluid_duct_paintable_block_exhaust);
		register(fluid_duct_paintable);
		register(fluid_duct_gauge);
		register(fluid_valve);
		register(fluid_switch);
		register(fluid_pump);
		register(machine_drain);
		register(radio_torch_sender);
		register(radio_torch_receiver);
		register(radio_torch_counter);
		register(radio_torch_logic);
		register(radio_torch_reader);
		register(radio_torch_controller);
		register(radio_telex);

		register(crane_extractor);
		register(crane_inserter);
		register(crane_grabber);
		register(crane_router);
		register(crane_boxer);
		register(crane_unboxer);
		register(conveyor);
		register(conveyor_express);
		register(conveyor_double);
		register(conveyor_triple);
		register(conveyor_chute);
		register(conveyor_lift);
		register(crane_splitter);
		register(crane_partitioner);
		register(drone_waypoint);
		register(drone_crate);
		register(drone_waypoint_request);
		register(drone_dock);
		register(drone_crate_provider);
		register(drone_crate_requester);
		register(pneumatic_tube);
		register(pneumatic_tube_paintable);
		register(fan);
		register(piston_inserter);

		GameRegistry.registerBlock(chain, chain.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_sturdy, ladder_sturdy.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_iron, ladder_iron.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_gold, ladder_gold.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_titanium, ladder_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_copper, ladder_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_tungsten, ladder_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_aluminium, ladder_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_steel, ladder_steel.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_lead, ladder_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ladder_cobalt, ladder_cobalt.getUnlocalizedName());
		GameRegistry.registerBlock(trapdoor_steel, trapdoor_steel.getUnlocalizedName());

		register(barrel_plastic);
		register(barrel_corroded);
		register(barrel_iron);
		register(barrel_steel);
		register(barrel_tcalloy);
		register(barrel_antimatter);
		register(machine_battery_potato);
		register(machine_battery);
		register(machine_lithium_battery);
		register(machine_schrabidium_battery);
		register(machine_dineutronium_battery);
		register(machine_fensu);
		register(capacitor_bus);
		register(capacitor_copper);
		register(capacitor_gold);
		register(capacitor_niobium);
		register(capacitor_tantalium);
		register(capacitor_schrabidate);
		GameRegistry.registerBlock(machine_transformer, machine_transformer.getUnlocalizedName());
		GameRegistry.registerBlock(machine_transformer_dnt, machine_transformer_dnt.getUnlocalizedName());
		GameRegistry.registerBlock(machine_converter_he_rf, machine_converter_he_rf.getUnlocalizedName());
		GameRegistry.registerBlock(machine_converter_rf_he, machine_converter_rf_he.getUnlocalizedName());
		GameRegistry.registerBlock(machine_electric_furnace_off, machine_electric_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_electric_furnace_on, machine_electric_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_microwave, machine_microwave.getUnlocalizedName());
		GameRegistry.registerBlock(machine_assembler, machine_assembler.getUnlocalizedName());
		register(machine_assembly_machine);
		GameRegistry.registerBlock(machine_assemfac, machine_assemfac.getUnlocalizedName());
		GameRegistry.registerBlock(machine_chemplant, machine_chemplant.getUnlocalizedName());
		register(machine_chemical_plant);
		register(machine_chemfac);
		register(machine_chemical_factory);
		register(machine_purex);
		register(machine_arc_welder);
		register(machine_soldering_station);
		register(machine_arc_furnace);
		register(machine_mixer);
		register(machine_fluidtank);
		register(machine_bat9000);
		register(machine_orbus);
		GameRegistry.registerBlock(machine_boiler_off, machine_boiler_off.getUnlocalizedName());
		register(machine_steam_engine);
		register(machine_turbine);
		register(machine_large_turbine);
		register(machine_chungus);
		GameRegistry.registerBlock(machine_condenser, machine_condenser.getUnlocalizedName());
		GameRegistry.registerBlock(machine_tower_small, machine_tower_small.getUnlocalizedName());
		GameRegistry.registerBlock(machine_tower_large, machine_tower_large.getUnlocalizedName());
		register(machine_condenser_powered);
		GameRegistry.registerBlock(machine_deuterium_extractor, machine_deuterium_extractor.getUnlocalizedName());
		GameRegistry.registerBlock(machine_deuterium_tower, machine_deuterium_tower.getUnlocalizedName());
		GameRegistry.registerBlock(machine_liquefactor, ItemBlockBase.class, machine_liquefactor.getUnlocalizedName());
		GameRegistry.registerBlock(machine_solidifier, ItemBlockBase.class, machine_solidifier.getUnlocalizedName());
		register(machine_intake);
		register(machine_compressor);
		register(machine_compressor_compact);
		GameRegistry.registerBlock(machine_electrolyser, machine_electrolyser.getUnlocalizedName());
		GameRegistry.registerBlock(machine_waste_drum, machine_waste_drum.getUnlocalizedName());
		GameRegistry.registerBlock(machine_storage_drum, machine_storage_drum.getUnlocalizedName());
		GameRegistry.registerBlock(machine_shredder, machine_shredder.getUnlocalizedName());
		register(machine_well);
		register(machine_pumpjack);
		register(machine_fracking_tower);
		register(machine_flare);
		register(chimney_brick);
		register(chimney_industrial);
		register(machine_refinery);
		register(machine_vacuum_distill);
		register(machine_fraction_tower);
		register(fraction_spacer);
		register(machine_catalytic_cracker);
		register(machine_catalytic_reformer);
		register(machine_hydrotreater);
		register(machine_coker);
		register(machine_pyrooven);
		register(machine_autosaw);
		register(machine_excavator);
		register(machine_ore_slopper);
		register(machine_mining_laser);
		register(barricade);
		register(machine_turbofan);
		register(machine_turbinegas);
		register(machine_lpw2);
		GameRegistry.registerBlock(machine_schrabidium_transmutator, machine_schrabidium_transmutator.getUnlocalizedName());
		GameRegistry.registerBlock(machine_teleporter, machine_teleporter.getUnlocalizedName());
		GameRegistry.registerBlock(teleanchor, teleanchor.getUnlocalizedName());
		GameRegistry.registerBlock(field_disturber, field_disturber.getUnlocalizedName());
		GameRegistry.registerBlock(machine_satlinker, machine_satlinker.getUnlocalizedName());
		GameRegistry.registerBlock(machine_keyforge, machine_keyforge.getUnlocalizedName());
		GameRegistry.registerBlock(machine_armor_table, machine_armor_table.getUnlocalizedName());
		GameRegistry.registerBlock(machine_weapon_table, machine_weapon_table.getUnlocalizedName());
		GameRegistry.registerBlock(machine_forcefield, machine_forcefield.getUnlocalizedName());
		GameRegistry.registerBlock(radiorec, radiorec.getUnlocalizedName());
		GameRegistry.registerBlock(radiobox, radiobox.getUnlocalizedName());

		//Multiblock Parts
		GameRegistry.registerBlock(struct_launcher, struct_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(struct_scaffold, struct_scaffold.getUnlocalizedName());
		GameRegistry.registerBlock(struct_launcher_core, struct_launcher_core.getUnlocalizedName());
		GameRegistry.registerBlock(struct_launcher_core_large, struct_launcher_core_large.getUnlocalizedName());
		GameRegistry.registerBlock(struct_soyuz_core, struct_soyuz_core.getUnlocalizedName());
		GameRegistry.registerBlock(struct_iter_core, struct_iter_core.getUnlocalizedName());
		GameRegistry.registerBlock(struct_plasma_core, struct_plasma_core.getUnlocalizedName());
		GameRegistry.registerBlock(struct_watz_core, struct_watz_core.getUnlocalizedName());
		GameRegistry.registerBlock(struct_icf_core, struct_icf_core.getUnlocalizedName());

		//Absorbers
		GameRegistry.registerBlock(absorber, absorber.getUnlocalizedName());
		GameRegistry.registerBlock(absorber_red, absorber_red.getUnlocalizedName());
		GameRegistry.registerBlock(absorber_green, absorber_green.getUnlocalizedName());
		GameRegistry.registerBlock(absorber_pink, absorber_pink.getUnlocalizedName());
		GameRegistry.registerBlock(decon, decon.getUnlocalizedName());

		//Solar Tower Blocks
		GameRegistry.registerBlock(machine_solar_boiler, machine_solar_boiler.getUnlocalizedName());
		GameRegistry.registerBlock(solar_mirror, solar_mirror.getUnlocalizedName());

		//Literal fucking garbage
		GameRegistry.registerBlock(factory_titanium_hull, factory_titanium_hull.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_hull, factory_advanced_hull.getUnlocalizedName());

		//CM stuff
		register(custom_machine, ItemCustomMachine.class);
		register(cm_block);
		register(cm_sheet);
		register(cm_engine);
		register(cm_tank);
		register(cm_circuit);
		register(cm_port);
		register(cm_flux);
		register(cm_heat);
		register(cm_anchor);

		//PWR
		register(pwr_fuel);
		register(pwr_control);
		register(pwr_channel);
		register(pwr_heatex);
		register(pwr_heatsink);
		register(pwr_neutron_source);
		register(pwr_reflector);
		register(pwr_casing);
		register(pwr_port);
		register(pwr_controller);
		register(pwr_block);

		//Multiblock Generators
		register(fusion_conductor);
		GameRegistry.registerBlock(fusion_center, fusion_center.getUnlocalizedName());
		GameRegistry.registerBlock(fusion_motor, fusion_motor.getUnlocalizedName());
		GameRegistry.registerBlock(fusion_heater, fusion_heater.getUnlocalizedName());
		GameRegistry.registerBlock(fusion_hatch, fusion_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(plasma, ItemBlockLore.class, plasma.getUnlocalizedName());
		GameRegistry.registerBlock(iter, iter.getUnlocalizedName());
		GameRegistry.registerBlock(plasma_heater, plasma_heater.getUnlocalizedName());

		register(watz_element);
		register(watz_cooler);
		register(watz_end);
		register(watz);
		register(watz_pump);

		register(machine_icf_press);
		register(icf_laser_component);
		register(icf_controller);
		register(icf_block);
		register(icf_component);
		register(icf);

		//E
		GameRegistry.registerBlock(balefire, balefire.getUnlocalizedName());
		GameRegistry.registerBlock(fire_digamma, fire_digamma.getUnlocalizedName());
		GameRegistry.registerBlock(digamma_matter, digamma_matter.getUnlocalizedName());
		register(volcano_core);
		register(volcano_rad_core);

		//Dark Fusion Core
		GameRegistry.registerBlock(dfc_emitter, dfc_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(dfc_injector, dfc_injector.getUnlocalizedName());
		GameRegistry.registerBlock(dfc_receiver, dfc_receiver.getUnlocalizedName());
		GameRegistry.registerBlock(dfc_stabilizer, dfc_stabilizer.getUnlocalizedName());
		GameRegistry.registerBlock(dfc_core, dfc_core.getUnlocalizedName());

		//Missile Blocks
		GameRegistry.registerBlock(machine_missile_assembly, machine_missile_assembly.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad, launch_pad.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_rusted, launch_pad_rusted.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_large, launch_pad_large.getUnlocalizedName());
		GameRegistry.registerBlock(compact_launcher, compact_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(launch_table, launch_table.getUnlocalizedName());
		GameRegistry.registerBlock(soyuz_launcher, soyuz_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(sat_dock, sat_dock.getUnlocalizedName());
		GameRegistry.registerBlock(soyuz_capsule, soyuz_capsule.getUnlocalizedName());
		GameRegistry.registerBlock(crate_supply, crate_supply.getUnlocalizedName());
		GameRegistry.registerBlock(machine_radar, machine_radar.getUnlocalizedName());
		GameRegistry.registerBlock(machine_radar_large, machine_radar_large.getUnlocalizedName());
		GameRegistry.registerBlock(radar_screen, radar_screen.getUnlocalizedName());

		//Guide
		GameRegistry.registerBlock(book_guide, book_guide.getUnlocalizedName());

		//Sat Blocks
		GameRegistry.registerBlock(sat_mapper, sat_mapper.getUnlocalizedName());
		GameRegistry.registerBlock(sat_scanner, sat_scanner.getUnlocalizedName());
		GameRegistry.registerBlock(sat_radar, sat_radar.getUnlocalizedName());
		GameRegistry.registerBlock(sat_laser, sat_laser.getUnlocalizedName());
		GameRegistry.registerBlock(sat_foeq, sat_foeq.getUnlocalizedName());
		GameRegistry.registerBlock(sat_resonator, sat_resonator.getUnlocalizedName());

		//Rails
		GameRegistry.registerBlock(rail_wood, ItemBlockBase.class, rail_wood.getUnlocalizedName());
		GameRegistry.registerBlock(rail_narrow, ItemBlockBase.class, rail_narrow.getUnlocalizedName());
		GameRegistry.registerBlock(rail_highspeed, ItemBlockBase.class, rail_highspeed.getUnlocalizedName());
		GameRegistry.registerBlock(rail_booster, ItemBlockBase.class, rail_booster.getUnlocalizedName());
		register(rail_narrow_straight);
		register(rail_narrow_curve);
		register(rail_large_straight);
		register(rail_large_straight_short);
		register(rail_large_curve);
		register(rail_large_curve_7);
		register(rail_large_curve_9);
		register(rail_large_ramp);
		register(rail_large_buffer);
		register(rail_large_switch);
		register(rail_large_switch_flipped);

		//Crate
		GameRegistry.registerBlock(crate, crate.getUnlocalizedName());
		GameRegistry.registerBlock(crate_weapon, crate_weapon.getUnlocalizedName());
		GameRegistry.registerBlock(crate_lead, crate_lead.getUnlocalizedName());
		GameRegistry.registerBlock(crate_metal, crate_metal.getUnlocalizedName());
		GameRegistry.registerBlock(crate_red, crate_red.getUnlocalizedName());
		GameRegistry.registerBlock(crate_can, crate_can.getUnlocalizedName());
		GameRegistry.registerBlock(crate_ammo, crate_ammo.getUnlocalizedName());
		GameRegistry.registerBlock(crate_jungle, crate_jungle.getUnlocalizedName());

		//ElB
		GameRegistry.registerBlock(statue_elb_f, statue_elb_f.getUnlocalizedName());

		//Fluids
		GameRegistry.registerBlock(mud_block, mud_block.getUnlocalizedName());
		GameRegistry.registerBlock(acid_block, acid_block.getUnlocalizedName());
		GameRegistry.registerBlock(toxic_block, toxic_block.getUnlocalizedName());
		GameRegistry.registerBlock(schrabidic_block, schrabidic_block.getUnlocalizedName());
		GameRegistry.registerBlock(corium_block, corium_block.getUnlocalizedName());
		GameRegistry.registerBlock(volcanic_lava_block, volcanic_lava_block.getUnlocalizedName());
		GameRegistry.registerBlock(rad_lava_block, rad_lava_block.getUnlocalizedName());
		GameRegistry.registerBlock(sulfuric_acid_block, sulfuric_acid_block.getUnlocalizedName());
		//GameRegistry.registerBlock(concrete_liquid, concrete_liquid.getUnlocalizedName());

		//Multiblock Dummy Blocks
		GameRegistry.registerBlock(dummy_block_vault, dummy_block_vault.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_blast, dummy_block_blast.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_plate_compact_launcher, dummy_plate_compact_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_compact_launcher, dummy_port_compact_launcher.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_plate_launch_table, dummy_plate_launch_table.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_launch_table, dummy_port_launch_table.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_plate_cargo, dummy_plate_cargo.getUnlocalizedName());

		//Other Technical Blocks
		GameRegistry.registerBlock(oil_pipe, oil_pipe.getUnlocalizedName());
		GameRegistry.registerBlock(vent_chlorine, vent_chlorine.getUnlocalizedName());
		GameRegistry.registerBlock(vent_cloud, vent_cloud.getUnlocalizedName());
		GameRegistry.registerBlock(vent_pink_cloud, vent_pink_cloud.getUnlocalizedName());
		GameRegistry.registerBlock(vent_chlorine_seal, vent_chlorine_seal.getUnlocalizedName());
		GameRegistry.registerBlock(chlorine_gas, chlorine_gas.getUnlocalizedName());
		GameRegistry.registerBlock(gas_radon, gas_radon.getUnlocalizedName());
		GameRegistry.registerBlock(gas_radon_dense, gas_radon_dense.getUnlocalizedName());
		GameRegistry.registerBlock(gas_radon_tomb, gas_radon_tomb.getUnlocalizedName());
		GameRegistry.registerBlock(gas_meltdown, gas_meltdown.getUnlocalizedName());
		GameRegistry.registerBlock(gas_monoxide, gas_monoxide.getUnlocalizedName());
		GameRegistry.registerBlock(gas_asbestos, gas_asbestos.getUnlocalizedName());
		GameRegistry.registerBlock(gas_coal, gas_coal.getUnlocalizedName());
		GameRegistry.registerBlock(gas_flammable, gas_flammable.getUnlocalizedName());
		GameRegistry.registerBlock(gas_explosive, gas_explosive.getUnlocalizedName());
		GameRegistry.registerBlock(vacuum, vacuum.getUnlocalizedName());

		// OC Compat Items
		if (Loader.isModLoaded("OpenComputers")) {
			register(oc_cable_paintable);
		}

		//???
		GameRegistry.registerBlock(crystal_virus, crystal_virus.getUnlocalizedName());
		GameRegistry.registerBlock(crystal_hardened, crystal_hardened.getUnlocalizedName());
		GameRegistry.registerBlock(crystal_pulsar, crystal_pulsar.getUnlocalizedName());
		register(taint);
		GameRegistry.registerBlock(cheater_virus, cheater_virus.getUnlocalizedName());
		GameRegistry.registerBlock(cheater_virus_seed, cheater_virus_seed.getUnlocalizedName());
		GameRegistry.registerBlock(ntm_dirt, ntm_dirt.getUnlocalizedName());
		GameRegistry.registerBlock(pink_log, pink_log.getUnlocalizedName());
		GameRegistry.registerBlock(pink_planks, pink_planks.getUnlocalizedName());
		GameRegistry.registerBlock(pink_slab, pink_slab.getUnlocalizedName());
		GameRegistry.registerBlock(pink_double_slab, pink_double_slab.getUnlocalizedName());
		GameRegistry.registerBlock(pink_stairs, pink_stairs.getUnlocalizedName());

		register(wand_air);
		register(wand_loot);
		register(wand_jigsaw);
		register(wand_logic);
		register(wand_tandem);

		register(logic_block);
	}

	private static void register(Block b) {
		GameRegistry.registerBlock(b, ItemBlockBase.class, b.getUnlocalizedName());
	}

	private static void register(Block b, Class<? extends ItemBlock> clazz) {
		GameRegistry.registerBlock(b, clazz, b.getUnlocalizedName());
	}

	public static void addRemap(String unloc, Block block, int meta) {
		Block remap = new BlockRemap(block, meta).setBlockName(unloc);
		register(remap, ItemBlockRemap.class);
	}

	// Pretty much the default getDrops function but with no damage set on the item (fucks with recipes)
	// but setting the meta via damageDropped breaks creative middle-click and any WAILA-like overlays
	public static ArrayList<ItemStack> getDropsWithoutDamage(World world, Block block, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int count = block.quantityDropped(metadata, fortune, world.rand);
		for(int i = 0; i < count; i++) {
			Item item = block.getItemDropped(metadata, world.rand, fortune);
			if(item != null) {
				ret.add(new ItemStack(item, 1, 0));
			}
		}

		return ret;
	}
}
