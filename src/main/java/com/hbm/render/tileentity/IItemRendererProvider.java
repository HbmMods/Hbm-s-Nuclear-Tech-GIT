package com.hbm.render.tileentity;

import net.minecraft.item.Item;
import net.minecraftforge.client.IItemRenderer;

public interface IItemRendererProvider {

	public Item getItemForRenderer();
	public IItemRenderer getRenderer();
}
