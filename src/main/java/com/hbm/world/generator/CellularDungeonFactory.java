package com.hbm.world.generator;

import com.hbm.world.generator.room.*;

@Deprecated
public class CellularDungeonFactory {

	public static CellularDungeon jungle;

	public static void init() {

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
