package com.hbm.crafting;

import com.hbm.items.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * For the loading and unloading of fuel rods
 * @author hbm
 */
public class RodRecipes {
	
	public static void register() {

		//Single rods
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rod_empty, 16), new Object[] { "SSS", "L L", "SSS", 'S', "plateSteel", 'L', "plateLead" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_th232, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_uranium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_u233, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_u235, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_u238, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_plutonium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_pu238, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_pu239, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_pu240, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_neptunium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_polonium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_lead, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_schrabidium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_solinium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_uranium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_thorium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_plutonium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_mox_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_schrabidium_fuel, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_euphemium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium, ModItems.nugget_euphemium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_australium, 1), new Object[] { ModItems.rod_empty, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium, ModItems.nugget_australium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_balefire, 1), new Object[] { ModItems.rod_empty, ModItems.egg_balefire_shard });
		
		//...with billets
		RecipesCommon.addRodBilletUnload(ModItems.billet_uranium, ModItems.rod_uranium);
		RecipesCommon.addRodBilletUnload(ModItems.billet_u233, ModItems.rod_u233);
		RecipesCommon.addRodBilletUnload(ModItems.billet_u235, ModItems.rod_u235);
		RecipesCommon.addRodBilletUnload(ModItems.billet_u238, ModItems.rod_u238);
		RecipesCommon.addRodBilletUnload(ModItems.billet_th232, ModItems.rod_th232);
		RecipesCommon.addRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_plutonium);
		RecipesCommon.addRodBilletUnload(ModItems.billet_pu238, ModItems.rod_pu238);
		RecipesCommon.addRodBilletUnload(ModItems.billet_pu239, ModItems.rod_pu239);
		RecipesCommon.addRodBilletUnload(ModItems.billet_pu240, ModItems.rod_pu240);
		RecipesCommon.addRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_neptunium);
		RecipesCommon.addRodBilletUnload(ModItems.billet_polonium, ModItems.rod_polonium);
		RecipesCommon.addRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_schrabidium);
		RecipesCommon.addRodBilletUnload(ModItems.billet_solinium, ModItems.rod_solinium);
		RecipesCommon.addRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_uranium_fuel);
		RecipesCommon.addRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_thorium_fuel);
		RecipesCommon.addRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_plutonium_fuel);
		RecipesCommon.addRodBillet(ModItems.billet_mox_fuel, ModItems.rod_mox_fuel);
		RecipesCommon.addRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_schrabidium_fuel);

		//Dual rods
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_empty, 2), new Object[] { ModItems.rod_dual_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_th232, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_uranium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_u233, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_u235, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_u238, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_plutonium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu238, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu239, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_pu240, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_neptunium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_polonium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_lead, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_schrabidium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_solinium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_uranium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_thorium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_plutonium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_mox_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_schrabidium_fuel, 1), new Object[] { ModItems.rod_dual_empty, ModItems.ingot_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_balefire, 1), new Object[] { ModItems.rod_dual_empty, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard });
		
		//...with billets
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_uranium, ModItems.rod_dual_uranium);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_u233, ModItems.rod_dual_u233);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_u235, ModItems.rod_dual_u235);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_u238, ModItems.rod_dual_u238);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_th232, ModItems.rod_dual_th232);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_dual_plutonium);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_pu238, ModItems.rod_dual_pu238);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_pu239, ModItems.rod_dual_pu239);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_pu240, ModItems.rod_dual_pu240);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_dual_neptunium);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_polonium, ModItems.rod_dual_polonium);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_dual_schrabidium);
		RecipesCommon.addDualRodBilletUnload(ModItems.billet_solinium, ModItems.rod_dual_solinium);
		RecipesCommon.addDualRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_dual_uranium_fuel);
		RecipesCommon.addDualRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_dual_thorium_fuel);
		RecipesCommon.addDualRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_dual_plutonium_fuel);
		RecipesCommon.addDualRodBillet(ModItems.billet_mox_fuel, ModItems.rod_dual_mox_fuel);
		RecipesCommon.addDualRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_dual_schrabidium_fuel);

		//Lithium and tritium rods
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_lithium, 1), new Object[] { ModItems.rod_empty, ModItems.lithium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_lithium, 1), new Object[] { ModItems.rod_dual_empty, ModItems.lithium, ModItems.lithium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_lithium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.lithium, ModItems.lithium, ModItems.lithium, ModItems.lithium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_tritium, 1), new Object[] { ModItems.rod_tritium, ModItems.cell_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_tritium, 2), new Object[] { ModItems.rod_dual_tritium, ModItems.cell_empty, ModItems.cell_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cell_tritium, 4), new Object[] { ModItems.rod_quad_tritium, ModItems.cell_empty, ModItems.cell_empty, ModItems.cell_empty, ModItems.cell_empty });

		//Quad rods
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_empty, 4), new Object[] { ModItems.rod_quad_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_dual_empty, ModItems.rod_dual_empty });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_th232, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_th232, ModItems.ingot_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232, ModItems.nugget_th232 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_uranium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_uranium, ModItems.ingot_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium, ModItems.nugget_uranium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_u233, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u233, ModItems.ingot_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233, ModItems.nugget_u233 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_u235, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u235, ModItems.ingot_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235, ModItems.nugget_u235 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_u238, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_u238, ModItems.ingot_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238, ModItems.nugget_u238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_plutonium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_plutonium, ModItems.ingot_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium, ModItems.nugget_plutonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu238, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu238, ModItems.ingot_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238, ModItems.nugget_pu238 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu239, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu239, ModItems.ingot_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239, ModItems.nugget_pu239 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_pu240, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_pu240, ModItems.ingot_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240, ModItems.nugget_pu240 });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_neptunium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_neptunium, ModItems.ingot_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium, ModItems.nugget_neptunium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_polonium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_polonium, ModItems.ingot_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium, ModItems.nugget_polonium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_lead, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_lead, ModItems.ingot_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead, ModItems.nugget_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_schrabidium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_schrabidium, ModItems.ingot_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, ModItems.nugget_schrabidium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_solinium, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_solinium, ModItems.ingot_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium, ModItems.nugget_solinium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_uranium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_uranium_fuel, ModItems.ingot_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_thorium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_thorium_fuel, ModItems.ingot_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel, ModItems.nugget_thorium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_plutonium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_plutonium_fuel, ModItems.ingot_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel, ModItems.nugget_plutonium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_mox_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_mox_fuel, ModItems.ingot_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel, ModItems.nugget_mox_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_schrabidium_fuel, 1), new Object[] { ModItems.rod_quad_empty, ModItems.ingot_schrabidium_fuel, ModItems.ingot_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel, ModItems.nugget_schrabidium_fuel });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_balefire, 1), new Object[] { ModItems.rod_quad_empty, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard, ModItems.egg_balefire_shard });
		
		//...with billets
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_uranium, ModItems.rod_quad_uranium);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_u233, ModItems.rod_quad_u233);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_u235, ModItems.rod_quad_u235);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_u238, ModItems.rod_quad_u238);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_th232, ModItems.rod_quad_th232);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_plutonium, ModItems.rod_quad_plutonium);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_pu238, ModItems.rod_quad_pu238);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_pu239, ModItems.rod_quad_pu239);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_pu240, ModItems.rod_quad_pu240);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_neptunium, ModItems.rod_quad_neptunium);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_polonium, ModItems.rod_quad_polonium);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_schrabidium, ModItems.rod_quad_schrabidium);
		RecipesCommon.addQuadRodBilletUnload(ModItems.billet_solinium, ModItems.rod_quad_solinium);
		RecipesCommon.addQuadRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_quad_uranium_fuel);
		RecipesCommon.addQuadRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_quad_thorium_fuel);
		RecipesCommon.addQuadRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_quad_plutonium_fuel);
		RecipesCommon.addQuadRodBillet(ModItems.billet_mox_fuel, ModItems.rod_quad_mox_fuel);
		RecipesCommon.addQuadRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_quad_schrabidium_fuel);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rbmk_fuel_empty, 1), new Object[] { "ZRZ", "Z Z", "ZRZ", 'Z', "ingotZirconium", 'R', ModItems.rod_quad_empty }));
		RecipesCommon.addRBMKRod(ModItems.billet_uranium, ModItems.rbmk_fuel_ueu);
		RecipesCommon.addRBMKRod(ModItems.billet_uranium_fuel, ModItems.rbmk_fuel_meu);
		RecipesCommon.addRBMKRod(ModItems.billet_u233, ModItems.rbmk_fuel_heu233);
		RecipesCommon.addRBMKRod(ModItems.billet_u235, ModItems.rbmk_fuel_heu235);
		RecipesCommon.addRBMKRod(ModItems.billet_thorium_fuel, ModItems.rbmk_fuel_thmeu);
		RecipesCommon.addRBMKRod(ModItems.billet_mox_fuel, ModItems.rbmk_fuel_mox);
		RecipesCommon.addRBMKRod(ModItems.billet_plutonium_fuel, ModItems.rbmk_fuel_lep);
		RecipesCommon.addRBMKRod(ModItems.billet_pu_mix, ModItems.rbmk_fuel_mep);
		RecipesCommon.addRBMKRod(ModItems.billet_pu239, ModItems.rbmk_fuel_hep239);
		RecipesCommon.addRBMKRod(ModItems.billet_pu241, ModItems.rbmk_fuel_hep241);
		RecipesCommon.addRBMKRod(ModItems.billet_americium_fuel, ModItems.rbmk_fuel_lea);
		RecipesCommon.addRBMKRod(ModItems.billet_am_mix, ModItems.rbmk_fuel_mea);
		RecipesCommon.addRBMKRod(ModItems.billet_am241, ModItems.rbmk_fuel_hea241);
		RecipesCommon.addRBMKRod(ModItems.billet_am242, ModItems.rbmk_fuel_hea242);
		RecipesCommon.addRBMKRod(ModItems.billet_neptunium_fuel, ModItems.rbmk_fuel_men);
		RecipesCommon.addRBMKRod(ModItems.billet_neptunium, ModItems.rbmk_fuel_hen);
		RecipesCommon.addRBMKRod(ModItems.billet_po210be, ModItems.rbmk_fuel_po210be);
		RecipesCommon.addRBMKRod(ModItems.billet_ra226be, ModItems.rbmk_fuel_ra226be);
		RecipesCommon.addRBMKRod(ModItems.billet_pu238be, ModItems.rbmk_fuel_pu238be);
		RecipesCommon.addRBMKRod(ModItems.billet_australium_lesser, ModItems.rbmk_fuel_leaus);
		RecipesCommon.addRBMKRod(ModItems.billet_australium_greater, ModItems.rbmk_fuel_heaus);
		RecipesCommon.addRBMKRod(ModItems.egg_balefire_shard, ModItems.rbmk_fuel_balefire);
		RecipesCommon.addRBMKRod(ModItems.billet_les, ModItems.rbmk_fuel_les);
		RecipesCommon.addRBMKRod(ModItems.billet_schrabidium_fuel, ModItems.rbmk_fuel_mes);
		RecipesCommon.addRBMKRod(ModItems.billet_hes, ModItems.rbmk_fuel_hes);
		RecipesCommon.addRBMKRod(ModItems.billet_balefire_gold, ModItems.rbmk_fuel_balefire_gold);
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rbmk_fuel_drx, 1), new Object[] { ModItems.rbmk_fuel_balefire, ModItems.particle_digamma });
		
		//Water rods
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_water, 1), new Object[] { ModItems.rod_empty, Items.water_bucket });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_dual_water, 1), new Object[] { ModItems.rod_dual_empty, Items.water_bucket, Items.water_bucket });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.rod_quad_water, 1), new Object[] { ModItems.rod_quad_empty, Items.water_bucket, Items.water_bucket, Items.water_bucket, Items.water_bucket });

		//Rod recycling
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 6), new Object[] { ModItems.rod_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_australium, 6), new Object[] { ModItems.rod_australium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_euphemium, 6), new Object[] { ModItems.rod_euphemium });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.egg_balefire_shard, 1), new Object[] { ModItems.rod_balefire });

		//Dual rod recycling
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 12), new Object[] { ModItems.rod_dual_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.egg_balefire_shard, 2), new Object[] { ModItems.rod_dual_balefire });

		//Quad rod recycling
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nugget_lead, 24), new Object[] { ModItems.rod_quad_lead });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.egg_balefire_shard, 4), new Object[] { ModItems.rod_quad_balefire });

		//Waste rod recycling
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 1), new Object[] { ModItems.rod_waste });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 2), new Object[] { ModItems.rod_dual_waste });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.nuclear_waste, 4), new Object[] { ModItems.rod_quad_waste });

		//Depleted fuel recycling
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_uranium_hot, 1), new Object[] { ModItems.rod_uranium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_uranium_hot, 2), new Object[] { ModItems.rod_dual_uranium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_uranium_hot, 4), new Object[] { ModItems.rod_quad_uranium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_thorium_hot, 1), new Object[] { ModItems.rod_thorium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_thorium_hot, 2), new Object[] { ModItems.rod_dual_thorium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_thorium_hot, 4), new Object[] { ModItems.rod_quad_thorium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_plutonium_hot, 1), new Object[] { ModItems.rod_plutonium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_plutonium_hot, 2), new Object[] { ModItems.rod_dual_plutonium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_plutonium_hot, 4), new Object[] { ModItems.rod_quad_plutonium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_mox_hot, 1), new Object[] { ModItems.rod_mox_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_mox_hot, 2), new Object[] { ModItems.rod_dual_mox_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_mox_hot, 4), new Object[] { ModItems.rod_quad_mox_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_schrabidium_hot, 1), new Object[] { ModItems.rod_schrabidium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_schrabidium_hot, 2), new Object[] { ModItems.rod_dual_schrabidium_fuel_depleted });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.waste_schrabidium_hot, 4), new Object[] { ModItems.rod_quad_schrabidium_fuel_depleted });
	}
}
