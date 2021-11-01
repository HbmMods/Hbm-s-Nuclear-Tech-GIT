package com.hbm.blocks.generic;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;

public class BlockNTMGlass extends BlockBreakable {
	
	int renderLayer;

	public BlockNTMGlass(int layer, String name, Material material) {
		super(name, material, false);
		this.renderLayer = layer;
	}
	
    public int quantityDropped(Random rand) {
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return renderLayer;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }

}
