package com.hbm.uninos.networkproviders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.tileentity.network.TileEntityPneumoTube;
import com.hbm.uninos.NodeNet;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple.Pair;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class PneumaticNetwork extends NodeNet {

	public static final byte SEND_FIRST = 0;
	public static final byte SEND_LAST = 1;
	public static final byte SEND_RANDOM = 2;
	public static final byte RECEIVE_ROBIN = 0;
	public static final byte RECEIVE_RANDOM = 1;
	
	public int nextReceiver = 0;

	protected static int timeout = 1_000;
	
	public HashMap<IInventory, Pair<ForgeDirection, Long>> receivers = new HashMap();
	
	public void addReceiver(IInventory inventory, ForgeDirection pipeDir) {
		receivers.put(inventory, new Pair(pipeDir, System.currentTimeMillis()));
	}

	@Override public void update() {

		long timestamp = System.currentTimeMillis();
		receivers.entrySet().removeIf(x -> { return (timestamp - x.getValue().getValue() > timeout) || NodeNet.isBadLink(x.getKey()); });
	}
	
	public boolean send(IInventory source, TileEntityPneumoTube tube, ForgeDirection dir, int sendOrder, int receiveOrder) {
		
		if(receivers.isEmpty()) return false;
		
		int[] slotAccess;
		
		if(source instanceof ISidedInventory) {
			ISidedInventory sided = (ISidedInventory) source;
			slotAccess = sided.getAccessibleSlotsFromSide(dir.ordinal());
		} else {
			slotAccess = new int[source.getSizeInventory()];
			for(int i = 0; i < source.getSizeInventory(); i++) slotAccess[i] = i;
		}

		if(sendOrder == SEND_LAST) BobMathUtil.reverseIntArray(slotAccess);
		if(sendOrder == SEND_RANDOM) BobMathUtil.shuffleIntArray(slotAccess);
		
		nextReceiver++;

		ReceiverComparator comparator = new ReceiverComparator(tube);
		List<Entry<IInventory, Pair<ForgeDirection, Long>>> receiverList = new ArrayList(receivers.size());
		receiverList.addAll(receivers.entrySet());
		receiverList.sort(comparator);
		
		int index = nextReceiver % receivers.size();
		Entry<IInventory, Pair<ForgeDirection, Long>> chosenReceiverEntry = receiverList.get(index);
		
		//TBI - the painful part
		
		return false;
	}
	
	public static class ReceiverComparator implements Comparator<Entry<IInventory, Pair<ForgeDirection, Long>>> {
		
		private TileEntityPneumoTube origin;
		
		public ReceiverComparator(TileEntityPneumoTube origin) {
			this.origin = origin;
		}

		@Override
		public int compare(Entry<IInventory, Pair<ForgeDirection, Long>> o1, Entry<IInventory, Pair<ForgeDirection, Long>> o2) {

			TileEntity tile1 = o1.getKey() instanceof TileEntity ? (TileEntity) o1.getKey() : null;
			TileEntity tile2 = o2.getKey() instanceof TileEntity ? (TileEntity) o2.getKey() : null;

			//prioritize actual TileEntities
			if(tile1 == null && tile2 != null) return 1;
			if(tile1 != null && tile2 == null) return -1;
			if(tile1 == null && tile2 == null) return 0;
			
			//calculate distances from origin
			int dist1 = (tile1.xCoord - origin.xCoord) * (tile1.xCoord - origin.xCoord) + (tile1.yCoord - origin.yCoord) * (tile1.yCoord - origin.yCoord) + (tile1.zCoord - origin.zCoord) * (tile1.zCoord - origin.zCoord);
			int dist2 = (tile2.xCoord - origin.xCoord) * (tile2.xCoord - origin.xCoord) + (tile2.yCoord - origin.yCoord) * (tile2.yCoord - origin.yCoord) + (tile2.zCoord - origin.zCoord) * (tile2.zCoord - origin.zCoord);
			
			//tier-breaker: use hash value instead
			if(dist1 == dist2) {
				return TileEntityPneumoTube.getIdentifier(tile1.xCoord, tile1.yCoord, tile1.zCoord) - TileEntityPneumoTube.getIdentifier(tile2.xCoord, tile2.yCoord, tile2.zCoord);
			}
			
			//no tie? return difference of the distances
			return dist1 - dist2;
		}
	}
}
