package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockFluidBarrel;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.radiation.ChunkRadiationManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YellowBarrel extends Block {

	Random rand = new Random();

	public YellowBarrel(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {
		if(!p_149723_1_.isRemote && this == ModBlocks.yellow_barrel) {
			explode(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_);
		}
	}

	public void explode(World world, int x, int y, int z) {

		if(rand.nextInt(3) == 0) {
			world.setBlock(x, y, z, ModBlocks.toxic_block);
		} else {
			world.createExplosion(null, x, y, z, 18.0F, true);
		}
		ExplosionNukeGeneric.waste(world, x, y, z, 35);

		for(int i = -5; i <= 5; i++) {
			for(int j = -5; j <= 5; j++) {
				for(int k = -5; k <= 5; k++) {
					
					if(world.rand.nextInt(5) == 0 && world.getBlock(x + i, y + j, z + k) == Blocks.air)
						world.setBlock(x + i, y + j, z + k, ModBlocks.gas_radon_dense);
				}
			}
		}
		ChunkRadiationManager.proxy.incrementRad(world, x, y, z, 35);
	}

	@Override
	public int getRenderType() {
		return BlockFluidBarrel.renderID;
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
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		float f = 0.0625F;
		this.setBlockBounds(2 * f, 0.0F, 2 * f, 14 * f, 1.0F, 14 * f);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.0625F;
		this.setBlockBounds(2 * f, 0.0F, 2 * f, 14 * f, 1.0F, 14 * f);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public boolean canDropFromExplosion(Explosion p_149659_1_) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
		super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);

		p_149734_1_.spawnParticle("townaura", p_149734_2_ + p_149734_5_.nextFloat() * 0.5F + 0.25F, p_149734_3_ + 1.1F, p_149734_4_ + p_149734_5_.nextFloat() * 0.5F + 0.25F, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);

		if(this == ModBlocks.yellow_barrel)
			ChunkRadiationManager.proxy.incrementRad(world, x, y, z, 5.0F);
		else
			ChunkRadiationManager.proxy.incrementRad(world, x, y, z, 0.5F);

		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

	@Override
	public int tickRate(World world) {
		return 20;
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

}