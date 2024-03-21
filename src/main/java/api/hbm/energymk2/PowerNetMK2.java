package api.hbm.energymk2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import api.hbm.energymk2.Nodespace.PowerNode;

public class PowerNetMK2 {
	
	private boolean valid = true;
	private Set<PowerNode> links = new HashSet();

	/** Maps all active subscribers to a timestamp, handy for handling timeouts. In a good system this shouldn't be necessary, but the previous system taught me to be cautious anyway */
	private HashMap<IEnergyReceiverMK2, Long> subscriberEntries = new HashMap();
	/** A simple set containing all subscribers, might be redundant because of the hashmap, we'll see if we keep this around */
	private Set<IEnergyReceiverMK2> subscriberSet = new HashSet();

	private HashMap<IEnergyProviderMK2, Long> providerEntries = new HashMap();
	private Set<IEnergyProviderMK2> providerSet = new HashSet();

	/// SUBSCRIBER HANDLING ///
	public boolean isSubscribed(IEnergyReceiverMK2 receiver) {
		return this.subscriberSet.contains(receiver);
	}

	public void subscribe(IEnergyReceiverMK2 receiver) {
		this.subscriberSet.add(receiver);
		this.subscriberEntries.put(receiver, System.currentTimeMillis());
	}

	public void unsubscribe(IEnergyReceiverMK2 receiver) {
		this.subscriberSet.remove(receiver);
		this.subscriberEntries.remove(receiver);
	}

	/// PROVIDER HANDLING ///
	public boolean isProvider(IEnergyProviderMK2 provider) {
		return this.providerSet.contains(provider);
	}

	public void addProvider(IEnergyProviderMK2 provider) {
		this.providerSet.add(provider);
		this.providerEntries.put(provider, System.currentTimeMillis());
	}

	public void removeProvider(IEnergyProviderMK2 provider) {
		this.providerSet.remove(provider);
		this.providerEntries.remove(provider);
	}
	
	/// GENERAL POWER NET CONTROL ///
	public void invalidate() {
		this.valid = false;
	}
}
