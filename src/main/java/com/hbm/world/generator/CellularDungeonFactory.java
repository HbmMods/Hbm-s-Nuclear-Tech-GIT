package com.hbm.world.generator;

import com.hbm.world.generator.room.*;

import net.minecraftforge.common.util.ForgeDirection;

public class CellularDungeonFactory {

	public static CellularDungeon meteor;
	public static CellularDungeon jungle;
	
	public static void init() {
		
		meteor = new TestDungeon(11, 7, 11, 11, 150, 3);
		meteor.rooms.add(new TestDungeonRoom1(meteor));
		meteor.rooms.add(new TestDungeonRoom2(meteor));
		meteor.rooms.add(new TestDungeonRoom3(meteor));
		meteor.rooms.add(new TestDungeonRoom4(meteor, new TestDungeonRoom5(meteor), ForgeDirection.NORTH));
		meteor.rooms.add(new TestDungeonRoom6(meteor));
		meteor.rooms.add(new TestDungeonRoom7(meteor));
		meteor.rooms.add(new TestDungeonRoom8(meteor));
		
		jungle = new JungleDungeon(5, 5, 21, 21, 500, 5);
		jungle.rooms.add(new JungleDungeonRoom(jungle));
	}

}
