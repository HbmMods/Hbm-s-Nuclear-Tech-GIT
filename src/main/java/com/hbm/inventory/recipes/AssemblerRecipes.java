package com.hbm.inventory.recipes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.OreDictManager;
import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AssemblerRecipes {

	public static File config;
	public static File template;
	private static final Gson gson = new Gson();
	public static HashMap<ComparableStack, AStack[]> recipes = new HashMap();
	public static HashMap<ComparableStack, Integer> time = new HashMap();
	public static List<ComparableStack> recipeList = new ArrayList();
	public static HashMap<ComparableStack, HashSet<Item>> hidden = new HashMap();
	
	/**
	 * Pre-Init phase: Finds the recipe config (if exists) and checks if a template is present, if not it generates one.
	 * @param dir The suggested config folder
	 */
	public static void preInit(File dir) {
		
		if(dir == null || !dir.isDirectory())
			return;
		
		template = dir;
		
		List<File> files = Arrays.asList(dir.listFiles());
		
		for(File file : files) {
			if(file.getName().equals("hbmAssembler.json")) {
				
				config = file;
			}
		}
	}
	
	public static void loadRecipes() {
		
		if(config == null) {
			registerDefaults();
		} else {
			loadJSONRecipes();
		}
		
		generateList();
		saveTemplateJSON(template);
	}
	
	/**
	 * Generates an ordered list of outputs, used by the template item to generate subitems
	 */
	private static void generateList() {
		
		List<ComparableStack> list = new ArrayList(recipes.keySet());
		Collections.sort(list);
		recipeList = list;
	}
	
	public static ItemStack getOutputFromTempate(ItemStack stack) {
		
		if(stack != null && stack.getItem() instanceof ItemAssemblyTemplate) {
			
			ComparableStack comp = ItemAssemblyTemplate.readType(stack);
			
			//NEW
			if(comp != null) {
				return comp.toStack();
			}
			
			//LEGACY
			int i = stack.getItemDamage();
			if(i >= 0 && i < recipeList.size()) {
				return recipeList.get(i).toStack();
			}
		}
		
		return null;
	}
	
	public static List<AStack> getRecipeFromTempate(ItemStack stack) {
		
		if(stack != null && stack.getItem() instanceof ItemAssemblyTemplate) {
			
			//NEW
			ComparableStack compStack = ItemAssemblyTemplate.readType(stack);
			if(compStack != null) {
				AStack[] ret = recipes.get(compStack);
				return Arrays.asList(ret);
			}
			
			//LEGACY
			int i = stack.getItemDamage();
			if(i >= 0 && i < recipeList.size()) {
				ItemStack out = recipeList.get(i).toStack();
				
				if(out != null) {
					ComparableStack comp = new ComparableStack(out);
					AStack[] ret = recipes.get(comp);
					return Arrays.asList(ret);
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Registers regular recipes if there's no custom configuration
	 */
	private static void registerDefaults() {
		
		makeRecipe(new ComparableStack(ModItems.plate_iron, 2), new AStack[] {new OreDictStack(IRON.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_gold, 2), new AStack[] {new OreDictStack(GOLD.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_titanium, 2), new AStack[] {new OreDictStack(TI.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_aluminium, 2), new AStack[] {new OreDictStack(AL.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_steel, 2), new AStack[] {new OreDictStack(STEEL.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_lead, 2), new AStack[] {new OreDictStack(PB.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_copper, 2), new AStack[] {new OreDictStack(CU.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_advanced_alloy, 2), new AStack[] {new OreDictStack(ALLOY.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_schrabidium, 2), new AStack[] {new OreDictStack(SA326.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_combine_steel, 2), new AStack[] {new OreDictStack(CMB.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_saturnite, 2), new AStack[] {new OreDictStack(BIGMT.ingot(), 3), },30);
		makeRecipe(new ComparableStack(ModItems.plate_mixed, 4), new AStack[] {new OreDictStack(ALLOY.plate(), 2), new OreDictStack(OreDictManager.getReflector(), 1), new OreDictStack(BIGMT.plate(), 1) },50);
		makeRecipe(new ComparableStack(ModItems.wire_aluminium, 6), new AStack[] {new OreDictStack(AL.ingot(), 1), },20);
		makeRecipe(new ComparableStack(ModItems.wire_copper, 6), new AStack[] {new OreDictStack(CU.ingot(), 1), },20);
		makeRecipe(new ComparableStack(ModItems.wire_tungsten, 6), new AStack[] {new OreDictStack(W.ingot(), 1), },20);
		makeRecipe(new ComparableStack(ModItems.wire_red_copper, 6), new AStack[] {new OreDictStack(MINGRADE.ingot(), 1), },20);
		makeRecipe(new ComparableStack(ModItems.wire_advanced_alloy, 6), new AStack[] {new OreDictStack(ALLOY.ingot(), 1), },20);
		makeRecipe(new ComparableStack(ModItems.wire_gold, 6), new AStack[] {new ComparableStack(Items.gold_ingot, 1), },20);
		makeRecipe(new ComparableStack(ModItems.wire_schrabidium, 6), new AStack[] {new OreDictStack(SA326.ingot(), 1), },20);
		makeRecipe(new ComparableStack(ModItems.wire_magnetized_tungsten, 6), new AStack[] {new OreDictStack(MAGTUNG.ingot(), 1), },20);
		makeRecipe(new ComparableStack(ModItems.hazmat_cloth, 4), new AStack[] {new OreDictStack(PB.dust(), 4), new ComparableStack(Items.string, 8), },50);
		makeRecipe(new ComparableStack(ModItems.asbestos_cloth, 4), new AStack[] {new OreDictStack(ASBESTOS.ingot(), 2), new ComparableStack(Items.string, 6), new ComparableStack(Blocks.wool, 1), },50);
		makeRecipe(new ComparableStack(ModItems.filter_coal, 1), new AStack[] {new OreDictStack(COAL.dust(), 4), new ComparableStack(Items.string, 6), new ComparableStack(Items.paper, 1), },50);
		makeRecipe(new ComparableStack(ModItems.centrifuge_element, 1), new AStack[] {new ComparableStack(ModItems.tank_steel, 1), new ComparableStack(ModItems.coil_tungsten, 1), new ComparableStack(ModItems.wire_red_copper, 4), new ComparableStack(ModItems.motor, 1), },200);
		makeRecipe(new ComparableStack(ModItems.centrifuge_tower, 1), new AStack[] {new ComparableStack(ModItems.centrifuge_element, 4), new OreDictStack(STEEL.plate(), 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(ANY_PLASTIC.ingot(), 2), },150);
		makeRecipe(new ComparableStack(ModItems.magnet_circular, 1), new AStack[] {new ComparableStack(ModBlocks.fusion_conductor, 5), new OreDictStack(STEEL.ingot(), 4), new OreDictStack(ALLOY.plate(), 6), },150);
		makeRecipe(new ComparableStack(ModItems.reactor_core, 1), new AStack[] {new OreDictStack(PB.ingot(), 8), new OreDictStack(BE.ingot(), 6), new OreDictStack(STEEL.plate(), 16), new OreDictStack(OreDictManager.getReflector(), 8), new OreDictStack(FIBER.ingot(), 2) },100);
		makeRecipe(new ComparableStack(ModItems.rtg_unit, 1), new AStack[] {new ComparableStack(ModItems.thermo_element, 2), new ComparableStack(ModItems.board_copper, 1), new OreDictStack(PB.ingot(), 2), new OreDictStack(STEEL.plate(), 2), new ComparableStack(ModItems.circuit_copper, 1), },100);
		makeRecipe(new ComparableStack(ModItems.thermo_unit_empty, 1), new AStack[] {new ComparableStack(ModItems.coil_copper_torus, 3), new OreDictStack(STEEL.ingot(), 3), new OreDictStack(TI.plate(), 6), new ComparableStack(ModItems.plate_polymer, 12), },100);
		makeRecipe(new ComparableStack(ModItems.levitation_unit, 1), new AStack[] {new ComparableStack(ModItems.coil_copper, 4), new ComparableStack(ModItems.coil_tungsten, 2), new OreDictStack(TI.plate(), 6), new ComparableStack(ModItems.nugget_schrabidium, 2), },100);
		makeRecipe(new ComparableStack(ModItems.drill_titanium, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 2), new OreDictStack(DURA.ingot(), 2), new ComparableStack(ModItems.bolt_dura_steel, 2), new OreDictStack(TI.plate(), 6), },100);
		makeRecipe(new ComparableStack(ModItems.telepad, 1), new AStack[] {new OreDictStack(ANY_PLASTIC.ingot(), 12), new OreDictStack(SA326.plate(), 2), new OreDictStack(CMB.plate(), 4), new OreDictStack(STEEL.plate(), 2), new ComparableStack(ModItems.wire_gold, 6), new ComparableStack(ModItems.circuit_schrabidium, 1), },300);
		makeRecipe(new ComparableStack(ModItems.entanglement_kit, 1), new AStack[] {new ComparableStack(ModItems.coil_magnetized_tungsten, 6), new OreDictStack(PB.plate(), 16), new OreDictStack(OreDictManager.getReflector(), 4), new ComparableStack(ModItems.singularity_counter_resonant, 1), new ComparableStack(ModItems.singularity_super_heated, 1), new ComparableStack(ModItems.powder_power, 4), },200);
		makeRecipe(new ComparableStack(ModItems.dysfunctional_reactor, 1), new AStack[] {new OreDictStack(STEEL.plate(), 15), new OreDictStack(PB.ingot(), 5), new ComparableStack(ModItems.rod_quad_empty, 10), new OreDictStack("dyeBrown", 3), },200);
		makeRecipe(new ComparableStack(ModItems.missile_assembly, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 1), new ComparableStack(ModItems.hull_small_aluminium, 4), new OreDictStack(STEEL.ingot(), 2), new OreDictStack(TI.plate(), 6), new ComparableStack(ModItems.wire_aluminium, 6), new ComparableStack(ModItems.canister_full, 3, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		makeRecipe(new ComparableStack(ModItems.missile_carrier, 1), new AStack[] {new ComparableStack(ModItems.fluid_barrel_full, 16, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.thruster_medium, 4), new ComparableStack(ModItems.thruster_large, 1), new ComparableStack(ModItems.hull_big_titanium, 6), new ComparableStack(ModItems.hull_big_steel, 2), new ComparableStack(ModItems.hull_small_aluminium, 12), new OreDictStack(TI.plate(), 24), new ComparableStack(ModItems.plate_polymer, 128), new ComparableStack(ModBlocks.det_cord, 8), new ComparableStack(ModItems.circuit_targeting_tier3, 12), new ComparableStack(ModItems.circuit_targeting_tier4, 3), },4800);
		makeRecipe(new ComparableStack(ModItems.warhead_generic_small, 1), new AStack[] {new OreDictStack(TI.plate(), 5), new OreDictStack(STEEL.plate(), 3), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 2), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_generic_medium, 1), new AStack[] {new OreDictStack(TI.plate(), 8), new OreDictStack(STEEL.plate(), 5), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 4), },150);
		makeRecipe(new ComparableStack(ModItems.warhead_generic_large, 1), new AStack[] {new OreDictStack(TI.plate(), 15), new OreDictStack(STEEL.plate(), 8), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 8), },200);
		makeRecipe(new ComparableStack(ModItems.warhead_incendiary_small, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_small, 1), new OreDictStack(P_RED.dust(), 4), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_incendiary_medium, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_medium, 1), new OreDictStack(P_RED.dust(), 8), },150);
		makeRecipe(new ComparableStack(ModItems.warhead_incendiary_large, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_large, 1), new OreDictStack(P_RED.dust(), 16), },200);
		makeRecipe(new ComparableStack(ModItems.warhead_cluster_small, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_small, 1), new ComparableStack(ModItems.pellet_cluster, 4), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_cluster_medium, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_medium, 1), new ComparableStack(ModItems.pellet_cluster, 8), },150);
		makeRecipe(new ComparableStack(ModItems.warhead_cluster_large, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_large, 1), new ComparableStack(ModItems.pellet_cluster, 16), },200);
		makeRecipe(new ComparableStack(ModItems.warhead_buster_small, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_small, 1), new ComparableStack(ModBlocks.det_cord, 8), },100);
		makeRecipe(new ComparableStack(ModItems.warhead_buster_medium, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_medium, 1), new ComparableStack(ModBlocks.det_cord, 4), new ComparableStack(ModBlocks.det_charge, 4), },150);
		makeRecipe(new ComparableStack(ModItems.warhead_buster_large, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_large, 1), new ComparableStack(ModBlocks.det_charge, 8), },200);
		makeRecipe(new ComparableStack(ModItems.warhead_nuclear, 1), new AStack[] {new ComparableStack(ModItems.boy_shielding, 1), new ComparableStack(ModItems.boy_target, 1), new ComparableStack(ModItems.boy_bullet, 1), new ComparableStack(ModItems.boy_propellant, 1), new ComparableStack(ModItems.wire_red_copper, 6), new OreDictStack(TI.plate(), 20), new OreDictStack(STEEL.plate(), 12), },300);
		makeRecipe(new ComparableStack(ModItems.warhead_mirv, 1), new AStack[] {new OreDictStack(TI.plate(), 20), new OreDictStack(STEEL.plate(), 12), new OreDictStack(PU239.ingot(), 1), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 8), new OreDictStack(BE.ingot(), 4), new OreDictStack(LI.ingot(), 4), new ComparableStack(ModItems.cell_deuterium, 6), },500);
		makeRecipe(new ComparableStack(ModItems.warhead_volcano, 1), new AStack[] {new OreDictStack(TI.plate(), 24), new OreDictStack(STEEL.plate(), 16), new ComparableStack(ModBlocks.det_nuke, 3), new OreDictStack(U238.block(), 24), new ComparableStack(ModItems.circuit_tantalium, 5) }, 600);
		makeRecipe(new ComparableStack(ModItems.warhead_thermo_endo, 1), new AStack[] {new ComparableStack(ModBlocks.therm_endo, 2), new OreDictStack(TI.plate(), 12), new OreDictStack(STEEL.plate(), 6), },300);
		makeRecipe(new ComparableStack(ModItems.warhead_thermo_exo, 1), new AStack[] {new ComparableStack(ModBlocks.therm_exo, 2), new OreDictStack(TI.plate(), 12), new OreDictStack(STEEL.plate(), 6), },300);
		makeRecipe(new ComparableStack(ModItems.fuel_tank_small, 1), new AStack[] {new ComparableStack(ModItems.canister_full, 4, Fluids.KEROSENE.getID()), new OreDictStack(TI.plate(), 6), new OreDictStack(STEEL.plate(), 2), },100);
		makeRecipe(new ComparableStack(ModItems.fuel_tank_medium, 1), new AStack[] {new ComparableStack(ModItems.fuel_tank_small, 3), new OreDictStack(TI.plate(), 4), new OreDictStack(STEEL.plate(), 2), },150);
		makeRecipe(new ComparableStack(ModItems.fuel_tank_large, 1), new AStack[] {new ComparableStack(ModItems.fuel_tank_medium, 3), new OreDictStack(TI.plate(), 4), new OreDictStack(STEEL.plate(), 2), },200);
		makeRecipe(new ComparableStack(ModItems.thruster_small, 1), new AStack[] {new OreDictStack(STEEL.plate(), 2), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.wire_aluminium, 4), },100);
		makeRecipe(new ComparableStack(ModItems.thruster_medium, 1), new AStack[] {new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(STEEL.plate(), 2), new ComparableStack(ModItems.hull_small_steel, 1), new ComparableStack(ModItems.hull_big_steel, 1), new ComparableStack(ModItems.wire_copper, 4), },150);
		makeRecipe(new ComparableStack(ModItems.thruster_large, 1), new AStack[] {new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(STEEL.plate(), 4), new ComparableStack(ModItems.hull_big_steel, 2), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		makeRecipe(new ComparableStack(ModItems.thruster_nuclear, 1), new AStack[] {new ComparableStack(ModItems.thruster_large, 1), new ComparableStack(ModItems.tank_steel, 2), new ComparableStack(ModBlocks.deco_pipe_quad, 3), new ComparableStack(ModItems.board_copper, 6), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModBlocks.reactor_research, 1), },600);
		makeRecipe(new ComparableStack(ModItems.sat_base, 1), new AStack[] {new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(STEEL.plate(), 6), new ComparableStack(ModItems.plate_desh, 4), new ComparableStack(ModItems.hull_big_titanium, 3), new ComparableStack(ModItems.fluid_barrel_full, 1, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.photo_panel, 24), new ComparableStack(ModItems.board_copper, 12), new ComparableStack(ModItems.circuit_gold, 6), new ComparableStack(ModItems.battery_lithium_cell_6, 1), },500);
		makeRecipe(new ComparableStack(ModItems.sat_head_mapper, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 4), new OreDictStack(STEEL.plate(), 6), new ComparableStack(ModItems.hull_small_steel, 3), new ComparableStack(ModItems.plate_desh, 2), new ComparableStack(ModItems.circuit_gold, 2), new OreDictStack(RUBBER.ingot(), 12), new OreDictStack(REDSTONE.dust(), 6), new ComparableStack(Items.diamond, 1), new ComparableStack(Blocks.glass_pane, 6), },400);
		makeRecipe(new ComparableStack(ModItems.sat_head_scanner, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 6), new OreDictStack(TI.plate(), 32), new ComparableStack(ModItems.plate_desh, 6), new ComparableStack(ModItems.magnetron, 6), new ComparableStack(ModItems.coil_advanced_torus, 2), new ComparableStack(ModItems.circuit_gold, 6), new OreDictStack(RUBBER.ingot(), 6), new ComparableStack(Items.diamond, 1), },400);
		makeRecipe(new ComparableStack(ModItems.sat_head_radar, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 4), new OreDictStack(TI.plate(), 32), new ComparableStack(ModItems.magnetron, 12), new OreDictStack(RUBBER.ingot(), 16), new ComparableStack(ModItems.wire_red_copper, 16), new ComparableStack(ModItems.coil_gold, 3), new ComparableStack(ModItems.circuit_gold, 5), new ComparableStack(Items.diamond, 1), },400);
		makeRecipe(new ComparableStack(ModItems.sat_head_laser, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 12), new OreDictStack(W.ingot(), 16), new OreDictStack(ANY_PLASTIC.ingot(), 6), new OreDictStack(RUBBER.ingot(), 16), new ComparableStack(ModItems.board_copper, 24), new ComparableStack(ModItems.circuit_targeting_tier5, 2), new OreDictStack(REDSTONE.dust(), 16), new ComparableStack(Items.diamond, 5), new ComparableStack(Blocks.glass_pane, 16), },450);
		makeRecipe(new ComparableStack(ModItems.sat_head_resonator, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 32), new OreDictStack(ANY_PLASTIC.ingot(), 48), new OreDictStack(RUBBER.ingot(), 8), new ComparableStack(ModItems.crystal_xen, 1), new OreDictStack(STAR.ingot(), 7), new ComparableStack(ModItems.circuit_targeting_tier5, 6), new ComparableStack(ModItems.circuit_targeting_tier6, 2), },1000);
		makeRecipe(new ComparableStack(ModItems.sat_foeq, 1), new AStack[] {new OreDictStack(STEEL.plate(), 8), new OreDictStack(TI.plate(), 12), new ComparableStack(ModItems.plate_desh, 8), new ComparableStack(ModItems.hull_big_titanium, 3), new ComparableStack(ModItems.fluid_barrel_full, 1, Fluids.HYDROGEN.getID()), new ComparableStack(ModItems.photo_panel, 16), new ComparableStack(ModItems.thruster_nuclear, 1), new ComparableStack(ModItems.ingot_uranium_fuel, 6), new ComparableStack(ModItems.circuit_targeting_tier5, 6), new ComparableStack(ModItems.magnetron, 3), new ComparableStack(ModItems.battery_lithium_cell_6, 1), },1200);
		makeRecipe(new ComparableStack(ModItems.sat_miner, 1), new AStack[] {new OreDictStack(BIGMT.plate(), 24), new ComparableStack(ModItems.plate_desh, 8), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.drill_titanium, 2), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.fluid_barrel_full, 1, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.thruster_small, 1), new ComparableStack(ModItems.photo_panel, 12), new ComparableStack(ModItems.centrifuge_element, 4), new ComparableStack(ModItems.magnetron, 3), new OreDictStack(RUBBER.ingot(), 12), new ComparableStack(ModItems.battery_lithium_cell_6, 1), },600);
		makeRecipe(new ComparableStack(ModItems.sat_lunar_miner, 1), new AStack[] {new ComparableStack(ModItems.ingot_meteorite, 4), new ComparableStack(ModItems.plate_desh, 4), new ComparableStack(ModItems.motor_desh, 2), new ComparableStack(ModItems.drill_titanium, 2), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.fluid_barrel_full, 1, Fluids.KEROSENE.getID()), new ComparableStack(ModItems.thruster_small, 1), new ComparableStack(ModItems.photo_panel, 12), new ComparableStack(ModItems.magnetron, 3), new OreDictStack(RUBBER.ingot(), 12), new ComparableStack(ModItems.battery_lithium_cell_6, 1), },600);
		makeRecipe(new ComparableStack(ModItems.chopper_head, 1), new AStack[] {new ComparableStack(ModBlocks.reinforced_glass, 2), new ComparableStack(ModBlocks.fwatz_computer, 1), new OreDictStack(CMB.ingot(), 22), new ComparableStack(ModItems.wire_magnetized_tungsten, 4), },300);
		makeRecipe(new ComparableStack(ModItems.chopper_gun, 1), new AStack[] {new OreDictStack(CMB.plate(), 4), new OreDictStack(CMB.ingot(), 2), new ComparableStack(ModItems.wire_tungsten, 6), new ComparableStack(ModItems.coil_magnetized_tungsten, 1), new ComparableStack(ModItems.motor, 1), },150);
		makeRecipe(new ComparableStack(ModItems.chopper_torso, 1), new AStack[] {new OreDictStack(CMB.ingot(), 26), new ComparableStack(ModBlocks.fwatz_computer, 1), new ComparableStack(ModItems.wire_magnetized_tungsten, 4), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.chopper_blades, 2), },350);
		makeRecipe(new ComparableStack(ModItems.chopper_tail, 1), new AStack[] {new OreDictStack(CMB.plate(), 8), new OreDictStack(CMB.ingot(), 5), new ComparableStack(ModItems.wire_magnetized_tungsten, 4), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.chopper_blades, 2), },200);
		makeRecipe(new ComparableStack(ModItems.chopper_wing, 1), new AStack[] {new OreDictStack(CMB.plate(), 6), new OreDictStack(CMB.ingot(), 3), new ComparableStack(ModItems.wire_magnetized_tungsten, 2), },150);
		makeRecipe(new ComparableStack(ModItems.chopper_blades, 1), new AStack[] {new OreDictStack(CMB.plate(), 8), new OreDictStack(STEEL.plate(), 2), new OreDictStack(CMB.ingot(), 2), },200);
		makeRecipe(new ComparableStack(ModItems.circuit_aluminium, 1), new AStack[] {new ComparableStack(ModItems.circuit_raw, 1), },50);
		makeRecipe(new ComparableStack(ModItems.circuit_copper, 1), new AStack[] {new ComparableStack(ModItems.circuit_aluminium, 1), new ComparableStack(ModItems.wire_copper, 4), new OreDictStack(NETHERQUARTZ.dust(), 1), new OreDictStack(CU.plate(), 1), },100);
		makeRecipe(new ComparableStack(ModItems.circuit_red_copper, 1), new AStack[] {new ComparableStack(ModItems.circuit_copper, 1), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(GOLD.dust(), 1), new ComparableStack(ModItems.plate_polymer, 1), },150);
		makeRecipe(new ComparableStack(ModItems.crt_display, 8), new AStack[] {new OreDictStack(AL.dust(), 2), new ComparableStack(Blocks.glass_pane, 2), new ComparableStack(ModItems.wire_tungsten, 4), new ComparableStack(ModItems.hull_small_steel, 1) }, 100);
		makeRecipe(new ComparableStack(ModItems.tritium_deuterium_cake, 1), new AStack[] {new ComparableStack(ModItems.cell_deuterium, 6), new ComparableStack(ModItems.cell_tritium, 2), new OreDictStack(LI.ingot(), 4), },150);
		makeRecipe(new ComparableStack(ModItems.pellet_cluster, 1), new AStack[] {new OreDictStack(STEEL.plate(), 4), new ComparableStack(Blocks.tnt, 1), }, 50);
		makeRecipe(new ComparableStack(ModItems.pellet_buckshot, 1), new AStack[] {new OreDictStack(PB.nugget(), 6), }, 50);
		makeRecipe(new ComparableStack(ModItems.australium_iii, 1), new AStack[] {new ComparableStack(ModItems.nugget_australium, 6), new OreDictStack(STEEL.ingot(), 1), new OreDictStack(STEEL.plate(), 6), new OreDictStack(CU.plate(), 2), new ComparableStack(ModItems.wire_copper, 6), },150);
		makeRecipe(new ComparableStack(ModItems.magnetron, 1), new AStack[] {new OreDictStack(ALLOY.ingot(), 1), new OreDictStack(ALLOY.plate(), 2), new ComparableStack(ModItems.wire_tungsten, 1), new ComparableStack(ModItems.coil_tungsten, 1), },100);
		makeRecipe(new ComparableStack(ModItems.pellet_schrabidium, 1), new AStack[] {new OreDictStack(SA326.ingot(), 5), new OreDictStack(IRON.plate(), 2), }, 200);
		makeRecipe(new ComparableStack(ModItems.pellet_hes, 1), new AStack[] {new ComparableStack(ModItems.ingot_hes, 5), new OreDictStack(IRON.plate(), 2), }, 200);
		makeRecipe(new ComparableStack(ModItems.pellet_mes, 1), new AStack[] {new ComparableStack(ModItems.ingot_schrabidium_fuel, 5), new OreDictStack(IRON.plate(), 2), }, 200);
		makeRecipe(new ComparableStack(ModItems.pellet_les, 1), new AStack[] {new ComparableStack(ModItems.ingot_les, 5), new OreDictStack(IRON.plate(), 2), }, 200);
		makeRecipe(new ComparableStack(ModItems.pellet_beryllium, 1), new AStack[] {new OreDictStack(BE.ingot(), 5), new OreDictStack(IRON.plate(), 2), }, 200);
		makeRecipe(new ComparableStack(ModItems.pellet_neptunium, 1), new AStack[] {new OreDictStack(NP237.ingot(), 5), new OreDictStack(IRON.plate(), 2), }, 200);
		makeRecipe(new ComparableStack(ModItems.pellet_lead, 1), new AStack[] {new OreDictStack(PB.ingot(), 5), new OreDictStack(IRON.plate(), 2), }, 200);
		makeRecipe(new ComparableStack(ModItems.pellet_advanced, 1), new AStack[] {new OreDictStack(DESH.ingot(), 5), new OreDictStack(IRON.plate(), 2), }, 200);
		makeRecipe(new ComparableStack(ModItems.upgrade_template, 1), new AStack[] {new OreDictStack(STEEL.plate(), 1), new OreDictStack(IRON.plate(), 4), new OreDictStack(CU.plate(), 2), new ComparableStack(ModItems.wire_copper, 6), },100);
		makeRecipe(new ComparableStack(ModItems.upgrade_speed_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(MINGRADE.dust(), 4), new OreDictStack(REDSTONE.dust(), 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		makeRecipe(new ComparableStack(ModItems.upgrade_speed_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_speed_1, 1), new OreDictStack(MINGRADE.dust(), 2), new OreDictStack(REDSTONE.dust(), 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(ANY_PLASTIC.ingot(), 2), },300);
		makeRecipe(new ComparableStack(ModItems.upgrade_speed_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_speed_2, 1), new OreDictStack(MINGRADE.dust(), 2), new OreDictStack(REDSTONE.dust(), 6), new OreDictStack(DESH.ingot(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.upgrade_effect_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(DURA.dust(), 4), new OreDictStack(STEEL.dust(), 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		makeRecipe(new ComparableStack(ModItems.upgrade_effect_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_effect_1, 1), new OreDictStack(DURA.dust(), 2), new OreDictStack(STEEL.dust(), 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(ANY_PLASTIC.ingot(), 2), },300);
		makeRecipe(new ComparableStack(ModItems.upgrade_effect_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_effect_2, 1), new OreDictStack(DURA.dust(), 2), new OreDictStack(STEEL.dust(), 6), new OreDictStack(DESH.ingot(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.upgrade_power_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(LAPIS.dust(), 4), new ComparableStack(Items.glowstone_dust, 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		makeRecipe(new ComparableStack(ModItems.upgrade_power_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_power_1, 1), new OreDictStack(LAPIS.dust(), 2), new ComparableStack(Items.glowstone_dust, 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(ANY_PLASTIC.ingot(), 2), },300);
		makeRecipe(new ComparableStack(ModItems.upgrade_power_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_power_2, 1), new OreDictStack(LAPIS.dust(), 2), new ComparableStack(Items.glowstone_dust, 6), new OreDictStack(DESH.ingot(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.upgrade_fortune_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(DIAMOND.dust(), 4), new OreDictStack(IRON.dust(), 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		makeRecipe(new ComparableStack(ModItems.upgrade_fortune_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_fortune_1, 1), new OreDictStack(DIAMOND.dust(), 2), new OreDictStack(IRON.dust(), 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(ANY_PLASTIC.ingot(), 2), },300);
		makeRecipe(new ComparableStack(ModItems.upgrade_fortune_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_fortune_2, 1), new OreDictStack(DIAMOND.dust(), 2), new OreDictStack(IRON.dust(), 6), new OreDictStack(DESH.ingot(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new OreDictStack(POLYMER.dust(), 4), new OreDictStack(W.dust(), 6), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_afterburn_1, 1), new OreDictStack(POLYMER.dust(), 2), new OreDictStack(W.dust(), 4), new ComparableStack(ModItems.circuit_red_copper, 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(ANY_PLASTIC.ingot(), 2), },300);
		makeRecipe(new ComparableStack(ModItems.upgrade_afterburn_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_afterburn_2, 1), new OreDictStack(POLYMER.dust(), 2), new OreDictStack(W.dust(), 6), new OreDictStack(DESH.ingot(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.upgrade_radius, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new ComparableStack(Items.glowstone_dust, 6), new OreDictStack(DIAMOND.dust(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.upgrade_health, 1), new AStack[] {new ComparableStack(ModItems.upgrade_template, 1), new ComparableStack(Items.glowstone_dust, 6), new OreDictStack(TI.dust(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_1, 1), new AStack[] {new ComparableStack(ModItems.upgrade_speed_3, 1), new ComparableStack(ModItems.upgrade_effect_3, 1), new OreDictStack(DESH.ingot(), 8), new ComparableStack(ModItems.powder_power, 16), new ComparableStack(ModItems.crystal_lithium, 4), new ComparableStack(ModItems.circuit_schrabidium, 1), }, 200);
		makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_2, 1), new AStack[] {new ComparableStack(ModItems.upgrade_overdrive_1, 1), new ComparableStack(ModItems.upgrade_afterburn_1, 1), new ComparableStack(ModItems.upgrade_speed_3, 1), new ComparableStack(ModItems.upgrade_effect_3, 1), new ComparableStack(ModItems.crystal_lithium, 8), new ComparableStack(ModItems.circuit_tantalium, 16), }, 300);
		makeRecipe(new ComparableStack(ModItems.upgrade_overdrive_3, 1), new AStack[] {new ComparableStack(ModItems.upgrade_overdrive_2, 1), new ComparableStack(ModItems.upgrade_afterburn_1, 1), new ComparableStack(ModItems.upgrade_speed_3, 1), new ComparableStack(ModItems.upgrade_effect_3, 1), new ComparableStack(ModItems.crystal_lithium, 16), new OreDictStack(KEY_CIRCUIT_BISMUTH), }, 500);
		makeRecipe(new ComparableStack(ModItems.fuse, 1), new AStack[] {new OreDictStack(STEEL.plate(), 2), new ComparableStack(Blocks.glass_pane, 1), new ComparableStack(ModItems.wire_aluminium, 1), },100);
		makeRecipe(new ComparableStack(ModItems.redcoil_capacitor, 1), new AStack[] {new OreDictStack(GOLD.plate(), 3), new ComparableStack(ModItems.fuse, 1), new ComparableStack(ModItems.wire_advanced_alloy, 4), new ComparableStack(ModItems.coil_advanced_alloy, 6), new ComparableStack(Blocks.redstone_block, 2), },200);
		makeRecipe(new ComparableStack(ModItems.titanium_filter, 1), new AStack[] {new OreDictStack(PB.plate(), 3), new ComparableStack(ModItems.fuse, 1), new ComparableStack(ModItems.wire_tungsten, 4), new OreDictStack(TI.plate(), 6), new OreDictStack(U238.ingot(), 2), },200);
		makeRecipe(new ComparableStack(ModItems.part_lithium, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new OreDictStack(LI.dust(), 1), },50);
		makeRecipe(new ComparableStack(ModItems.part_beryllium, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new OreDictStack(BE.dust(), 1), },50);
		makeRecipe(new ComparableStack(ModItems.part_carbon, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new OreDictStack(COAL.dust(), 1), },50);
		makeRecipe(new ComparableStack(ModItems.part_copper, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new OreDictStack(CU.dust(), 1), },50);
		makeRecipe(new ComparableStack(ModItems.part_plutonium, 1), new AStack[] {new ComparableStack(ModItems.plate_polymer, 1), new ComparableStack(ModItems.powder_plutonium, 1), },50);
		makeRecipe(new ComparableStack(ModItems.thermo_element, 1), new AStack[] {new OreDictStack(STEEL.plate(), 2), new OreDictStack(IRON.plate(), 1), new OreDictStack(CU.plate(), 2), new ComparableStack(ModItems.wire_red_copper, 2), new ComparableStack(ModItems.wire_aluminium, 2), new OreDictStack(NETHERQUARTZ.dust(), 2), },150);
		makeRecipe(new ComparableStack(ModItems.limiter, 1), new AStack[] {new OreDictStack(STEEL.plate(), 3), new OreDictStack(IRON.plate(), 1), new ComparableStack(ModItems.circuit_copper, 2), new ComparableStack(ModItems.wire_copper, 4), },150);
		makeRecipe(new ComparableStack(ModItems.plate_dalekanium, 1), new AStack[] {new ComparableStack(ModBlocks.block_meteor, 1), },50);
		makeRecipe(new ComparableStack(ModBlocks.block_meteor, 1), new AStack[] {new ComparableStack(ModItems.fragment_meteorite, 100), },500);
		makeRecipe(new ComparableStack(ModBlocks.cmb_brick, 8), new AStack[] {new OreDictStack(CMB.ingot(), 1), new OreDictStack(CMB.plate(), 8), },100);
		makeRecipe(new ComparableStack(ModBlocks.cmb_brick_reinforced, 8), new AStack[] {new ComparableStack(ModBlocks.block_magnetized_tungsten, 4), new ComparableStack(ModBlocks.brick_concrete, 4), new ComparableStack(ModBlocks.cmb_brick, 1), new OreDictStack(STEEL.plate(), 4), },200);
		makeRecipe(new ComparableStack(ModBlocks.seal_frame, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 3), new ComparableStack(ModItems.wire_aluminium, 4), new OreDictStack(REDSTONE.dust(), 2), new ComparableStack(ModBlocks.steel_roof, 5), },50);
		makeRecipe(new ComparableStack(ModBlocks.seal_controller, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 3), new OreDictStack(ANY_PLASTIC.ingot(), 4), new OreDictStack(MINGRADE.ingot(), 1), new OreDictStack(REDSTONE.dust(), 4), new ComparableStack(ModBlocks.steel_roof, 5), },100);
		makeRecipe(new ComparableStack(ModBlocks.machine_centrifuge, 1), new AStack[] {new ComparableStack(ModItems.centrifuge_tower, 1), new OreDictStack(STEEL.ingot(), 4), new OreDictStack(IRON.ingot(), 4), new OreDictStack(STEEL.plate(), 2), new OreDictStack(CU.plate(), 2), new ComparableStack(ModItems.wire_red_copper, 8), },250);
		makeRecipe(new ComparableStack(ModBlocks.machine_gascent, 1), new AStack[] {new ComparableStack(ModItems.centrifuge_tower, 1), new OreDictStack(STEEL.ingot(), 4), new OreDictStack(DESH.ingot(), 2), new ComparableStack(ModItems.coil_tungsten, 4), new ComparableStack(ModItems.wire_red_copper, 16) },300);
		makeRecipe(new ComparableStack(ModBlocks.machine_rtg_furnace_off, 1), new AStack[] {new ComparableStack(Blocks.furnace, 1), new ComparableStack(ModItems.rtg_unit, 3), new OreDictStack(PB.plate(), 6), new OreDictStack(OreDictManager.getReflector(), 4), new OreDictStack(CU.plate(), 2), },150);
		makeRecipe(new ComparableStack(ModBlocks.machine_diesel, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 4), new ComparableStack(Blocks.piston, 4), new OreDictStack(STEEL.ingot(), 6), new OreDictStack(MINGRADE.ingot(), 2), new OreDictStack(CU.plate(), 4), new ComparableStack(ModItems.wire_red_copper, 6), },200);
		makeRecipe(new ComparableStack(ModBlocks.machine_selenium, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 4), new OreDictStack(TI.plate(), 6), new OreDictStack(CU.plate(), 8), new ComparableStack(ModItems.hull_big_steel, 1), new ComparableStack(ModItems.hull_small_steel, 9), new ComparableStack(ModItems.pedestal_steel, 1), new ComparableStack(ModItems.coil_copper, 4), },250);
		makeRecipe(new ComparableStack(ModBlocks.machine_rtg_grey, 1), new AStack[] {new ComparableStack(ModItems.rtg_unit, 3), new OreDictStack(STEEL.plate(), 4), new ComparableStack(ModItems.wire_red_copper, 4), new OreDictStack(ANY_PLASTIC.ingot(), 3), },200);
		makeRecipe(new ComparableStack(ModBlocks.machine_battery, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 4), new OreDictStack(S.dust(), 12), new OreDictStack(PB.dust(), 12), new OreDictStack(MINGRADE.ingot(), 2), new ComparableStack(ModItems.wire_red_copper, 4), },200);
		makeRecipe(new ComparableStack(ModBlocks.machine_lithium_battery, 1), new AStack[] {new OreDictStack(ANY_PLASTIC.ingot(), 4), new OreDictStack(CO.dust(), 12), new OreDictStack(LI.dust(), 12), new OreDictStack(ALLOY.ingot(), 2), new ComparableStack(ModItems.wire_red_copper, 4), },400);
		makeRecipe(new ComparableStack(ModBlocks.machine_schrabidium_battery, 1), new AStack[] {new OreDictStack(DESH.ingot(), 4), new OreDictStack(NP237.dust(), 12), new OreDictStack(SA326.dust(), 12), new OreDictStack(SA326.ingot(), 2), new ComparableStack(ModItems.wire_schrabidium, 4), },800);
		makeRecipe(new ComparableStack(ModBlocks.machine_dineutronium_battery, 1), new AStack[] {new OreDictStack(DNT.ingot(), 24), new ComparableStack(ModItems.powder_spark_mix, 12), new ComparableStack(ModItems.battery_spark_cell_1000, 1), new OreDictStack(CMB.ingot(), 32), new ComparableStack(ModItems.coil_magnetized_tungsten, 8), },1600);
		makeRecipe(new ComparableStack(ModBlocks.machine_shredder, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 2), new OreDictStack(STEEL.plate(), 4), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.wire_red_copper, 2), new ComparableStack(ModBlocks.steel_beam, 2), new ComparableStack(Blocks.iron_bars, 2), new ComparableStack(ModBlocks.red_wire_coated, 1), },200);
		makeRecipe(new ComparableStack(ModBlocks.machine_well, 1), new AStack[] {new ComparableStack(ModBlocks.steel_scaffold, 20), new ComparableStack(ModBlocks.steel_beam, 8), new ComparableStack(ModItems.tank_steel, 2), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.pipes_steel, 3), new ComparableStack(ModItems.drill_titanium, 1), new ComparableStack(ModItems.wire_red_copper, 6), },250);
		makeRecipe(new ComparableStack(ModBlocks.machine_pumpjack, 1), new AStack[] {new ComparableStack(ModBlocks.steel_scaffold, 8), new OreDictStack(STEEL.block(), 8), new ComparableStack(ModItems.pipes_steel, 4), new ComparableStack(ModItems.tank_steel, 4), new OreDictStack(STEEL.ingot(), 24), new OreDictStack(STEEL.plate(), 16), new OreDictStack(AL.plate(), 6), new ComparableStack(ModItems.drill_titanium, 1), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.wire_red_copper, 8), },400);
		makeRecipe(new ComparableStack(ModBlocks.machine_flare, 1), new AStack[] {new ComparableStack(ModBlocks.steel_scaffold, 28), new ComparableStack(ModItems.tank_steel, 2), new ComparableStack(ModItems.pipes_steel, 2), new ComparableStack(ModItems.hull_small_steel, 1), new ComparableStack(ModItems.thermo_element, 3), },200);
		makeRecipe(new ComparableStack(ModBlocks.machine_refinery, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 16), new OreDictStack(STEEL.plate(), 20), new OreDictStack(CU.plate(), 16), new ComparableStack(ModItems.hull_big_steel, 6), new ComparableStack(ModItems.pipes_steel, 2), new ComparableStack(ModItems.coil_tungsten, 8), new ComparableStack(ModItems.wire_red_copper, 8), new ComparableStack(ModItems.circuit_copper, 2), new ComparableStack(ModItems.circuit_red_copper, 1), new ComparableStack(ModItems.plate_polymer, 8), },350);
		makeRecipe(new ComparableStack(ModBlocks.machine_epress, 1), new AStack[] {new OreDictStack(STEEL.plate(), 8), new ComparableStack(ModItems.plate_polymer, 4), new ComparableStack(ModItems.pipes_steel, 1), new ComparableStack(ModItems.bolt_tungsten, 4), new ComparableStack(ModItems.coil_copper, 2), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.circuit_copper, 1), new ComparableStack(ModItems.canister_full, 1, Fluids.LUBRICANT.getID()), },160);
		makeRecipe(new ComparableStack(ModBlocks.machine_chemplant, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 8), new OreDictStack(CU.plate(), 6), new ComparableStack(ModItems.tank_steel, 4), new ComparableStack(ModItems.hull_big_steel, 1), new ComparableStack(ModItems.coil_tungsten, 3), new ComparableStack(ModItems.circuit_copper, 2), new ComparableStack(ModItems.circuit_red_copper, 1), new ComparableStack(ModItems.plate_polymer, 8), },200);
		makeRecipe(new ComparableStack(ModBlocks.machine_crystallizer, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 4), new ComparableStack(ModItems.pipes_steel, 1), new OreDictStack(DESH.ingot(), 4), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.blades_advanced_alloy, 2), new OreDictStack(STEEL.ingot(), 16), new OreDictStack(TI.plate(), 16), new ComparableStack(Blocks.glass, 4), new ComparableStack(ModItems.circuit_gold, 1), },400);
		makeRecipe(new ComparableStack(ModBlocks.machine_fluidtank, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 2), new OreDictStack(STEEL.plate(), 6), new ComparableStack(ModItems.hull_big_steel, 4), new OreDictStack(ANY_TAR.any(), 4), },150);
		makeRecipe(new ComparableStack(ModBlocks.machine_bat9000, 1), new AStack[] {new OreDictStack(STEEL.plate(), 16), new OreDictStack(TCALLOY.ingot(), 16), new ComparableStack(ModBlocks.steel_scaffold, 16), new OreDictStack(ANY_TAR.any(), 16), },150);
		makeRecipe(new ComparableStack(ModBlocks.machine_orbus, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 12), new OreDictStack(TCALLOY.ingot(), 12), new OreDictStack(BIGMT.plate(), 12), new ComparableStack(ModItems.coil_advanced_alloy, 12), new ComparableStack(ModItems.battery_sc_polonium, 1) }, 200);
		makeRecipe(new ComparableStack(ModBlocks.machine_drill, 1), new AStack[] {new ComparableStack(ModBlocks.steel_scaffold, 6), new OreDictStack(STEEL.ingot(), 4), new ComparableStack(ModItems.wire_red_copper, 4), new ComparableStack(ModItems.circuit_copper, 1), new ComparableStack(ModItems.motor, 1), new OreDictStack(DURA.ingot(), 2), new ComparableStack(ModItems.bolt_dura_steel, 2), new ComparableStack(ModItems.drill_titanium, 1), },200);
		makeRecipe(new ComparableStack(ModBlocks.machine_mining_laser, 1), new AStack[] {new ComparableStack(ModItems.tank_steel, 3), new OreDictStack(STEEL.plate(), 16), new ComparableStack(ModItems.crystal_redstone, 3), new ComparableStack(Items.diamond, 3), new OreDictStack(ANY_PLASTIC.ingot(), 4), new ComparableStack(ModItems.motor, 3), new OreDictStack(DURA.ingot(), 4), new ComparableStack(ModItems.bolt_dura_steel, 6), new ComparableStack(ModBlocks.machine_battery, 3), },400);
		makeRecipe(new ComparableStack(ModBlocks.machine_turbofan, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 1), new ComparableStack(ModItems.hull_big_titanium, 3), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.turbine_tungsten, 1), new ComparableStack(ModItems.turbine_titanium, 7), new ComparableStack(ModItems.bolt_compound, 8), new OreDictStack(MINGRADE.ingot(), 12), new ComparableStack(ModItems.wire_red_copper, 24), },500);
		makeRecipe(new ComparableStack(ModBlocks.machine_teleporter, 1), new AStack[] {new OreDictStack(TI.ingot(), 6), new OreDictStack(ALLOY.plate(), 12), new OreDictStack(CMB.plate(), 4), new ComparableStack(ModItems.telepad, 1), new ComparableStack(ModItems.entanglement_kit, 1), new ComparableStack(ModBlocks.machine_battery, 2), new ComparableStack(ModItems.coil_magnetized_tungsten, 4), },300);
		makeRecipe(new ComparableStack(ModBlocks.machine_schrabidium_transmutator, 1), new AStack[] {new OreDictStack(MAGTUNG.ingot(), 1), new OreDictStack(TI.ingot(), 24), new OreDictStack(ALLOY.plate(), 18), new OreDictStack(STEEL.plate(), 12), new ComparableStack(ModItems.plate_desh, 6), new OreDictStack(RUBBER.ingot(), 8), new ComparableStack(ModBlocks.machine_battery, 5), new ComparableStack(ModItems.circuit_gold, 5), },500);
		makeRecipe(new ComparableStack(ModBlocks.machine_combine_factory, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 8), new OreDictStack(ANY_PLASTIC.ingot(), 6), new OreDictStack(TI.plate(), 4), new OreDictStack(CU.plate(), 6), new ComparableStack(ModItems.circuit_gold, 6), new ComparableStack(ModItems.coil_advanced_alloy, 8), new ComparableStack(ModItems.coil_tungsten, 4), new OreDictStack(MAGTUNG.ingot(), 12), },150);
		makeRecipe(new ComparableStack(ModBlocks.factory_advanced_hull, 1), new AStack[] {new OreDictStack(ALLOY.ingot(), 4), new OreDictStack(ALLOY.plate(), 4), new ComparableStack(ModItems.wire_advanced_alloy, 6), },50);
		makeRecipe(new ComparableStack(ModBlocks.factory_advanced_furnace, 1), new AStack[] {new OreDictStack(ALLOY.ingot(), 4), new OreDictStack(ALLOY.plate(), 4), new OreDictStack(STEEL.plate(), 8), new ComparableStack(ModItems.coil_advanced_alloy, 2), },100);
		makeRecipe(new ComparableStack(ModBlocks.factory_advanced_core, 1), new AStack[] {new OreDictStack(ALLOY.ingot(), 6), new OreDictStack(ALLOY.plate(), 6), new OreDictStack(STEEL.plate(), 8), new ComparableStack(ModItems.coil_advanced_alloy, 2), new ComparableStack(ModItems.motor, 16), new ComparableStack(Blocks.piston, 6), },100);
		makeRecipe(new ComparableStack(ModBlocks.factory_advanced_conductor, 1), new AStack[] {new OreDictStack(ALLOY.ingot(), 8), new OreDictStack(ALLOY.plate(), 6), new ComparableStack(ModItems.wire_advanced_alloy, 4), new ComparableStack(ModItems.fuse, 6), },50);
		makeRecipe(new ComparableStack(ModBlocks.fusion_conductor, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 2), new ComparableStack(ModItems.coil_advanced_alloy, 5), },150);
		makeRecipe(new ComparableStack(ModBlocks.fusion_center, 1), new AStack[] {new OreDictStack(W.ingot(), 4), new OreDictStack(STEEL.plate(), 6), new ComparableStack(ModItems.wire_advanced_alloy, 24), },200);
		makeRecipe(new ComparableStack(ModBlocks.fusion_motor, 1), new AStack[] {new OreDictStack(TI.ingot(), 4), new OreDictStack(STEEL.ingot(), 2), new ComparableStack(ModItems.motor, 4), },250);
		makeRecipe(new ComparableStack(ModBlocks.fusion_heater, 1), new AStack[] {new OreDictStack(W.ingot(), 4), new OreDictStack(STEEL.plate(), 2), new OreDictStack(OreDictManager.getReflector(), 2), new OreDictStack(CU.plate(), 4), new ComparableStack(ModItems.magnetron, 1), new ComparableStack(ModItems.wire_advanced_alloy, 4), },150);
		makeRecipe(new ComparableStack(ModBlocks.watz_element, 1), new AStack[] {new OreDictStack(W.ingot(), 4), new OreDictStack(ALLOY.plate(), 4), new ComparableStack(ModItems.rod_empty, 2), new ComparableStack(ModItems.wire_magnetized_tungsten, 2), new ComparableStack(ModItems.wire_advanced_alloy, 4), },200);
		makeRecipe(new ComparableStack(ModBlocks.watz_control, 1), new AStack[] {new OreDictStack(W.ingot(), 4), new OreDictStack(ALLOY.ingot(), 4), new OreDictStack(PB.ingot(), 2), new ComparableStack(ModItems.wire_magnetized_tungsten, 4), new ComparableStack(ModItems.wire_advanced_alloy, 2), },250);
		makeRecipe(new ComparableStack(ModBlocks.watz_cooler, 1), new AStack[] {new OreDictStack(W.ingot(), 2), new OreDictStack(STEEL.ingot(), 2), new OreDictStack(KNO.dust(), 4), },300);
		makeRecipe(new ComparableStack(ModBlocks.watz_end, 1), new AStack[] {new OreDictStack(W.ingot(), 2), new OreDictStack(PB.ingot(), 2), new OreDictStack(STEEL.ingot(), 3), },150);
		makeRecipe(new ComparableStack(ModBlocks.watz_hatch, 1), new AStack[] {new ComparableStack(ModBlocks.reinforced_brick, 1), new OreDictStack(TI.plate(), 6), },200);
		makeRecipe(new ComparableStack(ModBlocks.watz_conductor, 1), new AStack[] {new OreDictStack(W.ingot(), 2), new OreDictStack(PB.ingot(), 2), new OreDictStack(STEEL.ingot(), 2), new ComparableStack(ModItems.wire_red_copper, 6), new ComparableStack(ModItems.wire_magnetized_tungsten, 2), new ComparableStack(ModItems.fuse, 4), },250);
		makeRecipe(new ComparableStack(ModBlocks.watz_core, 1), new AStack[] {new ComparableStack(ModBlocks.block_meteor, 1), new ComparableStack(ModItems.circuit_gold, 5), new ComparableStack(ModItems.circuit_schrabidium, 2), new ComparableStack(ModItems.wire_magnetized_tungsten, 12), },350);
		makeRecipe(new ComparableStack(ModBlocks.fwatz_hatch, 1), new AStack[] {new OreDictStack(W.ingot(), 6), new OreDictStack(CMB.plate(), 4), },250);
		makeRecipe(new ComparableStack(ModBlocks.fwatz_conductor, 1), new AStack[] {new OreDictStack(CMB.plate(), 2), new ComparableStack(ModItems.coil_magnetized_tungsten, 5), },250);
		makeRecipe(new ComparableStack(ModBlocks.fwatz_computer, 1), new AStack[] {new ComparableStack(ModBlocks.block_meteor, 1), new ComparableStack(ModItems.wire_magnetized_tungsten, 16), new OreDictStack(DIAMOND.dust(), 6), new OreDictStack(MAGTUNG.dust(), 6), new OreDictStack(DESH.dust(), 4), },300);
		makeRecipe(new ComparableStack(ModBlocks.fwatz_core, 1), new AStack[] {new ComparableStack(ModBlocks.block_meteor, 1), new ComparableStack(ModItems.wire_magnetized_tungsten, 24), new OreDictStack(DIAMOND.dust(), 8), new OreDictStack(MAGTUNG.dust(), 12), new OreDictStack(DESH.dust(), 8), new ComparableStack(ModItems.upgrade_power_3, 1), new ComparableStack(ModItems.upgrade_speed_3, 1), new OreDictStack(KEY_CIRCUIT_BISMUTH, 8)},450);
		makeRecipe(new ComparableStack(ModBlocks.nuke_gadget, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.fins_flat, 2), new ComparableStack(ModItems.pedestal_steel, 1), new ComparableStack(ModItems.circuit_targeting_tier3, 1), new ComparableStack(ModItems.wire_gold, 6), new OreDictStack("dyeGray", 6), },300);
		makeRecipe(new ComparableStack(ModBlocks.nuke_boy, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.fins_small_steel, 1), new ComparableStack(ModItems.circuit_targeting_tier2, 1), new ComparableStack(ModItems.wire_aluminium, 6), new OreDictStack("dyeBlue", 4), },300);
		makeRecipe(new ComparableStack(ModBlocks.nuke_man, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.hull_big_steel, 2), new ComparableStack(ModItems.fins_big_steel, 1), new ComparableStack(ModItems.circuit_targeting_tier2, 2), new ComparableStack(ModItems.wire_copper, 6), new OreDictStack("dyeYellow", 6), },300);
		makeRecipe(new ComparableStack(ModBlocks.nuke_mike, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.hull_big_aluminium, 4), new ComparableStack(ModItems.cap_aluminium, 1), new ComparableStack(ModItems.circuit_targeting_tier4, 3), new ComparableStack(ModItems.wire_gold, 18), new OreDictStack("dyeLightGray", 12), },300);
		makeRecipe(new ComparableStack(ModBlocks.nuke_tsar, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.hull_big_titanium, 6), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.fins_tri_steel, 1), new ComparableStack(ModItems.circuit_targeting_tier4, 5), new ComparableStack(ModItems.wire_gold, 24), new ComparableStack(ModItems.wire_tungsten, 12), new OreDictStack("dyeBlack", 6), },600);
		makeRecipe(new ComparableStack(ModBlocks.nuke_prototype, 1), new AStack[] {new ComparableStack(ModItems.dysfunctional_reactor, 1), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.ingot_euphemium, 3), new ComparableStack(ModItems.circuit_targeting_tier5, 1), new ComparableStack(ModItems.wire_gold, 16), },500);
		makeRecipe(new ComparableStack(ModBlocks.nuke_fleija, 1), new AStack[] {new ComparableStack(ModItems.hull_small_aluminium, 1), new ComparableStack(ModItems.fins_quad_titanium, 1), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.wire_gold, 8), new OreDictStack("dyeWhite", 4), },400);
		makeRecipe(new ComparableStack(ModBlocks.nuke_solinium, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.fins_quad_titanium, 1), new ComparableStack(ModItems.circuit_targeting_tier4, 3), new ComparableStack(ModItems.wire_gold, 10), new ComparableStack(ModItems.pipes_steel, 4), new OreDictStack("dyeGray", 4), },400);
		makeRecipe(new ComparableStack(ModBlocks.nuke_n2, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 3), new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.wire_magnetized_tungsten, 12), new ComparableStack(ModItems.pipes_steel, 6), new ComparableStack(ModItems.circuit_targeting_tier4, 3), new OreDictStack("dyeBlack", 12), },300);
		makeRecipe(new ComparableStack(ModBlocks.nuke_fstbmb, 1), new AStack[] {new ComparableStack(ModItems.sphere_steel, 1), new ComparableStack(ModItems.hull_big_titanium, 6), new ComparableStack(ModItems.fins_big_steel, 1), new ComparableStack(ModItems.powder_magic, 8), new ComparableStack(ModItems.wire_gold, 12), new ComparableStack(ModItems.circuit_targeting_tier4, 4), new OreDictStack("dyeGray", 6), },600);
		makeRecipe(new ComparableStack(ModBlocks.nuke_custom, 1), new AStack[] {new ComparableStack(ModItems.hull_small_steel, 2), new ComparableStack(ModItems.fins_small_steel, 1), new ComparableStack(ModItems.circuit_gold, 1), new ComparableStack(ModItems.wire_gold, 12), new OreDictStack("dyeGray", 4), },300);
		makeRecipe(new ComparableStack(ModBlocks.float_bomb, 1), new AStack[] {new OreDictStack(TI.plate(), 12), new ComparableStack(ModItems.levitation_unit, 1), new ComparableStack(ModItems.circuit_gold, 4), new ComparableStack(ModItems.wire_gold, 6), },250);
		makeRecipe(new ComparableStack(ModBlocks.therm_endo, 1), new AStack[] {new OreDictStack(TI.plate(), 12), new ComparableStack(ModItems.thermo_unit_endo, 1), new ComparableStack(ModItems.circuit_gold, 2), new ComparableStack(ModItems.wire_gold, 6), },250);
		makeRecipe(new ComparableStack(ModBlocks.therm_exo, 1), new AStack[] {new OreDictStack(TI.plate(), 12), new ComparableStack(ModItems.thermo_unit_exo, 1), new ComparableStack(ModItems.circuit_gold, 2), new ComparableStack(ModItems.wire_gold, 6), },250);
		makeRecipe(new ComparableStack(ModBlocks.launch_pad, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 4), new OreDictStack(ANY_PLASTIC.ingot(), 2), new OreDictStack(STEEL.plate(), 12), new ComparableStack(ModBlocks.machine_battery, 1), new ComparableStack(ModItems.circuit_gold, 2), },250);
		makeRecipe(new ComparableStack(ModItems.spawn_chopper, 1), new AStack[] {new ComparableStack(ModItems.chopper_blades, 5), new ComparableStack(ModItems.chopper_gun, 1), new ComparableStack(ModItems.chopper_head, 1), new ComparableStack(ModItems.chopper_tail, 1), new ComparableStack(ModItems.chopper_torso, 1), new ComparableStack(ModItems.chopper_wing, 2), },300);
		//makeRecipe(new ComparableStack(ModBlocks.turret_light, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 6), new ComparableStack(ModItems.pipes_steel, 2), new OreDictStack(MINGRADE.ingot(), 2), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.circuit_targeting_tier2, 2), },200);
		//makeRecipe(new ComparableStack(ModBlocks.turret_heavy, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 8), new OreDictStack(AL.ingot(), 4), new ComparableStack(ModItems.pipes_steel, 2), new ComparableStack(ModItems.hull_small_steel, 1), new OreDictStack(MINGRADE.ingot(), 4), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.circuit_targeting_tier2, 3), },250);
		//makeRecipe(new ComparableStack(ModBlocks.turret_rocket, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 12), new OreDictStack(TI.ingot(), 4), new ComparableStack(ModItems.hull_small_steel, 8), new OreDictStack(MINGRADE.ingot(), 6), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.circuit_targeting_tier3, 2), },300);
		//makeRecipe(new ComparableStack(ModBlocks.turret_flamer, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 8), new OreDictStack(W.ingot(), 2), new ComparableStack(ModItems.pipes_steel, 1), new ComparableStack(ModItems.tank_steel, 2), new OreDictStack(MINGRADE.ingot(), 4), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.circuit_targeting_tier3, 2), },250);
		//makeRecipe(new ComparableStack(ModBlocks.turret_tau, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 16), new OreDictStack(TI.ingot(), 8), new OreDictStack(ALLOY.plate(), 4), new ComparableStack(ModItems.redcoil_capacitor, 3), new OreDictStack(MINGRADE.ingot(), 12), new ComparableStack(ModItems.motor, 2), new ComparableStack(ModItems.circuit_targeting_tier4, 2), },350);
		makeRecipe(new ComparableStack(ModBlocks.turret_spitfire, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 6), new OreDictStack(MINGRADE.ingot(), 6), new OreDictStack(STEEL.plate(), 16), new OreDictStack(IRON.plate(), 8), new ComparableStack(ModItems.hull_small_steel, 4), new ComparableStack(ModItems.pipes_steel, 2), new ComparableStack(ModItems.motor, 3), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		//makeRecipe(new ComparableStack(ModBlocks.turret_cwis, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 6), new OreDictStack(MINGRADE.ingot(), 8), new OreDictStack(STEEL.plate(), 10), new OreDictStack(TI.plate(), 4), new ComparableStack(ModItems.hull_small_aluminium, 2), new ComparableStack(ModItems.pipes_steel, 6), new ComparableStack(ModItems.motor, 4), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.magnetron, 3), },400);
		makeRecipe(new ComparableStack(ModBlocks.turret_cheapo, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 4), new OreDictStack(IRON.plate(), 4), new ComparableStack(ModItems.pipes_steel, 1), new ComparableStack(ModItems.motor, 3), new ComparableStack(ModItems.circuit_targeting_tier1, 4), },200);
		makeRecipe(new ComparableStack(ModItems.missile_generic, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_small, 1), new ComparableStack(ModItems.fuel_tank_small, 1), new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(TI.plate(), 6), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		makeRecipe(new ComparableStack(ModItems.missile_incendiary, 1), new AStack[] {new ComparableStack(ModItems.warhead_incendiary_small, 1), new ComparableStack(ModItems.fuel_tank_small, 1), new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(TI.plate(), 6), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		makeRecipe(new ComparableStack(ModItems.missile_cluster, 1), new AStack[] {new ComparableStack(ModItems.warhead_cluster_small, 1), new ComparableStack(ModItems.fuel_tank_small, 1), new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(TI.plate(), 6), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		makeRecipe(new ComparableStack(ModItems.missile_buster, 1), new AStack[] {new ComparableStack(ModItems.warhead_buster_small, 1), new ComparableStack(ModItems.fuel_tank_small, 1), new ComparableStack(ModItems.thruster_small, 1), new OreDictStack(TI.plate(), 6), new ComparableStack(ModItems.circuit_targeting_tier1, 1), },200);
		makeRecipe(new ComparableStack(ModItems.missile_strong, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_medium, 1), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(TI.plate(), 10), new OreDictStack(STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		makeRecipe(new ComparableStack(ModItems.missile_incendiary_strong, 1), new AStack[] {new ComparableStack(ModItems.warhead_incendiary_medium, 1), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(TI.plate(), 10), new OreDictStack(STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		makeRecipe(new ComparableStack(ModItems.missile_cluster_strong, 1), new AStack[] {new ComparableStack(ModItems.warhead_cluster_medium, 1), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(TI.plate(), 10), new OreDictStack(STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		makeRecipe(new ComparableStack(ModItems.missile_buster_strong, 1), new AStack[] {new ComparableStack(ModItems.warhead_buster_medium, 1), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(TI.plate(), 10), new OreDictStack(STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		makeRecipe(new ComparableStack(ModItems.missile_emp_strong, 1), new AStack[] {new ComparableStack(ModBlocks.emp_bomb, 3), new ComparableStack(ModItems.fuel_tank_medium, 1), new ComparableStack(ModItems.thruster_medium, 1), new OreDictStack(TI.plate(), 10), new OreDictStack(STEEL.plate(), 14), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },250);
		makeRecipe(new ComparableStack(ModItems.missile_burst, 1), new AStack[] {new ComparableStack(ModItems.warhead_generic_large, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 14), new OreDictStack(STEEL.plate(), 20), new OreDictStack(AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		makeRecipe(new ComparableStack(ModItems.missile_inferno, 1), new AStack[] {new ComparableStack(ModItems.warhead_incendiary_large, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 14), new OreDictStack(STEEL.plate(), 20), new OreDictStack(AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		makeRecipe(new ComparableStack(ModItems.missile_rain, 1), new AStack[] {new ComparableStack(ModItems.warhead_cluster_large, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 14), new OreDictStack(STEEL.plate(), 20), new OreDictStack(AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		makeRecipe(new ComparableStack(ModItems.missile_drill, 1), new AStack[] {new ComparableStack(ModItems.warhead_buster_large, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 14), new OreDictStack(STEEL.plate(), 20), new OreDictStack(AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },350);
		makeRecipe(new ComparableStack(ModItems.missile_nuclear, 1), new AStack[] {new ComparableStack(ModItems.warhead_nuclear, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 20), new OreDictStack(STEEL.plate(), 24), new OreDictStack(AL.plate(), 16), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },500);
		makeRecipe(new ComparableStack(ModItems.missile_nuclear_cluster, 1), new AStack[] {new ComparableStack(ModItems.warhead_mirv, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 20), new OreDictStack(STEEL.plate(), 24), new OreDictStack(AL.plate(), 16), new ComparableStack(ModItems.circuit_targeting_tier5, 1), },600);
		makeRecipe(new ComparableStack(ModItems.missile_volcano, 1), new AStack[] {new ComparableStack(ModItems.warhead_volcano, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 20), new OreDictStack(STEEL.plate(), 24), new OreDictStack(AL.plate(), 16), new ComparableStack(ModItems.circuit_targeting_tier5, 1), },600);
		makeRecipe(new ComparableStack(ModItems.missile_endo, 1), new AStack[] {new ComparableStack(ModItems.warhead_thermo_endo, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 14), new OreDictStack(STEEL.plate(), 20), new OreDictStack(AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },350);
		makeRecipe(new ComparableStack(ModItems.missile_exo, 1), new AStack[] {new ComparableStack(ModItems.warhead_thermo_exo, 1), new ComparableStack(ModItems.fuel_tank_large, 1), new ComparableStack(ModItems.thruster_large, 1), new OreDictStack(TI.plate(), 14), new OreDictStack(STEEL.plate(), 20), new OreDictStack(AL.plate(), 12), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },350);
		makeRecipe(new ComparableStack(ModItems.gun_defabricator, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 2), new OreDictStack(ANY_PLASTIC.ingot(), 8), new OreDictStack(IRON.plate(), 5), new ComparableStack(ModItems.mechanism_special, 3), new ComparableStack(Items.diamond, 1), new ComparableStack(ModItems.plate_dalekanium, 3), },200);
		makeRecipe(new ComparableStack(ModItems.gun_osipr_ammo, 24), new AStack[] {new OreDictStack(STEEL.plate(), 2), new OreDictStack(REDSTONE.dust(), 1), new ComparableStack(Items.glowstone_dust, 1), },50);
		makeRecipe(new ComparableStack(ModItems.gun_osipr_ammo2, 1), new AStack[] {new OreDictStack(CMB.plate(), 4), new OreDictStack(REDSTONE.dust(), 7), new ComparableStack(ModItems.powder_power, 3), },200);
		makeRecipe(new ComparableStack(ModItems.grenade_fire, 1), new AStack[] {new ComparableStack(ModItems.grenade_frag, 1), new OreDictStack(P_RED.dust(), 1), new OreDictStack(CU.plate(), 2), },150);
		makeRecipe(new ComparableStack(ModItems.grenade_shrapnel, 1), new AStack[] {new ComparableStack(ModItems.grenade_frag, 1), new ComparableStack(ModItems.pellet_buckshot, 1), new OreDictStack(STEEL.plate(), 2), },150);
		makeRecipe(new ComparableStack(ModItems.grenade_cluster, 1), new AStack[] {new ComparableStack(ModItems.grenade_frag, 1), new ComparableStack(ModItems.pellet_cluster, 1), new OreDictStack(STEEL.plate(), 2), },200);
		makeRecipe(new ComparableStack(ModItems.grenade_flare, 1), new AStack[] {new ComparableStack(ModItems.grenade_generic, 1), new ComparableStack(Items.glowstone_dust, 1), new OreDictStack(AL.plate(), 2), },100);
		makeRecipe(new ComparableStack(ModItems.grenade_electric, 1), new AStack[] {new ComparableStack(ModItems.grenade_generic, 1), new ComparableStack(ModItems.circuit_red_copper, 1), new OreDictStack(GOLD.plate(), 2), },200);
		makeRecipe(new ComparableStack(ModItems.grenade_pulse, 4), new AStack[] {new OreDictStack(STEEL.plate(), 1), new OreDictStack(IRON.plate(), 3), new ComparableStack(ModItems.wire_red_copper, 6), new ComparableStack(Items.diamond, 1), },300);
		makeRecipe(new ComparableStack(ModItems.grenade_plasma, 2), new AStack[] {new OreDictStack(STEEL.plate(), 3), new OreDictStack(ALLOY.plate(), 1), new ComparableStack(ModItems.coil_advanced_torus, 1), new ComparableStack(ModItems.cell_deuterium, 1), new ComparableStack(ModItems.cell_tritium, 1), },300);
		makeRecipe(new ComparableStack(ModItems.grenade_tau, 2), new AStack[] {new OreDictStack(PB.plate(), 3), new OreDictStack(ALLOY.plate(), 1), new ComparableStack(ModItems.coil_advanced_torus, 1), new ComparableStack(ModItems.gun_xvl1456_ammo, 1), },300);
		makeRecipe(new ComparableStack(ModItems.grenade_schrabidium, 1), new AStack[] {new ComparableStack(ModItems.grenade_flare, 1), new OreDictStack(SA326.dust(), 1), new OreDictStack(OreDictManager.getReflector(), 2), },300);
		makeRecipe(new ComparableStack(ModItems.grenade_nuclear, 1), new AStack[] {new OreDictStack(IRON.plate(), 1), new OreDictStack(STEEL.plate(), 1), new OreDictStack(PU239.nugget(), 2), new ComparableStack(ModItems.wire_red_copper, 2), },200);
		makeRecipe(new ComparableStack(ModItems.grenade_zomg, 1), new AStack[] {new ComparableStack(ModItems.plate_paa, 3), new OreDictStack(OreDictManager.getReflector(), 1), new ComparableStack(ModItems.coil_magnetized_tungsten, 3), new ComparableStack(ModItems.powder_power, 3), },300);
		makeRecipe(new ComparableStack(ModItems.grenade_black_hole, 1), new AStack[] {new OreDictStack(ANY_PLASTIC.ingot(), 6), new OreDictStack(OreDictManager.getReflector(), 3), new ComparableStack(ModItems.coil_magnetized_tungsten, 2), new ComparableStack(ModItems.black_hole, 1), },500);
		makeRecipe(new ComparableStack(ModItems.gadget_explosive, 1), new AStack[] {new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 4), new OreDictStack(STEEL.plate(), 2), new OreDictStack(AL.plate(), 3), new ComparableStack(ModItems.wire_gold, 3), },200);
		makeRecipe(new ComparableStack(ModItems.gadget_wireing, 1), new AStack[] {new OreDictStack(IRON.plate(), 1), new ComparableStack(ModItems.wire_gold, 12), },100);
		makeRecipe(new ComparableStack(ModItems.gadget_core, 1), new AStack[] {new OreDictStack(PU239.nugget(), 7), new OreDictStack(U238.nugget(), 3), },200);
		makeRecipe(new ComparableStack(ModItems.boy_shielding, 1), new AStack[] {new OreDictStack(OreDictManager.getReflector(), 12), new OreDictStack(STEEL.plate(), 4), },150);
		makeRecipe(new ComparableStack(ModItems.boy_target, 1), new AStack[] {new OreDictStack(U235.nugget(), 18), },200);
		makeRecipe(new ComparableStack(ModItems.boy_bullet, 1), new AStack[] {new OreDictStack(U235.nugget(), 9), },100);
		makeRecipe(new ComparableStack(ModItems.boy_propellant, 1), new AStack[] {new ComparableStack(ModItems.cordite, 8), new OreDictStack(IRON.plate(), 8), new OreDictStack(AL.plate(), 4), new ComparableStack(ModItems.wire_red_copper, 4), },100); 
		makeRecipe(new ComparableStack(ModItems.boy_igniter, 1), new AStack[] {new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 1), new OreDictStack(AL.plate(), 6), new OreDictStack(STEEL.plate(), 1), new ComparableStack(ModItems.circuit_red_copper, 1), new ComparableStack(ModItems.wire_red_copper, 3), },150); //HE for gating purposes
		makeRecipe(new ComparableStack(ModItems.man_explosive, 1), new AStack[] {new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 2), new OreDictStack(ANY_PLASTICEXPLOSIVE.ingot(), 1), new OreDictStack(STEEL.plate(), 2), new OreDictStack(TI.plate(), 1), new ComparableStack(ModItems.wire_red_copper, 3), },200);
		makeRecipe(new ComparableStack(ModItems.man_igniter, 1), new AStack[] {new OreDictStack(STEEL.plate(), 6), new ComparableStack(ModItems.circuit_red_copper, 1), new ComparableStack(ModItems.wire_red_copper, 9), },150);
		makeRecipe(new ComparableStack(ModItems.man_core, 1), new AStack[] {new OreDictStack(PU239.nugget(), 8), new OreDictStack(BE.nugget(), 2), },250);
		makeRecipe(new ComparableStack(ModItems.mike_core, 1), new AStack[] {new OreDictStack(U238.nugget(), 24), new OreDictStack(PB.ingot(), 6), },250);
		makeRecipe(new ComparableStack(ModItems.mike_deut, 1), new AStack[] {new OreDictStack(IRON.plate(), 12), new OreDictStack(STEEL.plate(), 16), new ComparableStack(ModItems.cell_deuterium, 10), },200);
		makeRecipe(new ComparableStack(ModItems.mike_cooling_unit, 1), new AStack[] {new OreDictStack(IRON.plate(), 8), new ComparableStack(ModItems.coil_copper, 5), new ComparableStack(ModItems.coil_tungsten, 5), new ComparableStack(ModItems.motor, 2), },200);
		makeRecipe(new ComparableStack(ModItems.fleija_igniter, 1), new AStack[] {new OreDictStack(TI.plate(), 6), new ComparableStack(ModItems.wire_schrabidium, 2), new ComparableStack(ModItems.circuit_schrabidium, 1), },300);
		makeRecipe(new ComparableStack(ModItems.fleija_core, 1), new AStack[] {new OreDictStack(U235.nugget(), 8), new OreDictStack(NP237.nugget(), 2), new OreDictStack(BE.nugget(), 4), new ComparableStack(ModItems.coil_copper, 2), },500);
		makeRecipe(new ComparableStack(ModItems.fleija_propellant, 1), new AStack[] {new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 3), new OreDictStack(SA326.plate(), 8), },400);
		makeRecipe(new ComparableStack(ModItems.solinium_igniter, 1), new AStack[] {new OreDictStack(TI.plate(), 4), new ComparableStack(ModItems.wire_advanced_alloy, 2), new ComparableStack(ModItems.circuit_schrabidium, 1), new ComparableStack(ModItems.coil_gold, 1), },400);
		makeRecipe(new ComparableStack(ModItems.solinium_core, 1), new AStack[] {new OreDictStack(SA327.nugget(), 9), new OreDictStack(EUPH.nugget(), 1), },400);
		makeRecipe(new ComparableStack(ModItems.solinium_propellant, 1), new AStack[] {new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 3), new OreDictStack(OreDictManager.getReflector(), 2), new ComparableStack(ModItems.plate_polymer, 6), new ComparableStack(ModItems.wire_tungsten, 6), new ComparableStack(ModItems.biomass_compressed, 4), },350);
		makeRecipe(new ComparableStack(ModItems.schrabidium_hammer, 1), new AStack[] {new OreDictStack(SA326.block(), 35), new ComparableStack(ModItems.billet_yharonite, 128), new ComparableStack(Items.nether_star, 3), new ComparableStack(ModItems.fragment_meteorite, 512), },1000);
		makeRecipe(new ComparableStack(ModItems.component_limiter, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 2), new OreDictStack(STEEL.plate(), 32), new OreDictStack(TI.plate(), 18), new ComparableStack(ModItems.plate_desh, 12), new ComparableStack(ModItems.pipes_steel, 4), new ComparableStack(ModItems.circuit_gold, 8), new ComparableStack(ModItems.circuit_schrabidium, 4), new OreDictStack(STAR.ingot(), 14), new ComparableStack(ModItems.plate_dalekanium, 5), new ComparableStack(ModItems.powder_magic, 16), new ComparableStack(ModBlocks.fwatz_computer, 3), },2500);
		makeRecipe(new ComparableStack(ModItems.component_emitter, 1), new AStack[] {new ComparableStack(ModItems.hull_big_steel, 3), new ComparableStack(ModItems.hull_big_titanium, 2), new OreDictStack(STEEL.plate(), 32), new OreDictStack(PB.plate(), 24), new ComparableStack(ModItems.plate_desh, 24), new ComparableStack(ModItems.pipes_steel, 8), new ComparableStack(ModItems.circuit_gold, 12), new ComparableStack(ModItems.circuit_schrabidium, 8), new OreDictStack(STAR.ingot(), 26), new ComparableStack(ModItems.powder_magic, 48), new ComparableStack(ModBlocks.fwatz_computer, 2), new ComparableStack(ModItems.crystal_xen, 1), },2500);
		makeRecipe(new ComparableStack(ModBlocks.ams_limiter, 1), new AStack[] {new ComparableStack(ModItems.board_copper, 6), new OreDictStack(STEEL.plate(), 24), new ComparableStack(ModBlocks.steel_scaffold, 20), new ComparableStack(ModItems.crystal_diamond, 1)}, 600);
		makeRecipe(new ComparableStack(ModBlocks.ams_emitter, 1), new AStack[] {new ComparableStack(ModItems.board_copper, 24), new OreDictStack(STEEL.plate(), 32), new ComparableStack(ModBlocks.steel_scaffold, 40), new ComparableStack(ModItems.crystal_redstone, 5), new ComparableStack(ModBlocks.machine_lithium_battery)}, 600);
		makeRecipe(new ComparableStack(ModBlocks.ams_base, 1), new AStack[] {new ComparableStack(ModItems.board_copper, 12), new OreDictStack(STEEL.plate(), 28), new ComparableStack(ModBlocks.steel_scaffold, 30), new ComparableStack(ModBlocks.steel_grate, 8), new ComparableStack(ModBlocks.barrel_steel, 2)}, 600);
		makeRecipe(new ComparableStack(ModBlocks.machine_radar, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 8), new OreDictStack(STEEL.plate(), 16), new OreDictStack(ANY_PLASTIC.ingot(), 4), new ComparableStack(ModItems.plate_polymer, 24), new ComparableStack(ModItems.magnetron, 10), new ComparableStack(ModItems.motor, 3), new ComparableStack(ModItems.circuit_gold, 4), new ComparableStack(ModItems.coil_copper, 12), new ComparableStack(ModItems.crt_display, 4), },300);
		makeRecipe(new ComparableStack(ModBlocks.machine_forcefield, 1), new AStack[] {new OreDictStack(ALLOY.plate(), 8), new ComparableStack(ModItems.plate_desh, 4), new ComparableStack(ModItems.coil_gold_torus, 6), new ComparableStack(ModItems.coil_magnetized_tungsten, 12), new ComparableStack(ModItems.motor, 1), new ComparableStack(ModItems.upgrade_radius, 1), new ComparableStack(ModItems.upgrade_health, 1), new ComparableStack(ModItems.circuit_targeting_tier5, 1), new ComparableStack(ModBlocks.machine_transformer, 1), },1000);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_10_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 4), new OreDictStack(STEEL.plate(), 4), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_10_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.coil_tungsten, 1), new OreDictStack(DURA.ingot(), 4), new OreDictStack(STEEL.plate(), 4), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_10_xenon, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(STEEL.plate(), 4), new ComparableStack(ModItems.pipes_steel, 2), new ComparableStack(ModItems.arc_electrode, 4), },100);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 8), new OreDictStack(STEEL.plate(), 6), new OreDictStack(DESH.ingot(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene_dual, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 4), new OreDictStack(STEEL.plate(), 6), new OreDictStack(DESH.ingot(), 1), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_kerosene_triple, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 6), new OreDictStack(STEEL.plate(), 6), new OreDictStack(DESH.ingot(), 2), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(STEEL.plate(), 6), new OreDictStack(DURA.ingot(), 6), new ComparableStack(ModItems.coil_tungsten, 3), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_solid_hexdecuple, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(STEEL.plate(), 6), new OreDictStack(DURA.ingot(), 12), new ComparableStack(ModItems.coil_tungsten, 6), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_hydrogen, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 8), new OreDictStack(STEEL.plate(), 6), new ComparableStack(ModItems.tank_steel, 1), new OreDictStack(DESH.ingot(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_hydrogen_dual, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 4), new OreDictStack(STEEL.plate(), 6), new ComparableStack(ModItems.tank_steel, 1), new OreDictStack(DESH.ingot(), 1), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire_short, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.plate_polymer, 8), new ComparableStack(ModBlocks.reactor_element, 1), new OreDictStack(DESH.ingot(), 8), new OreDictStack(BIGMT.plate(), 12), new ComparableStack(ModItems.board_copper, 2), new ComparableStack(ModItems.ingot_uranium_fuel, 4), new ComparableStack(ModItems.pipes_steel, 2), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.plate_polymer, 16), new ComparableStack(ModBlocks.reactor_element, 2), new OreDictStack(DESH.ingot(), 16), new OreDictStack(BIGMT.plate(), 24), new ComparableStack(ModItems.board_copper, 4), new ComparableStack(ModItems.ingot_uranium_fuel, 8), new ComparableStack(ModItems.pipes_steel, 2), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_15_balefire_large, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.plate_polymer, 16), new ComparableStack(ModBlocks.reactor_element, 2), new OreDictStack(DESH.ingot(), 24), new OreDictStack(BIGMT.plate(), 32), new ComparableStack(ModItems.board_copper, 4), new ComparableStack(ModItems.ingot_uranium_fuel, 8), new ComparableStack(ModItems.pipes_steel, 2), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 16), new OreDictStack(STEEL.plate(), 12), new OreDictStack(DESH.ingot(), 8), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene_dual, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 8), new OreDictStack(STEEL.plate(), 6), new OreDictStack(DESH.ingot(), 4), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_kerosene_triple, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.deco_pipe_quad, 1), new OreDictStack(W.ingot(), 12), new OreDictStack(STEEL.plate(), 8), new OreDictStack(DESH.ingot(), 6), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModItems.coil_tungsten, 8), new OreDictStack(DURA.ingot(), 16), new OreDictStack(STEEL.plate(), 12), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid_multi, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModItems.coil_tungsten, 12), new OreDictStack(DURA.ingot(), 18), new OreDictStack(STEEL.plate(), 12), },500);
		makeRecipe(new ComparableStack(ModItems.mp_thruster_20_solid_multier, 1), new AStack[] {new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModItems.coil_tungsten, 16), new OreDictStack(DURA.ingot(), 20), new OreDictStack(STEEL.plate(), 12), },500);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 3), new OreDictStack(TI.plate(), 12), new OreDictStack(STEEL.plate(), 3), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 3), new OreDictStack(TI.plate(), 12), new OreDictStack(AL.plate(), 3), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_xenon, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 3), new OreDictStack(TI.plate(), 12), new ComparableStack(ModItems.board_copper, 3), },100);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_long_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 6), new OreDictStack(TI.plate(), 24), new OreDictStack(STEEL.plate(), 6), },200);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_long_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 2), new ComparableStack(ModBlocks.steel_scaffold, 6), new OreDictStack(TI.plate(), 24), new OreDictStack(AL.plate(), 6), },200);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(TI.plate(), 36), new OreDictStack(STEEL.plate(), 9), },300);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(TI.plate(), 36), new OreDictStack(AL.plate(), 9), },300);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_hydrogen, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(TI.plate(), 36), new OreDictStack(IRON.plate(), 9), },300);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_balefire, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(TI.plate(), 36), new OreDictStack(BIGMT.plate(), 9), },300);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 2), new ComparableStack(ModBlocks.steel_scaffold, 12), new OreDictStack(TI.plate(), 48), new OreDictStack(STEEL.plate(), 12), },500);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 2), new ComparableStack(ModBlocks.steel_scaffold, 12), new OreDictStack(TI.plate(), 48), new OreDictStack(AL.plate(), 12), },500);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_hydrogen, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 2), new ComparableStack(ModBlocks.steel_scaffold, 12), new OreDictStack(TI.plate(), 48), new OreDictStack(IRON.plate(), 12), },500);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_10_15_balefire, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModBlocks.steel_scaffold, 9), new OreDictStack(TI.plate(), 36), new OreDictStack(BIGMT.plate(), 9), },500);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_20_kerosene, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.steel_scaffold, 16), new OreDictStack(TI.plate(), 64), new OreDictStack(STEEL.plate(), 16), },600);
		makeRecipe(new ComparableStack(ModItems.mp_fuselage_15_20_solid, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new ComparableStack(ModItems.seg_20, 1), new ComparableStack(ModBlocks.steel_scaffold, 16), new OreDictStack(TI.plate(), 64), new OreDictStack(AL.plate(), 16), },600);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_he, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(STEEL.plate(), 6), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 3), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_incendiary, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(TI.plate(), 4), new OreDictStack(P_RED.dust(), 3), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 2), new ComparableStack(ModItems.circuit_targeting_tier2, 1), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_buster, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(TI.plate(), 4), new ComparableStack(ModBlocks.det_charge, 1), new ComparableStack(ModBlocks.det_cord, 4), new ComparableStack(ModItems.board_copper, 4), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_nuclear, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(STEEL.plate(), 6), new OreDictStack(PU239.ingot(), 1), new OreDictStack(OreDictManager.getReflector(), 2), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 4), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },200);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_nuclear_large, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(STEEL.plate(), 8), new OreDictStack(AL.plate(), 4), new OreDictStack(PU239.ingot(), 2), new ComparableStack(ModBlocks.det_charge, 4), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },300);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_taint, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(STEEL.plate(), 12), new ComparableStack(ModBlocks.det_cord, 2), new ComparableStack(ModItems.powder_magic, 12), new ComparableStack(ModItems.bucket_mud, 1), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_10_cloud, 1), new AStack[] {new ComparableStack(ModItems.seg_10, 1), new OreDictStack(STEEL.plate(), 12), new ComparableStack(ModBlocks.det_cord, 2), new ComparableStack(ModItems.grenade_pink_cloud, 2), },100);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_he, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(STEEL.plate(), 16), new ComparableStack(ModBlocks.det_charge, 4), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },200);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_incendiary, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(STEEL.plate(), 16), new ComparableStack(ModBlocks.det_charge, 2), new OreDictStack(P_RED.dust(), 8), new ComparableStack(ModItems.circuit_targeting_tier3, 1), },200);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_nuclear, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(STEEL.plate(), 24), new OreDictStack(TI.plate(), 12), new OreDictStack(PU239.ingot(), 3), new ComparableStack(ModBlocks.det_charge, 6), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },500);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_n2, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(STEEL.plate(), 8), new OreDictStack(TI.plate(), 20), new ComparableStack(ModBlocks.det_charge, 24), new ComparableStack(Blocks.redstone_block, 12), new OreDictStack(MAGTUNG.dust(), 6), new ComparableStack(ModItems.circuit_targeting_tier4, 1), },400);
		makeRecipe(new ComparableStack(ModItems.mp_warhead_15_balefire, 1), new AStack[] {new ComparableStack(ModItems.seg_15, 1), new OreDictStack(OreDictManager.getReflector(), 16), new ComparableStack(ModItems.powder_magic, 6), new ComparableStack(ModItems.egg_balefire_shard, 4), new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 8), new ComparableStack(ModItems.circuit_targeting_tier4, 1), }, 60);
		makeRecipe(new ComparableStack(ModItems.missile_soyuz, 1), new AStack[] {new ComparableStack(ModItems.rocket_fuel, 40), new ComparableStack(ModBlocks.det_cord, 20), new ComparableStack(ModItems.thruster_medium, 12), new ComparableStack(ModItems.thruster_small, 12), new ComparableStack(ModItems.tank_steel, 10), new ComparableStack(ModItems.circuit_targeting_tier4, 2), new ComparableStack(ModItems.circuit_targeting_tier3, 8), new OreDictStack(RUBBER.ingot(), 64), new ComparableStack(ModItems.fins_small_steel, 4), new ComparableStack(ModItems.hull_big_titanium, 32), new ComparableStack(ModItems.hull_big_steel, 18), new OreDictStack(FIBER.ingot(), 64), },600);
		makeRecipe(new ComparableStack(ModItems.missile_soyuz_lander, 1), new AStack[] {new ComparableStack(ModItems.rocket_fuel, 10), new ComparableStack(ModItems.thruster_small, 3), new ComparableStack(ModItems.tank_steel, 2), new ComparableStack(ModItems.circuit_targeting_tier3, 4), new ComparableStack(ModItems.plate_polymer, 32), new ComparableStack(ModItems.hull_big_aluminium, 2), new ComparableStack(ModItems.sphere_steel, 1), new OreDictStack(FIBER.ingot(), 12), },600);
		makeRecipe(new ComparableStack(ModItems.fusion_shield_tungsten, 1), new AStack[] {new OreDictStack(W.block(), 32), new OreDictStack(OreDictManager.getReflector(), 96)}, 600);
		makeRecipe(new ComparableStack(ModItems.fusion_shield_desh, 1), new AStack[] {new OreDictStack(DESH.block(), 16), new OreDictStack(CO.block(), 16), new OreDictStack(BIGMT.plate(), 96)}, 600);
		makeRecipe(new ComparableStack(ModItems.fusion_shield_chlorophyte, 1), new AStack[] {new OreDictStack(W.block(), 16), new OreDictStack(DURA.block(), 16), new OreDictStack(OreDictManager.getReflector(), 48), new ComparableStack(ModItems.powder_chlorophyte, 48)}, 600);
		makeRecipe(new ComparableStack(ModBlocks.machine_fensu, 1), new AStack[] {
				new ComparableStack(ModItems.ingot_electronium, 32),
				new ComparableStack(ModBlocks.machine_dineutronium_battery, 16),
				new OreDictStack(STEEL.block(), 32),
				new OreDictStack(DURA.block(), 16),
				new OreDictStack(STAR.block(), 64),
				new ComparableStack(ModBlocks.machine_transformer_dnt, 8),
				new ComparableStack(ModItems.coil_magnetized_tungsten, 24),
				new ComparableStack(ModItems.powder_magic, 64),
				new ComparableStack(ModItems.plate_dineutronium, 24),
				new ComparableStack(ModItems.ingot_u238m2),
				new OreDictStack(FIBER.ingot(), 128)
			}, 1200);
		makeRecipe(new ComparableStack(ModBlocks.struct_iter_core, 1), new AStack[] {
				new OreDictStack(STEEL.ingot(), 6),
				new OreDictStack(W.ingot(), 6),
				new OreDictStack(OreDictManager.getReflector(), 12),
				new ComparableStack(ModItems.coil_advanced_alloy, 12),
				new OreDictStack(ANY_PLASTIC.ingot(), 8),
				new ComparableStack(ModItems.circuit_red_copper, 8),
				new OreDictStack(KEY_CIRCUIT_BISMUTH, 1)
			}, 600);
		makeRecipe(new ComparableStack(ModBlocks.machine_large_turbine, 1), new AStack[] {
				new ComparableStack(ModItems.hull_big_steel, 1),
				new OreDictStack(STEEL.plate(), 12),
				new ComparableStack(ModItems.turbine_titanium, 3),
				new ComparableStack(ModItems.generator_steel, 1),
				new ComparableStack(ModItems.bolt_compound, 3),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.circuit_aluminium, 1),
			}, 200);
		makeRecipe(new ComparableStack(ModBlocks.machine_chungus, 1), new AStack[] {
				new ComparableStack(ModItems.hull_big_steel, 6),
				new OreDictStack(STEEL.plate(), 32),
				new OreDictStack(TI.plate(), 12),
				new OreDictStack(TCALLOY.ingot(), 16),
				new ComparableStack(ModItems.turbine_tungsten, 5),
				new ComparableStack(ModItems.turbine_titanium, 3),
				new ComparableStack(ModItems.flywheel_beryllium, 1),
				new ComparableStack(ModItems.generator_steel, 10),
				new ComparableStack(ModItems.bolt_compound, 16),
				new ComparableStack(ModItems.pipes_steel, 3)
			}, 600);

		makeRecipe(new ComparableStack(ModItems.pellet_chlorophyte, 2), new AStack[] {
				new ComparableStack(ModItems.powder_chlorophyte, 1),
				new OreDictStack(PB.nugget(), 12),
			}, 50);
		makeRecipe(new ComparableStack(ModItems.pellet_mercury, 2), new AStack[] {
				new ComparableStack(ModItems.ingot_mercury, 1),
				new OreDictStack(PB.nugget(), 12),
			}, 50);
		makeRecipe(new ComparableStack(ModItems.pellet_meteorite, 2), new AStack[] {
				new ComparableStack(ModItems.powder_meteorite, 1),
				new OreDictStack(PB.nugget(), 12),
			}, 50);
		makeRecipe(new ComparableStack(ModItems.pellet_canister, 2), new AStack[] {
				new OreDictStack(IRON.ingot(), 3),
			}, 50);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_cyclotron, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_lithium_battery, 3),
				new ComparableStack(ModBlocks.hadron_coil_neodymium, 8),
				new ComparableStack(ModItems.wire_advanced_alloy, 96),
				new OreDictStack(STEEL.ingot(), 16),
				new OreDictStack(STEEL.plate(), 32),
				new OreDictStack(AL.plate(), 32),
				new OreDictStack(ANY_PLASTIC.ingot(), 24),
				new OreDictStack(RUBBER.ingot(), 24),
				new ComparableStack(ModItems.board_copper, 8),
				new ComparableStack(ModItems.circuit_red_copper, 8),
				new ComparableStack(ModItems.circuit_gold, 3),
			}, 600);
		
		makeRecipe(new ComparableStack(ModBlocks.reactor_zirnox, 1), new AStack[] {
				new ComparableStack(ModItems.hull_big_steel, 3),
				new ComparableStack(ModItems.hull_small_steel, 6),
				new ComparableStack(ModBlocks.steel_scaffold, 4),
				new ComparableStack(ModBlocks.steel_grate, 8),
				new ComparableStack(ModBlocks.concrete_smooth, 12),
				new ComparableStack(ModBlocks.deco_pipe_quad, 12),
				new ComparableStack(ModItems.motor, 4),
				new OreDictStack(B.ingot(), 2),
				new OreDictStack(PB.ingot(), 4),
				new OreDictStack(GRAPHITE.ingot(), 24),
				new ComparableStack(ModItems.circuit_copper, 4),
				new ComparableStack(ModItems.circuit_red_copper, 2),
			}, 600);

		makeRecipe(new ComparableStack(ModItems.gun_zomg, 1), new AStack[] {
				new ComparableStack(ModItems.crystal_xen, 2),
				new ComparableStack(ModItems.singularity_counter_resonant, 1),
				new ComparableStack(ModItems.mechanism_special, 3),
				new ComparableStack(ModItems.plate_paa, 12),
				new OreDictStack(OreDictManager.getReflector(), 8),
				new ComparableStack(ModItems.coil_magnetized_tungsten, 5),
				new ComparableStack(ModItems.powder_magic, 4),
				new OreDictStack(ASBESTOS.ingot(), 8)
			}, 200);

		/*makeRecipe(new ComparableStack(ModBlocks.machine_industrial_generator, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_coal_off, 2),
				new ComparableStack(ModBlocks.machine_boiler_off, 2),
				new ComparableStack(ModBlocks.machine_large_turbine, 1),
				new ComparableStack(ModBlocks.machine_transformer, 1),
				new ComparableStack(ModBlocks.steel_scaffold, 20),
				new OreDictStack(STEEL.ingot(), 12),
				new OreDictStack(PB.plate(), 8),
				new OreDictStack(AL.plate(), 12),
				new ComparableStack(ModItems.pipes_steel, 1)
			}, 200);*/

		makeRecipe(new ComparableStack(ModItems.ammo_75bolt, 2), new AStack[] {
				new OreDictStack(STEEL.plate(), 2),
				new OreDictStack(CU.plate(), 1),
				new ComparableStack(ModItems.primer_50, 5),
				new ComparableStack(ModItems.casing_50, 5),
				new OreDictStack(ANY_PLASTICEXPLOSIVE.ingot(), 2),
				new ComparableStack(ModItems.cordite, 3),
				new OreDictStack(U238.ingot(), 1)
			}, 60);

		makeRecipe(new ComparableStack(ModItems.ammo_75bolt_incendiary, 2), new AStack[] {
				new OreDictStack(STEEL.plate(), 2),
				new OreDictStack(CU.plate(), 1),
				new ComparableStack(ModItems.primer_50, 5),
				new ComparableStack(ModItems.casing_50, 5),
				new OreDictStack(ANY_PLASTICEXPLOSIVE.ingot(), 3),
				new ComparableStack(ModItems.cordite, 3),
				new OreDictStack(P_WHITE.ingot(), 3)
			}, 60);

		makeRecipe(new ComparableStack(ModItems.ammo_75bolt_he, 2), new AStack[] {
				new OreDictStack(STEEL.plate(), 2),
				new OreDictStack(CU.plate(), 1),
				new ComparableStack(ModItems.primer_50, 5),
				new ComparableStack(ModItems.casing_50, 5),
				new OreDictStack(ANY_HIGHEXPLOSIVE.ingot(), 5),
				new ComparableStack(ModItems.cordite, 5),
				new OreDictStack(REDSTONE.dust(), 3)
			}, 60);

		makeRecipe(new ComparableStack(ModItems.spawn_worm, 1), new AStack[] {
				new OreDictStack(TI.block(), 75),
				new ComparableStack(ModItems.motor, 75),
				new ComparableStack(ModBlocks.glass_trinitite, 25),
				new OreDictStack(REDSTONE.dust(), 75),
				new ComparableStack(ModItems.wire_gold, 75),
				new OreDictStack(PO210.block(), 10),
				new ComparableStack(ModItems.plate_armor_titanium, 50),
				new ComparableStack(ModItems.coin_worm, 1)
			}, 1200);

		makeRecipe(new ComparableStack(ModItems.sat_gerald, 1), new AStack[] {
				new ComparableStack(ModItems.burnt_bark, 1),
				new ComparableStack(ModItems.combine_scrap, 1),
				new ComparableStack(ModItems.crystal_horn, 1),
				new ComparableStack(ModItems.crystal_charred, 1),
				new ComparableStack(ModBlocks.pink_log, 1),
				new ComparableStack(ModItems.mp_warhead_15_balefire, 1),
				new ComparableStack(ModBlocks.det_nuke, 16),
				new OreDictStack(STAR.ingot(), 32),
				new ComparableStack(ModItems.coin_creeper, 1),
				new ComparableStack(ModItems.coin_radiation, 1),
				new ComparableStack(ModItems.coin_maskman, 1),
				new ComparableStack(ModItems.coin_worm, 1),
			}, 1200);
		
		makeRecipe(new ComparableStack(ModBlocks.vault_door, 1), new AStack[] {
				new OreDictStack(STEEL.ingot(), 32),
				new OreDictStack(W.ingot(), 32),
				new OreDictStack(PB.plate(), 16),
				new OreDictStack(ALLOY.plate(), 4),
				new ComparableStack(ModItems.plate_polymer, 4),
				new ComparableStack(ModItems.bolt_tungsten, 8),
				new ComparableStack(ModItems.bolt_dura_steel, 8),
				new ComparableStack(ModItems.motor, 3),
			}, 200);
		
		makeRecipe(new ComparableStack(ModBlocks.blast_door, 1), new AStack[] {
				new OreDictStack(STEEL.ingot(), 8),
				new OreDictStack(W.ingot(), 8),
				new OreDictStack(PB.plate(), 6),
				new OreDictStack(ALLOY.plate(), 3),
				new ComparableStack(ModItems.plate_polymer, 3),
				new ComparableStack(ModItems.bolt_tungsten, 3),
				new ComparableStack(ModItems.bolt_dura_steel, 3),
				new ComparableStack(ModItems.motor, 1),
			}, 300);

		makeRecipe(new ComparableStack(ModBlocks.turret_chekhov, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(STEEL.ingot(), 16),
				new OreDictStack(DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 3),
				new ComparableStack(ModItems.circuit_targeting_tier3, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_rifle_2, 1),
				new ComparableStack(ModBlocks.crate_iron, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		makeRecipe(new ComparableStack(ModBlocks.turret_friendly, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(STEEL.ingot(), 16),
				new OreDictStack(DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 3),
				new ComparableStack(ModItems.circuit_targeting_tier2, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_rifle_1, 1),
				new ComparableStack(ModBlocks.crate_iron, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		makeRecipe(new ComparableStack(ModBlocks.turret_jeremy, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(STEEL.ingot(), 16),
				new OreDictStack(DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.circuit_targeting_tier4, 1),
				new ComparableStack(ModItems.motor_desh, 1),
				new ComparableStack(ModItems.hull_small_steel, 3),
				new ComparableStack(ModItems.mechanism_launcher_2, 1),
				new ComparableStack(ModBlocks.crate_steel, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		makeRecipe(new ComparableStack(ModBlocks.turret_tauon, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_lithium_battery, 1),
				new OreDictStack(STEEL.ingot(), 16),
				new OreDictStack(ANY_PLASTIC.ingot(), 4),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.circuit_targeting_tier4, 1),
				new ComparableStack(ModItems.motor_desh, 1),
				new OreDictStack(CU.ingot(), 32),
				new ComparableStack(ModItems.mechanism_special, 1),
				new ComparableStack(ModItems.battery_lithium, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		makeRecipe(new ComparableStack(ModBlocks.turret_richard, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(STEEL.ingot(), 16),
				new OreDictStack(DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.circuit_targeting_tier4, 1),
				new OreDictStack(ANY_PLASTIC.ingot(), 2),
				new ComparableStack(ModItems.hull_small_steel, 8),
				new ComparableStack(ModItems.mechanism_launcher_2, 1),
				new ComparableStack(ModBlocks.crate_steel, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		makeRecipe(new ComparableStack(ModBlocks.turret_howard, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(STEEL.ingot(), 24),
				new OreDictStack(DURA.ingot(), 6),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.motor_desh, 2),
				new ComparableStack(ModItems.circuit_targeting_tier3, 2),
				new ComparableStack(ModItems.pipes_steel, 2),
				new ComparableStack(ModItems.mechanism_rifle_2, 2),
				new ComparableStack(ModBlocks.crate_steel, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		makeRecipe(new ComparableStack(ModBlocks.turret_maxwell, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_lithium_battery, 1),
				new OreDictStack(STEEL.ingot(), 24),
				new OreDictStack(DURA.ingot(), 6),
				new ComparableStack(ModItems.motor, 2),
				new ComparableStack(ModItems.circuit_targeting_tier4, 2),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_special, 3),
				new ComparableStack(ModItems.magnetron, 16),
				new OreDictStack(TCALLOY.ingot(), 8),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		makeRecipe(new ComparableStack(ModBlocks.turret_fritz, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_battery, 1),
				new OreDictStack(STEEL.ingot(), 16),
				new OreDictStack(DURA.ingot(), 4),
				new ComparableStack(ModItems.motor, 3),
				new ComparableStack(ModItems.circuit_targeting_tier3, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.mechanism_launcher_1, 1),
				new ComparableStack(ModBlocks.barrel_steel, 1),
				new ComparableStack(ModItems.crt_display, 1)
			}, 200);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_silex, 1), new AStack[] {
				new ComparableStack(Blocks.glass, 12),
				new ComparableStack(ModItems.motor, 2),
				new OreDictStack(DURA.ingot(), 4),
				new OreDictStack(STEEL.plate(), 8),
				new OreDictStack(DESH.ingot(), 2),
				new ComparableStack(ModItems.tank_steel, 1),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.crystal_diamond, 1)
			}, 400);
		
		makeRecipe(new ComparableStack(Item.getItemFromBlock(ModBlocks.machine_fel), 1), new AStack[] {
				new ComparableStack(ModBlocks.fusion_conductor, 16),
				new ComparableStack(ModBlocks.machine_lithium_battery, 2),
				new OreDictStack(STEEL.ingot(), 16),
				new OreDictStack(STEEL.plate(), 24),
				new OreDictStack(ANY_PLASTIC.ingot(), 8),
				new ComparableStack(ModItems.circuit_red_copper, 4),
				new ComparableStack(ModItems.wire_red_copper, 64),
				new ComparableStack(ModItems.coil_advanced_torus, 16),
				new ComparableStack(ModItems.circuit_gold, 1)
		}, 400);
		
		makeRecipe(new ComparableStack(ModBlocks.rbmk_blank, 1), new AStack[] {
				new ComparableStack(ModBlocks.concrete_asbestos, 4),
				new OreDictStack(STEEL.plate(), 4),
				new OreDictStack(CU.ingot(), 4),
				new ComparableStack(ModItems.plate_polymer, 4)
			}, 100);
		
		makeRecipe(new ComparableStack(ModItems.multitool_hit, 1), new AStack[] {
				new OreDictStack(TCALLOY.ingot(), 4),
				new OreDictStack(STEEL.plate(), 4),
				new ComparableStack(ModItems.wire_gold, 12),
				new ComparableStack(ModItems.motor, 4),
				new ComparableStack(ModItems.circuit_tantalium, 16)
			}, 100);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_deuterium_extractor, 1), new AStack[] {
				new ComparableStack(ModItems.deuterium_filter, 1),
				new ComparableStack(ModItems.sulfur, 12),
				new OreDictStack(STEEL.plate(), 8),
				new OreDictStack(AL.plate(), 4),
				new ComparableStack(ModItems.pipes_steel),
				new ComparableStack(ModItems.board_copper, 2),
				new ComparableStack(ModItems.turbine_titanium, 2),
				new ComparableStack(ModItems.circuit_aluminium, 3)
			}, 100);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_chemfac, 1), new AStack[] {
				new OreDictStack(STEEL.ingot(), 48),
				new OreDictStack(TCALLOY.ingot(), 8),
				new OreDictStack(NB.ingot(), 4),
				new OreDictStack(RUBBER.ingot(), 16),
				new ComparableStack(ModItems.hull_big_steel, 12),
				new ComparableStack(ModItems.tank_steel, 8),
				new ComparableStack(ModItems.motor_desh, 4),
				new ComparableStack(ModItems.coil_tungsten, 24),
				new ComparableStack(ModItems.pipes_steel, 1),
				new ComparableStack(ModItems.circuit_gold, 3)
			}, 400);
		
		makeRecipe(new ComparableStack(ModItems.missile_shuttle, 1), new AStack[] {
				new ComparableStack(ModItems.missile_generic, 2),
				new ComparableStack(ModItems.missile_strong, 1),
				new OreDictStack(KEY_ORANGE, 5),
				new ComparableStack(ModItems.canister_full, 24, Fluids.GASOLINE_LEADED.getID()),
				new OreDictStack(FIBER.ingot(), 12),
				new ComparableStack(ModItems.circuit_copper, 2),
				new OreDictStack(ANY_PLASTICEXPLOSIVE.ingot(), 8),
				new OreDictStack(KEY_ANYPANE, 6),
				new OreDictStack(STEEL.plate(), 4),
		}, 100);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_difurnace_rtg_off, 1), new AStack[] {
				new ComparableStack(ModBlocks.machine_difurnace_off, 1),
				new ComparableStack(ModItems.rtg_unit, 3),
				new OreDictStack(DESH.ingot(), 4),
				new OreDictStack(PB.plate(), 6),
				new OreDictStack(OreDictManager.getReflector(), 8),
				new OreDictStack(CU.plate(), 12)
			}, 150);

		makeRecipe(new ComparableStack(ModBlocks.block_cap_nuka, 1), new AStack[] { new ComparableStack(ModItems.cap_nuka, 128) }, 10);
		makeRecipe(new ComparableStack(ModBlocks.block_cap_quantum, 1), new AStack[] { new ComparableStack(ModItems.cap_quantum, 128) }, 10);
		makeRecipe(new ComparableStack(ModBlocks.block_cap_sparkle, 1), new AStack[] { new ComparableStack(ModItems.cap_sparkle, 128) }, 10);
		makeRecipe(new ComparableStack(ModBlocks.block_cap_rad, 1), new AStack[] { new ComparableStack(ModItems.cap_rad, 128) }, 10);
		makeRecipe(new ComparableStack(ModBlocks.block_cap_korl, 1), new AStack[] { new ComparableStack(ModItems.cap_korl, 128) }, 10);
		makeRecipe(new ComparableStack(ModBlocks.block_cap_fritz, 1), new AStack[] { new ComparableStack(ModItems.cap_fritz, 128) }, 10);
		makeRecipe(new ComparableStack(ModBlocks.block_cap_sunset, 1), new AStack[] { new ComparableStack(ModItems.cap_sunset, 128) }, 10);
		makeRecipe(new ComparableStack(ModBlocks.block_cap_star, 1), new AStack[] { new ComparableStack(ModItems.cap_star, 128) }, 10);

		if(!GeneralConfig.enable528) {
			makeRecipe(new ComparableStack(ModBlocks.reactor_element, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 2), new OreDictStack(OreDictManager.getReflector(), 4), new OreDictStack(PB.plate(), 2), new OreDictStack(ZR.ingot(), 2), },150);
			makeRecipe(new ComparableStack(ModBlocks.reactor_control, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 4), new OreDictStack(PB.ingot(), 6), new ComparableStack(ModItems.bolt_tungsten, 6), new ComparableStack(ModItems.motor, 1), },100);
			makeRecipe(new ComparableStack(ModBlocks.reactor_hatch, 1), new AStack[] {new ComparableStack(ModBlocks.brick_concrete, 1), new OreDictStack(STEEL.plate(), 6), },150);
			makeRecipe(new ComparableStack(ModBlocks.reactor_conductor, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 4), new OreDictStack(CU.plate(), 12), new ComparableStack(ModItems.wire_tungsten, 4), },130);
			makeRecipe(new ComparableStack(ModBlocks.reactor_computer, 1), new AStack[] {new ComparableStack(ModBlocks.reactor_conductor, 2), new ComparableStack(ModItems.circuit_targeting_tier3, 4), new ComparableStack(ModItems.circuit_gold, 1), },250);
			makeRecipe(new ComparableStack(ModBlocks.machine_radgen, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 8), new OreDictStack(STEEL.plate(), 32), new ComparableStack(ModItems.coil_magnetized_tungsten, 6), new ComparableStack(ModItems.wire_magnetized_tungsten, 24), new ComparableStack(ModItems.circuit_gold, 4), new ComparableStack(ModItems.reactor_core, 3), new OreDictStack(STAR.ingot(), 1), new OreDictStack("dyeRed", 1), },400);
			makeRecipe(new ComparableStack(ModBlocks.machine_reactor_breeding, 1), new AStack[] {new ComparableStack(ModItems.reactor_core, 1), new OreDictStack(STEEL.ingot(), 12), new OreDictStack(PB.plate(), 16), new ComparableStack(ModBlocks.reinforced_glass, 4), new OreDictStack(ASBESTOS.ingot(), 4), new OreDictStack(TCALLOY.ingot(), 4), new ComparableStack(ModItems.crt_display, 1)},150);
			makeRecipe(new ComparableStack(ModBlocks.reactor_research, 1), new AStack[] {new OreDictStack(STEEL.ingot(), 8), new OreDictStack(TCALLOY.ingot(), 4), new ComparableStack(ModItems.motor_desh, 2), new OreDictStack(B.ingot(), 5), new OreDictStack(PB.ingot(), 12), new OreDictStack(PB.plate(), 2), new OreDictStack(AL.plate(), 4), new ComparableStack(ModItems.crt_display, 3), new ComparableStack(ModItems.circuit_copper, 2), },300);
		
		} else {
			addTantalium(new ComparableStack(ModBlocks.machine_centrifuge, 1), 5);
			addTantalium(new ComparableStack(ModBlocks.machine_gascent, 1), 25);
			addTantalium(new ComparableStack(ModBlocks.machine_crystallizer, 1), 15);
			addTantalium(new ComparableStack(ModBlocks.machine_large_turbine, 1), 10);
			addTantalium(new ComparableStack(ModBlocks.machine_chungus, 1), 50);
			addTantalium(new ComparableStack(ModBlocks.machine_refinery, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.machine_silex, 1), 15);
			addTantalium(new ComparableStack(ModBlocks.machine_radar, 1), 20);
			addTantalium(new ComparableStack(ModBlocks.machine_mining_laser, 1), 30);
			
			addTantalium(new ComparableStack(ModBlocks.turret_chekhov, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.turret_friendly, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.turret_jeremy, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.turret_tauon, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.turret_richard, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.turret_howard, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.turret_maxwell, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.turret_fritz, 1), 3);
			addTantalium(new ComparableStack(ModBlocks.launch_pad, 1), 5);
			
			makeRecipe(new ComparableStack(ModBlocks.machine_cyclotron, 1), new AStack[] {
					new ComparableStack(ModBlocks.machine_lithium_battery, 3),
					new ComparableStack(ModBlocks.hadron_coil_neodymium, 8),
					new ComparableStack(ModItems.wire_advanced_alloy, 64),
					new OreDictStack(STEEL.ingot(), 16),
					new OreDictStack(STEEL.plate(), 32),
					new OreDictStack(AL.plate(), 32),
					new OreDictStack(ANY_PLASTIC.ingot(), 24),
					new OreDictStack(RUBBER.ingot(), 24),
					new ComparableStack(ModItems.board_copper, 8),
					new ComparableStack(ModItems.circuit_red_copper, 8),
					new ComparableStack(ModItems.circuit_gold, 3),
					new ComparableStack(ModItems.circuit_tantalium, 50),
				}, 600);
			
			makeRecipe(new ComparableStack(ModBlocks.rbmk_console, 1), new AStack[] {
					new OreDictStack(STEEL.ingot(), 16),
					new OreDictStack(AL.plate(), 32),
					new ComparableStack(ModItems.plate_polymer, 16),
					new ComparableStack(ModItems.circuit_gold, 5),
					new ComparableStack(ModItems.circuit_tantalium, 20),
					new ComparableStack(ModItems.crt_display, 8),
				}, 300);
			
			makeRecipe(new ComparableStack(ModBlocks.rbmk_crane_console, 1), new AStack[] {
					new OreDictStack(STEEL.ingot(), 16),
					new OreDictStack(AL.plate(), 8),
					new ComparableStack(ModItems.plate_polymer, 4),
					new ComparableStack(ModItems.circuit_gold, 1),
					new ComparableStack(ModItems.circuit_tantalium, 10),
				}, 300);
			
			makeRecipe(new ComparableStack(ModBlocks.hadron_core, 1), new AStack[] {
					new ComparableStack(ModBlocks.hadron_coil_alloy, 24),
					new OreDictStack(STEEL.ingot(), 8),
					new OreDictStack(ANY_PLASTIC.ingot(), 16),
					new OreDictStack(TCALLOY.ingot(), 8),
					new ComparableStack(ModItems.circuit_gold, 5),
					new ComparableStack(ModItems.circuit_schrabidium, 5),
					new ComparableStack(ModItems.circuit_tantalium, 192),
					new ComparableStack(ModItems.crt_display, 1),
				}, 300);
			
			makeRecipe(new ComparableStack(ModBlocks.struct_launcher_core, 1), new AStack[] {
					new ComparableStack(ModBlocks.machine_battery, 3),
					new ComparableStack(ModBlocks.steel_scaffold, 10),
					new OreDictStack(STEEL.ingot(), 16),
					new OreDictStack(ANY_PLASTIC.ingot(), 8),
					new ComparableStack(ModItems.circuit_red_copper, 5),
					new ComparableStack(ModItems.circuit_tantalium, 15),
				}, 200);
			
			makeRecipe(new ComparableStack(ModBlocks.struct_launcher_core_large, 1), new AStack[] {
					new ComparableStack(ModBlocks.machine_battery, 5),
					new ComparableStack(ModBlocks.steel_scaffold, 10),
					new OreDictStack(STEEL.ingot(), 24),
					new OreDictStack(ANY_PLASTIC.ingot(), 12),
					new ComparableStack(ModItems.circuit_gold, 5),
					new ComparableStack(ModItems.circuit_tantalium, 25),
				}, 200);
			
			makeRecipe(new ComparableStack(ModBlocks.struct_soyuz_core, 1), new AStack[] {
					new ComparableStack(ModBlocks.machine_lithium_battery, 5),
					new ComparableStack(ModBlocks.steel_scaffold, 24),
					new OreDictStack(STEEL.ingot(), 32),
					new OreDictStack(ANY_PLASTIC.ingot(), 24),
					new ComparableStack(ModItems.circuit_gold, 5),
					new ComparableStack(ModItems.upgrade_power_3, 3),
					new ComparableStack(ModItems.circuit_tantalium, 100),
				}, 200);
		}
		
		makeRecipe(new ComparableStack(ModBlocks.machine_fracking_tower), new AStack[] {
						new ComparableStack(ModBlocks.steel_scaffold, 40),
						new ComparableStack(ModBlocks.concrete_smooth, 64),
						new ComparableStack(ModItems.drill_titanium),
						new ComparableStack(ModItems.motor_desh, 2),
						new ComparableStack(ModItems.plate_desh, 6),
						new OreDictStack(NB.ingot(), 8),
						new ComparableStack(ModItems.tank_steel, 24),
						new ComparableStack(ModItems.pipes_steel, 2)
				}, 600);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_catalytic_cracker), new AStack[] {
				new ComparableStack(ModBlocks.steel_scaffold, 16),
				new ComparableStack(ModItems.hull_big_steel, 4),
				new ComparableStack(ModItems.tank_steel, 3),
				new OreDictStack(ANY_PLASTIC.ingot(), 4),
				new OreDictStack(NB.ingot(), 2),
				new ComparableStack(ModItems.catalyst_clay, 12),
				}, 300);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_liquefactor), new AStack[] {
				new OreDictStack(STEEL.ingot(), 8),
				new OreDictStack(NB.ingot(), 2),
				new OreDictStack(CU.plate(), 12),
				new OreDictStack(ANY_TAR.any(), 8),
				new ComparableStack(ModItems.catalyst_clay, 4),
				new ComparableStack(ModItems.coil_tungsten, 8),
				new ComparableStack(ModItems.tank_steel, 2),
				new ComparableStack(ModItems.inf_water_mk2, 2)
				}, 200);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_solidifier), new AStack[] {
				new OreDictStack(ANY_CONCRETE.any(), 8),
				new OreDictStack(NB.ingot(), 2),
				new OreDictStack(AL.plate(), 12),
				new OreDictStack(ANY_PLASTIC.ingot(), 4),
				new ComparableStack(ModItems.hull_big_steel, 3),
				new ComparableStack(ModItems.catalyst_clay, 4),
				new ComparableStack(ModItems.coil_copper, 4),
				new ComparableStack(ModItems.tank_steel, 2)
				}, 200);
		
		makeRecipe(new ComparableStack(ModBlocks.machine_radiolysis), new AStack[] {
				new OreDictStack(STEEL.ingot(), 12),
				new OreDictStack(TCALLOY.ingot(), 4),
				new OreDictStack(DURA.ingot(), 10),
				new OreDictStack(RUBBER.ingot(), 4),
				new OreDictStack(PB.plate(), 12),
				new ComparableStack(ModItems.board_copper, 4),
				new ComparableStack(ModItems.thermo_element, 10),
				new ComparableStack(ModItems.wire_red_copper, 8),
				new ComparableStack(ModItems.tank_steel, 3)
			}, 200);
		
		makeRecipe(new ComparableStack(ModBlocks.transition_seal, 1), new AStack[]{
				new ComparableStack(ModBlocks.cmb_brick_reinforced, 16),
				new OreDictStack(STEEL.plate(), 64),
				new OreDictStack(ALLOY.plate(), 40),
				new ComparableStack(ModItems.plate_polymer, 36),
				new OreDictStack(STEEL.block(), 24),
				new ComparableStack(ModItems.motor_desh, 16),
				new ComparableStack(ModItems.bolt_dura_steel, 12),
				new OreDictStack(KEY_YELLOW, 4)
			}, 5000);
		
		if(Loader.isModLoaded("Mekanism")) {
			
			Block mb = (Block) Block.blockRegistry.getObject("Mekanism:MachineBlock");
			
			if(mb != null) {
	
				makeRecipe(new ComparableStack(mb, 1, 4), new AStack[] {
						new OreDictStack(DURA.ingot(), 16),
						new OreDictStack(DESH.ingot(), 8),
						new OreDictStack(STEEL.plate(), 48),
						new OreDictStack(CU.plate(), 24),
						new ComparableStack(ModItems.pipes_steel, 8),
						new ComparableStack(ModItems.circuit_gold, 8),
						new ComparableStack(ModItems.wire_advanced_alloy, 24),
						new ComparableStack(ModBlocks.fusion_conductor, 12),
						new ComparableStack(ModBlocks.machine_lithium_battery, 3),
						new ComparableStack(ModItems.crystal_redstone, 12),
						new ComparableStack(ModItems.crystal_diamond, 8),
						new ComparableStack(ModItems.motor_desh, 16)
					}, 15 * 60 * 20);
			}
		}
		
		
		/// HIDDEN ///
		hidden.put(new ComparableStack(ModBlocks.machine_radgen, 1), new HashSet() {{ add(ModItems.journal_pip); }});
		hidden.put(new ComparableStack(ModBlocks.nuke_fstbmb, 1), new HashSet() {{ add(ModItems.journal_pip); add(ModItems.journal_bj); }});
		hidden.put(new ComparableStack(ModItems.mp_warhead_10_cloud, 1), new HashSet() {{ add(ModItems.journal_pip); }});
		hidden.put(new ComparableStack(ModItems.mp_warhead_10_taint, 1), new HashSet() {{ add(ModItems.journal_pip); }});
		hidden.put(new ComparableStack(ModItems.mp_warhead_15_balefire, 1), new HashSet() {{ add(ModItems.journal_bj); }});
		hidden.put(new ComparableStack(ModItems.sat_gerald, 1), new HashSet() {{ add(ModItems.journal_bj); }});
		hidden.put(new ComparableStack(ModItems.missile_soyuz, 1), new HashSet() {{ add(ModItems.journal_bj); }});
		hidden.put(new ComparableStack(ModItems.missile_soyuz_lander, 1), new HashSet() {{ add(ModItems.journal_bj); }});
	}
	
	public static void makeRecipe(ComparableStack out, AStack[] in, int duration) {
		
		if(out == null || Item.itemRegistry.getNameForObject(out.item) == null) {
			MainRegistry.logger.error("Canceling assembler registration, item was null!");
			return;
		}
		
		recipes.put(out, in);
		time.put(out, duration);
	}
	
	public static void addTantalium(ComparableStack out, int amount) {
		
		AStack[] ins = recipes.get(out);
		
		if(ins != null) {
			
			AStack[] news = new AStack[ins.length + 1];
			
			for(int i = 0; i < ins.length; i++)
				news[i] = ins[i];
			
			news[news.length - 1] = new ComparableStack(ModItems.circuit_tantalium, amount);
			
			recipes.put(out, news);
		}
	}
	
	/*
	 *  {
	 *    recipes : [
	 *      {
	 *        output : [ "item", "hbm:item.tank_steel", 1, 0 ],
	 *        duration : 100,
	 *        input : [
	 *          [ "dict", "blockSteel", 6 ],
	 *          [ "dict", "plateTitanium", 2 ],
	 *          [ "dict", "dyeGray", 1 ],
	 *        ]
	 *      },
	 *      {
	 *        output : [ "item", "hbm:plate_gold", 2, 0 ],
	 *        duration : 20,
	 *        input : [
	 *          [ "dict", "ingotGold", 3 ],
	 *          [ "item", "hbm:item.wire_gold", 5 ]
	 *        ]
	 *      }
	 *    ]
	 *  }
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
							AssemblerRecipes.recipes.put((ComparableStack) outp, Arrays.copyOf(inp.toArray(), inp.size(), AStack[].class));
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
		
		//if index 2 exists, eval it as a stacksize
		if(array.size() > 2 && array.get(2).isJsonPrimitive()) {
			
			if(array.get(2).getAsJsonPrimitive().isNumber()) {
				
				stacksize = Math.max(1, array.get(2).getAsJsonPrimitive().getAsNumber().intValue());
				
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
	
	public static void saveTemplateJSON(File dir) {
		
		template = new File(dir.getAbsolutePath() + File.separatorChar + "hbmTemplate.json");
		
		try {
			
			JsonWriter writer = new JsonWriter(new FileWriter(template));
			writer.setIndent("  ");
			
			writer.beginObject();

			writer.name("recipes").beginArray();
			
			for(ComparableStack output : recipeList) {
				
				writer.beginObject();
				writer.name("output").beginArray();
				writer.setIndent("");
				writer.value("item");
				writer.value(Item.itemRegistry.getNameForObject(output.toStack().getItem()));
				writer.value(output.stacksize);
				if(output.meta > 0)
					writer.value(output.meta);
				writer.endArray();
				writer.setIndent("  ");
				

				writer.name("input").beginArray();
				
				AStack[] inputs = recipes.get(output);
				for(AStack astack : inputs) {
					
					writer.beginArray();
					writer.setIndent("");
					
					if(astack instanceof ComparableStack) {
						ComparableStack comp = (ComparableStack) astack;
						
						writer.value("item");
						writer.value(Item.itemRegistry.getNameForObject(comp.toStack().getItem()));
						writer.value(comp.stacksize);
						if(comp.meta > 0)
							writer.value(comp.meta);
					}
					
					if(astack instanceof OreDictStack) {
						OreDictStack ore = (OreDictStack) astack;
						
						writer.value("dict");
						writer.value(ore.name);
						writer.value(ore.stacksize);
					}
					
					writer.endArray();
					writer.setIndent("  ");
				}
				
				writer.endArray();
				
				writer.name("duration").value(time.get(output));
				
				writer.endObject();
			}

			writer.endArray();
			writer.endObject();
			writer.close();
			
		} catch(IOException e) {
			//shush
		}
	}

	public static Map<ItemStack, List<Object>> getRecipes() {
		
		Map<ItemStack, List<Object>> recipes = new HashMap();
		
		for(Entry<ComparableStack, AStack[]> entry : AssemblerRecipes.recipes.entrySet()) {
			
			List<Object> value = new ArrayList();
			
			for(AStack o : entry.getValue()) {
				
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
