package com.hbm.tileentity.machine.rbmk;

import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public abstract class TileEntityRBMKControl extends TileEntityRBMKSlottedBase implements SimpleComponent {

	@SideOnly(Side.CLIENT)
	public double lastLevel;
	public double level;
	public static final double speed = 0.00277D; // it takes around 18 seconds for the thing to fully extend
	public double targetLevel;

	public TileEntityRBMKControl() {
		super(0);
	}
	
	@Override
	public boolean isLidRemovable() {
		return false;
	}
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			
			this.lastLevel = this.level;
		
		} else {
			
			if(level < targetLevel) {
				
				level += speed * RBMKDials.getControlSpeed(worldObj);
				
				if(level > targetLevel)
					level = targetLevel;
			}
			
			if(level > targetLevel) {
				
				level -= speed * RBMKDials.getControlSpeed(worldObj);
				
				if(level < targetLevel)
					level = targetLevel;
			}
		}
		
		super.updateEntity();
	}
	
	public void setTarget(double target) {
		this.targetLevel = target;
	}
	
	public double getMult() {
		return this.level;
	}
	
	@Override
	public int trackingRange() {
		return 150;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.level = nbt.getDouble("level");
		this.targetLevel = nbt.getDouble("targetLevel");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("level", this.level);
		nbt.setDouble("targetLevel", this.targetLevel);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public void onMelt(int reduce) {
		
		if(this.isModerated()) {
			
			int count = 2 + worldObj.rand.nextInt(2);
			
			for(int i = 0; i < count; i++) {
				spawnDebris(DebrisType.GRAPHITE);
			}
		}
		
		int count = 2 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.ROD);
		}
		
		this.standardMelt(reduce);
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		data.setDouble("level", this.level);
		return data;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "rbmk_control_rod";
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getLevel(Context context, Arguments args) {
		return new Object[] {getMult() * 100};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTargetLevel(Context context, Arguments args) {
		return new Object[] {targetLevel * 100};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {heat, getMult() * 100, targetLevel * 100, xCoord, yCoord, zCoord};
	}

	@Callback(direct = true, limit = 16)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setLevel(Context context, Arguments args) {
		double newLevel = args.checkDouble(0)/100.0;
		if (newLevel > 1.0) {
			newLevel = 1.0;
		} else if (newLevel < 0.0) {
			newLevel = 0.0;
		}	
		targetLevel = newLevel;
		return new Object[] {};
	}
}
