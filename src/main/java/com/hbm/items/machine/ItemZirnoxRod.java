package com.hbm.items.machine;

import java.util.List;
import java.util.Locale;

import com.hbm.items.ItemEnumMulti;
import com.hbm.util.BobMathUtil;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemZirnoxRod extends ItemEnumMulti {

	public ItemZirnoxRod() {
		super(EnumZirnoxType.class, true, true);
		this.setMaxStackSize(1);
		this.canRepair = false;
	}

	public static void incrementLifeTime(ItemStack stack) {

		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		int time = stack.stackTagCompound.getInteger("life");

		stack.stackTagCompound.setInteger("life", time + 1);
	}

	public static void setLifeTime(ItemStack stack, int time) {

		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		stack.stackTagCompound.setInteger("life", time);
	}

	public static int getLifeTime(ItemStack stack) {

		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}

		return stack.stackTagCompound.getInteger("life");
	}

	public boolean showDurabilityBar(ItemStack stack) {
		return getDurabilityForDisplay(stack) > 0D;
	}

	public double getDurabilityForDisplay(ItemStack stack) {
		EnumZirnoxType num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		return (double) getLifeTime(stack) / (double) num.maxLife;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {


		EnumZirnoxType num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.rbmk.depletion", ((int)((((double)getLifeTime(stack)) / (double)num.maxLife) * 100000)) / 1000D + "%"));
		String[] loc = I18nUtil.resolveKeyArray("desc.item.zirnox" + (num.breeding ? "BreedingRod" : "Rod"), BobMathUtil.getShortNumber(num.maxLife));

		if(num.breeding)
			loc = I18nUtil.resolveKeyArray("desc.item.zirnoxBreedingRod", BobMathUtil.getShortNumber(num.maxLife));
		else
			loc = I18nUtil.resolveKeyArray("desc.item.zirnoxRod", num.heat, BobMathUtil.getShortNumber(num.maxLife));

		for(String s : loc) {
			list.add(s);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		Enum[] enums = theEnum.getEnumConstants();
		this.icons = new IIcon[enums.length];

		for(int i = 0; i < icons.length; i++) {
			Enum num = enums[i];
			this.icons[i] = reg.registerIcon(this.getIconString() + "_" + num.name().toLowerCase(Locale.US));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		Enum num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
		return super.getUnlocalizedName() + "_" + num.name().toLowerCase(Locale.US);
	}

	public static enum EnumZirnoxType {
		NATURAL_URANIUM_FUEL(250_000, 30),
		URANIUM_FUEL(200_000, 50),
		TH232(20_000, 0, true),
		THORIUM_FUEL(200_000, 40),
		MOX_FUEL(165_000, 75),
		PLUTONIUM_FUEL(175_000, 65),
		U233_FUEL(150_000, 100),
		U235_FUEL(165_000, 85),
		LES_FUEL(150_000, 150),
		LITHIUM(20_000, 0, true),
		ZFB_MOX(50_000, 35),
		PU_MIX(185_000, 60),
		PU241(180_000,120),
		AMRG(145_000, 165),
		AMF(155_000, 180),
		CMRG(175_500, 185),
		CMF(180_000, 200),
		CF251(750_000, 50),
		CF252(250_000, 75),
		BK247(75_000, 245),
		ES253(25_000, 375),
		LEAUS(1_000_000, 20),
		MEAUS(750_000, 40),
		HEAUS(400_000, 90),
		GRAVEL(4_000_000, 10, true);

		public final int maxLife;
		public final int heat;
		public final boolean breeding;
		
		private EnumZirnoxType(int life, int heat, boolean breeding) {
			this.maxLife = life;
			this.heat = heat;
			this.breeding = breeding;
		}
		
		private EnumZirnoxType(int life, int heat) {
			this.maxLife = life;
			this.heat = heat;
			this.breeding = false;
		}
	}
}
