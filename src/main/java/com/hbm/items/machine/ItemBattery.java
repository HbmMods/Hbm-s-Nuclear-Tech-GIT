package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.HbmCollection;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBattery extends Item implements IBatteryItem {
	private EnumRarity rarity = EnumRarity.common;
	private long maxCharge;
	private long chargeRate;
	private long dischargeRate;

	public ItemBattery(long dura, long chargeRate, long dischargeRate) {
		this.maxCharge = dura;
		this.chargeRate = chargeRate;
		this.dischargeRate = dischargeRate;
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		long charge = maxCharge;
		if(itemstack.hasTagCompound())
			charge = getCharge(itemstack);

		if(itemstack.getItem() != ModItems.fusion_core && itemstack.getItem() != ModItems.factory_core_titanium && itemstack.getItem() != ModItems.factory_core_advanced && itemstack.getItem() != ModItems.energy_core && itemstack.getItem() != ModItems.dynosphere_desh && itemstack.getItem() != ModItems.dynosphere_schrabidium && itemstack.getItem() != ModItems.dynosphere_euphemium && itemstack.getItem() != ModItems.dynosphere_dineutronium) {
			list.add("Energy stored: " + Library.getShortNumber(charge) + "/" + Library.getShortNumber(maxCharge) + "HE");
		} else {
			String charge1 = Library.getShortNumber((charge * 100) / this.maxCharge);
			list.add(I18nUtil.resolveKey(HbmCollection.chargePerc, charge1));
			list.add("(" + Library.getShortNumber(charge) + "/" + Library.getShortNumber(maxCharge) + "HE)");
		}
//		list.add("Charge rate: " + Library.getShortNumber(chargeRate) + "HE/t");
//		list.add("Discharge rate: " + Library.getShortNumber(dischargeRate) + "HE/t");
		list.add(I18nUtil.resolveKey(HbmCollection.chargeRate, Library.getShortNumber(chargeRate)));
		list.add(I18nUtil.resolveKey(HbmCollection.dischargeRate, Library.getShortNumber(dischargeRate)));
	}
	
	public ItemBattery setRarity(EnumRarity customRarity)
	{
		rarity = customRarity;
		return this;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return rarity;
	}

	@Override
	public void chargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") + i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void setCharge(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", i);
			}
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				stack.stackTagCompound.setLong("charge", stack.stackTagCompound.getLong("charge") - i);
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", this.maxCharge - i);
			}
		}
	}

	@Override
	public long getCharge(ItemStack stack) {
		if(stack.getItem() instanceof ItemBattery) {
			if(stack.hasTagCompound()) {
				return stack.stackTagCompound.getLong("charge");
			} else {
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong("charge", ((ItemBattery) stack.getItem()).maxCharge);
				return stack.stackTagCompound.getLong("charge");
			}
		}

		return 0;
	}

	@Override
	public long getMaxCharge() {
		return maxCharge;
	}

	@Override
	public long getChargeRate() {
		return chargeRate;
	}

	@Override
	public long getDischargeRate() {
		return dischargeRate;
	}

	public static ItemStack getEmptyBattery(Item item) {

		if(item instanceof ItemBattery) {
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", 0);
			return stack.copy();
		}

		return null;
	}

	public static ItemStack getFullBattery(Item item) {

		if(item instanceof ItemBattery) {
			ItemStack stack = new ItemStack(item);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong("charge", ((ItemBattery) item).getMaxCharge());
			return stack.copy();
		}

		return new ItemStack(item);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double) getCharge(stack) / (double) getMaxCharge();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {

		if(this.chargeRate > 0) {
			list.add(getEmptyBattery(item));
		}
		
		if(this.dischargeRate > 0) {
			list.add(getFullBattery(item));
		}
	}
}
