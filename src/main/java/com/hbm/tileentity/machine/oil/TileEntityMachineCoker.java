package com.hbm.tileentity.machine.oil;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerMachineCoker;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCoker;
import com.hbm.inventory.recipes.CokerRecipes;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityMachineCoker extends TileEntityMachineBase implements IFluidStandardTransceiver, IGUIProvider {
	
	public int progress;
	public static int processTime = 20_000;
	
	public int heat;
	public static int maxHeat = 100_000;
	public static double diffusion = 0.25D;
	
	public FluidTank[] tanks;

	public TileEntityMachineCoker() {
		super(2);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.HEAVYOIL, 16_000);
		tanks[1] = new FluidTank(Fluids.OIL_COKER, 8_000);
	}

	@Override
	public String getName() {
		return "container.machineCoker";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.tryPullHeat();
			this.tanks[0].setType(0, slots);
			
			if(canProcess()) {
				int burn = heat / 100;
						
				if(burn > 0) {
					this.progress += burn;
					this.heat -= burn;
					
					if(progress >= processTime) {
						this.markChanged();
						progress -= this.processTime;
						
						Triplet<Integer, ItemStack, FluidStack> recipe = CokerRecipes.getOutput(tanks[0].getTankType());
						int fillReq = recipe.getX();
						ItemStack output = recipe.getY();
						FluidStack byproduct = recipe.getZ();
						
						if(output != null) {
							if(slots[1] == null) {
								slots[1] = output.copy();
							} else {
								slots[1].stackSize += output.stackSize;
							}
						}
						
						if(byproduct != null) {
							tanks[1].setFill(tanks[1].getFill() + byproduct.fill);
						}
						
						tanks[0].setFill(tanks[0].getFill() - fillReq);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			tanks[0].writeToNBT(data, "t0");
			tanks[1].writeToNBT(data, "t1");
			this.networkPack(data, 25);
		}
	}
	
	public boolean canProcess() {
		Triplet<Integer, ItemStack, FluidStack> recipe = CokerRecipes.getOutput(tanks[0].getTankType());
		
		if(recipe == null) return false;
		
		int fillReq = recipe.getX();
		ItemStack output = recipe.getY();
		FluidStack byproduct = recipe.getZ();
		
		if(byproduct != null) tanks[1].setTankType(byproduct.type);
		
		if(tanks[0].getFill() < recipe.getX()) return false;
		if(byproduct != null && byproduct.fill + tanks[1].getFill() > tanks[1].getMaxFill()) return false;
		
		if(output != null && slots[1] != null) {
			if(output.getItem() != slots[1].getItem()) return false;
			if(output.getItemDamage() != slots[1].getItemDamage()) return false;
			if(output.stackSize + slots[1].stackSize > output.getMaxStackSize()) return false;
		}
		
		return true;
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");
	}
	
	protected void tryPullHeat() {
		
		if(this.heat >= this.maxHeat) return;
		
		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > this.maxHeat)
					this.heat = this.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
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
