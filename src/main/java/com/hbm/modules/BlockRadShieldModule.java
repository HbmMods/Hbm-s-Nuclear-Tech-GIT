package com.hbm.modules;

import com.hbm.interfaces.Untested;

@Untested
public class BlockRadShieldModule
{
	float EMShield;
	float neutShield;
	
	public void addEMShield(float shield)
	{
		EMShield = shield;
	}
	public void addNeutronShield(float shield)
	{
		neutShield = shield;
	}
}
