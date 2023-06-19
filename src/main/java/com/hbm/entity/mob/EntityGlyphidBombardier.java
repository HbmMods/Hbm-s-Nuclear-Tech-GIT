package com.hbm.entity.mob;

import com.hbm.entity.projectile.EntityAcidBomb;
import com.hbm.main.ResourceManager;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGlyphidBombardier extends EntityGlyphid {

	public EntityGlyphidBombardier(World world) {
		super(world);
	}
	
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_bombardier_tex;
	}
	
	protected Entity lastTarget;
	protected double lastX;
	protected double lastY;
	protected double lastZ;

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!this.worldObj.isRemote) {

			Entity e = this.getEntityToAttack();
			
			if(this.ticksExisted % 20 == 0 && e != null) {
				this.lastTarget = e;
				this.lastX = e.posX;
				this.lastY = e.posY;
				this.lastZ = e.posZ;
			}
			
			if(this.ticksExisted % 20 == 1 && e != null) {
				
				boolean topAttack = rand.nextBoolean();

				double velX = e.posX - lastX;
				double velY = e.posY - lastY;
				double velZ = e.posZ - lastZ;
				
				if(this.lastTarget != e || Vec3.createVectorHelper(velX, velY, velZ).lengthVector() > 30) {
					velX = velY = velZ = 0;
				}
				
				int prediction = topAttack ? 60 : 20;
				Vec3 delta = Vec3.createVectorHelper(e.posX - posX + velX * prediction, (e.posY + e.height / 2) - (posY + 1) + velY * prediction, e.posZ - posZ + velZ * prediction);
				double len = delta.lengthVector();
				if(len < 3) return;
				double targetYaw = -Math.atan2(delta.xCoord, delta.zCoord);
				
				double x = Math.sqrt(delta.xCoord * delta.xCoord + delta.zCoord * delta.zCoord);
				double y = delta.yCoord;
				double v0 = getV0();
				double v02 = v0 * v0;
				double g = 0.04D;
				double upperLower = topAttack ? 1 : -1;
				double targetPitch = Math.atan((v02 + Math.sqrt(v02*v02 - g*(g*x*x + 2*y*v02)) * upperLower) / (g*x));
				
				if(!Double.isNaN(targetPitch)) {
					
					Vec3 fireVec = Vec3.createVectorHelper(v0, 0, 0);
					fireVec.rotateAroundZ((float) -targetPitch);
					fireVec.rotateAroundY((float) -(targetYaw + Math.PI * 0.5));
					
					for(int i = 0; i < getBombCount(); i++) {
						EntityAcidBomb bomb = new EntityAcidBomb(worldObj, posX, posY + 1, posZ);
						bomb.setThrowableHeading(fireVec.xCoord, fireVec.yCoord, fireVec.zCoord, (float) v0, i * getSpreadMult());
						bomb.damage = getBombDamage();
						worldObj.spawnEntityInWorld(bomb);
					}
					
					this.swingItem();
				}
			}
		}
	}
	
	public float getBombDamage() {
		return 1.5F;
	}
	
	public int getBombCount() {
		return 10;
	}
	
	public float getSpreadMult() {
		return 1F;
	}
	
	public double getV0() {
		return 1D;
	}
}
