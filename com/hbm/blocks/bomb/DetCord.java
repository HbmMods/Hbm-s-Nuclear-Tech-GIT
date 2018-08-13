package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DetCord extends Block implements IBomb {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public DetCord(Material p_i45394_1_) {
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
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion p_149723_5_)
    {
        this.explode(world, x, y, z);
    }

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block p_149695_5_)
    {
        if (world.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	this.explode(world, x, y, z);
        }
    }

    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return null;
    }

	@Override
	public void explode(World world, int x, int y, int z) {
		if(!world.isRemote) {
			
			world.setBlock(x, y, z, Blocks.air);
			if(this == ModBlocks.det_cord) {
				world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, true);
			}
			if(this == ModBlocks.det_charge) {
				ExplosionLarge.explode(world, x, y, z, 15, true, false, false);
			}
			if(this == ModBlocks.det_nuke) {
				world.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(world, MainRegistry.missileRadius, x + 0.5, y + 0.5, z + 0.5));

				EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000, MainRegistry.missileRadius * 0.005F);
				entity2.posX = x;
				entity2.posY = y;
				entity2.posZ = z;
				world.spawnEntityInWorld(entity2);
			}
		}
	}

}
