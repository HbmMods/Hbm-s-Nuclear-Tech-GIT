package com.hbm.itempool;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.ItemPoolConfigJSON;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemPool {

	public String name;
	public WeightedRandomChestContent[] pool = new WeightedRandomChestContent[0];
	
	private List<WeightedRandomChestContent> buildingList = new ArrayList();
	
	public ItemPool() { }
	
	public ItemPool(String name) {
		this.name = name;
		ItemPoolConfigJSON.pools.put(name, this);
	}

	public ItemPool add(Item item, int meta, int min, int max, int weight) {	buildingList.add(new WeightedRandomChestContent(item, meta, min, max, weight));							return this; }
	public ItemPool add(Block block, int meta, int min, int max, int weight) {	buildingList.add(new WeightedRandomChestContent(Item.getItemFromBlock(block), meta, min, max, weight));	return this; }
	public ItemPool add(ItemStack item, int min, int max, int weight) {			buildingList.add(new WeightedRandomChestContent(item, min, max, weight));								return this; }
	
	public ItemPool build() {
		
		this.pool = new WeightedRandomChestContent[buildingList.size()];
		
		for(int i = 0; i < pool.length; i++) {
			this.pool[i] = this.buildingList.get(i);
		}
		
		this.buildingList.clear();
		
		return this;
	}
	
	public static void initialize() {
		//here we abuse java's lazy loading behavior, referencing the item pools with what is effectively a NOP in order to cause instantiation
		//this of course has to happen after the items and blocks are registered but before the item pool config
		//will this cause horrific issues if something manages to load the item pools prematurely? absolutely
		//however, the advantage here is that i don't need to separate the fields from the instantiation, i can simply slap them together into the class and it works
		//is this shitty coding practice? yes! but it's slightly more convenient than the alternative so i will roll with it anyway because go fuck yourself
		//who are you to tell me what i can and cannot do
		ItemPoolsLegacy.poolGeneric.hashCode();
	}
}
