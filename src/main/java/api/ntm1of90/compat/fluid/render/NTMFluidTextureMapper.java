package api.ntm1of90.compat.fluid.render;

import api.ntm1of90.compat.fluid.registry.FluidRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

import java.util.Locale;

/**
 * Mapper for fluid textures.
 * This class provides a bridge between the old texture system and the new FluidRegistry.
 */
public class NTMFluidTextureMapper {

    /**
     * Initialize the texture mapper.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        // Only perform client-side initialization if we're on the client
        if (cpw.mods.fml.common.FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            initializeClient();
        }

        System.out.println("[NTM] Fluid texture mapper initialized");
    }

    /**
     * Initialize client-side functionality.
     * This is only called on the client side.
     */
    @SideOnly(Side.CLIENT)
    private static void initializeClient() {
        System.out.println("[NTM] Fluid texture mapper client-side initialized");
    }

    /**
     * Get the still icon for a fluid.
     * @param fluidName The name of the fluid
     * @return The still icon for the fluid
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getStillIcon(String fluidName) {
        return FluidRegistry.getStillIcon(fluidName.toLowerCase(Locale.US));
    }

    /**
     * Get the flowing icon for a fluid.
     * @param fluidName The name of the fluid
     * @return The flowing icon for the fluid
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getFlowingIcon(String fluidName) {
        return FluidRegistry.getFlowingIcon(fluidName.toLowerCase(Locale.US));
    }

    /**
     * Get the inventory icon for a fluid.
     * @param fluidName The name of the fluid
     * @return The inventory icon for the fluid
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getInventoryIcon(String fluidName) {
        return FluidRegistry.getInventoryIcon(fluidName.toLowerCase(Locale.US));
    }
}
