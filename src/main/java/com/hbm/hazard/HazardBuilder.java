package com.hbm.hazard;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.hazard.type.*;
import com.google.gson.Gson;
import com.hbm.inventory.RecipesCommon;
import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.util.*;

public class HazardBuilder {
	private final String FILE_NAME = "hbmHazards.json";
	private final HashMap<Object, HazardData> hazardItems = new HashMap<>();
	private final float powder = HazardRegistry.powder;
	private final float powder_tiny = HazardRegistry.powder_tiny;
	private final Gson gson = new Gson();

	public void initialize(){
		File hazDir = new File(MainRegistry.configHbmDir.getAbsolutePath());

		if(!hazDir.exists() && !hazDir.mkdir()){
			throw new IllegalStateException("Unable to make hazards directory: " + hazDir.getAbsolutePath());
		}

		MainRegistry.logger.info("Starting hazard init!");

		File hazFile = new File(hazDir.getAbsolutePath() + File.separatorChar + FILE_NAME);
		if (hazFile.exists() && hazFile.isFile()){
			try{
				parseConfigFile(hazFile);
			} catch (IOException e){
				MainRegistry.logger.error("{} is malformed or inaccessible! Using default values.", FILE_NAME);
				registerDefault();
			}
		}
		else {
			MainRegistry.logger.info("{} doesn't exists, creating a new one using default values...", FILE_NAME);
			File hazFileTemplate = new File(hazDir.getAbsolutePath() + File.separatorChar + "_" + FILE_NAME);
			createDefaultFile(hazFileTemplate);
		}

		for(HashMap.Entry<Object, HazardData> set : hazardItems.entrySet()){
			HazardSystem.register(set.getKey(), set.getValue());
		}
	}

	private void registerDefault() {
		hazardItems.clear();

		hazardItems.put("dustCoal", new HazardDataBuilder().addCoalHazard(powder).getHazardData());
		hazardItems.put("dustTinyCoal", new HazardDataBuilder().addCoalHazard(powder_tiny).getHazardData());
		hazardItems.put("dustLignite", new HazardDataBuilder().addCoalHazard(powder).getHazardData());
		hazardItems.put("dustTinyLignite", new HazardDataBuilder().addCoalHazard(powder_tiny).getHazardData());
	}

	private void parseConfigFile(File hazFile) throws IOException {
		JsonObject json = gson.fromJson(new FileReader(hazFile), JsonObject.class);
		JsonArray itemHazards = json.get("itemHazards").getAsJsonArray();

		// Iterate over all item hazards in the config file
		for(JsonElement item : itemHazards){
			JsonObject obj = (JsonObject) item;
			HazardData hazData = extractHazardEnrties(obj.get("hazards").getAsJsonArray());

			String itemName = obj.get("itemName").getAsString();

			// Check if it is an oreDict item
			String[] parts = itemName.split(":");
			if (parts.length < 2){
				hazardItems.put(itemName, hazData);
				continue;
			}

			// Add as ItemStack instead
			Item possibleItem = Compat.tryLoadItem(parts[0], parts[1]);
			if (possibleItem == null){
				MainRegistry.logger.warn("Item {} does not exists, skipping.", itemName);
				continue;
			}

			int dataTag = obj.get("dataTag").getAsInt();
			ItemStack completeItem = new ItemStack(possibleItem, 1, dataTag);

			hazardItems.put(completeItem, hazData);
		}
	}

	private HazardData extractHazardEnrties(JsonArray entries){
		HazardData toReturn = new HazardData();

		// Iterate over all hazards that should be applied to the item
		for(JsonElement entry : entries){
			JsonObject obj = (JsonObject) entry;

			HazardTypeBase type;
			String typeData = obj.get("type").getAsString();
			switch (typeData) {
				case "ASBESTOS":
					type = HazardRegistry.ASBESTOS;
					break;
				case "BLINDING":
					type = HazardRegistry.BLINDING;
					break;
				case "COAL":
					type = HazardRegistry.COAL;
					break;
				case "DIGAMMA":
					type = HazardRegistry.DIGAMMA;
					break;
				case "EXPLOSIVE":
					type = HazardRegistry.EXPLOSIVE;
					break;
				case "HOT":
					type = HazardRegistry.HOT;
					break;
				case "HYDROACTIVE":
					type = HazardRegistry.HYDROACTIVE;
					break;
				case "RADIATION":
					type = HazardRegistry.RADIATION;
					break;
				default:
					MainRegistry.logger.warn("Hazard type {} doesn't exists skipping.");
					continue;
			}

			float level = obj.get("level").getAsFloat();
			toReturn.addEntry(type, level);
		}

		return toReturn;
	}

	private void createDefaultFile(File hazFile){
		registerDefault();

		try{
			JsonWriter writer = new JsonWriter(new FileWriter(hazFile));
			writer.setIndent("  ");
			writer.beginObject();

			writer.name("comment").value("Template file, remove the underscore ('_') from the name to enable the config.");

			writer.name("itemHazards").beginArray();

			// Write all items and their hazards
			for(Map.Entry<Object, HazardData> set : hazardItems.entrySet()){
				writer.beginObject();

				// Determine the item name
				writer.name("itemName");
				Object o = set.getKey();
				if(o instanceof String) {
					writer.value((String) o);
					writer.name("dataTag").value(0);
				}
				else if(o instanceof Item){
					writer.value(((Item)o).getUnlocalizedName());
					writer.name("dataTag").value(0);
				}
				else if(o instanceof Block) {
					writer.value(((Block)o).getUnlocalizedName());
					writer.name("dataTag").value(0);
				}
				else if(o instanceof ItemStack) {
					ItemStack stack = (ItemStack)o;
					writer.value(stack.getItem().getUnlocalizedName());
					writer.name("dataTag").value(stack.getItemDamage());
				}
				else if(o instanceof RecipesCommon.ComparableStack) {
					ItemStack stack = ((RecipesCommon.ComparableStack)o).toStack();
					writer.value(stack.getItem().getUnlocalizedName());
					writer.name("dataTag").value(stack.getItemDamage());
				}
				else {
					writer.value("undefined");
					writer.name("dataTag").value(0);
				}

				// Add all hazards and their levels
				writer.name("hazards").beginArray();
				for(HazardEntry he : set.getValue().entries){
					writer.beginObject();
					writer.name("type");
					if(he.type instanceof HazardTypeAsbestos){
						writer.value("ASBESTOS");
					}
					if(he.type instanceof HazardTypeBlinding){
						writer.value("BLINDING");
					}
					if(he.type instanceof HazardTypeCoal){
						writer.value("COAL");
					}
					if(he.type instanceof HazardTypeDigamma){
						writer.value("DIGAMMA");
					}
					if(he.type instanceof HazardTypeExplosive){
						writer.value("EXPLOSIVE");
					}
					if(he.type instanceof HazardTypeHot){
						writer.value("HOT");
					}
					if(he.type instanceof HazardTypeHydroactive){
						writer.value("HYDROACTIVE");
					}
					if(he.type instanceof HazardTypeRadiation){
						writer.value("RADIATION");
					}
					writer.name("level").value(he.baseLevel);
					writer.endObject();
				}
				writer.endArray();


				writer.endObject();
			}

			writer.endArray();
			writer.endObject();
			writer.close();
		} catch(IOException e){
			MainRegistry.logger.error("An error occurred while tyring to write hazards to {}!", FILE_NAME);
			e.printStackTrace();
		}

	}

}
