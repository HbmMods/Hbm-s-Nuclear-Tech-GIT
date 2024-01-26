package com.hbm.blocks.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class RadFluid extends Fluid {

	public RadFluid() {
		super("rad_lava_fluid");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon() {
		return getStillIcon();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getStillIcon() {
		return RadBlock.stillIconRad;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getFlowingIcon() {
		return RadBlock.flowingIconRad;
	}
}
