package com.hbm.items.armor;

import java.util.List;

import com.hbm.lib.HbmCollection;
import com.hbm.render.model.ModelGoggles;
import com.hbm.util.I18nUtil;

import api.hbm.energy.IBatteryItem;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemModNightVision extends ItemArmorMod implements IBatteryItem
{
	public static final long maxCharge = 1000000;
	public final long rate = 10;
	public ItemModNightVision()
	{
		super(0, true, false, false, false);
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add(I18nUtil.resolveKey("desc.item.battery.charge", getCharge(itemstack), getMaxCharge()));
		super.addInformation(itemstack, player, list, bool);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 10));
		dischargeBattery(itemStack, rate);
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		return armorType == 0;
	}
	
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
	{
		return new ModelGoggles();
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return "hbm:textures/models/Goggles.png";
	}
	
	@Override
	public void chargeBattery(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemModNightVision)
		{
			if (stack.hasTagCompound())
				stack.stackTagCompound.setLong(getChargeTagName(), getCharge(stack) + i);
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong(getChargeTagName(), i);
			}
		}
	}

	@Override
	public void setCharge(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemModNightVision)
		{
			if (stack.hasTagCompound())
				stack.stackTagCompound.setLong(getChargeTagName(), i);
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong(getChargeTagName(), i);
			}
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemModNightVision)
		{
			if (stack.hasTagCompound())
				stack.stackTagCompound.setLong(getChargeTagName(), getCharge(stack) - i);
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong(getChargeTagName(), 0);
			}
			if (stack.stackTagCompound.getLong(getChargeTagName()) < 0)
				stack.stackTagCompound.setLong(getChargeTagName(), 0);
		}
	}

	@Override
	public long getCharge(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemModNightVision)
		{
			if (stack.hasTagCompound())
				return stack.stackTagCompound.getLong(getChargeTagName());
			else
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setLong(getChargeTagName(), 0);
				return 0;
			}
		}
		return 0;
	}

	@Override
	public long getMaxCharge()
	{
		return maxCharge;
	}

	@Override
	public long getChargeRate()
	{
		return rate * 1000;
	}

	@Override
	public long getDischargeRate()
	{
		return 0;
	}

}
