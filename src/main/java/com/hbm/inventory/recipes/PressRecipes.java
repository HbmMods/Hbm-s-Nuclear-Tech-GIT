package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemStamp;
import com.hbm.items.machine.ItemStamp.StampType;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PressRecipes {

	public static HashMap<Pair<AStack, StampType>, ItemStack> recipes = new HashMap();
	
	public static ItemStack getOutput(ItemStack ingredient, ItemStack stamp) {
		
		if(ingredient == null || stamp == null)
			return null;
		
		if(!(stamp.getItem() instanceof ItemStamp))
			return null;
		
		StampType type = ((ItemStamp) stamp.getItem()).type;
		
		for(Entry<Pair<AStack, StampType>, ItemStack> recipe : recipes.entrySet()) {
			
			if(recipe.getKey().getValue() == type && recipe.getKey().getKey().matchesRecipe(ingredient, true))
				return recipe.getValue();
		}
		
		return null;
	}
	
	public static void register() {

		makeRecipe(StampType.FLAT, new OreDictStack(NETHERQUARTZ.dust()),					Items.quartz);
		makeRecipe(StampType.FLAT, new OreDictStack(LAPIS.dust()),							new ItemStack(Items.dye, 1, 4));
		makeRecipe(StampType.FLAT, new OreDictStack(DIAMOND.dust()),						Items.diamond);
		makeRecipe(StampType.FLAT, new OreDictStack(EMERALD.dust()),						Items.emerald);
		makeRecipe(StampType.FLAT, new ComparableStack(ModItems.pellet_coal),				Items.diamond);
		makeRecipe(StampType.FLAT, new ComparableStack(ModItems.biomass),					ModItems.biomass_compressed);
		makeRecipe(StampType.FLAT, new OreDictStack(ANY_COKE.gem()),						ModItems.ingot_graphite);
		makeRecipe(StampType.FLAT, new ComparableStack(ModItems.meteorite_sword_reforged),	ModItems.meteorite_sword_hardened);

		makeRecipe(StampType.FLAT, new OreDictStack(COAL.dust()),							DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.COAL));
		makeRecipe(StampType.FLAT, new OreDictStack(LIGNITE.dust()),						DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.LIGNITE));
		makeRecipe(StampType.FLAT, new ComparableStack(ModItems.powder_sawdust),			DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.WOOD));

		makeRecipe(StampType.PLATE, new OreDictStack(IRON.ingot()),		ModItems.plate_iron);
		makeRecipe(StampType.PLATE, new OreDictStack(GOLD.ingot()),		ModItems.plate_gold);
		makeRecipe(StampType.PLATE, new OreDictStack(TI.ingot()),		ModItems.plate_titanium);
		makeRecipe(StampType.PLATE, new OreDictStack(AL.ingot()),		ModItems.plate_aluminium);
		makeRecipe(StampType.PLATE, new OreDictStack(STEEL.ingot()),	ModItems.plate_steel);
		makeRecipe(StampType.PLATE, new OreDictStack(PB.ingot()),		ModItems.plate_lead);
		makeRecipe(StampType.PLATE, new OreDictStack(CU.ingot()),		ModItems.plate_copper);
		makeRecipe(StampType.PLATE, new OreDictStack(ALLOY.ingot()),	ModItems.plate_advanced_alloy);
		makeRecipe(StampType.PLATE, new OreDictStack(SA326.ingot()),	ModItems.plate_schrabidium);
		makeRecipe(StampType.PLATE, new OreDictStack(CMB.ingot()),		ModItems.plate_combine_steel);
		makeRecipe(StampType.PLATE, new OreDictStack(BIGMT.ingot()),	ModItems.plate_saturnite);

		makeRecipe(StampType.WIRE, new OreDictStack(AL.ingot()),		new ItemStack(ModItems.wire_aluminium, 8));
		makeRecipe(StampType.WIRE, new OreDictStack(CU.ingot()),		new ItemStack(ModItems.wire_copper, 8));
		makeRecipe(StampType.WIRE, new OreDictStack(W.ingot()),			new ItemStack(ModItems.wire_tungsten, 8));
		makeRecipe(StampType.WIRE, new OreDictStack(MINGRADE.ingot()),	new ItemStack(ModItems.wire_red_copper, 8));
		makeRecipe(StampType.WIRE, new OreDictStack(GOLD.ingot()),		new ItemStack(ModItems.wire_gold, 8));
		makeRecipe(StampType.WIRE, new OreDictStack(SA326.ingot()),		new ItemStack(ModItems.wire_schrabidium, 8));
		makeRecipe(StampType.WIRE, new OreDictStack(ALLOY.ingot()),		new ItemStack(ModItems.wire_advanced_alloy, 8));
		makeRecipe(StampType.WIRE, new OreDictStack(MAGTUNG.ingot()),	new ItemStack(ModItems.wire_magnetized_tungsten, 8));

		makeRecipe(StampType.CIRCUIT, new ComparableStack(ModItems.circuit_raw),				ModItems.circuit_aluminium);
		makeRecipe(StampType.CIRCUIT, new ComparableStack(ModItems.circuit_bismuth_raw),		ModItems.circuit_bismuth);
		makeRecipe(StampType.CIRCUIT, new ComparableStack(ModItems.circuit_arsenic_raw),		ModItems.circuit_arsenic);
		makeRecipe(StampType.CIRCUIT, new ComparableStack(ModItems.circuit_tantalium_raw),	ModItems.circuit_tantalium);

		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_iron),			ModItems.gun_revolver_iron_ammo);
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_steel),		ModItems.gun_revolver_ammo);
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_lead),			ModItems.gun_revolver_lead_ammo);
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_gold),			ModItems.gun_revolver_gold_ammo);
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_schrabidium),	ModItems.gun_revolver_schrabidium_ammo);
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_nightmare),	ModItems.gun_revolver_nightmare_ammo);
		makeRecipe(StampType.C357, new ComparableStack(ModItems.assembly_desh),			ModItems.ammo_357_desh);
		makeRecipe(StampType.C357, new OreDictStack(STEEL.ingot()),						ModItems.gun_revolver_cursed_ammo);
		
		makeRecipe(StampType.C44, new ComparableStack(ModItems.assembly_nopip),	ModItems.ammo_44);

		makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_smg),		ModItems.ammo_9mm);
		makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_uzi),		ModItems.ammo_22lr);
		makeRecipe(StampType.C9, new OreDictStack(GOLD.ingot()),					ModItems.ammo_566_gold);
		makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_lacunae),	ModItems.ammo_5mm);
		makeRecipe(StampType.C9, new ComparableStack(ModItems.assembly_556),		ModItems.ammo_556);

		makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_calamity),		ModItems.ammo_50bmg);
		makeRecipe(StampType.C50, new ComparableStack(ModItems.assembly_actionexpress),	ModItems.ammo_50ae);
	}

	public static void makeRecipe(StampType type, AStack in, Item out) {
		recipes.put(new Pair<AStack, StampType>(in, type),  new ItemStack(out));
	}
	public static void makeRecipe(StampType type, AStack in, ItemStack out) {
		recipes.put(new Pair<AStack, StampType>(in, type),  out);
	}
}
