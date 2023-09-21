package com.hbm.handler;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

import org.eclipse.collections.api.factory.primitive.IntIntMaps;
import org.eclipse.collections.api.factory.primitive.IntLists;
import org.eclipse.collections.api.factory.primitive.ShortSets;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.api.set.primitive.MutableShortSet;

import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.main.MainRegistry;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

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
	//0 = normal, 1 = release, 2 = both
	public int gunMode;
	//0 = manual, 1 = automatic
	public int firingMode;
	//weapon won't fire after weapon breaks (main only)
	public int durability;
	
	/*
	 * All magazine related settings should be ignored if it is disabled.
	 * 
	 * Makes use of Eclipse Collections for efficient use of primitives,
	 * but it should be possible to convert easily enough if too large. 
	 */
	
	/// Mag related stuff
	/**If the gun should support a +1 behavior, ie for semi-automatic and full automatic weapons like pistols, most rifles, pump shotguns, and machine guns, but not revolvers or breach loaded guns (which shouldn't use the magazine system anyway).**/
	public boolean independentChamber = true;
	/**If the gun has funny behavior when trying to load or fire a bullet incompatible. May overlap with messed up NBT rather than someone trying to force a bullet to work, but that's an edge case.**/
	public boolean drm = false;
	/**Whether or not the gun should "absorb" the magazine when reloading and relinquish when reloading again (if applicable). Pistols and most rifles should do this, but revolvers don't due to the nature of speed-loaders.**/
	public boolean absorbsMag = true;
	/**Whether or not the gun should fallback to using the default mode if an appropriate magazine cannot be found. Relevant for revolvers.**/
	public boolean fallback = false;
	/**Usually used by revolvers, caches fired bullets for post-reload casing ejection.**/
	public boolean cacheBulletsForPostReloadCasingEjection = false;
	/**Ejector config for cached rounds**/
	public CasingEjector cacheEjector;
	/**If the gun uses a "true" belt, instead of just grabbing from inventory.**/
//	public boolean trueBelt = false;
	/**Map configurations to another, so the magazine and gun can use the same ammo item, but use a different bullet config.**/
	public MutableIntIntMap configMap = IntIntMaps.mutable.empty();
	/**Magazine items that the gun uses, storing their meta value/ordinal.**/
	public MutableShortSet magazines = ShortSets.mutable.empty();
//	public EnumSet<EnumMagazine> magazines = EnumSet.noneOf(EnumMagazine.class); // EnumSet inappropriate, since itemstacks will only report the ordinal and getting an enum's ordinal is very easy
	/**Magazines that are sort of allowed, but may contain rounds that aren't. DRM will have a say about this.**/
	public MutableShortSet badMagazines = ShortSets.mutable.empty();
	
	//animations!
//	public HashMap<AnimType, BusAnimation> animations = new HashMap<AnimType, BusAnimation>();
	public EnumMap<AnimType, BusAnimation> animations = new EnumMap<>(AnimType.class);// More efficient maybe??? (At the very least doesn't change behavior)
	//when sneaking, disables crosshair and centers the bullet spawn point
	public boolean hasSights;
	//texture overlay when sneaking
	public ResourceLocation scopeTexture;
	//whether the FOV multiplier should be absolute or multiplicative to other modifiers, multiplicative mode is experimental!
	public boolean absoluteFOV = true;
	//the target FOV/multiplied FOV modifier when sneaking
	public float zoomFOV = 0.0F;
	
	//how long the reload animation will play
	//MUST BE GREATER THAN ZERO ! ! !
	public int reloadDuration;
	//duration of every animation cycle
	public int firingDuration;
	//sound path to the reload sound
	public String reloadSound = "";
	//sound path to the shooting sound
	public String firingSound = "";
	public float firingVolume = 1.0F;
	public float firingPitch = 1.0F;
	//whether the reload sound should be played at the beginning or at the end of the reload
	public boolean reloadSoundEnd = true;
	public String equipSound = "";
	
	//how much ammo the clip can hold, 0 if drawn from inventory
	public int ammoCap;
	//0 does not allow direct reload, 1 is full clip, 2 is single bullet
	public int reloadType;
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
//	public List<Integer> config = new ArrayList<Integer>();
	public MutableIntList config = IntLists.mutable.empty();

	//crosshair
	public Crosshair crosshair;
	
	//casing eject behavior
	public CasingEjector ejector = null;
	/**If link particles should be spawned as well (currently unused). Spawns 2 particles per casing.**/
	public boolean links = false;

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
	
	@Override
	public GunConfiguration clone()
	{
		try
		{
			return (GunConfiguration) super.clone();
		} catch (CloneNotSupportedException e)
		{
			// Shouldn't happen, but just in case...
			MainRegistry.logger.catching(e);
			return new GunConfiguration();
		}
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(absoluteFOV, absorbsMag, allowsInfinity, ammoCap, animations, chargeRate, comment, config,
				configMap, crosshair, dischargePerShot, drm, durability, ejector, equipSound, firingDuration,
				firingMode, firingPitch, firingSound, firingVolume, gunMode, hasSights, independentChamber, magazines,
				manufacturer, maxCharge, name, rateOfFire, reloadDuration, reloadSound, reloadSoundEnd, reloadType,
				roundsPerCycle, scopeTexture, showAmmo, zoomFOV);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof GunConfiguration))
			return false;
		final GunConfiguration other = (GunConfiguration) obj;
		return absoluteFOV == other.absoluteFOV && absorbsMag == other.absorbsMag
				&& allowsInfinity == other.allowsInfinity && ammoCap == other.ammoCap
				&& Objects.equals(animations, other.animations) && chargeRate == other.chargeRate
				&& Objects.equals(comment, other.comment) && Objects.equals(config, other.config)
				&& Objects.equals(configMap, other.configMap) && crosshair == other.crosshair
				&& dischargePerShot == other.dischargePerShot && drm == other.drm && durability == other.durability
				&& Objects.equals(ejector, other.ejector) && Objects.equals(equipSound, other.equipSound)
				&& firingDuration == other.firingDuration && firingMode == other.firingMode
				&& Float.floatToIntBits(firingPitch) == Float.floatToIntBits(other.firingPitch)
				&& Objects.equals(firingSound, other.firingSound)
				&& Float.floatToIntBits(firingVolume) == Float.floatToIntBits(other.firingVolume)
				&& gunMode == other.gunMode && hasSights == other.hasSights
				&& independentChamber == other.independentChamber && Objects.equals(magazines, other.magazines)
				&& manufacturer == other.manufacturer && maxCharge == other.maxCharge
				&& Objects.equals(name, other.name) && rateOfFire == other.rateOfFire
				&& reloadDuration == other.reloadDuration && Objects.equals(reloadSound, other.reloadSound)
				&& reloadSoundEnd == other.reloadSoundEnd && reloadType == other.reloadType
				&& roundsPerCycle == other.roundsPerCycle && Objects.equals(scopeTexture, other.scopeTexture)
				&& showAmmo == other.showAmmo && Float.floatToIntBits(zoomFOV) == Float.floatToIntBits(other.zoomFOV);
	}

}
