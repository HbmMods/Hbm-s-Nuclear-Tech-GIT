package com.hbm.tileentity.machine;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.fluid.IFluidStandardTransceiver;
import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.container.ContainerMachineStrandCaster;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineStrandCaster;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemScraps;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.NBTPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.fauxpointtwelve.DirPos;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

//god thank you bob for this base class
public class TileEntityMachineStrandCaster extends TileEntityFoundryCastingBase implements IGUIProvider, ICrucibleAcceptor, ISidedInventory, IFluidStandardTransceiver, INBTPacketReceiver, IInventory {

	public FluidTank water;
	public FluidTank steam;
	private long lastCastTick = 0;

	public String getName() {
		return "container.machineStrandCaster";
	}

	@Override
	public String getInventoryName() {
		return getName();
	}

	public TileEntityMachineStrandCaster() {
		super(7);
		water = new FluidTank(Fluids.WATER, 64_000);
		steam = new FluidTank(Fluids.SPENTSTEAM, 64_000);
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			if(this.lastType != this.type || this.lastAmount != this.amount) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				this.lastType = this.type;
				this.lastAmount = this.amount;
			}

            if (this.amount >= this.getCapacity()) {
                //In case of overfill problems, spit out the excess as scrap
                if (amount > getCapacity()) {
                    ItemStack scrap = ItemScraps.create(new Mats.MaterialStack(type, Math.max(amount - getCapacity(), 0)));
                    EntityItem item = new EntityItem(worldObj, xCoord + 0.5, yCoord + 2, zCoord + 0.5, scrap);
                    worldObj.spawnEntityInWorld(item);
                }
                this.amount = this.getCapacity();
			}

			if(this.amount == 0) {
				this.type = null;
			}

			this.updateConnections();

			ItemMold.Mold mold = this.getInstalledMold();

			if(canProcess()) {
				int minAmount = mold.getCost() * 9;
	
				// Makes it flush the buffers after 10 seconds of inactivity
				if(worldObj.getWorldTime() >= lastCastTick + 200) {
					minAmount = mold.getCost();
				}

				if(this.amount >= minAmount) {
					int itemsCasted = amount / mold.getCost();

					for(int j = 0; j < itemsCasted; j++) {
						this.amount -= mold.getCost();

						ItemStack out = mold.getOutput(type);

						for(int i = 1; i < 7; i++) {
							if(slots[i] == null) {
								slots[i] = out.copy();
								break;
							}

							if(slots[i].isItemEqual(out) && slots[i].stackSize + out.stackSize <= out.getMaxStackSize()) {
								slots[i].stackSize += out.stackSize;
								break;
							}

						}
					}
					markChanged();

					water.setFill(water.getFill() - getWaterRequired() * itemsCasted);
					steam.setFill(steam.getFill() + getWaterRequired() * itemsCasted);

					lastCastTick = worldObj.getWorldTime();
				}
			}
		}

		NBTTagCompound data = new NBTTagCompound();

		water.writeToNBT(data, "w");
		steam.writeToNBT(data, "s");

		this.networkPack(data, 150);

	}

	public boolean canProcess() {
		ItemMold.Mold mold = this.getInstalledMold();
		if(type != null && mold != null && mold.getOutput(type) != null) {
			for(int i = 1; i < 7; i++) {
				if(slots[i] == null || slots[i].isItemEqual(mold.getOutput(type)) && slots[i].stackSize + mold.getOutput(type).stackSize <= mold.getOutput(type).getMaxStackSize())
					return water.getFill() >= getWaterRequired() && steam.getFill() < steam.getMaxFill();

			}
		}

		return false;
	}

	public DirPos[] getFluidConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 2 - dir.offsetX,     yCoord, zCoord + rot.offsetZ * 2 - dir.offsetZ, rot),
				new DirPos(xCoord - rot.offsetX     - dir.offsetX,     yCoord, zCoord - rot.offsetZ     - dir.offsetZ, rot.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 2 - dir.offsetX * 5, yCoord, zCoord + rot.offsetZ * 2 - dir.offsetZ * 5, rot),
				new DirPos(xCoord - rot.offsetX     - dir.offsetX * 5, yCoord, zCoord - rot.offsetZ     - dir.offsetZ * 5, rot.getOpposite())
			};
	}

	public int[][] getMetalPourPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new int[][] {
			new int[] { xCoord + rot.offsetX - dir.offsetX, yCoord + 2, zCoord + rot.offsetZ - dir.offsetZ },
			new int[] { xCoord - dir.offsetX, yCoord + 2, zCoord - dir.offsetZ },
			new int[] { xCoord + rot.offsetX, yCoord + 2, zCoord + rot.offsetZ },
			new int[] { xCoord, yCoord + 2, zCoord }
			};
	}

	@Override
	public ItemMold.Mold getInstalledMold() {
		if(slots[0] == null)
			return null;

		if(slots[0].getItem() == ModItems.mold) {
			return ((ItemMold) slots[0].getItem()).getMold(slots[0]);
		}

		return null;
	}

	@Override
	public int getMoldSize() {
		return getInstalledMold().size;
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, Mats.MaterialStack stack) {

		if(side != ForgeDirection.UP)
			return false;
		for(int[] pos : getMetalPourPos()) {
			if(pos[0] == x && pos[1] == y && pos[2] == z) {
				return this.standardCheck(world, x, y, z, side, stack);
			}
		}
		return false;

	}

	@Override
	public boolean standardCheck(World world, int x, int y, int z, ForgeDirection side, Mats.MaterialStack stack) {
		if(this.type != null && this.type != stack.material) return false;
		int limit = this.getInstalledMold() != null ? this.getInstalledMold().getCost() * 9 : this.getCapacity();
		return !(this.amount >= limit || getInstalledMold() == null);
	}

	@Override
	public int getCapacity() {
		ItemMold.Mold mold = this.getInstalledMold();
		return mold == null ? 50000 : mold.getCost() * 10;
	}

	private int getWaterRequired() {
		return getInstalledMold() != null ? 5 * getInstalledMold().getCost() : 50;
	}

	private void updateConnections() {
		for(DirPos pos : getFluidConPos()) {
			this.trySubscribe(water.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.sendFluid(steam, worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
 	@Override
	public Mats.MaterialStack standardAdd(World world, int x, int y, int z, ForgeDirection side, Mats.MaterialStack stack) {
		this.type = stack.material;
        int limit = this.getInstalledMold() != null ? this.getInstalledMold().getCost() * 9 : this.getCapacity();
		if(stack.amount + this.amount <= limit) {
			this.amount += stack.amount;
			return null;
		}

		int required = limit - this.amount;
		this.amount = limit;

		stack.amount -= required;

		return stack;
	}
	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { steam };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { water };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { water, steam };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineStrandCaster(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineStrandCaster(player.inventory, this);
	}

	public void networkPack(NBTTagCompound nbt, int range) {
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		water.readFromNBT(nbt, "w");
		steam.readFromNBT(nbt, "s");

	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		water.writeToNBT(nbt, "w");
		steam.writeToNBT(nbt, "s");
		nbt.setLong("t", lastCastTick);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		water.readFromNBT(nbt, "w");
		steam.readFromNBT(nbt, "s");
		lastCastTick = nbt.getLong("t");
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0) return stack.getItem() == ModItems.mold;
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int meta) {
		return new int[] { 1, 2, 3, 4, 5, 6 };
	}

	public void markChanged() {
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side) {
		return this.isItemValidForSlot(slot, itemStack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return !this.isItemValidForSlot(slot, itemStack);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 7,
					yCoord,
					zCoord - 7,
					xCoord + 7,
					yCoord + 3,
					zCoord + 7);
		}
		return bb;
	}

	public boolean isLoaded = true;
	
	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	@Override
	public void onChunkUnload() {
		super.onChunkUnload();
		this.isLoaded = false;
	}
}
