package com.hbm.inventory.recipes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.hbm.inventory.OreDictManager.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SolderingRecipes extends SerializableRecipe {
	
	public static List<SolderingRecipe> recipes = new ArrayList();

	@Override
	public void registerDefaults() {
		
		boolean lbsm = GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleCrafting;
		
		/*
		 * CIRCUITS
		 */
		
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.ANALOG.ordinal()), 100, 100,
				new AStack[] {
						new ComparableStack(ModItems.circuit, 3, EnumCircuitType.VACUUM_TUBE),
						new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CAPACITOR)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.PCB)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 4)}
		));
		
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.BASIC.ordinal()), 200, 250,
				new AStack[] {
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CHIP)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.PCB)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 4)}
		));
		
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.ADVANCED.ordinal()), 300, 1_000,
				new FluidStack(Fluids.SULFURIC_ACID, 1_000),
				new AStack[] {
						new ComparableStack(ModItems.circuit, lbsm ? 4 : 16, EnumCircuitType.CHIP),
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CAPACITOR)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 8, EnumCircuitType.PCB),
						new OreDictStack(RUBBER.ingot(), 2)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 8)}
		));
		
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.CAPACITOR_BOARD.ordinal()), 200, 300,
				new FluidStack(Fluids.PEROXIDE, 250),
				new AStack[] {
						new ComparableStack(ModItems.circuit, 3, EnumCircuitType.CAPACITOR_TANTALIUM)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.PCB)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 3)}
		));
		
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.BISMOID.ordinal()), 400, 10_000,
				new FluidStack(Fluids.SOLVENT, 1_000),
				new AStack[] {
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CHIP_BISMOID),
						new ComparableStack(ModItems.circuit, lbsm ? 4 : 16, EnumCircuitType.CHIP),
						new ComparableStack(ModItems.circuit, lbsm ? 8 : 24, EnumCircuitType.CAPACITOR)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 12, EnumCircuitType.PCB),
						new OreDictStack(ANY_HARDPLASTIC.ingot(), 2)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 12)}
		));
		
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.QUANTUM.ordinal()), 400, 100_000,
				new FluidStack(Fluids.HELIUM4, 1_000),
				new AStack[] {
						new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CHIP_QUANTUM),
						new ComparableStack(ModItems.circuit, lbsm ? 4 : 16, EnumCircuitType.CHIP_BISMOID),
						new ComparableStack(ModItems.circuit, lbsm ? 1 : 4, EnumCircuitType.ATOMIC_CLOCK)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 16, EnumCircuitType.PCB),
						new OreDictStack(ANY_HARDPLASTIC.ingot(), 4)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 16)}
		));
		
		/*
		 * COMPUTERS
		 */

		// a very, very vague guess on what the recipes should be. testing still needed, upgrade requirements are likely to change. maybe inclusion of caesium?
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER.ordinal()), 400, 15_000,
				new FluidStack(Fluids.PERFLUOROMETHYL, 1_000),
				new AStack[] {
						new ComparableStack(ModItems.circuit, lbsm ? 8 : 32, EnumCircuitType.CHIP),
						new ComparableStack(ModItems.circuit, lbsm ? 8 : 32, EnumCircuitType.CAPACITOR),
						new ComparableStack(ModItems.circuit, lbsm ? 8 : 16, EnumCircuitType.CAPACITOR_TANTALIUM)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_CHASSIS),
						new ComparableStack(ModItems.upgrade_speed_1)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 16)}
		));
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_ADVANCED.ordinal()), 600, 25_000,
				new FluidStack(Fluids.PERFLUOROMETHYL, 4_000),
				new AStack[] {
						new ComparableStack(ModItems.circuit, lbsm ? 8 : 16, EnumCircuitType.CHIP_BISMOID),
						new ComparableStack(ModItems.circuit, lbsm ? 16 : 48, EnumCircuitType.CAPACITOR_TANTALIUM),
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.ATOMIC_CLOCK)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_CHASSIS),
						new ComparableStack(ModItems.upgrade_speed_3)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 24)}
		));
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.circuit, 1, EnumCircuitType.CONTROLLER_QUANTUM.ordinal()), 600, 250_000,
				new FluidStack(Fluids.PERFLUOROMETHYL_COLD, 6_000),
				new AStack[] {
						new ComparableStack(ModItems.circuit, lbsm ? 8 : 16, EnumCircuitType.CHIP_QUANTUM),
						new ComparableStack(ModItems.circuit, lbsm ? 16 : 48, EnumCircuitType.CHIP_BISMOID),
						new ComparableStack(ModItems.circuit, lbsm ? 1 : 8, EnumCircuitType.ATOMIC_CLOCK)},
				new AStack[] {
						new ComparableStack(ModItems.circuit, 2, EnumCircuitType.CONTROLLER_ADVANCED),
						new ComparableStack(ModItems.upgrade_overdrive_1)},
				new AStack[] {
						new OreDictStack(PB.wireFine(), 32)}
		));

		/*
		 * UPGRADES
		 */
		
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.upgrade_speed_1), 200, 1_000,
				new AStack[] {new ComparableStack(ModItems.circuit, 4, EnumCircuitType.VACUUM_TUBE), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(ModItems.upgrade_template), new OreDictStack(MINGRADE.dust(), 4)},
				new AStack[] {}
		));
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.upgrade_effect_1), 200, 1_000,
				new AStack[] {new ComparableStack(ModItems.circuit, 4, EnumCircuitType.VACUUM_TUBE), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(ModItems.upgrade_template), new OreDictStack(EMERALD.dust(), 4)},
				new AStack[] {}
		));
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.upgrade_power_1), 200, 1_000,
				new AStack[] {new ComparableStack(ModItems.circuit, 4, EnumCircuitType.VACUUM_TUBE), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(ModItems.upgrade_template), new OreDictStack(GOLD.dust(), 4)},
				new AStack[] {}
		));
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.upgrade_fortune_1), 200, 1_000,
				new AStack[] {new ComparableStack(ModItems.circuit, 4, EnumCircuitType.VACUUM_TUBE), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(ModItems.upgrade_template), new OreDictStack(NB.dust(), 4)},
				new AStack[] {}
		));
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.upgrade_afterburn_1), 200, 1_000,
				new AStack[] {new ComparableStack(ModItems.circuit, 4, EnumCircuitType.VACUUM_TUBE), new ComparableStack(ModItems.circuit, 1, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(ModItems.upgrade_template), new OreDictStack(W.dust(), 4)},
				new AStack[] {}
		));
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.upgrade_radius), 200, 1_000,
				new AStack[] {new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CHIP), new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(ModItems.upgrade_template), new OreDictStack("dustGlowstone", 4)},
				new AStack[] {}
		));
		recipes.add(new SolderingRecipe(new ItemStack(ModItems.upgrade_health), 200, 1_000,
				new AStack[] {new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CHIP), new ComparableStack(ModItems.circuit, 4, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(ModItems.upgrade_template), new OreDictStack(LI.dust(), 4)},
				new AStack[] {}
		));
		
		addFirstUpgrade(ModItems.upgrade_speed_1, ModItems.upgrade_speed_2);
		addSecondUpgrade(ModItems.upgrade_speed_2, ModItems.upgrade_speed_3);
		addFirstUpgrade(ModItems.upgrade_effect_1, ModItems.upgrade_effect_2);
		addSecondUpgrade(ModItems.upgrade_effect_2, ModItems.upgrade_effect_3);
		addFirstUpgrade(ModItems.upgrade_power_1, ModItems.upgrade_power_2);
		addSecondUpgrade(ModItems.upgrade_power_2, ModItems.upgrade_power_3);
		addFirstUpgrade(ModItems.upgrade_fortune_1, ModItems.upgrade_fortune_2);
		addSecondUpgrade(ModItems.upgrade_fortune_2, ModItems.upgrade_fortune_3);
		addFirstUpgrade(ModItems.upgrade_afterburn_1, ModItems.upgrade_afterburn_2);
		addSecondUpgrade(ModItems.upgrade_afterburn_2, ModItems.upgrade_afterburn_3);
	}
	
	public static void addFirstUpgrade(Item lower, Item higher) {
		boolean lbsm = GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleCrafting;
		recipes.add(new SolderingRecipe(new ItemStack(higher), 300, 10_000,
				new AStack[] {new ComparableStack(ModItems.circuit, lbsm ? 4 : 8, EnumCircuitType.CHIP), new ComparableStack(ModItems.circuit, lbsm ? 2 : 4, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(lower), new OreDictStack(ANY_PLASTIC.ingot(), 4)},
				new AStack[] {}
		));
	}
	
	public static void addSecondUpgrade(Item lower, Item higher) {
		boolean lbsm = GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleCrafting;
		recipes.add(new SolderingRecipe(new ItemStack(higher), 400, 25_000,
				new FluidStack(Fluids.SOLVENT, 500),
				new AStack[] {new ComparableStack(ModItems.circuit, lbsm ? 6 : 16, EnumCircuitType.CHIP), new ComparableStack(ModItems.circuit, lbsm ? 4 : 16, EnumCircuitType.CAPACITOR)},
				new AStack[] {new ComparableStack(lower), new OreDictStack(RUBBER.ingot(), 4)},
				new AStack[] {}
		));
	}
	
	public static SolderingRecipe getRecipe(ItemStack[] inputs) {

		for(SolderingRecipe recipe : recipes) {
			if(matchesIngredients(new ItemStack[] {inputs[0], inputs[1], inputs[2]}, recipe.toppings) &&
					matchesIngredients(new ItemStack[] {inputs[3], inputs[4]}, recipe.pcb) &&
					matchesIngredients(new ItemStack[] {inputs[5]}, recipe.solder)) return recipe;
		}
		
		return null;
	}
	
	public static HashMap getRecipes() {

		HashMap<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(SolderingRecipe recipe : SolderingRecipes.recipes) {
			
			List ingredients = new ArrayList();
			for(AStack stack : recipe.toppings) ingredients.add(stack);
			for(AStack stack : recipe.pcb) ingredients.add(stack);
			for(AStack stack : recipe.solder) ingredients.add(stack);
			if(recipe.fluid != null) ingredients.add(ItemFluidIcon.make(recipe.fluid));
			
			recipes.put(ingredients.toArray(), recipe.output);
		}
		
		return recipes;
	}

	@Override
	public String getFileName() {
		return "hbmSoldering.json";
	}

	@Override
	public Object getRecipeObject() {
		return recipes;
	}

	@Override
	public void deleteRecipes() {
		recipes.clear();
		toppings.clear();
		pcb.clear();
		solder.clear();
	}

	@Override
	public void readRecipe(JsonElement recipe) {
		JsonObject obj = (JsonObject) recipe;

		AStack[] toppings = this.readAStackArray(obj.get("toppings").getAsJsonArray());
		AStack[] pcb = this.readAStackArray(obj.get("pcb").getAsJsonArray());
		AStack[] solder = this.readAStackArray(obj.get("solder").getAsJsonArray());
		FluidStack fluid = obj.has("fluid") ? this.readFluidStack(obj.get("fluid").getAsJsonArray()) : null;
		ItemStack output = this.readItemStack(obj.get("output").getAsJsonArray());
		int duration = obj.get("duration").getAsInt();
		long consumption = obj.get("consumption").getAsLong();
		
		recipes.add(new SolderingRecipe(output, duration, consumption, fluid, toppings, pcb, solder));
	}

	@Override
	public void writeRecipe(Object obj, JsonWriter writer) throws IOException {
		SolderingRecipe recipe = (SolderingRecipe) obj;
		
		writer.name("toppings").beginArray();
		for(AStack aStack : recipe.toppings) this.writeAStack(aStack, writer);
		writer.endArray();
		
		writer.name("pcb").beginArray();
		for(AStack aStack : recipe.pcb) this.writeAStack(aStack, writer);
		writer.endArray();
		
		writer.name("solder").beginArray();
		for(AStack aStack : recipe.solder) this.writeAStack(aStack, writer);
		writer.endArray();
		
		if(recipe.fluid != null) {
			writer.name("fluid");
			this.writeFluidStack(recipe.fluid, writer);
		}
		
		writer.name("output");
		this.writeItemStack(recipe.output, writer);

		writer.name("duration").value(recipe.duration);
		writer.name("consumption").value(recipe.consumption);
	}

	public static HashSet<AStack> toppings = new HashSet();
	public static HashSet<AStack> pcb = new HashSet();
	public static HashSet<AStack> solder = new HashSet();
	
	public static class SolderingRecipe {

		public AStack[] toppings;
		public AStack[] pcb;
		public AStack[] solder;
		public FluidStack fluid;
		public ItemStack output;
		public int duration;
		public long consumption;
		
		public SolderingRecipe(ItemStack output, int duration, long consumption, FluidStack fluid, AStack[] toppings, AStack[] pcb, AStack[] solder) {
			this.toppings = toppings;
			this.pcb = pcb;
			this.solder = solder;
			this.fluid = fluid;
			this.output = output;
			this.duration = duration;
			this.consumption = consumption;
			for(AStack t : toppings) SolderingRecipes.toppings.add(t);
			for(AStack t : pcb) SolderingRecipes.pcb.add(t);
			for(AStack t : solder) SolderingRecipes.solder.add(t);
		}
		
		public SolderingRecipe(ItemStack output, int duration, long consumption, AStack[] toppings, AStack[] pcb, AStack[] solder) {
			this(output, duration, consumption, null, toppings, pcb, solder);
		}
	}
}
