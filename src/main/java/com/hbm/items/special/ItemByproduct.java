package com.hbm.items.special;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public class ItemByproduct extends ItemEnumMulti {

	public ItemByproduct() {
		super(EnumByproduct.class, true, false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		
		EnumByproduct ore = EnumUtil.grabEnumSafely(EnumByproduct.class, stack.getItemDamage());
		return ore.color;
	}
	
	public static enum EnumByproduct {
		B_IRON(0xE2C0AA),
		B_COPPER(0xEC9A63),
		B_LITHIUM(0xEDEDED),
		B_SILICON(0xFFFBD1),
		B_LEAD(0x646470),
		B_TITANIUM(0xF2EFE2),
		B_ALUMINIUM(0xE8F2F9),
		B_SULFUR(0xEAD377),
		B_CALCIUM(0xCFCFA6),
		B_BISMUTH(0x8D8577),
		B_RADIUM(0xE9FAF6),
		B_TECHNETIUM(0xCADFDF),
		B_POLONIUM(0xCADFDF),
		B_URANIUM(0x868D82);
		
		public int color;
		
		private EnumByproduct(int color) {
			this.color = color;
		}
	}
}
