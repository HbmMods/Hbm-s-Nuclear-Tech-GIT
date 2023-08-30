package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosiveCharge extends Block implements IBomb, IDetConnectible {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public ExplosiveCharge(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {

		super.registerBlockIcons(iconRegister);

		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":det_nuke_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {

		if(this != ModBlocks.det_nuke)
			return this.blockIcon;

		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion p_149723_5_) {
		this.explode(world, x, y, z);
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion explosion) {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_) {
		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			this.explode(world, x, y, z);
		}
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			world.setBlock(x, y, z, Blocks.air);
			if(this == ModBlocks.det_cord) {
				world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, true);
			}
			if(this == ModBlocks.det_charge) {
				new ExplosionNT(world, null, x + 0.5, y + 0.5, z + 0.5, 15).overrideResolution(64).explode();
				ExplosionLarge.spawnParticles(world, x, y, z, ExplosionLarge.cloudFunction(15));
			}
			if(this == ModBlocks.det_nuke) {
				world.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(world, BombConfig.missileRadius, x + 0.5, y + 0.5, z + 0.5));
				EntityNukeTorex.statFac(world, x + 0.5, y + 0.5, z + 0.5, BombConfig.missileRadius);
			}
		}

		return BombReturnCode.DETONATED;
	}

}
