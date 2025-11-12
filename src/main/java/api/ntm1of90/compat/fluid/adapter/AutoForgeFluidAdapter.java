package api.ntm1of90.compat.fluid.adapter;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardSenderMK2;
import api.hbm.fluidmk2.IFluidUserMK2;
import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import api.ntm1of90.compat.fluid.util.NTMForgeFluidConverter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * An automatic adapter that implements Forge's IFluidHandler interface for any HBM fluid tile entity.
 * This class is used to make all HBM fluid tile entities compatible with Forge's fluid system.
 */
public class AutoForgeFluidAdapter implements IFluidHandler {

    static {
        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();
    }

    private final IFluidUserMK2 fluidUser;
    private final TileEntity tileEntity;

    /**
     * Create a new adapter for an HBM fluid tile entity
     *
     * @param fluidUser The HBM fluid user to adapt
     * @param tileEntity The tile entity that contains the fluid user
     */
    public AutoForgeFluidAdapter(IFluidUserMK2 fluidUser, TileEntity tileEntity) {
        this.fluidUser = fluidUser;
        this.tileEntity = tileEntity;
    }

    /**
     * Convert NTM fluid amount to Forge fluid amount (mB)
     */
    private int toForgeAmount(int ntmAmount) {
        return NTMForgeFluidConverter.toForgeAmount(ntmAmount);
    }

    /**
     * Convert Forge fluid amount to NTM fluid amount
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

        // Check if this is a receiver
        if (fluidUser instanceof IFluidStandardReceiverMK2) {
            IFluidStandardReceiverMK2 receiver = (IFluidStandardReceiverMK2) fluidUser;

            // Get the receiving tanks
            FluidTank[] tanks = receiver.getReceivingTanks();
            if (tanks.length == 0) {
                return 0; // No tanks to fill
            }

            // Find a tank that can accept this fluid
            for (FluidTank tank : tanks) {
                // Check if tank is empty or contains the same fluid
                int currentFill = tank.getFill();
                FluidType currentType = tank.getTankType();
                int maxFill = tank.getMaxFill();

                if (currentFill < maxFill && (currentFill <= 0 || currentType == ntmFluid)) {
                    // Calculate how much can be filled
                    // Respect the tank's capacity
                    int ntmAmount = toNTMAmount(resource.amount);
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
                        if (tileEntity != null) {
                            tileEntity.markDirty();
                        }
                    }

                    return toForgeAmount(fillAmount);
                }
            }
        }

        return 0; // No tank could accept the fluid
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0) {
            return null;
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(resource.getFluid());
        if (ntmFluid == Fluids.NONE) {
            return null; // Unknown fluid
        }

        // Check if this is a sender
        if (fluidUser instanceof IFluidStandardSenderMK2) {
            IFluidStandardSenderMK2 sender = (IFluidStandardSenderMK2) fluidUser;

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

                if (currentFill <= 0 || currentType != ntmFluid) {
                    continue; // Tank is empty or contains a different fluid
                }

                // Calculate how much can be drained
                // Respect the tank's capacity
                int ntmAmount = toNTMAmount(resource.amount);
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
                    if (tileEntity != null) {
                        tileEntity.markDirty();
                    }
                }

                // Create a fluid stack for the drained fluid
                Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(currentType);
                if (forgeFluid != null) {
                    return new FluidStack(forgeFluid, toForgeAmount(drainAmount));
                }
            }
        }

        return null; // No tank could provide the fluid
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (maxDrain <= 0) {
            return null;
        }

        // Check if this is a sender
        if (fluidUser instanceof IFluidStandardSenderMK2) {
            IFluidStandardSenderMK2 sender = (IFluidStandardSenderMK2) fluidUser;

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
                int ntmDrainAmount = toNTMAmount(maxDrain);
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
                    if (tileEntity != null) {
                        tileEntity.markDirty();
                    }
                }

                // Create a fluid stack for the drained fluid
                return new FluidStack(forgeFluid, toForgeAmount(drainAmount));
            }
        }

        return null; // No tank could provide fluid
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

        // Check if this is a receiver
        if (fluidUser instanceof IFluidStandardReceiverMK2) {
            IFluidStandardReceiverMK2 receiver = (IFluidStandardReceiverMK2) fluidUser;

            // Get the receiving tanks
            FluidTank[] tanks = receiver.getReceivingTanks();
            if (tanks.length == 0) {
                return false; // No tanks to fill
            }

            // Find a tank that can accept this fluid
            for (FluidTank tank : tanks) {
                // Check if tank is empty or contains the same fluid
                int currentFill = tank.getFill();
                FluidType currentType = tank.getTankType();
                int maxFill = tank.getMaxFill();

                if (currentFill < maxFill && (currentFill <= 0 || currentType == ntmFluid)) {
                    return true; // Tank can accept the fluid
                }
            }
        }

        return false; // No tank can accept the fluid
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (fluid == null) {
            return false;
        }

        // Convert Forge fluid to NTM fluid
        FluidType ntmFluid = FluidMappingRegistry.getHbmFluidType(fluid);
        if (ntmFluid == Fluids.NONE) {
            return false; // Unknown fluid
        }

        // Check if this is a sender
        if (fluidUser instanceof IFluidStandardSenderMK2) {
            IFluidStandardSenderMK2 sender = (IFluidStandardSenderMK2) fluidUser;

            // Get the sending tanks
            FluidTank[] tanks = sender.getSendingTanks();
            if (tanks.length == 0) {
                return false; // No tanks to drain
            }

            // Find a tank that contains this fluid
            for (FluidTank tank : tanks) {
                // Check if tank contains the requested fluid
                int currentFill = tank.getFill();
                FluidType currentType = tank.getTankType();

                if (currentFill > 0 && currentType == ntmFluid) {
                    return true; // Tank contains the fluid
                }
            }
        }

        return false; // No tank contains the fluid
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        // Get all tanks
        FluidTank[] tanks = fluidUser.getAllTanks();
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
                    stack = new FluidStack(forgeFluid, toForgeAmount(currentFill));
                }
            }

            // Create the tank info
            tankInfo[i] = new FluidTankInfo(stack, toForgeAmount(maxFill));
        }

        return tankInfo;
    }
}
