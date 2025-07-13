package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.special.ItemBedrockOreNew;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreGrade;
import com.hbm.items.special.ItemBedrockOreNew.BedrockOreType;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CombinationRecipes extends SerializableRecipe {

	public static HashMap<Object, Pair<ItemStack, FluidStack>> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		recipes.put(COAL.gem(),		new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), new FluidStack(Fluids.COALCREOSOTE, 100)));
		recipes.put(COAL.dust(),	new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), new FluidStack(Fluids.COALCREOSOTE, 100)));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.COAL)), new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), new FluidStack(Fluids.COALCREOSOTE, 150)));

		recipes.put(LIGNITE.gem(),										new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(Fluids.COALCREOSOTE, 50)));
		recipes.put(LIGNITE.dust(),										new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(Fluids.COALCREOSOTE, 50)));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.LIGNITE)), new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(Fluids.COALCREOSOTE, 100)));

		recipes.put(CHLOROCALCITE.dust(),						new Pair(new ItemStack(ModItems.powder_calcium), new FluidStack(Fluids.CHLORINE, 250)));
		recipes.put(MOLYSITE.dust(),							new Pair(new ItemStack(Items.iron_ingot), new FluidStack(Fluids.CHLORINE, 250)));
		recipes.put(CINNABAR.crystal(),							new Pair(new ItemStack(ModItems.sulfur), new FluidStack(Fluids.MERCURY, 100)));
		recipes.put(new ComparableStack(Items.glowstone_dust),	new Pair(new ItemStack(ModItems.sulfur), new FluidStack(Fluids.CHLORINE, 100)));
		recipes.put(SODALITE.gem(),								new Pair(new ItemStack(ModItems.powder_sodium), new FluidStack(Fluids.CHLORINE, 100)));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.chunk_ore, ItemEnums.EnumChunkType.CRYOLITE)), new Pair(new ItemStack(ModItems.powder_aluminium, 1), new FluidStack(Fluids.LYE, 150)));
		recipes.put(NA.dust(),									new Pair(null, new FluidStack(Fluids.SODIUM, 100)));
		recipes.put(LIMESTONE.dust(),							new Pair(new ItemStack(ModItems.powder_calcium), new FluidStack(Fluids.CARBONDIOXIDE, 50)));

		recipes.put(KEY_LOG,		new Pair(new ItemStack(Items.coal, 1 ,1),							new FluidStack(Fluids.WOODOIL, 250)));
		recipes.put(KEY_SAPLING,	new Pair(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD),	new FluidStack(Fluids.WOODOIL, 50)));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.WOOD)), new Pair(new ItemStack(Items.coal, 1 ,1),	new FluidStack(Fluids.WOODOIL, 500)));

		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE)),	new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), null));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK)),	new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), null));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL)),		new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), null));
		recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WOOD)),		new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), null));

		recipes.put(new ComparableStack(Items.reeds), new Pair(new ItemStack(Items.sugar, 2), new FluidStack(Fluids.ETHANOL, 50)));
		recipes.put(new ComparableStack(Blocks.clay), new Pair(new ItemStack(Blocks.brick_block, 1), null));

		for(BedrockOreType type : BedrockOreType.values()) {
			recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.BASE, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.BASE_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
			recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
			recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_BYPRODUCT, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
			recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_BYPRODUCT, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
			recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.RAD_BYPRODUCT, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.RAD_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
		}
	}

	public static Pair<ItemStack, FluidStack> getOutput(ItemStack stack) {

		if(stack == null || stack.getItem() == null)
			return null;

		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());

		if(recipes.containsKey(comp)) {
			Pair<ItemStack, FluidStack> out = recipes.get(comp);
			return new Pair(out.getKey() == null ? null : out.getKey().copy(), out.getValue());
		}

		String[] dictKeys = comp.getDictKeys();

		for(String key : dictKeys) {

			if(recipes.containsKey(key)) {
				Pair<ItemStack, FluidStack> out = recipes.get(key);
				return new Pair(out.getKey() == null ? null : out.getKey().copy(), out.getValue());
			}
		}

		return null;
	}

	public static HashMap getRecipes() {

		HashMap<Object, Object[]> recipes = new HashMap<Object, Object[]>();

		for(Entry<Object, Pair<ItemStack, FluidStack>> entry : CombinationRecipes.recipes.entrySet()) {
			Object key = entry.getKey();
			Pair<ItemStack, FluidStack> val = entry.getValue();
			Object o = key instanceof String ? new OreDictStack((String) key) : key;

			if(val.getKey() != null && val.getValue() != null) {
				recipes.put(o, new ItemStack[] {val.getKey(), ItemFluidIcon.make(val.getValue())});
			} else if(val.getKey() != null) {
				recipes.put(o, new ItemStack[] {val.getKey()});
			} else if(val.getValue() != null) {
				recipes.put(o, new ItemStack[] {ItemFluidIcon.make(val.getValue())});
			}
		}

		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmCombination.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		AStack in = this.readAStack(obj.get("input").getAsJsonArray());
		FluidStack fluid = null;
		ItemStack out = null;

		if(obj.has("fluid")) fluid = this.readFluidStack(obj.get("fluid").getAsJsonArray());
		if(obj.has("output")) out = this.readItemStack(obj.get("output").getAsJsonArray());

		if(in instanceof ComparableStack) {
			recipes.put(((ComparableStack) in).makeSingular(), new Pair(out, fluid));
		} else if(in instanceof OreDictStack) {
			recipes.put(((OreDictStack) in).name, new Pair(out, fluid));
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<Object, Pair> rec = (Entry<Object, Pair>) recipe;
		Object in = rec.getKey();
		Pair<ItemStack, FluidStack> Pair = rec.getValue();
		ItemStack output = Pair.key;
		FluidStack fluid = Pair.value;

		writer.name("input");
		if(in instanceof String) {
			this.writeAStack(new OreDictStack((String) in), writer);
		} else if(in instanceof ComparableStack) {
			this.writeAStack((ComparableStack) in, writer);
		}
		if(output != null) {
			writer.name("output");
			this.writeItemStack(output, writer);
		}
		if(fluid != null) {
			writer.name("fluid");
			this.writeFluidStack(fluid, writer);
		}
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}
}
