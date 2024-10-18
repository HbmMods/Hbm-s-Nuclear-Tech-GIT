package com.hbm.handler.neutron;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.util.fauxpointtwelve.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RBMKNeutronHandler {

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

	public static RBMKNeutronNode makeNode(TileEntityRBMKBase tile) {
		BlockPos pos = new BlockPos(tile);
		if (NeutronNodeWorld.nodeCache.containsKey(pos))
			return (RBMKNeutronNode) NeutronNodeWorld.getNode(pos);
		return new RBMKNeutronNode(tile, tile.getRBMKType(), tile.hasLid());
	}

	public static class RBMKNeutronNode extends NeutronNode {

		public RBMKNeutronNode(TileEntityRBMKBase tile, RBMKType type, boolean hasLid) {
			super(tile, NeutronStream.NeutronType.RBMK);
			this.data.put("hasLid", hasLid);
			this.data.put("type", type);
		}

		public void addLid() {
			this.data.replace("hasLid", true);
		}

		public void removeLid() {
			this.data.replace("hasLid", false);
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

			List<RBMKNeutronStream> streams = new ArrayList<>();

			// Simulate streams coming out of the RBMK rod.
			for (ForgeDirection dir : TileEntityRBMKRod.fluxDirs) {
				streams.add(new RBMKNeutronStream(this, Vec3.createVectorHelper(dir.offsetX, 0, dir.offsetZ)));
			}

			// Check if the rod should uncache nodes.
			if (tile instanceof TileEntityRBMKRod && !(tile instanceof TileEntityRBMKRodReaSim)) {
				TileEntityRBMKRod rod = (TileEntityRBMKRod) tile;
				if (!rod.hasRod || rod.lastFluxQuantity == 0) {

					for (RBMKNeutronStream stream : streams) {
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
				List<RBMKNeutronNode> nodes = new ArrayList<>();
				points.forEach(nodePos -> {
					RBMKNeutronNode node = (RBMKNeutronNode) NeutronNodeWorld.getNode(nodePos);
					if (node != null)
						nodes.add(node);
				});

				boolean hasRod = false;

				for (RBMKNeutronNode node : nodes) {

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
			for (RBMKNeutronStream stream : streams) {

				List<RBMKNeutronNode> nodes = stream.getNodes(false);

				for (RBMKNeutronNode node : nodes) {
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


	public static class RBMKNeutronStream extends NeutronStream {

		public RBMKNeutronStream(NeutronNode origin, Vec3 vector) {
			super(origin, vector);
		}

		public RBMKNeutronStream(NeutronNode origin, Vec3 vector, double flux, double ratio) {
			super(origin, vector, flux, ratio, NeutronType.RBMK);
		}

		// Does NOT include the origin node
		// USES THE CACHE!!!
		public List<RBMKNeutronNode> getNodes(boolean addNode) {
			List<RBMKNeutronNode> positions = new ArrayList<>();

			for (int i = 1; i <= fluxRange; i++) {
				int x = (int) Math.floor(0.5 + vector.xCoord * i);
				int z = (int) Math.floor(0.5 + vector.zCoord * i);

				BlockPos pos = new BlockPos(origin.tile).add(x, 0, z);

				if (NeutronNodeWorld.nodeCache.containsKey(pos))
					positions.add((RBMKNeutronNode) NeutronNodeWorld.getNode(pos));

				else if (this.origin.tile.getBlockType() instanceof RBMKBase) {
					TileEntity te = blockPosToTE(this.origin.tile.getWorldObj(), pos);
					if (te instanceof TileEntityRBMKBase) {
						TileEntityRBMKBase rbmkBase = (TileEntityRBMKBase) te;
						RBMKNeutronNode node = makeNode(rbmkBase);
						positions.add(node);
						if (addNode)
							NeutronNodeWorld.addNode(node);
					}
				}
			}
			return positions;
		}

		// The... small one? whatever it's still pretty big, runs the interaction for the stream.
		public void runStreamInteraction(World worldObj) {

			// do nothing if there's nothing to do lmao
			if (fluxQuantity == 0D)
				return;

			BlockPos pos = new BlockPos(origin.tile);

			TileEntityRBMKBase originTE;

			if (NeutronNodeWorld.nodeCache.containsKey(pos))
				originTE = (TileEntityRBMKBase) NeutronNodeWorld.nodeCache.get(pos).tile;
			else {
				originTE = (TileEntityRBMKBase) blockPosToTE(worldObj, pos);
				if (originTE == null)
					return; // Doesn't exist anymore!
				NeutronNodeWorld.addNode(new RBMKNeutronNode(originTE, originTE.getRBMKType(), originTE.hasLid()));
			}

			int moderatedCount = 0;

			for (BlockPos nodePos : getBlocks(fluxRange)) {

				if (fluxQuantity == 0D) // Whoops, used it all up!
					return;

				RBMKNeutronNode node = (RBMKNeutronNode) NeutronNodeWorld.nodeCache.get(nodePos);

				if (node == null) {
					TileEntity te = blockPosToTE(worldObj, nodePos); // ok, maybe it didn't get added to the list somehow??
					if (te instanceof TileEntityRBMKBase) {
						node = makeNode((TileEntityRBMKBase) te);
						NeutronNodeWorld.addNode(node); // whoops!
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

				RBMKType type = (RBMKType) node.data.get("type");

				if (type == RBMKType.OTHER) // pass right on by!
					continue;

				// we established earlier during `getNodes()` that they should all be RBMKBase TEs
				// no issue with casting here!
				TileEntityRBMKBase nodeTE = (TileEntityRBMKBase) node.tile;

				if (!(boolean) node.data.get("hasLid"))
					ChunkRadiationManager.proxy.incrementRad(worldObj, nodePos.getX(), nodePos.getY(), nodePos.getZ(), (float) (this.fluxQuantity * 0.05F));

				if (type == RBMKType.MODERATOR || nodeTE.isModerated()) {
					moderatedCount++;
					moderateStream();
				}

				if (nodeTE instanceof IRBMKFluxReceiver) {
					IRBMKFluxReceiver column = (IRBMKFluxReceiver) nodeTE;

					if (type == RBMKType.ROD) {
						TileEntityRBMKRod rod = (TileEntityRBMKRod) column;

						if (rod.hasRod) {
							rod.receiveFlux(this);
							return;
						}

					} else if (type == RBMKType.OUTGASSER) {
						TileEntityRBMKOutgasser outgasser = ((TileEntityRBMKOutgasser) column);

						if (outgasser.canProcess()) {
							column.receiveFlux(this);
							return;
						}
					}

				} else if (type == RBMKType.CONTROL_ROD) {
					TileEntityRBMKControl rod = (TileEntityRBMKControl) nodeTE;

					if (rod.level > 0.0D) {

						this.fluxQuantity *= rod.getMult();
						continue;
					}
					return;
				} else if (type == RBMKType.REFLECTOR) {

					if (((TileEntityRBMKBase) this.origin.tile).isModerated())
						moderatedCount++;

					if (this.fluxRatio > 0 && moderatedCount > 0)
						for (int i = 0; i < moderatedCount; i++)
							moderateStream();

					if (reflectorEfficiency != 1.0D) {
						this.fluxQuantity *= reflectorEfficiency;
						continue;
					}

					((TileEntityRBMKRod) originTE).receiveFlux(this);
					return;
				} else if (type == RBMKType.ABSORBER) {
					if (absorberEfficiency == 1)
						return;

					this.fluxQuantity *= absorberEfficiency;
				}
			}

			List<RBMKNeutronNode> nodes = getNodes(true);
			if (nodes.isEmpty())
				return;

			RBMKNeutronNode lastNode = nodes.get(nodes.size() - 1);

			RBMKType lastNodeType = (RBMKType) lastNode.data.get("type");

			if (lastNodeType != RBMKType.REFLECTOR && lastNodeType != RBMKType.ABSORBER && lastNodeType != RBMKType.CONTROL_ROD)
				irradiateFromFlux((new BlockPos(lastNode.tile)).add(this.vector.xCoord, 0.0D, this.vector.zCoord));

			if (lastNodeType == RBMKType.CONTROL_ROD) {
				TileEntityRBMKControl rod = (TileEntityRBMKControl) lastNode.tile;
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

	private static int ticks = 0;

	// The big one!! Runs all interactions for neutrons.
	public static void runAllInteractions() {

		// Remove `StreamWorld` objects if they have no streams.
		{ // aflghdkljghlkbhfjkghgilurbhlkfjghkffdjgn
			List<World> toRemove = new ArrayList<>();
			NeutronNodeWorld.streamWorlds.forEach((world, streamWorld) -> {
				if (streamWorld.streams.isEmpty())
					toRemove.add(world);
			});

			for (World world : toRemove) {
				NeutronNodeWorld.streamWorlds.remove(world);
			}
		}

		for (Entry<World, NeutronNodeWorld.StreamWorld> world : NeutronNodeWorld.streamWorlds.entrySet()) {

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
				if (stream.type == NeutronStream.NeutronType.RBMK)
					stream.runStreamInteraction(world.getKey());
			}
			world.getValue().removeAllStreamsOfType(NeutronStream.NeutronType.RBMK);
		}

		// Freshen the node cache every `cacheTime` ticks to prevent huge RAM usage.
		int cacheTime = 40;
		if (ticks >= cacheTime) {
			ticks = 0;
			List<BlockPos> toRemove = new ArrayList<>();
			for(NeutronNode cachedNode : NeutronNodeWorld.nodeCache.values()) {
				if (cachedNode.type == NeutronStream.NeutronType.RBMK) {
					RBMKNeutronNode node = (RBMKNeutronNode) cachedNode;
					toRemove.addAll(node.checkNode());
				}
			}

			for(BlockPos pos : toRemove)
				NeutronNodeWorld.removeNode(pos);
		}
		ticks++;
	}
}
