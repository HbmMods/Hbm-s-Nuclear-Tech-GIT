package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityTickingBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.EnumSkyBlock;

public class TileEntitySolarMirror extends TileEntityTickingBase {

	public int tX;
	public int tY;
	public int tZ;
	public boolean isOn;

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0)
				sendUpdate();
			
			if(tY < yCoord) {
				isOn = false;
				return;
			}
			
			int sun = worldObj.getSavedLightValue(EnumSkyBlock.Sky, xCoord, yCoord, zCoord) - worldObj.skylightSubtracted - 11;
			
			if(sun <= 0 || !worldObj.canBlockSeeTheSky(xCoord, yCoord + 1, zCoord)) {
				isOn = false;
				return;
			}
			
			isOn = true;
			
			TileEntity te = worldObj.getTileEntity(tX, tY - 1, tZ);
			
			if(te instanceof TileEntitySolarBoiler) {
				TileEntitySolarBoiler boiler = (TileEntitySolarBoiler)te;
				boiler.heat += sun;
			}
		} else {
			
			TileEntity te = worldObj.getTileEntity(tX, tY - 1, tZ);
			
			if(isOn && te instanceof TileEntitySolarBoiler) {
				TileEntitySolarBoiler boiler = (TileEntitySolarBoiler)te;
				boiler.primary.add(new ChunkCoordinates(xCoord, yCoord, zCoord));
			}
			
			if(worldObj.getTotalWorldTime() % 20 == 0)
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public void sendUpdate() {

		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("posX", tX);
		data.setInteger("posY", tY);
		data.setInteger("posZ", tZ);
		data.setBoolean("isOn", isOn);
		this.networkPack(data, 200);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		tX = nbt.getInteger("posX");
		tY = nbt.getInteger("posY");
		tZ = nbt.getInteger("posZ");
		isOn = nbt.getBoolean("isOn");
	}
	
	public void setTarget(int x, int y, int z) {
		tX = x;
		tY = y;
		tZ = z;
		this.markDirty();
		this.sendUpdate();
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tX = nbt.getInteger("targetX");
		tY = nbt.getInteger("targetY");
		tZ = nbt.getInteger("targetZ");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("targetX", tX);
		nbt.setInteger("targetY", tY);
		nbt.setInteger("targetZ", tZ);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 25,
					yCoord - 25,
					zCoord - 25,
					xCoord + 25,
					yCoord + 25,
					zCoord + 25
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
