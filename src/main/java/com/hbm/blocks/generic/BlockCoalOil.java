package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCoalOil extends Block {

	public BlockCoalOil(Material mat) {
		super(mat);
	}
	
    @Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
        
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
        	
        	Block n = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
        	
        	if(n == ModBlocks.ore_coal_oil_burning || n == ModBlocks.balefire || n == Blocks.fire || n.getMaterial() == Material.lava) {
        		world.scheduleBlockUpdate(x, y, z, this, world.rand.nextInt(20) + 2);
        	}
        }
    }
	
    @Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
    	world.setBlock(x, y, z, ModBlocks.ore_coal_oil_burning);
    }

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return Items.coal;
    }
    
    @Override
	public int quantityDropped(Random rand) {
    	return 2 + rand.nextInt(2);
    }
}
