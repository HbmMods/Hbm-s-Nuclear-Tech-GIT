package com.hbm.handler;

import java.util.ArrayList;
import java.util.List;

import com.hbm.render.util.RenderScreenOverlay.Crosshair;

public class GunConfiguration {
	
	/**
	 * alt function restrictions:
	 * alt can not be reloaded (reload type of 0, ammo cap of 0)
	 * alt cooldown and main cooldown are shared (alt cooldown will almoast always be greater or equal)
	 * alt is always the lower priority, mouse2 will be canceled then mouse1 is activated at the same time
	 * restrictions must be applied in gun's logic, mechanism may be dysfunctional if these rules are ignored
	 */

	//amount of ticks between each bullet
	public int rateOfFire;
	//amount of bullets fired per delay passed
	public int roundsPerCycle;
	//0 = normal, 1 = release, 2 = both
	public int gunMode;
	//0 = manual, 1 = automatic
	public int firingMode;
	//weapon won't fire after weapon breaks (main only)
	public int durability;
	//weapon does not break
	public boolean unbreakable;
	
	//whether or not there is a reload delay animation
	public boolean hasReloadAnim;
	//whether or not there is a firing delay with animation
	public boolean hasFiringAnim;
	//whether there is a warmup duration for spinup
	public boolean hasSpinup;
	//whether there is a cooldown duration for spindown
	public boolean hasSpindown;
	//whether or not to disable crosshairs when sneaking
	public boolean hasSights;
	
	//how long the reload animation will play
	//MUST BE GREATER THAN ZERO ! ! !
	public int reloadDuration;
	//duration of every animation cycle
	public int firingDuration;
	//sound path to the reload sound
	public String reloadSound = "";
	//sound path to the shooting sound
	public String firingSound = "";
	public float firingPitch = 1.0F;
	//whether the reload sound should be played at the beginning or at the end of the reload
	public boolean reloadSoundEnd = true;
	
	//how much ammo the clip can hold, 0 if drawn from inventory
	public int ammoCap;
	//0 does not allow direct reload, 1 is full clip, 2 is single bullet
	public int reloadType;
	//determines the number of shells loaded each time a single bullet is loaded in
	public int shellsPerReload = 1;
	//whether or not the infinity enchantment should work
	public boolean allowsInfinity;
	//indicates the gun doesn't use ammo (IE energy pistol)
	public boolean ammoless;
	//indicates the type of ammo to use for ammoless guns
	public Integer ammoType;
	//indicates the NBT tag to use for the left side of the ammo display
	public String ammoDisplayTag;
	//indicated the value to display on the right side of the ammo display
	public String ammoMaxValue;
	//what to call ammo in the tooltip
	public String ammoName;
	
	public String name = "";
	public String manufacturer = "";
	public List<String> comment = new ArrayList();

	//bullet configs for main and alt fire
	public List<Integer> config;

	//crosshair
	public Crosshair crosshair;

	public static final int MODE_NORMAL = 0;
	public static final int MODE_RELEASE = 1;
	public static final int MODE_BOTH = 1;

	public static final int FIRE_MANUAL = 0;
	public static final int FIRE_AUTO = 1;

	public static final int RELOAD_NONE = 0;
	public static final int RELOAD_FULL = 1;
	public static final int RELOAD_SINGLE = 2;

	public static final String RSOUND_REVOLVER = "hbm:weapon.revolverReload";
	public static final String RSOUND_RIFLE = "";
	public static final String RSOUND_MAG = "hbm:weapon.magReload";
	public static final String RSOUND_SHOTGUN = "hbm:weapon.shotgunReload";
	public static final String RSOUND_LAUNCHER = "hbm:weapon.rpgReload";
	public static final String RSOUND_GRENADE = "hbm:weapon.hkReload";
	public static final String RSOUND_FATMAN = "hbm:weapon.fatmanReload";
	
	public GunConfiguration silenced() {
		this.firingSound = "hbm:weapon.silencerShoot";
		return this;
	}

}
