package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumSecretType;
import com.hbm.items.food.ItemConserve.EnumFoodType;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmoSecret;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumModSpecial;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PedestalRecipes extends SerializableRecipe {
	
	public static List<PedestalRecipe> recipes = new ArrayList();
	public static ArrayList<PedestalRecipe>[] recipeSets = new ArrayList[2];
	
	static { for(int i = 0; i < recipeSets.length; i++) recipeSets[i] = new ArrayList(); }

	@Override
	public void registerDefaults() {

		register(new PedestalRecipe(new ItemStack(ModItems.gun_light_revolver_dani),
				null,							new OreDictStack(PB.plate()),						null,
				new OreDictStack(GOLD.plate()),	new ComparableStack(ModItems.gun_light_revolver),	new OreDictStack(GOLD.plate()),
				null,							new OreDictStack(PB.plate()),						null));

		register(new PedestalRecipe(new ItemStack(ModItems.gun_maresleg_broken),
				new ComparableStack(ModBlocks.barbed_wire),	new OreDictStack(WEAPONSTEEL.plate()),		new ComparableStack(ModBlocks.barbed_wire),
				new OreDictStack(WEAPONSTEEL.plate()),		new ComparableStack(ModItems.gun_maresleg),	new OreDictStack(WEAPONSTEEL.plate()),
				new ComparableStack(ModBlocks.barbed_wire),	new OreDictStack(WEAPONSTEEL.plate()),		new ComparableStack(ModBlocks.barbed_wire)));

		register(new PedestalRecipe(new ItemStack(ModItems.gun_heavy_revolver_lilmac),
				null,										new ComparableStack(ModItems.weapon_mod_special, 1, EnumModSpecial.SCOPE),	null,
				new ComparableStack(ModItems.powder_magic),	new ComparableStack(ModItems.gun_heavy_revolver),							new OreDictStack(WEAPONSTEEL.plate()),
				null,										new OreDictStack(BONE.grip()),												new ComparableStack(Items.apple, 3)));

		register(new PedestalRecipe(new ItemStack(ModItems.gun_heavy_revolver_protege),
				new ComparableStack(ModBlocks.chain, 16),		new OreDictStack(CINNABAR.gem()),					new ComparableStack(ModBlocks.chain, 16),
				new ComparableStack(ModItems.scrap_nuclear),	new ComparableStack(ModItems.gun_heavy_revolver),	new ComparableStack(ModItems.scrap_nuclear),
				new ComparableStack(ModBlocks.chain, 16),		new OreDictStack(CINNABAR.gem()),					new ComparableStack(ModBlocks.chain, 16)));

		register(new PedestalRecipe(new ItemStack(ModItems.gun_flamer_daybreaker),
				new OreDictStack(GOLD.plateCast()),	new ComparableStack(ModItems.canned_conserve, 1, EnumFoodType.JIZZ),	new OreDictStack(GOLD.plateCast()),
				new OreDictStack(P_WHITE.ingot()),	new ComparableStack(ModItems.gun_flamer),								new OreDictStack(P_WHITE.ingot()),
				new OreDictStack(GOLD.plateCast()),	new ComparableStack(ModItems.stick_dynamite),							new OreDictStack(GOLD.plateCast()))
				.extra(PedestalExtraCondition.SUN));

		register(new PedestalRecipe(new ItemStack(ModItems.gun_autoshotgun_sexy),
				new ComparableStack(ModItems.bolt_spike, 16),	new OreDictStack(STAR.ingot(), 4),				new ComparableStack(ModItems.bolt_spike, 16),
				new ComparableStack(ModItems.card_qos),			new ComparableStack(ModItems.gun_autoshotgun),	new ComparableStack(ModItems.card_aos),
				new ComparableStack(ModItems.bolt_spike, 16),	new OreDictStack(STAR.ingot(), 4),				new ComparableStack(ModItems.bolt_spike, 16)));

		register(new PedestalRecipe(new ItemStack(ModItems.gun_minigun_lacunae),
				null,																			new ComparableStack(ModItems.powder_magic, 4),	null,
				new ComparableStack(ModItems.item_secret, 4, EnumSecretType.SELENIUM_STEEL),	new ComparableStack(ModItems.gun_minigun),		new ComparableStack(ModItems.item_secret, 4, EnumSecretType.SELENIUM_STEEL),
				null,																			new ComparableStack(ModItems.powder_magic, 4),	null)
				.extra(PedestalExtraCondition.FULL_MOON));

		register(new PedestalRecipe(new ItemStack(ModItems.gun_folly),
				new ComparableStack(ModItems.item_secret, 4, EnumSecretType.FOLLY),	new ComparableStack(ModItems.item_secret, 2, EnumSecretType.CONTROLLER),	new ComparableStack(ModItems.item_secret, 4, EnumSecretType.FOLLY),
				new OreDictStack(BSCCO.ingot(), 16),								new OreDictStack(STAR.block(), 64),											new OreDictStack(BSCCO.ingot(), 16),
				new ComparableStack(ModItems.item_secret, 4, EnumSecretType.FOLLY),	new ComparableStack(ModItems.item_secret, 2, EnumSecretType.CONTROLLER),	new ComparableStack(ModItems.item_secret, 4, EnumSecretType.FOLLY))
				.extra(PedestalExtraCondition.FULL_MOON).set(1));

		register(new PedestalRecipe(new ItemStack(ModItems.gun_aberrator),
				null,																	new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	null,
				new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	new OreDictStack(BIGMT.mechanism(), 4),									new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),
				null,																	new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	null).set(1));
		register(new PedestalRecipe(new ItemStack(ModItems.gun_aberrator_eott),
				new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),
				new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	new OreDictStack(BIGMT.mechanism(), 16),								new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),
				new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR))
				.extra(PedestalExtraCondition.GOOD_KARMA).set(1));

		register(new PedestalRecipe(new ItemStack(ModItems.ammo_secret, 1, EnumAmmoSecret.FOLLY_SM.ordinal()),
				new OreDictStack(STAR.ingot(), 1),			new ComparableStack(ModItems.powder_magic),	new OreDictStack(STAR.ingot(), 1),
				new ComparableStack(ModItems.powder_magic),	new ComparableStack(ModBlocks.moon_turf),	new ComparableStack(ModItems.powder_magic),
				new OreDictStack(STAR.ingot(), 1),			new ComparableStack(ModItems.powder_magic),	new OreDictStack(STAR.ingot(), 1))
				.extra(PedestalExtraCondition.FULL_MOON).set(1));
		register(new PedestalRecipe(new ItemStack(ModItems.ammo_secret, 1, EnumAmmoSecret.FOLLY_NUKE.ordinal()),
				new OreDictStack(STAR.ingot(), 1),			new ComparableStack(ModItems.powder_magic),							new OreDictStack(STAR.ingot(), 1),
				new ComparableStack(ModItems.powder_magic),	new ComparableStack(ModItems.ammo_standard, 4, EnumAmmo.NUKE_HIGH),	new ComparableStack(ModItems.powder_magic),
				new OreDictStack(STAR.ingot(), 1),			new ComparableStack(ModItems.powder_magic),							new OreDictStack(STAR.ingot(), 1))
				.extra(PedestalExtraCondition.FULL_MOON).set(1));
		register(new PedestalRecipe(new ItemStack(ModItems.ammo_secret, 5, EnumAmmoSecret.P35_800.ordinal()),
				null,	null,																	null,
				null,	new ComparableStack(ModItems.item_secret, 1, EnumSecretType.ABERRATOR),	null,
				null,	null,																	null).set(1));
	}
	
	public static void register(PedestalRecipe recipe) {
		recipes.add(recipe);
		int set = Math.abs(recipe.recipeSet) % recipeSets.length;
		recipeSets[set].add(recipe);
	}

	@Override
	public String getFileName() {
		return "hbmPedestal.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
		for(int i = 0; i < recipeSets.length; i++) recipeSets[i].clear();
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
		
		PedestalRecipe rec = new PedestalRecipe(output, input);
		if(obj.has("extra")) {
			rec.extra = PedestalExtraCondition.valueOf(obj.get("extra").getAsString());
		}

		if(obj.has("set")) {
			rec.recipeSet = obj.get("set").getAsInt();
		}
		
		this.recipes.add(rec);
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		PedestalRecipe rec = (PedestalRecipe) recipe;
		
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
		
		writer.name("extra").value(rec.extra.name());
		if(rec.recipeSet != 0) writer.name("set").value(rec.recipeSet);
	}
	
	public static enum PedestalExtraCondition {
		NONE, FULL_MOON, NEW_MOON, SUN, GOOD_KARMA, BAD_KARMA
	}
	
	public static class PedestalRecipe {
		public ItemStack output;
		public AStack[] input;
		public int recipeSet = 0;
		public PedestalExtraCondition extra = PedestalExtraCondition.NONE;
		
		public PedestalRecipe(ItemStack output, AStack... input) {
			this.output = output;
			this.input = input;
		}
		
		public PedestalRecipe extra(PedestalExtraCondition extra) {
			this.extra = extra;
			return this;
		}
		
		public PedestalRecipe set(int set) {
			this.recipeSet = set;
			return this;
		}
	}
}
