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

public class ItemGunBio extends ItemGunBase {

	public ItemGunBio(GunConfiguration config) {
		super(config);
	}
	
	/* just a test */
	public static long lastShot;
	public static List<double[]> smokeNodes = new ArrayList();
	
	@Override
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main) {
		lastShot = System.currentTimeMillis();
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
				node[0] += prev.xCoord * accel + world.rand.nextGaussian() * waggle + side;
				node[1] += prev.yCoord + 1.5D;
				node[2] += prev.zCoord * accel + world.rand.nextGaussian() * waggle;
			}
			
			double alpha = (System.currentTimeMillis() - ItemGunBio.lastShot) / 2000D;
			alpha = (1 - alpha) * 0.35D;
			
			if(this.getReloadCycle(stack) > 0) alpha = 0;
			
			smokeNodes.add(new double[] {0, 0, 0, alpha});
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BusAnimation getAnimation(ItemStack stack, AnimType type) {
		//GunConfiguration config = ((ItemGunBase) stack.getItem()).mainConfig;
		//return config.animations.get(type);
		
		 if(type == AnimType.CYCLE) return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, -3, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 250))
						)
				.addBus("HAMMER", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 50))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 300))
						.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))
						)
				.addBus("DRUM", new BusAnimationSequence()
						.addKeyframe(new BusAnimationKeyframe(0, 0, 1, 50))
						);
		
		 if(type == AnimType.RELOAD) return new BusAnimation()
				 .addBus("LATCH", new BusAnimationSequence()
							.addKeyframe(new BusAnimationKeyframe(0, 0, 90, 300))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 90, 2000))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 150))
						)
				 .addBus("FRONT", new BusAnimationSequence()
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 200))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 45, 150))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 45, 2000))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 75))
						)
				 .addBus("RELOAD_ROT", new BusAnimationSequence()
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))
							.addKeyframe(new BusAnimationKeyframe(60, 0, 0, 500))
							.addKeyframe(new BusAnimationKeyframe(60, 0, 0, 500))
							.addKeyframe(new BusAnimationKeyframe(0, -90, -90, 0))
							.addKeyframe(new BusAnimationKeyframe(0, -90, -90, 600))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 100))
							.addKeyframe(new BusAnimationKeyframe(-45, 0, 0, 50))
							.addKeyframe(new BusAnimationKeyframe(-45, 0, 0, 100))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))
						)
				 .addBus("RELOAD_MOVE", new BusAnimationSequence()
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))
							.addKeyframe(new BusAnimationKeyframe(0, -15, 0, 1000))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 450))
						)
				 .addBus("DRUM_PUSH", new BusAnimationSequence()
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 1600))
							.addKeyframe(new BusAnimationKeyframe(0, 0, -5, 0))
							.addKeyframe(new BusAnimationKeyframe(0, 0, 0, 300))
						);
		
		 return null;
	}
}
