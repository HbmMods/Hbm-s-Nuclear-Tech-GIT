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
		OreDictStack nugget = new OreDictStack(PB.nugget());
		OreDictStack flechette = new OreDictStack(PB.bolt());
		OreDictStack steel = new OreDictStack(STEEL.ingot());
		OreDictStack wSteel = new OreDictStack(WEAPONSTEEL.ingot());
		OreDictStack copper = new OreDictStack(CU.ingot());
		OreDictStack plastic = new OreDictStack(ANY_PLASTIC.ingot());
		OreDictStack uranium = new OreDictStack(U238.ingot());
		OreDictStack smokeless = new OreDictStack(ANY_SMOKELESS.dust());
		OreDictStack he = new OreDictStack(ANY_HIGHEXPLOSIVE.ingot());
		OreDictStack wp = new OreDictStack(P_WHITE.ingot());
		ComparableStack cSmall = new ComparableStack(ModItems.casing, 1, EnumCasingType.SMALL);
		ComparableStack cBig = new ComparableStack(ModItems.casing, 1, EnumCasingType.LARGE);
		ComparableStack sSmall = new ComparableStack(ModItems.casing, 1, EnumCasingType.SMALL_STEEL);
		ComparableStack sBig = new ComparableStack(ModItems.casing, 1, EnumCasingType.LARGE_STEEL);
		ComparableStack bpShell = new ComparableStack(ModItems.casing, 1, EnumCasingType.SHOTSHELL);
		ComparableStack pShell = new ComparableStack(ModItems.casing, 1, EnumCasingType.BUCKSHOT);
		ComparableStack sShell = new ComparableStack(ModItems.casing, 1, EnumCasingType.BUCKSHOT_ADVANCED);
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M357_SP, 8),
				null,	lead,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M357_FMJ, 8),
				null,	steel,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M357_JHP, 8),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M357_AP, 8),
				null,	wSteel,				null,
				null,	smokeless.copy(2),	null,
				null,	sSmall,				null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M357_EXPRESS, 8),
				null,	steel,				null,
				null,	smokeless.copy(3),	null,
				null,	cSmall,				null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M44_SP, 6),
				null,	lead,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M44_FMJ, 6),
				null,	steel,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M44_JHP, 6),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M44_AP, 6),
				null,	wSteel,				null,
				null,	smokeless.copy(2),	null,
				null,	sSmall,				null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.M44_EXPRESS, 6),
				null,	steel,				null,
				null,	smokeless.copy(3),	null,
				null,	cSmall,				null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P22_SP, 24),
				null,	lead,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P22_FMJ, 24),
				null,	steel,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P22_JHP, 24),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P22_AP, 24),
				null,	wSteel,				null,
				null,	smokeless.copy(2),	null,
				null,	sSmall,				null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_SP, 12),
				null,	lead,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_FMJ, 12),
				null,	steel,		null,
				null,	smokeless,	null,
				null,	cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_JHP, 12),
				plastic,	copper,		null,
				null,		smokeless,	null,
				null,		cSmall,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.P9_AP, 12),
				null,	wSteel,				null,
				null,	smokeless.copy(2),	null,
				null,	sSmall,				null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R556_SP, 16),
				null,	lead.copy(2),		null,
				null,	smokeless.copy(2),	null,
				null,	cSmall.copy(2),		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R556_FMJ, 16),
				null,	steel.copy(2),		null,
				null,	smokeless.copy(2),	null,
				null,	cSmall.copy(2),		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R556_JHP, 16),
				plastic,	copper.copy(2),		null,
				null,		smokeless.copy(2),	null,
				null,		cSmall.copy(2),		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R556_AP, 16),
				null,	wSteel.copy(2),		null,
				null,	smokeless.copy(4),	null,
				null,	sSmall.copy(2),		null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R762_SP, 12),
				null,	lead.copy(2),		null,
				null,	smokeless.copy(2),	null,
				null,	cSmall.copy(2),		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R762_FMJ, 12),
				null,	steel.copy(2),		null,
				null,	smokeless.copy(2),	null,
				null,	cSmall.copy(2),		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R762_JHP, 12),
				plastic,	copper.copy(2),		null,
				null,		smokeless.copy(2),	null,
				null,		cSmall.copy(2),		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R762_AP, 12),
				null,	wSteel.copy(2),		null,
				null,	smokeless.copy(4),	null,
				null,	sSmall.copy(2),		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.R762_DU, 12),
				null,	uranium.copy(2),	null,
				null,	smokeless.copy(4),	null,
				null,	sSmall.copy(2),		null));
		
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.BMG50_SP, 12),
				null,	lead.copy(2),		null,
				null,	smokeless.copy(3),	null,
				null,	cBig,				null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.BMG50_FMJ, 12),
				null,	steel.copy(2),		null,
				null,	smokeless.copy(3),	null,
				null,	cBig,				null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.BMG50_JHP, 12),
				plastic,	copper.copy(2),		null,
				null,		smokeless.copy(3),	null,
				null,		cBig,				null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.BMG50_AP, 12),
				null,	wSteel.copy(2),		null,
				null,	smokeless.copy(6),	null,
				null,	sBig,				null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.BMG50_DU, 12),
				null,	uranium.copy(2),	null,
				null,	smokeless.copy(6),	null,
				null,	sBig,				null));

		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12_BP, 6),
				null,	nugget.copy(6), null,
				null,	smokeless,		null,
				null,	bpShell,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12_BP_MAGNUM, 6),
				null,	nugget.copy(8), null,
				null,	smokeless,		null,
				null,	bpShell,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12_BP_SLUG, 6),
				null,	lead, 		null,
				null,	smokeless,	null,
				null,	bpShell,	null));

		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12, 6),
				null,	nugget.copy(6),	null,
				null,	smokeless,		null,
				null,	pShell,			null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12_SLUG, 6),
				null,	lead, 		null,
				null,	smokeless,	null,
				null,	pShell,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12_FLECHETTE, 6),
				null,	flechette.copy(12),	null,
				null,	smokeless,			null,
				null,	pShell,				null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12_MAGNUM, 6),
				null,	nugget.copy(8),		null,
				null,	smokeless,			null,
				null,	sShell,				null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12_EXPLOSIVE, 6),
				null,	he,			null,
				null,	smokeless,	null,
				null,	sShell,		null));
		recipes.add(new AmmoPressRecipe(DictFrame.fromOne(ModItems.ammo_standard, EnumAmmo.G12_PHOSPHORUS, 6),
				null,	wp,			null,
				null,	smokeless,	null,
				null,	sShell,		null));
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
