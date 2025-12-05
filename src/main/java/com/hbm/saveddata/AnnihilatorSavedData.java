package com.hbm.saveddata;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.util.ItemStackUtil;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class AnnihilatorSavedData extends WorldSavedData {

	public static final String KEY = "annihilator";
	
	public HashMap<String, AnnihilatorPool> pools = new HashMap();
	
	public AnnihilatorSavedData() {
		super(KEY);
		this.markDirty();
	}

	public AnnihilatorSavedData(String name) { super(name); }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		//NBTTagList list = nbt.getTagList(p_150295_1_, p_150295_2_)
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList list = new NBTTagList();
		
		for(Entry<String, AnnihilatorPool> entry : pools.entrySet()) {
			list.appendTag(new NBTTagString(entry.getKey()));
			NBTTagList poolList = new NBTTagList();
			entry.getValue().serialize(poolList);
			list.appendTag(poolList);
		}
		
		nbt.setTag("list", list);
	}
	
	public static AnnihilatorSavedData getData(World worldObj) {
		AnnihilatorSavedData data = (AnnihilatorSavedData) worldObj.perWorldStorage.loadData(AnnihilatorSavedData.class, KEY);
		if(data == null) {
			data = new AnnihilatorSavedData();
			worldObj.perWorldStorage.setData(KEY, data);
		}
		return data;
	}
	
	public AnnihilatorPool grabPool(String pool) {
		AnnihilatorPool poolInstance = pools.get(pool);
		if(poolInstance == null) {
			poolInstance = new AnnihilatorPool();
			pools.put(pool, poolInstance);
		}
		return poolInstance;
	}
	
	/** For fluids */
	public void pushToPool(String pool, FluidType type, long amount) {
		AnnihilatorPool poolInstance = grabPool(pool);
		poolInstance.increment(type, amount);
		this.markDirty();
	}
	
	/** For items (type + meta as well as only type), also handles ore dict */
	public void pushToPool(String pool, ItemStack stack) {
		AnnihilatorPool poolInstance = grabPool(pool);

		poolInstance.increment(stack.getItem(), stack.stackSize);
		poolInstance.increment(new ComparableStack(stack).makeSingular(), stack.stackSize);
		
		List<String> oreDict = ItemStackUtil.getOreDictNames(stack);
		for(String name : oreDict) poolInstance.increment(name, stack.stackSize);
		
		// originally a lookup for fluid containers was considered, but no one would use the annihilator like that, and it adds unnecessary overhead
		
		this.markDirty();
	}
	
	public static class AnnihilatorPool {
		
		/**
		 * Valid keys include:
		 * <ul>
		 * <li>Items, for wildcard</li>
		 * <li>ComparableStacks, for type + meta</li>
		 * <li>FluidTypes</li>
		 * <li>Strings, for ore dict keys</li>
		 * </ul>
		 * ItemStacks should be analyzed for all matching types and added accordingly. Any additions need to be added for (de)serializing
		 */
		public HashMap<Object, BigInteger> items = new HashMap();
		
		public void increment(Object type, long amount) {
			BigInteger counter = items.get(type);
			if(counter == null) {
				counter = BigInteger.valueOf(amount);
			} else {
				counter.add(BigInteger.valueOf(amount));
			}
			items.put(type, counter);
		}
		
		public void serialize(NBTTagList nbt) {
			for(Entry<Object, BigInteger> entry : items.entrySet()) {
				NBTTagList list = new NBTTagList();
				serializeKey(list, entry.getKey());
				list.appendTag(new NBTTagByteArray(entry.getValue().toByteArray()));
				nbt.appendTag(list);
			}
		}
		
		/*
		 * this absolutely will not work because it was written under the assumption that NBTTagList can support arbitrary NBTTagBase
		 * even though an NBTTagList can only hold tags of a single type. this fucking sucks and implementing it better would have
		 * been easy, but we work with what we are given.
		 * 
		 * alternative: keep the lists, but change all types to NBTTagByteArray since we can effectively reduce all other data
		 * types down into byte arrays anyway. if we can avoid NBTTagCompounds, we will. so much named tag crap we don't need just
		 * bloats the file size.
		 */
		public void serializeKey(NBTTagList list, Object key) {
			if(key instanceof Item) { // 0
				Item item = (Item) key;
				list.appendTag(new NBTTagByte((byte) 0));
				list.appendTag(new NBTTagString(Item.itemRegistry.getNameForObject(item)));
			}
			
			if(key instanceof ComparableStack) { // 1
				ComparableStack item = (ComparableStack) key;
				list.appendTag(new NBTTagByte((byte) 1));
				list.appendTag(new NBTTagString(Item.itemRegistry.getNameForObject(item.item)));
				list.appendTag(new NBTTagShort((short) item.meta));
			}
			
			if(key instanceof FluidType) { // 2
				FluidType item = (FluidType) key;
				list.appendTag(new NBTTagByte((byte) 2));
				list.appendTag(new NBTTagString(item.getUnlocalizedName()));
			}
			
			if(key instanceof String) { // 3
				String item = (String) key;
				list.appendTag(new NBTTagByte((byte)3));
				list.appendTag(new NBTTagString(item));
			}
		}
	}
}
