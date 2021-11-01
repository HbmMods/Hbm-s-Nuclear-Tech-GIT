package com.hbm.items.special;

import java.util.List;

import com.hbm.inventory.BreederRecipes;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemRadioactive extends ItemCustomLore {
	
	float radiation;
	boolean fire;
	boolean blinding;
	
	//PO210		           138d		α	25.00Rad/s	Spicy
	//TH232		14,000,000,000a		α	00.10Rad/s
	//U233		       160,000a		α	05.00Rad/s
	//U235		   700,000,000a		α	01.00Rad/s
	//U238		 4,500,000,000a		α	00.25Rad/s
	//NP237		     2,100,000a		α	02.50Rad/s
	//PU238		            88a		α	10.00Rad/s	Spicy
	//PU239		        24,000a		α	05.00Rad/s
	//PU240		         6,600a		α	07.50Rad/s

	public static final float po210 = 25.0F;
	public static final float th232 = 0.1F;
	public static final float thf = 1.75F;
	public static final float u = 0.35F;
	public static final float u233 = 5.0F;
	public static final float u235 = 1.0F;
	public static final float u238 = 0.25F;
	public static final float uf = 0.5F;
	public static final float np237 = 2.5F;
	public static final float pu = 7.5F;
	public static final float purg = 6.25F;
	public static final float pu238 = 10.0F;
	public static final float pu239 = 5.0F;
	public static final float pu240 = 7.5F;
	public static final float puf = 4.25F;
	public static final float mox = 2.5F;
	public static final float sa326 = 15.0F;
	public static final float sa327 = 17.5F;
	public static final float saf = 5.85F;

	public static final float nugget = 0.1F;
	public static final float ingot = 1.0F;
	public static final float block = 10.0F;
	public static final float billet = 0.5F;
	public static final float rod = 0.5F;
	public static final float rod_dual = 1.0F;
	public static final float rod_quad = 2.0F;
	
	public ItemRadioactive(float radiation) {
		this.radiation = radiation;
	}
	
	public ItemRadioactive(float radiation, boolean fire) {
		this.radiation = radiation;
		this.fire = fire;
	}
	
	public ItemRadioactive(float radiation, boolean fire, boolean blinding) {
		this.radiation = radiation;
		this.fire = fire;
		this.blinding = blinding;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {

		doRadiationDamage(entity, stack.stackSize);
	}

	public void doRadiationDamage(Entity entity, float mod) {

		if (entity instanceof EntityLivingBase) {
			
			if(this.radiation > 0)
				ContaminationUtil.applyRadData(entity, this.radiation * mod / 20F);
			
			if(this.fire)
				entity.setFire(5);
			
			if(!(entity instanceof EntityPlayer && ArmorUtil.checkForGoggles((EntityPlayer)entity)))
			if(this.blinding)
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		super.addInformation(stack, player, list, bool);
		
		if(radiation > 0) {
			list.add(EnumChatFormatting.GREEN + "[" + I18nUtil.resolveKey("trait.radioactive") + "]");
			String rad = "" + (Math.floor(radiation * 1000) / 1000);
			list.add(EnumChatFormatting.YELLOW + (rad + "RAD/s"));
		}
		
		if(fire)
			list.add(EnumChatFormatting.GOLD + "[" + I18nUtil.resolveKey("trait.hot") + "]");
		
		if(blinding)
			list.add(EnumChatFormatting.DARK_AQUA + "[" + I18nUtil.resolveKey("trait.blinding") + "]");
		
		int[] breeder = BreederRecipes.getFuelValue(stack);
		
		if(breeder != null) {
			list.add(BreederRecipes.getHEATString("[" + I18nUtil.resolveKey("trait.heat", breeder[0]) + "]", breeder[0]));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.breeding", breeder[1]));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.furnace", (breeder[0] * breeder[1] * 5)));
		}
	}
}
