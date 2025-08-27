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

		if (this == ModItems.meteorite_sword) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_seared) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.seared.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_reforged) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.reforged.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_hardened) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.hardened.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_alloyed) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.alloyed.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_machined) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.machined.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_treated) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.treated.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_etched) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.etched.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_bred) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.bred.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_irradiated) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.irradiated.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_fused) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.fused.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
		}

		if (this == ModItems.meteorite_sword_baleful) {
			String[] lines = I18nUtil.resolveKeyArray("item.meteorite_sword.baleful.desc");
			for (String line : lines) {
				list.add(EnumChatFormatting.ITALIC + line);
			}
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
