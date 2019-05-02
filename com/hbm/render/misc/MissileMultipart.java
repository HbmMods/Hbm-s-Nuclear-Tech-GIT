package com.hbm.render.misc;

import com.hbm.items.weapon.ItemMissile.PartType;

public class MissileMultipart {

	public MissilePart warhead;
	public MissilePart fuselage;
	public MissilePart fins;
	public MissilePart thruster;
	
	public double getHeight() {
		
		double height = 0;

		if(warhead != null && warhead.type == PartType.WARHEAD)
			height += warhead.height;
		if(fuselage != null && fuselage.type == PartType.FUSELAGE)
			height += fuselage.height;
		if(thruster != null && thruster.type == PartType.THRUSTER)
			height += thruster.height;
		
		return height;
	}
}
