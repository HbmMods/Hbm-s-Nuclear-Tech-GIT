package com.hbm.handler;

public class GunConfigurationEnergy extends GunConfiguration
{
	/** Max energy stored **/
	public long ammoChargeCap;
	/** Power consumption per shot **/
	public long ammoRate;
	/** Charge rate of the gun, similar to batteries **/
	public long chargeRate;
	public GunConfigurationEnergy()
	{
		reloadSound = "hbm:item.battery";
	}
	@Override
	public GunConfigurationEnergy clone()
	{
		GunConfigurationEnergy newConfig = (GunConfigurationEnergy) super.clone();
		newConfig.ammoChargeCap = ammoChargeCap;
		newConfig.ammoRate = ammoRate;
		newConfig.chargeRate = chargeRate;
		newConfig.reloadSound = reloadSound;
		return newConfig;
	}
}
