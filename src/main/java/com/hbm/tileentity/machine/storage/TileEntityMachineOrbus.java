package com.hbm.tileentity.machine.storage;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.lib.Library;
import com.hbm.tileentity.TilePort.PortDef;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineOrbus extends TileEntityBarrel {

	public TileEntityMachineOrbus() {
		super(512000);
	}
	
	@Override
	public String getName() {
		return "container.orbus";
	}

	@Override public long getReceiverSpeed(FluidType type, int pressure) { return (mode == 0 || mode == 1) ? Math.max(1_000, (tank.getMaxFill() - tank.getFill()) / 100) : 0; }
	@Override public long getProviderSpeed(FluidType type, int pressure) { return (mode == 1 || mode == 2) ? Math.max(1_000, tank.getFill() / 100) : 0; }
	
	@Override
	public void checkFluidInteraction() { } //NO!

	@Override
	public PortDef[] getPorts() {
		if(cachedPorts == null) {
			// cursed
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
			ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
			
			cachedPorts = new PortDef[] {
					PortDef.make(xCoord, yCoord, zCoord, Library.NEG_Y),
					PortDef.make(xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, Library.NEG_Y),
					PortDef.make(xCoord + rot.offsetX, yCoord, zCoord + rot.offsetZ, Library.NEG_Y),
					PortDef.make(xCoord + dir.offsetX + rot.offsetX, yCoord, zCoord + dir.offsetZ + rot.offsetZ, Library.NEG_Y),
					PortDef.make(xCoord, yCoord + 3, zCoord, Library.POS_Y),
					PortDef.make(xCoord + dir.offsetX, yCoord + 3, zCoord + dir.offsetZ, Library.POS_Y),
					PortDef.make(xCoord + rot.offsetX, yCoord + 3, zCoord + rot.offsetZ, Library.POS_Y),
					PortDef.make(xCoord + dir.offsetX + rot.offsetX, yCoord + 3, zCoord + dir.offsetZ + rot.offsetZ, Library.POS_Y),
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
