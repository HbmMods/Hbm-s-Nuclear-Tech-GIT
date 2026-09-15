package com.hbm.uninos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import api.hbm.tile.ILoadedTile;
import net.minecraft.tileentity.TileEntity;

public abstract class NodeNet<R, P, L extends GenNode> {
	
	/** Global random for figuring things out like random leftover distribution */
	public static Random rand = new Random();
	
	public boolean valid = true;
	public Set<L> links = new LinkedHashSet();

	public Set<R> receiverEntries = new HashSet();
	public Set<P> providerEntries = new HashSet();
	
	public NodeNet() {
		UniNodespace.activeNodeNets.add(this);
	}

	/// SUBSCRIBER HANDLING ///
	public boolean isSubscribed(R receiver) { return this.receiverEntries.contains(receiver); }
	public void addReceiver(R receiver) { this.receiverEntries.add(receiver); }
	public void removeReceiver(R receiver) { this.receiverEntries.remove(receiver); }

	/// PROVIDER HANDLING ///
	public boolean isProvider(P provider) { return this.providerEntries.contains(provider); }
	public void addProvider(P provider) { this.providerEntries.add(provider); }
	public void removeProvider(P provider) { this.providerEntries.remove(provider); }
	
	/** Combines two networks into one */
	public void joinNetworks(NodeNet network) {
		if(network == this) return;

		List<L> oldNodes = new ArrayList(network.links.size());
		oldNodes.addAll(network.links);
		
		for(L conductor : oldNodes) forceJoinLink(conductor);
		network.links.clear();

		for(Object /*this is bullshit*/ connector : network.receiverEntries) this.addReceiver((R) connector);
		for(Object /*this is bullshit*/ connector : network.providerEntries) this.addProvider((P) connector);
		network.destroy();
	}

	/** Adds the node as part of this network's links */
	public NodeNet joinLink(L node) {
		if(node.net != null) node.net.leaveLink(node);
		return forceJoinLink(node);
	}

	/** Adds the node as part of this network's links, skips the part about removing it from existing networks */
	public NodeNet forceJoinLink(L node) {
		this.links.add(node);
		node.setNet(this);
		return this;
	}

	/** Removes the specified node */
	public void leaveLink(L node) {
		node.setNet(null);
		this.links.remove(node);
	}
	
	/// GENERAL POWER NET CONTROL ///
	public void invalidate() { this.valid = false; UniNodespace.activeNodeNets.remove(this); }
	public boolean isValid() { return this.valid; }
	public void resetTrackers() { }
	public abstract void update();
	
	public void destroy() {
		this.invalidate();
		for(GenNode link : this.links) if(link.net == this) link.setNet(null);
		this.links.clear();
		this.receiverEntries.clear();
		this.providerEntries.clear();
	}
	
	public static boolean isBadLink(Object o) {
		if(o instanceof ILoadedTile && !((ILoadedTile) o).isLoaded()) return true;
		if(o instanceof TileEntity && ((TileEntity) o).isInvalid()) return true;
		return false;
	}
}
