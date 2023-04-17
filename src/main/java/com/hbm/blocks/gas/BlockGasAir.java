package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ArmorUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		//System.out.println("Update");
		//System.out.println("Meta: "+meta);
		if(!world.isRemote)
		{
			//System.out.println("Meta Test");
			Random diff = new Random();
			if(diff.nextInt(5) == 0)
			{
				Block b = world.getBlock(x, y+1, z);
				if(b==Blocks.air || b==ModBlocks.vacuum)
				{
					world.setBlock(x, y, z, getResult());
					if(this!=ModBlocks.air_block)
					{
						world.setBlock(x, y+1, z, ModBlocks.air_block);	
					}
				}
				else if(b instanceof BlockGasAir)
				{
					world.setBlock(x, y, z, b);
					world.setBlock(x, y+1, z, this);
				}
			}
			if(diff.nextInt(5) == 1)
			{
				Block b = world.getBlock(x, y-1, z);
				if(b==Blocks.air || b==ModBlocks.vacuum)
				{
					world.setBlock(x, y, z, getResult());
					if(this!=ModBlocks.air_block)
					{
						world.setBlock(x, y-1, z, ModBlocks.air_block);	
					}
				}
				else if(b instanceof BlockGasAir)
				{
					world.setBlock(x, y, z, b);
					world.setBlock(x, y-1, z, this);
				}
			}
			if(diff.nextInt(5) == 2)
			{
				Block b = world.getBlock(x+1, y, z);
				if(b==Blocks.air || b==ModBlocks.vacuum)
				{
					world.setBlock(x, y, z, getResult());
					if(this!=ModBlocks.air_block)
					{
						world.setBlock(x+1, y, z, ModBlocks.air_block);	
					}
				}
				else if(b instanceof BlockGasAir)
				{
					world.setBlock(x, y, z, b);
					world.setBlock(x+1, y, z, this);
				}
			}
			if(diff.nextInt(5) == 3)
			{
				Block b = world.getBlock(x-1, y, z);
				if(b==Blocks.air || b==ModBlocks.vacuum)
				{
					world.setBlock(x, y, z, getResult());
					if(this!=ModBlocks.air_block)
					{
						world.setBlock(x-1, y, z, ModBlocks.air_block);	
					}
				}
				else if(b instanceof BlockGasAir)
				{
					world.setBlock(x, y, z, b);
					world.setBlock(x-1, y, z, this);
				}
			}
			if(diff.nextInt(5) == 4)
			{
				Block b = world.getBlock(x, y, z+1);
				if(b==Blocks.air || b==ModBlocks.vacuum)
				{
					world.setBlock(x, y, z, getResult());
					if(this!=ModBlocks.air_block)
					{
						world.setBlock(x, y, z+1, ModBlocks.air_block);	
					}
				}
				else if(b instanceof BlockGasAir)
				{
					world.setBlock(x, y, z, b);
					world.setBlock(x, y, z+1, this);
				}
			}
			if(diff.nextInt(5) == 5)
			{
				Block b = world.getBlock(x, y, z-1);
				if(b==Blocks.air || b==ModBlocks.vacuum)
				{
					world.setBlock(x, y, z, getResult());
					if(this!=ModBlocks.air_block)
					{
						world.setBlock(x, y, z-1, ModBlocks.air_block);	
					}
				}
				else if(b instanceof BlockGasAir)
				{
					world.setBlock(x, y, z, b);
					world.setBlock(x, y, z-1, this);
				}
			}
		}
		if(!world.isRemote && rand.nextInt(10) == 0 && meta == 0) {
			boolean up = world.getBlock(x, y+1, z)==Blocks.air || world.getBlock(x, y+1, z)==ModBlocks.vacuum;
			boolean down = world.getBlock(x, y-1, z)==Blocks.air || world.getBlock(x, y-1, z)==ModBlocks.vacuum;
			boolean north = world.getBlock(x, y, z-1)==Blocks.air || world.getBlock(x, y, z-1)==ModBlocks.vacuum;
			boolean south = world.getBlock(x, y, z+1)==Blocks.air || world.getBlock(x, y, z+1)==ModBlocks.vacuum;
			boolean east = world.getBlock(x+1, y, z)==Blocks.air || world.getBlock(x+1, y, z)==ModBlocks.vacuum;
			boolean west = world.getBlock(x+1, y, z)==Blocks.air || world.getBlock(x+1, y, z)==ModBlocks.vacuum;
			if(up && down && north && south && east && west)
			{
				world.setBlockToAir(x, y, z);
				return;
			}
			//world.setBlockToAir(x, y, z);
			//return;
		}
		super.updateTick(world, x, y, z, rand);
	}

	private Block getResult()
	{
		if(this==ModBlocks.air_block9)
		{
			return ModBlocks.air_block8;
		}
		if(this==ModBlocks.air_block8)
		{
			return ModBlocks.air_block7;
		}
		if(this==ModBlocks.air_block7)
		{
			return ModBlocks.air_block6;
		}
		if(this==ModBlocks.air_block6)
		{
			return ModBlocks.air_block5;
		}
		if(this==ModBlocks.air_block5)
		{
			return ModBlocks.air_block4;
		}
		if(this==ModBlocks.air_block4)
		{
			return ModBlocks.air_block3;
		}
		if(this==ModBlocks.air_block3)
		{
			return ModBlocks.air_block2;
		}
		if(this==ModBlocks.air_block2)
		{
			return ModBlocks.air_block1;
		}
		if(this==ModBlocks.air_block1)
		{
			return ModBlocks.air_block;
		}
		//if(this==ModBlocks.air_block)
		//{
		//	return Blocks.air;
		//}
		return ModBlocks.air_block;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side) {
		/*if (WarpDriveConfig.BREATHING_AIR_BLOCK_DEBUG) {
			return side == 0 || side == 1;
		}*/
		
		final Block blockSide = blockAccess.getBlock(x, y, z);
		if (blockSide instanceof BlockGasAir) {
			return false;
		}
		
		return blockAccess.isAirBlock(x, y, z);
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
}
