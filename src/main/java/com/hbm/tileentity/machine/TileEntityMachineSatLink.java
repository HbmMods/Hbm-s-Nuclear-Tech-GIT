package com.hbm.tileentity.machine;

import com.hbm.handler.CompatHandler;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.satellites.SatelliteBase;
import com.hbm.saveddata.satellites.SatelliteRayScan;
import com.hbm.saveddata.satellites.SatelliteRayScan.RayEvent;
import com.hbm.tileentity.TileEntityTickingBase;

import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IChatComponent;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityMachineSatLink extends TileEntityTickingBase implements IRORValueProvider, IRORInteractive, SimpleComponent, CompatHandler.OCComponent {

	public boolean connected;
	public int freq;

	public float rot = INACTIVE_ROT;
	public float prevRot = INACTIVE_ROT;
	public float lift = INACTIVE_LIFT;
	public float prevLift = INACTIVE_LIFT;

	public static final float SPEED = 0.25F;
	public static final float ACTIVE_ROT = -15F;
	public static final float ACTIVE_LIFT = -45F;
	public static final float INACTIVE_ROT = 0F;
	public static final float INACTIVE_LIFT = -85F;

	public IChatComponent[] info = new IChatComponent[0];

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			this.connected = false;

			if(worldObj.getHeightValue(xCoord, zCoord) <= yCoord) {

				SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
				this.connected = dat.isFreqTaken(freq);
			}

			this.updateInfo(connected);
			this.networkPackNT(150);

		} else {

			this.prevRot = this.rot;
			this.prevLift = this.lift;

			float targetR = this.connected ? ACTIVE_ROT : INACTIVE_ROT;
			float targetL = this.connected ? ACTIVE_LIFT : INACTIVE_LIFT;

			if(Math.abs(rot - targetR) <= SPEED) rot = targetR;
			else if(rot < targetR) rot += SPEED;
			else if(rot > targetR) rot -= SPEED;

			if(Math.abs(lift - targetL) <= SPEED) lift = targetL;
			else if(lift < targetL) lift += SPEED;
			else if(lift > targetL) lift -= SPEED;
		}
	}

	protected void updateInfo(boolean canConnect) {

		if(!canConnect) {
			if(this.info.length > 0) this.info = new IChatComponent[0];
			return;
		}

		SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
		SatelliteBase sat = dat.getSatFromFreq(freq);

		if(sat != null) {
			this.info = sat.getInfo(worldObj);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(connected);
		buf.writeInt(freq);

		buf.writeInt(info.length);

		for(int i = 0; i < info.length; i++) {
			ByteBufUtils.writeUTF8String(buf, IChatComponent.Serializer.func_150696_a(info[i]));
		}
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.connected = buf.readBoolean();
		this.freq = buf.readInt();

		int length = buf.readInt();
		if(this.info.length != length) this.info = new IChatComponent[length];

		for(int i = 0; i < info.length; i++) {
			info[i] = IChatComponent.Serializer.func_150699_a(ByteBufUtils.readUTF8String(buf));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.freq = nbt.getInteger("freq");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("freq", freq);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 10,
					zCoord + 3
			);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_VALUE + "connected",
				PREFIX_VALUE + "freq",
				PREFIX_VALUE + "rx",
				PREFIX_VALUE + "type",
				PREFIX_FUNCTION + "setfreq" + NAME_SEPARATOR + "freq",
				PREFIX_FUNCTION + "tx" + NAME_SEPARATOR + "payload"
		};
	}

	@Override
	public String provideRORValue(String name) {

		if(name.equals(PREFIX_VALUE + connected)) {
			return this.connected ? "TRUE" : "FALSE";
		}

		if(name.equals(PREFIX_VALUE + "freq")) {
			return "" + this.freq;
		}

		if(name.equals(PREFIX_VALUE + "type")) {
			SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
			SatelliteBase sat = dat.getSatFromFreq(this.freq);
			if(sat != null) {
				return sat.getType();
			}
			return "";
		}

		if(name.equals(PREFIX_VALUE + "rx")) {
			SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
			SatelliteBase sat = dat.getSatFromFreq(this.freq);
			if(sat != null) {
				return sat.tx;
			}
			return "";
		}

		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		if(name.equals(PREFIX_FUNCTION + "setfreq") && params.length == 1) {
			this.freq = IRORInteractive.parseInt(params[0], 0, 100_000);
			this.markChanged();
		}

		if(name.equals(PREFIX_FUNCTION + "tx")) {
			SatelliteSavedData dat = SatelliteSavedData.getData(worldObj);
			SatelliteBase sat = dat.getSatFromFreq(this.freq);
			String[] cmd = String.join(IRORInteractive.PARAM_SEPARATOR, params).split(" ");
			if(sat != null) {
				sat.onCommand(worldObj, cmd);
				dat.markDirty();
			}
			SatelliteRayScan.reportEvent(worldObj, xCoord, yCoord, zCoord, RayEvent.INFO_RADIO, 300);
			this.markChanged();
		}

		return null;
	}

	// yay opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_satlink";
	}

	@Callback(direct = true, doc = "function():boolean -- Returns connection state")
	@Optional.Method(modid = "OpenComputers")
	public Object[] isConnected(Context context, Arguments args) {
		return new Object[] { connected };
	}

	@Callback(direct = true, limit = 4, doc = "function(freq: number) -- Sets satellite frequency")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setFreq(Context context, Arguments args) {
		freq = args.checkInteger(0);
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function():number -- Gets satellite frequency")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFreq(Context context, Arguments args) {
		return new Object[] { freq };
	}

	@Callback(direct = true, doc = "function():string -- Gets satellite type")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getType(Context context, Arguments args) {
		return new Object[] { provideRORValue(PREFIX_VALUE + "type") };
	}

	@Callback(direct = true, limit = 4, doc = "function(command: string) -- Transmits a command to the satellite")
	@Optional.Method(modid = "OpenComputers")
	public Object[] send(Context context, Arguments args) {
		// would be easier to just trick it into thinking it ran a RoR function
		runRORFunction(PREFIX_FUNCTION + "tx", new String[]{args.checkString(0)});
		return new Object[] {};
	}

	@Callback(direct = true, limit = 4, doc = "function():string -- Gets received command from the satellite")
	@Optional.Method(modid = "OpenComputers")
	public Object[] read(Context context, Arguments args) {
		return new Object[] { provideRORValue(PREFIX_VALUE + "rx") };
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
			"isConnected",
			"setFreq",
			"getFreq",
			"getType",
			"send",
			"read"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch(method) {
			case ("isConnected"):
				return isConnected(context, args);
			case ("setFreq"):
				return setFreq(context, args);
			case ("getFreq"):
				return getFreq(context, args);
			case ("getType"):
				return getType(context, args);
			case ("send"):
				return send(context, args);
			case ("read"):
				return read(context, args);
		}
		throw new NoSuchMethodException();
	}
}
