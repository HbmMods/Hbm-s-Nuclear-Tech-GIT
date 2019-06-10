package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockClorine extends Block {

    public BlockClorine(Material p_i45394_1_) {
		super(p_i45394_1_);
        this.setTickRandomly(true);
	}

	@Override
	public boolean isOpaqueCube()
    {    	
        return false;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
    	
    	return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    	
        /*Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
        
        if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]))
        {
            return true;
        }
        
        if (block == this)
        {
            return false;
        }

        return block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);*/
    }
	
    @Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
        world.setBlock(x, y, z, Blocks.air);
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
    @Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity)
    {
		if (entity instanceof EntityPlayer && Library.checkForGasMask((EntityPlayer) entity)) {
			
			if(world.rand.nextInt(25) == 0)
				Library.damageSuit((EntityPlayer)entity, 3, world.rand.nextInt(2));

		} else if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity)
					.addPotionEffect(new PotionEffect(Potion.blindness.getId(), 5 * 20, 0));
			((EntityLivingBase) entity)
					.addPotionEffect(new PotionEffect(Potion.poison.getId(), 20 * 20, 2));
			((EntityLivingBase) entity)
					.addPotionEffect(new PotionEffect(Potion.wither.getId(), 1 * 20, 1));
			((EntityLivingBase) entity)
					.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 30 * 20, 1));
			((EntityLivingBase) entity)
					.addPotionEffect(new PotionEffect(Potion.digSlowdown.getId(), 30 * 20, 2));
		}
    }

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		return null;
    }
	
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
        return false;
    }
    
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

}
