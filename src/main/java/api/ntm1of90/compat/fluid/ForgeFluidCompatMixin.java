package api.ntm1of90.compat.fluid;

import java.util.HashMap;
import java.util.Map;

import api.hbm.fluidmk2.IFluidUserMK2;
import api.ntm1of90.compat.fluid.adapter.AutoForgeFluidAdapter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

/**
 * A mixin class that automatically adds IFluidHandler to all IFluidUserMK2 implementations.
 * This class is used by the ForgeFluidCompatManager to make all HBM fluid tile entities compatible with Forge's fluid system.
 */
public class ForgeFluidCompatMixin {
    
    // Cache of adapters for each fluid user
    private static final Map<IFluidUserMK2, AutoForgeFluidAdapter> adapterCache = new HashMap<>();
    
    /**
     * Get the adapter for a fluid user
     * 
     * @param fluidUser The fluid user to get the adapter for
     * @param tileEntity The tile entity that contains the fluid user
     * @return The adapter for the fluid user
     */
    public static AutoForgeFluidAdapter getAdapter(IFluidUserMK2 fluidUser, TileEntity tileEntity) {
        if (!adapterCache.containsKey(fluidUser)) {
            adapterCache.put(fluidUser, new AutoForgeFluidAdapter(fluidUser, tileEntity));
        }
        return adapterCache.get(fluidUser);
    }
    
    /**
     * Fill a fluid user with a fluid
     * 
     * @param fluidUser The fluid user to fill
     * @param tileEntity The tile entity that contains the fluid user
     * @param from The direction to fill from
     * @param resource The fluid to fill with
     * @param doFill Whether to actually fill the tank
     * @return The amount of fluid that was filled
     */
    public static int fill(IFluidUserMK2 fluidUser, TileEntity tileEntity, ForgeDirection from, FluidStack resource, boolean doFill) {
        return getAdapter(fluidUser, tileEntity).fill(from, resource, doFill);
    }
    
    /**
     * Drain a specific fluid from a fluid user
     * 
     * @param fluidUser The fluid user to drain
     * @param tileEntity The tile entity that contains the fluid user
     * @param from The direction to drain from
     * @param resource The fluid to drain
     * @param doDrain Whether to actually drain the tank
     * @return The fluid that was drained
     */
    public static FluidStack drain(IFluidUserMK2 fluidUser, TileEntity tileEntity, ForgeDirection from, FluidStack resource, boolean doDrain) {
        return getAdapter(fluidUser, tileEntity).drain(from, resource, doDrain);
    }
    
    /**
     * Drain a fluid from a fluid user
     * 
     * @param fluidUser The fluid user to drain
     * @param tileEntity The tile entity that contains the fluid user
     * @param from The direction to drain from
     * @param maxDrain The maximum amount to drain
     * @param doDrain Whether to actually drain the tank
     * @return The fluid that was drained
     */
    public static FluidStack drain(IFluidUserMK2 fluidUser, TileEntity tileEntity, ForgeDirection from, int maxDrain, boolean doDrain) {
        return getAdapter(fluidUser, tileEntity).drain(from, maxDrain, doDrain);
    }
    
    /**
     * Check if a fluid user can be filled with a fluid
     * 
     * @param fluidUser The fluid user to check
     * @param tileEntity The tile entity that contains the fluid user
     * @param from The direction to fill from
     * @param fluid The fluid to fill with
     * @return True if the fluid user can be filled with the fluid, false otherwise
     */
    public static boolean canFill(IFluidUserMK2 fluidUser, TileEntity tileEntity, ForgeDirection from, Fluid fluid) {
        return getAdapter(fluidUser, tileEntity).canFill(from, fluid);
    }
    
    /**
     * Check if a fluid user can be drained of a fluid
     * 
     * @param fluidUser The fluid user to check
     * @param tileEntity The tile entity that contains the fluid user
     * @param from The direction to drain from
     * @param fluid The fluid to drain
     * @return True if the fluid user can be drained of the fluid, false otherwise
     */
    public static boolean canDrain(IFluidUserMK2 fluidUser, TileEntity tileEntity, ForgeDirection from, Fluid fluid) {
        return getAdapter(fluidUser, tileEntity).canDrain(from, fluid);
    }
    
    /**
     * Get information about the tanks in a fluid user
     * 
     * @param fluidUser The fluid user to get information about
     * @param tileEntity The tile entity that contains the fluid user
     * @param from The direction to get information from
     * @return Information about the tanks in the fluid user
     */
    public static FluidTankInfo[] getTankInfo(IFluidUserMK2 fluidUser, TileEntity tileEntity, ForgeDirection from) {
        return getAdapter(fluidUser, tileEntity).getTankInfo(from);
    }
}
