package com.hbm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.lib.HbmCollection.EnumGunManufacturer;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.render.util.RenderScreenOverlay.Crosshair;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
	
	public World dimWorld;
	public int x;
	public int y;
	public int z;
	
	//animations!
	public HashMap<AnimType, BusAnimation> animations = new HashMap();
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
	//duration of every animation cycle, used also for how quickly a burst fire rifle can fire
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
	public List<String> comment = new ArrayList();

	//bullet configs for main and alt fire
	public List<Integer> config = new ArrayList();

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
	public static final String RSOUND_SHOTGUN = "hbm:weapon.shotgunReload";
	public static final String RSOUND_LAUNCHER = "hbm:weapon.rpgReload";
	public static final String RSOUND_GRENADE = "hbm:weapon.hkReload";
	public static final String RSOUND_GRENADE_NEW = "hbm:weapon.glReload";
	public static final String RSOUND_FATMAN = "hbm:weapon.fatmanReload";
	
	public GunConfiguration silenced() {
		this.firingSound = "hbm:weapon.silencerShoot";
		return this;
	}
	public static void spawnParticles(World world, double x, double y, double z, int count) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "smoke");
		data.setString("mode", "cloud");
		data.setInteger("count", count);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z),  new TargetPoint(world.provider.dimensionId, x, y, z, 250));
	}
}
