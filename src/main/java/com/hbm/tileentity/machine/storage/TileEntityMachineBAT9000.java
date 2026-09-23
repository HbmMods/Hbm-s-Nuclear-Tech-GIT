package com.hbm.tileentity.machine.storage;

import com.hbm.lib.Library;
import com.hbm.tileentity.TilePort.PortDef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;

@Deprecated
public class TileEntityMachineBAT9000 extends TileEntityBarrel {

	public TileEntityMachineBAT9000() {
		super(2048000);
	}
	
	@Override
	public String getName() {
		return "container.bat9000";
	}
	
	@Override
	public void checkFluidInteraction() {
		
		if(tank.getTankType().isAntimatter()) {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 10, true, true);
		}
	}
	
	@Override
	public PortDef[] getPorts() {
		if(cachedPorts == null) {
			cachedPorts = new PortDef[] {
					PortDef.make(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
					PortDef.make(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
					PortDef.make(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
					PortDef.make(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z),
					PortDef.make(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
					PortDef.make(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
					PortDef.make(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
					PortDef.make(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X)
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
					yCoord + 5,
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
