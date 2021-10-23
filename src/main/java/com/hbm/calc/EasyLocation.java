package com.hbm.calc;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EasyLocation
{
	public double posX;
	public double posY;
	public double posZ;
	public int dimID;
	public World world;
	
	public EasyLocation(double x, double y, double z)
	{
		posX = x;
		posY = y;
		posZ = z;
	}
	
	public EasyLocation(double[] coord)
	{
		if (coord.length < 3)
			throw new IllegalArgumentException("Coordinate array length less than 3!");
		posX = coord[0];
		posY = coord[1];
		posZ = coord[2];
	}
	
	public EasyLocation(int[] coord)
	{
		if (coord.length < 3)
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
		world = e.worldObj;
		dimID = e.worldObj.provider.dimensionId;
	}
	
	public EasyLocation(TileEntity te)
	{
		posX = te.xCoord;
		posX = te.yCoord;
		posZ = te.zCoord;
		world = te.getWorldObj();
		dimID = te.getWorldObj().provider.dimensionId;
	}
	
	public static double distance(EasyLocation loc1, EasyLocation loc2)
	{
		return Math.sqrt(Math.pow(loc2.posX - loc1.posX, 2) + Math.pow(loc2.posY - loc1.posY, 2) + Math.pow(loc2.posZ - loc1.posZ, 2));
	}
	
	public double distance(EasyLocation loc)
	{
		return distance(this, loc);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof EasyLocation))
			return false;
		
		EasyLocation test = (EasyLocation) obj;
		
		return (posX == test.posX && posY == test.posY && posZ == test.posZ && dimID == test.dimID);
	}
	/** Also sets the dimID **/
	public EasyLocation setWorld(World worldIn)
	{
		world = worldIn;
		dimID = worldIn.provider.dimensionId;
		return this;
	}
	
	public EasyLocation modifyCoord(int nX, int nY, int nZ)
	{
		posX += nX;
		posY += nY;
		posZ += nZ;
		return this;
	}
	
	public EasyLocation modifyCoord(double nX, double nY, double nZ)
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
	@Deprecated
	public TileEntity getTEAtCoord(World worldIn)
	{
		return worldIn.getTileEntity((int) posX, (int) posY, (int) posZ);
	}
	/** <b><i>Has prerequisite of having the world object set</b></i> **/
	public TileEntity getTEAtCoord()
	{
		return world.getTileEntity((int) posX, (int) posY, (int) posZ);
	}
	@Deprecated
	public Block getBlockAtCoord(World worldIn)
	{
		return worldIn.getBlock((int) posX, (int) posY, (int) posZ);
	}
	/** <b><i>Has prerequisite of having the world object set</b></i> **/
	public Block getBlockAtCoord()
	{
		return world.getBlock((int) posX, (int) posY, (int) posZ);
	}
	/** <b><i>Has prerequisite of having the world object set</b></i>
	 * @param b The block in question
	 * @return Whatever the {@link#World.setBlock()} method is supposed to return**/
	public boolean setBlockAtCoord(Block b)
	{
		return world.setBlock((int) posX, (int) posY, (int) posZ, b);
	}
	@Deprecated
	public static Block getBlockAtCoord(EasyLocation loc, World worldIn)
	{
		return loc.getBlockAtCoord(worldIn);
	}
	@Deprecated
	public boolean setBlockAtCoord(Block b, World worldIn)
	{
		return worldIn.setBlock((int) posX, (int) posY, (int) posZ, b);
	}
	@Deprecated
	public static boolean setBlockAtCoord(EasyLocation loc, Block b, World worldIn)
	{
		return loc.setBlockAtCoord(b, worldIn);
	}
	
	@Override
	public String toString()
	{
		return String.format("posX: %s; posY: %s; posZ: %s", getCoordDouble());
	}
	
	@Override
	public EasyLocation clone()
	{
		return new EasyLocation(posX, posY, posZ);
	}
	
	@Override
	public int hashCode()
	{
		return (int) (dimID * posX * posY * posZ) * 7 * 31;
	}
	
	public Vec3 toVec3()
	{
		return Vec3.createVectorHelper(posY, posY, posZ);
	}
}
