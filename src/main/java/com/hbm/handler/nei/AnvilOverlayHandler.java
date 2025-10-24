package com.hbm.handler.nei;

import com.hbm.inventory.gui.GUIAnvil;
import com.hbm.inventory.recipes.anvil.AnvilRecipes.AnvilConstructionRecipe;

import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.recipe.IRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;

/**
 * Links NEI overlay interactions with the {@link GUIAnvil} so selecting a recipe focuses the anvil view.
 */
public class AnvilOverlayHandler implements IOverlayHandler {

	/**
	 * {@inheritDoc}
	 *
	 * <p>When the NEI overlay is opened for the Nuclear Tech anvil, the GUI is instructed to focus the selected recipe.</p>
	 * <p>It will not craft the recepie. Only select it.</p>
	 *
	 * @param firstGui the GUI currently displaying the overlay
	 * @param recipe the NEI recipe handler backing the overlay
	 * @param recipeIndex index of the recipe within the handler
	 * @param maxTransfer whether NEI is performing a maximal transfer (ignored)
	 */
	@Override
	public void overlayRecipe(GuiContainer firstGui, IRecipeHandler recipe, int recipeIndex, boolean maxTransfer) {
		if(!(firstGui instanceof GUIAnvil) || !(recipe instanceof AnvilRecipeHandler)) {
			return;
		}

		AnvilConstructionRecipe construction = ((AnvilRecipeHandler) recipe).getConstructionRecipe(recipeIndex);
		if(construction != null) {
			((GUIAnvil) firstGui).focusRecipe(construction);
		}
	}
}
