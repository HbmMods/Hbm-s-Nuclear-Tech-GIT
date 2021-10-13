package com.hbm.blocks.machine.rbmk;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RBMKDebris extends Block {

	public RBMKDebris() {
		super(Material.iron);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType(){
		return this.renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
