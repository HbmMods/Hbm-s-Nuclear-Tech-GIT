package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.neutron.RBMKNeutronHandler.RBMKType;
import com.hbm.interfaces.NotableComments;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

@NotableComments
@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public abstract class TileEntityRBMKControl extends TileEntityRBMKSlottedBase implements SimpleComponent, CompatHandler.OCComponent, IEnergyReceiverMK2 {

	public double lastLevel;
	public double level;
	public static final double speed = 0.00277D; // it takes around 18 seconds for the thing to fully extend
	public double targetLevel;
	
	public boolean hasPower = false;;
	public long power;
	public static final long consumption = 5_000;
	public static final long maxPower = consumption * 10; // enough buffer for half a second of movement

	public TileEntityRBMKControl() {
		super(0);
	}
	
	public boolean isPowered() {
		return this.getBlockType() == ModBlocks.rbmk_control_reasim || this.getBlockType() == ModBlocks.rbmk_control_reasim_auto;
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return isPowered() ? this.maxPower : 0; }
	
	@Override public boolean canConnect(ForgeDirection dir) {
		return isPowered() ? dir == ForgeDirection.DOWN : false;
	}
	
	@Override public ConnectionPriority getPriority() {
		return ConnectionPriority.LOW; // high would make more sense, but i am a sadistic asshole
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
			
			this.hasPower = true;
			
			if(this.isPowered()) {
				this.trySubscribe(worldObj, xCoord, yCoord - 1, zCoord, ForgeDirection.DOWN);
				if(this.power < consumption) this.hasPower = false;
			}

			this.lastLevel = this.level;
			
			if(this.hasPower) {
				if(level < targetLevel) {
					level += speed * RBMKDials.getControlSpeed(worldObj);
					if(level > targetLevel) level = targetLevel;
				}
	
				if(level > targetLevel) {
					level -= speed * RBMKDials.getControlSpeed(worldObj);
					if(level < targetLevel) level = targetLevel;
				}
				
				if(this.isPowered() && level != lastLevel) {
					this.power -= this.consumption;
				}
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
		return 100;
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
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(this.level);
		buf.writeDouble(this.targetLevel);
		buf.writeLong(this.power);
		buf.writeBoolean(this.hasPower);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.level = buf.readDouble();
		this.targetLevel = buf.readDouble();
		this.power = buf.readLong();
		this.hasPower = buf.readBoolean();
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
	public RBMKType getRBMKType() {
		return RBMKType.CONTROL_ROD;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		data.setDouble("level", this.level);
		data.setDouble("targetLevel", this.targetLevel);
		return data;
	}

	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_control_rod";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getLevel(Context context, Arguments args) {
		return new Object[] {getMult() * 100};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTargetLevel(Context context, Arguments args) {
		return new Object[] {targetLevel * 100};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {heat, getMult() * 100, targetLevel * 100, xCoord, yCoord, zCoord};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setLevel(Context context, Arguments args) {
		double newLevel = args.checkDouble(0)/100.0;
		targetLevel = MathHelper.clamp_double(newLevel, 0, 1);
		return new Object[] {};
	}
}
