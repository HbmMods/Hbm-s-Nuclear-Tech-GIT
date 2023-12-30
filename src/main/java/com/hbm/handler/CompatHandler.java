package com.hbm.handler;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;


/**
 * General handler for OpenComputers compatibility.
 * <p/>
 * Mostly just functions used across many TEs.
 */
public class CompatHandler {
    public static Object[] steamTypeToInt(FluidType type) {
        if(type == Fluids.STEAM) {return new Object[] {0};}
        else if(type == Fluids.HOTSTEAM) {return new Object[] {1};}
        else if(type == Fluids.SUPERHOTSTEAM) {return new Object[] {2};}
        return new Object[] {3};
    }
    public static FluidType intToSteamType(int arg) {
        switch(arg) {
            default:
                return Fluids.STEAM;
            case(1):
                return Fluids.HOTSTEAM;
            case(2):
                return Fluids.SUPERHOTSTEAM;
            case(3):
                return Fluids.ULTRAHOTSTEAM;
        }
    }
}
