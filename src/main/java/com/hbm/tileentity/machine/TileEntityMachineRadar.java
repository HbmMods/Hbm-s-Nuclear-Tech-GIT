package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.interfaces.IConsumer;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TERadarPacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineRadar extends TileEntity implements IConsumer {

	public static List<EntityMissileBaseAdvanced> allMissiles = new ArrayList();
	public List<int[]> nearbyMissiles = new ArrayList();
	
	public static int range = 1000;

	public long power = 0;
	public static final int maxPower = 100000;

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
	}

	@Override
	public void updateEntity() {

		nearbyMissiles.clear();
		
		if(power > 0) {

			allocateMissiles();
			if(!worldObj.isRemote) {
				sendMissileData();
			}
			
			power -= 500;
			if(power < 0)
				power = 0;
		}
		
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
		
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
	}
	
	private void allocateMissiles() {
		
		for(Object e : allMissiles) {
		//for(Object e : worldObj.loadedEntityList) {
			if(e instanceof EntityMissileBaseAdvanced) {
				EntityMissileBaseAdvanced m = (EntityMissileBaseAdvanced)e;
				
				if(!m.isDead && m.posX < xCoord + range && m.posX > xCoord - range &&
						m.posZ < zCoord + range && m.posZ > zCoord - range)
					this.nearbyMissiles.add(new int[] {(int)m.posX, (int)m.posZ, m.getMissileType()});
			}
		}
	}
	
	public int getRedPower() {
		
		if(!nearbyMissiles.isEmpty()) {
			
			double maxRange = range * Math.sqrt(2D);
			
			int power = 0;
			
			for(int i = 0; i < nearbyMissiles.size(); i++) {
				
				int[] j = nearbyMissiles.get(i);
				double dist = Math.sqrt(Math.pow(j[0] - xCoord, 2) + Math.pow(j[1] - zCoord, 2));
				int p = 15 - (int)Math.floor(dist / maxRange * 15);
				
				if(p > power)
					power = p;
			}
			
			return power;
		}
		
		return 0;
	}
	
	private void sendMissileData() {
		
		for(int[] e : this.nearbyMissiles) {
			PacketDispatcher.wrapper.sendToAll(new TERadarPacket(xCoord, yCoord, zCoord, e[0], e[1], e[2]));
		}
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
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
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
