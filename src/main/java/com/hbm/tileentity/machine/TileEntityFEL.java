package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityFEL extends TileEntityMachineBase {
	
	public long power;
	public static final long maxPower = 1000000;
	public int watts;
	public int mode = 0;
	
	public TileEntityFEL() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.machineFEL";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setByte("mode", (byte)mode);
			this.networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.mode = nbt.getByte("mode");
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0) {
			this.mode = Math.abs(value) % 6;
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(
				xCoord - 4,
				yCoord,
				zCoord - 4,
				xCoord + 5,
				yCoord + 3,
				zCoord + 5
			);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
