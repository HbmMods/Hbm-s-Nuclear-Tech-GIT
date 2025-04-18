package api.ntm1of90.compat.fluid;

import com.hbm.main.MainRegistry;

import api.hbm.fluidmk2.IFluidUserMK2;
import api.ntm1of90.compat.fluid.registry.ForgeFluidAdapterRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * A hook into Forge's capability system to provide IFluidHandler capabilities for NTM TileEntities.
 * This is a workaround for Forge 1.7.10 not having a proper capability system.
 */
public class ForgeFluidCapabilityHook {

    private static boolean initialized = false;

    /**
     * Initialize the hook
     */
    public static void initialize() {
        if (initialized) {
            return;
        }

        // Register the event handler
        MinecraftForge.EVENT_BUS.register(new ForgeFluidCapabilityHook());

        // Register the world unload handler
        cpw.mods.fml.common.FMLCommonHandler.instance().bus().register(new ForgeFluidCapabilityHook());

        initialized = true;
        MainRegistry.logger.info("ForgeFluidCapabilityHook initialized");
    }

    /**
     * Event handler for player interaction
     * This is used to intercept fluid-related interactions with NTM tile entities
     */
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        // Get the tile entity at the clicked position
        TileEntity tileEntity = event.world.getTileEntity(event.x, event.y, event.z);
        if (tileEntity == null) {
            return;
        }

        // If the tile entity already implements IFluidHandler, let Forge handle it
        if (tileEntity instanceof IFluidHandler) {
            return;
        }

        // If the tile entity implements IFluidUserMK2, check if it has an adapter
        if (tileEntity instanceof IFluidUserMK2) {
            // Get the fluid handler for this tile entity
            IFluidHandler fluidHandler = ForgeFluidAdapterRegistry.getFluidHandler(tileEntity);
            if (fluidHandler != null) {
                // Check if the player is holding a fluid container
                net.minecraft.item.ItemStack heldItem = event.entityPlayer.getHeldItem();
                if (heldItem != null && net.minecraftforge.fluids.FluidContainerRegistry.isContainer(heldItem)) {
                    // Handle fluid container interaction manually
                    boolean handled = handleFluidContainerInteraction(heldItem, fluidHandler, event.entityPlayer);
                    if (handled) {
                        // Set the result to ALLOW to prevent further processing
                        event.useBlock = Result.ALLOW;
                        event.useItem = Result.DENY;
                    }
                }
            }
        }
    }

    /**
     * Event handler for world unload
     * This is used to clear the adapter registry when a world is unloaded
     */
    @SubscribeEvent
    public void onWorldUnload(net.minecraftforge.event.world.WorldEvent.Unload event) {
        // Clear the adapter registry
        ForgeFluidAdapterRegistry.clearAdapters();
    }

    /**
     * Event handler for server tick
     * This is used to register fluid handlers for new tile entities
     */
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        // Process all loaded worlds
        for (net.minecraft.world.WorldServer world : net.minecraft.server.MinecraftServer.getServer().worldServers) {
            // Process all loaded tile entities
            for (Object obj : world.loadedTileEntityList) {
                if (obj instanceof TileEntity) {
                    TileEntity tileEntity = (TileEntity) obj;

                    // Skip null tile entities or those that have been removed
                    if (tileEntity == null || tileEntity.isInvalid()) {
                        continue;
                    }

                    // If the tile entity implements IFluidUserMK2 but not IFluidHandler,
                    // make sure it has an adapter registered
                    if (tileEntity instanceof IFluidUserMK2 && !(tileEntity instanceof IFluidHandler)) {
                        ForgeFluidAdapterRegistry.getFluidHandler(tileEntity);
                    }
                }
            }
        }
    }

    /**
     * Handle fluid container interaction with a fluid handler
     * This is a replacement for FluidUtil.interactWithFluidHandler which doesn't exist in Forge 1.7.10
     *
     * @param container The fluid container item stack
     * @param fluidHandler The fluid handler to interact with
     * @param player The player performing the interaction
     * @return True if the interaction was successful, false otherwise
     */
    private boolean handleFluidContainerInteraction(net.minecraft.item.ItemStack container, IFluidHandler fluidHandler, net.minecraft.entity.player.EntityPlayer player) {
        if (container == null || fluidHandler == null) {
            return false;
        }

        // Try to fill the fluid handler from the container
        net.minecraftforge.fluids.FluidStack fluid = net.minecraftforge.fluids.FluidContainerRegistry.getFluidForFilledItem(container);
        if (fluid != null) {
            // Container has fluid, try to drain it into the handler
            int filled = fluidHandler.fill(net.minecraftforge.common.util.ForgeDirection.UNKNOWN, fluid, false);
            if (filled > 0) {
                // Handler can accept the fluid, do the actual transfer
                fluidHandler.fill(net.minecraftforge.common.util.ForgeDirection.UNKNOWN, fluid, true);

                // Give the player the empty container if they're not in creative mode
                if (!player.capabilities.isCreativeMode) {
                    net.minecraft.item.ItemStack emptyContainer = net.minecraftforge.fluids.FluidContainerRegistry.drainFluidContainer(container);
                    if (emptyContainer != null) {
                        // Try to add the empty container to the player's inventory
                        if (container.stackSize == 1) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, emptyContainer);
                        } else {
                            container.stackSize--;
                            if (!player.inventory.addItemStackToInventory(emptyContainer)) {
                                player.dropPlayerItemWithRandomChoice(emptyContainer, false);
                            }
                        }
                    }
                } else {
                    // In creative mode, just keep the filled container
                }

                return true;
            }
        } else {
            // Container is empty or not a fluid container, try to fill it from the handler
            net.minecraftforge.fluids.FluidTankInfo[] tankInfo = fluidHandler.getTankInfo(net.minecraftforge.common.util.ForgeDirection.UNKNOWN);
            if (tankInfo != null && tankInfo.length > 0) {
                for (net.minecraftforge.fluids.FluidTankInfo info : tankInfo) {
                    if (info.fluid != null && info.fluid.amount > 0) {
                        // Try to fill the container with this fluid
                        net.minecraft.item.ItemStack filledContainer = net.minecraftforge.fluids.FluidContainerRegistry.fillFluidContainer(info.fluid, container);
                        if (filledContainer != null) {
                            // Container can be filled, get the amount of fluid needed
                            net.minecraftforge.fluids.FluidStack fluidToExtract = net.minecraftforge.fluids.FluidContainerRegistry.getFluidForFilledItem(filledContainer);
                            if (fluidToExtract != null) {
                                // Drain the fluid from the handler
                                net.minecraftforge.fluids.FluidStack drained = fluidHandler.drain(net.minecraftforge.common.util.ForgeDirection.UNKNOWN, fluidToExtract, true);
                                if (drained != null && drained.amount > 0) {
                                    // Give the player the filled container if they're not in creative mode
                                    if (!player.capabilities.isCreativeMode) {
                                        if (container.stackSize == 1) {
                                            player.inventory.setInventorySlotContents(player.inventory.currentItem, filledContainer);
                                        } else {
                                            container.stackSize--;
                                            if (!player.inventory.addItemStackToInventory(filledContainer)) {
                                                player.dropPlayerItemWithRandomChoice(filledContainer, false);
                                            }
                                        }
                                    } else {
                                        // In creative mode, just keep the empty container
                                    }

                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
