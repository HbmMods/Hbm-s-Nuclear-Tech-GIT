package com.hbm.tileentity.machine;

import com.hbm.config.VersatileConfig;
import com.hbm.inventory.container.ContainerMachineRTG;
import com.hbm.inventory.gui.GUIMachineRTG;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TilePortShapes;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.RTGUtil;

import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityMachineRTG extends TileEntityMachineBase implements ISidedInventory, IEnergyProviderMK2, IGUIProvider, IInfoProviderEC {

	public int heat;
	public final int heatMax = VersatileConfig.rtgDecay() ? 600 : 200;
	public long power;
	public final long powerMax = 100000;

	public static final int[] slot_io = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

	public TileEntityMachineRTG() {
		super(15);
	}

	@Override
	public String getName() {
		return "container.rtg";
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		heat = nbt.getInteger("heat");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setInteger("heat", heat);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return slot_io;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}

	public long getPowerScaled(long i) {
		return (power * i) / powerMax;
	}

	public int getHeatScaled(int i) {
		return (heat * i) / heatMax;
	}

	public boolean hasPower() {
		return power > 0;
	}

	public boolean hasHeat() {
		return RTGUtil.hasHeat(slots, slot_io);
	}
	
	protected PortDef[] cachedPorts;
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.around(xCoord, yCoord, zCoord); return cachedPorts; }

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			this.setupPowerPorts(getPorts());
			this.updateAllPorts();
			this.providePower();

			heat = RTGUtil.updateRTGs(slots, slot_io);

			if(heat > heatMax)
				heat = heatMax;

			power += heat * 5;
			if(power > powerMax)
				power = powerMax;

			networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		power = buf.readLong();
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return powerMax;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineRTG(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineRTG(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.heat > 0);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, heat * 5D);
	}
}
