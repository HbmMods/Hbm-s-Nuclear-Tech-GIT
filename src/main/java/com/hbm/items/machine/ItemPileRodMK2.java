package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

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
		/* 0 */ RA226BE(1D),
		/* 1 */ PO210BE(1D),
		/* 2 */ ZR(		0D,      0D, 0D,    2),
		/* 3 */ NU(		1D, 25_000D, 0.25D, 4),
		/* 4 */ PU239(	1D,    500D, 0.5D,  5),
		/* 5 */ RGP(	1D,  1_000D, 0.5D,  6),
		/* 6 */ WASTE(	1D,      0D, 1.5D,  6);

		public double reactionMult = 1.0D;
		public double life = 1_000D;
		public double heatMult = 0.0D;
		public double neutronSource = 0D;
		public int turnsInto;
		
		private EnumPileRod(double neutronSource) {
			this.neutronSource = neutronSource;
			this.reactionMult = 0;
			this.life = 0;
			this.heatMult = 0;
		}
		
		private EnumPileRod(double reaction, double life, double heat, int turnsInto) {
			this.reactionMult = reaction;
			this.life = life;
			this.heatMult = heat;
			this.turnsInto = turnsInto;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		EnumPileRod rod = EnumUtil.grabEnumSafely(EnumPileRod.class, stack.getItemDamage());
		
		if(rod.life > 0) {
			list.add("Lifetime: " + (int) Math.round(rod.life));
			double depletion = getDepletionPercent(stack);
			if(depletion > 0) list.add("Depletion: " + (int) Math.round(depletion) + "%");
		}
		
		for(String loc : I18nUtil.autoBreak(Minecraft.getMinecraft().fontRenderer, I18nUtil.resolveKey(this.getUnlocalizedName(stack) + ".desc"), 225)) {
			list.add(EnumChatFormatting.YELLOW + loc);
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
	
	public static double getDepletionPercent(ItemStack stack) {
		if(stack == null) return 0D;
		EnumPileRod rod = EnumUtil.grabEnumSafely(EnumPileRod.class, stack.getItemDamage());
		double life = rod.life;
		if(life <= 0) return 0D;
		return (getDepletion(stack) / life) * 100;
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
	
	public static double getHeatPerNeutron(ItemStack stack) {
		EnumPileRod rod = EnumUtil.grabEnumSafely(EnumPileRod.class, stack.getItemDamage());
		return rod.heatMult;
	}
	
	public static ItemStack react(ItemStack stack, double inFlux) {
		EnumPileRod rod = EnumUtil.grabEnumSafely(EnumPileRod.class, stack.getItemDamage());
		if(rod.life <= 0) return stack;
		double dep = getDepletion(stack) + inFlux;
		
		if(dep < rod.life) {
			setDepletion(stack, dep);
			return stack;
		} else {
			return new ItemStack(stack.getItem(), 1, rod.turnsInto);
		}
	}
}
