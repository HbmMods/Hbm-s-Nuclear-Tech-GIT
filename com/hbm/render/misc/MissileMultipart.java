package com.hbm.render.misc;

public class MissileMultipart {

	public MissilePart warhead;
	public MissilePart fuselage;
	public MissilePart fins;
	public MissilePart thruster;
	
	public double getHeight() {
		
		double height = 0;

		if(warhead != null)
			height += warhead.height;
		if(fuselage != null)
			height += fuselage.height;
		if(thruster != null)
			height += thruster.height;
		
		return height;
	}
	
	/*public boolean hadFuselage() {
		return fuselage != null;
	}*/

}
