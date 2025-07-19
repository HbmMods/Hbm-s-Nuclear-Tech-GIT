package api.ntm1of90.compat.fluid.render;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Viscous;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

/**
 * HBM-style fluid renderer that uses base textures with color tinting
 * instead of individual PNG files for each fluid.
 */
public class HBMStyleFluidRenderer {

    // Base texture types for different fluid categories
    public enum BaseTextureType {
        LIQUID("liquid_base"),
        GAS("gas_base"),
        VISCOUS("viscous_base"),
        PLASMA("plasma_base"),
        ANTIMATTER("antimatter_base"),
        CORROSIVE("corrosive_base"),
        DEFAULT("fluid_base");

        private final String textureName;

        BaseTextureType(String textureName) {
            this.textureName = textureName;
        }

        public String getTextureName() {
            return textureName;
        }

        public ResourceLocation getStillTexture() {
            return new ResourceLocation(RefStrings.MODID, "textures/blocks/fluids/" + textureName + "_still.png");
        }

        public ResourceLocation getFlowingTexture() {
            return new ResourceLocation(RefStrings.MODID, "textures/blocks/fluids/" + textureName + "_flowing.png");
        }

        public ResourceLocation getInventoryTexture() {
            return new ResourceLocation(RefStrings.MODID, "textures/gui/fluids/" + textureName + ".png");
        }
    }

    // Maps to store base texture icons
    @SideOnly(Side.CLIENT)
    private static final Map<BaseTextureType, IIcon> stillBaseIcons = new HashMap<>();
    @SideOnly(Side.CLIENT)
    private static final Map<BaseTextureType, IIcon> flowingBaseIcons = new HashMap<>();
    @SideOnly(Side.CLIENT)
    private static final Map<BaseTextureType, IIcon> inventoryBaseIcons = new HashMap<>();

    // Cache for fluid type to base texture mapping
    private static final Map<FluidType, BaseTextureType> fluidTypeCache = new HashMap<>();

    /**
     * Initialize the HBM-style renderer
     */
    public static void initialize() {
        System.out.println("[NTM] HBM-style fluid renderer initialized");

        // Analyze all HBM fluids and cache their base texture types
        analyzeHBMFluids();

        // Print detailed statistics
        printRenderingStatistics();
    }

    /**
     * Analyze all HBM fluids to determine which ones should use tinted rendering
     */
    private static void analyzeHBMFluids() {
        int tintedCount = 0;
        int pngCount = 0;

        for (FluidType hbmFluid : Fluids.getAll()) {
            if (hbmFluid == Fluids.NONE) continue;

            BaseTextureType baseType = determineBaseTextureType(hbmFluid);
            fluidTypeCache.put(hbmFluid, baseType);

            if (shouldUseTintedRendering(hbmFluid)) {
                tintedCount++;
                System.out.println("[NTM] Fluid '" + hbmFluid.getName() + "' will use tinted rendering with base: " + baseType.name());
            } else {
                pngCount++;
            }
        }

        System.out.println("[NTM] Analyzed " + (tintedCount + pngCount) + " fluids: " + tintedCount + " tinted, " + pngCount + " PNG-based");
        System.out.println("[NTM] HBM-style rendering will be used for " + tintedCount + " fluids, PNG rendering for " + pngCount + " fluids");
    }

    /**
     * Determine if a fluid should use tinted rendering like HBM does
     */
    public static boolean shouldUseTintedRendering(FluidType hbmFluid) {
        if (hbmFluid == null || hbmFluid == Fluids.NONE) return false;

        // Use tinted rendering if:
        // 1. The fluid has renderWithTint flag set, OR
        // 2. The fluid has a color that's not the default white/transparent, OR
        // 3. The fluid doesn't have a specific PNG texture (uses base texture)

        if (hbmFluid.renderWithTint) {
            return true;
        }

        // Check if fluid has a meaningful color (not white/transparent)
        int color = hbmFluid.getColor();
        if (color != 0xFFFFFF && color != 0x000000 && color != 0) {
            return true;
        }

        return false;
    }

    /**
     * Determine the appropriate base texture type for a fluid
     */
    public static BaseTextureType determineBaseTextureType(FluidType hbmFluid) {
        if (hbmFluid == null || hbmFluid == Fluids.NONE) {
            return BaseTextureType.DEFAULT;
        }

        // Check for special fluid types first
        if (hbmFluid.isAntimatter()) {
            return BaseTextureType.ANTIMATTER;
        }

        if (hbmFluid.isCorrosive()) {
            return BaseTextureType.CORROSIVE;
        }

        // Check for plasma (name contains "plasma")
        if (hbmFluid.getName().toLowerCase().contains("plasma")) {
            return BaseTextureType.PLASMA;
        }

        // Check fluid traits
        if (hbmFluid.hasTrait(FT_Gaseous.class)) {
            return BaseTextureType.GAS;
        }

        if (hbmFluid.hasTrait(FT_Viscous.class)) {
            return BaseTextureType.VISCOUS;
        }

        // Default to liquid for most fluids
        return BaseTextureType.LIQUID;
    }

    /**
     * Get the cached base texture type for a fluid
     */
    public static BaseTextureType getBaseTextureType(FluidType hbmFluid) {
        BaseTextureType cached = fluidTypeCache.get(hbmFluid);
        return cached != null ? cached : determineBaseTextureType(hbmFluid);
    }

    /**
     * Register base texture icons during texture stitching
     */
    @SideOnly(Side.CLIENT)
    public static void registerBaseTextures(TextureMap textureMap) {
        for (BaseTextureType baseType : BaseTextureType.values()) {
            // Register still texture
            String stillTexturePath = "blocks/fluids/" + baseType.getTextureName() + "_still";
            IIcon stillIcon = textureMap.registerIcon(RefStrings.MODID + ":" + stillTexturePath);
            stillBaseIcons.put(baseType, stillIcon);

            // Register flowing texture
            String flowingTexturePath = "blocks/fluids/" + baseType.getTextureName() + "_flowing";
            IIcon flowingIcon = textureMap.registerIcon(RefStrings.MODID + ":" + flowingTexturePath);
            flowingBaseIcons.put(baseType, flowingIcon);

            System.out.println("[NTM] Registered base texture: " + baseType.name() + " (still: " + stillTexturePath + ", flowing: " + flowingTexturePath + ")");
        }
    }

    /**
     * Register inventory base texture icons during texture stitching
     */
    @SideOnly(Side.CLIENT)
    public static void registerInventoryBaseTextures(TextureMap textureMap) {
        for (BaseTextureType baseType : BaseTextureType.values()) {
            // Register inventory texture
            String inventoryTexturePath = "gui/fluids/" + baseType.getTextureName();
            IIcon inventoryIcon = textureMap.registerIcon(RefStrings.MODID + ":" + inventoryTexturePath);
            inventoryBaseIcons.put(baseType, inventoryIcon);

            System.out.println("[NTM] Registered inventory base texture: " + baseType.name() + " (" + inventoryTexturePath + ")");
        }
    }

    /**
     * Get the still icon for a base texture type
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getStillIcon(BaseTextureType baseType) {
        return stillBaseIcons.get(baseType);
    }

    /**
     * Get the flowing icon for a base texture type
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getFlowingIcon(BaseTextureType baseType) {
        return flowingBaseIcons.get(baseType);
    }

    /**
     * Get the inventory icon for a base texture type
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getInventoryIcon(BaseTextureType baseType) {
        return inventoryBaseIcons.get(baseType);
    }

    /**
     * Apply HBM-style color tinting to OpenGL rendering
     */
    @SideOnly(Side.CLIENT)
    public static void applyFluidTint(FluidType hbmFluid) {
        if (hbmFluid == null || !shouldUseTintedRendering(hbmFluid)) {
            GL11.glColor3f(1.0f, 1.0f, 1.0f); // Reset to white
            return;
        }

        int color = hbmFluid.renderWithTint ? hbmFluid.getTint() : hbmFluid.getColor();

        double r = ((color & 0xff0000) >> 16) / 255.0;
        double g = ((color & 0x00ff00) >> 8) / 255.0;
        double b = ((color & 0x0000ff) >> 0) / 255.0;

        GL11.glColor3d(r, g, b);
    }

    /**
     * Reset OpenGL color to default
     */
    @SideOnly(Side.CLIENT)
    public static void resetTint() {
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
    }

    /**
     * Get the appropriate still icon for a fluid type
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getStillIconForFluid(FluidType hbmFluid) {
        if (hbmFluid == null || hbmFluid == Fluids.NONE) {
            return getStillIcon(BaseTextureType.DEFAULT);
        }

        BaseTextureType baseType = getBaseTextureType(hbmFluid);
        return getStillIcon(baseType);
    }

    /**
     * Get the appropriate flowing icon for a fluid type
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getFlowingIconForFluid(FluidType hbmFluid) {
        if (hbmFluid == null || hbmFluid == Fluids.NONE) {
            return getFlowingIcon(BaseTextureType.DEFAULT);
        }

        BaseTextureType baseType = getBaseTextureType(hbmFluid);
        return getFlowingIcon(baseType);
    }

    /**
     * Get the appropriate inventory icon for a fluid type
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getInventoryIconForFluid(FluidType hbmFluid) {
        if (hbmFluid == null || hbmFluid == Fluids.NONE) {
            return getInventoryIcon(BaseTextureType.DEFAULT);
        }

        BaseTextureType baseType = getBaseTextureType(hbmFluid);
        return getInventoryIcon(baseType);
    }

    /**
     * Check if base textures are loaded
     */
    @SideOnly(Side.CLIENT)
    public static boolean areBaseTexturesLoaded() {
        return !stillBaseIcons.isEmpty() && !flowingBaseIcons.isEmpty() && !inventoryBaseIcons.isEmpty();
    }

    /**
     * Get the number of fluids that will use tinted rendering
     */
    public static int getTintedFluidCount() {
        int count = 0;
        for (FluidType hbmFluid : Fluids.getAll()) {
            if (shouldUseTintedRendering(hbmFluid)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get the number of fluids that will use PNG rendering
     */
    public static int getPngFluidCount() {
        int count = 0;
        for (FluidType hbmFluid : Fluids.getAll()) {
            if (!shouldUseTintedRendering(hbmFluid)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Print detailed statistics about fluid rendering
     */
    public static void printRenderingStatistics() {
        System.out.println("[NTM] === HBM-Style Fluid Rendering Statistics ===");
        System.out.println("[NTM] Total fluids: " + Fluids.getAll().length);
        System.out.println("[NTM] Tinted rendering: " + getTintedFluidCount());
        System.out.println("[NTM] PNG rendering: " + getPngFluidCount());
        System.out.println("[NTM] Base textures loaded: " + areBaseTexturesLoaded());

        // Print breakdown by base texture type
        Map<BaseTextureType, Integer> typeCount = new HashMap<>();
        for (FluidType hbmFluid : Fluids.getAll()) {
            if (shouldUseTintedRendering(hbmFluid)) {
                BaseTextureType type = getBaseTextureType(hbmFluid);
                typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
            }
        }

        System.out.println("[NTM] Breakdown by base texture type:");
        for (Map.Entry<BaseTextureType, Integer> entry : typeCount.entrySet()) {
            System.out.println("[NTM]   " + entry.getKey().name() + ": " + entry.getValue() + " fluids");
        }
        System.out.println("[NTM] ===============================================");
    }
}
