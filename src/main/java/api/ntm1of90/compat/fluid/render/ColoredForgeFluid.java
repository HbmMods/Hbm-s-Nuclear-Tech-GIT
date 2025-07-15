package api.ntm1of90.compat.fluid.render;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Viscous;

import api.ntm1of90.compat.fluid.util.NTMFluidLocalization;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

/**
 * A Forge Fluid implementation that carries color information from HBM's fluid system.
 * This allows for proper rendering of HBM fluids in Forge-compatible containers.
 */
public class ColoredForgeFluid extends Fluid {

    private int color;
    private FluidType hbmFluidType;
    private boolean hasCustomTexture = false;

    /**
     * Create a new ColoredForgeFluid from an HBM FluidType
     * @param fluidName The name of the fluid (should be lowercase)
     * @param hbmFluid The HBM FluidType to base this fluid on
     */
    public ColoredForgeFluid(String fluidName, FluidType hbmFluid) {
        super(fluidName);
        this.hbmFluidType = hbmFluid;
        this.color = hbmFluid.getColor();

        // Set basic properties based on the HBM fluid
        this.setDensity(hbmFluid.hasTrait(FT_Gaseous.class) ? -1000 : 1000);
        this.setViscosity(hbmFluid.hasTrait(FT_Viscous.class) ? 3000 : 1000);
        this.setTemperature(hbmFluid.temperature);
        this.setLuminosity(0); // No glowing trait found in the codebase

        // Textures will be set by NTMFluidTextureMapper during texture stitching
    }

    /**
     * Create a new ColoredForgeFluid with a specific color
     * @param fluidName The name of the fluid (should be lowercase)
     * @param color The color of the fluid as an RGB integer
     */
    public ColoredForgeFluid(String fluidName, int color) {
        super(fluidName);
        this.color = color;

        // Textures will be set by NTMFluidTextureMapper during texture stitching
    }

    /**
     * Get the color of this fluid
     * @return The RGB color as an integer
     */
    public int getColor() {
        return color;
    }

    /**
     * Get the HBM FluidType associated with this fluid
     * @return The HBM FluidType, or null if not created from an HBM fluid
     */
    public FluidType getHbmFluidType() {
        return hbmFluidType;
    }

    /**
     * Set whether this fluid has a custom texture
     * @param hasCustomTexture Whether this fluid has a custom texture
     */
    public void setHasCustomTexture(boolean hasCustomTexture) {
        this.hasCustomTexture = hasCustomTexture;
    }

    /**
     * Check if this fluid has a custom texture
     * @return Whether this fluid has a custom texture
     */
    public boolean hasCustomTexture() {
        return hasCustomTexture;
    }

    /**
     * Override to use our texture system
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getStillIcon() {
        IIcon icon = NTMFluidTextureMapper.getStillIcon(getName());
        return icon != null ? icon : super.getStillIcon();
    }

    /**
     * Override to use our texture system
     */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getFlowingIcon() {
        IIcon icon = NTMFluidTextureMapper.getFlowingIcon(getName());
        return icon != null ? icon : super.getFlowingIcon();
    }

    // Cache for inventory icons
    private static java.util.Map<String, IIcon> inventoryIcons = new java.util.HashMap<>();

    /**
     * Get the icon to use in inventory rendering
     */
    @SideOnly(Side.CLIENT)
    public IIcon getInventoryIcon() {
        // Try to get from cache first
        IIcon icon = inventoryIcons.get(getName());
        if (icon != null) {
            return icon;
        }

        // Try to get from the fluid registry
        icon = api.ntm1of90.compat.fluid.registry.FluidRegistry.getInventoryIcon(getName());
        if (icon != null) {
            return icon;
        }

        // Fall back to still icon
        return getStillIcon();
    }

    /**
     * Set the inventory icon for this fluid
     */
    @SideOnly(Side.CLIENT)
    public void setInventoryIcon(IIcon icon) {
        if (icon != null) {
            inventoryIcons.put(getName(), icon);
        }
    }

    /**
     * Apply the fluid's color for inventory rendering
     */
    @SideOnly(Side.CLIENT)
    public void applyColorForInventory() {
        // Apply the color using OpenGL
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;

        org.lwjgl.opengl.GL11.glColor4f(r, g, b, 1.0f);
    }

    /**
     * Reset the color after rendering
     */
    @SideOnly(Side.CLIENT)
    public static void resetColor() {
        org.lwjgl.opengl.GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Get the localized display name for this fluid
     * @return The localized display name
     */
    @Override
    public String getLocalizedName() {
        // Use our enhanced fluid localization system
        return NTMFluidLocalization.getForgeFluidDisplayName(this);
    }

    /**
     * Override to provide the fluid color for Forge rendering
     */
    @Override
    public int getColor(net.minecraftforge.fluids.FluidStack stack) {
        return color;
    }

    /**
     * Get the color with alpha channel (ARGB format)
     * This is used by AE2 integration for fluid rendering
     * @return The color in ARGB format (with alpha channel set to 0xFF)
     */
    public int getColorARGB() {
        // Add alpha channel (0xFF) to the RGB color
        return 0xFF000000 | color;
    }

    /**
     * Set the color for this fluid
     * @param color The color to set
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Override to provide the fluid color for item rendering
     * This is used by Minecraft to determine the color of items in the inventory
     */
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(net.minecraft.item.ItemStack stack, int pass) {
        return color;
    }

    /**
     * Render the fluid using the forgefluid texture
     * @param x The x position to render at
     * @param y The y position to render at
     * @param width The width to render
     * @param height The height to render
     */
    @SideOnly(Side.CLIENT)
    public void renderUsingForgeFluidTexture(int x, int y, int width, int height) {
        // Apply the fluid color
        applyColorForInventory();

        // Get the inventory icon
        IIcon icon = getInventoryIcon();

        if (icon != null) {
            // Bind the texture map
            net.minecraft.client.Minecraft.getMinecraft().getTextureManager().bindTexture(net.minecraft.client.renderer.texture.TextureMap.locationBlocksTexture);

            // Render the fluid
            net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(x, y + height, 0, icon.getMinU(), icon.getMaxV());
            tessellator.addVertexWithUV(x + width, y + height, 0, icon.getMaxU(), icon.getMaxV());
            tessellator.addVertexWithUV(x + width, y, 0, icon.getMaxU(), icon.getMinV());
            tessellator.addVertexWithUV(x, y, 0, icon.getMinU(), icon.getMinV());
            tessellator.draw();
        } else {
            // If no icon is available, try to use the still icon
            IIcon stillIcon = getStillIcon();

            if (stillIcon != null) {
                // Bind the texture map
                net.minecraft.client.Minecraft.getMinecraft().getTextureManager().bindTexture(net.minecraft.client.renderer.texture.TextureMap.locationBlocksTexture);

                // Render the fluid
                net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(x, y + height, 0, stillIcon.getMinU(), stillIcon.getMaxV());
                tessellator.addVertexWithUV(x + width, y + height, 0, stillIcon.getMaxU(), stillIcon.getMaxV());
                tessellator.addVertexWithUV(x + width, y, 0, stillIcon.getMaxU(), stillIcon.getMinV());
                tessellator.addVertexWithUV(x, y, 0, stillIcon.getMinU(), stillIcon.getMinV());
                tessellator.draw();
            } else {
                // Fallback to a colored quad if no icon is available
                net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.addVertex(x, y + height, 0);
                tessellator.addVertex(x + width, y + height, 0);
                tessellator.addVertex(x + width, y, 0);
                tessellator.addVertex(x, y, 0);
                tessellator.draw();
            }
        }

        // Reset color
        resetColor();
    }
}
