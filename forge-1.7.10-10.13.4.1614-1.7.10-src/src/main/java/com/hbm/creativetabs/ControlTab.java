package com.hbm.creativetabs;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ControlTab extends CreativeTabs {

	public ControlTab(int p_i1853_1_, String p_i1853_2_) {
		super(p_i1853_1_, p_i1853_2_);
	}

	@Override
	public Item getTabIconItem() {
		
		if(ModItems.rod_balefire_blazing != null)
		{
			return ModItems.rod_balefire_blazing;
		}
		
		return Items.iron_pickaxe;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public void displayAllReleventItems(List list) {
		super.displayAllReleventItems(list);
		
		List<ItemStack> batteries = new ArrayList();
		
		for(Object o : list) {
			
			if(o instanceof ItemStack) {
				
				ItemStack stack = (ItemStack) o;
				
				if(stack.getItem() instanceof IBatteryItem) {
					batteries.add(stack);
				}
			}
		}
		
		for(ItemStack stack : batteries) {
			
			if(!(stack.getItem() instanceof IBatteryItem)) //shouldn't happen but just to make sure
				continue;
			
			IBatteryItem battery = (IBatteryItem) stack.getItem();
			
			ItemStack empty = stack.copy();
			ItemStack full = stack.copy();
			
			battery.setCharge(empty, 0);
			battery.setCharge(full, battery.getMaxCharge());
			
			int index = list.indexOf(stack);
			
			list.remove(index);
			list.add(index, full);
			
			//do not list empty versions of SU batteries
			if(battery.getChargeRate() > 0)
				list.add(index, empty);
		}
	}
}
