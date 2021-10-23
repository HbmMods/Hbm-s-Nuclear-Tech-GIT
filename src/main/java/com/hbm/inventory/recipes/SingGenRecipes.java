package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnegative;

import com.google.common.collect.Lists;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;
@Spaghetti("Is there a more efficient way of doing this? Probably")
public class SingGenRecipes
{
	public static final ArrayList<SingGenRecipe> recipes = new ArrayList<SingGenRecipes.SingGenRecipe>();
	/* -- Input Slots --
	 * 5  9  6
	 * 11 4  12
	 * 7  10 8
	 */
	// Output:{<Item(s) out>}, Input:{<corner>, <side>, <corner>, <side>, <side>, <corner>, <side>, <corner>, <center>, Shaped?, Keep shell?, Fluid, Fluid amount}
	public static void register()
	{
		recipes.add(commonFluid(new ItemStack(ModItems.singularity, 1), ModItems.nugget_euphemium, FluidType.ASCHRAB, new ComparableStack(ModBlocks.block_schrabidium), 4000).setLevel(1));
		recipes.add(commonStandard(new ItemStack(ModItems.singularity_super_heated, 1), ModItems.plate_advanced_alloy, ModItems.powder_power, new ComparableStack(ModItems.singularity)).setLevel(2));
		recipes.add(commonStandard(new ItemStack(ModItems.singularity_counter_resonant, 1), ModItems.plate_combine_steel, ModItems.ingot_magnetized_tungsten, new ComparableStack(ModItems.singularity)).setLevel(3));
		recipes.add(addRecipe(new ItemStack(ModItems.black_hole, 1), new ItemStack[]
				{
					new ItemStack(ModItems.crystal_xen), new ItemStack(ModItems.crystal_xen), new ItemStack(ModItems.crystal_xen),
					new ItemStack(ModItems.crystal_xen), new ItemStack(ModItems.crystal_xen),
					new ItemStack(ModItems.crystal_xen), new ItemStack(ModItems.crystal_xen), new ItemStack(ModItems.crystal_xen)
				}, new ComparableStack(ModItems.singularity), true, false, FluidType.NONE, 0).setLevel(4));
		recipes.add(addRecipe(new ItemStack(ModItems.overfuse, 1), new ItemStack[]
				{
					new ItemStack(ModItems.screwdriver_chad), new ItemStack(ModItems.board_copper), new ItemStack(ModItems.powder_nitan_mix),
					new ItemStack(ModItems.powder_nitan_mix), new ItemStack(ModItems.powder_nitan_mix),
					new ItemStack(ModItems.powder_nitan_mix), new ItemStack(ModItems.powder_nitan_mix), new ItemStack(ModItems.powder_nitan_mix)
				}, new ComparableStack(ModItems.black_hole), false, false, FluidType.NONE, 0).setLevel(5));
		recipes.add(addRecipe(new ItemStack(ModItems.overfuse, 1), new ItemStack[]
				{
					new ItemStack(ModItems.screwdriver_chad), new ItemStack(ModItems.board_copper), new ItemStack(ModItems.powder_neptunium),
					new ItemStack(ModItems.powder_iodine), new ItemStack(ModItems.powder_thorium),
					new ItemStack(ModItems.powder_astatine), new ItemStack(ModItems.powder_neodymium), new ItemStack(ModItems.powder_caesium)
				}, new ComparableStack(ModItems.black_hole), false, false, FluidType.NONE, 0).setLevel(5));
		recipes.add(addRecipe(new ItemStack(ModItems.overfuse, 1), new ItemStack[]
				{
					new ItemStack(ModItems.screwdriver_chad), new ItemStack(ModItems.board_copper), new ItemStack(ModItems.powder_strontium),
					new ItemStack(ModItems.powder_bromine), new ItemStack(ModItems.powder_cobalt),
					new ItemStack(ModItems.powder_tennessine), new ItemStack(ModItems.powder_niobium), new ItemStack(ModItems.powder_cerium)
				}, new ComparableStack(ModItems.black_hole), false, false, FluidType.NONE, 0).setLevel(5));
		recipes.add(addRecipe(new ItemStack(ModItems.singularity_spark, 1), new ItemStack[]
				{
					new ItemStack(ModItems.plate_dineutronium), new ItemStack(ModItems.singularity_super_heated), new ItemStack(ModItems.plate_dineutronium),
					new ItemStack(ModItems.singularity_counter_resonant), new ItemStack(ModItems.singularity_counter_resonant),
					new ItemStack(ModItems.plate_dineutronium), new ItemStack(ModItems.singularity_super_heated), new ItemStack(ModItems.plate_dineutronium)
				}, new ComparableStack(ModItems.black_hole), true, false, FluidType.NONE, 0).setLevel(5));
		recipes.add(addRecipe(new ItemStack(ModItems.singularity_spark, 1), new ItemStack[]
				{
					new ItemStack(ModItems.plate_dineutronium), new ItemStack(ModItems.singularity_counter_resonant), new ItemStack(ModItems.plate_dineutronium),
					new ItemStack(ModItems.singularity_super_heated), new ItemStack(ModItems.singularity_super_heated),
					new ItemStack(ModItems.plate_dineutronium), new ItemStack(ModItems.singularity_counter_resonant), new ItemStack(ModItems.plate_dineutronium)
				}, new ComparableStack(ModItems.black_hole), true, false, FluidType.NONE, 0).setLevel(5));
		recipes.add(commonFluid(new ItemStack(ModItems.ams_core_sing, 1), ModItems.plate_euphemium, FluidType.ASCHRAB, new ComparableStack(ModItems.singularity), 4000).setLevel(5));
		recipes.add(commonStandard(new ItemStack(ModItems.ams_core_wormhole, 1), ModItems.plate_dineutronium, ModItems.powder_spark_mix, new ComparableStack(ModItems.singularity)).setLevel(5));
		recipes.add(commonFluid(new ItemStack(ModItems.ams_core_eyeofharmony, 1), ModItems.plate_dalekanium, FluidType.PLASMA_WARP, new ComparableStack(ModItems.black_hole), 64000).setLevel(5));
		recipes.add(addRecipe(new ItemStack(ModItems.singularity_micro, 8), new ItemStack[]
				{
					new ItemStack(ModItems.nugget_euphemium), new ItemStack(ModItems.catalyst_rare), new ItemStack(ModItems.nugget_euphemium),
					new ItemStack(ModItems.catalyst_rare), new ItemStack(ModItems.catalyst_rare),
					new ItemStack(ModItems.nugget_euphemium), new ItemStack(ModItems.catalyst_rare), new ItemStack(ModItems.nugget_euphemium)
				}, new ComparableStack(ModItems.ingot_schrabidium), true, true, FluidType.ASCHRAB, 1000));
		recipes.add(addRecipe(new ItemStack(ModItems.ingot_dineutronium, 1), new ItemStack[]
				{
					new ItemStack(ModBlocks.block_starmetal), new ItemStack(ModItems.powder_spark_mix), new ItemStack(ModBlocks.block_starmetal),
					new ItemStack(ModItems.powder_spark_mix), new ItemStack(ModItems.powder_spark_mix),
					new ItemStack(ModBlocks.block_starmetal), new ItemStack(ModItems.powder_spark_mix), new ItemStack(ModBlocks.block_starmetal)
				}, new ComparableStack(ModBlocks.block_schrabidate), true, false, FluidType.ASCHRAB, 4000).setLevel(1));
		recipes.add(commonStandard(ItemBattery.getEmptyBattery(ModItems.battery_gun_advanced), ModItems.ingot_schrabidate, ModItems.powder_tennessine, new ComparableStack(ModItems.battery_gun_enhanced)).setLevel(2));
		recipes.add(addRecipe(ItemBattery.getEmptyBattery(ModItems.battery_gun_elite), new ItemStack[]
				{
					new ItemStack(ModItems.powder_spark_mix), new ItemStack(ModItems.plate_dineutronium), new ItemStack(ModItems.powder_spark_mix),
					new ItemStack(ModItems.plate_euphemium), new ItemStack(ModItems.plate_euphemium),
					new ItemStack(ModItems.powder_spark_mix), new ItemStack(ModItems.plate_dineutronium), new ItemStack(ModItems.powder_spark_mix)
				}, new ComparableStack(ModItems.battery_gun_advanced), true, false, FluidType.ASCHRAB, 2000).setLevel(3));
		recipes.add(addRecipe(ItemBattery.getEmptyBattery(ModItems.battery_gun_elite), new ItemStack[]
				{
					new ItemStack(ModItems.powder_spark_mix), new ItemStack(ModItems.plate_euphemium), new ItemStack(ModItems.powder_spark_mix),
					new ItemStack(ModItems.plate_dineutronium), new ItemStack(ModItems.plate_dineutronium),
					new ItemStack(ModItems.powder_spark_mix), new ItemStack(ModItems.plate_euphemium), new ItemStack(ModItems.powder_spark_mix)
				}, new ComparableStack(ModItems.battery_gun_advanced), true, false, FluidType.ASCHRAB, 2000).setLevel(3));
		recipes.add(addRecipe(new ItemStack(ModItems.orichalcum, 4, 5), new ItemStack[]
				{
						new ItemStack(ModItems.billet_yharonite), new ItemStack(ModItems.orichalcum, 1, 4), new ItemStack(ModItems.billet_yharonite),
						new ItemStack(ModItems.orichalcum, 1, 4), new ItemStack(ModItems.orichalcum, 1, 4),
						new ItemStack(ModItems.billet_yharonite), new ItemStack(ModItems.orichalcum, 1, 4), new ItemStack(ModItems.billet_yharonite)
				}, new OreDictStack("blockAustralium"), true, false, FluidType.PLASMA_WARP, 32000).setLevel(4));
		recipes.add(addRecipe(new ItemStack(ModItems.plate_armor_orichalcum), new ItemStack[]
				{
						new ItemStack(ModItems.orichalcum, 1, 1), new ItemStack(ModItems.orichalcum, 1, 5), new ItemStack(ModItems.orichalcum, 1, 1),
						new ItemStack(ModItems.orichalcum, 1, 5), new ItemStack(ModItems.orichalcum, 1, 5),
						new ItemStack(ModItems.orichalcum, 1, 1), new ItemStack(ModItems.orichalcum, 1, 5), new ItemStack(ModItems.orichalcum, 1, 1)
				}, new ComparableStack(ModItems.orichalcum, 1, 0), true, false, FluidType.ASCHRAB, 4000).setLevel(5));
		recipes.add(addRecipe(new ItemStack(ModItems.orichalcum, 1, 1), new ItemStack[]
				{
						new ItemStack(ModItems.plate_paa), new ItemStack(ModItems.orichalcum, 1, 6), new ItemStack(ModItems.plate_paa),
						new ItemStack(ModItems.orichalcum, 1, 6), new ItemStack(ModItems.orichalcum, 1, 6),
						new ItemStack(ModItems.plate_paa), new ItemStack(ModItems.orichalcum, 1, 6), new ItemStack(ModItems.plate_paa)
				}, new ComparableStack(ModItems.crystal_starmetal), true, false, FluidType.PLASMA_WARP, 8000).setLevel(3));
	}
	/**
	 * Standard recipe for upgrading singularities
	 * @param output - The resultant item output
	 * @param containmentItem - Item used for upgrading containment, goes in the corners
	 * @param modifierItem - Item used for modifying the item, goes in the sides
	 * @param centerItem - Item to be upgraded, goes in the center
	 */
	public static SingGenRecipe commonStandard(ItemStack output, Item containmentItem, Item modifierItem, AStack centerItem)
	{
		return addRecipe(output, new ItemStack[] {new ItemStack(containmentItem), new ItemStack(modifierItem), new ItemStack(containmentItem), new ItemStack(modifierItem), new ItemStack(modifierItem), new ItemStack(containmentItem),  new ItemStack(modifierItem), new ItemStack(containmentItem)}, centerItem, true, false, FluidType.NONE, 0);
	}
	/**
	 * Standard recipe type
	 * @param output - The resultant item
	 * @param containmentItem - Item used for containment of the item, goes in the corners
	 * @param fluid - The fluid type used, must be Antischrabidium or warp plasma
	 * @param centerItem - The item that goes in the center
	 * @param fluidAmount - The amount of fluid needed, max 64b
	 */
	public static SingGenRecipe commonFluid(ItemStack output, Item containmentItem, FluidType fluid, AStack centerItem, int fluidAmount)
	{
		return addRecipe(output, new ItemStack[] {new ItemStack(containmentItem), null, new ItemStack(containmentItem), null, null, new ItemStack(containmentItem), null, new ItemStack(containmentItem)}, centerItem, true, false, fluid, fluidAmount);
	}
	/**
	 * Add a recipe directly
	 * @param output - The output, may be more than 1
	 * @param inputRing - The ring of 8 items around the center
	 * @param inputCenter - The center item
	 * @param shaped - Does placement of items in the ring matter?
	 * @param keepRing - Do the items in the ring persist?
	 * @param fluid - Either Antischrabidium or warp plasma
	 * @param fluidAmount - Amount of fluid needed, max 64b
	 */
	public static SingGenRecipe addRecipe(ItemStack output, ItemStack[] inputRing, AStack inputCenter, boolean shaped, boolean keepRing, FluidType fluid, @Nonnegative int fluidAmount)
	{
		// Error checking
		if (inputRing.length != 8)
			throw new IllegalArgumentException("Recipe ring input must be exactly 8! Recipe output: " + output.getItem().getUnlocalizedName() + "; Ring length: " + inputRing.length);// So we know which recipe errored and why
		if (!(fluid.equals(FluidType.ASCHRAB) || fluid.equals(FluidType.PLASMA_WARP) || fluid.equals(FluidType.NONE)))
			throw new IllegalArgumentException("Recipe fluid input must be either antischrabidium, warp plasma, or none! Recipe output: " + output.getItem().getUnlocalizedName() + "; Fluid attempted: " + fluid.getUnlocalizedName());
		if (fluidAmount > 64000 || fluidAmount < 0)
			throw new IndexOutOfBoundsException("Recipe fluid input amount out of bounds, must be between 0 and 64000! Recipe output: " + output.getItem().getUnlocalizedName() + "; Fluid amount attempted: " + fluidAmount);
		
//		recipes.add(new SingGenRecipe(output, inputRing, inputCenter, shaped, keepRing, fluid, fluidAmount));
		return new SingGenRecipe(output, inputRing, inputCenter, shaped, keepRing, fluid, fluidAmount);
	}
	@Deprecated
	/**
	 * Converts a list of item IDs to their item
	 * @param ringIn - The list of IDs to be converted
	 * @return The list of items
	 */
	public static List<ItemStack> getRingItems(List<Integer> ringIn)
	{
		List<ItemStack> ringItems = new ArrayList<ItemStack>();
		for (int i = 0; i < 8; i++)
		{
			if (ringIn.get(i) != -1)
				ringItems.add(new ItemStack(Item.getItemById(ringIn.get(i))));
			else
				ringItems.add(null);
		}
		return ringItems;
	}
	@Deprecated
	/**
	 * Converts an array of items to their ID
	 * @param ringIn - The array of items 
	 * @return The list of IDs
	 */
	public static Integer[] getRingIDs(ItemStack[] ringIn)
	{
//		List<Integer> ringIDs = new ArrayList<Integer>();
		Integer[] ringIDs = new Integer[8];
		int i = 0;
		for (ItemStack itemIn : ringIn)
		{
			if (itemIn != null)
				ringIDs[i] = (Item.getIdFromItem(itemIn.getItem()));
			else
				ringIDs[i] = -1;
			
			i++;
		}
		return ringIDs;
	}
	@Deprecated
	/**
	 * Check if the ring in the currently checked recipe matches what's in the machine
	 * @param machineRing - The ring in the machine
	 * @param recipeRing - The ring in the recipe
	 * @param shaped - Is it shaped?
	 * @return If it matches or not (boolean)
	 */
	public static boolean doRingsMatch(ItemStack[] machineRing, ItemStack[] recipeRing, boolean shaped)
	{
		for (int i = 0; i < 8; i++)
		{
			Integer[] machineRingIDs = getRingIDs(machineRing);
			Integer[] recipeRingIDs = getRingIDs(recipeRing);

			if (!shaped)
			{
				Collections.sort(Arrays.asList(machineRingIDs));
				Collections.sort(Arrays.asList(recipeRingIDs));
			}
			if (shaped)
			{
				if (machineRing[i] != recipeRing[i])
					return false;
			}
			else
			{
				if (Item.getItemById(machineRingIDs[i]) != Item.getItemById(recipeRingIDs[i]))
				{
					return false;
				}
			}
		}
		return true;
	}
	@Deprecated
	public static boolean doRingsMatch(ItemStack[] machineRing, AStack[] recipeRing, boolean shaped)
	{
		for (int i1 = 0; i1 < 8; i1++)
		{
			if (shaped)
				if (!isItemValid(machineRing[i1], recipeRing[i1]))
					return false;
			else
			{
//				List<ItemStack> machineRingStack = Arrays.asList(machineRing);
//				int matches = 0;
//				for (int i2 = 0; i2 < 8; i2++)
//				{
//					if (isItemValid(machineRingStack.get(i1), recipeRing[i2]))
//					{
//						machineRingStack.remove(i1);
//						matches++;
//					}
//				}
//				if (matches != 8)
//					return false;
				
				AStack[] machineRingStack = RecipesCommon.itemStackToAStack(machineRing, true).clone();
				AStack[] recipeRingStack = recipeRing.clone();
				
				return Arrays.asList(recipeRingStack).containsAll(Arrays.asList(machineRingStack));
			}
		}
		return true;
	}
	/**
	 * Updated version, can basically handle everything in theory, not quite finished yet
	 * @param machineRing - The ring that came from the machine
	 * @param recipe - The currently checked recipe
	 * @param center - Machine's center item
	 * @param fluidIn - Machine's fluid type
	 * @return If the recipe is valid with the contents of the machine
	 */
	public static boolean isRecipeValid(ItemStack[] machineRing, SingGenRecipe recipe, ItemStack center, FluidType fluidIn)
	{
		if (!(recipe.fluid.equals(fluidIn) || recipe.fluid.equals(FluidType.NONE)))
			return false;
		if ((center == null && recipe.inputCenter != null) || (center != null && recipe.inputCenter == null))
			return false;
//		else if (center != null && recipe.inputCenter != null && !(recipe.inputCenter.isApplicable((new ComparableStack(center)).makeSingular())))
		else if (!isItemValid(center, recipe.inputCenter))
			return false;
		
		if (recipe.shaped)
		{
			for (int i = 0; i < 8; i++)
			{
//				if ((machineRing[i] == null && recipe.aInputRing[i] != null) || (machineRing[i] != null && recipe.aInputRing[i] == null))
//					return false;
//				if (machineRing[i] == null && recipe.aInputRing[i] == null)
//					continue;
//				if (!recipe.aInputRing[i].isApplicable(new ComparableStack(machineRing[i]).makeSingular()))
//					return false;
				if (!isItemValid(machineRing[i], recipe.aInputRing[i]))
					return false;
			}
		}
		else// FIXME
		{
			List<AStack> clonedRecipe = Lists.newArrayList(recipe.aInputRing);
//			Collections.addAll(clonedRecipe, recipe.aInputRing);
			List<ItemStack> clonedMachine = Lists.newArrayList(singularizeItemStackArray(machineRing));
//			Collections.addAll(clonedMachine, singularizeItemStackArray(machineRing));
			int matches = 0;
			for (int i1 = 0; i1 < clonedRecipe.size(); i1++)
			{
				for (int i2 = 0; i2 < clonedMachine.size(); i2++)
				{
//					if (clonedMachine.get(i2) == null && clonedRecipe.get(i1) == null || clonedRecipe.get(i1).isApplicable(clonedMachine.get(i2)))
					if (isItemValid(clonedMachine.get(i2), clonedRecipe.get(i1)))
					{
						i1 = 0;
						i2 = 0;
						clonedRecipe.remove(i1);
						clonedMachine.remove(i2);
						matches++;
						System.out.println("New match! Match #" + matches);
					}
					else
						System.out.println("No match! Current matches: " + matches);
				}
				
			}
			return matches >= 8;
		}
		
		return true;
	}
	/**
	 * Reduces the stack size of all itemstacks in the array to 1
	 * @param stacksIn - The array of itemstacks in question
	 * @return A new itemstack array whose stack sizes are 1
	 */
	private static ItemStack[] singularizeItemStackArray(ItemStack[] stacksIn)
	{
		ItemStack[] newStacks = stacksIn.clone();
		
		for (int i = 0; i < stacksIn.length; i++)
			if (newStacks[i] != null)
				newStacks[i].stackSize = 1;
		
		return newStacks;
	}
	
	/**
	 * Check if an item reasonably matches one in a recipe 
	 * @param stackIn - The itemstack that will be checked
	 * @param recipeIn - The AStack that it will be checked against
	 * @return If it is valid or not
	 */
	public static boolean isItemValid(ItemStack stackIn, AStack recipeIn)
	{
//		if (stackIn == null && recipeIn == null)
//			return true;
//		else if ((stackIn == null && recipeIn != null) || (stackIn != null && recipeIn == null))
//			return false;
//		
//		ComparableStack cStack = new ComparableStack(stackIn).makeSingular();
//		AStack aStackIn = recipeIn.copy();
//		aStackIn.stacksize = 1;
//		
//		return aStackIn.isApplicable(cStack);
		if ((stackIn == null && recipeIn != null) || (stackIn != null && recipeIn == null))
			return false;
		if (stackIn == null && recipeIn == null)
			return true;
		return (recipeIn.isApplicable(new ComparableStack(stackIn).makeSingular()));
//			return false;
//		return stackIn != null && recipeIn != null && !(recipeIn.isApplicable((new ComparableStack(stackIn)).makeSingular()));
	}
	
	public static class SingGenRecipe
	{
		private ItemStack output;
		public ItemStack[] inputRing;
		public AStack[] aInputRing = new AStack[8];
		public AStack inputCenter;
		public boolean shaped;
		public boolean keepRing;
		public FluidType fluid = FluidType.NONE;
		public int fluidAmount;
		private byte level = 0;
		public SingGenRecipe(ItemStack output, ItemStack[] inputRing, AStack inputCenter, boolean shaped, boolean keepRing, FluidType fluid, int fluidAmount)
		{
			this.output = output;
			this.inputRing = inputRing;
			this.inputCenter = inputCenter;
			this.shaped = true;
			this.keepRing = keepRing;
			this.fluid = fluid;
			this.fluidAmount = fluidAmount;
			aInputRing = RecipesCommon.itemStackToAStack(inputRing, true);
		}
		/**
		 * To set the recipe "level", purely decorative
		 * @param levelIn The desired level
		 * @return Itself
		 * @throws IndexOutOfBoundsException If the input is above 5
		 */
		public SingGenRecipe setLevel(@Nonnegative int levelIn) throws IndexOutOfBoundsException
		{
			if (levelIn > 5)
				throw new IndexOutOfBoundsException("Attempted to use number greater than 5! Number attempted: " + levelIn);
			
			level = (byte) levelIn;
			return this;
		}
		/**
		 * Set the recipe to use a fluid, due to current limitations, it only supports {@literal ASCHRAB} and {@literal PLASMA_WARP}
		 * @param fluid The fluid type
		 * @param amount Fluid amount
		 * @return Itself
		 * @throws IllegalArgumentException If the fluid type is neither {@literal ASCHRAB} or {@literal PLASMA_WARP}
		 */
		public SingGenRecipe setFluid(FluidType fluid, @Nonnegative int amount) throws IllegalArgumentException
		{
			if (fluid != FluidType.ASCHRAB || fluid != FluidType.PLASMA_WARP)
				throw new IllegalArgumentException("Recipe fluid input must be either antischrabidium, warp plasma, or none! Recipe output: " + output.getItem().getUnlocalizedName() + "; Fluid attempted: " + fluid.getUnlocalizedName());
			this.fluid = fluid;
			this.fluidAmount = amount;
			return this;
		}
		public ItemStack getOutput()
		{
			return output.copy();
		}
		public byte getLevel()
		{
			return level;
		}
		@Override
		public String toString()
		{
			return String.format("Stock toString(): %s; Recipe output: %s; Center input: %s; Ring input: [%s, %s, %s, %s, %s, %s, %s, %s]; Fluid input: %s; Fluid amount: %s; Recipe level: %s", super.toString(), output.getDisplayName(), inputCenter.getFriendlyName(), aInputRing.toString(), fluid.getName(), fluidAmount, level);
		}
	}
}
