package com.hbm.tileentity.machine;

import com.hbm.lib.Library;
import com.hbm.module.portmanager.ModulePortManPower;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TilePort.PortDef;
import com.hbm.tileentity.TilePortShapes;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.redstoneoverradio.IRORInteractive;
import api.hbm.redstoneoverradio.IRORValueProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineTrackingRadar extends TileEntityMachineBase implements IEnergyReceiverMK2, IGUIProvider, IRORValueProvider, IRORInteractive {

	public float rot = INACTIVE_ROT;
	public float prevRot = INACTIVE_ROT;
	public float lift = INACTIVE_LIFT;
	public float prevLift = INACTIVE_LIFT;

	public static final float SPEED = 0.25F;
	public static final float ACTIVE_ROT = -15F;
	public static final float ACTIVE_LIFT = -45F;
	public static final float INACTIVE_ROT = 0F;
	public static final float INACTIVE_LIFT = -85F;
	
	public long power;
	public static final long maxPower = 100_000_000;
	
	protected ModulePortManPower portman;
	
	public TileEntityMachineTrackingRadar() {
		super(1);
		portman = new ModulePortManPower(this).onlyInput();
	}


	@Override
	public String getName() {
		return "container.machineTrackingRadar";
	}

	protected PortDef[] cachedPorts;
	public PortDef[] getPorts() { if(cachedPorts == null) cachedPorts = TilePortShapes.refinery(xCoord, yCoord, zCoord); return cachedPorts; }


	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			this.portman.update(getPorts());
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
		} else {
			
			boolean isOn = true;

			this.prevRot = this.rot;
			this.prevLift = this.lift;

			float targetR = isOn ? ACTIVE_ROT : INACTIVE_ROT;
			float targetL = isOn ? ACTIVE_LIFT : INACTIVE_LIFT;

			if(Math.abs(rot - targetR) <= SPEED) rot = targetR;
			else if(rot < targetR) rot += SPEED;
			else if(rot > targetR) rot -= SPEED;

			if(Math.abs(lift - targetL) <= SPEED) lift = targetL;
			else if(lift < targetL) lift += SPEED;
			else if(lift > targetL) lift -= SPEED;
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return this.isBattery(stack);
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		portman.destroy();
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		portman.destroy();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.power = nbt.getLong("power");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 5,
					yCoord,
					zCoord - 5,
					xCoord + 6,
					yCoord + 16,
					zCoord + 6
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
			
		};
	}


	@Override
	public String provideRORValue(String name) {
		return null;
	}


	@Override
	public String runRORFunction(String name, String[] params) {
		return null;
	}


	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}


	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
