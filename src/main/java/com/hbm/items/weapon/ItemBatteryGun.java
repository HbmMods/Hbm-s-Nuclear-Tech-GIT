package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBattery;
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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemBatteryGun extends ItemBattery implements IBatteryItem
{
	private boolean singleUse;
	/**
	 * Constructor for gun batteries
	 * @param maxCharge - Maximum charge of the battery, stock charge of single use batteries
	 * @param chargeRate - Same as regular batteries, single use should be set to 0
	 * @param singleUseIn - Is it single use?
	 */
	public ItemBatteryGun(long maxCharge, long chargeRate, boolean singleUseIn)
	{
		super(maxCharge, chargeRate, 0);
		this.singleUse = singleUseIn;
		setMaxStackSize(singleUseIn ? 4 : 1);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		//super.addInformation(itemstack, player, list, bool);
		long charge = getMaxCharge();
		if (itemstack.hasTagCompound())
			charge = getCharge(itemstack);
		boolean isSingleUse = ((ItemBatteryGun)itemstack.getItem()).singleUse;
		
		String[] desc = I18nUtil.resolveKeyArray("item.battery_gun.desc.1", Library.getShortNumber(charge));
		list.add(desc[0]);
		list.add(desc[1]);
		if (isSingleUse)
			list.add(I18nUtil.resolveKey("item.battery_gun.desc.2"));
		else
		{
			list.add("Charge: " + Library.getShortNumber((charge * 100) / getMaxCharge()) + "%");
			list.add(String.format("(%s/%sHE)", Library.getShortNumber(charge), Library.getShortNumber(getMaxCharge())));
			list.add(String.format("Charge rate: %sHE/t", Library.getShortNumber(getChargeRate())));
		}
	}
	
	private ItemStack consumeItem(ItemStack stack, EntityPlayer player)
	{
		stack.stackSize--;
		player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_gun_raw));
		return stack;
	}
	
	// Could be worse, probably
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		boolean consumedCharge = false;
		if (this.getCharge(stack) == 0)
			return stack;

		for (int i = 0; i < 9; i++)
		{
			if (player.inventory.mainInventory[i] == null)
				continue;
			
			ItemStack gun = player.inventory.mainInventory[i];
			if (gun != null && gun.getItem() instanceof ItemGunEnergyBase)
			{
				
				ItemGunEnergyBase energyGun = (ItemGunEnergyBase)gun.getItem();
				long maxCharge = energyGun.getMaxCharge();
				long currentCharge = ItemGunEnergyBase.getGunCharge(gun);
				long battCharge = getCharge(stack);
				if (maxCharge == currentCharge)
					continue;
				
				if (singleUse)
				{
					if (battCharge + currentCharge < maxCharge)
						energyGun.chargeBattery(gun, currentCharge + battCharge);
					else
						energyGun.chargeBattery(stack, maxCharge);
					
					stack = consumeItem(stack, player);
					consumedCharge = true;
				}
				else
				{
					if (battCharge + currentCharge < maxCharge)
					{
						//ItemGunEnergyBase.setGunCharge(stack, currentCharge + this.getCharge(stack));
						energyGun.chargeBattery(gun, currentCharge + battCharge);
						consumedCharge = true;
						setCharge(stack, 0);
					}
					else if (battCharge + currentCharge >= maxCharge)
					{
						long chargeDiff = maxCharge - currentCharge;
						//ItemGunEnergyBase.setGunCharge(stack, maxCharge);
						energyGun.chargeBattery(gun, maxCharge);
						consumedCharge = true;
						if (chargeDiff >= battCharge)
							setCharge(stack, 0);
						else
							setCharge(stack, battCharge - chargeDiff);
					}
				}
				if (consumedCharge)
					break;
			}
		}
		if (consumedCharge)
            world.playSoundAtEntity(player, "hbm:item.battery", 1.0F, 1.0F);

		return stack;
	}
	
	public ItemBatteryGun setRarity(EnumRarity rarityIn)
	{
		return (ItemBatteryGun)super.setRarity(rarityIn);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		if (!singleUse)
			list.add(getEmptyBattery(item));
		list.add(getFullBattery(item));
	}
	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return !singleUse;
	}
}
