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

public class ItemHazard extends ItemCustomLore {
	
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

	float radiation;
	float digamma;
	int fire;
	boolean blinding;
	boolean asbestos;
	boolean hydro;
	
	public ItemHazard() {
	}
	
	public ItemHazard addRadiation(float radiation) {
		this.radiation = radiation;
		return this;
	}
	
	public ItemHazard addDigamma(float digamma) {
		this.digamma = digamma;
		return this;
	}
	
	public ItemHazard addFire(int fire) {
		this.fire = fire;
		return this;
	}
	
	public ItemHazard addAsbestos() {
		this.asbestos = true;
		return this;
	}
	
	public ItemHazard addHydroReactivity() {
		this.hydro = true;
		return this;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		
		if(entity instanceof EntityLivingBase)
			applyEffects((EntityLivingBase) entity, stack.stackSize, i, b);
	}

	public void applyEffects(EntityLivingBase entity, float mod, int slot, boolean currentItem) {
			
		if(this.radiation > 0)
			ContaminationUtil.applyRadData(entity, this.radiation * mod / 20F);

		if(this.digamma > 0)
			ContaminationUtil.applyDigammaData(entity, 1F / ((float) digamma));

		if(this.fire > 0)
			entity.setFire(this.fire);

		if(this.asbestos)
			ContaminationUtil.applyAsbestos(entity, (int) (1 * mod));

		if(this.hydro && currentItem) {

			if(!entity.worldObj.isRemote && entity.isInWater() && entity instanceof EntityPlayer) {
				
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack held = player.getHeldItem();
				
				player.inventory.mainInventory[player.inventory.currentItem] = held.getItem().getContainerItem(held);
				player.inventoryContainer.detectAndSendChanges();
				player.worldObj.newExplosion(null, player.posX, player.posY + player.getEyeHeight() - player.getYOffset(), player.posZ, 2F, true, true);
			}
		}

		if(this.blinding && !(entity instanceof EntityPlayer && ArmorUtil.checkForGoggles((EntityPlayer) entity))) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 0));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		super.addInformation(stack, player, list, bool);
		
		if(this.radiation > 0) {
			list.add(EnumChatFormatting.GREEN + "[" + I18nUtil.resolveKey("trait.radioactive") + "]");
			String rad = "" + (Math.floor(radiation * 1000) / 1000);
			list.add(EnumChatFormatting.YELLOW + (rad + "RAD/s"));
		}
		
		if(this.fire > 0) {
			list.add(EnumChatFormatting.GOLD + "[" + I18nUtil.resolveKey("trait.hot") + "]");
		}
		
		if(this.blinding) {
			list.add(EnumChatFormatting.DARK_AQUA + "[" + I18nUtil.resolveKey("trait.blinding") + "]");
		}
		
		if(this.asbestos) {
			list.add(EnumChatFormatting.WHITE + "[" + I18nUtil.resolveKey("trait.asbestos") + "]");
		}
		
		if(this.hydro) {
			list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.hydro") + "]");
		}
		
		if(this.digamma > 0) {
			float d = ((int) ((1000F / digamma) * 10F)) / 10F;
			list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.digamma") + "]");
			list.add(EnumChatFormatting.DARK_RED + "" + d + "mDRX/s");
		}
		
		int[] breeder = BreederRecipes.getFuelValue(stack);
		
		if(breeder != null) {
			list.add(BreederRecipes.getHEATString("[" + I18nUtil.resolveKey("trait.heat", breeder[0]) + "]", breeder[0]));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.breeding", breeder[1]));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.furnace", (breeder[0] * breeder[1] * 5)));
		}
	}
	
	/*
	 * DEPRECATED CTORS
	 */
	@Deprecated()
	public ItemHazard(float radiation) {
		this.radiation = radiation;
	}

	@Deprecated()
	public ItemHazard(float radiation, boolean fire) {
		this.radiation = radiation;
		if(fire) this.fire = 5;
	}

	@Deprecated()
	public ItemHazard(float radiation, boolean fire, boolean blinding) {
		this.radiation = radiation;
		this.blinding = blinding;
		if(fire) this.fire = 5;
	}
}
