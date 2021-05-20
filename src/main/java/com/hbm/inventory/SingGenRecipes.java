package com.hbm.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;
@Spaghetti("Is there a more efficient way of doing this? Probably")
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
		addRecipe(new ItemStack(ModItems.black_hole, 1), new Item[] {(ModItems.crystal_xen), (ModItems.crystal_xen), (ModItems.crystal_xen), (ModItems.crystal_xen), (ModItems.crystal_xen), (ModItems.crystal_xen), (ModItems.crystal_xen), (ModItems.crystal_xen)}, ModItems.singularity, true, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.overfuse, 1), new Item[] {(ModItems.screwdriver), (ModItems.board_copper), (ModItems.powder_nitan_mix), (ModItems.powder_nitan_mix), (ModItems.powder_nitan_mix), (ModItems.powder_nitan_mix), (ModItems.powder_nitan_mix), (ModItems.powder_nitan_mix)}, ModItems.black_hole, false, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.overfuse, 1), new Item[] {(ModItems.screwdriver), (ModItems.board_copper), (ModItems.powder_neptunium), (ModItems.powder_iodine), (ModItems.powder_thorium), (ModItems.powder_astatine), (ModItems.powder_neodymium), (ModItems.powder_caesium)}, ModItems.black_hole, false, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.overfuse, 1), new Item[] {(ModItems.screwdriver), (ModItems.board_copper), (ModItems.powder_strontium), (ModItems.powder_bromine), (ModItems.powder_cobalt), (ModItems.powder_tennessine), (ModItems.powder_niobium), (ModItems.powder_cerium)}, ModItems.black_hole, false, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.singularity_spark, 1), new Item[] {(ModItems.plate_dineutronium), (ModItems.singularity_super_heated), (ModItems.plate_dineutronium), (ModItems.singularity_counter_resonant), (ModItems.singularity_counter_resonant), (ModItems.plate_dineutronium), (ModItems.singularity_super_heated), (ModItems.plate_dineutronium)}, ModItems.black_hole, true, false, FluidType.NONE, 0);
		addRecipe(new ItemStack(ModItems.singularity_spark, 1), new Item[] {(ModItems.plate_dineutronium), (ModItems.singularity_counter_resonant), (ModItems.plate_dineutronium), (ModItems.singularity_super_heated), (ModItems.singularity_super_heated), (ModItems.plate_dineutronium), (ModItems.singularity_counter_resonant), (ModItems.plate_dineutronium)}, ModItems.black_hole, true, false, FluidType.NONE, 0);
		commonFluid(new ItemStack(ModItems.ams_core_sing, 1), ModItems.plate_euphemium, FluidType.ASCHRAB, ModItems.singularity, 4000);
		commonStandard(new ItemStack(ModItems.ams_core_wormhole, 1), ModItems.plate_dineutronium, ModItems.powder_spark_mix, ModItems.singularity);
		commonFluid(new ItemStack(ModItems.ams_core_eyeofharmony, 1), ModItems.plate_dalekanium, FluidType.LAVA, ModItems.black_hole, 64000);
		addRecipe(new ItemStack(ModItems.singularity_micro, 8), new Item[] {(ModItems.nugget_euphemium), null, (ModItems.nugget_euphemium), null, null, (ModItems.nugget_euphemium), null, (ModItems.nugget_euphemium)}, ModItems.ingot_schrabidium, true, true, FluidType.ASCHRAB, 1000);
		addRecipe(new ItemStack(ModItems.ingot_dineutronium, 1), new Item[] {Item.getItemFromBlock(ModBlocks.block_starmetal), (ModItems.powder_spark_mix), Item.getItemFromBlock(ModBlocks.block_starmetal), (ModItems.powder_spark_mix), (ModItems.powder_spark_mix), Item.getItemFromBlock(ModBlocks.block_starmetal), (ModItems.powder_spark_mix), Item.getItemFromBlock(ModBlocks.block_starmetal)}, Item.getItemFromBlock(ModBlocks.block_schrabidate), true, false, FluidType.ASCHRAB, 4000);
	}
	/**
	 * Standard recipe for upgrading singularities
	 * @param output - The resultant item output
	 * @param containmentItem - Item used for upgrading containment, goes in the corners
	 * @param modifierItem - Item used for modifying the item, goes in the sides
	 * @param centerItem - Item to be upgraded, goes in the center
	 */
	public static void commonStandard(ItemStack output, Item containmentItem, Item modifierItem, Item centerItem)
	{
		addRecipe(output, new Item[] {containmentItem, modifierItem, containmentItem, modifierItem, modifierItem, containmentItem,  modifierItem, containmentItem}, centerItem, true, false, FluidType.NONE, 0);
	}
	/**
	 * Standard recipe type
	 * @param output - The resultant item
	 * @param containmentItem - Item used for containment of the item, goes in the corners
	 * @param fluid - The fluid type used, must be Antischrabidium or lava 
	 * @param centerItem - The item that goes in the center
	 * @param fluidAmount - The amount of fluid needed, max 64b
	 */
	public static void commonFluid(ItemStack output, Item containmentItem, FluidType fluid, Item centerItem, int fluidAmount)
	{
		addRecipe(output, new Item[] {containmentItem, null, containmentItem, null, null, containmentItem, null, containmentItem}, centerItem, true, false, fluid, fluidAmount);
	}
	/**
	 * Add a recipe directly
	 * @param output - The output, may be more than 1
	 * @param inputRing - The ring of 8 items around the center
	 * @param inputCenter - The center item
	 * @param shaped - Does placement of items in the ring matter?
	 * @param keepRing - Should it not consume the items in the ring?
	 * @param fluid - Either Antischrabidium or Lava
	 * @param fluidAmount - Amount of fluid needed, max 64b
	 */
	public static void addRecipe(ItemStack output, Item[] inputRing, Item inputCenter, boolean shaped, boolean keepRing, FluidType fluid, int fluidAmount)
	{
		// Error checking
		if (inputRing.length != 8)
			throw new IllegalArgumentException("Recipe ring input must be exactly 8! Recipe output: " + output.getItem().getUnlocalizedName() + "; Ring length: " + inputRing.length);// So we know which recipe errored and why
		if (!(fluid.equals(FluidType.ASCHRAB) || fluid.equals(FluidType.LAVA) || fluid.equals(FluidType.NONE)))
			throw new IllegalArgumentException("Recipe fluid input must be either antischrabidium, lava, or none! Recipe output: " + output.getItem().getUnlocalizedName() + "; Fluid attempted: " + fluid.getUnlocalizedName());
		if (fluidAmount > 64000 || fluidAmount < 0)
			throw new IndexOutOfBoundsException("Recipe fluid input amount out of bounds, must be between 0 and 64000! Recipe output: " + output.getItem().getUnlocalizedName() + "; Fluid amount attempted: " + fluidAmount);
		
		recipes.add(new SingGenRecipe(output, inputRing, inputCenter, shaped, keepRing, fluid, fluidAmount));
	}
	/**
	 * Converts a list of item IDs to their item
	 * @param ringIn - The list of IDs to be converted
	 * @return The list of items
	 */
	public static List<Item> getRingItems(List<Integer> ringIn)
	{
		List<Item> ringItems = new ArrayList<Item>();
		for (int itemIn : ringIn)
		{
			if (itemIn != -1)
			{
				ringItems.add(Item.getItemById(itemIn));
			}
			else
			{
				ringItems.add(null);
			}
		}
		return ringItems;
	}
	/**
	 * Converts an array of items to their ID
	 * @param ringIn - The array of items 
	 * @return The list of IDs
	 */
	public static List<Integer> getRingIDs(Item[] ringIn)
	{
		List<Integer> ringIDs = new ArrayList<Integer>();
		for (Item itemIn : ringIn)
		{
			if (itemIn != null)
			{
				ringIDs.add(Item.getIdFromItem(itemIn));
			}
			else
			{
				ringIDs.add(-1);
			}
		}
		return ringIDs;
	}
	/**
	 * Check if the ring in the currently checked recipe matches what's in the machine
	 * @param machineRing - The ring in the machine
	 * @param recipeRing - The ring in the recipe
	 * @param shaped - Is it shaped?
	 * @return If it matches or not (boolean)
	 */
	public static boolean doRingsMatch(Item[] machineRing, Item[] recipeRing, boolean shaped)
	{
		for (int i = 0; i < 8; i++)
		{
			List<Integer> machineRingIDs = getRingIDs(machineRing);
			List<Integer> recipeRingIDs = getRingIDs(recipeRing);

			if (!shaped)
			{
				Collections.sort(machineRingIDs);
				Collections.sort(recipeRingIDs);
			}
			if (shaped)
			{
				if (machineRing[i] != recipeRing[i])
				{
					return false;
				}
			}
			else
			{
				if (Item.getItemById(machineRingIDs.get(i)) != Item.getItemById(recipeRingIDs.get(i)))
				{
					return false;
				}
			}
		}
		return true;
	}
	public static boolean doRingsMatchDebugMode(Item[] machineRing, Item[] recipeRing, boolean shaped)
	{
		for (int i = 0; i < 8; i++)
		{
			String machineInString;
			String recipeInString;
			Item[] machineRingTest = machineRing;
			Item[] recipeRingTest = recipeRing;
			List<Integer> machineRingIDs = getRingIDs(machineRingTest);
			List<Integer> recipeRingIDs = getRingIDs(recipeRingTest);
			if (!shaped)
			{
				Collections.sort(machineRingIDs);
				Collections.sort(recipeRingIDs);
			}
			if (machineRingIDs.get(i) == -1)
			{
				machineInString = "<empty>";
			}
			else
			{
				machineInString = machineRingTest[i].getUnlocalizedName();
			}
			if (recipeRingIDs.get(i) == -1)
			{
				recipeInString = "<empty>";
			}
			else
			{
				recipeInString = recipeRingTest[i].getUnlocalizedName();
			}
			System.out.println(String.format("Index #%s; Machine in: %s; Recipe in: %s", i, machineInString, recipeInString));
			if (shaped)
			{
				if (recipeRingTest[i] == machineRingTest[i])
				{
					System.out.println("Pair matches!");
				}
				else
				{
					System.out.println("Pair doesn't match");
				}
			}
			else
			{
				if (Item.getItemById(machineRingIDs.get(i)) == Item.getItemById(recipeRingIDs.get(i)))
				{
					System.out.println("Pair matches!");
				}
				else
				{
					System.out.println("Pair doesn't match");
				}
			}
		}
		return false;
	}
	public static class SingGenRecipe
	{
		public ItemStack output;
		public Item[] inputRing;
		public Item inputCenter;
		public boolean shaped;
		public boolean keepRing;
		public FluidType fluid;
		public int fluidAmount;
		
		public SingGenRecipe(ItemStack output, Item[] inputRing, Item inputCenter, boolean shaped, boolean keepRing, FluidType fluid, int fluidAmount)
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
