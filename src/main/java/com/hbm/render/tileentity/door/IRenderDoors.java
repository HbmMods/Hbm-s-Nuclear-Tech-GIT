package com.hbm.render.tileentity.door;

import java.nio.DoubleBuffer;

import com.hbm.tileentity.TileEntityDoorGeneric;

public interface IRenderDoors {

	public void render(TileEntityDoorGeneric door, DoubleBuffer buf);
}
