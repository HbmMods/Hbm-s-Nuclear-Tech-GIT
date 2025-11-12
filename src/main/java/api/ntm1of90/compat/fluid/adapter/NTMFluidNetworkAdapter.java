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
 * Adapter class that makes NTM fluid tanks compatible with Forge's IFluidHandler interface.
 * This allows NTM tanks to interact with other mods' fluid systems.
 */
public class NTMFluidNetworkAdapter implements IFluidHandler {

    private final FluidTank ntmTank;
    private final TileEntity tileEntity;

    /**
     * Create a new adapter for an NTM fluid tank
     *
     * @param tank The NTM fluid tank to adapt
     * @param tile The tile entity that contains the tank (for marking dirty)
     */
    public NTMFluidNetworkAdapter(FluidTank tank, TileEntity tile) {
        this.ntmTank = tank;
        this.tileEntity = tile;
    }

    /**
     * Convert NTM fluid amount to Forge fluid amount (mB)
     */
    private int toForgeAmount(int ntmAmount) {
        return NTMForgeFluidConverter.toForgeAmount(ntmAmount);
    }

    /**
     * Convert Forge fluid amount (mB) to NTM fluid amount
     */
    private int toNTMAmount(int forgeAmount) {
        return NTMForgeFluidConverter.toNTMAmount(forgeAmount);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0) {
            return 0;
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
        if (ntmFluid == Fluids.NONE) {
            return 0; // Unknown fluid
        }

        // Calculate how much can be filled
        int ntmAmount = toNTMAmount(resource.amount);
        int currentFill = ntmTank.getFill();
        int maxFill = ntmTank.getMaxFill();
        FluidType currentType = ntmTank.getTankType();

        // Check if tank is empty or contains the same fluid
        if (currentFill > 0 && currentType != ntmFluid) {
            return 0; // Tank contains a different fluid
        }

        // Calculate how much can be filled
        int fillAmount = Math.min(ntmAmount, maxFill - currentFill);
        if (fillAmount <= 0) {
            return 0; // Tank is full
        }

        // Fill the tank
        if (doFill) {
            ntmTank.setTankType(ntmFluid);
            ntmTank.setFill(currentFill + fillAmount);
            if (tileEntity != null) {
                tileEntity.markDirty();
            }
        }

        // Return the amount filled in Forge units
        return toForgeAmount(fillAmount);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null) {
            return null;
        }

        // Check if the requested fluid matches the tank's fluid
        FluidType currentType = ntmTank.getTankType();
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);

        if (forgeFluid == null || !resource.getFluid().equals(forgeFluid)) {
            return null; // Not the requested fluid
        }

        // Drain the requested amount
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (maxDrain <= 0) {
            return null;
        }

        // Get the current tank state
        int currentFill = ntmTank.getFill();
        FluidType currentType = ntmTank.getTankType();

        if (currentFill <= 0 || currentType == Fluids.NONE) {
            return null; // Tank is empty
        }

        // Get the corresponding Forge fluid
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
        if (forgeFluid == null) {
            return null; // No Forge fluid mapping
        }

        // Calculate how much can be drained
        int ntmDrainAmount = toNTMAmount(maxDrain);
        int drainAmount = Math.min(ntmDrainAmount, currentFill);

        if (drainAmount <= 0) {
            return null; // Nothing to drain
        }

        // Create the fluid stack to return
        FluidStack result = new FluidStack(forgeFluid, toForgeAmount(drainAmount));

        // Drain the tank
        if (doDrain) {
            int newFill = currentFill - drainAmount;
            ntmTank.setFill(newFill);

            // If tank is now empty, reset the type
            if (newFill <= 0) {
                ntmTank.setTankType(Fluids.NONE);
            }

            if (tileEntity != null) {
                tileEntity.markDirty();
            }
        }

        return result;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (fluid == null) {
            return false;
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(fluid);
        if (ntmFluid == Fluids.NONE) {
            return false; // Unknown fluid
        }

        // Check if tank is empty or contains the same fluid
        FluidType currentType = ntmTank.getTankType();
        int currentFill = ntmTank.getFill();

        return currentFill <= 0 || currentType == ntmFluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (ntmTank.getFill() <= 0) {
            return false; // Tank is empty
        }

        // If no specific fluid is requested, can drain any fluid
        if (fluid == null) {
            return true;
        }

        // Check if the tank contains the requested fluid
        FluidType currentType = ntmTank.getTankType();
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);

        return forgeFluid != null && forgeFluid.equals(fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        // Get the current tank state
        int currentFill = ntmTank.getFill();
        int maxFill = ntmTank.getMaxFill();
        FluidType currentType = ntmTank.getTankType();

        // Create a fluid stack for the current contents
        FluidStack stack = null;
        if (currentFill > 0 && currentType != Fluids.NONE) {
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
            if (forgeFluid != null) {
                stack = new FluidStack(forgeFluid, toForgeAmount(currentFill));
            }
        }

        // Return the tank info
        return new FluidTankInfo[] { new FluidTankInfo(stack, toForgeAmount(maxFill)) };
    }
}
