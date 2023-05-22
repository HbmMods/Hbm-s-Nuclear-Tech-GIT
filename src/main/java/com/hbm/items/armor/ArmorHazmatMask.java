package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.render.model.ModelM65;
import com.hbm.util.ArmorRegistry.HazardClass;
import com.hbm.util.ArmorUtil;

import api.hbm.item.IGasMask;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ArmorHazmatMask extends ArmorHazmat implements IGasMask {
	
	@SideOnly(Side.CLIENT)
	private ModelM65 modelM65;

	public ArmorHazmatMask(ArmorMaterial material, int slot, String texture) {
		super(material, slot, texture);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {

		if(this == ModItems.hazmat_helmet_red || this == ModItems.hazmat_helmet_grey) {
			if (this.modelM65 == null) {
				this.modelM65 = new ModelM65();
			}
			return this.modelM65;
		}
		
		return null;
	}

	@Override
	public ArrayList<HazardClass> getBlacklist(ItemStack stack, EntityLivingBase entity) {
		return new ArrayList(); // full hood has no restrictions
	}

	@Override
	public ItemStack getFilter(ItemStack stack, EntityLivingBase entity) {
		return ArmorUtil.getGasMaskFilter(stack);
	}

	@Override
	public void installFilter(ItemStack stack, EntityLivingBase entity, ItemStack filter) {
		ArmorUtil.installGasMaskFilter(stack, filter);
	}

	@Override
	public void damageFilter(ItemStack stack, EntityLivingBase entity, int damage) {
		ArmorUtil.damageGasMaskFilter(stack, damage);
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		ArmorUtil.addGasMaskTooltip(stack, player, list, ext);
		
		super.addInformation(stack, player, list, ext);
	}

	@Override
	public boolean isFilterApplicable(ItemStack stack, EntityLivingBase entity, ItemStack filter) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(player.isSneaking()) {
			
			ItemStack filter = this.getFilter(stack, player);
			
			if(filter != null) {
				ArmorUtil.removeFilter(stack);
				
				if(!player.inventory.addItemStackToInventory(filter)) {
					player.dropPlayerItemWithRandomChoice(filter, true);
				}
				
				return stack;
			}
		}
		
		return super.onItemRightClick(stack, world, player);
	}
}
