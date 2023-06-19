package com.hbm.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	
	public static abstract class AStack implements Comparable<AStack> {
		
		public int stacksize;
		
		public boolean isApplicable(ItemStack stack) {
			return isApplicable(new ComparableStack(stack));
		}
		
		/*
		 * Is it unprofessional to pool around in child classes from an abstract superclass? Do I look like I give a shit?
		 * 
		 * Major fuckup: comparablestacks need EQUAL stacksize but the oredictstack ignores stack size entirely
		 */
		public boolean isApplicable(ComparableStack comp) {
			
			if(this instanceof ComparableStack) {
				return ((ComparableStack)this).equals(comp);
			}
			
			if(this instanceof OreDictStack) {
				
				List<ItemStack> ores = OreDictionary.getOres(((OreDictStack)this).name);
				
				for(ItemStack stack : ores) {
					if(stack.getItem() == comp.item && stack.getItemDamage() == comp.meta)
						return true;
				}
			}
			
			return false;
		}
		
		/**
		 * Whether the supplied itemstack is applicable for a recipe (e.g. anvils). Slightly different from {@code isApplicable}.
		 * @param stack the ItemStack to check
		 * @param ignoreSize whether size should be ignored entirely or if the ItemStack needs to be >at least< the same size as this' size
		 * @return
		 */
		public abstract boolean matchesRecipe(ItemStack stack, boolean ignoreSize);
		
		public abstract AStack copy();
		
		/**
		 * Generates either an ItemStack or an ArrayList of ItemStacks
		 * @return
		 */
		public abstract List<ItemStack> extractForNEI();
		
		public ItemStack extractForCyclingDisplay(int cycle) {
			List<ItemStack> list = extractForNEI();
			
			cycle *= 50;
			
			return list.get((int)(System.currentTimeMillis() % (cycle * list.size()) / cycle));
		}
	}
	
	public static class ComparableStack extends AStack {

		public Item item;
		public int meta;
		
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
		
		public ComparableStack(Block item, int stacksize) {
			this.item = Item.getItemFromBlock(item);
			this.stacksize = stacksize;
			this.meta = 0;
		}
		
		public ComparableStack(Block item, int stacksize, int meta) {
			this.item = Item.getItemFromBlock(item);
			this.stacksize = stacksize;
			this.meta = meta;
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
			
			if(item == null) {
				if(!GeneralConfig.enableSilentCompStackErrors) {
					MainRegistry.logger.error("ComparableStack has a null item! This is a serious issue!");
					Thread.currentThread().dumpStack();
				}
				item = ModItems.nothing;
			}
			
			String name = Item.itemRegistry.getNameForObject(item);
			
			if(name == null) {
				if(!GeneralConfig.enableSilentCompStackErrors) {
					MainRegistry.logger.error("ComparableStack holds an item that does not seem to be registered. How does that even happen? This error can be turned off with the config <enableSilentCompStackErrors>. Item name: " + item.getUnlocalizedName());
					Thread.currentThread().dumpStack();
				}
				item = ModItems.nothing;
			}
			
			final int prime = 31;
			int result = 1;
			result = prime * result + Item.itemRegistry.getNameForObject(item).hashCode(); //using the int ID will cause fucky-wuckys if IDs are scrambled
			result = prime * result + meta;
			result = prime * result + stacksize;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			ComparableStack other = (ComparableStack) obj;
			if(item == null) {
				if(other.item != null)
					return false;
			} else if(!item.equals(other.item))
				return false;
			if(meta != OreDictionary.WILDCARD_VALUE && other.meta != OreDictionary.WILDCARD_VALUE && meta != other.meta)
				return false;
			if(stacksize != other.stacksize)
				return false;
			return true;
		}

		@Override
		public int compareTo(AStack stack) {
			
			if(stack instanceof ComparableStack) {
				
				ComparableStack comp = (ComparableStack) stack;
				
				int thisID = Item.getIdFromItem(item);
				int thatID = Item.getIdFromItem(comp.item);
				
				if(thisID > thatID)
					return 1;
				if(thatID > thisID)
					return -1;
				
				if(meta > comp.meta)
					return 1;
				if(comp.meta > meta)
					return -1;
				
				return 0;
			}

			//if compared with an ODStack, the CStack will take priority
			if(stack instanceof OreDictStack)
				return 1;
			
			return 0;
		}

		@Override
		public AStack copy() {
			return new ComparableStack(item, stacksize, meta);
		}

		@Override
		public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
			
			if(stack == null)
				return false;
			
			if(stack.getItem() != this.item)
				return false;
			
			if(this.meta != OreDictionary.WILDCARD_VALUE && stack.getItemDamage() != this.meta)
				return false;
			
			if(!ignoreSize && stack.stackSize < this.stacksize)
				return false;
			
			return true;
		}

		@Override
		public List<ItemStack> extractForNEI() {
			return Arrays.asList(new ItemStack[] {this.toStack()});
		}
	}
	
	/*
	 * This implementation does not override the compare function, which effectively makes it ignore stack data.
	 * This is still in line with the use-case of the ComparableNBTStack holding machine output stack information,
	 * since the compare function is not needed. In case the compare function is required, make a new child class.
	 */
	public static class ComparableNBTStack extends ComparableStack {
		
		NBTTagCompound nbt;
		
		public ComparableNBTStack(ItemStack stack) {
			super(stack);
		}
		
		public ComparableNBTStack(Item item) {
			super(item);
		}
		
		public ComparableNBTStack(Block item) {
			super(item);
		}
		
		public ComparableNBTStack(Block item, int stacksize) {
			super(item, stacksize);
		}
		
		public ComparableNBTStack(Block item, int stacksize, int meta) {
			super(item, stacksize, meta);
		}
		
		public ComparableNBTStack(Item item, int stacksize) {
			super(item, stacksize);
		}
		
		public ComparableNBTStack(Item item, int stacksize, int meta) {
			super(item, stacksize, meta);
		}
		
		public ComparableNBTStack addNBT(NBTTagCompound nbt) {
			this.nbt = nbt;
			return this;
		}
		
		public ItemStack toStack() {
			ItemStack stack = super.toStack();
			stack.stackTagCompound = this.nbt;
			return stack;
		}
	}
	
	public static class OreDictStack extends AStack {

		public String name;
		
		public OreDictStack(String name) {
			this.name = name;
			this.stacksize = 1;
		}
		
		public OreDictStack(String name, int stacksize) {
			this(name);
			this.stacksize = stacksize;
		}
		
		public List<ItemStack> toStacks() {
			return OreDictionary.getOres(name);
		}

		@Override
		public int compareTo(AStack stack) {
			
			if(stack instanceof OreDictStack) {
				
				OreDictStack comp = (OreDictStack) stack;
				return name.compareTo(comp.name);
			}
			
			//if compared with a CStack, the ODStack will yield
			if(stack instanceof ComparableStack)
				return -1;
			
			return 0;
		}

		@Override
		public AStack copy() {
			return new OreDictStack(name, stacksize);
		}

		@Override
		public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
			
			if(stack == null)
				return false;
			
			if(!ignoreSize && stack.stackSize < this.stacksize)
				return false;
			
			int[] ids = OreDictionary.getOreIDs(stack);
			
			if(ids == null || ids.length == 0)
				return false;
			
			for(int i = 0; i < ids.length; i++) {
				if(this.name.equals(OreDictionary.getOreName(ids[i])))
					return true;
			}
			
			return false;
		}

		@Override
		public List<ItemStack> extractForNEI() {
			
			List<ItemStack> fromDict = OreDictionary.getOres(name);
			List<ItemStack> ores = new ArrayList();
			
			for(ItemStack stack : fromDict) {

				ItemStack copy = stack.copy();
				copy.stackSize = this.stacksize;
				
				if(stack.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
					ores.add(copy);
				} else {
					ores.addAll(MainRegistry.proxy.getSubItems(copy));
				}
			}
			
			return ores;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + this.stacksize;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			OreDictStack other = (OreDictStack) obj;
			if(name == null) {
				if(other.name != null)
					return false;
			} else if(!name.equals(other.name)) {
				return false;
			}
			if(this.stacksize != other.stacksize)
				return false;
			return true;
		}
	}
	
	public static class MetaBlock {

		public Block block;
		public int meta;
		
		public MetaBlock(Block block, int meta) {
			this.block = block;
			this.meta = meta;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Block.blockRegistry.getNameForObject(block).hashCode();
			result = prime * result + meta;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			MetaBlock other = (MetaBlock) obj;
			if(block == null) {
				if(other.block != null)
					return false;
			} else if(!block.equals(other.block))
				return false;
			if(meta != other.meta)
				return false;
			return true;
		}
		
		public MetaBlock(Block block) {
			this(block, 0);
		}
		
		@Deprecated public int getID() {
			return hashCode();
		}
	}

}
