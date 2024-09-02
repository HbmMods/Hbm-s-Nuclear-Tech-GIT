package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import api.hbm.block.IPileNeutronReceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

public class TileEntityPileFuel extends TileEntityPileBase implements IPileNeutronReceiver {

	public int heat;
	public static final int maxHeat = 1000;
	public int neutrons;
	public int lastNeutrons;
	public int progress;
	public static final int maxProgress = GeneralConfig.enable528 ? 75000 : 50000; //might double to reduce compact setup's effectiveness

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			dissipateHeat();
			checkRedstone(react());
			transmute();
			
			if(this.heat >= this.maxHeat) {
				worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 4, true, true);
				worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.gas_radon_dense);
			}
			
			if(worldObj.rand.nextFloat() * 2F <= this.heat / (float)this.maxHeat) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "smoke");
				data.setDouble("mY", 0.05);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 1, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5),
						new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.5, yCoord + 1, zCoord + 0.5, 20));
				MainRegistry.proxy.effectNT(data);
			}
			
			if(this.progress >= this.maxProgress) {
				worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.block_graphite_plutonium, this.getBlockMetadata() & 7, 3);
			}
		}
		
	}
	
	private void dissipateHeat() {
		this.heat -= (this.getBlockMetadata() & 4) == 4 ? heat * 0.065 : heat * 0.05; //remove 5% of the stored heat per tick; 6.5% for windscale
	}
	
	private int react() {
		
		int reaction = (int) (this.neutrons * (1D - ((double)this.heat / (double)this.maxHeat) * 0.5D)); //max heat reduces reaction by 50% due to thermal expansion
		
		this.lastNeutrons = this.neutrons;
		this.neutrons = 0;
		
		int lastProgress = this.progress;
		
		this.progress += reaction;
		
		if(reaction <= 0)
			return lastProgress;
		
		this.heat += reaction;
		
		for(int i = 0; i < 12; i++)
			this.castRay((int) Math.max(reaction * 0.25, 1), 5);
		
		return lastProgress;
	}
	
	private void checkRedstone(int lastProgress) {
		int lastLevel = MathHelper.clamp_int((lastProgress * 16) / maxProgress, 0, 15);
		int newLevel = MathHelper.clamp_int((progress * 16) / maxProgress, 0, 15);
		if(lastLevel != newLevel)
			worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType());
	}
	
	private void transmute() {
		
		if((this.getBlockMetadata() & 8) == 8) {
			if(this.progress < this.maxProgress - 1000) //Might be subject to change, but 1000 seems like a good number.
				this.progress = maxProgress - 1000;
			
			return;
		} else if(this.progress >= maxProgress - 1000) {
			worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.getBlockMetadata() | 8, 3);
			return;
		}
	}

	@Override
	public void receiveNeutrons(int n) {
		this.neutrons += n;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.heat = nbt.getInteger("heat");
		this.progress = nbt.getInteger("progress");
		this.neutrons = nbt.getInteger("neutrons");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", this.heat);
		nbt.setInteger("progress", this.progress);
		nbt.setInteger("neutrons", this.neutrons);
	}
}
