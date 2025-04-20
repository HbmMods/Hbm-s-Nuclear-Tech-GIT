package api.ntm1of90.compat.fluid.bridge;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardSenderMK2;
import api.ntm1of90.compat.fluid.ForgeFluidCompatManager;
import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import api.ntm1of90.compat.fluid.util.NTMForgeFluidConverter;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A utility class for handling fluid interactions between NTM and other mods.
 * This class provides methods for transferring fluids between NTM and Forge fluid systems.
 */
public class NTMFluidInteractionHandler {

    /**
     * Transfer fluid from an NTM tile entity to a Forge fluid handler
     *
     * @param sender The NTM tile entity that sends fluid
     * @param receiver The Forge fluid handler that receives fluid
     * @param direction The direction to transfer in (from the perspective of the Forge handler)
     * @return The amount transferred
     */
    public static int transferFluid(IFluidStandardSenderMK2 sender, IFluidHandler receiver, ForgeDirection direction) {
        if (sender == null || receiver == null) {
            return 0;
        }

        // Get the sending tanks
        FluidTank[] tanks = sender.getSendingTanks();
        if (tanks.length == 0) {
            return 0; // No tanks to drain
        }

        int totalTransferred = 0;

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
            int maxDrain = Math.min(currentFill, ForgeFluidCompatManager.getDefaultForgeFlowRate());

            // Create a fluid stack for the fluid to transfer
            FluidStack stack = new FluidStack(forgeFluid, NTMForgeFluidConverter.toForgeAmount(maxDrain));

            // Try to fill the receiver
            int filled = receiver.fill(direction.getOpposite(), stack, true);

            if (filled <= 0) {
                continue; // Receiver couldn't accept any fluid
            }

            // Calculate how much was actually transferred in NTM units
            int ntmTransferred = NTMForgeFluidConverter.toNTMAmount(filled);

            // Drain the tank
            int newFill = currentFill - ntmTransferred;
            tank.setFill(newFill);

            // We don't reset the tank type when it's empty anymore
            // This allows the tank to remember what fluid it contained

            // Add to the total transferred
            totalTransferred += ntmTransferred;

            // Only transfer from one tank per operation
            break;
        }

        return totalTransferred;
    }

    /**
     * Transfer fluid from a Forge fluid handler to an NTM tile entity
     *
     * @param sender The Forge fluid handler that sends fluid
     * @param receiver The NTM tile entity that receives fluid
     * @param direction The direction to transfer in (from the perspective of the Forge handler)
     * @return The amount transferred
     */
    public static int transferFluid(IFluidHandler sender, IFluidStandardReceiverMK2 receiver, ForgeDirection direction) {
        if (sender == null || receiver == null) {
            return 0;
        }

        // Get the receiving tanks
        FluidTank[] tanks = receiver.getReceivingTanks();
        if (tanks.length == 0) {
            return 0; // No tanks to fill
        }

        int totalTransferred = 0;

        // Try to drain from the sender
        FluidStack drained = sender.drain(direction.getOpposite(), ForgeFluidCompatManager.getDefaultForgeFlowRate(), false);
        if (drained == null || drained.amount <= 0) {
            return 0; // Nothing to drain
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(drained.getFluid());
        if (ntmFluid == Fluids.NONE) {
            return 0; // Unknown fluid
        }

        // Find a tank that can accept this fluid
        for (FluidTank tank : tanks) {
            // Check if tank is empty or contains the same fluid
            int currentFill = tank.getFill();
            FluidType currentType = tank.getTankType();
            int maxFill = tank.getMaxFill();

            if (currentFill < maxFill && (currentFill <= 0 || currentType == ntmFluid)) {
                // Calculate how much can be filled
                int ntmAmount = NTMForgeFluidConverter.toNTMAmount(drained.amount);
                int fillAmount = Math.min(ntmAmount, maxFill - currentFill);

                if (fillAmount <= 0) {
                    continue; // Tank is full
                }

                // Actually drain from the sender
                FluidStack actuallyDrained = sender.drain(direction.getOpposite(),
                        NTMForgeFluidConverter.toForgeAmount(fillAmount), true);

                if (actuallyDrained == null || actuallyDrained.amount <= 0) {
                    continue; // Nothing was actually drained
                }

                // Calculate how much was actually transferred in NTM units
                int ntmTransferred = NTMForgeFluidConverter.toNTMAmount(actuallyDrained.amount);

                // Fill the tank
                if (currentFill == 0) {
                    tank.setTankType(ntmFluid);
                }
                tank.setFill(currentFill + ntmTransferred);

                // Add to the total transferred
                totalTransferred += ntmTransferred;

                // Only transfer to one tank per operation
                break;
            }
        }

        return totalTransferred;
    }

    /**
     * Check if a Forge fluid handler can accept a specific NTM fluid
     *
     * @param handler The Forge fluid handler to check
     * @param fluid The NTM fluid type to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return True if the handler can accept the fluid, false otherwise
     */
    public static boolean canAcceptFluid(IFluidHandler handler, FluidType fluid, ForgeDirection direction) {
        if (handler == null || fluid == Fluids.NONE) {
            return false;
        }

        // Get the corresponding Forge fluid
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(fluid);
        if (forgeFluid == null) {
            return false; // No Forge fluid mapping
        }

        // Check if the handler can fill this fluid
        return handler.canFill(direction.getOpposite(), forgeFluid);
    }

    /**
     * Check if a Forge fluid handler can provide a specific NTM fluid
     *
     * @param handler The Forge fluid handler to check
     * @param fluid The NTM fluid type to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return True if the handler can provide the fluid, false otherwise
     */
    public static boolean canProvideFluid(IFluidHandler handler, FluidType fluid, ForgeDirection direction) {
        if (handler == null || fluid == Fluids.NONE) {
            return false;
        }

        // Get the corresponding Forge fluid
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(fluid);
        if (forgeFluid == null) {
            return false; // No Forge fluid mapping
        }

        // Check if the handler can drain this fluid
        return handler.canDrain(direction.getOpposite(), forgeFluid);
    }
}
