package com.hbm.tileentity.machine.rbmk;

import api.hbm.fluid.IFluidStandardSender;
import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.handler.CompatHandler;
import com.hbm.handler.neutron.NeutronStream;
import com.hbm.handler.neutron.RBMKNeutronHandler;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerRBMKOutgasser;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIRBMKOutgasser;
import com.hbm.inventory.recipes.OutgasserRecipes;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.DirPos;
import cpw.mods.fml.common.Optional;
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
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKOutgasser extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver, IFluidStandardSender, SimpleComponent, CompatHandler.OCComponent {

	public FluidTank gas;
	public double progress;
	public static final int duration = 10000;

	public TileEntityRBMKOutgasser() {
		super(2);
		gas = new FluidTank(Fluids.TRITIUM, 64000);
	}

	@Override
	public String getName() {
		return "container.rbmkOutgasser";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(!canProcess()) {
				this.progress = 0;
			}
			
			for(DirPos pos : getOutputPos()) {
				if(this.gas.getFill() > 0) this.sendFluid(gas, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
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
	public void receiveFlux(NeutronStream stream) {
		
		if(canProcess()) {

			double efficiency = Math.min(1 - stream.fluxRatio * 0.8, 1);

			progress += stream.fluxQuantity * efficiency * RBMKDials.getOutgasserMod(worldObj);
			
			if(progress > duration) {
				process();
				this.markDirty();
			}
		}
	}
	
	public boolean canProcess() {
		
		if(slots[0] == null)
			return false;
		
		Pair<ItemStack, FluidStack> output = OutgasserRecipes.getOutput(slots[0]);
		
		if(output == null)
			return false;
		
		FluidStack fluid = output.getValue();

		if(fluid != null) {
			if(gas.getTankType() != fluid.type && gas.getFill() > 0) return false;
			gas.setTankType(fluid.type);
			if(gas.getFill() + fluid.fill > gas.getMaxFill()) return false;
		}
		
		ItemStack out = output.getKey();
		
		if(slots[1] == null || out == null)
			return true;
		
		return slots[1].getItem() == out.getItem() && slots[1].getItemDamage() == out.getItemDamage() && slots[1].stackSize + out.stackSize <= slots[1].getMaxStackSize();
	}
	
	private void process() {
		
		Pair<ItemStack, FluidStack> output = OutgasserRecipes.getOutput(slots[0]);
		this.decrStackSize(0, 1);
		this.progress = 0;
		
		if(output.getValue() != null) {
			gas.setFill(gas.getFill() + output.getValue().fill);
		}
		
		ItemStack out = output.getKey();
		
		if(out != null) {
			if(slots[1] == null) {
				slots[1] = out.copy();
			} else {
				slots[1].stackSize += out.stackSize;
			}
		}
	}
	
	@Override
	public void onMelt(int reduce) {
		
		int count = 4 + worldObj.rand.nextInt(2);
		
		for(int i = 0; i < count; i++) {
			spawnDebris(DebrisType.BLANK);
		}
		
		super.onMelt(reduce);
	}

	@Override
	public RBMKNeutronHandler.RBMKType getRBMKType() {
		return RBMKNeutronHandler.RBMKType.OUTGASSER;
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.OUTGASSER;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger("gas", this.gas.getFill());
		data.setInteger("maxGas", this.gas.getMaxFill());
		data.setShort("type", (short)this.gas.getTankType().getID());
		data.setDouble("progress", this.progress);
		return data;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.progress = nbt.getDouble("progress");
		this.gas.readFromNBT(nbt, "gas");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setDouble("progress", this.progress);
		this.gas.writeToNBT(nbt, "gas");
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.gas.serialize(buf);
		buf.writeDouble(this.progress);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.gas.deserialize(buf);
		this.progress = buf.readDouble();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return OutgasserRecipes.getOutput(itemStack) != null && i == 0;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {0, 1};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] {gas};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {gas};
	}

	//do some opencomputers stuff
	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "rbmk_outgasser";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getGas(Context context, Arguments args) {
		return new Object[] {gas.getFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getGasMax(Context context, Arguments args) {
		return new Object[] {gas.getMaxFill()};
	}
	
		@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getGasType(Context context, Arguments args) {
		return new Object[] {gas.getTankType().getName()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getProgress(Context context, Arguments args) {
		return new Object[] {progress};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoordinates(Context context, Arguments args) {
		return new Object[] {xCoord, yCoord, zCoord};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[] {gas.getFill(), gas.getMaxFill(), progress, gas.getTankType().getID(), xCoord, yCoord, zCoord};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerRBMKOutgasser(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIRBMKOutgasser(player.inventory, this);
	}
}
