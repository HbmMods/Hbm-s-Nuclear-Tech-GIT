package com.hbm.tileentity.machine.storage;

import api.ntm1of90.compat.FluidMappingRegistry;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A barrel that supports both HBM's Fluids System and Forge's fluid system (for compatibility with mods like Buildcraft)
 *
 * This class extends TileEntityBarrel and inherits all its functionality, including GUI and container.
 */
public class TileEntityBarrelForge extends TileEntityBarrel implements IFluidHandler {

    static {
        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();
    }

    public TileEntityBarrelForge() {
        super();
    }

    public TileEntityBarrelForge(int capacity) {
        super(capacity);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
    }

    // IFluidHandler implementation

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0) {
            return 0;
        }

        // Convert Forge fluid to HBM fluid
        FluidType hbmType = getHbmFluidType(resource.getFluid());
        if (hbmType == null || hbmType == Fluids.NONE) {
            return 0; // Unknown fluid type
        }

        // Check if we can accept this fluid
        if (tank.getTankType() != Fluids.NONE && tank.getTankType() != hbmType) {
            return 0; // Tank contains a different fluid
        }

        // Calculate how much we can accept
        int toFill = Math.min(resource.amount, tank.getMaxFill() - tank.getFill());
        if (toFill <= 0) {
            return 0; // Tank is full
        }

        if (doFill) {
            // Set the tank type if it's empty
            if (tank.getTankType() == Fluids.NONE) {
                tank.setTankType(hbmType);
            }

            // Fill the tank
            tank.setFill(tank.getFill() + toFill);
            this.markDirty();
        }

        return toFill;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0) {
            return null;
        }

        // Convert Forge fluid to HBM fluid
        FluidType hbmType = getHbmFluidType(resource.getFluid());
        if (hbmType == null || hbmType != tank.getTankType()) {
            return null; // Not the same fluid
        }

        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (maxDrain <= 0 || tank.getTankType() == Fluids.NONE || tank.getFill() <= 0) {
            return null;
        }

        // Get the Forge fluid for the current HBM fluid
        Fluid forgeFluid = getForgeFluid(tank.getTankType());
        if (forgeFluid == null) {
            return null; // Unknown fluid type
        }

        // Calculate how much we can drain
        int toDrain = Math.min(maxDrain, tank.getFill());
        if (toDrain <= 0) {
            return null;
        }

        FluidStack result = new FluidStack(forgeFluid, toDrain);

        if (doDrain) {
            // Drain the tank
            tank.setFill(tank.getFill() - toDrain);

            // Reset the tank type if it's empty
            if (tank.getFill() <= 0) {
                tank.setTankType(Fluids.NONE);
            }

            this.markDirty();
        }

        return result;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        // Check if we know this fluid type
        FluidType hbmType = getHbmFluidType(fluid);
        if (hbmType == null || hbmType == Fluids.NONE) {
            return false;
        }

        // Check if the tank is empty or contains the same fluid
        return tank.getTankType() == Fluids.NONE || tank.getTankType() == hbmType;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        // Check if the tank contains this fluid
        FluidType hbmType = getHbmFluidType(fluid);
        return hbmType != null && tank.getTankType() == hbmType && tank.getFill() > 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        FluidStack stack = null;

        if (tank.getTankType() != Fluids.NONE && tank.getFill() > 0) {
            Fluid forgeFluid = getForgeFluid(tank.getTankType());
            if (forgeFluid != null) {
                stack = new FluidStack(forgeFluid, tank.getFill());
            }
        }

        return new FluidTankInfo[] { new FluidTankInfo(stack, tank.getMaxFill()) };
    }

    /**
     * Convert a Forge Fluid to an HBM FluidType
     * Uses the FluidMappingRegistry to find the best matching fluid type
     */
    private FluidType getHbmFluidType(Fluid fluid) {
        return FluidMappingRegistry.getHbmFluidType(fluid);
    }

    /**
     * Convert an HBM FluidType to a Forge Fluid
     * Uses the FluidMappingRegistry to find the best matching Forge fluid
     */
    private Fluid getForgeFluid(FluidType type) {
        return FluidMappingRegistry.getForgeFluid(type);
    }
}
