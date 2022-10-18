package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardReceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCrystallizer extends TileEntityMachineBase implements IEnergyUser, IFluidAcceptor, IFluidStandardReceiver {
	
	public long power;
	public static final long maxPower = 1000000;
	public static final int demand = 1000;
	public static final int acidRequired = 500;
	public short progress;
	public short duration = 600;
	
	public float angle;
	public float prevAngle;
	
	public FluidTank tank;

	public TileEntityMachineCrystallizer() {
		super(7);
		tank = new FluidTank(Fluids.ACID, 8000, 0);
	}

	@Override
	public String getName() {
		return "container.crystallizer";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			power = Library.chargeTEFromItems(slots, 1, power, maxPower);
			tank.loadTank(3, 4, slots);
			
			for(int i = 0; i < getCycleCount(); i++) {
				
				if(canProcess()) {
					
					progress++;
					power -= getPowerRequired();
					
					if(progress > getDuration()) {
						progress = 0;
						tank.setFill(tank.getFill() - getRequiredAcid());
						processItem();
						
						this.markDirty();
					}
					
				} else {
					progress = 0;
				}
			}
			
			tank.updateTank(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("progress", progress);
			data.setShort("duration", getDuration());
			data.setLong("power", power);
			this.networkPack(data, 25);
		} else {
			
			prevAngle = angle;
			
			if(progress > 0) {
				angle += 5F * this.getCycleCount();
				
				if(angle >= 360) {
					angle -= 360;
					prevAngle -= 360;
				}
			}
		}
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tank.getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	protected DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);

		if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
			
			return new DirPos[] {
				new DirPos(xCoord + 2, yCoord + 5, zCoord, Library.POS_X),
				new DirPos(xCoord - 2, yCoord + 5, zCoord, Library.NEG_X)
			};
		}

		if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
			
			return new DirPos[] {
				new DirPos(xCoord, yCoord + 5, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord, yCoord + 5, zCoord - 2, Library.NEG_Z)
			};
		}
		
		return new DirPos[0];
	}
	
	public void networkUnpack(NBTTagCompound data) {
		
		this.power = data.getLong("power");
		this.progress = data.getShort("progress");
		this.duration = data.getShort("duration");
	}
	
	private void processItem() {

		ItemStack result = CrystallizerRecipes.getOutput(slots[0]);
		
		if(result == null) //never happens but you can't be sure enough
			return;
		
		if(slots[2] == null)
			slots[2] = result;
		else if(slots[2].stackSize + result.stackSize <= slots[2].getMaxStackSize())
			slots[2].stackSize += result.stackSize;
		
		float freeChance = this.getFreeChance();
		
		if(freeChance == 0 || freeChance < worldObj.rand.nextFloat())
			this.decrStackSize(0, 1);
	}
	
	private boolean canProcess() {
		
		//Is there no input?
		if(slots[0] == null)
			return false;
		
		if(power < getPowerRequired())
			return false;
		
		if(tank.getFill() < getRequiredAcid())
			return false;
		
		ItemStack result = CrystallizerRecipes.getOutput(slots[0]);
		
		//Or output?
		if(result == null)
			return false;
		
		//Does the output not match?
		if(slots[2] != null && (slots[2].getItem() != result.getItem() || slots[2].getItemDamage() != result.getItemDamage()))
			return false;
		
		//Or is the output slot already full?
		if(slots[2] != null && slots[2].stackSize + result.stackSize > slots[2].getMaxStackSize())
			return false;
		
		return true;
	}
	
	public int getRequiredAcid() {
		
		int extra = 0;
		
		for(int i = 5; i <= 6; i++) {

			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_1)
				extra += 1000;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_2)
				extra += 2000;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_3)
				extra += 3000;
		}
		
		return acidRequired + Math.min(extra, 3000);
	}
	
	public float getFreeChance() {
		
		float chance = 0.0F;
		
		for(int i = 5; i <= 6; i++) {

			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_1)
				chance += 0.05F;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_2)
				chance += 0.1F;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_effect_3)
				chance += 0.15F;
		}
		
		return Math.min(chance, 0.15F);
	}
	
	public short getDuration() {
		
		float durationMod = 1;
		
		for(int i = 5; i <= 6; i++) {

			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_1)
				durationMod -= 0.25F;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_2)
				durationMod -= 0.50F;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_3)
				durationMod -= 0.75F;
		}
		
		return (short) (600 * Math.max(durationMod, 0.25F));
	}
	
	public int getPowerRequired() {
		
		int consumption = 0;
		
		for(int i = 5; i <= 6; i++) {

			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_1)
				consumption += 1000;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_2)
				consumption += 2000;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_speed_3)
				consumption += 3000;
		}
		
		return (int) (demand + Math.min(consumption, 3000));
	}
	
	public float getCycleCount() {
		
		int cycles = 1;
		
		for(int i = 5; i <= 6; i++) {

			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_overdrive_1)
				cycles += 2;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_overdrive_2)
				cycles += 4;
			if(slots[i] != null && slots[i].getItem() == ModItems.upgrade_overdrive_3)
				cycles += 6;
		}
		
		return Math.min(cycles, 4);
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / duration;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		return tank.getFill();
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		return tank.getMaxFill();
	}

	@Override
	public void setPower(long i) {
		this.power = i;
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		power = nbt.getLong("power");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		
		if(i == 0 && CrystallizerRecipes.getOutput(itemStack) != null)
			return true;
		
		if(i == 1 && itemStack.getItem() instanceof IBatteryItem)
			return true;
		
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		
		return side == 0 ? new int[] { 2 } : new int[] { 0, 2 };
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
	public void setInventorySlotContents(int i, ItemStack stack) {
		super.setInventorySlotContents(i, stack);
		
		if(stack != null && i >= 5 && i <= 6 && stack.getItem() instanceof ItemMachineUpgrade)
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}
}
