package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineTeleporter extends TileEntityLoadedBase implements IEnergyUser, INBTPacketReceiver {

	public long power = 0;
	public int targetX = -1;
	public int targetY = -1;
	public int targetZ = -1;
	public static final int maxPower = 1_500_000;
	public static final int consumption = 1_000_000;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		targetX = nbt.getInteger("x1");
		targetY = nbt.getInteger("y1");
		targetZ = nbt.getInteger("z1");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setInteger("x1", targetX);
		nbt.setInteger("y1", targetY);
		nbt.setInteger("z1", targetZ);
	}

	@Override
	public void updateEntity() {

		boolean b0 = false;
		
		if (!this.worldObj.isRemote) {
			this.updateStandardConnections(worldObj, xCoord, yCoord, zCoord);
			
			List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(this.xCoord - 0.25, this.yCoord, this.zCoord - 0.25, this.xCoord + 1.5, this.yCoord + 2, this.zCoord + 1.5));
			
			if(!entities.isEmpty()) {
				for(Entity e : entities) {
					teleport(e);
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setIntArray("target", new int[] {targetX, targetY, targetZ});
			INBTPacketReceiver.networkPack(this, data, 15);
			
		} else {
			
			if(power >= consumption) {
				double x = xCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25D;
				double y = yCoord + 1 + worldObj.rand.nextDouble() * 2D;
				double z = zCoord + 0.5 + worldObj.rand.nextGaussian() * 0.25D;
				worldObj.spawnParticle("reddust", x, y, z, 0.4F, 0.8F, 1F);
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		int[] target = nbt.getIntArray("target");
		this.targetX = target[0];
		this.targetX = target[1];
		this.targetX = target[2];
	}

	public void teleport(Entity entity) {
		
		if(this.power < consumption) return;
		
		worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "mob.endermen.portal", 1.0F, 1.0F);
		
		if((entity instanceof EntityPlayerMP)) {
			((EntityPlayerMP) entity).setPositionAndUpdate(this.targetX + 0.5D, this.targetY + 1D + entity.getYOffset(), this.targetZ + 0.5D);
		} else {
			entity.setPositionAndRotation(this.targetX + 0.5D, this.targetY + 1D + entity.getYOffset(), this.targetZ + 0.5D, entity.rotationYaw, entity.rotationPitch);
		}
		
		worldObj.playSoundEffect(entity.posX, entity.posY, entity.posZ, "mob.endermen.portal", 1.0F, 1.0F);
		
		this.power -= consumption;
		this.markDirty();
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
}
