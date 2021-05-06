package com.hbm.tileentity.machine.rbmk;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityRBMKControlManual extends TileEntityRBMKControl implements IControlReceiver {

	public RBMKColor color;

	@Override
	public String getName() {
		return "container.rbmkControl";
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("level")) {
			this.targetLevel = data.getDouble("level");
		}
		
		if(data.hasKey("color")) {
			int c = Math.abs(data.getInteger("color")) % RBMKColor.values().length; //to stop naughty kids from sending packets that crash the server
			
			RBMKColor newCol = RBMKColor.values()[c];
			
			if(newCol == this.color) {
				this.color = null;
			} else {
				this.color = newCol;
			}
		}
		
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		if(nbt.hasKey("color"))
			this.color = RBMKColor.values()[nbt.getInteger("color")];
		else
			this.color = null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		if(color != null)
			nbt.setInteger("color", color.ordinal());
	}
	
	public static enum RBMKColor {
		RED,
		YELLOW,
		GREEN,
		BLUE,
		PURPLE
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.CONTROL;
	}
}
