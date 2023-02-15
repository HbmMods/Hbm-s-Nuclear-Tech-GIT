package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ItemEnumMulti;
import com.hbm.items.machine.ItemZirnoxRod.EnumZirnoxType;
import com.hbm.util.EnumUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemDepletedWaste extends ItemEnumMulti {

	public ItemDepletedWaste(Class<? extends Enum> theEnum, boolean multiName, boolean multiTexture) {
		super(theEnum, multiName, multiTexture);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < theEnum.getEnumConstants().length; i++) {
			if(theEnum == ItemZirnoxRod.EnumZirnoxType.class) { //i could make an interface or a base enum, but it's only used for 'nox rods anyway
				EnumZirnoxType num = EnumUtil.grabEnumSafely(EnumZirnoxType.class, i);
				if(!num.breeding) list.add(new ItemStack(item, 1, i)); //wastes' ordinals are always equal to their fuels' ordinals
			} else
				list.add(new ItemStack(item, 1, i));
		}
	}
	
	//TODO: cooling stuff
}
