package api.ntm1of90.compat;

import java.util.HashMap;
import java.util.Map;

import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;

import api.hbm.fluidmk2.IFluidUserMK2;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * A manager class that automatically registers all IFluidUserMK2 implementations as IFluidHandler.
 * This class is initialized during the mod's initialization phase.
 */
public class ForgeFluidCompatManager {

    // Default flow rate for Forge fluid transfers (in Forge fluid units per operation)
    // This controls how much fluid can be transferred in a single operation
    private static int defaultForgeFlowRate = 1000000; // 1,000,000 mB per operation by default (much higher since we're using 1:1 conversion)

    /**
     * Set the default flow rate for Forge fluid transfers
     *
     * @param flowRate The flow rate in Forge fluid units per operation
     */
    public static void setDefaultForgeFlowRate(int flowRate) {
        if (flowRate > 0) {
            defaultForgeFlowRate = flowRate;
            MainRegistry.logger.info("Set default Forge fluid flow rate to " + flowRate + " mB per operation");
        }
    }

    /**
     * Get the default flow rate for Forge fluid transfers
     *
     * @return The flow rate in Forge fluid units per operation
     */
    public static int getDefaultForgeFlowRate() {
        return defaultForgeFlowRate;
    }

    private static boolean initialized = false;
    private static Map<Class<?>, Boolean> fluidHandlerCache = new HashMap<>();

    /**
     * Initialize the manager
     */
    public static void initialize() {
        if (initialized) {
            return;
        }

        // Initialize the fluid mapping registry
        FluidMappingRegistry.initialize();

        // Register the event handler
        MinecraftForge.EVENT_BUS.register(new ForgeFluidCompatManager());

        // Register the TileEntityFluidProxy class
        cpw.mods.fml.common.registry.GameRegistry.registerTileEntity(TileEntityFluidProxy.class, "ntm1of90:fluid_proxy");

        // We can't use ASM transformers after the game has started,
        // so we'll use a different approach to make HBM fluid tile entities compatible with Forge's fluid system.
        // Instead of transforming classes, we'll use the proxy approach in the onBlockPlace event handler.
        MainRegistry.logger.info("Using proxy approach for Forge fluid compatibility");

        initialized = true;

        MainRegistry.logger.info("ForgeFluidCompatManager initialized");
    }

    /**
     * Check if a class implements IFluidHandler
     *
     * @param clazz The class to check
     * @return True if the class implements IFluidHandler, false otherwise
     */
    public static boolean implementsIFluidHandler(Class<?> clazz) {
        if (fluidHandlerCache.containsKey(clazz)) {
            return fluidHandlerCache.get(clazz);
        }

        boolean result = IFluidHandler.class.isAssignableFrom(clazz);
        fluidHandlerCache.put(clazz, result);
        return result;
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

        // Check if the tile entity is in the blacklist
        if (isBlacklistedTileEntity(tileEntity)) {
            return null;
        }

        // If the tile entity implements IFluidUserMK2, check if it actually has fluid tanks
        if (tileEntity instanceof IFluidUserMK2) {
            IFluidUserMK2 fluidUser = (IFluidUserMK2) tileEntity;

            // Check if the tile entity has any fluid tanks
            com.hbm.inventory.fluid.tank.FluidTank[] tanks = fluidUser.getAllTanks();
            if (tanks == null || tanks.length == 0) {
                // No tanks, so no need for fluid compatibility
                return null;
            }

            return new AutoForgeFluidAdapter(fluidUser, tileEntity);
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
     * Event handler for block placement
     *
     * @param event The event
     */
    @SubscribeEvent
    public void onWorldLoad(net.minecraftforge.event.world.WorldEvent.Load event) {
        // Register a tick handler to process existing tile entities
        if (!event.world.isRemote) {
            MainRegistry.logger.info("Registering tick handler for Forge fluid compatibility");
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new WorldTickHandler(event.world));
        }
    }

    /**
     * Tick handler for processing existing tile entities
     */
    public static class WorldTickHandler {
        private final net.minecraft.world.World world;
        private boolean processed = false;

        public WorldTickHandler(net.minecraft.world.World world) {
            this.world = world;
        }

        @cpw.mods.fml.common.eventhandler.SubscribeEvent
        public void onWorldTick(cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent event) {
            if (event.world != world || processed || event.phase != cpw.mods.fml.common.gameevent.TickEvent.Phase.END) {
                return;
            }

            // Process existing tile entities
            processExistingTileEntities();

            // Unregister the tick handler
            cpw.mods.fml.common.FMLCommonHandler.instance().bus().unregister(this);
        }

        private void processExistingTileEntities() {
            MainRegistry.logger.info("Processing existing tile entities for Forge fluid compatibility");

            // Create a copy of the loaded tile entities list to avoid ConcurrentModificationException
            java.util.List<TileEntity> tileEntitiesCopy = new java.util.ArrayList<TileEntity>(world.loadedTileEntityList);
            int count = 0;

            // Process each tile entity
            for (TileEntity tileEntity : tileEntitiesCopy) {
                // Skip null tile entities or those that have been removed
                if (tileEntity == null || tileEntity.isInvalid()) {
                    continue;
                }

                // Check if the tile entity implements IFluidUserMK2 but not IFluidHandler
                if (tileEntity instanceof IFluidUserMK2 && !(tileEntity instanceof IFluidHandler)) {
                    try {
                        // Create a proxy tile entity that implements IFluidHandler
                        TileEntity proxy = createProxyTileEntity(tileEntity);
                        if (proxy != null) {
                            // Replace the tile entity with the proxy
                            world.setTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, proxy);
                            count++;
                        }
                    } catch (Exception e) {
                        // Log the error and continue with the next tile entity
                        MainRegistry.logger.error("Error processing tile entity at (" + tileEntity.xCoord + ", " + tileEntity.yCoord + ", " + tileEntity.zCoord + "): " + e.getMessage());
                    }
                }
            }

            MainRegistry.logger.info("Processed " + count + " existing tile entities for Forge fluid compatibility");
            processed = true;
        }
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        // Get the tile entity directly - no need to check hasTileEntity
        TileEntity tileEntity = event.world.getTileEntity(event.x, event.y, event.z);
        if (tileEntity == null) {
            return;
        }

        // Check if the tile entity implements IFluidUserMK2 but not IFluidHandler
        if (tileEntity instanceof IFluidUserMK2 && !(tileEntity instanceof IFluidHandler)) {
            // Create a proxy tile entity that implements IFluidHandler
            TileEntity proxy = createProxyTileEntity(tileEntity);
            if (proxy != null) {
                // Replace the tile entity with the proxy
                event.world.setTileEntity(event.x, event.y, event.z, proxy);
                MainRegistry.logger.info("Replaced " + tileEntity.getClass().getSimpleName() + " with a Forge-compatible proxy");
            }
        }
    }

    /**
     * Create a proxy tile entity that implements IFluidHandler
     *
     * @param tileEntity The tile entity to create a proxy for
     * @return The proxy tile entity, or null if the tile entity is not a fluid user
     */
    private static TileEntity createProxyTileEntity(TileEntity tileEntity) {
        if (!(tileEntity instanceof IFluidUserMK2)) {
            return null;
        }

        // Check if the tile entity is in the blacklist
        if (isBlacklistedTileEntity(tileEntity)) {
            return null;
        }

        // Check if the tile entity actually has fluid tanks
        IFluidUserMK2 fluidUser = (IFluidUserMK2) tileEntity;
        com.hbm.inventory.fluid.tank.FluidTank[] tanks = fluidUser.getAllTanks();
        if (tanks == null || tanks.length == 0) {
            // No tanks, so no need for fluid compatibility
            return null;
        }

        // Create a proxy tile entity
        return new TileEntityFluidProxy(fluidUser, tileEntity);
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

        // Get the class name
        String className = tileEntity.getClass().getName();

        // Check against known problematic classes
        if (className.contains("TileEntityCustomMachine") ||
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
            className.contains("TileEntityFluidDuct")) {
            return true;
        }

        return false;
    }

    /**
     * A proxy tile entity that implements IFluidHandler
     */
    public static class TileEntityFluidProxy extends TileEntity implements IFluidHandler {

        private IFluidUserMK2 fluidUser;
        private TileEntity originalTileEntity;
        private AutoForgeFluidAdapter adapter;

        // Store the original tile entity class name and coordinates for reconstruction
        private String originalClassName;
        private int originalX;
        private int originalY;
        private int originalZ;

        /**
         * Create a new proxy tile entity
         *
         * @param fluidUser The fluid user to proxy
         * @param originalTileEntity The original tile entity
         */
        public TileEntityFluidProxy(IFluidUserMK2 fluidUser, TileEntity originalTileEntity) {
            this.fluidUser = fluidUser;
            this.originalTileEntity = originalTileEntity;
            this.adapter = new AutoForgeFluidAdapter(fluidUser, this);

            // Copy the tile entity's data using reflection
            try {
                // Get the fields
                java.lang.reflect.Field xCoordField = TileEntity.class.getDeclaredField("xCoord");
                java.lang.reflect.Field yCoordField = TileEntity.class.getDeclaredField("yCoord");
                java.lang.reflect.Field zCoordField = TileEntity.class.getDeclaredField("zCoord");
                java.lang.reflect.Field worldObjField = TileEntity.class.getDeclaredField("worldObj");
                java.lang.reflect.Field blockMetadataField = TileEntity.class.getDeclaredField("blockMetadata");
                java.lang.reflect.Field blockTypeField = TileEntity.class.getDeclaredField("blockType");

                // Make the fields accessible
                xCoordField.setAccessible(true);
                yCoordField.setAccessible(true);
                zCoordField.setAccessible(true);
                worldObjField.setAccessible(true);
                blockMetadataField.setAccessible(true);
                blockTypeField.setAccessible(true);

                // Set the values
                xCoordField.set(this, originalTileEntity.xCoord);
                yCoordField.set(this, originalTileEntity.yCoord);
                zCoordField.set(this, originalTileEntity.zCoord);
                worldObjField.set(this, originalTileEntity.getWorldObj());
                blockMetadataField.set(this, originalTileEntity.getBlockMetadata());
                blockTypeField.set(this, originalTileEntity.getBlockType());
            } catch (Exception e) {
                MainRegistry.logger.error("Failed to copy tile entity data: " + e.getMessage());
            }

            // Store the original tile entity class name and coordinates
            this.originalClassName = originalTileEntity.getClass().getName();
            this.originalX = originalTileEntity.xCoord;
            this.originalY = originalTileEntity.yCoord;
            this.originalZ = originalTileEntity.zCoord;
        }

        /**
         * Default constructor for NBT deserialization
         */
        public TileEntityFluidProxy() {
            this.fluidUser = null;
            this.originalTileEntity = null;
            this.adapter = null;
        }

        @Override
        public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
            if (adapter != null) {
                return adapter.fill(from, resource, doFill);
            }
            return 0;
        }

        @Override
        public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
            if (adapter != null) {
                return adapter.drain(from, resource, doDrain);
            }
            return null;
        }

        @Override
        public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
            if (adapter != null) {
                return adapter.drain(from, maxDrain, doDrain);
            }
            return null;
        }

        @Override
        public boolean canFill(ForgeDirection from, Fluid fluid) {
            if (adapter != null) {
                return adapter.canFill(from, fluid);
            }
            return false;
        }

        @Override
        public boolean canDrain(ForgeDirection from, Fluid fluid) {
            if (adapter != null) {
                return adapter.canDrain(from, fluid);
            }
            return false;
        }

        @Override
        public FluidTankInfo[] getTankInfo(ForgeDirection from) {
            if (adapter != null) {
                return adapter.getTankInfo(from);
            }
            return new FluidTankInfo[0];
        }

        /**
         * Get the original tile entity
         *
         * @return The original tile entity
         */
        public TileEntity getOriginalTileEntity() {
            return originalTileEntity;
        }

        @Override
        public void updateEntity() {
            // Forward the update to the original tile entity
            if (originalTileEntity != null) {
                originalTileEntity.updateEntity();

                // Recreate the adapter if it's null
                if (adapter == null && fluidUser != null) {
                    adapter = new AutoForgeFluidAdapter(fluidUser, this);
                }
            } else if (worldObj != null && !worldObj.isRemote) {
                // Try to recreate the original tile entity if it's null
                tryRecreateOriginalTileEntity();
            }
        }

        /**
         * Try to recreate the original tile entity from the stored class name and coordinates
         */
        private void tryRecreateOriginalTileEntity() {
            if (originalClassName == null || worldObj == null) {
                return;
            }

            try {
                // Get the original tile entity class
                Class<?> originalClass = Class.forName(originalClassName);

                // Create a new instance of the original tile entity
                TileEntity newTileEntity = (TileEntity) originalClass.newInstance();

                // We can't directly set protected fields, so we need to use reflection
                try {
                    // Set the coordinates
                    java.lang.reflect.Field xCoordField = TileEntity.class.getDeclaredField("xCoord");
                    java.lang.reflect.Field yCoordField = TileEntity.class.getDeclaredField("yCoord");
                    java.lang.reflect.Field zCoordField = TileEntity.class.getDeclaredField("zCoord");
                    java.lang.reflect.Field worldObjField = TileEntity.class.getDeclaredField("worldObj");
                    java.lang.reflect.Field blockMetadataField = TileEntity.class.getDeclaredField("blockMetadata");
                    java.lang.reflect.Field blockTypeField = TileEntity.class.getDeclaredField("blockType");

                    // Make the fields accessible
                    xCoordField.setAccessible(true);
                    yCoordField.setAccessible(true);
                    zCoordField.setAccessible(true);
                    worldObjField.setAccessible(true);
                    blockMetadataField.setAccessible(true);
                    blockTypeField.setAccessible(true);

                    // Set the values
                    xCoordField.set(newTileEntity, this.xCoord);
                    yCoordField.set(newTileEntity, this.yCoord);
                    zCoordField.set(newTileEntity, this.zCoord);
                    worldObjField.set(newTileEntity, this.worldObj);
                    blockMetadataField.set(newTileEntity, this.blockMetadata);
                    blockTypeField.set(newTileEntity, this.blockType);
                } catch (Exception e) {
                    MainRegistry.logger.error("Failed to set tile entity properties: " + e.getMessage());
                    return;
                }

                // Replace this proxy with the original tile entity
                worldObj.setTileEntity(xCoord, yCoord, zCoord, newTileEntity);

                MainRegistry.logger.info("Recreated original tile entity at (" + xCoord + ", " + yCoord + ", " + zCoord + "): " + originalClassName);
            } catch (Exception e) {
                MainRegistry.logger.error("Failed to recreate original tile entity at (" + xCoord + ", " + yCoord + ", " + zCoord + "): " + e.getMessage());
            }
        }

        @Override
        public void readFromNBT(net.minecraft.nbt.NBTTagCompound nbt) {
            super.readFromNBT(nbt);

            // Read the original tile entity class name and coordinates
            if (nbt.hasKey("OriginalClass")) {
                originalClassName = nbt.getString("OriginalClass");
            }
            if (nbt.hasKey("OriginalX")) {
                originalX = nbt.getInteger("OriginalX");
            }
            if (nbt.hasKey("OriginalY")) {
                originalY = nbt.getInteger("OriginalY");
            }
            if (nbt.hasKey("OriginalZ")) {
                originalZ = nbt.getInteger("OriginalZ");
            }

            // If we have a valid original tile entity, forward the read to it
            if (originalTileEntity != null) {
                originalTileEntity.readFromNBT(nbt);

                // Try to recreate the fluid user and adapter
                if (originalTileEntity instanceof IFluidUserMK2) {
                    fluidUser = (IFluidUserMK2) originalTileEntity;
                    adapter = new AutoForgeFluidAdapter(fluidUser, this);
                }
            }
        }

        @Override
        public void writeToNBT(net.minecraft.nbt.NBTTagCompound nbt) {
            super.writeToNBT(nbt);

            // Store the original tile entity class name and coordinates
            if (originalTileEntity != null) {
                nbt.setString("OriginalClass", originalTileEntity.getClass().getName());
                nbt.setInteger("OriginalX", originalTileEntity.xCoord);
                nbt.setInteger("OriginalY", originalTileEntity.yCoord);
                nbt.setInteger("OriginalZ", originalTileEntity.zCoord);

                // Forward the write to the original tile entity
                originalTileEntity.writeToNBT(nbt);
            }
        }
    }
}
