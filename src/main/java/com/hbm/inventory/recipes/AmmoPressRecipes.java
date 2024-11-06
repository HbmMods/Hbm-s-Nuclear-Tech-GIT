package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumCasingType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;

import net.minecraft.item.ItemStack;

public class AmmoPressRecipes extends SerializableRecipe {
	
	public static List<AmmoPressRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {

		OreDictStack lead = new OreDictStack(PB.ingot());
		OreDictStack steel = new OreDictStack(STEEL.ingot());
		OreDictStack copper = new OreDictStack(CU.ingot());
		OreDictStack plastic = new OreDictStack(ANY_PLASTIC.ingot());
		OreDictStack smokeless = new OreDictStack(ANY_SMOKELESS.dust());
		ComparableStack cSmall = new ComparableStack(ModItems.casing, 1, EnumCasingType.SMALL);
		ComparableStack cBig = new ComparableStack(ModItems.casing, 1, EnumCasingType.LARGE);
		ComparableStack sSmall = new ComparableStack(ModItems.casing, 1, EnumCasingType.SMALL_STEEL);
		ComparableStack sBig = new ComparableStack(ModItems.casing, 1, EnumCasingType.LARGE_STEEL);
		ComparableStack bpShell = new ComparableStack(ModItems.casing, 1, EnumCasingType.SHOTSHELL);
		ComparableStack pShell = new ComparableStack(ModItems.casing, 1, EnumCasingType.BUCKSHOT);
		ComparableStack sShell = new ComparableStack(ModItems.casing, 1, EnumCasingType.BUCKSHOT_ADVANCED);
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_SP, 8),
				null,	lead,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_FMJ, 8),
				null,	steel,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_SP, 8),
				null,	lead,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_FMJ, 8),
				null,	steel,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_SP, 8),
				null,	lead,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_FMJ, 8),
				null,	steel,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_SP, 8),
				null,	lead,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_FMJ, 8),
				null,	steel,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
	}

	@Override
	public String getFileName() {
		return "hbmAmmoPress.json";
	}

	@Override
	public String getComment() {
		return "Input array describes slots from left to right, top to bottom. Make sure the input array is exactly 9 elements long, empty slots are represented by null.";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		ItemStack output = this.readItemStack(obj.get("output").getAsJsonArray());
		JsonArray inputArray = obj.get("input").getAsJsonArray();
		AStack[] input = new AStack[9];
		
		for(int i = 0; i < 9; i++) {
			JsonElement element = inputArray.get(i);
			if(element.isJsonNull()) {
				input[i] = null;
			} else {
				input[i] = this.readAStack(element.getAsJsonArray());
			}
		}
		
		this.recipes.add(new AmmoPressRecipe(output, input));
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		AmmoPressRecipe rec = (AmmoPressRecipe) recipe;
		
		writer.name("output");
		this.writeItemStack(rec.output, writer);
		
		writer.name("input").beginArray();
		for(int i = 0; i < rec.input.length; i++) {
			if(rec.input[i] == null) {
				writer.nullValue();
			} else {
				this.writeAStack(rec.input[i], writer);
			}
		}
		writer.endArray();
	}
	
	public static class AmmoPressRecipe {
		public ItemStack output;
		public AStack[] input;
		
		public AmmoPressRecipe(ItemStack output, AStack... input) {
			this.output = output;
			this.input = input;
		}
	}
}
