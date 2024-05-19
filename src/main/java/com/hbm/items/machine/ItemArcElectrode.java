package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.EnumUtil;

import net.minecraft.item.ItemStack;

public class ItemArcElectrode extends ItemEnumMulti {

	public ItemArcElectrode() {
		super(EnumElectrodeType.class, true, true);
		this.setFull3D();
		this.setMaxStackSize(1);
	}
	
	public static int getDurability(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0;
		return stack.stackTagCompound.getInteger("durability");
	}
	
	public static int getMaxtDurability(ItemStack stack) {
		EnumElectrodeType num = EnumUtil.grabEnumSafely(EnumElectrodeType.class, stack.getItemDamage());
		return num.durability;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (double) getDurability(stack) / (double) getMaxtDurability(stack);
	}

	public static enum EnumElectrodeType {
		GRAPHITE(	10),
		LANTHANIUM(	50),
		DESH(		250),
		SATURNITE(	500);
		
		public int durability;
		
		private EnumElectrodeType(int dura) {
			this.durability = dura;
		}
	}
}
