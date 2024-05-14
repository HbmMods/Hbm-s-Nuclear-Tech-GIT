package com.hbm.dim.trait;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.nbt.NBTTagCompound;

public class CBT_Atmosphere extends CelestialBodyTrait {
    
    public FluidType fluid;
    public float pressure;
    //just like this class name, it has indeed tortured something
    public CBT_Atmosphere() {}
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
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
    	nbt.getString("fluid");
    	nbt.getFloat("pressure");
    	super.readFromNBT(nbt);
    }
}
