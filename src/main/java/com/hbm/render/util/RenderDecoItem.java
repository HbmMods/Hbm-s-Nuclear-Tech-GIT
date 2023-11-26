package com.hbm.render.util;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

/**
 * For small items as part of a TESR, e.g. items in a press
 * @author hbm
 */
public class RenderDecoItem extends RenderItem {
	
	public RenderDecoItem(TileEntitySpecialRenderer render) {
		this.setRenderManager(RenderManager.instance);
	}

	@Override
	public byte getMiniBlockCount(ItemStack stack, byte original) {
		return 1;
	}

	@Override
	public byte getMiniItemCount(ItemStack stack, byte original) {
		return 1;
	}

	@Override
	public boolean shouldBob() {
		return false;
	}

	@Override
	public boolean shouldSpreadItems() {
		return false;
	}
}
