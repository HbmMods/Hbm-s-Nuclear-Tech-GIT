package api.ntm1of90.compat.fluid.bridge;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.fluidmk2.IFluidUserMK2;
import api.hbm.fluidmk2.IFluidReceiverMK2;
import api.ntm1of90.compat.fluid.ForgeFluidCompatManager;
import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import api.ntm1of90.compat.fluid.registry.ForgeFluidAdapterRegistry;
import api.ntm1of90.compat.fluid.util.NTMForgeFluidConverter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A bridge class that connects NTM's fluid network to Forge's fluid system.
 * This can be used to transfer fluids between NTM's network and other mods.
 *
 * This class is used by the ForgeFluidCompatManager to make all HBM fluid tile entities compatible with Forge's fluid system.
 */
public class NTMFluidNetworkBridge {

    static {
        // Initialize the ForgeFluidCompatManager
        ForgeFluidCompatManager.initialize();

        // Initialize the adapter registry
        ForgeFluidAdapterRegistry.initialize();
    }

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
        // Respect the tank's capacity and the provider's speed
        int transferAmount = Math.min(maxAmount, ntmFill);
        // No need to limit by ForgeFluidCompatManager.getDefaultForgeFlowRate() as we're respecting the tank's settings
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

        // We don't reset the tank type when it's empty anymore
        // This allows the tank to remember what fluid it contained

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
        // Respect the tank's capacity
        int transferAmount = Math.min(maxAmount, availableSpace);
        // No need to limit by ForgeFluidCompatManager.getDefaultForgeFlowRate() as we're respecting the tank's settings
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
     * Check if a tile entity can accept a specific NTM fluid
     *
     * @param tileEntity The tile entity to check
     * @param ntmFluid The NTM fluid type to check
     * @param direction The direction to check (from the perspective of the tile entity)
     * @return True if the tile entity can accept the fluid, false otherwise
     */
    public static boolean canTileEntityAcceptFluid(TileEntity tileEntity, FluidType ntmFluid, ForgeDirection direction) {
        if (tileEntity == null || ntmFluid == Fluids.NONE) {
            return false;
        }

        // If the tile entity is a Forge fluid handler, check if it can accept the fluid
        if (tileEntity instanceof IFluidHandler) {
            return canForgeHandlerAcceptFluid((IFluidHandler) tileEntity, ntmFluid, direction);
        }

        // If the tile entity is an HBM fluid user, check if it can accept the fluid
        if (tileEntity instanceof IFluidUserMK2) {
            IFluidHandler handler = ForgeFluidAdapterRegistry.getFluidHandler(tileEntity);
            if (handler != null) {
                return canForgeHandlerAcceptFluid(handler, ntmFluid, direction);
            }
        }

        return false;
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
     * Check if a tile entity can provide a specific NTM fluid
     *
     * @param tileEntity The tile entity to check
     * @param ntmFluid The NTM fluid type to check
     * @param direction The direction to check (from the perspective of the tile entity)
     * @return True if the tile entity can provide the fluid, false otherwise
     */
    public static boolean canTileEntityProvideFluid(TileEntity tileEntity, FluidType ntmFluid, ForgeDirection direction) {
        if (tileEntity == null || ntmFluid == Fluids.NONE) {
            return false;
        }

        // If the tile entity is a Forge fluid handler, check if it can provide the fluid
        if (tileEntity instanceof IFluidHandler) {
            return canForgeHandlerProvideFluid((IFluidHandler) tileEntity, ntmFluid, direction);
        }

        // If the tile entity is an HBM fluid user, check if it can provide the fluid
        if (tileEntity instanceof IFluidUserMK2) {
            IFluidHandler handler = ForgeFluidAdapterRegistry.getFluidHandler(tileEntity);
            if (handler != null) {
                return canForgeHandlerProvideFluid(handler, ntmFluid, direction);
            }
        }

        return false;
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

    /**
     * Get information about the fluids in a tile entity
     *
     * @param tileEntity The tile entity to check
     * @param direction The direction to check (from the perspective of the tile entity)
     * @return An array of NTM fluid pairs (type and amount)
     */
    public static NTMForgeFluidConverter.FluidPair[] getTileEntityFluids(TileEntity tileEntity, ForgeDirection direction) {
        if (tileEntity == null) {
            return new NTMForgeFluidConverter.FluidPair[0];
        }

        // If the tile entity is a Forge fluid handler, get its fluids
        if (tileEntity instanceof IFluidHandler) {
            return getForgeHandlerFluids((IFluidHandler) tileEntity, direction);
        }

        // If the tile entity is an HBM fluid user, get its fluids
        if (tileEntity instanceof IFluidUserMK2) {
            IFluidHandler handler = ForgeFluidAdapterRegistry.getFluidHandler(tileEntity);
            if (handler != null) {
                return getForgeHandlerFluids(handler, direction);
            }
        }

        return new NTMForgeFluidConverter.FluidPair[0];
    }

    /**
     * Get tank information for an HBM fluid user
     *
     * @param fluidUser The HBM fluid user to get information for
     * @return An array of Forge fluid tank information
     */
    public static FluidTankInfo[] getTankInfo(IFluidUserMK2 fluidUser) {
        if (fluidUser == null) {
            return new FluidTankInfo[0];
        }

        // Get the HBM tanks
        FluidTank[] hbmTanks = fluidUser.getAllTanks();
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
                    stack = new FluidStack(forgeFluid, NTMForgeFluidConverter.toForgeAmount(currentFill));
                }
            }

            // Create the tank info
            tankInfo[i] = new FluidTankInfo(stack, NTMForgeFluidConverter.toForgeAmount(maxFill));
        }

        return tankInfo;
    }

    /**
     * Fill an HBM fluid receiver with a Forge fluid
     *
     * @param receiver The HBM fluid receiver to fill
     * @param resource The Forge fluid to fill with
     * @param doFill Whether to actually fill the tank
     * @return The amount of fluid that was filled
     */
    public static int fillFromForge(IFluidReceiverMK2 receiver, FluidStack resource, boolean doFill) {
        if (receiver == null || resource == null || resource.amount <= 0) {
            return 0;
        }

        // Convert Forge fluid to HBM fluid
        FluidType hbmFluid = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
        if (hbmFluid == Fluids.NONE) {
            return 0; // Unknown fluid
        }

        // Get the demand for this fluid
        long demand = receiver.getDemand(hbmFluid, 0);
        if (demand <= 0) {
            return 0; // No demand
        }

        // Calculate how much can be filled
        // Respect the receiver's demand and speed
        int hbmAmount = NTMForgeFluidConverter.toNTMAmount(resource.amount);
        long fillAmount = Math.min(hbmAmount, demand);
        // Respect the receiver's speed
        fillAmount = Math.min(fillAmount, receiver.getReceiverSpeed(hbmFluid, 0));

        if (fillAmount <= 0) {
            return 0; // Nothing to fill
        }

        // Fill the tank
        if (doFill) {
            long remaining = receiver.transferFluid(hbmFluid, 0, fillAmount);
            fillAmount -= remaining;
        }

        return NTMForgeFluidConverter.toForgeAmount((int) fillAmount);
    }

    /**
     * Drain an HBM fluid sender to a Forge fluid
     *
     * @param sender The HBM fluid sender to drain
     * @param resource The Forge fluid to drain
     * @param doDrain Whether to actually drain the tank
     * @return The fluid that was drained
     */
    public static FluidStack drainToForge(api.hbm.fluidmk2.IFluidStandardSenderMK2 sender, FluidStack resource, boolean doDrain) {
        if (sender == null || resource == null || resource.amount <= 0) {
            return null;
        }

        // Convert Forge fluid to HBM fluid
        FluidType hbmFluid = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
        if (hbmFluid == Fluids.NONE) {
            return null; // Unknown fluid
        }

        // Get the sending tanks
        FluidTank[] tanks = sender.getSendingTanks();
        if (tanks.length == 0) {
            return null; // No tanks to drain
        }

        // Find a tank that contains this fluid
        for (FluidTank tank : tanks) {
            // Check if tank contains the requested fluid
            int currentFill = tank.getFill();
            FluidType currentType = tank.getTankType();

            if (currentFill <= 0 || currentType != hbmFluid) {
                continue; // Tank is empty or contains a different fluid
            }

            // Calculate how much can be drained
            // Respect the tank's capacity
            int hbmAmount = NTMForgeFluidConverter.toNTMAmount(resource.amount);
            int drainAmount = Math.min(hbmAmount, currentFill);
            // Respect the sender's speed if it's an IFluidProviderMK2
            if (sender instanceof api.hbm.fluidmk2.IFluidProviderMK2) {
                api.hbm.fluidmk2.IFluidProviderMK2 provider = (api.hbm.fluidmk2.IFluidProviderMK2) sender;
                drainAmount = (int) Math.min(drainAmount, provider.getProviderSpeed(hbmFluid, 0));
            }

            if (drainAmount <= 0) {
                continue; // Nothing to drain
            }

            // Drain the tank
            if (doDrain) {
                tank.setFill(currentFill - drainAmount);

                // We don't reset the tank type when it's empty anymore
                // This allows the tank to remember what fluid it contained
            }

            // Create a fluid stack for the drained fluid
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
            if (forgeFluid != null) {
                return new FluidStack(forgeFluid, NTMForgeFluidConverter.toForgeAmount(drainAmount));
            }
        }

        return null; // No tank could provide the fluid
    }

    /**
     * Drain an HBM fluid sender to a Forge fluid
     *
     * @param sender The HBM fluid sender to drain
     * @param maxDrain The maximum amount to drain
     * @param doDrain Whether to actually drain the tank
     * @return The fluid that was drained
     */
    public static FluidStack drainToForge(api.hbm.fluidmk2.IFluidStandardSenderMK2 sender, int maxDrain, boolean doDrain) {
        if (sender == null || maxDrain <= 0) {
            return null;
        }

        // Get the sending tanks
        FluidTank[] tanks = sender.getSendingTanks();
        if (tanks.length == 0) {
            return null; // No tanks to drain
        }

        // Find a tank that contains fluid
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
            // Respect the tank's capacity
            int hbmDrainAmount = NTMForgeFluidConverter.toNTMAmount(maxDrain);
            int drainAmount = Math.min(hbmDrainAmount, currentFill);
            // Respect the sender's speed if it's an IFluidProviderMK2
            if (sender instanceof api.hbm.fluidmk2.IFluidProviderMK2) {
                api.hbm.fluidmk2.IFluidProviderMK2 provider = (api.hbm.fluidmk2.IFluidProviderMK2) sender;
                drainAmount = (int) Math.min(drainAmount, provider.getProviderSpeed(currentType, 0));
            }

            if (drainAmount <= 0) {
                continue; // Nothing to drain
            }

            // Drain the tank
            if (doDrain) {
                tank.setFill(currentFill - drainAmount);

                // If the tank is now empty, reset the type
                if (tank.getFill() <= 0) {
                    tank.setTankType(Fluids.NONE);
                }
            }

            // Create a fluid stack for the drained fluid
            return new FluidStack(forgeFluid, NTMForgeFluidConverter.toForgeAmount(drainAmount));
        }

        return null; // No tank could provide fluid
    }
}
