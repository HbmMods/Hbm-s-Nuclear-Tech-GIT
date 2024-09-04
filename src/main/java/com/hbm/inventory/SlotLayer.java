package com.hbm.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLayer extends Slot {

	private int originalYPosition;
	private int layer;
	private boolean isVisible = true;

	/**
	 * Creates a slot that can be hidden at will
	 * @param inventory
	 * @param id
	 * @param x
	 * @param y
	 */
	public SlotLayer(IInventory inventory, int id, int x, int y, int layer) {
		super(inventory, id, x, y);
		this.layer = layer;
		this.originalYPosition = y;

		if(layer != 0) hide();
	}

	public boolean isVisible() {
		return this.isVisible;
	}

	public void setLayer(int layer) {
		if(this.layer == layer) {
			show();
		} else {
			hide();
		}
	}

	private void hide() {
		isVisible = false;
		yDisplayPosition = 9999;
	}

	private void show() {
		isVisible = true;
		yDisplayPosition = originalYPosition;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return isVisible;
	}
	
}
