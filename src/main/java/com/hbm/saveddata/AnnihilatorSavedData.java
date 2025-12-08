package com.hbm.saveddata;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.util.ItemStackUtil;
import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

@NotableComments
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
		NBTTagList pools = nbt.getTagList("pools", 10);
		
		for(int i = 0; i < pools.tagCount(); i++) {
			NBTTagCompound poolCompound = pools.getCompoundTagAt(i);
			
			String poolName = poolCompound.getString("poolname");
			AnnihilatorPool pool = new AnnihilatorPool();
			pool.deserialize(poolCompound.getTagList("pool", 7));
			
			this.pools.put(poolName, pool);
		}
	}
	
	/*
	 * woah nelly!
	 * 
	 * ROOT NBT
	 *  \
	 *   POOLS LIST(COMPOUND) - all pools
	 *    \
	 *     POOL COMPOUND - pool + name association
	 *      \
	 *       POOLNAME STRING
	 *       POOL LIST(LIST) - all item entries in a pool
	 *        \
	 *         PER-ITEM LIST(BYTEARRAY) - all data associated with one item
	 *          \
	 *           KEY ID
	 *           KEY BYTES
	 *           POOL SIZE BYTES
	 */

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		
		NBTTagList pools = new NBTTagList();
		
		for(Entry<String, AnnihilatorPool> entry : this.pools.entrySet()) {
			NBTTagCompound pool = new NBTTagCompound();
			NBTTagList poolList = new NBTTagList();
			
			entry.getValue().serialize(poolList);
			pool.setString("poolname", entry.getKey());
			pool.setTag("pool", poolList);
			pools.appendTag(pool);
		}
		
		nbt.setTag("pools", pools);
	}
	
	public static AnnihilatorSavedData getData(World worldObj) {
		AnnihilatorSavedData data = (AnnihilatorSavedData) worldObj.perWorldStorage.loadData(AnnihilatorSavedData.class, KEY);
		if(data == null) {
			worldObj.perWorldStorage.setData(KEY, new AnnihilatorSavedData());
			data = (AnnihilatorSavedData) worldObj.perWorldStorage.loadData(AnnihilatorSavedData.class, KEY);
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
		for(String name : oreDict) if(name != null && !name.isEmpty()) poolInstance.increment(name, stack.stackSize); // because some assholes pollute the ore dict with crap values
		
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
				counter = counter.add(BigInteger.valueOf(amount));
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
		
		public void deserialize(NBTTagList nbt) {
			try {
				for(int i = 0; i < nbt.tagCount(); i++) {
					NBTTagList list = (NBTTagList) nbt.tagList.get(i);
					Object key = deserializeKey(list);
					NBTTagByteArray ntba = (NBTTagByteArray) list.tagList.get(list.tagList.size() - 1);
					if(key != null) this.items.put(key, new BigInteger(ntba.func_150292_c()));
				}
			} catch(Throwable ex) { } // because world data can be dented to all fucking hell and back
		}
		
		/** So we want to avoid NBTTagCompounds because the keys are basically useless here and Strings are heavy as shit.
		 * So what do? Shrimple, we use NBTTagLists. However, Mojang never expected lists to use different types, even though
		 * implementing a list like that would be really easy, so we just break down absolutely all information we have into
		 * byte arrays because the NBTTagList can handle those. God I hate this. */
		public void serializeKey(NBTTagList list, Object key) {
			if(key instanceof Item) { // 0
				Item item = (Item) key;
				list.appendTag(new NBTTagByteArray(new byte[] {0}));
				list.appendTag(new NBTTagByteArray(Item.itemRegistry.getNameForObject(item).getBytes()));
			}
			
			if(key instanceof ComparableStack) { // 1
				ComparableStack item = (ComparableStack) key;
				list.appendTag(new NBTTagByteArray(new byte[] {1}));
				list.appendTag(new NBTTagByteArray(Item.itemRegistry.getNameForObject(item.item).getBytes()));
				short meta = (short) item.meta;
				list.appendTag(new NBTTagByteArray(new byte[] {
						(byte) ((meta & 0xFF00) << 8),
						(byte) (meta & 0x00FF)
						})); // HSB and LSB may not be split "fairly" due to sign bit, but we do not care, we just want to store bits
			}
			
			if(key instanceof FluidType) { // 2
				FluidType item = (FluidType) key;
				list.appendTag(new NBTTagByteArray(new byte[] {2}));
				list.appendTag(new NBTTagByteArray(item.getUnlocalizedName().getBytes()));
			}
			
			if(key instanceof String) { // 3
				String item = (String) key;
				list.appendTag(new NBTTagByteArray(new byte[] {3}));
				list.appendTag(new NBTTagByteArray(item.getBytes()));
			}
		}
		
		public Object deserializeKey(NBTTagList list) {
			try {
				int size = list.tagCount();
				if(size <= 0) return null;
				byte key = ((NBTTagByteArray) list.tagList.get(0)).func_150292_c()[0]; // i am pissing myself from all these assumptions
				
				if(key == 0) { // item
					byte[] bytes = ((NBTTagByteArray) list.tagList.get(1)).func_150292_c();
					Item item = (Item) Item.itemRegistry.getObject(new String(bytes)); // not specifying a charset is probably dangerous for multiple 
					// reasons but given that i don't really think the charset should change serverside, i *think* this should work
					return item;
				}
				if(key == 1) { // comparablestack
					byte[] itembytes = ((NBTTagByteArray) list.tagList.get(1)).func_150292_c();
					byte[] metabytes = ((NBTTagByteArray) list.tagList.get(2)).func_150292_c();
					Item item = (Item) Item.itemRegistry.getObject(new String(itembytes));
					//short hsb = (short) (((short) metabytes[0]) << 8);
					//short lsb = (short) metabytes[1];
					short meta = (short) ((metabytes[0]  << 8) | (metabytes[1] & 0xFF));
					return new ComparableStack(item, 1, meta);
				}
				if(key == 2) { // fluidtype
					byte[] bytes = ((NBTTagByteArray) list.tagList.get(1)).func_150292_c();
					FluidType type = Fluids.fromName(new String(bytes));
					return type;
				}
				if(key == 3) {
					byte[] bytes = ((NBTTagByteArray) list.tagList.get(1)).func_150292_c();
					String type = new String(bytes);
					return type;
				}
				// i feel filthy
				
			} catch(Throwable ex) { }
			return null;
		}
	}
}
