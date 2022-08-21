package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.container.ContainerOilburner;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.gui.GUIOilburner;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityHeaterOilburner extends TileEntityMachineBase implements IGUIProvider, IFluidStandardReceiver, IHeatSource, IControlReceiver, IFluidAcceptor {
	
	public boolean isOn = false;
	public FluidTank tank;

	public int heatEnergy;
	public static final int maxHeatEnergy = 100_000;

	public TileEntityHeaterOilburner() {
		super(3);
		tank = new FluidTank(Fluids.HEATINGOIL, 16000, 0);
	}

	@Override
	public String getName() {
		return "container.heaterOilburner";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			tank.loadTank(0, 1, slots);
			tank.setType(2, slots);
			
			boolean shouldCool = true;
			
			if(this.isOn && this.heatEnergy < maxHeatEnergy) {
				
				if(tank.getTankType().hasTrait(FT_Flammable.class)) {
					FT_Flammable type = tank.getTankType().getTrait(FT_Flammable.class);
					
					int burnRate = 10;
					int toBurn = Math.min(burnRate, tank.getFill());
					
					tank.setFill(tank.getFill() - toBurn);
					
					int heat = (int)(type.getHeatEnergy() / 1000);
					
					this.heatEnergy += heat * toBurn;
					
					shouldCool = false;
				}
			}
			
			if(this.heatEnergy >= maxHeatEnergy)
				shouldCool = false;
			
			if(shouldCool)
				this.heatEnergy = Math.max(this.heatEnergy - Math.max(this.heatEnergy / 1000, 1), 0);
			
			NBTTagCompound data = new NBTTagCompound();
			tank.writeToNBT(data, "tank");
			data.setBoolean("isOn", isOn);
			data.setInteger("heatEnergy", heatEnergy);
			this.networkPack(data, 25);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		tank.readFromNBT(nbt, "tank");
		isOn = nbt.getBoolean("isOn");
		heatEnergy = nbt.getInteger("heatEnergy");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "tank");
		isOn = nbt.getBoolean("isOn");
		heatEnergy = nbt.getInteger("heatEnergy");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "tank");
		nbt.setBoolean("isOn", isOn);
		nbt.setInteger("heatEnergy", heatEnergy);
	}

	@Override
	public FluidTank[] getReceivingTanks()  {
		return new FluidTank[] { tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerOilburner(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIOilburner(player.inventory, this);
	}

	@Override
	public int getHeatStored() {
		return heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
	}

	@Override
	public void setFillForSync(int fill, int index) { }

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if(type == tank.getTankType())
			tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) { }

	@Override
	public int getFluidFill(FluidType type) {
		return type == tank.getTankType() ? tank.getFill() : 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == tank.getTankType() ? tank.getMaxFill() : 0;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 256;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			this.isOn = !this.isOn;
		}
		this.markChanged();
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
					yCoord + 2,
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
}
