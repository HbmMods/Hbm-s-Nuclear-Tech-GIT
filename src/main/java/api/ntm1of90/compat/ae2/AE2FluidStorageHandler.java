package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles integration with AE2's fluid storage system.
 * This class directly integrates with AE2's fluid storage cells.
 */
@SideOnly(Side.CLIENT)
public class AE2FluidStorageHandler {

    private static boolean initialized = false;
    private static Map<String, Integer> fluidColors = new HashMap<>();

    // Cache for AE2 classes and methods to avoid repeated reflection
    private static Class<?> fluidCellClass = null;
    private static Method getFluidFromCell = null;

    /**
     * Initialize the AE2 fluid storage handler.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 fluid storage handler");
            return;
        }

        try {
            // Register for tick events
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new AE2FluidStorageHandler());
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new AE2FluidStorageHandler());

            // Initialize AE2 integration
            initializeAE2();

            System.out.println("[NTM] AE2 fluid storage handler initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 fluid storage handler: " + e.getMessage());
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
            // Try to hook into AE2's fluid storage system
            fluidCellClass = Class.forName("appeng.items.storage.ItemFluidStorage");

            // Get the method to extract fluid from a cell
            getFluidFromCell = fluidCellClass.getMethod("getFluidFromCell", ItemStack.class);

            // Register our fluid colors with AE2
            registerFluidColors();

            System.out.println("[NTM] Successfully hooked into AE2's fluid storage system");
        } catch (Exception e) {
            System.err.println("[NTM] Could not hook into AE2's fluid storage system: " + e.getMessage());
        }
    }

    /**
     * Register fluid colors with AE2.
     * This method is only called if AE2 is loaded.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerFluidColors() {
        try {
            // Try to register with AE2's fluid storage system
            Class<?> fluidStorageClass = Class.forName("appeng.me.storage.FluidCellInventory");

            // Register colors for all fluids
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    int color = ((ColoredForgeFluid) fluid).getColorARGB();

                    // Store the color for later use
                    fluidColors.put(fluid.getName(), color);

                    // Register with AE2
                    try {
                        Method registerColorMethod = fluidStorageClass.getMethod("registerFluidColor", Fluid.class, int.class);
                        registerColorMethod.invoke(null, fluid, color);
                        System.out.println("[NTM] Registered color for fluid in AE2 storage: " + fluid.getName() + " (0x" + Integer.toHexString(color) + ")");
                    } catch (Exception e) {
                        System.err.println("[NTM] Could not register color for fluid in AE2 storage: " + e.getMessage());
                    }
                }
            }

            System.out.println("[NTM] Registered fluid colors with AE2's fluid storage system");
        } catch (Exception e) {
            System.err.println("[NTM] Could not register fluid colors with AE2's fluid storage system: " + e.getMessage());
        }
    }

    /**
     * Handle client tick events to update fluid colors in AE2 fluid cells
     */
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!Loader.isModLoaded("appliedenergistics2")) return;
        if (event.phase != TickEvent.Phase.END) return;

        // Only update once per second to avoid performance issues
        if (Minecraft.getMinecraft().theWorld != null &&
            Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 20 == 0) {
            updateFluidCellColors();
        }
    }

    /**
     * Update fluid colors in AE2 fluid cells
     */
    @Optional.Method(modid = "appliedenergistics2")
    private void updateFluidCellColors() {
        try {
            // Try to update fluid colors in AE2's fluid cells
            Class<?> fluidCellInventoryClass = Class.forName("appeng.me.storage.FluidCellInventory");

            // Register colors for all fluids
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    int color = ((ColoredForgeFluid) fluid).getColorARGB();

                    // Register with AE2
                    try {
                        Method registerColorMethod = fluidCellInventoryClass.getMethod("registerFluidColor", Fluid.class, int.class);
                        registerColorMethod.invoke(null, fluid, color);
                    } catch (Exception e) {
                        // Ignore errors
                    }
                }
            }
        } catch (Exception e) {
            // Ignore errors
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
