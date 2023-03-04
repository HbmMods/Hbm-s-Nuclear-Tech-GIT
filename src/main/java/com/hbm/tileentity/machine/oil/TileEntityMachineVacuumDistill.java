package com.hbm.tileentity.machine.oil;

import com.hbm.inventory.container.ContainerMachineVacuumDistill;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineVacuumDistill;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineVacuumDistill extends TileEntityMachineBase implements IEnergyUser, IFluidStandardTransceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 1_000_000;
	
	public FluidTank[] tanks;

	public TileEntityMachineVacuumDistill() {
		super(11);
		
		this.tanks = new FluidTank[5];
		this.tanks[0] = new FluidTank(Fluids.OIL, 64_000);
		this.tanks[1] = new FluidTank(Fluids.HEAVYOIL, 24_000);
		this.tanks[2] = new FluidTank(Fluids.NAPHTHA, 24_000);
		this.tanks[3] = new FluidTank(Fluids.LIGHTOIL, 24_000);
		this.tanks[4] = new FluidTank(Fluids.PETROLEUM, 24_000);
	}

	@Override
	public String getName() {
		return "container.vacuumDistill";
	}

	@Override
	public void updateEntity() {
		
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 9,
					zCoord + 2
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
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return null;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return null;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return null;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineVacuumDistill(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineVacuumDistill(player.inventory, this);
	}
}
