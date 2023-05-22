package com.hbm.particle.psys.engine;

import java.util.List;

import com.hbm.lib.Library;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public abstract class PSysFXMoving extends PSysFX {

	public double motionX;
	public double motionY;
	public double motionZ;
	boolean noClip = false;
	/* using the forgedirection's ordinal as an index, this tells us what side of a block the particle has collided with */
	public boolean collisionData[] = new boolean[6];
	
	public PSysFXMoving(World world, double x, double y, double z, double mX, double mY, double mZ) {
		super(world, x, y, z);
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
	}
	
	public double getParticleGravity() {
		return 0.04D;
	}
	
	public double getParticleDrag() {
		return 0.98D;
	}
	
	public void updateParticle() {
		super.updateParticle();

		if(!this.isUnloaded) {
			this.motionX -= this.getParticleGravity();
			this.motionX *= this.getParticleDrag();
			this.motionY *= this.getParticleDrag();
			this.motionZ *= this.getParticleDrag();
			
			this.move(motionX, motionY, motionZ);
		}
	}

	public void move(double x, double y, double z) {

		double x0 = x;
		double y0 = y;
		double z0 = z;
		
		this.collisionData = new boolean[6];

		if(!noClip) {
			List<AxisAlignedBB> list = this.world.getCollidingBoundingBoxes(null, this.getBoundingBox().expand(x, y, z));

			for(AxisAlignedBB aabb : list) y = aabb.calculateYOffset(this.getBoundingBox(), y);
			this.setBoundingBox(this.getBoundingBox().offset(0.0D, y, 0.0D));

			for(AxisAlignedBB aabb : list) x = aabb.calculateXOffset(this.getBoundingBox(), x);
			this.setBoundingBox(this.getBoundingBox().offset(x, 0.0D, 0.0D));

			for(AxisAlignedBB aabb : list) z = aabb.calculateZOffset(this.getBoundingBox(), z);
			this.setBoundingBox(this.getBoundingBox().offset(0.0D, 0.0D, z));
			
		} else {
			this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
		}

		this.setPosToAABB();

		if(x0 != x && x > 0) this.collisionData[Library.NEG_X.ordinal()] = true;
		if(x0 != x && x < 0) this.collisionData[Library.POS_X.ordinal()] = true;
		if(y0 != y && y > 0) this.collisionData[Library.NEG_Y.ordinal()] = true;
		if(y0 != y && y < 0) this.collisionData[Library.POS_Y.ordinal()] = true;
		if(z0 != z && z > 0) this.collisionData[Library.NEG_Z.ordinal()] = true;
		if(z0 != z && z < 0) this.collisionData[Library.POS_Z.ordinal()] = true;

		if(x0 != x) this.motionX = 0.0D;
		if(y0 != y) this.motionY = 0.0D;
		if(z0 != z) this.motionZ = 0.0D;
	}
}
