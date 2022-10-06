package com.hbm.render.tileentity;

import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;

public interface IItemRendererProvider {

	public Item getItemForRenderer();
	
	public default Item[] getItemsForRenderer() {
		return new Item[] { this.getItemForRenderer() };
	}
	
	public IItemRenderer getRenderer();
}
