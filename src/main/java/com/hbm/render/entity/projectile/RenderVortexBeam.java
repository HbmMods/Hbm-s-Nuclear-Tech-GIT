package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBeamBase;
import com.hbm.lib.Library;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderVortexBeam extends Render {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		this.doRender((EntityBeamBase)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityBeamBase laser, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		
		GL11.glPushMatrix();
		
		EntityPlayer player = laser.worldObj.getPlayerEntityByName(laser.getDataWatcher().getWatchableObjectString(20));
		
		if(player != null) {
			
			GL11.glTranslated(x, y, z);
			
			MovingObjectPosition pos = Library.rayTrace(player, 100, 1);
			
			Vec3 skeleton = Vec3.createVectorHelper(pos.hitVec.xCoord - player.posX, pos.hitVec.yCoord - player.posY - player.getEyeHeight(), pos.hitVec.zCoord - player.posZ);
			int init = (int) -(System.currentTimeMillis() % 360);

	        BeamPronter.prontBeam(skeleton, EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x000040, 0x2020d0, init, 1, 0F, 4, 0.005F, 256);
	        BeamPronter.prontBeam(skeleton, EnumWaveType.RANDOM, EnumBeamType.LINE, 0x8080ff, 0x8080ff, init, (int)skeleton.lengthVector() * 3 + 1, 0.01F, 1, 0.01F, 256);
		}
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
