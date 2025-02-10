package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.CompatHandler;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerCombustionEngine;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FluidTrait.FluidReleaseType;
import com.hbm.inventory.gui.GUICombustionEngine;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPistons.EnumPistonType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityMachineCombustionEngine extends TileEntityMachinePolluting implements IEnergyProviderMK2, IFluidStandardTransceiver, IControlReceiver, IGUIProvider, SimpleComponent, CompatHandler.OCComponent, IFluidCopiable {

	public boolean isOn = false;
	public static long maxPower = 2_500_000;
	public long power;
	private int playersUsing = 0;
	public int setting = 0;
	public boolean wasOn = false;

	public float doorAngle = 0;
	public float prevDoorAngle = 0;

	private AudioWrapper audio;

	public FluidTank tank;
	public int tenth = 0;

	public TileEntityMachineCombustionEngine() {
		super(5, 50);
		this.tank = new FluidTank(Fluids.DIESEL, 24_000);
	}

	@Override
	public String getName() {
		return "container.combustionEngine";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.tank.loadTank(0, 1, slots);
			if(this.tank.setType(4, slots)) {
				this.tenth = 0;
			}

			wasOn = false;

			int fill = tank.getFill() * 10 + tenth;
			if(isOn && setting > 0 && slots[2] != null && slots[2].getItem() == ModItems.piston_set && fill > 0 && tank.getTankType().hasTrait(FT_Combustible.class)) {
				EnumPistonType piston = EnumUtil.grabEnumSafely(EnumPistonType.class, slots[2].getItemDamage());
				FT_Combustible trait = tank.getTankType().getTrait(FT_Combustible.class);

				double eff = piston.eff[trait.getGrade().ordinal()];

				if(eff > 0) {
					int speed = setting * 2;

					int toBurn = Math.min(fill, speed);
					this.power += toBurn * (trait.getCombustionEnergy() / 10_000D) * eff;
					fill -= toBurn;

					if(worldObj.getTotalWorldTime() % 5 == 0 && toBurn > 0) {
						super.pollute(tank.getTankType(), FluidReleaseType.BURN, toBurn * 0.5F);
					}

					if(toBurn > 0) {
						wasOn = true;
					}

					tank.setFill(fill / 10);
					tenth = fill % 10;
				}
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", Math.min(power, maxPower));

			this.power = Library.chargeItemsFromTE(slots, 3, power, power);

			for(DirPos pos : getConPos()) {
				this.tryProvide(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.sendSmoke(pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			if(power > maxPower)
				power = maxPower;

			this.networkPackNT(50);

		} else {
			this.prevDoorAngle = this.doorAngle;
			float swingSpeed = (doorAngle / 10F) + 3;

			if(this.playersUsing > 0) {
				this.doorAngle += swingSpeed;
			} else {
				this.doorAngle -= swingSpeed;
			}

			this.doorAngle = MathHelper.clamp_float(this.doorAngle, 0F, 135F);

			if(wasOn) {

				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}

				audio.keepAlive();
				audio.updateVolume(this.getVolume(1F));

			} else {

				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}

	private DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 1 + rot.offsetX, yCoord, zCoord + dir.offsetZ * 1 + rot.offsetZ, dir),
				new DirPos(xCoord + dir.offsetX * 1 - rot.offsetX, yCoord, zCoord + dir.offsetZ * 1 - rot.offsetZ, dir),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite())
		};
	}

	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.igeneratorOperate", xCoord, yCoord, zCoord, 1.0F, 10F, 1.0F, 20);
	}

	@Override
	public void onChunkUnload() {

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

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.DOWN;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(this.playersUsing);
		buf.writeInt(this.setting);
		buf.writeLong(this.power);
		buf.writeBoolean(this.isOn);
		buf.writeBoolean(this.wasOn);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.playersUsing = buf.readInt();
		this.setting = buf.readInt();
		this.power = buf.readLong();
		this.isOn = buf.readBoolean();
		this.wasOn = buf.readBoolean();
		tank.deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.setting = nbt.getInteger("setting");
		this.power = nbt.getLong("power");
		this.isOn = nbt.getBoolean("isOn");
		this.tank.readFromNBT(nbt, "tank");
		this.tenth = nbt.getInteger("tenth");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("setting", setting);
		nbt.setLong("power", power);
		nbt.setBoolean("isOn", isOn);
		tank.writeToNBT(nbt, "tank");
		nbt.setInteger("tenth", tenth);
	}

	@Override
	public void openInventory() {
		if(!worldObj.isRemote) this.playersUsing++;
	}

	@Override
	public void closeInventory() {
		if(!worldObj.isRemote) this.playersUsing--;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCombustionEngine(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICombustionEngine(player.inventory, this);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.getSmokeTanks();
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 3,
					yCoord,
					zCoord - 3,
					xCoord + 4,
					yCoord + 2,
					zCoord + 4
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
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 25;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("turnOn")) this.isOn = !this.isOn;
		if(data.hasKey("setting")) this.setting = data.getInteger("setting");

		this.markChanged();
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setIntArray("fluidID", new int[]{tank.getTankType().getID()});
		tag.setBoolean("isOn", isOn);
		tag.setInteger("burnRate", setting);
		return tag;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		int id = nbt.getIntArray("fluidID")[index];
		tank.setTankType(Fluids.fromID(id));
		if(nbt.hasKey("isOn")) isOn = nbt.getBoolean("isOn");
		if(nbt.hasKey("burnRate")) setting = nbt.getInteger("burnRate");
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_combustion_engine";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluid(Context context, Arguments args) {
		return new Object[] {tank.getFill(), tank.getMaxFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getType(Context context, Arguments args) {
		return new Object[] {tank.getTankType().getName()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPower(Context context, Arguments args) {
		return new Object[] {power};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getThrottle	(Context context, Arguments args) {
		return new Object[] {setting};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getState(Context context, Arguments args) {
		return new Object[] {isOn};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEfficiency(Context context, Arguments args) {
		EnumPistonType piston = EnumUtil.grabEnumSafely(EnumPistonType.class, slots[2].getItemDamage());
		FT_Combustible trait = tank.getTankType().getTrait(FT_Combustible.class);
		double eff = piston.eff[trait.getGrade().ordinal()];
		return new Object[] {eff};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] setThrottle(Context context, Arguments args) {
		int throttleRequest = args.checkInteger(0);
		if ((throttleRequest < 0) || (throttleRequest > 30)) { // return false without doing anything if number is outside normal
			return new Object[] {false, "Throttle request outside of range 0-30"};
		};
		setting = throttleRequest;
		return new Object[] {true};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] start(Context context, Arguments args) {
		isOn = true;
		return new Object[] {};
	}

	@Callback(direct = true, limit = 4)
	@Optional.Method(modid = "OpenComputers")
	public Object[] stop(Context context, Arguments args) {
		isOn = false;
		return new Object[] {};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		EnumPistonType piston = EnumUtil.grabEnumSafely(EnumPistonType.class, slots[2].getItemDamage());
		FT_Combustible trait = tank.getTankType().getTrait(FT_Combustible.class);
		double eff = piston.eff[trait.getGrade().ordinal()];
		return new Object[] {setting, isOn, power, eff, tank.getFill(), tank.getMaxFill(), tank.getTankType().getName()};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
			"getFluid",
			"getType",
			"getPower",
			"getThrottle",
			"getState",
			"getEfficiency",
			"setThrottle",
			"start",
			"stop",
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
			case ("getPower"):
				return getPower(context, args);
			case ("getThrottle"):
				return getThrottle(context, args);
			case ("getState"):
				return getState(context, args);
			case ("getEfficiency"):
				return getEfficiency(context, args);
			case ("setThrottle"):
				return setThrottle(context, args);
			case ("start"):
				return start(context, args);
			case ("stop"):
				return stop(context, args);
			case ("getInfo"):
				return getInfo(context, args);
		}
		throw new NoSuchMethodException();
	}

}
