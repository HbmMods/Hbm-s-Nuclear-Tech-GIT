package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.EnumUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemPACoil extends ItemEnumMulti {

	public ItemPACoil() {
		super(EnumCoilType.class, true, true);
		this.setMaxStackSize(1);
	}

	public static enum EnumCoilType {
		GOLD(0, 10_000, 0, 10_000, 0.99D),
		NIOBIUM(5_000, 100_000, 5_000, 100_000, 0.999D),
		BSCCO(50_000, 500_000, 50_000, 500_000, 0.99975D);

		public int quadMin;
		public int quadMax;
		public int diMin;
		public int diMax;
		public double diMult;
		
		private EnumCoilType(int quadMin, int quadMax, int diMin, int diMax, double diMult) {
			this.quadMin = quadMin;
			this.quadMax = quadMax;
			this.diMin = diMin;
			this.diMax = diMax;
			this.diMult = diMult;
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		EnumCoilType type = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		list.add("Quadrupole operational range: " + type.quadMin + " - " + type.quadMax);
		list.add("Dipole operational range: " + type.diMin + " - " + type.diMax);
		list.add("Dipole momentum multiplier: x" + type.diMult);
	}
}
