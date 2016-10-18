package com.hbm.explosion;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.EntityBullet;
import com.hbm.entity.EntityGrenadeTau;
import com.hbm.entity.EntityGrenadeZOMG;
import com.hbm.entity.EntityMirv;
import com.hbm.entity.EntityMissileBase;
import com.hbm.entity.EntityRainbow;
import com.hbm.entity.EntityRocket;
import com.hbm.entity.EntitySchrab;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ExplosionChaos {
	
    private final static Random random = new Random();
    private static Random rand = new Random();
    
    public static void explode(World world, int x, int y, int z, int bombStartStrength) {
    	
    	int r = bombStartStrength;
    	int r2 = r*r;
    	int r22 = r2/2;
    	for (int xx = -r; xx < r; xx++)
    	{
    		int X = xx+x;
    		int XX = xx*xx;
    		for (int yy = -r; yy < r; yy++)
    		{
    			int Y = yy+y;
    			int YY = XX+yy*yy;
    			for (int zz = -r; zz < r; zz++)
    			{
    				int Z = zz+z;
    				int ZZ = YY+zz*zz;
    				if (ZZ<r22)
    				{
    					destruction(world, X, Y, Z);
    				}
    			}
    		}
    	}
    }
    
    public static void explodeZOMG(World world, int x, int y, int z, int bombStartStrength) {
    	
    	int r = bombStartStrength;
    	int r2 = r*r;
    	int r22 = r2/2;
    	for (int xx = -r; xx < r; xx++)
    	{
    		int X = xx+x;
    		int XX = xx*xx;
    		for (int yy = -r; yy < r; yy++)
    		{
    			int Y = yy+y;
    			int YY = XX+yy*yy;
    			for (int zz = -r; zz < r; zz++)
    			{
    				int Z = zz+z;
    				int ZZ = YY+zz*zz;
    				if (ZZ<r22)
    				{
    					if(!(world.getBlock(X, Y, Z) == Blocks.bedrock && Y <= 0))
    						world.setBlock(X, Y, Z, Blocks.air);
    				}
    			}
    		}
    	}
    }
    
    public static void spawnExplosion(World world, int x, int y, int z, int bound) {
    	
    	int randX;
    	int randY;
    	int randZ;
    	
    	for(int i = 0; i < 25; i++)
    	{
    		
    		randX = random.nextInt(bound);
    		randY = random.nextInt(bound);
    		randZ = random.nextInt(bound);

        	world.createExplosion(null, x + randX, y + randY, z + randZ, 10.0F, true);
        	//ExplosionChaos.explode(world, x + randX, y + randY, z + randZ, 5);

    		randX = random.nextInt(bound);
    		randY = random.nextInt(bound);
    		randZ = random.nextInt(bound);

        	world.createExplosion(null, x + randX, y - randY, z + randZ, 10.0F, true);
        	//ExplosionChaos.explode(world, x - randX, y + randY, z + randZ, 5);

    		randX = random.nextInt(bound);
    		randY = random.nextInt(bound);
    		randZ = random.nextInt(bound);

        	world.createExplosion(null, x + randX, y + randY, z - randZ, 10.0F, true);
        	//ExplosionChaos.explode(world, x + randX, y - randY, z + randZ, 5);

    		randX = random.nextInt(bound);
    		randY = random.nextInt(bound);
    		randZ = random.nextInt(bound);

        	world.createExplosion(null, x - randX, y + randY, z + randZ, 10.0F, true);
        	//ExplosionChaos.explode(world, x + randX, y + randY, z - randZ, 5);
    		randX = random.nextInt(bound);
    		randY = random.nextInt(bound);
    		randZ = random.nextInt(bound);

        	world.createExplosion(null, x - randX, y - randY, z + randZ, 10.0F, true);
        	//ExplosionChaos.explode(world, x - randX, y - randY, z + randZ, 5);

    		randX = random.nextInt(bound);
    		randY = random.nextInt(bound);
    		randZ = random.nextInt(bound);

        	world.createExplosion(null, x - randX, y + randY, z - randZ, 10.0F, true);
        	//ExplosionChaos.explode(world, x - randX, y + randY, z - randZ, 5);

    		randX = random.nextInt(bound);
    		randY = random.nextInt(bound);
    		randZ = random.nextInt(bound);

        	world.createExplosion(null, x + randX, y - randY, z - randZ, 10.0F, true);
        	//ExplosionChaos.explode(world, x + randX, y - randY, z - randZ, 5);

    		randX = random.nextInt(bound);
    		randY = random.nextInt(bound);
    		randZ = random.nextInt(bound);

        	world.createExplosion(null, x - randX, y - randY, z - randZ, 10.0F, true);
        	//ExplosionChaos.explode(world, x - randX, y - randY, z - randZ, 5);
    	}
    }
    
    /**
     * Sets all flammable blocks on fire
     * @param world
     * @param x
     * @param y
     * @param z
     * @param bound
     */
    public static void flameDeath(World world, int x, int y, int z, int bound) {
    	
    	int r = bound;
    	int r2 = r*r;
    	int r22 = r2/2;
    	for (int xx = -r; xx < r; xx++)
    	{
    		int X = xx+x;
    		int XX = xx*xx;
    		for (int yy = -r; yy < r; yy++)
    		{
    			int Y = yy+y;
    			int YY = XX+yy*yy;
    			for (int zz = -r; zz < r; zz++)
    			{
    				int Z = zz+z;
    				int ZZ = YY+zz*zz;
    				if (ZZ<r22)
    				{
    					if(world.getBlock(X, Y, Z).isFlammable(world, XX, YY, ZZ, ForgeDirection.UP) && world.getBlock(X, Y + 1, Z) == Blocks.air) {
    						world.setBlock(X, Y + 1, Z, Blocks.fire);
    					}
    				}
    			}
    		}
    	}
    	
    }
    
    /**
     * Sets all blocks on fire
     * @param world
     * @param x
     * @param y
     * @param z
     * @param bound
     */
    public static void burn(World world, int x, int y, int z, int bound) {
    	
    	int r = bound;
    	int r2 = r*r;
    	int r22 = r2/2;
    	for (int xx = -r; xx < r; xx++)
    	{
    		int X = xx+x;
    		int XX = xx*xx;
    		for (int yy = -r; yy < r; yy++)
    		{
    			int Y = yy+y;
    			int YY = XX+yy*yy;
    			for (int zz = -r; zz < r; zz++)
    			{
    				int Z = zz+z;
    				int ZZ = YY+zz*zz;
    				if (ZZ<r22)
    				{
    					if(world.getBlock(X, Y + 1, Z) == Blocks.air) {
    						world.setBlock(X, Y + 1, Z, Blocks.fire);
    					}
    				}
    			}
    		}
    	}
    	
    }
    
    public static void destruction(World world, int x, int y, int z) {
    	
    	if(world.getBlock(x, y, z) == Blocks.bedrock || 
    			world.getBlock(x, y, z) == ModBlocks.reinforced_brick || 
    			world.getBlock(x, y, z) == ModBlocks.reinforced_sand || 
    			world.getBlock(x, y, z) == ModBlocks.reinforced_glass || 
    			world.getBlock(x, y, z) == ModBlocks.reinforced_lamp_on || 
    			world.getBlock(x, y, z) == ModBlocks.reinforced_lamp_off)
    	{
    		
    	} else {
    		world.setBlock(x, y, z, Blocks.air);
    	}
    	
    }
    
    public static void cluster(World world, int x, int y, int z, int count, int gravity) {
    	
    	double d1 = 0;
    	double d2 = 0;
    	double d3 = 0;
    	EntityRocket fragment;
    	
    	for(int i = 0; i < count; i++)
    	{
    		d1 = rand.nextDouble();
    		d2 = rand.nextDouble();
    		d3 = rand.nextDouble();
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d1 *= -1;
    		}
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d3 *= -1;
    		}
    		
    		fragment = new EntityRocket(world, x, y, z, d1, d2, d3, 0.0125D);
    		
    		world.spawnEntityInWorld(fragment);
    	}
    }
    
    public static void schrab(World world, int x, int y, int z, int count, int gravity) {
    	
    	double d1 = 0;
    	double d2 = 0;
    	double d3 = 0;
    	EntitySchrab fragment;
    	
    	for(int i = 0; i < count; i++)
    	{
    		d1 = rand.nextDouble();
    		d2 = rand.nextDouble();
    		d3 = rand.nextDouble();
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d1 *= -1;
    		}
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d3 *= -1;
    		}
    		
    		fragment = new EntitySchrab(world, x, y, z, d1, d2, d3, 0.0125D);
    		
    		world.spawnEntityInWorld(fragment);
    	}
    }
    
    public static void nuke(World world, int x, int y, int z, int count) {
    	
    	double d1 = 0;
    	double d2 = 0;
    	double d3 = 0;
    	EntityTNTPrimed fragment;
    	
    	for(int i = 0; i < 5; i++)
    	{
    		d1 = rand.nextDouble();
    		d2 = rand.nextDouble();
    		d3 = rand.nextDouble();
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d1 *= -1;
    		}
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d3 *= -1;
    		}
    		
    		fragment = new EntityTNTPrimed(world);
    		fragment.motionX = d1;
    		fragment.motionY = d2;
    		fragment.motionZ = d3;
    		
    		world.spawnEntityInWorld(fragment);
    	}
    }
    
    public static void frag(World world, int x, int y, int z, int count, boolean flame, Entity shooter) {
    	
    	double d1 = 0;
    	double d2 = 0;
    	double d3 = 0;
    	EntityArrow fragment;
    	
    	for(int i = 0; i < count; i++)
    	{
    		d1 = rand.nextDouble();
    		d2 = rand.nextDouble();
    		d3 = rand.nextDouble();
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d1 *= -1;
    		}
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d3 *= -1;
    		}
    		
    		fragment = new EntityArrow(world, x, y, z);
    		
    		fragment.motionX = d1;
    		fragment.motionY = d2;
    		fragment.motionZ = d3;
    		fragment.shootingEntity = shooter;
    		
    		fragment.setIsCritical(true);
    		if(flame)
    		{
    			fragment.setFire(1000);
    		}
    		
    		fragment.setDamage(2.5);
    		
    		world.spawnEntityInWorld(fragment);
    	}
    }
    
    public static void anvil(World world, int x, int y, int z, int count) {
    	
    	double d1 = 0;
    	double d2 = 0;
    	double d3 = 0;
    	EntityFallingBlock fragment;
    	
    	for(int i = 0; i < count; i++)
    	{
    		d1 = rand.nextDouble();
    		d2 = rand.nextDouble();
    		d3 = rand.nextDouble();;
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d1 *= -1;
    		}
    		
    		if(rand.nextInt(2) == 0)
    		{
    			d3 *= -1;
    		}
    		
    		world.setBlock(x, y, z, Blocks.anvil);
    		fragment = new EntityFallingBlock(world, x + 0.5, y + 0.5, z + 0.5, Blocks.anvil);
    		world.setBlock(x, y, z, Blocks.air);
    		
    		fragment.motionX = d1;
    		fragment.motionY = d2;
    		fragment.motionZ = d3;
    		
    		world.spawnEntityInWorld(fragment);
    	}
    }
    
    public static void poison(World world, int x, int y, int z, int bombStartStrength) {
		float f = bombStartStrength;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;
        double wat = bombStartStrength*2;
        boolean isOccupied = false;
        

        bombStartStrength *= 2.0F;
        i = MathHelper.floor_double(x - wat - 1.0D);
        j = MathHelper.floor_double(x + wat + 1.0D);
        k = MathHelper.floor_double(y - wat - 1.0D);
        int i2 = MathHelper.floor_double(y + wat + 1.0D);
        int l = MathHelper.floor_double(z - wat - 1.0D);
        int j2 = MathHelper.floor_double(z + wat + 1.0D);
        List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            double d4 = entity.getDistance(x, y, z) / bombStartStrength;

            if (d4 <= 1.0D)
            {
                d5 = entity.posX - x;
                d6 = entity.posY + entity.getEyeHeight() - y;
                d7 = entity.posZ - z;
                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
                if (d9 < wat)
                {
                	if(entity instanceof EntityPlayer && Library.checkForGasMask((EntityPlayer)entity))
                	{
                		//Library.damageSuit(((EntityPlayer)entity), 3);
                		
                	} else if(entity instanceof EntityLivingBase)
                    {
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.getId(), 15 * 20, 0));
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.getId(), 2 * 60 * 20, 2));
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.getId(), 30 * 20, 5));
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 2 * 60 * 20, 2));
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 2 * 60 * 20, 2));
                    }
                }
            }
        }

        bombStartStrength = (int)f;
    }
    
    public static void floater(World world, int x, int y, int z, int radi, int height) {
    	
    	Block save;
    	int meta;
    	
    	int r = radi;
    	int r2 = r*r;
    	int r22 = r2/2;
    	for (int xx = -r; xx < r; xx++)
    	{
    		int X = xx+x;
    		int XX = xx*xx;
    		for (int yy = -r; yy < r; yy++)
    		{
    			int Y = yy+y;
    			int YY = XX+yy*yy;
    			for (int zz = -r; zz < r; zz++)
    			{
    				int Z = zz+z;
    				int ZZ = YY+zz*zz;
    				if (ZZ<r22)
    				{
    					save = world.getBlock(X, Y, Z);
    					meta = world.getBlockMetadata(X, Y, Z);
    					world.setBlock(X, Y, Z, Blocks.air);
    					if(save != Blocks.air)
    					{
    						world.setBlock(X, Y + height, Z, save);
    						world.setBlockMetadataWithNotify(X, Y + height, Z, meta, 2);
    					}
    				}
    			}
    		}
    	}
    	
    }
    
    public static void move(World world, int x, int y, int z, int radius, int a, int b, int c) {
		float f = radius;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;
        double wat = radius;
        boolean isOccupied = false;
        int rand = 0;
        

        radius *= 2.0F;
        i = MathHelper.floor_double(x - wat - 1.0D);
        j = MathHelper.floor_double(x + wat + 1.0D);
        k = MathHelper.floor_double(y - wat - 1.0D);
        int i2 = MathHelper.floor_double(y + wat + 1.0D);
        int l = MathHelper.floor_double(z - wat - 1.0D);
        int j2 = MathHelper.floor_double(z + wat + 1.0D);
        List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            double d4 = entity.getDistance(x, y, z) / radius;

            if (d4 <= 1.0D)
            {
                d5 = entity.posX - x;
                d6 = entity.posY + entity.getEyeHeight() - y;
                d7 = entity.posZ - z;
                if(entity instanceof EntityLiving && !(entity instanceof EntitySheep))
                {
                	rand = random.nextInt(2);
                	if(rand == 0)
                	{
                		((EntityLiving)entity).setCustomNameTag("Dinnerbone");
                	} else {
                		((EntityLiving)entity).setCustomNameTag("Grumm");
                	}
                }
                
                if (entity instanceof EntitySheep)
                {
            		((EntityLiving)entity).setCustomNameTag("jeb_");
                }
                
                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
                if (d9 < wat)
                {
                	entity.setPosition(entity.posX += a, entity.posY += b, entity.posZ += c);
                }
            }
        }

        radius = (int)f;
    }
	
	public static Entity getHomingTarget(World world, int x, int y, int z, int radius, Entity e) {
				float f = radius;
		        HashSet hashset = new HashSet();
		        int i;
		        int j;
		        int k;
		        double d5;
		        double d6;
		        double d7;
		        double wat = radius*2;
		        boolean isOccupied = false;
		        

		        radius *= 2.0F;
		        i = MathHelper.floor_double(x - wat - 1.0D);
		        j = MathHelper.floor_double(x + wat + 1.0D);
		        k = MathHelper.floor_double(y - wat - 1.0D);
		        int i2 = MathHelper.floor_double(y + wat + 1.0D);
		        int l = MathHelper.floor_double(z - wat - 1.0D);
		        int j2 = MathHelper.floor_double(z + wat + 1.0D);
		        List list = world.getEntitiesWithinAABBExcludingEntity(e, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
		        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

		        for (int i1 = 0; i1 < list.size(); ++i1)
		        {
		            Entity entity = (Entity)list.get(i1);
		            double d4 = entity.getDistance(x, y, z) / radius;

		            if (d4 <= 1.0D)
		            {
		                d5 = entity.posX - x;
		                d6 = entity.posY + entity.getEyeHeight() - y;
		                d7 = entity.posZ - z;
		                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
		                if (true)
		                {
		                    d5 /= d9;
		                    d6 /= d9;
		                    d7 /= d9;
		                    double d10 = world.getBlockDensity(vec3, entity.boundingBox);
		                    if(entity instanceof EntityMissileBase)
		                    {
		                    	return entity;
		                    }
		                }
		            }
		        }

		        radius = (int)f;
		        return null;
	}
	
	public static void delMissiles(World world, int x, int y, int z, int radius, Entity e) {
				float f = radius;
		        HashSet hashset = new HashSet();
		        int i;
		        int j;
		        int k;
		        double d5;
		        double d6;
		        double d7;
		        double wat = radius*2;
		        boolean isOccupied = false;
		        

		        radius *= 2.0F;
		        i = MathHelper.floor_double(x - wat - 1.0D);
		        j = MathHelper.floor_double(x + wat + 1.0D);
		        k = MathHelper.floor_double(y - wat - 1.0D);
		        int i2 = MathHelper.floor_double(y + wat + 1.0D);
		        int l = MathHelper.floor_double(z - wat - 1.0D);
		        int j2 = MathHelper.floor_double(z + wat + 1.0D);
		        List list = world.getEntitiesWithinAABBExcludingEntity(e, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
		        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

		        for (int i1 = 0; i1 < list.size(); ++i1)
		        {
		            Entity entity = (Entity)list.get(i1);
		            double d4 = entity.getDistance(x, y, z) / radius;

		            if (d4 <= 1.0D)
		            {
		                d5 = entity.posX - x;
		                d6 = entity.posY + entity.getEyeHeight() - y;
		                d7 = entity.posZ - z;
		                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
		                if (true)
		                {
		                    d5 /= d9;
		                    d6 /= d9;
		                    d7 /= d9;
		                    double d10 = world.getBlockDensity(vec3, entity.boundingBox);
		                    if(entity instanceof EntityMissileBase)
		                    {
		                    	entity = null;
		                    }
		                }
		            }
		        }

		        radius = (int)f;
	}
	
	public static void mirv(World world, double x, double y, double z) {
		double modifier = 2.5;
    	double zeta = Math.sqrt(2)/2;
    	double theta = Math.sqrt(1 - Math.pow(0.5, 2));
    	EntityMirv mirv1 = new EntityMirv(world);
    	EntityMirv mirv2 = new EntityMirv(world);
    	EntityMirv mirv3 = new EntityMirv(world);
    	EntityMirv mirv4 = new EntityMirv(world);
    	//double vx1 = rand.nextDouble();
    	//double vy1 = rand.nextDouble() * -1;
    	//double vz1 = Math.sqrt(Math.pow(1, 2) - Math.pow(vx1, 2));
    	double vx1 = 1;                           
    	double vy1 = rand.nextDouble() * -1;                      
    	double vz1 = 0;

    	mirv1.posX = x;
    	mirv1.posY = y;
    	mirv1.posZ = z;
    	mirv1.motionY = vy1;
    	mirv2.posX = x;
    	mirv2.posY = y;
    	mirv2.posZ = z;
    	mirv2.motionY = vy1;
    	mirv3.posX = x;
    	mirv3.posY = y;
    	mirv3.posZ = z;
    	mirv3.motionY = vy1;
    	mirv4.posX = x;
    	mirv4.posY = y;
    	mirv4.posZ = z;
    	mirv4.motionY = vy1;
    	
    	mirv1.motionX = vx1 * modifier;
    	mirv1.motionZ = vz1 * modifier;	
    	world.spawnEntityInWorld(mirv1);
    	
    	mirv2.motionX = -vz1 * modifier;
    	mirv2.motionZ = vx1 * modifier;	
    	world.spawnEntityInWorld(mirv2);
    	
    	mirv3.motionX = -vx1 * modifier;
    	mirv3.motionZ = -vz1 * modifier;	
    	world.spawnEntityInWorld(mirv3);
    	
    	mirv4.motionX = vz1 * modifier;
    	mirv4.motionZ = -vx1 * modifier;	
    	world.spawnEntityInWorld(mirv4);
    	
    	EntityMirv mirv5 = new EntityMirv(world);
    	EntityMirv mirv6 = new EntityMirv(world);
    	EntityMirv mirv7 = new EntityMirv(world);
    	EntityMirv mirv8 = new EntityMirv(world);
    	//double vx2 = vx1 < theta ? vx1 + theta : vx1 - theta;
    	//double vy2 = vy1;
    	//double vz2 = Math.sqrt(Math.pow(1, 2) - Math.pow(vx2, 2));
    	double vx2 = zeta;     
    	double vy2 = vy1;                                         
    	double vz2 = zeta;

    	mirv5.posX = x;
    	mirv5.posY = y;
    	mirv5.posZ = z;
    	mirv5.motionY = vy2;
    	mirv6.posX = x;
    	mirv6.posY = y;
    	mirv6.posZ = z;
    	mirv6.motionY = vy2;
    	mirv7.posX = x;
    	mirv7.posY = y;
    	mirv7.posZ = z;
    	mirv7.motionY = vy2;
    	mirv8.posX = x;
    	mirv8.posY = y;
    	mirv8.posZ = z;
    	mirv8.motionY = vy2;
    	
    	mirv5.motionX = vx2 * modifier;
    	mirv5.motionZ = vz2 * modifier;	
    	world.spawnEntityInWorld(mirv5);
    	
    	mirv6.motionX = -vz2 * modifier;
    	mirv6.motionZ = vx2 * modifier;	
    	world.spawnEntityInWorld(mirv6);
    	
    	mirv7.motionX = -vx2 * modifier;
    	mirv7.motionZ = -vz2 * modifier;	
    	world.spawnEntityInWorld(mirv7);
    	
    	mirv8.motionX = vz2 * modifier;
    	mirv8.motionZ = -vx2 * modifier;	
    	world.spawnEntityInWorld(mirv8);
	}

	public static void plasma(World world, int x, int y, int z, int radius)
	{
		int r = radius;
		int r2 = r*r;
		int r22 = r2/2;
		for (int xx = -r; xx < r; xx++)
		{
			int X = xx+x;
			int XX = xx*xx;
			for (int yy = -r; yy < r; yy++)
			{
				int Y = yy+y;
				int YY = XX+yy*yy;
				for (int zz = -r; zz < r; zz++)
				{
					int Z = zz+z;
					int ZZ = YY+zz*zz;
					if (ZZ<r22+world.rand.nextInt(r22/2))
					{
						if(world.getBlock(X, Y, Z) != Blocks.bedrock && world.getBlock(X, Y, Z) != ModBlocks.statue_elb && world.getBlock(X, Y, Z) != ModBlocks.statue_elb_g&& world.getBlock(X, Y, Z) != ModBlocks.statue_elb_w && world.getBlock(X, Y, Z) != ModBlocks.statue_elb_f) world.setBlock(X, Y, Z, ModBlocks.plasma);
					}
				}
			}
		}	
	}

	public static void tauMeSinPi(World world, double x, double y, double z, int count, Entity shooter,
			EntityGrenadeTau tau) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntityBullet fragment;

		if (shooter != null && shooter instanceof EntityPlayer)
			for (int i = 0; i < count; i++) {
				d1 = rand.nextDouble();
				d2 = rand.nextDouble();
				d3 = rand.nextDouble();

				if (rand.nextInt(2) == 0) {
					d1 *= -1;
				}

				if (rand.nextInt(2) == 0) {
					d2 *= -1;
				}

				if (rand.nextInt(2) == 0) {
					d3 *= -1;
				}

				if (rand.nextInt(5) == 0) {
					fragment = new EntityBullet(world, (EntityPlayer) shooter, 3.0F, 35, 45, false, "tauDay", tau);
					fragment.setDamage(rand.nextInt(301) + 100);
				} else {
					fragment = new EntityBullet(world, (EntityPlayer) shooter, 3.0F, 35, 45, false, "eyyOk", tau);
					fragment.setDamage(rand.nextInt(11) + 35);
				}

				fragment.motionX = d1 * 5;
				fragment.motionY = d2 * 5;
				fragment.motionZ = d3 * 5;
				fragment.shootingEntity = shooter;

				fragment.setIsCritical(true);

				world.spawnEntityInWorld(fragment);
			}
	}

	public static void zomgMeSinPi(World world, double x, double y, double z, int count, Entity shooter,
			EntityGrenadeZOMG zomg) {

		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		EntityBullet fragment;

		if (shooter != null && shooter instanceof EntityPlayer)
			for (int i = 0; i < count; i++) {
				d1 = rand.nextDouble();
				d2 = rand.nextDouble();
				d3 = rand.nextDouble();

				if (rand.nextInt(2) == 0) {
					d1 *= -1;
				}

				if (rand.nextInt(2) == 0) {
					d2 *= -1;
				}

				if (rand.nextInt(2) == 0) {
					d3 *= -1;
				}
				
				EntityRainbow entityZomg = new EntityRainbow(world, (EntityPlayer) shooter, 1F, 10000, 100000, zomg);

				entityZomg.motionX = d1;// * 5;
				entityZomg.motionY = d2;// * 5;
				entityZomg.motionZ = d3;// * 5;
				entityZomg.shootingEntity = shooter;

				world.spawnEntityInWorld(entityZomg);
				world.playSoundAtEntity(zomg, "hbm:weapon.zomgShoot", 10.0F, 0.8F + (rand.nextFloat() * 0.4F));
			}
	}

}
