package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.lib.ModDamageSource;
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
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBuilding extends EntityThrowable {

	public EntityBuilding(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	@Override
	public void onUpdate() {

		if(!worldObj.isRemote && this.ticksExisted == 1) {
			for(int i = 0; i < 100; i++) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "bf");
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data,
						posX + (rand.nextDouble() - 0.5) * 15,
						posY + (rand.nextDouble() - 0.5) * 15,
						posZ + (rand.nextDouble() - 0.5) * 15),
						new TargetPoint(dimension, posX, posY, posZ, 150));
			}
		}

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);

		this.motionY -= 0.03;
		if(motionY < -1.5) motionY = -1.5;

		if(this.worldObj.getBlock((int) this.posX, (int) this.posY, (int) this.posZ) != Blocks.air) {
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:entity.oldExplosion", 10000.0F, 0.5F + this.rand.nextFloat() * 0.1F);
			this.setDead();
			ExplosionLarge.spawnParticles(worldObj, posX, posY + 3, posZ, 150);
			ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 6);
			ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 5);
			ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 4);
			ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 3);
			ExplosionLarge.spawnShock(worldObj, posX, posY + 1, posZ, 24, 3);

			List<Entity> list = (List<Entity>) worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(posX - 8, posY - 8, posZ - 8, posX + 8, posY + 8, posZ + 8));

			for(Entity e : list) e.attackEntityFrom(ModDamageSource.building, 1000);

			for(int i = 0; i < 250; i++) {

				Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
				vec.rotateAroundZ((float) (-rand.nextFloat() * Math.PI / 2));
				vec.rotateAroundY((float) (rand.nextFloat() * Math.PI * 2));

				EntityRubble rubble = new EntityRubble(worldObj, posX, posY + 3, posZ);
				rubble.setMetaBasedOnBlock(Blocks.brick_block, 0);
				rubble.motionX = vec.xCoord;
				rubble.motionY = vec.yCoord;
				rubble.motionZ = vec.zCoord;
				worldObj.spawnEntityInWorld(rubble);
			}
		}
	}

	@Override protected void onImpact(MovingObjectPosition p_70184_1_) { }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}
}
