package com.hbm.tileentity;

public abstract class TileEntityTickingBase extends TileEntityLoadedBase {

	public TileEntityTickingBase() { }

	public abstract String getInventoryName();

	//abstracting this method forces child classes to implement it
	//so i don't have to remember the fucking method name
	//was it update? onUpdate? updateTile? did it have any args?
	//shit i don't know man
	@Override
	public abstract void updateEntity();

	@Deprecated
	public void handleButtonPacket(int value, int meta) { }

}
