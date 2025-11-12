package api.ntm1of90.compat.fluid.registry;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Registry for fluid textures and properties.
 * This class loads fluid definitions from the Fluids.java class and registers them with Minecraft.
 */
public class FluidRegistry {

    // Maps fluid names to their still and flowing icons
    private static final Map<String, IIcon> stillIcons = new HashMap<>();
    private static final Map<String, IIcon> flowingIcons = new HashMap<>();
    private static final Map<String, IIcon> inventoryIcons = new HashMap<>();

    // Maps fluid names to their properties
    private static final Map<String, FluidProperties> fluidProperties = new HashMap<>();

    // Default properties for fluids not defined in Fluids.java
    private static FluidProperties defaultProperties;

    /**
     * Initialize the fluid registry.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        // Only register for texture stitch events on the client side
        if (cpw.mods.fml.common.FMLCommonHandler.instance().getSide() == cpw.mods.fml.relauncher.Side.CLIENT) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new FluidRegistry());
            System.out.println("[NTM] Registered fluid registry for texture stitch events");
        }

        // Load fluid properties from Fluids.java
        loadFluidProperties();

        System.out.println("[NTM] Fluid registry initialized with " + fluidProperties.size() + " fluids");
    }

    /**
     * Load fluid properties from Fluids.java
     */
    private static void loadFluidProperties() {
        try {
            // Create default properties
            defaultProperties = new FluidProperties(
                "default",
                0xFFFFFF,
                "fluid_still",
                "fluid_flowing",
                "forgefluid/default"
            );

            // Load fluid properties from all HBM fluids
            FluidType[] allFluids = Fluids.getAll();
            for (FluidType hbmFluid : allFluids) {
                if (hbmFluid == Fluids.NONE) continue;

                // Convert fluid name to lowercase as requested
                String name = hbmFluid.getName().toLowerCase(Locale.US);
                int color = hbmFluid.getColor();

                // Generate texture names based on fluid name
                // Using the same pattern as the original HBM texture system
                String stillTexture = "forgefluid/" + name;
                String flowingTexture = "forgefluid/" + name;
                String inventoryTexture = "forgefluid/" + name;

                FluidProperties properties = new FluidProperties(name, color, stillTexture, flowingTexture, inventoryTexture);
                fluidProperties.put(name, properties);

                System.out.println("[NTM] Loaded fluid properties for " + name + ": " + properties);
            }

        } catch (Exception e) {
            System.err.println("[NTM] Error loading fluid properties from Fluids.java: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle texture stitching to register fluid textures
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        TextureMap map = event.map;

        // Register textures for all HBM fluids
        for (FluidType hbmFluid : Fluids.getAll()) {
            if (hbmFluid == Fluids.NONE) continue;

            String fluidName = hbmFluid.getName().toLowerCase(Locale.US);
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(hbmFluid);

            if (forgeFluid != null) {
                if (map.getTextureType() == 0) {
                    // For block textures, register the fluid textures
                    registerFluidTextures(map, forgeFluid, fluidName);
                } else if (map.getTextureType() == 1) {
                    // For item textures, register the inventory icons
                    registerInventoryIcon(map, forgeFluid, fluidName);
                }
            }
        }
    }

    /**
     * Register textures for a specific fluid
     */
    @SideOnly(Side.CLIENT)
    private void registerFluidTextures(TextureMap map, Fluid forgeFluid, String fluidName) {
        try {
            // Get the fluid properties
            FluidProperties properties = getFluidProperties(fluidName);

            // Register the textures
            IIcon stillIcon = map.registerIcon(RefStrings.MODID + ":" + properties.stillTexture);
            IIcon flowingIcon = map.registerIcon(RefStrings.MODID + ":" + properties.flowingTexture);

            // Store the icons for later use
            stillIcons.put(fluidName, stillIcon);
            flowingIcons.put(fluidName, flowingIcon);

            // Set the icons on the Forge fluid
            forgeFluid.setIcons(stillIcon, flowingIcon);

            // If this is a ColoredForgeFluid, set the color
            if (forgeFluid instanceof ColoredForgeFluid) {
                ((ColoredForgeFluid) forgeFluid).setColor(properties.color);
                ((ColoredForgeFluid) forgeFluid).setHasCustomTexture(true);
            }

            System.out.println("[NTM] Registered textures for Forge fluid: " + fluidName +
                " (still: " + properties.stillTexture + ", flowing: " + properties.flowingTexture + ")");
        } catch (Exception e) {
            System.err.println("[NTM] Error registering textures for fluid " + fluidName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Register an inventory icon for a fluid
     */
    @SideOnly(Side.CLIENT)
    private void registerInventoryIcon(TextureMap map, Fluid forgeFluid, String fluidName) {
        try {
            // Get the fluid properties
            FluidProperties properties = getFluidProperties(fluidName);

            // Register the inventory icon
            IIcon icon = map.registerIcon(RefStrings.MODID + ":" + properties.inventoryTexture);

            // Store the icon for later use
            inventoryIcons.put(fluidName, icon);

            // If this is a ColoredForgeFluid, set the inventory icon
            if (forgeFluid instanceof ColoredForgeFluid) {
                ((ColoredForgeFluid) forgeFluid).setInventoryIcon(icon);
            }

            System.out.println("[NTM] Registered inventory icon for fluid: " + fluidName +
                " (texture: " + properties.inventoryTexture + ")");
        } catch (Exception e) {
            System.err.println("[NTM] Error registering inventory icon for fluid " + fluidName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get the properties for a fluid
     */
    public static FluidProperties getFluidProperties(String fluidName) {
        fluidName = fluidName.toLowerCase(Locale.US);

        // Try to get the properties from the map
        FluidProperties properties = fluidProperties.get(fluidName);

        // If not found, use the default properties
        if (properties == null) {
            System.out.println("[NTM] No properties found for fluid " + fluidName + ", using defaults");
            properties = defaultProperties;
        }

        return properties;
    }

    /**
     * Get the still icon for a fluid
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getStillIcon(String fluidName) {
        return stillIcons.get(fluidName.toLowerCase(Locale.US));
    }

    /**
     * Get the flowing icon for a fluid
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getFlowingIcon(String fluidName) {
        return flowingIcons.get(fluidName.toLowerCase(Locale.US));
    }

    /**
     * Get the inventory icon for a fluid
     */
    @SideOnly(Side.CLIENT)
    public static IIcon getInventoryIcon(String fluidName) {
        return inventoryIcons.get(fluidName.toLowerCase(Locale.US));
    }

    /**
     * Class to hold fluid properties
     */
    public static class FluidProperties {
        public final String name;
        public final int color;
        public final String stillTexture;
        public final String flowingTexture;
        public final String inventoryTexture;

        public FluidProperties(String name, int color, String stillTexture, String flowingTexture, String inventoryTexture) {
            this.name = name;
            this.color = color;
            this.stillTexture = stillTexture;
            this.flowingTexture = flowingTexture;
            this.inventoryTexture = inventoryTexture;
        }

        @Override
        public String toString() {
            return "FluidProperties{" +
                "name='" + name + '\'' +
                ", color=0x" + Integer.toHexString(color) +
                ", stillTexture='" + stillTexture + '\'' +
                ", flowingTexture='" + flowingTexture + '\'' +
                ", inventoryTexture='" + inventoryTexture + '\'' +
                '}';
        }
    }
}
