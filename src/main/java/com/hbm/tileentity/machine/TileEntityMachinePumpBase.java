package com.hbm.tileentity.machine;

import java.util.HashSet;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public abstract class TileEntityMachinePumpBase extends TileEntityLoadedBase implements IFluidStandardTransceiver, INBTPacketReceiver {
	
	public static final HashSet<Block> validBlocks = new HashSet();
	
	static {
		validBlocks.add(Blocks.grass);
		validBlocks.add(Blocks.dirt);
		validBlocks.add(Blocks.sand);
		validBlocks.add(Blocks.mycelium);
		validBlocks.add(ModBlocks.waste_earth);
		validBlocks.add(ModBlocks.dirt_dead);
		validBlocks.add(ModBlocks.dirt_oily);
		validBlocks.add(ModBlocks.sand_dirty);
		validBlocks.add(ModBlocks.sand_dirty_red);
	}
	
	public FluidTank water;

	public boolean isOn = false;
	public float rotor;
	public float lastRotor;
	public boolean onGround = false;
	public int groundCheckDelay = 0;
	
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(DirPos pos : getConPos()) {
				if(water.getFill() > 0) this.sendFluid(water, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			if(groundCheckDelay > 0) {
				groundCheckDelay--;
			} else {
				onGround = this.checkGround();
			}
			
			this.isOn = false;
			if(this.canOperate() && yCoord <= 70 && onGround) {
				this.isOn = true;
				this.operate();
			}
			
			NBTTagCompound data = this.getSync();
			INBTPacketReceiver.networkPack(this, data, 150);
			
		} else {
			
			this.lastRotor = this.rotor;
			if(this.isOn) this.rotor += 10F;
			
			if(this.rotor >= 360F) {
				this.rotor -= 360F;
				this.lastRotor -= 360F;
				
				MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "hbm:block.steamEngineOperate", 0.5F, 0.75F);
				MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "game.neutral.swim.splash", 1F, 0.5F);
			}
		}
	}
	
	protected boolean checkGround() {
		
		if(worldObj.provider.hasNoSky) return false;
		
		int validBlocks = 0;
		int invalidBlocks = 0;
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y >= -4; y--) {
				for(int z = -1; z <= 1; z++) {
					
					Block b = worldObj.getBlock(xCoord + x, yCoord + y, zCoord + z);
					
					if(y == -1 && !b.isNormalCube()) return false; // first layer has to be full solid
					
					if(this.validBlocks.contains(b)) validBlocks++;
					else invalidBlocks ++;
				}
			}
		}
		
		return validBlocks >= invalidBlocks; // valid block count has to be at least 50%
	}
	
	protected NBTTagCompound getSync() {
		NBTTagCompound data = new NBTTagCompound();
		data.setBoolean("isOn", isOn);
		data.setBoolean("onGround", onGround);
		water.writeToNBT(data, "w");
		return data;
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.isOn = nbt.getBoolean("isOn");
		this.onGround = nbt.getBoolean("onGround");
		water.readFromNBT(nbt, "w");
	}

	protected abstract boolean canOperate();
	protected abstract void operate();
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {water};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {water};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[0];
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
					yCoord + 5,
					zCoord + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
