package com.hbm.tileentity.machine;

import java.util.Random;
import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.container.ContainerMachineLargeTurbine;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.inventory.gui.GUIMachineLargeTurbine;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityMachineLargeTurbine extends TileEntityMachineBase implements IEnergyProviderMK2, IFluidStandardTransceiver, IGUIProvider, SimpleComponent, IInfoProviderEC, CompatHandler.OCComponent, IConfigurableMachine, IFluidCopiable {

	public long power;
	public FluidTank[] tanks;
	protected double[] info = new double[3];

	private boolean shouldTurn;
	public float rotor;
	public float lastRotor;
	public float fanAcceleration = 0F;

	private AudioWrapper audio;
	private float audioDesync;

	//Configurable Values
	public static long maxPower = 100000000;
	public static int inputTankSize = 512_000;
	public static int outputTankSize = 10_240_000;
	public static double efficiency = 1.0;


	public TileEntityMachineLargeTurbine() {
		super(7);

		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.STEAM, inputTankSize);
		tanks[1] = new FluidTank(Fluids.SPENTSTEAM, outputTankSize);

		Random rand = new Random();
		audioDesync = rand.nextFloat() * 0.05F;
	}

	@Override
	public String getConfigName() {
		return "steamturbineIndustrial";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		maxPower = IConfigurableMachine.grab(obj, "L:maxPower", maxPower);
		inputTankSize = IConfigurableMachine.grab(obj, "I:inputTankSize", inputTankSize);
		outputTankSize = IConfigurableMachine.grab(obj, "I:outputTankSize", outputTankSize);
		efficiency = IConfigurableMachine.grab(obj, "D:efficiency", efficiency);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:maxPower").value(maxPower);
		writer.name("INFO").value("industrial steam turbine consumes 20% of availible steam per tick");
		writer.name("I:inputTankSize").value(inputTankSize);
		writer.name("I:outputTankSize").value(outputTankSize);
		writer.name("D:efficiency").value(efficiency);
	}

	@Override
	public String getName() {
		return "container.machineLargeTurbine";
	}

	private boolean operational;

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.info = new double[3];

			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			this.tryProvide(worldObj, xCoord + dir.offsetX * -4, yCoord, zCoord + dir.offsetZ * -4, dir.getOpposite());
			for(DirPos pos : getConPos()) this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			for(DirPos pos : getConPos()) this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

			tanks[0].setType(0, 1, slots);
			tanks[0].loadTank(2, 3, slots);
			power = Library.chargeItemsFromTE(slots, 4, power, maxPower);

			FluidType in = tanks[0].getTankType();
			boolean valid = false;
			if(in.hasTrait(FT_Coolable.class)) {
				FT_Coolable trait = in.getTrait(FT_Coolable.class);
				double eff = trait.getEfficiency(CoolingType.TURBINE) * efficiency; //100% efficiency by default
				if(eff > 0) {
					tanks[1].setTankType(trait.coolsTo);
					int inputOps = (int) Math.floor(tanks[0].getFill() / trait.amountReq); //amount of cycles possible with the entire input buffer
					int outputOps = (tanks[1].getMaxFill() - tanks[1].getFill()) / trait.amountProduced; //amount of cycles possible with the output buffer's remaining space
					int cap = (int) Math.ceil(tanks[0].getFill() / trait.amountReq / 5F); //amount of cycles by the "at least 20%" rule
					int ops = Math.min(inputOps, Math.min(outputOps, cap)); //defacto amount of cycles
					tanks[0].setFill(tanks[0].getFill() - ops * trait.amountReq);
					tanks[1].setFill(tanks[1].getFill() + ops * trait.amountProduced);
					this.power += (ops * trait.heatEnergy * eff);
					info[0] = ops * trait.amountReq;
					info[1] = ops * trait.amountProduced;
					info[2] = ops * trait.heatEnergy * eff;
					valid = true;
					operational = ops > 0;
				}
			}
			if(!valid) tanks[1].setTankType(Fluids.NONE);
			if(power > maxPower) power = maxPower;

			tanks[1].unloadTank(5, 6, slots);

			this.networkPackNT(50);

		} else {
			this.lastRotor = this.rotor;
			this.rotor += this.fanAcceleration;

			if(this.rotor >= 360) {
				this.rotor -= 360;
				this.lastRotor -= 360;
			}

			if(shouldTurn) {
				// Fan accelerates with a random offset to ensure the audio doesn't perfectly align, makes for a more pleasant hum
				this.fanAcceleration = Math.max(0F, Math.min(15F, this.fanAcceleration += 0.075F + audioDesync));

				if(audio == null) {
					audio = MainRegistry.proxy.getLoopedSound("hbm:block.largeTurbineRunning", xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F);
					audio.startSound();
				}

				float turbineSpeed = this.fanAcceleration / 15F;
				audio.updateVolume(getVolume(0.4f * turbineSpeed));
				audio.updatePitch(0.25F + 0.75F * turbineSpeed);
			} else {
				this.fanAcceleration = Math.max(0F, Math.min(15F, this.fanAcceleration -= 0.1F));

				if(audio != null) {
					if(this.fanAcceleration > 0) {
						float turbineSpeed = this.fanAcceleration / 15F;
						audio.updateVolume(getVolume(0.4f * turbineSpeed));
						audio.updatePitch(0.25F + 0.75F * turbineSpeed);
					} else {
						audio.stopSound();
						audio = null;
					}
				}
			}
		}
	}

	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord - rot.offsetX * 2, yCoord, zCoord - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2, dir)
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(this.power);
		buf.writeBoolean(operational);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.shouldTurn = buf.readBoolean();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
	}

	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
		power = nbt.getLong("power");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
		nbt.setLong("power", power);
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
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

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
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
		return new Object[] {power};
	}

	@Callback(direct = true, doc = "function():table -- Gets information about this turbine. The format is the following: <input tank amount>, <input tank capacity>, <output tank amount>, <output tank capacity>, <input tank fluid type>, <power>")
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {tanks[0].getFill(), tanks[0].getMaxFill(), tanks[1].getFill(), tanks[1].getMaxFill(), CompatHandler.steamTypeToInt(tanks[0].getTankType())[0], power};
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
			case ("getInfo"):
				return getInfo(context, args);
		}
		throw new NoSuchMethodException();
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineLargeTurbine(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineLargeTurbine(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, info[1] > 0);
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, info[0]);
		data.setDouble(CompatEnergyControl.D_OUTPUT_MB, info[1]);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, info[2]);
	}
}
