package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;

import com.hbm.util.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemSwordMeteorite extends ItemSwordAbility {
	
	public static final List<ItemSwordMeteorite> swords = new ArrayList();

	public ItemSwordMeteorite(float damage, double movement, ToolMaterial material) {
		super(damage, movement, material);
		this.setMaxDamage(0);
		swords.add(this);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		super.addInformation(stack, player, list, ext);

		if(this == ModItems.meteorite_sword) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_seared) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_seared.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_reforged) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_reforged.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_hardened) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_hardened.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_alloyed) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_alloyed.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_machined) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_machined.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_treated) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_treated.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_etched) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_etched.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_bred) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_bred.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_irradiated) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_irradiated.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_fused) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_fused.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		if(this == ModItems.meteorite_sword_baleful) {
			for(String s : I18nUtil.resolveKeyArray("item.meteorite_sword_baleful.desc"))
				list.add(EnumChatFormatting.ITALIC + s);
		}

		/*
		 * if(this == ModItems.meteorite_sword_subatomic) {
		 * 
		 * }
		 */

		/*
		 * if(this == ModItems.meteorite_sword_void) {
		 * 
		 * }
		 */

		/*
		 * if(this == ModItems.meteorite_sword_clouded) {
		 * list.add(EnumChatFormatting.ITALIC + "The sword to fell");
		 * list.add(EnumChatFormatting.ITALIC + "The capital"); }
		 */

		/*
		 * if(this == ModItems.meteorite_sword_enchanted) {
		 * list.add(EnumChatFormatting.ITALIC + "The sword to defeat");
		 * list.add(EnumChatFormatting.ITALIC + "The country"); }
		 */

		/*
		 * if(this == ModItems.meteorite_sword_fstbmb) {
		 * list.add(EnumChatFormatting.ITALIC + "The sword to end");
		 * list.add(EnumChatFormatting.ITALIC + "The world"); }
		 */

		/*
		 * if(this == ModItems.meteorite_sword_digamma) {
		 * list.add(EnumChatFormatting.ITALIC + "The sword to extinguish");
		 * list.add(EnumChatFormatting.ITALIC + "The universe"); }
		 */

		// meteorite_sword_duchess
		// meteorite_sword_queen
		// meteorite_sword_storm
		// §k
	}

}
