package com.hbm.blocks.machine;

import com.hbm.blocks.generic.BlockToolConversion;

import net.minecraft.block.material.Material;

public class BlockICFComponent extends BlockToolConversion {

	public BlockICFComponent() {
		super(Material.iron);
		this.addVariant(".vessel", ".vessel_welded", ".structure", ".structure_bolted");
	}
}
