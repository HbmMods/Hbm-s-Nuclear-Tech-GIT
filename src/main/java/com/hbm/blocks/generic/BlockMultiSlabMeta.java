package com.hbm.blocks.generic;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockMultiSlabMeta extends BlockMultiSlab {
	
	public int[] metas;
	
	public BlockMultiSlabMeta(Block single, Material mat, Block[] slabMaterials, int...metas) {
		super(single != null, mat);
		this.single = single;
		this.slabMaterials = slabMaterials;
		this.metas = new int[slabMaterials.length]; //initialized to 0s
		for(int i = 0; i < metas.length; i++) {
			this.metas[i] = metas[i];
		}
		
		this.useNeighborBrightness = true;
		
		if(single == null) {
			for(int i = 0; i < slabMaterials.length; i++) {
				recipeGen.add(new Object[] {slabMaterials[i], metas[i], this, i});
			}
		}
		
		this.setBlockTextureName(RefStrings.MODID + ":concrete");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		meta = (meta & 7) % slabMaterials.length;
		Block block = slabMaterials[meta];
		return block.getIcon(side, metas[meta]);
	}
	
	@Override
	public String func_150002_b(int meta) {
		meta = (meta & 7) % slabMaterials.length;
		return super.func_150002_b(meta) + "." + metas[meta];
	}
}
