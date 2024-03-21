package com.hbm.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.itempool.ItemPool;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolConfigJSON {

	public static final Gson gson = new Gson();
	
	public static void initialize() {
		
		//writes the defaults
		ItemPool.initialize();
		
		File folder = MainRegistry.configHbmDir;

		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmItemPools.json");
		File template = new File(folder.getAbsolutePath() + File.separatorChar + "_hbmItemPools.json");
		
		if(!config.exists()) {
			writeDefault(template);
		} else {
			readConfig(config);
		}
	}
	
	private static void writeDefault(File file) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("  ");
			writer.beginObject();
			writer.name("description").value("Format is as follows: First object is an array representing the itemstack in question, same rules apply here as they do for recipe configs but with one difference: Stacks accept NBT. NBT is contained in {curly brackets}, the format is the same as it is for the /give command. After the stack comes the minimum amount of items, then the maximum (the stack's own stacksize value is ignored). The final number is the weight, an item with a weight of 3 is 3x as likely to appear than an item with a weight of 1.");
			writer.name("pools").beginObject();
			
			for(Entry<String, ItemPool> entry : ItemPool.pools.entrySet()) {
				writer.name(entry.getKey()).beginArray();
				
				for(WeightedRandomChestContent content : entry.getValue().pool) {
					writer.setIndent("  ");
					writer.beginArray();
					writer.setIndent("");
					writeItemStack(content.theItemId, writer);
					writer.setIndent("");
					writer.value(content.theMinimumChanceToGenerateItem);
					writer.value(content.theMaximumChanceToGenerateItem);
					writer.value(content.itemWeight);
					writer.endArray();
				}
				
				writer.setIndent("  ");
				writer.endArray();
			}
			
			writer.endObject();
			writer.endObject();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void readConfig(File file) {
		HashMap<String, ItemPool> newPools = new HashMap();
		try {
			JsonObject json = gson.fromJson(new FileReader(file), JsonObject.class);
			JsonObject pools = json.get("pools").getAsJsonObject();
			
			for(Entry<String, JsonElement> entry : pools.entrySet()) {
				
				String poolName = entry.getKey();
				ItemPool pool = new ItemPool();
				pool.name = poolName;
				
				for(JsonElement poolEntry : entry.getValue().getAsJsonArray()) {
					JsonArray array = poolEntry.getAsJsonArray();
					ItemStack stack = readItemStack(array.get(0).getAsJsonArray());
					int min = array.get(1).getAsInt();
					int max = array.get(2).getAsInt();
					int weight = array.get(3).getAsInt();
					pool.add(stack, min, max, weight);
				}
				
				pool.build();
				newPools.put(poolName, pool);
			}
			
			ItemPool.pools = newPools;
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void writeItemStack(ItemStack stack, JsonWriter writer) throws IOException {
		writer.beginArray();
		writer.setIndent("");
		writer.value(Item.itemRegistry.getNameForObject(stack.getItem()));
		if(stack.stackSize != 1 || stack.getItemDamage() != 0 || stack.hasTagCompound()) writer.value(stack.stackSize);
		if(stack.getItemDamage() != 0 || stack.hasTagCompound()) writer.value(stack.getItemDamage());
		if(stack.hasTagCompound()) writer.value(stack.stackTagCompound.toString());
		writer.endArray();
		writer.setIndent("  ");
	}
	
	public static ItemStack readItemStack(JsonArray array) {
		try {
			Item item = (Item) Item.itemRegistry.getObject(array.get(0).getAsString());
			int stacksize = array.size() > 1 ? array.get(1).getAsInt() : 1;
			int meta = array.size() > 2 ? array.get(2).getAsInt() : 0;
			if(item != null) {
				ItemStack stack =  new ItemStack(item, stacksize, meta);
				if(array.size() > 3) {
					String tag = array.get(3).getAsString();
					NBTBase nbt = JsonToNBT.func_150315_a(tag);
					if(nbt instanceof NBTTagCompound) {
						stack.stackTagCompound = (NBTTagCompound) nbt;
					}
				}
				return stack;
			}
		} catch(Exception ex) { }
		MainRegistry.logger.error("Error reading stack array " + array.toString() + " - defaulting to NOTHING item!");
		return new ItemStack(ModItems.nothing);
	}
}
