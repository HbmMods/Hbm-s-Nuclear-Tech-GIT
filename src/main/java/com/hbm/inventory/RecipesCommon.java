package com.hbm.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
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
		
		/**
		 * Whether the supplied itemstack is applicable for a recipe (e.g. anvils). Slightly different from {@code isApplicable}.
		 * @param stack the ItemStack to check
		 * @param ignoreSize whether size should be ignored entirely or if the ItemStack needs to be >at least< the same size as this' size
		 * @return
		 */
		public abstract boolean matchesRecipe(ItemStack stack, boolean ignoreSize);

		public abstract AStack copy();
		public abstract AStack copy(int stacksize);
		
		/**
		 * Generates either an ItemStack or an ArrayList of ItemStacks
		 * @return
		 */
		public abstract List<ItemStack> extractForNEI();
		
		public ItemStack extractForCyclingDisplay(int cycle) {
			List<ItemStack> list = extractForNEI();
			cycle *= 50;
			
			if(list.isEmpty()) return new ItemStack(ModItems.nothing);
			return list.get((int)(System.currentTimeMillis() % (cycle * list.size()) / cycle));
		}
	}
	
	public static class ComparableStack extends AStack {

		public Item item;
		public int meta;
		
		public ComparableStack(ItemStack stack) {
			if(stack == null) {
				this.item = ModItems.nothing;
				this.stacksize = 1;
				return;
			}
			try {
				this.item = stack.getItem();
				if(this.item == null) this.item = ModItems.nothing; //i'm going to bash some fuckard's head in
				this.stacksize = stack.stackSize;
				this.meta = stack.getItemDamage();
			} catch(Exception ex) {
				this.item = ModItems.nothing;
				if(!GeneralConfig.enableSilentCompStackErrors) {
					ex.printStackTrace();
				}
			}
		}
		
		public ComparableStack makeSingular() {
			stacksize = 1;
			return this;
		}
		
		public ComparableStack(Item item) {
			this.item = item;
			if(this.item == null) this.item = ModItems.nothing;
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
		
		public ComparableStack(Block item, int stacksize, Enum meta) {
			this(item, stacksize);
			this.meta = meta.ordinal();
		}
		
		public ComparableStack(Item item, int stacksize) {
			this(item);
			this.stacksize = stacksize;
		}
		
		public ComparableStack(Item item, int stacksize, int meta) {
			this(item, stacksize);
			this.meta = meta;
		}
		
		public ComparableStack(Item item, int stacksize, Enum meta) {
			this(item, stacksize);
			this.meta = meta.ordinal();
		}
		
		public ItemStack toStack() {
			return new ItemStack(item == null ? ModItems.nothing : item, stacksize, meta);
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
			if(this == obj) return true;
			if(obj == null) return false;
			if(getClass() != obj.getClass()) return false;
			ComparableStack other = (ComparableStack) obj;
			if(item == null) {
				if(other.item != null) return false;
			} else if(!item.equals(other.item))
				return false;
			if(meta != OreDictionary.WILDCARD_VALUE && other.meta != OreDictionary.WILDCARD_VALUE && meta != other.meta) return false;
			if(stacksize != other.stacksize) return false;
			return true;
		}

		@Override
		public int compareTo(AStack stack) {
			
			//if compared with a NBTStack, the CStack will yield
			if(stack instanceof NBTStack) return -1;
			//if compared with an ODStack, the CStack will take priority
			if(stack instanceof OreDictStack) return 1;
			
			if(stack instanceof ComparableStack) {
				
				ComparableStack comp = (ComparableStack) stack;
				
				int thisID = Item.getIdFromItem(item);
				int thatID = Item.getIdFromItem(comp.item);
				
				if(thisID > thatID) return 1;
				if(thatID > thisID) return -1;
				
				if(meta > comp.meta) return 1;
				if(comp.meta > meta) return -1;
				
				return 0;
			}
			
			return 0;
		}

		@Override
		public ComparableStack copy() {
			return new ComparableStack(item, stacksize, meta);
		}

		@Override
		public ComparableStack copy(int stacksize) {
			return new ComparableStack(item, stacksize, meta);
		}

		@Override
		public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
			
			if(stack == null) return false;
			if(stack.getItem() != this.item) return false;
			if(this.meta != OreDictionary.WILDCARD_VALUE && stack.getItemDamage() != this.meta) return false;
			if(!ignoreSize && stack.stackSize < this.stacksize) return false;
			
			return true;
		}

		@Override
		public List<ItemStack> extractForNEI() {
			return Arrays.asList(new ItemStack[] {this.toStack()});
		}
	}
	
	public static class NBTStack extends ComparableStack {
		
		public NBTTagCompound nbt;
		
		public NBTStack(Item item) { super(item); }
		public NBTStack(Block item) { super(item); }
		public NBTStack(Block item, int stacksize) { super(item, stacksize); }
		public NBTStack(Block item, int stacksize, int meta) { super(item, stacksize, meta); }
		public NBTStack(Block item, int stacksize, Enum meta) { super(item, stacksize, meta); }
		public NBTStack(Item item, int stacksize) { super(item, stacksize); }
		public NBTStack(Item item, int stacksize, int meta) { super(item, stacksize, meta); }
		public NBTStack(Item item, int stacksize, Enum meta) { super(item, stacksize, meta); }
		public NBTStack(ItemStack stack) { super(stack.getItem(), stack.stackSize, stack.getItemDamage()); this.withNBT(stack.stackTagCompound); }

		public NBTStack withNBT(NBTTagCompound nbt) {
			this.nbt = nbt;
			return this;
		}
		
		public NBTStack initNBT() {
			if(this.nbt == null) this.nbt = new NBTTagCompound();
			return this;
		}
		
		public NBTStack setInt(String key, int value) {
			initNBT().nbt.setInteger(key, value);
			return this;
		}
		
		@Override
		public ItemStack toStack() {
			ItemStack stack = new ItemStack(item == null ? ModItems.nothing : item, stacksize, meta);
			if(this.nbt != null) stack.stackTagCompound = (NBTTagCompound) nbt.copy();
			return stack;
		}

		/**
		 * For an ItemStack to match an NBTStack, all the rules from ComparableStack apply, with the added condition that all
		 * tags in the NBTStack's NBTTagCompound need to be present and equal the tags of the ItemStack. Any additional tags
		 * the ItemStack has are ignored.
		 */
		@Override
		public boolean matchesRecipe(ItemStack stack, boolean ignoreSize) {
			
			if(stack == null) return false;
			if(stack.getItem() != this.item) return false;
			if(this.meta != OreDictionary.WILDCARD_VALUE && stack.getItemDamage() != this.meta) return false;
			if(!ignoreSize && stack.stackSize < this.stacksize) return false;
			if(this.nbt != null && !this.nbt.hasNoTags() && stack.stackTagCompound == null) return false;
			
			if(this.nbt != null && stack.stackTagCompound != null) {
				Set<String> neededKeys = this.nbt.func_150296_c();
				for(String key : neededKeys) {
					NBTBase tag = stack.stackTagCompound.getTag(key);
					if(tag == null) return false;
					if(!this.nbt.getTag(key).equals(tag)) return false;
				}
			}
			
			return true;
		}

		@Override
		public NBTStack copy() {
			return new NBTStack(item, stacksize, meta).withNBT(nbt != null ? (NBTTagCompound) nbt.copy() : null);
		}

		@Override
		public NBTStack copy(int stacksize) {
			return new NBTStack(item, stacksize, meta).withNBT(nbt != null ? (NBTTagCompound) nbt.copy() : null);
		}
	
		@Override
		public int compareTo(AStack stack) {
			
			if(stack instanceof NBTStack) {
				
				NBTStack comp = (NBTStack) stack;
				
				int thisID = Item.getIdFromItem(item);
				int thatID = Item.getIdFromItem(comp.item);
				
				if(thisID > thatID) return 1;
				if(thatID > thisID) return -1;
				
				if(meta > comp.meta) return 1;
				if(comp.meta > meta) return -1;

				if(nbt != null && comp.nbt == null) return 1;
				if(nbt == null && comp.nbt != null) return -1;
				
				return 0;
			}
			
			//if compared with a CStack, the NBTStack will take priority
			if(stack instanceof ComparableStack) return 1;
			//if compared with an ODStack, the NBTStack will take priority
			if(stack instanceof OreDictStack) return 1;
			
			return 0;
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
			if(stack instanceof ComparableStack) return -1;
			
			return 0;
		}

		@Override
		public OreDictStack copy() {
			return new OreDictStack(name, stacksize);
		}

		@Override
		public OreDictStack copy(int stacksize) {
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
