package com.hbm.blocks;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BombFloat extends Block {
	
    public World worldObj;
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	protected BombFloat(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":bomb_float_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":bomb_float");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : this.blockIcon);
	}

	@Override
	public void onNeighborBlockChange(World p_149695_1_, int x, int y, int z, Block p_149695_5_)
    {
    	this.worldObj = p_149695_1_;
        if (p_149695_1_.isBlockIndirectlyGettingPowered(x, y, z))
        {
        	p_149695_1_.setBlock(x, y, z, Blocks.air);
        	ExplosionChaos.floater(p_149695_1_, x, y, z, 15, 50);
        	ExplosionChaos.move(p_149695_1_, x, y, z, 15, 0, 50, 0);
        }
    }

}
