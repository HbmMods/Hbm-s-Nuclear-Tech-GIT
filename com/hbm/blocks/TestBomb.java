package com.hbm.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TestBomb extends Block {
	
	int bombStartStrength = 40;
	int bombStrengthA = 10;
	protected int timer1 = 20;
	int timer2 = timer1;

	protected TestBomb(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
            this.onBlockDestroyedByPlayer(world, x, y, z, 1);
            world.setBlockToAir(x, y, z);
            soos(world, x, y, z, bombStartStrength, bombStrengthA);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
            this.onBlockDestroyedByPlayer(p_149695_1_, x, y, z, 1);
            p_149695_1_.setBlockToAir(x, y, z);
            soos(p_149695_1_, x, y, z, bombStartStrength, bombStrengthA);
        }
    }
	
	public boolean soos(World world, int x, int y, int z, int bombStartStrength, int bombStrengthA)
	{
		if (!world.isRemote)
		explode(world,x,y,z, bombStartStrength, bombStrengthA);
		world.spawnParticle("hugeexplosion", x, y, z, 0, 0, 0); //spawns a huge explosion particle
		world.playSoundEffect(x, y, z, "random.explode", 1.0f, world.rand.nextFloat() * 0.1F + 0.9F); //x,y,z,sound,volume,pitch
		return false;
	}
	
	public void explode(World world, int x, int y, int z, int bombStartStrength, int bombStrengthA)
	{
		int r = bombStartStrength; //radius of explosion (change this to bigger numbers for more epicness)
		int r2 = r*r; //radius^2, for faster distance checks. (No sqrt needed for pythagoras)
		int r22 = r2/2; //half of r^2, calculations outside the loop only get called once. Always pull out as many things from the loop as possible.
		for (int xx = -r; xx < r; xx++)
		{
			int X = xx+x; //x coordinate we are working on
			int XX = xx*xx; //more stuff for a faster distance check
			for (int yy = -r; yy < r; yy++)
			{
				int Y = yy+y; //y coord
				//int YY = XX+yy*yy*3;
				int YY = XX+yy*yy*3;
				for (int zz = -r; zz < r; zz++)
				{
					int Z = zz+z; //z coord
					int ZZ = YY+zz*zz; //final= x*x+y*y+z*z. remind you of anything?
					if (ZZ<r22+world.rand.nextInt(r22)) //and the distance check. x*x+y*y+z*z < radius^2 is the same as sqrt(x*x+y*y+z*z) < radius, the distance formula. Here we use a random number between r^2 and r^2/2 for a jagged explosion.
					{ //but since sqrt is slow we optimize the algorithm for super fast explosions! Yay!
						//faster explosions means more explosions per second! 
						world.setBlock(X, Y, Z, Blocks.air, 0, 3); //destroy the block if its within the radius
					} //you can change the if statement to if (ZZ<r2) for a smoother explosion crater.
				}
			}
		}
	}
}
