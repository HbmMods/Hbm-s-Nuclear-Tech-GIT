package api.ntm1of90.compat.fluid.util;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Utility class for converting between NTM fluids and Forge fluids.
 */
public class NTMForgeFluidConverter {

    /**
     * Convert NTM fluid amount to Forge fluid amount (mB)
     */
    public static int toForgeAmount(int ntmAmount) {
        return ntmAmount; // 1:1 conversion ratio
    }

    /**
     * Convert Forge fluid amount (mB) to NTM fluid amount
     */
    public static int toNTMAmount(int forgeAmount) {
        return forgeAmount; // 1:1 conversion ratio
    }

    /**
     * Create a Forge FluidStack from an NTM fluid type and amount
     */
    public static FluidStack toForgeFluidStack(FluidType type, int amount) {
        if (type == null || type == Fluids.NONE || amount <= 0) {
            return null;
        }

        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(type);
        if (forgeFluid == null) {
            return null;
        }

        return new FluidStack(forgeFluid, toForgeAmount(amount));
    }

    /**
     * Create an NTM fluid pair (type and amount) from a Forge FluidStack
     */
    public static FluidPair toNTMFluid(FluidStack stack) {
        if (stack == null || stack.amount <= 0) {
            return new FluidPair(Fluids.NONE, 0);
        }

        FluidType type = FluidMappingRegistry.getHbmFluidType(stack.getFluid());
        int amount = toNTMAmount(stack.amount);

        return new FluidPair(type, amount);
    }

    /**
     * A simple pair class to hold a fluid type and amount
     */
    public static class FluidPair {
        public final FluidType type;
        public final int amount;

        public FluidPair(FluidType type, int amount) {
            this.type = type;
            this.amount = amount;
        }
    }
}
