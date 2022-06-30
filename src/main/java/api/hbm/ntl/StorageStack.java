package api.hbm.ntl;

import net.minecraft.item.ItemStack;

public class StorageStack {

	private ItemStack type;
	private long amount;
	
	public StorageStack(ItemStack type) {
		this(type, type.stackSize);
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
}
