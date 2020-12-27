package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.WeaponConfig;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.Untested;
import com.hbm.tileentity.TileEntityTickingBase;

import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectable.RadarTargetType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

@Untested
public class TileEntityMachineRadar extends TileEntityTickingBase implements IConsumer {

	public List<int[]> nearbyMissiles = new ArrayList();
	int pingTimer = 0;
	int lastPower;
	final static int maxTimer = 40;

	public long power = 0;
	public static final int maxPower = 100000;

	@Override
	public String getInventoryName() {
		return "";
	}

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
		
		if(this.yCoord < WeaponConfig.radarAltitude)
			return;
		
		int lastPower = getRedPower();
		
		if(!worldObj.isRemote) {
			nearbyMissiles.clear();
			
			if(power > 0) {
				
				allocateMissiles();
				
				power -= 500;
				
				if(power < 0)
					power = 0;
			}
			
			if(lastPower != getRedPower())
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
			
			sendMissileData();
			
			if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) != ModBlocks.muffler) {
				
				pingTimer++;
				
				if(power > 0 && pingTimer >= maxTimer) {
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.sonarPing", 5.0F, 1.0F);
					pingTimer = 0;
				}
			}
		}
	}
	
	private void allocateMissiles() {
		
		nearbyMissiles.clear();
		
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord + 0.5 - WeaponConfig.radarRange, 0, zCoord + 0.5 - WeaponConfig.radarRange, xCoord + 0.5 + WeaponConfig.radarRange, 5000, zCoord + 0.5 + WeaponConfig.radarRange));

		for(Entity e : list) {

			if(e instanceof EntityPlayer && e.posY >= yCoord + WeaponConfig.radarBuffer) {
				nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, RadarTargetType.PLAYER.ordinal(), (int)e.posY });
			}
			
			if(e instanceof IRadarDetectable && e.posY >= yCoord + WeaponConfig.radarBuffer) {
				nearbyMissiles.add(new int[] { (int)e.posX, (int)e.posZ, ((IRadarDetectable)e).getTargetType().ordinal(), (int)e.posY });
			}
		}
	}
	
	public int getRedPower() {
		
		if(!nearbyMissiles.isEmpty()) {
			
			double maxRange = WeaponConfig.radarRange * Math.sqrt(2D);
			
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
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setInteger("count", this.nearbyMissiles.size());
		
		for(int i = 0; i < this.nearbyMissiles.size(); i++) {
			data.setInteger("x" + i, this.nearbyMissiles.get(i)[0]);
			data.setInteger("z" + i, this.nearbyMissiles.get(i)[1]);
			data.setInteger("type" + i, this.nearbyMissiles.get(i)[2]);
			data.setInteger("y" + i, this.nearbyMissiles.get(i)[3]);
		}
		
		this.networkPack(data, 15);
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		this.nearbyMissiles.clear();
		this.power = data.getLong("power");
		
		int count = data.getInteger("count");
		
		for(int i = 0; i < count; i++) {

			int x = data.getInteger("x" + i);
			int z = data.getInteger("z" + i);
			int type = data.getInteger("type" + i);
			int y = data.getInteger("y" + i);
			
			this.nearbyMissiles.add(new int[] {x, z, type, y});
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
