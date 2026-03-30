package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

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
		
		String[] lines = I18nUtil.resolveKeyArray(this.getUnlocalizedName()+ "desc");
		for(String line : lines) {
			list.add(EnumChatFormatting.ITALIC + line);
		}
	}
}
