package com.hbm.tileentity.machine.oil;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.world.feature.OilSpot;

import net.minecraft.block.Block;

public class TileEntityMachineFrackingTower extends TileEntityOilDrillBase implements IFluidAcceptor {

	public TileEntityMachineFrackingTower() {
		super();
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.OIL, 64_000, 0);
		tanks[1] = new FluidTank(Fluids.GAS, 64_000, 1);
		tanks[2] = new FluidTank(Fluids.FRACKSOL, 64_000, 2);
	}

	@Override
	public String getName() {
		return "container.frackingTower";
	}

	@Override
	protected void updateConnections() {
		this.updateStandardConnections(worldObj, xCoord, yCoord, zCoord);
	}

	@Override
	public long getMaxPower() {
		return 5_000_000;
	}

	@Override
	public int getPowerReq() {
		return 5000;
	}

	@Override
	public int getDelay() {
		return 20;
	}

	@Override
	public int getDrillDepth() {
		return 0;
	}

	@Override
	public boolean canPump() {
		boolean b = this.tanks[2].getFill() >= 10;
		
		if(!b) {
			this.indicator = 3;
		}
		
		return b;
	}

	@Override
	public boolean canSuckBlock(Block b) {
		return super.canSuckBlock(b) || b == ModBlocks.ore_bedrock_oil;
	}

	@Override
	public void doSuck(int x, int y, int z) {
		super.doSuck(x, y, z);
		
		if(worldObj.getBlock(x, y, z) == ModBlocks.ore_bedrock_oil) {
			onSuck(x, y, z);
		}
	}

	@Override
	public void onSuck(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		
		int oil = 0;
		int gas = 0;

		if(b == ModBlocks.ore_oil) {
			oil = 1000;
			gas = 100 + worldObj.rand.nextInt(401);
		}
		if(b == ModBlocks.ore_bedrock_oil) {
			oil = 100;
			gas = 10 + worldObj.rand.nextInt(41);
		}
		
		this.tanks[0].setFill(this.tanks[0].getFill() + oil);
		if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(tanks[0].getMaxFill());
		this.tanks[1].setFill(this.tanks[1].getFill() + gas);
		if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(tanks[1].getMaxFill());
		
		this.tanks[2].setFill(tanks[2].getFill() - 10);

		OilSpot.generateOilSpot(worldObj, xCoord, zCoord, 75, 10);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
	}

	@Override
	public int getMaxFillForReceive(FluidType type) {
		return type == tanks[2].getTankType() ? tanks[2].getMaxFill() : 0;
	}
}
