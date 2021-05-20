package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.weapon.ItemGunEnergyBase;
import com.hbm.lib.Library;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemBatteryGun extends ItemBattery implements IBatteryItem
{
	private EnumRarity rarity = EnumRarity.common;
	private boolean singleUse;
	public ItemBatteryGun(long maxCharge, long chargeRate, boolean singleUseIn)
	{
		super(maxCharge, chargeRate, 0);
		this.singleUse = singleUseIn;
		if (!singleUseIn)
			setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(itemstack, player, list, bool);
		list.add(String.format("%sOnly charges energy guns! Charges: %sHE", EnumChatFormatting.YELLOW, Library.getShortNumber(getMaxCharge())));
		if (singleUse)
		{
			list.add(EnumChatFormatting.YELLOW + "Single use!");
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		for (int i = 0; i < 9; i++)
		{
			if (player.inventory.mainInventory[i] == null)
				continue;
			
			ItemStack gun = player.inventory.mainInventory[i];
			if (gun != null && gun.getItem() instanceof ItemGunEnergyBase)
			{
				if (this.getCharge(stack) == 0)
					break;
				
				ItemGunEnergyBase energyGun = (ItemGunEnergyBase)gun.getItem();
				long maxCharge = energyGun.maxCharge;
				long currentCharge = ItemGunEnergyBase.getGunCharge(gun);
				
				if (this.getCharge(stack) + currentCharge < maxCharge || this.getCharge(stack) + currentCharge == maxCharge)
				{
					ItemGunEnergyBase.setGunCharge(stack, currentCharge + this.getCharge(stack));
					if (singleUse)
						stack.stackSize--;
					else
						this.setCharge(stack, 0);
		            world.playSoundAtEntity(player, "hbm:item.battery", 1.0F, 1.0F);

				}
				else if (this.getCharge(stack) + currentCharge > maxCharge)
				{
					long chargeDiff = maxCharge - currentCharge;
					ItemGunEnergyBase.setGunCharge(stack, maxCharge);
					if (singleUse)
						stack.stackSize--;
					else
						this.setCharge(stack, chargeDiff);
		            world.playSoundAtEntity(player, "hbm:item.battery", 1.0F, 1.0F);

				}
			}
		}
		return stack;
	}
	
	public ItemBatteryGun setRarity(EnumRarity rarityIn)
	{
		rarity = rarityIn;
		return this;
	}
	
	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return rarity;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(getEmptyBattery(item));
		list.add(getFullBattery(item));
	}
}
