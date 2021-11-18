package com.hbm.tileentity.machine.oil;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.FluidTypeHandler.FluidType;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachineOilWell extends TileEntityOilDrillBase {

	@Override
	public String getName() {
		return "container.oilWell";
	}

	@Override
	protected void updateConnections() {
		this.trySubscribe(worldObj, xCoord + 2, yCoord, zCoord);
		this.trySubscribe(worldObj, xCoord - 2, yCoord, zCoord);
		this.trySubscribe(worldObj, xCoord, yCoord, zCoord + 2);
		this.trySubscribe(worldObj, xCoord, yCoord, zCoord - 2);
	}

	@Override
	public long getMaxPower() {
		return 100_000;
	}

	@Override
	public int getPowerReq() {
		return 100;
	}

	@Override
	public int getDelay() {
		return 50;
	}

	@Override
	public void onDrill(int y) {
		Block b = worldObj.getBlock(xCoord, y, zCoord);
		ItemStack stack = new ItemStack(b);
		int[] ids = OreDictionary.getOreIDs(stack);
		for(Integer i : ids) {
			String name = OreDictionary.getOreName(i);
			
			if("oreUranium".equals(name)) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						if(worldObj.getBlock(xCoord + j, yCoord + 7, zCoord + j).isReplaceable(worldObj, xCoord + j, yCoord + 7, zCoord + k)) {
							worldObj.setBlock(xCoord + k, yCoord + 7, zCoord + k, ModBlocks.gas_radon_dense);
						}
					}
				}
			}
			
			if("oreAsbestos".equals(name)) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						if(worldObj.getBlock(xCoord + j, yCoord + 7, zCoord + j).isReplaceable(worldObj, xCoord + j, yCoord + 7, zCoord + k)) {
							worldObj.setBlock(xCoord + k, yCoord + 7, zCoord + k, ModBlocks.gas_asbestos);
						}
					}
				}
			}
		}
	}

	@Override
	public void onSuck(int x, int y, int z) {

		ExplosionLarge.spawnOilSpills(worldObj, xCoord + 0.5F, yCoord + 5.5F, zCoord + 0.5F, 3);
		worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 2.0F, 0.5F);
		
		this.tanks[0].setFill(this.tanks[0].getFill() + 500);
		if(this.tanks[0].getFill() > this.tanks[0].getMaxFill()) this.tanks[0].setFill(tanks[0].getMaxFill());
		this.tanks[1].setFill(this.tanks[1].getFill() + (100 + worldObj.rand.nextInt(401)));
		if(this.tanks[1].getFill() > this.tanks[1].getMaxFill()) this.tanks[1].setFill(tanks[1].getMaxFill());
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 2, getTact(), type);
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
					yCoord + 7,
					zCoord + 2
					);
		}
		
		return bb;
	}
}
