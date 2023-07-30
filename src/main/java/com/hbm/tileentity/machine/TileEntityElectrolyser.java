package com.hbm.tileentity.machine;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerElectrolyserFluid;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIElectrolyserFluid;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityElectrolyser extends TileEntityMachineBase implements IEnergyUser, IControlReceiver, IGUIProvider {
	
	public long power;
	public static final long maxPower = 20000000;
	public static final int usageBase = 10000;
	public int usage;
	
	public int progressFluid;
	public static final int processFluidTimeBase = 100;
	public int processFluidTime;
	public int progressOre;
	public static final int processOreTimeBase = 1000;
	public int processOreTime;
	
	public FluidTank[] tanks;

	public TileEntityElectrolyser() {
		//0: Battery
		//1-2: Upgrades
		//// FLUID
		//3-4: Fluid ID
		//5-10: Fluid IO
		//11-13: Byproducts
		//// METAL
		super(21);
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(Fluids.WATER, 16000);
		tanks[1] = new FluidTank(Fluids.HYDROGEN, 16000);
		tanks[2] = new FluidTank(Fluids.OXYGEN, 16000);
		tanks[3] = new FluidTank(Fluids.NITRIC_ACID, 16000);
	}

	@Override
	public String getName() {
		return "container.machineElectrolyser";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			data.setInteger("progressFluid", this.progressFluid);
			data.setInteger("progressOre", this.progressOre);
			data.setInteger("usage", this.usage);
			data.setInteger("processFluidTime", this.processFluidTime);
			data.setInteger("processOreTime", this.processOreTime);
			for(int i = 0; i < 4; i++) tanks[i].writeToNBT(data, "t" + i);
			this.networkPack(data, 50);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.progressFluid = nbt.getInteger("progressFluid");
		this.progressOre = nbt.getInteger("progressOre");
		this.usage = nbt.getInteger("usage");
		this.processFluidTime = nbt.getInteger("processFluidTime");
		this.processOreTime = nbt.getInteger("processOreTime");
		for(int i = 0; i < 4; i++) tanks[i].readFromNBT(nbt, "t" + i);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord - 0,
					zCoord - 5,
					xCoord + 6,
					yCoord + 4,
					zCoord + 6
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
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerElectrolyserFluid(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIElectrolyserFluid(player.inventory, this);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
	}
	
	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {
		
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}
}
