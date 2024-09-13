package com.hbm.tileentity.network;

import java.util.List;

import com.hbm.entity.item.EntityDeliveryDrone;
import com.hbm.util.ParticleUtil;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.BufPacket;
import com.hbm.tileentity.IBufPacketReceiver;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDroneWaypoint extends TileEntity implements IBufPacketReceiver, IDroneLinkable {
	
	public int height = 5;
	public int nextX = -1;
	public int nextY = -1;
	public int nextZ = -1;

	@Override
	public void updateEntity() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		
		if(!worldObj.isRemote) {
			if(nextY != -1) {
				List<EntityDeliveryDrone> drones = worldObj.getEntitiesWithinAABB(EntityDeliveryDrone.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1).offset(dir.offsetX * height, dir.offsetY * height, dir.offsetZ * height));
				for(EntityDeliveryDrone drone : drones) {
					if(Vec3.createVectorHelper(drone.motionX, drone.motionY, drone.motionZ).lengthVector() < 0.05) {
						drone.setTarget(nextX + 0.5, nextY, nextZ + 0.5);
					}
				}
			}

			PacketDispatcher.wrapper.sendToAllAround(new BufPacket(xCoord, yCoord, zCoord, this), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 15));
		} else {
			BlockPos pos = getCoord(dir);
			if(nextY != -1 && worldObj.getTotalWorldTime() % 2 == 0) {
				double x = xCoord + height * dir.offsetX + 0.5;
				double y = yCoord + height * dir.offsetY + 0.5;
				double z = zCoord + height * dir.offsetZ + 0.5;
				
				worldObj.spawnParticle("reddust", x, y, z, 0, 0, 0);

				ParticleUtil.spawnDroneLine(worldObj,
				pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
				(nextX  - pos.getX()), (nextY - pos.getY()), (nextZ - pos.getZ()), 0x0000ff);
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeInt(height);
		buf.writeInt(nextX);
		buf.writeInt(nextY);
		buf.writeInt(nextZ);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		height = buf.readInt();
		nextX = buf.readInt();
		nextY = buf.readInt();
		nextZ = buf.readInt();
	}

	@Override
	public BlockPos getPoint() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
		return new BlockPos(xCoord, yCoord, zCoord).offset(dir, height);
	}

	@Override
	public void setNextTarget(int x, int y, int z) {
		this.nextX = x;
		this.nextY = y;
		this.nextZ = z;
		this.markDirty();
	}
	
	public void addHeight(int h) {
		height += h;
		height = MathHelper.clamp_int(height, 1, 15);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.height = nbt.getInteger("height");
		int[] pos = nbt.getIntArray("pos");
		this.nextX = pos[0];
		this.nextY = pos[1];
		this.nextZ = pos[2];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("height", height);
		nbt.setIntArray("pos", new int[] {nextX, nextY, nextZ});
	}
	public BlockPos getCoord(ForgeDirection dir) {
		return new BlockPos(xCoord  + height * dir.offsetX + 0.5, yCoord + height * dir.offsetY + 0.5, zCoord + height * dir.offsetZ + 0.5);
	}
}
