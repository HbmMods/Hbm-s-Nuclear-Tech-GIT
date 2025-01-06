package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBoxcar extends EntityThrowable {

	public EntityBoxcar(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	@Override
	public void onUpdate() {

		if(!worldObj.isRemote && this.ticksExisted == 1) {
			for(int i = 0; i < 50; i++) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "bf");
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data,
						posX + (rand.nextDouble() - 0.5) * 3,
						posY + (rand.nextDouble() - 0.5) * 15,
						posZ + (rand.nextDouble() - 0.5) * 3),
						new TargetPoint(dimension, posX, posY, posZ, 150));
			}
		}

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);
		
		this.motionY -= 0.03;
		if(motionY < -1.5)
			motionY = -1.5;

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air) {
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:weapon.trainImpact", 100.0F, 1.0F);
			this.setDead();
			ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 3);
			ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 2.5);
			ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 2);
			//ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 1.5);
			//ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 1);

			List<Entity> list = (List<Entity>) worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(posX - 2, posY - 2, posZ - 2, posX + 2, posY + 2, posZ + 2));

			for(Entity e : list) {
				e.attackEntityFrom(ModDamageSource.boxcar, 1000);
			}

			if(!worldObj.isRemote)
				worldObj.setBlock((int) Math.floor(this.posX), (int) Math.floor(this.posY + 0.5), (int) Math.floor(this.posZ), ModBlocks.boxcar);
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
