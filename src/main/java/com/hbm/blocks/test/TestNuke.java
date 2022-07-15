package com.hbm.blocks.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.EnumGUI;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityTestNuke;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TestNuke extends BlockContainer {
	
	protected int timer1 = 20;
	int timer2 = timer1;
	public TileEntityTestNuke tetn = new TileEntityTestNuke();

    private final Random field_149933_a = new Random();
	private static boolean keepInventory = false;
    private Map field_77288_k = new HashMap();
    private boolean isExploding = false;

	public TestNuke(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTestNuke();
		
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.test_nuke);
    }
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
            TileEntityTestNuke tileentityfurnace = (TileEntityTestNuke)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

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
			TileEntityTestNuke entity = (TileEntityTestNuke) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, EnumGUI.NUKE_TEST.ordinal(), world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
    	TileEntityTestNuke entity = (TileEntityTestNuke) p_149695_1_.getTileEntity(x, y, z);
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	if(entity.getNukeTier() > 0)
        	{
        		this.onBlockDestroyedByPlayer(p_149695_1_, x, y, z, 1);
        		int pootis = entity.getNukeTier();
            	entity.clearSlots();
            	p_149695_1_.setBlockToAir(x, y, z);
            	switch(pootis)
            	{
            	case 1:
            		igniteTestBomb(p_149695_1_, x, y, z, 20); break;
            	case 2:
            		igniteTestBomb(p_149695_1_, x, y, z, 40); break;
            	case 999:
            		igniteTestBomb(p_149695_1_, x, y, z, 200); break;
            	}
        	}
        }
    }
	
	public boolean igniteTestBomb(World world, int x, int y, int z, int bombStartStrength)
	{
		if (!world.isRemote)
		detonateTestBomb(world,x,y,z, bombStartStrength);
		world.spawnParticle("hugeexplosion", x, y, z, 0, 0, 0); //spawns a huge explosion particle
		world.playSoundEffect(x, y, z, "random.explode", 1.0f, world.rand.nextFloat() * 0.1F + 0.9F); //x,y,z,sound,volume,pitch
		return false;
	}
	
	public void detonateTestBomb(World world, int x, int y, int z, int bombStartStrength)
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
				int YY = XX+yy*yy*3;
				for (int zz = -r; zz < r; zz++)
				{
					int Z = zz+z; //z coord
					int ZZ = YY+zz*zz; //final= x*x+y*y+z*z. remind you of anything?
					if (ZZ<r22+world.rand.nextInt(r22)) //and the distance check. x*x+y*y+z*z < radius^2 is the same as sqrt(x*x+y*y+z*z) < radius, the distance formula. Here we use a random number between r^2 and r^2/2 for a jagged explosion.
					{ //but since sqrt is slow we optimize the algorithm for super fast explosions! Yay!
						//faster explosions means more explosions per second! 
						if(world.getBlock(X, Y, Z) != Blocks.bedrock)world.setBlock(X, Y, Z, Blocks.air, 0, 3); //destroy the block if its within the radius ...and if it's not bedrock :D
					} //you can change the if statement to if (ZZ<r2) for a smoother explosion crater.
				}
			}
		}
		
		//BlockTNT.class Damage code
		float f = bombStartStrength;
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
        List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));

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
                    d5 /= d9;
                    d6 /= d9;
                    d7 /= d9;
                    double d11 = (1.0D - d4);// * d10;
                    entity.attackEntityFrom(DamageSource.generic, ((int)((d11 * d11 + d11) / 2.0D * 8.0D * bombStartStrength + 1.0D)));
                    double d8 = EnchantmentProtection.func_92092_a(entity, d11);
                    entity.motionX += d5 * d8;
                    entity.motionY += d6 * d8;
                    entity.motionZ += d7 * d8;

                    if (entity instanceof EntityPlayer)
                    {
                        this.field_77288_k.put(entity, Vec3.createVectorHelper(d5 * d11, d6 * d11, d7 * d11));
                    }
                }
            }
        }

        bombStartStrength = (int)f;
        
        //Particle creation: coming soon!
        
        //spawnMush(world, x, y, z);
        
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
	}
}
