package com.hbm.tileentity.machine.oil;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerMachineCoker;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCoker;
import com.hbm.inventory.recipes.CokerRecipes;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IHeatSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityMachineCoker extends TileEntityMachineBase implements IFluidStandardTransceiver, IGUIProvider, IFluidCopiable {

	public boolean wasOn;
	public int progress;
	public static int processTime = 20_000;
	
	public int heat;
	public static int maxHeat = 100_000;
	public static double diffusion = 0.25D;
	
	public FluidTank[] tanks;

	public TileEntityMachineCoker() {
		super(2);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.HEAVYOIL, 16_000);
		tanks[1] = new FluidTank(Fluids.OIL_COKER, 8_000);
	}

	@Override
	public String getName() {
		return "container.machineCoker";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.tryPullHeat();
			this.tanks[0].setType(0, slots);

			if(worldObj.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				}
			}
			
			this.wasOn = false;
			
			if(canProcess()) {
				int burn = heat / 100;
						
				if(burn > 0) {
					this.wasOn = true;
					this.progress += burn;
					this.heat -= burn;
					
					if(progress >= processTime) {
						this.markChanged();
						progress -= this.processTime;
						
						Triplet<Integer, ItemStack, FluidStack> recipe = CokerRecipes.getOutput(tanks[0].getTankType());
						int fillReq = recipe.getX();
						ItemStack output = recipe.getY();
						FluidStack byproduct = recipe.getZ();
						
						if(output != null) {
							if(slots[1] == null) {
								slots[1] = output.copy();
							} else {
								slots[1].stackSize += output.stackSize;
							}
						}
						
						if(byproduct != null) {
							tanks[1].setFill(tanks[1].getFill() + byproduct.fill);
						}
						
						tanks[0].setFill(tanks[0].getFill() - fillReq);
					}
				}

				if(wasOn && worldObj.getTotalWorldTime() % 5 == 0) PollutionHandler.incrementPollution(worldObj, xCoord, yCoord, zCoord, PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * 5);
			}
			
			for(DirPos pos : getConPos()) {
				if(this.tanks[1].getFill() > 0) this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			this.networkPackNT(25);
		} else {
			
			if(this.wasOn) {

				if(worldObj.getTotalWorldTime() % 2 == 0) {
					NBTTagCompound fx = new NBTTagCompound();
					fx.setString("type", "tower");
					fx.setFloat("lift", 10F);
					fx.setFloat("base", 0.75F);
					fx.setFloat("max", 3F);
					fx.setInteger("life", 200 + worldObj.rand.nextInt(50));
					fx.setInteger("color",0x404040);
					fx.setDouble("posX", xCoord + 0.5);
					fx.setDouble("posY", yCoord + 22);
					fx.setDouble("posZ", zCoord + 0.5);
					MainRegistry.proxy.effectNT(fx);
				}
			}
		}
	}



	public DirPos[] getConPos() {
		
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}
	
	public boolean canProcess() {
		Triplet<Integer, ItemStack, FluidStack> recipe = CokerRecipes.getOutput(tanks[0].getTankType());
		
		if(recipe == null) return false;
		
		int fillReq = recipe.getX();
		ItemStack output = recipe.getY();
		FluidStack byproduct = recipe.getZ();
		
		if(byproduct != null) tanks[1].setTankType(byproduct.type);
		
		if(tanks[0].getFill() < fillReq) return false;
		if(byproduct != null && byproduct.fill + tanks[1].getFill() > tanks[1].getMaxFill()) return false;
		
		if(output != null && slots[1] != null) {
			if(output.getItem() != slots[1].getItem()) return false;
			if(output.getItemDamage() != slots[1].getItemDamage()) return false;
			if(output.stackSize + slots[1].stackSize > output.getMaxStackSize()) return false;
		}
		
		return true;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(this.wasOn);
		buf.writeInt(this.heat);
		buf.writeInt(this.progress);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.wasOn = buf.readBoolean();
		this.heat = buf.readInt();
		this.progress = buf.readInt();
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
	}
	
	protected void tryPullHeat() {
		
		if(this.heat >= this.maxHeat) return;
		
		TileEntity con = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
		
		if(con instanceof IHeatSource) {
			IHeatSource source = (IHeatSource) con;
			int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > this.maxHeat)
					this.heat = this.maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1 };
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.tanks[0].readFromNBT(nbt, "t0");
		this.tanks[1].readFromNBT(nbt, "t1");
		this.progress = nbt.getInteger("prog");
		this.heat = nbt.getInteger("heat");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.tanks[0].writeToNBT(nbt, "t0");
		this.tanks[1].writeToNBT(nbt, "t1");
		nbt.setInteger("prog", progress);
		nbt.setInteger("heat", heat);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[1] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0] };
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 23,
					zCoord + 3
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
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineCoker(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCoker(player.inventory, this);
	}
}
