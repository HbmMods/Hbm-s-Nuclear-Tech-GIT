package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import com.hbm.items.block.ItemBlockStorageCrate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerCrateBase extends ContainerBase {

	public ContainerCrateBase(InventoryPlayer invPlayer, IInventory tedf) {
		super(invPlayer, tedf);
		tile.openInventory();
	}

	@Override
	public void playerInv(InventoryPlayer invPlayer, int playerInvX, int playerInvY, int playerHotbarY) {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new SlotNonRetarded(invPlayer, j + i * 9 + 9, playerInvX + j * 18, playerInvY + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(
					invPlayer.currentItem == i ? new SlotPlayerCrateLocked(invPlayer, i, playerInvX + i * 18, playerHotbarY) :
						new SlotNonRetarded(invPlayer, i, playerInvX + i * 18, playerHotbarY));
		}
	}

	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		// prevents the player from moving around the currently open box
		if(player.inventory.getStackInSlot(player.inventory.currentItem) != null &&
			player.inventory.getStackInSlot(player.inventory.currentItem).getItem() instanceof ItemBlockStorageCrate && !(this.tile instanceof TileEntity)) {
			if (mode == 2 && button == player.inventory.currentItem) {
				return null;
			}
			if (index == player.inventory.currentItem + 27 + this.tile.getSizeInventory()) {
				return null;
			}
		}
		return super.slotClick(index, button, mode, player);
	}

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		super.onContainerClosed(p_75134_1_);
		tile.closeInventory();
	}

	/**
	 * No touching anything here. No moving around, no taking, no inserting, fuck off.
	 */
	public class SlotPlayerCrateLocked extends SlotNonRetarded {

		public SlotPlayerCrateLocked(IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);
		}
		@Override
		public boolean canTakeStack(EntityPlayer player) {
			return false;
		}

		@Override
		public boolean isItemValid(ItemStack item) {
			return false;
		}
	}
}
