package com.hbm.world.generator;

import com.hbm.world.generator.room.*;

import net.minecraftforge.common.util.ForgeDirection;

public class CellularDungeonFactory {
	
	public static CellularDungeon test;
	
	public static void init() {
		
		test = new TestDungeon(11, 7, 11, 11, 150);
		test.rooms.add(new TestDungeonRoom1(test));
		test.rooms.add(new TestDungeonRoom2(test));
		test.rooms.add(new TestDungeonRoom3(test));
		test.rooms.add(new TestDungeonRoom4(test, new TestDungeonRoom5(test), ForgeDirection.NORTH));
	}

}
