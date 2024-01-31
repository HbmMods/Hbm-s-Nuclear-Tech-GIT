package com.hbm.items.weapon;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.GunConfiguration;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationKeyframe;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.HbmAnimations.AnimType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemGunPip extends ItemGunBase {
	
	public static long lastShot;
	public static List<double[]> smokeNodes = new ArrayList();

	public ItemGunPip(GunConfiguration config) {
		super(config);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void updateClient(ItemStack stack, World world, EntityPlayer entity, int slot, boolean isCurrentItem) {
		super.updateClient(stack, world, entity, slot, isCurrentItem);
		
		boolean smoking = lastShot + 2000 > System.currentTimeMillis();
		
		if(!smoking && !smokeNodes.isEmpty()) {
			smokeNodes.clear();
		}
		
		if(smoking) {
			Vec3 prev = Vec3.createVectorHelper(-entity.motionX, -entity.motionY, -entity.motionZ);
			prev.rotateAroundY((float) (entity.rotationYaw * Math.PI / 180D));
			double accel = 15D;
			double side = (entity.rotationYaw - entity.prevRotationYawHead) * 0.1D;
			double waggle = 0.025D;
			
			for(double[] node : smokeNodes) {
				node[0] += -prev.zCoord * accel + world.rand.nextGaussian() * waggle;
				node[1] += prev.yCoord + 1.5D;
				node[2] += prev.xCoord * accel + world.rand.nextGaussian() * waggle + side;
			}
			
			double alpha = (System.currentTimeMillis() - lastShot) / 2000D;
			alpha = (1 - alpha) * 0.5D;
			
			if(this.getIsReloading(stack)) alpha = 0;
			
			smokeNodes.add(new double[] {0, 0, 0, alpha});
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(ItemStack stack, AnimType type) {
		
		if(type == AnimType.EQUIP) {
				return new BusAnimation()
						.addBus("ROTATE", new BusAnimationSequence()
								.addKeyframePosition(-360, 0, 0, 350)
								);
		}
		
		if(type == AnimType.CYCLE) {
			lastShot = System.currentTimeMillis();
			int s = 1;
			return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframePosition(0, 0, 0, 50 * s)
						.addKeyframePosition(0, 0, -3, 50 * s)
						.addKeyframePosition(0, 0, 0, 250 * s)
						)
				.addBus("HAMMER", new BusAnimationSequence()
						.addKeyframePosition(0, 0, 1, 50 * s)
						.addKeyframePosition(0, 0, 1, 300 * s)
						.addKeyframePosition(0, 0, 0, 200 * s)
						)
				.addBus("DRUM", new BusAnimationSequence()
						.addKeyframePosition(0, 0, 1, 50 * s)
						);
		 }
		
		if(type == AnimType.RELOAD) {
			int s = 1;
				return new BusAnimation()
						.addBus("RELAOD_TILT", new BusAnimationSequence()
								.addKeyframePosition(-15, 0, 0, 100 * s)
								.addKeyframePosition(65, 0, 0, 100 * s) //200
								.addKeyframePosition(45, 0, 0, 50 * s) //250
								.addKeyframePosition(0, 0, 0, 200 * s) //450
								.addKeyframePosition(0, 0, 0, 1450 * s) //1900
								.addKeyframePosition(-80, 0, 0, 100 * s) //2000
								.addKeyframePosition(-80, 0, 0, 100 * s) //2100
								.addKeyframePosition(0, 0, 0, 200 * s) //2300
								)
						.addBus("RELOAD_CYLINDER", new BusAnimationSequence()
								.addKeyframePosition(0, 0, 0, 200 * s)
								.addKeyframePosition(90, 0, 0, 100 * s) //300
								.addKeyframePosition(90, 0, 0, 1700 * s) //2000
								.addKeyframePosition(0, 0, 0, 70 * s) //2100
								)
						.addBus("RELOAD_LIFT", new BusAnimationSequence()
								.addKeyframePosition(0, 0, 0, 350 * s)
								.addKeyframePosition(-45, 0, 0, 250 * s) //600
								.addKeyframePosition(-45, 0, 0, 350 * s) //950
								.addKeyframePosition(-15, 0, 0, 200 * s) //1150
								.addKeyframePosition(-15, 0, 0, 1050 * s) //2200
								.addKeyframePosition(0, 0, 0, 100 * s) //2300
								)
						.addBus("RELOAD_JOLT", new BusAnimationSequence()
								.addKeyframePosition(0, 0, 0, 600 * s)
								.addKeyframePosition(2, 0, 0, 50 * s) //650
								.addKeyframePosition(0, 0, 0, 100 * s) //750
								)
						.addBus("RELOAD_BULLETS", new BusAnimationSequence()
								.addKeyframePosition(0, 0, 0, 650 * s)
								.addKeyframePosition(10, 0, 0, 300 * s) //950
								.addKeyframePosition(10, 0, 0, 200 * s) //1150
								.addKeyframePosition(0, 0, 0, 700 * s) //1850
								)
						.addBus("RELOAD_BULLETS_CON", new BusAnimationSequence()
								.addKeyframePosition(1, 0, 0, 0 * s)
								.addKeyframePosition(1, 0, 0, 950 * s)
								.addKeyframePosition(0, 0, 0, 1 * s)
								);
		}
		
		return null;
	}
}
