package com.hbm.items.special;

import java.util.List;

import javax.annotation.Nullable;

import com.hbm.lib.HelveticaFont;
import com.hbm.main.ResourceManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemPowderSr90 extends Item {
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public FontRenderer getFontRenderer(ItemStack stack) {
		HelveticaFont helvetica = new HelveticaFont(Minecraft.getMinecraft().gameSettings, ResourceManager.helvetica_tex, Minecraft.getMinecraft().renderEngine, true);
		return helvetica;
	}
	
	/*
	 *    WHAT TJE FIck
	 * 
	 *	i have given my flesh and blood to this computer 
	 * 
	 * 			and failure is all it repays me with
	 */

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		list.add("Every cloud has a silver lining...");
		list.add("Except nuclear mushroom clouds,");
		list.add("which have a lining of Strontium-90,");
		list.add("Caesium-137 and other radioactive");
		list.add("Isotopes.");
	}
}