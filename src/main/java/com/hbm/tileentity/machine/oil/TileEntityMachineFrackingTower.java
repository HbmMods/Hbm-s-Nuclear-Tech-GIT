package com.hbm.tileentity.machine.oil;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.FluidTank;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class TileEntityMachineFrackingTower extends TileEntityOilDrillBase implements IFluidAcceptor {

	public TileEntityMachineFrackingTower() {
		super();
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(FluidType.OIL, 64_000, 0);
		tanks[1] = new FluidTank(FluidType.GAS, 64_000, 1);
		tanks[2] = new FluidTank(FluidType.FRACKSOL, 64_000, 2);
	}

	@Override
	public String getName() {
		return "container.frackingTower";
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

		for(int i = 0; i < 10; i++) {
			int rX = xCoord + (int)(worldObj.rand.nextGaussian() * 75);
			int rZ = zCoord + (int)(worldObj.rand.nextGaussian() * 75);
			int rY = worldObj.getHeightValue(rX, rZ) - 1;
			
			Block ground = worldObj.getBlock(rX, rY, rZ);
			
			if(ground == Blocks.grass || ground == Blocks.dirt) {
				worldObj.setBlock(rX, rY, rZ, worldObj.rand.nextInt(10) == 0 ? ModBlocks.dirt_oily : ModBlocks.dirt_dead);
				
			} else if(ground == Blocks.sand || ground == ModBlocks.ore_oil_sand) {
				
				if(worldObj.getBlockMetadata(rX, rY, rZ) == 1)
					worldObj.setBlock(rX, rY, rZ, ModBlocks.sand_dirty_red);
				else
					worldObj.setBlock(rX, rY, rZ, ModBlocks.sand_dirty);
				
			} else if(ground.getMaterial() == Material.leaves) {
				worldObj.setBlockToAir(rX, rY, rZ);
			}
		}
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type == tanks[2].getTankType() ? tanks[2].getMaxFill() : 0;
	}

}
