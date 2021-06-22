package com.hbm.entity.effect;

import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySpear extends Entity {

	public EntitySpear(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(2F, 10F);
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() { }

	@Override
	public void onUpdate() {
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.motionX = 0;
		this.motionY = -0.2;
		this.motionZ = 0;

		int x = (int) Math.floor(posX);
		int y = (int) Math.floor(posY);
		int z = (int) Math.floor(posZ);
		
		if(worldObj.getBlock(x, y - 1, z).getMaterial() == Material.air) {
			this.setPositionAndRotation(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ, 0, 0);

			if(!worldObj.isRemote) {
				double ix = posX + rand.nextGaussian() * 25;
				double iz = posZ + rand.nextGaussian() * 25;
				double iy = worldObj.getHeightValue((int)Math.floor(ix), (int)Math.floor(iz)) + 2;
				
				ExAttrib at = Vec3.createVectorHelper(ix - posX, 0, iz - posZ).lengthVector() < 20 ? ExAttrib.DIGAMMA_CIRCUIT : ExAttrib.DIGAMMA;
				
				new ExplosionNT(worldObj, this, ix, iy, iz, 7.5F)
				.addAttrib(ExAttrib.NOHURT)
				.addAttrib(ExAttrib.NOPARTICLE)
				.addAttrib(ExAttrib.NODROP)
				.addAttrib(ExAttrib.NOSOUND)
				.addAttrib(at).explode();
			}
			
			if(worldObj.isRemote) {
				
				double dy = worldObj.getHeightValue((int)Math.floor(posX), (int)Math.floor(posZ)) + 2;
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "smoke");
				data.setString("mode", "radialDigamma");
				data.setInteger("count", 3);
				data.setDouble("posX", posX);
				data.setDouble("posY", dy);
				data.setDouble("posZ", posZ);
				MainRegistry.proxy.effectNT(data);
			}
			
		} else if(!worldObj.isRemote) {
			this.setDead();
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}
}
