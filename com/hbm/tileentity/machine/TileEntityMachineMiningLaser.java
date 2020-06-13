package com.hbm.tileentity.machine;

import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineMiningLaser extends TileEntityMachineBase {
	
	public long power;
	public static final long maxPower = 1000000;
	public static final int consumption = 10000;

	public int targetX;
	public int targetY;
	public int targetZ;
	public int lastTargetX;
	public int lastTargetY;
	public int lastTargetZ;
	public boolean beam;
	boolean lock = false;
	double breakProgress;

	public TileEntityMachineMiningLaser() {
		super(30);
	}

	@Override
	public String getName() {
		return "container.miningLaser";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			//reset progress if the position changes
			if(lastTargetX != targetX ||
					lastTargetY != targetY ||
					lastTargetZ != targetZ)
				breakProgress = 0;
			
			//set last positions for interpolation and the like
			lastTargetX = targetX;
			lastTargetY = targetY;
			lastTargetZ = targetZ;
			
			for(int i = 0; i < 1; i++) {
				if(targetY <= 0)
					targetY = yCoord -1;
				
				scan();
				
				if(beam && canBreak(worldObj.getBlock(targetX, targetY, targetZ))) {
					
					breakProgress += getSpeed();
					
					if(breakProgress < 1) {
						worldObj.destroyBlockInWorldPartially(-1, targetX, targetY, targetZ, (int) Math.floor(breakProgress * 10));
					} else {
						worldObj.func_147480_a(targetX, targetY, targetZ, false);
						breakProgress = 0;
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("lastX", lastTargetX);
			data.setInteger("lastY", lastTargetY);
			data.setInteger("lastZ", lastTargetZ);
			data.setInteger("x", targetX);
			data.setInteger("y", targetY);
			data.setInteger("z", targetZ);
			data.setBoolean("beam", beam);
			
			this.networkPack(data, 250);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {

		this.lastTargetX = data.getInteger("lastX");
		this.lastTargetY = data.getInteger("lastY");
		this.lastTargetZ = data.getInteger("lastZ");
		this.targetX = data.getInteger("x");
		this.targetY = data.getInteger("y");
		this.targetZ = data.getInteger("z");
		this.beam = data.getBoolean("beam");
	}
	
	public double getSpeed() {
		
		float hardness = worldObj.getBlock(targetX, targetY, targetZ).getBlockHardness(worldObj, targetX, targetY, targetZ) * 10;
		
		if(hardness == 0)
			return 1;
		
		return 1 / hardness;
	}
	
	public void scan() {
		
		int range = getRange();
		
		for(int x = -range; x <= range; x++) {
			for(int z = -range; z <= range; z++) {
				
				if(canBreak(worldObj.getBlock(x + xCoord, targetY, z + zCoord))) {
					targetX = x + xCoord;
					targetZ = z + zCoord;
					beam = true;
					return;
				}
			}
		}
		
		beam = false;
		targetY--;
	}
	
	private boolean canBreak(Block block) {
		return block != Blocks.air && block != Blocks.water && block != Blocks.flowing_water;
	}
	
	/*public int targetHeight(int x, int z) {
		
		for(int y = yCoord - 1; y > 0; y--) {
			
			if(worldObj.getBlock(x, y, z) != Blocks.air)
				return y;
		}
		
		return 0;
	}*/
	
	public int getRange() {
		
		return 20;
		
		/*int range = 1;
		
		for(int i = 1; i < 9; i++) {
			
			if(slots[i] != null) {
				
				if(slots[i].getItem() == ModItems.upgrade_effect_1)
					range += 2;
				else if(slots[i].getItem() == ModItems.upgrade_effect_2)
					range += 4;
				else if(slots[i].getItem() == ModItems.upgrade_effect_3)
					range += 6;
			}
		}
		
		return Math.min(range, 26);*/
	}
	
	public int getWidth() {
		
		return 1 + getRange() * 2;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

}
