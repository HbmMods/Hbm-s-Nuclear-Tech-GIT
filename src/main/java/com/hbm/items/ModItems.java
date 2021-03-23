package com.hbm.items;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.BucketHandler;
import com.hbm.handler.ToolAbility;
import com.hbm.handler.ToolAbility.LuckAbility;
import com.hbm.handler.WeaponAbility;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.handler.guncfg.*;
import com.hbm.items.armor.*;
import com.hbm.items.bomb.*;
import com.hbm.items.food.*;
import com.hbm.items.machine.*;
import com.hbm.items.special.*;
import com.hbm.items.tool.*;
import com.hbm.items.tool.ItemToolAbility.EnumToolType;
import com.hbm.items.weapon.*;
import com.hbm.items.weapon.ItemMissile.*;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
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

	public static Item ingot_th232;
	public static Item ingot_uranium;
	public static Item ingot_u233;
	public static Item ingot_u235;
	public static Item ingot_u238;
	public static Item ingot_u238m2;
	public static Item ingot_plutonium;
	public static Item ingot_pu238;
	public static Item ingot_pu239;
	public static Item ingot_pu240;
	public static Item ingot_pu_mix;
	public static Item ingot_neptunium;
	public static Item ingot_polonium;
	public static Item ingot_titanium;
	public static Item ingot_cobalt;
	public static Item sulfur;
	
	public static Item coke;
	public static Item lignite;
	public static Item powder_lignite;
	public static Item briquette_lignite;

	public static Item niter;
	public static Item ingot_copper;
	public static Item ingot_red_copper;
	public static Item ingot_tungsten;
	public static Item ingot_aluminium;
	public static Item fluorite;
	public static Item ingot_beryllium;
	public static Item ingot_schraranium;
	public static Item ingot_schrabidium;
	public static Item ingot_schrabidate;
	public static Item ingot_plutonium_fuel;
	public static Item ingot_uranium_fuel;
	public static Item ingot_mox_fuel;
	public static Item ingot_schrabidium_fuel;
	public static Item ingot_thorium_fuel;
	public static Item nugget_uranium_fuel;
	public static Item nugget_thorium_fuel;
	public static Item nugget_plutonium_fuel;
	public static Item nugget_mox_fuel;
	public static Item nugget_schrabidium_fuel;
	public static Item ingot_advanced_alloy;
	public static Item lithium;
	public static Item ingot_zirconium;
	public static Item ingot_hes;
	public static Item ingot_les;
	public static Item nugget_hes;
	public static Item nugget_les;
	public static Item ingot_magnetized_tungsten;
	public static Item ingot_combine_steel;
	public static Item ingot_solinium;
	public static Item nugget_solinium;
	public static Item ingot_phosphorus;
	public static Item ingot_semtex;

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
	public static Item ingot_electronium;
	public static Item nugget_mercury;
	public static Item bottle_mercury;

	public static Item billet_uranium;
	public static Item billet_u233;
	public static Item billet_u235;
	public static Item billet_u238;
	public static Item billet_th232;
	public static Item billet_plutonium;
	public static Item billet_pu238;
	public static Item billet_pu239;
	public static Item billet_pu240;
	public static Item billet_pu_mix;
	public static Item billet_neptunium;
	public static Item billet_polonium;
	public static Item billet_schrabidium;
	public static Item billet_solinium;
	public static Item billet_uranium_fuel;
	public static Item billet_thorium_fuel;
	public static Item billet_plutonium_fuel;
	public static Item billet_mox_fuel;
	public static Item billet_schrabidium_fuel;
	public static Item billet_yharonite;

	public static Item nugget_th232;
	public static Item nugget_uranium;
	public static Item nugget_u233;
	public static Item nugget_u235;
	public static Item nugget_u238;
	public static Item nugget_plutonium;
	public static Item nugget_pu238;
	public static Item nugget_pu239;
	public static Item nugget_pu240;
	public static Item nugget_pu_mix;
	public static Item nugget_neptunium;
	public static Item nugget_polonium;
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
	public static Item hazmat_cloth_red;
	public static Item hazmat_cloth_grey;
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
	public static Item plate_kevlar;
	public static Item plate_dineutronium;
	public static Item plate_desh;
	public static Item photo_panel;
	public static Item sat_base;
	public static Item thruster_nuclear;
	
	public static Item ingot_dura_steel;
	public static Item ingot_polymer;
	
	public static Item ingot_fiberglass;
	public static Item ingot_asbestos;
	public static Item powder_asbestos;

	public static Item ingot_lanthanium;
	public static Item ingot_actinium;

	public static Item ingot_meteorite;
	public static Item ingot_meteorite_forged;
	public static Item blade_meteorite;
	public static Item ingot_steel_dusted;
	public static Item ingot_chainsteel;

	public static Item plate_armor_titanium;
	public static Item plate_armor_ajr;
	public static Item plate_armor_hev;
	public static Item plate_armor_lunar;
	public static Item plate_armor_fau;

	public static Item solid_fuel;
	public static Item rocket_fuel;

	public static Item crystal_iron;
	public static Item crystal_gold;
	public static Item crystal_redstone;
	public static Item crystal_diamond;
	public static Item crystal_uranium;
	public static Item crystal_thorium;
	public static Item crystal_plutonium;
	public static Item crystal_titanium;
	public static Item crystal_sulfur;
	public static Item crystal_niter;
	public static Item crystal_copper;
	public static Item crystal_tungsten;
	public static Item crystal_aluminium;
	public static Item crystal_fluorite;
	public static Item crystal_beryllium;
	public static Item crystal_lead;
	public static Item crystal_schraranium;
	public static Item crystal_schrabidium;
	public static Item crystal_rare;
	public static Item crystal_phosphorus;
	public static Item crystal_lithium;
	public static Item crystal_starmetal;
	public static Item crystal_trixite;

	public static Item powder_lead;
	public static Item powder_neptunium;
	public static Item powder_polonium;
	public static Item powder_schrabidium;
	public static Item powder_schrabidate;

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
	public static Item fallout;
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
	public static Item powder_chlorophyte;
	public static Item powder_red_copper;
	public static Item powder_steel;
	public static Item powder_lithium;
	public static Item powder_zirconium;
	public static Item redstone_depleted;

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

	public static Item powder_steel_tiny;
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
	public static Item powder_semtex_mix;
	public static Item powder_desh_mix;
	public static Item powder_desh_ready;
	public static Item powder_nitan_mix;
	public static Item powder_spark_mix;
	public static Item powder_yellowcake;
	public static Item powder_magic;
	public static Item powder_cloud;
	public static Item powder_balefire;

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
	public static Item motor_desh;
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
	public static Item chlorine_pinwheel;

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

	public static Item primer_357;
	public static Item primer_44;
	public static Item primer_9;
	public static Item primer_50;
	public static Item primer_buckshot;
	public static Item casing_357;
	public static Item casing_44;
	public static Item casing_9;
	public static Item casing_50;
	public static Item casing_buckshot;
	public static Item assembly_iron;
	public static Item assembly_steel;
	public static Item assembly_lead;
	public static Item assembly_gold;
	public static Item assembly_schrabidium;
	public static Item assembly_nightmare;
	public static Item assembly_desh;
	//public static Item assembly_pip;
	public static Item assembly_nopip;
	public static Item assembly_smg;
	public static Item assembly_556;
	public static Item assembly_uzi;
	public static Item assembly_actionexpress;
	public static Item assembly_calamity;
	public static Item assembly_lacunae;
	public static Item assembly_nuke;

	public static Item folly_shell;
	public static Item folly_bullet;
	public static Item folly_bullet_nuclear;
	public static Item folly_bullet_du;

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
	public static Item ring_starmetal;

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

	public static Item seg_10;
	public static Item seg_15;
	public static Item seg_20;

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

	public static Item stamp_357;
	public static Item stamp_44;
	public static Item stamp_9;
	public static Item stamp_50;

	public static Item blades_gold;
	public static Item blades_aluminium;
	public static Item blades_iron;
	public static Item blades_steel;
	public static Item blades_titanium;
	public static Item blades_advanced_alloy;
	public static Item blades_combine_steel;
	public static Item blades_schrabidium;
	public static Item blades_desh;

	public static Item part_lithium;
	public static Item part_beryllium;
	public static Item part_carbon;
	public static Item part_copper;
	public static Item part_plutonium;

	public static Item thermo_element;
	public static Item limiter;

	public static Item pellet_rtg;
	public static Item pellet_rtg_weak;
	public static Item pellet_rtg_polonium;
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

	public static Item ams_lens;

	public static Item ams_core_sing;
	public static Item ams_core_wormhole;
	public static Item ams_core_eyeofharmony;
	public static Item ams_core_thingy;

	public static Item fusion_shield_tungsten;
	public static Item fusion_shield_desh;
	public static Item fusion_shield_chlorophyte;
	public static Item fusion_shield_vaporwave;
	
	public static Item cell_empty;
	public static Item cell_uf6;
	public static Item cell_puf6;
	public static Item cell_deuterium;
	public static Item cell_tritium;
	public static Item cell_sas3;
	public static Item cell_antimatter;
	public static Item cell_anti_schrabidium;

	public static Item demon_core_open;
	public static Item demon_core_closed;

	public static Item particle_empty;
	public static Item particle_hydrogen;
	public static Item particle_copper;
	public static Item particle_lead;
	public static Item particle_aproton;
	public static Item particle_aelectron;
	public static Item particle_amat;
	public static Item particle_aschrab;
	public static Item particle_higgs;
	public static Item particle_muon;
	public static Item particle_strange;
	public static Item particle_dark;
	public static Item particle_sparkticle;
	public static Item particle_digamma;
	
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
	public static Item radaway_strong;
	public static Item radaway_flush;
	public static Item radx;
	public static Item xanax;
	public static Item fmn;
	public static Item five_htp;
	public static Item med_bag;
	public static Item pill_iodine;
	public static Item plan_c;
	public static Item stealth_boy;
	public static Item gas_mask_filter;
	public static Item jetpack_tank;
	public static Item gun_kit_1;
	public static Item gun_kit_2;
	public static Item cbt_device;

	public static Item can_empty;
	public static Item can_smart;
	public static Item can_creature;
	public static Item can_redbomb;
	public static Item can_mrsugar;
	public static Item can_overcharge;
	public static Item can_luna;
	public static Item can_bepis;
	public static Item can_breen;
	public static Item bottle_empty;
	public static Item bottle_nuka;
	public static Item bottle_cherry;
	public static Item bottle_quantum;
	public static Item bottle_sparkle;
	public static Item bottle_rad;
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
	public static Item cap_rad;
	public static Item cap_korl;
	public static Item cap_fritz;
	public static Item cap_sunset;
	public static Item cap_star;
	public static Item ring_pull;
	public static Item canned_beef;
	public static Item canned_tuna;
	public static Item canned_mystery;
	public static Item canned_pashtet;
	public static Item canned_cheese;
	public static Item canned_jizz;
	public static Item canned_milk;
	public static Item canned_ass;
	public static Item canned_pizza;
	public static Item canned_tube;
	public static Item canned_tomato;
	public static Item canned_asbestos;
	public static Item canned_bhole;
	public static Item canned_hotdogs;
	public static Item canned_leftovers;
	public static Item canned_yogurt;
	public static Item canned_stew;
	public static Item canned_chinese;
	public static Item canned_oil;
	public static Item canned_fist;
	public static Item canned_spam;
	public static Item canned_fried;
	public static Item canned_napalm;
	public static Item canned_diesel;
	public static Item canned_kerosene;
	public static Item canned_recursion;
	public static Item canned_bark;
	public static Item can_key;

	public static Item coin_creeper;
	public static Item coin_radiation;
	public static Item coin_maskman;
	public static Item coin_worm;

	public static Item rod_empty;
	public static Item rod_uranium;
	public static Item rod_th232;
	public static Item rod_u233;
	public static Item rod_u235;
	public static Item rod_u238;
	public static Item rod_plutonium;
	public static Item rod_pu238;
	public static Item rod_pu239;
	public static Item rod_pu240;
	public static Item rod_neptunium;
	public static Item rod_polonium;
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
	public static Item rod_balefire;
	public static Item rod_balefire_blazing;

	public static Item rod_dual_empty;
	public static Item rod_dual_uranium;
	public static Item rod_dual_th232;
	public static Item rod_dual_u233;
	public static Item rod_dual_u235;
	public static Item rod_dual_u238;
	public static Item rod_dual_plutonium;
	public static Item rod_dual_pu238;
	public static Item rod_dual_pu239;
	public static Item rod_dual_pu240;
	public static Item rod_dual_neptunium;
	public static Item rod_dual_polonium;
	public static Item rod_dual_lead;
	public static Item rod_dual_schrabidium;
	public static Item rod_dual_solinium;
	public static Item rod_dual_balefire;
	public static Item rod_dual_balefire_blazing;

	public static Item rod_quad_empty;
	public static Item rod_quad_uranium;
	public static Item rod_quad_th232;
	public static Item rod_quad_u233;
	public static Item rod_quad_u235;
	public static Item rod_quad_u238;
	public static Item rod_quad_plutonium;
	public static Item rod_quad_pu238;
	public static Item rod_quad_pu239;
	public static Item rod_quad_pu240;
	public static Item rod_quad_neptunium;
	public static Item rod_quad_polonium;
	public static Item rod_quad_lead;
	public static Item rod_quad_schrabidium;
	public static Item rod_quad_solinium;
	public static Item rod_quad_balefire;
	public static Item rod_quad_balefire_blazing;

	public static Item rod_uranium_fuel;
	public static Item rod_dual_uranium_fuel;
	public static Item rod_quad_uranium_fuel;
	public static Item rod_thorium_fuel;
	public static Item rod_dual_thorium_fuel;
	public static Item rod_quad_thorium_fuel;
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
	public static Item nuclear_waste_long;
	public static Item nuclear_waste_long_tiny;
	public static Item nuclear_waste_short;
	public static Item nuclear_waste_short_tiny;
	public static Item nuclear_waste_long_depleted;
	public static Item nuclear_waste_long_depleted_tiny;
	public static Item nuclear_waste_short_depleted;
	public static Item nuclear_waste_short_depleted_tiny;
	public static Item nuclear_waste;
	public static Item nuclear_waste_tiny;
	public static Item rod_uranium_fuel_depleted;
	public static Item rod_dual_uranium_fuel_depleted;
	public static Item rod_quad_uranium_fuel_depleted;
	public static Item rod_thorium_fuel_depleted;
	public static Item rod_dual_thorium_fuel_depleted;
	public static Item rod_quad_thorium_fuel_depleted;
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
	public static Item waste_uranium;
	public static Item waste_thorium;
	public static Item waste_plutonium;
	public static Item waste_mox;
	public static Item waste_schrabidium;
	public static Item waste_uranium_hot;
	public static Item waste_thorium_hot;
	public static Item waste_plutonium_hot;
	public static Item waste_mox_hot;
	public static Item waste_schrabidium_hot;

	public static Item containment_box;

	public static Item recycled_ground;
	public static Item recycled_rock;
	public static Item recycled_metal;
	public static Item recycled_refined;
	public static Item recycled_organic;
	public static Item recycled_crystal;
	public static Item recycled_explosive;
	public static Item recycled_electronic;
	public static Item recycled_nuclear;
	public static Item recycled_misc;
	
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
	public static Item cordite;
	public static Item ballistite;
	public static Item pellet_gas;
	public static Item magnetron;
	public static Item pellet_buckshot;
	public static Item pellet_flechette;
	public static Item pellet_chlorophyte;
	public static Item pellet_mercury;
	public static Item pellet_meteorite;
	public static Item pellet_canister;
	public static Item pellet_claws;
	public static Item pellet_charged;

	public static Item designator;
	public static Item designator_range;
	public static Item designator_manual;
	public static Item linker;
	public static Item reactor_sensor;
	public static Item oil_detector;
	public static Item geiger_counter;
	public static Item digamma_diagnostic;
	public static Item survey_scanner;
	public static Item mirror_tool;

	public static Item template_folder;
	public static Item journal_pip;
	public static Item journal_bj;
	public static Item journal_silver;
	public static Item assembly_template;
	public static Item chemistry_template;
	public static Item chemistry_icon;
	public static Item fluid_identifier;
	public static Item fluid_icon;
	public static Item siren_track;
	public static Item fluid_duct;

	public static Item bobmazon_materials;
	public static Item bobmazon_machines;
	public static Item bobmazon_weapons;
	public static Item bobmazon_tools;
	public static Item bobmazon_hidden;

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
	public static Item missile_emp_strong;
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

	public static Item mp_thruster_10_kerosene;
	public static Item mp_thruster_10_kerosene_tec;
	public static Item mp_thruster_10_solid;
	public static Item mp_thruster_10_xenon;
	public static Item mp_thruster_15_kerosene;
	public static Item mp_thruster_15_kerosene_tec;
	public static Item mp_thruster_15_kerosene_dual;
	public static Item mp_thruster_15_kerosene_triple;
	public static Item mp_thruster_15_solid;
	public static Item mp_thruster_15_solid_hexdecuple;
	public static Item mp_thruster_15_hydrogen;
	public static Item mp_thruster_15_hydrogen_dual;
	public static Item mp_thruster_15_balefire_short;
	public static Item mp_thruster_15_balefire;
	public static Item mp_thruster_15_balefire_large;
	public static Item mp_thruster_15_balefire_large_rad;
	public static Item mp_thruster_20_kerosene;
	public static Item mp_thruster_20_kerosene_dual;
	public static Item mp_thruster_20_kerosene_triple;
	public static Item mp_thruster_20_solid;
	public static Item mp_thruster_20_solid_multi;
	public static Item mp_thruster_20_solid_multier;

	public static Item mp_stability_10_flat;
	public static Item mp_stability_10_cruise;
	public static Item mp_stability_10_space;
	public static Item mp_stability_15_flat;
	public static Item mp_stability_15_thin;
	public static Item mp_stability_15_soyuz;
	public static Item mp_stability_20_flat;

	public static Item mp_fuselage_10_kerosene;
	public static Item mp_fuselage_10_kerosene_camo;
	public static Item mp_fuselage_10_kerosene_desert;
	public static Item mp_fuselage_10_kerosene_sky;
	public static Item mp_fuselage_10_kerosene_flames;
	public static Item mp_fuselage_10_kerosene_insulation;
	public static Item mp_fuselage_10_kerosene_sleek;
	public static Item mp_fuselage_10_kerosene_metal;
	public static Item mp_fuselage_10_kerosene_taint;
	
	public static Item mp_fuselage_10_solid;
	public static Item mp_fuselage_10_solid_flames;
	public static Item mp_fuselage_10_solid_insulation;
	public static Item mp_fuselage_10_solid_sleek;
	public static Item mp_fuselage_10_solid_soviet_glory;
	public static Item mp_fuselage_10_solid_cathedral;
	public static Item mp_fuselage_10_solid_moonlit;
	public static Item mp_fuselage_10_solid_battery;
	public static Item mp_fuselage_10_solid_duracell;

	public static Item mp_fuselage_10_xenon;
	public static Item mp_fuselage_10_xenon_bhole;
	
	public static Item mp_fuselage_10_long_kerosene;
	public static Item mp_fuselage_10_long_kerosene_camo;
	public static Item mp_fuselage_10_long_kerosene_desert;
	public static Item mp_fuselage_10_long_kerosene_sky;
	public static Item mp_fuselage_10_long_kerosene_flames;
	public static Item mp_fuselage_10_long_kerosene_insulation;
	public static Item mp_fuselage_10_long_kerosene_sleek;
	public static Item mp_fuselage_10_long_kerosene_metal;
	public static Item mp_fuselage_10_long_kerosene_taint;
	public static Item mp_fuselage_10_long_kerosene_dash;
	public static Item mp_fuselage_10_long_kerosene_vap;
	
	public static Item mp_fuselage_10_long_solid;
	public static Item mp_fuselage_10_long_solid_flames;
	public static Item mp_fuselage_10_long_solid_insulation;
	public static Item mp_fuselage_10_long_solid_sleek;
	public static Item mp_fuselage_10_long_solid_soviet_glory;
	public static Item mp_fuselage_10_long_solid_bullet;
	public static Item mp_fuselage_10_long_solid_silvermoonlight;
	
	public static Item mp_fuselage_10_15_kerosene;
	public static Item mp_fuselage_10_15_solid;
	public static Item mp_fuselage_10_15_hydrogen;
	public static Item mp_fuselage_10_15_balefire;
	
	public static Item mp_fuselage_15_kerosene;
	public static Item mp_fuselage_15_kerosene_camo;
	public static Item mp_fuselage_15_kerosene_desert;
	public static Item mp_fuselage_15_kerosene_sky;
	public static Item mp_fuselage_15_kerosene_insulation;
	public static Item mp_fuselage_15_kerosene_metal;
	public static Item mp_fuselage_15_kerosene_decorated;
	public static Item mp_fuselage_15_kerosene_steampunk;
	public static Item mp_fuselage_15_kerosene_polite;
	public static Item mp_fuselage_15_kerosene_blackjack;
	public static Item mp_fuselage_15_kerosene_lambda;
	public static Item mp_fuselage_15_kerosene_minuteman;
	public static Item mp_fuselage_15_kerosene_pip;
	public static Item mp_fuselage_15_kerosene_taint;
	public static Item mp_fuselage_15_kerosene_yuck;
	
	public static Item mp_fuselage_15_solid;
	public static Item mp_fuselage_15_solid_insulation;
	public static Item mp_fuselage_15_solid_desh;
	public static Item mp_fuselage_15_solid_soviet_glory;
	public static Item mp_fuselage_15_solid_soviet_stank;
	public static Item mp_fuselage_15_solid_faust;
	public static Item mp_fuselage_15_solid_silvermoonlight;
	public static Item mp_fuselage_15_solid_snowy;
	public static Item mp_fuselage_15_solid_panorama;
	public static Item mp_fuselage_15_solid_roses;

	public static Item mp_fuselage_15_hydrogen;
	public static Item mp_fuselage_15_hydrogen_cathedral;
	
	public static Item mp_fuselage_15_balefire;

	public static Item mp_fuselage_15_20_kerosene;
	public static Item mp_fuselage_15_20_kerosene_magnusson;
	public static Item mp_fuselage_15_20_solid;
	
	public static Item mp_fuselage_20_kerosene;

	public static Item mp_warhead_10_he;
	public static Item mp_warhead_10_incendiary;
	public static Item mp_warhead_10_buster;
	public static Item mp_warhead_10_nuclear;
	public static Item mp_warhead_10_nuclear_large;
	public static Item mp_warhead_10_taint;
	public static Item mp_warhead_10_cloud;
	public static Item mp_warhead_15_he;
	public static Item mp_warhead_15_incendiary;
	public static Item mp_warhead_15_nuclear;
	public static Item mp_warhead_15_nuclear_shark;
	public static Item mp_warhead_15_boxcar;
	public static Item mp_warhead_15_n2;
	public static Item mp_warhead_15_balefire;
	public static Item mp_warhead_20_he;
	
	public static Item mp_chip_1;
	public static Item mp_chip_2;
	public static Item mp_chip_3;
	public static Item mp_chip_4;
	public static Item mp_chip_5;

	public static Item missile_skin_camo;
	public static Item missile_skin_desert;
	public static Item missile_skin_flames;
	public static Item missile_skin_manly_pink;
	public static Item missile_skin_orange_insulation;
	public static Item missile_skin_sleek;
	public static Item missile_skin_soviet_glory;
	public static Item missile_skin_soviet_stank;
	public static Item missile_skin_metal;
	
	public static Item missile_custom;

	public static Item missile_carrier;
	public static Item missile_soyuz;
	public static Item missile_soyuz_lander;
	public static Item sat_mapper;
	public static Item sat_scanner;
	public static Item sat_radar;
	public static Item sat_laser;
	public static Item sat_foeq;
	public static Item sat_resonator;
	public static Item sat_miner;
	public static Item sat_gerald;
	public static Item sat_chip;
	public static Item sat_interface;
	public static Item sat_coord;

	public static Item ammo_12gauge;
	public static Item ammo_12gauge_incendiary;
	public static Item ammo_12gauge_shrapnel;
	public static Item ammo_12gauge_du;
	public static Item ammo_12gauge_sleek;
	public static Item ammo_12gauge_marauder;
	public static Item ammo_20gauge;
	public static Item ammo_20gauge_slug;
	public static Item ammo_20gauge_flechette;
	public static Item ammo_20gauge_incendiary;
	public static Item ammo_20gauge_shrapnel;
	public static Item ammo_20gauge_explosive;
	public static Item ammo_20gauge_caustic;
	public static Item ammo_20gauge_shock;
	public static Item ammo_20gauge_wither;
	public static Item ammo_20gauge_sleek;
	public static Item ammo_4gauge;
	public static Item ammo_4gauge_slug;
	public static Item ammo_4gauge_flechette;
	public static Item ammo_4gauge_flechette_phosphorus;
	public static Item ammo_4gauge_explosive;
	public static Item ammo_4gauge_semtex;
	public static Item ammo_4gauge_balefire;
	public static Item ammo_4gauge_kampf;
	public static Item ammo_4gauge_canister;
	public static Item ammo_4gauge_claw;
	public static Item ammo_4gauge_vampire;
	public static Item ammo_4gauge_void;
	public static Item ammo_4gauge_sleek;
	public static Item ammo_357_desh;
	public static Item ammo_44;
	public static Item ammo_44_ap;
	public static Item ammo_44_du;
	public static Item ammo_44_phosphorus;
	public static Item ammo_44_star;
	public static Item ammo_44_chlorophyte;
	public static Item ammo_44_pip;
	public static Item ammo_44_bj;
	public static Item ammo_44_silver;
	public static Item ammo_44_rocket;
	public static Item ammo_5mm;
	public static Item ammo_5mm_explosive;
	public static Item ammo_5mm_du;
	public static Item ammo_5mm_star;
	public static Item ammo_5mm_chlorophyte;
	public static Item ammo_9mm;
	public static Item ammo_9mm_ap;
	public static Item ammo_9mm_du;
	public static Item ammo_9mm_chlorophyte;
	public static Item ammo_9mm_rocket;
	public static Item ammo_556;
	public static Item ammo_566_gold;
	public static Item ammo_556_phosphorus;
	public static Item ammo_556_ap;
	public static Item ammo_556_du;
	public static Item ammo_556_star;
	public static Item ammo_556_chlorophyte;
	public static Item ammo_556_sleek;
	public static Item ammo_556_tracer;
	public static Item ammo_556_flechette;
	public static Item ammo_556_flechette_incendiary;
	public static Item ammo_556_flechette_phosphorus;
	public static Item ammo_556_flechette_du;
	public static Item ammo_556_flechette_chlorophyte;
	public static Item ammo_556_flechette_sleek;
	public static Item ammo_556_k;
	public static Item ammo_22lr;
	public static Item ammo_22lr_ap;
	public static Item ammo_22lr_chlorophyte;
	public static Item ammo_50ae;
	public static Item ammo_50ae_ap;
	public static Item ammo_50ae_du;
	public static Item ammo_50ae_star;
	public static Item ammo_50ae_chlorophyte;
	public static Item ammo_50bmg;
	public static Item ammo_50bmg_incendiary;
	public static Item ammo_50bmg_phosphorus;
	public static Item ammo_50bmg_explosive;
	public static Item ammo_50bmg_ap;
	public static Item ammo_50bmg_du;
	public static Item ammo_50bmg_star;
	public static Item ammo_50bmg_chlorophyte;
	public static Item ammo_50bmg_sleek;
	public static Item ammo_75bolt;
	public static Item ammo_75bolt_incendiary;
	public static Item ammo_75bolt_he;
	public static Item ammo_folly;
	public static Item ammo_folly_nuclear;
	public static Item ammo_folly_du;
	public static Item ammo_rocket;
	public static Item ammo_rocket_he;
	public static Item ammo_rocket_incendiary;
	public static Item ammo_rocket_phosphorus;
	public static Item ammo_rocket_shrapnel;
	public static Item ammo_rocket_emp;
	public static Item ammo_rocket_glare;
	public static Item ammo_rocket_toxic;
	public static Item ammo_rocket_canister;
	public static Item ammo_rocket_sleek;
	public static Item ammo_rocket_nuclear;
	public static Item ammo_rocket_rpc;
	public static Item ammo_grenade;
	public static Item ammo_grenade_he;
	public static Item ammo_grenade_incendiary;
	public static Item ammo_grenade_phosphorus;
	public static Item ammo_grenade_toxic;
	public static Item ammo_grenade_concussion;
	public static Item ammo_grenade_finned;
	public static Item ammo_grenade_sleek;
	public static Item ammo_grenade_nuclear;
	public static Item ammo_grenade_tracer;
	public static Item ammo_grenade_kampf;
	public static Item ammo_shell;
	public static Item ammo_shell_explosive;
	public static Item ammo_shell_apfsds_t;
	public static Item ammo_shell_apfsds_du;
	public static Item ammo_shell_w9;
	public static Item ammo_dgk;
	public static Item ammo_nuke;
	public static Item ammo_nuke_low;
	public static Item ammo_nuke_high;
	public static Item ammo_nuke_tots;
	public static Item ammo_nuke_safe;
	public static Item ammo_nuke_pumpkin;
	public static Item ammo_mirv;
	public static Item ammo_mirv_low;
	public static Item ammo_mirv_high;
	public static Item ammo_mirv_safe;
	public static Item ammo_mirv_special;
	public static Item ammo_fuel;
	public static Item ammo_fuel_napalm;
	public static Item ammo_fuel_phosphorus;
	public static Item ammo_fuel_vaporizer;
	public static Item ammo_fuel_gas;
	public static Item ammo_cell;
	public static Item ammo_dart;
	public static Item ammo_dart_nerf;
	
	public static Item gun_rpg;
	public static Item gun_rpg_ammo;
	public static Item gun_karl;
	public static Item gun_panzerschreck;
	public static Item gun_quadro;
	public static Item gun_hk69;
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
	public static Item gun_revolver_nopip;
	public static Item gun_revolver_blackjack;
	public static Item gun_revolver_silver;
	public static Item gun_revolver_red;
	public static Item gun_revolver_nopip_ammo;
	public static Item gun_deagle;
	public static Item gun_flechette;
	public static Item gun_calamity;
	public static Item gun_calamity_dual;
	public static Item gun_calamity_ammo;
	public static Item gun_minigun;
	public static Item gun_avenger;
	public static Item gun_lacunae;
	public static Item gun_lacunae_ammo;
	public static Item gun_folly;
	public static Item gun_fatman;
	public static Item gun_proto;
	public static Item gun_fatman_ammo;
	public static Item gun_mirv;
	public static Item gun_mirv_ammo;
	public static Item gun_bf;
	public static Item gun_bf_ammo;
	public static Item gun_mp40;
	public static Item gun_mp40_ammo;
	public static Item gun_thompson;
	public static Item gun_uzi;
	public static Item gun_uzi_silencer;
	public static Item gun_uzi_saturnite;
	public static Item gun_uzi_saturnite_silencer;
	public static Item gun_uzi_ammo;
	public static Item gun_uboinik;
	public static Item gun_uboinik_ammo;
	public static Item gun_supershotgun;
	public static Item gun_ks23;
	public static Item gun_sauer;
	public static Item gun_lever_action;
	public static Item gun_lever_action_dark;
	public static Item gun_lever_action_sonata;
	public static Item gun_lever_action_ammo;
	public static Item gun_bolt_action;
	public static Item gun_bolt_action_green;
	public static Item gun_bolt_action_saturnite;
	public static Item gun_bolt_action_ammo;
	public static Item gun_mymy;
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
	public static Item gun_flamer;
	public static Item gun_cryolator;
	public static Item gun_cryolator_ammo;
	public static Item gun_mp;
	public static Item gun_bolter;
	public static Item gun_bolter_digamma;
	public static Item gun_brimstone;
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
	public static Item gun_vortex;
	public static Item gun_dampfmaschine;
	public static Item gun_waluigi;
	public static Item gun_darter;
	
	public static Item crucible;

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

	public static Item grenade_if_generic;
	public static Item grenade_if_he;
	public static Item grenade_if_bouncy;
	public static Item grenade_if_sticky;
	public static Item grenade_if_impact;
	public static Item grenade_if_incendiary;
	public static Item grenade_if_toxic;
	public static Item grenade_if_concussion;
	public static Item grenade_if_brimstone;
	public static Item grenade_if_mystery;
	public static Item grenade_if_spark;
	public static Item grenade_if_hopwire;
	public static Item grenade_if_null;

	public static Item grenade_smart;
	public static Item grenade_mirv;
	public static Item grenade_breach;
	public static Item grenade_burst;
	
	public static Item nuclear_waste_pearl;

	public static Item weaponized_starblaster_cell;

	public static Item bomb_waffle;
	public static Item schnitzel_vegan;
	public static Item cotton_candy;
	public static Item apple_lead;
	public static Item apple_schrabidium;
	public static Item tem_flakes;
	public static Item glowing_stew;
	public static Item balefire_scrambled;
	public static Item balefire_and_ham;
	public static Item lemon;
	public static Item definitelyfood;
	public static Item loops;
	public static Item loop_stew;
	public static Item spongebob_macaroni;
	public static Item fooditem;
	public static Item twinkie;
	public static Item static_sandwich;
	public static Item pudding;
	public static Item pancake;
	public static Item nugget;
	public static Item peas;

	public static Item med_ipecac;
	public static Item med_ptsd;
	public static Item med_schizophrenia;

	public static Item canteen_13;
	public static Item canteen_vodka;
	public static Item canteen_fab;
	
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
	public static Item egg_balefire_shard;
	public static Item egg_balefire;

	public static Item custom_tnt;
	public static Item custom_nuke;
	public static Item custom_hydro;
	public static Item custom_amat;
	public static Item custom_dirty;
	public static Item custom_schrab;
	public static Item custom_fall;

	public static Item battery_generic;
	public static Item battery_advanced;
	public static Item battery_lithium;
	public static Item battery_schrabidium;
	public static Item battery_spark;
	public static Item battery_trixite;
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
	public static Item hev_battery;
	public static Item fusion_core;
	public static Item fusion_core_infinite;
	public static Item energy_core;
	public static Item fuse;
	public static Item redcoil_capacitor;
	public static Item titanium_filter;
	public static Item screwdriver;
	public static Item overfuse;
	public static Item arc_electrode;
	public static Item arc_electrode_burnt;
	public static Item arc_electrode_desh;
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
	public static Item upgrade_overdrive_1;
	public static Item upgrade_overdrive_2;
	public static Item upgrade_overdrive_3;
	public static Item upgrade_radius;
	public static Item upgrade_health;
	public static Item upgrade_smelter;
	public static Item upgrade_shredder;
	public static Item upgrade_centrifuge;
	public static Item upgrade_crystallizer;
	public static Item upgrade_nullifier;
	public static Item upgrade_screm;

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
	public static Item hat;
	public static Item beta;
	
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
	public static Item security_helmet;
	public static Item security_plate;
	public static Item security_legs;
	public static Item security_boots;
	public static Item cobalt_helmet;
	public static Item cobalt_plate;
	public static Item cobalt_legs;
	public static Item cobalt_boots;
	public static Item starmetal_helmet;
	public static Item starmetal_plate;
	public static Item starmetal_legs;
	public static Item starmetal_boots;
	public static Item dnt_helmet;
	public static Item dnt_plate;
	public static Item dnt_legs;
	public static Item dnt_boots;
	public static Item ajr_helmet;
	public static Item ajr_plate;
	public static Item ajr_legs;
	public static Item ajr_boots;
	public static Item ajro_helmet;
	public static Item ajro_plate;
	public static Item ajro_legs;
	public static Item ajro_boots;
	public static Item bj_helmet;
	public static Item bj_plate;
	public static Item bj_plate_jetpack;
	public static Item bj_legs;
	public static Item bj_boots;
	public static Item hev_helmet;
	public static Item hev_plate;
	public static Item hev_legs;
	public static Item hev_boots;
	public static Item fau_helmet;
	public static Item fau_plate;
	public static Item fau_legs;
	public static Item fau_boots;

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
	public static Item cobalt_sword;
	public static Item cobalt_pickaxe;
	public static Item cobalt_axe;
	public static Item cobalt_shovel;
	public static Item cobalt_hoe;
	public static Item cobalt_decorated_sword;
	public static Item cobalt_decorated_pickaxe;
	public static Item cobalt_decorated_axe;
	public static Item cobalt_decorated_shovel;
	public static Item cobalt_decorated_hoe;
	public static Item starmetal_sword;
	public static Item starmetal_pickaxe;
	public static Item starmetal_axe;
	public static Item starmetal_shovel;
	public static Item starmetal_hoe;
	public static Item smashing_hammer;
	public static Item centri_stick;
	public static Item drax;
	public static Item drax_mk2;
	public static Item drax_mk3;
	public static Item chlorophyte_pickaxe;
	public static Item mese_pickaxe;

	public static Item meteorite_sword;
	public static Item meteorite_sword_seared;
	public static Item meteorite_sword_reforged;
	public static Item meteorite_sword_hardened;
	public static Item meteorite_sword_alloyed;
	public static Item meteorite_sword_machined;
	public static Item meteorite_sword_treated;
	public static Item meteorite_sword_etched;
	public static Item meteorite_sword_bred;
	public static Item meteorite_sword_irradiated;
	public static Item meteorite_sword_fused;
	public static Item meteorite_sword_baleful;

	public static Item matchstick;
	public static Item balefire_and_steel;
	
	public static Item mask_of_infamy;

	public static Item schrabidium_hammer;
	public static Item shimmer_sledge;
	public static Item shimmer_axe;
	public static Item bottle_opener;
	public static Item pch; //for compat please do not hit me
	public static Item wood_gavel;
	public static Item lead_gavel;
	public static Item diamond_gavel;
	public static Item mese_gavel;

	public static Item crowbar;

	public static Item wrench;
	public static Item wrench_flipped;
	public static Item memespoon;

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
	public static Item stopsign;
	public static Item sopsign;
	public static Item chernobylsign;

	public static Item crystal_horn;
	public static Item crystal_charred;

	public static Item attachment_mask;
	public static Item back_tesla;
	public static Item servo_set;
	public static Item servo_set_desh;
	public static Item pads_rubber;
	public static Item pads_slime;
	public static Item pads_static;
	public static Item cladding_paint;
	public static Item cladding_rubber;
	public static Item cladding_lead;
	public static Item cladding_desh;
	public static Item cladding_obsidian;
	public static Item insert_kevlar;
	public static Item insert_sapi;
	public static Item insert_esapi;
	public static Item insert_xsapi;
	public static Item insert_steel;
	public static Item insert_du;
	public static Item insert_polonium;
	public static Item insert_era;
	public static Item insert_yharonite;
	public static Item insert_doxium;
	public static Item armor_polish;
	public static Item bandaid;
	public static Item serum;
	public static Item quartz_plutonium;
	public static Item morning_glory;
	public static Item lodestone;
	public static Item horseshoe_magnet;
	public static Item industrial_magnet;
	public static Item bathwater;
	public static Item bathwater_mk2;
	public static Item spider_milk;
	public static Item ink;
	public static Item heart_piece;
	public static Item heart_container;
	public static Item heart_booster;
	public static Item heart_fab;
	public static Item wd40;
	public static Item scrumpy;
	public static Item wild_p;
	public static Item fabsols_vodka;

	public static Item hazmat_helmet;
	public static Item hazmat_plate;
	public static Item hazmat_legs;
	public static Item hazmat_boots;
	public static Item hazmat_helmet_red;
	public static Item hazmat_plate_red;
	public static Item hazmat_legs_red;
	public static Item hazmat_boots_red;
	public static Item hazmat_helmet_grey;
	public static Item hazmat_plate_grey;
	public static Item hazmat_legs_grey;
	public static Item hazmat_boots_grey;

	public static Item hazmat_paa_helmet;
	public static Item hazmat_paa_plate;
	public static Item hazmat_paa_legs;
	public static Item hazmat_paa_boots;

	public static Item wand;
	public static Item wand_s;
	public static Item wand_d;

	public static Item rod_of_discord;

	public static Item cape_test;
	public static Item cape_radiation;
	public static Item cape_gasmask;
	public static Item cape_schrabidium;
	/*public static Item cape_hbm;
	public static Item cape_dafnik;
	public static Item cape_lpkukin;
	public static Item cape_vertice;
	public static Item cape_codered_;
	public static Item cape_ayy;
	public static Item cape_nostalgia;*/

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
	public static Item custom_kit;
	public static Item grenade_kit;
	public static Item fleija_kit;
	public static Item prototype_kit;
	public static Item missile_kit;
	public static Item t45_kit;
	public static Item euphemium_kit;
	public static Item solinium_kit;
	public static Item hazmat_kit;
	public static Item hazmat_red_kit;
	public static Item hazmat_grey_kit;

	public static Item loot_10;
	public static Item loot_15;
	public static Item loot_misc;
	
	public static Item clip_revolver_iron;
	public static Item clip_revolver;
	public static Item clip_revolver_gold;
	public static Item clip_revolver_lead;
	public static Item clip_revolver_schrabidium;
	public static Item clip_revolver_cursed;
	public static Item clip_revolver_nightmare;
	public static Item clip_revolver_nightmare2;
	public static Item clip_revolver_pip;
	public static Item clip_revolver_nopip;
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
	public static Item detonator_deadman;
	public static Item detonator_de;
	public static Item crate_caller;
	public static Item bomb_caller;
	public static Item meteor_remote;
	public static Item remote;
	public static Item turret_control;
	public static Item turret_chip;
	public static Item turret_biometry;
	
	public static Item spawn_chopper;
	public static Item spawn_worm;

	public static Item key;
	public static Item key_red;
	public static Item key_kit;
	public static Item key_fake;
	public static Item pin;
	public static Item padlock_rusty;
	public static Item padlock;
	public static Item padlock_reinforced;
	public static Item padlock_unbreakable;

	public static Item mech_key;

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
	public static Item bucket_schrabidic_acid;

	public static Item door_metal;
	public static Item door_office;
	public static Item door_bunker;
	
	public static Item record_lc;
	public static Item record_ss;
	public static Item record_vc;
	public static Item record_glass;

	public static Item book_guide;

	public static Item polaroid;
	public static Item glitch;
	public static Item letter;
	public static Item book_secret;
	public static Item book_of_;
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

	public static Item bob_metalworks;
	public static Item bob_assembly;
	public static Item bob_chemistry;
	public static Item bob_oil;
	public static Item bob_nuclear;
	
	public static Item digamma_see;
	public static Item digamma_feel;
	public static Item digamma_know;
	public static Item digamma_kauai_moho;
	public static Item digamma_up_on_top;

	public static final int guiID_item_folder = 1099;
	public static final int guiID_item_designator = 10100;
	public static final int guiID_item_sat_interface = 10101;
	public static final int guiID_item_box = 10102;
	public static final int guiID_item_bobmazon = 10103;
	public static final int guiID_item_sat_coord = 10104;
	public static final int guiID_item_book = 10105;
	public static final int guiID_item_guide = 10106;

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
		
		ingot_th232 = new ItemHazard(ItemHazard.th232 * ItemHazard.ingot).setUnlocalizedName("ingot_th232").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_th232");
		ingot_uranium = new ItemHazard(ItemHazard.u * ItemHazard.ingot).setUnlocalizedName("ingot_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_uranium");
		ingot_u233 = new ItemHazard(ItemHazard.u233 * ItemHazard.ingot).setUnlocalizedName("ingot_u233").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_u233");
		ingot_u235 = new ItemHazard(ItemHazard.u235 * ItemHazard.ingot).setUnlocalizedName("ingot_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_u235");
		ingot_u238 = new ItemHazard(ItemHazard.u238 * ItemHazard.ingot).setUnlocalizedName("ingot_u238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_u238");
		ingot_u238m2 = new ItemUnstable(350, 200).setUnlocalizedName("ingot_u238m2").setCreativeTab(null).setTextureName(RefStrings.MODID + ":ingot_u238m2");
		ingot_plutonium = new ItemHazard(ItemHazard.pu * ItemHazard.ingot).setUnlocalizedName("ingot_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ingot_pu238 = new ItemHazard(ItemHazard.pu238 * ItemHazard.ingot, true).setUnlocalizedName("ingot_pu238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu238");
		ingot_pu239 = new ItemHazard(ItemHazard.pu239 * ItemHazard.ingot).setUnlocalizedName("ingot_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu239");
		ingot_pu240 = new ItemHazard(ItemHazard.pu240 * ItemHazard.ingot).setUnlocalizedName("ingot_pu240").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu240");
		ingot_pu_mix = new ItemHazard(ItemHazard.purg * ItemHazard.ingot).setUnlocalizedName("ingot_pu_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu_mix");
		ingot_neptunium = new ItemHazard(ItemHazard.np237 * ItemHazard.ingot).setUnlocalizedName("ingot_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_neptunium");
		ingot_polonium = new ItemHazard(ItemHazard.po210 * ItemHazard.ingot, true).setUnlocalizedName("ingot_polonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_polonium");
		ingot_titanium = new Item().setUnlocalizedName("ingot_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_titanium");
		ingot_cobalt = new Item().setUnlocalizedName("ingot_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_cobalt");
		sulfur = new Item().setUnlocalizedName("sulfur").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sulfur");

		ingot_uranium_fuel = new ItemHazard(ItemHazard.uf * ItemHazard.ingot).setUnlocalizedName("ingot_uranium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_uranium_fuel");
		ingot_plutonium_fuel = new ItemHazard(ItemHazard.puf * ItemHazard.ingot).setUnlocalizedName("ingot_plutonium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_plutonium_fuel");
		ingot_mox_fuel = new ItemHazard(ItemHazard.mox * ItemHazard.ingot).setUnlocalizedName("ingot_mox_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_mox_fuel");
		ingot_schrabidium_fuel = new ItemHazard(ItemHazard.saf * ItemHazard.ingot, false, true).setUnlocalizedName("ingot_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidium_fuel");
		ingot_thorium_fuel = new ItemHazard(ItemHazard.thf * ItemHazard.ingot).setUnlocalizedName("ingot_thorium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_thorium_fuel");
		nugget_uranium_fuel = new ItemHazard(ItemHazard.uf * ItemHazard.nugget).setUnlocalizedName("nugget_uranium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_uranium_fuel");
		nugget_thorium_fuel = new ItemHazard(ItemHazard.thf * ItemHazard.nugget).setUnlocalizedName("nugget_thorium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_thorium_fuel");
		nugget_plutonium_fuel = new ItemHazard(ItemHazard.puf * ItemHazard.nugget).setUnlocalizedName("nugget_plutonium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_plutonium_fuel");
		nugget_mox_fuel = new ItemHazard(ItemHazard.mox * ItemHazard.nugget).setUnlocalizedName("nugget_mox_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mox_fuel");
		nugget_schrabidium_fuel = new ItemHazard(ItemHazard.saf * ItemHazard.nugget, false, true).setUnlocalizedName("nugget_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_schrabidium_fuel");
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
		ingot_schraranium = new ItemSchraranium(ItemHazard.sa326 * ItemHazard.nugget, false, true).setUnlocalizedName("ingot_schraranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schraranium");
		ingot_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.ingot, false, true).setUnlocalizedName("ingot_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidium");
		ingot_schrabidate = new ItemHazard(ItemHazard.sa326 * ItemHazard.ingot, false, true).setUnlocalizedName("ingot_schrabidate").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidate");
		plate_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.ingot, false, true).setUnlocalizedName("plate_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_schrabidium");
		plate_copper = new Item().setUnlocalizedName("plate_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_copper");
		plate_gold = new Item().setUnlocalizedName("plate_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_gold");
		plate_advanced_alloy = new Item().setUnlocalizedName("plate_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_advanced_alloy");
		lithium = new ItemHazard().addHydroReactivity().setUnlocalizedName("lithium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":lithium");
		ingot_zirconium = new Item().setUnlocalizedName("ingot_zirconium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_zirconium");
		ingot_semtex = new ItemLemon(4, 5, true).setUnlocalizedName("ingot_semtex").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_semtex");
		ingot_phosphorus = new ItemHazard(0F, true).setUnlocalizedName("ingot_phosphorus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_phosphorus");
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
		plate_kevlar = new Item().setUnlocalizedName("plate_kevlar").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_kevlar");
		plate_dineutronium = new Item().setUnlocalizedName("plate_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_dineutronium");
		plate_desh = new Item().setUnlocalizedName("plate_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_desh");
		ingot_solinium = new ItemHazard(ItemHazard.sa327 * ItemHazard.ingot, false, true).setUnlocalizedName("ingot_solinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_solinium");
		nugget_solinium = new ItemHazard(ItemHazard.sa327 * ItemHazard.nugget, false, true).setUnlocalizedName("nugget_solinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_solinium");
		photo_panel = new Item().setUnlocalizedName("photo_panel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":photo_panel");
		sat_base = new Item().setUnlocalizedName("sat_base").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_base");
		thruster_nuclear = new Item().setUnlocalizedName("thruster_nuclear").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thruster_nuclear");

		billet_uranium = new ItemHazard(ItemHazard.u * ItemHazard.billet).setUnlocalizedName("billet_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_uranium");
		billet_u233 = new ItemHazard(ItemHazard.u233 * ItemHazard.billet).setUnlocalizedName("billet_u233").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_u233");
		billet_u235 = new ItemHazard(ItemHazard.u235 * ItemHazard.billet).setUnlocalizedName("billet_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_u235");
		billet_u238 = new ItemHazard(ItemHazard.u238 * ItemHazard.billet).setUnlocalizedName("billet_u238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_u238");
		billet_th232 = new ItemHazard(ItemHazard.th232 * ItemHazard.billet).setUnlocalizedName("billet_th232").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_th232");
		billet_plutonium = new ItemHazard(ItemHazard.pu * ItemHazard.billet).setUnlocalizedName("billet_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_plutonium");
		billet_pu238 = new ItemHazard(ItemHazard.pu238 * ItemHazard.billet, true).setUnlocalizedName("billet_pu238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu238");
		billet_pu239 = new ItemHazard(ItemHazard.pu239 * ItemHazard.billet).setUnlocalizedName("billet_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu239");
		billet_pu240 = new ItemHazard(ItemHazard.pu240 * ItemHazard.billet).setUnlocalizedName("billet_pu240").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu240");
		billet_pu_mix = new ItemHazard(ItemHazard.purg * ItemHazard.billet).setUnlocalizedName("billet_pu_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu_mix");
		billet_neptunium = new ItemHazard(ItemHazard.np237 * ItemHazard.billet).setUnlocalizedName("billet_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_neptunium");
		billet_polonium = new ItemHazard(ItemHazard.po210 * ItemHazard.billet, true).setUnlocalizedName("billet_polonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_polonium");
		billet_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.billet, false, true).setUnlocalizedName("billet_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_schrabidium");
		billet_solinium = new ItemHazard(ItemHazard.sa327 * ItemHazard.billet, false, true).setUnlocalizedName("billet_solinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_solinium");
		billet_uranium_fuel = new ItemHazard(ItemHazard.uf * ItemHazard.billet).setUnlocalizedName("billet_uranium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_uranium_fuel");
		billet_thorium_fuel = new ItemHazard(ItemHazard.thf * ItemHazard.billet).setUnlocalizedName("billet_thorium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_thorium_fuel");
		billet_plutonium_fuel = new ItemHazard(ItemHazard.puf * ItemHazard.billet).setUnlocalizedName("billet_plutonium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_plutonium_fuel");
		billet_mox_fuel = new ItemHazard(ItemHazard.mox * ItemHazard.billet).setUnlocalizedName("billet_mox_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_mox_fuel");
		billet_schrabidium_fuel = new ItemHazard(ItemHazard.saf * ItemHazard.billet, false, true).setUnlocalizedName("billet_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_schrabidium_fuel");
		billet_yharonite = new Item().setUnlocalizedName("billet_yharonite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_yharonite");

		ingot_dura_steel = new ItemCustomLore().setUnlocalizedName("ingot_dura_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_dura_steel");
		ingot_polymer = new ItemCustomLore().setUnlocalizedName("ingot_polymer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_polymer");
		ingot_desh = new ItemCustomLore().setUnlocalizedName("ingot_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_desh");
		nugget_desh = new ItemCustomLore().setUnlocalizedName("nugget_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_desh");
		ingot_dineutronium = new ItemCustomLore().setUnlocalizedName("ingot_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_dineutronium");
		nugget_dineutronium = new ItemCustomLore().setUnlocalizedName("nugget_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_dineutronium");
		powder_dineutronium = new ItemCustomLore().setUnlocalizedName("powder_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_dineutronium");
		ingot_starmetal = new ItemStarmetal().setUnlocalizedName("ingot_starmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_starmetal");
		ingot_saturnite = new ItemCustomLore().setUnlocalizedName("ingot_saturnite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_saturnite");
		plate_saturnite = new ItemCustomLore().setUnlocalizedName("plate_saturnite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_saturnite");
		ingot_fiberglass = new ItemCustomLore().setUnlocalizedName("ingot_fiberglass").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_fiberglass");
		ingot_asbestos = new ItemHazard().addAsbestos().setUnlocalizedName("ingot_asbestos").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_asbestos");
		powder_asbestos = new ItemHazard().addAsbestos().setUnlocalizedName("powder_asbestos").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_asbestos");
		ingot_electronium = new ItemCustomLore().setUnlocalizedName("ingot_electronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_electronium");
		nugget_mercury = new ItemCustomLore().setUnlocalizedName("nugget_mercury").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mercury");
		bottle_mercury = new ItemCustomLore().setUnlocalizedName("bottle_mercury").setContainerItem(Items.glass_bottle).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bottle_mercury");

		ingot_lanthanium = new ItemCustomLore().setUnlocalizedName("ingot_lanthanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_lanthanium");
		ingot_actinium = new ItemCustomLore().setUnlocalizedName("ingot_actinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_actinium");

		ingot_meteorite = new ItemHot(200).setUnlocalizedName("ingot_meteorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_meteorite");
		ingot_meteorite_forged = new ItemHot(200).setUnlocalizedName("ingot_meteorite_forged").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_meteorite_forged");
		blade_meteorite = new ItemHot(200).setUnlocalizedName("blade_meteorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":blade_meteorite");
		ingot_steel_dusted = new ItemHotDusted(200).setUnlocalizedName("ingot_steel_dusted").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_steel_dusted");
		ingot_chainsteel = new ItemHot(100).setUnlocalizedName("ingot_chainsteel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_chainsteel");

		plate_armor_titanium = new Item().setUnlocalizedName("plate_armor_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_titanium");
		plate_armor_ajr = new Item().setUnlocalizedName("plate_armor_ajr").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_ajr");
		plate_armor_hev = new Item().setUnlocalizedName("plate_armor_hev").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_hev");
		plate_armor_lunar = new Item().setUnlocalizedName("plate_armor_lunar").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_lunar");
		plate_armor_fau = new Item().setUnlocalizedName("plate_armor_fau").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_fau");

		solid_fuel = new Item().setUnlocalizedName("solid_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel");
		rocket_fuel = new Item().setUnlocalizedName("rocket_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":rocket_fuel");
		coke = new Item().setUnlocalizedName("coke").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coke");
		lignite = new Item().setUnlocalizedName("lignite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":lignite");
		briquette_lignite = new Item().setUnlocalizedName("briquette_lignite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":briquette_lignite");
		powder_lignite = new Item().setUnlocalizedName("powder_lignite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lignite");

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

		nugget_th232 = new ItemHazard(ItemHazard.th232 * ItemHazard.nugget).setUnlocalizedName("nugget_th232").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_th232");
		nugget_uranium = new ItemHazard(ItemHazard.u * ItemHazard.nugget).setUnlocalizedName("nugget_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_uranium");
		nugget_u233 = new ItemHazard(ItemHazard.u233 * ItemHazard.nugget).setUnlocalizedName("nugget_u233").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_u233");
		nugget_u235 = new ItemHazard(ItemHazard.u235 * ItemHazard.nugget).setUnlocalizedName("nugget_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_u235");
		nugget_u238 = new ItemHazard(ItemHazard.u238 * ItemHazard.nugget).setUnlocalizedName("nugget_u238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_u238");
		nugget_plutonium = new ItemHazard(ItemHazard.pu * ItemHazard.nugget).setUnlocalizedName("nugget_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		nugget_pu238 = new ItemHazard(ItemHazard.pu238 * ItemHazard.nugget, true).setUnlocalizedName("nugget_pu238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu238");
		nugget_pu239 = new ItemHazard(ItemHazard.pu239 * ItemHazard.nugget).setUnlocalizedName("nugget_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu239");
		nugget_pu240 = new ItemHazard(ItemHazard.pu240 * ItemHazard.nugget).setUnlocalizedName("nugget_pu240").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu240");
		nugget_pu_mix = new ItemHazard(ItemHazard.purg * ItemHazard.nugget).setUnlocalizedName("nugget_pu_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu_mix");
		nugget_neptunium = new ItemHazard(ItemHazard.np237 * ItemHazard.nugget).setUnlocalizedName("nugget_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_neptunium");
		nugget_polonium = new ItemHazard(ItemHazard.po210 * ItemHazard.nugget, true).setUnlocalizedName("nugget_polonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_polonium");
		plate_titanium = new Item().setUnlocalizedName("plate_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_titanium");
		plate_aluminium = new Item().setUnlocalizedName("plate_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_aluminium");
		wire_red_copper = new Item().setUnlocalizedName("wire_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_red_copper");
		wire_tungsten = new ItemCustomLore().setUnlocalizedName("wire_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_tungsten");
		neutron_reflector = new Item().setUnlocalizedName("neutron_reflector").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":neutron_reflector");
		nugget_lead = new Item().setUnlocalizedName("nugget_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_lead");
		nugget_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.nugget, false, true).setUnlocalizedName("nugget_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_schrabidium");
		nugget_beryllium = new Item().setUnlocalizedName("nugget_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_beryllium");
		hazmat_cloth = new Item().setUnlocalizedName("hazmat_cloth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth");
		hazmat_cloth_red = new Item().setUnlocalizedName("hazmat_cloth_red").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth_red");
		hazmat_cloth_grey = new Item().setUnlocalizedName("hazmat_cloth_grey").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth_grey");
		asbestos_cloth = new Item().setUnlocalizedName("asbestos_cloth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":asbestos_cloth");
		filter_coal = new Item().setUnlocalizedName("filter_coal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":filter_coal");
		ingot_hes = new ItemHazard(ItemHazard.saf * ItemHazard.ingot, false, true).setUnlocalizedName("ingot_hes").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_hes");
		ingot_les = new ItemHazard(ItemHazard.saf * ItemHazard.ingot, false, true).setUnlocalizedName("ingot_les").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_les");
		nugget_hes = new ItemHazard(ItemHazard.saf * ItemHazard.nugget, false, true).setUnlocalizedName("nugget_hes").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_hes");
		nugget_les = new ItemHazard(ItemHazard.saf * ItemHazard.nugget, false, true).setUnlocalizedName("nugget_les").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_les");
		plate_combine_steel = new Item().setUnlocalizedName("plate_combine_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_combine_steel");

		crystal_iron = new Item().setUnlocalizedName("crystal_iron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_iron");
		crystal_gold = new Item().setUnlocalizedName("crystal_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_gold");
		crystal_redstone = new Item().setUnlocalizedName("crystal_redstone").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_redstone");
		crystal_diamond = new Item().setUnlocalizedName("crystal_diamond").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_diamond");
		crystal_uranium = new ItemHazard(ItemHazard.u * ItemHazard.block).setUnlocalizedName("crystal_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_uranium");
		crystal_thorium = new ItemHazard(ItemHazard.th232 * ItemHazard.block).setUnlocalizedName("crystal_thorium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_thorium");
		crystal_plutonium = new ItemHazard(ItemHazard.pu * ItemHazard.block).setUnlocalizedName("crystal_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_plutonium");
		crystal_titanium = new Item().setUnlocalizedName("crystal_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_titanium");
		crystal_sulfur = new Item().setUnlocalizedName("crystal_sulfur").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_sulfur");
		crystal_niter = new Item().setUnlocalizedName("crystal_niter").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_niter");
		crystal_copper = new Item().setUnlocalizedName("crystal_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_copper");
		crystal_tungsten = new Item().setUnlocalizedName("crystal_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_tungsten");
		crystal_aluminium = new Item().setUnlocalizedName("crystal_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_aluminium");
		crystal_fluorite = new Item().setUnlocalizedName("crystal_fluorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_fluorite");
		crystal_beryllium = new Item().setUnlocalizedName("crystal_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_beryllium");
		crystal_lead = new Item().setUnlocalizedName("crystal_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_lead");
		crystal_schraranium = new ItemHazard(ItemHazard.sa326 * ItemHazard.ingot, false, true).setUnlocalizedName("crystal_schraranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_schraranium");
		crystal_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.block, false, true).setUnlocalizedName("crystal_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_schrabidium");
		crystal_rare = new Item().setUnlocalizedName("crystal_rare").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_rare");
		crystal_phosphorus = new ItemHazard(0.0F, true).setUnlocalizedName("crystal_phosphorus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_phosphorus");
		crystal_lithium = new ItemHazard().addHydroReactivity().setUnlocalizedName("crystal_lithium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_lithium");
		crystal_starmetal = new Item().setUnlocalizedName("crystal_starmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_starmetal");
		crystal_trixite = new ItemHazard(10.0F).setUnlocalizedName("crystal_trixite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_trixite");
		
		powder_lead = new Item().setUnlocalizedName("powder_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lead");
		powder_neptunium = new ItemHazard(ItemHazard.np237 * ItemHazard.ingot, true).setUnlocalizedName("powder_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neptunium");
		powder_polonium = new ItemHazard(ItemHazard.po210 * ItemHazard.ingot, true).setUnlocalizedName("powder_polonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_polonium");
		powder_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.ingot, true, true).setUnlocalizedName("powder_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_schrabidium");
		powder_schrabidate = new ItemHazard(ItemHazard.sa326 * ItemHazard.ingot, true, true).setUnlocalizedName("powder_schrabidate").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_schrabidate");
		powder_aluminium = new Item().setUnlocalizedName("powder_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_aluminium");
		powder_beryllium = new Item().setUnlocalizedName("powder_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_beryllium");
		powder_copper = new Item().setUnlocalizedName("powder_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_copper");
		powder_gold = new Item().setUnlocalizedName("powder_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_gold");
		powder_iron = new Item().setUnlocalizedName("powder_iron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_iron");
		powder_titanium = new Item().setUnlocalizedName("powder_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_titanium");
		powder_tungsten = new Item().setUnlocalizedName("powder_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tungsten");
		powder_uranium = new ItemHazard(ItemHazard.u * ItemHazard.ingot, true).setUnlocalizedName("powder_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_uranium");
		powder_plutonium = new ItemHazard(ItemHazard.pu * ItemHazard.ingot, true).setUnlocalizedName("powder_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_plutonium");
		dust = new ItemCustomLore().setUnlocalizedName("dust").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":dust");
		fallout = new ItemHazard(10.0F).setUnlocalizedName("fallout").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fallout");
		powder_advanced_alloy = new Item().setUnlocalizedName("powder_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_advanced_alloy");
		powder_coal = new Item().setUnlocalizedName("powder_coal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_coal");
		powder_combine_steel = new Item().setUnlocalizedName("powder_combine_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_combine_steel");
		powder_diamond = new Item().setUnlocalizedName("powder_diamond").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_diamond");
		powder_emerald = new Item().setUnlocalizedName("powder_emerald").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_emerald");
		powder_lapis = new Item().setUnlocalizedName("powder_lapis").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lapis");
		powder_quartz = new Item().setUnlocalizedName("powder_quartz").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_quartz");
		powder_magnetized_tungsten = new Item().setUnlocalizedName("powder_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_magnetized_tungsten");
		powder_chlorophyte = new Item().setUnlocalizedName("powder_chlorophyte").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_chlorophyte");
		powder_red_copper = new Item().setUnlocalizedName("powder_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_red_copper");
		powder_steel = new Item().setUnlocalizedName("powder_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_steel");
		powder_lithium = new ItemHazard().addHydroReactivity().setUnlocalizedName("powder_lithium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lithium");
		powder_zirconium = new Item().setUnlocalizedName("powder_zirconium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_zirconium");
		redstone_depleted = new Item().setUnlocalizedName("redstone_depleted").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":redstone_depleted");
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
		powder_semtex_mix = new Item().setUnlocalizedName("powder_semtex_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_semtex_mix");
		powder_desh_mix = new Item().setUnlocalizedName("powder_desh_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh_mix");
		powder_desh_ready = new Item().setUnlocalizedName("powder_desh_ready").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh_ready");
		powder_nitan_mix = new Item().setUnlocalizedName("powder_nitan_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_nitan_mix");
		powder_spark_mix = new Item().setUnlocalizedName("powder_spark_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_spark_mix");
		powder_desh = new Item().setUnlocalizedName("powder_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh");
		powder_steel_tiny = new Item().setUnlocalizedName("powder_steel_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_steel_tiny");
		powder_lithium_tiny = new ItemHazard().addHydroReactivity().setUnlocalizedName("powder_lithium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lithium_tiny");
		powder_neodymium_tiny = new Item().setUnlocalizedName("powder_neodymium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neodymium_tiny");
		powder_cobalt_tiny = new Item().setUnlocalizedName("powder_cobalt_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cobalt_tiny");
		powder_niobium_tiny = new Item().setUnlocalizedName("powder_niobium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_niobium_tiny");
		powder_cerium_tiny = new Item().setUnlocalizedName("powder_cerium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cerium_tiny");
		powder_lanthanium_tiny = new Item().setUnlocalizedName("powder_lanthanium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lanthanium_tiny");
		powder_actinium_tiny = new Item().setUnlocalizedName("powder_actinium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_actinium_tiny");
		powder_meteorite_tiny = new Item().setUnlocalizedName("powder_meteorite_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_meteorite_tiny");
		powder_yellowcake = new ItemHazard(ItemHazard.u * ItemHazard.ingot).setUnlocalizedName("powder_yellowcake").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_yellowcake");
		powder_magic = new Item().setUnlocalizedName("powder_magic").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_magic");
		powder_cloud = new Item().setUnlocalizedName("powder_cloud").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cloud");
		powder_balefire = new ItemHazard(500F, true).setUnlocalizedName("powder_balefire").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_balefire");

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
		motor_desh = new Item().setUnlocalizedName("motor_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":motor_desh");
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
		wire_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.nugget, false, true).setUnlocalizedName("wire_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_schrabidium");
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
		chlorine_pinwheel = new Item().setUnlocalizedName("chlorine_pinwheel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chlorine_pinwheel");
		ring_starmetal = new Item().setUnlocalizedName("ring_starmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ring_starmetal");
		
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
		warhead_mirvlet = new Item().setUnlocalizedName("warhead_mirvlet").setCreativeTab(null).setTextureName(RefStrings.MODID + ":warhead_mirvlet");
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

		seg_10 = new Item().setUnlocalizedName("seg_10").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":seg_10");
		seg_15 = new Item().setUnlocalizedName("seg_15").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":seg_15");
		seg_20 = new Item().setUnlocalizedName("seg_20").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":seg_20");

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
		primer_357 = new Item().setUnlocalizedName("primer_357").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":primer_357");
		primer_44 = new Item().setUnlocalizedName("primer_44").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":primer_44");
		primer_9 = new Item().setUnlocalizedName("primer_9").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":primer_9");
		primer_50 = new Item().setUnlocalizedName("primer_50").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":primer_50");
		primer_buckshot = new Item().setUnlocalizedName("primer_buckshot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":primer_buckshot");
		casing_357 = new Item().setUnlocalizedName("casing_357").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":casing_357");
		casing_44 = new Item().setUnlocalizedName("casing_44").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":casing_44");
		casing_9 = new Item().setUnlocalizedName("casing_9").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":casing_9");
		casing_50 = new Item().setUnlocalizedName("casing_50").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":casing_50");
		casing_buckshot = new Item().setUnlocalizedName("casing_buckshot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":casing_buckshot");
		assembly_iron = new Item().setUnlocalizedName("assembly_iron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_iron");
		assembly_steel = new Item().setUnlocalizedName("assembly_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_steel");
		assembly_lead = new Item().setUnlocalizedName("assembly_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_lead");
		assembly_gold = new Item().setUnlocalizedName("assembly_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_gold");
		assembly_schrabidium = new Item().setUnlocalizedName("assembly_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_schrabidium");
		assembly_nightmare = new Item().setUnlocalizedName("assembly_nightmare").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_nightmare");
		assembly_desh = new Item().setUnlocalizedName("assembly_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_desh");
		//assembly_pip = new Item().setUnlocalizedName("assembly_pip").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_pip");
		assembly_nopip = new Item().setUnlocalizedName("assembly_nopip").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_nopip");
		assembly_smg = new Item().setUnlocalizedName("assembly_smg").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_smg");
		assembly_556 = new Item().setUnlocalizedName("assembly_556").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_556");
		assembly_uzi = new Item().setUnlocalizedName("assembly_uzi").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_uzi");
		assembly_actionexpress = new Item().setUnlocalizedName("assembly_actionexpress").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_actionexpress");
		assembly_calamity = new Item().setUnlocalizedName("assembly_calamity").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_calamity");
		assembly_lacunae = new Item().setUnlocalizedName("assembly_lacunae").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_lacunae");
		assembly_nuke = new Item().setUnlocalizedName("assembly_nuke").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_nuke");
		folly_shell = new Item().setUnlocalizedName("folly_shell").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":folly_shell");
		folly_bullet = new Item().setUnlocalizedName("folly_bullet").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":folly_bullet");
		folly_bullet_nuclear = new Item().setUnlocalizedName("folly_bullet_nuclear").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":folly_bullet_nuclear");
		folly_bullet_du = new Item().setUnlocalizedName("folly_bullet_du").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":folly_bullet_du");
		
		wiring_red_copper = new ItemWiring().setUnlocalizedName("wiring_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wiring_red_copper");

		pellet_rtg = new ItemRTGPellet(ItemHazard.pu238 * 1.5F, true).setUnlocalizedName("pellet_rtg").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg");
		pellet_rtg_weak = new ItemHazard(ItemHazard.pu238 * 0.5F).setUnlocalizedName("pellet_rtg_weak").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg_weak");
		pellet_rtg_polonium = new ItemHazard(ItemHazard.po210 * 1.5F, true).setUnlocalizedName("pellet_rtg_polonium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg_polonium");
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
		cell_tritium = new ItemHazard(0.5F).setUnlocalizedName("cell_tritium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_tritium");
		cell_sas3 = new ItemHazard(ItemHazard.sa326 * ItemHazard.ingot, false, true).setUnlocalizedName("cell_sas3").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_sas3");
		cell_anti_schrabidium = new ItemDrop().setUnlocalizedName("cell_anti_schrabidium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_anti_schrabidium");

		demon_core_open = new ItemDemonCore().setUnlocalizedName("demon_core_open").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":demon_core_open");
		demon_core_closed = new ItemHazard(100000F).setUnlocalizedName("demon_core_closed").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":demon_core_closed");

		particle_empty = new Item().setUnlocalizedName("particle_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":particle_empty");
		particle_hydrogen = new Item().setUnlocalizedName("particle_hydrogen").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_hydrogen");
		particle_copper = new Item().setUnlocalizedName("particle_copper").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_copper");
		particle_lead = new Item().setUnlocalizedName("particle_lead").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_lead");
		particle_aproton = new Item().setUnlocalizedName("particle_aproton").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_aproton");
		particle_aelectron = new Item().setUnlocalizedName("particle_aelectron").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_aelectron");
		particle_amat = new Item().setUnlocalizedName("particle_amat").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_amat");
		particle_aschrab = new Item().setUnlocalizedName("particle_aschrab").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_aschrab");
		particle_higgs = new Item().setUnlocalizedName("particle_higgs").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_higgs");
		particle_muon = new Item().setUnlocalizedName("particle_muon").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_muon");
		particle_strange = new Item().setUnlocalizedName("particle_strange").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_strange");
		particle_dark = new Item().setUnlocalizedName("particle_dark").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_dark");
		particle_sparkticle = new Item().setUnlocalizedName("particle_sparkticle").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_sparkticle");
		particle_digamma = new ItemDigamma(100F, 60).setUnlocalizedName("particle_digamma").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_digamma");
		
		singularity = new ItemDrop().setUnlocalizedName("singularity").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity");
		singularity_counter_resonant = new ItemDrop().setUnlocalizedName("singularity_counter_resonant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_alt");
		singularity_super_heated = new ItemDrop().setUnlocalizedName("singularity_super_heated").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_5");
		black_hole = new ItemDrop().setUnlocalizedName("black_hole").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_4");
		singularity_spark = new ItemDrop().setUnlocalizedName("singularity_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_spark_alt");
		pellet_antimatter = new ItemDrop().setUnlocalizedName("pellet_antimatter").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":pellet_antimatter");
		crystal_xen = new ItemDrop().setUnlocalizedName("crystal_xen").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":crystal_xen");
		inf_water = new Item().setUnlocalizedName("inf_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":inf_water");

		stamp_stone_flat = new ItemStamp(5).setUnlocalizedName("stamp_stone_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_flat");
		stamp_stone_plate = new ItemStamp(5).setUnlocalizedName("stamp_stone_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_plate");
		stamp_stone_wire = new ItemStamp(5).setUnlocalizedName("stamp_stone_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_wire");
		stamp_stone_circuit = new ItemStamp(5).setUnlocalizedName("stamp_stone_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_circuit");
		stamp_iron_flat = new ItemStamp(25).setUnlocalizedName("stamp_iron_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_flat");
		stamp_iron_plate = new ItemStamp(25).setUnlocalizedName("stamp_iron_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_plate");
		stamp_iron_wire = new ItemStamp(25).setUnlocalizedName("stamp_iron_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_wire");
		stamp_iron_circuit = new ItemStamp(25).setUnlocalizedName("stamp_iron_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_circuit");
		stamp_steel_flat = new ItemStamp(50).setUnlocalizedName("stamp_steel_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_flat");
		stamp_steel_plate = new ItemStamp(50).setUnlocalizedName("stamp_steel_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_plate");
		stamp_steel_wire = new ItemStamp(50).setUnlocalizedName("stamp_steel_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_wire");
		stamp_steel_circuit = new ItemStamp(50).setUnlocalizedName("stamp_steel_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_circuit");
		stamp_titanium_flat = new ItemStamp(65).setUnlocalizedName("stamp_titanium_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_flat");
		stamp_titanium_plate = new ItemStamp(65).setUnlocalizedName("stamp_titanium_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_plate");
		stamp_titanium_wire = new ItemStamp(65).setUnlocalizedName("stamp_titanium_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_wire");
		stamp_titanium_circuit = new ItemStamp(65).setUnlocalizedName("stamp_titanium_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_circuit");
		stamp_obsidian_flat = new ItemStamp(100).setUnlocalizedName("stamp_obsidian_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_flat");
		stamp_obsidian_plate = new ItemStamp(100).setUnlocalizedName("stamp_obsidian_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_plate");
		stamp_obsidian_wire = new ItemStamp(100).setUnlocalizedName("stamp_obsidian_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_wire");
		stamp_obsidian_circuit = new ItemStamp(100).setUnlocalizedName("stamp_obsidian_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_circuit");
		stamp_schrabidium_flat = new ItemStamp(1024).setUnlocalizedName("stamp_schrabidium_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_schrabidium_flat");
		stamp_schrabidium_plate = new ItemStamp(1024).setUnlocalizedName("stamp_schrabidium_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_schrabidium_plate");
		stamp_schrabidium_wire = new ItemStamp(1024).setUnlocalizedName("stamp_schrabidium_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_schrabidium_wire");
		stamp_schrabidium_circuit = new ItemStamp(1024).setUnlocalizedName("stamp_schrabidium_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_schrabidium_circuit");
		stamp_357 = new ItemBlades(512).setUnlocalizedName("stamp_357").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_357");
		stamp_44 = new ItemBlades(512).setUnlocalizedName("stamp_44").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_44");
		stamp_9 = new ItemBlades(512).setUnlocalizedName("stamp_9").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_9");
		stamp_50 = new ItemBlades(512).setUnlocalizedName("stamp_50").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_50");

		blades_aluminium = new ItemBlades(10).setUnlocalizedName("blades_aluminium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_aluminium");
		blades_gold = new ItemBlades(25).setUnlocalizedName("blades_gold").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_gold");
		blades_iron = new ItemBlades(35).setUnlocalizedName("blades_iron").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_iron");
		blades_steel = new ItemBlades(50).setUnlocalizedName("blades_steel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_steel");
		blades_titanium = new ItemBlades(65).setUnlocalizedName("blades_titanium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_titanium");
		blades_advanced_alloy = new ItemBlades(85).setUnlocalizedName("blades_advanced_alloy").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_advanced_alloy");
		blades_combine_steel = new ItemBlades(150).setUnlocalizedName("blades_combine_steel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_combine_steel");
		blades_schrabidium = new ItemBlades(250).setUnlocalizedName("blades_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_schrabidium");
		blades_desh = new ItemBlades(0).setUnlocalizedName("blades_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_desh");

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
		radaway_strong = new ItemSyringe().setUnlocalizedName("radaway_strong").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":radaway_strong");
		radaway_flush = new ItemSyringe().setUnlocalizedName("radaway_flush").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":radaway_flush");
		radx = new ItemPill(0).setUnlocalizedName("radx").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":radx");
		xanax = new ItemPill(0).setUnlocalizedName("xanax").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":xanax_2");
		fmn = new ItemPill(0).setUnlocalizedName("fmn").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":tablet");
		five_htp = new ItemPill(0).setUnlocalizedName("five_htp").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":5htp");
		pill_iodine = new ItemPill(0).setUnlocalizedName("pill_iodine").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pill_iodine");
		plan_c = new ItemPill(0).setUnlocalizedName("plan_c").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":plan_c");
		stealth_boy = new ItemStarterKit().setUnlocalizedName("stealth_boy").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":stealth_boy");
		gas_mask_filter = new ItemSyringe().setUnlocalizedName("gas_mask_filter").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":gas_mask_filter");
		jetpack_tank = new ItemSyringe().setUnlocalizedName("jetpack_tank").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":jetpack_tank");
		gun_kit_1 = new ItemSyringe().setUnlocalizedName("gun_kit_1").setMaxStackSize(16).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":gun_kit_1");
		gun_kit_2 = new ItemSyringe().setUnlocalizedName("gun_kit_2").setMaxStackSize(16).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":gun_kit_2");
		cbt_device = new ItemSyringe().setUnlocalizedName("cbt_device").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":cbt_device");
		
		attachment_mask = new ItemModGasmask().setUnlocalizedName("attachment_mask").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":attachment_mask");
		back_tesla = new ItemModTesla().setUnlocalizedName("back_tesla").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":back_tesla");
		servo_set = new ItemModServos().setUnlocalizedName("servo_set").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":servo_set");
		servo_set_desh = new ItemModServos().setUnlocalizedName("servo_set_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":servo_set_desh");
		pads_rubber = new ItemModPads(0.5F).setUnlocalizedName("pads_rubber").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pads_rubber");
		pads_slime = new ItemModPads(0.25F).setUnlocalizedName("pads_slime").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pads_slime");
		pads_static = new ItemModPads(0.75F).setUnlocalizedName("pads_static").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pads_static");
		cladding_paint = new ItemModCladding(0.025F).setUnlocalizedName("cladding_paint").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cladding_paint");
		cladding_rubber = new ItemModCladding(0.075F).setUnlocalizedName("cladding_rubber").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cladding_rubber");
		cladding_lead = new ItemModCladding(0.2F).setUnlocalizedName("cladding_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cladding_lead");
		cladding_desh = new ItemModCladding(0.4F).setUnlocalizedName("cladding_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cladding_desh");
		cladding_obsidian = new ItemModObsidian().setUnlocalizedName("cladding_obsidian").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cladding_obsidian");
		insert_kevlar = new ItemModInsert(1500, 1F, 0.9F, 1F, 1F).setUnlocalizedName("insert_kevlar").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_kevlar");
		insert_sapi = new ItemModInsert(1750, 1F, 0.85F, 1F, 1F).setUnlocalizedName("insert_sapi").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_sapi");
		insert_esapi = new ItemModInsert(2000, 0.95F, 0.8F, 1F, 1F).setUnlocalizedName("insert_esapi").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_esapi");
		insert_xsapi = new ItemModInsert(2500, 0.9F, 0.75F, 1F, 1F).setUnlocalizedName("insert_xsapi").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_xsapi");
		insert_steel = new ItemModInsert(1000, 1F, 0.95F, 0.75F, 0.95F).setUnlocalizedName("insert_steel").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_steel");
		insert_du = new ItemModInsert(1500, 0.9F, 0.85F, 0.5F, 0.9F).setUnlocalizedName("insert_du").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_du");
		insert_polonium = new ItemModInsert(500, 0.9F, 1F, 0.95F, 0.9F).setUnlocalizedName("insert_polonium").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_polonium");
		insert_era = new ItemModInsert(25, 0.5F, 1F, 0.25F, 1F).setUnlocalizedName("insert_era").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_era");
		insert_yharonite = new ItemModInsert(9999, 0.01F, 1F, 1F, 1F).setUnlocalizedName("insert_yharonite").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_yharonite");
		insert_doxium = new ItemModInsert(9999, 5.0F, 1F, 1F, 1F).setUnlocalizedName("insert_doxium").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":insert_doxium");
		armor_polish = new ItemModPolish().setUnlocalizedName("armor_polish").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":armor_polish");
		bandaid = new ItemModBandaid().setUnlocalizedName("bandaid").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bandaid");
		serum = new ItemModSerum().setUnlocalizedName("serum").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":serum");
		quartz_plutonium = new ItemModQuartz().setUnlocalizedName("quartz_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":quartz_plutonium");
		morning_glory = new ItemModMorningGlory().setUnlocalizedName("morning_glory").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":morning_glory");
		lodestone = new ItemModLodestone(5).setUnlocalizedName("lodestone").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":lodestone");
		horseshoe_magnet = new ItemModLodestone(8).setUnlocalizedName("horseshoe_magnet").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":horseshoe_magnet");
		industrial_magnet = new ItemModLodestone(12).setUnlocalizedName("industrial_magnet").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":industrial_magnet");
		bathwater = new ItemModBathwater().setUnlocalizedName("bathwater").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bathwater");
		bathwater_mk2 = new ItemModBathwater().setUnlocalizedName("bathwater_mk2").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bathwater_mk2");
		spider_milk = new ItemModMilk().setUnlocalizedName("spider_milk").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spider_milk");
		ink = new ItemModInk().setUnlocalizedName("ink").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":ink");
		heart_piece = new ItemModHealth(5F).setUnlocalizedName("heart_piece").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":heart_piece");
		heart_container = new ItemModHealth(20F).setUnlocalizedName("heart_container").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":heart_container");
		heart_booster = new ItemModHealth(40F).setUnlocalizedName("heart_booster").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":heart_booster");
		heart_fab = new ItemModHealth(60F).setUnlocalizedName("heart_fab").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":heart_fab");
		wd40 = new ItemModWD40().setUnlocalizedName("wd40").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":wd40");
		scrumpy = new ItemModRevive(1).setUnlocalizedName("scrumpy").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":scrumpy");
		wild_p = new ItemModRevive(3).setUnlocalizedName("wild_p").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":wild_p");
		fabsols_vodka = new ItemModRevive(9999).setUnlocalizedName("fabsols_vodka").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":fabsols_vodka");

		can_empty = new Item().setUnlocalizedName("can_empty").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_empty");
		can_smart = new ItemEnergy().setUnlocalizedName("can_smart").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_smart");
		can_creature = new ItemEnergy().setUnlocalizedName("can_creature").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_creature");
		can_redbomb = new ItemEnergy().setUnlocalizedName("can_redbomb").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_redbomb");
		can_mrsugar = new ItemEnergy().setUnlocalizedName("can_mrsugar").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_mrsugar");
		can_overcharge = new ItemEnergy().setUnlocalizedName("can_overcharge").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_overcharge");
		can_luna = new ItemEnergy().setUnlocalizedName("can_luna").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_luna");
		can_bepis = new ItemEnergy().setUnlocalizedName("can_bepis").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_bepis");;
		can_breen = new ItemEnergy().setUnlocalizedName("can_breen").setContainerItem(ModItems.can_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_breen");
		bottle_empty = new Item().setUnlocalizedName("bottle_empty").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_empty");
		bottle_nuka = new ItemEnergy().setUnlocalizedName("bottle_nuka").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_nuka");
		bottle_cherry = new ItemEnergy().setUnlocalizedName("bottle_cherry").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_cherry");
		bottle_quantum = new ItemEnergy().setUnlocalizedName("bottle_quantum").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_quantum");
		bottle_sparkle = new ItemEnergy().setUnlocalizedName("bottle_sparkle").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_sparkle");
		bottle_rad = new ItemEnergy().setUnlocalizedName("bottle_rad").setContainerItem(ModItems.bottle_empty).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bottle_rad");
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
		cap_rad = new Item().setUnlocalizedName("cap_rad").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_rad");
		cap_korl = new Item().setUnlocalizedName("cap_korl").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_korl");
		cap_fritz = new Item().setUnlocalizedName("cap_fritz").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_fritz");
		cap_sunset = new Item().setUnlocalizedName("cap_sunset").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_sunset");
		cap_star = new Item().setUnlocalizedName("cap_star").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_star");
		ring_pull = new Item().setUnlocalizedName("ring_pull").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":ring_pull");

		canned_beef = new ItemLemon(8, 5, false).setUnlocalizedName("canned_beef").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_beef");
		canned_tuna = new ItemLemon(4, 5, false).setUnlocalizedName("canned_tuna").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_tuna");
		canned_mystery = new ItemLemon(6, 5, false).setUnlocalizedName("canned_mystery").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_mystery");
		canned_pashtet = new ItemLemon(4, 5, false).setUnlocalizedName("canned_pashtet").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_pashtet");
		canned_cheese = new ItemLemon(3, 5, false).setUnlocalizedName("canned_cheese").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_cheese");
		canned_jizz = new ItemLemon(15, 5, false).setUnlocalizedName("canned_jizz").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_jizz");
		canned_milk = new ItemLemon(5, 5, false).setUnlocalizedName("canned_milk").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_milk");
		canned_ass = new ItemLemon(6, 5, false).setUnlocalizedName("canned_ass").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_ass");
		canned_pizza = new ItemLemon(8, 5, false).setUnlocalizedName("canned_pizza").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_pizza");
		canned_tube = new ItemLemon(2, 5, false).setUnlocalizedName("canned_tube").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_tube");
		canned_tomato = new ItemLemon(4, 5, false).setUnlocalizedName("canned_tomato").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_tomato");
		canned_asbestos = new ItemLemon(7, 5, false).setUnlocalizedName("canned_asbestos").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_asbestos");
		canned_bhole = new ItemLemon(10, 5, false).setUnlocalizedName("canned_bhole").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_bhole");
		canned_hotdogs = new ItemLemon(5, 5, false).setUnlocalizedName("canned_hotdogs").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_hotdogs");
		canned_leftovers = new ItemLemon(1, 5, false).setUnlocalizedName("canned_leftovers").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_leftovers");
		canned_yogurt = new ItemLemon(3, 5, false).setUnlocalizedName("canned_yogurt").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_yogurt");
		canned_stew = new ItemLemon(5, 5, false).setUnlocalizedName("canned_stew").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_stew");
		canned_chinese = new ItemLemon(6, 5, false).setUnlocalizedName("canned_chinese").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_chinese");
		canned_oil = new ItemLemon(3, 5, false).setUnlocalizedName("canned_oil").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_oil");
		canned_fist = new ItemLemon(6, 5, false).setUnlocalizedName("canned_fist").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_fist");
		canned_spam = new ItemLemon(8, 5, false).setUnlocalizedName("canned_spam").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_spam");
		canned_fried = new ItemLemon(10, 5, false).setUnlocalizedName("canned_fried").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_fried");
		canned_napalm = new ItemLemon(6, 5, false).setUnlocalizedName("canned_napalm").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_napalm");
		canned_diesel = new ItemLemon(6, 5, false).setUnlocalizedName("canned_diesel").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_diesel");
		canned_kerosene = new ItemLemon(6, 5, false).setUnlocalizedName("canned_kerosene").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_kerosene");
		canned_recursion = new ItemLemon(1, 5, false).setUnlocalizedName("canned_recursion").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_recursion");
		canned_bark = new ItemLemon(2, 5, false).setUnlocalizedName("canned_bark").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned_bark");
		can_key = new Item().setUnlocalizedName("can_key").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_key");
		
		coin_creeper = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_creeper").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_creeper");
		coin_radiation = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_radiation").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_radiation");
		coin_maskman = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_maskman").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_maskman");
		coin_worm = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_worm").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_worm");

		recycled_ground = new Item().setUnlocalizedName("recycled_ground").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_ground");
		recycled_rock = new Item().setUnlocalizedName("recycled_rock").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_rock");
		recycled_metal = new Item().setUnlocalizedName("recycled_metal").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_metal");
		recycled_refined = new Item().setUnlocalizedName("recycled_refined").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_refined");
		recycled_organic = new Item().setUnlocalizedName("recycled_organic").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_organic");
		recycled_crystal = new Item().setUnlocalizedName("recycled_crystal").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_crystal");
		recycled_explosive = new Item().setUnlocalizedName("recycled_explosive").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_explosive");
		recycled_electronic = new Item().setUnlocalizedName("recycled_electronic").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_electronic");
		recycled_nuclear = new Item().setUnlocalizedName("recycled_nuclear").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_nuclear");
		recycled_misc = new Item().setUnlocalizedName("recycled_misc").setCreativeTab(null).setTextureName(RefStrings.MODID + ":recycled_misc");

		rod_empty = new Item().setUnlocalizedName("rod_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_empty");
		rod_th232 = new ItemHazard(ItemHazard.th232 * ItemHazard.rod).setUnlocalizedName("rod_th232").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_th232");
		rod_uranium = new ItemHazard(ItemHazard.u * ItemHazard.rod).setUnlocalizedName("rod_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium");
		rod_u233 = new ItemHazard(ItemHazard.u233 * ItemHazard.rod).setUnlocalizedName("rod_u233").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_u233");
		rod_u235 = new ItemHazard(ItemHazard.u235 * ItemHazard.rod).setUnlocalizedName("rod_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_u235");
		rod_u238 = new ItemHazard(ItemHazard.u238 * ItemHazard.rod).setUnlocalizedName("rod_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_u238");
		rod_plutonium = new ItemHazard(ItemHazard.pu * ItemHazard.rod).setUnlocalizedName("rod_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium");
		rod_pu238 = new ItemHazard(ItemHazard.pu238 * ItemHazard.rod, true).setUnlocalizedName("rod_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_pu238");
		rod_pu239 = new ItemHazard(ItemHazard.pu239 * ItemHazard.rod).setUnlocalizedName("rod_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_pu239");
		rod_pu240 = new ItemHazard(ItemHazard.pu240 * ItemHazard.rod).setUnlocalizedName("rod_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_pu240");
		rod_neptunium = new ItemHazard(ItemHazard.np237 * ItemHazard.rod).setUnlocalizedName("rod_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_neptunium");
		rod_polonium = new ItemHazard(ItemHazard.po210 * ItemHazard.rod, true).setUnlocalizedName("rod_polonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_polonium");
		rod_lead = new Item().setUnlocalizedName("rod_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_lead");
		rod_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.rod, false, true).setUnlocalizedName("rod_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium");
		rod_solinium = new ItemHazard(ItemHazard.sa327 * ItemHazard.rod, false, true).setUnlocalizedName("rod_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_solinium");
		rod_euphemium = new ItemCustomLore().setUnlocalizedName("rod_euphemium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_euphemium");
		rod_australium = new ItemCustomLore().setUnlocalizedName("rod_australium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_australium");
		rod_weidanium = new ItemCustomLore().setUnlocalizedName("rod_weidanium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_weidanium");
		rod_reiium = new ItemCustomLore().setUnlocalizedName("rod_reiium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_reiium");
		rod_unobtainium = new ItemCustomLore().setUnlocalizedName("rod_unobtainium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_unobtainium");
		rod_daffergon = new ItemCustomLore().setUnlocalizedName("rod_daffergon").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_daffergon");
		rod_verticium = new ItemCustomLore().setUnlocalizedName("rod_verticium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_verticium");
		rod_balefire = new ItemHazard(1500F).setUnlocalizedName("rod_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_balefire");
		rod_balefire_blazing = new ItemHazard(2500F, true).setUnlocalizedName("rod_balefire_blazing").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_balefire_blazing");

		rod_dual_empty = new Item().setUnlocalizedName("rod_dual_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_dual_empty");
		rod_dual_th232 = new ItemHazard(ItemHazard.th232 * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_th232").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_th232");
		rod_dual_uranium = new ItemHazard(ItemHazard.u * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium");
		rod_dual_u233 = new ItemHazard(ItemHazard.u233 * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_u233").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_u233");
		rod_dual_u235 = new ItemHazard(ItemHazard.u235 * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_u235");
		rod_dual_u238 = new ItemHazard(ItemHazard.u238 * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_u238");
		rod_dual_plutonium = new ItemHazard(ItemHazard.pu * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium");
		rod_dual_pu238 = new ItemHazard(ItemHazard.pu238 * ItemHazard.rod_dual, true).setUnlocalizedName("rod_dual_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_pu238");
		rod_dual_pu239 = new ItemHazard(ItemHazard.pu239 * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_pu239");
		rod_dual_pu240 = new ItemHazard(ItemHazard.pu240 * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_pu240");
		rod_dual_neptunium = new ItemHazard(ItemHazard.np237 * ItemHazard.rod_dual).setUnlocalizedName("rod_dual_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_neptunium");
		rod_dual_polonium = new ItemHazard(ItemHazard.po210 * ItemHazard.rod_dual, true).setUnlocalizedName("rod_dual_polonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_polonium");
		rod_dual_lead = new Item().setUnlocalizedName("rod_dual_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_lead");
		rod_dual_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.rod_dual, false, true).setUnlocalizedName("rod_dual_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium");
		rod_dual_solinium = new ItemHazard(ItemHazard.sa327 * ItemHazard.rod_dual, false, true).setUnlocalizedName("rod_dual_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_solinium");
		rod_dual_balefire = new ItemHazard(3000F).setUnlocalizedName("rod_dual_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_balefire");
		rod_dual_balefire_blazing = new ItemHazard(5000F, true).setUnlocalizedName("rod_dual_balefire_blazing").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_balefire_blazing");

		rod_quad_empty = new Item().setUnlocalizedName("rod_quad_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_quad_empty");
		rod_quad_th232 = new ItemHazard(ItemHazard.th232 * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_th232").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_th232");
		rod_quad_uranium = new ItemHazard(ItemHazard.u * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium");
		rod_quad_u233 = new ItemHazard(ItemHazard.u233 * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_u233").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_u233");
		rod_quad_u235 = new ItemHazard(ItemHazard.u235 * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_u235");
		rod_quad_u238 = new ItemHazard(ItemHazard.u238 * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_u238");
		rod_quad_plutonium = new ItemHazard(ItemHazard.pu * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium");
		rod_quad_pu238 = new ItemHazard(ItemHazard.pu238 * ItemHazard.rod_quad, true).setUnlocalizedName("rod_quad_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_pu238");
		rod_quad_pu239 = new ItemHazard(ItemHazard.pu239 * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_pu239");
		rod_quad_pu240 = new ItemHazard(ItemHazard.pu240 * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_pu240");
		rod_quad_neptunium = new ItemHazard(ItemHazard.np237 * ItemHazard.rod_quad).setUnlocalizedName("rod_quad_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_neptunium");
		rod_quad_polonium = new ItemHazard(ItemHazard.po210 * ItemHazard.rod_quad, true).setUnlocalizedName("rod_quad_polonium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_polonium");
		rod_quad_lead = new Item().setUnlocalizedName("rod_quad_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_lead");
		rod_quad_schrabidium = new ItemHazard(ItemHazard.sa326 * ItemHazard.rod_quad, false, true).setUnlocalizedName("rod_quad_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium");
		rod_quad_solinium = new ItemHazard(ItemHazard.sa327 * ItemHazard.rod_quad, false, true).setUnlocalizedName("rod_quad_solinium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_solinium");
		rod_quad_balefire = new ItemHazard(6000F).setUnlocalizedName("rod_quad_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_balefire");
		rod_quad_balefire_blazing = new ItemHazard(10000F, true).setUnlocalizedName("rod_quad_balefire_blazing").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_balefire_blazing");

		rod_thorium_fuel = new ItemFuelRod(ItemHazard.thf * ItemHazard.rod, false, 100000, 10).setUnlocalizedName("rod_thorium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_thorium_fuel");
		rod_dual_thorium_fuel = new ItemFuelRod(ItemHazard.thf * ItemHazard.rod_dual, false, 100000, 20).setUnlocalizedName("rod_dual_thorium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_thorium_fuel");
		rod_quad_thorium_fuel = new ItemFuelRod(ItemHazard.thf * ItemHazard.rod_quad, false, 100000, 40).setUnlocalizedName("rod_quad_thorium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_thorium_fuel");
		rod_uranium_fuel = new ItemFuelRod(ItemHazard.uf * ItemHazard.rod, false, 100000, 15).setUnlocalizedName("rod_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium_fuel");
		rod_dual_uranium_fuel = new ItemFuelRod(ItemHazard.uf * ItemHazard.rod_dual, false, 100000, 30).setUnlocalizedName("rod_dual_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium_fuel");
		rod_quad_uranium_fuel = new ItemFuelRod(ItemHazard.uf * ItemHazard.rod_quad, false, 100000, 60).setUnlocalizedName("rod_quad_uranium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium_fuel");
		rod_plutonium_fuel = new ItemFuelRod(ItemHazard.puf * ItemHazard.rod, false, 75000, 25).setUnlocalizedName("rod_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium_fuel");
		rod_dual_plutonium_fuel = new ItemFuelRod(ItemHazard.puf * ItemHazard.rod_dual, false, 75000, 50).setUnlocalizedName("rod_dual_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium_fuel");
		rod_quad_plutonium_fuel = new ItemFuelRod(ItemHazard.puf * ItemHazard.rod_quad, false, 75000, 100).setUnlocalizedName("rod_quad_plutonium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium_fuel");
		rod_mox_fuel = new ItemFuelRod(ItemHazard.mox * ItemHazard.rod, false, 150000, 10).setUnlocalizedName("rod_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_mox_fuel");
		rod_dual_mox_fuel = new ItemFuelRod(ItemHazard.mox * ItemHazard.rod_dual, false, 150000, 20).setUnlocalizedName("rod_dual_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_mox_fuel");
		rod_quad_mox_fuel = new ItemFuelRod(ItemHazard.mox * ItemHazard.rod_quad, false, 150000, 40).setUnlocalizedName("rod_quad_mox_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_mox_fuel");
		rod_schrabidium_fuel = new ItemFuelRod(ItemHazard.saf * ItemHazard.rod, true, 500000, 250).setUnlocalizedName("rod_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium_fuel");
		rod_dual_schrabidium_fuel = new ItemFuelRod(ItemHazard.saf * ItemHazard.rod_dual, true, 500000, 500).setUnlocalizedName("rod_dual_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium_fuel");
		rod_quad_schrabidium_fuel = new ItemFuelRod(ItemHazard.saf * ItemHazard.rod_quad, true, 500000, 1000).setUnlocalizedName("rod_quad_schrabidium_fuel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium_fuel");

		rod_water = new ItemCustomLore().setUnlocalizedName("rod_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_water");
		rod_dual_water = new ItemCustomLore().setUnlocalizedName("rod_dual_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_water");
		rod_quad_water = new ItemCustomLore().setUnlocalizedName("rod_quad_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_water");

		rod_coolant = new ItemCustomLore().setUnlocalizedName("rod_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_coolant");
		rod_dual_coolant = new ItemCustomLore().setUnlocalizedName("rod_dual_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_coolant");
		rod_quad_coolant = new ItemCustomLore().setUnlocalizedName("rod_quad_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_coolant");

		rod_lithium = new ItemHazard().addHydroReactivity().setUnlocalizedName("rod_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_lithium");
		rod_dual_lithium = new ItemHazard().addHydroReactivity().setUnlocalizedName("rod_dual_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_lithium");
		rod_quad_lithium = new ItemHazard().addHydroReactivity().setUnlocalizedName("rod_quad_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_lithium");

		rod_tritium = new ItemHazard(0.5F).setUnlocalizedName("rod_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_tritium");
		rod_dual_tritium = new ItemHazard(1F).setUnlocalizedName("rod_dual_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_tritium");
		rod_quad_tritium = new ItemHazard(2F).setUnlocalizedName("rod_quad_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_tritium");

		trinitite = new ItemHazard(5F).setUnlocalizedName("trinitite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":trinitite_new");
		nuclear_waste_long = new ItemHazard(5F).setUnlocalizedName("nuclear_waste_long").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long");
		nuclear_waste_long_tiny = new ItemHazard(0.5F).setUnlocalizedName("nuclear_waste_long_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long_tiny");
		nuclear_waste_short = new ItemHazard(30F, true).setUnlocalizedName("nuclear_waste_short").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short");
		nuclear_waste_short_tiny = new ItemHazard(3F, true).setUnlocalizedName("nuclear_waste_short_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short_tiny");
		nuclear_waste_long_depleted = new ItemHazard(0.5F).setUnlocalizedName("nuclear_waste_long_depleted").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long_depleted");
		nuclear_waste_long_depleted_tiny = new ItemHazard(0.05F).setUnlocalizedName("nuclear_waste_long_depleted_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long_depleted_tiny");
		nuclear_waste_short_depleted = new ItemHazard(3F).setUnlocalizedName("nuclear_waste_short_depleted").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short_depleted");
		nuclear_waste_short_depleted_tiny = new ItemHazard(0.3F).setUnlocalizedName("nuclear_waste_short_depleted_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short_depleted_tiny");
		nuclear_waste = new ItemHazard(15F).setUnlocalizedName("nuclear_waste").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste");
		nuclear_waste_tiny = new ItemHazard(2F).setUnlocalizedName("nuclear_waste_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_tiny");
		waste_uranium = new ItemHazard(15F).setUnlocalizedName("waste_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		waste_thorium = new ItemHazard(10F).setUnlocalizedName("waste_thorium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_thorium");
		waste_plutonium = new ItemHazard(15F).setUnlocalizedName("waste_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plutonium");
		waste_mox = new ItemHazard(15F).setUnlocalizedName("waste_mox").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_mox");
		waste_schrabidium = new ItemHazard(40F, false, true).setUnlocalizedName("waste_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_schrabidium");
		waste_uranium_hot = new ItemHazard(15F, true).setUnlocalizedName("waste_uranium_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium_hot");
		waste_thorium_hot = new ItemHazard(10F, true).setUnlocalizedName("waste_thorium_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_thorium_hot");
		waste_plutonium_hot = new ItemHazard(15F, true).setUnlocalizedName("waste_plutonium_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plutonium_hot");
		waste_mox_hot = new ItemHazard(15F, true).setUnlocalizedName("waste_mox_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_mox_hot");
		waste_schrabidium_hot = new ItemHazard(40F, true, true).setUnlocalizedName("waste_schrabidium_hot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_schrabidium_hot");
		scrap = new Item().setUnlocalizedName("scrap").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scrap");
		containment_box = new ItemLeadBox().setUnlocalizedName("containment_box").setCreativeTab(null).setTextureName(RefStrings.MODID + ":containment_box");
		rod_thorium_fuel_depleted = new ItemHazard(10F, true).setUnlocalizedName("rod_thorium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_thorium_fuel_depleted");
		rod_dual_thorium_fuel_depleted = new ItemHazard(20F, true).setUnlocalizedName("rod_dual_thorium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_thorium_fuel_depleted");
		rod_quad_thorium_fuel_depleted = new ItemHazard(40F, true).setUnlocalizedName("rod_quad_thorium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_thorium_fuel_depleted");
		rod_uranium_fuel_depleted = new ItemHazard(15F, true).setUnlocalizedName("rod_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium_fuel_depleted");
		rod_dual_uranium_fuel_depleted = new ItemHazard(30F, true).setUnlocalizedName("rod_dual_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium_fuel_depleted");
		rod_quad_uranium_fuel_depleted = new ItemHazard(60F, true).setUnlocalizedName("rod_quad_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium_fuel_depleted");
		rod_plutonium_fuel_depleted = new ItemHazard(15F, true).setUnlocalizedName("rod_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium_fuel_depleted");
		rod_dual_plutonium_fuel_depleted = new ItemHazard(30F, true).setUnlocalizedName("rod_dual_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium_fuel_depleted");
		rod_quad_plutonium_fuel_depleted = new ItemHazard(60F, true).setUnlocalizedName("rod_quad_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium_fuel_depleted");
		rod_mox_fuel_depleted = new ItemHazard(15F, true).setUnlocalizedName("rod_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_mox_fuel_depleted");
		rod_dual_mox_fuel_depleted = new ItemHazard(30F, true).setUnlocalizedName("rod_dual_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_mox_fuel_depleted");
		rod_quad_mox_fuel_depleted = new ItemHazard(60F, true).setUnlocalizedName("rod_quad_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_mox_fuel_depleted");
		rod_schrabidium_fuel_depleted = new ItemHazard(40F, true).setUnlocalizedName("rod_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium_fuel_depleted");
		rod_dual_schrabidium_fuel_depleted = new ItemHazard(80F, true).setUnlocalizedName("rod_dual_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium_fuel_depleted");
		rod_quad_schrabidium_fuel_depleted = new ItemHazard(160F, true).setUnlocalizedName("rod_quad_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium_fuel_depleted");
		rod_waste = new ItemHazard(15F).setUnlocalizedName("rod_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_waste");
		rod_dual_waste = new ItemHazard(30F).setUnlocalizedName("rod_dual_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_waste");
		rod_quad_waste = new ItemHazard(60F).setUnlocalizedName("rod_quad_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_waste");

		pellet_cluster = new ItemCustomLore().setUnlocalizedName("pellet_cluster").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_cluster");
		powder_fire = new ItemCustomLore().setUnlocalizedName("powder_fire").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_red_phosphorus");
		powder_ice = new ItemCustomLore().setUnlocalizedName("powder_ice").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_ice");
		powder_poison = new ItemCustomLore().setUnlocalizedName("powder_poison").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_poison");
		powder_thermite = new ItemCustomLore().setUnlocalizedName("powder_thermite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_thermite");
		cordite = new Item().setUnlocalizedName("cordite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":cordite");
		ballistite = new Item().setUnlocalizedName("ballistite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ballistite");
		pellet_gas = new ItemCustomLore().setUnlocalizedName("pellet_gas").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_gas");
		magnetron = new ItemCustomLore().setUnlocalizedName("magnetron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":magnetron_alt");
		pellet_buckshot = new Item().setUnlocalizedName("pellet_buckshot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_lead");
		pellet_flechette = new Item().setUnlocalizedName("pellet_flechette").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_flechette");
		pellet_chlorophyte = new Item().setUnlocalizedName("pellet_chlorophyte").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_chlorophyte");
		pellet_mercury = new Item().setUnlocalizedName("pellet_mercury").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_mercury");
		pellet_meteorite = new Item().setUnlocalizedName("pellet_meteorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_meteorite");
		pellet_canister = new Item().setUnlocalizedName("pellet_canister").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_canister");
		pellet_claws = new Item().setUnlocalizedName("pellet_claws").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_claws");
		pellet_charged = new Item().setUnlocalizedName("pellet_charged").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_charged");

		pellet_schrabidium = new WatzFuel(25F, true, 50000, 140000, 0.975F, 200, 1.05F, 1.05F).setUnlocalizedName("pellet_schrabidium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_schrabidium").setMaxStackSize(1);
		pellet_hes = new WatzFuel(20F, true, 108000, 65000, 1F, 85, 1, 1.025F).setUnlocalizedName("pellet_hes").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_hes").setMaxStackSize(1);
		pellet_mes = new WatzFuel(15F, true, 216000, 23000, 1.025F, 50, 1, 1F).setUnlocalizedName("pellet_mes").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_mes").setMaxStackSize(1);
		pellet_les = new WatzFuel(10F, true, 432000, 7000, 1.05F, 15, 1, 0.975F).setUnlocalizedName("pellet_les").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_les").setMaxStackSize(1);
		pellet_beryllium = new WatzFuel(0F, false, 864000, 50, 1.05F, 0, 0.95F, 1.025F).setUnlocalizedName("pellet_beryllium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_beryllium").setMaxStackSize(1);
		pellet_neptunium = new WatzFuel(10F, false, 216000, 3000, 1.1F, 25, 1.1F, 1.005F).setUnlocalizedName("pellet_neptunium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_neptunium").setMaxStackSize(1);
		pellet_lead = new WatzFuel(0F, false, 1728000, 0, 0.95F, 0, 0.95F, 0.95F).setUnlocalizedName("pellet_lead").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_lead").setMaxStackSize(1);
		pellet_advanced = new WatzFuel(0F, false, 216000, 1000, 1.1F, 0, 0.995F, 0.99F).setUnlocalizedName("pellet_advanced").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_advanced").setMaxStackSize(1);

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
		missile_emp_strong = new Item().setUnlocalizedName("missile_emp_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_emp_strong");
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
		missile_soyuz = new ItemSoyuz().setUnlocalizedName("missile_soyuz").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":soyuz");
		missile_soyuz_lander = new ItemCustomLore().setUnlocalizedName("missile_soyuz_lander").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":soyuz_lander");
		missile_custom = new ItemCustomMissile().setUnlocalizedName("missile_custom").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":missile_custom");
		sat_mapper = new ItemSatChip().setUnlocalizedName("sat_mapper").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_mapper");
		sat_scanner = new ItemSatChip().setUnlocalizedName("sat_scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_scanner");
		sat_radar = new ItemSatChip().setUnlocalizedName("sat_radar").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_radar");
		sat_laser = new ItemSatChip().setUnlocalizedName("sat_laser").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_laser");
		sat_foeq = new ItemSatChip().setUnlocalizedName("sat_foeq").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_foeq");
		sat_resonator = new ItemSatChip().setUnlocalizedName("sat_resonator").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_resonator");
		sat_miner = new ItemSatChip().setUnlocalizedName("sat_miner").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_miner");
		sat_gerald = new ItemSatChip().setUnlocalizedName("sat_gerald").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_gerald");
		sat_chip = new ItemSatChip().setUnlocalizedName("sat_chip").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_chip");
		sat_interface = new ItemSatInterface().setUnlocalizedName("sat_interface").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_interface");
		sat_coord = new ItemSatInterface().setUnlocalizedName("sat_coord").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_coord");

		mp_thruster_10_kerosene = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 1.5F, PartSize.SIZE_10).setHealth(10F)					.setUnlocalizedName("mp_thruster_10_kerosene");
		mp_thruster_10_kerosene_tec = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 1.5F, PartSize.SIZE_10).setHealth(15F).setRarity(Rarity.COMMON).setUnlocalizedName("mp_thruster_10_kerosene_tec");
		mp_thruster_10_solid = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 1.5F, PartSize.SIZE_10).setHealth(15F)						.setUnlocalizedName("mp_thruster_10_solid");
		mp_thruster_10_xenon = new ItemMissile().makeThruster(FuelType.XENON, 1F, 1.5F, PartSize.SIZE_10).setHealth(5F)							.setUnlocalizedName("mp_thruster_10_xenon");
		mp_thruster_15_kerosene = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 7.5F, PartSize.SIZE_15).setHealth(15F)					.setUnlocalizedName("mp_thruster_15_kerosene");
		mp_thruster_15_kerosene_tec = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 7.5F, PartSize.SIZE_15).setHealth(20F).setRarity(Rarity.COMMON).setUnlocalizedName("mp_thruster_15_kerosene_tec");
		mp_thruster_15_kerosene_dual = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 2.5F, PartSize.SIZE_15).setHealth(15F)				.setUnlocalizedName("mp_thruster_15_kerosene_dual");
		mp_thruster_15_kerosene_triple = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 5F, PartSize.SIZE_15).setHealth(15F)				.setUnlocalizedName("mp_thruster_15_kerosene_triple");
		mp_thruster_15_solid = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15).setHealth(20F)							.setUnlocalizedName("mp_thruster_15_solid");
		mp_thruster_15_solid_hexdecuple = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15).setHealth(25F).setRarity(Rarity.UNCOMMON).setUnlocalizedName("mp_thruster_15_solid_hexdecuple");
		mp_thruster_15_hydrogen = new ItemMissile().makeThruster(FuelType.HYDROGEN, 1F, 7.5F, PartSize.SIZE_15).setHealth(20F)					.setUnlocalizedName("mp_thruster_15_hydrogen");
		mp_thruster_15_hydrogen_dual = new ItemMissile().makeThruster(FuelType.HYDROGEN, 1F, 2.5F, PartSize.SIZE_15).setHealth(15F)				.setUnlocalizedName("mp_thruster_15_hydrogen_dual");
		mp_thruster_15_balefire_short = new ItemMissile().makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15).setHealth(25F)				.setUnlocalizedName("mp_thruster_15_balefire_short");
		mp_thruster_15_balefire = new ItemMissile().makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15).setHealth(25F)					.setUnlocalizedName("mp_thruster_15_balefire");
		mp_thruster_15_balefire_large = new ItemMissile().makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setHealth(35F)			.setUnlocalizedName("mp_thruster_15_balefire_large");
		mp_thruster_15_balefire_large_rad = new ItemMissile().makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setAuthor("The Master").setHealth(35F).setRarity(Rarity.UNCOMMON).setUnlocalizedName("mp_thruster_15_balefire_large_rad");
		mp_thruster_20_kerosene = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F)					.setUnlocalizedName("mp_thruster_20_kerosene");
		mp_thruster_20_kerosene_dual = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F)				.setUnlocalizedName("mp_thruster_20_kerosene_dual");
		mp_thruster_20_kerosene_triple = new ItemMissile().makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F)			.setUnlocalizedName("mp_thruster_20_kerosene_triple");
		mp_thruster_20_solid = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F).setWittyText("It's basically just a big hole at the end of the fuel tank.").setUnlocalizedName("mp_thruster_20_solid");
		mp_thruster_20_solid_multi = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F)					.setUnlocalizedName("mp_thruster_20_solid_multi");
		mp_thruster_20_solid_multier = new ItemMissile().makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F).setWittyText("Did I miscount? Hope not.").setUnlocalizedName("mp_thruster_20_solid_multier");
		
		mp_stability_10_flat = new ItemMissile().makeStability(0.5F, PartSize.SIZE_10).setHealth(10F)											.setUnlocalizedName("mp_stability_10_flat");
		mp_stability_10_cruise = new ItemMissile().makeStability(0.25F, PartSize.SIZE_10).setHealth(5F)											.setUnlocalizedName("mp_stability_10_cruise");
		mp_stability_10_space = new ItemMissile().makeStability(0.35F, PartSize.SIZE_10).setHealth(5F).setRarity(Rarity.COMMON).setWittyText("Standing there alone, the ship is waiting / All systems are go, are you sure?")					.setUnlocalizedName("mp_stability_10_space");
		mp_stability_15_flat = new ItemMissile().makeStability(0.5F, PartSize.SIZE_15).setHealth(10F)											.setUnlocalizedName("mp_stability_15_flat");
		mp_stability_15_thin = new ItemMissile().makeStability(0.35F, PartSize.SIZE_15).setHealth(5F)											.setUnlocalizedName("mp_stability_15_thin");
		mp_stability_15_soyuz = new ItemMissile().makeStability(0.25F, PartSize.SIZE_15).setHealth(15F).setRarity(Rarity.COMMON).setWittyText("Союз!").setUnlocalizedName("mp_stability_15_soyuz");
		mp_stability_20_flat = new ItemMissile().makeStability(0.5F, PartSize.SIZE_20)															.setUnlocalizedName("mp_s_20");
		
		mp_fuselage_10_kerosene = new ItemMissile().makeFuselage(FuelType.KEROSENE, 2500F, PartSize.SIZE_10, PartSize.SIZE_10).setAuthor("Hoboy").setHealth(20F).setUnlocalizedName("mp_fuselage_10_kerosene");
		mp_fuselage_10_kerosene_camo =			((ItemMissile) mp_fuselage_10_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_10_kerosene_camo");
		mp_fuselage_10_kerosene_desert = 		((ItemMissile) mp_fuselage_10_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Desert Camo").setUnlocalizedName("mp_fuselage_10_kerosene_desert");
		mp_fuselage_10_kerosene_sky = 			((ItemMissile) mp_fuselage_10_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_10_kerosene_sky");
		mp_fuselage_10_kerosene_flames = 		((ItemMissile) mp_fuselage_10_kerosene).copy().setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_kerosene_flames");
		mp_fuselage_10_kerosene_insulation = 	((ItemMissile) mp_fuselage_10_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(25F).setUnlocalizedName("mp_fuselage_10_kerosene_insulation");
		mp_fuselage_10_kerosene_sleek = 		((ItemMissile) mp_fuselage_10_kerosene).copy().setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(35F).setUnlocalizedName("mp_fuselage_10_kerosene_sleek");
		mp_fuselage_10_kerosene_metal = 		((ItemMissile) mp_fuselage_10_kerosene).copy().setRarity(Rarity.UNCOMMON).setTitle("Bolted Metal").setHealth(30F).setAuthor("Hoboy").setUnlocalizedName("mp_fuselage_10_kerosene_metal");
		mp_fuselage_10_kerosene_taint =			((ItemMissile) mp_fuselage_10_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted").setUnlocalizedName("mp_fuselage_10_kerosene_taint");
		
		mp_fuselage_10_solid = new ItemMissile().makeFuselage(FuelType.SOLID, 2500F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(25F)			.setUnlocalizedName("mp_fuselage_10_solid");
		mp_fuselage_10_solid_flames = 		((ItemMissile) mp_fuselage_10_solid).copy().setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_solid_flames");
		mp_fuselage_10_solid_insulation = 	((ItemMissile) mp_fuselage_10_solid).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(30F).setUnlocalizedName("mp_fuselage_10_solid_insulation");
		mp_fuselage_10_solid_sleek = 		((ItemMissile) mp_fuselage_10_solid).copy().setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(35F).setUnlocalizedName("mp_fuselage_10_solid_sleek");
		mp_fuselage_10_solid_soviet_glory = ((ItemMissile) mp_fuselage_10_solid).copy().setRarity(Rarity.EPIC).setAuthor("Hoboy").setHealth(35F).setTitle("Soviet Glory").setUnlocalizedName("mp_fuselage_10_solid_soviet_glory");
		mp_fuselage_10_solid_cathedral = 	((ItemMissile) mp_fuselage_10_solid).copy().setRarity(Rarity.RARE).setAuthor("Satan").setTitle("Unholy Cathedral").setWittyText("Quakeesque!").setUnlocalizedName("mp_fuselage_10_solid_cathedral");
		mp_fuselage_10_solid_moonlit = 		((ItemMissile) mp_fuselage_10_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("The Master & Hoboy").setTitle("Moonlit").setUnlocalizedName("mp_fuselage_10_solid_moonlit");
		mp_fuselage_10_solid_battery = 		((ItemMissile) mp_fuselage_10_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("wolfmonster222").setHealth(30F).setTitle("Ecstatic").setWittyText("I got caught eating batteries again :(").setUnlocalizedName("mp_fuselage_10_solid_battery");
		mp_fuselage_10_solid_duracell = 	((ItemMissile) mp_fuselage_10_solid).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Duracell").setHealth(30F).setWittyText("The crunchiest battery on the market!").setUnlocalizedName("mp_fuselage_10_solid_duracell");
		
		mp_fuselage_10_xenon = new ItemMissile().makeFuselage(FuelType.XENON, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(20F)			.setUnlocalizedName("mp_fuselage_10_xenon");
		mp_fuselage_10_xenon_bhole = 	((ItemMissile) mp_fuselage_10_xenon).copy().setRarity(Rarity.RARE).setAuthor("Sten89").setTitle("Morceus-1457").setUnlocalizedName("mp_fuselage_10_xenon_bhole");
		
		mp_fuselage_10_long_kerosene = new ItemMissile().makeFuselage(FuelType.KEROSENE, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setAuthor("Hoboy").setHealth(30F).setUnlocalizedName("mp_fuselage_10_long_kerosene");
		mp_fuselage_10_long_kerosene_camo = 		((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_10_long_kerosene_camo");
		mp_fuselage_10_long_kerosene_desert = 		((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Desert Camo").setUnlocalizedName("mp_fuselage_10_long_kerosene_desert");
		mp_fuselage_10_long_kerosene_sky = 			((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_10_long_kerosene_sky");
		mp_fuselage_10_long_kerosene_flames = 		((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_long_kerosene_flames");
		mp_fuselage_10_long_kerosene_insulation = 	((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(35F).setUnlocalizedName("mp_fuselage_10_long_kerosene_insulation");
		mp_fuselage_10_long_kerosene_sleek =		((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(40F).setUnlocalizedName("mp_fuselage_10_long_kerosene_sleek");
		mp_fuselage_10_long_kerosene_metal = 		((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setHealth(35F).setUnlocalizedName("mp_fuselage_10_long_kerosene_metal");
		mp_fuselage_10_long_kerosene_dash = 		((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.EPIC).setAuthor("Sam").setTitle("Dash").setWittyText("I wash my hands of it.").setCreativeTab(null).setUnlocalizedName("mp_fuselage_10_long_kerosene_dash");
		mp_fuselage_10_long_kerosene_taint = 		((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted").setUnlocalizedName("mp_fuselage_10_long_kerosene_taint");
		mp_fuselage_10_long_kerosene_vap = 			((ItemMissile) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.EPIC).setAuthor("VT-6/24").setTitle("Minty Contrail").setWittyText("Upper rivet!").setUnlocalizedName("mp_fuselage_10_long_kerosene_vap");
		
		mp_fuselage_10_long_solid = new ItemMissile().makeFuselage(FuelType.SOLID, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(35F)	.setUnlocalizedName("mp_fuselage_10_long_solid");
		mp_fuselage_10_long_solid_flames = 			((ItemMissile) mp_fuselage_10_long_solid).copy().setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_long_solid_flames");
		mp_fuselage_10_long_solid_insulation = 		((ItemMissile) mp_fuselage_10_long_solid).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(40F).setUnlocalizedName("mp_fuselage_10_long_solid_insulation");
		mp_fuselage_10_long_solid_sleek = 			((ItemMissile) mp_fuselage_10_long_solid).copy().setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(45F).setUnlocalizedName("mp_fuselage_10_long_solid_sleek");
		mp_fuselage_10_long_solid_soviet_glory = 	((ItemMissile) mp_fuselage_10_long_solid).copy().setRarity(Rarity.EPIC).setAuthor("Hoboy").setHealth(45F).setTitle("Soviet Glory").setWittyText("Fully Automated Luxury Gay Space Communism!").setUnlocalizedName("mp_fuselage_10_long_solid_soviet_glory");
		mp_fuselage_10_long_solid_bullet = 			((ItemMissile) mp_fuselage_10_long_solid).copy().setRarity(Rarity.COMMON).setAuthor("Sam").setTitle("Bullet Bill").setUnlocalizedName("mp_fuselage_10_long_solid_bullet");
		mp_fuselage_10_long_solid_silvermoonlight = ((ItemMissile) mp_fuselage_10_long_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("The Master").setTitle("Silver Moonlight").setUnlocalizedName("mp_fuselage_10_long_solid_silvermoonlight");
		
		mp_fuselage_10_15_kerosene = new ItemMissile().makeFuselage(FuelType.KEROSENE, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F).setUnlocalizedName("mp_fuselage_10_15_kerosene");
		mp_fuselage_10_15_solid = new ItemMissile().makeFuselage(FuelType.SOLID, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F)		.setUnlocalizedName("mp_fuselage_10_15_solid");
		mp_fuselage_10_15_hydrogen = new ItemMissile().makeFuselage(FuelType.HYDROGEN, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F).setUnlocalizedName("mp_fuselage_10_15_hydrogen");
		mp_fuselage_10_15_balefire = new ItemMissile().makeFuselage(FuelType.BALEFIRE, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F).setUnlocalizedName("mp_fuselage_10_15_balefire");
		
		mp_fuselage_15_kerosene = new ItemMissile().makeFuselage(FuelType.KEROSENE, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setAuthor("Hoboy").setHealth(50F).setUnlocalizedName("mp_fuselage_15_kerosene");
		mp_fuselage_15_kerosene_camo = 			((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_15_kerosene_camo");
		mp_fuselage_15_kerosene_desert = 		((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Desert Camo").setUnlocalizedName("mp_fuselage_15_kerosene_desert");
		mp_fuselage_15_kerosene_sky = 			((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_15_kerosene_sky");
		mp_fuselage_15_kerosene_insulation = 	((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(55F).setWittyText("Rest in spaghetti Columbia :(").setUnlocalizedName("mp_fuselage_15_kerosene_insulation");
		mp_fuselage_15_kerosene_metal = 		((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Bolted Metal").setHealth(60F).setWittyText("Metal frame with metal plating reinforced with bolted metal sheets and metal.").setUnlocalizedName("mp_fuselage_15_kerosene_metal");
		mp_fuselage_15_kerosene_decorated = 	((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Decorated").setHealth(60F).setUnlocalizedName("mp_fuselage_15_kerosene_decorated");
		mp_fuselage_15_kerosene_steampunk = 	((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Steampunk").setHealth(60F).setUnlocalizedName("mp_fuselage_15_kerosene_steampunk");
		mp_fuselage_15_kerosene_polite = 		((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.LEGENDARY).setAuthor("Hoboy").setTitle("Polite").setHealth(60F).setUnlocalizedName("mp_fuselage_15_kerosene_polite");
		mp_fuselage_15_kerosene_blackjack = 	((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.LEGENDARY).setTitle("Queen Whiskey").setHealth(100F).setUnlocalizedName("mp_fuselage_15_kerosene_blackjack");
		mp_fuselage_15_kerosene_lambda = 		((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.RARE).setAuthor("VT-6/24").setTitle("Lambda Complex").setHealth(75F).setWittyText("MAGNIFICENT MICROWAVE CASSEROLE").setUnlocalizedName("mp_fuselage_15_kerosene_lambda");
		mp_fuselage_15_kerosene_minuteman = 	((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Spexta").setTitle("MX 1702").setUnlocalizedName("mp_fuselage_15_kerosene_minuteman");
		mp_fuselage_15_kerosene_pip = 			((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.EPIC).setAuthor("The Doctor").setTitle("LittlePip").setWittyText("31!").setCreativeTab(null).setUnlocalizedName("mp_fuselage_15_kerosene_pip");
		mp_fuselage_15_kerosene_taint = 		((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted").setWittyText("DUN-DUN!").setUnlocalizedName("mp_fuselage_15_kerosene_taint");
		mp_fuselage_15_kerosene_yuck = 			((ItemMissile) mp_fuselage_15_kerosene).copy().setRarity(Rarity.EPIC).setAuthor("Hoboy").setTitle("Flesh").setWittyText("Note: Never clean DNA vials with your own spit.").setHealth(60F).setUnlocalizedName("mp_fuselage_15_kerosene_yuck");
		
		mp_fuselage_15_solid = new ItemMissile().makeFuselage(FuelType.SOLID, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(60F)			.setUnlocalizedName("mp_fuselage_15_solid").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		mp_fuselage_15_solid_insulation = 		((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(65F).setUnlocalizedName("mp_fuselage_15_solid_insulation");
		mp_fuselage_15_solid_desh = 			((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Desh Plating").setHealth(80F).setUnlocalizedName("mp_fuselage_15_solid_desh");
		mp_fuselage_15_solid_soviet_glory = 	((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Soviet Glory").setHealth(70F).setUnlocalizedName("mp_fuselage_15_solid_soviet_glory");
		mp_fuselage_15_solid_soviet_stank = 	((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.EPIC).setAuthor("Hoboy").setTitle("Soviet Stank").setHealth(15F).setWittyText("Aged like a fine wine! Well, almost.").setUnlocalizedName("mp_fuselage_15_solid_soviet_stank");
		mp_fuselage_15_solid_faust = 			((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.LEGENDARY).setAuthor("Dr.Nostalgia").setTitle("Mighty Lauren").setHealth(250F).setWittyText("Welcome to Subway, may I take your order?").setUnlocalizedName("mp_fuselage_15_solid_faust");
		mp_fuselage_15_solid_silvermoonlight = 	((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("The Master").setTitle("Silver Moonlight").setUnlocalizedName("mp_fuselage_15_solid_silvermoonlight");
		mp_fuselage_15_solid_snowy = 			((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("Dr.Nostalgia").setTitle("Chilly Day").setUnlocalizedName("mp_fuselage_15_solid_snowy");
		mp_fuselage_15_solid_panorama = 		((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Panorama").setUnlocalizedName("mp_fuselage_15_solid_panorama");
		mp_fuselage_15_solid_roses = 			((ItemMissile) mp_fuselage_15_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Bed of roses").setUnlocalizedName("mp_fuselage_15_solid_roses");
		
		mp_fuselage_15_hydrogen = new ItemMissile().makeFuselage(FuelType.HYDROGEN, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(50F)	.setUnlocalizedName("mp_fuselage_15_hydrogen").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		mp_fuselage_15_hydrogen_cathedral = ((ItemMissile) mp_fuselage_15_hydrogen).copy().setRarity(Rarity.UNCOMMON).setAuthor("Satan").setTitle("Unholy Cathedral").setUnlocalizedName("mp_fuselage_15_hydrogen_cathedral");
		
		mp_fuselage_15_balefire = new ItemMissile().makeFuselage(FuelType.BALEFIRE, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(75F)	.setUnlocalizedName("mp_fuselage_15_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		
		mp_fuselage_15_20_kerosene = new ItemMissile().makeFuselage(FuelType.KEROSENE, 20000, PartSize.SIZE_15, PartSize.SIZE_20).setAuthor("Hoboy").setHealth(70F).setUnlocalizedName("mp_fuselage_15_20_kerosene").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		mp_fuselage_15_20_kerosene_magnusson = ((ItemMissile)mp_fuselage_15_20_kerosene).copy().setRarity(Rarity.RARE).setAuthor("VT-6/24").setTitle("White Forest Rocket").setWittyText("And get your cranio-conjugal parasite away from my nose cone!").setUnlocalizedName("mp_fuselage_15_20_kerosene_magnusson");
		mp_fuselage_15_20_solid = new ItemMissile().makeFuselage(FuelType.SOLID, 20000, PartSize.SIZE_15, PartSize.SIZE_20).setHealth(70F).setUnlocalizedName("mp_fuselage_15_20_solid").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		
		mp_fuselage_20_kerosene = new ItemMissile().makeFuselage(FuelType.KEROSENE, 1000F, PartSize.SIZE_20, PartSize.SIZE_20)					.setUnlocalizedName("mp_f_20").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		
		mp_warhead_10_he = new ItemMissile().makeWarhead(WarheadType.HE, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F)								.setUnlocalizedName("mp_warhead_10_he").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_incendiary = new ItemMissile().makeWarhead(WarheadType.INC, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F)					.setUnlocalizedName("mp_warhead_10_incendiary").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_buster = new ItemMissile().makeWarhead(WarheadType.BUSTER, 5F, 1.5F, PartSize.SIZE_10).setHealth(5F)						.setUnlocalizedName("mp_warhead_10_buster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_nuclear = new ItemMissile().makeWarhead(WarheadType.NUCLEAR, 35F, 1.5F, PartSize.SIZE_10).setTitle("Tater Tot").setHealth(10F).setUnlocalizedName("mp_warhead_10_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_nuclear_large = new ItemMissile().makeWarhead(WarheadType.NUCLEAR, 75F, 2.5F, PartSize.SIZE_10).setTitle("Chernobyl Boris").setHealth(15F).setUnlocalizedName("mp_warhead_10_nuclear_large").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_taint = new ItemMissile().makeWarhead(WarheadType.TAINT, 15F, 1.5F, PartSize.SIZE_10).setHealth(20F).setRarity(Rarity.UNCOMMON).setWittyText("Eat my taint! Bureaucracy is dead and we killed it!").setUnlocalizedName("mp_warhead_10_taint").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_cloud = new ItemMissile().makeWarhead(WarheadType.CLOUD, 15F, 1.5F, PartSize.SIZE_10).setHealth(20F).setRarity(Rarity.RARE).setUnlocalizedName("mp_warhead_10_cloud").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_he = new ItemMissile().makeWarhead(WarheadType.HE, 50F, 2.5F, PartSize.SIZE_15).setHealth(10F)							.setUnlocalizedName("mp_warhead_15_he").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_incendiary = new ItemMissile().makeWarhead(WarheadType.INC, 35F, 2.5F, PartSize.SIZE_15).setHealth(10F)					.setUnlocalizedName("mp_warhead_15_incendiary").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_nuclear = new ItemMissile().makeWarhead(WarheadType.NUCLEAR, 125F, 5F, PartSize.SIZE_15).setTitle("Auntie Bertha").setHealth(15F).setUnlocalizedName("mp_warhead_15_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_nuclear_shark = ((ItemMissile) mp_warhead_15_nuclear).copy().setRarity(Rarity.UNCOMMON).setTitle("Discount Bullet Bill").setWittyText("Nose art on a cannon bullet? Who does that?").setUnlocalizedName("mp_warhead_15_nuclear_shark");
		mp_warhead_15_boxcar = new ItemMissile().makeWarhead(WarheadType.TX, 250F, 7.5F, PartSize.SIZE_15).setWittyText("?!?!").setHealth(35F).setRarity(Rarity.LEGENDARY).setUnlocalizedName("mp_warhead_15_boxcar").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_n2 = new ItemMissile().makeWarhead(WarheadType.N2, 100F, 5F, PartSize.SIZE_15).setWittyText("[screams geometrically]").setHealth(20F).setRarity(Rarity.RARE).setUnlocalizedName("mp_warhead_15_n2").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_balefire = new ItemMissile().makeWarhead(WarheadType.BALEFIRE, 100F, 7.5F, PartSize.SIZE_15).setRarity(Rarity.LEGENDARY).setAuthor("VT-6/24").setHealth(15F).setWittyText("Hightower, never forgetti.").setUnlocalizedName("mp_warhead_15_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_20_he = new ItemMissile().makeWarhead(WarheadType.HE, 15F, 1F, PartSize.SIZE_20)												.setUnlocalizedName("mp_w_20").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		
		mp_chip_1 = new ItemMissile().makeChip(0.1F)																							.setUnlocalizedName("mp_c_1").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_1");
		mp_chip_2 = new ItemMissile().makeChip(0.05F)																							.setUnlocalizedName("mp_c_2").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_2");
		mp_chip_3 = new ItemMissile().makeChip(0.01F)																							.setUnlocalizedName("mp_c_3").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_3");
		mp_chip_4 = new ItemMissile().makeChip(0.005F)																							.setUnlocalizedName("mp_c_4").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_4");
		mp_chip_5 = new ItemMissile().makeChip(0.0F)																							.setUnlocalizedName("mp_c_5").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_5");

		missile_skin_camo = new ItemCustomLore().setUnlocalizedName("missile_skin_camo").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_camo");
		missile_skin_desert = new ItemCustomLore().setUnlocalizedName("missile_skin_desert").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_desert");
		missile_skin_flames = new ItemCustomLore().setUnlocalizedName("missile_skin_flames").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_flames");
		missile_skin_manly_pink = new ItemCustomLore().setUnlocalizedName("missile_skin_manly_pink").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_manly_pink");
		missile_skin_orange_insulation = new ItemCustomLore().setUnlocalizedName("missile_skin_orange_insulation").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_orange_insulation");
		missile_skin_sleek = new ItemCustomLore().setUnlocalizedName("missile_skin_sleek").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_sleek");
		missile_skin_soviet_glory = new ItemCustomLore().setUnlocalizedName("missile_skin_soviet_glory").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_soviet_glory");
		missile_skin_soviet_stank = new ItemCustomLore().setUnlocalizedName("missile_skin_soviet_stank").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_soviet_stank");
		missile_skin_metal = new ItemCustomLore().setUnlocalizedName("missile_skin_metal").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_skin_metal");
		
		ammo_12gauge = new ItemAmmo().setUnlocalizedName("ammo_12gauge").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_12gauge");
		ammo_12gauge_incendiary = new ItemAmmo().setUnlocalizedName("ammo_12gauge_incendiary").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_12gauge_incendiary");
		ammo_12gauge_shrapnel = new ItemAmmo().setUnlocalizedName("ammo_12gauge_shrapnel").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_12gauge_shrapnel");
		ammo_12gauge_du = new ItemAmmo().setUnlocalizedName("ammo_12gauge_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_12gauge_du");
		ammo_12gauge_sleek = new ItemAmmo().setUnlocalizedName("ammo_12gauge_sleek").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_12gauge_sleek");
		ammo_12gauge_marauder = new ItemAmmo().setUnlocalizedName("ammo_12gauge_marauder").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_12gauge_marauder");
		ammo_20gauge = new ItemAmmo().setUnlocalizedName("ammo_20gauge").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge");
		ammo_20gauge_slug = new ItemAmmo().setUnlocalizedName("ammo_20gauge_slug").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_slug");
		ammo_20gauge_flechette = new ItemAmmo().setUnlocalizedName("ammo_20gauge_flechette").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_flechette");
		ammo_20gauge_incendiary = new ItemAmmo().setUnlocalizedName("ammo_20gauge_incendiary").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_incendiary");
		ammo_20gauge_shrapnel = new ItemAmmo().setUnlocalizedName("ammo_20gauge_shrapnel").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_shrapnel");
		ammo_20gauge_explosive = new ItemAmmo().setUnlocalizedName("ammo_20gauge_explosive").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_explosive");
		ammo_20gauge_caustic = new ItemAmmo().setUnlocalizedName("ammo_20gauge_caustic").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_caustic");
		ammo_20gauge_shock = new ItemAmmo().setUnlocalizedName("ammo_20gauge_shock").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_shock");
		ammo_20gauge_wither = new ItemAmmo().setUnlocalizedName("ammo_20gauge_wither").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_wither");
		ammo_20gauge_sleek = new ItemAmmo().setUnlocalizedName("ammo_20gauge_sleek").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_20gauge_sleek");
		ammo_4gauge = new ItemAmmo().setUnlocalizedName("ammo_4gauge").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge");
		ammo_4gauge_slug = new ItemAmmo().setUnlocalizedName("ammo_4gauge_slug").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_slug");
		ammo_4gauge_flechette = new ItemAmmo().setUnlocalizedName("ammo_4gauge_flechette").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_flechette");
		ammo_4gauge_flechette_phosphorus = new ItemAmmo().setUnlocalizedName("ammo_4gauge_flechette_phosphorus").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_flechette_phosphorus");
		ammo_4gauge_explosive = new ItemAmmo().setUnlocalizedName("ammo_4gauge_explosive").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_explosive");
		ammo_4gauge_semtex = new ItemAmmo().setUnlocalizedName("ammo_4gauge_semtex").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_semtex");
		ammo_4gauge_balefire = new ItemAmmo().setUnlocalizedName("ammo_4gauge_balefire").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_balefire");
		ammo_4gauge_kampf = new ItemAmmo().setUnlocalizedName("ammo_4gauge_kampf").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_kampf");
		ammo_4gauge_canister = new ItemAmmo().setUnlocalizedName("ammo_4gauge_canister").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_canister");
		ammo_4gauge_claw = new ItemAmmo().setUnlocalizedName("ammo_4gauge_claw").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_claw");
		ammo_4gauge_vampire = new ItemAmmo().setUnlocalizedName("ammo_4gauge_vampire").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_vampire");
		ammo_4gauge_void = new ItemAmmo().setUnlocalizedName("ammo_4gauge_void").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_void");
		ammo_4gauge_sleek = new ItemAmmo().setUnlocalizedName("ammo_4gauge_sleek").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_4gauge_sleek");
		ammo_5mm = new ItemAmmo().setUnlocalizedName("ammo_5mm").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_5mm");
		ammo_5mm_explosive = new ItemAmmo().setUnlocalizedName("ammo_5mm_explosive").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_5mm_explosive");
		ammo_5mm_du = new ItemAmmo().setUnlocalizedName("ammo_5mm_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_5mm_du");
		ammo_5mm_star = new ItemAmmo().setUnlocalizedName("ammo_5mm_star").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_5mm_star");
		ammo_5mm_chlorophyte = new ItemAmmo().setUnlocalizedName("ammo_5mm_chlorophyte").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_5mm_chlorophyte");
		ammo_9mm = new ItemAmmo().setUnlocalizedName("ammo_9mm").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_9mm");
		ammo_9mm_ap = new ItemAmmo().setUnlocalizedName("ammo_9mm_ap").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_9mm_ap");
		ammo_9mm_du = new ItemAmmo().setUnlocalizedName("ammo_9mm_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_9mm_du");
		ammo_9mm_chlorophyte = new ItemAmmo().setUnlocalizedName("ammo_9mm_chlorophyte").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_9mm_chlorophyte");
		ammo_9mm_rocket = new ItemAmmo().setUnlocalizedName("ammo_9mm_rocket").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_9mm_rocket");
		ammo_556 = new ItemAmmo().setUnlocalizedName("ammo_556").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556");
		ammo_556_phosphorus = new ItemAmmo().setUnlocalizedName("ammo_556_phosphorus").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_phosphorus");
		ammo_556_ap = new ItemAmmo().setUnlocalizedName("ammo_556_ap").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_ap");
		ammo_556_du = new ItemAmmo().setUnlocalizedName("ammo_556_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_du");
		ammo_556_star = new ItemAmmo().setUnlocalizedName("ammo_556_star").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_star");
		ammo_556_chlorophyte = new ItemAmmo().setUnlocalizedName("ammo_556_chlorophyte").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_chlorophyte");
		ammo_556_sleek = new ItemAmmo().setUnlocalizedName("ammo_556_sleek").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_sleek");
		ammo_556_tracer = new ItemAmmo().setUnlocalizedName("ammo_556_tracer").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_tracer");
		ammo_556_flechette = new ItemAmmo().setUnlocalizedName("ammo_556_flechette").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_flechette");
		ammo_556_flechette_incendiary = new ItemAmmo().setUnlocalizedName("ammo_556_flechette_incendiary").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_flechette_incendiary");
		ammo_556_flechette_phosphorus = new ItemAmmo().setUnlocalizedName("ammo_556_flechette_phosphorus").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_flechette_phosphorus");
		ammo_556_flechette_du = new ItemAmmo().setUnlocalizedName("ammo_556_flechette_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_flechette_du");
		ammo_556_flechette_chlorophyte = new ItemAmmo().setUnlocalizedName("ammo_556_flechette_chlorophyte").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_flechette_chlorophyte");
		ammo_556_flechette_sleek = new ItemAmmo().setUnlocalizedName("ammo_556_flechette_sleek").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_flechette_sleek");
		ammo_556_k = new ItemAmmo().setUnlocalizedName("ammo_556_k").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_556_k");
		ammo_50ae = new ItemAmmo().setUnlocalizedName("ammo_50ae").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50ae");
		ammo_50ae_ap = new ItemAmmo().setUnlocalizedName("ammo_50ae_ap").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50ae_ap");
		ammo_50ae_du = new ItemAmmo().setUnlocalizedName("ammo_50ae_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50ae_du");
		ammo_50ae_star = new ItemAmmo().setUnlocalizedName("ammo_50ae_star").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50ae_star");
		ammo_50ae_chlorophyte = new ItemAmmo().setUnlocalizedName("ammo_50ae_chlorophyte").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50ae_chlorophyte");
		ammo_50bmg = new ItemAmmo().setUnlocalizedName("ammo_50bmg").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg");
		ammo_50bmg_incendiary = new ItemAmmo().setUnlocalizedName("ammo_50bmg_incendiary").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg_incendiary");
		ammo_50bmg_phosphorus = new ItemAmmo().setUnlocalizedName("ammo_50bmg_phosphorus").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg_phosphorus");
		ammo_50bmg_explosive = new ItemAmmo().setUnlocalizedName("ammo_50bmg_explosive").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg_explosive");
		ammo_50bmg_ap = new ItemAmmo().setUnlocalizedName("ammo_50bmg_ap").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg_ap");
		ammo_50bmg_du = new ItemAmmo().setUnlocalizedName("ammo_50bmg_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg_du");
		ammo_50bmg_star = new ItemAmmo().setUnlocalizedName("ammo_50bmg_star").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg_star");
		ammo_50bmg_chlorophyte = new ItemAmmo().setUnlocalizedName("ammo_50bmg_chlorophyte").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg_chlorophyte");
		ammo_50bmg_sleek = new ItemAmmo().setUnlocalizedName("ammo_50bmg_sleek").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_50bmg_sleek");
		ammo_75bolt = new ItemAmmo().setUnlocalizedName("ammo_75bolt").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(8).setTextureName(RefStrings.MODID + ":ammo_75bolt");
		ammo_75bolt_incendiary = new ItemAmmo().setUnlocalizedName("ammo_75bolt_incendiary").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(8).setTextureName(RefStrings.MODID + ":ammo_75bolt_incendiary");
		ammo_75bolt_he = new ItemAmmo().setUnlocalizedName("ammo_75bolt_he").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(8).setTextureName(RefStrings.MODID + ":ammo_75bolt_he");
		ammo_357_desh = new ItemAmmo().setUnlocalizedName("ammo_357_desh").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_357_desh");
		ammo_44 = new ItemAmmo().setUnlocalizedName("ammo_44").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44");
		ammo_44_ap = new ItemAmmo().setUnlocalizedName("ammo_44_ap").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_ap");
		ammo_44_du = new ItemAmmo().setUnlocalizedName("ammo_44_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_du");
		ammo_44_phosphorus = new ItemAmmo().setUnlocalizedName("ammo_44_phosphorus").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_phosphorus");
		ammo_44_star = new ItemAmmo().setUnlocalizedName("ammo_44_star").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_star");
		ammo_44_chlorophyte = new ItemAmmo().setUnlocalizedName("ammo_44_chlorophyte").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_chlorophyte");
		ammo_44_pip = new ItemAmmo().setUnlocalizedName("ammo_44_pip").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_pip");
		ammo_44_bj = new ItemAmmo().setUnlocalizedName("ammo_44_bj").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_bj");
		ammo_44_silver = new ItemAmmo().setUnlocalizedName("ammo_44_silver").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_silver");
		ammo_44_rocket = new ItemAmmo().setUnlocalizedName("ammo_44_rocket").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_44_rocket");
		ammo_22lr = new ItemAmmo().setUnlocalizedName("ammo_22lr").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_22lr");
		ammo_22lr_ap = new ItemAmmo().setUnlocalizedName("ammo_22lr_ap").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_22lr_ap");
		ammo_22lr_chlorophyte = new ItemAmmo().setUnlocalizedName("ammo_22lr_chlorophyte").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_22lr_chlorophyte");
		ammo_folly = new ItemAmmo().setUnlocalizedName("ammo_folly").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_folly");
		ammo_folly_nuclear = new ItemAmmo().setUnlocalizedName("ammo_folly_nuclear").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_folly_nuclear");
		ammo_folly_du = new ItemAmmo().setUnlocalizedName("ammo_folly_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_folly_du");
		ammo_rocket = new ItemAmmo().setUnlocalizedName("ammo_rocket").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket");
		ammo_rocket_he = new ItemAmmo().setUnlocalizedName("ammo_rocket_he").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_he");
		ammo_rocket_incendiary = new ItemAmmo().setUnlocalizedName("ammo_rocket_incendiary").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_incendiary");
		ammo_rocket_phosphorus = new ItemAmmo().setUnlocalizedName("ammo_rocket_phosphorus").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_phosphorus");
		ammo_rocket_shrapnel = new ItemAmmo().setUnlocalizedName("ammo_rocket_shrapnel").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_shrapnel");
		ammo_rocket_emp = new ItemAmmo().setUnlocalizedName("ammo_rocket_emp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_emp");
		ammo_rocket_glare = new ItemAmmo().setUnlocalizedName("ammo_rocket_glare").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_glare");
		ammo_rocket_toxic = new ItemAmmo().setUnlocalizedName("ammo_rocket_toxic").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_toxic");
		ammo_rocket_canister = new ItemAmmo().setUnlocalizedName("ammo_rocket_canister").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_canister");
		ammo_rocket_sleek = new ItemAmmo().setUnlocalizedName("ammo_rocket_sleek").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_sleek");
		ammo_rocket_nuclear = new ItemAmmo().setUnlocalizedName("ammo_rocket_nuclear").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_nuclear");
		ammo_rocket_rpc = new ItemAmmo().setUnlocalizedName("ammo_rocket_rpc").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_rocket_rpc");
		ammo_grenade = new ItemAmmo().setUnlocalizedName("ammo_grenade").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade");
		ammo_grenade_he = new ItemAmmo().setUnlocalizedName("ammo_grenade_he").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_he");
		ammo_grenade_incendiary = new ItemAmmo().setUnlocalizedName("ammo_grenade_incendiary").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_incendiary");
		ammo_grenade_phosphorus = new ItemAmmo().setUnlocalizedName("ammo_grenade_phosphorus").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_phosphorus");
		ammo_grenade_toxic = new ItemAmmo().setUnlocalizedName("ammo_grenade_toxic").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_toxic");
		ammo_grenade_concussion = new ItemAmmo().setUnlocalizedName("ammo_grenade_concussion").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_concussion");
		ammo_grenade_finned = new ItemAmmo().setUnlocalizedName("ammo_grenade_finned").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_finned");
		ammo_grenade_sleek = new ItemAmmo().setUnlocalizedName("ammo_grenade_sleek").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_sleek");
		ammo_grenade_nuclear = new ItemAmmo().setUnlocalizedName("ammo_grenade_nuclear").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_nuclear");
		ammo_grenade_tracer = new ItemAmmo().setUnlocalizedName("ammo_grenade_tracer").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_tracer");
		ammo_grenade_kampf = new ItemAmmo().setUnlocalizedName("ammo_grenade_kampf").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_grenade_kampf");
		ammo_shell = new ItemAmmo().setUnlocalizedName("ammo_shell").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_shell");
		ammo_shell_explosive = new ItemAmmo().setUnlocalizedName("ammo_shell_explosive").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_shell_explosive");
		ammo_shell_apfsds_t = new ItemAmmo().setUnlocalizedName("ammo_shell_apfsds_t").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_shell_apfsds-t");
		ammo_shell_apfsds_du = new ItemAmmo().setUnlocalizedName("ammo_shell_apfsds_du").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_shell_apfsds-du");
		ammo_shell_w9 = new ItemAmmo().setUnlocalizedName("ammo_shell_w9").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_shell_w9");
		ammo_dgk = new ItemAmmo().setUnlocalizedName("ammo_dgk").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_dgk");
		ammo_nuke = new ItemAmmo().setUnlocalizedName("ammo_nuke").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_nuke");
		ammo_nuke_low = new ItemAmmo().setUnlocalizedName("ammo_nuke_low").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_nuke_low");
		ammo_nuke_high = new ItemAmmo().setUnlocalizedName("ammo_nuke_high").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_nuke_high");
		ammo_nuke_tots = new ItemAmmo().setUnlocalizedName("ammo_nuke_tots").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_nuke_tots");
		ammo_nuke_safe = new ItemAmmo().setUnlocalizedName("ammo_nuke_safe").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_nuke_safe");
		ammo_nuke_pumpkin = new ItemAmmo().setUnlocalizedName("ammo_nuke_pumpkin").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_nuke_pumpkin");
		ammo_mirv = new ItemAmmo().setUnlocalizedName("ammo_mirv").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_mirv");
		ammo_mirv_low = new ItemAmmo().setUnlocalizedName("ammo_mirv_low").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_mirv_low");
		ammo_mirv_high = new ItemAmmo().setUnlocalizedName("ammo_mirv_high").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_mirv_high");
		ammo_mirv_safe = new ItemAmmo().setUnlocalizedName("ammo_mirv_safe").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_mirv_safe");
		ammo_mirv_special = new ItemAmmo().setUnlocalizedName("ammo_mirv_special").setCreativeTab(null).setTextureName(RefStrings.MODID + ":ammo_mirv_special");
		ammo_fuel = new ItemAmmo().setUnlocalizedName("ammo_fuel").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ammo_fuel");
		ammo_fuel_napalm = new ItemAmmo().setUnlocalizedName("ammo_fuel_napalm").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ammo_fuel_napalm");
		ammo_fuel_phosphorus = new ItemAmmo().setUnlocalizedName("ammo_fuel_phosphorus").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ammo_fuel_phosphorus");
		ammo_fuel_vaporizer = new ItemAmmo().setUnlocalizedName("ammo_fuel_vaporizer").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ammo_fuel_vaporizer");
		ammo_fuel_gas = new ItemAmmo().setUnlocalizedName("ammo_fuel_gas").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ammo_fuel_gas");
		ammo_cell = new ItemAmmo().setUnlocalizedName("ammo_cell").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(16).setTextureName(RefStrings.MODID + ":ammo_cell");
		ammo_dart = new ItemAmmo().setUnlocalizedName("ammo_dart").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(16).setTextureName(RefStrings.MODID + ":ammo_dart");
		ammo_dart_nerf = new ItemAmmo().setUnlocalizedName("ammo_dart_nerf").setCreativeTab(MainRegistry.weaponTab).setMaxStackSize(16).setTextureName(RefStrings.MODID + ":ammo_dart_nerf");
		
		gun_rpg = new ItemGunBase(GunRocketFactory.getGustavConfig()).setUnlocalizedName("gun_rpg").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_rpg");
		gun_karl = new ItemGunBase(GunRocketFactory.getKarlConfig()).setUnlocalizedName("gun_karl").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_karl");
		gun_panzerschreck = new ItemGunBase(GunRocketFactory.getPanzConfig()).setUnlocalizedName("gun_panzerschreck").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_panzerschreck");
		gun_quadro = new ItemGunBase(GunRocketFactory.getQuadroConfig()).setUnlocalizedName("gun_quadro").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_quadro");
		gun_rpg_ammo = new Item().setUnlocalizedName("gun_rpg_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_rpg_ammo_alt");
		gun_hk69 = new ItemGunBase(GunGrenadeFactory.getHK69Config()).setUnlocalizedName("gun_hk69").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_hk69");
		gun_stinger = new GunStinger().setUnlocalizedName("gun_stinger").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_stinger");
		gun_skystinger = new GunStinger().setUnlocalizedName("gun_skystinger").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_skystinger");
		gun_stinger_ammo = new Item().setUnlocalizedName("gun_stinger_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_stinger_ammo");
		gun_revolver_ammo = new Item().setUnlocalizedName("gun_revolver_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_ammo");
		gun_revolver = new ItemGunBase(Gun357MagnumFactory.getRevolverConfig()).setUnlocalizedName("gun_revolver").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver");
		gun_revolver_saturnite = new ItemGunBase(Gun357MagnumFactory.getRevolverSaturniteConfig()).setUnlocalizedName("gun_revolver_saturnite").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_saturnite");
		gun_revolver_iron_ammo = new Item().setUnlocalizedName("gun_revolver_iron_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_iron_ammo");
		gun_revolver_iron = new ItemGunBase(Gun357MagnumFactory.getRevolverIronConfig()).setUnlocalizedName("gun_revolver_iron").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_iron");
		gun_revolver_gold_ammo = new Item().setUnlocalizedName("gun_revolver_gold_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_gold_ammo");
		gun_revolver_gold = new ItemGunBase(Gun357MagnumFactory.getRevolverGoldConfig()).setUnlocalizedName("gun_revolver_gold").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_gold");
		gun_revolver_lead_ammo = new Item().setUnlocalizedName("gun_revolver_lead_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_lead_ammo");
		gun_revolver_lead = new ItemGunBase(Gun357MagnumFactory.getRevolverLeadConfig()).setUnlocalizedName("gun_revolver_lead").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_lead");
		gun_revolver_schrabidium_ammo = new ItemHazard(1.5F).setUnlocalizedName("gun_revolver_schrabidium_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_schrabidium_ammo");
		gun_revolver_schrabidium = new ItemGunBase(Gun357MagnumFactory.getRevolverSchrabidiumConfig()).setUnlocalizedName("gun_revolver_schrabidium").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_schrabidium");
		gun_revolver_cursed_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_cursed_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_cursed_ammo");
		gun_revolver_cursed = new ItemGunBase(Gun357MagnumFactory.getRevolverCursedConfig()).setUnlocalizedName("gun_revolver_cursed").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_cursed");
		gun_revolver_nightmare_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_nightmare_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nightmare_ammo");
		gun_revolver_nightmare = new ItemGunBase(Gun357MagnumFactory.getRevolverNightmareConfig()).setUnlocalizedName("gun_revolver_nightmare").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nightmare");
		gun_revolver_nightmare2_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_nightmare2_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nightmare2_ammo");
		gun_revolver_nightmare2 = new ItemGunBase(Gun357MagnumFactory.getRevolverNightmare2Config()).setUnlocalizedName("gun_revolver_nightmare2").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nightmare2");
		gun_revolver_pip_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_pip_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_revolver_pip_ammo");
		gun_revolver_pip = new ItemGunBase(Gun44MagnumFactory.getMacintoshConfig()).setUnlocalizedName("gun_revolver_pip").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_pip");
		gun_revolver_nopip_ammo = new Item().setUnlocalizedName("gun_revolver_nopip_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_revolver_nopip_ammo");
		gun_revolver_nopip = new ItemGunBase(Gun44MagnumFactory.getNovacConfig()).setUnlocalizedName("gun_revolver_nopip").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_nopip");
		gun_revolver_blackjack = new ItemGunBase(Gun44MagnumFactory.getBlackjackConfig()).setUnlocalizedName("gun_revolver_blackjack").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_blackjack");
		gun_revolver_silver = new ItemGunBase(Gun44MagnumFactory.getSilverConfig()).setUnlocalizedName("gun_revolver_silver").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_silver");
		gun_revolver_red = new ItemGunBase(Gun44MagnumFactory.getRedConfig()).setUnlocalizedName("gun_revolver_red").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_red");
		gun_deagle = new ItemGunBase(Gun50AEFactory.getDeagleConfig()).setUnlocalizedName("gun_deagle").setFull3D().setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_deagle");
		gun_flechette = new ItemGunBase(Gun556mmFactory.getSPIWConfig(), Gun556mmFactory.getGLauncherConfig()).setUnlocalizedName("gun_flechette").setFull3D().setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_flechette");
		gun_calamity_ammo = new ItemCustomLore().setUnlocalizedName("gun_calamity_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_calamity_ammo");
		gun_calamity = new ItemGunBase(Gun50BMGFactory.getCalamityConfig()).setUnlocalizedName("gun_calamity").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_calamity");
		gun_calamity_dual = new ItemGunBase(Gun50BMGFactory.getSaddleConfig()).setUnlocalizedName("gun_calamity_dual").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_calamity_dual");
		gun_lacunae_ammo = new ItemCustomLore().setUnlocalizedName("gun_lacunae_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_lacunae_ammo");
		gun_minigun = new ItemGunLacunae(Gun5mmFactory.get53Config()).setUnlocalizedName("gun_minigun").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_minigun");
		gun_avenger = new ItemGunLacunae(Gun5mmFactory.get57Config()).setUnlocalizedName("gun_avenger").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_avenger");
		gun_lacunae = new ItemGunLacunae(Gun5mmFactory.getLacunaeConfig()).setUnlocalizedName("gun_lacunae").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lacunae");
		gun_folly = new GunFolly().setUnlocalizedName("gun_folly").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_folly");
		gun_fatman_ammo = new Item().setUnlocalizedName("gun_fatman_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_fatman_ammo");
		gun_fatman = new ItemGunBase(GunFatmanFactory.getFatmanConfig()).setUnlocalizedName("gun_fatman").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fatman");
		gun_proto = new ItemGunBase(GunFatmanFactory.getProtoConfig()).setUnlocalizedName("gun_proto").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_fatman");
		gun_mirv_ammo = new Item().setUnlocalizedName("gun_mirv_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_mirv_ammo");
		gun_mirv = new ItemGunBase(GunFatmanFactory.getMIRVConfig()).setUnlocalizedName("gun_mirv").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mirv");
		gun_bf_ammo = new Item().setUnlocalizedName("gun_bf_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_bf_ammo");
		gun_bf = new ItemGunBase(GunFatmanFactory.getBELConfig()).setUnlocalizedName("gun_bf").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_bf");
		gun_mp40_ammo = new Item().setUnlocalizedName("gun_mp40_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_mp40_ammo");
		gun_mp40 = new ItemGunBase(Gun9mmFactory.getMP40Config()).setUnlocalizedName("gun_mp40").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mp40");
		gun_thompson = new ItemGunBase(Gun9mmFactory.getThompsonConfig()).setUnlocalizedName("gun_thompson").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_thompson");
		gun_uzi_ammo = new Item().setUnlocalizedName("gun_uzi_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_uzi_ammo");
		gun_uzi = new ItemGunBase(Gun22LRFactory.getUziConfig()).setUnlocalizedName("gun_uzi").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi");
		gun_uzi_silencer = new ItemGunBase(Gun22LRFactory.getUziConfig().silenced()).setUnlocalizedName("gun_uzi_silencer").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi_silencer");
		gun_uzi_saturnite = new ItemGunBase(Gun22LRFactory.getSaturniteConfig()).setUnlocalizedName("gun_uzi_saturnite").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi_saturnite");
		gun_uzi_saturnite_silencer = new ItemGunBase(Gun22LRFactory.getSaturniteConfig().silenced()).setUnlocalizedName("gun_uzi_saturnite_silencer").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uzi_saturnite_silencer");
		gun_uboinik_ammo = new Item().setUnlocalizedName("gun_uboinik_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_uboinik_ammo");
		gun_uboinik = new ItemGunBase(Gun12GaugeFactory.getUboinikConfig()).setUnlocalizedName("gun_uboinik").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik");
		gun_supershotgun = new ItemGunShotty(Gun12GaugeFactory.getShottyConfig()).setUnlocalizedName("gun_supershotgun").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik");
		gun_ks23 = new ItemGunBase(Gun4GaugeFactory.getKS23Config()).setUnlocalizedName("gun_ks23").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik");
		gun_sauer = new ItemGunBase(Gun4GaugeFactory.getSauerConfig()).setUnlocalizedName("gun_sauer").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_uboinik");
		gun_lever_action_ammo = new Item().setUnlocalizedName("gun_lever_action_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_lever_action_ammo");
		gun_lever_action = new ItemGunBase(Gun20GaugeFactory.getMareConfig()).setUnlocalizedName("gun_lever_action").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lever_action");
		gun_lever_action_dark = new ItemGunBase(Gun20GaugeFactory.getMareDarkConfig()).setUnlocalizedName("gun_lever_action_dark").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lever_action_dark");
		gun_lever_action_sonata = new GunLeverActionS().setUnlocalizedName("gun_lever_action_sonata").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_lever_action_sonata");
		gun_bolt_action_ammo = new Item().setUnlocalizedName("gun_bolt_action_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":gun_bolt_action_ammo");
		gun_bolt_action = new ItemGunBase(Gun20GaugeFactory.getBoltConfig()).setUnlocalizedName("gun_bolt_action").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolt_action");
		gun_bolt_action_green = new ItemGunBase(Gun20GaugeFactory.getBoltGreenConfig()).setUnlocalizedName("gun_bolt_action_green").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolt_action_green");
		gun_bolt_action_saturnite = new ItemGunBase(Gun20GaugeFactory.getBoltSaturniteConfig()).setUnlocalizedName("gun_bolt_action_saturnite").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolt_action_saturnite");
		gun_mymy = new ItemGunBase(GunDartFactory.getMymyConfig()).setUnlocalizedName("gun_mymy").setFull3D().setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_mymy");
		gun_b92_ammo = new GunB92Cell().setUnlocalizedName("gun_b92_ammo").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92_ammo_alt");
		gun_b92 = new GunB92().setUnlocalizedName("gun_b92").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92");
		gun_b93 = new GunB93().setUnlocalizedName("gun_b93").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b93");
		gun_xvl1456_ammo = new Item().setUnlocalizedName("gun_xvl1456_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_xvl1456_ammo");
		gun_xvl1456 = new ItemGunGauss(GunGaussFactory.getXVLConfig(), GunGaussFactory.getChargedConfig()).setUnlocalizedName("gun_xvl1456").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_xvl1456");
		gun_osipr_ammo = new Item().setUnlocalizedName("gun_osipr_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_osipr_ammo");
		gun_osipr_ammo2 = new Item().setUnlocalizedName("gun_osipr_ammo2").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_osipr_ammo2");
		gun_osipr = new ItemGunOSIPR(GunOSIPRFactory.getOSIPRConfig(), GunOSIPRFactory.getAltConfig()).setUnlocalizedName("gun_osipr").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_osipr");
		gun_immolator_ammo = new Item().setUnlocalizedName("gun_immolator_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_immolator_ammo");
		gun_immolator = new GunImmolator().setUnlocalizedName("gun_immolator").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_immolator");
		gun_flamer = new ItemGunBase(GunEnergyFactory.getFlamerConfig()).setUnlocalizedName("gun_flamer").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_flamer");
		gun_cryolator_ammo = new Item().setUnlocalizedName("gun_cryolator_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_cryolator_ammo");
		gun_cryolator = new GunCryolator().setUnlocalizedName("gun_cryolator").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_cryolator");
		ammo_566_gold = new ItemCustomLore().setUnlocalizedName("gun_mp_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_pm_ammo");
		gun_mp = new ItemGunBase(Gun556mmFactory.getEuphieConfig()).setUnlocalizedName("gun_mp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_pm");
		gun_bolter = new ItemGunBase(Gun75BoltFactory.getBolterConfig()).setUnlocalizedName("gun_bolter").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolter");
		gun_bolter_digamma = new ItemGunBase(Gun75BoltFactory.getBolterConfig()).setUnlocalizedName("gun_bolter_digamma").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_bolter_digamma");
		gun_brimstone = new GunBrimstone().setUnlocalizedName("gun_brimstone").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_brimstone");
		gun_zomg = new ItemGunBase(GunEnergyFactory.getZOMGConfig()).setUnlocalizedName("gun_zomg").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_zomg");
		gun_revolver_inverted = new GunSuicide().setUnlocalizedName("gun_revolver_inverted").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_revolver_inverted");
		gun_emp_ammo = new Item().setUnlocalizedName("gun_emp_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_emp_ammo");
		gun_emp = new ItemGunBase(GunEnergyFactory.getEMPConfig()).setUnlocalizedName("gun_emp").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_emp");
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
		gun_vortex = new ItemGunVortex(Gun556mmFactory.getEuphieConfig()).setUnlocalizedName("gun_vortex").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_vortex");
		gun_super_shotgun = new ItemCustomLore().setUnlocalizedName("gun_super_shotgun").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_super_shotgun");
		gun_moist_nugget = new ItemNugget(3, false).setUnlocalizedName("gun_moist_nugget").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_moist_nugget");
		gun_dampfmaschine = new GunDampfmaschine().setUnlocalizedName("gun_dampfmaschine").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_dampfmaschine");
		gun_darter = new ItemGunDart(GunDartFactory.getDarterConfig()).setFull3D().setUnlocalizedName("gun_darter").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_darter");
		
		ToolMaterial matCrucible = EnumHelper.addToolMaterial("CRUCIBLE", 10, 3, 50.0F, 100.0F, 0);
		crucible = new ItemCrucible(5000, 1F, matCrucible).setUnlocalizedName("crucible").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":crucible");
		
		grenade_generic = new ItemGrenade(4).setUnlocalizedName("grenade_generic").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_generic");
		grenade_strong = new ItemGrenade(5).setUnlocalizedName("grenade_strong").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_strong");
		grenade_frag = new ItemGrenade(4).setUnlocalizedName("grenade_frag").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_frag_alt");
		grenade_fire = new ItemGrenade(4).setUnlocalizedName("grenade_fire").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_fire_alt");
		grenade_shrapnel = new ItemGrenade(4).setUnlocalizedName("grenade_shrapnel").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_shrapnel");
		grenade_cluster = new ItemGrenade(5).setUnlocalizedName("grenade_cluster").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_cluster_alt");
		grenade_flare = new ItemGrenade(0).setUnlocalizedName("grenade_flare").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_flare_alt");
		grenade_electric = new ItemGrenade(5).setUnlocalizedName("grenade_electric").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_electric_alt");
		grenade_poison = new ItemGrenade(4).setUnlocalizedName("grenade_poison").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_poison_alt");
		grenade_gas = new ItemGrenade(4).setUnlocalizedName("grenade_gas").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_gas_alt");
		grenade_pulse = new ItemGrenade(4).setUnlocalizedName("grenade_pulse").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_pulse");
		grenade_plasma = new ItemGrenade(5).setUnlocalizedName("grenade_plasma").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_plasma_alt");
		grenade_tau = new ItemGrenade(5).setUnlocalizedName("grenade_tau").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_tau_alt");
		grenade_schrabidium = new ItemGrenade(7).setUnlocalizedName("grenade_schrabidium").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_schrabidium_alt");
		grenade_lemon = new ItemGrenade(4).setUnlocalizedName("grenade_lemon").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_lemon");
		grenade_gascan = new ItemGrenade(-1).setUnlocalizedName("grenade_gascan").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_gascan");
		grenade_mk2 = new ItemGrenade(5).setUnlocalizedName("grenade_mk2").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_mk2_alt");
		grenade_aschrab = new ItemGrenade(-1).setUnlocalizedName("grenade_aschrab").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_aschrab");
		grenade_nuke = new ItemGrenade(-1).setUnlocalizedName("grenade_nuke").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_nuke_alt");
		grenade_nuclear = new ItemGrenade(7).setUnlocalizedName("grenade_nuclear").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_nuclear");
		grenade_zomg = new ItemGrenade(7).setUnlocalizedName("grenade_zomg").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_zomg");
		grenade_black_hole = new ItemGrenade(7).setUnlocalizedName("grenade_black_hole").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_black_hole");
		grenade_cloud = new ItemGrenade(-1).setUnlocalizedName("grenade_cloud").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_cloud");
		grenade_pink_cloud = new ItemGrenade(-1).setUnlocalizedName("grenade_pink_cloud").setCreativeTab(null).setTextureName(RefStrings.MODID + ":grenade_pink_cloud");
		ullapool_caber = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("ullapool_caber").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ullapool_caber");

		grenade_if_generic = new ItemGrenade(4).setUnlocalizedName("grenade_if_generic").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_generic");
		grenade_if_he = new ItemGrenade(5).setUnlocalizedName("grenade_if_he").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_he");
		grenade_if_bouncy = new ItemGrenade(4).setUnlocalizedName("grenade_if_bouncy").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_bouncy");
		grenade_if_sticky = new ItemGrenade(4).setUnlocalizedName("grenade_if_sticky").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_sticky");
		grenade_if_impact = new ItemGrenade(-1).setUnlocalizedName("grenade_if_impact").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_impact");
		grenade_if_incendiary = new ItemGrenade(4).setUnlocalizedName("grenade_if_incendiary").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_incendiary");
		grenade_if_toxic = new ItemGrenade(4).setUnlocalizedName("grenade_if_toxic").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_toxic");
		grenade_if_concussion = new ItemGrenade(4).setUnlocalizedName("grenade_if_concussion").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_concussion");
		grenade_if_brimstone = new ItemGrenade(5).setUnlocalizedName("grenade_if_brimstone").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_brimstone");
		grenade_if_mystery = new ItemGrenade(5).setUnlocalizedName("grenade_if_mystery").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_mystery");
		grenade_if_spark = new ItemGrenade(7).setUnlocalizedName("grenade_if_spark").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_spark");
		grenade_if_hopwire = new ItemGrenade(7).setUnlocalizedName("grenade_if_hopwire").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_hopwire");
		grenade_if_null = new ItemGrenade(7).setUnlocalizedName("grenade_if_null").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_if_null");
		
		grenade_smart = new ItemGrenade(-1).setUnlocalizedName("grenade_smart").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_smart");
		grenade_mirv = new ItemGrenade(1).setUnlocalizedName("grenade_mirv").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_mirv");
		grenade_breach = new ItemGrenade(-1).setUnlocalizedName("grenade_breach").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_breach");
		grenade_burst = new ItemGrenade(1).setUnlocalizedName("grenade_burst").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_burst");
		nuclear_waste_pearl = new ItemGrenade(-1).setUnlocalizedName("nuclear_waste_pearl").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":nuclear_waste_pearl");
		
		weaponized_starblaster_cell = new WeaponizedCell().setUnlocalizedName("weaponized_starblaster_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92_ammo_weaponized");

		bomb_waffle = new ItemWaffle(20, false).setUnlocalizedName("bomb_waffle").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bomb_waffle");
		schnitzel_vegan = new ItemSchnitzelVegan(0, true).setUnlocalizedName("schnitzel_vegan").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":schnitzel_vegan");
		cotton_candy = new ItemCottonCandy(5, false).setUnlocalizedName("cotton_candy").setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":cotton_candy");
		apple_lead = new ItemAppleSchrabidium(5, 0, false).setUnlocalizedName("apple_lead").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":apple_lead");
		apple_schrabidium = new ItemAppleSchrabidium(20, 100, false).setUnlocalizedName("apple_schrabidium").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":apple_schrabidium");
		tem_flakes = new ItemTemFlakes(0, 0, false).setUnlocalizedName("tem_flakes").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":tem_flakes");
		glowing_stew = new ItemSoup(6).setUnlocalizedName("glowing_stew").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glowing_stew");
		balefire_scrambled = new ItemSoup(6).setUnlocalizedName("balefire_scrambled").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":balefire_scrambled");
		balefire_and_ham = new ItemSoup(6).setUnlocalizedName("balefire_and_ham").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":balefire_and_ham");
		lemon = new ItemLemon(3, 5, false).setUnlocalizedName("lemon").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":lemon");
		definitelyfood = new ItemLemon(2, 5, false).setUnlocalizedName("definitelyfood").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":definitelyfood");
		med_ipecac = new ItemLemon(0, 0, false).setUnlocalizedName("med_ipecac").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_ipecac_new");
		med_ptsd = new ItemLemon(0, 0, false).setUnlocalizedName("med_ptsd").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_ptsd_new");
		med_schizophrenia = new ItemLemon(0, 0, false).setUnlocalizedName("med_schizophrenia").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_schizophrenia_new");
		loops = new ItemLemon(4, 5, false).setUnlocalizedName("loops").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":loops");
		loop_stew = new ItemLemon(10, 10, false).setUnlocalizedName("loop_stew").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":loop_stew");
		spongebob_macaroni = new ItemLemon(5, 5, false).setUnlocalizedName("spongebob_macaroni").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spongebob_macaroni");
		fooditem = new ItemLemon(2, 5, false).setUnlocalizedName("fooditem").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":fooditem");
		twinkie = new ItemLemon(3, 5, false).setUnlocalizedName("twinkie").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":twinkie");
		static_sandwich = new ItemLemon(6, 5, false).setUnlocalizedName("static_sandwich").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":static_sandwich");
		pudding = new ItemLemon(6, 15, false).setUnlocalizedName("pudding").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pudding");
		canteen_13 = new ItemCanteen(1 * 60).setUnlocalizedName("canteen_13").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canteen_13");
		canteen_vodka = new ItemCanteen(3 * 60).setUnlocalizedName("canteen_vodka").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canteen_vodka");
		canteen_fab = new ItemCanteen(2 * 60).setUnlocalizedName("canteen_fab").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canteen_fab");
		pancake = new ItemPancake(20, 20, false).setUnlocalizedName("pancake").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pancake");
		nugget = new ItemLemon(200, 200, false).setUnlocalizedName("nugget").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nugget");
		peas = new ItemPeas().setUnlocalizedName("peas").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":peas");

		defuser = new Item().setUnlocalizedName("defuser").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":defuser");
		
		flame_pony = new ItemCustomLore().setUnlocalizedName("flame_pony").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_pony");
		flame_conspiracy = new ItemCustomLore().setUnlocalizedName("flame_conspiracy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_conspiracy");
		flame_politics = new ItemCustomLore().setUnlocalizedName("flame_politics").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_politics");
		flame_opinion = new ItemCustomLore().setUnlocalizedName("flame_opinion").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_opinion");
		
		gadget_explosive = new Item().setUnlocalizedName("gadget_explosive").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_explosive");
		gadget_explosive8 = new ItemGadget(0F).setUnlocalizedName("gadget_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_explosive8");
		gadget_wireing = new ItemGadget(0F).setUnlocalizedName("gadget_wireing").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_wireing");
		gadget_core = new ItemGadget(5F).setUnlocalizedName("gadget_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_core");

		boy_igniter = new ItemBoy(0F).setUnlocalizedName("boy_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_igniter");
		boy_propellant = new ItemBoy(0F).setUnlocalizedName("boy_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_propellant");
		boy_bullet = new ItemBoy(1.5F).setUnlocalizedName("boy_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_bullet");
		boy_target = new ItemBoy(5.0F).setUnlocalizedName("boy_target").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_target");
		boy_shielding = new ItemBoy(0F).setUnlocalizedName("boy_shielding").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_shielding");
		
		man_explosive = new Item().setUnlocalizedName("man_explosive").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_explosive");
		man_explosive8 = new ItemManMike(0F).setUnlocalizedName("man_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_explosive8");
		man_igniter = new ItemMan().setUnlocalizedName("man_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_igniter");
		man_core = new ItemManMike(5.0F).setUnlocalizedName("man_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_core");

		mike_core = new ItemMike(2.5F).setUnlocalizedName("mike_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_core");
		mike_deut = new ItemMike(0F).setUnlocalizedName("mike_deut").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setContainerItem(ModItems.tank_steel).setTextureName(RefStrings.MODID + ":mike_deut");
		mike_cooling_unit = new ItemMike(0F).setUnlocalizedName("mike_cooling_unit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_cooling_unit");

		tsar_core = new ItemTsar(10F).setUnlocalizedName("tsar_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":tsar_core");

		fleija_igniter = new ItemFleija(0F, false).setUnlocalizedName("fleija_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_igniter");
		fleija_propellant = new ItemFleija(15F, true).setUnlocalizedName("fleija_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_propellant");
		fleija_core = new ItemFleija(10F, false).setUnlocalizedName("fleija_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_core");

		solinium_igniter = new ItemSolinium(0F, false).setUnlocalizedName("solinium_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_igniter");
		solinium_propellant = new ItemSolinium(0F, false).setUnlocalizedName("solinium_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_propellant");
		solinium_core = new ItemSolinium(15F, true).setUnlocalizedName("solinium_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_core");
		
		n2_charge = new ItemN2().setUnlocalizedName("n2_charge").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":n2_charge");
		
		egg_balefire_shard = new ItemHazard(1500F, true).setUnlocalizedName("egg_balefire_shard").setMaxStackSize(16).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":egg_balefire_shard");
		egg_balefire = new ItemHazard(10000F, true).setUnlocalizedName("egg_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":egg_balefire");

		custom_tnt = new ItemCustomLore().setUnlocalizedName("custom_tnt").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_tnt");
		custom_nuke = new ItemCustomLore().setUnlocalizedName("custom_nuke").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_nuke");
		custom_hydro = new ItemCustomLore().setUnlocalizedName("custom_hydro").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_hydro");
		custom_amat = new ItemCustomLore().setUnlocalizedName("custom_amat").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_amat");
		custom_dirty = new ItemCustomLore().setUnlocalizedName("custom_dirty").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_dirty");
		custom_schrab = new ItemCustomLore().setUnlocalizedName("custom_schrab").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_schrab");
		custom_fall = new ItemCustomLore().setUnlocalizedName("custom_fall").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_fall");

		battery_generic = new ItemBattery(5000, 100, 100).setUnlocalizedName("battery_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_generic_new");
		battery_advanced = new ItemBattery(20000, 500, 500).setUnlocalizedName("battery_advanced").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_new");
		battery_lithium = new ItemBattery(250000, 1000, 1000).setUnlocalizedName("battery_lithium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_lithium");
		battery_schrabidium = new ItemBattery(1000000, 5000, 5000).setUnlocalizedName("battery_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium_new");
		battery_spark = new ItemBattery(100000000, 2000000, 2000000).setUnlocalizedName("battery_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark");
		battery_trixite = new ItemBattery(5000000, 40000, 200000).setUnlocalizedName("battery_trixite").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_trixite");
		battery_creative = new Item().setUnlocalizedName("battery_creative").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_creative_new");

		battery_red_cell = new ItemBattery(15000, 100, 100).setUnlocalizedName("battery_red_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_red_cell");
		battery_red_cell_6 = new ItemBattery(15000 * 6, 100, 100).setUnlocalizedName("battery_red_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_red_cell_6");
		battery_red_cell_24 = new ItemBattery(15000 * 24, 100, 100).setUnlocalizedName("battery_red_cell_24").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_red_cell_24");
		battery_advanced_cell = new ItemBattery(60000, 500, 500).setUnlocalizedName("battery_advanced_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_cell");
		battery_advanced_cell_4 = new ItemBattery(60000 * 4, 500, 500).setUnlocalizedName("battery_advanced_cell_4").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_cell_4");
		battery_advanced_cell_12 = new ItemBattery(60000 * 12, 500, 500).setUnlocalizedName("battery_advanced_cell_12").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_advanced_cell_12");
		battery_lithium_cell = new ItemBattery(750000, 1000, 1000).setUnlocalizedName("battery_lithium_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_lithium_cell");
		battery_lithium_cell_3 = new ItemBattery(750000 * 3, 1000, 1000).setUnlocalizedName("battery_lithium_cell_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_lithium_cell_3");
		battery_lithium_cell_6 = new ItemBattery(750000 * 6, 1000, 1000).setUnlocalizedName("battery_lithium_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_lithium_cell_6");
		battery_schrabidium_cell = new ItemBattery(3000000, 5000, 5000).setUnlocalizedName("battery_schrabidium_cell").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium_cell");
		battery_schrabidium_cell_2 = new ItemBattery(3000000 * 2, 5000, 5000).setUnlocalizedName("battery_schrabidium_cell_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium_cell_2");
		battery_schrabidium_cell_4 = new ItemBattery(3000000 * 4, 5000, 5000).setUnlocalizedName("battery_schrabidium_cell_4").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_schrabidium_cell_4");
		battery_spark_cell_6 = new ItemBattery(100000000L * 6L, 2000000, 2000000).setUnlocalizedName("battery_spark_cell_6").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_6");
		battery_spark_cell_25 = new ItemBattery(100000000L * 25L, 2000000, 2000000).setUnlocalizedName("battery_spark_cell_25").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_25");
		battery_spark_cell_100 = new ItemBattery(100000000L * 100L, 2000000, 2000000).setUnlocalizedName("battery_spark_cell_100").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_100");
		battery_spark_cell_1000 = new ItemBattery(100000000L * 1000L, 20000000, 20000000).setUnlocalizedName("battery_spark_cell_1000").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_1000");
		battery_spark_cell_2500 = new ItemBattery(100000000L * 2500L, 20000000, 20000000).setUnlocalizedName("battery_spark_cell_2500").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_2500");
		battery_spark_cell_10000 = new ItemBattery(100000000L * 10000L, 200000000, 200000000).setUnlocalizedName("battery_spark_cell_10000").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_10000");
		battery_spark_cell_power = new ItemBattery(100000000L * 1000000L, 200000000, 200000000).setUnlocalizedName("battery_spark_cell_power").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_spark_cell_power");

		battery_potato = new ItemBattery(100, 0, 100).setUnlocalizedName("battery_potato").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_potato");
		battery_potatos = new ItemPotatos(5000, 0, 100).setUnlocalizedName("battery_potatos").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_potatos");
		battery_su = new ItemBattery(1500, 0, 100).setUnlocalizedName("battery_su").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_su");
		battery_su_l = new ItemBattery(3500, 0, 100).setUnlocalizedName("battery_su_l").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_su_l");
		battery_steam = new ItemBattery(60000, 300, 6000).setUnlocalizedName("battery_steam").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_steam");
		battery_steam_large = new ItemBattery(100000, 500, 10000).setUnlocalizedName("battery_steam_large").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_steam_large");
		hev_battery = new ItemFusionCore(150000).setUnlocalizedName("hev_battery").setMaxStackSize(4).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":hev_battery");
		fusion_core = new ItemFusionCore(2500000).setUnlocalizedName("fusion_core").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_core");
		fusion_core_infinite = new Item().setUnlocalizedName("fusion_core_infinite").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_core_infinite");
		energy_core = new ItemBattery(10000000, 0, 1000).setUnlocalizedName("energy_core").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":energy_core");
		fuse = new ItemCustomLore().setUnlocalizedName("fuse").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fuse");
		redcoil_capacitor = new ItemCapacitor(10).setUnlocalizedName("redcoil_capacitor").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":redcoil_capacitor");
		titanium_filter = new ItemCapacitor(6 * 60 * 60 * 20).setUnlocalizedName("titanium_filter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":titanium_filter");
		screwdriver = new ItemCustomLore().setUnlocalizedName("screwdriver").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":screwdriver");
		overfuse = new ItemCustomLore().setUnlocalizedName("overfuse").setMaxStackSize(1).setFull3D().setTextureName(RefStrings.MODID + ":overfuse");
		arc_electrode = new ItemCustomLore().setUnlocalizedName("arc_electrode").setMaxDamage(250).setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setFull3D().setTextureName(RefStrings.MODID + ":arc_electrode");
		arc_electrode_burnt = new Item().setUnlocalizedName("arc_electrode_burnt").setMaxStackSize(1).setFull3D().setTextureName(RefStrings.MODID + ":arc_electrode_burnt");
		arc_electrode_desh = new ItemCustomLore().setUnlocalizedName("arc_electrode_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setFull3D().setTextureName(RefStrings.MODID + ":arc_electrode_desh");

		dynosphere_base = new Item().setUnlocalizedName("dynosphere_base").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_base");
		dynosphere_desh = new ItemBattery(1000000L, 10000, 0).setUnlocalizedName("dynosphere_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_desh");
		dynosphere_desh_charged = new Item().setUnlocalizedName("dynosphere_desh_charged").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_desh_charged");
		dynosphere_schrabidium = new ItemBattery(100000000L, 100000, 0).setUnlocalizedName("dynosphere_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_schrabidium");
		dynosphere_schrabidium_charged = new Item().setUnlocalizedName("dynosphere_schrabidium_charged").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_schrabidium_charged");
		dynosphere_euphemium = new ItemBattery(10000000000L, 1000000, 0).setUnlocalizedName("dynosphere_euphemium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_euphemium");
		dynosphere_euphemium_charged = new Item().setUnlocalizedName("dynosphere_euphemium_charged").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_euphemium_charged");
		dynosphere_dineutronium = new ItemBattery(1000000000000L, 10000000, 0).setUnlocalizedName("dynosphere_dineutronium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_dineutronium");
		dynosphere_dineutronium_charged = new Item().setUnlocalizedName("dynosphere_dineutronium_charged").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":dynosphere_dineutronium_charged");

		factory_core_titanium = new ItemBattery(7040000, 1000, 0).setUnlocalizedName("factory_core_titanium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":factory_core_titanium");
		factory_core_advanced = new ItemBattery(4160000, 1000, 0).setUnlocalizedName("factory_core_advanced").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":factory_core_advanced");

		ams_focus_blank = new Item().setUnlocalizedName("ams_focus_blank").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_blank");
		ams_focus_limiter = new ItemCustomLore().setUnlocalizedName("ams_focus_limiter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_limiter");
		ams_focus_booster = new ItemCustomLore().setUnlocalizedName("ams_focus_booster").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_focus_booster");
		ams_muzzle = new ItemCustomLore().setUnlocalizedName("ams_muzzle").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_muzzle");
		ams_lens = new ItemLens(60 * 60 * 60 * 20 * 100).setUnlocalizedName("ams_lens").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_lens");
		ams_core_sing = new ItemAMSCore(1000000000L, 200, 10).setUnlocalizedName("ams_core_sing").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_sing");
		ams_core_wormhole = new ItemAMSCore(1500000000L, 200, 15).setUnlocalizedName("ams_core_wormhole").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_wormhole");
		ams_core_eyeofharmony = new ItemAMSCore(2500000000L, 300, 10).setUnlocalizedName("ams_core_eyeofharmony").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_eyeofharmony");
		ams_core_thingy = new ItemAMSCore(5000000000L, 250, 5).setUnlocalizedName("ams_core_thingy").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":ams_core_thingy");
		
		fusion_shield_tungsten = new ItemFusionShield(60 * 60 * 60 * 5, 3500).setUnlocalizedName("fusion_shield_tungsten").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_tungsten");
		fusion_shield_desh = new ItemFusionShield(60 * 60 * 60 * 10, 4500).setUnlocalizedName("fusion_shield_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_desh");
		fusion_shield_chlorophyte = new ItemFusionShield(60 * 60 * 60 * 15, 9000).setUnlocalizedName("fusion_shield_chlorophyte").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_chlorophyte");
		fusion_shield_vaporwave = new ItemFusionShield(60 * 60 * 60 * 10, 1916169).setUnlocalizedName("fusion_shield_vaporwave").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_vaporwave");

		upgrade_template = new ItemCustomLore().setUnlocalizedName("upgrade_template").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":upgrade_template");
		upgrade_speed_1 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_speed_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_1");
		upgrade_speed_2 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_speed_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_2");
		upgrade_speed_3 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_speed_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_3");
		upgrade_effect_1 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_effect_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_1");
		upgrade_effect_2 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_effect_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_2");
		upgrade_effect_3 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_effect_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_3");
		upgrade_power_1 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_power_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_1");
		upgrade_power_2 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_power_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_2");
		upgrade_power_3 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_power_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_3");
		upgrade_fortune_1 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_fortune_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_1");
		upgrade_fortune_2 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_fortune_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_2");
		upgrade_fortune_3 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_fortune_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_3");
		upgrade_afterburn_1 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_afterburn_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_1");
		upgrade_afterburn_2 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_afterburn_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_2");
		upgrade_afterburn_3 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_afterburn_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_3");
		upgrade_overdrive_1 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_overdrive_1").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_overdrive_1");
		upgrade_overdrive_2 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_overdrive_2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_overdrive_2");
		upgrade_overdrive_3 = new ItemMachineUpgrade().setUnlocalizedName("upgrade_overdrive_3").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_overdrive_3");
		upgrade_radius = new ItemMachineUpgrade().setUnlocalizedName("upgrade_radius").setMaxStackSize(16).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_radius");
		upgrade_health = new ItemMachineUpgrade().setUnlocalizedName("upgrade_health").setMaxStackSize(16).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_health");
		upgrade_smelter = new ItemMachineUpgrade().setUnlocalizedName("upgrade_smelter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_smelter");
		upgrade_shredder = new ItemMachineUpgrade().setUnlocalizedName("upgrade_shredder").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_shredder");
		upgrade_centrifuge = new ItemMachineUpgrade().setUnlocalizedName("upgrade_centrifuge").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_centrifuge");
		upgrade_crystallizer = new ItemMachineUpgrade().setUnlocalizedName("upgrade_crystallizer").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_crystallizer");
		upgrade_nullifier = new ItemMachineUpgrade().setUnlocalizedName("upgrade_nullifier").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_nullifier");
		upgrade_screm = new ItemMachineUpgrade().setUnlocalizedName("upgrade_screm").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_screm");

		wand = new ItemWand().setUnlocalizedName("wand_k").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand");
		wand_s = new ItemWandS().setUnlocalizedName("wand_s").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_s");
		wand_d = new ItemWandD().setUnlocalizedName("wand_d").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_d");
		
		rod_of_discord = new ItemDiscord().setUnlocalizedName("rod_of_discord").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":rod_of_discord");
		
		nuke_starter_kit = new ItemStarterKit().setUnlocalizedName("nuke_starter_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_starter_kit");
		nuke_advanced_kit = new ItemStarterKit().setUnlocalizedName("nuke_advanced_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_advanced_kit");
		nuke_commercially_kit = new ItemStarterKit().setUnlocalizedName("nuke_commercially_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_commercially_kit");
		nuke_electric_kit = new ItemStarterKit().setUnlocalizedName("nuke_electric_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nuke_electric_kit");
		gadget_kit = new ItemStarterKit().setUnlocalizedName("gadget_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_kit");
		boy_kit = new ItemStarterKit().setUnlocalizedName("boy_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_kit");
		man_kit = new ItemStarterKit().setUnlocalizedName("man_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_kit");
		mike_kit = new ItemStarterKit().setUnlocalizedName("mike_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_kit");
		tsar_kit = new ItemStarterKit().setUnlocalizedName("tsar_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":tsar_kit");
		multi_kit = new ItemStarterKit().setUnlocalizedName("multi_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":multi_kit");
		custom_kit = new ItemStarterKit().setUnlocalizedName("custom_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_kit");
		grenade_kit = new ItemStarterKit().setUnlocalizedName("grenade_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_kit");
		fleija_kit = new ItemStarterKit().setUnlocalizedName("fleija_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_kit");
		prototype_kit = new ItemStarterKit().setUnlocalizedName("prototype_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":prototype_kit");
		missile_kit = new ItemStarterKit().setUnlocalizedName("missile_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_kit");
		t45_kit = new ItemStarterKit().setUnlocalizedName("t45_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":t45_kit");
		euphemium_kit = new ItemStarterKit().setUnlocalizedName("euphemium_kit").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":code");
		solinium_kit = new ItemStarterKit().setUnlocalizedName("solinium_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_kit");
		hazmat_kit = new ItemStarterKit().setUnlocalizedName("hazmat_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_kit");
		hazmat_red_kit = new ItemStarterKit().setUnlocalizedName("hazmat_red_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_red_kit");
		hazmat_grey_kit = new ItemStarterKit().setUnlocalizedName("hazmat_grey_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_grey_kit");

		loot_10 = new ItemLootCrate().setUnlocalizedName("loot_10").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_10");
		loot_15 = new ItemLootCrate().setUnlocalizedName("loot_15").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_15");
		loot_misc = new ItemLootCrate().setUnlocalizedName("loot_misc").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_misc");

		clip_revolver_iron = new ItemClip().setUnlocalizedName("clip_revolver_iron").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_iron");
		clip_revolver = new ItemClip().setUnlocalizedName("clip_revolver").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver");
		clip_revolver_gold = new ItemClip().setUnlocalizedName("clip_revolver_gold").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_gold");
		clip_revolver_lead = new ItemClip().setUnlocalizedName("clip_revolver_lead").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_lead");
		clip_revolver_schrabidium = new ItemClip().setUnlocalizedName("clip_revolver_schrabidium").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_schrabidium");
		clip_revolver_cursed = new ItemClip().setUnlocalizedName("clip_revolver_cursed").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_cursed");
		clip_revolver_nightmare = new ItemClip().setUnlocalizedName("clip_revolver_nightmare").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_nightmare");
		clip_revolver_nightmare2 = new ItemClip().setUnlocalizedName("clip_revolver_nightmare2").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_nightmare2");
		clip_revolver_pip = new ItemClip().setUnlocalizedName("clip_revolver_pip").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_pip");
		clip_revolver_nopip = new ItemClip().setUnlocalizedName("clip_revolver_nopip").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":clip_revolver_nopip");
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
		detonator_deadman = new ItemDrop().setUnlocalizedName("detonator_deadman").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_deadman");
		detonator_de = new ItemDrop().setUnlocalizedName("detonator_de").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_de");
		crate_caller = new ItemCrateCaller().setUnlocalizedName("crate_caller").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":crate_caller");
		bomb_caller = new ItemBombCaller().setUnlocalizedName("bomb_caller").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bomb_caller");
		meteor_remote = new ItemMeteorRemote().setUnlocalizedName("meteor_remote").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":meteor_remote");
		spawn_chopper = new ItemChopper().setUnlocalizedName("chopper").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":chopper");
		spawn_worm = new ItemChopper().setUnlocalizedName("spawn_worm").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spawn_worm");
		linker = new ItemTeleLink().setUnlocalizedName("linker").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":linker");
		reactor_sensor = new ItemReactorSensor().setUnlocalizedName("reactor_sensor").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":reactor_sensor");
		oil_detector = new ItemOilDetector().setUnlocalizedName("oil_detector").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":oil_detector");
		turret_control = new ItemTurretControl().setUnlocalizedName("turret_control").setFull3D().setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":turret_control");
		turret_chip = new ItemTurretChip().setUnlocalizedName("turret_chip").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_chip");
		turret_biometry = new ItemTurretBiometry().setUnlocalizedName("turret_biometry").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":rei_scanner");
		geiger_counter = new ItemGeigerCounter().setUnlocalizedName("geiger_counter").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":geiger_counter");
		digamma_diagnostic = new ItemDigammaDiagnostic().setUnlocalizedName("digamma_diagnostic").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":digamma_diagnostic");
		survey_scanner = new ItemSurveyScanner().setUnlocalizedName("survey_scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":survey_scanner");
		mirror_tool = new ItemMirrorTool().setUnlocalizedName("mirror_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":mirror_tool");

		key = new ItemKey().setUnlocalizedName("key").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":key");
		key_red = new ItemCustomLore().setUnlocalizedName("key_red").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":key_red");
		key_kit = new ItemCounterfitKeys().setUnlocalizedName("key_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":key_pair");
		key_fake = new ItemKey().setUnlocalizedName("key_fake").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":key_gold");
		pin = new ItemCustomLore().setUnlocalizedName("pin").setMaxStackSize(8).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pin");
		padlock_rusty = new ItemLock(1).setUnlocalizedName("padlock_rusty").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock_rusty");
		padlock = new ItemLock(0.1).setUnlocalizedName("padlock").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock");
		padlock_reinforced = new ItemLock(0.02).setUnlocalizedName("padlock_reinforced").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock_reinforced");
		padlock_unbreakable = new ItemLock(0).setUnlocalizedName("padlock_unbreakable").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":padlock_unbreakable");
		
		mech_key = new ItemCustomLore().setUnlocalizedName("mech_key").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":mech_key");

		turret_light_ammo = new ItemTurretAmmo(ModBlocks.turret_light, 100).setUnlocalizedName("turret_light_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":turret_light_ammo");
		turret_heavy_ammo = new ItemTurretAmmo(ModBlocks.turret_heavy, 25).setUnlocalizedName("turret_heavy_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":turret_heavy_ammo");
		turret_rocket_ammo = new ItemTurretAmmo(ModBlocks.turret_rocket, 8).setUnlocalizedName("turret_rocket_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":turret_rocket_ammo");
		turret_flamer_ammo = new ItemTurretAmmo(ModBlocks.turret_flamer, 200).setUnlocalizedName("turret_flamer_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":turret_flamer_ammo");
		turret_tau_ammo = new ItemTurretAmmo(ModBlocks.turret_tau, 100).setUnlocalizedName("turret_tau_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":turret_tau_ammo");
		turret_spitfire_ammo = new ItemTurretAmmo(ModBlocks.turret_spitfire, 2).setUnlocalizedName("turret_spitfire_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_spitfire_ammo");
		turret_cwis_ammo = new ItemTurretAmmo(ModBlocks.turret_cwis, 250).setUnlocalizedName("turret_cwis_ammo").setCreativeTab(null).setTextureName(RefStrings.MODID + ":turret_cwis_ammo");
		turret_cheapo_ammo = new ItemTurretAmmo(ModBlocks.turret_cheapo, 100).setUnlocalizedName("turret_cheapo_ammo").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_cheapo_ammo");
		
		template_folder = new ItemTemplateFolder().setUnlocalizedName("template_folder").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":template_folder");
		journal_pip = new ItemTemplateFolder().setUnlocalizedName("journal_pip").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":journal_pip");
		journal_bj = new ItemTemplateFolder().setUnlocalizedName("journal_bj").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":journal_bj");
		journal_silver = new ItemTemplateFolder().setUnlocalizedName("journal_silver").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":journal_silver");
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
		fluid_duct = new ItemFluidDuct().setUnlocalizedName("fluid_duct").setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":duct");

		bobmazon_materials = new ItemCatalog().setUnlocalizedName("bobmazon_materials").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":bobmazon_materials");
		bobmazon_machines = new ItemCatalog().setUnlocalizedName("bobmazon_machines").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":bobmazon_machines");
		bobmazon_weapons = new ItemCatalog().setUnlocalizedName("bobmazon_weapons").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":bobmazon_weapons");
		bobmazon_tools = new ItemCatalog().setUnlocalizedName("bobmazon_tools").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":bobmazon_tools");
		bobmazon_hidden = new ItemCatalog().setUnlocalizedName("bobmazon_hidden").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":bobmazon_special");

		euphemium_helmet = new ArmorEuphemium(MainRegistry.aMatEuph, 6, 0).setUnlocalizedName("euphemium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_helmet");
		euphemium_plate = new ArmorEuphemium(MainRegistry.aMatEuph, 6, 1).setUnlocalizedName("euphemium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_plate");
		euphemium_legs = new ArmorEuphemium(MainRegistry.aMatEuph, 6, 2).setUnlocalizedName("euphemium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_legs");
		euphemium_boots = new ArmorEuphemium(MainRegistry.aMatEuph, 6, 3).setUnlocalizedName("euphemium_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_boots");
		
		goggles = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("goggles").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":goggles");
		gas_mask = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("gas_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask");
		gas_mask_m65 = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("gas_mask_m65").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask_m65");
		hat = new ArmorHat(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("nossy_hat").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hat");
		beta = new ItemDrop().setUnlocalizedName("beta").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":beta");
		//oxy_mask = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("oxy_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":oxy_mask");

		schrabidium_helmet = new ArmorFSB(MainRegistry.aMatSchrab, 7, 0, RefStrings.MODID + ":textures/armor/schrabidium_1.png").setCap(4F).setMod(0.1F)
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 2))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.setFireproof(true).setUnlocalizedName("schrabidium_helmet").setTextureName(RefStrings.MODID + ":schrabidium_helmet");
		schrabidium_plate = new ArmorFSB(MainRegistry.aMatSchrab, 7, 1, RefStrings.MODID + ":textures/armor/schrabidium_1.png").cloneStats((ArmorFSB) schrabidium_helmet).setUnlocalizedName("schrabidium_plate").setTextureName(RefStrings.MODID + ":schrabidium_plate");
		schrabidium_legs = new ArmorFSB(MainRegistry.aMatSchrab, 7, 2, RefStrings.MODID + ":textures/armor/schrabidium_2.png").cloneStats((ArmorFSB) schrabidium_helmet).setCap(4F).setMod(0.1F).setUnlocalizedName("schrabidium_legs").setTextureName(RefStrings.MODID + ":schrabidium_legs");
		schrabidium_boots = new ArmorFSB(MainRegistry.aMatSchrab, 7, 3, RefStrings.MODID + ":textures/armor/schrabidium_1.png").cloneStats((ArmorFSB) schrabidium_helmet).setCap(4F).setMod(0.1F).setUnlocalizedName("schrabidium_boots").setTextureName(RefStrings.MODID + ":schrabidium_boots");
		titanium_helmet = new ArmorFSB(MainRegistry.aMatTitan, 7, 0, RefStrings.MODID + ":textures/armor/titanium_1.png").setMod(0.85F).setUnlocalizedName("titanium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_helmet");
		titanium_plate = new ArmorFSB(MainRegistry.aMatTitan, 7, 1, RefStrings.MODID + ":textures/armor/titanium_1.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_plate");
		titanium_legs = new ArmorFSB(MainRegistry.aMatTitan, 7, 2, RefStrings.MODID + ":textures/armor/titanium_2.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_legs");
		titanium_boots = new ArmorFSB(MainRegistry.aMatTitan, 7, 3, RefStrings.MODID + ":textures/armor/titanium_1.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_boots").setTextureName(RefStrings.MODID + ":titanium_boots");
		steel_helmet = new ArmorFSB(MainRegistry.aMatSteel, 7, 0, RefStrings.MODID + ":textures/armor/steel_1.png").setMod(0.9F).setUnlocalizedName("steel_helmet").setTextureName(RefStrings.MODID + ":steel_helmet");
		steel_plate = new ArmorFSB(MainRegistry.aMatSteel, 7, 1, RefStrings.MODID + ":textures/armor/steel_1.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_plate").setTextureName(RefStrings.MODID + ":steel_plate");
		steel_legs = new ArmorFSB(MainRegistry.aMatSteel, 7, 2, RefStrings.MODID + ":textures/armor/steel_2.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_legs").setTextureName(RefStrings.MODID + ":steel_legs");
		steel_boots = new ArmorFSB(MainRegistry.aMatSteel, 7, 3, RefStrings.MODID + ":textures/armor/steel_1.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_boots").setTextureName(RefStrings.MODID + ":steel_boots");
		alloy_helmet = new ArmorFSB(MainRegistry.aMatAlloy, 7, 0, RefStrings.MODID + ":textures/armor/alloy_1.png").setMod(0.75F).setUnlocalizedName("alloy_helmet").setTextureName(RefStrings.MODID + ":alloy_helmet");
		alloy_plate = new ArmorFSB(MainRegistry.aMatAlloy, 7, 1, RefStrings.MODID + ":textures/armor/alloy_1.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_plate").setTextureName(RefStrings.MODID + ":alloy_plate");
		alloy_legs = new ArmorFSB(MainRegistry.aMatAlloy, 7, 2, RefStrings.MODID + ":textures/armor/alloy_2.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_legs").setTextureName(RefStrings.MODID + ":alloy_legs");
		alloy_boots = new ArmorFSB(MainRegistry.aMatAlloy, 7, 3, RefStrings.MODID + ":textures/armor/alloy_1.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_boots").setTextureName(RefStrings.MODID + ":alloy_boots");
		cmb_helmet = new ArmorFSB(MainRegistry.aMatCMB, 7, 0, RefStrings.MODID + ":textures/armor/cmb_1.png").setCap(2F).setThreshold(2F).setMod(0.05F)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 4))
				.setFireproof(true).setUnlocalizedName("cmb_helmet").setTextureName(RefStrings.MODID + ":cmb_helmet");
		cmb_plate = new ArmorFSB(MainRegistry.aMatCMB, 7, 1, RefStrings.MODID + ":textures/armor/cmb_1.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_plate").setTextureName(RefStrings.MODID + ":cmb_plate");
		cmb_legs = new ArmorFSB(MainRegistry.aMatCMB, 7, 2, RefStrings.MODID + ":textures/armor/cmb_2.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_legs").setTextureName(RefStrings.MODID + ":cmb_legs");
		cmb_boots = new ArmorFSB(MainRegistry.aMatCMB, 7, 3, RefStrings.MODID + ":textures/armor/cmb_1.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_boots").setTextureName(RefStrings.MODID + ":cmb_boots");
		paa_plate = new ArmorFSB(MainRegistry.aMatPaa, 7, 1, RefStrings.MODID + ":textures/armor/paa_1.png").setCap(6F).setMod(0.3F).setNoHelmet(true)
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 0)).setUnlocalizedName("paa_plate").setTextureName(RefStrings.MODID + ":paa_plate");
		paa_legs = new ArmorFSB(MainRegistry.aMatPaa, 7, 2, RefStrings.MODID + ":textures/armor/paa_2.png").cloneStats((ArmorFSB) paa_plate).setUnlocalizedName("paa_legs").setTextureName(RefStrings.MODID + ":paa_legs");
		paa_boots = new ArmorFSB(MainRegistry.aMatPaa, 7, 3, RefStrings.MODID + ":textures/armor/paa_1.png").cloneStats((ArmorFSB) paa_plate).setUnlocalizedName("paa_boots").setTextureName(RefStrings.MODID + ":paa_boots");
		asbestos_helmet = new ArmorFSB(MainRegistry.aMatAsbestos, 7, 0, RefStrings.MODID + ":textures/armor/asbestos_1.png").setFireproof(true).setOverlay(RefStrings.MODID + ":textures/misc/overlay_asbestos.png").setUnlocalizedName("asbestos_helmet").setTextureName(RefStrings.MODID + ":asbestos_helmet");
		asbestos_plate = new ArmorFSB(MainRegistry.aMatAsbestos, 7, 1, RefStrings.MODID + ":textures/armor/asbestos_1.png").setFireproof(true).setUnlocalizedName("asbestos_plate").setTextureName(RefStrings.MODID + ":asbestos_plate");
		asbestos_legs = new ArmorFSB(MainRegistry.aMatAsbestos, 7, 2, RefStrings.MODID + ":textures/armor/asbestos_2.png").setFireproof(true).setUnlocalizedName("asbestos_legs").setTextureName(RefStrings.MODID + ":asbestos_legs");
		asbestos_boots = new ArmorFSB(MainRegistry.aMatAsbestos, 7, 3, RefStrings.MODID + ":textures/armor/asbestos_1.png").setFireproof(true).setUnlocalizedName("asbestos_boots").setTextureName(RefStrings.MODID + ":asbestos_boots");
		security_helmet = new ArmorFSB(MainRegistry.aMatSecurity, 7, 0, RefStrings.MODID + ":textures/armor/security_1.png").setMod(0.5F).setUnlocalizedName("security_helmet").setTextureName(RefStrings.MODID + ":security_helmet");
		security_plate = new ArmorFSB(MainRegistry.aMatSecurity, 7, 1, RefStrings.MODID + ":textures/armor/security_1.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_plate").setTextureName(RefStrings.MODID + ":security_plate");
		security_legs = new ArmorFSB(MainRegistry.aMatSecurity, 7, 2, RefStrings.MODID + ":textures/armor/security_2.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_legs").setTextureName(RefStrings.MODID + ":security_legs");
		security_boots = new ArmorFSB(MainRegistry.aMatSecurity, 7, 3, RefStrings.MODID + ":textures/armor/security_1.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_boots").setTextureName(RefStrings.MODID + ":security_boots");
		cobalt_helmet = new ArmorFSB(MainRegistry.aMatCobalt, 7, 0, RefStrings.MODID + ":textures/armor/cobalt_1.png").setMod(0.75F).setUnlocalizedName("cobalt_helmet").setTextureName(RefStrings.MODID + ":cobalt_helmet");
		cobalt_plate = new ArmorFSB(MainRegistry.aMatCobalt, 7, 1, RefStrings.MODID + ":textures/armor/cobalt_1.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_plate").setTextureName(RefStrings.MODID + ":cobalt_plate");
		cobalt_legs = new ArmorFSB(MainRegistry.aMatCobalt, 7, 2, RefStrings.MODID + ":textures/armor/cobalt_2.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_legs").setTextureName(RefStrings.MODID + ":cobalt_legs");
		cobalt_boots = new ArmorFSB(MainRegistry.aMatCobalt, 7, 3, RefStrings.MODID + ":textures/armor/cobalt_1.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_boots").setTextureName(RefStrings.MODID + ":cobalt_boots");
		starmetal_helmet = new ArmorFSB(MainRegistry.aMatStarmetal, 7, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png").setMod(0.5F).setCap(15F)
				.setFireproof(true).setUnlocalizedName("starmetal_helmet").setTextureName(RefStrings.MODID + ":starmetal_helmet");
		starmetal_plate = new ArmorFSB(MainRegistry.aMatStarmetal, 7, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_plate").setTextureName(RefStrings.MODID + ":starmetal_plate");
		starmetal_legs = new ArmorFSB(MainRegistry.aMatStarmetal, 7, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_legs").setTextureName(RefStrings.MODID + ":starmetal_legs");
		starmetal_boots = new ArmorFSB(MainRegistry.aMatStarmetal, 7, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_boots").setTextureName(RefStrings.MODID + ":starmetal_boots");

		ArmorMaterial aMatDNT = EnumHelper.addArmorMaterial("HBM_DNT_LOLOLOL", 3, new int[] { 1, 1, 1, 1 }, 0);
		aMatDNT.customCraftingMaterial = ModItems.ingot_dineutronium;
		dnt_helmet = new ArmorFSB(aMatDNT, 7, 0, RefStrings.MODID + ":textures/armor/dnt_1.png")
				.setMod(5F).setUnlocalizedName("dnt_helmet").setTextureName(RefStrings.MODID + ":dnt_helmet");
		dnt_plate = new ArmorFSB(aMatDNT, 7, 1, RefStrings.MODID + ":textures/armor/dnt_1.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_plate").setTextureName(RefStrings.MODID + ":dnt_plate");
		dnt_legs = new ArmorFSB(aMatDNT, 7, 2, RefStrings.MODID + ":textures/armor/dnt_2.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_legs").setTextureName(RefStrings.MODID + ":dnt_legs");
		dnt_boots = new ArmorFSB(aMatDNT, 7, 3, RefStrings.MODID + ":textures/armor/dnt_1.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_boots").setTextureName(RefStrings.MODID + ":dnt_boots");
		
		ArmorMaterial aMatT45 = EnumHelper.addArmorMaterial("HBM_T45", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatT45.customCraftingMaterial = ModItems.plate_armor_titanium;
		t45_helmet = new ArmorT45(aMatT45, 2, 0, 1000000, 10000, 1000, 5).setCap(10F).setMod(0.5F)
				.setFireproof(true)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setBlastProtection(0.5F)
				.addResistance("monoxide", 0F)
				.addResistance("fall", 0)
				.setUnlocalizedName("t45_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_helmet");
		t45_plate = new ArmorT45(aMatT45, 2, 1, 1000000, 10000, 1000, 5).cloneStats((ArmorFSB) t45_helmet).setUnlocalizedName("t45_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_plate");
		t45_legs = new ArmorT45(aMatT45, 2, 2, 1000000, 10000, 1000, 5).cloneStats((ArmorFSB) t45_helmet).setUnlocalizedName("t45_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_legs");
		t45_boots = new ArmorT45(aMatT45, 2, 3, 1000000, 10000, 1000, 5).cloneStats((ArmorFSB) t45_helmet).setUnlocalizedName("t45_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_boots");
		
		ArmorMaterial aMatAJR = EnumHelper.addArmorMaterial("HBM_T45AJR", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatAJR.customCraftingMaterial = ModItems.plate_armor_ajr;
		ajr_helmet = new ArmorAJR(aMatAJR, 7, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).setMod(0.25F).setCap(6.0F).setThreshold(4F)
				.setFireproof(true)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setBlastProtection(0.25F)
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.addResistance("monoxide", 0F)
				.addResistance("fall", 0).setUnlocalizedName("ajr_helmet").setTextureName(RefStrings.MODID + ":ajr_helmet");
		ajr_plate = new ArmorAJR(aMatAJR, 7, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_plate").setTextureName(RefStrings.MODID + ":ajr_plate");
		ajr_legs = new ArmorAJR(aMatAJR, 7, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_legs").setTextureName(RefStrings.MODID + ":ajr_legs");
		ajr_boots = new ArmorAJR(aMatAJR, 7, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_boots").setTextureName(RefStrings.MODID + ":ajr_boots");
		
		ajro_helmet = new ArmorAJRO(aMatAJR, 7, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).setMod(0.25F).setCap(6.0F).setThreshold(4F)
				.setFireproof(true)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setBlastProtection(0.25F)
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.addResistance("monoxide", 0F)
				.addResistance("fall", 0).setUnlocalizedName("ajro_helmet").setTextureName(RefStrings.MODID + ":ajro_helmet");
		ajro_plate = new ArmorAJRO(aMatAJR, 7, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_plate").setTextureName(RefStrings.MODID + ":ajro_plate");
		ajro_legs = new ArmorAJRO(aMatAJR, 7, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_legs").setTextureName(RefStrings.MODID + ":ajro_legs");
		ajro_boots = new ArmorAJRO(aMatAJR, 7, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_boots").setTextureName(RefStrings.MODID + ":ajro_boots");
		
		ArmorMaterial aMatBJ = EnumHelper.addArmorMaterial("HBM_BLACKJACK", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatBJ.customCraftingMaterial = ModItems.plate_armor_lunar;
		bj_helmet = new ArmorBJ(aMatBJ, 7, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).setMod(0.25F).setCap(4.0F).setThreshold(4F)
				.setFireproof(true)
				.enableVATS(true)
				.enableThermalSight(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.field_76443_y.id, 20, 0))
				.addEffect(new PotionEffect(HbmPotion.radx.id, 20, 0))
				.setBlastProtection(0.5F)
				//.setGravity(0.02D)
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.addResistance("fall", 0).setUnlocalizedName("bj_helmet").setTextureName(RefStrings.MODID + ":bj_helmet");
		bj_plate = new ArmorBJ(aMatBJ, 7, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_plate").setTextureName(RefStrings.MODID + ":bj_plate");
		bj_plate_jetpack = new ArmorBJJetpack(aMatBJ, 7, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_plate_jetpack").setTextureName(RefStrings.MODID + ":bj_plate_jetpack");
		bj_legs = new ArmorBJ(aMatBJ, 7, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_legs").setTextureName(RefStrings.MODID + ":bj_legs");
		bj_boots = new ArmorBJ(aMatBJ, 7, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_boots").setTextureName(RefStrings.MODID + ":bj_boots");

		ArmorMaterial aMatHEV = EnumHelper.addArmorMaterial("HBM_HEV", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatHEV.customCraftingMaterial = ModItems.plate_armor_hev;
		hev_helmet = new ArmorHEV(aMatHEV, 7, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0).setMod(0.25F).setCap(4.0F).setThreshold(2.0F)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.setBlastProtection(0.25F)
				.setMod(0.2F)
				.setHasGeigerSound(true)
				.setHasCustomGeiger(true)
				.addResistance("fall", 0.5F)
				.addResistance("monoxide", 0F)
				.addResistance("onFire", 0F).setUnlocalizedName("hev_helmet").setTextureName(RefStrings.MODID + ":hev_helmet");
		hev_plate = new ArmorHEV(aMatHEV, 7, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_plate").setTextureName(RefStrings.MODID + ":hev_plate");
		hev_legs = new ArmorHEV(aMatHEV, 7, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_legs").setTextureName(RefStrings.MODID + ":hev_legs");
		hev_boots = new ArmorHEV(aMatHEV, 7, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_boots").setTextureName(RefStrings.MODID + ":hev_boots");

		jackt = new ModArmor(MainRegistry.aMatSteel, 7, 1).setUnlocalizedName("jackt").setTextureName(RefStrings.MODID + ":jackt");
		jackt2 = new ModArmor(MainRegistry.aMatSteel, 7, 1).setUnlocalizedName("jackt2").setTextureName(RefStrings.MODID + ":jackt2");

		ArmorMaterial aMatFau = EnumHelper.addArmorMaterial("HBM_DIGAMMA", 150, new int[] { 3, 8, 6, 3 }, 100);
		aMatFau.customCraftingMaterial = ModItems.plate_armor_fau;
		fau_helmet = new ArmorDigamma(aMatFau, 7, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0).setMod(0.25F).setCap(4.0F).setThreshold(2.0F)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.setBlastProtection(0.05F)
				.setMod(0.05F)
				.setHasGeigerSound(true)
				.enableThermalSight(true)
				.setHasHardLanding(true)
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.addResistance("fall", 0F)
				.addResistance("monoxide", 0F)
				.setFireproof(true).setUnlocalizedName("fau_helmet").setTextureName(RefStrings.MODID + ":fau_helmet");
		fau_plate = new ArmorDigamma(aMatFau, 7, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).setUnlocalizedName("fau_plate").setTextureName(RefStrings.MODID + ":fau_plate");
		fau_legs = new ArmorDigamma(aMatFau, 7, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).setUnlocalizedName("fau_legs").setTextureName(RefStrings.MODID + ":fau_legs");
		fau_boots = new ArmorDigamma(aMatFau, 7, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).setUnlocalizedName("fau_boots").setTextureName(RefStrings.MODID + ":fau_boots");

		jackt = new ModArmor(MainRegistry.aMatSteel, 7, 1).setUnlocalizedName("jackt").setTextureName(RefStrings.MODID + ":jackt");
		jackt2 = new ModArmor(MainRegistry.aMatSteel, 7, 1).setUnlocalizedName("jackt2").setTextureName(RefStrings.MODID + ":jackt2");

		chainsaw = new ItemToolAbility(25, -0.05, MainRegistry.tMatChainsaw, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addHitAbility(new WeaponAbility.ChainsawAbility(4))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("chainsaw").setTextureName(RefStrings.MODID + ":chainsaw");
		
		schrabidium_sword = new ItemSwordAbility(150, 0, MainRegistry.tMatSchrab)
				.addHitAbility(new WeaponAbility.RadiationAbility(50F))
				.addHitAbility(new WeaponAbility.VampireAbility(2F))
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_sword").setTextureName(RefStrings.MODID + ":schrabidium_sword");
		
		schrabidium_pickaxe = new ItemToolAbility(20, 0, MainRegistry.tMatSchrab, EnumToolType.PICKAXE)
				.addHitAbility(new WeaponAbility.RadiationAbility(15F))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(10))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility())
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_pickaxe").setTextureName(RefStrings.MODID + ":schrabidium_pickaxe");
		
		schrabidium_axe = new ItemToolAbility(25, 0, MainRegistry.tMatSchrab, EnumToolType.AXE)
				.addHitAbility(new WeaponAbility.RadiationAbility(15F))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(10))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility())
				.addHitAbility(new WeaponAbility.BeheaderAbility())
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_axe").setTextureName(RefStrings.MODID + ":schrabidium_axe");
		
		schrabidium_shovel = new ItemToolAbility(15, 0, MainRegistry.tMatSchrab, EnumToolType.SHOVEL)
				.addHitAbility(new WeaponAbility.RadiationAbility(15F))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(10))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility())
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_shovel").setTextureName(RefStrings.MODID + ":schrabidium_shovel");
		
		schrabidium_hoe = new HoeSchrabidium(MainRegistry.tMatSchrab).setUnlocalizedName("schrabidium_hoe").setTextureName(RefStrings.MODID + ":schrabidium_hoe");
		
		titanium_sword = new ItemSwordAbility(6.5F, 0, MainRegistry.tMatTitan).setUnlocalizedName("titanium_sword").setTextureName(RefStrings.MODID + ":titanium_sword");
		titanium_pickaxe = new ItemToolAbility(4.5F, 0, MainRegistry.tMatTitan, EnumToolType.PICKAXE).setUnlocalizedName("titanium_pickaxe").setTextureName(RefStrings.MODID + ":titanium_pickaxe");
		titanium_axe = new ItemToolAbility(5.5F, 0, MainRegistry.tMatTitan, EnumToolType.AXE)
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("titanium_axe").setTextureName(RefStrings.MODID + ":titanium_axe");
		titanium_shovel = new ItemToolAbility(3.5F, 0, MainRegistry.tMatTitan, EnumToolType.SHOVEL).setUnlocalizedName("titanium_shovel").setTextureName(RefStrings.MODID + ":titanium_shovel");
		titanium_hoe = new ModHoe(MainRegistry.tMatTitan).setUnlocalizedName("titanium_hoe").setTextureName(RefStrings.MODID + ":titanium_hoe");
		steel_sword = new ItemSwordAbility(6F, 0, MainRegistry.tMatSteel).setUnlocalizedName("steel_sword").setTextureName(RefStrings.MODID + ":steel_sword");
		steel_pickaxe = new ItemToolAbility(4F, 0, MainRegistry.tMatSteel, EnumToolType.PICKAXE).setUnlocalizedName("steel_pickaxe").setTextureName(RefStrings.MODID + ":steel_pickaxe");
		steel_axe = new ItemToolAbility(5F, 0, MainRegistry.tMatSteel, EnumToolType.AXE)
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("steel_axe").setTextureName(RefStrings.MODID + ":steel_axe");
		steel_shovel = new ItemToolAbility(3F, 0, MainRegistry.tMatSteel, EnumToolType.SHOVEL).setUnlocalizedName("steel_shovel").setTextureName(RefStrings.MODID + ":steel_shovel");
		steel_hoe = new ModHoe(MainRegistry.tMatSteel).setUnlocalizedName("steel_hoe").setTextureName(RefStrings.MODID + ":steel_hoe");
		
		alloy_sword = new ItemSwordAbility(9F, 0, MainRegistry.tMatAlloy)
				.addHitAbility(new WeaponAbility.StunAbility(2)).setUnlocalizedName("alloy_sword").setTextureName(RefStrings.MODID + ":alloy_sword");
		
		alloy_pickaxe = new ItemToolAbility(6F, 0, MainRegistry.tMatAlloy, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(3)).setUnlocalizedName("alloy_pickaxe").setTextureName(RefStrings.MODID + ":alloy_pickaxe");
		
		alloy_axe = new ItemToolAbility(7F, 0, MainRegistry.tMatAlloy, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(3))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("alloy_axe").setTextureName(RefStrings.MODID + ":alloy_axe");
		
		alloy_shovel = new ItemToolAbility(5F, 0, MainRegistry.tMatAlloy, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(3)).setUnlocalizedName("alloy_shovel").setTextureName(RefStrings.MODID + ":alloy_shovel");
		
		alloy_hoe = new ModHoe(MainRegistry.tMatAlloy).setUnlocalizedName("alloy_hoe").setTextureName(RefStrings.MODID + ":alloy_hoe");
		
		cmb_sword = new ItemSwordAbility(50F, 0, MainRegistry.tMatCMB)
				.addHitAbility(new WeaponAbility.StunAbility(2))
				.addHitAbility(new WeaponAbility.VampireAbility(2F)).setUnlocalizedName("cmb_sword").setTextureName(RefStrings.MODID + ":cmb_sword");
		
		cmb_pickaxe = new ItemToolAbility(10F, 0, MainRegistry.tMatCMB, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(3)).setUnlocalizedName("cmb_pickaxe").setTextureName(RefStrings.MODID + ":cmb_pickaxe");
		
		cmb_axe = new ItemToolAbility(12.5F, 0, MainRegistry.tMatCMB, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(3))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("cmb_axe").setTextureName(RefStrings.MODID + ":cmb_axe");
		
		cmb_shovel = new ItemToolAbility(8F, 0, MainRegistry.tMatCMB, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(3)).setUnlocalizedName("cmb_shovel").setTextureName(RefStrings.MODID + ":cmb_shovel");
		
		cmb_hoe = new ModHoe(MainRegistry.tMatCMB).setUnlocalizedName("cmb_hoe").setTextureName(RefStrings.MODID + ":cmb_hoe");
		
		elec_sword = new ItemSwordAbilityPower(15F, 0, MainRegistry.tMatElec, 500000, 1000, 100)
				.addHitAbility(new WeaponAbility.StunAbility(5)).setUnlocalizedName("elec_sword").setTextureName(RefStrings.MODID + ":elec_sword_anim");
		
		elec_pickaxe = new ItemToolAbilityPower(10F, 0, MainRegistry.tMatElec, EnumToolType.PICKAXE, 500000, 1000, 100)
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2)).setUnlocalizedName("elec_pickaxe").setTextureName(RefStrings.MODID + ":elec_drill_anim");
		
		elec_axe = new ItemToolAbilityPower(12.5F, 0, MainRegistry.tMatElec, EnumToolType.AXE, 500000, 1000, 100)
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2))
				.addHitAbility(new WeaponAbility.ChainsawAbility(6))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("elec_axe").setTextureName(RefStrings.MODID + ":elec_chainsaw_anim");
		
		elec_shovel = new ItemToolAbilityPower(7.5F, 0, MainRegistry.tMatElec, EnumToolType.SHOVEL, 500000, 1000, 100)
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2)).setUnlocalizedName("elec_shovel").setTextureName(RefStrings.MODID + ":elec_shovel_anim");
		
		desh_sword = new ItemSwordAbility(15F, 0, MainRegistry.tMatDesh)
				.addHitAbility(new WeaponAbility.StunAbility(2)).setUnlocalizedName("desh_sword").setTextureName(RefStrings.MODID + ":desh_sword");
		
		desh_pickaxe = new ItemToolAbility(5F, -0.05, MainRegistry.tMatDesh, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.RecursionAbility(3))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2)).setUnlocalizedName("desh_pickaxe").setTextureName(RefStrings.MODID + ":desh_pickaxe");
		
		desh_axe = new ItemToolAbility(6.5F, -0.05, MainRegistry.tMatDesh, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.RecursionAbility(3))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("desh_axe").setTextureName(RefStrings.MODID + ":desh_axe");
		
		desh_shovel = new ItemToolAbility(4F, -0.05, MainRegistry.tMatDesh, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.RecursionAbility(3))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(2)).setUnlocalizedName("desh_shovel").setTextureName(RefStrings.MODID + ":desh_shovel");
		
		desh_hoe = new ModHoe(MainRegistry.tMatDesh).setUnlocalizedName("desh_hoe").setTextureName(RefStrings.MODID + ":desh_hoe");
		
		cobalt_sword = new ItemSwordAbility(12F, 0, MainRegistry.tMatCobalt).setUnlocalizedName("cobalt_sword").setTextureName(RefStrings.MODID + ":cobalt_sword");
		cobalt_pickaxe = new ItemToolAbility(4F, 0, MainRegistry.tMatCobalt, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(1)).setUnlocalizedName("cobalt_pickaxe").setTextureName(RefStrings.MODID + ":cobalt_pickaxe");
		cobalt_axe = new ItemToolAbility(6F, 0, MainRegistry.tMatCobalt, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(1))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("cobalt_axe").setTextureName(RefStrings.MODID + ":cobalt_axe");
		cobalt_shovel = new ItemToolAbility(3.5F, 0, MainRegistry.tMatCobalt, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(1)).setUnlocalizedName("cobalt_shovel").setTextureName(RefStrings.MODID + ":cobalt_shovel");
		cobalt_hoe = new ModHoe(MainRegistry.tMatCobalt).setUnlocalizedName("cobalt_hoe").setTextureName(RefStrings.MODID + ":cobalt_hoe");

		ToolMaterial matDecCobalt = EnumHelper.addToolMaterial("HBM_COBALT2", 3, 1000, 15.0F, 2.5F, 25).setRepairItem(new ItemStack(ModItems.ingot_cobalt));
		cobalt_decorated_sword = new ItemSwordAbility(15F, 0, matDecCobalt).setUnlocalizedName("cobalt_decorated_sword").setTextureName(RefStrings.MODID + ":cobalt_decorated_sword");
		cobalt_decorated_pickaxe = new ItemToolAbility(6F, 0, matDecCobalt, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(3)).setUnlocalizedName("cobalt_decorated_pickaxe").setTextureName(RefStrings.MODID + ":cobalt_decorated_pickaxe");
		cobalt_decorated_axe = new ItemToolAbility(8F, 0, matDecCobalt, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(3))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("cobalt_decorated_axe").setTextureName(RefStrings.MODID + ":cobalt_decorated_axe");
		cobalt_decorated_shovel = new ItemToolAbility(5F, 0, matDecCobalt, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(4))
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(3)).setUnlocalizedName("cobalt_decorated_shovel").setTextureName(RefStrings.MODID + ":cobalt_decorated_shovel");
		cobalt_decorated_hoe = new ModHoe(matDecCobalt).setUnlocalizedName("cobalt_decorated_hoe").setTextureName(RefStrings.MODID + ":cobalt_decorated_hoe");

		ToolMaterial matStarmetal = EnumHelper.addToolMaterial("HBM_STARMETAL", 3, 1000, 20.0F, 2.5F, 30).setRepairItem(new ItemStack(ModItems.ingot_starmetal));
		starmetal_sword = new ItemSwordAbility(25F, 0, matStarmetal)
				.addHitAbility(new WeaponAbility.BeheaderAbility())
				.addHitAbility(new WeaponAbility.StunAbility(3)).setUnlocalizedName("starmetal_sword").setTextureName(RefStrings.MODID + ":starmetal_sword");
		starmetal_pickaxe = new ItemToolAbility(8F, 0, matStarmetal, EnumToolType.PICKAXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(6))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(5))
				.addHitAbility(new WeaponAbility.StunAbility(3)).setUnlocalizedName("starmetal_pickaxe").setTextureName(RefStrings.MODID + ":starmetal_pickaxe");
		starmetal_axe = new ItemToolAbility(12F, 0, matStarmetal, EnumToolType.AXE)
				.addBreakAbility(new ToolAbility.RecursionAbility(6))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(5))
				.addHitAbility(new WeaponAbility.BeheaderAbility())
				.addHitAbility(new WeaponAbility.StunAbility(3)).setUnlocalizedName("starmetal_axe").setTextureName(RefStrings.MODID + ":starmetal_axe");
		starmetal_shovel = new ItemToolAbility(7F, 0, matStarmetal, EnumToolType.SHOVEL)
				.addBreakAbility(new ToolAbility.RecursionAbility(6))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new LuckAbility(5))
				.addHitAbility(new WeaponAbility.StunAbility(3)).setUnlocalizedName("starmetal_shovel").setTextureName(RefStrings.MODID + ":starmetal_shovel");
		starmetal_hoe = new ModHoe(matStarmetal).setUnlocalizedName("starmetal_hoe").setTextureName(RefStrings.MODID + ":starmetal_hoe");
		
		centri_stick = new ItemToolAbility(3F, 0, MainRegistry.tMatElec, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.CentrifugeAbility()).setMaxDamage(50).setUnlocalizedName("centri_stick").setTextureName(RefStrings.MODID + ":centri_stick");
		smashing_hammer = new ItemToolAbility(12F, -0.1, MainRegistry.tMatSteel, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.ShredderAbility()).setMaxDamage(2500).setUnlocalizedName("smashing_hammer").setTextureName(RefStrings.MODID + ":smashing_hammer");
		drax = new ItemToolAbilityPower(15F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 500000000, 100000, 5000)
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility())
				.addBreakAbility(new ToolAbility.LuckAbility(2))
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.RecursionAbility(5)).setUnlocalizedName("drax").setTextureName(RefStrings.MODID + ":drax");
		drax_mk2 = new ItemToolAbilityPower(20F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 1000000000, 250000, 7500)
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility())
				.addBreakAbility(new ToolAbility.CentrifugeAbility())
				.addBreakAbility(new ToolAbility.LuckAbility(3))
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.HammerAbility(3))
				.addBreakAbility(new ToolAbility.RecursionAbility(7)).setUnlocalizedName("drax_mk2").setTextureName(RefStrings.MODID + ":drax_mk2");
		drax_mk3 = new ItemToolAbilityPower(20F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 2500000000L, 500000, 10000)
				.addBreakAbility(new ToolAbility.SmelterAbility())
				.addBreakAbility(new ToolAbility.ShredderAbility())
				.addBreakAbility(new ToolAbility.CentrifugeAbility())
				.addBreakAbility(new ToolAbility.CrystallizerAbility())
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new ToolAbility.LuckAbility(4))
				.addBreakAbility(new ToolAbility.HammerAbility(1))
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.HammerAbility(3))
				.addBreakAbility(new ToolAbility.HammerAbility(4))
				.addBreakAbility(new ToolAbility.RecursionAbility(9)).setUnlocalizedName("drax_mk3").setTextureName(RefStrings.MODID + ":drax_mk3");
		
		ToolMaterial matChlorophyte = EnumHelper.addToolMaterial("HBM_CHLOROPHYTE", 4, 0, 50F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.powder_chlorophyte));
		chlorophyte_pickaxe = new ItemToolAbility(20F, 0, matChlorophyte, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.HammerAbility(2))
				.addBreakAbility(new ToolAbility.LuckAbility(4))
				.addBreakAbility(new ToolAbility.CentrifugeAbility())
				.addBreakAbility(new ToolAbility.MercuryAbility())
				.addHitAbility(new WeaponAbility.StunAbility(10))
				.addHitAbility(new WeaponAbility.VampireAbility(5F))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("chlorophyte_pickaxe").setTextureName(RefStrings.MODID + ":chlorophyte_pickaxe");
		
		ToolMaterial matMese = EnumHelper.addToolMaterial("HBM_MESE", 4, 0, 50F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.plate_paa));
		mese_pickaxe = new ItemToolAbility(35F, 0, matMese, EnumToolType.MINER)
				.addBreakAbility(new ToolAbility.HammerAbility(3))
				.addBreakAbility(new ToolAbility.RecursionAbility(5))
				.addBreakAbility(new ToolAbility.CrystallizerAbility())
				.addBreakAbility(new ToolAbility.SilkAbility())
				.addBreakAbility(new ToolAbility.LuckAbility(9))
				.addBreakAbility(new ToolAbility.ExplosionAbility(2.5F))
				.addBreakAbility(new ToolAbility.ExplosionAbility(5F))
				.addBreakAbility(new ToolAbility.ExplosionAbility(10F))
				.addBreakAbility(new ToolAbility.ExplosionAbility(15F))
				.addHitAbility(new WeaponAbility.StunAbility(10))
				.addHitAbility(new WeaponAbility.PhosphorusAbility(60))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("mese_pickaxe").setTextureName(RefStrings.MODID + ":mese_pickaxe");

		ToolMaterial matMeteorite = EnumHelper.addToolMaterial("HBM_METEORITE", 4, 0, 50F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.plate_paa));
		meteorite_sword = new ItemSwordMeteorite(10F, 0, matMeteorite).setUnlocalizedName("meteorite_sword").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_seared = new ItemSwordMeteorite(15F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_seared").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_reforged = new ItemSwordMeteorite(25F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_reforged").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_hardened = new ItemSwordMeteorite(35F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_hardened").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_alloyed = new ItemSwordMeteorite(50F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_alloyed").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_machined = new ItemSwordMeteorite(65F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_machined").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_treated = new ItemSwordMeteorite(80F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_treated").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_etched = new ItemSwordMeteorite(100F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_etched").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_bred = new ItemSwordMeteorite(125F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_bred").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_irradiated = new ItemSwordMeteorite(175F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_irradiated").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_fused = new ItemSwordMeteorite(250F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_fused").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_baleful = new ItemSwordMeteorite(500F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_baleful").setTextureName(RefStrings.MODID + ":meteorite_sword");
		
		mask_of_infamy = new MaskOfInfamy(ArmorMaterial.IRON, 8, 0).setUnlocalizedName("mask_of_infamy").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_of_infamy");

		hazmat_helmet = new ArmorHazmat(MainRegistry.aMatHaz, 9, 0).setUnlocalizedName("hazmat_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_helmet");
		hazmat_plate = new ArmorHazmat(MainRegistry.aMatHaz, 9, 1).setUnlocalizedName("hazmat_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_plate");
		hazmat_legs = new ArmorHazmat(MainRegistry.aMatHaz, 9, 2).setUnlocalizedName("hazmat_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_legs");
		hazmat_boots = new ArmorHazmat(MainRegistry.aMatHaz, 9, 3).setUnlocalizedName("hazmat_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_boots");
		hazmat_helmet_red = new ArmorModel(MainRegistry.aMatHaz2, 9, 0).setUnlocalizedName("hazmat_helmet_red").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_helmet_red");
		hazmat_plate_red = new ArmorHazmat(MainRegistry.aMatHaz2, 9, 1).setUnlocalizedName("hazmat_plate_red").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_plate_red");
		hazmat_legs_red = new ArmorHazmat(MainRegistry.aMatHaz2, 9, 2).setUnlocalizedName("hazmat_legs_red").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_legs_red");
		hazmat_boots_red = new ArmorHazmat(MainRegistry.aMatHaz2, 9, 3).setUnlocalizedName("hazmat_boots_red").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_boots_red");
		hazmat_helmet_grey = new ArmorModel(MainRegistry.aMatHaz3, 9, 0).setUnlocalizedName("hazmat_helmet_grey").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_helmet_grey");
		hazmat_plate_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 9, 1).setUnlocalizedName("hazmat_plate_grey").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_plate_grey");
		hazmat_legs_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 9, 2).setUnlocalizedName("hazmat_legs_grey").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_legs_grey");
		hazmat_boots_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 9, 3).setUnlocalizedName("hazmat_boots_grey").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_boots_grey");
		hazmat_paa_helmet = new ArmorHazmat(MainRegistry.aMatPaa, 9, 0).setUnlocalizedName("hazmat_paa_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_paa_helmet");
		hazmat_paa_plate = new ArmorHazmat(MainRegistry.aMatPaa, 9, 1).setUnlocalizedName("hazmat_paa_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_paa_plate");
		hazmat_paa_legs = new ArmorHazmat(MainRegistry.aMatPaa, 9, 2).setUnlocalizedName("hazmat_paa_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_paa_legs");
		hazmat_paa_boots = new ArmorHazmat(MainRegistry.aMatPaa, 9, 3).setUnlocalizedName("hazmat_paa_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_paa_boots");

		australium_iii = new ArmorAustralium(MainRegistry.aMatAus3, 9, 1).setUnlocalizedName("australium_iii").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":australium_iii");
		
		jetpack_boost = new JetpackBooster(FluidType.BALEFIRE, 32000).setUnlocalizedName("jetpack_boost").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_boost");
		jetpack_break = new JetpackBreak(FluidType.KEROSENE, 12000).setUnlocalizedName("jetpack_break").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_break");
		jetpack_fly = new JetpackRegular(FluidType.KEROSENE, 12000).setUnlocalizedName("jetpack_fly").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_fly");
		jetpack_vector = new JetpackVectorized(FluidType.KEROSENE, 16000).setUnlocalizedName("jetpack_vector").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_vector");

		cape_test = new ArmorModel(MainRegistry.enumArmorMaterialEmerald, 9, 1).setUnlocalizedName("cape_test").setCreativeTab(null).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_test");
		cape_radiation = new ArmorModel(ArmorMaterial.CHAIN, 9, 1).setUnlocalizedName("cape_radiation").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_radiation");
		cape_gasmask = new ArmorModel(ArmorMaterial.CHAIN, 9, 1).setUnlocalizedName("cape_gasmask").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_gasmask");
		cape_schrabidium = new ArmorModel(MainRegistry.aMatSchrab, 9, 1).setUnlocalizedName("cape_schrabidium").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_schrabidium");

		schrabidium_hammer = new WeaponSpecial(MainRegistry.tMatHammmer).setUnlocalizedName("schrabidium_hammer").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_hammer");
		shimmer_sledge = new WeaponSpecial(MainRegistry.enumToolMaterialSledge).setUnlocalizedName("shimmer_sledge").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":shimmer_sledge_original");
		shimmer_axe = new WeaponSpecial(MainRegistry.enumToolMaterialSledge).setUnlocalizedName("shimmer_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":shimmer_axe");
		bottle_opener = new WeaponSpecial(MainRegistry.enumToolMaterialBottleOpener).setUnlocalizedName("bottle_opener").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":bottle_opener");
		pch = new WeaponSpecial(MainRegistry.tMatHammmer).setUnlocalizedName("pch").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":schrabidium_hammer");
		euphemium_stopper = new ItemSyringe().setUnlocalizedName("euphemium_stopper").setMaxStackSize(1).setFull3D().setTextureName(RefStrings.MODID + ":euphemium_stopper");
		matchstick = new ItemMatch().setUnlocalizedName("matchstick").setCreativeTab(CreativeTabs.tabTools).setFull3D().setTextureName(RefStrings.MODID + ":matchstick");
		balefire_and_steel = new ItemBalefireMatch().setUnlocalizedName("balefire_and_steel").setCreativeTab(CreativeTabs.tabTools).setFull3D().setTextureName(RefStrings.MODID + ":balefire_and_steel");
		crowbar = new ModSword(MainRegistry.tMatSteel).setUnlocalizedName("crowbar").setFull3D().setTextureName(RefStrings.MODID + ":crowbar");
		wrench = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("wrench").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":wrench");
		wrench_flipped = new WeaponSpecial(MainRegistry.tMatElec).setUnlocalizedName("wrench_flipped").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":wrench_flipped");
		memespoon = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("memespoon").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":memespoon");
		wood_gavel = new WeaponSpecial(ToolMaterial.WOOD).setUnlocalizedName("wood_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":wood_gavel");
		lead_gavel = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("lead_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":lead_gavel");
		diamond_gavel = new WeaponSpecial(ToolMaterial.EMERALD).setUnlocalizedName("diamond_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":diamond_gavel");
		ToolMaterial matMeseGavel = EnumHelper.addToolMaterial("HBM_MESEGAVEL", 4, 0, 50F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.plate_paa));
		mese_gavel = new ItemSwordAbility(250, 1.5, matMeseGavel)
				.addHitAbility(new WeaponAbility.PhosphorusAbility(60))
				.addHitAbility(new WeaponAbility.RadiationAbility(500))
				.addHitAbility(new WeaponAbility.StunAbility(10))
				.addHitAbility(new WeaponAbility.VampireAbility(50))
				.addHitAbility(new WeaponAbility.BeheaderAbility()).setUnlocalizedName("mese_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mese_gavel");

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
		reer_graar = new ModSword(MainRegistry.tMatTitan).setUnlocalizedName("reer_graar").setFull3D().setTextureName(RefStrings.MODID + ":reer_graar_hd");
		stopsign = new WeaponSpecial(MainRegistry.tMatAlloy).setUnlocalizedName("stopsign").setTextureName(RefStrings.MODID + ":stopsign");
		sopsign = new WeaponSpecial(MainRegistry.tMatAlloy).setUnlocalizedName("sopsign").setTextureName(RefStrings.MODID + ":sopsign");
		chernobylsign = new WeaponSpecial(MainRegistry.tMatAlloy).setUnlocalizedName("chernobylsign").setTextureName(RefStrings.MODID + ":chernobylsign");

		crystal_horn = new ItemCustomLore().setUnlocalizedName("crystal_horn").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_horn");
		crystal_charred = new ItemCustomLore().setUnlocalizedName("crystal_charred").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_charred");
		
		bucket_mud = new ItemModBucket(ModBlocks.mud_block).setUnlocalizedName("bucket_mud").setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_mud");
		bucket_acid = new ItemModBucket(ModBlocks.acid_block).setUnlocalizedName("bucket_acid").setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_acid");
		bucket_toxic = new ItemModBucket(ModBlocks.toxic_block).setUnlocalizedName("bucket_toxic").setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_toxic");
		bucket_schrabidic_acid = new ItemModBucket(ModBlocks.schrabidic_block).setUnlocalizedName("bucket_schrabidic_acid").setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_schrabidic_acid");
		
		door_metal = new ItemModDoor().setUnlocalizedName("door_metal").setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":door_metal");
		door_office = new ItemModDoor().setUnlocalizedName("door_office").setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":door_office");
		door_bunker = new ItemModDoor().setUnlocalizedName("door_bunker").setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":door_bunker");

		record_lc = new ItemModRecord("lc").setUnlocalizedName("record_lc").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_lc");
		record_ss = new ItemModRecord("ss").setUnlocalizedName("record_ss").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_ss");
		record_vc = new ItemModRecord("vc").setUnlocalizedName("record_vc").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_vc");
		record_glass = new ItemModRecord("glass").setUnlocalizedName("record_glass").setCreativeTab(null).setTextureName(RefStrings.MODID + ":record_glass");
		
		book_guide = new ItemGuideBook().setUnlocalizedName("book_guide").setCreativeTab(null).setTextureName(RefStrings.MODID + ":book_guide");

		polaroid = new ItemPolaroid().setUnlocalizedName("polaroid").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":polaroid_" + MainRegistry.polaroidID);
		glitch = new ItemGlitch().setUnlocalizedName("glitch").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glitch_" + MainRegistry.polaroidID);
		letter = new ItemStarterKit().setUnlocalizedName("letter").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":letter");
		book_secret = new ItemCustomLore().setUnlocalizedName("book_secret").setCreativeTab(MainRegistry.polaroidID == 11 ? MainRegistry.consumableTab : null).setTextureName(RefStrings.MODID + ":book_secret");
		book_of_ = new ItemBook().setUnlocalizedName("book_of_").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":book_of_");
		burnt_bark = new ItemCustomLore().setUnlocalizedName("burnt_bark").setCreativeTab(null).setTextureName(RefStrings.MODID + ":burnt_bark");
		
		initializeItem2();
	}
	
	public static void initializeItem2() {

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

		bob_metalworks = new Item().setUnlocalizedName("bob_metalworks").setTextureName(RefStrings.MODID + ":bob_metalworks");
		bob_assembly = new Item().setUnlocalizedName("bob_assembly").setTextureName(RefStrings.MODID + ":bob_assembly");
		bob_chemistry = new Item().setUnlocalizedName("bob_chemistry").setTextureName(RefStrings.MODID + ":bob_chemistry");
		bob_oil = new Item().setUnlocalizedName("bob_oil").setTextureName(RefStrings.MODID + ":bob_oil");
		bob_nuclear = new Item().setUnlocalizedName("bob_nuclear").setTextureName(RefStrings.MODID + ":bob_nuclear");
		digamma_see = new Item().setUnlocalizedName("digamma_see").setTextureName(RefStrings.MODID + ":digamma_see");
		digamma_feel = new Item().setUnlocalizedName("digamma_feel").setTextureName(RefStrings.MODID + ":digamma_feel");
		digamma_know = new Item().setUnlocalizedName("digamma_know").setTextureName(RefStrings.MODID + ":digamma_know");
		digamma_kauai_moho = new Item().setUnlocalizedName("digamma_kauai_moho").setTextureName(RefStrings.MODID + ":digamma_kauai_moho");
		digamma_up_on_top = new Item().setUnlocalizedName("digamma_up_on_top").setTextureName(RefStrings.MODID + ":digamma_up_on_top");
		
		mysteryshovel = new ItemMS().setUnlocalizedName("mysteryshovel").setFull3D().setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cursed_shovel");
		memory = new ItemBattery(Long.MAX_VALUE / 100L, 100000000000000L, 100000000000000L).setUnlocalizedName("memory").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mo8_anim");

		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.mud_fluid, 1000), new ItemStack(ModItems.bucket_mud));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.acid_fluid, 1000), new ItemStack(ModItems.bucket_acid));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.toxic_fluid, 1000), new ItemStack(ModItems.bucket_toxic));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.schrabidic_fluid, 1000), new ItemStack(ModItems.bucket_schrabidic_acid));
		BucketHandler.INSTANCE.buckets.put(ModBlocks.mud_block, ModItems.bucket_mud);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.acid_block, ModItems.bucket_acid);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.toxic_block, ModItems.bucket_toxic);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.schrabidic_block, ModItems.bucket_schrabidic_acid);
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
		GameRegistry.registerItem(ingot_u233, ingot_u233.getUnlocalizedName());
		GameRegistry.registerItem(ingot_u235, ingot_u235.getUnlocalizedName());
		GameRegistry.registerItem(ingot_u238, ingot_u238.getUnlocalizedName());
		GameRegistry.registerItem(ingot_u238m2, ingot_u238m2.getUnlocalizedName());
		GameRegistry.registerItem(ingot_th232, ingot_th232.getUnlocalizedName());
		GameRegistry.registerItem(ingot_plutonium, ingot_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu238, ingot_pu238.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu239, ingot_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu240, ingot_pu240.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu_mix, ingot_pu_mix.getUnlocalizedName());
		GameRegistry.registerItem(ingot_neptunium, ingot_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_polonium, ingot_polonium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_titanium, ingot_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_copper, ingot_copper.getUnlocalizedName());
		GameRegistry.registerItem(ingot_red_copper, ingot_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(ingot_advanced_alloy, ingot_advanced_alloy.getUnlocalizedName());
		GameRegistry.registerItem(ingot_tungsten, ingot_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ingot_aluminium, ingot_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_steel, ingot_steel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_lead, ingot_lead.getUnlocalizedName());
		GameRegistry.registerItem(ingot_beryllium, ingot_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_cobalt, ingot_cobalt.getUnlocalizedName());
		GameRegistry.registerItem(ingot_dura_steel, ingot_dura_steel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_polymer, ingot_polymer.getUnlocalizedName());
		GameRegistry.registerItem(ingot_schraranium, ingot_schraranium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_schrabidium, ingot_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_schrabidate, ingot_schrabidate.getUnlocalizedName());
		GameRegistry.registerItem(ingot_magnetized_tungsten, ingot_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ingot_combine_steel, ingot_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_solinium, ingot_solinium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_uranium_fuel, ingot_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_thorium_fuel, ingot_thorium_fuel.getUnlocalizedName());
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
		GameRegistry.registerItem(ingot_electronium, ingot_electronium.getUnlocalizedName());

		//Meteorite Ingots
		GameRegistry.registerItem(ingot_steel_dusted, ingot_steel_dusted.getUnlocalizedName());
		GameRegistry.registerItem(ingot_chainsteel, ingot_chainsteel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_meteorite, ingot_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(ingot_meteorite_forged, ingot_meteorite_forged.getUnlocalizedName());
		GameRegistry.registerItem(blade_meteorite, blade_meteorite.getUnlocalizedName());

		//Misc Ingots
		GameRegistry.registerItem(ingot_phosphorus, ingot_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(lithium, lithium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_zirconium, ingot_zirconium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_semtex, ingot_semtex.getUnlocalizedName());
		GameRegistry.registerItem(solid_fuel, solid_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rocket_fuel, rocket_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_fiberglass, ingot_fiberglass.getUnlocalizedName());
		GameRegistry.registerItem(ingot_asbestos, ingot_asbestos.getUnlocalizedName());
		
		//Billets
		GameRegistry.registerItem(billet_uranium, billet_uranium.getUnlocalizedName());
		GameRegistry.registerItem(billet_u233, billet_u233.getUnlocalizedName());
		GameRegistry.registerItem(billet_u235, billet_u235.getUnlocalizedName());
		GameRegistry.registerItem(billet_u238, billet_u238.getUnlocalizedName());
		GameRegistry.registerItem(billet_th232, billet_th232.getUnlocalizedName());
		GameRegistry.registerItem(billet_plutonium, billet_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(billet_pu238, billet_pu238.getUnlocalizedName());
		GameRegistry.registerItem(billet_pu239, billet_pu239.getUnlocalizedName());
		GameRegistry.registerItem(billet_pu240, billet_pu240.getUnlocalizedName());
		GameRegistry.registerItem(billet_pu_mix, billet_pu_mix.getUnlocalizedName());
		GameRegistry.registerItem(billet_neptunium, billet_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(billet_polonium, billet_polonium.getUnlocalizedName());
		GameRegistry.registerItem(billet_schrabidium, billet_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(billet_solinium, billet_solinium.getUnlocalizedName());
		GameRegistry.registerItem(billet_uranium_fuel, billet_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(billet_thorium_fuel, billet_thorium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(billet_plutonium_fuel, billet_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(billet_mox_fuel, billet_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(billet_schrabidium_fuel, billet_schrabidium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(billet_yharonite, billet_yharonite.getUnlocalizedName());
		
		//Dusts % Other
		GameRegistry.registerItem(nugget_mercury, nugget_mercury.getUnlocalizedName());
		GameRegistry.registerItem(bottle_mercury, bottle_mercury.getUnlocalizedName());
		GameRegistry.registerItem(coke, coke.getUnlocalizedName());
		GameRegistry.registerItem(lignite, lignite.getUnlocalizedName());
		GameRegistry.registerItem(briquette_lignite, briquette_lignite.getUnlocalizedName());
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
		GameRegistry.registerItem(powder_polonium, powder_polonium.getUnlocalizedName());
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
		GameRegistry.registerItem(powder_schrabidate, powder_schrabidate.getUnlocalizedName());
		GameRegistry.registerItem(powder_magnetized_tungsten, powder_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(powder_chlorophyte, powder_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(powder_combine_steel, powder_combine_steel.getUnlocalizedName());
		GameRegistry.registerItem(powder_lithium, powder_lithium.getUnlocalizedName());
		GameRegistry.registerItem(powder_zirconium, powder_zirconium.getUnlocalizedName());
		GameRegistry.registerItem(powder_lignite, powder_lignite.getUnlocalizedName());
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
		GameRegistry.registerItem(powder_asbestos, powder_asbestos.getUnlocalizedName());
		GameRegistry.registerItem(powder_magic, powder_magic.getUnlocalizedName());
		GameRegistry.registerItem(powder_cloud, powder_cloud.getUnlocalizedName());
		GameRegistry.registerItem(powder_balefire, powder_balefire.getUnlocalizedName());
		GameRegistry.registerItem(powder_semtex_mix, powder_semtex_mix.getUnlocalizedName());
		GameRegistry.registerItem(powder_desh_mix, powder_desh_mix.getUnlocalizedName());
		GameRegistry.registerItem(powder_desh_ready, powder_desh_ready.getUnlocalizedName());
		GameRegistry.registerItem(powder_desh, powder_desh.getUnlocalizedName());
		GameRegistry.registerItem(powder_nitan_mix, powder_nitan_mix.getUnlocalizedName());
		GameRegistry.registerItem(powder_spark_mix, powder_spark_mix.getUnlocalizedName());
		GameRegistry.registerItem(powder_meteorite, powder_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(powder_euphemium, powder_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(powder_dineutronium, powder_dineutronium.getUnlocalizedName());
		GameRegistry.registerItem(redstone_depleted, redstone_depleted.getUnlocalizedName());
		GameRegistry.registerItem(dust, dust.getUnlocalizedName());
		GameRegistry.registerItem(fallout, fallout.getUnlocalizedName());
		GameRegistry.registerItem(powder_steel_tiny, powder_steel_tiny.getUnlocalizedName());
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
		GameRegistry.registerItem(cordite, cordite.getUnlocalizedName());
		GameRegistry.registerItem(ballistite, ballistite.getUnlocalizedName());
		
		//Crystals
		GameRegistry.registerItem(crystal_iron, crystal_iron.getUnlocalizedName());
		GameRegistry.registerItem(crystal_gold, crystal_gold.getUnlocalizedName());
		GameRegistry.registerItem(crystal_redstone, crystal_redstone.getUnlocalizedName());
		GameRegistry.registerItem(crystal_diamond, crystal_diamond.getUnlocalizedName());
		GameRegistry.registerItem(crystal_uranium, crystal_uranium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_thorium, crystal_thorium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_plutonium, crystal_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_titanium, crystal_titanium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_sulfur, crystal_sulfur.getUnlocalizedName());
		GameRegistry.registerItem(crystal_niter, crystal_niter.getUnlocalizedName());
		GameRegistry.registerItem(crystal_copper, crystal_copper.getUnlocalizedName());
		GameRegistry.registerItem(crystal_tungsten, crystal_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(crystal_aluminium, crystal_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_fluorite, crystal_fluorite.getUnlocalizedName());
		GameRegistry.registerItem(crystal_beryllium, crystal_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_lead, crystal_lead.getUnlocalizedName());
		GameRegistry.registerItem(crystal_schraranium, crystal_schraranium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_schrabidium, crystal_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_rare, crystal_rare.getUnlocalizedName());
		GameRegistry.registerItem(crystal_phosphorus, crystal_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(crystal_lithium, crystal_lithium.getUnlocalizedName());
		GameRegistry.registerItem(crystal_starmetal, crystal_starmetal.getUnlocalizedName());
		GameRegistry.registerItem(crystal_trixite, crystal_trixite.getUnlocalizedName());
		
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
		GameRegistry.registerItem(nugget_u233, nugget_u233.getUnlocalizedName());
		GameRegistry.registerItem(nugget_u235, nugget_u235.getUnlocalizedName());
		GameRegistry.registerItem(nugget_u238, nugget_u238.getUnlocalizedName());
		GameRegistry.registerItem(nugget_th232, nugget_th232.getUnlocalizedName());
		GameRegistry.registerItem(nugget_plutonium, nugget_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_pu238, nugget_pu238.getUnlocalizedName());
		GameRegistry.registerItem(nugget_pu239, nugget_pu239.getUnlocalizedName());
		GameRegistry.registerItem(nugget_pu240, nugget_pu240.getUnlocalizedName());
		GameRegistry.registerItem(nugget_pu_mix, nugget_pu_mix.getUnlocalizedName());
		GameRegistry.registerItem(nugget_neptunium, nugget_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_polonium, nugget_polonium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_lead, nugget_lead.getUnlocalizedName());
		GameRegistry.registerItem(nugget_beryllium, nugget_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_schrabidium, nugget_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_solinium, nugget_solinium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_uranium_fuel, nugget_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(nugget_thorium_fuel, nugget_thorium_fuel.getUnlocalizedName());
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
		GameRegistry.registerItem(plate_kevlar, plate_kevlar.getUnlocalizedName());
		GameRegistry.registerItem(plate_dalekanium, plate_dalekanium.getUnlocalizedName());
		GameRegistry.registerItem(plate_desh, plate_desh.getUnlocalizedName());
		GameRegistry.registerItem(plate_euphemium, plate_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(plate_dineutronium, plate_dineutronium.getUnlocalizedName());
		
		//Armor Plates
		GameRegistry.registerItem(plate_armor_titanium, plate_armor_titanium.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_ajr, plate_armor_ajr.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_hev, plate_armor_hev.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_lunar, plate_armor_lunar.getUnlocalizedName());
		GameRegistry.registerItem(plate_armor_fau, plate_armor_fau.getUnlocalizedName());
		
		//Boards
		GameRegistry.registerItem(board_copper, board_copper.getUnlocalizedName());
		
		//Bolts
		GameRegistry.registerItem(bolt_dura_steel, bolt_dura_steel.getUnlocalizedName());
		GameRegistry.registerItem(bolt_tungsten, bolt_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(bolt_compound, bolt_compound.getUnlocalizedName());
		
		//Other Plates
		GameRegistry.registerItem(hazmat_cloth, hazmat_cloth.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_cloth_red, hazmat_cloth_red.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_cloth_grey, hazmat_cloth_grey.getUnlocalizedName());
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
		GameRegistry.registerItem(motor_desh, motor_desh.getUnlocalizedName());
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
		GameRegistry.registerItem(chlorine_pinwheel, chlorine_pinwheel.getUnlocalizedName());
		GameRegistry.registerItem(ring_starmetal, ring_starmetal.getUnlocalizedName());
		
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
		GameRegistry.registerItem(seg_10, seg_10.getUnlocalizedName());
		GameRegistry.registerItem(seg_15, seg_15.getUnlocalizedName());
		GameRegistry.registerItem(seg_20, seg_20.getUnlocalizedName());
		
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
		
		//Primers
		GameRegistry.registerItem(primer_357, primer_357.getUnlocalizedName());
		GameRegistry.registerItem(primer_44, primer_44.getUnlocalizedName());
		GameRegistry.registerItem(primer_9, primer_9.getUnlocalizedName());
		GameRegistry.registerItem(primer_50, primer_50.getUnlocalizedName());
		GameRegistry.registerItem(primer_buckshot, primer_buckshot.getUnlocalizedName());
		
		//Casings
		GameRegistry.registerItem(casing_357, casing_357.getUnlocalizedName());
		GameRegistry.registerItem(casing_44, casing_44.getUnlocalizedName());
		GameRegistry.registerItem(casing_9, casing_9.getUnlocalizedName());
		GameRegistry.registerItem(casing_50, casing_50.getUnlocalizedName());
		GameRegistry.registerItem(casing_buckshot, casing_buckshot.getUnlocalizedName());
		
		//Bullet Assemblies
		GameRegistry.registerItem(assembly_iron, assembly_iron.getUnlocalizedName());
		GameRegistry.registerItem(assembly_steel, assembly_steel.getUnlocalizedName());
		GameRegistry.registerItem(assembly_lead, assembly_lead.getUnlocalizedName());
		GameRegistry.registerItem(assembly_gold, assembly_gold.getUnlocalizedName());
		GameRegistry.registerItem(assembly_schrabidium, assembly_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(assembly_nightmare, assembly_nightmare.getUnlocalizedName());
		GameRegistry.registerItem(assembly_desh, assembly_desh.getUnlocalizedName());
		//GameRegistry.registerItem(assembly_pip, assembly_pip.getUnlocalizedName());
		GameRegistry.registerItem(assembly_nopip, assembly_nopip.getUnlocalizedName());
		GameRegistry.registerItem(assembly_smg, assembly_smg.getUnlocalizedName());
		GameRegistry.registerItem(assembly_556, assembly_556.getUnlocalizedName());
		GameRegistry.registerItem(assembly_uzi, assembly_uzi.getUnlocalizedName());
		GameRegistry.registerItem(assembly_lacunae, assembly_lacunae.getUnlocalizedName());
		GameRegistry.registerItem(assembly_actionexpress, assembly_actionexpress.getUnlocalizedName());
		GameRegistry.registerItem(assembly_calamity, assembly_calamity.getUnlocalizedName());
		GameRegistry.registerItem(assembly_nuke, assembly_nuke.getUnlocalizedName());
		
		//Folly Parts
		GameRegistry.registerItem(folly_shell, folly_shell.getUnlocalizedName());
		GameRegistry.registerItem(folly_bullet, folly_bullet.getUnlocalizedName());
		GameRegistry.registerItem(folly_bullet_nuclear, folly_bullet_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(folly_bullet_du, folly_bullet_du.getUnlocalizedName());
		
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
		GameRegistry.registerItem(pellet_rtg_polonium, pellet_rtg_polonium.getUnlocalizedName());
		GameRegistry.registerItem(tritium_deuterium_cake, tritium_deuterium_cake.getUnlocalizedName());
		GameRegistry.registerItem(pellet_cluster, pellet_cluster.getUnlocalizedName());
		GameRegistry.registerItem(pellet_buckshot, pellet_buckshot.getUnlocalizedName());
		GameRegistry.registerItem(pellet_flechette, pellet_flechette.getUnlocalizedName());
		GameRegistry.registerItem(pellet_chlorophyte, pellet_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(pellet_mercury, pellet_mercury.getUnlocalizedName());
		GameRegistry.registerItem(pellet_meteorite, pellet_meteorite.getUnlocalizedName());
		GameRegistry.registerItem(pellet_canister, pellet_canister.getUnlocalizedName());
		GameRegistry.registerItem(pellet_claws, pellet_claws.getUnlocalizedName());
		GameRegistry.registerItem(pellet_charged, pellet_charged.getUnlocalizedName());
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

		//DEMON CORE
		GameRegistry.registerItem(demon_core_open, demon_core_open.getUnlocalizedName());
		GameRegistry.registerItem(demon_core_closed, demon_core_closed.getUnlocalizedName());
		
		//Particle Containers
		GameRegistry.registerItem(particle_empty, particle_empty.getUnlocalizedName());
		GameRegistry.registerItem(particle_hydrogen, particle_hydrogen.getUnlocalizedName());
		GameRegistry.registerItem(particle_copper, particle_copper.getUnlocalizedName());
		GameRegistry.registerItem(particle_lead, particle_lead.getUnlocalizedName());
		GameRegistry.registerItem(particle_aproton, particle_aproton.getUnlocalizedName());
		GameRegistry.registerItem(particle_aelectron, particle_aelectron.getUnlocalizedName());
		GameRegistry.registerItem(particle_amat, particle_amat.getUnlocalizedName());
		GameRegistry.registerItem(particle_aschrab, particle_aschrab.getUnlocalizedName());
		GameRegistry.registerItem(particle_higgs, particle_higgs.getUnlocalizedName());
		GameRegistry.registerItem(particle_muon, particle_muon.getUnlocalizedName());
		GameRegistry.registerItem(particle_strange, particle_strange.getUnlocalizedName());
		GameRegistry.registerItem(particle_dark, particle_dark.getUnlocalizedName());
		GameRegistry.registerItem(particle_sparkticle, particle_sparkticle.getUnlocalizedName());
		GameRegistry.registerItem(particle_digamma, particle_digamma.getUnlocalizedName());
		
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
		GameRegistry.registerItem(battery_trixite, battery_trixite.getUnlocalizedName());
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
		GameRegistry.registerItem(hev_battery, hev_battery.getUnlocalizedName());
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
		
		//Folders
		GameRegistry.registerItem(template_folder, template_folder.getUnlocalizedName());
		GameRegistry.registerItem(journal_pip, journal_pip.getUnlocalizedName());
		GameRegistry.registerItem(journal_bj, journal_bj.getUnlocalizedName());
		GameRegistry.registerItem(journal_silver, journal_silver.getUnlocalizedName());
		GameRegistry.registerItem(bobmazon_materials, bobmazon_materials.getUnlocalizedName());
		GameRegistry.registerItem(bobmazon_machines, bobmazon_machines.getUnlocalizedName());
		GameRegistry.registerItem(bobmazon_weapons, bobmazon_weapons.getUnlocalizedName());
		GameRegistry.registerItem(bobmazon_tools, bobmazon_tools.getUnlocalizedName());
		GameRegistry.registerItem(bobmazon_hidden, bobmazon_hidden.getUnlocalizedName());
		
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
		GameRegistry.registerItem(stamp_357, stamp_357.getUnlocalizedName());
		GameRegistry.registerItem(stamp_44, stamp_44.getUnlocalizedName());
		GameRegistry.registerItem(stamp_9, stamp_9.getUnlocalizedName());
		GameRegistry.registerItem(stamp_50, stamp_50.getUnlocalizedName());
		
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
		GameRegistry.registerItem(upgrade_overdrive_1, upgrade_overdrive_1.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_overdrive_2, upgrade_overdrive_2.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_overdrive_3, upgrade_overdrive_3.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_radius, upgrade_radius.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_health, upgrade_health.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_smelter, upgrade_smelter.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_shredder, upgrade_shredder.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_centrifuge, upgrade_centrifuge.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_crystallizer, upgrade_crystallizer.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_nullifier, upgrade_nullifier.getUnlocalizedName());
		GameRegistry.registerItem(upgrade_screm, upgrade_screm.getUnlocalizedName());
		
		//Machine Templates
		GameRegistry.registerItem(siren_track, siren_track.getUnlocalizedName());
		GameRegistry.registerItem(fluid_identifier, fluid_identifier.getUnlocalizedName());
		GameRegistry.registerItem(fluid_icon, fluid_icon.getUnlocalizedName());
		GameRegistry.registerItem(fluid_duct, fluid_duct.getUnlocalizedName());
		GameRegistry.registerItem(assembly_template, assembly_template.getUnlocalizedName());
		GameRegistry.registerItem(chemistry_template, chemistry_template.getUnlocalizedName());
		GameRegistry.registerItem(chemistry_icon, chemistry_icon.getUnlocalizedName());
		
		//Machine Items
		GameRegistry.registerItem(fuse, fuse.getUnlocalizedName());
		GameRegistry.registerItem(redcoil_capacitor, redcoil_capacitor.getUnlocalizedName());
		GameRegistry.registerItem(titanium_filter, titanium_filter.getUnlocalizedName());
		GameRegistry.registerItem(screwdriver, screwdriver.getUnlocalizedName());
		GameRegistry.registerItem(overfuse, overfuse.getUnlocalizedName());
		GameRegistry.registerItem(arc_electrode, arc_electrode.getUnlocalizedName());
		GameRegistry.registerItem(arc_electrode_burnt, arc_electrode_burnt.getUnlocalizedName());
		GameRegistry.registerItem(arc_electrode_desh, arc_electrode_desh.getUnlocalizedName());
		
		//Particle Collider Items
		GameRegistry.registerItem(crystal_energy, crystal_energy.getUnlocalizedName());
		GameRegistry.registerItem(pellet_coolant, pellet_coolant.getUnlocalizedName());
		
		//Particle Collider Fuel
		GameRegistry.registerItem(part_lithium, part_lithium.getUnlocalizedName());
		GameRegistry.registerItem(part_beryllium, part_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(part_carbon, part_carbon.getUnlocalizedName());
		GameRegistry.registerItem(part_copper, part_copper.getUnlocalizedName());
		GameRegistry.registerItem(part_plutonium, part_plutonium.getUnlocalizedName());
		
		//Recycled Units
		GameRegistry.registerItem(recycled_ground, recycled_ground.getUnlocalizedName());
		GameRegistry.registerItem(recycled_rock, recycled_rock.getUnlocalizedName());
		GameRegistry.registerItem(recycled_metal, recycled_metal.getUnlocalizedName());
		GameRegistry.registerItem(recycled_refined, recycled_refined.getUnlocalizedName());
		GameRegistry.registerItem(recycled_organic, recycled_organic.getUnlocalizedName());
		GameRegistry.registerItem(recycled_crystal, recycled_crystal.getUnlocalizedName());
		GameRegistry.registerItem(recycled_explosive, recycled_explosive.getUnlocalizedName());
		GameRegistry.registerItem(recycled_electronic, recycled_electronic.getUnlocalizedName());
		GameRegistry.registerItem(recycled_nuclear, recycled_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(recycled_misc, recycled_misc.getUnlocalizedName());
		
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
		GameRegistry.registerItem(blades_desh, blades_desh.getUnlocalizedName());
		
		//Generator Stuff
		GameRegistry.registerItem(thermo_element, thermo_element.getUnlocalizedName());
		GameRegistry.registerItem(limiter, limiter.getUnlocalizedName());
		
		//AMS Components
		GameRegistry.registerItem(ams_focus_blank, ams_focus_blank.getUnlocalizedName());
		GameRegistry.registerItem(ams_focus_limiter, ams_focus_limiter.getUnlocalizedName());
		GameRegistry.registerItem(ams_focus_booster, ams_focus_booster.getUnlocalizedName());
		GameRegistry.registerItem(ams_muzzle, ams_muzzle.getUnlocalizedName());
		GameRegistry.registerItem(ams_lens, ams_lens.getUnlocalizedName());
		GameRegistry.registerItem(ams_core_sing, ams_core_sing.getUnlocalizedName());
		GameRegistry.registerItem(ams_core_wormhole, ams_core_wormhole.getUnlocalizedName());
		GameRegistry.registerItem(ams_core_eyeofharmony, ams_core_eyeofharmony.getUnlocalizedName());
		GameRegistry.registerItem(ams_core_thingy, ams_core_thingy.getUnlocalizedName());
		
		//Fusion Shields
		GameRegistry.registerItem(fusion_shield_tungsten, fusion_shield_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(fusion_shield_desh, fusion_shield_desh.getUnlocalizedName());
		GameRegistry.registerItem(fusion_shield_chlorophyte, fusion_shield_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(fusion_shield_vaporwave, fusion_shield_vaporwave.getUnlocalizedName());
		
		//Fuel Rods
		GameRegistry.registerItem(rod_empty, rod_empty.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_empty, rod_dual_empty.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_empty, rod_quad_empty.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_uranium, rod_uranium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_uranium, rod_dual_uranium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_uranium, rod_quad_uranium.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_th232, rod_th232.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_th232, rod_dual_th232.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_th232, rod_quad_th232.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_u233, rod_u233.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_u233, rod_dual_u233.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_u233, rod_quad_u233.getUnlocalizedName());
		
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
		
		GameRegistry.registerItem(rod_polonium, rod_polonium.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_polonium, rod_dual_polonium.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_polonium, rod_quad_polonium.getUnlocalizedName());
		
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
		
		GameRegistry.registerItem(rod_thorium_fuel, rod_thorium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_thorium_fuel, rod_dual_thorium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_thorium_fuel, rod_quad_thorium_fuel.getUnlocalizedName());
		
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
		
		GameRegistry.registerItem(rod_balefire, rod_balefire.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_balefire, rod_dual_balefire.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_balefire, rod_quad_balefire.getUnlocalizedName());
		
		GameRegistry.registerItem(rod_balefire_blazing, rod_balefire_blazing.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_balefire_blazing, rod_dual_balefire_blazing.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_balefire_blazing, rod_quad_balefire_blazing.getUnlocalizedName());

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

		GameRegistry.registerItem(rod_thorium_fuel_depleted, rod_thorium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_thorium_fuel_depleted, rod_dual_thorium_fuel_depleted.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_thorium_fuel_depleted, rod_quad_thorium_fuel_depleted.getUnlocalizedName());
		
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
		GameRegistry.registerItem(waste_uranium_hot, waste_uranium_hot.getUnlocalizedName());
		GameRegistry.registerItem(waste_thorium_hot, waste_thorium_hot.getUnlocalizedName());
		GameRegistry.registerItem(waste_plutonium_hot, waste_plutonium_hot.getUnlocalizedName());
		GameRegistry.registerItem(waste_mox_hot, waste_mox_hot.getUnlocalizedName());
		GameRegistry.registerItem(waste_schrabidium_hot, waste_schrabidium_hot.getUnlocalizedName());
		GameRegistry.registerItem(waste_uranium, waste_uranium.getUnlocalizedName());
		GameRegistry.registerItem(waste_thorium, waste_thorium.getUnlocalizedName());
		GameRegistry.registerItem(waste_plutonium, waste_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(waste_mox, waste_mox.getUnlocalizedName());
		GameRegistry.registerItem(waste_schrabidium, waste_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(trinitite, trinitite.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_long, nuclear_waste_long.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_long_tiny, nuclear_waste_long_tiny.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_short, nuclear_waste_short.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_short_tiny, nuclear_waste_short_tiny.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_long_depleted, nuclear_waste_long_depleted.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_long_depleted_tiny, nuclear_waste_long_depleted_tiny.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_short_depleted, nuclear_waste_short_depleted.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_short_depleted_tiny, nuclear_waste_short_depleted_tiny.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste, nuclear_waste.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_tiny, nuclear_waste_tiny.getUnlocalizedName());
		
		//Spawners
		GameRegistry.registerItem(spawn_chopper, spawn_chopper.getUnlocalizedName());
		GameRegistry.registerItem(spawn_worm, spawn_worm.getUnlocalizedName());
		
		//Computer Tools
		GameRegistry.registerItem(designator, designator.getUnlocalizedName());
		GameRegistry.registerItem(designator_range, designator_range.getUnlocalizedName());
		GameRegistry.registerItem(designator_manual, designator_manual.getUnlocalizedName());
		GameRegistry.registerItem(turret_control, turret_control.getUnlocalizedName());
		GameRegistry.registerItem(turret_chip, turret_chip.getUnlocalizedName());
		GameRegistry.registerItem(turret_biometry, turret_biometry.getUnlocalizedName());
		GameRegistry.registerItem(linker, linker.getUnlocalizedName());
		GameRegistry.registerItem(reactor_sensor, reactor_sensor.getUnlocalizedName());
		GameRegistry.registerItem(oil_detector, oil_detector.getUnlocalizedName());
		GameRegistry.registerItem(survey_scanner, survey_scanner.getUnlocalizedName());
		GameRegistry.registerItem(mirror_tool, mirror_tool.getUnlocalizedName());
		GameRegistry.registerItem(geiger_counter, geiger_counter.getUnlocalizedName());
		GameRegistry.registerItem(digamma_diagnostic, digamma_diagnostic.getUnlocalizedName());
		GameRegistry.registerItem(containment_box, containment_box.getUnlocalizedName());
		
		//Keys and Locks
		GameRegistry.registerItem(key, key.getUnlocalizedName());
		GameRegistry.registerItem(key_red, key_red.getUnlocalizedName());
		GameRegistry.registerItem(key_kit, key_kit.getUnlocalizedName());
		GameRegistry.registerItem(key_fake, key_fake.getUnlocalizedName());
		GameRegistry.registerItem(mech_key, mech_key.getUnlocalizedName());
		GameRegistry.registerItem(pin, pin.getUnlocalizedName());
		GameRegistry.registerItem(padlock_rusty, padlock_rusty.getUnlocalizedName());
		GameRegistry.registerItem(padlock, padlock.getUnlocalizedName());
		GameRegistry.registerItem(padlock_reinforced, padlock_reinforced.getUnlocalizedName());
		GameRegistry.registerItem(padlock_unbreakable, padlock_unbreakable.getUnlocalizedName());
		
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
		GameRegistry.registerItem(missile_emp_strong, missile_emp_strong.getUnlocalizedName());
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
		GameRegistry.registerItem(missile_soyuz, missile_soyuz.getUnlocalizedName());
		GameRegistry.registerItem(missile_soyuz_lander, missile_soyuz_lander.getUnlocalizedName());
		GameRegistry.registerItem(missile_custom, missile_custom.getUnlocalizedName());
		
		//Missile Parts
		GameRegistry.registerItem(mp_thruster_10_kerosene, mp_thruster_10_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_10_kerosene_tec, mp_thruster_10_kerosene_tec.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_10_solid, mp_thruster_10_solid.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_10_xenon, mp_thruster_10_xenon.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_kerosene, mp_thruster_15_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_kerosene_tec, mp_thruster_15_kerosene_tec.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_kerosene_dual, mp_thruster_15_kerosene_dual.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_kerosene_triple, mp_thruster_15_kerosene_triple.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_solid, mp_thruster_15_solid.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_solid_hexdecuple, mp_thruster_15_solid_hexdecuple.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_hydrogen, mp_thruster_15_hydrogen.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_hydrogen_dual, mp_thruster_15_hydrogen_dual.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_balefire_short, mp_thruster_15_balefire_short.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_balefire, mp_thruster_15_balefire.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_balefire_large, mp_thruster_15_balefire_large.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_15_balefire_large_rad, mp_thruster_15_balefire_large_rad.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_20_kerosene, mp_thruster_20_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_20_kerosene_dual, mp_thruster_20_kerosene_dual.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_20_kerosene_triple, mp_thruster_20_kerosene_triple.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_20_solid, mp_thruster_20_solid.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_20_solid_multi, mp_thruster_20_solid_multi.getUnlocalizedName());
		GameRegistry.registerItem(mp_thruster_20_solid_multier, mp_thruster_20_solid_multier.getUnlocalizedName());
		GameRegistry.registerItem(mp_stability_10_flat, mp_stability_10_flat.getUnlocalizedName());
		GameRegistry.registerItem(mp_stability_10_cruise, mp_stability_10_cruise.getUnlocalizedName());
		GameRegistry.registerItem(mp_stability_10_space, mp_stability_10_space.getUnlocalizedName());
		GameRegistry.registerItem(mp_stability_15_flat, mp_stability_15_flat.getUnlocalizedName());
		GameRegistry.registerItem(mp_stability_15_thin, mp_stability_15_thin.getUnlocalizedName());
		GameRegistry.registerItem(mp_stability_15_soyuz, mp_stability_15_soyuz.getUnlocalizedName());
		GameRegistry.registerItem(mp_stability_20_flat, mp_stability_20_flat.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene, mp_fuselage_10_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene_camo, mp_fuselage_10_kerosene_camo.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene_desert, mp_fuselage_10_kerosene_desert.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene_sky, mp_fuselage_10_kerosene_sky.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene_flames, mp_fuselage_10_kerosene_flames.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene_insulation, mp_fuselage_10_kerosene_insulation.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene_sleek, mp_fuselage_10_kerosene_sleek.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene_metal, mp_fuselage_10_kerosene_metal.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_kerosene_taint, mp_fuselage_10_kerosene_taint.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid, mp_fuselage_10_solid.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid_flames, mp_fuselage_10_solid_flames.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid_insulation, mp_fuselage_10_solid_insulation.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid_sleek, mp_fuselage_10_solid_sleek.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid_soviet_glory, mp_fuselage_10_solid_soviet_glory.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid_cathedral, mp_fuselage_10_solid_cathedral.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid_moonlit, mp_fuselage_10_solid_moonlit.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid_battery, mp_fuselage_10_solid_battery.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_solid_duracell, mp_fuselage_10_solid_duracell.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_xenon, mp_fuselage_10_xenon.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_xenon_bhole, mp_fuselage_10_xenon_bhole.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene, mp_fuselage_10_long_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_camo, mp_fuselage_10_long_kerosene_camo.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_desert, mp_fuselage_10_long_kerosene_desert.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_sky, mp_fuselage_10_long_kerosene_sky.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_flames, mp_fuselage_10_long_kerosene_flames.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_insulation, mp_fuselage_10_long_kerosene_insulation.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_sleek, mp_fuselage_10_long_kerosene_sleek.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_metal, mp_fuselage_10_long_kerosene_metal.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_dash, mp_fuselage_10_long_kerosene_dash.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_taint, mp_fuselage_10_long_kerosene_taint.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_kerosene_vap, mp_fuselage_10_long_kerosene_vap.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_solid, mp_fuselage_10_long_solid.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_solid_flames, mp_fuselage_10_long_solid_flames.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_solid_insulation, mp_fuselage_10_long_solid_insulation.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_solid_sleek, mp_fuselage_10_long_solid_sleek.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_solid_soviet_glory, mp_fuselage_10_long_solid_soviet_glory.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_solid_bullet, mp_fuselage_10_long_solid_bullet.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_long_solid_silvermoonlight, mp_fuselage_10_long_solid_silvermoonlight.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_15_kerosene, mp_fuselage_10_15_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_15_solid, mp_fuselage_10_15_solid.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_15_hydrogen, mp_fuselage_10_15_hydrogen.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_10_15_balefire, mp_fuselage_10_15_balefire.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene, mp_fuselage_15_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_camo, mp_fuselage_15_kerosene_camo.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_desert, mp_fuselage_15_kerosene_desert.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_sky, mp_fuselage_15_kerosene_sky.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_insulation, mp_fuselage_15_kerosene_insulation.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_metal, mp_fuselage_15_kerosene_metal.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_decorated, mp_fuselage_15_kerosene_decorated.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_steampunk, mp_fuselage_15_kerosene_steampunk.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_polite, mp_fuselage_15_kerosene_polite.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_blackjack, mp_fuselage_15_kerosene_blackjack.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_lambda, mp_fuselage_15_kerosene_lambda.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_minuteman, mp_fuselage_15_kerosene_minuteman.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_pip, mp_fuselage_15_kerosene_pip.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_taint, mp_fuselage_15_kerosene_taint.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_kerosene_yuck, mp_fuselage_15_kerosene_yuck.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid, mp_fuselage_15_solid.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_insulation, mp_fuselage_15_solid_insulation.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_desh, mp_fuselage_15_solid_desh.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_soviet_glory, mp_fuselage_15_solid_soviet_glory.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_soviet_stank, mp_fuselage_15_solid_soviet_stank.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_faust, mp_fuselage_15_solid_faust.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_silvermoonlight, mp_fuselage_15_solid_silvermoonlight.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_snowy, mp_fuselage_15_solid_snowy.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_panorama, mp_fuselage_15_solid_panorama.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_solid_roses, mp_fuselage_15_solid_roses.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_hydrogen, mp_fuselage_15_hydrogen.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_hydrogen_cathedral, mp_fuselage_15_hydrogen_cathedral.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_balefire, mp_fuselage_15_balefire.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_20_kerosene, mp_fuselage_15_20_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_20_kerosene_magnusson, mp_fuselage_15_20_kerosene_magnusson.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_15_20_solid, mp_fuselage_15_20_solid.getUnlocalizedName());
		GameRegistry.registerItem(mp_fuselage_20_kerosene, mp_fuselage_20_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_10_he, mp_warhead_10_he.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_10_incendiary, mp_warhead_10_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_10_buster, mp_warhead_10_buster.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_10_nuclear, mp_warhead_10_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_10_nuclear_large, mp_warhead_10_nuclear_large.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_10_taint, mp_warhead_10_taint.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_10_cloud, mp_warhead_10_cloud.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_15_he, mp_warhead_15_he.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_15_incendiary, mp_warhead_15_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_15_nuclear, mp_warhead_15_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_15_nuclear_shark, mp_warhead_15_nuclear_shark.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_15_boxcar, mp_warhead_15_boxcar.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_15_n2, mp_warhead_15_n2.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_15_balefire, mp_warhead_15_balefire.getUnlocalizedName());
		GameRegistry.registerItem(mp_warhead_20_he, mp_warhead_20_he.getUnlocalizedName());
		GameRegistry.registerItem(mp_chip_1, mp_chip_1.getUnlocalizedName());
		GameRegistry.registerItem(mp_chip_2, mp_chip_2.getUnlocalizedName());
		GameRegistry.registerItem(mp_chip_3, mp_chip_3.getUnlocalizedName());
		GameRegistry.registerItem(mp_chip_4, mp_chip_4.getUnlocalizedName());
		GameRegistry.registerItem(mp_chip_5, mp_chip_5.getUnlocalizedName());

		/*GameRegistry.registerItem(missile_skin_camo, missile_skin_camo.getUnlocalizedName());
		GameRegistry.registerItem(missile_skin_desert, missile_skin_desert.getUnlocalizedName());
		GameRegistry.registerItem(missile_skin_flames, missile_skin_flames.getUnlocalizedName());
		GameRegistry.registerItem(missile_skin_manly_pink, missile_skin_manly_pink.getUnlocalizedName());
		GameRegistry.registerItem(missile_skin_orange_insulation, missile_skin_orange_insulation.getUnlocalizedName());
		GameRegistry.registerItem(missile_skin_sleek, missile_skin_sleek.getUnlocalizedName());
		GameRegistry.registerItem(missile_skin_soviet_glory, missile_skin_soviet_glory.getUnlocalizedName());
		GameRegistry.registerItem(missile_skin_soviet_stank, missile_skin_soviet_stank.getUnlocalizedName());
		GameRegistry.registerItem(missile_skin_metal, missile_skin_metal.getUnlocalizedName());*/
		
		//Satellites
		GameRegistry.registerItem(sat_mapper, sat_mapper.getUnlocalizedName());
		GameRegistry.registerItem(sat_scanner, sat_scanner.getUnlocalizedName());
		GameRegistry.registerItem(sat_radar, sat_radar.getUnlocalizedName());
		GameRegistry.registerItem(sat_laser, sat_laser.getUnlocalizedName());
		GameRegistry.registerItem(sat_foeq, sat_foeq.getUnlocalizedName());
		GameRegistry.registerItem(sat_resonator, sat_resonator.getUnlocalizedName());
		GameRegistry.registerItem(sat_miner, sat_miner.getUnlocalizedName());
		GameRegistry.registerItem(sat_gerald, sat_gerald.getUnlocalizedName());
		GameRegistry.registerItem(sat_chip, sat_chip.getUnlocalizedName());
		GameRegistry.registerItem(sat_interface, sat_interface.getUnlocalizedName());
		GameRegistry.registerItem(sat_coord, sat_coord.getUnlocalizedName());
		
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
		GameRegistry.registerItem(gun_revolver_nopip, gun_revolver_nopip.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_blackjack, gun_revolver_blackjack.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_silver, gun_revolver_silver.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_red, gun_revolver_red.getUnlocalizedName());
		GameRegistry.registerItem(gun_deagle, gun_deagle.getUnlocalizedName());
		GameRegistry.registerItem(gun_flechette, gun_flechette.getUnlocalizedName());
		GameRegistry.registerItem(gun_calamity, gun_calamity.getUnlocalizedName());
		GameRegistry.registerItem(gun_calamity_dual, gun_calamity_dual.getUnlocalizedName());
		GameRegistry.registerItem(gun_minigun, gun_minigun.getUnlocalizedName());
		GameRegistry.registerItem(gun_avenger, gun_avenger.getUnlocalizedName());
		GameRegistry.registerItem(gun_lacunae, gun_lacunae.getUnlocalizedName());
		GameRegistry.registerItem(gun_folly, gun_folly.getUnlocalizedName());
		GameRegistry.registerItem(gun_b92, gun_b92.getUnlocalizedName());
		GameRegistry.registerItem(gun_b93, gun_b93.getUnlocalizedName());
		GameRegistry.registerItem(gun_rpg, gun_rpg.getUnlocalizedName());
		GameRegistry.registerItem(gun_karl, gun_karl.getUnlocalizedName());
		GameRegistry.registerItem(gun_panzerschreck, gun_panzerschreck.getUnlocalizedName());
		GameRegistry.registerItem(gun_quadro, gun_quadro.getUnlocalizedName());
		GameRegistry.registerItem(gun_hk69, gun_hk69.getUnlocalizedName());
		GameRegistry.registerItem(gun_stinger, gun_stinger.getUnlocalizedName());
		GameRegistry.registerItem(gun_fatman, gun_fatman.getUnlocalizedName());
		GameRegistry.registerItem(gun_proto, gun_proto.getUnlocalizedName());
		GameRegistry.registerItem(gun_mirv, gun_mirv.getUnlocalizedName());
		GameRegistry.registerItem(gun_bf, gun_bf.getUnlocalizedName());
		GameRegistry.registerItem(gun_mp40, gun_mp40.getUnlocalizedName());
		GameRegistry.registerItem(gun_thompson, gun_thompson.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi, gun_uzi.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi_silencer,gun_uzi_silencer.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi_saturnite, gun_uzi_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi_saturnite_silencer,gun_uzi_saturnite_silencer.getUnlocalizedName());
		GameRegistry.registerItem(gun_uboinik, gun_uboinik.getUnlocalizedName());
		GameRegistry.registerItem(gun_supershotgun, gun_supershotgun.getUnlocalizedName());
		GameRegistry.registerItem(gun_ks23, gun_ks23.getUnlocalizedName());
		GameRegistry.registerItem(gun_sauer, gun_sauer.getUnlocalizedName());
		GameRegistry.registerItem(gun_lever_action, gun_lever_action.getUnlocalizedName());
		GameRegistry.registerItem(gun_lever_action_dark, gun_lever_action_dark.getUnlocalizedName());
		GameRegistry.registerItem(gun_lever_action_sonata, gun_lever_action_sonata.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolt_action, gun_bolt_action.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolt_action_green, gun_bolt_action_green.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolt_action_saturnite, gun_bolt_action_saturnite.getUnlocalizedName());
		GameRegistry.registerItem(gun_mymy, gun_mymy.getUnlocalizedName());
		GameRegistry.registerItem(gun_xvl1456, gun_xvl1456.getUnlocalizedName());
		GameRegistry.registerItem(gun_osipr, gun_osipr.getUnlocalizedName());
		GameRegistry.registerItem(gun_immolator, gun_immolator.getUnlocalizedName());
		GameRegistry.registerItem(gun_flamer, gun_flamer.getUnlocalizedName());
		GameRegistry.registerItem(gun_cryolator, gun_cryolator.getUnlocalizedName());
		GameRegistry.registerItem(gun_mp, gun_mp.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolter, gun_bolter.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolter_digamma, gun_bolter_digamma.getUnlocalizedName());
		GameRegistry.registerItem(gun_brimstone, gun_brimstone.getUnlocalizedName());
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
		GameRegistry.registerItem(gun_vortex, gun_vortex.getUnlocalizedName());
		GameRegistry.registerItem(gun_super_shotgun, gun_super_shotgun.getUnlocalizedName());
		GameRegistry.registerItem(gun_moist_nugget, gun_moist_nugget.getUnlocalizedName());
		GameRegistry.registerItem(gun_dampfmaschine, gun_dampfmaschine.getUnlocalizedName());
		GameRegistry.registerItem(gun_darter, gun_darter.getUnlocalizedName());
		GameRegistry.registerItem(crucible, crucible.getUnlocalizedName());
		
		//Ammo
		GameRegistry.registerItem(gun_revolver_iron_ammo, gun_revolver_iron_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_ammo, gun_revolver_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_gold_ammo, gun_revolver_gold_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_lead_ammo, gun_revolver_lead_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_schrabidium_ammo, gun_revolver_schrabidium_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_cursed_ammo, gun_revolver_cursed_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_nightmare_ammo, gun_revolver_nightmare_ammo.getUnlocalizedName());
		GameRegistry.registerItem(ammo_357_desh, ammo_357_desh.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_nightmare2_ammo, gun_revolver_nightmare2_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_pip_ammo, gun_revolver_pip_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_nopip_ammo, gun_revolver_nopip_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_calamity_ammo, gun_calamity_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_lacunae_ammo, gun_lacunae_ammo.getUnlocalizedName());
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
		GameRegistry.registerItem(gun_emp_ammo, gun_emp_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_jack_ammo, gun_jack_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_spark_ammo, gun_spark_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_hp_ammo, gun_hp_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_defabricator_ammo, gun_defabricator_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_euthanasia_ammo, gun_euthanasia_ammo.getUnlocalizedName());

		GameRegistry.registerItem(ammo_12gauge, ammo_12gauge.getUnlocalizedName());
		GameRegistry.registerItem(ammo_12gauge_incendiary, ammo_12gauge_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ammo_12gauge_shrapnel, ammo_12gauge_shrapnel.getUnlocalizedName());
		GameRegistry.registerItem(ammo_12gauge_du, ammo_12gauge_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_12gauge_sleek, ammo_12gauge_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ammo_12gauge_marauder, ammo_12gauge_marauder.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge, ammo_20gauge.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_slug, ammo_20gauge_slug.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_flechette, ammo_20gauge_flechette.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_incendiary, ammo_20gauge_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_shrapnel, ammo_20gauge_shrapnel.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_explosive, ammo_20gauge_explosive.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_caustic, ammo_20gauge_caustic.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_shock, ammo_20gauge_shock.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_wither, ammo_20gauge_wither.getUnlocalizedName());
		GameRegistry.registerItem(ammo_20gauge_sleek, ammo_20gauge_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge, ammo_4gauge.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_slug, ammo_4gauge_slug.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_flechette, ammo_4gauge_flechette.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_flechette_phosphorus, ammo_4gauge_flechette_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_explosive, ammo_4gauge_explosive.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_semtex, ammo_4gauge_semtex.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_balefire, ammo_4gauge_balefire.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_kampf, ammo_4gauge_kampf.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_canister, ammo_4gauge_canister.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_claw, ammo_4gauge_claw.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_vampire, ammo_4gauge_vampire.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_void, ammo_4gauge_void.getUnlocalizedName());
		GameRegistry.registerItem(ammo_4gauge_sleek, ammo_4gauge_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44, ammo_44.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_ap, ammo_44_ap.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_du, ammo_44_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_phosphorus, ammo_44_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_star, ammo_44_star.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_chlorophyte, ammo_44_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_pip, ammo_44_pip.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_bj, ammo_44_bj.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_silver, ammo_44_silver.getUnlocalizedName());
		GameRegistry.registerItem(ammo_44_rocket, ammo_44_rocket.getUnlocalizedName());
		GameRegistry.registerItem(ammo_5mm, ammo_5mm.getUnlocalizedName());
		GameRegistry.registerItem(ammo_5mm_explosive, ammo_5mm_explosive.getUnlocalizedName());
		GameRegistry.registerItem(ammo_5mm_du, ammo_5mm_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_5mm_star, ammo_5mm_star.getUnlocalizedName());
		GameRegistry.registerItem(ammo_5mm_chlorophyte, ammo_5mm_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ammo_9mm, ammo_9mm.getUnlocalizedName());
		GameRegistry.registerItem(ammo_9mm_ap, ammo_9mm_ap.getUnlocalizedName());
		GameRegistry.registerItem(ammo_9mm_du, ammo_9mm_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_9mm_chlorophyte, ammo_9mm_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ammo_9mm_rocket, ammo_9mm_rocket.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556, ammo_556.getUnlocalizedName());
		GameRegistry.registerItem(ammo_566_gold, ammo_566_gold.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_phosphorus, ammo_556_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_ap, ammo_556_ap.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_du, ammo_556_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_star, ammo_556_star.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_chlorophyte, ammo_556_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_sleek, ammo_556_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_tracer, ammo_556_tracer.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_flechette, ammo_556_flechette.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_flechette_incendiary, ammo_556_flechette_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_flechette_phosphorus, ammo_556_flechette_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_flechette_du, ammo_556_flechette_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_flechette_chlorophyte, ammo_556_flechette_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_flechette_sleek, ammo_556_flechette_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ammo_556_k, ammo_556_k.getUnlocalizedName());
		GameRegistry.registerItem(ammo_22lr, ammo_22lr.getUnlocalizedName());
		GameRegistry.registerItem(ammo_22lr_ap, ammo_22lr_ap.getUnlocalizedName());
		GameRegistry.registerItem(ammo_22lr_chlorophyte, ammo_22lr_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50ae, ammo_50ae.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50ae_ap, ammo_50ae_ap.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50ae_du, ammo_50ae_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50ae_star, ammo_50ae_star.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50ae_chlorophyte, ammo_50ae_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg, ammo_50bmg.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg_incendiary, ammo_50bmg_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg_phosphorus, ammo_50bmg_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg_explosive, ammo_50bmg_explosive.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg_ap, ammo_50bmg_ap.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg_du, ammo_50bmg_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg_star, ammo_50bmg_star.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg_chlorophyte, ammo_50bmg_chlorophyte.getUnlocalizedName());
		GameRegistry.registerItem(ammo_50bmg_sleek, ammo_50bmg_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ammo_75bolt, ammo_75bolt.getUnlocalizedName());
		GameRegistry.registerItem(ammo_75bolt_incendiary, ammo_75bolt_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ammo_75bolt_he, ammo_75bolt_he.getUnlocalizedName());
		GameRegistry.registerItem(ammo_fuel, ammo_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ammo_fuel_napalm, ammo_fuel_napalm.getUnlocalizedName());
		GameRegistry.registerItem(ammo_fuel_phosphorus, ammo_fuel_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ammo_fuel_vaporizer, ammo_fuel_vaporizer.getUnlocalizedName());
		GameRegistry.registerItem(ammo_fuel_gas, ammo_fuel_gas.getUnlocalizedName());
		GameRegistry.registerItem(ammo_cell, ammo_cell.getUnlocalizedName());
		GameRegistry.registerItem(ammo_dart, ammo_dart.getUnlocalizedName());
		GameRegistry.registerItem(ammo_dart_nerf, ammo_dart_nerf.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket, ammo_rocket.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_he, ammo_rocket_he.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_incendiary, ammo_rocket_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_phosphorus, ammo_rocket_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_shrapnel, ammo_rocket_shrapnel.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_emp, ammo_rocket_emp.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_glare, ammo_rocket_glare.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_toxic, ammo_rocket_toxic.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_canister, ammo_rocket_canister.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_sleek, ammo_rocket_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_nuclear, ammo_rocket_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ammo_rocket_rpc, ammo_rocket_rpc.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade, ammo_grenade.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_he, ammo_grenade_he.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_incendiary, ammo_grenade_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_phosphorus, ammo_grenade_phosphorus.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_toxic, ammo_grenade_toxic.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_concussion, ammo_grenade_concussion.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_finned, ammo_grenade_finned.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_sleek, ammo_grenade_sleek.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_nuclear, ammo_grenade_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_tracer, ammo_grenade_tracer.getUnlocalizedName());
		GameRegistry.registerItem(ammo_grenade_kampf, ammo_grenade_kampf.getUnlocalizedName());
		GameRegistry.registerItem(ammo_shell, ammo_shell.getUnlocalizedName());
		GameRegistry.registerItem(ammo_shell_explosive, ammo_shell_explosive.getUnlocalizedName());
		GameRegistry.registerItem(ammo_shell_apfsds_t, ammo_shell_apfsds_t.getUnlocalizedName());
		GameRegistry.registerItem(ammo_shell_apfsds_du, ammo_shell_apfsds_du.getUnlocalizedName());
		GameRegistry.registerItem(ammo_shell_w9, ammo_shell_w9.getUnlocalizedName());
		GameRegistry.registerItem(ammo_dgk, ammo_dgk.getUnlocalizedName());
		GameRegistry.registerItem(ammo_nuke, ammo_nuke.getUnlocalizedName());
		GameRegistry.registerItem(ammo_nuke_low, ammo_nuke_low.getUnlocalizedName());
		GameRegistry.registerItem(ammo_nuke_high, ammo_nuke_high.getUnlocalizedName());
		GameRegistry.registerItem(ammo_nuke_tots, ammo_nuke_tots.getUnlocalizedName());
		GameRegistry.registerItem(ammo_nuke_safe, ammo_nuke_safe.getUnlocalizedName());
		GameRegistry.registerItem(ammo_nuke_pumpkin, ammo_nuke_pumpkin.getUnlocalizedName());
		GameRegistry.registerItem(ammo_mirv, ammo_mirv.getUnlocalizedName());
		GameRegistry.registerItem(ammo_mirv_low, ammo_mirv_low.getUnlocalizedName());
		GameRegistry.registerItem(ammo_mirv_high, ammo_mirv_high.getUnlocalizedName());
		GameRegistry.registerItem(ammo_mirv_safe, ammo_mirv_safe.getUnlocalizedName());
		GameRegistry.registerItem(ammo_mirv_special, ammo_mirv_special.getUnlocalizedName());
		GameRegistry.registerItem(ammo_folly, ammo_folly.getUnlocalizedName());
		GameRegistry.registerItem(ammo_folly_nuclear, ammo_folly_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(ammo_folly_du, ammo_folly_du.getUnlocalizedName());
		
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
		GameRegistry.registerItem(clip_revolver_nopip, clip_revolver_nopip.getUnlocalizedName());
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
		GameRegistry.registerItem(grenade_smart, grenade_smart.getUnlocalizedName());
		GameRegistry.registerItem(grenade_mirv, grenade_mirv.getUnlocalizedName());
		GameRegistry.registerItem(grenade_breach, grenade_breach.getUnlocalizedName());
		GameRegistry.registerItem(grenade_burst, grenade_burst.getUnlocalizedName());
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

		GameRegistry.registerItem(grenade_if_generic, grenade_if_generic.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_he, grenade_if_he.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_bouncy, grenade_if_bouncy.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_sticky, grenade_if_sticky.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_impact, grenade_if_impact.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_incendiary, grenade_if_incendiary.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_toxic, grenade_if_toxic.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_concussion, grenade_if_concussion.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_brimstone, grenade_if_brimstone.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_mystery, grenade_if_mystery.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_spark, grenade_if_spark.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_hopwire, grenade_if_hopwire.getUnlocalizedName());
		GameRegistry.registerItem(grenade_if_null, grenade_if_null.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste_pearl, nuclear_waste_pearl.getUnlocalizedName());
		
		GameRegistry.registerItem(ullapool_caber, ullapool_caber.getUnlocalizedName());
		GameRegistry.registerItem(weaponized_starblaster_cell, weaponized_starblaster_cell.getUnlocalizedName());
		
		//Capes
		GameRegistry.registerItem(cape_radiation, cape_radiation.getUnlocalizedName());
		GameRegistry.registerItem(cape_gasmask, cape_gasmask.getUnlocalizedName());
		GameRegistry.registerItem(cape_schrabidium, cape_schrabidium.getUnlocalizedName());
		
		//Tools
		GameRegistry.registerItem(schrabidium_sword, schrabidium_sword.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_hammer, schrabidium_hammer.getUnlocalizedName());
		GameRegistry.registerItem(shimmer_sledge, shimmer_sledge.getUnlocalizedName());
		GameRegistry.registerItem(shimmer_axe, shimmer_axe.getUnlocalizedName());
		//GameRegistry.registerItem(pch, pch.getUnlocalizedName()); //sike, nevermind
		GameRegistry.registerItem(wood_gavel, wood_gavel.getUnlocalizedName());
		GameRegistry.registerItem(lead_gavel, lead_gavel.getUnlocalizedName());
		GameRegistry.registerItem(diamond_gavel, diamond_gavel.getUnlocalizedName());
		GameRegistry.registerItem(mese_gavel, mese_gavel.getUnlocalizedName());
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
		GameRegistry.registerItem(cobalt_sword, cobalt_sword.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_pickaxe, cobalt_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_axe, cobalt_axe.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_shovel, cobalt_shovel.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_hoe, cobalt_hoe.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_decorated_sword, cobalt_decorated_sword.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_decorated_pickaxe, cobalt_decorated_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_decorated_axe, cobalt_decorated_axe.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_decorated_shovel, cobalt_decorated_shovel.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_decorated_hoe, cobalt_decorated_hoe.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_sword, starmetal_sword.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_pickaxe, starmetal_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_axe, starmetal_axe.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_shovel, starmetal_shovel.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_hoe, starmetal_hoe.getUnlocalizedName());
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
		GameRegistry.registerItem(smashing_hammer, smashing_hammer.getUnlocalizedName());
		GameRegistry.registerItem(centri_stick, centri_stick.getUnlocalizedName());
		GameRegistry.registerItem(drax, drax.getUnlocalizedName());
		GameRegistry.registerItem(drax_mk2, drax_mk2.getUnlocalizedName());
		GameRegistry.registerItem(drax_mk3, drax_mk3.getUnlocalizedName());
		GameRegistry.registerItem(chlorophyte_pickaxe, chlorophyte_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(mese_pickaxe, mese_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(matchstick, matchstick.getUnlocalizedName());
		GameRegistry.registerItem(balefire_and_steel, balefire_and_steel.getUnlocalizedName());
		GameRegistry.registerItem(crowbar, crowbar.getUnlocalizedName());
		GameRegistry.registerItem(wrench, wrench.getUnlocalizedName());
		GameRegistry.registerItem(wrench_flipped, wrench_flipped.getUnlocalizedName());
		GameRegistry.registerItem(memespoon, memespoon.getUnlocalizedName());
		GameRegistry.registerItem(saw, saw.getUnlocalizedName());
		GameRegistry.registerItem(bat, bat.getUnlocalizedName());
		GameRegistry.registerItem(bat_nail, bat_nail.getUnlocalizedName());
		GameRegistry.registerItem(golf_club, golf_club.getUnlocalizedName());
		GameRegistry.registerItem(pipe_rusty, pipe_rusty.getUnlocalizedName());
		GameRegistry.registerItem(pipe_lead, pipe_lead.getUnlocalizedName());
		GameRegistry.registerItem(reer_graar, reer_graar.getUnlocalizedName());
		GameRegistry.registerItem(stopsign, stopsign.getUnlocalizedName());
		GameRegistry.registerItem(sopsign, sopsign.getUnlocalizedName());
		GameRegistry.registerItem(chernobylsign, chernobylsign.getUnlocalizedName());

		GameRegistry.registerItem(meteorite_sword, meteorite_sword.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_seared, meteorite_sword_seared.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_reforged, meteorite_sword_reforged.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_hardened, meteorite_sword_hardened.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_alloyed, meteorite_sword_alloyed.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_machined, meteorite_sword_machined.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_treated, meteorite_sword_treated.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_etched, meteorite_sword_etched.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_bred, meteorite_sword_bred.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_irradiated, meteorite_sword_irradiated.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_fused, meteorite_sword_fused.getUnlocalizedName());
		GameRegistry.registerItem(meteorite_sword_baleful, meteorite_sword_baleful.getUnlocalizedName());
		
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
		GameRegistry.registerItem(radaway_strong, radaway_strong.getUnlocalizedName());
		GameRegistry.registerItem(radaway_flush, radaway_flush.getUnlocalizedName());
		GameRegistry.registerItem(radx, radx.getUnlocalizedName());
		GameRegistry.registerItem(pill_iodine, pill_iodine.getUnlocalizedName());
		GameRegistry.registerItem(xanax, xanax.getUnlocalizedName());
		GameRegistry.registerItem(fmn, fmn.getUnlocalizedName());
		GameRegistry.registerItem(five_htp, five_htp.getUnlocalizedName());
		GameRegistry.registerItem(plan_c, plan_c.getUnlocalizedName());
		GameRegistry.registerItem(stealth_boy, stealth_boy.getUnlocalizedName());
		GameRegistry.registerItem(gas_mask_filter, gas_mask_filter.getUnlocalizedName());
		GameRegistry.registerItem(jetpack_tank, jetpack_tank.getUnlocalizedName());
		GameRegistry.registerItem(gun_kit_1, gun_kit_1.getUnlocalizedName());
		GameRegistry.registerItem(gun_kit_2, gun_kit_2.getUnlocalizedName());
		
		//Food
		GameRegistry.registerItem(bomb_waffle, bomb_waffle.getUnlocalizedName());
		GameRegistry.registerItem(schnitzel_vegan, schnitzel_vegan.getUnlocalizedName());
		GameRegistry.registerItem(cotton_candy, cotton_candy.getUnlocalizedName());
		GameRegistry.registerItem(apple_lead, apple_lead.getUnlocalizedName());
		GameRegistry.registerItem(apple_schrabidium, apple_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(tem_flakes, tem_flakes.getUnlocalizedName());
		GameRegistry.registerItem(glowing_stew, glowing_stew.getUnlocalizedName());
		GameRegistry.registerItem(balefire_scrambled, balefire_scrambled.getUnlocalizedName());
		GameRegistry.registerItem(balefire_and_ham, balefire_and_ham.getUnlocalizedName());
		GameRegistry.registerItem(lemon, lemon.getUnlocalizedName());
		GameRegistry.registerItem(definitelyfood, definitelyfood.getUnlocalizedName());
		GameRegistry.registerItem(loops, loops.getUnlocalizedName());
		GameRegistry.registerItem(loop_stew, loop_stew.getUnlocalizedName());
		GameRegistry.registerItem(spongebob_macaroni, spongebob_macaroni.getUnlocalizedName());
		GameRegistry.registerItem(fooditem, fooditem.getUnlocalizedName());
		GameRegistry.registerItem(twinkie, twinkie.getUnlocalizedName());
		GameRegistry.registerItem(static_sandwich, static_sandwich.getUnlocalizedName());
		GameRegistry.registerItem(pudding, pudding.getUnlocalizedName());
		GameRegistry.registerItem(pancake, pancake.getUnlocalizedName());
		GameRegistry.registerItem(nugget, nugget.getUnlocalizedName());
		GameRegistry.registerItem(peas, peas.getUnlocalizedName());
		GameRegistry.registerItem(med_ipecac, med_ipecac.getUnlocalizedName());
		GameRegistry.registerItem(med_ptsd, med_ptsd.getUnlocalizedName());
		GameRegistry.registerItem(canteen_13, canteen_13.getUnlocalizedName());
		GameRegistry.registerItem(canteen_vodka, canteen_vodka.getUnlocalizedName());
		GameRegistry.registerItem(canteen_fab, canteen_fab.getUnlocalizedName());
		
		//Energy Drinks
		GameRegistry.registerItem(can_empty, can_empty.getUnlocalizedName());
		GameRegistry.registerItem(can_smart, can_smart.getUnlocalizedName());
		GameRegistry.registerItem(can_creature, can_creature.getUnlocalizedName());
		GameRegistry.registerItem(can_redbomb, can_redbomb.getUnlocalizedName());
		GameRegistry.registerItem(can_mrsugar, can_mrsugar.getUnlocalizedName());
		GameRegistry.registerItem(can_overcharge, can_overcharge.getUnlocalizedName());
		GameRegistry.registerItem(can_luna, can_luna.getUnlocalizedName());
		GameRegistry.registerItem(can_bepis, can_bepis.getUnlocalizedName());
		GameRegistry.registerItem(can_breen, can_breen.getUnlocalizedName());
		
		//Cola
		GameRegistry.registerItem(bottle_empty, bottle_empty.getUnlocalizedName());
		GameRegistry.registerItem(bottle_nuka, bottle_nuka.getUnlocalizedName());
		GameRegistry.registerItem(bottle_cherry, bottle_cherry.getUnlocalizedName());
		GameRegistry.registerItem(bottle_quantum, bottle_quantum.getUnlocalizedName());
		GameRegistry.registerItem(bottle_sparkle, bottle_sparkle.getUnlocalizedName());
		GameRegistry.registerItem(bottle_rad, bottle_rad.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_empty, bottle2_empty.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_korl, bottle2_korl.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_fritz, bottle2_fritz.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_korl_special, bottle2_korl_special.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_fritz_special, bottle2_fritz_special.getUnlocalizedName());
		GameRegistry.registerItem(bottle2_sunset, bottle2_sunset.getUnlocalizedName());
		GameRegistry.registerItem(bottle_opener, bottle_opener.getUnlocalizedName());
		
		//Canned Food
		GameRegistry.registerItem(canned_beef, canned_beef.getUnlocalizedName());
		GameRegistry.registerItem(canned_tuna, canned_tuna.getUnlocalizedName());
		GameRegistry.registerItem(canned_mystery, canned_mystery.getUnlocalizedName());
		GameRegistry.registerItem(canned_pashtet, canned_pashtet.getUnlocalizedName());
		GameRegistry.registerItem(canned_cheese, canned_cheese.getUnlocalizedName());
		GameRegistry.registerItem(canned_jizz, canned_jizz.getUnlocalizedName());
		GameRegistry.registerItem(canned_milk, canned_milk.getUnlocalizedName());
		GameRegistry.registerItem(canned_ass, canned_ass.getUnlocalizedName());
		GameRegistry.registerItem(canned_pizza, canned_pizza.getUnlocalizedName());
		GameRegistry.registerItem(canned_tube, canned_tube.getUnlocalizedName());
		GameRegistry.registerItem(canned_tomato, canned_tomato.getUnlocalizedName());
		GameRegistry.registerItem(canned_asbestos, canned_asbestos.getUnlocalizedName());
		GameRegistry.registerItem(canned_bhole, canned_bhole.getUnlocalizedName());
		GameRegistry.registerItem(canned_hotdogs, canned_hotdogs.getUnlocalizedName());
		GameRegistry.registerItem(canned_leftovers, canned_leftovers.getUnlocalizedName());
		GameRegistry.registerItem(canned_yogurt, canned_yogurt.getUnlocalizedName());
		GameRegistry.registerItem(canned_stew, canned_stew.getUnlocalizedName());
		GameRegistry.registerItem(canned_chinese, canned_chinese.getUnlocalizedName());
		GameRegistry.registerItem(canned_oil, canned_oil.getUnlocalizedName());
		GameRegistry.registerItem(canned_fist, canned_fist.getUnlocalizedName());
		GameRegistry.registerItem(canned_spam, canned_spam.getUnlocalizedName());
		GameRegistry.registerItem(canned_fried, canned_fried.getUnlocalizedName());
		GameRegistry.registerItem(canned_napalm, canned_napalm.getUnlocalizedName());
		GameRegistry.registerItem(canned_diesel, canned_diesel.getUnlocalizedName());
		GameRegistry.registerItem(canned_kerosene, canned_kerosene.getUnlocalizedName());
		GameRegistry.registerItem(canned_recursion, canned_recursion.getUnlocalizedName());
		GameRegistry.registerItem(canned_bark, canned_bark.getUnlocalizedName());
		
		//Money
		GameRegistry.registerItem(cap_nuka, cap_nuka.getUnlocalizedName());
		GameRegistry.registerItem(cap_quantum, cap_quantum.getUnlocalizedName());
		GameRegistry.registerItem(cap_sparkle, cap_sparkle.getUnlocalizedName());
		GameRegistry.registerItem(cap_rad, cap_rad.getUnlocalizedName());
		GameRegistry.registerItem(cap_korl, cap_korl.getUnlocalizedName());
		GameRegistry.registerItem(cap_fritz, cap_fritz.getUnlocalizedName());
		GameRegistry.registerItem(cap_sunset, cap_sunset.getUnlocalizedName());
		GameRegistry.registerItem(cap_star, cap_star.getUnlocalizedName());
		GameRegistry.registerItem(ring_pull, ring_pull.getUnlocalizedName());
		GameRegistry.registerItem(can_key, can_key.getUnlocalizedName());
		GameRegistry.registerItem(coin_creeper, coin_creeper.getUnlocalizedName());
		GameRegistry.registerItem(coin_radiation,coin_radiation.getUnlocalizedName());
		GameRegistry.registerItem(coin_maskman, coin_maskman.getUnlocalizedName());
		GameRegistry.registerItem(coin_worm, coin_worm.getUnlocalizedName());
		
		//Chaos
		GameRegistry.registerItem(chocolate_milk, chocolate_milk.getUnlocalizedName());
		GameRegistry.registerItem(cbt_device, cbt_device.getUnlocalizedName());
		
		//Armor mods
		GameRegistry.registerItem(attachment_mask, attachment_mask.getUnlocalizedName());
		GameRegistry.registerItem(back_tesla, back_tesla.getUnlocalizedName());
		GameRegistry.registerItem(servo_set, servo_set.getUnlocalizedName());
		GameRegistry.registerItem(servo_set_desh, servo_set_desh.getUnlocalizedName());
		GameRegistry.registerItem(pads_rubber, pads_rubber.getUnlocalizedName());
		GameRegistry.registerItem(pads_slime, pads_slime.getUnlocalizedName());
		GameRegistry.registerItem(pads_static, pads_static.getUnlocalizedName());
		GameRegistry.registerItem(cladding_paint, cladding_paint.getUnlocalizedName());
		GameRegistry.registerItem(cladding_rubber, cladding_rubber.getUnlocalizedName());
		GameRegistry.registerItem(cladding_lead, cladding_lead.getUnlocalizedName());
		GameRegistry.registerItem(cladding_desh, cladding_desh.getUnlocalizedName());
		GameRegistry.registerItem(cladding_obsidian, cladding_obsidian.getUnlocalizedName());
		GameRegistry.registerItem(insert_kevlar, insert_kevlar.getUnlocalizedName());
		GameRegistry.registerItem(insert_sapi, insert_sapi.getUnlocalizedName());
		GameRegistry.registerItem(insert_esapi, insert_esapi.getUnlocalizedName());
		GameRegistry.registerItem(insert_xsapi, insert_xsapi.getUnlocalizedName());
		GameRegistry.registerItem(insert_steel, insert_steel.getUnlocalizedName());
		GameRegistry.registerItem(insert_du, insert_du.getUnlocalizedName());
		GameRegistry.registerItem(insert_polonium, insert_polonium.getUnlocalizedName());
		GameRegistry.registerItem(insert_era, insert_era.getUnlocalizedName());
		GameRegistry.registerItem(insert_yharonite, insert_yharonite.getUnlocalizedName());
		GameRegistry.registerItem(insert_doxium, insert_doxium.getUnlocalizedName());
		GameRegistry.registerItem(armor_polish, armor_polish.getUnlocalizedName());
		GameRegistry.registerItem(bandaid, bandaid.getUnlocalizedName());
		GameRegistry.registerItem(serum, serum.getUnlocalizedName());
		GameRegistry.registerItem(quartz_plutonium, quartz_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(morning_glory, morning_glory.getUnlocalizedName());
		GameRegistry.registerItem(lodestone, lodestone.getUnlocalizedName());
		GameRegistry.registerItem(horseshoe_magnet, horseshoe_magnet.getUnlocalizedName());
		GameRegistry.registerItem(industrial_magnet, industrial_magnet.getUnlocalizedName());
		GameRegistry.registerItem(bathwater, bathwater.getUnlocalizedName());
		GameRegistry.registerItem(bathwater_mk2, bathwater_mk2.getUnlocalizedName());
		GameRegistry.registerItem(spider_milk, spider_milk.getUnlocalizedName());
		GameRegistry.registerItem(ink, ink.getUnlocalizedName());
		GameRegistry.registerItem(heart_piece, heart_piece.getUnlocalizedName());
		GameRegistry.registerItem(heart_container, heart_container.getUnlocalizedName());
		GameRegistry.registerItem(heart_booster, heart_booster.getUnlocalizedName());
		GameRegistry.registerItem(heart_fab, heart_fab.getUnlocalizedName());
		GameRegistry.registerItem(wd40, wd40.getUnlocalizedName());
		GameRegistry.registerItem(scrumpy, scrumpy.getUnlocalizedName());
		GameRegistry.registerItem(wild_p, wild_p.getUnlocalizedName());
		GameRegistry.registerItem(fabsols_vodka, fabsols_vodka.getUnlocalizedName());

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
		
		//FSTBMB
		GameRegistry.registerItem(egg_balefire_shard, egg_balefire_shard.getUnlocalizedName());
		GameRegistry.registerItem(egg_balefire, egg_balefire.getUnlocalizedName());
		
		//Conventional Armor
		GameRegistry.registerItem(goggles, goggles.getUnlocalizedName());
		GameRegistry.registerItem(gas_mask, gas_mask.getUnlocalizedName());
		GameRegistry.registerItem(gas_mask_m65, gas_mask_m65.getUnlocalizedName());
		//GameRegistry.registerItem(oxy_mask, oxy_mask.getUnlocalizedName());
		GameRegistry.registerItem(hat, hat.getUnlocalizedName());
		GameRegistry.registerItem(beta, beta.getUnlocalizedName());
		
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
		
		//Custom Rods
		GameRegistry.registerItem(custom_tnt, custom_tnt.getUnlocalizedName());
		GameRegistry.registerItem(custom_nuke, custom_nuke.getUnlocalizedName());
		GameRegistry.registerItem(custom_hydro, custom_hydro.getUnlocalizedName());
		GameRegistry.registerItem(custom_amat, custom_amat.getUnlocalizedName());
		GameRegistry.registerItem(custom_dirty, custom_dirty.getUnlocalizedName());
		GameRegistry.registerItem(custom_schrab, custom_schrab.getUnlocalizedName());
		GameRegistry.registerItem(custom_fall, custom_fall.getUnlocalizedName());
		
		//Power Armor
		GameRegistry.registerItem(t45_helmet, t45_helmet.getUnlocalizedName());
		GameRegistry.registerItem(t45_plate, t45_plate.getUnlocalizedName());
		GameRegistry.registerItem(t45_legs, t45_legs.getUnlocalizedName());
		GameRegistry.registerItem(t45_boots, t45_boots.getUnlocalizedName());
		GameRegistry.registerItem(ajr_helmet, ajr_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ajr_plate, ajr_plate.getUnlocalizedName());
		GameRegistry.registerItem(ajr_legs, ajr_legs.getUnlocalizedName());
		GameRegistry.registerItem(ajr_boots, ajr_boots.getUnlocalizedName());
		GameRegistry.registerItem(ajro_helmet, ajro_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ajro_plate, ajro_plate.getUnlocalizedName());
		GameRegistry.registerItem(ajro_legs, ajro_legs.getUnlocalizedName());
		GameRegistry.registerItem(ajro_boots, ajro_boots.getUnlocalizedName());
		GameRegistry.registerItem(bj_helmet, bj_helmet.getUnlocalizedName());
		GameRegistry.registerItem(bj_plate, bj_plate.getUnlocalizedName());
		GameRegistry.registerItem(bj_plate_jetpack, bj_plate_jetpack.getUnlocalizedName());
		GameRegistry.registerItem(bj_legs, bj_legs.getUnlocalizedName());
		GameRegistry.registerItem(bj_boots, bj_boots.getUnlocalizedName());
		GameRegistry.registerItem(hev_helmet, hev_helmet.getUnlocalizedName());
		GameRegistry.registerItem(hev_plate, hev_plate.getUnlocalizedName());
		GameRegistry.registerItem(hev_legs, hev_legs.getUnlocalizedName());
		GameRegistry.registerItem(hev_boots, hev_boots.getUnlocalizedName());
		GameRegistry.registerItem(fau_helmet, fau_helmet.getUnlocalizedName());
		GameRegistry.registerItem(fau_plate, fau_plate.getUnlocalizedName());
		GameRegistry.registerItem(fau_legs, fau_legs.getUnlocalizedName());
		GameRegistry.registerItem(fau_boots, fau_boots.getUnlocalizedName());
		
		//Nobody will ever read this anyway, so it shouldn't matter.
		GameRegistry.registerItem(chainsaw, chainsaw.getUnlocalizedName());
		GameRegistry.registerItem(igniter, igniter.getUnlocalizedName());
		GameRegistry.registerItem(detonator, detonator.getUnlocalizedName());
		GameRegistry.registerItem(detonator_multi, detonator_multi.getUnlocalizedName());
		GameRegistry.registerItem(detonator_laser, detonator_laser.getUnlocalizedName());
		GameRegistry.registerItem(detonator_deadman, detonator_deadman.getUnlocalizedName());
		GameRegistry.registerItem(detonator_de, detonator_de.getUnlocalizedName());
		GameRegistry.registerItem(crate_caller, crate_caller.getUnlocalizedName());
		GameRegistry.registerItem(bomb_caller, bomb_caller.getUnlocalizedName());
		GameRegistry.registerItem(meteor_remote, meteor_remote.getUnlocalizedName());
		GameRegistry.registerItem(defuser, defuser.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_helmet, hazmat_helmet.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_plate, hazmat_plate.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_legs, hazmat_legs.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_boots, hazmat_boots.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_helmet_red, hazmat_helmet_red.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_plate_red, hazmat_plate_red.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_legs_red, hazmat_legs_red.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_boots_red, hazmat_boots_red.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_helmet_grey, hazmat_helmet_grey.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_plate_grey, hazmat_plate_grey.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_legs_grey, hazmat_legs_grey.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_boots_grey, hazmat_boots_grey.getUnlocalizedName());
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
		GameRegistry.registerItem(security_helmet, security_helmet.getUnlocalizedName());
		GameRegistry.registerItem(security_plate, security_plate.getUnlocalizedName());
		GameRegistry.registerItem(security_legs, security_legs.getUnlocalizedName());
		GameRegistry.registerItem(security_boots, security_boots.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_helmet, cobalt_helmet.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_plate, cobalt_plate.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_legs, cobalt_legs.getUnlocalizedName());
		GameRegistry.registerItem(cobalt_boots, cobalt_boots.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_helmet, starmetal_helmet.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_plate, starmetal_plate.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_legs, starmetal_legs.getUnlocalizedName());
		GameRegistry.registerItem(starmetal_boots, starmetal_boots.getUnlocalizedName());
		GameRegistry.registerItem(dnt_helmet, dnt_helmet.getUnlocalizedName());
		GameRegistry.registerItem(dnt_plate, dnt_plate.getUnlocalizedName());
		GameRegistry.registerItem(dnt_legs, dnt_legs.getUnlocalizedName());
		GameRegistry.registerItem(dnt_boots, dnt_boots.getUnlocalizedName());
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
		GameRegistry.registerItem(jetpack_fly, jetpack_fly.getUnlocalizedName());
		GameRegistry.registerItem(jetpack_break, jetpack_break.getUnlocalizedName());
		GameRegistry.registerItem(jetpack_vector, jetpack_vector.getUnlocalizedName());
		GameRegistry.registerItem(jetpack_boost, jetpack_boost.getUnlocalizedName());
		//GameRegistry.registerItem(australium_iv, australium_iv.getUnlocalizedName());
		//GameRegistry.registerItem(australium_v, australium_v.getUnlocalizedName());
		
		//Expensive Ass Shit
		GameRegistry.registerItem(crystal_horn, crystal_horn.getUnlocalizedName());
		GameRegistry.registerItem(crystal_charred, crystal_charred.getUnlocalizedName());
		
		//OP Tools
		GameRegistry.registerItem(wand, wand.getUnlocalizedName());
		GameRegistry.registerItem(wand_s, wand_s.getUnlocalizedName());
		GameRegistry.registerItem(wand_d, wand_d.getUnlocalizedName());
		GameRegistry.registerItem(rod_of_discord, rod_of_discord.getUnlocalizedName());
		//GameRegistry.registerItem(analyzer, analyzer.getUnlocalizedName());
		//GameRegistry.registerItem(remote, remote.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_stopper, euphemium_stopper.getUnlocalizedName());
		GameRegistry.registerItem(polaroid, polaroid.getUnlocalizedName());
		GameRegistry.registerItem(glitch, glitch.getUnlocalizedName());
		GameRegistry.registerItem(book_secret, book_secret.getUnlocalizedName());
		GameRegistry.registerItem(book_of_, book_of_.getUnlocalizedName());
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
		GameRegistry.registerItem(custom_kit, custom_kit.getUnlocalizedName());
		GameRegistry.registerItem(missile_kit, missile_kit.getUnlocalizedName());
		GameRegistry.registerItem(grenade_kit, grenade_kit.getUnlocalizedName());
		GameRegistry.registerItem(t45_kit, t45_kit.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_kit, hazmat_kit.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_red_kit, hazmat_red_kit.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_grey_kit, hazmat_grey_kit.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_kit, euphemium_kit.getUnlocalizedName());
		GameRegistry.registerItem(letter, letter.getUnlocalizedName());
		
		//Misile Loot Boxes
		GameRegistry.registerItem(loot_10, loot_10.getUnlocalizedName());
		GameRegistry.registerItem(loot_15, loot_15.getUnlocalizedName());
		GameRegistry.registerItem(loot_misc, loot_misc.getUnlocalizedName());
		
		//THIS is a bucket.
		GameRegistry.registerItem(bucket_mud, bucket_mud.getUnlocalizedName());
		GameRegistry.registerItem(bucket_acid, bucket_acid.getUnlocalizedName());
		GameRegistry.registerItem(bucket_toxic, bucket_toxic.getUnlocalizedName());
		GameRegistry.registerItem(bucket_schrabidic_acid, bucket_schrabidic_acid.getUnlocalizedName());
		
		//Door Items
		GameRegistry.registerItem(door_metal, door_metal.getUnlocalizedName());
		GameRegistry.registerItem(door_office, door_office.getUnlocalizedName());
		GameRegistry.registerItem(door_bunker, door_bunker.getUnlocalizedName());
		
		//Records
		GameRegistry.registerItem(record_lc, record_lc.getUnlocalizedName());
		GameRegistry.registerItem(record_ss, record_ss.getUnlocalizedName());
		GameRegistry.registerItem(record_vc, record_vc.getUnlocalizedName());
		GameRegistry.registerItem(record_glass, record_glass.getUnlocalizedName());
		
		//wow we're far down the item registry, is this the cellar?
		GameRegistry.registerItem(book_guide, book_guide.getUnlocalizedName());
		
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
		GameRegistry.registerItem(bob_metalworks, bob_metalworks.getUnlocalizedName());
		GameRegistry.registerItem(bob_assembly, bob_assembly.getUnlocalizedName());
		GameRegistry.registerItem(bob_chemistry, bob_chemistry.getUnlocalizedName());
		GameRegistry.registerItem(bob_oil, bob_oil.getUnlocalizedName());
		GameRegistry.registerItem(bob_nuclear, bob_nuclear.getUnlocalizedName());
		GameRegistry.registerItem(digamma_see, digamma_see.getUnlocalizedName());
		GameRegistry.registerItem(digamma_feel, digamma_feel.getUnlocalizedName());
		GameRegistry.registerItem(digamma_know, digamma_know.getUnlocalizedName());
		GameRegistry.registerItem(digamma_kauai_moho, digamma_kauai_moho.getUnlocalizedName());
		GameRegistry.registerItem(digamma_up_on_top, digamma_up_on_top.getUnlocalizedName());
		GameRegistry.registerItem(mysteryshovel, mysteryshovel.getUnlocalizedName());
		GameRegistry.registerItem(memory, memory.getUnlocalizedName());
	}
}
