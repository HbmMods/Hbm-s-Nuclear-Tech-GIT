package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.recipes.GasCentrifugeRecipes;
import com.hbm.inventory.recipes.GasCentrifugeRecipes.PseudoFluidType;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.inventory.recipes.MachineRecipes.GasCentOutput;
import com.hbm.items.ModItems;
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
	
	public long power;
	public int progress;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 200;
	
	public FluidTank tank;
	public PseudoFluidTank inputTank;
	public PseudoFluidTank outputTank;
	
	private static final int[] slots_top = new int[] {3};
	private static final int[] slots_bottom = new int[] {5, 6, 7, 8};
	private static final int[] slots_side = new int[] {0, 3};
	
	private String customName;
	
	//TODO add inter-TE communications (outputting pseudofluids to other gascents, setting pseudofluidtype for other gascents, etc.)
	//Check the TileEntityPileBase for how to do this, tis pretty easy
	public TileEntityMachineGasCent() {
		super(9); //6 slots
		tank = new FluidTank(FluidType.UF6, 4000, 0);
		inputTank = new PseudoFluidTank(PseudoFluidType.NUF6, 8000);
		
	}
	
	@Override
	public String getName() {
		return "container.gasCentrifuge";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		power = nbt.getLong("power");
		progress = nbt.getShort("progress");
		tank.readFromNBT(nbt, "tank");
		inputTank.readFromNBT(nbt, "inputTank");
		outputTank.readFromNBT(nbt, "outputTank");
		slots = new ItemStack[getSizeInventory()];
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setShort("progress", (short) progress);
		tank.writeToNBT(nbt, "tank");
		inputTank.writeToNBT(nbt, "inputTank");
		outputTank.writeToNBT(nbt, "outputTank");
		NBTTagList list = new NBTTagList();
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int meta)
    {
        return meta == 0 ? slots_bottom : (meta == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return j != 0 || i != 1;
	}
	
	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}
	
	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}
	
	private boolean canProcess() {
		
		if(power > 0 && this.tank.getFill() >= MachineRecipes.getFluidConsumedGasCent(tank.getTankType())) {
			
			List<GasCentOutput> list = MachineRecipes.getGasCentOutput(tank.getTankType());
			
			if(list == null)
				return false;
			
			if(list.size() < 1 || list.size() > 4)
				return false;
			
			for(int i = 0; i < list.size(); i++) {
				
				int slot = i + 5;
				
				if(slots[slot] == null)
					continue;
				
				if(slots[slot].getItem() == list.get(i).output.getItem() &&
						slots[slot].getItemDamage() == list.get(i).output.getItemDamage() &&
						slots[slot].stackSize + list.get(i).output.stackSize <= slots[slot].getMaxStackSize())
					continue;
				
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	private void process() {

		List<GasCentOutput> out = MachineRecipes.getGasCentOutput(tank.getTankType());
		this.progress = 0;
		tank.setFill(tank.getFill() - MachineRecipes.getFluidConsumedGasCent(tank.getTankType()));
		
		List<GasCentOutput> random = new ArrayList();
		
		for(int i = 0; i < out.size(); i++) {
			for(int j = 0; j < out.get(i).weight; j++) {
				random.add(out.get(i));
			}
		}
		
		Collections.shuffle(random);
		
		GasCentOutput result = random.get(worldObj.rand.nextInt(random.size()));
		
		int slot = result.slot + 4;
		
		if(slots[slot] == null) {
			slots[slot] = result.output.copy();
		} else {
			slots[slot].stackSize += result.output.stackSize;
		}
	}
	
	private boolean canEnrich() {
		if(power > 0 && this.inputTank.getFill() >= inputTank.getTankType().getFluidConsumed() && this.outputTank.getFill() <= outputTank.getMaxFill()) {
			
			ItemStack[] list = inputTank.getTankType().getOutput();
			
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
		
		for(byte i = 0; i < output.length + 2 && i < 3; i++) {
			if(slots[i + 2] == null) {
				slots[i + 2] = output[i].copy();
			} else {
				slots[i + 2].stackSize += output[i].stackSize;
			}
		}
	}
	
	private void attemptTransfer() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		TileEntity te = worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord, this.zCoord + dir.offsetZ);
		
		if(te instanceof TileEntityMachineGasCent) {
			TileEntityMachineGasCent gasCent = (TileEntityMachineGasCent) te;
			
			if(gasCent.tank.getFill() == 0 && gasCent.tank.getTankType() == this.tank.getTankType()) {
				if(gasCent.inputTank.getTankType() != this.outputTank.getTankType()) {
					gasCent.inputTank.setTankType(this.inputTank.getTankType().getOutputFluid());
					gasCent.outputTank.setTankType(this.inputTank.getTankType().getOutputFluid().getOutputFluid());
				}
				//whew boy, so many nested if statements! this calls for a celebration!
				
				if(gasCent.inputTank.getFill() <= gasCent.inputTank.getMaxFill() && this.outputTank.getFill() > 0) {
					int fill = gasCent.inputTank.getMaxFill() - gasCent.inputTank.getFill();
					
					if(this.outputTank.getFill() >= fill) {
						this.outputTank.setFill(this.outputTank.getFill() - fill);
						gasCent.inputTank.setFill(gasCent.inputTank.getFill() + fill);
					} else {
						gasCent.inputTank.setFill(gasCent.inputTank.getFill() + this.outputTank.getFill());
						this.outputTank.setFill(0);
					}
				}
			}
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			this.updateStandardConnections(worldObj, xCoord, yCoord, zCoord);

			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tank.setType(1, 2, slots);
			tank.loadTank(3, 4, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			if(canProcess()) {
				
				isProgressing = true;
				
				this.progress++;
				
				this.power -= 200;
				
				if(this.power < 0) {
					power = 0;
					this.progress = 0;
				}
				
				if(progress >= processingSpeed) {
					process();
				}
				
			} else {
				isProgressing = false;
				this.progress = 0;
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, progress, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, isProgressing ? 1 : 0, 1), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(xCoord, yCoord, zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}
	
	//@Override
	public void updatEntity() {

		if(!worldObj.isRemote) {
			
			this.updateStandardConnections(worldObj, xCoord, yCoord, zCoord);

			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tank.setType(1, 1, slots);
			
			if(inputTank.getTankType() == PseudoFluidType.PF6 || inputTank.getTankType() == PseudoFluidType.NUF6) {
				tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
				
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
			
			if(canProcess()) {
				
				isProgressing = true;
				
				this.progress++;
				
				this.power -= 200;
				
				if(this.power < 0) {
					power = 0;
					this.progress = 0;
				}
				
				if(progress >= processingSpeed) {
					process();
				}
				
			} else {
				isProgressing = false;
				this.progress = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			this.networkPack(data, 50);

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, progress, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, isProgressing ? 1 : 0, 1), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
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

	@Override
	public void setFillstate(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		tank.setTankType(type);
		if(tank.getTankType() == FluidType.UF6) {
			inputTank.setTankType(PseudoFluidType.NUF6);
			outputTank.setTankType(PseudoFluidType.NUF6.getOutputFluid());
		} else if(tank.getTankType() == FluidType.PUF6) {
			inputTank.setTankType(PseudoFluidType.PF6);
			outputTank.setTankType(PseudoFluidType.PF6.getOutputFluid());
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

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
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
		
		/*		  ______      ______
		 * 		 _I____I_    _I____I_
		 * 		/	   \\\  /	   \\\
		 * 	   |IF{    || ||	 } || |
		 * 	   | IF{   || ||    }  || |
		 * 	   |  IF{  || ||   }   || |
		 * 	   |   IF{ || ||  }    || |
		 * 	   |	IF{|| || }     || |
		 *     |	   || ||	   || |
		 * 	   |	 } || ||IF{	   || |
		 * 	   |	}  || || IF{   || |
		 * 	   |   }   || ||  IF{  || |
		 * 	   |  }    || ||   IF{ || |
		 * 	   | }     || ||	IF{|| |
		 * 	   |IF{    || ||	 } || |
		 * 	   | IF{   || ||    }  || |
		 * 	   |  IF{  || ||   }   || |
		 * 	   |   IF{ || ||  }    || |
		 * 	   |	IF{|| || }     || |
		 *     |	   || ||	   || |
		 * 	   |	 } || ||IF{	   || |
		 * 	   |	}  || || IF{   || |
		 * 	   |   }   || ||  IF{  || |
		 * 	   |  }    || ||   IF{ || |
		 * 	   | }     || ||	IF{|| |
		 * 	   |IF{    || ||	 } || |
		 * 	   | IF{   || ||    }  || |
		 * 	   |  IF{  || ||   }   || |
		 * 	   |   IF{ || ||  }    || |
		 * 	   |	IF{|| || }     || |
		 *     |	   || ||	   || |
		 * 	   |	 } || ||IF{	   || |
		 * 	   |	}  || || IF{   || |
		 * 	   |   }   || ||  IF{  || |
		 * 	   |  }    || ||   IF{ || |
		 * 	   | }     || ||	IF{|| |
		 * 	  _|_______||_||_______||_|_
		 * 	 |                          |
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
