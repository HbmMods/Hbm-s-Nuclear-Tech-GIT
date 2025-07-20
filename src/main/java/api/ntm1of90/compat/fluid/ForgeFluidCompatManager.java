package api.ntm1of90.compat.fluid;

import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import api.ntm1of90.compat.fluid.registry.FluidRegistry;
import api.ntm1of90.compat.fluid.registry.ForgeFluidAdapterRegistry;
import api.ntm1of90.compat.fluid.render.NTMFluidCompatRenderer;
import api.ntm1of90.compat.fluid.render.NTMFluidTextureMapper;
import api.ntm1of90.compat.fluid.render.NTMForgeFluidRenderer;
import api.ntm1of90.compat.fluid.util.NTMFluidLocalization;

/**
 * Main entry point for the Forge Fluid Compatibility API.
 * This class initializes all the components needed for fluid compatibility between
 * HBM's Nuclear Tech Mod and Forge's fluid system.
 */
public class ForgeFluidCompatManager {

    private static boolean initialized = false;

    /**
     * Initialize the Forge Fluid Compatibility API.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) {
            return;
        }

        System.out.println("[NTM] Initializing Forge Fluid Compatibility API...");

        // Initialize the fluid registry
        FluidRegistry.initialize();

        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();

        // Initialize the fluid adapter registry
        ForgeFluidAdapterRegistry.initialize();

        // Initialize the fluid capability hook
        ForgeFluidCapabilityHook.initialize();

        // Initialize the fluid localization
        NTMFluidLocalization.initialize();

        // Initialize the fluid compat
        NTMFluidCompat.initialize();

        // Only initialize client-side components when on the client
        if (cpw.mods.fml.common.FMLCommonHandler.instance().getSide() == cpw.mods.fml.relauncher.Side.CLIENT) {
            // Initialize the fluid texture mapper
            NTMFluidTextureMapper.initialize();

            // Initialize the fluid renderer
            NTMForgeFluidRenderer.initialize();

            // Initialize the fluid compat renderer
            NTMFluidCompatRenderer.initialize();
        }

        initialized = true;
        System.out.println("[NTM] Forge Fluid Compatibility API initialized");
    }

    /**
     * Check if the Forge Fluid Compatibility API has been initialized.
     * @return True if the API has been initialized, false otherwise
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Get the default flow rate for Forge fluid transfers.
     * This is the amount of fluid that can be transferred in a single operation.
     * @return The default flow rate in NTM units
     */
    public static int getDefaultForgeFlowRate() {
        return 10000; //Flow rate in mB
    }
}
