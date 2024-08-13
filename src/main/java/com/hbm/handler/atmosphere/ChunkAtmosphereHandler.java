package com.hbm.handler.atmosphere;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.hbm.config.GeneralConfig;
import com.hbm.dim.CelestialBody;
import com.hbm.dim.trait.CBT_Atmosphere;
import com.hbm.dim.trait.CBT_Atmosphere.FluidEntry;
import com.hbm.handler.ThreeInts;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
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
		return getAtmosphere(entity.worldObj, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY + entity.getEyeHeight()), MathHelper.floor_double(entity.posZ), null);
	}

	public CBT_Atmosphere getAtmosphere(World world, int x, int y, int z) {
		return getAtmosphere(world, x, y, z, null);
	}

	public CBT_Atmosphere getAtmosphere(World world, int x, int y, int z, AtmosphereBlob excludeBlob) {
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
			if(blob == excludeBlob) continue;

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

	public List<AtmosphereBlob> getBlobs(World world, int x, int y, int z) {
		List<AtmosphereBlob> inBlobs = new ArrayList<AtmosphereBlob>();

		ThreeInts pos = new ThreeInts(x, y, z);
		HashMap<IAtmosphereProvider, AtmosphereBlob> blobs = worldBlobs.get(world.provider.dimensionId);

		for(AtmosphereBlob blob : blobs.values()) {
			if(blob.contains(pos)) {
				inBlobs.add(blob);
			}
		}

		return inBlobs;
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

    // Assuming 21% AIR/9% OXY is required for breathable atmosphere
    public boolean canBreathe(EntityLivingBase entity) {
		CBT_Atmosphere atmosphere = getAtmosphere(entity);

		if(GeneralConfig.enableDebugMode && entity instanceof EntityPlayer && entity.worldObj.getTotalWorldTime() % 20 == 0) {
			if(atmosphere != null && atmosphere.fluids.size() > 0) {
				for(FluidEntry entry : atmosphere.fluids) {
					MainRegistry.logger.info("Atmosphere: " + entry.fluid.getUnlocalizedName() + " - " + entry.pressure + "bar");
				}
			} else {
				MainRegistry.logger.info("Atmosphere: TOTAL VACUUM");
			}
		}

		return canBreathe(atmosphere);
    }

	public boolean canBreathe(CBT_Atmosphere atmosphere) {
        return atmosphere != null && (atmosphere.hasFluid(Fluids.AIR, 0.21) || atmosphere.hasFluid(Fluids.OXYGEN, 0.09));
	}

	// Is the air pressure high enough to support liquids
	public boolean hasLiquidPressure(CBT_Atmosphere atmosphere) {
		return atmosphere != null && atmosphere.getPressure() >= 0.19D;
	}

	public boolean willCorrode(EntityLivingBase entity) {
		return willCorrode(getAtmosphere(entity));
	}

	public boolean willCorrode(CBT_Atmosphere atmosphere) {
		return atmosphere != null && atmosphere.hasTrait(FT_Corrosive.class, 0.2);
	}

	/**
	 * Actions to rectify world status based on atmosphere
	 */
	private boolean runEffectsOnBlock(CBT_Atmosphere atmosphere, World world, Block block, int x, int y, int z, boolean fetchAtmosphere) {
		boolean requiresPressure = block == Blocks.water || block == Blocks.flowing_water;
		boolean requiresO2 = block instanceof BlockTorch || block instanceof BlockFire;
		boolean requiresCO2 = block instanceof IPlantable;

		if(!requiresO2 && !requiresCO2 && !requiresPressure) return false;

		if(fetchAtmosphere) {
			atmosphere = getAtmosphere(world, x, y, z);
		}

		boolean canExist = true;

		if(requiresO2) {
			// Check for an atmosphere and destroy torches if there is insufficient oxygen
			canExist = !(atmosphere == null || (!atmosphere.hasFluid(Fluids.OXYGEN, 0.09) && !atmosphere.hasFluid(Fluids.AIR, 0.21)));
		} else if(requiresPressure) {
			canExist = hasLiquidPressure(atmosphere);
		} else if(requiresCO2) {
			// TODO: Make plants rely on CO2 once CO2 is more readily available (via natural gas most likely)
			canExist = !(atmosphere == null || (!atmosphere.hasFluid(Fluids.OXYGEN, 0.01) && !atmosphere.hasFluid(Fluids.AIR, 0.1)));
		}

		if(canExist) return false;
		
		block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);

		return true;
	}

	public boolean runEffectsOnBlock(CBT_Atmosphere atmosphere, World world, Block block, int x, int y, int z) {
		return runEffectsOnBlock(atmosphere, world, block, x, y, z, false);
	}

	public boolean runEffectsOnBlock(World world, Block block, int x, int y, int z) {
		return runEffectsOnBlock(null, world, block, x, y, z, true);
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
