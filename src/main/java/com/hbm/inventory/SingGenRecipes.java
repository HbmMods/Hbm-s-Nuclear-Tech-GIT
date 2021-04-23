package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SingGenRecipes
{
	public static List<SingGenRecipe> recipes = new ArrayList<SingGenRecipes.SingGenRecipe>();

	/* -- Input Slots --
	 * 5  9  6
	 * 11 4  12
	 * 7  10 8
	 */
	// Output:{<Item(s) out>}, Input:{<corner>, <side>, <corner>, <side>, <side>, <corner>, <side>, <corner>, <center>, Shaped?, Keep shell?, Fluid, Fluid amount}
	public static void register()
	{
		commonFluid(new ItemStack(ModItems.singularity, 1), ModItems.nugget_euphemium, FluidType.ASCHRAB, Item.getItemFromBlock(ModBlocks.block_schrabidium), 4000);
		commonStandard(new ItemStack(ModItems.singularity_super_heated, 1), ModItems.plate_advanced_alloy, ModItems.powder_power, ModItems.singularity);
		commonStandard(new ItemStack(ModItems.singularity_counter_resonant, 1), ModItems.plate_combine_steel, ModItems.ingot_magnetized_tungsten, ModItems.singularity);
		addRecipe(new ItemStack(ModItems.black_hole, 1), new AStack[] {new ComparableStack(ModItems.crystal_xen), new ComparableStack(ModItems.crystal_xen), new ComparableStack(ModItems.crystal_xen), new ComparableStack(ModItems.crystal_xen), new ComparableStack(ModItems.crystal_xen), new ComparableStack(ModItems.crystal_xen), new ComparableStack(ModItems.crystal_xen), new ComparableStack(ModItems.crystal_xen)}, ModItems.singularity, true, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.overfuse, 1), new AStack[] {new ComparableStack(ModItems.screwdriver), new ComparableStack(ModItems.board_copper), new ComparableStack(ModItems.powder_nitan_mix), new ComparableStack(ModItems.powder_nitan_mix), new ComparableStack(ModItems.powder_nitan_mix), new ComparableStack(ModItems.powder_nitan_mix), new ComparableStack(ModItems.powder_nitan_mix)}, ModItems.black_hole, false, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.overfuse, 1), new AStack[] {new ComparableStack(ModItems.screwdriver), new ComparableStack(ModItems.board_copper), new ComparableStack(ModItems.powder_neptunium), new ComparableStack(ModItems.powder_iodine), new ComparableStack(ModItems.powder_thorium), new ComparableStack(ModItems.powder_astatine), new ComparableStack(ModItems.powder_neodymium), new ComparableStack(ModItems.powder_caesium)}, ModItems.black_hole, false, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.overfuse, 1), new AStack[] {new ComparableStack(ModItems.screwdriver), new ComparableStack(ModItems.board_copper), new ComparableStack(ModItems.powder_strontium), new ComparableStack(ModItems.powder_bromine), new ComparableStack(ModItems.powder_cobalt), new ComparableStack(ModItems.powder_tennessine), new ComparableStack(ModItems.powder_niobium), new ComparableStack(ModItems.powder_cerium)}, ModItems.black_hole, false, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.singularity_spark, 1), new AStack[] {new ComparableStack(ModItems.plate_dineutronium), new ComparableStack(ModItems.singularity_super_heated), new ComparableStack(ModItems.plate_dineutronium), new ComparableStack(ModItems.singularity_counter_resonant), new ComparableStack(ModItems.singularity_counter_resonant), new ComparableStack(ModItems.plate_dineutronium), new ComparableStack(ModItems.singularity_super_heated), new ComparableStack(ModItems.plate_dineutronium)}, ModItems.black_hole, true, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.singularity_spark, 1), new AStack[] {new ComparableStack(ModItems.plate_dineutronium), new ComparableStack(ModItems.singularity_counter_resonant), new ComparableStack(ModItems.plate_dineutronium), new ComparableStack(ModItems.singularity_super_heated), new ComparableStack(ModItems.singularity_super_heated), new ComparableStack(ModItems.plate_dineutronium), new ComparableStack(ModItems.singularity_counter_resonant), new ComparableStack(ModItems.plate_dineutronium)}, ModItems.black_hole, true, false, FluidType.NONE, 0);
		commonFluid(new ItemStack(ModItems.ams_core_sing, 1), ModItems.plate_euphemium, FluidType.ASCHRAB, ModItems.singularity, 4000);
		commonStandard(new ItemStack(ModItems.ams_core_wormhole, 1), ModItems.plate_dineutronium, ModItems.powder_spark_mix, ModItems.singularity);
		commonFluid(new ItemStack(ModItems.ams_core_eyeofharmony, 1), ModItems.plate_dalekanium, FluidType.LAVA, ModItems.black_hole, 64000);
		addRecipe(new ItemStack(ModItems.singularity_micro, 8), new AStack[] {new ComparableStack(ModItems.nugget_euphemium), null, new ComparableStack(ModItems.nugget_euphemium), null, null, new ComparableStack(ModItems.nugget_euphemium), null, new ComparableStack(ModItems.nugget_euphemium)}, ModItems.billet_schrabidium, true, true, FluidType.ASCHRAB, 1000);
		addRecipe(new ItemStack(ModItems.ingot_dineutronium, 1), new AStack[] {new ComparableStack(ModBlocks.block_starmetal), new ComparableStack(ModItems.powder_spark_mix), new ComparableStack(ModBlocks.block_starmetal), new ComparableStack(ModItems.powder_spark_mix), new ComparableStack(ModItems.powder_spark_mix), new ComparableStack(ModBlocks.block_starmetal), new ComparableStack(ModItems.powder_spark_mix), new ComparableStack(ModBlocks.block_starmetal)}, Item.getItemFromBlock(ModBlocks.block_schrabidate), true, false, FluidType.ASCHRAB, 4000);
	}
	public static void commonStandard(ItemStack output, Item containmentItem, Item modifierItem, Item centerItem)
	{
		addRecipe(output, new AStack[] {new ComparableStack(containmentItem), new ComparableStack(modifierItem), new ComparableStack(containmentItem), new ComparableStack(modifierItem), new ComparableStack(modifierItem), new ComparableStack(containmentItem), new ComparableStack (modifierItem), new ComparableStack(containmentItem)}, centerItem, true, false, FluidType.NONE, 0);
	}
	public static void commonFluid(ItemStack output, Item containmentItem, FluidType fluid, Item centerItem, int fluidAmount)
	{
		addRecipe(output, new AStack[] {new ComparableStack(containmentItem), null, new ComparableStack(containmentItem), null, null, new ComparableStack(containmentItem), null, new ComparableStack(containmentItem)}, centerItem, true, false, FluidType.NONE, 0);
	}
	public static void addRecipe(ItemStack output, AStack[] inputRing, Item inputCenter, boolean shaped, boolean keepRing, FluidType fluid, int fluidAmount)
	{
		recipes.add(new SingGenRecipe(output, inputRing, inputCenter, shaped, keepRing, fluid, fluidAmount));
	}
	public int getIntFromItem(Item itemIn)
	{
		return itemIn.hashCode();
	}
	public static class SingGenRecipe
	{
		public ItemStack output;
		public AStack[] inputRing;
		public Item inputCenter;
		public boolean shaped;
		public boolean keepRing;
		public FluidType fluid;
		public int fluidAmount;
		
		public SingGenRecipe(ItemStack output, AStack[] inputRing, Item inputCenter, boolean shaped, boolean keepRing, FluidType fluid, int fluidAmount)
		{
			this.output = output;
			this.inputRing = inputRing;
			this.inputCenter = inputCenter;
			this.shaped = shaped;
			this.keepRing = keepRing;
			this.fluid = fluid;
			this.fluidAmount = fluidAmount;
		}
		public ItemStack getOutput()
		{
			return output.copy();
		}
	}
}
