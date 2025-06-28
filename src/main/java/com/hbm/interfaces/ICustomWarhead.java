package com.hbm.interfaces;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Arrays;

import org.apache.logging.log4j.Level;

import com.google.common.annotations.Beta;
import com.hbm.hazard.HazardRegistry;
import com.hbm.main.MainRegistry;
import com.hbm.util.BobMathUtil;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
/**
 * Interface for customizable warheads or other explosive devices
 * @author UFFR
 *
 */
@Beta
@Spaghetti("AAAAAAAA")
public interface ICustomWarhead
{
	public static enum EnumCustomWarhead
	{
		AMAT,
		BF,
		BIO,
		CHEM,
		FUSION,
		GRAV,
		HE,
		NUCLEAR,
		TX,
		SCHRAB,
		ZPE;
		public String getLoc()
		{
			return I18nUtil.resolveKey("warhead.".concat(toString()));
		}
		
		public enum EnumChemicalType
		{
			ACID,
			CHLORINE,
			NERVE,
			TOX;
			public String getLoc()
			{
				return I18nUtil.resolveKey("warhead.CHEM.".concat(toString()));
			}
		}
		
		public enum EnumBioType
		{
			ANTHRAX,
			MKU;
			public String getLoc()
			{
				return I18nUtil.resolveKey("warhead.BIO.".concat(toString()));
			}
		}
	}
	public static enum EnumCustomWarheadTrait
	{
		CLEAN,
		CLEANISH,
		DIRTY,
		RAD,
		SALT;
		public String getLoc()
		{
			return I18nUtil.resolveKey("warheadTrait.".concat(toString()));
		}
	}
	public static enum EnumWeaponType
	{
		DENIAL,
		STRATEGIC,
		TACTICAL,
		WMD;
		public String getLoc()
		{
			return I18nUtil.resolveKey("warheadType.".concat(toString()));
		}
	}
	public static final String KEY_ANTHRAX = "warheadFuel.ANTHRAX";
	public static final String KEY_MKU = "warheadFuel.MKU";
	public static final String KEY_CAUSTIC = "warheadFuel.ACID";
	public static final String KEY_NERVE = "warheadFuel.NERVE";
	public static final String KEY_TOX = "warheadFuel.TOX";
	
	public static final String NBT_GROUP = "NTM_NUKE_INFO";
	public static final String NBT_YIELD = "YIELD";
	public static final String NBT_ALTITUDE = "ALTITUDE";
	public static final String NBT_MASS = "MASS";
	public static final String NBT_SPECIAL = "SPECIAL_FIELD";
	public static final String NBT_WARHEAD = "WARHEAD";
	public static final String NBT_TYPE = "WARHEAD_TYPE";
	public static final String NBT_TRAIT = "WARHEAD_TRAIT";
	public static final DecimalFormat df = new DecimalFormat("#.00");
	
	public static EnumChatFormatting getColorFromWarhead(EnumCustomWarhead warhead)
	{
		switch (warhead)
		{
		case AMAT:
			return EnumChatFormatting.DARK_RED;
		case BF:
			return EnumChatFormatting.GREEN;
		case BIO:
			return EnumChatFormatting.GOLD;
		case CHEM:
			return EnumChatFormatting.YELLOW;
		case FUSION:
			return EnumChatFormatting.BLUE;
		case GRAV:
			return EnumChatFormatting.DARK_GRAY;
		case HE:
			return EnumChatFormatting.RED;
		case NUCLEAR:
			return EnumChatFormatting.DARK_GREEN;
		case SCHRAB:
			return EnumChatFormatting.AQUA;
		case TX:
			return EnumChatFormatting.DARK_PURPLE;
		case ZPE:
			return (System.currentTimeMillis() % 1000 < 500 ? EnumChatFormatting.DARK_AQUA : EnumChatFormatting.LIGHT_PURPLE);
		default:
			return EnumChatFormatting.WHITE;
		}
	}
	
	public default float getYield()
	{
		return 0.0F;
	}
	public default EnumCustomWarhead getWarheadType(NBTTagCompound data)
	{
		return EnumCustomWarhead.valueOf(data.getString(NBT_WARHEAD));
	}
	public default EnumWeaponType getWeaponType(NBTTagCompound data)
	{
		return EnumWeaponType.valueOf(data.getString(NBT_TYPE));
	}
	public default EnumCustomWarheadTrait getWeaponTrait(NBTTagCompound data)
	{
		return EnumCustomWarheadTrait.valueOf(data.getString(NBT_TRAIT));
	}
	public ItemStack constructNew();
	public ICustomWarhead getInstance();
	
	public default Item getItem()
	{
		return (Item) this;
	}
	
	public static ItemStack addData(NBTTagCompound data, Item item)
	{
		ItemStack stackOut = new ItemStack(item);
		
		stackOut.stackTagCompound = new NBTTagCompound();
		stackOut.stackTagCompound.setTag(NBT_GROUP, data);
		
		return stackOut.copy();
	}
	
	public default NBTTagCompound getWarheadData(ItemStack stack)
	{
		return stack.getTagCompound().getCompoundTag(NBT_GROUP);
	}
	
	public default ItemStack addFuel(ItemStack stack, Enum<?> fuel, float amount)
	{
		if (stack != null && stack.getItem() instanceof ICustomWarhead)
		{
			NBTTagCompound data = getWarheadData(stack);
			data.setFloat(fuel.toString(), amount);
			data.setFloat(NBT_MASS, data.getFloat(NBT_MASS) + amount);
		}
		return stack;
	}
	
	public default ItemStack addData(ItemStack stack, String key, String value)
	{
		if (stack != null && stack.getItem() instanceof ICustomWarhead)
			getWarheadData(stack).setString(key, value);
		
		return stack;
	}
	
	public default void addCompositionalInfo(NBTTagCompound data, List<String> tooltip, List<Enum<?>> combinedFuels)
	{
		for (Enum<?> f : combinedFuels)
			if (data.getFloat(f.toString()) > 0)
				tooltip.add(String.format(Locale.US, "%s: %skg (%s)", I18nUtil.resolveKey("warheadFuel.".concat(f.toString())), df.format(data.getFloat(f.toString())), BobMathUtil.toPercentage(data.getFloat(f.toString()), data.getFloat(NBT_MASS))));
	}
	
	public default void addTooltip(ItemStack stack, List<String> tooltip)
	{
//		tooltip.clear();
		try {
		NBTTagCompound data = getWarheadData(stack);
		
		final ArrayList<Enum<?>> combinedFuels = new ArrayList<>();
		combinedFuels.addAll(Arrays.asList(FissileFuel.values()));
		combinedFuels.addAll(Arrays.asList(FusionFuel.values()));
		combinedFuels.addAll(Arrays.asList(SaltedFuel.values()));
		combinedFuels.addAll(Arrays.asList(EnumCustomWarhead.values()));
		switch (getWarheadType(data))
		{
		case NUCLEAR:
		case TX:
		case HE:
			tooltip.add("Composition:");
			addCompositionalInfo(data, tooltip, combinedFuels);
			break;
		default:
			break;
		}
		final EnumCustomWarhead warhead = getWarheadType(data);
		tooltip.add(data.getFloat(NBT_MASS) + "kg total");
		tooltip.add("");
		switch (warhead)
		{
		case CHEM:
		case BIO:
			tooltip.add("Type: " + getColorFromWarhead(warhead) + I18nUtil.resolveKey("warhead.".concat(warhead.toString()), I18nUtil.resolveKey(data.getString(NBT_SPECIAL))));
			break;
		default:
			tooltip.add("Type: " + getColorFromWarhead(warhead) + warhead.getLoc());
			break;
		}
		tooltip.add("Function: " + getWeaponType(data).getLoc());
		switch (warhead)
		{
		case AMAT:
		case BF:
		case FUSION:
		case GRAV:
		case HE:
		case NUCLEAR:
		case TX:
			tooltip.add("Yield: " + BobMathUtil.getShortNumber(data.getInteger(NBT_YIELD)) + "T");
			break;
		case BIO:
		case CHEM:
		case SCHRAB:
			tooltip.add("Radius: " + BobMathUtil.getShortNumber(data.getInteger(NBT_YIELD)) + "M");
			break;
		default:
			break;
		}
		tooltip.add("Trait: " + getWeaponTrait(data).getLoc());
		}
		catch (Exception e)
		{
			MainRegistry.logger.catching(Level.ERROR, e);
		}
	}
	
	public enum FissileFuel
	{
		U233(15F, 197.5F, HazardRegistry.u233, 19.05F),
		U235(52F, 202.5F, HazardRegistry.u235, 19.05F),
		Np237(60F, 202.5F, HazardRegistry.np237, 20.45F),
		Pu239(10F, 207.1F, HazardRegistry.pu239, 19.86F),
		Pu241(12, 210F, HazardRegistry.pu241, 19.86F),
		Am241(66, 210F, HazardRegistry.am241, 13.67F),
		Am242m(11F, 212F, HazardRegistry.am242, 13.67F),
		Sa326(1F, 250F, HazardRegistry.sa326, 39.7F);
		public final float criticalMass;
		public final float energyReleased;
		public final float radioactivity;
		private final float mass;
		private FissileFuel(float criticalMass, float energyReleased, float radioactivity, float mass)
		{
			this.criticalMass = criticalMass;
			this.energyReleased = energyReleased;
			this.radioactivity = radioactivity;
			this.mass = mass;
		}
		public float getBlockMass()
		{
			return mass * 100;
		}
		public float getIngotMass()
		{
			return getBlockMass() / 9;
		}
		public float getNuggetMass()
		{
			return getIngotMass() / 9;
		}
		public String getLoc()
		{
			return I18nUtil.resolveKey("warheadFuel.".concat(toString()));
		}
	}
	public enum FusionFuel
	{
		DEUT,
		TRIT,
		Li,
		LiDEUT;
		public String getLoc()
		{
			return I18nUtil.resolveKey("warheadFuel".concat(toString()));
		}
	}
	public enum SaltedFuel
	{
		Co59(1.4902F * 0.75F, 5, HalfLifeType.MEDIUM, 8.86F),
		Co60(1.4902F, 5, HalfLifeType.MEDIUM, 8.86F),
		Sr90(0.546F, 28, HalfLifeType.MEDIUM, 2.64F),
		Cs137(1.1737F, 30, HalfLifeType.MEDIUM, 1.93F),
		Ta181(0.52F * 0.75F, 114, HalfLifeType.SHORT, 16.65F),
		Ta182(0.52F, 114, HalfLifeType.SHORT, 16.654F),
		Au197(1.3735F * 0.75F, 2, HalfLifeType.SHORT, 19.32F),
		Au198(1.3735F, 2, HalfLifeType.SHORT, 19.32F),
		Pu240(5.25575F, 65, HalfLifeType.LONG, 19.86F),
		Sa327(0.5F, 100, HalfLifeType.LONG, 39.7F);
		public final float decayEnergy;
		public final int halfLife;
		public final HalfLifeType type;
		private final float mass;
		SaltedFuel(float decayEnergy, int halfLife, HalfLifeType type, float mass)
		{
			this.decayEnergy = decayEnergy;
			this.halfLife = halfLife;
			this.type = type;
			this.mass = mass;
		}
		public enum HalfLifeType
		{
			/** Counted in days **/
			SHORT,
			/** Counted in years **/
			MEDIUM,
			/** Counted in hundreds of years **/
			LONG;
		}
		public float getBlockMass()
		{
			return mass * 100;
		}
		public float getIngotMass()
		{
			return getBlockMass() / 9;
		}
		public float getNuggetMass()
		{
			return getIngotMass() / 9;
		}
	}
	
	/*public static class CustomWarheadWrapper
	{
		public static final ICustomWarhead cWarhead = (ICustomWarhead) ModItems.custom_warhead;
		public static final ICustomWarhead cCore = (ICustomWarhead) ModItems.custom_core;
		public static final CustomWarheadWrapper gravimetricBase = new CustomWarheadWrapper(cWarhead).addFuel(EnumCustomWarhead.GRAV, 1000.0F).addData(NBT_TYPE, EnumWeaponType.TACTICAL).addData(NBT_WARHEAD, EnumCustomWarhead.GRAV).addData(NBT_TRAIT, EnumCustomWarheadTrait.CLEAN);
		public static final CustomWarheadWrapper pureFusionBase = new CustomWarheadWrapper(cWarhead).addFuel(FusionFuel.LiDEUT, 500).addData(NBT_TYPE, EnumWeaponType.TACTICAL).addData(NBT_WARHEAD, EnumCustomWarhead.FUSION).addData(NBT_TRAIT, EnumCustomWarheadTrait.CLEANISH).setStackData(8, 1);
		public static final CustomWarheadWrapper chemicalBase = new CustomWarheadWrapper(cWarhead).addFuel(EnumChemicalType.NERVE, 15).addData(NBT_TYPE, EnumWeaponType.WMD).addData(NBT_WARHEAD, EnumCustomWarhead.CHEM).addData(NBT_TRAIT, EnumCustomWarheadTrait.DIRTY).addData(NBT_SPECIAL, KEY_NERVE).setStackData(1, 2);
		public static final CustomWarheadWrapper biologicalBase = new CustomWarheadWrapper(cWarhead).addFuel(EnumBioType.ANTHRAX, 15).addData(NBT_TYPE, EnumWeaponType.WMD).addData(NBT_WARHEAD, EnumCustomWarhead.BIO).addData(NBT_TRAIT, EnumCustomWarheadTrait.DIRTY).addData(NBT_SPECIAL, KEY_ANTHRAX).setStackData(1, 3);
		public static final CustomWarheadWrapper saltedBase = new CustomWarheadWrapper(cWarhead).addFuel(FissileFuel.U235, 20).addFuel(FissileFuel.Pu239, 5).addFuel(FusionFuel.LiDEUT, 20).addFuel(SaltedFuel.Co59, 10).addData(NBT_TYPE, EnumWeaponType.DENIAL).addData(NBT_WARHEAD, EnumCustomWarhead.TX).addData(NBT_TRAIT, EnumCustomWarheadTrait.SALT).setStackData(1, 4);
		private ItemStack stack;
		private ICustomWarhead warhead;
		public CustomWarheadWrapper(ItemStack stack)
		{
			if (stack != null && stack.getItem() instanceof ICustomWarhead)
			{
				this.stack = ((ICustomWarhead) stack.getItem()).constructNew();
				warhead = (ICustomWarhead) stack.getItem();
			}
			else
				throw new IllegalArgumentException("Input stack item is not instance of " + ICustomWarhead.class.toString());
		}
		public CustomWarheadWrapper(ICustomWarhead warhead)
		{
			stack = warhead.constructNew();
			this.warhead = warhead;
		}
		public CustomWarheadWrapper(Item item)
		{
			if (!(item instanceof ICustomWarhead))
				throw new IllegalArgumentException("Input stack item is not instance of [ICustomWarhead]");
			stack = ((ICustomWarhead) item).constructNew();
			warhead = (ICustomWarhead) item;
		}
		public CustomWarheadWrapper addFuel(Enum<?> fuel, float amount)
		{
			warhead.addFuel(stack, fuel, amount);
			return this;
		}
		public CustomWarheadWrapper addData(String key, String value)
		{
			warhead.addData(stack, key, value);
			return this;
		}
		public CustomWarheadWrapper addData(String key, Enum<?> value)
		{
			return addData(key, value.toString());
		}
		public CustomWarheadWrapper setStackData(int stackSize, int meta)
		{
			stack.stackSize = stackSize <= 1 ? 1 : stackSize;
			stack.setItemDamage(meta <= 0 ? 0 : meta);
			return this;
		}
		public NBTTagCompound regurgitateData()
		{
			return (NBTTagCompound) warhead.getWarheadData(getStack()).copy();
		}
		public ICustomWarhead getInterface()
		{
			return warhead;
		}
		public ItemStack getStack()
		{
			return stack.copy();
		}
	}*/
}
