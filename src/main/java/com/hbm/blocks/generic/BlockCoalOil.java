package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.tool.ItemToolAbility;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.Explosion;
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
	
    /*public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
    	
		if(world.rand.nextInt(10) == 0)
			world.setBlock(x, y, z, Blocks.fire);
    }*/

    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
    	
    	if(player.getHeldItem() == null)
    		return;
    	
    	if(!(player.getHeldItem().getItem() instanceof ItemTool || player.getHeldItem().getItem() instanceof ItemToolAbility))
    		return;
    	
    	ItemTool tool = (ItemTool)player.getHeldItem().getItem();
    	
    	if(tool.func_150913_i() != ToolMaterial.WOOD) {

    		if(world.rand.nextInt(10) == 0)
    			world.setBlock(x, y, z, Blocks.fire);
    	}
    }
    
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		world.setBlock(x, y, z, Blocks.fire);
    }
}
