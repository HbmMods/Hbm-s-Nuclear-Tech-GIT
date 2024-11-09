package com.hbm.util;

import com.hbm.inventory.recipes.loader.SerializableRecipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ICrashCallable;

public class CrashHelper {

	public static void init() {
		FMLCommonHandler.instance().registerCrashCallable(new CrashCallableRecipe());
	}

	public static class CrashCallableRecipe implements ICrashCallable {

		@Override
		public String getLabel() {
			return "NTM Modified recipes:";
		}

		@Override
		public String call() throws Exception {

			String call = "";

			for(SerializableRecipe rec : SerializableRecipe.recipeHandlers) {
				if(rec.modified) call += "\n\t\t" + rec.getFileName();
			}

			return call;
		}
	}
}
