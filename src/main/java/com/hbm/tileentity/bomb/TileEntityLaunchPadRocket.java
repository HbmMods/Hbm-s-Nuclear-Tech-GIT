package com.hbm.tileentity.bomb;

import com.hbm.entity.missile.EntityRideableRocket;
import com.hbm.handler.RocketStruct;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerLaunchPadRocket;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUILaunchPadRocket;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.weapon.ItemCustomRocket;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityLaunchPadRocket extends TileEntityMachineBase implements IControlReceiver, IEnergyReceiverMK2, IFluidStandardReceiver, IGUIProvider {
	
	public long power;
	public final long maxPower = 100_000;

	public int solidFuel = 0;

	public FluidTank[] tanks;

	@SideOnly(Side.CLIENT)
	public RocketStruct rocket;

	public TileEntityLaunchPadRocket() {
		super(5); // 0 rocket, 1 drive, 2 battery, 3/4 liquid/solid fuel in/out
		tanks = new FluidTank[RocketStruct.MAX_STAGES * 2]; // enough tanks for any combination of rocket stages
		for(int i = 0; i < tanks.length; i++) tanks[i] = new FluidTank(Fluids.NONE, 64_000);
	}

	@Override
	public String getName() {
		return "container.launchPadRocket";
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					for(FluidTank tank : tanks) {
						if(tank.getTankType() == Fluids.NONE) continue;
						trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
					}
				}
			}

			power = Library.chargeTEFromItems(slots, 2, power, maxPower);
			for(FluidTank tank : tanks) tank.loadTank(3, 4, slots);

			networkPackNT(250);
		}
	}

	public DirPos[] getConPos() {
		return new DirPos[] {
			new DirPos(xCoord + 5, yCoord, zCoord - 2, Library.POS_X),
			new DirPos(xCoord + 5, yCoord, zCoord + 2, Library.POS_X),
			new DirPos(xCoord - 5, yCoord, zCoord - 2, Library.NEG_X),
			new DirPos(xCoord - 5, yCoord, zCoord + 2, Library.NEG_X),
			new DirPos(xCoord - 2, yCoord, zCoord + 5, Library.POS_Z),
			new DirPos(xCoord + 2, yCoord, zCoord + 5, Library.POS_Z),
			new DirPos(xCoord - 2, yCoord, zCoord - 5, Library.NEG_Z),
			new DirPos(xCoord + 2, yCoord, zCoord - 5, Library.NEG_Z)
		};
	}

	public void launch() {
		if(!canLaunch()) return;

		EntityRideableRocket rocket = new EntityRideableRocket(worldObj, xCoord + 0.5F, yCoord + 1.0F, zCoord + 0.5F, slots[0]).withPayload(slots[1]);
		worldObj.spawnEntityInWorld(rocket);
		
		slots[0] = null;
		slots[1] = null;
	}

	public boolean canLaunch() {
		if(slots[0] == null || !(slots[0].getItem() instanceof ItemCustomRocket)) return false;
		if(slots[1] == null || !(slots[1].getItem() instanceof ItemVOTVdrive)) return false;

		return true;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeLong(power);
		buf.writeInt(solidFuel);

		if(slots[0] != null && slots[0].getItem() instanceof ItemCustomRocket) {
			buf.writeBoolean(true);
			RocketStruct sendRocket = ItemCustomRocket.get(slots[0]);
			sendRocket.writeToByteBuffer(buf);
		} else {
			buf.writeBoolean(false);
		}

		for(int i = 0; i < tanks.length; i++) tanks[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		power = buf.readLong();
		solidFuel = buf.readInt();

		if(buf.readBoolean()) {
			rocket = RocketStruct.readFromByteBuffer(buf);
		} else {
			rocket = null;
		}

		for(int i = 0; i < tanks.length; i++) tanks[i].deserialize(buf);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("solid", solidFuel);
		for(int i = 0; i < tanks.length; i++) tanks[i].writeToNBT(nbt, "t" + i);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		solidFuel = nbt.getInteger("solid");
		for(int i = 0; i < tanks.length; i++) tanks[i].readFromNBT(nbt, "t" + i);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.getBoolean("launch")) {
			launch();
		}
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }
	@Override public FluidTank[] getAllTanks() { return this.tanks; }
	@Override public FluidTank[] getReceivingTanks() { return this.tanks; }

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLaunchPadRocket(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILaunchPadRocket(player.inventory, this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 10,
					yCoord,
					zCoord - 10,
					xCoord + 11,
					yCoord + 15,
					zCoord + 11
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
}
