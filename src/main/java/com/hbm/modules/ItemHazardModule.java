package com.hbm.modules;

import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.inventory.BreederRecipes;
import com.hbm.items.ModItems;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.I18nUtil;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

@Deprecated
public class ItemHazardModule {
	
	/**
	 * Dependency injection: It's fun for boys and girls!
	 * All this interface-pattern-wish-wash only exists for three reasons:
	 * -it lets me add item hazards with ease by using self-returning setters
	 * -it's agnositc and also works with ItemBlocks or whatever implementation I want it to work
	 * -it makes the system truly centralized and I don't have to add new cases to 5 different classes when adding a new hazard
	 */

	public float radiation;
	public float digamma;
	public int fire;
	public boolean blinding;
	public int asbestos;
	public int coal;
	public boolean hydro;
	public float explosive;
	
	public float tempMod = 1F;
	
	public void setMod(float tempMod) {
		this.tempMod = tempMod;
	}
	
	public void addRadiation(float radiation) {
		this.radiation = radiation;
	}
	
	public void addDigamma(float digamma) {
		this.digamma = digamma;
	}
	
	public void addFire(int fire) {
		this.fire = fire;
	}
	
	public void addAsbestos(int asbestos) {
		this.asbestos = asbestos;
	}
	
	public void addCoal(int coal) {
		this.coal = coal;
	}
	
	public void addBlinding() {
		this.blinding = true;
	}
	
	public void addHydroReactivity() {
		this.hydro = true;
	}
	
	public void addExplosive(float bang) {
		this.explosive = bang;
	}

	public void applyEffects(EntityLivingBase entity, float mod, int slot, boolean currentItem) {
		
		boolean reacher = false;

		if(this.coal > 0) {
			if(!ArmorRegistry.hasProtection(entity, 3, HazardClass.PARTICLE_COARSE))
				HbmLivingProps.incrementBlackLung(entity, (int) (this.coal * Math.min(mod, 10)));
			else
				ArmorUtil.damageGasMaskFilter(entity, (int) (this.coal));
		}

		if(this.hydro && currentItem) {

			if(!entity.worldObj.isRemote && entity.isInWater() && entity instanceof EntityPlayer) {
				
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack held = player.getHeldItem();
				
				player.inventory.mainInventory[player.inventory.currentItem] = held.getItem().getContainerItem(held);
				player.inventoryContainer.detectAndSendChanges();
				player.worldObj.newExplosion(null, player.posX, player.posY + player.getEyeHeight() - player.getYOffset(), player.posZ, 2F, true, true);
			}
		}

		if(this.explosive > 0 && currentItem) {

			if(!entity.worldObj.isRemote && entity.isBurning() && entity instanceof EntityPlayer) {
				
				EntityPlayer player = (EntityPlayer) entity;
				ItemStack held = player.getHeldItem();
				
				player.inventory.mainInventory[player.inventory.currentItem] = held.getItem().getContainerItem(held);
				player.inventoryContainer.detectAndSendChanges();
				player.worldObj.newExplosion(null, player.posX, player.posY + player.getEyeHeight() - player.getYOffset(), player.posZ, this.explosive, true, true);
			}
		}
	}
	
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		if(this.coal > 0) {
			list.add(EnumChatFormatting.DARK_GRAY + "[" + I18nUtil.resolveKey("trait.coal") + "]");
		}
		
		if(this.hydro) {
			list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.hydro") + "]");
		}
		
		if(this.explosive > 0) {
			list.add(EnumChatFormatting.RED + "[" + I18nUtil.resolveKey("trait.explosive") + "]");
		}
		
		int[] breeder = BreederRecipes.getFuelValue(stack);
		
		if(breeder != null) {
			list.add(BreederRecipes.getHEATString("[" + I18nUtil.resolveKey("trait.heat", breeder[0]) + "]", breeder[0]));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.breeding", breeder[1]));
			list.add(EnumChatFormatting.YELLOW + I18nUtil.resolveKey("trait.furnace", (breeder[0] * breeder[1] * 5)));
		}
	}

	public boolean onEntityItemUpdate(EntityItem item) {
		
		if(!item.worldObj.isRemote) {
			
			if(this.hydro && item.worldObj.getBlock((int)Math.floor(item.posX), (int)Math.floor(item.posY), (int)Math.floor(item.posZ)).getMaterial() == Material.water) {

				item.setDead();
				item.worldObj.newExplosion(item, item.posX, item.posY, item.posZ, 2F, true, true);
				return true;
			}
			
			if(this.explosive > 0 && item.isBurning()) {

				item.setDead();
				item.worldObj.newExplosion(item, item.posX, item.posY, item.posZ, this.explosive, true, true);
				return true;
			}
		}
		
		return false;
	}
}
