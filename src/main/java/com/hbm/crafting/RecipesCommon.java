package com.hbm.crafting;

import com.hbm.items.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Wrappers for common recipe schemes
 * @author hbm
 */
public class RecipesCommon {

	//Decompress one item into nine
	public static void add1To9(Block one, Item nine) {
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
	}

	public static void add1To9(Item one, Item nine) {
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
	}

	public static void add1To9SameMeta(Item one, Item nine, int meta) {
		add1To9(new ItemStack(one, 1, meta), new ItemStack(nine, 9, meta));
	}

	public static void add1To9(ItemStack one, ItemStack nine) {
		GameRegistry.addRecipe(nine, new Object[] { "#", '#', one });
	}

	//Compress nine items into one
	public static void add9To1(Item nine, Block one) {
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add9To1(Item nine, Item one) {
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add9To1SameMeta(Item nine, Item one, int meta) {
		add9To1(new ItemStack(nine, 1, meta), new ItemStack(one, 1, meta));
	}

	public static void add9To1(ItemStack nine, ItemStack one) {
		GameRegistry.addRecipe(one, new Object[] { "###", "###", "###", '#', nine });
	}

	public static void addBillet(Item billet, Item nugget, String... ore) {
		
		for(String o : ore)
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(billet), new Object[] { "###", "###", '#', o }));
		
		GameRegistry.addShapelessRecipe(new ItemStack(nugget, 6), new Object[] { billet });
	}

	public static void addBillet(Item billet, Item nugget) {
		GameRegistry.addRecipe(new ItemStack(billet), new Object[] { "###", "###", '#', nugget });
		GameRegistry.addShapelessRecipe(new ItemStack(nugget, 6), new Object[] { billet });
	}
	
	//Fill rods with 6 nuggets
	public static void addRod(Item nugget, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_empty, nugget, nugget, nugget, nugget, nugget, nugget });
	}
	
	//Fill rods with 12 nuggets
	public static void addDualRod(Item ingot, Item nugget, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, ingot, nugget, nugget, nugget });
	}
	
	//Fill rods with 24 nuggets
	public static void addQuadRod(Item ingot, Item nugget, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, ingot, ingot, nugget, nugget, nugget, nugget, nugget, nugget });
	}
	
	//Fill rods with one billet
	public static void addRodBillet(Item billet, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_empty, billet });
	}
	
	//Fill rods with two billets
	public static void addDualRodBillet(Item billet, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, billet, billet });
	}
	
	//Fill rods with three billets
	public static void addQuadRodBillet(Item billet, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, billet, billet, billet, billet });
	}
	
	//Fill rods with one billet + unload
	public static void addRodBilletUnload(Item billet, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_empty, billet });
		GameRegistry.addShapelessRecipe(new ItemStack(billet, 1), new Object[] { out });
	}
	
	//Fill rods with two billets + unload
	public static void addDualRodBilletUnload(Item billet, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_dual_empty, billet, billet });
		GameRegistry.addShapelessRecipe(new ItemStack(billet, 2), new Object[] { out });
	}
	
	//Fill rods with three billets + unload
	public static void addQuadRodBilletUnload(Item billet, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rod_quad_empty, billet, billet, billet, billet });
		GameRegistry.addShapelessRecipe(new ItemStack(billet, 4), new Object[] { out });
	}
	
	//Fill rods with 6 nuggets
	public static void addRBMKRod(Item billet, Item out) {
		GameRegistry.addShapelessRecipe(new ItemStack(out), new Object[] { ModItems.rbmk_fuel_empty, billet, billet, billet, billet, billet, billet, billet, billet });
	}

	//Sword
	public static void addSword(Item ingot, Item sword) {
		GameRegistry.addRecipe(new ItemStack(sword), new Object[] { "I", "I", "S", 'I', ingot, 'S', Items.stick });
	}

	//Pickaxe
	public static void addPickaxe(Item ingot, Item pick) {
		GameRegistry.addRecipe(new ItemStack(pick), new Object[] { "III", " S ", " S ", 'I', ingot, 'S', Items.stick });
	}

	//Axe
	public static void addAxe(Item ingot, Item axe) {
		GameRegistry.addRecipe(new ItemStack(axe), new Object[] { "II", "IS", " S", 'I', ingot, 'S', Items.stick });
	}

	//Shovel
	public static void addShovel(Item ingot, Item shovel) {
		GameRegistry.addRecipe(new ItemStack(shovel), new Object[] { "I", "S", "S", 'I', ingot, 'S', Items.stick });
	}

	//Hoe
	public static void addHoe(Item ingot, Item hoe) {
		GameRegistry.addRecipe(new ItemStack(hoe), new Object[] { "II", " S", " S", 'I', ingot, 'S', Items.stick });
	}
}
