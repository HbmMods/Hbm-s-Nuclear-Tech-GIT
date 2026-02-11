package com.hbm.tileentity.machine;

import java.util.Random;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.common.Optional;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityChungus extends TileEntityTurbineBase implements SimpleComponent, CompatHandler.OCComponent, IConfigurableMachine {

	private int turnTimer;
	public float rotor;
	public float lastRotor;
	public float fanAcceleration = 0F;

	private AudioWrapper audio;
	private float audioDesync;

	//Configurable values
	public static int inputTankSize = 1_000_000_000;
	public static int outputTankSize = 1_000_000_000;
	public static double efficiency = 0.85D;

	public TileEntityChungus() {
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.STEAM, inputTankSize);
		tanks[1] = new FluidTank(Fluids.SPENTSTEAM, outputTankSize);

		Random rand = new Random();
		audioDesync = rand.nextFloat() * 0.05F;
	}

	@Override
	public String getConfigName() {
		return "steamturbineLeviathan";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		inputTankSize = IConfigurableMachine.grab(obj, "I:inputTankSize", inputTankSize);
		outputTankSize = IConfigurableMachine.grab(obj, "I:outputTankSize", outputTankSize);
		efficiency = IConfigurableMachine.grab(obj, "D:efficiency", efficiency);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("INFO").value("leviathan steam turbine consumes all availible steam per tick");
		writer.name("I:inputTankSize").value(inputTankSize);
		writer.name("I:outputTankSize").value(outputTankSize);
		writer.name("D:efficiency").value(efficiency);
	}
	
	@Override public double consumptionPercent() { return 1D; }
	@Override public double getEfficiency() { return efficiency; }

	@Override
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 5, yCoord + 2, zCoord + dir.offsetZ * 5, dir),
				new DirPos(xCoord + rot.offsetX * 3, yCoord, zCoord + rot.offsetZ * 3, rot),
				new DirPos(xCoord - rot.offsetX * 3, yCoord, zCoord - rot.offsetZ * 3, rot.getOpposite())
		};
	}
	
	@Override
	public DirPos[] getPowerPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		return new DirPos[] { new DirPos(xCoord - dir.offsetX * 11, yCoord, zCoord - dir.offsetZ * 11, dir.getOpposite()) };
	}

	@Override
	public void onServerTick() {
		turnTimer--;
		if(operational) turnTimer = 25;
	}
	
	@Override
	public void onClientTick() {

		this.lastRotor = this.rotor;
		this.rotor += this.fanAcceleration;

		if(this.rotor >= 360) {
			this.rotor -= 360;
			this.lastRotor -= 360;
		}

		if(turnTimer > 0) {
			// Fan accelerates with a random offset to ensure the audio doesn't perfectly align, makes for a more pleasant hum
			this.fanAcceleration = Math.max(0F, Math.min(25F, this.fanAcceleration += 0.075F + audioDesync));

			Random rand = worldObj.rand;
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			ForgeDirection side = dir.getRotation(ForgeDirection.UP);

			for(int i = 0; i < 10; i++) {
				worldObj.spawnParticle("cloud",
						xCoord + 0.5 + dir.offsetX * (rand.nextDouble() + 1.25) + rand.nextGaussian() * side.offsetX * 0.65,
						yCoord + 2.5 + rand.nextGaussian() * 0.65,
						zCoord + 0.5 + dir.offsetZ * (rand.nextDouble() + 1.25) + rand.nextGaussian() * side.offsetZ * 0.65,
						-dir.offsetX * 0.2, 0, -dir.offsetZ * 0.2);
			}

			if(audio == null) {
				audio = MainRegistry.proxy.getLoopedSound("hbm:block.chungusTurbineRunning", xCoord, yCoord, zCoord, 1.0F, 20F, 1.0F, 20);
				audio.startSound();
			}

			float turbineSpeed = this.fanAcceleration / 25F;
			audio.updateVolume(getVolume(0.5f * turbineSpeed));
			audio.updatePitch(0.25F + 0.75F * turbineSpeed);
			audio.keepAlive();
			
		} else {
			this.fanAcceleration = Math.max(0F, Math.min(25F, this.fanAcceleration -= 0.1F));

			if(audio != null) {
				if(this.fanAcceleration > 0) {
					float turbineSpeed = this.fanAcceleration / 25F;
					audio.updateVolume(getVolume(0.5f * turbineSpeed));
					audio.updatePitch(0.25F + 0.75F * turbineSpeed);
				} else {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.turnTimer);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.turnTimer = buf.readInt();
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN && dir != ForgeDirection.UNKNOWN;
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_turbine";
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Callback(direct = true, doc = "function():table -- Gets current tanks state. The format is the following: <input tank amount>, <input tank capacity>, <output tank amount>, <output tank capacity>")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluid(Context context, Arguments args) {
		return new Object[] {tanks[0].getFill(), tanks[0].getMaxFill(), tanks[1].getFill(), tanks[1].getMaxFill()};
	}

	@Callback(direct = true, doc = "function():number -- Gets the current input tank fluid type. 0 stands for steam, 1 for dense steam, 2 for super dense steam and 3 for ultra dense steam.")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getType(Context context, Arguments args) {
		return CompatHandler.steamTypeToInt(tanks[0].getTankType());
	}

	@Callback(direct = true, limit = 4, doc = "function(type:number) -- Sets the input tank fluid type. Refer getType() for the accepted values information.")
	@Optional.Method(modid = "OpenComputers")
	public Object[] setType(Context context, Arguments args) {
		tanks[0].setTankType(CompatHandler.intToSteamType(args.checkInteger(0)));
		return new Object[] {};
	}

	@Callback(direct = true, doc = "function():number -- Gets the power buffer of the turbine.")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPower(Context context, Arguments args) {
		return new Object[] {powerBuffer};
	}

	@Callback(direct = true, doc = "function():table -- Gets information about this turbine. The format is the following: <input tank amount>, <input tank capacity>, <output tank amount>, <output tank capacity>, <input tank fluid type>, <power>")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {tanks[0].getFill(), tanks[0].getMaxFill(), tanks[1].getFill(), tanks[1].getMaxFill(), CompatHandler.steamTypeToInt(tanks[0].getTankType())[0], powerBuffer};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
				"getFluid",
				"getType",
				"setType",
				"getPower",
				"getInfo"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch(method) {
			case ("getFluid"):
				return getFluid(context, args);
			case ("getType"):
				return getType(context, args);
			case ("setType"):
				return setType(context, args);
			case ("getPower"):
				return getPower(context, args);
			case ("getInfo"):
				return getInfo(context, args);
		}
		throw new NoSuchMethodException();
	}
}
