package com.hbm.items;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;

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

	public static Item powder_lead;
	public static Item powder_neptunium;
	public static Item powder_schrabidium;
	
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

	public static Item circuit_aluminium;
	public static Item circuit_copper;
	public static Item circuit_red_copper;
	public static Item circuit_gold;
	public static Item circuit_schrabidium;

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
	
	public static Item pellet_rtg;
	
	public static Item cell_empty;
	public static Item cell_uf6;
	public static Item cell_puf6;
	public static Item cell_antimatter;
	public static Item cell_deuterium;
	public static Item cell_sas3;

	public static Item syringe_empty;
	public static Item syringe_antidote;
	public static Item syringe_awesome;
	public static Item syringe_metal_empty;
	public static Item syringe_metal_stimpak;

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
	public static Item powder_poison;
	public static Item pellet_gas;

	public static Item designator;
	
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
	
	public static Item gun_rpg;
	public static Item gun_rpg_ammo;
	public static Item gun_revolver;
	public static Item gun_revolver_ammo;
	public static Item gun_revolver_iron;
	public static Item gun_revolver_iron_ammo;
	public static Item gun_revolver_gold;
	public static Item gun_revolver_gold_ammo;
	public static Item gun_revolver_schrabidium;
	public static Item gun_revolver_schrabidium_ammo;
	public static Item gun_revolver_cursed;
	public static Item gun_revolver_cursed_ammo;
	public static Item gun_waluigi;

	public static Item grenade_generic;
	public static Item grenade_strong;
	public static Item grenade_frag;
	public static Item grenade_fire;
	public static Item grenade_cluster;
	public static Item grenade_flare;
	public static Item grenade_electric;
	public static Item grenade_poison;
	public static Item grenade_gas;
	public static Item grenade_schrabidium;
	public static Item grenade_nuke;

	public static Item bomb_waffle;
	public static Item schnitzel_vegan;
	public static Item cotton_candy;
	public static Item apple_schrabidium;

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
	
	public static Item battery_generic;
	public static Item battery_advanced;
	public static Item battery_schrabidium;
	public static Item battery_creative;

	public static Item fusion_core;

	public static Item ingot_euphemium;
	public static Item nugget_euphemium;
	public static Item rod_quad_euphemium;
	public static Item euphemium_helmet;
	public static Item euphemium_plate;
	public static Item euphemium_legs;
	public static Item euphemium_boots;
	public static Item apple_euphemium;
	public static Item watch;

	public static Item goggles;
	public static Item gas_mask;
	
	public static Item t45_helmet;
	public static Item t45_plate;
	public static Item t45_legs;
	public static Item t45_boots;
	
	public static Item schrabidium_helmet;
	public static Item schrabidium_plate;
	public static Item schrabidium_legs;
	public static Item schrabidium_boots;
	
	public static Item schrabidium_sword;
	public static Item schrabidium_pickaxe;
	public static Item schrabidium_axe;
	public static Item schrabidium_shovel;
	public static Item schrabidium_hoe;
	
	public static Item mask_of_infamy;

	public static Item hazmat_helmet;
	public static Item hazmat_plate;
	public static Item hazmat_legs;
	public static Item hazmat_boots;

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

	public static Item igniter;

	public static void initializeItem()
	{			
		redstone_sword = new RedstoneSword(ToolMaterial.STONE).setUnlocalizedName("redstone_sword").setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":redstone_sword");
		big_sword = new BigSword(ToolMaterial.EMERALD).setUnlocalizedName("big_sword").setCreativeTab(CreativeTabs.tabCombat).setTextureName(RefStrings.MODID + ":big_sword");

		test_helmet = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 5, 0).setUnlocalizedName("test_helmet").setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_helmet");
		test_chestplate = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 5, 1).setUnlocalizedName("test_chestplate").setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_chestplate");
		test_leggings = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 5, 2).setUnlocalizedName("test_leggings").setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_leggings");
		test_boots = new ArmorTest(MainRegistry.enumArmorMaterialEmerald, 5, 3).setUnlocalizedName("test_boots").setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_boots");
		
		test_nuke_igniter = new Item().setUnlocalizedName("test_nuke_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_nuke_igniter");
		test_nuke_propellant = new Item().setUnlocalizedName("test_nuke_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_nuke_propellant");
		test_nuke_tier1_shielding = new Item().setUnlocalizedName("test_nuke_tier1_shielding").setMaxStackSize(1).setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_nuke_tier1_shielding");
		test_nuke_tier2_shielding = new Item().setUnlocalizedName("test_nuke_tier2_shielding").setMaxStackSize(1).setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_nuke_tier2_shielding");
		test_nuke_tier1_bullet = new Item().setUnlocalizedName("test_nuke_tier1_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_nuke_tier1_bullet");
		test_nuke_tier2_bullet = new Item().setUnlocalizedName("test_nuke_tier2_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_nuke_tier2_bullet");
		test_nuke_tier1_target = new Item().setUnlocalizedName("test_nuke_tier1_target").setMaxStackSize(1).setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_nuke_tier1_target");
		test_nuke_tier2_target = new Item().setUnlocalizedName("test_nuke_tier2_target").setMaxStackSize(1).setCreativeTab(MainRegistry.tabTest).setTextureName(RefStrings.MODID + ":test_nuke_tier2_target");
		
		ingot_uranium = new Item().setUnlocalizedName("ingot_uranium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_uranium");
		ingot_u235 = new Item().setUnlocalizedName("ingot_u235").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_uranium");
		ingot_u238 = new Item().setUnlocalizedName("ingot_u238").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_uranium");
		ingot_plutonium = new Item().setUnlocalizedName("ingot_plutonium").setCreativeTab(MainRegistry.tabParts).setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ingot_pu238 = new Item().setUnlocalizedName("ingot_pu238").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ingot_pu239 = new Item().setUnlocalizedName("ingot_pu239").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ingot_pu240 = new Item().setUnlocalizedName("ingot_pu240").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ingot_neptunium = new ItemCustomLore().setUnlocalizedName("ingot_neptunium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_neptunium");
		ingot_titanium = new Item().setUnlocalizedName("ingot_titanium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_titanium");
		sulfur = new Item().setUnlocalizedName("sulfur").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":sulfur");

		ingot_uranium_fuel = new Item().setUnlocalizedName("ingot_uranium_fuel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_uranium");
		ingot_plutonium_fuel = new Item().setUnlocalizedName("ingot_plutonium_fuel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_plutonium");
		ingot_mox_fuel = new Item().setUnlocalizedName("ingot_mox_fuel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_mox_fuel");
		ingot_schrabidium_fuel = new Item().setUnlocalizedName("ingot_schrabidium_fuel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_schrabidium_fuel");
		nugget_uranium_fuel = new Item().setUnlocalizedName("nugget_uranium_fuel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_uranium");
		nugget_plutonium_fuel = new Item().setUnlocalizedName("nugget_plutonium_fuel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		nugget_mox_fuel = new Item().setUnlocalizedName("nugget_mox_fuel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_mox_fuel");
		nugget_schrabidium_fuel = new Item().setUnlocalizedName("nugget_schrabidium_fuel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_schrabidium_fuel");

		niter = new Item().setUnlocalizedName("niter").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":salpeter");
		ingot_copper = new Item().setUnlocalizedName("ingot_copper").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_copper");
		ingot_red_copper = new Item().setUnlocalizedName("ingot_red_copper").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_red_copper");
		ingot_tungsten = new Item().setUnlocalizedName("ingot_tungsten").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_tungsten");
		ingot_aluminium = new Item().setUnlocalizedName("ingot_aluminium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_aluminium");
		fluorite = new Item().setUnlocalizedName("fluorite").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":fluorite");
		ingot_beryllium = new Item().setUnlocalizedName("ingot_beryllium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_beryllium");
		ingot_steel = new Item().setUnlocalizedName("ingot_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_steel");
		plate_steel = new Item().setUnlocalizedName("plate_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":plate_steel");
		plate_iron = new Item().setUnlocalizedName("plate_iron").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":plate_iron");
		ingot_lead = new Item().setUnlocalizedName("ingot_lead").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_lead");
		plate_lead = new Item().setUnlocalizedName("plate_lead").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":plate_lead");
		ingot_schrabidium = new ItemCustomLore().setUnlocalizedName("ingot_schrabidium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":ingot_schrabidium");
		plate_schrabidium = new ItemCustomLore().setUnlocalizedName("plate_schrabidium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":plate_schrabidium");
		plate_copper = new Item().setUnlocalizedName("plate_copper").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":plate_copper");
		plate_gold = new Item().setUnlocalizedName("plate_gold").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":plate_gold");

		nugget_uranium = new Item().setUnlocalizedName("nugget_uranium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_uranium");
		nugget_u235 = new Item().setUnlocalizedName("nugget_u235").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_uranium");
		nugget_u238 = new Item().setUnlocalizedName("nugget_u238").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_uranium");
		nugget_plutonium = new Item().setUnlocalizedName("nugget_plutonium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		nugget_pu238 = new Item().setUnlocalizedName("nugget_pu238").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		nugget_pu239 = new Item().setUnlocalizedName("nugget_pu239").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		nugget_pu240 = new Item().setUnlocalizedName("nugget_pu240").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_plutonium");
		nugget_neptunium = new Item().setUnlocalizedName("nugget_neptunium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_neptunium");
		plate_titanium = new Item().setUnlocalizedName("plate_titanium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":plate_titanium");
		plate_aluminium = new Item().setUnlocalizedName("plate_aluminium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":plate_aluminium");
		wire_red_copper = new Item().setUnlocalizedName("wire_red_copper").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":wire_red_copper");
		wire_tungsten = new ItemCustomLore().setUnlocalizedName("wire_tungsten").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":wire_tungsten");
		neutron_reflector = new Item().setUnlocalizedName("neutron_reflector").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":neutron_reflector");
		nugget_lead = new Item().setUnlocalizedName("nugget_lead").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_lead");
		nugget_schrabidium = new ItemCustomLore().setUnlocalizedName("nugget_schrabidium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_schrabidium");
		nugget_beryllium = new Item().setUnlocalizedName("nugget_beryllium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nugget_beryllium");

		powder_lead = new Item().setUnlocalizedName("powder_lead").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":powder_lead");
		powder_neptunium = new Item().setUnlocalizedName("powder_neptunium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":powder_neptunium");
		powder_schrabidium = new ItemCustomLore().setUnlocalizedName("powder_schrabidium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":powder_schrabidium");

		coil_copper = new Item().setUnlocalizedName("coil_copper").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":coil_copper");
		coil_copper_torus = new Item().setUnlocalizedName("coil_copper_torus").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":coil_copper_torus");
		coil_tungsten = new Item().setUnlocalizedName("coil_tungsten").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":coil_tungsten");
		tank_steel = new Item().setUnlocalizedName("tank_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":tank_steel");
		motor = new Item().setUnlocalizedName("motor").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":motor");
		centrifuge_element = new Item().setUnlocalizedName("centrifuge_element").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":centrifuge_element");
		centrifuge_tower = new Item().setUnlocalizedName("centrifuge_tower").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":centrifuge_tower");
		reactor_core = new Item().setUnlocalizedName("reactor_core").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":reactor_core");
		rtg_unit = new Item().setUnlocalizedName("rtg_unit").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":rtg_unit");
		thermo_unit_empty = new Item().setUnlocalizedName("thermo_unit_empty").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":thermo_unit_empty");
		thermo_unit_endo= new Item().setUnlocalizedName("thermo_unit_endo").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":thermo_unit_endo");
		thermo_unit_exo = new Item().setUnlocalizedName("thermo_unit_exo").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":thermo_unit_exo");
		levitation_unit = new Item().setUnlocalizedName("levitation_unit").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":levitation_unit");
		wire_aluminium = new Item().setUnlocalizedName("wire_aluminium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":wire_aluminium");
		wire_copper = new Item().setUnlocalizedName("wire_copper").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":wire_copper");
		wire_gold = new Item().setUnlocalizedName("wire_gold").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":wire_gold");
		wire_schrabidium = new ItemCustomLore().setUnlocalizedName("wire_schrabidium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":wire_schrabidium");
		
		cap_aluminium = new Item().setUnlocalizedName("cap_aluminium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":cap_aluminium");      
		hull_small_steel = new Item().setUnlocalizedName("hull_small_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":hull_small_steel");
		hull_small_aluminium = new Item().setUnlocalizedName("hull_small_aluminium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":hull_small_aluminium");
		hull_big_steel = new Item().setUnlocalizedName("hull_big_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":hull_big_steel");
		hull_big_aluminium = new Item().setUnlocalizedName("hull_big_aluminium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":hull_big_aluminium");
		hull_big_titanium = new Item().setUnlocalizedName("hull_big_titanium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":hull_big_titanium");
		fins_flat = new Item().setUnlocalizedName("fins_flat").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":fins_flat");
		fins_small_steel = new Item().setUnlocalizedName("fins_small_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":fins_small_steel");
		fins_big_steel = new Item().setUnlocalizedName("fins_big_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":fins_big_steel");
		fins_tri_steel = new Item().setUnlocalizedName("fins_tri_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":fins_tri_steel");
		fins_quad_titanium = new Item().setUnlocalizedName("fins_quad_titanium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":fins_quad_titanium");
        sphere_steel = new Item().setUnlocalizedName("sphere_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":sphere_steel");
		pedestal_steel = new Item().setUnlocalizedName("pedestal_steel").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":pedestal_steel");
		dysfunctional_reactor = new Item().setUnlocalizedName("dysfunctional_reactor").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":dysfunctional_reactor");
		
		circuit_aluminium = new Item().setUnlocalizedName("circuit_aluminium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":circuit_aluminium");
		circuit_copper = new Item().setUnlocalizedName("circuit_copper").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":circuit_copper");
		circuit_red_copper = new Item().setUnlocalizedName("circuit_red_copper").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":circuit_red_copper");
		circuit_gold = new Item().setUnlocalizedName("circuit_gold").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":circuit_gold");
		circuit_schrabidium = new ItemCustomLore().setUnlocalizedName("circuit_schrabidium").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":circuit_schrabidium");
		
		pellet_rtg = new ItemCustomLore().setUnlocalizedName("pellet_rtg").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":pellet_rtg");

		cell_empty = new Item().setUnlocalizedName("cell_empty").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":cell_empty");
		cell_uf6 = new Item().setUnlocalizedName("cell_uf6").setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_uf6");
		cell_puf6 = new Item().setUnlocalizedName("cell_puf6").setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_puf6");
		cell_antimatter = new Item().setUnlocalizedName("cell_antimatter").setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_antimatter");
		cell_deuterium = new Item().setUnlocalizedName("cell_deuterium").setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_deuterium");
		cell_sas3 = new ItemCustomLore().setUnlocalizedName("cell_sas3").setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.cell_empty).setTextureName(RefStrings.MODID + ":cell_sas3");

		syringe_empty = new Item().setUnlocalizedName("syringe_empty").setFull3D().setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":syringe_empty");
		syringe_antidote = new ItemSyringe().setUnlocalizedName("syringe_antidote").setFull3D().setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":syringe_antidote");
		syringe_awesome = new ItemSyringe().setUnlocalizedName("syringe_awesome").setFull3D().setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":syringe_awesome");
		syringe_metal_empty = new Item().setUnlocalizedName("syringe_metal_empty").setFull3D().setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":syringe_metal_empty");
		syringe_metal_stimpak = new ItemSyringe().setUnlocalizedName("syringe_metal_stimpak").setFull3D().setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":syringe_metal_stimpak");

		rod_empty = new Item().setUnlocalizedName("rod_empty").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":rod_empty");
		rod_uranium = new ItemCustomLore().setUnlocalizedName("rod_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium");
		rod_u235 = new ItemCustomLore().setUnlocalizedName("rod_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium");
		rod_u238 = new ItemCustomLore().setUnlocalizedName("rod_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium");
		rod_plutonium = new ItemCustomLore().setUnlocalizedName("rod_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium");
		rod_pu238 = new ItemCustomLore().setUnlocalizedName("rod_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium");
		rod_pu239 = new ItemCustomLore().setUnlocalizedName("rod_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium");
		rod_pu240 = new ItemCustomLore().setUnlocalizedName("rod_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium");
		rod_neptunium = new ItemCustomLore().setUnlocalizedName("rod_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_neptunium");
		rod_lead = new Item().setUnlocalizedName("rod_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_lead");
		rod_schrabidium = new ItemCustomLore().setUnlocalizedName("rod_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium");

		rod_dual_empty = new Item().setUnlocalizedName("rod_dual_empty").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":rod_dual_empty");
		rod_dual_uranium = new ItemCustomLore().setUnlocalizedName("rod_dual_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium");
		rod_dual_u235 = new ItemCustomLore().setUnlocalizedName("rod_dual_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium");
		rod_dual_u238 = new ItemCustomLore().setUnlocalizedName("rod_dual_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium");
		rod_dual_plutonium = new ItemCustomLore().setUnlocalizedName("rod_dual_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium");
		rod_dual_pu238 = new ItemCustomLore().setUnlocalizedName("rod_dual_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium");
		rod_dual_pu239 = new ItemCustomLore().setUnlocalizedName("rod_dual_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium");
		rod_dual_pu240 = new ItemCustomLore().setUnlocalizedName("rod_dual_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium");
		rod_dual_neptunium = new ItemCustomLore().setUnlocalizedName("rod_dual_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_neptunium");
		rod_dual_lead = new Item().setUnlocalizedName("rod_dual_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_lead");
		rod_dual_schrabidium = new ItemCustomLore().setUnlocalizedName("rod_dual_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium");

		rod_quad_empty = new Item().setUnlocalizedName("rod_quad_empty").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":rod_quad_empty");
		rod_quad_uranium = new ItemCustomLore().setUnlocalizedName("rod_quad_uranium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium");
		rod_quad_u235 = new ItemCustomLore().setUnlocalizedName("rod_quad_u235").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium");
		rod_quad_u238 = new ItemCustomLore().setUnlocalizedName("rod_quad_u238").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium");
		rod_quad_plutonium = new ItemCustomLore().setUnlocalizedName("rod_quad_plutonium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium");
		rod_quad_pu238 = new ItemCustomLore().setUnlocalizedName("rod_quad_pu238").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium");
		rod_quad_pu239 = new ItemCustomLore().setUnlocalizedName("rod_quad_pu239").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium");
		rod_quad_pu240 = new ItemCustomLore().setUnlocalizedName("rod_quad_pu240").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium");
		rod_quad_neptunium = new ItemCustomLore().setUnlocalizedName("rod_quad_neptunium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_neptunium");
		rod_quad_lead = new Item().setUnlocalizedName("rod_quad_lead").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_lead");
		rod_quad_schrabidium = new ItemCustomLore().setUnlocalizedName("rod_quad_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium");

		rod_uranium_fuel = new ItemFuelRod().setUnlocalizedName("rod_uranium_fuel").setMaxStackSize(1).setMaxDamage(10000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium");
		rod_dual_uranium_fuel = new ItemFuelRod().setUnlocalizedName("rod_dual_uranium_fuel").setMaxStackSize(1).setMaxDamage(20000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium");
		rod_quad_uranium_fuel = new ItemFuelRod().setUnlocalizedName("rod_quad_uranium_fuel").setMaxStackSize(1).setMaxDamage(40000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium");
		rod_plutonium_fuel = new ItemFuelRod().setUnlocalizedName("rod_plutonium_fuel").setMaxStackSize(1).setMaxDamage(25000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium");
		rod_dual_plutonium_fuel = new ItemFuelRod().setUnlocalizedName("rod_dual_plutonium_fuel").setMaxStackSize(1).setMaxDamage(50000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium");
		rod_quad_plutonium_fuel = new ItemFuelRod().setUnlocalizedName("rod_quad_plutonium_fuel").setMaxStackSize(1).setMaxDamage(100000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium");
		rod_mox_fuel = new ItemFuelRod().setUnlocalizedName("rod_mox_fuel").setMaxStackSize(1).setMaxDamage(100000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_mox_fuel");
		rod_dual_mox_fuel = new ItemFuelRod().setUnlocalizedName("rod_dual_mox_fuel").setMaxStackSize(1).setMaxDamage(200000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_mox_fuel");
		rod_quad_mox_fuel = new ItemFuelRod().setUnlocalizedName("rod_quad_mox_fuel").setMaxStackSize(1).setMaxDamage(400000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_mox_fuel");
		rod_schrabidium_fuel = new ItemFuelRod().setUnlocalizedName("rod_schrabidium_fuel").setMaxStackSize(1).setMaxDamage(2500000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium_fuel");
		rod_dual_schrabidium_fuel = new ItemFuelRod().setUnlocalizedName("rod_dual_schrabidium_fuel").setMaxStackSize(1).setMaxDamage(5000000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium_fuel");
		rod_quad_schrabidium_fuel = new ItemFuelRod().setUnlocalizedName("rod_quad_schrabidium_fuel").setMaxStackSize(1).setMaxDamage(10000000).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium_fuel");

		rod_water= new ItemCustomLore().setUnlocalizedName("rod_water").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_water");
		rod_dual_water = new ItemCustomLore().setUnlocalizedName("rod_dual_water").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_water");
		rod_quad_water = new ItemCustomLore().setUnlocalizedName("rod_quad_water").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_water");

		rod_coolant = new ItemCustomLore().setUnlocalizedName("rod_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_coolant");
		rod_dual_coolant = new ItemCustomLore().setUnlocalizedName("rod_dual_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_coolant");
		rod_quad_coolant = new ItemCustomLore().setUnlocalizedName("rod_quad_coolant").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_coolant");

		trinitite = new Item().setUnlocalizedName("trinitite").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":trinitite_new");
		nuclear_waste = new Item().setUnlocalizedName("nuclear_waste").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":nuclear_waste");
		rod_uranium_fuel_depleted = new Item().setUnlocalizedName("rod_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_uranium_fuel_depleted");
		rod_dual_uranium_fuel_depleted = new Item().setUnlocalizedName("rod_dual_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_uranium_fuel_depleted");
		rod_quad_uranium_fuel_depleted = new Item().setUnlocalizedName("rod_quad_uranium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_uranium_fuel_depleted");
		rod_plutonium_fuel_depleted = new Item().setUnlocalizedName("rod_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_plutonium_fuel_depleted");
		rod_dual_plutonium_fuel_depleted = new Item().setUnlocalizedName("rod_dual_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_plutonium_fuel_depleted");
		rod_quad_plutonium_fuel_depleted = new Item().setUnlocalizedName("rod_quad_plutonium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_plutonium_fuel_depleted");
		rod_mox_fuel_depleted = new Item().setUnlocalizedName("rod_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_mox_fuel_depleted");
		rod_dual_mox_fuel_depleted = new Item().setUnlocalizedName("rod_dual_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_mox_fuel_depleted");
		rod_quad_mox_fuel_depleted = new Item().setUnlocalizedName("rod_quad_mox_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_mox_fuel_depleted");
		rod_schrabidium_fuel_depleted = new Item().setUnlocalizedName("rod_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_schrabidium_fuel_depleted");
		rod_dual_schrabidium_fuel_depleted = new Item().setUnlocalizedName("rod_dual_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_schrabidium_fuel_depleted");
		rod_quad_schrabidium_fuel_depleted = new Item().setUnlocalizedName("rod_quad_schrabidium_fuel_depleted").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_schrabidium_fuel_depleted");
		rod_waste = new Item().setUnlocalizedName("rod_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_empty).setTextureName(RefStrings.MODID + ":rod_waste");
		rod_dual_waste = new Item().setUnlocalizedName("rod_dual_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_dual_empty).setTextureName(RefStrings.MODID + ":rod_dual_waste");
		rod_quad_waste = new Item().setUnlocalizedName("rod_quad_waste").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_waste");

		pellet_cluster = new ItemCustomLore().setUnlocalizedName("pellet_cluster").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":pellet_cluster");
		powder_fire = new ItemCustomLore().setUnlocalizedName("powder_fire").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":powder_fire");
		powder_poison = new ItemCustomLore().setUnlocalizedName("powder_poison").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":powder_poison");
		pellet_gas = new ItemCustomLore().setUnlocalizedName("pellet_gas").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":pellet_gas");

		designator = new ItemDesingator().setUnlocalizedName("designator").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":designator");
		missile_generic = new Item().setUnlocalizedName("missile_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_generic");
		missile_anti_ballistic = new Item().setUnlocalizedName("missile_anti_ballistic").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_anti_ballistic");
		missile_incendiary = new Item().setUnlocalizedName("missile_incendiary").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_incendiary");
		missile_cluster = new Item().setUnlocalizedName("missile_cluster").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_cluster");
		missile_buster = new Item().setUnlocalizedName("missile_buster").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_buster");
		missile_strong = new Item().setUnlocalizedName("missile_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_strong");
		missile_incendiary_strong = new Item().setUnlocalizedName("missile_incendiary_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_incendiary_strong");
		missile_cluster_strong = new Item().setUnlocalizedName("missile_cluster_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_cluster_strong");
		missile_buster_strong = new Item().setUnlocalizedName("missile_buster_strong").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_buster_strong");
		missile_burst = new Item().setUnlocalizedName("missile_burst").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_burst");
		missile_inferno = new Item().setUnlocalizedName("missile_inferno").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_inferno");
		missile_rain = new Item().setUnlocalizedName("missile_rain").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_rain");
		missile_drill = new Item().setUnlocalizedName("missile_drill").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_drill");
		missile_nuclear = new Item().setUnlocalizedName("missile_nuclear").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_nuclear");
		missile_nuclear_cluster = new Item().setUnlocalizedName("missile_nuclear_cluster").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_nuclear_cluster");
		missile_endo = new Item().setUnlocalizedName("missile_endo").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_endo");
		missile_exo = new Item().setUnlocalizedName("missile_exo").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_exo");

		gun_rpg = new GunRpg().setUnlocalizedName("gun_rpg").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_rpg");
		gun_rpg_ammo = new Item().setUnlocalizedName("gun_rpg_ammo").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_rpg_ammo");
		gun_revolver_ammo = new Item().setUnlocalizedName("gun_revolver_ammo").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_ammo");
		gun_revolver = new GunRevolver(gun_revolver_ammo, 10, 25, false).setMaxDamage(500).setUnlocalizedName("gun_revolver").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver");
		gun_revolver_iron_ammo = new Item().setUnlocalizedName("gun_revolver_iron_ammo").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_iron_ammo");
		gun_revolver_iron = new GunRevolver(gun_revolver_iron_ammo, 5, 15, false).setMaxDamage(100).setUnlocalizedName("gun_revolver_iron").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_iron");
		gun_revolver_gold_ammo = new Item().setUnlocalizedName("gun_revolver_gold_ammo").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_gold_ammo");
		gun_revolver_gold = new GunRevolver(gun_revolver_gold_ammo, 20, 30, false).setMaxDamage(1000).setUnlocalizedName("gun_revolver_gold").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_gold");
		gun_revolver_schrabidium_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_schrabidium_ammo").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_schrabidium_ammo");
		gun_revolver_schrabidium = new GunRevolver(gun_revolver_schrabidium_ammo, 10000, 100000, true).setMaxDamage(100000).setUnlocalizedName("gun_revolver_schrabidium").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_schrabidium");
		gun_revolver_cursed_ammo = new ItemCustomLore().setUnlocalizedName("gun_revolver_cursed_ammo").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_cursed_ammo");
		gun_revolver_cursed = new GunRevolver(gun_revolver_cursed_ammo, 25, 40, false).setMaxDamage(5000).setUnlocalizedName("gun_revolver_cursed").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gun_revolver_cursed");
		
		grenade_generic = new ItemGrenade().setUnlocalizedName("grenade_generic").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_generic");
		grenade_strong = new ItemGrenade().setUnlocalizedName("grenade_strong").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_strong");
		grenade_frag = new ItemGrenade().setUnlocalizedName("grenade_frag").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_frag");
		grenade_fire = new ItemGrenade().setUnlocalizedName("grenade_fire").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_fire");
		grenade_cluster = new ItemGrenade().setUnlocalizedName("grenade_cluster").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_cluster");
		grenade_flare = new ItemGrenade().setUnlocalizedName("grenade_flare").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_flare");
		grenade_electric = new ItemGrenade().setUnlocalizedName("grenade_electric").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_electric");
		grenade_poison = new ItemGrenade().setUnlocalizedName("grenade_poison").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_poison");
		grenade_gas = new ItemGrenade().setUnlocalizedName("grenade_gas").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_gas");
		grenade_schrabidium = new ItemGrenade().setUnlocalizedName("grenade_schrabidium").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_schrabidium");
		grenade_nuke = new ItemGrenade().setUnlocalizedName("grenade_nuke").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_nuke");

		bomb_waffle = new ItemWaffle(20, false).setUnlocalizedName("bomb_waffle").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":bomb_waffle");
		schnitzel_vegan = new ItemSchnitzelVegan(0, true).setUnlocalizedName("schnitzel_vegan").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":schnitzel_vegan");
		cotton_candy = new ItemCottonCandy(5, false).setUnlocalizedName("cotton_candy").setCreativeTab(MainRegistry.tabNuke).setFull3D().setTextureName(RefStrings.MODID + ":cotton_candy");
		apple_schrabidium = new ItemAppleSchrabidium(20, 100, false).setUnlocalizedName("apple_schrabidium").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":apple_schrabidium");
		
		flame_pony = new ItemCustomLore().setUnlocalizedName("flame_pony").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":flame_pony");
		flame_conspiracy = new ItemCustomLore().setUnlocalizedName("flame_conspiracy").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":flame_conspiracy");
		flame_politics = new ItemCustomLore().setUnlocalizedName("flame_politics").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":flame_politics");
		flame_opinion = new ItemCustomLore().setUnlocalizedName("flame_opinion").setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":flame_opinion");
		
		gadget_explosive = new Item().setUnlocalizedName("gadget_explosive").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gadget_explosive");
		gadget_explosive8 = new ItemGadget().setUnlocalizedName("gadget_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gadget_explosive8");
		gadget_wireing = new ItemGadget().setUnlocalizedName("gadget_wireing").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gadget_wireing");
		gadget_core = new ItemGadget().setUnlocalizedName("gadget_core").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gadget_core");

		boy_igniter = new ItemBoy().setUnlocalizedName("boy_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":boy_igniter");
		boy_propellant = new ItemBoy().setUnlocalizedName("boy_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":boy_propellant");
		boy_bullet = new ItemBoy().setUnlocalizedName("boy_bullet").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":boy_bullet");
		boy_target = new ItemBoy().setUnlocalizedName("boy_target").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":boy_target");
		boy_shielding = new ItemBoy().setUnlocalizedName("boy_shielding").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":boy_shielding");
		
		man_explosive = new Item().setUnlocalizedName("man_explosive").setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":man_explosive");
		man_explosive8 = new ItemManMike().setUnlocalizedName("man_explosive8").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":man_explosive8");
		man_igniter = new ItemMan().setUnlocalizedName("man_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":man_igniter");
		man_core = new ItemManMike().setUnlocalizedName("man_core").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":man_core");

		mike_core = new ItemMike().setUnlocalizedName("mike_core").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":mike_core");
		mike_deut = new ItemMike().setUnlocalizedName("mike_deut").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setContainerItem(ModItems.tank_steel).setTextureName(RefStrings.MODID + ":mike_deut");
		mike_cooling_unit = new ItemMike().setUnlocalizedName("mike_cooling_unit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":mike_cooling_unit");

		tsar_core = new ItemTsar().setUnlocalizedName("tsar_core").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":tsar_core");

		fleija_igniter = new ItemFleija().setUnlocalizedName("fleija_igniter").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":fleija_igniter");
		fleija_propellant = new ItemFleija().setUnlocalizedName("fleija_propellant").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":fleija_propellant");
		fleija_core = new ItemFleija().setUnlocalizedName("fleija_core").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":fleija_core");

		battery_generic = new ItemBattery(50).setUnlocalizedName("battery_generic").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":battery_generic");
		battery_advanced = new ItemBattery(200).setUnlocalizedName("battery_advanced").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":battery_advanced");
		battery_schrabidium = new ItemBattery(1000).setUnlocalizedName("battery_schrabidium").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":battery_schrabidium");
		battery_creative = new Item().setUnlocalizedName("battery_creative").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":battery_creative");

		fusion_core = new ItemBattery(5000).setUnlocalizedName("fusion_core").setMaxStackSize(1).setCreativeTab(MainRegistry.tabParts).setTextureName(RefStrings.MODID + ":fusion_core");
		
		nuke_starter_kit = new ItemStarterKit().setUnlocalizedName("nuke_starter_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":nuke_starter_kit");
		nuke_advanced_kit = new ItemStarterKit().setUnlocalizedName("nuke_advanced_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":nuke_advanced_kit");
		nuke_commercially_kit = new ItemStarterKit().setUnlocalizedName("nuke_commercially_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":nuke_commercially_kit");
		nuke_electric_kit = new ItemStarterKit().setUnlocalizedName("nuke_electric_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":nuke_electric_kit");
		gadget_kit = new ItemStarterKit().setUnlocalizedName("gadget_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":gadget_kit");
		boy_kit = new ItemStarterKit().setUnlocalizedName("boy_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":boy_kit");
		man_kit = new ItemStarterKit().setUnlocalizedName("man_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":man_kit");
		mike_kit = new ItemStarterKit().setUnlocalizedName("mike_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":mike_kit");
		tsar_kit = new ItemStarterKit().setUnlocalizedName("tsar_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":tsar_kit");
		multi_kit = new ItemStarterKit().setUnlocalizedName("multi_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":multi_kit");
		grenade_kit = new ItemStarterKit().setUnlocalizedName("grenade_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":grenade_kit");
		fleija_kit = new ItemStarterKit().setUnlocalizedName("fleija_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":fleija_kit");
		prototype_kit = new ItemStarterKit().setUnlocalizedName("prototype_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":prototype_kit");
		missile_kit = new ItemStarterKit().setUnlocalizedName("missile_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":missile_kit");
		t45_kit = new ItemStarterKit().setUnlocalizedName("t45_kit").setMaxStackSize(1).setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":t45_kit");
		
		igniter = new ItemCustomLore().setUnlocalizedName("igniter").setMaxStackSize(1).setFull3D().setCreativeTab(MainRegistry.tabNuke).setTextureName(RefStrings.MODID + ":trigger");
		ingot_euphemium = new ItemCustomLore().setUnlocalizedName("ingot_euphemium").setTextureName(RefStrings.MODID + ":ingot_euphemium");
		nugget_euphemium = new ItemCustomLore().setUnlocalizedName("nugget_euphemium").setTextureName(RefStrings.MODID + ":nugget_euphemium");
		rod_quad_euphemium = new ItemCustomLore().setUnlocalizedName("rod_quad_euphemium").setMaxStackSize(1).setContainerItem(ModItems.rod_quad_empty).setTextureName(RefStrings.MODID + ":rod_quad_euphemium");
		watch = new ItemCustomLore().setUnlocalizedName("watch").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":watch");
		apple_euphemium = new ItemAppleEuphemium(20, 100, false).setUnlocalizedName("apple_euphemium").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":apple_euphemium");

		euphemium_helmet = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, 6, 0).setUnlocalizedName("euphemium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_helmet");
		euphemium_plate = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, 6, 1).setUnlocalizedName("euphemium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_plate");
		euphemium_legs = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, 6, 2).setUnlocalizedName("euphemium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_legs");
		euphemium_boots = new ArmorEuphemium(MainRegistry.enumArmorMaterialEuphemium, 6, 3).setUnlocalizedName("euphemium_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":euphemium_boots");

		goggles = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("goggles").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":goggles");
		gas_mask = new ArmorModel(ArmorMaterial.IRON, 7, 0).setUnlocalizedName("gas_mask").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":gas_mask");
		
		t45_helmet = new ArmorT45(MainRegistry.enumArmorMaterialT45, 2, 0).setUnlocalizedName("t45_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_helmet");
		t45_plate = new ArmorT45(MainRegistry.enumArmorMaterialT45, 2, 1).setUnlocalizedName("t45_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_plate");
		t45_legs = new ArmorT45(MainRegistry.enumArmorMaterialT45, 2, 2).setUnlocalizedName("t45_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_legs");
		t45_boots = new ArmorT45(MainRegistry.enumArmorMaterialT45, 2, 3).setUnlocalizedName("t45_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":t45_boots");
		
		schrabidium_helmet = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, 7, 0).setUnlocalizedName("schrabidium_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_helmet");
		schrabidium_plate = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, 7, 1).setUnlocalizedName("schrabidium_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_plate");
		schrabidium_legs = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, 7, 2).setUnlocalizedName("schrabidium_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_legs");
		schrabidium_boots = new ArmorSchrabidium(MainRegistry.enumArmorMaterialSchrabidium, 7, 3).setUnlocalizedName("schrabidium_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_boots");

		schrabidium_sword = new SwordSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_sword").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_sword");
		schrabidium_pickaxe = new PickaxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_pickaxe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_pickaxe");
		schrabidium_axe = new AxeSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_axe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_axe");
		schrabidium_shovel = new SpadeSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_shovel").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_shovel");
		schrabidium_hoe = new HoeSchrabidium(MainRegistry.enumToolMaterialSchrabidium).setUnlocalizedName("schrabidium_hoe").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":schrabidium_hoe");
		
		mask_of_infamy = new MaskOfInfamy(ArmorMaterial.IRON, 8, 0).setUnlocalizedName("mask_of_infamy").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":mask_of_infamy");

		hazmat_helmet = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, 9, 0).setUnlocalizedName("hazmat_helmet").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_helmet");
		hazmat_plate = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, 9, 1).setUnlocalizedName("hazmat_plate").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_plate");
		hazmat_legs = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, 9, 2).setUnlocalizedName("hazmat_legs").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_legs");
		hazmat_boots = new ArmorHazmat(MainRegistry.enumArmorMaterialHazmat, 9, 3).setUnlocalizedName("hazmat_boots").setMaxStackSize(1).setTextureName(RefStrings.MODID + ":hazmat_boots");
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
		GameRegistry.registerItem(ingot_plutonium, ingot_plutonium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu238, ingot_pu238.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu239, ingot_pu239.getUnlocalizedName());
		GameRegistry.registerItem(ingot_pu240, ingot_pu240.getUnlocalizedName());
		GameRegistry.registerItem(ingot_neptunium, ingot_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_titanium, ingot_titanium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_copper, ingot_copper.getUnlocalizedName());
		GameRegistry.registerItem(ingot_red_copper, ingot_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(ingot_tungsten, ingot_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(ingot_aluminium, ingot_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_steel, ingot_steel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_lead, ingot_lead.getUnlocalizedName());
		GameRegistry.registerItem(ingot_beryllium, ingot_beryllium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_schrabidium, ingot_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(ingot_uranium_fuel, ingot_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_plutonium_fuel, ingot_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_mox_fuel, ingot_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(ingot_schrabidium_fuel, ingot_schrabidium_fuel.getUnlocalizedName());

		//Dusts & Other
		GameRegistry.registerItem(sulfur, sulfur.getUnlocalizedName());
		GameRegistry.registerItem(niter, niter.getUnlocalizedName());
		GameRegistry.registerItem(fluorite, fluorite.getUnlocalizedName());
		GameRegistry.registerItem(powder_lead, powder_lead.getUnlocalizedName());
		GameRegistry.registerItem(powder_neptunium, powder_neptunium.getUnlocalizedName());
		GameRegistry.registerItem(powder_schrabidium, powder_schrabidium.getUnlocalizedName());
		
		//Powders
		GameRegistry.registerItem(powder_fire, powder_fire.getUnlocalizedName());
		GameRegistry.registerItem(powder_poison, powder_poison.getUnlocalizedName());

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
		GameRegistry.registerItem(nugget_uranium_fuel, nugget_uranium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(nugget_plutonium_fuel, nugget_plutonium_fuel.getUnlocalizedName());
		GameRegistry.registerItem(nugget_mox_fuel, nugget_mox_fuel.getUnlocalizedName());
		GameRegistry.registerItem(nugget_schrabidium_fuel, nugget_schrabidium_fuel.getUnlocalizedName());

		//Plates
		GameRegistry.registerItem(plate_titanium, plate_titanium.getUnlocalizedName());
		GameRegistry.registerItem(plate_aluminium, plate_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(plate_iron, plate_iron.getUnlocalizedName());
		GameRegistry.registerItem(plate_steel, plate_steel.getUnlocalizedName());
		GameRegistry.registerItem(plate_lead, plate_lead.getUnlocalizedName());
		GameRegistry.registerItem(neutron_reflector, neutron_reflector.getUnlocalizedName());
		GameRegistry.registerItem(plate_schrabidium, plate_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(plate_copper, plate_copper.getUnlocalizedName());
		GameRegistry.registerItem(plate_gold, plate_gold.getUnlocalizedName());
		
		//Wires
		GameRegistry.registerItem(wire_aluminium, wire_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(wire_copper, wire_copper.getUnlocalizedName());
		GameRegistry.registerItem(wire_tungsten, wire_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(wire_red_copper, wire_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(wire_gold, wire_gold.getUnlocalizedName());
		GameRegistry.registerItem(wire_schrabidium, wire_schrabidium.getUnlocalizedName());
		
		//Parts
		GameRegistry.registerItem(coil_copper, coil_copper.getUnlocalizedName());
		GameRegistry.registerItem(coil_copper_torus, coil_copper_torus.getUnlocalizedName());
		GameRegistry.registerItem(coil_tungsten, coil_tungsten.getUnlocalizedName());
		GameRegistry.registerItem(tank_steel, tank_steel.getUnlocalizedName());
		GameRegistry.registerItem(motor, motor.getUnlocalizedName());
		GameRegistry.registerItem(centrifuge_element, centrifuge_element.getUnlocalizedName());
		GameRegistry.registerItem(centrifuge_tower, centrifuge_tower.getUnlocalizedName());
		GameRegistry.registerItem(reactor_core, reactor_core.getUnlocalizedName());
		GameRegistry.registerItem(rtg_unit, rtg_unit.getUnlocalizedName());
		GameRegistry.registerItem(thermo_unit_empty, thermo_unit_empty.getUnlocalizedName());
		GameRegistry.registerItem(thermo_unit_endo, thermo_unit_endo.getUnlocalizedName());
		GameRegistry.registerItem(thermo_unit_exo, thermo_unit_exo.getUnlocalizedName());
		GameRegistry.registerItem(levitation_unit, levitation_unit.getUnlocalizedName());
		
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
		
		//Circuits
		GameRegistry.registerItem(circuit_aluminium, circuit_aluminium.getUnlocalizedName());
		GameRegistry.registerItem(circuit_copper, circuit_copper.getUnlocalizedName());
		GameRegistry.registerItem(circuit_red_copper, circuit_red_copper.getUnlocalizedName());
		GameRegistry.registerItem(circuit_gold, circuit_gold.getUnlocalizedName());
		GameRegistry.registerItem(circuit_schrabidium, circuit_schrabidium.getUnlocalizedName());
		
		//Flame War in a Box
		GameRegistry.registerItem(flame_pony, flame_pony.getUnlocalizedName());
		GameRegistry.registerItem(flame_conspiracy, flame_conspiracy.getUnlocalizedName());
		GameRegistry.registerItem(flame_politics, flame_politics.getUnlocalizedName());
		GameRegistry.registerItem(flame_opinion, flame_opinion.getUnlocalizedName());
		
		//Pellets
		GameRegistry.registerItem(pellet_rtg, pellet_rtg.getUnlocalizedName());
		GameRegistry.registerItem(pellet_cluster, pellet_cluster.getUnlocalizedName());
		GameRegistry.registerItem(pellet_gas, pellet_gas.getUnlocalizedName());
		
		//Cells
		GameRegistry.registerItem(cell_empty, cell_empty.getUnlocalizedName());
		GameRegistry.registerItem(cell_uf6, cell_uf6.getUnlocalizedName());
		GameRegistry.registerItem(cell_puf6, cell_puf6.getUnlocalizedName());
		GameRegistry.registerItem(cell_deuterium, cell_deuterium.getUnlocalizedName());
		GameRegistry.registerItem(cell_sas3, cell_sas3.getUnlocalizedName());
		GameRegistry.registerItem(cell_antimatter, cell_antimatter.getUnlocalizedName());
		
		//Batteries
		GameRegistry.registerItem(battery_generic, battery_generic.getUnlocalizedName());
		GameRegistry.registerItem(battery_advanced, battery_advanced.getUnlocalizedName());
		GameRegistry.registerItem(battery_schrabidium, battery_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(battery_creative, battery_creative.getUnlocalizedName());
		GameRegistry.registerItem(fusion_core, fusion_core.getUnlocalizedName());
		
		//Fuelrods
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
		
		GameRegistry.registerItem(rod_waste, rod_waste.getUnlocalizedName());
		GameRegistry.registerItem(rod_dual_waste, rod_dual_waste.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_waste, rod_quad_waste.getUnlocalizedName());
		
		GameRegistry.registerItem(trinitite, trinitite.getUnlocalizedName());
		GameRegistry.registerItem(nuclear_waste, nuclear_waste.getUnlocalizedName());
		
		//Missiles
		GameRegistry.registerItem(designator, designator.getUnlocalizedName());
		GameRegistry.registerItem(missile_generic, missile_generic.getUnlocalizedName());
		//GameRegistry.registerItem(missile_anti_ballistic, missile_anti_ballistic.getUnlocalizedName());
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
		
		//Guns
		GameRegistry.registerItem(gun_revolver_iron, gun_revolver_iron.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_iron_ammo, gun_revolver_iron_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver, gun_revolver.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_ammo, gun_revolver_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_gold, gun_revolver_gold.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_gold_ammo, gun_revolver_gold_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_schrabidium, gun_revolver_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_schrabidium_ammo, gun_revolver_schrabidium_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_cursed, gun_revolver_cursed.getUnlocalizedName());
		GameRegistry.registerItem(gun_revolver_cursed_ammo, gun_revolver_cursed_ammo.getUnlocalizedName());
		GameRegistry.registerItem(gun_rpg, gun_rpg.getUnlocalizedName());
		GameRegistry.registerItem(gun_rpg_ammo, gun_rpg_ammo.getUnlocalizedName());
		
		//Grenades
		GameRegistry.registerItem(grenade_generic, grenade_generic.getUnlocalizedName());
		GameRegistry.registerItem(grenade_strong, grenade_strong.getUnlocalizedName());
		GameRegistry.registerItem(grenade_frag, grenade_frag.getUnlocalizedName());
		GameRegistry.registerItem(grenade_fire, grenade_fire.getUnlocalizedName());
		GameRegistry.registerItem(grenade_cluster, grenade_cluster.getUnlocalizedName());
		GameRegistry.registerItem(grenade_flare, grenade_flare.getUnlocalizedName());
		GameRegistry.registerItem(grenade_electric, grenade_electric.getUnlocalizedName());
		GameRegistry.registerItem(grenade_poison, grenade_poison.getUnlocalizedName());
		GameRegistry.registerItem(grenade_gas, grenade_gas.getUnlocalizedName());
		GameRegistry.registerItem(grenade_schrabidium, grenade_schrabidium.getUnlocalizedName());
		GameRegistry.registerItem(grenade_nuke, grenade_nuke.getUnlocalizedName());
		
		//Tools
		GameRegistry.registerItem(schrabidium_sword, schrabidium_sword.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_pickaxe, schrabidium_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_axe, schrabidium_axe.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_shovel, schrabidium_shovel.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_hoe, schrabidium_hoe.getUnlocalizedName());
		
		//Syringes
		GameRegistry.registerItem(syringe_empty, syringe_empty.getUnlocalizedName());
		GameRegistry.registerItem(syringe_antidote, syringe_antidote.getUnlocalizedName());
		GameRegistry.registerItem(syringe_awesome, syringe_awesome.getUnlocalizedName());
		GameRegistry.registerItem(syringe_metal_empty, syringe_metal_empty.getUnlocalizedName());
		GameRegistry.registerItem(syringe_metal_stimpak, syringe_metal_stimpak.getUnlocalizedName());
		
		//Food
		GameRegistry.registerItem(bomb_waffle, bomb_waffle.getUnlocalizedName());
		GameRegistry.registerItem(schnitzel_vegan, schnitzel_vegan.getUnlocalizedName());
		GameRegistry.registerItem(cotton_candy, cotton_candy.getUnlocalizedName());
		GameRegistry.registerItem(apple_schrabidium, apple_schrabidium.getUnlocalizedName());

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
		
		//Conventional Armor
		GameRegistry.registerItem(goggles, goggles.getUnlocalizedName());
		GameRegistry.registerItem(gas_mask, gas_mask.getUnlocalizedName());
		
		//Power Armor
		GameRegistry.registerItem(t45_helmet, t45_helmet.getUnlocalizedName());
		GameRegistry.registerItem(t45_plate, t45_plate.getUnlocalizedName());
		GameRegistry.registerItem(t45_legs, t45_legs.getUnlocalizedName());
		GameRegistry.registerItem(t45_boots, t45_boots.getUnlocalizedName());
		
		//Nobody will ever read this anyway, so it shouldn't matter.
		GameRegistry.registerItem(igniter, igniter.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_helmet, hazmat_helmet.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_plate, hazmat_plate.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_legs, hazmat_legs.getUnlocalizedName());
		GameRegistry.registerItem(hazmat_boots, hazmat_boots.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_helmet, schrabidium_helmet.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_plate, schrabidium_plate.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_legs, schrabidium_legs.getUnlocalizedName());
		GameRegistry.registerItem(schrabidium_boots, schrabidium_boots.getUnlocalizedName());
		GameRegistry.registerItem(rod_quad_euphemium, rod_quad_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_helmet, euphemium_helmet.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_plate, euphemium_plate.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_legs, euphemium_legs.getUnlocalizedName());
		GameRegistry.registerItem(euphemium_boots, euphemium_boots.getUnlocalizedName());
		GameRegistry.registerItem(ingot_euphemium, ingot_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(nugget_euphemium, nugget_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(apple_euphemium, apple_euphemium.getUnlocalizedName());
		GameRegistry.registerItem(watch, watch.getUnlocalizedName());
		GameRegistry.registerItem(mask_of_infamy, mask_of_infamy.getUnlocalizedName());
		
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
		GameRegistry.registerItem(multi_kit, multi_kit.getUnlocalizedName());
		GameRegistry.registerItem(missile_kit, missile_kit.getUnlocalizedName());
		GameRegistry.registerItem(grenade_kit, grenade_kit.getUnlocalizedName());
		GameRegistry.registerItem(t45_kit, t45_kit.getUnlocalizedName());
	}
}
