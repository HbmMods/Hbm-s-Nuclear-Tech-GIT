package com.hbm.tileentity.machine;


import com.hbm.config.MobConfig;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityChimneyBrick extends TileEntityChimneyBase {
	
	@Override
	public void spawnParticles() {

		if(worldObj.getTotalWorldTime() % 2 == 0) {
			NBTTagCompound fx = new NBTTagCompound();
			fx.setString("type", "tower");
			fx.setFloat("lift", 10F);
			fx.setFloat("base", 0.5F);
			fx.setFloat("max", 3F);
			fx.setInteger("life", 250 + worldObj.rand.nextInt(50));
			fx.setInteger("color",0x404040);
			fx.setDouble("posX", xCoord + 0.5);
			fx.setDouble("posY", yCoord + 12);
			fx.setDouble("posZ", zCoord + 0.5);
			MainRegistry.proxy.effectNT(fx);
		}
	}

	@Override
	public double getPollutionMod() {
		return MobConfig.rampantMode ? MobConfig.rampantSmokeStackOverride : 0.25D;
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
					yCoord + 13,
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
