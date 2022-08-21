package api.hbm.ntl;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StorageManifest {

	public HashMap<Integer, MetaNode> itemMeta = new HashMap();
	
	public void writeStack(ItemStack stack) {
		int id = Item.getIdFromItem(stack.getItem());
		
		MetaNode meta = itemMeta.get(id);
		
		if(meta == null) {
			meta = new MetaNode();
			itemMeta.put(id, meta);
		}
		
		NBTNode nbt = meta.metaNBT.get(stack.getItemDamage());
		
		if(nbt == null) {
			nbt = new NBTNode();
			meta.metaNBT.put(stack.getItemDamage(), nbt);
		}
		
		long amount = nbt.nbtAmount.containsKey(stack.stackTagCompound) ? nbt.nbtAmount.get(stack.stackTagCompound) : 0;
		
		amount += stack.stackSize;
		
		nbt.nbtAmount.put(stack.stackTagCompound, amount);
	}
	
	public class MetaNode {
		
		public HashMap<Integer, NBTNode> metaNBT = new HashMap();
	}
	
	public class NBTNode {

		public HashMap<NBTTagCompound, Long> nbtAmount = new HashMap();
	}
}
