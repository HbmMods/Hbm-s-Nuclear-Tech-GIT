package com.hbm.tileentity.machine.fusion;

import com.hbm.inventory.recipes.FusionRecipes;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class TileEntityFusionKlystronCreative extends TileEntityLoadedBase {

	protected GenNode klystronNode;

	public float fan;
	public float prevFan;
	public float fanSpeed;
	public static final float FAN_ACCELERATION = 0.125F;
	
	public boolean isConnected = false;

	private AudioWrapper audio;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			this.klystronNode = TileEntityFusionKlystron.handleKNode(klystronNode, this);
			this.isConnected = TileEntityFusionKlystron.provideKyU(klystronNode, FusionRecipes.INSTANCE.maxInput);

			this.networkPackNT(100);

		} else {

			if(this.isConnected) this.fanSpeed += FAN_ACCELERATION;
			else this.fanSpeed -= FAN_ACCELERATION;

			this.fanSpeed = MathHelper.clamp_float(this.fanSpeed, 0F, 5F);

			this.prevFan = this.fan;
			this.fan += this.fanSpeed;

			if(this.fan >= 360F) {
				this.fan -= 360F;
				this.prevFan -= 360F;
			}

			if(this.fanSpeed > 0 && MainRegistry.proxy.me().getDistanceSq(xCoord + 0.5, yCoord + 2.5, zCoord + 0.5) < 30 * 30) {

				float speed = this.fanSpeed / 5F;

				if(audio == null) {
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.fel", xCoord + 0.5F, yCoord + 2.5F, zCoord + 0.5F, getVolume(speed), 15F, speed, 20);
					audio.startSound();
				} else {
					audio.updateVolume(getVolume(speed));
					audio.updatePitch(speed);
					audio.keepAlive();
				}

			} else {

				if(audio != null) {
					if(audio.isPlaying()) audio.stopSound();
					audio = null;
				}
			}
		}
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}

		if(!worldObj.isRemote) {
			if(this.klystronNode != null) UniNodespace.destroyNode(worldObj, klystronNode);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(isConnected);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.isConnected = buf.readBoolean();
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord,
					zCoord - 4,
					xCoord + 5,
					yCoord + 5,
					zCoord + 5
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
