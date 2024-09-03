package com.hbm.items.weapon.sedna;

import com.hbm.items.weapon.sedna.mags.IMagazine;

public class Receiver {

	protected float baseDamage;
	protected int delayAfterFire;
	protected int roundsPerCycle = 1;
	protected boolean refireOnHold = false;
	protected int burstSize = 1;
	protected int delayAfterBurst;
	
	protected IMagazine magazine;
	
	public Receiver setMag(IMagazine magazine) {
		this.magazine = magazine;
		return this;
	}
}
