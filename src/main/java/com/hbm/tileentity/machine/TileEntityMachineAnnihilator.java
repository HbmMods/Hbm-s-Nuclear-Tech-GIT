package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerMachineAnnihilator;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineAnnihilator;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.saveddata.AnnihilatorSavedData;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityMachineAnnihilator extends TileEntityMachineBase implements IFluidStandardReceiverMK2, IGUIProvider {
	
	public String pool = "Recycling";
	public int timer;
	
	public FluidTank tank;

	public TileEntityMachineAnnihilator() {
		super(11);
		
		this.tank = new FluidTank(Fluids.NONE, 256_000);
	}

	@Override
	public String getName() {
		return "container.annihilator";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.tank.setType(1, slots);
			
			if(this.pool != null && !this.pool.isEmpty()) {
				
				if(slots[0] != null) {
					AnnihilatorSavedData.getData(worldObj).pushToPool(pool, slots[0]);
					this.slots[0] = null;
					this.markChanged();
				}
				if(tank.getFill() > 0) {
					AnnihilatorSavedData.getData(worldObj).pushToPool(pool, tank.getTankType(), tank.getFill());
					tank.setFill(0);
					this.markChanged();
				}
			}
			
			this.networkPackNT(25);
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // trash
		if(slot == 1 && stack.getItem() instanceof IItemFluidIdentifier) return true;
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {0};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.pool = nbt.getString("pool");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("pool", pool);
	}

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {tank}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tank}; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineAnnihilator(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineAnnihilator(player.inventory, this); }
}
