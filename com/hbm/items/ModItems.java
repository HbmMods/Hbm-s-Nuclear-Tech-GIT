package com.hbm.items;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.BucketHandler;
import com.hbm.items.bomb.*;
import com.hbm.items.food.*;
import com.hbm.items.gear.*;
import com.hbm.items.special.*;
import com.hbm.items.tool.*;
import com.hbm.items.weapon.*;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModItems {
	
	public static void mainRegistry()
	{
		initializeItem();
		registerItem();
	}
	
	public static Item redstone_sword;
	public static Item big_sword;

	public static Item test_helmet;
	public static Item test_chestplate;
	public static Item test_leggings;
	public static Item test_boots;
	
	public static Item ingot_uranium;
	public static Item ingot_u235;
	public static Item ingot_u238;
	public static Item ingot_u238m2;
	public static Item ingot_plutonium;
	public static Item ingot_pu238;
	public static Item ingot_pu239;
	public static Item ingot_pu240;
	public static Item ingot_neptunium;
	public static Item ingot_titanium;
	public static Item sulfur;

	public static Item niter;
	public static Item ingot_copper;
	public static Item ingot_red_copper;
	public static Item ingot_tungsten;
	public static Item ingot_aluminium;
	public static Item fluorite;
	public static Item ingot_beryllium;
	public static Item ingot_schrabidium;
	public static Item ingot_plutonium_fuel;
	public static Item ingot_uranium_fuel;
	public static Item ingot_mox_fuel;
	public static Item ingot_schrabidium_fuel;
	public static Item nugget_uranium_fuel;
	public static Item nugget_plutonium_fuel;
	public static Item nugget_mox_fuel;
	public static Item nugget_schrabidium_fuel;
	public static Item ingot_advanced_alloy;
	public static Item lithium;
	public static Item ingot_hes;
	public static Item ingot_les;
	public static Item nugget_hes;
	public static Item nugget_les;
	public static Item ingot_magnetized_tungsten;
	public static Item ingot_combine_steel;
	public static Item ingot_solinium;
	public static Item nugget_solinium;

	public static Item ingot_australium;
	public static Item ingot_weidanium;
	public static Item ingot_reiium;
	public static Item ingot_unobtainium;
	public static Item ingot_daffergon;
	public static Item ingot_verticium;
	public static Item nugget_australium;
	public static Item nugget_weidanium;
	public static Item nugget_reiium;
	public static Item nugget_unobtainium;
	public static Item nugget_daffergon;
	public static Item nugget_verticium;
	
	public static Item ingot_desh;
	public static Item nugget_desh;
	public static Item ingot_dineutronium;
	public static Item nugget_dineutronium;
	public static Item powder_dineutronium;
	public static Item ingot_tetraneutronium;
	public static Item nugget_tetraneutronium;
	public static Item powder_tetraneutronium;
	public static Item ingot_starmetal;
	public static Item ingot_saturnite;
	public static Item plate_saturnite;

	public static Item nugget_uranium;
	public static Item nugget_u235;
	public static Item nugget_u238;
	public static Item nugget_plutonium;
	public static Item nugget_pu238;
	public static Item nugget_pu239;
	public static Item nugget_pu240;
	public static Item nugget_neptunium;
	public static Item plate_titanium;
	public static Item plate_aluminium;
	public static Item wire_red_copper;
	public static Item wire_tungsten;
	public static Item neutron_reflector;
	public static Item ingot_steel;
	public static Item plate_steel;
	public static Item plate_iron;
	public static Item ingot_lead;
	public static Item nugget_lead;
	public static Item plate_lead;
	public static Item nugget_schrabidium;
	public static Item plate_schrabidium;
	public static Item plate_copper;
	public static Item nugget_beryllium;
	public static Item plate_gold;
	public static Item hazmat_cloth;
	public static Item asbestos_cloth;
	public static Item filter_coal;
	public static Item plate_advanced_alloy;
	public static Item plate_combine_steel;
	public static Item plate_mixed;
	public static Item plate_paa;
	public static Item board_copper;
	public static Item bolt_dura_steel;
	public static Item pipes_steel;
	public static Item drill_titanium;
	public static Item plate_dalekanium;
	public static Item plate_euphemium;
	public static Item bolt_tungsten;
	public static Item bolt_compound;
	public static Item plate_polymer;
	public static Item plate_dineutronium;
	public static Item plate_desh;
	public static Item photo_panel;
	public static Item sat_base;
	public static Item thruster_nuclear;
	
	public static Item ingot_dura_steel;
	public static Item ingot_polymer;

	public static Item ingot_lanthanium;
	public static Item ingot_actinium;

	public static Item solid_fuel;

	public static Item powder_lead;
	public static Item powder_neptunium;
	public static Item powder_schrabidium;

	public static Item powder_aluminium;
	public static Item powder_beryllium;
	public static Item powder_copper;
	public static Item powder_gold;
	public static Item powder_iron;
	public static Item powder_titanium;
	public static Item powder_tungsten;
	public static Item powder_uranium;
	public static Item powder_plutonium;
	public static Item dust;
	public static Item powder_power;

	public static Item powder_thorium;
	public static Item powder_iodine;
	public static Item powder_neodymium;
	public static Item powder_astatine;
	public static Item powder_caesium;

	public static Item powder_strontium;
	public static Item powder_cobalt;
	public static Item powder_bromine;
	public static Item powder_niobium;
	public static Item powder_tennessine;
	public static Item powder_cerium;

	public static Item powder_advanced_alloy;
	public static Item powder_coal;
	public static Item powder_combine_steel;
	public static Item powder_diamond;
	public static Item powder_emerald;
	public static Item powder_lapis;
	public static Item powder_quartz;
	public static Item powder_magnetized_tungsten;
	public static Item powder_red_copper;
	public static Item powder_steel;
	public static Item powder_lithium;

	public static Item powder_australium;
	public static Item powder_weidanium;
	public static Item powder_reiium;
	public static Item powder_unobtainium;
	public static Item powder_daffergon;
	public static Item powder_verticium;

	public static Item powder_dura_steel;
	public static Item powder_polymer;
	public static Item powder_euphemium;
	public static Item powder_meteorite;
	
	public static Item powder_lithium_tiny;
	public static Item powder_neodymium_tiny;
	public static Item powder_cobalt_tiny;
	public static Item powder_niobium_tiny;
	public static Item powder_cerium_tiny;
	public static Item powder_lanthanium_tiny;
	public static Item powder_actinium_tiny;
	public static Item powder_meteorite_tiny;

	public static Item powder_lanthanium;
	public static Item powder_actinium;
	public static Item powder_desh;
	public static Item powder_desh_mix;
	public static Item powder_nitan_mix;
	public static Item powder_spark_mix;
	public static Item powder_yellowcake;
	public static Item powder_magic;

	public static Item fragment_neodymium;
	public static Item fragment_cobalt;
	public static Item fragment_niobium;
	public static Item fragment_cerium;
	public static Item fragment_lanthanium;
	public static Item fragment_actinium;
	public static Item fragment_meteorite;

	public static Item biomass;
	public static Item biomass_compressed;
	
	public static Item coil_copper;
	public static Item coil_copper_torus;
	public static Item coil_tungsten;
	public static Item tank_steel;
	public static Item motor;
	public static Item centrifuge_element;
	public static Item centrifuge_tower;
	public static Item reactor_core;
	public static Item rtg_unit;
	public static Item thermo_unit_empty;
	public static Item thermo_unit_endo;
	public static Item thermo_unit_exo;
	public static Item levitation_unit;
	public static Item wire_aluminium;
	public static Item wire_copper;
	public static Item wire_gold;
	public static Item wire_schrabidium;
	public static Item wire_advanced_alloy;
	public static Item coil_advanced_alloy;
	public static Item coil_advanced_torus;
	public static Item wire_magnetized_tungsten;
	public static Item coil_magnetized_tungsten;
	public static Item coil_gold;
	public static Item coil_gold_torus;
	public static Item magnet_dee;
	public static Item magnet_circular;
	public static Item cyclotron_tower;
	public static Item component_limiter;
	public static Item component_emitter;

	public static Item circuit_raw;
	public static Item circuit_aluminium;
	public static Item circuit_copper;
	public static Item circuit_red_copper;
	public static Item circuit_gold;
	public static Item circuit_schrabidium;

	public static Item mechanism_revolver_1;
	public static Item mechanism_revolver_2;
	public static Item mechanism_rifle_1;
	public static Item mechanism_rifle_2;
	public static Item mechanism_launcher_1;
	public static Item mechanism_launcher_2;
	public static Item mechanism_special;

	public static Item circuit_targeting_tier1;
	public static Item circuit_targeting_tier2;
	public static Item circuit_targeting_tier3;
	public static Item circuit_targeting_tier4;
	public static Item circuit_targeting_tier5;
	public static Item circuit_targeting_tier6;

	public static Item wiring_red_copper;

	public static Item cap_aluminium;
	public static Item hull_small_steel;
	public static Item hull_small_aluminium;
	public static Item hull_big_steel;
	public static Item hull_big_aluminium;
	public static Item hull_big_titanium;
	public static Item fins_flat;
	public static Item fins_small_steel;
	public static Item fins_big_steel;
	public static Item fins_tri_steel;
	public static Item fins_quad_titanium;
	public static Item sphere_steel;
	public static Item pedestal_steel;
	public static Item dysfunctional_reactor;
	public static Item rotor_steel;
	public static Item generator_steel;
	public static Item blade_titanium;
	public static Item turbine_titanium;
	public static Item generator_front;
	public static Item blade_tungsten;
	public static Item turbine_tungsten;
	public static Item pellet_coal;

	public static Item toothpicks;
	public static Item ducttape;
	public static Item catalyst_clay;
	
	public static Item warhead_generic_small;
	public static Item warhead_generic_medium;
	public static Item warhead_generic_large;
	public static Item warhead_incendiary_small; 
	public static Item warhead_incendiary_medium;
	public static Item warhead_incendiary_large; 
	public static Item warhead_cluster_small; 
	public static Item warhead_cluster_medium;
	public static Item warhead_cluster_large; 
	public static Item warhead_buster_small; 
	public static Item warhead_buster_medium;
	public static Item warhead_buster_large; 
	public static Item warhead_nuclear;
	public static Item warhead_mirvlet;
	public static Item warhead_mirv;
	public static Item warhead_thermo_endo;
	public static Item warhead_thermo_exo;

	public static Item fuel_tank_small;
	public static Item fuel_tank_medium;
	public static Item fuel_tank_large;

	public static Item thruster_small;
	public static Item thruster_medium;
	public static Item thruster_large;

	public static Item sat_head_mapper;
	public static Item sat_head_scanner;
	public static Item sat_head_radar;
	public static Item sat_head_laser;
	public static Item sat_head_resonator;

	public static Item chopper_head;
	public static Item chopper_gun;
	public static Item chopper_torso;
	public static Item chopper_tail;
	public static Item chopper_wing;
	public static Item chopper_blades;
	public static Item combine_scrap;

	public static Item shimmer_head;
	public static Item shimmer_axe_head;
	public static Item shimmer_handle;

	public static Item telepad;
	public static Item entanglement_kit;

	public static Item stamp_stone_flat;
	public static Item stamp_stone_plate;
	public static Item stamp_stone_wire;
	public static Item stamp_stone_circuit;
	public static Item stamp_iron_flat;
	public static Item stamp_iron_plate;
	public static Item stamp_iron_wire;
	public static Item stamp_iron_circuit;
	public static Item stamp_steel_flat;
	public static Item stamp_steel_plate;
	public static Item stamp_steel_wire;
	public static Item stamp_steel_circuit;
	public static Item stamp_titanium_flat;
	public static Item stamp_titanium_plate;
	public static Item stamp_titanium_wire;
	public static Item stamp_titanium_circuit;
	public static Item stamp_obsidian_flat;
	public static Item stamp_obsidian_plate;
	public static Item stamp_obsidian_wire;
	public static Item stamp_obsidian_circuit;
	public static Item stamp_schrabidium_flat;
	public static Item stamp_schrabidium_plate;
	public static Item stamp_schrabidium_wire;
	public static Item stamp_schrabidium_circuit;

	public static Item blades_gold;
	public static Item blades_aluminium;
	public static Item blades_iron;
	public static Item blades_steel;
	public static Item blades_titanium;
	public static Item blades_advanced_alloy;
	public static Item blades_combine_steel;
	public static Item blades_schrabidium;

	public static Item part_lithium;
	public static Item part_beryllium;
	public static Item part_carbon;
	public static Item part_copper;
	public static Item part_plutonium;

	public static Item thermo_element;
	public static Item limiter;

	public static Item pellet_rtg;
	public static Item pellet_rtg_weak;
	public static Item tritium_deuterium_cake;

	public static Item pellet_schrabidium;
	public static Item pellet_hes;
	public static Item pellet_mes;
	public static Item pellet_les;
	public static Item pellet_beryllium;
	public static Item pellet_neptunium;
	public static Item pellet_lead;
	public static Item pellet_advanced;

	public static Item piston_selenium;
	
	public static Item crystal_energy;
	public static Item pellet_coolant;

	public static Item rune_blank;
	public static Item rune_isa;
	public static Item rune_dagaz;
	public static Item rune_hagalaz;
	public static Item rune_jera;
	public static Item rune_thurisaz;
	
	public static Item ams_catalyst_blank;
	public static Item ams_catalyst_aluminium;
	public static Item ams_catalyst_beryllium;
	public static Item ams_catalyst_caesium;
	public static Item ams_catalyst_cerium;
	public static Item ams_catalyst_cobalt;
	public static Item ams_catalyst_copper;
	public static Item ams_catalyst_dineutronium;
	public static Item ams_catalyst_euphemium;
	public static Item ams_catalyst_iron;
	public static Item ams_catalyst_lithium;
	public static Item ams_catalyst_niobium;
	public static Item ams_catalyst_schrabidium;
	public static Item ams_catalyst_strontium;
	public static Item ams_catalyst_thorium;
	public static Item ams_catalyst_tungsten;

	public static Item ams_focus_blank;
	public static Item ams_focus_limiter;
	public static Item ams_focus_booster;

	public static Item ams_muzzle;

	public static Item ams_core_sing;
	public static Item ams_core_wormhole;
	public static Item ams_core_eyeofharmony;
	public static Item ams_core_thingy;
	
	public static Item cell_empty;
	public static Item cell_uf6;
	public static Item cell_puf6;
	public static Item cell_deuterium;
	public static Item cell_tritium;
	public static Item cell_sas3;
	public static Item cell_antimatter;
	public static Item cell_anti_schrabidium;
	public static Item pellet_antimatter;
	public static Item singularity;
	public static Item singularity_counter_resonant;
	public static Item singularity_super_heated;
	public static Item black_hole;
	public static Item singularity_spark;
	public static Item crystal_xen;
	public static Item inf_water;

	public static Item canister_empty;
	public static Item canister_smear;
	public static Item canister_canola;
	public static Item canister_oil;
	public static Item canister_fuel;
	public static Item canister_kerosene;
	public static Item canister_reoil;
	public static Item canister_petroil;
	public static Item canister_napalm;
	public static Item canister_NITAN;

	public static Item canister_heavyoil;
	public static Item canister_bitumen;
	public static Item canister_heatingoil;
	public static Item canister_naphtha;
	public static Item canister_lightoil;
	public static Item canister_biofuel;

	public static Item gas_empty;
	public static Item gas_full;
	public static Item gas_petroleum;
	public static Item gas_biogas;

	public static Item fluid_tank_full;
	public static Item fluid_tank_empty;
	public static Item fluid_barrel_full;
	public static Item fluid_barrel_empty;
	public static Item fluid_barrel_infinite;

	public static Item syringe_empty;
	public static Item syringe_antidote;
	public static Item syringe_poison;
	public static Item syringe_awesome;
	public static Item syringe_metal_empty;
	public static Item syringe_metal_stimpak;
	public static Item syringe_metal_medx;
	public static Item syringe_metal_psycho;
	public static Item syringe_metal_super;
	public static Item syringe_taint;
	public static Item radaway;
	public static Item med_bag;
	public static Item pill_iodine;
	public static Item plan_c;
	public static Item stealth_boy;

	public static Item can_empty;
	public static Item can_smart;
	public static Item can_creature;
	public static Item can_redbomb;
	public static Item can_mrsugar;
	public static Item can_overcharge;
	public static Item can_luna;
	public static Item bottle_empty;
	public static Item bottle_nuka;
	public static Item bottle_cherry;
	public static Item bottle_quantum;
	public static Item bottle_sparkle;
	public static Item bottle2_empty;
	public static Item bottle2_korl;
	public static Item bottle2_fritz;
	public static Item bottle2_korl_special;
	public static Item bottle2_fritz_special;
	public static Item bottle2_sunset;
	public static Item chocolate_milk;
	public static Item cap_nuka;
	public static Item cap_quantum;
	public static Item cap_sparkle;
	public static Item cap_korl;
	public static Item cap_fritz;
	public static Item cap_sunset;
	public static Item ring_pull;

	public static Item rod_empty;
	public static Item rod_uranium;
	public static Item rod_u235;
	public static Item rod_u238;
	public static Item rod_plutonium;
	public static Item rod_pu238;
	public static Item rod_pu239;
	public static Item rod_pu240;
	public static Item rod_neptunium;
	public static Item rod_lead;
	public static Item rod_schrabidium;
	public static Item rod_solinium;
	public static Item rod_euphemium;
	public static Item rod_australium;
	public static Item rod_weidanium;
	public static Item rod_reiium;
	public static Item rod_unobtainium;
	public static Item rod_daffergon;
	public static Item rod_verticium;

	public static Item rod_dual_empty;
	public static Item rod_dual_uranium;
	public static Item rod_dual_u235;
	public static Item rod_dual_u238;
	public static Item rod_dual_plutonium;
	public static Item rod_dual_pu238;
	public static Item rod_dual_pu239;
	public static Item rod_dual_pu240;
	public static Item rod_dual_neptunium;
	public static Item rod_dual_lead;
	public static Item rod_dual_schrabidium;
	public static Item rod_dual_solinium;

	public static Item rod_quad_empty;
	public static Item rod_quad_uranium;
	public static Item rod_quad_u235;
	public static Item rod_quad_u238;
	public static Item rod_quad_plutonium;
	public static Item rod_quad_pu238;
	public static Item rod_quad_pu239;
	public static Item rod_quad_pu240;
	public static Item rod_quad_neptunium;
	public static Item rod_quad_lead;
	public static Item rod_quad_schrabidium;
	public static Item rod_quad_solinium;

	public static Item rod_uranium_fuel;
	public static Item rod_dual_uranium_fuel;
	public static Item rod_quad_uranium_fuel;
	public static Item rod_plutonium_fuel;
	public static Item rod_dual_plutonium_fuel;
	public static Item rod_quad_plutonium_fuel;
	public static Item rod_mox_fuel;
	public static Item rod_dual_mox_fuel;
	public static Item rod_quad_mox_fuel;
	public static Item rod_schrabidium_fuel;
	public static Item rod_dual_schrabidium_fuel;
	public static Item rod_quad_schrabidium_fuel;

	public static Item rod_water;
	public static Item rod_dual_water;
	public static Item rod_quad_water;

	public static Item rod_coolant;
	public static Item rod_dual_coolant;
	public static Item rod_quad_coolant;

	public static Item scrap;
	public static Item trinitite;
	public static Item nuclear_waste;
	public static Item rod_uranium_fuel_depleted;
	public static Item rod_dual_uranium_fuel_depleted;
	public static Item rod_quad_uranium_fuel_depleted;
	public static Item rod_plutonium_fuel_depleted;
	public static Item rod_dual_plutonium_fuel_depleted;
	public static Item rod_quad_plutonium_fuel_depleted;
	public static Item rod_mox_fuel_depleted;
	public static Item rod_dual_mox_fuel_depleted;
	public static Item rod_quad_mox_fuel_depleted;
	public static Item rod_schrabidium_fuel_depleted;
	public static Item rod_dual_schrabidium_fuel_depleted;
	public static Item rod_quad_schrabidium_fuel_depleted;
	public static Item rod_waste;
	public static Item rod_dual_waste;
	public static Item rod_quad_waste;
	
	public static Item rod_lithium;
	public static Item rod_dual_lithium;
	public static Item rod_quad_lithium;
	public static Item rod_tritium;
	public static Item rod_dual_tritium;
	public static Item rod_quad_tritium;
	
	public static Item test_nuke_igniter;
	public static Item test_nuke_propellant;
	public static Item test_nuke_tier1_shielding;
	public static Item test_nuke_tier2_shielding;
	public static Item test_nuke_tier1_bullet;
	public static Item test_nuke_tier2_bullet;
	public static Item test_nuke_tier1_target;
	public static Item test_nuke_tier2_target;

	public static Item pellet_cluster;
	public static Item powder_fire;
	public static Item powder_ice;
	public static Item powder_poison;
	public static Item powder_thermite;
	public static Item pellet_gas;
	public static Item magnetron;
	public static Item pellet_buckshot;

	public static Item designator;
	public static Item designator_range;
	public static Item designator_manual;
	public static Item linker;
	public static Item oil_detector;
	public static Item geiger_counter;
	public static Item survey_scanner;

	public static Item template_folder;
	public static Item assembly_template;
	public static Item chemistry_template;
	public static Item chemistry_icon;
	public static Item fluid_identifier;
	public static Item fluid_icon;
	public static Item siren_track;

	public static Item missile_assembly;
	public static Item missile_generic;
	public static Item missile_anti_ballistic;
	public static Item missile_incendiary;
	public static Item missile_cluster;
	public static Item missile_buster;
	public static Item missile_strong;
	public static Item missile_incendiary_strong;
	public static Item missile_cluster_strong;
	public static Item missile_buster_strong;
	public static Item missile_burst;
	public static Item missile_inferno;
	public static Item missile_rain;
	public static Item missile_drill;
	public static Item missile_nuclear;
	public static Item missile_nuclear_cluster;
	public static Item missile_endo;
	public static Item missile_exo;
	public static Item missile_doomsday;
	public static Item missile_taint;
	public static Item missile_micro;
	public static Item missile_bhole;
	public static Item missile_schrabidium;
	public static Item missile_emp;

	public static Item missile_carrier;
	public static Item sat_mapper;
	public static Item sat_scanner;
	public static Item sat_radar;
	public static Item sat_laser;
	public static Item sat_foeq;
	public static Item sat_resonator;
	public static Item sat_chip;
	public static Item sat_interface;

	public static Item gun_rpg;
	public static Item gun_rpg_ammo;
	public static Item gun_stinger;
	public static Item gun_skystinger;
	public static Item gun_stinger_ammo;
	public static Item gun_revolver;
	public static Item gun_revolver_saturnite;
	public static Item gun_revolver_ammo;
	public static Item gun_revolver_iron;
	public static Item gun_revolver_iron_ammo;
	public static Item gun_revolver_gold;
	public static Item gun_revolver_gold_ammo;
	public static Item gun_revolver_lead;
	public static Item gun_revolver_lead_ammo;
	public static Item gun_revolver_schrabidium;
	public static Item gun_revolver_schrabidium_ammo;
	public static Item gun_revolver_cursed;
	public static Item gun_revolver_cursed_ammo;
	public static Item gun_revolver_nightmare;
	public static Item gun_revolver_nightmare_ammo;
	public static Item gun_revolver_nightmare2;
	public static Item gun_revolver_nightmare2_ammo;
	public static Item gun_revolver_pip;
	public static Item gun_revolver_pip_ammo;
	public static Item gun_fatman;
	public static Item gun_proto;
	public static Item gun_fatman_ammo;
	public static Item gun_mirv;
	public static Item gun_mirv_ammo;
	public static Item gun_bf;
	public static Item gun_bf_ammo;
	public static Item gun_mp40;
	public static Item gun_mp40_ammo;
	public static Item gun_uzi;
	public static Item gun_uzi_silencer;
	public static Item gun_uzi_saturnite;
	public static Item gun_uzi_saturnite_silencer;
	public static Item gun_uzi_ammo;
	public static Item gun_uboinik;
	public static Item gun_uboinik_ammo;
	public static Item gun_lever_action;
	public static Item gun_lever_action_dark;
	public static Item gun_lever_action_sonata;
	public static Item gun_lever_action_ammo;
	public static Item gun_bolt_action;
	public static Item gun_bolt_action_green;
	public static Item gun_bolt_action_saturnite;
	public static Item gun_bolt_action_ammo;
	public static Item gun_b92;
	public static Item gun_b92_ammo;
	public static Item gun_b93;
	public static Item gun_xvl1456;
	public static Item gun_xvl1456_ammo;
	public static Item gun_osipr;
	public static Item gun_osipr_ammo;
	public static Item gun_osipr_ammo2;
	public static Item gun_immolator;
	public static Item gun_immolator_ammo;
	public static Item gun_cryolator;
	public static Item gun_cryolator_ammo;
	public static Item gun_mp;
	public static Item gun_mp_ammo;
	public static Item gun_zomg;
	public static Item gun_super_shotgun;
	public static Item gun_moist_nugget;
	public static Item gun_revolver_inverted;
	public static Item gun_emp;
	public static Item gun_emp_ammo;
	public static Item gun_jack;
	public static Item gun_jack_ammo;
	public static Item gun_spark;
	public static Item gun_spark_ammo;
	public static Item gun_hp;
	public static Item gun_hp_ammo;
	public static Item gun_euthanasia;
	public static Item gun_euthanasia_ammo;
	public static Item gun_dash;
	public static Item gun_dash_ammo;
	public static Item gun_twigun;
	public static Item gun_twigun_ammo;
	public static Item gun_defabricator;
	public static Item gun_defabricator_ammo;
	public static Item gun_dampfmaschine;
	public static Item gun_waluigi;

	public static Item grenade_generic;
	public static Item grenade_strong;
	public static Item grenade_frag;
	public static Item grenade_fire;
	public static Item grenade_shrapnel;
	public static Item grenade_cluster;
	public static Item grenade_flare;
	public static Item grenade_electric;
	public static Item grenade_poison;
	public static Item grenade_gas;
	public static Item grenade_pulse;
	public static Item grenade_plasma;
	public static Item grenade_tau;
	public static Item grenade_schrabidium;
	public static Item grenade_lemon;
	public static Item grenade_gascan;
	public static Item grenade_mk2;
	public static Item grenade_aschrab;
	public static Item grenade_nuke;
	public static Item grenade_nuclear;
	public static Item grenade_zomg;
	public static Item grenade_black_hole;
	public static Item grenade_cloud;
	public static Item grenade_pink_cloud;
	public static Item ullapool_caber;

	public static Item weaponized_starblaster_cell;

	public static Item bomb_waffle;
	public static Item schnitzel_vegan;
	public static Item cotton_candy;
	public static Item apple_schrabidium;
	public static Item tem_flakes;
	public static Item glowing_stew;
	public static Item lemon;
	public static Item definitelyfood;

	public static Item med_ipecac;
	public static Item med_ptsd;
	public static Item med_schizophrenia;
	
	public static Item defuser;

	public static Item flame_pony;
	public static Item flame_conspiracy;
	public static Item flame_politics;
	public static Item flame_opinion;
	
	public static Item gadget_explosive;
	public static Item gadget_explosive8;
	public static Item gadget_wireing;
	public static Item gadget_core;

	public static Item boy_igniter;
	public static Item boy_propellant;
	public static Item boy_bullet;
	public static Item boy_target;
	public static Item boy_shielding;
	
	public static Item man_explosive;
	public static Item man_explosive8;
	public static Item man_igniter;
	public static Item man_core;

	public static Item mike_core;
	public static Item mike_deut;
	public static Item mike_cooling_unit;

	public static Item tsar_core;

	public static Item fleija_igniter;
	public static Item fleija_propellant;
	public static Item fleija_core;

	public static Item solinium_igniter;
	public static Item solinium_propellant;
	public static Item solinium_core;

	public static Item n2_charge;
	

	public static Item battery_generic;
	public static Item battery_advanced;
	public static Item battery_lithium;
	public static Item battery_schrabidium;
	public static Item battery_spark;
	public static Item battery_creative;

	public static Item battery_red_cell;
	public static Item battery_red_cell_6;
	public static Item battery_red_cell_24;
	public static Item battery_advanced_cell;
	public static Item battery_advanced_cell_4;
	public static Item battery_advanced_cell_12;
	public static Item battery_lithium_cell;
	public static Item battery_lithium_cell_3;
	public static Item battery_lithium_cell_6;
	public static Item battery_schrabidium_cell;
	public static Item battery_schrabidium_cell_2;
	public static Item battery_schrabidium_cell_4;
	public static Item battery_spark_cell_6;
	public static Item battery_spark_cell_25;
	public static Item battery_spark_cell_100;
	public static Item battery_spark_cell_1000;
	public static Item battery_spark_cell_2500;
	public static Item battery_spark_cell_10000;
	public static Item battery_spark_cell_power;

	public static Item battery_su;
	public static Item battery_su_l;
	public static Item battery_potato;
	public static Item battery_potatos;
	public static Item battery_steam;
	public static Item battery_steam_large;
	public static Item fusion_core;
	public static Item fusion_core_infinite;
	public static Item energy_core;
	public static Item fuse;
	public static Item redcoil_capacitor;
	public static Item titanium_filter;
	public static Item screwdriver;
	public static Item overfuse;
	public static Item dynosphere_base;
	public static Item dynosphere_desh;
	public static Item dynosphere_desh_charged;
	public static Item dynosphere_schrabidium;
	public static Item dynosphere_schrabidium_charged;
	public static Item dynosphere_euphemium;
	public static Item dynosphere_euphemium_charged;
	public static Item dynosphere_dineutronium;
	public static Item dynosphere_dineutronium_charged;
	
	public static Item tank_waste;

	public static Item factory_core_titanium;
	public static Item factory_core_advanced;

	public static Item upgrade_template;
	public static Item upgrade_speed_1;
	public static Item upgrade_speed_2;
	public static Item upgrade_speed_3;
	public static Item upgrade_effect_1;
	public static Item upgrade_effect_2;
	public static Item upgrade_effect_3;
	public static Item upgrade_power_1;
	public static Item upgrade_power_2;
	public static Item upgrade_power_3;
	public static Item upgrade_fortune_1;
	public static Item upgrade_fortune_2;
	public static Item upgrade_fortune_3;
	public static Item upgrade_afterburn_1;
	public static Item upgrade_afterburn_2;
	public static Item upgrade_afterburn_3;

	public static Item ingot_euphemium;
	public static Item nugget_euphemium;
	public static Item rod_quad_euphemium;
	public static Item euphemium_helmet;
	public static Item euphemium_plate;
	public static Item euphemium_legs;
	public static Item euphemium_boots;
	public static Item apple_euphemium;
	public static Item watch;
	public static Item euphemium_stopper;

	public static Item goggles;
	public static Item gas_mask;
	public static Item gas_mask_m65;
	public static Item oxy_mask;
	
	public static Item t45_helmet;
	public static Item t45_plate;
	public static Item t45_legs;
	public static Item t45_boots;

	public static Item chainsaw;

	public static Item schrabidium_helmet;
	public static Item schrabidium_plate;
	public static Item schrabidium_legs;
	public static Item schrabidium_boots;
	public static Item titanium_helmet;
	public static Item titanium_plate;
	public static Item titanium_legs;
	public static Item titanium_boots;
	public static Item steel_helmet;
	public static Item steel_plate;
	public static Item steel_legs;
	public static Item steel_boots;
	public static Item alloy_helmet;
	public static Item alloy_plate;
	public static Item alloy_legs;
	public static Item alloy_boots;
	public static Item cmb_helmet;
	public static Item cmb_plate;
	public static Item cmb_legs;
	public static Item cmb_boots;
	public static Item paa_plate;
	public static Item paa_legs;
	public static Item paa_boots;
	public static Item asbestos_helmet;
	public static Item asbestos_plate;
	public static Item asbestos_legs;
	public static Item asbestos_boots;

	public static Item australium_iii;
	public static Item australium_iv;
	public static Item australium_v;

	public static Item jetpack_boost;
	public static Item jetpack_break;
	public static Item jetpack_fly;
	public static Item jetpack_vector;
	
	public static Item jackt;
	public static Item jackt2;

	public static Item schrabidium_sword;
	public static Item schrabidium_pickaxe;
	public static Item schrabidium_axe;
	public static Item schrabidium_shovel;
	public static Item schrabidium_hoe;
	public static Item titanium_sword;
	public static Item titanium_pickaxe;
	public static Item titanium_axe;
	public static Item titanium_shovel;
	public static Item titanium_hoe;
	public static Item steel_sword;
	public static Item steel_pickaxe;
	public static Item steel_axe;
	public static Item steel_shovel;
	public static Item steel_hoe;
	public static Item alloy_sword;
	public static Item alloy_pickaxe;
	public static Item alloy_axe;
	public static Item alloy_shovel;
	public static Item alloy_hoe;
	public static Item cmb_sword;
	public static Item cmb_pickaxe;
	public static Item cmb_axe;
	public static Item cmb_shovel;
	public static Item cmb_hoe;
	public static Item elec_sword;
	public static Item elec_pickaxe;
	public static Item elec_axe;
	public static Item elec_shovel;
	public static Item desh_sword;
	public static Item desh_pickaxe;
	public static Item desh_axe;
	public static Item desh_shovel;
	public static Item desh_hoe;

	public static Item matchstick;
	
	public static Item mask_of_infamy;

	public static Item schrabidium_hammer;
	public static Item shimmer_sledge;
	public static Item shimmer_axe;
	public static Item bottle_opener;

	public static Item crowbar;

	public static Item multitool_hit;
	public static Item multitool_dig;
	public static Item multitool_silk;
	public static Item multitool_ext;
	public static Item multitool_miner;
	public static Item multitool_beam;
	public static Item multitool_sky;
	public static Item multitool_mega;
	public static Item multitool_joule;
	public static Item multitool_decon;

	public static Item saw;
	public static Item bat;
	public static Item bat_nail;
	public static Item golf_club;
	public static Item pipe_rusty;
	public static Item pipe_lead;
	public static Item reer_graar;

	public static Item crystal_horn;
	public static Item crystal_charred;

	public static Item hazmat_helmet;
	public static Item hazmat_plate;
	public static Item hazmat_legs;
	public static Item hazmat_boots;

	public static Item hazmat_paa_helmet;
	public static Item hazmat_paa_plate;
	public static Item hazmat_paa_legs;
	public static Item hazmat_paa_boots;

	public static Item wand;
	public static Item wand_s;
	public static Item wand_d;

	public static Item cape_test;
	public static Item cape_radiation;
	public static Item cape_gasmask;
	public static Item cape_schrabidium;
	public static Item cape_hbm;
	public static Item cape_dafnik;
	public static Item cape_lpkukin;
	public static Item cape_vertice;
	public static Item cape_codered_;

	public static Item nuke_starter_kit;
	public static Item nuke_advanced_kit;
	public static Item nuke_commercially_kit;
	public static Item nuke_electric_kit;
	public static Item gadget_kit;
	public static Item boy_kit;
	public static Item man_kit;
	public static Item mike_kit;
	public static Item tsar_kit;
	public static Item multi_kit;
	public static Item grenade_kit;
	public static Item fleija_kit;
	public static Item prototype_kit;
	public static Item missile_kit;
	public static Item t45_kit;
	public static Item euphemium_kit;
	public static Item solinium_kit;
	
	public static Item clip_revolver_iron;
	public static Item clip_revolver;
	public static Item clip_revolver_gold;
	public static Item clip_revolver_lead;
	public static Item clip_revolver_schrabidium;
	public static Item clip_revolver_cursed;
	public static Item clip_revolver_nightmare;
	public static Item clip_revolver_nightmare2;
	public static Item clip_revolver_pip;
	public static Item clip_rpg;
	public static Item clip_stinger;
	public static Item clip_fatman;
	public static Item clip_mirv;
	public static Item clip_bf;
	public static Item clip_mp40;
	public static Item clip_uzi;
	public static Item clip_uboinik;
	public static Item clip_lever_action;
	public static Item clip_bolt_action;
	public static Item clip_osipr;
	public static Item clip_immolator;
	public static Item clip_cryolator;
	public static Item clip_mp;
	public static Item clip_xvl1456;
	public static Item clip_emp;
	public static Item clip_jack;
	public static Item clip_spark;
	public static Item clip_hp;
	public static Item clip_euthanasia;
	public static Item clip_defabricator;

	public static Item ammo_container;

	public static Item igniter;
	public static Item detonator;
	public static Item detonator_multi;
	public static Item detonator_laser;
	public static Item crate_caller;
	public static Item bomb_caller;
	public static Item meteor_remote;
	public static Item remote;
	public static Item turret_control;
	public static Item turret_chip;
	public static Item turret_biometry;
	public static Item chopper;

	public static Item turret_light_ammo;
	public static Item turret_heavy_ammo;
	public static Item turret_rocket_ammo;
	public static Item turret_flamer_ammo;
	public static Item turret_tau_ammo;
	public static Item turret_spitfire_ammo;
	public static Item turret_cwis_ammo;
	public static Item turret_cheapo_ammo;

	public static Item bucket_mud;
	public static Item bucket_acid;
	public static Item bucket_toxic;
	
	public static Item record_lc;
	public static Item record_ss;
	public static Item record_vc;

	public static Item polaroid;
	public static Item glitch;
	public static Item letter;
	public static Item book_secret;
	public static Item burnt_bark;

	public static Item smoke1;
	public static Item smoke2;
	public static Item smoke3;
	public static Item smoke4;
	public static Item smoke5;
	public static Item smoke6;
	public static Item smoke7;
	public static Item smoke8;
	public static Item b_smoke1;
	public static Item b_smoke2;
	public static Item b_smoke3;
	public static Item b_smoke4;
	public static Item b_smoke5;
	public static Item b_smoke6;
	public static Item b_smoke7;
	public static Item b_smoke8;
	public static Item d_smoke1;
	public static Item d_smoke2;
	public static Item d_smoke3;
	public static Item d_smoke4;
	public static Item d_smoke5;
	public static Item d_smoke6;
	public static Item d_smoke7;
	public static Item d_smoke8;
	public static Item spill1;
	public static Item spill2;
	public static Item spill3;
	public static Item spill4;
	public static Item spill5;
	public static Item spill6;
	public static Item spill7;
	public static Item spill8;
	public static Item gas1;
	public static Item gas2;
	public static Item gas3;
	public static Item gas4;
	public static Item gas5;
	public static Item gas6;
	public static Item gas7;
	public static Item gas8;
	public static Item chlorine1;
	public static Item chlorine2;
	public static Item chlorine3;
	public static Item chlorine4;
	public static Item chlorine5;
	public static Item chlorine6;
	public static Item chlorine7;
	public static Item chlorine8;
	public static Item pc1;
	public static Item pc2;
	public static Item pc3;
	public static Item pc4;
	public static Item pc5;
	public static Item pc6;
	public static Item pc7;
	public static Item pc8;
	public static Item cloud1;
	public static Item cloud2;
	public static Item cloud3;
	public static Item cloud4;
	public static Item cloud5;
	public static Item cloud6;
	public static Item cloud7;
	public static Item cloud8;
	public static Item orange1;
	public static Item orange2;
	public static Item orange3;
	public static Item orange4;
	public static Item orange5;
	public static Item orange6;
	public static Item orange7;
	public static Item orange8;
	public static Item gasflame1;
	public static Item gasflame2;
	public static Item gasflame3;
	public static Item gasflame4;
	public static Item gasflame5;
	public static Item gasflame6;
	public static Item gasflame7;
	public static Item gasflame8;
	public static Item energy_ball;
	public static Item discharge;
	public static Item empblast;
	public static Item flame_1;
	public static Item flame_2;
	public static Item flame_3;
	public static Item flame_4;
	public static Item flame_5;
	public static Item flame_6;
	public static Item flame_7;
	public static Item flame_8;
	public static Item flame_9;
	public static Item flame_10;
	public static Item ln2_1;
	public static Item ln2_2;
	public static Item ln2_3;
	public static Item ln2_4;
	public static Item ln2_5;
	public static Item ln2_6;
	public static Item ln2_7;
	public static Item ln2_8;
	public static Item ln2_9;
	public static Item ln2_10;
	public static Item nothing;
	public static Item void_anim;

	public static final int guiID_item_folder = 99;
	public static final int guiID_item_designator = 100;
	public static final int guiID_item_sat_interface = 101;

	public static Item mysteryshovel;
	public static Item memory;

	public static void initializeItem()
	{			
		redstone_sword = new RedstoneSword(ToolMaterial.STONE).setUnlocalizedName("redstone_sword").setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":redstone_sword");
		big_sword = new BigSword(ToolMaterial.EMERALD).setUnlocalizedName("big_sword").setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":big_sword");

		test_helmet = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 5, 0).setUnlocalizedName("test_helmet").setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_helmet");
		test_chestplate = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 5, 1).setUnlocalizedName("test_chestplate").setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_chestplate");
		test_leggings = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 5, 2).setUnlocalizedName("test_leggings").setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_leggings");
		test_boots = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 5, 3).setUnlocalizedName("test_boots").setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_boots");
		
		test_nuke_igniter = new Item().setUnlocalizedName("test_nuke_igniter").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_igniter");
		test_nuke_propellant = new Item().setUnlocalizedName("test_nuke_propellant").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_propellant");
		test_nuke_tier1_shielding = new Item().setUnlocalizedName("test_nuke_tier1_shielding").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier1_shielding");
		test_nuke_tier2_shielding = new Item().setUnlocalizedName("test_nuke_tier2_shielding").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier2_shielding");
		test_nuke_tier1_bullet = new Item().setUnlocalizedName("test_nuke_tier1_bullet").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier1_bullet");
		test_nuke_tier2_bullet = new Item().setUnlocalizedName("test_nuke_tier2_bullet").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier2_bullet");
		test_nuke_tier1_target = new Item().setUnlocalizedName("test_nuke_tier1_target").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier1_target");
		test_nuke_tier2_target = new Item().setUnlocalizedName("test_nuke_tier2_target").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":test_nuke_tier2_target");
		
		ingot_uranium = new ItemRadioactive().setUnlocalizedName("ingot_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_uranium");
		ingot_u235 = new ItemRadioactive().setUnlocalizedName("ingot_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_u235");
		ingot_u238 = new ItemRadioactive().setUnlocalizedName("ingot_u238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_u238");
		ingot_u238m2 = new ItemUnstable(350, 200).setUnlocalizedName("ingot_u238m2").setCreativeTab(null).setTextureName(RefStrings.MODID + ":ingot_u238m2");
		ingot_plutonium = new ItemRadioactive().setUnlocalizedName("ingot_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ingot_pu238 = new ItemRadioactive().setUnlocalizedName("ingot_pu238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu238");
		ingot_pu239 = new ItemRadioactive().setUnlocalizedName("ingot_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu239");
		ingot_pu240 = new ItemRadioactive().setUnlocalizedName("ingot_pu240").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu240");
		ingot_neptunium = new ItemCustomLore().setUnlocalizedName("ingot_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_neptunium");
		ingot_titanium = new Item().setUnlocalizedName("ingot_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_titanium");
		sulfur = new Item().setUnlocalizedName("sulfur").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sulfur");

		ingot_uranium_fuel = new ItemRadioactive().setUnlocalizedName("ingot_uranium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_uranium_fuel");
		ingot_plutonium_fuel = new ItemRadioactive().setUnlocalizedName("ingot_plutonium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_plutonium_fuel");
		ingot_mox_fuel = new ItemRadioactive().setUnlocalizedName("ingot_mox_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_mox_fuel");
		ingot_schrabidium_fuel = new ItemRadioactive().setUnlocalizedName("ingot_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidium_fuel");
		nugget_uranium_fuel = new ItemRadioactive().setUnlocalizedName("nugget_uranium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_uranium_fuel");
		nugget_plutonium_fuel = new ItemRadioactive().setUnlocalizedName("nugget_plutonium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_plutonium_fuel");
		nugget_mox_fuel = new ItemRadioactive().setUnlocalizedName("nugget_mox_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mox_fuel");
		nugget_schrabidium_fuel = new ItemRadioactive().setUnlocalizedName("nugget_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_schrabidium_fuel");
		ingot_advanced_alloy = new Item().setUnlocalizedName("ingot_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_advanced_alloy");

		niter = new Item().setUnlocalizedName("niter").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":salpeter");
		ingot_copper = new Item().setUnlocalizedName("ingot_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_copper");
		ingot_red_copper = new Item().setUnlocalizedName("ingot_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_red_copper");
		ingot_tungsten = new Item().setUnlocalizedName("ingot_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_tungsten");
		ingot_aluminium = new Item().setUnlocalizedName("ingot_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_aluminium");
		fluorite = new Item().setUnlocalizedName("fluorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fluorite");
		ingot_beryllium = new Item().setUnlocalizedName("ingot_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_beryllium");
		ingot_steel = new Item().setUnlocalizedName("ingot_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_steel");
		plate_steel = new Item().setUnlocalizedName("plate_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_steel");
		plate_iron = new Item().setUnlocalizedName("plate_iron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_iron");
		ingot_lead = new Item().setUnlocalizedName("ingot_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_lead");
		plate_lead = new Item().setUnlocalizedName("plate_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_lead");
		ingot_schrabidium = new ItemCustomLore().setUnlocalizedName("ingot_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidium");
		plate_schrabidium = new ItemCustomLore().setUnlocalizedName("plate_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_schrabidium");
		plate_copper = new Item().setUnlocalizedName("plate_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_copper");
		plate_gold = new Item().setUnlocalizedName("plate_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_gold");
		plate_advanced_alloy = new Item().setUnlocalizedName("plate_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_advanced_alloy");
		lithium = new Item().setUnlocalizedName("lithium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":lithium");
		wire_advanced_alloy = new Item().setUnlocalizedName("wire_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_advanced_alloy");
		coil_advanced_alloy = new Item().setUnlocalizedName("coil_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_advanced_alloy");
		coil_advanced_torus = new Item().setUnlocalizedName("coil_advanced_torus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_advanced_torus");
		ingot_magnetized_tungsten = new Item().setUnlocalizedName("ingot_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_magnetized_tungsten");
		ingot_combine_steel = new ItemCustomLore().setUnlocalizedName("ingot_combine_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_combine_steel");
		plate_mixed = new Item().setUnlocalizedName("plate_mixed").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_mixed");
		plate_paa = new ItemCustomLore().setUnlocalizedName("plate_paa").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_paa");
		board_copper = new Item().setUnlocalizedName("board_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":board_copper");
		bolt_dura_steel = new Item().setUnlocalizedName("bolt_dura_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bolt_dura_steel");
		pipes_steel = new Item().setUnlocalizedName("pipes_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pipes_steel");
		drill_titanium = new Item().setUnlocalizedName("drill_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":drill_titanium");
		plate_dalekanium = new Item().setUnlocalizedName("plate_dalekanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_dalekanium");
		plate_euphemium = new ItemCustomLore().setUnlocalizedName("plate_euphemium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_euphemium");
		bolt_tungsten = new Item().setUnlocalizedName("bolt_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bolt_tungsten");
		bolt_compound = new Item().setUnlocalizedName("bolt_compound").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bolt_compound");
		plate_polymer = new Item().setUnlocalizedName("plate_polymer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_polymer");
		plate_dineutronium = new Item().setUnlocalizedName("plate_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_dineutronium");
		plate_desh = new Item().setUnlocalizedName("plate_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_desh");
		ingot_solinium = new ItemCustomLore().setUnlocalizedName("ingot_solinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_solinium");
		nugget_solinium = new ItemCustomLore().setUnlocalizedName("nugget_solinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_solinium");
		photo_panel = new Item().setUnlocalizedName("photo_panel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":photo_panel");
		sat_base = new Item().setUnlocalizedName("sat_base").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_base");
		thruster_nuclear = new Item().setUnlocalizedName("thruster_nuclear").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thruster_nuclear");

		ingot_dura_steel = new ItemCustomLore().setUnlocalizedName("ingot_dura_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_dura_steel");
		ingot_polymer = new ItemCustomLore().setUnlocalizedName("ingot_polymer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_polymer");
		ingot_desh = new ItemCustomLore().setUnlocalizedName("ingot_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_desh");
		nugget_desh = new ItemCustomLore().setUnlocalizedName("nugget_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_desh");
		ingot_dineutronium = new ItemCustomLore().setUnlocalizedName("ingot_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_dineutronium");
		nugget_dineutronium = new ItemCustomLore().setUnlocalizedName("nugget_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_dineutronium");
		powder_dineutronium = new ItemCustomLore().setUnlocalizedName("powder_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_dineutronium");
		ingot_starmetal = new ItemCustomLore().setUnlocalizedName("ingot_starmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_starmetal");
		ingot_saturnite = new ItemCustomLore().setUnlocalizedName("ingot_saturnite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_saturnite");
		plate_saturnite = new ItemCustomLore().setUnlocalizedName("plate_saturnite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_saturnite");

		ingot_lanthanium = new ItemCustomLore().setUnlocalizedName("ingot_lanthanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_lanthanium");
		ingot_actinium = new ItemCustomLore().setUnlocalizedName("ingot_actinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_actinium");
		
		solid_fuel = new Item().setUnlocalizedName("solid_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel");

		ingot_australium = new ItemCustomLore().setUnlocalizedName("ingot_australium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_australium");
		ingot_weidanium = new ItemCustomLore().setUnlocalizedName("ingot_weidanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_weidanium");
		ingot_reiium = new ItemCustomLore().setUnlocalizedName("ingot_reiium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_reiium");
		ingot_unobtainium = new ItemCustomLore().setUnlocalizedName("ingot_unobtainium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_unobtainium");
		ingot_daffergon = new ItemCustomLore().setUnlocalizedName("ingot_daffergon").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_daffergon");
		ingot_verticium = new ItemCustomLore().setUnlocalizedName("ingot_verticium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_verticium");
		nugget_australium = new ItemCustomLore().setUnlocalizedName("nugget_australium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_australium");
		nugget_weidanium = new ItemCustomLore().setUnlocalizedName("nugget_weidanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_weidanium");
		nugget_reiium = new ItemCustomLore().setUnlocalizedName("nugget_reiium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_reiium");
		nugget_unobtainium = new ItemCustomLore().setUnlocalizedName("nugget_unobtainium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_unobtainium");
		nugget_daffergon = new ItemCustomLore().setUnlocalizedName("nugget_daffergon").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_daffergon");
		nugget_verticium = new ItemCustomLore().setUnlocalizedName("nugget_verticium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_verticium");

		nugget_uranium = new ItemRadioactive().setUnlocalizedName("nugget_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_uranium");
		nugget_u235 = new ItemRadioactive().setUnlocalizedName("nugget_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_u235");
		nugget_u238 = new ItemRadioactive().setUnlocalizedName("nugget_u238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_u238");
		nugget_plutonium = new ItemRadioactive().setUnlocalizedName("nugget_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		nugget_pu238 = new ItemRadioactive().setUnlocalizedName("nugget_pu238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu238");
		nugget_pu239 = new ItemRadioactive().setUnlocalizedName("nugget_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu239");
		nugget_pu240 = new ItemRadioactive().setUnlocalizedName("nugget_pu240").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu240");
		nugget_neptunium = new ItemRadioactive().setUnlocalizedName("nugget_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_neptunium");
		plate_titanium = new Item().setUnlocalizedName("plate_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_titanium");
		plate_aluminium = new Item().setUnlocalizedName("plate_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_aluminium");
		wire_red_copper = new Item().setUnlocalizedName("wire_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_red_copper");
		wire_tungsten = new ItemCustomLore().setUnlocalizedName("wire_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_tungsten");
		neutron_reflector = new Item().setUnlocalizedName("neutron_reflector").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":neutron_reflector");
		nugget_lead = new Item().setUnlocalizedName("nugget_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_lead");
		nugget_schrabidium = new ItemCustomLore().setUnlocalizedName("nugget_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_schrabidium");
		nugget_beryllium = new Item().setUnlocalizedName("nugget_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_beryllium");
		hazmat_cloth = new Item().setUnlocalizedName("hazmat_cloth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth");
		asbestos_cloth = new Item().setUnlocalizedName("asbestos_cloth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":asbestos_cloth");
		filter_coal = new Item().setUnlocalizedName("filter_coal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":filter_coal");
		ingot_hes = new ItemRadioactive().setUnlocalizedName("ingot_hes").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_hes");
		ingot_les = new ItemRadioactive().setUnlocalizedName("ingot_les").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_les");
		nugget_hes = new ItemRadioactive().setUnlocalizedName("nugget_hes").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_hes");
		nugget_les = new ItemRadioactive().setUnlocalizedName("nugget_les").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_les");
		plate_combine_steel = new Item().setUnlocalizedName("plate_combine_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_combine_steel");

		powder_lead = new Item().setUnlocalizedName("powder_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lead");
		powder_neptunium = new ItemCustomLore().setUnlocalizedName("powder_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neptunium");
		powder_schrabidium = new ItemCustomLore().setUnlocalizedName("powder_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_schrabidium");
		powder_aluminium = new Item().setUnlocalizedName("powder_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_aluminium");
		powder_beryllium = new Item().setUnlocalizedName("powder_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_beryllium");
		powder_copper = new Item().setUnlocalizedName("powder_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_copper");
		powder_gold = new Item().setUnlocalizedName("powder_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_gold");
		powder_iron = new Item().setUnlocalizedName("powder_iron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_iron");
		powder_titanium = new Item().setUnlocalizedName("powder_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_titanium");
		powder_tungsten = new Item().setUnlocalizedName("powder_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tungsten");
		powder_uranium = new ItemRadioactive().setUnlocalizedName("powder_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_uranium");
		powder_plutonium = new ItemRadioactive().setUnlocalizedName("powder_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_plutonium");
		dust = new ItemCustomLore().setUnlocalizedName("dust").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":dust");
		powder_advanced_alloy = new Item().setUnlocalizedName("powder_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_advanced_alloy");
		powder_coal = new Item().setUnlocalizedName("powder_coal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_coal");
		powder_combine_steel = new Item().setUnlocalizedName("powder_combine_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_combine_steel");
		powder_diamond = new Item().setUnlocalizedName("powder_diamond").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_diamond");
		powder_emerald = new Item().setUnlocalizedName("powder_emerald").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_emerald");
		powder_lapis = new Item().setUnlocalizedName("powder_lapis").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lapis");
		powder_quartz = new Item().setUnlocalizedName("powder_quartz").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_quartz");
		powder_magnetized_tungsten = new Item().setUnlocalizedName("powder_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_magnetized_tungsten");
		powder_red_copper = new Item().setUnlocalizedName("powder_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_red_copper");
		powder_steel = new Item().setUnlocalizedName("powder_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_steel");
		powder_lithium = new Item().setUnlocalizedName("powder_lithium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lithium");
		powder_power = new ItemCustomLore().setUnlocalizedName("powder_power").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_energy_alt");
		powder_iodine = new ItemCustomLore().setUnlocalizedName("powder_iodine").setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_iodine");
		powder_thorium = new ItemCustomLore().setUnlocalizedName("powder_thorium").setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_thorium");
		powder_neodymium = new ItemCustomLore().setUnlocalizedName("powder_neodymium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neodymium");
		powder_astatine = new ItemCustomLore().setUnlocalizedName("powder_astatine").setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_astatine");
		powder_caesium = new ItemCustomLore().setUnlocalizedName("powder_caesium").setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_caesium");
		powder_australium = new ItemCustomLore().setUnlocalizedName("powder_australium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_australium");
		powder_weidanium = new ItemCustomLore().setUnlocalizedName("powder_weidanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_weidanium");
		powder_reiium = new ItemCustomLore().setUnlocalizedName("powder_reiium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_reiium");
		powder_unobtainium = new ItemCustomLore().setUnlocalizedName("powder_unobtainium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_unobtainium");
		powder_daffergon = new ItemCustomLore().setUnlocalizedName("powder_daffergon").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_daffergon");
		powder_verticium = new ItemCustomLore().setUnlocalizedName("powder_verticium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_verticium");
		powder_strontium = new ItemCustomLore().setUnlocalizedName("powder_strontium").setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_strontium");
		powder_cobalt = new ItemCustomLore().setUnlocalizedName("powder_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cobalt");
		powder_bromine = new ItemCustomLore().setUnlocalizedName("powder_bromine").setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_bromine");
		powder_niobium = new ItemCustomLore().setUnlocalizedName("powder_niobium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_niobium");
		powder_tennessine = new ItemCustomLore().setUnlocalizedName("powder_tennessine").setCreativeTab(null).setTextureName(RefStrings.MODID + ":powder_tennessine");
		powder_cerium = new ItemCustomLore().setUnlocalizedName("powder_cerium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cerium");
		powder_dura_steel = new ItemCustomLore().setUnlocalizedName("powder_dura_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_dura_steel");
		powder_polymer = new ItemCustomLore().setUnlocalizedName("powder_polymer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_polymer");
		powder_euphemium = new ItemCustomLore().setUnlocalizedName("powder_euphemium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_euphemium");
		powder_meteorite = new Item().setUnlocalizedName("powder_meteorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_meteorite");
		powder_lanthanium = new ItemCustomLore().setUnlocalizedName("powder_lanthanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lanthanium");
		powder_actinium = new ItemCustomLore().setUnlocalizedName("powder_actinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_actinium");
		powder_desh_mix = new Item().setUnlocalizedName("powder_desh_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh_mix");
		powder_nitan_mix = new Item().setUnlocalizedName("powder_nitan_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_nitan_mix");
		powder_spark_mix = new Item().setUnlocalizedName("powder_spark_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_spark_mix");
		powder_desh = new Item().setUnlocalizedName("powder_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh");
		powder_lithium_tiny = new Item().setUnlocalizedName("powder_lithium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lithium_tiny");
		powder_neodymium_tiny = new Item().setUnlocalizedName("powder_neodymium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neodymium_tiny");
		powder_cobalt_tiny = new Item().setUnlocalizedName("powder_cobalt_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cobalt_tiny");
		powder_niobium_tiny = new Item().setUnlocalizedName("powder_niobium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_niobium_tiny");
		powder_cerium_tiny = new Item().setUnlocalizedName("powder_cerium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cerium_tiny");
		powder_lanthanium_tiny = new Item().setUnlocalizedName("powder_lanthanium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lanthanium_tiny");
		powder_actinium_tiny = new Item().setUnlocalizedName("powder_actinium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_actinium_tiny");
		powder_meteorite_tiny = new Item().setUnlocalizedName("powder_meteorite_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_meteorite_tiny");
		powder_yellowcake = new ItemRadioactive().setUnlocalizedName("powder_yellowcake").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_yellowcake");
		powder_magic = new Item().setUnlocalizedName("powder_magic").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_magic");

		fragment_neodymium = new Item().setUnlocalizedName("fragment_neodymium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_neodymium");
		fragment_cobalt = new Item().setUnlocalizedName("fragment_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_cobalt");
		fragment_niobium = new Item().setUnlocalizedName("fragment_niobium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_niobium");
		fragment_cerium = new Item().setUnlocalizedName("fragment_cerium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_cerium");
		fragment_lanthanium = new Item().setUnlocalizedName("fragment_lanthanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_lanthanium");
		fragment_actinium = new Item().setUnlocalizedName("fragment_actinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_actinium");
		fragment_meteorite = new Item().setUnlocalizedName("fragment_meteorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_meteorite");
		
		biomass = new Item().setUnlocalizedName("biomass").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":biomass");
		biomass_compressed = new Item().setUnlocalizedName("biomass_compressed").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":biomass_compressed");

		coil_copper = new Item().setUnlocalizedName("coil_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_copper");
		coil_copper_torus = new Item().setUnlocalizedName("coil_copper_torus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_copper_torus");
		coil_tungsten = new Item().setUnlocalizedName("coil_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_tungsten");
		tank_steel = new Item().setUnlocalizedName("tank_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":tank_steel");
		motor = new Item().setUnlocalizedName("motor").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":motor");
		centrifuge_element = new Item().setUnlocalizedName("centrifuge_element").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":centrifuge_element");
		centrifuge_tower = new Item().setUnlocalizedName("centrifuge_tower").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":centrifuge_tower");
		reactor_core = new Item().setUnlocalizedName("reactor_core").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":reactor_core");
		rtg_unit = new Item().setUnlocalizedName("rtg_unit").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":rtg_unit");
		thermo_unit_empty = new Item().setUnlocalizedName("thermo_unit_empty").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thermo_unit_empty");
		thermo_unit_endo= new Item().setUnlocalizedName("thermo_unit_endo").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thermo_unit_endo");
		thermo_unit_exo = new Item().setUnlocalizedName("thermo_unit_exo").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thermo_unit_exo");
		levitation_unit = new Item().setUnlocalizedName("levitation_unit").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":levitation_unit");
		wire_aluminium = new Item().setUnlocalizedName("wire_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_aluminium");
		wire_copper = new Item().setUnlocalizedName("wire_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_copper");
		wire_gold = new Item().setUnlocalizedName("wire_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_gold");
		wire_schrabidium = new ItemCustomLore().setUnlocalizedName("wire_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_schrabidium");
		wire_magnetized_tungsten = new Item().setUnlocalizedName("wire_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_magnetized_tungsten");
		coil_magnetized_tungsten = new Item().setUnlocalizedName("coil_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_magnetized_tungsten");
		coil_gold = new Item().setUnlocalizedName("coil_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_gold");
		coil_gold_torus = new Item().setUnlocalizedName("coil_gold_torus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_gold_torus");
		magnet_dee = new Item().setUnlocalizedName("magnet_dee").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":magnet_dee");
		magnet_circular = new Item().setUnlocalizedName("magnet_circular").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":magnet_circular");
		cyclotron_tower = new Item().setUnlocalizedName("cyclotron_tower").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":cyclotron_tower");
		pellet_coal = new Item().setUnlocalizedName("pellet_coal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_coal");
		component_limiter = new Item().setUnlocalizedName("component_limiter").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":component_limiter");
		component_emitter = new Item().setUnlocalizedName("component_emitter").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":component_emitter");
		
		cap_aluminium = new Item().setUnlocalizedName("cap_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":cap_aluminium");      
		hull_small_steel = new Item().setUnlocalizedName("hull_small_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_small_steel");
		hull_small_aluminium = new Item().setUnlocalizedName("hull_small_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_small_aluminium");
		hull_big_steel = new Item().setUnlocalizedName("hull_big_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_big_steel");
		hull_big_aluminium = new Item().setUnlocalizedName("hull_big_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_big_aluminium");
		hull_big_titanium = new Item().setUnlocalizedName("hull_big_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hull_big_titanium");
		fins_flat = new Item().setUnlocalizedName("fins_flat").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_flat");
		fins_small_steel = new Item().setUnlocalizedName("fins_small_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_small_steel");
		fins_big_steel = new Item().setUnlocalizedName("fins_big_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_big_steel");
		fins_tri_steel = new Item().setUnlocalizedName("fins_tri_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_tri_steel");
		fins_quad_titanium = new Item().setUnlocalizedName("fins_quad_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_quad_titanium");
        sphere_steel = new Item().setUnlocalizedName("sphere_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sphere_steel");
		pedestal_steel = new Item().setUnlocalizedName("pedestal_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pedestal_steel");
		dysfunctional_reactor = new Item().setUnlocalizedName("dysfunctional_reactor").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":dysfunctional_reactor");
		rotor_steel = new Item().setUnlocalizedName("rotor_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":rotor_steel");
		generator_steel = new Item().setUnlocalizedName("generator_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":generator_steel");
		blade_titanium = new Item().setUnlocalizedName("blade_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":blade_titanium");
		turbine_titanium = new Item().setUnlocalizedName("turbine_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":turbine_titanium");
		generator_front = new Item().setUnlocalizedName("generator_front").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":generator_front");
		blade_tungsten = new Item().setUnlocalizedName("blade_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":blade_tungsten");
		turbine_tungsten = new Item().setUnlocalizedName("turbine_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":turbine_tungsten");

		toothpicks = new Item().setUnlocalizedName("toothpicks").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":toothpicks");
		ducttape = new Item().setUnlocalizedName("ducttape").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ducttape");
		catalyst_clay = new Item().setUnlocalizedName("catalyst_clay").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":catalyst_clay");
		
		warhead_generic_small = new Item().setUnlocalizedName("warhead_generic_small").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_generic_small");
		warhead_generic_medium = new Item().setUnlocalizedName("warhead_generic_medium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_generic_medium");
		warhead_generic_large = new Item().setUnlocalizedName("warhead_generic_large").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_generic_large");
		warhead_incendiary_small = new Item().setUnlocalizedName("warhead_incendiary_small").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_incendiary_small");
		warhead_incendiary_medium = new Item().setUnlocalizedName("warhead_incendiary_medium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_incendiary_medium");
		warhead_incendiary_large = new Item().setUnlocalizedName("warhead_incendiary_large").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_incendiary_large");
		warhead_cluster_small = new Item().setUnlocalizedName("warhead_cluster_small").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_cluster_small");
		warhead_cluster_medium = new Item().setUnlocalizedName("warhead_cluster_medium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_cluster_medium");
		warhead_cluster_large = new Item().setUnlocalizedName("warhead_cluster_large").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_cluster_large");
		warhead_buster_small = new Item().setUnlocalizedName("warhead_buster_small").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_buster_small");
		warhead_buster_medium = new Item().setUnlocalizedName("warhead_buster_medium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_buster_medium");
		warhead_buster_large = new Item().setUnlocalizedName("warhead_buster_large").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_buster_large");
		warhead_nuclear = new Item().setUnlocalizedName("warhead_nuclear").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_nuclear");
		warhead_mirvlet = new Item().setUnlocalizedName("warhead_mirvlet").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_mirvlet");
		warhead_mirv = new Item().setUnlocalizedName("warhead_mirv").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_mirv");
		warhead_thermo_endo = new Item().setUnlocalizedName("warhead_thermo_endo").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_thermo_endo");
		warhead_thermo_exo = new Item().setUnlocalizedName("warhead_thermo_exo").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_thermo_exo");
		
		fuel_tank_small = new Item().setUnlocalizedName("fuel_tank_small").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fuel_tank_small");
		fuel_tank_medium = new Item().setUnlocalizedName("fuel_tank_medium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fuel_tank_medium");
		fuel_tank_large = new Item().setUnlocalizedName("fuel_tank_large").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fuel_tank_large");
		
		thruster_small = new Item().setUnlocalizedName("thruster_small").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thruster_small");
		thruster_medium = new Item().setUnlocalizedName("thruster_medium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thruster_medium");
		thruster_large = new Item().setUnlocalizedName("thruster_large").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thruster_large");

		sat_head_mapper = new Item().setUnlocalizedName("sat_head_mapper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_head_mapper");
		sat_head_scanner = new Item().setUnlocalizedName("sat_head_scanner").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_head_scanner");
		sat_head_radar = new Item().setUnlocalizedName("sat_head_radar").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_head_radar");
		sat_head_laser = new Item().setUnlocalizedName("sat_head_laser").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_head_laser");
		sat_head_resonator = new Item().setUnlocalizedName("sat_head_resonator").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_head_resonator");

		chopper_head = new Item().setUnlocalizedName("chopper_head").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chopper_head");
		chopper_gun = new Item().setUnlocalizedName("chopper_gun").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chopper_gun");
		chopper_torso = new Item().setUnlocalizedName("chopper_torso").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chopper_torso");
		chopper_tail = new Item().setUnlocalizedName("chopper_tail").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chopper_tail");
		chopper_wing = new Item().setUnlocalizedName("chopper_wing").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chopper_wing");
		chopper_blades = new Item().setUnlocalizedName("chopper_blades").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chopper_blades");
		combine_scrap = new Item().setUnlocalizedName("combine_scrap").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":combine_scrap");

		shimmer_head = new Item().setUnlocalizedName("shimmer_head").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":shimmer_head_original");
		shimmer_axe_head = new Item().setUnlocalizedName("shimmer_axe_head").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":shimmer_axe_head");
		shimmer_handle = new Item().setUnlocalizedName("shimmer_handle").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":shimmer_handle");

		telepad = new Item().setUnlocalizedName("telepad").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":telepad");
		entanglement_kit = new ItemCustomLore().setUnlocalizedName("entanglement_kit").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":entanglement_kit");
		
		circuit_raw = new Item().setUnlocalizedName("circuit_raw").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_raw");
		circuit_aluminium = new Item().setUnlocalizedName("circuit_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_aluminium");
		circuit_copper = new Item().setUnlocalizedName("circuit_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_copper");
		circuit_red_copper = new Item().setUnlocalizedName("circuit_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_red_copper");
		circuit_gold = new Item().setUnlocalizedName("circuit_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_gold");
		circuit_schrabidium = new ItemCustomLore().setUnlocalizedName("circuit_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_schrabidium");
		circuit_targeting_tier1 = new Item().setUnlocalizedName("circuit_targeting_tier1").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier1");
		circuit_targeting_tier2 = new Item().setUnlocalizedName("circuit_targeting_tier2").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier2");
		circuit_targeting_tier3 = new Item().setUnlocalizedName("circuit_targeting_tier3").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier3");
		circuit_targeting_tier4 = new Item().setUnlocalizedName("circuit_targeting_tier4").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier4");
		circuit_targeting_tier5 = new Item().setUnlocalizedName("circuit_targeting_tier5").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier5");
		circuit_targeting_tier6 = new Item().setUnlocalizedName("circuit_targeting_tier6").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit_targeting_tier6");
		mechanism_revolver_1 = new Item().setUnlocalizedName("mechanism_revolver_1").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_1");
		mechanism_revolver_2 = new Item().setUnlocalizedName("mechanism_revolver_2").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_3");
		mechanism_rifle_1 = new Item().setUnlocalizedName("mechanism_rifle_1").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_2");
		mechanism_rifle_2 = new Item().setUnlocalizedName("mechanism_rifle_2").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_4");
		mechanism_launcher_1 = new Item().setUnlocalizedName("mechanism_launcher_1").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_5");
		mechanism_launcher_2 = new Item().setUnlocalizedName("mechanism_launcher_2").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_6");
		mechanism_special = new Item().setUnlocalizedName("mechanism_special").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":mechanism_7");
		
		wiring_red_copper = new ItemWiring().setUnlocalizedName("wiring_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wiring_red_copper");

		pellet_rtg = new ItemCustomLore().setUnlocalizedName("pellet_rtg").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg");
		pellet_rtg_weak = new ItemCustomLore().setUnlocalizedName("pellet_rtg_weak").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg_weak");
		tritium_deuterium_cake = new ItemCustomLore().setUnlocalizedName("tritium_deuterium_cake").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":tritium_deuterium_cake");
		
		piston_selenium = new Item().setUnlocalizedName("piston_selenium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":piston_selenium");

		crystal_energy = new ItemCustomLore().setUnlocalizedName("crystal_energy").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":crystal_energy");
		pellet_coolant = new ItemCustomLore().setUnlocalizedName("pellet_coolant").setMaxDamage(41400).setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_coolant");

		rune_blank = new ItemCustomLore().setUnlocalizedName("rune_blank").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_blank");
		rune_isa = new ItemCustomLore().setUnlocalizedName("rune_isa").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_isa");
		rune_dagaz = new ItemCustomLore().setUnlocalizedName("rune_dagaz").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_dagaz");
		rune_hagalaz = new ItemCustomLore().setUnlocalizedName("rune_hagalaz").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_hagalaz");
		rune_jera = new ItemCustomLore().setUnlocalizedName("rune_jera").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_jera");
		rune_thurisaz = new ItemCustomLore().setUnlocalizedName("rune_thurisaz").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_thurisaz");

		ams_catalyst_blank = new Item().setUnlocalizedName("ams_catalyst_blank").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_blank");
		ams_catalyst_aluminium = 	new ItemCatalyst(0xCCCCCC, 1000000, 1.15F, 	0.85F, 	1.15F).setUnlocalizedName("ams_catalyst_aluminium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_aluminium");
		ams_catalyst_beryllium = 	new ItemCatalyst(0x97978B, 0, 		1.25F, 	0.95F, 	1.05F).setUnlocalizedName("ams_catalyst_beryllium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_beryllium");
		ams_catalyst_caesium = 		new ItemCatalyst(0x6400FF, 2500000, 1.00F, 	0.85F, 	1.15F).setUnlocalizedName("ams_catalyst_caesium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_caesium");
		ams_catalyst_cerium = 		new ItemCatalyst(0x1D3FFF, 1000000, 1.15F, 	1.15F, 	0.85F).setUnlocalizedName("ams_catalyst_cerium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_cerium");
		ams_catalyst_cobalt = 		new ItemCatalyst(0x789BBE, 0, 		1.25F, 	1.05F, 	0.95F).setUnlocalizedName("ams_catalyst_cobalt").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_cobalt");
		ams_catalyst_copper = 		new ItemCatalyst(0xAADE29, 0, 		1.25F, 	1.00F, 	1.00F).setUnlocalizedName("ams_catalyst_copper").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_copper");
		ams_catalyst_dineutronium = new ItemCatalyst(0x334077, 2500000, 1.00F, 	1.15F, 	0.85F).setUnlocalizedName("ams_catalyst_dineutronium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_dineutronium");
		ams_catalyst_euphemium = 	new ItemCatalyst(0xFF9CD2, 2500000, 1.00F, 	1.00F, 	1.00F).setUnlocalizedName("ams_catalyst_euphemium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_euphemium");
		ams_catalyst_iron = 		new ItemCatalyst(0xFF7E22, 1000000, 1.15F, 	0.95F, 	1.05F).setUnlocalizedName("ams_catalyst_iron").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_iron");
		ams_catalyst_lithium = 		new ItemCatalyst(0xFF2727, 0, 		1.25F, 	0.85F, 	1.15F).setUnlocalizedName("ams_catalyst_lithium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_lithium");
		ams_catalyst_niobium = 		new ItemCatalyst(0x3BF1B6, 1000000, 1.15F, 	1.05F, 	0.95F).setUnlocalizedName("ams_catalyst_niobium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_niobium");
		ams_catalyst_schrabidium = 	new ItemCatalyst(0x32FFFF, 2500000, 1.00F, 	1.05F, 	0.95F).setUnlocalizedName("ams_catalyst_schrabidium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_schrabidium");
		ams_catalyst_strontium = 	new ItemCatalyst(0xDD0D35, 1000000, 1.15F, 	1.00F, 	1.00F).setUnlocalizedName("ams_catalyst_strontium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_strontium");
		ams_catalyst_thorium = 		new ItemCatalyst(0x653B22, 2500000, 1.00F, 	0.95F, 	1.05F).setUnlocalizedName("ams_catalyst_thorium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_thorium");
		ams_catalyst_tungsten = 	new ItemCatalyst(0xF5FF48, 0, 		1.25F, 	1.15F, 	0.85F).setUnlocalizedName("ams_catalyst_tungsten").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ams_catalyst_tungsten");
		
		cell_empty = new ItemCell().setUnlocalizedName("cell_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":cell_empty");
		cell_uf6 = new Item().setUnlocalizedName("cell_uf6").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_uf6");
		cell_puf6 = new Item().setUnlocalizedName("cell_puf6").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_puf6");
		cell_antimatter = new ItemDrop().setUnlocalizedName("cell_antimatter").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_antimatter");
		cell_deuterium = new Item().setUnlocalizedName("cell_deuterium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_deuterium");
		cell_tritium = new ItemRadioactive().setUnlocalizedName("cell_tritium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_tritium");
		cell_sas3 = new ItemCustomLore().setUnlocalizedName("cell_sas3").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_sas3");
		cell_anti_schrabidium = new ItemDrop().setUnlocalizedName("cell_anti_schrabidium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_anti_schrabidium");
		singularity = new ItemDrop().setUnlocalizedName("singularity").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity");
		singularity_counter_resonant = new ItemDrop().setUnlocalizedName("singularity_counter_resonant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_alt");
		singularity_super_heated = new ItemDrop().setUnlocalizedName("singularity_super_heated").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_5");
		black_hole = new ItemDrop().setUnlocalizedName("black_hole").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_4");
		singularity_spark = new ItemDrop().setUnlocalizedName("singularity_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_spark_alt");
		pellet_antimatter = new ItemDrop().setUnlocalizedName("pellet_antimatter").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":pellet_antimatter");
		crystal_xen = new ItemDrop().setUnlocalizedName("crystal_xen").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":crystal_xen");
		inf_water = new Item().setUnlocalizedName("inf_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":inf_water");

		stamp_stone_flat = new ItemBlades(5).setUnlocalizedName("stamp_stone_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_flat");
		stamp_stone_plate = new ItemBlades(5).setUnlocalizedName("stamp_stone_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_plate");
		stamp_stone_wire = new ItemBlades(5).setUnlocalizedName("stamp_stone_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_wire");
		stamp_stone_circuit = new ItemBlades(5).setUnlocalizedName("stamp_stone_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_circuit");
		stamp_iron_flat = new ItemBlades(25).setUnlocalizedName("stamp_iron_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_flat");
		stamp_iron_plate = new ItemBlades(25).setUnlocalizedName("stamp_iron_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_plate");
		stamp_iron_wire = new ItemBlades(25).setUnlocalizedName("stamp_iron_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_wire");
		stamp_iron_circuit = new ItemBlades(25).setUnlocalizedName("stamp_iron_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_circuit");
		stamp_steel_flat = new ItemBlades(50).setUnlocalizedName("stamp_steel_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_flat");
		stamp_steel_plate = new ItemBlades(50).setUnlocalizedName("stamp_steel_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_plate");
		stamp_steel_wire = new ItemBlades(50).setUnlocalizedName("stamp_steel_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_wire");
		stamp_steel_circuit = new ItemBlades(50).setUnlocalizedName("stamp_steel_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_circuit");
		stamp_titanium_flat = new ItemBlades(65).setUnlocalizedName("stamp_titanium_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_flat");
		stamp_titanium_plate = new ItemBlades(65).setUnlocalizedName("stamp_titanium_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_plate");
		stamp_titanium_wire = new ItemBlades(65).setUnlocalizedName("stamp_titanium_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_wire");
		stamp_titanium_circuit = new ItemBlades(65).setUnlocalizedName("stamp_titanium_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_circuit");
		stamp_obsidian_flat = new ItemBlades(100).setUnlocalizedName("stamp_obsidian_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_flat");
		stamp_obsidian_plate = new ItemBlades(100).setUnlocalizedName("stamp_obsidian_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_plate");
		stamp_obsidian_wire = new ItemBlades(100).setUnlocalizedName("stamp_obsidian_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_wire");
		stamp_obsidian_circuit = new ItemBlades(100).setUnlocalizedName("stamp_obsidian_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_circuit");
		stamp_schrabidium_flat = new ItemBlades(1024).setUnlocalizedName("stamp_schrabidium_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_schrabidium_flat");
		stamp_schrabidium_plate = new ItemBlades(1024).setUnlocalizedName("stamp_schrabidium_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_schrabidium_plate");
		stamp_schrabidium_wire = new ItemBlades(1024).setUnlocalizedName("stamp_schrabidium_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_schrabidium_wire");
		stamp_schrabidium_circuit = new ItemBlades(1024).setUnlocalizedName("stamp_schrabidium_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_schrabidium_circuit");

		blades_aluminium = new ItemBlades(1 * 1200).setUnlocalizedName("blades_aluminium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_aluminium");
		blades_gold = new ItemBlades(5 * 1200).setUnlocalizedName("blades_gold").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_gold");
		blades_iron = new ItemBlades(10 * 1200).setUnlocalizedName("blades_iron").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_iron");
		blades_steel = new ItemBlades(20 * 1200).setUnlocalizedName("blades_steel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_steel");
		blades_titanium = new ItemBlades(35 * 1200).setUnlocalizedName("blades_titanium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_titanium");
		blades_advanced_alloy = new ItemBlades(50 * 1200).setUnlocalizedName("blades_advanced_alloy").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_advanced_alloy");
		blades_combine_steel = new ItemBlades(90 * 1200).setUnlocalizedName("blades_combine_steel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_combine_steel");
		blades_schrabidium = new ItemBlades(120 * 1200).setUnlocalizedName("blades_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_schrabidium");

		part_lithium = new Item().setUnlocalizedName("part_lithium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_lithium");
		part_beryllium = new Item().setUnlocalizedName("part_beryllium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_beryllium");
		part_carbon = new Item().setUnlocalizedName("part_carbon").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_carbon");
		part_copper = new Item().setUnlocalizedName("part_copper").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_copper");
		part_plutonium = new Item().setUnlocalizedName("part_plutonium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_plutonium");
		
		thermo_element = new Item().setUnlocalizedName("thermo_element").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":thermo_element");
		limiter = new Item().setUnlocalizedName("limiter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":limiter");

		canister_empty = new ItemCustomLore().setUnlocalizedName("canister_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":canister_empty");
		canister_smear = new ItemCustomLore().setUnlocalizedName("canister_smear").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_smear");
		canister_canola = new ItemCustomLore().setUnlocalizedName("canister_canola").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_canola");
		canister_oil = new ItemCustomLore().setUnlocalizedName("canister_oil").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_oil");
		canister_fuel = new ItemCustomLore().setUnlocalizedName("canister_fuel").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_fuel");
		canister_kerosene = new ItemCustomLore().setUnlocalizedName("canister_kerosene").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_kerosene");
		canister_reoil = new ItemCustomLore().setUnlocalizedName("canister_reoil").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_reoil");
		canister_petroil = new ItemCustomLore().setUnlocalizedName("canister_petroil").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_petroil");
		canister_napalm = new ItemCustomLore().setUnlocalizedName("canister_napalm").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_napalm");
		canister_NITAN = new ItemCustomLore().setUnlocalizedName("canister_NITAN").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_superfuel");
		canister_heavyoil = new ItemCustomLore().setUnlocalizedName("canister_heavyoil").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_heavyoil");
		canister_bitumen = new ItemCustomLore().setUnlocalizedName("canister_bitumen").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_bitumen");
		canister_heatingoil = new ItemCustomLore().setUnlocalizedName("canister_heatingoil").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_heatingoil");
		canister_naphtha = new ItemCustomLore().setUnlocalizedName("canister_naphtha").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_naphtha");
		canister_lightoil = new ItemCustomLore().setUnlocalizedName("canister_lightoil").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_lightoil");
		canister_biofuel = new ItemCustomLore().setUnlocalizedName("canister_biofuel").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_biofuel");
		gas_empty = new Item().setUnlocalizedName("gas_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":gas_empty");
		gas_full = new Item().setUnlocalizedName("gas_full").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.gas_empty).setTextureName(RefStrings.MODID + ":gas_full");
		gas_petroleum = new Item().setUnlocalizedName("gas_petroleum").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.gas_empty).setTextureName(RefStrings.MODID + ":gas_petroleum");
		gas_biogas = new Item().setUnlocalizedName("gas_biogas").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.gas_empty).setTextureName(RefStrings.MODID + ":gas_biogas");
		
		tank_waste = new ItemTankWaste().setUnlocalizedName("tank_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab);

		syringe_empty = new Item().setUnlocalizedName("syringe_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_empty");
		syringe_antidote = new ItemSyringe().setUnlocalizedName("syringe_antidote").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_antidote");
		syringe_poison = new ItemSyringe().setUnlocalizedName("syringe_poison").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_poison");
		syringe_awesome = new ItemSyringe().setUnlocalizedName("syringe_awesome").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_awesome");
		syringe_metal_empty = new Item().setUnlocalizedName("syringe_metal_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_empty");
		syringe_metal_stimpak = new ItemSyringe().setUnlocalizedName("syringe_metal_stimpak").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_stimpak");
		syringe_metal_medx = new ItemSyringe().setUnlocalizedName("syringe_metal_medx").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_medx");
		syringe_metal_psycho = new ItemSyringe().setUnlocalizedName("syringe_metal_psycho").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_psycho");
		syringe_metal_super = new ItemSyringe().setUnlocalizedName("syringe_metal_super").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_super");
		syringe_taint = new ItemSyringe().setUnlocalizedName("syringe_taint").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_taint");
		med_bag = new ItemSyringe().setUnlocalizedName("med_bag").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_bag");
		radaway = new ItemSyringe().setUnlocalizedName("radaway").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":radaway");
		pill_iodine = new ItemPill(0).setUnlocalizedName("pill_iodine").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pill_iodine");
		plan_c = new ItemPill(0).setUnlocalizedName("plan_c").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":plan_c");
		stealth_boy = new ItemStarterKit().setUnlocalizedName("stealth_boy").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":stealth_boy");

		can_empty = new Item().setUnlocalizedName("can_empty").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_empty");
		can_smart = new ItemEnergy().setUnlocalizedName("can_smart").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_smart");
		can_creature = new ItemEnergy().setUnlocalizedName("can_creature").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_creature");
		can_redbomb = new ItemEnergy().setUnlocalizedName("can_redbomb").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_redbomb");
		can_mrsugar = new ItemEnergy().setUnlocalizedName("can_mrsugar").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_mrsugar");
		can_overcharge = new ItemEnergy().setUnlocalizedName("can_overcharge").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_overcharge");
		can_luna = new ItemEnergy().setUnlocalizedName("can_luna").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_luna");
		bottle_empty = new Item().setUnlocalizedName("bottle_empty").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_empty");
		bottle_nuka = new ItemEnergy().setUnlocalizedName("bottle_nuka").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_nuka");
		bottle_cherry = new ItemEnergy().setUnlocalizedName("bottle_cherry").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_cherry");
		bottle_quantum = new ItemEnergy().setUnlocalizedName("bottle_quantum").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_quantum");
		bottle_sparkle = new ItemEnergy().setUnlocalizedName("bottle_sparkle").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_sparkle");
		bottle2_empty = new Item().setUnlocalizedName("bottle2_empty").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle2_empty");
		bottle2_korl = new ItemEnergy().setUnlocalizedName("bottle2_korl").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle2_korl");
		bottle2_fritz = new ItemEnergy().setUnlocalizedName("bottle2_fritz").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle2_fritz");
		bottle2_korl_special = new ItemEnergy().setUnlocalizedName("bottle2_korl_special").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle2_korl");
		bottle2_fritz_special = new ItemEnergy().setUnlocalizedName("bottle2_fritz_special").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle2_fritz");
		bottle2_sunset = new ItemEnergy().setUnlocalizedName("bottle2_sunset").setContainerItem(ModItems.bottle2_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle2_sunset");
		chocolate_milk = new ItemEnergy().setUnlocalizedName("chocolate_milk").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":chocolate_milk");
		cap_nuka = new Item().setUnlocalizedName("cap_nuka").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_nuka");
		cap_quantum = new Item().setUnlocalizedName("cap_quantum").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_quantum");
		cap_sparkle = new Item().setUnlocalizedName("cap_sparkle").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_sparkle");
		cap_korl = new Item().setUnlocalizedName("cap_korl").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_korl");
		cap_fritz = new Item().setUnlocalizedName("cap_fritz").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_fritz");
		cap_sunset = new Item().setUnlocalizedName("cap_sunset").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_sunset");
		ring_pull = new Item().setUnlocalizedName("ring_pull").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":ring_pull");

		rod_empty = new Item().setUnlocalizedName("rod_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_empty");
		rod_uranium = new ItemCustomLore().setUnlocalizedName("rod_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium");
		rod_u235 = new ItemCustomLore().setUnlocalizedName("rod_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_u235");
		rod_u238 = new ItemCustomLore().setUnlocalizedName("rod_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_u238");
		rod_plutonium = new ItemCustomLore().setUnlocalizedName("rod_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium");
		rod_pu238 = new ItemCustomLore().setUnlocalizedName("rod_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_pu238");
		rod_pu239 = new ItemCustomLore().setUnlocalizedName("rod_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_pu239");
		rod_pu240 = new ItemCustomLore().setUnlocalizedName("rod_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_pu240");
		rod_neptunium = new ItemCustomLore().setUnlocalizedName("rod_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_neptunium");
		rod_lead = new Item().setUnlocalizedName("rod_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_lead");
		rod_schrabidium = new ItemCustomLore().setUnlocalizedName("rod_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium");
		rod_solinium = new ItemCustomLore().setUnlocalizedName("rod_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_solinium");
		rod_euphemium = new ItemCustomLore().setUnlocalizedName("rod_euphemium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_euphemium");
		rod_australium = new ItemCustomLore().setUnlocalizedName("rod_australium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_australium");
		rod_weidanium = new ItemCustomLore().setUnlocalizedName("rod_weidanium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_weidanium");
		rod_reiium = new ItemCustomLore().setUnlocalizedName("rod_reiium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_reiium");
		rod_unobtainium = new ItemCustomLore().setUnlocalizedName("rod_unobtainium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_unobtainium");
		rod_daffergon = new ItemCustomLore().setUnlocalizedName("rod_daffergon").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_daffergon");
		rod_verticium = new ItemCustomLore().setUnlocalizedName("rod_verticium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_verticium");

		rod_dual_empty = new Item().setUnlocalizedName("rod_dual_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_dual_empty");
		rod_dual_uranium = new ItemCustomLore().setUnlocalizedName("rod_dual_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium");
		rod_dual_u235 = new ItemCustomLore().setUnlocalizedName("rod_dual_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_u235");
		rod_dual_u238 = new ItemCustomLore().setUnlocalizedName("rod_dual_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_u238");
		rod_dual_plutonium = new ItemCustomLore().setUnlocalizedName("rod_dual_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium");
		rod_dual_pu238 = new ItemCustomLore().setUnlocalizedName("rod_dual_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_pu238");
		rod_dual_pu239 = new ItemCustomLore().setUnlocalizedName("rod_dual_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_pu239");
		rod_dual_pu240 = new ItemCustomLore().setUnlocalizedName("rod_dual_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_pu240");
		rod_dual_neptunium = new ItemCustomLore().setUnlocalizedName("rod_dual_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_neptunium");
		rod_dual_lead = new Item().setUnlocalizedName("rod_dual_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_lead");
		rod_dual_schrabidium = new ItemCustomLore().setUnlocalizedName("rod_dual_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium");
		rod_dual_solinium = new ItemCustomLore().setUnlocalizedName("rod_dual_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_solinium");

		rod_quad_empty = new Item().setUnlocalizedName("rod_quad_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_quad_empty");
		rod_quad_uranium = new ItemCustomLore().setUnlocalizedName("rod_quad_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium");
		rod_quad_u235 = new ItemCustomLore().setUnlocalizedName("rod_quad_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_u235");
		rod_quad_u238 = new ItemCustomLore().setUnlocalizedName("rod_quad_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_u238");
		rod_quad_plutonium = new ItemCustomLore().setUnlocalizedName("rod_quad_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium");
		rod_quad_pu238 = new ItemCustomLore().setUnlocalizedName("rod_quad_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_pu238");
		rod_quad_pu239 = new ItemCustomLore().setUnlocalizedName("rod_quad_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_pu239");
		rod_quad_pu240 = new ItemCustomLore().setUnlocalizedName("rod_quad_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_pu240");
		rod_quad_neptunium = new ItemCustomLore().setUnlocalizedName("rod_quad_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_neptunium");
		rod_quad_lead = new Item().setUnlocalizedName("rod_quad_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_lead");
		rod_quad_schrabidium = new ItemCustomLore().setUnlocalizedName("rod_quad_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium");
		rod_quad_solinium = new ItemCustomLore().setUnlocalizedName("rod_quad_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_solinium");

		rod_uranium_fuel = new ItemFuelRod(100000, 15).setUnlocalizedName("rod_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium_fuel");
		rod_dual_uranium_fuel = new ItemFuelRod(100000, 30).setUnlocalizedName("rod_dual_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium_fuel");
		rod_quad_uranium_fuel = new ItemFuelRod(100000, 60).setUnlocalizedName("rod_quad_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium_fuel");
		rod_plutonium_fuel = new ItemFuelRod(75000, 25).setUnlocalizedName("rod_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium_fuel");
		rod_dual_plutonium_fuel = new ItemFuelRod(75000, 50).setUnlocalizedName("rod_dual_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium_fuel");
		rod_quad_plutonium_fuel = new ItemFuelRod(75000, 100).setUnlocalizedName("rod_quad_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium_fuel");
		rod_mox_fuel = new ItemFuelRod(150000, 10).setUnlocalizedName("rod_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_mox_fuel");
		rod_dual_mox_fuel = new ItemFuelRod(150000, 20).setUnlocalizedName("rod_dual_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_mox_fuel");
		rod_quad_mox_fuel = new ItemFuelRod(150000, 40).setUnlocalizedName("rod_quad_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_mox_fuel");
		rod_schrabidium_fuel = new ItemFuelRod(500000, 250).setUnlocalizedName("rod_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium_fuel");
		rod_dual_schrabidium_fuel = new ItemFuelRod(500000, 500).setUnlocalizedName("rod_dual_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium_fuel");
		rod_quad_schrabidium_fuel = new ItemFuelRod(500000, 1000).setUnlocalizedName("rod_quad_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium_fuel");

		rod_water = new ItemCustomLore().setUnlocalizedName("rod_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_water");
		rod_dual_water = new ItemCustomLore().setUnlocalizedName("rod_dual_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_water");
		rod_quad_water = new ItemCustomLore().setUnlocalizedName("rod_quad_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_water");

		rod_coolant = new ItemCustomLore().setUnlocalizedName("rod_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_coolant");
		rod_dual_coolant = new ItemCustomLore().setUnlocalizedName("rod_dual_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_coolant");
		rod_quad_coolant = new ItemCustomLore().setUnlocalizedName("rod_quad_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_coolant");

		rod_lithium = new ItemCustomLore().setUnlocalizedName("rod_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_lithium");
		rod_dual_lithium = new ItemCustomLore().setUnlocalizedName("rod_dual_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_lithium");
		rod_quad_lithium = new ItemCustomLore().setUnlocalizedName("rod_quad_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_lithium");

		rod_tritium = new ItemCustomLore().setUnlocalizedName("rod_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_tritium");
		rod_dual_tritium = new ItemCustomLore().setUnlocalizedName("rod_dual_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_tritium");
		rod_quad_tritium = new ItemCustomLore().setUnlocalizedName("rod_quad_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_tritium");

		trinitite = new ItemCustomLore().setUnlocalizedName("trinitite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":trinitite_new");
		nuclear_waste = new ItemCustomLore().setUnlocalizedName("nuclear_waste").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste");
		scrap = new Item().setUnlocalizedName("scrap").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scrap");
		rod_uranium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium_fuel_depleted");
		rod_dual_uranium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_dual_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium_fuel_depleted");
		rod_quad_uranium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_quad_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium_fuel_depleted");
		rod_plutonium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium_fuel_depleted");
		rod_dual_plutonium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_dual_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium_fuel_depleted");
		rod_quad_plutonium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_quad_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium_fuel_depleted");
		rod_mox_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_mox_fuel_depleted");
		rod_dual_mox_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_dual_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_mox_fuel_depleted");
		rod_quad_mox_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_quad_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_mox_fuel_depleted");
		rod_schrabidium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium_fuel_depleted");
		rod_dual_schrabidium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_dual_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium_fuel_depleted");
		rod_quad_schrabidium_fuel_depleted = new ItemCustomLore().setUnlocalizedName("rod_quad_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium_fuel_depleted");
		rod_waste = new ItemCustomLore().setUnlocalizedName("rod_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_waste");
		rod_dual_waste = new ItemCustomLore().setUnlocalizedName("rod_dual_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_waste");
		rod_quad_waste = new ItemCustomLore().setUnlocalizedName("rod_quad_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_waste");

		pellet_cluster = new ItemCustomLore().setUnlocalizedName("pellet_cluster").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_cluster");
		powder_fire = new ItemCustomLore().setUnlocalizedName("powder_fire").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_fire");
		powder_ice = new ItemCustomLore().setUnlocalizedName("powder_ice").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_ice");
		powder_poison = new ItemCustomLore().setUnlocalizedName("powder_poison").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_poison");
		powder_thermite = new ItemCustomLore().setUnlocalizedName("powder_thermite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_thermite");
		pellet_gas = new ItemCustomLore().setUnlocalizedName("pellet_gas").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_gas");
		magnetron = new ItemCustomLore().setUnlocalizedName("magnetron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":magnetron_alt");
		pellet_buckshot = new Item().setUnlocalizedName("pellet_buckshot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_lead");

		pellet_schrabidium = new WatzFuel(50000, 140000, 0.975F, 200, 1.05F, 1.05F).setUnlocalizedName("pellet_schrabidium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_schrabidium").setMaxStackSize(1);
		pellet_hes = new WatzFuel(108000, 65000, 1F, 85, 1, 1.025F).setUnlocalizedName("pellet_hes").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_hes").setMaxStackSize(1);
		pellet_mes = new WatzFuel(216000, 23000, 1.025F, 50, 1, 1F).setUnlocalizedName("pellet_mes").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_mes").setMaxStackSize(1);
		pellet_les = new WatzFuel(432000, 7000, 1.05F, 15, 1, 0.975F).setUnlocalizedName("pellet_les").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_les").setMaxStackSize(1);
		pellet_beryllium = new WatzFuel(864000, 50, 1.05F, 0, 0.95F, 1.025F).setUnlocalizedName("pellet_beryllium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_beryllium").setMaxStackSize(1);
		pellet_neptunium = new WatzFuel(216000, 3000, 1.1F, 25, 1.1F, 1.005F).setUnlocalizedName("pellet_neptunium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_neptunium").setMaxStackSize(1);
		pellet_lead = new WatzFuel(1728000, 0, 0.95F, 0, 0.95F, 0.95F).setUnlocalizedName("pellet_lead").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_lead").setMaxStackSize(1);
		pellet_advanced = new WatzFuel(216000, 1000, 1.1F, 0, 0.995F, 0.99F).setUnlocalizedName("pellet_advanced").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_advanced").setMaxStackSize(1);

		designator = new ItemDesingator().setUnlocalizedName("designator").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator");
		designator_range = new ItemDesingatorRange().setUnlocalizedName("designator_range").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator_range_alt");
		designator_manual = new ItemDesingatorManual().setUnlocalizedName("designator_manual").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator_manual");
		missile_assembly = new Item().setUnlocalizedName("missile_assembly").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":missile_assembly");
		missile_generic = new Item().setUnlocalizedName("missile_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_generic");
		missile_anti_ballistic = new Item().setUnlocalizedName("missile_anti_ballistic").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_anti_ballistic");
		missile_incendiary = new Item().setUnlocalizedName("missile_incendiary").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_incendiary");
		missile_cluster = new Item().setUnlocalizedName("missile_cluster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_cluster");
		missile_buster = new Item().setUnlocalizedName("missile_buster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_buster");
		missile_strong = new Item().setUnlocalizedName("missile_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_strong");
		missile_incendiary_strong = new Item().setUnlocalizedName("missile_incendiary_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_incendiary_strong");
		missile_cluster_strong = new Item().setUnlocalizedName("missile_cluster_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_cluster_strong");
		missile_buster_strong = new Item().setUnlocalizedName("missile_buster_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_buster_strong");
		missile_burst = new Item().setUnlocalizedName("missile_burst").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_burst");
		missile_inferno = new Item().setUnlocalizedName("missile_inferno").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_inferno");
		missile_rain = new Item().setUnlocalizedName("missile_rain").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_rain");
		missile_drill = new Item().setUnlocalizedName("missile_drill").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_drill");
		missile_nuclear = new Item().setUnlocalizedName("missile_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_nuclear");
		missile_nuclear_cluster = new Item().setUnlocalizedName("missile_nuclear_cluster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_nuclear_cluster");
		missile_endo = new Item().setUnlocalizedName("missile_endo").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_endo");
		missile_exo = new Item().setUnlocalizedName("missile_exo").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_exo");
		missile_doomsday = new Item().setUnlocalizedName("missile_doomsday").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_doomsday");
		missile_taint = new Item().setUnlocalizedName("missile_taint").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_taint");
		missile_micro = new Item().setUnlocalizedName("missile_micro").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_micro");
		missile_bhole = new Item().setUnlocalizedName("missile_bhole").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_bhole");
		missile_schrabidium = new Item().setUnlocalizedName("missile_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_schrabidium");
		missile_emp = new Item().setUnlocalizedName("missile_emp").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_emp");
		missile_carrier = new Item().setUnlocalizedName("missile_carrier").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_carrier");
		sat_mapper = new ItemSatChip().setUnlocalizedName("sat_mapper").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_mapper");
		sat_scanner = new ItemSatChip().setUnlocalizedName("sat_scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_scanner");
		sat_radar = new ItemSatChip().setUnlocalizedName("sat_radar").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_radar");
		sat_laser = new ItemSatChip().setUnlocalizedName("sat_laser").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_laser");
		sat_foeq = new ItemSatChip().setUnlocalizedName("sat_foeq").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_foeq");
		sat_resonator = new ItemSatChip().setUnlocalizedName("sat_resonator").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_resonator");
		sat_chip = new ItemSatChip().setUnlocalizedName("sat_chip").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_chip");
		sat_interface = new ItemSatInterface().setUnlocalizedName("sat_interface").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_interface");

		gun_rpg = new GunRpg().setUnlocalizedName("gun_rpg").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_rpg_new");
		gun_rpg_ammo = new Item().setUnlocalizedName("gun_rpg_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_rpg_ammo_alt");
		gun_stinger = new GunStinger().setUnlocalizedName("gun_stinger").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_stinger");
		gun_skystinger = new GunStinger().setUnlocalizedName("gun_skystinger").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_skystinger");
		gun_stinger_ammo = new Item().setUnlocalizedName("gun_stinger_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_stinger_ammo");
		gun_revolver_ammo = new Item().setUnlocalizedName("gun_revolver_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_ammo");
		gun_revolver = new GunRevolver(gun_revolver_ammo, 10, 25, false, false).setMaxDamage(500).setUnlocalizedName("gun_revolver").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver");
		gun_revolver_saturnite = new GunRevolver(gun_revolver_ammo, 20, 35, false, false).setMaxDamage(2500).setUnlocalizedName("gun_revolver_saturnite").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_saturnite");
		gun_revolver_iron_ammo = new Item().setUnlocalizedName("gun_revolver_iron_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_iron_ammo");
		gun_revolver_iron = new GunRevolver(gun_revolver_iron_ammo, 5, 15, false, false).setMaxDamage(100).setUnlocalizedName("gun_revolver_iron").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_iron");
		gun_revolver_gold_ammo = new Item().setUnlocalizedName("gun_revolver_gold_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_gold_ammo");
		gun_revolver_gold = new GunRevolver(gun_revolver_gold_ammo, 20, 30, false, false).setMaxDamage(1000).setUnlocalizedName("gun_revolver_gold").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_gold");
		gun_revolver_lead_ammo = new Item().setUnlocalizedName("gun_revolver_lead_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_lead_ammo");
		gun_revolver_lead = new GunRevolver(gun_revolver_lead_ammo, 5, 15, false, true).setMaxDamage(250).setUnlocalizedName("gun_revolver_lead").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_lead");
		gun_revolver_schrabidium_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_schrabidium_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_schrabidium_ammo");
		gun_revolver_schrabidium = new GunRevolver(gun_revolver_schrabidium_ammo, 10000, 100000, true, false).setMaxDamage(100000).setUnlocalizedName("gun_revolver_schrabidium").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_schrabidium");
		gun_revolver_cursed_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_cursed_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_cursed_ammo");
		gun_revolver_cursed = new GunRevolver(gun_revolver_cursed_ammo, 25, 40, false, false).setMaxDamage(5000).setUnlocalizedName("gun_revolver_cursed").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_cursed");
		gun_revolver_nightmare_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_nightmare_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nightmare_ammo");
		gun_revolver_nightmare = new GunNightmare().setMaxDamage(6).setUnlocalizedName("gun_revolver_nightmare").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nightmare");
		gun_revolver_nightmare2_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_nightmare2_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nightmare2_ammo");
		gun_revolver_nightmare2 = new GunNightmare().setMaxDamage(6).setUnlocalizedName("gun_revolver_nightmare2").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nightmare2");
		gun_revolver_pip_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_pip_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_pip_ammo");
		gun_revolver_pip = new GunRevolver(gun_revolver_pip_ammo, 25, 35, false, false).setMaxDamage(1000).setUnlocalizedName("gun_revolver_pip").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_pip");
		gun_fatman_ammo = new Item().setUnlocalizedName("gun_fatman_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fatman_ammo");
		gun_fatman = new GunFatman().setMaxDamage(2500).setUnlocalizedName("gun_fatman").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fatman");
		gun_proto = new GunProtoMirv().setMaxDamage(2500).setUnlocalizedName("gun_proto").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fatman");
		gun_mirv_ammo = new Item().setUnlocalizedName("gun_mirv_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mirv_ammo");
		gun_mirv = new GunMIRV().setMaxDamage(2500).setUnlocalizedName("gun_mirv").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mirv");
		gun_bf_ammo = new Item().setUnlocalizedName("gun_bf_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_bf_ammo");
		gun_bf = new GunBaleFlare().setMaxDamage(2500).setUnlocalizedName("gun_bf").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_bf");
		gun_mp40_ammo = new Item().setUnlocalizedName("gun_mp40_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mp40_ammo");
		gun_mp40 = new GunSMG().setUnlocalizedName("gun_mp40").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mp40");
		gun_uzi_ammo = new Item().setUnlocalizedName("gun_uzi_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi_ammo");
		gun_uzi = new GunUZI().setUnlocalizedName("gun_uzi").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi");
		gun_uzi_silencer = new GunUZI().setUnlocalizedName("gun_uzi_silencer").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi_silencer");
		gun_uzi_saturnite = new GunUZI().setUnlocalizedName("gun_uzi_saturnite").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi_saturnite");
		gun_uzi_saturnite_silencer = new GunUZI().setUnlocalizedName("gun_uzi_saturnite_silencer").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi_saturnite_silencer");
		gun_uboinik_ammo = new Item().setUnlocalizedName("gun_uboinik_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik_ammo");
		gun_uboinik = new GunShotgun().setUnlocalizedName("gun_uboinik").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik");
		gun_lever_action_ammo = new Item().setUnlocalizedName("gun_lever_action_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lever_action_ammo");
		gun_lever_action = new GunLeverAction().setUnlocalizedName("gun_lever_action").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lever_action");
		gun_lever_action_dark = new GunLeverAction().setUnlocalizedName("gun_lever_action_dark").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lever_action_dark");
		gun_lever_action_sonata = new GunLeverActionS().setUnlocalizedName("gun_lever_action_sonata").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lever_action_sonata");
		gun_bolt_action_ammo = new Item().setUnlocalizedName("gun_bolt_action_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolt_action_ammo");
		gun_bolt_action = new GunBoltAction().setUnlocalizedName("gun_bolt_action").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolt_action");
		gun_bolt_action_green = new GunBoltAction().setUnlocalizedName("gun_bolt_action_green").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolt_action_green");
		gun_bolt_action_saturnite = new GunBoltAction().setUnlocalizedName("gun_bolt_action_saturnite").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolt_action_saturnite");
		gun_b92_ammo = new GunB92Cell().setUnlocalizedName("gun_b92_ammo").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92_ammo_alt");
		gun_b92 = new GunB92().setUnlocalizedName("gun_b92").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92");
		gun_b93 = new GunB93().setUnlocalizedName("gun_b93").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b93");
		gun_xvl1456_ammo = new Item().setUnlocalizedName("gun_xvl1456_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_xvl1456_ammo");
		gun_xvl1456 = new GunXVL1456().setUnlocalizedName("gun_xvl1456").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_xvl1456");
		gun_osipr_ammo = new Item().setUnlocalizedName("gun_osipr_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_osipr_ammo");
		gun_osipr_ammo2 = new Item().setUnlocalizedName("gun_osipr_ammo2").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_osipr_ammo2");
		gun_osipr = new GunOSIPR().setUnlocalizedName("gun_osipr").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_osipr");
		gun_immolator_ammo = new Item().setUnlocalizedName("gun_immolator_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_immolator_ammo");
		gun_immolator = new GunImmolator().setUnlocalizedName("gun_immolator").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_immolator");
		gun_cryolator_ammo = new Item().setUnlocalizedName("gun_cryolator_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_cryolator_ammo");
		gun_cryolator = new GunCryolator().setUnlocalizedName("gun_cryolator").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_cryolator");
		gun_mp_ammo = new ItemCustomLore().setUnlocalizedName("gun_mp_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_pm_ammo");
		gun_mp = new GunMP().setUnlocalizedName("gun_mp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_pm");
		gun_zomg = new GunZOMG().setUnlocalizedName("gun_zomg").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_zomg");
		gun_revolver_inverted = new GunSuicide().setUnlocalizedName("gun_revolver_inverted").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_inverted");
		gun_emp_ammo = new Item().setUnlocalizedName("gun_emp_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_emp_ammo");
		gun_emp = new GunEMPRay().setUnlocalizedName("gun_emp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_emp");
		gun_jack_ammo = new Item().setUnlocalizedName("gun_jack_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_jack_ammo");
		gun_jack = new GunJack().setUnlocalizedName("gun_jack").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_jack");
		gun_spark_ammo = new Item().setUnlocalizedName("gun_spark_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_spark_ammo");
		gun_spark = new GunSpark().setUnlocalizedName("gun_spark").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_spark");
		gun_hp_ammo = new Item().setUnlocalizedName("gun_hp_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_hp_ammo");
		gun_hp = new GunHP().setUnlocalizedName("gun_hp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_hp");
		gun_euthanasia_ammo = new Item().setUnlocalizedName("gun_euthanasia_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_euthanasia_ammo");
		gun_euthanasia = new GunEuthanasia().setUnlocalizedName("gun_euthanasia").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_euthanasia");
		gun_dash_ammo = new Item().setUnlocalizedName("gun_dash_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_dash_ammo");
		gun_dash = new GunDash().setUnlocalizedName("gun_dash").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_dash");
		gun_twigun_ammo = new Item().setUnlocalizedName("gun_twigun_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_twigun_ammo");
		gun_twigun = new GunEuthanasia().setUnlocalizedName("gun_twigun").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_twigun");
		gun_defabricator_ammo = new Item().setUnlocalizedName("gun_defabricator_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_defabricator_ammo");
		gun_defabricator = new GunDefabricator().setUnlocalizedName("gun_defabricator").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_defabricator");
		gun_super_shotgun = new ItemCustomLore().setUnlocalizedName("gun_super_shotgun").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_super_shotgun");
		gun_moist_nugget = new ItemNugget(3, false).setUnlocalizedName("gun_moist_nugget").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_moist_nugget");
		gun_dampfmaschine = new GunDampfmaschine().setUnlocalizedName("gun_dampfmaschine").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_dampfmaschine");
		
		grenade_generic = new ItemGrenade().setUnlocalizedName("grenade_generic").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_generic");
		grenade_strong = new ItemGrenade().setUnlocalizedName("grenade_strong").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_strong");
		grenade_frag = new ItemGrenade().setUnlocalizedName("grenade_frag").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_frag_alt");
		grenade_fire = new ItemGrenade().setUnlocalizedName("grenade_fire").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_fire_alt");
		grenade_shrapnel = new ItemGrenade().setUnlocalizedName("grenade_shrapnel").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_shrapnel");
		grenade_cluster = new ItemGrenade().setUnlocalizedName("grenade_cluster").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_cluster_alt");
		grenade_flare = new ItemGrenade().setUnlocalizedName("grenade_flare").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_flare_alt");
		grenade_electric = new ItemGrenade().setUnlocalizedName("grenade_electric").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_electric_alt");
		grenade_poison = new ItemGrenade().setUnlocalizedName("grenade_poison").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_poison_alt");
		grenade_gas = new ItemGrenade().setUnlocalizedName("grenade_gas").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_gas_alt");
		grenade_pulse = new ItemGrenade().setUnlocalizedName("grenade_pulse").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_pulse");
		grenade_plasma = new ItemGrenade().setUnlocalizedName("grenade_plasma").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_plasma_alt");
		grenade_tau = new ItemGrenade().setUnlocalizedName("grenade_tau").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_tau_alt");
		grenade_schrabidium = new ItemGrenade().setUnlocalizedName("grenade_schrabidium").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_schrabidium_alt");
		grenade_lemon = new ItemGrenade().setUnlocalizedName("grenade_lemon").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_lemon");
		grenade_gascan = new ItemGrenade().setUnlocalizedName("grenade_gascan").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_gascan");
		grenade_mk2 = new ItemGrenade().setUnlocalizedName("grenade_mk2").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_mk2_alt");
		grenade_aschrab = new ItemGrenade().setUnlocalizedName("grenade_aschrab").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_aschrab");
		grenade_nuke = new ItemGrenade().setUnlocalizedName("grenade_nuke").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_nuke_alt");
		grenade_nuclear = new ItemGrenade().setUnlocalizedName("grenade_nuclear").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_nuclear");
		grenade_zomg = new ItemGrenade().setUnlocalizedName("grenade_zomg").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_zomg");
		grenade_black_hole = new ItemGrenade().setUnlocalizedName("grenade_black_hole").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_black_hole");
		grenade_cloud = new ItemGrenade().setUnlocalizedName("grenade_cloud").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_cloud");
		grenade_pink_cloud = new ItemGrenade().setUnlocalizedName("grenade_pink_cloud").setCreativeTab(null).setTextureName(RefStrings.MODID + ":grenade_pink_cloud");
		ullapool_caber = new WeaponSpecial(MainRegistry.enumToolMaterialSteel).setUnlocalizedName("ullapool_caber").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ullapool_caber");
		
		weaponized_starblaster_cell = new WeaponizedCell().setUnlocalizedName("weaponized_starblaster_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92_ammo_weaponized");

		bomb_waffle = new ItemWaffle(20, false).setUnlocalizedName("bomb_waffle").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bomb_waffle");
		schnitzel_vegan = new ItemSchnitzelVegan(0, true).setUnlocalizedName("schnitzel_vegan").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":schnitzel_vegan");
		cotton_candy = new ItemCottonCandy(5, false).setUnlocalizedName("cotton_candy").setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":cotton_candy");
		apple_schrabidium = new ItemAppleSchrabidium(20, 100, false).setUnlocalizedName("apple_schrabidium").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":apple_schrabidium");
		tem_flakes = new ItemTemFlakes(0, 0, false).setUnlocalizedName("tem_flakes").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":tem_flakes");
		glowing_stew = new ItemSoup(6).setUnlocalizedName("glowing_stew").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glowing_stew");
		lemon = new ItemLemon(3, 5, false).setUnlocalizedName("lemon").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":lemon");
		definitelyfood = new ItemLemon(2, 5, false).setUnlocalizedName("definitelyfood").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":definitelyfood");
		med_ipecac = new ItemLemon(0, 0, false).setUnlocalizedName("med_ipecac").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_ipecac_new");
		med_ptsd = new ItemLemon(0, 0, false).setUnlocalizedName("med_ptsd").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_ptsd_new");
		med_schizophrenia = new ItemLemon(0, 0, false).setUnlocalizedName("med_schizophrenia").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_schizophrenia_new");
		
		defuser = new Item().setUnlocalizedName("defuser").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":defuser");
		
		flame_pony = new ItemCustomLore().setUnlocalizedName("flame_pony").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_pony");
		flame_conspiracy = new ItemCustomLore().setUnlocalizedName("flame_conspiracy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_conspiracy");
		flame_politics = new ItemCustomLore().setUnlocalizedName("flame_politics").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_politics");
		flame_opinion = new ItemCustomLore().setUnlocalizedName("flame_opinion").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_opinion");
		
		gadget_explosive = new Item().setUnlocalizedName("gadget_explosive").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_explosive");
		gadget_explosive8 = new ItemGadget().setUnlocalizedName("gadget_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_explosive8");
		gadget_wireing = new ItemGadget().setUnlocalizedName("gadget_wireing").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_wireing");
		gadget_core = new ItemGadget().setUnlocalizedName("gadget_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_core");

		boy_igniter = new ItemBoy().setUnlocalizedName("boy_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_igniter");
		boy_propellant = new ItemBoy().setUnlocalizedName("boy_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_propellant");
		boy_bullet = new ItemBoy().setUnlocalizedName("boy_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_bullet");
		boy_target = new ItemBoy().setUnlocalizedName("boy_target").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_target");
		boy_shielding = new ItemBoy().setUnlocalizedName("boy_shielding").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_shielding");
		
		man_explosive = new Item().setUnlocalizedName("man_explosive").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_explosive");
		man_explosive8 = new ItemManMike().setUnlocalizedName("man_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_explosive8");
		man_igniter = new ItemMan().setUnlocalizedName("man_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_igniter");
		man_core = new ItemManMike().setUnlocalizedName("man_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_core");

		mike_core = new ItemMike().setUnlocalizedName("mike_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_core");
		mike_deut = new ItemMike().setUnlocalizedName("mike_deut").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setContainerItem(ModItems.tank_steel).setTextureName(RefStrings.MODID + ":mike_deut");
		mike_cooling_unit = new ItemMike().setUnlocalizedName("mike_cooling_unit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_cooling_unit");

		tsar_core = new ItemTsar().setUnlocalizedName("tsar_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":tsar_core");

		fleija_igniter = new ItemFleija().setUnlocalizedName("fleija_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_igniter");
		fleija_propellant = new ItemFleija().setUnlocalizedName("fleija_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_propellant");
		fleija_core = new ItemFleija().setUnlocalizedName("fleija_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_core");

		solinium_igniter = new ItemSolinium().setUnlocalizedName("solinium_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_igniter");
		solinium_propellant = new ItemSolinium().setUnlocalizedName("solinium_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_propellant");
		solinium_core = new ItemSolinium().setUnlocalizedName("solinium_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_core");
		
		n2_charge = new ItemN2().setUnlocalizedName("n2_charge").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":n2_charge");

		battery_generic = new ItemBattery(50, 1, 1).setUnlocalizedName("battery_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_generic");
		battery_advanced = new ItemBattery(200, 5, 5).setUnlocalizedName("battery_advanced").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced");
		battery_lithium = new ItemBattery(2500, 10, 10).setUnlocalizedName("battery_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_lithium");
		battery_schrabidium = new ItemBattery(10000, 50, 50).setUnlocalizedName("battery_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium");
		battery_spark = new ItemBattery(1000000, 20000, 20000).setUnlocalizedName("battery_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark");
		battery_creative = new Item().setUnlocalizedName("battery_creative").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_creative");

		battery_red_cell = new ItemBattery(150, 1, 1).setUnlocalizedName("battery_red_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_red_cell");
		battery_red_cell_6 = new ItemBattery(150 * 6, 1, 1).setUnlocalizedName("battery_red_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_red_cell_6");
		battery_red_cell_24 = new ItemBattery(150 * 24, 1, 1).setUnlocalizedName("battery_red_cell_24").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_red_cell_24");
		battery_advanced_cell = new ItemBattery(600, 5, 5).setUnlocalizedName("battery_advanced_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_cell");
		battery_advanced_cell_4 = new ItemBattery(600 * 4, 5, 5).setUnlocalizedName("battery_advanced_cell_4").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_cell_4");
		battery_advanced_cell_12 = new ItemBattery(600 * 12, 5, 5).setUnlocalizedName("battery_advanced_cell_12").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_cell_12");
		battery_lithium_cell = new ItemBattery(7500, 10, 10).setUnlocalizedName("battery_lithium_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_lithium_cell");
		battery_lithium_cell_3 = new ItemBattery(7500 * 3, 10, 10).setUnlocalizedName("battery_lithium_cell_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_lithium_cell_3");
		battery_lithium_cell_6 = new ItemBattery(7500 * 6, 10, 10).setUnlocalizedName("battery_lithium_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_lithium_cell_6");
		battery_schrabidium_cell = new ItemBattery(30000, 50, 50).setUnlocalizedName("battery_schrabidium_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium_cell");
		battery_schrabidium_cell_2 = new ItemBattery(30000 * 2, 50, 50).setUnlocalizedName("battery_schrabidium_cell_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium_cell_2");
		battery_schrabidium_cell_4 = new ItemBattery(30000 * 4, 50, 50).setUnlocalizedName("battery_schrabidium_cell_4").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium_cell_4");
		battery_spark_cell_6 = new ItemBattery(1000000 * 6, 20000, 20000).setUnlocalizedName("battery_spark_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_6");
		battery_spark_cell_25 = new ItemBattery(1000000 * 25, 20000, 20000).setUnlocalizedName("battery_spark_cell_25").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_25");
		battery_spark_cell_100 = new ItemBattery(1000000L * 100L, 20000, 20000).setUnlocalizedName("battery_spark_cell_100").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_100");
		battery_spark_cell_1000 = new ItemBattery(1000000L * 1000L, 200000, 200000).setUnlocalizedName("battery_spark_cell_1000").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_1000");
		battery_spark_cell_2500 = new ItemBattery(1000000L * 2500L, 200000, 200000).setUnlocalizedName("battery_spark_cell_2500").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_2500");
		battery_spark_cell_10000 = new ItemBattery(1000000L * 10000L, 2000000, 2000000).setUnlocalizedName("battery_spark_cell_10000").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_10000");
		battery_spark_cell_power = new ItemBattery(1000000L * 1000000L, 2000000, 2000000).setUnlocalizedName("battery_spark_cell_power").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_power");

		battery_potato = new ItemBattery(1, 0, 1).setUnlocalizedName("battery_potato").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_potato");
		battery_potatos = new ItemPotatos(50, 0, 1).setUnlocalizedName("battery_potatos").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_potatos");
		battery_su = new ItemBattery(15, 0, 1).setUnlocalizedName("battery_su").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_su");
		battery_su_l = new ItemBattery(35, 0, 1).setUnlocalizedName("battery_su_l").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_su_l");
		battery_steam = new ItemBattery(600, 3, 60).setUnlocalizedName("battery_steam").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_steam");
		battery_steam_large = new ItemBattery(1000, 5, 100).setUnlocalizedName("battery_steam_large").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_steam_large");
		fusion_core = new ItemBattery(200000, 0, 25).setUnlocalizedName("fusion_core").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_core");
		fusion_core_infinite = new Item().setUnlocalizedName("fusion_core_infinite").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_core_infinite");
		energy_core = new ItemBattery(100000, 0, 10).setUnlocalizedName("energy_core").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":energy_core");
		fuse = new ItemCustomLore().setUnlocalizedName("fuse").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fuse");
		redcoil_capacitor = new ItemCapacitor(10).setUnlocalizedName("redcoil_capacitor").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":redcoil_capacitor");
		titanium_filter = new ItemCapacitor(72000).setUnlocalizedName("titanium_filter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":titanium_filter");
		screwdriver = new ItemCustomLore().setUnlocalizedName("screwdriver").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":screwdriver");
		overfuse = new ItemCustomLore().setUnlocalizedName("overfuse").setMaxStackSize(1).setFull3D().setTextureName(RefStrings.MODID + ":overfuse");

		dynosphere_base = new Item().setUnlocalizedName("dynosphere_base").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_base");
		dynosphere_desh = new ItemBattery(10000L, 100, 0).setUnlocalizedName("dynosphere_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_desh");
		dynosphere_desh_charged = new Item().setUnlocalizedName("dynosphere_desh_charged").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_desh_charged");
		dynosphere_schrabidium = new ItemBattery(1000000L, 1000, 0).setUnlocalizedName("dynosphere_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_schrabidium");
		dynosphere_schrabidium_charged = new Item().setUnlocalizedName("dynosphere_schrabidium_charged").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_schrabidium_charged");
		dynosphere_euphemium = new ItemBattery(100000000L, 10000, 0).setUnlocalizedName("dynosphere_euphemium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_euphemium");
		dynosphere_euphemium_charged = new Item().setUnlocalizedName("dynosphere_euphemium_charged").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_euphemium_charged");
		dynosphere_dineutronium = new ItemBattery(10000000000L, 100000, 0).setUnlocalizedName("dynosphere_dineutronium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_dineutronium");
		dynosphere_dineutronium_charged = new Item().setUnlocalizedName("dynosphere_dineutronium_charged").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_dineutronium_charged");

		factory_core_titanium = new ItemBattery(70400, 10, 0).setUnlocalizedName("factory_core_titanium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":factory_core_titanium");
		factory_core_advanced = new ItemBattery(41600, 10, 0).setUnlocalizedName("factory_core_advanced").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":factory_core_advanced");

		ams_focus_blank = new Item().setUnlocalizedName("ams_focus_blank").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_blank");
		ams_focus_limiter = new ItemCustomLore().setUnlocalizedName("ams_focus_limiter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_limiter");
		ams_focus_booster = new ItemCustomLore().setUnlocalizedName("ams_focus_booster").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_booster");
		ams_muzzle = new ItemCustomLore().setUnlocalizedName("ams_muzzle").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_muzzle");
		ams_core_sing = new ItemAMSCore(1000000000L, 200, 10).setUnlocalizedName("ams_core_sing").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_sing");
		ams_core_wormhole = new ItemAMSCore(1500000000L, 200, 15).setUnlocalizedName("ams_core_wormhole").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_wormhole");
		ams_core_eyeofharmony = new ItemAMSCore(2500000000L, 300, 10).setUnlocalizedName("ams_core_eyeofharmony").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_eyeofharmony");
		ams_core_thingy = new ItemAMSCore(5000000000L, 250, 5).setUnlocalizedName("ams_core_thingy").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":ams_core_thingy");

		upgrade_template = new ItemCustomLore().setUnlocalizedName("upgrade_template").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":upgrade_template");
		upgrade_speed_1 = new ItemCustomLore().setUnlocalizedName("upgrade_speed_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_1");
		upgrade_speed_2 = new ItemCustomLore().setUnlocalizedName("upgrade_speed_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_2");
		upgrade_speed_3 = new ItemCustomLore().setUnlocalizedName("upgrade_speed_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_3");
		upgrade_effect_1 = new ItemCustomLore().setUnlocalizedName("upgrade_effect_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_1");
		upgrade_effect_2 = new ItemCustomLore().setUnlocalizedName("upgrade_effect_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_2");
		upgrade_effect_3 = new ItemCustomLore().setUnlocalizedName("upgrade_effect_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_3");
		upgrade_power_1 = new ItemCustomLore().setUnlocalizedName("upgrade_power_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_1");
		upgrade_power_2 = new ItemCustomLore().setUnlocalizedName("upgrade_power_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_2");
		upgrade_power_3 = new ItemCustomLore().setUnlocalizedName("upgrade_power_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_3");
		upgrade_fortune_1 = new ItemCustomLore().setUnlocalizedName("upgrade_fortune_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_1");
		upgrade_fortune_2 = new ItemCustomLore().setUnlocalizedName("upgrade_fortune_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_2");
		upgrade_fortune_3 = new ItemCustomLore().setUnlocalizedName("upgrade_fortune_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_3");
		upgrade_afterburn_1 = new ItemCustomLore().setUnlocalizedName("upgrade_afterburn_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_1");
		upgrade_afterburn_2 = new ItemCustomLore().setUnlocalizedName("upgrade_afterburn_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_2");
		upgrade_afterburn_3 = new ItemCustomLore().setUnlocalizedName("upgrade_afterburn_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_3");

		wand = new ItemWand().setUnlocalizedName("wand_k").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand");
		wand_s = new ItemWandS().setUnlocalizedName("wand_s").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_s");
		wand_d = new ItemWandD().setUnlocalizedName("wand_d").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_d");
		
		nuke_starter_kit = new ItemStarterKit().setUnlocalizedName("nuke_starter_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_starter_kit");
		nuke_advanced_kit = new ItemStarterKit().setUnlocalizedName("nuke_advanced_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_advanced_kit");
		nuke_commercially_kit = new ItemStarterKit().setUnlocalizedName("nuke_commercially_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_commercially_kit");
		nuke_electric_kit = new ItemStarterKit().setUnlocalizedName("nuke_electric_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_electric_kit");
		gadget_kit = new ItemStarterKit().setUnlocalizedName("gadget_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":gadget_kit");
		boy_kit = new ItemStarterKit().setUnlocalizedName("boy_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":boy_kit");
		man_kit = new ItemStarterKit().setUnlocalizedName("man_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":man_kit");
		mike_kit = new ItemStarterKit().setUnlocalizedName("mike_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":mike_kit");
		tsar_kit = new ItemStarterKit().setUnlocalizedName("tsar_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":tsar_kit");
		multi_kit = new ItemStarterKit().setUnlocalizedName("multi_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":multi_kit");
		grenade_kit = new ItemStarterKit().setUnlocalizedName("grenade_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":grenade_kit");
		fleija_kit = new ItemStarterKit().setUnlocalizedName("fleija_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":fleija_kit");
		prototype_kit = new ItemStarterKit().setUnlocalizedName("prototype_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":prototype_kit");
		missile_kit = new ItemStarterKit().setUnlocalizedName("missile_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":missile_kit");
		t45_kit = new ItemStarterKit().setUnlocalizedName("t45_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":t45_kit");
		euphemium_kit = new ItemStarterKit().setUnlocalizedName("euphemium_kit").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":code");
		solinium_kit = new ItemStarterKit().setUnlocalizedName("solinium_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":solinium_kit");

		clip_revolver_iron = new ItemClip().setUnlocalizedName("clip_revolver_iron").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_iron");
		clip_revolver = new ItemClip().setUnlocalizedName("clip_revolver").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver");
		clip_revolver_gold = new ItemClip().setUnlocalizedName("clip_revolver_gold").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_gold");
		clip_revolver_lead = new ItemClip().setUnlocalizedName("clip_revolver_lead").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_lead");
		clip_revolver_schrabidium = new ItemClip().setUnlocalizedName("clip_revolver_schrabidium").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_schrabidium");
		clip_revolver_cursed = new ItemClip().setUnlocalizedName("clip_revolver_cursed").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_cursed");
		clip_revolver_nightmare = new ItemClip().setUnlocalizedName("clip_revolver_nightmare").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_nightmare");
		clip_revolver_nightmare2 = new ItemClip().setUnlocalizedName("clip_revolver_nightmare2").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_nightmare2");
		clip_revolver_pip = new ItemClip().setUnlocalizedName("clip_revolver_pip").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_pip");
		clip_rpg = new ItemClip().setUnlocalizedName("clip_rpg").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_rpg_alt");
		clip_stinger = new ItemClip().setUnlocalizedName("clip_stinger").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_stinger");
		clip_fatman = new ItemClip().setUnlocalizedName("clip_fatman").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_fatman");
		clip_mirv = new ItemClip().setUnlocalizedName("clip_mirv").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_mirv");
		clip_bf = new ItemClip().setUnlocalizedName("clip_bf").setCreativeTab(null).setTextureName(RefStrings.MODID + ":clip_bf");
		clip_mp40 = new ItemClip().setUnlocalizedName("clip_mp40").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_mp40");
		clip_uzi = new ItemClip().setUnlocalizedName("clip_uzi").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_uzi");
		clip_uboinik = new ItemClip().setUnlocalizedName("clip_uboinik").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_uboinik");
		clip_lever_action = new ItemClip().setUnlocalizedName("clip_lever_action").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_lever_action");
		clip_bolt_action = new ItemClip().setUnlocalizedName("clip_bolt_action").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_bolt_action");
		clip_osipr = new ItemClip().setUnlocalizedName("clip_osipr").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_osipr");
		clip_immolator = new ItemClip().setUnlocalizedName("clip_immolator").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_immolator");
		clip_cryolator = new ItemClip().setUnlocalizedName("clip_cryolator").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_cryolator");
		clip_mp = new ItemClip().setUnlocalizedName("clip_mp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_mp");
		clip_xvl1456 = new ItemClip().setUnlocalizedName("clip_xvl1456").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_xvl1456");
		clip_emp = new ItemClip().setUnlocalizedName("clip_emp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_emp");
		clip_jack = new ItemClip().setUnlocalizedName("clip_jack").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_jack");
		clip_spark = new ItemClip().setUnlocalizedName("clip_spark").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_spark");
		clip_hp = new ItemClip().setUnlocalizedName("clip_hp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_hp");
		clip_euthanasia = new ItemClip().setUnlocalizedName("clip_euthanasia").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_euthanasia");
		clip_defabricator = new ItemClip().setUnlocalizedName("clip_defabricator").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_defabricator");
		
		ammo_container = new ItemClip().setUnlocalizedName("ammo_container").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_container");
		
		ingot_euphemium = new ItemCustomLore().setUnlocalizedName("ingot_euphemium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_euphemium");
		nugget_euphemium = new ItemCustomLore().setUnlocalizedName("nugget_euphemium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_euphemium");
		rod_quad_euphemium = new ItemCustomLore().setUnlocalizedName("rod_quad_euphemium").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_euphemium");
		watch = new ItemCustomLore().setUnlocalizedName("watch").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":watch");
		apple_euphemium = new ItemAppleEuphemium(20, 100, false).setUnlocalizedName("apple_euphemium").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":apple_euphemium");

		igniter = new ItemCustomLore().setUnlocalizedName("igniter").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":trigger");
		detonator = new ItemDetonator().setUnlocalizedName("detonator").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator");
		detonator_multi = new ItemMultiDetonator().setUnlocalizedName("detonator_multi").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_multi");
		detonator_laser = new ItemLaserDetonator().setUnlocalizedName("detonator_laser").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_laser");
		crate_caller = new ItemCrateCaller().setUnlocalizedName("crate_caller").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":crate_caller");
		bomb_caller = new ItemBombCaller().setUnlocalizedName("bomb_caller").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bomb_caller");
		meteor_remote = new ItemMeteorRemote().setUnlocalizedName("meteor_remote").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":meteor_remote");
		remote = new ItemRamManipulator().setUnlocalizedName("remote").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":remote");
		chopper = new ItemChopper().setUnlocalizedName("chopper").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":chopper");
		linker = new ItemTeleLink().setUnlocalizedName("linker").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":linker");
		oil_detector = new ItemOilDetector().setUnlocalizedName("oil_detector").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":oil_detector");
		turret_control = new ItemTurretControl().setUnlocalizedName("turret_control").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_control");
		turret_chip = new ItemTurretChip().setUnlocalizedName("turret_chip").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_chip");
		turret_biometry = new ItemTurretBiometry().setUnlocalizedName("turret_biometry").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":rei_scanner");
		geiger_counter = new ItemGeigerCounter().setUnlocalizedName("geiger_counter").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":geiger_counter");
		survey_scanner = new ItemSurveyScanner().setUnlocalizedName("survey_scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":survey_scanner");

		turret_light_ammo = new ItemTurretAmmo(ModBlocks.turret_light, 100).setUnlocalizedName("turret_light_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_light_ammo");
		turret_heavy_ammo = new ItemTurretAmmo(ModBlocks.turret_heavy, 25).setUnlocalizedName("turret_heavy_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_heavy_ammo");
		turret_rocket_ammo = new ItemTurretAmmo(ModBlocks.turret_rocket, 8).setUnlocalizedName("turret_rocket_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_rocket_ammo");
		turret_flamer_ammo = new ItemTurretAmmo(ModBlocks.turret_flamer, 200).setUnlocalizedName("turret_flamer_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_flamer_ammo");
		turret_tau_ammo = new ItemTurretAmmo(ModBlocks.turret_tau, 100).setUnlocalizedName("turret_tau_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_tau_ammo");
		turret_spitfire_ammo = new ItemTurretAmmo(ModBlocks.turret_spitfire, 2).setUnlocalizedName("turret_spitfire_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_spitfire_ammo");
		turret_cwis_ammo = new ItemTurretAmmo(ModBlocks.turret_cwis, 250).setUnlocalizedName("turret_cwis_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_cwis_ammo");
		turret_cheapo_ammo = new ItemTurretAmmo(ModBlocks.turret_cheapo, 100).setUnlocalizedName("turret_cheapo_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_cheapo_ammo");
		
		template_folder = new ItemTemplateFolder().setUnlocalizedName("template_folder").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":template_folder");
		assembly_template = new ItemAssemblyTemplate().setUnlocalizedName("assembly_template").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":assembly_template");
		chemistry_template = new ItemChemistryTemplate().setUnlocalizedName("chemistry_template").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":chemistry_template");
		chemistry_icon = new ItemChemistryIcon().setUnlocalizedName("chemistry_icon").setMaxStackSize(1).setCreativeTab(null);
		fluid_identifier = new ItemFluidIdentifier().setUnlocalizedName("fluid_identifier").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":fluid_identifier");
		fluid_icon = new ItemFluidIcon().setUnlocalizedName("fluid_icon").setCreativeTab(null).setTextureName(RefStrings.MODID + ":fluid_icon");
		fluid_tank_full = new ItemFluidTank().setUnlocalizedName("fluid_tank_full").setContainerItem(ModItems.fluid_tank_empty).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_tank");
		fluid_tank_empty = new Item().setUnlocalizedName("fluid_tank_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_tank");
		fluid_barrel_full = new ItemFluidTank().setUnlocalizedName("fluid_barrel_full").setContainerItem(ModItems.fluid_barrel_empty).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_barrel");
		fluid_barrel_empty = new Item().setUnlocalizedName("fluid_barrel_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_barrel");
		fluid_barrel_infinite = new Item().setUnlocalizedName("fluid_barrel_infinite").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_barrel_infinite");
		siren_track = new ItemCassette().setUnlocalizedName("siren_track").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":cassette");

		euphemium_helmet = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, 6, 0).setUnlocalizedName("euphemium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_helmet");
		euphemium_plate = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, 6, 1).setUnlocalizedName("euphemium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_plate");
		euphemium_legs = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, 6, 2).setUnlocalizedName("euphemium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_legs");
		euphemium_boots = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, 6, 3).setUnlocalizedName("euphemium_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_boots");
		
		goggles = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("goggles").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":goggles");
		gas_mask = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("gas_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask");
		gas_mask_m65 = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("gas_mask_m65").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask_m65");
		//oxy_mask = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("oxy_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":oxy_mask");
		
		t45_helmet = new ArmorT45(MainRegistry.enumArmorMaterialT45, 2, 0).setUnlocalizedName("t45_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_helmet");
		t45_plate = new ArmorT45(MainRegistry.enumArmorMaterialT45, 2, 1).setUnlocalizedName("t45_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_plate");
		t45_legs = new ArmorT45(MainRegistry.enumArmorMaterialT45, 2, 2).setUnlocalizedName("t45_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_legs");
		t45_boots = new ArmorT45(MainRegistry.enumArmorMaterialT45, 2, 3).setUnlocalizedName("t45_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_boots");
		
		chainsaw = new ModAxe(MainRegistry.enumToolMaterialChainsaw).setUnlocalizedName("chainsaw").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":chainsaw");

		schrabidium_helmet = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, 7, 0).setUnlocalizedName("schrabidium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_helmet");
		schrabidium_plate = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, 7, 1).setUnlocalizedName("schrabidium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_plate");
		schrabidium_legs = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, 7, 2).setUnlocalizedName("schrabidium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_legs");
		schrabidium_boots = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, 7, 3).setUnlocalizedName("schrabidium_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_boots");
		titanium_helmet = new ModArmor(MainRegistry.enumArmorMaterialTitanium, 7, 0).setUnlocalizedName("titanium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_helmet");
		titanium_plate = new ModArmor(MainRegistry.enumArmorMaterialTitanium, 7, 1).setUnlocalizedName("titanium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_plate");
		titanium_legs = new ModArmor(MainRegistry.enumArmorMaterialTitanium, 7, 2).setUnlocalizedName("titanium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_legs");
		titanium_boots = new ModArmor(MainRegistry.enumArmorMaterialTitanium, 7, 3).setUnlocalizedName("titanium_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_boots");
		steel_helmet = new ModArmor(MainRegistry.enumArmorMaterialSteel, 7, 0).setUnlocalizedName("steel_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_helmet");
		steel_plate = new ModArmor(MainRegistry.enumArmorMaterialSteel, 7, 1).setUnlocalizedName("steel_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_plate");
		steel_legs = new ModArmor(MainRegistry.enumArmorMaterialSteel, 7, 2).setUnlocalizedName("steel_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_legs");
		steel_boots = new ModArmor(MainRegistry.enumArmorMaterialSteel, 7, 3).setUnlocalizedName("steel_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_boots");
		alloy_helmet = new ModArmor(MainRegistry.enumArmorMaterialAlloy, 7, 0).setUnlocalizedName("alloy_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_helmet");
		alloy_plate = new ModArmor(MainRegistry.enumArmorMaterialAlloy, 7, 1).setUnlocalizedName("alloy_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_plate");
		alloy_legs = new ModArmor(MainRegistry.enumArmorMaterialAlloy, 7, 2).setUnlocalizedName("alloy_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_legs");
		alloy_boots = new ModArmor(MainRegistry.enumArmorMaterialAlloy, 7, 3).setUnlocalizedName("alloy_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_boots");
		cmb_helmet = new ModArmor(MainRegistry.enumArmorMaterialCmb, 7, 0).setUnlocalizedName("cmb_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_helmet");
		cmb_plate = new ModArmor(MainRegistry.enumArmorMaterialCmb, 7, 1).setUnlocalizedName("cmb_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_plate");
		cmb_legs = new ModArmor(MainRegistry.enumArmorMaterialCmb, 7, 2).setUnlocalizedName("cmb_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_legs");
		cmb_boots = new ModArmor(MainRegistry.enumArmorMaterialCmb, 7, 3).setUnlocalizedName("cmb_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_boots");
		paa_plate = new ModArmor(MainRegistry.enumArmorMaterialPaa, 7, 1).setUnlocalizedName("paa_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":paa_plate");
		paa_legs = new ModArmor(MainRegistry.enumArmorMaterialPaa, 7, 2).setUnlocalizedName("paa_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":paa_legs");
		paa_boots = new ModArmor(MainRegistry.enumArmorMaterialPaa, 7, 3).setUnlocalizedName("paa_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":paa_boots");
		asbestos_helmet = new ArmorAsbestos(MainRegistry.enumArmorMaterialSteel, 7, 0).setUnlocalizedName("asbestos_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":asbestos_helmet");
		asbestos_plate = new ArmorAsbestos(MainRegistry.enumArmorMaterialSteel, 7, 1).setUnlocalizedName("asbestos_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":asbestos_plate");
		asbestos_legs = new ArmorAsbestos(MainRegistry.enumArmorMaterialSteel, 7, 2).setUnlocalizedName("asbestos_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":asbestos_legs");
		asbestos_boots = new ArmorAsbestos(MainRegistry.enumArmorMaterialSteel, 7, 3).setUnlocalizedName("asbestos_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":asbestos_boots");

		jackt = new ModArmor(MainRegistry.enumArmorMaterialSteel, 7, 1).setUnlocalizedName("jackt").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jackt");
		jackt2 = new ModArmor(MainRegistry.enumArmorMaterialSteel, 7, 1).setUnlocalizedName("jackt2").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jackt2");
		
		schrabidium_sword = new SwordSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_sword").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_sword");
		schrabidium_pickaxe = new PickaxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_pickaxe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_pickaxe");
		schrabidium_axe = new AxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_axe");
		schrabidium_shovel = new SpadeSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_shovel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_shovel");
		schrabidium_hoe = new HoeSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_hoe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_hoe");
		titanium_sword = new ModSword(MainRegistry.enumToolMaterialTitanium).setUnlocalizedName("titanium_sword").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_sword");
		titanium_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialTitanium).setUnlocalizedName("titanium_pickaxe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_pickaxe");
		titanium_axe = new ModAxe(MainRegistry.enumToolMaterialTitanium).setUnlocalizedName("titanium_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_axe");
		titanium_shovel = new ModSpade(MainRegistry.enumToolMaterialTitanium).setUnlocalizedName("titanium_shovel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_shovel");
		titanium_hoe = new ModHoe(MainRegistry.enumToolMaterialTitanium).setUnlocalizedName("titanium_hoe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_hoe");
		steel_sword = new ModSword(MainRegistry.enumToolMaterialSteel).setUnlocalizedName("steel_sword").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_sword");
		steel_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialSteel).setUnlocalizedName("steel_pickaxe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_pickaxe");
		steel_axe = new ModAxe(MainRegistry.enumToolMaterialSteel).setUnlocalizedName("steel_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_axe");
		steel_shovel = new ModSpade(MainRegistry.enumToolMaterialSteel).setUnlocalizedName("steel_shovel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_shovel");
		steel_hoe = new ModHoe(MainRegistry.enumToolMaterialSteel).setUnlocalizedName("steel_hoe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steel_hoe");
		alloy_sword = new ModSword(MainRegistry.enumToolMaterialAlloy).setUnlocalizedName("alloy_sword").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_sword");
		alloy_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialAlloy).setUnlocalizedName("alloy_pickaxe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_pickaxe");
		alloy_axe = new ModAxe(MainRegistry.enumToolMaterialAlloy).setUnlocalizedName("alloy_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_axe");
		alloy_shovel = new ModSpade(MainRegistry.enumToolMaterialAlloy).setUnlocalizedName("alloy_shovel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_shovel");
		alloy_hoe = new ModHoe(MainRegistry.enumToolMaterialAlloy).setUnlocalizedName("alloy_hoe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":alloy_hoe");
		cmb_sword = new ModSword(MainRegistry.enumToolMaterialCmb).setUnlocalizedName("cmb_sword").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_sword");
		cmb_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialCmb).setUnlocalizedName("cmb_pickaxe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_pickaxe");
		cmb_axe = new ModAxe(MainRegistry.enumToolMaterialCmb).setUnlocalizedName("cmb_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_axe");
		cmb_shovel = new ModSpade(MainRegistry.enumToolMaterialCmb).setUnlocalizedName("cmb_shovel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_shovel");
		cmb_hoe = new ModHoe(MainRegistry.enumToolMaterialCmb).setUnlocalizedName("cmb_hoe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cmb_hoe");
		elec_sword = new ModSword(MainRegistry.enumToolMaterialElec).setUnlocalizedName("elec_sword").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":elec_sword_anim");
		elec_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialElec).setUnlocalizedName("elec_pickaxe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":elec_drill_anim");
		elec_axe = new ModAxe(MainRegistry.enumToolMaterialElec).setUnlocalizedName("elec_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":elec_chainsaw_anim");
		elec_shovel = new ModSpade(MainRegistry.enumToolMaterialElec).setUnlocalizedName("elec_shovel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":elec_shovel_anim");
		desh_sword = new ModSword(MainRegistry.enumToolMaterialDesh).setUnlocalizedName("desh_sword").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":desh_sword");
		desh_pickaxe = new ModPickaxe(MainRegistry.enumToolMaterialDesh).setUnlocalizedName("desh_pickaxe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":desh_pickaxe");
		desh_axe = new ModAxe(MainRegistry.enumToolMaterialDesh).setUnlocalizedName("desh_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":desh_axe");
		desh_shovel = new ModSpade(MainRegistry.enumToolMaterialDesh).setUnlocalizedName("desh_shovel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":desh_shovel");
		desh_hoe = new ModHoe(MainRegistry.enumToolMaterialDesh).setUnlocalizedName("desh_hoe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":desh_hoe");
		
		mask_of_infamy = new MaskOfInfamy(ArmorMaterial.IRON, 8, 0).setUnlocalizedName("mask_of_infamy").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_of_infamy");

		hazmat_helmet = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, 9, 0).setUnlocalizedName("hazmat_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_helmet");
		hazmat_plate = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, 9, 1).setUnlocalizedName("hazmat_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_plate");
		hazmat_legs = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, 9, 2).setUnlocalizedName("hazmat_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_legs");
		hazmat_boots = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, 9, 3).setUnlocalizedName("hazmat_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_boots");
		hazmat_paa_helmet = new ArmorHazmat(MainRegistry.enumArmorMaterialPaa, 9, 0).setUnlocalizedName("hazmat_paa_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_paa_helmet");
		hazmat_paa_plate = new ArmorHazmat(MainRegistry.enumArmorMaterialPaa, 9, 1).setUnlocalizedName("hazmat_paa_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_paa_plate");
		hazmat_paa_legs = new ArmorHazmat(MainRegistry.enumArmorMaterialPaa, 9, 2).setUnlocalizedName("hazmat_paa_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_paa_legs");
		hazmat_paa_boots = new ArmorHazmat(MainRegistry.enumArmorMaterialPaa, 9, 3).setUnlocalizedName("hazmat_paa_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_paa_boots");

		australium_iii = new ArmorAustralium(MainRegistry.enumArmorMaterialAusIII, 9, 1).setUnlocalizedName("australium_iii").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":australium_iii");
		australium_iv = new ArmorAustralium(MainRegistry.enumArmorMaterialAusIV, 9, 1).setUnlocalizedName("australium_iv").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":australium_iv");
		australium_v = new ArmorAustralium(MainRegistry.enumArmorMaterialAusV, 9, 1).setUnlocalizedName("australium_v").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":australium_v");
		
		jetpack_boost = new JetpackBooster(MainRegistry.enumArmorMaterialSteel, 9, 1).setUnlocalizedName("jetpack_boost").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_boost");
		jetpack_break = new JetpackBreak(MainRegistry.enumArmorMaterialSteel, 9, 1).setUnlocalizedName("jetpack_break").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_break");
		jetpack_fly = new JetpackRegular(MainRegistry.enumArmorMaterialSteel, 9, 1).setUnlocalizedName("jetpack_fly").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_fly");
		jetpack_vector = new JetpackVectorized(MainRegistry.enumArmorMaterialSteel, 9, 1).setUnlocalizedName("jetpack_vector").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_vector");

		cape_test = new ArmorModel(MainRegistry.enumArmorMaterialEmerald, 9, 1).setUnlocalizedName("cape_test").setCreativeTab(null).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_test");
		cape_radiation = new ArmorModel(ArmorMaterial.CHAIN, 9, 1).setUnlocalizedName("cape_radiation").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_radiation");
		cape_gasmask = new ArmorModel(ArmorMaterial.CHAIN, 9, 1).setUnlocalizedName("cape_gasmask").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_gasmask");
		cape_schrabidium = new ArmorModel(MainRegistry.enumArmorMaterialSchrabidium, 9, 1).setUnlocalizedName("cape_schrabidium").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_schrabidium");
		cape_hbm = new ArmorModel(MainRegistry.enumArmorMaterialEuphemium, 9, 1).setUnlocalizedName("cape_hbm").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_unknown");
		cape_dafnik = new ArmorModel(MainRegistry.enumArmorMaterialEmerald, 9, 1).setUnlocalizedName("cape_dafnik").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_unknown");
		cape_lpkukin = new ArmorModel(MainRegistry.enumArmorMaterialEmerald, 9, 1).setUnlocalizedName("cape_lpkukin").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_unknown");
		cape_vertice = new ArmorModel(MainRegistry.enumArmorMaterialEmerald, 9, 1).setUnlocalizedName("cape_vertice").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_unknown");
		cape_codered_ = new ArmorModel(MainRegistry.enumArmorMaterialEmerald, 9, 1).setUnlocalizedName("cape_codered_").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_unknown");

		schrabidium_hammer = new WeaponSpecial(MainRegistry.enumToolMaterialHammer).setUnlocalizedName("schrabidium_hammer").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_hammer");
		shimmer_sledge = new WeaponSpecial(MainRegistry.enumToolMaterialSledge).setUnlocalizedName("shimmer_sledge").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":shimmer_sledge_original");
		shimmer_axe = new WeaponSpecial(MainRegistry.enumToolMaterialSledge).setUnlocalizedName("shimmer_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":shimmer_axe");
		bottle_opener = new WeaponSpecial(MainRegistry.enumToolMaterialBottleOpener).setUnlocalizedName("bottle_opener").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":bottle_opener");
		euphemium_stopper = new ItemSyringe().setUnlocalizedName("euphemium_stopper").setMaxStackSize(1).setFull3D().setTextureName(RefStrings.MODID + ":euphemium_stopper");
		matchstick = new ItemMatch().setUnlocalizedName("matchstick").setCreativeTab(CreativeTabs.tabTools).setFull3D().setTextureName(RefStrings.MODID + ":matchstick");
		crowbar = new ModSword(MainRegistry.enumToolMaterialSteel).setUnlocalizedName("crowbar").setFull3D().setTextureName(RefStrings.MODID + ":crowbar");

		multitool_hit = new ItemMultitoolPassive().setUnlocalizedName("multitool_hit").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_fist");
		multitool_dig = new ItemMultitoolTool(4.0F, MainRegistry.enumToolMaterialMultitool, ItemMultitoolTool.getAllBlocks()).setFull3D().setUnlocalizedName("multitool_dig").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":multitool_claw");
		multitool_silk = new ItemMultitoolTool(4.0F, MainRegistry.enumToolMaterialMultitool, ItemMultitoolTool.getAllBlocks()).setFull3D().setUnlocalizedName("multitool_silk").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_claw");
		multitool_ext = new ItemMultitoolPassive().setUnlocalizedName("multitool_ext").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_open");
		multitool_miner = new ItemMultitoolPassive().setUnlocalizedName("multitool_miner").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_pointer");
		multitool_beam = new ItemMultitoolPassive().setUnlocalizedName("multitool_beam").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_pointer");
		multitool_sky= new ItemMultitoolPassive().setUnlocalizedName("multitool_sky").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_open");
		multitool_mega = new ItemMultitoolPassive().setUnlocalizedName("multitool_mega").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_fist");
		multitool_joule = new ItemMultitoolPassive().setUnlocalizedName("multitool_joule").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_fist");
		multitool_decon = new ItemMultitoolPassive().setUnlocalizedName("multitool_decon").setCreativeTab(null).setTextureName(RefStrings.MODID + ":multitool_fist");
		
		saw = new ModSword(MainRegistry.enumToolMaterialSaw).setUnlocalizedName("weapon_saw").setFull3D().setTextureName(RefStrings.MODID + ":saw");
		bat = new ModSword(MainRegistry.enumToolMaterialBat).setUnlocalizedName("weapon_bat").setFull3D().setTextureName(RefStrings.MODID + ":bat");
		bat_nail = new ModSword(MainRegistry.enumToolMaterialBatNail).setUnlocalizedName("weapon_bat_nail").setFull3D().setTextureName(RefStrings.MODID + ":bat_nail");
		golf_club = new ModSword(MainRegistry.enumToolMaterialGolfClub).setUnlocalizedName("weapon_golf_club").setFull3D().setTextureName(RefStrings.MODID + ":golf_club");
		pipe_rusty = new ModSword(MainRegistry.enumToolMaterialPipeRusty).setUnlocalizedName("weapon_pipe_rusty").setFull3D().setTextureName(RefStrings.MODID + ":pipe_rusty");
		pipe_lead = new ModSword(MainRegistry.enumToolMaterialPipeLead).setUnlocalizedName("weapon_pipe_lead").setFull3D().setTextureName(RefStrings.MODID + ":pipe_lead");
		reer_graar = new ModSword(MainRegistry.enumToolMaterialTitanium).setUnlocalizedName("reer_graar").setFull3D().setTextureName(RefStrings.MODID + ":reer_graar_hd");

		crystal_horn = new ItemCustomLore().setUnlocalizedName("crystal_horn").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_horn");
		crystal_charred = new ItemCustomLore().setUnlocalizedName("crystal_charred").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_charred");
		
		bucket_mud = new ItemModBucket(ModBlocks.mud_block).setUnlocalizedName("bucket_mud").setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_mud");
		bucket_acid = new ItemModBucket(ModBlocks.acid_block).setUnlocalizedName("bucket_acid").setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_acid");
		bucket_toxic = new ItemModBucket(ModBlocks.toxic_block).setUnlocalizedName("bucket_toxic").setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_toxic");

		record_lc = new ItemModRecord("lc").setUnlocalizedName("record_lc").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_lc");
		record_ss = new ItemModRecord("ss").setUnlocalizedName("record_ss").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_ss");
		record_vc = new ItemModRecord("vc").setUnlocalizedName("record_vc").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_vc");

		polaroid = new ItemPolaroid().setUnlocalizedName("polaroid").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":polaroid_" + MainRegistry.polaroidID);
		glitch = new ItemGlitch().setUnlocalizedName("glitch").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glitch_" + MainRegistry.polaroidID);
		letter = new ItemStarterKit().setUnlocalizedName("letter").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":letter");
		book_secret = new ItemCustomLore().setUnlocalizedName("book_secret").setCreativeTab(MainRegistry.polaroidID == 11 ? MainRegistry.consumableTab : null).setTextureName(RefStrings.MODID + ":book_secret");
		burnt_bark = new ItemCustomLore().setUnlocalizedName("burnt_bark").setCreativeTab(null).setTextureName(RefStrings.MODID + ":burnt_bark");

		smoke1 = new Item().setUnlocalizedName("smoke1").setTextureName(RefStrings.MODID + ":smoke1");
		smoke2 = new Item().setUnlocalizedName("smoke2").setTextureName(RefStrings.MODID + ":smoke2");
		smoke3 = new Item().setUnlocalizedName("smoke3").setTextureName(RefStrings.MODID + ":smoke3");
		smoke4 = new Item().setUnlocalizedName("smoke4").setTextureName(RefStrings.MODID + ":smoke4");
		smoke5 = new Item().setUnlocalizedName("smoke5").setTextureName(RefStrings.MODID + ":smoke5");
		smoke6 = new Item().setUnlocalizedName("smoke6").setTextureName(RefStrings.MODID + ":smoke6");
		smoke7 = new Item().setUnlocalizedName("smoke7").setTextureName(RefStrings.MODID + ":smoke7");
		smoke8 = new Item().setUnlocalizedName("smoke8").setTextureName(RefStrings.MODID + ":smoke8");
		b_smoke1 = new Item().setUnlocalizedName("b_smoke1").setTextureName(RefStrings.MODID + ":b_smoke1");
		b_smoke2 = new Item().setUnlocalizedName("b_smoke2").setTextureName(RefStrings.MODID + ":b_smoke2");
		b_smoke3 = new Item().setUnlocalizedName("b_smoke3").setTextureName(RefStrings.MODID + ":b_smoke3");
		b_smoke4 = new Item().setUnlocalizedName("b_smoke4").setTextureName(RefStrings.MODID + ":b_smoke4");
		b_smoke5 = new Item().setUnlocalizedName("b_smoke5").setTextureName(RefStrings.MODID + ":b_smoke5");
		b_smoke6 = new Item().setUnlocalizedName("b_smoke6").setTextureName(RefStrings.MODID + ":b_smoke6");
		b_smoke7 = new Item().setUnlocalizedName("b_smoke7").setTextureName(RefStrings.MODID + ":b_smoke7");
		b_smoke8 = new Item().setUnlocalizedName("b_smoke8").setTextureName(RefStrings.MODID + ":b_smoke8");
		d_smoke1 = new Item().setUnlocalizedName("d_smoke1").setTextureName(RefStrings.MODID + ":d_smoke1");
		d_smoke2 = new Item().setUnlocalizedName("d_smoke2").setTextureName(RefStrings.MODID + ":d_smoke2");
		d_smoke3 = new Item().setUnlocalizedName("d_smoke3").setTextureName(RefStrings.MODID + ":d_smoke3");
		d_smoke4 = new Item().setUnlocalizedName("d_smoke4").setTextureName(RefStrings.MODID + ":d_smoke4");
		d_smoke5 = new Item().setUnlocalizedName("d_smoke5").setTextureName(RefStrings.MODID + ":d_smoke5");
		d_smoke6 = new Item().setUnlocalizedName("d_smoke6").setTextureName(RefStrings.MODID + ":d_smoke6");
		d_smoke7 = new Item().setUnlocalizedName("d_smoke7").setTextureName(RefStrings.MODID + ":d_smoke7");
		d_smoke8 = new Item().setUnlocalizedName("d_smoke8").setTextureName(RefStrings.MODID + ":d_smoke8");
		spill1 = new Item().setUnlocalizedName("spill1").setTextureName(RefStrings.MODID + ":spill1");
		spill2 = new Item().setUnlocalizedName("spill2").setTextureName(RefStrings.MODID + ":spill2");
		spill3 = new Item().setUnlocalizedName("spill3").setTextureName(RefStrings.MODID + ":spill3");
		spill4 = new Item().setUnlocalizedName("spill4").setTextureName(RefStrings.MODID + ":spill4");
		spill5 = new Item().setUnlocalizedName("spill5").setTextureName(RefStrings.MODID + ":spill5");
		spill6 = new Item().setUnlocalizedName("spill6").setTextureName(RefStrings.MODID + ":spill6");
		spill7 = new Item().setUnlocalizedName("spill7").setTextureName(RefStrings.MODID + ":spill7");
		spill8 = new Item().setUnlocalizedName("spill8").setTextureName(RefStrings.MODID + ":spill8");
		gas1 = new Item().setUnlocalizedName("gas1").setTextureName(RefStrings.MODID + ":gas1");
		gas2 = new Item().setUnlocalizedName("gas2").setTextureName(RefStrings.MODID + ":gas2");
		gas3 = new Item().setUnlocalizedName("gas3").setTextureName(RefStrings.MODID + ":gas3");
		gas4 = new Item().setUnlocalizedName("gas4").setTextureName(RefStrings.MODID + ":gas4");
		gas5 = new Item().setUnlocalizedName("gas5").setTextureName(RefStrings.MODID + ":gas5");
		gas6 = new Item().setUnlocalizedName("gas6").setTextureName(RefStrings.MODID + ":gas6");
		gas7 = new Item().setUnlocalizedName("gas7").setTextureName(RefStrings.MODID + ":gas7");
		gas8 = new Item().setUnlocalizedName("gas8").setTextureName(RefStrings.MODID + ":gas8");
		chlorine1 = new Item().setUnlocalizedName("chlorine1").setTextureName(RefStrings.MODID + ":chlorine1");
		chlorine2 = new Item().setUnlocalizedName("chlorine2").setTextureName(RefStrings.MODID + ":chlorine2");
		chlorine3 = new Item().setUnlocalizedName("chlorine3").setTextureName(RefStrings.MODID + ":chlorine3");
		chlorine4 = new Item().setUnlocalizedName("chlorine4").setTextureName(RefStrings.MODID + ":chlorine4");
		chlorine5 = new Item().setUnlocalizedName("chlorine5").setTextureName(RefStrings.MODID + ":chlorine5");
		chlorine6 = new Item().setUnlocalizedName("chlorine6").setTextureName(RefStrings.MODID + ":chlorine6");
		chlorine7 = new Item().setUnlocalizedName("chlorine7").setTextureName(RefStrings.MODID + ":chlorine7");
		chlorine8 = new Item().setUnlocalizedName("chlorine8").setTextureName(RefStrings.MODID + ":chlorine8");
		pc1 = new Item().setUnlocalizedName("pc1").setTextureName(RefStrings.MODID + ":pc1");
		pc2 = new Item().setUnlocalizedName("pc2").setTextureName(RefStrings.MODID + ":pc2");
		pc3 = new Item().setUnlocalizedName("pc3").setTextureName(RefStrings.MODID + ":pc3");
		pc4 = new Item().setUnlocalizedName("pc4").setTextureName(RefStrings.MODID + ":pc4");
		pc5 = new Item().setUnlocalizedName("pc5").setTextureName(RefStrings.MODID + ":pc5");
		pc6 = new Item().setUnlocalizedName("pc6").setTextureName(RefStrings.MODID + ":pc6");
		pc7 = new Item().setUnlocalizedName("pc7").setTextureName(RefStrings.MODID + ":pc7");
		pc8 = new Item().setUnlocalizedName("pc8").setTextureName(RefStrings.MODID + ":pc8");
		cloud1 = new Item().setUnlocalizedName("cloud1").setTextureName(RefStrings.MODID + ":cloud1");
		cloud2 = new Item().setUnlocalizedName("cloud2").setTextureName(RefStrings.MODID + ":cloud2");
		cloud3 = new Item().setUnlocalizedName("cloud3").setTextureName(RefStrings.MODID + ":cloud3");
		cloud4 = new Item().setUnlocalizedName("cloud4").setTextureName(RefStrings.MODID + ":cloud4");
		cloud5 = new Item().setUnlocalizedName("cloud5").setTextureName(RefStrings.MODID + ":cloud5");
		cloud6 = new Item().setUnlocalizedName("cloud6").setTextureName(RefStrings.MODID + ":cloud6");
		cloud7 = new Item().setUnlocalizedName("cloud7").setTextureName(RefStrings.MODID + ":cloud7");
		cloud8 = new Item().setUnlocalizedName("cloud8").setTextureName(RefStrings.MODID + ":cloud8");
		orange1 = new Item().setUnlocalizedName("orange1").setTextureName(RefStrings.MODID + ":orange1");
		orange2 = new Item().setUnlocalizedName("orange2").setTextureName(RefStrings.MODID + ":orange2");
		orange3 = new Item().setUnlocalizedName("orange3").setTextureName(RefStrings.MODID + ":orange3");
		orange4 = new Item().setUnlocalizedName("orange4").setTextureName(RefStrings.MODID + ":orange4");
		orange5 = new Item().setUnlocalizedName("orange5").setTextureName(RefStrings.MODID + ":orange5");
		orange6 = new Item().setUnlocalizedName("orange6").setTextureName(RefStrings.MODID + ":orange6");
		orange7 = new Item().setUnlocalizedName("orange7").setTextureName(RefStrings.MODID + ":orange7");
		orange8 = new Item().setUnlocalizedName("orange8").setTextureName(RefStrings.MODID + ":orange8");
		gasflame1 = new Item().setUnlocalizedName("gasflame1").setTextureName(RefStrings.MODID + ":gasflame1");
		gasflame2 = new Item().setUnlocalizedName("gasflame2").setTextureName(RefStrings.MODID + ":gasflame2");
		gasflame3 = new Item().setUnlocalizedName("gasflame3").setTextureName(RefStrings.MODID + ":gasflame3");
		gasflame4 = new Item().setUnlocalizedName("gasflame4").setTextureName(RefStrings.MODID + ":gasflame4");
		gasflame5 = new Item().setUnlocalizedName("gasflame5").setTextureName(RefStrings.MODID + ":gasflame5");
		gasflame6 = new Item().setUnlocalizedName("gasflame6").setTextureName(RefStrings.MODID + ":gasflame6");
		gasflame7 = new Item().setUnlocalizedName("gasflame7").setTextureName(RefStrings.MODID + ":gasflame7");
		gasflame8 = new Item().setUnlocalizedName("gasflame8").setTextureName(RefStrings.MODID + ":gasflame8");
		energy_ball = new Item().setUnlocalizedName("energy_ball").setTextureName(RefStrings.MODID + ":energy_ball");
		discharge = new Item().setUnlocalizedName("discharge").setTextureName(RefStrings.MODID + ":discharge");
		empblast = new Item().setUnlocalizedName("empblast").setTextureName(RefStrings.MODID + ":empblast");
		flame_1 = new Item().setUnlocalizedName("flame_1").setTextureName(RefStrings.MODID + ":flame_1");
		flame_2 = new Item().setUnlocalizedName("flame_2").setTextureName(RefStrings.MODID + ":flame_2");
		flame_3 = new Item().setUnlocalizedName("flame_3").setTextureName(RefStrings.MODID + ":flame_3");
		flame_4 = new Item().setUnlocalizedName("flame_4").setTextureName(RefStrings.MODID + ":flame_4");
		flame_5 = new Item().setUnlocalizedName("flame_5").setTextureName(RefStrings.MODID + ":flame_5");
		flame_6 = new Item().setUnlocalizedName("flame_6").setTextureName(RefStrings.MODID + ":flame_6");
		flame_7 = new Item().setUnlocalizedName("flame_7").setTextureName(RefStrings.MODID + ":flame_7");
		flame_8 = new Item().setUnlocalizedName("flame_8").setTextureName(RefStrings.MODID + ":flame_8");
		flame_9 = new Item().setUnlocalizedName("flame_9").setTextureName(RefStrings.MODID + ":flame_9");
		flame_10 = new Item().setUnlocalizedName("flame_10").setTextureName(RefStrings.MODID + ":flame_10");
		ln2_1 = new Item().setUnlocalizedName("ln2_1").setTextureName(RefStrings.MODID + ":ln2_1");
		ln2_2 = new Item().setUnlocalizedName("ln2_2").setTextureName(RefStrings.MODID + ":ln2_2");
		ln2_3 = new Item().setUnlocalizedName("ln2_3").setTextureName(RefStrings.MODID + ":ln2_3");
		ln2_4 = new Item().setUnlocalizedName("ln2_4").setTextureName(RefStrings.MODID + ":ln2_4");
		ln2_5 = new Item().setUnlocalizedName("ln2_5").setTextureName(RefStrings.MODID + ":ln2_5");
		ln2_6 = new Item().setUnlocalizedName("ln2_6").setTextureName(RefStrings.MODID + ":ln2_6");
		ln2_7 = new Item().setUnlocalizedName("ln2_7").setTextureName(RefStrings.MODID + ":ln2_7");
		ln2_8 = new Item().setUnlocalizedName("ln2_8").setTextureName(RefStrings.MODID + ":ln2_8");
		ln2_9 = new Item().setUnlocalizedName("ln2_9").setTextureName(RefStrings.MODID + ":ln2_9");
		ln2_10 = new Item().setUnlocalizedName("ln2_10").setTextureName(RefStrings.MODID + ":ln2_10");
		nothing = new Item().setUnlocalizedName("nothing").setTextureName(RefStrings.MODID + ":nothing");
		void_anim = new Item().setUnlocalizedName("void_anim").setTextureName(RefStrings.MODID + ":void_anim");
		
		mysteryshovel = new ItemMS().setUnlocalizedName("mysteryshovel").setFull3D().setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cursed_shovel");
		memory = new ItemBattery(Long.MAX_VALUE / 100L, 100000, 100000).setUnlocalizedName("memory").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mo8_anim");

		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.mud_fluid, 1000), new ItemStack(ModItems.bucket_mud));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.acid_fluid, 1000), new ItemStack(ModItems.bucket_acid));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.toxic_fluid, 1000), new ItemStack(ModItems.bucket_toxic));
		BucketHandler.INSTANCE.buckets.put(ModBlocks.mud_block, ModItems.bucket_mud);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.acid_block, ModItems.bucket_acid);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.toxic_block, ModItems.bucket_toxic);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
	}
	
	private static void registerItem() {
		//Weapons
		GameRegistry.registerItem(redstone_sword, redstone_sword.getUnlocalizedName());
		GameRegistry.registerItem(big_sword, big_sword.getUnlocalizedName());
		
		//Test Armor
		GameRegistry.registerItem(test_helmet, test_helmet.getUnlocalizedName());
		GameRegistry.registerItem(test_chestplate, test_chestplate.getUnlocalizedName());
		GameRegistry.registerItem(test_leggings, test_leggings.getUnlocalizedName());
		GameRegistry.registerItem(test_boots, test_boots.getUnlocalizedName());
		GameRegistry.registerItem(cape_test, cape_test.getUnlocalizedName());
		
		//Test Nuke
		GameRegistry.registerItem(test_nuke_igniter, test_nuke_igniter.getUnlocalizedName());
		GameRegistry.registerItem(test_nuke_propellant, test_nuke_propellant.getUnlocalizedName());
		GameRegistry.registerItem(test_nuke_tier1_shielding, test_nuke_tier1_shielding.getUnlocalizedName());
		GameRegistry.registerItem(test_nuke_tier2_shielding, test_nuke_tier2_shielding.getUnlocalizedName());
		GameRegistry.registerItem(test_nuke_tier1_bullet, test_nuke_tier1_bullet.getUnlocalizedName());
		GameRegistry.registerItem(test_nuke_tier2_bullet, test_nuke_tier2_bullet.getUnlocalizedName());
		GameRegistry.registerItem(test_nuke_tier1_target, test_nuke_tier1_target.getUnlocalizedName());
		GameRegistry.registerItem(test_nuke_tier2_target, test_nuke_tier2_target.getUnlocalizedName());

		//Ingots
		GameRegistry.registerItem(ingot_uranium, ingot_uranium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_u235, ingot_u235.getUnlocalizedName());
		GameRegistry.registerItem(ingot_u238, ingot_u238.getUnlocalizedName());
		GameRegistry.registerItem(ingot_u238m2, ingot_u238m2.getUnlocalizedName());
		GameRegistry.registerItem(ingot_plutonium, ingot_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu238, ingot_pu238.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu239, ingot_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu240, ingot_pu240.getUnlocalizedName());
		GameRegistry.registerItem(ingot_neptunium, ingot_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_titanium, ingot_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_copper, ingot_copper.getUnlocalizedName());
		GameRegistry.registerItem(ingot_red_copper, ingot_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(ingot_advanced_alloy, ingot_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(ingot_tungsten, ingot_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ingot_aluminium, ingot_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_steel, ingot_steel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_lead, ingot_lead.getUnlocalizedName());
		GameRegistry.registerItem(ingot_beryllium, ingot_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_dura_steel, ingot_dura_steel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_polymer, ingot_polymer.getUnlocalizedName());
		GameRegistry.registerItem(ingot_schrabidium, ingot_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_magnetized_tungsten, ingot_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ingot_combine_steel, ingot_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_solinium, ingot_solinium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_uranium_fuel, ingot_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_plutonium_fuel, ingot_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_mox_fuel, ingot_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_schrabidium_fuel, ingot_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_hes, ingot_hes.getUnlocalizedName());
		GameRegistry.registerItem(ingot_les, ingot_les.getUnlocalizedName());
		GameRegistry.registerItem(ingot_australium, ingot_australium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_weidanium, ingot_weidanium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_reiium, ingot_reiium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_unobtainium, ingot_unobtainium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_daffergon, ingot_daffergon.getUnlocalizedName());
		GameRegistry.registerItem(ingot_verticium, ingot_verticium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_lanthanium, ingot_lanthanium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_actinium, ingot_actinium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_desh, ingot_desh.getUnlocalizedName());
		GameRegistry.registerItem(ingot_starmetal, ingot_starmetal.getUnlocalizedName());
		GameRegistry.registerItem(ingot_saturnite, ingot_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(ingot_euphemium, ingot_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_dineutronium, ingot_dineutronium.getUnlocalizedName());

		//Dusts & Other
		GameRegistry.registerItem(lithium, lithium.getUnlocalizedName());
		GameRegistry.registerItem(solid_fuel, solid_fuel.getUnlocalizedName());
		GameRegistry.registerItem(sulfur, sulfur.getUnlocalizedName());
		GameRegistry.registerItem(niter, niter.getUnlocalizedName());
		GameRegistry.registerItem(fluorite, fluorite.getUnlocalizedName());
		GameRegistry.registerItem(powder_coal, powder_coal.getUnlocalizedName());
		GameRegistry.registerItem(powder_iron, powder_iron.getUnlocalizedName());
		GameRegistry.registerItem(powder_gold, powder_gold.getUnlocalizedName());
		GameRegistry.registerItem(powder_lapis, powder_lapis.getUnlocalizedName());
		GameRegistry.registerItem(powder_quartz, powder_quartz.getUnlocalizedName());
		GameRegistry.registerItem(powder_diamond, powder_diamond.getUnlocalizedName());
		GameRegistry.registerItem(powder_emerald, powder_emerald.getUnlocalizedName());
		GameRegistry.registerItem(powder_uranium, powder_uranium.getUnlocalizedName());
		GameRegistry.registerItem(powder_plutonium, powder_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(powder_neptunium, powder_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(powder_titanium, powder_titanium.getUnlocalizedName());
		GameRegistry.registerItem(powder_copper, powder_copper.getUnlocalizedName());
		GameRegistry.registerItem(powder_red_copper, powder_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(powder_advanced_alloy, powder_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(powder_tungsten, powder_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(powder_aluminium, powder_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(powder_steel, powder_steel.getUnlocalizedName());
		GameRegistry.registerItem(powder_lead, powder_lead.getUnlocalizedName());
		GameRegistry.registerItem(powder_yellowcake, powder_yellowcake.getUnlocalizedName());
		GameRegistry.registerItem(powder_beryllium, powder_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(powder_dura_steel, powder_dura_steel.getUnlocalizedName());
		GameRegistry.registerItem(powder_polymer, powder_polymer.getUnlocalizedName());
		GameRegistry.registerItem(powder_schrabidium, powder_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(powder_magnetized_tungsten, powder_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(powder_combine_steel, powder_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(powder_lithium, powder_lithium.getUnlocalizedName());
		GameRegistry.registerItem(powder_iodine, powder_iodine.getUnlocalizedName());
		GameRegistry.registerItem(powder_thorium, powder_thorium.getUnlocalizedName());
		GameRegistry.registerItem(powder_neodymium, powder_neodymium.getUnlocalizedName());
		GameRegistry.registerItem(powder_astatine, powder_astatine.getUnlocalizedName());
		GameRegistry.registerItem(powder_caesium, powder_caesium.getUnlocalizedName());
		GameRegistry.registerItem(powder_australium, powder_australium.getUnlocalizedName());
		GameRegistry.registerItem(powder_weidanium, powder_weidanium.getUnlocalizedName());
		GameRegistry.registerItem(powder_reiium, powder_reiium.getUnlocalizedName());
		GameRegistry.registerItem(powder_unobtainium, powder_unobtainium.getUnlocalizedName());
		GameRegistry.registerItem(powder_daffergon, powder_daffergon.getUnlocalizedName());
		GameRegistry.registerItem(powder_verticium, powder_verticium.getUnlocalizedName());
		GameRegistry.registerItem(powder_strontium, powder_strontium.getUnlocalizedName());
		GameRegistry.registerItem(powder_cobalt, powder_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(powder_bromine, powder_bromine.getUnlocalizedName());
		GameRegistry.registerItem(powder_niobium, powder_niobium.getUnlocalizedName());
		GameRegistry.registerItem(powder_tennessine, powder_tennessine.getUnlocalizedName());
		GameRegistry.registerItem(powder_cerium, powder_cerium.getUnlocalizedName());
		GameRegistry.registerItem(powder_lanthanium, powder_lanthanium.getUnlocalizedName());
		GameRegistry.registerItem(powder_actinium, powder_actinium.getUnlocalizedName());
		GameRegistry.registerItem(powder_magic, powder_magic.getUnlocalizedName());
		GameRegistry.registerItem(powder_desh_mix, powder_desh_mix.getUnlocalizedName());
		GameRegistry.registerItem(powder_desh, powder_desh.getUnlocalizedName());
		GameRegistry.registerItem(powder_nitan_mix, powder_nitan_mix.getUnlocalizedName());
		GameRegistry.registerItem(powder_spark_mix, powder_spark_mix.getUnlocalizedName());
		GameRegistry.registerItem(powder_meteorite, powder_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(powder_euphemium, powder_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(powder_dineutronium, powder_dineutronium.getUnlocalizedName());
		GameRegistry.registerItem(dust, dust.getUnlocalizedName());
		GameRegistry.registerItem(powder_lithium_tiny, powder_lithium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(powder_neodymium_tiny, powder_neodymium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(powder_cobalt_tiny, powder_cobalt_tiny.getUnlocalizedName());
		GameRegistry.registerItem(powder_niobium_tiny, powder_niobium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(powder_cerium_tiny, powder_cerium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(powder_lanthanium_tiny, powder_lanthanium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(powder_actinium_tiny, powder_actinium_tiny.getUnlocalizedName());
		GameRegistry.registerItem(powder_meteorite_tiny, powder_meteorite_tiny.getUnlocalizedName());
		
		//Powders
		GameRegistry.registerItem(powder_fire, powder_fire.getUnlocalizedName());
		GameRegistry.registerItem(powder_ice, powder_ice.getUnlocalizedName());
		GameRegistry.registerItem(powder_poison, powder_poison.getUnlocalizedName());
		GameRegistry.registerItem(powder_thermite, powder_thermite.getUnlocalizedName());
		GameRegistry.registerItem(powder_power, powder_power.getUnlocalizedName());
		
		//Fragments
		GameRegistry.registerItem(fragment_neodymium, fragment_neodymium.getUnlocalizedName());
		GameRegistry.registerItem(fragment_cobalt, fragment_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(fragment_niobium, fragment_niobium.getUnlocalizedName());
		GameRegistry.registerItem(fragment_cerium, fragment_cerium.getUnlocalizedName());
		GameRegistry.registerItem(fragment_lanthanium, fragment_lanthanium.getUnlocalizedName());
		GameRegistry.registerItem(fragment_actinium, fragment_actinium.getUnlocalizedName());
		GameRegistry.registerItem(fragment_meteorite, fragment_meteorite.getUnlocalizedName());
		
		//Things that look like rotten flesh but aren't
		GameRegistry.registerItem(biomass, biomass.getUnlocalizedName());
		GameRegistry.registerItem(biomass_compressed, biomass_compressed.getUnlocalizedName());

		//Nuggets
		GameRegistry.registerItem(nugget_uranium, nugget_uranium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_u235, nugget_u235.getUnlocalizedName());
		GameRegistry.registerItem(nugget_u238, nugget_u238.getUnlocalizedName());
		GameRegistry.registerItem(nugget_plutonium, nugget_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_pu238, nugget_pu238.getUnlocalizedName());
		GameRegistry.registerItem(nugget_pu239, nugget_pu239.getUnlocalizedName());
		GameRegistry.registerItem(nugget_pu240, nugget_pu240.getUnlocalizedName());
		GameRegistry.registerItem(nugget_neptunium, nugget_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_lead, nugget_lead.getUnlocalizedName());
		GameRegistry.registerItem(nugget_beryllium, nugget_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_schrabidium, nugget_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_solinium, nugget_solinium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_uranium_fuel, nugget_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(nugget_plutonium_fuel, nugget_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(nugget_mox_fuel, nugget_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(nugget_schrabidium_fuel, nugget_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(nugget_hes, nugget_hes.getUnlocalizedName());
		GameRegistry.registerItem(nugget_les, nugget_les.getUnlocalizedName());
		GameRegistry.registerItem(nugget_australium, nugget_australium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_weidanium, nugget_weidanium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_reiium, nugget_reiium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_unobtainium, nugget_unobtainium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_daffergon, nugget_daffergon.getUnlocalizedName());
		GameRegistry.registerItem(nugget_verticium, nugget_verticium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_desh, nugget_desh.getUnlocalizedName());
		GameRegistry.registerItem(nugget_euphemium, nugget_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_dineutronium, nugget_dineutronium.getUnlocalizedName());

		//Plates
		GameRegistry.registerItem(plate_iron, plate_iron.getUnlocalizedName());
		GameRegistry.registerItem(plate_gold, plate_gold.getUnlocalizedName());
		GameRegistry.registerItem(plate_titanium, plate_titanium.getUnlocalizedName());
		GameRegistry.registerItem(plate_aluminium, plate_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(plate_steel, plate_steel.getUnlocalizedName());
		GameRegistry.registerItem(plate_lead, plate_lead.getUnlocalizedName());
		GameRegistry.registerItem(plate_copper, plate_copper.getUnlocalizedName());
		GameRegistry.registerItem(plate_advanced_alloy, plate_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(neutron_reflector, neutron_reflector.getUnlocalizedName());
		GameRegistry.registerItem(plate_schrabidium, plate_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(plate_combine_steel, plate_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(plate_mixed, plate_mixed.getUnlocalizedName());
		GameRegistry.registerItem(plate_saturnite, plate_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(plate_paa, plate_paa.getUnlocalizedName());
		GameRegistry.registerItem(plate_polymer, plate_polymer.getUnlocalizedName());
		GameRegistry.registerItem(plate_dalekanium, plate_dalekanium.getUnlocalizedName());
		GameRegistry.registerItem(plate_desh, plate_desh.getUnlocalizedName());
		GameRegistry.registerItem(plate_euphemium, plate_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(plate_dineutronium, plate_dineutronium.getUnlocalizedName());
		
		//Boards
		GameRegistry.registerItem(board_copper, board_copper.getUnlocalizedName());
		
		//Bolts
		GameRegistry.registerItem(bolt_dura_steel, bolt_dura_steel.getUnlocalizedName());
		GameRegistry.registerItem(bolt_tungsten, bolt_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(bolt_compound, bolt_compound.getUnlocalizedName());
		
		//Other Plates
		GameRegistry.registerItem(hazmat_cloth, hazmat_cloth.getUnlocalizedName());
		GameRegistry.registerItem(asbestos_cloth, asbestos_cloth.getUnlocalizedName());
		GameRegistry.registerItem(filter_coal, filter_coal.getUnlocalizedName());
		
		//Wires
		GameRegistry.registerItem(wire_aluminium, wire_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(wire_copper, wire_copper.getUnlocalizedName());
		GameRegistry.registerItem(wire_tungsten, wire_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(wire_red_copper, wire_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(wire_advanced_alloy, wire_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(wire_gold, wire_gold.getUnlocalizedName());
		GameRegistry.registerItem(wire_schrabidium, wire_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(wire_magnetized_tungsten, wire_magnetized_tungsten.getUnlocalizedName());
		
		//Parts
		GameRegistry.registerItem(coil_copper, coil_copper.getUnlocalizedName());
		GameRegistry.registerItem(coil_copper_torus, coil_copper_torus.getUnlocalizedName());
		GameRegistry.registerItem(coil_advanced_alloy, coil_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(coil_advanced_torus, coil_advanced_torus.getUnlocalizedName());
		GameRegistry.registerItem(coil_gold, coil_gold.getUnlocalizedName());
		GameRegistry.registerItem(coil_gold_torus, coil_gold_torus.getUnlocalizedName());
		GameRegistry.registerItem(coil_tungsten, coil_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(coil_magnetized_tungsten, coil_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(tank_steel, tank_steel.getUnlocalizedName());
		GameRegistry.registerItem(motor, motor.getUnlocalizedName());
		GameRegistry.registerItem(centrifuge_element, centrifuge_element.getUnlocalizedName());
		GameRegistry.registerItem(centrifuge_tower, centrifuge_tower.getUnlocalizedName());
		GameRegistry.registerItem(magnet_dee, magnet_dee.getUnlocalizedName());
		GameRegistry.registerItem(magnet_circular, magnet_circular.getUnlocalizedName());
		GameRegistry.registerItem(cyclotron_tower, cyclotron_tower.getUnlocalizedName());
		GameRegistry.registerItem(reactor_core, reactor_core.getUnlocalizedName());
		GameRegistry.registerItem(rtg_unit, rtg_unit.getUnlocalizedName());
		GameRegistry.registerItem(thermo_unit_empty, thermo_unit_empty.getUnlocalizedName());
		GameRegistry.registerItem(thermo_unit_endo, thermo_unit_endo.getUnlocalizedName());
		GameRegistry.registerItem(thermo_unit_exo, thermo_unit_exo.getUnlocalizedName());
		GameRegistry.registerItem(levitation_unit, levitation_unit.getUnlocalizedName());
		GameRegistry.registerItem(pipes_steel, pipes_steel.getUnlocalizedName());
		GameRegistry.registerItem(drill_titanium, drill_titanium.getUnlocalizedName());
		GameRegistry.registerItem(photo_panel, photo_panel.getUnlocalizedName());
		
		//Teleporter Parts
		GameRegistry.registerItem(telepad, telepad.getUnlocalizedName());
		GameRegistry.registerItem(entanglement_kit, entanglement_kit.getUnlocalizedName());
		
		//AMS Parts
		GameRegistry.registerItem(component_limiter, component_limiter.getUnlocalizedName());
		GameRegistry.registerItem(component_emitter, component_emitter.getUnlocalizedName());
		
		//Bomb Parts
		GameRegistry.registerItem(cap_aluminium, cap_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(hull_small_steel, hull_small_steel.getUnlocalizedName());
		GameRegistry.registerItem(hull_small_aluminium, hull_small_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(hull_big_steel, hull_big_steel.getUnlocalizedName());
		GameRegistry.registerItem(hull_big_aluminium, hull_big_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(hull_big_titanium, hull_big_titanium.getUnlocalizedName());
		GameRegistry.registerItem(fins_flat, fins_flat.getUnlocalizedName());
		GameRegistry.registerItem(fins_small_steel, fins_small_steel.getUnlocalizedName());
		GameRegistry.registerItem(fins_big_steel, fins_big_steel.getUnlocalizedName());
		GameRegistry.registerItem(fins_tri_steel, fins_tri_steel.getUnlocalizedName());
		GameRegistry.registerItem(fins_quad_titanium, fins_quad_titanium.getUnlocalizedName());
		GameRegistry.registerItem(sphere_steel, sphere_steel.getUnlocalizedName());
		GameRegistry.registerItem(pedestal_steel, pedestal_steel.getUnlocalizedName());
		GameRegistry.registerItem(dysfunctional_reactor, dysfunctional_reactor.getUnlocalizedName());
		GameRegistry.registerItem(rotor_steel, rotor_steel.getUnlocalizedName());
		GameRegistry.registerItem(generator_steel, generator_steel.getUnlocalizedName());
		GameRegistry.registerItem(blade_titanium, blade_titanium.getUnlocalizedName());
		GameRegistry.registerItem(blade_tungsten, blade_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(turbine_titanium, turbine_titanium.getUnlocalizedName());
		GameRegistry.registerItem(turbine_tungsten, turbine_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(generator_front, generator_front.getUnlocalizedName());
		GameRegistry.registerItem(toothpicks, toothpicks.getUnlocalizedName());
		GameRegistry.registerItem(ducttape, ducttape.getUnlocalizedName());
		GameRegistry.registerItem(catalyst_clay, catalyst_clay.getUnlocalizedName());
		GameRegistry.registerItem(missile_assembly, missile_assembly.getUnlocalizedName());
		GameRegistry.registerItem(warhead_generic_small, warhead_generic_small.getUnlocalizedName());
		GameRegistry.registerItem(warhead_generic_medium, warhead_generic_medium.getUnlocalizedName());
		GameRegistry.registerItem(warhead_generic_large, warhead_generic_large.getUnlocalizedName());
		GameRegistry.registerItem(warhead_incendiary_small, warhead_incendiary_small.getUnlocalizedName());
		GameRegistry.registerItem(warhead_incendiary_medium, warhead_incendiary_medium.getUnlocalizedName());
		GameRegistry.registerItem(warhead_incendiary_large, warhead_incendiary_large.getUnlocalizedName());
		GameRegistry.registerItem(warhead_cluster_small, warhead_cluster_small.getUnlocalizedName());
		GameRegistry.registerItem(warhead_cluster_medium, warhead_cluster_medium.getUnlocalizedName());
		GameRegistry.registerItem(warhead_cluster_large, warhead_cluster_large.getUnlocalizedName());
		GameRegistry.registerItem(warhead_buster_small, warhead_buster_small.getUnlocalizedName());
		GameRegistry.registerItem(warhead_buster_medium, warhead_buster_medium.getUnlocalizedName());
		GameRegistry.registerItem(warhead_buster_large, warhead_buster_large.getUnlocalizedName());
		GameRegistry.registerItem(warhead_nuclear, warhead_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(warhead_mirvlet, warhead_mirvlet.getUnlocalizedName());
		GameRegistry.registerItem(warhead_mirv, warhead_mirv.getUnlocalizedName());
		GameRegistry.registerItem(warhead_thermo_endo, warhead_thermo_endo.getUnlocalizedName());
		GameRegistry.registerItem(warhead_thermo_exo, warhead_thermo_exo.getUnlocalizedName());
		GameRegistry.registerItem(fuel_tank_small, fuel_tank_small.getUnlocalizedName());
		GameRegistry.registerItem(fuel_tank_medium, fuel_tank_medium.getUnlocalizedName());
		GameRegistry.registerItem(fuel_tank_large, fuel_tank_large.getUnlocalizedName());
		GameRegistry.registerItem(thruster_small, thruster_small.getUnlocalizedName());
		GameRegistry.registerItem(thruster_medium, thruster_medium.getUnlocalizedName());
		GameRegistry.registerItem(thruster_large, thruster_large.getUnlocalizedName());
		GameRegistry.registerItem(thruster_nuclear, thruster_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(sat_base, sat_base.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_mapper, sat_head_mapper.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_scanner, sat_head_scanner.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_radar, sat_head_radar.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_laser, sat_head_laser.getUnlocalizedName());
		GameRegistry.registerItem(sat_head_resonator, sat_head_resonator.getUnlocalizedName());
		
		//Chopper parts
		GameRegistry.registerItem(chopper_head, chopper_head.getUnlocalizedName());
		GameRegistry.registerItem(chopper_gun, chopper_gun.getUnlocalizedName());
		GameRegistry.registerItem(chopper_torso, chopper_torso.getUnlocalizedName());
		GameRegistry.registerItem(chopper_tail, chopper_tail.getUnlocalizedName());
		GameRegistry.registerItem(chopper_wing, chopper_wing.getUnlocalizedName());
		GameRegistry.registerItem(chopper_blades, chopper_blades.getUnlocalizedName());
		GameRegistry.registerItem(combine_scrap, combine_scrap.getUnlocalizedName());
		
		//Hammer Parts
		GameRegistry.registerItem(shimmer_head, shimmer_head.getUnlocalizedName());
		GameRegistry.registerItem(shimmer_axe_head, shimmer_axe_head.getUnlocalizedName());
		GameRegistry.registerItem(shimmer_handle, shimmer_handle.getUnlocalizedName());
		
		//Circuits
		GameRegistry.registerItem(circuit_raw, circuit_raw.getUnlocalizedName());
		GameRegistry.registerItem(circuit_aluminium, circuit_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(circuit_copper, circuit_copper.getUnlocalizedName());
		GameRegistry.registerItem(circuit_red_copper, circuit_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(circuit_gold, circuit_gold.getUnlocalizedName());
		GameRegistry.registerItem(circuit_schrabidium, circuit_schrabidium.getUnlocalizedName());
		
		//Military Circuits
		GameRegistry.registerItem(circuit_targeting_tier1, circuit_targeting_tier1.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier2, circuit_targeting_tier2.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier3, circuit_targeting_tier3.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier4, circuit_targeting_tier4.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier5, circuit_targeting_tier5.getUnlocalizedName());
		GameRegistry.registerItem(circuit_targeting_tier6, circuit_targeting_tier6.getUnlocalizedName());
		
		//Gun Mechanisms
		GameRegistry.registerItem(mechanism_revolver_1, mechanism_revolver_1.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_revolver_2, mechanism_revolver_2.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_rifle_1, mechanism_rifle_1.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_rifle_2, mechanism_rifle_2.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_launcher_1, mechanism_launcher_1.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_launcher_2, mechanism_launcher_2.getUnlocalizedName());
		GameRegistry.registerItem(mechanism_special, mechanism_special.getUnlocalizedName());
		
		//Wiring
		GameRegistry.registerItem(wiring_red_copper, wiring_red_copper.getUnlocalizedName());
		
		//Flame War in a Box
		GameRegistry.registerItem(flame_pony, flame_pony.getUnlocalizedName());
		GameRegistry.registerItem(flame_conspiracy, flame_conspiracy.getUnlocalizedName());
		GameRegistry.registerItem(flame_politics, flame_politics.getUnlocalizedName());
		GameRegistry.registerItem(flame_opinion, flame_opinion.getUnlocalizedName());
		
		//Pellets
		GameRegistry.registerItem(pellet_rtg, pellet_rtg.getUnlocalizedName());
		GameRegistry.registerItem(pellet_rtg_weak, pellet_rtg_weak.getUnlocalizedName());
		GameRegistry.registerItem(tritium_deuterium_cake, tritium_deuterium_cake.getUnlocalizedName());
		GameRegistry.registerItem(pellet_cluster, pellet_cluster.getUnlocalizedName());
		GameRegistry.registerItem(pellet_buckshot, pellet_buckshot.getUnlocalizedName());
		GameRegistry.registerItem(pellet_gas, pellet_gas.getUnlocalizedName());

		GameRegistry.registerItem(magnetron, magnetron.getUnlocalizedName());

		GameRegistry.registerItem(pellet_coal, pellet_coal.getUnlocalizedName());
		
		//Watz Pellets
		GameRegistry.registerItem(pellet_schrabidium, pellet_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_hes, pellet_hes.getUnlocalizedName());
		GameRegistry.registerItem(pellet_mes, pellet_mes.getUnlocalizedName());
		GameRegistry.registerItem(pellet_les, pellet_les.getUnlocalizedName());
		GameRegistry.registerItem(pellet_beryllium, pellet_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_neptunium, pellet_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(pellet_lead, pellet_lead.getUnlocalizedName());
		GameRegistry.registerItem(pellet_advanced, pellet_advanced.getUnlocalizedName());
		
		//Engine Pieces
		GameRegistry.registerItem(piston_selenium, piston_selenium.getUnlocalizedName());
		
		//Cells
		GameRegistry.registerItem(cell_empty, cell_empty.getUnlocalizedName());
		GameRegistry.registerItem(cell_uf6, cell_uf6.getUnlocalizedName());
		GameRegistry.registerItem(cell_puf6, cell_puf6.getUnlocalizedName());
		GameRegistry.registerItem(cell_deuterium, cell_deuterium.getUnlocalizedName());
		GameRegistry.registerItem(cell_tritium, cell_tritium.getUnlocalizedName());
		GameRegistry.registerItem(cell_sas3, cell_sas3.getUnlocalizedName());
		GameRegistry.registerItem(cell_antimatter, cell_antimatter.getUnlocalizedName());
		GameRegistry.registerItem(cell_anti_schrabidium, cell_anti_schrabidium.getUnlocalizedName());
		
		//OMG how the hell is that even possible!?
		GameRegistry.registerItem(singularity, singularity.getUnlocalizedName());
		GameRegistry.registerItem(singularity_counter_resonant, singularity_counter_resonant.getUnlocalizedName());
		GameRegistry.registerItem(singularity_super_heated, singularity_super_heated.getUnlocalizedName());
		GameRegistry.registerItem(black_hole, black_hole.getUnlocalizedName());
		GameRegistry.registerItem(singularity_spark, singularity_spark.getUnlocalizedName());
		GameRegistry.registerItem(crystal_xen, crystal_xen.getUnlocalizedName());
		GameRegistry.registerItem(pellet_antimatter, pellet_antimatter.getUnlocalizedName());
		
		//Infinite Tanks
		GameRegistry.registerItem(inf_water, inf_water.getUnlocalizedName());
		
		//Large Tanks
		GameRegistry.registerItem(tank_waste, tank_waste.getUnlocalizedName());
		
		//Canisters
		GameRegistry.registerItem(canister_empty, canister_empty.getUnlocalizedName());
		GameRegistry.registerItem(canister_oil, canister_oil.getUnlocalizedName());
		GameRegistry.registerItem(canister_heavyoil, canister_heavyoil.getUnlocalizedName());
		GameRegistry.registerItem(canister_bitumen, canister_bitumen.getUnlocalizedName());
		GameRegistry.registerItem(canister_smear, canister_smear.getUnlocalizedName());
		GameRegistry.registerItem(canister_heatingoil, canister_heatingoil.getUnlocalizedName());
		GameRegistry.registerItem(canister_canola, canister_canola.getUnlocalizedName());
		GameRegistry.registerItem(canister_naphtha, canister_naphtha.getUnlocalizedName());
		GameRegistry.registerItem(canister_fuel, canister_fuel.getUnlocalizedName());
		GameRegistry.registerItem(canister_kerosene, canister_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(canister_lightoil, canister_lightoil.getUnlocalizedName());
		GameRegistry.registerItem(canister_reoil, canister_reoil.getUnlocalizedName());
		GameRegistry.registerItem(canister_petroil, canister_petroil.getUnlocalizedName());
		GameRegistry.registerItem(canister_biofuel, canister_biofuel.getUnlocalizedName());
		GameRegistry.registerItem(canister_napalm, canister_napalm.getUnlocalizedName());
		GameRegistry.registerItem(canister_NITAN, canister_NITAN.getUnlocalizedName());
		
		//Gas Tanks
		GameRegistry.registerItem(gas_empty, gas_empty.getUnlocalizedName());
		GameRegistry.registerItem(gas_full, gas_full.getUnlocalizedName());
		GameRegistry.registerItem(gas_petroleum, gas_petroleum.getUnlocalizedName());
		GameRegistry.registerItem(gas_biogas, gas_biogas.getUnlocalizedName());
		
		//Universal Tank
		GameRegistry.registerItem(fluid_tank_empty, fluid_tank_empty.getUnlocalizedName());
		GameRegistry.registerItem(fluid_tank_full, fluid_tank_full.getUnlocalizedName());
		GameRegistry.registerItem(fluid_barrel_empty, fluid_barrel_empty.getUnlocalizedName());
		GameRegistry.registerItem(fluid_barrel_full, fluid_barrel_full.getUnlocalizedName());
		GameRegistry.registerItem(fluid_barrel_infinite, fluid_barrel_infinite.getUnlocalizedName());
		
		//Batteries
		GameRegistry.registerItem(battery_generic, battery_generic.getUnlocalizedName());
		GameRegistry.registerItem(battery_red_cell, battery_red_cell.getUnlocalizedName());
		GameRegistry.registerItem(battery_red_cell_6, battery_red_cell_6.getUnlocalizedName());
		GameRegistry.registerItem(battery_red_cell_24, battery_red_cell_24.getUnlocalizedName());
		GameRegistry.registerItem(battery_advanced, battery_advanced.getUnlocalizedName());
		GameRegistry.registerItem(battery_advanced_cell, battery_advanced_cell.getUnlocalizedName());
		GameRegistry.registerItem(battery_advanced_cell_4, battery_advanced_cell_4.getUnlocalizedName());
		GameRegistry.registerItem(battery_advanced_cell_12, battery_advanced_cell_12.getUnlocalizedName());
		GameRegistry.registerItem(battery_lithium, battery_lithium.getUnlocalizedName());
		GameRegistry.registerItem(battery_lithium_cell, battery_lithium_cell.getUnlocalizedName());
		GameRegistry.registerItem(battery_lithium_cell_3, battery_lithium_cell_3.getUnlocalizedName());
		GameRegistry.registerItem(battery_lithium_cell_6, battery_lithium_cell_6.getUnlocalizedName());
		GameRegistry.registerItem(battery_schrabidium, battery_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(battery_schrabidium_cell, battery_schrabidium_cell.getUnlocalizedName());
		GameRegistry.registerItem(battery_schrabidium_cell_2, battery_schrabidium_cell_2.getUnlocalizedName());
		GameRegistry.registerItem(battery_schrabidium_cell_4, battery_schrabidium_cell_4.getUnlocalizedName());
		GameRegistry.registerItem(battery_spark, battery_spark.getUnlocalizedName());
		GameRegistry.registerItem(battery_spark_cell_6, battery_spark_cell_6.getUnlocalizedName());
		GameRegistry.registerItem(battery_spark_cell_25, battery_spark_cell_25.getUnlocalizedName());
		GameRegistry.registerItem(battery_spark_cell_100, battery_spark_cell_100.getUnlocalizedName());
		GameRegistry.registerItem(battery_spark_cell_1000, battery_spark_cell_1000.getUnlocalizedName());
		GameRegistry.registerItem(battery_spark_cell_2500, battery_spark_cell_2500.getUnlocalizedName());
		GameRegistry.registerItem(battery_spark_cell_10000, battery_spark_cell_10000.getUnlocalizedName());
		GameRegistry.registerItem(battery_spark_cell_power, battery_spark_cell_power.getUnlocalizedName());
		GameRegistry.registerItem(battery_creative, battery_creative.getUnlocalizedName());
		GameRegistry.registerItem(battery_su, battery_su.getUnlocalizedName());
		GameRegistry.registerItem(battery_su_l, battery_su_l.getUnlocalizedName());
		GameRegistry.registerItem(battery_potato, battery_potato.getUnlocalizedName());
		GameRegistry.registerItem(battery_potatos, battery_potatos.getUnlocalizedName());
		GameRegistry.registerItem(battery_steam, battery_steam.getUnlocalizedName());
		GameRegistry.registerItem(battery_steam_large, battery_steam_large.getUnlocalizedName());
		GameRegistry.registerItem(fusion_core, fusion_core.getUnlocalizedName());
		GameRegistry.registerItem(energy_core, energy_core.getUnlocalizedName());
		GameRegistry.registerItem(fusion_core_infinite, fusion_core_infinite.getUnlocalizedName());
		GameRegistry.registerItem(factory_core_titanium, factory_core_titanium.getUnlocalizedName());
		GameRegistry.registerItem(factory_core_advanced, factory_core_advanced.getUnlocalizedName());
		
		//Dynospheres
		GameRegistry.registerItem(dynosphere_base, dynosphere_base.getUnlocalizedName());
		GameRegistry.registerItem(dynosphere_desh, dynosphere_desh.getUnlocalizedName());
		GameRegistry.registerItem(dynosphere_desh_charged, dynosphere_desh_charged.getUnlocalizedName());
		GameRegistry.registerItem(dynosphere_schrabidium, dynosphere_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(dynosphere_schrabidium_charged, dynosphere_schrabidium_charged.getUnlocalizedName());
		GameRegistry.registerItem(dynosphere_euphemium, dynosphere_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(dynosphere_euphemium_charged, dynosphere_euphemium_charged.getUnlocalizedName());
		GameRegistry.registerItem(dynosphere_dineutronium, dynosphere_dineutronium.getUnlocalizedName());
		GameRegistry.registerItem(dynosphere_dineutronium_charged, dynosphere_dineutronium_charged.getUnlocalizedName());
		
		//Template Folder
		GameRegistry.registerItem(template_folder, template_folder.getUnlocalizedName());
		
		//Hydraulic Press Stamps
		GameRegistry.registerItem(stamp_stone_flat, stamp_stone_flat.getUnlocalizedName());
		GameRegistry.registerItem(stamp_stone_plate, stamp_stone_plate.getUnlocalizedName());
		GameRegistry.registerItem(stamp_stone_wire, stamp_stone_wire.getUnlocalizedName());
		GameRegistry.registerItem(stamp_stone_circuit, stamp_stone_circuit.getUnlocalizedName());
		GameRegistry.registerItem(stamp_iron_flat, stamp_iron_flat.getUnlocalizedName());
		GameRegistry.registerItem(stamp_iron_plate, stamp_iron_plate.getUnlocalizedName());
		GameRegistry.registerItem(stamp_iron_wire, stamp_iron_wire.getUnlocalizedName());
		GameRegistry.registerItem(stamp_iron_circuit, stamp_iron_circuit.getUnlocalizedName());
		GameRegistry.registerItem(stamp_steel_flat, stamp_steel_flat.getUnlocalizedName());
		GameRegistry.registerItem(stamp_steel_plate, stamp_steel_plate.getUnlocalizedName());
		GameRegistry.registerItem(stamp_steel_wire, stamp_steel_wire.getUnlocalizedName());
		GameRegistry.registerItem(stamp_steel_circuit, stamp_steel_circuit.getUnlocalizedName());
		GameRegistry.registerItem(stamp_titanium_flat, stamp_titanium_flat.getUnlocalizedName());
		GameRegistry.registerItem(stamp_titanium_plate, stamp_titanium_plate.getUnlocalizedName());
		GameRegistry.registerItem(stamp_titanium_wire, stamp_titanium_wire.getUnlocalizedName());
		GameRegistry.registerItem(stamp_titanium_circuit, stamp_titanium_circuit.getUnlocalizedName());
		GameRegistry.registerItem(stamp_obsidian_flat, stamp_obsidian_flat.getUnlocalizedName());
		GameRegistry.registerItem(stamp_obsidian_plate, stamp_obsidian_plate.getUnlocalizedName());
		GameRegistry.registerItem(stamp_obsidian_wire, stamp_obsidian_wire.getUnlocalizedName());
		GameRegistry.registerItem(stamp_obsidian_circuit, stamp_obsidian_circuit.getUnlocalizedName());
		GameRegistry.registerItem(stamp_schrabidium_flat, stamp_schrabidium_flat.getUnlocalizedName());
		GameRegistry.registerItem(stamp_schrabidium_plate, stamp_schrabidium_plate.getUnlocalizedName());
		GameRegistry.registerItem(stamp_schrabidium_wire, stamp_schrabidium_wire.getUnlocalizedName());
		GameRegistry.registerItem(stamp_schrabidium_circuit, stamp_schrabidium_circuit.getUnlocalizedName());
		
		//Machine Upgrades
		GameRegistry.registerItem(upgrade_template, upgrade_template.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_speed_1, upgrade_speed_1.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_speed_2, upgrade_speed_2.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_speed_3, upgrade_speed_3.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_effect_1, upgrade_effect_1.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_effect_2, upgrade_effect_2.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_effect_3, upgrade_effect_3.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_power_1, upgrade_power_1.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_power_2, upgrade_power_2.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_power_3, upgrade_power_3.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_fortune_1, upgrade_fortune_1.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_fortune_2, upgrade_fortune_2.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_fortune_3, upgrade_fortune_3.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_afterburn_1, upgrade_afterburn_1.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_afterburn_2, upgrade_afterburn_2.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_afterburn_3, upgrade_afterburn_3.getUnlocalizedName());
		
		//Machine Templates
		GameRegistry.registerItem(siren_track, siren_track.getUnlocalizedName());
		GameRegistry.registerItem(fluid_identifier, fluid_identifier.getUnlocalizedName());
		GameRegistry.registerItem(fluid_icon, fluid_icon.getUnlocalizedName());
		GameRegistry.registerItem(assembly_template, assembly_template.getUnlocalizedName());
		GameRegistry.registerItem(chemistry_template, chemistry_template.getUnlocalizedName());
		GameRegistry.registerItem(chemistry_icon, chemistry_icon.getUnlocalizedName());
		
		//Machine Items
		GameRegistry.registerItem(fuse, fuse.getUnlocalizedName());
		GameRegistry.registerItem(redcoil_capacitor, redcoil_capacitor.getUnlocalizedName());
		GameRegistry.registerItem(titanium_filter, titanium_filter.getUnlocalizedName());
		GameRegistry.registerItem(screwdriver, screwdriver.getUnlocalizedName());
		GameRegistry.registerItem(overfuse, overfuse.getUnlocalizedName());
		
		//Particle Collider Items
		GameRegistry.registerItem(crystal_energy, crystal_energy.getUnlocalizedName());
		GameRegistry.registerItem(pellet_coolant, pellet_coolant.getUnlocalizedName());
		
		//Particle Collider Fuel
		GameRegistry.registerItem(part_lithium, part_lithium.getUnlocalizedName());
		GameRegistry.registerItem(part_beryllium, part_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(part_carbon, part_carbon.getUnlocalizedName());
		GameRegistry.registerItem(part_copper, part_copper.getUnlocalizedName());
		GameRegistry.registerItem(part_plutonium, part_plutonium.getUnlocalizedName());
		
		//Catalyst Rune Sigils
		GameRegistry.registerItem(rune_blank, rune_blank.getUnlocalizedName());
		GameRegistry.registerItem(rune_isa, rune_isa.getUnlocalizedName());
		GameRegistry.registerItem(rune_dagaz, rune_dagaz.getUnlocalizedName());
		GameRegistry.registerItem(rune_hagalaz, rune_hagalaz.getUnlocalizedName());
		GameRegistry.registerItem(rune_jera, rune_jera.getUnlocalizedName());
		GameRegistry.registerItem(rune_thurisaz, rune_thurisaz.getUnlocalizedName());
		
		//AMS Catalysts
		GameRegistry.registerItem(ams_catalyst_blank, ams_catalyst_blank.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_aluminium, ams_catalyst_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_beryllium, ams_catalyst_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_caesium, ams_catalyst_caesium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_cerium, ams_catalyst_cerium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_cobalt, ams_catalyst_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_copper, ams_catalyst_copper.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_euphemium, ams_catalyst_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_dineutronium, ams_catalyst_dineutronium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_iron, ams_catalyst_iron.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_lithium, ams_catalyst_lithium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_niobium, ams_catalyst_niobium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_schrabidium, ams_catalyst_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_strontium, ams_catalyst_strontium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_thorium, ams_catalyst_thorium.getUnlocalizedName());
		GameRegistry.registerItem(ams_catalyst_tungsten, ams_catalyst_tungsten.getUnlocalizedName());
		
		//Shredder Blades
		GameRegistry.registerItem(blades_aluminium, blades_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(blades_gold, blades_gold.getUnlocalizedName());
		GameRegistry.registerItem(blades_iron, blades_iron.getUnlocalizedName());
		GameRegistry.registerItem(blades_steel, blades_steel.getUnlocalizedName());
		GameRegistry.registerItem(blades_titanium, blades_titanium.getUnlocalizedName());
		GameRegistry.registerItem(blades_advanced_alloy, blades_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(blades_combine_steel, blades_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(blades_schrabidium, blades_schrabidium.getUnlocalizedName());
		
		//Generator Stuff
		GameRegistry.registerItem(thermo_element, thermo_element.getUnlocalizedName());
		GameRegistry.registerItem(limiter, limiter.getUnlocalizedName());
		
		//AMS Components
		GameRegistry.registerItem(ams_focus_blank, ams_focus_blank.getUnlocalizedName());
		GameRegistry.registerItem(ams_focus_limiter, ams_focus_limiter.getUnlocalizedName());
		GameRegistry.registerItem(ams_focus_booster, ams_focus_booster.getUnlocalizedName());
		GameRegistry.registerItem(ams_muzzle, ams_muzzle.getUnlocalizedName());
		GameRegistry.registerItem(ams_core_sing, ams_core_sing.getUnlocalizedName());
		GameRegistry.registerItem(ams_core_wormhole, ams_core_wormhole.getUnlocalizedName());
		GameRegistry.registerItem(ams_core_eyeofharmony, ams_core_eyeofharmony.getUnlocalizedName());
		GameRegistry.registerItem(ams_core_thingy, ams_core_thingy.getUnlocalizedName());
		
		//Fuel Rods
		GameRegistry.registerItem(rod_empty, rod_empty.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_empty, rod_dual_empty.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_empty, rod_quad_empty.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_uranium, rod_uranium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_uranium, rod_dual_uranium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_uranium, rod_quad_uranium.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_u235, rod_u235.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_u235, rod_dual_u235.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_u235, rod_quad_u235.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_u238, rod_u238.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_u238, rod_dual_u238.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_u238, rod_quad_u238.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_plutonium, rod_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_plutonium, rod_dual_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_plutonium, rod_quad_plutonium.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_pu238, rod_pu238.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_pu238, rod_dual_pu238.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_pu238, rod_quad_pu238.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_pu239, rod_pu239.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_pu239, rod_dual_pu239.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_pu239, rod_quad_pu239.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_pu240, rod_pu240.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_pu240, rod_dual_pu240.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_pu240, rod_quad_pu240.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_neptunium, rod_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_neptunium, rod_dual_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_neptunium, rod_quad_neptunium.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_lead, rod_lead.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_lead, rod_dual_lead.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_lead, rod_quad_lead.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_schrabidium, rod_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_schrabidium, rod_dual_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_schrabidium, rod_quad_schrabidium.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_solinium, rod_solinium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_solinium, rod_dual_solinium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_solinium, rod_quad_solinium.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_uranium_fuel, rod_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_uranium_fuel, rod_dual_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_uranium_fuel, rod_quad_uranium_fuel.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_plutonium_fuel, rod_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_plutonium_fuel, rod_dual_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_plutonium_fuel, rod_quad_plutonium_fuel.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_mox_fuel, rod_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_mox_fuel, rod_dual_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_mox_fuel, rod_quad_mox_fuel.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_schrabidium_fuel, rod_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_schrabidium_fuel, rod_dual_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_schrabidium_fuel, rod_quad_schrabidium_fuel.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_water, rod_water.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_water, rod_dual_water.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_water, rod_quad_water.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_coolant, rod_coolant.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_coolant, rod_dual_coolant.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_coolant, rod_quad_coolant.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_lithium, rod_lithium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_lithium, rod_dual_lithium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_lithium, rod_quad_lithium.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_tritium, rod_tritium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_tritium, rod_dual_tritium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_tritium, rod_quad_tritium.getUnlocalizedName());

		GameRegistry.registerItem(rod_euphemium, rod_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(rod_australium, rod_australium.getUnlocalizedName());
		GameRegistry.registerItem(rod_weidanium, rod_weidanium.getUnlocalizedName());
		GameRegistry.registerItem(rod_reiium, rod_reiium.getUnlocalizedName());
		GameRegistry.registerItem(rod_unobtainium, rod_unobtainium.getUnlocalizedName());
		GameRegistry.registerItem(rod_daffergon, rod_daffergon.getUnlocalizedName());
		GameRegistry.registerItem(rod_verticium, rod_verticium.getUnlocalizedName());
		
		//Nuclear Waste
		GameRegistry.registerItem(rod_uranium_fuel_depleted, rod_uranium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_uranium_fuel_depleted, rod_dual_uranium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_uranium_fuel_depleted, rod_quad_uranium_fuel_depleted.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_plutonium_fuel_depleted, rod_plutonium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_plutonium_fuel_depleted, rod_dual_plutonium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_plutonium_fuel_depleted, rod_quad_plutonium_fuel_depleted.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_mox_fuel_depleted, rod_mox_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_mox_fuel_depleted, rod_dual_mox_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_mox_fuel_depleted, rod_quad_mox_fuel_depleted.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_schrabidium_fuel_depleted, rod_schrabidium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_schrabidium_fuel_depleted, rod_dual_schrabidium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_schrabidium_fuel_depleted, rod_quad_schrabidium_fuel_depleted.getUnlocalizedName());

		GameRegistry.registerItem(rod_quad_euphemium, rod_quad_euphemium.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_waste, rod_waste.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_waste, rod_dual_waste.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_waste, rod_quad_waste.getUnlocalizedName());
		
		GameRegistry.registerItem(scrap, scrap.getUnlocalizedName());
		GameRegistry.registerItem(trinitite, trinitite.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste, nuclear_waste.getUnlocalizedName());
		
		//Da Chopper
		GameRegistry.registerItem(chopper, chopper.getUnlocalizedName());
		
		//Computer Tools
		GameRegistry.registerItem(designator, designator.getUnlocalizedName());
		GameRegistry.registerItem(designator_range, designator_range.getUnlocalizedName());
		GameRegistry.registerItem(designator_manual, designator_manual.getUnlocalizedName());
		GameRegistry.registerItem(turret_control, turret_control.getUnlocalizedName());
		GameRegistry.registerItem(turret_chip, turret_chip.getUnlocalizedName());
		GameRegistry.registerItem(turret_biometry, turret_biometry.getUnlocalizedName());
		GameRegistry.registerItem(linker, linker.getUnlocalizedName());
		GameRegistry.registerItem(oil_detector, oil_detector.getUnlocalizedName());
		GameRegistry.registerItem(survey_scanner, survey_scanner.getUnlocalizedName());
		GameRegistry.registerItem(geiger_counter, geiger_counter.getUnlocalizedName());
		
		//Missiles
		GameRegistry.registerItem(missile_generic, missile_generic.getUnlocalizedName());
		GameRegistry.registerItem(missile_anti_ballistic, missile_anti_ballistic.getUnlocalizedName());
		GameRegistry.registerItem(missile_incendiary, missile_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(missile_cluster, missile_cluster.getUnlocalizedName());
		GameRegistry.registerItem(missile_buster, missile_buster.getUnlocalizedName());
		GameRegistry.registerItem(missile_strong, missile_strong.getUnlocalizedName());
		GameRegistry.registerItem(missile_incendiary_strong, missile_incendiary_strong.getUnlocalizedName());
		GameRegistry.registerItem(missile_cluster_strong, missile_cluster_strong.getUnlocalizedName());
		GameRegistry.registerItem(missile_buster_strong, missile_buster_strong.getUnlocalizedName());
		GameRegistry.registerItem(missile_burst, missile_burst.getUnlocalizedName());
		GameRegistry.registerItem(missile_inferno, missile_inferno.getUnlocalizedName());
		GameRegistry.registerItem(missile_rain, missile_rain.getUnlocalizedName());
		GameRegistry.registerItem(missile_drill, missile_drill.getUnlocalizedName());
		GameRegistry.registerItem(missile_nuclear, missile_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(missile_nuclear_cluster, missile_nuclear_cluster.getUnlocalizedName());
		GameRegistry.registerItem(missile_endo, missile_endo.getUnlocalizedName());
		GameRegistry.registerItem(missile_exo, missile_exo.getUnlocalizedName());
		GameRegistry.registerItem(missile_doomsday, missile_doomsday.getUnlocalizedName());
		GameRegistry.registerItem(missile_taint, missile_taint.getUnlocalizedName());
		GameRegistry.registerItem(missile_micro, missile_micro.getUnlocalizedName());
		GameRegistry.registerItem(missile_bhole, missile_bhole.getUnlocalizedName());
		GameRegistry.registerItem(missile_schrabidium, missile_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(missile_emp, missile_emp.getUnlocalizedName());
		GameRegistry.registerItem(missile_carrier, missile_carrier.getUnlocalizedName());
		
		//Satellites
		GameRegistry.registerItem(sat_mapper, sat_mapper.getUnlocalizedName());
		GameRegistry.registerItem(sat_scanner, sat_scanner.getUnlocalizedName());
		GameRegistry.registerItem(sat_radar, sat_radar.getUnlocalizedName());
		GameRegistry.registerItem(sat_laser, sat_laser.getUnlocalizedName());
		GameRegistry.registerItem(sat_foeq, sat_foeq.getUnlocalizedName());
		GameRegistry.registerItem(sat_resonator, sat_resonator.getUnlocalizedName());
		GameRegistry.registerItem(sat_chip, sat_chip.getUnlocalizedName());
		GameRegistry.registerItem(sat_interface, sat_interface.getUnlocalizedName());
		
		//Guns
		GameRegistry.registerItem(gun_revolver_iron, gun_revolver_iron.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver, gun_revolver.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_saturnite, gun_revolver_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_gold, gun_revolver_gold.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_lead, gun_revolver_lead.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_schrabidium, gun_revolver_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_cursed, gun_revolver_cursed.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_nightmare, gun_revolver_nightmare.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_nightmare2, gun_revolver_nightmare2.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_pip, gun_revolver_pip.getUnlocalizedName());
		GameRegistry.registerItem(gun_b92, gun_b92.getUnlocalizedName());
		GameRegistry.registerItem(gun_b93, gun_b93.getUnlocalizedName());
		GameRegistry.registerItem(gun_rpg, gun_rpg.getUnlocalizedName());
		GameRegistry.registerItem(gun_stinger, gun_stinger.getUnlocalizedName());
		GameRegistry.registerItem(gun_fatman, gun_fatman.getUnlocalizedName());
		GameRegistry.registerItem(gun_proto, gun_proto.getUnlocalizedName());
		GameRegistry.registerItem(gun_mirv, gun_mirv.getUnlocalizedName());
		GameRegistry.registerItem(gun_bf, gun_bf.getUnlocalizedName());
		GameRegistry.registerItem(gun_mp40, gun_mp40.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi, gun_uzi.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi_silencer,gun_uzi_silencer.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi_saturnite, gun_uzi_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi_saturnite_silencer,gun_uzi_saturnite_silencer.getUnlocalizedName());
		GameRegistry.registerItem(gun_uboinik, gun_uboinik.getUnlocalizedName());
		GameRegistry.registerItem(gun_lever_action, gun_lever_action.getUnlocalizedName());
		GameRegistry.registerItem(gun_lever_action_dark, gun_lever_action_dark.getUnlocalizedName());
		GameRegistry.registerItem(gun_lever_action_sonata, gun_lever_action_sonata.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolt_action, gun_bolt_action.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolt_action_green, gun_bolt_action_green.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolt_action_saturnite, gun_bolt_action_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(gun_xvl1456, gun_xvl1456.getUnlocalizedName());
		GameRegistry.registerItem(gun_osipr, gun_osipr.getUnlocalizedName());
		GameRegistry.registerItem(gun_immolator, gun_immolator.getUnlocalizedName());
		GameRegistry.registerItem(gun_cryolator, gun_cryolator.getUnlocalizedName());
		GameRegistry.registerItem(gun_mp, gun_mp.getUnlocalizedName());
		GameRegistry.registerItem(gun_zomg, gun_zomg.getUnlocalizedName());
		GameRegistry.registerItem(gun_emp, gun_emp.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_inverted, gun_revolver_inverted.getUnlocalizedName());
		GameRegistry.registerItem(gun_jack, gun_jack.getUnlocalizedName());
		GameRegistry.registerItem(gun_spark, gun_spark.getUnlocalizedName());
		GameRegistry.registerItem(gun_hp, gun_hp.getUnlocalizedName());
		GameRegistry.registerItem(gun_euthanasia, gun_euthanasia.getUnlocalizedName());
		GameRegistry.registerItem(gun_skystinger, gun_skystinger.getUnlocalizedName());
		//GameRegistry.registerItem(gun_dash, gun_dash.getUnlocalizedName());
		//GameRegistry.registerItem(gun_twigun, gun_twigun.getUnlocalizedName());
		GameRegistry.registerItem(gun_defabricator, gun_defabricator.getUnlocalizedName());
		GameRegistry.registerItem(gun_super_shotgun, gun_super_shotgun.getUnlocalizedName());
		GameRegistry.registerItem(gun_moist_nugget, gun_moist_nugget.getUnlocalizedName());
		GameRegistry.registerItem(gun_dampfmaschine, gun_dampfmaschine.getUnlocalizedName());
		
		//Ammo
		GameRegistry.registerItem(gun_revolver_iron_ammo, gun_revolver_iron_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_ammo, gun_revolver_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_gold_ammo, gun_revolver_gold_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_lead_ammo, gun_revolver_lead_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_schrabidium_ammo, gun_revolver_schrabidium_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_cursed_ammo, gun_revolver_cursed_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_nightmare_ammo, gun_revolver_nightmare_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_nightmare2_ammo, gun_revolver_nightmare2_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_pip_ammo, gun_revolver_pip_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_b92_ammo, gun_b92_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_rpg_ammo, gun_rpg_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_stinger_ammo, gun_stinger_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_fatman_ammo, gun_fatman_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_mirv_ammo, gun_mirv_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_bf_ammo, gun_bf_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_mp40_ammo, gun_mp40_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi_ammo, gun_uzi_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_uboinik_ammo, gun_uboinik_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_lever_action_ammo, gun_lever_action_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolt_action_ammo, gun_bolt_action_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_xvl1456_ammo, gun_xvl1456_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_osipr_ammo, gun_osipr_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_osipr_ammo2, gun_osipr_ammo2.getUnlocalizedName());
		GameRegistry.registerItem(gun_immolator_ammo, gun_immolator_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_cryolator_ammo, gun_cryolator_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_mp_ammo, gun_mp_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_emp_ammo, gun_emp_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_jack_ammo, gun_jack_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_spark_ammo, gun_spark_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_hp_ammo, gun_hp_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_defabricator_ammo, gun_defabricator_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_euthanasia_ammo, gun_euthanasia_ammo.getUnlocalizedName());
		
		//Turret Ammo
		GameRegistry.registerItem(turret_light_ammo, turret_light_ammo.getUnlocalizedName());
		GameRegistry.registerItem(turret_heavy_ammo, turret_heavy_ammo.getUnlocalizedName());
		GameRegistry.registerItem(turret_rocket_ammo, turret_rocket_ammo.getUnlocalizedName());
		GameRegistry.registerItem(turret_flamer_ammo, turret_flamer_ammo.getUnlocalizedName());
		GameRegistry.registerItem(turret_tau_ammo, turret_tau_ammo.getUnlocalizedName());
		GameRegistry.registerItem(turret_spitfire_ammo, turret_spitfire_ammo.getUnlocalizedName());
		GameRegistry.registerItem(turret_cwis_ammo, turret_cwis_ammo.getUnlocalizedName());
		GameRegistry.registerItem(turret_cheapo_ammo, turret_cheapo_ammo.getUnlocalizedName());
		
		//-C-l-i-p-s- Magazines
		GameRegistry.registerItem(clip_revolver_iron, clip_revolver_iron.getUnlocalizedName());
		GameRegistry.registerItem(clip_revolver, clip_revolver.getUnlocalizedName());
		GameRegistry.registerItem(clip_revolver_gold, clip_revolver_gold.getUnlocalizedName());
		GameRegistry.registerItem(clip_revolver_lead, clip_revolver_lead.getUnlocalizedName());
		GameRegistry.registerItem(clip_revolver_schrabidium, clip_revolver_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(clip_revolver_cursed, clip_revolver_cursed.getUnlocalizedName());
		GameRegistry.registerItem(clip_revolver_nightmare, clip_revolver_nightmare.getUnlocalizedName());
		GameRegistry.registerItem(clip_revolver_nightmare2, clip_revolver_nightmare2.getUnlocalizedName());
		GameRegistry.registerItem(clip_revolver_pip, clip_revolver_pip.getUnlocalizedName());
		GameRegistry.registerItem(clip_rpg, clip_rpg.getUnlocalizedName());
		GameRegistry.registerItem(clip_stinger, clip_stinger.getUnlocalizedName());
		GameRegistry.registerItem(clip_fatman, clip_fatman.getUnlocalizedName());
		GameRegistry.registerItem(clip_mirv, clip_mirv.getUnlocalizedName());
		GameRegistry.registerItem(clip_bf, clip_bf.getUnlocalizedName());
		GameRegistry.registerItem(clip_mp40, clip_mp40.getUnlocalizedName());
		GameRegistry.registerItem(clip_uzi, clip_uzi.getUnlocalizedName());
		GameRegistry.registerItem(clip_uboinik, clip_uboinik.getUnlocalizedName());
		GameRegistry.registerItem(clip_lever_action, clip_lever_action.getUnlocalizedName());
		GameRegistry.registerItem(clip_bolt_action, clip_bolt_action.getUnlocalizedName());
		GameRegistry.registerItem(clip_xvl1456, clip_xvl1456.getUnlocalizedName());
		GameRegistry.registerItem(clip_osipr, clip_osipr.getUnlocalizedName());
		GameRegistry.registerItem(clip_immolator, clip_immolator.getUnlocalizedName());
		GameRegistry.registerItem(clip_cryolator, clip_cryolator.getUnlocalizedName());
		GameRegistry.registerItem(clip_mp, clip_mp.getUnlocalizedName());
		GameRegistry.registerItem(clip_emp, clip_emp.getUnlocalizedName());
		GameRegistry.registerItem(clip_jack, clip_jack.getUnlocalizedName());
		GameRegistry.registerItem(clip_spark, clip_spark.getUnlocalizedName());
		GameRegistry.registerItem(clip_hp, clip_hp.getUnlocalizedName());
		GameRegistry.registerItem(clip_euthanasia, clip_euthanasia.getUnlocalizedName());
		GameRegistry.registerItem(clip_defabricator, clip_defabricator.getUnlocalizedName());
		
		GameRegistry.registerItem(ammo_container, ammo_container.getUnlocalizedName());
		
		//Grenades
		GameRegistry.registerItem(grenade_generic, grenade_generic.getUnlocalizedName());
		GameRegistry.registerItem(grenade_strong, grenade_strong.getUnlocalizedName());
		GameRegistry.registerItem(grenade_frag, grenade_frag.getUnlocalizedName());
		GameRegistry.registerItem(grenade_fire, grenade_fire.getUnlocalizedName());
		GameRegistry.registerItem(grenade_shrapnel, grenade_shrapnel.getUnlocalizedName());
		GameRegistry.registerItem(grenade_cluster, grenade_cluster.getUnlocalizedName());
		GameRegistry.registerItem(grenade_flare, grenade_flare.getUnlocalizedName());
		GameRegistry.registerItem(grenade_electric, grenade_electric.getUnlocalizedName());
		GameRegistry.registerItem(grenade_poison, grenade_poison.getUnlocalizedName());
		GameRegistry.registerItem(grenade_gas, grenade_gas.getUnlocalizedName());
		GameRegistry.registerItem(grenade_cloud, grenade_cloud.getUnlocalizedName());
		GameRegistry.registerItem(grenade_pink_cloud, grenade_pink_cloud.getUnlocalizedName());
		GameRegistry.registerItem(grenade_pulse, grenade_pulse.getUnlocalizedName());
		GameRegistry.registerItem(grenade_plasma, grenade_plasma.getUnlocalizedName());
		GameRegistry.registerItem(grenade_tau, grenade_tau.getUnlocalizedName());
		GameRegistry.registerItem(grenade_schrabidium, grenade_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(grenade_nuke, grenade_nuke.getUnlocalizedName());
		GameRegistry.registerItem(grenade_lemon, grenade_lemon.getUnlocalizedName());
		GameRegistry.registerItem(grenade_gascan, grenade_gascan.getUnlocalizedName());
		GameRegistry.registerItem(grenade_mk2, grenade_mk2.getUnlocalizedName());
		GameRegistry.registerItem(grenade_aschrab, grenade_aschrab.getUnlocalizedName());
		GameRegistry.registerItem(grenade_nuclear, grenade_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(grenade_zomg, grenade_zomg.getUnlocalizedName());
		GameRegistry.registerItem(grenade_black_hole, grenade_black_hole.getUnlocalizedName());
		GameRegistry.registerItem(ullapool_caber, ullapool_caber.getUnlocalizedName());
		GameRegistry.registerItem(weaponized_starblaster_cell, weaponized_starblaster_cell.getUnlocalizedName());
		
		//Capes
		GameRegistry.registerItem(cape_radiation, cape_radiation.getUnlocalizedName());
		GameRegistry.registerItem(cape_gasmask, cape_gasmask.getUnlocalizedName());
		GameRegistry.registerItem(cape_schrabidium, cape_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(cape_hbm, cape_hbm.getUnlocalizedName());
		GameRegistry.registerItem(cape_dafnik, cape_dafnik.getUnlocalizedName());
		GameRegistry.registerItem(cape_lpkukin, cape_lpkukin.getUnlocalizedName());
		GameRegistry.registerItem(cape_vertice, cape_vertice.getUnlocalizedName());
		GameRegistry.registerItem(cape_codered_, cape_codered_.getUnlocalizedName());
		
		//Tools
		GameRegistry.registerItem(schrabidium_sword, schrabidium_sword.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_hammer, schrabidium_hammer.getUnlocalizedName());
		GameRegistry.registerItem(shimmer_sledge, shimmer_sledge.getUnlocalizedName());
		GameRegistry.registerItem(shimmer_axe, shimmer_axe.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_pickaxe, schrabidium_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_axe, schrabidium_axe.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_shovel, schrabidium_shovel.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_hoe, schrabidium_hoe.getUnlocalizedName());
		GameRegistry.registerItem(steel_sword, steel_sword.getUnlocalizedName());
		GameRegistry.registerItem(steel_pickaxe, steel_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(steel_axe, steel_axe.getUnlocalizedName());
		GameRegistry.registerItem(steel_shovel, steel_shovel.getUnlocalizedName());
		GameRegistry.registerItem(steel_hoe, steel_hoe.getUnlocalizedName());
		GameRegistry.registerItem(titanium_sword, titanium_sword.getUnlocalizedName());
		GameRegistry.registerItem(titanium_pickaxe, titanium_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(titanium_axe, titanium_axe.getUnlocalizedName());
		GameRegistry.registerItem(titanium_shovel, titanium_shovel.getUnlocalizedName());
		GameRegistry.registerItem(titanium_hoe, titanium_hoe.getUnlocalizedName());
		GameRegistry.registerItem(alloy_sword, alloy_sword.getUnlocalizedName());
		GameRegistry.registerItem(alloy_pickaxe, alloy_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(alloy_axe, alloy_axe.getUnlocalizedName());
		GameRegistry.registerItem(alloy_shovel, alloy_shovel.getUnlocalizedName());
		GameRegistry.registerItem(alloy_hoe, alloy_hoe.getUnlocalizedName());
		GameRegistry.registerItem(cmb_sword, cmb_sword.getUnlocalizedName());
		GameRegistry.registerItem(cmb_pickaxe, cmb_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(cmb_axe, cmb_axe.getUnlocalizedName());
		GameRegistry.registerItem(cmb_shovel, cmb_shovel.getUnlocalizedName());
		GameRegistry.registerItem(cmb_hoe, cmb_hoe.getUnlocalizedName());
		GameRegistry.registerItem(desh_sword, desh_sword.getUnlocalizedName());
		GameRegistry.registerItem(desh_pickaxe, desh_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(desh_axe, desh_axe.getUnlocalizedName());
		GameRegistry.registerItem(desh_shovel, desh_shovel.getUnlocalizedName());
		GameRegistry.registerItem(desh_hoe, desh_hoe.getUnlocalizedName());
		GameRegistry.registerItem(elec_sword, elec_sword.getUnlocalizedName());
		GameRegistry.registerItem(elec_pickaxe, elec_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(elec_axe, elec_axe.getUnlocalizedName());
		GameRegistry.registerItem(elec_shovel, elec_shovel.getUnlocalizedName());
		GameRegistry.registerItem(matchstick, matchstick.getUnlocalizedName());
		GameRegistry.registerItem(crowbar, crowbar.getUnlocalizedName());
		GameRegistry.registerItem(saw, saw.getUnlocalizedName());
		GameRegistry.registerItem(bat, bat.getUnlocalizedName());
		GameRegistry.registerItem(bat_nail, bat_nail.getUnlocalizedName());
		GameRegistry.registerItem(golf_club, golf_club.getUnlocalizedName());
		GameRegistry.registerItem(pipe_rusty, pipe_rusty.getUnlocalizedName());
		GameRegistry.registerItem(pipe_lead, pipe_lead.getUnlocalizedName());
		GameRegistry.registerItem(reer_graar, reer_graar.getUnlocalizedName());
		
		//Multitool
		GameRegistry.registerItem(multitool_hit, multitool_hit.getUnlocalizedName());
		GameRegistry.registerItem(multitool_dig, multitool_dig.getUnlocalizedName());
		GameRegistry.registerItem(multitool_silk, multitool_silk.getUnlocalizedName());
		GameRegistry.registerItem(multitool_ext, multitool_ext.getUnlocalizedName());
		GameRegistry.registerItem(multitool_miner, multitool_miner.getUnlocalizedName());
		GameRegistry.registerItem(multitool_beam, multitool_beam.getUnlocalizedName());
		GameRegistry.registerItem(multitool_sky, multitool_sky.getUnlocalizedName());
		GameRegistry.registerItem(multitool_mega, multitool_mega.getUnlocalizedName());
		GameRegistry.registerItem(multitool_joule, multitool_joule.getUnlocalizedName());
		GameRegistry.registerItem(multitool_decon, multitool_decon.getUnlocalizedName());
		
		//Syringes & Pills
		GameRegistry.registerItem(syringe_empty, syringe_empty.getUnlocalizedName());
		GameRegistry.registerItem(syringe_antidote, syringe_antidote.getUnlocalizedName());
		GameRegistry.registerItem(syringe_poison, syringe_poison.getUnlocalizedName());
		GameRegistry.registerItem(syringe_awesome, syringe_awesome.getUnlocalizedName());
		GameRegistry.registerItem(syringe_metal_empty, syringe_metal_empty.getUnlocalizedName());
		GameRegistry.registerItem(syringe_metal_stimpak, syringe_metal_stimpak.getUnlocalizedName());
		GameRegistry.registerItem(syringe_metal_medx, syringe_metal_medx.getUnlocalizedName());
		GameRegistry.registerItem(syringe_metal_psycho, syringe_metal_psycho.getUnlocalizedName());
		GameRegistry.registerItem(syringe_metal_super, syringe_metal_super.getUnlocalizedName());
		GameRegistry.registerItem(syringe_taint, syringe_taint.getUnlocalizedName());
		GameRegistry.registerItem(med_bag, med_bag.getUnlocalizedName());
		GameRegistry.registerItem(radaway, radaway.getUnlocalizedName());
		GameRegistry.registerItem(pill_iodine, pill_iodine.getUnlocalizedName());
		GameRegistry.registerItem(plan_c, plan_c.getUnlocalizedName());
		GameRegistry.registerItem(stealth_boy, stealth_boy.getUnlocalizedName());
		
		//Food
		GameRegistry.registerItem(bomb_waffle, bomb_waffle.getUnlocalizedName());
		GameRegistry.registerItem(schnitzel_vegan, schnitzel_vegan.getUnlocalizedName());
		GameRegistry.registerItem(cotton_candy, cotton_candy.getUnlocalizedName());
		GameRegistry.registerItem(apple_schrabidium, apple_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(tem_flakes, tem_flakes.getUnlocalizedName());
		GameRegistry.registerItem(glowing_stew, glowing_stew.getUnlocalizedName());
		GameRegistry.registerItem(lemon, lemon.getUnlocalizedName());
		GameRegistry.registerItem(definitelyfood, definitelyfood.getUnlocalizedName());
		GameRegistry.registerItem(med_ipecac, med_ipecac.getUnlocalizedName());
		GameRegistry.registerItem(med_ptsd, med_ptsd.getUnlocalizedName());
		//GameRegistry.registerItem(med_schizophrenia, med_schizophrenia.getUnlocalizedName());
		
		//Energy Drinks
		GameRegistry.registerItem(can_empty, can_empty.getUnlocalizedName());
		GameRegistry.registerItem(can_smart, can_smart.getUnlocalizedName());
		GameRegistry.registerItem(can_creature, can_creature.getUnlocalizedName());
		GameRegistry.registerItem(can_redbomb, can_redbomb.getUnlocalizedName());
		GameRegistry.registerItem(can_mrsugar, can_mrsugar.getUnlocalizedName());
		GameRegistry.registerItem(can_overcharge, can_overcharge.getUnlocalizedName());
		GameRegistry.registerItem(can_luna, can_luna.getUnlocalizedName());
		
		//Cola
		GameRegistry.registerItem(bottle_empty, bottle_empty.getUnlocalizedName());
		GameRegistry.registerItem(bottle_nuka, bottle_nuka.getUnlocalizedName());
		GameRegistry.registerItem(bottle_cherry, bottle_cherry.getUnlocalizedName());
		GameRegistry.registerItem(bottle_quantum, bottle_quantum.getUnlocalizedName());
		GameRegistry.registerItem(bottle_sparkle, bottle_sparkle.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_empty, bottle2_empty.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_korl, bottle2_korl.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_fritz, bottle2_fritz.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_korl_special, bottle2_korl_special.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_fritz_special, bottle2_fritz_special.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_sunset, bottle2_sunset.getUnlocalizedName());
		GameRegistry.registerItem(bottle_opener, bottle_opener.getUnlocalizedName());
		
		//Money
		GameRegistry.registerItem(cap_nuka, cap_nuka.getUnlocalizedName());
		GameRegistry.registerItem(cap_quantum, cap_quantum.getUnlocalizedName());
		GameRegistry.registerItem(cap_sparkle, cap_sparkle.getUnlocalizedName());
		GameRegistry.registerItem(cap_korl, cap_korl.getUnlocalizedName());
		GameRegistry.registerItem(cap_fritz, cap_fritz.getUnlocalizedName());
		GameRegistry.registerItem(cap_sunset, cap_sunset.getUnlocalizedName());
		GameRegistry.registerItem(ring_pull, ring_pull.getUnlocalizedName());
		
		//Chaos
		GameRegistry.registerItem(chocolate_milk, chocolate_milk.getUnlocalizedName());

		//The Gadget
		GameRegistry.registerItem(gadget_explosive, gadget_explosive.getUnlocalizedName());
		GameRegistry.registerItem(gadget_explosive8, gadget_explosive8.getUnlocalizedName());
		GameRegistry.registerItem(gadget_wireing, gadget_wireing.getUnlocalizedName());
		GameRegistry.registerItem(gadget_core, gadget_core.getUnlocalizedName());

		//Little Boy
		GameRegistry.registerItem(boy_shielding, boy_shielding.getUnlocalizedName());
		GameRegistry.registerItem(boy_target, boy_target.getUnlocalizedName());
		GameRegistry.registerItem(boy_bullet, boy_bullet.getUnlocalizedName());
		GameRegistry.registerItem(boy_propellant, boy_propellant.getUnlocalizedName());
		GameRegistry.registerItem(boy_igniter, boy_igniter.getUnlocalizedName());;
		
		//Fat Man
		GameRegistry.registerItem(man_explosive, man_explosive.getUnlocalizedName());
		GameRegistry.registerItem(man_explosive8, man_explosive8.getUnlocalizedName());
		GameRegistry.registerItem(man_igniter, man_igniter.getUnlocalizedName());
		GameRegistry.registerItem(man_core, man_core.getUnlocalizedName());
		
		//Ivy Mike
		GameRegistry.registerItem(mike_core, mike_core.getUnlocalizedName());
		GameRegistry.registerItem(mike_deut, mike_deut.getUnlocalizedName());
		GameRegistry.registerItem(mike_cooling_unit, mike_cooling_unit.getUnlocalizedName());
		
		//Tsar Bomba
		GameRegistry.registerItem(tsar_core, tsar_core.getUnlocalizedName());
		
		//FLEIJA
		GameRegistry.registerItem(fleija_igniter, fleija_igniter.getUnlocalizedName());
		GameRegistry.registerItem(fleija_propellant, fleija_propellant.getUnlocalizedName());
		GameRegistry.registerItem(fleija_core, fleija_core.getUnlocalizedName());
		
		//Solinium
		GameRegistry.registerItem(solinium_igniter, solinium_igniter.getUnlocalizedName());
		GameRegistry.registerItem(solinium_propellant, solinium_propellant.getUnlocalizedName());
		GameRegistry.registerItem(solinium_core, solinium_core.getUnlocalizedName());
		
		//N2
		GameRegistry.registerItem(n2_charge, n2_charge.getUnlocalizedName());
		
		//Conventional Armor
		GameRegistry.registerItem(goggles, goggles.getUnlocalizedName());
		GameRegistry.registerItem(gas_mask, gas_mask.getUnlocalizedName());
		GameRegistry.registerItem(gas_mask_m65, gas_mask_m65.getUnlocalizedName());
		//GameRegistry.registerItem(oxy_mask, oxy_mask.getUnlocalizedName());
		
		GameRegistry.registerItem(steel_helmet, steel_helmet.getUnlocalizedName());
		GameRegistry.registerItem(steel_plate, steel_plate.getUnlocalizedName());
		GameRegistry.registerItem(steel_legs, steel_legs.getUnlocalizedName());
		GameRegistry.registerItem(steel_boots, steel_boots.getUnlocalizedName());
		GameRegistry.registerItem(titanium_helmet, titanium_helmet.getUnlocalizedName());
		GameRegistry.registerItem(titanium_plate, titanium_plate.getUnlocalizedName());
		GameRegistry.registerItem(titanium_legs, titanium_legs.getUnlocalizedName());
		GameRegistry.registerItem(titanium_boots, titanium_boots.getUnlocalizedName());
		GameRegistry.registerItem(alloy_helmet, alloy_helmet.getUnlocalizedName());
		GameRegistry.registerItem(alloy_plate, alloy_plate.getUnlocalizedName());
		GameRegistry.registerItem(alloy_legs, alloy_legs.getUnlocalizedName());
		GameRegistry.registerItem(alloy_boots, alloy_boots.getUnlocalizedName());
		
		//Power Armor
		GameRegistry.registerItem(t45_helmet, t45_helmet.getUnlocalizedName());
		GameRegistry.registerItem(t45_plate, t45_plate.getUnlocalizedName());
		GameRegistry.registerItem(t45_legs, t45_legs.getUnlocalizedName());
		GameRegistry.registerItem(t45_boots, t45_boots.getUnlocalizedName());
		
		//Nobody will ever read this anyway, so it shouldn't matter.
		GameRegistry.registerItem(chainsaw, chainsaw.getUnlocalizedName());
		GameRegistry.registerItem(igniter, igniter.getUnlocalizedName());
		GameRegistry.registerItem(detonator, detonator.getUnlocalizedName());
		GameRegistry.registerItem(detonator_multi, detonator_multi.getUnlocalizedName());
		GameRegistry.registerItem(detonator_laser, detonator_laser.getUnlocalizedName());
		GameRegistry.registerItem(crate_caller, crate_caller.getUnlocalizedName());
		GameRegistry.registerItem(bomb_caller, bomb_caller.getUnlocalizedName());
		GameRegistry.registerItem(meteor_remote, meteor_remote.getUnlocalizedName());
		GameRegistry.registerItem(defuser, defuser.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_helmet, hazmat_helmet.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_plate, hazmat_plate.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_legs, hazmat_legs.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_boots, hazmat_boots.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_paa_helmet, hazmat_paa_helmet.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_paa_plate, hazmat_paa_plate.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_paa_legs, hazmat_paa_legs.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_paa_boots, hazmat_paa_boots.getUnlocalizedName());
		GameRegistry.registerItem(cmb_helmet, cmb_helmet.getUnlocalizedName());
		GameRegistry.registerItem(cmb_plate, cmb_plate.getUnlocalizedName());
		GameRegistry.registerItem(cmb_legs, cmb_legs.getUnlocalizedName());
		GameRegistry.registerItem(cmb_boots, cmb_boots.getUnlocalizedName());
		GameRegistry.registerItem(paa_plate, paa_plate.getUnlocalizedName());
		GameRegistry.registerItem(paa_legs, paa_legs.getUnlocalizedName());
		GameRegistry.registerItem(paa_boots, paa_boots.getUnlocalizedName());
		GameRegistry.registerItem(asbestos_helmet, asbestos_helmet.getUnlocalizedName());
		GameRegistry.registerItem(asbestos_plate, asbestos_plate.getUnlocalizedName());
		GameRegistry.registerItem(asbestos_legs, asbestos_legs.getUnlocalizedName());
		GameRegistry.registerItem(asbestos_boots, asbestos_boots.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_helmet, schrabidium_helmet.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_plate, schrabidium_plate.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_legs, schrabidium_legs.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_boots, schrabidium_boots.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_helmet, euphemium_helmet.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_plate, euphemium_plate.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_legs, euphemium_legs.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_boots, euphemium_boots.getUnlocalizedName());
		GameRegistry.registerItem(apple_euphemium, apple_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(watch, watch.getUnlocalizedName());
		GameRegistry.registerItem(mask_of_infamy, mask_of_infamy.getUnlocalizedName());
		GameRegistry.registerItem(australium_iii, australium_iii.getUnlocalizedName());
		GameRegistry.registerItem(jackt, jackt.getUnlocalizedName());
		GameRegistry.registerItem(jackt2, jackt2.getUnlocalizedName());
		GameRegistry.registerItem(jetpack_boost, jetpack_boost.getUnlocalizedName());
		GameRegistry.registerItem(jetpack_break, jetpack_break.getUnlocalizedName());
		GameRegistry.registerItem(jetpack_fly, jetpack_fly.getUnlocalizedName());
		GameRegistry.registerItem(jetpack_vector, jetpack_vector.getUnlocalizedName());
		//GameRegistry.registerItem(australium_iv, australium_iv.getUnlocalizedName());
		//GameRegistry.registerItem(australium_v, australium_v.getUnlocalizedName());
		
		//Expensive Ass Shit
		GameRegistry.registerItem(crystal_horn, crystal_horn.getUnlocalizedName());
		GameRegistry.registerItem(crystal_charred, crystal_charred.getUnlocalizedName());
		
		//OP Tools
		GameRegistry.registerItem(wand, wand.getUnlocalizedName());
		GameRegistry.registerItem(wand_s, wand_s.getUnlocalizedName());
		GameRegistry.registerItem(wand_d, wand_d.getUnlocalizedName());
		//GameRegistry.registerItem(remote, remote.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_stopper, euphemium_stopper.getUnlocalizedName());
		GameRegistry.registerItem(polaroid, polaroid.getUnlocalizedName());
		GameRegistry.registerItem(glitch, glitch.getUnlocalizedName());
		GameRegistry.registerItem(book_secret, book_secret.getUnlocalizedName());
		GameRegistry.registerItem(burnt_bark, burnt_bark.getUnlocalizedName());
		
		//Kits
		GameRegistry.registerItem(nuke_starter_kit, nuke_starter_kit.getUnlocalizedName());
		GameRegistry.registerItem(nuke_advanced_kit, nuke_advanced_kit.getUnlocalizedName());
		GameRegistry.registerItem(nuke_commercially_kit, nuke_commercially_kit.getUnlocalizedName());
		GameRegistry.registerItem(nuke_electric_kit, nuke_electric_kit.getUnlocalizedName());
		GameRegistry.registerItem(gadget_kit, gadget_kit.getUnlocalizedName());
		GameRegistry.registerItem(boy_kit, boy_kit.getUnlocalizedName());
		GameRegistry.registerItem(man_kit, man_kit.getUnlocalizedName());
		GameRegistry.registerItem(mike_kit, mike_kit.getUnlocalizedName());
		GameRegistry.registerItem(tsar_kit, tsar_kit.getUnlocalizedName());
		GameRegistry.registerItem(prototype_kit, prototype_kit.getUnlocalizedName());
		GameRegistry.registerItem(fleija_kit, fleija_kit.getUnlocalizedName());
		GameRegistry.registerItem(solinium_kit, solinium_kit.getUnlocalizedName());
		GameRegistry.registerItem(multi_kit, multi_kit.getUnlocalizedName());
		GameRegistry.registerItem(missile_kit, missile_kit.getUnlocalizedName());
		GameRegistry.registerItem(grenade_kit, grenade_kit.getUnlocalizedName());
		GameRegistry.registerItem(t45_kit, t45_kit.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_kit, euphemium_kit.getUnlocalizedName());
		GameRegistry.registerItem(letter, letter.getUnlocalizedName());
		
		//THIS is a bucket.
		GameRegistry.registerItem(bucket_mud, bucket_mud.getUnlocalizedName());
		GameRegistry.registerItem(bucket_acid, bucket_acid.getUnlocalizedName());
		GameRegistry.registerItem(bucket_toxic, bucket_toxic.getUnlocalizedName());
		
		//Records
		GameRegistry.registerItem(record_lc, record_lc.getUnlocalizedName());
		GameRegistry.registerItem(record_ss, record_ss.getUnlocalizedName());
		GameRegistry.registerItem(record_vc, record_vc.getUnlocalizedName());
		
		//Technical Items
		GameRegistry.registerItem(smoke1, smoke1.getUnlocalizedName());
		GameRegistry.registerItem(smoke2, smoke2.getUnlocalizedName());
		GameRegistry.registerItem(smoke3, smoke3.getUnlocalizedName());
		GameRegistry.registerItem(smoke4, smoke4.getUnlocalizedName());
		GameRegistry.registerItem(smoke5, smoke5.getUnlocalizedName());
		GameRegistry.registerItem(smoke6, smoke6.getUnlocalizedName());
		GameRegistry.registerItem(smoke7, smoke7.getUnlocalizedName());
		GameRegistry.registerItem(smoke8, smoke8.getUnlocalizedName());
		GameRegistry.registerItem(b_smoke1, b_smoke1.getUnlocalizedName());
		GameRegistry.registerItem(b_smoke2, b_smoke2.getUnlocalizedName());
		GameRegistry.registerItem(b_smoke3, b_smoke3.getUnlocalizedName());
		GameRegistry.registerItem(b_smoke4, b_smoke4.getUnlocalizedName());
		GameRegistry.registerItem(b_smoke5, b_smoke5.getUnlocalizedName());
		GameRegistry.registerItem(b_smoke6, b_smoke6.getUnlocalizedName());
		GameRegistry.registerItem(b_smoke7, b_smoke7.getUnlocalizedName());
		GameRegistry.registerItem(b_smoke8, b_smoke8.getUnlocalizedName());
		GameRegistry.registerItem(d_smoke1, d_smoke1.getUnlocalizedName());
		GameRegistry.registerItem(d_smoke2, d_smoke2.getUnlocalizedName());
		GameRegistry.registerItem(d_smoke3, d_smoke3.getUnlocalizedName());
		GameRegistry.registerItem(d_smoke4, d_smoke4.getUnlocalizedName());
		GameRegistry.registerItem(d_smoke5, d_smoke5.getUnlocalizedName());
		GameRegistry.registerItem(d_smoke6, d_smoke6.getUnlocalizedName());
		GameRegistry.registerItem(d_smoke7, d_smoke7.getUnlocalizedName());
		GameRegistry.registerItem(d_smoke8, d_smoke8.getUnlocalizedName());
		GameRegistry.registerItem(spill1, spill1.getUnlocalizedName());
		GameRegistry.registerItem(spill2, spill2.getUnlocalizedName());
		GameRegistry.registerItem(spill3, spill3.getUnlocalizedName());
		GameRegistry.registerItem(spill4, spill4.getUnlocalizedName());
		GameRegistry.registerItem(spill5, spill5.getUnlocalizedName());
		GameRegistry.registerItem(spill6, spill6.getUnlocalizedName());
		GameRegistry.registerItem(spill7, spill7.getUnlocalizedName());
		GameRegistry.registerItem(spill8, spill8.getUnlocalizedName());
		GameRegistry.registerItem(gas1, gas1.getUnlocalizedName());
		GameRegistry.registerItem(gas2, gas2.getUnlocalizedName());
		GameRegistry.registerItem(gas3, gas3.getUnlocalizedName());
		GameRegistry.registerItem(gas4, gas4.getUnlocalizedName());
		GameRegistry.registerItem(gas5, gas5.getUnlocalizedName());
		GameRegistry.registerItem(gas6, gas6.getUnlocalizedName());
		GameRegistry.registerItem(gas7, gas7.getUnlocalizedName());
		GameRegistry.registerItem(gas8, gas8.getUnlocalizedName());
		GameRegistry.registerItem(chlorine1, chlorine1.getUnlocalizedName());
		GameRegistry.registerItem(chlorine2, chlorine2.getUnlocalizedName());
		GameRegistry.registerItem(chlorine3, chlorine3.getUnlocalizedName());
		GameRegistry.registerItem(chlorine4, chlorine4.getUnlocalizedName());
		GameRegistry.registerItem(chlorine5, chlorine5.getUnlocalizedName());
		GameRegistry.registerItem(chlorine6, chlorine6.getUnlocalizedName());
		GameRegistry.registerItem(chlorine7, chlorine7.getUnlocalizedName());
		GameRegistry.registerItem(chlorine8, chlorine8.getUnlocalizedName());
		GameRegistry.registerItem(pc1, pc1.getUnlocalizedName());
		GameRegistry.registerItem(pc2, pc2.getUnlocalizedName());
		GameRegistry.registerItem(pc3, pc3.getUnlocalizedName());
		GameRegistry.registerItem(pc4, pc4.getUnlocalizedName());
		GameRegistry.registerItem(pc5, pc5.getUnlocalizedName());
		GameRegistry.registerItem(pc6, pc6.getUnlocalizedName());
		GameRegistry.registerItem(pc7, pc7.getUnlocalizedName());
		GameRegistry.registerItem(pc8, pc8.getUnlocalizedName());
		GameRegistry.registerItem(cloud1, cloud1.getUnlocalizedName());
		GameRegistry.registerItem(cloud2, cloud2.getUnlocalizedName());
		GameRegistry.registerItem(cloud3, cloud3.getUnlocalizedName());
		GameRegistry.registerItem(cloud4, cloud4.getUnlocalizedName());
		GameRegistry.registerItem(cloud5, cloud5.getUnlocalizedName());
		GameRegistry.registerItem(cloud6, cloud6.getUnlocalizedName());
		GameRegistry.registerItem(cloud7, cloud7.getUnlocalizedName());
		GameRegistry.registerItem(cloud8, cloud8.getUnlocalizedName());
		GameRegistry.registerItem(orange1, orange1.getUnlocalizedName());
		GameRegistry.registerItem(orange2, orange2.getUnlocalizedName());
		GameRegistry.registerItem(orange3, orange3.getUnlocalizedName());
		GameRegistry.registerItem(orange4, orange4.getUnlocalizedName());
		GameRegistry.registerItem(orange5, orange5.getUnlocalizedName());
		GameRegistry.registerItem(orange6, orange6.getUnlocalizedName());
		GameRegistry.registerItem(orange7, orange7.getUnlocalizedName());
		GameRegistry.registerItem(orange8, orange8.getUnlocalizedName());
		GameRegistry.registerItem(gasflame1, gasflame1.getUnlocalizedName());
		GameRegistry.registerItem(gasflame2, gasflame2.getUnlocalizedName());
		GameRegistry.registerItem(gasflame3, gasflame3.getUnlocalizedName());
		GameRegistry.registerItem(gasflame4, gasflame4.getUnlocalizedName());
		GameRegistry.registerItem(gasflame5, gasflame5.getUnlocalizedName());
		GameRegistry.registerItem(gasflame6, gasflame6.getUnlocalizedName());
		GameRegistry.registerItem(gasflame7, gasflame7.getUnlocalizedName());
		GameRegistry.registerItem(gasflame8, gasflame8.getUnlocalizedName());
		GameRegistry.registerItem(energy_ball, energy_ball.getUnlocalizedName());
		GameRegistry.registerItem(discharge, discharge.getUnlocalizedName());
		GameRegistry.registerItem(empblast, empblast.getUnlocalizedName());
		GameRegistry.registerItem(flame_1, flame_1.getUnlocalizedName());
		GameRegistry.registerItem(flame_2, flame_2.getUnlocalizedName());
		GameRegistry.registerItem(flame_3, flame_3.getUnlocalizedName());
		GameRegistry.registerItem(flame_4, flame_4.getUnlocalizedName());
		GameRegistry.registerItem(flame_5, flame_5.getUnlocalizedName());
		GameRegistry.registerItem(flame_6, flame_6.getUnlocalizedName());
		GameRegistry.registerItem(flame_7, flame_7.getUnlocalizedName());
		GameRegistry.registerItem(flame_8, flame_8.getUnlocalizedName());
		GameRegistry.registerItem(flame_9, flame_9.getUnlocalizedName());
		GameRegistry.registerItem(flame_10, flame_10.getUnlocalizedName());
		GameRegistry.registerItem(ln2_1, ln2_1.getUnlocalizedName());
		GameRegistry.registerItem(ln2_2, ln2_2.getUnlocalizedName());
		GameRegistry.registerItem(ln2_3, ln2_3.getUnlocalizedName());
		GameRegistry.registerItem(ln2_4, ln2_4.getUnlocalizedName());
		GameRegistry.registerItem(ln2_5, ln2_5.getUnlocalizedName());
		GameRegistry.registerItem(ln2_6, ln2_6.getUnlocalizedName());
		GameRegistry.registerItem(ln2_7, ln2_7.getUnlocalizedName());
		GameRegistry.registerItem(ln2_8, ln2_8.getUnlocalizedName());
		GameRegistry.registerItem(ln2_9, ln2_9.getUnlocalizedName());
		GameRegistry.registerItem(ln2_10, ln2_10.getUnlocalizedName());
		GameRegistry.registerItem(nothing, nothing.getUnlocalizedName());
		GameRegistry.registerItem(void_anim, void_anim.getUnlocalizedName());
		GameRegistry.registerItem(mysteryshovel, mysteryshovel.getUnlocalizedName());
		GameRegistry.registerItem(memory, memory.getUnlocalizedName());
	}
}
