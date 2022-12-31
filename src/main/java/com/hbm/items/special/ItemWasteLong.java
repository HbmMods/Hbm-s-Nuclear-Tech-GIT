package com.hbm.items.special;

import java.util.List;

import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemWasteLong extends ItemNuclearWaste {

	public ItemWasteLong() {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(MainRegistry.controlTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < WasteClass.values().length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		
		list.add(EnumChatFormatting.ITALIC + WasteClass.values()[rectify(stack.getItemDamage())].name);
		
		super.addInformation(stack, player, list, bool);
	}
	
	public static int rectify(int meta) {
		return Math.abs(meta) % WasteClass.values().length;
	}
	
	public enum WasteClass {

		//all decayed versions include lead-types and classic nuclear waste
		URANIUM235("Uranium-235", 0, 0),	//plutonium 239 and 240, neptunium 237 / -
		URANIUM233("Uranium-233", 0, 50),	//uranium 235, plutonium 239, neptunium 237 / -
		NEPTUNIUM("Neptunium-237", 0, 100),	//plutonium 239 and uranium 238 / -
		THORIUM("Thorium-232", 0, 0),		//uranium 233 and uranium 235 / -
		SCHRABIDIUM("Schrabidium-326", 0, 250); //tantalum, neodymium, solinium, euphemium, ghiorsium-336 / -
		
		public String name;
		public int liquid;
		public int gas;
		
		private WasteClass(String name, int liquid, int gas) {
			this.name = name;
			this.liquid = liquid;
			this.gas = gas;
		}
	}
}
