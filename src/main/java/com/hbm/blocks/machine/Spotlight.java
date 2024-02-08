package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Spotlight extends Block {

    public int beamLength;

	public Spotlight(Material mat, int beamLength) {
		super(mat);
        setLightLevel(1.0F);

        this.beamLength = beamLength;
	}

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hx, float hy, float hz, int initData) {
        return side << 1;
    }
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		updateBeam(world, x, y, z);
	}

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        ForgeDirection dir = getDirection(metadata);
        super.breakBlock(world, x, y, z, block, metadata);

        if (world.isRemote) return;
        
        unpropagateBeam(world, x, y, z, dir);
    }

	// Repropagate the beam if we've become unblocked
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if (world.isRemote) return;
        if (neighborBlock instanceof SpotlightBeam) return;
        updateBeam(world, x, y, z);
	}

	public void updateBeam(World world, int x, int y, int z) {
        if (world.isRemote) return;

        ForgeDirection dir = getDirection(world, x, y, z);
        propagateBeam(world, x, y, z, dir, beamLength);
	}

    private ForgeDirection getDirection(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        return getDirection(metadata);
    }

    private ForgeDirection getDirection(int metadata) {
        return ForgeDirection.getOrientation(metadata >> 1);
    }

    // Recursively add beam blocks, updating any that already exist with new incoming light directions
    public static void propagateBeam(World world, int x, int y, int z, ForgeDirection dir, int distance) {
        distance--;
        if (distance <= 0) return;

        x += dir.offsetX;
        y += dir.offsetY;
        z += dir.offsetZ;

        Block block = world.getBlock(x, y, z);
        if (!block.isAir(world, x, y, z)) return;

        if (!(block instanceof SpotlightBeam)) {
            world.setBlock(x, y, z, ModBlocks.spotlight_beam);
        }

        // If we encounter an existing beam, add a new INCOMING direction to the metadata
        SpotlightBeam.setDirection(world, x, y, z, dir, true);

        propagateBeam(world, x, y, z, dir, distance);
    }

    // Recursively delete beam blocks, if they aren't still illuminated from a different direction
    public static void unpropagateBeam(World world, int x, int y, int z, ForgeDirection dir) {
        x += dir.offsetX;
        y += dir.offsetY;
        z += dir.offsetZ;

        Block block = world.getBlock(x, y, z);
        if (!(block instanceof SpotlightBeam)) return;

        // Remove the metadata associated with this direction
        // If all directions are set to zero, delete the beam
        if (SpotlightBeam.setDirection(world, x, y, z, dir, false) == 0) {
            world.setBlockToAir(x, y, z);
        }

        unpropagateBeam(world, x, y, z, dir);
    }

    // Travels back through a beam to the source, and if found, repropagates the beam
    public static void backPropagate(World world, int x, int y, int z, ForgeDirection dir) {
        x -= dir.offsetX;
        y -= dir.offsetY;
        z -= dir.offsetZ;

        Block block = world.getBlock(x, y, z);
        if (block instanceof Spotlight) {
            Spotlight spot = (Spotlight) block;
            propagateBeam(world, x, y, z, dir, spot.beamLength);
        } else if (!(block instanceof SpotlightBeam)) {
            return;
        }

        backPropagate(world, x, y, z, dir);
    }
    
}
