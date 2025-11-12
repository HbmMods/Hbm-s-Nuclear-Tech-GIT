package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;

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
			list.add(EnumChatFormatting.ITALIC + "Forged from a fallen star");
			list.add(EnumChatFormatting.ITALIC + "Sharper than most terrestrial blades");
		}

		if(this == ModItems.meteorite_sword_seared) {
			list.add(EnumChatFormatting.ITALIC + "Fire strengthens the blade");
			list.add(EnumChatFormatting.ITALIC + "Making it even more powerful");
		}

		if(this == ModItems.meteorite_sword_reforged) {
			list.add(EnumChatFormatting.ITALIC + "The sword has been reforged");
			list.add(EnumChatFormatting.ITALIC + "To rectify past imperfections");
		}

		if(this == ModItems.meteorite_sword_hardened) {
			list.add(EnumChatFormatting.ITALIC + "Extremely high pressure has been used");
			list.add(EnumChatFormatting.ITALIC + "To harden the blade further");
		}

		if(this == ModItems.meteorite_sword_alloyed) {
			list.add(EnumChatFormatting.ITALIC + "Cobalt fills the fissures");
			list.add(EnumChatFormatting.ITALIC + "Strengthening the sword");
		}

		if(this == ModItems.meteorite_sword_machined) {
			list.add(EnumChatFormatting.ITALIC + "Advanced machinery was used");
			list.add(EnumChatFormatting.ITALIC + "To refine the blade even more");
		}

		if(this == ModItems.meteorite_sword_treated) {
			list.add(EnumChatFormatting.ITALIC + "Chemicals have been applied");
			list.add(EnumChatFormatting.ITALIC + "Making the sword more powerful");
		}

		if(this == ModItems.meteorite_sword_etched) {
			list.add(EnumChatFormatting.ITALIC + "Acids clean the material");
			list.add(EnumChatFormatting.ITALIC + "To make this the perfect sword");
		}

		if(this == ModItems.meteorite_sword_bred) {
			list.add(EnumChatFormatting.ITALIC + "Immense heat and radiation");
			list.add(EnumChatFormatting.ITALIC + "Compress the material");
		}

		if(this == ModItems.meteorite_sword_irradiated) {
			list.add(EnumChatFormatting.ITALIC + "The power of the Atom");
			list.add(EnumChatFormatting.ITALIC + "Gives the sword might");
		}

		if(this == ModItems.meteorite_sword_fused) {
			list.add(EnumChatFormatting.ITALIC + "This blade has met");
			list.add(EnumChatFormatting.ITALIC + "With the forces of the stars");
		}

		if(this == ModItems.meteorite_sword_baleful) {
			list.add(EnumChatFormatting.ITALIC + "This sword has met temperatures");
			list.add(EnumChatFormatting.ITALIC + "Far beyond what normal material can endure");
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
