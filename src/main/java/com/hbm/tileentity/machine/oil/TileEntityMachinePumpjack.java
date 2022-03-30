package com.hbm.tileentity.machine.oil;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachinePumpjack extends TileEntityOilDrillBase {
	
	public float rot = 0;
	public float prevRot = 0;
	public float speed = 0;

	@Override
	public String getName() {
		return "container.pumpjack";
	}

	@Override
	protected void updateConnections() {
		this.getBlockMetadata();
		ForgeDirection dir = ForgeDirection.getOrientation(this.blockMetadata - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		this.trySubscribe(worldObj, xCoord + rot.offsetX * 2 + dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2 + dir.offsetZ * 2, ForgeDirection.UNKNOWN); //brain overheating, do this shit somewhen else
		this.trySubscribe(worldObj, xCoord + rot.offsetX * 2 + dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 4 - dir.offsetZ * 2, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + rot.offsetX * 4 - dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 4 + dir.offsetZ * 2, ForgeDirection.UNKNOWN);
		this.trySubscribe(worldObj, xCoord + rot.offsetX * 4 - dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2 - dir.offsetZ * 2, ForgeDirection.UNKNOWN);
	}

	@Override
	public long getMaxPower() {
		return 250_000;
	}

	@Override
	public int getPowerReq() {
		return 200;
	}

	@Override
	public int getDelay() {
		return 25;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {

			this.prevRot = rot;
			
			if(this.indicator == 0) {
				this.rot += speed;
			}
			
			if(this.rot >= 360) {
				this.prevRot -= 360;
				this.rot -= 360;
			}
		}
	}
	
	@Override
	public void sendUpdate() {
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setInteger("indicator", this.indicator);
		data.setFloat("speed", this.indicator == 0 ? (5F + (2F * this.speedLevel)) + (this.overLevel - 1F) * 10: 0F);
		this.networkPack(data, 25);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.indicator = nbt.getInteger("indicator");
		this.speed = nbt.getFloat("speed");
	}

	@Override
	public void onSuck() {
		
		this.tanks[0].setFill(this.tanks[0].getFill() + 750);
		if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(tanks[0].getMaxFill());
		this.tanks[1].setFill(this.tanks[1].getFill() + (50 + worldObj.rand.nextInt(201)));
		if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(tanks[1].getMaxFill());
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		int pX2 = xCoord + rot.offsetX * 2;
		int pZ2 = zCoord + rot.offsetZ * 2;
		int pX4 = xCoord + rot.offsetX * 4;
		int pZ4 = zCoord + rot.offsetZ * 4;
		int oX = Math.abs(dir.offsetX) * 2;
		int oZ = Math.abs(dir.offsetZ) * 2;
		
		fillFluid(pX2 + oX, this.yCoord, pZ2 + oZ, getTact(), type);
		fillFluid(pX2 - oX, this.yCoord, pZ2 - oZ, getTact(), type);
		fillFluid(pX4 + oX, this.yCoord, pZ4 + oZ, getTact(), type);
		fillFluid(pX4 - oX, this.yCoord, pZ4 - oZ, getTact(), type);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 7,
					yCoord,
					zCoord - 7,
					xCoord + 8,
					yCoord + 6,
					zCoord + 8
					);
		}
		
		return bb;
	}
}
