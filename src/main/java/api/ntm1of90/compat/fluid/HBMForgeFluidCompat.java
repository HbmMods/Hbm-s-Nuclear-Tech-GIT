package api.ntm1of90.compat.fluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.fluidmk2.IFluidUserMK2;
import api.ntm1of90.compat.fluid.adapter.NTMFluidTileAdapter;
import api.ntm1of90.compat.fluid.bridge.NTMFluidNetworkBridge;
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
 * Utility class for adding Forge fluid compatibility to HBM tile entities.
 * This class provides methods for transferring fluids between HBM and Forge fluid systems.
 *
 * This class is used by the ForgeFluidCompatManager to make all HBM fluid tile entities compatible with Forge's fluid system.
 */
public class HBMForgeFluidCompat {

    static {
        // Initialize the ForgeFluidCompatManager
        ForgeFluidCompatManager.initialize();

        // Initialize the adapter registry
        ForgeFluidAdapterRegistry.initialize();
    }

    /**
     * Check if a tile entity is a Forge fluid handler
     * @param tile The tile entity to check
     * @return True if the tile entity is a Forge fluid handler, false otherwise
     */
    public static boolean isForgeFluidHandler(TileEntity tile) {
        if (tile == null) {
            return false;
        }

        // If the tile entity already implements IFluidHandler, return true
        if (tile instanceof IFluidHandler) {
            return true;
        }

        // If the tile entity implements IFluidUserMK2, it can be adapted to IFluidHandler
        if (tile instanceof IFluidUserMK2) {
            return true;
        }

        return false;
    }

    /**
     * Get the Forge fluid handler for a tile entity
     * @param tile The tile entity to get the handler for
     * @return The fluid handler, or null if the tile entity is not a fluid handler
     */
    public static IFluidHandler getForgeFluidHandler(TileEntity tile) {
        return ForgeFluidAdapterRegistry.getFluidHandler(tile);
    }

    /**
     * Transfer fluid from an HBM tank to a Forge fluid handler
     * @param hbmTank The HBM tank to transfer from
     * @param forgeHandler The Forge fluid handler to transfer to
     * @param direction The direction to transfer in (from the perspective of the Forge handler)
     * @param maxAmount The maximum amount to transfer
     * @return The amount transferred
     */
    public static int transferToForge(FluidTank hbmTank, IFluidHandler forgeHandler, ForgeDirection direction, int maxAmount) {
        return NTMFluidNetworkBridge.transferToForge(hbmTank, forgeHandler, direction, maxAmount);
    }

    /**
     * Transfer fluid from a Forge fluid handler to an HBM tank
     * @param forgeHandler The Forge fluid handler to transfer from
     * @param hbmTank The HBM tank to transfer to
     * @param direction The direction to transfer in (from the perspective of the Forge handler)
     * @param maxAmount The maximum amount to transfer
     * @return The amount transferred
     */
    public static int transferFromForge(IFluidHandler forgeHandler, FluidTank hbmTank, ForgeDirection direction, int maxAmount) {
        return NTMFluidNetworkBridge.transferFromForge(forgeHandler, hbmTank, direction, maxAmount);
    }

    /**
     * Check if a Forge fluid handler can accept a specific HBM fluid
     * @param forgeHandler The Forge fluid handler to check
     * @param hbmFluid The HBM fluid type to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return True if the handler can accept the fluid, false otherwise
     */
    public static boolean canForgeHandlerAcceptFluid(IFluidHandler forgeHandler, FluidType hbmFluid, ForgeDirection direction) {
        return NTMFluidNetworkBridge.canForgeHandlerAcceptFluid(forgeHandler, hbmFluid, direction);
    }

    /**
     * Check if a Forge fluid handler can provide a specific HBM fluid
     * @param forgeHandler The Forge fluid handler to check
     * @param hbmFluid The HBM fluid type to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return True if the handler can provide the fluid, false otherwise
     */
    public static boolean canForgeHandlerProvideFluid(IFluidHandler forgeHandler, FluidType hbmFluid, ForgeDirection direction) {
        return NTMFluidNetworkBridge.canForgeHandlerProvideFluid(forgeHandler, hbmFluid, direction);
    }

    /**
     * Create a Forge-compatible fluid handler for an HBM tile entity
     * @param tile The HBM tile entity
     * @param tank The HBM fluid tank
     * @return A Forge-compatible fluid handler
     */
    public static IFluidHandler createForgeFluidHandler(TileEntity tile, FluidTank tank) {
        return NTMFluidTileAdapter.createFluidHandler(tile, tank);
    }

    /**
     * Create a Forge-compatible fluid handler for an HBM tile entity with multiple tanks
     * @param tile The HBM tile entity
     * @param tanks The HBM fluid tanks
     * @return A Forge-compatible fluid handler
     */
    public static IFluidHandler createForgeFluidHandler(TileEntity tile, FluidTank[] tanks) {
        return new MultiTankForgeAdapter(tile, tanks);
    }

    /**
     * A Forge fluid handler adapter for HBM tile entities with multiple tanks
     */
    public static class MultiTankForgeAdapter implements IFluidHandler {
        private final TileEntity tile;
        private final FluidTank[] tanks;

        public MultiTankForgeAdapter(TileEntity tile, FluidTank[] tanks) {
            this.tile = tile;
            this.tanks = tanks;
        }

        @Override
        public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
            if (resource == null || resource.amount <= 0) {
                return 0;
            }

            // Convert Forge fluid to HBM fluid
            FluidType hbmFluid = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
            if (hbmFluid == Fluids.NONE) {
                return 0; // Unknown fluid
            }

            // Find a tank that can accept this fluid
            for (FluidTank tank : tanks) {
                // Check if tank is empty or contains the same fluid
                int currentFill = tank.getFill();
                FluidType currentType = tank.getTankType();
                int maxFill = tank.getMaxFill();

                if (currentFill < maxFill && (currentFill <= 0 || currentType == hbmFluid)) {
                    // Calculate how much can be filled
                    int ntmAmount = NTMForgeFluidConverter.toNTMAmount(resource.amount);
                    int fillAmount = Math.min(ntmAmount, maxFill - currentFill);

                    if (fillAmount <= 0) {
                        continue; // Tank is full
                    }

                    // Fill the tank
                    if (doFill) {
                        if (currentFill == 0) {
                            tank.setTankType(hbmFluid);
                        }
                        tank.setFill(currentFill + fillAmount);

                        // Mark the tile entity as dirty
                        if (tile != null) {
                            tile.markDirty();
                        }
                    }

                    return NTMForgeFluidConverter.toForgeAmount(fillAmount);
                }
            }

            return 0; // No tank could accept the fluid
        }

        @Override
        public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
            if (resource == null || resource.amount <= 0) {
                return null;
            }

            // Convert Forge fluid to HBM fluid
            FluidType hbmFluid = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
            if (hbmFluid == Fluids.NONE) {
                return null; // Unknown fluid
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
                int ntmAmount = NTMForgeFluidConverter.toNTMAmount(resource.amount);
                int drainAmount = Math.min(ntmAmount, currentFill);

                if (drainAmount <= 0) {
                    continue; // Nothing to drain
                }

                // Drain the tank
                if (doDrain) {
                    int newFill = currentFill - drainAmount;
                    tank.setFill(newFill);

                    // If the tank is now empty, reset the type
                    if (newFill <= 0) {
                        tank.setTankType(Fluids.NONE);
                    }

                    // Mark the tile entity as dirty
                    if (tile != null) {
                        tile.markDirty();
                    }
                }

                // Create a fluid stack for the drained fluid
                Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
                if (forgeFluid != null) {
                    return new FluidStack(forgeFluid, NTMForgeFluidConverter.toForgeAmount(drainAmount));
                }
            }

            return null; // No tank could provide the fluid
        }

        @Override
        public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
            if (maxDrain <= 0) {
                return null;
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
                int ntmDrainAmount = NTMForgeFluidConverter.toNTMAmount(maxDrain);
                int drainAmount = Math.min(ntmDrainAmount, currentFill);

                if (drainAmount <= 0) {
                    continue; // Nothing to drain
                }

                // Drain the tank
                if (doDrain) {
                    int newFill = currentFill - drainAmount;
                    tank.setFill(newFill);

                    // If the tank is now empty, reset the type
                    if (newFill <= 0) {
                        tank.setTankType(Fluids.NONE);
                    }

                    // Mark the tile entity as dirty
                    if (tile != null) {
                        tile.markDirty();
                    }
                }

                // Create a fluid stack for the drained fluid
                return new FluidStack(forgeFluid, NTMForgeFluidConverter.toForgeAmount(drainAmount));
            }

            return null; // No tank could provide fluid
        }

        @Override
        public boolean canFill(ForgeDirection from, Fluid fluid) {
            if (fluid == null) {
                return false;
            }

            // Convert Forge fluid to HBM fluid
            FluidType hbmFluid = FluidMappingRegistry.getHbmFluidType(fluid);
            if (hbmFluid == Fluids.NONE) {
                return false; // Unknown fluid
            }

            // Find a tank that can accept this fluid
            for (FluidTank tank : tanks) {
                // Check if tank is empty or contains the same fluid
                int currentFill = tank.getFill();
                FluidType currentType = tank.getTankType();
                int maxFill = tank.getMaxFill();

                if (currentFill < maxFill && (currentFill <= 0 || currentType == hbmFluid)) {
                    return true; // Tank can accept the fluid
                }
            }

            return false; // No tank can accept the fluid
        }

        @Override
        public boolean canDrain(ForgeDirection from, Fluid fluid) {
            if (fluid == null) {
                return false;
            }

            // Convert Forge fluid to HBM fluid
            FluidType hbmFluid = FluidMappingRegistry.getHbmFluidType(fluid);
            if (hbmFluid == Fluids.NONE) {
                return false; // Unknown fluid
            }

            // Find a tank that contains this fluid
            for (FluidTank tank : tanks) {
                // Check if tank contains the requested fluid
                int currentFill = tank.getFill();
                FluidType currentType = tank.getTankType();

                if (currentFill > 0 && currentType == hbmFluid) {
                    return true; // Tank contains the fluid
                }
            }

            return false; // No tank contains the fluid
        }

        @Override
        public FluidTankInfo[] getTankInfo(ForgeDirection from) {
            FluidTankInfo[] tankInfo = new FluidTankInfo[tanks.length];

            // Create tank info for each tank
            for (int i = 0; i < tanks.length; i++) {
                FluidTank tank = tanks[i];
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
    }
}
