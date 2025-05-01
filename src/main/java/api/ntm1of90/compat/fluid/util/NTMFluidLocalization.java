package api.ntm1of90.compat.fluid.util;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Handles localization of NTM fluids in the Forge fluid system.
 * This ensures that NTM fluids have proper names when displayed in other mods' GUIs.
 *
 * Fluid names are loaded from the language files, using both 'fluid.' and 'hbmfluid.' prefixes.
 * No special cases are handled - all fluid names come from language files or automatic formatting.
 */
public class NTMFluidLocalization {

    // Cache for fluid names loaded from language files
    private static Map<String, String> fluidNameCache = new HashMap<>();

    /**
     * Initialize the fluid localization.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        System.out.println("[NTM] Initializing fluid localization...");
        initializeWithNames(false);
    }

    /**
     * Load fluid names from the language files.
     * This checks both 'fluid.' and 'hbmfluid.' prefixes.
     */
    private static void loadFluidNamesFromLang() {
        fluidNameCache.clear();
        int count = 0;

        // Load all NTM fluids using hbmfluid.* entries
        for (FluidType ntmFluid : Fluids.getAll()) {
            if (ntmFluid == Fluids.NONE) continue;

            String ntmName = ntmFluid.getName().toLowerCase(Locale.US);
            String langKey = "hbmfluid." + ntmName;

            // Try to get the name from the language file
            if (StatCollector.canTranslate(langKey)) {
                String displayName = StatCollector.translateToLocal(langKey);
                if (!displayName.equals(langKey)) {
                    fluidNameCache.put(ntmName, displayName);
                    System.out.println("[NTM] Loaded NTM fluid name from lang: " + ntmName + " -> " + displayName);
                    count++;
                }
            }

            // No special cases - rely entirely on language files
        }

        // Check for fluid.* entries in the language file
        for (String fluidName : FluidRegistry.getRegisteredFluids().keySet()) {
            String langKey = "fluid." + fluidName;
            if (StatCollector.canTranslate(langKey)) {
                String displayName = StatCollector.translateToLocal(langKey);
                if (!displayName.equals(langKey)) {
                    fluidNameCache.put(fluidName, displayName);
                    System.out.println("[NTM] Loaded Forge fluid name from lang: " + fluidName + " -> " + displayName);
                    count++;
                }
            }
        }

        // Also check for any hbmfluid.* entries that might not be associated with an NTM fluid
        // This allows for custom fluid names to be added to the language file
        for (String fluidName : FluidRegistry.getRegisteredFluids().keySet()) {
            if (fluidNameCache.containsKey(fluidName)) continue; // Skip if we already have a name

            String langKey = "hbmfluid." + fluidName;
            if (StatCollector.canTranslate(langKey)) {
                String displayName = StatCollector.translateToLocal(langKey);
                if (!displayName.equals(langKey)) {
                    fluidNameCache.put(fluidName, displayName);
                    System.out.println("[NTM] Loaded Forge fluid name from NTM lang: " + fluidName + " -> " + displayName);
                    count++;
                }
            }
        }

        System.out.println("[NTM] Loaded " + count + " fluid names from language files");
    }

    /**
     * Register localized names for all NTM fluids in the Forge fluid system.
     */
    private static void registerFluidNames() {
        int count = 0;

        // Register names for all NTM fluids
        for (FluidType ntmFluid : Fluids.getAll()) {
            if (ntmFluid == Fluids.NONE) continue;

            // Get the Forge fluid for this NTM fluid
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(ntmFluid);
            if (forgeFluid == null) continue;

            // Get the display name for this fluid
            String displayName = getDisplayName(ntmFluid);

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
            System.out.println("[NTM] Registered localized name for fluid: " + forgeFluid.getName() + " -> " + displayName);

            count++;
        }

        // Also register names for all Forge fluids that don't have an NTM equivalent
        for (Fluid forgeFluid : FluidRegistry.getRegisteredFluids().values()) {
            // Skip fluids that are already registered (NTM fluids)
            if (FluidMappingRegistry.getHbmFluidType(forgeFluid) != Fluids.NONE) continue;

            // Get the display name for this fluid
            String displayName = getForgeFluidDisplayName(forgeFluid);

            // Register the name with Forge
            String unlocalizedName = "fluid." + forgeFluid.getName();
            LanguageRegistry.instance().addStringLocalization(unlocalizedName, displayName);

            // Also register the bucket name
            String bucketName = "item.bucket." + forgeFluid.getName();
            String bucketDisplayName = displayName + " Bucket";
            LanguageRegistry.instance().addStringLocalization(bucketName, bucketDisplayName);

            System.out.println("[NTM] Registered localized name for non-NTM fluid: " + forgeFluid.getName() + " -> " + displayName);
            count++;
        }

        System.out.println("[NTM] Registered localized names for " + count + " fluids");
    }

    /**
     * Get a display name for an NTM fluid.
     * This first checks the cache for a name from the language file.
     * If not found, it checks the language file directly using the "hbmfluid." prefix.
     * If still not found, it converts the internal name (e.g., "SULFURIC_ACID") to a user-friendly name (e.g., "Sulfuric Acid").
     * No special cases are handled - all fluid names come from language files or automatic formatting.
     */
    private static String getDisplayName(FluidType fluid) {
        String name = fluid.getName();
        String lowercaseName = name.toLowerCase(Locale.US);

        // First check if we have a cached name from the language file
        if (fluidNameCache.containsKey(lowercaseName)) {
            return fluidNameCache.get(lowercaseName);
        }

        // Try to get the name directly from the language file
        String langKey = "hbmfluid." + lowercaseName;
        if (StatCollector.canTranslate(langKey)) {
            String displayName = StatCollector.translateToLocal(langKey);
            if (!displayName.equals(langKey)) {
                fluidNameCache.put(lowercaseName, displayName);
                return displayName;
            }
        }

        // No special cases - rely entirely on language files

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
        System.out.println("[NTM] Skipping bucket registration for fluid: " + fluid.getName() + " (handled by Forge)");
    }

    /**
     * Get the display name for a Forge fluid.
     * This can be used by other parts of the code to get the localized name of a fluid.
     * Names are retrieved from language files or generated automatically - no special cases are handled.
     *
     * @param fluid The Forge fluid to get the name for
     * @return The localized display name of the fluid
     */
    public static String getForgeFluidDisplayName(Fluid fluid) {
        if (fluid == null) return "Unknown Fluid";

        String fluidName = fluid.getName().toLowerCase(Locale.US);

        // First check if we have a cached name from the language file
        if (fluidNameCache.containsKey(fluidName)) {
            return fluidNameCache.get(fluidName);
        }

        // If it's a ColoredForgeFluid, try to get the name from the NTM fluid
        if (fluid instanceof ColoredForgeFluid) {
            ColoredForgeFluid coloredFluid = (ColoredForgeFluid) fluid;
            FluidType ntmFluid = coloredFluid.getHbmFluidType();
            if (ntmFluid != null) {
                return getDisplayName(ntmFluid);
            }
        }

        // Try to get the name from the Forge registry (fluid.* entries)
        String unlocalizedName = "fluid." + fluidName;
        if (StatCollector.canTranslate(unlocalizedName)) {
            String displayName = StatCollector.translateToLocal(unlocalizedName);
            if (!displayName.equals(unlocalizedName)) {
                fluidNameCache.put(fluidName, displayName);
                return displayName;
            }
        }

        // Try to get the name from the NTM registry (hbmfluid.* entries)
        String ntmLangKey = "hbmfluid." + fluidName;
        if (StatCollector.canTranslate(ntmLangKey)) {
            String displayName = StatCollector.translateToLocal(ntmLangKey);
            if (!displayName.equals(ntmLangKey)) {
                fluidNameCache.put(fluidName, displayName);
                return displayName;
            }
        }

        // No special cases - rely entirely on language files

        // Fall back to a formatted version of the fluid name
        return formatFluidName(fluidName);
    }

    /**
     * Format a fluid name for display.
     * Converts from lowercase with underscores to title case with spaces.
     *
     * @param name The fluid name to format
     * @return The formatted name
     */
    private static String formatFluidName(String name) {
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
     * Reload the fluid names from the language files.
     * This is an internal method used for development and debugging.
     */
    private static void reloadFluidNames() {
        System.out.println("[NTM] Reloading fluid localization...");
        fluidNameCache.clear();
        loadFluidNamesFromLang();
        registerFluidNames();
        System.out.println("[NTM] Fluid localization reloaded");
    }

    /**
     * Initialize the fluid localization system with the names from the language files.
     * This is called during mod initialization.
     *
     * @param forceReload If true, the fluid names will be reloaded even if they have already been loaded
     */
    public static void initializeWithNames(boolean forceReload) {
        if (forceReload || fluidNameCache.isEmpty()) {
            System.out.println("[NTM] Initializing fluid localization with names from language files...");
            loadFluidNamesFromLang();
            registerFluidNames();
        }
    }
}
