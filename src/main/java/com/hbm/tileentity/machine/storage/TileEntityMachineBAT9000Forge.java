package com.hbm.tileentity.machine.storage;

import api.ntm1of90.compat.FluidMappingRegistry;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A Big Ass Tank that supports both HBM's Fluids System and Forge's fluid system (for compatibility with mods like Thermal Dynamics)
 *
 * This class extends TileEntityMachineBAT9000 and inherits all its functionality, including GUI and container.
 *
 *
 * The tank respects the mode settings from the GUI:
 * - Mode 0: Input only
 * - Mode 1: Input/Output
 * - Mode 2: Output only
 * - Mode 3: Disabled
 */
public class TileEntityMachineBAT9000Forge extends TileEntityMachineBAT9000 implements IFluidHandler {

    static {
        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();
    }

    public TileEntityMachineBAT9000Forge() {
        super();
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
        // Check if the tank is in a mode that allows filling (mode 0 = input only, mode 1 = input/output)
        if (mode != 0 && mode != 1) {
            return 0; // Not in input mode
        }

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
        // Check if the tank is in a mode that allows draining (mode 1 = input/output, mode 2 = output only)
        if (mode != 1 && mode != 2) {
            return null; // Not in output mode
        }

        if (maxDrain <= 0 || tank.getTankType() == Fluids.NONE || tank.getFill() <= 0) {
            return null;
        }

        // Get the Forge fluid for the current HBM fluid
        Fluid forgeFluid = getForgeFluid(tank.getTankType());
        if (forgeFluid == null) {
            return null; // Unknown fluid type
        }

        int increasedMaxDrain = (int)(maxDrain * 3.0);
        int toDrain = Math.min(increasedMaxDrain, tank.getFill());
        if (toDrain <= 0) {
            return null;
        }

        // Limit the result to the original maxDrain to avoid returning more than requested
        int resultAmount = Math.min(toDrain, maxDrain);
        FluidStack result = new FluidStack(forgeFluid, resultAmount);

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
        // Check if the tank is in a mode that allows filling (mode 0 = input only, mode 1 = input/output)
        if (mode != 0 && mode != 1) {
            return false; // Not in input mode
        }

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
        // Check if the tank is in a mode that allows draining (mode 1 = input/output, mode 2 = output only)
        if (mode != 1 && mode != 2) {
            return false; // Not in output mode
        }

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

    // Override the method from TileEntityBarrel to ensure it returns true for all directions
    @Override
    public boolean canConnect(FluidType fluid, ForgeDirection dir) {
        return true;
    }

    // Override these methods to ensure HBM's fluid pipes can connect properly
    @Override
    public FluidTank[] getSendingTanks() {
        return super.getSendingTanks(); // This calls the parent method which checks the mode
    }

    @Override
    public FluidTank[] getReceivingTanks() {
        return super.getReceivingTanks(); // This calls the parent method which checks the mode
    }

    /**
     * This allows the Big Ass Tank Forge to extract fluids faster when connected to Forge fluid pipes
     * Also respects the mode settings from the GUI
     */
    @Override
    public long getProviderSpeed(FluidType type, int pressure) {
        // Check if the tank is in a mode that allows output (mode 1 = input/output, mode 2 = output only)
        if (mode != 1 && mode != 2) {
            return 0; // Not in output mode
        }

        return (long)(super.getProviderSpeed(type, pressure) * 3.0);
    }

    // Methods for compatibility with various mods that use Forge fluid system

    /**
     * Method used by BuildCraft pipes
     */
    public boolean canConnectToForge(ForgeDirection dir) {
        return true;
    }

    /**
     * Method used by some mods that implement the Forge fluid system
     */
    public boolean canConnectFluid(ForgeDirection from) {
        return true;
    }

    /**
     * Method used by Thermal Dynamics pipes
     */
    public boolean isConnectable(ForgeDirection from) {
        return true;
    }

    /**
     * Method used by Thermal Expansion and some other mods
     */
    public boolean canInterface(ForgeDirection from) {
        return true;
    }

    /**
     * Method used by EnderIO conduits
     */
    public boolean canInputFluid(ForgeDirection from) {
        // Check if the tank is in a mode that allows filling (mode 0 = input only, mode 1 = input/output)
        return mode == 0 || mode == 1;
    }

    /**
     * Method used by EnderIO conduits
     */
    public boolean canOutputFluid(ForgeDirection from) {
        // Check if the tank is in a mode that allows draining (mode 1 = input/output, mode 2 = output only)
        return mode == 1 || mode == 2;
    }

    /**
     * Method used by Mekanism pipes
     */
    public boolean canReceiveFrom(ForgeDirection from) {
        // Check if the tank is in a mode that allows filling (mode 0 = input only, mode 1 = input/output)
        return mode == 0 || mode == 1;
    }

    /**
     * Method used by Mekanism pipes
     */
    public boolean canSendTo(ForgeDirection from) {
        // Check if the tank is in a mode that allows draining (mode 1 = input/output, mode 2 = output only)
        return mode == 1 || mode == 2;
    }

    /**
     * Method used by BuildCraft and some other mods
     */
    public boolean canConnectEnergy(ForgeDirection from) {
        return false; // This is not an energy handler
    }

    /**
     * Method used by some mods to check if a tile entity can connect to fluid pipes
     */
    public boolean canAcceptFluid(ForgeDirection from) {
        // Check if the tank is in a mode that allows filling (mode 0 = input only, mode 1 = input/output)
        return mode == 0 || mode == 1;
    }

    /**
     * Method used by some mods to check if a tile entity can connect to fluid pipes
     */
    public boolean canProvideFluid(ForgeDirection from) {
        // Check if the tank is in a mode that allows draining (mode 1 = input/output, mode 2 = output only)
        return mode == 1 || mode == 2;
    }

    /**
     * Method used by some mods to check if a tile entity can connect to fluid pipes
     */
    public boolean isFluidHandler(ForgeDirection from) {
        return true;
    }
}
