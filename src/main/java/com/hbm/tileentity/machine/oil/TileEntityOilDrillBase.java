package com.hbm.tileentity.machine.oil;

import java.util.HashMap;
import java.util.HashSet;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.tileentity.*;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple;
import com.hbm.util.Tuple.Triplet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityOilDrillBase extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiver, IConfigurableMachine, IPersistentNBT, IGUIProvider, IUpgradeInfoProvider, IFluidCopiable {

	public int indicator = 0;

	public long power;

	public FluidTank[] tanks;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT();

	public TileEntityOilDrillBase() {
		super(8);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.OIL, 64_000);
		tanks[1] = new FluidTank(Fluids.GAS, 64_000);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].readFromNBT(nbt, "t" + i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setLong("power", power);
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].writeToNBT(nbt, "t" + i);
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {

		boolean empty = power == 0;
		for(FluidTank tank : tanks) if(tank.getFill() > 0) empty = false;

		if(!empty) {
			nbt.setLong("power", power);
			for(int i = 0; i < this.tanks.length; i++) {
				this.tanks[i].writeToNBT(nbt, "t" + i);
			}
		}
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		for(int i = 0; i < this.tanks.length; i++)
			this.tanks[i].readFromNBT(nbt, "t" + i);
	}

	public int speedLevel;
	public int energyLevel;
	public int overLevel;

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

			this.updateConnections();

			this.tanks[0].unloadTank(1, 2, slots);
			this.tanks[1].unloadTank(3, 4, slots);

			upgradeManager.checkSlots(this, slots, 5, 7);
			this.speedLevel = upgradeManager.getLevel(UpgradeType.SPEED);
			this.energyLevel = upgradeManager.getLevel(UpgradeType.POWER);
			this.overLevel = upgradeManager.getLevel(UpgradeType.OVERDRIVE) + 1;
			int abLevel = upgradeManager.getLevel(UpgradeType.AFTERBURN);

			int toBurn = Math.min(tanks[1].getFill(), abLevel * 10);

			if (toBurn > 0) {
				tanks[1].setFill(tanks[1].getFill() - toBurn);
				this.power += toBurn * 5;

				if (this.power > this.getMaxPower())
					this.power = this.getMaxPower();
			}

			power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());

			for (DirPos pos : getConPos()) {
				if (tanks[0].getFill() > 0)
					this.sendFluid(tanks[0], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				if (tanks[1].getFill() > 0)
					this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}

			if (this.power >= this.getPowerReqEff() && this.tanks[0].getFill() < this.tanks[0].getMaxFill() && this.tanks[1].getFill() < this.tanks[1].getMaxFill()) {

				this.power -= this.getPowerReqEff();

				if (worldObj.getTotalWorldTime() % getDelayEff() == 0) {
					this.indicator = 0;

					for (int y = yCoord - 1; y >= getDrillDepth(); y--) {

						if (worldObj.getBlock(xCoord, y, zCoord) != ModBlocks.oil_pipe) {

							if (trySuck(y)) {
								break;
							} else {
								tryDrill(y);
								break;
							}
						}

						if (y == getDrillDepth())
							this.indicator = 1;
					}
				}

			} else {
				this.indicator = 2;
			}

			this.networkPackNT(25);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);

		buf.writeLong(this.power);
		buf.writeInt(this.indicator);
		for (FluidTank tank : tanks) tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);

		this.power = buf.readLong();
		this.indicator = buf.readInt();
		for (FluidTank tank : tanks) tank.deserialize(buf);
	}

	public boolean canPump() {
		return true;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);

		if(stack != null && i >= 5 && i <= 7 && stack.getItem() instanceof ItemMachineUpgrade)
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
	}

	public int getPowerReqEff() {
		int req = this.getPowerReq();
		return (req + (req / 4 * this.speedLevel) - (req / 4 * this.energyLevel)) * this.overLevel;
	}

	public int getDelayEff() {
		int delay = getDelay();
		return Math.max((delay - (delay / 4 * this.speedLevel) + (delay / 10 * this.energyLevel)) / this.overLevel, 1);
	}

	public abstract int getPowerReq();
	public abstract int getDelay();

	public void tryDrill(int y) {
		Block b = worldObj.getBlock(xCoord, y, zCoord);

		if(b.getExplosionResistance(null) < 1000) {
			onDrill(y);
			worldObj.setBlock(xCoord, y, zCoord, ModBlocks.oil_pipe);
		} else {
			this.indicator = 2;
		}
	}

	public void onDrill(int y) { }

	public int getDrillDepth() {
		return 5;
	}

	public boolean trySuck(int y) {

		Block b = worldObj.getBlock(xCoord, y, zCoord);

		if(!canSuckBlock(b))
			return false;

		if(!this.canPump())
			return true;

		trace.clear();

		return suckRec(xCoord, y, zCoord, 0);
	}

	public boolean canSuckBlock(Block b) {
		return b == ModBlocks.ore_oil || b == ModBlocks.ore_oil_empty;
	}

	protected HashSet<Tuple.Triplet<Integer, Integer, Integer>> trace = new HashSet();

	public boolean suckRec(int x, int y, int z, int layer) {

		Triplet<Integer, Integer, Integer> pos = new Triplet(x, y, z);

		if(trace.contains(pos))
			return false;

		trace.add(pos);

		if(layer > 64)
			return false;

		Block b = worldObj.getBlock(x, y, z);

		if(b == ModBlocks.ore_oil || b == ModBlocks.ore_bedrock_oil) {
			doSuck(x, y, z);
			return true;
		}

		if(b == ModBlocks.ore_oil_empty) {
			ForgeDirection[] dirs = BobMathUtil.getShuffledDirs();

			for(ForgeDirection dir : dirs) {
				if(suckRec(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, layer + 1))
					return true;
			}
		}

		return false;
	}

	public void doSuck(int x, int y, int z) {

		if(worldObj.getBlock(x, y, z) == ModBlocks.ore_oil) {
			onSuck(x, y, z);
		}
	}

	public abstract void onSuck(int x, int y, int z);

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
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
	public FluidTank[] getSendingTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[0];
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	public abstract DirPos[] getConPos();

	protected void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}

	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE || type == UpgradeType.AFTERBURN;
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.POWER, 3);
		upgrades.put(UpgradeType.AFTERBURN, 3);
		upgrades.put(UpgradeType.OVERDRIVE, 3);
		return upgrades;
	}

	@Override
	public FluidTank getTankToPaste() {
		return null;
	}
}
