package com.hbm.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//This time we're doing this right
//...right?
public class CrystallizerRecipes {
	
	//'Object' is either a ComparableStack or the key for the ore dict
	private static HashMap<Object, ItemStack> recipes = new HashMap();
	
	public static void register() {

		recipes.put("oreCoal", new ItemStack(ModItems.crystal_coal));
		recipes.put("oreIron", new ItemStack(ModItems.crystal_iron));
		recipes.put("oreGold", new ItemStack(ModItems.crystal_gold));
		recipes.put("oreRedstone", new ItemStack(ModItems.crystal_redstone));
		recipes.put("oreLapis", new ItemStack(ModItems.crystal_lapis));
		recipes.put("oreDiamond", new ItemStack(ModItems.crystal_diamond));
		recipes.put("oreUranium", new ItemStack(ModItems.crystal_uranium));
		recipes.put("oreThorium", new ItemStack(ModItems.crystal_thorium));
		recipes.put("orePlutonium", new ItemStack(ModItems.crystal_plutonium));
		recipes.put("oreTitanium", new ItemStack(ModItems.crystal_titanium));
		recipes.put("oreSulfur", new ItemStack(ModItems.crystal_sulfur));
		recipes.put("oreNiter", new ItemStack(ModItems.crystal_niter));
		recipes.put("oreSaltpeter", new ItemStack(ModItems.crystal_niter));
		recipes.put("oreCopper", new ItemStack(ModItems.crystal_copper));
		recipes.put("oreTungsten", new ItemStack(ModItems.crystal_tungsten));
		recipes.put("oreAluminum", new ItemStack(ModItems.crystal_aluminium));
		recipes.put("oreFluorite", new ItemStack(ModItems.crystal_fluorite));
		recipes.put("oreBeryllium", new ItemStack(ModItems.crystal_beryllium));
		recipes.put("oreLead", new ItemStack(ModItems.crystal_lead));
		recipes.put("oreSchrabidium", new ItemStack(ModItems.crystal_schrabidium));
		recipes.put("oreLithium", new ItemStack(ModItems.crystal_lithium));
		recipes.put("oreStarmetal", new ItemStack(ModItems.crystal_starmetal));
		recipes.put("oreRareEarth", new ItemStack(ModItems.crystal_rare));
		recipes.put("oreCobalt", new ItemStack(ModItems.crystal_cobalt));
		
		recipes.put(new ComparableStack(ModBlocks.ore_nether_fire), new ItemStack(ModItems.crystal_phosphorus));
		recipes.put(new ComparableStack(ModBlocks.ore_tikite), new ItemStack(ModItems.crystal_trixite));
		recipes.put(new ComparableStack(ModBlocks.gravel_diamond), new ItemStack(ModItems.crystal_diamond));
		recipes.put(new ComparableStack(ModItems.ingot_schraranium), new ItemStack(ModItems.crystal_schraranium));
		
		recipes.put("sand", new ItemStack(ModItems.ingot_fiberglass));
		recipes.put(new ComparableStack(Blocks.cobblestone), new ItemStack(ModBlocks.reinforced_stone));
		recipes.put(new ComparableStack(ModBlocks.gravel_obsidian), new ItemStack(ModBlocks.brick_obsidian));
		recipes.put("blockRedstone", new ItemStack(ModItems.nugget_mercury));
		recipes.put(new ComparableStack(Items.rotten_flesh), new ItemStack(Items.leather));
		recipes.put(new ComparableStack(ModItems.coal_infernal), new ItemStack(ModItems.solid_fuel));
		recipes.put(new ComparableStack(ModItems.cinnebar), new ItemStack(ModItems.nugget_mercury, 3));
		recipes.put("blockCoal", new ItemStack(ModBlocks.block_graphite));
		recipes.put(new ComparableStack(ModBlocks.stone_gneiss), new ItemStack(ModItems.powder_lithium));
		
		recipes.put(new ComparableStack(ModItems.powder_diamond), new ItemStack(Items.diamond));
		recipes.put(new ComparableStack(ModItems.powder_emerald), new ItemStack(Items.emerald));
		recipes.put(new ComparableStack(ModItems.powder_lapis), new ItemStack(Items.dye, 1, 4));
		recipes.put(new ComparableStack(ModItems.powder_semtex_mix), new ItemStack(ModItems.ingot_semtex));
		recipes.put(new ComparableStack(ModItems.powder_desh_ready), new ItemStack(ModItems.ingot_desh));
		recipes.put(new ComparableStack(ModItems.powder_meteorite), new ItemStack(ModItems.fragment_meteorite, 1));
		
		recipes.put(new ComparableStack(ModItems.meteorite_sword_treated), new ItemStack(ModItems.meteorite_sword_etched, 1));
		
		List<ItemStack> quartz = OreDictionary.getOres("crystalCertusQuartz");
		
		if(quartz != null && !quartz.isEmpty()) {
			ItemStack qItem = quartz.get(0).copy();
			qItem.stackSize = 6;
			recipes.put("oreCertusQuartz", qItem);
		}
	}
	
	public static ItemStack getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(recipes.containsKey(comp))
			return recipes.get(comp).copy();
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(recipes.containsKey(key))
				return recipes.get(key).copy();
		}
		
		return null;
	}

	public static Map<Object, Object> getRecipes() {
		
		Map<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(Entry<Object, ItemStack> entry : CrystallizerRecipes.recipes.entrySet()) {
			
			if(entry.getKey() instanceof String) {
				List<ItemStack> ingredients = OreDictionary.getOres((String)entry.getKey());
				recipes.put(ingredients, entry.getValue());
			} else {
				recipes.put(((ComparableStack)entry.getKey()).toStack(), entry.getValue());
			}
		}
		
		return recipes;
	}

}
