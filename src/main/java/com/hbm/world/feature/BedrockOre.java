package com.hbm.world.feature;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.inventory.FluidStack;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BedrockOre {

	public static void generate(World world, int x, int z, ItemStack stack, FluidStack acid, int color, int tier) {
		
		for(int ix = x - 1; ix <= x + 1; ix++) {
			for(int iz = z - 1; iz <= z + 1; iz++) {
				
				if((ix == x && iz == z) || world.rand.nextBoolean()) {
					
					world.setBlock(ix, 0, iz, ModBlocks.ore_bedrock);
					TileEntityBedrockOre ore = (TileEntityBedrockOre) world.getTileEntity(ix, 0, iz);
					ore.resource = stack;
					ore.color = color;
					ore.shape = world.rand.nextInt(10);
					ore.acidRequirement = acid;
					ore.tier = tier;
					world.markBlockForUpdate(ix, 0, iz);
					world.markTileEntityChunkModified(ix, 0, iz, ore);
				}
			}
		}
		
		for(int ix = x - 3; ix <= x + 3; ix++) {
			for(int iz = z - 3; iz <= z + 3; iz++) {
				
				for(int iy = 1; iy < 7; iy++) {
					if(iy < 3 || world.getBlock(ix, iy, iz) == Blocks.bedrock) {
						world.setBlock(ix, iy, iz, ModBlocks.stone_depth);
					}
				}
			}
		}
	}
}
