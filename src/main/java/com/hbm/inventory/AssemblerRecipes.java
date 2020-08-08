package com.hbm.inventory;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@Untested
public class AssemblerRecipes {

	public static File config;
	public static File template;
	private static final Gson gson = new Gson();
	public static HashMap<ComparableStack, Object[]> recipes = new HashMap();
	public static HashMap<ComparableStack, Integer> time = new HashMap();
	public static List<ComparableStack> recipeList = new ArrayList();
	
	/**
	 * Pre-Init phase: Finds the recipe config (if exists) and checks if a template is present, if not it generates one.
	 * @param dir The suggested config folder
	 */
	public static void preInit(File dir) {
		
		if(dir == null || !dir.isDirectory())
			return;
		
		List<File> files = Arrays.asList(dir.listFiles());
		
		boolean needsTemplate = true;
		
		for(File file : files) {
			if(file.getName().equals("hbmAssembler.json")) {
				
				config = file;
			}
			
			if(file.getName().equals("hbmAssemblerTemplate.json")) {
				
				needsTemplate = false;
			}
		}
		
		if(needsTemplate)
			saveTemplateJSON();
	}
	
	public static void loadRecipes() {
		
		if(config == null)
			registerDefaults();
		else
			loadJSONRecipes();
		
		generateList();
	}
	
	/**
	 * Generates an ordered list of outputs, used by the template item to generate subitems
	 */
	private static void generateList() {
		
		List<ComparableStack> list = new ArrayList(recipes.keySet());
		Collections.sort(list);
		recipeList = list;
	}
	
	/**
	 * Registers regular recipes if there's no custom confiuration
	 */
	private static void registerDefaults() {

		makeRecipe(new ComparableStack(ModItems.plate_iron, 2),
				new Object[] {new OreDictStack("ingotIron", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_gold, 2),
				new Object[] {new OreDictStack("ingotGold", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_titanium, 2),
				new Object[] {new OreDictStack("ingotTitanium", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_aluminium, 2),
				new Object[] {new OreDictStack("ingotAluminum", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_steel, 2),
				new Object[] {new OreDictStack("ingotSteel", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.plate_lead, 2),
				new Object[] {new OreDictStack("ingotLead", 3)},
				20);
		makeRecipe(new ComparableStack(ModItems.asbestos_cloth, 4),
				new Object[] {new OreDictStack("ingotAsbestos", 2), new ComparableStack(Items.string, 6)},
				20);
		
		makeRecipe(new ComparableStack(ModItems.plate_iron, 2), new Object[] {new ComparableStack(Items.iron_ingot, 3, 0), },20);
		makeRecipe(new ComparableStack(ModItems.plate_gold, 2), new Object[] {new ComparableStack(Items.gold_ingot, 3, 0), },20);
		makeRecipe(new ComparableStack(ModItems.plate_titanium, 2), new Object[] {new ComparableStack(ModItems.ingot_titanium, 3, 0), },20);
		makeRecipe(new ComparableStack(ModItems.plate_aluminium, 2), new Object[] {new ComparableStack(ModItems.ingot_aluminium, 3, 0), },20);
		makeRecipe(new ComparableStack(ModItems.plate_steel, 2), new Object[] {new ComparableStack(ModItems.ingot_steel, 3, 0), },20);
		makeRecipe(new ComparableStack(ModItems.plate_lead, 2), new Object[] {new ComparableStack(ModItems.ingot_lead, 3, 0), },20);
		makeRecipe(new ComparableStack(ModItems.plate_copper, 2), new Object[] {new ComparableStack(ModItems.ingot_copper, 3, 0), },20);
		makeRecipe(new ComparableStack(ModItems.plate_advanced_alloy, 2), new Object[] {new ComparableStack(ModItems.ingot_advanced_alloy, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.plate_schrabidium, 2), new Object[] {new ComparableStack(ModItems.ingot_schrabidium, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.plate_combine_steel, 2), new Object[] {new ComparableStack(ModItems.ingot_combine_steel, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.plate_saturnite, 2), new Object[] {new ComparableStack(ModItems.ingot_saturnite, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.plate_mixed, 6), new Object[] {new ComparableStack(ModItems.plate_advanced_alloy, 2, 0), new ComparableStack(ModItems.neutron_reflector, 2, 0), new ComparableStack(ModItems.plate_combine_steel, 1, 0), new ComparableStack(ModItems.plate_lead, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.wire_aluminium, 6), new Object[] {new ComparableStack(ModItems.ingot_aluminium, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.wire_copper, 6), new Object[] {new ComparableStack(ModItems.ingot_copper, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.wire_tungsten, 6), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.wire_red_copper, 6), new Object[] {new ComparableStack(ModItems.ingot_red_copper, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.wire_advanced_alloy, 6), new Object[] {new ComparableStack(ModItems.ingot_advanced_alloy, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.wire_gold, 6), new Object[] {new ComparableStack(Items.gold_ingot, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.wire_schrabidium, 6), new Object[] {new ComparableStack(ModItems.ingot_schrabidium, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.wire_magnetized_tungsten, 6), new Object[] {new ComparableStack(ModItems.ingot_magnetized_tungsten, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.hazmat_cloth, 4), new Object[] {new ComparableStack(ModItems.powder_lead, 4, 0), new ComparableStack(Items.string, 8, 0), },100);
		makeRecipe(new ComparableStack(ModItems.asbestos_cloth, 4), new Object[] {new ComparableStack(ModItems.ingot_asbestos, 2, 0), new ComparableStack(Items.string, 6, 0), new ComparableStack(Blocks.wool, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.filter_coal, 1), new Object[] {new ComparableStack(ModItems.powder_coal, 4, 0), new ComparableStack(Items.string, 6, 0), new ComparableStack(Items.paper, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.centrifuge_element, 1), new Object[] {new ComparableStack(ModItems.tank_steel, 2, 0), new ComparableStack(ModItems.coil_tungsten, 2, 0), new ComparableStack(ModItems.wire_red_copper, 6, 0), new ComparableStack(ModItems.motor, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.centrifuge_tower, 1), new Object[] {new ComparableStack(ModItems.centrifuge_element, 4, 0), new ComparableStack(ModItems.plate_steel, 4, 0), new ComparableStack(ModItems.wire_red_copper, 6, 0), new ComparableStack(ModItems.powder_lapis, 2, 0), new ComparableStack(ModItems.ingot_polymer, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.magnet_dee, 1), new Object[] {new ComparableStack(ModBlocks.fusion_conductor, 6, 0), new ComparableStack(ModItems.ingot_steel, 3, 0), new ComparableStack(ModItems.coil_advanced_torus, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.magnet_circular, 1), new Object[] {new ComparableStack(ModBlocks.fusion_conductor, 5, 0), new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.plate_advanced_alloy, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.cyclotron_tower, 1), new Object[] {new ComparableStack(ModItems.magnet_circular, 6, 0), new ComparableStack(ModItems.magnet_dee, 3, 0), new ComparableStack(ModItems.plate_steel, 12, 0), new ComparableStack(ModItems.wire_advanced_alloy, 8, 0), new ComparableStack(ModItems.plate_polymer, 24, 0), },100);
		makeRecipe(new ComparableStack(ModItems.reactor_core, 1), new Object[] {new ComparableStack(ModItems.ingot_lead, 4, 0), new ComparableStack(ModItems.plate_steel, 4, 0), new ComparableStack(ModItems.neutron_reflector, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.rtg_unit, 2), new Object[] {new ComparableStack(ModItems.thermo_element, 6, 0), new ComparableStack(ModItems.board_copper, 2, 0), new ComparableStack(ModItems.ingot_lead, 4, 0), new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.circuit_copper, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.thermo_unit_empty, 1), new Object[] {new ComparableStack(ModItems.coil_copper_torus, 3, 0), new ComparableStack(ModItems.ingot_steel, 3, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.plate_polymer, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.levitation_unit, 1), new Object[] {new ComparableStack(ModItems.coil_copper, 4, 0), new ComparableStack(ModItems.coil_tungsten, 2, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.nugget_schrabidium, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.drill_titanium, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.ingot_dura_steel, 2, 0), new ComparableStack(ModItems.bolt_dura_steel, 2, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.telepad, 1), new Object[] {new ComparableStack(ModItems.ingot_polymer, 12, 0), new ComparableStack(ModItems.plate_schrabidium, 2, 0), new ComparableStack(ModItems.plate_combine_steel, 4, 0), new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.wire_gold, 6, 0), new ComparableStack(ModItems.circuit_schrabidium, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.entanglement_kit, 1), new Object[] {new ComparableStack(ModItems.coil_magnetized_tungsten, 6, 0), new ComparableStack(ModItems.plate_lead, 16, 0), new ComparableStack(ModItems.neutron_reflector, 4, 0), new ComparableStack(ModItems.singularity_counter_resonant, 1, 0), new ComparableStack(ModItems.singularity_super_heated, 1, 0), new ComparableStack(ModItems.powder_power, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.dysfunctional_reactor, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 15, 0), new ComparableStack(ModItems.ingot_lead, 5, 0), new ComparableStack(ModItems.rod_quad_empty, 10, 0), new OreDictStack("dyeBrown", 3), },100);
		makeRecipe(new ComparableStack(ModItems.generator_front, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 3, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.tank_steel, 4, 0), new ComparableStack(ModItems.turbine_titanium, 1, 0), new ComparableStack(ModItems.wire_red_copper, 6, 0), new ComparableStack(ModItems.wire_gold, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_assembly, 1), new Object[] {new ComparableStack(ModItems.hull_small_steel, 1, 0), new ComparableStack(ModItems.hull_small_aluminium, 4, 0), new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.wire_aluminium, 6, 0), new ComparableStack(ModItems.canister_kerosene, 3, 0), new ComparableStack(ModItems.circuit_targeting_tier1, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_carrier, 1), new Object[] {new ComparableStack(ModItems.fluid_barrel_full, 16, 21), new ComparableStack(ModItems.thruster_medium, 4, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.hull_big_titanium, 6, 0), new ComparableStack(ModItems.hull_big_steel, 2, 0), new ComparableStack(ModItems.hull_small_aluminium, 12, 0), new ComparableStack(ModItems.plate_titanium, 24, 0), new ComparableStack(ModItems.plate_polymer, 128, 0), new ComparableStack(ModBlocks.det_cord, 8, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 12, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_generic_small, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 5, 0), new ComparableStack(ModItems.plate_steel, 3, 0), new ComparableStack(Blocks.tnt, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_generic_medium, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 8, 0), new ComparableStack(ModItems.plate_steel, 5, 0), new ComparableStack(Blocks.tnt, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_generic_large, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 15, 0), new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(Blocks.tnt, 8, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_incendiary_small, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_small, 1, 0), new ComparableStack(ModItems.powder_fire, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_incendiary_medium, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_medium, 1, 0), new ComparableStack(ModItems.powder_fire, 8, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_incendiary_large, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_large, 1, 0), new ComparableStack(ModItems.powder_fire, 16, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_cluster_small, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_small, 1, 0), new ComparableStack(ModItems.pellet_cluster, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_cluster_medium, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_medium, 1, 0), new ComparableStack(ModItems.pellet_cluster, 8, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_cluster_large, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_large, 1, 0), new ComparableStack(ModItems.pellet_cluster, 16, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_buster_small, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_small, 1, 0), new ComparableStack(ModBlocks.det_cord, 8, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_buster_medium, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_medium, 1, 0), new ComparableStack(ModBlocks.det_cord, 4, 0), new ComparableStack(ModBlocks.det_charge, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_buster_large, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_large, 1, 0), new ComparableStack(ModBlocks.det_charge, 8, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_nuclear, 1), new Object[] {new ComparableStack(ModItems.boy_shielding, 1, 0), new ComparableStack(ModItems.boy_target, 1, 0), new ComparableStack(ModItems.boy_bullet, 1, 0), new ComparableStack(ModItems.plate_titanium, 20, 0), new ComparableStack(ModItems.plate_steel, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_mirvlet, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 5, 0), new ComparableStack(ModItems.plate_steel, 18, 0), new ComparableStack(ModItems.ingot_pu239, 1, 0), new ComparableStack(Blocks.tnt, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_mirv, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 20, 0), new ComparableStack(ModItems.plate_steel, 12, 0), new ComparableStack(ModItems.ingot_pu239, 1, 0), new ComparableStack(Blocks.tnt, 8, 0), new ComparableStack(ModItems.neutron_reflector, 6, 0), new ComparableStack(ModItems.lithium, 4, 0), new ComparableStack(ModItems.cell_deuterium, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_thermo_endo, 1), new Object[] {new ComparableStack(ModBlocks.therm_endo, 2, 0), new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.plate_steel, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_thermo_exo, 1), new Object[] {new ComparableStack(ModBlocks.therm_exo, 2, 0), new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.plate_steel, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.fuel_tank_small, 1), new Object[] {new ComparableStack(ModItems.canister_kerosene, 4, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.plate_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.fuel_tank_medium, 1), new Object[] {new ComparableStack(ModItems.fuel_tank_small, 3, 0), new ComparableStack(ModItems.plate_titanium, 4, 0), new ComparableStack(ModItems.plate_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.fuel_tank_large, 1), new Object[] {new ComparableStack(ModItems.fuel_tank_medium, 3, 0), new ComparableStack(ModItems.plate_titanium, 4, 0), new ComparableStack(ModItems.plate_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.thruster_small, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.wire_aluminium, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.thruster_medium, 1), new Object[] {new ComparableStack(ModItems.thruster_small, 1, 0), new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.hull_small_steel, 1, 0), new ComparableStack(ModItems.hull_big_steel, 1, 0), new ComparableStack(ModItems.wire_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.thruster_large, 1), new Object[] {new ComparableStack(ModItems.thruster_medium, 1, 0), new ComparableStack(ModItems.plate_steel, 4, 0), new ComparableStack(ModItems.hull_big_steel, 2, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.thruster_nuclear, 1), new Object[] {new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.tank_steel, 2, 0), new ComparableStack(ModItems.pipes_steel, 3, 0), new ComparableStack(ModItems.board_copper, 6, 0), new ComparableStack(ModItems.motor, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 2, 0), new ComparableStack(ModBlocks.machine_reactor_small, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_base, 1), new Object[] {new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.plate_desh, 4, 0), new ComparableStack(ModItems.hull_big_titanium, 3, 0), new ComparableStack(ModItems.fluid_barrel_full, 1, 21), new ComparableStack(ModItems.photo_panel, 24, 0), new ComparableStack(ModItems.board_copper, 12, 0), new ComparableStack(ModItems.circuit_gold, 6, 0), new ComparableStack(ModItems.battery_lithium_cell_6, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_head_mapper, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.hull_small_steel, 3, 0), new ComparableStack(ModItems.plate_desh, 2, 0), new ComparableStack(ModItems.circuit_gold, 2, 0), new ComparableStack(ModItems.plate_polymer, 12, 0), new ComparableStack(Items.redstone, 6, 0), new ComparableStack(Items.diamond, 1, 0), new ComparableStack(Blocks.glass_pane, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_head_scanner, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 6, 0), new ComparableStack(ModItems.plate_titanium, 32, 0), new ComparableStack(ModItems.plate_desh, 6, 0), new ComparableStack(ModItems.magnetron, 6, 0), new ComparableStack(ModItems.coil_advanced_torus, 2, 0), new ComparableStack(ModItems.circuit_gold, 6, 0), new ComparableStack(ModItems.plate_polymer, 6, 0), new ComparableStack(Items.diamond, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_head_radar, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.plate_titanium, 32, 0), new ComparableStack(ModItems.magnetron, 12, 0), new ComparableStack(ModItems.plate_polymer, 16, 0), new ComparableStack(ModItems.wire_red_copper, 16, 0), new ComparableStack(ModItems.coil_gold, 3, 0), new ComparableStack(ModItems.circuit_gold, 5, 0), new ComparableStack(Items.diamond, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_head_laser, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 12, 0), new ComparableStack(ModItems.ingot_tungsten, 16, 0), new ComparableStack(ModItems.ingot_polymer, 6, 0), new ComparableStack(ModItems.plate_polymer, 16, 0), new ComparableStack(ModItems.board_copper, 24, 0), new ComparableStack(ModItems.circuit_targeting_tier5, 2, 0), new ComparableStack(Items.redstone, 16, 0), new ComparableStack(Items.diamond, 5, 0), new ComparableStack(Blocks.glass_pane, 16, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_head_resonator, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 32, 0), new ComparableStack(ModItems.ingot_polymer, 48, 0), new ComparableStack(ModItems.plate_polymer, 8, 0), new ComparableStack(ModItems.crystal_xen, 1, 0), new ComparableStack(ModItems.ingot_starmetal, 7, 0), new ComparableStack(ModItems.circuit_targeting_tier5, 6, 0), new ComparableStack(ModItems.circuit_targeting_tier6, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_foeq, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.plate_desh, 8, 0), new ComparableStack(ModItems.hull_big_titanium, 3, 0), new ComparableStack(ModItems.fluid_barrel_full, 1, 1), new ComparableStack(ModItems.photo_panel, 16, 0), new ComparableStack(ModItems.thruster_nuclear, 1, 0), new ComparableStack(ModItems.rod_quad_uranium_fuel, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier5, 6, 0), new ComparableStack(ModItems.magnetron, 3, 0), new ComparableStack(ModItems.battery_lithium_cell_6, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_miner, 1), new Object[] {new ComparableStack(ModItems.plate_saturnite, 24, 0), new ComparableStack(ModItems.plate_desh, 8, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.drill_titanium, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 2, 0), new ComparableStack(ModItems.fluid_barrel_full, 1, 21), new ComparableStack(ModItems.thruster_small, 1, 0), new ComparableStack(ModItems.photo_panel, 12, 0), new ComparableStack(ModItems.centrifuge_element, 4, 0), new ComparableStack(ModItems.magnetron, 3, 0), new ComparableStack(ModItems.plate_polymer, 12, 0), new ComparableStack(ModItems.battery_lithium_cell_6, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.chopper_head, 1), new Object[] {new ComparableStack(ModBlocks.reinforced_glass, 2, 0), new ComparableStack(ModBlocks.fwatz_computer, 1, 0), new ComparableStack(ModItems.ingot_combine_steel, 22, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.chopper_gun, 1), new Object[] {new ComparableStack(ModItems.plate_combine_steel, 4, 0), new ComparableStack(ModItems.ingot_combine_steel, 2, 0), new ComparableStack(ModItems.wire_tungsten, 6, 0), new ComparableStack(ModItems.coil_magnetized_tungsten, 1, 0), new ComparableStack(ModItems.motor, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.chopper_torso, 1), new Object[] {new ComparableStack(ModItems.ingot_combine_steel, 26, 0), new ComparableStack(ModBlocks.fwatz_computer, 1, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 4, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.chopper_blades, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.chopper_tail, 1), new Object[] {new ComparableStack(ModItems.plate_combine_steel, 8, 0), new ComparableStack(ModItems.ingot_combine_steel, 5, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 4, 0), new ComparableStack(ModItems.motor, 1, 0), new ComparableStack(ModItems.chopper_blades, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.chopper_wing, 1), new Object[] {new ComparableStack(ModItems.plate_combine_steel, 6, 0), new ComparableStack(ModItems.ingot_combine_steel, 3, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.chopper_blades, 1), new Object[] {new ComparableStack(ModItems.plate_combine_steel, 8, 0), new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.ingot_combine_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.circuit_aluminium, 1), new Object[] {new ComparableStack(ModItems.circuit_raw, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.circuit_copper, 1), new Object[] {new ComparableStack(ModItems.circuit_aluminium, 1, 0), new ComparableStack(ModItems.wire_copper, 6, 0), new ComparableStack(ModItems.powder_quartz, 4, 0), new ComparableStack(ModItems.plate_copper, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.circuit_red_copper, 1), new Object[] {new ComparableStack(ModItems.circuit_copper, 1, 0), new ComparableStack(ModItems.wire_red_copper, 6, 0), new ComparableStack(ModItems.powder_gold, 4, 0), new ComparableStack(ModItems.plate_polymer, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_rtg, 1), new Object[] {new ComparableStack(ModItems.nugget_pu238, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_rtg_weak, 1), new Object[] {new ComparableStack(ModItems.nugget_u238, 4, 0), new ComparableStack(ModItems.nugget_pu238, 1, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.tritium_deuterium_cake, 1), new Object[] {new ComparableStack(ModItems.cell_deuterium, 6, 0), new ComparableStack(ModItems.cell_tritium, 2, 0), new ComparableStack(ModItems.lithium, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_cluster, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 4, 0), new ComparableStack(Blocks.tnt, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_buckshot, 1), new Object[] {new ComparableStack(ModItems.nugget_lead, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.australium_iii, 1), new Object[] {new ComparableStack(ModItems.rod_australium, 1, 0), new ComparableStack(ModItems.ingot_steel, 1, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.plate_copper, 2, 0), new ComparableStack(ModItems.wire_copper, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.magnetron, 1), new Object[] {new ComparableStack(ModItems.ingot_advanced_alloy, 1, 0), new ComparableStack(ModItems.plate_advanced_alloy, 2, 0), new ComparableStack(ModItems.wire_tungsten, 1, 0), new ComparableStack(ModItems.coil_tungsten, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_schrabidium, 1), new Object[] {new ComparableStack(ModItems.ingot_schrabidium, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_hes, 1), new Object[] {new ComparableStack(ModItems.ingot_hes, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_mes, 1), new Object[] {new ComparableStack(ModItems.ingot_schrabidium_fuel, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_les, 1), new Object[] {new ComparableStack(ModItems.ingot_les, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_beryllium, 1), new Object[] {new ComparableStack(ModItems.ingot_beryllium, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_neptunium, 1), new Object[] {new ComparableStack(ModItems.ingot_neptunium, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_lead, 1), new Object[] {new ComparableStack(ModItems.ingot_lead, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_advanced, 1), new Object[] {new ComparableStack(ModItems.ingot_desh, 5, 0), new ComparableStack(ModItems.plate_iron, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_template, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 1, 0), new ComparableStack(ModItems.plate_iron, 4, 0), new ComparableStack(ModItems.plate_copper, 2, 0), new ComparableStack(ModItems.wire_copper, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_speed_1, 1), new Object[] {new ComparableStack(ModItems.upgrade_template, 1, 0), new ComparableStack(ModItems.powder_red_copper, 4, 0), new ComparableStack(Items.redstone, 6, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_speed_2, 1), new Object[] {new ComparableStack(ModItems.upgrade_speed_1, 1, 0), new ComparableStack(ModItems.powder_red_copper, 2, 0), new ComparableStack(Items.redstone, 4, 0), new ComparableStack(ModItems.circuit_red_copper, 4, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), new ComparableStack(ModItems.ingot_polymer, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_speed_3, 1), new Object[] {new ComparableStack(ModItems.upgrade_speed_2, 1, 0), new ComparableStack(ModItems.powder_red_copper, 2, 0), new ComparableStack(Items.redstone, 6, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_effect_1, 1), new Object[] {new ComparableStack(ModItems.upgrade_template, 1, 0), new ComparableStack(ModItems.powder_dura_steel, 4, 0), new ComparableStack(ModItems.powder_steel, 6, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_effect_2, 1), new Object[] {new ComparableStack(ModItems.upgrade_effect_1, 1, 0), new ComparableStack(ModItems.powder_dura_steel, 2, 0), new ComparableStack(ModItems.powder_steel, 4, 0), new ComparableStack(ModItems.circuit_red_copper, 4, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), new ComparableStack(ModItems.ingot_polymer, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_effect_3, 1), new Object[] {new ComparableStack(ModItems.upgrade_effect_2, 1, 0), new ComparableStack(ModItems.powder_dura_steel, 2, 0), new ComparableStack(ModItems.powder_steel, 6, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_power_1, 1), new Object[] {new ComparableStack(ModItems.upgrade_template, 1, 0), new ComparableStack(ModItems.powder_lapis, 4, 0), new ComparableStack(Items.glowstone_dust, 6, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_power_2, 1), new Object[] {new ComparableStack(ModItems.upgrade_power_1, 1, 0), new ComparableStack(ModItems.powder_lapis, 2, 0), new ComparableStack(Items.glowstone_dust, 4, 0), new ComparableStack(ModItems.circuit_red_copper, 4, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), new ComparableStack(ModItems.ingot_polymer, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_power_3, 1), new Object[] {new ComparableStack(ModItems.upgrade_power_2, 1, 0), new ComparableStack(ModItems.powder_lapis, 2, 0), new ComparableStack(Items.glowstone_dust, 6, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_fortune_1, 1), new Object[] {new ComparableStack(ModItems.upgrade_template, 1, 0), new ComparableStack(ModItems.powder_diamond, 4, 0), new ComparableStack(ModItems.powder_iron, 6, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_fortune_2, 1), new Object[] {new ComparableStack(ModItems.upgrade_fortune_1, 1, 0), new ComparableStack(ModItems.powder_diamond, 2, 0), new ComparableStack(ModItems.powder_iron, 4, 0), new ComparableStack(ModItems.circuit_red_copper, 4, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), new ComparableStack(ModItems.ingot_polymer, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_fortune_3, 1), new Object[] {new ComparableStack(ModItems.upgrade_fortune_2, 1, 0), new ComparableStack(ModItems.powder_diamond, 2, 0), new ComparableStack(ModItems.powder_iron, 6, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_1, 1), new Object[] {new ComparableStack(ModItems.upgrade_template, 1, 0), new ComparableStack(ModItems.powder_polymer, 4, 0), new ComparableStack(ModItems.powder_tungsten, 6, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_2, 1), new Object[] {new ComparableStack(ModItems.upgrade_afterburn_1, 1, 0), new ComparableStack(ModItems.powder_polymer, 2, 0), new ComparableStack(ModItems.powder_tungsten, 4, 0), new ComparableStack(ModItems.circuit_red_copper, 4, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), new ComparableStack(ModItems.ingot_polymer, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_3, 1), new Object[] {new ComparableStack(ModItems.upgrade_afterburn_2, 1, 0), new ComparableStack(ModItems.powder_polymer, 2, 0), new ComparableStack(ModItems.powder_tungsten, 6, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_radius, 1), new Object[] {new ComparableStack(ModItems.upgrade_template, 1, 0), new ComparableStack(Items.glowstone_dust, 6, 0), new ComparableStack(ModItems.powder_diamond, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_health, 1), new Object[] {new ComparableStack(ModItems.upgrade_template, 1, 0), new ComparableStack(Items.glowstone_dust, 6, 0), new ComparableStack(ModItems.powder_titanium, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_1, 1), new Object[] {new ComparableStack(ModItems.upgrade_speed_3, 4, 0), new ComparableStack(ModItems.upgrade_effect_3, 2, 0), new ComparableStack(ModItems.ingot_desh, 8, 0), new ComparableStack(ModItems.powder_power, 16, 0), new ComparableStack(ModItems.crystal_lithium, 4, 0), new ComparableStack(ModItems.circuit_schrabidium, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_2, 1), new Object[] {new ComparableStack(ModItems.upgrade_overdrive_1, 1, 0), new ComparableStack(ModItems.upgrade_afterburn_1, 1, 0), new ComparableStack(ModItems.upgrade_speed_3, 2, 0), new ComparableStack(ModItems.upgrade_effect_3, 2, 0), new ComparableStack(ModItems.ingot_saturnite, 12, 0), new ComparableStack(ModItems.powder_nitan_mix, 16, 0), new ComparableStack(ModItems.crystal_starmetal, 6, 0), new ComparableStack(ModItems.circuit_schrabidium, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_3, 1), new Object[] {new ComparableStack(ModItems.upgrade_overdrive_2, 1, 0), new ComparableStack(ModItems.upgrade_afterburn_1, 1, 0), new ComparableStack(ModItems.upgrade_speed_3, 2, 0), new ComparableStack(ModItems.upgrade_effect_3, 2, 0), new ComparableStack(ModItems.ingot_desh, 8, 0), new ComparableStack(ModItems.powder_power, 16, 0), new ComparableStack(ModItems.crystal_lithium, 4, 0), new ComparableStack(ModItems.circuit_schrabidium, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.fuse, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(Blocks.glass_pane, 1, 0), new ComparableStack(ModItems.wire_aluminium, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.redcoil_capacitor, 1), new Object[] {new ComparableStack(ModItems.plate_gold, 3, 0), new ComparableStack(ModItems.fuse, 1, 0), new ComparableStack(ModItems.wire_advanced_alloy, 4, 0), new ComparableStack(ModItems.coil_advanced_alloy, 6, 0), new ComparableStack(Blocks.redstone_block, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.titanium_filter, 1), new Object[] {new ComparableStack(ModItems.plate_lead, 3, 0), new ComparableStack(ModItems.fuse, 1, 0), new ComparableStack(ModItems.wire_tungsten, 4, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.ingot_u238, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.part_lithium, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.powder_lithium, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.part_beryllium, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.powder_beryllium, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.part_carbon, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.powder_coal, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.part_copper, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.powder_copper, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.part_plutonium, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.powder_plutonium, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.thermo_element, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 3, 0), new ComparableStack(ModItems.plate_iron, 1, 0), new ComparableStack(ModItems.plate_copper, 2, 0), new ComparableStack(ModItems.wire_red_copper, 2, 0), new ComparableStack(ModItems.wire_aluminium, 2, 0), new ComparableStack(ModItems.powder_quartz, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.limiter, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 3, 0), new ComparableStack(ModItems.plate_iron, 1, 0), new ComparableStack(ModItems.circuit_copper, 2, 0), new ComparableStack(ModItems.wire_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.plate_dalekanium, 1), new Object[] {new ComparableStack(ModBlocks.block_meteor, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.block_meteor, 1), new Object[] {new ComparableStack(ModItems.fragment_meteorite, 100, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.cmb_brick, 8), new Object[] {new ComparableStack(ModItems.ingot_combine_steel, 1, 0), new ComparableStack(ModItems.plate_combine_steel, 8, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.cmb_brick_reinforced, 8), new Object[] {new ComparableStack(ModBlocks.block_magnetized_tungsten, 4, 0), new ComparableStack(ModBlocks.brick_concrete, 4, 0), new ComparableStack(ModBlocks.cmb_brick, 1, 0), new ComparableStack(ModItems.plate_steel, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.seal_frame, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 3, 0), new ComparableStack(ModItems.wire_aluminium, 4, 0), new ComparableStack(Items.redstone, 2, 0), new ComparableStack(ModBlocks.steel_roof, 5, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.seal_controller, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 3, 0), new ComparableStack(ModItems.ingot_polymer, 4, 0), new ComparableStack(ModItems.ingot_red_copper, 1, 0), new ComparableStack(Items.redstone, 4, 0), new ComparableStack(ModBlocks.steel_roof, 5, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.vault_door, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 128, 0), new ComparableStack(ModItems.ingot_tungsten, 32, 0), new ComparableStack(ModItems.plate_lead, 48, 0), new ComparableStack(ModItems.plate_advanced_alloy, 8, 0), new ComparableStack(ModItems.plate_polymer, 16, 0), new ComparableStack(ModItems.bolt_tungsten, 18, 0), new ComparableStack(ModItems.bolt_dura_steel, 27, 0), new ComparableStack(ModItems.motor, 5, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.blast_door, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 16, 0), new ComparableStack(ModItems.ingot_tungsten, 8, 0), new ComparableStack(ModItems.plate_lead, 12, 0), new ComparableStack(ModItems.plate_advanced_alloy, 3, 0), new ComparableStack(ModItems.plate_polymer, 3, 0), new ComparableStack(ModItems.bolt_tungsten, 3, 0), new ComparableStack(ModItems.bolt_dura_steel, 3, 0), new ComparableStack(ModItems.motor, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_centrifuge, 1), new Object[] {new ComparableStack(ModItems.centrifuge_tower, 1, 0), new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(Items.iron_ingot, 4, 0), new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.plate_copper, 2, 0), new ComparableStack(ModItems.wire_red_copper, 8, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_gascent, 1), new Object[] {new ComparableStack(ModItems.centrifuge_tower, 1, 0), new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.ingot_polymer, 4, 0), new ComparableStack(ModItems.ingot_desh, 2, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.plate_advanced_alloy, 4, 0), new ComparableStack(ModItems.wire_red_copper, 8, 0), new ComparableStack(ModItems.wire_gold, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_reactor, 1), new Object[] {new ComparableStack(ModItems.reactor_core, 1, 0), new ComparableStack(ModItems.ingot_lead, 4, 0), new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.plate_steel, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_rtg_furnace_off, 1), new Object[] {new ComparableStack(Blocks.furnace, 1, 0), new ComparableStack(ModItems.rtg_unit, 3, 0), new ComparableStack(ModItems.plate_lead, 6, 0), new ComparableStack(ModItems.neutron_reflector, 4, 0), new ComparableStack(ModItems.plate_copper, 2, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_radgen, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 8, 0), new ComparableStack(ModItems.plate_steel, 32, 0), new ComparableStack(ModItems.coil_magnetized_tungsten, 6, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 24, 0), new ComparableStack(ModItems.circuit_gold, 4, 0), new ComparableStack(ModItems.reactor_core, 3, 0), new ComparableStack(ModItems.ingot_starmetal, 1, 0), new OreDictStack("dyeRed", 1), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_diesel, 1), new Object[] {new ComparableStack(ModItems.hull_small_steel, 4, 0), new ComparableStack(Blocks.piston, 4, 0), new ComparableStack(ModItems.ingot_steel, 6, 0), new ComparableStack(ModItems.ingot_red_copper, 2, 0), new ComparableStack(ModItems.plate_copper, 4, 0), new ComparableStack(ModItems.wire_red_copper, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_selenium, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.plate_copper, 8, 0), new ComparableStack(ModItems.hull_big_steel, 1, 0), new ComparableStack(ModItems.hull_small_steel, 9, 0), new ComparableStack(ModItems.pedestal_steel, 1, 0), new ComparableStack(ModItems.coil_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_reactor_small, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 6, 0), new ComparableStack(ModItems.ingot_polymer, 4, 0), new ComparableStack(ModItems.plate_lead, 8, 0), new ComparableStack(ModItems.plate_copper, 4, 0), new ComparableStack(ModItems.ingot_lead, 12, 0), new ComparableStack(ModItems.ingot_red_copper, 6, 0), new ComparableStack(ModItems.circuit_copper, 8, 0), new ComparableStack(ModItems.circuit_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_industrial_generator, 1), new Object[] {new ComparableStack(ModItems.generator_front, 1, 0), new ComparableStack(ModItems.generator_steel, 3, 0), new ComparableStack(ModItems.rotor_steel, 3, 0), new ComparableStack(ModItems.ingot_steel, 6, 0), new ComparableStack(ModItems.board_copper, 4, 0), new ComparableStack(ModItems.wire_gold, 8, 0), new ComparableStack(ModBlocks.red_wire_coated, 2, 0), new ComparableStack(ModItems.pedestal_steel, 2, 0), new ComparableStack(ModItems.circuit_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_cyclotron, 1), new Object[] {new ComparableStack(ModItems.cyclotron_tower, 1, 0), new ComparableStack(ModItems.board_copper, 4, 0), new ComparableStack(ModItems.ingot_steel, 16, 0), new ComparableStack(ModItems.ingot_polymer, 24, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModBlocks.machine_battery, 4, 0), new ComparableStack(ModItems.wire_red_copper, 20, 0), new ComparableStack(ModItems.circuit_red_copper, 12, 0), new ComparableStack(ModItems.circuit_gold, 3, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_rtg_grey, 1), new Object[] {new ComparableStack(ModItems.rtg_unit, 5, 0), new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), new ComparableStack(ModItems.ingot_polymer, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_battery, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.sulfur, 12, 0), new ComparableStack(ModItems.powder_lead, 12, 0), new ComparableStack(ModItems.ingot_red_copper, 2, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_lithium_battery, 1), new Object[] {new ComparableStack(ModItems.ingot_polymer, 4, 0), new ComparableStack(ModItems.powder_cobalt, 12, 0), new ComparableStack(ModItems.powder_lithium, 12, 0), new ComparableStack(ModItems.ingot_advanced_alloy, 2, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_schrabidium_battery, 1), new Object[] {new ComparableStack(ModItems.ingot_desh, 4, 0), new ComparableStack(ModItems.powder_neptunium, 12, 0), new ComparableStack(ModItems.powder_schrabidium, 12, 0), new ComparableStack(ModItems.ingot_schrabidium, 2, 0), new ComparableStack(ModItems.wire_schrabidium, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_dineutronium_battery, 1), new Object[] {new ComparableStack(ModItems.ingot_dineutronium, 24, 0), new ComparableStack(ModItems.powder_spark_mix, 12, 0), new ComparableStack(ModItems.battery_spark_cell_1000, 1, 0), new ComparableStack(ModItems.ingot_combine_steel, 32, 0), new ComparableStack(ModItems.coil_magnetized_tungsten, 8, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_shredder, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.plate_steel, 4, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.wire_red_copper, 2, 0), new ComparableStack(ModBlocks.steel_beam, 2, 0), new ComparableStack(Blocks.iron_bars, 2, 0), new ComparableStack(ModBlocks.red_wire_coated, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_well, 1), new Object[] {new ComparableStack(ModBlocks.steel_scaffold, 20, 0), new ComparableStack(ModBlocks.steel_beam, 8, 0), new ComparableStack(ModItems.tank_steel, 2, 0), new ComparableStack(ModItems.motor, 1, 0), new ComparableStack(ModItems.pipes_steel, 3, 0), new ComparableStack(ModItems.drill_titanium, 1, 0), new ComparableStack(ModItems.wire_red_copper, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_pumpjack, 1), new Object[] {new ComparableStack(ModBlocks.steel_scaffold, 8, 0), new ComparableStack(ModBlocks.block_steel, 8, 0), new ComparableStack(ModItems.pipes_steel, 4, 0), new ComparableStack(ModItems.tank_steel, 4, 0), new ComparableStack(ModItems.ingot_steel, 24, 0), new ComparableStack(ModItems.plate_steel, 16, 0), new ComparableStack(ModItems.plate_aluminium, 6, 0), new ComparableStack(ModItems.drill_titanium, 1, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.wire_red_copper, 8, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_flare, 1), new Object[] {new ComparableStack(ModBlocks.steel_scaffold, 28, 0), new ComparableStack(ModItems.tank_steel, 2, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), new ComparableStack(ModItems.hull_small_steel, 1, 0), new ComparableStack(ModItems.thermo_element, 3, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_refinery, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 16, 0), new ComparableStack(ModItems.plate_steel, 24, 0), new ComparableStack(ModItems.plate_copper, 16, 0), new ComparableStack(ModItems.tank_steel, 4, 0), new ComparableStack(ModItems.hull_big_steel, 6, 0), new ComparableStack(ModItems.pipes_steel, 4, 0), new ComparableStack(ModItems.coil_tungsten, 10, 0), new ComparableStack(ModItems.wire_red_copper, 8, 0), new ComparableStack(ModItems.circuit_red_copper, 4, 0), new ComparableStack(ModItems.plate_polymer, 8, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_epress, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(ModItems.plate_polymer, 4, 0), new ComparableStack(ModItems.pipes_steel, 1, 0), new ComparableStack(ModItems.bolt_tungsten, 4, 0), new ComparableStack(ModItems.coil_copper, 2, 0), new ComparableStack(ModItems.motor, 1, 0), new ComparableStack(ModItems.circuit_copper, 1, 0), new ComparableStack(ModItems.canister_canola, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_chemplant, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 8, 0), new ComparableStack(ModItems.plate_copper, 6, 0), new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.tank_steel, 4, 0), new ComparableStack(ModItems.hull_big_steel, 1, 0), new ComparableStack(ModItems.wire_red_copper, 16, 0), new ComparableStack(ModItems.wire_tungsten, 3, 0), new ComparableStack(ModItems.circuit_copper, 4, 0), new ComparableStack(ModItems.circuit_red_copper, 2, 0), new ComparableStack(ModItems.plate_polymer, 8, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_crystallizer, 1), new Object[] {new ComparableStack(ModItems.hull_big_steel, 4, 0), new ComparableStack(ModItems.pipes_steel, 4, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.blades_advanced_alloy, 2, 0), new ComparableStack(ModItems.ingot_steel, 16, 0), new ComparableStack(ModItems.plate_titanium, 16, 0), new ComparableStack(Blocks.glass, 4, 0), new ComparableStack(ModItems.circuit_gold, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_fluidtank, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.hull_big_steel, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_drill, 1), new Object[] {new ComparableStack(ModBlocks.steel_scaffold, 6, 0), new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), new ComparableStack(ModItems.circuit_copper, 1, 0), new ComparableStack(ModItems.motor, 1, 0), new ComparableStack(ModItems.ingot_dura_steel, 2, 0), new ComparableStack(ModItems.bolt_dura_steel, 2, 0), new ComparableStack(ModItems.drill_titanium, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_mining_laser, 1), new Object[] {new ComparableStack(ModItems.tank_steel, 3, 0), new ComparableStack(ModItems.ingot_steel, 8, 0), new ComparableStack(ModItems.plate_steel, 12, 0), new ComparableStack(ModItems.crystal_redstone, 3, 0), new ComparableStack(Items.diamond, 5, 0), new ComparableStack(ModItems.ingot_polymer, 8, 0), new ComparableStack(ModItems.motor, 3, 0), new ComparableStack(ModItems.ingot_dura_steel, 4, 0), new ComparableStack(ModItems.bolt_dura_steel, 6, 0), new ComparableStack(ModBlocks.machine_lithium_battery, 3, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_turbofan, 1), new Object[] {new ComparableStack(ModItems.hull_big_steel, 1, 0), new ComparableStack(ModItems.hull_big_titanium, 3, 0), new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.turbine_tungsten, 1, 0), new ComparableStack(ModItems.turbine_titanium, 7, 0), new ComparableStack(ModItems.bolt_compound, 8, 0), new ComparableStack(ModItems.ingot_red_copper, 12, 0), new ComparableStack(ModItems.wire_red_copper, 24, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_teleporter, 1), new Object[] {new ComparableStack(ModItems.ingot_titanium, 6, 0), new ComparableStack(ModItems.plate_advanced_alloy, 12, 0), new ComparableStack(ModItems.plate_combine_steel, 4, 0), new ComparableStack(ModItems.telepad, 1, 0), new ComparableStack(ModItems.entanglement_kit, 1, 0), new ComparableStack(ModBlocks.machine_battery, 2, 0), new ComparableStack(ModItems.coil_magnetized_tungsten, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_schrabidium_transmutator, 1), new Object[] {new ComparableStack(ModItems.ingot_magnetized_tungsten, 1, 0), new ComparableStack(ModItems.ingot_titanium, 24, 0), new ComparableStack(ModItems.plate_advanced_alloy, 18, 0), new ComparableStack(ModItems.plate_steel, 12, 0), new ComparableStack(ModItems.plate_desh, 6, 0), new ComparableStack(ModItems.plate_polymer, 8, 0), new ComparableStack(ModBlocks.machine_battery, 5, 0), new ComparableStack(ModItems.circuit_gold, 5, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_combine_factory, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 8, 0), new ComparableStack(ModItems.ingot_polymer, 6, 0), new ComparableStack(ModItems.plate_titanium, 4, 0), new ComparableStack(ModItems.plate_copper, 6, 0), new ComparableStack(ModItems.circuit_gold, 6, 0), new ComparableStack(ModItems.coil_advanced_alloy, 8, 0), new ComparableStack(ModItems.coil_tungsten, 4, 0), new ComparableStack(ModItems.ingot_magnetized_tungsten, 12, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.factory_advanced_hull, 1), new Object[] {new ComparableStack(ModItems.ingot_advanced_alloy, 4, 0), new ComparableStack(ModItems.plate_advanced_alloy, 4, 0), new ComparableStack(ModItems.wire_advanced_alloy, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.factory_advanced_furnace, 1), new Object[] {new ComparableStack(ModItems.ingot_advanced_alloy, 4, 0), new ComparableStack(ModItems.plate_advanced_alloy, 4, 0), new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(ModItems.coil_advanced_alloy, 2, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.factory_advanced_core, 1), new Object[] {new ComparableStack(ModItems.ingot_advanced_alloy, 6, 0), new ComparableStack(ModItems.plate_advanced_alloy, 6, 0), new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(ModItems.coil_advanced_alloy, 2, 0), new ComparableStack(ModItems.motor, 16, 0), new ComparableStack(Blocks.piston, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.factory_advanced_conductor, 1), new Object[] {new ComparableStack(ModItems.ingot_advanced_alloy, 8, 0), new ComparableStack(ModItems.plate_advanced_alloy, 6, 0), new ComparableStack(ModItems.wire_advanced_alloy, 4, 0), new ComparableStack(ModItems.fuse, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.reactor_element, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.neutron_reflector, 4, 0), new ComparableStack(ModItems.plate_lead, 2, 0), new ComparableStack(ModItems.rod_empty, 8, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.reactor_control, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.ingot_lead, 6, 0), new ComparableStack(ModItems.bolt_tungsten, 6, 0), new ComparableStack(ModItems.motor, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.reactor_hatch, 1), new Object[] {new ComparableStack(ModBlocks.brick_concrete, 1, 0), new ComparableStack(ModItems.plate_steel, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.reactor_conductor, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.plate_copper, 12, 0), new ComparableStack(ModItems.wire_tungsten, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.reactor_computer, 1), new Object[] {new ComparableStack(ModBlocks.reactor_conductor, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 4, 0), new ComparableStack(ModItems.circuit_gold, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fusion_conductor, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 10, 0), new ComparableStack(ModItems.coil_advanced_alloy, 5, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fusion_center, 1), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 4, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.wire_advanced_alloy, 24, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fusion_motor, 1), new Object[] {new ComparableStack(ModItems.ingot_titanium, 4, 0), new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.motor, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fusion_heater, 1), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 4, 0), new ComparableStack(ModItems.neutron_reflector, 6, 0), new ComparableStack(ModItems.magnetron, 4, 0), new ComparableStack(ModItems.wire_advanced_alloy, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fusion_hatch, 1), new Object[] {new ComparableStack(ModBlocks.fusion_heater, 1, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fusion_core, 1), new Object[] {new ComparableStack(ModBlocks.fusion_center, 3, 0), new ComparableStack(ModItems.circuit_red_copper, 48, 0), new ComparableStack(ModItems.circuit_gold, 12, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.watz_element, 1), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 4, 0), new ComparableStack(ModItems.plate_advanced_alloy, 4, 0), new ComparableStack(ModItems.rod_empty, 2, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 2, 0), new ComparableStack(ModItems.wire_advanced_alloy, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.watz_control, 1), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 4, 0), new ComparableStack(ModItems.ingot_advanced_alloy, 4, 0), new ComparableStack(ModItems.ingot_lead, 2, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 4, 0), new ComparableStack(ModItems.wire_advanced_alloy, 2, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.watz_cooler, 1), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 2, 0), new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.niter, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.watz_end, 1), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 2, 0), new ComparableStack(ModItems.ingot_lead, 2, 0), new ComparableStack(ModItems.ingot_steel, 3, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.watz_hatch, 1), new Object[] {new ComparableStack(ModBlocks.reinforced_brick, 1, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.watz_conductor, 1), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 2, 0), new ComparableStack(ModItems.ingot_lead, 2, 0), new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.wire_red_copper, 6, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 2, 0), new ComparableStack(ModItems.fuse, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.watz_core, 1), new Object[] {new ComparableStack(ModBlocks.block_meteor, 1, 0), new ComparableStack(ModItems.circuit_gold, 5, 0), new ComparableStack(ModItems.circuit_schrabidium, 2, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 12, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fwatz_hatch, 1), new Object[] {new ComparableStack(ModItems.ingot_tungsten, 6, 0), new ComparableStack(ModItems.plate_combine_steel, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fwatz_conductor, 1), new Object[] {new ComparableStack(ModItems.plate_combine_steel, 10, 0), new ComparableStack(ModItems.coil_magnetized_tungsten, 5, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fwatz_computer, 1), new Object[] {new ComparableStack(ModBlocks.block_meteor, 1, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 16, 0), new ComparableStack(ModItems.powder_diamond, 6, 0), new ComparableStack(ModItems.powder_magnetized_tungsten, 6, 0), new ComparableStack(ModItems.powder_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.fwatz_core, 1), new Object[] {new ComparableStack(ModBlocks.block_meteor, 1, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 24, 0), new ComparableStack(ModItems.powder_diamond, 8, 0), new ComparableStack(ModItems.powder_magnetized_tungsten, 12, 0), new ComparableStack(ModItems.powder_desh, 8, 0), new ComparableStack(ModItems.upgrade_power_3, 1, 0), new ComparableStack(ModItems.upgrade_speed_3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_gadget, 1), new Object[] {new ComparableStack(ModItems.sphere_steel, 1, 0), new ComparableStack(ModItems.fins_flat, 2, 0), new ComparableStack(ModItems.pedestal_steel, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), new ComparableStack(ModItems.wire_gold, 6, 0), new OreDictStack("dyeGray", 6), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_boy, 1), new Object[] {new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.fins_small_steel, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 1, 0), new ComparableStack(ModItems.wire_aluminium, 6, 0), new OreDictStack("dyeBlue", 4), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_man, 1), new Object[] {new ComparableStack(ModItems.sphere_steel, 1, 0), new ComparableStack(ModItems.hull_big_steel, 2, 0), new ComparableStack(ModItems.fins_big_steel, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 2, 0), new ComparableStack(ModItems.wire_copper, 6, 0), new OreDictStack("dyeYellow", 6), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_mike, 1), new Object[] {new ComparableStack(ModItems.sphere_steel, 1, 0), new ComparableStack(ModItems.hull_big_aluminium, 4, 0), new ComparableStack(ModItems.cap_aluminium, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 3, 0), new ComparableStack(ModItems.wire_gold, 18, 0), new OreDictStack("dyeLightGray", 12), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_tsar, 1), new Object[] {new ComparableStack(ModItems.sphere_steel, 1, 0), new ComparableStack(ModItems.hull_big_titanium, 6, 0), new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.fins_tri_steel, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 5, 0), new ComparableStack(ModItems.wire_gold, 24, 0), new ComparableStack(ModItems.wire_tungsten, 12, 0), new OreDictStack("dyeBlack", 6), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_prototype, 1), new Object[] {new ComparableStack(ModItems.dysfunctional_reactor, 1, 0), new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.ingot_euphemium, 3, 0), new ComparableStack(ModItems.circuit_targeting_tier5, 1, 0), new ComparableStack(ModItems.wire_gold, 16, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_fleija, 1), new Object[] {new ComparableStack(ModItems.hull_small_aluminium, 1, 0), new ComparableStack(ModItems.fins_quad_titanium, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 2, 0), new ComparableStack(ModItems.wire_gold, 8, 0), new OreDictStack("dyeWhite", 4), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_solinium, 1), new Object[] {new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.fins_quad_titanium, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 3, 0), new ComparableStack(ModItems.wire_gold, 10, 0), new ComparableStack(ModItems.pipes_steel, 4, 0), new OreDictStack("dyeGray", 4), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_n2, 1), new Object[] {new ComparableStack(ModItems.hull_big_steel, 3, 0), new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.wire_magnetized_tungsten, 12, 0), new ComparableStack(ModItems.pipes_steel, 6, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 3, 0), new OreDictStack("dyeBlack", 12), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_fstbmb, 1), new Object[] {new ComparableStack(ModItems.sphere_steel, 1, 0), new ComparableStack(ModItems.hull_big_titanium, 6, 0), new ComparableStack(ModItems.fins_big_steel, 1, 0), new ComparableStack(ModItems.powder_magic, 8, 0), new ComparableStack(ModItems.wire_gold, 12, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 4, 0), new OreDictStack("dyeGray", 6), },100);
		makeRecipe(new ComparableStack(ModBlocks.nuke_custom, 1), new Object[] {new ComparableStack(ModItems.hull_small_steel, 2, 0), new ComparableStack(ModItems.fins_small_steel, 1, 0), new ComparableStack(ModItems.circuit_gold, 1, 0), new ComparableStack(ModItems.wire_gold, 12, 0), new OreDictStack("dyeGray", 4), },100);
		makeRecipe(new ComparableStack(ModBlocks.float_bomb, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.levitation_unit, 1, 0), new ComparableStack(ModItems.circuit_gold, 4, 0), new ComparableStack(ModItems.wire_gold, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.therm_endo, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.thermo_unit_endo, 1, 0), new ComparableStack(ModItems.circuit_gold, 2, 0), new ComparableStack(ModItems.wire_gold, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.therm_exo, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.thermo_unit_exo, 1, 0), new ComparableStack(ModItems.circuit_gold, 2, 0), new ComparableStack(ModItems.wire_gold, 6, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.launch_pad, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.ingot_polymer, 2, 0), new ComparableStack(ModItems.plate_steel, 12, 0), new ComparableStack(ModBlocks.machine_battery, 1, 0), new ComparableStack(ModItems.circuit_gold, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.chopper, 1), new Object[] {new ComparableStack(ModItems.chopper_blades, 5, 0), new ComparableStack(ModItems.chopper_gun, 1, 0), new ComparableStack(ModItems.chopper_head, 1, 0), new ComparableStack(ModItems.chopper_tail, 1, 0), new ComparableStack(ModItems.chopper_torso, 1, 0), new ComparableStack(ModItems.chopper_wing, 2, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.turret_light, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 6, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), new ComparableStack(ModItems.ingot_red_copper, 2, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 2, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.turret_heavy, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 8, 0), new ComparableStack(ModItems.ingot_aluminium, 4, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), new ComparableStack(ModItems.hull_small_steel, 1, 0), new ComparableStack(ModItems.ingot_red_copper, 4, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 3, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.turret_rocket, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 12, 0), new ComparableStack(ModItems.ingot_titanium, 4, 0), new ComparableStack(ModItems.hull_small_steel, 8, 0), new ComparableStack(ModItems.ingot_red_copper, 6, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 2, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.turret_flamer, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 8, 0), new ComparableStack(ModItems.ingot_tungsten, 2, 0), new ComparableStack(ModItems.pipes_steel, 1, 0), new ComparableStack(ModItems.tank_steel, 2, 0), new ComparableStack(ModItems.ingot_red_copper, 4, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 2, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.turret_tau, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 16, 0), new ComparableStack(ModItems.ingot_titanium, 8, 0), new ComparableStack(ModItems.plate_advanced_alloy, 4, 0), new ComparableStack(ModItems.redcoil_capacitor, 3, 0), new ComparableStack(ModItems.ingot_red_copper, 12, 0), new ComparableStack(ModItems.motor, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 2, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.turret_spitfire, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 6, 0), new ComparableStack(ModItems.ingot_red_copper, 6, 0), new ComparableStack(ModItems.plate_steel, 16, 0), new ComparableStack(ModItems.plate_iron, 8, 0), new ComparableStack(ModItems.hull_small_steel, 4, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), new ComparableStack(ModItems.motor, 3, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.turret_cwis, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 6, 0), new ComparableStack(ModItems.ingot_red_copper, 8, 0), new ComparableStack(ModItems.plate_steel, 10, 0), new ComparableStack(ModItems.plate_titanium, 4, 0), new ComparableStack(ModItems.hull_small_aluminium, 2, 0), new ComparableStack(ModItems.pipes_steel, 6, 0), new ComparableStack(ModItems.motor, 4, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 2, 0), new ComparableStack(ModItems.magnetron, 3, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.turret_cheapo, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 4, 0), new ComparableStack(ModItems.plate_iron, 4, 0), new ComparableStack(ModItems.pipes_steel, 3, 0), new ComparableStack(ModItems.motor, 3, 0), new ComparableStack(ModItems.circuit_targeting_tier1, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_generic, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_small, 1, 0), new ComparableStack(ModItems.fuel_tank_small, 1, 0), new ComparableStack(ModItems.thruster_small, 1, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.circuit_targeting_tier1, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_incendiary, 1), new Object[] {new ComparableStack(ModItems.warhead_incendiary_small, 1, 0), new ComparableStack(ModItems.fuel_tank_small, 1, 0), new ComparableStack(ModItems.thruster_small, 1, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.circuit_targeting_tier1, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_cluster, 1), new Object[] {new ComparableStack(ModItems.warhead_cluster_small, 1, 0), new ComparableStack(ModItems.fuel_tank_small, 1, 0), new ComparableStack(ModItems.thruster_small, 1, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.circuit_targeting_tier1, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_buster, 1), new Object[] {new ComparableStack(ModItems.warhead_buster_small, 1, 0), new ComparableStack(ModItems.fuel_tank_small, 1, 0), new ComparableStack(ModItems.thruster_small, 1, 0), new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.circuit_targeting_tier1, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_strong, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_medium, 1, 0), new ComparableStack(ModItems.fuel_tank_medium, 1, 0), new ComparableStack(ModItems.thruster_medium, 1, 0), new ComparableStack(ModItems.plate_titanium, 10, 0), new ComparableStack(ModItems.plate_steel, 14, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_incendiary_strong, 1), new Object[] {new ComparableStack(ModItems.warhead_incendiary_medium, 1, 0), new ComparableStack(ModItems.fuel_tank_medium, 1, 0), new ComparableStack(ModItems.thruster_medium, 1, 0), new ComparableStack(ModItems.plate_titanium, 10, 0), new ComparableStack(ModItems.plate_steel, 14, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_cluster_strong, 1), new Object[] {new ComparableStack(ModItems.warhead_cluster_medium, 1, 0), new ComparableStack(ModItems.fuel_tank_medium, 1, 0), new ComparableStack(ModItems.thruster_medium, 1, 0), new ComparableStack(ModItems.plate_titanium, 10, 0), new ComparableStack(ModItems.plate_steel, 14, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_buster_strong, 1), new Object[] {new ComparableStack(ModItems.warhead_buster_medium, 1, 0), new ComparableStack(ModItems.fuel_tank_medium, 1, 0), new ComparableStack(ModItems.thruster_medium, 1, 0), new ComparableStack(ModItems.plate_titanium, 10, 0), new ComparableStack(ModItems.plate_steel, 14, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_burst, 1), new Object[] {new ComparableStack(ModItems.warhead_generic_large, 1, 0), new ComparableStack(ModItems.fuel_tank_large, 1, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_titanium, 14, 0), new ComparableStack(ModItems.plate_steel, 20, 0), new ComparableStack(ModItems.plate_aluminium, 12, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_inferno, 1), new Object[] {new ComparableStack(ModItems.warhead_incendiary_large, 1, 0), new ComparableStack(ModItems.fuel_tank_large, 1, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_titanium, 14, 0), new ComparableStack(ModItems.plate_steel, 20, 0), new ComparableStack(ModItems.plate_aluminium, 12, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_rain, 1), new Object[] {new ComparableStack(ModItems.warhead_cluster_large, 1, 0), new ComparableStack(ModItems.fuel_tank_large, 1, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_titanium, 14, 0), new ComparableStack(ModItems.plate_steel, 20, 0), new ComparableStack(ModItems.plate_aluminium, 12, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_drill, 1), new Object[] {new ComparableStack(ModItems.warhead_buster_large, 1, 0), new ComparableStack(ModItems.fuel_tank_large, 1, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_titanium, 14, 0), new ComparableStack(ModItems.plate_steel, 20, 0), new ComparableStack(ModItems.plate_aluminium, 12, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_nuclear, 1), new Object[] {new ComparableStack(ModItems.warhead_nuclear, 1, 0), new ComparableStack(ModItems.fuel_tank_large, 1, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_titanium, 20, 0), new ComparableStack(ModItems.plate_steel, 24, 0), new ComparableStack(ModItems.plate_aluminium, 16, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_nuclear_cluster, 1), new Object[] {new ComparableStack(ModItems.warhead_mirv, 1, 0), new ComparableStack(ModItems.fuel_tank_large, 1, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_titanium, 20, 0), new ComparableStack(ModItems.plate_steel, 24, 0), new ComparableStack(ModItems.plate_aluminium, 16, 0), new ComparableStack(ModItems.circuit_targeting_tier5, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_endo, 1), new Object[] {new ComparableStack(ModItems.warhead_thermo_endo, 1, 0), new ComparableStack(ModItems.fuel_tank_large, 1, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_titanium, 14, 0), new ComparableStack(ModItems.plate_steel, 20, 0), new ComparableStack(ModItems.plate_aluminium, 12, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_exo, 1), new Object[] {new ComparableStack(ModItems.warhead_thermo_exo, 1, 0), new ComparableStack(ModItems.fuel_tank_large, 1, 0), new ComparableStack(ModItems.thruster_large, 1, 0), new ComparableStack(ModItems.plate_titanium, 14, 0), new ComparableStack(ModItems.plate_steel, 20, 0), new ComparableStack(ModItems.plate_aluminium, 12, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.gun_defabricator, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 2, 0), new ComparableStack(ModItems.ingot_polymer, 8, 0), new ComparableStack(ModItems.plate_iron, 5, 0), new ComparableStack(ModItems.mechanism_special, 3, 0), new ComparableStack(Items.diamond, 1, 0), new ComparableStack(ModItems.plate_dalekanium, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.gun_fatman_ammo, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 3, 0), new ComparableStack(ModItems.plate_iron, 1, 0), new ComparableStack(ModItems.nugget_pu239, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.gun_mirv_ammo, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 20, 0), new ComparableStack(ModItems.plate_iron, 10, 0), new ComparableStack(ModItems.nugget_pu239, 24, 0), },100);
		makeRecipe(new ComparableStack(ModItems.gun_osipr_ammo, 24), new Object[] {new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(Items.redstone, 1, 0), new ComparableStack(Items.glowstone_dust, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.gun_osipr_ammo2, 1), new Object[] {new ComparableStack(ModItems.plate_combine_steel, 4, 0), new ComparableStack(Items.redstone, 7, 0), new ComparableStack(ModItems.powder_power, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_fire, 1), new Object[] {new ComparableStack(ModItems.grenade_frag, 1, 0), new ComparableStack(ModItems.powder_fire, 1, 0), new ComparableStack(ModItems.plate_copper, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_shrapnel, 1), new Object[] {new ComparableStack(ModItems.grenade_frag, 1, 0), new ComparableStack(ModItems.pellet_buckshot, 1, 0), new ComparableStack(ModItems.plate_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_cluster, 1), new Object[] {new ComparableStack(ModItems.grenade_frag, 1, 0), new ComparableStack(ModItems.pellet_cluster, 1, 0), new ComparableStack(ModItems.plate_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_flare, 1), new Object[] {new ComparableStack(ModItems.grenade_generic, 1, 0), new ComparableStack(Items.glowstone_dust, 1, 0), new ComparableStack(ModItems.plate_aluminium, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_electric, 1), new Object[] {new ComparableStack(ModItems.grenade_generic, 1, 0), new ComparableStack(ModItems.circuit_red_copper, 1, 0), new ComparableStack(ModItems.plate_gold, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_pulse, 4), new Object[] {new ComparableStack(ModItems.plate_steel, 1, 0), new ComparableStack(ModItems.plate_iron, 3, 0), new ComparableStack(ModItems.wire_red_copper, 6, 0), new ComparableStack(Items.diamond, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_plasma, 2), new Object[] {new ComparableStack(ModItems.plate_steel, 3, 0), new ComparableStack(ModItems.plate_advanced_alloy, 1, 0), new ComparableStack(ModItems.coil_advanced_torus, 1, 0), new ComparableStack(ModItems.cell_deuterium, 1, 0), new ComparableStack(ModItems.cell_tritium, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_tau, 2), new Object[] {new ComparableStack(ModItems.plate_lead, 3, 0), new ComparableStack(ModItems.plate_advanced_alloy, 1, 0), new ComparableStack(ModItems.coil_advanced_torus, 1, 0), new ComparableStack(ModItems.gun_xvl1456_ammo, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_schrabidium, 1), new Object[] {new ComparableStack(ModItems.grenade_flare, 1, 0), new ComparableStack(ModItems.powder_schrabidium, 1, 0), new ComparableStack(ModItems.neutron_reflector, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_nuclear, 1), new Object[] {new ComparableStack(ModItems.plate_iron, 1, 0), new ComparableStack(ModItems.plate_steel, 1, 0), new ComparableStack(ModItems.nugget_pu239, 2, 0), new ComparableStack(ModItems.wire_red_copper, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_zomg, 1), new Object[] {new ComparableStack(ModItems.plate_paa, 3, 0), new ComparableStack(ModItems.neutron_reflector, 1, 0), new ComparableStack(ModItems.coil_magnetized_tungsten, 3, 0), new ComparableStack(ModItems.powder_power, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_black_hole, 1), new Object[] {new ComparableStack(ModItems.ingot_polymer, 6, 0), new ComparableStack(ModItems.neutron_reflector, 3, 0), new ComparableStack(ModItems.coil_magnetized_tungsten, 2, 0), new ComparableStack(ModItems.black_hole, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.multitool_dig, 1), new Object[] {new ComparableStack(ModItems.rod_reiium, 1, 0), new ComparableStack(ModItems.rod_weidanium, 1, 0), new ComparableStack(ModItems.rod_australium, 1, 0), new ComparableStack(ModItems.rod_verticium, 1, 0), new ComparableStack(ModItems.rod_unobtainium, 1, 0), new ComparableStack(ModItems.rod_daffergon, 1, 0), new ComparableStack(ModItems.ingot_polymer, 4, 0), new ComparableStack(ModItems.circuit_gold, 1, 0), new ComparableStack(ModItems.ducttape, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.gadget_explosive, 1), new Object[] {new ComparableStack(Blocks.tnt, 3, 0), new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.plate_aluminium, 4, 0), new ComparableStack(ModItems.wire_gold, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.gadget_wireing, 1), new Object[] {new ComparableStack(ModItems.plate_iron, 1, 0), new ComparableStack(ModItems.wire_gold, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.gadget_core, 1), new Object[] {new ComparableStack(ModItems.nugget_pu239, 7, 0), new ComparableStack(ModItems.nugget_u238, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.boy_shielding, 1), new Object[] {new ComparableStack(ModItems.neutron_reflector, 12, 0), new ComparableStack(ModItems.plate_steel, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.boy_target, 1), new Object[] {new ComparableStack(ModItems.nugget_u235, 7, 0), },100);
		makeRecipe(new ComparableStack(ModItems.boy_bullet, 1), new Object[] {new ComparableStack(ModItems.nugget_u235, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.boy_propellant, 1), new Object[] {new ComparableStack(Blocks.tnt, 3, 0), new ComparableStack(ModItems.plate_iron, 8, 0), new ComparableStack(ModItems.plate_aluminium, 4, 0), new ComparableStack(ModItems.wire_red_copper, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.boy_igniter, 1), new Object[] {new ComparableStack(ModItems.plate_aluminium, 6, 0), new ComparableStack(ModItems.plate_steel, 1, 0), new ComparableStack(ModItems.circuit_red_copper, 1, 0), new ComparableStack(ModItems.wire_red_copper, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.man_explosive, 1), new Object[] {new ComparableStack(Blocks.tnt, 3, 0), new ComparableStack(ModItems.plate_steel, 2, 0), new ComparableStack(ModItems.plate_titanium, 4, 0), new ComparableStack(ModItems.wire_red_copper, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.man_igniter, 1), new Object[] {new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.circuit_red_copper, 1, 0), new ComparableStack(ModItems.wire_red_copper, 9, 0), },100);
		makeRecipe(new ComparableStack(ModItems.man_core, 1), new Object[] {new ComparableStack(ModItems.nugget_pu239, 8, 0), new ComparableStack(ModItems.nugget_beryllium, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mike_core, 1), new Object[] {new ComparableStack(ModItems.nugget_u238, 24, 0), new ComparableStack(ModItems.ingot_lead, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mike_deut, 1), new Object[] {new ComparableStack(ModItems.plate_iron, 12, 0), new ComparableStack(ModItems.plate_steel, 16, 0), new ComparableStack(ModItems.cell_deuterium, 10, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mike_cooling_unit, 1), new Object[] {new ComparableStack(ModItems.plate_iron, 8, 0), new ComparableStack(ModItems.coil_copper, 5, 0), new ComparableStack(ModItems.coil_tungsten, 5, 0), new ComparableStack(ModItems.motor, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.fleija_igniter, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 6, 0), new ComparableStack(ModItems.wire_schrabidium, 2, 0), new ComparableStack(ModItems.circuit_schrabidium, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.fleija_core, 1), new Object[] {new ComparableStack(ModItems.nugget_u235, 8, 0), new ComparableStack(ModItems.nugget_neptunium, 2, 0), new ComparableStack(ModItems.nugget_beryllium, 4, 0), new ComparableStack(ModItems.coil_copper, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.fleija_propellant, 1), new Object[] {new ComparableStack(Blocks.tnt, 3, 0), new ComparableStack(ModItems.plate_schrabidium, 8, 0), },100);
		makeRecipe(new ComparableStack(ModItems.solinium_igniter, 1), new Object[] {new ComparableStack(ModItems.plate_titanium, 4, 0), new ComparableStack(ModItems.wire_advanced_alloy, 2, 0), new ComparableStack(ModItems.circuit_schrabidium, 1, 0), new ComparableStack(ModItems.coil_gold, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.solinium_core, 1), new Object[] {new ComparableStack(ModItems.nugget_solinium, 9, 0), new ComparableStack(ModItems.nugget_euphemium, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.solinium_propellant, 1), new Object[] {new ComparableStack(Blocks.tnt, 3, 0), new ComparableStack(ModItems.neutron_reflector, 2, 0), new ComparableStack(ModItems.plate_polymer, 6, 0), new ComparableStack(ModItems.wire_tungsten, 6, 0), new ComparableStack(ModItems.biomass_compressed, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.schrabidium_hammer, 1), new Object[] {new ComparableStack(ModBlocks.block_schrabidium, 15, 0), new ComparableStack(ModItems.ingot_polymer, 128, 0), new ComparableStack(Items.nether_star, 3, 0), new ComparableStack(ModItems.fragment_meteorite, 512, 0), },100);
		makeRecipe(new ComparableStack(ModItems.component_limiter, 1), new Object[] {new ComparableStack(ModItems.hull_big_steel, 2, 0), new ComparableStack(ModItems.plate_steel, 32, 0), new ComparableStack(ModItems.plate_titanium, 18, 0), new ComparableStack(ModItems.plate_desh, 12, 0), new ComparableStack(ModItems.pipes_steel, 4, 0), new ComparableStack(ModItems.circuit_gold, 8, 0), new ComparableStack(ModItems.circuit_schrabidium, 4, 0), new ComparableStack(ModItems.ingot_starmetal, 14, 0), new ComparableStack(ModItems.plate_dalekanium, 5, 0), new ComparableStack(ModItems.powder_magic, 16, 0), new ComparableStack(ModBlocks.fwatz_computer, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.component_emitter, 1), new Object[] {new ComparableStack(ModItems.hull_big_steel, 3, 0), new ComparableStack(ModItems.hull_big_titanium, 2, 0), new ComparableStack(ModItems.plate_steel, 32, 0), new ComparableStack(ModItems.plate_lead, 24, 0), new ComparableStack(ModItems.plate_desh, 24, 0), new ComparableStack(ModItems.pipes_steel, 8, 0), new ComparableStack(ModItems.circuit_gold, 12, 0), new ComparableStack(ModItems.circuit_schrabidium, 8, 0), new ComparableStack(ModItems.ingot_starmetal, 26, 0), new ComparableStack(ModItems.powder_magic, 48, 0), new ComparableStack(ModBlocks.fwatz_computer, 2, 0), new ComparableStack(ModItems.crystal_xen, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.ams_limiter, 1), new Object[] {new ComparableStack(ModItems.component_limiter, 5, 0), new ComparableStack(ModItems.plate_steel, 64, 0), new ComparableStack(ModItems.plate_titanium, 128, 0), new ComparableStack(ModItems.plate_dineutronium, 16, 0), new ComparableStack(ModItems.circuit_schrabidium, 6, 0), new ComparableStack(ModItems.pipes_steel, 16, 0), new ComparableStack(ModItems.motor, 12, 0), new ComparableStack(ModItems.coil_advanced_torus, 12, 0), new ComparableStack(ModItems.entanglement_kit, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.ams_emitter, 1), new Object[] {new ComparableStack(ModItems.component_emitter, 16, 0), new ComparableStack(ModItems.plate_steel, 128, 0), new ComparableStack(ModItems.plate_titanium, 192, 0), new ComparableStack(ModItems.plate_dineutronium, 32, 0), new ComparableStack(ModItems.circuit_schrabidium, 12, 0), new ComparableStack(ModItems.coil_advanced_torus, 24, 0), new ComparableStack(ModItems.entanglement_kit, 3, 0), new ComparableStack(ModItems.crystal_horn, 1, 0), new ComparableStack(ModBlocks.fwatz_core, 1, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_radar, 1), new Object[] {new ComparableStack(ModItems.ingot_steel, 8, 0), new ComparableStack(ModItems.plate_steel, 16, 0), new ComparableStack(ModItems.ingot_polymer, 4, 0), new ComparableStack(ModItems.plate_polymer, 24, 0), new ComparableStack(ModItems.magnetron, 10, 0), new ComparableStack(ModItems.motor, 3, 0), new ComparableStack(ModItems.circuit_gold, 4, 0), new ComparableStack(ModItems.coil_copper, 12, 0), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_forcefield, 1), new Object[] {new ComparableStack(ModItems.plate_advanced_alloy, 8, 0), new ComparableStack(ModItems.plate_desh, 4, 0), new ComparableStack(ModItems.coil_gold_torus, 6, 0), new ComparableStack(ModItems.coil_magnetized_tungsten, 12, 0), new ComparableStack(ModItems.motor, 1, 0), new ComparableStack(ModItems.upgrade_radius, 1, 0), new ComparableStack(ModItems.upgrade_health, 1, 0), new ComparableStack(ModItems.circuit_targeting_tier5, 1, 0), new ComparableStack(ModBlocks.machine_transformer, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_10_kerosene, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.pipes_steel, 1, 0), new ComparableStack(ModItems.ingot_tungsten, 4, 0), new ComparableStack(ModItems.plate_steel, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_10_solid, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.coil_tungsten, 1, 0), new ComparableStack(ModItems.ingot_dura_steel, 4, 0), new ComparableStack(ModItems.plate_steel, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_10_xenon, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.plate_steel, 4, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), new ComparableStack(ModItems.arc_electrode, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.pipes_steel, 4, 0), new ComparableStack(ModItems.ingot_tungsten, 8, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene_dual, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), new ComparableStack(ModItems.ingot_tungsten, 4, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.ingot_desh, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene_triple, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.pipes_steel, 3, 0), new ComparableStack(ModItems.ingot_tungsten, 6, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.ingot_desh, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_solid, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.ingot_dura_steel, 6, 0), new ComparableStack(ModItems.coil_tungsten, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_solid_hexdecuple, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.ingot_dura_steel, 12, 0), new ComparableStack(ModItems.coil_tungsten, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_hydrogen, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.pipes_steel, 4, 0), new ComparableStack(ModItems.ingot_tungsten, 8, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.tank_steel, 1, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_hydrogen_dual, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), new ComparableStack(ModItems.ingot_tungsten, 4, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.tank_steel, 1, 0), new ComparableStack(ModItems.ingot_desh, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire_short, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_polymer, 8, 0), new ComparableStack(ModBlocks.reactor_element, 1, 0), new ComparableStack(ModItems.ingot_desh, 8, 0), new ComparableStack(ModItems.plate_saturnite, 12, 0), new ComparableStack(ModItems.board_copper, 2, 0), new ComparableStack(ModItems.ingot_uranium_fuel, 4, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_polymer, 16, 0), new ComparableStack(ModBlocks.reactor_element, 2, 0), new ComparableStack(ModItems.ingot_desh, 16, 0), new ComparableStack(ModItems.plate_saturnite, 24, 0), new ComparableStack(ModItems.board_copper, 4, 0), new ComparableStack(ModItems.ingot_uranium_fuel, 8, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire_large, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_polymer, 16, 0), new ComparableStack(ModBlocks.reactor_element, 2, 0), new ComparableStack(ModItems.ingot_desh, 24, 0), new ComparableStack(ModItems.plate_saturnite, 32, 0), new ComparableStack(ModItems.board_copper, 4, 0), new ComparableStack(ModItems.ingot_uranium_fuel, 8, 0), new ComparableStack(ModItems.pipes_steel, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene, 1), new Object[] {new ComparableStack(ModItems.seg_20, 1, 0), new ComparableStack(ModItems.pipes_steel, 8, 0), new ComparableStack(ModItems.ingot_tungsten, 16, 0), new ComparableStack(ModItems.plate_steel, 12, 0), new ComparableStack(ModItems.ingot_desh, 8, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene_dual, 1), new Object[] {new ComparableStack(ModItems.seg_20, 1, 0), new ComparableStack(ModItems.pipes_steel, 4, 0), new ComparableStack(ModItems.ingot_tungsten, 8, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.ingot_desh, 4, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene_triple, 1), new Object[] {new ComparableStack(ModItems.seg_20, 1, 0), new ComparableStack(ModItems.pipes_steel, 6, 0), new ComparableStack(ModItems.ingot_tungsten, 12, 0), new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(ModItems.ingot_desh, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid, 1), new Object[] {new ComparableStack(ModItems.seg_20, 1, 0), new ComparableStack(ModItems.coil_tungsten, 8, 0), new ComparableStack(ModItems.ingot_dura_steel, 16, 0), new ComparableStack(ModItems.plate_steel, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid_multi, 1), new Object[] {new ComparableStack(ModItems.seg_20, 1, 0), new ComparableStack(ModItems.coil_tungsten, 12, 0), new ComparableStack(ModItems.ingot_dura_steel, 18, 0), new ComparableStack(ModItems.plate_steel, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid_multier, 1), new Object[] {new ComparableStack(ModItems.seg_20, 1, 0), new ComparableStack(ModItems.coil_tungsten, 16, 0), new ComparableStack(ModItems.ingot_dura_steel, 20, 0), new ComparableStack(ModItems.plate_steel, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_kerosene, 1), new Object[] {new ComparableStack(ModItems.seg_10, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 3, 0), new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.plate_steel, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_solid, 1), new Object[] {new ComparableStack(ModItems.seg_10, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 3, 0), new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.plate_aluminium, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_xenon, 1), new Object[] {new ComparableStack(ModItems.seg_10, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 3, 0), new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.board_copper, 3, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_long_kerosene, 1), new Object[] {new ComparableStack(ModItems.seg_10, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 6, 0), new ComparableStack(ModItems.plate_titanium, 24, 0), new ComparableStack(ModItems.plate_steel, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_long_solid, 1), new Object[] {new ComparableStack(ModItems.seg_10, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 6, 0), new ComparableStack(ModItems.plate_titanium, 24, 0), new ComparableStack(ModItems.plate_aluminium, 6, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_kerosene, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModBlocks.steel_scaffold, 9, 0), new ComparableStack(ModItems.plate_titanium, 36, 0), new ComparableStack(ModItems.plate_steel, 9, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_solid, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModBlocks.steel_scaffold, 9, 0), new ComparableStack(ModItems.plate_titanium, 36, 0), new ComparableStack(ModItems.plate_aluminium, 9, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_hydrogen, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModBlocks.steel_scaffold, 9, 0), new ComparableStack(ModItems.plate_titanium, 36, 0), new ComparableStack(ModItems.plate_iron, 9, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_balefire, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModBlocks.steel_scaffold, 9, 0), new ComparableStack(ModItems.plate_titanium, 36, 0), new ComparableStack(ModItems.plate_saturnite, 9, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_kerosene, 1), new Object[] {new ComparableStack(ModItems.seg_15, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 12, 0), new ComparableStack(ModItems.plate_titanium, 48, 0), new ComparableStack(ModItems.plate_steel, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_solid, 1), new Object[] {new ComparableStack(ModItems.seg_15, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 12, 0), new ComparableStack(ModItems.plate_titanium, 48, 0), new ComparableStack(ModItems.plate_aluminium, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_hydrogen, 1), new Object[] {new ComparableStack(ModItems.seg_15, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 12, 0), new ComparableStack(ModItems.plate_titanium, 48, 0), new ComparableStack(ModItems.plate_iron, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_balefire, 1), new Object[] {new ComparableStack(ModItems.seg_15, 2, 0), new ComparableStack(ModBlocks.steel_scaffold, 12, 0), new ComparableStack(ModItems.plate_titanium, 48, 0), new ComparableStack(ModItems.plate_saturnite, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_20_kerosene, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.seg_20, 1, 0), new ComparableStack(ModBlocks.steel_scaffold, 16, 0), new ComparableStack(ModItems.plate_titanium, 64, 0), new ComparableStack(ModItems.plate_steel, 16, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_20_solid, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.seg_20, 1, 0), new ComparableStack(ModBlocks.steel_scaffold, 16, 0), new ComparableStack(ModItems.plate_titanium, 64, 0), new ComparableStack(ModItems.plate_aluminium, 16, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_he, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(Blocks.tnt, 3, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_incendiary, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.plate_titanium, 4, 0), new ComparableStack(ModItems.powder_fire, 3, 0), new ComparableStack(Blocks.tnt, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier2, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_buster, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.plate_titanium, 4, 0), new ComparableStack(ModBlocks.det_charge, 1, 0), new ComparableStack(ModBlocks.det_cord, 4, 0), new ComparableStack(ModItems.board_copper, 4, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_nuclear, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.plate_steel, 6, 0), new ComparableStack(ModItems.ingot_pu239, 1, 0), new ComparableStack(Blocks.tnt, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_nuclear_large, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(ModItems.plate_aluminium, 4, 0), new ComparableStack(ModItems.ingot_pu239, 2, 0), new ComparableStack(ModBlocks.det_charge, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_taint, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.plate_steel, 12, 0), new ComparableStack(ModBlocks.det_cord, 2, 0), new ComparableStack(ModItems.powder_magic, 12, 0), new ComparableStack(ModItems.bucket_mud, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_cloud, 1), new Object[] {new ComparableStack(ModItems.seg_10, 1, 0), new ComparableStack(ModItems.plate_steel, 12, 0), new ComparableStack(ModBlocks.det_cord, 2, 0), new ComparableStack(ModItems.grenade_pink_cloud, 2, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_he, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_steel, 16, 0), new ComparableStack(ModBlocks.det_charge, 4, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_incendiary, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_steel, 16, 0), new ComparableStack(ModBlocks.det_charge, 2, 0), new ComparableStack(ModItems.powder_fire, 8, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_nuclear, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_steel, 24, 0), new ComparableStack(ModItems.plate_titanium, 12, 0), new ComparableStack(ModItems.ingot_pu239, 3, 0), new ComparableStack(ModBlocks.det_charge, 4, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_n2, 1), new Object[] {new ComparableStack(ModItems.seg_15, 1, 0), new ComparableStack(ModItems.plate_steel, 8, 0), new ComparableStack(ModItems.plate_titanium, 20, 0), new ComparableStack(ModBlocks.det_charge, 24, 0), new ComparableStack(Blocks.redstone_block, 12, 0), new ComparableStack(ModItems.powder_magnetized_tungsten, 6, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 1, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_soyuz, 1), new Object[] {new ComparableStack(ModItems.rocket_fuel, 40, 0), new ComparableStack(ModBlocks.det_cord, 20, 0), new ComparableStack(ModItems.thruster_medium, 12, 0), new ComparableStack(ModItems.thruster_small, 12, 0), new ComparableStack(ModItems.tank_steel, 10, 0), new ComparableStack(ModItems.circuit_targeting_tier4, 4, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 8, 0), new ComparableStack(ModItems.plate_polymer, 64, 0), new ComparableStack(ModItems.fins_small_steel, 4, 0), new ComparableStack(ModItems.hull_big_titanium, 40, 0), new ComparableStack(ModItems.hull_big_steel, 24, 0), new ComparableStack(ModItems.ingot_fiberglass, 64, 0), },100);
		makeRecipe(new ComparableStack(ModItems.missile_soyuz_lander, 1), new Object[] {new ComparableStack(ModItems.rocket_fuel, 10, 0), new ComparableStack(ModItems.thruster_small, 3, 0), new ComparableStack(ModItems.tank_steel, 2, 0), new ComparableStack(ModItems.circuit_targeting_tier3, 4, 0), new ComparableStack(ModItems.plate_polymer, 32, 0), new ComparableStack(ModItems.hull_big_aluminium, 2, 0), new ComparableStack(ModItems.sphere_steel, 1, 0), new ComparableStack(ModItems.ingot_fiberglass, 12, 0), },100);
		makeRecipe(new ComparableStack(ModItems.sat_gerald, 1), new Object[] {new ComparableStack(ModItems.cap_star, 1, 0), new ComparableStack(ModItems.chlorine_pinwheel, 1, 0), new ComparableStack(ModItems.burnt_bark, 1, 0), new ComparableStack(ModItems.combine_scrap, 1, 0), new ComparableStack(ModBlocks.block_euphemium_cluster, 1, 0), new ComparableStack(ModItems.crystal_horn, 1, 0), new ComparableStack(ModItems.crystal_charred, 1, 0), new ComparableStack(ModBlocks.pink_log, 1, 0), new ComparableStack(ModItems.mp_warhead_15_balefire, 1, 0), new ComparableStack(ModBlocks.crate_red, 1, 0), new ComparableStack(ModBlocks.det_nuke, 16, 0), new ComparableStack(ModItems.ingot_starmetal, 32, 0), },100);
	}
	
	private static void makeRecipe(ComparableStack out, Object[] in, int duration) {
		
		recipes.put(out, in);
		time.put(out, duration);
	}
	
	/*
	 * recipes : [
	 *   {
	 *     output : [ "item", "hbm:tank_steel", 1, 0 ],
	 *     duration : 100,
	 *     input : [
	 *       [ "dict", "plateSteel", 6 ],
	 *       [ "dict", "plateTitanium", 2 ],
	 *       [ "item", "dye", 1, 15 ],
	 *     ]
	 *   },
	 *   {
	 *     output : [ "item", "hbm:plate_gold", 2, 0 ],
	 *     duration : 20,
	 *     input : [
	 *       [ "dict", "ingotGold", 3 ]
	 *     ]
	 *   }
	 * ]
	 */
	private static void loadJSONRecipes() {
		
		try {
			JsonObject json = gson.fromJson(new FileReader(config), JsonObject.class);
			
			JsonElement recipes = json.get("recipes");
			
			if(recipes instanceof JsonArray) {
				
				JsonArray recArray = recipes.getAsJsonArray();
				
				//go through the recipes array
				for(JsonElement recipe : recArray) {
					
					if(recipe.isJsonObject()) {
						
						JsonObject recObj = recipe.getAsJsonObject();
						
						JsonElement input = recObj.get("input");
						JsonElement output = recObj.get("output");
						JsonElement duration = recObj.get("duration");
						
						int time = 100;
						
						if(duration.isJsonPrimitive()) {
							if(duration.getAsJsonPrimitive().isNumber()) {
								time = Math.max(1, duration.getAsJsonPrimitive().getAsInt());
							}
						}
						
						if(!(input instanceof JsonArray)) {
							MainRegistry.logger.error("Error reading recipe, no input found!");
							continue;
						}
						
						if(!(output instanceof JsonArray)) {
							MainRegistry.logger.error("Error reading recipe, no output found!");
							continue;
						}
						
						Object outp = parseJsonArray(output.getAsJsonArray());
						List inp = new ArrayList();
						
						for(JsonElement in : input.getAsJsonArray()) {
							
							if(in.isJsonArray()) {
								Object i = parseJsonArray(in.getAsJsonArray());

								if(i instanceof ComparableStack || i instanceof OreDictStack)
									inp.add(i);
							}
						}
						
						if(outp instanceof ComparableStack) {
							AssemblerRecipes.recipes.put((ComparableStack) outp, inp.toArray());
							AssemblerRecipes.time.put((ComparableStack) outp, time);
						}
					}
				}
			}
			
		} catch (Exception e) {
			//shush
		}
	}
	
	private static Object parseJsonArray(JsonArray array) {
		
		boolean dict = false;
		String item = "";
		int stacksize = 1;
		int meta = 0;
		
		if(array.size() < 2)
			return null;
		
		//is index 0 "item" or "dict"?
		if(array.get(0).isJsonPrimitive()) {
			
			if(array.get(0).getAsString().equals("item")) {
				dict = false;
			} else if(array.get(0).getAsString().equals("dict")) {
				dict = true;
			} else {
				
				MainRegistry.logger.error("Error reading recipe, stack array does not have 'item' or 'dict' label!");
				return null;
			}
			
		} else {
			
			MainRegistry.logger.error("Error reading recipe, label is not a valid data type!");
			return null;
		}
		
		//is index 1 a string
		if(array.get(1).isJsonPrimitive()) {
			
			item = array.get(1).getAsString();
			
		} else {
			MainRegistry.logger.error("Error reading recipe, item string is not a valid data type!");
			return null;
		}
		
		//if index 3 exists, eval it as a stacksize
		if(array.size() > 2 && array.get(2).isJsonPrimitive()) {
			
			if(array.get(2).getAsJsonPrimitive().isNumber()) {
				
				stacksize = Math.min(64, Math.max(1, array.get(2).getAsJsonPrimitive().getAsNumber().intValue()));
				
			} else {
				
				MainRegistry.logger.error("Error reading recipe, stack size is not a valid data type!");
				return null;
			}
		}
		
		//ore dict implementation
		if(dict) {
			
			if(OreDictionary.doesOreNameExist(item)) {
				return new OreDictStack(item, stacksize);
			} else {
				
				MainRegistry.logger.error("Error reading recipe, ore dict name does not exist!");
				return null;
			}
		
		//comparable stack
		} else {
			
			//if index 4 exists, eval it as a meta
			if(array.size() > 3 && array.get(3).isJsonPrimitive()) {
				
				if(array.get(3).getAsJsonPrimitive().isNumber()) {
					
					meta = Math.max(0, array.get(3).getAsJsonPrimitive().getAsNumber().intValue());
					
				} else {
					
					MainRegistry.logger.error("Error reading recipe, metadata is not a valid data type!");
					return null;
				}
			}
			
			Item it = (Item)Item.itemRegistry.getObject(item);
			
			if(it == null) {
				
				MainRegistry.logger.error("Item could not be found!");
				return null;
			}
			
			return new ComparableStack(it, stacksize, meta);
		}
	}
	
	public static void saveTemplateJSON() {
		
		//TODO: pending
	}

	public static Map<ItemStack, List<Object>> getRecipes() {
		
		Map<ItemStack, List<Object>> recipes = new HashMap();
		
		for(Entry<ComparableStack, Object[]> entry : AssemblerRecipes.recipes.entrySet()) {
			
			List<Object> value = new ArrayList();
			
			for(Object o : entry.getValue()) {
				
				if(o instanceof ComparableStack) {
					value.add(((ComparableStack)o).toStack());
					
				} else if(o instanceof OreDictStack) {
					
					List<ItemStack> list = new ArrayList();
					OreDictStack oreStack = (OreDictStack)o;
					List<ItemStack> ores = OreDictionary.getOres(oreStack.name);
					
					for(ItemStack ore : ores) {
						ItemStack copy = ore.copy();
						copy.stackSize = oreStack.stacksize;
						list.add(copy);
					}
					
					value.add(list);
				}
			}
			
			recipes.put(entry.getKey().toStack(), value);
		}
		
		return recipes;
	}
}
