package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;

import api.hbm.block.IPileNeutronReceiver;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPileFuel extends TileEntityPileBase implements IPileNeutronReceiver {

	public int heat;
	public static final int maxHeat = 1000;
	public int neutrons;
	public int lastNeutrons;
	public int progress;
	public static final int maxProgress = 100000;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			dissipateHeat();
			react();
			
			if(this.heat >= this.maxHeat) {
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
				worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
			}
			
			if(this.progress >= this.maxProgress) {
				worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.block_graphite_plutonium, this.getBlockMetadata(), 3);
			}
		}
	}
	
	private void dissipateHeat() {
		this.heat -= heat * 0.025; //remove 2.5% of the stored heat per tick
	}
	
	private void react() {
		
		int reaction = (int) (this.neutrons * (1D - ((double)this.heat / (double)this.maxHeat) * 0.5D)); //max heat reduces reaction by 50% due to thermal expansion
		
		this.progress += reaction;
		
		if(reaction <= 0)
			return;
		
		this.heat += reaction;
		
		for(int i = 0; i < 6; i++)
			this.castRay(reaction, 5);
		
		this.lastNeutrons = this.neutrons;
		this.neutrons = 0;
	}

	@Override
	public void receiveNeutrons(int n) {
		this.neutrons += n;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.heat = nbt.getInteger("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", this.heat);
	}
}
