package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BombThermo extends Block implements IBomb {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BombThermo(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":therm_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.therm_exo ? ":therm_exo" : ":therm_endo"));
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		if(this == ModBlocks.therm_endo) {
			return Item.getItemFromBlock(ModBlocks.therm_endo);
		}

		return Item.getItemFromBlock(ModBlocks.therm_exo);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_) {
		if(p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z)) {
			p_149695_1_.setBlock(x, y, z, Blocks.air);
			if(this == ModBlocks.therm_endo) {
				ExplosionThermo.freeze(p_149695_1_, x, y, z, 15);
				ExplosionThermo.freezer(p_149695_1_, x, y, z, 20);
			}

			if(this == ModBlocks.therm_exo) {
				ExplosionThermo.scorch(p_149695_1_, x, y, z, 15);
				ExplosionThermo.setEntitiesOnFire(p_149695_1_, x, y, z, 20);
			}

			p_149695_1_.createExplosion(null, x, y, z, 5.0F, true);
		}
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		world.setBlock(x, y, z, Blocks.air);
		if(this == ModBlocks.therm_endo) {
			ExplosionThermo.freeze(world, x, y, z, 15);
			ExplosionThermo.freezer(world, x, y, z, 20);
		}

		if(this == ModBlocks.therm_exo) {
			ExplosionThermo.scorch(world, x, y, z, 15);
			ExplosionThermo.setEntitiesOnFire(world, x, y, z, 20);
		}

		world.createExplosion(null, x, y, z, 5.0F, true);
		return BombReturnCode.DETONATED;
	}
}
