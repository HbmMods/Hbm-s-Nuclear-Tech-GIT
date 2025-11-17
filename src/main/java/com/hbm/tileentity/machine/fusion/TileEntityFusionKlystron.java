package com.hbm.tileentity.machine.fusion;

import java.util.Map.Entry;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerFusionKlystron;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIFusionKlystron;
import com.hbm.lib.Library;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.KlystronNetwork;
import com.hbm.uninos.networkproviders.KlystronNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionKlystron extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiverMK2, IControlReceiver, IGUIProvider {

	protected GenNode klystronNode;
	public static final long MAX_OUTPUT = 100_000_000;
	public static final int AIR_CONSUMPTION = 2_500;
	public long outputTarget;
	public long output;
	public long power;
	public long maxPower;
	
	public float fan;
	public float prevFan;
	public float fanSpeed;
	public static final float FAN_ACCELERATION = 0.125F;
	
	public FluidTank compair;

	public TileEntityFusionKlystron() {
		super(1);
		
		compair = new FluidTank(Fluids.AIR, AIR_CONSUMPTION * 60);
	}

	@Override
	public String getName() {
		return "container.fusionKlystron";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.maxPower = Math.max(1_000_000L, this.outputTarget * 100L);
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos);
				this.trySubscribe(compair.getTankType(), worldObj, pos);
			}
			
			this.output = 0;
			
			double powerFactor = TileEntityFusionTorus.getSpeedScaled(maxPower, power);
			double airFactor = TileEntityFusionTorus.getSpeedScaled(compair.getMaxFill(), compair.getFill());
			double factor = Math.min(powerFactor, airFactor);

			long powerReq = (long) Math.ceil(outputTarget * factor);
			int airReq = (int) Math.ceil(AIR_CONSUMPTION * factor);
			
			if(outputTarget > 0 && power >= powerReq && compair.getFill() >= airReq) {
				this.output = powerReq;
				
				this.power -= powerReq;
				this.compair.setFill(this.compair.getFill() - airReq);
			}
			
			if(output < outputTarget / 50) output = 0;
			
			if(klystronNode == null || klystronNode.expired) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getOpposite();
				klystronNode = UniNodespace.getNode(worldObj, xCoord + dir.offsetX * 4, yCoord + 2, zCoord + dir.offsetZ * 4, KlystronNetworkProvider.THE_PROVIDER);
				
				if(klystronNode == null) {
					klystronNode = new GenNode(KlystronNetworkProvider.THE_PROVIDER,
							new BlockPos(xCoord + dir.offsetX * 4, yCoord + 2, zCoord + dir.offsetZ * 4))
							.setConnections(new DirPos(xCoord + dir.offsetX * 5, yCoord + 2, zCoord + dir.offsetZ * 5, dir));
					
					UniNodespace.createNode(worldObj, klystronNode);
				}
			}
			
			if(klystronNode.net != null) klystronNode.net.addProvider(this);
			
			if(klystronNode != null && klystronNode.net != null) {
				KlystronNetwork net = (KlystronNetwork) klystronNode.net;
				
				for(Object o : net.receiverEntries.entrySet()) {
					Entry e = (Entry) o;
					if(e.getKey() instanceof TileEntityFusionTorus) { // replace this with an interface should we ever get more acceptors
						TileEntityFusionTorus torus = (TileEntityFusionTorus) e.getKey();
						
						if(torus.isLoaded() && !torus.isInvalid()) { // check against zombie network members
							torus.klystronEnergy += this.output;
							break; // we only do one anyway
						}
					}
				}
			}
			
			this.networkPackNT(100);
		} else {
			
			double mult = TileEntityFusionTorus.getSpeedScaled(outputTarget, output);
			if(this.output > 0) this.fanSpeed += FAN_ACCELERATION * mult;
			else this.fanSpeed -= FAN_ACCELERATION;
			
			this.fanSpeed = MathHelper.clamp_float(this.fanSpeed, 0F, 5F * (float) mult);
			
			this.prevFan = this.fan;
			this.fan += this.fanSpeed;
			
			if(this.fan >= 360F) {
				this.fan -= 360F;
				this.prevFan -= 360F;
			}
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 4, yCoord + 2, zCoord + dir.offsetZ * 4, dir),
				new DirPos(xCoord + rot.offsetX * 3, yCoord, zCoord + rot.offsetZ * 3, rot),
				new DirPos(xCoord - rot.offsetX * 3, yCoord, zCoord - rot.offsetZ * 3, rot.getOpposite())
		};
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.klystronNode != null) UniNodespace.destroyNode(worldObj, klystronNode);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeLong(outputTarget);
		buf.writeLong(output);
		this.compair.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		this.outputTarget = buf.readLong();
		this.output = buf.readLong();
		this.compair.deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.outputTarget = nbt.getLong("outputTarget");
		
		this.compair.readFromNBT(nbt, "t");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		nbt.setLong("outputTarget", outputTarget);
		
		this.compair.writeToNBT(nbt, "t");
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // battery
		return false;
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {compair}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {compair}; }

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFusionKlystron(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFusionKlystron(player.inventory, this);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord,
					zCoord - 4,
					xCoord + 5,
					yCoord + 5,
					zCoord + 5
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
		return player.getDistanceSq(xCoord + 0.5, yCoord + 2.5, zCoord + 0.5) < 20 * 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("amount")) {
			this.outputTarget = data.getLong("amount");
			if(this.outputTarget < 0) this.outputTarget = 0;
			if(this.outputTarget > MAX_OUTPUT) this.outputTarget = MAX_OUTPUT;
		}
	}
}
