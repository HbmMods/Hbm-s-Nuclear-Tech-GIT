package com.hbm.items;

import com.hbm.hazard.HazardData;

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

	public static enum EnumLegendaryType {
		TIER1,
		TIER2,
		TIER3
	}

	public static enum EnumPlantType {
		TOBACCO,
		ROPE
	}
}
