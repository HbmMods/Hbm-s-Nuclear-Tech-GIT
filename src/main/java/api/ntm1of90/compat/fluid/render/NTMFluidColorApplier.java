package api.ntm1of90.compat.fluid.render;

import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

/**
 * Utility class for applying fluid colors in rendering.
 * This class provides methods for applying fluid colors to OpenGL rendering.
 */
public class NTMFluidColorApplier {

    private static float brightnessFactor = 1.0f;

    /**
     * Initialize the fluid color applier.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        System.out.println("[NTM] Fluid color applier initialized");
    }

    /**
     * Set the brightness factor for fluid colors.
     * This can be used to make fluids appear brighter or darker.
     *
     * @param factor The brightness factor (1.0 = normal, > 1.0 = brighter, < 1.0 = darker)
     */
    public static void setBrightnessFactor(float factor) {
        brightnessFactor = factor;
        System.out.println("[NTM] Fluid color brightness factor set to " + factor);
    }

    /**
     * Apply a fluid's color to OpenGL rendering.
     * This method sets the OpenGL color to the fluid's color.
     *
     * @param fluid The fluid to apply the color for
     * @return True if a color was applied, false otherwise
     */
    public static boolean applyFluidColor(Fluid fluid) {
        if (fluid == null) {
            // Reset to default color
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            return false;
        }

        if (fluid instanceof ColoredForgeFluid) {
            // Get the color from the fluid
            int color = ((ColoredForgeFluid) fluid).getColor();

            // Apply the color with brightness factor
            float r = ((color >> 16) & 0xFF) / 255.0f * brightnessFactor;
            float g = ((color >> 8) & 0xFF) / 255.0f * brightnessFactor;
            float b = (color & 0xFF) / 255.0f * brightnessFactor;

            // Clamp values to [0, 1]
            r = Math.min(1.0f, Math.max(0.0f, r));
            g = Math.min(1.0f, Math.max(0.0f, g));
            b = Math.min(1.0f, Math.max(0.0f, b));

            GL11.glColor4f(r, g, b, 1.0f);
            return true;
        } else {
            // Reset to default color
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            return false;
        }
    }

    /**
     * Reset the OpenGL color to default.
     * This method resets the OpenGL color to white.
     */
    public static void resetColor() {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Get a fluid's color for AE2 rendering.
     * This method returns the fluid's color in ARGB format for AE2 rendering.
     *
     * @param fluid The fluid to get the color for
     * @return The fluid's color in ARGB format
     */
    public static int getFluidColorForAE2(Fluid fluid) {
        if (fluid == null) {
            return 0xFFFFFFFF; // White with full alpha
        }

        if (fluid instanceof ColoredForgeFluid) {
            // Get the color from the fluid
            return ((ColoredForgeFluid) fluid).getColorARGB();
        }

        return 0xFFFFFFFF; // White with full alpha
    }

    /**
     * Get a fluid's color for inventory rendering.
     * This method returns the fluid's color in RGB format for inventory rendering.
     *
     * @param fluid The fluid to get the color for
     * @return The fluid's color in RGB format
     */
    public static int getFluidColorForInventory(Fluid fluid) {
        if (fluid == null) {
            return 0xFFFFFF; // White
        }

        if (fluid instanceof ColoredForgeFluid) {
            // Get the color from the fluid
            return ((ColoredForgeFluid) fluid).getColor();
        }

        return 0xFFFFFF; // White
    }
}
