package com.hbm.blocks.machine;

import com.hbm.blocks.generic.BlockToolConversion;

import net.minecraft.block.material.Material;

public class BlockFusionComponent extends BlockToolConversion {

	public BlockFusionComponent() {
		super(Material.iron);
		this.addVariant(".bscco_welded", ".blanket", ".motor");
	}
}
