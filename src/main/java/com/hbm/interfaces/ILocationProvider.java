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
	public double posX();
	public double posY();
	public double posZ();
	public boolean hasWorld();
	public World getWorld();
	public default double[] getCoordDouble()
	{
		return new double[] {posX(), posY(), posZ()};
	}
	public default int[] getCoordInt()
	{
		return new int[] {(int) posX(), (int) posY(), (int) posZ()};
	}
	/**
	 * Checks if the coordinates are invalid.
	 * @return If any of the coordinates are {@code NaN}.
	 */
	public default boolean isInvalid()
	{
		return Double.isNaN(posX()) || Double.isNaN(posY()) || Double.isNaN(posZ());
	}
	/** <b><i>Has prerequisite of having the world object set</b></i>
	 * @throws NoSuchElementException If the world is not set and is backed by an Optional
	 * @throws NullPointerException If the world is not set and is not backed by an Optional**/
	public default Block getBlock()
	{
		return getWorld().getBlock((int) posX(), (int) posY(), (int) posZ());
	}
	/** <b><i>Has prerequisite of having the world object set</b></i>
	 * @throws NoSuchElementException If the world is not set and is backed by an Optional
	 * @throws NullPointerException If the world is not set and is not backed by an Optional**/
	public default TileEntity getTE()
	{
		return getWorld().getTileEntity((int) posX(), (int) posY(), (int) posZ());
	}
	
	/** <b><i>Has prerequisite of having the world object set</b></i>
	 * @param b The block in question
	 * @return Whatever the {@link#World.setBlock()} method is supposed to return
	 * @throws NoSuchElementException If the world is not set and is backed by an Optional
	 * @throws NullPointerException If the world is not set and is not backed by an Optional**/
	public default boolean setBlock(Block block)
	{
		return getWorld().setBlock((int) posX(), (int) posY(), (int) posZ(), block);
	}
	public static Vec3 makeVector(@Nonnull ILocationProvider loc1, @Nonnull ILocationProvider loc2)
	{
		return Vec3.createVectorHelper(loc2.posX() - loc1.posX(), loc2.posY() - loc1.posY(), loc2.posZ() - loc2.posZ());
	}
	
	public static double distance(@Nonnull ILocationProvider loc1, @Nonnull ILocationProvider loc2)
	{
		final Vec3 vec = makeVector(loc1, loc2);
		return Math.sqrt((vec.xCoord * vec.xCoord) + (vec.yCoord * vec.yCoord) + (vec.zCoord + vec.zCoord));
	}
	
	public static double yaw(@Nonnull ILocationProvider loc1, @Nonnull ILocationProvider loc2)
	{
		return Math.atan2(loc1.posZ() - loc2.posZ(), loc1.posX() - loc2.posX());
	}
	
	public static double pitch(@Nonnull ILocationProvider loc1, @Nonnull ILocationProvider loc2)
	{
		final Vec3 vec = makeVector(loc1, loc2);
		return Math.atan2(Math.sqrt(vec.zCoord * vec.zCoord + vec.xCoord * vec.xCoord), vec.yCoord) + Math.PI;
	}
	
	public static ILocationProvider wrap(Entity entity, boolean adjustForHeight)
	{
		return new DelegatedEntityLocation(entity, adjustForHeight);
	}
	
	public static ILocationProvider wrap(TileEntity tileEntity)
	{
		return new DelegatedTileEntityLocation(tileEntity);
	}
	
	static class DelegatedEntityLocation implements ILocationProvider
	{
		private final Entity entity;
		private final boolean adjustForHeight;
		DelegatedEntityLocation(Entity entity, boolean adjustForHeight)
		{
			this.entity = entity;
			this.adjustForHeight = adjustForHeight;
		}
		
		@Override
		public double posX()
		{
			return entity.posX;
		}
		@Override
		public double posY()
		{
			return adjustForHeight ? entity.posY + entity.getEyeHeight() : entity.posY;
		}
		@Override
		public double posZ()
		{
			return entity.posZ;
		}
		@Override
		public boolean hasWorld()
		{
			return true;
		}
		@Override
		public World getWorld()
		{
			return entity.worldObj;
		}
	}
	
	static class DelegatedTileEntityLocation implements ILocationProvider
	{
		private final TileEntity tileEntity;
		DelegatedTileEntityLocation(TileEntity tileEntity)
		{
			this.tileEntity = tileEntity;
		}
		
		@Override
		public double posX()
		{
			return tileEntity.xCoord;
		}
		@Override
		public double posY()
		{
			return tileEntity.yCoord;
		}
		@Override
		public double posZ()
		{
			return tileEntity.zCoord;
		}
		@Override
		public boolean hasWorld()
		{
			return tileEntity.hasWorldObj();
		}
		@Override
		public World getWorld()
		{
			return tileEntity.getWorldObj();
		}
		
	}
}
