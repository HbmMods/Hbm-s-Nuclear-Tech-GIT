package com.hbm.blocks.generic;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCoalBurning extends Block {

	public BlockCoalBurning(Material mat) {
		super(mat);
	}
    
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        super.randomDisplayTick(world, x, y, z, rand);
        
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
        	
        	if(dir == ForgeDirection.DOWN)
        		continue;
        	
        	if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.air) {

        		double ix = x + 0.5F + dir.offsetX + rand.nextDouble() - 0.5D;
        		double iy = y + 0.5F + dir.offsetY + rand.nextDouble() - 0.5D;
        		double iz = z + 0.5F + dir.offsetZ + rand.nextDouble() - 0.5D;

        		if(dir.offsetX != 0)
        			ix = x + 0.5F + dir.offsetX * 0.5 + rand.nextDouble() * 0.125 * dir.offsetX;
        		if(dir.offsetY != 0)
        			iy = y + 0.5F + dir.offsetY * 0.5 + rand.nextDouble() * 0.125 * dir.offsetY;
        		if(dir.offsetZ != 0)
        			iz = z + 0.5F + dir.offsetZ * 0.5 + rand.nextDouble() * 0.125 * dir.offsetZ;

        		world.spawnParticle("flame", ix, iy, iz, 0.0, 0.0, 0.0);
        		world.spawnParticle("smoke", ix, iy, iz, 0.0, 0.0, 0.0);
        		world.spawnParticle("smoke", ix, iy, iz, 0.0, 0.1, 0.0);
        	}
        }
    }

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
    }

	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		super.breakBlock(world, x, y, z, block, i);
		
		world.setBlock(x, y, z, Blocks.fire);
	}

    @Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
    	entity.setFire(3);
    }
}
