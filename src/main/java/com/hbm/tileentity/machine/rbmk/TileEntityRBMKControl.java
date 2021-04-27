package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public abstract class TileEntityRBMKControl extends TileEntityRBMKSlottedBase {

	@SideOnly(Side.CLIENT)
	public double lastLevel;
	public double level;
	public static final double speed = 0.00277D; // it takes around 18 seconds for the thing to fully extend
	public double targetLevel;

	public TileEntityRBMKControl() {
		super(0);
	}
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			
			this.lastLevel = this.level;
		
		} else {
			
			if(level < targetLevel) {
				
				level += speed;
				
				if(level > targetLevel)
					level = targetLevel;
			}
			
			if(level > targetLevel) {
				
				level -= speed;
				
				if(level < targetLevel)
					level = targetLevel;
			}
		}
		
		super.updateEntity();
	}
	
	@Override
	public int trackingRange() {
		return 150;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.level = nbt.getDouble("level");
		this.targetLevel = nbt.getDouble("targetLevel");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("level", this.level);
		nbt.setDouble("targetLevel", this.targetLevel);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 10, zCoord + 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public void onMelt(int reduce) {

		reduce = MathHelper.clamp_int(reduce, 1, 3);
		
		if(worldObj.rand.nextInt(3) == 0)
			reduce++;
		
		for(int i = 3; i >= 0; i--) {
			
			if(i <= 4 - reduce) {
				
				if(reduce > 1 && i == 4 - reduce) {
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris_burning);
				} else {
					worldObj.setBlock(xCoord, yCoord + i, zCoord, ModBlocks.pribris);
				}
				
			} else {
				worldObj.setBlock(xCoord, yCoord + i, zCoord, Blocks.air);
			}
			worldObj.markBlockForUpdate(xCoord, yCoord + i, zCoord);
		}
		
		int count = 2 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.ROD);
		}
		
		//control rods will not spawn lid projectiles since the lid is already part of the rod projectiles
		//super.onMelt(reduce);
	}
}
