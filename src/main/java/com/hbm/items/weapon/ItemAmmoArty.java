package com.hbm.items.weapon;

import java.util.List;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemAmmoArty extends Item {

	public static enum EnumArtyType {
		NORMAL("ammo_arty"),
		CLASSIC("ammo_arty_classic"),
		EXPLOSIVE("ammo_arty_he"),
		MINI_NUKE("ammo_arty_mini_nuke"),
		NUKE("ammo_arty_nuke");
		
		private String name;
		
		private EnumArtyType(String texture) {
			this.name = texture;
		}
	}
	
	public ItemAmmoArty() {
		this.setHasSubtypes(true);
		this.setCreativeTab(MainRegistry.weaponTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < EnumArtyType.values().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	private IIcon[] icons = new IIcon[EnumArtyType.values().length];

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		this.icons = new IIcon[EnumArtyType.values().length];

		for(int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":" + EnumArtyType.values()[i].name);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		EnumArtyType num = EnumUtil.grabEnumSafely(EnumArtyType.class, stack.getItemDamage());
		return "item." + num.name;
	}
}
