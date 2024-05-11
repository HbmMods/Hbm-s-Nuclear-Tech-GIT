package com.hbm.dim.trait;

import net.minecraft.nbt.NBTTagCompound;

public abstract class CelestialBodyTrait {

    // Similarly to fluid traits, we have classes, and instance members.
    // For the simple traits, we'll just init both here rather than two places.
    
    // Breathable MAY be just replaced with a check against PT_Atmosphere
    public static class CBT_Breathable extends CelestialBodyTrait { }
    public static CBT_Breathable BREATHABLE = new CBT_Breathable();

    public static class CBT_Hot extends CelestialBodyTrait { }
    public static CBT_Hot HOT = new CBT_Hot();
    
    public static class CBT_Cold extends CelestialBodyTrait { }
    public static CBT_Cold COLD = new CBT_Cold();


    // Serialization
    public CelestialBodyTrait readFromNBT(NBTTagCompound nbt) {
        return null;
    }

    public void writeToNBT(NBTTagCompound nbt) {

    }
    
}
