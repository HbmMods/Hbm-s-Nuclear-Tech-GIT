package com.hbm.world.generator.room;

import java.util.ArrayList;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;
import com.hbm.world.generator.TimedGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class JungleDungeonRoom extends CellularDungeonRoom {

	public JungleDungeonRoom(CellularDungeon parent) {
		super(parent);
	}

	public void generateMain(World world, int x, int y, int z) {
		
		DungeonToolbox.generateBoxTimed(world, x, y, z, parent.width, 1, parent.width, parent.floor);
		DungeonToolbox.generateBoxTimed(world, x, y + 1, z, parent.width, parent.height - 1, parent.width, Blocks.air);
		DungeonToolbox.generateBoxTimed(world, x, y + parent.height - 1, z, parent.width, 1, parent.width, parent.ceiling);
		
		int rtd = world.rand.nextInt(10);
		
		if(rtd == 0) {
			DungeonToolbox.generateBoxTimed(world, x + parent.width / 2 - 1, y, z + parent.width / 2 - 1, 3, 1, 3, new ArrayList() {{ add(ModBlocks.brick_jungle_cracked); add(ModBlocks.brick_jungle_lava); }});
		
		} else if(rtd == 1) {
			TimedGenerator.addOp(world, x + 1 + world.rand.nextInt(parent.width - 1), y + 1, z + world.rand.nextInt(parent.width - 1), ModBlocks.crate_jungle, 0, 2);
		}
	}
	
	public void generateWall(World world, int x, int y, int z, ForgeDirection wall, boolean door) {
		
		if(wall == ForgeDirection.NORTH) {
			DungeonToolbox.generateBoxTimed(world, x, y + 1, z, parent.width, parent.height - 2, 1, parent.wall);
			
			if(door)
				DungeonToolbox.generateBoxTimed(world, x + parent.width / 2 - 1, y + 1, z, 3, 3, 1, Blocks.air);
		}
		
		if(wall == ForgeDirection.SOUTH) {
			DungeonToolbox.generateBoxTimed(world, x, y + 1, z + parent.width - 1, parent.width, parent.height - 2, 1, parent.wall);
			
			if(door)
				DungeonToolbox.generateBoxTimed(world, x + parent.width / 2 - 1, y + 1, z + parent.width - 1, 3, 3, 1, Blocks.air);
		}
		
		if(wall == ForgeDirection.WEST) {
			DungeonToolbox.generateBoxTimed(world, x, y + 1, z, 1, parent.height - 2, parent.width, parent.wall);
			
			if(door)
				DungeonToolbox.generateBoxTimed(world, x, y + 1, z + parent.width / 2 - 1, 1, 3, 3, Blocks.air);
		}
		
		if(wall == ForgeDirection.EAST) {
			DungeonToolbox.generateBoxTimed(world, x + parent.width - 1, y + 1, z, 1, parent.height - 2, parent.width, parent.wall);
			
			if(door)
				DungeonToolbox.generateBoxTimed(world, x + parent.width - 1, y + 1, z + parent.width / 2 - 1, 1, 3, 3, Blocks.air);
		}
	}
}
