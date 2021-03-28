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

public class CentrifugeRecipes {

	private static HashMap<Object, ItemStack[]> recipes = new HashMap();
	
	public static void register() {
		
		recipes.put(new ComparableStack(ModItems.waste_uranium), new ItemStack[] {
				new ItemStack(ModItems.nugget_u235, 1),
				new ItemStack(ModItems.nugget_u238, 1),
				new ItemStack(ModItems.nugget_pu_mix, 2),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) });
		
		recipes.put(new ComparableStack(ModItems.waste_plutonium), new ItemStack[] {
				new ItemStack(ModItems.nugget_pu_mix, 1),
				new ItemStack(ModItems.nugget_pu_mix, 1),
				new ItemStack(ModItems.nugget_polonium, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 3) });
		
		recipes.put(new ComparableStack(ModItems.waste_mox), new ItemStack[] {
				new ItemStack(ModItems.nugget_pu_mix, 1),
				new ItemStack(ModItems.nugget_u238, 2),
				new ItemStack(ModItems.nugget_polonium, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) });
		
		recipes.put(new ComparableStack(ModItems.waste_schrabidium), new ItemStack[] {
				new ItemStack(ModItems.nugget_beryllium, 1),
				new ItemStack(ModItems.nugget_lead, 1),
				new ItemStack(ModItems.nugget_solinium, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 3) });
		
		recipes.put(new ComparableStack(ModItems.waste_thorium), new ItemStack[] {
				new ItemStack(ModItems.nugget_u238, 1),
				new ItemStack(ModItems.nugget_th232, 1),
				new ItemStack(ModItems.nugget_u233, 3),
				new ItemStack(ModItems.nuclear_waste_tiny, 1) });
		
		recipes.put(new ComparableStack(ModItems.powder_cloud), new ItemStack[] {
				new ItemStack(ModItems.powder_copper, 1),
				new ItemStack(ModItems.sulfur, 1),
				new ItemStack(ModItems.dust, 1),
				new ItemStack(ModItems.dust, 1) });

		recipes.put("oreCoal", new ItemStack[] {
				new ItemStack(ModItems.powder_coal, 2),
				new ItemStack(ModItems.powder_coal, 2),
				new ItemStack(ModItems.powder_coal, 2),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreLignite", new ItemStack[] {
				new ItemStack(ModItems.powder_lignite, 2),
				new ItemStack(ModItems.powder_lignite, 2),
				new ItemStack(ModItems.powder_lignite, 2),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreIron", new ItemStack[] {
				new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreGold", new ItemStack[] {
				new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreDiamond", new ItemStack[] {
				new ItemStack(ModItems.powder_diamond, 1),
				new ItemStack(ModItems.powder_diamond, 1),
				new ItemStack(ModItems.powder_diamond, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreEmerald", new ItemStack[] {
				new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreTitanium", new ItemStack[] {
				new ItemStack(ModItems.powder_titanium, 1),
				new ItemStack(ModItems.powder_titanium, 1),
				new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreQuartz", new ItemStack[] {
				new ItemStack(ModItems.powder_quartz, 1),
				new ItemStack(ModItems.powder_quartz, 1),
				new ItemStack(ModItems.powder_lithium_tiny, 1),
				new ItemStack(Blocks.netherrack, 1) });
		
		recipes.put("oreTungsten", new ItemStack[] {
				new ItemStack(ModItems.powder_tungsten, 1),
				new ItemStack(ModItems.powder_tungsten, 1),
				new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreCopper", new ItemStack[] {
				new ItemStack(ModItems.powder_copper, 1),
				new ItemStack(ModItems.powder_copper, 1),
				new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreAluminum", new ItemStack[] {
				new ItemStack(ModItems.powder_aluminium, 1),
				new ItemStack(ModItems.powder_aluminium, 1),
				new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreLead", new ItemStack[] {
				new ItemStack(ModItems.powder_lead, 1),
				new ItemStack(ModItems.powder_lead, 1),
				new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreSchrabidium", new ItemStack[] {
				new ItemStack(ModItems.powder_schrabidium, 1),
				new ItemStack(ModItems.powder_schrabidium, 1),
				new ItemStack(ModItems.nugget_solinium, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put(new ComparableStack(ModBlocks.ore_rare), new ItemStack[] {
				new ItemStack(ModItems.powder_desh_mix, 1),
				new ItemStack(ModItems.nugget_zirconium, 1),
				new ItemStack(ModItems.nugget_zirconium, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("orePlutonium", new ItemStack[] {
				new ItemStack(ModItems.powder_plutonium, 1),
				new ItemStack(ModItems.powder_plutonium, 1),
				new ItemStack(ModItems.nugget_polonium, 3),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreUranium", new ItemStack[] {
				new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(ModItems.nugget_polonium, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreThorium", new ItemStack[] {
				new ItemStack(ModItems.powder_thorium, 1),
				new ItemStack(ModItems.powder_thorium, 1),
				new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreBeryllium", new ItemStack[] {
				new ItemStack(ModItems.powder_beryllium, 1),
				new ItemStack(ModItems.powder_beryllium, 1),
				new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put("oreRedstone", new ItemStack[] {
				new ItemStack(Items.redstone, 3),
				new ItemStack(Items.redstone, 3),
				new ItemStack(ModItems.nugget_mercury, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put(new ComparableStack(ModBlocks.ore_tikite), new ItemStack[] {
				new ItemStack(ModItems.powder_plutonium, 2),
				new ItemStack(ModItems.powder_cobalt, 2),
				new ItemStack(ModItems.powder_nitan_mix, 1),
				new ItemStack(Blocks.end_stone, 1) });
		
		recipes.put("oreLapis", new ItemStack[] {
				new ItemStack(ModItems.powder_lapis, 3),
				new ItemStack(ModItems.powder_lapis, 3),
				new ItemStack(ModItems.powder_cobalt_tiny, 1),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put(new ComparableStack(ModBlocks.ore_meteor_starmetal), new ItemStack[] {
				new ItemStack(ModItems.powder_dura_steel, 3),
				new ItemStack(ModItems.powder_astatine, 1),
				new ItemStack(ModItems.powder_cobalt, 2),
				new ItemStack(Blocks.gravel, 1) });
		
		recipes.put(new ComparableStack(ModBlocks.block_euphemium_cluster), new ItemStack[] {
				new ItemStack(ModItems.nugget_euphemium, 7),
				new ItemStack(ModItems.powder_schrabidium, 4),
				new ItemStack(ModItems.ingot_starmetal, 2),
				new ItemStack(ModItems.nugget_solinium, 2) });
		
		recipes.put(new ComparableStack(ModBlocks.ore_nether_fire), new ItemStack[] {
				new ItemStack(Items.blaze_powder, 2),
				new ItemStack(ModItems.powder_fire, 2),
				new ItemStack(ModItems.ingot_phosphorus),
				new ItemStack(Blocks.netherrack) });
		
		recipes.put(new ComparableStack(Items.blaze_rod), new ItemStack[] {new ItemStack(Items.blaze_powder, 1), new ItemStack(Items.blaze_powder, 1), new ItemStack(ModItems.powder_fire, 1), new ItemStack(ModItems.powder_fire, 1) });

		recipes.put(new ComparableStack(ModItems.ingot_schraranium), new ItemStack[] { new ItemStack(ModItems.nugget_schrabidium, 2), new ItemStack(ModItems.nugget_schrabidium, 1), new ItemStack(ModItems.nugget_uranium, 3), new ItemStack(ModItems.nugget_plutonium, 2) });

		recipes.put(new ComparableStack(ModItems.crystal_iron), new ItemStack[] { new ItemStack(ModItems.powder_iron, 2), new ItemStack(ModItems.powder_iron, 2), new ItemStack(ModItems.powder_titanium, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_gold), new ItemStack[] { new ItemStack(ModItems.powder_gold, 2), new ItemStack(ModItems.powder_gold, 2), new ItemStack(ModItems.nugget_mercury, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_redstone), new ItemStack[] { new ItemStack(Items.redstone, 3), new ItemStack(Items.redstone, 3), new ItemStack(Items.redstone, 3), new ItemStack(ModItems.nugget_mercury, 3) });
		recipes.put(new ComparableStack(ModItems.crystal_diamond), new ItemStack[] { new ItemStack(ModItems.powder_diamond, 1), new ItemStack(ModItems.powder_diamond, 1), new ItemStack(ModItems.powder_diamond, 1), new ItemStack(ModItems.powder_diamond, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_uranium), new ItemStack[] { new ItemStack(ModItems.powder_uranium, 2), new ItemStack(ModItems.powder_uranium, 2), new ItemStack(ModItems.nugget_polonium, 2), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_thorium), new ItemStack[] { new ItemStack(ModItems.powder_thorium, 2), new ItemStack(ModItems.powder_thorium, 2), new ItemStack(ModItems.powder_uranium, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_plutonium), new ItemStack[] { new ItemStack(ModItems.powder_plutonium, 2), new ItemStack(ModItems.powder_plutonium, 2), new ItemStack(ModItems.powder_polonium, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_titanium), new ItemStack[] { new ItemStack(ModItems.powder_titanium, 2), new ItemStack(ModItems.powder_titanium, 2), new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_sulfur), new ItemStack[] { new ItemStack(ModItems.sulfur, 4), new ItemStack(ModItems.sulfur, 4), new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.nugget_mercury, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_niter), new ItemStack[] { new ItemStack(ModItems.niter, 3), new ItemStack(ModItems.niter, 3), new ItemStack(ModItems.niter, 3), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_copper), new ItemStack[] { new ItemStack(ModItems.powder_copper, 2), new ItemStack(ModItems.powder_copper, 2), new ItemStack(ModItems.sulfur, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_tungsten), new ItemStack[] { new ItemStack(ModItems.powder_tungsten, 2), new ItemStack(ModItems.powder_tungsten, 2), new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_aluminium), new ItemStack[] { new ItemStack(ModItems.powder_aluminium, 2), new ItemStack(ModItems.powder_aluminium, 2), new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_fluorite), new ItemStack[] { new ItemStack(ModItems.fluorite, 3), new ItemStack(ModItems.fluorite, 3), new ItemStack(ModItems.fluorite, 3), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_beryllium), new ItemStack[] { new ItemStack(ModItems.powder_beryllium, 2), new ItemStack(ModItems.powder_beryllium, 2), new ItemStack(ModItems.powder_quartz, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_lead), new ItemStack[] { new ItemStack(ModItems.powder_lead, 2), new ItemStack(ModItems.powder_lead, 2), new ItemStack(ModItems.powder_gold, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_schraranium), new ItemStack[] { new ItemStack(ModItems.nugget_schrabidium, 2), new ItemStack(ModItems.nugget_schrabidium, 2), new ItemStack(ModItems.nugget_uranium, 2), new ItemStack(ModItems.nugget_plutonium, 2) });
		recipes.put(new ComparableStack(ModItems.crystal_schrabidium), new ItemStack[] { new ItemStack(ModItems.powder_schrabidium, 2), new ItemStack(ModItems.powder_schrabidium, 2), new ItemStack(ModItems.powder_plutonium, 1), new ItemStack(ModItems.powder_lithium_tiny, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_rare), new ItemStack[] { new ItemStack(ModItems.powder_desh_mix, 1), new ItemStack(ModItems.powder_desh_mix, 1), new ItemStack(ModItems.nugget_zirconium, 2), new ItemStack(ModItems.nugget_zirconium, 2) });
		recipes.put(new ComparableStack(ModItems.crystal_phosphorus), new ItemStack[] { new ItemStack(ModItems.powder_fire, 3), new ItemStack(ModItems.powder_fire, 3), new ItemStack(ModItems.ingot_phosphorus, 2), new ItemStack(Items.blaze_powder, 2) });
		recipes.put(new ComparableStack(ModItems.crystal_trixite), new ItemStack[] { new ItemStack(ModItems.powder_plutonium, 3), new ItemStack(ModItems.powder_cobalt, 3), new ItemStack(ModItems.powder_spark_mix, 1), new ItemStack(ModItems.powder_nitan_mix, 2) });
		recipes.put(new ComparableStack(ModItems.crystal_lithium), new ItemStack[] { new ItemStack(ModItems.powder_lithium, 2), new ItemStack(ModItems.powder_lithium, 2), new ItemStack(ModItems.powder_quartz, 1), new ItemStack(ModItems.fluorite, 1) });
		recipes.put(new ComparableStack(ModItems.crystal_starmetal), new ItemStack[] { new ItemStack(ModItems.powder_dura_steel, 3), new ItemStack(ModItems.powder_cobalt, 3), new ItemStack(ModItems.powder_astatine, 2), new ItemStack(ModItems.nugget_mercury, 5) });
	}
	
	public static ItemStack[] getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
		
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		
		if(recipes.containsKey(comp))
			return RecipesCommon.copyStackArray(recipes.get(comp));
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(recipes.containsKey(key))
				return RecipesCommon.copyStackArray(recipes.get(key));
		}
		
		return null;
	}

	public static Map<Object, Object[]> getRecipes() {
		
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		
		for(Entry<Object, ItemStack[]> entry : CentrifugeRecipes.recipes.entrySet()) {
			
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
