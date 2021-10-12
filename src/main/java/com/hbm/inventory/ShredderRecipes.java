package com.hbm.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ShredderRecipes {

	public static HashMap<ComparableStack, ItemStack> shredderRecipes = new HashMap<ComparableStack, ItemStack>();
	public static HashMap<Object, Object> neiShredderRecipes;
	@Spaghetti("Ok, I get it, but wow")
	public static void registerShredder() {
		
		String[] names = OreDictionary.getOreNames();
		
		for(int i = 0; i < names.length; i++) {
			
			String name = names[i];
			
			//if the dict contains invalid names, skip
			if(name == null || name.isEmpty())
				continue;
			
			List<ItemStack> matches = OreDictionary.getOres(name);
			
			//if the name isn't assigned to an ore, also skip
			if(matches == null || matches.isEmpty())
				continue;

			if(name.length() > 5 && name.substring(0, 5).equals("ingot")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 3).equals("ore")) {
				ItemStack dust = getDustByName(name.substring(3));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.stackSize = 2;

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 5 && name.substring(0, 5).equals("block")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.stackSize = 9;

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 3).equals("gem")) {
				ItemStack dust = getDustByName(name.substring(3));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			} 
			else if(name.length() > 5 && name.startsWith("plate")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new ComparableStack(stack), dust);
					}
				}
			}
			else if (name.length() > 6 && name.startsWith("nugget"))
			{
				ItemStack smallDust = getSmallDustByName(name.substring(6));
				
				if (smallDust != null && smallDust.getItem() != ModItems.scrap)
					for (ItemStack stack : matches)
						shredderRecipes.put(new ComparableStack(stack), smallDust);
			}
			else if(name.length() > 3 && name.substring(0, 4).equals("dust")) {

				for(ItemStack stack : matches) {
					shredderRecipes.put(new ComparableStack(stack), new ItemStack(ModItems.dust));
				}
			}
		}
	}
	
	public static void registerOverrides() {

		setRecipe(ModItems.scrap, new ItemStack(ModItems.dust));
		setRecipe(ModItems.dust, new ItemStack(ModItems.dust));
		setRecipe(Blocks.glowstone, new ItemStack(Items.glowstone_dust, 4));
		setRecipe(new ItemStack(Blocks.quartz_block, 1, 0), new ItemStack(ModItems.powder_quartz, 4));
		setRecipe(new ItemStack(Blocks.quartz_block, 1, 1), new ItemStack(ModItems.powder_quartz, 4));
		setRecipe(new ItemStack(Blocks.quartz_block, 1, 2), new ItemStack(ModItems.powder_quartz, 4));
		setRecipe(Blocks.quartz_stairs, new ItemStack(ModItems.powder_quartz, 3));
		setRecipe(new ItemStack(Blocks.stone_slab, 1, 7), new ItemStack(ModItems.powder_quartz, 2));
		setRecipe(Items.quartz, new ItemStack(ModItems.powder_quartz));
		setRecipe(Blocks.quartz_ore, new ItemStack(ModItems.powder_quartz, 2));
		setRecipe(ModBlocks.ore_nether_fire, new ItemStack(ModItems.powder_fire, 6));
		setRecipe(Blocks.packed_ice, new ItemStack(ModItems.powder_ice, 1));
		setRecipe(ModBlocks.brick_light, new ItemStack(Items.clay_ball, 4));
		setRecipe(ModBlocks.concrete, new ItemStack(Blocks.gravel, 1));
		setRecipe(ModBlocks.concrete_smooth, new ItemStack(Blocks.gravel, 1));
		setRecipe(ModBlocks.brick_concrete, new ItemStack(Blocks.gravel, 1));
		setRecipe(ModBlocks.brick_concrete_mossy, new ItemStack(Blocks.gravel, 1));
		setRecipe(ModBlocks.brick_concrete_cracked, new ItemStack(Blocks.gravel, 1));
		setRecipe(ModBlocks.brick_concrete_broken, new ItemStack(Blocks.gravel, 1));
		setRecipe(ModBlocks.brick_obsidian, new ItemStack(ModBlocks.gravel_obsidian, 1));
		setRecipe(Blocks.obsidian, new ItemStack(ModBlocks.gravel_obsidian, 1));
		setRecipe(Blocks.stone, new ItemStack(Blocks.gravel, 1));
		setRecipe(ModBlocks.ore_oil_empty, new ItemStack(Blocks.gravel, 1));
		setRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel, 1));
		setRecipe(Blocks.stonebrick, new ItemStack(Blocks.gravel, 1));
		setRecipe(Blocks.gravel, new ItemStack(Blocks.sand, 1));
		setRecipe(Blocks.sand, new ItemStack(ModItems.dust, 2));
		setRecipe(Blocks.brick_block, new ItemStack(Items.clay_ball, 4));
		setRecipe(Blocks.brick_stairs, new ItemStack(Items.clay_ball, 3));
		setRecipe(Items.flower_pot, new ItemStack(Items.clay_ball, 3));
		setRecipe(Items.brick, new ItemStack(Items.clay_ball, 1));
		setRecipe(Blocks.sandstone, new ItemStack(Blocks.sand, 4));
		setRecipe(Blocks.sandstone_stairs, new ItemStack(Blocks.sand, 6));
		setRecipe(Blocks.clay, new ItemStack(Items.clay_ball, 4));
		setRecipe(Blocks.hardened_clay, new ItemStack(Items.clay_ball, 4));
		setRecipe(Blocks.tnt, new ItemStack(Items.gunpowder, 5));
		setRecipe(ModBlocks.stone_gneiss, new ItemStack(ModItems.powder_lithium_tiny, 1));
		setRecipe(ModItems.powder_lapis, new ItemStack(ModItems.powder_cobalt_tiny, 1));
		setRecipe(ModItems.fragment_neodymium, new ItemStack(ModItems.powder_neodymium_tiny, 1));
		setRecipe(ModItems.fragment_cobalt, new ItemStack(ModItems.powder_cobalt_tiny, 1));
		setRecipe(ModItems.fragment_niobium, new ItemStack(ModItems.powder_niobium_tiny, 1));
		setRecipe(ModItems.fragment_cerium, new ItemStack(ModItems.powder_cerium_tiny, 1));
		setRecipe(ModItems.fragment_lanthanium, new ItemStack(ModItems.powder_lanthanium_tiny, 1));
		setRecipe(ModItems.fragment_actinium, new ItemStack(ModItems.powder_actinium_tiny, 1));
		setRecipe(ModItems.fragment_meteorite, new ItemStack(ModItems.powder_meteorite_tiny, 1));
		setRecipe(ModBlocks.block_meteor, new ItemStack(ModItems.powder_meteorite, 10));
		setRecipe(Items.enchanted_book, new ItemStack(ModItems.powder_magic, 1));
		setRecipe(ModItems.arc_electrode_burnt, new ItemStack(ModItems.powder_coal, 1));
		setRecipe(ModItems.arc_electrode_desh, new ItemStack(ModItems.powder_desh, 2));
		setRecipe(ModBlocks.meteor_polished, new ItemStack(ModItems.powder_meteorite, 1));
		setRecipe(ModBlocks.meteor_brick, new ItemStack(ModItems.powder_meteorite, 1));
		setRecipe(ModBlocks.meteor_brick_mossy, new ItemStack(ModItems.powder_meteorite, 1));
		setRecipe(ModBlocks.meteor_brick_cracked, new ItemStack(ModItems.powder_meteorite, 1));
		setRecipe(ModBlocks.meteor_brick_chiseled, new ItemStack(ModItems.powder_meteorite, 1));
		setRecipe(ModBlocks.meteor_pillar, new ItemStack(ModItems.powder_meteorite, 1));
		setRecipe(ModBlocks.ore_rare, new ItemStack(ModItems.powder_desh_mix, 1));
		setRecipe(ModBlocks.ore_cobalt, new ItemStack(ModItems.powder_cobalt, 1));
		setRecipe(ModBlocks.ore_nether_cobalt, new ItemStack(ModItems.powder_cobalt, 1));
		setRecipe(Blocks.diamond_ore, new ItemStack(ModBlocks.gravel_diamond, 2));
		setRecipe(ModBlocks.boxcar, new ItemStack(ModItems.powder_steel, 32));
		setRecipe(ModItems.ingot_schrabidate, new ItemStack(ModItems.powder_schrabidate, 1));
		setRecipe(ModBlocks.block_schrabidate, new ItemStack(ModItems.powder_schrabidate, 9));
		setRecipe(ModItems.pellet_lead, new ItemStack(ModItems.powder_lead, 2));
		setRecipe(ModBlocks.glass_polonium, new ItemStack(ModBlocks.sand_polonium));
		setRecipe(ModBlocks.glass_quartz, new ItemStack(ModBlocks.sand_quartz));
		setRecipe(ModBlocks.glass_uranium, new ItemStack(ModBlocks.sand_uranium));
		setRecipe(ModBlocks.brick_concrete_stairs, new ItemStack(Blocks.gravel));
		setRecipe(ModBlocks.concrete_smooth_stairs, new ItemStack(Blocks.gravel));
		setRecipe(ModBlocks.concrete_stairs, new ItemStack(Blocks.gravel));
		setRecipe(ModItems.wafer_silicon, new ItemStack(ModItems.powder_silicon));
		setRecipe(ModItems.wafer_diamond, new ItemStack(ModItems.powder_diamond));
		setRecipe(ModItems.wafer_gold, new ItemStack(ModItems.powder_gold));
		setRecipe(ModItems.wafer_lapis, new ItemStack(ModItems.wafer_lapis));
		setRecipe(ModItems.wafer_spark, new ItemStack(ModItems.powder_spark_mix));

		setRecipe(ModItems.ingot_schraranium, new ItemStack(ModItems.nugget_schrabidium, 2));
		setRecipe(ModItems.crystal_iron, new ItemStack(ModItems.powder_iron, 3));
		setRecipe(ModItems.crystal_gold, new ItemStack(ModItems.powder_gold, 3));
		setRecipe(ModItems.crystal_redstone, new ItemStack(Items.redstone, 8));
		setRecipe(ModItems.crystal_diamond, new ItemStack(ModItems.powder_diamond, 3));
		setRecipe(ModItems.crystal_uranium, new ItemStack(ModItems.powder_uranium, 3));
		setRecipe(ModItems.crystal_plutonium, new ItemStack(ModItems.powder_plutonium, 3));
		setRecipe(ModItems.crystal_thorium, new ItemStack(ModItems.powder_thorium, 3));
		setRecipe(ModItems.crystal_titanium, new ItemStack(ModItems.powder_titanium, 3));
		setRecipe(ModItems.crystal_sulfur, new ItemStack(ModItems.sulfur, 8));
		setRecipe(ModItems.crystal_niter, new ItemStack(ModItems.niter, 8));
		setRecipe(ModItems.crystal_copper, new ItemStack(ModItems.powder_copper, 3));
		setRecipe(ModItems.crystal_tungsten, new ItemStack(ModItems.powder_tungsten, 3));
		setRecipe(ModItems.crystal_aluminium, new ItemStack(ModItems.powder_aluminium, 3));
		setRecipe(ModItems.crystal_fluorite, new ItemStack(ModItems.fluorite, 8));
		setRecipe(ModItems.crystal_beryllium, new ItemStack(ModItems.powder_beryllium, 3));
		setRecipe(ModItems.crystal_lead, new ItemStack(ModItems.powder_lead, 3));
		setRecipe(ModItems.crystal_schraranium, new ItemStack(ModItems.nugget_schrabidium, 3));
		setRecipe(ModItems.crystal_schrabidium, new ItemStack(ModItems.powder_schrabidium, 3));
		setRecipe(ModItems.crystal_rare, new ItemStack(ModItems.powder_desh_mix, 2));
		setRecipe(ModItems.crystal_phosphorus, new ItemStack(ModItems.powder_fire, 8));
		setRecipe(ModItems.crystal_trixite, new ItemStack(ModItems.powder_plutonium, 6));
		setRecipe(ModItems.crystal_lithium, new ItemStack(ModItems.powder_lithium, 3));
		setRecipe(ModItems.crystal_starmetal, new ItemStack(ModItems.powder_dura_steel, 6));
		setRecipe(ModItems.crystal_cobalt, new ItemStack(ModItems.powder_cobalt, 2));

		setRecipe(ModBlocks.steel_poles, new ItemStack(ModItems.powder_steel_tiny, 3));
		setRecipe(ModBlocks.pole_top, new ItemStack(ModItems.powder_tungsten, 4));
		setRecipe(ModBlocks.tape_recorder, new ItemStack(ModItems.powder_steel, 1));
		setRecipe(ModBlocks.pole_satellite_receiver, new ItemStack(ModItems.powder_steel, 5));
		setRecipe(ModBlocks.steel_roof, new ItemStack(ModItems.powder_steel_tiny, 13));
		setRecipe(ModBlocks.steel_wall, new ItemStack(ModItems.powder_steel_tiny, 13));
		setRecipe(ModBlocks.steel_corner, new ItemStack(ModItems.powder_steel_tiny, 26));
		setRecipe(ModBlocks.steel_beam, new ItemStack(ModItems.powder_steel_tiny, 3));
		setRecipe(ModBlocks.steel_scaffold, new ItemStack(ModItems.powder_steel_tiny, 7));
		setRecipe(ModItems.coil_copper, new ItemStack(ModItems.powder_red_copper, 1));
		setRecipe(ModItems.coil_copper_torus, new ItemStack(ModItems.powder_red_copper, 2));
		setRecipe(ModItems.coil_advanced_alloy, new ItemStack(ModItems.powder_advanced_alloy, 1));
		setRecipe(ModItems.coil_advanced_torus, new ItemStack(ModItems.powder_advanced_alloy, 2));
		setRecipe(ModItems.coil_gold, new ItemStack(ModItems.powder_gold, 1));
		setRecipe(ModItems.coil_gold_torus, new ItemStack(ModItems.powder_gold, 2));
		setRecipe(ModItems.coil_tungsten, new ItemStack(ModItems.powder_tungsten, 1));
		setRecipe(ModItems.coil_magnetized_tungsten, new ItemStack(ModItems.powder_magnetized_tungsten, 1));
		setRecipe(ModBlocks.crate_iron, new ItemStack(ModItems.powder_iron, 8));
		setRecipe(ModBlocks.crate_steel, new ItemStack(ModItems.powder_steel, 8));
		setRecipe(ModBlocks.crate_tungsten, new ItemStack(ModItems.powder_tungsten, 36));
		setRecipe(Blocks.anvil, new ItemStack(ModItems.powder_iron, 31));
		setRecipe(ModBlocks.chain, new ItemStack(ModItems.powder_steel_tiny, 1));

		setRecipe(ModBlocks.turret_light, new ItemStack(ModItems.powder_steel, 16));
		setRecipe(ModBlocks.turret_heavy, new ItemStack(ModItems.powder_steel, 16));
		setRecipe(ModBlocks.turret_flamer, new ItemStack(ModItems.powder_steel, 16));
		setRecipe(ModBlocks.turret_rocket, new ItemStack(ModItems.powder_steel, 16));
		setRecipe(ModBlocks.turret_cwis, new ItemStack(ModItems.powder_steel, 16));
		setRecipe(ModBlocks.turret_tau, new ItemStack(ModItems.powder_steel, 16));
		setRecipe(ModItems.turret_light_ammo, new ItemStack(Items.gunpowder, 4));
		setRecipe(ModItems.turret_heavy_ammo, new ItemStack(Items.gunpowder, 4));
		setRecipe(ModItems.turret_flamer_ammo, new ItemStack(Items.gunpowder, 4));
		setRecipe(ModItems.turret_rocket_ammo, new ItemStack(Items.gunpowder, 4));
		setRecipe(ModItems.turret_cwis_ammo, new ItemStack(Items.gunpowder, 4));
		setRecipe(ModItems.turret_tau_ammo, new ItemStack(ModItems.powder_uranium, 4));

		for(int i = 0; i < 16; i++)
		{
			setRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, i), new ItemStack(Items.clay_ball, 4));
			setRecipe(new ItemStack(Blocks.wool, 1, i), new ItemStack(Items.string, 4));
		}
	}
	
	public static ItemStack getDustByName(String name) {
		
		List<ItemStack> matches = OreDictionary.getOres("dust" + name);
		
		if(matches != null && !matches.isEmpty())
			return matches.get(0).copy();
		
		return new ItemStack(ModItems.scrap);
	}

	public static ItemStack getSmallDustByName(String name) {
		
		List<ItemStack> matches = OreDictionary.getOres("dustSmall" + name);
		
		if(matches != null && !matches.isEmpty())
			return matches.get(0).copy();
		
		return new ItemStack(ModItems.scrap);
	}

	
	public static void setRecipe(Item in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}
	
	public static void setRecipe(Block in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}
	
	public static void setRecipe(ItemStack in, ItemStack out) {
		
		shredderRecipes.put(new ComparableStack(in), out);
	}
	
	public static Map<Object, Object> getShredderRecipes() {
		
		//convert the map only once to save on processing power (might be more ram intensive but that can't be THAT bad, right?)
		if(neiShredderRecipes == null)
			neiShredderRecipes = new HashMap<Object, Object>(shredderRecipes);
		
		return neiShredderRecipes;
	}
	
	public static ItemStack getShredderResult(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return new ItemStack(ModItems.scrap);
		
		ItemStack sta = shredderRecipes.get(new ComparableStack(stack).makeSingular());
		
		/*if(sta != null)
			System.out.println(stack.getDisplayName() + " resulted " + sta.getDisplayName());
		else
			System.out.println(stack.getDisplayName() + " resulted null");*/
		
		return sta == null ? new ItemStack(ModItems.scrap) : sta;
	}
}
