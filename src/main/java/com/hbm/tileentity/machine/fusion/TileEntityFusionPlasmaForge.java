package com.hbm.tileentity.machine.fusion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Consumer;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.inventory.container.ContainerMachinePlasmaForge;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachinePlasmaForge;
import com.hbm.inventory.recipes.PlasmaForgeRecipe;
import com.hbm.inventory.recipes.PlasmaForgeRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.module.machine.ModuleMachinePlasma;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PlasmaNetworkProvider;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionPlasmaForge extends TileEntityMachineBase implements IFusionPowerReceiver, IEnergyReceiverMK2, IFluidStandardReceiverMK2, IControlReceiver, IGUIProvider {

	public FluidTank inputTank;
	
	public long power;
	public long maxPower = 10_000_000;
	public boolean didProcess;

	public float plasmaRed;
	public float plasmaGreen;
	public float plasmaBlue;
	public long plasmaEnergy;
	public long plasmaEnergySync;
	public double neutronEnergy;
	protected GenNode receiverNode;
	protected GenNode providerNode;
	public boolean connected;
	
	public int booster;
	public int maxBooster;
	
	public int timeOffset = -1;

	public ModuleMachinePlasma plasmaModule;
	
	// animation crap
	public double prevRing;
	public double ring;
	public double ringSpeed;
	public double ringTarget;
	public int ringDelay;
	public ForgeArm armStriker;
	public ForgeArm armJet;
	
	public static List<Pair<AStack, Integer>> boosters = new ArrayList();
	
	static {
		boosters.add(new Pair(new OreDictStack(CO60.nugget()),		20));
		boosters.add(new Pair(new OreDictStack(CO60.billet()),		120));
		boosters.add(new Pair(new OreDictStack(CO60.ingot()),		200));
		boosters.add(new Pair(new OreDictStack(CO60.dust()),		200));
		boosters.add(new Pair(new OreDictStack(SR90.nugget()),		40));
		boosters.add(new Pair(new OreDictStack(SR90.dustTiny()),	40));
		boosters.add(new Pair(new OreDictStack(SR90.billet()),		240));
		boosters.add(new Pair(new OreDictStack(SR90.ingot()),		400));
		boosters.add(new Pair(new OreDictStack(SR90.dust()),		400));
		boosters.add(new Pair(new OreDictStack(AU198.nugget()),		60));
		boosters.add(new Pair(new OreDictStack(AU198.billet()), 	360));
		boosters.add(new Pair(new OreDictStack(AU198.ingot()),		600));
		boosters.add(new Pair(new OreDictStack(AU198.dust()),		600));
		boosters.add(new Pair(new OreDictStack(I131.dustTiny()),	60));
		boosters.add(new Pair(new OreDictStack(I131.dust()),		600));
		boosters.add(new Pair(new OreDictStack(XE135.dustTiny()),	60));
		boosters.add(new Pair(new OreDictStack(XE135.dust()),		600));
		boosters.add(new Pair(new OreDictStack(CS137.dustTiny()),	50));
		boosters.add(new Pair(new OreDictStack(CS137.dust()),		500));
		boosters.add(new Pair(new OreDictStack(AT209.dust()),		1_200));
	}
	
	public TileEntityFusionPlasmaForge() {
		super(16);
		this.inputTank = new FluidTank(Fluids.NONE, 16_000);
		
		this.plasmaModule = new ModuleMachinePlasma(0, this, slots)
				.itemInput(3).itemOutput(15).fluidInput(inputTank);

		this.armStriker = new ForgeArm(ForgeArmType.STRIKER);
		this.armJet = new ForgeArm(ForgeArmType.JET);
	}

	@Override
	public String getName() {
		return "container.machinePlasmaForge";
	}
	
	@Override public boolean receivesFusionPower() { return true; }
	@Override public void receiveFusionPower(long fusionPower, double neutronPower, float r, float g, float b) {
		this.plasmaEnergy = fusionPower;
		this.neutronEnergy = neutronPower;
		this.plasmaRed = r;
		this.plasmaGreen = g;
		this.plasmaBlue = b;
	}

	@Override
	public void updateEntity() {
		
		if(maxPower <= 0) this.maxPower = 1_000_000;
		
		if(!worldObj.isRemote) {
			
			this.plasmaEnergySync = this.plasmaEnergy;
			this.plasmaEnergy = 0;
			
			if(booster <= 0 && slots[2] != null) {
				for(Pair<AStack, Integer> booster : boosters) {
					if(booster.getKey().matchesRecipe(slots[2], true)) {
						this.maxBooster = this.booster = booster.getValue();
						this.decrStackSize(2, 1);
						break;
					}
				}
			}
			
			ForgeDirection rot = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getRotation(ForgeDirection.UP);
			if(receiverNode == null || receiverNode.expired) receiverNode = this.createNode(PlasmaNetworkProvider.THE_PROVIDER, rot);
			if(providerNode == null || providerNode.expired) providerNode = this.createNode(PlasmaNetworkProvider.THE_PROVIDER, rot.getOpposite());

			if(receiverNode != null && receiverNode.hasValidNet()) receiverNode.net.addReceiver(this);
			if(providerNode != null && providerNode.hasValidNet()) providerNode.net.addProvider(this); // technically unused, but good to have when we do something else with the plasma nets
			
			PlasmaForgeRecipe recipe = (PlasmaForgeRecipe) PlasmaForgeRecipes.INSTANCE.recipeNameMap.get(plasmaModule.recipe);
			if(recipe != null) this.maxPower = recipe.power * 100;
			
			this.maxPower = BobMathUtil.max(this.power, this.maxPower, 100_000);
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos);
				if(inputTank.getTankType() != Fluids.NONE) this.trySubscribe(inputTank.getTankType(), worldObj, pos);
			}

			double speed = booster > 0 ? 4D : 1D;
			double pow = 1D;

			boolean ignition = recipe != null ? recipe.ignitionTemp <= this.plasmaEnergySync : true;
			
			this.plasmaModule.update(speed, pow, ignition, slots[1]);
			this.didProcess = this.plasmaModule.didProcess;
			if(this.plasmaModule.markDirty) this.markDirty();

			long powerReceived = (long) Math.ceil(this.plasmaEnergySync * 0.75);
			
			this.connected = false;
			if(providerNode != null && providerNode.hasValidNet() && !providerNode.net.receiverEntries.isEmpty()) connected = true;
			
			if(providerNode != null && providerNode.hasValidNet()) {

				for(Object o : providerNode.net.receiverEntries.entrySet()) {
					Entry<Object, Long> entry = (Entry<Object, Long>) o;

					if(entry.getKey() instanceof IFusionPowerReceiver) {
						if(powerReceived > 0) ((IFusionPowerReceiver) entry.getKey()).receiveFusionPower(powerReceived, this.neutronEnergy, plasmaRed, plasmaGreen, plasmaBlue);
					}
				}
			}
			
			if(this.didProcess && this.booster > 0) this.booster--;
			this.neutronEnergy = 0D;
			
			this.networkPackNT(100);
		} else {

			if(timeOffset == -1) this.timeOffset = worldObj.rand.nextInt(30_000);
			
			this.armStriker.updateArm();
			this.armJet.updateArm();
			
			this.prevRing = this.ring;
			
			if(didProcess) {
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
						this.ringDelay = 100 + worldObj.rand.nextInt(41);
					}
				} else {
					if(this.ringDelay > 0) this.ringDelay--;
					if(this.ringDelay <= 0) {
						this.ringTarget += (worldObj.rand.nextDouble() + 1) * 60 * (worldObj.rand.nextBoolean() ? - 1 : 1);
						this.ringSpeed = 2.5D;
					}
				}
			}
		}
	}

	public GenNode createNode(INetworkProvider provider, ForgeDirection dir) {
		GenNode node = UniNodespace.getNode(worldObj, xCoord + dir.offsetX * 5, yCoord + 2, zCoord + dir.offsetZ * 5, provider);
		if(node != null) return node;

		node = new GenNode(provider,
				new BlockPos(xCoord + dir.offsetX * 5, yCoord + 2, zCoord + dir.offsetZ * 5))
				.setConnections(new DirPos(xCoord + dir.offsetX * 6, yCoord + 2, zCoord + dir.offsetZ * 6, dir));

		UniNodespace.createNode(worldObj, node);

		return node;
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(receiverNode != null) UniNodespace.destroyNode(worldObj, receiverNode);
			if(providerNode != null) UniNodespace.destroyNode(worldObj, providerNode);
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 6 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 6 - rot.offsetZ * 2, dir),
				new DirPos(xCoord + dir.offsetX * 6 - rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 6 - rot.offsetZ * 1, dir),
				new DirPos(xCoord + dir.offsetX * 6 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * 6 + rot.offsetZ * 0, dir),
				new DirPos(xCoord + dir.offsetX * 6 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 6 + rot.offsetZ * 1, dir),
				new DirPos(xCoord + dir.offsetX * 6 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 6 + rot.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 6 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 6 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 - rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 6 - rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 + rot.offsetX * 0, yCoord, zCoord - dir.offsetZ * 6 + rot.offsetZ * 0, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 + rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 6 + rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 6 + rot.offsetZ * 2, dir.getOpposite()),
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.inputTank.serialize(buf);
		buf.writeFloat(plasmaRed);
		buf.writeFloat(plasmaGreen);
		buf.writeFloat(plasmaBlue);
		buf.writeLong(plasmaEnergySync);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeBoolean(didProcess);
		buf.writeBoolean(connected);
		buf.writeInt(booster);
		buf.writeInt(maxBooster);
		this.plasmaModule.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.inputTank.deserialize(buf);
		this.plasmaRed = buf.readFloat();
		this.plasmaGreen = buf.readFloat();
		this.plasmaBlue = buf.readFloat();
		this.plasmaEnergySync = buf.readLong();
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		this.didProcess = buf.readBoolean();
		this.connected = buf.readBoolean();
		this.booster = buf.readInt();
		this.maxBooster = buf.readInt();
		this.plasmaModule.deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.inputTank.readFromNBT(nbt, "i");
		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.booster = nbt.getInteger("booster");
		this.maxBooster = nbt.getInteger("maxBooster");
		this.plasmaModule.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.inputTank.writeToNBT(nbt, "i");
		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		nbt.setInteger("booster", booster);
		nbt.setInteger("maxBooster", maxBooster);
		this.plasmaModule.writeToNBT(nbt);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // battery
		if(slot == 1 && stack.getItem() == ModItems.blueprints) return true;
		if(this.plasmaModule.isItemValid(slot, stack)) return true; // recipe input crap
		if(slot == 2) {
			for(Pair<AStack, Integer> booster : boosters) { // booster isotopes
				if(booster.getKey().matchesRecipe(stack, true)) return true;
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 15 || this.plasmaModule.isSlotClogged(i);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {inputTank}; }
	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {inputTank}; }
	
	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index == 0) {
				this.plasmaModule.recipe = selection;
				this.markChanged();
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 5, yCoord, zCoord - 5, xCoord + 5, yCoord + 6, zCoord + 6);
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachinePlasmaForge(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachinePlasmaForge(player.inventory, this); }
	
	public class ForgeArm {

		public ForgeArmType type;
		public ForgeArmState state = ForgeArmState.RETIRE;
		public double[] angles;
		public double[] prevAngles;
		public double[] targetAngles;
		public double[] speed;
		public int actionDelay = 0;
		
		public ForgeArm(ForgeArmType type) {
			this.type = type;
			this.angles =		new double[type.angleCount];
			this.prevAngles =	new double[type.angleCount];
			this.targetAngles =	new double[type.angleCount];
			this.speed =		new double[type.angleCount];
			
			for(int i = 0; i < speed.length; i++) {
				if(i < 3 || i == 4) speed[i] = 15;
				if(i == 3) speed[i] = 15;
				if(i > 4) speed[i] = 0.5;
			}
		}
		
		public void updateArm() {
			for(int i = 0; i < angles.length; i++) prevAngles[i] = angles[i];

			if(!didProcess()) this.state = ForgeArmState.RETIRE;
			if(this.state == ForgeArmState.RETIRE) this.actionDelay = 0;
			
			if(this.actionDelay > 0) {
				this.actionDelay--;
				return;
			}
			
			this.type.stateMachine.accept(this);
		}
		
		public boolean didProcess() { return didProcess; }
		
		public boolean move() {
			boolean didMove = false;

			for(int i = 0; i < angles.length; i++) {
				if(angles[i] == targetAngles[i]) continue;
				didMove = true;

				double angle = angles[i];
				double target = targetAngles[i];
				double turn = speed[i];
				double delta = Math.abs(angle - target);

				if(delta <= turn) {
					angles[i] = targetAngles[i];
					continue;
				}
				if(angle < target) angles[i] += turn;
				else angles[i] -= turn;
			}

			return !didMove;
		}
		
		public void playStrikerSound() {
			MainRegistry.proxy.playSoundClient(xCoord, yCoord, zCoord, "hbm:item.boltgun", getVolume(0.25F), 1.25F);
		}
		
		public double[] getPositions(float interp) {
			double[] pos = new double[this.angles.length];
			for(int i = 0; i < pos.length; i++) {
				pos[i] = BobMathUtil.interp(this.prevAngles[i], this.angles[i], interp);
			}
			return pos;
		}
	}
	
	public static enum ForgeArmType {
		STRIKER(STRIKER_STATE_MACHINE, 6), // pivot to lower arm, lower arm to upper arm, upper arm to mount, mount to strikers, striker 1, striker 2
		JET(JET_STATE_MACHINE, 4); // pivot to lower arm, lower arm to upper arm, upper arm to jet, jet (unused, actual jet is handled via other conditions)
		
		protected final int angleCount;
		protected final Consumer<ForgeArm> stateMachine;
		
		private ForgeArmType(Consumer<ForgeArm> stateMachine, int angleCount) {
			this.stateMachine = stateMachine;
			this.angleCount = angleCount;
		}
	}
	
	public static enum ForgeArmState {
		REPOSITION,
		EXTEND1,
		EXTEND2,
		RETRACT1,
		RETRACT2,
		RETIRE
	}
	
	public static Random rand = new Random();
	
	public static double[][] strikerPositions = new double[][] {
			{20, -30, -20, 30},
			{45, -80, 15, 30},
			{30, -45, -10, 30},
			{15, -20, -30, 30},
			{0, 10, -55, 30}
	};
	
	public static double[][] jetPositions = new double[][] {
			{10, 45, -120},
			{20, 45, -140},
			{0, 30, -80},
			{0, 40, -100},
			{30, 50, -160}
	};
	
	public static Consumer<ForgeArm> STRIKER_STATE_MACHINE = (arm) -> {
		
		switch(arm.state) {
		case REPOSITION: {
			if(arm.move()) {
				arm.actionDelay = 5;
				arm.state = ForgeArmState.EXTEND1;
				arm.targetAngles[4] = 0.5D;
			}
		} break;
		case EXTEND1: {
			if(arm.move()) {
				arm.actionDelay = 0;
				arm.state = ForgeArmState.RETRACT1;
				arm.targetAngles[4] = 0D;
				arm.playStrikerSound();
			}
		} break;
		case RETRACT1: {
			if(arm.move()) {
				arm.actionDelay = 0;
				arm.state = ForgeArmState.EXTEND2;
				arm.targetAngles[5] = 0.5D;
			}
		} break;
		case EXTEND2: {
			if(arm.move()) {
				arm.actionDelay = 0;
				arm.state = ForgeArmState.RETRACT2;
				arm.targetAngles[5] = 0D;
				arm.playStrikerSound();
			}
		} break;
		case RETRACT2: {
			if(arm.move()) {
				if(rand.nextInt(3) == 0) {
					arm.actionDelay = 10;
					arm.state = ForgeArmState.REPOSITION;
					choosePosition(arm, strikerPositions);
				} else {
					arm.actionDelay = 5;
					arm.state = ForgeArmState.EXTEND1;
					arm.targetAngles[4] = 0.5D;
				}
			}
		} break;
		case RETIRE: {
			for(int i = 0; i < arm.targetAngles.length; i++) arm.targetAngles[i] = 0;
			if(arm.move()) {
				arm.actionDelay = 10;
				arm.state = ForgeArmState.REPOSITION;
				choosePosition(arm, strikerPositions);
			}
		} break;
		}
		
		// experimental bit to make the dual pivots reset between repositions for more movement, looked way too janky
		/*if(arm.state == ForgeArmState.REPOSITION || arm.state == ForgeArmState.RETIRE) {
			arm.targetAngles[3] = 0;
		} else {
			arm.targetAngles[3] = 30;
		}*/
	};

	@SuppressWarnings("incomplete-switch") // shut up
	public static Consumer<ForgeArm> JET_STATE_MACHINE = (arm) -> {
		
		switch(arm.state) {
		case REPOSITION: {
			if(arm.move()) {
				arm.actionDelay = 20 + rand.nextInt(3) * 10;
				arm.state = ForgeArmState.REPOSITION;
				choosePosition(arm, jetPositions);
			}
		} break;
		case RETIRE: {
			for(int i = 0; i < arm.targetAngles.length; i++) arm.targetAngles[i] = 0;
			if(arm.move()) {
				arm.actionDelay = 10;
				arm.state = ForgeArmState.REPOSITION;
				choosePosition(arm, jetPositions);
			}
		} break;
		}
	};
	
	public static void choosePosition(ForgeArm arm, double[][] positions) {
		double[] newPos = positions[rand.nextInt(positions.length)];
		for(int i = 0; i < newPos.length; i++) arm.targetAngles[i] = newPos[i];
	}
}
