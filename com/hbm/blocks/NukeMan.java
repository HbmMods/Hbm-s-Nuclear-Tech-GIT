package com.hbm.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.hbm.entity.EntityNukeCloudNoShroom;
import com.hbm.entity.EntityNukeCloudSmall;
import com.hbm.entity.EntityNukeExplosionAdvanced;
import com.hbm.interfaces.IBomb;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityNukeMan;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class NukeMan extends BlockContainer implements IBomb {

	public TileEntityNukeMan tetn = new TileEntityNukeMan();

    private final Random field_149933_a = new Random();
	private static boolean keepInventory = false;
    private Map field_77288_k = new HashMap();

	protected NukeMan(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityNukeMan();
		
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.nuke_man);
    }
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
        	TileEntityNukeMan tileentityfurnace = (TileEntityNukeMan)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

            if (tileentityfurnace != null)
            {
                for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

                    if (itemstack != null)
                    {
                        float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j1 = this.field_149933_a.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                            {
                                j1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j1;
                            EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (float)this.field_149933_a.nextGaussian() * f3;
                            entityitem.motionY = (float)this.field_149933_a.nextGaussian() * f3 + 0.2F;
                            entityitem.motionZ = (float)this.field_149933_a.nextGaussian() * f3;
                            p_149749_1_.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityNukeMan entity = (TileEntityNukeMan) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_nuke_man, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
    	TileEntityNukeMan entity = (TileEntityNukeMan) p_149695_1_.getTileEntity(x, y, z);
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	if(entity.isReady())
        	{
        		this.onBlockDestroyedByPlayer(p_149695_1_, x, y, z, 1);
            	entity.clearSlots();
            	p_149695_1_.setBlockToAir(x, y, z);
            	igniteTestBomb(p_149695_1_, x, y, z);
        	}
        }
    }
	
	public boolean igniteTestBomb(World world, int x, int y, int z)
	{
		if (!world.isRemote) {
			/*
		dealDamage(world,x,y,z, 30);
		detonateTestBomb(world,x,y,z, 30);
		vapor(world,x,y,z, 30);
		dealDamage(world,x,y,z, 60);
		detonateTestBomb(world,x,y,z, 60);
		vapor(world,x,y,z, 60);
		dealDamage(world,x,y,z, 90);
		detonateTestBomb(world,x,y,z, 90);
		vapor(world,x,y,z, 90);
		dealDamage(world,x,y,z, 120);
		detonateTestBomb(world,x,y,z, 120);
		vapor(world,x,y,z, 120);
		*/
		tetn.clearSlots();
		//world.spawnParticle("hugeexplosion", x, y, z, 0, 0, 0); //spawns a huge explosion particle
		world.playSoundEffect(x, y, z, "random.explode", 1.0f, world.rand.nextFloat() * 0.1F + 0.9F); //x,y,z,sound,volume,pitch
		/*ExplosionNukeGeneric.detonateTestBomb(world, x, y, z, 175);
		ExplosionNukeGeneric.vapor(world, x, y, z, 195);
		ExplosionNukeGeneric.waste(world, x, y, z, 250);
		ExplosionNukeGeneric.dealDamage(world, x, y, z, 195);*/

    	/*EntityNukeExplosion entity = new EntityNukeExplosion(world);
    	entity.posX = x;
    	entity.posY = y;
    	entity.posZ = z;
    	entity.destructionRange = 175;
    	entity.vaporRange = 195;
    	entity.wasteRange = 250;
    	entity.damageRange = 195;
    	
    	world.spawnEntityInWorld(entity);*/
		
		EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(world);
    	entity.posX = x;
    	entity.posY = y;
    	entity.posZ = z;
    	entity.destructionRange = MainRegistry.manRadius;
    	entity.speed = 25;
    	entity.coefficient = 10.0F;
    	
    	world.spawnEntityInWorld(entity);
    	
    	//ExplosionNukeAdvanced.mush(world, x, y, z);

    	if (MainRegistry.enableNukeClouds) {
			EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000);
			entity2.posX = x;
			entity2.posY = y - 17;
			entity2.posZ = z;
			world.spawnEntityInWorld(entity2);
		} else {
			EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
			entity2.posX = x;
			entity2.posY = y - 17;
			entity2.posZ = z;
			world.spawnEntityInWorld(entity2);
		}
		}
    	
		return false;
	}
	
	/*public void detonateTestBomb(World world, int x, int y, int z, int bombStartStrength)
	{
		//Rodol's awesome destruction code
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
				int YY = XX+yy*yy;
				for (int zz = -r; zz < r; zz++)
				{
					int Z = zz+z; //z coord
					int ZZ = YY+zz*zz; //final= x*x+y*y+z*z. remind you of anything?
					if (ZZ<r22+world.rand.nextInt(r22/25)) //and the distance check. x*x+y*y+z*z < radius^2 is the same as sqrt(x*x+y*y+z*z) < radius, the distance formula. Here we use a random number between r^2 and r^2/2 for a jagged explosion.
					{ //but since sqrt is slow we optimize the algorithm for super fast explosions! Yay!
						//faster explosions means more explosions per second! 
						if(Y >= y) destruction(world, X, Y, Z); //destroy the block if its within the radius ...and if it's not bedrock :D
					} //you can change the if statement to if (ZZ<r2) for a smoother explosion crater.
				}
			}
		}
			
			for (int xx = -r; xx < r; xx++)
			{
				int X = xx+x;
				int XX = xx*xx;
				for (int yy = -r; yy < r; yy++)
				{
					int Y = yy+y;
					int YY = XX+yy*yy*50;
					for (int zz = -r; zz < r; zz++)
					{
						int Z = zz+z;
						int ZZ = YY+zz*zz;
						if (ZZ<r22/*+world.rand.nextInt(r22)*//*)
						{
							if(Y < y) destruction(world, X, Y, Z);
						}
					}
				}
			}
		
		
        
        //Particle creation: coming soon!
        
        //spawnMush(world, x, y, z);
        
	}
	
	public void dealDamage(World world, int x, int y, int z, int bombStartStrength) {
		//BlockTNT.class Damage code
				float f = bombStartStrength;
		        HashSet hashset = new HashSet();
		        int i;
		        int j;
		        int k;
		        double d5;
		        double d6;
		        double d7;
		        double wat = bombStartStrength*2;
		        

		        bombStartStrength *= 2.0F;
		        i = MathHelper.floor_double(x - wat - 1.0D);
		        j = MathHelper.floor_double(x + wat + 1.0D);
		        k = MathHelper.floor_double(y - wat - 1.0D);
		        int i2 = MathHelper.floor_double(y + wat + 1.0D);
		        int l = MathHelper.floor_double(z - wat - 1.0D);
		        int j2 = MathHelper.floor_double(z + wat + 1.0D);
		        List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox((double)i, (double)k, (double)l, (double)j, (double)i2, (double)j2));
		        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

		        for (int i1 = 0; i1 < list.size(); ++i1)
		        {
		            Entity entity = (Entity)list.get(i1);
		            double d4 = entity.getDistance(x, y, z) / (double)bombStartStrength;

		            if (d4 <= 1.0D)
		            {
		                d5 = entity.posX - x;
		                d6 = entity.posY + (double)entity.getEyeHeight() - y;
		                d7 = entity.posZ - z;
		                double d9 = (double)MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
		                if (d9 < wat)
		                {
		                    d5 /= d9;
		                    d6 /= d9;
		                    d7 /= d9;
		                    double d10 = (double)world.getBlockDensity(vec3, entity.boundingBox);
		                    double d11 = (1.0D - d4);// * d10;
		                    entity.attackEntityFrom(DamageSource.generic, (float)((int)((d11 * d11 + d11) / 2.0D * 8.0D * (double)bombStartStrength + 1.0D)));
		                    double d8 = EnchantmentProtection.func_92092_a(entity, d11);
		                    entity.motionX += d5 * d8;
		                    entity.motionY += d6 * d8;
		                    entity.motionZ += d7 * d8;

		                    if (entity instanceof EntityPlayer)
		                    {
		                        this.field_77288_k.put((EntityPlayer)entity, Vec3.createVectorHelper(d5 * d11, d6 * d11, d7 * d11));
		                    }
		                }
		                /*
		                if(d9 < bombStartStrength)
		                {
		                	d5 /= d9;
		                    d6 /= d9;
		                    d7 /= d9;
		                    double d10 = (double)world.getBlockDensity(vec3, entity.boundingBox);
		                    double d11 = (1.0D - d4);// * d10;
		                    if (!entity.worldObj.isRemote)
		                    {
		                    entity.setDead();
		                    entity = null;
		                    }/*
		                    double d8 = EnchantmentProtection.func_92092_a(entity, d11);
		                    entity.motionX += d5 * d8;
		                    entity.motionY += d6 * d8;
		                    entity.motionZ += d7 * d8;

		                    if (entity instanceof EntityPlayer)
		                    {
		                        this.field_77288_k.put((EntityPlayer)entity, Vec3.createVectorHelper(d5 * d11, d6 * d11, d7 * d11));
		                    }
		                }*//*
		            }
		        }

		        bombStartStrength = (int)f;
	}
	
	public void vapor(World world, int x, int y, int z, int bombStartStrength) {
		int r = bombStartStrength * 2;
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
						vaporDest(world, X, Y, Z);
				}
			}
		}
	}

	public void spawnMush(World world, int x, int y, int z)
	{
		for(int m = 0; m < 10; m++)
        {
        	//world.spawnParticle("largesmoke", x, y + m, z, 0, 0, 0);
        	world.setBlock(x, y + m, z, ModBlocks.event_tester, 0, 0);
        }
        for(int m = -4; m < 6; m++)
        {
        	for(int n = -6; n < 4; n++)
        	{
        		//world.spawnParticle("largesmoke", x + m, y + 10, z + n, 0, 0, 0);
        		world.setBlock(x + m, y + 10, z + n, ModBlocks.event_tester, 0, 0);
        	}
        }
	}*/
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}/*
	
	public void destruction(World world, int x, int y, int z)
	{
		int rand;
		
		if(world.getBlock(x, y, z) != Blocks.bedrock && world.getBlock(x, y, z) != ModBlocks.reinforced_brick && world.getBlock(x, y, z) != ModBlocks.reinforced_glass && world.getBlock(x, y, z) != ModBlocks.reinforced_light && world.getBlock(x, y, z) != ModBlocks.reinforced_sand && world.getBlock(x, y, z) != ModBlocks.reinforced_lamp_off && world.getBlock(x, y, z) != ModBlocks.reinforced_lamp_on)
		{
			if(world.getBlock(x, y, z) == ModBlocks.brick_concrete)
			{
				rand = field_149933_a.nextInt(8);
				if(rand == 0)
				{
					world.setBlock(x, y, z, Blocks.air, 0, 3);
				}
			}else if(world.getBlock(x, y, z) == ModBlocks.brick_light)
			{
				rand = field_149933_a.nextInt(2);
				if(rand == 0)
				{
					world.setBlock(x, y, z, Blocks.air, 0, 3);
				}
			}else if(world.getBlock(x, y, z) == ModBlocks.brick_obsidian)
			{
				rand = field_149933_a.nextInt(20);
				if(rand == 0)
				{
					world.setBlock(x, y, z, Blocks.air, 0, 3);
				}
			}else{
				world.setBlock(x, y, z, Blocks.air, 0, 3);
			}
		}
	}
	
	public void vaporDest(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z) == Blocks.water ||
				world.getBlock(x, y, z) == Blocks.flowing_water ||
				world.getBlock(x, y, z) == Blocks.tallgrass ||
				world.getBlock(x, y, z) == Blocks.leaves ||
				world.getBlock(x, y, z) == Blocks.leaves2 ||
				world.getBlock(x, y, z) == Blocks.double_plant ||
				world.getBlock(x, y, z) == Blocks.cactus)
		{
			world.setBlock(x, y, z, Blocks.air);
		}
		
		if(world.getBlock(x, y, z).isFlammable(world, x, y, z, ForgeDirection.UP) && world.getBlock(x, y + 1, z) == Blocks.air)
		{
			world.setBlock(x, y + 1, z, Blocks.fire);
		}
	}*/

	public void explode(World world, int x, int y, int z) {
    	TileEntityNukeMan entity = (TileEntityNukeMan) world.getTileEntity(x, y, z);
        //if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	if(entity.isReady())
        	{
        		this.onBlockDestroyedByPlayer(world, x, y, z, 1);
            	entity.clearSlots();
            	world.setBlockToAir(x, y, z);
            	igniteTestBomb(world, x, y, z);
        	}
        }
	}
}
