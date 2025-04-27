package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.container.ContainerIGenerator;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.CompatEnergyControl;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineIGenerator extends TileEntityMachineBase implements IFluidStandardReceiver, IGUIProvider, IInfoProviderEC {
	
	public long power;
	public int spin;
	public int[] burn = new int[4];
	public boolean hasRTG = false;
	public int[] RTGSlots = new int[]{ 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };

	@SideOnly(Side.CLIENT)
	public float rotation;
	@SideOnly(Side.CLIENT)
	public float prevRotation;
	
	public FluidTank[] tanks;
	
	public int age = 0;
	
	public static final int coalConRate = 75;
	
	/* CONFIGURABLE */
	public static long maxPower = 1_000_000;
	public static int waterCap = 16000;
	public static int oilCap = 16000;
	public static int lubeCap = 4000;
	public static int coalGenRate = 100;
	public static double rtgHeatMult = 0.15D;
	public static int waterRate = 10;
	public static int lubeRate = 1;
	public static long fluidHeatDiv = 1_000L;
	
	protected long output;

	public TileEntityMachineIGenerator() {
		super(21);
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(Fluids.WATER, waterCap);
		tanks[1] = new FluidTank(Fluids.HEATINGOIL, oilCap);
		tanks[2] = new FluidTank(Fluids.LUBRICANT, lubeCap);
	}

	@Override
	public String getName() {
		return "container.iGenerator";
	}
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * -4, yCoord, zCoord + dir.offsetZ * -4, dir.getOpposite()),
				new DirPos(xCoord + dir.offsetX * -2, yCoord - 1, zCoord + dir.offsetZ * -2, ForgeDirection.DOWN),
				new DirPos(xCoord + dir.offsetX * -1, yCoord - 1, zCoord + dir.offsetZ * -1, ForgeDirection.DOWN),
				new DirPos(xCoord, yCoord - 1, zCoord, ForgeDirection.DOWN),
				new DirPos(xCoord + dir.offsetX * 3, yCoord, zCoord + dir.offsetZ * 3, dir),
		};
	}

	@Override
	public void updateEntity() {
		
		/*if(!worldObj.isRemote) {
			
			boolean con = GeneralConfig.enableLBSM && GeneralConfig.enableLBSMIGen;
			
			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);
			
			for(DirPos dir : getConPos()) {
				this.sendPower(worldObj, dir.getX(), dir.getY(), dir.getZ(), dir.getDir());
				
				for(FluidTank tank : tanks) {
					this.trySubscribe(tank.getTankType(), worldObj, dir.getX(), dir.getY(), dir.getZ(), dir.getDir());
				}
			}
			
			tanks[1].setType(9, 10, slots);
			tanks[0].loadTank(1, 2, slots);
			tanks[1].loadTank(9, 10, slots);
			tanks[2].loadTank(7, 8, slots);
			
			this.spin = 0;
			
			/// LIQUID FUEL ///
			if(tanks[1].getFill() > 0) {
				int pow = this.getPowerFromFuel(con);
				
				if(pow > 0) {
					tanks[1].setFill(tanks[1].getFill() - 1);
					this.spin += pow;
				}
			}
			
			///SOLID FUEL ///
			for(int i = 0; i < 4; i++) {
				
				// POWER GEN //
				if(burn[i] > 0) {
					burn[i]--;
					this.spin += con ? coalConRate : coalGenRate;
					
				// REFUELING //
				} else {
					int slot = i + 3;
					
					if(slots[slot] != null) {
						ItemStack fuel = slots[slot];
						int burnTime = TileEntityFurnace.getItemBurnTime(fuel) / 2;
						
						if(burnTime > 0) {
							
							if(fuel.getItem() == Items.coal) //1200 (1600)
								burnTime *= con ? 1.5 : 1.1;
							if(fuel.getItem() == ModItems.solid_fuel) //3200 (3200)
								burnTime *= con ? 2 : 1.1;
							if(fuel.getItem() == ModItems.solid_fuel_presto) //16000 (8000)
								burnTime *= con ? 4 : 1.1;
							if(fuel.getItem() == ModItems.solid_fuel_presto_triplet) //80000 (40000)
								burnTime *= con ? 4 : 1.1;
							
							burn[i] = burnTime;
							
							slots[slot].stackSize--;
							
							if(slots[slot].stackSize <= 0) {
								
								if(slots[slot].getItem().hasContainerItem(slots[slot])) {
									slots[slot] = slots[slot].getItem().getContainerItem(slots[slot]);
								} else {
									slots[slot] = null;
								}
							}
						}
					}
				}
			}
			
			// RTG ///
			this.hasRTG = RTGUtil.hasHeat(slots, RTGSlots);
			this.spin += RTGUtil.updateRTGs(slots, RTGSlots) * (con ? 0.2 : rtgHeatMult);
			
			if(this.spin > 0) {
				
				double genMult = 0.5D;
				
				
				if(this.tanks[0].getFill() >= 10) {
					genMult += 0.5D;
					this.tanks[0].setFill(this.tanks[0].getFill() - waterRate);
				}
				
				if(this.tanks[2].getFill() >= 1) {
					genMult += 0.25D;
					this.tanks[2].setFill(this.tanks[2].getFill() - lubeRate);
				}
				
				this.output = (long) (this.spin * genMult);
				this.power += this.output;
				
				if(this.power > this.maxPower)
					this.power = this.maxPower;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("spin", spin);
			data.setIntArray("burn", burn);
			data.setBoolean("hasRTG", hasRTG);
			this.networkPack(data, 150);
			
			for(int i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
			
		} else {
			
			this.prevRotation = this.rotation;
			
			if(this.spin > 0) {
				this.rotation += 15;
			}
			
			if(this.rotation >= 360) {
				this.rotation -= 360;
				this.prevRotation -= 360;
			}
		}*/
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i >= 3 && i <= 6 && TileEntityFurnace.getItemBurnTime(itemStack) > 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 3, 4, 5, 6 };
	}

	// o7
	/*
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		super.networkUnpack(nbt);

		this.power = nbt.getLong("power");
		this.spin = nbt.getInteger("spin");
		this.burn = nbt.getIntArray("burn");
		this.hasRTG = nbt.getBoolean("hasRTG");
	}
	*/
	
	public int getPowerFromFuel(boolean con) {
		FluidType type = tanks[1].getTankType();
		return type.hasTrait(FT_Flammable.class) ? (int)(type.getTrait(FT_Flammable.class).getHeatEnergy() / (con ? 5000L : fluidHeatDiv)) : 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 3; i++)
			tanks[i].readFromNBT(nbt, "tank_" + i);
		
		this.power = nbt.getLong("power");
		this.burn = nbt.getIntArray("burn");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 3; i++)
			tanks[i].writeToNBT(nbt, "tank_" + i);
		
		nbt.setLong("power", power);
		nbt.setIntArray("burn", burn);
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
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0], tanks[1], tanks[2] };
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerIGenerator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setBoolean(CompatEnergyControl.B_ACTIVE, this.output > 0);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, this.output);
	}
}
