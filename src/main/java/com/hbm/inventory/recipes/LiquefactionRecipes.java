package com.hbm.inventory.recipes;

import static com.hbm.inventory.OreDictManager.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class LiquefactionRecipes extends SerializableRecipe {

	public static HashMap<Object, FluidStack> recipes = new HashMap();

	@Override
	public void registerDefaults() {
		
		//oil processing
		recipes.put(COAL.gem(),										new FluidStack(100, Fluids.COALOIL));
		recipes.put(COAL.dust(),									new FluidStack(100, Fluids.COALOIL));
		recipes.put(LIGNITE.gem(),									new FluidStack(50, Fluids.COALOIL));
		recipes.put(LIGNITE.dust(),									new FluidStack(50, Fluids.COALOIL));
		recipes.put(KEY_OIL_TAR,									new FluidStack(75, Fluids.BITUMEN));
		recipes.put(KEY_CRACK_TAR,									new FluidStack(100, Fluids.BITUMEN));
		recipes.put(KEY_COAL_TAR,									new FluidStack(50, Fluids.BITUMEN));
		recipes.put(KEY_LOG,										new FluidStack(100, Fluids.MUG));
		recipes.put(KNO.dust(),										new FluidStack(750, Fluids.NITRIC_ACID));
		recipes.put(NA.dust(),										new FluidStack(100, Fluids.SODIUM));
		recipes.put(PB.ingot(),										new FluidStack(100, Fluids.LEAD));
		recipes.put(PB.dust(),										new FluidStack(100, Fluids.LEAD));
		//general utility recipes because why not
		recipes.put(new ComparableStack(Blocks.netherrack),			new FluidStack(250, Fluids.LAVA));
		recipes.put(new ComparableStack(Blocks.cobblestone),		new FluidStack(250, Fluids.LAVA));
		recipes.put(new ComparableStack(Blocks.stone),				new FluidStack(250, Fluids.LAVA));
		recipes.put(new ComparableStack(Blocks.obsidian),			new FluidStack(500, Fluids.LAVA));
		recipes.put(new ComparableStack(Items.snowball),			new FluidStack(125, Fluids.WATER));
		recipes.put(new ComparableStack(Blocks.snow),				new FluidStack(500, Fluids.WATER));
		recipes.put(new ComparableStack(Blocks.ice),				new FluidStack(1000, Fluids.WATER));
		recipes.put(new ComparableStack(Blocks.packed_ice),			new FluidStack(1000, Fluids.WATER));
		recipes.put(new ComparableStack(Items.ender_pearl),			new FluidStack(100, Fluids.ENDERJUICE));
		recipes.put(new ComparableStack(ModItems.pellet_charged),	new FluidStack(4000, Fluids.HELIUM4));
		recipes.put(new ComparableStack(ModBlocks.ore_oil_sand),	new FluidStack(100, Fluids.BITUMEN));

		recipes.put(new ComparableStack(Items.sugar),				new FluidStack(100, Fluids.ETHANOL));
		recipes.put(new ComparableStack(ModBlocks.plant_flower, 1, 3), new FluidStack(150, Fluids.ETHANOL));
		recipes.put(new ComparableStack(ModBlocks.plant_flower, 1, 4), new FluidStack(50, Fluids.ETHANOL));
		recipes.put(new ComparableStack(ModItems.biomass),			new FluidStack(125, Fluids.BIOGAS));
		recipes.put(new ComparableStack(ModItems.glyphid_gland_empty),			new FluidStack(2000, Fluids.BIOGAS));
		recipes.put(new ComparableStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), new FluidStack(100, Fluids.FISHOIL));
		recipes.put(new ComparableStack(Blocks.double_plant, 1, 0),	new FluidStack(100, Fluids.SUNFLOWEROIL));

		recipes.put(new ComparableStack(Items.wheat_seeds),			new FluidStack(50, Fluids.SEEDSLURRY));
		recipes.put(new ComparableStack(Blocks.tallgrass, 1, 1),	new FluidStack(100, Fluids.SEEDSLURRY));
		recipes.put(new ComparableStack(Blocks.tallgrass, 1, 2),	new FluidStack(100, Fluids.SEEDSLURRY));
		recipes.put(new ComparableStack(Blocks.vine),				new FluidStack(100, Fluids.SEEDSLURRY));
	}
	
	public static FluidStack getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(recipes.containsKey(comp))
			return recipes.get(comp);
		
		String[] dictKeys = comp.getDictKeys();
		comp = new ComparableStack(stack.getItem(), 1, OreDictionary.WILDCARD_VALUE);
		
		if(recipes.containsKey(comp))
			return recipes.get(comp);
		
		for(String key : dictKeys) {

			if(recipes.containsKey(key))
				return recipes.get(key);
		}
		
		if(stack.getItem() instanceof ItemFood) {
			ItemFood food = (ItemFood) stack.getItem();
			float saturation = food.func_150905_g(stack) * food.func_150906_h(stack) * 20; //food val * saturation mod * 2 (constant) * 10 (quanta)
			return new FluidStack(Fluids.SALIENT, (int) saturation);
		}
		
		return null;
	}

	public static HashMap<Object, ItemStack> getRecipes() {
		
		HashMap<Object, ItemStack> recipes = new HashMap<Object, ItemStack>();
		
		for(Entry<Object, FluidStack> entry : LiquefactionRecipes.recipes.entrySet()) {
			
			FluidStack out = entry.getValue();
			
			if(entry.getKey() instanceof String) {
				recipes.put(new OreDictStack((String)entry.getKey()), ItemFluidIcon.make(out.type, out.fill));
			} else {
				recipes.put(((ComparableStack)entry.getKey()).toStack(), ItemFluidIcon.make(out.type, out.fill));
			}
		}
		
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmLiquefactor.json";
	}
	
	@Override
	public String getComment() {
		return "As with most handlers, stacksizes for the inputs are ignored and default to 1.";
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
		AStack in = this.readAStack(obj.get("input").getAsJsonArray());
		FluidStack out = this.readFluidStack(obj.get("output").getAsJsonArray());
		
		if(in instanceof ComparableStack) {
			recipes.put(((ComparableStack) in).makeSingular(), out);
		} else if(in instanceof OreDictStack) {
			recipes.put(((OreDictStack) in).name, out);
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<Object, FluidStack> rec = (Entry<Object, FluidStack>) recipe;
		Object key = rec.getKey();
		
		writer.name("input");
		if(key instanceof String) {
			this.writeAStack(new OreDictStack((String) key), writer);
		} else if(key instanceof ComparableStack) {
			this.writeAStack((ComparableStack) key, writer);
		}
		
		writer.name("output");
		this.writeFluidStack(rec.getValue(), writer);
	}
}
