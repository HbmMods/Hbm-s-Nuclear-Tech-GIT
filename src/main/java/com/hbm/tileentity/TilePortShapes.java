package com.hbm.tileentity;

import com.hbm.lib.Library;
import com.hbm.tileentity.TilePort.PortDef;

import net.minecraftforge.common.util.ForgeDirection;

public class TilePortShapes {
	
	/** Port of the legacy "all around" system, single block port with connections in all directions */
	public static PortDef[] around(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x, y, z, ForgeDirection.VALID_DIRECTIONS)
		};
	}
	
	/** Like around, but the topmost port is shifted up by two blocks */
	public static PortDef[] mixer(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x, y, z, Library.POS_X, Library.NEG_X, Library.POS_Z, Library.NEG_Z, Library.NEG_Y),
				PortDef.make(x, y + 2, z, Library.POS_Y),
		};
	}
	
	/** Like around, but only the horizontal commpass directions */
	public static PortDef[] horizontal(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x, y, z, Library.POS_X, Library.NEG_X, Library.POS_Z, Library.NEG_Z, Library.NEG_Y),
		};
	}
	
	/** Assembler-like 8 port with 12 connections */
	public static PortDef[] assembler(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x - 1, y, z - 1, Library.NEG_X, Library.NEG_Z),
				PortDef.make(x + 0, y, z - 1, Library.NEG_Z),
				PortDef.make(x + 1, y, z - 1, Library.POS_X, Library.NEG_Z),
				PortDef.make(x + 1, y, z + 0, Library.POS_X),
				PortDef.make(x + 1, y, z + 1, Library.POS_X, Library.POS_Z),
				PortDef.make(x + 0, y, z + 1, Library.POS_Z),
				PortDef.make(x - 1, y, z + 1, Library.NEG_X, Library.POS_Z),
				PortDef.make(x - 1, y, z + 0, Library.NEG_X),
		};
	}
	
	/** Combination oven-like 16 port with 24 connections */
	public static PortDef[] comboven(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x - 1, y, z - 1, Library.NEG_X, Library.NEG_Z),
				PortDef.make(x + 0, y, z - 1, Library.NEG_Z),
				PortDef.make(x + 1, y, z - 1, Library.POS_X, Library.NEG_Z),
				PortDef.make(x + 1, y, z + 0, Library.POS_X),
				PortDef.make(x + 1, y, z + 1, Library.POS_X, Library.POS_Z),
				PortDef.make(x + 0, y, z + 1, Library.POS_Z),
				PortDef.make(x - 1, y, z + 1, Library.NEG_X, Library.POS_Z),
				PortDef.make(x - 1, y, z + 0, Library.NEG_X),
				PortDef.make(x - 1, y + 1, z - 1, Library.NEG_X, Library.NEG_Z),
				PortDef.make(x + 0, y + 1, z - 1, Library.NEG_Z),
				PortDef.make(x + 1, y + 1, z - 1, Library.POS_X, Library.NEG_Z),
				PortDef.make(x + 1, y + 1, z + 0, Library.POS_X),
				PortDef.make(x + 1, y + 1, z + 1, Library.POS_X, Library.POS_Z),
				PortDef.make(x + 0, y + 1, z + 1, Library.POS_Z),
				PortDef.make(x - 1, y + 1, z + 1, Library.NEG_X, Library.POS_Z),
				PortDef.make(x - 1, y + 1, z + 0, Library.NEG_X),
		};
	}
	
	/** Refinery-like 4 port with 8 connections */
	public static PortDef[] refinery(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x - 1, y, z - 1, Library.NEG_X, Library.NEG_Z),
				PortDef.make(x + 1, y, z - 1, Library.POS_X, Library.NEG_Z),
				PortDef.make(x + 1, y, z + 1, Library.POS_X, Library.POS_Z),
				PortDef.make(x - 1, y, z + 1, Library.NEG_X, Library.POS_Z),
		};
	}
	
	/** Flare stack-like 4 port with 4 connections */
	public static PortDef[] flare(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x + 0, y, z - 1, Library.NEG_Z),
				PortDef.make(x + 1, y, z + 0, Library.POS_X),
				PortDef.make(x + 0, y, z + 1, Library.POS_Z),
				PortDef.make(x - 1, y, z + 0, Library.NEG_X),
		};
	}
	
	/** Solderer-like 4 port with 8 connections */
	public static PortDef[] solderer(int x, int y, int z, int meta) {
		ForgeDirection dir = ForgeDirection.getOrientation(meta - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new PortDef[] {
				PortDef.make(x, y, z, dir, rot.getOpposite()),
				PortDef.make(x + rot.offsetX, y, z + rot.offsetZ, dir, rot),
				PortDef.make(x - dir.offsetX, y, z - dir.offsetZ, dir.getOpposite(), rot.getOpposite()),
				PortDef.make(x - dir.offsetX + rot.offsetX, y, z - dir.offsetZ + rot.offsetZ, dir.getOpposite(), rot),
		};
	}
	
	/** Condenser-like 4 port with 8 connections */
	public static PortDef[] condenser(int x, int y, int z, int meta) {
		ForgeDirection dir = ForgeDirection.getOrientation(meta - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new PortDef[] {
				PortDef.make(x + rot.offsetX * 3, y + 1, z + rot.offsetZ * 3, rot),
				PortDef.make(x - rot.offsetX * 3, y + 1, z - rot.offsetZ * 3, rot.getOpposite()),
				PortDef.make(x + dir.offsetX - rot.offsetX, y + 1, z + dir.offsetZ - rot.offsetZ, dir),
				PortDef.make(x + dir.offsetX + rot.offsetX, y + 1, z + dir.offsetZ + rot.offsetZ, dir),
				PortDef.make(x - dir.offsetX - rot.offsetX, y + 1, z - dir.offsetZ - rot.offsetZ, dir.getOpposite()),
				PortDef.make(x - dir.offsetX + rot.offsetX, y + 1, z - dir.offsetZ + rot.offsetZ, dir.getOpposite()),
		};
	}
	
	/** Flare stack-like 4 port with 4 connections */
	public static PortDef[] auxTower(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x + 0, y, z - 2, Library.NEG_Z),
				PortDef.make(x + 2, y, z + 0, Library.POS_X),
				PortDef.make(x + 0, y, z + 2, Library.POS_Z),
				PortDef.make(x - 2, y, z + 0, Library.NEG_X),
		};
	}
	
	/** Flare stack-like 4 port with 4 connections */
	public static PortDef[] bigTower(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x - 3, y, z - 4, Library.NEG_Z),
				PortDef.make(x + 0, y, z - 4, Library.NEG_Z),
				PortDef.make(x + 3, y, z - 4, Library.NEG_Z),
				PortDef.make(x + 4, y, z - 3, Library.POS_X),
				PortDef.make(x + 4, y, z + 0, Library.POS_X),
				PortDef.make(x + 4, y, z + 3, Library.POS_X),
				PortDef.make(x - 3, y, z + 4, Library.POS_Z),
				PortDef.make(x + 0, y, z + 4, Library.POS_Z),
				PortDef.make(x + 3, y, z + 4, Library.POS_Z),
				PortDef.make(x - 4, y, z - 3, Library.NEG_X),
				PortDef.make(x - 4, y, z + 0, Library.NEG_X),
				PortDef.make(x - 4, y, z + 3, Library.NEG_X),
		};
	}
	
	/** Six ports, each centered on the face of a 3x3 cube */
	public static PortDef[] liquefactor(int x, int y, int z) {
		return new PortDef[] {
				PortDef.make(x, y + 3, z, Library.POS_Y),
				PortDef.make(x, y, z, Library.NEG_Y),
				PortDef.make(x + 1, y + 1, z, Library.POS_X),
				PortDef.make(x - 1, y + 1, z, Library.NEG_X),
				PortDef.make(x, y + 1, z + 1, Library.POS_Z),
				PortDef.make(x, y + 1, z - 1, Library.NEG_Z)
		};
	}
}
