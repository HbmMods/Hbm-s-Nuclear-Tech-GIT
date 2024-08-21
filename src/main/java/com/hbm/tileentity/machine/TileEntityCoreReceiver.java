package com.hbm.tileentity.machine;

import com.hbm.handler.CompatHandler;
import com.hbm.inventory.container.ContainerCoreReceiver;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICoreReceiver;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;

import api.hbm.block.ILaserable;
import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreReceiver extends TileEntityMachineBase implements IEnergyProviderMK2, ILaserable, IFluidStandardReceiver, SimpleComponent, IGUIProvider, IInfoProviderEC, CompatHandler.OCComponent {
	
	public long power;
	public long joules;
	public FluidTank tank;

	public TileEntityCoreReceiver() {
		super(0);
		tank = new FluidTank(Fluids.CRYOGEL, 64000);
	}

	@Override
	public String getName() {
		return "container.dfcReceiver";
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			this.subscribeToAllAround(tank.getTankType(), this);
			
			power = joules * 5000;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.tryProvide(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			
			if(joules > 0) {

				if(tank.getFill() >= 20) {
					tank.setFill(tank.getFill() - 20);
				} else {
					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.flowing_lava);
					return;
				}
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setLong("joules", joules);
			tank.writeToNBT(data, "t");
			this.networkPack(data, 50);
			
			joules = 0;
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);
		joules = data.getLong("joules");
		tank.readFromNBT(data, "t");
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public long getMaxPower() {
		return this.power;
	}

	@Override
	public void addEnergy(World world, int x, int y, int z, long energy, ForgeDirection dir) {
		
		//only accept lasers from the front
		if(dir.getOpposite().ordinal() == this.getBlockMetadata()) {
			joules += energy;
		} else {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.createExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 2.5F, true);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		joules = nbt.getLong("joules");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		nbt.setLong("joules", joules);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "dfc_receiver";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {joules, getPower()}; //literally only doing this for the consistency between components
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCryogel(Context context, Arguments args) {
		return new Object[] {tank.getFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {joules, getPower(), tank.getFill()};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCoreReceiver(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICoreReceiver(player.inventory, this);
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, joules > 0 ? 20 : 0);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, joules * 5000);
	}
}
