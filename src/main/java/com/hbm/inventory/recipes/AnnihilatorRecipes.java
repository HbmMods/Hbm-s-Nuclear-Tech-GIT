package com.hbm.inventory.recipes;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.items.machine.ItemBlueprints;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.Tuple.Pair;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class AnnihilatorRecipes extends SerializableRecipe {
	
	public static HashMap<Object, AnnihilatorRecipe> recipes = new HashMap();
	
	/*
	 * MILESTONES
	 * STEEL -> DERRICK (ASSEM)
	 * SILICON -> CHIPS (PRECASS)
	 * PLASTIC -> CRACKER, COKER (ASSEM)
	 * RUBBER -> FRACKER (ASSEM)
	 * URANIUM -> GASCENT (ASSEM)
	 * FERRO -> RBMK (ASSEM)
	 * STRONTIUM -> ATOMIC CLOCK (PRECASS)
	 * BISMUTH -> BIS CHIPS (PRECASS)
	 * HARDPLASTIC -> OIL 3.5 (ASSEM)
	 * TCALLOY -> FUSION, WATZ (ASSEM)
	 * IONS -> Q CHIPS (PRECASS) PA (ASSEM)
	 * CHLOROPHYTE -> MHDT, ICF (ASSEM)
	 * 50BMG -> TURRETS (ASSEM)
	 * ARTY -> ARTY (ASSEM)
	 * CONTROLLER -> NUKES (ASSEM)
	 */

	@Override
	public void registerDefaults() {

		if(GeneralConfig.enable528) {
			recipes.put(STEEL.ingot(),					new AnnihilatorRecipe(new Pair(new BigInteger("256"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "steel"))));
			recipes.put(SI.billet(),					new AnnihilatorRecipe(new Pair(new BigInteger("256"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "chip"))));
			recipes.put(BI.nugget(),					new AnnihilatorRecipe(new Pair(new BigInteger("128"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "chip_bismoid"))));
			recipes.put(ModItems.pellet_charged,		new AnnihilatorRecipe(new Pair(new BigInteger("1024"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "chip_quantum"))));
	
			recipes.put(U.billet(),						new AnnihilatorRecipe(new Pair(new BigInteger("256"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "gascent"))));
			recipes.put(ANY_PLASTIC.ingot(),			new AnnihilatorRecipe(new Pair(new BigInteger("512"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "plastic"))));
			recipes.put(RUBBER.ingot(),					new AnnihilatorRecipe(new Pair(new BigInteger("512"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "rubber"))));
			recipes.put(FERRO.ingot(),					new AnnihilatorRecipe(new Pair(new BigInteger("1024"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "ferrouranium"))));
			recipes.put(SR.dust(),						new AnnihilatorRecipe(new Pair(new BigInteger("256"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "strontium"))));
			recipes.put(ANY_HARDPLASTIC.ingot(),		new AnnihilatorRecipe(new Pair(new BigInteger("1024"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "hardplastic"))));
			recipes.put(ANY_RESISTANTALLOY.ingot(),		new AnnihilatorRecipe(new Pair(new BigInteger("1024"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "tcalloy"))));
			recipes.put(ModItems.powder_chlorophyte,	new AnnihilatorRecipe(new Pair(new BigInteger("1024"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "chlorophyte"))));
			
			recipes.put(new ComparableStack(ModItems.ammo_standard, 1, EnumAmmo.BMG50_FMJ),		new AnnihilatorRecipe(new Pair(new BigInteger("256"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "bmg"))));
			recipes.put(new ComparableStack(ModItems.ammo_arty, 1, 0),							new AnnihilatorRecipe(new Pair(new BigInteger("128"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "arty"))));
			recipes.put(new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER),	new AnnihilatorRecipe(new Pair(new BigInteger("128"), ItemBlueprints.make(GenericRecipes.POOL_PREFIX_528 + "controller"))));
		}
	}

	@Override public String getFileName() { return "hbmAnnihilator.json"; }
	@Override public Object getRecipeObject() { return recipes; }
	@Override public void deleteRecipes() { recipes.clear(); }

	public static HashMap getRecipes() {

		HashMap<Object, Object> recipes = new HashMap();
		
		for(Entry<Object, AnnihilatorRecipe> entry : AnnihilatorRecipes.recipes.entrySet()) {
			for(Pair<BigInteger, ItemStack> milestone : entry.getValue().milestones) {
				
				Object input = new ItemStack[1];
	
				if(entry.getKey() instanceof Item) input = new ItemStack((Item) entry.getKey());
				if(entry.getKey() instanceof ComparableStack) input = ((ComparableStack) entry.getKey()).toStack();
				if(entry.getKey() instanceof FluidType) input = ItemFluidIcon.make((FluidType) entry.getKey(), 0);
				if(entry.getKey() instanceof String) input = new OreDictStack((String) entry.getKey()).extractForNEI();
				
				if(input == null) continue;
				
				if(input instanceof ItemStack) {
					ItemStackUtil.addTooltipToStack((ItemStack) input, EnumChatFormatting.RED + String.format(Locale.US, "%,d", milestone.getKey()));
				}
				if(input instanceof List) {
					List<ItemStack> list = (List<ItemStack>) input;
					for(ItemStack stack : list) ItemStackUtil.addTooltipToStack(stack, EnumChatFormatting.RED + String.format(Locale.US, "%,d", milestone.getKey()));
					input = new ItemStack[][] { list.toArray(new ItemStack[0]) };
				}
				
				recipes.put(input, milestone.getValue().copy());
			}
		}
		
		return recipes;
	}
	
	/**
	 * If prevAmount is null, a payout is guaranteed if the currentAmount matches or exceeds the requirement.
	 * Otherwise, the prevAmount needs to be smaller than the requirement to count.
	 * @param stack
	 * @param prevAmount
	 * @param currentAmount
	 * @return
	 */
	public static ItemStack getHighestPayoutFromKey(Object key, BigInteger prevAmount, BigInteger currentAmount) {
		AnnihilatorRecipe recipe = recipes.get(key);
		if(recipe != null) {
			ItemStack payout = getHighestPayoutFromRecipe(recipe, prevAmount, currentAmount);
			if(payout != null) return payout;
		}
		return null;
	}
	
	public static ItemStack getHighestPayoutFromStack(ItemStack stack, BigInteger prevAmount, BigInteger currentAmount) {
		
		if(stack.getItem() instanceof IItemFluidIdentifier) {
			IItemFluidIdentifier id = (IItemFluidIdentifier) stack.getItem();
			return getHighestPayoutFromFluid(id.getType(null, 0, 0, 0, stack), prevAmount, currentAmount);
		}
		
		List<String> dictKeys = ItemStackUtil.getOreDictNames(stack);
		
		AnnihilatorRecipe recipe;
		
		for(String key : dictKeys) {
			recipe = recipes.get(key);
			if(recipe != null) {
				ItemStack payout = getHighestPayoutFromRecipe(recipe, prevAmount, currentAmount);
				if(payout != null) return payout;
			}
		}
		
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		recipe = recipes.get(comp);
		if(recipe != null) {
			ItemStack payout = getHighestPayoutFromRecipe(recipe, prevAmount, currentAmount);
			if(payout != null) return payout;
		}
		
		recipe = recipes.get(stack.getItem());
		if(recipe != null) {
			ItemStack payout = getHighestPayoutFromRecipe(recipe, prevAmount, currentAmount);
			if(payout != null) return payout;
		}
		
		return null;
	}
	
	public static ItemStack getHighestPayoutFromFluid(FluidType fluid, BigInteger prevAmount, BigInteger currentAmount) {
		
		AnnihilatorRecipe recipe = recipes.get(fluid);
		if(recipe != null) {
			ItemStack payout = getHighestPayoutFromRecipe(recipe, prevAmount, currentAmount);
			if(payout != null) return payout;
		}
		
		return null;
	}
	
	public static ItemStack getHighestPayoutFromRecipe(AnnihilatorRecipe recipe, BigInteger prevAmount, BigInteger currentAmount) {
		
		BigInteger highestYet = BigInteger.ZERO;
		ItemStack highestPayout = null;
		for(Pair<BigInteger, ItemStack> milestone : recipe.milestones) {
			if(prevAmount != null && prevAmount.compareTo(milestone.getKey()) != -1) continue; // if prevAmount is set and GEQUAL the requirement, skip
			if(currentAmount.compareTo(highestYet) != 1) continue; // if currentAmount is GEQUAL to the largest already existing step, skip
			if(currentAmount.compareTo(milestone.getKey()) != -1) { // if currentAmount is GEQUAL to the milestone, accept it
				highestYet = milestone.getKey();
				highestPayout = milestone.getValue();
			}
		}
		
		return highestPayout != null ? highestPayout.copy() : null;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		
		JsonObject key = obj.get("key").getAsJsonObject();
		Object keyObject = null;
		String keyType = key.get("type").getAsString();

		if("item".equals(keyType)) {
			keyObject = Item.itemRegistry.getObject(key.get("item").getAsString());
		}
		if("comp".equals(keyType)) {
			keyObject = new ComparableStack((Item) Item.itemRegistry.getObject(key.get("item").getAsString()), 1, key.get("meta").getAsInt());
		}
		if("fluid".equals(keyType)) {
			keyObject = Fluids.fromName(key.get("fluid").getAsString());
		}
		if("dict".equals(keyType)) {
			keyObject = key.get("dict").getAsString();
		}
		
		JsonArray milestones = obj.get("milestones").getAsJsonArray();
		List<Pair<BigInteger, ItemStack>> milestoneList = new ArrayList();
		
		for(JsonElement e : milestones) {
			JsonObject milestone = e.getAsJsonObject();
			milestoneList.add(new Pair(milestone.get("amount").getAsBigInteger(), this.readItemStack(milestone.get("payout").getAsJsonArray())));
		}
		
		if(keyObject != null) {
			AnnihilatorRecipe newRecipe = new AnnihilatorRecipe();
			newRecipe.milestones.addAll(milestoneList);
			this.recipes.put(keyObject, newRecipe);
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<Object, AnnihilatorRecipe> rec = (Entry<Object, AnnihilatorRecipe>) recipe;
		
		writer.name("key").beginObject();
		if(rec.getKey() instanceof Item) {
			Item item = (Item) rec.getKey();
			writer.name("type").value("item");
			writer.name("item").value(Item.itemRegistry.getNameForObject(item));
		}
		if(rec.getKey() instanceof ComparableStack) {
			ComparableStack comp = (ComparableStack) rec.getKey();
			writer.name("type").value("comp");
			writer.name("item").value(Item.itemRegistry.getNameForObject(comp.item));
			writer.name("meta").value(comp.meta);
		}
		if(rec.getKey() instanceof FluidType) {
			FluidType fluid = (FluidType) rec.getKey();
			writer.name("type").value("fluid");
			writer.name("fluid").value(fluid.getUnlocalizedName());
		}
		if(rec.getKey() instanceof String) {
			writer.name("type").value("dict");
			writer.name("dict").value((String) rec.getKey());
		}
		writer.endObject();
		
		writer.name("milestones").beginArray();
		
		for(Pair<BigInteger, ItemStack> milestone : rec.getValue().milestones) {
			writer.beginObject();
			writer.name("amount").value(milestone.getKey());
			writer.name("payout");
			this.writeItemStack(milestone.getValue(), writer);
			writer.endObject();
		}
		writer.endArray();
	}
	
	public static class AnnihilatorRecipe {
		
		public List<Pair<BigInteger, ItemStack>> milestones = new ArrayList();
		
		public AnnihilatorRecipe(Pair<BigInteger, ItemStack>... milestones) {
			for(Pair<BigInteger, ItemStack> milestone : milestones) this.milestones.add(milestone);
		}
	}
}
