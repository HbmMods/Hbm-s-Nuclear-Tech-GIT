package com.hbm.items.weapon.sedna;

import com.hbm.handler.CasingEjector;
import com.hbm.items.weapon.sedna.mags.IMagazine;

public class Receiver {

	protected float baseDamage;
	protected int delayAfterFire;
	protected int roundsPerCycle = 1;
	protected boolean refireOnHold = false;
	protected int burstSize = 1;
	protected int delayAfterBurst = 0;
	protected CasingEjector ejector = null;
	protected IMagazine magazine;

	public Receiver dmg(float dmg) { this.baseDamage = dmg; return this; }
	public Receiver delay(int delay) { this.delayAfterFire = delay; return this; }
	public Receiver rounds(int rounds) { this.roundsPerCycle = rounds; return this; }
	public Receiver auto(boolean auto) { this.refireOnHold = auto; return this; }
	public Receiver burst(int size, int delay) { this.burstSize = size; this.delayAfterBurst = delay; return this; }
	public Receiver burst(CasingEjector ejector) { this.ejector = ejector; return this; }
	public Receiver mag(IMagazine magazine) { this.magazine = magazine; return this; }
}
