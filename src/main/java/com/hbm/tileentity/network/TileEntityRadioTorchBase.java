package com.hbm.tileentity.network;

import net.minecraft.tileentity.TileEntity;

public class TileEntityRadioTorchBase extends TileEntity {

	/** channel we're broadcasting on/listening to */
	public String channel = "";
	/** previous redstone state for input/output, needed for state change detection */
	protected int lastState = 0;
	/** last update tick, needed for receivers listening for changes */
	protected long lastUpdate;
	/** switches state change mode to tick-based polling */
	public boolean polling = false;
	/** switches redstone passthrough to custom signal mapping */
	public boolean customMap = false;
	/** custom mapping */
	public String[] mapping = new String[16];
}
