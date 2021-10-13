package com.hbm.items.special;

import java.util.List;

import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

public class ItemSiegeCoin extends Item {
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	public ItemSiegeCoin() {
		this.hasSubtypes = true;
		this.setMaxDamage(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta % icons.length];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		this.itemIcon = Items.stick.getIconFromDamage(0);
		
		icons = new IIcon[SiegeTier.getLength()];
		
		for(int i = 0; i < SiegeTier.getLength(); i++) {
			icons[i] = p_94581_1_.registerIcon(RefStrings.MODID + ":coin_siege_" + SiegeTier.tiers[i].name);
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.uncommon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(int i = 0; i < SiegeTier.getLength(); i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		super.addInformation(stack, player, list, bool);
		list.add(EnumChatFormatting.YELLOW + "Tier " + (stack.getItemDamage() + 1));
	}
}
