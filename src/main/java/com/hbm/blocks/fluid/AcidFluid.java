package com.hbm.blocks.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class AcidFluid extends Fluid {

	public AcidFluid() {
		super("acid_fluid");
	}

	public AcidFluid(String name) {
		super(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return getStillIcon();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getStillIcon() {
		return AcidBlock.stillIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getFlowingIcon() {
		return AcidBlock.flowingIcon;
	}
}
