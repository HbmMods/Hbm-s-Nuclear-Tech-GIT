package com.hbm.tileentity.machine;

import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.CompatEnergyControl;

import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

@Deprecated
@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityReactorResearch extends TileEntityMachineBase implements IControlReceiver, SimpleComponent, IInfoProviderEC, CompatHandler.OCComponent {
	
	@SideOnly(Side.CLIENT)
	public double lastLevel;
	public double level;
	public double speed = 0.04;
	public double targetLevel;
	
	public int heat;
	public byte water;
	public final int maxHeat = 50000;
	public int[] slotFlux = new int[12];
	public int totalFlux = 0;
	
	private static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	public TileEntityReactorResearch() {
		super(12);
	}
	
	public String getName() {
		return "container.reactorResearch";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		heat = nbt.getInteger("heat");
		water = nbt.getByte("water");
		level = nbt.getDouble("level");
		targetLevel = nbt.getDouble("targetLevel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("heat", heat);
		nbt.setByte("water", water);
		nbt.setDouble("level", level);
		nbt.setDouble("targetLevel", targetLevel);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_io;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.networkPackNT(150);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.heat);
		buf.writeByte(this.water);
		buf.writeDouble(this.level);
		buf.writeDouble(this.targetLevel);
		BufferUtil.writeIntArray(buf, this.slotFlux);
		buf.writeInt(this.totalFlux);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.heat = buf.readInt();
		this.water = buf.readByte();
		this.level = buf.readDouble();
		this.targetLevel = buf.readDouble();
		this.slotFlux = BufferUtil.readIntArray(buf);
		this.totalFlux = buf.readInt();
	}
	
	public byte getWater() {
		byte water = 0;
		
		for(byte d = 0; d < 6; d++) {
			ForgeDirection dir = ForgeDirection.getOrientation(d);
			if(d < 2) {
				if(worldObj.getBlock(xCoord, yCoord + 1 + dir.offsetY * 2, zCoord).getMaterial() == Material.water)
					water++;
			} else {
				for(byte i = 0; i < 3; i++) {
					if(worldObj.getBlock(xCoord + dir.offsetX, yCoord + i, zCoord + dir.offsetZ).getMaterial() == Material.water)
						water++;
				}
			}
		}
		
		return water;
	}
	
	//Control Rods
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}
	
	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {
		if(data.hasKey("level")) {
			this.setTarget(data.getDouble("level"));
		}
		
		this.markDirty();
	}
	
	public void setTarget(double target) {
		this.targetLevel = target;
	}
	
	public void rodControl() {
		if(worldObj.isRemote) {
			
			this.lastLevel = this.level;
		
		} else {
			
			if(level < targetLevel) {
				
				level += speed;
				
				if(level >= targetLevel)
					level = targetLevel;
			}
			
			if(level > targetLevel) {
				
				level -= speed;
				
				if(level <= targetLevel)
					level = targetLevel;
			}
		}
	}
	
	public int[] getDisplayData() {
		int[] data = new int[2];
		data[0] = this.totalFlux;
		data[1] = (int) Math.round((this.heat) * 0.00002 * 980 + 20);
		return data;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "research_reactor";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTemp(Context context, Arguments args) { // or getHeat, whatever.
		return new Object[] {heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getLevel(Context context, Arguments args) {
		return new Object[] {level * 100};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTargetLevel(Context context, Arguments args) {
		return new Object[] {targetLevel};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFlux(Context context, Arguments args) {
		return new Object[] {totalFlux};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {heat, level, targetLevel, totalFlux};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
				"getTemp",
				"getLevel",
				"getTargetLevel",
				"getFlux",
				"getInfo"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch(method) {
			case ("getTemp"):
				return getTemp(context, args);
			case ("getLevel"):
				return getLevel(context, args);
			case ("getTargetLevel"):
				return getTargetLevel(context, args);
			case ("getFlux"):
				return getFlux(context, args);
			case ("getInfo"):
				return getInfo(context, args);
		}
		throw new NoSuchMethodException();
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setLevel(Context context, Arguments args) {
		double newLevel = args.checkDouble(0)/100.0;
		targetLevel = MathHelper.clamp_double(newLevel, 0, 1.0);
		return new Object[] {};
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setDouble(CompatEnergyControl.D_HEAT_C, Math.round(heat * 2.0E-5D * 980.0D + 20.0D));
		data.setInteger(CompatEnergyControl.I_FLUX, totalFlux);
		data.setInteger(CompatEnergyControl.I_WATER, water);
	}
}
