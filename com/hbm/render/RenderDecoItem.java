package com.hbm.render;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

	public class RenderDecoItem extends RenderItem {
		RenderDecoItem(RenderDecoBlockAlt render) {}

		public byte getMiniBlockCount(ItemStack stack, byte original)
		{
			return 1;
		}

		public byte getMiniItemCount(ItemStack stack, byte original)
		{
			return 1;
		}

		public boolean shouldBob()
		{
			return false;
		}

		public boolean shouldSpreadItems()
		{
			return false;
		}
}
