package com.hbm.uninos.networks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.uninos.IGenProvider;
import com.hbm.uninos.IGenReceiver;
import com.hbm.uninos.NodeNet;
import com.hbm.uninos.networkproviders.PowerProvider;
import com.hbm.util.Tuple.Pair;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;

public class PowerNetwork extends NodeNet<PowerProvider> {

	public HashMap<IEnergyReceiverMK2, Long> receiverEntries = new HashMap();
	public HashMap<IEnergyProviderMK2, Long> providerEntries = new HashMap();
	
	public long energyTracker = 0L;
	
	public void resetEnergyTracker() {
		this.energyTracker = 0;
	}

	@Override // this was all fun and games but let's take a few steps back: this generics stuff is kinda breaking shit, and as it turns out, apparently nothing even uses the type
	public HashMap<IGenReceiver<PowerProvider>, Long> receiverEntries() {
		return null;
	}

	@Override // therefore i should probably consider scrapping the majority of the generic types - they seem to be kinda useless with the current approach
	public HashMap<IGenProvider<PowerProvider>, Long> providerEntries() {
		return null;
	}
	
	protected static int timeout = 3_000;
	
	public void update() {
		
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
}
