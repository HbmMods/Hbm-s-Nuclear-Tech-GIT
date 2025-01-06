package com.hbm.tileentity.machine.rbmk;

import api.hbm.fluid.IFluidStandardReceiver;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import cpw.mods.fml.common.Optional;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKCooler extends TileEntityRBMKBase implements IFluidStandardReceiver, SimpleComponent, CompatHandler.OCComponent {

	private FluidTank tank;
	private int lastCooled;

	public TileEntityRBMKCooler() {
		super();

		this.tank = new FluidTank(Fluids.CRYOGEL, 8_000);
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

			if (this.worldObj.getTotalWorldTime() % 20 == 0)
				this.trySubscribe(tank.getTankType(), worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);

			if ((int) (this.heat) > 750) {

				int heatProvided = (int) (this.heat - 750D);
				int cooling = Math.min(heatProvided, tank.getFill());

				this.heat -= cooling;
				this.tank.setFill(this.tank.getFill() - cooling);

				this.lastCooled = cooling;

				if (lastCooled > 0) {
					List<Entity> entities = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 4, zCoord, xCoord + 1, yCoord + 8, zCoord + 1));

					for (Entity e : entities) {
						e.setFire(5);
						e.attackEntityFrom(DamageSource.inFire, 10);
					}
				}
			} else {
				this.lastCooled = 0;
			}

		} else {

			if (this.lastCooled > 100) {
				for (int i = 0; i < 2; i++) {
					worldObj.spawnParticle("flame", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);
					worldObj.spawnParticle("smoke", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);
				}

				if (worldObj.rand.nextInt(20) == 0)
					worldObj.spawnParticle("lava", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, 0, 0.0, 0);
			} else if (this.lastCooled > 50) {
				for (int i = 0; i < 2; i++) {
					worldObj.spawnParticle("cloud", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, worldObj.rand.nextGaussian() * 0.05, 0.2, worldObj.rand.nextGaussian() * 0.05);
				}
			} else if (this.lastCooled > 0) {

				if (worldObj.getTotalWorldTime() % 2 == 0)
					worldObj.spawnParticle("cloud", xCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, yCoord + 4.5, zCoord + 0.25 + worldObj.rand.nextDouble() * 0.5, 0, 0.2, 0);

			}
		}

		super.updateEntity();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		tank.readFromNBT(nbt, "cryo");
		this.lastCooled = nbt.getInteger("cooled");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tank.writeToNBT(nbt, "cryo");
		nbt.setInteger("cooled", this.lastCooled);
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.tank.serialize(buf);
		buf.writeInt(this.lastCooled);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.tank.deserialize(buf);
		this.lastCooled = buf.readInt();
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.COOLER;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[]{tank};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[]{tank};
	}

	//do some opencomputers stuff

	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_cooler";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[]{heat};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryo(Context context, Arguments args) {
		return new Object[]{tank.getFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryoMax(Context context, Arguments args) {
		return new Object[]{tank.getMaxFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[]{heat, tank.getFill(), tank.getMaxFill(), xCoord, yCoord, zCoord};
	}
}
