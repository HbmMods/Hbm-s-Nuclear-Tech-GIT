package com.hbm.inventory.fluid.trait;

import java.util.List;

import com.hbm.dim.CelestialBody;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class FT_Gaseous extends FluidTrait {

    @Override
    public void addInfoHidden(List<String> info) {
        info.add(EnumChatFormatting.BLUE + "[Gaseous]");
    }
	
    // Venting gases into the atmosphere
	public static void release(World world, FluidType type, double mB) {
        if(world.isRemote) return;

		boolean isGas = type.hasTrait(FT_Gaseous.class) || type.hasTrait(FT_Gaseous_ART.class);
		if(!isGas) return;

        CelestialBody.emitGas(world, type, mB);
	}
	
    // Extracting gases from the atmosphere
	public static void capture(World world, FluidType type, double mB) {
        if(world.isRemote) return;

		boolean isGas = type.hasTrait(FT_Gaseous.class) || type.hasTrait(FT_Gaseous_ART.class);
		if(!isGas) return;

        CelestialBody.consumeGas(world, type, mB);
	}

}
