package com.hbm.tileentity.machine.fusion;

public interface IFusionPowerReceiver {

	/**
	 * fusionPower is the per-port output adjusted for the amount of connected receivers (i.e. when boilers share output energy)
	 * neutronPower is a fixed value provided by the recipe
	 */
	public void receiveFusionPower(long fusionPower, double neutronPower);
}
