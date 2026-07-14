package com.hbm.tileentity;

import com.hbm.interfaces.NotableComments;

@NotableComments
public abstract class TileEntityTickingBase extends TileEntityLoadedBase {

	public TileEntityTickingBase() { }

	//abstracting this method forces child classes to implement it
	//so i don't have to remember the fucking method name
	//was it update? onUpdate? updateTile? did it have any args?
	//shit i don't know man
	@Override
	public abstract void updateEntity();

}
