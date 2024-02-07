package com.hbm.items.machine;

import com.hbm.util.CompatExternal;

import com.hbm.inventory.FluidContainer;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Unsiphonable;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemPipette;

import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemFluidSiphon extends Item {
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int i, float f0, float f1, float f2) {
		TileEntity te = CompatExternal.getCoreFromPos(world, x, y, z);

		if(te != null && (te instanceof IFluidStandardReceiver || te instanceof IFluidStandardTransceiver)) {
            FluidTank[] tanks;
            if (te instanceof IFluidStandardReceiver) {
                tanks = ((IFluidStandardReceiver) te).getReceivingTanks();
            } else {
                tanks = ((IFluidStandardTransceiver) te).getReceivingTanks();
            }

            boolean hasDrainedTank = false;
            
            // We need to iterate through the inventory for _each_ siphonable tank, so we can handle fluids that can only go into certain containers
            // After we successfully siphon any fluid from a tank, we stop further processing, multiple fluid types require multiple clicks
            for (FluidTank tank : tanks) {
                if (tank.getFill() <= 0) continue;

                ItemStack availablePipette = null;
                FluidType tankType = tank.getTankType();

                if (tankType.hasTrait(FT_Unsiphonable.class)) continue;

                for (int j = 0; j < player.inventory.mainInventory.length; j++) {
                    ItemStack inventoryStack = player.inventory.mainInventory[j];
                    if (inventoryStack == null) continue;

                    FluidContainer container = FluidContainerRegistry.getContainer(tankType, inventoryStack);

                    if (availablePipette == null && inventoryStack.getItem() instanceof ItemPipette) {
                        ItemPipette pipette = (ItemPipette) inventoryStack.getItem();
                        if (!pipette.willFizzle(tankType) && pipette != ModItems.pipette_laboratory) { // Ignoring laboratory pipettes for now
                            availablePipette = inventoryStack;
                        }
                    }

                    if (container == null) continue;

                    ItemStack full = FluidContainerRegistry.getFullContainer(inventoryStack, tankType);

                    while (tank.getFill() >= container.content && inventoryStack.stackSize > 0) {
                        hasDrainedTank = true;

                        inventoryStack.stackSize--;
                        if (inventoryStack.stackSize <= 0) {
                            player.inventory.mainInventory[j] = null;
                        }

                        ItemStack filledContainer = full.copy();
                        tank.setFill(tank.getFill() - container.content);
                        player.inventory.addItemStackToInventory(filledContainer);
                    }
                }

                // If the remainder of the tank can only fit into a pipette, fill a pipette with the remainder
                // Will not auto-fill fizzlable pipettes, there is no feedback for the fizzle in this case, and that's a touch too unfair
                if (availablePipette != null && tank.getFill() < 1000) {
                    ItemPipette pipette = (ItemPipette) availablePipette.getItem();
		
                    if (pipette.acceptsFluid(tankType, availablePipette)) {
                        hasDrainedTank = true;
                        tank.setFill(pipette.tryFill(tankType, tank.getFill(), availablePipette));
                    }
                }

                if (hasDrainedTank) return true;
            }
		}

		return false;
	}
    
}
