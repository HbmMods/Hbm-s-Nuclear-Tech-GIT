package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStamp extends Item {
	
	public StampType type;
	public static final HashMap<StampType, List<ItemStack>> stamps = new HashMap();
	
	public ItemStamp(int dura, StampType type) {
		this.setMaxDamage(dura);
		this.type = type;
		
		List<ItemStack> list = stamps.get(type);
		
		if(list == null)
			list = new ArrayList();
		
		list.add(new ItemStack(this));
		stamps.put(type, list);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(this.type == StampType.PLATE || this.type == StampType.WIRE || this.type == StampType.CIRCUIT)
			list.add("[CREATED USING TEMPLATE FOLDER]");
	}
	
	//TODO: give UFFR one (1) good boy token
	public static enum StampType {
		FLAT,
		PLATE,
		WIRE,
		CIRCUIT,
		//DISC,
		C357,
		C44,
		C50,
		C9;
	}
}
