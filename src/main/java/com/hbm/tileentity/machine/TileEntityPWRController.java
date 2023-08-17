package com.hbm.tileentity.machine;

import java.util.HashMap;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPWR;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.inventory.gui.GUIPWR;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemPWRFuel.EnumPWRFuel;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPWRController extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {
	
	public FluidTank[] tanks;
	public int coreHeat;
	public static final int coreHeatCapacity = 25_000_000;
	public int hullHeat;
	public static final int hullHeatCapacity = 25_000_000;
	public double flux;
	
	public int rodLevel;
	public int rodTarget;
	
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

		int connectionsDouble = 0;
		int connectionsControlledDouble = 0;
		
		for(Entry<BlockPos, Block> entry : partMap.entrySet()) {
			Block block = entry.getValue();

			if(block == ModBlocks.pwr_fuel) rodCount++;
			if(block == ModBlocks.pwr_heatex) heatexCount++;
			if(block == ModBlocks.pwr_channel) channelCount++;
			if(block == ModBlocks.pwr_neutron_source) sourceCount++;
		}
		
		for(Entry<BlockPos, Block> entry : rodMap.entrySet()) {
			BlockPos fuelPos = entry.getKey();
			
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

		System.out.println("Finalized nuclear reactor!");
		System.out.println("Rods: " + rodCount);
		System.out.println("Connections: " + connections);
		System.out.println("Controlled connections: " + connectionsControlled);
		System.out.println("Heatex: " + heatexCount);
		System.out.println("Channels: " + channelCount);
		System.out.println("Sources: " + sourceCount);
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
					
					this.markChanged();
				}
			}
			
			if(this.amountLoaded <= 0) {
				this.typeLoaded = -1;
			}
			
			/* CORE COOLING */
			double coreCoolingApproachNum = getXOverE(this.heatexCount, 10) / 2D;
			int averageCoreHeat = (this.coreHeat + this.hullHeat) / 2;
			this.coreHeat -= (coreHeat - averageCoreHeat) * coreCoolingApproachNum;
			this.hullHeat -= (hullHeat - averageCoreHeat) * coreCoolingApproachNum;
			
			this.hullHeat *= 0.99D;
			
			this.flux = newFlux;
			
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
			this.networkPack(data, 150);
		}
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
	}
	
	protected void setupTanks() {
		
		FT_Heatable trait = tanks[0].getTankType().getTrait(FT_Heatable.class);
		
		if(trait == null || trait.getEfficiency(HeatingType.PWR) <= 0) {
			tanks[0].setTankType(Fluids.NONE);
			tanks[1].setTankType(Fluids.NONE);
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.assembled = nbt.getBoolean("assembled");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("assembled", assembled);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("control")) {
			this.rodTarget = MathHelper.clamp_int(data.getInteger("control"), 0, 100);
		}
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPWR(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPWR(player.inventory, this);
	}
}
