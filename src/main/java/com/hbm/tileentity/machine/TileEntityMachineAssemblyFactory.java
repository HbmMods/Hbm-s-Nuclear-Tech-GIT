package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.NotableComments;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerMachineAssemblyFactory;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineAssemblyFactory;
import com.hbm.inventory.recipes.AssemblyMachineRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.module.machine.ModuleMachineAssembler;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TileEntityProxyDyn.IProxyDelegateProvider;
import com.hbm.tileentity.machine.TileEntityMachineAssemblyMachine.AssemblerArm.ArmActionState;
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

// TODO: make a base class because 90% of this is just copy pasted from the chemfac
@NotableComments
public class TileEntityMachineAssemblyFactory extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiverMK2, IUpgradeInfoProvider, IControlReceiver, IGUIProvider, IProxyDelegateProvider {

	public FluidTank[] allTanks;
	public FluidTank[] inputTanks;
	public FluidTank[] outputTanks;
	
	public FluidTank water;
	public FluidTank lps;
	
	public long power;
	public long maxPower = 1_000_000;
	public boolean[] didProcess = new boolean[4];

	public boolean frame = false;
	private AudioWrapper audio;

	public ModuleMachineAssembler[] assemblerModule;
	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT(this);
	
	protected DelegateAssemblyFactoy delegate = new DelegateAssemblyFactoy();

	public TileEntityMachineAssemblyFactory() {
		super(60);
		
		this.inputTanks = new FluidTank[4];
		this.outputTanks = new FluidTank[4];
		for(int i = 0; i < 4; i++) {
			this.inputTanks[i] = new FluidTank(Fluids.NONE, 4_000);
			this.outputTanks[i] = new FluidTank(Fluids.NONE, 4_000);
		}

		this.water = new FluidTank(Fluids.WATER, 4_000);
		this.lps = new FluidTank(Fluids.SPENTSTEAM, 4_000);
		
		this.allTanks = new FluidTank[this.inputTanks.length + this.outputTanks.length + 2];
		for(int i = 0; i < inputTanks.length; i++) this.allTanks[i] = this.inputTanks[i];
		for(int i = 0; i < outputTanks.length; i++) this.allTanks[i + this.inputTanks.length] = this.outputTanks[i];
		
		this.allTanks[this.allTanks.length - 2] = this.water;
		this.allTanks[this.allTanks.length - 1] = this.lps;
		
		this.assemblerModule = new ModuleMachineAssembler[4];
		for(int i = 0; i < 4; i++) this.assemblerModule[i] = new ModuleMachineAssembler(i, this, slots)
				.itemInput(5 + i * 14).itemOutput(17 + i * 14)
				.fluidInput(inputTanks[i]).fluidOutput(outputTanks[i]);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		for(int k = 0; k < 4; k++) if(i == 17 + k * 14) return true;
		for(int k = 0; k < 4; k++) if(this.assemblerModule[k].isSlotClogged(i)) return true;
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // battery
		for(int i = 0; i < 4; i++) if(slot == 4 + i * 14 && stack.getItem() == ModItems.blueprints) return true;
		if(slot >= 1 && slot <= 3 && stack.getItem() instanceof ItemMachineUpgrade) return true; // upgrades
		for(int i = 0; i < 4; i++) if(this.assemblerModule[i].isItemValid(slot, stack)) return true; // recipe input crap
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {
				 5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15, 16, 17,
				19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31,
				33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45,
				47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59
		}; // ho boy, a big fucking array of hand-written values, surely this isn't gonna bite me in the ass some day
	}

	@Override
	public String getName() {
		return "container.machineAssemblyFactory";
	}

	@Override
	public void updateEntity() {
		
		if(maxPower <= 0) this.maxPower = 10_000_000;
		
		if(!worldObj.isRemote) {
			
			long nextMaxPower = 0;
			for(int i = 0; i < 4; i++) {
				GenericRecipe recipe = AssemblyMachineRecipes.INSTANCE.recipeNameMap.get(assemblerModule[i].recipe);
				if(recipe != null) {
					nextMaxPower += recipe.power * 100;
				}
			}
			this.maxPower = nextMaxPower;
			this.maxPower = BobMathUtil.max(this.power, this.maxPower, 1_000_000);
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			upgradeManager.checkSlots(slots, 1, 3);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos);
				for(FluidTank tank : inputTanks) if(tank.getTankType() != Fluids.NONE) this.trySubscribe(tank.getTankType(), worldObj, pos);
				for(FluidTank tank : outputTanks) if(tank.getFill() > 0) this.tryProvide(tank, worldObj, pos);
			}
			
			for(DirPos pos : getCoolPos()) {
				delegate.trySubscribe(worldObj, pos);
				delegate.trySubscribe(water.getTankType(), worldObj, pos);
				delegate.tryProvide(lps, worldObj, pos);
			}

			double speed = 1D;
			double pow = 1D;

			speed += Math.min(upgradeManager.getLevel(UpgradeType.SPEED), 3) / 3D;
			speed += Math.min(upgradeManager.getLevel(UpgradeType.OVERDRIVE), 3);

			pow -= Math.min(upgradeManager.getLevel(UpgradeType.POWER), 3) * 0.25D;
			pow += Math.min(upgradeManager.getLevel(UpgradeType.SPEED), 3) * 1D;
			pow += Math.min(upgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) * 10D / 3D;
			boolean markDirty = false;
			
			for(int i = 0; i < 4; i++) {
				this.assemblerModule[i].update(speed * 2D, pow * 2D, canCool(), slots[4 + i * 7]);
				this.didProcess[i] =  this.assemblerModule[i].didProcess;
				markDirty |= this.assemblerModule[i].markDirty;
				
				if(this.assemblerModule[i].didProcess) {
					this.water.setFill(this.water.getFill() - 100);
					this.lps.setFill(this.lps.getFill() + 100);
				}
			}
			
			if(markDirty) this.markDirty();
			
			this.networkPackNT(100);
		} else {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				frame = !worldObj.getBlock(xCoord, yCoord + 3, zCoord).isAir(worldObj, xCoord, yCoord + 3, zCoord);
			}
		}
	}

	@Override public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.motor", xCoord, yCoord, zCoord, 0.5F, 15F, 0.75F, 20);
	}

	@Override public void onChunkUnload() {
		if(audio != null) { audio.stopSound(); audio = null; }
	}

	@Override public void invalidate() {
		super.invalidate();
		if(audio != null) { audio.stopSound(); audio = null; }
	}
	
	public boolean canCool() {
		return water.getFill() >= 100 && lps.getFill() <= lps.getMaxFill() - 100;
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

				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX * 2, yCoord + 3, zCoord + dir.offsetZ * 2 + rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord + dir.offsetX * 1 + rot.offsetX * 2, yCoord + 3, zCoord + dir.offsetZ * 1 + rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord + dir.offsetX * 0 + rot.offsetX * 2, yCoord + 3, zCoord + dir.offsetZ * 0 + rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord - dir.offsetX * 1 + rot.offsetX * 2, yCoord + 3, zCoord - dir.offsetZ * 1 + rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX * 2, yCoord + 3, zCoord - dir.offsetZ * 2 + rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * 2, yCoord + 3, zCoord + dir.offsetZ * 2 - rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord + dir.offsetX * 1 - rot.offsetX * 2, yCoord + 3, zCoord + dir.offsetZ * 1 - rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord + dir.offsetX * 0 - rot.offsetX * 2, yCoord + 3, zCoord + dir.offsetZ * 0 - rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord - dir.offsetX * 1 - rot.offsetX * 2, yCoord + 3, zCoord - dir.offsetZ * 1 - rot.offsetZ * 2, Library.POS_Y),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX * 2, yCoord + 3, zCoord - dir.offsetZ * 2 - rot.offsetZ * 2, Library.POS_Y),

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
		water.serialize(buf);
		lps.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		for(boolean b : didProcess) buf.writeBoolean(b);
		for(int i = 0; i < 4; i++) this.assemblerModule[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for(FluidTank tank : inputTanks) tank.deserialize(buf);
		for(FluidTank tank : outputTanks) tank.deserialize(buf);
		water.deserialize(buf);
		lps.deserialize(buf);
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		for(int i = 0; i < 4; i++) this.didProcess[i] = buf.readBoolean();
		for(int i = 0; i < 4; i++) this.assemblerModule[i].deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		for(int i = 0; i < inputTanks.length; i++) this.inputTanks[i].readFromNBT(nbt, "i" + i);
		for(int i = 0; i < outputTanks.length; i++) this.outputTanks[i].readFromNBT(nbt, "i" + i);

		this.water.readFromNBT(nbt, "w");
		this.lps.readFromNBT(nbt, "s");

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		for(int i = 0; i < 4; i++) this.assemblerModule[i].readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		for(int i = 0; i < inputTanks.length; i++) this.inputTanks[i].writeToNBT(nbt, "i" + i);
		for(int i = 0; i < outputTanks.length; i++) this.outputTanks[i].writeToNBT(nbt, "i" + i);

		this.water.writeToNBT(nbt, "w");
		this.lps.writeToNBT(nbt, "s");

		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		for(int i = 0; i < 4; i++) this.assemblerModule[i].writeToNBT(nbt);
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return inputTanks; }
	@Override public FluidTank[] getSendingTanks() { return outputTanks; }
	@Override public FluidTank[] getAllTanks() { return allTanks; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineAssemblyFactory(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineAssemblyFactory(player.inventory, this); }

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index >= 0 && index < 4) {
				this.assemblerModule[index].recipe = selection;
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
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_chemical_factory));
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

	public DirPos[] coolantLine;
	
	@Override // carelessly copy pasted from TileEntityMachineChemicalFactory
	public Object getDelegateForPosition(int x, int y, int z) {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		if(coolantLine == null) coolantLine = new DirPos[] {
				new DirPos(xCoord + rot.offsetX + dir.offsetX * 2, yCoord, zCoord + rot.offsetZ + dir.offsetZ * 2, dir),
				new DirPos(xCoord - rot.offsetX + dir.offsetX * 2, yCoord, zCoord - rot.offsetZ + dir.offsetZ * 2, dir),
				new DirPos(xCoord + rot.offsetX - dir.offsetX * 2, yCoord, zCoord + rot.offsetZ - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - rot.offsetX - dir.offsetX * 2, yCoord, zCoord - rot.offsetZ - dir.offsetZ * 2, dir.getOpposite()),
		};
		
		for(DirPos pos : coolantLine) if(pos.compare(x, y, z)) return this.delegate; // this actually fucking works
		
		return null;
	}
	
	public class DelegateAssemblyFactoy implements IEnergyReceiverMK2, IFluidStandardTransceiverMK2 { // scumware

		@Override public long getPower() { return TileEntityMachineAssemblyFactory.this.getPower(); }
		@Override public void setPower(long power) { TileEntityMachineAssemblyFactory.this.setPower(power); }
		@Override public long getMaxPower() { return TileEntityMachineAssemblyFactory.this.getMaxPower(); }
		@Override public boolean isLoaded() { return TileEntityMachineAssemblyFactory.this.isLoaded(); }

		@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {TileEntityMachineAssemblyFactory.this.water}; }
		@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {TileEntityMachineAssemblyFactory.this.lps}; }

		@Override public FluidTank[] getAllTanks() { return TileEntityMachineAssemblyFactory.this.getAllTanks(); }
	}
	
	public static class TragicYuri {
		
	}
	
	public static class AssemblerArm {
		
		public double[] angles = new double[4];
		public double[] prevAngles = new double[4];
		public double[] targetAngles = new double[4];
		public double[] speed = new double[4];
		public double sawAngle;
		public double prevSawAngle;

		Random rand = new Random();
		ArmActionState state = ArmActionState.ASSUME_POSITION;
		int actionDelay = 0;
		boolean saw = false;
		
		public AssemblerArm() {
			this.resetSpeed();
		}
		
		private void resetSpeed() {
			speed[0] = 15;	//Pivot
			speed[1] = 15;	//Arm
			speed[2] = 15;	//Piston
			speed[3] = 0.5;	//Striker
		}
		
		public double[] getPositions(float interp) {
			return new double[] {
					BobMathUtil.interp(this.prevAngles[0], this.angles[0], interp),
					BobMathUtil.interp(this.prevAngles[1], this.angles[1], interp),
					BobMathUtil.interp(this.prevAngles[2], this.angles[2], interp),
					BobMathUtil.interp(this.prevAngles[3], this.angles[3], interp),
					BobMathUtil.interp(this.prevSawAngle, this.sawAngle, interp)
			};
		}
	}
	
	public static enum YuriState {
		WORKING, RETIRING, SLIDING
	}

	public static enum ArmState {
		REPOSITION, EXTEND, CUT, RETRACT, RETIRE
	}
}
