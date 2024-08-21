package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerHeaterHeatex;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.inventory.gui.GUIHeaterHeatex;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHeaterHeatex extends TileEntityMachineBase implements IHeatSource, INBTPacketReceiver, IFluidStandardTransceiver, IGUIProvider, IControlReceiver {
	
	public FluidTank[] tanks;
	public int amountToCool = 1;
	public int tickDelay = 1;
	public int heatEnergy;
	
	public TileEntityHeaterHeatex() {
		super(1);
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.COOLANT_HOT, 24_000);
		this.tanks[1] = new FluidTank(Fluids.COOLANT, 24_000);
	}

	@Override
	public String getName() {
		return "container.heaterHeatex";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.tanks[0].setType(0, slots);
			this.setupTanks();
			this.updateConnections();
			
			this.heatEnergy *= 0.999;
			
			NBTTagCompound data = new NBTTagCompound();
			tanks[0].writeToNBT(data, "0");
			this.tryConvert();
			tanks[1].writeToNBT(data, "1");
			data.setInteger("heat", heatEnergy);
			data.setInteger("toCool", amountToCool);
			data.setInteger("delay", tickDelay);
			INBTPacketReceiver.networkPack(this, data, 25);
			
			for(DirPos pos : getConPos()) {
				if(this.tanks[1].getFill() > 0) this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		tanks[0].readFromNBT(nbt, "0");
		tanks[1].readFromNBT(nbt, "1");
		this.heatEnergy = nbt.getInteger("heat");
		this.amountToCool = nbt.getInteger("toCool");
		this.tickDelay = nbt.getInteger("delay");
	}
	
	protected void setupTanks() {
		
		if(tanks[0].getTankType().hasTrait(FT_Coolable.class)) {
			FT_Coolable trait = tanks[0].getTankType().getTrait(FT_Coolable.class);
			if(trait.getEfficiency(CoolingType.HEATEXCHANGER) > 0) {
				tanks[1].setTankType(trait.coolsTo);
				return;
			}
		}

		tanks[0].setTankType(Fluids.NONE);
		tanks[1].setTankType(Fluids.NONE);
	}
	
	protected void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	protected void tryConvert() {
		
		if(!tanks[0].getTankType().hasTrait(FT_Coolable.class)) return;
		if(tickDelay < 1) tickDelay = 1;
		if(worldObj.getTotalWorldTime() % tickDelay != 0) return;
		
		FT_Coolable trait = tanks[0].getTankType().getTrait(FT_Coolable.class);
		
		int inputOps = tanks[0].getFill() / trait.amountReq;
		int outputOps = (tanks[1].getMaxFill() - tanks[1].getFill()) / trait.amountProduced;
		int opCap = this.amountToCool;
		
		int ops = Math.min(inputOps, Math.min(outputOps, opCap));
		tanks[0].setFill(tanks[0].getFill() - trait.amountReq * ops);
		tanks[1].setFill(tanks[1].getFill() + trait.amountProduced * ops);
		this.heatEnergy += trait.heatEnergy * ops * trait.getEfficiency(CoolingType.HEATEXCHANGER);
		this.markChanged();
	}
	
	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ, dir),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite())
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.tanks[0].readFromNBT(nbt, "0");
		this.tanks[1].readFromNBT(nbt, "1");
		this.heatEnergy = nbt.getInteger("heatEnergy");
		this.amountToCool = nbt.getInteger("toCool");
		this.tickDelay = nbt.getInteger("delay");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.tanks[0].writeToNBT(nbt, "0");
		this.tanks[1].writeToNBT(nbt, "1");
		nbt.setInteger("heatEnergy", heatEnergy);
		nbt.setInteger("toCool", amountToCool);
		nbt.setInteger("delay", tickDelay);
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
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		ForgeDirection facing = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		return dir == facing || dir == facing.getOpposite();
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerHeaterHeatex(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIHeaterHeatex(player.inventory, this);
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
					yCoord + 1,
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
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 16;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("toCool")) this.amountToCool = MathHelper.clamp_int(data.getInteger("toCool"), 1, tanks[0].getMaxFill());
		if(data.hasKey("delay")) this.tickDelay = Math.max(data.getInteger("delay"), 1);
		
		this.markChanged();
	}
}
