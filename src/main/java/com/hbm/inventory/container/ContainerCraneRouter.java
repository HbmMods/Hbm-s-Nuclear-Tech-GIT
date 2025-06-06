package com.hbm.inventory.container;

import com.hbm.inventory.SlotPattern;
import com.hbm.tileentity.network.TileEntityCraneRouter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCraneRouter extends ContainerBase {

	private TileEntityCraneRouter router;

	public ContainerCraneRouter(InventoryPlayer invPlayer, TileEntityCraneRouter router) {
		super(invPlayer, router);
		this.router = router;

		for(int j = 0; j < 2; j++) {
			for(int i = 0; i < 3; i++) {
				for(int k = 0; k < 5; k++) {
					this.addSlotToContainer(new SlotPattern(router, k + j * 15 + i * 5, 34 + k * 18 + j * 98, 17 + i * 26));
				}
			}
		}
		playerInv(invPlayer, 47, 119, 177);
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		//L/R: 0
		//M3: 3
		//SHIFT: 1
		//DRAG: 5

		if(index < 0 || index >= 30) {
			return super.slotClick(index, button, mode, player);
		}

		Slot slot = this.getSlot(index);
		
		ItemStack ret = null;
		ItemStack held = player.inventory.getItemStack();
		
		if(slot.getHasStack())
			ret = slot.getStack().copy();
		
		if(button == 1 && mode == 0 && slot.getHasStack()) {
			router.nextMode(index);
			return ret;
			
		} else {
	
			slot.putStack(held);
			router.initPattern(slot.getStack(), index);
			
			return ret;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
}
