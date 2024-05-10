package com.hbm.dim.trait;

import net.minecraft.nbt.NBTTagCompound;

public abstract class PlanetaryTrait {

    // Similarly to fluid traits, we have classes, and instance members.
    // For the simple traits, we'll just init both here rather than two places.
    
    // Breathable MAY be just replaced with a check against PT_Atmosphere
    public static class PT_Breathable extends PlanetaryTrait { }
    public static PT_Breathable BREATHABLE = new PT_Breathable();

    public static class PT_Hot extends PlanetaryTrait { }
    public static PT_Hot HOT = new PT_Hot();
    
    public static class PT_Cold extends PlanetaryTrait { }
    public static PT_Cold COLD = new PT_Cold();


    // Serialization
    public PlanetaryTrait readFromNBT(NBTTagCompound nbt) {
        return null;
    }

    public void writeToNBT(NBTTagCompound nbt) {

    }
    
}
