package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockDetonatable;
import com.hbm.blocks.machine.BlockFluidBarrel;
import com.hbm.entity.item.EntityTNTPrimedBase;
import com.hbm.explosion.ExplosionThermo;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RedBarrel extends BlockDetonatable {

	// Flammable barrels also explode when shot
	public RedBarrel(Material material, boolean flammable) {
		super(material, flammable ? 2 : 0,  flammable ? 15 : 0, 100, true, flammable);
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
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
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
	public void explodeEntity(World world, double x, double y, double z, EntityTNTPrimedBase entity) {
		int ix = MathHelper.floor_double(x), iy = MathHelper.floor_double(y), iz = MathHelper.floor_double(z);

		if(this == ModBlocks.red_barrel || this == ModBlocks.pink_barrel) {
			world.newExplosion(entity, x, y, z, 2.5F, true, true);
		} else if(this == ModBlocks.lox_barrel) {
			world.newExplosion(entity, x, y, z, 1F, false, false);

			ExplosionThermo.freeze(world, ix, iy, iz, 7);
		} else if(this == ModBlocks.taint_barrel) {
			world.newExplosion(entity, x, y, z, 1F, false, false);

			Random rand = world.rand;
			for(int i = 0; i < 100; i++) {
				int a = rand.nextInt(9) - 4 + ix;
				int b = rand.nextInt(9) - 4 + iy;
				int c = rand.nextInt(9) - 4 + iz;
				Block block = world.getBlock(a, b, c);
				if(block.isNormalCube() && !block.isAir(world, a, b, c)) {
					world.setBlock(a, b, c, ModBlocks.taint, rand.nextInt(3) + 4, 2);
				}
			}
		}
	}

}
