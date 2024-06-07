package com.hbm.handler.atmosphere;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.hbm.dim.CelestialBody;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.handler.ThreeInts;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ChunkAtmosphereHandler {

	// Misnomer, this isn't actually "chunk" based, but instead a global handler
	// ah who feckin cares, for all you need to know, this is _magic_
	// Breathe easy, bub

	private HashMap<Integer, HashMap<IAtmosphereProvider, AtmosphereBlob>> worldBlobs = new HashMap<>();
	private final int MAX_BLOB_RADIUS = 256;

	/*
	 * Methods to get information about the current atmosphere
	 */
	public CBT_Atmosphere getAtmosphere(Entity entity) {
		return getAtmosphere(entity.worldObj, MathHelper.floor_double(entity.posX), MathHelper.ceiling_double_int(entity.posY), MathHelper.floor_double(entity.posZ));
	}

	public CBT_Atmosphere getAtmosphere(World world, int x, int y, int z) {
		ThreeInts pos = new ThreeInts(x, y, z);
		HashMap<IAtmosphereProvider, AtmosphereBlob> blobs = worldBlobs.get(world.provider.dimensionId);

		CBT_Atmosphere atmosphere = CelestialBody.getTrait(world, CBT_Atmosphere.class);
		if(atmosphere == null) {
			atmosphere = new CBT_Atmosphere();
		} else {
			// Don't modify the trait directly
			atmosphere = atmosphere.clone();
		}

		for(AtmosphereBlob blob : blobs.values()) {
			if(blob.contains(pos)) {
				double pressure = blob.handler.getFluidPressure();
				if(pressure < 0) {
					atmosphere.reduce(pressure);
				} else {
					atmosphere.add(blob.handler.getFluidType(), pressure);
				}
			}
		}

		if(atmosphere.fluids.size() == 0) return null;

		return atmosphere;
	}

	public boolean hasAtmosphere(World world, int x, int y, int z) {
		ThreeInts pos = new ThreeInts(x, y, z);
		HashMap<IAtmosphereProvider, AtmosphereBlob> blobs = worldBlobs.get(world.provider.dimensionId);

		for(AtmosphereBlob blob : blobs.values()) {
			if(blob.contains(pos)) {
				return true;
			}
		}

		return false;
	}

	protected List<AtmosphereBlob> getBlobsWithinRadius(World world, ThreeInts pos, int radius) {
		HashMap<IAtmosphereProvider, AtmosphereBlob> blobs = worldBlobs.get(world.provider.dimensionId);
		List<AtmosphereBlob> list = new LinkedList<AtmosphereBlob>();

		for(AtmosphereBlob blob : blobs.values()) {
			if(blob.getRootPosition().getDistanceSquared(pos) <= radius * radius) {
				list.add(blob);
			}
		}

		return list;
	}


	/*
	 * Registration methods
	 */
	public void registerAtmosphere(IAtmosphereProvider handler) {
		HashMap<IAtmosphereProvider, AtmosphereBlob> blobs = worldBlobs.get(handler.getWorld().provider.dimensionId);
		AtmosphereBlob blob = blobs.get(handler);
		
		if(blob == null) {
			blob = new AtmosphereBlob(handler);
			blob.addBlock(handler.getRootPosition());
			blobs.put(handler, blob);
		}
	}

	public void unregisterAtmosphere(IAtmosphereProvider handler) {
		HashMap<IAtmosphereProvider, AtmosphereBlob> blobs = worldBlobs.get(handler.getWorld().provider.dimensionId);
		blobs.remove(handler);
	}


	/*
	 * Hooks to update our volumes as necessary
	 */
	public void onSealableChange(World world, ThreeInts pos, IBlockSealable sealable, List<AtmosphereBlob> nearbyBlobs) {
		if(world.isRemote) return;

		if(sealable.isSealed(world, pos.x, pos.y, pos.z)) {
			for(AtmosphereBlob blob : nearbyBlobs) {
				if(blob.contains(pos)) {
					blob.removeBlock(pos);
				} else if(!blob.contains(blob.getRootPosition())) {
					blob.addBlock(blob.getRootPosition());
				}
			}
		} else {
			onBlockRemoved(world, pos);
		}
	}

	private void onBlockPlaced(World world, ThreeInts pos) {
		if(!AtmosphereBlob.isBlockSealed(world, pos)) return;

		List<AtmosphereBlob> nearbyBlobs = getBlobsWithinRadius(world, pos, MAX_BLOB_RADIUS);
		for(AtmosphereBlob blob : nearbyBlobs) {
			if(blob.contains(pos)) {
				blob.removeBlock(pos);
			} else if(!blob.contains(blob.getRootPosition())) {
				blob.addBlock(blob.getRootPosition());
			}
		}
	}

	private void onBlockRemoved(World world, ThreeInts pos) {
		List<AtmosphereBlob> nearbyBlobs = getBlobsWithinRadius(world, pos, MAX_BLOB_RADIUS);
		for(AtmosphereBlob blob : nearbyBlobs) {
			// Make sure that a block can actually be attached to the blob
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(blob.contains(pos.getPositionAtOffset(dir.offsetX, dir.offsetY, dir.offsetZ))) {
					blob.addBlock(pos);
					break;
				}
			}
		}
	}


	/*
	 * Event handlers
	 */
	public void receiveWorldLoad(WorldEvent.Load event) {
		if(event.world.isRemote) return;
		worldBlobs.put(event.world.provider.dimensionId, new HashMap<>());
	}

	public void receiveWorldUnload(WorldEvent.Unload event) {
		if(event.world.isRemote) return;
		worldBlobs.remove(event.world.provider.dimensionId);
	}

	public void receiveBlockPlaced(BlockEvent.PlaceEvent event) {
		if(event.world.isRemote) return;
		onBlockPlaced(event.world, new ThreeInts(event.x, event.y, event.z));
	}

	public void receiveBlockBroken(BlockEvent.BreakEvent event) {
		if(event.world.isRemote) return;
		onBlockRemoved(event.world, new ThreeInts(event.x, event.y, event.z));
	}
	
}
