package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.NodeNet;
import com.hbm.uninos.UniNodespace;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
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

	// hijack ports will only wrap around existing nodes, and cannot create their own or destroy any nodes
	protected boolean isHijackPort = false;
	protected boolean needsRebuild = false;
	protected boolean isEnabled = true;
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
	
	public TilePort setupPort(PortDef def) {
		return this.setupPositions(def.portPositions).setupConnections(def.portConnections);
	}
	
	/** Ideally only run this once, ports shouldn't change position (there is no handling for that unless a rebuild is forced). Multiple positions for one port means that this is a passthrough port group */
	public TilePort setupPositions(BlockPos... pos) {
		this.positions = pos;
		this.needsRebuild = true;
		return this;
	}
	
	/** Creates a hijack port which can only connect to existing nodes.
	 * This becomes necessary for blocks where in and output nodes would land on the same position, but that have to explicitly not connect. */
	public TilePort setHijack() {
		this.isHijackPort = true;
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
			if(this.isHijackPort) checkHijack();
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
			if(!this.isHijackPort) this.createNode(world);
		}
	}
	
	protected void disableIfPresent(World world) {
		if(this.node != null) {
			if(!this.isHijackPort) UniNodespace.destroyNode(world, node);
			this.node = null;
		}
	}
	
	protected void createNode(World world) {
		this.node = this.type.provideNode(positions);
		this.node.setConnections(connections);
		UniNodespace.createNode(world, this.node);
	}
	
	// check if any of the connections match, if not then release the hijacked node
	protected void checkHijack() {
		if(this.node != null && !this.node.expired) {
			
			for(DirPos dir : this.connections) {
				if(UniNodespace.checkConnection(this.node, dir, false)) return;
			}
			
			this.node = null;
		}
	}
	
	public void checkSubscribe(World world) {
		if(this.node == null) return;
		
		if(timeSinceNetworkChange < 2) {
			if(this.node.net != null) this.node.net.addReceiver(owner);
		}
		
		if(TileEntityLoadedBase.particleDebug) for(DirPos pos : this.connections) {
			ForgeDirection dir = pos.getDir();
			BlockPos offset = pos.offset(dir);

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "network");
			data.setString("mode", "power");
			double posX = offset.getX() + 0.5 - dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posY = offset.getY() + 0.5 - dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posZ = offset.getZ() + 0.5 - dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			data.setDouble("mX", dir.offsetX * -0.1);
			data.setDouble("mY", dir.offsetY * -0.1);
			data.setDouble("mZ", dir.offsetZ * -0.1);
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
		}
	}
	
	public void checkProvide(World world) {
		if(this.node == null) return;

		if(timeSinceNetworkChange < 2) {
			if(this.node.net != null) this.node.net.addProvider(owner);
		}
		
		if(TileEntityLoadedBase.particleDebug) for(DirPos pos : this.connections) {
			ForgeDirection dir = pos.getDir();

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "network");
			data.setString("mode", "power");
			double posX = pos.getX() + 0.5 - dir.offsetX * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posY = pos.getY() + 0.5 - dir.offsetY * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			double posZ = pos.getZ() + 0.5 - dir.offsetZ * 0.5 + world.rand.nextDouble() * 0.5 - 0.25;
			data.setDouble("mX", dir.offsetX * 0.1);
			data.setDouble("mY", dir.offsetY * 0.1);
			data.setDouble("mZ", dir.offsetZ * 0.1);
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 25));
		}
	}
	
	public void forceRebuild() {
		this.needsRebuild = true;
	}

	public void enable() { if(!isEnabled) { isEnabled = true; this.forceRebuild(); } }
	public void disable() { if(isEnabled) { isEnabled = false; this.forceRebuild(); } }
	
	/** The port node is considered active if the netprov is not null and not the NONE fluid */
	protected boolean isEnabled() {
		if(!isEnabled) return false;
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
