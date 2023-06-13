package com.hbm.tileentity.machine.rbmk;

import api.hbm.fluid.IFluidStandardTransceiver;
import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.container.ContainerRBMKHeater;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.inventory.gui.GUIRBMKHeater;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.fauxpointtwelve.DirPos;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public class TileEntityRBMKHeater extends TileEntityRBMKSlottedBase implements IFluidAcceptor, IFluidSource, IFluidStandardTransceiver, SimpleComponent {

	public FluidTank feed;
	public FluidTank steam;
	public List<IFluidAcceptor> list = new ArrayList();
	
	public TileEntityRBMKHeater() {
		super(1);
		this.feed = new FluidTank(Fluids.COOLANT, 16_000, 0);
		this.steam = new FluidTank(Fluids.COOLANT_HOT, 16_000, 1);
	}

	@Override
	public String getName() {
		return "container.rbmkHeater";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			feed.setType(0, slots);
			
			feed.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			steam.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			if(feed.getTankType().hasTrait(FT_Heatable.class)) {
				FT_Heatable trait = feed.getTankType().getTrait(FT_Heatable.class);
				HeatingStep step = trait.getFirstStep();
				steam.setTankType(step.typeProduced);
				double tempRange = this.heat - steam.getTankType().temperature;
				
				if(tempRange > 0) {
					double TU_PER_DEGREE = 2_000D; //based on 1mB of water absorbing 200 TU as well as 0.1°C from an RBMK column
					int inputOps = feed.getFill() / step.amountReq;
					int outputOps = (steam.getMaxFill() - steam.getFill()) / step.amountProduced;
					int tempOps = (int) Math.floor((tempRange * TU_PER_DEGREE) / step.heatReq);
					int ops = Math.min(inputOps, Math.min(outputOps, tempOps));

					feed.setFill(feed.getFill() - step.amountReq * ops);
					steam.setFill(steam.getFill() + step.amountProduced * ops);
					this.heat -= (step.heatReq * ops / TU_PER_DEGREE) * trait.getEfficiency(HeatingType.HEATEXCHANGER);
				}
				
			} else {
				steam.setTankType(Fluids.NONE);
			}
			
			fillFluidInit(steam.getTankType());
			
			this.trySubscribe(feed.getTankType(), worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);
			for(DirPos pos : getOutputPos()) {
				if(this.steam.getFill() > 0) this.sendFluid(steam, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
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
	public void fillFluidInit(FluidType type) {

		fillFluid(this.xCoord, this.yCoord + RBMKDials.getColumnHeight(worldObj) + 1, this.zCoord, getTact(), type);
		
		if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord, getTact(), type);
		}
		
		if(worldObj.getBlock(xCoord, yCoord - 2, zCoord) == ModBlocks.rbmk_loader) {

			fillFluid(this.xCoord + 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord - 2, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 2, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
			fillFluid(this.xCoord, this.yCoord - 3, this.zCoord, getTact(), type);
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	@Deprecated //why are we still doing this?
	public boolean getTact() { return worldObj.getTotalWorldTime() % 2 == 0; }

	@Override
	public void setFluidFill(int i, FluidType type) {
		
		if(type == feed.getTankType())
			feed.setFill(i);
		else if(type == steam.getTankType())
			steam.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		if(type == feed.getTankType())
			return feed.getFill();
		else if(type == steam.getTankType())
			return steam.getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		
		if(type == feed.getTankType())
			return feed.getMaxFill();
		else if(type == steam.getTankType())
			return steam.getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {

		if(index == 0)
			feed.setFill(fill);
		else if(index == 1)
			steam.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {

		if(index == 0)
			feed.setTankType(type);
		else if(index == 1)
			steam.setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		feed.readFromNBT(nbt, "feed");
		steam.readFromNBT(nbt, "steam");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		feed.writeToNBT(nbt, "feed");
		steam.writeToNBT(nbt, "steam");
	}
	
	@Override
	public void onMelt(int reduce) {
		
		int count = 1 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.HEATEX;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("water", this.feed.getFill());
		data.setInteger("maxWater", this.feed.getMaxFill());
		data.setInteger("steam", this.steam.getFill());
		data.setInteger("maxSteam", this.steam.getMaxFill());
		data.setShort("type", (short)this.feed.getTankType().getID());
		data.setShort("hottype", (short)this.steam.getTankType().getID());
		return data;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {feed, steam};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {steam};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {feed};
	}

	//opencomputers stuff

	@Override
	public String getComponentName() {
		return "rbmk_heater";
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getHeat(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFill(Context context, Arguments args) {
		return new Object[] {feed.getFill()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFillMax(Context context, Arguments args) {
		return new Object[] {feed.getMaxFill()};
	}
	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getExport(Context context, Arguments args) {
		return new Object[] {steam.getFill()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getExportMax(Context context, Arguments args) {
		return new Object[] {steam.getMaxFill()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFillType(Context context, Arguments args) {
		return new Object[] {feed.getTankType().getID()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getExportType(Context context, Arguments args) {
		return new Object[] {steam.getTankType().getID()};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {heat, feed.getFill(), feed.getMaxFill(), steam.getFill(), steam.getMaxFill(), feed.getTankType().getID(), steam.getTankType().getID(), xCoord, yCoord, zCoord};
	}

	@Callback(direct = true, limit = 8)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKHeater(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKHeater(player.inventory, this);
	}
}
