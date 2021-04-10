package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityRadiobox extends TileEntity implements IConsumer {
	
	long power;
	public static long maxPower = 500000;
	public boolean infinite = false;
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote && this.getBlockMetadata() > 5 && (power >= 25000 || infinite)) {
			
			if(!infinite) {
				power -= 25000;
				this.markDirty();
			}
			
			int range = 15;
			
			List<IMob> entities = worldObj.getEntitiesWithinAABB(IMob.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range, yCoord + range, zCoord + range));
			for(IMob entity : entities)
				((Entity)entity).attackEntityFrom(ModDamageSource.enervation, 20.0F);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		infinite = nbt.getBoolean("infinite");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setBoolean("infinite", infinite);
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
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
