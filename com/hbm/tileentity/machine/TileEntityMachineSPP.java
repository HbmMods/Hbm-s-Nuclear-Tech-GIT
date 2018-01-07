package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineSPP extends TileEntity implements ISource {
	
	public long power;
	public static final long maxPower = 100000;
	public int age = 0;
	public int gen = 0;
	public List<IConsumer> list = new ArrayList();
	
	@Override
	public void updateEntity() {

		MainRegistry.logger.debug("0");
		
		age++;
		if(age >= 20)
			age -= 20;
		if(age == 9 || age == 19)
			ffgeuaInit();
		
		if(!worldObj.isRemote) {
			//if(age == 1)
				gen = checkStructure() * 15;
			if(gen > 0)
				power += gen;
			if(power > maxPower)
				power = maxPower;
		}
		
	}
	
	public int checkStructure() {

		int h = 0;
		
		for(int i = yCoord + 1; i < 254; i++)
			if(worldObj.getBlock(xCoord, i, zCoord) == ModBlocks.machine_spp_top) {
				h = i;
				break;
			}
		
		for(int i = yCoord + 1; i < h - 1; i++)
			if(!checkSegment(i))
				return 0;

		
		return h - yCoord - 1;
	}
	
	public boolean checkSegment(int y) {
		
		//   BBB
		//   BAB
		//   BBB
		System.out.println(y);
		return (worldObj.getBlock(xCoord + 1, y, zCoord) != Blocks.air &&
				worldObj.getBlock(xCoord + 1, y, zCoord + 1) != Blocks.air &&
				worldObj.getBlock(xCoord + 1, y, zCoord - 1) != Blocks.air &&
				worldObj.getBlock(xCoord - 1, y, zCoord + 1) != Blocks.air &&
				worldObj.getBlock(xCoord - 1, y, zCoord) != Blocks.air &&
				worldObj.getBlock(xCoord - 1, y, zCoord - 1) != Blocks.air &&
				worldObj.getBlock(xCoord, y, zCoord + 1) != Blocks.air &&
				worldObj.getBlock(xCoord, y, zCoord - 1) != Blocks.air &&
				worldObj.getBlock(xCoord, y, zCoord) == Blocks.air);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public long getSPower() {
		return this.power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return this.list;
	}

}
