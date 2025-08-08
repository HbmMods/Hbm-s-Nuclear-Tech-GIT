package com.hbm.uninos.networkproviders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.hbm.tileentity.machine.TileEntityMachineAutocrafter;
import com.hbm.tileentity.network.TileEntityPneumoTube;
import com.hbm.uninos.NodeNet;
import com.hbm.util.BobMathUtil;
import com.hbm.util.ItemStackUtil;
import com.hbm.util.Tuple.Pair;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class PneumaticNetwork extends NodeNet {

	public static final byte SEND_FIRST = 0;
	public static final byte SEND_LAST = 1;
	public static final byte SEND_RANDOM = 2;
	public static final byte RECEIVE_ROBIN = 0;
	public static final byte RECEIVE_RANDOM = 1;
	
	public Random rand = new Random();
	public int nextReceiver = 0;

	protected static final int timeout = 1_000;
	public static final int ITEMS_PER_TRANSFER = 64;
	
	// while the system has parts that expects IInventires to be TileEntities to work properly (mostly range checks),
	// it can actually handle non-TileEntities just fine.
	public HashMap<IInventory, Pair<ForgeDirection, Long>> receivers = new HashMap();
	
	public void addReceiver(IInventory inventory, ForgeDirection pipeDir) {
		receivers.put(inventory, new Pair(pipeDir, System.currentTimeMillis()));
	}

	@Override public void update() {

		// weeds out invalid targets
		// technically not necessary since that step is taken during the send operation,
		// but we still want to reap garbage data that would otherwise accumulate
		long timestamp = System.currentTimeMillis();
		receivers.entrySet().removeIf(x -> { return (timestamp - x.getValue().getValue() > timeout) || NodeNet.isBadLink(x.getKey()); });
	}
	
	public boolean send(IInventory source, TileEntityPneumoTube tube, ForgeDirection accessDir, int sendOrder, int receiveOrder, int maxRange) {

		// turns out there may be a short time window where the cleanup hasn't happened yet, but chunkloading has already caused tiles to go invalid
		// so we just run it again here, just to be sure.
		long timestamp = System.currentTimeMillis();
		receivers.entrySet().removeIf(x -> { return (timestamp - x.getValue().getValue() > timeout) || NodeNet.isBadLink(x.getKey()); });
		
		if(receivers.isEmpty()) return false;

		int sourceSide = accessDir.ordinal();
		int[] sourceSlotAccess = getSlotAccess(source, sourceSide);

		if(sendOrder == SEND_LAST) BobMathUtil.reverseIntArray(sourceSlotAccess);
		if(sendOrder == SEND_RANDOM) BobMathUtil.shuffleIntArray(sourceSlotAccess);

		// for round robin, receivers are ordered by proximity to the source
		ReceiverComparator comparator = new ReceiverComparator(tube);
		List<Entry<IInventory, Pair<ForgeDirection, Long>>> receiverList = new ArrayList(receivers.size());
		receiverList.addAll(receivers.entrySet());
		receiverList.sort(comparator);
		
		int index = nextReceiver % receivers.size();
		Entry<IInventory, Pair<ForgeDirection, Long>> chosenReceiverEntry = null;
		nextReceiver++;

		if(receiveOrder == RECEIVE_ROBIN) chosenReceiverEntry = receiverList.get(index);
		if(receiveOrder == RECEIVE_RANDOM) chosenReceiverEntry = receiverList.get(rand.nextInt(receiverList.size()));
		
		if(chosenReceiverEntry == null) return false;
		
		IInventory dest = chosenReceiverEntry.getKey();
		ISidedInventory sidedDest = dest instanceof ISidedInventory ? (ISidedInventory) dest : null;
		ISidedInventory sidedSource = source instanceof ISidedInventory ? (ISidedInventory) source : null;
		
		TileEntity tile1 = source instanceof TileEntity ? (TileEntity) source : null;
		TileEntity tile2 = dest instanceof TileEntity ? (TileEntity) dest : null;
		
		// range check for our compression level, skip if either source or dest aren't tile entities
		if(tile1 != null && tile2 != null) {
			int sq = (tile1.xCoord - tile2.xCoord) * (tile1.xCoord - tile2.xCoord) + (tile1.yCoord - tile2.yCoord) * (tile1.yCoord - tile2.yCoord) + (tile1.zCoord - tile2.zCoord) * (tile1.zCoord - tile2.zCoord);
			if(sq > maxRange * maxRange) {
				return false;
			}
		}
		
		int destSide = chosenReceiverEntry.getValue().getKey().getOpposite().ordinal();
		int[] destSlotAccess = getSlotAccess(dest, destSide);
		int itemsLeftToSend = ITEMS_PER_TRANSFER; // not actually individual items, but rather the total "mass", based on max stack size
		int itemHardCap = dest instanceof TileEntityMachineAutocrafter ? 1 : ITEMS_PER_TRANSFER;
		boolean didSomething = false;
		
		for(int sourceIndex : sourceSlotAccess) {
			ItemStack sourceStack = source.getStackInSlot(sourceIndex);
			if(sourceStack == null) continue;
			if(sidedSource != null && !sidedSource.canExtractItem(sourceIndex, sourceStack, sourceSide)) continue;
			boolean match = tube.matchesFilter(sourceStack);
			if((match && !tube.whitelist) || (!match && tube.whitelist)) continue;
			// the "mass" of an item. something that only stacks to 4 has a "mass" of 16. max transfer mass is 64, i.e. one standard stack, or one single unstackable item
			int proportionalValue = MathHelper.clamp_int(64 / sourceStack.getMaxStackSize(), 1, 64);
			
			// try to fill partial stacks first
			for(int destIndex : destSlotAccess) {
				ItemStack destStack = dest.getStackInSlot(destIndex);
				if(destStack == null) continue;
				if(!ItemStackUtil.areStacksCompatible(sourceStack, destStack)) continue;
				int toMove = BobMathUtil.min(sourceStack.stackSize, destStack.getMaxStackSize() - destStack.stackSize, dest.getInventoryStackLimit() - destStack.stackSize, itemsLeftToSend / proportionalValue, itemHardCap);
				if(toMove <= 0) continue;
				
				ItemStack checkStack = destStack.copy();
				checkStack.stackSize += toMove;
				if(!dest.isItemValidForSlot(destIndex, checkStack)) continue;
				if(sidedDest != null && !sidedDest.canInsertItem(destIndex, checkStack, destSide)) continue;
				
				sourceStack.stackSize -= toMove;
				if(sourceStack.stackSize <= 0) source.setInventorySlotContents(sourceIndex, null);
				destStack.stackSize += toMove;
				itemsLeftToSend -= toMove * proportionalValue;
				didSomething = true;
				if(itemsLeftToSend <= 0) break;
			}
			
			// if there's stuff left to send, occupy empty slots
			if(itemsLeftToSend > 0 && sourceStack.stackSize > 0) for(int destIndex : destSlotAccess) {
				if(dest.getStackInSlot(destIndex) != null) continue;
				int toMove = BobMathUtil.min(sourceStack.stackSize, dest.getInventoryStackLimit(), itemsLeftToSend / proportionalValue, itemHardCap);
				if(toMove <= 0) continue;
				
				ItemStack checkStack = sourceStack.copy();
				checkStack.stackSize = toMove;
				if(!dest.isItemValidForSlot(destIndex, checkStack)) continue;
				if(sidedDest != null && !sidedDest.canInsertItem(destIndex, checkStack, destSide)) continue;

				ItemStack newStack = sourceStack.copy();
				newStack.stackSize = toMove;
				sourceStack.stackSize -= toMove;
				if(sourceStack.stackSize <= 0) source.setInventorySlotContents(sourceIndex, null);
				dest.setInventorySlotContents(destIndex, newStack);
				itemsLeftToSend -= toMove * proportionalValue;
				didSomething = true;
				if(itemsLeftToSend <= 0) break;
			}
			
			if(itemsLeftToSend <= 0) break;
		}
		
		// make sure both parties are saved to disk and increment the counter for round robin
		if(didSomething) {
			source.markDirty();
			dest.markDirty();
		}
		
		return didSomething;
	}
	
	/** Returns an array of accessible slots from the given side of an IInventory. If it's an ISidedInventory, uses the sided restrictions instead. */
	public static int[] getSlotAccess(IInventory inventory, int dir) {
		
		if(inventory instanceof ISidedInventory) {
			int[] slotAccess = ((ISidedInventory) inventory).getAccessibleSlotsFromSide(dir);
			return Arrays.copyOf(slotAccess, slotAccess.length); //we mess with the order, so better not use the original array
		} else {
			int[] slotAccess = new int[inventory.getSizeInventory()];
			for(int i = 0; i < inventory.getSizeInventory(); i++) slotAccess[i] = i;
			return slotAccess;
		}
	}
	
	/** Compares IInventory by distance, going off the assumption that they are TileEntities. Uses positional data for tie-breaking if the distance is the same. */
	public static class ReceiverComparator implements Comparator<Entry<IInventory, Pair<ForgeDirection, Long>>> {
		
		private TileEntityPneumoTube origin;
		
		public ReceiverComparator(TileEntityPneumoTube origin) {
			this.origin = origin;
		}

		@Override
		public int compare(Entry<IInventory, Pair<ForgeDirection, Long>> o1, Entry<IInventory, Pair<ForgeDirection, Long>> o2) {

			TileEntity tile1 = o1.getKey() instanceof TileEntity ? (TileEntity) o1.getKey() : null;
			TileEntity tile2 = o2.getKey() instanceof TileEntity ? (TileEntity) o2.getKey() : null;

			// prioritize actual TileEntities
			if(tile1 == null && tile2 != null) return 1;
			if(tile1 != null && tile2 == null) return -1;
			if(tile1 == null && tile2 == null) return 0;
			
			// calculate distances from origin
			int dist1 = (tile1.xCoord - origin.xCoord) * (tile1.xCoord - origin.xCoord) + (tile1.yCoord - origin.yCoord) * (tile1.yCoord - origin.yCoord) + (tile1.zCoord - origin.zCoord) * (tile1.zCoord - origin.zCoord);
			int dist2 = (tile2.xCoord - origin.xCoord) * (tile2.xCoord - origin.xCoord) + (tile2.yCoord - origin.yCoord) * (tile2.yCoord - origin.yCoord) + (tile2.zCoord - origin.zCoord) * (tile2.zCoord - origin.zCoord);
			
			// tier-breaker: use hash value instead
			if(dist1 == dist2) {
				return TileEntityPneumoTube.getIdentifier(tile1.xCoord, tile1.yCoord, tile1.zCoord) - TileEntityPneumoTube.getIdentifier(tile2.xCoord, tile2.yCoord, tile2.zCoord);
			}
			
			// no tie? return difference of the distances
			return dist1 - dist2;
		}
	}
}
