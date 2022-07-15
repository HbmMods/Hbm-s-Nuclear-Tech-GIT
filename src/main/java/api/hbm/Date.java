package api.hbm;

import java.math.BigInteger;
import java.util.Arrays;

import com.hbm.lib.HbmCollection;

import api.hbm.serialization.ISerializable;
import api.hbm.serialization.SerializationRegistry;

public class Date implements Comparable<Date>, Cloneable, ISerializable<Date>
{
	static
	{
		SerializationRegistry.register(Date.class, Date::new);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -6699718132345062075L;
	protected static final BigInteger tickDay = BigInteger.valueOf(HbmCollection.tickDay), tickYear = BigInteger.valueOf(HbmCollection.tickYear), hundred = BigInteger.valueOf(100);
	protected BigInteger data;
	public Date()
	{
		data = BigInteger.ZERO;
	}
	
	public Date(long year, int day, int time)
	{
		data = BigInteger.valueOf(time);
		data = data.add(BigInteger.valueOf(day).multiply(tickDay));
		data = data.add(BigInteger.valueOf(year).multiply(tickYear));
	}
	
	public Date(byte[] bytes)
	{
		data = new BigInteger(bytes);
	}
	
	public Date(BigInteger integer)
	{
		data = integer;
	}
	
	public Date(long l)
	{
		data = BigInteger.valueOf(l);
	}
	
	public Date add(Date d)
	{
		data = data.add(d.data);
		return this;
	}
	
	public Date subtract(Date d)
	{
		data = data.subtract(d.data);
		return this;
	}
	
	public Date increment()
	{
		data = data.add(BigInteger.ONE);
		return this;
	}

	public BigInteger getData()
	{
		return data;
	}
	
	public Number[] export()
	{
		Number[] exp = new Number[4];
		
		BigInteger calcDays = data.divide(tickDay);
		
		if (calcDays.compareTo(hundred) == 1)
		{
			BigInteger calcYears = calcDays.divide(hundred);
			exp[0] = calcYears.longValueExact();
			calcDays = calcDays.subtract(calcYears.multiply(hundred));
			exp[2] = calcDays.byteValueExact();
			exp[3] = data.subtract(calcYears.multiply(tickYear)).subtract(calcDays.multiply(tickDay));
		}
		else
		{
			exp[2] = calcDays.byteValue();
			exp[3] = data.subtract(calcDays.multiply(hundred));
		}
		exp[1] = (byte) Math.ceil(calcDays.floatValue() / 10);
		
		return exp;
	}
	
	public long getYear()
	{
		return export()[0].longValue();
	}
	
	public byte getMonth()
	{
		return export()[1].byteValue();
	}
	
	public byte getDay()
	{
		return export()[2].byteValue();
	}
	
	public int getTime()
	{
		return export()[3].intValue();
	}
	
	@Override
	public Date clone()
	{
		try
		{
			return (Date) super.clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return new Date(data);
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || !(obj instanceof Date))
			return false;
		else if (this == obj)
			return true;
		else
		{
			Date test = (Date) obj;
			
			return data.equals(test.data);
		}
	}
	
	@Override
	public int hashCode()
	{
		return data.hashCode();
	}
	
	@Override
	public String toString()
	{
		return Arrays.toString(export()) + ';' + data.toString();
	}
	
	@Override
	public int compareTo(Date arg0)
	{
		return data.compareTo(arg0.data);
	}

	@Override
	public byte[] serialize()
	{
		return data.toByteArray();
	}

	@Override
	public Date deserialize(byte[] bytes)
	{
		return new Date(new BigInteger(bytes));
	}

}
