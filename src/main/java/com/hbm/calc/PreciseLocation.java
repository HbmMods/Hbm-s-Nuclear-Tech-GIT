package com.hbm.calc;

import static java.math.MathContext.DECIMAL128;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.CheckForNull;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.ILocationProvider;
import com.hbm.main.DeserializationException;

import api.hbm.serialization.ISerializable;
import api.hbm.serialization.SerializationRegistry;
import ch.obermuhlner.math.big.BigDecimalMath;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
@Beta
public class PreciseLocation implements Cloneable, Comparable<PreciseLocation>, ISerializable<PreciseLocation>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -525812461028759983L;
	private static final PreciseLocation ZERO_LOCATION = new PreciseLocation(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	public final BigDecimal[] coord = new BigDecimal[3];
	public int dimID;
	public transient Optional<World> world = Optional.empty();
	static
	{
		SerializationRegistry.register(PreciseLocation.class, bytes -> new PreciseLocation(bytes));
	}
	public PreciseLocation(BigDecimal x, BigDecimal y, BigDecimal z)
	{
		coord[0] = x;
		coord[1] = y;
		coord[2] = z;
	}
	
	public PreciseLocation(BigDecimal[] coord)
	{
		this(coord[0], coord[1], coord[2]);
	}
	
	public PreciseLocation(double x, double y, double z)
	{
		coord[0] = BigDecimal.valueOf(x);
		coord[1] = BigDecimal.valueOf(y);
		coord[2] = BigDecimal.valueOf(z);
	}

	public PreciseLocation(long x, long y, long z)
	{
		coord[0] = BigDecimal.valueOf(x);
		coord[1] = BigDecimal.valueOf(y);
		coord[2] = BigDecimal.valueOf(z);
	}
	
	public PreciseLocation(double[] coord)
	{
		if (coord.length < 3)
			throw new IllegalArgumentException("Coordinate array length less than 3!");
		
		this.coord[0] = BigDecimal.valueOf(coord[0]);
		this.coord[1] = BigDecimal.valueOf(coord[1]);
		this.coord[2] = BigDecimal.valueOf(coord[2]);
	}
	
	public PreciseLocation(long[] coord)
	{
		if (coord.length < 3)
			throw new IllegalArgumentException("Coordinate array length less than 3!");
		
		this.coord[0] = BigDecimal.valueOf(coord[0]);
		this.coord[1] = BigDecimal.valueOf(coord[1]);
		this.coord[2] = BigDecimal.valueOf(coord[2]);
	}
	
	public PreciseLocation(Entity e)
	{
		coord[0] = BigDecimal.valueOf(e.posX);
		coord[1] = BigDecimal.valueOf(e.posY);
		coord[2] = BigDecimal.valueOf(e.posZ);
		world = Optional.of(e.worldObj);
		dimID = e.worldObj.provider.dimensionId;
	}
	
	public PreciseLocation(TileEntity te)
	{
		coord[0] = BigDecimal.valueOf(te.xCoord);
		coord[1] = BigDecimal.valueOf(te.yCoord);
		coord[2] = BigDecimal.valueOf(te.zCoord);
		world = Optional.of(te.getWorldObj());
		dimID = te.getWorldObj().provider.dimensionId;
	}
	
	public PreciseLocation(ILocationProvider loc)
	{
		this(loc.getCoordDouble());
		if (loc.hasWorld())
			setWorld(loc.getWorld());
	}
	
	public PreciseLocation(Vec3 vector)
	{
		this(vector.xCoord, vector.yCoord, vector.zCoord);
	}
	
	public PreciseLocation(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
			final BigDecimal[] readCoord = (BigDecimal[]) objectInputStream.readObject();
			for (byte i = 0; i < 3; i++)
				coord[i] = readCoord[i];
			dimID = objectInputStream.readInt();
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}
	
	@Override
	public byte[] serialize()
	{
		try
		{
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(coord);
			objectOutputStream.writeInt(dimID);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e)
		{
			e.printStackTrace();
			return new byte[0];
		}
	}
	
	public static BigDecimal distance(PreciseLocation loc1, PreciseLocation loc2)
	{
		return BigDecimalMath.sqrt(BigDecimalMath.pow(loc2.coord[0].subtract(loc1.coord[0]), 2, DECIMAL128).add(BigDecimalMath.pow(loc2.coord[1].subtract(loc1.coord[1]), 2, DECIMAL128), DECIMAL128).add(BigDecimalMath.pow(loc2.coord[2].subtract(loc1.coord[2]), 2, DECIMAL128), DECIMAL128), DECIMAL128);
	}
	
	public BigDecimal distance(PreciseLocation loc)
	{
		return distance(this, loc);
	}
	
	public static BigDecimal yaw(PreciseLocation loc1, PreciseLocation loc2)
	{
		return BigDecimalMath.atan2(loc1.coord[2].subtract(loc2.coord[2]), loc1.coord[0].subtract(loc2.coord[0]), DECIMAL128);
	}
	
	public BigDecimal yaw(PreciseLocation loc)
	{
		return yaw(this, loc);
	}
	
	public static BigDecimal pitch(PreciseLocation loc1, PreciseLocation loc2)
	{
		BigDecimal[] vec = makeVector(loc1, loc2);
		return BigDecimalMath.atan2(BigDecimalMath.sqrt(vec[2].multiply(vec[2]).add(vec[0].multiply(vec[0])), DECIMAL128), vec[1], DECIMAL128).add(BigDecimalMath.pi(DECIMAL128));
	}
	
	public BigDecimal pitch(PreciseLocation loc)
	{
		return pitch(this, loc);
	}
	
	public static BigDecimal[] makeVector(PreciseLocation loc1, PreciseLocation loc2)
	{
		BigDecimal[] vec = new BigDecimal[3];
		vec[0] = loc2.coord[0].subtract(loc1.coord[0], DECIMAL128);
		vec[1] = loc2.coord[1].subtract(loc1.coord[1], DECIMAL128);
		vec[2] = loc2.coord[2].subtract(loc1.coord[2], DECIMAL128);
		return vec;
	}
	
	public PreciseLocation modifyCoord(double x, double y, double z)
	{
		coord[0].add(BigDecimal.valueOf(x));
		coord[1].add(BigDecimal.valueOf(y));
		coord[2].add(BigDecimal.valueOf(z));
		return this;
	}
	
	public PreciseLocation modifyCoord(BigDecimal x, BigDecimal y, BigDecimal z)
	{
		coord[0].add(x);
		coord[1].add(y);
		coord[2].add(z);
		return this;
	}
	/**Preserves both original PreciseLocation instances**/
	public static PreciseLocation addCoord(PreciseLocation loc1, PreciseLocation loc2)
	{
		return loc1.clone().addCoord(loc2);
	}
	
	public PreciseLocation addCoord(PreciseLocation loc)
	{
		coord[0].add(loc.coord[0]);
		coord[1].add(loc.coord[1]);
		coord[2].add(loc.coord[2]);
		return this;
	}
	
	/**Preserves both original PreciseLocation instances**/
	public static PreciseLocation subtractCoord(PreciseLocation loc1, PreciseLocation loc2)
	{
		return loc1.clone().subtractCoord(loc2);
	}
	
	public PreciseLocation subtractCoord(PreciseLocation loc)
	{
		coord[0].subtract(loc.coord[0]);
		coord[1].subtract(loc.coord[1]);
		coord[2].subtract(loc.coord[2]);
		return this;
	}
	
	public PreciseLocation setWorld(World world)
	{
		this.world = Optional.of(world);
		return this;
	}
	
	public BigDecimal getX()
	{
		return coord[0];
	}
	
	public BigDecimal getY()
	{
		return coord[1];
	}
	
	public BigDecimal getZ()
	{
		return coord[2];
	}
	
	public BigDecimal[] getCoordBigDecimal()
	{
		return coord.clone();
	}
	
	public BigInteger[] getCoordBigInteger()
	{
		BigInteger[] coordOut = new BigInteger[3];
		coordOut[0] = coord[0].toBigInteger();
		coordOut[1] = coord[1].toBigInteger();
		coordOut[2] = coord[2].toBigInteger();
		return coordOut;
	}
	
	public double[] getCoordDouble()
	{
		double[] coordOut = new double[3];
		coordOut[0] = coord[0].doubleValue();
		coordOut[1] = coord[1].doubleValue();
		coordOut[2] = coord[2].doubleValue();
		return coordOut;
	}
	
	public long[] getCoordLong()
	{
		long[] coordOut = new long[3];
		coordOut[0] = coord[0].longValue();
		coordOut[1] = coord[1].longValue();
		coordOut[2] = coord[2].longValue();
		return coordOut;
	}
	
	@CheckForNull
	public static PreciseLocation constructGeneric(Object obj)
	{
		if (obj instanceof PreciseLocation)
			return (PreciseLocation) obj;
		else if (obj instanceof EasyLocation)
			return new PreciseLocation((EasyLocation) obj);
		else if (obj instanceof long[])
			return new PreciseLocation((long[]) obj);
		else if (obj instanceof double[])
			return new PreciseLocation((double[]) obj);
		else if (obj instanceof Vec3)
			return new PreciseLocation((Vec3) obj);
		else if (obj instanceof TileEntity)
			return new PreciseLocation((TileEntity) obj);
		else if (obj instanceof Entity)
			return new PreciseLocation((Entity) obj);
		else if (obj instanceof BigDecimal[])
			return new PreciseLocation((BigDecimal[]) obj);
		else
			return null;

	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof PreciseLocation))
			return false;
		final PreciseLocation other = (PreciseLocation) obj;
		return Arrays.equals(coord, other.coord) && dimID == other.dimID;
	}
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("PreciseLocation [coord=").append(Arrays.toString(coord)).append(", dimID=").append(dimID)
				.append(", world=").append(world).append(']');
		return builder.toString();
	}
	
	@Override
	public PreciseLocation clone()
	{
		try
		{
			return (PreciseLocation) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			PreciseLocation loc = new PreciseLocation(getCoordBigDecimal());
			if (world.isPresent())
				loc.setWorld(world.get());
			return loc;
		}
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coord);
		result = prime * result + Objects.hash(dimID);
		return result;
	}

	@Override
	public int compareTo(PreciseLocation o)
	{
		if (equals(o))
			return 0;
		final BigDecimal testDist = distance(o, ZERO_LOCATION);
		final BigDecimal myDist = distance(this, ZERO_LOCATION);
		return myDist.compareTo(testDist);
	}

	@Override
	public PreciseLocation deserialize(byte[] bytes) throws DeserializationException
	{
		return new PreciseLocation(bytes);
	}
}
