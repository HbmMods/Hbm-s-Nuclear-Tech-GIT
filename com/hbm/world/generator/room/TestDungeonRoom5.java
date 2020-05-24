package com.hbm.world.generator.room;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TestDungeonRoom5 extends CellularDungeonRoom {
	
	public TestDungeonRoom5(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x, y + parent.height - 2, z, parent.width, 1, parent.width, new ArrayList() {{ add(Blocks.air); add(Blocks.web); }});

		DungeonToolbox.generateBox(world, x + 1, y, z + 1, parent.width - 2, 1, parent.width - 2, new ArrayList() {{ add(ModBlocks.meteor_polished); add(ModBlocks.meteor_polished); add(ModBlocks.meteor_polished); add(ModBlocks.meteor_polished); add(ModBlocks.meteor_polished); add(ModBlocks.meteor_spawner); }});
	}

	public void generateWall(World world, int x, int y, int z, ForgeDirection wall, boolean door) {
		
		if(wall != ForgeDirection.SOUTH)
			super.generateWall(world, x, y, z, wall, door);
	}
}