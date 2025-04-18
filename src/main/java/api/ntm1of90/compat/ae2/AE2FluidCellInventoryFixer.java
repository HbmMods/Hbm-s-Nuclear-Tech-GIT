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
 * Direct fixer for AE2's fluid cell inventory.
 * This class directly modifies AE2's fluid cell inventory to fix fluid colors.
 */
@SideOnly(Side.CLIENT)
public class AE2FluidCellInventoryFixer {

    private static boolean initialized = false;
    private static Map<String, Integer> fluidColors = new HashMap<>();

    // Cache for AE2 classes and fields
    private static Class<?> fluidCellInventoryClass = null;
    private static Field fluidColorsField = null;

    /**
     * Initialize the AE2 fluid cell inventory fixer.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 fluid cell inventory fixer");
            return;
        }

        try {
            // Register for tick events
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new AE2FluidCellInventoryFixer());

            // Initialize AE2 integration
            initializeAE2();

            System.out.println("[NTM] AE2 fluid cell inventory fixer initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 fluid cell inventory fixer: " + e.getMessage());
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
            // Get the FluidCellInventory class
            fluidCellInventoryClass = Class.forName("appeng.me.storage.FluidCellInventory");

            // Find the fluid colors field
            for (Field field : fluidCellInventoryClass.getDeclaredFields()) {
                if (field.getType() == Map.class) {
                    field.setAccessible(true);
                    fluidColorsField = field;
                    break;
                }
            }

            if (fluidColorsField == null) {
                System.err.println("[NTM] Could not find fluid colors field in FluidCellInventory");
                return;
            }

            // Register all fluid colors
            registerAllFluidColors();

            System.out.println("[NTM] Successfully initialized AE2 fluid cell inventory fixer");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 fluid cell inventory fixer: " + e.getMessage());
        }
    }

    /**
     * Register all fluid colors with AE2's fluid cell inventory.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerAllFluidColors() {
        if (fluidCellInventoryClass == null || fluidColorsField == null) return;

        try {
            // Get the fluid colors map
            @SuppressWarnings("unchecked")
            Map<Fluid, Integer> colors = (Map<Fluid, Integer>) fluidColorsField.get(null);

            if (colors == null) {
                System.err.println("[NTM] Fluid colors map is null in FluidCellInventory");
                return;
            }

            // Register colors for all fluids
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    int color = ((ColoredForgeFluid) fluid).getColorARGB();
                    fluidColors.put(fluid.getName(), color);

                    // Register with AE2
                    colors.put(fluid, color);
                }
            }

            System.out.println("[NTM] Registered all fluid colors with AE2's fluid cell inventory");
        } catch (Exception e) {
            System.err.println("[NTM] Error registering fluid colors with AE2's fluid cell inventory: " + e.getMessage());
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
            registerAllFluidColors();
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
