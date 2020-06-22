package com.hbm.inventory;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesCommon {
	
	public static ItemStack[] copyStackArray(ItemStack[] array) {
		
		if(array == null)
			return null;
		
		ItemStack[] clone = new ItemStack[array.length];
		
		for(int i = 0; i < array.length; i++) {
			
			if(array[i] != null)
				clone[i] = array[i].copy();
		}
		
		return clone;
	}
	
	public static ItemStack[] objectToStackArray(Object[] array) {

		if(array == null)
			return null;
		
		ItemStack[] clone = new ItemStack[array.length];
		
		for(int i = 0; i < array.length; i++) {
			
			if(array[i] instanceof ItemStack)
				clone[i] = (ItemStack)array[i];
		}
		
		return clone;
	}
	
	public static class ComparableStack {

		Item item;
		int stacksize;
		int meta;
		
		public ComparableStack(ItemStack stack) {
			this.item = stack.getItem();
			this.stacksize = stack.stackSize;
			this.meta = stack.getItemDamage();
		}
		
		public ComparableStack makeSingular() {
			stacksize = 1;
			return this;
		}
		
		public ComparableStack(Item item) {
			this.item = item;
			this.stacksize = 1;
			this.meta = 0;
		}
		
		public ComparableStack(Block item) {
			this.item = Item.getItemFromBlock(item);
			this.stacksize = 1;
			this.meta = 0;
		}
		
		public ComparableStack(Item item, int stacksize) {
			this(item);
			this.stacksize = stacksize;
		}
		
		public ComparableStack(Item item, int stacksize, int meta) {
			this(item, stacksize);
			this.meta = meta;
		}
		
		public ItemStack toStack() {
			
			return new ItemStack(item, stacksize, meta);
		}
		
		public String[] getDictKeys() {
			
			int[] ids = OreDictionary.getOreIDs(toStack());
			
			if(ids == null || ids.length == 0)
				return new String[0];
			
			String[] entries = new String[ids.length];
			
			for(int i = 0; i < ids.length; i++) {
				
				entries[i] = OreDictionary.getOreName(ids[i]);
			}
			
			return entries;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Item.itemRegistry.getNameForObject(item).hashCode(); //using the int ID will cause fucky-wuckys if IDs are scrambled
			result = prime * result + meta;
			result = prime * result + stacksize;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ComparableStack other = (ComparableStack) obj;
			if (item == null) {
				if (other.item != null)
					return false;
			} else if (!item.equals(other.item))
				return false;
			if (meta != OreDictionary.WILDCARD_VALUE && other.meta != OreDictionary.WILDCARD_VALUE && meta != other.meta)
				return false;
			if (stacksize != other.stacksize)
				return false;
			return true;
		}
	}

}
