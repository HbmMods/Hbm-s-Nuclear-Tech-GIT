package com.hbm.blocks.bomb;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.interfaces.IBomb;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;

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

public class NukeFleija extends BlockContainer implements IBomb {

	public TileEntityNukeFleija tetn = new TileEntityNukeFleija();

    private final Random field_149933_a = new Random();
	private static boolean keepInventory = false;
    private Map field_77288_k = new HashMap();

	public NukeFleija(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityNukeFleija();
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.nuke_fleija);
    }
	
	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!keepInventory)
        {
        	TileEntityNukeFleija tileentityfurnace = (TileEntityNukeFleija)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

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
			TileEntityNukeFleija entity = (TileEntityNukeFleija) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_nuke_fleija, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
    @Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
    	TileEntityNukeFleija entity = (TileEntityNukeFleija) p_149695_1_.getTileEntity(x, y, z);
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z) && !p_149695_1_.isRemote)
        {
        	if(entity.isReady())
        	{
        		this.onBlockDestroyedByPlayer(p_149695_1_, x, y, z, 1);
            	entity.clearSlots();
            	p_149695_1_.setBlockToAir(x, y, z);
            	igniteTestBomb(p_149695_1_, x, y, z, MainRegistry.fleijaRadius);
        	}
        }
    }
	
	public boolean igniteTestBomb(World world, int x, int y, int z, int r)
	{
		if (!world.isRemote)
		{
			tetn.clearSlots();
			//world.spawnParticle("hugeexplosion", x, y, z, 0, 0, 0);
			world.playSoundEffect(x, y, z, "random.explode", 1.0f, world.rand.nextFloat() * 0.1F + 0.9F);
		
			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
    		entity.posX = x;
    		entity.posY = y;
    		entity.posZ = z;
    		entity.destructionRange = r;
    		entity.speed = MainRegistry.blastSpeed;
    		entity.coefficient = 1.0F;
    		entity.waste = false;
    	
    		world.spawnEntityInWorld(entity);
    		
    		EntityCloudFleija cloud = new EntityCloudFleija(world, r);
    		cloud.posX = x;
    		cloud.posY = y;
    		cloud.posZ = z;
    		world.spawnEntityInWorld(cloud);
    	}
    	
		return false;
	}
	
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
	}

	@Override
	public void explode(World world, int x, int y, int z) {
    	TileEntityNukeFleija entity = (TileEntityNukeFleija) world.getTileEntity(x, y, z);
        //if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	if(entity.isReady())
        	{
        		this.onBlockDestroyedByPlayer(world, x, y, z, 1);
            	entity.clearSlots();
            	world.setBlockToAir(x, y, z);
            	igniteTestBomb(world, x, y, z, MainRegistry.fleijaRadius);
        	}
        }
	}

}
