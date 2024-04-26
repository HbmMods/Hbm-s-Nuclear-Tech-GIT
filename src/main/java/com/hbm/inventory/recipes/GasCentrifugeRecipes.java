package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.util.I18nUtil;

import net.minecraft.item.ItemStack;

public class GasCentrifugeRecipes extends SerializableRecipe {

	@Override
	public String getFileName() {
		return "hbmGasCentrifuge.json";
	}

	@Override
	public Object getRecipeObject() {
		return fluidConversions;
	}
	@Override
	public String getComment() {
		return "Rules: PseudoFluid is not a regular fluid, but a unique fluid in gas centrifuges. Generally speaking, the \"pseudoFluid\" option in the JSON file determines the non localized name of a PseudoFluid. To configure a localized name, "
				+ "add \"hbmpseudofluid. 'Lowercase non localized name'='Localized name'\" to the lang file in each language; The \"outputFluid\" option determines the next level of PseudoFluid that can be output after being centrifuged, "
				+ "and then this output PseudoFluid is configured immediately after the previous level of PseudoFluid, repeating this process until the output PseudoFluid is NONE. Please ensure that each PseudoFluid is configured in the order of output, "
				+ "and the final output PseudoFluid is NONE. The \"fluidConsumed\" and \"fluidProduced\" items respectively determine the amount consumed by the PseudoFluid in one centrifugation and the amount of the next level PseudoFluid output; "
				+ "The \"isHighSpeed\" option determines whether an overclocking upgrade is required for the PseudoFluid centrifuge. If an overclocking upgrade is required but not installed during runtime, and the \"low-SpeedOutputItems\" option is not empty, "
				+ "it will be converted to output items within the \"low-SpeedOutputItems\" option, and the next level of PseudoFluid will not be produced; The \"isIdling\" option determines whether the PseudoFluid can idle to consume three times the amount of the next PseudoFluid produced by centrifugation, "
				+ "and output the items in the \"idlingOutputItems\" option if the PseudoFluid produced by centrifugation is not NONE and there is no next gas centrifuge connected";
	}
	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;
		FluidType inputFluidType = Fluids.fromName(obj.get("inputFluidType").getAsString());
		JsonArray recipeArray = obj.get("recipes").getAsJsonArray();
		List<ItemStack> finalOutputItems = new ArrayList<>();
		List<Integer> highSpeedFluid = new ArrayList<>();
		List<Integer> idlingFluid = new ArrayList<>();
		int finalFluidConsumed = recipeArray.get(0).getAsJsonObject().get("fluidConsumed").getAsInt();

		float finalMultiplier = 1;
		boolean hasHighSpeed = false;
		boolean hasIdling = false;

		for(int i=recipeArray.size()-1; i>=0; i--) {
			JsonObject pseudoFluid = recipeArray.get(i).getAsJsonObject();
			String pseudoFluidName = pseudoFluid.get("pseudoFluid").getAsString();
			int fluidConsumed = pseudoFluid.get("fluidConsumed").getAsInt();
			int fluidProduced = pseudoFluid.get("fluidProduced").getAsInt();
			PseudoFluidType outputFluid = PseudoFluidType.types.get(pseudoFluid.get("outputFluid").getAsString());
			boolean isHighSpeed = pseudoFluid.get("isHighSpeed").getAsBoolean();
			if(isHighSpeed){
				hasHighSpeed=true;
				highSpeedFluid.add(i);
			}
			boolean isIdling = pseudoFluid.get("isIdling").getAsBoolean();
			if(isIdling){
				hasIdling=true;
				idlingFluid.add(i);
			}
			ItemStack[] outputItems = readItemStackArray(pseudoFluid.get("outputItems").getAsJsonArray());
			ItemStack[] lowSpeedOutputItems = readItemStackArray(pseudoFluid.get("lowSpeedOutputItems").getAsJsonArray());
			ItemStack[] idlingOutputItems = readItemStackArray(pseudoFluid.get("idlingOutputItems").getAsJsonArray());
			finalMultiplier = finalMultiplier * (outputFluid==PseudoFluidType.NONE || outputFluid.fluidConsumed==0 ? 1 : (float)outputFluid.fluidConsumed/(float)fluidProduced);
			for(ItemStack output : outputItems) {
				boolean hasItem = false;
					for (ItemStack finaloutput : finalOutputItems) {
						if (finaloutput.getItem() == output.getItem()) {
							finaloutput.stackSize = (int) (finaloutput.stackSize + output.stackSize * finalMultiplier);
							hasItem = true;
						}
					}

				if(!hasItem) {
					output.stackSize=(int)(output.stackSize*finalMultiplier);
					finalOutputItems.add(output);
				}
			}

			if(!PseudoFluidType.types.containsKey(pseudoFluidName)){
				new PseudoFluidType(pseudoFluidName,fluidConsumed,fluidProduced,outputFluid,isHighSpeed,isIdling,idlingOutputItems,lowSpeedOutputItems,outputItems);
			}
			else{
				PseudoFluidType pseudoFluidType = PseudoFluidType.types.get(pseudoFluidName);
				pseudoFluidType.fluidConsumed = fluidConsumed;
				pseudoFluidType.fluidProduced = fluidProduced;
				pseudoFluidType.outputFluid = outputFluid;
				pseudoFluidType.isHighSpeed = isHighSpeed;
				pseudoFluidType.isIdling = isIdling;
				pseudoFluidType.output = outputItems;
				pseudoFluidType.IdlingOutput = idlingOutputItems;
				pseudoFluidType.LowSpeedOutput = lowSpeedOutputItems;
			}
		}
		Collections.reverse(highSpeedFluid);
		finalFluidConsumed = (int) (finalFluidConsumed * finalMultiplier);
		fluidConversions.put(inputFluidType,PseudoFluidType.types.get(recipeArray.get(0).getAsJsonObject().get("pseudoFluid").getAsString()));
		gasCent.put(new FluidStack(inputFluidType, finalFluidConsumed),
					new Object[] {finalOutputItems.toArray(new ItemStack[finalOutputItems.size()]),
							       hasHighSpeed,
							       recipeArray.size(), highSpeedFluid.size() == 0 ? new ArrayList<Integer>() : highSpeedFluid}
				);
		if(hasIdling){
			for(int i : idlingFluid){
				if(i<recipeArray.size()-1){
					int stageOutputConsumed = recipeArray.get(0).getAsJsonObject().get("fluidConsumed").getAsInt();
					List<ItemStack> stageOutputItems = new ArrayList<>();
					boolean highSpeed = recipeArray.get(i).getAsJsonObject().get("isHighSpeed").getAsBoolean();
					for (int j = 0; j <= i; j++) {
						float stageMultiplier = 1;
						JsonObject pseudoFluid = recipeArray.get(j).getAsJsonObject();
						String pseudoFluidName = pseudoFluid.get("pseudoFluid").getAsString();
						ItemStack[] outputItems = readItemStackArray(pseudoFluid.get("outputItems").getAsJsonArray());
						PseudoFluidType pseudoFluidType = PseudoFluidType.types.get(pseudoFluidName);
						while (pseudoFluidType != PseudoFluidType.types.get(recipeArray.get(i).getAsJsonObject().get("pseudoFluid").getAsString())) {
							stageMultiplier = stageMultiplier * (pseudoFluidType.getOutputType() == PseudoFluidType.NONE || pseudoFluidType.getOutputType().fluidConsumed == 0 ? 1 : (float) pseudoFluidType.getOutputType().fluidConsumed / (float) pseudoFluidType.fluidProduced);
							pseudoFluidType = pseudoFluidType.getOutputType();
						}
						if (j == 0) {
							stageOutputConsumed = (int) (stageOutputConsumed * stageMultiplier) * 3;
						}
						for (ItemStack output : outputItems) {
							boolean hasItem = false;
							for (ItemStack stageoutput : stageOutputItems) {
								if (stageoutput.getItem() == output.getItem()) {
									stageoutput.stackSize = (int) (stageoutput.stackSize + output.stackSize * stageMultiplier * 3);
									hasItem = true;
								}
							}

							if (!hasItem) {
								output.stackSize = (int) (output.stackSize * stageMultiplier * 3);
								stageOutputItems.add(output);
							}
						}
					}
					ItemStack[] idlingOutputItems = readItemStackArray(recipeArray.get(i).getAsJsonObject().get("idlingOutputItems").getAsJsonArray());
					for (ItemStack output : idlingOutputItems) {
						boolean hasItem = false;
						for (ItemStack stageoutput : stageOutputItems) {
							if (stageoutput.getItem() == output.getItem()) {
								stageoutput.stackSize = (int) (stageoutput.stackSize + output.stackSize);
								hasItem = true;
							}
						}

						if (!hasItem) {
							stageOutputItems.add(output);
						}
					}
					gasCent.put(new FluidStack(inputFluidType, stageOutputConsumed),
							new Object[]{stageOutputItems.toArray(new ItemStack[stageOutputItems.size()]),
									highSpeed,
									i + 1, new ArrayList<Integer>(){
											{
												for(int highSpeedFluid : highSpeedFluid){
													if (highSpeedFluid<=i){
														add(highSpeedFluid);
													}
												}
											}
											}
							}
					);
				}
			}
		}
		if(hasHighSpeed){
			for(int i : highSpeedFluid){
				int stageOutputConsumed = recipeArray.get(0).getAsJsonObject().get("fluidConsumed").getAsInt();
				List<ItemStack> stageOutputItems = new ArrayList<>();
				for (int j = 0; j <= i; j++) {
					float stageMultiplier = 1;
					JsonObject pseudoFluid = recipeArray.get(j).getAsJsonObject();
					String pseudoFluidName = pseudoFluid.get("pseudoFluid").getAsString();
					ItemStack[] outputItems = readItemStackArray(pseudoFluid.get("outputItems").getAsJsonArray());
					ItemStack[] lowSpeedOutputItems = j==i ? readItemStackArray(pseudoFluid.get("lowSpeedOutputItems").getAsJsonArray()) : outputItems;
					PseudoFluidType pseudoFluidType = PseudoFluidType.types.get(pseudoFluidName);
					while (pseudoFluidType != PseudoFluidType.types.get(recipeArray.get(i).getAsJsonObject().get("pseudoFluid").getAsString())) {
						stageMultiplier = stageMultiplier * (pseudoFluidType.getOutputType() == PseudoFluidType.NONE || pseudoFluidType.getOutputType().fluidConsumed == 0 ? 1 : (float) pseudoFluidType.getOutputType().fluidConsumed / (float) pseudoFluidType.fluidProduced);
						pseudoFluidType = pseudoFluidType.getOutputType();
					}
					if(j==0){
						stageOutputConsumed = (int) (stageOutputConsumed * stageMultiplier);
					}
					for (ItemStack output : lowSpeedOutputItems) {
						boolean hasItem = false;
						for (ItemStack stageoutput : stageOutputItems) {
							if (stageoutput.getItem() == output.getItem()) {
								stageoutput.stackSize = (int) (stageoutput.stackSize + output.stackSize * stageMultiplier);
								hasItem = true;
							}
						}

						if (!hasItem) {
							output.stackSize = (int) (output.stackSize * stageMultiplier);
							stageOutputItems.add(output);
						}
					}

				}
				gasCent.put(new FluidStack(inputFluidType, stageOutputConsumed),
						new Object[]{stageOutputItems.toArray(new ItemStack[stageOutputItems.size()]),
								false,
								i + 1,new ArrayList<Integer>()}
				     		);
			}
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<FluidType, PseudoFluidType> rec = (Entry<FluidType, PseudoFluidType>) recipe;
		PseudoFluidType recipes = rec.getValue();
		writer.name("inputFluidType").value(rec.getKey().getName());
		writer.name("recipes").beginArray();
		PseudoFluidType PseudoFluidType = recipes;
		while(PseudoFluidType.getOutputType()!=null){
			writer.beginObject();
			writer.name("pseudoFluid").value(PseudoFluidType.name);
			writer.name("fluidConsumed").value(PseudoFluidType.getFluidConsumed());
			writer.name("fluidProduced").value(PseudoFluidType.getFluidProduced());
			writer.name("outputFluid").value(PseudoFluidType.getOutputType().name);
			writer.name("isHighSpeed").value(PseudoFluidType.getIfHighSpeed());
			writer.name("isIdling").value(PseudoFluidType.getIfIdling());
			writer.name("outputItems").beginArray();
			for(ItemStack stack : PseudoFluidType.getOutput()) {
				writeItemStack(stack, writer);
			}
			writer.endArray();
			writer.name("idlingOutputItems").beginArray();
			if(PseudoFluidType.getIdlingOutput() != null) {
				for (ItemStack stack : PseudoFluidType.getIdlingOutput()) {
					writeItemStack(stack, writer);
				}
			}
			writer.endArray();
			writer.name("lowSpeedOutputItems").beginArray();
			if(PseudoFluidType.getLowSpeedOutput() != null) {
				for (ItemStack stack : PseudoFluidType.getLowSpeedOutput()) {
					writeItemStack(stack, writer);
				}
			}
			writer.endArray();
			writer.endObject();
			PseudoFluidType = PseudoFluidType.getOutputType();
		}
		writer.endArray();
	}

	@Override
	public void registerDefaults() {
		fluidConversions.put(Fluids.UF6, PseudoFluidType.NUF6);
		fluidConversions.put(Fluids.PUF6, PseudoFluidType.PF6);
		fluidConversions.put(Fluids.WATZ, PseudoFluidType.MUD);

		gasCent.put(new FluidStack(1200, Fluids.UF6), new Object[] { new ItemStack[] {new ItemStack(ModItems.nugget_u238, 11), new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.fluorite, 4)}, true, 4, new ArrayList<Integer>(){ { add(3); } } });
		gasCent.put(new FluidStack(1200, Fluids.UF6), new Object[] { new ItemStack[] {new ItemStack(ModItems.nugget_u238, 6), new ItemStack(ModItems.nugget_uranium_fuel, 6), new ItemStack(ModItems.fluorite, 4)}, false, 2, new ArrayList<Integer>() });
		gasCent.put(new FluidStack(900, Fluids.PUF6), new Object[] { new ItemStack[] {new ItemStack(ModItems.nugget_pu238, 3), new ItemStack(ModItems.nugget_pu_mix, 6), new ItemStack(ModItems.fluorite, 3)}, false, 1, new ArrayList<Integer>() });
		gasCent.put(new FluidStack(1000, Fluids.WATZ), new Object[] { new ItemStack[] {new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.powder_lead, 1), new ItemStack(ModItems.nuclear_waste_tiny, 1), new ItemStack(ModItems.dust, 2)}, false, 2, new ArrayList<Integer>() });
	}

	@Override
	public void deleteRecipes() {
		fluidConversions.clear();
		gasCent.clear();
	}

	public static class PseudoFluidType {
		
		public static HashMap<String, PseudoFluidType> types = new HashMap();
		
		public static PseudoFluidType NONE		= new PseudoFluidType("NONE",		0,		0,		null,		false,	false, (ItemStack[])null, (ItemStack[])null);
		
		public static PseudoFluidType HEUF6		= new PseudoFluidType("HEUF6",		300,	0,		NONE,		true,	false, (ItemStack[])null, (ItemStack[])null, new ItemStack(ModItems.nugget_u238, 2), new ItemStack(ModItems.nugget_u235, 1), new ItemStack(ModItems.fluorite, 1));
		public static PseudoFluidType MEUF6		= new PseudoFluidType("MEUF6",		200,	100,	HEUF6,		false,	false, (ItemStack[])null, (ItemStack[])null, new ItemStack(ModItems.nugget_u238, 1));
		public static PseudoFluidType LEUF6 	= new PseudoFluidType("LEUF6",		300,	200,	MEUF6,		false,	true, new ItemStack[]{new ItemStack(ModItems.nugget_uranium_fuel, 2), new ItemStack(ModItems.fluorite)}, (ItemStack[])null, new ItemStack(ModItems.nugget_u238, 1), new ItemStack(ModItems.fluorite, 1));
		public static PseudoFluidType NUF6 		= new PseudoFluidType("NUF6",		400,	300,	LEUF6,		false,	false, (ItemStack[])null, (ItemStack[])null, new ItemStack(ModItems.nugget_u238, 1));
		
		public static PseudoFluidType PF6		= new PseudoFluidType("PF6",		300,	0,		NONE,		false,	false, (ItemStack[])null, (ItemStack[])null, new ItemStack(ModItems.nugget_pu238, 1), new ItemStack(ModItems.nugget_pu_mix, 2), new ItemStack(ModItems.fluorite, 1));
		
		public static PseudoFluidType MUD_HEAVY	= new PseudoFluidType("MUD_HEAVY",	500,	0,		NONE,		false,	false, (ItemStack[])null, (ItemStack[])null, new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.dust, 1), new ItemStack(ModItems.nuclear_waste_tiny, 1));
		public static PseudoFluidType MUD		= new PseudoFluidType("MUD", 		1000,	500,	MUD_HEAVY,	false,	false, (ItemStack[])null, (ItemStack[])null, new ItemStack(ModItems.powder_lead, 1), new ItemStack(ModItems.dust, 1));
		
		public String name;
		int fluidConsumed;
		int fluidProduced;
		PseudoFluidType outputFluid;
		boolean isHighSpeed;
		boolean isIdling;
		ItemStack[] output;
		ItemStack[] LowSpeedOutput;
		ItemStack[] IdlingOutput;
		
		PseudoFluidType(String name, int fluidConsumed, int fluidProduced, PseudoFluidType outputFluid, boolean isHighSpeed,boolean isIdling, ItemStack[] IdlingOutput, ItemStack[] LowSpeedOutput, ItemStack... output) {
			this.name = name;
			this.fluidConsumed = fluidConsumed;
			this.fluidProduced = fluidProduced;
			this.outputFluid = outputFluid;
			this.isHighSpeed = isHighSpeed;
			this.isIdling = isIdling;
			this.output = output;
			this.LowSpeedOutput = LowSpeedOutput;
			this.IdlingOutput = IdlingOutput;

			types.put(name, this);
		}
		
		public int getFluidConsumed() {				return this.fluidConsumed; }
		public int getFluidProduced() {				return this.fluidProduced; }
		public PseudoFluidType getOutputType() {	return this.outputFluid; }
		public ItemStack[] getOutput() {			return this.output; }
		public ItemStack[] getLowSpeedOutput() {			return this.LowSpeedOutput; }
		public ItemStack[] getIdlingOutput() {			return this.IdlingOutput; }

		public boolean getIfHighSpeed() {			return this.isHighSpeed; }
		public boolean getIfIdling() {			return this.isIdling; }

		public String getName() {					return I18nUtil.resolveKey("hbmpseudofluid.".concat(this.name.toLowerCase(Locale.US))); }
		
	}
		
	/* Recipe NEI Handler */
	//Fluid input; ItemStack[] outputs, isHighSpeed, # of centrifuges
	private static Map<FluidStack, Object[]> gasCent = new HashMap();
	
	//Iterators are lots of fun
	public static Map<Object, Object[]> getGasCentrifugeRecipes() {
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		Iterator itr = gasCent.entrySet().iterator();
		
		while(itr.hasNext()) {
			Map.Entry<Object, Object[]> entry = (Entry) itr.next();
			FluidStack input = (FluidStack) entry.getKey();
			ItemStack[] out = new ItemStack[4];
			ItemStack[] outputs = (ItemStack[]) entry.getValue()[0];
			
			for(int j = 0; j < outputs.length; j++) {
				out[j] = outputs[j].copy();
			}
			for(int j = 0; j < 4; j++)
				if(out[j] == null)
					out[j] = new ItemStack(ModItems.nothing);
			
			recipes.put(ItemFluidIcon.make(input.type, input.fill), new Object[] { out, entry.getValue()[1], entry.getValue()[2], entry.getValue()[3] });
		}
		
		return recipes;
	}
	
	public static HashMap<FluidType, PseudoFluidType> fluidConversions = new HashMap();

}
