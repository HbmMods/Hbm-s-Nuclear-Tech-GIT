package com.hbm.items.special;

import java.util.List;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemStarmetal extends Item {
	
	private IIcon[] icons = new IIcon[4];
	
	public ItemStarmetal() {
		this.setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		this.itemIcon = reg.registerIcon(this.getIconString());

		this.icons[0] = reg.registerIcon(RefStrings.MODID + ":ingot_starmetal");
		this.icons[1] = reg.registerIcon(RefStrings.MODID + ":ingot_starmetal_astra");
		this.icons[2] = reg.registerIcon(RefStrings.MODID + ":ingot_starmetal_ursa");
		this.icons[3] = reg.registerIcon(RefStrings.MODID + ":ingot_starmetal_orion");
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		super.getSubItems(item, tab, list);
		
		//for(int i = 0; i < 4; i++)
		//	list.add(new ItemStack(item, 1, i));
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		
		int s = Math.abs(meta) % 4;
		return icons[s];
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		switch(stack.getItemDamage()) {
		case 1: list.add(EnumChatFormatting.ITALIC + "Astra"); break;
		case 2: list.add(EnumChatFormatting.ITALIC + "Ursa"); break;
		case 3: list.add(EnumChatFormatting.ITALIC + "Orion"); break;
		}
	}
}
