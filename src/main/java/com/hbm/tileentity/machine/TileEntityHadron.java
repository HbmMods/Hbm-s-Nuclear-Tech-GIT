package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IConsumer;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityHadron extends TileEntityMachineBase implements IConsumer {
	
	public long power;
	public static final long maxPower = 1000000000;
	
	public boolean isOn = false;
	public boolean analysis = true;
	
	public TileEntityHadron() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.hadron";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			drawPower();
		}
	}

	@Override
	public void handleButtonPacket(int value, int meta) {
		
		if(meta == 0)
			this.isOn = !this.isOn;
		if(meta == 1)
			this.analysis = !this.analysis;
	}
	
	private void drawPower() {
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			
			if(power == maxPower)
				return;

			int x = xCoord + dir.offsetX * 2;
			int y = yCoord + dir.offsetY * 2;
			int z = zCoord + dir.offsetZ * 2;
			
			TileEntity te = worldObj.getTileEntity(x, y, z);
			
			if(te instanceof TileEntityHadronPower) {
				
				TileEntityHadronPower plug = (TileEntityHadronPower)te;
				
				long toDraw = Math.min(maxPower - power, plug.getPower());
				this.setPower(power + toDraw);
				plug.setPower(plug.getPower() - toDraw);
			}
		}
	}
	
	static final int maxParticles = 1;
	List<Particle> particles = new ArrayList();
	
	private void updateParticle() {
		
		for(Particle particle : particles) {
			particle.update();
		}
	}

	@Override
	public void setPower(long i) {
		power = i;
		this.markDirty();
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	public static class Particle {
		
		//Starting values
		Item item;
		ForgeDirection dir;
		int posX;
		int posY;
		int posZ;
		
		//Progressing values
		int momentum;
		int charge;
		
		public Particle(Item item, ForgeDirection dir, int posX, int posY, int posZ) {
			this.item = item;
			this.dir = dir;
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			
			this.charge = 10;
			this.momentum = 0;
		}
		
		public void update() {
			
		}
	}
}
