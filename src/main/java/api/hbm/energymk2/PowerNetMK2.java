package api.hbm.energymk2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.hbm.util.Tuple.Pair;

import java.util.Map.Entry;
import java.util.Random;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;
import api.hbm.energymk2.Nodespace.PowerNode;

public class PowerNetMK2 {
	
	public static Random rand = new Random();
	public boolean valid = true;
	public Set<PowerNode> links = new HashSet();

	/** Maps all active subscribers to a timestamp, handy for handling timeouts. In a good system this shouldn't be necessary, but the previous system taught me to be cautious anyway */
	public HashMap<IEnergyReceiverMK2, Long> receiverEntries = new HashMap();
	public HashMap<IEnergyProviderMK2, Long> providerEntries = new HashMap();
	
	public long energyTracker = 0L;
	
	public PowerNetMK2() {
		Nodespace.activePowerNets.add(this);
	}

	/// SUBSCRIBER HANDLING ///
	public boolean isSubscribed(IEnergyReceiverMK2 receiver) {
		return this.receiverEntries.containsKey(receiver);
	}

	public void addReceiver(IEnergyReceiverMK2 receiver) {
		this.receiverEntries.put(receiver, System.currentTimeMillis());
	}

	public void removeReceiver(IEnergyReceiverMK2 receiver) {
		this.receiverEntries.remove(receiver);
	}

	/// PROVIDER HANDLING ///
	public boolean isProvider(IEnergyProviderMK2 provider) {
		return this.providerEntries.containsKey(provider);
	}

	public void addProvider(IEnergyProviderMK2 provider) {
		this.providerEntries.put(provider, System.currentTimeMillis());
	}

	public void removeProvider(IEnergyProviderMK2 provider) {
		this.providerEntries.remove(provider);
	}
	
	/// LINK JOINING ///
	
	/** Combines two networks into one */
	public void joinNetworks(PowerNetMK2 network) {
		
		if(network == this) return; //wtf?!

		List<PowerNode> oldNodes = new ArrayList(network.links.size());
		oldNodes.addAll(network.links); // might prevent oddities related to joining - nvm it does nothing
		
		for(PowerNode conductor : oldNodes) forceJoinLink(conductor);
		network.links.clear();

		for(IEnergyReceiverMK2 connector : network.receiverEntries.keySet()) this.addReceiver(connector);
		for(IEnergyProviderMK2 connector : network.providerEntries.keySet()) this.addProvider(connector);
		network.destroy();
	}

	/** Adds the power node as part of this network's links */
	public PowerNetMK2 joinLink(PowerNode node) {
		if(node.net != null) node.net.leaveLink(node);
		return forceJoinLink(node);
	}

	/** Adds the power node as part of this network's links, skips the part about removing it from existing networks */
	public PowerNetMK2 forceJoinLink(PowerNode node) {
		this.links.add(node);
		node.setNet(this);
		return this;
	}

	/** Removes the specified power node */
	public void leaveLink(PowerNode node) {
		node.setNet(null);
		this.links.remove(node);
	}
	
	/// GENERAL POWER NET CONTROL ///
	public void invalidate() {
		this.valid = false;
		Nodespace.activePowerNets.remove(this);
	}
	
	public boolean isValid() {
		return this.valid;
	}
	
	public void destroy() {
		this.invalidate();
		for(PowerNode link : this.links) if(link.net == this) link.setNet(null);
		this.links.clear();
		this.receiverEntries.clear();
		this.providerEntries.clear();
	}
	
	public void resetEnergyTracker() {
		this.energyTracker = 0;
	}
	
	protected static int timeout = 3_000;
	
	public void transferPower() {
		
		if(providerEntries.isEmpty()) return;
		if(receiverEntries.isEmpty()) return;
		
		long timestamp = System.currentTimeMillis();
		
		List<Pair<IEnergyProviderMK2, Long>> providers = new ArrayList();
		long powerAvailable = 0;
		
		Iterator<Entry<IEnergyProviderMK2, Long>> provIt = providerEntries.entrySet().iterator();
		while(provIt.hasNext()) {
			Entry<IEnergyProviderMK2, Long> entry = provIt.next();
			if(timestamp - entry.getValue() > timeout) { provIt.remove(); continue; }
			long src = Math.min(entry.getKey().getPower(), entry.getKey().getProviderSpeed());
			providers.add(new Pair(entry.getKey(), src));
			powerAvailable += src;
		}
		
		List<Pair<IEnergyReceiverMK2, Long>>[] receivers = new ArrayList[ConnectionPriority.values().length];
		for(int i = 0; i < receivers.length; i++) receivers[i] = new ArrayList();
		long[] demand = new long[ConnectionPriority.values().length];
		long totalDemand = 0;

		Iterator<Entry<IEnergyReceiverMK2, Long>> recIt = receiverEntries.entrySet().iterator();
		
		while(recIt.hasNext()) {
			Entry<IEnergyReceiverMK2, Long> entry = recIt.next();
			if(timestamp - entry.getValue() > timeout) { recIt.remove(); continue; }
			long rec = Math.min(entry.getKey().getMaxPower() - entry.getKey().getPower(), entry.getKey().getReceiverSpeed());
			int p = entry.getKey().getPriority().ordinal();
			receivers[p].add(new Pair(entry.getKey(), rec));
			demand[p] += rec;
			totalDemand += rec;
		}

		long toTransfer = Math.min(powerAvailable, totalDemand);
		long energyUsed = 0;
		
		for(int i = ConnectionPriority.values().length - 1; i >= 0; i--) {
			List<Pair<IEnergyReceiverMK2, Long>> list = receivers[i];
			long priorityDemand = demand[i];
			
			for(Pair<IEnergyReceiverMK2, Long> entry : list) {
				double weight = (double) entry.getValue() / (double) (priorityDemand);
				long toSend = (long) Math.max(toTransfer * weight, 0D);
				energyUsed += (toSend - entry.getKey().transferPower(toSend)); //leftovers are subtracted from the intended amount to use up
			}
			
			toTransfer -= energyUsed;
		}
		
		this.energyTracker += energyUsed;
		long leftover = energyUsed;

		for(Pair<IEnergyProviderMK2, Long> entry : providers) {
			double weight = (double) entry.getValue() / (double) powerAvailable;
			long toUse = (long) Math.max(energyUsed * weight, 0D);
			entry.getKey().usePower(toUse);
			leftover -= toUse;
		}
		
		//rounding error compensation, detects surplus that hasn't been used and removes it from random providers
		int iterationsLeft = 100; // whiles without emergency brakes are a bad idea
		while(iterationsLeft > 0 && leftover > 0 && providers.size() > 0) {
			iterationsLeft--;
			
			Pair<IEnergyProviderMK2, Long> selected = providers.get(rand.nextInt(providers.size()));
			IEnergyProviderMK2 scapegoat = selected.getKey();
			
			long toUse = Math.min(leftover, scapegoat.getPower());
			scapegoat.usePower(toUse);
			leftover -= toUse;
		}
	}
	
	@Deprecated public void transferPowerOld() {
		
		if(providerEntries.isEmpty()) return;
		if(receiverEntries.isEmpty()) return;
		
		long timestamp = System.currentTimeMillis();
		long transferCap = 100_000_000_000_000_00L; // that ought to be enough

		long supply = 0;
		long demand = 0;
		long[] priorityDemand = new long[ConnectionPriority.values().length];
		
		Iterator<Entry<IEnergyProviderMK2, Long>> provIt = providerEntries.entrySet().iterator();
		while(provIt.hasNext()) {
			Entry<IEnergyProviderMK2, Long> entry = provIt.next();
			if(timestamp - entry.getValue() > timeout) { provIt.remove(); continue; }
			supply += Math.min(entry.getKey().getPower(), entry.getKey().getProviderSpeed());
		}
		
		if(supply <= 0) return;

		Iterator<Entry<IEnergyReceiverMK2, Long>> recIt = receiverEntries.entrySet().iterator();
		while(recIt.hasNext()) {
			Entry<IEnergyReceiverMK2, Long> entry = recIt.next();
			if(timestamp - entry.getValue() > timeout) { recIt.remove(); continue; }
			long rec = Math.min(entry.getKey().getMaxPower() - entry.getKey().getPower(), entry.getKey().getReceiverSpeed());
			demand += rec;
			for(int i = 0; i <= entry.getKey().getPriority().ordinal(); i++) priorityDemand[i] += rec;
		}
		
		if(demand <= 0) return;
		
		long toTransfer = Math.min(supply, demand);
		if(toTransfer > transferCap) toTransfer = transferCap;
		if(toTransfer <= 0) return;
		
		List<IEnergyProviderMK2> buffers = new ArrayList();
		List<IEnergyProviderMK2> providers = new ArrayList();
		Set<IEnergyReceiverMK2> receiverSet = receiverEntries.keySet();
		for(IEnergyProviderMK2 provider : providerEntries.keySet()) {
			if(receiverSet.contains(provider)) {
				buffers.add(provider);
			} else {
				providers.add(provider);
			}
		}
		providers.addAll(buffers); //makes buffers go last
		List<IEnergyReceiverMK2> receivers = new ArrayList() {{ addAll(receiverSet); }};
		
		receivers.sort(COMP);
		
		int maxIteration = 1000;
		
		//how much the current sender/receiver have already sent/received
		long prevSrc = 0;
		long prevDest = 0;
		
		while(!receivers.isEmpty() && !providers.isEmpty() && maxIteration > 0) {
			maxIteration--;
			
			IEnergyProviderMK2 src = providers.get(0);
			IEnergyReceiverMK2 dest = receivers.get(0);

			if(src.getPower() <= 0) { providers.remove(0); prevSrc = 0; continue; }
			
			if(src == dest) { // STALEMATE DETECTED
				//if this happens, a buffer will waste both its share of transfer and receiving potential and do effectively nothing, essentially breaking
				
				//try if placing the conflicting provider at the end of the list does anything
				//we do this first because providers have no priority, so we may shuffle those around as much as we want
				if(providers.size() > 1) {
					providers.add(providers.get(0));
					providers.remove(0);
					prevSrc = 0; //this might cause slight issues due to the tracking being effectively lost while there still might be pending operations
					continue;
				}
				//if that didn't work, try shifting the receiver by one place (to minimize priority breakage)
				if(receivers.size() > 1) {
					receivers.add(2, receivers.get(0));
					receivers.remove(0);
					prevDest = 0; //ditto
					continue;
				}
				
				//if neither option could be performed, the only conclusion is that this buffer mode battery is alone in the power net, in which case: not my provlem
			}
			
			long pd = priorityDemand[dest.getPriority().ordinal()];
			
			long receiverShare = Math.min((long) Math.ceil((double) Math.min(dest.getMaxPower() - dest.getPower(), dest.getReceiverSpeed()) * (double) supply / (double) pd), dest.getReceiverSpeed()) - prevDest;
			long providerShare = Math.min((long) Math.ceil((double) Math.min(src.getPower(), src.getProviderSpeed()) * (double) demand / (double) supply), src.getProviderSpeed()) - prevSrc;
			
			long toDrain = Math.min((long) (src.getPower()), providerShare);
			long toFill = Math.min(dest.getMaxPower() - dest.getPower(), receiverShare);
			
			long finalTransfer = Math.min(toDrain, toFill);
			if(toFill <= 0) { receivers.remove(0); prevDest = 0; continue; }
			
			finalTransfer -= dest.transferPower(finalTransfer);
			src.usePower(finalTransfer);
			
			prevSrc += finalTransfer;
			prevDest += finalTransfer;

			if(prevSrc >= src.getProviderSpeed()) { providers.remove(0); prevSrc = 0; continue; }
			if(prevDest >= dest.getReceiverSpeed()) { receivers.remove(0); prevDest = 0; continue; }
			
			toTransfer -= finalTransfer;
			this.energyTracker += finalTransfer;
		}
	}
	
	public long sendPowerDiode(long power) {
		
		if(receiverEntries.isEmpty()) return power;
		
		long timestamp = System.currentTimeMillis();
		
		List<Pair<IEnergyReceiverMK2, Long>>[] receivers = new ArrayList[ConnectionPriority.values().length];
		for(int i = 0; i < receivers.length; i++) receivers[i] = new ArrayList();
		long[] demand = new long[ConnectionPriority.values().length];
		long totalDemand = 0;

		Iterator<Entry<IEnergyReceiverMK2, Long>> recIt = receiverEntries.entrySet().iterator();
		
		while(recIt.hasNext()) {
			Entry<IEnergyReceiverMK2, Long> entry = recIt.next();
			if(timestamp - entry.getValue() > timeout) { recIt.remove(); continue; }
			long rec = Math.min(entry.getKey().getMaxPower() - entry.getKey().getPower(), entry.getKey().getReceiverSpeed());
			int p = entry.getKey().getPriority().ordinal();
			receivers[p].add(new Pair(entry.getKey(), rec));
			demand[p] += rec;
			totalDemand += rec;
		}

		long toTransfer = Math.min(power, totalDemand);
		long energyUsed = 0;
		
		for(int i = ConnectionPriority.values().length - 1; i >= 0; i--) {
			List<Pair<IEnergyReceiverMK2, Long>> list = receivers[i];
			long priorityDemand = demand[i];
			
			for(Pair<IEnergyReceiverMK2, Long> entry : list) {
				double weight = (double) entry.getValue() / (double) (priorityDemand);
				long toSend = (long) Math.max(toTransfer * weight, 0D);
				energyUsed += (toSend - entry.getKey().transferPower(toSend)); //leftovers are subtracted from the intended amount to use up
			}
			
			toTransfer -= energyUsed;
		}
		
		this.energyTracker += energyUsed;
		
		return power - energyUsed;
	}
	
	public static final ReceiverComparator COMP = new ReceiverComparator();
	
	public static class ReceiverComparator implements Comparator<IEnergyReceiverMK2> {

		@Override
		public int compare(IEnergyReceiverMK2 o1, IEnergyReceiverMK2 o2) {
			return o2.getPriority().ordinal() - o1.getPriority().ordinal();
		}
	}
}
