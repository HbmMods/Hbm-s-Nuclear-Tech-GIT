package com.hbm.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class AcidFluid extends Fluid {

	public AcidFluid() {
		super("acid_fluid");
	}

	@SideOnly(Side.CLIENT)
    public IIcon getIcon() {
    	return getStillIcon();
    }

	@SideOnly(Side.CLIENT)
    public IIcon getStillIcon() {
        return AcidBlock.stillIcon;
    }

	@SideOnly(Side.CLIENT)
    public IIcon getFlowingIcon() {
        return AcidBlock.flowingIcon;
    }
}
