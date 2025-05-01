package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import api.ntm1of90.compat.fluid.FluidDisplayItem;
import api.ntm1of90.compat.fluid.render.NTMFluidColorApplier;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple compatibility class for Applied Energistics 2.
 * This class handles fluid rendering in AE2's ME system.
 */
@SideOnly(Side.CLIENT)
public class AE2FluidCompat {

    private static boolean initialized = false;
    private static Object fluidRenderMap = null;
    private static Method registerFluidMethod = null;

    /**
     * Initialize the AE2 compatibility.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 compatibility");
            return;
        }

        try {
            // Register for tick events to periodically update fluid colors
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new AE2FluidCompat());

            // Initialize AE2 integration
            initializeAE2();

            System.out.println("[NTM] AE2 compatibility initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 compatibility: " + e.getMessage());
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
            // Get the FluidRenderMap class
            Class<?> fluidRenderMapClass = Class.forName("appeng.client.texture.FluidRenderMap");

            // Get the instance method
            Method instanceMethod = fluidRenderMapClass.getMethod("instance");
            fluidRenderMap = instanceMethod.invoke(null);

            // Get the registerFluid method
            registerFluidMethod = fluidRenderMapClass.getMethod("registerFluid", Fluid.class, int.class);

            // Register all fluids immediately
            registerAllFluids();

            // Try to patch the fluid cell inventory
            patchFluidCellInventory();

            // Register fluid items with AE2
            registerFluidItems();

            // Initialize the test class
            AE2CompatTest.initialize();

            System.out.println("[NTM] Successfully initialized AE2 integration");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 integration: " + e.getMessage());
        }
    }

    /**
     * Register all fluids with AE2.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerAllFluids() {
        if (fluidRenderMap == null || registerFluidMethod == null) return;

        try {
            int count = 0;

            // Register all fluids
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    int colorWithAlpha = ((ColoredForgeFluid) fluid).getColorARGB();
                    registerFluidMethod.invoke(fluidRenderMap, fluid, colorWithAlpha);
                    count++;
                }
            }

            System.out.println("[NTM] Registered " + count + " fluids with AE2");
        } catch (Exception e) {
            System.err.println("[NTM] Error registering fluids with AE2: " + e.getMessage());
        }
    }

    /**
     * Patch AE2's fluid cell inventory to handle our fluids.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void patchFluidCellInventory() {
        try {
            // Try to patch AE2's FluidCellInventory class
            Class<?> fluidCellInventoryClass = Class.forName("appeng.me.storage.FluidCellInventory");

            // Try to find the fluid colors field
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

                if (fluidColorsMap != null) {
                    // Add our colors to the map
                    for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                        if (fluid instanceof ColoredForgeFluid) {
                            int colorWithAlpha = ((ColoredForgeFluid) fluid).getColorARGB();
                            fluidColorsMap.put(fluid, colorWithAlpha);
                        }
                    }

                    System.out.println("[NTM] Successfully patched AE2's fluid cell inventory");
                }
            }
        } catch (Exception e) {
            System.err.println("[NTM] Error patching AE2's fluid cell inventory: " + e.getMessage());
        }
    }

    /**
     * Register fluid items with AE2.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void registerFluidItems() {
        try {
            // Try to patch AE2's FluidCellInventory class
            Class<?> fluidCellInventoryClass = Class.forName("appeng.me.storage.FluidCellInventory");

            // Try to find the fluid items field
            Field fluidItemsField = null;
            for (Field field : fluidCellInventoryClass.getDeclaredFields()) {
                if (field.getType() == Map.class) {
                    field.setAccessible(true);
                    Object fieldValue = field.get(null);
                    if (fieldValue instanceof Map) {
                        // Check if this is the fluid items map by looking at the first entry
                        @SuppressWarnings("unchecked")
                        Map<Object, Object> map = (Map<Object, Object>) fieldValue;
                        if (!map.isEmpty()) {
                            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                                if (entry.getKey() instanceof Fluid && entry.getValue() instanceof ItemStack) {
                                    fluidItemsField = field;
                                    break;
                                }
                            }
                        }
                    }

                    if (fluidItemsField != null) {
                        break;
                    }
                }
            }

            if (fluidItemsField != null) {
                // Get the fluid items map
                @SuppressWarnings("unchecked")
                Map<Fluid, ItemStack> fluidItemsMap = (Map<Fluid, ItemStack>) fluidItemsField.get(null);

                if (fluidItemsMap != null) {
                    // Register items for all fluids
                    int count = 0;
                    for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                        if (fluid instanceof ColoredForgeFluid) {
                            ItemStack itemStack = FluidDisplayItem.getItemStackForFluid(fluid);
                            if (itemStack != null) {
                                fluidItemsMap.put(fluid, itemStack);
                                System.out.println("[NTM] Registered item for fluid in AE2: " + fluid.getName());
                                count++;
                            }
                        }
                    }

                    System.out.println("[NTM] Registered " + count + " fluid items with AE2");
                }
            } else {
                System.err.println("[NTM] Could not find fluid items field in FluidCellInventory");
            }
        } catch (Exception e) {
            System.err.println("[NTM] Error registering fluid items with AE2: " + e.getMessage());
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
            registerAllFluids();
            patchFluidCellInventory();
            registerFluidItems();
        }
    }
}
