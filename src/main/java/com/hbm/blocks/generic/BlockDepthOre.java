package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.item.Item;

public class BlockDepthOre extends BlockDepth {
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		
		if(this == ModBlocks.cluster_depth_iron) {
			return ModItems.crystal_iron;
		}
		if(this == ModBlocks.cluster_depth_titanium) {
			return ModItems.crystal_titanium;
		}
		if(this == ModBlocks.cluster_depth_tungsten) {
			return ModItems.crystal_tungsten;
		}
		if(this == ModBlocks.ore_depth_cinnebar) {
			return ModItems.cinnebar;
		}
		if(this == ModBlocks.ore_depth_zirconium) {
			return ModItems.nugget_zirconium;
		}
		if(this == ModBlocks.ore_depth_nether_neodymium) {
			return ModItems.fragment_neodymium;
		}
		if(this == ModBlocks.ore_depth_borax) {
			return ModItems.powder_borax;
		}
		if(this == ModBlocks.ore_alexandrite) {
			return ModItems.gem_alexandrite;
		}
		
		return super.getItemDropped(metadata, rand, fortune);
	}
	
	@Override
	public int quantityDropped(Random rand) {
		
		if(this == ModBlocks.ore_depth_cinnebar) {
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_depth_zirconium) {
			return 2 + rand.nextInt(2);
		}
		if(this == ModBlocks.ore_depth_nether_neodymium) {
			return 2 + rand.nextInt(2);
		}
		
		return super.quantityDropped(rand);
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random rand) {
		
		int mult = rand.nextInt(fortune + 2) - 1;

		if(mult < 0) {
			mult = 0;
		}

		return this.quantityDropped(rand) * (mult + 1);
	}
}
