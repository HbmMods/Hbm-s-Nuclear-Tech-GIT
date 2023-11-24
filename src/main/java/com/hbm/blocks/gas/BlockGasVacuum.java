package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.SpaceConfig;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.PlanetaryTraitUtil.Hospitality;
import com.hbm.util.ArmorUtil;
import com.hbm.util.PlanetaryTraitUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
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

public class BlockGasVacuum extends BlockGasBase {
	
	public BlockGasVacuum() {
		super(0F, 0F, 0F);
	}

	@Override
	public int getRenderType() {
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return super.getRenderBlockPass();
	}

    public boolean isAir(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(!world.isRemote)
		{
			if(PlanetaryTraitUtil.isDimensionWithTraitNT(world, Hospitality.OXYNEG))
			{
				for(int i = -1; i < 2; i++) {
					for(int j = -1; j < 2; j++) {
						for(int k = -1; k < 2; k++) {
							Block b = world.getBlock(x+i, y+j, z+k);
							if(b == ModBlocks.air_block)
							{
								world.setBlock(x+i, y+j, z+k, ModBlocks.vacuum);
								world.scheduleBlockUpdate(x+i, y+j, z+k, this, 1);
							}	
							if(b == Blocks.water)
							{
								world.setBlock(x+i, y+j, z+k, ModBlocks.vacuum);
								world.scheduleBlockUpdate(x+i, y+j, z+k, this, 1);
							}
							if(b instanceof BlockLeaves)
							{
								world.setBlock(x+i, y+j, z+k, ModBlocks.waste_leaves);
							}
							if(b instanceof BlockGrass)
							{
								world.setBlock(x+i, y+j, z+k, ModBlocks.waste_earth);
							}
						}
					}
				}
			}
		}
		super.updateTick(world, x, y, z, rand);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final int x, final int y, final int z, final int side) {

		return false;
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
		
		return b==Blocks.air || b==ModBlocks.air_block;
	}
}
