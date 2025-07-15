package api.ntm1of90.compat.fluid.adapter;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import api.ntm1of90.compat.fluid.util.NTMForgeFluidConverter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A base adapter class that implements Forge's IFluidHandler interface for HBM tile entities.
 * This makes it easier to add Forge fluid compatibility to HBM tile entities.
 */
public abstract class ForgeFluidHandlerAdapter implements IFluidHandler {

    static {
        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();
    }

    /**
     * Get the HBM fluid tanks for this tile entity
     * @return An array of HBM fluid tanks
     */
    protected abstract FluidTank[] getHbmTanks();

    /**
     * Get the tile entity that contains the tanks
     * @return The tile entity
     */
    protected abstract TileEntity getTileEntity();

    /**
     * Check if the given direction is valid for fluid transfer
     * @param from The direction to check
     * @return True if the direction is valid, false otherwise
     */
    protected abstract boolean isValidDirection(ForgeDirection from);

    /**
     * Convert NTM fluid amount to Forge fluid amount (mB)
     */
    protected int toForgeAmount(int ntmAmount) {
        return NTMForgeFluidConverter.toForgeAmount(ntmAmount);
    }

    /**
     * Convert Forge fluid amount to NTM fluid amount
     */
    protected int toNTMAmount(int forgeAmount) {
        return NTMForgeFluidConverter.toNTMAmount(forgeAmount);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0 || !isValidDirection(from)) {
            return 0;
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
        if (ntmFluid == Fluids.NONE) {
            return 0; // Unknown fluid
        }

        // Find a tank that can accept this fluid
        FluidTank[] tanks = getHbmTanks();
        for (FluidTank tank : tanks) {
            // Check if tank is empty or contains the same fluid
            int currentFill = tank.getFill();
            FluidType currentType = tank.getTankType();

            if (currentFill > 0 && currentType != ntmFluid) {
                continue; // Tank contains a different fluid
            }

            // Calculate how much can be filled
            int ntmAmount = toNTMAmount(resource.amount);
            int maxFill = tank.getMaxFill();
            int fillAmount = Math.min(ntmAmount, maxFill - currentFill);

            if (fillAmount <= 0) {
                continue; // Tank is full
            }

            // Fill the tank
            if (doFill) {
                if (currentFill == 0) {
                    tank.setTankType(ntmFluid);
                }
                tank.setFill(currentFill + fillAmount);

                // Mark the tile entity as dirty
                TileEntity tile = getTileEntity();
                if (tile != null) {
                    tile.markDirty();
                }
            }

            return toForgeAmount(fillAmount);
        }

        return 0; // No tank could accept the fluid
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0 || !isValidDirection(from)) {
            return null;
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
        if (ntmFluid == Fluids.NONE) {
            return null; // Unknown fluid
        }

        // Find a tank that contains this fluid
        FluidTank[] tanks = getHbmTanks();
        for (FluidTank tank : tanks) {
            // Check if tank contains the requested fluid
            int currentFill = tank.getFill();
            FluidType currentType = tank.getTankType();

            if (currentFill <= 0 || currentType != ntmFluid) {
                continue; // Tank is empty or contains a different fluid
            }

            // Calculate how much can be drained
            int ntmAmount = toNTMAmount(resource.amount);
            int drainAmount = Math.min(ntmAmount, currentFill);

            if (drainAmount <= 0) {
                continue; // Nothing to drain
            }

            // Drain the tank
            if (doDrain) {
                int newFill = currentFill - drainAmount;
                tank.setFill(newFill);

                // We don't reset the tank type when it's empty anymore
                // This allows the tank to remember what fluid it contained

                // Mark the tile entity as dirty
                TileEntity tile = getTileEntity();
                if (tile != null) {
                    tile.markDirty();
                }
            }

            // Create a fluid stack for the drained fluid
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
            if (forgeFluid != null) {
                return new FluidStack(forgeFluid, toForgeAmount(drainAmount));
            }
        }

        return null; // No tank could provide the fluid
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (maxDrain <= 0 || !isValidDirection(from)) {
            return null;
        }

        // Find a tank that contains fluid
        FluidTank[] tanks = getHbmTanks();
        for (FluidTank tank : tanks) {
            // Check if tank contains fluid
            int currentFill = tank.getFill();
            FluidType currentType = tank.getTankType();

            if (currentFill <= 0 || currentType == Fluids.NONE) {
                continue; // Tank is empty
            }

            // Get the corresponding Forge fluid
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
            if (forgeFluid == null) {
                continue; // No Forge fluid mapping
            }

            // Calculate how much can be drained
            int ntmDrainAmount = toNTMAmount(maxDrain);
            int drainAmount = Math.min(ntmDrainAmount, currentFill);

            if (drainAmount <= 0) {
                continue; // Nothing to drain
            }

            // Drain the tank
            if (doDrain) {
                int newFill = currentFill - drainAmount;
                tank.setFill(newFill);

                // We don't reset the tank type when it's empty anymore
                // This allows the tank to remember what fluid it contained

                // Mark the tile entity as dirty
                TileEntity tile = getTileEntity();
                if (tile != null) {
                    tile.markDirty();
                }
            }

            // Create a fluid stack for the drained fluid
            return new FluidStack(forgeFluid, toForgeAmount(drainAmount));
        }

        return null; // No tank could provide fluid
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (fluid == null || !isValidDirection(from)) {
            return false;
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(fluid);
        if (ntmFluid == Fluids.NONE) {
            return false; // Unknown fluid
        }

        // Find a tank that can accept this fluid
        FluidTank[] tanks = getHbmTanks();
        for (FluidTank tank : tanks) {
            // Check if tank is empty or contains the same fluid
            int currentFill = tank.getFill();
            FluidType currentType = tank.getTankType();
            int maxFill = tank.getMaxFill();

            if (currentFill < maxFill && (currentFill <= 0 || currentType == ntmFluid)) {
                return true; // Tank can accept the fluid
            }
        }

        return false; // No tank can accept the fluid
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (fluid == null || !isValidDirection(from)) {
            return false;
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(fluid);
        if (ntmFluid == Fluids.NONE) {
            return false; // Unknown fluid
        }

        // Find a tank that contains this fluid
        FluidTank[] tanks = getHbmTanks();
        for (FluidTank tank : tanks) {
            // Check if tank contains the requested fluid
            int currentFill = tank.getFill();
            FluidType currentType = tank.getTankType();

            if (currentFill > 0 && currentType == ntmFluid) {
                return true; // Tank contains the fluid
            }
        }

        return false; // No tank contains the fluid
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (!isValidDirection(from)) {
            return new FluidTankInfo[0];
        }

        // Get the HBM tanks
        FluidTank[] hbmTanks = getHbmTanks();
        FluidTankInfo[] tankInfo = new FluidTankInfo[hbmTanks.length];

        // Create tank info for each tank
        for (int i = 0; i < hbmTanks.length; i++) {
            FluidTank tank = hbmTanks[i];
            int currentFill = tank.getFill();
            int maxFill = tank.getMaxFill();
            FluidType currentType = tank.getTankType();

            // Create a fluid stack for the current contents
            FluidStack stack = null;
            if (currentFill > 0 && currentType != Fluids.NONE) {
                Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
                if (forgeFluid != null) {
                    stack = new FluidStack(forgeFluid, toForgeAmount(currentFill));
                }
            }

            // Create the tank info
            tankInfo[i] = new FluidTankInfo(stack, toForgeAmount(maxFill));
        }

        return tankInfo;
    }
}
