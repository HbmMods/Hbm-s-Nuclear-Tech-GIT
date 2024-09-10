package com.hbm.entity.logic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.ParticleBurstPacket;

import api.hbm.energymk2.IEnergyHandlerMK2;
import cofh.api.energy.IEnergyProvider;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityEMP extends Entity {
	
	List<int[]> machines;
	int life = 10 * 60 * 20;

	public EntityEMP(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			if(machines == null) {
				allocate();
			} else {
				shock();
			}
			
			if(this.ticksExisted > life)
				this.setDead();
		}
	}
	
	private void allocate() {
		
		machines = new ArrayList();
		int radius = 100;
		
		for(int x = -radius; x <= radius; x++) {
			int x2 = (int) Math.pow(x, 2);
			
			for(int y = -radius; y <= radius; y++) {
				int y2 = (int) Math.pow(y, 2);
				
				for(int z = -radius; z <= radius; z++) {
					int z2 = (int) Math.pow(z, 2);
					
					if(Math.sqrt(x2 + y2 + z2) <= radius) {
						add((int)posX + x, (int)posY + y, (int)posZ + z);
					}
				}
			}
		}
	}
	
	private void shock() {
		
		for(int i = 0; i < machines.size(); i++) {
			emp(
					machines.get(i)[0], 
					machines.get(i)[1], 
					machines.get(i)[2]
				);
		}
	}
	
	private void add(int x, int y, int z) {
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		if (te != null && te instanceof IEnergyHandlerMK2) {
			machines.add(new int[] { x, y, z });
		} else if (te != null && te instanceof IEnergyProvider) {
			machines.add(new int[] { x, y, z });
		}
	}
	
	private void emp(int x, int y, int z) {
		
		TileEntity te = worldObj.getTileEntity(x, y, z);
		
		boolean flag = false;
		
		if (te != null && te instanceof IEnergyHandlerMK2) {
			
			((IEnergyHandlerMK2)te).setPower(0);
			flag = true;
		}
		if (te != null && te instanceof IEnergyProvider) {

			((IEnergyProvider)te).extractEnergy(ForgeDirection.UP, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.UP), false);
			((IEnergyProvider)te).extractEnergy(ForgeDirection.DOWN, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.DOWN), false);
			((IEnergyProvider)te).extractEnergy(ForgeDirection.NORTH, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.NORTH), false);
			((IEnergyProvider)te).extractEnergy(ForgeDirection.SOUTH, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.SOUTH), false);
			((IEnergyProvider)te).extractEnergy(ForgeDirection.EAST, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.EAST), false);
			((IEnergyProvider)te).extractEnergy(ForgeDirection.WEST, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.WEST), false);
			flag = true;
		}
		
		if(flag && rand.nextInt(20) == 0) {
			
			PacketDispatcher.wrapper.sendToAllAround(new ParticleBurstPacket(x, y, z, Block.getIdFromBlock(Blocks.stained_glass), 3), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 50));
	        
		}
		
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) { }

}
