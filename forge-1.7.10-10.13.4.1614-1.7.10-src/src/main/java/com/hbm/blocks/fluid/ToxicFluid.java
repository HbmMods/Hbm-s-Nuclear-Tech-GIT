package com.hbm.blocks.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class ToxicFluid extends Fluid {

	public ToxicFluid() {
		super("toxic_fluid");
	}

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon() {
    	return getStillIcon();
    }

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getStillIcon() {
        return ToxicBlock.stillIcon;
    }

	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getFlowingIcon() {
        return ToxicBlock.flowingIcon;
    }
}
