package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.item.EntityTNTPrimedBase;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionNT;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.RefStrings;
import com.hbm.particle.helper.ExplosionCreator;

import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ExplosiveCharge extends BlockDetonatable implements IBomb, IDetConnectible {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public ExplosiveCharge(Material material) {
		super(material, 0, 0, 0, false, false);
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
				ExplosionCreator.composeEffectStandard(world, x + 0.5, y + 1, z + 0.5);
			}
			if(this == ModBlocks.det_nuke) {
				world.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(world, BombConfig.missileRadius, x + 0.5, y + 0.5, z + 0.5));
				EntityNukeTorex.statFacStandard(world, x + 0.5, y + 0.5, z + 0.5, BombConfig.missileRadius);
			}
		}

		return BombReturnCode.DETONATED;
	}

	@Override
	public void explodeEntity(World world, double x, double y, double z, EntityTNTPrimedBase entity) {
		explode(world, MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
	}

}
