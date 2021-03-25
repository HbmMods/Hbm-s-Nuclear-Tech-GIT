package com.hbm.blocks.generic;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRadioactive extends Block implements IItemHazard {
	
	ItemHazardModule module;
	
	private float radIn = 0.0F;
	private float radMax = 0.0F;

	public BlockRadioactive(Material mat, float rad, float max) {
		super(mat);
		this.module = new ItemHazardModule();
		this.setTickRandomly(true);
		radIn = rad;
		radMax = max;
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
	}

}
