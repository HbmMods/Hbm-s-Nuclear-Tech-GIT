package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCharger extends TileEntityLoadedBase implements IEnergyUser, INBTPacketReceiver {
	
	private List<EntityPlayer> players = new ArrayList();
	private long charge = 0;
	
	long lastOp = 0;
	boolean particles = false;
	
	public int usingTicks;
	public int lastUsingTicks;
	public static final int delay = 20;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
			this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord, zCoord + dir.offsetZ, dir);
			
			players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(1, 0.5, 1));
			
			charge = 0;
			
			for(EntityPlayer player : players) {
				
				for(int i = 0; i < 5; i++) {
					
					ItemStack stack = player.getEquipmentInSlot(i);
					
					if(stack != null && stack.getItem() instanceof IBatteryItem) {
						IBatteryItem battery = (IBatteryItem) stack.getItem();
						charge += Math.min(battery.getMaxCharge() - battery.getCharge(stack), battery.getChargeRate());
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("c", charge);
			data.setBoolean("p", worldObj.getTotalWorldTime() - lastOp < 4);
			INBTPacketReceiver.networkPack(this, data, 50);
		}
		
		lastUsingTicks = usingTicks;
		
		if(charge > 0 && usingTicks < delay)
			usingTicks++;
		if(charge <= 0 && usingTicks > 0)
			usingTicks--;
		
		if(particles) {
			Random rand = worldObj.rand;
			worldObj.spawnParticle("magicCrit", xCoord + 0.25 + rand.nextDouble() * 0.5, yCoord + rand.nextDouble() * 0.5, zCoord + 0.25 + rand.nextDouble() * 0.5, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.charge = nbt.getLong("c");
		this.particles = nbt.getBoolean("p");
	}

	@Override
	public long getPower() {
		return 0;
	}

	@Override
	public long getMaxPower() {
		return charge;
	}

	@Override
	public void setPower(long power) { }
	
	@Override
	public long transferPower(long power) {
		
		if(this.usingTicks < delay || power == 0)
			return power;
		
		lastOp = worldObj.getTotalWorldTime();
		
		for(EntityPlayer player : players) {
			
			for(int i = 0; i < 5; i++) {
				
				ItemStack stack = player.getEquipmentInSlot(i);
				
				if(stack != null && stack.getItem() instanceof IBatteryItem) {
					IBatteryItem battery = (IBatteryItem) stack.getItem();
					
					long toCharge = Math.min(battery.getMaxCharge() - battery.getCharge(stack), battery.getChargeRate());
					toCharge = Math.min(toCharge, power);
					battery.chargeBattery(stack, toCharge);
					power -= toCharge;
				}
			}
		}
		
		return power;
	}
}
