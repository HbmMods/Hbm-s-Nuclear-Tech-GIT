package api.ntm1of90.compat;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A bridge class that connects NTM's fluid network to Forge's fluid system.
 * This can be used to transfer fluids between NTM's network and other mods.
 */
public class NTMFluidNetworkBridge {
    
    /**
     * Transfer fluid from an NTM tank to a Forge fluid handler
     * 
     * @param ntmTank The NTM tank to transfer from
     * @param forgeHandler The Forge fluid handler to transfer to
     * @param direction The direction to transfer in (from the perspective of the Forge handler)
     * @param maxAmount The maximum amount to transfer (in NTM units)
     * @return The amount transferred (in NTM units)
     */
    public static int transferToForge(FluidTank ntmTank, IFluidHandler forgeHandler, ForgeDirection direction, int maxAmount) {
        if (ntmTank == null || forgeHandler == null || maxAmount <= 0) {
            return 0;
        }
        
        // Check if the NTM tank has fluid
        FluidType ntmFluidType = ntmTank.getTankType();
        int ntmFill = ntmTank.getFill();
        
        if (ntmFluidType == Fluids.NONE || ntmFill <= 0) {
            return 0; // No fluid to transfer
        }
        
        // Get the corresponding Forge fluid
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(ntmFluidType);
        if (forgeFluid == null) {
            return 0; // No Forge fluid mapping
        }
        
        // Calculate the amount to transfer
        int transferAmount = Math.min(maxAmount, ntmFill);
        int forgeAmount = NTMForgeFluidConverter.toForgeAmount(transferAmount);
        
        // Create a fluid stack for the transfer
        FluidStack stack = new FluidStack(forgeFluid, forgeAmount);
        
        // Try to fill the Forge handler
        int filledAmount = forgeHandler.fill(direction.getOpposite(), stack, true);
        
        if (filledAmount <= 0) {
            return 0; // Nothing was transferred
        }
        
        // Calculate the actual amount transferred in NTM units
        int ntmTransferred = NTMForgeFluidConverter.toNTMAmount(filledAmount);
        
        // Update the NTM tank
        int newFill = ntmFill - ntmTransferred;
        ntmTank.setFill(newFill);
        
        // If the tank is now empty, reset the type
        if (newFill <= 0) {
            ntmTank.setTankType(Fluids.NONE);
        }
        
        return ntmTransferred;
    }
    
    /**
     * Transfer fluid from a Forge fluid handler to an NTM tank
     * 
     * @param forgeHandler The Forge fluid handler to transfer from
     * @param ntmTank The NTM tank to transfer to
     * @param direction The direction to transfer in (from the perspective of the Forge handler)
     * @param maxAmount The maximum amount to transfer (in NTM units)
     * @return The amount transferred (in NTM units)
     */
    public static int transferFromForge(IFluidHandler forgeHandler, FluidTank ntmTank, ForgeDirection direction, int maxAmount) {
        if (forgeHandler == null || ntmTank == null || maxAmount <= 0) {
            return 0;
        }
        
        // Get the current state of the NTM tank
        FluidType currentType = ntmTank.getTankType();
        int currentFill = ntmTank.getFill();
        int maxFill = ntmTank.getMaxFill();
        
        // Calculate how much space is available
        int availableSpace = maxFill - currentFill;
        if (availableSpace <= 0) {
            return 0; // Tank is full
        }
        
        // Calculate the amount to transfer
        int transferAmount = Math.min(maxAmount, availableSpace);
        int forgeAmount = NTMForgeFluidConverter.toForgeAmount(transferAmount);
        
        // If the tank already has a fluid, try to drain that specific fluid
        FluidStack drainedStack = null;
        if (currentType != Fluids.NONE && currentFill > 0) {
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
            if (forgeFluid != null) {
                FluidStack requestStack = new FluidStack(forgeFluid, forgeAmount);
                drainedStack = forgeHandler.drain(direction.getOpposite(), requestStack, true);
            }
        } else {
            // Otherwise, drain any fluid
            drainedStack = forgeHandler.drain(direction.getOpposite(), forgeAmount, true);
        }
        
        if (drainedStack == null || drainedStack.amount <= 0) {
            return 0; // Nothing was drained
        }
        
        // Convert the drained fluid to NTM
        NTMForgeFluidConverter.FluidPair ntmFluid = NTMForgeFluidConverter.toNTMFluid(drainedStack);
        
        // Check if the fluid can be added to the tank
        if (ntmFluid.type == Fluids.NONE) {
            return 0; // Unknown fluid
        }
        
        if (currentFill > 0 && currentType != ntmFluid.type) {
            return 0; // Tank contains a different fluid
        }
        
        // Update the NTM tank
        ntmTank.setTankType(ntmFluid.type);
        ntmTank.setFill(currentFill + ntmFluid.amount);
        
        return ntmFluid.amount;
    }
    
    /**
     * Check if a Forge fluid handler can accept a specific NTM fluid
     * 
     * @param forgeHandler The Forge fluid handler to check
     * @param ntmFluid The NTM fluid type to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return True if the handler can accept the fluid, false otherwise
     */
    public static boolean canForgeHandlerAcceptFluid(IFluidHandler forgeHandler, FluidType ntmFluid, ForgeDirection direction) {
        if (forgeHandler == null || ntmFluid == Fluids.NONE) {
            return false;
        }
        
        // Get the corresponding Forge fluid
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(ntmFluid);
        if (forgeFluid == null) {
            return false; // No Forge fluid mapping
        }
        
        // Check if the handler can accept the fluid
        return forgeHandler.canFill(direction.getOpposite(), forgeFluid);
    }
    
    /**
     * Check if a Forge fluid handler can provide a specific NTM fluid
     * 
     * @param forgeHandler The Forge fluid handler to check
     * @param ntmFluid The NTM fluid type to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return True if the handler can provide the fluid, false otherwise
     */
    public static boolean canForgeHandlerProvideFluid(IFluidHandler forgeHandler, FluidType ntmFluid, ForgeDirection direction) {
        if (forgeHandler == null || ntmFluid == Fluids.NONE) {
            return false;
        }
        
        // Get the corresponding Forge fluid
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(ntmFluid);
        if (forgeFluid == null) {
            return false; // No Forge fluid mapping
        }
        
        // Check if the handler can provide the fluid
        return forgeHandler.canDrain(direction.getOpposite(), forgeFluid);
    }
    
    /**
     * Get information about the fluids in a Forge fluid handler
     * 
     * @param forgeHandler The Forge fluid handler to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return An array of NTM fluid pairs (type and amount)
     */
    public static NTMForgeFluidConverter.FluidPair[] getForgeHandlerFluids(IFluidHandler forgeHandler, ForgeDirection direction) {
        if (forgeHandler == null) {
            return new NTMForgeFluidConverter.FluidPair[0];
        }
        
        // Get the tank info from the handler
        FluidTankInfo[] tankInfo = forgeHandler.getTankInfo(direction.getOpposite());
        if (tankInfo == null || tankInfo.length == 0) {
            return new NTMForgeFluidConverter.FluidPair[0];
        }
        
        // Convert each tank's fluid to NTM
        NTMForgeFluidConverter.FluidPair[] result = new NTMForgeFluidConverter.FluidPair[tankInfo.length];
        for (int i = 0; i < tankInfo.length; i++) {
            FluidStack stack = tankInfo[i].fluid;
            if (stack != null && stack.amount > 0) {
                result[i] = NTMForgeFluidConverter.toNTMFluid(stack);
            } else {
                result[i] = new NTMForgeFluidConverter.FluidPair(Fluids.NONE, 0);
            }
        }
        
        return result;
    }
}
