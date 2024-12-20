package com.hbm.entity.projectile;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.particle.helper.ExplosionCreator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityTorpedo extends EntityThrowable {

	public EntityTorpedo(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	@Override
	public void onUpdate() {

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);
		
		this.motionY -= 0.03;
		if(motionY < -1.5) motionY = -1.5;

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air) {
			this.setDead();
			ExplosionCreator.composeEffectStandard(worldObj, posX, posY + 1, posZ);
			ExplosionVNT vnt = new ExplosionVNT(worldObj, posX, posY, posZ, 20F);
			vnt.makeStandard();
			vnt.explode();
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}
}
