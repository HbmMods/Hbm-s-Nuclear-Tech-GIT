package com.hbm.world.generator;

import com.hbm.inventory.RecipesCommon.MetaBlock;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Deprecated
public class CellularDungeonRoom {
	
	protected CellularDungeon parent;
	protected CellularDungeonRoom daisyChain = null;
	protected ForgeDirection daisyDirection = ForgeDirection.UNKNOWN;
	
	public CellularDungeonRoom(CellularDungeon parent) {
		this.parent = parent;
	}
	
	//per generation, only one door can be made. rooms having multiple doors will be the consequence of daisychaining.
	//the initial room will use an invalid type to not spawn any doors.
	public void generate(World world, int x, int y, int z, ForgeDirection door) {
		
		generateMain(world, x, y, z);
		
		for(int i = 2; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			generateWall(world, x, y, z, dir, dir == door);
		}
	}
	
	public void generateMain(World world, int x, int y, int z) {
		
		DungeonToolbox.generateBox(world, x, y, z, parent.width, 1, parent.width, parent.floor);
		DungeonToolbox.generateBox(world, x, y + 1, z, parent.width, parent.height - 1, parent.width, new MetaBlock(Blocks.air));
		DungeonToolbox.generateBox(world, x, y + parent.height - 1, z, parent.width, 1, parent.width, parent.ceiling);
	}
	
	public void generateWall(World world, int x, int y, int z, ForgeDirection wall, boolean door) {
		
		if(wall == ForgeDirection.NORTH) {
			DungeonToolbox.generateBox(world, x, y + 1, z, parent.width, parent.height - 2, 1, parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + parent.width / 2, y + 1, z, 1, 2, 1, new MetaBlock(Blocks.air));
		}
		
		if(wall == ForgeDirection.SOUTH) {
			DungeonToolbox.generateBox(world, x, y + 1, z + parent.width - 1, parent.width, parent.height - 2, 1, parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + parent.width / 2, y + 1, z + parent.width - 1, 1, 2, 1, new MetaBlock(Blocks.air));
		}
		
		if(wall == ForgeDirection.WEST) {
			DungeonToolbox.generateBox(world, x, y + 1, z, 1, parent.height - 2, parent.width, parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x, y + 1, z + parent.width / 2, 1, 2, 1, new MetaBlock(Blocks.air));
		}
		
		if(wall == ForgeDirection.EAST) {
			DungeonToolbox.generateBox(world, x + parent.width - 1, y + 1, z, 1, parent.height - 2, parent.width, parent.wall);
			
			if(door)
				DungeonToolbox.generateBox(world, x + parent.width - 1, y + 1, z + parent.width / 2, 1, 2, 1, new MetaBlock(Blocks.air));
		}
	}

}
