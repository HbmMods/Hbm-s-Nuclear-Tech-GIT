package com.hbm.handler;

public class GunConfigurationEnergy extends GunConfiguration
{
	/** Max energy stored **/
	@SuppressWarnings("hiding")
	public long ammoCap;
	/** Power consumption per shot **/
	public long ammoRate;
	/** Charge rate of the gun, similar to batteries **/
	public long chargeRate;
	/** Sound that plays when reloading **/
	@SuppressWarnings("hiding")
	public String reloadSound = "hbm:item.battery";
	
	@Override
	public GunConfigurationEnergy clone()
	{
		GunConfigurationEnergy newConfig = (GunConfigurationEnergy) super.clone();
		newConfig.ammoCap = ammoCap;
		newConfig.ammoRate = ammoRate;
		newConfig.chargeRate = chargeRate;
		newConfig.reloadSound = reloadSound;
		return newConfig;
	}
}
