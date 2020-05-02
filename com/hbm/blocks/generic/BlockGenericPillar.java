package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockGenericPillar extends BlockRotatedPillar {

    @SideOnly(Side.CLIENT)
    protected IIcon iconSide;
    
	public BlockGenericPillar(Material p_i45425_1_) {
		super(p_i45425_1_);
	}

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
    	if(this == ModBlocks.meteor_pillar) {
	        this.field_150164_N = reg.registerIcon(RefStrings.MODID + ":meteor_pillar_top");
	        this.iconSide = reg.registerIcon(RefStrings.MODID + ":meteor_pillar");
    	}
    	if(this == ModBlocks.block_schrabidium_cluster) {
	        this.field_150164_N = reg.registerIcon(RefStrings.MODID + ":block_schrabidium_cluster_top");
	        this.iconSide = reg.registerIcon(RefStrings.MODID + ":block_schrabidium_cluster_side");
    	}
    	if(this == ModBlocks.block_euphemium_cluster) {
	        this.field_150164_N = reg.registerIcon(RefStrings.MODID + ":block_euphemium_cluster_top");
	        this.iconSide = reg.registerIcon(RefStrings.MODID + ":block_euphemium_cluster_side");
    	}
    }

	@Override
	protected IIcon getSideIcon(int p_150163_1_) {
		return iconSide;
	}

}
