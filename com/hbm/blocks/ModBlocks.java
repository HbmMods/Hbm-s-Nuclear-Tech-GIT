package com.hbm.blocks;

import com.hbm.items.ItemPlasmaBlock;
import com.hbm.items.ItemPrototypeBlock;
import com.hbm.items.ItemSchrabidiumBlock;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
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
	public static Block block_beryllium;
	public static Block block_schrabidium;
	public static Block block_advanced_alloy;
	public static Block block_magnetized_tungsten;
	public static Block block_combine_steel;
	
	public static Block gravel_obsidian;
	
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

	public static Block tape_recorder;
	public static Block steel_poles;
	public static Block pole_top;
	public static Block pole_satellite_receiver;
	public static Block steel_wall;
	public static Block steel_corner;
	public static Block steel_roof;
	public static Block steel_beam;
	public static Block steel_scaffold;

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

	public static Block flame_war;
	public static Block float_bomb;
	public static Block therm_endo;
	public static Block therm_exo;
	public static Block det_cord;
	public static Block red_barrel;
	public static Block yellow_barrel;
	public static Block crashed_balefire;

	public static Block crate;
	
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
	
	public static Block bomb_multi;
	public static final int guiID_bomb_multi = 10;
	
	public static Block machine_difurnace_off;
	public static Block machine_difurnace_on;
	public static final int guiID_test_difurnace = 1;
	
	public static Block machine_centrifuge;
	public static final int guiID_centrifuge = 5;
	
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
	
	public static Block machine_electric_furnace_off;
	public static Block machine_electric_furnace_on;
	public static final int guiID_electric_furnace = 16;

	public static Block machine_deuterium;
	public static final int guiID_machine_deuterium = 20;

	public static Block machine_battery;
	public static final int guiID_machine_battery = 21;
	
	public static Block machine_coal_off;
	public static Block machine_coal_on;
	public static final int guiID_machine_coal = 22;

	public static Block red_wire_coated;
	public static Block red_cable;

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

	public static Block launch_pad;
	public static Block launch_pad_generic;
	public static Block launch_pad_incendiary;
	public static Block launch_pad_cluster;
	public static Block launch_pad_buster;
	public static Block launch_pad_strong;
	public static Block launch_pad_incendiary_strong;
	public static Block launch_pad_cluster_strong;
	public static Block launch_pad_buster_strong;
	public static Block launch_pad_burst;
	public static Block launch_pad_inferno;
	public static Block launch_pad_rain;
	public static Block launch_pad_drill;
	public static Block launch_pad_nuclear;
	public static Block launch_pad_endo;
	public static Block launch_pad_exo;
	public static Block launch_pad_mirv;
	public static final int guiID_launch_pad = 19;

	public static Block book_guide;
	
	public static Block statue_elb;
	public static Block statue_elb_g;
	public static Block statue_elb_w;
	public static Block statue_elb_f;

	public static Block mud_block;
	public static Fluid mud_fluid;
	public static final Material fluidmud = (new MaterialLiquid(MapColor.adobeColor));

	public static Block acid_block;
	public static Fluid acid_fluid;
	public static final Material fluidacid = (new MaterialLiquid(MapColor.purpleColor));

	public static Block toxic_block;
	public static Fluid toxic_fluid;
	public static final Material fluidtoxic = (new MaterialLiquid(MapColor.greenColor));
	

	private static void initializeBlock() {
		
		test_render = new TestRender(Material.rock).setBlockName("test_render").setCreativeTab(MainRegistry.tabTest);
		test_container = new TestContainer(0).setBlockName("test_container").setCreativeTab(MainRegistry.tabTest);
		test_bomb = new TestBomb(Material.tnt).setBlockName("test_bomb").setCreativeTab(MainRegistry.tabTest).setBlockTextureName(RefStrings.MODID + ":test_bomb");
		test_bomb_advanced = new TestBombAdvanced(Material.tnt).setBlockName("test_bomb_advanced").setCreativeTab(MainRegistry.tabTest);
		
		test_nuke = new TestNuke(Material.iron).setBlockName("test_nuke").setCreativeTab(MainRegistry.tabTest).setHardness(2.5F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":test_nuke");
		event_tester = new TestEventTester(Material.iron).setBlockName("event_tester").setCreativeTab(MainRegistry.tabTest).setHardness(2.5F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":event_tester");
		rotation_tester = new TestRotationTester(Material.iron).setBlockName("rotation_tester").setCreativeTab(MainRegistry.tabTest).setHardness(2.5F).setResistance(10.0F);
		obj_tester = new TestObjTester(Material.iron).setBlockName("obj_tester").setCreativeTab(MainRegistry.tabTest).setHardness(2.5F).setResistance(10.0F);
		
		test_ticker = new TestTicker(Material.iron).setBlockName("test_ticker").setCreativeTab(MainRegistry.tabTest).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":test_ticker");
		
		test_missile = new TestMissile(Material.iron).setBlockName("test_missile").setCreativeTab(MainRegistry.tabTest).setHardness(2.5F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":test_missile");
		
		ore_uranium = new BlockGeneric(Material.rock).setBlockName("ore_uranium").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_uranium");
		ore_titanium = new BlockGeneric(Material.rock).setBlockName("ore_titanium").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_titanium");
		ore_sulfur = new BlockOre(Material.rock).setBlockName("ore_sulfur").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_sulfur");
		
		ore_niter = new BlockOre(Material.rock).setBlockName("ore_niter").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_niter");
		ore_copper = new BlockGeneric(Material.rock).setBlockName("ore_copper").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_copper");
		ore_tungsten = new BlockGeneric(Material.rock).setBlockName("ore_tungsten").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_tungsten");
		ore_aluminium = new BlockGeneric(Material.rock).setBlockName("ore_aluminium").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_aluminium");
		ore_fluorite = new BlockOre(Material.rock).setBlockName("ore_fluorite").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_fluorite");
		ore_lead = new BlockGeneric(Material.rock).setBlockName("ore_lead").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_lead");
		ore_schrabidium = new BlockGeneric(Material.rock).setBlockName("ore_schrabidium").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_schrabidium");
		ore_beryllium = new BlockGeneric(Material.rock).setBlockName("ore_beryllium").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":ore_beryllium");

		ore_nether_uranium = new BlockGeneric(Material.rock).setBlockName("ore_nether_uranium").setCreativeTab(MainRegistry.tabBlock).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_uranium");
		ore_nether_plutonium = new BlockGeneric(Material.rock).setBlockName("ore_nether_plutonium").setCreativeTab(MainRegistry.tabBlock).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_plutonium");
		ore_nether_tungsten = new BlockGeneric(Material.rock).setBlockName("ore_nether_tungsten").setCreativeTab(MainRegistry.tabBlock).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_tungsten");
		ore_nether_sulfur = new BlockOre(Material.rock).setBlockName("ore_nether_sulfur").setCreativeTab(MainRegistry.tabBlock).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_sulfur");
		ore_nether_fire = new BlockOre(Material.rock).setBlockName("ore_nether_fire").setCreativeTab(MainRegistry.tabBlock).setHardness(0.4F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_fire");
		ore_nether_schrabidium = new BlockGeneric(Material.rock).setBlockName("ore_nether_schrabidium").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":ore_nether_schrabidium");
		
		block_uranium = new BlockGeneric(Material.iron).setBlockName("block_uranium").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_uranium");
		block_titanium = new BlockGeneric(Material.iron).setBlockName("block_titanium").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_titanium");
		block_sulfur = new BlockGeneric(Material.iron).setBlockName("block_sulfur").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_sulfur");
		block_niter = new BlockGeneric(Material.iron).setBlockName("block_niter").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_niter");
		block_copper = new BlockGeneric(Material.iron).setBlockName("block_copper").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_copper");
		block_red_copper = new BlockGeneric(Material.iron).setBlockName("block_red_copper").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_red_copper");
		block_tungsten = new BlockGeneric(Material.iron).setBlockName("block_tungsten").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_tungsten");
		block_aluminium = new BlockGeneric(Material.iron).setBlockName("block_aluminium").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_aluminium");
		block_fluorite = new BlockGeneric(Material.iron).setBlockName("block_fluorite").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_fluorite");
		block_steel = new BlockGeneric(Material.iron).setBlockName("block_steel").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_steel");
		block_lead = new BlockGeneric(Material.iron).setBlockName("block_lead").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_lead");
		block_trinitite = new BlockOre(Material.iron).setBlockName("block_trinitite").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_trinitite");
		block_waste = new BlockOre(Material.iron).setBlockName("block_waste").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_waste");
		block_scrap = new BlockFalling(Material.sand).setBlockName("block_scrap").setCreativeTab(MainRegistry.tabBlock).setHardness(2.5F).setResistance(5.0F).setStepSound(Block.soundTypeGravel).setBlockTextureName(RefStrings.MODID + ":block_scrap");
		block_beryllium = new BlockGeneric(Material.iron).setBlockName("block_beryllium").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_beryllium");
		block_schrabidium = new BlockGeneric(Material.iron).setBlockName("block_schrabidium").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_schrabidium");
		block_advanced_alloy = new BlockGeneric(Material.iron).setBlockName("block_advanced_alloy").setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":block_advanced_alloy");
		block_magnetized_tungsten = new BlockGeneric(Material.iron).setBlockName("block_magnetized_tungsten").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(35.0F).setBlockTextureName(RefStrings.MODID + ":block_magnetized_tungsten");
		block_combine_steel = new BlockGeneric(Material.iron).setBlockName("block_combine_steel").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeMetal).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":block_combine_steel");
		
		gravel_obsidian = new BlockFalling(Material.iron).setBlockName("gravel_obsidian").setCreativeTab(MainRegistry.tabBlock).setStepSound(Block.soundTypeGravel).setHardness(5.0F).setResistance(600.0F).setBlockTextureName(RefStrings.MODID + ":gravel_obsidian");

		reinforced_brick = new BlockGeneric(Material.rock).setBlockName("reinforced_brick").setCreativeTab(MainRegistry.tabBlock).setLightOpacity(15).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_brick");
		reinforced_glass = new ReinforcedBlock(Material.glass).setBlockName("reinforced_glass").setCreativeTab(MainRegistry.tabBlock).setLightOpacity(0).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_glass");
		reinforced_light = new ReinforcedBlock(Material.rock).setBlockName("reinforced_light").setCreativeTab(MainRegistry.tabBlock).setLightOpacity(15).setLightLevel(1.0F).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_light");
		reinforced_sand = new BlockGeneric(Material.rock).setBlockName("reinforced_sand").setCreativeTab(MainRegistry.tabBlock).setLightOpacity(15).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_sand");
		reinforced_lamp_off = new ReinforcedLamp(Material.rock, false).setBlockName("reinforced_lamp_off").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_lamp_off");
		reinforced_lamp_on = new ReinforcedLamp(Material.rock, true).setBlockName("reinforced_lamp_on").setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":reinforced_lamp_on");

		brick_concrete = new BlockGeneric(Material.rock).setBlockName("brick_concrete").setCreativeTab(MainRegistry.tabBlock).setLightOpacity(15).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		brick_obsidian = new BlockGeneric(Material.rock).setBlockName("brick_obsidian").setCreativeTab(MainRegistry.tabBlock).setLightOpacity(15).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":brick_obsidian");
		brick_light = new BlockGeneric(Material.rock).setBlockName("brick_light").setCreativeTab(MainRegistry.tabBlock).setLightOpacity(15).setHardness(15.0F).setResistance(60.0F).setBlockTextureName(RefStrings.MODID + ":brick_light");

		cmb_brick = new BlockGeneric(Material.rock).setBlockName("cmb_brick").setCreativeTab(MainRegistry.tabBlock).setHardness(25.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":cmb_brick");
		cmb_brick_reinforced = new BlockGeneric(Material.rock).setBlockName("cmb_brick_reinforced").setCreativeTab(MainRegistry.tabBlock).setHardness(25.0F).setResistance(60000.0F).setBlockTextureName(RefStrings.MODID + ":cmb_brick_reinforced");

		block_meteor = new BlockGeneric(Material.rock).setBlockName("block_meteor").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":block_meteor");
		
		tape_recorder = new DecoTapeRecorder(Material.rock).setBlockName("tape_recorder").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_tape_recorder");
		steel_poles = new DecoSteelPoles(Material.rock).setBlockName("steel_poles").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_steel_poles");
		pole_top = new DecoPoleTop(Material.rock).setBlockName("pole_top").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_pole_top");
		pole_satellite_receiver = new DecoPoleSatelliteReceiver(Material.rock).setBlockName("pole_satellite_receiver").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":deco_satellite_receiver");
		steel_wall = new DecoBlock(Material.rock).setBlockName("steel_wall").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_wall");
		steel_corner = new DecoBlock(Material.rock).setBlockName("steel_corner").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_corner");
		steel_roof = new DecoBlock(Material.rock).setBlockName("steel_roof").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_roof");
		steel_beam = new DecoBlock(Material.rock).setBlockName("steel_beam").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_beam");
		steel_scaffold = new DecoBlock(Material.rock).setBlockName("steel_scaffold").setCreativeTab(MainRegistry.tabBlock).setHardness(15.0F).setResistance(15.0F).setBlockTextureName(RefStrings.MODID + ":steel_scaffold");

		mush = new BlockMush(Material.plants).setBlockName("mush").setCreativeTab(MainRegistry.tabBlock).setLightLevel(0.5F).setStepSound(Block.soundTypeGrass).setBlockTextureName(RefStrings.MODID + ":mush");
		mush_block = new BlockMushHuge(Material.plants).setBlockName("mush_block").setLightLevel(1.0F).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":mush_block_skin");
		mush_block_stem = new BlockMushHuge(Material.plants).setBlockName("mush_block_stem").setLightLevel(1.0F).setStepSound(Block.soundTypeGrass).setHardness(0.2F).setBlockTextureName(RefStrings.MODID + ":mush_block_stem");

		waste_earth = new WasteEarth(Material.ground).setBlockName("waste_earth").setStepSound(Block.soundTypeGrass).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":waste_earth");
		waste_mycelium = new WasteEarth(Material.ground).setBlockName("waste_mycelium").setStepSound(Block.soundTypeGrass).setLightLevel(1F).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(1.0F).setBlockTextureName(RefStrings.MODID + ":waste_mycelium_side");
		waste_trinitite = new BlockOre(Material.sand).setBlockName("waste_trinitite").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_trinitite");
		waste_trinitite_red = new BlockOre(Material.sand).setBlockName("waste_trinitite_red").setStepSound(Block.soundTypeSand).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_trinitite_red");
		waste_log = new WasteLog(Material.wood).setBlockName("waste_log").setStepSound(Block.soundTypeWood).setCreativeTab(MainRegistry.tabBlock).setHardness(5.0F).setResistance(0.5F);
		waste_planks = new BlockOre(Material.wood).setBlockName("waste_planks").setStepSound(Block.soundTypeWood).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":waste_planks");
		frozen_dirt = new BlockOre(Material.wood).setBlockName("frozen_dirt").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":frozen_dirt");
		frozen_grass = new WasteEarth(Material.wood).setBlockName("frozen_grass").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(2.5F);
		frozen_log = new WasteLog(Material.wood).setBlockName("frozen_log").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(2.5F);
		frozen_planks = new BlockOre(Material.wood).setBlockName("frozen_planks").setStepSound(Block.soundTypeGlass).setCreativeTab(MainRegistry.tabBlock).setHardness(0.5F).setResistance(2.5F).setBlockTextureName(RefStrings.MODID + ":frozen_planks");
		
		nuke_gadget = new NukeGadget(Material.iron).setBlockName("nuke_gadget").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":theGadget");
		nuke_boy = new NukeBoy(Material.iron).setBlockName("nuke_boy").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":lilBoy");
		nuke_man = new NukeMan(Material.iron).setBlockName("nuke_man").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":fatMan");
		nuke_mike = new NukeMike(Material.iron).setBlockName("nuke_mike").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":ivyMike");
		nuke_tsar = new NukeTsar(Material.iron).setBlockName("nuke_tsar").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":tsarBomba");
		nuke_fleija = new NukeFleija(Material.iron).setBlockName("nuke_fleija").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":fleija");
		nuke_prototype = new NukePrototype(Material.iron).setBlockName("nuke_prototype").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":prototype");

		bomb_multi = new BombMulti(Material.iron).setBlockName("bomb_multi").setCreativeTab(MainRegistry.tabNuke).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":bomb_multi1");
		//bomb_multi_large = new BombMultiLarge(Material.iron).setBlockName("bomb_multi_large").setCreativeTab(MainRegistry.tabNuke).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":bomb_multi_large");
		
		flame_war = new BombFlameWar(Material.iron).setBlockName("flame_war").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":flame_war");
		float_bomb = new BombFloat(Material.iron).setBlockName("float_bomb").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F);
		therm_endo = new BombThermo(Material.iron).setBlockName("therm_endo").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F);
		therm_exo = new BombThermo(Material.iron).setBlockName("therm_exo").setCreativeTab(MainRegistry.tabNuke).setHardness(5.0F).setResistance(6000.0F);
		det_cord = new DetCord(Material.iron).setBlockName("det_cord").setCreativeTab(MainRegistry.tabNuke).setHardness(0.1F).setResistance(0.0F).setBlockTextureName(RefStrings.MODID + ":det_cord");
		red_barrel = new RedBarrel(Material.iron).setBlockName("red_barrel").setCreativeTab(MainRegistry.tabNuke).setHardness(0.5F).setResistance(2.5F);
		yellow_barrel = new YellowBarrel(Material.iron).setBlockName("yellow_barrel").setCreativeTab(MainRegistry.tabNuke).setHardness(0.5F).setResistance(2.5F);
		crashed_balefire = new BlockCrashedBomb(Material.iron).setBlockName("crashed_bomb").setCreativeTab(MainRegistry.tabNuke).setBlockUnbreakable().setResistance(6000.0F).setBlockTextureName(RefStrings.MODID + ":crashed_balefire");
		
		machine_difurnace_off = new MachineDiFurnace(false).setBlockName("machine_difurnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		machine_difurnace_on = new MachineDiFurnace(true).setBlockName("machine_difurnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		
		machine_centrifuge = new MachineCentrifuge(Material.iron).setBlockName("machine_centrifuge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		
		machine_uf6_tank = new MachineUF6Tank(Material.iron).setBlockName("machine_uf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		
		machine_puf6_tank = new MachinePuF6Tank(Material.iron).setBlockName("machine_puf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		
		machine_reactor = new MachineReactor(Material.iron).setBlockName("machine_reactor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		
		machine_nuke_furnace_off = new MachineNukeFurnace(false).setBlockName("machine_nuke_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		machine_nuke_furnace_on = new MachineNukeFurnace(true).setBlockName("machine_nuke_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
		
		machine_rtg_furnace_off = new MachineRtgFurnace(false).setBlockName("machine_rtg_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		machine_rtg_furnace_on = new MachineRtgFurnace(true).setBlockName("machine_rtg_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);

		machine_generator = new MachineGenerator(Material.iron).setBlockName("machine_generator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		
		machine_electric_furnace_off = new MachineElectricFurnace(false).setBlockName("machine_electric_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		machine_electric_furnace_on = new MachineElectricFurnace(true).setBlockName("machine_electric_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);

		machine_deuterium = new MachineDeuterium(Material.iron).setBlockName("machine_deuterium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);

		machine_battery = new MachineBattery(Material.iron).setBlockName("machine_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		
		machine_coal_off = new MachineCoal(false).setBlockName("machine_coal_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);
		machine_coal_on = new MachineCoal(true).setBlockName("machine_coal_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);

		machine_diesel = new MachineDiesel(Material.iron).setBlockName("machine_diesel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);

		machine_shredder = new MachineShredder(Material.iron).setBlockName("machine_shredder").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);

		machine_combine_factory = new MachineCMBFactory(Material.iron).setBlockName("machine_combine_factory").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock);

		red_wire_coated = new WireCoated(Material.iron).setBlockName("red_wire_coated").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":red_wire_coated");
		red_cable = new BlockCable(Material.iron).setBlockName("red_cable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":red_cable_icon");

		factory_titanium_hull = new BlockGeneric(Material.iron).setBlockName("factory_titanium_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":factory_titanium_hull");
		factory_titanium_furnace = new FactoryHatch(Material.iron).setBlockName("factory_titanium_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":factory_titanium_furnace");
		factory_titanium_conductor = new BlockReactor(Material.iron).setBlockName("factory_titanium_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":factory_titanium_conductor");
		factory_titanium_core = new FactoryCoreTitanium(Material.iron).setBlockName("factory_titanium_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":factory_titanium_core");
		factory_advanced_hull = new BlockGeneric(Material.iron).setBlockName("factory_advanced_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":factory_advanced_hull");
		factory_advanced_furnace = new FactoryHatch(Material.iron).setBlockName("factory_advanced_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":factory_advanced_furnace");
		factory_advanced_conductor = new BlockReactor(Material.iron).setBlockName("factory_advanced_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":factory_advanced_conductor");
		factory_advanced_core = new FactoryCoreAdvanced(Material.iron).setBlockName("factory_advanced_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":factory_advanced_core");

		reactor_element = new BlockReactor(Material.iron).setBlockName("reactor_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":reactor_element_side");
		reactor_control = new BlockReactor(Material.iron).setBlockName("reactor_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":reactor_control_side");
		reactor_hatch = new ReactorHatch(Material.iron).setBlockName("reactor_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":brick_concrete");
		reactor_conductor = new BlockReactor(Material.iron).setBlockName("reactor_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":reactor_conductor_side");
		reactor_computer = new ReactorCore(Material.iron).setBlockName("reactor_computer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":reactor_computer");

		fusion_conductor = new BlockReactor(Material.iron).setBlockName("fusion_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fusion_conductor_side");
		fusion_center = new BlockReactor(Material.iron).setBlockName("fusion_center").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fusion_center_side");
		fusion_motor = new BlockReactor(Material.iron).setBlockName("fusion_motor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fusion_motor_side");
		fusion_heater = new BlockReactor(Material.iron).setBlockName("fusion_heater").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fusion_heater_side");
		fusion_hatch = new FusionHatch(Material.iron).setBlockName("fusion_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fusion_hatch");
		fusion_core = new FusionCore(Material.iron).setBlockName("fusion_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fusion_core_side");
		plasma = new BlockPlasma(Material.iron).setBlockName("plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":plasma");

		watz_element = new BlockReactor(Material.iron).setBlockName("watz_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":watz_element");
		watz_control = new BlockReactor(Material.iron).setBlockName("watz_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":watz_control");
		watz_cooler = new BlockGeneric(Material.iron).setBlockName("watz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":watz_cooler");
		watz_end = new BlockGeneric(Material.iron).setBlockName("watz_end").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":watz_end");
		watz_hatch = new WatzHatch(Material.iron).setBlockName("watz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":watz_hatch");
		watz_conductor = new BlockReactor(Material.iron).setBlockName("watz_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":watz_conductor");
		watz_core = new WatzCore(Material.iron).setBlockName("watz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":watz_computer");

		fwatz_conductor = new BlockReactor(Material.iron).setBlockName("fwatz_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fwatz_conductor");
		fwatz_cooler = new BlockReactor(Material.iron).setBlockName("fwatz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fwatz_cooler");
		fwatz_tank = new ReinforcedBlock(Material.iron).setBlockName("fwatz_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fwatz_tank");
		fwatz_scaffold = new BlockGeneric(Material.iron).setBlockName("fwatz_scaffold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fwatz_scaffold");
		fwatz_hatch = new FWatzHatch(Material.iron).setBlockName("fwatz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fwatz_computer");
		fwatz_computer = new BlockGeneric(Material.iron).setBlockName("fwatz_computer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fwatz_computer");
		fwatz_core = new FWatzCore(Material.iron).setBlockName("fwatz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fwatz_core");
		fwatz_plasma = new BlockPlasma(Material.iron).setBlockName("fwatz_plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":fwatz_plasma");

		machine_converter_he_rf = new BlockConverterHeRf(Material.iron).setBlockName("machine_converter_he_rf").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":machine_converter_he_rf");
		machine_converter_rf_he = new BlockConverterRfHe(Material.iron).setBlockName("machine_converter_rf_he").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabBlock).setBlockTextureName(RefStrings.MODID + ":machine_converter_rf_he");
		
		launch_pad = new LaunchPad(Material.iron).setBlockName("launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabNuke).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_generic = new LaunchPad(Material.iron).setBlockName("launch_pad_generic").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_incendiary = new LaunchPad(Material.iron).setBlockName("launch_pad_incendiary").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_cluster = new LaunchPad(Material.iron).setBlockName("launch_pad_cluster").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_buster = new LaunchPad(Material.iron).setBlockName("launch_pad_buster").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_strong = new LaunchPad(Material.iron).setBlockName("launch_pad_strong").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_incendiary_strong = new LaunchPad(Material.iron).setBlockName("launch_pad_incendiary_strong").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_cluster_strong = new LaunchPad(Material.iron).setBlockName("launch_pad_cluster_strong").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_buster_strong = new LaunchPad(Material.iron).setBlockName("launch_pad_buster_strong").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_burst = new LaunchPad(Material.iron).setBlockName("launch_pad_burst").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_inferno = new LaunchPad(Material.iron).setBlockName("launch_pad_inferno").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_rain = new LaunchPad(Material.iron).setBlockName("launch_pad_rain").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_drill = new LaunchPad(Material.iron).setBlockName("launch_pad_drill").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_nuclear = new LaunchPad(Material.iron).setBlockName("launch_pad_nuclear").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_endo = new LaunchPad(Material.iron).setBlockName("launch_pad_endo").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_exo = new LaunchPad(Material.iron).setBlockName("launch_pad_exo").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		launch_pad_mirv = new LaunchPad(Material.iron).setBlockName("launch_pad_mirv").setHardness(5.0F).setResistance(10.0F).setBlockTextureName(RefStrings.MODID + ":launch_pad");
		
		book_guide = new Guide(Material.iron).setBlockName("book_guide").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabNuke);
		
		crate = new BlockCrate(Material.iron).setBlockName("crate").setStepSound(Block.soundTypeWood).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.tabNuke).setBlockTextureName(RefStrings.MODID + ":crate");

		machine_schrabidium_transmutator = new MachineSchrabidiumTransmutator(Material.iron).setBlockName("machine_schrabidium_transmutator").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.tabBlock);

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
		GameRegistry.registerBlock(ore_schrabidium, ItemSchrabidiumBlock.class, ore_schrabidium.getUnlocalizedName());
		
		//Nether Ores
		GameRegistry.registerBlock(ore_nether_uranium, ore_nether_uranium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_plutonium, ore_nether_plutonium.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_tungsten, ore_nether_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_sulfur, ore_nether_sulfur.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_fire, ore_nether_fire.getUnlocalizedName());
		GameRegistry.registerBlock(ore_nether_schrabidium, ItemSchrabidiumBlock.class, ore_nether_schrabidium.getUnlocalizedName());
		
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
		GameRegistry.registerBlock(block_scrap, block_scrap.getUnlocalizedName());
		GameRegistry.registerBlock(block_trinitite, block_trinitite.getUnlocalizedName());
		GameRegistry.registerBlock(block_waste, block_waste.getUnlocalizedName());
		GameRegistry.registerBlock(block_schrabidium, ItemSchrabidiumBlock.class, block_schrabidium.getUnlocalizedName());
		GameRegistry.registerBlock(block_magnetized_tungsten, block_magnetized_tungsten.getUnlocalizedName());
		GameRegistry.registerBlock(block_combine_steel, block_combine_steel.getUnlocalizedName());
		
		//Gravel
		GameRegistry.registerBlock(gravel_obsidian, gravel_obsidian.getUnlocalizedName());

		//Reinforced Blocks
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
		
		//Decoration Blocks
		GameRegistry.registerBlock(block_meteor, block_meteor.getUnlocalizedName());
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

		//Nukes
		GameRegistry.registerBlock(nuke_gadget, nuke_gadget.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_boy, nuke_boy.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_man, nuke_man.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_mike, nuke_mike.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_tsar, nuke_tsar.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_prototype, ItemPrototypeBlock.class, nuke_prototype.getUnlocalizedName());
		GameRegistry.registerBlock(nuke_fleija, nuke_fleija.getUnlocalizedName());
		
		//Generic Bombs
		GameRegistry.registerBlock(bomb_multi, bomb_multi.getUnlocalizedName());
		GameRegistry.registerBlock(crashed_balefire, crashed_balefire.getUnlocalizedName());
		//GameRegistry.registerBlock(bomb_multi_large, bomb_multi_large.getUnlocalizedName());
		
		//Block Bombs
		GameRegistry.registerBlock(flame_war, flame_war.getUnlocalizedName());
		GameRegistry.registerBlock(float_bomb, float_bomb.getUnlocalizedName());
		GameRegistry.registerBlock(therm_endo, therm_endo.getUnlocalizedName());
		GameRegistry.registerBlock(therm_exo, therm_exo.getUnlocalizedName());
		GameRegistry.registerBlock(det_cord, det_cord.getUnlocalizedName());
		GameRegistry.registerBlock(red_barrel, red_barrel.getUnlocalizedName());
		GameRegistry.registerBlock(yellow_barrel, yellow_barrel.getUnlocalizedName());
		
		//Machines
		GameRegistry.registerBlock(machine_difurnace_off, machine_difurnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_difurnace_on, machine_difurnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_centrifuge, machine_centrifuge.getUnlocalizedName());
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
		GameRegistry.registerBlock(machine_generator, machine_generator.getUnlocalizedName());
		GameRegistry.registerBlock(red_cable, red_cable.getUnlocalizedName());
		GameRegistry.registerBlock(red_wire_coated, red_wire_coated.getUnlocalizedName());
		GameRegistry.registerBlock(machine_battery, machine_battery.getUnlocalizedName());
		GameRegistry.registerBlock(machine_converter_he_rf, machine_converter_he_rf.getUnlocalizedName());
		GameRegistry.registerBlock(machine_converter_rf_he, machine_converter_rf_he.getUnlocalizedName());
		GameRegistry.registerBlock(machine_electric_furnace_off, machine_electric_furnace_off.getUnlocalizedName());
		GameRegistry.registerBlock(machine_electric_furnace_on, machine_electric_furnace_on.getUnlocalizedName());
		GameRegistry.registerBlock(machine_shredder, machine_shredder.getUnlocalizedName());
		GameRegistry.registerBlock(machine_deuterium, machine_deuterium.getUnlocalizedName());
		GameRegistry.registerBlock(machine_schrabidium_transmutator, machine_schrabidium_transmutator.getUnlocalizedName());
		GameRegistry.registerBlock(machine_combine_factory, machine_combine_factory.getUnlocalizedName());
		
		//Industrial Factories
		GameRegistry.registerBlock(factory_titanium_hull, factory_titanium_hull.getUnlocalizedName());
		GameRegistry.registerBlock(factory_titanium_furnace, factory_titanium_furnace.getUnlocalizedName());
		GameRegistry.registerBlock(factory_titanium_conductor, factory_titanium_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(factory_titanium_core, factory_titanium_core.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_hull, factory_advanced_hull.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_furnace, factory_advanced_furnace.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_conductor, factory_advanced_conductor.getUnlocalizedName());
		GameRegistry.registerBlock(factory_advanced_core, factory_advanced_core.getUnlocalizedName());
		
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
		
		//Launch Pads
		GameRegistry.registerBlock(launch_pad, launch_pad.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_generic, launch_pad_generic.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_incendiary, launch_pad_incendiary.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_cluster, launch_pad_cluster.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_buster, launch_pad_buster.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_strong, launch_pad_strong.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_incendiary_strong, launch_pad_incendiary_strong.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_cluster_strong, launch_pad_cluster_strong.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_buster_strong, launch_pad_buster_strong.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_burst, launch_pad_burst.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_inferno, launch_pad_inferno.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_rain, launch_pad_rain.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_drill, launch_pad_drill.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_nuclear, launch_pad_nuclear.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_endo, launch_pad_endo.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_exo, launch_pad_exo.getUnlocalizedName());
		GameRegistry.registerBlock(launch_pad_mirv, launch_pad_mirv.getUnlocalizedName());
		
		//Guide
		GameRegistry.registerBlock(book_guide, book_guide.getUnlocalizedName());
		
		//Crate
		GameRegistry.registerBlock(crate, crate.getUnlocalizedName());
		
		//ElB
		GameRegistry.registerBlock(statue_elb, statue_elb.getUnlocalizedName());
		GameRegistry.registerBlock(statue_elb_g, statue_elb_g.getUnlocalizedName());
		GameRegistry.registerBlock(statue_elb_w, statue_elb_w.getUnlocalizedName());
		GameRegistry.registerBlock(statue_elb_f, statue_elb_f.getUnlocalizedName());
		
		//Fluids
		GameRegistry.registerBlock(mud_block, mud_block.getUnlocalizedName());
		GameRegistry.registerBlock(acid_block, acid_block.getUnlocalizedName());
		GameRegistry.registerBlock(toxic_block, toxic_block.getUnlocalizedName());
	}
}
