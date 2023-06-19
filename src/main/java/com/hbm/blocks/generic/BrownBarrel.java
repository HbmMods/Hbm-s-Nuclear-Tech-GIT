package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockFluidBarrel;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.world.feature.OilSpot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundEvent;

public class BrownBarrel extends Block {

	Random rand = new Random();
	
	public BrownBarrel(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public void onBlockDestroyedByExplosion(World p_149723_1_, int p_149723_2_, int p_149723_3_, int p_149723_4_, Explosion p_149723_5_) {
		if(!p_149723_1_.isRemote && this == ModBlocks.yellow_barrel) {
			explode(p_149723_1_, p_149723_2_, p_149723_3_, p_149723_4_);
		}
	}

	public void explode(World world, int x, int y, int z) {

		if(this == ModBlocks.oil_barrel)
			world.newExplosion((Entity) null, x + 0.5F, y + 0.5F, z + 0.5F, 2.5F, true, true);

	}
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        super.breakBlock(world, x, y, z, block, metadata);

		//world.playSoundEffect(x, y, z, "mob.zombie.woodbreak", 2.0F, 0.95F + world.rand.nextFloat() * 0.2F);

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
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);

		if(this == ModBlocks.oil_barrel)
			OilSpot.generateOilSpot(world, x, z, 4, 10, false);

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
