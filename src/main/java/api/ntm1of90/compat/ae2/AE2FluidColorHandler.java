package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Handles compatibility with Applied Energistics 2 for fluid colors.
 * This class is only loaded if AE2 is present.
 */
public class AE2FluidColorHandler {

    private static boolean initialized = false;

    /**
     * Initialize the AE2 fluid color handler.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 fluid color handler");
            return;
        }

        try {
            // Register for AE2 events
            initializeAE2();
            System.out.println("[NTM] AE2 fluid color handler initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 fluid color handler: " + e.getMessage());
        }

        initialized = true;
    }

    /**
     * Initialize AE2 compatibility.
     * This method is only called if AE2 is loaded.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void initializeAE2() {
        // Register our handler with AE2's event bus
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new AE2FluidColorHandler());

        // Register our fluid colors with AE2
        registerFluidColors();

        // Initialize the fluid cell renderer
        AE2FluidCellRenderer.initialize();

        // Initialize the fluid storage handler
        AE2FluidStorageHandler.initialize();

        // Initialize the fluid GUI handler
        AE2FluidGUIHandler.initialize();

        // Initialize the RV2-specific handler
        AE2RV2FluidColorHandler.initialize();

        // Initialize the core patcher
        AE2CorePatcher.initialize();

        // Initialize the fluid cell patcher
        AE2FluidCellPatcher.initialize();

        // Initialize the direct color fixer
        AE2DirectColorFixer.initialize();

        // Initialize the fluid cell inventory fixer
        AE2FluidCellInventoryFixer.initialize();
    }

    /**
     * Register fluid colors with AE2.
     * This method is only called if AE2 is loaded.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerFluidColors() {
        // Register colors for all fluids
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid instanceof ColoredForgeFluid) {
                int color = NTMFluidColorApplier.getFluidColorForAE2(fluid);
                registerFluidColorWithAE2(fluid, color);
            }
        }
    }

    /**
     * Register a fluid color with AE2.
     * This method is only called if AE2 is loaded.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerFluidColorWithAE2(Fluid fluid, int color) {
        try {
            // First approach: Use AE2's FluidRendererRegistry if available
            try {
                Class<?> fluidRenderClass = Class.forName("appeng.client.render.FluidRendererRegistry");
                Object instance = fluidRenderClass.getMethod("instance").invoke(null);
                fluidRenderClass.getMethod("setFluidColor", Fluid.class, int.class).invoke(instance, fluid, color);
                System.out.println("[NTM] Registered AE2 color for fluid via FluidRendererRegistry: " + fluid.getName() + " (0x" + Integer.toHexString(color) + ")");
            } catch (Exception e) {
                System.err.println("[NTM] Could not use FluidRendererRegistry: " + e.getMessage());
            }

            // Second approach: Register with AE2's FluidRenderMap
            try {
                Class<?> fluidRenderMapClass = Class.forName("appeng.client.texture.FluidRenderMap");
                Object instance = fluidRenderMapClass.getMethod("instance").invoke(null);
                fluidRenderMapClass.getMethod("registerFluid", Fluid.class, int.class).invoke(instance, fluid, color);
                System.out.println("[NTM] Registered AE2 color for fluid via FluidRenderMap: " + fluid.getName() + " (0x" + Integer.toHexString(color) + ")");
            } catch (Exception e) {
                System.err.println("[NTM] Could not use FluidRenderMap: " + e.getMessage());
            }

            // Third approach: Register with AE2's CellInventoryHandler
            try {
                Class<?> cellInventoryHandlerClass = Class.forName("appeng.me.storage.CellInventoryHandler");
                // This is a static method that registers fluid colors for cell rendering
                cellInventoryHandlerClass.getMethod("registerFluidColor", Fluid.class, int.class).invoke(null, fluid, color);
                System.out.println("[NTM] Registered AE2 color for fluid via CellInventoryHandler: " + fluid.getName() + " (0x" + Integer.toHexString(color) + ")");
            } catch (Exception e) {
                System.err.println("[NTM] Could not use CellInventoryHandler: " + e.getMessage());
            }

            // Fourth approach: Register with AE2's FluidCellInventory
            try {
                Class<?> fluidCellInventoryClass = Class.forName("appeng.me.storage.FluidCellInventory");
                // This is a static method that registers fluid colors for cell rendering
                fluidCellInventoryClass.getMethod("registerFluidColor", Fluid.class, int.class).invoke(null, fluid, color);
                System.out.println("[NTM] Registered AE2 color for fluid via FluidCellInventory: " + fluid.getName() + " (0x" + Integer.toHexString(color) + ")");
            } catch (Exception e) {
                System.err.println("[NTM] Could not use FluidCellInventory: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("[NTM] Error registering AE2 color for fluid " + fluid.getName() + ": " + e.getMessage());
        }
    }
}
