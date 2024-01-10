package com.hbm.inventory.container;

import com.hbm.inventory.SlotCraftingOutput;
import com.hbm.inventory.SlotNonRetarded;
import com.hbm.tileentity.machine.TileEntityMachineStrandCaster;
import com.hbm.util.InventoryUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMachineStrandCaster extends Container {

    protected TileEntityMachineStrandCaster caster;

    public ContainerMachineStrandCaster(InventoryPlayer invPlayer, TileEntityMachineStrandCaster caster) {
        this.caster = caster;

        //the wretched mold
        this.addSlotToContainer(new SlotNonRetarded(this.caster, 0, 57, 62));

        //output
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlotToContainer(new SlotCraftingOutput(invPlayer.player, this.caster, j + i * 2 + 1, 125 + j * 18, 26 + i * 18));
            }
        }


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 132 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 190));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack originalStack = slot.getStack();
            stack = originalStack.copy();

            if (index <= 6) {
                if (!InventoryUtil.mergeItemStack(this.inventorySlots, originalStack, 7, this.inventorySlots.size(), true)) {
                    return null;
                }

                slot.onSlotChange(originalStack, stack);

            } else if (!InventoryUtil.mergeItemStack(this.inventorySlots, originalStack, 1, 2, false)) {
                return null;
            }

            if (originalStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return caster.isUseableByPlayer(player);
    }
}
