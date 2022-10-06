package com.hbm.wiaj.actors;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Very basic actor base class that holds NBT, not very useful on its own
 * @author hbm
 */
public abstract class ActorBase implements ISpecialActor {
	
	protected NBTTagCompound data = new NBTTagCompound();

	@Override
	public void setActorData(NBTTagCompound data) {
		this.data = data;
	}

	@Override
	public void setDataPoint(String tag, Object o) {
		if(o instanceof String) this.data.setString(tag, (String) o);
		if(o instanceof Integer) this.data.setInteger(tag, (Integer) o);
		if(o instanceof Float) this.data.setFloat(tag, (Float) o);
		if(o instanceof Double) this.data.setDouble(tag, (Double) o);
		if(o instanceof Boolean) this.data.setBoolean(tag, (Boolean) o);
		if(o instanceof Byte) this.data.setByte(tag, (Byte) o);
		if(o instanceof Short) this.data.setShort(tag, (Short) o);
	}
}
