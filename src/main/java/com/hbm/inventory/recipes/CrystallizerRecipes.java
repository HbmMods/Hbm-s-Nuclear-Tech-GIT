package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.imc.IMCCrystallizer;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//This time we're doing this right
//...right?
public class CrystallizerRecipes {
	
	//'Object' is either a ComparableStack or the key for the ore dict
	private static HashMap<Object, ItemStack> recipes = new HashMap<Object, ItemStack>();
	
	public static void register() {

		recipes.put(COAL.ore(), new ItemStack(ModItems.crystal_coal));
		recipes.put(FE.ore(), new ItemStack(ModItems.crystal_iron));
		recipes.put(AU.ore(), new ItemStack(ModItems.crystal_gold));
		recipes.put(REDSTONE.ore(), new ItemStack(ModItems.crystal_redstone));
		recipes.put(LAPIS.ore(), new ItemStack(ModItems.crystal_lapis));
		recipes.put(DIAMOND.ore(), new ItemStack(ModItems.crystal_diamond));
		recipes.put(U.ore(), new ItemStack(ModItems.crystal_uranium));
		recipes.put(TH232.ore(), new ItemStack(ModItems.crystal_thorium));
		recipes.put(PU.ore(), new ItemStack(ModItems.crystal_plutonium));
		recipes.put(TI.ore(), new ItemStack(ModItems.crystal_titanium));
		recipes.put(S.ore(), new ItemStack(ModItems.crystal_sulfur));
		recipes.put(KNO.ore(), new ItemStack(ModItems.crystal_niter));
		recipes.put(CU.ore(), new ItemStack(ModItems.crystal_copper));
		recipes.put(W.ore(), new ItemStack(ModItems.crystal_tungsten));
		recipes.put(AL.ore(), new ItemStack(ModItems.crystal_aluminium));
		recipes.put(F.ore(), new ItemStack(ModItems.crystal_fluorite));
		recipes.put(BE.ore(), new ItemStack(ModItems.crystal_beryllium));
		recipes.put(PB.ore(), new ItemStack(ModItems.crystal_lead));
		recipes.put(SA326.ore(), new ItemStack(ModItems.crystal_schrabidium));
		recipes.put(LI.ore(), new ItemStack(ModItems.crystal_lithium));
		recipes.put(STAR.ore(), new ItemStack(ModItems.crystal_starmetal));
		recipes.put(CO.ore(), new ItemStack(ModItems.crystal_cobalt));
		
		recipes.put("oreRareEarth", new ItemStack(ModItems.crystal_rare));
		recipes.put("oreCinnabar", new ItemStack(ModItems.crystal_cinnebar));
		
		recipes.put(new ComparableStack(ModBlocks.ore_nether_fire), new ItemStack(ModItems.crystal_phosphorus));
		recipes.put(new ComparableStack(ModBlocks.ore_tikite), new ItemStack(ModItems.crystal_trixite));
		recipes.put(new ComparableStack(ModBlocks.gravel_diamond), new ItemStack(ModItems.crystal_diamond));
		recipes.put(SRN.ingot(), new ItemStack(ModItems.crystal_schraranium));
		
		recipes.put("sand", new ItemStack(ModItems.ingot_fiberglass));
		recipes.put(REDSTONE.block(), new ItemStack(ModItems.ingot_mercury));
		recipes.put(CINNABAR.crystal(), new ItemStack(ModItems.ingot_mercury, 3));
		recipes.put(BORAX.dust(), new ItemStack(ModItems.powder_boron_tiny, 3));
		recipes.put(COAL.block(), new ItemStack(ModBlocks.block_graphite));

		recipes.put(new ComparableStack(Blocks.cobblestone), new ItemStack(ModBlocks.reinforced_stone));
		recipes.put(new ComparableStack(ModBlocks.gravel_obsidian), new ItemStack(ModBlocks.brick_obsidian));
		recipes.put(new ComparableStack(Items.rotten_flesh), new ItemStack(Items.leather));
		recipes.put(new ComparableStack(ModItems.coal_infernal), new ItemStack(ModItems.solid_fuel));
		recipes.put(new ComparableStack(ModBlocks.stone_gneiss), new ItemStack(ModItems.powder_lithium));
		
		recipes.put(DIAMOND.dust(), new ItemStack(Items.diamond));
		recipes.put(EMERALD.dust(), new ItemStack(Items.emerald));
		recipes.put(LAPIS.dust(), new ItemStack(Items.dye, 1, 4));
		recipes.put(new ComparableStack(ModItems.powder_semtex_mix), new ItemStack(ModItems.ingot_semtex));
		recipes.put(new ComparableStack(ModItems.powder_desh_ready), new ItemStack(ModItems.ingot_desh));
		recipes.put(new ComparableStack(ModItems.powder_meteorite), new ItemStack(ModItems.fragment_meteorite, 1));
		
		recipes.put(new ComparableStack(ModItems.meteorite_sword_treated), new ItemStack(ModItems.meteorite_sword_etched, 1));
		
		recipes.put(new ComparableStack(ModItems.powder_impure_osmiridium), new ItemStack(ModItems.crystal_osmiridium));
		
		for(int i = 0; i < ScrapType.values().length; i++) {
			recipes.put(new ComparableStack(ModItems.scrap_plastic, 1, i), new ItemStack(ModItems.circuit_star_piece, 1, i));
		}
		
		List<ItemStack> quartz = OreDictionary.getOres("crystalCertusQuartz");
		
		if(quartz != null && !quartz.isEmpty()) {
			ItemStack qItem = quartz.get(0).copy();
			qItem.stackSize = 12;
			recipes.put("oreCertusQuartz", qItem);
		}
		
		if(!IMCCrystallizer.buffer.isEmpty()) {
			recipes.putAll(IMCCrystallizer.buffer);
			MainRegistry.logger.info("Fetched " + IMCCrystallizer.buffer.size() + " IMC crystallizer recipes!");
			IMCCrystallizer.buffer.clear();
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
