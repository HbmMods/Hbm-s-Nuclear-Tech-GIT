package api.hbm.recipe;

/*

Quick guide on how to make robust and safe recipe integration:
* Implement IRecipeRegisterListener, the resulting class will handle all recipes, and the onRecipeLoad method is called every time the SerializableRecipe system updates
* Register your IRecipeRegisterListener using CompatExternal.registerRecipeRegisterListener, this has to happen before the SerializableRecipe initializes, doing this during PreInit should be safe
* In your IRecipeRegisterListener, check the supplied recipe type string (which will be the class name of the SerializableRecipe currently being registered) and register your custom recipes accordingly using CompatRecipeRegistry

Explanation:
* Order of operations is important for the recipes to work, if recipes are loaded outside the scope of SerializableRecipe.initialize, they will not work correctly
* If recipes are registered before the init, they are deleted, if they are registered after the init, they will not be part of the config template file, and get deleted when running /ntmreload
* Machines change all the time, so the recipe classes should not be considered API, since the compat would break immediately if a machine is changed or removed
* CompatRecipeRegistry promises to never change its method signatures, and have solid sanity checking when recipes are registered, allowing it to make the bst of whatever data is thrown at it
* Using this dedicated registry class means that even if a machine is changed or removed, the recipes will continue to work to the best of its abilities

*/