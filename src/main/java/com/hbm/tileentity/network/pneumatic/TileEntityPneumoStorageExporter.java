package com.hbm.tileentity.network.pneumatic;


import api.hbm.redstoneoverradio.IRORInteractive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityPneumoStorageExporter extends TileEntityPneumaticMachineBase implements IRORInteractive {
	
	/** If requests should be pulled repeatedly every tick */
	public boolean continuousRequest = false;
	/** If ROR configuration has taken place, ignore manually defined filters entirely */
	public boolean rorConfiguredMode = false;
	/** What strategy to use for handling request filters */
	public int requestMode = 0;

	/** Each slot individually tries to pull as much as it can of the configured item */
	public static final int MODE_AS_MUCH_AS_POSSIBLE = 0;
	/** Each slot individually tries to pull the exact quantity configured */
	public static final int MODE_FULL_STACK = 1;
	/** All request slots try to pull the desired quantities simultaneously */
	public static final int MODE_FULL_REQUEST = 2;

	public TileEntityPneumoStorageExporter() {
		super(18);
	}

	@Override
	public String getName() {
		return "container.pneumoStorageExporter";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return null; }

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_FUNCTION + "setfilter" + NAME_SEPARATOR + "slot" + PARAM_SEPARATOR + "itemid" + PARAM_SEPARATOR + "itemmeta" + PARAM_SEPARATOR + "amount",
				PREFIX_FUNCTION + "setcontinuous" + NAME_SEPARATOR + "slot" + PARAM_SEPARATOR + "itemid" + PARAM_SEPARATOR + "itemmeta" + PARAM_SEPARATOR + "amount",
				PREFIX_FUNCTION + "request",
				PREFIX_FUNCTION + "requestslot" + NAME_SEPARATOR + "slot",
				PREFIX_FUNCTION + "checkavailability" + NAME_SEPARATOR + "itemid" + PARAM_SEPARATOR + "itemmeta" + PARAM_SEPARATOR + "returnchannel",
		};
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		return null;
	}
}
