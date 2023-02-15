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
		B_IRON(0xE2C0AA), //0
		B_COPPER(0xEC9A63), //1
		B_LITHIUM(0xEDEDED), //2
		B_SILICON(0xFFFBD1),//3
		B_LEAD(0x646470),//4
		B_TITANIUM(0xF2EFE2),//5
		B_ALUMINIUM(0xE8F2F9),//6
		B_SULFUR(0xEAD377),//7
		B_CALCIUM(0xCFCFA6),//8
		B_BISMUTH(0x8D8577);//9
		
		public int color;
		
		private EnumByproduct(int color) {
			this.color = color;
		}
	}
}
