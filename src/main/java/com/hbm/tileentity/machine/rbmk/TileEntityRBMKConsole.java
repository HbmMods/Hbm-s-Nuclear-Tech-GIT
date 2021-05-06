package com.hbm.tileentity.machine.rbmk;

import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityRBMKConsole extends TileEntityMachineBase {
	
	private int targetX;
	private int targetY;
	private int targetZ;
	
	public RBMKColumn[][] columns = new RBMKColumn[15][15];

	public TileEntityRBMKConsole() {
		super(0);
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(this.worldObj.getTotalWorldTime() % 10 == 0) {
				rescan();
			}
		}
	}
	
	private void rescan() {
		
		for(int i = -7; i <= 7; i++) {
			for(int j = -7; j <= 7; j++) {
				
				TileEntity te = worldObj.getTileEntity(targetX + i, targetY, targetZ + j);
				
				if(te instanceof TileEntityRBMKBase) {
					
					TileEntityRBMKBase rbmk = (TileEntityRBMKBase)te;
					
					columns[i + 7][j + 7] = new RBMKColumn(rbmk.getConsoleType(), rbmk.getNBTForConsole());
					
				} else {
					columns[i + 7][j + 7] = null;
				}
			}
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 4, zCoord + 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public void setTarget(int x, int y, int z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
		this.markDirty();
	}
	
	public static class RBMKColumn {
		
		public ColumnType type;
		public NBTTagCompound data;
		
		public RBMKColumn(ColumnType type) {
			this.type = type;
		}
		
		public RBMKColumn(ColumnType type, NBTTagCompound data) {
			this.type = type;
			this.data = data;
		}
	}
	
	public static enum ColumnType {
		BLANK,
		FUEL,
		CONTROL,
		CONTROL_AUTO,
		BOILER,
		MODERATOR,
		ABSORBER,
		REFLECTOR
	}
}
