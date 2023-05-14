package com.hbm.handler;

import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class NewAirHandler {
    public static void floodFillAir(World world, int startX, int startY, int startZ) {
        Block airBlock = ModBlocks.asphalt;

        if (world.getBlock(startX, startY, startZ) != airBlock) {
            return; // Exit if the starting block is not air
        }

        floodFill(world, startX, startY, startZ, airBlock);
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.world;
        int x = event.x;
        int y = event.y;
        int z = event.z;

        Block airBlock = ModBlocks.asphalt;

        if (world.getBlock(x, y, z) != airBlock) {
            return; // Exit if the broken block is not air
        }

        floodFill(world, x, y, z, airBlock);
    }
    @SubscribeEvent
    public void onBlockPlaced(BlockEvent.PlaceEvent event) {
    	World world = event.world;
        int x = event.x;
        int y = event.y;
        int z = event.z;

        Block placedBlock = event.block;

        if (placedBlock == ModBlocks.asphalt) {
            floodFillAir(world, x, y, z);
        }
    }

    public static void floodFill(World world, int x, int y, int z, Block airBlock) {
        if (x < 0 || y < 0 || z < 0 || x >= world.provider.getActualHeight() || y >= world.getHeight() || z >= world.provider.getActualHeight()) {
            return; // Exit if coordinates are out of bounds
        }

        Block currentBlock = world.getBlock(x, y, z);

        if (currentBlock != airBlock) {
            return; // Exit if the current block is not air
        }

        world.setBlock(x, y, z, ModBlocks.asphalt); // Replace the current block with air

        // Recursive flood fill in all adjacent blocks
        System.out.println(airBlock);
        floodFill(world, x + 1, y, z, airBlock);
        floodFill(world, x - 1, y, z, airBlock);
        floodFill(world, x, y + 1, z, airBlock);
        floodFill(world, x, y - 1, z, airBlock);
        floodFill(world, x, y, z + 1, airBlock);
        floodFill(world, x, y, z - 1, airBlock);

        // Check if any adjacent block is broken, if yes, stop filling
        if (world.getBlock(x + 1, y, z) != airBlock ||
            world.getBlock(x - 1, y, z) != airBlock ||
            world.getBlock(x, y + 1, z) != airBlock ||
            world.getBlock(x, y - 1, z) != airBlock ||
            world.getBlock(x, y, z + 1) != airBlock ||
            world.getBlock(x, y, z - 1) != airBlock) {
            return;
        }
    }
}
