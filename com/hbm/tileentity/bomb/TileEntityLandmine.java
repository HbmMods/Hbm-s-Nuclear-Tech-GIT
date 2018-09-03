package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.Landmine;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.ResourceManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityLandmine extends TileEntity {
	
	private boolean isPrimed = false;

	public void updateEntity() {

		if(!worldObj.isRemote) {
			Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
			double range = 1;

			if (block == ModBlocks.mine_ap) {
				range = 1.5D;
			}
			if (block == ModBlocks.mine_he) {
				range = 2;
			}
			if (block == ModBlocks.mine_shrap) {
				range = 1.5D;
			}
			if (block == ModBlocks.mine_fat) {
				range = 2.5D;
			}
	
			List<Object> list = worldObj.getEntitiesWithinAABBExcludingEntity(null,
					AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - 1, zCoord - range, xCoord + range, yCoord + 1, zCoord + range));
	
			boolean flag = false;
			for (Object o : list) {
	
				if (o instanceof EntityLivingBase) {
	
					flag = true;
					
					if(isPrimed) {
						
						((Landmine)block).explode(worldObj, xCoord, yCoord, zCoord);
					}
	
					return;
				}
			}
			
			if(!isPrimed && !flag) {
	
				this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:item.techBoop", 2.0F, 1.0F);
				isPrimed = true;
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		isPrimed = nbt.getBoolean("primed");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("primed", isPrimed);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
