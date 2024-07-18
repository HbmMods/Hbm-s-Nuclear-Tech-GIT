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
		meteor.rooms.add(new TestDungeonRoom8(meteor));
		
		jungle = new JungleDungeon(5, 5, 25, 25, 700, 6);
		for(int i = 0; i < 10; i++) jungle.rooms.add(new JungleDungeonRoom(jungle));
		jungle.rooms.add(new JungleDungeonRoomArrow(jungle));
		jungle.rooms.add(new JungleDungeonRoomArrowFire(jungle));
		jungle.rooms.add(new JungleDungeonRoomFire(jungle));
		jungle.rooms.add(new JungleDungeonRoomMagic(jungle));
		jungle.rooms.add(new JungleDungeonRoomMine(jungle));
		jungle.rooms.add(new JungleDungeonRoomPillar(jungle));
		jungle.rooms.add(new JungleDungeonRoomPoison(jungle));
		jungle.rooms.add(new JungleDungeonRoomRad(jungle));
		jungle.rooms.add(new JungleDungeonRoomRubble(jungle));
		jungle.rooms.add(new JungleDungeonRoomSlowness(jungle));
		jungle.rooms.add(new JungleDungeonRoomSpiders(jungle));
		jungle.rooms.add(new JungleDungeonRoomSpikes(jungle));
		jungle.rooms.add(new JungleDungeonRoomWeakness(jungle));
		jungle.rooms.add(new JungleDungeonRoomWeb(jungle));
		jungle.rooms.add(new JungleDungeonRoomZombie(jungle));
	}

}
