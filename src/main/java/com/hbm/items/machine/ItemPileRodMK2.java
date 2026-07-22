package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemPileRodMK2 extends ItemEnumMulti {
	
	/*  _____
	 * /     \
	 * \_____/
	 * |     |
	 * |     |
	 * |     |
	 * |     |
	 * \_____/
	 * 
	 * Not terribly sophisticated,
	 * it's a cylinder.
	 */
	
	public static final String KEY_NBT_DEPLETION = "depletion";

	public ItemPileRodMK2() {
		super(EnumPileRod.class, true, true);
	}
	
	public static enum EnumPileRod {
		RA226BE(1D),
		PO210BE(1D),
		ZR(		0D, 0D, 0D),
		NU(		1D,	1_000D,	1D),
		RGP(	1D,	1_000D,	1D),
		PU239(	1D,	1_000D,	1D),
		WASTE(	1D,	1_000D,	1D);

		public double reactionMult = 1.0D;
		public double life = 1_000D;
		public double heatMult = 1.0D;
		public double neutronSource = 0D;
		
		private EnumPileRod(double neutronSource) {
			this.neutronSource = neutronSource;
			this.reactionMult = 0;
			this.life = 0;
			this.heatMult = 0;
		}
		
		private EnumPileRod(double reaction, double life, double heat) {
			this.reactionMult = reaction;
			this.life = life;
			this.heatMult = heat;
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0D;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		EnumPileRod rod = EnumUtil.grabEnumSafely(EnumPileRod.class, stack.getItemDamage());
		double life = rod.life;
		if(life <= 0) return 0D;
		return getDepletion(stack) / life;
	}
	
	public static double getDepletion(ItemStack stack) {
		if(!stack.hasTagCompound()) return 0D;
		return stack.stackTagCompound.getDouble(KEY_NBT_DEPLETION);
	}
	
	public static void setDepletion(ItemStack stack, double depletion) {
		if(!stack.hasTagCompound()) stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setDouble(KEY_NBT_DEPLETION, depletion);
	}
	
	public static double getReactivity(ItemStack stack, double inFlux) {
		EnumPileRod rod = EnumUtil.grabEnumSafely(EnumPileRod.class, stack.getItemDamage());
		double outFlux = rod.neutronSource;
		if(rod.reactionMult > 0) {
			outFlux += BobMathUtil.squirt(inFlux) * rod.reactionMult;
		}
		return outFlux;
	}
}
