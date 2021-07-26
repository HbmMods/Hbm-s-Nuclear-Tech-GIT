package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSemtex extends Block implements IBomb {
	
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;

	public BlockSemtex(Material mat) {
		super(mat);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":block_semtex");
		this.topIcon = p_149651_1_.registerIcon(RefStrings.MODID + ":block_semtex_front");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		int k = getPistonOrientation(meta);
		return ForgeDirection.getOrientation(k).getOpposite().ordinal() == side ? this.topIcon : this.blockIcon;
	}

	public static int getPistonOrientation(int meta) {
		return meta & 7;
	}

	@Override
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
		int l = determineOrientation(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
		p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, l, 2);
	}

	public static int determineOrientation(World world, int x, int y, int z, EntityLivingBase player) {

		if(MathHelper.abs((float) player.posX - (float) x) < 2.0F && MathHelper.abs((float) player.posZ - (float) z) < 2.0F) {
			double d0 = player.posY + 1.82D - (double) player.yOffset;
			
			if(d0 - (double) y > 2.0D) {
				return 0;
			}
			
			if((double) y - d0 > 0.0D) {
				return 1;
			}
		}
		
		int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		return l == 0 ? 3 : (l == 1 ? 4 : (l == 2 ? 2 : (l == 3 ? 5 : 1)));
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		this.explode(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_) {
		if(world.isBlockIndirectlyGettingPowered(x, y, z)) {
			this.explode(world, x, y, z);
		}
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		if(!world.isRemote) {
			new ExplosionNT(world, null, x + 0.5, y + 0.5, z + 0.5, 50).overrideResolution(64).explode();
			ExplosionLarge.spawnParticles(world, x, y, z, ExplosionLarge.cloudFunction(15));
		}
	}
}
