package com.hbm.tileentity.machine.rbmk;

import com.hbm.interfaces.IControlReceiver;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityRBMKControl extends TileEntityRBMKSlottedBase implements IControlReceiver {

	public double level;
	public static final double speed = 0.00277D; // it takes around 18 seconds for the thing to fully extend
	public double targetLevel;
	public RBMKColor color;

	public TileEntityRBMKControl() {
		super(0);
	}

	@Override
	public String getName() {
		return "container.rbmkControl";
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(level < targetLevel) {
				
				level += speed;
				
				if(level > targetLevel)
					level = targetLevel;
			}
			
			if(level > targetLevel) {
				
				level -= speed;
				
				if(level < targetLevel)
					level = targetLevel;
			}
		}
		
		super.updateEntity();
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

		this.level = nbt.getDouble("level");
		this.targetLevel = nbt.getDouble("targetLevel");
		
		if(nbt.hasKey("color"))
			this.color = RBMKColor.values()[nbt.getInteger("color")];
		else
			this.color = null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("level", this.level);
		nbt.setDouble("targetLevel", this.targetLevel);
		
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
}
