package com.hbm.handler.rbmkmk2;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.util.fauxpointtwelve.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RBMKHandler {

	static double moderatorEfficiency;
	static double reflectorEfficiency;
	static double absorberEfficiency;
	static int columnHeight;
	static int fluxRange;

	public enum RBMKType {
		ROD,
		MODERATOR,
		CONTROL_ROD,
		REFLECTOR,
		ABSORBER,
		OUTGASSER,
		OTHER // why do neutron calculations on them if they won't change anything?
	}

	private static TileEntity blockPosToTE(World worldObj, BlockPos pos) {
		return worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
	}

	public static class RBMKNode {

		protected RBMKType type;
		protected TileEntityRBMKBase tile;
		protected boolean hasLid;

		public RBMKNode(TileEntityRBMKBase tile, RBMKType type) {
			this.type = type;
			this.tile = tile;
			this.hasLid = tile.hasLid();
		}

		public RBMKNode(TileEntityRBMKBase tile, RBMKType type, boolean hasLid) {
			this.type = type;
			this.tile = tile;
			this.hasLid = hasLid;
		}

		public void addLid() {
			this.hasLid = true;
		}

		public void removeLid() {
			this.hasLid = false;
		}

		public List<BlockPos> getReaSimNodes() {
			List<BlockPos> list = new ArrayList<>();
			for (int x = -fluxRange; x <= fluxRange; x++)
				for (int z = -fluxRange; z <= fluxRange; z++)
					if (Math.pow(x, 2) + Math.pow(z, 2) <= fluxRange * fluxRange)
						list.add(new BlockPos(tile).add(x, 0, z));
			return list;
		}

		public List<BlockPos> checkNode() {

			List<BlockPos> list = new ArrayList<>();

			BlockPos pos = new BlockPos(this.tile);

			List<NeutronStream> streams = new ArrayList<>();

			// Simulate streams coming out of the RBMK rod.
			for (ForgeDirection dir : TileEntityRBMKRod.fluxDirs) {
				streams.add(new NeutronStream(this, Vec3.createVectorHelper(dir.offsetX, 0, dir.offsetZ)));
			}

			// Check if the rod should uncache nodes.
			if (tile instanceof TileEntityRBMKRod && !(tile instanceof TileEntityRBMKRodReaSim)) {
				TileEntityRBMKRod rod = (TileEntityRBMKRod) tile;
				if (!rod.hasRod || rod.lastFluxQuantity == 0) {

					for (NeutronStream stream : streams) {
						stream.getNodes(false).forEach(node -> list.add(new BlockPos(node.tile)));
					}

					return list;
				}
			}

			List<BlockPos> points = getReaSimNodes();

			// Check if the ReaSim rod should be culled from the cache due to no rod or no flux.
			if (tile instanceof TileEntityRBMKRodReaSim) { // fuckkkkkkk
				TileEntityRBMKRodReaSim rod = (TileEntityRBMKRodReaSim) tile;
				if (!rod.hasRod || rod.lastFluxQuantity == 0) {
					list.addAll(points);
					return list;
				}
			}

			// Check if non-rod nodes should be uncached... but now with ReaSim!
			{ //  Yeah, I don't want to contaminate the surrounding scope.
				List<RBMKNode> nodes = new ArrayList<>();
				points.forEach(nodePos -> {
					RBMKNode node = getNode(nodePos);
					if (node != null)
						nodes.add(node);
				});

				boolean hasRod = false;

				for (RBMKNode node : nodes) {

					if (node.tile instanceof TileEntityRBMKRod) {

						TileEntityRBMKRod rod = (TileEntityRBMKRod) node.tile;

						if (rod.hasRod && rod.lastFluxQuantity > 0) {
							hasRod = true;
							break;
						}
					}
				}

				if (nodes.isEmpty() || !hasRod) {
					list.add(pos);
					return list;
				}
			}

			// Check if non-rod nodes should be uncached due to no rod in range.
			for (NeutronStream stream : streams) {

				List<RBMKNode> nodes = stream.getNodes(false);

				for (RBMKNode node : nodes) {
					if (node.tile instanceof TileEntityRBMKRod)
						return list;
				}
			}

			// If we get here, then no rods were found along this stream's path!
			// This, most of the time, means we can just uncache all the nodes inside the stream's path.
			// That other part of the time, streams will be crossing paths.
			// This is fine though, we can just uncache them anyway and the streams later on (next tick) will recache them.
			//  /\ idk what this guy was on about but this is just plain wrong. /\
			list.add(pos);

			return list;
		}
	}

	public static RBMKNode makeNode(TileEntityRBMKBase tile) {
		BlockPos pos = new BlockPos(tile);
		if (nodeCache.containsKey(pos))
			return getNode(pos);
		if (!tile.hasWorldObj())
			return new RBMKNode(tile, tile.getRBMKType(), true);
		return new RBMKNode(tile, tile.getRBMKType());
	}

	public static class StreamWorld {

		List<NeutronStream> streams;

		public StreamWorld() {
			streams = new ArrayList<>();
		}

		public void addStream(NeutronStream stream) {
			this.streams.add(stream);
		}

		public void removeAllStreams() {
			this.streams.clear();
		}
	}

	public static class NeutronStream {

		public RBMKNode origin;

		// doubles!!
		public double fluxQuantity;
		// Hey, new implementation! Basically a ratio for slow flux to fast flux
		// 0 = all slow flux
		// 1 = all fast flux
		public double fluxRatio;

		// Vector for direction of neutron flow.
		public Vec3 vector;

		// Primarily used as a "dummy stream", not to be added to the streams list.
		public NeutronStream(RBMKNode origin, Vec3 vector) {
			this.origin = origin;
			this.vector = vector;
		}

		public NeutronStream(RBMKNode origin, Vec3 vector, double flux, double ratio) {
			this.origin = origin;
			this.vector = vector;
			this.fluxQuantity = flux;
			this.fluxRatio = ratio;
			World worldObj = origin.tile.getWorldObj();
			if (streamWorlds.get(worldObj) == null) {
				StreamWorld world = new StreamWorld();
				world.addStream(this);
				streamWorlds.put(worldObj, world);
			} else
				streamWorlds.get(worldObj).addStream(this);
		}

		// USES THE CACHE!!!
		public List<BlockPos> getBlocks() {
			List<BlockPos> positions = new ArrayList<>();

			for (int i = 1; i <= fluxRange; i++) {
				int x = (int) Math.floor(0.5 + vector.xCoord * i);
				int z = (int) Math.floor(0.5 + vector.zCoord * i);

				BlockPos pos = new BlockPos(origin.tile).add(x, 0, z);
				positions.add(pos);
			}
			return positions;
		}

		// Does NOT include the origin node
		// USES THE CACHE!!!
		public List<RBMKNode> getNodes(boolean addNode) {
			List<RBMKNode> positions = new ArrayList<>();

			for (int i = 1; i <= fluxRange; i++) {
				int x = (int) Math.floor(0.5 + vector.xCoord * i);
				int z = (int) Math.floor(0.5 + vector.zCoord * i);

				BlockPos pos = new BlockPos(origin.tile).add(x, 0, z);

				if (nodeCache.containsKey(pos))
					positions.add(getNode(pos));

				else if (this.origin.tile.getBlockType() instanceof RBMKBase) {
					TileEntity te = blockPosToTE(this.origin.tile.getWorldObj(), pos);
					if (te instanceof TileEntityRBMKBase) {
						TileEntityRBMKBase rbmkBase = (TileEntityRBMKBase)te;
						RBMKNode node = makeNode(rbmkBase);
						positions.add(node);
						if (addNode)
							addNode(node);
					}
				}
			}
			return positions;
		}

		// The... small one? whatever it's still pretty big, runs the interaction for the stream.
		public void runStreamInteraction(World worldObj) {

			// do nothing if there's nothing to do lmao
			if(fluxQuantity == 0D)
				return;

			BlockPos pos = new BlockPos(origin.tile);

			TileEntityRBMKBase originTE;

			if (nodeCache.containsKey(pos))
				originTE = nodeCache.get(pos).tile;
			else {
				originTE = (TileEntityRBMKBase) blockPosToTE(worldObj, pos);
				if(originTE == null)
					return; // Doesn't exist anymore!
				addNode(new RBMKNode(originTE, originTE.getRBMKType()));
			}

			int moderatedCount = 0;

			for(BlockPos nodePos : getBlocks()) {

				if(fluxQuantity == 0D) // Whoops, used it all up!
					return;

				RBMKNode node = nodeCache.get(nodePos);

				if(node == null) {
					TileEntity te = blockPosToTE(worldObj, nodePos); // ok, maybe it didn't get added to the list somehow??
					if (te instanceof TileEntityRBMKBase) {
						node = makeNode((TileEntityRBMKBase) te);
						addNode(node); // whoops!
					} else {
						int hits = getHits(nodePos); // Get the amount of hits on blocks.
						if (hits == columnHeight) // If stream is fully blocked.
							return;
						else if (hits > 0) { // If stream is partially blocked.
							irradiateFromFlux(pos, hits);
							fluxQuantity *= 1 - ((double) hits / columnHeight); // Inverse to get partial blocking by blocks.
							continue;
						} else { // Nothing hit!
							irradiateFromFlux(pos, 0);
							continue;
						}
					}
				}

				if(node.type == RBMKType.OTHER) // pass right on by!
					continue;

				// we established earlier during `getNodes()` that they should all be RBMKBase TEs
				// no issue with casting here!
				TileEntityRBMKBase nodeTE = node.tile;

				if(!node.hasLid)
					ChunkRadiationManager.proxy.incrementRad(worldObj, nodePos.getX(), nodePos.getY(), nodePos.getZ(), (float) (this.fluxQuantity * 0.05F));

				if (node.type == RBMKType.MODERATOR || nodeTE.isModerated()) {
					moderatedCount++;
					moderateStream();
				}

				if (nodeTE instanceof IRBMKFluxReceiver) {
					IRBMKFluxReceiver column = (IRBMKFluxReceiver)nodeTE;

					if (node.type == RBMKType.ROD) {
						TileEntityRBMKRod rod = (TileEntityRBMKRod)column;

						if (rod.hasRod) {
							rod.receiveFlux(this);
							return;
						}

					} else if(node.type == RBMKType.OUTGASSER) {
						TileEntityRBMKOutgasser outgasser = ((TileEntityRBMKOutgasser) column);

						if(outgasser.canProcess()) {
							column.receiveFlux(this);
							return;
						}
					}

				} else if (node.type == RBMKType.CONTROL_ROD) {
					TileEntityRBMKControl rod = (TileEntityRBMKControl) nodeTE;

					if (rod.level > 0.0D) {

						this.fluxQuantity *= rod.getMult();
						continue;
					}
					return;
				} else if (node.type == RBMKType.REFLECTOR) {

					if (this.origin.tile.isModerated())
						moderatedCount++;

					if (this.fluxRatio > 0 && moderatedCount > 0)
						for (int i = 0; i < moderatedCount; i++)
							moderateStream();

					if (RBMKHandler.reflectorEfficiency != 1.0D) {
						this.fluxQuantity *= RBMKHandler.reflectorEfficiency;
						continue;
					}

					((TileEntityRBMKRod)originTE).receiveFlux(this);
					return;
				} else if (node.type == RBMKType.ABSORBER) {
					if (RBMKHandler.absorberEfficiency == 1)
						return;

					this.fluxQuantity *= RBMKHandler.absorberEfficiency;
				}
			}

			List<RBMKNode> nodes = getNodes(true);
			if (nodes.isEmpty())
				return;

			RBMKNode lastNode = nodes.get(nodes.size() - 1);

			if (lastNode.type != RBMKType.REFLECTOR && lastNode.type != RBMKType.ABSORBER && lastNode.type != RBMKType.CONTROL_ROD)
				irradiateFromFlux((new BlockPos(lastNode.tile)).add(this.vector.xCoord, 0.0D, this.vector.zCoord));

			if (lastNode.type == RBMKType.CONTROL_ROD) {
				TileEntityRBMKControl rod = (TileEntityRBMKControl)lastNode.tile;
				if (rod.getMult() > 0.0D) {
					this.fluxQuantity *= rod.getMult();
					irradiateFromFlux((new BlockPos(lastNode.tile)).add(this.vector.xCoord, 0.0D, this.vector.zCoord));
				}
			}
		}

		public int getHits(BlockPos pos) {
			int hits = 0;

			for(int h = 0; h < columnHeight; h++) {
				// holy fucking shit
				// I have had this one line cause me like tens of problems
				// I FUCKING HATE THIS
				// total count of bugs fixed attributed to this function: 13
				Block block = origin.tile.getWorldObj().getBlock(pos.getX(), pos.getY() + h, pos.getZ());
				if (block.isOpaqueCube())
					hits += 1;
			}

			return hits;
		}

		public void irradiateFromFlux(BlockPos pos) {
			ChunkRadiationManager.proxy.incrementRad(origin.tile.getWorldObj(), pos.getX(), pos.getY(), pos.getZ(), (float) (fluxQuantity * 0.05F * (1 - (double) getHits(pos) / columnHeight)));
		}

		public void irradiateFromFlux(BlockPos pos, int hits) {
			ChunkRadiationManager.proxy.incrementRad(origin.tile.getWorldObj(), pos.getX(), pos.getY(), pos.getZ(), (float) (fluxQuantity * 0.05F * (1 - (double) hits / columnHeight)));
		}

		public void moderateStream() {
			fluxRatio *= (1 - moderatorEfficiency);
		}
	}

	// List of all stream worlds.
	public static HashMap<World, StreamWorld> streamWorlds = new HashMap<>();

	public static void removeAllWorlds() {
		streamWorlds.clear();
	}

	// HashMap of all RBMK nodes and their positions.
	protected static HashMap<BlockPos, RBMKNode> nodeCache = new HashMap<>();

	public static void addNode(RBMKNode node) {
		nodeCache.put(new BlockPos(node.tile), node);
	}

	public static void removeNode(BlockPos position) {
		nodeCache.remove(position);
	}

	public static RBMKNode getNode(BlockPos position) {
		return nodeCache.get(position);
	}

	public static void removeAllNodes() {
		nodeCache.clear();
	}

	private static int ticks = 0;

	// The big one!! Runs all interactions for neutrons.
	public static void runAllInteractions() {

		// Remove `StreamWorld` objects if they have no streams.
		{ // aflghdkljghlkbhfjkghgilurbhlkfjghkffdjgn
			List<World> toRemove = new ArrayList<>();
			streamWorlds.forEach((world, streamWorld) -> {
				if (streamWorld.streams.isEmpty())
					toRemove.add(world);
			});

			for (World world : toRemove) {
				streamWorlds.remove(world);
			}
		}

		for (Entry<World, StreamWorld> world : streamWorlds.entrySet()) {

			// Gamerule caching because this apparently is kinda slow?
			// meh, good enough
			reflectorEfficiency = RBMKDials.getReflectorEfficiency(world.getKey());
			absorberEfficiency = RBMKDials.getAbsorberEfficiency(world.getKey());
			moderatorEfficiency = RBMKDials.getModeratorEfficiency(world.getKey());

			// I hate this.
			// this broke everything because it was ONE OFF
			// IT'S NOT THE TOTAL HEIGHT IT'S THE AMOUNT OF BLOCKS ABOVE AAAAAAAAAAAAA
			columnHeight = RBMKDials.getColumnHeight(world.getKey()) + 1;
			fluxRange = RBMKDials.getFluxRange(world.getKey());

			for (NeutronStream stream : world.getValue().streams) {
				stream.runStreamInteraction(world.getKey());
			}
			world.getValue().removeAllStreams();
		}

		// Freshen the node cache every `cacheTime` ticks to prevent huge RAM usage.
		int cacheTime = 40;
		if (ticks >= cacheTime) {
			ticks = 0;
			List<BlockPos> toRemove = new ArrayList<>();
			for(RBMKNode cachedNode : nodeCache.values())
				toRemove.addAll(cachedNode.checkNode());

			for(BlockPos pos : toRemove)
				removeNode(pos);
		}
		ticks++;
	}
}
