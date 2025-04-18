package com.hbm.tileentity;

import api.hbm.fluidmk2.IFluidConnectorMK2;
import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A proxy tile entity that supports both HBM's Fluids System and Forge's fluid system
 * This is used for the multiblock structure of the BAT9000Forge
 */
public class TileEntityProxyForge extends TileEntityProxyCombo implements IFluidHandler {

    static {
        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();
    }

    public TileEntityProxyForge() {
        super();
    }

    public TileEntityProxyForge(boolean inventory, boolean power, boolean fluid) {
        super(inventory, power, fluid);
    }

    // IFluidHandler implementation

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (!fluid || resource == null || resource.amount <= 0) {
            return 0;
        }

        TileEntity te = getTile();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).fill(from, resource, doFill);
        }

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (!fluid || resource == null || resource.amount <= 0) {
            return null;
        }

        TileEntity te = getTile();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).drain(from, resource, doDrain);
        }

        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (!fluid || maxDrain <= 0) {
            return null;
        }

        TileEntity te = getTile();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).drain(from, maxDrain, doDrain);
        }

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (!this.fluid) {
            return false;
        }

        TileEntity te = getTile();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).canFill(from, fluid);
        }

        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (!this.fluid) {
            return false;
        }

        TileEntity te = getTile();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).canDrain(from, fluid);
        }

        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (!fluid) {
            return new FluidTankInfo[0];
        }

        TileEntity te = getTile();
        if (te instanceof IFluidHandler) {
            return ((IFluidHandler) te).getTankInfo(from);
        }

        return new FluidTankInfo[0];
    }

    // Additional methods for compatibility with various mods

    public boolean canConnectFluid(ForgeDirection from) {
        return fluid;
    }

    public boolean isConnectable(ForgeDirection from) {
        return fluid;
    }

    public boolean canInterface(ForgeDirection from) {
        return fluid;
    }

    public boolean canInputFluid(ForgeDirection from) {
        return fluid;
    }

    public boolean canOutputFluid(ForgeDirection from) {
        return fluid;
    }

    public boolean canReceiveFrom(ForgeDirection from) {
        return fluid;
    }

    public boolean canSendTo(ForgeDirection from) {
        return fluid;
    }

    public boolean canAcceptFluid(ForgeDirection from) {
        return fluid;
    }

    public boolean canProvideFluid(ForgeDirection from) {
        return fluid;
    }

    public boolean isFluidHandler(ForgeDirection from) {
        return fluid;
    }
}
