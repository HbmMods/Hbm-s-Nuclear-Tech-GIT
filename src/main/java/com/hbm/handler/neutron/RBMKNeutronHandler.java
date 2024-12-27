package com.hbm.handler.neutron;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.util.fauxpointtwelve.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

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
			posInstance = new BlockPos(tile);
		}

		public void addLid() {
			this.data.replace("hasLid", true);
		}

		public void removeLid() {
			this.data.replace("hasLid", false);
		}

		protected BlockPos posInstance;

		private int x;
		private int z;

		public Iterator<BlockPos> getReaSimNodes() {

			x = -fluxRange;
			z = -fluxRange;

			return new Iterator<BlockPos>() {
				@Override
				public boolean hasNext() {
					return (fluxRange + x) * (fluxRange * 2 + 1) + z + fluxRange + 1 < (fluxRange * 2 + 1) * (fluxRange * 2 + 1);
				}

				@Override
				public BlockPos next() {
					if (Math.pow(x, 2) + Math.pow(z, 2) <= fluxRange * fluxRange) {
						z++;
						if (z > fluxRange) {
							z = -fluxRange;
							x++;
						}
						return posInstance.mutate(tile.xCoord + x, tile.yCoord, tile.zCoord + z);
					} else {
						z++;
						if (z > fluxRange) {
							z = -fluxRange;
							x++;
						}
						return null;
					}
				}
			};
		}

		public List<BlockPos> checkNode() {
			List<BlockPos> list = new ArrayList<>();

			BlockPos pos = new BlockPos(this.tile);

			RBMKNeutronStream[] streams = new RBMKNeutronStream[TileEntityRBMKRod.fluxDirs.length];

			// Simulate streams coming out of the RBMK rod.
			ForgeDirection[] fluxDirs = TileEntityRBMKRod.fluxDirs;
			for (int i = 0; i < fluxDirs.length; i++) {
				streams[i] = (new RBMKNeutronStream(this, Vec3.createVectorHelper(fluxDirs[i].offsetX, 0, fluxDirs[i].offsetZ)));
			}

			// Check if the rod should uncache nodes.
			if (tile instanceof TileEntityRBMKRod && !(tile instanceof TileEntityRBMKRodReaSim)) {
				TileEntityRBMKRod rod = (TileEntityRBMKRod) tile;
				if (!rod.hasRod || rod.lastFluxQuantity == 0) {

					for (RBMKNeutronStream stream : streams) {
						for(RBMKNeutronNode node : stream.getNodes(false))
							if (node != null)
								list.add(new BlockPos(node.tile));
					}

					return list;
				}
			}

			{
				Iterator<BlockPos> reaSimNodes = getReaSimNodes();

				// Check if the ReaSim rod should be culled from the cache due to no rod or no flux.
				if (tile instanceof TileEntityRBMKRodReaSim) { // fuckkkkkkk
					TileEntityRBMKRodReaSim rod = (TileEntityRBMKRodReaSim) tile;
					if (!rod.hasRod || rod.lastFluxQuantity == 0) {
						reaSimNodes.forEachRemaining((a) -> {
							if (a != null)
								list.add(a.clone()); // ae The RAM usage will be really high here but hopefully the GC can take care of it :pray:
						});
						return list;
					}
				}
			}

			// Check if non-rod nodes should be uncached... but now with ReaSim!
			{ //  Yeah, I don't want to contaminate the surrounding scope.
				Iterator<BlockPos> reaSimNodes = getReaSimNodes();

				boolean hasRod = false;

				while(reaSimNodes.hasNext()) {

					BlockPos nodePos = reaSimNodes.next();

					if(nodePos == null)
						continue;

					NeutronNode node = NeutronNodeWorld.nodeCache.get(nodePos);

					if (node != null && node.tile instanceof TileEntityRBMKRod) {

						TileEntityRBMKRod rod = (TileEntityRBMKRod) node.tile;

						if (rod.hasRod && rod.lastFluxQuantity > 0) {
							hasRod = true;
							break;
						}
					}
				}

				if (!hasRod) {
					list.add(pos);
					return list;
				}
			}

			// Check if non-rod nodes should be uncached due to no rod in range.
			for (RBMKNeutronStream stream : streams) {

				RBMKNeutronNode[] nodes = stream.getNodes(false);

				for (RBMKNeutronNode node : nodes) {
					if (!(node == null) && node.tile instanceof TileEntityRBMKRod)
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
		public RBMKNeutronNode[] getNodes(boolean addNode) {
			RBMKNeutronNode[] positions = new RBMKNeutronNode[fluxRange];

			BlockPos pos = new BlockPos(origin.tile);

			for (int i = 1; i <= fluxRange; i++) {
				int x = (int) Math.floor(0.5 + vector.xCoord * i);
				int z = (int) Math.floor(0.5 + vector.zCoord * i);

				pos.mutate(origin.tile.xCoord + x, origin.tile.yCoord, origin.tile.zCoord + z);

				if (NeutronNodeWorld.nodeCache.containsKey(pos))
					positions[i - 1] = (RBMKNeutronNode) NeutronNodeWorld.getNode(pos);

				else if (this.origin.tile.getBlockType() instanceof RBMKBase) {
					TileEntity te = blockPosToTE(this.origin.tile.getWorldObj(), pos);
					if (te instanceof TileEntityRBMKBase) {
						TileEntityRBMKBase rbmkBase = (TileEntityRBMKBase) te;
						RBMKNeutronNode node = makeNode(rbmkBase);
						positions[i - 1] = node;
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

			Iterator<BlockPos> iterator = getBlocks(fluxRange);

			while(iterator.hasNext()) {

				BlockPos nodePos = iterator.next();

				if (fluxQuantity == 0D) // Whoops, used it all up!
					return;

				RBMKNeutronNode node;

				if (!NeutronNodeWorld.nodeCache.containsKey(nodePos)) {
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

				node = (RBMKNeutronNode) NeutronNodeWorld.nodeCache.get(nodePos);

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

			RBMKNeutronNode[] nodes = getNodes(true);

			RBMKNeutronNode lastNode = nodes[(nodes.length - 1)];

			if(lastNode == null) { // This implies that there was *no* last node, meaning either way it was never caught.
				// There is really no good way to figure out where exactly it should irradiate, so just irradiate at the origin tile.
				irradiateFromFlux(new BlockPos(origin.tile.xCoord + this.vector.xCoord, origin.tile.yCoord, origin.tile.zCoord + this.vector.zCoord));
				return;
			}

			RBMKType lastNodeType = (RBMKType) lastNode.data.get("type");

			if (lastNodeType == RBMKType.CONTROL_ROD) {
				TileEntityRBMKControl rod = (TileEntityRBMKControl) lastNode.tile;
				if (rod.getMult() > 0.0D) {
					this.fluxQuantity *= rod.getMult();
					irradiateFromFlux(new BlockPos(lastNode.tile.xCoord + this.vector.xCoord, lastNode.tile.yCoord, lastNode.tile.zCoord + this.vector.zCoord));
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
}
