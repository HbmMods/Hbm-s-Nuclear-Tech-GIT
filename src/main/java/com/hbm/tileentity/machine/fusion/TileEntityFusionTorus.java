package com.hbm.tileentity.machine.fusion;

import java.util.Map.Entry;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerFusionTorus;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIFusionTorus;
import com.hbm.inventory.recipes.FusionRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.module.machine.ModuleMachineFusion;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.albion.TileEntityCooledBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.KlystronNetworkProvider;
import com.hbm.uninos.networkproviders.PlasmaNetworkProvider;
import com.hbm.util.BobMathUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionTorus extends TileEntityCooledBase implements IGUIProvider, IControlReceiver {

	public boolean didProcess = false;
	
	public FluidTank[] tanks;
	public ModuleMachineFusion fusionModule;

	protected GenNode[] klystronNodes;
	protected GenNode[] plasmaNodes;
	public boolean[] connections;

	public long klystronEnergy;
	public long plasmaEnergy;
	public double fuelConsumption;
	
	public float magnet;
	public float prevMagnet;
	public float magnetSpeed;
	public static final float MAGNET_ACCELERATION = 0.25F;
	
	public TileEntityFusionTorus() {
		super(3);

		klystronNodes = new GenNode[4];
		plasmaNodes = new GenNode[4];
		connections = new boolean[4];
		
		this.tanks = new FluidTank[4];

		this.tanks[0] = new FluidTank(Fluids.NONE, 4_000);
		this.tanks[1] = new FluidTank(Fluids.NONE, 4_000);
		this.tanks[2] = new FluidTank(Fluids.NONE, 4_000);
		this.tanks[3] = new FluidTank(Fluids.NONE, 4_000);
		
		this.fusionModule = new ModuleMachineFusion(0, this, slots)
				.fluidInput(tanks[0], tanks[1], tanks[2])
				.fluidOutput(tanks[3])
				.itemOutput(2);
	}

	@Override
	public String getName() {
		return "container.fusionTorus";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			for(int i = 0; i < 4; i++) {
				if(klystronNodes[i] == null || klystronNodes[i].expired) klystronNodes[i] = createNode(KlystronNetworkProvider.THE_PROVIDER, ForgeDirection.getOrientation(i + 2));
				if(plasmaNodes[i] == null || plasmaNodes[i].expired) plasmaNodes[i] = createNode(PlasmaNetworkProvider.THE_PROVIDER, ForgeDirection.getOrientation(i + 2));

				if(klystronNodes[i].net != null) klystronNodes[i].net.addReceiver(this);
				if(plasmaNodes[i].net != null) plasmaNodes[i].net.addProvider(this);
			}
			
			this.temperature += this.temp_passive_heating;
			if(this.temperature > KELVIN + 20) this.temperature = KELVIN + 20;
			
			if(this.temperature > this.temperature_target) {
				int cyclesTemp = (int) Math.ceil((Math.min(this.temperature - temperature_target, temp_change_max)) / temp_change_per_mb);
				int cyclesCool = coolantTanks[0].getFill();
				int cyclesHot = coolantTanks[1].getMaxFill() - coolantTanks[1].getFill();
				int cycles = BobMathUtil.min(cyclesTemp, cyclesCool, cyclesHot);

				coolantTanks[0].setFill(coolantTanks[0].getFill() - cycles);
				coolantTanks[1].setFill(coolantTanks[1].getFill() + cycles);
				this.temperature -= this.temp_change_per_mb * cycles;
			}
			
			for(DirPos pos : getConPos()) {
				
				if(worldObj.getTotalWorldTime() % 20 == 0) {
					this.trySubscribe(worldObj, pos);
					this.trySubscribe(coolantTanks[0].getTankType(), worldObj, pos);
					if(tanks[0].getTankType() != Fluids.NONE) this.trySubscribe(tanks[0].getTankType(), worldObj, pos);
					if(tanks[1].getTankType() != Fluids.NONE) this.trySubscribe(tanks[1].getTankType(), worldObj, pos);
					if(tanks[2].getTankType() != Fluids.NONE) this.trySubscribe(tanks[2].getTankType(), worldObj, pos);
				}

				if(coolantTanks[1].getFill() > 0) this.tryProvide(coolantTanks[1], worldObj, pos);
				if(tanks[3].getFill() > 0) this.tryProvide(tanks[3], worldObj, pos);
			}

			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
			
			int receiverCount = 0;
			int collectors = 0;
			
			for(int i = 0; i < 4; i++) {
				connections[i] = false;
				if(klystronNodes[i] != null && klystronNodes[i].hasValidNet() && !klystronNodes[i].net.providerEntries.isEmpty()) connections[i] = true;
				if(!connections[i] && plasmaNodes[i] != null && plasmaNodes[i].hasValidNet() && !plasmaNodes[i].net.receiverEntries.isEmpty()) connections[i] = true;
				
				if(plasmaNodes[i] != null && plasmaNodes[i].hasValidNet() && !plasmaNodes[i].net.receiverEntries.isEmpty()) {
					
					for(Object o : plasmaNodes[i].net.receiverEntries.entrySet()) {
						Entry<Object, Long> entry = (Entry<Object, Long>) o;
						if(entry.getKey() instanceof IFusionPowerReceiver) receiverCount++;
						if(entry.getKey() instanceof TileEntityFusionCollector) collectors++;
						break;
					}
				}
			}
			
			FusionRecipe recipe = (FusionRecipe) this.fusionModule.getRecipe();

			double powerFactor = TileEntityFusionTorus.getSpeedScaled(this.getMaxPower(), power);
			double fuel0Factor = recipe != null && recipe.inputFluid.length > 0 ?  getSpeedScaled(tanks[0].getMaxFill(), tanks[0].getFill()) : 1D;
			double fuel1Factor = recipe != null && recipe.inputFluid.length > 1 ?  getSpeedScaled(tanks[1].getMaxFill(), tanks[1].getFill()) : 1D;
			double fuel2Factor = recipe != null && recipe.inputFluid.length > 2 ?  getSpeedScaled(tanks[2].getMaxFill(), tanks[2].getFill()) : 1D;
			
			double factor = BobMathUtil.min(powerFactor, fuel0Factor, fuel1Factor, fuel2Factor);
			
			boolean ignition = recipe != null ? recipe.ignitionTemp <= this.klystronEnergy : true;
			
			this.plasmaEnergy = 0;
			this.fuelConsumption = 0;
			this.fusionModule.preUpdate(factor, collectors * 0.5D);
			this.fusionModule.update(1D, 1D, this.isCool() && ignition, slots[1]);
			this.didProcess = this.fusionModule.didProcess;
			if(this.fusionModule.markDirty) this.markDirty();
			if(didProcess && recipe != null) {
				this.plasmaEnergy = (long) Math.ceil(recipe.outputTemp * factor);
				this.fuelConsumption = factor;
			}
			
			double outputIntensity = this.getOuputIntensity(receiverCount);
			double outputFlux = recipe != null ? recipe.neutronFlux * factor : 0D;
			
			if(this.plasmaEnergy > 0) for(int i = 0; i < 4; i++) {
				
				if(plasmaNodes[i] != null && plasmaNodes[i].hasValidNet() && !plasmaNodes[i].net.receiverEntries.isEmpty()) {
					
					for(Object o : plasmaNodes[i].net.receiverEntries.entrySet()) {
						Entry<Object, Long> entry = (Entry<Object, Long>) o;
						
						if(entry.getKey() instanceof IFusionPowerReceiver) {
							long powerReceived = (long) Math.ceil(this.plasmaEnergy * outputIntensity);
							((IFusionPowerReceiver) entry.getKey()).receiveFusionPower(powerReceived, outputFlux);
						}
					}
				}
			}
			
			this.networkPackNT(150);
			
			this.klystronEnergy = 0;
			
		} else {

			double powerFactor = TileEntityFusionTorus.getSpeedScaled(this.getMaxPower(), power);
			if(this.didProcess) this.magnetSpeed += MAGNET_ACCELERATION;
			else this.magnetSpeed -= MAGNET_ACCELERATION;
			
			this.magnetSpeed = MathHelper.clamp_float(this.magnetSpeed, 0F, 30F * (float) powerFactor);
			
			this.prevMagnet = this.magnet;
			this.magnet += this.magnetSpeed;
			
			if(this.magnet >= 360F) {
				this.magnet -= 360F;
				this.prevMagnet -= 360F;
			}
		}
	}
	
	public static double getOuputIntensity(int receiverCount) {
		if(receiverCount == 1) return 1D; // 100%
		if(receiverCount == 2) return 0.625D; // 125%
		if(receiverCount == 3) return 0.5D; // 150%
		return 0.4375D; // 175%
	}
	
	public GenNode createNode(INetworkProvider provider, ForgeDirection dir) {
		GenNode node = UniNodespace.getNode(worldObj, xCoord + dir.offsetX * 7, yCoord + 2, zCoord + dir.offsetZ * 7, provider);
		if(node != null) return node;
		
		node = new GenNode(provider,
				new BlockPos(xCoord + dir.offsetX * 7, yCoord + 2, zCoord + dir.offsetZ * 7))
				.setConnections(new DirPos(xCoord + dir.offsetX * 8, yCoord + 2, zCoord + dir.offsetZ * 8, dir));
		
		UniNodespace.createNode(worldObj, node);
		
		return node;
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			for(GenNode node : klystronNodes) if(node != null) UniNodespace.destroyNode(worldObj, node);
			for(GenNode node : plasmaNodes) if(node != null) UniNodespace.destroyNode(worldObj, node);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(this.didProcess);
		buf.writeLong(this.klystronEnergy);
		buf.writeLong(this.plasmaEnergy);
		buf.writeDouble(this.fuelConsumption);
		
		this.fusionModule.serialize(buf);
		for(int i = 0; i < 4; i++) this.tanks[i].serialize(buf);
		for(int i = 0; i < 4; i++) buf.writeBoolean(connections[i]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.didProcess = buf.readBoolean();
		this.klystronEnergy = buf.readLong();
		this.plasmaEnergy = buf.readLong();
		this.fuelConsumption = buf.readDouble();
		
		this.fusionModule.deserialize(buf);
		for(int i = 0; i < 4; i++) this.tanks[i].deserialize(buf);
		for(int i = 0; i < 4; i++) connections[i] = buf.readBoolean();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 4; i++) this.tanks[i].readFromNBT(nbt, "ft" + i);

		this.power = nbt.getLong("power");
		this.fusionModule.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 4; i++) this.tanks[i].writeToNBT(nbt, "ft" + i);

		nbt.setLong("power", power);
		this.fusionModule.writeToNBT(nbt);
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}

	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {coolantTanks[1], tanks[3]}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {coolantTanks[0], tanks[0], tanks[1], tanks[2]}; }
	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {coolantTanks[0], coolantTanks[1], tanks[0], tanks[1], tanks[2], tanks[3]}; }
	
	/** Linearly scales up from 0% to 100% from 0 to 0.5, then stays at 100% */
	public static double getSpeedScaled(double max, double level) {
		if(max == 0) return 0D;
		if(level >= max * 0.5) return 1D;
		return level / max * 2D;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
				new DirPos(xCoord, yCoord + 5, zCoord, Library.POS_Y),

				new DirPos(xCoord + 6, yCoord - 1, zCoord, Library.NEG_Y),
				new DirPos(xCoord + 6, yCoord + 5, zCoord, Library.POS_Y),
				new DirPos(xCoord + 6, yCoord - 1, zCoord + 2, Library.NEG_Y),
				new DirPos(xCoord + 6, yCoord + 5, zCoord + 2, Library.POS_Y),
				new DirPos(xCoord + 6, yCoord - 1, zCoord - 2, Library.NEG_Y),
				new DirPos(xCoord + 6, yCoord + 5, zCoord - 2, Library.POS_Y),

				new DirPos(xCoord - 6, yCoord - 1, zCoord, Library.NEG_Y),
				new DirPos(xCoord - 6, yCoord + 5, zCoord, Library.POS_Y),
				new DirPos(xCoord - 6, yCoord - 1, zCoord + 2, Library.NEG_Y),
				new DirPos(xCoord - 6, yCoord + 5, zCoord + 2, Library.POS_Y),
				new DirPos(xCoord - 6, yCoord - 1, zCoord - 2, Library.NEG_Y),
				new DirPos(xCoord - 6, yCoord + 5, zCoord - 2, Library.POS_Y),

				new DirPos(xCoord, yCoord - 1, zCoord + 6, Library.NEG_Y),
				new DirPos(xCoord, yCoord + 5, zCoord + 6, Library.POS_Y),
				new DirPos(xCoord + 2, yCoord - 1, zCoord + 6, Library.NEG_Y),
				new DirPos(xCoord + 2, yCoord + 5, zCoord + 6, Library.POS_Y),
				new DirPos(xCoord - 2, yCoord - 1, zCoord + 6, Library.NEG_Y),
				new DirPos(xCoord - 2, yCoord + 5, zCoord + 6, Library.POS_Y),

				new DirPos(xCoord, yCoord - 1, zCoord - 6, Library.NEG_Y),
				new DirPos(xCoord, yCoord + 5, zCoord - 6, Library.POS_Y),
				new DirPos(xCoord + 2, yCoord - 1, zCoord - 6, Library.NEG_Y),
				new DirPos(xCoord + 2, yCoord + 5, zCoord - 6, Library.POS_Y),
				new DirPos(xCoord - 2, yCoord - 1, zCoord - 6, Library.NEG_Y),
				new DirPos(xCoord - 2, yCoord + 5, zCoord - 6, Library.POS_Y),
		};
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // battery
		if(slot == 1 && stack.getItem() == ModItems.blueprints) return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {2};
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 8,
					yCoord,
					zCoord - 8,
					xCoord + 9,
					yCoord + 5,
					zCoord + 9
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
		return new ContainerFusionTorus(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFusionTorus(player.inventory, this);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) return false;
		return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 32 * 32;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index == 0) {
				this.fusionModule.recipe = selection;
				this.markChanged();
			}
		}
	}
}
