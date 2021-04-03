package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.blocks.machine.BlockFluidBarrel;
import com.hbm.explosion.ExplosionThermo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RedBarrel extends Block {

	public RedBarrel(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {
		if(!p_149723_1_.isRemote) {
			explode(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_);
		}
	}

	@Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_) {
		if((this == ModBlocks.red_barrel || this == ModBlocks.pink_barrel) && p_149695_1_.getBlock(x + 1, y, z) == Blocks.fire || p_149695_1_.getBlock(x - 1, y, z) == Blocks.fire || p_149695_1_.getBlock(x, y + 1, z) == Blocks.fire || p_149695_1_.getBlock(x, y - 1, z) == Blocks.fire || p_149695_1_.getBlock(x, y, z + 1) == Blocks.fire || p_149695_1_.getBlock(x, y, z - 1) == Blocks.fire) {
			if(!p_149695_1_.isRemote)
				explode(p_149695_1_, x, y, z);
		}
	}

	public void explode(World p_149695_1_, int x, int y, int z) {

		if(this == ModBlocks.red_barrel || this == ModBlocks.pink_barrel)
			p_149695_1_.newExplosion((Entity) null, x + 0.5F, y + 0.5F, z + 0.5F, 2.5F, true, true);

		if(this == ModBlocks.lox_barrel) {

			p_149695_1_.newExplosion(null, x + 0.5F, y + 0.5F, z + 0.5F, 1F, false, false);

			ExplosionThermo.freeze(p_149695_1_, x, y, z, 7);
		}

		if(this == ModBlocks.taint_barrel) {

			p_149695_1_.newExplosion(null, x + 0.5F, y + 0.5F, z + 0.5F, 1F, false, false);

			Random rand = p_149695_1_.rand;
			for(int i = 0; i < 100; i++) {
				int a = rand.nextInt(9) - 4 + x;
				int b = rand.nextInt(9) - 4 + y;
				int c = rand.nextInt(9) - 4 + z;
				if(p_149695_1_.getBlock(a, b, c).isReplaceable(p_149695_1_, a, b, c) && BlockTaint.hasPosNeightbour(p_149695_1_, a, b, c)) {
					p_149695_1_.setBlock(a, b, c, ModBlocks.taint, rand.nextInt(3) + 4, 2);
				}
			}
		}
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

}
