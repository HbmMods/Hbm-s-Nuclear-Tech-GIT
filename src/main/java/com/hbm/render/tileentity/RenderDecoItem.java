package com.hbm.render.tileentity;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

	public class RenderDecoItem extends RenderItem {
		RenderDecoItem(TileEntitySpecialRenderer render) {}

		@Override
		public byte getMiniBlockCount(ItemStack stack, byte original)
		{
			return 1;
		}

		@Override
		public byte getMiniItemCount(ItemStack stack, byte original)
		{
			return 1;
		}

		@Override
		public boolean shouldBob()
		{
			return false;
		}

		@Override
		public boolean shouldSpreadItems()
		{
			return false;
		}
}
