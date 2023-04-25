package com.hbm.tileentity.machine.oil;

import com.hbm.inventory.container.ContainerMachineCoker;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCoker;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityMachineCoker extends TileEntityMachineBase implements IFluidStandardTransceiver, IGUIProvider {
	
	public FluidTank[] tanks;

	public TileEntityMachineCoker() {
		super(1);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.HEAVYOIL, 16_000);
		tanks[1] = new FluidTank(Fluids.GAS, 8_000);
	}

	@Override
	public String getName() {
		return "container.machineCoker";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[1] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0] };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineCoker(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCoker(player.inventory, this);
	}
}
