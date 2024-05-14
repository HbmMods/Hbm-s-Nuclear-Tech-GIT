package com.hbm.dim.trait;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.nbt.NBTTagCompound;

public class CBT_Atmosphere extends CelestialBodyTrait {
    
    public FluidType fluid;
    public float pressure;

    public CBT_Atmosphere(FluidType fluid, float pressure) {
        this.fluid = fluid;
        this.pressure = pressure;
    }
    
    
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
    	String fluidString = fluid.getName();
	    nbt.setString("fluid", fluidString);
	    nbt.setFloat("pressure", pressure);
    	super.writeToNBT(nbt);
    }
    
}
