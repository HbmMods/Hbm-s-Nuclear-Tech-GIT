package com.hbm.world.generator.room;

import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class JungleDungeonRoom extends CellularDungeonRoom {

	public JungleDungeonRoom(CellularDungeon parent) {
		super(parent);
	}
	
	public void generateWall(World world, int x, int y, int z, ForgeDirection wall, boolean door) {
		
		if(wall == ForgeDirection.NORTH) {
			DungeonToolbox.generateBox(world, x, y + 1, z, parent.width, parent.height - 2, 1, parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y + 1, z, 3, 3, 1, Blocks.air);
		}
		
		if(wall == ForgeDirection.SOUTH) {
			DungeonToolbox.generateBox(world, x, y + 1, z + parent.width - 1, parent.width, parent.height - 2, 1, parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + parent.width / 2 - 1, y + 1, z + parent.width - 1, 3, 3, 1, Blocks.air);
		}
		
		if(wall == ForgeDirection.WEST) {
			DungeonToolbox.generateBox(world, x, y + 1, z, 1, parent.height - 2, parent.width, parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x, y + 1, z + parent.width / 2 - 1, 1, 3, 3, Blocks.air);
		}
		
		if(wall == ForgeDirection.EAST) {
			DungeonToolbox.generateBox(world, x + parent.width - 1, y + 1, z, 1, parent.height - 2, parent.width, parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + parent.width - 1, y + 1, z + parent.width / 2 - 1, 1, 3, 3, Blocks.air);
		}
	}
}
