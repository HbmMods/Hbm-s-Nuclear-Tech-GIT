package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerOilburner;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FluidTrait.FluidReleaseType;
import com.hbm.inventory.gui.GUIOilburner;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.tileentity.TilePortShapes;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.util.BobMathUtil;

import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TileEntityHeaterOilburner extends TileEntityMachinePolluting implements IGUIProvider, IFluidStandardTransceiverMK2, IHeatSource, IControlReceiver, IFluidCopiable, IRORValueProvider, IRORInteractive, IUpgradeInfoProvider {
	
	public boolean isOn = false;
	public FluidTank tank;
	public int burnRate = 1;
	public int maxBurnRate = 10;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT(this);

	public int heatEnergy;
	public int maxHeatEnergy = 500_000;

	public TileEntityHeaterOilburner() {
		super(4, 100);
		tank = new FluidTank(Fluids.HEATINGOIL, 16000);
	}

	@Override
	public String getName() {
		return "container.heaterOilburner";
	}
	
	protected PortDef[] cachedPorts;
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.flare(xCoord, yCoord, zCoord); return cachedPorts; }
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.setupFluidPorts(getPorts());
			this.updatePortFIFO();
			
			tank.loadTank(0, 1, slots);
			tank.setType(2, slots);

			this.upgradeManager.checkSlots(slots, 3, 3);
			int od = upgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			this.maxBurnRate = 10 * (od + 1);
			this.maxHeatEnergy = 500_000 * (od + 1);
			
			this.burnRate = Math.min(this.burnRate, this.maxBurnRate);
			this.heatEnergy = Math.min(this.heatEnergy, this.maxHeatEnergy);
			
			boolean shouldCool = true;
			
			if(this.isOn && this.heatEnergy < maxHeatEnergy) {
				
				if(tank.getTankType().hasTrait(FT_Flammable.class)) {
					FT_Flammable type = tank.getTankType().getTrait(FT_Flammable.class);
					
					int toBurn = Math.min(this.burnRate, tank.getFill());
					
					tank.setFill(tank.getFill() - toBurn);
					
					int heat = (int)(type.getHeatEnergy() / 1000);
					
					this.heatEnergy += heat * toBurn;

					if(worldObj.getTotalWorldTime() % 5 == 0 && toBurn > 0) {
						super.pollute(tank.getTankType(), FluidReleaseType.BURN, toBurn * 5);
					}
					
					shouldCool = false;
				}
			}
			
			if(this.heatEnergy >= maxHeatEnergy)
				shouldCool = false;
			
			if(shouldCool)
				this.heatEnergy = Math.max(this.heatEnergy - Math.max(this.heatEnergy / 1000, 1), 0);
			
			this.networkPackNT(25);
		}
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.heater_oilburner));
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>(1);
		upgrades.put(UpgradeType.OVERDRIVE, 3);
		return upgrades;
	}
	
	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.tank.serialize(buf);
		
		buf.writeBoolean(this.isOn);
		buf.writeInt(this.heatEnergy);
		buf.writeInt(this.maxHeatEnergy);
		buf.writeByte(this.burnRate);
		buf.writeByte(this.maxBurnRate);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.tank.deserialize(buf);

		this.isOn = buf.readBoolean();
		this.heatEnergy = buf.readInt();
		this.maxHeatEnergy = buf.readInt();
		this.burnRate = buf.readByte();
		this.maxBurnRate = buf.readByte();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "tank");
		isOn = nbt.getBoolean("isOn");
		heatEnergy = nbt.getInteger("heatEnergy");
		burnRate = nbt.getByte("setting");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "tank");
		nbt.setBoolean("isOn", isOn);
		nbt.setInteger("heatEnergy", heatEnergy);
		nbt.setByte("setting", (byte) this.burnRate);
	}

	@Override
	public FluidTank[] getReceivingTanks()  {
		return new FluidTank[] { tank };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerOilburner(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIOilburner(player.inventory, this);
	}

	@Override
	public int getHeatStored() {
		return heatEnergy;
	}

	@Override
	public void useUpHeat(int heat) {
		this.heatEnergy = Math.max(0, this.heatEnergy - heat);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistanceSq(xCoord, yCoord, zCoord) <= 256;
	}

	@Override
	public void receiveControl(EntityPlayer player, NBTTagCompound data) {
		if(data.hasKey("toggle")) {
			this.isOn = !this.isOn;
		}
		if(data.hasKey("burnRate")) {
			this.burnRate = data.getInteger("burnRate");
		}
		this.markChanged();
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + 2,
					zCoord + 2
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
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.getSmokeTanks();
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setIntArray("fluidID", new int[]{tank.getTankType().getID()});
		tag.setInteger("burnRate", burnRate);
		tag.setBoolean("isOn", isOn);
		return tag;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		int id = nbt.getIntArray("fluidID")[index];
		tank.setTankType(Fluids.fromID(id));
		if(nbt.hasKey("isOn")) isOn = nbt.getBoolean("isOn");
		if(nbt.hasKey("burnRate")) burnRate = Math.min(nbt.getInteger("burnRate"), this.maxBurnRate);
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
				PREFIX_VALUE + "heat",
				PREFIX_VALUE + "fuel",
				PREFIX_VALUE + "burnRate",
				PREFIX_VALUE + "state",
				PREFIX_FUNCTION + "setstate" + NAME_SEPARATOR + "active",
				PREFIX_FUNCTION + "setburnrate" + NAME_SEPARATOR + "rate"
		};
	}

	@Override
	public String provideRORValue(String name) {
		if((PREFIX_VALUE + "heat").equals(name))		return "" + heatEnergy;
		if((PREFIX_VALUE + "fuel").equals(name))		return "" + tank.getFill();
		if((PREFIX_VALUE + "burnrate").equals(name))	return "" + burnRate;
		if((PREFIX_VALUE + "state").equals(name))		return isOn ? "1" : "0";
		return null;
	}

	@Override
	public String runRORFunction(String name, String[] params) {
		if((PREFIX_FUNCTION + "setstate").equals(name) && params.length > 0) {
			this.isOn = IRORInteractive.parseBool(params[0]);
			this.markChanged();
			return null;
		}
		if((PREFIX_FUNCTION + "setburnrate").equals(name) && params.length > 0) {
			this.burnRate = IRORInteractive.parseInt(params[0], 1, this.maxBurnRate);
			this.markChanged();
			return null;
		}
		return null;
	}
}
