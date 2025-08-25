package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.i18n.I18nUtil;

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
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.desc2"));
		}

		if(this == ModItems.meteorite_sword_seared) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.seared.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.seared.desc2"));
		}

		if(this == ModItems.meteorite_sword_reforged) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.reforged.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.reforged.desc2"));
		}

		if(this == ModItems.meteorite_sword_hardened) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.hardened.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.hardened.desc2"));
		}

		if(this == ModItems.meteorite_sword_alloyed) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.alloyed.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.alloyed.desc2"));
		}

		if(this == ModItems.meteorite_sword_machined) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.machined.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.machined.desc2"));
		}

		if(this == ModItems.meteorite_sword_treated) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.treated.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.treated.desc2"));
		}

		if(this == ModItems.meteorite_sword_etched) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.etched.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.etched.desc2"));
		}

		if(this == ModItems.meteorite_sword_bred) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.bred.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.bred.desc2"));
		}

		if(this == ModItems.meteorite_sword_irradiated) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.irradiated.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.irradiated.desc2"));
		}

		if(this == ModItems.meteorite_sword_fused) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.fused.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.fused.desc2"));
		}

		if(this == ModItems.meteorite_sword_baleful) {
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.baleful.desc1"));
			list.add(EnumChatFormatting.ITALIC + I18nUtil.resolveKey("item.meteorite_sword.baleful.desc2"));
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
