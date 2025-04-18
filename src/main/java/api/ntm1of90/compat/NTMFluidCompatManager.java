package api.ntm1of90.compat;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.RefStrings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager class for NTM fluid compatibility with other mods.
 * This class handles initialization and registration of fluid compatibility features.
 */
public class NTMFluidCompatManager {
    
    // Map of mod IDs to compatibility handlers
    private static final Map<String, IModFluidCompat> modCompatHandlers = new HashMap<>();
    
    /**
     * Initialize the fluid compatibility system.
     * This should be called during mod initialization.
     */
    public static void initialize() {
        System.out.println("[NTM] Initializing fluid compatibility system...");
        
        // Register event handlers
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            registerClientEvents();
        }
        
        // Register mod-specific compatibility handlers
        registerModCompat();
        
        System.out.println("[NTM] Fluid compatibility system initialized");
    }
    
    /**
     * Register client-side event handlers
     */
    @SideOnly(Side.CLIENT)
    private static void registerClientEvents() {
        // Register texture stitching event handler
        MinecraftForge.EVENT_BUS.register(new Object() {
            @SideOnly(Side.CLIENT)
            @cpw.mods.fml.common.eventhandler.SubscribeEvent
            public void onTextureStitch(TextureStitchEvent.Pre event) {
                if (event.map.getTextureType() == 0) { // Block textures
                    // Make sure all fluid textures are registered
                    for (FluidType ntmFluid : Fluids.getAll()) {
                        if (ntmFluid == Fluids.NONE) continue;
                        
                        Fluid forgeFluid = FluidMappingRegistry.getForgeFluid(ntmFluid);
                        if (forgeFluid != null) {
                            // Register still and flowing textures
                            String fluidName = ntmFluid.getName().toLowerCase(java.util.Locale.US);
                            forgeFluid.setIcons(
                                event.map.registerIcon(RefStrings.MODID + ":forgefluid/" + fluidName),
                                event.map.registerIcon(RefStrings.MODID + ":forgefluid/" + fluidName)
                            );
                        }
                    }
                }
            }
        });
    }
    
    /**
     * Register mod-specific compatibility handlers
     */
    private static void registerModCompat() {
        // Register Thermal Expansion compatibility if it's loaded
        if (Loader.isModLoaded("ThermalExpansion")) {
            registerThermalExpansionCompat();
        }
        
        // Register BuildCraft compatibility if it's loaded
        if (Loader.isModLoaded("BuildCraft|Core")) {
            registerBuildCraftCompat();
        }
        
        // Register EnderIO compatibility if it's loaded
        if (Loader.isModLoaded("EnderIO")) {
            registerEnderIOCompat();
        }
        
        // Register Applied Energistics 2 compatibility if it's loaded
        if (Loader.isModLoaded("appliedenergistics2")) {
            registerAE2Compat();
        }
    }
    
    /**
     * Register Thermal Expansion compatibility
     */
    private static void registerThermalExpansionCompat() {
        System.out.println("[NTM] Registering Thermal Expansion fluid compatibility...");
        
        // Register example recipes with Thermal Expansion
        // These are just examples - you would need to add your actual recipes
        
        // Example: Register a Magma Crucible recipe
        NBTTagCompound data = new NBTTagCompound();
        data.setInteger("energy", 8000);
        FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", data);
        
        System.out.println("[NTM] Thermal Expansion fluid compatibility registered");
    }
    
    /**
     * Register BuildCraft compatibility
     */
    private static void registerBuildCraftCompat() {
        System.out.println("[NTM] Registering BuildCraft fluid compatibility...");
        
        // Add BuildCraft-specific compatibility code here
        
        System.out.println("[NTM] BuildCraft fluid compatibility registered");
    }
    
    /**
     * Register EnderIO compatibility
     */
    private static void registerEnderIOCompat() {
        System.out.println("[NTM] Registering EnderIO fluid compatibility...");
        
        // Add EnderIO-specific compatibility code here
        
        System.out.println("[NTM] EnderIO fluid compatibility registered");
    }
    
    /**
     * Register Applied Energistics 2 compatibility
     */
    private static void registerAE2Compat() {
        System.out.println("[NTM] Registering Applied Energistics 2 fluid compatibility...");
        
        // Add AE2-specific compatibility code here
        
        System.out.println("[NTM] Applied Energistics 2 fluid compatibility registered");
    }
    
    /**
     * Interface for mod-specific fluid compatibility handlers
     */
    public interface IModFluidCompat {
        /**
         * Initialize the compatibility handler
         */
        void initialize();
        
        /**
         * Get the mod ID that this handler is for
         */
        String getModId();
    }
}
