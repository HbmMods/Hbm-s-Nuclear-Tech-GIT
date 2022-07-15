package com.hbm.calc;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.ILocationProvider;
import com.hbm.main.DeserializationException;

import api.hbm.serialization.SerializationRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
@Beta
public final class EasyLocationStep extends EasyLocation implements Iterable<ILocationProvider>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7616340419778639505L;
	@FunctionalInterface
	public interface ConcludeIteration
	{
		public boolean hasConcluded(EasyLocation start, EasyLocation current, Vec3 vector);
	}
	/**Origin coordinates**/
	private final EasyLocation origin;
	protected final Vec3 vector;
	protected transient ConcludeIteration conclude = (start, current, vec) -> {return current.posY > 255 || current.posY < 0 || distanceFromOrigin() >= 1000;};
	static
	{
		SerializationRegistry.register(EasyLocationStep.class, EasyLocationStep::fromBytes);
	}
	public EasyLocationStep(double x, double y, double z, Vec3 vector)
	{
		super(x, y, z);
		origin = clone();
		this.vector = vector;
	}

	public EasyLocationStep(double[] coord, Vec3 vector)
	{
		super(coord);
		origin = clone();
		this.vector = vector;
	}

	public EasyLocationStep(int[] coord, Vec3 vector)
	{
		super(coord);
		origin = clone();
		this.vector = vector;
	}

	public EasyLocationStep(Entity e, Vec3 vector)
	{
		super(e);
		origin = clone();
		this.vector = vector;
	}

	public EasyLocationStep(TileEntity te, Vec3 vector)
	{
		super(te);
		origin = clone();
		this.vector = vector;
	}

	public EasyLocationStep(Vec3 vector, Vec3 directionalVector)
	{
		super(vector);
		origin = clone();
		this.vector = directionalVector;
	}
	
	@Override
	public byte[] serialize()
	{
		final ByteBuf buf = alloc.get();
		buf.writeBytes(origin.serialize());
		buf.writeBytes(super.serialize());
		buf.writeDouble(vector.xCoord);
		buf.writeDouble(vector.yCoord);
		buf.writeDouble(vector.zCoord);
		return buf.array();
	}
	
	public static EasyLocationStep fromBytes(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = allocCopy.apply(bytes);
			final EasyLocationStep out = new EasyLocationStep(buf.readDouble(), buf.readDouble(), buf.readDouble(), Vec3.createVectorHelper(buf.readDouble(), buf.readDouble(), buf.readDouble()));
			out.posX = buf.readDouble();
			out.posY = buf.readDouble();
			out.posZ = buf.readDouble();
			return out;
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}
	
	@Override
	public EasyLocationStep deserialize(byte[] bytes) throws DeserializationException
	{
		return fromBytes(bytes);
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(origin, vector);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EasyLocationStep))
			return false;
		final EasyLocationStep other = (EasyLocationStep) obj;
		return Objects.equals(origin, other.origin) && Objects.equals(vector, other.vector);
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("EasyLocationStep [origin=").append(origin).append(", vector=").append(vector).append(", posX=")
				.append(posX).append(", posY=").append(posY).append(", posZ=").append(posZ).append(", dimID=")
				.append(dimID).append(", distanceFromOrigin()=").append(distanceFromOrigin()).append(", hasWorld()=")
				.append(hasWorld()).append(']');
		return builder.toString();
	}

	public EasyLocationStep setConclusionCondition(ConcludeIteration conclude)
	{
		this.conclude = conclude;
		return this;
	}
	
	public EasyLocationStep stepAhead()
	{
		modifyCoord(vector.xCoord, vector.yCoord, vector.zCoord);
		return this;
	}
	
	public EasyLocationStep stepBehind()
	{
		modifyCoord(-vector.xCoord, -vector.yCoord, -vector.zCoord);
		return this;
	}

	public double distanceFromOrigin()
	{
		return ILocationProvider.distance(this, origin);
	}
	
	public EasyLocation getOrigin()
	{
		return origin.clone();
	}
	
	public EasyLocation getCurrentPos()
	{
		return clone();
	}
	
	@Override
	public Iterator<ILocationProvider> iterator()
	{
		return new EasyLocIterator();
	}
	private class EasyLocIterator implements Iterator<ILocationProvider>
	{
		public EasyLocIterator()
		{
		}

		@Override
		public boolean hasNext()
		{
			return !conclude.hasConcluded(getOrigin(), getCurrentPos(), vector);
		}

		@Override
		public EasyLocationStep next()
		{
			if (conclude.hasConcluded(getOrigin(), getCurrentPos(), vector))
				throw new NoSuchElementException("Attempted to step when specified conclusion has been reached already.");
			else
				return stepAhead();
		}

	}

}
