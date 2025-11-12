package api.ntm1of90.compat.fluid.render;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;

import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

/**
 * A utility class for rendering NTM fluids in other mods' GUIs and inventories.
 * This class provides methods that other mods can use to render our fluids correctly.
 */
@SideOnly(Side.CLIENT)
public class NTMFluidCompatRenderer {

    /**
     * Initialize the renderer.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        // Only register for render events on the client side
        if (cpw.mods.fml.common.FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new NTMFluidCompatRenderer());
            System.out.println("[NTM] Fluid compat renderer registered for client-side events");
        }
        System.out.println("[NTM] Fluid compat renderer initialized");
    }

    /**
     * Render a fluid in a GUI or inventory
     * @param fluid The fluid to render
     * @param x The x position to render at
     * @param y The y position to render at
     * @param width The width to render
     * @param height The height to render
     */
    public static void renderFluid(Fluid fluid, int x, int y, int width, int height) {
        if (fluid == null) return;

        // Get the fluid's icon
        IIcon icon = getFluidIcon(fluid);

        // Get the fluid's color
        int color = getFluidColor(fluid);

        // Render the fluid
        renderFluidWithIconAndColor(icon, color, x, y, width, height);
    }

    /**
     * Render a fluid stack in a GUI or inventory
     * @param fluidStack The fluid stack to render
     * @param x The x position to render at
     * @param y The y position to render at
     * @param width The width to render
     * @param height The height to render
     */
    public static void renderFluidStack(FluidStack fluidStack, int x, int y, int width, int height) {
        if (fluidStack == null || fluidStack.getFluid() == null) return;

        renderFluid(fluidStack.getFluid(), x, y, width, height);
    }

    /**
     * Get the icon to use for a fluid in inventory rendering
     * @param fluid The fluid to get the icon for
     * @return The icon to use for the fluid
     */
    public static IIcon getFluidIcon(Fluid fluid) {
        if (fluid == null) return null;

        // Try to get the inventory icon from ColoredForgeFluid
        if (fluid instanceof ColoredForgeFluid) {
            IIcon icon = ((ColoredForgeFluid) fluid).getInventoryIcon();
            if (icon != null) {
                return icon;
            }
        }

        // Try to get the still icon
        IIcon stillIcon = fluid.getStillIcon();
        if (stillIcon != null) {
            return stillIcon;
        }

        // Try to get the flowing icon
        return fluid.getFlowingIcon();
    }

    /**
     * Get the color to use for a fluid in inventory rendering
     * @param fluid The fluid to get the color for
     * @return The color to use for the fluid
     */
    public static int getFluidColor(Fluid fluid) {
        if (fluid == null) return 0xFFFFFF;

        // Try to get the color from ColoredForgeFluid
        if (fluid instanceof ColoredForgeFluid) {
            return ((ColoredForgeFluid) fluid).getColor();
        }

        // Try to get the color from the fluid
        FluidStack fluidStack = new FluidStack(fluid, 1000);
        return fluid.getColor(fluidStack);
    }

    /**
     * Render a fluid with the given icon and color
     * @param icon The icon to use
     * @param color The color to use
     * @param x The x position to render at
     * @param y The y position to render at
     * @param width The width to render
     * @param height The height to render
     */
    public static void renderFluidWithIconAndColor(IIcon icon, int color, int x, int y, int width, int height) {
        if (icon == null) return;

        // Convert the color to RGB components
        float r = ((color >> 16) & 0xFF) / 255.0F;
        float g = ((color >> 8) & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;

        // Apply the color using OpenGL
        GL11.glColor4f(r, g, b, 1.0F);

        // Bind the texture map
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        // Render the fluid
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + width, y + height, 0, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(x + width, y, 0, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(x, y, 0, icon.getMinU(), icon.getMinV());
        tessellator.draw();

        // Reset the color
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Get the NTM fluid type for a Forge fluid
     * @param fluid The Forge fluid
     * @return The NTM fluid type, or Fluids.NONE if not found
     */
    public static FluidType getNTMFluidType(Fluid fluid) {
        if (fluid == null) return Fluids.NONE;

        // Try to get the NTM fluid type from ColoredForgeFluid
        if (fluid instanceof ColoredForgeFluid) {
            FluidType ntmFluid = ((ColoredForgeFluid) fluid).getHbmFluidType();
            if (ntmFluid != null) {
                return ntmFluid;
            }
        }

        // Try to get the NTM fluid type from the registry
        return FluidMappingRegistry.getHbmFluidType(fluid);
    }

    /**
     * Get the texture to use for a fluid in inventory rendering
     * @param fluid The fluid to get the texture for
     * @return The texture to use for the fluid
     */
    public static ResourceLocation getFluidTexture(Fluid fluid) {
        if (fluid == null) return null;

        // Get the NTM fluid type
        FluidType ntmFluid = getNTMFluidType(fluid);

        if (ntmFluid != null && ntmFluid != Fluids.NONE) {
            // Use the NTM fluid texture
            return ntmFluid.getTexture();
        }

        // Fall back to a default texture
        return new ResourceLocation(RefStrings.MODID, "textures/gui/forgefluids/water.png");
    }
}
