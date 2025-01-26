package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;

public class ItemPACoil extends ItemEnumMulti {

	public ItemPACoil() {
		super(EnumCoilType.class, true, true);
		this.setMaxStackSize(1);
	}

	public static enum EnumCoilType {
		GOLD(0, 10_000, 0, 10_000, 0.01D),
		NIOBIUM(5_000, 100_000, 5_000, 100_000, 0.001D),
		BSCCO(50_000, 500_000, 50_000, 500_000, 0.00025D);

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
}
