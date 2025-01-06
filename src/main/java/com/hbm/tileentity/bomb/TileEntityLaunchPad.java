package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLaunchPad extends TileEntityLaunchPadBase {

	@Override public boolean isReadyForLaunch() { return delay <= 0; }
	@Override public double getLaunchOffset() { return 1D; }

	public int delay = 0;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(this.delay > 0) delay--;

			if(!this.isMissileValid() || !this.hasFuel()) {
				this.delay = 100;
			}

			if(!this.hasFuel() || !this.isMissileValid()) {
				this.state = this.STATE_MISSING;
			} else {
				if(this.delay > 0) {
					this.state = this.STATE_LOADING;
				} else {
					this.state = this.STATE_READY;
				}
			}

		} else {

			List<EntityMissileBaseNT> entities = worldObj.getEntitiesWithinAABB(EntityMissileBaseNT.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord, zCoord - 0.5, xCoord + 1.5, yCoord + 10, zCoord + 1.5));

			if(!entities.isEmpty()) {
				for(int i = 0; i < 15; i++) {

					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
					if(worldObj.rand.nextBoolean()) dir = dir.getOpposite();
					if(worldObj.rand.nextBoolean()) dir = dir.getRotation(ForgeDirection.UP);
					float moX = (float) (worldObj.rand.nextGaussian() * 0.15F + 0.75) * dir.offsetX;
					float moZ = (float) (worldObj.rand.nextGaussian() * 0.15F + 0.75) * dir.offsetZ;

					NBTTagCompound data = new NBTTagCompound();
					data.setDouble("posX", xCoord + 0.5);
					data.setDouble("posY", yCoord + 0.25);
					data.setDouble("posZ", zCoord + 0.5);
					data.setString("type", "launchSmoke");
					data.setDouble("moX", moX);
					data.setDouble("moY", 0);
					data.setDouble("moZ", moZ);
					MainRegistry.proxy.effectNT(data);
				}
			}
		}

		super.updateEntity();
	}

	@Override
	public void finalizeLaunch(Entity missile) {
		super.finalizeLaunch(missile);
		this.delay = 100;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.delay = nbt.getInteger("delay");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("delay", delay);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 15,
					zCoord + 3
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
