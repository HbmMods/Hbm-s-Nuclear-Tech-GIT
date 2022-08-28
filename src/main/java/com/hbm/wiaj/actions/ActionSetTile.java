package com.hbm.wiaj.actions;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

import net.minecraft.tileentity.TileEntity;

public class ActionSetTile implements IJarAction {
	
	int x;
	int y;
	int z;
	TileEntity tile;
	
	public ActionSetTile(int x, int y, int z, TileEntity tile) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tile = tile;
	}

	@Override
	public int getDuration() {
		return 0;
	}

	@Override
	public void act(WorldInAJar world, JarScene scene) {
		world.setTileEntity(x, y, z, tile);
	}
}
