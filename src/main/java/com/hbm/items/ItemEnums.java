package com.hbm.items;

import com.hbm.hazard.HazardData;
import com.hbm.util.BobMathUtil;

import net.minecraft.item.EnumRarity;

/**
 * I'm not at all sure if bunching together all these enums in one long class is a good idea
 * but I don't want to make a new class for every multi item to hold the enum
 * since that's entirely against the point of ItemEnumMulti to begin with.
 * @author hbm
 */
public class ItemEnums {
	
	public interface IItemEnum
	{
		default String getBasicTooltip()
		{
			return new String();
		}
		default String getOreDict()
		{
			return new String();
		}
		default boolean getHasEffect()
		{
			return false;
		}
		default EnumRarity getRarity()
		{
			return EnumRarity.common;
		}
		default HazardData getHazards()
		{
			return new HazardData();
		}
		default String overrideTexture()
		{
			return new String();
		}
	}
	
	public enum Orichalcum implements IItemEnum
	{
		INGOT,
		GEM,
		INGOT_SMALL,
		INGOT_SMALL_IRR,
		POWDER,
		PLATE,
		FRAGMENT,
		INGOT_FINISHED,
		PLATE_ARMOR;
		@Override
		public boolean getHasEffect()
		{
			return this == INGOT_FINISHED || this == PLATE_ARMOR;
		}
	}
	
	public enum StorageRaws
	{
		M_TAPE,
		M_REEL,
		M_DISC,
		M_PLATTER,
		O_DISC;
	}
	
	public enum AmmoAssembly
	{
		ACP_45,
		NATO_556,
		NATO_762,
		AE_50,
		BMG_50,
		MAG_357_DESH,
		MAG_357_GOLD,
		MAG_357_IRON,
		MAG_357_LEAD,
		MAG_357_NUCLEAR,
		MAG_357_NIGHTMARE,
		R_5MM,
		LUNATIC,
		MAG_44,
		M_NUKE,
		P_9MM,
		LR_22;
	}
	
	public enum EnumSctintillator implements IItemEnum
	{
		CS_F,
		CS_BR,
		CS_I,
		LA_CL,
		LA_BR,
		LA_CL_CE,
		LA_BR_CE,
		PB_W,
		BI,
	}
	
	public enum EnumCPU implements IItemEnum
	{
		ALUMINIUM(BobMathUtil.KB * 500),
		COPPER(BobMathUtil.GB * 4),
		ADVANCED_ALLOY(EnumRarity.uncommon, BobMathUtil.GB * 8),
		PAA(EnumRarity.uncommon, BobMathUtil.GB * 32),
		GOLD(EnumRarity.uncommon, BobMathUtil.GB * 128),
		SCHRABIDIUM(EnumRarity.rare, BobMathUtil.GB * 512),
		BISMUTH(EnumRarity.rare, BobMathUtil.TB * 2),
		NEURAL(EnumRarity.epic, BobMathUtil.TB * 24);
		EnumRarity rarity = EnumRarity.common;
		public final long power;
		private EnumCPU(long power)
		{
			this.power = power;
		}
		private EnumCPU(EnumRarity rarity, long power)
		{
			this.rarity = rarity;
			this.power = power;
		}
		@Override
		public EnumRarity getRarity()
		{
			return rarity;
		}
		@Override
		public boolean getHasEffect()
		{
			return this == NEURAL;
		}
	}
	
	public enum EnumEinsteinium implements IItemEnum
	{
		INGOT_ES254,
		NUGGET_ES254,
		BILLET_ES254;
	}
	
	public enum EnumCalifornium implements IItemEnum
	{
		INGOT_CF249,
		INGOT_CF251,
		INGOT_CF252,
		BILLET_CF249,
		BILLET_CF251,
		BILLET_CF252,
		NUGGET_CF249,
		NUGGET_CF251,
		NUGGET_CF252;
	}
	
	public enum EnumBerkelium implements IItemEnum
	{
		INGOT_BK247,
		INGOT_BK248,
		INGOT_BK249,
		NUGGET_BK247,
		NUGGET_BK248,
		NUGGET_BK249,
		BILLET_BK247,
		BILLET_BK248,
		BILLET_BK249;
		@Override
		public boolean getHasEffect()
		{
			return this.toString().contains("249");
		}
	}
	
	public enum EnumCurium implements IItemEnum
	{
		INGOT_CM243,
		INGOT_CM244,
		INGOT_CM245,
		INGOT_CM246,
		INGOT_CM247,
		INGOT_CM248,
		INGOT_CM250,
		INGOT_CURIUM_MIX_FAST,
		INGOT_CURIUM_FUEL_FAST,
		INGOT_CURIUM_MIX_SLOW,
		INGOT_CURIUM_FUEL_SLOW,
		NUGGET_CM243,
		NUGGET_CM244,
		NUGGET_CM245,
		NUGGET_CM246,
		NUGGET_CM247,
		NUGGET_CM248,
		NUGGET_CM250,
		NUGGET_CURIUM_MIX_FAST,
		NUGGET_CURIUM_FUEL_FAST,
		NUGGET_CURIUM_MIX_SLOW,
		NUGGET_CURIUM_FUEL_SLOW,
		BILLET_CM243,
		BILLET_CM244,
		BILLET_CM245,
		BILLET_CM246,
		BILLET_CM247,
		BILLET_CM248,
		BILLET_CM250,
		BILLET_CURIUM_MIX_FAST,
		BILLET_CURIUM_FUEL_FAST,
		BILLET_CURIUM_MIX_SLOW,
		BILLET_CURIUM_FUEL_SLOW;
		@Override
		public String overrideTexture()
		{
			final String prefix = toString().split("_")[0].toLowerCase();
			final String suffix;
			if (toString().contains("SLOW"))
				suffix = "slow";
			else if (toString().contains("FAST"))
				suffix = "fast";
			else
			{
				final char c = toString().charAt(toString().length() - 1);
				suffix = Character.isDigit(c) ? (Character.getNumericValue(c) % 2 == 0 ? "fast" : "slow") : "fast";
			}
			return String.format("%s_curium_%s", prefix, suffix);
		}
	}
	
	public enum EnumNickel implements IItemEnum
	{
		INGOT_NICKEL,
		PLATE_NICKEL,
		POWDER_NICKEL;
	}
	
	public enum EnumThA implements IItemEnum
	{
		INGOT,
		NUGGET,
		BILLET;
		@Override
		public boolean getHasEffect()
		{
			return true;
		}
	}
	
	public enum EnumProtactinium implements IItemEnum
	{
		INGOT_PA233,
		NUGGET_PA233,
		BILLET_PA233;
		@Override
		public boolean getHasEffect()
		{
			return true;
		}
	}
	
	public enum EnumRAM implements IItemEnum
	{
		REDSTONE(BobMathUtil.KB * 512),
		SILICON(BobMathUtil.MB * 16),
		NICKEL(BobMathUtil.MB * 256),
		LAPIS(BobMathUtil.GB * 2),
		GOLD(BobMathUtil.GB * 16),
		DIAMOND(EnumRarity.rare, BobMathUtil.GB * 128),
		CAESIUM(EnumRarity.epic, BobMathUtil.TB),
		SPARK(EnumRarity.epic, BobMathUtil.TB * 16);
		private EnumRarity rarity = EnumRarity.common;
		public final long capacity;
		private EnumRAM(long capacity)
		{
			this.capacity = capacity;
		}
		private EnumRAM(EnumRarity rarityIn, long capacity)
		{
			rarity = rarityIn;
			this.capacity = capacity;
		}
		@Override
		public EnumRarity getRarity()
		{
			return rarity;
		}
	}
	
	public enum EnumWaferType implements IItemEnum
	{
		SILICON,
		NICKEL,
		LAPIS,
		GOLD,
		DIAMOND(EnumRarity.rare),
		CAESIUM(EnumRarity.epic),
		SPARK(EnumRarity.epic);
		private EnumRarity rarity = EnumRarity.common;
		private EnumWaferType()
		{
		}
		private EnumWaferType(EnumRarity rarityIn)
		{
			rarity = rarityIn;
		}
		@Override
		public String toString()
		{
			return "WAFER_".concat(super.toString());
		}
		@Override
		public EnumRarity getRarity()
		{
			return rarity;
		}
	}

	public enum EnumSilicon
	{
		NUGGET,
		INGOT,
		LUMP;
	}
	
	public static enum EnumLegendaryType {
		TIER1,
		TIER2,
		TIER3
	}
	
	public static enum EnumCokeType {
		COAL,
		LIGNITE,
		PETROLEUM
	}

	public static enum EnumTarType {
		CRUDE,
		CRACK,
		COAL
	}
	
	public static enum EnumPlantType {
		TOBACCO,
		ROPE
	}
}