package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStamp extends Item {
	
	protected StampType type;
	public static final HashMap<StampType, List<ItemStack>> stamps = new HashMap();
	
	public ItemStamp(int dura, StampType type) {
		this.setMaxDamage(dura);
		this.type = type;
		
		if(type != null) {
			this.addStampToList(this, 0, type);
		}
	}
	
	protected void addStampToList(Item item, int meta, StampType type) {
		List<ItemStack> list = stamps.get(type);
		
		if(list == null)
			list = new ArrayList();
		
		ItemStack stack = new ItemStack(item, 1, meta);
		
		list.add(stack);
		stamps.put(type, list);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if((this.type == StampType.PLATE || this.type == StampType.WIRE || this.type == StampType.CIRCUIT) && this.getMaxDamage() > 0)
			list.add("[CREATED USING TEMPLATE FOLDER]");
	}
	
	/** Params can't take an ItemStack, for some reason it crashes during init */
	public StampType getStampType(Item item, int meta) {
		return type;
	}
	
	public static enum StampType {
		FLAT,
		PLATE,
		WIRE,
		CIRCUIT,
		C357,
		C44,
		C50,
		C9,
		PRINTING1,
		PRINTING2,
		PRINTING3,
		PRINTING4,
		PRINTING5,
		PRINTING6,
		PRINTING7,
		PRINTING8;
	}
}
