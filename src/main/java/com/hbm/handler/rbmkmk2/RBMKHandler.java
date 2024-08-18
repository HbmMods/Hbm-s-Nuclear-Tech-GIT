package com.hbm.handler.rbmkmk2;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.*;

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

	public static class RBMKNode {

		protected RBMKType type;
		protected TileEntityRBMKBase tile;
		protected boolean hasLid;

		public RBMKNode(TileEntityRBMKBase tile, RBMKType type) {
			this.type = type;
			this.tile = tile;
			this.hasLid = tile.hasLid();
			addNode(this);
		}
	}

	private static TileEntity blockPosToTE(World worldObj, BlockPos pos) {
		return worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
	}

	public static RBMKNode makeNode(TileEntityRBMKBase tile) {
		return new RBMKNode(tile, tile.getRBMKType());
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

		public NeutronStream(RBMKNode origin, Vec3 vector, double flux, double ratio) {
			this.origin = origin;
			this.vector = vector;
			this.fluxQuantity = flux;
			this.fluxRatio = ratio;
			streams.put(this, origin.tile.getWorldObj());
		}

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

		// This, however, is used for actual RBMK flux calculations.
		// Does NOT include the origin node
		// USES THE CACHE!!!
		public List<RBMKNode> getNodes() {
			List<RBMKNode> positions = new ArrayList<>();

			for (int i = 1; i <= fluxRange; i++) {
				int x = (int) Math.floor(0.5 + vector.xCoord * i);
				int z = (int) Math.floor(0.5 + vector.zCoord * i);

				BlockPos pos = new BlockPos(origin.tile).add(x, 0, z);

				if (nodeCache.containsKey(pos)) {
					positions.add(nodeCache.get(pos));
					continue;
				}

				// If it isn't an RBMK block then don't do anything with it
				if(!(origin.tile.getBlockType() instanceof RBMKBase))
					continue;

				TileEntity te = blockPosToTE(origin.tile.getWorldObj(), pos);

				if (te instanceof TileEntityRBMKBase) {
					TileEntityRBMKBase rbmkBase = (TileEntityRBMKBase) te;
					RBMKType type = rbmkBase.getRBMKType();
					// they should ALL be RBMKBase TEs
					RBMKNode node = new RBMKNode(rbmkBase, type);
					positions.add(node);
					addNode(node);
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
					TileEntity te = blockPosToTE(worldObj, nodePos); // ok, maybe it didn't get added to the list somehow?
					if (te instanceof TileEntityRBMKBase) {
						node = makeNode((TileEntityRBMKBase) te);
						addNode(node); // whoops!
					} else
						return; // TE no longer exists, die!!
				}

				if(node.type == RBMKType.OTHER) // pass right on by!
					continue;

				Block block = node.tile.getBlockType();

				if (!(block instanceof RBMKBase)) {
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

				// we established earlier during `getNodes()` that they should all be RBMKBase TEs
				// no issue with casting here!
				TileEntityRBMKBase nodeTE = node.tile;

				if(!node.hasLid)
					ChunkRadiationManager.proxy.incrementRad(worldObj, nodePos.getX(), nodePos.getY(), nodePos.getZ(), (float) (this.fluxQuantity * 0.05F));

				if(node.type == RBMKType.MODERATOR) {
					moderatedCount += 1;
					moderateStream();
				}

				if(node.tile instanceof IRBMKFluxReceiver) {
					IRBMKFluxReceiver column = ((IRBMKFluxReceiver) nodeTE);
					if(node.type == RBMKType.ROD) {
						TileEntityRBMKRod rod = ((TileEntityRBMKRod) column);
						if(rod.hasRod) {
							if(rod.isModerated())
								moderateStream();
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
				}

				else if(node.type == RBMKType.CONTROL_ROD) {
					TileEntityRBMKControl rod = ((TileEntityRBMKControl) nodeTE);
					if (rod.level > 0D)
						fluxQuantity *= ((TileEntityRBMKControl) nodeTE).getMult();
					else
						return;
				}

				else if(node.type == RBMKType.REFLECTOR) {
					if((origin.tile).isModerated())
						moderatedCount += 1;
					if (fluxRatio > 0D && moderatedCount > 0) {
						for (int i = 0; i < moderatedCount; i++) {
							moderateStream(); // Moderate streams on the way back!
						}
					}
					if(reflectorEfficiency != 1) {
						fluxQuantity *= reflectorEfficiency;
					} else {
						((TileEntityRBMKRod) originTE).receiveFlux(this);
						// this one missing return line was baffling me for half an hour
						// "why is this rod jumping to double the flux randomly????"
						// because you aren't fucking returning from the function, and it's hitting the reflector after it, you idiot
						return;
					}
				}

				else if(node.type == RBMKType.ABSORBER) {
					if (absorberEfficiency == 1)
						return; // Instantly stop stream processing.
					else
						fluxQuantity *= absorberEfficiency;
				}
			}

			// Called *after* most streams have returned.
			List<RBMKNode> nodes = getNodes();

			if(nodes.isEmpty()) // how tf did we get here if its empty
				return;

			// Get the last node in the stream.
			RBMKNode lastNode = nodes.get(nodes.size() - 1);

			// Block hits don't have to be considered here, since if it's fully blocked it'll return the function before getting here.
			if(lastNode.type != RBMKType.REFLECTOR && lastNode.type != RBMKType.ABSORBER && lastNode.type != RBMKType.CONTROL_ROD) {
				// Neutrons must not have been caught then!
				irradiateFromFlux(new BlockPos(lastNode.tile).add(vector.xCoord, 0, vector.zCoord));
			}

			// but oh wait, control rods exist
			if(lastNode.type == RBMKType.CONTROL_ROD) {
				TileEntityRBMKControl rod = ((TileEntityRBMKControl) lastNode.tile);
				// just get level and irradiate based on that
				if(rod.getMult() > 0D) {
					fluxQuantity = fluxQuantity * rod.getMult();
					irradiateFromFlux(new BlockPos(lastNode.tile).add(vector.xCoord, 0, vector.zCoord));
				}

			}
		}

		public int getHits(BlockPos pos) {
			int hits = 0;

			for(int h = 0; h < columnHeight; h++) {
				// holy fucking shit
				// I have had this one line cause me like tens of problems
				// I FUCKING HATE THIS
				// total count of bugs fixed attributed to this function: 6
				if (!origin.tile.getWorldObj().getBlock(pos.getX(), pos.getY() + h, pos.getZ()).isOpaqueCube())
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
			fluxRatio = fluxRatio * (1 - moderatorEfficiency);
		}
	}

	// HashMap of all RBMK nodes and their positions.
	protected static HashMap<BlockPos, RBMKNode> nodeCache = new HashMap<>();

	// List of all active neutron streams.
	public static HashMap<NeutronStream, World> streams = new HashMap<>();

	public static void addNode(RBMKNode node) {
		nodeCache.put(new BlockPos(node.tile), node);
	}

	public static void removeNode(BlockPos position) {
		nodeCache.remove(position);
	}

	// The big one!! Runs all interactions for neutrons.
	public static void runAllInteractions() {

		for (World worldObj : MinecraftServer.getServer().worldServers) {
			// Gamerule caching because this apparently is kinda slow?
			// meh, good enough
			reflectorEfficiency = 1; //RBMKDials.getReflectorEfficiency();
			absorberEfficiency = 1; //RBMKDials.getAbsorberEfficiency();
			moderatorEfficiency = RBMKDials.getModeratorEfficiency(worldObj);
			// I hate this.
			// this broke everything because it was ONE OFF
			// IT'S NOT THE TOTAL HEIGHT IT'S THE AMOUNT OF BLOCKS ABOVE AAAAAAAAAAAAA
			columnHeight = RBMKDials.getColumnHeight(worldObj) + 1;
			fluxRange = RBMKDials.getFluxRange(worldObj);
			streams.forEach((stream, world) -> {
				if (world == worldObj)
					// WOO!!
					stream.runStreamInteraction(world);
			});
		}
		streams.clear();
	}
}
