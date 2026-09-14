package com.hbm.tileentity;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.NodeNet;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.world.World;

/**
 * TilePort - each machine now has actual UNINOS nodes for ports, eliminating the need for in-world checks.
 * Connections are done once when the machine is placed or when the port type changes.
 * There are no timeouts or constant reconnects.
 * 
 * @author hbm
 */
public class TilePort {

	// generic ports now work with literally anything that supports UNINOS
	protected GenNode node;
	// for checking if the network has changed and managing subscribes
	protected NodeNet prevNet;
	protected INetworkProvider type;
	protected BlockPos[] positions;
	protected DirPos[] connections;
	
	protected boolean needsRebuild = false;
	// usually a tile entity, can be a delegate/proxy type object too
	protected Object owner;
	
	/** For the love of god don't change this */
	public TilePort setupOwner(Object owner) {
		this.owner = owner;
		return this;
	}
	
	/** This may change over the tile's lifetime, null and NONE types will disable this port (group) */
	public TilePort setupType(INetworkProvider type) {
		if(this.type != type) {
			this.type = type;
			this.needsRebuild = true;
		}
		return this;
	}
	
	/** Ideally only run this once, ports shouldn't change position (there is no handling for that unless a rebuild is forced). Multiple positions for one port means that this is a passthrough port group */
	public TilePort setupPositions(BlockPos... pos) {
		this.positions = pos;
		this.needsRebuild = true;
		return this;
	}

	/** Ideally only run this once, ports shouldn't change connectivity (there is no handling for that unless a rebuild is forced) */
	public TilePort setupConnections(DirPos... pos) {
		this.connections = pos;
		this.needsRebuild = true;
		return this;
	}
	
	public void update(World world) {
		if(this.positions == null || this.connections == null || this.owner == null) return; // skip ticking if setup isn't complete
		// if positions or connections are nulled, ticks are skipped
		// if a node already exists, this may cause unintended behavior, so don't do that

		this.prevNet = this.node != null ? this.node.net : null;
		
		if(this.needsRebuild) disableIfPresent(world);
		
		// wording so clear and 8 year old could understand it
		if(isEnabled()) {
			enableIfMissing(world);
		} else {
			disableIfPresent(world);
		}
	}
	
	protected void enableIfMissing(World world) {
		if(this.node == null || this.node.expired) {
			BlockPos pos = positions[0];
			this.node = UniNodespace.getNode(world, pos.getX(), pos.getY(), pos.getZ(), type);
		}
		if(this.node == null || this.node.expired) {
			this.createNode(world);
		}
	}
	
	protected void disableIfPresent(World world) {
		if(this.node != null) UniNodespace.destroyNode(world, node);
	}
	
	protected void createNode(World world) {
		this.node = this.type.provideNode(positions);
		UniNodespace.createNode(world, this.node);
	}
	
	public void checkSubscribe() {
		if(this.node == null) return;
		
		if(this.prevNet != this.node.net) {
			if(this.prevNet != null) this.prevNet.removeReceiver(owner);
			if(this.node.net != null) this.node.net.addReceiver(owner);
		}
	}
	
	public void checkProvide() {
		if(this.node == null) return;
		
		if(this.prevNet != this.node.net) {
			if(this.prevNet != null) this.prevNet.removeProvider(owner);
			if(this.node.net != null) this.node.net.addProvider(owner);
		}
	}
	
	public void forceRebuild() {
		this.needsRebuild = true;
	}
	
	/** The port node is considered active if the netprov is not null and not the NONE fluid */
	protected boolean isEnabled() {
		if(type == null) return false;
		if(type == Fluids.NONE.getNetworkProvider()) return false;
		return true;
	}
}
