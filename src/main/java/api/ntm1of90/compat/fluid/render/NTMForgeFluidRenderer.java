package api.ntm1of90.compat.fluid.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Handles rendering of Forge fluid blocks with NTM colors and textures.
 * This class provides hooks for fluid rendering.
 */
public class NTMForgeFluidRenderer {

    /**
     * Initialize the renderer.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        // Only register for render events on the client side
        if (cpw.mods.fml.common.FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new NTMForgeFluidRenderer());
            System.out.println("[NTM] Forge fluid renderer registered for client-side events");
        }
        System.out.println("[NTM] Forge fluid renderer initialized");
    }

    /**
     * This method is called when a fluid block is rendered.
     * It applies the appropriate color to the fluid.
     *
     * @param fluid The fluid being rendered
     * @return true if color was applied, false otherwise
     */
    @SideOnly(Side.CLIENT)
    public static boolean applyFluidColor(net.minecraftforge.fluids.Fluid fluid) {
        if (fluid instanceof ColoredForgeFluid) {
            return NTMFluidColorApplier.applyFluidColor(fluid);
        }
        return false;
    }

    /**
     * Reset the color after rendering a fluid.
     */
    @SideOnly(Side.CLIENT)
    public static void resetFluidColor() {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }
}
