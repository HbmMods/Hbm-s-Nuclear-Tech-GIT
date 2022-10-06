package com.hbm.tileentity.bomb;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.handler.MissileStruct;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEMissileMultipartPacket;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.item.IDesignatorItem;
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
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLaunchTable extends TileEntityLoadedBase implements ISidedInventory, IEnergyUser, IFluidContainer, IFluidAcceptor {

	private ItemStack slots[];

	public long power;
	public static final long maxPower = 100000;
	public int solid;
	public static final int maxSolid = 100000;
	public FluidTank[] tanks;
	public PartSize padSize;
	public int height;
	
	public MissileStruct load;

	private static final int[] access = new int[] { 0 };

	private String customName;

	public TileEntityLaunchTable() {
		slots = new ItemStack[8];
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.NONE, 100000, 0);
		tanks[1] = new FluidTank(Fluids.NONE, 100000, 1);
		padSize = PartSize.SIZE_10;
		height = 10;
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
		if (slots[i] != null) {
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
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.launchTable";
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
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
	}

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (slots[i] != null) {
			if (slots[i].stackSize <= j) {
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0) {
				slots[i] = null;
			}

			return itemStack1;
		} else {
			return null;
		}
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getSolidScaled(int i) {
		return (solid * i) / maxSolid;
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {
			
			updateTypes();
			
			if(worldObj.getTotalWorldTime() % 20 == 0)
				this.updateConnections();

			tanks[0].loadTank(2, 6, slots);
			tanks[1].loadTank(3, 7, slots);

			for (int i = 0; i < 2; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			power = Library.chargeTEFromItems(slots, 5, power, maxPower);
			
			if(slots[4] != null && slots[4].getItem() == ModItems.rocket_fuel && solid + 250 <= maxSolid) {
				
				this.decrStackSize(4, 1);
				solid += 250;
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, solid, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, padSize.ordinal(), 1), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			
			MissileStruct multipart = getStruct(slots[0]);
			
			if(multipart != null)
				PacketDispatcher.wrapper.sendToAllAround(new TEMissileMultipartPacket(xCoord, yCoord, zCoord, multipart), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			else
				PacketDispatcher.wrapper.sendToAllAround(new TEMissileMultipartPacket(xCoord, yCoord, zCoord, new MissileStruct()), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));

			outer:
			for(int x = -4; x <= 4; x++) {
				for(int z = -4; z <= 4; z++) {
					
					if(worldObj.isBlockIndirectlyGettingPowered(xCoord + x, yCoord, zCoord + z) && canLaunch()) {
						launch();
						break outer;
					}
				}
			}
		} else {
			
			List<EntityMissileCustom> entities = worldObj.getEntitiesWithinAABB(EntityMissileCustom.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord, zCoord - 0.5, xCoord + 1.5, yCoord + 10, zCoord + 1.5));
			
			if(!entities.isEmpty()) {
				for(int i = 0; i < 15; i++) {

					boolean dir = worldObj.rand.nextBoolean();
					float moX = (float) (dir ? 0 : worldObj.rand.nextGaussian() * 0.65F);
					float moZ = (float) (!dir ? 0 : worldObj.rand.nextGaussian() * 0.65F);
					
					MainRegistry.proxy.spawnParticle(xCoord + 0.5, yCoord + 0.25, zCoord + 0.5, "launchsmoke", new float[] {moX, 0, moZ});
				}
			}
		}
	}
	
	private void updateConnections() {

		for(int i = -4; i <= 4; i++) {
			this.trySubscribe(worldObj, xCoord + i, yCoord, zCoord + 5, Library.POS_Z);
			this.trySubscribe(worldObj, xCoord + i, yCoord, zCoord - 5, Library.NEG_Z);
			this.trySubscribe(worldObj, xCoord + 5, yCoord, zCoord + i, Library.POS_X);
			this.trySubscribe(worldObj, xCoord - 5, yCoord, zCoord + i, Library.NEG_X);
		}
	}
	
	public boolean canLaunch() {
		
		if(power >= maxPower * 0.75 && isMissileValid() && hasDesignator() && hasFuel())
			return true;
		
		return false;
	}
	
	public void launch() {

		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.missileTakeOff", 10.0F, 1.0F);

		int tX = slots[1].stackTagCompound.getInteger("xCoord");
		int tZ = slots[1].stackTagCompound.getInteger("zCoord");
		
		EntityMissileCustom missile = new EntityMissileCustom(worldObj, xCoord + 0.5F, yCoord + 2.5F, zCoord + 0.5F, tX, tZ, getStruct(slots[0]));
		worldObj.spawnEntityInWorld(missile);
		
		subtractFuel();
		
		slots[0] = null;
	}
	
	private boolean hasFuel() {

		return solidState() != 0 && liquidState() != 0 && oxidizerState() != 0;
	}
	
	private void subtractFuel() {
		
		MissileStruct multipart = getStruct(slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return;
		
		ItemMissile fuselage = (ItemMissile)multipart.fuselage;
		
		float f = (Float)fuselage.attributes[1];
		int fuel = (int)f;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
				tanks[0].setFill(tanks[0].getFill() - fuel);
				tanks[1].setFill(tanks[1].getFill() - fuel);
				break;
			case HYDROGEN:
				tanks[0].setFill(tanks[0].getFill() - fuel);
				tanks[1].setFill(tanks[1].getFill() - fuel);
				break;
			case XENON:
				tanks[0].setFill(tanks[0].getFill() - fuel);
				break;
			case BALEFIRE:
				tanks[0].setFill(tanks[0].getFill() - fuel);
				tanks[1].setFill(tanks[1].getFill() - fuel);
				break;
			case SOLID:
				this.solid -= fuel; break;
			default: break;
		}
		
		this.power -= maxPower * 0.75;
	}
	
	public static MissileStruct getStruct(ItemStack stack) {
		
		return ItemCustomMissile.getStruct(stack);
	}
	
	public boolean isMissileValid() {
		
		MissileStruct multipart = getStruct(slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return false;
		
		ItemMissile fuselage = (ItemMissile)multipart.fuselage;
		
		return fuselage.top == padSize;
	}
	
	public boolean hasDesignator() {
		
		if(slots[1] != null && slots[1].getItem() instanceof IDesignatorItem && ((IDesignatorItem)slots[1].getItem()).isReady(worldObj, slots[1], xCoord, yCoord, zCoord)) {
			return true;
		}
		
		return false;
	}
	
	public int solidState() {
		
		MissileStruct multipart = getStruct(slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return -1;
		
		ItemMissile fuselage = (ItemMissile)multipart.fuselage;
		
		if((FuelType)fuselage.attributes[0] == FuelType.SOLID) {
			
			if(solid >= (Float)fuselage.attributes[1])
				return 1;
			else
				return 0;
		}
		
		return -1;
	}
	
	public int liquidState() {
		
		MissileStruct multipart = getStruct(slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return -1;
		
		ItemMissile fuselage = (ItemMissile)multipart.fuselage;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
			case HYDROGEN:
			case XENON:
			case BALEFIRE:
				
				if(tanks[0].getFill() >= (Float)fuselage.attributes[1])
					return 1;
				else
					return 0;
			default: break;
		}
		
		return -1;
	}
	
	public int oxidizerState() {
		
		MissileStruct multipart = getStruct(slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return -1;
		
		ItemMissile fuselage = (ItemMissile)multipart.fuselage;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
			case HYDROGEN:
			case BALEFIRE:
				
				if(tanks[1].getFill() >= (Float)fuselage.attributes[1])
					return 1;
				else
					return 0;
			default: break;
		}
		
		return -1;
	}
	
	public void updateTypes() {
		
		MissileStruct multipart = getStruct(slots[0]);
		
		if(multipart == null || multipart.fuselage == null)
			return;
		
		ItemMissile fuselage = (ItemMissile)multipart.fuselage;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
				tanks[0].setTankType(Fluids.KEROSENE);
				tanks[1].setTankType(Fluids.ACID);
				break;
			case HYDROGEN:
				tanks[0].setTankType(Fluids.HYDROGEN);
				tanks[1].setTankType(Fluids.OXYGEN);
				break;
			case XENON:
				tanks[0].setTankType(Fluids.XENON);
				break;
			case BALEFIRE:
				tanks[0].setTankType(Fluids.BALEFIRE);
				tanks[1].setTankType(Fluids.ACID);
				break;
			default: break;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		tanks[0].readFromNBT(nbt, "fuel");
		tanks[1].readFromNBT(nbt, "oxidizer");
		solid = nbt.getInteger("solidfuel");
		power = nbt.getLong("power");
		padSize = PartSize.values()[nbt.getInteger("padSize")];

		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList list = new NBTTagList();

		tanks[0].writeToNBT(nbt, "fuel");
		tanks[1].writeToNBT(nbt, "oxidizer");
		nbt.setInteger("solidfuel", solid);
		nbt.setLong("power", power);
		nbt.setInteger("padSize", padSize.ordinal());

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return access;
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
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(fill);
		else if (type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else
			return 0;
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
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}
	
	@Override
	public long transferPower(long power) {
		
		this.power += power;
		
		if(this.power > this.getMaxPower()) {
			
			long overshoot = this.power - this.getMaxPower();
			this.power = this.getMaxPower();
			return overshoot;
		}
		
		return 0;
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.DOWN && dir != ForgeDirection.UNKNOWN;
	}
}
