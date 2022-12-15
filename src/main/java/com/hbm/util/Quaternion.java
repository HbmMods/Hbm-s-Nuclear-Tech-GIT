package com.hbm.util;

import java.io.Serializable;
import java.util.Objects;

import com.hbm.interfaces.IByteSerializable;
import com.hbm.interfaces.INBTSerializable;
import com.hbm.main.DeserializationException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

/**
 * Credits to Apache for the basic structure.
 * @author UFFR
 *
 */
public class Quaternion implements Serializable, Cloneable, IByteSerializable, INBTSerializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -403051011515625947L;
	private double w, x, y, z;

	public Quaternion(double pitch, double yaw)
	{
		final double rPitch = Math.toRadians(pitch),
					 rYaw = Math.toRadians(yaw),
					 
					 cp = Math.cos(rPitch * 0.5),
					 sp = Math.sin(rPitch * 0.5),
					 cy = Math.cos(rYaw * 0.5),
					 sy = Math.sin(rYaw * 0.5);
		
		w = cy * sp;
		x = sy * cp;
		y = sy * cp;
		z = cy * cp;
	}
	
	public Quaternion(double roll, double pitch, double yaw)
	{
		final double rRoll = Math.toRadians(roll),
					 rPitch = Math.toRadians(pitch),
					 rYaw = Math.toRadians(yaw),
					 
					 cr = Math.cos(rRoll * 0.5),
					 sr = Math.sin(rRoll * 0.5),
					 cp = Math.cos(rPitch * 0.5),
					 sp = Math.sin(rPitch * 0.5),
					 cy = Math.cos(rYaw * 0.5),
					 sy = Math.sin(rYaw * 0.5);
		
		w = cr * cp * cy + sr * sp * sy;
		x = sr * cp * cy - cr * sp * sy;
		y = cr * sp * cy + sr * cp * sy;
		z = cr * cp * sy - sr * sp * cy;

	}
	
	public Quaternion(double w, double x, double y, double z)
	{
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Quaternion(double w, Vec3 vec3)
	{
		this.w = w;
		
		x = vec3.xCoord;
		y = vec3.yCoord;
		z = vec3.zCoord;
	}
	
	public Quaternion(Vec3 vec3)
	{
		this(0, vec3);
	}
	
	public Quaternion(double w, double[] vector)
	{
		if (vector.length != 3)
			throw new IllegalArgumentException("Vector argument must only have 3 values!");
		
		this.w = w;
		
		x = vector[0];
		y = vector[1];
		z = vector[2];
	}
	
	public Quaternion(double[] vector)
	{
		this(0, vector);
	}
	
	public Quaternion getConjugate()
	{
		return new Quaternion(w, -x, -y, -z);
	}
	
	public Quaternion add(Quaternion q)
	{
		return add(this, q);
	}
	
	public Quaternion subtract(Quaternion q)
	{
		return subtract(this, q);
	}
	
	public Quaternion multiply(Quaternion q)
	{
		return multiply(this, q);
	}
	
	public Quaternion dotProduct(Quaternion q)
	{
		return dotProduct(this, q);
	}
	
	public double getNorm()
	{
		return Math.sqrt(w * w + x * x + y * y + z * z);
	}
	
	public Quaternion normalize()
	{
		final double norm = getNorm();
		if (norm < Double.MIN_NORMAL)
			throw new ArithmeticException("Quaternion norm zero!");
		
		return new Quaternion(
				w / norm,
				x / norm,
				y / norm,
				z / norm);
	}
	
	public double getPitch()
	{
		return Math.atan2(2 * (y * z + w * x), w * w - x * x - y * y + z * z);
	}
	
	public double getYaw()
	{
		return Math.asin(-2 * (x * z - w * y));
	}
	
	public double getRoll()
	{
		return Math.atan2(2 * (x * y + w * z), w * w + x * x - y * y - z * z);
	}
	
	public static Quaternion add(Quaternion q1, Quaternion q2)
	{
		return new Quaternion(q1.w + q2.w, q1.x + q2.x, q1.y + q2.y, q1.z + q2.z);
	}
	
	public static Quaternion subtract(Quaternion q1, Quaternion q2)
	{
		return new Quaternion(q1.w - q2.w, q1.x - q2.x, q1.y - q2.y, q1.z - q2.z);
	}
	
	public static Quaternion multiply(Quaternion q1, Quaternion q2)
	{
		return new Quaternion(
				q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z,
		        q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y,
		        q1.w * q2.y - q1.x * q2.z + q1.y * q2.w + q1.z * q2.x,
		        q1.w * q2.z + q1.x * q2.y - q1.y * q2.x + q1.z * q2.w
				);
	}
	
	public static Quaternion dotProduct(Quaternion q1, Quaternion q2)
	{
		return new Quaternion(q1.w * q2.w, q1.x * q2.x, q1.y * q2.y, q1.z * q2.z);
	}
	
	public static Vec3 rotate(Quaternion q, Vec3 vec3)
	{
		return q.multiply(new Quaternion(vec3)).multiply(q.getConjugate()).getVector();
	}
	
	public static Vec3 rotate(Quaternion q, Vec3 vec3, Vec3 origin)
	{
		return q.multiply(new Quaternion(vec3.subtract(origin))).multiply(q.getConjugate()).getVector().addVector(origin.xCoord, origin.yCoord, origin.zCoord);
	}
	
	public double getW()
	{
		return w;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}

	public Vec3 getVector()
	{
		return Vec3.createVectorHelper(x, y, z);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setDouble("w", w);
		nbt.setDouble("x", x);
		nbt.setDouble("y", y);
		nbt.setDouble("z", z);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		w = nbt.getDouble("w");
		x = nbt.getDouble("x");
		y = nbt.getDouble("y");
		z = nbt.getDouble("z");
	}

	@Override
	public void writeToBytes(ByteBuf buf)
	{
		buf.writeDouble(w).writeDouble(x).writeDouble(y).writeDouble(z);
	}

	@Override
	public void readFromBytes(byte[] bytes) throws DeserializationException
	{
		try
		{
			final ByteBuf buf = Unpooled.copiedBuffer(bytes);
			w = buf.readDouble();
			x = buf.readDouble();
			y = buf.readDouble();
			z = buf.readDouble();
		} catch (Exception e)
		{
			throw new DeserializationException(e);
		}
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(w, x, y, z);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof Quaternion))
			return false;
		final Quaternion other = (Quaternion) obj;
		return Double.doubleToLongBits(w) == Double.doubleToLongBits(other.w)
				&& Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
				&& Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y)
				&& Double.doubleToLongBits(z) == Double.doubleToLongBits(other.z);
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("Quaternion [w=").append(w).append(", x=").append(x).append(", y=").append(y).append(", z=")
				.append(z).append(']');
		return builder.toString();
	}
	
	@Override
	public Quaternion clone()
	{
		try
		{
			return (Quaternion) super.clone();
		} catch (CloneNotSupportedException e)
		{
			return new Quaternion(w, x, y, z);
		}
	}

}
