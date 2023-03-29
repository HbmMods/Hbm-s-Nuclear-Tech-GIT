package com.hbm.particle.psys.engine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

/**
 * HBM: reinventing the fucking wheel for the 15th time since 2014
 * 
 * @author hbm
 */
@SideOnly(Side.CLIENT)
public class PSysFX {
	
	public World world;
	public double posX;
	public double posY;
	public double posZ;
	public double prevPosX;
	public double prevPosY;
	public double prevPosZ;
	public double motionX;
	public double motionY;
	public double motionZ;
	public static double interpPosX;
	public static double interpPosY;
	public static double interpPosZ;

	public PSysFX() {
		
	}
}
