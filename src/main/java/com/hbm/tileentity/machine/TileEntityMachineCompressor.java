package com.hbm.tileentity.machine;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TilePort.PortDef;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCompressor extends TileEntityMachineCompressorBase {
	
	public float fanSpin;
	public float prevFanSpin;
	public float piston;
	public float prevPiston;
	public boolean pistonDir;
	private float randSpeed = 0.1F;

	@Override
	public void updateEntity() {
		super.updateEntity();

		if(worldObj.isRemote) {

			this.prevFanSpin = this.fanSpin;
			this.prevPiston = this.piston;

			if(this.isOn) {
				this.fanSpin += 15;

				if(this.fanSpin >= 360) {
					this.prevFanSpin -= 360;
					this.fanSpin -= 360;
				}

				if(this.pistonDir) {
					this.piston -= randSpeed;
					if(this.piston <= 0) {
						MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "hbm:item.boltgun", this.getVolume(0.5F), 0.75F);
						this.pistonDir = !this.pistonDir;
					}
				} else {
					this.piston += 0.05F;
					if(this.piston >= 1) {
						this.randSpeed = 0.085F + worldObj.rand.nextFloat() * 0.03F;
						this.pistonDir = !this.pistonDir;
					}
				}

				this.piston = MathHelper.clamp_float(this.piston, 0F, 1F);
			}
		}
	}
	
	protected PortDef[] cachedPorts;

	public PortDef[] getPorts() {
		if(cachedPorts == null) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
			ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
			
			cachedPorts = new PortDef[] {
					PortDef.make(xCoord + rot.offsetX, yCoord, zCoord + rot.offsetZ, rot),
					PortDef.make(xCoord - rot.offsetX, yCoord, zCoord - rot.offsetZ, rot.getOpposite()),
					PortDef.make(xCoord - dir.offsetX, yCoord, zCoord - dir.offsetZ, dir.getOpposite()),
			};
		}
		return cachedPorts;
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
					yCoord + 9,
					zCoord + 3
					);
		}

		return bb;
	}
}
