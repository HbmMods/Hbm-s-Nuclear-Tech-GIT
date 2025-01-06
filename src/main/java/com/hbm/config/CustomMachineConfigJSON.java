package com.hbm.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration.ComponentDefinition;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.main.CraftingManager;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CustomMachineConfigJSON {

	public static final Gson gson = new Gson();
	public static HashMap<String, MachineConfiguration> customMachines = new HashMap();
	public static List<MachineConfiguration> niceList = new ArrayList();

	public static void initialize() {
		File folder = MainRegistry.configHbmDir;

		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmCustomMachines.json");

		if(!config.exists()) {
			writeDefault(config);
		}

		readConfig(config);
	}

	public static void writeDefault(File config) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(config));
			writer.setIndent("  ");
			writer.beginObject();
			writer.name("machines").beginArray();

			writer.beginObject();
			writer.name("recipeKey").value("paperPress");
			writer.name("unlocalizedName").value("paperPress");
			writer.name("localization").beginObject();
			writer.name("de_DE").value("Papierpresse");
			writer.endObject();
			writer.name("localizedName").value("Paper Press");
			writer.name("fluidInCount").value(1);
			writer.name("fluidInCap").value(1_000);
			writer.name("itemInCount").value(1);
			writer.name("fluidOutCount").value(0);
			writer.name("fluidOutCap").value(0);
			writer.name("itemOutCount").value(1);
			writer.name("generatorMode").value(false);
			writer.name("maxPollutionCap").value(100);
			writer.name("fluxMode").value(false);
			writer.name("recipeSpeedMult").value(1.0D);
			writer.name("recipeConsumptionMult").value(1.0D);
			writer.name("maxPower").value(10_000L);
			writer.name("maxHeat").value(0);

			writer.name("recipeShape").beginArray();
			writer.value("IPI").value("PCP").value("IPI");
			writer.endArray();

			writer.name("recipeParts").beginArray().setIndent("");
			writer.value("I");
			SerializableRecipe.writeAStack(new OreDictStack(OreDictManager.STEEL.ingot()), writer);
			writer.setIndent("");
			writer.value("P");
			SerializableRecipe.writeAStack(new OreDictStack(OreDictManager.STEEL.plate()), writer);
			writer.setIndent("");
			writer.value("C");
			SerializableRecipe.writeAStack(new ComparableStack(ModItems.circuit, 1, EnumCircuitType.BASIC), writer);
			writer.endArray().setIndent("  ");

			writer.name("components").beginArray();

			for(int x = -1; x <= 1; x++) {
				for(int y = -1; y <= 1; y++) {
					for(int z = 0; z <= 2; z++) {
						if(!(x == 0 && y == 0 && z == 1) && !(x == 0 && z == 0)) {
							writer.beginObject().setIndent("");
							writer.name("block").value(y == 0 ? "hbm:tile.cm_sheet" : "hbm:tile.cm_block");
							writer.name("x").value(x);
							writer.name("y").value(y);
							writer.name("z").value(z);
							writer.name("metas").beginArray();
							writer.value(0);
							writer.endArray();
							writer.endObject().setIndent("  ");
						}
					}
				}
			}

			writer.beginObject().setIndent("");
			writer.name("block").value("hbm:tile.cm_port");
			writer.name("x").value(0);
			writer.name("y").value(-1);
			writer.name("z").value(0);
			writer.name("metas").beginArray();
			writer.value(0);
			writer.endArray();
			writer.endObject().setIndent("  ");

			writer.beginObject().setIndent("");
			writer.name("block").value("hbm:tile.cm_port");
			writer.name("x").value(0);
			writer.name("y").value(1);
			writer.name("z").value(0);
			writer.name("metas").beginArray();
			writer.value(0);
			writer.endArray();
			writer.endObject().setIndent("  ");

			writer.endArray();
			writer.endObject();

			writer.endArray();
			writer.endObject();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void readConfig(File config) {

		try {
			JsonObject json = gson.fromJson(new FileReader(config), JsonObject.class);
			JsonArray machines = json.get("machines").getAsJsonArray();

			for(int i = 0; i < machines.size(); i++) {
				JsonObject machineObject = machines.get(i).getAsJsonObject();

				MachineConfiguration configuration = new MachineConfiguration();
				configuration.recipeKey = machineObject.get("recipeKey").getAsString();
				configuration.unlocalizedName = machineObject.get("unlocalizedName").getAsString();
				configuration.localizedName = machineObject.get("localizedName").getAsString();
				if(machineObject.has("localization")) {
					JsonObject localization = machineObject.get("localization").getAsJsonObject();
					for(Entry<String, JsonElement> entry : localization.entrySet()) {
						configuration.localization.put(entry.getKey(), entry.getValue().getAsString());
					}
				}
				configuration.fluidInCount = machineObject.get("fluidInCount").getAsInt();
				configuration.fluidInCap = machineObject.get("fluidInCap").getAsInt();
				configuration.itemInCount = machineObject.get("itemInCount").getAsInt();
				configuration.fluidOutCount = machineObject.get("fluidOutCount").getAsInt();
				configuration.fluidOutCap = machineObject.get("fluidOutCap").getAsInt();
				configuration.itemOutCount = machineObject.get("itemOutCount").getAsInt();
				configuration.generatorMode = machineObject.get("generatorMode").getAsBoolean();
				if(machineObject.has("maxPollutionCap")) configuration.maxPollutionCap = machineObject.get("maxPollutionCap").getAsInt();
				if(machineObject.has("fluxMode")) configuration.fluxMode = machineObject.get("fluxMode").getAsBoolean();
				configuration.recipeSpeedMult = machineObject.get("recipeSpeedMult").getAsDouble();
				configuration.recipeConsumptionMult = machineObject.get("recipeConsumptionMult").getAsDouble();
				configuration.maxPower = machineObject.get("maxPower").getAsLong();
				if(machineObject.has("maxHeat")) configuration.maxHeat = machineObject.get("maxHeat").getAsInt();

				if(machineObject.has("recipeShape") && machineObject.has("recipeParts")) {
					try {
						JsonArray recipeShape = machineObject.get("recipeShape").getAsJsonArray();
						JsonArray recipeParts = machineObject.get("recipeParts").getAsJsonArray();
	
						Object[] parts = new Object[recipeShape.size() + recipeParts.size()];
	
						for(int j = 0; j < recipeShape.size(); j++) {
							parts[j] = recipeShape.get(j).getAsString();
						}
	
						for(int j = 0; j < recipeParts.size(); j++) {
							Object o = null;
	
							if(j % 2 == 0) {
								o = recipeParts.get(j).getAsString().charAt(0); //god is dead and we killed him
							} else {
								AStack a = SerializableRecipe.readAStack(recipeParts.get(j).getAsJsonArray());
	
								if(a instanceof ComparableStack) o = ((ComparableStack) a).toStack();
								if(a instanceof OreDictStack) o = ((OreDictStack) a).name;
							}
	
							parts[j + recipeShape.size()] = o;
						}
	
						ItemStack stack = new ItemStack(ModBlocks.custom_machine, 1, i + 100);
						stack.stackTagCompound = new NBTTagCompound();
						stack.stackTagCompound.setString("machineType", configuration.unlocalizedName);
	
						CraftingManager.addRecipeAuto(stack, parts);
					} catch(Exception ex) {
						MainRegistry.logger.error("Caught exception trying to parse core recipe for custom machine " + configuration.unlocalizedName);
						MainRegistry.logger.error("recipeShape was" + machineObject.get("recipeShape").toString());
						MainRegistry.logger.error("recipeParts was" + machineObject.get("recipeParts").toString());
					}
				}

				JsonArray components = machineObject.get("components").getAsJsonArray();
				configuration.components = new ArrayList();

				for(int j = 0; j < components.size(); j++) {
					JsonObject compObject = components.get(j).getAsJsonObject();
					ComponentDefinition compDef = new ComponentDefinition();
					compDef.block = (Block) Block.blockRegistry.getObject(compObject.get("block").getAsString());
					compDef.x = compObject.get("x").getAsInt();
					compDef.y = compObject.get("y").getAsInt();
					compDef.z = compObject.get("z").getAsInt();
					compDef.allowedMetas = new HashSet();
					compDef.metas = compObject.get("metas").getAsJsonArray();
					for(int k = 0; k < compDef.metas.size(); k++) {
						compDef.allowedMetas.add(compDef.metas.get(k).getAsInt());
					}

					configuration.components.add(compDef);
				}

				customMachines.put(configuration.unlocalizedName, configuration);
				niceList.add(configuration);
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static class MachineConfiguration {

		/** The name of the recipe set that this machine can handle */
		public String recipeKey;
		/** The internal name of this machine */
		public String unlocalizedName;
		/** The display name of this machine */
		public String localizedName;
		public HashMap<String, String> localization = new HashMap();;

		public int fluidInCount;
		public int fluidInCap;
		public int itemInCount;
		public int fluidOutCount;
		public int fluidOutCap;
		public int itemOutCount;
		/** Whether inputs should be used up when the process begins */
		public boolean generatorMode;
		public int maxPollutionCap;
		public boolean fluxMode;
		public double recipeSpeedMult = 1D;
		public double recipeConsumptionMult = 1D;
		public long maxPower;
		public int maxHeat;


		/** Definitions of blocks that this machine is composed of */
		public List<ComponentDefinition> components;

		public static class ComponentDefinition {
			public Block block;
			public Set<Integer> allowedMetas;
			public JsonArray metas;
			public int x;
			public int y;
			public int z;
		}
	}
}
