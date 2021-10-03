package com.hbm.items.armor;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableSet;
import com.hbm.calc.EasyLocation;
import com.hbm.interfaces.Untested;

import api.hbm.energy.IBatteryItem;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
@Beta
@Untested
public class ItemModMagBoots extends ItemArmorMod implements IBatteryItem
{
	public static final long maxCharge = 5000000;
	public static final short rate = 200;
	private static final ImmutableSet<String> validMetals = ImmutableSet.of("Iron", "Steel", "CMBSteel", "Schrabidium", "Solinium", "Cobalt", "Nickel", "MagnetizedTungsten", "StainlessSteel", "Neodymium");
	private static final ImmutableSet<Material> validMaterials = ImmutableSet.of(Material.anvil, Material.iron);
	public ItemModMagBoots()
	{
		super(3, false, false, false, true);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
	{
		if (!player.capabilities.isCreativeMode)
			dischargeBattery(itemStack, rate);
		EasyLocation pLoc = new EasyLocation(player).modifyCoord(0, -1, 0);
		Block b = pLoc.getBlockAtCoord();
		boolean flag = false;
		for (String metal : validMetals)
			if (b.getUnlocalizedName().toLowerCase().contains(metal.toLowerCase()))
				flag = true;
		if (flag)
			player.motionY -= 5;
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity)
	{
		return armorType == 3;
	}
	
	@Override
	public void chargeBattery(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemModMagBoots)
		{
			if (!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong(getChargeTagName(), getCharge(stack) + i);
		}
	}

	@Override
	public void setCharge(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemModMagBoots)
		{
			if (!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong(getChargeTagName(), i);
		}
	}

	@Override
	public void dischargeBattery(ItemStack stack, long i)
	{
		if (stack.getItem() instanceof ItemModMagBoots)
		{
			if (!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setLong(getChargeTagName(), getCharge(stack) - i);
			if (getCharge(stack) < 0)
				setCharge(stack, 0);
		}
	}

	@Override
	public long getCharge(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemModMagBoots)
		{
			if (!stack.hasTagCompound())
				stack.stackTagCompound = new NBTTagCompound();
			return stack.stackTagCompound.getLong(getChargeTagName());
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
		return rate;
	}

	@Override
	public long getDischargeRate()
	{
		return 0;
	}

}
