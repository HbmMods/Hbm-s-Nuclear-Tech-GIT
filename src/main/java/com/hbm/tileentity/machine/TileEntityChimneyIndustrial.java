package com.hbm.tileentity.machine;

import com.hbm.config.MobConfig;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityChimneyIndustrial extends TileEntityChimneyBase {
	
	@Override
	public void spawnParticles() {

		if(worldObj.getTotalWorldTime() % 2 == 0) {
			NBTTagCompound fx = new NBTTagCompound();
			fx.setString("type", "tower");
			fx.setFloat("lift", 10F);
			fx.setFloat("base", 0.75F);
			fx.setFloat("max", 3F);
			fx.setInteger("life", 250 + worldObj.rand.nextInt(50));
			fx.setInteger("color",0x404040);
			fx.setDouble("posX", xCoord + 0.5);
			fx.setDouble("posY", yCoord + 22);
			fx.setDouble("posZ", zCoord + 0.5);
			MainRegistry.proxy.effectNT(fx);
		}
	}

	@Override
	public double getPollutionMod() {
		return MobConfig.rampantMode ? MobConfig.rampantSmokeStackOverride/2 : 0.1D;
	}

	@Override
	public boolean cpaturesSoot() {
		return true;
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
					yCoord + 23,
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
