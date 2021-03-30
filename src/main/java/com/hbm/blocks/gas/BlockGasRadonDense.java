package com.hbm.blocks.gas;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.potion.HbmPotion;
import com.hbm.util.ContaminationUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockGasRadonDense extends BlockGasBase {

	@Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity) {
		
		if(entity instanceof EntityLivingBase) {
			ContaminationUtil.applyRadDirect(entity, 0.5F);
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 15 * 20, 0));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);
		world.spawnParticle("townaura", x + rand.nextFloat(), y + rand.nextFloat(), z + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
	}

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(5) == 0)
			return ForgeDirection.UP;
		
		return ForgeDirection.DOWN;
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		if(!world.isRemote) {
	
			if(rand.nextInt(20) == 0) {
				if(world.getBlock(x, y - 1, z) == Blocks.grass)
					world.setBlock(x, y - 1, z, ModBlocks.waste_earth);
			}
	
			if(rand.nextInt(30) == 0) {
				world.setBlockToAir(x, y, z);
				
				if(ModBlocks.fallout.canPlaceBlockAt(world, x, y, z)) {
					world.setBlock(x, y, z, ModBlocks.fallout);
				}
				
				return;
			}
		}
		
		super.updateTick(world, x, y, z, rand);
	}
}
