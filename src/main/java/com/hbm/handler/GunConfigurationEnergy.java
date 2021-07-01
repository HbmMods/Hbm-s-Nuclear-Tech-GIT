package com.hbm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunConfigurationEnergy extends GunConfiguration
{
	/** Max energy stored **/
	public long ammoCap;
	/** Power consumption per shot **/
	public long ammoRate;
	/** Charge rate of the gun, similar to batteries **/
	public long chargeRate;
}
