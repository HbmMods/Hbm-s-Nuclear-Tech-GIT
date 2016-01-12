package com.hbm.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BombMulti extends BlockContainer {

	public TileEntityBombMulti tetn = new TileEntityBombMulti();
	
	public final float explosionBaseValue = 8.0F;
	public float explosionValue = 0.0F;
	public int clusterCount = 0;
	public int fireRadius = 0;
	public int poisonRadius = 0;
	public int gasCloud = 0;

    private final Random field_149933_a = new Random();
	private static boolean keepInventory = false;
    private Map field_77288_k = new HashMap();

	protected BombMulti(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityBombMulti();
	}
	
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.bomb_multi);
    }
	
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
        	TileEntityBombMulti tileentityfurnace = (TileEntityBombMulti)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

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
                            EntityItem entityitem = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + f), (double)((float)p_149749_3_ + f1), (double)((float)p_149749_4_ + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.field_149933_a.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.field_149933_a.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.field_149933_a.nextGaussian() * f3);
                            p_149749_1_.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
            }
        }

        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityBombMulti entity = (TileEntityBombMulti) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_bomb_multi, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
    public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
    	TileEntityBombMulti entity = (TileEntityBombMulti) p_149695_1_.getTileEntity(x, y, z);
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	if(/*entity.getExplosionType() != 0*/entity.isLoaded())
        	{
        		this.onBlockDestroyedByPlayer(p_149695_1_, x, y, z, 1);
            	igniteTestBomb(p_149695_1_, x, y, z);
        	}
        }
    }
	
	public boolean igniteTestBomb(World world, int x, int y, int z)
	{
    	TileEntityBombMulti entity = (TileEntityBombMulti) world.getTileEntity(x, y, z);
		if (!world.isRemote)
		{
		//world.spawnParticle("hugeexplosion", x, y, z, 0, 0, 0);
		//world.playSoundEffect(x, y, z, "random.explode", 1.0f, world.rand.nextFloat() * 0.1F + 0.9F);
		
        /*switch(entity.getExplosionType())
        {
        case 1:
        	entity.clearSlots();
        	world.setBlockToAir(x, y, z);
        	world.createExplosion(null, x , y , z , 18.0F, true);
        	break;
        case 2:
        	entity.clearSlots();
        	world.setBlockToAir(x, y, z);
        	world.createExplosion(null, x , y , z , 34.0F, true);
        	break;
        case 3:
        	entity.clearSlots();
        	world.setBlockToAir(x, y, z);
        	world.createExplosion(null, x , y , z , 8.0F, true);
        	ExplosionChaos.cluster(world, x, y, z, 100, 1);
        	break;
        case 4:
        	entity.clearSlots();
        	world.setBlockToAir(x, y, z);
        	world.createExplosion(null, x , y , z , 3.0F, true);
        	ExplosionChaos.burn(world, x, y, z, 20);
        	break;
        case 5:
        	entity.clearSlots();
        	world.setBlockToAir(x, y, z);
        	world.createExplosion(null, x , y , z , 3.0F, true);
        	ExplosionNukeGeneric.waste(world, x, y, z, 30);
        	break;
        case 6:
        	entity.clearSlots();
        	world.setBlockToAir(x, y, z);
        	world.createExplosion(null, x , y , z , 3.0F, true);
        	ExplosionChaos.poison(world, x, y, z, 25);
        	break;
        }*/
        	if(entity.isLoaded())
        	{
        		this.explosionValue = this.explosionBaseValue;
        		switch(entity.return2type())
        		{
        		case 1:
        			this.explosionValue += 1.0F;
        			break;
        		case 2:
        			this.explosionValue += 4.0F;
        			break;
        		case 3:
        			this.clusterCount += 50;
        			break;
        		case 4:
        			this.fireRadius += 10;
        			break;
        		case 5:
        			this.poisonRadius += 15;
        			break;
        		case 6:
        			this.gasCloud += 15;
        		}
        		switch(entity.return5type())
        		{
        		case 1:
        			this.explosionValue += 1.0F;
        			break;
        		case 2:
        			this.explosionValue += 4.0F;
        			break;
        		case 3:
        			this.clusterCount += 50;
        			break;
        		case 4:
        			this.fireRadius += 10;
        			break;
        		case 5:
        			this.poisonRadius += 15;
        			break;
        		case 6:
        			this.gasCloud += 15;
        		}

        		entity.clearSlots();
            	world.setBlockToAir(x, y, z);
            	world.createExplosion(null, x , y , z , this.explosionValue, true);
            	this.explosionValue = 0;
        		
        		if(this.clusterCount > 0)
        		{
                	ExplosionChaos.cluster(world, x, y, z, this.clusterCount, 1);
        		}
        		
        		if(this.fireRadius > 0)
        		{
                	ExplosionChaos.burn(world, x, y, z, this.fireRadius);
        		}
        		
        		if(this.poisonRadius > 0)
        		{
                	ExplosionNukeGeneric.waste(world, x, y, z, this.poisonRadius);
        		}
        		
        		if(this.gasCloud > 0)
        		{
                	ExplosionChaos.poison(world, x, y, z, this.gasCloud);
        		}
        		
        		this.clusterCount = 0;
        		this.fireRadius = 0;
        		this.poisonRadius = 0;
        		this.gasCloud = 0;
        		
        		
        	}
        }
		return false;
	}
	
	public int getRenderType(){
		return -1;
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
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
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        float f = 0.0625F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8*f, 1.0F);
    }

}