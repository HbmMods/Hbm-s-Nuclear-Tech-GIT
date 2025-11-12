package api.ntm1of90.compat.fluid.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import com.hbm.main.MainRegistry;

import api.hbm.fluidmk2.IFluidUserMK2;
import api.ntm1of90.compat.fluid.adapter.AutoForgeFluidAdapter;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A registry for managing fluid adapters for tile entities.
 * This allows for Forge fluid compatibility without replacing tile entities.
 */
public class ForgeFluidAdapterRegistry {

    // Use a WeakHashMap to avoid memory leaks when tile entities are unloaded
    private static final Map<TileEntity, IFluidHandler> adapterMap = new WeakHashMap<>();

    // Cache of class compatibility to avoid repeated checks
    private static final Map<Class<?>, Boolean> compatibilityCache = new HashMap<>();

    private static boolean initialized = false;

    /**
     * Initialize the registry
     */
    public static void initialize() {
        if (initialized) {
            return;
        }

        initialized = true;
        MainRegistry.logger.info("ForgeFluidAdapterRegistry initialized");
    }

    /**
     * Get a fluid handler for a tile entity
     *
     * @param tileEntity The tile entity to get a fluid handler for
     * @return The fluid handler, or null if the tile entity is not a fluid handler
     */
    public static IFluidHandler getFluidHandler(TileEntity tileEntity) {
        if (tileEntity == null) {
            return null;
        }

        // If the tile entity already implements IFluidHandler, return it
        if (tileEntity instanceof IFluidHandler) {
            return (IFluidHandler) tileEntity;
        }

        // Check if we already have an adapter for this tile entity
        if (adapterMap.containsKey(tileEntity)) {
            return adapterMap.get(tileEntity);
        }

        // Check if the tile entity is in the blacklist
        if (isBlacklistedTileEntity(tileEntity)) {
            return null;
        }

        // If the tile entity implements IFluidUserMK2, create an adapter
        if (tileEntity instanceof IFluidUserMK2) {
            IFluidUserMK2 fluidUser = (IFluidUserMK2) tileEntity;

            // Check if the tile entity has any fluid tanks
            com.hbm.inventory.fluid.tank.FluidTank[] tanks = fluidUser.getAllTanks();
            if (tanks == null || tanks.length == 0) {
                // No tanks, so no need for fluid compatibility
                return null;
            }

            // Create an adapter and register it
            IFluidHandler adapter = new AutoForgeFluidAdapter(fluidUser, tileEntity);
            adapterMap.put(tileEntity, adapter);
            return adapter;
        }

        // If the tile entity is a dummy, get the target tile entity
        if (tileEntity instanceof com.hbm.tileentity.machine.TileEntityDummy) {
            com.hbm.tileentity.machine.TileEntityDummy dummy = (com.hbm.tileentity.machine.TileEntityDummy) tileEntity;
            TileEntity target = tileEntity.getWorldObj().getTileEntity(dummy.targetX, dummy.targetY, dummy.targetZ);
            return getFluidHandler(target);
        }

        return null;
    }

    /**
     * Register a fluid handler for a tile entity
     *
     * @param tileEntity The tile entity to register a fluid handler for
     * @param fluidHandler The fluid handler to register
     */
    public static void registerFluidHandler(TileEntity tileEntity, IFluidHandler fluidHandler) {
        if (tileEntity != null && fluidHandler != null) {
            adapterMap.put(tileEntity, fluidHandler);
        }
    }

    /**
     * Unregister a fluid handler for a tile entity
     *
     * @param tileEntity The tile entity to unregister a fluid handler for
     */
    public static void unregisterFluidHandler(TileEntity tileEntity) {
        if (tileEntity != null) {
            adapterMap.remove(tileEntity);
        }
    }

    /**
     * Check if a tile entity is blacklisted from fluid compatibility
     *
     * @param tileEntity The tile entity to check
     * @return True if the tile entity is blacklisted, false otherwise
     */
    private static boolean isBlacklistedTileEntity(TileEntity tileEntity) {
        if (tileEntity == null) {
            return true;
        }

        // Check the cache first
        Class<?> tileClass = tileEntity.getClass();
        if (compatibilityCache.containsKey(tileClass)) {
            return compatibilityCache.get(tileClass);
        }

        // Get the class name
        String className = tileClass.getName();

        // Check against known problematic classes
        boolean isBlacklisted = className.contains("TileEntityCustomMachine") ||
            className.contains("TileEntityMachineBase") ||
            className.contains("TileEntityMachineAssembler") ||
            className.contains("TileEntityMachineChemplant") ||
            className.contains("TileEntityMachineFluidTank") ||
            className.contains("TileEntityMachineTurbine") ||
            className.contains("TileEntityMachineReactorLarge") ||
            className.contains("TileEntityMachineReactorSmall") ||
            className.contains("TileEntityMachineMiningDrill") ||
            className.contains("TileEntityMachineMiningLaser") ||
            className.contains("TileEntityMachinePress") ||
            className.contains("TileEntityMachineCrystallizer") ||
            className.contains("TileEntityMachineBoiler") ||
            className.contains("TileEntityMachineElectricFurnace") ||
            className.contains("TileEntityMachineGenerator") ||
            className.contains("TileEntityMachineDiesel") ||
            className.contains("TileEntityMachineCombustionEngine") ||
            className.contains("TileEntityMachineOilWell") ||
            className.contains("TileEntityMachineRefinery") ||
            className.contains("TileEntityMachinePumpjack") ||
            className.contains("TileEntityMachineGasFlare") ||
            className.contains("TileEntityMachineCoal") ||
            className.contains("TileEntityMachineRTG") ||
            className.contains("TileEntityMachineBattery") ||
            className.contains("TileEntityMachineTransformer") ||
            className.contains("TileEntityMachineCapacitor") ||
            className.contains("TileEntityMachineEMP") ||
            className.contains("TileEntityMachineRadar") ||
            className.contains("TileEntityMachineRadio") ||
            className.contains("TileEntityMachineRadGen") ||
            className.contains("TileEntityMachineUF6Tank") ||
            className.contains("TileEntityMachinePuF6Tank") ||
            className.contains("TileEntityMachineIGenerator") ||
            className.contains("TileEntityMachineCyclotron") ||
            className.contains("TileEntityMachineOilDerrick") ||
            className.contains("TileEntityMachineGasCent") ||
            className.contains("TileEntityMachineConveyorPress") ||
            className.contains("TileEntityMachineConveyor") ||
            className.contains("TileEntityMachineEPress") ||
            className.contains("TileEntityMachineExcavator") ||
            className.contains("TileEntityMachineMixer") ||
            className.contains("TileEntityMachineFluidTank") ||
            className.contains("TileEntityDummy") ||
            className.contains("TileEntityProxyBase") ||
            className.contains("TileEntityProxyInventory") ||
            className.contains("TileEntityProxyCombo") ||
            className.contains("TileEntityCable") ||
            className.contains("TileEntityConnector") ||
            className.contains("TileEntityPipe") ||
            className.contains("TileEntityPipeBaseNT") ||
            className.contains("TileEntityFluidDuct");

        // Cache the result
        compatibilityCache.put(tileClass, isBlacklisted);

        return isBlacklisted;
    }

    /**
     * Clear the adapter map
     * This should be called when a world is unloaded
     */
    public static void clearAdapters() {
        adapterMap.clear();
    }
}
