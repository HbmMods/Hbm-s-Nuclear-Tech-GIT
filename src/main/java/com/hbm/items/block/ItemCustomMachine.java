package com.hbm.items.block;

import java.util.List;

import com.hbm.config.CustomMachineConfigJSON;
import com.hbm.config.CustomMachineConfigJSON.MachineConfiguration;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemCustomMachine extends ItemBlock {

	public ItemCustomMachine(Block block) {
		super(block);
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		
		for(int i = 0; i < CustomMachineConfigJSON.niceList.size(); i++) {
			MachineConfiguration conf = CustomMachineConfigJSON.niceList.get(i);
			ItemStack stack = new ItemStack(item, 1, i + 100);
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setString("machineType", conf.unlocalizedName);
			list.add(stack);
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		
		if(stack.hasTagCompound()) {
			String name = stack.getTagCompound().getString("machineType");
			MachineConfiguration conf = CustomMachineConfigJSON.customMachines.get(name);
			
			if(conf != null) {
				return conf.localizedName;
			}
			
			return "INVALID MACHINE CONTROLLER (" + name + ")";
		}
		
		return "INVALID MACHINE CONTROLLER";
	}
}
