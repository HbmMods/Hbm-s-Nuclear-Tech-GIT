package com.hbm.items.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMetaUpgrade extends ItemMachineUpgrade {
	
	protected int levels;
	
	public ItemMetaUpgrade(int levels) {
		super();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.levels = levels;
	}
	
	public ItemMetaUpgrade(UpgradeType type, int levels) {
		super(type);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.levels = levels;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		
		for(int i = 0; i < this.levels; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	protected IIcon[] icons;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		
		this.icons = new IIcon[levels];

		for(int i = 0; i < levels; i++) {
			this.icons[i] = reg.registerIcon(this.getIconString() + "_" + (i + 1));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		if(meta >= 0 && meta < levels) {
			return this.icons[meta];
		}
		
		return this.icons[0];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName() + "_" + (stack.getItemDamage() + 1);
	}
}
