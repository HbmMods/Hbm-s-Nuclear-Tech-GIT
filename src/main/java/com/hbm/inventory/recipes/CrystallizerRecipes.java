package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.IMCCrystallizer;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemEnums.EnumPlantType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemicalDye.EnumChemDye;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.special.ItemBedrockOre.EnumBedrockOre;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//This time we're doing this right
//...right?
public class CrystallizerRecipes extends SerializableRecipe {
	
	//'Object' is either a ComparableStack or the key for the ore dict
	private static HashMap<Pair<Object, FluidType>, CrystallizerRecipe> recipes = new HashMap();

	@Override
	public void registerDefaults() {

		int baseTime = 600;
		int utilityTime = 100;
		FluidStack sulfur = new FluidStack(Fluids.SULFURIC_ACID, 500);
		FluidStack nitric = new FluidStack(Fluids.NITRIC_ACID, 500);

		registerRecipe(COAL.ore(),		new CrystallizerRecipe(ModItems.crystal_coal, baseTime));
		registerRecipe(IRON.ore(),		new CrystallizerRecipe(ModItems.crystal_iron, baseTime));
		registerRecipe(GOLD.ore(),		new CrystallizerRecipe(ModItems.crystal_gold, baseTime));
		registerRecipe(REDSTONE.ore(),	new CrystallizerRecipe(ModItems.crystal_redstone, baseTime));
		registerRecipe(LAPIS.ore(),		new CrystallizerRecipe(ModItems.crystal_lapis, baseTime));
		registerRecipe(DIAMOND.ore(),	new CrystallizerRecipe(ModItems.crystal_diamond, baseTime));
		registerRecipe(U.ore(),			new CrystallizerRecipe(ModItems.crystal_uranium, baseTime), sulfur);
		registerRecipe(TH232.ore(),		new CrystallizerRecipe(ModItems.crystal_thorium, baseTime), sulfur);
		registerRecipe(PU.ore(),		new CrystallizerRecipe(ModItems.crystal_plutonium, baseTime), sulfur);
		registerRecipe(TI.ore(),		new CrystallizerRecipe(ModItems.crystal_titanium, baseTime), sulfur);
		registerRecipe(S.ore(),			new CrystallizerRecipe(ModItems.crystal_sulfur, baseTime));
		registerRecipe(KNO.ore(),		new CrystallizerRecipe(ModItems.crystal_niter, baseTime));
		registerRecipe(CU.ore(),		new CrystallizerRecipe(ModItems.crystal_copper, baseTime));
		registerRecipe(W.ore(),			new CrystallizerRecipe(ModItems.crystal_tungsten, baseTime), sulfur);
		registerRecipe(AL.ore(),		new CrystallizerRecipe(ModItems.crystal_aluminium, baseTime));
		registerRecipe(F.ore(),			new CrystallizerRecipe(ModItems.crystal_fluorite, baseTime));
		registerRecipe(BE.ore(),		new CrystallizerRecipe(ModItems.crystal_beryllium, baseTime));
		registerRecipe(PB.ore(),		new CrystallizerRecipe(ModItems.crystal_lead, baseTime));
		registerRecipe(SA326.ore(),		new CrystallizerRecipe(ModItems.crystal_schrabidium, baseTime), sulfur);
		registerRecipe(LI.ore(),		new CrystallizerRecipe(ModItems.crystal_lithium, baseTime), sulfur);
		registerRecipe(STAR.ore(),		new CrystallizerRecipe(ModItems.crystal_starmetal, baseTime), sulfur);
		registerRecipe(CO.ore(),		new CrystallizerRecipe(ModItems.crystal_cobalt, baseTime), sulfur);
		registerRecipe(NI.ore(),		new CrystallizerRecipe(ModItems.crystal_nickel, baseTime), nitric);
		registerRecipe((new ComparableStack(ModBlocks.ore_mineral)),		new CrystallizerRecipe(ModItems.crystal_mineral, baseTime)); //temp
		
		registerRecipe("oreRareEarth",	new CrystallizerRecipe(ModItems.crystal_rare, baseTime), sulfur);
		registerRecipe("oreCinnabar",	new CrystallizerRecipe(ModItems.crystal_cinnebar, baseTime));
		
		registerRecipe(new ComparableStack(ModBlocks.ore_nether_fire),	new CrystallizerRecipe(ModItems.crystal_phosphorus, baseTime));
		registerRecipe(new ComparableStack(ModBlocks.ore_tikite),		new CrystallizerRecipe(ModItems.crystal_trixite, baseTime), sulfur);
		registerRecipe(new ComparableStack(ModBlocks.gravel_diamond),	new CrystallizerRecipe(ModItems.crystal_diamond, baseTime));
		registerRecipe(new ComparableStack(ModItems.crystal_mineral),	new CrystallizerRecipe(ModItems.crystal_diamond, baseTime));
		registerRecipe(SRN.ingot(),										new CrystallizerRecipe(ModItems.crystal_schraranium, baseTime));
		
		registerRecipe("sand",				new CrystallizerRecipe(ModItems.ingot_fiberglass, utilityTime));
		registerRecipe(REDSTONE.block(),	new CrystallizerRecipe(ModItems.ingot_mercury, baseTime));
		registerRecipe(CINNABAR.crystal(),	new CrystallizerRecipe(new ItemStack(ModItems.ingot_mercury, 3), baseTime));
		registerRecipe(BORAX.dust(),		new CrystallizerRecipe(new ItemStack(ModItems.powder_boron_tiny, 3), baseTime), sulfur);
		registerRecipe(COAL.block(),		new CrystallizerRecipe(ModBlocks.block_graphite, baseTime));

		registerRecipe(new ComparableStack(Blocks.cobblestone),			new CrystallizerRecipe(ModBlocks.reinforced_stone, utilityTime));
		registerRecipe(new ComparableStack(ModBlocks.gravel_obsidian),	new CrystallizerRecipe(ModBlocks.brick_obsidian, utilityTime));
		registerRecipe(new ComparableStack(Items.rotten_flesh),			new CrystallizerRecipe(Items.leather, utilityTime));
		registerRecipe(new ComparableStack(ModItems.coal_infernal),		new CrystallizerRecipe(ModItems.solid_fuel, utilityTime));
		registerRecipe(new ComparableStack(ModBlocks.stone_gneiss),		new CrystallizerRecipe(ModItems.powder_lithium, utilityTime));
		registerRecipe(new ComparableStack(Items.dye, 1, 15),			new CrystallizerRecipe(new ItemStack(Items.slime_ball, 4), 20), new FluidStack(Fluids.SULFURIC_ACID, 250));
		registerRecipe(new ComparableStack(Items.bone),					new CrystallizerRecipe(new ItemStack(Items.slime_ball, 16), 20), new FluidStack(Fluids.SULFURIC_ACID, 1_000));
		registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.MUSTARDWILLOW)), new CrystallizerRecipe(new ItemStack(ModItems.powder_cadmium), 100).setReq(10), new FluidStack(Fluids.RADIOSOLVENT, 250));
		
		registerRecipe(DIAMOND.dust(), 									new CrystallizerRecipe(Items.diamond, utilityTime));
		registerRecipe(EMERALD.dust(), 									new CrystallizerRecipe(Items.emerald, utilityTime));
		registerRecipe(LAPIS.dust(),									new CrystallizerRecipe(new ItemStack(Items.dye, 1, 4), utilityTime));
		registerRecipe(new ComparableStack(ModItems.powder_semtex_mix),	new CrystallizerRecipe(ModItems.ingot_semtex, baseTime));
		registerRecipe(new ComparableStack(ModItems.powder_desh_ready),	new CrystallizerRecipe(ModItems.ingot_desh, baseTime));
		registerRecipe(new ComparableStack(ModItems.powder_meteorite),	new CrystallizerRecipe(ModItems.fragment_meteorite, utilityTime));
		registerRecipe(CD.dust(),										new CrystallizerRecipe(ModItems.ingot_rubber, baseTime), new FluidStack(Fluids.FISHOIL, 250));
		
		registerRecipe(new ComparableStack(ModItems.meteorite_sword_treated),	new CrystallizerRecipe(ModItems.meteorite_sword_etched, baseTime));
		registerRecipe(new ComparableStack(ModItems.powder_impure_osmiridium),	new CrystallizerRecipe(ModItems.crystal_osmiridium, baseTime), new FluidStack(Fluids.SCHRABIDIC, 1_000));
		
		
		for(int i = 0; i < ScrapType.values().length; i++) {
			registerRecipe(new ComparableStack(ModItems.scrap_plastic, 1, i), new CrystallizerRecipe(new ItemStack(ModItems.circuit_star_piece, 1, i), baseTime));
		}

		FluidStack organic = new FluidStack(Fluids.SOLVENT, 500);
		FluidStack chloric = new FluidStack(Fluids.HCL, 500);
		FluidStack schrabidic = new FluidStack(Fluids.SCHRABIDIC, 1000);
		FluidStack hiperf = new FluidStack(Fluids.RADIOSOLVENT, 500);
		
		int oreTime = 200;
		FluidStack technetic = new FluidStack(Fluids.HTcO4, 500);

		for(EnumBedrockOre ore : EnumBedrockOre.values()) {
			int i = ore.ordinal();
			FluidStack fluidStack = technetic;
			FluidStack hcl = chloric;
		    if (i == 8) {
		    	registerRecipe(new ComparableStack(ModItems.ore_centrifuged, 1, 8),			new CrystallizerRecipe(new ItemStack(ModItems.ore_cleaned, 1, 8), oreTime), fluidStack);
		    	registerRecipe(new ComparableStack(ModItems.ore_separated, 1, 8),			new CrystallizerRecipe(new ItemStack(ModItems.ore_purified, 1, 8), oreTime), hcl);
				registerRecipe(new ComparableStack(ModItems.ore_separated, 1, 8),			new CrystallizerRecipe(new ItemStack(ModItems.ore_nitrated, 1, 8), oreTime), nitric); //theoretically
				registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, 8),	new CrystallizerRecipe(new ItemStack(ModItems.ore_deepcleaned, 1, 8), oreTime), organic);
				registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, 8),	new CrystallizerRecipe(new ItemStack(ModItems.ore_seared, 1, 8), oreTime), hiperf);
		    }
		    else if(i == 9) {
		    	registerRecipe(new ComparableStack(ModItems.ore_centrifuged, 1, 9),			new CrystallizerRecipe(new ItemStack(ModItems.ore_cleaned, 1, 9), oreTime), fluidStack);
		    	registerRecipe(new ComparableStack(ModItems.ore_separated, 1, 9),			new CrystallizerRecipe(new ItemStack(ModItems.ore_purified, 1, 9), oreTime), hcl);
		    	
				registerRecipe(new ComparableStack(ModItems.ore_separated, 1, 9),			new CrystallizerRecipe(new ItemStack(ModItems.ore_nitrated, 1, 9), oreTime), nitric); //theoretically
				registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, 9),	new CrystallizerRecipe(new ItemStack(ModItems.ore_deepcleaned, 1, 9), oreTime), organic);
				registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, 9),	new CrystallizerRecipe(new ItemStack(ModItems.ore_seared, 1, 9), oreTime), hiperf);
		    }
		    
		    else {
			registerRecipe(new ComparableStack(ModItems.ore_centrifuged, 1, i),			new CrystallizerRecipe(new ItemStack(ModItems.ore_cleaned, 1, i), oreTime));
			registerRecipe(new ComparableStack(ModItems.ore_separated, 1, i),			new CrystallizerRecipe(new ItemStack(ModItems.ore_purified, 1, i), oreTime), sulfur);
			registerRecipe(new ComparableStack(ModItems.ore_separated, 1, i),			new CrystallizerRecipe(new ItemStack(ModItems.ore_nitrated, 1, i), oreTime), nitric);
			registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, i),	new CrystallizerRecipe(new ItemStack(ModItems.ore_deepcleaned, 1, i), oreTime), organic);
			registerRecipe(new ComparableStack(ModItems.ore_nitrocrystalline, 1, i),	new CrystallizerRecipe(new ItemStack(ModItems.ore_seared, 1, i), oreTime), hiperf);
			
		}
	}

		int mineraltime = 300;
		registerRecipe(new ComparableStack(ModItems.mineral_dust),	new CrystallizerRecipe(new ItemStack(ModItems.mineral_fragment, 1, 0), mineraltime));
		registerRecipe(new ComparableStack(ModItems.mineral_dust),	new CrystallizerRecipe(new ItemStack(ModItems.mineral_fragment, 1, 1), mineraltime), nitric);
		registerRecipe(new ComparableStack(ModItems.mineral_dust),	new CrystallizerRecipe(new ItemStack(ModItems.mineral_fragment, 1, 2), mineraltime), sulfur);
		registerRecipe(new ComparableStack(ModItems.mineral_dust),	new CrystallizerRecipe(new ItemStack(ModItems.mineral_fragment, 1, 3), mineraltime), organic);
		registerRecipe(new ComparableStack(ModItems.mineral_dust),	new CrystallizerRecipe(new ItemStack(ModItems.mineral_fragment, 1, 4), mineraltime), chloric);
		registerRecipe(new ComparableStack(ModItems.mineral_dust),	new CrystallizerRecipe(new ItemStack(ModItems.mineral_fragment, 1, 5), mineraltime), schrabidic);
		
		FluidStack[] dyes = new FluidStack[] {new FluidStack(Fluids.WOODOIL, 100), new FluidStack(Fluids.FISHOIL, 100)};
		for(FluidStack dye : dyes) {
			registerRecipe(COAL.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLACK, 4), 20), dye);
			registerRecipe(TI.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.WHITE, 4), 20), dye);
			registerRecipe(IRON.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.RED, 4), 20), dye);
			registerRecipe(W.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.YELLOW, 4), 20), dye);
			registerRecipe(CU.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.GREEN, 4), 20), dye);
			registerRecipe(CO.dust(),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.chemical_dye, EnumChemDye.BLUE, 4), 20), dye);
		}

		registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE)),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX), 20),	new FluidStack(Fluids.CHLORINE, 250));
		registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK)),		new CrystallizerRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX), 20),	new FluidStack(Fluids.CHLORINE, 100));
		registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)),	new CrystallizerRecipe(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX), 20),	new FluidStack(Fluids.CHLORINE, 100));
		registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WAX)), 		new CrystallizerRecipe(new ItemStack(ModItems.pellet_charged), 200), 				new FluidStack(Fluids.IONGEL, 500));
		registerRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.PARAFFIN)), 	new CrystallizerRecipe(new ItemStack(ModItems.pill_red), 200), 						new FluidStack(Fluids.ESTRADIOL, 250));

		registerRecipe(KEY_SAND, new CrystallizerRecipe(Blocks.clay, 20), new FluidStack(Fluids.COLLOID, 1_000));
		registerRecipe(new ComparableStack(ModBlocks.sand_quartz), new CrystallizerRecipe(new ItemStack(ModItems.ball_dynamite, 16), 20), new FluidStack(Fluids.NITROGLYCERIN, 1_000));
		registerRecipe(NETHERQUARTZ.dust(), new CrystallizerRecipe(new ItemStack(ModItems.ball_dynamite, 4), 20), new FluidStack(Fluids.NITROGLYCERIN, 250));
		
		/// COMPAT CERTUS QUARTZ ///
		List<ItemStack> quartz = OreDictionary.getOres("crystalCertusQuartz");
		if(quartz != null && !quartz.isEmpty()) {
			ItemStack qItem = quartz.get(0).copy();
			qItem.stackSize = 12;
			registerRecipe("oreCertusQuartz", new CrystallizerRecipe(qItem, baseTime));
		}

		/// COMPAT WHITE PHOSPHORUS DUST ///
		List<ItemStack> dustWhitePhosphorus = OreDictionary.getOres(P_WHITE.dust());
		if(dustWhitePhosphorus != null && !dustWhitePhosphorus.isEmpty()) {
			registerRecipe(P_WHITE.dust(), new CrystallizerRecipe(new ItemStack(ModItems.ingot_phosphorus), utilityTime), new FluidStack(Fluids.AROMATICS, 50));
		}
		
		if(!IMCCrystallizer.buffer.isEmpty()) {
			recipes.putAll(IMCCrystallizer.buffer);
			MainRegistry.logger.info("Fetched " + IMCCrystallizer.buffer.size() + " IMC crystallizer recipes!");
			IMCCrystallizer.buffer.clear();
		}
	}
	
	public static CrystallizerRecipe getOutput(ItemStack stack, FluidType type) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		Pair compKey = new Pair(comp, type);
		
		if(recipes.containsKey(compKey))
			return recipes.get(compKey);
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {
			
			Pair dictKey = new Pair(key, type);

			if(recipes.containsKey(dictKey))
				return recipes.get(dictKey);
		}
		
		return null;
	}

	public static HashMap getRecipes() {
		
		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(Entry<Pair<Object, FluidType>, CrystallizerRecipe> entry : CrystallizerRecipes.recipes.entrySet()) {
			
			CrystallizerRecipe recipe = entry.getValue();
			
			Pair<Object, FluidType> key = entry.getKey();
			Object input = key.getKey();
			FluidType acid = key.getValue();
			
			if(input instanceof String) {
				OreDictStack stack = new OreDictStack((String) input, recipe.itemAmount);
				recipes.put(new Object[] {ItemFluidIcon.make(acid, recipe.acidAmount), stack}, recipe.output);
			} else {
				ComparableStack stack = ((ComparableStack) input);
				stack = (ComparableStack) stack.copy();
				stack.stacksize = recipe.itemAmount;
				if(stack.item == ModItems.scrap_plastic) continue;
				recipes.put(new Object[] {ItemFluidIcon.make(acid, recipe.acidAmount), stack}, recipe.output);
			}
		}
		
		return recipes;
	}
	
	public static void registerRecipe(Object input, CrystallizerRecipe recipe) {
		registerRecipe(input, recipe, new FluidStack(Fluids.ACID, 500));
	}
	
	public static void registerRecipe(Object input, CrystallizerRecipe recipe, FluidStack stack) {
		recipe.acidAmount = stack.fill;
		recipes.put(new Pair(input, stack.type), recipe);
	}
	
	public static class CrystallizerRecipe {
		public int acidAmount;
		public int itemAmount = 1;
		public int duration;
		public ItemStack output;
		
		public CrystallizerRecipe(Block output, int duration) { this(new ItemStack(output), duration); }
		public CrystallizerRecipe(Item output, int duration) { this(new ItemStack(output), duration); }
		
		public CrystallizerRecipe setReq(int amount) {
			this.itemAmount = amount;
			return this;
		}
		
		public CrystallizerRecipe(ItemStack output, int duration) {
			this.output = output;
			this.duration = duration;
			this.acidAmount = 500;
		}
	}

	@Override
	public String getFileName() {
		return "hbmCrystallizer.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		ItemStack output = this.readItemStack(obj.get("output").getAsJsonArray());
		AStack input = this.readAStack(obj.get("input").getAsJsonArray());
		FluidStack fluid = this.readFluidStack(obj.get("fluid").getAsJsonArray());
		int duration = obj.get("duration").getAsInt();
		
		CrystallizerRecipe cRecipe = new CrystallizerRecipe(output, duration).setReq(input.stacksize);
		input.stacksize = 1;
		cRecipe.acidAmount = fluid.fill;
		if(input instanceof ComparableStack) {
			recipes.put(new Pair(((ComparableStack) input), fluid.type), cRecipe);
		} else if(input instanceof OreDictStack) {
			recipes.put(new Pair(((OreDictStack) input).name, fluid.type), cRecipe);
		}
	}

	@Override
	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException {
		Entry<Pair, CrystallizerRecipe> rec = (Entry<Pair, CrystallizerRecipe>) recipe;
		CrystallizerRecipe cRecipe = rec.getValue();
		Pair<Object, FluidType> pair = rec.getKey();
		AStack input = pair.getKey() instanceof String ? new OreDictStack((String )pair.getKey()) : ((ComparableStack) pair.getKey()).copy();
		input.stacksize = cRecipe.itemAmount;
		FluidStack fluid = new FluidStack(pair.value, cRecipe.acidAmount);

		writer.name("duration").value(cRecipe.duration);
		writer.name("fluid");
		this.writeFluidStack(fluid, writer);
		writer.name("input");
		this.writeAStack(input, writer);
		writer.name("output");
		this.writeItemStack(cRecipe.output, writer);
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
	}

	@Override
	public String getComment() {
		return "The acidizer also supports stack size requirements for input items, eg. the cadmium recipe requires 10 willow leaves.";
	}
}
