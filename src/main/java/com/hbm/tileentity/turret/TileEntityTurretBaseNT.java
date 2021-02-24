package com.hbm.tileentity.turret;

import java.util.List;

import com.hbm.handler.BulletConfiguration;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

/**
 * More over-engineered than ever, but chopping this thing into the smallest possible pieces makes it easier for my demented brain to comprehend
 * @author hbm
 *
 */
public abstract class TileEntityTurretBaseNT extends TileEntityMachineBase {

	//what way are we facing?
	public double rotationYaw;
	public double rotationPitch;
	//only used by clients for interpolation
	public double lastRotationYaw;
	public double lastRotationPitch;
	//is the turret on?
	public boolean isOn = false;
	//is the turret aimed at the target?
	public boolean aligned = false;

	public boolean targetPlayers = false;
	public boolean targetAnimals = false;
	public boolean targetMobs = true;
	public boolean targetMachines = true;
	
	public Entity target;
	
	//tally marks!
	public int stattrak;
	
	/**
	 * 		 X
	 * 
	 * 		YYY
	 * 		YYY
	 * 		YYY Z
	 * 
	 * 		X -> ai slot		(0)
	 * 		Y -> ammo slots		(1 - 9)
	 * 		Z -> battery slot	(10)
	 */
	
	public TileEntityTurretBaseNT() {
		super(11);
	}
	
	/**
	 * Reads the namelist from the AI chip in slot 0
	 * @return null if there is either no chip to be found or if the name list is empty, otherwise it just reads the strings from the chip's NBT
	 */
	public List<String> getWhitelist() {
		return null;
	}
	
	/**
	 * Finds the nearest acceptable target within range aand in line of sight
	 */
	protected void seekNewTarget() {
		
	}
	
	/**
	 * Turns the turret by a specific amount of degrees towards the target
	 */
	protected void alignTurret() {
		
		/* TODO */
		
		//if the delta is smaller than the angular velocity, just snap directly to the optimal position
		//check if the delta exceeds 180 and choose an appropriate shortest turning direction based on that
	}
	
	/**
	 * Checks line of sight to the passed entity along with whether the angle falls within swivel range
	 * @return
	 */
	public boolean entityInLOS(Entity e) {
		return false; //TODO: for the love of god don't forget to check the swivel range
	}
	
	/**
	 * How many degrees the turret can deviate from the target to be acceptable to fire at
	 * @return
	 */
	public double getAcceptableInaccuracy() {
		return 5;
	}
	
	/**
	 * How many degrees the turret can rotate per tick (4.5°/t = 90°/s or a half turn in two seconds)
	 * @return
	 */
	public double getTurretYawSpeed() {
		return 4.5D;
	}
	
	/**
	 * How many degrees the turret can lift per tick (3°/t = 60°/s or roughly the lowest to the highest point of an average turret in one second)
	 * @return
	 */
	public double getTurretPitchSpeed() {
		return 3D;
	}

	/**
	 * Makes turrets sad :'(
	 * @return
	 */
	public double getTurretDepression() {
		return 30D;
	}

	/**
	 * Makes turrets feel privileged
	 * @return
	 */
	public double getTurretElevation() {
		return 30D;
	}
	
	/**
	 * How many ticks until a target rescan is required
	 * @return
	 */
	public int getDecetorInterval() {
		return 20;
	}
	
	/**
	 * The pivot point of the turret, larger models have a default of 1.5
	 * @return
	 */
	public double getHeightOffset() {
		return 1.5D;
	}
	
	/**
	 * Horizontal offset for the spawn point of bullets
	 * @return
	 */
	public double getBarrelLength() {
		return 1.0D;
	}
	
	/**
	 * The pivot point of the turret, this position is used for LOS calculation and more
	 * @return
	 */
	public Vec3 getTurretPos() {
		//TODO: account for multiblock rotation
		return Vec3.createVectorHelper(xCoord, yCoord + getHeightOffset(), zCoord);
	}
	
	/**
	 * Yes, new turrets fire BulletNTs.
	 * @return
	 */
	protected abstract List<BulletConfiguration> getAmmoList();
	
	/*
	 * the void
	 * 
	 * 
	 * more stuff pending: the thing that makes the pew pew
	 * probably a separate method that consumes or checks ammo
	 * mayhaps some dangly bits that tie together the GUI with the AI chip's whitelist
	 */
	
	@Override
	public void updateEntity() {
		
		//hinga dinga durgen
		
		//let's see
		
		//check power, if it's turned on, all that jazz
		
		//is there an active enemy? good, aim at it (alignTurret does all that)
		
		//is the gun aimed? now shøøt
		
		//check ammo, spawn boolets if present, make empty clicking if not
		
		//decrement the timer, if the timer runs out, set the timer to the detector interval and choose a new target
		
		//target choosing time? seekNewTarget does all the work
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
