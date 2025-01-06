package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.machine.BlockPillar;
import com.hbm.entity.item.EntityTNTPrimedBase;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.interfaces.IBomb;

import api.hbm.block.IFuckingExplode;
import net.minecraft.util.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DetMiner extends BlockPillar implements IBomb, IFuckingExplode {

	public DetMiner(Material mat, String top) {
		super(mat, top);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j) {
		return null;
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {

		if(!world.isRemote) {

			world.func_147480_a(x, y, z, false);
			ExplosionNT explosion = new ExplosionNT(world, null, x + 0.5, y + 0.5, z + 0.5, 4);
			explosion.atttributes.add(ExAttrib.ALLDROP);
			explosion.atttributes.add(ExAttrib.NOHURT);
			explosion.doExplosionA();
			explosion.doExplosionB(false);

			ExplosionLarge.spawnParticles(world, x + 0.5, y + 0.5, z + 0.5, 30);
		}

		return BombReturnCode.DETONATED;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		if(!world.isRemote) {
			EntityTNTPrimedBase tntPrimed = new EntityTNTPrimedBase(world, x + 0.5D, y + 0.5D, z + 0.5D, explosion != null ? explosion.getExplosivePlacedBy() : null, this);
			tntPrimed.fuse = 0;
			tntPrimed.detonateOnCollision = false;
			world.spawnEntityInWorld(tntPrimed);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_) {
		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			this.explode(world, x, y, z);
		}
	}

	@Override
	public void explodeEntity(World world, double x, double y, double z, EntityTNTPrimedBase entity) {
		explode(world, MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
	}
}
