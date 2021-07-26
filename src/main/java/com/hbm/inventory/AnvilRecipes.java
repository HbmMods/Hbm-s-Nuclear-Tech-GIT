package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AnvilRecipes {

	private static List<AnvilSmithingRecipe> smithingRecipes = new ArrayList();
	private static List<AnvilConstructionRecipe> constructionRecipes = new ArrayList();
	
	public static void register() {
		registerSmithing();
		registerConstruction();
	}
	
	/*
	 *      //////  //      //  //  //////  //  //  //  //    //  //////
	 *     //      ////  ////  //    //    //  //  //  ////  //  //
	 *    //////  //  //  //  //    //    //////  //  //  ////  //  //
	 *       //  //      //  //    //    //  //  //  //    //  //  //
	 *  //////  //      //  //    //    //  //  //  //    //  //////
	 */
	public static void registerSmithing() {
		
		Block[] anvils = new Block[]{ModBlocks.anvil_iron, ModBlocks.anvil_lead};
		
		for(Block anvil : anvils) {
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_bismuth, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_bismuth, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_dnt, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_dineutronium, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_ferrouranium, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_u238, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_meteorite, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_meteorite, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_schrabidate, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_schrabidate, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_starmetal, 1), new ComparableStack(anvil), new ComparableStack(ModItems.ingot_starmetal, 10)));
			smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModBlocks.anvil_steel, 1), new ComparableStack(anvil), new OreDictStack("ingotSteel", 10)));
		}
		
		for(int i = 0; i < 9; i++)
			smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_steel_dusted, 1, i + 1),
					new ComparableStack(ModItems.ingot_steel_dusted, 1, i), new ComparableStack(ModItems.ingot_steel_dusted, 1, i)));
		
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_chainsteel, 1),
				new ComparableStack(ModItems.ingot_steel_dusted, 1, 9), new ComparableStack(ModItems.ingot_steel_dusted, 1, 9)));
		
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.ingot_meteorite_forged, 1), new ComparableStack(ModItems.ingot_meteorite), new ComparableStack(ModItems.ingot_meteorite)));
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.blade_meteorite, 1), new ComparableStack(ModItems.ingot_meteorite_forged), new ComparableStack(ModItems.ingot_meteorite_forged)));
		smithingRecipes.add(new AnvilSmithingHotRecipe(3, new ItemStack(ModItems.meteorite_sword_reforged, 1), new ComparableStack(ModItems.meteorite_sword_seared), new ComparableStack(ModItems.ingot_meteorite_forged)));
		smithingRecipes.add(new AnvilSmithingRecipe(1, new ItemStack(ModItems.gun_ar15, 1), new ComparableStack(ModItems.gun_thompson), new ComparableStack(ModItems.pipe_lead)));
		smithingRecipes.add(new AnvilSmithingRecipe(1916169, new ItemStack(ModItems.wings_murk, 1), new ComparableStack(ModItems.wings_limp), new ComparableStack(ModItems.particle_tachyon)));
	}
	
	/*
	 *      //////  //////  //    //  //////  //////  ////    //  //  //////  //////  //  //////  //    //
	 *     //      //  //  ////  //  //        //    //  //  //  //  //        //    //  //  //  ////  //
	 *    //      //  //  //  ////  //////    //    ////    //  //  //        //    //  //  //  //  ////
	 *   //      //  //  //    //      //    //    //  //  //  //  //        //    //  //  //  //    //
	 *  //////  //////  //    //  //////    //    //  //  //////  //////    //    //  //////  //    //
	 */
	public static void registerConstruction() {
		registerConstructionRecipes();
		registerConstructionAmmo();
		registerConstructionRecycling();
		
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotIron"), new AnvilOutput(new ItemStack(ModItems.plate_iron))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotGold"), new AnvilOutput(new ItemStack(ModItems.plate_gold))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotCopper"), new AnvilOutput(new ItemStack(ModItems.plate_copper))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotLead"), new AnvilOutput(new ItemStack(ModItems.plate_lead))).setTier(3));
		constructionRecipes.add(new AnvilConstructionRecipe(new OreDictStack("ingotSteel"), new AnvilOutput(new ItemStack(ModItems.plate_steel))).setTier(3));
	}
	
	public static void registerConstructionRecipes() {
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new OreDictStack("plateCopper", 4)
				},
				new AnvilOutput(new ItemStack(ModItems.board_copper))).setTier(1));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new OreDictStack("plateIron", 2),
						new ComparableStack(ModItems.coil_copper),
						new ComparableStack(ModItems.coil_copper_torus)
				},
				new AnvilOutput(new ItemStack(ModItems.motor, 2))).setTier(1));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(Blocks.stonebrick, 4),
						new OreDictStack("ingotIron", 2),
						new OreDictStack("ingotTungsten", 4),
						new ComparableStack(ModItems.board_copper, 2)
				},
				new AnvilOutput(new ItemStack(ModBlocks.machine_difurnace_off))).setTier(1));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new OreDictStack("blockGlassColorless", 4),
						new OreDictStack("ingotSteel", 8),
						new OreDictStack("ingotCopper", 8),
						new ComparableStack(ModItems.motor, 2),
						new ComparableStack(ModItems.circuit_aluminium, 1)
				},
				new AnvilOutput(new ItemStack(ModBlocks.machine_assembler))).setTier(2));
		
		constructionRecipes.add(new AnvilConstructionRecipe(
				new AStack[] {
						new ComparableStack(Items.bone, 16),
						new ComparableStack(Items.leather, 4),
						new ComparableStack(Items.feather, 24)
				},
				new AnvilOutput(new ItemStack(ModItems.wings_limp))).setTier(2));
	}
	
	public static void registerConstructionAmmo() {
		
		Object[][] recs = new Object[][] {
			new Object[] {ModItems.ammo_12gauge, ModItems.powder_fire, ModItems.ammo_12gauge_incendiary, 20, 2},
			new Object[] {ModItems.ammo_12gauge, Item.getItemFromBlock(ModBlocks.gravel_obsidian), ModItems.ammo_12gauge_shrapnel, 20, 2},
			new Object[] {ModItems.ammo_12gauge, ModItems.ingot_u238, ModItems.ammo_12gauge_du, 20, 3},
			new Object[] {ModItems.ammo_12gauge, ModItems.coin_maskman, ModItems.ammo_12gauge_sleek, 100, 4},
			
			new Object[] {ModItems.ammo_20gauge, ModItems.powder_fire, ModItems.ammo_20gauge_incendiary, 20, 2},
			new Object[] {ModItems.ammo_20gauge, Item.getItemFromBlock(ModBlocks.gravel_obsidian), ModItems.ammo_20gauge_shrapnel, 20, 2},
			new Object[] {ModItems.ammo_20gauge, ModItems.powder_poison, ModItems.ammo_20gauge_caustic, 20, 2},
			new Object[] {ModItems.ammo_20gauge, "dustDiamond", ModItems.ammo_20gauge_shock, 20, 2},
			new Object[] {ModItems.ammo_20gauge, Item.getItemFromBlock(Blocks.soul_sand), ModItems.ammo_20gauge_wither, 10, 3},
			new Object[] {ModItems.ammo_20gauge, ModItems.coin_maskman, ModItems.ammo_20gauge_sleek, 100, 4},
		};
		
		for(Object[] objs : recs) {
			
			if(objs[1] instanceof Item) {
				constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] { new ComparableStack((Item)objs[0], (int)objs[3]), new ComparableStack((Item)objs[1], 1) },
						new AnvilOutput(new ItemStack((Item)objs[2], (int)objs[3]))).setTier((int)objs[4]));
				
			} else if(objs[1] instanceof String) {
				constructionRecipes.add(new AnvilConstructionRecipe(new AStack[] { new ComparableStack((Item)objs[0], (int)objs[3]), new OreDictStack((String)objs[1], 1) },
						new AnvilOutput(new ItemStack((Item)objs[2], (int)objs[3]))).setTier((int)objs[4]));
			}
		}
	}
	
	public static void registerConstructionRecycling() {
		constructionRecipes.add(new AnvilConstructionRecipe(
				new ComparableStack(ModBlocks.barrel_tcalloy),
				new AnvilOutput[] {
						new AnvilOutput(new ItemStack(ModItems.ingot_titanium, 2)),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 4)),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 1), 0.50F),
						new AnvilOutput(new ItemStack(ModItems.ingot_tcalloy, 1), 0.25F)
				}
		).setTier(3));
	}
	
	public static List<AnvilSmithingRecipe> getSmithing() {
		return smithingRecipes;
	}
	
	public static List<AnvilConstructionRecipe> getConstruction() {
		return constructionRecipes;
	}
	
	public static class AnvilConstructionRecipe {
		public List<AStack> input = new ArrayList();
		public List<AnvilOutput> output = new ArrayList();
		public int tierLower = 0;
		public int tierUpper = -1;
		OverlayType overlay = OverlayType.NONE;
		
		public AnvilConstructionRecipe(AStack input, AnvilOutput output) {
			this.input.add(input);
			this.output.add(output);
			this.setOverlay(OverlayType.SMITHING); //preferred overlay for 1:1 conversions is smithing
		}
		
		public AnvilConstructionRecipe(AStack[] input, AnvilOutput output) {
			for(AStack stack : input) this.input.add(stack);
			this.output.add(output);
			this.setOverlay(OverlayType.CONSTRUCTION); //preferred overlay for many:1 conversions is construction
		}
		
		public AnvilConstructionRecipe(AStack input, AnvilOutput[] output) {
			this.input.add(input);
			for(AnvilOutput out : output) this.output.add(out);
			this.setOverlay(OverlayType.RECYCLING); //preferred overlay for 1:many conversions is recycling
		}
		
		public AnvilConstructionRecipe(AStack[] input, AnvilOutput[] output) {
			for(AStack stack : input) this.input.add(stack);
			for(AnvilOutput out : output) this.output.add(out);
			this.setOverlay(OverlayType.NONE); //no preferred overlay for many:many conversions
		}
		
		public AnvilConstructionRecipe setTier(int tier) {
			this.tierLower = tier;
			return this;
		}
		
		public AnvilConstructionRecipe setTierRange(int lower, int upper) {
			this.tierLower = lower;
			this.tierUpper = upper;
			return this;
		}
		
		public boolean isTierValid(int tier) {
			if(this.tierUpper == -1)
				return tier >= this.tierLower;
			
			return tier >= this.tierLower && tier <= this.tierUpper;
		}
		
		public AnvilConstructionRecipe setOverlay(OverlayType overlay) {
			this.overlay = overlay;
			return this;
		}
		
		public OverlayType getOverlay() {
			return this.overlay;
		}
		
		public ItemStack getDisplay() {
			switch(this.overlay) {
			case NONE: return this.output.get(0).stack.copy();
			case CONSTRUCTION: return this.output.get(0).stack.copy();
			case SMITHING: return this.output.get(0).stack.copy();
			case RECYCLING:
				for(AStack stack : this.input) {
					if(stack instanceof ComparableStack)
						return ((ComparableStack)stack).toStack();
				}
				return this.output.get(0).stack.copy();
			default: return new ItemStack(Items.iron_pickaxe);
			}
		}
	}
	
	public static class AnvilOutput {
		public ItemStack stack;
		public float chance;
		
		public AnvilOutput(ItemStack stack) {
			this(stack, 1F);
		}
		
		public AnvilOutput(ItemStack stack, float chance) {
			this.stack = stack;
			this.chance = chance;
		}
	}
	
	public static enum OverlayType {
		NONE,
		CONSTRUCTION,
		RECYCLING,
		SMITHING;
	}
}
