package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockJungleCrate extends Block {

	public BlockJungleCrate(Material material) {
		super(material);
	}

	Random rand = new Random();

	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {

		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		ret.add(new ItemStack(Items.gold_ingot, 4 + rand.nextInt(4)));
		ret.add(new ItemStack(Items.gold_nugget, 8 + rand.nextInt(10)));
		ret.add(new ItemStack(ModItems.powder_gold, 2 + rand.nextInt(3)));
		ret.add(new ItemStack(ModItems.wire_fine, 4 + rand.nextInt(5), Mats.MAT_GOLD.id));
		ret.add(new ItemStack(ModItems.wire_dense, 1 + rand.nextInt(2), Mats.MAT_GOLD.id));

		if(rand.nextInt(2) == 0)
			ret.add(new ItemStack(ModItems.plate_gold, 1 + rand.nextInt(2)));

		if(rand.nextInt(3) == 0)
			ret.add(new ItemStack(ModItems.crystal_gold));

		return ret;
	}

}
