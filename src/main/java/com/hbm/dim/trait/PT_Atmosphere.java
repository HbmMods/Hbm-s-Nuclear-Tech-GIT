package com.hbm.dim.trait;

import com.hbm.inventory.fluid.FluidType;

public class PT_Atmosphere extends PlanetaryTrait {
    
    public FluidType fluid;
    public float pressure;

    public PT_Atmosphere(FluidType fluid, float pressure) {
        this.fluid = fluid;
        this.pressure = pressure;
    }

}
