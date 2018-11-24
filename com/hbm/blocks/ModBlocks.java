package com.hbm.blocks;

import com.hbm.blocks.generic.*;
import com.hbm.blocks.bomb.*;
import com.hbm.blocks.fluid.*;
import com.hbm.blocks.machine.*;
import com.hbm.blocks.test.*;
import com.hbm.items.bomb.ItemPrototypeBlock;
import com.hbm.items.special.ItemOreBlock;
import com.hbm.items.special.ItemPlasmaBlock;
import com.hbm.items.special.ItemSchrabidiumBlock;
import com.hbm.items.special.ItemTaintBlock;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModBlocks {
	
	public static void mainRegistry()
	{
		initializeBlock();
		registerBlock();
	}
	
	public static Block test_render;
	public static Block test_container;
	public static Block test_bomb;
	public static Block test_bomb_advanced;
	public static Block test_nuke;
	public static final int guiID_test_nuke = 2;
	public static Block event_tester;
	public static Block rotation_tester;
	public static Block obj_tester;
	public static Block test_ticker;
	public static Block test_missile;
	
	public static Block ore_uranium;
	public static Block ore_titanium;
	public static Block ore_sulfur;

	public static Block ore_niter;
	public static Block ore_copper;
	public static Block ore_tungsten;
	public static Block ore_aluminium;
	public static Block ore_fluorite;
	public static Block ore_lead;
	public static Block ore_schrabidium;
	public static Block ore_beryllium;

	public static Block ore_nether_uranium;
	public static Block ore_nether_plutonium;
	public static Block ore_nether_tungsten;
	public static Block ore_nether_sulfur;
	public static Block ore_nether_fire;
	public static Block ore_nether_schrabidium;

	public static Block ore_australium;
	public static Block ore_weidanium;
	public static Block ore_reiium;
	public static Block ore_unobtainium;
	public static Block ore_daffergon;
	public static Block ore_verticium;
	public static Block ore_rare;

	public static Block ore_oil;
	public static Block ore_oil_empty;
	public static Block ore_oil_sand;

	public static Block ore_tikite;

	public static Block block_uranium;
	public static Block block_titanium;
	public static Block block_sulfur;
	public static Block block_niter;
	public static Block block_copper;
	public static Block block_red_copper;
	public static Block block_tungsten;
	public static Block block_aluminium;
	public static Block block_fluorite;
	public static Block block_steel;
	public static Block block_lead;
	public static Block block_trinitite;
	public static Block block_waste;
	public static Block block_scrap;
	public static Block block_electrical_scrap;
	public static Block block_beryllium;
	public static Block block_schrabidium;
	public static Block block_advanced_alloy;
	public static Block block_magnetized_tungsten;
	public static Block block_combine_steel;
	public static Block block_desh;
	public static Block block_yellowcake;

	public static Block block_australium;
	public static Block block_weidanium;
	public static Block block_reiium;
	public static Block block_unobtainium;
	public static Block block_daffergon;
	public static Block block_verticium;

	public static Block deco_titanium;
	public static Block deco_red_copper;
	public static Block deco_tungsten;
	public static Block deco_aluminium;
	public static Block deco_steel;
	public static Block deco_lead;
	public static Block deco_beryllium;

	public static Block hazmat;

	public static Block gravel_obsidian;
	public static Block asphalt;
	
	public static Block reinforced_brick;
	public static Block reinforced_glass;
	public static Block reinforced_light;
	public static Block reinforced_sand;
	public static Block reinforced_lamp_off;
	public static Block reinforced_lamp_on;

	public static Block brick_concrete;
	public static Block brick_obsidian;
	public static Block brick_light;

	public static Block cmb_brick;
	public static Block cmb_brick_reinforced;

	public static Block block_meteor;
	public static Block block_meteor_cobble;
	public static Block block_meteor_broken;
	public static Block block_meteor_molten;
	public static Block block_meteor_treasure;

	public static Block tape_recorder;
	public static Block steel_poles;
	public static Block pole_top;
	public static Block pole_satellite_receiver;
	public static Block steel_wall;
	public static Block steel_corner;
	public static Block steel_roof;
	public static Block steel_beam;
	public static Block steel_scaffold;

	public static Block broadcaster_pc;

	public static Block mush;
	public static Block mush_block;
	public static Block mush_block_stem;

	public static Block waste_earth;
	public static Block waste_mycelium;
	public static Block waste_trinitite;
	public static Block waste_trinitite_red;
	public static Block waste_log;
	public static Block waste_planks;
	public static Block frozen_dirt;
	public static Block frozen_grass;
	public static Block frozen_log;
	public static Block frozen_planks;

	public static Block sellafield_slaked;
	public static Block sellafield_0;
	public static Block sellafield_1;
	public static Block sellafield_2;
	public static Block sellafield_3;
	public static Block sellafield_4;
	public static Block sellafield_core;

	public static Block flame_war;
	public static Block float_bomb;
	public static Block therm_endo;
	public static Block therm_exo;
	public static Block emp_bomb;
	public static Block det_cord;
	public static Block det_charge;
	public static Block det_nuke;
	public static Block red_barrel;
	public static Block yellow_barrel;
	public static Block crashed_balefire;
	public static Block rejuvinator;
	public static Block mine_ap;
	public static Block mine_he;
	public static Block mine_shrap;
	public static Block mine_fat;
	
	public static Block crate;
	public static Block crate_weapon;
	public static Block crate_lead;
	public static Block crate_metal;
	public static Block crate_red;

	public static Block boxcar;
	public static Block bomber;

	public static Block seal_frame;
	public static Block seal_controller;
	public static Block seal_hatch;

	public static Block vault_door;
	public static Block blast_door;
	
	public static Block barbed_wire;

	public static Block marker_structure;

	public static Block sat_mapper;
	public static Block sat_scanner;
	public static Block sat_radar;
	public static Block sat_laser;
	public static Block sat_foeq;
	public static Block sat_resonator;
	
	public static Block crate_iron;
	public static final int guiID_crate_iron = 46;
	
	public static Block crate_steel;
	public static final int guiID_crate_steel = 47;
	
	public static Block safe;
	public static final int guiID_safe = 70;
	
	public static Block nuke_gadget;
	public static final int guiID_nuke_gadget = 3;

	public static Block nuke_boy;
	public static final int guiID_nuke_boy = 4;
	
	public static Block nuke_man;
	public static final int guiID_nuke_man = 6;

	public static Block nuke_mike;
	public static final int guiID_nuke_mike = 11;
	
	public static Block nuke_tsar;
	public static final int guiID_nuke_tsar = 12;
	
	public static Block nuke_fleija;
	public static final int guiID_nuke_fleija = 17;
	
	public static Block nuke_prototype;
	public static final int guiID_nuke_prototype = 23;
	
	public static Block nuke_custom;
	public static final int guiID_nuke_custom = 37;
	
	public static Block nuke_solinium;
	public static final int guiID_nuke_solinium = 60;
	
	public static Block nuke_n2;
	public static final int guiID_nuke_n2 = 61;

	public static Block nuke_n45;
	
	public static Block bomb_multi;
	public static final int guiID_bomb_multi = 10;
	
	public static Block cel_prime;
	public static final int guiID_cel_prime = 62;
	public static Block cel_prime_terminal;
	public static Block cel_prime_battery;
	public static Block cel_prime_port;
	public static Block cel_prime_tanks;
	
	public static Block machine_difurnace_off;
	public static Block machine_difurnace_on;
	public static final int guiID_test_difurnace = 1;
	
	public static Block machine_centrifuge;
	public static final int guiID_centrifuge = 5;
	
	public static Block machine_gascent;
	public static final int guiID_gascent = 71;
	
	public static Block machine_uf6_tank;
	public static final int guiID_uf6_tank = 7;
	
	public static Block machine_puf6_tank;
	public static final int guiID_puf6_tank = 8;
	
	public static Block machine_reactor;
	public static final int guiID_reactor = 9;
	
	public static Block machine_nuke_furnace_off;
	public static Block machine_nuke_furnace_on;
	public static final int guiID_nuke_furnace = 13;
	
	public static Block machine_rtg_furnace_off;
	public static Block machine_rtg_furnace_on;
	public static final int guiID_rtg_furnace = 14;

	public static Block machine_generator;
	public static final int guiID_machine_generator = 15;

	public static Block machine_industrial_generator;
	public static final int guiID_machine_industrial_generator = 39;

	public static Block machine_cyclotron;
	public static final int guiID_machine_cyclotron = 41;
	
	public static Block machine_electric_furnace_off;
	public static Block machine_electric_furnace_on;
	public static final int guiID_electric_furnace = 16;

	//public static Block machine_deuterium;
	public static final int guiID_machine_deuterium = 20;

	public static Block machine_battery;
	public static Block machine_lithium_battery;
	public static Block machine_schrabidium_battery;
	public static Block machine_dineutronium_battery;
	public static final int guiID_machine_battery = 21;
	
	public static Block machine_coal_off;
	public static Block machine_coal_on;
	public static final int guiID_machine_coal = 22;

	public static Block red_wire_coated;
	public static Block red_cable;
	public static Block red_pylon;
	public static Block oil_duct_solid;
	public static Block oil_duct;
	public static Block gas_duct_solid;
	public static Block gas_duct;
	public static Block fluid_duct;

	public static Block machine_transformer;
	public static Block machine_transformer_20;
	public static Block machine_transformer_dnt;
	public static Block machine_transformer_dnt_20;

	public static Block bomb_multi_large;
	public static final int guiID_bomb_multi_large = 18;

	public static Block factory_titanium_hull;
	public static Block factory_titanium_furnace;
	public static Block factory_titanium_conductor;
	public static Block factory_titanium_core;
	public static final int guiID_factory_titanium = 24;
	
	public static Block factory_advanced_hull;
	public static Block factory_advanced_furnace;
	public static Block factory_advanced_conductor;
	public static Block factory_advanced_core;
	public static final int guiID_factory_advanced = 25;

	public static Block reactor_element;
	public static Block reactor_control;
	public static Block reactor_hatch;
	public static Block reactor_conductor;
	public static Block reactor_computer;
	public static final int guiID_reactor_multiblock = 26;

	public static Block fusion_conductor;
	public static Block fusion_center;
	public static Block fusion_motor;
	public static Block fusion_heater;
	public static Block fusion_hatch;
	public static Block fusion_core;
	public static Block plasma;
	public static final int guiID_fusion_multiblock = 27;

	public static Block watz_element;
	public static Block watz_control;
	public static Block watz_cooler;
	public static Block watz_end;
	public static Block watz_hatch;
	public static Block watz_conductor;
	public static Block watz_core;
	public static final int guiID_watz_multiblock = 32;

	public static Block fwatz_conductor;
	public static Block fwatz_cooler;
	public static Block fwatz_tank;
	public static Block fwatz_scaffold;
	public static Block fwatz_hatch;
	public static Block fwatz_computer;
	public static Block fwatz_core;
	public static Block fwatz_plasma;
	public static final int guiID_fwatz_multiblock = 33;

	public static Block ams_base;
	public static final int guiID_ams_base = 54;
	public static Block ams_emitter;
	public static final int guiID_ams_emitter = 55;
	public static Block ams_limiter;
	public static final int guiID_ams_limiter = 56;

	public static Block machine_converter_he_rf;
	public static final int guiID_converter_he_rf = 28;

	public static Block machine_converter_rf_he;
	public static final int guiID_converter_rf_he = 29;

	public static Block machine_schrabidium_transmutator;
	public static final int guiID_schrabidium_transmutator = 30;

	public static Block machine_diesel;
	public static final int guiID_machine_diesel = 31;

	public static Block machine_shredder;
	public static final int guiID_machine_shredder = 34;

	public static Block machine_combine_factory;
	public static final int guiID_combine_factory = 35;

	public static Block machine_teleporter;
	public static final int guiID_machine_teleporter = 36;

	public static Block machine_reix_mainframe;
	public static final int guiID_machine_reix_mainframe = 38;

	public static Block machine_rtg_grey;
	public static final int guiID_machine_rtg = 42;
	//public static Block machine_rtg_red;
	//public static Block machine_rtg_orange;
	//public static Block machine_rtg_yellow;
	//public static Block machine_rtg_green;
	public static Block machine_rtg_cyan;
	//public static Block machine_rtg_blue;
	//public static Block machine_rtg_purple;

	public static Block machine_well;
	public static Block oil_pipe;
	public static final int guiID_machine_well = 40;

	public static Block machine_flare;
	public static final int guiID_machine_flare = 44;

	public static Block machine_refinery;
	public static final int guiID_machine_refinery = 43;

	public static Block machine_boiler_off;
	public static Block machine_boiler_on;
	public static final int guiID_machine_boiler = 72;

	public static Block machine_boiler_electric_off;
	public static Block machine_boiler_electric_on;
	public static final int guiID_machine_boiler_electric = 73;

	public static Block machine_turbine;
	public static final int guiID_machine_turbine = 74;

	public static Block machine_drill;
	public static Block drill_pipe;
	public static final int guiID_machine_drill = 45;

	public static Block machine_assembler;
	public static final int guiID_machine_assembler = 48;

	public static Block machine_chemplant;
	public static final int guiID_machine_chemplant = 49;

	public static Block machine_fluidtank;
	public static final int guiID_machine_fluidtank = 50;

	public static Block launch_pad;
	public static final int guiID_launch_pad = 19;

	public static Block machine_radar;
	public static final int guiID_radar = 59;

	public static Block machine_pumpjack;
	public static final int guiID_machine_pumpjack = 51;

	public static Block machine_turbofan;
	public static final int guiID_machine_turbofan = 52;

	public static Block machine_selenium;
	public static final int guiID_machine_selenium = 63;

	public static Block machine_press;
	public static final int guiID_machine_press = 53;

	public static Block machine_siren;
	public static final int guiID_siren = 57;

	public static Block machine_radgen;
	public static final int guiID_radgen = 58;

	public static Block machine_satlinker;
	public static final int guiID_satlinker = 64;

	public static Block machine_telelinker;
	public static final int guiID_telelinker = 68;

	public static Block machine_keyforge;
	public static final int guiID_keyforge = 67;

	public static Block machine_reactor_small;
	public static final int guiID_reactor_small = 65;

	public static Block machine_spp_bottom;
	public static Block machine_spp_top;

	public static Block radiobox;
	public static final int guiID_radiobox = 66;

	public static Block radiorec;
	public static final int guiID_radiorec = 69;

	public static Block turret_light;
	public static Block turret_heavy;
	public static Block turret_rocket;
	public static Block turret_flamer;
	public static Block turret_tau;
	public static Block turret_spitfire;
	public static Block turret_cwis;
	public static Block turret_cheapo;

	public static Block book_guide;

	public static Block rail_highspeed;
	public static Block rail_booster;
	
	public static Block statue_elb;
	public static Block statue_elb_g;
	public static Block statue_elb_w;
	public static Block statue_elb_f;

	public static Block cheater_virus;
	public static Block cheater_virus_seed;
	public static Block crystal_virus;
	public static Block crystal_hardened;
	public static Block crystal_pulsar;
	public static Block taint;
	public static Block residue;

	public static Block vent_chlorine;
	public static Block vent_cloud;
	public static Block vent_pink_cloud;

	public static Block mud_block;
	public static Fluid mud_fluid;
	public static final Material fluidmud = (new MaterialLiquid(MapColor.adobeColor));

	public static Block acid_block;
	public static Fluid acid_fluid;
	public static final Material fluidacid = (new MaterialLiquid(MapColor.purpleColor));

	public static Block toxic_block;
	public static Fluid toxic_fluid;
	public static final Material fluidtoxic = (new MaterialLiquid(MapColor.greenColor));

	public static Block dummy_block_igenerator;
	public static Block dummy_port_igenerator;
	public static Block dummy_block_centrifuge;
	public static Block dummy_block_cyclotron;
	public static Block dummy_port_cyclotron;
	public static Block dummy_block_well;
	public static Block dummy_port_well;
	public static Block dummy_block_flare;
	public static Block dummy_port_flare;
	public static Block dummy_block_drill;
	public static Block dummy_port_drill;
	public static Block dummy_block_assembler;
	public static Block dummy_port_assembler;
	public static Block dummy_block_chemplant;
	public static Block dummy_port_chemplant;
	public static Block dummy_block_fluidtank;
	public static Block dummy_port_fluidtank;
	public static Block dummy_block_refinery;
	public static Block dummy_port_refinery;
	public static Block dummy_block_pumpjack;
	public static Block dummy_port_pumpjack;
	public static Block dummy_block_turbofan;
	public static Block dummy_port_turbofan;
	public static Block dummy_block_ams_limiter;
	public static Block dummy_port_ams_limiter;
	public static Block dummy_block_ams_emitter;
	public static Block dummy_port_ams_emitter;
	public static Block dummy_block_ams_base;
	public static Block dummy_port_ams_base;
	public static Block dummy_block_radgen;
	public static Block dummy_port_radgen;
	public static Block dummy_block_reactor_small;
	public static Block dummy_port_reactor_small;
	public static Block dummy_block_vault;
	public static Block dummy_block_blast;
	public static Block dummy_block_gascent;
	public static Block dummy_block_uf6;
	public static Block dummy_block_puf6;

	public static Block ntm_dirt;
	

	private static void initializeBlock() {
		
		test_render = new TestRender(Material.rock).setBlockName("test_render").setCreativeTab(null);
		test_container = new TestContainer(0).setBlockName("test_container").setCreativeTab(null);
		test_bomb = new TestBomb(Material.tnt).setBlockName("test_bomb").setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":test_bomb");
		test_bomb_advanced = new TestBombAdvanced(Material.tnt).setBlockName("test_bomb_advanced").setCreativeTab(null);
		
		test_nuke = new TestNuke(Material.iron).setBlockName("test_nuke").setCreativeTab(null).setHardness(2.5F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":test_nuke");
		event_tester = new TestEventTester(Material.iron).setBlockName("event_tester").setCreativeTab(null).setHardness(2.5F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":event_tester");
		rotation_tester = new TestRotationTester(Material.iron).setBlockName("rotation_tester").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F);
		obj_tester = new TestObjTester(Material.iron).setBlockName("obj_tester").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F);
		
		test_ticker = new TestTicker(Material.iron).setBlockName("test_ticker").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":test_ticker");
		
		test_missile = new TestMissile(Material.iron).setBlockName("test_missile").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":test_missile");
		
		ore_uranium = new BlockGeneric(Material.rock).setBlockName("ore_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_uranium");
		ore_titanium = new BlockGeneric(Material.rock).setBlockName("ore_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_titanium");
		ore_sulfur = new BlockOre(Material.rock).setBlockName("ore_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_sulfur");
		
		ore_niter = new BlockOre(Material.rock).setBlockName("ore_niter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_niter");
		ore_copper = new BlockGeneric(Material.rock).setBlockName("ore_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_copper");
		ore_tungsten = new BlockGeneric(Material.rock).setBlockName("ore_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_tungsten");
		ore_aluminium = new BlockGeneric(Material.rock).setBlockName("ore_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_aluminium");
		ore_fluorite = new BlockOre(Material.rock).setBlockName("ore_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_fluorite");
		ore_lead = new BlockGeneric(Material.rock).setBlockName("ore_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_lead");
		ore_schrabidium = new BlockOre(Material.rock, 0.1F, 0.5F).setBlockName("ore_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_schrabidium");
		ore_beryllium = new BlockGeneric(Material.rock).setBlockName("ore_beryllium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_beryllium");

		ore_nether_uranium = new BlockOre(Material.rock).setBlockName("ore_nether_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_uranium");
		ore_nether_plutonium = new BlockGeneric(Material.rock).setBlockName("ore_nether_plutonium").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_plutonium");
		ore_nether_tungsten = new BlockGeneric(Material.rock).setBlockName("ore_nether_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_tungsten");
		ore_nether_sulfur = new BlockOre(Material.rock).setBlockName("ore_nether_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_sulfur");
		ore_nether_fire = new BlockOre(Material.rock).setBlockName("ore_nether_fire").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_fire");
		ore_nether_schrabidium = new BlockGeneric(Material.rock).setBlockName("ore_nether_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_schrabidium");

		ore_australium = new BlockGeneric(Material.rock).setBlockName("ore_australium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_australium");
		ore_weidanium = new BlockGeneric(Material.rock).setBlockName("ore_weidanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_weidanium");
		ore_reiium = new BlockGeneric(Material.rock).setBlockName("ore_reiium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_reiium");
		ore_unobtainium = new BlockGeneric(Material.rock).setBlockName("ore_unobtainium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_unobtainium");
		ore_daffergon = new BlockGeneric(Material.rock).setBlockName("ore_daffergon").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_daffergon");
		ore_verticium = new BlockGeneric(Material.rock).setBlockName("ore_verticium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_verticium");
		ore_rare = new BlockOre(Material.rock).setBlockName("ore_rare").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_rare");

		ore_oil = new BlockOre(Material.rock).setBlockName("ore_oil").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil");
		ore_oil_empty = new BlockGeneric(Material.rock).setBlockName("ore_oil_empty").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil_empty");
		ore_oil_sand = new BlockFalling(Material.sand).setBlockName("ore_oil_sand").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeSand).setHardness(0.5F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":ore_oil_sand_alt");
		
		ore_tikite = new BlockGeneric(Material.rock).setBlockName("ore_tikite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_tikite_alt");
		
		block_uranium = new BlockOre(Material.iron, 0.1F, 1.5F).setBlockName("block_uranium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_uranium");
		block_titanium = new BlockGeneric(Material.iron).setBlockName("block_titanium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		block_sulfur = new BlockGeneric(Material.iron).setBlockName("block_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_sulfur");
		block_niter = new BlockGeneric(Material.iron).setBlockName("block_niter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_niter");
		block_copper = new BlockGeneric(Material.iron).setBlockName("block_copper").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_copper");
		block_red_copper = new BlockGeneric(Material.iron).setBlockName("block_red_copper").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_red_copper");
		block_tungsten = new BlockGeneric(Material.iron).setBlockName("block_tungsten").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_tungsten");
		block_aluminium = new BlockGeneric(Material.iron).setBlockName("block_aluminium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		block_fluorite = new BlockGeneric(Material.iron).setBlockName("block_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_fluorite");
		block_steel = new BlockGeneric(Material.iron).setBlockName("block_steel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		block_lead = new BlockGeneric(Material.iron).setBlockName("block_lead").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_lead");
		block_trinitite = new BlockOre(Material.iron, 3F, 35F).setBlockName("block_trinitite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_trinitite");
		block_waste = new BlockOre(Material.iron, 5F, 60F).setBlockName("block_waste").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_waste");
		block_scrap = new BlockFalling(Material.sand).setBlockName("block_scrap").setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F).setStepSound(Block.soundTypeGravel).setBlockTextureName(RefStrings.MODID + ":block_scrap");
		block_electrical_scrap = new BlockFalling(Material.iron).setBlockName("block_electrical_scrap").setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F).setStepSound(Block.soundTypeMetal).setBlockTextureName(RefStrings.MODID + ":electrical_scrap_alt2");
		block_beryllium = new BlockGeneric(Material.iron).setBlockName("block_beryllium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_beryllium");
		block_schrabidium = new BlockGeneric(Material.iron).setBlockName("block_schrabidium").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidium");
		block_advanced_alloy = new BlockGeneric(Material.iron).setBlockName("block_advanced_alloy").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_advanced_alloy");
		block_magnetized_tungsten = new BlockGeneric(Material.iron).setBlockName("block_magnetized_tungsten").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(35.0F).setBlockTextureName(RefStrings.MODID + ":block_magnetized_tungsten");
		block_combine_steel = new BlockGeneric(Material.iron).setBlockName("block_combine_steel").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_combine_steel");
		block_desh = new BlockGeneric(Material.iron).setBlockName("block_desh").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_desh");
		block_yellowcake = new BlockFallingRad(Material.sand, 0.5F, 3F).setBlockName("block_yellowcake").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeSand).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_yellowcake");

		block_australium = new BlockGeneric(Material.iron).setBlockName("block_australium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_australium");
		block_weidanium = new BlockGeneric(Material.iron).setBlockName("block_weidanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_weidanium");
		block_reiium = new BlockGeneric(Material.iron).setBlockName("block_reiium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_reiium");
		block_unobtainium = new BlockGeneric(Material.iron).setBlockName("block_unobtainium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_unobtainium");
		block_daffergon = new BlockGeneric(Material.iron).setBlockName("block_daffergon").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_daffergon");
		block_verticium = new BlockGeneric(Material.iron).setBlockName("block_verticium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_verticium");

		deco_titanium = new BlockOre(Material.iron).setBlockName("deco_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_titanium");
		deco_red_copper = new BlockOre(Material.iron).setBlockName("deco_red_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_red_copper");
		deco_tungsten = new BlockOre(Material.iron).setBlockName("deco_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_tungsten");
		deco_aluminium = new BlockOre(Material.iron).setBlockName("deco_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_aluminium");
		deco_steel = new BlockOre(Material.iron).setBlockName("deco_steel").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_steel");
		deco_lead = new BlockOre(Material.iron).setBlockName("deco_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_lead");
		deco_beryllium = new BlockOre(Material.iron).setBlockName("deco_beryllium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":deco_beryllium");
		
		hazmat = new BlockGeneric(Material.cloth).setBlockName("hazmat").setStepSound(Block.soundTypeCloth).setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":hazmat");
		
		gravel_obsidian = new BlockFalling(Material.iron).setBlockName("gravel_obsidian").setCreativeTab(MainRegistry.blockTab).setStepSound(Block.soundTypeGravel).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":gravel_obsidian");
		asphalt = new BlockGeneric(Material.rock).setBlockName("asphalt").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F).setBlockTextureName(RefStrings.MODID + ":asphalt");

		reinforced_brick = new BlockGeneric(Material.rock).setBlockName("reinforced_brick").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_brick");
		reinforced_glass = new ReinforcedBlock(Material.glass).setBlockName("reinforced_glass").setCreativeTab(MainRegistry.blockTab).setLightOpacity(0).setHardness(15.0F).setResistance(3000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_glass");
		reinforced_light = new ReinforcedBlock(Material.rock).setBlockName("reinforced_light").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setLightLevel(1.0F).setHardness(15.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_light");
		reinforced_sand = new BlockGeneric(Material.rock).setBlockName("reinforced_sand").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(4000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_sand");
		reinforced_lamp_off = new ReinforcedLamp(Material.rock, false).setBlockName("reinforced_lamp_off").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_lamp_off");
		reinforced_lamp_on = new ReinforcedLamp(Material.rock, true).setBlockName("reinforced_lamp_on").setHardness(15.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_lamp_on");

		brick_concrete = new BlockGeneric(Material.rock).setBlockName("brick_concrete").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(4000.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		brick_obsidian = new BlockGeneric(Material.rock).setBlockName("brick_obsidian").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":brick_obsidian");
		brick_light = new BlockGeneric(Material.rock).setBlockName("brick_light").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(1000.0F).setBlockTextureName(RefStrings.MODID + ":brick_light");
		cmb_brick = new BlockGeneric(Material.rock).setBlockName("cmb_brick").setCreativeTab(MainRegistry.blockTab).setHardness(25.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":cmb_brick");
		cmb_brick_reinforced = new BlockGeneric(Material.rock).setBlockName("cmb_brick_reinforced").setCreativeTab(MainRegistry.blockTab).setHardness(25.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":cmb_brick_reinforced");

		block_meteor = new BlockOre(Material.rock).setBlockName("block_meteor").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":block_meteor");
		block_meteor_cobble = new BlockOre(Material.rock).setBlockName("block_meteor_cobble").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":block_meteor_cobble");
		block_meteor_broken = new BlockOre(Material.rock).setBlockName("block_meteor_broken").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":block_meteor_broken");
		block_meteor_molten = new BlockOre(Material.rock, true).setBlockName("block_meteor_molten").setLightLevel(0.75F).setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":block_meteor_molten");
		block_meteor_treasure = new BlockOre(Material.rock).setBlockName("block_meteor_treasure").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setBlockTextureName(RefStrings.MODID + ":block_meteor_treasure");
		
		tape_recorder = new DecoTapeRecorder(Material.rock).setBlockName("tape_recorder").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_tape_recorder");
		steel_poles = new DecoSteelPoles(Material.rock).setBlockName("steel_poles").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_steel_poles");
		pole_top = new DecoPoleTop(Material.rock).setBlockName("pole_top").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_pole_top");
		pole_satellite_receiver = new DecoPoleSatelliteReceiver(Material.rock).setBlockName("pole_satellite_receiver").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_satellite_receiver");
		steel_wall = new DecoBlock(Material.rock).setBlockName("steel_wall").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_wall");
		steel_corner = new DecoBlock(Material.rock).setBlockName("steel_corner").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_corner");
		steel_roof = new DecoBlock(Material.rock).setBlockName("steel_roof").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_roof");
		steel_beam = new DecoBlock(Material.rock).setBlockName("steel_beam").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_beam");
		steel_scaffold = new DecoBlock(Material.rock).setBlockName("steel_scaffold").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_scaffold");
		
		broadcaster_pc = new PinkCloudBroadcaster(Material.rock).setBlockName("broadcaster_pc").setCreativeTab(MainRegistry.machineTab).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":broadcaster_pc");

		mush = new BlockMush(Material.plants).setBlockName("mush").setCreativeTab(MainRegistry.blockTab).setLightLevel(0.5F).setStepSound(Block.soundTypeGrass).setBlockTextureName(RefStrings.MODID + ":mush");
		mush_block = new BlockMushHuge(Material.plants).setBlockName("mush_block").setLightLevel(1.0F).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":mush_block_skin");
		mush_block_stem = new BlockMushHuge(Material.plants).setBlockName("mush_block_stem").setLightLevel(1.0F).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":mush_block_stem");

		waste_earth = new WasteEarth(Material.ground, 0.25F, 7.5F).setBlockName("waste_earth").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":waste_earth");
		waste_mycelium = new WasteEarth(Material.ground, 1F, 25F).setBlockName("waste_mycelium").setStepSound(Block.soundTypeGrass).setLightLevel(1F).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":waste_mycelium_side");
		waste_trinitite = new BlockOre(Material.sand, 0.5F, 10F).setBlockName("waste_trinitite").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_trinitite");
		waste_trinitite_red = new BlockOre(Material.sand, 0.5F,10F).setBlockName("waste_trinitite_red").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_trinitite_red");
		waste_log = new WasteLog(Material.wood).setBlockName("waste_log").setStepSound(Block.soundTypeWood).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(2.5F);
		waste_planks = new BlockOre(Material.wood).setBlockName("waste_planks").setStepSound(Block.soundTypeWood).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_planks");
		frozen_dirt = new BlockOre(Material.wood).setBlockName("frozen_dirt").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":frozen_dirt");
		frozen_grass = new WasteEarth(Material.wood).setBlockName("frozen_grass").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F);
		frozen_log = new WasteLog(Material.wood).setBlockName("frozen_log").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F);
		frozen_planks = new BlockOre(Material.wood).setBlockName("frozen_planks").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":frozen_planks");

		sellafield_slaked = new BlockGeneric(Material.rock).setBlockName("sellafield_slaked").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_slaked");
		sellafield_0 = new BlockOre(Material.rock, 0.5F, 10F).setBlockName("sellafield_0").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_0");
		sellafield_1 = new BlockOre(Material.rock, 1F, 15F).setBlockName("sellafield_1").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_1");
		sellafield_2 = new BlockOre(Material.rock, 2.5F, 25F).setBlockName("sellafield_2").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_2");
		sellafield_3 = new BlockOre(Material.rock, 4F, 40F).setBlockName("sellafield_3").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_3");
		sellafield_4 = new BlockOre(Material.rock, 5, 50F).setBlockName("sellafield_4").setStepSound(Block.soundTypeStone).setHardness(5.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_4");
		sellafield_core = new BlockOre(Material.rock, 10F, 150F).setBlockName("sellafield_core").setStepSound(Block.soundTypeStone).setHardness(10.0F).setBlockTextureName(RefStrings.MODID + ":sellafield_core");
		
		nuke_gadget = new NukeGadget(Material.iron).setBlockName("nuke_gadget").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":theGadget");
		nuke_boy = new NukeBoy(Material.iron).setBlockName("nuke_boy").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":lilBoy");
		nuke_man = new NukeMan(Material.iron).setBlockName("nuke_man").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":fatMan");
		nuke_mike = new NukeMike(Material.iron).setBlockName("nuke_mike").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":ivyMike");
		nuke_tsar = new NukeTsar(Material.iron).setBlockName("nuke_tsar").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":tsarBomba");
		nuke_fleija = new NukeFleija(Material.iron).setBlockName("nuke_fleija").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":fleija");
		nuke_prototype = new NukePrototype(Material.iron).setBlockName("nuke_prototype").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":prototype");
		nuke_custom = new NukeCustom(Material.iron).setBlockName("nuke_custom").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":custom");
		nuke_solinium = new NukeSolinium(Material.iron).setBlockName("nuke_solinium").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":nuke_solinium");
		nuke_n2 = new NukeN2(Material.iron).setBlockName("nuke_n2").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":nuke_n2");
		nuke_n45 = new NukeN45(Material.iron).setBlockName("nuke_n45").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":code");
		
		cel_prime = new CelPrime(Material.iron).setBlockName("cel_prime").setCreativeTab(null).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":code");
		cel_prime_terminal = new CelPrimePart(Material.iron).setBlockName("cel_prime_terminal").setCreativeTab(null).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":code");
		cel_prime_battery = new CelPrimePart(Material.iron).setBlockName("cel_prime_battery").setCreativeTab(null).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":code");
		cel_prime_port = new CelPrimePart(Material.iron).setBlockName("cel_prime_port").setCreativeTab(null).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":code");
		cel_prime_tanks = new CelPrimePart(Material.iron).setBlockName("cel_prime_tanks").setCreativeTab(null).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":code");

		bomb_multi = new BombMulti(Material.iron).setBlockName("bomb_multi").setCreativeTab(MainRegistry.nukeTab).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":bomb_multi1");
		//bomb_multi_large = new BombMultiLarge(Material.iron).setBlockName("bomb_multi_large").setCreativeTab(MainRegistry.tabNuke).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":bomb_multi_large");
		
		flame_war = new BombFlameWar(Material.iron).setBlockName("flame_war").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":flame_war");
		float_bomb = new BombFloat(Material.iron).setBlockName("float_bomb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
		therm_endo = new BombThermo(Material.iron).setBlockName("therm_endo").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
		therm_exo = new BombThermo(Material.iron).setBlockName("therm_exo").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
		emp_bomb = new BombFloat(Material.iron).setBlockName("emp_bomb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
		det_cord = new DetCord(Material.iron).setBlockName("det_cord").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_cord");
		det_charge = new DetCord(Material.iron).setBlockName("det_charge").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_charge");
		det_nuke = new DetCord(Material.iron).setBlockName("det_nuke").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_nuke");
		red_barrel = new RedBarrel(Material.iron).setBlockName("red_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
		yellow_barrel = new YellowBarrel(Material.iron).setBlockName("yellow_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
		crashed_balefire = new BlockCrashedBomb(Material.iron).setBlockName("crashed_bomb").setCreativeTab(MainRegistry.nukeTab).setBlockUnbreakable().setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":crashed_balefire");
		rejuvinator = new BombRejuvinator(Material.iron).setBlockName("rejuvinator").setCreativeTab(MainRegistry.nukeTab).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":inserter_side");
		mine_ap = new Landmine(Material.iron).setBlockName("mine_ap").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_ap");
		mine_he = new Landmine(Material.iron).setBlockName("mine_he").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_he");
		mine_shrap = new Landmine(Material.iron).setBlockName("mine_shrap").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_shrap");
		mine_fat = new Landmine(Material.iron).setBlockName("mine_fat").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F).setBlockTextureName(RefStrings.MODID + ":mine_fat");
		
		machine_difurnace_off = new MachineDiFurnace(false).setBlockName("machine_difurnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_difurnace_on = new MachineDiFurnace(true).setBlockName("machine_difurnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		
		machine_centrifuge = new MachineCentrifuge(Material.iron).setBlockName("machine_centrifuge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_gascent = new MachineGasCent(Material.iron).setBlockName("machine_gascent").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		machine_uf6_tank = new MachineUF6Tank(Material.iron).setBlockName("machine_uf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		machine_puf6_tank = new MachinePuF6Tank(Material.iron).setBlockName("machine_puf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		machine_reactor = new MachineReactor(Material.iron).setBlockName("machine_reactor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		machine_nuke_furnace_off = new MachineNukeFurnace(false).setBlockName("machine_nuke_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_nuke_furnace_on = new MachineNukeFurnace(true).setBlockName("machine_nuke_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		
		machine_rtg_furnace_off = new MachineRtgFurnace(false).setBlockName("machine_rtg_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_rtg_furnace_on = new MachineRtgFurnace(true).setBlockName("machine_rtg_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);

		machine_generator = new MachineGenerator(Material.iron).setBlockName("machine_generator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
		machine_industrial_generator = new MachineIGenerator(Material.iron).setBlockName("machine_industrial_generator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":industrial_generator");
		machine_cyclotron = new MachineCyclotron(Material.iron).setBlockName("machine_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":cyclotron");
		machine_radgen = new MachineRadGen(Material.iron).setBlockName("machine_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_radgen");
		
		machine_electric_furnace_off = new MachineElectricFurnace(false).setBlockName("machine_electric_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_electric_furnace_on = new MachineElectricFurnace(true).setBlockName("machine_electric_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);

		//machine_deuterium = new MachineDeuterium(Material.iron).setBlockName("machine_deuterium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		machine_battery = new MachineBattery(Material.iron, 1000000).setBlockName("machine_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_lithium_battery = new MachineBattery(Material.iron, 15000000).setBlockName("machine_lithium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_schrabidium_battery = new MachineBattery(Material.iron, 500000000).setBlockName("machine_schrabidium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_dineutronium_battery = new MachineBattery(Material.iron, 150000000000L).setBlockName("machine_dineutronium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		machine_coal_off = new MachineCoal(false).setBlockName("machine_coal_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_coal_on = new MachineCoal(true).setBlockName("machine_coal_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);

		machine_diesel = new MachineDiesel(Material.iron).setBlockName("machine_diesel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		machine_shredder = new MachineShredder(Material.iron).setBlockName("machine_shredder").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		machine_combine_factory = new MachineCMBFactory(Material.iron).setBlockName("machine_combine_factory").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		machine_teleporter = new MachineTeleporter(Material.iron).setBlockName("machine_teleporter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		machine_rtg_grey = new MachineRTG(Material.iron).setBlockName("machine_rtg_grey").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		//machine_rtg_red = new MachineRTG(Material.iron).setBlockName("machine_rtg_red").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		//machine_rtg_orange = new MachineRTG(Material.iron).setBlockName("machine_rtg_orange").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		//machine_rtg_yellow = new MachineRTG(Material.iron).setBlockName("machine_rtg_yellow").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		//machine_rtg_green = new MachineRTG(Material.iron).setBlockName("machine_rtg_green").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_rtg_cyan = new MachineRTG(Material.iron).setBlockName("machine_rtg_cyan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		//machine_rtg_blue = new MachineRTG(Material.iron).setBlockName("machine_rtg_blue").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		//machine_rtg_purple = new MachineRTG(Material.iron).setBlockName("machine_rtg_purple").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);

		red_wire_coated = new WireCoated(Material.iron).setBlockName("red_wire_coated").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_wire_coated");
		red_cable = new BlockCable(Material.iron).setBlockName("red_cable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_cable_icon");
		red_pylon = new PylonRedWire(Material.iron).setBlockName("red_pylon").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":red_pylon");
		oil_duct_solid = new OilDuctSolid(Material.iron).setBlockName("oil_duct_solid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":oil_duct_solid_alt");
		oil_duct = new BlockOilDuct(Material.iron).setBlockName("oil_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":oil_duct_icon_alt");
		gas_duct_solid = new GasDuctSolid(Material.iron).setBlockName("gas_duct_solid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_duct_solid");
		gas_duct = new BlockGasDuct(Material.iron).setBlockName("gas_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":gas_duct_icon");
		fluid_duct = new BlockFluidDuct(Material.iron).setBlockName("fluid_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fluid_duct_icon");

		machine_transformer = new MachineTransformer(Material.iron, 10000L, 1).setBlockName("machine_transformer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer_iron");
		machine_transformer_dnt = new MachineTransformer(Material.iron, 1000000000000000L, 1).setBlockName("machine_transformer_dnt").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer");
		machine_transformer_20 = new MachineTransformer(Material.iron, 10000L, 20).setBlockName("machine_transformer_20").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer_iron");
		machine_transformer_dnt_20 = new MachineTransformer(Material.iron, 1000000000000000L, 20).setBlockName("machine_transformer_dnt_20").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_transformer");
		
		machine_satlinker = new MachineSatLinker(Material.iron).setBlockName("machine_satlinker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_satlinker_side");
		machine_telelinker = new MachineTeleLinker(Material.iron).setBlockName("machine_telelinker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.nukeTab).setBlockTextureName(RefStrings.MODID + ":machine_telelinker_side");
		machine_keyforge = new MachineKeyForge(Material.iron).setBlockName("machine_keyforge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":machine_keyforge_side");

		factory_titanium_hull = new BlockGeneric(Material.iron).setBlockName("factory_titanium_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_titanium_hull");
		factory_titanium_furnace = new FactoryHatch(Material.iron).setBlockName("factory_titanium_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_titanium_furnace");
		factory_titanium_conductor = new BlockReactor(Material.iron).setBlockName("factory_titanium_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_titanium_conductor");
		factory_titanium_core = new FactoryCoreTitanium(Material.iron).setBlockName("factory_titanium_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_titanium_core");
		factory_advanced_hull = new BlockGeneric(Material.iron).setBlockName("factory_advanced_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_advanced_hull");
		factory_advanced_furnace = new FactoryHatch(Material.iron).setBlockName("factory_advanced_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_advanced_furnace");
		factory_advanced_conductor = new BlockReactor(Material.iron).setBlockName("factory_advanced_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_advanced_conductor");
		factory_advanced_core = new FactoryCoreAdvanced(Material.iron).setBlockName("factory_advanced_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":factory_advanced_core");

		reactor_element = new BlockReactor(Material.iron).setBlockName("reactor_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":reactor_element_side");
		reactor_control = new BlockReactor(Material.iron).setBlockName("reactor_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":reactor_control_side");
		reactor_hatch = new ReactorHatch(Material.iron).setBlockName("reactor_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		reactor_conductor = new BlockReactor(Material.iron).setBlockName("reactor_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":reactor_conductor_side");
		reactor_computer = new ReactorCore(Material.iron).setBlockName("reactor_computer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":reactor_computer");

		fusion_conductor = new BlockReactor(Material.iron).setBlockName("fusion_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_conductor_side");
		fusion_center = new BlockReactor(Material.iron).setBlockName("fusion_center").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_center_side");
		fusion_motor = new BlockReactor(Material.iron).setBlockName("fusion_motor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_motor_side");
		fusion_heater = new BlockReactor(Material.iron).setBlockName("fusion_heater").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_heater_side");
		fusion_hatch = new FusionHatch(Material.iron).setBlockName("fusion_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_hatch");
		fusion_core = new FusionCore(Material.iron).setBlockName("fusion_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fusion_core_side");
		plasma = new BlockPlasma(Material.iron).setBlockName("plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":plasma");

		watz_element = new BlockReactor(Material.iron).setBlockName("watz_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_element");
		watz_control = new BlockReactor(Material.iron).setBlockName("watz_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_control");
		watz_cooler = new BlockGeneric(Material.iron).setBlockName("watz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_cooler");
		watz_end = new BlockGeneric(Material.iron).setBlockName("watz_end").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_end");
		watz_hatch = new WatzHatch(Material.iron).setBlockName("watz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_hatch");
		watz_conductor = new BlockReactor(Material.iron).setBlockName("watz_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_conductor");
		watz_core = new WatzCore(Material.iron).setBlockName("watz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":watz_computer");

		fwatz_conductor = new BlockReactor(Material.iron).setBlockName("fwatz_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_conductor");
		fwatz_cooler = new BlockReactor(Material.iron).setBlockName("fwatz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_cooler");
		fwatz_tank = new ReinforcedBlock(Material.iron).setBlockName("fwatz_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_tank");
		fwatz_scaffold = new BlockGeneric(Material.iron).setBlockName("fwatz_scaffold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_scaffold");
		fwatz_hatch = new FWatzHatch(Material.iron).setBlockName("fwatz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_computer");
		fwatz_computer = new BlockGeneric(Material.iron).setBlockName("fwatz_computer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_computer");
		fwatz_core = new FWatzCore(Material.iron).setBlockName("fwatz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_core");
		fwatz_plasma = new BlockPlasma(Material.iron).setBlockName("fwatz_plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":fwatz_plasma");

		ams_base = new BlockAMSBase(Material.iron).setBlockName("ams_base").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":ams_base");
		ams_emitter = new BlockAMSEmitter(Material.iron).setBlockName("ams_emitter").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":ams_emitter");
		ams_limiter = new BlockAMSLimiter(Material.iron).setBlockName("ams_limiter").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":ams_limiter");

		machine_converter_he_rf = new BlockConverterHeRf(Material.iron).setBlockName("machine_converter_he_rf").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_converter_he_rf");
		machine_converter_rf_he = new BlockConverterRfHe(Material.iron).setBlockName("machine_converter_rf_he").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_converter_rf_he");

		seal_frame = new BlockGeneric(Material.iron).setBlockName("seal_frame").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":seal_frame");
		seal_controller = new BlockSeal(Material.iron).setBlockName("seal_controller").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
		seal_hatch = new BlockHatch(Material.iron).setBlockName("seal_hatch").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":seal_hatch_3");
		
		vault_door = new VaultDoor(Material.iron).setBlockName("vault_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vault_door");
		blast_door = new BlastDoor(Material.iron).setBlockName("blast_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":blast_door");
		
		barbed_wire = new BarbedWire(Material.iron).setBlockName("barbed_wire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":barbed_wire");
		
		marker_structure = new BlockMarker(Material.iron).setBlockName("marker_structure").setHardness(0.0F).setResistance(0.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":marker_structure");
		
		launch_pad = new LaunchPad(Material.iron).setBlockName("launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		machine_radar = new MachineRadar(Material.iron).setBlockName("machine_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab).setBlockTextureName(RefStrings.MODID + ":machine_radar");
		
		sat_mapper = new DecoBlock(Material.iron).setBlockName("sat_mapper").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_mapper");
		sat_radar = new DecoBlock(Material.iron).setBlockName("sat_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_radar");
		sat_scanner = new DecoBlock(Material.iron).setBlockName("sat_scanner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_scanner");
		sat_laser = new DecoBlock(Material.iron).setBlockName("sat_laser").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_laser");
		sat_foeq = new DecoBlock(Material.iron).setBlockName("sat_foeq").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_foeq");
		sat_resonator = new DecoBlock(Material.iron).setBlockName("sat_resonator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":sat_resonator");
		
		turret_light = new TurretLight(Material.iron).setBlockName("turret_light").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":turret_light");
		turret_heavy = new TurretHeavy(Material.iron).setBlockName("turret_heavy").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":turret_heavy");
		turret_rocket = new TurretRocket(Material.iron).setBlockName("turret_rocket").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":turret_rocket");
		turret_flamer = new TurretFlamer(Material.iron).setBlockName("turret_flamer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":turret_flamer");
		turret_tau = new TurretTau(Material.iron).setBlockName("turret_tau").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":turret_tau");
		turret_spitfire = new TurretSpitfire(Material.iron).setBlockName("turret_spitfire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":code");
		turret_cwis = new TurretCIWS(Material.iron).setBlockName("turret_cwis").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":turret_cwis");
		turret_cheapo = new TurretCheapo(Material.iron).setBlockName("turret_cheapo").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab).setBlockTextureName(RefStrings.MODID + ":turret_cheapo");
		
		book_guide = new Guide(Material.iron).setBlockName("book_guide").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.nukeTab);
		
		rail_highspeed = new RailHighspeed().setBlockName("rail_highspeed").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_highspeed");
		rail_booster = new RailBooster().setBlockName("rail_booster").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.tabTransport).setBlockTextureName(RefStrings.MODID + ":rail_booster");

		crate = new BlockCrate(Material.iron).setBlockName("crate").setStepSound(Block.soundTypeWood).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate");
		crate_weapon = new BlockCrate(Material.iron).setBlockName("crate_weapon").setStepSound(Block.soundTypeWood).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_weapon");
		crate_lead = new BlockCrate(Material.iron).setBlockName("crate_lead").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_lead");
		crate_metal = new BlockCrate(Material.iron).setBlockName("crate_metal").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab).setBlockTextureName(RefStrings.MODID + ":crate_metal");
		crate_red = new BlockCrate(Material.iron).setBlockName("crate_red").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crate_red");
		crate_iron = new BlockStorageCrate(Material.iron).setBlockName("crate_iron").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		crate_steel = new BlockStorageCrate(Material.iron).setBlockName("crate_steel").setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		safe = new BlockStorageCrate(Material.iron).setBlockName("safe").setStepSound(Block.soundTypeMetal).setHardness(7.5F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
		
		boxcar = new DecoBlock(Material.iron).setBlockName("boxcar").setStepSound(Block.soundTypeMetal).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab).setBlockTextureName(RefStrings.MODID + ":boxcar");
		bomber = new DecoBlock(Material.iron).setBlockName("bomber").setStepSound(Block.soundTypeMetal).setHardness(10.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":code");

		machine_well = new MachineOilWell(Material.iron).setBlockName("machine_well").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_well");
		machine_pumpjack = new MachinePumpjack(Material.iron).setBlockName("machine_pumpjack").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_pumpjack");
		oil_pipe = new BlockNoDrop(Material.iron).setBlockName("oil_pipe").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":oil_pipe");
		machine_flare = new MachineGasFlare(Material.iron).setBlockName("machine_flare").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_flare");
		machine_refinery = new MachineRefinery(Material.iron).setBlockName("machine_refinery").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_refinery");
		machine_drill = new MachineMiningDrill(Material.iron).setBlockName("machine_drill").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_drill");
		drill_pipe = new BlockNoDrop(Material.iron).setBlockName("drill_pipe").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":drill_pipe");
		machine_assembler = new MachineAssembler(Material.iron).setBlockName("machine_assembler").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_assembler");
		machine_chemplant = new MachineChemplant(Material.iron).setBlockName("machine_chemplant").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_chemplant");
		machine_fluidtank = new MachineFluidTank(Material.iron).setBlockName("machine_fluidtank").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_fluidtank");
		machine_turbofan = new MachineTurbofan(Material.iron).setBlockName("machine_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_turbofan");
		machine_press = new MachinePress(Material.iron).setBlockName("machine_press").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_press");
		machine_selenium = new MachineSeleniumEngine(Material.iron).setBlockName("machine_selenium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_selenium");
		machine_reactor_small = new MachineReactorSmall(Material.iron).setBlockName("machine_reactor_small").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_reactor_small");

		machine_boiler_off = new MachineBoiler(false).setBlockName("machine_boiler_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_boiler_off");
		machine_boiler_on = new MachineBoiler(true).setBlockName("machine_boiler_on").setHardness(5.0F).setResistance(10.0F).setLightLevel(1.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":machine_boiler_on");
		machine_boiler_electric_off = new MachineBoiler(false).setBlockName("machine_boiler_electric_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_boiler_electric_off");
		machine_boiler_electric_on = new MachineBoiler(true).setBlockName("machine_boiler_electric_on").setHardness(5.0F).setResistance(10.0F).setLightLevel(1.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":machine_boiler_electric_on");
		
		machine_turbine = new MachineTurbine(Material.iron).setBlockName("machine_turbine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_turbine");

		machine_schrabidium_transmutator = new MachineSchrabidiumTransmutator(Material.iron).setBlockName("machine_schrabidium_transmutator").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);

		machine_reix_mainframe = new MachineReiXMainframe(Material.iron).setBlockName("machine_reix_mainframe").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
		
		machine_siren = new MachineSiren(Material.iron).setBlockName("machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":machine_siren");
		
		machine_spp_bottom = new SPPBottom(Material.iron).setBlockName("machine_spp_bottom").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		machine_spp_top = new SPPTop(Material.iron).setBlockName("machine_spp_top").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
		
		radiobox = new Radiobox(Material.iron).setBlockName("radiobox").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":radiobox");
		radiorec = new RadioRec(Material.iron).setBlockName("radiorec").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":radiorec");

		cheater_virus = new CheaterVirus(Material.iron).setBlockName("cheater_virus").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":cheater_virus");
		cheater_virus_seed = new CheaterVirusSeed(Material.iron).setBlockName("cheater_virus_seed").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":cheater_virus_seed");
		crystal_virus = new CrystalVirus(Material.iron).setBlockName("crystal_virus").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_virus");
		crystal_hardened = new BlockGeneric(Material.iron).setBlockName("crystal_hardened").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_hardened");
		crystal_pulsar = new CrystalPulsar(Material.iron).setBlockName("crystal_pulsar").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":crystal_pulsar");
		taint = new BlockTaint(Material.iron).setBlockName("taint").setHardness(15.0F).setResistance(10.0F).setCreativeTab(null);
		residue = new BlockCloudResidue(Material.iron).setBlockName("residue").setHardness(0.5F).setResistance(0.5F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":residue");

		vent_chlorine = new BlockVent(Material.iron).setBlockName("vent_chlorine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_chlorine");
		vent_cloud = new BlockVent(Material.iron).setBlockName("vent_cloud").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_cloud");
		vent_pink_cloud = new BlockVent(Material.iron).setBlockName("vent_pink_cloud").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab).setBlockTextureName(RefStrings.MODID + ":vent_pink_cloud");

		statue_elb = new DecoBlockAlt(Material.iron).setBlockName("#null").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
		statue_elb_g = new DecoBlockAlt(Material.iron).setBlockName("#void").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
		statue_elb_w = new DecoBlockAlt(Material.iron).setBlockName("#ngtv").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
		statue_elb_f = new DecoBlockAlt(Material.iron).setBlockName("#undef").setHardness(Float.POSITIVE_INFINITY).setLightLevel(1.0F).setResistance(Float.POSITIVE_INFINITY);

		mud_fluid = new MudFluid().setDensity(2500).setViscosity(3000).setLuminosity(5).setTemperature(2773).setUnlocalizedName("mud_fluid");
		FluidRegistry.registerFluid(mud_fluid);
		mud_block = new MudBlock(mud_fluid, fluidmud.setReplaceable(), ModDamageSource.mudPoisoning).setBlockName("mud_block").setResistance(500F);

		acid_fluid = new AcidFluid().setDensity(2500).setViscosity(1500).setLuminosity(5).setTemperature(2773).setUnlocalizedName("mud_fluid");
		FluidRegistry.registerFluid(acid_fluid);
		acid_block = new AcidBlock(acid_fluid, fluidacid.setReplaceable(), ModDamageSource.acid).setBlockName("acid_block").setResistance(500F);

		toxic_fluid = new ToxicFluid().setDensity(2500).setViscosity(2000).setLuminosity(15).setTemperature(2773).setUnlocalizedName("mud_fluid");
		FluidRegistry.registerFluid(toxic_fluid);
		toxic_block = new ToxicBlock(toxic_fluid, fluidtoxic.setReplaceable(), ModDamageSource.radiation).setBlockName("toxic_block").setResistance(500F);

		dummy_block_igenerator = new DummyBlockIGenerator(Material.iron).setBlockName("dummy_block_igenerator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_igenerator = new DummyBlockIGenerator(Material.iron).setBlockName("dummy_port_igenerator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_centrifuge = new DummyBlockCentrifuge(Material.iron).setBlockName("dummy_block_centrifuge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_cyclotron = new DummyBlockCyclotron(Material.iron).setBlockName("dummy_block_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		dummy_port_cyclotron = new DummyBlockCyclotron(Material.iron).setBlockName("dummy_port_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		dummy_block_well = new DummyBlockWell(Material.iron).setBlockName("dummy_block_well").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_well = new DummyBlockWell(Material.iron).setBlockName("dummy_port_well").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_flare = new DummyBlockFlare(Material.iron).setBlockName("dummy_block_flare").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		dummy_port_flare = new DummyBlockFlare(Material.iron).setBlockName("dummy_port_flare").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		dummy_block_drill = new DummyBlockDrill(Material.iron).setBlockName("dummy_block_drill").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_lead");
		dummy_port_drill = new DummyBlockDrill(Material.iron).setBlockName("dummy_port_drill").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_lead");
		dummy_block_assembler = new DummyBlockAssembler(Material.iron).setBlockName("dummy_block_assembler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_assembler = new DummyBlockAssembler(Material.iron).setBlockName("dummy_port_assembler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_chemplant = new DummyBlockChemplant(Material.iron).setBlockName("dummy_block_chemplant").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_chemplant = new DummyBlockChemplant(Material.iron).setBlockName("dummy_port_chemplant").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_fluidtank = new DummyBlockFluidTank(Material.iron).setBlockName("dummy_block_fluidtank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_fluidtank = new DummyBlockFluidTank(Material.iron).setBlockName("dummy_port_fluidtank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_refinery = new DummyBlockRefinery(Material.iron).setBlockName("dummy_block_refinery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		dummy_port_refinery = new DummyBlockRefinery(Material.iron).setBlockName("dummy_port_refinery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		dummy_block_pumpjack = new DummyBlockPumpjack(Material.iron).setBlockName("dummy_block_pumpjack").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		dummy_port_pumpjack = new DummyBlockPumpjack(Material.iron).setBlockName("dummy_port_pumpjack").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		dummy_block_turbofan = new DummyBlockTurbofan(Material.iron).setBlockName("dummy_block_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_turbofan = new DummyBlockTurbofan(Material.iron).setBlockName("dummy_port_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_ams_limiter = new DummyBlockAMSLimiter(Material.iron).setBlockName("dummy_block_ams_limiter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		dummy_port_ams_limiter = new DummyBlockAMSLimiter(Material.iron).setBlockName("dummy_port_ams_limiter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		dummy_block_ams_emitter = new DummyBlockAMSEmitter(Material.iron).setBlockName("dummy_block_ams_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		dummy_port_ams_emitter = new DummyBlockAMSEmitter(Material.iron).setBlockName("dummy_port_ams_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		dummy_block_ams_base = new DummyBlockAMSBase(Material.iron).setBlockName("dummy_block_ams_base").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		dummy_port_ams_base = new DummyBlockAMSBase(Material.iron).setBlockName("dummy_port_ams_base").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_copper");
		dummy_block_radgen = new DummyBlockRadGen(Material.iron).setBlockName("dummy_block_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_radgen = new DummyBlockRadGen(Material.iron).setBlockName("dummy_port_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_reactor_small = new DummyBlockMachine(Material.iron, guiID_reactor_small, machine_reactor_small).setBlockName("dummy_block_reactor_small").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_port_reactor_small = new DummyBlockMachine(Material.iron, guiID_reactor_small, machine_reactor_small).setBlockName("dummy_port_reactor_small").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_vault = new DummyBlockVault(Material.iron).setBlockName("dummy_block_vault").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_blast = new DummyBlockBlast(Material.iron).setBlockName("dummy_block_blast").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_gascent = new DummyBlockMachine(Material.iron, guiID_gascent, machine_gascent).setBlockName("dummy_block_gascent").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		dummy_block_uf6 = new DummyBlockMachine(Material.iron, guiID_uf6_tank, machine_uf6_tank).setBlockName("dummy_block_uf6").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		dummy_block_puf6 = new DummyBlockMachine(Material.iron, guiID_puf6_tank, machine_puf6_tank).setBlockName("dummy_block_puf6").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null).setBlockTextureName(RefStrings.MODID + ":block_steel");
		
		ntm_dirt = new BlockNTMDirt().setBlockName("ntm_dirt").setHardness(0.5F).setStepSound(Block.soundTypeGravel).setCreativeTab(null).setBlockTextureName("dirt");
	}

	private static void registerBlock() {
		//Test
		GameRegistry.registerBlock(test_render, test_render.getUnlocalizedName());
		//GameRegistry.registerBlock(test_container, test_container.getUnlocalizedName());
		GameRegistry.registerBlock(test_bomb, test_bomb.getUnlocalizedName());
		GameRegistry.registerBlock(test_bomb_advanced, test_bomb_advanced.getUnlocalizedName());
		
		GameRegistry.registerBlock(test_nuke, test_nuke.getUnlocalizedName());
		
		GameRegistry.registerBlock(event_tester, event_tester.getUnlocalizedName());
		GameRegistry.registerBlock(rotation_tester, rotation_tester.getUnlocalizedName());
		GameRegistry.registerBlock(obj_tester, obj_tester.getUnlocalizedName());
		GameRegistry.registerBlock(test_ticker, test_ticker.getUnlocalizedName());
		GameRegistry.registerBlock(test_missile, test_missile.getUnlocalizedName());

		//Ores
		GameRegistry.registerBlock(ore_uranium, ore_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_titanium, ore_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_sulfur, ore_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ore_niter, ore_niter.getUnlocalizedName());
		GameRegistry.registerBlock(ore_copper, ore_copper.getUnlocalizedName());
		GameRegistry.registerBlock(ore_tungsten, ore_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ore_aluminium, ore_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_fluorite, ore_fluorite.getUnlocalizedName());
		GameRegistry.registerBlock(ore_beryllium, ore_beryllium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_lead, ore_lead.getUnlocalizedName());
		GameRegistry.registerBlock(ore_oil, ore_oil.getUnlocalizedName());
		GameRegistry.registerBlock(ore_oil_empty, ore_oil_empty.getUnlocalizedName());
		GameRegistry.registerBlock(ore_oil_sand, ore_oil_sand.getUnlocalizedName());
		GameRegistry.registerBlock(ore_schrabidium, ItemSchrabidiumBlock.class, ore_schrabidium.getUnlocalizedName());
		
		//Rare Minerals
		GameRegistry.registerBlock(ore_australium, ItemOreBlock.class, ore_australium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_weidanium, ItemOreBlock.class, ore_weidanium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_reiium, ItemOreBlock.class, ore_reiium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_unobtainium, ItemOreBlock.class, ore_unobtainium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_daffergon, ItemOreBlock.class, ore_daffergon.getUnlocalizedName());
		GameRegistry.registerBlock(ore_verticium, ItemOreBlock.class, ore_verticium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_rare, ItemOreBlock.class, ore_rare.getUnlocalizedName());
		
		//Nether Ores
		GameRegistry.registerBlock(ore_nether_uranium, ore_nether_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_plutonium, ore_nether_plutonium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_tungsten, ore_nether_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_sulfur, ore_nether_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_fire, ore_nether_fire.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_schrabidium, ItemSchrabidiumBlock.class, ore_nether_schrabidium.getUnlocalizedName());
		
		//End Ores
		GameRegistry.registerBlock(ore_tikite, ore_tikite.getUnlocalizedName());
		
		//Blocks
		GameRegistry.registerBlock(block_uranium, block_uranium.getUnlocalizedName());
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
		GameRegistry.registerBlock(block_steel, block_steel.getUnlocalizedName());
		GameRegistry.registerBlock(block_lead, block_lead.getUnlocalizedName());
		GameRegistry.registerBlock(block_yellowcake, block_yellowcake.getUnlocalizedName());
		GameRegistry.registerBlock(block_scrap, block_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(block_electrical_scrap, block_electrical_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(block_trinitite, block_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(block_waste, block_waste.getUnlocalizedName());
		GameRegistry.registerBlock(block_schrabidium, ItemSchrabidiumBlock.class, block_schrabidium.getUnlocalizedName());
		GameRegistry.registerBlock(block_magnetized_tungsten, block_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(block_combine_steel, block_combine_steel.getUnlocalizedName());
		GameRegistry.registerBlock(block_desh, block_desh.getUnlocalizedName());
		GameRegistry.registerBlock(block_australium, ItemOreBlock.class, block_australium.getUnlocalizedName());
		GameRegistry.registerBlock(block_weidanium, ItemOreBlock.class, block_weidanium.getUnlocalizedName());
		GameRegistry.registerBlock(block_reiium, ItemOreBlock.class, block_reiium.getUnlocalizedName());
		GameRegistry.registerBlock(block_unobtainium, ItemOreBlock.class, block_unobtainium.getUnlocalizedName());
		GameRegistry.registerBlock(block_daffergon, ItemOreBlock.class, block_daffergon.getUnlocalizedName());
		GameRegistry.registerBlock(block_verticium, ItemOreBlock.class, block_verticium.getUnlocalizedName());
		
		//Deco Blocks
		GameRegistry.registerBlock(deco_titanium, deco_titanium.getUnlocalizedName());
		GameRegistry.registerBlock(deco_red_copper, deco_red_copper.getUnlocalizedName());
		GameRegistry.registerBlock(deco_tungsten, deco_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(deco_aluminium, deco_aluminium.getUnlocalizedName());
		GameRegistry.registerBlock(deco_steel, deco_steel.getUnlocalizedName());
		GameRegistry.registerBlock(deco_lead, deco_lead.getUnlocalizedName());
		GameRegistry.registerBlock(deco_beryllium, deco_beryllium.getUnlocalizedName());
		GameRegistry.registerBlock(hazmat, hazmat.getUnlocalizedName());
		
		//Gravel
		GameRegistry.registerBlock(gravel_obsidian, gravel_obsidian.getUnlocalizedName());

		//Reinforced Blocks
		GameRegistry.registerBlock(asphalt, asphalt.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_brick, reinforced_brick.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_glass, reinforced_glass.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_light, reinforced_light.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_sand, reinforced_sand.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_lamp_off, reinforced_lamp_off.getUnlocalizedName());
		GameRegistry.registerBlock(reinforced_lamp_on, reinforced_lamp_on.getUnlocalizedName());

		//Bricks
		GameRegistry.registerBlock(brick_concrete, brick_concrete.getUnlocalizedName());
		GameRegistry.registerBlock(brick_obsidian, brick_obsidian.getUnlocalizedName());
		GameRegistry.registerBlock(brick_light, brick_light.getUnlocalizedName());
		
		//CMB Building Elements
		GameRegistry.registerBlock(cmb_brick, cmb_brick.getUnlocalizedName());
		GameRegistry.registerBlock(cmb_brick_reinforced, cmb_brick_reinforced.getUnlocalizedName());
		
		//Other defensive stuff
		GameRegistry.registerBlock(barbed_wire, barbed_wire.getUnlocalizedName());
		
		//Decoration Blocks
		GameRegistry.registerBlock(block_meteor, block_meteor.getUnlocalizedName());
		GameRegistry.registerBlock(block_meteor_cobble, block_meteor_cobble.getUnlocalizedName());
		GameRegistry.registerBlock(block_meteor_broken, block_meteor_broken.getUnlocalizedName());
		GameRegistry.registerBlock(block_meteor_molten, block_meteor_molten.getUnlocalizedName());
		GameRegistry.registerBlock(block_meteor_treasure, block_meteor_treasure.getUnlocalizedName());
		GameRegistry.registerBlock(tape_recorder, tape_recorder.getUnlocalizedName());
		GameRegistry.registerBlock(steel_poles, steel_poles.getUnlocalizedName());
		GameRegistry.registerBlock(pole_top, pole_top.getUnlocalizedName());
		GameRegistry.registerBlock(pole_satellite_receiver, pole_satellite_receiver.getUnlocalizedName());
		GameRegistry.registerBlock(steel_wall, steel_wall.getUnlocalizedName());
		GameRegistry.registerBlock(steel_corner, steel_corner.getUnlocalizedName());
		GameRegistry.registerBlock(steel_roof, steel_roof.getUnlocalizedName());
		GameRegistry.registerBlock(steel_beam, steel_beam.getUnlocalizedName());
		GameRegistry.registerBlock(steel_scaffold, steel_scaffold.getUnlocalizedName());
		GameRegistry.registerBlock(mush, mush.getUnlocalizedName());
		GameRegistry.registerBlock(mush_block, mush_block.getUnlocalizedName());
		GameRegistry.registerBlock(mush_block_stem, mush_block_stem.getUnlocalizedName());
		
		//Nuclear Waste
		GameRegistry.registerBlock(waste_earth, waste_earth.getUnlocalizedName());
		GameRegistry.registerBlock(waste_mycelium, waste_mycelium.getUnlocalizedName());
		GameRegistry.registerBlock(waste_trinitite, waste_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(waste_trinitite_red, waste_trinitite_red.getUnlocalizedName());
		GameRegistry.registerBlock(waste_log, waste_log.getUnlocalizedName());
		GameRegistry.registerBlock(waste_planks, waste_planks.getUnlocalizedName());
		GameRegistry.registerBlock(frozen_grass, frozen_grass.getUnlocalizedName());
		GameRegistry.registerBlock(frozen_dirt, frozen_dirt.getUnlocalizedName());
		GameRegistry.registerBlock(frozen_log, frozen_log.getUnlocalizedName());
		GameRegistry.registerBlock(frozen_planks, frozen_planks.getUnlocalizedName());
		
		//RAD
		GameRegistry.registerBlock(sellafield_slaked, sellafield_slaked.getUnlocalizedName());
		GameRegistry.registerBlock(sellafield_0, sellafield_0.getUnlocalizedName());
		GameRegistry.registerBlock(sellafield_1, sellafield_1.getUnlocalizedName());
		GameRegistry.registerBlock(sellafield_2, sellafield_2.getUnlocalizedName());
		GameRegistry.registerBlock(sellafield_3, sellafield_3.getUnlocalizedName());
		GameRegistry.registerBlock(sellafield_4, sellafield_4.getUnlocalizedName());
		GameRegistry.registerBlock(sellafield_core, sellafield_core.getUnlocalizedName());

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
		GameRegistry.registerBlock(nuke_n45, nuke_n45.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_custom, nuke_custom.getUnlocalizedName());
		
		//Generic Bombs
		GameRegistry.registerBlock(bomb_multi, bomb_multi.getUnlocalizedName());
		GameRegistry.registerBlock(crashed_balefire, crashed_balefire.getUnlocalizedName());
		//GameRegistry.registerBlock(bomb_multi_large, bomb_multi_large.getUnlocalizedName());
		
		//Turrets
		GameRegistry.registerBlock(turret_light, turret_light.getUnlocalizedName());
		GameRegistry.registerBlock(turret_heavy, turret_heavy.getUnlocalizedName());
		GameRegistry.registerBlock(turret_rocket, turret_rocket.getUnlocalizedName());
		GameRegistry.registerBlock(turret_flamer, turret_flamer.getUnlocalizedName());
		GameRegistry.registerBlock(turret_tau, turret_tau.getUnlocalizedName());
		GameRegistry.registerBlock(turret_spitfire, turret_spitfire.getUnlocalizedName());
		GameRegistry.registerBlock(turret_cwis, turret_cwis.getUnlocalizedName());
		GameRegistry.registerBlock(turret_cheapo, turret_cheapo.getUnlocalizedName());
		
		//Mines
		GameRegistry.registerBlock(mine_ap, mine_ap.getUnlocalizedName());
		GameRegistry.registerBlock(mine_he, mine_he.getUnlocalizedName());
		GameRegistry.registerBlock(mine_shrap, mine_shrap.getUnlocalizedName());
		GameRegistry.registerBlock(mine_fat, mine_fat.getUnlocalizedName());
		
		//Wot
		GameRegistry.registerBlock(cel_prime, cel_prime.getUnlocalizedName());
		GameRegistry.registerBlock(cel_prime_terminal, cel_prime_terminal.getUnlocalizedName());
		GameRegistry.registerBlock(cel_prime_battery, cel_prime_battery.getUnlocalizedName());
		GameRegistry.registerBlock(cel_prime_port, cel_prime_port.getUnlocalizedName());
		GameRegistry.registerBlock(cel_prime_tanks, cel_prime_tanks.getUnlocalizedName());
		
		//Block Bombs
		GameRegistry.registerBlock(flame_war, flame_war.getUnlocalizedName());
		GameRegistry.registerBlock(float_bomb, float_bomb.getUnlocalizedName());
		GameRegistry.registerBlock(therm_endo, therm_endo.getUnlocalizedName());
		GameRegistry.registerBlock(therm_exo, therm_exo.getUnlocalizedName());
		GameRegistry.registerBlock(emp_bomb, emp_bomb.getUnlocalizedName());
		//GameRegistry.registerBlock(rejuvinator, rejuvinator.getUnlocalizedName());
		GameRegistry.registerBlock(det_cord, det_cord.getUnlocalizedName());
		GameRegistry.registerBlock(det_charge, det_charge.getUnlocalizedName());
		GameRegistry.registerBlock(det_nuke, det_nuke.getUnlocalizedName());
		GameRegistry.registerBlock(red_barrel, red_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(yellow_barrel, yellow_barrel.getUnlocalizedName());
		
		//Siren
		GameRegistry.registerBlock(machine_siren, machine_siren.getUnlocalizedName());
		
		//This Thing
		GameRegistry.registerBlock(broadcaster_pc, broadcaster_pc.getUnlocalizedName());
		
		//Silo Hatch
		GameRegistry.registerBlock(seal_frame, seal_frame.getUnlocalizedName());
		GameRegistry.registerBlock(seal_controller, seal_controller.getUnlocalizedName());
		GameRegistry.registerBlock(seal_hatch, seal_hatch.getUnlocalizedName());
		
		//Vault Door
		GameRegistry.registerBlock(vault_door, vault_door.getUnlocalizedName());
		GameRegistry.registerBlock(blast_door, blast_door.getUnlocalizedName());
		
		//Crates
		GameRegistry.registerBlock(crate_iron, crate_iron.getUnlocalizedName());
		GameRegistry.registerBlock(crate_steel, crate_steel.getUnlocalizedName());
		GameRegistry.registerBlock(safe, safe.getUnlocalizedName());
		
		//Junk
		GameRegistry.registerBlock(boxcar, boxcar.getUnlocalizedName());
		GameRegistry.registerBlock(bomber, bomber.getUnlocalizedName());
		
		//Machines
		GameRegistry.registerBlock(machine_press, machine_press.getUnlocalizedName());
		GameRegistry.registerBlock(machine_difurnace_off, machine_difurnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_difurnace_on, machine_difurnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_centrifuge, machine_centrifuge.getUnlocalizedName());
		GameRegistry.registerBlock(machine_gascent, machine_gascent.getUnlocalizedName());
		GameRegistry.registerBlock(machine_uf6_tank, machine_uf6_tank.getUnlocalizedName());
		GameRegistry.registerBlock(machine_puf6_tank, machine_puf6_tank.getUnlocalizedName());
		GameRegistry.registerBlock(machine_reactor, machine_reactor.getUnlocalizedName());
		GameRegistry.registerBlock(machine_nuke_furnace_off, machine_nuke_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_nuke_furnace_on, machine_nuke_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_rtg_furnace_off, machine_rtg_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_rtg_furnace_on, machine_rtg_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_coal_off, machine_coal_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_coal_on, machine_coal_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_diesel, machine_diesel.getUnlocalizedName());
		GameRegistry.registerBlock(machine_selenium, machine_selenium.getUnlocalizedName());
		GameRegistry.registerBlock(machine_generator, machine_generator.getUnlocalizedName());
		GameRegistry.registerBlock(machine_reactor_small, machine_reactor_small.getUnlocalizedName());
		GameRegistry.registerBlock(machine_industrial_generator, machine_industrial_generator.getUnlocalizedName());
		GameRegistry.registerBlock(machine_radgen, machine_radgen.getUnlocalizedName());
		GameRegistry.registerBlock(machine_cyclotron, machine_cyclotron.getUnlocalizedName());
		GameRegistry.registerBlock(machine_rtg_grey, machine_rtg_grey.getUnlocalizedName());
		GameRegistry.registerBlock(machine_spp_bottom, machine_spp_bottom.getUnlocalizedName());
		GameRegistry.registerBlock(machine_spp_top, machine_spp_top.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_rtg_red, machine_rtg_red.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_rtg_orange, machine_rtg_orange.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_rtg_yellow, machine_rtg_yellow.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_rtg_green, machine_rtg_green.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_rtg_cyan, machine_rtg_cyan.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_rtg_blue, machine_rtg_blue.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_rtg_purple, machine_rtg_purple.getUnlocalizedName());
		GameRegistry.registerBlock(red_cable, red_cable.getUnlocalizedName());
		GameRegistry.registerBlock(red_wire_coated, red_wire_coated.getUnlocalizedName());
		GameRegistry.registerBlock(red_pylon, red_pylon.getUnlocalizedName());
		GameRegistry.registerBlock(oil_duct, oil_duct.getUnlocalizedName());
		GameRegistry.registerBlock(oil_duct_solid, oil_duct_solid.getUnlocalizedName());
		GameRegistry.registerBlock(gas_duct, gas_duct.getUnlocalizedName());
		GameRegistry.registerBlock(gas_duct_solid, gas_duct_solid.getUnlocalizedName());
		GameRegistry.registerBlock(fluid_duct, fluid_duct.getUnlocalizedName());
		GameRegistry.registerBlock(machine_battery, machine_battery.getUnlocalizedName());
		GameRegistry.registerBlock(machine_lithium_battery, machine_lithium_battery.getUnlocalizedName());
		GameRegistry.registerBlock(machine_schrabidium_battery, machine_schrabidium_battery.getUnlocalizedName());
		GameRegistry.registerBlock(machine_dineutronium_battery, machine_dineutronium_battery.getUnlocalizedName());
		GameRegistry.registerBlock(machine_transformer, machine_transformer.getUnlocalizedName());
		GameRegistry.registerBlock(machine_transformer_20, machine_transformer_20.getUnlocalizedName());
		GameRegistry.registerBlock(machine_transformer_dnt, machine_transformer_dnt.getUnlocalizedName());
		GameRegistry.registerBlock(machine_transformer_dnt_20, machine_transformer_dnt_20.getUnlocalizedName());
		GameRegistry.registerBlock(machine_converter_he_rf, machine_converter_he_rf.getUnlocalizedName());
		GameRegistry.registerBlock(machine_converter_rf_he, machine_converter_rf_he.getUnlocalizedName());
		GameRegistry.registerBlock(machine_electric_furnace_off, machine_electric_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_electric_furnace_on, machine_electric_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_assembler, machine_assembler.getUnlocalizedName());
		GameRegistry.registerBlock(machine_chemplant, machine_chemplant.getUnlocalizedName());
		GameRegistry.registerBlock(machine_fluidtank, machine_fluidtank.getUnlocalizedName());
		GameRegistry.registerBlock(machine_boiler_off, machine_boiler_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_boiler_on, machine_boiler_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_boiler_electric_on, machine_boiler_electric_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_boiler_electric_off, machine_boiler_electric_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_turbine, machine_turbine.getUnlocalizedName());
		GameRegistry.registerBlock(machine_shredder, machine_shredder.getUnlocalizedName());
		//GameRegistry.registerBlock(machine_deuterium, machine_deuterium.getUnlocalizedName());
		GameRegistry.registerBlock(machine_well, machine_well.getUnlocalizedName());
		GameRegistry.registerBlock(machine_pumpjack, machine_pumpjack.getUnlocalizedName());
		GameRegistry.registerBlock(machine_flare, machine_flare.getUnlocalizedName());
		GameRegistry.registerBlock(machine_refinery, machine_refinery.getUnlocalizedName());
		GameRegistry.registerBlock(machine_drill, machine_drill.getUnlocalizedName());
		GameRegistry.registerBlock(machine_turbofan, machine_turbofan.getUnlocalizedName());
		GameRegistry.registerBlock(machine_schrabidium_transmutator, machine_schrabidium_transmutator.getUnlocalizedName());
		GameRegistry.registerBlock(machine_combine_factory, machine_combine_factory.getUnlocalizedName());
		GameRegistry.registerBlock(machine_teleporter, machine_teleporter.getUnlocalizedName());
		GameRegistry.registerBlock(machine_satlinker, machine_satlinker.getUnlocalizedName());
		GameRegistry.registerBlock(machine_telelinker, machine_telelinker.getUnlocalizedName());
		GameRegistry.registerBlock(machine_keyforge, machine_keyforge.getUnlocalizedName());
		GameRegistry.registerBlock(radiorec, radiorec.getUnlocalizedName());
		GameRegistry.registerBlock(radiobox, radiobox.getUnlocalizedName());
		
		//ReiX Machines
		//GameRegistry.registerBlock(machine_reix_mainframe, machine_reix_mainframe.getUnlocalizedName());
		
		//Multiblock Helpers
		GameRegistry.registerBlock(marker_structure, marker_structure.getUnlocalizedName());
		
		//Industrial Factories
		GameRegistry.registerBlock(factory_titanium_hull, factory_titanium_hull.getUnlocalizedName());
		GameRegistry.registerBlock(factory_titanium_furnace, factory_titanium_furnace.getUnlocalizedName());
		GameRegistry.registerBlock(factory_titanium_conductor, factory_titanium_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(factory_titanium_core, factory_titanium_core.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_hull, factory_advanced_hull.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_furnace, factory_advanced_furnace.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_conductor, factory_advanced_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_core, factory_advanced_core.getUnlocalizedName());
		
		//The Fluid Inserter
		//GameRegistry.registerBlock(machine_inserter, machine_inserter.getUnlocalizedName());
		
		//Multiblock Generators
		GameRegistry.registerBlock(reactor_element, reactor_element.getUnlocalizedName());
		GameRegistry.registerBlock(reactor_control, reactor_control.getUnlocalizedName());
		GameRegistry.registerBlock(reactor_hatch, reactor_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(reactor_conductor, reactor_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(reactor_computer, reactor_computer.getUnlocalizedName());

		GameRegistry.registerBlock(fusion_conductor, fusion_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(fusion_center, fusion_center.getUnlocalizedName());
		GameRegistry.registerBlock(fusion_motor, fusion_motor.getUnlocalizedName());
		GameRegistry.registerBlock(fusion_heater, fusion_heater.getUnlocalizedName());
		GameRegistry.registerBlock(fusion_hatch, fusion_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(fusion_core, fusion_core.getUnlocalizedName());
		GameRegistry.registerBlock(plasma, ItemPlasmaBlock.class, plasma.getUnlocalizedName());

		GameRegistry.registerBlock(watz_element, watz_element.getUnlocalizedName());
		GameRegistry.registerBlock(watz_control, watz_control.getUnlocalizedName());
		GameRegistry.registerBlock(watz_cooler, watz_cooler.getUnlocalizedName());
		GameRegistry.registerBlock(watz_end, watz_end.getUnlocalizedName());
		GameRegistry.registerBlock(watz_hatch, watz_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(watz_conductor, watz_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(watz_core, watz_core.getUnlocalizedName());

		GameRegistry.registerBlock(fwatz_conductor, fwatz_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(fwatz_scaffold, fwatz_scaffold.getUnlocalizedName());
		GameRegistry.registerBlock(fwatz_hatch, fwatz_hatch.getUnlocalizedName());
		GameRegistry.registerBlock(fwatz_computer, fwatz_computer.getUnlocalizedName());
		GameRegistry.registerBlock(fwatz_core, fwatz_core.getUnlocalizedName());
		GameRegistry.registerBlock(fwatz_cooler, fwatz_cooler.getUnlocalizedName());
		GameRegistry.registerBlock(fwatz_tank, fwatz_tank.getUnlocalizedName());
		GameRegistry.registerBlock(fwatz_plasma, fwatz_plasma.getUnlocalizedName());
		
		//AMS
		GameRegistry.registerBlock(ams_base, ams_base.getUnlocalizedName());
		GameRegistry.registerBlock(ams_emitter, ams_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(ams_limiter, ams_limiter.getUnlocalizedName());
		
		//Missile Blocks
		GameRegistry.registerBlock(launch_pad, launch_pad.getUnlocalizedName());
		GameRegistry.registerBlock(machine_radar, machine_radar.getUnlocalizedName());
		
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
		GameRegistry.registerBlock(rail_highspeed, rail_highspeed.getUnlocalizedName());
		GameRegistry.registerBlock(rail_booster, rail_booster.getUnlocalizedName());
		
		//Crate
		GameRegistry.registerBlock(crate, crate.getUnlocalizedName());
		GameRegistry.registerBlock(crate_weapon, crate_weapon.getUnlocalizedName());
		GameRegistry.registerBlock(crate_lead, crate_lead.getUnlocalizedName());
		GameRegistry.registerBlock(crate_metal, crate_metal.getUnlocalizedName());
		GameRegistry.registerBlock(crate_red, crate_red.getUnlocalizedName());
		
		//ElB
		GameRegistry.registerBlock(statue_elb, statue_elb.getUnlocalizedName());
		GameRegistry.registerBlock(statue_elb_g, statue_elb_g.getUnlocalizedName());
		GameRegistry.registerBlock(statue_elb_w, statue_elb_w.getUnlocalizedName());
		GameRegistry.registerBlock(statue_elb_f, statue_elb_f.getUnlocalizedName());
		
		//Fluids
		GameRegistry.registerBlock(mud_block, mud_block.getUnlocalizedName());
		GameRegistry.registerBlock(acid_block, acid_block.getUnlocalizedName());
		GameRegistry.registerBlock(toxic_block, toxic_block.getUnlocalizedName());
		
		//Multiblock Dummy Blocks
		GameRegistry.registerBlock(dummy_block_igenerator, dummy_block_igenerator.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_igenerator, dummy_port_igenerator.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_centrifuge, dummy_block_centrifuge.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_cyclotron, dummy_block_cyclotron.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_cyclotron, dummy_port_cyclotron.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_well, dummy_block_well.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_well, dummy_port_well.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_flare, dummy_block_flare.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_flare, dummy_port_flare.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_drill, dummy_block_drill.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_drill, dummy_port_drill.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_assembler, dummy_block_assembler.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_assembler, dummy_port_assembler.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_chemplant, dummy_block_chemplant.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_chemplant, dummy_port_chemplant.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_fluidtank, dummy_block_fluidtank.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_fluidtank, dummy_port_fluidtank.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_refinery, dummy_block_refinery.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_refinery, dummy_port_refinery.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_pumpjack, dummy_block_pumpjack.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_pumpjack, dummy_port_pumpjack.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_turbofan, dummy_block_turbofan.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_turbofan, dummy_port_turbofan.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_ams_limiter, dummy_block_ams_limiter.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_ams_limiter, dummy_port_ams_limiter.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_ams_emitter, dummy_block_ams_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_ams_emitter, dummy_port_ams_emitter.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_ams_base, dummy_block_ams_base.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_ams_base, dummy_port_ams_base.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_radgen, dummy_block_radgen.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_radgen, dummy_port_radgen.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_reactor_small, dummy_block_reactor_small.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_port_reactor_small, dummy_port_reactor_small.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_vault, dummy_block_vault.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_blast, dummy_block_blast.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_gascent, dummy_block_gascent.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_uf6, dummy_block_uf6.getUnlocalizedName());
		GameRegistry.registerBlock(dummy_block_puf6, dummy_block_puf6.getUnlocalizedName());
		
		//Other Technical Blocks
		GameRegistry.registerBlock(oil_pipe, oil_pipe.getUnlocalizedName());
		GameRegistry.registerBlock(drill_pipe, drill_pipe.getUnlocalizedName());
		GameRegistry.registerBlock(vent_chlorine, vent_chlorine.getUnlocalizedName());
		GameRegistry.registerBlock(vent_cloud, vent_cloud.getUnlocalizedName());
		GameRegistry.registerBlock(vent_pink_cloud, vent_pink_cloud.getUnlocalizedName());
		
		//???
		GameRegistry.registerBlock(crystal_virus, crystal_virus.getUnlocalizedName());
		GameRegistry.registerBlock(crystal_hardened, crystal_hardened.getUnlocalizedName());
		GameRegistry.registerBlock(crystal_pulsar, crystal_pulsar.getUnlocalizedName());
		GameRegistry.registerBlock(taint, ItemTaintBlock.class, taint.getUnlocalizedName());
		GameRegistry.registerBlock(residue, residue.getUnlocalizedName());
		GameRegistry.registerBlock(cheater_virus, cheater_virus.getUnlocalizedName());
		GameRegistry.registerBlock(cheater_virus_seed, cheater_virus_seed.getUnlocalizedName());
		GameRegistry.registerBlock(ntm_dirt, ntm_dirt.getUnlocalizedName());
	}
}
