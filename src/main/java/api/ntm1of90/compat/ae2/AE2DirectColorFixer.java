package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Direct fixer for AE2's fluid colors.
 * This class uses a very direct approach to fix fluid colors in AE2.
 */
@SideOnly(Side.CLIENT)
public class AE2DirectColorFixer {

    private static boolean initialized = false;
    private static Map<String, Integer> fluidColors = new HashMap<>();

    // Cache for AE2 classes and methods
    private static Class<?> fluidRenderMapClass = null;
    private static Object fluidRenderMapInstance = null;
    private static Field colorsField = null;
    private static Method registerFluidMethod = null;

    /**
     * Initialize the AE2 direct color fixer.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 direct color fixer");
            return;
        }

        try {
            // Register for tick events
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new AE2DirectColorFixer());

            // Initialize AE2 integration
            initializeAE2();

            System.out.println("[NTM] AE2 direct color fixer initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 direct color fixer: " + e.getMessage());
        }

        initialized = true;
    }

    /**
     * Initialize AE2 integration.
     * This method is only called if AE2 is loaded.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void initializeAE2() {
        try {
            // Get the FluidRenderMap class
            fluidRenderMapClass = Class.forName("appeng.client.texture.FluidRenderMap");

            // Get the instance
            Method instanceMethod = fluidRenderMapClass.getMethod("instance");
            fluidRenderMapInstance = instanceMethod.invoke(null);

            // Get the colors field
            colorsField = fluidRenderMapClass.getDeclaredField("colors");
            colorsField.setAccessible(true);

            // Get the registerFluid method
            registerFluidMethod = fluidRenderMapClass.getMethod("registerFluid", Fluid.class, int.class);

            // Register all fluid colors
            registerAllFluidColors();

            System.out.println("[NTM] Successfully initialized AE2 direct color fixer");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 direct color fixer: " + e.getMessage());
        }
    }

    /**
     * Register all fluid colors with AE2.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerAllFluidColors() {
        try {
            // Register colors for all fluids
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    int color = ((ColoredForgeFluid) fluid).getColorARGB();
                    fluidColors.put(fluid.getName(), color);

                    // Register with AE2
                    registerFluidColor(fluid, color);
                }
            }

            System.out.println("[NTM] Registered all fluid colors with AE2");
        } catch (Exception e) {
            System.err.println("[NTM] Error registering fluid colors with AE2: " + e.getMessage());
        }
    }

    /**
     * Register a fluid color with AE2.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerFluidColor(Fluid fluid, int color) {
        if (fluid == null) return;

        try {
            // Try using the registerFluid method
            if (fluidRenderMapInstance != null && registerFluidMethod != null) {
                registerFluidMethod.invoke(fluidRenderMapInstance, fluid, color);
            }

            // Try directly modifying the colors map
            if (fluidRenderMapInstance != null && colorsField != null) {
                @SuppressWarnings("unchecked")
                Map<Fluid, Integer> colors = (Map<Fluid, Integer>) colorsField.get(fluidRenderMapInstance);
                colors.put(fluid, color);
            }
        } catch (Exception e) {
            // Ignore errors
        }
    }

    /**
     * Handle client tick events to periodically update fluid colors.
     */
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        // Update every 5 seconds to ensure colors are applied
        if (Minecraft.getMinecraft().theWorld != null &&
            Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 100 == 0) {
            registerAllFluidColors();
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
