package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerMachinePrecAss;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachinePrecAss;
import com.hbm.inventory.recipes.PrecAssRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.module.machine.ModuleMachinePrecAss;
import com.hbm.sound.AudioWrapper;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

// horribly copy-pasted crap device
public class TileEntityMachinePrecAss extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiverMK2, IUpgradeInfoProvider, IControlReceiver, IGUIProvider {

	public FluidTank inputTank;
	public FluidTank outputTank;
	
	public long power;
	public long maxPower = 100_000;
	public boolean didProcess = false;
	
	public boolean frame = false;
	private AudioWrapper audio;

	public ModuleMachinePrecAss assemblerModule;
	
	public double prevRing;
	public double ring;
	public double ringSpeed;
	public double ringTarget;
	public int ringDelay;

	public double[] armAngles = new double[] {45, -15, -5};
	public double[] prevArmAngles = new double[] {45, -15, -5};
	public double[] strikers = new double[4];
	public double[] prevStrikers = new double[4];
	public boolean[] strikerDir = new boolean[4];
	protected int strikerIndex;
	protected int strikerDelay;

	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT(this);
	
	public TileEntityMachinePrecAss() {
		super(22);
		this.inputTank = new FluidTank(Fluids.NONE, 4_000);
		this.outputTank = new FluidTank(Fluids.NONE, 4_000);
		
		this.assemblerModule = new ModuleMachinePrecAss(0, this, slots)
				.itemInput(4).itemOutput(13)
				.fluidInput(inputTank).fluidOutput(outputTank);
	}

	@Override
	public String getName() {
		return "container.machinePrecAss";
	}

	@Override
	public void updateEntity() {
		
		if(maxPower <= 0) this.maxPower = 1_000_000;
		
		if(!worldObj.isRemote) {
			
			GenericRecipe recipe = PrecAssRecipes.INSTANCE.recipeNameMap.get(assemblerModule.recipe);
			if(recipe != null) {
				this.maxPower = recipe.power * 100;
			}
			this.maxPower = BobMathUtil.max(this.power, this.maxPower, 100_000);
			
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			upgradeManager.checkSlots(slots, 2, 3);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos);
				if(inputTank.getTankType() != Fluids.NONE) this.trySubscribe(inputTank.getTankType(), worldObj, pos);
				if(outputTank.getFill() > 0) this.tryProvide(outputTank, worldObj, pos);
			}

			double speed = 1D;
			double pow = 1D;

			speed += Math.min(upgradeManager.getLevel(UpgradeType.SPEED), 3) / 3D;
			speed += Math.min(upgradeManager.getLevel(UpgradeType.OVERDRIVE), 3);

			pow -= Math.min(upgradeManager.getLevel(UpgradeType.POWER), 3) * 0.25D;
			pow += Math.min(upgradeManager.getLevel(UpgradeType.SPEED), 3) * 1D;
			pow += Math.min(upgradeManager.getLevel(UpgradeType.OVERDRIVE), 3) * 10D / 3D;
			
			this.assemblerModule.update(speed, pow, true, slots[1]);
			this.didProcess = this.assemblerModule.didProcess;
			if(this.assemblerModule.markDirty) this.markDirty();
			
			this.networkPackNT(100);
			
		} else {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				frame = !worldObj.getBlock(xCoord, yCoord + 3, zCoord).isAir(worldObj, xCoord, yCoord + 3, zCoord);
			}
			
			if(this.didProcess && MainRegistry.proxy.me().getDistance(xCoord , yCoord, zCoord) < 50) {
				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}
				audio.keepAlive();
				audio.updatePitch(0.75F);
				audio.updateVolume(this.getVolume(0.5F));
				
			} else {
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}

			for(int i = 0; i < 3; i++) this.prevArmAngles[i] = this.armAngles[i];
			for(int i = 0; i < 4; i++) this.prevStrikers[i] = this.strikers[i];
			
			this.prevRing = this.ring;
			
			for(int i = 0; i < 4; i++) {
				if(this.strikerDir[i]) {
					this.strikers[i] = -0.75D;
					this.strikerDir[i] = false;
				} else {
					this.strikers[i] = MathHelper.clamp_double(this.strikers[i] + 0.5D, -0.75D, 0D);
				}
			}
			
			if(this.isInWorkingPosition(prevArmAngles) && !this.isInWorkingPosition(armAngles)) {
				if(!this.muffled) MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "hbm:block.assemblerStop", this.getVolume(0.25F), 1.25F + worldObj.rand.nextFloat() * 0.25F);
			}
			
			if(this.ring != this.ringTarget) {
				double ringDelta = Math.abs(this.ringTarget - this.ring);
				if(ringDelta <= this.ringSpeed) this.ring = this.ringTarget;
				if(this.ringTarget > this.ring) this.ring += this.ringSpeed;
				if(this.ringTarget < this.ring) this.ring -= this.ringSpeed;
				if(this.ringTarget == this.ring) {
					double sub = ringTarget >= 360 ? -360D : 360D;
					this.ringTarget += sub;
					this.ring += sub;
					this.prevRing += sub;
					this.ringDelay = 100 + worldObj.rand.nextInt(21);
				}
			}
			
			if(didProcess) {
				if(this.ring == this.ringTarget) {
					if(this.ringDelay > 0) this.ringDelay--;
					if(this.ringDelay <= 0) {
						this.ringTarget += 45 * (worldObj.rand.nextBoolean() ? -1 : 1);
						this.ringSpeed = 10D + worldObj.rand.nextDouble() * 5D;
						if(!this.muffled) MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "hbm:block.assemblerStart", this.getVolume(0.25F), 1.25F + worldObj.rand.nextFloat() * 0.25F);
					}
				}
				
				if(!isInWorkingPosition(this.armAngles) && canArmsMove()) {
					move(WORKING_POSITION);
				}
				
				if(isInWorkingPosition(this.armAngles)) {
					this.strikerDelay--;
					if(this.strikerDelay <= 0) {
						this.strikerDir[this.strikerIndex] = true;
						this.strikerIndex = (this.strikerIndex + 1) % this.strikers.length;
						this.strikerDelay = this.strikerIndex == 3 ? 10 : 2;
					}
				}
				
			} else {
				for(int i = 0; i < 4; i++) this.strikerDir[i] = false; // set all strikers to retract
				if(canArmsMove()) move(NULL_POSITION);
			}
		}
	}

	public double[] NULL_POSITION = new double[] {45, -30, 45};
	public double[] WORKING_POSITION = new double[] {45, -15, -5};
	
	private boolean canArmsMove() {
		for(int i = 0; i < 4; i++) if(this.strikers[i] != 0) return false;
		return true;
	}
	
	private boolean isInWorkingPosition(double[] arms) {
		for(int i = 0; i < 3; i++) if(arms[i] != WORKING_POSITION[i]) return false;
		return true;
	}
	
	private boolean move(double[] targetAngles) {
		boolean didMove = false;

		for(int i = 0; i < armAngles.length; i++) {
			if(armAngles[i] == targetAngles[i]) continue;
			didMove = true;
			double angle = armAngles[i];
			double target = targetAngles[i];
			double turn = 15D;
			double delta = Math.abs(angle - target);

			if(delta <= turn) { armAngles[i] = targetAngles[i]; continue; }
			if(angle < target) armAngles[i] += turn;
			else armAngles[i] -= turn;
		}
		return !didMove;
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
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord + 0, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 0, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 0, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord + 0, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.inputTank.serialize(buf);
		this.outputTank.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeBoolean(didProcess);
		this.assemblerModule.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		boolean wasProcessing = this.didProcess;
		this.inputTank.deserialize(buf);
		this.outputTank.deserialize(buf);
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		this.didProcess = buf.readBoolean();
		this.assemblerModule.deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.inputTank.readFromNBT(nbt, "i");
		this.outputTank.readFromNBT(nbt, "o");
		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.assemblerModule.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.inputTank.writeToNBT(nbt, "i");
		this.outputTank.writeToNBT(nbt, "o");
		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		this.assemblerModule.writeToNBT(nbt);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // battery
		if(slot == 1 && stack.getItem() == ModItems.blueprints) return true;
		if(slot >= 2 && slot <= 3 && stack.getItem() instanceof ItemMachineUpgrade) return true; // upgrades
		if(this.assemblerModule.isItemValid(slot, stack)) return true; // recipe input crap
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i >= 13 || this.assemblerModule.isSlotClogged(i);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {inputTank}; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {outputTank}; }
	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {inputTank, outputTank}; }

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachinePrecAss(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachinePrecAss(player.inventory, this); }

	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index == 0) {
				this.assemblerModule.recipe = selection;
				this.markChanged();
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
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
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_precass));
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
