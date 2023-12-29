package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.config.SpaceConfig;
import com.hbm.config.WorldConfig;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.PlanetaryTraitUtil;
import com.hbm.util.PlanetaryTraitUtil.Hospitality;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardSender;
import api.hbm.fluid.IFluidStandardTransceiver;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityAtmoExtractor extends TileEntityMachineBase implements IFluidSource, IEnergyUser, IFluidStandardSender {
	float rotSpeed;
	int consumption = 200;
	public float rot;
	public float prevRot;
	public long power = 0;
	public FluidTank tanks;
	public List<IFluidAcceptor> list = new ArrayList();

	public TileEntityAtmoExtractor() {
		super(0);
		tanks = new FluidTank(Fluids.AIR, 50000, 0);
	}

	@Override
	public String getName() {
		return "container.air";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			if(hasPower() && !hasTooMuch() && tanks.getMaxFill() > tanks.getFill()) {
				//int collect = Math.min(tanks.getMaxFill(), tanks.getFill()) / 50;
				//collect = Math.min(collect, tanks.getMaxFill() - tanks.getFill());
				
				tanks.setFill(tanks.getFill() + 50);
				power -= this.getMaxPower() / 100;
				//tank.setFill(tank.getFill() - 1);
				//this.power -= this.consumption;
		}
			
		if(worldObj.provider.dimensionId == SpaceConfig.eveDimension) {
			tanks.setTankType(Fluids.EVEAIR);
			this.markDirty();
			//player.addChatComponentMessage(new ChatComponentText("Changed type to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)).appendSibling(new ChatComponentTranslation("hbmfluid." + type.getName().toLowerCase())).appendSibling(new ChatComponentText("!")));
			//System.out.println("among us has been detected at " + WorldConfig.eveDimension);
			}
		if(worldObj.provider.dimensionId == SpaceConfig.dunaDimension) {
			tanks.setTankType(Fluids.CARBONDIOXIDE);
			this.markDirty();
			}
		if(PlanetaryTraitUtil.isDimensionWithTraitNT(worldObj, Hospitality.BREATHEABLE)) {
			tanks.setTankType(Fluids.AIR);
			this.markDirty();
		}
		if(PlanetaryTraitUtil.isDimensionWithTraitNT(worldObj, Hospitality.OXYNEG)) {
			tanks.setTankType(Fluids.NONE);
			this.markDirty();
		}

		
		this.sendFluidToAll(tanks, this);
		fillFluidInit(tanks.getTankType());

		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		tanks.writeToNBT(data, "water");
		
		this.networkPack(data, 50);
		} else {
			
			float maxSpeed = 30F;
			
			if(hasPower()) {
				rotSpeed += 0.1;
				//ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
				//ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				//Random rand = worldObj.rand;
				//double x = zCoord + 0.5D - dir.offsetX * 0.5D - rot.offsetZ * 0.5D;
				//double z = zCoord + 0.5D - dir.offsetZ * 0.5D - rot.offsetZ * 0.5D;
				//worldObj.spawnParticle("cloud", x + 0.0D + dir.offsetX - 0, yCoord + 5.5D, z + 0.0D + dir.offsetZ - 0, 0,-0.2, 0);
				rotSpeed += 0.1;
				// 1.20
				//+ 0.70 
				if(rotSpeed > maxSpeed)
					rotSpeed = maxSpeed;

				if(rotSpeed == maxSpeed && this.worldObj.getTotalWorldTime() % 5 == 0) {

					if(rotSpeed > maxSpeed)
						rotSpeed = maxSpeed;
				}
			} else {
				
				rotSpeed -= 0.1;
				
				if(rotSpeed < 0)
					rotSpeed = 0;
			}
			
			prevRot = rot;
			
			rot += rotSpeed;
			
			if(rot >= 360) {
				rot -= 360;
				prevRot -= 360;
			}
		}
	}
	




	protected void updateConnections() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
	}

	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		tanks.readFromNBT(data, "water");
	}

	public boolean hasPower() {
		return power >= this.getMaxPower() / 100;
	}
	
	public boolean hasTooMuch() {
		return tanks.getFill() >= 50000;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
		tanks.readFromNBT(nbt, "water");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		tanks.writeToNBT(nbt, "water");
	}


	@Override
	public void fillFluidInit(FluidType type) {

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			fillFluid(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}
	
	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type == tanks.getTankType())
			tanks.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type == tanks.getTankType())
			return tanks.getFill();

		return 0;
	}

	//@Override
	//public int getMaxFluidFill(FluidType type) {
	//	if(type == tanks.getTankType())
	//		return tanks.getMaxFill();

	//	return 0;
	//}

	@Override
	public void setFillForSync(int fill, int index) { }

	@Override
	public void setTypeForSync(FluidType type, int index) { }

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return 100000;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks };
	}

	//@Override
	//public FluidTank[] getReceivingTanks() {
	//	return new FluidTank[] { tanks };
	//}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tanks };
	}
}