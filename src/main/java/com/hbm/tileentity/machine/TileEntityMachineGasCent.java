package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.GasCentrifugeRecipes;
import com.hbm.inventory.recipes.GasCentrifugeRecipes.PseudoFluidType;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIdentifier;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineGasCent extends TileEntityMachineBase implements IEnergyUser, IFluidContainer, IFluidAcceptor {
	
	public byte age;
	public long power;
	public int progress;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 200;
	
	public FluidTank tank;
	public PseudoFluidTank inputTank;
	public PseudoFluidTank outputTank;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {2, 3, 4};
	private static final int[] slots_side = new int[] { };
	
	public TileEntityMachineGasCent() {
		super(6); 
		tank = new FluidTank(Fluids.UF6, 2000, 0);
		inputTank = new PseudoFluidTank(PseudoFluidType.NUF6, 8000);
		outputTank = new PseudoFluidTank(PseudoFluidType.LEUF6, 8000);
	}
	
	@Override
	public String getName() {
		return "container.gasCentrifuge";
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : side == 1 ? slots_top : slots_side;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		progress = nbt.getShort("progress");
		tank.readFromNBT(nbt, "tank");
		inputTank.readFromNBT(nbt, "inputTank");
		outputTank.readFromNBT(nbt, "outputTank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setShort("progress", (short) progress);
		tank.writeToNBT(nbt, "tank");
		inputTank.writeToNBT(nbt, "inputTank");
		outputTank.writeToNBT(nbt, "outputTank");
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return (i != 0 && i != 1) || j == 1;
	}
	
	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / getProcessingSpeed();
	}
	
	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getTankScaled(int i, int id) {
		if(id == 0) {
			return (this.inputTank.getFill() * i) / inputTank.getMaxFill();
		} else if(id == 1) {
			return (this.outputTank.getFill() * i) / outputTank.getMaxFill();
		}
		return i;
	}
	
	private boolean canEnrich() {
		if(power > 0 && this.inputTank.getFill() >= inputTank.getTankType().getFluidConsumed() && this.outputTank.getFill() + this.inputTank.getTankType().getFluidProduced() <= outputTank.getMaxFill()) {
			
			ItemStack[] list = inputTank.getTankType().getOutput();
			
			if(this.inputTank.getTankType().getIfHighSpeed())
				if(!(slots[5] != null && slots[5].getItem() == ModItems.upgrade_gc_speed))
					return false;
			
			if(list == null)
				return false;
			
			if(list.length < 1 || list.length > 3)
				return false;
			
			for(int i = 0; i < list.length; i++) {
				
				int slot = i + 2;
				
				if(slots[slot] == null)
					continue;
				
				if(slots[slot].getItem() == list[i].getItem() &&
						slots[slot].getItemDamage() == list[i].getItemDamage() &&
						slots[slot].stackSize + list[i].stackSize <= slots[slot].getMaxStackSize())
					continue;
				
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	private void enrich() {
		ItemStack[] output = inputTank.getTankType().getOutput();
		
		this.progress = 0;
		inputTank.setFill(inputTank.getFill() - inputTank.getTankType().getFluidConsumed()); 
		outputTank.setFill(outputTank.getFill() + inputTank.getTankType().getFluidProduced()); 
		
		for(byte i = 0; i < output.length && i < 3; i++) {
			if(slots[i + 2] == null) {
				slots[i + 2] = output[i].copy();
			} else {
				slots[i + 2].stackSize += output[i].stackSize;
			}
		}
	}
	
	private void attemptConversion() {
		if(inputTank.getFill() <= inputTank.getMaxFill() && tank.getFill() > 0) {
			int fill = inputTank.getMaxFill() - inputTank.getFill();
			
			if(tank.getFill() >= fill) {
				tank.setFill(tank.getFill() - fill);
				inputTank.setFill(inputTank.getFill() + fill);
			} else {
				inputTank.setFill(inputTank.getFill() + tank.getFill());
				tank.setFill(0);
			}
		}
	}
	
	private boolean attemptTransfer(TileEntity te) {
		if(te instanceof TileEntityMachineGasCent) {
			TileEntityMachineGasCent gasCent = (TileEntityMachineGasCent) te;
			
			if(gasCent.tank.getFill() == 0 && gasCent.tank.getTankType() == this.tank.getTankType()) {
				if(gasCent.inputTank.getTankType() != this.outputTank.getTankType()) {
					gasCent.inputTank.setTankType(this.outputTank.getTankType());
					gasCent.outputTank.setTankType(this.outputTank.getTankType().getOutputFluid());
				}
				
				if(gasCent.inputTank.getFill() < gasCent.inputTank.getMaxFill() && this.outputTank.getFill() > 0) {
					int fill = gasCent.inputTank.getMaxFill() - gasCent.inputTank.getFill();
					
					if(this.outputTank.getFill() >= fill) {
						this.outputTank.setFill(this.outputTank.getFill() - fill);
						gasCent.inputTank.setFill(gasCent.inputTank.getFill() + fill);
					} else {
						gasCent.inputTank.setFill(gasCent.inputTank.getFill() + this.outputTank.getFill());
						this.outputTank.setFill(0);
					}
				}
				return false;
			}
		}
		return true;
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
		this.isProgressing = data.getBoolean("isProgressing");
		this.inputTank.setTankType(PseudoFluidType.valueOf(data.getString("inputType")));
		this.outputTank.setTankType(PseudoFluidType.valueOf(data.getString("outputType")));
		this.inputTank.setFill(data.getInteger("inputFill"));
		this.outputTank.setFill(data.getInteger("outputFill"));
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			this.updateStandardConnections(worldObj, xCoord, yCoord, zCoord);

			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			setTankType(1);
			
			if(inputTank.getTankType() == PseudoFluidType.PF6 || inputTank.getTankType() == PseudoFluidType.NUF6) {
				tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
				attemptConversion();
			}
			
			if(canEnrich()) {
				
				isProgressing = true;
				
				this.progress++;
				
				if(slots[5] != null && slots[5].getItem() == ModItems.upgrade_gc_speed)
					this.power -= 300;
				else
					this.power -= 200;
				
				if(this.power < 0) {
					power = 0;
					this.progress = 0;
				}
				
				if(progress >= getProcessingSpeed())
					enrich();

				
			} else {
				isProgressing = false;
				this.progress = 0;
			}
			
			age++;
			if(age >= 10) {
				age = 0;
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
				TileEntity te = worldObj.getTileEntity(this.xCoord - dir.offsetX, this.yCoord, this.zCoord - dir.offsetZ);
				
				if(attemptTransfer(te) && this.inputTank.getTankType() == PseudoFluidType.LEUF6) {
					if(this.outputTank.getFill() >= 100 && (slots[4] == null || (slots[4].getItem() == ModItems.nugget_uranium_fuel && slots[4].stackSize + 1 <= slots[4].getMaxStackSize()))) {
						this.outputTank.setFill(this.outputTank.getFill() - 100);
						if(slots[4] == null) {
							slots[4] = new ItemStack(ModItems.nugget_uranium_fuel, 1);
						} else {
							slots[4].stackSize += 1;
						}
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setBoolean("isProgressing", isProgressing);
			data.setInteger("inputFill", inputTank.getFill());
			data.setInteger("outputFill", outputTank.getFill());
			data.setString("inputType", inputTank.getTankType().toString());
			data.setString("outputType", outputTank.getTankType().toString());
			this.networkPack(data, 50);

			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(xCoord, yCoord, zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
		
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	public int getProcessingSpeed() {
		if(slots[5] != null && slots[5].getItem() == ModItems.upgrade_gc_speed) {
			return processingSpeed - 75;
		}
		return processingSpeed;
	}

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
	}
	
	public void setTankType(int in) {
		
		if(slots[in] != null && slots[in].getItem() instanceof ItemFluidIdentifier) {
			FluidType newType = ItemFluidIdentifier.getType(slots[in]);
			
			if(tank.getTankType() != newType) {
				tank.setTankType(newType);
				tank.setFill(0);
				
				if(newType == Fluids.UF6) {
					inputTank.setTankType(PseudoFluidType.NUF6);
					outputTank.setTankType(PseudoFluidType.NUF6.getOutputFluid());
				}
				if(newType == Fluids.PUF6) {
					inputTank.setTankType(PseudoFluidType.PF6);
					outputTank.setTankType(PseudoFluidType.PF6.getOutputFluid());
				}
			}
			return;
		}
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tank);
		
		return list;
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 5, zCoord + 1);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public class PseudoFluidTank {
		PseudoFluidType type;
		int fluid;
		int maxFluid;
		
		public PseudoFluidTank(PseudoFluidType type, int maxFluid) {
			this.type = type;
			this.maxFluid = maxFluid;
		}
		
		public void setFill(int i) {
			fluid = i;
		}
		
		public void setTankType(PseudoFluidType type) {
			
			if(this.type.name().equals(type.name()))
				return;
			
			this.type = type;
			this.setFill(0);
		}
		
		public PseudoFluidType getTankType() {
			return type;
		}
		
		public int getFill() {
			return fluid;
		}
		
		public int getMaxFill() {
			return maxFluid;
		}
		
		//Called by TE to save fillstate
		public void writeToNBT(NBTTagCompound nbt, String s) {
			nbt.setInteger(s, fluid);
			nbt.setInteger(s + "_max", maxFluid);
			nbt.setString(s + "_type", type.toString());
		}
		
		//Called by TE to load fillstate
		public void readFromNBT(NBTTagCompound nbt, String s) {
			fluid = nbt.getInteger(s);
			int max = nbt.getInteger(s + "_max");
			if(max > 0)
				maxFluid = nbt.getInteger(s + "_max");
			type = PseudoFluidType.valueOf(nbt.getString(s + "_type"));
		}
		
		/*        ______      ______
		 *       _I____I_    _I____I_
		 *      /      \\\  /      \\\
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{    || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{	   || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *     |IF{    || ||     } || |
		 *     | IF{   || ||    }  || |
		 *     |  IF{  || ||   }   || |
		 *     |   IF{ || ||  }    || |
		 *     |    IF{|| || }     || |
		 *     |       || ||       || |
		 *     |     } || ||IF{	   || |
		 *     |    }  || || IF{   || |
		 *     |   }   || ||  IF{  || |
		 *     |  }    || ||   IF{ || |
		 *     | }     || ||    IF{|| |
		 *    _|_______||_||_______||_|_
		 *   |                          |
		 *   |                          |
		 *   |       |==========|       |
		 *   |       |NESTED    |       |
		 *   |       |IF  (:    |       |
		 *   |       |STATEMENTS|       |
		 *   |       |==========|       |
		 *   |                          |
		 *   |                          |
		 *   ----------------------------
		 */
	}
}
