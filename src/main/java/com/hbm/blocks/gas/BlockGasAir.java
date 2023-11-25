package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.PlanetaryTraitUtil.Hospitality;
import com.hbm.util.ArmorUtil;
import com.hbm.util.PlanetaryTraitUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasAir extends BlockGasBase {
	
	public BlockGasAir() {
		super(1F, 1F, 1F);
	}

	@Override
	public int getRenderType() {
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}
    public boolean isAir(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
    		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
    			
    			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
    			
    			if(b == this)
    				continue;
    			
    			if(isAirBlock(b)) {
    				
    				if(b.isReplaceable(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
    					if(getAirCount(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == 0) {
    						world.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, ModBlocks.vacuum);
    						return;
    					}
    				}
    				
    				world.setBlockToAir(x, y, z);
    				return;
    			}
    		}
        
        super.updateTick(world, x, y, z, rand);
    }

	private Block[] airBlocks = {ModBlocks.air_block, ModBlocks.air_block1, ModBlocks.air_block2, ModBlocks.air_block3, ModBlocks.air_block4, ModBlocks.air_block5, ModBlocks.air_block6, ModBlocks.air_block7, ModBlocks.air_block8, ModBlocks.air_block9};

	private Block getResult() {
	    for (int i = 0; i < airBlocks.length - 1; i++) {
	        if (this == airBlocks[i]) {
	            return airBlocks[i+1];
	        }
	    }
	    return ModBlocks.air_block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side) {
		/*if (WarpDriveConfig.BREATHING_AIR_BLOCK_DEBUG) {
			return side == 0 || side == 1;
		}*/
		
		/*final Block blockSide = blockAccess.getBlock(x, y, z);
		if (blockSide instanceof BlockGasAir) {
			return false;
		}*/
		
		return false;///blockAccess.isAirBlock(x, y, z);
	}

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(2) == 0)
			return ForgeDirection.UP;
		
		return ForgeDirection.DOWN;
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}
	private boolean isAirBlock(Block b) {
		
		if(b == this)
			return false;
		
		return b==Blocks.air || b==ModBlocks.vacuum;
		/*return b.getMaterial() == Material.air || 
				b.getBlockBoundsMinX() > 0 || b.getBlockBoundsMinY() > 0 || b.getBlockBoundsMinZ() > 0 ||
				b.getBlockBoundsMaxX() < 1 || b.getBlockBoundsMaxY() < 1 || b.getBlockBoundsMaxZ() < 1;*/
	}
	private int getAirCount(World world, int x, int y, int z) {
		
		int air = 0;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			
			if(isAirBlock(b))
				air++;
		}
		
		return air;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		
		if(!(entity instanceof EntityLivingBase))
			return;
		
		HbmLivingProps.SsetOxy((EntityLivingBase)entity, 20);
	}
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if(!world.isRemote) world.scheduleBlockUpdate(x, y, z, this, 1);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!world.isRemote) world.scheduleBlockUpdate(x, y, z, this, 1);
	}
}
