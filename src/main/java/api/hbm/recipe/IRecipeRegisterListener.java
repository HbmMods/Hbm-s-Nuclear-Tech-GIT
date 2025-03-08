package api.hbm.recipe;

public interface IRecipeRegisterListener {

	/**
	 * Called during SerializableRecipe.initialize(), after the defaults are loaded but before the template is written.
	 * Due to the way the recipes are handled, calling it once is not enough, it has to be called once for every SerializableRecipe
	 * instance handled, therefore the load operation passes the type name of the recipe, so that the listeners know what type of recipe
	 * to register at that point. Note that the actual SerializableRecipe instance is irrelevant, since recipes are static anyway,
	 * and direct tampering with SerializableRecipes is not recommended.
	 */
	public void onRecipeLoad(String recipeClassName);
}
