package com.hbm.dim.trait;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

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
	    nbt.setString("fluid", fluid.getName());
	    nbt.setFloat("pressure", pressure);
	    
    	super.writeToNBT(nbt);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
    	Fluids.fromName(nbt.getString("fluid"));
    	pressure = nbt.getFloat("pressure");
    	super.readFromNBT(nbt);
    }
}
