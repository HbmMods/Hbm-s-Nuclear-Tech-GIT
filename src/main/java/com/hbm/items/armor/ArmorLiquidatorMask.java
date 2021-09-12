package com.hbm.items.armor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.util.ArmorUtil;
import com.hbm.util.ArmorRegistry.HazardClass;

import api.hbm.item.IGasMask;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ArmorLiquidatorMask extends ArmorLiquidator implements IGasMask {

	public ArmorLiquidatorMask(ArmorMaterial material, int layer, int slot, String texture) {
		super(material, layer, slot, texture);
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
	}

	@Override
	public boolean isFilterApplicable(ItemStack stack, EntityLivingBase entity, ItemStack filter) {
		return true;
	}
}
