package api.hbm.ntl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
		
		NBTTagCompound compound = stack.hasTagCompound() ? (NBTTagCompound) stack.stackTagCompound.copy() : null;
		long amount = nbt.nbtAmount.containsKey(compound) ? nbt.nbtAmount.get(compound) : 0;
		
		amount += stack.stackSize;
		
		nbt.nbtAmount.put(compound, amount);
	}
	
	public List<StorageStack> getStacks() {
		List<StorageStack> stacks = new ArrayList();
		
		for(Entry<Integer, MetaNode> itemNode : itemMeta.entrySet()) {
			for(Entry<Integer, NBTNode> metaNode : itemNode.getValue().metaNBT.entrySet()) {
				for(Entry<NBTTagCompound, Long> nbtNode : metaNode.getValue().nbtAmount.entrySet()) {
					
					ItemStack itemStack = new ItemStack(Item.getItemById(itemNode.getKey()), 1, metaNode.getKey());
					itemStack.stackTagCompound = nbtNode.getKey();
					StorageStack stack = new StorageStack(itemStack, nbtNode.getValue());
					stacks.add(stack);
				}
			}
		}
		
		return stacks;
	}
	
	public class MetaNode {
		
		public HashMap<Integer, NBTNode> metaNBT = new HashMap();
	}
	
	public class NBTNode {

		public HashMap<NBTTagCompound, Long> nbtAmount = new HashMap();
	}
}
