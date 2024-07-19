package com.hbm.tileentity.bomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.dim.CelestialBody;
import com.hbm.entity.missile.EntityRideableRocket;
import com.hbm.handler.RocketStruct;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerLaunchPadRocket;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUILaunchPadRocket;
import com.hbm.items.ItemVOTVdrive;
import com.hbm.items.ModItems;
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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TileEntityLaunchPadRocket extends TileEntityMachineBase implements IControlReceiver, IEnergyReceiverMK2, IFluidStandardReceiver, IGUIProvider {
	
	public long power;
	public final long maxPower = 100_000;

	public int solidFuel = 0;
	public int maxSolidFuel = 0;

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
			// Setup tanks required for the current rocket
			updateTanks();

			// Connections
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

					if(hasRocket()) {
						for(FluidTank tank : tanks) {
							if(tank.getTankType() == Fluids.NONE) continue;
							trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
						}
					}
				}
			}

			// Fills, note that the liquid input also takes solid fuel
			power = Library.chargeTEFromItems(slots, 2, power, maxPower);
			for(FluidTank tank : tanks) tank.loadTank(3, 4, slots);
			if(slots[3] != null && slots[3].getItem() == ModItems.rocket_fuel && solidFuel < maxSolidFuel) {
				decrStackSize(3, 1);
				solidFuel += 250;
				if(solidFuel > maxSolidFuel) solidFuel = maxSolidFuel;
			}

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

		// Deplete all fills
		for(int i = 0; i < tanks.length; i++) tanks[i] = new FluidTank(Fluids.NONE, 64_000);
		solidFuel = maxSolidFuel = 0;
		
		slots[0] = null;
		slots[1] = null;
	}

	private boolean hasRocket() {
		return slots[0] != null && slots[0].getItem() instanceof ItemCustomRocket;
	}

	private boolean hasDrive() {
		return slots[1] != null && slots[1].getItem() instanceof ItemVOTVdrive;
	}

	private boolean areTanksFull() {
		for(FluidTank tank : tanks) if(tank.getTankType() != Fluids.NONE && tank.getFill() < tank.getMaxFill()) return false;
		if(solidFuel < maxSolidFuel) return false;
		return true;
	}

	private boolean canReachDestination() {
		CelestialBody localBody = CelestialBody.getBody(worldObj);
		ItemVOTVdrive drive = (ItemVOTVdrive) slots[1].getItem();
		CelestialBody destination = drive.getDestination(slots[1]).body.getBody();

		// Check if the stage can make the journey
		if(destination != null && destination != localBody) {
			RocketStruct rocket = ItemCustomRocket.get(slots[0]);
			if(rocket.hasSufficientFuel(localBody, destination)) return true;
		}

		return false;
	}

	public boolean canLaunch() {
		return hasRocket() && hasDrive() && power >= maxPower * 0.75 && areTanksFull() && canReachDestination();
	}

	private void updateTanks() {
		if(!hasRocket()) return;

		RocketStruct rocket = ItemCustomRocket.get(slots[0]);
		Map<FluidType, Integer> fuels = rocket.getFillRequirement();

		// Remove solid fuels (listed as NONE fluid) from tank updates
		if(fuels.containsKey(Fluids.NONE)) {
			maxSolidFuel = fuels.get(Fluids.NONE);
			fuels.remove(Fluids.NONE);
		} else {
			maxSolidFuel = 0;
		}

		// Check to see if any of the current tanks already fulfil fuelling requirements
		List<FluidTank> keepTanks = new ArrayList<FluidTank>();
		for(FluidTank tank : tanks) {
			if(fuels.containsKey(tank.getTankType())) {
				tank.changeTankSize(fuels.get(tank.getTankType()));
				keepTanks.add(tank);
				fuels.remove(tank.getTankType());
			}
		}

		// Add new tanks
		for(Entry<FluidType, Integer> entry : fuels.entrySet()) {
			keepTanks.add(new FluidTank(entry.getKey(), entry.getValue()));
		}

		// Sort and fill the tank array to place NONE at the end
		keepTanks.sort((a, b) -> b.getTankType().getID() - a.getTankType().getID());
		while(keepTanks.size() < RocketStruct.MAX_STAGES * 2) {
			keepTanks.add(new FluidTank(Fluids.NONE, 64_000));
		}

		tanks = keepTanks.toArray(new FluidTank[RocketStruct.MAX_STAGES * 2]);
	}

	public List<String> findIssues() {
		List<String> issues = new ArrayList<String>();

		if(!hasRocket()) return issues;
		
		// Check that the rocket is fully fueled and capable of leaving our starting planet
		RocketStruct rocket = ItemCustomRocket.get(slots[0]);

		if(power < maxPower * 0.75) {
			issues.add(EnumChatFormatting.RED + "Insufficient power");
		}

		for(FluidTank tank : tanks) {
			if(tank.getTankType() == Fluids.NONE) continue;
			int fill = tank.getFill();
			int maxFill = tank.getMaxFill();
			String tankName = tank.getTankType().getLocalizedName();
			if(tankName.contains(" ")) {
				String[] split = tankName.split(" ");
				tankName = split[split.length - 1];
			}
			if(fill < maxFill) {
				issues.add(EnumChatFormatting.YELLOW + "" + fill + "/" + maxFill + "mB " + tankName);
			} else {
				issues.add(EnumChatFormatting.GREEN + "" + fill + "/" + maxFill + "mB " + tankName);
			}
		}

		if(maxSolidFuel > 0) {
			if(solidFuel < maxSolidFuel) {
				issues.add(EnumChatFormatting.YELLOW + "" + solidFuel + "/" + maxSolidFuel + "kg Solid Fuel");
			} else {
				issues.add(EnumChatFormatting.GREEN + "" + solidFuel + "/" + maxSolidFuel + "kg Solid Fuel");
			}
		}

		if(!hasDrive()) {
			issues.add(EnumChatFormatting.YELLOW + "No destination drive installed");
			return issues;
		}

		// Check that the rocket is actually capable of reaching our destination
		CelestialBody localBody = CelestialBody.getBody(worldObj);
		ItemVOTVdrive drive = (ItemVOTVdrive) slots[1].getItem();
		CelestialBody destination = drive.getDestination(slots[1]).body.getBody();

		if(destination == null || destination == localBody) {
			issues.add(EnumChatFormatting.RED + "Invalid destination");
		} else {
			if(!rocket.hasSufficientFuel(localBody, destination)) {
				issues.add(EnumChatFormatting.RED + "Rocket can't reach destination");
			}
		}

		return issues;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeLong(power);
		buf.writeInt(solidFuel);
		buf.writeInt(maxSolidFuel);

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
		maxSolidFuel = buf.readInt();

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
		nbt.setInteger("maxSolid", maxSolidFuel);
		for(int i = 0; i < tanks.length; i++) tanks[i].writeToNBT(nbt, "t" + i);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		solidFuel = nbt.getInteger("solid");
		maxSolidFuel = nbt.getInteger("maxSolid");
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
