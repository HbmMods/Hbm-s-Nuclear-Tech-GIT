package api.ntm1of90.compat;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Locale;

/**
 * Handles localization of HBM fluids in the Forge fluid system.
 * This ensures that HBM fluids have proper names when displayed in other mods' GUIs.
 */
public class HBMFluidLocalization {

    /**
     * Initialize the fluid localization.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        System.out.println("[HBM] Initializing fluid localization...");
        registerFluidNames();
    }

    /**
     * Register localized names for all HBM fluids in the Forge fluid system.
     */
    private static void registerFluidNames() {
        int count = 0;

        // Register names for all HBM fluids
        for (FluidType hbmFluid : Fluids.getAll()) {
            if (hbmFluid == Fluids.NONE) continue;

            // Get the Forge fluid for this HBM fluid
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(hbmFluid);
            if (forgeFluid == null) continue;

            // Get the display name for this fluid
            String displayName = getDisplayName(hbmFluid);

            // Register the name with Forge - only use string localization
            // We can't use LanguageRegistry.addName() with Fluid objects
            String unlocalizedName = "fluid." + forgeFluid.getName();
            LanguageRegistry.instance().addStringLocalization(unlocalizedName, displayName);

            // Also register the bucket name if applicable
            String bucketName = "item.bucket." + forgeFluid.getName();
            String bucketDisplayName = displayName + " Bucket";
            LanguageRegistry.instance().addStringLocalization(bucketName, bucketDisplayName);

            // Register for Forge fluid containers
            if (FluidRegistry.isFluidRegistered(forgeFluid)) {
                // In 1.7.10, we need to manually register buckets
                // This is handled by the FluidContainerRegistry
                registerBucketForFluid(forgeFluid);
            }

            // Log the registration
            System.out.println("[HBM] Registered localized name for fluid: " + forgeFluid.getName() + " -> " + displayName);

            count++;
        }

        System.out.println("[HBM] Registered localized names for " + count + " fluids");
    }

    /**
     * Get a display name for an HBM fluid.
     * This converts the internal name (e.g., "SULFURIC_ACID") to a user-friendly name (e.g., "Sulfuric Acid").
     */
    private static String getDisplayName(FluidType fluid) {
        String name = fluid.getName();

        // Handle special cases
        if (name.equals("UF6")) return "Uranium Hexafluoride";
        if (name.equals("PUF6")) return "Plutonium Hexafluoride";
        if (name.equals("SAS3")) return "Strange Antimatter Solution";
        if (name.equals("NITAN")) return "Nitan Fuel";

        // Convert from uppercase with underscores to title case with spaces
        StringBuilder result = new StringBuilder();
        String[] parts = name.split("_");

        for (String part : parts) {
            if (part.length() > 0) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    result.append(part.substring(1).toLowerCase(Locale.US));
                }
            }
        }

        return result.toString();
    }

    /**
     * Register a bucket for a fluid in the FluidContainerRegistry
     * This is the 1.7.10 equivalent of FluidRegistry.addBucketForFluid
     */
    private static void registerBucketForFluid(Fluid fluid) {
        // In 1.7.10, we don't need to manually register buckets
        // Forge handles this automatically when the fluid is registered
        // This method is kept as a placeholder for future compatibility

        // Just log that we're skipping this step
        System.out.println("[HBM] Skipping bucket registration for fluid: " + fluid.getName() + " (handled by Forge)");
    }
}
