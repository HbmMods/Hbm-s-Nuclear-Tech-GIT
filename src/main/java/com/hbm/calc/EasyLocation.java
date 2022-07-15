package com.hbm.calc;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.CheckForNull;

import com.hbm.interfaces.ILocationProvider;
import com.hbm.main.DeserializationException;

import api.hbm.serialization.ISerializable;
import api.hbm.serialization.SerializationRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EasyLocation implements Cloneable, Comparable<EasyLocation>, ISerializable<EasyLocation>, ILocationProvider
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1791762616348589379L;
	private static final EasyLocation ZERO_LOCATION = new EasyLocation(0, 0, 0);
	public double posX, posY, posZ;
	public int dimID;
	public transient Optional<World> world = Optional.empty();
	static
	{
		SerializationRegistry.register(EasyLocation.class, EasyLocation::new);
	}
	public EasyLocation(ILocationProvider locationProvider)
	{
		posX = locationProvider.getX();
		posY = locationProvider.getY();
		posZ = locationProvider.getZ();
		if (locationProvider.hasWorld())
			world = Optional.of(locationProvider.getWorld());
	}
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
		world = Optional.of(e.worldObj);
		dimID = e.worldObj.provider.dimensionId;
	}
	
	public EasyLocation(TileEntity te)
	{
		posX = te.xCoord;
		posY = te.yCoord;
		posZ = te.zCoord;
		world = Optional.of(te.getWorldObj());
		dimID = te.getWorldObj().provider.dimensionId;
	}
	
	public EasyLocation(Vec3 vector)
	{
		this(vector.xCoord, vector.yCoord, vector.zCoord);
	}
	
	public EasyLocation(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			posX = buf.readDouble();
			posY = buf.readDouble();
			posZ = buf.readDouble();
			dimID = buf.readInt();
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}

	@Override
	public byte[] serialize()
	{
		final ByteBuf buf = alloc.get();
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
		buf.writeInt(dimID);
		return buf.array();
	}
	
	public EasyLocation round()
	{
		posX = Math.round(posX);
		posY = Math.round(posY);
		posZ = Math.round(posZ);
		return this;
	}
	
	@CheckForNull
	public static EasyLocation constructGeneric(Object obj)
	{
		if (obj instanceof EasyLocation)
			return (EasyLocation) obj;
		else if (obj instanceof int[])
			return new EasyLocation((int[]) obj);
		else if (obj instanceof double[])
			return new EasyLocation((double[]) obj);
		else if (obj instanceof Vec3)
			return new EasyLocation((Vec3) obj);
		else if (obj instanceof TileEntity)
			return new EasyLocation((TileEntity) obj);
		else if (obj instanceof Entity)
			return new EasyLocation((Entity) obj);
		else
			return null;

	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof EasyLocation))
			return false;
		final EasyLocation other = (EasyLocation) obj;
		return dimID == other.dimID && Double.doubleToLongBits(posX) == Double.doubleToLongBits(other.posX)
				&& Double.doubleToLongBits(posY) == Double.doubleToLongBits(other.posY)
				&& Double.doubleToLongBits(posZ) == Double.doubleToLongBits(other.posZ);
	}
	/** Also sets the dimID **/
	public EasyLocation setWorld(World worldIn)
	{
		world = Optional.of(worldIn);
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
	/**Preserves both original EasyLocation instances**/
	public static EasyLocation addCoord(EasyLocation loc1, EasyLocation loc2)
	{
		return loc1.clone().addCoord(loc2);
	}
	
	public EasyLocation addCoord(EasyLocation loc)
	{
		posX += loc.posX;
		posY += loc.posY;
		posZ += loc.posZ;
		return this;
	}
	
	public EasyLocation addCoord(Vec3 vec)
	{
		posX += vec.xCoord;
		posY += vec.yCoord;
		posZ += vec.zCoord;
		return this;
	}
	
	public EasyLocation subtractCoord(Vec3 vec)
	{
		posX -= vec.xCoord;
		posY -= vec.yCoord;
		posZ -= vec.zCoord;
		return this;
	}
	
	/**Preserves both original EasyLocation instances**/
	public static EasyLocation subtractCoord(EasyLocation loc1, EasyLocation loc2)
	{
		return loc1.clone().subtractCoord(loc2);
	}
	
	public EasyLocation subtractCoord(EasyLocation loc)
	{
		posX -= loc.posX;
		posY -= loc.posY;
		posZ -= loc.posZ;
		return this;
	}

	/** <b><i>Must</i></b> have world set!
	 * @deprecated No point in existing since the entity itself needs the coordinates.
	 * @param e The entity to spawn
	 * @return Whatever spawnEntityInWorld() returns
	 * @throws NoSuchElementException If the world is not set**/
	@Deprecated
	public boolean spawnEntity(Entity e)
	{
		return world.get().spawnEntityInWorld(e);
	}
	
	@Override
	public EasyLocation clone()
	{
		try
		{
			return ((EasyLocation) super.clone());
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			final EasyLocation cloned = new EasyLocation(getCoordDouble());
			if (world.isPresent())
				cloned.setWorld(world.get());
			return cloned;
		}
	}
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("EasyLocation [posX=").append(posX).append(", posY=").append(posY).append(", posZ=").append(posZ)
				.append(", dimID=").append(dimID).append(", hasWorld()=").append(hasWorld()).append(']');
		return builder.toString();
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(dimID, posX, posY, posZ);
	}
	
	public Vec3 toVec3()
	{
		return Vec3.createVectorHelper(posX, posY, posZ);
	}

	@Override
	public int compareTo(EasyLocation o)
	{
		if (equals(o))
			return 0;
		final double testDist = ILocationProvider.distance(o, ZERO_LOCATION);
		final double myDist = ILocationProvider.distance(this, ZERO_LOCATION);
		if (testDist == myDist)
			return 0;
		return myDist < testDist ? 1 : -1;
	}

	@Override
	public EasyLocation deserialize(byte[] bytes) throws DeserializationException
	{
		return new EasyLocation(bytes);
	}

	@Override
	public double getX()
	{
		return posX;
	}

	@Override
	public double getY()
	{
		return posY;
	}

	@Override
	public double getZ()
	{
		return posZ;
	}

	@Override
	public boolean hasWorld()
	{
		return world.isPresent();
	}

	@Override
	public World getWorld()
	{
		return world.get();
	}
}
