package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TestDungeonRoom6 extends CellularDungeonRoom {

	public TestDungeonRoom6(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x + 1, y, z + 1, parent.width - 2, 1, parent.width - 2, ModBlocks.toxic_block);
		DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y, z + parent.width / 2 - 1, 3, 1, 3, ModBlocks.meteor_brick_chiseled);
		world.setBlock(x + parent.width / 2, y, z + parent.width / 2, ModBlocks.meteor_polished);

		world.setBlock(x + 1, y, z + parent.width / 2, ModBlocks.meteor_polished);
		world.setBlock(x + parent.width / 2, y, z + 1, ModBlocks.meteor_polished);
		
		world.setBlock(x + parent.width - 2, y, z + parent.width / 2, ModBlocks.meteor_polished);
		world.setBlock(x + parent.width / 2, y, z + parent.width - 2, ModBlocks.meteor_polished);
	}
	
	public void generateWall(World world, int x, int y, int z, ForgeDirection wall, boolean door) {

		super.generateWall(world, x, y, z, wall, door);
		
		if(!door)
			return;
		
		if(wall == ForgeDirection.NORTH) {
			DungeonToolbox.generateBox(world, x + parent.width / 2, y, z + 1, 1, 1, parent.width / 2 - 2, ModBlocks.meteor_polished);
		}
		
		if(wall == ForgeDirection.SOUTH) {
			DungeonToolbox.generateBox(world, x + parent.width / 2, y, z + parent.width / 2 + 2, 1, 1, parent.width / 2 - 2, ModBlocks.meteor_polished);
		}
		
		if(wall == ForgeDirection.WEST) {
			DungeonToolbox.generateBox(world, x + 1, y, z + parent.width / 2, parent.width / 2 - 2, 1, 1, ModBlocks.meteor_polished);
		}
		
		if(wall == ForgeDirection.EAST) {
			DungeonToolbox.generateBox(world, x + parent.width / 2 + 2, y, z + parent.width / 2, parent.width / 2 - 2, 1, 1, ModBlocks.meteor_polished);
		}
	}
}
