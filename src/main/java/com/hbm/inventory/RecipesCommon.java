package com.hbm.inventory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.main.DeserializationException;
import com.hbm.main.MainRegistry;

import api.hbm.serialization.Deserializer;
import api.hbm.serialization.ISerializable;
import api.hbm.serialization.SerializationRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesCommon {
	static
	{
		SerializationRegistry.register(ComparableStack.class, ComparableStack.C_DESERIALIZER);
		SerializationRegistry.register(ComparableNBTStack.class, ComparableNBTStack.C_NBT_DESERIALIZER);
		SerializationRegistry.register(OreDictStack.class, OreDictStack.DESERIALIZER);
	}
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
	
	public static abstract class AStack implements Comparable<AStack>, ISerializable<AStack> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -8910069932231358156L;
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
		/** Localized names, so we don't have to read hieroglyphics to figure out what it is **/
		public abstract String getFriendlyName();
		/** Generic getter to itemstack **/
		public abstract ItemStack toStack();
		public abstract void writeToNBT(String key, NBTTagCompound nbt);
		public abstract void writeToNBT(NBTTagCompound nbt);
		
		public static AStack readFromNBT(String key, NBTTagCompound nbt)
		{
			NBTBase tag = nbt.getTag(key);
			if (tag instanceof NBTTagIntArray)
			{
				int[] array = ((NBTTagIntArray) tag).func_150302_c();
				return new ComparableStack(Item.getItemById(array[0]), array[1], array[2]);
			}
			else if (tag instanceof NBTTagCompound)
			{
				NBTTagCompound item = (NBTTagCompound) tag;
				return new OreDictStack(item.getString("NAME"), item.getInteger("SIZE"));
			}
			return null;
		}
		public static AStack readFromNBT(NBTTagCompound nbt)
		{
			return readFromNBT("AStack", nbt);
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

		/**
		 * 
		 */
		private static final long serialVersionUID = 6396702514319374418L;
		public transient Item item;
		public int meta;
		public static final Deserializer<ComparableStack> C_DESERIALIZER = bytes ->
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final byte[] stringBytes = new byte[buf.readInt()];
			buf.readBytes(stringBytes);
			final Item item = (Item) Item.itemRegistry.getObject(new String(stringBytes));
			return new ComparableStack(item, buf.readInt(), buf.readShort());
		};
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
		
		@Override
		public String getFriendlyName()
		{
			return toStack().getDisplayName();
		}
		
		@Override
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
				MainRegistry.logger.error("ComparableStack has a null item! This is a serious issue!");
				Thread.currentThread();
				Thread.dumpStack();
				item = Items.stick;
			}
			
			String name = Item.itemRegistry.getNameForObject(item);
			
			if(name == null) {
				MainRegistry.logger.error("ComparableStack holds an item that does not seem to be registered. How does that even happen?");
				Thread.currentThread();
				Thread.dumpStack();
				item = Items.stick; //we know sticks have a name, so sure, why not
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
		public ComparableStack copy() {
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

		@Override
		public void writeToNBT(String key, NBTTagCompound nbt)
		{
			nbt.setIntArray(key, new int[] {Item.getIdFromItem(item), stacksize, meta});
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt)
		{
			writeToNBT("AStack", nbt);
		}
		
		private void writeObject(ObjectOutputStream stream) throws IOException
		{
			stream.write(serialize());
		}
		
		private void readObject(ObjectInputStream stream) throws IOException
		{
			final byte[] nameBytes = new byte[stream.readInt()];
			stream.read(nameBytes);
			item = (Item) Item.itemRegistry.getObject(new String(nameBytes));
			stacksize = stream.readInt();
			meta = stream.readShort();
		}

		@Override
		public byte[] serialize()
		{
			final ByteBuf buf = alloc.get();
			buf.writeInt(Item.itemRegistry.getNameForObject(item).length());
			buf.writeBytes(Item.itemRegistry.getNameForObject(item).getBytes());
			buf.writeInt(stacksize);
			buf.writeShort(meta);
			return buf.array();
		}

		@Override
		public String toString()
		{
			final StringBuilder builder = new StringBuilder();
			builder.append("ComparableStack [item=").append(item).append(", meta=").append(meta).append(", stacksize=")
					.append(stacksize).append(", getFriendlyName()=").append(getFriendlyName())
					.append(", getDictKeys()=").append(Arrays.toString(getDictKeys())).append(']');
			return builder.toString();
		}

		@Override
		public ComparableStack deserialize(byte[] bytes) throws DeserializationException
		{
			return C_DESERIALIZER.deserialize(bytes);
		}
	}
	
	/*
	 * This implementation does not override the compare function, which effectively makes it ignore stack data.
	 * This is still in line with the use-case of the ComparableNBTStack holding machine output stack information,
	 * since the compare function is not needed. In case the compare function is required, make a new child class.
	 */
	public static class ComparableNBTStack extends ComparableStack {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -8166670902183709354L;
		transient NBTTagCompound nbt;
		public static final Deserializer<ComparableNBTStack> C_NBT_DESERIALIZER = bytes ->
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final byte[] stringBytes = new byte[buf.readInt()];
			buf.readBytes(stringBytes);
			final Item item = (Item) Item.itemRegistry.getObject(new String(stringBytes));
			final int stackSize = buf.readInt();
			final short stackMeta = buf.readShort(), dataLength = buf.readShort();
			if (dataLength <= 0)
				return new ComparableNBTStack(item, stackSize, stackMeta);
			else
			{
				try
				{
					byte[] nbtBytes = new byte[dataLength];
					final NBTTagCompound data = CompressedStreamTools.func_152457_a(nbtBytes, new NBTSizeTracker(2097152L));
					return new ComparableNBTStack(item, stackSize, stackMeta).addNBT(data);
				} catch (IOException e)
				{
					MainRegistry.logger.catching(e);
					return new ComparableNBTStack(item, stackSize, stackMeta);
				}
			}
		};
		public ComparableNBTStack(ItemStack stack) {
			super(stack);
			if (stack.hasTagCompound())
				nbt = stack.getTagCompound();
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
		
		@Override
		public void writeToNBT(String key, NBTTagCompound nbt)
		{
			super.writeToNBT(key, nbt);
			nbt.setTag(key.concat("_NBT"), this.nbt);
		}
		
		@Override
		public boolean matchesRecipe(ItemStack stack, boolean ignoreSize)
		{
			boolean matchesNBT = false;
			if (stack.hasTagCompound() && nbt != null)
				matchesNBT = stack.getTagCompound().equals(nbt);
			else if (!stack.hasTagCompound() && nbt == null)
				matchesNBT = true;
			return super.matchesRecipe(stack, ignoreSize) && matchesNBT;
		}
		
		@Override
		public ItemStack toStack() {
			ItemStack stack = super.toStack();
			stack.stackTagCompound = this.nbt;
			return stack;
		}
		
		@Override
		public byte[] serialize()
		{
			final ByteBuf buf = alloc.get();
			buf.writeInt(Item.itemRegistry.getNameForObject(item).length());
			buf.writeBytes(Item.itemRegistry.getNameForObject(item).getBytes());
			buf.writeInt(stacksize);
			buf.writeShort(meta);
			if (nbt.hasNoTags())
			{
				buf.writeShort(0);
				return buf.array();
			}
			try
			{
				final byte[] nbtBytes = CompressedStreamTools.compress(nbt);
				buf.writeShort(nbtBytes.length);
				buf.writeBytes(nbtBytes);
				return buf.array();
			} catch (IOException e)
			{
				MainRegistry.logger.catching(e);
				buf.writeShort(-1);
				return buf.array();
			}
		}
		
		@Override
		public ComparableNBTStack deserialize(byte[] bytes) throws DeserializationException
		{
			return C_NBT_DESERIALIZER.deserialize(bytes);
		}
		
		private void writeObject(ObjectOutputStream stream) throws IOException
		{
			stream.write(serialize());
		}
		
		private void readObject(ObjectInputStream stream) throws IOException
		{
			final byte[] nameBytes = new byte[stream.readInt()];
			stream.read(nameBytes);
			item = (Item) Item.itemRegistry.getObject(new String(nameBytes));
			stacksize = stream.readInt();
			meta = stream.readShort();
			final short nbtLength = stream.readShort();
			if (nbtLength <= 0)
				nbt = null;
			else
			{
				final byte[] nbtBytes = new byte[nbtLength];
				nbt = CompressedStreamTools.func_152457_a(nbtBytes, new NBTSizeTracker(2097152L));
			}
		}

		@Override
		public String toString()
		{
			final StringBuilder builder = new StringBuilder();
			builder.append("ComparableNBTStack [nbt=").append(nbt).append(", item=").append(item).append(", meta=")
					.append(meta).append(", stacksize=").append(stacksize).append(", getFriendlyName()=")
					.append(getFriendlyName()).append(", getDictKeys()=").append(Arrays.toString(getDictKeys()))
					.append(']');
			return builder.toString();
		}
	}
	
	public static class OreDictStack extends AStack {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -9165927601026982547L;
		public String name;
		public static final Deserializer<OreDictStack> DESERIALIZER = bytes ->
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final byte[] stringBytes = new byte[buf.readInt()];
			buf.readBytes(stringBytes);
			return new OreDictStack(new String(stringBytes), buf.readInt());
		};
		public OreDictStack(String name) {
			this.name = name;
			this.stacksize = 1;
		}
		
		public OreDictStack(String name, int stacksize) {
			this(name);
			this.stacksize = stacksize;
		}
		
		public ArrayList<ItemStack> toStacks() {
			return OreDictionary.getOres(name);
		}

		@Override
		public String getFriendlyName()
		{
			return name;
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
			
			List<ItemStack> ores = OreDictionary.getOres(name);
			
			for(ItemStack stack : ores)
				stack.stackSize = this.stacksize;
			
			return ores;
		}

		@Override
		public ItemStack toStack()
		{
			return toStacks().get(0);
		}

		@Override
		public void writeToNBT(String key, NBTTagCompound nbt)
		{
			NBTTagCompound stack = new NBTTagCompound();
			stack.setString("NAME", name);
			stack.setInteger("SIZE", stacksize);
			nbt.setTag(key, stack);
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt)
		{
			writeToNBT("AStack", nbt);
		}

		@Override
		public byte[] serialize()
		{
			final ByteBuf buf = alloc.get();
			buf.writeInt(name.length());
			buf.writeBytes(name.getBytes());
			buf.writeInt(stacksize);
			return buf.array();
		}

		@Override
		public OreDictStack deserialize(byte[] bytes) throws DeserializationException
		{
			return DESERIALIZER.deserialize(bytes);
		}

		@Override
		public String toString()
		{
			final StringBuilder builder = new StringBuilder();
			builder.append("OreDictStack [name=").append(name).append(", stacksize=").append(stacksize).append(']');
			return builder.toString();
		}
	}
	/**
	 * Converts an ItemStack array to an AStack array
	 * @param stacksIn - The ItemStack to be converted
	 * @param singularize - If the stack sizes should be made 1
	 * @return The requested AStack
	 */
	public static AStack[] itemStackToAStack(ItemStack[] stacksIn, boolean singularize)
	{
		AStack[] newStacks = new AStack[stacksIn.length];
		
		for (int i = 0; i < stacksIn.length; i++)
		{
			if (stacksIn[i] != null)
				newStacks[i] = singularize ? new ComparableStack(stacksIn[i]).makeSingular() : new ComparableStack(stacksIn[i]);
			else
				newStacks[i] = null;
		}
		
		return newStacks;
	}
	public static ComparableStack[] itemStackToCStack(ItemStack[] stacksIn, boolean singularize)
	{
		ComparableStack[] newStacks = new ComparableStack[stacksIn.length];
		
		for (int i = 0; i < stacksIn.length; i++)
		{
			if (stacksIn[i] == null)
				newStacks[i] = null;
			else
				newStacks[i] = singularize ? new ComparableStack(stacksIn[i]).makeSingular() : new ComparableStack(stacksIn[i]);
		}
		
		return newStacks;
	}
	
	public static class MetaBlock {

		public Block block;
		public int meta;
		
		public MetaBlock(Block block, int meta) {
			this.block = block;
			this.meta = meta;
		}
		
		public MetaBlock(Block block) {
			this(block, 0);
		}
		
		public int getID() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Block.getIdFromBlock(block);
			result = prime * result + meta;
			return result;
		}
	}

}
