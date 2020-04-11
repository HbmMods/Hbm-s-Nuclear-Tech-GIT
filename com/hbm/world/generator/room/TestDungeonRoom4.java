package com.hbm.world.generator.room;

import java.util.ArrayList;

import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TestDungeonRoom4 extends CellularDungeonRoom {
	
	public TestDungeonRoom4(CellularDungeon parent, CellularDungeonRoom daisyChain, ForgeDirection dir) {
		super(parent);
		this.daisyChain = daisyChain;
		this.daisyDirection = dir;
	}

	public void generateMain(World world, int x, int y, int z) {
		
		super.generateMain(world, x, y, z);
		DungeonToolbox.generateBox(world, x, y + parent.height - 2, z, parent.width, 1, parent.width, new ArrayList() {{ add(Blocks.air); add(Blocks.web); }});
	}
	
	public void generateWall(World world, int x, int y, int z, ForgeDirection wall, boolean door) {
		
		if(wall != ForgeDirection.NORTH)
			super.generateWall(world, x, y, z, wall, door);
	}
}