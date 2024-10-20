package com.hbm.tileentity.machine;

import com.hbm.handler.CompatHandler;
import com.hbm.inventory.container.ContainerICF;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.inventory.gui.GUIICF;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemICFPellet;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityICF extends TileEntityMachineBase implements IGUIProvider, IFluidStandardTransceiver, IInfoProviderEC, SimpleComponent, CompatHandler.OCComponent, IFluidCopiable {
	
	public long laser;
	public long maxLaser;
	public long heat;
	public static final long maxHeat = 1_000_000_000_000L;
	public long heatup;
	public int consumption;
	public int output;
	
	public FluidTank[] tanks;

	public TileEntityICF() {
		super(12);
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(Fluids.SODIUM, 512_000);
		this.tanks[1] = new FluidTank(Fluids.SODIUM_HOT, 512_000);
		this.tanks[2] = new FluidTank(Fluids.STELLAR_FLUX, 24_000);
	}

	@Override
	public String getName() {
		return "container.machineICF";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			tanks[0].setType(11, slots);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			boolean markDirty = false;
			
			//eject depleted pellet
			if(slots[5] != null && slots[5].getItem() == ModItems.icf_pellet_depleted) {
				for(int i = 6; i < 11; i++) {
					if(slots[i] == null) {
						slots[i] = slots[5].copy();
						slots[5] = null;
						markDirty = true;
						break;
					}
				}
			}
			
			//insert fresh pellet
			if(slots[5] == null) {
				for(int i = 0; i < 5; i++) {
					if(slots[i] != null && slots[i].getItem() == ModItems.icf_pellet) {
						slots[5] = slots[i].copy();
						slots[i] = null;
						markDirty = true;
						break;
					}
				}
			}
			
			this.heatup = 0;
			
			if(slots[5] != null && slots[5].getItem() == ModItems.icf_pellet) {
				if(ItemICFPellet.getFusingDifficulty(slots[5]) <=  this.laser) {
					this.heatup = ItemICFPellet.react(slots[5], this.laser);
					this.heat += heatup;
					if(ItemICFPellet.getDepletion(slots[5]) >= ItemICFPellet.getMaxDepletion(slots[5])) {
						slots[5] = new ItemStack(ModItems.icf_pellet_depleted);
						markDirty = true;
					}
					
					tanks[2].setFill(tanks[2].getFill() + (int) Math.ceil(this.heat * 2.5D / this.maxHeat));
					if(tanks[2].getFill() > tanks[2].getMaxFill()) tanks[2].setFill(tanks[2].getMaxFill());
					
					NBTTagCompound dPart = new NBTTagCompound();
					dPart.setString("type", "hadron");
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(dPart, xCoord + 0.5, yCoord + 3.5, zCoord + 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 25));
				}
			}
			
			if(heatup == 0) {
				this.heat += this.laser * 0.25D;
			}

			this.consumption = 0;
			this.output = 0;
			
			if(tanks[0].getTankType().hasTrait(FT_Heatable.class)) {
				FT_Heatable trait = tanks[0].getTankType().getTrait(FT_Heatable.class);
				HeatingStep step = trait.getFirstStep();
				tanks[1].setTankType(step.typeProduced);
				
				int coolingCycles = tanks[0].getFill() / step.amountReq;
				int heatingCycles = (tanks[1].getMaxFill() - tanks[1].getFill()) / step.amountProduced;
				int heatCycles = (int) Math.min(this.heat / 4D / step.heatReq * trait.getEfficiency(HeatingType.ICF), this.heat / step.heatReq); //25% cooling per tick
				int cycles = Math.min(coolingCycles, Math.min(heatingCycles, heatCycles));
				
				tanks[0].setFill(tanks[0].getFill() - step.amountReq * cycles);
				tanks[1].setFill(tanks[1].getFill() + step.amountProduced * cycles);
				this.heat -= step.heatReq * cycles;

				this.consumption = step.amountReq * cycles;
				this.output = step.amountProduced * cycles;
			}
			
			for(DirPos pos : getConPos()) {
				this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.sendFluid(tanks[2], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			this.heat *= 0.999D;
			if(this.heat > this.maxHeat) this.heat = this.maxHeat;
			if(markDirty) this.markDirty();
			
			this.networkPackNT(150);
			this.laser = 0;
			this.maxLaser = 0;
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord, yCoord + 6, zCoord, Library.POS_Y),
				new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
				new DirPos(xCoord + dir.offsetX * 3 + rot.offsetX * 6, yCoord + 3, zCoord + dir.offsetZ * 3 + rot.offsetZ * 6, dir),
				new DirPos(xCoord + dir.offsetX * 3 - rot.offsetX * 6, yCoord + 3, zCoord + dir.offsetZ * 3 - rot.offsetZ * 6, dir),
				new DirPos(xCoord - dir.offsetX * 3 + rot.offsetX * 6, yCoord + 3, zCoord - dir.offsetZ * 3 + rot.offsetZ * 6, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 3 - rot.offsetX * 6, yCoord + 3, zCoord - dir.offsetZ * 3 - rot.offsetZ * 6, dir.getOpposite())
		};
	}

	@Override public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(laser);
		buf.writeLong(maxLaser);
		buf.writeLong(heat);
		for(int i = 0; i < 3; i++) tanks[i].serialize(buf);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.laser = buf.readLong();
		this.maxLaser = buf.readLong();
		this.heat = buf.readLong();
		for(int i = 0; i < 3; i++) tanks[i].deserialize(buf);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot < 5 && stack.getItem() == ModItems.icf_pellet;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot > 5;
	}

	public static final int[] io = new int[] {0, 1, 2, 3, 4, 6, 7, 8, 9, 10};

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return io;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		for(int i = 0; i < 3; i++) tanks[i].readFromNBT(nbt, "t" + i);
		
		this.heat = nbt.getLong("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		for(int i = 0; i < 3; i++) tanks[i].writeToNBT(nbt, "t" + i);
		
		nbt.setLong("heat", heat);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 256;
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 8,
					yCoord,
					zCoord + 0.5 - 8,
					xCoord + 0.5 + 9,
					yCoord + 0.5 + 5,
					zCoord + 0.5 + 9
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
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1], tanks[2]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerICF(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIICF(player.inventory, this);
	}
	
	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, heatup > 0);
		data.setLong(CompatEnergyControl.L_CAPACITY_TU, this.maxHeat);
		data.setLong(CompatEnergyControl.L_ENERGY_TU, this.heat);
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, this.consumption);
		data.setDouble(CompatEnergyControl.D_OUTPUT_MB, this.output);
	}

	//OC stuff

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_icf_reactor";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {this.heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeatingRate(Context context, Arguments args) {
		return new Object[] {this.heatup};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getMaxHeat(Context context, Arguments args) {
		return new Object[] {maxHeat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPower(Context context, Arguments args) {
		return new Object[] {this.laser};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluid(Context context, Arguments args) {
		return new Object[] {
				tanks[0].getFill(), tanks[0].getMaxFill(), tanks[0].getTankType().getUnlocalizedName(),
				tanks[1].getFill(), tanks[1].getMaxFill(), tanks[1].getTankType().getUnlocalizedName(),
				tanks[2].getFill(), tanks[2].getMaxFill()
		};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getPelletStats(Context context, Arguments args) {
		return new Object[] {
				ItemICFPellet.getDepletion(slots[5]),
				ItemICFPellet.getMaxDepletion(slots[5]),
				ItemICFPellet.getFusingDifficulty(slots[5]),
				ItemICFPellet.getType(slots[5], true).name(),
				ItemICFPellet.getType(slots[5], false).name()
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
				"getHeat",
				"getHeatingRate",
				"getMaxHeat",
				"getPower",
				"getFluid",
				"getPelletStats"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch (method) {
			case ("getHeat"):
				return getHeat(context, args);
			case ("getHeatingRate"):
				return getHeatingRate(context, args);
			case ("getMaxHeat"):
				return getMaxHeat(context, args);
			case ("getPower"):
				return getPower(context, args);
			case ("getFluid"):
				return getFluid(context, args);
			case ("getPelletStats"):
				return getPelletStats(context, args);
		}
		throw new NoSuchMethodException();
	}
}
