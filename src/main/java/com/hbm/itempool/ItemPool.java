package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemPool {
	
	public static void initialize() {
		ItemPoolsLegacy.init();
		ItemPoolsComponent.init();
		ItemPoolsSingle.init();
		ItemPoolsRedRoom.init();
		ItemPoolsSatellite.init();
		ItemPoolsPile.init();
		ItemPoolsC130.init();
	}
	
	public static HashMap<String, ItemPool> pools = new HashMap();

	public String name;
	public WeightedRandomChestContent[] pool = new WeightedRandomChestContent[0];
	
	private List<WeightedRandomChestContent> buildingList = new ArrayList();
	
	public ItemPool() { }
	
	public ItemPool(String name) {
		this.name = name;
		pools.put(name, this);
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
	
	/** Grabs the specified item pool out of the pool map, will return the backup pool if the given pool is not present */
	public static WeightedRandomChestContent[] getPool(String name) {
		ItemPool pool = pools.get(name);
		if(pool == null) return backupPool;
		return pool.pool;
	}
	
	public static ItemStack getStack(String pool, Random rand) {
		return getStack(ItemPool.getPool(pool), rand);
	}
	
	public static ItemStack getStack(WeightedRandomChestContent[] pool, Random rand) {
		WeightedRandomChestContent weighted = (WeightedRandomChestContent) WeightedRandom.getRandomItem(rand, pool);
		ItemStack stack = weighted.theItemId.copy();
		stack.stackSize = weighted.theMinimumChanceToGenerateItem + rand.nextInt(weighted.theMaximumChanceToGenerateItem - weighted.theMinimumChanceToGenerateItem + 1);
		return stack;
	}
	
	/** Should a pool be lost due to misconfiguration or otherwise, this pool will be returned in its place */
	private static WeightedRandomChestContent[] backupPool = new WeightedRandomChestContent[] {
			weighted(Items.bread, 0, 1, 3, 10),
			weighted(Items.stick, 0, 2, 5, 10),
			weighted(ModItems.scrap, 0, 1, 3, 10),
			weighted(ModItems.dust, 0, 2, 5, 5)
	};
}
