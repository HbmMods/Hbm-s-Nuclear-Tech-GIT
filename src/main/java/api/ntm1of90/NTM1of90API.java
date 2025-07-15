package api.ntm1of90;

import api.ntm1of90.compat.ae2.AE2FluidCompat;
import api.ntm1of90.compat.fluid.ForgeFluidCompatManager;

/**
 * Main entry point for NTM1of90's API.
 * This class provides access to all the APIs provided by NTM1of90's extensions to HBM's Nuclear Tech Mod.
 */
public class NTM1of90API {

    private static boolean initialized = false;

    /**
     * Initialize the API.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) {
            return;
        }

        System.out.println("[NTM] Initializing NTM1of90 API...");

        // Initialize the Forge Fluid Compatibility API
        ForgeFluidCompatManager.initialize();

        // Initialize the Applied Energistics 2 Compatibility API
        AE2FluidCompat.initialize();

        initialized = true;
        System.out.println("[NTM] NTM1of90 API initialized");
    }

    /**
     * Check if the API has been initialized.
     * @return True if the API has been initialized, false otherwise
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Get the version of the API.
     * @return The version of the API
     */
    public static String getVersion() {
        return "1.0.0";
    }
}
