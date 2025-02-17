package com.hbm.uninos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public abstract class NodeNet<T extends INetworkProvider> {
	
	public static Random rand = new Random();
	
	public boolean valid = true;
	public Set<GenNode<T>> links = new HashSet();

	public abstract HashMap<IGenReceiver<T>, Long> receiverEntries();
	public abstract HashMap<IGenProvider<T>, Long> providerEntries();
	
	public NodeNet() {
		UniNodespace.activeNodeNets.add(this);
	}

	/// SUBSCRIBER HANDLING ///
	public boolean isSubscribed(IGenReceiver<T> receiver) { return this.receiverEntries().containsKey(receiver); }
	public void addReceiver(IGenReceiver<T> receiver) { this.receiverEntries().put(receiver, System.currentTimeMillis()); }
	public void removeReceiver(IGenReceiver<T> receiver) { this.receiverEntries().remove(receiver); }

	/// PROVIDER HANDLING ///
	public boolean isProvider(IGenProvider<T> provider) { return this.providerEntries().containsKey(provider); }
	public void addProvider(IGenProvider<T> provider) { this.providerEntries().put(provider, System.currentTimeMillis()); }
	public void removeProvider(IGenProvider<T> provider) { this.providerEntries().remove(provider); }
	
	/** Combines two networks into one */
	public void joinNetworks(NodeNet<T> network) {
		if(network == this) return;

		List<GenNode<T>> oldNodes = new ArrayList(network.links.size());
		oldNodes.addAll(network.links);
		
		for(GenNode<T> conductor : oldNodes) forceJoinLink(conductor);
		network.links.clear();

		for(IGenReceiver<T> connector : network.receiverEntries().keySet()) this.addReceiver(connector);
		for(IGenProvider<T> connector : network.providerEntries().keySet()) this.addProvider(connector);
		network.destroy();
	}

	/** Adds the node as part of this network's links */
	public NodeNet<T> joinLink(GenNode<T> node) {
		if(node.net != null) node.net.leaveLink(node);
		return forceJoinLink(node);
	}

	/** Adds the node as part of this network's links, skips the part about removing it from existing networks */
	public NodeNet<T> forceJoinLink(GenNode<T> node) {
		this.links.add(node);
		node.setNet(this);
		return this;
	}

	/** Removes the specified node */
	public void leaveLink(GenNode<T> node) {
		node.setNet(null);
		this.links.remove(node);
	}
	
	/// GENERAL POWER NET CONTROL ///
	public void invalidate() { this.valid = false; UniNodespace.activeNodeNets.remove(this); }
	public boolean isValid() { return this.valid; }
	public abstract void update();
	
	public void destroy() {
		this.invalidate();
		for(GenNode<T> link : this.links) if(link.net == this) link.setNet(null);
		this.links.clear();
		this.receiverEntries().clear();
		this.providerEntries().clear();
	}
}
