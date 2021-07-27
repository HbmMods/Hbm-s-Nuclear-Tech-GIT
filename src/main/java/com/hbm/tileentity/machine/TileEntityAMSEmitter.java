package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAMSEmitter extends TileEntity implements ISidedInventory, IConsumer, IFluidContainer, IFluidAcceptor {

	private ItemStack slots[];

	public long power = 0;
	public static final long maxPower = 100000000;
	public int efficiency = 0;
	public static final int maxEfficiency = 100;
	public int heat = 0;
	public static final int maxHeat = 2500;
	public int age = 0;
	public int warning = 0;
	public boolean locked = false;
	public FluidTank tank;
	
	Random rand = new Random();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0 };
	private static final int[] slots_side = new int[] { 0 };
	
	private String customName;
	
	public TileEntityAMSEmitter() {
		slots = new ItemStack[4];
		tank = new FluidTank(FluidType.COOLANT, 16000, 0);
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.amsEmitter";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=128;
		}
	}
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		power = nbt.getLong("power");
		tank.readFromNBT(nbt, "coolant");
		efficiency = nbt.getInteger("efficiency");
		heat = nbt.getInteger("heat");
		locked = nbt.getBoolean("locked");
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
		tank.writeToNBT(nbt, "coolant");
		nbt.setInteger("efficiency", efficiency);
		nbt.setInteger("heat", heat);
		nbt.setBoolean("locked", locked);
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
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			if(!locked) {
				
				tank.setType(0, 1, slots);
				tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
				
				if(power > 0) {
					//" - (maxHeat / 2)" offsets center to 50% instead of 0%
					efficiency = Math.round(calcEffect(power, heat - (maxHeat / 2)) * 100);
					power -= Math.ceil(power * 0.025);
					warning = 0;
				} else {
					efficiency = 0;
					warning = 1;
				}
				
				if(tank.getTankType().name().equals(FluidType.CRYOGEL.name())) {
					
					if(tank.getFill() >= 15) {
						if(heat > 0)
							tank.setFill(tank.getFill() - 15);

						if(heat <= maxHeat / 2)
							if(efficiency > 0)
								heat += efficiency;
							else
								for(int i = 0; i < 10; i++)
									if(heat > 0)
										heat--;
						
						for(int i = 0; i < 10; i++)
							if(heat > maxHeat / 2)
								heat--;
					} else {
						heat += efficiency;
					}
				} else if(tank.getTankType().name().equals(FluidType.COOLANT.name())) {
					
					if(tank.getFill() >= 15) {
						if(heat > 0)
							tank.setFill(tank.getFill() - 15);

						if(heat <= maxHeat / 4)
							if(efficiency > 0)
								heat += efficiency;
							else
								for(int i = 0; i < 5; i++)
									if(heat > 0)
										heat--;
						
						for(int i = 0; i < 5; i++)
							if(heat > maxHeat / 4)
								heat--;
					} else {
						heat += efficiency;
					}
				} else if(tank.getTankType().name().equals(FluidType.WATER.name())) {
					
					if(tank.getFill() >= 45) {
						if(heat > 0)
							tank.setFill(tank.getFill() - 45);

						if(heat <= maxHeat * 0.85)
							if(efficiency > 0)
								heat += efficiency;
							else
								for(int i = 0; i < 2; i++)
									if(heat > 0)
										heat--;
						
						for(int i = 0; i < 2; i++)
							if(heat > maxHeat * 0.85)
								heat--;
					} else {
						heat += efficiency;
					}
				} else {
					heat += efficiency;
					warning = 2;
				}
				
				if(slots[2] != null) {
					if(slots[2].getItem() != ModItems.ams_muzzle) {
						this.efficiency = 0;
						this.warning = 2;
					}
				} else {
					this.efficiency = 0;
					this.warning = 2;
				}
				
				if(tank.getFill() <= 5 || heat > maxHeat * 0.9)
					warning = 2;
				
				if(heat > maxHeat) {
					heat = maxHeat;
					locked = true;
					ExplosionLarge.spawnBurst(worldObj, xCoord + 0.5, yCoord, zCoord + 0.5, 36, 3);
					ExplosionLarge.spawnBurst(worldObj, xCoord + 0.5, yCoord, zCoord + 0.5, 36, 2.5);
					ExplosionLarge.spawnBurst(worldObj, xCoord + 0.5, yCoord, zCoord + 0.5, 36, 2);
					ExplosionLarge.spawnBurst(worldObj, xCoord + 0.5, yCoord, zCoord + 0.5, 36, 1.5);
					ExplosionLarge.spawnBurst(worldObj, xCoord + 0.5, yCoord, zCoord + 0.5, 36, 1);
		            this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:entity.oldExplosion", 10.0F, 1);
			        this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:block.shutdown", 10.0F, 1.0F);
				}
	
				power = Library.chargeTEFromItems(slots, 3, power, maxPower);
				
			} else {
				//fire particles n stuff
				ExplosionLarge.spawnBurst(worldObj, xCoord + 0.5, yCoord - 0.5, zCoord + 0.5, rand.nextInt(10), 1);
				
				efficiency = 0;
				power = 0;
				warning = 3;
			}

			tank.setTankType(FluidType.CRYOGEL);
			tank.setFill(tank.getMaxFill());

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, locked ? 1 : 0, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, efficiency, 1), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
		}
	}
	
	private float gauss(float a, float x) {
		
		//Greater values -> less difference of temperate impact
		double amplifier = 0.10;
		
		return (float) ( (1/Math.sqrt(a * Math.PI)) * Math.pow(Math.E, -1 * Math.pow(x, 2)/amplifier) );
	}
	
	private float calcEffect(float a, float x) {
		return (float) (gauss( 1 / a, x / maxHeat) * Math.sqrt(Math.PI * 2) / (Math.sqrt(2) * Math.sqrt(maxPower)));
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getEfficiencyScaled(int i) {
		return (efficiency * i) / maxEfficiency;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / maxHeat;
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
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			return tank.getFill();
		else
			return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			return tank.getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
			tank.setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
			tank.setTankType(type);
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

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tank);
		
		return list;
	}
}
