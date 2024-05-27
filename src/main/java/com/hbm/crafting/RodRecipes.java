package com.hbm.crafting;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.*;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.main.CraftingManager;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.stats.StatList;

/**
 * For the loading and unloading of fuel rods
 * @author hbm
 */
public class RodRecipes {
	
	public static void register() {
		
		//Zirnox Fuel
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.rod_zirnox_empty, 4), new Object[] { "Z Z", "ZBZ", "Z Z", 'Z', ZR.nugget(), 'B', BE.ingot() }));
		addZIRNOXRod(U, EnumZirnoxType.NATURAL_URANIUM_FUEL);
		addZIRNOXRod(ModItems.billet_uranium_fuel, EnumZirnoxType.URANIUM_FUEL);
		addZIRNOXRod(TH232, EnumZirnoxType.TH232);
		addZIRNOXRod(ModItems.billet_thorium_fuel, EnumZirnoxType.THORIUM_FUEL);
		addZIRNOXRod(ModItems.billet_mox_fuel, EnumZirnoxType.MOX_FUEL);
		addZIRNOXRod(ModItems.billet_plutonium_fuel, EnumZirnoxType.PLUTONIUM_FUEL);
		addZIRNOXRod(U233, EnumZirnoxType.U233_FUEL);
		addZIRNOXRod(U235, EnumZirnoxType.U235_FUEL);
		addZIRNOXRod(ModItems.billet_les, EnumZirnoxType.LES_FUEL);
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_zirnox, 1, EnumZirnoxType.LITHIUM.ordinal()), new Object[] { ModItems.rod_zirnox_empty, LI.ingot(), LI.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_zirnox, 1, EnumZirnoxType.ZFB_MOX.ordinal()), new Object[] { ModItems.rod_zirnox_empty, ModItems.billet_mox_fuel, ZR.billet() });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_natural_uranium, 2, 1), new Object[] { ModItems.rod_zirnox_natural_uranium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_uranium, 2, 1), new Object[] { ModItems.rod_zirnox_uranium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_thorium, 2, 1), new Object[] { ModItems.rod_zirnox_thorium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_mox, 2, 1), new Object[] { ModItems.rod_zirnox_mox_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_plutonium, 2, 1), new Object[] { ModItems.rod_zirnox_plutonium_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_u233, 2, 1), new Object[] { ModItems.rod_zirnox_u233_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_u235, 2, 1), new Object[] { ModItems.rod_zirnox_u235_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_schrabidium, 2, 1), new Object[] { ModItems.rod_zirnox_les_fuel_depleted });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.waste_zfb_mox, 2, 1), new Object[] { ModItems.rod_zirnox_zfb_mox_depleted });
		
		//Breeding Rods
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.rod_empty, 16), new Object[] { "SSS", "L L", "SSS", 'S', STEEL.plate528(), 'L', PB.plate528() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_empty, 2), new Object[] { ModItems.rod_dual_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_empty, 4), new Object[] { ModItems.rod_quad_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty, ModItems.rod_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad_empty, 1), new Object[] { ModItems.rod_dual_empty, ModItems.rod_dual_empty });
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod, 1, BreedingRodType.LITHIUM.ordinal()), new Object[] { ModItems.rod_empty, LI.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 1), new Object[] { new ItemStack(ModItems.rod, 1, BreedingRodType.LITHIUM.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual, 1, BreedingRodType.LITHIUM.ordinal()), new Object[] { ModItems.rod_dual_empty, LI.ingot(), LI.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 2), new Object[] { new ItemStack(ModItems.rod_dual, 1, BreedingRodType.LITHIUM.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad, 1, BreedingRodType.LITHIUM.ordinal()), new Object[] { ModItems.rod_quad_empty, LI.ingot(), LI.ingot(), LI.ingot(), LI.ingot() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.lithium, 4), new Object[] { new ItemStack(ModItems.rod_quad, 1, BreedingRodType.LITHIUM.ordinal()) });
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell_tritium, 1), new Object[] { new ItemStack(ModItems.rod, 1, BreedingRodType.TRITIUM.ordinal()), ModItems.cell_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell_tritium, 2), new Object[] { new ItemStack(ModItems.rod_dual, 1, BreedingRodType.TRITIUM.ordinal()), ModItems.cell_empty, ModItems.cell_empty });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.cell_tritium, 4), new Object[] { new ItemStack(ModItems.rod_quad, 1, BreedingRodType.TRITIUM.ordinal()), ModItems.cell_empty, ModItems.cell_empty, ModItems.cell_empty, ModItems.cell_empty });

		addBreedingRod(CO, ModItems.billet_cobalt, BreedingRodType.CO);
		addBreedingRod(CO60, ModItems.billet_co60, BreedingRodType.CO60);
		addBreedingRod(RA226, ModItems.billet_ra226, BreedingRodType.RA226);
		addBreedingRod(AC227, ModItems.billet_actinium, BreedingRodType.AC227);
		addBreedingRod(TH232, ModItems.billet_th232, BreedingRodType.TH232);
		addBreedingRod(ModItems.billet_thorium_fuel, BreedingRodType.THF);
		addBreedingRod(U235, ModItems.billet_u235, BreedingRodType.U235);
		addBreedingRod(NP237, ModItems.billet_neptunium, BreedingRodType.NP237);
		addBreedingRod(U238, ModItems.billet_u238, BreedingRodType.U238);
		addBreedingRod(PU238, ModItems.billet_pu238, BreedingRodType.PU238);
		addBreedingRod(PU239, ModItems.billet_pu239, BreedingRodType.PU239);
		addBreedingRod(ModItems.billet_pu_mix, BreedingRodType.RGP);
		addBreedingRod(ModItems.billet_nuclear_waste, BreedingRodType.WASTE);
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod, 1, BreedingRodType.LEAD.ordinal()), new Object[] { ModItems.rod_empty, PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 6), new Object[] { new ItemStack(ModItems.rod, 1, BreedingRodType.LEAD.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual, 1, BreedingRodType.LEAD.ordinal()), new Object[] { ModItems.rod_dual_empty, PB.ingot(), PB.nugget(), PB.nugget(), PB.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 12), new Object[] { new ItemStack(ModItems.rod_dual, 1, BreedingRodType.LEAD.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad, 1, BreedingRodType.LEAD.ordinal()), new Object[] { ModItems.rod_quad_empty, PB.ingot(), PB.ingot(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget(), PB.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.nugget_lead, 24), new Object[] { new ItemStack(ModItems.rod_quad, 1, BreedingRodType.LEAD.ordinal()) });
		addBreedingRod(U, ModItems.billet_uranium, BreedingRodType.URANIUM);


		//Pile fuel
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pile_rod_uranium, 1), new Object[] { " U ", "PUP", " U ", 'P', IRON.plate(), 'U', U.billet() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pile_rod_source, 1), new Object[] { " U ", "PUP", " U ", 'P', IRON.plate(), 'U', ModItems.billet_ra226be });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pile_rod_boron, 1), new Object[] { " B ", " W ", " B ", 'B', B.ingot(), 'W', KEY_PLANKS });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.pile_rod_lithium, 1), new Object[] { ModItems.cell_empty, LI.ingot() });
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.pile_rod_detector, 1), new Object[] { " B ", "CM ", " B ", 'B', B.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'M', ModItems.motor });
		
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
		addRBMKRod(BK247, ModItems.rbmk_fuel_bk247);
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
		addRBMKRod(ModItems.billet_flashlead, ModItems.rbmk_fuel_flashlead);
		addRBMKRod(ModItems.billet_zfb_bismuth, ModItems.rbmk_fuel_zfb_bismuth);
		addRBMKRod(ModItems.billet_zfb_pu241, ModItems.rbmk_fuel_zfb_pu241);
		addRBMKRod(ModItems.billet_zfb_am_mix, ModItems.rbmk_fuel_zfb_am_mix);
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rbmk_fuel_drx, 1), new Object[] { ModItems.rbmk_fuel_balefire, ModItems.particle_digamma });
		addRBMKRod(ModItems.billet_cm_fuel, ModItems.rbmk_fuel_lecm);
		addRBMKRod(CMRG, ModItems.rbmk_fuel_mecm);
		addRBMKRod(CM245, ModItems.rbmk_fuel_hecm);
		
		addPellet(SA326,							EnumWatzType.SCHRABIDIUM);
		addPellet(ModItems.ingot_hes,				EnumWatzType.HES);
		addPellet(ModItems.ingot_schrabidium_fuel,	EnumWatzType.MES);
		addPellet(ModItems.ingot_les,				EnumWatzType.LES);
		addPellet(NP237,							EnumWatzType.HEN);
		addPellet(ModItems.ingot_uranium_fuel,		EnumWatzType.MEU);
		addPellet(ModItems.ingot_pu_mix,			EnumWatzType.MEP);
		addPellet(PB,								EnumWatzType.LEAD);
		addPellet(B,								EnumWatzType.BORON);
		addPellet(U238,								EnumWatzType.DU);
		addPellet(PU241,							EnumWatzType.PU241);
		addPellet(AMF,								EnumWatzType.AMF);
		addPellet(AMRG,								EnumWatzType.AMRG);
		addPellet(CMRG,								EnumWatzType.CMRG);
		addPellet(CMF,								EnumWatzType.CMF);
		addPellet(BK247,							EnumWatzType.BK247);
		addPellet(CF252,							EnumWatzType.CF252);
		addPellet(CF251,							EnumWatzType.CF251);
		addPellet(ES253,							EnumWatzType.ES253);
		
		//PWR fuel
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.MEU), new Object[] { "F", "I", "F", 'F', ModItems.billet_uranium_fuel, 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.HEU233), new Object[] { "F", "I", "F", 'F', U233.billet(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.HEU235), new Object[] { "F", "I", "F", 'F', U235.billet(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.MEN), new Object[] { "F", "I", "F", 'F', ModItems.billet_neptunium_fuel, 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.HEN237), new Object[] { "F", "I", "F", 'F', NP237.billet(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.MOX), new Object[] { "F", "I", "F", 'F', ModItems.billet_mox_fuel, 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.MEP), new Object[] { "F", "I", "F", 'F', ModItems.billet_pu_mix, 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.HEP239), new Object[] { "F", "I", "F", 'F', PU239.billet(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.HEP241), new Object[] { "F", "I", "F", 'F', PU241.billet(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.MEA), new Object[] { "F", "I", "F", 'F', ModItems.billet_am_mix, 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.HEA242), new Object[] { "F", "I", "F", 'F', AM242.billet(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.HES326), new Object[] { "F", "I", "F", 'F', SA326.billet(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.HES327), new Object[] { "F", "I", "F", 'F', SA327.billet(), 'I', ModItems.plate_polymer });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.BFB_AM_MIX), new Object[] { "NFN", "NIN", "NBN", 'F', ModItems.billet_am_mix, 'I', ModItems.plate_polymer, 'B', BI.billet(), 'N', ModItems.nugget_plutonium_fuel });
		CraftingManager.addRecipeAuto(DictFrame.fromOne(ModItems.pwr_fuel, EnumPWRFuel.BFB_PU241), new Object[] { "NFN", "NIN", "NBN", 'F', PU241.billet(), 'I', ModItems.plate_polymer, 'B', BI.billet(), 'N', ModItems.nugget_uranium_fuel });

		CraftingManager.addRecipeAuto(new ItemStack(ModItems.icf_pellet_empty), new Object[] { "ZLZ", "L L", "ZLZ", 'Z', ZR.wireFine(), 'L', PB.wireFine() });
	}
	
	public static void registerInit() {
		/* GT6 */
		if(OreDictionary.doesOreNameExist("ingotNaquadah-Enriched"))	addPellet(new DictFrame("Naquadah-Enriched"),	EnumWatzType.NQD);
		if(OreDictionary.doesOreNameExist("ingotNaquadria"))			addPellet(new DictFrame("Naquadria"),			EnumWatzType.NQR);
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rbmk_fuel_drx, 1), new Object[] { ModItems.rbmk_fuel_balefire, ModItems.particle_digamma });

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
	
	/** Single, dual, quad rod loading + unloading **/
	public static void addBreedingRod(Item billet, BreedingRodType type) {
		addBreedingRodLoad(billet, type);
		addBreedingRodUnload(billet, type);
	}
	/** Single, dual, quad rod loading + unloading + oredict **/
	public static void addBreedingRod(DictFrame mat, Item billet, BreedingRodType type) {
		addBreedingRodLoad(mat, billet, type);
		addBreedingRodUnload(mat, billet, type);
	}
	
	/** Single, dual, quad rod loading **/
	public static void addBreedingRodLoad(Item billet, BreedingRodType type) {
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod, 1, type.ordinal()), new Object[] { ModItems.rod_empty, billet});
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual, 1, type.ordinal()), new Object[] { ModItems.rod_dual_empty, billet, billet});
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad, 1, type.ordinal()), new Object[] { ModItems.rod_quad_empty, billet, billet, billet, billet});
	}
	/** Single, dual, quad rod unloading **/
	public static void addBreedingRodUnload(Item billet, BreedingRodType type) {
		CraftingManager.addShapelessAuto(new ItemStack(billet, 1), new Object[] { new ItemStack(ModItems.rod, 1, type.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 2), new Object[] { new ItemStack(ModItems.rod_dual, 1, type.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 4), new Object[] { new ItemStack(ModItems.rod_quad, 1, type.ordinal()) });
	}
	/** Single, dual, quad rod loading with OreDict **/
	public static void addBreedingRodLoad(DictFrame mat, Item billet, BreedingRodType type) {
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod, 1, type.ordinal()), new Object[] { ModItems.rod_empty, mat.billet()});
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_dual, 1, type.ordinal()), new Object[] { ModItems.rod_dual_empty, mat.billet(), mat.billet()});
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_quad, 1, type.ordinal()), new Object[] { ModItems.rod_quad_empty, mat.billet(), mat.billet(), mat.billet(), mat.billet()});
	}
	/** Single, dual, quad rod unloading with OreDict **/
	public static void addBreedingRodUnload(DictFrame mat, Item billet, BreedingRodType type) {
		CraftingManager.addShapelessAuto(new ItemStack(billet, 1), new Object[] { new ItemStack(ModItems.rod, 1, type.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 2), new Object[] { new ItemStack(ModItems.rod_dual, 1, type.ordinal()) });
		CraftingManager.addShapelessAuto(new ItemStack(billet, 4), new Object[] { new ItemStack(ModItems.rod_quad, 1, type.ordinal()) });
	}
	
	//Fill rods with 8 billets
	public static void addRBMKRod(DictFrame mat, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rbmk_fuel_empty, mat.billet(), mat.billet(), mat.billet(), mat.billet(), mat.billet(), mat.billet(), mat.billet(), mat.billet() });
	}
	public static void addRBMKRod(Item billet, Item out) {
		CraftingManager.addShapelessAuto(new ItemStack(out), new Object[] { ModItems.rbmk_fuel_empty, billet, billet, billet, billet, billet, billet, billet, billet });
	}
	
	/** Fill ZIRNOX rod with two billets **/
	public static void addZIRNOXRod(Item billet, EnumZirnoxType num) {
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_zirnox, 1, num.ordinal()), new Object[] { ModItems.rod_zirnox_empty, billet, billet });
	}
	
	/** Fill ZIRNOX rod with two billets with OreDict **/
	public static void addZIRNOXRod(DictFrame mat, EnumZirnoxType num) {
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.rod_zirnox, 1, num.ordinal()), new Object[] { ModItems.rod_zirnox_empty, mat.billet(), mat.billet() });
	}
	
	/** Watz pellet crafting **/
	public static void addPellet(DictFrame mat, EnumWatzType num) {
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.watz_pellet, 1, num.ordinal()), new Object[] { " I ", "IGI", " I ", 'I', mat.ingot(), 'G', GRAPHITE.ingot() });
	}
	public static void addPellet(Item item, EnumWatzType num) {
		CraftingManager.addRecipeAuto(new ItemStack(ModItems.watz_pellet, 1, num.ordinal()), new Object[] { " I ", "IGI", " I ", 'I', item, 'G', GRAPHITE.ingot() });
	}
}
