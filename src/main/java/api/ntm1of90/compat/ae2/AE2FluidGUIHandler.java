package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles integration with AE2's GUI system for fluid rendering.
 * This class directly integrates with AE2's GUI system.
 */
@SideOnly(Side.CLIENT)
public class AE2FluidGUIHandler {

    private static boolean initialized = false;
    private static Map<String, Integer> fluidColors = new HashMap<>();

    // Cache for AE2 classes and methods to avoid repeated reflection
    private static Class<?> fluidSlotClass = null;
    private static Field fluidField = null;

    /**
     * Initialize the AE2 fluid GUI handler.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 fluid GUI handler");
            return;
        }

        try {
            // Register for GUI events
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new AE2FluidGUIHandler());

            // Initialize AE2 integration
            initializeAE2();

            System.out.println("[NTM] AE2 fluid GUI handler initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 fluid GUI handler: " + e.getMessage());
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
            // Try to hook into AE2's GUI system
            fluidSlotClass = Class.forName("appeng.client.gui.widgets.GuiFluidSlot");

            // Get the fluid field from the fluid slot class
            fluidField = fluidSlotClass.getDeclaredField("myFluid");
            fluidField.setAccessible(true);

            // Register our fluid colors with AE2
            registerFluidColors();

            System.out.println("[NTM] Successfully hooked into AE2's GUI system");
        } catch (Exception e) {
            System.err.println("[NTM] Could not hook into AE2's GUI system: " + e.getMessage());
        }
    }

    /**
     * Register fluid colors with AE2.
     * This method is only called if AE2 is loaded.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerFluidColors() {
        try {
            // Try to register with AE2's GUI system
            Class<?> fluidRenderClass = Class.forName("appeng.client.gui.widgets.GuiFluidTank");

            // Register colors for all fluids
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    int color = ((ColoredForgeFluid) fluid).getColorARGB();

                    // Store the color for later use
                    fluidColors.put(fluid.getName(), color);

                    // Register with AE2
                    try {
                        Method registerColorMethod = fluidRenderClass.getMethod("registerFluidColor", Fluid.class, int.class);
                        registerColorMethod.invoke(null, fluid, color);
                        System.out.println("[NTM] Registered color for fluid in AE2 GUI: " + fluid.getName() + " (0x" + Integer.toHexString(color) + ")");
                    } catch (Exception e) {
                        System.err.println("[NTM] Could not register color for fluid in AE2 GUI: " + e.getMessage());
                    }
                }
            }

            System.out.println("[NTM] Registered fluid colors with AE2's GUI system");
        } catch (Exception e) {
            System.err.println("[NTM] Could not register fluid colors with AE2's GUI system: " + e.getMessage());
        }
    }

    /**
     * Handle GUI open events to apply fluid colors to AE2 fluid slots
     */
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (!Loader.isModLoaded("appliedenergistics2")) return;

        GuiScreen gui = event.gui;
        if (gui == null) return;

        // Check if this is an AE2 GUI
        if (gui.getClass().getName().startsWith("appeng.client.gui")) {
            // Schedule a delayed update to ensure the GUI is fully initialized
            scheduleGuiUpdate(gui);
        }
    }

    /**
     * Schedule a delayed update for an AE2 GUI
     */
    private void scheduleGuiUpdate(final GuiScreen gui) {
        // Use a client tick handler to update the GUI after it's fully initialized
        cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new Object() {
            private int ticksWaited = 0;

            @SubscribeEvent
            public void onClientTick(cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent event) {
                if (event.phase != cpw.mods.fml.common.gameevent.TickEvent.Phase.END) return;

                ticksWaited++;

                // Wait a few ticks to ensure the GUI is fully initialized
                if (ticksWaited >= 5) {
                    // Unregister this handler
                    cpw.mods.fml.common.FMLCommonHandler.instance().bus().unregister(this);

                    // Update the GUI if it's still open
                    if (Minecraft.getMinecraft().currentScreen == gui) {
                        try {
                            // Find all fluid slots in the GUI
                            findAndUpdateFluidSlots(gui);
                        } catch (Exception e) {
                            // Ignore errors
                        }
                    }
                }
            }
        });
    }

    /**
     * Find and update all fluid slots in an AE2 GUI
     */
    @Optional.Method(modid = "appliedenergistics2")
    private void findAndUpdateFluidSlots(GuiScreen gui) {
        try {
            // Get all fields from the GUI class
            Field[] fields = gui.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                // Check if this field is a fluid slot
                if (fluidSlotClass != null && fluidSlotClass.isAssignableFrom(field.getType())) {
                    Object fluidSlot = field.get(gui);

                    // Get the fluid from the slot
                    if (fluidField != null) {
                        Object fluidObj = fluidField.get(fluidSlot);

                        if (fluidObj instanceof Fluid) {
                            Fluid fluid = (Fluid) fluidObj;

                            // Update the fluid color in the slot
                            updateFluidSlotColor(fluidSlot, fluid);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ignore errors
        }
    }

    /**
     * Update the color of a fluid in an AE2 fluid slot
     */
    @Optional.Method(modid = "appliedenergistics2")
    private void updateFluidSlotColor(Object fluidSlot, Fluid fluid) {
        try {
            // Get the color for this fluid
            int color = getFluidColorForAE2(fluid);

            // Set the color in the fluid slot
            Method setColorMethod = fluidSlotClass.getMethod("setColor", int.class);
            setColorMethod.invoke(fluidSlot, color);
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
