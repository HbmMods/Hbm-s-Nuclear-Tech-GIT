package com.hbm.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialGas extends Material {

	public MaterialGas() {
		super(MapColor.airColor);
		this.setNoPushMobility();
		this.setReplaceable();
	}

	public boolean isSolid() {
		return true;
	}

	public boolean getCanBlockGrass() {
		return false;
	}

	public boolean blocksMovement() {
		return false;
	}
}
