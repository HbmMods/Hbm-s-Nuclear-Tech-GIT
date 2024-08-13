package com.hbm.crafting;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.config.GeneralConfig;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemicalDye.EnumChemDye;
import com.hbm.items.machine.ItemScraps;
import com.hbm.main.CraftingManager;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * For recipes mostly involving or resulting in powder
 * @author hbm
 */
public class PowderRecipes {
	
	public static void register() {

		//Explosives
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ballistite, 3), new Object[] { Items.gunpowder, KNO.dust(), Items.sugar });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ball_dynamite, 2), new Object[] { KNO.dust(), Items.sugar, Blocks.sand, KEY_TOOL_CHEMISTRYSET });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ball_tnt, 4), new Object[] { Fluids.AROMATICS.getDict(1000), KNO.dust(), KEY_TOOL_CHEMISTRYSET });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ingot_c4, 4), new Object[] { Fluids.UNSATURATEDS.getDict(1000), KNO.dust(), KEY_TOOL_CHEMISTRYSET });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_semtex_mix, 3), new Object[] { ModItems.solid_fuel, ModItems.cordite, KNO.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_semtex_mix, 1), new Object[] { ModItems.solid_fuel, ModItems.ballistite, KNO.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(Items.clay_ball, 4), new Object[] { KEY_SAND, ModItems.dust, ModItems.dust, Fluids.WATER.getDict(1_000) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.clay_ball, 4), new Object[] { Blocks.clay }); //clay uncrafting because placing and breaking it isn't worth anyone's time
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_cement, 4), new Object[] { LIMESTONE.dust(), Items.clay_ball, Items.clay_ball, Items.clay_ball });
		
		//Other
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ingot_steel_dusted, 1), new Object[] { STEEL.ingot(), COAL.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_bakelite, 2), new Object[] { Fluids.AROMATICS.getDict(1000), Fluids.PETROLEUM.getDict(1000), KEY_TOOL_CHEMISTRYSET });

		//Gunpowder
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { S.dust(), KNO.dust(), COAL.gem() });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { S.dust(), KNO.dust(), new ItemStack(Items.coal, 1, 1) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { S.dust(), KNO.dust(), COAL.gem() });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { S.dust(), KNO.dust(), new ItemStack(Items.coal, 1, 1) });
		
		//Blends
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_power, 3), new Object[] { "dustGlowstone", DIAMOND.dust(), MAGTUNG.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CS.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { ST.dust(), CO.dust(), BR.dust(), TS.dust(), NB.dust(), CE.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_spark_mix, 3), new Object[] { DESH.dust(), EUPH.dust(), ModItems.powder_power });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_meteorite, 4), new Object[] { IRON.dust(), CU.dust(), LI.dust(), NETHERQUARTZ.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_thermite, 4), new Object[] { IRON.dust(), IRON.dust(), IRON.dust(), AL.dust() });
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_mix, 1), new Object[] { B.dustTiny(), B.dustTiny(), LA.dustTiny(), LA.dustTiny(), CE.dustTiny(), CO.dustTiny(), LI.dustTiny(), ND.dustTiny(), NB.dustTiny() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_mix, 9), new Object[] { B.dust(), B.dust(), LA.dust(), LA.dust(), CE.dust(), CO.dust(), LI.dust(), ND.dust(), NB.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_ready, 1), new Object[] { ModItems.powder_desh_mix, ModItems.ingot_mercury, ModItems.ingot_mercury, COAL.dust() });

		//Metal powders
		CraftingManager.addShapelessAuto(ItemScraps.create(new MaterialStack(Mats.MAT_MINGRADE, MaterialShapes.INGOT.q(2))), new Object[] { CU.dust(), REDSTONE.dust() });
		CraftingManager.addShapelessAuto(ItemScraps.create(new MaterialStack(Mats.MAT_MAGTUNG, MaterialShapes.INGOT.q(1))), new Object[] { W.dust(), SA326.nugget() });
		CraftingManager.addShapelessAuto(ItemScraps.create(new MaterialStack(Mats.MAT_TCALLOY, MaterialShapes.INGOT.q(1))), new Object[] { STEEL.dust(), TC99.nugget() });
		CraftingManager.addShapelessAuto(ItemScraps.create(new MaterialStack(Mats.MAT_STEEL, MaterialShapes.INGOT.q(1))), new Object[] { IRON.dust(), COAL.dust() });
		CraftingManager.addShapelessAuto(ItemScraps.create(new MaterialStack(Mats.MAT_STEEL, MaterialShapes.INGOT.q(4))), new Object[] { IRON.dust(), IRON.dust(), IRON.dust(), IRON.dust(), COAL.dust(), COAL.dust(), COAL.dust(), COAL.dust() });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 1), new Object[] { new ItemStack(Items.coal, 1, 1), KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 2), new Object[] { COAL.dust(), KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 4), new Object[] { F.dust(), KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 8), new Object[] { PB.dust(), S.dust(), KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 12), new Object[] { LIMESTONE.dust(), KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 12), new Object[] { CA.dust(), KEY_SAND });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_flux, 16), new Object[] { BORAX.dust(), KEY_SAND });

		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_fertilizer, 4), new Object[] { CA.dust(), P_RED.dust(), KNO.dust(), S.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_fertilizer, 4), new Object[] { ANY_ASH.any(), P_RED.dust(), KNO.dust(), S.dust() });

		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleCrafting) {
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { REDSTONE.dust(), IRON.dust(), COAL.dust(), CU.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { IRON.dust(), COAL.dust(), MINGRADE.dust(), MINGRADE.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { REDSTONE.dust(), CU.dust(), STEEL.dust(), STEEL.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 2), new Object[] { MINGRADE.dust(), STEEL.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_red_copper, 2), new Object[] { REDSTONE.dust(), CU.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { STEEL.dust(), W.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { STEEL.dust(), CO.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { IRON.dust(), COAL.dust(), W.dust(), W.dust() });
			CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { IRON.dust(), COAL.dust(), CO.dust(), CO.dust() });
			CraftingManager.addRecipeAuto(new ItemStack(ModItems.ingot_firebrick, 4), new Object[] { "BN", "NB", 'B', Items.brick, 'N', Items.netherbrick });
		}
		
		//Unleash the colores
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GRAY, 2),			new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLACK),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.SILVER, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GRAY),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.ORANGE, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.YELLOW) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.LIME, 2),			new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.CYAN, 2),			new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PURPLE, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BROWN, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.ORANGE),	DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLACK) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.MAGENTA, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PINK),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PURPLE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.LIGHTBLUE, 2),	new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.PINK, 2),			new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE) });
		CraftingManager.addShapelessAuto(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN, 2),		new Object[] { DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE),		DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.YELLOW) });
		
		for(int i = 0; i < 15; i++) CraftingManager.addShapelessAuto(new ItemStack(ModItems.crayon, 4, i), new Object[] { new ItemStack(ModItems.chemical_dye, 1, i), ANY_TAR.any(), Items.paper });

	}
}
