package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerMachineChemicalFactory;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineChemicalFactory;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.module.ModuleMachineChemplant;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.util.i18n.I18nUtil;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineChemicalFactory extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiverMK2, IUpgradeInfoProvider, IControlReceiver, IGUIProvider {

	public FluidTank[] allTanks;
	public FluidTank[] inputTanks;
	public FluidTank[] outputTanks;
	
	public long power;
	public long maxPower = 10_000_000;
	public boolean didProcess = false;
	
	public boolean frame = false;
	public int anim;
	public int prevAnim;

	public ModuleMachineChemplant[] chemplantModule;
	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT(this);

	public TileEntityMachineChemicalFactory() {
		super(32);
		
		this.inputTanks = new FluidTank[12];
		this.outputTanks = new FluidTank[12];
		for(int i = 0; i < 12; i++) {
			this.inputTanks[i] = new FluidTank(Fluids.NONE, 24_000);
			this.outputTanks[i] = new FluidTank(Fluids.NONE, 24_000);
		}
		
		this.allTanks = new FluidTank[this.inputTanks.length + this.outputTanks.length];
		for(int i = 0; i < inputTanks.length; i++) this.allTanks[i] = this.inputTanks[i];
		for(int i = 0; i < outputTanks.length; i++) this.allTanks[i + this.inputTanks.length] = this.outputTanks[i];
		
		this.chemplantModule = new ModuleMachineChemplant[4];
		for(int i = 0; i < 4; i++) this.chemplantModule[i] = new ModuleMachineChemplant(i, this, slots)
				.itemInput(5 + i * 7, 6 + i * 7, 7 + i * 7)
				.itemOutput(8 + i * 7, 9 + i * 7, 10 + i * 7)
				.fluidInput(inputTanks[0 + i * 3], inputTanks[1 + i * 3], inputTanks[2 + i * 3])
				.fluidOutput(outputTanks[0 + i * 3], outputTanks[1 + i * 3], outputTanks[2 + i * 3]);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		if(i >= 8 && i <= 10) return true;
		if(i >= 12 && i <= 14) return true;
		if(i >= 19 && i <= 21) return true;
		if(i >= 26 && i <= 28) return true;
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // battery
		if(slot >= 1 && slot <= 3 && stack.getItem() instanceof ItemMachineUpgrade) return true; // upgrades
		for(int i = 0; i < 4; i++) if(this.chemplantModule[i].isItemValid(slot, stack)) return true; // recipe input crap
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {
				5, 6, 7, 8, 9, 10,
				12, 13, 14, 15, 16, 17,
				19, 20, 21, 22, 23, 24,
				26, 27, 28, 29, 30, 31
		};
	}

	@Override
	public String getName() {
		return "container.machineChemicalFactory";
	}

	@Override
	public void updateEntity() {
		
		if(maxPower <= 0) this.maxPower = 10_000_000;
		
		if(!worldObj.isRemote) {
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			upgradeManager.checkSlots(slots, 1, 3);

			inputTanks[0].loadTank(10, 13, slots);
			inputTanks[1].loadTank(11, 14, slots);
			inputTanks[2].loadTank(12, 15, slots);

			outputTanks[0].unloadTank(16, 19, slots);
			outputTanks[1].unloadTank(17, 20, slots);
			outputTanks[2].unloadTank(18, 21, slots);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos);
				for(FluidTank tank : inputTanks) if(tank.getTankType() != Fluids.NONE) this.trySubscribe(tank.getTankType(), worldObj, pos);
				for(FluidTank tank : outputTanks) if(tank.getFill() > 0) this.tryProvide(tank, worldObj, pos);
			}

			double speed = 1D;
			double pow = 1D;

			speed += Math.min(upgradeManager.getLevel(UpgradeType.SPEED), 3) / 3D;
			speed += Math.min(upgradeManager.getLevel(UpgradeType.OVERDRIVE), 3);

			pow -= Math.min(upgradeManager.getLevel(UpgradeType.POWER), 3) * 0.25D;
			pow += Math.min(upgradeManager.getLevel(UpgradeType.SPEED), 3) * 1D;
			pow += Math.min(upgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) * 10D / 3D;

			this.didProcess = false;
			boolean markDirty = false;
			
			for(int i = 0; i < 4; i++) {
				this.chemplantModule[i].update(speed * 2D, pow);
				this.didProcess |= this.chemplantModule[i].didProcess;
				markDirty |= this.chemplantModule[i].markDirty;
			}
			
			if(markDirty) this.markDirty();
			
			this.networkPackNT(100);
			
		} else {
			
			this.prevAnim = this.anim;
			if(this.didProcess) this.anim++;
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				frame = !worldObj.getBlock(xCoord, yCoord + 3, zCoord).isAir(worldObj, xCoord, yCoord + 3, zCoord);
			}
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + 3, yCoord, zCoord - 2, Library.POS_X),
				new DirPos(xCoord + 3, yCoord, zCoord + 0, Library.POS_X),
				new DirPos(xCoord + 3, yCoord, zCoord + 2, Library.POS_X),
				new DirPos(xCoord - 3, yCoord, zCoord - 2, Library.NEG_X),
				new DirPos(xCoord - 3, yCoord, zCoord + 0, Library.NEG_X),
				new DirPos(xCoord - 3, yCoord, zCoord + 2, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 3, Library.POS_Z),
				new DirPos(xCoord + 0, yCoord, zCoord + 3, Library.POS_Z),
				new DirPos(xCoord + 2, yCoord, zCoord + 3, Library.POS_Z),
				new DirPos(xCoord - 2, yCoord, zCoord - 3, Library.NEG_Z),
				new DirPos(xCoord + 0, yCoord, zCoord - 3, Library.NEG_Z),
				new DirPos(xCoord + 2, yCoord, zCoord - 3, Library.NEG_Z),

				new DirPos(xCoord + dir.offsetX + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ + rot.offsetZ * 3, rot),
				new DirPos(xCoord - dir.offsetX + rot.offsetX * 3, yCoord, zCoord - dir.offsetZ + rot.offsetZ * 3, rot),
				new DirPos(xCoord + dir.offsetX - rot.offsetX * 3, yCoord, zCoord + dir.offsetZ - rot.offsetZ * 3, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX - rot.offsetX * 3, yCoord, zCoord - dir.offsetZ - rot.offsetZ * 3, rot.getOpposite()),
		};
	}

	
	public DirPos[] getCoolPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX + dir.offsetX * 3, yCoord, zCoord + rot.offsetZ + dir.offsetZ * 3, dir),
				new DirPos(xCoord - rot.offsetX + dir.offsetX * 3, yCoord, zCoord - rot.offsetZ + dir.offsetZ * 3, dir),
				new DirPos(xCoord + rot.offsetX - dir.offsetX * 3, yCoord, zCoord + rot.offsetZ - dir.offsetZ * 3, dir.getOpposite()),
				new DirPos(xCoord - rot.offsetX - dir.offsetX * 3, yCoord, zCoord - rot.offsetZ - dir.offsetZ * 3, dir.getOpposite()),
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for(FluidTank tank : inputTanks) tank.serialize(buf);
		for(FluidTank tank : outputTanks) tank.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeBoolean(didProcess);
		for(int i = 0; i < 4; i++) this.chemplantModule[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(FluidTank tank : inputTanks) tank.deserialize(buf);
		for(FluidTank tank : outputTanks) tank.deserialize(buf);
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		this.didProcess = buf.readBoolean();
		for(int i = 0; i < 4; i++) this.chemplantModule[i].deserialize(buf);
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return inputTanks; }
	@Override public FluidTank[] getSendingTanks() { return outputTanks; }
	@Override public FluidTank[] getAllTanks() { return allTanks; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineChemicalFactory(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineChemicalFactory(player.inventory, this); }

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index >= 0 && index < 4) {
				this.chemplantModule[index].recipe = selection;
				this.markChanged();
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 3, zCoord + 3);
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	@Override
	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
		return type == UpgradeType.SPEED || type == UpgradeType.POWER || type == UpgradeType.OVERDRIVE;
	}

	@Override
	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_chemical_plant));
		if(type == UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(KEY_SPEED, "+" + (level * 100 / 3) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(KEY_CONSUMPTION, "+" + (level * 50) + "%"));
		}
		if(type == UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(KEY_CONSUMPTION, "-" + (level * 25) + "%"));
		}
		if(type == UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}

	@Override
	public HashMap<UpgradeType, Integer> getValidUpgrades() {
		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(UpgradeType.SPEED, 3);
		upgrades.put(UpgradeType.POWER, 3);
		upgrades.put(UpgradeType.OVERDRIVE, 3);
		return upgrades;
	}
}
