package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles rendering of HBM fluids in AE2 fluid cells.
 * This class directly integrates with AE2's fluid cell rendering system.
 */
@SideOnly(Side.CLIENT)
public class AE2FluidCellRenderer {

    private static boolean initialized = false;
    private static Map<String, Integer> fluidColors = new HashMap<>();

    /**
     * Initialize the AE2 fluid cell renderer.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 fluid cell renderer");
            return;
        }

        try {
            // Register for texture stitch events
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new AE2FluidCellRenderer());

            // Initialize AE2 integration
            initializeAE2();

            System.out.println("[NTM] AE2 fluid cell renderer initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 fluid cell renderer: " + e.getMessage());
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
            // Try to hook into AE2's fluid rendering system
            Class<?> fluidRenderClass = Class.forName("appeng.client.gui.widgets.GuiFluidSlot");

            // Register our custom renderer
            registerCustomRenderer();

            System.out.println("[NTM] Successfully hooked into AE2's fluid rendering system");
        } catch (Exception e) {
            System.err.println("[NTM] Could not hook into AE2's fluid rendering system: " + e.getMessage());
        }
    }

    /**
     * Register our custom renderer with AE2.
     * This method is only called if AE2 is loaded.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerCustomRenderer() {
        try {
            // Try to register with AE2's fluid cell renderer
            Class<?> fluidCellRendererClass = Class.forName("appeng.client.render.FluidCellRenderer");

            // Get the instance of the renderer
            Object instance = fluidCellRendererClass.getMethod("instance").invoke(null);

            // Register our custom color provider
            Method registerColorProviderMethod = fluidCellRendererClass.getMethod("registerColorProvider", String.class, Object.class);
            registerColorProviderMethod.invoke(instance, "hbm", new Object() {
                // This is the color provider method that AE2 will call
                public int getColor(Fluid fluid) {
                    if (fluid == null) return 0xFFFFFFFF;

                    // Get the color from our system
                    int color = NTMFluidColorApplier.getFluidColorForAE2(fluid);

                    // Store the color for later use
                    fluidColors.put(fluid.getName(), color);

                    return color;
                }
            });

            System.out.println("[NTM] Registered custom color provider with AE2's fluid cell renderer");
        } catch (Exception e) {
            System.err.println("[NTM] Could not register custom color provider: " + e.getMessage());
        }
    }

    /**
     * Handle texture stitching to register fluid colors
     */
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        TextureMap map = event.map;

        // Only handle item textures (for fluid cells)
        if (map.getTextureType() != 1) {
            return;
        }

        // Register colors for all fluids
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid instanceof ColoredForgeFluid) {
                int color = ((ColoredForgeFluid) fluid).getColorARGB();
                fluidColors.put(fluid.getName(), color);
            }
        }
    }

    /**
     * Apply color to a fluid for AE2 rendering
     */
    public static void applyFluidColorForAE2(Fluid fluid) {
        if (fluid == null) {
            // Reset to default color
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            return;
        }

        Integer color = fluidColors.get(fluid.getName());

        if (color != null) {
            // Apply the color
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;
            float a = ((color >> 24) & 0xFF) / 255.0f;

            GL11.glColor4f(r, g, b, a);
        } else {
            // Reset to default color
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    /**
     * Get the color for a fluid in AE2
     */
    public static int getFluidColorForAE2(Fluid fluid) {
        if (fluid == null) return 0xFFFFFFFF;

        Integer color = fluidColors.get(fluid.getName());
        return color != null ? color : 0xFFFFFFFF;
    }
}
