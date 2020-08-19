package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockJungleCrate extends Block {

	public BlockJungleCrate(Material material) {
		super(material);
	}
	
	Random rand = new Random();
	
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
    	
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        ret.add(new ItemStack(ModItems.cap_nuka, 12 + rand.nextInt(21)));
        ret.add(new ItemStack(ModItems.syringe_metal_stimpak, 1 + rand.nextInt(3)));
        
        return ret;
    }

}
