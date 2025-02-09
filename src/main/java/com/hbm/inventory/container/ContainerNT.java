package com.hbm.inventory.container;

import java.util.Iterator;

import com.hbm.util.InventoryUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ContainerNT extends Container {

	/**
	 * Fix: mergeItemStack does not respect inventory or slot stack limitations.
	 * We simply intercept the method and call InventoryUtil.mergeItemStack which
	 * runs the same logic but respecting stack limits.
	 */
	@Override
	protected boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
		return InventoryUtil.mergeItemStack(inventorySlots, stack, start, end, reverse);
	}

	/**
	 * Fix: the default behavior of transferStackInSlot simply crashes the game.
	 * We intercept this part and return null, which means that by default, the
	 * shift click function does nothing.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}
	
	// fuck you mojang
	@Override
	public ItemStack slotClick(int index, int button, int mode, EntityPlayer player) {
		
		/*
		 * INDEX
		 * -999: Mouse outside GUI
		 * MODE
		 * 2: Hotbar keys
		 * 3: Duplicate item
		 * 4: Drop item
		 * 5: Drag
		 */
		
		ItemStack returnStack = null;
		InventoryPlayer invPlayer = player.inventory;
		Slot slot = index >= 0 && index < this.inventorySlots.size() ? (Slot) this.inventorySlots.get(index) : null;
		
		int i1;
		ItemStack itemstack3;

		/// DRAG ///
		if(mode == 5) {
			int l = this.field_94536_g;
			this.field_94536_g = func_94532_c(button);

			if((l != 1 || this.field_94536_g != 2) && l != this.field_94536_g) {
				this.func_94533_d();
			} else if(invPlayer.getItemStack() == null) {
				this.func_94533_d();
			} else if(this.field_94536_g == 0) {
				this.field_94535_f = func_94529_b(button);

				if(func_94528_d(this.field_94535_f)) {
					this.field_94536_g = 1;
					this.field_94537_h.clear();
				} else {
					this.func_94533_d();
				}
			} else if(this.field_94536_g == 1) {

				if(slot != null && func_94527_a(slot, invPlayer.getItemStack(), true) && slot.isItemValid(invPlayer.getItemStack())
						&& invPlayer.getItemStack().stackSize > this.field_94537_h.size() && this.canDragIntoSlot(slot)) {
					this.field_94537_h.add(slot);
				}
			} else if(this.field_94536_g == 2) {
				if(!this.field_94537_h.isEmpty()) {
					itemstack3 = invPlayer.getItemStack().copy();
					i1 = invPlayer.getItemStack().stackSize;
					Iterator iterator = this.field_94537_h.iterator();

					while(iterator.hasNext()) {
						Slot slot1 = (Slot) iterator.next();

						if(slot1 != null && func_94527_a(slot1, invPlayer.getItemStack(), true) && slot1.isItemValid(invPlayer.getItemStack())
								&& invPlayer.getItemStack().stackSize >= this.field_94537_h.size() && this.canDragIntoSlot(slot1)) {
							ItemStack stackCopy = itemstack3.copy();
							int j1 = slot1.getHasStack() ? slot1.getStack().stackSize : 0;
							func_94525_a(this.field_94537_h, this.field_94535_f, stackCopy, j1);

							if(stackCopy.stackSize > stackCopy.getMaxStackSize()) {
								stackCopy.stackSize = stackCopy.getMaxStackSize();
							}

							if(stackCopy.stackSize > slot1.getSlotStackLimit()) {
								stackCopy.stackSize = slot1.getSlotStackLimit();
							}

							i1 -= stackCopy.stackSize - j1;
							slot1.putStack(stackCopy);
						}
					}

					itemstack3.stackSize = i1;

					if(itemstack3.stackSize <= 0) {
						itemstack3 = null;
					}

					invPlayer.setItemStack(itemstack3);
				}

				this.func_94533_d();
			} else {
				this.func_94533_d();
			}
		/// NOBODY KNOWS ///
		} else if(this.field_94536_g != 0) {
			this.func_94533_d();
			
		/// NON-DRAG ///
		} else {
			
			int l1;
			ItemStack itemstack5;

			/// LMB/RMB ///
			if((mode == 0 || mode == 1) && (button == 0 || button == 1)) {
				if(index == -999) {
					if(invPlayer.getItemStack() != null && index == -999) {
						if(button == 0) {
							player.dropPlayerItemWithRandomChoice(invPlayer.getItemStack(), true);
							invPlayer.setItemStack((ItemStack) null);
						}

						if(button == 1) {
							player.dropPlayerItemWithRandomChoice(invPlayer.getItemStack().splitStack(1), true);

							if(invPlayer.getItemStack().stackSize == 0) {
								invPlayer.setItemStack((ItemStack) null);
							}
						}
					}
				} else if(mode == 1) {
					if(index < 0) {
						return null;
					}

					slot = (Slot) this.inventorySlots.get(index);

					if(slot != null && slot.canTakeStack(player)) {
						itemstack3 = this.transferStackInSlot(player, index);

						if(itemstack3 != null) {
							Item item = itemstack3.getItem();
							returnStack = itemstack3.copy();

							if(slot.getStack() != null && slot.getStack().getItem() == item) {
								this.retrySlotClick(index, button, true, player);
							}
						}
					}
				} else {
					if(index < 0) {
						return null;
					}

					slot = (Slot) this.inventorySlots.get(index);

					if(slot != null) {
						itemstack3 = slot.getStack();
						ItemStack draggedStack = invPlayer.getItemStack();

						if(itemstack3 != null) {
							returnStack = itemstack3.copy();
						}

						if(itemstack3 == null) {
							if(draggedStack != null && slot.isItemValid(draggedStack)) {
								l1 = button == 0 ? draggedStack.stackSize : 1;

								if(l1 > slot.getSlotStackLimit()) {
									l1 = slot.getSlotStackLimit();
								}

								if(draggedStack.stackSize >= l1) {
									slot.putStack(draggedStack.splitStack(l1));
								}

								if(draggedStack.stackSize == 0) {
									invPlayer.setItemStack((ItemStack) null);
								}
							}
						} else if(slot.canTakeStack(player)) {
							if(draggedStack == null) {
								l1 = button == 0 ? itemstack3.stackSize : (itemstack3.stackSize + 1) / 2;
								itemstack5 = slot.decrStackSize(l1);
								invPlayer.setItemStack(itemstack5);

								if(itemstack3.stackSize == 0) {
									slot.putStack((ItemStack) null);
								}

								slot.onPickupFromSlot(player, invPlayer.getItemStack());
							} else if(slot.isItemValid(draggedStack)) {
								if(itemstack3.getItem() == draggedStack.getItem() && itemstack3.getItemDamage() == draggedStack.getItemDamage()
										&& ItemStack.areItemStackTagsEqual(itemstack3, draggedStack)) {
									l1 = button == 0 ? draggedStack.stackSize : 1;

									if(l1 > slot.getSlotStackLimit() - itemstack3.stackSize) {
										l1 = slot.getSlotStackLimit() - itemstack3.stackSize;
									}

									if(l1 > draggedStack.getMaxStackSize() - itemstack3.stackSize) {
										l1 = draggedStack.getMaxStackSize() - itemstack3.stackSize;
									}

									draggedStack.splitStack(l1);

									if(draggedStack.stackSize == 0) {
										invPlayer.setItemStack((ItemStack) null);
									}

									itemstack3.stackSize += l1;
								} else if(draggedStack.stackSize <= slot.getSlotStackLimit()) {
									slot.putStack(draggedStack);
									invPlayer.setItemStack(itemstack3);
								}
							} else if(itemstack3.getItem() == draggedStack.getItem() && draggedStack.getMaxStackSize() > 1
									&& (!itemstack3.getHasSubtypes() || itemstack3.getItemDamage() == draggedStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(itemstack3, draggedStack)) {
								l1 = itemstack3.stackSize;

								if(l1 > 0 && l1 + draggedStack.stackSize <= draggedStack.getMaxStackSize()) {
									draggedStack.stackSize += l1;
									itemstack3 = slot.decrStackSize(l1);

									if(itemstack3.stackSize == 0) {
										slot.putStack((ItemStack) null);
									}

									slot.onPickupFromSlot(player, invPlayer.getItemStack());
								}
							}
						}

						slot.onSlotChanged();
					}
				}
			} else if(mode == 2 && button >= 0 && button < 9) {
				slot = (Slot) this.inventorySlots.get(index);

				if(slot.canTakeStack(player)) {
					itemstack3 = invPlayer.getStackInSlot(button);
					boolean flag = itemstack3 == null || slot.inventory == invPlayer && slot.isItemValid(itemstack3);
					l1 = -1;

					if(!flag) {
						l1 = invPlayer.getFirstEmptyStack();
						flag |= l1 > -1;
					}

					if(slot.getHasStack() && flag) {
						itemstack5 = slot.getStack();
						invPlayer.setInventorySlotContents(button, itemstack5.copy());

						if((slot.inventory != invPlayer || !slot.isItemValid(itemstack3)) && itemstack3 != null) {
							if(l1 > -1) {
								invPlayer.addItemStackToInventory(itemstack3);
								slot.decrStackSize(itemstack5.stackSize);
								slot.putStack((ItemStack) null);
								slot.onPickupFromSlot(player, itemstack5);
							}
						} else {
							slot.decrStackSize(itemstack5.stackSize);
							slot.putStack(itemstack3);
							slot.onPickupFromSlot(player, itemstack5);
						}
					} else if(!slot.getHasStack() && itemstack3 != null && slot.isItemValid(itemstack3)) {
						invPlayer.setInventorySlotContents(button, (ItemStack) null);
						slot.putStack(itemstack3);
					}
				}
			} else if(mode == 3 && player.capabilities.isCreativeMode && invPlayer.getItemStack() == null && index >= 0) {
				slot = (Slot) this.inventorySlots.get(index);

				if(slot != null && slot.getHasStack()) {
					itemstack3 = slot.getStack().copy();
					itemstack3.stackSize = itemstack3.getMaxStackSize();
					invPlayer.setItemStack(itemstack3);
				}
			} else if(mode == 4 && invPlayer.getItemStack() == null && index >= 0) {
				slot = (Slot) this.inventorySlots.get(index);

				if(slot != null && slot.getHasStack() && slot.canTakeStack(player)) {
					itemstack3 = slot.decrStackSize(button == 0 ? 1 : slot.getStack().stackSize);
					slot.onPickupFromSlot(player, itemstack3);
					player.dropPlayerItemWithRandomChoice(itemstack3, true);
				}
			} else if(mode == 6 && index >= 0) {
				slot = (Slot) this.inventorySlots.get(index);
				itemstack3 = invPlayer.getItemStack();

				if(itemstack3 != null && (slot == null || !slot.getHasStack() || !slot.canTakeStack(player))) {
					i1 = button == 0 ? 0 : this.inventorySlots.size() - 1;
					l1 = button == 0 ? 1 : -1;

					for(int i2 = 0; i2 < 2; ++i2) {
						for(int j2 = i1; j2 >= 0 && j2 < this.inventorySlots.size() && itemstack3.stackSize < itemstack3.getMaxStackSize(); j2 += l1) {
							Slot slot3 = (Slot) this.inventorySlots.get(j2);

							if(slot3.getHasStack() && func_94527_a(slot3, itemstack3, true) && slot3.canTakeStack(player) && this.func_94530_a(itemstack3, slot3)
									&& (i2 != 0 || slot3.getStack().stackSize != slot3.getStack().getMaxStackSize())) {
								int k1 = Math.min(itemstack3.getMaxStackSize() - itemstack3.stackSize, slot3.getStack().stackSize);
								ItemStack itemstack2 = slot3.decrStackSize(k1);
								itemstack3.stackSize += k1;

								if(itemstack2.stackSize <= 0) {
									slot3.putStack((ItemStack) null);
								}

								slot3.onPickupFromSlot(player, itemstack2);
							}
						}
					}
				}

				this.detectAndSendChanges();
			}
		}

		return returnStack;
	}
}
