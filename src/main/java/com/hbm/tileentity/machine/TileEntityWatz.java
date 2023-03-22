package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerWatz;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIWatz;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityWatz extends TileEntityMachineBase implements IGUIProvider {
	
	public FluidTank[] tanks;
	public int heat;
	public int flux;
	
	/* lock types for item IO */
	public boolean isLocked = false;
	public ItemStack[] locks = new ItemStack[24];
	
	public TileEntityWatz() {
		super(24);
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.COOLANT, 64_000);
		this.tanks[1] = new FluidTank(Fluids.COOLANT_HOT, 64_000);
		this.tanks[2] = new FluidTank(Fluids.WATZ, 64_000);
	}

	@Override
	public String getName() {
		return "container.watz";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
		}
		
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 3,
					zCoord + 4
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
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerWatz(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIWatz(player.inventory, this);
	}
}
