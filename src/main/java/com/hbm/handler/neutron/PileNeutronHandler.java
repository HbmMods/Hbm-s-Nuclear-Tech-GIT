package com.hbm.handler.neutron;

import api.hbm.block.IPileNeutronReceiver;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.pile.TileEntityPileBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PileNeutronHandler {

	public static int range = 5;

	public static class PileNeutronNode extends NeutronNode {

		public PileNeutronNode(TileEntityPileBase tile) {
			super(tile, NeutronStream.NeutronType.PILE);
		}

	}

	public static PileNeutronNode makeNode(TileEntityPileBase tile) {
		BlockPos pos = new BlockPos(tile);
		if (NeutronNodeWorld.nodeCache.containsKey(pos))
			return (PileNeutronNode) NeutronNodeWorld.getNode(pos);
		return new PileNeutronNode(tile);
	}

	private static TileEntity blockPosToTE(World worldObj, BlockPos pos) {
		return worldObj.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
	}

	public static class PileNeutronStream extends NeutronStream {

		public PileNeutronStream(NeutronNode origin, Vec3 vector) {
			super(origin, vector);
		}

		public PileNeutronStream(NeutronNode origin, Vec3 vector, double flux) {
			super(origin, vector, flux, 0D, NeutronType.PILE);
		}

		@Override
		public void runStreamInteraction(World worldObj) {

			//Random rand = origin.tile.getWorldObj().rand;
			//Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
			//vec.rotateAroundZ((float)(rand.nextDouble() * Math.PI * 2D));
			//vec.rotateAroundY((float)(rand.nextDouble() * Math.PI * 2D));
			//vec.rotateAroundX((float)(rand.nextDouble() * Math.PI * 2D));

			TileEntityPileBase originTE = (TileEntityPileBase) origin.tile;
			BlockPos pos = new BlockPos(originTE);

			for(float i = 1; i <= range; i += 1F) {

				BlockPos node = new BlockPos(
						(int)Math.floor(pos.getX() + 0.5 + vector.xCoord * i),
						(int)Math.floor(pos.getY() + 0.5 + vector.yCoord * i),
						(int)Math.floor(pos.getZ() + 0.5 + vector.zCoord * i)
				);

				if(node.equals(pos))
					continue; // don't interact with itself!

				TileEntity tile;

				if (NeutronNodeWorld.nodeCache.containsKey(node))
					tile = NeutronNodeWorld.nodeCache.get(node).tile;
				else {
					tile = blockPosToTE(worldObj, node);
					if (tile == null)
						return; // Doesn't exist!
					if (tile instanceof TileEntityPileBase)
						NeutronNodeWorld.addNode(new PileNeutronNode((TileEntityPileBase) tile));
				}

				TileEntityPileBase te = (TileEntityPileBase) tile;
				Block block = te.getBlockType();


				// Return when a boron block is hit
				if(block == ModBlocks.block_boron)
					return;

				else if(block == ModBlocks.concrete ||
						block == ModBlocks.concrete_smooth ||
						block == ModBlocks.concrete_asbestos ||
						block == ModBlocks.concrete_colored ||
						block == ModBlocks.brick_concrete)
					fluxQuantity *= 0.25;

				int meta = te.getBlockMetadata();

				if(block == ModBlocks.block_graphite_rod && (meta & 8) == 0)
					return;

				if(te instanceof IPileNeutronReceiver) {

					IPileNeutronReceiver rec = (IPileNeutronReceiver) te;
					rec.receiveNeutrons((int) fluxQuantity);

					if(block != ModBlocks.block_graphite_detector || (meta & 8) == 0)
						return;
				}

				int x = (int) (node.getX() + 0.5);
				int y = (int) (node.getY() + 0.5);
				int z = (int) (node.getZ() + 0.5);
				List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x, y, z, x, y, z));

				if(entities != null)
					for(EntityLivingBase e : entities)
						ContaminationUtil.contaminate(e, ContaminationUtil.HazardType.RADIATION, ContaminationUtil.ContaminationType.CREATIVE, (float) (fluxQuantity / 4D));
			}
		}
	}

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

		for (Map.Entry<World, NeutronNodeWorld.StreamWorld> world : NeutronNodeWorld.streamWorlds.entrySet()) {

			for (NeutronStream stream : world.getValue().streams) {
				stream.runStreamInteraction(world.getKey());
			}
			world.getValue().removeAllStreams();
		}

		for (Map.Entry<World, NeutronNodeWorld.StreamWorld> world : NeutronNodeWorld.streamWorlds.entrySet()) {

			for (NeutronStream stream : world.getValue().streams) {
				if (stream.type == NeutronStream.NeutronType.PILE)
					stream.runStreamInteraction(world.getKey());
			}
			world.getValue().removeAllStreamsOfType(NeutronStream.NeutronType.PILE);
		}

	}
}
