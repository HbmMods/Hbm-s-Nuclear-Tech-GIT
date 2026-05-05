package com.hbm.tileentity.machine.rbmk;

import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.Compat;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.common.Optional;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKCooler extends TileEntityRBMKBase implements IFluidStandardTransceiverMK2, SimpleComponent, CompatHandler.OCComponent {

	protected int timer = 0;
	private FluidTank[] tanks;
	protected TileEntityRBMKBase[] neighborCache = new TileEntityRBMKBase[25];

	public TileEntityRBMKCooler() {
		super();

		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.PERFLUOROMETHYL_COLD, 4_000);
		this.tanks[1] = new FluidTank(Fluids.PERFLUOROMETHYL, 4_000);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			if(timer <= 0) {
				timer = 60;
				
				for(int i = 0; i < 25; i++) {
					int x = xCoord - 2 + i / 5;
					int z = zCoord - 2 + i % 5;
					TileEntity tile = Compat.getTileStandard(worldObj, x, yCoord, z);
					if(tile instanceof TileEntityRBMKBase) {
						neighborCache[i] = (TileEntityRBMKBase) tile;
					} else {
						neighborCache[i] = null;
					}
				}
				
			} else {
				timer--;
			}
			
			if(tanks[0].getFill() >= 50 && tanks[1].getMaxFill() - tanks[1].getFill() >= 50) {
				tanks[0].setFill(tanks[0].getFill() - 50);
				tanks[1].setFill(tanks[1].getFill() + 50);
				
				for(TileEntityRBMKBase neighbor : neighborCache) {
					if(neighbor != null) {
						neighbor.heat -= 200;
						if(neighbor.heat < 20) neighbor.heat = 20;
					}
				}
			}

			this.trySubscribe(tanks[0].getTankType(), worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);
			
			if(this.tanks[1].getFill() > 0) for(DirPos pos : getOutputPos()) {
				this.tryProvide(this.tanks[1], worldObj, pos);
			}

		}

		super.updateEntity();
	}

	protected DirPos[] getOutputPos() {
		
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.rbmk_loader) {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y),
					new DirPos(this.xCoord + 1, this.yCoord - 1, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord - 1, this.yCoord - 1, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord - 1, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord, this.yCoord - 1, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord, Library.NEG_Y)
			};
		} else if(worldObj.getBlock(xCoord, yCoord - 2, zCoord) == ModBlocks.rbmk_loader) {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y),
					new DirPos(this.xCoord + 1, this.yCoord - 2, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord - 1, this.yCoord - 2, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord, this.yCoord - 2, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord, this.yCoord - 3, this.zCoord, Library.NEG_Y)
			};
		} else {
			return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, Library.POS_Y)
			};
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tanks[0].writeToNBT(nbt, "t0");
		tanks[1].writeToNBT(nbt, "t1");
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.tanks[0].serialize(buf);
		this.tanks[1].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.tanks[0].deserialize(buf);
		this.tanks[1].deserialize(buf);
	}

	@Override public ColumnType getConsoleType() { return ColumnType.COOLER; }

	@Override public FluidTank[] getAllTanks() { return tanks; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tanks[0]}; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {tanks[1]}; }

	//do some opencomputers stuff

	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_cooler";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoolant(Context context, Arguments args) {
		return new Object[] {
			tanks[0].getFill(), tanks[0].getMaxFill(),
			tanks[1].getFill(), tanks[1].getMaxFill()
		};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {
			heat,

			tanks[0].getFill(), tanks[0].getMaxFill(),
			tanks[1].getFill(), tanks[1].getMaxFill(),

			xCoord, yCoord, zCoord
		};
	}
}
