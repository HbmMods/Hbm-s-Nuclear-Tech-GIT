package com.hbm.dim.trait;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.nbt.NBTTagCompound;

public class CBT_Temperature extends CelestialBodyTrait {
    
    public float gregrees;

    public CBT_Temperature(float gregrees) {
        this.gregrees = gregrees;
    }
    
    
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
	    nbt.setFloat("gregrees", gregrees);
    	super.writeToNBT(nbt);
    }
    
}
