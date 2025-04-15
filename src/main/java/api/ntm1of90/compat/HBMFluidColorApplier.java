package api.ntm1of90.compat;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Applies HBM fluid colors to Forge fluids during rendering.
 * This class handles the color application for Forge fluids
 * that correspond to HBM fluids.
 */
@SideOnly(Side.CLIENT)
public class HBMFluidColorApplier {

    // Maps fluid names to their colors
    private static final Map<String, Integer> fluidColors = new HashMap<>();

    // Color adjustment factor to make fluids appear brighter in Forge rendering
    private static float BRIGHTNESS_FACTOR = 0.9f;

    /**
     * Initialize the color applier.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        // Register for texture stitch events
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new HBMFluidColorApplier());

        System.out.println("[HBM] Fluid color applier initialized");
    }

    /**
     * Set the brightness factor for fluid colors
     * @param factor The brightness factor (>1 = brighter, <1 = darker)
     */
    public static void setBrightnessFactor(float factor) {
        BRIGHTNESS_FACTOR = factor;
        System.out.println("[HBM] Fluid color brightness factor set to " + factor);
    }

    /**
     * Get the current brightness factor
     * @return The current brightness factor
     */
    public static float getBrightnessFactor() {
        return BRIGHTNESS_FACTOR;
    }

    /**
     * Log all the fluid colors for debugging
     * This is called after the texture stitch event when all colors are registered
     */
    private void logFluidColors() {
        System.out.println("[HBM] Fluid color mappings:");
        System.out.println("[HBM] Brightness factor: " + BRIGHTNESS_FACTOR);
        System.out.println("[HBM] Registered fluid colors:");

        // Sort fluids by name for cleaner output
        List<String> fluidNames = new ArrayList<>(fluidColors.keySet());
        Collections.sort(fluidNames);

        for (String fluidName : fluidNames) {
            int color = fluidColors.get(fluidName);
            System.out.println("[HBM]   " + fluidName + ": 0x" + Integer.toHexString(color).toUpperCase());
        }
    }

    /**
     * Handle texture stitching to register fluid colors
     */
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        TextureMap map = event.map;

        // Only handle block textures
        if (map.getTextureType() != 0) {
            return;
        }

        // Register colors for all HBM fluids
        for (FluidType hbmFluid : Fluids.getAll()) {
            if (hbmFluid == Fluids.NONE) continue;

            String fluidName = hbmFluid.getName().toLowerCase(Locale.US);
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(hbmFluid);

            if (forgeFluid != null) {
                registerFluidColor(forgeFluid, hbmFluid);
            }
        }
    }

    @SubscribeEvent
    public void onTextureStitchPost(TextureStitchEvent.Post event) {
        // Log all the fluid colors after they've been registered
        logFluidColors();
    }

    /**
     * Register color for a specific fluid
     */
    private void registerFluidColor(Fluid forgeFluid, FluidType hbmFluid) {
        String fluidName = forgeFluid.getName().toLowerCase(Locale.US);
        int color = hbmFluid.getColor();

        // Store the color for later use
        fluidColors.put(fluidName, color);

        // If the fluid is a ColoredForgeFluid, it already has the color
        if (forgeFluid instanceof ColoredForgeFluid) {
            System.out.println("[HBM] Registered color for Forge fluid: " + fluidName + " (0x" + Integer.toHexString(color) + ")");
        }
    }

    /**
     * Apply color to a fluid during rendering
     * @param fluidName The name of the fluid
     * @return true if color was applied, false otherwise
     */
    public static boolean applyFluidColor(String fluidName) {
        Integer color = fluidColors.get(fluidName.toLowerCase(Locale.US));

        if (color != null) {
            // Apply the color with brightness adjustment
            applyColor(color, BRIGHTNESS_FACTOR);
            return true;
        }

        return false;
    }

    /**
     * Apply color to a fluid during rendering
     * @param fluid The fluid to apply color to
     * @return true if color was applied, false otherwise
     */
    public static boolean applyFluidColor(Fluid fluid) {
        if (fluid == null) return false;
        return applyFluidColor(fluid.getName());
    }

    /**
     * Apply a color with brightness adjustment
     * @param color The RGB color as an integer
     * @param brightnessFactor Factor to adjust brightness (>1 = brighter, <1 = darker)
     */
    private static void applyColor(int color, float brightnessFactor) {
        float r = ((color >> 16) & 0xFF) / 255.0f * brightnessFactor;
        float g = ((color >> 8) & 0xFF) / 255.0f * brightnessFactor;
        float b = (color & 0xFF) / 255.0f * brightnessFactor;

        // Clamp values to prevent overflow
        r = Math.min(1.0f, r);
        g = Math.min(1.0f, g);
        b = Math.min(1.0f, b);

        GL11.glColor3f(r, g, b);
    }

    /**
     * Get the color for a fluid
     * @param fluidName The name of the fluid
     * @return The RGB color as an integer, or 0xFFFFFF if not found
     */
    public static int getFluidColor(String fluidName) {
        Integer color = fluidColors.get(fluidName.toLowerCase(Locale.US));
        return color != null ? color : 0xFFFFFF;
    }

    /**
     * Get the color for a fluid
     * @param fluid The fluid
     * @return The RGB color as an integer, or 0xFFFFFF if not found
     */
    public static int getFluidColor(Fluid fluid) {
        if (fluid == null) return 0xFFFFFF;
        return getFluidColor(fluid.getName());
    }

    /**
     * Get the fluid color for AE2 rendering.
     * This returns the color in ARGB format (with alpha channel).
     * @param fluid The fluid to get the color for
     * @return The color in ARGB format
     */
    public static int getFluidColorForAE2(Fluid fluid) {
        if (fluid instanceof ColoredForgeFluid) {
            return ((ColoredForgeFluid) fluid).getColorARGB();
        } else {
            // Get the color from the fluid color map
            int color = getFluidColor(fluid);
            // Add alpha channel (0xFF) to the RGB color
            return 0xFF000000 | color;
        }
    }
}
