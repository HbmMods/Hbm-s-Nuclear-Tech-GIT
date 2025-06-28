package com.hbm.items.armor;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.main.MainRegistry;
import com.hbm.util.i18n.I18nUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ItemArmorMod extends Item {

	public final int type;
	public final boolean helmet;
	public final boolean chestplate;
	public final boolean leggings;
	public final boolean boots;
	
	public ItemArmorMod(int type, boolean helmet, boolean chestplate, boolean leggings, boolean boots) {
		this.type = type;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
		
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.consumableTab);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add(EnumChatFormatting.DARK_PURPLE + I18nUtil.resolveKey("armorMod.applicableTo"));
		
		if(helmet && chestplate && leggings && boots) {
			list.add("  " + I18nUtil.resolveKey("armorMod.all"));
		} else {

			if(helmet)
				list.add("  " + I18nUtil.resolveKey("armorMod.helmets"));
			if(chestplate)
				list.add("  " + I18nUtil.resolveKey("armorMod.chestplates"));
			if(leggings)
				list.add("  " + I18nUtil.resolveKey("armorMod.leggings"));
			if(boots)
				list.add("  " + I18nUtil.resolveKey("armorMod.boots"));
		}
		list.add(EnumChatFormatting.DARK_PURPLE + "Slot:");
		
		switch(this.type) {
		case ArmorModHandler.helmet_only: list.add("  " + I18nUtil.resolveKey("armorMod.type.helmet")); break;
		case ArmorModHandler.plate_only: list.add("  " + I18nUtil.resolveKey("armorMod.type.chestplate")); break;
		case ArmorModHandler.legs_only: list.add("  " + I18nUtil.resolveKey("armorMod.type.leggings")); break;
		case ArmorModHandler.boots_only: list.add("  " + I18nUtil.resolveKey("armorMod.type.boots")); break;
		case ArmorModHandler.servos: list.add("  " + I18nUtil.resolveKey("armorMod.type.servo")); break;
		case ArmorModHandler.cladding: list.add("  " + I18nUtil.resolveKey("armorMod.type.cladding")); break;
		case ArmorModHandler.kevlar: list.add("  " + I18nUtil.resolveKey("armorMod.type.insert")); break;
		case ArmorModHandler.extra: list.add("  " + I18nUtil.resolveKey("armorMod.type.special")); break;
		case ArmorModHandler.battery: list.add("  " + I18nUtil.resolveKey("armorMod.type.battery")); break;
		}
	}

	@SideOnly(Side.CLIENT)
	public void addDesc(List list, ItemStack stack, ItemStack armor) {
		list.add(stack.getDisplayName());
	}
	
	public void modUpdate(EntityLivingBase entity, ItemStack armor) { }
	
	public void modDamage(LivingHurtEvent event, ItemStack armor) { }
	
	public Multimap getModifiers(ItemStack armor) { return null; }
	
	@SideOnly(Side.CLIENT)
	public void modRender(RenderPlayerEvent.SetArmorModel event, ItemStack armor) { }
}
