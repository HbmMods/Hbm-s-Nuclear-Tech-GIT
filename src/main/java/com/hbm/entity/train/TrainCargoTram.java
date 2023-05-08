package com.hbm.entity.train;

import com.hbm.blocks.rail.IRailNTM.TrackGauge;
import com.hbm.inventory.container.ContainerCrateSteel;
import com.hbm.inventory.gui.GUICrateSteel;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TrainCargoTram extends EntityRailCarRidable implements IGUIProvider {

	/*
	 * 
	 *     _________
	 *    | |       \          <--
	 *    | |       |___
	 *    | |       |  |                             |
	 * _O\|_|_______|__|_____________________________|/O_
	 * |____|                                      |____|
	 *    \__________________________________________/
	 *        '( + )'                      '( + )'
	 * 
	 */

	public TrainCargoTram(World world) {
		super(world);
		this.setSize(2F, 1F);
	}
	
	public double speed = 0;
	public static final double maxSpeed = 0.5;
	public static final double acceleration = 0.01;
	public static final double deceleration = 0.95;

	@Override
	public double getCurrentSpeed() { // in its current form, only call once per tick
		
		if(this.riddenByEntity instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) this.riddenByEntity;
			
			if(player.moveForward > 0) {
				speed += acceleration;
			} else if(player.moveForward < 0) {
				speed -= acceleration;
			} else {
				speed *= deceleration;
			}
			
		} else {
			speed *= deceleration;
		}
		
		speed = MathHelper.clamp_double(speed, -maxSpeed, maxSpeed);
		
		return speed;
	}

	@Override
	public TrackGauge getGauge() {
		return TrackGauge.STANDARD;
	}

	@Override
	public double getLengthSpan() {
		return 2;
	}

	@Override
	public Vec3 getRiderSeatPosition() {
		return Vec3.createVectorHelper(0.375, 1.75, 0.5);
	}

	@Override
	public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
		if(!this.worldObj.isRemote && !this.isDead) {
			this.setDead();
		}
		
		return true;
	}

	@Override
	public boolean shouldRiderSit() {
		return true;
	}

	@Override
	public Vec3[] getPassengerSeats() {
		return new Vec3[] {
				Vec3.createVectorHelper(0.5, 1.75, -1.5),
				Vec3.createVectorHelper(-0.5, 1.75, -1.5)
		};
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.getEntityName() : "container.trainTram";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		//return new ContainerTrainCargoTram(player.inventory, (TrainCargoTram)player.worldObj.getEntityByID(x));
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		//return new GUITrainCargoTram(player.inventory, (TrainCargoTram) player.worldObj.getEntityByID(x));
		return null;
	}
}
