package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineTeleporter extends TileEntity implements IConsumer {

	public long power = 0;
	public int targetX = 0;
	public int targetY = 0;
	public int targetZ = 0;
	public boolean linked = false;
	// true: send; false: receive
	public boolean mode = false;
	public static final int maxPower = 100000;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		targetX = nbt.getInteger("x1");
		targetY = nbt.getInteger("y1");
		targetZ = nbt.getInteger("z1");
		linked = nbt.getBoolean("linked");
		mode = nbt.getBoolean("mode");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setInteger("x1", targetX);
		nbt.setInteger("y1", targetY);
		nbt.setInteger("z1", targetZ);
		nbt.setBoolean("linked", linked);
		nbt.setBoolean("mode", mode);
	}

	@Override
	public void updateEntity() {

		boolean b0 = false;
		
		if (!this.worldObj.isRemote) {
			List<Entity> entities = this.worldObj.getEntitiesWithinAABB(Entity.class,
					AxisAlignedBB.getBoundingBox(this.xCoord - 0.25, this.yCoord, this.zCoord - 0.25, this.xCoord + 1.5,
							this.yCoord + 2, this.zCoord + 1.5));
			if (!entities.isEmpty())
				for (Entity e : entities) {
					if(e.ticksExisted >= 10) {
						teleport(e);
						b0 = true;
					}
				}

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
		
		if(b0)
            worldObj.spawnParticle("cloud", xCoord + 0.5, yCoord + 1, zCoord + 0.5, 0.0D, 0.1D, 0.0D);
	}

	public void teleport(Entity entity) {

		if (!this.linked || !this.mode || this.power < 50000)
			return;

		TileEntity te = this.worldObj.getTileEntity(targetX, targetY, targetZ);

		if (te == null || !(te instanceof TileEntityMachineTeleporter) || ((TileEntityMachineTeleporter) te).mode) {
			entity.attackEntityFrom(ModDamageSource.teleporter, 10000);
		} else {
			if ((entity instanceof EntityPlayerMP)) {
				((EntityPlayerMP) entity).setPositionAndUpdate(this.targetX + 0.5D,
						this.targetY + 1.5D + entity.getYOffset(), this.targetZ + 0.5D);
			} else {
				entity.setPositionAndRotation(this.targetX + 0.5D, this.targetY + 1.5D + entity.getYOffset(),
						this.targetZ + 0.5D, entity.rotationYaw, entity.rotationPitch);
			}
		}
		
		this.power -= 50000;
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
