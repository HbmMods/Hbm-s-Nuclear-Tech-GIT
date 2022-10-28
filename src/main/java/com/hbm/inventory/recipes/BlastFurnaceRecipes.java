package com.hbm.inventory.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;

import static com.hbm.inventory.OreDictManager.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.imc.IMCBlastFurnace;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Magic!
 * 
 * @author UFFR
 */
public class BlastFurnaceRecipes {

	private static final ArrayList<Triplet<Object, Object, ItemStack>> blastFurnaceRecipes = new ArrayList<Triplet<Object, Object, ItemStack>>();
	private static final ArrayList<ItemStack> hiddenRecipes = new ArrayList<ItemStack>();

	private static void addRecipe(Object in1, Object in2, ItemStack out) {
		blastFurnaceRecipes.add(new Triplet<Object, Object, ItemStack>(in1, in2, out));
	}

	static {
		/* STEEL */
		addRecipe(IRON,			COAL,										new ItemStack(ModItems.ingot_steel, 1));
		addRecipe(IRON,			ANY_COKE,									new ItemStack(ModItems.ingot_steel, 1));
		addRecipe(IRON.ore(),	COAL,										new ItemStack(ModItems.ingot_steel, 2));
		addRecipe(IRON.ore(),	ANY_COKE,									new ItemStack(ModItems.ingot_steel, 3));
		addRecipe(IRON.ore(),	new ComparableStack(ModItems.powder_flux),	new ItemStack(ModItems.ingot_steel, 3));
		
		addRecipe(CU, REDSTONE, new ItemStack(ModItems.ingot_red_copper, 2));
		addRecipe(STEEL, MINGRADE, new ItemStack(ModItems.ingot_advanced_alloy, 2));
		addRecipe(W, COAL, new ItemStack(ModItems.neutron_reflector, 2));
		addRecipe(W, ANY_COKE, new ItemStack(ModItems.neutron_reflector, 2));
		addRecipe(new ComparableStack(ModItems.canister_full, 1, Fluids.GASOLINE.getID()), "slimeball", new ItemStack(ModItems.canister_napalm));
		//addRecipe(STEEL, CO, new ItemStack(ModItems.ingot_dura_steel, 2));
		//addRecipe(STEEL, W, new ItemStack(ModItems.ingot_dura_steel, 2));
		//addRecipe(STEEL, U238, new ItemStack(ModItems.ingot_ferrouranium));
		addRecipe(W, SA326.nugget(), new ItemStack(ModItems.ingot_magnetized_tungsten));
		addRecipe(STEEL, TC99.nugget(), new ItemStack(ModItems.ingot_tcalloy));
		addRecipe(GOLD.plate(), ModItems.plate_mixed, new ItemStack(ModItems.plate_paa, 2));
		addRecipe(BIGMT, ModItems.powder_meteorite, new ItemStack(ModItems.ingot_starmetal, 2));
		addRecipe(CO, ModBlocks.block_meteor, new ItemStack(ModItems.ingot_meteorite));
		addRecipe(ModItems.meteorite_sword_hardened, CO, new ItemStack(ModItems.meteorite_sword_alloyed));
		addRecipe(ModBlocks.block_meteor, CO, new ItemStack(ModItems.ingot_meteorite));
		
		if(GeneralConfig.enableLBSMSimpleChemsitry)
			addRecipe(ModItems.canister_empty, COAL, new ItemStack(ModItems.canister_full, 1, Fluids.OIL.getID()));

		if(!IMCBlastFurnace.buffer.isEmpty()) {
			blastFurnaceRecipes.addAll(IMCBlastFurnace.buffer);
			MainRegistry.logger.info("Fetched " + IMCBlastFurnace.buffer.size() + " IMC blast furnace recipes!");
			IMCBlastFurnace.buffer.clear();
		}

		hiddenRecipes.add(new ItemStack(ModItems.meteorite_sword_alloyed));
	}

	@CheckForNull
	public static ItemStack getOutput(ItemStack in1, ItemStack in2) {
		for(Triplet<Object, Object, ItemStack> recipe : blastFurnaceRecipes) {
			final AStack[] recipeItem1 = getRecipeStacks(recipe.getX());
			final AStack[] recipeItem2 = getRecipeStacks(recipe.getY());

			if((doStacksMatch(recipeItem1, in1) && doStacksMatch(recipeItem2, in2)) || (doStacksMatch(recipeItem2, in1) && doStacksMatch(recipeItem1, in2)))
				return recipe.getZ().copy();
			else
				continue;
		}
		return null;
	}

	private static boolean doStacksMatch(AStack[] recipe, ItemStack in) {
		boolean flag = false;
		byte i = 0;
		while(!flag && i < recipe.length) {
			flag = recipe[i].matchesRecipe(in, true);
			i++;
		}
		return flag;
	}

	private static AStack[] getRecipeStacks(Object in) {
		final AStack[] recipeItem1;
		if(in instanceof DictFrame) {
			DictFrame recipeItem = (DictFrame) in;
			recipeItem1 = new AStack[] { new OreDictStack(recipeItem.ingot()), new OreDictStack(recipeItem.dust()), new OreDictStack(recipeItem.plate()), new OreDictStack(recipeItem.gem()) };
		} else if(in instanceof AStack)
			recipeItem1 = new AStack[] { (AStack) in };
		else if(in instanceof String)
			recipeItem1 = new AStack[] { new OreDictStack((String) in) };
		else if(in instanceof Block)
			recipeItem1 = new AStack[] { new ComparableStack((Block) in) };
		else if(in instanceof List<?>) {
			List<?> oreList = (List<?>) in;
			recipeItem1 = new AStack[oreList.size()];
			for(int i = 0; i < oreList.size(); i++)
				recipeItem1[i] = new OreDictStack((String) oreList.get(i));
		} else
			recipeItem1 = new AStack[] { new ComparableStack((Item) in) };

		return recipeItem1;
	}

	public static Map<List<ItemStack>[], ItemStack> getRecipesForNEI() {
		final HashMap<List<ItemStack>[], ItemStack> recipes = new HashMap<>();

		for(Triplet<Object, Object, ItemStack> recipe : blastFurnaceRecipes) {
			if(!hiddenRecipes.contains(recipe.getZ())) {
				final ItemStack nothing = new ItemStack(ModItems.nothing).setStackDisplayName("If you're reading this, an error has occured! Check the console.");
				final List<ItemStack> in1 = new ArrayList<ItemStack>();
				final List<ItemStack> in2 = new ArrayList<ItemStack>();
				in1.add(nothing);
				in2.add(nothing);

				for(AStack stack : getRecipeStacks(recipe.getX())) {
					if(stack.extractForNEI().isEmpty())
						continue;
					else {
						in1.remove(nothing);
						in1.addAll(stack.extractForNEI());
						break;
					}
				}
				if(in1.contains(nothing)) {
					MainRegistry.logger.error("Blast furnace cannot compile recipes for NEI: apparent nonexistent item #1 in recipe for item: " + recipe.getZ().getDisplayName());
				}
				for(AStack stack : getRecipeStacks(recipe.getY())) {
					if(stack.extractForNEI().isEmpty()) {
						continue;
					} else {
						in2.remove(nothing);
						in2.addAll(stack.extractForNEI());
						break;
					}
				}
				if(in2.contains(nothing)) {
					MainRegistry.logger.error("Blast furnace cannot compile recipes for NEI: apparent nonexistent item #2 in recipe for item: " + recipe.getZ().getDisplayName());
				}

				final List<ItemStack>[] inputs = new List[2];
				inputs[0] = in1;
				inputs[1] = in2;
				recipes.put(inputs, recipe.getZ());
			}
		}
		return ImmutableMap.copyOf(recipes);
	}

	public static List<Triplet<AStack[], AStack[], ItemStack>> getRecipes() {

		final List<Triplet<AStack[], AStack[], ItemStack>> subRecipes = new ArrayList<>();
		for(Triplet<Object, Object, ItemStack> recipe : blastFurnaceRecipes)
			subRecipes.add(new Triplet<AStack[], AStack[], ItemStack>(getRecipeStacks(recipe.getX()), getRecipeStacks(recipe.getY()), recipe.getZ()));
		return ImmutableList.copyOf(subRecipes);
	}
}
