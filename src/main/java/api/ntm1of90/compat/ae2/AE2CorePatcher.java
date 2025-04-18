package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * Core patcher for AE2 classes.
 * This class directly patches AE2's bytecode to ensure fluid colors are applied.
 */
public class AE2CorePatcher {

    private static boolean initialized = false;
    private static Map<String, Integer> fluidColors = new HashMap<>();

    /**
     * Initialize the AE2 core patcher.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 core patcher");
            return;
        }

        try {
            // Register all fluid colors
            registerFluidColors();

            // Try to directly access AE2's fluid render map
            directlyPatchAE2();

            System.out.println("[NTM] AE2 core patcher initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 core patcher: " + e.getMessage());
        }

        initialized = true;
    }

    /**
     * Register all fluid colors.
     */
    private static void registerFluidColors() {
        // Register colors for all fluids
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid instanceof ColoredForgeFluid) {
                int color = ((ColoredForgeFluid) fluid).getColorARGB();
                fluidColors.put(fluid.getName(), color);
            }
        }
    }

    /**
     * Directly patch AE2's fluid render map.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void directlyPatchAE2() {
        try {
            // Try to directly access AE2's fluid render map
            Class<?> fluidRenderMapClass = Class.forName("appeng.client.texture.FluidRenderMap");
            Object instance = fluidRenderMapClass.getMethod("instance").invoke(null);

            // Get the fluid colors field
            java.lang.reflect.Field colorsField = fluidRenderMapClass.getDeclaredField("colors");
            colorsField.setAccessible(true);

            // Get the current colors map
            @SuppressWarnings("unchecked")
            Map<Fluid, Integer> colors = (Map<Fluid, Integer>) colorsField.get(instance);

            // Add our colors to the map
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    int color = ((ColoredForgeFluid) fluid).getColorARGB();
                    colors.put(fluid, color);
                }
            }

            System.out.println("[NTM] Successfully patched AE2's FluidRenderMap directly");
        } catch (Exception e) {
            System.err.println("[NTM] Could not patch AE2's FluidRenderMap directly: " + e.getMessage());

            // Try using the registerFluid method
            try {
                Class<?> fluidRenderMapClass = Class.forName("appeng.client.texture.FluidRenderMap");
                Object instance = fluidRenderMapClass.getMethod("instance").invoke(null);
                java.lang.reflect.Method registerMethod = fluidRenderMapClass.getMethod("registerFluid", Fluid.class, int.class);

                // Register all fluids
                for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                    if (fluid instanceof ColoredForgeFluid) {
                        int color = ((ColoredForgeFluid) fluid).getColorARGB();
                        registerMethod.invoke(instance, fluid, color);
                    }
                }

                System.out.println("[NTM] Successfully registered fluids with AE2's FluidRenderMap");
            } catch (Exception ex) {
                System.err.println("[NTM] Could not register fluids with AE2's FluidRenderMap: " + ex.getMessage());
            }
        }
    }

    /**
     * Get the color for a fluid.
     */
    public static int getFluidColor(Fluid fluid) {
        if (fluid == null) return 0xFFFFFFFF;

        Integer color = fluidColors.get(fluid.getName());
        return color != null ? color : 0xFFFFFFFF;
    }
}
