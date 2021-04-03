package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.BlockDummyable;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.material.Material;

public abstract class RBMKBase extends BlockDummyable {

	protected RBMKBase() {
		super(Material.iron);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	public static int renderIDRods = RenderingRegistry.getNextAvailableRenderId();
	public static int renderIDPassive = RenderingRegistry.getNextAvailableRenderId();
	public static int renderIDControl = RenderingRegistry.getNextAvailableRenderId();
}
