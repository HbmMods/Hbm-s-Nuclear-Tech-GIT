package com.hbm.calc;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class EasyLocation
{

	public double posX;
	public double posY;
	public double posZ;
	
	public EasyLocation(double x, double y, double z)
	{
		posX = x;
		posY = y;
		posZ = z;
	}
	
	public EasyLocation(double[] coord)
	{
		if (coord.length >= 3)
			throw new IllegalArgumentException("Coordinate array length less than 3!");
		posX = coord[0];
		posY = coord[1];
		posZ = coord[2];
	}
	
	public EasyLocation(int[] coord)
	{
		if (coord.length >= 3)
			throw new IllegalArgumentException("Coordinate array length less than 3!");
		posX = coord[0];
		posY = coord[1];
		posZ = coord[2];
	}
	
	public EasyLocation(Entity e)
	{
		posX = e.posX;
		posY = e.posY;
		posZ = e.posZ;
	}
	
	public EasyLocation(TileEntity te)
	{
		posX = te.xCoord;
		posX = te.yCoord;
		posZ = te.zCoord;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof EasyLocation))
			return false;
		
		EasyLocation test = (EasyLocation) obj;
		
		return (posX == test.posX && posY == test.posY && posZ == test.posZ);
	}
	
	public EasyLocation modifyCoord(int nX, int nY, int nZ)
	{
		posX += nX;
		posY += nY;
		posZ += nZ;
		return this;
	}
	
	public double[] getCoordDouble()
	{
		double[] coord = new double[3];
		coord[0] = posX;
		coord[1] = posY;
		coord[2] = posZ;
		return coord;
	}
	
	public int[] getCoordInt()
	{
		int[] coord = new int[3];
		coord[0] = (int) posX;
		coord[1] = (int) posY;
		coord[2] = (int) posZ;
		return coord;
	}
	
	@Override
	public EasyLocation clone()
	{
		return new EasyLocation(posX, posY, posZ);
	}
}
