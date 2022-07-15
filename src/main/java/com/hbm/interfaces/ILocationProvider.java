package com.hbm.interfaces;

import java.util.NoSuchElementException;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface ILocationProvider
{
	public double getX();
	public double getY();
	public double getZ();
	public boolean hasWorld();
	public World getWorld();
	public default double[] getCoordDouble()
	{
		return new double[] {getX(), getY(), getZ()};
	}
	public default int[] getCoordInt()
	{
		return new int[] {(int) getX(), (int) getY(), (int) getZ()};
	}
	/**
	 * Checks if the coordinates are invalid.
	 * @return If any of the coordinates are {@code NaN}.
	 */
	public default boolean isInvalid()
	{
		return Double.isNaN(getX()) || Double.isNaN(getY()) || Double.isNaN(getZ());
	}
	/** <b><i>Has prerequisite of having the world object set</b></i>
	 * @throws NoSuchElementException If the world is not set and is backed by an Optional
	 * @throws NullPointerException If the world is not set and is not backed by an Optional**/
	public default Block getBlock()
	{
		return getWorld().getBlock((int) getX(), (int) getY(), (int) getZ());
	}
	/** <b><i>Has prerequisite of having the world object set</b></i>
	 * @throws NoSuchElementException If the world is not set and is backed by an Optional
	 * @throws NullPointerException If the world is not set and is not backed by an Optional**/
	public default TileEntity getTE()
	{
		return getWorld().getTileEntity((int) getX(), (int) getY(), (int) getZ());
	}
	
	/** <b><i>Has prerequisite of having the world object set</b></i>
	 * @param b The block in question
	 * @return Whatever the {@link#World.setBlock()} method is supposed to return
	 * @throws NoSuchElementException If the world is not set and is backed by an Optional
	 * @throws NullPointerException If the world is not set and is not backed by an Optional**/
	public default boolean setBlock(Block block)
	{
		return getWorld().setBlock((int) getX(), (int) getY(), (int) getZ(), block);
	}
	public static Vec3 makeVector(@Nonnull ILocationProvider loc1, @Nonnull ILocationProvider loc2)
	{
		return Vec3.createVectorHelper(loc2.getX() - loc1.getX(), loc2.getY() - loc1.getY(), loc2.getZ() - loc2.getZ());
	}
	
	public static double distance(@Nonnull ILocationProvider loc1, @Nonnull ILocationProvider loc2)
	{
		final Vec3 vec = makeVector(loc1, loc2);
		return Math.sqrt((vec.xCoord * vec.xCoord) + (vec.yCoord * vec.yCoord) + (vec.zCoord + vec.zCoord));
	}
	
	public static double yaw(@Nonnull ILocationProvider loc1, @Nonnull ILocationProvider loc2)
	{
		return Math.atan2(loc1.getZ() - loc2.getZ(), loc1.getX() - loc2.getX());
	}
	
	public static double pitch(@Nonnull ILocationProvider loc1, @Nonnull ILocationProvider loc2)
	{
		final Vec3 vec = makeVector(loc1, loc2);
		return Math.atan2(Math.sqrt(vec.zCoord * vec.zCoord + vec.xCoord * vec.xCoord), vec.yCoord) + Math.PI;
	}
	
	public static ILocationProvider wrap(Entity entity, boolean adjustForHeight)
	{
		return new ILocationProvider()
		{
			@Override public boolean hasWorld() {return true;}
			@Override public double getZ() {return entity.posZ;}
			@Override public double getY() {return entity.posY + (adjustForHeight ? entity.getEyeHeight() : 0);}
			@Override public double getX() {return entity.posX;}
			@Override public World getWorld() {return entity.worldObj;}
		};
	}
	
	public static ILocationProvider wrap(TileEntity tileEntity)
	{
		return new ILocationProvider()
		{
			@Override public boolean hasWorld() {return tileEntity.hasWorldObj();}
			@Override public double getZ() {return tileEntity.zCoord;}
			@Override public double getY() {return tileEntity.yCoord;}
			@Override public double getX() {return tileEntity.xCoord;}
			@Override public World getWorld() {return tileEntity.getWorldObj();}
		};
	}
}
