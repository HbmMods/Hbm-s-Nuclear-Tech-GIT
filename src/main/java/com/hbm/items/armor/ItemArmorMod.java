package com.hbm.items.armor;

import java.util.List;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.main.MainRegistry;

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
		list.add(EnumChatFormatting.DARK_PURPLE + "Applicable to:");
		
		if(helmet && chestplate && leggings && boots) {
			list.add("  All");
		} else {

			if(helmet)
				list.add("  Helmets");
			if(chestplate)
				list.add("  Chestplates");
			if(leggings)
				list.add("  Leggings");
			if(boots)
				list.add("  Boots");
		}
		list.add(EnumChatFormatting.DARK_PURPLE + "Slot:");
		
		switch(this.type) {
		case ArmorModHandler.helmet_only: list.add("  Helmet"); break;
		case ArmorModHandler.plate_only: list.add("  Chestplate"); break;
		case ArmorModHandler.legs_only: list.add("  Leggings"); break;
		case ArmorModHandler.boots_only: list.add("  Boots"); break;
		case ArmorModHandler.servos: list.add("  Servos"); break;
		case ArmorModHandler.cladding: list.add("  Cladding"); break;
		case ArmorModHandler.kevlar: list.add("  Insert"); break;
		case ArmorModHandler.extra: list.add("  Special"); break;
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
