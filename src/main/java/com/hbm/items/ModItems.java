package com.hbm.items;

import java.util.HashSet;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.VersatileConfig;
import com.hbm.handler.BucketHandler;
import com.hbm.handler.ability.IToolAreaAbility;
import com.hbm.handler.ability.IToolHarvestAbility;
import com.hbm.handler.ability.IWeaponAbility;
import com.hbm.interfaces.HalfLifeType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemAmmoEnums.*;
import com.hbm.items.ItemEnums.*;
import com.hbm.items.armor.*;
import com.hbm.items.armor.IArmorDisableModel.EnumPlayerPart;
import com.hbm.items.bomb.*;
import com.hbm.items.food.*;
import com.hbm.items.machine.*;
import com.hbm.items.machine.ItemFELCrystal.EnumWavelengths;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.machine.ItemPlateFuel.FunctionEnum;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.items.machine.ItemRBMKRod.EnumBurnFunc;
import com.hbm.items.machine.ItemRBMKRod.EnumDepleteFunc;
import com.hbm.items.machine.ItemRTGPelletDepleted.DepletedRTGMaterial;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.items.special.*;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.items.tool.*;
import com.hbm.items.tool.ItemToolAbility.EnumToolType;
import com.hbm.items.weapon.*;
import com.hbm.items.weapon.ItemCustomMissilePart.*;
import com.hbm.items.weapon.ItemMissile.MissileFormFactor;
import com.hbm.items.weapon.ItemMissile.MissileFuel;
import com.hbm.items.weapon.ItemMissile.MissileTier;
import com.hbm.items.weapon.grenade.ItemGrenadeKyiv;
import com.hbm.items.weapon.sedna.factory.GunFactory;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.rbmk.IRBMKFluxReceiver.NType;
import com.hbm.util.RTGUtil;

import api.hbm.block.IToolable.ToolType;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModItems {

	public static HashSet<Item> excludeNEI = new HashSet();
	public static Item billet_uzh;


	public static void mainRegistry() {
		initializeItem();
		registerItem();
	}
	public static Item ncrpa_helmet;
	public static Item ncrpa_plate;
	public static Item ncrpa_legs;
	public static Item ncrpa_boots;
	public static Item redstone_sword;
	public static Item big_sword;

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
	public static Item ingot_pu241;
	public static Item ingot_pu_mix;
	public static Item ingot_am241;
	public static Item ingot_am242;
	public static Item ingot_am_mix;
	public static Item ingot_neptunium;
	public static Item ingot_polonium;
	public static Item ingot_technetium;
	public static Item ingot_co60;
	public static Item ingot_sr90;
	public static Item ingot_au198;
	public static Item ingot_pb209;
	public static Item ingot_ra226;
	public static Item ingot_titanium;
	public static Item ingot_cobalt;
	public static Item sulfur;
	public static Item nitra;
	public static Item nitra_small;

	public static Item coke;
	public static Item lignite;
	public static Item powder_lignite;
	public static Item briquette;
	public static Item coal_infernal;
	public static Item cinnebar;
	public static Item powder_ash;
	public static Item powder_limestone;
	public static Item powder_cement;

	public static Item niter;
	public static Item ingot_copper;
	public static Item ingot_red_copper;
	public static Item ingot_tungsten;
	public static Item ingot_tungsten_carbide;
	public static Item ingot_aluminium;
	public static Item fluorite;
	public static Item ingot_beryllium;
	public static Item ingot_schraranium;
	public static Item ingot_schrabidium;
	public static Item ingot_schrabidate;
	public static Item ingot_plutonium_fuel;
	public static Item ingot_neptunium_fuel;
	public static Item ingot_uranium_fuel;
	public static Item ingot_mox_fuel;
	public static Item ingot_americium_fuel;
	public static Item ingot_schrabidium_fuel;
	public static Item ingot_thorium_fuel;
	public static Item nugget_uranium_fuel;
	public static Item nugget_thorium_fuel;
	public static Item nugget_plutonium_fuel;
	public static Item nugget_neptunium_fuel;
	public static Item nugget_mox_fuel;
	public static Item nugget_americium_fuel;
	public static Item nugget_schrabidium_fuel;
	public static Item ingot_advanced_alloy;
	public static Item ingot_tcalloy;
	public static Item ingot_cdalloy;
	public static Item ingot_bismuth_bronze;
	public static Item ingot_arsenic_bronze;
	public static Item ingot_bscco;
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
	public static Item ingot_c4;
	public static Item ingot_boron;
	public static Item ingot_graphite;
	public static Item ingot_firebrick;
	public static Item ingot_smore;

	public static Item ingot_gh336;
	public static Item nugget_gh336;

	public static Item ingot_australium;
	public static Item nugget_australium;
	public static Item nugget_australium_lesser;
	public static Item nugget_australium_greater;

	public static Item ingot_desh;
	public static Item nugget_desh;
	public static Item ingot_dineutronium;
	public static Item nugget_dineutronium;
	public static Item powder_dineutronium;
	public static Item ingot_tetraneutronium;
	public static Item nugget_tetraneutronium;
	public static Item powder_tetraneutronium;
	public static Item ingot_starmetal;
	public static Item ingot_gunmetal;
	public static Item plate_gunmetal;
	public static Item ingot_weaponsteel;
	public static Item plate_weaponsteel;
	public static Item ingot_saturnite;
	public static Item plate_saturnite;
	public static Item ingot_ferrouranium;
	public static Item ingot_electronium;
	public static Item nugget_zirconium;
	public static Item nugget_mercury;
	public static Item ingot_mercury; //It's to prevent any ambiguity, as it was treated as a full ingot in the past anyway
	public static Item bottle_mercury;

	public static Item ore_byproduct; //byproduct of variable purity and quantity, can be treated as a nugget, might require shredding or acidizing, depends on the type

	public static Item ore_bedrock;
	public static Item ore_centrifuged;
	public static Item ore_cleaned;
	public static Item ore_separated;
	public static Item ore_purified;
	public static Item ore_nitrated;
	public static Item ore_nitrocrystalline;
	public static Item ore_deepcleaned;
	public static Item ore_seared;
	//public static Item ore_radcleaned;
	public static Item ore_enriched; //final stage
	public static Item bedrock_ore_base;
	public static Item bedrock_ore;
	public static Item bedrock_ore_fragment;

	public static Item billet_uranium;
	public static Item billet_u233;
	public static Item billet_u235;
	public static Item billet_u238;
	public static Item billet_th232;
	public static Item billet_plutonium;
	public static Item billet_pu238;
	public static Item billet_pu239;
	public static Item billet_pu240;
	public static Item billet_pu241;
	public static Item billet_pu_mix;
	public static Item billet_am241;
	public static Item billet_am242;
	public static Item billet_am_mix;
	public static Item billet_neptunium;
	public static Item billet_polonium;
	public static Item billet_technetium;
	public static Item billet_cobalt;
	public static Item billet_co60;
	public static Item billet_sr90;
	public static Item billet_au198;
	public static Item billet_pb209;
	public static Item billet_ra226;
	public static Item billet_actinium;
	public static Item billet_schrabidium;
	public static Item billet_solinium;
	public static Item billet_gh336;
	public static Item billet_australium;
	public static Item billet_australium_lesser;
	public static Item billet_australium_greater;
	public static Item billet_uranium_fuel;
	public static Item billet_thorium_fuel;
	public static Item billet_plutonium_fuel;
	public static Item billet_neptunium_fuel;
	public static Item billet_mox_fuel;
	public static Item billet_americium_fuel;
	public static Item billet_les;
	public static Item billet_schrabidium_fuel;
	public static Item billet_hes;
	public static Item billet_po210be;
	public static Item billet_ra226be;
	public static Item billet_pu238be;
	public static Item billet_yharonite;
	public static Item billet_balefire_gold;
	public static Item billet_flashlead;
	public static Item billet_zfb_bismuth;
	public static Item billet_zfb_pu241;
	public static Item billet_zfb_am_mix;
	public static Item billet_beryllium;
	public static Item billet_bismuth;
	public static Item billet_zirconium;
	public static Item billet_nuclear_waste;

	public static Item nugget_th232;
	public static Item nugget_uranium;
	public static Item nugget_u233;
	public static Item nugget_u235;
	public static Item nugget_u238;
	public static Item nugget_plutonium;
	public static Item nugget_pu238;
	public static Item nugget_pu239;
	public static Item nugget_pu240;
	public static Item nugget_pu241;
	public static Item nugget_pu_mix;
	public static Item nugget_am241;
	public static Item nugget_am242;
	public static Item nugget_am_mix;
	public static Item nugget_neptunium;
	public static Item nugget_polonium;
	public static Item nugget_technetium;
	public static Item nugget_cobalt;
	public static Item nugget_co60;
	public static Item nugget_sr90;
	public static Item nugget_au198;
	public static Item nugget_pb209;
	public static Item nugget_ra226;
	public static Item nugget_actinium;
	public static Item plate_titanium;
	public static Item plate_aluminium;
	public static Item neutron_reflector;
	public static Item ingot_steel;
	public static Item plate_steel;
	public static Item plate_iron;
	public static Item ingot_lead;
	public static Item nugget_lead;
	public static Item ingot_bismuth;
	public static Item nugget_bismuth;
	public static Item ingot_arsenic;
	public static Item nugget_arsenic;
	public static Item ingot_tantalium;
	public static Item nugget_tantalium;
	public static Item ingot_silicon;
	public static Item billet_silicon;
	public static Item nugget_silicon;
	public static Item ingot_niobium;
	public static Item nugget_niobium;
	public static Item ingot_osmiridium;
	public static Item nugget_osmiridium;
	public static Item plate_lead;
	public static Item plate_dura_steel;
	public static Item nugget_schrabidium;
	public static Item plate_schrabidium;
	public static Item plate_copper;
	public static Item nugget_beryllium;
	public static Item plate_gold;
	public static Item hazmat_cloth;
	public static Item hazmat_cloth_red;
	public static Item hazmat_cloth_grey;
	public static Item asbestos_cloth;
	public static Item rag;
	public static Item rag_damp;
	public static Item rag_piss;
	public static Item filter_coal;
	public static Item plate_advanced_alloy;
	public static Item plate_combine_steel;
	public static Item plate_mixed;
	public static Item plate_paa;
	@Deprecated public static Item pipes_steel;
	public static Item drill_titanium;
	public static Item plate_dalekanium;
	public static Item plate_euphemium;
	public static Item bolt;
	public static Item bolt_spike;
	public static Item plate_polymer;
	public static Item plate_kevlar;
	public static Item plate_dineutronium;
	public static Item plate_desh;
	public static Item plate_bismuth;
	public static Item photo_panel;
	public static Item sat_base;
	public static Item thruster_nuclear;
	public static Item safety_fuse;
	public static Item part_generic;
	public static Item item_expensive;
	public static Item item_secret;
	public static Item ingot_metal;
	public static Item chemical_dye;
	public static Item crayon;

	public static Item undefined;
	public static Item fluid_display_item;
	public static Item hbm_fluid_compat;

	public static Item ball_resin;
	public static Item ingot_dura_steel;
	public static Item ingot_polymer;
	public static Item ingot_bakelite;
	public static Item ingot_biorubber;
	public static Item ingot_rubber;
	public static Item ingot_pet;
	public static Item ingot_pc;
	public static Item ingot_pvc;

	public static Item ingot_fiberglass;
	public static Item ingot_asbestos;
	public static Item powder_asbestos;
	public static Item ingot_calcium;
	public static Item powder_calcium;
	public static Item ingot_cadmium;
	public static Item powder_cadmium;
	public static Item powder_bismuth;
	public static Item ingot_mud;
	public static Item ingot_cft;

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
	public static Item plate_armor_dnt;

	public static Item oil_tar;
	public static Item solid_fuel;
	public static Item solid_fuel_presto;
	public static Item solid_fuel_presto_triplet;
	public static Item solid_fuel_bf;
	public static Item solid_fuel_presto_bf;
	public static Item solid_fuel_presto_triplet_bf;
	public static Item rocket_fuel;

	public static Item crystal_coal;
	public static Item crystal_iron;
	public static Item crystal_gold;
	public static Item crystal_redstone;
	public static Item crystal_lapis;
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
	public static Item crystal_cobalt;
	public static Item crystal_starmetal;
	public static Item crystal_cinnebar;
	public static Item crystal_trixite;
	public static Item crystal_osmiridium;

	public static Item gem_sodalite;
	public static Item gem_tantalium;
	public static Item gem_volcanic;
	public static Item gem_rad;
	public static Item gem_alexandrite;

	public static Item powder_lead;
	public static Item powder_tantalium;
	public static Item powder_neptunium;
	public static Item powder_polonium;
	public static Item powder_co60;
	public static Item powder_sr90;
	public static Item powder_sr90_tiny;
	public static Item powder_au198;
	public static Item powder_ra226;
	public static Item powder_i131;
	public static Item powder_i131_tiny;
	public static Item powder_xe135;
	public static Item powder_xe135_tiny;
	public static Item powder_cs137;
	public static Item powder_cs137_tiny;
	public static Item powder_at209;
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
	public static Item dust_tiny;
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
	public static Item powder_tcalloy;
	public static Item powder_coal;
	public static Item powder_coal_tiny;
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
	public static Item powder_sodium;

	public static Item powder_australium;

	public static Item powder_dura_steel;
	public static Item powder_polymer;
	public static Item powder_bakelite;
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
	public static Item powder_boron_tiny;
	public static Item powder_meteorite_tiny;

	public static Item powder_coltan_ore;
	public static Item powder_coltan;
	public static Item powder_tektite;
	public static Item powder_paleogenite;
	public static Item powder_paleogenite_tiny;
	public static Item powder_impure_osmiridium;
	public static Item powder_borax;
	public static Item powder_chlorocalcite;
	public static Item powder_molysite;

	public static Item powder_lanthanium;
	public static Item powder_actinium;
	public static Item powder_boron;
	public static Item powder_desh;
	public static Item powder_semtex_mix;
	public static Item powder_desh_mix;
	public static Item powder_desh_ready;
	public static Item powder_nitan_mix;
	public static Item powder_spark_mix;
	public static Item powder_yellowcake;
	public static Item powder_magic;
	public static Item powder_balefire;
	public static Item powder_sawdust;
	public static Item powder_flux;
	public static Item powder_fertilizer;

	public static Item fragment_neodymium;
	public static Item fragment_cobalt;
	public static Item fragment_niobium;
	public static Item fragment_cerium;
	public static Item fragment_lanthanium;
	public static Item fragment_actinium;
	public static Item fragment_boron;
	public static Item fragment_meteorite;
	public static Item fragment_coltan;
	public static Item chunk_ore;

	public static Item biomass;
	public static Item biomass_compressed;
	public static Item bio_wafer;
	public static Item plant_item;

	public static Item coil_copper;
	public static Item coil_copper_torus;
	public static Item coil_tungsten;
	@Deprecated public static Item tank_steel;
	public static Item motor;
	public static Item motor_desh;
	public static Item motor_bismuth;
	public static Item centrifuge_element;
	public static Item reactor_core;
	public static Item rtg_unit;

	public static Item coil_advanced_alloy;
	public static Item coil_advanced_torus;
	public static Item coil_magnetized_tungsten;
	public static Item coil_gold;
	public static Item coil_gold_torus;
	public static Item chlorine_pinwheel;
	public static Item deuterium_filter;

	public static Item parts_legendary;

	public static Item circuit;

	public static Item crt_display;
	public static ItemEnumMulti circuit_star_piece;
	public static ItemEnumMulti circuit_star_component;
	public static Item circuit_star;

	public static Item assembly_nuke;

	public static Item casing;

	public static Item wiring_red_copper;
	public static Item wrench;

	public static Item shell;
	public static Item pipe;
	public static Item fins_flat;
	public static Item fins_small_steel;
	public static Item fins_big_steel;
	public static Item fins_tri_steel;
	public static Item fins_quad_titanium;
	public static Item sphere_steel;
	public static Item pedestal_steel;
	public static Item dysfunctional_reactor;
	public static Item blade_titanium;
	public static Item turbine_titanium;
	public static Item blade_tungsten;
	public static Item turbine_tungsten;
	public static Item ring_starmetal;
	public static Item flywheel_beryllium;

	public static Item gear_large;
	public static Item sawblade;

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
	public static Item warhead_mirv;
	public static Item warhead_volcano;

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

	public static Item combine_scrap;

	public static Item shimmer_head;
	public static Item shimmer_axe_head;
	public static Item shimmer_handle;

	//public static Item telepad;
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
	public static Item stamp_desh_flat;
	public static Item stamp_desh_plate;
	public static Item stamp_desh_wire;
	public static Item stamp_desh_circuit;
	public static Item stamp_book;

	public static Item stamp_357;
	public static Item stamp_44;
	public static Item stamp_9;
	public static Item stamp_50;

	public static Item stamp_desh_357;
	public static Item stamp_desh_44;
	public static Item stamp_desh_9;
	public static Item stamp_desh_50;

	public static Item blades_steel;
	public static Item blades_titanium;
	public static Item blades_advanced_alloy;
	public static Item blades_desh;

	public static Item mold_base;
	public static Item mold;
	public static Item scraps;
	public static Item ingot_raw;
	public static Item plate_cast;
	public static Item plate_welded;
	public static Item wire_fine;
	public static Item wire_dense;
	public static Item part_barrel_light;
	public static Item part_barrel_heavy;
	public static Item part_receiver_light;
	public static Item part_receiver_heavy;
	public static Item part_mechanism;
	public static Item part_stock;
	public static Item part_grip;

	public static Item part_lithium;
	public static Item part_beryllium;
	public static Item part_carbon;
	public static Item part_copper;
	public static Item part_plutonium;

	public static Item laser_crystal_co2;
	public static Item laser_crystal_bismuth;
	public static Item laser_crystal_cmb;
	public static Item laser_crystal_dnt;
	public static Item laser_crystal_digamma;

	public static Item thermo_element;

	public static Item catalytic_converter;
	public static Item crackpipe;

	public static Item pellet_rtg_depleted;

	public static Item pellet_rtg_radium;
	public static Item pellet_rtg_weak;
	public static Item pellet_rtg;
	public static Item pellet_rtg_strontium;
	public static Item pellet_rtg_cobalt;
	public static Item pellet_rtg_actinium;
	public static Item pellet_rtg_polonium;
	public static Item pellet_rtg_americium;
	public static Item pellet_rtg_gold;
	public static Item pellet_rtg_lead;

	@Deprecated public static Item tritium_deuterium_cake;

	public static Item piston_selenium;
	public static Item piston_set;
	public static Item drillbit;

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
	public static Item cell_balefire;

	public static Item demon_core_open;
	public static Item demon_core_closed;

	public static Item pa_coil;

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
	public static Item particle_tachyon;
	public static Item particle_strange;
	public static Item particle_dark;
	public static Item particle_sparkticle;
	public static Item particle_digamma;
	public static Item particle_lutece;

	public static Item pellet_antimatter;
	public static Item singularity;
	public static Item singularity_counter_resonant;
	public static Item singularity_super_heated;
	public static Item black_hole;
	public static Item singularity_spark;
	public static Item crystal_xen;
	public static Item inf_water;
	public static Item inf_water_mk2;

	public static Item fuel_additive;

	public static Item canister_empty;
	public static Item canister_full;
	public static Item canister_napalm;

	public static Item gas_empty;
	public static Item gas_full;

	public static Item fluid_tank_full;
	public static Item fluid_tank_empty;
	public static Item fluid_tank_lead_full;
	public static Item fluid_tank_lead_empty;
	public static Item fluid_barrel_full;
	public static Item fluid_barrel_empty;
	public static Item fluid_barrel_infinite;
	public static Item fluid_pack_full;
	public static Item fluid_pack_empty;
	public static Item pipette;
	public static Item pipette_boron;
	public static Item pipette_laboratory;
	public static Item siphon;

	public static Item disperser_canister_empty;
	public static Item disperser_canister;
	public static Item glyphid_gland;
	public static Item glyphid_gland_empty;

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
	public static Item syringe_mkunicorn;
	public static Item iv_empty;
	public static Item iv_blood;
	public static Item iv_xp_empty;
	public static Item iv_xp;
	public static Item radaway;
	public static Item radaway_strong;
	public static Item radaway_flush;
	public static Item radx;
	public static Item siox;
	public static Item pill_herbal;
	public static Item xanax;
	public static Item fmn;
	public static Item five_htp;
	public static Item med_bag;
	public static Item pill_iodine;
	public static Item plan_c;
	public static Item pill_red;
	public static Item stealth_boy;
	public static Item gas_mask_filter;
	public static Item gas_mask_filter_mono;
	public static Item gas_mask_filter_combo;
	public static Item gas_mask_filter_rag;
	public static Item gas_mask_filter_piss;
	public static Item jetpack_tank;
	public static Item gun_kit_1;
	public static Item gun_kit_2;
	public static Item cbt_device;
	public static Item cigarette;

	public static Item can_empty;
	public static Item can_smart;
	public static Item can_creature;
	public static Item can_redbomb;
	public static Item can_mrsugar;
	public static Item can_overcharge;
	public static Item can_luna;
	public static Item can_bepis;
	public static Item can_breen;
	public static Item can_mug;
	public static Item mucho_mango;
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
	public static Item flask_empty;
	public static Item flask_infusion;
	public static Item chocolate_milk;
	public static Item coffee;
	public static Item coffee_radium;
	public static Item chocolate;
	public static Item cap_nuka;
	public static Item cap_quantum;
	public static Item cap_sparkle;
	public static Item cap_rad;
	public static Item cap_korl;
	public static Item cap_fritz;
	public static Item ring_pull;
	public static Item bdcl;
	public static ItemEnumMulti canned_conserve;
	public static Item can_key;

	public static Item boat_rubber;
	public static Item cart;
	public static Item train;
	public static Item drone;

	public static Item coin_creeper;
	public static Item coin_radiation;
	public static Item coin_maskman;
	public static Item coin_worm;
	public static Item coin_ufo;
	public static Item coin_token;
	//public static Item coin_siege;
	//public static Item source;

	public static Item rod_empty;
	public static Item rod;
	public static Item rod_dual_empty;
	public static Item rod_dual;
	public static Item rod_quad_empty;
	public static Item rod_quad;

	public static Item rod_zirnox_empty;
	public static Item rod_zirnox_tritium;
	public static ItemEnumMulti rod_zirnox;

	public static Item rod_zirnox_natural_uranium_fuel_depleted;
	public static Item rod_zirnox_uranium_fuel_depleted;
	public static Item rod_zirnox_thorium_fuel_depleted;
	public static Item rod_zirnox_mox_fuel_depleted;
	public static Item rod_zirnox_plutonium_fuel_depleted;
	public static Item rod_zirnox_u233_fuel_depleted;
	public static Item rod_zirnox_u235_fuel_depleted;
	public static Item rod_zirnox_les_fuel_depleted;
	public static Item rod_zirnox_zfb_mox_depleted;

	public static Item waste_natural_uranium;
	public static Item waste_uranium;
	public static Item waste_thorium;
	public static Item waste_mox;
	public static Item waste_plutonium;
	public static Item waste_u233;
	public static Item waste_u235;
	public static Item waste_schrabidium;
	public static Item waste_zfb_mox;

	public static Item waste_plate_u233;
	public static Item waste_plate_u235;
	public static Item waste_plate_mox;
	public static Item waste_plate_pu239;
	public static Item waste_plate_sa326;
	public static Item waste_plate_ra226be;
	public static Item waste_plate_pu238be;

	public static Item pile_rod_uranium;
	public static Item pile_rod_pu239;
	public static Item pile_rod_plutonium;
	public static Item pile_rod_source;
	public static Item pile_rod_boron;
	public static Item pile_rod_lithium;
	public static Item pile_rod_detector;

	public static Item plate_fuel_u233;
	public static Item plate_fuel_u235;
	public static Item plate_fuel_mox;
	public static Item plate_fuel_pu239;
	public static Item plate_fuel_sa326;
	public static Item plate_fuel_ra226be;
	public static Item plate_fuel_pu238be;

	public static Item pwr_fuel;
	public static Item pwr_fuel_hot;
	public static Item pwr_fuel_depleted;
	public static Item pwr_printer;

	public static Item rbmk_lid;
	public static Item rbmk_lid_glass;
	public static Item rbmk_fuel_empty;
	public static ItemRBMKRod rbmk_fuel_ueu;
	public static ItemRBMKRod rbmk_fuel_meu;
	public static ItemRBMKRod rbmk_fuel_heu233;
	public static ItemRBMKRod rbmk_fuel_heu235;
	public static ItemRBMKRod rbmk_fuel_thmeu;
	public static ItemRBMKRod rbmk_fuel_lep;
	public static ItemRBMKRod rbmk_fuel_mep;
	public static ItemRBMKRod rbmk_fuel_hep239;
	public static ItemRBMKRod rbmk_fuel_hep241;
	public static ItemRBMKRod rbmk_fuel_lea;
	public static ItemRBMKRod rbmk_fuel_mea;
	public static ItemRBMKRod rbmk_fuel_hea241;
	public static ItemRBMKRod rbmk_fuel_hea242;
	public static ItemRBMKRod rbmk_fuel_men;
	public static ItemRBMKRod rbmk_fuel_hen;
	public static ItemRBMKRod rbmk_fuel_mox;
	public static ItemRBMKRod rbmk_fuel_les;
	public static ItemRBMKRod rbmk_fuel_mes;
	public static ItemRBMKRod rbmk_fuel_hes;
	public static ItemRBMKRod rbmk_fuel_leaus;
	public static ItemRBMKRod rbmk_fuel_heaus;
	public static ItemRBMKRod rbmk_fuel_po210be;
	public static ItemRBMKRod rbmk_fuel_ra226be;
	public static ItemRBMKRod rbmk_fuel_pu238be;
	public static ItemRBMKRod rbmk_fuel_balefire_gold;
	public static ItemRBMKRod rbmk_fuel_flashlead;
	public static ItemRBMKRod rbmk_fuel_balefire;
	public static ItemRBMKRod rbmk_fuel_zfb_bismuth;
	public static ItemRBMKRod rbmk_fuel_zfb_pu241;
	public static ItemRBMKRod rbmk_fuel_zfb_am_mix;
	public static ItemRBMKRod rbmk_fuel_drx;
	public static ItemRBMKRod rbmk_fuel_test;
	//public static ItemRBMKRod rbmk_fuel_curve;
	public static ItemRBMKPellet rbmk_pellet_ueu;
	public static ItemRBMKPellet rbmk_pellet_meu;
	public static ItemRBMKPellet rbmk_pellet_heu233;
	public static ItemRBMKPellet rbmk_pellet_heu235;
	public static ItemRBMKPellet rbmk_pellet_thmeu;
	public static ItemRBMKPellet rbmk_pellet_lep;
	public static ItemRBMKPellet rbmk_pellet_mep;
	public static ItemRBMKPellet rbmk_pellet_hep239;
	public static ItemRBMKPellet rbmk_pellet_hep241;
	public static ItemRBMKPellet rbmk_pellet_lea;
	public static ItemRBMKPellet rbmk_pellet_mea;
	public static ItemRBMKPellet rbmk_pellet_hea241;
	public static ItemRBMKPellet rbmk_pellet_hea242;
	public static ItemRBMKPellet rbmk_pellet_men;
	public static ItemRBMKPellet rbmk_pellet_hen;
	public static ItemRBMKPellet rbmk_pellet_mox;
	public static ItemRBMKPellet rbmk_pellet_les;
	public static ItemRBMKPellet rbmk_pellet_mes;
	public static ItemRBMKPellet rbmk_pellet_hes;
	public static ItemRBMKPellet rbmk_pellet_leaus;
	public static ItemRBMKPellet rbmk_pellet_heaus;
	public static ItemRBMKPellet rbmk_pellet_po210be;
	public static ItemRBMKPellet rbmk_pellet_ra226be;
	public static ItemRBMKPellet rbmk_pellet_pu238be;
	public static ItemRBMKPellet rbmk_pellet_balefire_gold;
	public static ItemRBMKPellet rbmk_pellet_flashlead;
	public static ItemRBMKPellet rbmk_pellet_balefire;
	public static ItemRBMKPellet rbmk_pellet_zfb_bismuth;
	public static ItemRBMKPellet rbmk_pellet_zfb_pu241;
	public static ItemRBMKPellet rbmk_pellet_zfb_am_mix;
	public static ItemRBMKPellet rbmk_pellet_drx;

	public static Item watz_pellet;
	public static Item watz_pellet_depleted;

	public static Item icf_pellet_empty;
	public static Item icf_pellet;
	public static Item icf_pellet_depleted;

	public static Item scrap_plastic;
	public static Item scrap;
	public static Item scrap_oil;
	public static Item scrap_nuclear;
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
	public static Item nuclear_waste_vitrified;
	public static Item nuclear_waste_vitrified_tiny;

	public static Item debris_graphite;
	public static Item debris_metal;
	public static Item debris_fuel;
	public static Item debris_concrete;
	public static Item debris_exchanger;
	public static Item debris_shrapnel;
	public static Item debris_element;

	public static Item containment_box;
	public static Item plastic_bag;

	public static Item ammo_bag;
	public static Item ammo_bag_infinite;
	public static Item casing_bag;

	public static Item cordite;
	public static Item ballistite;
	public static Item ball_dynamite;
	public static Item ball_tnt;
	public static Item ball_tatb;
	public static Item ball_fireclay;

	public static Item pellet_cluster;
	public static Item powder_fire;
	public static Item powder_ice;
	public static Item powder_poison;
	public static Item powder_thermite;
	public static Item pellet_gas;
	public static Item magnetron;
	public static Item pellet_buckshot;
	public static Item pellet_charged;

	public static Item rangefinder;
	public static Item designator;
	public static Item designator_range;
	public static Item designator_manual;
	public static Item designator_arty_range;
	public static Item linker;
	public static Item reactor_sensor;
	public static Item oil_detector;
	public static Item dosimeter;
	public static Item geiger_counter;
	public static Item digamma_diagnostic;
	public static Item pollution_detector;
	public static Item ore_density_scanner;
	public static Item survey_scanner;
	public static Item mirror_tool;
	public static Item rbmk_tool;
	public static Item coltan_tool;
	public static Item power_net_tool;
	public static Item analysis_tool;
	public static Item coupling_tool;
	public static Item drone_linker;
	public static Item radar_linker;
	public static Item settings_tool;
	public static Item rtty_pager;

	public static Item blueprints;
	public static Item blueprint_folder;
	public static Item template_folder;
	@Deprecated public static Item assembly_template;
	@Deprecated public static Item chemistry_template;
	@Deprecated public static Item chemistry_icon;
	public static Item crucible_template;
	public static Item fluid_identifier_multi;
	public static Item fluid_icon;
	public static Item siren_track;
	public static Item fluid_duct;

	public static Item bobmazon;
	public static Item bobmazon_hidden;

	public static Item launch_code_piece;
	public static Item launch_code;
	public static Item launch_key;

	public static Item missile_assembly;
	public static Item missile_generic;
	public static Item missile_anti_ballistic;
	public static Item missile_incendiary;
	public static Item missile_cluster;
	public static Item missile_buster;
	public static Item missile_decoy;
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
	public static Item missile_volcano;
	public static Item missile_doomsday;
	public static Item missile_doomsday_rusted;
	public static Item missile_taint;
	public static Item missile_micro;
	public static Item missile_bhole;
	public static Item missile_schrabidium;
	public static Item missile_emp;
	public static Item missile_shuttle;
	public static Item missile_stealth;
	public static Item missile_test;

	public static Item mp_thruster_10_kerosene;
	public static Item mp_thruster_10_solid;
	public static Item mp_thruster_10_xenon;
	public static Item mp_thruster_15_kerosene;
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
	public static Item mp_fuselage_15_solid_mimi;

	public static Item mp_fuselage_15_hydrogen;
	public static Item mp_fuselage_15_hydrogen_cathedral;

	public static Item mp_fuselage_15_balefire;

	public static Item mp_fuselage_15_20_kerosene;
	public static Item mp_fuselage_15_20_kerosene_magnusson;
	public static Item mp_fuselage_15_20_solid;

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
	public static Item mp_warhead_15_nuclear_mimi;
	public static Item mp_warhead_15_boxcar;
	public static Item mp_warhead_15_n2;
	public static Item mp_warhead_15_balefire;
	public static Item mp_warhead_15_turbine;

	public static Item mp_chip_1;
	public static Item mp_chip_2;
	public static Item mp_chip_3;
	public static Item mp_chip_4;
	public static Item mp_chip_5;

	public static Item missile_custom;

	public static Item missile_soyuz;
	public static Item missile_soyuz_lander;
	public static Item sat_mapper;
	public static Item sat_scanner;
	public static Item sat_radar;
	public static Item sat_laser;
	public static Item sat_foeq;
	public static Item sat_resonator;
	public static Item sat_miner;
	public static Item sat_lunar_miner;
	public static Item sat_gerald;
	public static Item sat_chip;
	public static Item sat_interface;
	public static Item sat_coord;
	public static Item sat_designator;
	public static Item sat_relay;

	public static ItemEnumMulti ammo_misc;
	public static ItemEnumMulti ammo_shell;
	public static ItemEnumMulti ammo_fireext;

	public static Item ammo_dgk;
	public static Item ammo_arty;
	public static Item ammo_himars;

	public static Item gun_b92;
	public static Item gun_b92_ammo;
	public static Item gun_fireext;

	public static Item gun_debug;
	public static Item ammo_debug;

	public static Item gun_pepperbox;
	public static Item gun_light_revolver;
	public static Item gun_light_revolver_atlas;
	public static Item gun_light_revolver_dani;
	public static Item gun_henry;
	public static Item gun_henry_lincoln;
	public static Item gun_greasegun;
	public static Item gun_maresleg;
	public static Item gun_maresleg_akimbo;
	public static Item gun_maresleg_broken;
	public static Item gun_flaregun;
	public static Item gun_heavy_revolver;
	public static Item gun_heavy_revolver_lilmac;
	public static Item gun_heavy_revolver_protege;
	public static Item gun_carbine;
	public static Item gun_am180;
	public static Item gun_liberator;
	public static Item gun_congolake;
	public static Item gun_flamer;
	public static Item gun_flamer_topaz;
	public static Item gun_flamer_daybreaker;
	public static Item gun_uzi;
	public static Item gun_uzi_akimbo;
	public static Item gun_spas12;
	public static Item gun_panzerschreck;
	public static Item gun_star_f;
	public static Item gun_star_f_akimbo;
	public static Item gun_g3;
	public static Item gun_g3_zebra;
	public static Item gun_stinger;
	public static Item gun_mk108;
	public static Item gun_chemthrower;
	public static Item gun_amat;
	public static Item gun_amat_subtlety;
	public static Item gun_amat_penance;
	public static Item gun_m2;
	public static Item gun_autoshotgun;
	public static Item gun_autoshotgun_shredder;
	public static Item gun_autoshotgun_sexy;
	public static Item gun_autoshotgun_heretic;
	public static Item gun_quadro;
	public static Item gun_lag;
	public static Item gun_minigun;
	public static Item gun_minigun_dual;
	public static Item gun_minigun_lacunae;
	public static Item gun_missile_launcher;
	public static Item gun_tesla_cannon;
	public static Item gun_laser_pistol;
	public static Item gun_laser_pistol_pew_pew;
	public static Item gun_laser_pistol_morning_glory;
	public static Item gun_stg77;
	public static Item gun_tau;
	public static Item gun_fatman;
	public static Item gun_lasrifle;
	public static Item gun_coilgun;
	public static Item gun_hangman;
	public static Item gun_mas36;
	public static Item gun_bolter;
	public static Item gun_folly;
	public static Item gun_aberrator;
	public static Item gun_aberrator_eott;
	public static Item gun_double_barrel;
	public static Item gun_double_barrel_sacred_dragon;
	public static Item gun_n_i_4_n_i;
	public static Item gun_charge_thrower;
	public static Item gun_drill;

	public static Item ammo_standard;
	public static Item ammo_secret;

	public static Item weapon_mod_test;
	public static Item weapon_mod_generic;
	public static Item weapon_mod_special;
	public static Item weapon_mod_caliber;

	public static Item crucible;

	public static Item stick_dynamite;
	public static Item stick_dynamite_fishing;
	public static Item stick_tnt;
	public static Item stick_semtex;
	public static Item stick_c4;

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
	public static Item grenade_universal;
	public static Item grenade_shell;
	public static Item grenade_filling;
	public static Item grenade_fuze;
	public static Item grenade_extra;
	public static Item grenade_kyiv;
	public static Item grenade_mk2;
	public static Item grenade_aschrab;
	public static Item chopper_head;
	public static Item chopper_torso;
	public static Item chopper_wing;
	public static Item chopper_tail;
	public static Item chopper_gun;
	public static Item chopper_blades;
	public static Item t45_kit;
	public static Item rbmk_fuel_uzh;
	public static Item rbmk_pellet_uzh;
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
	public static Item marshmallow;
	public static Item cheese;
	public static Item quesadilla;
	public static Item glyphid_meat;
	public static Item glyphid_meat_grilled;
	public static Item egg_glyphid;

	public static Item med_ipecac;
	public static Item med_ptsd;
	public static Item med_schizophrenia;

	public static Item canteen_vodka;

	public static Item defuser;
	public static Item reacher;
	public static Item bismuth_tool;
	public static Item meltdown_tool;

	public static Item flame_pony;
	public static Item flame_conspiracy;
	public static Item flame_politics;
	public static Item flame_opinion;

	//public static Item gadget_explosive;
	public static Item early_explosive_lenses;
	public static Item explosive_lenses;
	public static Item gadget_wireing;
	public static Item gadget_core;
	public static Item boy_igniter;
	public static Item boy_propellant;
	public static Item boy_bullet;
	public static Item boy_target;
	public static Item boy_shielding;
	//public static Item man_explosive;
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
	
	public static Item battery_pack;
	public static Item battery_creative;
	public static Item cube_power;
	public static Item battery_spark;
	public static Item fusion_core_infinite;
	public static Item battery_trixite;
	
	public static Item battery_sc;

	public static Item battery_potato;
	public static Item battery_potatos;
	public static Item hev_battery;
	public static Item fusion_core;
	public static Item energy_core;
	public static Item fuse;
	public static Item redcoil_capacitor;
	public static Item euphemium_capacitor;
	//by using these in crafting table recipes, i'm running the risk of making my recipes too greg-ian (which i don't like)
	//in the event that i forget about the meaning of the word "sparingly", please throw a brick at my head
	public static Item screwdriver;
	public static Item screwdriver_desh;
	public static Item hand_drill;
	public static Item hand_drill_desh;
	public static Item wrench_archineer;
	public static Item chemistry_set;
	public static Item chemistry_set_boron;
	public static Item blowtorch;
	public static Item acetylene_torch;
	public static Item boltgun;
	public static Item arc_electrode;
	public static Item arc_electrode_burnt;

	public static Item upgrade_muffler;

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
	public static Item upgrade_gc_speed;
	public static Item upgrade_5g;
	public static Item upgrade_stack;
	public static Item upgrade_ejector;

	public static Item ingot_euphemium;
	public static Item nugget_euphemium;
	public static Item euphemium_helmet;
	public static Item euphemium_plate;
	public static Item euphemium_legs;
	public static Item euphemium_boots;
	public static Item apple_euphemium;
	public static Item watch;

	public static Item goggles;
	public static Item ashglasses;
	public static Item gas_mask;
	public static Item gas_mask_m65;
	public static Item gas_mask_mono;
	public static Item gas_mask_olde;
	public static Item mask_rag;
	public static Item mask_piss;
	public static Item hat;
	public static Item beta;
	public static Item no9;

	@Deprecated public static Item t45_helmet;
	@Deprecated public static Item t45_plate;
	@Deprecated public static Item t45_legs;
	@Deprecated public static Item t45_boots;
	public static Item t51_helmet;
	public static Item t51_plate;
	public static Item t51_legs;
	public static Item t51_boots;
	public static Item steamsuit_helmet;
	public static Item steamsuit_plate;
	public static Item steamsuit_legs;
	public static Item steamsuit_boots;
	public static Item dieselsuit_helmet;
	public static Item dieselsuit_plate;
	public static Item dieselsuit_legs;
	public static Item dieselsuit_boots;

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
	public static Item rpa_helmet;
	public static Item rpa_plate;
	public static Item rpa_legs;
	public static Item rpa_boots;
	public static Item bismuth_helmet;
	public static Item bismuth_plate;
	public static Item bismuth_legs;
	public static Item bismuth_boots;
	public static Item bj_helmet;
	public static Item bj_plate;
	public static Item bj_plate_jetpack;
	public static Item bj_legs;
	public static Item bj_boots;
	public static Item envsuit_helmet;
	public static Item envsuit_plate;
	public static Item envsuit_legs;
	public static Item envsuit_boots;
	public static Item hev_helmet;
	public static Item hev_plate;
	public static Item hev_legs;
	public static Item hev_boots;
	public static Item fau_helmet;
	public static Item fau_plate;
	public static Item fau_legs;
	public static Item fau_boots;
	public static Item dns_helmet;
	public static Item dns_plate;
	public static Item dns_legs;
	public static Item dns_boots;
	public static Item taurun_helmet;
	public static Item taurun_plate;
	public static Item taurun_legs;
	public static Item taurun_boots;
	public static Item trenchmaster_helmet;
	public static Item trenchmaster_plate;
	public static Item trenchmaster_legs;
	public static Item trenchmaster_boots;
	public static Item zirconium_legs;
	public static Item robes_helmet;
	public static Item robes_plate;
	public static Item robes_legs;
	public static Item robes_boots;

	public static Item jetpack_boost;
	public static Item jetpack_break;
	public static Item jetpack_fly;
	public static Item jetpack_vector;
	public static Item wings_limp;
	public static Item wings_murk;

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
	public static Item bismuth_pickaxe;
	public static Item bismuth_axe;
	public static Item volcanic_pickaxe;
	public static Item volcanic_axe;
	public static Item chlorophyte_pickaxe;
	public static Item chlorophyte_axe;
	public static Item mese_pickaxe;
	public static Item mese_axe;
	public static Item dnt_sword;
	public static Item dwarven_pickaxe;

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
	public static Item wrench_flipped;
	public static Item memespoon;

	public static Item pipe_lead;
	public static Item reer_graar;
	public static Item stopsign;
	public static Item sopsign;
	public static Item chernobylsign;

	public static Item crystal_horn;
	public static Item crystal_charred;

	public static Item attachment_mask;
	public static Item attachment_mask_mono;
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
	public static Item cladding_ghiorsium;
	public static Item cladding_iron;
	public static Item cladding_obsidian;
	public static Item insert_kevlar;
	public static Item insert_sapi;
	public static Item insert_esapi;
	public static Item insert_xsapi;
	public static Item insert_steel;
	public static Item insert_du;
	public static Item insert_polonium;
	public static Item insert_ghiorsium;
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
	public static Item black_diamond;
	public static Item wd40;
	public static Item scrumpy;
	public static Item wild_p;
	public static Item shackles;
	public static Item injector_5htp;
	public static Item injector_knife;
	public static Item medal_liquidator;
	public static Item bottled_cloud;
	public static Item protection_charm;
	public static Item meteor_charm;
	public static Item neutrino_lens;
	public static Item gas_tester;
	public static Item defuser_gold;
	public static Item ballistic_gauntlet;
	public static Item night_vision;
	public static Item card_aos;
	public static Item card_qos;
	public static Item australium_iii;
	public static Item armor_battery;
	public static Item armor_battery_mk2;
	public static Item armor_battery_mk3;

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
	public static Item liquidator_helmet;
	public static Item liquidator_plate;
	public static Item liquidator_legs;
	public static Item liquidator_boots;

	public static Item hazmat_paa_helmet;
	public static Item hazmat_paa_plate;
	public static Item hazmat_paa_legs;
	public static Item hazmat_paa_boots;

	public static Item rebar_placer;

	public static Item wand;
	public static Item wand_s;
	public static Item wand_d;

	public static Item structure_single;
	public static Item structure_solid;
	public static Item structure_pattern;
	public static Item structure_randomized;
	public static Item structure_randomly;
	public static Item structure_custommachine;

	public static Item rod_of_discord;

	@Deprecated public static Item cape_radiation;
	@Deprecated public static Item cape_gasmask;
	@Deprecated public static Item cape_schrabidium;
	@Deprecated public static Item cape_hidden;

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
	public static Item euphemium_kit;
	public static Item solinium_kit;
	public static Item hazmat_kit;
	public static Item hazmat_red_kit;
	public static Item hazmat_grey_kit;
	public static Item kit_custom;

	public static Item legacy_toolbox;
	public static Item toolbox;

	public static Item loot_10;
	public static Item loot_15;
	public static Item loot_misc;

	public static Item ammo_container;

	public static Item igniter;
	public static Item detonator;
	public static Item detonator_multi;
	public static Item detonator_laser;
	public static Item detonator_deadman;
	public static Item detonator_de;
	public static Item bomb_caller;
	public static Item meteor_remote;
	public static Item anchor_remote;
	public static Item remote;
	//public static Item turret_control;
	public static Item turret_chip;
	//public static Item turret_biometry;

	public static Item spawn_chopper;
	public static Item spawn_worm;
	public static Item spawn_ufo;
	public static Item spawn_duck;

	public static Item key;
	public static Item key_red;
	public static Item key_red_cracked;
	public static Item key_kit;
	public static Item key_fake;
	public static Item pin;
	public static Item padlock_rusty;
	public static Item padlock;
	public static Item padlock_reinforced;
	public static Item padlock_unbreakable;

	public static Item mech_key;

	public static Item bucket_mud;
	public static Item bucket_acid;
	public static Item bucket_toxic;
	public static Item bucket_schrabidic_acid;
	public static Item bucket_sulfuric_acid;

	public static Item door_metal;
	public static Item door_office;
	public static Item door_bunker;
	public static Item door_red;

	public static Item record_lc;
	public static Item record_ss;
	public static Item record_vc;
	public static Item record_glass;

	public static Item book_guide;
	public static Item book_lore;
	public static Item holotape_image;
	public static Item holotape_damaged;
	public static Item clay_tablet;

	public static Item polaroid;
	public static Item glitch;
	public static Item book_secret;
	public static Item book_of_;
	public static Item page_of_;
	public static Item book_lemegeton;
	public static Item burnt_bark;

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

	public static Item nothing;
	public static Item broken_item;

	public static Item achievement_icon;

	public static Item mysteryshovel;
	public static Item memory;

	public static Item conveyor_wand;

	public static void initializeItem() {

		redstone_sword = new RedstoneSword(ToolMaterial.STONE).setUnlocalizedName("redstone_sword").setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":redstone_sword");
		big_sword = new BigSword(ToolMaterial.EMERALD).setUnlocalizedName("big_sword").setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":big_sword");

		ingot_th232 = new Item().setUnlocalizedName("ingot_th232").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_th232");
		ingot_uranium = new Item().setUnlocalizedName("ingot_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_uranium");
		ingot_u233 = new Item().setUnlocalizedName("ingot_u233").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_u233");
		ingot_u235 = new Item().setUnlocalizedName("ingot_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_u235");
		ingot_u238 = new Item().setUnlocalizedName("ingot_u238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_u238");
		ingot_u238m2 = new ItemUnstable(350, 200).setUnlocalizedName("ingot_u238m2").setCreativeTab(null).setTextureName(RefStrings.MODID + ":ingot_u238m2");
		ingot_plutonium = new Item().setUnlocalizedName("ingot_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ingot_pu238 = new Item().setUnlocalizedName("ingot_pu238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu238");
		ingot_pu239 = new Item().setUnlocalizedName("ingot_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu239");
		ingot_pu240 = new Item().setUnlocalizedName("ingot_pu240").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu240");
		ingot_pu241 = new Item().setUnlocalizedName("ingot_pu241").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu241");
		ingot_pu_mix = new Item().setUnlocalizedName("ingot_pu_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pu_mix");
		ingot_am241 = new Item().setUnlocalizedName("ingot_am241").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_am241");
		ingot_am242 = new Item().setUnlocalizedName("ingot_am242").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_am242");
		ingot_am_mix = new Item().setUnlocalizedName("ingot_am_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_am_mix");
		ingot_neptunium = new ItemCustomLore().setUnlocalizedName("ingot_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_neptunium");
		ingot_polonium = new Item().setUnlocalizedName("ingot_polonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_polonium");
		ingot_technetium = new Item().setUnlocalizedName("ingot_technetium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_technetium");
		ingot_co60 = new Item().setUnlocalizedName("ingot_co60").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_co60");
		ingot_sr90 = new Item().setUnlocalizedName("ingot_sr90").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_sr90");
		ingot_au198 = new Item().setUnlocalizedName("ingot_au198").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_au198");
		ingot_pb209 = new Item().setUnlocalizedName("ingot_pb209").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pb209");
		ingot_ra226 = new Item().setUnlocalizedName("ingot_ra226").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_ra226");
		ingot_titanium = new Item().setUnlocalizedName("ingot_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_titanium");
		ingot_cobalt = new Item().setUnlocalizedName("ingot_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_cobalt");
		ingot_boron = new Item().setUnlocalizedName("ingot_boron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_boron");
		ingot_graphite = new Item().setUnlocalizedName("ingot_graphite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_graphite");
		ingot_firebrick = new Item().setUnlocalizedName("ingot_firebrick").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_firebrick");
		ingot_smore = new ItemFood(10, 20F, false).setUnlocalizedName("ingot_smore").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_smore");
		sulfur = new Item().setUnlocalizedName("sulfur").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sulfur");
		nitra = new Item().setUnlocalizedName("nitra").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nitra");
		nitra_small = new Item().setUnlocalizedName("nitra_small").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nitra_small");

		ingot_uranium_fuel = new Item().setUnlocalizedName("ingot_uranium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_uranium_fuel");
		ingot_plutonium_fuel = new Item().setUnlocalizedName("ingot_plutonium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_plutonium_fuel");
		ingot_neptunium_fuel = new Item().setUnlocalizedName("ingot_neptunium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_neptunium_fuel");
		ingot_mox_fuel = new Item().setUnlocalizedName("ingot_mox_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_mox_fuel");
		ingot_americium_fuel = new Item().setUnlocalizedName("ingot_americium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_americium_fuel");
		ingot_schrabidium_fuel = new Item().setUnlocalizedName("ingot_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidium_fuel");
		ingot_thorium_fuel = new Item().setUnlocalizedName("ingot_thorium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_thorium_fuel");
		nugget_uranium_fuel = new Item().setUnlocalizedName("nugget_uranium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_uranium_fuel");
		nugget_thorium_fuel = new Item().setUnlocalizedName("nugget_thorium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_thorium_fuel");
		nugget_plutonium_fuel = new Item().setUnlocalizedName("nugget_plutonium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_plutonium_fuel");
		nugget_neptunium_fuel = new Item().setUnlocalizedName("nugget_neptunium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_neptunium_fuel");
		nugget_mox_fuel = new Item().setUnlocalizedName("nugget_mox_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mox_fuel");
		nugget_americium_fuel = new Item().setUnlocalizedName("nugget_americium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_americium_fuel");
		nugget_schrabidium_fuel = new Item().setUnlocalizedName("nugget_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_schrabidium_fuel");
		ingot_advanced_alloy = new Item().setUnlocalizedName("ingot_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_advanced_alloy");
		ingot_tcalloy = new Item().setUnlocalizedName("ingot_tcalloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_tcalloy");
		ingot_cdalloy = new Item().setUnlocalizedName("ingot_cdalloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_cdalloy");
		ingot_bismuth_bronze = new Item().setUnlocalizedName("ingot_bismuth_bronze").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_bismuth_bronze");
		ingot_arsenic_bronze = new Item().setUnlocalizedName("ingot_arsenic_bronze").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_arsenic_bronze");
		ingot_bscco = new Item().setUnlocalizedName("ingot_bscco").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_bscco");

		niter = new Item().setUnlocalizedName("niter").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":salpeter");
		ingot_copper = new Item().setUnlocalizedName("ingot_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_copper");
		ingot_red_copper = new Item().setUnlocalizedName("ingot_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_red_copper");
		ingot_tungsten = new Item().setUnlocalizedName("ingot_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_tungsten");
		ingot_tungsten_carbide = new Item().setUnlocalizedName("ingot_tungsten_carbide").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_tungsten_carbide");
		ingot_aluminium = new Item().setUnlocalizedName("ingot_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_aluminium");
		fluorite = new Item().setUnlocalizedName("fluorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fluorite");
		ingot_beryllium = new Item().setUnlocalizedName("ingot_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_beryllium");
		ingot_steel = new Item().setUnlocalizedName("ingot_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_steel");
		plate_steel = new Item().setUnlocalizedName("plate_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_steel");
		plate_iron = new Item().setUnlocalizedName("plate_iron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_iron");
		ingot_lead = new Item().setUnlocalizedName("ingot_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_lead");
		plate_lead = new Item().setUnlocalizedName("plate_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_lead");
		plate_dura_steel = new Item().setUnlocalizedName("plate_dura_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_dura_steel");
		ingot_schraranium = new ItemSchraranium().setUnlocalizedName("ingot_schraranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schraranium");
		ingot_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("ingot_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidium");
		ingot_schrabidate = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("ingot_schrabidate").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_schrabidate");
		plate_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("plate_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_schrabidium");
		plate_copper = new Item().setUnlocalizedName("plate_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_copper");
		plate_gold = new Item().setUnlocalizedName("plate_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_gold");
		plate_advanced_alloy = new Item().setUnlocalizedName("plate_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_advanced_alloy");
		lithium = new Item().setUnlocalizedName("lithium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":lithium");
		ingot_zirconium = new Item().setUnlocalizedName("ingot_zirconium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_zirconium");
		ingot_semtex = new ItemLemon(4, 5, true).setUnlocalizedName("ingot_semtex").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_semtex");
		ingot_c4 = new ItemLemon(4, 5, true).setUnlocalizedName("ingot_c4").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_c4");
		ingot_phosphorus = new Item().setUnlocalizedName("ingot_phosphorus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_phosphorus");
		coil_advanced_alloy = new Item().setUnlocalizedName("coil_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_advanced_alloy");
		coil_advanced_torus = new Item().setUnlocalizedName("coil_advanced_torus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_advanced_torus");
		ingot_magnetized_tungsten = new Item().setUnlocalizedName("ingot_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_magnetized_tungsten");
		ingot_combine_steel = new ItemCustomLore().setUnlocalizedName("ingot_combine_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_combine_steel");
		plate_mixed = new Item().setUnlocalizedName("plate_mixed").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_mixed");
		plate_paa = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("plate_paa").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_paa");
		pipes_steel = new Item().setUnlocalizedName("pipes_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pipes_steel");
		drill_titanium = new Item().setUnlocalizedName("drill_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":drill_titanium");
		plate_dalekanium = new Item().setUnlocalizedName("plate_dalekanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_dalekanium");
		plate_euphemium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("plate_euphemium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_euphemium");
		bolt = new ItemAutogen(MaterialShapes.BOLT).oun("boltntm").setUnlocalizedName("bolt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bolt");
		bolt_spike = new ItemCustomLore().setUnlocalizedName("bolt_spike").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bolt_spike");
		plate_polymer = new Item().setUnlocalizedName("plate_polymer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_polymer");
		plate_kevlar = new Item().setUnlocalizedName("plate_kevlar").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_kevlar");
		plate_dineutronium = new Item().setUnlocalizedName("plate_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_dineutronium");
		plate_desh = new Item().setUnlocalizedName("plate_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_desh");
		plate_bismuth = new ItemCustomLore().setUnlocalizedName("plate_bismuth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_bismuth");
		ingot_solinium = new Item().setUnlocalizedName("ingot_solinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_solinium");
		nugget_solinium = new Item().setUnlocalizedName("nugget_solinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_solinium");
		photo_panel = new Item().setUnlocalizedName("photo_panel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":photo_panel");
		sat_base = new Item().setUnlocalizedName("sat_base").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sat_base");
		thruster_nuclear = new Item().setUnlocalizedName("thruster_nuclear").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":thruster_nuclear");
		safety_fuse = new Item().setUnlocalizedName("safety_fuse").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":safety_fuse");
		part_generic = new ItemGenericPart().setUnlocalizedName("part_generic").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_generic");
		item_expensive = new ItemExpensive().setUnlocalizedName("item_expensive").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":item_expensive");
		item_secret = new ItemEnumMulti(EnumSecretType.class, true, true).setUnlocalizedName("item_secret").setCreativeTab(null).setTextureName(RefStrings.MODID + ":item_secret");
		ingot_metal = new ItemEnumMulti(EnumIngotMetal.class, true, true).setUnlocalizedName("ingot_metal").setCreativeTab(null).setTextureName(RefStrings.MODID + ":ingot_metal");
		chemical_dye = new ItemChemicalDye().setUnlocalizedName("chemical_dye").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chemical_dye");
		crayon = new ItemCrayon().setUnlocalizedName("crayon").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crayon");

		undefined = new ItemCustomLore().setUnlocalizedName("undefined").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":undefined");

		billet_uranium = new Item().setUnlocalizedName("billet_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_uranium");
		billet_u233 = new Item().setUnlocalizedName("billet_u233").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_u233");
		billet_u235 = new Item().setUnlocalizedName("billet_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_u235");
		billet_u238 = new Item().setUnlocalizedName("billet_u238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_u238");
		billet_th232 = new Item().setUnlocalizedName("billet_th232").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_th232");
		billet_plutonium = new Item().setUnlocalizedName("billet_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_plutonium");
		billet_pu238 = new Item().setUnlocalizedName("billet_pu238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu238");
		billet_pu239 = new Item().setUnlocalizedName("billet_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu239");
		billet_pu240 = new Item().setUnlocalizedName("billet_pu240").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu240");
		billet_pu241 = new Item().setUnlocalizedName("billet_pu241").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu241");
		billet_pu_mix = new Item().setUnlocalizedName("billet_pu_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu_mix");
		billet_am241 = new Item().setUnlocalizedName("billet_am241").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_am241");
		billet_am242 = new Item().setUnlocalizedName("billet_am242").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_am242");
		billet_am_mix = new Item().setUnlocalizedName("billet_am_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_am_mix");
		billet_neptunium = new Item().setUnlocalizedName("billet_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_neptunium");
		billet_polonium = new Item().setUnlocalizedName("billet_polonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_polonium");
		billet_technetium = new Item().setUnlocalizedName("billet_technetium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_technetium");
		billet_cobalt = new Item().setUnlocalizedName("billet_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_cobalt");
		billet_co60 = new Item().setUnlocalizedName("billet_co60").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_co60");
		billet_sr90 = new Item().setUnlocalizedName("billet_sr90").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_sr90");
		billet_au198 = new Item().setUnlocalizedName("billet_au198").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_au198");
		billet_pb209 = new Item().setUnlocalizedName("billet_pb209").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pb209");
		billet_ra226 = new Item().setUnlocalizedName("billet_ra226").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_ra226");
		billet_actinium = new Item().setUnlocalizedName("billet_actinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_actinium");
		billet_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("billet_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_schrabidium");
		billet_solinium = new Item().setUnlocalizedName("billet_solinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_solinium");
		billet_gh336 = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("billet_gh336").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_gh336");
		billet_australium = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("billet_australium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_australium");
		billet_australium_lesser = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("billet_australium_lesser").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_australium_lesser");
		billet_australium_greater = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("billet_australium_greater").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_australium_greater");
		billet_uranium_fuel = new Item().setUnlocalizedName("billet_uranium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_uranium_fuel");
		billet_thorium_fuel = new Item().setUnlocalizedName("billet_thorium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_thorium_fuel");
		billet_plutonium_fuel = new Item().setUnlocalizedName("billet_plutonium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_plutonium_fuel");
		billet_neptunium_fuel = new Item().setUnlocalizedName("billet_neptunium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_neptunium_fuel");
		billet_mox_fuel = new Item().setUnlocalizedName("billet_mox_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_mox_fuel");
		billet_americium_fuel = new Item().setUnlocalizedName("billet_americium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_americium_fuel");
		billet_les = new Item().setUnlocalizedName("billet_les").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_les");
		billet_schrabidium_fuel = new Item().setUnlocalizedName("billet_schrabidium_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_schrabidium_fuel");
		billet_hes = new Item().setUnlocalizedName("billet_hes").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_hes");
		billet_po210be = new Item().setUnlocalizedName("billet_po210be").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_po210be");
		billet_ra226be = new Item().setUnlocalizedName("billet_ra226be").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_ra226be");
		billet_pu238be = new Item().setUnlocalizedName("billet_pu238be").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_pu238be");
		billet_beryllium = new Item().setUnlocalizedName("billet_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_beryllium");
		billet_bismuth = new Item().setUnlocalizedName("billet_bismuth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_bismuth");
		billet_zirconium = new Item().setUnlocalizedName("billet_zirconium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_zirconium");
		billet_yharonite = new Item().setUnlocalizedName("billet_yharonite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_yharonite");
		billet_balefire_gold = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("billet_balefire_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_balefire_gold");
		billet_flashlead = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("billet_flashlead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_flashlead");
		billet_zfb_bismuth = new Item().setUnlocalizedName("billet_zfb_bismuth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_zfb_bismuth");
		billet_zfb_pu241 = new Item().setUnlocalizedName("billet_zfb_pu241").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_zfb_pu241");
		billet_zfb_am_mix = new Item().setUnlocalizedName("billet_zfb_am_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_zfb_am_mix");
		billet_nuclear_waste = new Item().setUnlocalizedName("billet_nuclear_waste").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_nuclear_waste");

		ball_resin = new ItemCustomLore().setUnlocalizedName("ball_resin").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ball_resin");
		ingot_dura_steel = new ItemCustomLore().setUnlocalizedName("ingot_dura_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_dura_steel");
		ingot_polymer = new ItemCustomLore().setUnlocalizedName("ingot_polymer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_polymer");
		ingot_bakelite = new ItemCustomLore().setUnlocalizedName("ingot_bakelite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_bakelite");
		ingot_biorubber = new ItemCustomLore().setUnlocalizedName("ingot_biorubber").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_biorubber");
		ingot_rubber = new ItemCustomLore().setUnlocalizedName("ingot_rubber").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_rubber");
		ingot_pc = new ItemCustomLore().setUnlocalizedName("ingot_pc").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pc");
		ingot_pvc = new ItemCustomLore().setUnlocalizedName("ingot_pvc").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_pvc");
		ingot_desh = new ItemCustomLore().setUnlocalizedName("ingot_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_desh");
		nugget_desh = new ItemCustomLore().setUnlocalizedName("nugget_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_desh");
		ingot_dineutronium = new ItemCustomLore().setUnlocalizedName("ingot_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_dineutronium");
		nugget_dineutronium = new ItemCustomLore().setUnlocalizedName("nugget_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_dineutronium");
		powder_dineutronium = new ItemCustomLore().setUnlocalizedName("powder_dineutronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_dineutronium");
		ingot_starmetal = new ItemStarmetal().setUnlocalizedName("ingot_starmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_starmetal");
		ingot_gunmetal = new Item().setUnlocalizedName("ingot_gunmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_gunmetal");
		plate_gunmetal = new Item().setUnlocalizedName("plate_gunmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_gunmetal");
		ingot_weaponsteel = new Item().setUnlocalizedName("ingot_weaponsteel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_gunsteel");
		plate_weaponsteel = new Item().setUnlocalizedName("plate_weaponsteel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_gunsteel");
		ingot_saturnite = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("ingot_saturnite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_saturnite");
		plate_saturnite = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("plate_saturnite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_saturnite");
		ingot_ferrouranium = new ItemCustomLore().setUnlocalizedName("ingot_ferrouranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_ferrouranium");
		ingot_fiberglass = new ItemCustomLore().setUnlocalizedName("ingot_fiberglass").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_fiberglass");
		ingot_asbestos = new ItemCustomLore().setUnlocalizedName("ingot_asbestos").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_asbestos");
		powder_asbestos = new ItemCustomLore().setUnlocalizedName("powder_asbestos").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_asbestos");
		ingot_electronium = new ItemCustomLore().setUnlocalizedName("ingot_electronium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_electronium");
		nugget_zirconium = new ItemCustomLore().setUnlocalizedName("nugget_zirconium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_zirconium");
		nugget_mercury = new Item().setUnlocalizedName("nugget_mercury_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mercury_tiny");
		ingot_mercury = new ItemCustomLore().setUnlocalizedName("nugget_mercury").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_mercury");
		bottle_mercury = new ItemCustomLore().setUnlocalizedName("bottle_mercury").setContainerItem(Items.glass_bottle).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bottle_mercury");
		ingot_calcium = new Item().setUnlocalizedName("ingot_calcium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_calcium");
		powder_calcium = new Item().setUnlocalizedName("powder_calcium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_calcium");
		ingot_cadmium = new Item().setUnlocalizedName("ingot_cadmium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_cadmium");
		powder_cadmium = new Item().setUnlocalizedName("powder_cadmium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cadmium");
		powder_bismuth = new Item().setUnlocalizedName("powder_bismuth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_bismuth");
		ingot_mud = new Item().setUnlocalizedName("ingot_mud").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_mud");
		ingot_cft = new Item().setUnlocalizedName("ingot_cft").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_cft");

		ore_byproduct = new ItemByproduct().setUnlocalizedName("ore_byproduct").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":byproduct");

		ore_bedrock = new ItemBedrockOre().setUnlocalizedName("ore_bedrock").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_bedrock");
		ore_centrifuged = new ItemBedrockOre().setUnlocalizedName("ore_centrifuged").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_centrifuged");
		ore_cleaned = new ItemBedrockOre().setUnlocalizedName("ore_cleaned").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_cleaned");
		ore_separated = new ItemBedrockOre().setUnlocalizedName("ore_separated").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_separated");
		ore_purified = new ItemBedrockOre().setUnlocalizedName("ore_purified").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_purified");
		ore_nitrated = new ItemBedrockOre().setUnlocalizedName("ore_nitrated").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_nitrated");
		ore_nitrocrystalline = new ItemBedrockOre().setUnlocalizedName("ore_nitrocrystalline").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_nitrocrystalline");
		ore_deepcleaned = new ItemBedrockOre().setUnlocalizedName("ore_deepcleaned").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_deepcleaned");
		ore_seared = new ItemBedrockOre().setUnlocalizedName("ore_seared").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_seared");
		ore_enriched = new ItemBedrockOre().setUnlocalizedName("ore_enriched").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ore_enriched");
		bedrock_ore_base = new ItemBedrockOreBase().setUnlocalizedName("bedrock_ore_base").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bedrock_ore_new");
		bedrock_ore = new ItemBedrockOreNew().setUnlocalizedName("bedrock_ore").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bedrock_ore_new");
		bedrock_ore_fragment = new ItemAutogen(MaterialShapes.FRAGMENT).aot(Mats.MAT_BISMUTH, "bedrock_ore_fragment_bismuth").setUnlocalizedName("bedrock_ore_fragment").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bedrock_ore_fragment");

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
		plate_armor_dnt = new Item().setUnlocalizedName("plate_armor_dnt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_armor_dnt");

		oil_tar = new ItemEnumMulti(EnumTarType.class, true, true).setUnlocalizedName("oil_tar").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":oil_tar");
		solid_fuel = new Item().setUnlocalizedName("solid_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel");
		solid_fuel_presto = new Item().setUnlocalizedName("solid_fuel_presto").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel_presto");
		solid_fuel_presto_triplet = new Item().setUnlocalizedName("solid_fuel_presto_triplet").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel_presto_triplet");
		solid_fuel_bf = new Item().setUnlocalizedName("solid_fuel_bf").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel_bf");
		solid_fuel_presto_bf = new Item().setUnlocalizedName("solid_fuel_presto_bf").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel_presto_bf");
		solid_fuel_presto_triplet_bf = new Item().setUnlocalizedName("solid_fuel_presto_triplet_bf").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":solid_fuel_presto_triplet_bf");
		rocket_fuel = new Item().setUnlocalizedName("rocket_fuel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":rocket_fuel");
		coke = new ItemEnumMulti(EnumCokeType.class, true, true).setUnlocalizedName("coke").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coke");
		lignite = new Item().setUnlocalizedName("lignite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":lignite");
		briquette = new ItemEnumMulti(EnumBriquetteType.class, true, true).setUnlocalizedName("briquette").setCreativeTab(MainRegistry.partsTab);
		powder_lignite = new Item().setUnlocalizedName("powder_lignite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lignite");
		coal_infernal = new Item().setUnlocalizedName("coal_infernal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coal_infernal");
		cinnebar = new Item().setUnlocalizedName("cinnebar").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":cinnebar");
		powder_ash = new ItemEnumMulti(EnumAshType.class, true, true).setUnlocalizedName("powder_ash").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_ash");
		powder_limestone = new Item().setUnlocalizedName("powder_limestone").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_limestone");
		powder_cement = new ItemLemon(2, 0.5F, false).setUnlocalizedName("powder_cement").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cement");

		ingot_gh336 = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("ingot_gh336").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_gh336");
		nugget_gh336 = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("nugget_gh336").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_gh336");

		ingot_australium = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("ingot_australium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_australium");
		nugget_australium = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("nugget_australium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_australium");
		nugget_australium_lesser = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("nugget_australium_lesser").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_australium_lesser");
		nugget_australium_greater = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("nugget_australium_greater").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_australium_greater");

		nugget_th232 = new Item().setUnlocalizedName("nugget_th232").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_th232");
		nugget_uranium = new Item().setUnlocalizedName("nugget_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_uranium");
		nugget_u233 = new Item().setUnlocalizedName("nugget_u233").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_u233");
		nugget_u235 = new Item().setUnlocalizedName("nugget_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_u235");
		nugget_u238 = new Item().setUnlocalizedName("nugget_u238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_u238");
		nugget_plutonium = new Item().setUnlocalizedName("nugget_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		nugget_pu238 = new Item().setUnlocalizedName("nugget_pu238").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu238");
		nugget_pu239 = new Item().setUnlocalizedName("nugget_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu239");
		nugget_pu240 = new Item().setUnlocalizedName("nugget_pu240").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu240");
		nugget_pu241 = new Item().setUnlocalizedName("nugget_pu241").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu241");
		nugget_pu_mix = new Item().setUnlocalizedName("nugget_pu_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pu_mix");
		nugget_am241 = new Item().setUnlocalizedName("nugget_am241").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_am241");
		nugget_am242 = new Item().setUnlocalizedName("nugget_am242").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_am242");
		nugget_am_mix = new Item().setUnlocalizedName("nugget_am_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_am_mix");
		nugget_neptunium = new Item().setUnlocalizedName("nugget_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_neptunium");
		nugget_polonium = new Item().setUnlocalizedName("nugget_polonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_polonium");
		nugget_technetium = new Item().setUnlocalizedName("nugget_technetium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_technetium");
		nugget_cobalt = new Item().setUnlocalizedName("nugget_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_cobalt");
		nugget_co60 = new Item().setUnlocalizedName("nugget_co60").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_co60");
		nugget_sr90 = new Item().setUnlocalizedName("nugget_sr90").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_sr90");
		nugget_au198 = new Item().setUnlocalizedName("nugget_au198").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_au198");
		nugget_pb209 = new Item().setUnlocalizedName("nugget_pb209").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_pb209");
		nugget_ra226 = new Item().setUnlocalizedName("nugget_ra226").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_ra226");
		nugget_actinium = new Item().setUnlocalizedName("nugget_actinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_actinium");
		plate_titanium = new Item().setUnlocalizedName("plate_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_titanium");
		plate_aluminium = new Item().setUnlocalizedName("plate_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_aluminium");
		neutron_reflector = new Item().setUnlocalizedName("neutron_reflector").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":neutron_reflector");
		nugget_lead = new Item().setUnlocalizedName("nugget_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_lead");
		ingot_bismuth = new ItemCustomLore().setUnlocalizedName("ingot_bismuth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_bismuth");
		nugget_bismuth = new Item().setUnlocalizedName("nugget_bismuth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_bismuth");
		ingot_arsenic = new ItemCustomLore().setUnlocalizedName("ingot_arsenic").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_arsenic");
		nugget_arsenic = new Item().setUnlocalizedName("nugget_arsenic").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_arsenic");
		ingot_tantalium = new ItemCustomLore().setUnlocalizedName("ingot_tantalium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_tantalium");
		nugget_tantalium = new ItemCustomLore().setUnlocalizedName("nugget_tantalium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_tantalium");
		ingot_silicon = new Item().setUnlocalizedName("ingot_silicon").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_silicon");
		billet_silicon = new Item().setUnlocalizedName("billet_silicon").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":billet_silicon");
		nugget_silicon = new Item().setUnlocalizedName("nugget_silicon").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_silicon");
		ingot_niobium = new Item().setUnlocalizedName("ingot_niobium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_niobium");
		nugget_niobium = new Item().setUnlocalizedName("nugget_niobium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_niobium");
		ingot_osmiridium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("ingot_osmiridium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_osmiridium");
		nugget_osmiridium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("nugget_osmiridium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_osmiridium");
		nugget_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("nugget_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_schrabidium");
		nugget_beryllium = new Item().setUnlocalizedName("nugget_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_beryllium");
		hazmat_cloth = new Item().setUnlocalizedName("hazmat_cloth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth");
		hazmat_cloth_red = new Item().setUnlocalizedName("hazmat_cloth_red").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth_red");
		hazmat_cloth_grey = new Item().setUnlocalizedName("hazmat_cloth_grey").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":hazmat_cloth_grey");
		asbestos_cloth = new Item().setUnlocalizedName("asbestos_cloth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":asbestos_cloth");
		rag = new ItemRag().setUnlocalizedName("rag").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":rag");
		rag_damp = new Item().setUnlocalizedName("rag_damp").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":rag_damp");
		rag_piss = new Item().setUnlocalizedName("rag_piss").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":rag_piss");
		filter_coal = new Item().setUnlocalizedName("filter_coal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":filter_coal");
		ingot_hes = new Item().setUnlocalizedName("ingot_hes").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_hes");
		ingot_les = new Item().setUnlocalizedName("ingot_les").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_les");
		nugget_hes = new Item().setUnlocalizedName("nugget_hes").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_hes");
		nugget_les = new Item().setUnlocalizedName("nugget_les").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_les");
		plate_combine_steel = new Item().setUnlocalizedName("plate_combine_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_combine_steel");

		crystal_coal = new Item().setUnlocalizedName("crystal_coal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_coal");
		crystal_iron = new Item().setUnlocalizedName("crystal_iron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_iron");
		crystal_gold = new Item().setUnlocalizedName("crystal_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_gold");
		crystal_redstone = new Item().setUnlocalizedName("crystal_redstone").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_redstone");
		crystal_lapis = new Item().setUnlocalizedName("crystal_lapis").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_lapis");
		crystal_diamond = new Item().setUnlocalizedName("crystal_diamond").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_diamond");
		crystal_uranium = new Item().setUnlocalizedName("crystal_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_uranium");
		crystal_thorium = new Item().setUnlocalizedName("crystal_thorium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_thorium");
		crystal_plutonium = new Item().setUnlocalizedName("crystal_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_plutonium");
		crystal_titanium = new Item().setUnlocalizedName("crystal_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_titanium");
		crystal_sulfur = new Item().setUnlocalizedName("crystal_sulfur").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_sulfur");
		crystal_niter = new Item().setUnlocalizedName("crystal_niter").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_niter");
		crystal_copper = new Item().setUnlocalizedName("crystal_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_copper");
		crystal_tungsten = new Item().setUnlocalizedName("crystal_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_tungsten");
		crystal_aluminium = new Item().setUnlocalizedName("crystal_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_aluminium");
		crystal_fluorite = new Item().setUnlocalizedName("crystal_fluorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_fluorite");
		crystal_beryllium = new Item().setUnlocalizedName("crystal_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_beryllium");
		crystal_lead = new Item().setUnlocalizedName("crystal_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_lead");
		crystal_schraranium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("crystal_schraranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_schraranium");
		crystal_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("crystal_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_schrabidium");
		crystal_rare = new Item().setUnlocalizedName("crystal_rare").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_rare");
		crystal_phosphorus = new Item().setUnlocalizedName("crystal_phosphorus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_phosphorus");
		crystal_lithium = new Item().setUnlocalizedName("crystal_lithium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_lithium");
		crystal_cobalt = new Item().setUnlocalizedName("crystal_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_cobalt");
		crystal_starmetal = new Item().setUnlocalizedName("crystal_starmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_starmetal");
		crystal_cinnebar = new Item().setUnlocalizedName("crystal_cinnebar").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_cinnebar");
		crystal_trixite = new Item().setUnlocalizedName("crystal_trixite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_trixite");
		crystal_osmiridium = new Item().setUnlocalizedName("crystal_osmiridium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crystal_osmiridium");
		gem_sodalite = new ItemCustomLore().setUnlocalizedName("gem_sodalite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_sodalite");
		gem_tantalium = new ItemCustomLore().setUnlocalizedName("gem_tantalium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_tantalium");
		gem_volcanic = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("gem_volcanic").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_volcanic");
		gem_rad = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("gem_rad").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_rad");
		gem_alexandrite = new ItemAlexandrite().setUnlocalizedName("gem_alexandrite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gem_alexandrite");

		powder_lead = new Item().setUnlocalizedName("powder_lead").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lead");
		powder_tantalium = new ItemCustomLore().setUnlocalizedName("powder_tantalium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tantalium");
		powder_neptunium = new Item().setUnlocalizedName("powder_neptunium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neptunium");
		powder_polonium = new Item().setUnlocalizedName("powder_polonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_polonium");
		powder_co60 = new Item().setUnlocalizedName("powder_co60").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_co60");
		powder_sr90 = new Item().setUnlocalizedName("powder_sr90").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_sr90");
		powder_sr90_tiny = new Item().setUnlocalizedName("powder_sr90_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_sr90_tiny");
		powder_i131 = new Item().setUnlocalizedName("powder_i131").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_i131");
		powder_i131_tiny = new Item().setUnlocalizedName("powder_i131_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_i131_tiny");
		powder_xe135 = new Item().setUnlocalizedName("powder_xe135").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_xe135");
		powder_xe135_tiny = new Item().setUnlocalizedName("powder_xe135_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_xe135_tiny");
		powder_cs137 = new Item().setUnlocalizedName("powder_cs137").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cs137");
		powder_cs137_tiny = new Item().setUnlocalizedName("powder_cs137_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cs137_tiny");
		powder_au198 = new Item().setUnlocalizedName("powder_au198").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_au198");
		powder_ra226 = new Item().setUnlocalizedName("powder_ra226").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_ra226");
		powder_at209 = new Item().setUnlocalizedName("powder_at209").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_at209");
		powder_schrabidium = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("powder_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_schrabidium");
		powder_schrabidate = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("powder_schrabidate").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_schrabidate");
		powder_aluminium = new Item().setUnlocalizedName("powder_aluminium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_aluminium");
		powder_beryllium = new Item().setUnlocalizedName("powder_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_beryllium");
		powder_copper = new Item().setUnlocalizedName("powder_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_copper");
		powder_gold = new Item().setUnlocalizedName("powder_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_gold");
		powder_iron = new Item().setUnlocalizedName("powder_iron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_iron");
		powder_titanium = new Item().setUnlocalizedName("powder_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_titanium");
		powder_tungsten = new Item().setUnlocalizedName("powder_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tungsten");
		powder_uranium = new Item().setUnlocalizedName("powder_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_uranium");
		powder_plutonium = new Item().setUnlocalizedName("powder_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_plutonium");
		dust = new ItemCustomLore().setUnlocalizedName("dust").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":dust");
		dust_tiny = new Item().setUnlocalizedName("dust_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":dust_tiny");
		fallout = new Item().setUnlocalizedName("fallout").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fallout");
		powder_advanced_alloy = new Item().setUnlocalizedName("powder_advanced_alloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_advanced_alloy");
		powder_tcalloy = new Item().setUnlocalizedName("powder_tcalloy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tcalloy");
		powder_coal = new Item().setUnlocalizedName("powder_coal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_coal");
		powder_coal_tiny = new Item().setUnlocalizedName("powder_coal_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_coal_tiny");
		powder_combine_steel = new Item().setUnlocalizedName("powder_combine_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_combine_steel");
		powder_diamond = new Item().setUnlocalizedName("powder_diamond").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_diamond");
		powder_emerald = new Item().setUnlocalizedName("powder_emerald").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_emerald");
		powder_lapis = new Item().setUnlocalizedName("powder_lapis").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lapis");
		powder_quartz = new Item().setUnlocalizedName("powder_quartz").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_quartz");
		powder_magnetized_tungsten = new Item().setUnlocalizedName("powder_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_magnetized_tungsten");
		powder_chlorophyte = new Item().setUnlocalizedName("powder_chlorophyte").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_chlorophyte");
		powder_red_copper = new Item().setUnlocalizedName("powder_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_red_copper");
		powder_steel = new Item().setUnlocalizedName("powder_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_steel");
		powder_lithium = new Item().setUnlocalizedName("powder_lithium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lithium");
		powder_zirconium = new Item().setUnlocalizedName("powder_zirconium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_zirconium");
		powder_sodium = new Item().setUnlocalizedName("powder_sodium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_sodium");
		powder_power = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("powder_power").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_energy_alt");
		powder_iodine = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_iodine").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_iodine");
		powder_thorium = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("powder_thorium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_thorium");
		powder_neodymium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_neodymium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neodymium");
		powder_astatine = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_astatine").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_astatine");
		powder_caesium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_caesium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_caesium");
		powder_australium = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("powder_australium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_australium");
		powder_strontium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_strontium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_strontium");
		powder_cobalt = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cobalt");
		powder_bromine = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_bromine").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_bromine");
		powder_niobium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_niobium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_niobium");
		powder_tennessine = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_tennessine").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tennessine");
		powder_cerium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_cerium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cerium");
		powder_dura_steel = new ItemCustomLore().setUnlocalizedName("powder_dura_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_dura_steel");
		powder_polymer = new ItemCustomLore().setUnlocalizedName("powder_polymer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_polymer");
		powder_bakelite = new ItemCustomLore().setUnlocalizedName("powder_bakelite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_bakelite");
		powder_euphemium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_euphemium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_euphemium");
		powder_meteorite = new Item().setUnlocalizedName("powder_meteorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_meteorite");
		powder_lanthanium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_lanthanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lanthanium");
		powder_actinium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_actinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_actinium");
		powder_boron = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("powder_boron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_boron");
		powder_semtex_mix = new Item().setUnlocalizedName("powder_semtex_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_semtex_mix");
		powder_desh_mix = new Item().setUnlocalizedName("powder_desh_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh_mix");
		powder_desh_ready = new Item().setUnlocalizedName("powder_desh_ready").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh_ready");
		powder_nitan_mix = new Item().setUnlocalizedName("powder_nitan_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_nitan_mix");
		powder_spark_mix = new Item().setUnlocalizedName("powder_spark_mix").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_spark_mix");
		powder_desh = new Item().setUnlocalizedName("powder_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_desh");
		powder_steel_tiny = new Item().setUnlocalizedName("powder_steel_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_steel_tiny");
		powder_lithium_tiny = new Item().setUnlocalizedName("powder_lithium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lithium_tiny");
		powder_neodymium_tiny = new Item().setUnlocalizedName("powder_neodymium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_neodymium_tiny");
		powder_cobalt_tiny = new Item().setUnlocalizedName("powder_cobalt_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cobalt_tiny");
		powder_niobium_tiny = new Item().setUnlocalizedName("powder_niobium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_niobium_tiny");
		powder_cerium_tiny = new Item().setUnlocalizedName("powder_cerium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_cerium_tiny");
		powder_lanthanium_tiny = new Item().setUnlocalizedName("powder_lanthanium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_lanthanium_tiny");
		powder_actinium_tiny = new Item().setUnlocalizedName("powder_actinium_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_actinium_tiny");
		powder_boron_tiny = new Item().setUnlocalizedName("powder_boron_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_boron_tiny");
		powder_meteorite_tiny = new Item().setUnlocalizedName("powder_meteorite_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_meteorite_tiny");
		powder_yellowcake = new Item().setUnlocalizedName("powder_yellowcake").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_yellowcake");
		powder_magic = new Item().setUnlocalizedName("powder_magic").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_magic");
		powder_balefire = new Item().setUnlocalizedName("powder_balefire").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_balefire");
		powder_sawdust = new Item().setUnlocalizedName("powder_sawdust").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_sawdust");
		powder_flux = new Item().setUnlocalizedName("powder_flux").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_flux");
		powder_fertilizer = new ItemFertilizer().setUnlocalizedName("powder_fertilizer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_fertilizer");
		powder_coltan_ore = new Item().setUnlocalizedName("powder_coltan_ore").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_coltan_ore");
		powder_coltan = new Item().setUnlocalizedName("powder_coltan").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_coltan");
		powder_tektite = new Item().setUnlocalizedName("powder_tektite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_tektite");
		powder_paleogenite = new Item().setUnlocalizedName("powder_paleogenite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_paleogenite");
		powder_paleogenite_tiny = new Item().setUnlocalizedName("powder_paleogenite_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_paleogenite_tiny");
		powder_impure_osmiridium = new Item().setUnlocalizedName("powder_impure_osmiridium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_impure_osmiridium");
		powder_borax = new Item().setUnlocalizedName("powder_borax").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_borax");
		powder_chlorocalcite = new Item().setUnlocalizedName("powder_chlorocalcite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_chlorocalcite");
		powder_molysite = new Item().setUnlocalizedName("powder_molysite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_molysite");

		fragment_neodymium = new Item().setUnlocalizedName("fragment_neodymium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_neodymium");
		fragment_cobalt = new Item().setUnlocalizedName("fragment_cobalt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_cobalt");
		fragment_niobium = new Item().setUnlocalizedName("fragment_niobium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_niobium");
		fragment_cerium = new Item().setUnlocalizedName("fragment_cerium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_cerium");
		fragment_lanthanium = new Item().setUnlocalizedName("fragment_lanthanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_lanthanium");
		fragment_actinium = new Item().setUnlocalizedName("fragment_actinium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_actinium");
		fragment_boron = new Item().setUnlocalizedName("fragment_boron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_boron");
		fragment_meteorite = new Item().setUnlocalizedName("fragment_meteorite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_meteorite");
		fragment_coltan = new Item().setUnlocalizedName("fragment_coltan").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fragment_coltan");
		chunk_ore = new ItemEnumMulti(EnumChunkType.class, true, true).setUnlocalizedName("chunk_ore").setCreativeTab(MainRegistry.partsTab);

		biomass = new Item().setUnlocalizedName("biomass").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":biomass");
		biomass_compressed = new Item().setUnlocalizedName("biomass_compressed").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":biomass_compressed");
		bio_wafer = new ItemLemon(4, 2F, false).setUnlocalizedName("bio_wafer").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":bio_wafer");
		plant_item = new ItemEnumMulti(EnumPlantType.class, true, true).setUnlocalizedName("plant_item").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plant_item");

		coil_copper = new Item().setUnlocalizedName("coil_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_copper");
		coil_copper_torus = new Item().setUnlocalizedName("coil_copper_torus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_copper_torus");
		coil_tungsten = new Item().setUnlocalizedName("coil_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_tungsten");
		tank_steel = new Item().setUnlocalizedName("tank_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":tank_steel");
		motor = new Item().setUnlocalizedName("motor").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":motor");
		motor_desh = new Item().setUnlocalizedName("motor_desh").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":motor_desh");
		motor_bismuth = new Item().setUnlocalizedName("motor_bismuth").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":motor_bismuth");
		centrifuge_element = new Item().setUnlocalizedName("centrifuge_element").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":centrifuge_element");
		reactor_core = new Item().setUnlocalizedName("reactor_core").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":reactor_core");
		rtg_unit = new Item().setUnlocalizedName("rtg_unit").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":rtg_unit");
		coil_magnetized_tungsten = new Item().setUnlocalizedName("coil_magnetized_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_magnetized_tungsten");
		coil_gold = new Item().setUnlocalizedName("coil_gold").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_gold");
		coil_gold_torus = new Item().setUnlocalizedName("coil_gold_torus").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":coil_gold_torus");
		chlorine_pinwheel = new ItemInfiniteFluid(Fluids.CHLORINE, 1, 2).setUnlocalizedName("chlorine_pinwheel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":chlorine_pinwheel");
		FluidTank.noDualUnload.add(chlorine_pinwheel);
		ring_starmetal = new Item().setUnlocalizedName("ring_starmetal").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ring_starmetal");
		flywheel_beryllium = new Item().setUnlocalizedName("flywheel_beryllium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flywheel_beryllium");
		deuterium_filter = new Item().setUnlocalizedName("deuterium_filter").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":deuterium_filter");
		parts_legendary = new ItemEnumMulti(EnumLegendaryType.class, false, true).setUnlocalizedName("parts_legendary").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":parts_legendary");

		gear_large = new ItemGear().setUnlocalizedName("gear_large").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":gear_large");
		sawblade = new Item().setUnlocalizedName("sawblade").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sawblade");

		shell = new ItemAutogen(MaterialShapes.SHELL).oun("shellntm").setUnlocalizedName("shell").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":shell");
		pipe = new ItemAutogen(MaterialShapes.PIPE).oun("pipentm").setUnlocalizedName("pipe").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pipe");
		fins_flat = new Item().setUnlocalizedName("fins_flat").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_flat");
		fins_small_steel = new Item().setUnlocalizedName("fins_small_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_small_steel");
		fins_big_steel = new Item().setUnlocalizedName("fins_big_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_big_steel");
		fins_tri_steel = new Item().setUnlocalizedName("fins_tri_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_tri_steel");
		fins_quad_titanium = new Item().setUnlocalizedName("fins_quad_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":fins_quad_titanium");
		sphere_steel = new Item().setUnlocalizedName("sphere_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":sphere_steel");
		pedestal_steel = new Item().setUnlocalizedName("pedestal_steel").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pedestal_steel");
		dysfunctional_reactor = new Item().setUnlocalizedName("dysfunctional_reactor").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":dysfunctional_reactor");
		blade_titanium = new Item().setUnlocalizedName("blade_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":blade_titanium");
		turbine_titanium = new Item().setUnlocalizedName("turbine_titanium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":turbine_titanium");
		blade_tungsten = new Item().setUnlocalizedName("blade_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":blade_tungsten");
		turbine_tungsten = new Item().setUnlocalizedName("turbine_tungsten").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":turbine_tungsten");

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
		warhead_mirv = new Item().setUnlocalizedName("warhead_mirv").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_mirv");
		warhead_volcano = new Item().setUnlocalizedName("warhead_volcano").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":warhead_volcano");

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

		combine_scrap = new Item().setUnlocalizedName("combine_scrap").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":combine_scrap");

		shimmer_head = new Item().setUnlocalizedName("shimmer_head").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":shimmer_head_original");
		shimmer_axe_head = new Item().setUnlocalizedName("shimmer_axe_head").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":shimmer_axe_head");
		shimmer_handle = new Item().setUnlocalizedName("shimmer_handle").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":shimmer_handle");

		entanglement_kit = new ItemCustomLore().setUnlocalizedName("entanglement_kit").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":entanglement_kit");

		circuit = new ItemCircuit().setUnlocalizedName("circuit").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":circuit");
		crt_display = new Item().setUnlocalizedName("crt_display").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":crt_display");
		circuit_star_piece = (ItemEnumMulti) new ItemEnumMulti(ScrapType.class, true, true).setUnlocalizedName("circuit_star_piece").setCreativeTab(null);
		circuit_star_component = (ItemEnumMulti) new ItemCircuitStarComponent().setUnlocalizedName("circuit_star_component").setCreativeTab(null);
		circuit_star = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("circuit_star").setCreativeTab(null).setTextureName(RefStrings.MODID + ":circuit_star");
		assembly_nuke = new Item().setUnlocalizedName("assembly_nuke").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":assembly_nuke");
		casing = new ItemEnumMulti(ItemEnums.EnumCasingType.class, true, true).setUnlocalizedName("casing").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":casing");

		wiring_red_copper = new ItemWiring().setUnlocalizedName("wiring_red_copper").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wiring_red_copper");
		wrench = new ItemWrench(MainRegistry.tMatSteel).setUnlocalizedName("wrench").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wrench");
		pellet_rtg_depleted = new ItemRTGPelletDepleted().setContainerItem(plate_iron).setUnlocalizedName("pellet_rtg_depleted").setCreativeTab(MainRegistry.controlTab);

		pellet_rtg_radium = new ItemRTGPellet(3).setDecays(DepletedRTGMaterial.LEAD, (long) (RTGUtil.getLifespan(16.0F, HalfLifeType.LONG, false) * 1.5)).setUnlocalizedName("pellet_rtg_radium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg_radium");
		pellet_rtg_weak = new ItemRTGPellet(5).setDecays(DepletedRTGMaterial.LEAD, (long) (RTGUtil.getLifespan(1.0F, HalfLifeType.LONG, false) * 1.5)).setUnlocalizedName("pellet_rtg_weak").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg_weak");
		pellet_rtg = new ItemRTGPellet(10).setDecays(DepletedRTGMaterial.LEAD, (long) (RTGUtil.getLifespan(87.7F, HalfLifeType.MEDIUM, false) * 1.5)).setUnlocalizedName("pellet_rtg").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg");
		pellet_rtg_strontium = new ItemRTGPellet(15).setDecays(DepletedRTGMaterial.ZIRCONIUM, (long) (RTGUtil.getLifespan(29.0F, HalfLifeType.MEDIUM, false) * 1.5)).setUnlocalizedName("pellet_rtg_strontium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_rtg_strontium");
		pellet_rtg_cobalt = new ItemRTGPellet(15).setDecays(DepletedRTGMaterial.NICKEL, (long) (RTGUtil.getLifespan(5.3F, HalfLifeType.MEDIUM, false) * 1.5)).setUnlocalizedName("pellet_rtg_cobalt").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_rtg_cobalt");
		pellet_rtg_actinium = new ItemRTGPellet(20).setDecays(DepletedRTGMaterial.LEAD, (long) (RTGUtil.getLifespan(21.8F, HalfLifeType.MEDIUM, false) * 1.5)).setUnlocalizedName("pellet_rtg_actinium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_rtg_actinium");
		pellet_rtg_americium = new ItemRTGPellet(20).setDecays(DepletedRTGMaterial.NEPTUNIUM, (long) (RTGUtil.getLifespan(4.7F, HalfLifeType.LONG, false) * 1.5)).setUnlocalizedName("pellet_rtg_americium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg_americium");
		pellet_rtg_polonium = new ItemRTGPellet(50).setDecays(DepletedRTGMaterial.LEAD, (long) (RTGUtil.getLifespan(138.0F, HalfLifeType.SHORT, false) * 1.5)).setUnlocalizedName("pellet_rtg_polonium").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg_polonium");
		pellet_rtg_gold = new ItemRTGPellet(VersatileConfig.rtgDecay() ? 200 : 100).setDecays(DepletedRTGMaterial.MERCURY, (long) (RTGUtil.getLifespan(2.7F, HalfLifeType.SHORT, false) * 1.5)).setUnlocalizedName("pellet_rtg_gold").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":pellet_rtg_gold");
		pellet_rtg_lead = new ItemRTGPellet(VersatileConfig.rtgDecay() ? 600 : 200).setDecays(DepletedRTGMaterial.BISMUTH, (long) (RTGUtil.getLifespan(0.3F, HalfLifeType.SHORT, false) * 1.5)).setUnlocalizedName("pellet_rtg_lead").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pellet_rtg_lead");

		tritium_deuterium_cake = new ItemCustomLore().setUnlocalizedName("tritium_deuterium_cake").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":tritium_deuterium_cake");

		piston_selenium = new Item().setUnlocalizedName("piston_selenium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":piston_selenium");
		piston_set = new ItemPistons().setUnlocalizedName("piston_set").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);
		drillbit = new ItemDrillbit().setUnlocalizedName("drillbit").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1);

		rune_blank = new ItemCustomLore().setEffect().setUnlocalizedName("rune_blank").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_blank");
		rune_isa = new ItemCustomLore().setEffect().setUnlocalizedName("rune_isa").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_isa");
		rune_dagaz = new ItemCustomLore().setEffect().setUnlocalizedName("rune_dagaz").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_dagaz");
		rune_hagalaz = new ItemCustomLore().setEffect().setUnlocalizedName("rune_hagalaz").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_hagalaz");
		rune_jera = new ItemCustomLore().setEffect().setUnlocalizedName("rune_jera").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_jera");
		rune_thurisaz = new ItemCustomLore().setEffect().setUnlocalizedName("rune_thurisaz").setCreativeTab(MainRegistry.partsTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":rune_thurisaz");

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

		cell_empty = new Item().setUnlocalizedName("cell_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":cell_empty");
		cell_uf6 = new Item().setUnlocalizedName("cell_uf6").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_uf6");
		cell_puf6 = new Item().setUnlocalizedName("cell_puf6").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_puf6");
		cell_antimatter = new ItemDrop().setUnlocalizedName("cell_antimatter").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_antimatter");
		cell_deuterium = new Item().setUnlocalizedName("cell_deuterium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_deuterium");
		cell_tritium = new Item().setUnlocalizedName("cell_tritium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_tritium");
		cell_sas3 = new ItemCustomLore().setRarity(EnumRarity.rare).setUnlocalizedName("cell_sas3").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_sas3");
		cell_anti_schrabidium = new ItemDrop().setUnlocalizedName("cell_anti_schrabidium").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_anti_schrabidium");
		cell_balefire = new Item().setUnlocalizedName("cell_balefire").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_balefire");

		demon_core_open = new ItemDemonCore().setUnlocalizedName("demon_core_open").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":demon_core_open");
		demon_core_closed = new Item().setUnlocalizedName("demon_core_closed").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":demon_core_closed");

		pa_coil = new ItemPACoil().setUnlocalizedName("pa_coil").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pa_coil");

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
		particle_tachyon = new Item().setUnlocalizedName("particle_tachyon").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_tachyon");
		particle_strange = new Item().setUnlocalizedName("particle_strange").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_strange");
		particle_dark = new Item().setUnlocalizedName("particle_dark").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_dark");
		particle_sparkticle = new Item().setUnlocalizedName("particle_sparkticle").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_sparkticle");
		particle_digamma = new ItemDigamma(60).setUnlocalizedName("particle_digamma").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_digamma");
		particle_lutece = new Item().setUnlocalizedName("particle_lutece").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.particle_empty).setTextureName(RefStrings.MODID + ":particle_lutece");

		singularity = new ItemDrop().setUnlocalizedName("singularity").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity");
		singularity_counter_resonant = new ItemDrop().setUnlocalizedName("singularity_counter_resonant").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_alt");
		singularity_super_heated = new ItemDrop().setUnlocalizedName("singularity_super_heated").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_5");
		black_hole = new ItemDrop().setUnlocalizedName("black_hole").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_4");
		singularity_spark = new ItemDrop().setUnlocalizedName("singularity_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.nuclear_waste).setTextureName(RefStrings.MODID + ":singularity_spark_alt");
		pellet_antimatter = new ItemDrop().setUnlocalizedName("pellet_antimatter").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":pellet_antimatter");
		crystal_xen = new ItemDrop().setUnlocalizedName("crystal_xen").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":crystal_xen");

		stamp_stone_flat = new ItemStamp(32, StampType.FLAT).setUnlocalizedName("stamp_stone_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_flat");
		stamp_stone_plate = new ItemStamp(32, StampType.PLATE).setUnlocalizedName("stamp_stone_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_plate");
		stamp_stone_wire = new ItemStamp(32, StampType.WIRE).setUnlocalizedName("stamp_stone_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_wire");
		stamp_stone_circuit = new ItemStamp(32, StampType.CIRCUIT).setUnlocalizedName("stamp_stone_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_stone_circuit");
		stamp_iron_flat = new ItemStamp(64, StampType.FLAT).setUnlocalizedName("stamp_iron_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_flat");
		stamp_iron_plate = new ItemStamp(64, StampType.PLATE).setUnlocalizedName("stamp_iron_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_plate");
		stamp_iron_wire = new ItemStamp(64, StampType.WIRE).setUnlocalizedName("stamp_iron_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_wire");
		stamp_iron_circuit = new ItemStamp(64, StampType.CIRCUIT).setUnlocalizedName("stamp_iron_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_iron_circuit");
		stamp_steel_flat = new ItemStamp(192, StampType.FLAT).setUnlocalizedName("stamp_steel_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_flat");
		stamp_steel_plate = new ItemStamp(192, StampType.PLATE).setUnlocalizedName("stamp_steel_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_plate");
		stamp_steel_wire = new ItemStamp(192, StampType.WIRE).setUnlocalizedName("stamp_steel_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_wire");
		stamp_steel_circuit = new ItemStamp(192, StampType.CIRCUIT).setUnlocalizedName("stamp_steel_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_steel_circuit");
		stamp_titanium_flat = new ItemStamp(256, StampType.FLAT).setUnlocalizedName("stamp_titanium_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_flat");
		stamp_titanium_plate = new ItemStamp(256, StampType.PLATE).setUnlocalizedName("stamp_titanium_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_plate");
		stamp_titanium_wire = new ItemStamp(256, StampType.WIRE).setUnlocalizedName("stamp_titanium_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_wire");
		stamp_titanium_circuit = new ItemStamp(256, StampType.CIRCUIT).setUnlocalizedName("stamp_titanium_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_titanium_circuit");
		stamp_obsidian_flat = new ItemStamp(512, StampType.FLAT).setUnlocalizedName("stamp_obsidian_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_flat");
		stamp_obsidian_plate = new ItemStamp(512, StampType.PLATE).setUnlocalizedName("stamp_obsidian_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_plate");
		stamp_obsidian_wire = new ItemStamp(512, StampType.WIRE).setUnlocalizedName("stamp_obsidian_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_wire");
		stamp_obsidian_circuit = new ItemStamp(512, StampType.CIRCUIT).setUnlocalizedName("stamp_obsidian_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_obsidian_circuit");
		stamp_desh_flat = new ItemStamp(0, StampType.FLAT).setUnlocalizedName("stamp_desh_flat").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_desh_flat");
		stamp_desh_plate = new ItemStamp(0, StampType.PLATE).setUnlocalizedName("stamp_desh_plate").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_desh_plate");
		stamp_desh_wire = new ItemStamp(0, StampType.WIRE).setUnlocalizedName("stamp_desh_wire").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_desh_wire");
		stamp_desh_circuit = new ItemStamp(0, StampType.CIRCUIT).setUnlocalizedName("stamp_desh_circuit").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_desh_circuit");
		stamp_357 = new ItemStamp(1000, StampType.C357).setUnlocalizedName("stamp_357").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_357");
		stamp_44 = new ItemStamp(1000, StampType.C44).setUnlocalizedName("stamp_44").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_44");
		stamp_9 = new ItemStamp(1000, StampType.C9).setUnlocalizedName("stamp_9").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_9");
		stamp_50 = new ItemStamp(1000, StampType.C50).setUnlocalizedName("stamp_50").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_50");
		stamp_book = new ItemStampBook().setUnlocalizedName("stamp_book").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":stamp_book");

		stamp_desh_357 = new ItemStamp(0, StampType.C357).setUnlocalizedName("stamp_desh_357").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_357_desh");
		stamp_desh_44 = new ItemStamp(0, StampType.C44).setUnlocalizedName("stamp_desh_44").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_44_desh");
		stamp_desh_9 = new ItemStamp(0, StampType.C9).setUnlocalizedName("stamp_desh_9").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_9_desh");
		stamp_desh_50 = new ItemStamp(0, StampType.C50).setUnlocalizedName("stamp_desh_50").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":stamp_50_desh");

		blades_steel = new ItemBlades(200).setUnlocalizedName("blades_steel").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_steel");
		blades_titanium = new ItemBlades(350).setUnlocalizedName("blades_titanium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_titanium");
		blades_advanced_alloy = new ItemBlades(700).setUnlocalizedName("blades_advanced_alloy").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_advanced_alloy");
		blades_desh = new ItemBlades(0).setUnlocalizedName("blades_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":blades_desh");

		mold_base = new Item().setUnlocalizedName("mold_base").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":mold_base");
		mold = new ItemMold().setUnlocalizedName("mold").setCreativeTab(MainRegistry.controlTab);
		scraps = new ItemScraps().aot(Mats.MAT_BISMUTH, "scraps_bismuth").setUnlocalizedName("scraps").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scraps");
		ingot_raw = new ItemAutogen(MaterialShapes.INGOT).setUnlocalizedName("ingot_raw").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_raw");
		plate_cast = new ItemAutogen(MaterialShapes.CASTPLATE).aot(Mats.MAT_BISMUTH, "plate_cast_bismuth").setUnlocalizedName("plate_cast").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_cast");
		plate_welded = new ItemAutogen(MaterialShapes.WELDEDPLATE).setUnlocalizedName("plate_welded").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":plate_welded");
		wire_fine = new ItemAutogen(MaterialShapes.WIRE)
				.aot(Mats.MAT_ALUMINIUM, "wire_aluminium").aot(Mats.MAT_COPPER, "wire_copper")
				.aot(Mats.MAT_MINGRADE, "wire_red_copper").aot(Mats.MAT_GOLD, "wire_gold")
				.aot(Mats.MAT_TUNGSTEN, "wire_tungsten").aot(Mats.MAT_ALLOY, "wire_advanced_alloy")
				.aot(Mats.MAT_CARBON, "wire_carbon").aot(Mats.MAT_SCHRABIDIUM, "wire_schrabidium")
				.aot(Mats.MAT_MAGTUNG, "wire_magnetized_tungsten").setUnlocalizedName("wire_fine").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_fine");
		wire_dense = new ItemAutogen(MaterialShapes.DENSEWIRE).setUnlocalizedName("wire_dense").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":wire_dense");

		part_barrel_light = new ItemAutogen(MaterialShapes.LIGHTBARREL).setUnlocalizedName("part_barrel_light").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_barrel_light");
		part_barrel_heavy = new ItemAutogen(MaterialShapes.HEAVYBARREL).setUnlocalizedName("part_barrel_heavy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_barrel_heavy");
		part_receiver_light = new ItemAutogen(MaterialShapes.LIGHTRECEIVER).setUnlocalizedName("part_receiver_light").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_receiver_light");
		part_receiver_heavy = new ItemAutogen(MaterialShapes.HEAVYRECEIVER).setUnlocalizedName("part_receiver_heavy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_receiver_heavy");
		part_mechanism = new ItemAutogen(MaterialShapes.MECHANISM).setUnlocalizedName("part_mechanism").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_mechanism");
		part_stock = new ItemAutogen(MaterialShapes.STOCK).setUnlocalizedName("part_stock").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_stock");
		part_grip = new ItemAutogen(MaterialShapes.GRIP).setUnlocalizedName("part_grip").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":part_grip");

		part_lithium = new Item().setUnlocalizedName("part_lithium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_lithium");
		part_beryllium = new Item().setUnlocalizedName("part_beryllium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_beryllium");
		part_carbon = new Item().setUnlocalizedName("part_carbon").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_carbon");
		part_copper = new Item().setUnlocalizedName("part_copper").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_copper");
		part_plutonium = new Item().setUnlocalizedName("part_plutonium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":part_plutonium");

		laser_crystal_co2 = new ItemFELCrystal(EnumWavelengths.IR).setUnlocalizedName("laser_crystal_co2").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":laser_crystal_co2");
		laser_crystal_bismuth = new ItemFELCrystal(EnumWavelengths.VISIBLE).setUnlocalizedName("laser_crystal_bismuth").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":laser_crystal_bismuth");
		laser_crystal_cmb = new ItemFELCrystal(EnumWavelengths.UV).setUnlocalizedName("laser_crystal_cmb").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":laser_crystal_cmb");
		laser_crystal_dnt = new ItemFELCrystal(EnumWavelengths.GAMMA).setUnlocalizedName("laser_crystal_dnt").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":laser_crystal_dnt");
		laser_crystal_digamma = new ItemFELCrystal(EnumWavelengths.DRX).setUnlocalizedName("laser_crystal_digamma").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":laser_crystal_digamma");

		thermo_element = new Item().setUnlocalizedName("thermo_element").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":thermo_element");
		catalytic_converter = new Item().setUnlocalizedName("catalytic_converter").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":catalytic_converter");

		fuel_additive = new ItemEnumMulti(ItemEnums.EnumFuelAdditive.class, true, true).setUnlocalizedName("fuel_additive").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fuel_additive");

		canister_empty = new ItemCustomLore().setUnlocalizedName("canister_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":canister_empty");
		canister_full = new ItemCanister().setUnlocalizedName("canister_full").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_empty");
		canister_napalm = new ItemCustomLore().setUnlocalizedName("canister_napalm").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.canister_empty).setTextureName(RefStrings.MODID + ":canister_napalm");
		gas_empty = new Item().setUnlocalizedName("gas_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":gas_empty");
		gas_full = new ItemGasTank().setUnlocalizedName("gas_full").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.gas_empty).setTextureName(RefStrings.MODID + ":gas_empty");

		ItemSimpleConsumable.init();

		//TODO: move all this crap to ItemSimpleConsumable
		syringe_empty = new Item().setUnlocalizedName("syringe_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_empty");
		syringe_metal_empty = new Item().setUnlocalizedName("syringe_metal_empty").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_empty");
		syringe_metal_stimpak = new ItemSyringe().setUnlocalizedName("syringe_metal_stimpak").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_stimpak");
		syringe_metal_medx = new ItemSyringe().setUnlocalizedName("syringe_metal_medx").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_medx");
		syringe_metal_psycho = new ItemSyringe().setUnlocalizedName("syringe_metal_psycho").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_psycho");
		syringe_metal_super = new ItemSyringe().setUnlocalizedName("syringe_metal_super").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_metal_super");
		syringe_taint = new ItemSyringe().setUnlocalizedName("syringe_taint").setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":syringe_taint");
		syringe_mkunicorn = new ItemSyringe().setUnlocalizedName("syringe_mkunicorn").setFull3D().setCreativeTab(null).setTextureName(RefStrings.MODID + ":syringe_mkunicorn");

		med_bag = new ItemSyringe().setUnlocalizedName("med_bag").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_bag");
		radx = new ItemPill(0).setUnlocalizedName("radx").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":radx");
		siox = new ItemPill(0).setUnlocalizedName("siox").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":siox");
		pill_herbal = new ItemPill(0).setUnlocalizedName("pill_herbal").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pill_herbal");
		xanax = new ItemPill(0).setUnlocalizedName("xanax").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":xanax");
		fmn = new ItemPill(0).setUnlocalizedName("fmn").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":tablet");
		five_htp = new ItemPill(0).setUnlocalizedName("five_htp").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":5htp");
		pill_iodine = new ItemPill(0).setUnlocalizedName("pill_iodine").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pill_iodine");
		plan_c = new ItemPill(0).setUnlocalizedName("plan_c").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":plan_c");
		pill_red = new ItemPill(0).setUnlocalizedName("pill_red").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pill_red");
		stealth_boy = new ItemStarterKit().setUnlocalizedName("stealth_boy").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":stealth_boy");
		gas_mask_filter = new ItemFilter().setUnlocalizedName("gas_mask_filter").setTextureName(RefStrings.MODID + ":gas_mask_filter");
		gas_mask_filter_mono = new ItemFilter().setUnlocalizedName("gas_mask_filter_mono").setTextureName(RefStrings.MODID + ":gas_mask_filter_mono");
		gas_mask_filter_combo = new ItemFilter().setUnlocalizedName("gas_mask_filter_combo").setTextureName(RefStrings.MODID + ":gas_mask_filter_combo");
		gas_mask_filter_rag = new ItemFilter().setUnlocalizedName("gas_mask_filter_rag").setTextureName(RefStrings.MODID + ":gas_mask_filter_rag");
		gas_mask_filter_piss = new ItemFilter().setUnlocalizedName("gas_mask_filter_piss").setTextureName(RefStrings.MODID + ":gas_mask_filter_piss");
		jetpack_tank = new ItemSyringe().setUnlocalizedName("jetpack_tank").setMaxStackSize(16).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":jetpack_tank");
		gun_kit_1 = new ItemRepairKit(10).setUnlocalizedName("gun_kit_1").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":gun_kit_1");
		gun_kit_2 = new ItemRepairKit(100).setUnlocalizedName("gun_kit_2").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":gun_kit_2");
		cbt_device = new ItemSyringe().setUnlocalizedName("cbt_device").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":cbt_device");
		cigarette = new ItemCigarette().setUnlocalizedName("cigarette").setFull3D().setMaxStackSize(16).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cigarette");
		crackpipe = new ItemCigarette().setUnlocalizedName("crackpipe").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":crackpipe");
		bdcl = new ItemBDCL().setUnlocalizedName("bdcl").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bdcl");

		attachment_mask = new ItemModGasmask().setUnlocalizedName("attachment_mask").setTextureName(RefStrings.MODID + ":attachment_mask");
		attachment_mask_mono = new ItemModGasmask().setUnlocalizedName("attachment_mask_mono").setTextureName(RefStrings.MODID + ":attachment_mask_mono");
		back_tesla = new ItemModTesla().setUnlocalizedName("back_tesla").setTextureName(RefStrings.MODID + ":back_tesla");
		servo_set = new ItemModServos().setUnlocalizedName("servo_set").setTextureName(RefStrings.MODID + ":servo_set");
		servo_set_desh = new ItemModServos().setUnlocalizedName("servo_set_desh").setTextureName(RefStrings.MODID + ":servo_set_desh");
		pads_rubber = new ItemModPads(0.5F).setUnlocalizedName("pads_rubber").setTextureName(RefStrings.MODID + ":pads_rubber");
		pads_slime = new ItemModPads(0.25F).setUnlocalizedName("pads_slime").setTextureName(RefStrings.MODID + ":pads_slime");
		pads_static = new ItemModPads(0.75F).setUnlocalizedName("pads_static").setTextureName(RefStrings.MODID + ":pads_static");
		cladding_paint = new ItemModCladding(0.025).setUnlocalizedName("cladding_paint").setTextureName(RefStrings.MODID + ":cladding_paint");
		cladding_rubber = new ItemModCladding(0.005).setUnlocalizedName("cladding_rubber").setTextureName(RefStrings.MODID + ":cladding_rubber");
		cladding_lead = new ItemModCladding(0.1).setUnlocalizedName("cladding_lead").setTextureName(RefStrings.MODID + ":cladding_lead");
		cladding_desh = new ItemModCladding(0.2).setUnlocalizedName("cladding_desh").setTextureName(RefStrings.MODID + ":cladding_desh");
		cladding_ghiorsium = new ItemModCladding(0.5).setUnlocalizedName("cladding_ghiorsium").setTextureName(RefStrings.MODID + ":cladding_ghiorsium");
		cladding_iron = new ItemModIron().setUnlocalizedName("cladding_iron").setTextureName(RefStrings.MODID + ":cladding_iron");
		cladding_obsidian = new ItemModObsidian().setUnlocalizedName("cladding_obsidian").setTextureName(RefStrings.MODID + ":cladding_obsidian");
		insert_kevlar = new ItemModInsert(1500, 1F, 0.9F, 1F, 1F).setUnlocalizedName("insert_kevlar").setTextureName(RefStrings.MODID + ":insert_kevlar");
		insert_sapi = new ItemModInsert(1750, 1F, 0.85F, 1F, 1F).setUnlocalizedName("insert_sapi").setTextureName(RefStrings.MODID + ":insert_sapi");
		insert_esapi = new ItemModInsert(2000, 0.95F, 0.8F, 1F, 1F).setUnlocalizedName("insert_esapi").setTextureName(RefStrings.MODID + ":insert_esapi");
		insert_xsapi = new ItemModInsert(2500, 0.9F, 0.75F, 1F, 1F).setUnlocalizedName("insert_xsapi").setTextureName(RefStrings.MODID + ":insert_xsapi");
		insert_steel = new ItemModInsert(1000, 1F, 0.95F, 0.75F, 0.95F).setUnlocalizedName("insert_steel").setTextureName(RefStrings.MODID + ":insert_steel");
		insert_du = new ItemModInsert(1500, 0.9F, 0.85F, 0.5F, 0.9F).setUnlocalizedName("insert_du").setTextureName(RefStrings.MODID + ":insert_du");
		insert_polonium = new ItemModInsert(500, 0.9F, 1F, 0.95F, 0.9F).setUnlocalizedName("insert_polonium").setTextureName(RefStrings.MODID + ":insert_polonium");
		insert_ghiorsium = new ItemModInsert(2000, 0.8F, 0.75F, 0.35F, 0.9F).setUnlocalizedName("insert_ghiorsium").setTextureName(RefStrings.MODID + ":insert_ghiorsium");
		insert_era = new ItemModInsert(25, 0.5F, 1F, 0.25F, 1F).setUnlocalizedName("insert_era").setTextureName(RefStrings.MODID + ":insert_era");
		insert_yharonite = new ItemModInsert(9999, 0.01F, 1F, 1F, 1F).setUnlocalizedName("insert_yharonite").setTextureName(RefStrings.MODID + ":insert_yharonite");
		insert_doxium = new ItemModInsert(9999, 5.0F, 1F, 1F, 1F).setUnlocalizedName("insert_doxium").setTextureName(RefStrings.MODID + ":insert_doxium");
		armor_polish = new ItemModPolish().setUnlocalizedName("armor_polish").setTextureName(RefStrings.MODID + ":armor_polish");
		bandaid = new ItemModBandaid().setUnlocalizedName("bandaid").setTextureName(RefStrings.MODID + ":bandaid");
		serum = new ItemModSerum().setUnlocalizedName("serum").setTextureName(RefStrings.MODID + ":serum");
		quartz_plutonium = new ItemModQuartz().setUnlocalizedName("quartz_plutonium").setTextureName(RefStrings.MODID + ":quartz_plutonium");
		morning_glory = new ItemModMorningGlory().setUnlocalizedName("morning_glory").setTextureName(RefStrings.MODID + ":morning_glory");
		lodestone = new ItemModLodestone(5).setUnlocalizedName("lodestone").setTextureName(RefStrings.MODID + ":lodestone");
		horseshoe_magnet = new ItemModLodestone(8).setUnlocalizedName("horseshoe_magnet").setTextureName(RefStrings.MODID + ":horseshoe_magnet");
		industrial_magnet = new ItemModLodestone(12).setUnlocalizedName("industrial_magnet").setTextureName(RefStrings.MODID + ":industrial_magnet");
		bathwater = new ItemModBathwater().setUnlocalizedName("bathwater").setTextureName(RefStrings.MODID + ":bathwater");
		bathwater_mk2 = new ItemModBathwater().setUnlocalizedName("bathwater_mk2").setTextureName(RefStrings.MODID + ":bathwater_mk2");
		spider_milk = new ItemModMilk().setUnlocalizedName("spider_milk").setTextureName(RefStrings.MODID + ":spider_milk");
		ink = new ItemModInk().setUnlocalizedName("ink").setTextureName(RefStrings.MODID + ":ink");
		heart_piece = new ItemModHealth(5F).setUnlocalizedName("heart_piece").setTextureName(RefStrings.MODID + ":heart_piece");
		heart_container = new ItemModHealth(20F).setUnlocalizedName("heart_container").setTextureName(RefStrings.MODID + ":heart_container");
		heart_booster = new ItemModHealth(40F).setUnlocalizedName("heart_booster").setTextureName(RefStrings.MODID + ":heart_booster");
		heart_fab = new ItemModHealth(60F).setUnlocalizedName("heart_fab").setTextureName(RefStrings.MODID + ":heart_fab");
		black_diamond = new ItemModHealth(40F).setUnlocalizedName("black_diamond").setTextureName(RefStrings.MODID + ":black_diamond");
		wd40 = new ItemModWD40().setUnlocalizedName("wd40").setTextureName(RefStrings.MODID + ":wd40");
		scrumpy = new ItemModRevive(1).setUnlocalizedName("scrumpy").setTextureName(RefStrings.MODID + ":scrumpy");
		wild_p = new ItemModRevive(3).setUnlocalizedName("wild_p").setTextureName(RefStrings.MODID + ":wild_p");
		shackles = new ItemModShackles().setUnlocalizedName("shackles").setTextureName(RefStrings.MODID + ":shackles");
		injector_5htp = new ItemModAuto().setUnlocalizedName("injector_5htp").setTextureName(RefStrings.MODID + ":injector_5htp");
		injector_knife = new ItemModKnife().setUnlocalizedName("injector_knife").setTextureName(RefStrings.MODID + ":injector_knife");
		medal_liquidator = new ItemModMedal().setUnlocalizedName("medal_liquidator").setTextureName(RefStrings.MODID + ":medal_liquidator");
		bottled_cloud = new ItemModCloud().setUnlocalizedName("bottled_cloud").setTextureName(RefStrings.MODID + ":bottled_cloud");
		protection_charm = new ItemModCharm().setUnlocalizedName("protection_charm").setTextureName(RefStrings.MODID + ":protection_charm");
		meteor_charm = new ItemModCharm().setUnlocalizedName("meteor_charm").setTextureName(RefStrings.MODID + ":meteor_charm");
		neutrino_lens = new ItemModLens().setUnlocalizedName("neutrino_lens").setTextureName(RefStrings.MODID + ":neutrino_lens");
		gas_tester = new ItemModSensor().setUnlocalizedName("gas_tester").setTextureName(RefStrings.MODID + ":gas_tester");
		defuser_gold = new ItemModDefuser().setUnlocalizedName("defuser_gold").setTextureName(RefStrings.MODID + ":defuser_gold");
		ballistic_gauntlet = new ItemModTwoKick().setUnlocalizedName("ballistic_gauntlet").setTextureName(RefStrings.MODID + ":ballistic_gauntlet");
		night_vision = new ItemModNightVision().setUnlocalizedName("night_vision").setTextureName(RefStrings.MODID + ":night_vision");
		card_aos = new ItemModCard().setUnlocalizedName("card_aos").setTextureName(RefStrings.MODID + ":card_aos");
		card_qos = new ItemModCard().setUnlocalizedName("card_qos").setTextureName(RefStrings.MODID + ":card_qos");
		australium_iii = new ItemModShield(25F).setUnlocalizedName("australium_iii").setTextureName(RefStrings.MODID + ":australium_iii");
		armor_battery = new ItemModBattery(1.25D).setUnlocalizedName("armor_battery").setTextureName(RefStrings.MODID + ":armor_battery");
		armor_battery_mk2 = new ItemModBattery(1.5D).setUnlocalizedName("armor_battery_mk2").setTextureName(RefStrings.MODID + ":armor_battery_mk2");
		armor_battery_mk3 = new ItemModBattery(2D).setUnlocalizedName("armor_battery_mk3").setTextureName(RefStrings.MODID + ":armor_battery_mk3");

		cap_nuka = new Item().setUnlocalizedName("cap_nuka").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_nuka");
		cap_quantum = new Item().setUnlocalizedName("cap_quantum").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_quantum");
		cap_sparkle = new Item().setUnlocalizedName("cap_sparkle").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_sparkle");
		cap_rad = new Item().setUnlocalizedName("cap_rad").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_rad");
		cap_korl = new Item().setUnlocalizedName("cap_korl").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_korl");
		cap_fritz = new Item().setUnlocalizedName("cap_fritz").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cap_fritz");
		ring_pull = new Item().setUnlocalizedName("ring_pull").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":ring_pull");

		can_empty = new Item().setUnlocalizedName("can_empty").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_empty");
		can_smart = new ItemEnergy().makeCan().setUnlocalizedName("can_smart").setTextureName(RefStrings.MODID + ":can_smart");
		can_creature = new ItemEnergy().makeCan().setUnlocalizedName("can_creature").setTextureName(RefStrings.MODID + ":can_creature");
		can_redbomb = new ItemEnergy().makeCan().setUnlocalizedName("can_redbomb").setTextureName(RefStrings.MODID + ":can_redbomb");
		can_mrsugar = new ItemEnergy().makeCan().setUnlocalizedName("can_mrsugar").setTextureName(RefStrings.MODID + ":can_mrsugar");
		can_overcharge = new ItemEnergy().makeCan().setUnlocalizedName("can_overcharge").setTextureName(RefStrings.MODID + ":can_overcharge");
		can_luna = new ItemEnergy().makeCan().setUnlocalizedName("can_luna").setTextureName(RefStrings.MODID + ":can_luna");
		can_bepis = new ItemEnergy().makeCan().setUnlocalizedName("can_bepis").setTextureName(RefStrings.MODID + ":can_bepis");
		can_breen = new ItemEnergy().makeCan().setUnlocalizedName("can_breen").setTextureName(RefStrings.MODID + ":can_breen");
		can_mug = new ItemEnergy().makeCan().setUnlocalizedName("can_mug").setTextureName(RefStrings.MODID + ":can_mug");
		bottle_empty = new Item().setUnlocalizedName("bottle_empty").setTextureName(RefStrings.MODID + ":bottle_empty");
		bottle_nuka = new ItemEnergy().makeBottle(bottle_empty, cap_nuka).setUnlocalizedName("bottle_nuka").setTextureName(RefStrings.MODID + ":bottle_nuka");
		bottle_cherry = new ItemEnergy().makeBottle(bottle_empty, cap_nuka).setUnlocalizedName("bottle_cherry").setContainerItem(ModItems.bottle_empty).setTextureName(RefStrings.MODID + ":bottle_cherry");
		bottle_quantum = new ItemEnergy().makeBottle(bottle_empty, cap_quantum).setUnlocalizedName("bottle_quantum").setContainerItem(ModItems.bottle_empty).setTextureName(RefStrings.MODID + ":bottle_quantum");
		bottle_sparkle = new ItemEnergy().makeBottle(bottle_empty, cap_sparkle).setUnlocalizedName("bottle_sparkle").setContainerItem(ModItems.bottle_empty).setTextureName(RefStrings.MODID + ":bottle_sparkle");
		bottle_rad = new ItemEnergy().makeBottle(bottle_empty, cap_rad).setUnlocalizedName("bottle_rad").setContainerItem(ModItems.bottle_empty).setTextureName(RefStrings.MODID + ":bottle_rad");
		bottle2_empty = new Item().setUnlocalizedName("bottle2_empty").setTextureName(RefStrings.MODID + ":bottle2_empty");
		bottle2_korl = new ItemEnergy().makeBottle(bottle2_empty, cap_korl).setUnlocalizedName("bottle2_korl").setContainerItem(ModItems.bottle2_empty).setTextureName(RefStrings.MODID + ":bottle2_korl");
		bottle2_fritz = new ItemEnergy().makeBottle(bottle2_empty, cap_fritz).setUnlocalizedName("bottle2_fritz").setContainerItem(ModItems.bottle2_empty).setTextureName(RefStrings.MODID + ":bottle2_fritz");
		bottle2_korl_special = new ItemEnergy().makeBottle(bottle2_empty, cap_korl).setUnlocalizedName("bottle2_korl_special").setContainerItem(ModItems.bottle2_empty).setTextureName(RefStrings.MODID + ":bottle2_korl_special");
		bottle2_fritz_special = new ItemEnergy().makeBottle(bottle2_empty, cap_fritz).setUnlocalizedName("bottle2_fritz_special").setContainerItem(ModItems.bottle2_empty).setTextureName(RefStrings.MODID + ":bottle2_fritz_special");
		flask_infusion = new ItemFlask().setUnlocalizedName("flask_infusion").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":flask");
		chocolate_milk = new ItemEnergy().setUnlocalizedName("chocolate_milk").setTextureName(RefStrings.MODID + ":chocolate_milk");
		coffee = new ItemEnergy().setUnlocalizedName("coffee").setTextureName(RefStrings.MODID + ":coffee");
		coffee_radium = new ItemEnergy().setUnlocalizedName("coffee_radium").setTextureName(RefStrings.MODID + ":coffee_radium");
		chocolate = new ItemPill(0).setUnlocalizedName("chocolate").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":chocolate");
		canned_conserve = (ItemEnumMulti) new ItemConserve().setUnlocalizedName("canned_conserve").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canned");
		can_key = new Item().setUnlocalizedName("can_key").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":can_key");

		boat_rubber = new ItemBoatRubber().setUnlocalizedName("boat_rubber").setTextureName(RefStrings.MODID + ":boat_rubber");
		cart = new ItemModMinecart().setUnlocalizedName("cart");
		train = new ItemTrain().setUnlocalizedName("train");
		drone = new ItemDrone().setUnlocalizedName("drone");

		coin_creeper = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_creeper").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_creeper");
		coin_radiation = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_radiation").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_radiation");
		coin_maskman = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_maskman").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_maskman");
		coin_worm = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_worm").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_worm");
		coin_ufo = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("coin_ufo").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_ufo");
		coin_token = new Item().setUnlocalizedName("coin_token").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coin_token");

		rod_empty = new Item().setUnlocalizedName("rod_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_empty");
		rod = (ItemEnumMulti) new ItemBreedingRod().setUnlocalizedName("rod").setContainerItem(ModItems.rod_empty).setCreativeTab(MainRegistry.controlTab);
		rod_dual_empty = new Item().setUnlocalizedName("rod_dual_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_dual_empty");
		rod_dual = (ItemEnumMulti) new ItemBreedingRod().setUnlocalizedName("rod_dual").setContainerItem(ModItems.rod_dual_empty).setCreativeTab(MainRegistry.controlTab);
		rod_quad_empty = new Item().setUnlocalizedName("rod_quad_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_quad_empty");
		rod_quad = (ItemEnumMulti) new ItemBreedingRod().setUnlocalizedName("rod_quad").setContainerItem(ModItems.rod_quad_empty).setCreativeTab(MainRegistry.controlTab);

		rod_zirnox_empty = new Item().setUnlocalizedName("rod_zirnox_empty").setMaxStackSize(64).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_zirnox_empty");
		rod_zirnox_tritium = new Item().setUnlocalizedName("rod_zirnox_tritium").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_tritium");
		rod_zirnox = (ItemEnumMulti) new ItemZirnoxRod().setUnlocalizedName("rod_zirnox").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rod_zirnox");

		rod_zirnox_natural_uranium_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_natural_uranium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_uranium_fuel_depleted");
		rod_zirnox_uranium_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_uranium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_uranium_fuel_depleted");
		rod_zirnox_thorium_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_thorium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_thorium_fuel_depleted");
		rod_zirnox_mox_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_mox_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_mox_fuel_depleted");
		rod_zirnox_plutonium_fuel_depleted =  new Item().setUnlocalizedName("rod_zirnox_plutonium_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_plutonium_fuel_depleted");
		rod_zirnox_u233_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_u233_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_u233_fuel_depleted");
		rod_zirnox_u235_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_u235_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_u235_fuel_depleted");
		rod_zirnox_les_fuel_depleted = new Item().setUnlocalizedName("rod_zirnox_les_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_les_fuel_depleted");
		rod_zirnox_zfb_mox_depleted = new Item().setUnlocalizedName("rod_zirnox_zfb_mox_depleted").setCreativeTab(MainRegistry.controlTab).setContainerItem(ModItems.rod_zirnox_empty).setTextureName(RefStrings.MODID + ":rod_zirnox_zfb_mox_depleted");

		waste_natural_uranium = new ItemDepletedFuel().setUnlocalizedName("waste_natural_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		waste_uranium = new ItemDepletedFuel().setUnlocalizedName("waste_uranium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		waste_thorium = new ItemDepletedFuel().setUnlocalizedName("waste_thorium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_thorium");
		waste_mox = new ItemDepletedFuel().setUnlocalizedName("waste_mox").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_mox");
		waste_plutonium = new ItemDepletedFuel().setUnlocalizedName("waste_plutonium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plutonium");
		waste_u233 = new ItemDepletedFuel().setUnlocalizedName("waste_u233").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		waste_u235 = new ItemDepletedFuel().setUnlocalizedName("waste_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_uranium");
		waste_schrabidium = new ItemDepletedFuel().setUnlocalizedName("waste_schrabidium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_schrabidium");
		waste_zfb_mox = new ItemDepletedFuel().setUnlocalizedName("waste_zfb_mox").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_zfb_mox");

		waste_plate_u233 = new ItemDepletedFuel().setUnlocalizedName("waste_plate_u233").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_uranium");
		waste_plate_u235 = new ItemDepletedFuel().setUnlocalizedName("waste_plate_u235").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_uranium");
		waste_plate_mox = new ItemDepletedFuel().setUnlocalizedName("waste_plate_mox").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_mox");
		waste_plate_pu239 = new ItemDepletedFuel().setUnlocalizedName("waste_plate_pu239").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_mox");
		waste_plate_sa326 = new ItemDepletedFuel().setUnlocalizedName("waste_plate_sa326").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_sa326");
		waste_plate_ra226be = new ItemDepletedFuel().setUnlocalizedName("waste_plate_ra226be").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_ra226be");
		waste_plate_pu238be = new ItemDepletedFuel().setUnlocalizedName("waste_plate_pu238be").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":waste_plate_pu238be");

		pile_rod_uranium = new ItemPileRod().setUnlocalizedName("pile_rod_uranium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_uranium");
		pile_rod_pu239 = new ItemPileRod().setUnlocalizedName("pile_rod_pu239").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_pu239");
		pile_rod_plutonium = new ItemPileRod().setUnlocalizedName("pile_rod_plutonium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_plutonium");
		pile_rod_source = new ItemPileRod().setUnlocalizedName("pile_rod_source").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_source");
		pile_rod_boron = new ItemPileRod().setUnlocalizedName("pile_rod_boron").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_boron");
		pile_rod_lithium = new ItemPileRod().setUnlocalizedName("pile_rod_lithium").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_lithium");
		pile_rod_detector = new ItemPileRod().setUnlocalizedName("pile_rod_detector").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pile_rod_detector");

		plate_fuel_u233 = new ItemPlateFuel(2200000).setFunction(FunctionEnum.SQUARE_ROOT, 50).setUnlocalizedName("plate_fuel_u233").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":plate_fuel_u233");
		plate_fuel_u235 = new ItemPlateFuel(2200000).setFunction(FunctionEnum.SQUARE_ROOT, 40).setUnlocalizedName("plate_fuel_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":plate_fuel_u235");
		plate_fuel_mox = new ItemPlateFuel(2400000).setFunction(FunctionEnum.LOGARITHM, 50).setUnlocalizedName("plate_fuel_mox").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":plate_fuel_mox");
		plate_fuel_pu239 = new ItemPlateFuel(2000000).setFunction(FunctionEnum.NEGATIVE_QUADRATIC, 50).setUnlocalizedName("plate_fuel_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":plate_fuel_pu239");
		plate_fuel_sa326 = new ItemPlateFuel(2000000).setFunction(FunctionEnum.LINEAR, 80).setUnlocalizedName("plate_fuel_sa326").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":plate_fuel_sa326");
		plate_fuel_ra226be = new ItemPlateFuel(1300000).setFunction(FunctionEnum.PASSIVE, 30).setUnlocalizedName("plate_fuel_ra226be").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":plate_fuel_ra226be");
		plate_fuel_pu238be = new ItemPlateFuel(1000000).setFunction(FunctionEnum.PASSIVE, 50).setUnlocalizedName("plate_fuel_pu238be").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":plate_fuel_pu238be");

		pwr_fuel = new ItemPWRFuel().setUnlocalizedName("pwr_fuel").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pwr_fuel");
		pwr_fuel_hot = new ItemEnumMulti(EnumPWRFuel.class, true, false).setUnlocalizedName("pwr_fuel_hot").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pwr_fuel_hot");
		pwr_fuel_depleted = new ItemEnumMulti(EnumPWRFuel.class, true, false).setUnlocalizedName("pwr_fuel_depleted").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pwr_fuel_depleted");
		pwr_printer = new ItemPWRPrinter().setUnlocalizedName("pwr_printer").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pwr_printer");

		rbmk_lid = new ItemRBMKLid().setUnlocalizedName("rbmk_lid").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rbmk_lid");
		rbmk_lid_glass = new ItemRBMKLid().setUnlocalizedName("rbmk_lid_glass").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rbmk_lid_glass");

		rbmk_pellet_ueu = (ItemRBMKPellet) new ItemRBMKPellet("Unenriched Uranium").setUnlocalizedName("rbmk_pellet_ueu").setTextureName(RefStrings.MODID + ":rbmk_pellet_ueu");
		rbmk_pellet_meu = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Uranium-235").setUnlocalizedName("rbmk_pellet_meu").setTextureName(RefStrings.MODID + ":rbmk_pellet_meu");
		rbmk_pellet_heu233 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Uranium-233").setUnlocalizedName("rbmk_pellet_heu233").setTextureName(RefStrings.MODID + ":rbmk_pellet_heu233");
		rbmk_pellet_heu235 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Uranium-235").setUnlocalizedName("rbmk_pellet_heu235").setTextureName(RefStrings.MODID + ":rbmk_pellet_heu235");
		rbmk_pellet_thmeu = (ItemRBMKPellet) new ItemRBMKPellet("Thorium with MEU Driver Fuel").setUnlocalizedName("rbmk_pellet_thmeu").setTextureName(RefStrings.MODID + ":rbmk_pellet_thmeu");
		rbmk_pellet_lep = (ItemRBMKPellet) new ItemRBMKPellet("Low Enriched Plutonium-239").setUnlocalizedName("rbmk_pellet_lep").setTextureName(RefStrings.MODID + ":rbmk_pellet_lep");
		rbmk_pellet_mep = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Plutonium-239").setUnlocalizedName("rbmk_pellet_mep").setTextureName(RefStrings.MODID + ":rbmk_pellet_mep");
		rbmk_pellet_hep239 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Plutonium-239").setUnlocalizedName("rbmk_pellet_hep239").setTextureName(RefStrings.MODID + ":rbmk_pellet_hep239");
		rbmk_pellet_hep241 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Plutonium-241").setUnlocalizedName("rbmk_pellet_hep241").setTextureName(RefStrings.MODID + ":rbmk_pellet_hep241");
		rbmk_pellet_lea = (ItemRBMKPellet) new ItemRBMKPellet("Low Enriched Americium-242").setUnlocalizedName("rbmk_pellet_lea").setTextureName(RefStrings.MODID + ":rbmk_pellet_lea");
		rbmk_pellet_mea = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Americium-242").setUnlocalizedName("rbmk_pellet_mea").setTextureName(RefStrings.MODID + ":rbmk_pellet_mea");
		rbmk_pellet_hea241 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Americium-241").setUnlocalizedName("rbmk_pellet_hea241").setTextureName(RefStrings.MODID + ":rbmk_pellet_hea241");
		rbmk_pellet_hea242 = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Americium-242").setUnlocalizedName("rbmk_pellet_hea242").setTextureName(RefStrings.MODID + ":rbmk_pellet_hea242");
		rbmk_pellet_men = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Neptunium-237").setUnlocalizedName("rbmk_pellet_men").setTextureName(RefStrings.MODID + ":rbmk_pellet_men");
		rbmk_pellet_hen = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Neptunium-237").setUnlocalizedName("rbmk_pellet_hen").setTextureName(RefStrings.MODID + ":rbmk_pellet_hen");
		rbmk_pellet_mox = (ItemRBMKPellet) new ItemRBMKPellet("Mixed MEU & LEP Oxide").setUnlocalizedName("rbmk_pellet_mox").setTextureName(RefStrings.MODID + ":rbmk_pellet_mox");
		rbmk_pellet_les = (ItemRBMKPellet) new ItemRBMKPellet("Low Enriched Schrabidium-326").setUnlocalizedName("rbmk_pellet_les").setTextureName(RefStrings.MODID + ":rbmk_pellet_les");
		rbmk_pellet_mes = (ItemRBMKPellet) new ItemRBMKPellet("Medium Enriched Schrabidium-326").setUnlocalizedName("rbmk_pellet_mes").setTextureName(RefStrings.MODID + ":rbmk_pellet_mes");
		rbmk_pellet_hes = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Schrabidium-326").setUnlocalizedName("rbmk_pellet_hes").setTextureName(RefStrings.MODID + ":rbmk_pellet_hes");
		rbmk_pellet_leaus = (ItemRBMKPellet) new ItemRBMKPellet("Low Enriched Australium (Tasmanite)").setUnlocalizedName("rbmk_pellet_leaus").setTextureName(RefStrings.MODID + ":rbmk_pellet_leaus");
		rbmk_pellet_heaus = (ItemRBMKPellet) new ItemRBMKPellet("Highly Enriched Australium (Ayerite)").setUnlocalizedName("rbmk_pellet_heaus").setTextureName(RefStrings.MODID + ":rbmk_pellet_heaus");
		rbmk_pellet_po210be = (ItemRBMKPellet) new ItemRBMKPellet("Polonium-210 & Beryllium Neutron Source").disableXenon().setUnlocalizedName("rbmk_pellet_po210be").setTextureName(RefStrings.MODID + ":rbmk_pellet_po210be");
		rbmk_pellet_ra226be = (ItemRBMKPellet) new ItemRBMKPellet("Radium-226 & Beryllium Neutron Source").disableXenon().setUnlocalizedName("rbmk_pellet_ra226be").setTextureName(RefStrings.MODID + ":rbmk_pellet_ra226be");
		rbmk_pellet_pu238be = (ItemRBMKPellet) new ItemRBMKPellet("Plutonium-238 & Beryllium Neutron Source").setUnlocalizedName("rbmk_pellet_pu238be").setTextureName(RefStrings.MODID + ":rbmk_pellet_pu238be");
		rbmk_pellet_balefire_gold = (ItemRBMKPellet) new ItemRBMKPellet("Antihydrogen in a Magnetized Gold-198 Lattice").disableXenon().setUnlocalizedName("rbmk_pellet_balefire_gold").setTextureName(RefStrings.MODID + ":rbmk_pellet_balefire_gold");
		rbmk_pellet_flashlead = (ItemRBMKPellet) new ItemRBMKPellet("Antihydrogen confined by a Magnetized Gold-198 and Lead-209 Lattice").disableXenon().setUnlocalizedName("rbmk_pellet_flashlead").setTextureName(RefStrings.MODID + ":rbmk_pellet_flashlead");
		rbmk_pellet_balefire = (ItemRBMKPellet) new ItemRBMKPellet("Draconic Flames").disableXenon().setUnlocalizedName("rbmk_pellet_balefire").setTextureName(RefStrings.MODID + ":rbmk_pellet_balefire");
		rbmk_pellet_zfb_bismuth = (ItemRBMKPellet) new ItemRBMKPellet("Zirconium Fast Breeder - LEU/HEP-241#Bi").setUnlocalizedName("rbmk_pellet_zfb_bismuth").setTextureName(RefStrings.MODID + ":rbmk_pellet_zfb_bismuth");
		rbmk_pellet_zfb_pu241 = (ItemRBMKPellet) new ItemRBMKPellet("Zirconium Fast Breeder - HEU-235/HEP-240#Pu-241").setUnlocalizedName("rbmk_pellet_zfb_pu241").setTextureName(RefStrings.MODID + ":rbmk_pellet_zfb_pu241");
		rbmk_pellet_zfb_am_mix = (ItemRBMKPellet) new ItemRBMKPellet("Zirconium Fast Breeder - HEP-241#MEA").setUnlocalizedName("rbmk_pellet_zfb_am_mix").setTextureName(RefStrings.MODID + ":rbmk_pellet_zfb_am_mix");
		rbmk_pellet_drx = (ItemRBMKPellet) new ItemRBMKPellet(EnumChatFormatting.OBFUSCATED + "can't you hear, can't you hear the thunder?").setUnlocalizedName("rbmk_pellet_drx").setTextureName(RefStrings.MODID + ":rbmk_pellet_drx");

		rbmk_fuel_empty = new Item().setUnlocalizedName("rbmk_fuel_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":rbmk_fuel_empty");
		rbmk_fuel_ueu = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_ueu)
				.setYield(100000000D)
				.setStats(15)
				.setFunction(EnumBurnFunc.LOG_TEN)
				.setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(0.65) //0.5 is too much of a nerf in heat; pu239 buildup justifies it being on par with MEU ig
				.setMeltingPoint(2865)
				.setUnlocalizedName("rbmk_fuel_ueu").setTextureName(RefStrings.MODID + ":rbmk_fuel_ueu");
		rbmk_fuel_meu = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_meu)
				.setYield(100000000D)
				.setStats(20)
				.setFunction(EnumBurnFunc.LOG_TEN)
				.setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(0.65) //0.75 was a bit too much...
				.setMeltingPoint(2865)
				.setUnlocalizedName("rbmk_fuel_meu").setTextureName(RefStrings.MODID + ":rbmk_fuel_meu");
		rbmk_fuel_heu233 = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_heu233)
				.setYield(100000000D)
				.setStats(27.5D)
				.setFunction(EnumBurnFunc.LINEAR)
				.setHeat(1.25D)
				.setMeltingPoint(2865)
				.setUnlocalizedName("rbmk_fuel_heu233").setTextureName(RefStrings.MODID + ":rbmk_fuel_heu233");
		rbmk_fuel_heu235 = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_heu235)
				.setYield(100000000D)
				.setStats(50) //Consistency with HEN; its critical mass is too high to justify a linear function
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setMeltingPoint(2865)
				.setUnlocalizedName("rbmk_fuel_heu235").setTextureName(RefStrings.MODID + ":rbmk_fuel_heu235");
		rbmk_fuel_thmeu = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_thmeu)
				.setYield(100000000D)
				.setStats(20)
				.setFunction(EnumBurnFunc.PLATEU)
				.setDepletionFunction(EnumDepleteFunc.BOOSTED_SLOPE)
				.setHeat(0.65D) //Consistency with MEU
				.setMeltingPoint(3350)
				.setUnlocalizedName("rbmk_fuel_thmeu").setTextureName(RefStrings.MODID + ":rbmk_fuel_thmeu");
		rbmk_fuel_lep = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_lep)
				.setYield(100000000D)
				.setStats(35)
				.setFunction(EnumBurnFunc.LOG_TEN)
				.setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(0.75D)
				.setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_lep").setTextureName(RefStrings.MODID + ":rbmk_fuel_lep");
		rbmk_fuel_mep = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_mep)
				.setYield(100000000D)
				.setStats(35)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_mep").setTextureName(RefStrings.MODID + ":rbmk_fuel_mep");
		rbmk_fuel_hep239 = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_hep239)
				.setYield(100000000D)
				.setStats(30)
				.setFunction(EnumBurnFunc.LINEAR)
				.setHeat(1.25D)
				.setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_hep").setTextureName(RefStrings.MODID + ":rbmk_fuel_hep");
		rbmk_fuel_hep241 = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_hep241)
				.setYield(100000000D)
				.setStats(40)
				.setFunction(EnumBurnFunc.LINEAR)
				.setHeat(1.75D)
				.setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_hep241").setTextureName(RefStrings.MODID + ":rbmk_fuel_hep241");
		rbmk_fuel_lea = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_lea)
				.setYield(100000000D)
				.setStats(60, 10)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(1.5D)
				.setMeltingPoint(2386)
				.setUnlocalizedName("rbmk_fuel_lea").setTextureName(RefStrings.MODID + ":rbmk_fuel_lea");
		rbmk_fuel_mea = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_mea)
				.setYield(100000000D)
				.setStats(35D, 20)
				.setFunction(EnumBurnFunc.ARCH)
				.setHeat(1.75D)
				.setMeltingPoint(2386)
				.setUnlocalizedName("rbmk_fuel_mea").setTextureName(RefStrings.MODID + ":rbmk_fuel_mea");
		rbmk_fuel_hea241 = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_hea241)
				.setYield(100000000D)
				.setStats(65, 15)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setHeat(1.85D)
				.setMeltingPoint(2386)
				.setNeutronTypes(NType.FAST, NType.FAST)
				.setUnlocalizedName("rbmk_fuel_hea241").setTextureName(RefStrings.MODID + ":rbmk_fuel_hea241");
		rbmk_fuel_hea242 = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_hea242)
				.setYield(100000000D)
				.setStats(45)
				.setFunction(EnumBurnFunc.LINEAR)
				.setHeat(2D)
				.setMeltingPoint(2386)
				.setUnlocalizedName("rbmk_fuel_hea242").setTextureName(RefStrings.MODID + ":rbmk_fuel_hea242");
		rbmk_fuel_men = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_men)
				.setYield(100000000D)
				.setStats(30)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setHeat(0.75)
				.setMeltingPoint(2800)
				.setNeutronTypes(NType.ANY, NType.FAST) //Build-up of Pu-239 leads to both speeds of neutrons grooving
				.setUnlocalizedName("rbmk_fuel_men").setTextureName(RefStrings.MODID + ":rbmk_fuel_men");
		rbmk_fuel_hen = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_hen)
				.setYield(100000000D)
				.setStats(40)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setMeltingPoint(2800)
				.setNeutronTypes(NType.FAST, NType.FAST)
				.setUnlocalizedName("rbmk_fuel_hen").setTextureName(RefStrings.MODID + ":rbmk_fuel_hen");
		rbmk_fuel_mox = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_mox)
				.setYield(100000000D)
				.setStats(40)
				.setFunction(EnumBurnFunc.LOG_TEN)
				.setDepletionFunction(EnumDepleteFunc.RAISING_SLOPE)
				.setMeltingPoint(2815)
				.setUnlocalizedName("rbmk_fuel_mox").setTextureName(RefStrings.MODID + ":rbmk_fuel_mox");
		rbmk_fuel_les = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_les)
				.setYield(100000000D)
				.setStats(50)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setHeat(1.25D)
				.setMeltingPoint(2500)
				.setNeutronTypes(NType.SLOW, NType.SLOW) //Beryllium Moderation
				.setUnlocalizedName("rbmk_fuel_les").setTextureName(RefStrings.MODID + ":rbmk_fuel_les");
		rbmk_fuel_mes = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_mes)
				.setYield(100000000D)
				.setStats(75D)
				.setFunction(EnumBurnFunc.ARCH)
				.setHeat(1.5D)
				.setMeltingPoint(2750)
				.setUnlocalizedName("rbmk_fuel_mes").setTextureName(RefStrings.MODID + ":rbmk_fuel_mes");
		rbmk_fuel_hes = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_hes)
				.setYield(100000000D)
				.setStats(90)
				.setFunction(EnumBurnFunc.LINEAR)
				.setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setHeat(1.75D)
				.setMeltingPoint(3000)
				.setUnlocalizedName("rbmk_fuel_hes").setTextureName(RefStrings.MODID + ":rbmk_fuel_hes");
		rbmk_fuel_leaus = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_leaus)
				.setYield(100000000D)
				.setStats(30)
				.setFunction(EnumBurnFunc.SIGMOID)
				.setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setXenon(0.05D, 50D)
				.setHeat(1.5D)
				.setMeltingPoint(7029).setUnlocalizedName("rbmk_fuel_leaus").setTextureName(RefStrings.MODID + ":rbmk_fuel_leaus");
		rbmk_fuel_heaus = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_heaus)
				.setYield(100000000D)
				.setStats(35)
				.setFunction(EnumBurnFunc.LINEAR)
				.setXenon(0.05D, 50D)
				.setHeat(1.5D)
				.setMeltingPoint(5211).setUnlocalizedName("rbmk_fuel_heaus").setTextureName(RefStrings.MODID + ":rbmk_fuel_heaus");
		rbmk_fuel_po210be = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_po210be)
				.setYield(25000000D)
				.setStats(0D, 50)
				.setFunction(EnumBurnFunc.PASSIVE)
				.setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setXenon(0.0D, 50D)
				.setHeat(0.1D)
				.setDiffusion(0.05D)
				.setMeltingPoint(1287)
				.setNeutronTypes(NType.SLOW, NType.SLOW) //Beryllium Moderation
				.setUnlocalizedName("rbmk_fuel_po210be").setTextureName(RefStrings.MODID + ":rbmk_fuel_po210be");
		rbmk_fuel_ra226be = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_ra226be)
				.setYield(100000000D)
				.setStats(0D, 20)
				.setFunction(EnumBurnFunc.PASSIVE)
				.setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setXenon(0.0D, 50D)
				.setHeat(0.035D)
				.setDiffusion(0.5D)
				.setMeltingPoint(700)
				.setNeutronTypes(NType.SLOW, NType.SLOW) //Beryllium Moderation
				.setUnlocalizedName("rbmk_fuel_ra226be").setTextureName(RefStrings.MODID + ":rbmk_fuel_ra226be");
		rbmk_fuel_pu238be = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_pu238be)
				.setYield(50000000D)
				.setStats(40, 40)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setHeat(0.1D)
				.setDiffusion(0.05D)
				.setMeltingPoint(1287)
				.setNeutronTypes(NType.SLOW, NType.SLOW) //Beryllium Moderation
				.setUnlocalizedName("rbmk_fuel_pu238be").setTextureName(RefStrings.MODID + ":rbmk_fuel_pu238be");
		rbmk_fuel_balefire_gold = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_balefire_gold)
				.setYield(100000000D)
				.setStats(50, 10)
				.setFunction(EnumBurnFunc.ARCH)
				.setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setXenon(0.0D, 50D)
				.setMeltingPoint(2000)
				.setUnlocalizedName("rbmk_fuel_balefire_gold").setTextureName(RefStrings.MODID + ":rbmk_fuel_balefire_gold");
		rbmk_fuel_flashlead = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_flashlead)
				.setYield(250000000D)
				.setStats(40, 50)
				.setFunction(EnumBurnFunc.ARCH)
				.setDepletionFunction(EnumDepleteFunc.LINEAR)
				.setXenon(0.0D, 50D)
				.setMeltingPoint(2050)
				.setUnlocalizedName("rbmk_fuel_flashlead").setTextureName(RefStrings.MODID + ":rbmk_fuel_flashlead");
		rbmk_fuel_balefire = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_balefire)
				.setYield(100000000D)
				.setStats(100, 35)
				.setFunction(EnumBurnFunc.LINEAR)
				.setXenon(0.0D, 50D)
				.setHeat(3D)
				.setMeltingPoint(3652)
				.setUnlocalizedName("rbmk_fuel_balefire").setTextureName(RefStrings.MODID + ":rbmk_fuel_balefire");
		rbmk_fuel_zfb_bismuth = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_zfb_bismuth)
				.setYield(50000000D)
				.setStats(20)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setHeat(1.75D)
				.setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_zfb_bismuth").setTextureName(RefStrings.MODID + ":rbmk_fuel_zfb_bismuth");
		rbmk_fuel_zfb_pu241 = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_zfb_pu241)
				.setYield(50000000D)
				.setStats(20)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setMeltingPoint(2865)
				.setUnlocalizedName("rbmk_fuel_zfb_pu241").setTextureName(RefStrings.MODID + ":rbmk_fuel_zfb_pu241");
		rbmk_fuel_zfb_am_mix = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_zfb_am_mix)
				.setYield(50000000D)
				.setStats(20)
				.setFunction(EnumBurnFunc.LINEAR)
				.setHeat(1.75D)
				.setMeltingPoint(2744)
				.setUnlocalizedName("rbmk_fuel_zfb_am_mix").setTextureName(RefStrings.MODID + ":rbmk_fuel_zfb_am_mix");
		rbmk_fuel_drx = (ItemRBMKRod) new ItemRBMKRod(rbmk_pellet_drx)
				.setYield(10000000D)
				.setStats(1000, 10)
				.setFunction(EnumBurnFunc.QUADRATIC)
				.setHeat(0.1D)
				.setMeltingPoint(100000)
				.setUnlocalizedName("rbmk_fuel_drx").setTextureName(RefStrings.MODID + ":rbmk_fuel_drx");
		rbmk_fuel_test = (ItemRBMKRod) new ItemRBMKRod("THE VOICES")
				.setYield(1000000D)
				.setStats(100)
				.setFunction(EnumBurnFunc.EXPERIMENTAL)
				.setHeat(1.0D)
				.setMeltingPoint(100000)
				.setUnlocalizedName("rbmk_fuel_test").setTextureName(RefStrings.MODID + ":rbmk_fuel_test");
		/* Experimental flux curve shit
		rbmk_fuel_curve = (ItemRBMKRod) new ItemRBMKRod("3D Flux Curve Test")
				.setFluxCurve(true)
				.setOutputFluxCurve((fluxQuantity, fluxRatio) -> fluxQuantity * (1 - Math.pow(fluxRatio, 2)))
				.setDepletionOutputRatioCurve((ratioIn, depletion) -> Math.pow(ratioIn, 2) * depletion)
				.setYield(1000000D)
				.setStats(75)
				.setFunction(EnumBurnFunc.SQUARE_ROOT)
				.setHeat(1.5D)
				.setMeltingPoint(100000)
				.setUnlocalizedName("rbmk_fuel_curve").setTextureName(RefStrings.MODID + ":rbmk_fuel_curve");
		 */

		watz_pellet = new ItemWatzPellet().setUnlocalizedName("watz_pellet").setTextureName(RefStrings.MODID + ":watz_pellet");
		watz_pellet_depleted = new ItemWatzPellet().setUnlocalizedName("watz_pellet_depleted").setTextureName(RefStrings.MODID + ":watz_pellet");

		icf_pellet_empty = new Item().setUnlocalizedName("icf_pellet_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":icf_pellet_empty");
		icf_pellet = new ItemICFPellet().setUnlocalizedName("icf_pellet").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":icf_pellet");
		icf_pellet_depleted = new Item().setUnlocalizedName("icf_pellet_depleted").setCreativeTab(MainRegistry.controlTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":icf_pellet_depleted");

		trinitite = new ItemNuclearWaste().setUnlocalizedName("trinitite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":trinitite");
		nuclear_waste_long = new ItemWasteLong().setUnlocalizedName("nuclear_waste_long").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long");
		nuclear_waste_long_tiny = new ItemWasteLong().setUnlocalizedName("nuclear_waste_long_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long_tiny");
		nuclear_waste_short = new ItemWasteShort().setUnlocalizedName("nuclear_waste_short").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short");
		nuclear_waste_short_tiny = new ItemWasteShort().setUnlocalizedName("nuclear_waste_short_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short_tiny");
		nuclear_waste_long_depleted = new ItemWasteLong().setUnlocalizedName("nuclear_waste_long_depleted").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long_depleted");
		nuclear_waste_long_depleted_tiny = new ItemWasteLong().setUnlocalizedName("nuclear_waste_long_depleted_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_long_depleted_tiny");
		nuclear_waste_short_depleted = new ItemWasteShort().setUnlocalizedName("nuclear_waste_short_depleted").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short_depleted");
		nuclear_waste_short_depleted_tiny = new ItemWasteShort().setUnlocalizedName("nuclear_waste_short_depleted_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_short_depleted_tiny");
		nuclear_waste = new ItemNuclearWaste().setUnlocalizedName("nuclear_waste").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste");
		nuclear_waste_tiny = new ItemNuclearWaste().setUnlocalizedName("nuclear_waste_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_tiny");
		nuclear_waste_vitrified = new ItemNuclearWaste().setUnlocalizedName("nuclear_waste_vitrified").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_vitrified");
		nuclear_waste_vitrified_tiny = new ItemNuclearWaste().setUnlocalizedName("nuclear_waste_vitrified_tiny").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nuclear_waste_vitrified_tiny");
		scrap_plastic = new ItemPlasticScrap().setUnlocalizedName("scrap_plastic").setTextureName(RefStrings.MODID + ":scrap_plastic");
		scrap = new Item().setUnlocalizedName("scrap").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scrap");
		scrap_oil = new Item().setUnlocalizedName("scrap_oil").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scrap_oil");
		scrap_nuclear = new Item().setUnlocalizedName("scrap_nuclear").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":scrap_nuclear");
		containment_box = new ItemLeadBox().setUnlocalizedName("containment_box").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":containment_box");
		plastic_bag = new ItemPlasticBag().setUnlocalizedName("plastic_bag").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":plastic_bag");

		ammo_bag = new ItemAmmoBag().setUnlocalizedName("ammo_bag").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":ammo_bag");
		ammo_bag_infinite = new ItemAmmoBag().setUnlocalizedName("ammo_bag_infinite").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":ammo_bag_infinite");
		casing_bag = new ItemCasingBag().setUnlocalizedName("casing_bag").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":casing_bag");

		debris_graphite = new Item().setUnlocalizedName("debris_graphite").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_graphite");
		debris_metal = new Item().setUnlocalizedName("debris_metal").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_metal");
		debris_fuel = new Item().setUnlocalizedName("debris_fuel").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_fuel");
		debris_concrete = new Item().setUnlocalizedName("debris_concrete").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_concrete");
		debris_exchanger = new Item().setUnlocalizedName("debris_exchanger").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_exchanger");
		debris_shrapnel =new Item().setUnlocalizedName("debris_shrapnel").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_shrapnel");
		debris_element =new Item().setUnlocalizedName("debris_element").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":debris_element");

		pellet_cluster = new ItemCustomLore().setUnlocalizedName("pellet_cluster").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_cluster");
		powder_fire = new ItemCustomLore().setUnlocalizedName("powder_fire").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_red_phosphorus");
		powder_ice = new ItemCustomLore().setUnlocalizedName("powder_ice").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_ice");
		powder_poison = new ItemCustomLore().setUnlocalizedName("powder_poison").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_poison");
		powder_thermite = new ItemCustomLore().setUnlocalizedName("powder_thermite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":powder_thermite");
		cordite = new Item().setUnlocalizedName("cordite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":cordite");
		ballistite = new Item().setUnlocalizedName("ballistite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ballistite");
		ball_dynamite = new Item().setUnlocalizedName("ball_dynamite").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ball_dynamite");
		ball_tnt = new Item().setUnlocalizedName("ball_tnt").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ball_tnt");
		ball_tatb = new Item().setUnlocalizedName("ball_tatb").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ball_tatb");
		ball_fireclay = new Item().setUnlocalizedName("ball_fireclay").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ball_fireclay");
		pellet_gas = new ItemCustomLore().setUnlocalizedName("pellet_gas").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellet_gas");
		magnetron = new ItemCustomLore().setUnlocalizedName("magnetron").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":magnetron_alt");
		pellet_buckshot = new Item().setUnlocalizedName("pellet_buckshot").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_lead");
		pellet_charged = new Item().setUnlocalizedName("pellet_charged").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":pellets_charged");

		rangefinder = new ItemRangefinder().setUnlocalizedName("rangefinder").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":rangefinder");
		designator = new ItemDesingator().setUnlocalizedName("designator").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator");
		designator_range = new ItemDesingatorRange().setUnlocalizedName("designator_range").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator_range_alt");
		designator_manual = new ItemDesingatorManual().setUnlocalizedName("designator_manual").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator_manual");
		designator_arty_range = new ItemDesignatorArtyRange().setUnlocalizedName("designator_arty_range").setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":designator_arty_range");
		launch_code_piece = new Item().setUnlocalizedName("launch_code_piece").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":launch_code_piece");
		launch_code = new Item().setUnlocalizedName("launch_code").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":launch_code");
		launch_key = new Item().setUnlocalizedName("launch_key").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":launch_key");
		missile_assembly = new Item().setUnlocalizedName("missile_assembly").setMaxStackSize(1).setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":missile_assembly");
		missile_generic = new ItemMissile(MissileFormFactor.V2, MissileTier.TIER1).setUnlocalizedName("missile_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_generic");
		missile_anti_ballistic = new ItemMissile(MissileFormFactor.ABM, MissileTier.TIER1).setUnlocalizedName("missile_anti_ballistic").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_anti_ballistic");
		missile_incendiary = new ItemMissile(MissileFormFactor.V2, MissileTier.TIER1).setUnlocalizedName("missile_incendiary").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_incendiary");
		missile_cluster = new ItemMissile(MissileFormFactor.V2, MissileTier.TIER1).setUnlocalizedName("missile_cluster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_cluster");
		missile_buster = new ItemMissile(MissileFormFactor.V2, MissileTier.TIER1).setUnlocalizedName("missile_buster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_buster");
		missile_decoy = new ItemMissile(MissileFormFactor.V2, MissileTier.TIER1).setUnlocalizedName("missile_decoy").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_decoy");
		missile_strong = new ItemMissile(MissileFormFactor.STRONG, MissileTier.TIER2).setUnlocalizedName("missile_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_strong");
		missile_incendiary_strong = new ItemMissile(MissileFormFactor.STRONG, MissileTier.TIER2).setUnlocalizedName("missile_incendiary_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_incendiary_strong");
		missile_cluster_strong = new ItemMissile(MissileFormFactor.STRONG, MissileTier.TIER2).setUnlocalizedName("missile_cluster_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_cluster_strong");
		missile_buster_strong = new ItemMissile(MissileFormFactor.STRONG, MissileTier.TIER2).setUnlocalizedName("missile_buster_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_buster_strong");
		missile_emp_strong = new ItemMissile(MissileFormFactor.STRONG, MissileTier.TIER2).setUnlocalizedName("missile_emp_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_emp_strong");
		missile_burst = new ItemMissile(MissileFormFactor.HUGE, MissileTier.TIER3).setUnlocalizedName("missile_burst").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_burst");
		missile_inferno = new ItemMissile(MissileFormFactor.HUGE, MissileTier.TIER3).setUnlocalizedName("missile_inferno").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_inferno");
		missile_rain = new ItemMissile(MissileFormFactor.HUGE, MissileTier.TIER3).setUnlocalizedName("missile_rain").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_rain");
		missile_drill = new ItemMissile(MissileFormFactor.HUGE, MissileTier.TIER3).setUnlocalizedName("missile_drill").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_drill");
		missile_nuclear = new ItemMissile(MissileFormFactor.ATLAS, MissileTier.TIER4).setUnlocalizedName("missile_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_nuclear");
		missile_nuclear_cluster = new ItemMissile(MissileFormFactor.ATLAS, MissileTier.TIER4).setUnlocalizedName("missile_nuclear_cluster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_nuclear_cluster");
		missile_volcano = new ItemMissile(MissileFormFactor.ATLAS, MissileTier.TIER4).setUnlocalizedName("missile_volcano").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_volcano");
		missile_doomsday = new ItemMissile(MissileFormFactor.ATLAS, MissileTier.TIER4).setUnlocalizedName("missile_doomsday").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_doomsday");
		missile_doomsday_rusted = new ItemMissile(MissileFormFactor.ATLAS, MissileTier.TIER4).notLaunchable().setUnlocalizedName("missile_doomsday_rusted").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_doomsday");
		missile_taint = new ItemMissile(MissileFormFactor.MICRO, MissileTier.TIER0).setUnlocalizedName("missile_taint").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_taint");
		missile_micro = new ItemMissile(MissileFormFactor.MICRO, MissileTier.TIER0).setUnlocalizedName("missile_micro").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_micro");
		missile_bhole = new ItemMissile(MissileFormFactor.MICRO, MissileTier.TIER0).setUnlocalizedName("missile_bhole").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_bhole");
		missile_schrabidium = new ItemMissile(MissileFormFactor.MICRO, MissileTier.TIER0).setUnlocalizedName("missile_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_schrabidium");
		missile_emp = new ItemMissile(MissileFormFactor.MICRO, MissileTier.TIER0).setUnlocalizedName("missile_emp").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_emp");
		missile_shuttle = new ItemMissile(MissileFormFactor.OTHER, MissileTier.TIER3, MissileFuel.KEROSENE_PEROXIDE).setUnlocalizedName("missile_shuttle").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_shuttle");
		missile_stealth = new ItemMissile(MissileFormFactor.STRONG, MissileTier.TIER1).setUnlocalizedName("missile_stealth").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":missile_stealth");
		missile_test = new ItemMissile(MissileFormFactor.MICRO, MissileTier.TIER0).setUnlocalizedName("missile_test").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":missile_micro");
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
		sat_lunar_miner = new ItemSatChip().setUnlocalizedName("sat_lunar_miner").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_lunar_miner");
		sat_gerald = new ItemSatChip().setUnlocalizedName("sat_gerald").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_gerald");
		sat_chip = new ItemSatChip().setUnlocalizedName("sat_chip").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_chip");
		sat_interface = new ItemSatInterface().setUnlocalizedName("sat_interface").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_interface");
		sat_coord = new ItemSatInterface().setUnlocalizedName("sat_coord").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_coord");
		sat_designator = new ItemSatDesignator().setUnlocalizedName("sat_designator").setFull3D().setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_designator");
		sat_relay = new ItemSatChip().setUnlocalizedName("sat_relay").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":sat_relay");

		mp_thruster_10_kerosene = new ItemCustomMissilePart().makeThruster(FuelType.KEROSENE, 1F, 1.5F, PartSize.SIZE_10).setHealth(10F)					.setUnlocalizedName("mp_thruster_10_kerosene");
		mp_thruster_10_solid = new ItemCustomMissilePart().makeThruster(FuelType.SOLID, 1F, 1.5F, PartSize.SIZE_10).setHealth(15F)						.setUnlocalizedName("mp_thruster_10_solid");
		mp_thruster_10_xenon = new ItemCustomMissilePart().makeThruster(FuelType.XENON, 1F, 1.5F, PartSize.SIZE_10).setHealth(5F)							.setUnlocalizedName("mp_thruster_10_xenon");
		mp_thruster_15_kerosene = new ItemCustomMissilePart().makeThruster(FuelType.KEROSENE, 1F, 7.5F, PartSize.SIZE_15).setHealth(15F)					.setUnlocalizedName("mp_thruster_15_kerosene");
		mp_thruster_15_kerosene_dual = new ItemCustomMissilePart().makeThruster(FuelType.KEROSENE, 1F, 2.5F, PartSize.SIZE_15).setHealth(15F)				.setUnlocalizedName("mp_thruster_15_kerosene_dual");
		mp_thruster_15_kerosene_triple = new ItemCustomMissilePart().makeThruster(FuelType.KEROSENE, 1F, 5F, PartSize.SIZE_15).setHealth(15F)				.setUnlocalizedName("mp_thruster_15_kerosene_triple");
		mp_thruster_15_solid = new ItemCustomMissilePart().makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15).setHealth(20F)							.setUnlocalizedName("mp_thruster_15_solid");
		mp_thruster_15_solid_hexdecuple = new ItemCustomMissilePart().makeThruster(FuelType.SOLID, 1F, 5F, PartSize.SIZE_15).setHealth(25F).setRarity(Rarity.UNCOMMON).setUnlocalizedName("mp_thruster_15_solid_hexdecuple");
		mp_thruster_15_hydrogen = new ItemCustomMissilePart().makeThruster(FuelType.HYDROGEN, 1F, 7.5F, PartSize.SIZE_15).setHealth(20F)					.setUnlocalizedName("mp_thruster_15_hydrogen");
		mp_thruster_15_hydrogen_dual = new ItemCustomMissilePart().makeThruster(FuelType.HYDROGEN, 1F, 2.5F, PartSize.SIZE_15).setHealth(15F)				.setUnlocalizedName("mp_thruster_15_hydrogen_dual");
		mp_thruster_15_balefire_short = new ItemCustomMissilePart().makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15).setHealth(25F)				.setUnlocalizedName("mp_thruster_15_balefire_short");
		mp_thruster_15_balefire = new ItemCustomMissilePart().makeThruster(FuelType.BALEFIRE, 1F, 5F, PartSize.SIZE_15).setHealth(25F)					.setUnlocalizedName("mp_thruster_15_balefire");
		mp_thruster_15_balefire_large = new ItemCustomMissilePart().makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setHealth(35F)			.setUnlocalizedName("mp_thruster_15_balefire_large");
		mp_thruster_15_balefire_large_rad = new ItemCustomMissilePart().makeThruster(FuelType.BALEFIRE, 1F, 7.5F, PartSize.SIZE_15).setAuthor("The Master").setHealth(35F).setRarity(Rarity.UNCOMMON).setUnlocalizedName("mp_thruster_15_balefire_large_rad");
		mp_thruster_20_kerosene = new ItemCustomMissilePart().makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F)					.setUnlocalizedName("mp_thruster_20_kerosene");
		mp_thruster_20_kerosene_dual = new ItemCustomMissilePart().makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F)				.setUnlocalizedName("mp_thruster_20_kerosene_dual");
		mp_thruster_20_kerosene_triple = new ItemCustomMissilePart().makeThruster(FuelType.KEROSENE, 1F, 100F, PartSize.SIZE_20).setHealth(30F)			.setUnlocalizedName("mp_thruster_20_kerosene_triple");
		mp_thruster_20_solid = new ItemCustomMissilePart().makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F).setWittyText("It's basically just a big hole at the end of the fuel tank.").setUnlocalizedName("mp_thruster_20_solid");
		mp_thruster_20_solid_multi = new ItemCustomMissilePart().makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F)					.setUnlocalizedName("mp_thruster_20_solid_multi");
		mp_thruster_20_solid_multier = new ItemCustomMissilePart().makeThruster(FuelType.SOLID, 1F, 100F, PartSize.SIZE_20).setHealth(35F).setWittyText("Did I miscount? Hope not.").setUnlocalizedName("mp_thruster_20_solid_multier");

		mp_stability_10_flat = new ItemCustomMissilePart().makeStability(0.5F, PartSize.SIZE_10).setHealth(10F)											.setUnlocalizedName("mp_stability_10_flat");
		mp_stability_10_cruise = new ItemCustomMissilePart().makeStability(0.25F, PartSize.SIZE_10).setHealth(5F)											.setUnlocalizedName("mp_stability_10_cruise");
		mp_stability_10_space = new ItemCustomMissilePart().makeStability(0.35F, PartSize.SIZE_10).setHealth(5F).setRarity(Rarity.COMMON).setWittyText("Standing there alone, the ship is waiting / All systems are go, are you sure?")					.setUnlocalizedName("mp_stability_10_space");
		mp_stability_15_flat = new ItemCustomMissilePart().makeStability(0.5F, PartSize.SIZE_15).setHealth(10F)											.setUnlocalizedName("mp_stability_15_flat");
		mp_stability_15_thin = new ItemCustomMissilePart().makeStability(0.35F, PartSize.SIZE_15).setHealth(5F)											.setUnlocalizedName("mp_stability_15_thin");
		mp_stability_15_soyuz = new ItemCustomMissilePart().makeStability(0.25F, PartSize.SIZE_15).setHealth(15F).setRarity(Rarity.COMMON).setWittyText("Союз!").setUnlocalizedName("mp_stability_15_soyuz");
		mp_stability_20_flat = new ItemCustomMissilePart().makeStability(0.5F, PartSize.SIZE_20)															.setUnlocalizedName("mp_s_20");

		mp_fuselage_10_kerosene = new ItemCustomMissilePart().makeFuselage(FuelType.KEROSENE, 2500F, PartSize.SIZE_10, PartSize.SIZE_10).setAuthor("Hoboy").setHealth(20F).setUnlocalizedName("mp_fuselage_10_kerosene");
		mp_fuselage_10_kerosene_camo =			((ItemCustomMissilePart) mp_fuselage_10_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_10_kerosene_camo");
		mp_fuselage_10_kerosene_desert = 		((ItemCustomMissilePart) mp_fuselage_10_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Desert Camo").setUnlocalizedName("mp_fuselage_10_kerosene_desert");
		mp_fuselage_10_kerosene_sky = 			((ItemCustomMissilePart) mp_fuselage_10_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_10_kerosene_sky");
		mp_fuselage_10_kerosene_flames = 		((ItemCustomMissilePart) mp_fuselage_10_kerosene).copy().setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_kerosene_flames");
		mp_fuselage_10_kerosene_insulation = 	((ItemCustomMissilePart) mp_fuselage_10_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(25F).setUnlocalizedName("mp_fuselage_10_kerosene_insulation");
		mp_fuselage_10_kerosene_sleek = 		((ItemCustomMissilePart) mp_fuselage_10_kerosene).copy().setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(35F).setUnlocalizedName("mp_fuselage_10_kerosene_sleek");
		mp_fuselage_10_kerosene_metal = 		((ItemCustomMissilePart) mp_fuselage_10_kerosene).copy().setRarity(Rarity.UNCOMMON).setTitle("Bolted Metal").setHealth(30F).setAuthor("Hoboy").setUnlocalizedName("mp_fuselage_10_kerosene_metal");
		mp_fuselage_10_kerosene_taint =			((ItemCustomMissilePart) mp_fuselage_10_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted").setUnlocalizedName("mp_fuselage_10_kerosene_taint");

		mp_fuselage_10_solid = new ItemCustomMissilePart().makeFuselage(FuelType.SOLID, 2500F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(25F)			.setUnlocalizedName("mp_fuselage_10_solid");
		mp_fuselage_10_solid_flames = 		((ItemCustomMissilePart) mp_fuselage_10_solid).copy().setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_solid_flames");
		mp_fuselage_10_solid_insulation = 	((ItemCustomMissilePart) mp_fuselage_10_solid).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(30F).setUnlocalizedName("mp_fuselage_10_solid_insulation");
		mp_fuselage_10_solid_sleek = 		((ItemCustomMissilePart) mp_fuselage_10_solid).copy().setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(35F).setUnlocalizedName("mp_fuselage_10_solid_sleek");
		mp_fuselage_10_solid_soviet_glory = ((ItemCustomMissilePart) mp_fuselage_10_solid).copy().setRarity(Rarity.EPIC).setAuthor("Hoboy").setHealth(35F).setTitle("Soviet Glory").setUnlocalizedName("mp_fuselage_10_solid_soviet_glory");
		mp_fuselage_10_solid_cathedral = 	((ItemCustomMissilePart) mp_fuselage_10_solid).copy().setRarity(Rarity.RARE).setAuthor("Satan").setTitle("Unholy Cathedral").setWittyText("Quakeesque!").setUnlocalizedName("mp_fuselage_10_solid_cathedral");
		mp_fuselage_10_solid_moonlit = 		((ItemCustomMissilePart) mp_fuselage_10_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("The Master & Hoboy").setTitle("Moonlit").setUnlocalizedName("mp_fuselage_10_solid_moonlit");
		mp_fuselage_10_solid_battery = 		((ItemCustomMissilePart) mp_fuselage_10_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("wolfmonster222").setHealth(30F).setTitle("Ecstatic").setWittyText("I got caught eating batteries again :(").setUnlocalizedName("mp_fuselage_10_solid_battery");
		mp_fuselage_10_solid_duracell = 	((ItemCustomMissilePart) mp_fuselage_10_solid).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Duracell").setHealth(30F).setWittyText("The crunchiest battery on the market!").setUnlocalizedName("mp_fuselage_10_solid_duracell");

		mp_fuselage_10_xenon = new ItemCustomMissilePart().makeFuselage(FuelType.XENON, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(20F)			.setUnlocalizedName("mp_fuselage_10_xenon");
		mp_fuselage_10_xenon_bhole = 	((ItemCustomMissilePart) mp_fuselage_10_xenon).copy().setRarity(Rarity.RARE).setAuthor("Sten89").setTitle("Morceus-1457").setUnlocalizedName("mp_fuselage_10_xenon_bhole");

		mp_fuselage_10_long_kerosene = new ItemCustomMissilePart().makeFuselage(FuelType.KEROSENE, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setAuthor("Hoboy").setHealth(30F).setUnlocalizedName("mp_fuselage_10_long_kerosene");
		mp_fuselage_10_long_kerosene_camo = 		((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_10_long_kerosene_camo");
		mp_fuselage_10_long_kerosene_desert = 		((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Desert Camo").setUnlocalizedName("mp_fuselage_10_long_kerosene_desert");
		mp_fuselage_10_long_kerosene_sky = 			((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_10_long_kerosene_sky");
		mp_fuselage_10_long_kerosene_flames = 		((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_long_kerosene_flames");
		mp_fuselage_10_long_kerosene_insulation = 	((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(35F).setUnlocalizedName("mp_fuselage_10_long_kerosene_insulation");
		mp_fuselage_10_long_kerosene_sleek =		((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(40F).setUnlocalizedName("mp_fuselage_10_long_kerosene_sleek");
		mp_fuselage_10_long_kerosene_metal = 		((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setHealth(35F).setUnlocalizedName("mp_fuselage_10_long_kerosene_metal");
		mp_fuselage_10_long_kerosene_dash = 		((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.EPIC).setAuthor("Sam").setTitle("Dash").setWittyText("I wash my hands of it.").setCreativeTab(null).setUnlocalizedName("mp_fuselage_10_long_kerosene_dash");
		mp_fuselage_10_long_kerosene_taint = 		((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted").setUnlocalizedName("mp_fuselage_10_long_kerosene_taint");
		mp_fuselage_10_long_kerosene_vap = 			((ItemCustomMissilePart) mp_fuselage_10_long_kerosene).copy().setRarity(Rarity.EPIC).setAuthor("VT-6/24").setTitle("Minty Contrail").setWittyText("Upper rivet!").setUnlocalizedName("mp_fuselage_10_long_kerosene_vap");

		mp_fuselage_10_long_solid = new ItemCustomMissilePart().makeFuselage(FuelType.SOLID, 5000F, PartSize.SIZE_10, PartSize.SIZE_10).setHealth(35F)	.setUnlocalizedName("mp_fuselage_10_long_solid");
		mp_fuselage_10_long_solid_flames = 			((ItemCustomMissilePart) mp_fuselage_10_long_solid).copy().setRarity(Rarity.UNCOMMON).setTitle("Sick Flames").setUnlocalizedName("mp_fuselage_10_long_solid_flames");
		mp_fuselage_10_long_solid_insulation = 		((ItemCustomMissilePart) mp_fuselage_10_long_solid).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(40F).setUnlocalizedName("mp_fuselage_10_long_solid_insulation");
		mp_fuselage_10_long_solid_sleek = 			((ItemCustomMissilePart) mp_fuselage_10_long_solid).copy().setRarity(Rarity.RARE).setTitle("IF-R&D").setHealth(45F).setUnlocalizedName("mp_fuselage_10_long_solid_sleek");
		mp_fuselage_10_long_solid_soviet_glory = 	((ItemCustomMissilePart) mp_fuselage_10_long_solid).copy().setRarity(Rarity.EPIC).setAuthor("Hoboy").setHealth(45F).setTitle("Soviet Glory").setWittyText("Fully Automated Luxury Gay Space Communism!").setUnlocalizedName("mp_fuselage_10_long_solid_soviet_glory");
		mp_fuselage_10_long_solid_bullet = 			((ItemCustomMissilePart) mp_fuselage_10_long_solid).copy().setRarity(Rarity.COMMON).setAuthor("Sam").setTitle("Bullet Bill").setUnlocalizedName("mp_fuselage_10_long_solid_bullet");
		mp_fuselage_10_long_solid_silvermoonlight = ((ItemCustomMissilePart) mp_fuselage_10_long_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("The Master").setTitle("Silver Moonlight").setUnlocalizedName("mp_fuselage_10_long_solid_silvermoonlight");

		mp_fuselage_10_15_kerosene = new ItemCustomMissilePart().makeFuselage(FuelType.KEROSENE, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F).setUnlocalizedName("mp_fuselage_10_15_kerosene");
		mp_fuselage_10_15_solid = new ItemCustomMissilePart().makeFuselage(FuelType.SOLID, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F)		.setUnlocalizedName("mp_fuselage_10_15_solid");
		mp_fuselage_10_15_hydrogen = new ItemCustomMissilePart().makeFuselage(FuelType.HYDROGEN, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F).setUnlocalizedName("mp_fuselage_10_15_hydrogen");
		mp_fuselage_10_15_balefire = new ItemCustomMissilePart().makeFuselage(FuelType.BALEFIRE, 10000F, PartSize.SIZE_10, PartSize.SIZE_15).setHealth(40F).setUnlocalizedName("mp_fuselage_10_15_balefire");

		mp_fuselage_15_kerosene = new ItemCustomMissilePart().makeFuselage(FuelType.KEROSENE, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setAuthor("Hoboy").setHealth(50F).setUnlocalizedName("mp_fuselage_15_kerosene");
		mp_fuselage_15_kerosene_camo = 			((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Camo").setUnlocalizedName("mp_fuselage_15_kerosene_camo");
		mp_fuselage_15_kerosene_desert = 		((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Desert Camo").setUnlocalizedName("mp_fuselage_15_kerosene_desert");
		mp_fuselage_15_kerosene_sky = 			((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Sky Camo").setUnlocalizedName("mp_fuselage_15_kerosene_sky");
		mp_fuselage_15_kerosene_insulation = 	((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(55F).setWittyText("Rest in spaghetti Columbia :(").setUnlocalizedName("mp_fuselage_15_kerosene_insulation");
		mp_fuselage_15_kerosene_metal = 		((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Bolted Metal").setHealth(60F).setWittyText("Metal frame with metal plating reinforced with bolted metal sheets and metal.").setUnlocalizedName("mp_fuselage_15_kerosene_metal");
		mp_fuselage_15_kerosene_decorated = 	((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Decorated").setHealth(60F).setUnlocalizedName("mp_fuselage_15_kerosene_decorated");
		mp_fuselage_15_kerosene_steampunk = 	((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Steampunk").setHealth(60F).setUnlocalizedName("mp_fuselage_15_kerosene_steampunk");
		mp_fuselage_15_kerosene_polite = 		((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.LEGENDARY).setAuthor("Hoboy").setTitle("Polite").setHealth(60F).setUnlocalizedName("mp_fuselage_15_kerosene_polite");
		mp_fuselage_15_kerosene_blackjack = 	((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.LEGENDARY).setTitle("Queen Whiskey").setHealth(100F).setUnlocalizedName("mp_fuselage_15_kerosene_blackjack");
		mp_fuselage_15_kerosene_lambda = 		((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.RARE).setAuthor("VT-6/24").setTitle("Lambda Complex").setHealth(75F).setWittyText("MAGNIFICENT MICROWAVE CASSEROLE").setUnlocalizedName("mp_fuselage_15_kerosene_lambda");
		mp_fuselage_15_kerosene_minuteman = 	((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Spexta").setTitle("MX 1702").setUnlocalizedName("mp_fuselage_15_kerosene_minuteman");
		mp_fuselage_15_kerosene_pip = 			((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.EPIC).setAuthor("The Doctor").setTitle("LittlePip").setWittyText("31!").setCreativeTab(null).setUnlocalizedName("mp_fuselage_15_kerosene_pip");
		mp_fuselage_15_kerosene_taint = 		((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.UNCOMMON).setAuthor("Sam").setTitle("Tainted").setWittyText("DUN-DUN!").setUnlocalizedName("mp_fuselage_15_kerosene_taint");
		mp_fuselage_15_kerosene_yuck = 			((ItemCustomMissilePart) mp_fuselage_15_kerosene).copy().setRarity(Rarity.EPIC).setAuthor("Hoboy").setTitle("Flesh").setWittyText("Note: Never clean DNA vials with your own spit.").setHealth(60F).setUnlocalizedName("mp_fuselage_15_kerosene_yuck");

		mp_fuselage_15_solid = new ItemCustomMissilePart().makeFuselage(FuelType.SOLID, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(60F)			.setUnlocalizedName("mp_fuselage_15_solid").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		mp_fuselage_15_solid_insulation = 		((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.COMMON).setTitle("Orange Insulation").setHealth(65F).setUnlocalizedName("mp_fuselage_15_solid_insulation");
		mp_fuselage_15_solid_desh = 			((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Desh Plating").setHealth(80F).setUnlocalizedName("mp_fuselage_15_solid_desh");
		mp_fuselage_15_solid_soviet_glory = 	((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Soviet Glory").setHealth(70F).setUnlocalizedName("mp_fuselage_15_solid_soviet_glory");
		mp_fuselage_15_solid_soviet_stank = 	((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.EPIC).setAuthor("Hoboy").setTitle("Soviet Stank").setHealth(15F).setWittyText("Aged like a fine wine! Well, almost.").setUnlocalizedName("mp_fuselage_15_solid_soviet_stank");
		mp_fuselage_15_solid_faust = 			((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.LEGENDARY).setAuthor("Dr.Nostalgia").setTitle("Mighty Lauren").setHealth(250F).setWittyText("Welcome to Subway, may I take your order?").setUnlocalizedName("mp_fuselage_15_solid_faust");
		mp_fuselage_15_solid_silvermoonlight = 	((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("The Master").setTitle("Silver Moonlight").setUnlocalizedName("mp_fuselage_15_solid_silvermoonlight");
		mp_fuselage_15_solid_snowy = 			((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("Dr.Nostalgia").setTitle("Chilly Day").setUnlocalizedName("mp_fuselage_15_solid_snowy");
		mp_fuselage_15_solid_panorama = 		((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.RARE).setAuthor("Hoboy").setTitle("Panorama").setUnlocalizedName("mp_fuselage_15_solid_panorama");
		mp_fuselage_15_solid_roses = 			((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.UNCOMMON).setAuthor("Hoboy").setTitle("Bed of roses").setUnlocalizedName("mp_fuselage_15_solid_roses");
		mp_fuselage_15_solid_mimi = 			((ItemCustomMissilePart) mp_fuselage_15_solid).copy().setRarity(Rarity.RARE).setTitle("Mimi-chan").setUnlocalizedName("mp_fuselage_15_solid_mimi");

		mp_fuselage_15_hydrogen = new ItemCustomMissilePart().makeFuselage(FuelType.HYDROGEN, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(50F)	.setUnlocalizedName("mp_fuselage_15_hydrogen").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		mp_fuselage_15_hydrogen_cathedral = ((ItemCustomMissilePart) mp_fuselage_15_hydrogen).copy().setRarity(Rarity.UNCOMMON).setAuthor("Satan").setTitle("Unholy Cathedral").setUnlocalizedName("mp_fuselage_15_hydrogen_cathedral");

		mp_fuselage_15_balefire = new ItemCustomMissilePart().makeFuselage(FuelType.BALEFIRE, 15000F, PartSize.SIZE_15, PartSize.SIZE_15).setHealth(75F)	.setUnlocalizedName("mp_fuselage_15_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");

		mp_fuselage_15_20_kerosene = new ItemCustomMissilePart().makeFuselage(FuelType.KEROSENE, 20000, PartSize.SIZE_15, PartSize.SIZE_20).setAuthor("Hoboy").setHealth(70F).setUnlocalizedName("mp_fuselage_15_20_kerosene").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");
		mp_fuselage_15_20_kerosene_magnusson = ((ItemCustomMissilePart)mp_fuselage_15_20_kerosene).copy().setRarity(Rarity.RARE).setAuthor("VT-6/24").setTitle("White Forest Rocket").setWittyText("And get your cranio-conjugal parasite away from my nose cone!").setUnlocalizedName("mp_fuselage_15_20_kerosene_magnusson");
		mp_fuselage_15_20_solid = new ItemCustomMissilePart().makeFuselage(FuelType.SOLID, 20000, PartSize.SIZE_15, PartSize.SIZE_20).setHealth(70F).setUnlocalizedName("mp_fuselage_15_20_solid").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_fuselage");

		mp_warhead_10_he = new ItemCustomMissilePart().makeWarhead(WarheadType.HE, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F)								.setUnlocalizedName("mp_warhead_10_he").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_incendiary = new ItemCustomMissilePart().makeWarhead(WarheadType.INC, 15F, 1.5F, PartSize.SIZE_10).setHealth(5F)					.setUnlocalizedName("mp_warhead_10_incendiary").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_buster = new ItemCustomMissilePart().makeWarhead(WarheadType.BUSTER, 5F, 1.5F, PartSize.SIZE_10).setHealth(5F)						.setUnlocalizedName("mp_warhead_10_buster").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_nuclear = new ItemCustomMissilePart().makeWarhead(WarheadType.NUCLEAR, 35F, 1.5F, PartSize.SIZE_10).setTitle("Tater Tot").setHealth(10F).setUnlocalizedName("mp_warhead_10_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_nuclear_large = new ItemCustomMissilePart().makeWarhead(WarheadType.NUCLEAR, 75F, 2.5F, PartSize.SIZE_10).setTitle("Chernobyl Boris").setHealth(15F).setUnlocalizedName("mp_warhead_10_nuclear_large").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_taint = new ItemCustomMissilePart().makeWarhead(WarheadType.TAINT, 15F, 1.5F, PartSize.SIZE_10).setHealth(20F).setRarity(Rarity.UNCOMMON).setWittyText("Eat my taint! Bureaucracy is dead and we killed it!").setUnlocalizedName("mp_warhead_10_taint").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_10_cloud = new ItemCustomMissilePart().makeWarhead(WarheadType.CLOUD, 15F, 1.5F, PartSize.SIZE_10).setHealth(20F).setRarity(Rarity.RARE).setUnlocalizedName("mp_warhead_10_cloud").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_he = new ItemCustomMissilePart().makeWarhead(WarheadType.HE, 50F, 2.5F, PartSize.SIZE_15).setHealth(10F)							.setUnlocalizedName("mp_warhead_15_he").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_incendiary = new ItemCustomMissilePart().makeWarhead(WarheadType.INC, 35F, 2.5F, PartSize.SIZE_15).setHealth(10F)					.setUnlocalizedName("mp_warhead_15_incendiary").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_nuclear = new ItemCustomMissilePart().makeWarhead(WarheadType.NUCLEAR, 125F, 5F, PartSize.SIZE_15).setTitle("Auntie Bertha").setHealth(15F).setUnlocalizedName("mp_warhead_15_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_nuclear_shark = ((ItemCustomMissilePart) mp_warhead_15_nuclear).copy().setRarity(Rarity.UNCOMMON).setTitle("Discount Bullet Bill").setWittyText("Nose art on a cannon bullet? Who does that?").setUnlocalizedName("mp_warhead_15_nuclear_shark");
		mp_warhead_15_nuclear_mimi = ((ItemCustomMissilePart) mp_warhead_15_nuclear).copy().setRarity(Rarity.RARE).setTitle("FASHIONABLE MISSILE").setUnlocalizedName("mp_warhead_15_nuclear_mimi");
		mp_warhead_15_boxcar = new ItemCustomMissilePart().makeWarhead(WarheadType.TX, 250F, 7.5F, PartSize.SIZE_15).setWittyText("?!?!").setHealth(35F).setRarity(Rarity.LEGENDARY).setUnlocalizedName("mp_warhead_15_boxcar").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_n2 = new ItemCustomMissilePart().makeWarhead(WarheadType.N2, 100F, 5F, PartSize.SIZE_15).setWittyText("[screams geometrically]").setHealth(20F).setRarity(Rarity.RARE).setUnlocalizedName("mp_warhead_15_n2").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_balefire = new ItemCustomMissilePart().makeWarhead(WarheadType.BALEFIRE, 100F, 7.5F, PartSize.SIZE_15).setRarity(Rarity.LEGENDARY).setAuthor("VT-6/24").setHealth(15F).setWittyText("Hightower, never forgetti.").setUnlocalizedName("mp_warhead_15_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");
		mp_warhead_15_turbine = new ItemCustomMissilePart().makeWarhead(WarheadType.TURBINE, 200F, 5F, PartSize.SIZE_15).setRarity(Rarity.SEWS_CLOTHES_AND_SUCKS_HORSE_COCK).setHealth(250F).setUnlocalizedName("mp_warhead_15_turbine").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_warhead");

		mp_chip_1 = new ItemCustomMissilePart().makeChip(0.1F)	.setUnlocalizedName("mp_c_1").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_1");
		mp_chip_2 = new ItemCustomMissilePart().makeChip(0.05F)	.setUnlocalizedName("mp_c_2").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_2");
		mp_chip_3 = new ItemCustomMissilePart().makeChip(0.01F)	.setUnlocalizedName("mp_c_3").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_3");
		mp_chip_4 = new ItemCustomMissilePart().makeChip(0.005F)	.setUnlocalizedName("mp_c_4").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_4");
		mp_chip_5 = new ItemCustomMissilePart().makeChip(0.0F)	.setUnlocalizedName("mp_c_5").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":mp_c_5");

		ammo_shell = (ItemEnumMulti) new ItemAmmo(Ammo240Shell.class).setCreativeTab(MainRegistry.weaponTab).setUnlocalizedName("ammo_shell");
		ammo_dgk = new ItemCustomLore().setUnlocalizedName("ammo_dgk").setCreativeTab(MainRegistry.weaponTab);
		ammo_fireext = (ItemEnumMulti) new ItemAmmo(AmmoFireExt.class).setCreativeTab(MainRegistry.weaponTab).setUnlocalizedName("ammo_fireext");
		ammo_misc = new ItemAmmo(AmmoMisc.class).setUnlocalizedName("ammo_misc");
		ammo_arty = new ItemAmmoArty().setUnlocalizedName("ammo_arty");
		ammo_himars = new ItemAmmoHIMARS().setUnlocalizedName("ammo_himars");

		gun_b92_ammo = new GunB92Cell().setUnlocalizedName("gun_b92_ammo").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92_ammo_alt");
		gun_b92 = new GunB92().setUnlocalizedName("gun_b92").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":gun_b92");

		ToolMaterial matCrucible = EnumHelper.addToolMaterial("CRUCIBLE", 10, 3, 50.0F, 100.0F, 0);
		crucible = new ItemCrucible(5000, 1F, matCrucible).setUnlocalizedName("crucible").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":crucible");

		stick_dynamite = new ItemGrenade(3).setUnlocalizedName("stick_dynamite").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":stick_dynamite");
		stick_dynamite_fishing = new ItemGrenadeFishing(3).setUnlocalizedName("stick_dynamite_fishing").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":stick_dynamite_fishing");
		stick_tnt = new Item().setUnlocalizedName("stick_tnt").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":stick_tnt");
		stick_semtex = new Item().setUnlocalizedName("stick_semtex").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":stick_semtex");
		stick_c4 = new Item().setUnlocalizedName("stick_c4").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":stick_c4");

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
		grenade_kyiv = new ItemGrenadeKyiv(-1).setUnlocalizedName("grenade_kyiv").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":grenade_kyiv");
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
		lemon = new ItemLemon(3, 0.5F, false).setUnlocalizedName("lemon").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":lemon");
		definitelyfood = new ItemLemon(3, 0.5F, false).setUnlocalizedName("definitelyfood").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":definitelyfood");
		med_ipecac = new ItemLemon(0, 0, false).setUnlocalizedName("med_ipecac").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_ipecac_new");
		med_ptsd = new ItemLemon(0, 0, false).setUnlocalizedName("med_ptsd").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_ptsd_new");
		med_schizophrenia = new ItemLemon(0, 0, false).setUnlocalizedName("med_schizophrenia").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":med_schizophrenia_new");
		loops = new ItemLemon(4, 0.25F, false).setUnlocalizedName("loops").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":loops");
		loop_stew = new ItemLemon(10, 0.5F, false).setUnlocalizedName("loop_stew").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":loop_stew");
		spongebob_macaroni = new ItemLemon(5, 1F, false).setUnlocalizedName("spongebob_macaroni").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spongebob_macaroni");
		fooditem = new ItemLemon(2, 5F, false).setUnlocalizedName("fooditem").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":fooditem");
		twinkie = new ItemLemon(3, 0.25F, false).setUnlocalizedName("twinkie").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":twinkie");
		static_sandwich = new ItemLemon(6, 1F, false).setUnlocalizedName("static_sandwich").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":static_sandwich");
		pudding = new ItemLemon(6, 1F, false).setUnlocalizedName("pudding").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pudding");
		canteen_vodka = new ItemCanteen(3 * 60).setUnlocalizedName("canteen_vodka").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":canteen_vodka");
		pancake = new ItemPancake(20, 20, false).setUnlocalizedName("pancake").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pancake");
		nugget = new ItemLemon(200, 1F, false).setUnlocalizedName("nugget").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":nugget");
		peas = new ItemPeas().setUnlocalizedName("peas").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":peas");
		marshmallow = new ItemMarshmallow().setUnlocalizedName("marshmallow").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":marshmallow");
		cheese = new ItemLemon(5, 0.75F, false).setUnlocalizedName("cheese").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":cheese");
		quesadilla = new ItemLemon(8, 1F, false).setUnlocalizedName("cheese_quesadilla").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":quesadilla");
		mucho_mango = new ItemMuchoMango(10).setUnlocalizedName("mucho_mango").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":mucho_mango");
		glyphid_meat = new ItemLemon(3, 0.5F, true).setUnlocalizedName("glyphid_meat").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glyphid_meat");
		glyphid_meat_grilled = new ItemLemon(8, 0.75F, true).setPotionEffect(Potion.damageBoost.id, 180, 1, 1F).setUnlocalizedName("glyphid_meat_grilled").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glyphid_meat_grilled");
		egg_glyphid = new Item().setUnlocalizedName("egg_glyphid").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":egg_glyphid");

		defuser = new ItemDefuser(ToolType.DEFUSER, 100).setUnlocalizedName("defuser").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":defuser");
		reacher = new Item().setUnlocalizedName("reacher").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":reacher");
		bismuth_tool = new ItemAmatExtractor().setUnlocalizedName("bismuth_tool").setMaxStackSize(1).setFull3D().setCreativeTab(null).setTextureName(RefStrings.MODID + ":bismuth_tool");
		meltdown_tool = new ItemDyatlov().setUnlocalizedName("meltdown_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":meltdown_tool");

		flame_pony = new ItemCustomLore().setUnlocalizedName("flame_pony").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_pony");
		flame_conspiracy = new ItemCustomLore().setUnlocalizedName("flame_conspiracy").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_conspiracy");
		flame_politics = new ItemCustomLore().setUnlocalizedName("flame_politics").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_politics");
		flame_opinion = new ItemCustomLore().setUnlocalizedName("flame_opinion").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":flame_opinion");

		//gadget_explosive = new Item().setUnlocalizedName("gadget_explosive").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_explosive");
		early_explosive_lenses = new ItemCustomLore().setUnlocalizedName("early_explosive_lenses").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_explosive8");
		explosive_lenses = new ItemCustomLore().setUnlocalizedName("explosive_lenses").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_explosive8");
		gadget_wireing = new Item().setUnlocalizedName("gadget_wireing").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_wireing");
		gadget_core = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("gadget_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":gadget_core");

		boy_igniter = new Item().setUnlocalizedName("boy_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_igniter");
		boy_propellant = new Item().setUnlocalizedName("boy_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_propellant");
		boy_bullet = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("boy_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_bullet");
		boy_target = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("boy_target").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_target");
		boy_shielding = new Item().setUnlocalizedName("boy_shielding").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":boy_shielding");

		//man_explosive = new Item().setUnlocalizedName("man_explosive").setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_explosive");
		man_igniter = new Item().setUnlocalizedName("man_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_igniter");
		man_core = new ItemCustomLore().setRarity(EnumRarity.uncommon).setUnlocalizedName("man_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":man_core");

		mike_core = new Item().setUnlocalizedName("mike_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_core");
		mike_deut = new Item().setUnlocalizedName("mike_deut").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setContainerItem(ModItems.tank_steel).setTextureName(RefStrings.MODID + ":mike_deut");
		mike_cooling_unit = new Item().setUnlocalizedName("mike_cooling_unit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":mike_cooling_unit");

		tsar_core = new Item().setUnlocalizedName("tsar_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":tsar_core");

		fleija_igniter = new ItemFleija().setUnlocalizedName("fleija_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_igniter");
		fleija_propellant = new ItemFleija().setUnlocalizedName("fleija_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_propellant");
		fleija_core = new ItemFleija().setUnlocalizedName("fleija_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":fleija_core");

		solinium_igniter = new ItemSolinium().setUnlocalizedName("solinium_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_igniter");
		solinium_propellant = new ItemSolinium().setUnlocalizedName("solinium_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_propellant");
		solinium_core = new ItemSolinium().setUnlocalizedName("solinium_core").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_core");

		n2_charge = new ItemN2().setUnlocalizedName("n2_charge").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":n2_charge");

		egg_balefire_shard = new Item().setUnlocalizedName("egg_balefire_shard").setMaxStackSize(16).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":egg_balefire_shard");
		egg_balefire = new Item().setUnlocalizedName("egg_balefire").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":egg_balefire");

		custom_tnt = new ItemCustomLore().setUnlocalizedName("custom_tnt").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_tnt");
		custom_nuke = new ItemCustomLore().setUnlocalizedName("custom_nuke").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_nuke");
		custom_hydro = new ItemCustomLore().setUnlocalizedName("custom_hydro").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_hydro");
		custom_amat = new ItemCustomLore().setUnlocalizedName("custom_amat").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_amat");
		custom_dirty = new ItemCustomLore().setUnlocalizedName("custom_dirty").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_dirty");
		custom_schrab = new ItemCustomLore().setUnlocalizedName("custom_schrab").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_schrab");
		custom_fall = new ItemCustomLore().setUnlocalizedName("custom_fall").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":custom_fall");

		battery_spark = new Item().setUnlocalizedName("battery_spark").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":battery_spark");
		battery_trixite = new Item().setUnlocalizedName("battery_trixite").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":battery_trixite");
		
		battery_pack = new ItemBatteryPack().setUnlocalizedName("battery_pack").setTextureName(RefStrings.MODID + ":battery_creative_new");
		battery_creative = new ItemBatteryCreative().setUnlocalizedName("battery_creative").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_creative_new");
		cube_power = new ItemBattery(1000000000000000000L, 1000000000000000L, 1000000000000000L).setUnlocalizedName("cube_power").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":cube_power");

		battery_sc = new ItemBatterySC().setUnlocalizedName("battery_sc").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_sc");

		battery_potato = new ItemBattery(1000, 0, 100).setUnlocalizedName("battery_potato").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_potato");
		battery_potatos = new ItemPotatos(500000, 0, 100).setUnlocalizedName("battery_potatos").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":battery_potatos");
		hev_battery = new ItemFusionCore(150000).setUnlocalizedName("hev_battery").setMaxStackSize(4).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":hev_battery");
		fusion_core = new ItemFusionCore(2500000).setUnlocalizedName("fusion_core").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_core");
		energy_core = new ItemBattery(10000000, 0, 1000).setUnlocalizedName("energy_core").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":energy_core");
		fuse = new ItemCustomLore().setUnlocalizedName("fuse").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fuse");
		euphemium_capacitor = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("euphemium_capacitor").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":redcoil_capacitor_euphemium");
		screwdriver = new ItemTooling(ToolType.SCREWDRIVER, 100).setUnlocalizedName("screwdriver");
		screwdriver_desh = new ItemTooling(ToolType.SCREWDRIVER, 0).setUnlocalizedName("screwdriver_desh");
		hand_drill = new ItemTooling(ToolType.HAND_DRILL, 100).setUnlocalizedName("hand_drill");
		hand_drill_desh = new ItemTooling(ToolType.HAND_DRILL, 0).setUnlocalizedName("hand_drill_desh");
		wrench_archineer = new ItemToolingWeapon(ToolType.WRENCH, 1000, 12F).setUnlocalizedName("wrench_archineer").setTextureName(RefStrings.MODID + ":wrench_archineer_hd");
		chemistry_set = new ItemCraftingDegradation(100).setUnlocalizedName("chemistry_set");
		chemistry_set_boron = new ItemCraftingDegradation(0).setUnlocalizedName("chemistry_set_boron");
		blowtorch = new ItemBlowtorch().setUnlocalizedName("blowtorch");
		acetylene_torch = new ItemBlowtorch().setUnlocalizedName("acetylene_torch");
		boltgun = new ItemBoltgun().setUnlocalizedName("boltgun");
		arc_electrode = new ItemArcElectrode().setUnlocalizedName("arc_electrode").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":arc_electrode");
		arc_electrode_burnt = new ItemArcElectrodeBurnt().setUnlocalizedName("arc_electrode_burnt").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":arc_electrode_burnt");

		ams_lens = new ItemLens(60 * 60 * 60 * 20 * 100).setUnlocalizedName("ams_lens").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_lens");
		ams_core_sing = new ItemAMSCore(1000000000L, 200, 10).setUnlocalizedName("ams_core_sing").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_sing");
		ams_core_wormhole = new ItemAMSCore(1500000000L, 200, 15).setUnlocalizedName("ams_core_wormhole").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_wormhole");
		ams_core_eyeofharmony = new ItemAMSCore(2500000000L, 300, 10).setUnlocalizedName("ams_core_eyeofharmony").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":ams_core_eyeofharmony");
		ams_core_thingy = new ItemAMSCore(5000000000L, 250, 5).setUnlocalizedName("ams_core_thingy").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":ams_core_thingy");

		fusion_shield_tungsten = new ItemFusionShield(60 * 60 * 60 * 5, 3500).setUnlocalizedName("fusion_shield_tungsten").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_tungsten");
		fusion_shield_desh = new ItemFusionShield(60 * 60 * 60 * 10, 4500).setUnlocalizedName("fusion_shield_desh").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_desh");
		fusion_shield_chlorophyte = new ItemFusionShield(60 * 60 * 60 * 15, 9000).setUnlocalizedName("fusion_shield_chlorophyte").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_chlorophyte");
		fusion_shield_vaporwave = new ItemFusionShield(60 * 60 * 60 * 10, 1916169).setUnlocalizedName("fusion_shield_vaporwave").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fusion_shield_vaporwave");

		upgrade_muffler = new ItemMuffler().setUnlocalizedName("upgrade_muffler").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":upgrade_muffler");

		upgrade_template = new ItemCustomLore().setUnlocalizedName("upgrade_template").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":upgrade_template");
		upgrade_speed_1 = new ItemMachineUpgrade(UpgradeType.SPEED, 1).setUnlocalizedName("upgrade_speed_1").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_1");
		upgrade_speed_2 = new ItemMachineUpgrade(UpgradeType.SPEED, 2).setUnlocalizedName("upgrade_speed_2").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_2");
		upgrade_speed_3 = new ItemMachineUpgrade(UpgradeType.SPEED, 3).setUnlocalizedName("upgrade_speed_3").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_speed_3");
		upgrade_effect_1 = new ItemMachineUpgrade(UpgradeType.EFFECT, 1).setUnlocalizedName("upgrade_effect_1").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_1");
		upgrade_effect_2 = new ItemMachineUpgrade(UpgradeType.EFFECT, 2).setUnlocalizedName("upgrade_effect_2").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_2");
		upgrade_effect_3 = new ItemMachineUpgrade(UpgradeType.EFFECT, 3).setUnlocalizedName("upgrade_effect_3").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_effect_3");
		upgrade_power_1 = new ItemMachineUpgrade(UpgradeType.POWER, 1).setUnlocalizedName("upgrade_power_1").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_1");
		upgrade_power_2 = new ItemMachineUpgrade(UpgradeType.POWER, 2).setUnlocalizedName("upgrade_power_2").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_2");
		upgrade_power_3 = new ItemMachineUpgrade(UpgradeType.POWER, 3).setUnlocalizedName("upgrade_power_3").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_power_3");
		upgrade_fortune_1 = new ItemMachineUpgrade(UpgradeType.FORTUNE, 1).setUnlocalizedName("upgrade_fortune_1").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_1");
		upgrade_fortune_2 = new ItemMachineUpgrade(UpgradeType.FORTUNE, 2).setUnlocalizedName("upgrade_fortune_2").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_2");
		upgrade_fortune_3 = new ItemMachineUpgrade(UpgradeType.FORTUNE, 3).setUnlocalizedName("upgrade_fortune_3").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_fortune_3");
		upgrade_afterburn_1 = new ItemMachineUpgrade(UpgradeType.AFTERBURN, 1).setUnlocalizedName("upgrade_afterburn_1").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_1");
		upgrade_afterburn_2 = new ItemMachineUpgrade(UpgradeType.AFTERBURN, 2).setUnlocalizedName("upgrade_afterburn_2").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_2");
		upgrade_afterburn_3 = new ItemMachineUpgrade(UpgradeType.AFTERBURN, 3).setUnlocalizedName("upgrade_afterburn_3").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_afterburn_3");
		upgrade_overdrive_1 = new ItemMachineUpgrade(UpgradeType.OVERDRIVE, 1).setUnlocalizedName("upgrade_overdrive_1").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_overdrive_1");
		upgrade_overdrive_2 = new ItemMachineUpgrade(UpgradeType.OVERDRIVE, 2).setUnlocalizedName("upgrade_overdrive_2").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_overdrive_2");
		upgrade_overdrive_3 = new ItemMachineUpgrade(UpgradeType.OVERDRIVE, 3).setUnlocalizedName("upgrade_overdrive_3").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_overdrive_3");
		upgrade_radius = new ItemMachineUpgrade().setUnlocalizedName("upgrade_radius").setMaxStackSize(16).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_radius");
		upgrade_health = new ItemMachineUpgrade().setUnlocalizedName("upgrade_health").setMaxStackSize(16).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_health");
		upgrade_smelter = new ItemMachineUpgrade().setUnlocalizedName("upgrade_smelter").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_smelter");
		upgrade_shredder = new ItemMachineUpgrade().setUnlocalizedName("upgrade_shredder").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_shredder");
		upgrade_centrifuge = new ItemMachineUpgrade().setUnlocalizedName("upgrade_centrifuge").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_centrifuge");
		upgrade_crystallizer = new ItemMachineUpgrade().setUnlocalizedName("upgrade_crystallizer").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_crystallizer");
		upgrade_nullifier = new ItemMachineUpgrade().setUnlocalizedName("upgrade_nullifier").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_nullifier");
		upgrade_screm = new ItemMachineUpgrade().setUnlocalizedName("upgrade_screm").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_screm");
		upgrade_gc_speed = new ItemMachineUpgrade().setUnlocalizedName("upgrade_gc_speed").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_gc_speed");
		upgrade_5g = new ItemMachineUpgrade().setUnlocalizedName("upgrade_5g").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_5g");
		upgrade_stack = new ItemMetaUpgrade(3).setUnlocalizedName("upgrade_stack").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_stack");
		upgrade_ejector = new ItemMetaUpgrade(3).setUnlocalizedName("upgrade_ejector").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":upgrade_ejector");

		rebar_placer = new ItemRebarPlacer().setUnlocalizedName("rebar_placer").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":rebar_placer");
		wand = new ItemWand().setUnlocalizedName("wand_k").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand");
		wand_s = new ItemWandS().setUnlocalizedName("wand_s").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_s");
		wand_d = new ItemWandD().setUnlocalizedName("wand_d").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_d");

		structure_single = new ItemStructureSingle().setUnlocalizedName("structure_single").setMaxStackSize(1).setCreativeTab(null).setFull3D().setTextureName(RefStrings.MODID + ":structure_single");
		structure_solid = new ItemStructureSolid().setUnlocalizedName("structure_solid").setMaxStackSize(1).setCreativeTab(null).setFull3D().setTextureName(RefStrings.MODID + ":structure_solid");
		structure_pattern = new ItemStructurePattern().setUnlocalizedName("structure_pattern").setMaxStackSize(1).setCreativeTab(null).setFull3D().setTextureName(RefStrings.MODID + ":structure_pattern");
		structure_randomized = new ItemStructureRandomized().setUnlocalizedName("structure_randomized").setMaxStackSize(1).setCreativeTab(null).setFull3D().setTextureName(RefStrings.MODID + ":structure_randomized");
		structure_randomly = new ItemStructureRandomly().setUnlocalizedName("structure_randomly").setMaxStackSize(1).setCreativeTab(null).setFull3D().setTextureName(RefStrings.MODID + ":structure_randomly");
		structure_custommachine = new ItemCMStructure().setUnlocalizedName("structure_custommachine").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setFull3D().setTextureName(RefStrings.MODID + ":structure_custommachine");

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
		euphemium_kit = new ItemStarterKit().setUnlocalizedName("euphemium_kit").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":code");
		solinium_kit = new ItemStarterKit().setUnlocalizedName("solinium_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":solinium_kit");
		hazmat_kit = new ItemStarterKit().setUnlocalizedName("hazmat_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_kit");
		hazmat_red_kit = new ItemStarterKit().setUnlocalizedName("hazmat_red_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_red_kit");
		hazmat_grey_kit = new ItemStarterKit().setUnlocalizedName("hazmat_grey_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":hazmat_grey_kit");
		kit_custom = new ItemKitCustom().setUnlocalizedName("kit_custom").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":kit");
		toolbox = new ItemToolBox().setUnlocalizedName("toolbox").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":kit_toolbox");
		legacy_toolbox = new ItemKitNBT().setUnlocalizedName("toolbox_legacy").setContainerItem(toolbox).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":kit_toolbox");

		loot_10 = new ItemLootCrate().setUnlocalizedName("loot_10").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_10");
		loot_15 = new ItemLootCrate().setUnlocalizedName("loot_15").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_15");
		loot_misc = new ItemLootCrate().setUnlocalizedName("loot_misc").setMaxStackSize(1).setCreativeTab(MainRegistry.missileTab).setTextureName(RefStrings.MODID + ":loot_misc");

		ammo_container = new ItemAmmoContainer().setUnlocalizedName("ammo_container").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":ammo_container");

		ingot_euphemium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("ingot_euphemium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":ingot_euphemium");
		nugget_euphemium = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("nugget_euphemium").setCreativeTab(MainRegistry.partsTab).setTextureName(RefStrings.MODID + ":nugget_euphemium");
		watch = new ItemCustomLore().setRarity(EnumRarity.epic).setUnlocalizedName("watch").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":watch");
		apple_euphemium = new ItemAppleEuphemium(20, 100, false).setUnlocalizedName("apple_euphemium").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":apple_euphemium");

		igniter = new ItemCustomLore().setUnlocalizedName("igniter").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":trigger");
		detonator = new ItemDetonator().setUnlocalizedName("detonator").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator");
		detonator_multi = new ItemMultiDetonator().setUnlocalizedName("detonator_multi").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_multi");
		detonator_laser = new ItemLaserDetonator().setUnlocalizedName("detonator_laser").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_laser");
		detonator_deadman = new ItemDrop().setUnlocalizedName("detonator_deadman").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_deadman");
		detonator_de = new ItemDrop().setUnlocalizedName("detonator_de").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.nukeTab).setTextureName(RefStrings.MODID + ":detonator_de");
		bomb_caller = new ItemBombCaller().setUnlocalizedName("bomb_caller").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bomb_caller");
		meteor_remote = new ItemMeteorRemote().setUnlocalizedName("meteor_remote").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":meteor_remote");
		anchor_remote = new ItemAnchorRemote().setUnlocalizedName("anchor_remote").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":anchor_remote");
		spawn_chopper = new ItemChopper().setUnlocalizedName("chopper").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":chopper");
		spawn_worm = new ItemChopper().setUnlocalizedName("spawn_worm").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spawn_worm");
		spawn_ufo = new ItemChopper().setUnlocalizedName("spawn_ufo").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spawn_ufo");
		spawn_duck = new ItemChopper().setUnlocalizedName("spawn_duck").setMaxStackSize(16).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":spawn_duck");
		linker = new ItemTeleLink().setUnlocalizedName("linker").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":linker");
		reactor_sensor = new ItemReactorSensor().setUnlocalizedName("reactor_sensor").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":reactor_sensor");
		oil_detector = new ItemOilDetector().setUnlocalizedName("oil_detector").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":oil_detector");
		turret_chip = new ItemTurretChip().setUnlocalizedName("turret_chip").setMaxStackSize(1).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":turret_chip");
		dosimeter = new ItemDosimeter().setUnlocalizedName("dosimeter").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":dosimeter");
		geiger_counter = new ItemGeigerCounter().setUnlocalizedName("geiger_counter").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":geiger_counter");
		digamma_diagnostic = new ItemDigammaDiagnostic().setUnlocalizedName("digamma_diagnostic").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":digamma_diagnostic");
		pollution_detector = new ItemPollutionDetector().setUnlocalizedName("pollution_detector").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pollution_detector");
		ore_density_scanner = new ItemOreDensityScanner().setUnlocalizedName("ore_density_scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":ore_density_scanner");
		survey_scanner = new ItemSurveyScanner().setUnlocalizedName("survey_scanner").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":survey_scanner");
		mirror_tool = new ItemMirrorTool().setUnlocalizedName("mirror_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":mirror_tool");
		rbmk_tool = new ItemRBMKTool().setUnlocalizedName("rbmk_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":rbmk_tool");
		coltan_tool = new ItemColtanCompass().setUnlocalizedName("coltan_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coltass");
		power_net_tool = new ItemPowerNetTool().setUnlocalizedName("power_net_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":power_net_tool");
		analysis_tool = new ItemAnalysisTool().setUnlocalizedName("analysis_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":analysis_tool");
		coupling_tool = new ItemCouplingTool().setUnlocalizedName("coupling_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":coupling_tool");
		drone_linker = new ItemDroneLinker().setUnlocalizedName("drone_linker").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":drone_linker");
		radar_linker = new ItemRadarLinker().setUnlocalizedName("radar_linker").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":radar_linker");
		settings_tool = new ItemSettingsTool().setUnlocalizedName("settings_tool").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":settings_tool");
		rtty_pager = new ItemRTTYPager().setUnlocalizedName("rtty_pager").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":rtty_pager");

		key = new ItemKey().setUnlocalizedName("key").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":key");
		key_red = new ItemCustomLore().setUnlocalizedName("key_red").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":key_red");
		key_red_cracked = new ItemCustomLore().setUnlocalizedName("key_red_cracked").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":key_red_cracked");
		key_fake = new ItemKey().setUnlocalizedName("key_fake").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":key_gold");
		pin = new ItemCustomLore().setUnlocalizedName("pin").setMaxStackSize(8).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":pin");
		padlock_rusty = new ItemLock(1).setUnlocalizedName("padlock_rusty").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock_rusty");
		padlock = new ItemLock(0.1).setUnlocalizedName("padlock").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock");
		padlock_reinforced = new ItemLock(0.02).setUnlocalizedName("padlock_reinforced").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":padlock_reinforced");
		padlock_unbreakable = new ItemLock(0).setUnlocalizedName("padlock_unbreakable").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":padlock_unbreakable");

		mech_key = new ItemCustomLore().setUnlocalizedName("mech_key").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":mech_key");

		blueprints = new ItemBlueprints().setUnlocalizedName("blueprints").setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":blueprints");
		blueprint_folder = new ItemBlueprintFolder().setUnlocalizedName("blueprint_folder").setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":blueprint_folder");
		template_folder = new Item().setUnlocalizedName("template_folder").setTextureName(RefStrings.MODID + ":template_folder");
		fluid_identifier_multi = new ItemFluidIDMulti().setUnlocalizedName("fluid_identifier_multi").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":fluid_identifier_multi");
		fluid_icon = new ItemFluidIcon().setUnlocalizedName("fluid_icon").setCreativeTab(null).setTextureName(RefStrings.MODID + ":fluid_icon");
		fluid_tank_empty = new Item().setUnlocalizedName("fluid_tank_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_tank");
		fluid_tank_full = new ItemFluidTank().setUnlocalizedName("fluid_tank_full").setContainerItem(ModItems.fluid_tank_empty).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_tank");
		fluid_tank_lead_empty = new Item().setUnlocalizedName("fluid_tank_lead_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_tank_lead");
		fluid_tank_lead_full = new ItemFluidTank().setUnlocalizedName("fluid_tank_lead_full").setContainerItem(ModItems.fluid_tank_lead_empty).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_tank_lead");
		fluid_barrel_empty = new Item().setUnlocalizedName("fluid_barrel_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_barrel");
		fluid_barrel_full = new ItemFluidTank().setUnlocalizedName("fluid_barrel_full").setContainerItem(ModItems.fluid_barrel_empty).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_barrel");
		fluid_barrel_infinite = new ItemInfiniteFluid(null, 1_000_000_000).setUnlocalizedName("fluid_barrel_infinite").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_barrel_infinite");
		fluid_pack_empty = new Item().setUnlocalizedName("fluid_pack_empty").setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_pack");
		fluid_pack_full = new ItemFluidTank().setUnlocalizedName("fluid_pack_full").setContainerItem(ModItems.fluid_pack_empty).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":fluid_pack");
		pipette = new ItemPipette().setUnlocalizedName("pipette").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pipette");
		pipette_boron = new ItemPipette().setUnlocalizedName("pipette_boron").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pipette_boron");
		pipette_laboratory = new ItemPipette().setUnlocalizedName("pipette_laboratory").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":pipette_laboratory");
		siphon = new ItemFluidSiphon().setUnlocalizedName("siphon").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":siphon");
		inf_water = new ItemInfiniteFluid(Fluids.WATER, 50).setUnlocalizedName("inf_water").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":inf_water");
		inf_water_mk2 = new ItemInfiniteFluid(Fluids.WATER, 500).setUnlocalizedName("inf_water_mk2").setMaxStackSize(1).setCreativeTab(MainRegistry.controlTab).setTextureName(RefStrings.MODID + ":inf_water_mk2");

		FluidTank.noDualUnload.add(fluid_barrel_infinite);
		FluidTank.noDualUnload.add(inf_water);
		FluidTank.noDualUnload.add(inf_water_mk2);

		disperser_canister_empty = new Item().setUnlocalizedName("disperser_canister_empty").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":disperser_canister");
		disperser_canister = new ItemDisperser().setUnlocalizedName("disperser_canister").setContainerItem(ModItems.disperser_canister_empty).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":disperser_canister");

		glyphid_gland_empty = new Item().setUnlocalizedName("glyphid_gland_empty").setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":glyphid_gland");
		glyphid_gland = new ItemDisperser().setUnlocalizedName("glyphid_gland").setContainerItem(ModItems.glyphid_gland_empty).setCreativeTab(MainRegistry.weaponTab).setTextureName(RefStrings.MODID + ":glyphid_gland");

		siren_track = new ItemCassette().setUnlocalizedName("siren_track").setMaxStackSize(1).setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":cassette");
		fluid_duct = new ItemFluidDuct().setUnlocalizedName("fluid_duct").setCreativeTab(MainRegistry.templateTab).setTextureName(RefStrings.MODID + ":duct");

		bobmazon = new ItemCatalog().setUnlocalizedName("bobmazon").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":bobmazon");
		bobmazon_hidden = new ItemCatalog().setUnlocalizedName("bobmazon_hidden").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":bobmazon_special");

		euphemium_helmet = new ArmorEuphemium(MainRegistry.aMatEuph, 0).setUnlocalizedName("euphemium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_helmet");
		euphemium_plate = new ArmorEuphemium(MainRegistry.aMatEuph, 1).setUnlocalizedName("euphemium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_plate");
		euphemium_legs = new ArmorEuphemium(MainRegistry.aMatEuph, 2).setUnlocalizedName("euphemium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_legs");
		euphemium_boots = new ArmorEuphemium(MainRegistry.aMatEuph, 3).setUnlocalizedName("euphemium_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_boots");

		ArmorMaterial aMatRags = EnumHelper.addArmorMaterial("HBM_RAGS", 150, new int[] { 1, 1, 1, 1 }, 0);
		aMatRags.customCraftingMaterial = ModItems.rag;

		goggles = new ArmorModel(ArmorMaterial.IRON, 0).setUnlocalizedName("goggles").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":goggles");
		ashglasses = new ArmorAshGlasses(ArmorMaterial.IRON, 0).setUnlocalizedName("ashglasses").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":ashglasses");
		gas_mask = new ArmorGasMask().setUnlocalizedName("gas_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask");
		gas_mask_m65 = new ArmorGasMask().setUnlocalizedName("gas_mask_m65").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask_m65");
		gas_mask_mono = new ArmorGasMask().setUnlocalizedName("gas_mask_mono").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask_mono");
		gas_mask_olde = new ArmorGasMask().setUnlocalizedName("gas_mask_olde").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask_olde");
		mask_rag = new ModArmor(aMatRags, 0).setUnlocalizedName("mask_rag").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_rag");
		mask_piss = new ModArmor(aMatRags, 0).setUnlocalizedName("mask_piss").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_piss");
		hat = new ArmorHat(MainRegistry.aMatAlloy, 0).setUnlocalizedName("nossy_hat").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hat");
		no9 = new ArmorNo9(MainRegistry.aMatSteel, 0).setUnlocalizedName("no9").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":no9");
		beta = new ItemDrop().setUnlocalizedName("beta").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":beta");
		//oxy_mask = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("oxy_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":oxy_mask");

		schrabidium_helmet = new ArmorFSB(MainRegistry.aMatSchrab, 0, RefStrings.MODID + ":textures/armor/schrabidium_1.png")
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 2))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.setUnlocalizedName("schrabidium_helmet").setTextureName(RefStrings.MODID + ":schrabidium_helmet");
		schrabidium_plate = new ArmorFSB(MainRegistry.aMatSchrab, 1, RefStrings.MODID + ":textures/armor/schrabidium_1.png").cloneStats((ArmorFSB) schrabidium_helmet).setUnlocalizedName("schrabidium_plate").setTextureName(RefStrings.MODID + ":schrabidium_plate");
		schrabidium_legs = new ArmorFSB(MainRegistry.aMatSchrab, 2, RefStrings.MODID + ":textures/armor/schrabidium_2.png").cloneStats((ArmorFSB) schrabidium_helmet).setUnlocalizedName("schrabidium_legs").setTextureName(RefStrings.MODID + ":schrabidium_legs");
		schrabidium_boots = new ArmorFSB(MainRegistry.aMatSchrab, 3, RefStrings.MODID + ":textures/armor/schrabidium_1.png").cloneStats((ArmorFSB) schrabidium_helmet).setUnlocalizedName("schrabidium_boots").setTextureName(RefStrings.MODID + ":schrabidium_boots");
		bismuth_helmet = new ArmorBismuth(MainRegistry.aMatBismuth, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.addEffect(new PotionEffect(Potion.jump.id, 20, 6))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 6))
				.addEffect(new PotionEffect(Potion.regeneration.id, 20, 1))
				.addEffect(new PotionEffect(Potion.nightVision.id, 15 * 20, 0))
				.setDashCount(3)
				.setUnlocalizedName("bismuth_helmet").setTextureName(RefStrings.MODID + ":bismuth_helmet");
		bismuth_plate = new ArmorBismuth(MainRegistry.aMatBismuth, 1, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) bismuth_helmet).setUnlocalizedName("bismuth_plate").setTextureName(RefStrings.MODID + ":bismuth_plate");
		bismuth_legs = new ArmorBismuth(MainRegistry.aMatBismuth, 2, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) bismuth_helmet).setUnlocalizedName("bismuth_legs").setTextureName(RefStrings.MODID + ":bismuth_legs");
		bismuth_boots = new ArmorBismuth(MainRegistry.aMatBismuth, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) bismuth_helmet).setUnlocalizedName("bismuth_boots").setTextureName(RefStrings.MODID + ":bismuth_boots");
		titanium_helmet = new ArmorFSB(MainRegistry.aMatTitan, 0, RefStrings.MODID + ":textures/armor/titanium_1.png").setUnlocalizedName("titanium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_helmet");
		titanium_plate = new ArmorFSB(MainRegistry.aMatTitan, 1, RefStrings.MODID + ":textures/armor/titanium_1.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_plate");
		titanium_legs = new ArmorFSB(MainRegistry.aMatTitan, 2, RefStrings.MODID + ":textures/armor/titanium_2.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":titanium_legs");
		titanium_boots = new ArmorFSB(MainRegistry.aMatTitan, 3, RefStrings.MODID + ":textures/armor/titanium_1.png").cloneStats((ArmorFSB) titanium_helmet).setUnlocalizedName("titanium_boots").setTextureName(RefStrings.MODID + ":titanium_boots");
		steel_helmet = new ArmorFSB(MainRegistry.aMatSteel, 0, RefStrings.MODID + ":textures/armor/steel_1.png").setUnlocalizedName("steel_helmet").setTextureName(RefStrings.MODID + ":steel_helmet");
		steel_plate = new ArmorFSB(MainRegistry.aMatSteel, 1, RefStrings.MODID + ":textures/armor/steel_1.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_plate").setTextureName(RefStrings.MODID + ":steel_plate");
		steel_legs = new ArmorFSB(MainRegistry.aMatSteel, 2, RefStrings.MODID + ":textures/armor/steel_2.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_legs").setTextureName(RefStrings.MODID + ":steel_legs");
		steel_boots = new ArmorFSB(MainRegistry.aMatSteel, 3, RefStrings.MODID + ":textures/armor/steel_1.png").cloneStats((ArmorFSB) steel_helmet).setUnlocalizedName("steel_boots").setTextureName(RefStrings.MODID + ":steel_boots");
		alloy_helmet = new ArmorFSB(MainRegistry.aMatAlloy, 0, RefStrings.MODID + ":textures/armor/alloy_1.png").setUnlocalizedName("alloy_helmet").setTextureName(RefStrings.MODID + ":alloy_helmet");
		alloy_plate = new ArmorFSB(MainRegistry.aMatAlloy, 1, RefStrings.MODID + ":textures/armor/alloy_1.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_plate").setTextureName(RefStrings.MODID + ":alloy_plate");
		alloy_legs = new ArmorFSB(MainRegistry.aMatAlloy, 2, RefStrings.MODID + ":textures/armor/alloy_2.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_legs").setTextureName(RefStrings.MODID + ":alloy_legs");
		alloy_boots = new ArmorFSB(MainRegistry.aMatAlloy, 3, RefStrings.MODID + ":textures/armor/alloy_1.png").cloneStats((ArmorFSB) alloy_helmet).setUnlocalizedName("alloy_boots").setTextureName(RefStrings.MODID + ":alloy_boots");
		cmb_helmet = new ArmorFSB(MainRegistry.aMatCMB, 0, RefStrings.MODID + ":textures/armor/cmb_1.png")
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 4))
				.setUnlocalizedName("cmb_helmet").setTextureName(RefStrings.MODID + ":cmb_helmet");
		cmb_plate = new ArmorFSB(MainRegistry.aMatCMB, 1, RefStrings.MODID + ":textures/armor/cmb_1.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_plate").setTextureName(RefStrings.MODID + ":cmb_plate");
		cmb_legs = new ArmorFSB(MainRegistry.aMatCMB, 2, RefStrings.MODID + ":textures/armor/cmb_2.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_legs").setTextureName(RefStrings.MODID + ":cmb_legs");
		cmb_boots = new ArmorFSB(MainRegistry.aMatCMB, 3, RefStrings.MODID + ":textures/armor/cmb_1.png").cloneStats((ArmorFSB) cmb_helmet).setUnlocalizedName("cmb_boots").setTextureName(RefStrings.MODID + ":cmb_boots");
		paa_plate = new ArmorFSB(MainRegistry.aMatPaa, 1, RefStrings.MODID + ":textures/armor/paa_1.png").setNoHelmet(true)
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 0)).setUnlocalizedName("paa_plate").setTextureName(RefStrings.MODID + ":paa_plate");
		paa_legs = new ArmorFSB(MainRegistry.aMatPaa, 2, RefStrings.MODID + ":textures/armor/paa_2.png").cloneStats((ArmorFSB) paa_plate).setUnlocalizedName("paa_legs").setTextureName(RefStrings.MODID + ":paa_legs");
		paa_boots = new ArmorFSB(MainRegistry.aMatPaa, 3, RefStrings.MODID + ":textures/armor/paa_1.png").cloneStats((ArmorFSB) paa_plate).setUnlocalizedName("paa_boots").setTextureName(RefStrings.MODID + ":paa_boots");
		asbestos_helmet = new ArmorFSB(MainRegistry.aMatAsbestos, 0, RefStrings.MODID + ":textures/armor/asbestos_1.png").setOverlay(RefStrings.MODID + ":textures/misc/overlay_asbestos.png").setUnlocalizedName("asbestos_helmet").setTextureName(RefStrings.MODID + ":asbestos_helmet");
		asbestos_plate = new ArmorFSB(MainRegistry.aMatAsbestos, 1, RefStrings.MODID + ":textures/armor/asbestos_1.png").setUnlocalizedName("asbestos_plate").setTextureName(RefStrings.MODID + ":asbestos_plate");
		asbestos_legs = new ArmorFSB(MainRegistry.aMatAsbestos, 2, RefStrings.MODID + ":textures/armor/asbestos_2.png").setUnlocalizedName("asbestos_legs").setTextureName(RefStrings.MODID + ":asbestos_legs");
		asbestos_boots = new ArmorFSB(MainRegistry.aMatAsbestos, 3, RefStrings.MODID + ":textures/armor/asbestos_1.png").setUnlocalizedName("asbestos_boots").setTextureName(RefStrings.MODID + ":asbestos_boots");
		security_helmet = new ArmorFSB(MainRegistry.aMatSecurity, 0, RefStrings.MODID + ":textures/armor/security_1.png").setUnlocalizedName("security_helmet").setTextureName(RefStrings.MODID + ":security_helmet");
		security_plate = new ArmorFSB(MainRegistry.aMatSecurity, 1, RefStrings.MODID + ":textures/armor/security_1.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_plate").setTextureName(RefStrings.MODID + ":security_plate");
		security_legs = new ArmorFSB(MainRegistry.aMatSecurity, 2, RefStrings.MODID + ":textures/armor/security_2.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_legs").setTextureName(RefStrings.MODID + ":security_legs");
		security_boots = new ArmorFSB(MainRegistry.aMatSecurity, 3, RefStrings.MODID + ":textures/armor/security_1.png").cloneStats((ArmorFSB) security_helmet).setUnlocalizedName("security_boots").setTextureName(RefStrings.MODID + ":security_boots");
		cobalt_helmet = new ArmorFSB(MainRegistry.aMatCobalt, 0, RefStrings.MODID + ":textures/armor/cobalt_1.png").setUnlocalizedName("cobalt_helmet").setTextureName(RefStrings.MODID + ":cobalt_helmet");
		cobalt_plate = new ArmorFSB(MainRegistry.aMatCobalt, 1, RefStrings.MODID + ":textures/armor/cobalt_1.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_plate").setTextureName(RefStrings.MODID + ":cobalt_plate");
		cobalt_legs = new ArmorFSB(MainRegistry.aMatCobalt, 2, RefStrings.MODID + ":textures/armor/cobalt_2.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_legs").setTextureName(RefStrings.MODID + ":cobalt_legs");
		cobalt_boots = new ArmorFSB(MainRegistry.aMatCobalt, 3, RefStrings.MODID + ":textures/armor/cobalt_1.png").cloneStats((ArmorFSB) cobalt_helmet).setUnlocalizedName("cobalt_boots").setTextureName(RefStrings.MODID + ":cobalt_boots");
		starmetal_helmet = new ArmorFSB(MainRegistry.aMatStarmetal, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.setUnlocalizedName("starmetal_helmet").setTextureName(RefStrings.MODID + ":starmetal_helmet");
		starmetal_plate = new ArmorFSB(MainRegistry.aMatStarmetal, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_plate").setTextureName(RefStrings.MODID + ":starmetal_plate");
		starmetal_legs = new ArmorFSB(MainRegistry.aMatStarmetal, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_legs").setTextureName(RefStrings.MODID + ":starmetal_legs");
		starmetal_boots = new ArmorFSB(MainRegistry.aMatStarmetal, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) starmetal_helmet).setUnlocalizedName("starmetal_boots").setTextureName(RefStrings.MODID + ":starmetal_boots");

		robes_helmet = new ArmorFSB(ArmorMaterial.CHAIN, 0, RefStrings.MODID + ":textures/armor/robes_1.png").setUnlocalizedName("robes_helmet").setTextureName(RefStrings.MODID + ":robes_helmet");
		robes_plate = new ArmorFSB(ArmorMaterial.CHAIN, 1, RefStrings.MODID + ":textures/armor/robes_1.png").cloneStats((ArmorFSB) robes_helmet).setUnlocalizedName("robes_plate").setTextureName(RefStrings.MODID + ":robes_plate");
		robes_legs = new ArmorFSB(ArmorMaterial.CHAIN, 2, RefStrings.MODID + ":textures/armor/robes_2.png").cloneStats((ArmorFSB) robes_helmet).setUnlocalizedName("robes_legs").setTextureName(RefStrings.MODID + ":robes_legs");
		robes_boots = new ArmorFSB(ArmorMaterial.CHAIN, 3, RefStrings.MODID + ":textures/armor/robes_1.png").cloneStats((ArmorFSB) robes_helmet).setUnlocalizedName("robes_boots").setTextureName(RefStrings.MODID + ":robes_boots");

		initializeItem2();
	}

	public static void initializeItem2() {

		ArmorMaterial aMatZirconium = EnumHelper.addArmorMaterial("HBM_ZIRCONIUM", 1000, new int[] { 2, 5, 3, 1 }, 1000);
		aMatZirconium.customCraftingMaterial = ModItems.ingot_zirconium;
		zirconium_legs = new ArmorFSB(aMatZirconium, 2, RefStrings.MODID + ":textures/armor/zirconium_2.png").setUnlocalizedName("zirconium_legs").setTextureName(RefStrings.MODID + ":zirconium_legs");

		ArmorMaterial aMatDNT = EnumHelper.addArmorMaterial("HBM_DNT_LOLOLOL", 3, new int[] { 1, 1, 1, 1 }, 0);
		aMatDNT.customCraftingMaterial = ModItems.ingot_dineutronium;
		dnt_helmet = new ArmorFSB(aMatDNT, 0, RefStrings.MODID + ":textures/armor/dnt_1.png")
				.setUnlocalizedName("dnt_helmet").setTextureName(RefStrings.MODID + ":dnt_helmet");
		dnt_plate = new ArmorFSB(aMatDNT, 1, RefStrings.MODID + ":textures/armor/dnt_1.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_plate").setTextureName(RefStrings.MODID + ":dnt_plate");
		dnt_legs = new ArmorFSB(aMatDNT, 2, RefStrings.MODID + ":textures/armor/dnt_2.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_legs").setTextureName(RefStrings.MODID + ":dnt_legs");
		dnt_boots = new ArmorFSB(aMatDNT, 3, RefStrings.MODID + ":textures/armor/dnt_1.png").cloneStats((ArmorFSB) dnt_helmet).setUnlocalizedName("dnt_boots").setTextureName(RefStrings.MODID + ":dnt_boots");

		ArmorMaterial aMatT45 = EnumHelper.addArmorMaterial("HBM_T45", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatT45.customCraftingMaterial = ModItems.plate_armor_titanium;
		ArmorMaterial aMatDesh = EnumHelper.addArmorMaterial("HBM_DESH", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatDesh.customCraftingMaterial = ModItems.ingot_desh;
		steamsuit_helmet = new ArmorDesh(aMatDesh, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.STEAM, 64_000, 500, 50, 1)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 4))
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("steamsuit_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steamsuit_helmet");
		steamsuit_plate = new ArmorDesh(aMatDesh, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) steamsuit_helmet).setUnlocalizedName("steamsuit_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steamsuit_plate");
		steamsuit_legs = new ArmorDesh(aMatDesh, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) steamsuit_helmet).setUnlocalizedName("steamsuit_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steamsuit_legs");
		steamsuit_boots = new ArmorDesh(aMatDesh, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.STEAM, 64_000, 500, 50, 1).cloneStats((ArmorFSB) steamsuit_helmet).setUnlocalizedName("steamsuit_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":steamsuit_boots");

		ArmorMaterial aMatDiesel = EnumHelper.addArmorMaterial("HBM_BNUUY", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatDiesel.customCraftingMaterial = ModItems.plate_copper;
		dieselsuit_helmet = new ArmorDiesel(aMatDiesel, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.DIESEL, 64_000, 500, 50, 1)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 2))
				.enableThermalSight(true)
				.enableVATS(true)
				.setUnlocalizedName("dieselsuit_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":dieselsuit_helmet");
		dieselsuit_plate = new ArmorDiesel(aMatDiesel, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) dieselsuit_helmet).setUnlocalizedName("dieselsuit_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":dieselsuit_plate");
		dieselsuit_legs = new ArmorDiesel(aMatDiesel, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) dieselsuit_helmet).setUnlocalizedName("dieselsuit_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":dieselsuit_legs");
		dieselsuit_boots = new ArmorDiesel(aMatDiesel, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", Fluids.DIESEL, 64_000, 500, 50, 1).cloneStats((ArmorFSB) dieselsuit_helmet).setUnlocalizedName("dieselsuit_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":dieselsuit_boots");

		ArmorMaterial aMatAJR = EnumHelper.addArmorMaterial("HBM_T45AJR", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatAJR.customCraftingMaterial = ModItems.plate_armor_ajr;
		ajr_helmet = new ArmorAJR(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setStep("hbm:step.metal").setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("ajr_helmet").setTextureName(RefStrings.MODID + ":ajr_helmet");
		ajr_plate = new ArmorAJR(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_plate").setTextureName(RefStrings.MODID + ":ajr_plate");
		ajr_legs = new ArmorAJR(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_legs").setTextureName(RefStrings.MODID + ":ajr_legs");
		ajr_boots = new ArmorAJR(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajr_helmet).setUnlocalizedName("ajr_boots").setTextureName(RefStrings.MODID + ":ajr_boots");

		ajro_helmet = new ArmorAJRO(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setStep("hbm:step.metal").setJump("hbm:step.iron_jump").setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("ajro_helmet").setTextureName(RefStrings.MODID + ":ajro_helmet");
		ajro_plate = new ArmorAJRO(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_plate").setTextureName(RefStrings.MODID + ":ajro_plate");
		ajro_legs = new ArmorAJRO(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_legs").setTextureName(RefStrings.MODID + ":ajro_legs");
		ajro_boots = new ArmorAJRO(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) ajro_helmet).setUnlocalizedName("ajro_boots").setTextureName(RefStrings.MODID + ":ajro_boots");

		rpa_helmet = new ArmorRPA(aMatAJR, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25)
				.enableVATS(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 3))
				.setStep("hbm:step.powered")
				.setJump("hbm:step.powered")
				.setFall("hbm:step.powered")
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("rpa_helmet").setTextureName(RefStrings.MODID + ":rpa_helmet");
		rpa_plate = new ArmorRPA(aMatAJR, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) rpa_helmet).setUnlocalizedName("rpa_plate").setTextureName(RefStrings.MODID + ":rpa_plate");
		rpa_legs = new ArmorRPA(aMatAJR, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) rpa_helmet).setUnlocalizedName("rpa_legs").setTextureName(RefStrings.MODID + ":rpa_legs");
		rpa_boots = new ArmorRPA(aMatAJR, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 2500000, 10000, 2000, 25).cloneStats((ArmorFSB) rpa_helmet).setUnlocalizedName("rpa_boots").setTextureName(RefStrings.MODID + ":rpa_boots");

		ArmorMaterial aMatBJ = EnumHelper.addArmorMaterial("HBM_BLACKJACK", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatBJ.customCraftingMaterial = ModItems.plate_armor_lunar;
		bj_helmet = new ArmorBJ(aMatBJ, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100)
				.enableVATS(true)
				.enableThermalSight(true)
				.setHasGeigerSound(true)
				.setHasHardLanding(true)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.addEffect(new PotionEffect(Potion.field_76443_y.id, 20, 0))
				.addEffect(new PotionEffect(HbmPotion.radx.id, 20, 0))
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land").setUnlocalizedName("bj_helmet").setTextureName(RefStrings.MODID + ":bj_helmet");
		bj_plate = new ArmorBJ(aMatBJ, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_plate").setTextureName(RefStrings.MODID + ":bj_plate");
		bj_plate_jetpack = new ArmorBJJetpack(aMatBJ, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_plate_jetpack").setTextureName(RefStrings.MODID + ":bj_plate_jetpack");
		bj_legs = new ArmorBJ(aMatBJ, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_legs").setTextureName(RefStrings.MODID + ":bj_legs");
		bj_boots = new ArmorBJ(aMatBJ, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 1000, 100).cloneStats((ArmorFSB) bj_helmet).setUnlocalizedName("bj_boots").setTextureName(RefStrings.MODID + ":bj_boots");

		ArmorMaterial aMatEnv = EnumHelper.addArmorMaterial("HBM_ENV", 150, new int[] { 3, 8, 6, 3 }, 10);
		aMatEnv.customCraftingMaterial = ModItems.plate_armor_hev;
		envsuit_helmet = new ArmorEnvsuit(aMatEnv, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 100_000, 1_000, 250, 0)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("envsuit_helmet").setTextureName(RefStrings.MODID + ":envsuit_helmet");
		envsuit_plate = new ArmorEnvsuit(aMatEnv, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 100_000, 1_000, 250, 0).cloneStats((ArmorFSB) envsuit_helmet).setUnlocalizedName("envsuit_plate").setTextureName(RefStrings.MODID + ":envsuit_plate");
		envsuit_legs = new ArmorEnvsuit(aMatEnv, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 100_000, 1_000, 250, 0).cloneStats((ArmorFSB) envsuit_helmet).setUnlocalizedName("envsuit_legs").setTextureName(RefStrings.MODID + ":envsuit_legs");
		envsuit_boots = new ArmorEnvsuit(aMatEnv, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 100_000, 1_000, 250, 0).cloneStats((ArmorFSB) envsuit_helmet).setUnlocalizedName("envsuit_boots").setTextureName(RefStrings.MODID + ":envsuit_boots");

		ArmorMaterial aMatHEV = EnumHelper.addArmorMaterial("HBM_HEV", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatHEV.customCraftingMaterial = ModItems.plate_armor_hev;
		hev_helmet = new ArmorHEV(aMatHEV, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0)
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 0))
				.setHasGeigerSound(true)
				.setHasCustomGeiger(true)
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("hev_helmet").setTextureName(RefStrings.MODID + ":hev_helmet");
		hev_plate = new ArmorHEV(aMatHEV, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_plate").setTextureName(RefStrings.MODID + ":hev_plate");
		hev_legs = new ArmorHEV(aMatHEV, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_legs").setTextureName(RefStrings.MODID + ":hev_legs");
		hev_boots = new ArmorHEV(aMatHEV, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000, 10000, 2500, 0).cloneStats((ArmorFSB) hev_helmet).setUnlocalizedName("hev_boots").setTextureName(RefStrings.MODID + ":hev_boots");

		jackt = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt").setTextureName(RefStrings.MODID + ":jackt");
		jackt2 = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt2").setTextureName(RefStrings.MODID + ":jackt2");

		ArmorMaterial aMatFau = EnumHelper.addArmorMaterial("HBM_DIGAMMA", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatFau.customCraftingMaterial = ModItems.plate_armor_fau;
		fau_helmet = new ArmorDigamma(aMatFau, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0)
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.setHasGeigerSound(true)
				.enableThermalSight(true)
				.setHasHardLanding(true)
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("fau_helmet").setTextureName(RefStrings.MODID + ":fau_helmet");
		fau_plate = new ArmorDigamma(aMatFau, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).setFullSetForHide().setUnlocalizedName("fau_plate").setTextureName(RefStrings.MODID + ":fau_plate");
		fau_legs = new ArmorDigamma(aMatFau, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).hides(EnumPlayerPart.LEFT_LEG, EnumPlayerPart.RIGHT_LEG).setFullSetForHide().setUnlocalizedName("fau_legs").setTextureName(RefStrings.MODID + ":fau_legs");
		fau_boots = new ArmorDigamma(aMatFau, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 10000000, 10000, 2500, 0).cloneStats((ArmorFSB) fau_helmet).setUnlocalizedName("fau_boots").setTextureName(RefStrings.MODID + ":fau_boots");

		ArmorMaterial aMatDNS = EnumHelper.addArmorMaterial("HBM_DNT_NANO", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatDNS.customCraftingMaterial = ModItems.plate_armor_dnt;
		dns_helmet = new ArmorDNT(aMatDNS, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000, 1000000, 100000, 115)
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 9))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 7))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 2))
				.setHasGeigerSound(true)
				.enableVATS(true)
				.enableThermalSight(true)
				.setHasHardLanding(true)
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land")
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("dns_helmet").setTextureName(RefStrings.MODID + ":dns_helmet");
		dns_plate = new ArmorDNT(aMatDNS, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000, 1000000, 100000, 115).cloneStats((ArmorFSB) dns_helmet).setUnlocalizedName("dns_plate").setTextureName(RefStrings.MODID + ":dns_plate");
		dns_legs = new ArmorDNT(aMatDNS, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png", 1000000000, 1000000, 100000, 115).cloneStats((ArmorFSB) dns_helmet).setUnlocalizedName("dns_legs").setTextureName(RefStrings.MODID + ":dns_legs");
		dns_boots = new ArmorDNT(aMatDNS, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png", 1000000000, 1000000, 100000, 115).cloneStats((ArmorFSB) dns_helmet).setUnlocalizedName("dns_boots").setTextureName(RefStrings.MODID + ":dns_boots");

		ArmorMaterial aMatTaurun = EnumHelper.addArmorMaterial("HBM_TRENCH", 150, new int[] { 3, 8, 6, 3 }, 10);
		aMatTaurun.customCraftingMaterial = ModItems.plate_iron;
		taurun_helmet = new ArmorTaurun(aMatTaurun, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 0))
				.setStepSize(1)
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("taurun_helmet").setTextureName(RefStrings.MODID + ":taurun_helmet");
		taurun_plate = new ArmorTaurun(aMatTaurun, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) taurun_helmet).setUnlocalizedName("taurun_plate").setTextureName(RefStrings.MODID + ":taurun_plate");
		taurun_legs = new ArmorTaurun(aMatTaurun, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) taurun_helmet).setUnlocalizedName("taurun_legs").setTextureName(RefStrings.MODID + ":taurun_legs");
		taurun_boots = new ArmorTaurun(aMatTaurun, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) taurun_helmet).setUnlocalizedName("taurun_boots").setTextureName(RefStrings.MODID + ":taurun_boots");
		ArmorMaterial aMatTrench = EnumHelper.addArmorMaterial("HBM_TRENCH", 150, new int[] { 3, 8, 6, 3 }, 0);
		aMatTrench.customCraftingMaterial = ModItems.plate_iron;
		trenchmaster_helmet = new ArmorTrenchmaster(aMatTrench, 0, RefStrings.MODID + ":textures/armor/starmetal_1.png")
				.addEffect(new PotionEffect(Potion.damageBoost.id, 20, 2))
				.addEffect(new PotionEffect(Potion.digSpeed.id, 20, 1))
				.addEffect(new PotionEffect(Potion.jump.id, 20, 1))
				.addEffect(new PotionEffect(Potion.moveSpeed.id, 20, 0))
				.enableVATS(true)
				.setStepSize(1)
				.hides(EnumPlayerPart.HAT)
				.setUnlocalizedName("trenchmaster_helmet").setTextureName(RefStrings.MODID + ":trenchmaster_helmet");
		trenchmaster_plate = new ArmorTrenchmaster(aMatTrench, 1, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) trenchmaster_helmet).setUnlocalizedName("trenchmaster_plate").setTextureName(RefStrings.MODID + ":trenchmaster_plate");
		trenchmaster_legs = new ArmorTrenchmaster(aMatTrench, 2, RefStrings.MODID + ":textures/armor/starmetal_2.png").cloneStats((ArmorFSB) trenchmaster_helmet).setUnlocalizedName("trenchmaster_legs").setTextureName(RefStrings.MODID + ":trenchmaster_legs");
		trenchmaster_boots = new ArmorTrenchmaster(aMatTrench, 3, RefStrings.MODID + ":textures/armor/starmetal_1.png").cloneStats((ArmorFSB) trenchmaster_helmet).setUnlocalizedName("trenchmaster_boots").setTextureName(RefStrings.MODID + ":trenchmaster_boots");

		jackt = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt").setTextureName(RefStrings.MODID + ":jackt");
		jackt2 = new ModArmor(MainRegistry.aMatSteel, 1).setUnlocalizedName("jackt2").setTextureName(RefStrings.MODID + ":jackt2");

		chainsaw = new ItemChainsaw(25, -0.05, MainRegistry.tMatChainsaw, EnumToolType.AXE, 5000, 1, 250,
				Fluids.DIESEL, Fluids.DIESEL_CRACK, Fluids.KEROSENE, Fluids.BIOFUEL, Fluids.GASOLINE, Fluids.GASOLINE_LEADED, Fluids.PETROIL, Fluids.PETROIL_LEADED, Fluids.COALGAS, Fluids.COALGAS_LEADED)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IWeaponAbility.CHAINSAW, 1)
				.addAbility(IWeaponAbility.BEHEADER, 0).setShears().setUnlocalizedName("chainsaw").setTextureName(RefStrings.MODID + ":chainsaw");

		schrabidium_sword = new ItemSwordAbility(75, 0, MainRegistry.tMatSchrab)
				.addAbility(IWeaponAbility.RADIATION, 1)
				.addAbility(IWeaponAbility.VAMPIRE, 0)
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_sword").setTextureName(RefStrings.MODID + ":schrabidium_sword");

		schrabidium_pickaxe = new ItemToolAbility(20, 0, MainRegistry.tMatSchrab, EnumToolType.PICKAXE)
				.addAbility(IWeaponAbility.RADIATION, 0)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 6)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 4)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SHREDDER, 0)
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_pickaxe").setTextureName(RefStrings.MODID + ":schrabidium_pickaxe");

		schrabidium_axe = new ItemToolAbility(25, 0, MainRegistry.tMatSchrab, EnumToolType.AXE)
				.addAbility(IWeaponAbility.RADIATION, 0)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 6)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 4)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SHREDDER, 0)
				.addAbility(IWeaponAbility.BEHEADER, 0)
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_axe").setTextureName(RefStrings.MODID + ":schrabidium_axe");

		schrabidium_shovel = new ItemToolAbility(15, 0, MainRegistry.tMatSchrab, EnumToolType.SHOVEL)
				.addAbility(IWeaponAbility.RADIATION, 0)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 6)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 4)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SHREDDER, 0)
				.setRarity(EnumRarity.rare).setUnlocalizedName("schrabidium_shovel").setTextureName(RefStrings.MODID + ":schrabidium_shovel");

		schrabidium_hoe = new HoeSchrabidium(MainRegistry.tMatSchrab).setUnlocalizedName("schrabidium_hoe").setTextureName(RefStrings.MODID + ":schrabidium_hoe");

		titanium_sword = new ItemSwordAbility(6.5F, 0, MainRegistry.tMatTitan).setUnlocalizedName("titanium_sword").setTextureName(RefStrings.MODID + ":titanium_sword");
		titanium_pickaxe = new ItemToolAbility(4.5F, 0, MainRegistry.tMatTitan, EnumToolType.PICKAXE).setUnlocalizedName("titanium_pickaxe").setTextureName(RefStrings.MODID + ":titanium_pickaxe");
		titanium_axe = new ItemToolAbility(5.5F, 0, MainRegistry.tMatTitan, EnumToolType.AXE)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("titanium_axe").setTextureName(RefStrings.MODID + ":titanium_axe");
		titanium_shovel = new ItemToolAbility(3.5F, 0, MainRegistry.tMatTitan, EnumToolType.SHOVEL).setUnlocalizedName("titanium_shovel").setTextureName(RefStrings.MODID + ":titanium_shovel");
		titanium_hoe = new ModHoe(MainRegistry.tMatTitan).setUnlocalizedName("titanium_hoe").setTextureName(RefStrings.MODID + ":titanium_hoe");
		steel_sword = new ItemSwordAbility(6F, 0, MainRegistry.tMatSteel).setUnlocalizedName("steel_sword").setTextureName(RefStrings.MODID + ":steel_sword");
		steel_pickaxe = new ItemToolAbility(4F, 0, MainRegistry.tMatSteel, EnumToolType.PICKAXE).setUnlocalizedName("steel_pickaxe").setTextureName(RefStrings.MODID + ":steel_pickaxe");
		steel_axe = new ItemToolAbility(5F, 0, MainRegistry.tMatSteel, EnumToolType.AXE)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("steel_axe").setTextureName(RefStrings.MODID + ":steel_axe");
		steel_shovel = new ItemToolAbility(3F, 0, MainRegistry.tMatSteel, EnumToolType.SHOVEL).setUnlocalizedName("steel_shovel").setTextureName(RefStrings.MODID + ":steel_shovel");
		steel_hoe = new ModHoe(MainRegistry.tMatSteel).setUnlocalizedName("steel_hoe").setTextureName(RefStrings.MODID + ":steel_hoe");

		alloy_sword = new ItemSwordAbility(8F, 0, MainRegistry.tMatAlloy)
				.addAbility(IWeaponAbility.STUN, 0).setUnlocalizedName("alloy_sword").setTextureName(RefStrings.MODID + ":alloy_sword");

		alloy_pickaxe = new ItemToolAbility(5F, 0, MainRegistry.tMatAlloy, EnumToolType.PICKAXE)
				.addAbility(IToolAreaAbility.RECURSION, 0).setUnlocalizedName("alloy_pickaxe").setTextureName(RefStrings.MODID + ":alloy_pickaxe");

		alloy_axe = new ItemToolAbility(7F, 0, MainRegistry.tMatAlloy, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.RECURSION, 0)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("alloy_axe").setTextureName(RefStrings.MODID + ":alloy_axe");

		alloy_shovel = new ItemToolAbility(4F, 0, MainRegistry.tMatAlloy, EnumToolType.SHOVEL)
				.addAbility(IToolAreaAbility.RECURSION, 0).setUnlocalizedName("alloy_shovel").setTextureName(RefStrings.MODID + ":alloy_shovel");

		alloy_hoe = new ModHoe(MainRegistry.tMatAlloy).setUnlocalizedName("alloy_hoe").setTextureName(RefStrings.MODID + ":alloy_hoe");

		cmb_sword = new ItemSwordAbility(35F, 0, MainRegistry.tMatCMB)
				.addAbility(IWeaponAbility.STUN, 0)
				.addAbility(IWeaponAbility.VAMPIRE, 0).setUnlocalizedName("cmb_sword").setTextureName(RefStrings.MODID + ":cmb_sword");

		cmb_pickaxe = new ItemToolAbility(10F, 0, MainRegistry.tMatCMB, EnumToolType.PICKAXE)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2).setUnlocalizedName("cmb_pickaxe").setTextureName(RefStrings.MODID + ":cmb_pickaxe");

		cmb_axe = new ItemToolAbility(30F, 0, MainRegistry.tMatCMB, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("cmb_axe").setTextureName(RefStrings.MODID + ":cmb_axe");

		cmb_shovel = new ItemToolAbility(8F, 0, MainRegistry.tMatCMB, EnumToolType.SHOVEL)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2).setUnlocalizedName("cmb_shovel").setTextureName(RefStrings.MODID + ":cmb_shovel");

		cmb_hoe = new ModHoe(MainRegistry.tMatCMB).setUnlocalizedName("cmb_hoe").setTextureName(RefStrings.MODID + ":cmb_hoe");

		elec_sword = new ItemSwordAbilityPower(12.5F, 0, MainRegistry.tMatElec, 500000, 1000, 100)
				.addAbility(IWeaponAbility.STUN, 2).setUnlocalizedName("elec_sword").setTextureName(RefStrings.MODID + ":elec_sword_anim");

		elec_pickaxe = new ItemToolAbilityPower(6F, 0, MainRegistry.tMatElec, EnumToolType.PICKAXE, 500000, 1000, 100)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1).setUnlocalizedName("elec_pickaxe").setTextureName(RefStrings.MODID + ":elec_drill_anim");

		elec_axe = new ItemToolAbilityPower(10F, 0, MainRegistry.tMatElec, EnumToolType.AXE, 500000, 1000, 100)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1)
				.addAbility(IWeaponAbility.CHAINSAW, 0)
				.addAbility(IWeaponAbility.BEHEADER, 0).setShears().setUnlocalizedName("elec_axe").setTextureName(RefStrings.MODID + ":elec_chainsaw_anim");

		elec_shovel = new ItemToolAbilityPower(5F, 0, MainRegistry.tMatElec, EnumToolType.SHOVEL, 500000, 1000, 100)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1).setUnlocalizedName("elec_shovel").setTextureName(RefStrings.MODID + ":elec_shovel_anim");

		desh_sword = new ItemSwordAbility(12.5F, 0, MainRegistry.tMatDesh)
				.addAbility(IWeaponAbility.STUN, 0).setUnlocalizedName("desh_sword").setTextureName(RefStrings.MODID + ":desh_sword");

		desh_pickaxe = new ItemToolAbility(5F, -0.05, MainRegistry.tMatDesh, EnumToolType.PICKAXE)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolAreaAbility.RECURSION, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1).setUnlocalizedName("desh_pickaxe").setTextureName(RefStrings.MODID + ":desh_pickaxe");

		desh_axe = new ItemToolAbility(7.5F, -0.05, MainRegistry.tMatDesh, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolAreaAbility.RECURSION, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("desh_axe").setTextureName(RefStrings.MODID + ":desh_axe");

		desh_shovel = new ItemToolAbility(4F, -0.05, MainRegistry.tMatDesh, EnumToolType.SHOVEL)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolAreaAbility.RECURSION, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1).setUnlocalizedName("desh_shovel").setTextureName(RefStrings.MODID + ":desh_shovel");

		desh_hoe = new ModHoe(MainRegistry.tMatDesh).setUnlocalizedName("desh_hoe").setTextureName(RefStrings.MODID + ":desh_hoe");

		cobalt_sword = new ItemSwordAbility(12F, 0, MainRegistry.tMatCobalt).setUnlocalizedName("cobalt_sword").setTextureName(RefStrings.MODID + ":cobalt_sword");
		cobalt_pickaxe = new ItemToolAbility(4F, 0, MainRegistry.tMatCobalt, EnumToolType.PICKAXE)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 0).setUnlocalizedName("cobalt_pickaxe").setTextureName(RefStrings.MODID + ":cobalt_pickaxe");
		cobalt_axe = new ItemToolAbility(6F, 0, MainRegistry.tMatCobalt, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 0)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("cobalt_axe").setTextureName(RefStrings.MODID + ":cobalt_axe");
		cobalt_shovel = new ItemToolAbility(3.5F, 0, MainRegistry.tMatCobalt, EnumToolType.SHOVEL)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 0).setUnlocalizedName("cobalt_shovel").setTextureName(RefStrings.MODID + ":cobalt_shovel");
		cobalt_hoe = new ModHoe(MainRegistry.tMatCobalt).setUnlocalizedName("cobalt_hoe").setTextureName(RefStrings.MODID + ":cobalt_hoe");

		ToolMaterial matDecCobalt = EnumHelper.addToolMaterial("HBM_COBALT2", 3, 2500, 15.0F, 2.5F, 75).setRepairItem(new ItemStack(ModItems.ingot_cobalt));
		cobalt_decorated_sword = new ItemSwordAbility(15F, 0, matDecCobalt)
				.addAbility(IWeaponAbility.BOBBLE, 0).setUnlocalizedName("cobalt_decorated_sword").setTextureName(RefStrings.MODID + ":cobalt_decorated_sword");
		cobalt_decorated_pickaxe = new ItemToolAbility(6F, 0, matDecCobalt, EnumToolType.PICKAXE)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2).setUnlocalizedName("cobalt_decorated_pickaxe").setTextureName(RefStrings.MODID + ":cobalt_decorated_pickaxe");
		cobalt_decorated_axe = new ItemToolAbility(8F, 0, matDecCobalt, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("cobalt_decorated_axe").setTextureName(RefStrings.MODID + ":cobalt_decorated_axe");
		cobalt_decorated_shovel = new ItemToolAbility(5F, 0, matDecCobalt, EnumToolType.SHOVEL)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2).setUnlocalizedName("cobalt_decorated_shovel").setTextureName(RefStrings.MODID + ":cobalt_decorated_shovel");
		cobalt_decorated_hoe = new ModHoe(matDecCobalt).setUnlocalizedName("cobalt_decorated_hoe").setTextureName(RefStrings.MODID + ":cobalt_decorated_hoe");

		ToolMaterial matStarmetal = EnumHelper.addToolMaterial("HBM_STARMETAL", 3, 3000, 20.0F, 2.5F, 100).setRepairItem(new ItemStack(ModItems.ingot_starmetal));
		starmetal_sword = new ItemSwordAbility(25F, 0, matStarmetal)
				.addAbility(IWeaponAbility.BEHEADER, 0)
				.addAbility(IWeaponAbility.STUN, 1)
				.addAbility(IWeaponAbility.BOBBLE, 0).setUnlocalizedName("starmetal_sword").setTextureName(RefStrings.MODID + ":starmetal_sword");
		starmetal_pickaxe = new ItemToolAbility(8F, 0, matStarmetal, EnumToolType.PICKAXE)
				.addAbility(IToolAreaAbility.RECURSION, 3)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 4)
				.addAbility(IWeaponAbility.STUN, 1).setUnlocalizedName("starmetal_pickaxe").setTextureName(RefStrings.MODID + ":starmetal_pickaxe");
		starmetal_axe = new ItemToolAbility(12F, 0, matStarmetal, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.RECURSION, 3)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 4)
				.addAbility(IWeaponAbility.BEHEADER, 0)
				.addAbility(IWeaponAbility.STUN, 1).setUnlocalizedName("starmetal_axe").setTextureName(RefStrings.MODID + ":starmetal_axe");
		starmetal_shovel = new ItemToolAbility(7F, 0, matStarmetal, EnumToolType.SHOVEL)
				.addAbility(IToolAreaAbility.RECURSION, 3)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 4)
				.addAbility(IWeaponAbility.STUN, 1).setUnlocalizedName("starmetal_shovel").setTextureName(RefStrings.MODID + ":starmetal_shovel");
		starmetal_hoe = new ModHoe(matStarmetal).setUnlocalizedName("starmetal_hoe").setTextureName(RefStrings.MODID + ":starmetal_hoe");

		centri_stick = new ItemToolAbility(3F, 0, MainRegistry.tMatElec, EnumToolType.MINER)
				.addAbility(IToolHarvestAbility.CENTRIFUGE, 0).setMaxDamage(50).setUnlocalizedName("centri_stick").setTextureName(RefStrings.MODID + ":centri_stick");
		smashing_hammer = new ItemToolAbility(12F, -0.1, MainRegistry.tMatSteel, EnumToolType.MINER)
				.addAbility(IToolHarvestAbility.SHREDDER, 0).setMaxDamage(2500).setUnlocalizedName("smashing_hammer").setTextureName(RefStrings.MODID + ":smashing_hammer");
		drax = new ItemToolAbilityPower(10F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 500000000, 100000, 5000)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SHREDDER, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 2).setUnlocalizedName("drax").setCreativeTab(null).setTextureName(RefStrings.MODID + ":drax");
		drax_mk2 = new ItemToolAbilityPower(15F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 1000000000, 250000, 7500)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SHREDDER, 0)
				.addAbility(IToolHarvestAbility.CENTRIFUGE, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2)
				.addAbility(IToolAreaAbility.HAMMER, 2)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 2)
				.addAbility(IToolAreaAbility.RECURSION, 4).setUnlocalizedName("drax_mk2").setCreativeTab(null).setTextureName(RefStrings.MODID + ":drax_mk2");
		drax_mk3 = new ItemToolAbilityPower(20F, -0.05, MainRegistry.tMatElec, EnumToolType.MINER, 2500000000L, 500000, 10000)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.SHREDDER, 0)
				.addAbility(IToolHarvestAbility.CENTRIFUGE, 0)
				.addAbility(IToolHarvestAbility.CRYSTALLIZER, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 3)
				.addAbility(IToolAreaAbility.HAMMER, 3)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 3)
				.addAbility(IToolAreaAbility.RECURSION, 5).setUnlocalizedName("drax_mk3").setCreativeTab(null).setTextureName(RefStrings.MODID + ":drax_mk3");

		ToolMaterial matBismuth = EnumHelper.addToolMaterial("HBM_BISMUTH", 4, 0, 50F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.ingot_bismuth));
		bismuth_pickaxe = new ItemToolAbility(15F, 0, matBismuth, EnumToolType.MINER)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.SHREDDER, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IWeaponAbility.STUN, 2)
				.addAbility(IWeaponAbility.VAMPIRE, 0)
				.addAbility(IWeaponAbility.BEHEADER, 0)
				.setDepthRockBreaker().setUnlocalizedName("bismuth_pickaxe").setTextureName(RefStrings.MODID + ":bismuth_pickaxe");
		bismuth_axe = new ItemToolAbility(25F, 0, matBismuth, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.SHREDDER, 0)
				.addAbility(IToolHarvestAbility.LUCK, 1)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IWeaponAbility.STUN, 3)
				.addAbility(IWeaponAbility.VAMPIRE, 1)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("bismuth_axe").setTextureName(RefStrings.MODID + ":bismuth_axe");


		ToolMaterial matVolcano = EnumHelper.addToolMaterial("HBM_VOLCANIC", 4, 0, 50F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.ingot_bismuth));
		volcanic_pickaxe = new ItemToolAbility(15F, 0, matVolcano, EnumToolType.MINER)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IWeaponAbility.FIRE, 0)
				.addAbility(IWeaponAbility.VAMPIRE, 0)
				.addAbility(IWeaponAbility.BEHEADER, 0)
				.setDepthRockBreaker().setUnlocalizedName("volcanic_pickaxe").setTextureName(RefStrings.MODID + ":volcanic_pickaxe");
		volcanic_axe = new ItemToolAbility(25F, 0, matVolcano, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.SMELTER, 0)
				.addAbility(IToolHarvestAbility.LUCK, 2)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IWeaponAbility.FIRE, 1)
				.addAbility(IWeaponAbility.VAMPIRE, 1)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("volcanic_axe").setTextureName(RefStrings.MODID + ":volcanic_axe");

		ToolMaterial matChlorophyte = EnumHelper.addToolMaterial("HBM_CHLOROPHYTE", 4, 0, 75F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.powder_chlorophyte));
		chlorophyte_pickaxe = new ItemToolAbility(20F, 0, matChlorophyte, EnumToolType.MINER)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.LUCK, 3)
				.addAbility(IToolHarvestAbility.CENTRIFUGE, 0)
				.addAbility(IToolHarvestAbility.MERCURY, 0)
				.addAbility(IWeaponAbility.STUN, 3)
				.addAbility(IWeaponAbility.VAMPIRE, 2)
				.addAbility(IWeaponAbility.BEHEADER, 0)
				.setDepthRockBreaker().setUnlocalizedName("chlorophyte_pickaxe").setTextureName(RefStrings.MODID + ":chlorophyte_pickaxe");
		chlorophyte_axe = new ItemToolAbility(50F, 0, matChlorophyte, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.HAMMER, 1)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 1)
				.addAbility(IToolAreaAbility.RECURSION, 1)
				.addAbility(IToolHarvestAbility.LUCK, 3)
				.addAbility(IWeaponAbility.STUN, 4)
				.addAbility(IWeaponAbility.VAMPIRE, 3)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("chlorophyte_axe").setTextureName(RefStrings.MODID + ":chlorophyte_axe");

		ToolMaterial matMese = EnumHelper.addToolMaterial("HBM_MESE", 4, 0, 100F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.plate_paa));
		mese_pickaxe = new ItemToolAbility(35F, 0, matMese, EnumToolType.MINER)
				.addAbility(IToolAreaAbility.HAMMER, 2)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 2)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IToolHarvestAbility.CRYSTALLIZER, 0)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 5)
				.addAbility(IToolAreaAbility.EXPLOSION, 3)
				.addAbility(IWeaponAbility.STUN, 3)
				.addAbility(IWeaponAbility.PHOSPHORUS, 0)
				.addAbility(IWeaponAbility.BEHEADER, 0)
				.setDepthRockBreaker().setUnlocalizedName("mese_pickaxe").setTextureName(RefStrings.MODID + ":mese_pickaxe");
		mese_axe = new ItemToolAbility(75F, 0, matMese, EnumToolType.AXE)
				.addAbility(IToolAreaAbility.HAMMER, 2)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 2)
				.addAbility(IToolAreaAbility.RECURSION, 2)
				.addAbility(IToolHarvestAbility.SILK, 0)
				.addAbility(IToolHarvestAbility.LUCK, 5)
				.addAbility(IToolAreaAbility.EXPLOSION, 3)
				.addAbility(IWeaponAbility.STUN, 4)
				.addAbility(IWeaponAbility.PHOSPHORUS, 1)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("mese_axe").setTextureName(RefStrings.MODID + ":mese_axe");

		dnt_sword = new ItemSwordAbility(12F, 0, matMese).setUnlocalizedName("dnt_sword").setTextureName(RefStrings.MODID + ":dnt_sword");

		ToolMaterial matDwarf = EnumHelper.addToolMaterial("HBM_DWARVEN", 2, 0, 4F, 0.0F, 10).setRepairItem(new ItemStack(ModItems.ingot_copper));
		dwarven_pickaxe = new ItemToolAbility(5F, -0.1, matDwarf, EnumToolType.MINER)
				.addAbility(IToolAreaAbility.HAMMER, 0)
				.addAbility(IToolAreaAbility.HAMMER_FLAT, 0).setUnlocalizedName("dwarven_pickaxe").setMaxDamage(250).setTextureName(RefStrings.MODID + ":dwarven_pickaxe");

		ToolMaterial matMeteorite = EnumHelper.addToolMaterial("HBM_METEORITE", 4, 0, 50F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.plate_paa));
		meteorite_sword = new ItemSwordMeteorite(9F, 0, matMeteorite).setUnlocalizedName("meteorite_sword").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_seared = new ItemSwordMeteorite(10F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_seared").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_reforged = new ItemSwordMeteorite(12.5F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_reforged").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_hardened = new ItemSwordMeteorite(15F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_hardened").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_alloyed = new ItemSwordMeteorite(17.5F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_alloyed").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_machined = new ItemSwordMeteorite(20F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_machined").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_treated = new ItemSwordMeteorite(22.5F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_treated").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_etched = new ItemSwordMeteorite(25F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_etched").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_bred = new ItemSwordMeteorite(30F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_bred").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_irradiated = new ItemSwordMeteorite(35F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_irradiated").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_fused = new ItemSwordMeteorite(50F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_fused").setTextureName(RefStrings.MODID + ":meteorite_sword");
		meteorite_sword_baleful = new ItemSwordMeteorite(75F, 0, matMeteorite).setUnlocalizedName("meteorite_sword_baleful").setTextureName(RefStrings.MODID + ":meteorite_sword");

		mask_of_infamy = new MaskOfInfamy(ArmorMaterial.IRON, 0).setUnlocalizedName("mask_of_infamy").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_of_infamy");

		hazmat_helmet = new ArmorHazmatMask(MainRegistry.aMatHaz, 0, RefStrings.MODID + ":textures/armor/hazmat_1.png").setUnlocalizedName("hazmat_helmet").setTextureName(RefStrings.MODID + ":hazmat_helmet");
		hazmat_plate = new ArmorHazmat(MainRegistry.aMatHaz, 1, RefStrings.MODID + ":textures/armor/hazmat_1.png").setUnlocalizedName("hazmat_plate").setTextureName(RefStrings.MODID + ":hazmat_plate");
		hazmat_legs = new ArmorHazmat(MainRegistry.aMatHaz, 2, RefStrings.MODID + ":textures/armor/hazmat_2.png").setUnlocalizedName("hazmat_legs").setTextureName(RefStrings.MODID + ":hazmat_legs");
		hazmat_boots = new ArmorHazmat(MainRegistry.aMatHaz, 3, RefStrings.MODID + ":textures/armor/hazmat_1.png").setUnlocalizedName("hazmat_boots").setTextureName(RefStrings.MODID + ":hazmat_boots");
		hazmat_helmet_red = new ArmorHazmatMask(MainRegistry.aMatHaz2, 0, "hbm:textures/models/ModelHazRed.png").setUnlocalizedName("hazmat_helmet_red").setTextureName(RefStrings.MODID + ":hazmat_helmet_red");
		hazmat_plate_red = new ArmorHazmat(MainRegistry.aMatHaz2, 1, RefStrings.MODID + ":textures/armor/hazmat_1_red.png").setUnlocalizedName("hazmat_plate_red").setTextureName(RefStrings.MODID + ":hazmat_plate_red");
		hazmat_legs_red = new ArmorHazmat(MainRegistry.aMatHaz2, 2, RefStrings.MODID + ":textures/armor/hazmat_2_red.png").setUnlocalizedName("hazmat_legs_red").setTextureName(RefStrings.MODID + ":hazmat_legs_red");
		hazmat_boots_red = new ArmorHazmat(MainRegistry.aMatHaz2, 3, RefStrings.MODID + ":textures/armor/hazmat_1_red.png").setUnlocalizedName("hazmat_boots_red").setTextureName(RefStrings.MODID + ":hazmat_boots_red");
		hazmat_helmet_grey = new ArmorHazmatMask(MainRegistry.aMatHaz3, 0, "hbm:textures/models/ModelHazGrey.png")
				.setUnlocalizedName("hazmat_helmet_grey").setTextureName(RefStrings.MODID + ":hazmat_helmet_grey");
		hazmat_plate_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 1, RefStrings.MODID + ":textures/armor/hazmat_1_grey.png").cloneStats((ArmorFSB) hazmat_helmet_grey).setUnlocalizedName("hazmat_plate_grey").setTextureName(RefStrings.MODID + ":hazmat_plate_grey");
		hazmat_legs_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 2, RefStrings.MODID + ":textures/armor/hazmat_2_grey.png").cloneStats((ArmorFSB) hazmat_helmet_grey).setUnlocalizedName("hazmat_legs_grey").setTextureName(RefStrings.MODID + ":hazmat_legs_grey");
		hazmat_boots_grey = new ArmorHazmat(MainRegistry.aMatHaz3, 3, RefStrings.MODID + ":textures/armor/hazmat_1_grey.png").cloneStats((ArmorFSB) hazmat_helmet_grey).setUnlocalizedName("hazmat_boots_grey").setTextureName(RefStrings.MODID + ":hazmat_boots_grey");
		hazmat_paa_helmet = new ArmorHazmatMask(MainRegistry.aMatPaa, 0, RefStrings.MODID + ":textures/armor/hazmat_paa_1.png")
				.setUnlocalizedName("hazmat_paa_helmet").setTextureName(RefStrings.MODID + ":hazmat_paa_helmet");
		hazmat_paa_plate = new ArmorHazmat(MainRegistry.aMatPaa, 1, RefStrings.MODID + ":textures/armor/hazmat_paa_1.png").cloneStats((ArmorFSB) hazmat_paa_helmet).setUnlocalizedName("hazmat_paa_plate").setTextureName(RefStrings.MODID + ":hazmat_paa_plate");
		hazmat_paa_legs = new ArmorHazmat(MainRegistry.aMatPaa, 2, RefStrings.MODID + ":textures/armor/hazmat_paa_2.png").cloneStats((ArmorFSB) hazmat_paa_helmet).setUnlocalizedName("hazmat_paa_legs").setTextureName(RefStrings.MODID + ":hazmat_paa_legs");
		hazmat_paa_boots = new ArmorHazmat(MainRegistry.aMatPaa, 3, RefStrings.MODID + ":textures/armor/hazmat_paa_1.png").cloneStats((ArmorFSB) hazmat_paa_helmet).setUnlocalizedName("hazmat_paa_boots").setTextureName(RefStrings.MODID + ":hazmat_paa_boots");

		ArmorMaterial aMatLiquidator = EnumHelper.addArmorMaterial("HBM_LIQUIDATOR", 750, new int[] { 3, 8, 6, 3 }, 10);
		aMatLiquidator.customCraftingMaterial = ModItems.plate_lead;
		liquidator_helmet = new ArmorLiquidatorMask(aMatLiquidator, 0, RefStrings.MODID + ":textures/armor/liquidator_helmet.png")
				.setStep("hbm:step.metal")
				.setJump("hbm:step.iron_jump")
				.setFall("hbm:step.iron_land").setUnlocalizedName("liquidator_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":liquidator_helmet");
		liquidator_plate = new ArmorLiquidator(aMatLiquidator, 1, RefStrings.MODID + ":textures/armor/liquidator_1.png").cloneStats((ArmorFSB) liquidator_helmet).setUnlocalizedName("liquidator_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":liquidator_plate");
		liquidator_legs = new ArmorLiquidator(aMatLiquidator, 2, RefStrings.MODID + ":textures/armor/liquidator_2.png").cloneStats((ArmorFSB) liquidator_helmet).setUnlocalizedName("liquidator_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":liquidator_legs");
		liquidator_boots = new ArmorLiquidator(aMatLiquidator, 3, RefStrings.MODID + ":textures/armor/liquidator_1.png").cloneStats((ArmorFSB) liquidator_helmet).setUnlocalizedName("liquidator_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":liquidator_boots");

		jetpack_boost = new JetpackBooster(Fluids.BALEFIRE, 32000).setUnlocalizedName("jetpack_boost").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_boost");
		jetpack_break = new JetpackBreak(Fluids.KEROSENE, 12000).setUnlocalizedName("jetpack_break").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_break");
		jetpack_fly = new JetpackRegular(Fluids.KEROSENE, 12000).setUnlocalizedName("jetpack_fly").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_fly");
		jetpack_vector = new JetpackVectorized(Fluids.KEROSENE, 16000).setUnlocalizedName("jetpack_vector").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":jetpack_vector");
		wings_murk = new WingsMurk().setUnlocalizedName("wings_murk").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":wings_murk");
		wings_limp = new WingsMurk().setUnlocalizedName("wings_limp").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":wings_limp");

		cape_radiation = new ArmorModel(ArmorMaterial.CHAIN, 1).setUnlocalizedName("cape_radiation").setCreativeTab(null).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_radiation");
		cape_gasmask = new ArmorModel(ArmorMaterial.CHAIN, 1).setUnlocalizedName("cape_gasmask").setCreativeTab(null).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_gasmask");
		cape_schrabidium = new ArmorModel(MainRegistry.aMatSchrab, 1).setUnlocalizedName("cape_schrabidium").setCreativeTab(null).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_schrabidium");
		cape_hidden = new ArmorModel(ArmorMaterial.CHAIN, 1).setUnlocalizedName("cape_hidden").setCreativeTab(null).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cape_unknown");

		schrabidium_hammer = new WeaponSpecial(MainRegistry.tMatHammmer).setUnlocalizedName("schrabidium_hammer").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_hammer");
		shimmer_sledge = new WeaponSpecial(MainRegistry.enumToolMaterialSledge).setUnlocalizedName("shimmer_sledge").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":shimmer_sledge_original");
		shimmer_axe = new WeaponSpecial(MainRegistry.enumToolMaterialSledge).setUnlocalizedName("shimmer_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":shimmer_axe");
		bottle_opener = new WeaponSpecial(MainRegistry.enumToolMaterialBottleOpener).setUnlocalizedName("bottle_opener").setCreativeTab(MainRegistry.consumableTab).setMaxStackSize(1).setTextureName(RefStrings.MODID + ":bottle_opener");
		pch = new WeaponSpecial(MainRegistry.tMatHammmer).setUnlocalizedName("pch").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":schrabidium_hammer");
		matchstick = new ItemMatch().setUnlocalizedName("matchstick").setCreativeTab(CreativeTabs.tabTools).setFull3D().setTextureName(RefStrings.MODID + ":matchstick");
		balefire_and_steel = new ItemBalefireMatch().setUnlocalizedName("balefire_and_steel").setCreativeTab(CreativeTabs.tabTools).setFull3D().setTextureName(RefStrings.MODID + ":balefire_and_steel");
		crowbar = new ModSword(MainRegistry.tMatSteel).setUnlocalizedName("crowbar").setFull3D().setTextureName(RefStrings.MODID + ":crowbar");
		wrench_flipped = new WeaponSpecial(MainRegistry.tMatElec).setUnlocalizedName("wrench_flipped").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":wrench_flipped");
		memespoon = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("memespoon").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":memespoon");
		wood_gavel = new WeaponSpecial(ToolMaterial.WOOD).setUnlocalizedName("wood_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":wood_gavel");
		lead_gavel = new WeaponSpecial(MainRegistry.tMatSteel).setUnlocalizedName("lead_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":lead_gavel");
		diamond_gavel = new WeaponSpecial(ToolMaterial.EMERALD).setUnlocalizedName("diamond_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":diamond_gavel");
		ToolMaterial matMeseGavel = EnumHelper.addToolMaterial("HBM_MESEGAVEL", 4, 0, 50F, 0.0F, 200).setRepairItem(new ItemStack(ModItems.plate_paa));
		mese_gavel = new ItemSwordAbility(250, 1.5, matMeseGavel)
				.addAbility(IWeaponAbility.PHOSPHORUS, 0)
				.addAbility(IWeaponAbility.RADIATION, 2)
				.addAbility(IWeaponAbility.STUN, 3)
				.addAbility(IWeaponAbility.VAMPIRE, 4)
				.addAbility(IWeaponAbility.BEHEADER, 0).setUnlocalizedName("mese_gavel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mese_gavel");

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
		bucket_sulfuric_acid = new ItemModBucket(ModBlocks.sulfuric_acid_block).setUnlocalizedName("bucket_sulfuric_acid").setContainerItem(Items.bucket).setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":bucket_sulfuric_acid");

		door_metal = new ItemModDoor().setUnlocalizedName("door_metal").setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":door_metal");
		door_office = new ItemModDoor().setUnlocalizedName("door_office").setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":door_office");
		door_bunker = new ItemModDoor().setUnlocalizedName("door_bunker").setCreativeTab(MainRegistry.blockTab).setTextureName(RefStrings.MODID + ":door_bunker");
		door_red = new ItemModDoor().setUnlocalizedName("door_red").setCreativeTab(null).setTextureName(RefStrings.MODID + ":door_red");

		record_lc = new ItemModRecord("lc").setUnlocalizedName("record_lc").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_lc");
		record_ss = new ItemModRecord("ss").setUnlocalizedName("record_ss").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_ss");
		record_vc = new ItemModRecord("vc").setUnlocalizedName("record_vc").setCreativeTab(CreativeTabs.tabMisc).setTextureName(RefStrings.MODID + ":record_vc");
		record_glass = new ItemModRecord("glass").setUnlocalizedName("record_glass").setCreativeTab(null).setTextureName(RefStrings.MODID + ":record_glass");

		book_guide = new ItemGuideBook().setUnlocalizedName("book_guide").setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":book_guide");
		book_lore = new ItemBookLore().setUnlocalizedName("book_lore").setCreativeTab(null).setTextureName(RefStrings.MODID + ":book_pages");
		holotape_image = new ItemHolotapeImage().setUnlocalizedName("holotape_image").setCreativeTab(null).setTextureName(RefStrings.MODID + ":holotape");
		holotape_damaged = new Item().setUnlocalizedName("holotape_damaged").setCreativeTab(null).setTextureName(RefStrings.MODID + ":holotape_damaged");
		clay_tablet = new ItemClayTablet().setUnlocalizedName("clay_tablet").setCreativeTab(null).setTextureName(RefStrings.MODID + ":clay_tablet");

		polaroid = new ItemPolaroid().setUnlocalizedName("polaroid").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":polaroid_" + MainRegistry.polaroidID);
		glitch = new ItemGlitch().setUnlocalizedName("glitch").setMaxStackSize(1).setCreativeTab(MainRegistry.consumableTab).setTextureName(RefStrings.MODID + ":glitch_" + MainRegistry.polaroidID);
		book_secret = new ItemCustomLore().setUnlocalizedName("book_secret").setCreativeTab(MainRegistry.polaroidID == 11 ? MainRegistry.consumableTab : null).setTextureName(RefStrings.MODID + ":book_secret");
		book_of_ = new ItemBook().setUnlocalizedName("book_of_").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":book_of_");
		page_of_ = new ItemEnumMulti(ItemEnums.EnumPages.class, true, false).setUnlocalizedName("page_of_").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":page_of_");
		book_lemegeton = new ItemBookLemegeton().setUnlocalizedName("book_lemegeton").setMaxStackSize(1).setCreativeTab(null).setTextureName(RefStrings.MODID + ":book_lemegeton");
		burnt_bark = new ItemCustomLore().setUnlocalizedName("burnt_bark").setCreativeTab(null).setTextureName(RefStrings.MODID + ":burnt_bark");

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

		nothing = new Item().setUnlocalizedName("nothing").setTextureName(RefStrings.MODID + ":nothing");
		broken_item = new BrokenItem().setUnlocalizedName("broken_item").setTextureName(RefStrings.MODID + ":broken_item");

		achievement_icon = new ItemEnumMulti(ItemEnums.EnumAchievementType.class, true, true).setUnlocalizedName("achievement_icon");

		mysteryshovel = new ItemMS().setUnlocalizedName("mysteryshovel").setFull3D().setMaxStackSize(1).setTextureName(RefStrings.MODID + ":cursed_shovel");
		memory = new ItemBattery(Long.MAX_VALUE / 100L, 100000000000000L, 100000000000000L).setUnlocalizedName("memory").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mo8_anim");

		conveyor_wand = new ItemConveyorWand().setUnlocalizedName("conveyor_wand").setCreativeTab(MainRegistry.machineTab).setFull3D().setTextureName(RefStrings.MODID + ":wand_s");

		GunFactory.init();

		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.mud_fluid, 1000), new ItemStack(ModItems.bucket_mud), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.acid_fluid, 1000), new ItemStack(ModItems.bucket_acid), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.toxic_fluid, 1000), new ItemStack(ModItems.bucket_toxic), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.schrabidic_fluid, 1000), new ItemStack(ModItems.bucket_schrabidic_acid), new ItemStack(Items.bucket));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(ModBlocks.sulfuric_acid_fluid, 1000), new ItemStack(ModItems.bucket_sulfuric_acid), new ItemStack(Items.bucket));
		BucketHandler.INSTANCE.buckets.put(ModBlocks.mud_block, ModItems.bucket_mud);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.acid_block, ModItems.bucket_acid);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.toxic_block, ModItems.bucket_toxic);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.schrabidic_block, ModItems.bucket_schrabidic_acid);
		BucketHandler.INSTANCE.buckets.put(ModBlocks.sulfuric_acid_block, ModItems.bucket_sulfuric_acid);
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
	}

	private static void registerItemSafe(Item item) {
		if (item != null) {
			GameRegistry.registerItem(item, item.getUnlocalizedName());
		}
	}

	private static void registerItem() {

		//Weapons
		registerItemSafe(redstone_sword);
		registerItemSafe(big_sword);

		//Ingots
		registerItemSafe(ingot_uranium);
		registerItemSafe(ingot_u233);
		registerItemSafe(ingot_u235);
		registerItemSafe(ingot_u238);
		registerItemSafe(ingot_u238m2);
		registerItemSafe(ingot_th232);
		registerItemSafe(ingot_plutonium);
		registerItemSafe(ingot_pu238);
		registerItemSafe(ingot_pu239);
		registerItemSafe(ingot_pu240);
		registerItemSafe(ingot_pu241);
		registerItemSafe(ingot_pu_mix);
		registerItemSafe(ingot_am241);
		registerItemSafe(ingot_am242);
		registerItemSafe(ingot_am_mix);
		registerItemSafe(ingot_neptunium);
		registerItemSafe(ingot_polonium);
		registerItemSafe(ingot_technetium);
		registerItemSafe(ingot_co60);
		registerItemSafe(ingot_sr90);
		registerItemSafe(ingot_au198);
		registerItemSafe(ingot_pb209);
		registerItemSafe(ingot_ra226);
		registerItemSafe(ingot_titanium);
		registerItemSafe(ingot_copper);
		registerItemSafe(ingot_red_copper);
		registerItemSafe(ingot_advanced_alloy);
		registerItemSafe(ingot_tungsten);
		registerItemSafe(ingot_tungsten_carbide);
		registerItemSafe(ingot_aluminium);
		registerItemSafe(ingot_steel);
		registerItemSafe(ingot_tcalloy);
		registerItemSafe(ingot_cdalloy);
		registerItemSafe(ingot_bismuth_bronze);
		registerItemSafe(ingot_arsenic_bronze);
		registerItemSafe(ingot_bscco);
		registerItemSafe(ingot_lead);
		registerItemSafe(ingot_bismuth);
		registerItemSafe(ingot_arsenic);
		registerItemSafe(ingot_calcium);
		registerItemSafe(ingot_cadmium);
		registerItemSafe(ingot_tantalium);
		registerItemSafe(ingot_silicon);
		registerItemSafe(ingot_niobium);
		registerItemSafe(ingot_beryllium);
		registerItemSafe(ingot_cobalt);
		registerItemSafe(ingot_boron);
		registerItemSafe(ingot_graphite);
		registerItemSafe(ingot_firebrick);
		registerItemSafe(ingot_dura_steel);
		registerItemSafe(ingot_polymer);
		registerItemSafe(ingot_bakelite);
		registerItemSafe(ingot_biorubber);
		registerItemSafe(ingot_rubber);
		registerItemSafe(ingot_pc);
		registerItemSafe(ingot_pvc);
		registerItemSafe(ingot_mud);
		registerItemSafe(ingot_cft);
		registerItemSafe(ingot_schraranium);
		registerItemSafe(ingot_schrabidium);
		registerItemSafe(ingot_schrabidate);
		registerItemSafe(ingot_magnetized_tungsten);
		registerItemSafe(ingot_combine_steel);
		registerItemSafe(ingot_solinium);
		registerItemSafe(ingot_gh336);
		registerItemSafe(ingot_uranium_fuel);
		registerItemSafe(ingot_thorium_fuel);
		registerItemSafe(ingot_plutonium_fuel);
		registerItemSafe(ingot_neptunium_fuel);
		registerItemSafe(ingot_mox_fuel);
		registerItemSafe(ingot_americium_fuel);
		registerItemSafe(ingot_schrabidium_fuel);
		registerItemSafe(ingot_hes);
		registerItemSafe(ingot_les);
		registerItemSafe(ingot_australium);
		registerItemSafe(ingot_lanthanium);
		registerItemSafe(ingot_actinium);
		registerItemSafe(ingot_desh);
		registerItemSafe(ingot_ferrouranium);
		registerItemSafe(ingot_starmetal);
		registerItemSafe(ingot_gunmetal);
		registerItemSafe(ingot_weaponsteel);
		registerItemSafe(ingot_saturnite);
		registerItemSafe(ingot_euphemium);
		registerItemSafe(ingot_dineutronium);
		registerItemSafe(ingot_electronium);
		registerItemSafe(ingot_smore);
		registerItemSafe(ingot_osmiridium);

		//Meteorite Ingots
		registerItemSafe(ingot_steel_dusted);
		registerItemSafe(ingot_chainsteel);
		registerItemSafe(ingot_meteorite);
		registerItemSafe(ingot_meteorite_forged);
		registerItemSafe(blade_meteorite);

		//Misc Ingots
		registerItemSafe(ingot_phosphorus);
		registerItemSafe(lithium);
		registerItemSafe(ingot_zirconium);
		registerItemSafe(ingot_semtex);
		registerItemSafe(ingot_c4);
		registerItemSafe(oil_tar);
		registerItemSafe(solid_fuel);
		registerItemSafe(solid_fuel_presto);
		registerItemSafe(solid_fuel_presto_triplet);
		registerItemSafe(solid_fuel_bf);
		registerItemSafe(solid_fuel_presto_bf);
		registerItemSafe(solid_fuel_presto_triplet_bf);
		registerItemSafe(rocket_fuel);
		registerItemSafe(ingot_fiberglass);
		registerItemSafe(ingot_asbestos);
		registerItemSafe(ingot_raw);

		//Billets
		registerItemSafe(billet_uranium);
		registerItemSafe(billet_u233);
		registerItemSafe(billet_u235);
		registerItemSafe(billet_u238);
		registerItemSafe(billet_th232);
		registerItemSafe(billet_plutonium);
		registerItemSafe(billet_pu238);
		registerItemSafe(billet_pu239);
		registerItemSafe(billet_pu240);
		registerItemSafe(billet_pu241);
		registerItemSafe(billet_pu_mix);
		registerItemSafe(billet_am241);
		registerItemSafe(billet_am242);
		registerItemSafe(billet_am_mix);
		registerItemSafe(billet_neptunium);
		registerItemSafe(billet_polonium);
		registerItemSafe(billet_technetium);
		registerItemSafe(billet_cobalt);
		registerItemSafe(billet_co60);
		registerItemSafe(billet_sr90);
		registerItemSafe(billet_au198);
		registerItemSafe(billet_pb209);
		registerItemSafe(billet_ra226);
		registerItemSafe(billet_actinium);
		registerItemSafe(billet_schrabidium);
		registerItemSafe(billet_solinium);
		registerItemSafe(billet_gh336);
		registerItemSafe(billet_australium);
		registerItemSafe(billet_australium_lesser);
		registerItemSafe(billet_australium_greater);
		registerItemSafe(billet_uranium_fuel);
		registerItemSafe(billet_thorium_fuel);
		registerItemSafe(billet_plutonium_fuel);
		registerItemSafe(billet_neptunium_fuel);
		registerItemSafe(billet_mox_fuel);
		registerItemSafe(billet_americium_fuel);
		registerItemSafe(billet_les);
		registerItemSafe(billet_schrabidium_fuel);
		registerItemSafe(billet_hes);
		registerItemSafe(billet_po210be);
		registerItemSafe(billet_ra226be);
		registerItemSafe(billet_pu238be);
		registerItemSafe(billet_beryllium);
		registerItemSafe(billet_bismuth);
		registerItemSafe(billet_silicon);
		registerItemSafe(billet_zirconium);
		registerItemSafe(billet_zfb_bismuth);
		registerItemSafe(billet_zfb_pu241);
		registerItemSafe(billet_zfb_am_mix);
		registerItemSafe(billet_yharonite);
		registerItemSafe(billet_balefire_gold);
		registerItemSafe(billet_flashlead);
		registerItemSafe(billet_nuclear_waste);

		//Dusts & Other
		registerItemSafe(cinnebar);
		registerItemSafe(nugget_mercury);
		registerItemSafe(ingot_mercury);
		registerItemSafe(bottle_mercury);
		registerItemSafe(coke);
		registerItemSafe(lignite);
		registerItemSafe(coal_infernal);
		registerItemSafe(briquette);
		registerItemSafe(sulfur);
		registerItemSafe(niter);
		registerItemSafe(nitra);
		registerItemSafe(nitra_small);
		registerItemSafe(fluorite);
		registerItemSafe(powder_coal);
		registerItemSafe(powder_coal_tiny);
		registerItemSafe(powder_iron);
		registerItemSafe(powder_gold);
		registerItemSafe(powder_lapis);
		registerItemSafe(powder_quartz);
		registerItemSafe(powder_diamond);
		registerItemSafe(powder_emerald);
		registerItemSafe(powder_uranium);
		registerItemSafe(powder_plutonium);
		registerItemSafe(powder_neptunium);
		registerItemSafe(powder_polonium);
		registerItemSafe(powder_co60);
		registerItemSafe(powder_sr90);
		registerItemSafe(powder_sr90_tiny);
		registerItemSafe(powder_i131);
		registerItemSafe(powder_i131_tiny);
		registerItemSafe(powder_xe135);
		registerItemSafe(powder_xe135_tiny);
		registerItemSafe(powder_cs137);
		registerItemSafe(powder_cs137_tiny);
		registerItemSafe(powder_au198);
		registerItemSafe(powder_ra226);
		registerItemSafe(powder_at209);
		registerItemSafe(powder_titanium);
		registerItemSafe(powder_copper);
		registerItemSafe(powder_red_copper);
		registerItemSafe(powder_advanced_alloy);
		registerItemSafe(powder_tungsten);
		registerItemSafe(powder_aluminium);
		registerItemSafe(powder_steel);
		registerItemSafe(powder_steel_tiny);
		registerItemSafe(powder_tcalloy);
		registerItemSafe(powder_lead);
		registerItemSafe(powder_bismuth);
		registerItemSafe(powder_calcium);
		registerItemSafe(powder_cadmium);
		registerItemSafe(powder_coltan_ore);
		registerItemSafe(powder_coltan);
		registerItemSafe(powder_tantalium);
		registerItemSafe(powder_tektite);
		registerItemSafe(powder_paleogenite);
		registerItemSafe(powder_paleogenite_tiny);
		registerItemSafe(powder_impure_osmiridium);
		registerItemSafe(powder_borax);
		registerItemSafe(powder_chlorocalcite);
		registerItemSafe(powder_molysite);
		registerItemSafe(powder_yellowcake);
		registerItemSafe(powder_beryllium);
		registerItemSafe(powder_dura_steel);
		registerItemSafe(powder_polymer);
		registerItemSafe(powder_bakelite);
		registerItemSafe(powder_schrabidium);
		registerItemSafe(powder_schrabidate);
		registerItemSafe(powder_magnetized_tungsten);
		registerItemSafe(powder_chlorophyte);
		registerItemSafe(powder_combine_steel);
		registerItemSafe(powder_lithium);
		registerItemSafe(powder_lithium_tiny);
		registerItemSafe(powder_zirconium);
		registerItemSafe(powder_sodium);
		registerItemSafe(powder_lignite);
		registerItemSafe(powder_iodine);
		registerItemSafe(powder_thorium);
		registerItemSafe(powder_neodymium);
		registerItemSafe(powder_neodymium_tiny);
		registerItemSafe(powder_astatine);
		registerItemSafe(powder_caesium);
		registerItemSafe(powder_australium);
		registerItemSafe(powder_strontium);
		registerItemSafe(powder_cobalt);
		registerItemSafe(powder_cobalt_tiny);
		registerItemSafe(powder_bromine);
		registerItemSafe(powder_niobium);
		registerItemSafe(powder_niobium_tiny);
		registerItemSafe(powder_tennessine);
		registerItemSafe(powder_cerium);
		registerItemSafe(powder_cerium_tiny);
		registerItemSafe(powder_lanthanium);
		registerItemSafe(powder_lanthanium_tiny);
		registerItemSafe(powder_actinium);
		registerItemSafe(powder_actinium_tiny);
		registerItemSafe(powder_boron);
		registerItemSafe(powder_boron_tiny);
		registerItemSafe(powder_asbestos);
		registerItemSafe(powder_magic);
		registerItemSafe(powder_sawdust);
		registerItemSafe(powder_flux);
		registerItemSafe(powder_fertilizer);
		registerItemSafe(powder_balefire);
		registerItemSafe(powder_semtex_mix);
		registerItemSafe(powder_desh_mix);
		registerItemSafe(powder_desh_ready);
		registerItemSafe(powder_desh);
		registerItemSafe(powder_nitan_mix);
		registerItemSafe(powder_spark_mix);
		registerItemSafe(powder_meteorite);
		registerItemSafe(powder_meteorite_tiny);
		registerItemSafe(powder_euphemium);
		registerItemSafe(powder_dineutronium);
		registerItemSafe(dust);
		registerItemSafe(dust_tiny);
		registerItemSafe(fallout);
		registerItemSafe(powder_ash);
		registerItemSafe(powder_limestone);
		registerItemSafe(powder_cement);

		//Powders
		registerItemSafe(powder_fire);
		registerItemSafe(powder_ice);
		registerItemSafe(powder_poison);
		registerItemSafe(powder_thermite);
		registerItemSafe(powder_power);
		registerItemSafe(cordite);
		registerItemSafe(ballistite);
		registerItemSafe(ball_dynamite);
		registerItemSafe(ball_tnt);
		registerItemSafe(ball_tatb);
		registerItemSafe(ball_resin);
		registerItemSafe(ball_fireclay);

		//Ores
		registerItemSafe(ore_bedrock);
		registerItemSafe(ore_centrifuged);
		registerItemSafe(ore_cleaned);
		registerItemSafe(ore_separated);
		registerItemSafe(ore_purified);
		registerItemSafe(ore_nitrated);
		registerItemSafe(ore_nitrocrystalline);
		registerItemSafe(ore_deepcleaned);
		registerItemSafe(ore_seared);
		registerItemSafe(ore_enriched);
		registerItemSafe(ore_byproduct);
		registerItemSafe(bedrock_ore_base);
		registerItemSafe(bedrock_ore);
		registerItemSafe(bedrock_ore_fragment);

		//Crystals
		registerItemSafe(crystal_coal);
		registerItemSafe(crystal_iron);
		registerItemSafe(crystal_gold);
		registerItemSafe(crystal_redstone);
		registerItemSafe(crystal_lapis);
		registerItemSafe(crystal_diamond);
		registerItemSafe(crystal_uranium);
		registerItemSafe(crystal_thorium);
		registerItemSafe(crystal_plutonium);
		registerItemSafe(crystal_titanium);
		registerItemSafe(crystal_sulfur);
		registerItemSafe(crystal_niter);
		registerItemSafe(crystal_copper);
		registerItemSafe(crystal_tungsten);
		registerItemSafe(crystal_aluminium);
		registerItemSafe(crystal_fluorite);
		registerItemSafe(crystal_beryllium);
		registerItemSafe(crystal_lead);
		registerItemSafe(crystal_schraranium);
		registerItemSafe(crystal_schrabidium);
		registerItemSafe(crystal_rare);
		registerItemSafe(crystal_phosphorus);
		registerItemSafe(crystal_lithium);
		registerItemSafe(crystal_cobalt);
		registerItemSafe(crystal_starmetal);
		registerItemSafe(crystal_cinnebar);
		registerItemSafe(crystal_trixite);
		registerItemSafe(crystal_osmiridium);
		registerItemSafe(gem_sodalite);
		registerItemSafe(gem_tantalium);
		registerItemSafe(gem_volcanic);
		registerItemSafe(gem_rad);
		registerItemSafe(gem_alexandrite);

		//Fragments
		registerItemSafe(fragment_neodymium);
		registerItemSafe(fragment_cobalt);
		registerItemSafe(fragment_niobium);
		registerItemSafe(fragment_cerium);
		registerItemSafe(fragment_lanthanium);
		registerItemSafe(fragment_actinium);
		registerItemSafe(fragment_boron);
		registerItemSafe(fragment_meteorite);
		registerItemSafe(fragment_coltan);
		registerItemSafe(chunk_ore);

		//Things that look like rotten flesh but aren't
		registerItemSafe(biomass);
		registerItemSafe(biomass_compressed);
		//delicious!
		registerItemSafe(bio_wafer);

		//Nuggets
		registerItemSafe(nugget_uranium);
		registerItemSafe(nugget_u233);
		registerItemSafe(nugget_u235);
		registerItemSafe(nugget_u238);
		registerItemSafe(nugget_th232);
		registerItemSafe(nugget_plutonium);
		registerItemSafe(nugget_pu238);
		registerItemSafe(nugget_pu239);
		registerItemSafe(nugget_pu240);
		registerItemSafe(nugget_pu241);
		registerItemSafe(nugget_pu_mix);
		registerItemSafe(nugget_am241);
		registerItemSafe(nugget_am242);
		registerItemSafe(nugget_am_mix);
		registerItemSafe(nugget_neptunium);
		registerItemSafe(nugget_polonium);
		registerItemSafe(nugget_cobalt);
		registerItemSafe(nugget_co60);
		registerItemSafe(nugget_sr90);
		registerItemSafe(nugget_technetium);
		registerItemSafe(nugget_au198);
		registerItemSafe(nugget_pb209);
		registerItemSafe(nugget_ra226);
		registerItemSafe(nugget_actinium);
		registerItemSafe(nugget_lead);
		registerItemSafe(nugget_bismuth);
		registerItemSafe(nugget_arsenic);
		registerItemSafe(nugget_tantalium);
		registerItemSafe(nugget_silicon);
		registerItemSafe(nugget_niobium);
		registerItemSafe(nugget_beryllium);
		registerItemSafe(nugget_schrabidium);
		registerItemSafe(nugget_solinium);
		registerItemSafe(nugget_gh336);
		registerItemSafe(nugget_uranium_fuel);
		registerItemSafe(nugget_thorium_fuel);
		registerItemSafe(nugget_plutonium_fuel);
		registerItemSafe(nugget_neptunium_fuel);
		registerItemSafe(nugget_mox_fuel);
		registerItemSafe(nugget_americium_fuel);
		registerItemSafe(nugget_schrabidium_fuel);
		registerItemSafe(nugget_hes);
		registerItemSafe(nugget_les);
		registerItemSafe(nugget_zirconium);
		registerItemSafe(nugget_australium);
		registerItemSafe(nugget_australium_lesser);
		registerItemSafe(nugget_australium_greater);
		registerItemSafe(nugget_desh);
		registerItemSafe(nugget_euphemium);
		registerItemSafe(nugget_dineutronium);
		registerItemSafe(nugget_osmiridium);

		//Plates
		registerItemSafe(plate_iron);
		registerItemSafe(plate_gold);
		registerItemSafe(plate_titanium);
		registerItemSafe(plate_aluminium);
		registerItemSafe(plate_steel);
		registerItemSafe(plate_lead);
		registerItemSafe(plate_copper);
		registerItemSafe(plate_advanced_alloy);
		registerItemSafe(plate_dura_steel);
		registerItemSafe(neutron_reflector);
		registerItemSafe(plate_schrabidium);
		registerItemSafe(plate_combine_steel);
		registerItemSafe(plate_mixed);
		registerItemSafe(plate_gunmetal);
		registerItemSafe(plate_weaponsteel);
		registerItemSafe(plate_saturnite);
		registerItemSafe(plate_paa);
		registerItemSafe(plate_polymer);
		registerItemSafe(plate_kevlar);
		registerItemSafe(plate_dalekanium);
		registerItemSafe(plate_desh);
		registerItemSafe(plate_bismuth);
		registerItemSafe(plate_euphemium);
		registerItemSafe(plate_dineutronium);

		//Armor Plates
		registerItemSafe(plate_armor_titanium);
		registerItemSafe(plate_armor_ajr);
		registerItemSafe(plate_armor_hev);
		registerItemSafe(plate_armor_lunar);
		registerItemSafe(plate_armor_fau);
		registerItemSafe(plate_armor_dnt);

		//Heavy/Cast Plate
		registerItemSafe(plate_cast);
		registerItemSafe(plate_welded);
		registerItemSafe(shell);
		registerItemSafe(pipe);

		//Bolts
		registerItemSafe(bolt);
		registerItemSafe(bolt_spike);

		//Cloth
		registerItemSafe(hazmat_cloth);
		registerItemSafe(hazmat_cloth_red);
		registerItemSafe(hazmat_cloth_grey);
		registerItemSafe(asbestos_cloth);
		registerItemSafe(rag);
		registerItemSafe(rag_damp);
		registerItemSafe(rag_piss);
		registerItemSafe(filter_coal);

		//Wires
		registerItemSafe(wire_fine);
		registerItemSafe(wire_dense);

		//Parts
		registerItemSafe(coil_copper);
		registerItemSafe(coil_copper_torus);
		registerItemSafe(coil_advanced_alloy);
		registerItemSafe(coil_advanced_torus);
		registerItemSafe(coil_gold);
		registerItemSafe(coil_gold_torus);
		registerItemSafe(coil_tungsten);
		registerItemSafe(coil_magnetized_tungsten);
		registerItemSafe(safety_fuse);
		registerItemSafe(tank_steel);
		registerItemSafe(motor);
		registerItemSafe(motor_desh);
		registerItemSafe(motor_bismuth);
		registerItemSafe(centrifuge_element);
		registerItemSafe(reactor_core);
		registerItemSafe(rtg_unit);
		registerItemSafe(pipes_steel);
		registerItemSafe(drill_titanium);
		registerItemSafe(photo_panel);
		registerItemSafe(chlorine_pinwheel);
		registerItemSafe(ring_starmetal);
		registerItemSafe(deuterium_filter);
		registerItemSafe(chemical_dye);
		registerItemSafe(crayon);
		registerItemSafe(part_generic);
		registerItemSafe(item_expensive);
		registerItemSafe(item_secret);
		registerItemSafe(ingot_metal);
		registerItemSafe(parts_legendary);
		registerItemSafe(gear_large);
		registerItemSafe(sawblade);
		registerItemSafe(part_barrel_light);
		registerItemSafe(part_barrel_heavy);
		registerItemSafe(part_receiver_light);
		registerItemSafe(part_receiver_heavy);
		registerItemSafe(part_mechanism);
		registerItemSafe(part_stock);
		registerItemSafe(part_grip);

		//Plant Products
		registerItemSafe(plant_item);

		//Teleporter Parts
		registerItemSafe(entanglement_kit);

		//Bomb Parts
		registerItemSafe(fins_flat);
		registerItemSafe(fins_small_steel);
		registerItemSafe(fins_big_steel);
		registerItemSafe(fins_tri_steel);
		registerItemSafe(fins_quad_titanium);
		registerItemSafe(sphere_steel);
		registerItemSafe(pedestal_steel);
		registerItemSafe(dysfunctional_reactor);
		registerItemSafe(blade_titanium);
		registerItemSafe(blade_tungsten);
		registerItemSafe(turbine_titanium);
		registerItemSafe(turbine_tungsten);
		registerItemSafe(flywheel_beryllium);
		registerItemSafe(ducttape);
		registerItemSafe(catalyst_clay);
		registerItemSafe(missile_assembly);
		registerItemSafe(warhead_generic_small);
		registerItemSafe(warhead_generic_medium);
		registerItemSafe(warhead_generic_large);
		registerItemSafe(warhead_incendiary_small);
		registerItemSafe(warhead_incendiary_medium);
		registerItemSafe(warhead_incendiary_large);
		registerItemSafe(warhead_cluster_small);
		registerItemSafe(warhead_cluster_medium);
		registerItemSafe(warhead_cluster_large);
		registerItemSafe(warhead_buster_small);
		registerItemSafe(warhead_buster_medium);
		registerItemSafe(warhead_buster_large);
		registerItemSafe(warhead_nuclear);
		registerItemSafe(warhead_mirv);
		registerItemSafe(warhead_volcano);
		registerItemSafe(fuel_tank_small);
		registerItemSafe(fuel_tank_medium);
		registerItemSafe(fuel_tank_large);
		registerItemSafe(thruster_small);
		registerItemSafe(thruster_medium);
		registerItemSafe(thruster_large);
		registerItemSafe(thruster_nuclear);
		registerItemSafe(sat_base);
		registerItemSafe(sat_head_mapper);
		registerItemSafe(sat_head_scanner);
		registerItemSafe(sat_head_radar);
		registerItemSafe(sat_head_laser);
		registerItemSafe(sat_head_resonator);
		registerItemSafe(seg_10);
		registerItemSafe(seg_15);
		registerItemSafe(seg_20);

		//Chopper parts
		registerItemSafe(combine_scrap);

		//Hammer Parts
		registerItemSafe(shimmer_head);
		registerItemSafe(shimmer_axe_head);
		registerItemSafe(shimmer_handle);

		//Circuits
		registerItemSafe(circuit);
		registerItemSafe(crt_display);
		registerItemSafe(circuit_star_piece);
		registerItemSafe(circuit_star_component);
		registerItemSafe(circuit_star);

		//Casing
		registerItemSafe(casing);

		//Bullet Assemblies
		registerItemSafe(assembly_nuke);

		//Wiring
		registerItemSafe(wiring_red_copper);

		//Flame War in a Box
		registerItemSafe(flame_pony);
		registerItemSafe(flame_conspiracy);
		registerItemSafe(flame_politics);
		registerItemSafe(flame_opinion);

		//Pellets
		registerItemSafe(pellet_rtg_radium);
		registerItemSafe(pellet_rtg_weak);
		registerItemSafe(pellet_rtg);
		registerItemSafe(pellet_rtg_strontium);
		registerItemSafe(pellet_rtg_cobalt);
		registerItemSafe(pellet_rtg_actinium);
		registerItemSafe(pellet_rtg_polonium);
		registerItemSafe(pellet_rtg_americium);
		registerItemSafe(pellet_rtg_gold);
		registerItemSafe(pellet_rtg_lead);
		registerItemSafe(pellet_rtg_depleted);
		registerItemSafe(tritium_deuterium_cake);
		registerItemSafe(pellet_cluster);
		registerItemSafe(pellet_buckshot);
		registerItemSafe(pellet_charged);
		registerItemSafe(pellet_gas);
		registerItemSafe(magnetron);

		//Engine Pieces
		registerItemSafe(piston_selenium);
		registerItemSafe(piston_set);
		registerItemSafe(drillbit);

		//Cells
		registerItemSafe(cell_empty);
		registerItemSafe(cell_uf6);
		registerItemSafe(cell_puf6);
		registerItemSafe(cell_deuterium);
		registerItemSafe(cell_tritium);
		registerItemSafe(cell_sas3);
		registerItemSafe(cell_antimatter);
		registerItemSafe(cell_anti_schrabidium);
		registerItemSafe(cell_balefire);

		//DEMON CORE
		registerItemSafe(demon_core_open);
		registerItemSafe(demon_core_closed);

		//PA
		registerItemSafe(pa_coil);

		//Particle Containers
		registerItemSafe(particle_empty);
		registerItemSafe(particle_hydrogen);
		registerItemSafe(particle_copper);
		registerItemSafe(particle_lead);
		registerItemSafe(particle_aproton);
		registerItemSafe(particle_aelectron);
		registerItemSafe(particle_amat);
		registerItemSafe(particle_aschrab);
		registerItemSafe(particle_higgs);
		registerItemSafe(particle_muon);
		registerItemSafe(particle_tachyon);
		registerItemSafe(particle_strange);
		registerItemSafe(particle_dark);
		registerItemSafe(particle_sparkticle);
		registerItemSafe(particle_digamma);
		registerItemSafe(particle_lutece);

		//Singularities, black holes and other cosmic horrors
		registerItemSafe(singularity);
		registerItemSafe(singularity_counter_resonant);
		registerItemSafe(singularity_super_heated);
		registerItemSafe(black_hole);
		registerItemSafe(singularity_spark);
		registerItemSafe(crystal_xen);
		registerItemSafe(pellet_antimatter);

		//Infinite Tanks
		registerItemSafe(inf_water);
		registerItemSafe(inf_water_mk2);

		//Canisters
		registerItemSafe(fuel_additive);
		registerItemSafe(canister_empty);
		registerItemSafe(canister_full);
		registerItemSafe(canister_napalm);

		//Gas Tanks
		registerItemSafe(gas_empty);
		registerItemSafe(gas_full);

		//Universal Tank
		registerItemSafe(fluid_tank_empty);
		registerItemSafe(fluid_tank_full);
		registerItemSafe(fluid_tank_lead_empty);
		registerItemSafe(fluid_tank_lead_full);
		registerItemSafe(fluid_barrel_empty);
		registerItemSafe(fluid_barrel_full);
		registerItemSafe(fluid_barrel_infinite);

		//Packaged fluids
		registerItemSafe(fluid_pack_empty);
		registerItemSafe(fluid_pack_full);

		//Pipette
		registerItemSafe(pipette);
		registerItemSafe(pipette_boron);
		registerItemSafe(pipette_laboratory);

		//Siphon
		registerItemSafe(siphon);

		//Batteries
		registerItemSafe(battery_spark);
		registerItemSafe(battery_trixite);
		
		registerItemSafe(battery_pack);
		registerItemSafe(battery_sc);
		registerItemSafe(battery_creative);
		registerItemSafe(cube_power);

		registerItemSafe(battery_potato);
		registerItemSafe(battery_potatos);
		registerItemSafe(hev_battery);
		registerItemSafe(fusion_core);
		registerItemSafe(energy_core);

		//Folders
		registerItemSafe(blueprints);
		registerItemSafe(blueprint_folder);
		registerItemSafe(template_folder);
		registerItemSafe(bobmazon);
		registerItemSafe(bobmazon_hidden);

		//Hydraulic Press Stamps
		registerItemSafe(stamp_stone_flat);
		registerItemSafe(stamp_stone_plate);
		registerItemSafe(stamp_stone_wire);
		registerItemSafe(stamp_stone_circuit);
		registerItemSafe(stamp_iron_flat);
		registerItemSafe(stamp_iron_plate);
		registerItemSafe(stamp_iron_wire);
		registerItemSafe(stamp_iron_circuit);
		registerItemSafe(stamp_steel_flat);
		registerItemSafe(stamp_steel_plate);
		registerItemSafe(stamp_steel_wire);
		registerItemSafe(stamp_steel_circuit);
		registerItemSafe(stamp_titanium_flat);
		registerItemSafe(stamp_titanium_plate);
		registerItemSafe(stamp_titanium_wire);
		registerItemSafe(stamp_titanium_circuit);
		registerItemSafe(stamp_obsidian_flat);
		registerItemSafe(stamp_obsidian_plate);
		registerItemSafe(stamp_obsidian_wire);
		registerItemSafe(stamp_obsidian_circuit);
		registerItemSafe(stamp_desh_flat);
		registerItemSafe(stamp_desh_plate);
		registerItemSafe(stamp_desh_wire);
		registerItemSafe(stamp_desh_circuit);
		registerItemSafe(stamp_357);
		registerItemSafe(stamp_44);
		registerItemSafe(stamp_9);
		registerItemSafe(stamp_50);

		registerItemSafe(stamp_desh_357);
		registerItemSafe(stamp_desh_44);
		registerItemSafe(stamp_desh_9);
		registerItemSafe(stamp_desh_50);
		registerItemSafe(stamp_book);

		//Molds
		registerItemSafe(mold_base);
		registerItemSafe(mold);
		registerItemSafe(scraps);

		//Machine Upgrades
		registerItemSafe(upgrade_muffler);
		registerItemSafe(upgrade_template);
		registerItemSafe(upgrade_speed_1);
		registerItemSafe(upgrade_speed_2);
		registerItemSafe(upgrade_speed_3);
		registerItemSafe(upgrade_effect_1);
		registerItemSafe(upgrade_effect_2);
		registerItemSafe(upgrade_effect_3);
		registerItemSafe(upgrade_power_1);
		registerItemSafe(upgrade_power_2);
		registerItemSafe(upgrade_power_3);
		registerItemSafe(upgrade_fortune_1);
		registerItemSafe(upgrade_fortune_2);
		registerItemSafe(upgrade_fortune_3);
		registerItemSafe(upgrade_afterburn_1);
		registerItemSafe(upgrade_afterburn_2);
		registerItemSafe(upgrade_afterburn_3);
		registerItemSafe(upgrade_overdrive_1);
		registerItemSafe(upgrade_overdrive_2);
		registerItemSafe(upgrade_overdrive_3);
		registerItemSafe(upgrade_radius);
		registerItemSafe(upgrade_health);
		registerItemSafe(upgrade_smelter);
		registerItemSafe(upgrade_shredder);
		registerItemSafe(upgrade_centrifuge);
		registerItemSafe(upgrade_crystallizer);
		registerItemSafe(upgrade_nullifier);
		registerItemSafe(upgrade_screm);
		registerItemSafe(upgrade_gc_speed);
		registerItemSafe(upgrade_5g);
		registerItemSafe(upgrade_stack);
		registerItemSafe(upgrade_ejector);

		//Machine Templates
		registerItemSafe(siren_track);
		registerItemSafe(fluid_identifier_multi);
		registerItemSafe(fluid_icon);
		registerItemSafe(fluid_duct);
		registerItemSafe(assembly_template);
		registerItemSafe(chemistry_template);
		registerItemSafe(chemistry_icon);
		registerItemSafe(crucible_template);

		//Machine Items
		registerItemSafe(fuse);
		registerItemSafe(redcoil_capacitor);
		registerItemSafe(euphemium_capacitor);
		registerItemSafe(screwdriver);
		registerItemSafe(screwdriver_desh);
		registerItemSafe(hand_drill);
		registerItemSafe(hand_drill_desh);
		registerItemSafe(chemistry_set);
		registerItemSafe(chemistry_set_boron);
		registerItemSafe(blowtorch);
		registerItemSafe(acetylene_torch);
		registerItemSafe(boltgun);
		registerItemSafe(arc_electrode);
		registerItemSafe(arc_electrode_burnt);

		//Particle Collider Fuel
		registerItemSafe(part_lithium);
		registerItemSafe(part_beryllium);
		registerItemSafe(part_carbon);
		registerItemSafe(part_copper);
		registerItemSafe(part_plutonium);

		//FEL laser crystals
		registerItemSafe(laser_crystal_co2);
		registerItemSafe(laser_crystal_bismuth);
		registerItemSafe(laser_crystal_cmb);
		registerItemSafe(laser_crystal_dnt);
		registerItemSafe(laser_crystal_digamma);

		//Catalyst Rune Sigils
		registerItemSafe(rune_blank);
		registerItemSafe(rune_isa);
		registerItemSafe(rune_dagaz);
		registerItemSafe(rune_hagalaz);
		registerItemSafe(rune_jera);
		registerItemSafe(rune_thurisaz);

		//AMS Catalysts
		registerItemSafe(ams_catalyst_blank);
		registerItemSafe(ams_catalyst_aluminium);
		registerItemSafe(ams_catalyst_beryllium);
		registerItemSafe(ams_catalyst_caesium);
		registerItemSafe(ams_catalyst_cerium);
		registerItemSafe(ams_catalyst_cobalt);
		registerItemSafe(ams_catalyst_copper);
		registerItemSafe(ams_catalyst_euphemium);
		registerItemSafe(ams_catalyst_dineutronium);
		registerItemSafe(ams_catalyst_iron);
		registerItemSafe(ams_catalyst_lithium);
		registerItemSafe(ams_catalyst_niobium);
		registerItemSafe(ams_catalyst_schrabidium);
		registerItemSafe(ams_catalyst_strontium);
		registerItemSafe(ams_catalyst_thorium);
		registerItemSafe(ams_catalyst_tungsten);

		//Shredder Blades
		registerItemSafe(blades_steel);
		registerItemSafe(blades_titanium);
		registerItemSafe(blades_advanced_alloy);
		registerItemSafe(blades_desh);

		//Generator Stuff
		registerItemSafe(thermo_element);
		registerItemSafe(catalytic_converter);

		//AMS Components
		registerItemSafe(ams_lens);
		registerItemSafe(ams_core_sing);
		registerItemSafe(ams_core_wormhole);
		registerItemSafe(ams_core_eyeofharmony);
		registerItemSafe(ams_core_thingy);

		//Fusion Shields
		registerItemSafe(fusion_shield_tungsten);
		registerItemSafe(fusion_shield_desh);
		registerItemSafe(fusion_shield_chlorophyte);
		registerItemSafe(fusion_shield_vaporwave);

		//Breeding Rods
		registerItemSafe(rod_empty);
		registerItemSafe(rod);
		registerItemSafe(rod_dual_empty);
		registerItemSafe(rod_dual);
		registerItemSafe(rod_quad_empty);
		registerItemSafe(rod_quad);

		//ZIRNOX parts
		registerItemSafe(rod_zirnox_empty);
		registerItemSafe(rod_zirnox_tritium);
		registerItemSafe(rod_zirnox);

		registerItemSafe(rod_zirnox_natural_uranium_fuel_depleted);
		registerItemSafe(rod_zirnox_uranium_fuel_depleted);
		registerItemSafe(rod_zirnox_thorium_fuel_depleted);
		registerItemSafe(rod_zirnox_mox_fuel_depleted);
		registerItemSafe(rod_zirnox_plutonium_fuel_depleted);
		registerItemSafe(rod_zirnox_u233_fuel_depleted);
		registerItemSafe(rod_zirnox_u235_fuel_depleted);
		registerItemSafe(rod_zirnox_les_fuel_depleted);
		registerItemSafe(rod_zirnox_zfb_mox_depleted);

		//Depleted Fuel
		registerItemSafe(waste_natural_uranium);
		registerItemSafe(waste_uranium);
		registerItemSafe(waste_thorium);
		registerItemSafe(waste_mox);
		registerItemSafe(waste_plutonium);
		registerItemSafe(waste_u233);
		registerItemSafe(waste_u235);
		registerItemSafe(waste_schrabidium);
		registerItemSafe(waste_zfb_mox);

		registerItemSafe(waste_plate_u233);
		registerItemSafe(waste_plate_u235);
		registerItemSafe(waste_plate_mox);
		registerItemSafe(waste_plate_pu239);
		registerItemSafe(waste_plate_ra226be);
		registerItemSafe(waste_plate_sa326);
		registerItemSafe(waste_plate_pu238be);

		//Pile parts
		registerItemSafe(pile_rod_uranium);
		registerItemSafe(pile_rod_pu239);
		registerItemSafe(pile_rod_plutonium);
		registerItemSafe(pile_rod_source);
		registerItemSafe(pile_rod_boron);
		registerItemSafe(pile_rod_lithium);
		registerItemSafe(pile_rod_detector);

		//Plate Fuels
		registerItemSafe(plate_fuel_u233);
		registerItemSafe(plate_fuel_u235);
		registerItemSafe(plate_fuel_mox);
		registerItemSafe(plate_fuel_pu239);
		registerItemSafe(plate_fuel_sa326);
		registerItemSafe(plate_fuel_ra226be);
		registerItemSafe(plate_fuel_pu238be);

		//PWR Parts
		registerItemSafe(pwr_fuel);
		registerItemSafe(pwr_fuel_hot);
		registerItemSafe(pwr_fuel_depleted);
		registerItemSafe(pwr_printer);

		//RBMK parts
		registerItemSafe(rbmk_lid);
		registerItemSafe(rbmk_lid_glass);
		registerItemSafe(rbmk_fuel_empty);
		registerItemSafe(rbmk_fuel_ueu);
		registerItemSafe(rbmk_fuel_meu);
		registerItemSafe(rbmk_fuel_heu233);
		registerItemSafe(rbmk_fuel_heu235);
		registerItemSafe(rbmk_fuel_thmeu);
		registerItemSafe(rbmk_fuel_lep);
		registerItemSafe(rbmk_fuel_mep);
		registerItemSafe(rbmk_fuel_hep239);
		registerItemSafe(rbmk_fuel_hep241);
		registerItemSafe(rbmk_fuel_lea);
		registerItemSafe(rbmk_fuel_mea);
		registerItemSafe(rbmk_fuel_hea241);
		registerItemSafe(rbmk_fuel_hea242);
		registerItemSafe(rbmk_fuel_men);
		registerItemSafe(rbmk_fuel_hen);
		registerItemSafe(rbmk_fuel_mox);
		registerItemSafe(rbmk_fuel_les);
		registerItemSafe(rbmk_fuel_mes);
		registerItemSafe(rbmk_fuel_hes);
		registerItemSafe(rbmk_fuel_leaus);
		registerItemSafe(rbmk_fuel_heaus);
		registerItemSafe(rbmk_fuel_po210be);
		registerItemSafe(rbmk_fuel_ra226be);
		registerItemSafe(rbmk_fuel_pu238be);
		registerItemSafe(rbmk_fuel_balefire_gold);
		registerItemSafe(rbmk_fuel_flashlead);
		registerItemSafe(rbmk_fuel_balefire);
		registerItemSafe(rbmk_fuel_zfb_bismuth);
		registerItemSafe(rbmk_fuel_zfb_pu241);
		registerItemSafe(rbmk_fuel_zfb_am_mix);
		registerItemSafe(rbmk_fuel_drx);
		registerItemSafe(rbmk_fuel_test);

		registerItemSafe(rbmk_pellet_ueu);
		registerItemSafe(rbmk_pellet_meu);
		registerItemSafe(rbmk_pellet_heu233);
		registerItemSafe(rbmk_pellet_heu235);
		registerItemSafe(rbmk_pellet_thmeu);
		registerItemSafe(rbmk_pellet_lep);
		registerItemSafe(rbmk_pellet_mep);
		registerItemSafe(rbmk_pellet_hep239);
		registerItemSafe(rbmk_pellet_hep241);
		registerItemSafe(rbmk_pellet_lea);
		registerItemSafe(rbmk_pellet_mea);
		registerItemSafe(rbmk_pellet_hea241);
		registerItemSafe(rbmk_pellet_hea242);
		registerItemSafe(rbmk_pellet_men);
		registerItemSafe(rbmk_pellet_hen);
		registerItemSafe(rbmk_pellet_mox);
		registerItemSafe(rbmk_pellet_les);
		registerItemSafe(rbmk_pellet_mes);
		registerItemSafe(rbmk_pellet_hes);
		registerItemSafe(rbmk_pellet_leaus);
		registerItemSafe(rbmk_pellet_heaus);
		registerItemSafe(rbmk_pellet_po210be);
		registerItemSafe(rbmk_pellet_ra226be);
		registerItemSafe(rbmk_pellet_pu238be);
		registerItemSafe(rbmk_pellet_balefire_gold);
		registerItemSafe(rbmk_pellet_flashlead);
		registerItemSafe(rbmk_pellet_balefire);
		registerItemSafe(rbmk_pellet_zfb_bismuth);
		registerItemSafe(rbmk_pellet_zfb_pu241);
		registerItemSafe(rbmk_pellet_zfb_am_mix);
		registerItemSafe(rbmk_pellet_drx);

		registerItemSafe(watz_pellet);
		registerItemSafe(watz_pellet_depleted);

		registerItemSafe(icf_pellet_empty);
		registerItemSafe(icf_pellet);
		registerItemSafe(icf_pellet_depleted);

		registerItemSafe(debris_graphite);
		registerItemSafe(debris_metal);
		registerItemSafe(debris_fuel);
		registerItemSafe(debris_concrete);
		registerItemSafe(debris_exchanger);
		registerItemSafe(debris_shrapnel);
		registerItemSafe(debris_element);
		registerItemSafe(undefined);

		registerItemSafe(scrap_plastic);
		registerItemSafe(scrap);
		registerItemSafe(scrap_oil);
		registerItemSafe(scrap_nuclear);
		registerItemSafe(trinitite);
		registerItemSafe(nuclear_waste_long);
		registerItemSafe(nuclear_waste_long_tiny);
		registerItemSafe(nuclear_waste_short);
		registerItemSafe(nuclear_waste_short_tiny);
		registerItemSafe(nuclear_waste_long_depleted);
		registerItemSafe(nuclear_waste_long_depleted_tiny);
		registerItemSafe(nuclear_waste_short_depleted);
		registerItemSafe(nuclear_waste_short_depleted_tiny);
		registerItemSafe(nuclear_waste);
		registerItemSafe(nuclear_waste_tiny);
		registerItemSafe(nuclear_waste_vitrified);
		registerItemSafe(nuclear_waste_vitrified_tiny);

		//Spawners
		registerItemSafe(spawn_chopper);
		registerItemSafe(spawn_worm);
		registerItemSafe(spawn_ufo);
		registerItemSafe(spawn_duck);

		//Computer Tools
		registerItemSafe(rangefinder);
		registerItemSafe(designator);
		registerItemSafe(designator_range);
		registerItemSafe(designator_manual);
		registerItemSafe(designator_arty_range);
		registerItemSafe(turret_chip);
		registerItemSafe(linker);
		registerItemSafe(reactor_sensor);
		registerItemSafe(oil_detector);
		registerItemSafe(ore_density_scanner);
		registerItemSafe(survey_scanner);
		registerItemSafe(mirror_tool);
		registerItemSafe(rbmk_tool);
		registerItemSafe(drone_linker);
		registerItemSafe(radar_linker);
		registerItemSafe(coltan_tool);
		registerItemSafe(power_net_tool);
		registerItemSafe(analysis_tool);
		registerItemSafe(coupling_tool);
		registerItemSafe(settings_tool);
		registerItemSafe(rtty_pager);
		registerItemSafe(dosimeter);
		registerItemSafe(geiger_counter);
		registerItemSafe(digamma_diagnostic);
		registerItemSafe(pollution_detector);
		registerItemSafe(containment_box);
		registerItemSafe(plastic_bag);

		registerItemSafe(ammo_bag);
		registerItemSafe(ammo_bag_infinite);
		registerItemSafe(casing_bag);

		//Keys and Locks
		registerItemSafe(key);
		registerItemSafe(key_red);
		registerItemSafe(key_red_cracked);
		registerItemSafe(key_kit);
		registerItemSafe(key_fake);
		registerItemSafe(mech_key);
		registerItemSafe(pin);
		registerItemSafe(padlock_rusty);
		registerItemSafe(padlock);
		registerItemSafe(padlock_reinforced);
		registerItemSafe(padlock_unbreakable);
		registerItemSafe(launch_code_piece);
		registerItemSafe(launch_code);
		registerItemSafe(launch_key);

		//Missiles
		//Tier 0
		registerItemSafe(missile_test);
		registerItemSafe(missile_taint);
		registerItemSafe(missile_micro);
		registerItemSafe(missile_bhole);
		registerItemSafe(missile_schrabidium);
		registerItemSafe(missile_emp);
		//Tier 1
		registerItemSafe(missile_generic);
		registerItemSafe(missile_decoy);
		registerItemSafe(missile_incendiary);
		registerItemSafe(missile_cluster);
		registerItemSafe(missile_buster);
		registerItemSafe(missile_stealth);
		registerItemSafe(missile_anti_ballistic);
		//Tier 2
		registerItemSafe(missile_strong);
		registerItemSafe(missile_incendiary_strong);
		registerItemSafe(missile_cluster_strong);
		registerItemSafe(missile_buster_strong);
		registerItemSafe(missile_emp_strong);
		//Tier 3
		registerItemSafe(missile_burst);
		registerItemSafe(missile_inferno);
		registerItemSafe(missile_rain);
		registerItemSafe(missile_drill);
		registerItemSafe(missile_shuttle);
		//Tier 4
		registerItemSafe(missile_nuclear);
		registerItemSafe(missile_nuclear_cluster);
		registerItemSafe(missile_volcano);
		registerItemSafe(missile_doomsday);
		registerItemSafe(missile_doomsday_rusted);
		//Rockets
		registerItemSafe(missile_soyuz);
		registerItemSafe(missile_soyuz_lander);
		registerItemSafe(missile_custom);

		//Missile Parts
		registerItemSafe(mp_thruster_10_kerosene);
		registerItemSafe(mp_thruster_10_solid);
		registerItemSafe(mp_thruster_10_xenon);
		registerItemSafe(mp_thruster_15_kerosene);
		registerItemSafe(mp_thruster_15_kerosene_dual);
		registerItemSafe(mp_thruster_15_kerosene_triple);
		registerItemSafe(mp_thruster_15_solid);
		registerItemSafe(mp_thruster_15_solid_hexdecuple);
		registerItemSafe(mp_thruster_15_hydrogen);
		registerItemSafe(mp_thruster_15_hydrogen_dual);
		registerItemSafe(mp_thruster_15_balefire_short);
		registerItemSafe(mp_thruster_15_balefire);
		registerItemSafe(mp_thruster_15_balefire_large);
		registerItemSafe(mp_thruster_15_balefire_large_rad);
		registerItemSafe(mp_thruster_20_kerosene);
		registerItemSafe(mp_thruster_20_kerosene_dual);
		registerItemSafe(mp_thruster_20_kerosene_triple);
		registerItemSafe(mp_thruster_20_solid);
		registerItemSafe(mp_thruster_20_solid_multi);
		registerItemSafe(mp_thruster_20_solid_multier);
		registerItemSafe(mp_stability_10_flat);
		registerItemSafe(mp_stability_10_cruise);
		registerItemSafe(mp_stability_10_space);
		registerItemSafe(mp_stability_15_flat);
		registerItemSafe(mp_stability_15_thin);
		registerItemSafe(mp_stability_15_soyuz);
		registerItemSafe(mp_stability_20_flat);
		registerItemSafe(mp_fuselage_10_kerosene);
		registerItemSafe(mp_fuselage_10_kerosene_camo);
		registerItemSafe(mp_fuselage_10_kerosene_desert);
		registerItemSafe(mp_fuselage_10_kerosene_sky);
		registerItemSafe(mp_fuselage_10_kerosene_flames);
		registerItemSafe(mp_fuselage_10_kerosene_insulation);
		registerItemSafe(mp_fuselage_10_kerosene_sleek);
		registerItemSafe(mp_fuselage_10_kerosene_metal);
		registerItemSafe(mp_fuselage_10_kerosene_taint);
		registerItemSafe(mp_fuselage_10_solid);
		registerItemSafe(mp_fuselage_10_solid_flames);
		registerItemSafe(mp_fuselage_10_solid_insulation);
		registerItemSafe(mp_fuselage_10_solid_sleek);
		registerItemSafe(mp_fuselage_10_solid_soviet_glory);
		registerItemSafe(mp_fuselage_10_solid_cathedral);
		registerItemSafe(mp_fuselage_10_solid_moonlit);
		registerItemSafe(mp_fuselage_10_solid_battery);
		registerItemSafe(mp_fuselage_10_solid_duracell);
		registerItemSafe(mp_fuselage_10_xenon);
		registerItemSafe(mp_fuselage_10_xenon_bhole);
		registerItemSafe(mp_fuselage_10_long_kerosene);
		registerItemSafe(mp_fuselage_10_long_kerosene_camo);
		registerItemSafe(mp_fuselage_10_long_kerosene_desert);
		registerItemSafe(mp_fuselage_10_long_kerosene_sky);
		registerItemSafe(mp_fuselage_10_long_kerosene_flames);
		registerItemSafe(mp_fuselage_10_long_kerosene_insulation);
		registerItemSafe(mp_fuselage_10_long_kerosene_sleek);
		registerItemSafe(mp_fuselage_10_long_kerosene_metal);
		registerItemSafe(mp_fuselage_10_long_kerosene_dash);
		registerItemSafe(mp_fuselage_10_long_kerosene_taint);
		registerItemSafe(mp_fuselage_10_long_kerosene_vap);
		registerItemSafe(mp_fuselage_10_long_solid);
		registerItemSafe(mp_fuselage_10_long_solid_flames);
		registerItemSafe(mp_fuselage_10_long_solid_insulation);
		registerItemSafe(mp_fuselage_10_long_solid_sleek);
		registerItemSafe(mp_fuselage_10_long_solid_soviet_glory);
		registerItemSafe(mp_fuselage_10_long_solid_bullet);
		registerItemSafe(mp_fuselage_10_long_solid_silvermoonlight);
		registerItemSafe(mp_fuselage_10_15_kerosene);
		registerItemSafe(mp_fuselage_10_15_solid);
		registerItemSafe(mp_fuselage_10_15_hydrogen);
		registerItemSafe(mp_fuselage_10_15_balefire);
		registerItemSafe(mp_fuselage_15_kerosene);
		registerItemSafe(mp_fuselage_15_kerosene_camo);
		registerItemSafe(mp_fuselage_15_kerosene_desert);
		registerItemSafe(mp_fuselage_15_kerosene_sky);
		registerItemSafe(mp_fuselage_15_kerosene_insulation);
		registerItemSafe(mp_fuselage_15_kerosene_metal);
		registerItemSafe(mp_fuselage_15_kerosene_decorated);
		registerItemSafe(mp_fuselage_15_kerosene_steampunk);
		registerItemSafe(mp_fuselage_15_kerosene_polite);
		registerItemSafe(mp_fuselage_15_kerosene_blackjack);
		registerItemSafe(mp_fuselage_15_kerosene_lambda);
		registerItemSafe(mp_fuselage_15_kerosene_minuteman);
		registerItemSafe(mp_fuselage_15_kerosene_pip);
		registerItemSafe(mp_fuselage_15_kerosene_taint);
		registerItemSafe(mp_fuselage_15_kerosene_yuck);
		registerItemSafe(mp_fuselage_15_solid);
		registerItemSafe(mp_fuselage_15_solid_insulation);
		registerItemSafe(mp_fuselage_15_solid_desh);
		registerItemSafe(mp_fuselage_15_solid_soviet_glory);
		registerItemSafe(mp_fuselage_15_solid_soviet_stank);
		registerItemSafe(mp_fuselage_15_solid_faust);
		registerItemSafe(mp_fuselage_15_solid_silvermoonlight);
		registerItemSafe(mp_fuselage_15_solid_snowy);
		registerItemSafe(mp_fuselage_15_solid_panorama);
		registerItemSafe(mp_fuselage_15_solid_roses);
		registerItemSafe(mp_fuselage_15_solid_mimi);
		registerItemSafe(mp_fuselage_15_hydrogen);
		registerItemSafe(mp_fuselage_15_hydrogen_cathedral);
		registerItemSafe(mp_fuselage_15_balefire);
		registerItemSafe(mp_fuselage_15_20_kerosene);
		registerItemSafe(mp_fuselage_15_20_kerosene_magnusson);
		registerItemSafe(mp_fuselage_15_20_solid);
		registerItemSafe(mp_warhead_10_he);
		registerItemSafe(mp_warhead_10_incendiary);
		registerItemSafe(mp_warhead_10_buster);
		registerItemSafe(mp_warhead_10_nuclear);
		registerItemSafe(mp_warhead_10_nuclear_large);
		registerItemSafe(mp_warhead_10_taint);
		registerItemSafe(mp_warhead_10_cloud);
		registerItemSafe(mp_warhead_15_he);
		registerItemSafe(mp_warhead_15_incendiary);
		registerItemSafe(mp_warhead_15_nuclear);
		registerItemSafe(mp_warhead_15_nuclear_shark);
		registerItemSafe(mp_warhead_15_nuclear_mimi);
		registerItemSafe(mp_warhead_15_boxcar);
		registerItemSafe(mp_warhead_15_n2);
		registerItemSafe(mp_warhead_15_balefire);
		registerItemSafe(mp_warhead_15_turbine);
		registerItemSafe(mp_chip_1);
		registerItemSafe(mp_chip_2);
		registerItemSafe(mp_chip_3);
		registerItemSafe(mp_chip_4);
		registerItemSafe(mp_chip_5);

		//Satellites
		registerItemSafe(sat_mapper);
		registerItemSafe(sat_scanner);
		registerItemSafe(sat_radar);
		registerItemSafe(sat_laser);
		registerItemSafe(sat_foeq);
		registerItemSafe(sat_resonator);
		registerItemSafe(sat_miner);
		registerItemSafe(sat_lunar_miner);
		registerItemSafe(sat_gerald);
		registerItemSafe(sat_chip);
		registerItemSafe(sat_interface);
		registerItemSafe(sat_coord);
		registerItemSafe(sat_designator);
		registerItemSafe(sat_relay);

		//Guns
		registerItemSafe(gun_b92);
		registerItemSafe(crucible);

		registerItemSafe(gun_debug);
		registerItemSafe(ammo_debug);

		GameRegistry.registerItem(gun_pepperbox, gun_pepperbox.getUnlocalizedName());
		GameRegistry.registerItem(gun_light_revolver, gun_light_revolver.getUnlocalizedName());
		GameRegistry.registerItem(gun_light_revolver_atlas, gun_light_revolver_atlas.getUnlocalizedName());
		GameRegistry.registerItem(gun_light_revolver_dani, gun_light_revolver_dani.getUnlocalizedName());
		GameRegistry.registerItem(gun_henry, gun_henry.getUnlocalizedName());
		GameRegistry.registerItem(gun_henry_lincoln, gun_henry_lincoln.getUnlocalizedName());
		GameRegistry.registerItem(gun_greasegun, gun_greasegun.getUnlocalizedName());
		GameRegistry.registerItem(gun_maresleg, gun_maresleg.getUnlocalizedName());
		GameRegistry.registerItem(gun_maresleg_akimbo, gun_maresleg_akimbo.getUnlocalizedName());
		GameRegistry.registerItem(gun_maresleg_broken, gun_maresleg_broken.getUnlocalizedName());
		GameRegistry.registerItem(gun_flaregun, gun_flaregun.getUnlocalizedName());
		GameRegistry.registerItem(gun_heavy_revolver, gun_heavy_revolver.getUnlocalizedName());
		GameRegistry.registerItem(gun_heavy_revolver_lilmac, gun_heavy_revolver_lilmac.getUnlocalizedName());
		GameRegistry.registerItem(gun_heavy_revolver_protege, gun_heavy_revolver_protege.getUnlocalizedName());
		GameRegistry.registerItem(gun_carbine, gun_carbine.getUnlocalizedName());
		GameRegistry.registerItem(gun_am180, gun_am180.getUnlocalizedName());
		GameRegistry.registerItem(gun_liberator, gun_liberator.getUnlocalizedName());
		GameRegistry.registerItem(gun_congolake, gun_congolake.getUnlocalizedName());
		GameRegistry.registerItem(gun_flamer, gun_flamer.getUnlocalizedName());
		GameRegistry.registerItem(gun_flamer_topaz, gun_flamer_topaz.getUnlocalizedName());
		GameRegistry.registerItem(gun_flamer_daybreaker, gun_flamer_daybreaker.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi, gun_uzi.getUnlocalizedName());
		GameRegistry.registerItem(gun_uzi_akimbo, gun_uzi_akimbo.getUnlocalizedName());
		GameRegistry.registerItem(gun_spas12, gun_spas12.getUnlocalizedName());
		GameRegistry.registerItem(gun_panzerschreck, gun_panzerschreck.getUnlocalizedName());
		GameRegistry.registerItem(gun_star_f, gun_star_f.getUnlocalizedName());
		GameRegistry.registerItem(gun_star_f_akimbo, gun_star_f_akimbo.getUnlocalizedName());
		GameRegistry.registerItem(gun_g3, gun_g3.getUnlocalizedName());
		GameRegistry.registerItem(gun_g3_zebra, gun_g3_zebra.getUnlocalizedName());
		GameRegistry.registerItem(gun_mk108, gun_mk108.getUnlocalizedName());
		GameRegistry.registerItem(gun_chemthrower, gun_chemthrower.getUnlocalizedName());
		GameRegistry.registerItem(gun_amat, gun_amat.getUnlocalizedName());
		GameRegistry.registerItem(gun_amat_subtlety, gun_amat_subtlety.getUnlocalizedName());
		GameRegistry.registerItem(gun_amat_penance, gun_amat_penance.getUnlocalizedName());
		GameRegistry.registerItem(gun_m2, gun_m2.getUnlocalizedName());
		GameRegistry.registerItem(gun_autoshotgun, gun_autoshotgun.getUnlocalizedName());
		GameRegistry.registerItem(gun_autoshotgun_shredder, gun_autoshotgun_shredder.getUnlocalizedName());
		GameRegistry.registerItem(gun_autoshotgun_sexy, gun_autoshotgun_sexy.getUnlocalizedName());
		GameRegistry.registerItem(gun_autoshotgun_heretic, gun_autoshotgun_heretic.getUnlocalizedName());
		GameRegistry.registerItem(gun_quadro, gun_quadro.getUnlocalizedName());
		GameRegistry.registerItem(gun_lag, gun_lag.getUnlocalizedName());
		GameRegistry.registerItem(gun_minigun, gun_minigun.getUnlocalizedName());
		GameRegistry.registerItem(gun_minigun_dual, gun_minigun_dual.getUnlocalizedName());
		GameRegistry.registerItem(gun_minigun_lacunae, gun_minigun_lacunae.getUnlocalizedName());
		GameRegistry.registerItem(gun_missile_launcher, gun_missile_launcher.getUnlocalizedName());
		GameRegistry.registerItem(gun_tesla_cannon, gun_tesla_cannon.getUnlocalizedName());
		GameRegistry.registerItem(gun_laser_pistol, gun_laser_pistol.getUnlocalizedName());
		GameRegistry.registerItem(gun_laser_pistol_pew_pew, gun_laser_pistol_pew_pew.getUnlocalizedName());
		GameRegistry.registerItem(gun_laser_pistol_morning_glory, gun_laser_pistol_morning_glory.getUnlocalizedName());
		GameRegistry.registerItem(gun_stg77, gun_stg77.getUnlocalizedName());
		GameRegistry.registerItem(gun_tau, gun_tau.getUnlocalizedName());
		GameRegistry.registerItem(gun_fatman, gun_fatman.getUnlocalizedName());
		GameRegistry.registerItem(gun_lasrifle, gun_lasrifle.getUnlocalizedName());
		GameRegistry.registerItem(gun_stinger, gun_stinger.getUnlocalizedName());
		GameRegistry.registerItem(gun_coilgun, gun_coilgun.getUnlocalizedName());
		GameRegistry.registerItem(gun_hangman, gun_hangman.getUnlocalizedName());
		GameRegistry.registerItem(gun_mas36, gun_mas36.getUnlocalizedName());
		GameRegistry.registerItem(gun_bolter, gun_bolter.getUnlocalizedName());
		GameRegistry.registerItem(gun_folly, gun_folly.getUnlocalizedName());
		GameRegistry.registerItem(gun_aberrator, gun_aberrator.getUnlocalizedName());
		GameRegistry.registerItem(gun_aberrator_eott, gun_aberrator_eott.getUnlocalizedName());
		GameRegistry.registerItem(gun_double_barrel, gun_double_barrel.getUnlocalizedName());
		GameRegistry.registerItem(gun_double_barrel_sacred_dragon, gun_double_barrel_sacred_dragon.getUnlocalizedName());
		GameRegistry.registerItem(gun_n_i_4_n_i, gun_n_i_4_n_i.getUnlocalizedName());

		GameRegistry.registerItem(gun_fireext, gun_fireext.getUnlocalizedName());
		GameRegistry.registerItem(gun_charge_thrower, gun_charge_thrower.getUnlocalizedName());
		GameRegistry.registerItem(gun_drill, gun_drill.getUnlocalizedName());
		GameRegistry.registerItem(gun_pa_melee, gun_pa_melee.getUnlocalizedName());
		GameRegistry.registerItem(gun_pa_ranged, gun_pa_ranged.getUnlocalizedName());

		GameRegistry.registerItem(ammo_standard, ammo_standard.getUnlocalizedName());
		GameRegistry.registerItem(ammo_secret, ammo_secret.getUnlocalizedName());

		GameRegistry.registerItem(weapon_mod_test, weapon_mod_test.getUnlocalizedName());
		GameRegistry.registerItem(weapon_mod_generic, weapon_mod_generic.getUnlocalizedName());
		GameRegistry.registerItem(weapon_mod_special, weapon_mod_special.getUnlocalizedName());
		GameRegistry.registerItem(weapon_mod_caliber, weapon_mod_caliber.getUnlocalizedName());

		//Ammo
		registerItemSafe(gun_b92_ammo);

		registerItemSafe(ammo_fireext);
		registerItemSafe(ammo_shell);
		registerItemSafe(ammo_dgk);
		registerItemSafe(ammo_arty);
		registerItemSafe(ammo_himars);

		registerItemSafe(ammo_container);

		//Grenades
		registerItemSafe(stick_dynamite); //heave-ho!
		registerItemSafe(stick_dynamite_fishing);
		registerItemSafe(stick_tnt);
		registerItemSafe(stick_semtex);
		registerItemSafe(stick_c4);
		registerItemSafe(grenade_generic);
		registerItemSafe(grenade_strong);
		registerItemSafe(grenade_frag);
		registerItemSafe(grenade_fire);
		registerItemSafe(grenade_shrapnel);
		registerItemSafe(grenade_cluster);
		registerItemSafe(grenade_flare);
		registerItemSafe(grenade_electric);
		registerItemSafe(grenade_poison);
		registerItemSafe(grenade_gas);
		registerItemSafe(grenade_cloud);
		registerItemSafe(grenade_pink_cloud);
		registerItemSafe(grenade_smart);
		registerItemSafe(grenade_mirv);
		registerItemSafe(grenade_breach);
		registerItemSafe(grenade_burst);
		registerItemSafe(grenade_pulse);
		registerItemSafe(grenade_plasma);
		registerItemSafe(grenade_tau);
		registerItemSafe(grenade_schrabidium);
		registerItemSafe(grenade_nuke);
		registerItemSafe(grenade_lemon);
		registerItemSafe(grenade_gascan);
		registerItemSafe(grenade_kyiv);
		registerItemSafe(grenade_mk2);
		registerItemSafe(grenade_aschrab);
		registerItemSafe(grenade_nuclear);
		registerItemSafe(grenade_zomg);
		registerItemSafe(grenade_black_hole);

		registerItemSafe(grenade_if_generic);
		registerItemSafe(grenade_if_he);
		registerItemSafe(grenade_if_bouncy);
		registerItemSafe(grenade_if_sticky);
		registerItemSafe(grenade_if_impact);
		registerItemSafe(grenade_if_incendiary);
		registerItemSafe(grenade_if_toxic);
		registerItemSafe(grenade_if_concussion);
		registerItemSafe(grenade_if_brimstone);
		registerItemSafe(grenade_if_mystery);
		registerItemSafe(grenade_if_spark);
		registerItemSafe(grenade_if_hopwire);
		registerItemSafe(grenade_if_null);
		registerItemSafe(nuclear_waste_pearl);

		//Disperser Canister
		registerItemSafe(disperser_canister_empty);
		registerItemSafe(disperser_canister);
		registerItemSafe(glyphid_gland_empty);
		registerItemSafe(glyphid_gland);

		registerItemSafe(ullapool_caber);
		registerItemSafe(weaponized_starblaster_cell);

		//Capes
		registerItemSafe(cape_radiation);
		registerItemSafe(cape_gasmask);
		registerItemSafe(cape_schrabidium);
		registerItemSafe(cape_hidden);

		//Tools
		registerItemSafe(dwarven_pickaxe);
		registerItemSafe(schrabidium_sword);
		registerItemSafe(schrabidium_hammer);
		registerItemSafe(shimmer_sledge);
		registerItemSafe(shimmer_axe);
		registerItemSafe(wood_gavel);
		registerItemSafe(lead_gavel);
		registerItemSafe(diamond_gavel);
		registerItemSafe(mese_gavel);
		registerItemSafe(schrabidium_pickaxe);
		registerItemSafe(schrabidium_axe);
		registerItemSafe(schrabidium_shovel);
		registerItemSafe(schrabidium_hoe);
		registerItemSafe(steel_sword);
		registerItemSafe(steel_pickaxe);
		registerItemSafe(steel_axe);
		registerItemSafe(steel_shovel);
		registerItemSafe(steel_hoe);
		registerItemSafe(titanium_sword);
		registerItemSafe(titanium_pickaxe);
		registerItemSafe(titanium_axe);
		registerItemSafe(titanium_shovel);
		registerItemSafe(titanium_hoe);
		registerItemSafe(cobalt_sword);
		registerItemSafe(cobalt_pickaxe);
		registerItemSafe(cobalt_axe);
		registerItemSafe(cobalt_shovel);
		registerItemSafe(cobalt_hoe);
		registerItemSafe(cobalt_decorated_sword);
		registerItemSafe(cobalt_decorated_pickaxe);
		registerItemSafe(cobalt_decorated_axe);
		registerItemSafe(cobalt_decorated_shovel);
		registerItemSafe(cobalt_decorated_hoe);
		registerItemSafe(starmetal_sword);
		registerItemSafe(starmetal_pickaxe);
		registerItemSafe(starmetal_axe);
		registerItemSafe(starmetal_shovel);
		registerItemSafe(starmetal_hoe);
		registerItemSafe(alloy_sword);
		registerItemSafe(alloy_pickaxe);
		registerItemSafe(alloy_axe);
		registerItemSafe(alloy_shovel);
		registerItemSafe(alloy_hoe);
		registerItemSafe(cmb_sword);
		registerItemSafe(cmb_pickaxe);
		registerItemSafe(cmb_axe);
		registerItemSafe(cmb_shovel);
		registerItemSafe(cmb_hoe);
		registerItemSafe(desh_sword);
		registerItemSafe(desh_pickaxe);
		registerItemSafe(desh_axe);
		registerItemSafe(desh_shovel);
		registerItemSafe(desh_hoe);
		registerItemSafe(elec_sword);
		registerItemSafe(elec_pickaxe);
		registerItemSafe(elec_axe);
		registerItemSafe(elec_shovel);
		registerItemSafe(dnt_sword);
		registerItemSafe(smashing_hammer);
		registerItemSafe(centri_stick);
		registerItemSafe(drax);
		registerItemSafe(drax_mk2);
		registerItemSafe(drax_mk3);
		registerItemSafe(bismuth_pickaxe);
		registerItemSafe(bismuth_axe);
		registerItemSafe(volcanic_pickaxe);
		registerItemSafe(volcanic_axe);
		registerItemSafe(chlorophyte_pickaxe);
		registerItemSafe(chlorophyte_axe);
		registerItemSafe(mese_pickaxe);
		registerItemSafe(mese_axe);		registerItemSafe(matchstick);
		registerItemSafe(balefire_and_steel);
		registerItemSafe(crowbar);
		registerItemSafe(wrench);
		registerItemSafe(wrench_archineer);
		registerItemSafe(wrench_flipped);
		registerItemSafe(memespoon);
		registerItemSafe(pipe_lead);
		registerItemSafe(reer_graar);
		registerItemSafe(stopsign);
		registerItemSafe(sopsign);
		registerItemSafe(chernobylsign);

		registerItemSafe(meteorite_sword);
		registerItemSafe(meteorite_sword_seared);
		registerItemSafe(meteorite_sword_reforged);
		registerItemSafe(meteorite_sword_hardened);
		registerItemSafe(meteorite_sword_alloyed);
		registerItemSafe(meteorite_sword_machined);
		registerItemSafe(meteorite_sword_treated);
		registerItemSafe(meteorite_sword_etched);
		registerItemSafe(meteorite_sword_bred);
		registerItemSafe(meteorite_sword_irradiated);
		registerItemSafe(meteorite_sword_fused);
		registerItemSafe(meteorite_sword_baleful);

		//Syringes & Pills
		registerItemSafe(syringe_empty);
		registerItemSafe(syringe_antidote);
		registerItemSafe(syringe_poison);
		registerItemSafe(syringe_awesome);
		registerItemSafe(syringe_metal_empty);
		registerItemSafe(syringe_metal_stimpak);
		registerItemSafe(syringe_metal_medx);
		registerItemSafe(syringe_metal_psycho);
		registerItemSafe(syringe_metal_super);
		registerItemSafe(syringe_taint);
		registerItemSafe(syringe_mkunicorn);
		registerItemSafe(med_bag);
		registerItemSafe(iv_empty);
		registerItemSafe(iv_blood);
		registerItemSafe(iv_xp_empty);
		registerItemSafe(iv_xp);
		registerItemSafe(radaway);
		registerItemSafe(radaway_strong);
		registerItemSafe(radaway_flush);
		registerItemSafe(radx);
		registerItemSafe(siox);
		registerItemSafe(pill_herbal);
		registerItemSafe(pill_iodine);
		registerItemSafe(xanax);
		registerItemSafe(fmn);
		registerItemSafe(five_htp);
		registerItemSafe(plan_c);
		registerItemSafe(pill_red);
		registerItemSafe(stealth_boy);
		registerItemSafe(gas_mask_filter);
		registerItemSafe(gas_mask_filter_mono);
		registerItemSafe(gas_mask_filter_combo);
		registerItemSafe(gas_mask_filter_rag);
		registerItemSafe(gas_mask_filter_piss);
		registerItemSafe(jetpack_tank);
		registerItemSafe(gun_kit_1);
		registerItemSafe(gun_kit_2);

		//Food
		registerItemSafe(bomb_waffle);
		registerItemSafe(schnitzel_vegan);
		registerItemSafe(cotton_candy);
		registerItemSafe(apple_lead);
		registerItemSafe(apple_schrabidium);
		registerItemSafe(tem_flakes);
		registerItemSafe(glowing_stew);
		registerItemSafe(balefire_scrambled);
		registerItemSafe(balefire_and_ham);
		registerItemSafe(lemon);
		registerItemSafe(definitelyfood);
		registerItemSafe(loops);
		registerItemSafe(loop_stew);
		registerItemSafe(spongebob_macaroni);
		registerItemSafe(fooditem);
		registerItemSafe(twinkie);
		registerItemSafe(static_sandwich);
		registerItemSafe(pudding);
		registerItemSafe(pancake);
		registerItemSafe(nugget);
		registerItemSafe(peas);
		registerItemSafe(marshmallow);
		registerItemSafe(cheese);
		registerItemSafe(quesadilla);
		registerItemSafe(glyphid_meat);
		registerItemSafe(glyphid_meat_grilled);
		registerItemSafe(egg_glyphid);
		registerItemSafe(med_ipecac);
		registerItemSafe(med_ptsd);
		registerItemSafe(canteen_vodka);
		registerItemSafe(mucho_mango);
		registerItemSafe(chocolate);

		//Energy Drinks
		registerItemSafe(can_empty);
		registerItemSafe(can_smart);
		registerItemSafe(can_creature);
		registerItemSafe(can_redbomb);
		registerItemSafe(can_mrsugar);
		registerItemSafe(can_overcharge);
		registerItemSafe(can_luna);
		registerItemSafe(can_bepis);
		registerItemSafe(can_breen);
		registerItemSafe(can_mug);

		//Coffee
		registerItemSafe(coffee);
		registerItemSafe(coffee_radium);

		//Cola
		registerItemSafe(bottle_empty);
		registerItemSafe(bottle_nuka);
		registerItemSafe(bottle_cherry);
		registerItemSafe(bottle_quantum);
		registerItemSafe(bottle_sparkle);
		registerItemSafe(bottle_rad);
		registerItemSafe(bottle2_empty);
		registerItemSafe(bottle2_korl);
		registerItemSafe(bottle2_fritz);
		registerItemSafe(bottle_opener);

		//Flasks
		registerItemSafe(flask_infusion);

		//Canned Food
		registerItemSafe(canned_conserve);

		//Money
		GameRegistry.registerItem(cap_nuka, cap_nuka.getUnlocalizedName());
		GameRegistry.registerItem(cap_quantum, cap_quantum.getUnlocalizedName());
		GameRegistry.registerItem(cap_sparkle, cap_sparkle.getUnlocalizedName());
		GameRegistry.registerItem(cap_rad, cap_rad.getUnlocalizedName());
		GameRegistry.registerItem(cap_korl, cap_korl.getUnlocalizedName());
		GameRegistry.registerItem(cap_fritz, cap_fritz.getUnlocalizedName());
		GameRegistry.registerItem(ring_pull, ring_pull.getUnlocalizedName());
		GameRegistry.registerItem(can_key, can_key.getUnlocalizedName());
		GameRegistry.registerItem(coin_creeper, coin_creeper.getUnlocalizedName());
		GameRegistry.registerItem(coin_radiation,coin_radiation.getUnlocalizedName());
		GameRegistry.registerItem(coin_maskman, coin_maskman.getUnlocalizedName());
		GameRegistry.registerItem(coin_worm, coin_worm.getUnlocalizedName());
		GameRegistry.registerItem(coin_ufo, coin_ufo.getUnlocalizedName());
		GameRegistry.registerItem(coin_token, coin_token.getUnlocalizedName());
		GameRegistry.registerItem(medal_liquidator, medal_liquidator.getUnlocalizedName());
		GameRegistry.registerItem(bottled_cloud, bottled_cloud.getUnlocalizedName());
		GameRegistry.registerItem(protection_charm, protection_charm.getUnlocalizedName());
		GameRegistry.registerItem(meteor_charm, meteor_charm.getUnlocalizedName());
		GameRegistry.registerItem(neutrino_lens, neutrino_lens.getUnlocalizedName());
		GameRegistry.registerItem(gas_tester, gas_tester.getUnlocalizedName());
		GameRegistry.registerItem(defuser_gold, defuser_gold.getUnlocalizedName());
		GameRegistry.registerItem(ballistic_gauntlet, ballistic_gauntlet.getUnlocalizedName());
		GameRegistry.registerItem(night_vision, night_vision.getUnlocalizedName());
		GameRegistry.registerItem(card_aos, card_aos.getUnlocalizedName());
		GameRegistry.registerItem(card_qos, card_qos.getUnlocalizedName());
		GameRegistry.registerItem(australium_iii, australium_iii.getUnlocalizedName());
		GameRegistry.registerItem(armor_battery, armor_battery.getUnlocalizedName());
		GameRegistry.registerItem(armor_battery_mk2, armor_battery_mk2.getUnlocalizedName());
		GameRegistry.registerItem(armor_battery_mk3, armor_battery_mk3.getUnlocalizedName());

		//Chaos
		registerItemSafe(chocolate_milk);
		registerItemSafe(cbt_device);
		registerItemSafe(cigarette);
		registerItemSafe(crackpipe);
		registerItemSafe(bdcl);

		//Armor mods
		registerItemSafe(attachment_mask);
		registerItemSafe(attachment_mask_mono);
		registerItemSafe(back_tesla);
		registerItemSafe(servo_set);
		registerItemSafe(servo_set_desh);
		registerItemSafe(pads_rubber);
		registerItemSafe(pads_slime);
		registerItemSafe(pads_static);
		registerItemSafe(cladding_paint);
		registerItemSafe(cladding_rubber);
		registerItemSafe(cladding_lead);
		registerItemSafe(cladding_desh);
		registerItemSafe(cladding_ghiorsium);
		registerItemSafe(cladding_iron);
		registerItemSafe(cladding_obsidian);
		registerItemSafe(insert_kevlar);
		registerItemSafe(insert_sapi);
		registerItemSafe(insert_esapi);
		registerItemSafe(insert_xsapi);
		registerItemSafe(insert_steel);
		registerItemSafe(insert_du);
		registerItemSafe(insert_polonium);
		registerItemSafe(insert_ghiorsium);
		registerItemSafe(insert_era);
		registerItemSafe(insert_yharonite);
		registerItemSafe(insert_doxium);
		registerItemSafe(armor_polish);
		registerItemSafe(bandaid);
		registerItemSafe(serum);
		registerItemSafe(quartz_plutonium);
		registerItemSafe(morning_glory);
		registerItemSafe(lodestone);
		registerItemSafe(horseshoe_magnet);
		registerItemSafe(industrial_magnet);
		registerItemSafe(bathwater);
		registerItemSafe(bathwater_mk2);
		registerItemSafe(spider_milk);
		registerItemSafe(ink);
		registerItemSafe(heart_piece);
		registerItemSafe(heart_container);
		registerItemSafe(heart_booster);
		registerItemSafe(heart_fab);
		registerItemSafe(black_diamond);
		registerItemSafe(wd40);
		registerItemSafe(scrumpy);
		registerItemSafe(wild_p);
		registerItemSafe(shackles);
		registerItemSafe(injector_5htp);
		registerItemSafe(injector_knife);

		//Vehicles
		registerItemSafe(boat_rubber);
		registerItemSafe(cart);
		registerItemSafe(train);
		registerItemSafe(drone);

		//High Explosive Lenses
		registerItemSafe(early_explosive_lenses);
		registerItemSafe(explosive_lenses);

		//The Gadget
		registerItemSafe(gadget_wireing);
		registerItemSafe(gadget_core);

		//Little Boy
		registerItemSafe(boy_shielding);
		registerItemSafe(boy_target);
		registerItemSafe(boy_bullet);
		registerItemSafe(boy_propellant);
		registerItemSafe(boy_igniter);;

		//Fat Man
		registerItemSafe(man_igniter);
		registerItemSafe(man_core);

		//Ivy Mike
		registerItemSafe(mike_core);
		registerItemSafe(mike_deut);
		registerItemSafe(mike_cooling_unit);

		//Tsar Bomba
		registerItemSafe(tsar_core);

		//FLEIJA
		registerItemSafe(fleija_igniter);
		registerItemSafe(fleija_propellant);
		registerItemSafe(fleija_core);

		//Solinium
		registerItemSafe(solinium_igniter);
		registerItemSafe(solinium_propellant);
		registerItemSafe(solinium_core);

		//N2
		registerItemSafe(n2_charge);

		//FSTBMB
		registerItemSafe(egg_balefire_shard);
		registerItemSafe(egg_balefire);

		//Conventional Armor
		registerItemSafe(goggles);
		registerItemSafe(ashglasses);
		registerItemSafe(gas_mask);
		registerItemSafe(gas_mask_m65);
		registerItemSafe(gas_mask_mono);
		registerItemSafe(gas_mask_olde);
		registerItemSafe(mask_rag);
		registerItemSafe(mask_piss);
		registerItemSafe(hat);
		registerItemSafe(beta);
		registerItemSafe(no9);

		registerItemSafe(steel_helmet);
		registerItemSafe(steel_plate);
		registerItemSafe(steel_legs);
		registerItemSafe(steel_boots);
		registerItemSafe(titanium_helmet);
		registerItemSafe(titanium_plate);
		registerItemSafe(titanium_legs);
		registerItemSafe(titanium_boots);
		registerItemSafe(alloy_helmet);
		registerItemSafe(alloy_plate);
		registerItemSafe(alloy_legs);
		registerItemSafe(alloy_boots);

		//Custom Rods
		registerItemSafe(custom_tnt);
		registerItemSafe(custom_nuke);
		registerItemSafe(custom_hydro);
		registerItemSafe(custom_amat);
		registerItemSafe(custom_dirty);
		registerItemSafe(custom_schrab);
		registerItemSafe(custom_fall);

		//Power Armor
		registerItemSafe(steamsuit_helmet);
		registerItemSafe(steamsuit_plate);
		registerItemSafe(steamsuit_legs);
		registerItemSafe(steamsuit_boots);
		registerItemSafe(dieselsuit_helmet);
		registerItemSafe(dieselsuit_plate);
		registerItemSafe(dieselsuit_legs);
		registerItemSafe(dieselsuit_boots);
		registerItemSafe(t45_helmet);
		registerItemSafe(t45_plate);
		registerItemSafe(t45_legs);
		registerItemSafe(t45_boots);
		registerItemSafe(t51_helmet);
		registerItemSafe(t51_plate);
		registerItemSafe(t51_legs);
		registerItemSafe(t51_boots);
		registerItemSafe(ajr_helmet);
		registerItemSafe(ajr_plate);
		registerItemSafe(ajr_legs);
		registerItemSafe(ajr_boots);
		registerItemSafe(ajro_helmet);
		registerItemSafe(ajro_plate);
		registerItemSafe(ajro_legs);
		registerItemSafe(ajro_boots);
		registerItemSafe(rpa_helmet);
		registerItemSafe(rpa_plate);
		registerItemSafe(rpa_legs);
		registerItemSafe(rpa_boots);
		registerItemSafe(bj_helmet);
		registerItemSafe(bj_plate);
		registerItemSafe(bj_plate_jetpack);
		registerItemSafe(bj_legs);
		registerItemSafe(bj_boots);
		registerItemSafe(envsuit_helmet);
		registerItemSafe(envsuit_plate);
		registerItemSafe(envsuit_legs);
		registerItemSafe(envsuit_boots);
		registerItemSafe(hev_helmet);
		registerItemSafe(hev_plate);
		registerItemSafe(hev_legs);
		registerItemSafe(hev_boots);
		registerItemSafe(fau_helmet);
		registerItemSafe(fau_plate);
		registerItemSafe(fau_legs);
		registerItemSafe(fau_boots);
		registerItemSafe(dns_helmet);
		registerItemSafe(dns_plate);
		registerItemSafe(dns_legs);
		registerItemSafe(dns_boots);
		registerItemSafe(taurun_helmet);
		registerItemSafe(taurun_plate);
		registerItemSafe(taurun_legs);
		registerItemSafe(taurun_boots);
		registerItemSafe(trenchmaster_helmet);
		registerItemSafe(trenchmaster_plate);
		registerItemSafe(trenchmaster_legs);
		registerItemSafe(trenchmaster_boots);

		//Nobody will ever read this anyway, so it shouldn't matter.
		registerItemSafe(chainsaw);
		registerItemSafe(igniter);
		registerItemSafe(detonator);
		registerItemSafe(detonator_multi);
		registerItemSafe(detonator_laser);
		registerItemSafe(detonator_deadman);
		registerItemSafe(detonator_de);
		registerItemSafe(bomb_caller);
		registerItemSafe(meteor_remote);
		registerItemSafe(anchor_remote);
		registerItemSafe(defuser);
		registerItemSafe(reacher);
		registerItemSafe(bismuth_tool);
		registerItemSafe(meltdown_tool);

		registerItemSafe(hazmat_helmet);
		registerItemSafe(hazmat_plate);
		registerItemSafe(hazmat_legs);
		registerItemSafe(hazmat_boots);
		registerItemSafe(hazmat_helmet_red);
		registerItemSafe(hazmat_plate_red);
		registerItemSafe(hazmat_legs_red);
		registerItemSafe(hazmat_boots_red);
		registerItemSafe(hazmat_helmet_grey);
		registerItemSafe(hazmat_plate_grey);
		registerItemSafe(hazmat_legs_grey);
		registerItemSafe(hazmat_boots_grey);
		registerItemSafe(hazmat_paa_helmet);
		registerItemSafe(hazmat_paa_plate);
		registerItemSafe(hazmat_paa_legs);
		registerItemSafe(hazmat_paa_boots);
		registerItemSafe(liquidator_helmet);
		registerItemSafe(liquidator_plate);
		registerItemSafe(liquidator_legs);
		registerItemSafe(liquidator_boots);
		registerItemSafe(cmb_helmet);
		registerItemSafe(cmb_plate);
		registerItemSafe(cmb_legs);
		registerItemSafe(cmb_boots);
		registerItemSafe(paa_plate);
		registerItemSafe(paa_legs);
		registerItemSafe(paa_boots);
		registerItemSafe(asbestos_helmet);
		registerItemSafe(asbestos_plate);
		registerItemSafe(asbestos_legs);
		registerItemSafe(asbestos_boots);
		registerItemSafe(security_helmet);
		registerItemSafe(security_plate);
		registerItemSafe(security_legs);
		registerItemSafe(security_boots);
		registerItemSafe(cobalt_helmet);
		registerItemSafe(cobalt_plate);
		registerItemSafe(cobalt_legs);
		registerItemSafe(cobalt_boots);
		registerItemSafe(starmetal_helmet);
		registerItemSafe(starmetal_plate);
		registerItemSafe(starmetal_legs);
		registerItemSafe(starmetal_boots);
		registerItemSafe(zirconium_legs);
		registerItemSafe(dnt_helmet);
		registerItemSafe(dnt_plate);
		registerItemSafe(dnt_legs);
		registerItemSafe(dnt_boots);
		registerItemSafe(schrabidium_helmet);
		registerItemSafe(schrabidium_plate);
		registerItemSafe(schrabidium_legs);
		registerItemSafe(schrabidium_boots);
		registerItemSafe(bismuth_helmet);
		registerItemSafe(bismuth_plate);
		registerItemSafe(bismuth_legs);
		registerItemSafe(bismuth_boots);
		registerItemSafe(euphemium_helmet);
		registerItemSafe(euphemium_plate);
		registerItemSafe(euphemium_legs);
		registerItemSafe(euphemium_boots);
		registerItemSafe(robes_helmet);
		registerItemSafe(robes_plate);
		registerItemSafe(robes_legs);
		registerItemSafe(robes_boots);
		registerItemSafe(apple_euphemium);
		registerItemSafe(watch);
		registerItemSafe(mask_of_infamy);
		registerItemSafe(jackt);
		registerItemSafe(jackt2);
		registerItemSafe(jetpack_fly);
		registerItemSafe(jetpack_break);
		registerItemSafe(jetpack_vector);
		registerItemSafe(jetpack_boost);
		registerItemSafe(wings_limp);
		registerItemSafe(wings_murk);

		//Expensive Ass Shit
		registerItemSafe(crystal_horn);
		registerItemSafe(crystal_charred);

		//Wands, Tools, Other Crap
		registerItemSafe(rebar_placer);
		registerItemSafe(wand);
		registerItemSafe(wand_s);
		registerItemSafe(wand_d);
		registerItemSafe(structure_single);
		registerItemSafe(structure_solid);
		registerItemSafe(structure_pattern);
		registerItemSafe(structure_randomized);
		registerItemSafe(structure_randomly);
		registerItemSafe(structure_custommachine);
		registerItemSafe(rod_of_discord);
		registerItemSafe(polaroid);
		registerItemSafe(glitch);
		registerItemSafe(book_secret);
		registerItemSafe(book_of_);
		registerItemSafe(page_of_);
		registerItemSafe(book_lemegeton);
		registerItemSafe(burnt_bark);

		//Kits
		registerItemSafe(nuke_starter_kit);
		registerItemSafe(nuke_advanced_kit);
		registerItemSafe(nuke_commercially_kit);
		registerItemSafe(nuke_electric_kit);
		registerItemSafe(gadget_kit);
		registerItemSafe(boy_kit);
		registerItemSafe(man_kit);
		registerItemSafe(mike_kit);
		registerItemSafe(tsar_kit);
		registerItemSafe(prototype_kit);
		registerItemSafe(fleija_kit);
		registerItemSafe(solinium_kit);
		registerItemSafe(multi_kit);
		registerItemSafe(custom_kit);
		registerItemSafe(missile_kit);
		registerItemSafe(grenade_kit);
		registerItemSafe(hazmat_kit);
		registerItemSafe(hazmat_red_kit);
		registerItemSafe(hazmat_grey_kit);
		registerItemSafe(kit_custom);
		registerItemSafe(euphemium_kit);
		registerItemSafe(legacy_toolbox);
		registerItemSafe(toolbox);

		//Misile Loot Boxes
		registerItemSafe(loot_10);
		registerItemSafe(loot_15);
		registerItemSafe(loot_misc);

		//THIS is a bucket.
		registerItemSafe(bucket_mud);
		registerItemSafe(bucket_acid);
		registerItemSafe(bucket_toxic);
		registerItemSafe(bucket_schrabidic_acid);
		registerItemSafe(bucket_sulfuric_acid);

		//Door Items
		registerItemSafe(door_metal);
		registerItemSafe(door_office);
		registerItemSafe(door_bunker);
		registerItemSafe(door_red);

		//Records
		registerItemSafe(record_lc);
		registerItemSafe(record_ss);
		registerItemSafe(record_vc);
		registerItemSafe(record_glass);

		//wow we're far down the item registry, is this the cellar?
		registerItemSafe(book_guide);
		registerItemSafe(book_lore);
		registerItemSafe(holotape_image);
		registerItemSafe(holotape_damaged);
		registerItemSafe(clay_tablet);

		//Technical Items
		registerItemSafe(chlorine1);
		registerItemSafe(chlorine2);
		registerItemSafe(chlorine3);
		registerItemSafe(chlorine4);
		registerItemSafe(chlorine5);
		registerItemSafe(chlorine6);
		registerItemSafe(chlorine7);
		registerItemSafe(chlorine8);
		registerItemSafe(pc1);
		registerItemSafe(pc2);
		registerItemSafe(pc3);
		registerItemSafe(pc4);
		registerItemSafe(pc5);
		registerItemSafe(pc6);
		registerItemSafe(pc7);
		registerItemSafe(pc8);
		registerItemSafe(cloud1);
		registerItemSafe(cloud2);
		registerItemSafe(cloud3);
		registerItemSafe(cloud4);
		registerItemSafe(cloud5);
		registerItemSafe(cloud6);
		registerItemSafe(cloud7);
		registerItemSafe(cloud8);
		registerItemSafe(orange1);
		registerItemSafe(orange2);
		registerItemSafe(orange3);
		registerItemSafe(orange4);
		registerItemSafe(orange5);
		registerItemSafe(orange6);
		registerItemSafe(orange7);
		registerItemSafe(orange8);
		registerItemSafe(achievement_icon);

		registerItemSafe(mysteryshovel);
		registerItemSafe(memory);
		registerItemSafe(conveyor_wand);

		registerItemSafe(nothing);
		registerItemSafe(broken_item);
	}

	public static void addRemap(String unloc, Item item, Enum sub) {
		addRemap(unloc, item, sub.ordinal());
	}

	public static void addRemap(String unloc, Item item, int meta) {
		Item remap = new ItemRemap(item, meta).setUnlocalizedName(unloc).setTextureName(RefStrings.MODID + ":plate_armor_titanium");
		registerItemSafe(remap);
	}
}
