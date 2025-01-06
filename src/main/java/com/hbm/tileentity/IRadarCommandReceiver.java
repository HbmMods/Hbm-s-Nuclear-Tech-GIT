package com.hbm.tileentity;

import net.minecraft.entity.Entity;

public interface IRadarCommandReceiver {

	public boolean sendCommandPosition(int x, int y, int z);
	public boolean sendCommandEntity(Entity target);
}
