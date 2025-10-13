package com.hbm.hazard;

import static com.hbm.items.ModItems.*;
import static com.hbm.blocks.ModBlocks.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.hazard.modifier.HazardModifierFuelRadiation;
import com.hbm.hazard.type.*;
import com.google.gson.Gson;
import com.hbm.inventory.RecipesCommon;
import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.hbm.items.machine.ItemWatzPellet.EnumWatzType;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;

import java.io.*;
import net.minecraft.init.Blocks;
import java.util.*;

public class HazardBuilder {
	private final String FILE_NAME = "hbmHazards.json";
	private final String comment = "Template file, remove the underscore ('_') from the name to enable the config. Hazard types available: ASBESTOS, BLINDING, COAL, DIGAMMA, EXPLOSIVE, HOT, HYDROACTIVE and RADIATION. Values are a decimal point number over 0.";

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

		HashMap<Object, HazardData> newHazardItems;

		File hazFile = new File(hazDir.getAbsolutePath() + File.separatorChar + FILE_NAME);
		if (hazFile.exists() && hazFile.isFile()){
			try{
				newHazardItems = parseConfigFile(hazFile);
			} catch (IOException e){
				MainRegistry.logger.error("{} is malformed or inaccessible! Using default values.", FILE_NAME);
				newHazardItems = registerDefault();
			}
		}
		else {
			MainRegistry.logger.info("{} doesn't exists, creating a new one using default values...", FILE_NAME);
			File hazFileTemplate = new File(hazDir.getAbsolutePath() + File.separatorChar + "_" + FILE_NAME);
			newHazardItems = registerDefault();
			createDefaultFile(hazFileTemplate, newHazardItems);
		}

		// Remove old hazard items
		for (Map.Entry<Object, HazardData> set : hazardItems.entrySet()){
			if(set.getKey() instanceof String)
				HazardSystem.oreMap.remove(set.getKey());
			if(set.getKey() instanceof Item)
				HazardSystem.itemMap.remove(set.getKey());
			if(set.getKey() instanceof Block)
				HazardSystem.itemMap.remove(Item.getItemFromBlock((Block)set.getKey()));
			if(set.getKey() instanceof ItemStack)
				HazardSystem.stackMap.remove(new RecipesCommon.ComparableStack((ItemStack)set.getKey()));
			if(set.getKey() instanceof RecipesCommon.ComparableStack)
				HazardSystem.stackMap.remove((RecipesCommon.ComparableStack)set.getKey());
		}

		// Add new hazard items
		hazardItems.clear();
		hazardItems.putAll(newHazardItems);

		for(HashMap.Entry<Object, HazardData> set : newHazardItems.entrySet()){
			HazardSystem.register(set.getKey(), set.getValue());
		}
	}

	private HashMap<Object, HazardData> registerDefault() {
		HashMap<Object, HazardData> newHazardItems = new HashMap<>();

		newHazardItems.put("dustCoal", new HazardDataBuilder().addCoal(powder).getHazardData());
		newHazardItems.put("dustTinyCoal", new HazardDataBuilder().addCoal(powder_tiny).getHazardData());
		newHazardItems.put("dustLignite", new HazardDataBuilder().addCoal(powder).getHazardData());
		newHazardItems.put("dustTinyLignite", new HazardDataBuilder().addCoal(powder_tiny).getHazardData());

		newHazardItems.put(Items.gunpowder, new HazardDataBuilder().addExplosive(1F).getHazardData());
		newHazardItems.put(Blocks.tnt, new HazardDataBuilder().addExplosive(4F).getHazardData());
		newHazardItems.put(Items.pumpkin_pie, new HazardDataBuilder().addExplosive(1F).getHazardData());

		newHazardItems.put(ball_dynamite, new HazardDataBuilder().addExplosive(2F).getHazardData());
		newHazardItems.put(stick_dynamite, new HazardDataBuilder().addExplosive(1F).getHazardData());
		newHazardItems.put(stick_tnt, new HazardDataBuilder().addExplosive(1.5F).getHazardData());
		newHazardItems.put(stick_semtex, new HazardDataBuilder().addExplosive(2.5F).getHazardData());
		newHazardItems.put(stick_c4, new HazardDataBuilder().addExplosive(2.5F).getHazardData());

		newHazardItems.put(cordite, new HazardDataBuilder().addExplosive(2F).getHazardData());
		newHazardItems.put(ballistite, new HazardDataBuilder().addExplosive(1F).getHazardData());

		newHazardItems.put(insert_polonium, new HazardDataBuilder().addRadiation(100F).getHazardData());

		newHazardItems.put(demon_core_open, new HazardDataBuilder().addRadiation(5F).getHazardData());
		newHazardItems.put(demon_core_closed, new HazardDataBuilder().addRadiation(100_000F).getHazardData());
		newHazardItems.put(lamp_demon, new HazardDataBuilder().addRadiation(100_000F).getHazardData());

		newHazardItems.put(cell_tritium, new HazardDataBuilder().addRadiation(0.001F).getHazardData());
		newHazardItems.put(cell_sas3, new HazardDataBuilder().addRadiation(HazardRegistry.sas3).addBlinding(60F).getHazardData());
		newHazardItems.put(cell_balefire, new HazardDataBuilder().addRadiation(50F).getHazardData());
		newHazardItems.put(powder_balefire, new HazardDataBuilder().addRadiation(500F).getHazardData());
		newHazardItems.put(egg_balefire_shard, new HazardDataBuilder().addRadiation(HazardRegistry.bf * HazardRegistry.nugget).getHazardData());
		newHazardItems.put(egg_balefire, new HazardDataBuilder().addRadiation(HazardRegistry.bf * HazardRegistry.ingot).getHazardData());

		newHazardItems.put(solid_fuel_bf, new HazardDataBuilder().addRadiation(1000F).getHazardData()); //roughly the amount of the balefire shard diluted in 250mB of rocket fuel
		newHazardItems.put(solid_fuel_presto_bf, new HazardDataBuilder().addRadiation(2000F).getHazardData());
		newHazardItems.put(solid_fuel_presto_triplet_bf, new HazardDataBuilder().addRadiation(6000F).getHazardData());

		newHazardItems.put(nuclear_waste_long, new HazardDataBuilder().addRadiation(5F).getHazardData());
		newHazardItems.put(nuclear_waste_long_tiny, new HazardDataBuilder().addRadiation(0.5F).getHazardData());
		newHazardItems.put(nuclear_waste_short, new HazardDataBuilder().addRadiation(30F).addHot(5F).getHazardData());
		newHazardItems.put(nuclear_waste_short_tiny, new HazardDataBuilder().addRadiation(3F).addHot(5F).getHazardData());
		newHazardItems.put(nuclear_waste_long_depleted, new HazardDataBuilder().addRadiation(0.5F).getHazardData());
		newHazardItems.put(nuclear_waste_long_depleted_tiny, new HazardDataBuilder().addRadiation(0.05F).getHazardData());
		newHazardItems.put(nuclear_waste_short_depleted, new HazardDataBuilder().addRadiation(3F).getHazardData());
		newHazardItems.put(nuclear_waste_short_depleted_tiny, new HazardDataBuilder().addRadiation(0.3F).getHazardData());

		newHazardItems.put(scrap_nuclear, new HazardDataBuilder().addRadiation(1F).getHazardData());
		newHazardItems.put(trinitite, new HazardDataBuilder().addRadiation(HazardRegistry.trn * HazardRegistry.ingot).getHazardData());
		newHazardItems.put(block_trinitite, new HazardDataBuilder().addRadiation(HazardRegistry.trn * HazardRegistry.block).getHazardData());
		newHazardItems.put(yellow_barrel, new HazardDataBuilder().addRadiation(HazardRegistry.wst * HazardRegistry.ingot * 10).getHazardData());
		newHazardItems.put(billet_nuclear_waste, new HazardDataBuilder().addRadiation(HazardRegistry.wst * HazardRegistry.billet).getHazardData());
		newHazardItems.put(nuclear_waste, new HazardDataBuilder().addRadiation(HazardRegistry.wst * HazardRegistry.ingot).getHazardData());
		newHazardItems.put(nuclear_waste_tiny, new HazardDataBuilder().addRadiation(HazardRegistry.wst * HazardRegistry.nugget).getHazardData());
		newHazardItems.put(nuclear_waste_vitrified, new HazardDataBuilder().addRadiation(HazardRegistry.wstv * HazardRegistry.ingot).getHazardData());
		newHazardItems.put(nuclear_waste_vitrified_tiny, new HazardDataBuilder().addRadiation(HazardRegistry.wstv * HazardRegistry.nugget).getHazardData());
		newHazardItems.put(block_waste, new HazardDataBuilder().addRadiation(HazardRegistry.wst * HazardRegistry.block).getHazardData());
		newHazardItems.put(block_waste_painted, new HazardDataBuilder().addRadiation(HazardRegistry.wst * HazardRegistry.block).getHazardData());
		newHazardItems.put(block_waste_vitrified, new HazardDataBuilder().addRadiation(HazardRegistry.wstv * HazardRegistry.block).getHazardData());
		newHazardItems.put(ancient_scrap, new HazardDataBuilder().addRadiation(150F).getHazardData());
		newHazardItems.put(block_corium, new HazardDataBuilder().addRadiation(150F).getHazardData());
		newHazardItems.put(block_corium_cobble, new HazardDataBuilder().addRadiation(150F).getHazardData());

		newHazardItems.put(new ItemStack(sellafield, 1, 0), new HazardDataBuilder().addRadiation(0.5F).getHazardData());
		newHazardItems.put(new ItemStack(sellafield, 1, 1), new HazardDataBuilder().addRadiation(1F).getHazardData());
		newHazardItems.put(new ItemStack(sellafield, 1, 2), new HazardDataBuilder().addRadiation(2.5F).getHazardData());
		newHazardItems.put(new ItemStack(sellafield, 1, 3), new HazardDataBuilder().addRadiation(4F).getHazardData());
		newHazardItems.put(new ItemStack(sellafield, 1, 4), new HazardDataBuilder().addRadiation(5F).getHazardData());
		newHazardItems.put(new ItemStack(sellafield, 1, 5), new HazardDataBuilder().addRadiation(10F).getHazardData());

		newHazardItems.put(ore_sellafield_radgem, new HazardDataBuilder().addRadiation(25F).getHazardData());
		newHazardItems.put(gem_rad, new HazardDataBuilder().addRadiation(25F).getHazardData());

		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.NATURAL_URANIUM_FUEL.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.u, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 11.5F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.URANIUM_FUEL.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.uf, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 10F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.TH232.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.th232, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.thf)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.THORIUM_FUEL.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.thf, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 7.5F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.MOX_FUEL.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.mox, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 10F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.PLUTONIUM_FUEL.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.puf, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 12.5F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.U233_FUEL.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.u233, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 10F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.U235_FUEL.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.u235, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 11F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.LES_FUEL.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.saf, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 15F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.LITHIUM.ordinal()), new HazardDataBuilder()
			.addRadiation(0, new HazardModifier[]{
				new HazardModifierFuelRadiation(0.001F)
			})
			.getHazardData());
		newHazardItems.put(new ItemStack(rod_zirnox, 1, EnumZirnoxType.ZFB_MOX.ordinal()), new HazardDataBuilder()
			.addRadiation(HazardRegistry.mox, new HazardModifier[]{
				new HazardModifierFuelRadiation(HazardRegistry.wst * 5F)
			})
			.getHazardData());

		return newHazardItems;
	}

	private HashMap<Object, HazardData> parseConfigFile(File hazFile) throws IOException {
		HashMap<Object, HazardData> newHazardItems = new HashMap<>();

		JsonObject json = gson.fromJson(new FileReader(hazFile), JsonObject.class);
		JsonArray itemHazards = json.get("itemHazards").getAsJsonArray();

		// Iterate over all item hazards in the config file
		for(JsonElement item : itemHazards){
			JsonObject obj = (JsonObject) item;
			HazardData hazData;
			try {
				hazData = extractHazardEnrties(obj.get("hazards").getAsJsonArray());
			}
			catch (ClassCastException e){
				MainRegistry.logger.warn("Invalid hazard data format! Skipping: {}", itemHazards);
				continue;
			}

			String itemName = obj.get("itemName").getAsString();

			// Check if it is an oreDict item
			String[] parts = itemName.split(":");
			if (parts.length < 2){
				newHazardItems.put(itemName, hazData);
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

			newHazardItems.put(completeItem, hazData);
		}
		return newHazardItems;
	}

	private HazardData extractHazardEnrties(JsonArray entries) throws ClassCastException{
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

	private void createDefaultFile(File hazFile, HashMap<Object, HazardData> hazardItemsWrite){
		registerDefault();

		try{
			JsonWriter writer = new JsonWriter(new FileWriter(hazFile));
			writer.setIndent("  ");
			writer.beginObject();

			writer.name("comment").value(comment);

			writer.name("itemHazards").beginArray();

			// Write all items and their hazards
			for(Map.Entry<Object, HazardData> set : hazardItemsWrite.entrySet()){
				writer.beginObject();

				// Determine the item name
				writer.name("itemName");
				Object o = set.getKey();
				if(o instanceof String) {
					writer.value((String) o);
					writer.name("dataTag").value(0);
				}
				else if(o instanceof Item){
					writer.value(getModId((Item)o) + ":" + getItemName((Item)o));
					writer.name("dataTag").value(0);
				}
				else if(o instanceof Block) {
					Item item = Item.getItemFromBlock((Block)o);
					writer.value(getModId(item) + ":" + getItemName(item));
					writer.name("dataTag").value(0);
				}
				else if(o instanceof ItemStack) {
					ItemStack stack = (ItemStack)o;
					writer.value(getModId(stack) + ":" + getItemName(stack));
					writer.name("dataTag").value(stack.getItemDamage());
				}
				else if(o instanceof RecipesCommon.ComparableStack) {
					ItemStack stack = ((RecipesCommon.ComparableStack)o).toStack();
					writer.value(getModId(stack) + ":" + getItemName(stack));
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

	private String getItemName(Item item){
		GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(item);
		return id == null || id.name.isEmpty() ? null : id.name;
	}

	private String getItemName(ItemStack stack){
		return getItemName(stack.getItem());
	}

	private String getModId(Item item){
		GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(item);
		return id == null || id.modId.isEmpty() ? "minecraft" : id.modId;
	}

	private String getModId(ItemStack stack){
		return getModId(stack.getItem());
	}

}
