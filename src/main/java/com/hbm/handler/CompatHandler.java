package com.hbm.handler;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraftforge.common.util.ForgeDirection;


/**
 * General handler for OpenComputers compatibility.
 * @author BallOfEnergy (Microwave)
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

    public static final String nullComponent = "ntm_null";

    /**
     * This is an interface made specifically for adding OC compatibility to NTM machines. The {@link li.cil.oc.api.network.SimpleComponent} interface must also be implemented in the TE.
     * <br>
     * This interface is not required to be defined as an optional interface, though the {@link li.cil.oc.api.network.SimpleComponent} interface must be declared as an optional interface.
     * <br>
     * Pseudo multiblocks will automatically receive compatibility with their ports by proxying their `methods()` and `invoke()` functions. This is the only time they need to be defined.
     *
     **/
    @Optional.InterfaceList({
            @Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers"),
            @Optional.Interface(iface = "li.cil.oc.api.network.SidedComponent", modid = "OpenComputers"),
            @Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = "OpenComputers"),
    })
    @SimpleComponent.SkipInjection // make sure OC doesn't inject this shit into the interface and crash
    public interface OCComponent extends SimpleComponent, SidedComponent, ManagedPeripheral {

        /**
         * Must be overridden in the implemented TE, or it will default to "ntm_null".
         * <br>
         * Dictates the component name exposed to the computer.
         * @return String
         */
        @Override
        @Optional.Method(modid = "OpenComputers")
        default String getComponentName() {
            return nullComponent;
        }

        /**
         * Tells OC which sides of the block cables should connect to.
         * @param side Side to check
         * @return If the side should be able to connect.
         */
        @Override
        @Optional.Method(modid = "OpenComputers")
        default boolean canConnectNode(ForgeDirection side) {
            return true;
        }

        /**
         * Standard methods array from {@link li.cil.oc.api.network.ManagedPeripheral} extending {@link li.cil.oc.api.network.SimpleComponent}.
         * @return Array of methods to expose to the computer.
         */
        @Override
        @Optional.Method(modid = "OpenComputers")
        default String[] methods() {return new String[0];}

        /**
         * Standard invoke function from {@link li.cil.oc.api.network.ManagedPeripheral} extending {@link li.cil.oc.api.network.SimpleComponent}.
         * @return Data to the computer as a return from the function.
         */
        @Override
        @Optional.Method(modid = "OpenComputers")
        default Object[] invoke(String method, Context context, Arguments args) throws Exception {return null;}
    }
}
