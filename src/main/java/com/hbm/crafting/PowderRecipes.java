package com.hbm.crafting;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
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
		
		//Other
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ingot_steel_dusted, 1), new Object[] { STEEL.ingot(), COAL.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_bakelite, 2), new Object[] { Fluids.AROMATICS.getDict(1000), Fluids.PETROLEUM.getDict(1000), KEY_TOOL_CHEMISTRYSET });

		//Gunpowder
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { S.dust(), KNO.dust(), COAL.gem() });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { S.dust(), KNO.dust(), new ItemStack(Items.coal, 1, 1) });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { S.dust(), KNO.dust(), COAL.gem() });
		CraftingManager.addShapelessAuto(new ItemStack(Items.gunpowder, 3), new Object[] { S.dust(), KNO.dust(), new ItemStack(Items.coal, 1, 1) });
		
		//Blends
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_power, 5), new Object[] { REDSTONE.dust(), "dustGlowstone", DIAMOND.dust(), NP237.dust(), MAGTUNG.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CS.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { ST.dust(), CO.dust(), BR.dust(), TS.dust(), NB.dust(), CE.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_spark_mix, 5), new Object[] { DESH.dust(), EUPH.dust(), ModItems.powder_meteorite, ModItems.powder_power, ModItems.powder_nitan_mix });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_meteorite, 5), new Object[] { FE.dust(), CU.dust(), LI.dust(), W.dust(), U.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_thermite, 4), new Object[] { FE.dust(), FE.dust(), FE.dust(), AL.dust() });
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_mix, 1), new Object[] { B.dustTiny(), B.dustTiny(), LA.dustTiny(), LA.dustTiny(), CE.dustTiny(), CO.dustTiny(), LI.dustTiny(), ND.dustTiny(), NB.dustTiny() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_mix, 9), new Object[] { B.dust(), B.dust(), LA.dust(), LA.dust(), CE.dust(), CO.dust(), LI.dust(), ND.dust(), NB.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_desh_ready, 1), new Object[] { ModItems.powder_desh_mix, ModItems.ingot_mercury, ModItems.ingot_mercury, COAL.dust() });

		//Metal powders
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { REDSTONE.dust(), IRON.dust(), COAL.dust(), CU.dust() });
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { IRON.dust(), COAL.dust(), MINGRADE.dust(), MINGRADE.dust() });
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { REDSTONE.dust(), CU.dust(), STEEL.dust(), STEEL.dust() });
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_advanced_alloy, 2), new Object[] { MINGRADE.dust(), STEEL.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_magnetized_tungsten, 1), new Object[] { W.dust(), SA326.nugget() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_tcalloy, 1), new Object[] { STEEL.dust(), TC99.nugget() });
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_red_copper, 2), new Object[] { REDSTONE.dust(), CU.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_steel, 1), new Object[] { FE.dust(), COAL.dust() });
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { STEEL.dust(), W.dust() });
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { STEEL.dust(), CO.dust() });
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { IRON.dust(), COAL.dust(), W.dust(), W.dust() });
		//CraftingManager.addShapelessAuto(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { IRON.dust(), COAL.dust(), CO.dust(), CO.dust() });
	}
}
