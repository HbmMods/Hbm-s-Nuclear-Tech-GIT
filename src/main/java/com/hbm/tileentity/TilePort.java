package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.NodeNet;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * TilePort - each machine now has actual UNINOS nodes for ports, eliminating the need for in-world checks.
 * Connections are done once when the machine is placed or when the port type changes.
 * There are no timeouts or constant reconnects. Yay.
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
	protected int timeSinceNetworkChange = 0;
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
	
	// this will break the instant there are two ports on the same block, since one overrides the other, and this code does not support multiple directions on the same
	// source block. this means that the original getConPos will cease to function entirely. have to rethink that.
	@Deprecated
	public TilePort setupPositionsLegacy(DirPos... pos) {
		this.positions = new BlockPos[pos.length];
		for(int i = 0; i < this.positions.length; i++) {
			this.positions[i] = pos[i].offset(pos[i].getDir(), -1);
		}
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

		NodeNet current = this.node != null ? this.node.net : null;
		if(current != this.prevNet) {
			this.timeSinceNetworkChange = 0;
			// random bullshit go!
			if(this.prevNet != null) this.prevNet.removeReceiver(owner);
			if(this.prevNet != null) this.prevNet.removeProvider(owner);
		} else {
			this.timeSinceNetworkChange++;
		}
		
		this.prevNet = this.node != null ? this.node.net : null;
		
		if(this.needsRebuild) {
			disableIfPresent(world);
			this.needsRebuild = false;
		}
		
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
		this.node.setConnections(connections);
		UniNodespace.createNode(world, this.node);
	}
	
	public void checkSubscribe() {
		if(this.node == null) return;
		
		if(timeSinceNetworkChange < 2) {
			if(this.node.net != null) this.node.net.addReceiver(owner);
		}
	}
	
	public void checkProvide() {
		if(this.node == null) return;

		if(timeSinceNetworkChange < 2) {
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
	
	/** Turns one PortDef into an array of ports with identical positions and connections. Ideal for passthrough fluid connections */
	public static TilePort[] oneToMany(Object owner, int portCount, PortDef def) {
		TilePort[] ports = new TilePort[portCount];
		for(int i = 0; i < portCount; i++) {
			ports[i] = new TilePort().setupOwner(owner).setupPositions(def.portPositions).setupConnections(def.portConnections);
		}
		return ports;
	}
	/** Turns an array of PortDefs into an array of ports with individual positions and connections. Ideal for non-passthrough power ports. */
	public static TilePort[] manyToMany(Object owner, PortDef... defs) {
		TilePort[] ports = new TilePort[defs.length];
		for(int i = 0; i < defs.length; i++) {
			ports[i] = new TilePort().setupOwner(owner).setupPositions(defs[i].portPositions).setupConnections(defs[i].portConnections);
		}
		return ports;
	}
	
	public static class PortDef {
		public BlockPos[] portPositions;
		public DirPos[] portConnections;
		
		public PortDef atPos(BlockPos... pos) {
			this.portPositions = pos;
			return this;
		}
		
		public PortDef withCon(DirPos... pos) {
			this.portConnections = pos;
			return this;
		}
		
		public static PortDef make(int x, int y, int z, ForgeDirection... dirs) {
			DirPos[] cons = new DirPos[dirs.length];
			PortDef def = new PortDef().atPos(new BlockPos(x, y, z));
			for(int i = 0; i < cons.length; i++) {
				cons[i] = new DirPos(x + dirs[i].offsetX, y + dirs[i].offsetY, z + dirs[i].offsetZ, dirs[i]);
			}
			def.withCon(cons);
			return def;
		}
		
		public static PortDef combine(PortDef... defs) {
			PortDef single = new PortDef();
			List<BlockPos> pos = new ArrayList();
			List<DirPos> con = new ArrayList();
			for(PortDef def : defs) {
				for(BlockPos p : def.portPositions) pos.add(p);
				for(DirPos c : def.portConnections) con.add(c);
			}
			single.atPos(pos.toArray(new BlockPos[0])).withCon(con.toArray(new DirPos[0]));
			return single;
		}
	}
}
