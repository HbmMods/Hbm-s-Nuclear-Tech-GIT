package com.hbm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;
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
	
	//animations!
	public HashMap<AnimType, BusAnimation> animations = new HashMap();
	//whether to not to disable crosshais when sneaking
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
	//whether or not the infinity enchantment should work
	public boolean allowsInfinity;
	//whether the ammo count should be displayed
	public boolean showAmmo = true;
	
	// Just experimenting here ~ UFFR
	public String damage = "";
	public List<String> advLore = new ArrayList();
	public List<String> advFuncLore = new ArrayList();
	
	public String name = "";
	public String manufacturer = "";
	public List<String> comment = new ArrayList();

	//bullet configs for main and alt fire
	public List<Integer> config = new ArrayList();

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
