package api.ntm1of90.compat.fluid.adapter;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;

import api.ntm1of90.compat.fluid.bridge.NTMFluidNetworkBridge;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A utility class for adapting NTM tile entities to work with Forge's fluid system.
 * This allows NTM machines to interact with other mods' fluid systems.
 */
public class NTMFluidTileAdapter {

    /**
     * Make an NTM tile entity compatible with Forge's fluid system
     *
     * @param tile The NTM tile entity to adapt
     * @param tank The fluid tank in the tile entity
     * @return An IFluidHandler that can be used by other mods
     */
    public static IFluidHandler createFluidHandler(TileEntity tile, FluidTank tank) {
        return new NTMFluidNetworkAdapter(tank, tile);
    }

    /**
     * Check if a tile entity is a Forge fluid handler
     *
     * @param tile The tile entity to check
     * @return True if the tile entity is a Forge fluid handler, false otherwise
     */
    public static boolean isForgeFluidHandler(TileEntity tile) {
        return tile instanceof IFluidHandler;
    }

    /**
     * Get the Forge fluid handler for a tile entity
     *
     * @param tile The tile entity to get the handler for
     * @return The fluid handler, or null if the tile entity is not a fluid handler
     */
    public static IFluidHandler getForgeFluidHandler(TileEntity tile) {
        if (tile instanceof IFluidHandler) {
            return (IFluidHandler) tile;
        }
        return null;
    }

    /**
     * Transfer fluid from an NTM machine to an adjacent Forge fluid handler
     *
     * @param machine The NTM machine to transfer from
     * @param tank The fluid tank in the machine
     * @param direction The direction to transfer in
     * @param maxAmount The maximum amount to transfer (in NTM units)
     * @return The amount transferred (in NTM units)
     */
    public static int transferToAdjacent(TileEntityMachineBase machine, FluidTank tank, ForgeDirection direction, int maxAmount) {
        if (machine == null || tank == null || maxAmount <= 0) {
            return 0;
        }

        // Get the adjacent tile entity
        TileEntity adjacent = machine.getWorldObj().getTileEntity(
            machine.xCoord + direction.offsetX,
            machine.yCoord + direction.offsetY,
            machine.zCoord + direction.offsetZ
        );

        if (!isForgeFluidHandler(adjacent)) {
            return 0; // Not a fluid handler
        }

        // Get the fluid handler
        IFluidHandler handler = getForgeFluidHandler(adjacent);

        // Transfer the fluid
        return NTMFluidNetworkBridge.transferToForge(tank, handler, direction, maxAmount);
    }

    /**
     * Transfer fluid from an adjacent Forge fluid handler to an NTM machine
     *
     * @param machine The NTM machine to transfer to
     * @param tank The fluid tank in the machine
     * @param direction The direction to transfer from
     * @param maxAmount The maximum amount to transfer (in NTM units)
     * @return The amount transferred (in NTM units)
     */
    public static int transferFromAdjacent(TileEntityMachineBase machine, FluidTank tank, ForgeDirection direction, int maxAmount) {
        if (machine == null || tank == null || maxAmount <= 0) {
            return 0;
        }

        // Get the adjacent tile entity
        TileEntity adjacent = machine.getWorldObj().getTileEntity(
            machine.xCoord + direction.offsetX,
            machine.yCoord + direction.offsetY,
            machine.zCoord + direction.offsetZ
        );

        if (!isForgeFluidHandler(adjacent)) {
            return 0; // Not a fluid handler
        }

        // Get the fluid handler
        IFluidHandler handler = getForgeFluidHandler(adjacent);

        // Transfer the fluid
        return NTMFluidNetworkBridge.transferFromForge(handler, tank, direction, maxAmount);
    }
}
