package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPWR;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_PWRModerator;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingStep;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.inventory.gui.GUIPWR;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPWRController extends TileEntityMachineBase implements IGUIProvider, IControlReceiver, IFluidStandardTransceiver {
	
	public FluidTank[] tanks;
	public int coreHeat;
	public static final int coreHeatCapacity = 10_000_000;
	public int hullHeat;
	public static final int hullHeatCapacity = 10_000_000;
	public double flux;
	
	public int rodLevel = 100;
	public int rodTarget = 100;
	
	public int typeLoaded;
	public int amountLoaded;
	public double progress;
	public double processTime;
	
	public int rodCount;
	public int connections;
	public int connectionsControlled;
	public int heatexCount;
	public int channelCount;
	public int sourceCount;
	
	public boolean assembled;
	
	private AudioWrapper audio;

	protected List<BlockPos> ports = new ArrayList();
	protected List<BlockPos> rods = new ArrayList();

	public TileEntityPWRController() {
		super(3);
		
		this.tanks = new FluidTank[2];
		this.tanks[0] = new FluidTank(Fluids.COOLANT, 128_000);
		this.tanks[1] = new FluidTank(Fluids.COOLANT_HOT, 128_000);
	}
	
	/** The initial creation of the reactor, does all the pre-calculation and whatnot */
	public void setup(HashMap<BlockPos, Block> partMap, HashMap<BlockPos, Block> rodMap) {
		
		rodCount = 0;
		connections = 0;
		connectionsControlled = 0;
		heatexCount = 0;
		channelCount = 0;
		sourceCount = 0;
		ports.clear();
		rods.clear();

		int connectionsDouble = 0;
		int connectionsControlledDouble = 0;
		
		for(Entry<BlockPos, Block> entry : partMap.entrySet()) {
			Block block = entry.getValue();

			if(block == ModBlocks.pwr_fuel) rodCount++;
			if(block == ModBlocks.pwr_heatex) heatexCount++;
			if(block == ModBlocks.pwr_channel) channelCount++;
			if(block == ModBlocks.pwr_neutron_source) sourceCount++;
			if(block == ModBlocks.pwr_port) ports.add(entry.getKey());
		}
		
		for(Entry<BlockPos, Block> entry : rodMap.entrySet()) {
			BlockPos fuelPos = entry.getKey();
			
			rods.add(fuelPos);
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				
				boolean controlled = false;
				
				for(int i = 1; i < 16; i++) {
					BlockPos checkPos = fuelPos.offset(dir, i);
					Block atPos = partMap.get(checkPos);
					if(atPos == null || atPos == ModBlocks.pwr_casing) break;
					if(atPos == ModBlocks.pwr_control) controlled = true;
					if(atPos == ModBlocks.pwr_fuel) {
						if(controlled) {
							connectionsControlledDouble++;
						} else {
							connectionsDouble++;
						}
						break;
					}
					if(atPos == ModBlocks.pwr_reflector) {
						if(controlled) {
							connectionsControlledDouble += 2;
						} else {
							connectionsDouble += 2;
						}
						break;
					}
				}
			}
		}

		connections = connectionsDouble / 2;
		connectionsControlled = connectionsControlledDouble / 2;

		/*System.out.println("Finalized nuclear reactor!");
		System.out.println("Rods: " + rodCount);
		System.out.println("Connections: " + connections);
		System.out.println("Controlled connections: " + connectionsControlled);
		System.out.println("Heatex: " + heatexCount);
		System.out.println("Channels: " + channelCount);
		System.out.println("Sources: " + sourceCount);*/
	}

	@Override
	public String getName() {
		return "container.pwrController";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.tanks[0].setType(2, slots);
			setupTanks();
			
			if(this.assembled) {
				for(BlockPos pos : ports) {
					for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						BlockPos portPos = pos.offset(dir);
						
						if(tanks[1].getFill() > 0) this.sendFluid(tanks[1], worldObj, portPos.getX(), portPos.getY(), portPos.getZ(), dir);
						if(worldObj.getTotalWorldTime() % 20 == 0) this.trySubscribe(tanks[0].getTankType(), worldObj, portPos.getX(), portPos.getY(), portPos.getZ(), dir);
					}
				}
				
				if((typeLoaded == -1 || amountLoaded <= 0) && slots[0] != null && slots[0].getItem() == ModItems.pwr_fuel) {
					typeLoaded = slots[0].getItemDamage();
					amountLoaded++;
					this.decrStackSize(0, 1);
					this.markChanged();
				} else if(slots[0] != null && slots[0].getItem() == ModItems.pwr_fuel && slots[0].getItemDamage() == typeLoaded && amountLoaded < rodCount){
					amountLoaded++;
					this.decrStackSize(0, 1);
					this.markChanged();
				}
	
				if(this.rodTarget > this.rodLevel) this.rodLevel++;
				if(this.rodTarget < this.rodLevel) this.rodLevel--;
				
				int newFlux = this.sourceCount * 20;
				
				if(typeLoaded != -1 && amountLoaded > 0) {
					
					EnumPWRFuel fuel = EnumUtil.grabEnumSafely(EnumPWRFuel.class, typeLoaded);
					double usedRods = getTotalProcessMultiplier();
					double fluxPerRod = this.flux / this.rodCount;
					double outputPerRod = fuel.function.effonix(fluxPerRod);
					double totalOutput = outputPerRod * amountLoaded * usedRods;
					double totalHeatOutput = totalOutput * fuel.heatEmission;
					
					this.coreHeat += totalHeatOutput;
					newFlux += totalOutput;
					
					this.processTime = (int) fuel.yield;
					this.progress += totalOutput;
					
					if(this.progress >= this.processTime) {
						this.progress -= this.processTime;
						
						if(slots[1] == null) {
							slots[1] = new ItemStack(ModItems.pwr_fuel_hot, 1, typeLoaded);
						} else if(slots[1].getItem() == ModItems.pwr_fuel_hot && slots[1].getItemDamage() == typeLoaded && slots[1].stackSize < slots[1].getMaxStackSize()) {
							slots[1].stackSize++;
						}
						
						this.amountLoaded--;
						this.markChanged();
					}
				}
				
				if(this.amountLoaded <= 0) {
					this.typeLoaded = -1;
				}
				
				if(amountLoaded > rodCount) amountLoaded = rodCount;
				
				/* CORE COOLING */
				double coreCoolingApproachNum = getXOverE((double) this.heatexCount * 5 / (double) this.rodCount, 2) / 2D;
				int averageCoreHeat = (this.coreHeat + this.hullHeat) / 2;
				this.coreHeat -= (coreHeat - averageCoreHeat) * coreCoolingApproachNum;
				this.hullHeat -= (hullHeat - averageCoreHeat) * coreCoolingApproachNum;
				
				updateCoolant();
	
				this.coreHeat *= 0.999D;
				this.hullHeat *= 0.999D;
				
				this.flux = newFlux;
				
				if(tanks[0].getTankType().hasTrait(FT_PWRModerator.class) && tanks[0].getFill() > 0) {
					this.flux *= tanks[0].getTankType().getTrait(FT_PWRModerator.class).getMultiplier();
				}
				
				if(this.coreHeat > this.coreHeatCapacity) {
					meltDown();
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			tanks[0].writeToNBT(data, "t0");
			tanks[1].writeToNBT(data, "t1");
			data.setInteger("rodCount", rodCount);
			data.setInteger("coreHeat", coreHeat);
			data.setInteger("hullHeat", hullHeat);
			data.setDouble("flux", flux);
			data.setDouble("processTime", processTime);
			data.setDouble("progress", progress);
			data.setInteger("typeLoaded", typeLoaded);
			data.setInteger("amountLoaded", amountLoaded);
			data.setInteger("rodLevel", rodLevel);
			data.setInteger("rodTarget", rodTarget);
			this.networkPack(data, 150);
		} else {
			
			if(amountLoaded > 0) {
				
				if(audio == null) {
					audio = createAudioLoop();
					audio.startSound();
				} else if(!audio.isPlaying()) {
					audio = rebootAudio(audio);
				}
				
				audio.keepAlive();
				
			} else {
				
				if(audio != null) {
					audio.stopSound();
					audio = null;
				}
			}
		}
	}
	
	protected void meltDown() {
		
		worldObj.func_147480_a(xCoord, yCoord, zCoord, false);

		double x = 0;
		double y = 0;
		double z = 0;
		
		for(BlockPos pos : this.rods) {
			Block b = worldObj.getBlock(pos.getX(), pos.getY(), pos.getZ());
			b.breakBlock(worldObj, pos.getX(), pos.getY(), pos.getZ(), b, worldObj.getBlockMetadata(pos.getX(), pos.getY(), pos.getZ()));
			worldObj.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.corium_block, 5, 3);

			x += pos.getX() + 0.5;
			y += pos.getY() + 0.5;
			z += pos.getZ() + 0.5;
		}

		x /= rods.size();
		y /= rods.size();
		z /= rods.size();
		
		worldObj.newExplosion(null, x, y, z, 15F, true, true);
	}
	
	@Override
	public AudioWrapper createAudioLoop() {
		return MainRegistry.proxy.getLoopedSound("hbm:block.reactorLoop", xCoord, yCoord, zCoord, 1F, 10F, 1.0F, 20);
	}

	@Override
	public void onChunkUnload() {

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}

	@Override
	public void invalidate() {

		super.invalidate();

		if(audio != null) {
			audio.stopSound();
			audio = null;
		}
	}
	
	protected void updateCoolant() {
		
		FT_Heatable trait = tanks[0].getTankType().getTrait(FT_Heatable.class);
		if(trait == null || trait.getEfficiency(HeatingType.PWR) <= 0) return;
		
		double coolingEff = (double) this.channelCount / (double) this.rodCount * 0.1D; //10% cooling if numbers match
		if(coolingEff > 1D) coolingEff = 1D;
		
		int heatToUse = Math.min(this.hullHeat, (int) (this.hullHeat * coolingEff * trait.getEfficiency(HeatingType.PWR)));
		HeatingStep step = trait.getFirstStep();
		int coolCycles = tanks[0].getFill() / step.amountReq;
		int hotCycles = (tanks[1].getMaxFill() - tanks[1].getFill()) / step.amountProduced;
		int heatCycles = heatToUse / step.heatReq;
		int cycles = Math.min(coolCycles, Math.min(hotCycles, heatCycles));
		
		this.hullHeat -= step.heatReq * cycles;
		this.tanks[0].setFill(tanks[0].getFill() - step.amountReq * cycles);
		this.tanks[1].setFill(tanks[1].getFill() + step.amountProduced * cycles);
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");
		rodCount = nbt.getInteger("rodCount");
		coreHeat = nbt.getInteger("coreHeat");
		hullHeat = nbt.getInteger("hullHeat");
		flux = nbt.getDouble("flux");
		processTime = nbt.getDouble("processTime");
		progress = nbt.getDouble("progress");
		typeLoaded = nbt.getInteger("typeLoaded");
		amountLoaded = nbt.getInteger("amountLoaded");
		rodLevel = nbt.getInteger("rodLevel");
		rodTarget = nbt.getInteger("rodTarget");
	}
	
	protected void setupTanks() {
		
		FT_Heatable trait = tanks[0].getTankType().getTrait(FT_Heatable.class);
		
		if(trait == null || trait.getEfficiency(HeatingType.PWR) <= 0) {
			tanks[0].setTankType(Fluids.NONE);
			tanks[1].setTankType(Fluids.NONE);
			return;
		}
		
		tanks[1].setTankType(trait.getFirstStep().typeProduced);
	}
	
	public double getTotalProcessMultiplier() {
		double totalConnections = this.connections + this.connectionsControlled * (1D - (this.rodLevel / 100D));
		double connectionsEff = connectinFunc(totalConnections);
		return connectionsEff;
	}
	
	public double connectinFunc(double connections) {
		return connections / 10D * (1D - getXOverE(connections, 300D)) + connections / 150D * getXOverE(connections, 300D);
	}
	
	public double getXOverE(double x, double d) {
		return 1 - Math.pow(Math.E, -x / d);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return stack.getItem() == ModItems.pwr_fuel;
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {0, 1};
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return slot == 1;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");
		
		this.assembled = nbt.getBoolean("assembled");
		this.coreHeat = nbt.getInteger("coreHeat");
		this.hullHeat = nbt.getInteger("hullHeat");
		this.flux = nbt.getDouble("flux");
		this.rodLevel = nbt.getInteger("rodLevel");
		this.rodTarget = nbt.getInteger("rodTarget");
		this.typeLoaded = nbt.getInteger("typeLoaded");
		this.amountLoaded = nbt.getInteger("amountLoaded");
		this.progress = nbt.getDouble("progress");
		this.processTime = nbt.getDouble("processTime");

		this.rodCount = nbt.getInteger("rodCount");
		this.connections = nbt.getInteger("connections");
		this.connectionsControlled = nbt.getInteger("connectionsControlled");
		this.heatexCount = nbt.getInteger("heatexCount");
		this.channelCount = nbt.getInteger("channelCount");
		this.sourceCount = nbt.getInteger("sourceCount");
		
		ports.clear();
		int portCount = nbt.getInteger("portCount");
		for(int i = 0; i < portCount; i++) {
			int[] port = nbt.getIntArray("p" + i);
			ports.add(new BlockPos(port[0], port[1], port[2]));
		}
		
		rods.clear();
		int rodCount = nbt.getInteger("rodCount");
		for(int i = 0; i < rodCount; i++) {
			if(nbt.hasKey("r" + i)) {
				int[] port = nbt.getIntArray("r" + i);
				rods.add(new BlockPos(port[0], port[1], port[2]));
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		tanks[0].writeToNBT(nbt, "t0");
		tanks[1].writeToNBT(nbt, "t1");
		
		nbt.setBoolean("assembled", assembled);
		nbt.setInteger("coreHeat", coreHeat);
		nbt.setInteger("hullHeat", hullHeat);
		nbt.setDouble("flux", flux);
		nbt.setInteger("rodLevel", rodLevel);
		nbt.setInteger("rodTarget", rodTarget);
		nbt.setInteger("typeLoaded", typeLoaded);
		nbt.setInteger("amountLoaded", amountLoaded);
		nbt.setDouble("progress", progress);
		nbt.setDouble("processTime", processTime);

		nbt.setInteger("rodCount", rodCount);
		nbt.setInteger("connections", connections);
		nbt.setInteger("connectionsControlled", connectionsControlled);
		nbt.setInteger("heatexCount", heatexCount);
		nbt.setInteger("channelCount", channelCount);
		nbt.setInteger("sourceCount", sourceCount);
		
		nbt.setInteger("portCount", ports.size());
		for(int i = 0; i < ports.size(); i++) {
			BlockPos pos = ports.get(i);
			nbt.setIntArray("p" + i, new int[] { pos.getX(), pos.getY(), pos.getZ() });
		}
		
		nbt.setInteger("rodCount", rods.size());
		for(int i = 0; i < rods.size(); i++) {
			BlockPos pos = rods.get(i);
			nbt.setIntArray("r" + i, new int[] { pos.getX(), pos.getY(), pos.getZ() });
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("control")) {
			this.rodTarget = MathHelper.clamp_int(data.getInteger("control"), 0, 100);
			this.markChanged();
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPWR(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPWR(player.inventory, this);
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
}
