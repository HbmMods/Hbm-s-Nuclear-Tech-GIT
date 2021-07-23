package com.hbm.items.special;

import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WatzFuel extends ItemHazard {

	public int lifeTime;
	public int power;
	public float powerMultiplier;
	public int heat;
	public float heatMultiplier;
	public float decayMultiplier;
	private ItemStack decayItem = new ItemStack(ModItems.pellet_lead);
	/**
	 * Constructor for a new Watz fuel pellet
	 * @param lifeTime
	 * @param power
	 * @param powerMultiplier
	 * @param heat
	 * @param heatMultiplier
	 * @param decayMultiplier
	 */
	
	public WatzFuel(float radiation, boolean blinding, int lifeTime, int power, float powerMultiplier, int heat, float heatMultiplier, float decayMultiplier) {
		super(radiation, false, blinding);
		this.lifeTime = lifeTime * 100;
		this.power = power/10;
		this.powerMultiplier = powerMultiplier;
		this.heat = heat;
		this.heatMultiplier = heatMultiplier;
		this.decayMultiplier = decayMultiplier;
		this.setMaxDamage(100);
		this.canRepair = false;
		setMaxStackSize(1);
	}
	/**
	 * What the pellet will decay into, Lead pellet by default
	 * @param decayItem - The item it decays into
	 * @param amount - Size of the stack, if the decay item is not another Watz pellet
	 * @return - Itself
	 */
	public WatzFuel setDecayItem(@Nonnull Item decayItem, @Nonnegative int amount)
	{
		if (decayItem instanceof WatzFuel)
			this.decayItem = new ItemStack(decayItem);
		else
			this.decayItem = new ItemStack(decayItem, amount < 1 ? 1 : amount);
		return this;
	}
	
	public ItemStack getDecayItem()
	{
		return decayItem.copy();
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		try
		{
//			list.add("Max age:          " + this.lifeTime/100 + " ticks");
			list.add(I18nUtil.resolveKey("desc.watz.pelAge", this.lifeTime/100));
//			list.add("Power per tick:  " + (power) + "HE");
			list.add(I18nUtil.resolveKey("desc.watz.pelPower", power));
//			list.add("Power multiplier: " + (powerMultiplier >= 1 ? "+" : "") + (Math.round(powerMultiplier * 1000) * .10 - 100) + "%");
			list.add(I18nUtil.resolveKey("desc.watz.pelPowerMulti", (powerMultiplier >= 1 ? "+" : "") + (Math.round(powerMultiplier * 1000) * .10 - 100)));
//			list.add("Heat provided:   " + heat + " heat");
			list.add(I18nUtil.resolveKey("desc.watz.pelHeat", heat));
//			list.add("Heat multiplier:   " + (heatMultiplier >= 1 ? "+" : "") + (Math.round(heatMultiplier * 1000) * .10 - 100) + "%");
			list.add(I18nUtil.resolveKey("desc.watz.pelHeatMulti", (heatMultiplier >= 1 ? "+" : "") + (Math.round(heatMultiplier * 1000) * .10 - 100)));
//			list.add("Decay multiplier: " + (decayMultiplier >= 1 ? "+" : "") + (Math.round(decayMultiplier * 1000) * .10 - 100) + "%");
			list.add(I18nUtil.resolveKey("desc.watz.pelDecayMulti", (decayMultiplier >= 1 ? "+" : "") + (Math.round(decayMultiplier * 1000) * .10 - 100)));
//			list.add("Decays to:        " + I18nUtil.resolveKey(getDecayItem().getUnlocalizedName() + ".name"));
			list.add(I18nUtil.resolveKey("desc.watz.pelDecay", getDecayItem().getDisplayName() + " x" + getDecayItem().stackSize));
			
			super.addInformation(itemstack, player, list, bool);
		}
		catch(Exception e)
		{
		}
	}
	
	public static void setLifeTime(ItemStack stack, int time) {
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.stackTagCompound.setInteger("life", time);
	}
	
	public static void updateDamage(ItemStack stack) {
		
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		
		stack.setItemDamage((int)((double)getLifeTime(stack) / (double)((WatzFuel)stack.getItem()).lifeTime * 100D));
	}
	
	public static int getLifeTime(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("life");
	}
}
