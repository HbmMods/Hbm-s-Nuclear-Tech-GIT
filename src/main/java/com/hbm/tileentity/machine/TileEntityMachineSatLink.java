package com.hbm.tileentity.machine;

import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.tileentity.TileEntityTickingBase;

import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineSatLink extends TileEntityTickingBase implements IRORValueProvider, IRORInteractive {
	
	public boolean connected;
	public int freq;
	
	public float rot = INACTIVE_ROT;
	public float prevRot = INACTIVE_ROT;
	public float lift = INACTIVE_LIFT;
	public float prevLift = INACTIVE_LIFT;
	
	public static final float SPEED = 0.25F;
	public static final float ACTIVE_ROT = -15F;
	public static final float ACTIVE_LIFT = -45F;
	public static final float INACTIVE_ROT = 0F;
	public static final float INACTIVE_LIFT = -85F;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.connected = false;
			
			if(worldObj.getHeightValue(xCoord, zCoord) <= yCoord) {
				
				SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
				this.connected = dat.isFreqTaken(freq);
			}
			
			this.networkPackNT(150);
			
		} else {

			this.prevRot = this.rot;
			this.prevLift = this.lift;

			float targetR = this.connected ? ACTIVE_ROT : INACTIVE_ROT;
			float targetL = this.connected ? ACTIVE_LIFT : INACTIVE_LIFT;
			
			if(Math.abs(rot - targetR) <= SPEED) rot = targetR;
			else if(rot < targetR) rot += SPEED;
			else if(rot > targetR) rot -= SPEED;
			
			if(Math.abs(lift - targetL) <= SPEED) lift = targetL;
			else if(lift < targetL) lift += SPEED;
			else if(lift > targetL) lift -= SPEED;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(connected);
		buf.writeInt(freq);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.connected = buf.readBoolean();
		this.freq = buf.readInt();
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
					yCoord + 10,
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

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_VALUE + "connected",
				PREFIX_VALUE + "freq",
				PREFIX_VALUE + "rx",
				PREFIX_FUNCTION + "setfreq" + NAME_SEPARATOR + "freq",
				PREFIX_FUNCTION + "tx" + NAME_SEPARATOR + "payload"
		};
	}

	@Override
	public String provideRORValue(String name) {
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		return null;
	}
}
