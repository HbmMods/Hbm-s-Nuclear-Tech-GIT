package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityNuclearCreeper;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CheaterVirus extends Block {

	static boolean protect = true;
	
	public CheaterVirus(Material p_i45394_1_) {
		super(p_i45394_1_);
		this.setTickRandomly(true);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i)
	{
		super.breakBlock(world, x, y, z, block, i);
		
		if(CheaterVirus.protect)
			world.setBlock(x, y, z, this, i, 2);
	}

    @Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
    	{
    		if(world.getBlock(x + 1, y, z) != ModBlocks.cheater_virus && world.getBlock(x + 1, y, z) != Blocks.air && world.getBlock(x + 1, y, z) != ModBlocks.cheater_virus) {
    			world.setBlock(x + 1, y, z, ModBlocks.cheater_virus);
    		}
    	
    		if(world.getBlock(x, y + 1, z) != ModBlocks.cheater_virus && world.getBlock(x, y + 1, z) != Blocks.air && world.getBlock(x, y + 1, z) != ModBlocks.cheater_virus) {
    			world.setBlock(x, y + 1, z, ModBlocks.cheater_virus);
    		}
    	
    		if(world.getBlock(x, y, z + 1) != ModBlocks.cheater_virus && world.getBlock(x, y, z + 1) != Blocks.air && world.getBlock(x, y, z + 1) != ModBlocks.cheater_virus) {
    			world.setBlock(x, y, z + 1, ModBlocks.cheater_virus);
    		}
    	
    		if(world.getBlock(x - 1, y, z) != ModBlocks.cheater_virus && world.getBlock(x - 1, y, z) != Blocks.air && world.getBlock(x - 1, y, z) != ModBlocks.cheater_virus) {
    			world.setBlock(x - 1, y, z, ModBlocks.cheater_virus);
    		}
    	
    		if(world.getBlock(x, y - 1, z) != ModBlocks.cheater_virus && world.getBlock(x, y - 1, z) != Blocks.air && world.getBlock(x, y - 1, z) != ModBlocks.cheater_virus) {
    			world.setBlock(x, y - 1, z, ModBlocks.cheater_virus);
    		}
    	
    		if(world.getBlock(x, y, z - 1) != ModBlocks.cheater_virus && world.getBlock(x, y, z - 1) != Blocks.air && world.getBlock(x, y, z - 1) != ModBlocks.cheater_virus) {
    			world.setBlock(x, y, z - 1, ModBlocks.cheater_virus);
    		}
    		
    		protect = false;
    		world.setBlock(x, y, z, Blocks.air);
    		protect = true;
    	}
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    	
    	if((world.getBlock(x + 1, y, z) == Blocks.air || world.getBlock(x + 1, y, z) == ModBlocks.cheater_virus || world.getBlock(x + 1, y, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x - 1, y, z) == Blocks.air || world.getBlock(x - 1, y, z) == ModBlocks.cheater_virus || world.getBlock(x - 1, y, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y + 1, z) == Blocks.air || world.getBlock(x, y + 1, z) == ModBlocks.cheater_virus || world.getBlock(x, y + 1, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y - 1, z) == Blocks.air || world.getBlock(x, y - 1, z) == ModBlocks.cheater_virus || world.getBlock(x, y - 1, z) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y, z + 1) == Blocks.air || world.getBlock(x, y, z + 1) == ModBlocks.cheater_virus || world.getBlock(x, y, z + 1) == ModBlocks.cheater_virus_seed) && 
    			(world.getBlock(x, y, z - 1) == Blocks.air || world.getBlock(x, y, z - 1) == ModBlocks.cheater_virus || world.getBlock(x, y, z - 1) == ModBlocks.cheater_virus_seed) && 
    			!world.isRemote) {
			protect = false;
			world.setBlock(x, y, z, Blocks.air);
			ExplosionChaos.spreadVirus(world, x, y, z, 5);
			protect = true;
    	}
    }

    @Override
	public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity entity)
    {
    	if(entity instanceof EntityLivingBase) {
    		((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 60 * 60 * 60, 9));
    	}
    }

}
