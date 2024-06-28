package com.hbm.handler;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.tileentity.TileEntityProxyCombo;
import cpw.mods.fml.common.Optional;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Array;


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
            @Optional.Interface(iface = "li.cil.oc.api.network.Analyzable", modid = "OpenComputers"),
            @Optional.Interface(iface = "li.cil.oc.api.network.ManagedPeripheral", modid = "OpenComputers"),
    })
    @SimpleComponent.SkipInjection // make sure OC doesn't inject this shit into the interface and crash
    public interface OCComponent extends SimpleComponent, SidedComponent, Analyzable, ManagedPeripheral {

        /**
         * Must be overridden in the implemented TE, or it will default to "ntm_null".
         * <br>
         * Dictates the component name exposed to the computer.
         * @return String
         */
        @Override
        default String getComponentName() {
            return "ntm_null";
        }

        /**
         * Tells OC which sides of the block cables should connect to.
         * @param side Side to check
         * @return If the side should be able to connect.
         */
        @Override
        default boolean canConnectNode(ForgeDirection side) {
            return true;
        }

        /**
         * Function to give more information when analyzing the block. Multiple entries in the array will be sent to the user in the order of the array.
         * @return Additional text to add in the form of lang entries (ex: "analyze.basic2").
         */
        default String[] getExtraInfo() {return new String[] {"analyze.noInfo"};}

        @Override
        default Node[] onAnalyze(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
            player.addChatComponentMessage(new ChatComponentTranslation("analyze.basic1").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
            player.addChatComponentMessage(new ChatComponentTranslation("analyze.basic2").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
            player.addChatComponentMessage(new ChatComponentTranslation("analyze.basic3").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
            player.addChatComponentMessage(new ChatComponentTranslation("analyze.name", this.getComponentName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
            String[] extraInfo = getExtraInfo();
            for (String info : extraInfo) {
                if(!info.equals(""))
                    player.addChatComponentMessage(new ChatComponentTranslation(info).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
            }
            TileEntity te = (TileEntity) this;
            if(Array.getLength(this.methods()) == 0 && te instanceof TileEntityProxyCombo || this.getComponentName().equals("ntm_null"))
                player.addChatComponentMessage(new ChatComponentTranslation("analyze.error").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            return null;
        }

        /**
         * Standard methods array from {@link li.cil.oc.api.network.ManagedPeripheral} extending {@link li.cil.oc.api.network.SimpleComponent}.
         * @return Array of methods to expose to the computer.
         */
        @Override
        default String[] methods() {return new String[0];}

        /**
         * Standard invoke function from {@link li.cil.oc.api.network.ManagedPeripheral} extending {@link li.cil.oc.api.network.SimpleComponent}.
         * @return Data to the computer as a return from the function.
         */
        @Override
        default Object[] invoke(String method, Context context, Arguments args) throws Exception {return null;}
    }
}
