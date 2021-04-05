package com.hbm.tileentity.machine.rbmk;

public class TileEntityRBMKRod extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver {
	
	//amount of "neutron energy" buffered for the next tick to use for the reaction
	private float flux;

	public TileEntityRBMKRod() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.rbmkRod";
	}

	@Override
	public void receiveFlux(float flux) {
		this.flux += flux;
	}
}
