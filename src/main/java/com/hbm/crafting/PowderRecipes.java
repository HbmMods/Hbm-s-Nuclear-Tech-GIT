package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * For recipes mostly involving or resulting in powder
 * @author hbm
 */
public class PowderRecipes {
	
	public static void register() {

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_power, 5), new Object[] { "dustRedstone", "dustGlowstone", "dustDiamond", "dustNeptunium", "dustMagnetizedTungsten" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ballistite, 3), new Object[] { Items.gunpowder, "dustSaltpeter", Items.sugar }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_steel_dusted, 1), new Object[] { "ingotSteel", "dustCoal" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_uranium, 9), "dustUranium235", "dustUranium238", "dustUranium238", "dustUranium238", "dustUranium238", "dustUranium238", "dustUranium238", "dustUranium238", "dustUranium238"));
		
		//Gunpowder
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 3), new Object[] { "dustSulfur", "dustSaltpeter", Items.coal }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.gunpowder, 3), new Object[] { "dustSulfur", "dustSaltpeter", new ItemStack(Items.coal, 1, 1) }));
		
		//Blends
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { "dustNeptunium", "dustIodine", "dustThorium", "dustAstatine", "dustNeodymium", "dustCaesium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_nitan_mix, 6), new Object[] { "dustStrontium", "dustCobalt", "dustBromine", "dustTennessine", "dustNiobium", "dustCerium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_spark_mix, 5), new Object[] { "dustDeshAlloy", "dustEuphemium", ModItems.powder_meteorite, ModItems.powder_power, ModItems.powder_nitan_mix }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_meteorite, 5), new Object[] { "dustIron", "dustCopper", "dustLithium", "dustTungsten", "dustUranium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_thermite, 4), new Object[] { "dustIron", "dustIron", "dustIron", "dustAluminum" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_semtex_mix, 3), new Object[] { ModItems.solid_fuel, ModItems.cordite, "dustSaltpeter" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_semtex_mix, 1), new Object[] { ModItems.solid_fuel, ModItems.ballistite, "dustSaltpeter" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.sand_quartz), new Object[] { "sand", "sand", "dustNetherQuartz", "dustNetherQuartz" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.orichalcum, 1, 3), new Object[] { ModItems.powder_spark_mix, ModItems.powder_spark_mix, ModItems.powder_spark_mix, "dustAustralium", "dustAustralium", ModItems.powder_combine_steel, ModItems.powder_combine_steel, ModItems.powder_nitan_mix , ModItems.powder_nitan_mix }));
		
		//Desh
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.powder_desh_mix, 1), new Object[] { ModItems.powder_actinium_tiny, ModItems.powder_actinium_tiny, ModItems.powder_lanthanium_tiny, ModItems.powder_lanthanium_tiny, ModItems.powder_cerium_tiny, ModItems.powder_cobalt_tiny, ModItems.powder_lithium_tiny, ModItems.powder_neodymium_tiny, ModItems.powder_niobium_tiny });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.powder_desh_mix, 9), new Object[] { ModItems.powder_actinium, ModItems.powder_actinium, ModItems.powder_lanthanium, ModItems.powder_lanthanium, ModItems.powder_cerium, ModItems.powder_cobalt, ModItems.powder_lithium, ModItems.powder_neodymium, ModItems.powder_niobium });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_desh_ready, 1), new Object[] { ModItems.powder_desh_mix, ModItems.nugget_mercury, ModItems.nugget_mercury, "dustCoal" }));

		//Metal powders
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 4), new Object[] { "dustRedstone", "dustIron", "dustCoal", "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 3), new Object[] { "dustIron", "dustCoal", "dustRedCopperAlloy" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 3), new Object[] { "dustRedstone", "dustSteel", "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_advanced_alloy, 2), new Object[] { "dustRedCopperAlloy", "dustSteel" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_magnetized_tungsten, 1), new Object[] { "dustTungsten", "nuggetSchrabidium" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_red_copper, 2), new Object[] { "dustRedstone", "dustCopper" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_steel, 2), new Object[] { "dustIron", "dustCoal" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { "dustSteel", "dustTungsten" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 2), new Object[] { "dustSteel", ModItems.powder_cobalt }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { "dustIron", "dustCoal", "dustTungsten", "dustTungsten" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_dura_steel, 4), new Object[] { "dustIron", "dustCoal", ModItems.powder_cobalt, ModItems.powder_cobalt }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.powder_nbbe), new Object[] { "dustNiobium", "dustBeryllium" }));
	}
}
