package api.hbm.ntl;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StorageStack implements Comparable {

	private int cachedItemId;
	private ItemStack type;
	private long amount;
	
	public StorageStack(ItemStack type) {
		this(type, type.stackSize);
		this.cachedItemId = Item.getIdFromItem(type.getItem());
	}
	
	public StorageStack(ItemStack type, long amount) {
		this.type = type.copy();
		this.amount = amount;
		this.type.stackSize = 0;
	}
	
	public ItemStack getType() {
		return this.type.copy();
	}
	
	public long getAmount() {
		return this.amount;
	}

	@Override
	public int compareTo(Object o) {
		StorageStack other = (StorageStack) o;

		if(this.cachedItemId < other.cachedItemId) return -1;
		if(this.cachedItemId > other.cachedItemId) return 1;
		if(this.type.getItemDamage() < other.type.getItemDamage()) return -1;
		if(this.type.getItemDamage() > other.type.getItemDamage()) return 1;
		if(this.type.hasTagCompound() && !other.type.hasTagCompound()) return -1;
		if(!this.type.hasTagCompound() && other.type.hasTagCompound()) return 1;
		if(this.type.hasTagCompound() && other.type.hasTagCompound()) {
			// keyset size comparison should hopefully catch most of the larger NBT cases
			if(this.type.stackTagCompound.func_150296_c().size() < other.type.stackTagCompound.func_150296_c().size()) return -1;
			if(this.type.stackTagCompound.func_150296_c().size() > other.type.stackTagCompound.func_150296_c().size()) return 1;
			int comp = this.type.stackTagCompound.toString().compareTo(other.type.stackTagCompound.toString()); // not terribly performant but hopefully not that common
			if(comp != 0) return comp;
		}
		if(this.type.stackSize < other.type.stackSize) return -1;
		if(this.type.stackSize > other.type.stackSize) return 1;
		
		return 0;
	}
}
