package com.hbm.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialGas extends Material {

	public MaterialGas() {
		super(MapColor.airColor);
		this.setNoPushMobility();
		this.setReplaceable();
	}

<<<<<<< HEAD
	@Override
=======
>>>>>>> master
	public boolean isSolid() {
		return true;
	}

<<<<<<< HEAD
	@Override
=======
>>>>>>> master
	public boolean getCanBlockGrass() {
		return false;
	}

<<<<<<< HEAD
	@Override
=======
>>>>>>> master
	public boolean blocksMovement() {
		return false;
	}
}
