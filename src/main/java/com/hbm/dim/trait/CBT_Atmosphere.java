package com.hbm.dim.trait;

import com.hbm.inventory.fluid.FluidType;

public class CBT_Atmosphere extends CelestialBodyTrait {
    
    public FluidType fluid;
    public float pressure;

    public CBT_Atmosphere(FluidType fluid, float pressure) {
        this.fluid = fluid;
        this.pressure = pressure;
    }

}
