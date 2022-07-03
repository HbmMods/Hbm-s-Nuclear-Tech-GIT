package com.hbm.items.weapon;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.GunConfiguration;

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
		
		boolean smoking = lastShot + 3000 > System.currentTimeMillis();
		
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
			
			double alpha = (System.currentTimeMillis() - ItemGunBio.lastShot) / 3000D;
			alpha = (1 - alpha) * 0.25D;
			
			smokeNodes.add(new double[] {0, 0, 0, alpha});
		}
	}
}
