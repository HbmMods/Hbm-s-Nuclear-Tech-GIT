package api.ntm1of90.compat.ae2;

import api.ntm1of90.compat.fluid.FluidDisplayItem;
import api.ntm1of90.compat.fluid.render.ColoredForgeFluid;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Test class for AE2 compatibility.
 * This class logs information about AE2's fluid rendering system.
 */
@SideOnly(Side.CLIENT)
public class AE2CompatTest {

    private static boolean initialized = false;

    /**
     * Initialize the AE2 compatibility test.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        if (initialized) return;

        // Only initialize if AE2 is loaded
        if (!Loader.isModLoaded("appliedenergistics2")) {
            System.out.println("[NTM] Applied Energistics 2 not detected, skipping AE2 compatibility test");
            return;
        }

        try {
            // Register for events
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new AE2CompatTest());
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new AE2CompatTest());

            // Run the test
            testAE2Integration();

            System.out.println("[NTM] AE2 compatibility test initialized");
        } catch (Exception e) {
            System.err.println("[NTM] Error initializing AE2 compatibility test: " + e.getMessage());
        }

        initialized = true;
    }

    /**
     * Test AE2 integration.
     * This method logs information about AE2's fluid rendering system.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void testAE2Integration() {
        try {
            // Test FluidRenderMap
            testFluidRenderMap();

            // Test FluidCellInventory
            testFluidCellInventory();

            // Test fluid items
            testFluidItems();

            System.out.println("[NTM] AE2 compatibility test completed");
        } catch (Exception e) {
            System.err.println("[NTM] Error testing AE2 integration: " + e.getMessage());
        }
    }

    /**
     * Test AE2's FluidRenderMap.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void testFluidRenderMap() {
        try {
            // Get the FluidRenderMap class
            Class<?> fluidRenderMapClass = Class.forName("appeng.client.texture.FluidRenderMap");

            // Get the instance
            Method instanceMethod = fluidRenderMapClass.getMethod("instance");
            Object fluidRenderMap = instanceMethod.invoke(null);

            // Get the colors field
            Field colorsField = fluidRenderMapClass.getDeclaredField("colors");
            colorsField.setAccessible(true);

            // Get the colors map
            @SuppressWarnings("unchecked")
            Map<Fluid, Integer> colors = (Map<Fluid, Integer>) colorsField.get(fluidRenderMap);

            // Log information about the colors map
            System.out.println("[NTM] AE2 FluidRenderMap colors map size: " + colors.size());

            // Check if our fluids are in the map
            int count = 0;
            for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                if (fluid instanceof ColoredForgeFluid) {
                    if (colors.containsKey(fluid)) {
                        count++;
                    }
                }
            }

            System.out.println("[NTM] AE2 FluidRenderMap contains " + count + " of our fluids");
        } catch (Exception e) {
            System.err.println("[NTM] Error testing AE2 FluidRenderMap: " + e.getMessage());
        }
    }

    /**
     * Test AE2's FluidCellInventory.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void testFluidCellInventory() {
        try {
            // Get the FluidCellInventory class
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
                    // Log information about the fluid colors map
                    System.out.println("[NTM] AE2 FluidCellInventory fluid colors map size: " + fluidColorsMap.size());

                    // Check if our fluids are in the map
                    int count = 0;
                    for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                        if (fluid instanceof ColoredForgeFluid) {
                            if (fluidColorsMap.containsKey(fluid)) {
                                count++;
                            }
                        }
                    }

                    System.out.println("[NTM] AE2 FluidCellInventory contains " + count + " of our fluids");
                }
            }
        } catch (Exception e) {
            System.err.println("[NTM] Error testing AE2 FluidCellInventory: " + e.getMessage());
        }
    }

    /**
     * Test fluid items.
     */
    @Optional.Method(modid = "appliedenergistics2")
    private static void testFluidItems() {
        try {
            // Get the FluidCellInventory class
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
                    // Log information about the fluid items map
                    System.out.println("[NTM] AE2 FluidCellInventory fluid items map size: " + fluidItemsMap.size());

                    // Check if our fluids are in the map
                    int count = 0;
                    for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
                        if (fluid instanceof ColoredForgeFluid) {
                            if (fluidItemsMap.containsKey(fluid)) {
                                ItemStack itemStack = fluidItemsMap.get(fluid);
                                System.out.println("[NTM] AE2 FluidCellInventory contains item for fluid: " + fluid.getName() +
                                    " (item: " + itemStack.getItem().getUnlocalizedName() + ", meta: " + itemStack.getItemDamage() + ")");
                                count++;
                            }
                        }
                    }

                    System.out.println("[NTM] AE2 FluidCellInventory contains " + count + " of our fluid items");
                }
            }
        } catch (Exception e) {
            System.err.println("[NTM] Error testing fluid items: " + e.getMessage());
        }
    }

    /**
     * Handle GUI open events to log information about AE2 GUIs.
     */
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (!Loader.isModLoaded("appliedenergistics2")) return;

        GuiScreen gui = event.gui;
        if (gui == null) return;

        // Check if this is an AE2 GUI
        if (gui.getClass().getName().startsWith("appeng.client.gui")) {
            System.out.println("[NTM] AE2 GUI opened: " + gui.getClass().getName());
        }
    }

    /**
     * Handle client tick events to periodically run the test.
     */
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        // Run the test every 30 seconds
        if (Minecraft.getMinecraft().theWorld != null &&
            Minecraft.getMinecraft().theWorld.getTotalWorldTime() % 600 == 0) {
            testAE2Integration();
        }
    }
}
