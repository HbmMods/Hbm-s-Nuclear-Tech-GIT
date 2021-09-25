package com.hbm.items.machine;

import java.util.List;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.IFastChargable;
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
import net.minecraft.world.World;

public class ItemBatteryFast extends ItemBattery implements IBatteryItem
{
	private boolean singleUse;
	/**
	 * Constructor for gun batteries
	 * @param maxCharge - Maximum charge of the battery, stock charge of single use batteries
	 * @param chargeRate - Same as regular batteries, single use should be set to 0
	 * @param singleUseIn - Is it single use?
	 */
	public ItemBatteryFast(long maxCharge, long chargeRate, boolean singleUseIn)
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
		boolean isSingleUse = ((ItemBatteryFast)itemstack.getItem()).singleUse;
		
		String[] desc = I18nUtil.resolveKeyArray("item.battery_gun.desc.1", Library.getShortNumber(charge));
		list.add(desc[0]);
		list.add(desc[1]);
		if (isSingleUse)
			list.add(I18nUtil.resolveKey("item.battery_gun.desc.2"));
		else
		{
			list.add(I18nUtil.resolveKey(HbmCollection.chargePerc, Library.getShortNumber((charge * 100) / getMaxCharge())));
			list.add(String.format("(%s/%sHE)", Library.getShortNumber(charge), Library.getShortNumber(getMaxCharge())));
			list.add(I18nUtil.resolveKey(HbmCollection.chargeRate, Library.getShortNumber(getChargeRate())));
		}
	}
	
	public ItemStack consumeItem(ItemStack stack, EntityPlayer player)
	{
		stack.stackSize--;
		if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.battery_gun_raw)))
			player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.battery_gun_raw), true);
		return stack;
	}
	
	@Beta
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		boolean consumedCharge = false;
		for (int i = 0; i < 9; i++)
		{
			if (player.inventory.mainInventory[i] == null)
				continue;
			
			ItemStack charging = player.inventory.mainInventory[i];
			if (charging.getItem() instanceof IFastChargable)
				consumedCharge = IFastChargable.fastCharge(charging, stack);
			else
				continue;
		}
		
		if (consumedCharge)
		{
            world.playSoundAtEntity(player, "hbm:item.battery", 1.0F, 1.0F);
            if (singleUse)
            	consumeItem(stack, player);
		}
		
		return stack;
	}
	
	public boolean getIsSingleUse()
	{
		return singleUse;
	}
	
	public ItemBatteryFast setRarity(EnumRarity rarityIn)
	{
		return (ItemBatteryFast)super.setRarity(rarityIn);
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
