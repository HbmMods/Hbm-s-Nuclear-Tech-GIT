package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockDetonatable;
import com.hbm.blocks.machine.BlockFluidBarrel;
import com.hbm.entity.item.EntityTNTPrimedBase;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.radiation.ChunkRadiationManager;

import codechicken.lib.math.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YellowBarrel extends BlockDetonatable {

	Random rand = new Random();

	public YellowBarrel(Material material) {
		super(material, 0, 0, 100, true, false);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		if (this != ModBlocks.yellow_barrel) return;
		super.onBlockDestroyedByExplosion(world, x, y, z, explosion);
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
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);

		world.spawnParticle("townaura", x + rand.nextFloat() * 0.5F + 0.25F, y + 1.1F, z + rand.nextFloat() * 0.5F + 0.25F, 0.0D, 0.0D, 0.0D);
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

	@Override
	public void explodeEntity(World world, double x, double y, double z, EntityTNTPrimedBase entity) {
		int ix = MathHelper.floor_double(x), iy = MathHelper.floor_double(y), iz = MathHelper.floor_double(z);

		if(rand.nextInt(3) == 0) {
			world.setBlock(ix, iy, iz, ModBlocks.toxic_block);
		} else {
			world.createExplosion(entity, x, y, z, 12.0F, true);
		}
		ExplosionNukeGeneric.waste(world, ix, iy, iz, 35);

		for(int i = -5; i <= 5; i++) {
			for(int j = -5; j <= 5; j++) {
				for(int k = -5; k <= 5; k++) {
					
					if(world.rand.nextInt(5) == 0 && world.getBlock(ix + i, iy + j, iz + k) == Blocks.air)
						world.setBlock(ix + i, iy + j, iz + k, ModBlocks.gas_radon_dense);
				}
			}
		}
		ChunkRadiationManager.proxy.incrementRad(world, ix, iy, iz, 35);
	}

}
