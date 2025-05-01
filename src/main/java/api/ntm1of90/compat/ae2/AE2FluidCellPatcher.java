package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Direct patcher for AE2's fluid cell rendering system.
 * This class directly patches AE2's fluid cell rendering to ensure fluid colors are applied.
 */
@SideOnly(Side.CLIENT)
public class AE2FluidCellPatcher {

    private static boolean initialized = false;
    private static Map<String, Integer> fluidColors = new HashMap<>();

    /**
     * Initialize the AE2 fluid cell patcher.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 fluid cell patcher");
            return;
        }

        try {
            // Register for tick events
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new AE2FluidCellPatcher());

            // Initialize AE2 integration
            initializeAE2();

            System.out.println("[NTM] AE2 fluid cell patcher initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 fluid cell patcher: " + e.getMessage());
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
            // Register all fluid colors
            registerFluidColors();

            // Try all possible approaches to patch AE2's fluid cell rendering
            patchFluidCellInventory();
            patchFluidCellRenderer();
            patchFluidCellDisplay();

            System.out.println("[NTM] Successfully initialized AE2 fluid cell patcher");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 fluid cell patcher: " + e.getMessage());
        }
    }

    /**
     * Register all fluid colors.
     */
    private static void registerFluidColors() {
        // Register colors for all fluids
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid instanceof ColoredForgeFluid) {
                int color = ((ColoredForgeFluid) fluid).getColorARGB();
                fluidColors.put(fluid.getName(), color);
            }
        }
    }

    /**
     * Patch AE2's FluidCellInventory class.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void patchFluidCellInventory() {
        try {
            // Try to patch AE2's FluidCellInventory class
            Class<?> fluidCellInventoryClass = Class.forName("appeng.me.storage.FluidCellInventory");

            // Try to get the fluid colors field
            Field fluidColorsField = null;
            for (Field field : fluidCellInventoryClass.getDeclaredFields()) {
                if (field.getType() == Map.class) {
                    field.setAccessible(true);
                    fluidColorsField = field;
                    break;
                }
            }

            if (fluidColorsField != null) {
                // Get the fluid colors map
                @SuppressWarnings("unchecked")
                Map<Fluid, Integer> fluidColorsMap = (Map<Fluid, Integer>) fluidColorsField.get(null);

                // Add our colors to the map
                for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                    if (fluid instanceof ColoredForgeFluid) {
                        int color = ((ColoredForgeFluid) fluid).getColorARGB();
                        fluidColorsMap.put(fluid, color);
                    }
                }

                System.out.println("[NTM] Successfully patched AE2's FluidCellInventory");
            } else {
                System.err.println("[NTM] Could not find fluid colors field in FluidCellInventory");
            }
        } catch (Exception e) {
            System.err.println("[NTM] Could not patch AE2's FluidCellInventory: " + e.getMessage());
        }
    }

    /**
     * Patch AE2's FluidCellRenderer class.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void patchFluidCellRenderer() {
        try {
            // Try to patch AE2's FluidCellRenderer class
            Class<?> fluidCellRendererClass = Class.forName("appeng.client.render.FluidCellRenderer");

            // Try to get the instance
            Method instanceMethod = fluidCellRendererClass.getMethod("instance");
            Object instance = instanceMethod.invoke(null);

            // Try to get the fluid colors field
            Field fluidColorsField = null;
            for (Field field : fluidCellRendererClass.getDeclaredFields()) {
                if (field.getType() == Map.class) {
                    field.setAccessible(true);
                    fluidColorsField = field;
                    break;
                }
            }

            if (fluidColorsField != null) {
                // Get the fluid colors map
                @SuppressWarnings("unchecked")
                Map<Fluid, Integer> fluidColorsMap = (Map<Fluid, Integer>) fluidColorsField.get(instance);

                // Add our colors to the map
                for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                    if (fluid instanceof ColoredForgeFluid) {
                        int color = ((ColoredForgeFluid) fluid).getColorARGB();
                        fluidColorsMap.put(fluid, color);
                    }
                }

                System.out.println("[NTM] Successfully patched AE2's FluidCellRenderer");
            } else {
                System.err.println("[NTM] Could not find fluid colors field in FluidCellRenderer");
            }
        } catch (Exception e) {
            System.err.println("[NTM] Could not patch AE2's FluidCellRenderer: " + e.getMessage());
        }
    }

    /**
     * Patch AE2's fluid cell display.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void patchFluidCellDisplay() {
        try {
            // Try to patch AE2's fluid cell display
            Class<?> fluidCellDisplayClass = Class.forName("appeng.client.gui.widgets.GuiFluidTank");

            // Try to get the fluid colors field
            Field fluidColorsField = null;
            for (Field field : fluidCellDisplayClass.getDeclaredFields()) {
                if (field.getType() == Map.class) {
                    field.setAccessible(true);
                    fluidColorsField = field;
                    break;
                }
            }

            if (fluidColorsField != null) {
                // Get the fluid colors map
                @SuppressWarnings("unchecked")
                Map<Fluid, Integer> fluidColorsMap = (Map<Fluid, Integer>) fluidColorsField.get(null);

                // Add our colors to the map
                for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                    if (fluid instanceof ColoredForgeFluid) {
                        int color = ((ColoredForgeFluid) fluid).getColorARGB();
                        fluidColorsMap.put(fluid, color);
                    }
                }

                System.out.println("[NTM] Successfully patched AE2's fluid cell display");
            } else {
                System.err.println("[NTM] Could not find fluid colors field in fluid cell display");
            }
        } catch (Exception e) {
            System.err.println("[NTM] Could not patch AE2's fluid cell display: " + e.getMessage());
        }
    }

    /**
     * Handle client tick events to periodically update fluid colors.
     */
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        // Update every 5 seconds to ensure colors are applied
        if (Minecraft.getMinecraft().theWorld != null &&
            Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 100 == 0) {
            try {
                patchFluidCellInventory();
                patchFluidCellRenderer();
                patchFluidCellDisplay();
            } catch (Exception e) {
                // Ignore errors
            }
        }
    }

    /**
     * Get the color for a fluid.
     */
    public static int getFluidColor(Fluid fluid) {
        if (fluid == null) return 0xFFFFFFFF;

        Integer color = fluidColors.get(fluid.getName());
        return color != null ? color : 0xFFFFFFFF;
    }
}
