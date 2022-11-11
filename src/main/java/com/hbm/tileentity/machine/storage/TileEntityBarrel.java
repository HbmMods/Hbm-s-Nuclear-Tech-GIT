package com.hbm.tileentity.machine.storage;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerImpact;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityBarrel extends TileEntityMachineBase implements IFluidAcceptor, IFluidSource, IFluidStandardTransceiver, IPersistentNBT {
	
	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();

	public TileEntityBarrel() {
		super(6);
		tank = new FluidTank(Fluids.NONE, 0, 0);
	}

	public TileEntityBarrel(int capacity) {
		super(6);
		tank = new FluidTank(Fluids.NONE, capacity, 0);
	}

	@Override
	public String getName() {
		return "container.barrel";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			tank.setType(0, 1, slots);
			tank.loadTank(2, 3, slots);
			tank.unloadTank(4, 5, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			if(this.mode == 1 || this.mode == 2) {
				this.sendFluidToAll(tank.getTankType(), this);
			}
			
			if(this.mode == 0 || this.mode == 1) {
				this.subscribeToAllAround(tank.getTankType(), worldObj, xCoord, yCoord, zCoord);
			} else {
				this.unsubscribeToAllAround(tank.getTankType(), worldObj, xCoord, yCoord, zCoord);
			}
			
			age++;
			if(age >= 20)
				age = 0;
			
			if((mode == 1 || mode == 2) && (age == 9 || age == 19))
				fillFluidInit(tank.getTankType());
			
			if(tank.getFill() > 0) {
				checkFluidInteraction();
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", mode);
			this.networkPack(data, 50);
		}
	}
	
	public void checkFluidInteraction() {
		
		Block b = this.getBlockType();
		
		//for when you fill antimatter into a matter tank
		if(b != ModBlocks.barrel_antimatter && tank.getTankType().isAntimatter()) {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
		}
		
		//for when you fill hot or corrosive liquids into a plastic tank
		if(b == ModBlocks.barrel_plastic && (tank.getTankType().isCorrosive() || tank.getTankType().isHot())) {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.fizz", 1.0F, 1.0F);
		}
		
		//for when you fill corrosive liquid into an iron tank
		if((b == ModBlocks.barrel_iron && tank.getTankType().isCorrosive()) ||
				(b == ModBlocks.barrel_steel && tank.getTankType().hasTrait(FT_Corrosive.class) && tank.getTankType().getTrait(FT_Corrosive.class).getRating() > 50)) {
			ItemStack[] copy = this.slots.clone();
			this.slots = new ItemStack[6];
			worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.barrel_corroded);
			TileEntityBarrel barrel = (TileEntityBarrel)worldObj.getTileEntity(xCoord, yCoord, zCoord);
			
			if(barrel != null) {
				barrel.tank.setTankType(tank.getTankType());
				barrel.tank.setFill(Math.min(barrel.tank.getMaxFill(), tank.getFill()));
				barrel.slots = copy;
			}
			
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.fizz", 1.0F, 1.0F);
		}
		
		if(b == ModBlocks.barrel_corroded && worldObj.rand.nextInt(3) == 0) {
			tank.setFill(tank.getFill() - 1);
		}
		
		//For when Tom's firestorm hits a barrel full of water
		if(tank.getTankType() == Fluids.WATER && MainRegistry.proxy.getImpactFire(worldObj) > 0) {
			int light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);
			
			if(light > 7) {
				worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
			}
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		mode = data.getShort("mode");
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(mode == 2 || mode == 3)
			return 0;
		
		return type == this.tank.getTankType() ? tank.getMaxFill() : 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		return type == this.tank.getTankType() ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == tank.getTankType()) tank.setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return this.list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		this.list.clear();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		mode = nbt.getShort("mode");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setShort("mode", mode);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return (mode == 1 || mode == 2) ? new FluidTank[] {tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return (mode == 0 || mode == 1) ? new FluidTank[] {tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(tank.getFill() == 0) return;
		NBTTagCompound data = new NBTTagCompound();
		this.tank.writeToNBT(data, "tank");
		data.setShort("mode", mode);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		this.tank.readFromNBT(data, "tank");
		this.mode = data.getShort("nbt");
	}
}
