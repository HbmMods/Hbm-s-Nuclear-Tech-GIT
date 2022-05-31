package com.hbm.inventory.recipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ShredderRecipes {

	public static HashMap<ComparableStack, ItemStack> shredderRecipes = new HashMap();
	public static HashMap<Object, Object> neiShredderRecipes;
	
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

			//1 ingot unit, metal
			generateRecipes("ingot", name, matches, 1);
			generateRecipes("plate", name, matches, 1);
			//1 ingot unit, crystalline
			generateRecipes("gem", name, matches, 1);
			generateRecipes("crystal", name, matches, 1);
			//2 ingot units, any
			generateRecipes("ore", name, matches, 2);
			
			if(name.length() > 5 && name.substring(0, 5).equals("block")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.stackSize = 9;
					
					if(getIngotOrGemByName(name.substring(5)) == null)
						dust.stackSize = 4;
					
					for(ItemStack stack : matches) {
						putIfValid(stack, dust, name);
					}
				}
			}
			
			if(name.length() > 3 && name.substring(0, 4).equals("dust")) {
				for(ItemStack stack : matches) {
					putIfValid(stack, new ItemStack(ModItems.dust), name);
				}
			}
		}
	}
	
	@Untested
	private static void generateRecipes(String prefix, String name, List<ItemStack> matches, int outCount) {
		
		int len = prefix.length();
		
		if(name.length() > len && name.substring(0, len).equals(prefix)) {
			
			String matName = name.substring(len);
			
			//skip over genericized names so we don't accidentally convert item groups
			if(matName.startsWith("Any"))
				return;
			
			ItemStack dust = getDustByName(matName);
			
			if(dust != null && dust.getItem() != ModItems.scrap) {
				
				dust.stackSize = outCount;
				
				for(ItemStack stack : matches) {
					putIfValid(stack, dust, name);
				}
			}
		}
	}
	
	private static void putIfValid(ItemStack in, ItemStack dust, String name) {

		if(in != null) {
			
			if(in.getItem() != null) {
				shredderRecipes.put(new ComparableStack(in), dust);
			} else {
				MainRegistry.logger.error("Ore dict entry '" + name + "' has a null item in its stack! How does that even happen?");
				Thread.currentThread().dumpStack();
			}
			
		} else {
			MainRegistry.logger.error("Ore dict entry '" + name + "' has a null stack!");
			Thread.currentThread().dumpStack();
		}
	}
	
	public static void registerOverrides() {

		/*
		 * Primary recipes
		 */
		ShredderRecipes.setRecipe(ModItems.scrap, new ItemStack(ModItems.dust));
		ShredderRecipes.setRecipe(ModItems.dust, new ItemStack(ModItems.dust));
		ShredderRecipes.setRecipe(Blocks.glowstone, new ItemStack(Items.glowstone_dust, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.quartz_block, 1, 0), new ItemStack(ModItems.powder_quartz, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.quartz_block, 1, 1), new ItemStack(ModItems.powder_quartz, 4));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.quartz_block, 1, 2), new ItemStack(ModItems.powder_quartz, 4));
		ShredderRecipes.setRecipe(Blocks.quartz_stairs, new ItemStack(ModItems.powder_quartz, 3));
		ShredderRecipes.setRecipe(new ItemStack(Blocks.stone_slab, 1, 7), new ItemStack(ModItems.powder_quartz, 2));
		ShredderRecipes.setRecipe(Items.quartz, new ItemStack(ModItems.powder_quartz));
		ShredderRecipes.setRecipe(Blocks.quartz_ore, new ItemStack(ModItems.powder_quartz, 2));
		ShredderRecipes.setRecipe(ModBlocks.ore_nether_fire, new ItemStack(ModItems.powder_fire, 6));
		ShredderRecipes.setRecipe(Blocks.packed_ice, new ItemStack(ModItems.powder_ice, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_light, new ItemStack(Items.clay_ball, 4));
		ShredderRecipes.setRecipe(ModBlocks.concrete, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(ModBlocks.concrete_smooth, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete_mossy, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete_cracked, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_concrete_broken, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(ModBlocks.brick_obsidian, new ItemStack(ModBlocks.gravel_obsidian, 1));
		ShredderRecipes.setRecipe(Blocks.obsidian, new ItemStack(ModBlocks.gravel_obsidian, 1));
		ShredderRecipes.setRecipe(Blocks.stone, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(ModBlocks.ore_oil_empty, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(Blocks.cobblestone, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(Blocks.stonebrick, new ItemStack(Blocks.gravel, 1));
		ShredderRecipes.setRecipe(Blocks.gravel, new ItemStack(Blocks.sand, 1));
		ShredderRecipes.setRecipe(Blocks.sand, new ItemStack(ModItems.dust, 2));
		ShredderRecipes.setRecipe(Blocks.brick_block, new ItemStack(Items.clay_ball, 4));
		ShredderRecipes.setRecipe(Blocks.brick_stairs, new ItemStack(Items.clay_ball, 3));
		ShredderRecipes.setRecipe(Items.flower_pot, new ItemStack(Items.clay_ball, 3));
		ShredderRecipes.setRecipe(Items.brick, new ItemStack(Items.clay_ball, 1));
		ShredderRecipes.setRecipe(Blocks.sandstone, new ItemStack(Blocks.sand, 4));
		ShredderRecipes.setRecipe(Blocks.sandstone_stairs, new ItemStack(Blocks.sand, 6));
		ShredderRecipes.setRecipe(Blocks.clay, new ItemStack(Items.clay_ball, 4));
		ShredderRecipes.setRecipe(Blocks.hardened_clay, new ItemStack(Items.clay_ball, 4));
		ShredderRecipes.setRecipe(Blocks.tnt, new ItemStack(Items.gunpowder, 5));
		ShredderRecipes.setRecipe(ModBlocks.stone_gneiss, new ItemStack(ModItems.powder_lithium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.powder_lapis, new ItemStack(ModItems.powder_cobalt_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_neodymium, new ItemStack(ModItems.powder_neodymium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_cobalt, new ItemStack(ModItems.powder_cobalt_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_niobium, new ItemStack(ModItems.powder_niobium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_cerium, new ItemStack(ModItems.powder_cerium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_lanthanium, new ItemStack(ModItems.powder_lanthanium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_actinium, new ItemStack(ModItems.powder_actinium_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_boron, new ItemStack(ModItems.powder_boron_tiny, 1));
		ShredderRecipes.setRecipe(ModItems.fragment_meteorite, new ItemStack(ModItems.powder_meteorite_tiny, 1));
		ShredderRecipes.setRecipe(ModBlocks.block_meteor, new ItemStack(ModItems.powder_meteorite, 10));
		ShredderRecipes.setRecipe(Items.enchanted_book, new ItemStack(ModItems.powder_magic, 1));
		ShredderRecipes.setRecipe(ModItems.arc_electrode_burnt, new ItemStack(ModItems.powder_coal, 1));
		ShredderRecipes.setRecipe(ModItems.arc_electrode_desh, new ItemStack(ModItems.powder_desh, 2));
		ShredderRecipes.setRecipe(ModBlocks.meteor_polished, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_mossy, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_cracked, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_brick_chiseled, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.meteor_pillar, new ItemStack(ModItems.powder_meteorite, 1));
		ShredderRecipes.setRecipe(ModBlocks.ore_rare, new ItemStack(ModItems.powder_desh_mix, 1));
		ShredderRecipes.setRecipe(Blocks.diamond_ore, new ItemStack(ModBlocks.gravel_diamond, 2));
		ShredderRecipes.setRecipe(ModBlocks.boxcar, new ItemStack(ModItems.powder_steel, 32));
		ShredderRecipes.setRecipe(ModItems.ingot_schrabidate, new ItemStack(ModItems.powder_schrabidate, 1));
		ShredderRecipes.setRecipe(ModBlocks.block_schrabidate, new ItemStack(ModItems.powder_schrabidate, 9));
		ShredderRecipes.setRecipe(ModItems.coal_infernal, new ItemStack(ModItems.powder_coal, 3));
		ShredderRecipes.setRecipe(Items.reeds, new ItemStack(Items.paper, 3));
		ShredderRecipes.setRecipe(Items.fermented_spider_eye, new ItemStack(ModItems.powder_poison, 3));
		ShredderRecipes.setRecipe(Items.poisonous_potato, new ItemStack(ModItems.powder_poison, 1));
		ShredderRecipes.setRecipe(ModBlocks.ore_tektite_osmiridium, new ItemStack(ModItems.powder_tektite, 1));
		
		for(int i = 0; i < 5; i++) ShredderRecipes.setRecipe(new ItemStack(Items.skull, 1, i), new ItemStack(ModItems.biomass));

		/*
		 * Crstaly processing
		 */
		ShredderRecipes.setRecipe(ModItems.ingot_schraranium, new ItemStack(ModItems.nugget_schrabidium, 2));
		ShredderRecipes.setRecipe(ModItems.crystal_coal, new ItemStack(ModItems.powder_coal, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_iron, new ItemStack(ModItems.powder_iron, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_gold, new ItemStack(ModItems.powder_gold, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_redstone, new ItemStack(Items.redstone, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_lapis, new ItemStack(ModItems.powder_lapis, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_diamond, new ItemStack(ModItems.powder_diamond, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_uranium, new ItemStack(ModItems.powder_uranium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_plutonium, new ItemStack(ModItems.powder_plutonium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_thorium, new ItemStack(ModItems.powder_thorium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_titanium, new ItemStack(ModItems.powder_titanium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_sulfur, new ItemStack(ModItems.sulfur, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_niter, new ItemStack(ModItems.niter, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_copper, new ItemStack(ModItems.powder_copper, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_tungsten, new ItemStack(ModItems.powder_tungsten, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_aluminium, new ItemStack(ModItems.powder_aluminium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_fluorite, new ItemStack(ModItems.fluorite, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_beryllium, new ItemStack(ModItems.powder_beryllium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_lead, new ItemStack(ModItems.powder_lead, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_schraranium, new ItemStack(ModItems.nugget_schrabidium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_schrabidium, new ItemStack(ModItems.powder_schrabidium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_rare, new ItemStack(ModItems.powder_desh_mix, 2));
		ShredderRecipes.setRecipe(ModItems.crystal_phosphorus, new ItemStack(ModItems.powder_fire, 8));
		ShredderRecipes.setRecipe(ModItems.crystal_trixite, new ItemStack(ModItems.powder_plutonium, 6));
		ShredderRecipes.setRecipe(ModItems.crystal_lithium, new ItemStack(ModItems.powder_lithium, 3));
		ShredderRecipes.setRecipe(ModItems.crystal_starmetal, new ItemStack(ModItems.powder_dura_steel, 6));
		ShredderRecipes.setRecipe(ModItems.crystal_cobalt, new ItemStack(ModItems.powder_cobalt, 3));

		/*
		 * Misc recycling
		 */
		ShredderRecipes.setRecipe(ModBlocks.steel_poles, new ItemStack(ModItems.powder_steel_tiny, 3));
		ShredderRecipes.setRecipe(ModBlocks.pole_top, new ItemStack(ModItems.powder_tungsten, 4));
		ShredderRecipes.setRecipe(ModBlocks.tape_recorder, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.pole_satellite_receiver, new ItemStack(ModItems.powder_steel, 5));
		ShredderRecipes.setRecipe(ModBlocks.steel_roof, new ItemStack(ModItems.powder_steel_tiny, 13));
		ShredderRecipes.setRecipe(ModBlocks.steel_wall, new ItemStack(ModItems.powder_steel_tiny, 13));
		ShredderRecipes.setRecipe(ModBlocks.steel_corner, new ItemStack(ModItems.powder_steel_tiny, 26));
		ShredderRecipes.setRecipe(ModBlocks.steel_beam, new ItemStack(ModItems.powder_steel_tiny, 3));
		ShredderRecipes.setRecipe(ModBlocks.steel_scaffold, new ItemStack(ModItems.powder_steel_tiny, 7));
		ShredderRecipes.setRecipe(ModItems.coil_copper, new ItemStack(ModItems.powder_red_copper, 1));
		ShredderRecipes.setRecipe(ModItems.coil_copper_torus, new ItemStack(ModItems.powder_red_copper, 2));
		ShredderRecipes.setRecipe(ModItems.coil_advanced_alloy, new ItemStack(ModItems.powder_advanced_alloy, 1));
		ShredderRecipes.setRecipe(ModItems.coil_advanced_torus, new ItemStack(ModItems.powder_advanced_alloy, 2));
		ShredderRecipes.setRecipe(ModItems.coil_gold, new ItemStack(ModItems.powder_gold, 1));
		ShredderRecipes.setRecipe(ModItems.coil_gold_torus, new ItemStack(ModItems.powder_gold, 2));
		ShredderRecipes.setRecipe(ModItems.coil_tungsten, new ItemStack(ModItems.powder_tungsten, 1));
		ShredderRecipes.setRecipe(ModItems.coil_magnetized_tungsten, new ItemStack(ModItems.powder_magnetized_tungsten, 1));
		ShredderRecipes.setRecipe(ModBlocks.crate_iron, new ItemStack(ModItems.powder_iron, 8));
		ShredderRecipes.setRecipe(ModBlocks.crate_steel, new ItemStack(ModItems.powder_steel, 8));
		ShredderRecipes.setRecipe(ModBlocks.crate_tungsten, new ItemStack(ModItems.powder_tungsten, 36));
		ShredderRecipes.setRecipe(Blocks.anvil, new ItemStack(ModItems.powder_iron, 31));
		ShredderRecipes.setRecipe(ModBlocks.chain, new ItemStack(ModItems.powder_steel_tiny, 1));
		ShredderRecipes.setRecipe(ModBlocks.steel_grate, new ItemStack(ModItems.powder_steel_tiny, 3));
		ShredderRecipes.setRecipe(ModItems.pipes_steel, new ItemStack(ModItems.powder_steel, 27));

		/*
		 * Sellafite scrapping
		 */
		ShredderRecipes.setRecipe(ModBlocks.sellafield_0, new ItemStack(ModItems.scrap_nuclear, 1));
		ShredderRecipes.setRecipe(ModBlocks.sellafield_1, new ItemStack(ModItems.scrap_nuclear, 2));
		ShredderRecipes.setRecipe(ModBlocks.sellafield_2, new ItemStack(ModItems.scrap_nuclear, 3));
		ShredderRecipes.setRecipe(ModBlocks.sellafield_3, new ItemStack(ModItems.scrap_nuclear, 5));
		ShredderRecipes.setRecipe(ModBlocks.sellafield_4, new ItemStack(ModItems.scrap_nuclear, 7));
		ShredderRecipes.setRecipe(ModBlocks.sellafield_core, new ItemStack(ModItems.scrap_nuclear, 15));
		
		/*
		 * Fracking debris scrapping
		 */
		ShredderRecipes.setRecipe(ModBlocks.dirt_dead, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.dirt_oily, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.sand_dirty, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.sand_dirty_red, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.stone_cracked, new ItemStack(ModItems.scrap_oil, 1));
		ShredderRecipes.setRecipe(ModBlocks.stone_porous, new ItemStack(ModItems.scrap_oil, 1));

		/*
		 * Deco pipe recycling
		 */
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rusted, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_green, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_green_rusted, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_red, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_marked, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_rusted, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_green, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_green_rusted, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_red, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_rim_marked, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_rusted, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_green, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_green_rusted, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_red, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_quad_marked, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_rusted, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_green, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_green_rusted, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_red, new ItemStack(ModItems.powder_steel, 1));
		ShredderRecipes.setRecipe(ModBlocks.deco_pipe_framed_marked, new ItemStack(ModItems.powder_steel, 1));

		/*
		 * Turret and ammo recycling
		 */
		ShredderRecipes.setRecipe(ModBlocks.turret_light, new ItemStack(ModItems.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_heavy, new ItemStack(ModItems.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_flamer, new ItemStack(ModItems.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_rocket, new ItemStack(ModItems.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_cwis, new ItemStack(ModItems.powder_steel, 16));
		ShredderRecipes.setRecipe(ModBlocks.turret_tau, new ItemStack(ModItems.powder_steel, 16));
		ShredderRecipes.setRecipe(ModItems.turret_light_ammo, new ItemStack(Items.gunpowder, 4));
		ShredderRecipes.setRecipe(ModItems.turret_heavy_ammo, new ItemStack(Items.gunpowder, 4));
		ShredderRecipes.setRecipe(ModItems.turret_flamer_ammo, new ItemStack(Items.gunpowder, 4));
		ShredderRecipes.setRecipe(ModItems.turret_rocket_ammo, new ItemStack(Items.gunpowder, 4));
		ShredderRecipes.setRecipe(ModItems.turret_cwis_ammo, new ItemStack(Items.gunpowder, 4));
		ShredderRecipes.setRecipe(ModItems.turret_tau_ammo, new ItemStack(ModItems.powder_uranium, 4));

		/*
		 * Wool and clay scrapping
		 */
		for(int i = 0; i < 16; i++) {
			ShredderRecipes.setRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, i), new ItemStack(Items.clay_ball, 4));
			ShredderRecipes.setRecipe(new ItemStack(Blocks.wool, 1, i), new ItemStack(Items.string, 4));
		}
		
		/*
		 * Shredding bobbleheads
		 */
		for(int i = 0; i < BobbleType.values().length; i++) {
			BobbleType type = BobbleType.values()[i];
			ShredderRecipes.setRecipe(new ItemStack(ModBlocks.bobblehead, 1, i), new ItemStack(ModItems.scrap_plastic, 1, type.scrap.ordinal()));
		}
		
		/*
		 * Debris shredding
		 */
		ShredderRecipes.setRecipe(ModItems.debris_concrete, new ItemStack(ModItems.scrap_nuclear, 2));
		ShredderRecipes.setRecipe(ModItems.debris_shrapnel, new ItemStack(ModItems.powder_steel_tiny, 5));
		ShredderRecipes.setRecipe(ModItems.debris_exchanger, new ItemStack(ModItems.powder_steel, 3));
		ShredderRecipes.setRecipe(ModItems.debris_element, new ItemStack(ModItems.scrap_nuclear, 4));
		ShredderRecipes.setRecipe(ModItems.debris_metal, new ItemStack(ModItems.powder_steel_tiny, 3));
		
		/*
		 * GC COMPAT
		 */
		Item gcMoonBlock = Compat.tryLoadItem(Compat.MOD_GCC, "moonBlock");
		if(gcMoonBlock != null) {
			ShredderRecipes.setRecipe(new ItemStack(gcMoonBlock, 1, 3), new ItemStack(ModBlocks.moon_turf)); //Moon dirt
			ShredderRecipes.setRecipe(new ItemStack(gcMoonBlock, 1, 5), new ItemStack(ModBlocks.moon_turf)); //Moon topsoil
		}
		
		/*
		 * AR COMPAT
		 */
		Item arMoonTurf = Compat.tryLoadItem(Compat.MOD_AR, "turf");
		if(arMoonTurf != null) ShredderRecipes.setRecipe(arMoonTurf, new ItemStack(ModBlocks.moon_turf)); //i assume it's moon turf
		Item arMoonTurfDark = Compat.tryLoadItem(Compat.MOD_AR, "turfDark");
		if(arMoonTurfDark != null) ShredderRecipes.setRecipe(arMoonTurfDark, new ItemStack(ModBlocks.moon_turf)); //probably moon dirt? would have helped if i had ever played AR for more than 5 seconds
	}
	
	/**
	 * Returns scrap when no dust is found, for quickly adding recipes
	 * @param name
	 * @return
	 */
	public static ItemStack getDustByName(String name) {
		
		List<ItemStack> matches = OreDictionary.getOres("dust" + name);
		
		if(matches != null && !matches.isEmpty())
			return matches.get(0).copy();
		
		return new ItemStack(ModItems.scrap);
	}
	
	/**
	 * Returns null when no ingot or gem is found, for deciding whether the block shredding output should be 9 or 4 dusts
	 * @param name
	 * @return
	 */
	public static ItemStack getIngotOrGemByName(String name) {
		
		List<ItemStack> matches = OreDictionary.getOres("ingot" + name);
		
		if(matches != null && !matches.isEmpty())
			return matches.get(0).copy();
		
		matches = OreDictionary.getOres("gem" + name);
		
		if(matches != null && !matches.isEmpty())
			return matches.get(0).copy();
		
		return null;
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
			neiShredderRecipes = new HashMap(shredderRecipes);
		
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
