package api.ntm1of90.compat.fluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;

import api.ntm1of90.compat.fluid.registry.FluidMappingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * A utility class for registering NTM fluid compatibility with other mods.
 * This class provides methods for registering fluid items and renderers.
 */
public class NTMFluidCompat {
    
    /**
     * Initialize fluid compatibility
     * This should be called during mod initialization
     */
    public static void initialize() {
        // Register the fluid compat item
        registerFluidCompatItem();
        
        // Register fluid items for all NTM fluids
        registerFluidItems();
        
        System.out.println("[NTM] Fluid compatibility initialized");
    }
    
    /**
     * Register the fluid compat item
     */
    private static void registerFluidCompatItem() {
        // Register the fluid compat item
        Item fluidCompatItem = NTMFluidCompatItem.getInstance();
        GameRegistry.registerItem(fluidCompatItem, "ntm_fluid_compat");
        ModItems.hbm_fluid_compat = fluidCompatItem;
        
        System.out.println("[NTM] Registered fluid compat item");
    }
    
    /**
     * Register fluid items for all NTM fluids
     */
    private static void registerFluidItems() {
        int count = 0;
        
        // Register fluid items for all NTM fluids
        for (FluidType ntmFluid : Fluids.getAll()) {
            if (ntmFluid == Fluids.NONE) continue;
            
            // Get the Forge fluid for this NTM fluid
            Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(ntmFluid);
            if (forgeFluid == null) continue;
            
            // Create an item for this fluid
            ItemStack fluidItem = NTMFluidCompatItem.getItemStackForFluid(forgeFluid);
            if (fluidItem == null) continue;
            
            // Register the fluid item with the fluid registry
            FluidContainerRegistry.registerFluidContainer(
                new FluidStack(forgeFluid, FluidContainerRegistry.BUCKET_VOLUME),
                fluidItem
            );
            
            count++;
        }
        
        System.out.println("[NTM] Registered " + count + " fluid items");
    }
    
    /**
     * Get an ItemStack that represents a fluid
     * @param fluid The fluid to represent
     * @return An ItemStack that represents the fluid
     */
    public static ItemStack getFluidItem(Fluid fluid) {
        return NTMFluidCompatItem.getItemStackForFluid(fluid);
    }
    
    /**
     * Get an ItemStack that represents a fluid
     * @param fluidName The name of the fluid to represent
     * @return An ItemStack that represents the fluid
     */
    public static ItemStack getFluidItem(String fluidName) {
        Fluid fluid = FluidRegistry.getFluid(fluidName);
        return getFluidItem(fluid);
    }
    
    /**
     * Get an ItemStack that represents an NTM fluid
     * @param ntmFluid The NTM fluid to represent
     * @return An ItemStack that represents the fluid
     */
    public static ItemStack getFluidItem(FluidType ntmFluid) {
        if (ntmFluid == null || ntmFluid == Fluids.NONE) return null;
        
        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(ntmFluid);
        return getFluidItem(forgeFluid);
    }
}
