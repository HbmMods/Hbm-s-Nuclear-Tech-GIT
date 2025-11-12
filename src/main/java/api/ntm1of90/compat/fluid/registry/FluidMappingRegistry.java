package api.ntm1of90.compat.fluid.registry;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.registry.FluidRegistry;
import api.ntm1of90.compat.fluid.util.NTMFluidLocalization;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Registry for mapping between HBM's Nuclear Tech Mod fluids and Forge fluids.
 * This allows for compatibility between HBM's custom fluid system and mods that use Forge's fluid system.
 */
public class FluidMappingRegistry {

    // Mapping between Forge Fluid names and HBM FluidTypes
    private static final Map<String, FluidType> forgeToHbmMap = new HashMap<>();
    private static final Map<FluidType, String> hbmToForgeMap = new HashMap<>();
    private static boolean initialized = false;

    /**
     * Initialize the fluid mapping registry.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) {
            return;
        }

        // Initialize the fluid mapping registry

        System.out.println("[NTM] Starting automatic fluid mapping between NTM and Forge fluid systems...");
        int mappedCount = 0;

        // Automatically map all HBM fluids to Forge fluids
        // This ensures that all fluids are correctly mapped without manual listing
        for (FluidType fluidType : Fluids.getAll()) {
            // Skip NONE fluid as it's a special case
            if (fluidType == Fluids.NONE) {
                continue;
            }

            // Skip fluids with special traits that shouldn't be mapped
            if (fluidType.hasNoContainer() || fluidType.hasNoID()) {
                System.out.println("[NTM] Skipping special fluid: " + fluidType.getName());
                continue;
            }

            // Convert the HBM fluid name to a standard Forge fluid name format
            // HBM uses uppercase names like "WATER", Forge uses lowercase like "water"
            String forgeName = fluidType.getName().toLowerCase(Locale.US);

            // Register the primary mapping
            registerFluidMapping(forgeName, fluidType);
            mappedCount++;

            // Add common aliases for better compatibility with other mods
            addFluidAliases(fluidType);
        }

        // Count the number of mapped fluids
        mappedCount = forgeToHbmMap.size();

        // Register all HBM fluids with Forge's FluidRegistry
        for (String forgeName : forgeToHbmMap.keySet()) {
            if (net.minecraftforge.fluids.FluidRegistry.getFluid(forgeName) == null) {
                FluidType hbmFluid = forgeToHbmMap.get(forgeName);
                Fluid forgeFluid = new ColoredForgeFluid(forgeName, hbmFluid);
                net.minecraftforge.fluids.FluidRegistry.registerFluid(forgeFluid);
                System.out.println("[NTM] Registered new Forge fluid: " + forgeName + " with color 0x" + Integer.toHexString(((ColoredForgeFluid)forgeFluid).getColor()));
            }
        }

        // All fluids have been registered and mapped

        System.out.println("[NTM] Fluid mapping complete! Mapped " + mappedCount + " NTM fluids to Forge fluid system.");
        System.out.println("[NTM] Total number of fluid mappings created: " + forgeToHbmMap.size());

        // Initialize fluid registry
        api.ntm1of90.compat.fluid.registry.FluidRegistry.initialize();

        // Initialize fluid localization
        NTMFluidLocalization.initialize();

        // Fluid inventory rendering is now handled by FluidRegistry

        // Print a detailed summary of all mappings
        printFluidMappingSummary();

        initialized = true;
    }

    /**
     * Print a summary of all mapped fluids to the console
     */
    public static void printFluidMappingSummary() {
        System.out.println("\n[NTM] ===== FLUID MAPPING SUMMARY =====");
        System.out.println("[NTM] Total NTM fluids mapped: " + hbmToForgeMap.size());
        System.out.println("[NTM] Total Forge fluid mappings: " + forgeToHbmMap.size());

        // Get a set of all unique HBM fluids that have been mapped
        Set<FluidType> mappedHbmFluids = new HashSet<>(hbmToForgeMap.keySet());
        System.out.println("[NTM] NTM Fluids with Forge Mappings: " + mappedHbmFluids.size());

        // Sort the HBM fluids by name for easier reading
        List<FluidType> sortedHbmFluids = new ArrayList<>(mappedHbmFluids);
        sortedHbmFluids.sort((f1, f2) -> f1.getName().compareTo(f2.getName()));

        // Print each HBM fluid and all its Forge mappings
        for (FluidType hbmFluid : sortedHbmFluids) {
            // Find all Forge names that map to this HBM fluid
            List<String> forgeNames = new ArrayList<>();
            for (Map.Entry<String, FluidType> entry : forgeToHbmMap.entrySet()) {
                if (entry.getValue() == hbmFluid) {
                    forgeNames.add(entry.getKey());
                }
            }

            // Sort the Forge names for consistency
            Collections.sort(forgeNames);

            // Print the HBM fluid and all its Forge mappings
            System.out.println("[NTM]   - " + hbmFluid.getName() + " -> " + String.join(", ", forgeNames));
        }

        // Check for any HBM fluids that weren't mapped
        List<FluidType> unmappedFluids = new ArrayList<>();
        for (FluidType fluidType : Fluids.getAll()) {
            if (fluidType != Fluids.NONE && !mappedHbmFluids.contains(fluidType)) {
                unmappedFluids.add(fluidType);
            }
        }

        // Print any unmapped fluids
        if (!unmappedFluids.isEmpty()) {
            System.out.println("\n[NTM] WARNING: The following NTM fluids were not mapped to Forge fluids:");
            for (FluidType fluid : unmappedFluids) {
                System.out.println("[NTM]   - " + fluid.getName());
            }
        }

        System.out.println("[NTM] ================================\n");
    }

    /**
     * Add common aliases for a fluid type to improve compatibility with other mods
     */
    private static void addFluidAliases(FluidType fluidType) {
        String name = fluidType.getName();

        // BuildCraft compatibility
        if (fluidType == Fluids.OIL) {
            registerFluidMapping("crude_oil", fluidType);
        } else if (fluidType == Fluids.HEAVYOIL) {
            registerFluidMapping("heavy_oil", fluidType);
        } else if (fluidType == Fluids.LIGHTOIL) {
            registerFluidMapping("light_oil", fluidType);
        } else if (fluidType == Fluids.GAS) {
            registerFluidMapping("natural_gas", fluidType);
        } else if (fluidType == Fluids.PETROIL) {
            registerFluidMapping("fuel", fluidType);
        }

        // IC2 compatibility
        else if (fluidType == Fluids.SULFURIC_ACID) {
            registerFluidMapping("sulfuricacid", fluidType);
        } else if (fluidType == Fluids.UF6) {
            registerFluidMapping("uranium_hexafluoride", fluidType);
        }

        // Forestry compatibility
        else if (fluidType == Fluids.BIOFUEL) {
            registerFluidMapping("ethanol", fluidType);
        }

        // General compatibility
        else if (fluidType == Fluids.HELIUM3) {
            registerFluidMapping("helium", fluidType);
        } else if (fluidType == Fluids.CARBONDIOXIDE) {
            registerFluidMapping("carbon_dioxide", fluidType);
        }

        // Handle underscore variants (common in many mods)
        String lowercaseName = name.toLowerCase(Locale.US);
        if (lowercaseName.contains("_")) {
            registerFluidMapping(lowercaseName.replace("_", ""), fluidType);
        } else if (!lowercaseName.contains("_") && lowercaseName.length() > 3) {
            // Try to add underscores for readability in some cases
            // This is a heuristic and might not be perfect
            for (String forgeName : net.minecraftforge.fluids.FluidRegistry.getRegisteredFluids().keySet()) {
                if (forgeName.replace("_", "").equals(lowercaseName)) {
                    registerFluidMapping(forgeName, fluidType);
                    break;
                }
            }
        }
    }

    /**
     * Register a mapping between a Forge fluid name and an HBM FluidType
     */
    public static void registerFluidMapping(String forgeName, FluidType hbmType) {
        // Check if this mapping already exists
        FluidType existingType = forgeToHbmMap.get(forgeName);
        if (existingType != null && existingType != hbmType) {
            System.out.println("[NTM] Warning: Overriding existing fluid mapping for '" + forgeName + "' from " +
                existingType.getName() + " to " + hbmType.getName());
        }

        forgeToHbmMap.put(forgeName, hbmType);
        hbmToForgeMap.put(hbmType, forgeName);

        // Only log if we're not in the initial mapping phase (to avoid console spam)
        if (forgeToHbmMap.size() > 1 && existingType == null) {
            System.out.println("[NTM] Mapped Forge fluid '" + forgeName + "' to NTM fluid '" + hbmType.getName() + "'");
        }
    }

    /**
     * Convert a Forge Fluid to an HBM FluidType
     * Uses a dynamic approach to find the best matching fluid type
     */
    public static FluidType getHbmFluidType(Fluid fluid) {
        if (fluid == null) {
            return Fluids.NONE;
        }

        String fluidName = fluid.getName().toLowerCase(Locale.US);

        // First, check if we already have a direct mapping
        FluidType type = forgeToHbmMap.get(fluidName);
        if (type != null) {
            return type;
        }

        // If not found in our mappings, try to find a matching HBM fluid by name
        // This is the dynamic part - we don't need manual mappings

        // First pass: Try exact matches and simple transformations
        for (FluidType hbmFluid : Fluids.getAll()) {
            if (hbmFluid == Fluids.NONE) continue;

            String hbmName = hbmFluid.getName().toLowerCase(Locale.US);

            // Try exact match first
            if (hbmName.equals(fluidName)) {
                registerFluidMapping(fluidName, hbmFluid);
                return hbmFluid;
            }

            // Try removing underscores and comparing
            String cleanHbm = hbmName.replace("_", "");
            String cleanForge = fluidName.replace("_", "");
            if (cleanHbm.equals(cleanForge)) {
                registerFluidMapping(fluidName, hbmFluid);
                return hbmFluid;
            }
        }

        // Second pass: Try more complex matching strategies
        for (FluidType hbmFluid : Fluids.getAll()) {
            if (hbmFluid == Fluids.NONE) continue;

            String hbmName = hbmFluid.getName().toLowerCase(Locale.US);

            // Try substring matching (for cases like "crude_oil" vs "oil")
            if (hbmName.contains(fluidName) || fluidName.contains(hbmName)) {
                registerFluidMapping(fluidName, hbmFluid);
                return hbmFluid;
            }

            // Try common prefixes/suffixes
            if (fluidName.startsWith("fluid") && fluidName.substring(5).equals(hbmName)) {
                registerFluidMapping(fluidName, hbmFluid);
                return hbmFluid;
            }

            // Handle special cases for common mods
            if ((fluidName.equals("fuel") && hbmName.equals("petroleum")) ||
                (fluidName.equals("ethanol") && hbmName.equals("biofuel")) ||
                (fluidName.equals("crude_oil") && hbmName.equals("oil"))) {
                registerFluidMapping(fluidName, hbmFluid);
                return hbmFluid;
            }
        }

        // If we still can't find it, log it and return NONE
        System.out.println("[NTM] Unknown Forge fluid: '" + fluid.getName() + "'. No matching NTM fluid found. This fluid will not be usable in NTM machines.");
        return Fluids.NONE;
    }

    /**
     * Get the Forge fluid name for an HBM FluidType
     * This is used by the texture mapper to find the corresponding Forge fluid
     */
    public static String getForgeFluidName(FluidType type) {
        if (type == null || type == Fluids.NONE) {
            return null;
        }

        // First, check if we already have a mapping
        return hbmToForgeMap.get(type);
    }

    /**
     * Convert an HBM FluidType to a Forge Fluid
     * Uses a dynamic approach to find the best matching Forge fluid
     */
    public static Fluid getForgeFluid(FluidType type) {
        if (type == null || type == Fluids.NONE) {
            return null;
        }

        // First, check if we already have a mapping
        String fluidName = hbmToForgeMap.get(type);
        if (fluidName != null) {
            Fluid fluid = net.minecraftforge.fluids.FluidRegistry.getFluid(fluidName);
            if (fluid != null) {
                return fluid;
            }
        }

        // If no mapping exists or the mapped fluid doesn't exist in Forge,
        // try to find a matching Forge fluid by name
        String hbmName = type.getName().toLowerCase(Locale.US);

        // Try direct lookup first
        Fluid fluid = net.minecraftforge.fluids.FluidRegistry.getFluid(hbmName);
        if (fluid != null) {
            registerFluidMapping(hbmName, type);
            return fluid;
        }

        // First pass: Try exact matches and simple transformations
        for (String forgeName : net.minecraftforge.fluids.FluidRegistry.getRegisteredFluids().keySet()) {
            // Try removing underscores and comparing
            String cleanHbm = hbmName.replace("_", "");
            String cleanForge = forgeName.replace("_", "");
            if (cleanHbm.equals(cleanForge)) {
                registerFluidMapping(forgeName, type);
                return net.minecraftforge.fluids.FluidRegistry.getFluid(forgeName);
            }
        }

        // Second pass: Try more complex matching strategies
        for (String forgeName : net.minecraftforge.fluids.FluidRegistry.getRegisteredFluids().keySet()) {
            // Try substring matching
            if (forgeName.contains(hbmName) || hbmName.contains(forgeName)) {
                registerFluidMapping(forgeName, type);
                return net.minecraftforge.fluids.FluidRegistry.getFluid(forgeName);
            }

            // Try common prefixes/suffixes
            if (forgeName.startsWith("fluid") && forgeName.substring(5).equals(hbmName)) {
                registerFluidMapping(forgeName, type);
                return net.minecraftforge.fluids.FluidRegistry.getFluid(forgeName);
            }
        }

        // Handle special cases for common mods
        if (type == Fluids.PETROIL) {
            Fluid fuelFluid = net.minecraftforge.fluids.FluidRegistry.getFluid("fuel");
            if (fuelFluid != null) {
                registerFluidMapping("fuel", type);
                return fuelFluid;
            }
        } else if (type == Fluids.BIOFUEL) {
            Fluid ethanolFluid = net.minecraftforge.fluids.FluidRegistry.getFluid("ethanol");
            if (ethanolFluid != null) {
                registerFluidMapping("ethanol", type);
                return ethanolFluid;
            }
        } else if (type == Fluids.OIL) {
            Fluid crudeOilFluid = net.minecraftforge.fluids.FluidRegistry.getFluid("crude_oil");
            if (crudeOilFluid != null) {
                registerFluidMapping("crude_oil", type);
                return crudeOilFluid;
            }
        }

        // If we can't find a match, register a new Forge fluid with the HBM name
        if (net.minecraftforge.fluids.FluidRegistry.getFluid(hbmName) == null) {
            int color = type.getColor();
            Fluid newFluid = new ColoredForgeFluid(hbmName, color);
            net.minecraftforge.fluids.FluidRegistry.registerFluid(newFluid);
            registerFluidMapping(hbmName, type);
            System.out.println("[NTM] Created new Forge fluid '" + hbmName + "' for NTM fluid '" + type.getName() + "' with color 0x" + Integer.toHexString(color));
            return newFluid;
        }

        // This should never happen now, but just in case
        registerFluidMapping(hbmName, type);
        return null;
    }
}
