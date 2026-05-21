package com.hbm.tileentity.network;

import java.util.HashMap;

import com.hbm.tileentity.TileEntityTickingBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityRadioAUTOCAL extends TileEntityTickingBase {
	
	public boolean isOn = false;
	
	public Object buffer;
	public NBTTagCompound variables;
	public String[] script;
	public HashMap<String, Integer> jmp = new HashMap();

	@Override
	public String getInventoryName() {
		return "container.autocal";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
	}
}
