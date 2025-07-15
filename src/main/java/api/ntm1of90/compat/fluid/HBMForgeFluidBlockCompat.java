package api.ntm1of90.compat.fluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;

import api.ntm1of90.compat.fluid.bridge.NTMFluidNetworkBridge;
import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Utility class for adding Forge fluid compatibility to HBM blocks.
 * This class provides methods for transferring fluids between HBM and Forge fluid systems.
 */
public class HBMForgeFluidBlockCompat {

    /**
     * Check if a block is a Forge fluid block
     * @param block The block to check
     * @return True if the block is a Forge fluid block, false otherwise
     */
    public static boolean isForgeFluidBlock(Block block) {
        return block instanceof IFluidBlock;
    }

    /**
     * Get the Forge fluid from a Forge fluid block
     * @param block The block to get the fluid from
     * @return The fluid, or null if the block is not a fluid block
     */
    public static Fluid getForgeFluid(Block block) {
        if (block instanceof IFluidBlock) {
            return ((IFluidBlock) block).getFluid();
        }
        return null;
    }

    /**
     * Get the HBM fluid type from a Forge fluid block
     * @param block The block to get the fluid type from
     * @return The fluid type, or Fluids.NONE if the block is not a fluid block or the fluid is not mapped
     */
    public static FluidType getHbmFluidType(Block block) {
        Fluid fluid = getForgeFluid(block);
        if (fluid != null) {
            return FluidMappingRegistry.getHbmFluidType(fluid);
        }
        return Fluids.NONE;
    }

    /**
     * Check if a block is a Forge fluid handler
     * @param world The world
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return True if the block is a Forge fluid handler, false otherwise
     */
    public static boolean isForgeFluidHandler(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return HBMForgeFluidCompat.isForgeFluidHandler(tile);
    }

    /**
     * Get the Forge fluid handler for a block
     * @param world The world
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The fluid handler, or null if the block is not a fluid handler
     */
    public static IFluidHandler getForgeFluidHandler(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return HBMForgeFluidCompat.getForgeFluidHandler(tile);
    }

    /**
     * Transfer fluid from an HBM tank to a Forge fluid handler block
     * @param hbmTank The HBM tank to transfer from
     * @param world The world
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param direction The direction to transfer in (from the perspective of the Forge handler)
     * @param maxAmount The maximum amount to transfer
     * @return The amount transferred
     */
    public static int transferToForgeBlock(FluidTank hbmTank, World world, int x, int y, int z, ForgeDirection direction, int maxAmount) {
        IFluidHandler forgeHandler = getForgeFluidHandler(world, x, y, z);
        if (forgeHandler != null) {
            return NTMFluidNetworkBridge.transferToForge(hbmTank, forgeHandler, direction, maxAmount);
        }
        return 0;
    }

    /**
     * Transfer fluid from a Forge fluid handler block to an HBM tank
     * @param world The world
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param hbmTank The HBM tank to transfer to
     * @param direction The direction to transfer in (from the perspective of the Forge handler)
     * @param maxAmount The maximum amount to transfer
     * @return The amount transferred
     */
    public static int transferFromForgeBlock(World world, int x, int y, int z, FluidTank hbmTank, ForgeDirection direction, int maxAmount) {
        IFluidHandler forgeHandler = getForgeFluidHandler(world, x, y, z);
        if (forgeHandler != null) {
            return NTMFluidNetworkBridge.transferFromForge(forgeHandler, hbmTank, direction, maxAmount);
        }
        return 0;
    }

    /**
     * Check if a Forge fluid handler block can accept a specific HBM fluid
     * @param world The world
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param hbmFluid The HBM fluid type to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return True if the handler can accept the fluid, false otherwise
     */
    public static boolean canForgeBlockAcceptFluid(IBlockAccess world, int x, int y, int z, FluidType hbmFluid, ForgeDirection direction) {
        IFluidHandler forgeHandler = getForgeFluidHandler(world, x, y, z);
        if (forgeHandler != null) {
            return NTMFluidNetworkBridge.canForgeHandlerAcceptFluid(forgeHandler, hbmFluid, direction);
        }
        return false;
    }

    /**
     * Check if a Forge fluid handler block can provide a specific HBM fluid
     * @param world The world
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param hbmFluid The HBM fluid type to check
     * @param direction The direction to check (from the perspective of the Forge handler)
     * @return True if the handler can provide the fluid, false otherwise
     */
    public static boolean canForgeBlockProvideFluid(IBlockAccess world, int x, int y, int z, FluidType hbmFluid, ForgeDirection direction) {
        IFluidHandler forgeHandler = getForgeFluidHandler(world, x, y, z);
        if (forgeHandler != null) {
            return NTMFluidNetworkBridge.canForgeHandlerProvideFluid(forgeHandler, hbmFluid, direction);
        }
        return false;
    }

    /**
     * Create a Forge-compatible fluid handler for an HBM block
     * @param world The world
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param tank The HBM fluid tank
     * @return A Forge-compatible fluid handler
     */
    public static IFluidHandler createForgeFluidHandler(World world, int x, int y, int z, FluidTank tank) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return HBMForgeFluidCompat.createForgeFluidHandler(tile, tank);
    }

    /**
     * Create a Forge-compatible fluid handler for an HBM block with multiple tanks
     * @param world The world
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param tanks The HBM fluid tanks
     * @return A Forge-compatible fluid handler
     */
    public static IFluidHandler createForgeFluidHandler(World world, int x, int y, int z, FluidTank[] tanks) {
        TileEntity tile = world.getTileEntity(x, y, z);
        return HBMForgeFluidCompat.createForgeFluidHandler(tile, tanks);
    }
}
