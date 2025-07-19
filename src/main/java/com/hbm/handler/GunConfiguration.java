package com.hbm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;

import net.minecraft.util.ResourceLocation;

public class GunConfiguration implements Cloneable {
	
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
	/** Amount of rounds per burst, irrelevant if not a burst fire weapon**/
	public int roundsPerBurst;
	//0 = normal, 1 = release, 2 = both
	public int gunMode;
	//0 = manual, 1 = automatic
	public int firingMode;
	//weapon won't fire after weapon breaks (main only)
	public int durability;
	
	//animations!
	public HashMap<AnimType, BusAnimation> animations = new HashMap<AnimType, BusAnimation>();
	//lazy-ish loading for animations, required for loading animations from ResourceManager, since that occurs after we've initialised the guns
	public Consumer<Void> loadAnimations;
	public boolean animationsLoaded = false;
	//when sneaking, disables crosshair and centers the bullet spawn point
	public boolean hasSights;
	//does this weapon behave like fully sick old-school boomer shooters
	public boolean isCentered;
	//texture overlay when sneaking
	public ResourceLocation scopeTexture;
	//whether the FOV multiplier should be absolute or multiplicative to other modifiers, multiplicative mode is experimental!
	public boolean absoluteFOV = true;
	//the target FOV/multiplied FOV modifier when sneaking
	public float zoomFOV = 0.0F;
	
	//duration of every animation cycle, used also for how quickly a burst fire rifle can fire
	public int firingDuration;
	//sound path to the shooting sound
	public String firingSound = "";
	public String firingSoundEmpty = null;
	public float firingVolume = 1.0F;
	public float firingPitch = 1.0F;
	//how long the reload animation will play
	//MUST BE GREATER THAN ZERO ! ! !
	public int reloadDuration;
	public int emptyReloadAdditionalDuration;
	//sound path to the reload sound
	public String reloadSound = "";
	public String reloadSoundEmpty = null;
	//whether the reload sound should be played at the beginning or at the end of the reload
	public boolean reloadSoundEnd = true;
	public String equipSound = "";
	
	//how much ammo the clip can hold, 0 if drawn from inventory
	public int ammoCap;
	//0 does not allow direct reload, 1 is full clip, 2 is single bullet
	public int reloadType;
	// If the animations are designed to be sequential, the last frame will be held until the next anmiation starts
	public boolean reloadAnimationsSequential = false;
	//whether or not the infinity enchantment should work
	public boolean allowsInfinity;
	//whether the ammo count should be displayed
	public boolean showAmmo = true;
	
	//for electrically powered weapons:
	//the Maximum capacity of the gun
	public long maxCharge;
	//the rate at which the gun is charged
	public long chargeRate;
	//how much energy is discharged per shot
	public long dischargePerShot;
	
	public String name = "";
	public EnumGunManufacturer manufacturer = EnumGunManufacturer.NONE;
	public List<String> comment = new ArrayList<String>();

	//bullet configs for main and alt fire
	public List<Integer> config = new ArrayList<Integer>();

	//crosshair
	public Crosshair crosshair;
	
	//casing eject behavior
	public CasingEjector ejector = null;

	public static final int MODE_NORMAL = 0;
	public static final int MODE_RELEASE = 1;
	public static final int MODE_BOTH = 1;

	public static final int FIRE_MANUAL = 0;
	public static final int FIRE_AUTO = 1;
	public static final int FIRE_BURST = 2;

	public static final int RELOAD_NONE = 0;
	public static final int RELOAD_FULL = 1;
	public static final int RELOAD_SINGLE = 2;

	public static final String RSOUND_REVOLVER = "hbm:weapon.revolverReload";
	public static final String RSOUND_RIFLE = "";
	public static final String RSOUND_MAG = "hbm:weapon.magReload";
	public static final String RSOUND_MAG_BOLT = "hbm:weapon.magReloadBolt";
	public static final String RSOUND_SHOTGUN = "hbm:weapon.shotgunReload";
	public static final String RSOUND_LAUNCHER = "hbm:weapon.rpgReload";
	public static final String RSOUND_GRENADE = "hbm:weapon.hkReload";
	public static final String RSOUND_GRENADE_NEW = "hbm:weapon.glReload";
	public static final String RSOUND_FATMAN = "hbm:weapon.fatmanReload";
	
	public GunConfiguration silenced() {
		this.firingSound = "hbm:weapon.silencerShoot";
		return this;
	}

}
