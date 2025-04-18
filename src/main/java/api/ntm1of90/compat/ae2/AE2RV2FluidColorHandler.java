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
 * Specialized handler for AE2 rv2-stable-10 fluid colors.
 * This class directly targets the specific implementation in this AE2 version.
 */
@SideOnly(Side.CLIENT)
public class AE2RV2FluidColorHandler {

    private static boolean initialized = false;
    private static boolean isRV2 = false;
    private static Object fluidRenderMap = null;
    private static Method registerFluidMethod = null;

    /**
     * Initialize the AE2 RV2 fluid color handler.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 RV2 fluid color handler");
            return;
        }

        try {
            // Check if this is AE2 RV2
            try {
                Class<?> versionClass = Class.forName("appeng.core.AEConfig");
                Field versionField = versionClass.getDeclaredField("VERSION");
                versionField.setAccessible(true);
                String version = (String) versionField.get(null);

                isRV2 = version != null && version.contains("rv2");
                System.out.println("[NTM] Detected AE2 version: " + version + " (RV2: " + isRV2 + ")");
            } catch (Exception e) {
                // If we can't determine the version, assume it's RV2
                isRV2 = true;
                System.out.println("[NTM] Could not determine AE2 version, assuming RV2");
            }

            if (!isRV2) {
                System.out.println("[NTM] Not using AE2 RV2 fluid color handler as this is not AE2 RV2");
                return;
            }

            // Register for tick events
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new AE2RV2FluidColorHandler());

            // Initialize AE2 integration
            initializeAE2RV2();

            System.out.println("[NTM] AE2 RV2 fluid color handler initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 RV2 fluid color handler: " + e.getMessage());
        }

        initialized = true;
    }

    /**
     * Initialize AE2 RV2 integration.
     * This method is only called if AE2 RV2 is loaded.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void initializeAE2RV2() {
        try {
            // Get the FluidRenderMap class
            Class<?> fluidRenderMapClass = Class.forName("appeng.client.texture.FluidRenderMap");

            // Get the instance method
            Method instanceMethod = fluidRenderMapClass.getMethod("instance");
            fluidRenderMap = instanceMethod.invoke(null);

            // Get the registerFluid method
            registerFluidMethod = fluidRenderMapClass.getMethod("registerFluid", Fluid.class, int.class);

            // Register all fluids immediately
            registerAllFluids();

            System.out.println("[NTM] Successfully initialized AE2 RV2 fluid color handler");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 RV2 fluid color handler: " + e.getMessage());
        }
    }

    /**
     * Register all fluids with AE2 RV2.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerAllFluids() {
        if (fluidRenderMap == null || registerFluidMethod == null) return;

        try {
            int count = 0;

            // Register all fluids
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    int color = ((ColoredForgeFluid) fluid).getColorARGB();
                    registerFluidMethod.invoke(fluidRenderMap, fluid, color);
                    count++;
                }
            }

            System.out.println("[NTM] Registered " + count + " fluids with AE2 RV2 FluidRenderMap");
        } catch (Exception e) {
            System.err.println("[NTM] Error registering fluids with AE2 RV2: " + e.getMessage());
        }
    }

    /**
     * Handle client tick events to periodically update fluid colors.
     */
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (!isRV2) return;
        if (event.phase != TickEvent.Phase.END) return;

        // Update every 5 seconds to ensure colors are applied
        if (Minecraft.getMinecraft().theWorld != null &&
            Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 100 == 0) {
            registerAllFluids();
        }
    }
}
