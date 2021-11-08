package com.hbm.crafting;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.items.ModItems;
import com.hbm.main.CraftingManager;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * For the loading and unloading of fuel rods
 * @author hbm
 */
public class RodRecipes {
	
	public static void register() {

		//Single rods
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rod_empty, 16), new Object[] { "SSS", "L L", "SSS", 'S', STEEL.plate(), 'L', PB.plate() });
		addRodBilletUnload(U, ModItems.billet_uranium, ModItems.rod_uranium);
		addRodBilletUnload(U233, ModItems.billet_u233, ModItems.rod_u233);
		addRodBilletUnload(U235, ModItems.billet_u235, ModItems.rod_u235);
		addRodBilletUnload(U238, ModItems.billet_u238, ModItems.rod_u238);
		addRodBilletUnload(TH232, ModItems.billet_th232, ModItems.rod_th232);
		addRodBilletUnload(PU, ModItems.billet_plutonium, ModItems.rod_plutonium);
		addRodBilletUnload(PU238, ModItems.billet_pu238, ModItems.rod_pu238);
		addRodBilletUnload(PU239, ModItems.billet_pu239, ModItems.rod_pu239);
		addRodBilletUnload(PU240, ModItems.billet_pu240, ModItems.rod_pu240);
		addRodBilletUnload(NP237, ModItems.billet_neptunium, ModItems.rod_neptunium);
		addRodBilletUnload(PO210, ModItems.billet_polonium, ModItems.rod_polonium);
		addRodBilletUnload(SA326, ModItems.billet_schrabidium, ModItems.rod_schrabidium);
		addRodBilletUnload(SA327, ModItems.billet_solinium, ModItems.rod_solinium);
		addRodBilletUnload(ModItems.egg_balefire_shard, ModItems.rod_balefire);
		addFuelRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_uranium_fuel);
		addFuelRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_thorium_fuel);
		addFuelRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_plutonium_fuel);
		addFuelRodBillet(ModItems.billet_mox_fuel, ModItems.rod_mox_fuel);
		addFuelRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_schrabidium_fuel);
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_lead, 1), new Object[] { ModItems.rod_empty, PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 6), new Object[] { ModItems.rod_lead });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_lithium, 1), new Object[] { ModItems.rod_empty, LI.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 1), new Object[] { ModItems.rod_lithium });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell_tritium, 1), new Object[] { ModItems.rod_tritium, ModItems.cell_empty });

		//Dual rods
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_empty, 2), new Object[] { ModItems.rod_dual_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty });
		addDualRodBilletUnload(U, ModItems.billet_uranium, ModItems.rod_dual_uranium);
		addDualRodBilletUnload(U233, ModItems.billet_u233, ModItems.rod_dual_u233);
		addDualRodBilletUnload(U235, ModItems.billet_u235, ModItems.rod_dual_u235);
		addDualRodBilletUnload(U238, ModItems.billet_u238, ModItems.rod_dual_u238);
		addDualRodBilletUnload(TH232, ModItems.billet_th232, ModItems.rod_dual_th232);
		addDualRodBilletUnload(PU, ModItems.billet_plutonium, ModItems.rod_dual_plutonium);
		addDualRodBilletUnload(PU238, ModItems.billet_pu238, ModItems.rod_dual_pu238);
		addDualRodBilletUnload(PU239, ModItems.billet_pu239, ModItems.rod_dual_pu239);
		addDualRodBilletUnload(PU240, ModItems.billet_pu240, ModItems.rod_dual_pu240);
		addDualRodBilletUnload(NP237, ModItems.billet_neptunium, ModItems.rod_dual_neptunium);
		addDualRodBilletUnload(PO210, ModItems.billet_polonium, ModItems.rod_dual_polonium);
		addDualRodBilletUnload(SA326, ModItems.billet_schrabidium, ModItems.rod_dual_schrabidium);
		addDualRodBilletUnload(SA327, ModItems.billet_solinium, ModItems.rod_dual_solinium);
		addDualRodBilletUnload(ModItems.egg_balefire_shard, ModItems.rod_dual_balefire);
		addDualFuelRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_dual_uranium_fuel);
		addDualFuelRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_dual_thorium_fuel);
		addDualFuelRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_dual_plutonium_fuel);
		addDualFuelRodBillet(ModItems.billet_mox_fuel, ModItems.rod_dual_mox_fuel);
		addDualFuelRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_dual_schrabidium_fuel);
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual_lead, 1), new Object[] { ModItems.rod_dual_empty, PB.ingot(), PB.nugget(), PB.nugget(), PB.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 12), new Object[] { ModItems.rod_dual_lead });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual_lithium, 1), new Object[] { ModItems.rod_dual_empty, LI.ingot(), LI.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 2), new Object[] { ModItems.rod_dual_lithium });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell_tritium, 2), new Object[] { ModItems.rod_dual_tritium, ModItems.cell_empty, ModItems.cell_empty });

		//Quad rods
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_empty, 4), new Object[] { ModItems.rod_quad_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_dual_empty, ModItems.rod_dual_empty });
		addQuadRodBilletUnload(U, ModItems.billet_uranium, ModItems.rod_quad_uranium);
		addQuadRodBilletUnload(U233, ModItems.billet_u233, ModItems.rod_quad_u233);
		addQuadRodBilletUnload(U235, ModItems.billet_u235, ModItems.rod_quad_u235);
		addQuadRodBilletUnload(U238, ModItems.billet_u238, ModItems.rod_quad_u238);
		addQuadRodBilletUnload(TH232, ModItems.billet_th232, ModItems.rod_quad_th232);
		addQuadRodBilletUnload(PU, ModItems.billet_plutonium, ModItems.rod_quad_plutonium);
		addQuadRodBilletUnload(PU238, ModItems.billet_pu238, ModItems.rod_quad_pu238);
		addQuadRodBilletUnload(PU239, ModItems.billet_pu239, ModItems.rod_quad_pu239);
		addQuadRodBilletUnload(PU240, ModItems.billet_pu240, ModItems.rod_quad_pu240);
		addQuadRodBilletUnload(NP237, ModItems.billet_neptunium, ModItems.rod_quad_neptunium);
		addQuadRodBilletUnload(PO210, ModItems.billet_polonium, ModItems.rod_quad_polonium);
		addQuadRodBilletUnload(SA326, ModItems.billet_schrabidium, ModItems.rod_quad_schrabidium);
		addQuadRodBilletUnload(SA327, ModItems.billet_solinium, ModItems.rod_quad_solinium);
		addQuadRodBilletUnload(ModItems.egg_balefire_shard, ModItems.rod_quad_balefire);
		addQuadFuelRodBillet(ModItems.billet_uranium_fuel, ModItems.rod_quad_uranium_fuel);
		addQuadFuelRodBillet(ModItems.billet_thorium_fuel, ModItems.rod_quad_thorium_fuel);
		addQuadFuelRodBillet(ModItems.billet_plutonium_fuel, ModItems.rod_quad_plutonium_fuel);
		addQuadFuelRodBillet(ModItems.billet_mox_fuel, ModItems.rod_quad_mox_fuel);
		addQuadFuelRodBillet(ModItems.billet_schrabidium_fuel, ModItems.rod_quad_schrabidium_fuel);
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_lead, 1), new Object[] { ModItems.rod_quad_empty, PB.ingot(), PB.ingot(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 24), new Object[] { ModItems.rod_quad_lead });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_lithium, 1), new Object[] { ModItems.rod_quad_empty, LI.ingot(), LI.ingot(), LI.ingot(), LI.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 4), new Object[] { ModItems.rod_quad_lithium });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell_tritium, 4), new Object[] { ModItems.rod_quad_tritium, ModItems.cell_empty, ModItems.cell_empty, ModItems.cell_empty, ModItems.cell_empty });

		//Pile fuel
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pile_rod_uranium, 1), new Object[] { " U ", "PUP", " U ", 'P', IRON.plate(), 'U', U.billet() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pile_rod_source, 1), new Object[] { " U ", "PUP", " U ", 'P', IRON.plate(), 'U', ModItems.billet_ra226be });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pile_rod_boron, 1), new Object[] { "B", "W", "B", 'B', B.ingot(), 'W', KEY_PLANKS });
		
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rbmk_fuel_empty, 1), new Object[] { "ZRZ", "Z Z", "ZRZ", 'Z', ZR.ingot(), 'R', ModItems.rod_quad_empty });
		addRBMKRod(U, ModItems.rbmk_fuel_ueu);
		addRBMKRod(ModItems.billet_uranium_fuel, ModItems.rbmk_fuel_meu);
		addRBMKRod(U233, ModItems.rbmk_fuel_heu233);
		addRBMKRod(U235, ModItems.rbmk_fuel_heu235);
		addRBMKRod(ModItems.billet_thorium_fuel, ModItems.rbmk_fuel_thmeu);
		addRBMKRod(ModItems.billet_mox_fuel, ModItems.rbmk_fuel_mox);
		addRBMKRod(ModItems.billet_plutonium_fuel, ModItems.rbmk_fuel_lep);
		addRBMKRod(PURG, ModItems.rbmk_fuel_mep);
		addRBMKRod(PU239, ModItems.rbmk_fuel_hep239);
		addRBMKRod(PU241, ModItems.rbmk_fuel_hep241);
		addRBMKRod(ModItems.billet_americium_fuel, ModItems.rbmk_fuel_lea);
		addRBMKRod(AMRG, ModItems.rbmk_fuel_mea);
		addRBMKRod(AM241, ModItems.rbmk_fuel_hea241);
		addRBMKRod(AM242, ModItems.rbmk_fuel_hea242);
		addRBMKRod(ModItems.billet_neptunium_fuel, ModItems.rbmk_fuel_men);
		addRBMKRod(NP237, ModItems.rbmk_fuel_hen);
		addRBMKRod(ModItems.billet_po210be, ModItems.rbmk_fuel_po210be);
		addRBMKRod(ModItems.billet_ra226be, ModItems.rbmk_fuel_ra226be);
		addRBMKRod(ModItems.billet_pu238be, ModItems.rbmk_fuel_pu238be);
		addRBMKRod(ModItems.billet_australium_lesser, ModItems.rbmk_fuel_leaus);
		addRBMKRod(ModItems.billet_australium_greater, ModItems.rbmk_fuel_heaus);
		addRBMKRod(ModItems.egg_balefire_shard, ModItems.rbmk_fuel_balefire);
		addRBMKRod(ModItems.billet_les, ModItems.rbmk_fuel_les);
		addRBMKRod(ModItems.billet_schrabidium_fuel, ModItems.rbmk_fuel_mes);
		addRBMKRod(ModItems.billet_hes, ModItems.rbmk_fuel_hes);
		addRBMKRod(ModItems.billet_balefire_gold, ModItems.rbmk_fuel_balefire_gold);
		addRBMKRod(ModItems.billet_zfb_bismuth, ModItems.rbmk_fuel_zfb_bismuth);
		addRBMKRod(ModItems.billet_zfb_pu241, ModItems.rbmk_fuel_zfb_pu241);
		addRBMKRod(ModItems.billet_zfb_am_mix, ModItems.rbmk_fuel_zfb_am_mix);
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rbmk_fuel_drx, 1), new Object[] { ModItems.rbmk_fuel_balefire, ModItems.particle_digamma });

		//Rod recycling
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_australium, 6), new Object[] { ModItems.rod_australium });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_euphemium, 6), new Object[] { ModItems.rod_euphemium });

		//Waste rod recycling
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.billet_nuclear_waste, 1), new Object[] { ModItems.rod_waste });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.billet_nuclear_waste, 2), new Object[] { ModItems.rod_dual_waste });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.billet_nuclear_waste, 4), new Object[] { ModItems.rod_quad_waste });

		//Depleted fuel recycling
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 1), new Object[] { ModItems.rod_uranium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 2), new Object[] { ModItems.rod_dual_uranium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_uranium_hot, 4), new Object[] { ModItems.rod_quad_uranium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 1), new Object[] { ModItems.rod_thorium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 2), new Object[] { ModItems.rod_dual_thorium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_thorium_hot, 4), new Object[] { ModItems.rod_quad_thorium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 1), new Object[] { ModItems.rod_plutonium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 2), new Object[] { ModItems.rod_dual_plutonium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_plutonium_hot, 4), new Object[] { ModItems.rod_quad_plutonium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 1), new Object[] { ModItems.rod_mox_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 2), new Object[] { ModItems.rod_dual_mox_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_mox_hot, 4), new Object[] { ModItems.rod_quad_mox_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 1), new Object[] { ModItems.rod_schrabidium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 2), new Object[] { ModItems.rod_dual_schrabidium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_schrabidium_hot, 4), new Object[] { ModItems.rod_quad_schrabidium_fuel_depleted });
	}
	
	//Fill rods with one billet. For fuels only, therefore no unloading or ore dict
	public static void addFuelRodBillet(Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_empty, billet });
	}
	
	//Fill rods with two billets
	public static void addDualFuelRodBillet(Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, billet, billet });
	}
	
	//Fill rods with three billets
	public static void addQuadFuelRodBillet(Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, billet, billet, billet, billet });
	}
	
	//Fill rods with one billet + unload
	public static void addRodBilletUnload(Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_empty, billet });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 1), new Object[] { out });
	}
	public static void addRodBilletUnload(DictFrame mat, Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_empty, mat.billet() });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 1), new Object[] { out });
	}
	
	//Fill rods with two billets + unload
	public static void addDualRodBilletUnload(Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, billet, billet });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 2), new Object[] { out });
	}
	public static void addDualRodBilletUnload(DictFrame mat, Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, mat.billet(), mat.billet() });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 2), new Object[] { out });
	}
	
	//Fill rods with three billets + unload
	public static void addQuadRodBilletUnload(Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, billet, billet, billet, billet });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 4), new Object[] { out });
	}
	public static void addQuadRodBilletUnload(DictFrame mat, Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, mat.billet(), mat.billet(), mat.billet(), mat.billet() });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 4), new Object[] { out });
	}
	
	//Fill rods with 8 billets
	public static void addRBMKRod(DictFrame mat, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rbmk_fuel_empty, mat.billet(), mat.billet(), mat.billet(), mat.billet(), mat.billet(), mat.billet(), mat.billet(), mat.billet() });
	}
	public static void addRBMKRod(Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rbmk_fuel_empty, billet, billet, billet, billet, billet, billet, billet, billet });
	}
}
