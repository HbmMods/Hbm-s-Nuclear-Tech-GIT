package com.hbm.tileentity.machine;

import com.hbm.handler.CompatHandler;
import com.hbm.inventory.container.ContainerCoreInjector;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUICoreInjector;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreInjector extends TileEntityMachineBase implements IFluidStandardReceiver, SimpleComponent, IGUIProvider, CompatHandler.OCComponent {
	
	public FluidTank[] tanks;
	public static final int range = 15;
	public int beam;

	public TileEntityCoreInjector() {
		super(4);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.DEUTERIUM, 128000);
		tanks[1] = new FluidTank(Fluids.TRITIUM, 128000);
	}

	@Override
	public String getName() {
		return "container.dfcInjector";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.subscribeToAllAround(tanks[0].getTankType(), this);
			this.subscribeToAllAround(tanks[1].getTankType(), this);

			tanks[0].setType(0, 1, slots);
			tanks[1].setType(2, 3, slots);
			
			beam = 0;
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
			for(int i = 1; i <= range; i++) {

				int x = xCoord + dir.offsetX * i;
				int y = yCoord + dir.offsetY * i;
				int z = zCoord + dir.offsetZ * i;
				
				TileEntity te = worldObj.getTileEntity(x, y, z);
				
				if(te instanceof TileEntityCore) {
					
					TileEntityCore core = (TileEntityCore)te;
					
					for(int t = 0; t < 2; t++) {
						
						if(core.tanks[t].getTankType() == tanks[t].getTankType()) {
							
							int f = Math.min(tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());

							tanks[t].setFill(tanks[t].getFill() - f);
							core.tanks[t].setFill(core.tanks[t].getFill() + f);
							core.markDirty();
							
						} else if(core.tanks[t].getFill() == 0) {
							
							core.tanks[t].setTankType(tanks[t].getTankType());
							int f = Math.min(tanks[t].getFill(), core.tanks[t].getMaxFill() - core.tanks[t].getFill());

							tanks[t].setFill(tanks[t].getFill() - f);
							core.tanks[t].setFill(core.tanks[t].getFill() + f);
							core.markDirty();
						}
					}
					
					beam = i;
					break;
				}
				
				if(!worldObj.getBlock(x, y, z).isAir(worldObj, x, y, z))
					break;
			}
			
			this.markDirty();

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("beam", beam);
			tanks[0].writeToNBT(data, "t0");
			tanks[1].writeToNBT(data, "t1");
			this.networkPack(data, 250);
		}
	}

	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);
		beam = data.getInteger("beam");
		tanks[0].readFromNBT(data, "t0");
		tanks[1].readFromNBT(data, "t1");
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

		tanks[0].readFromNBT(nbt, "fuel1");
		tanks[1].readFromNBT(nbt, "fuel2");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tanks[0].writeToNBT(nbt, "fuel1");
		tanks[1].writeToNBT(nbt, "fuel2");
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0], tanks[1]};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}
	
	// do some opencomputer stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "dfc_injector";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFuel(Context context, Arguments args) {
		return new Object[] {tanks[0].getFill(), tanks[1].getFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTypes(Context context, Arguments args) {
		return new Object[] {tanks[0].getTankType().getName(), tanks[1].getTankType().getName()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {tanks[0].getFill(), tanks[0].getTankType().getName(), tanks[1].getFill(), tanks[1].getTankType().getName()};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCoreInjector(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICoreInjector(player.inventory, this);
	}
}
