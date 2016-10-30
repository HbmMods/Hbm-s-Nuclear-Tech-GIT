package com.hbm.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.hbm.entity.EntityNukeExplosionAdvanced;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityCrashedBomb;

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

public class BlockCrashedBomb extends BlockContainer implements IBomb {

	protected BlockCrashedBomb(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCrashedBomb();
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.crashed_balefire);
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
	
	public void explode(World world, int x, int y, int z) {
        if (!world.isRemote)
        {
        	EntityNukeExplosionAdvanced entity0 = new EntityNukeExplosionAdvanced(world);
	    	entity0.posX = x;
	    	entity0.posY = y;
	    	entity0.posZ = z;
	    	entity0.destructionRange = MainRegistry.fatmanRadius;
	    	entity0.speed = 25;
	    	entity0.coefficient = 10.0F;
	    	
	    	world.spawnEntityInWorld(entity0);
    		ExplosionParticleB.spawnMush(world, x, y - 3, z);
        }
	}
}
