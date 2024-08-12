package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.handler.MissileStruct;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.container.ContainerCompactLauncher;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCompactLauncher;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemCustomMissile;
import com.hbm.items.weapon.ItemCustomMissilePart;
import com.hbm.items.weapon.ItemCustomMissilePart.FuelType;
import com.hbm.items.weapon.ItemCustomMissilePart.PartSize;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEMissileMultipartPacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IRadarCommandReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardReceiver;
import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCompactLauncher extends TileEntityLoadedBase implements ISidedInventory, IFluidContainer, IEnergyReceiverMK2, IFluidStandardReceiver, IGUIProvider, IRadarCommandReceiver {

	private ItemStack slots[];

	public long power;
	public static final long maxPower = 100000;
	public int solid;
	public static final int maxSolid = 25000;
	public FluidTank[] tanks;
	
	public MissileStruct load;

	private static final int[] access = new int[] { 0 };

	private String customName;

	public TileEntityCompactLauncher() {
		slots = new ItemStack[8];
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.NONE, 25000, 0);
		tanks[1] = new FluidTank(Fluids.NONE, 25000, 1);
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
		return this.hasCustomInventoryName() ? this.customName : "container.compactLauncher";
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

			tanks[0].loadTank(2, 6, slots);
			tanks[1].loadTank(3, 7, slots);

			for (int i = 0; i < 2; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			power = Library.chargeTEFromItems(slots, 5, power, maxPower);
			
			if(slots[4] != null && slots[4].getItem() == ModItems.rocket_fuel && solid + 250 <= maxSolid) {
				
				this.decrStackSize(4, 1);
				solid += 250;
			}

			if(worldObj.getTotalWorldTime() % 20 == 0)
				this.updateConnections();
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, solid, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			
			MissileStruct multipart = getStruct(slots[0]);
			
			if(multipart != null)
				PacketDispatcher.wrapper.sendToAllAround(new TEMissileMultipartPacket(xCoord, yCoord, zCoord, multipart), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			else
				PacketDispatcher.wrapper.sendToAllAround(new TEMissileMultipartPacket(xCoord, yCoord, zCoord, new MissileStruct()), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));

			outer:
			for(int x = -1; x <= 1; x++) {
				for(int z = -1; z <= 1; z++) {
						
					if(worldObj.isBlockIndirectlyGettingPowered(xCoord + x, yCoord, zCoord + z) && canLaunch()) {
						launchFromDesignator();
						break outer;
					}
				}
			}
		} else {
			
			List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(xCoord - 0.5, yCoord, zCoord - 0.5, xCoord + 1.5, yCoord + 10, zCoord + 1.5));
			
			for(Entity e : entities) {
				
				if(e instanceof EntityMissileCustom) {
					
					for(int i = 0; i < 15; i++) {
						
						boolean dir = worldObj.rand.nextBoolean();
						float moX = (float) (dir ? 0 : worldObj.rand.nextGaussian() * 0.5F);
						float moZ = (float) (!dir ? 0 : worldObj.rand.nextGaussian() * 0.5F);
						
						MainRegistry.proxy.spawnParticle(xCoord + 0.5, yCoord + 0.25, zCoord + 0.5, "launchsmoke", new float[] {moX, 0, moZ});
					}
					
					break;
				}
			}
		}
	}
	
	private void updateConnections() {
		
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord + 1, yCoord - 1, zCoord + 1, Library.NEG_Y),
				new DirPos(xCoord + 1, yCoord - 1, zCoord - 1, Library.NEG_Y),
				new DirPos(xCoord - 1, yCoord - 1, zCoord + 1, Library.NEG_Y),
				new DirPos(xCoord - 1, yCoord - 1, zCoord - 1, Library.NEG_Y)
		};
	}
	
	public boolean canLaunch() {
		
		if(power >= maxPower * 0.75 && isMissileValid() && hasDesignator() && hasFuel())
			return true;
		
		return false;
	}

	@Override
	public boolean sendCommandEntity(Entity target) {
		return sendCommandPosition((int) Math.floor(target.posX), yCoord, (int) Math.floor(target.posX));
	}

	@Override
	public boolean sendCommandPosition(int x, int y, int z) {
		if(!canLaunch()) return false;
		this.launchTo(x, z);
		return true;
	}
	
	public void launchFromDesignator() {

		if(slots[1] != null && slots[1].getItem() instanceof IDesignatorItem) {
			IDesignatorItem designator = (IDesignatorItem) slots[1].getItem();
			
			if(designator.isReady(worldObj, slots[1], xCoord, yCoord, zCoord)) {
				Vec3 coords = designator.getCoords(worldObj, slots[1], xCoord, yCoord, zCoord);
				int tX = (int) Math.floor(coords.xCoord);
				int tZ = (int) Math.floor(coords.zCoord);
				
				this.launchTo(tX, tZ);
			}
		}
	}
	
	public void launchTo(int tX, int tZ) {

		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:weapon.missileTakeOff", 10.0F, 1.0F);
		
		ItemCustomMissilePart chip = (ItemCustomMissilePart) Item.getItemById(ItemCustomMissile.readFromNBT(slots[0], "chip"));
		float c = (Float)chip.attributes[0];
		float f = 1.0F;
		
		if(getStruct(slots[0]).fins != null) {
			ItemCustomMissilePart fins = (ItemCustomMissilePart) Item.getItemById(ItemCustomMissile.readFromNBT(slots[0], "stability"));
			f = (Float) fins.attributes[0];
		}
		
		Vec3 target = Vec3.createVectorHelper(xCoord - tX, 0, zCoord - tZ);
		target.xCoord *= c * f;
		target.zCoord *= c * f;
		
		target.rotateAroundY(worldObj.rand.nextFloat() * 360);
		
		EntityMissileCustom missile = new EntityMissileCustom(worldObj, xCoord + 0.5F, yCoord + 2.5F, zCoord + 0.5F, tX + (int)target.xCoord, tZ + (int)target.zCoord, getStruct(slots[0]));
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
		
		ItemCustomMissilePart fuselage = (ItemCustomMissilePart)multipart.fuselage;
		
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
		
		ItemCustomMissilePart fuselage = (ItemCustomMissilePart)multipart.fuselage;
		
		return fuselage.top == PartSize.SIZE_10;
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
		
		ItemCustomMissilePart fuselage = (ItemCustomMissilePart)multipart.fuselage;
		
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
		
		ItemCustomMissilePart fuselage = (ItemCustomMissilePart)multipart.fuselage;
		
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
		
		ItemCustomMissilePart fuselage = (ItemCustomMissilePart)multipart.fuselage;
		
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
		
		ItemCustomMissilePart fuselage = (ItemCustomMissilePart)multipart.fuselage;
		
		switch((FuelType)fuselage.attributes[0]) {
			case KEROSENE:
				tanks[0].setTankType(Fluids.KEROSENE);
				tanks[1].setTankType(Fluids.PEROXIDE);
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
				tanks[1].setTankType(Fluids.PEROXIDE);
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
	public void setFillForSync(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
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
		return dir != ForgeDirection.UP && dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public boolean canConnect(FluidType type, ForgeDirection dir) {
		return dir != ForgeDirection.UP && dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return tanks;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCompactLauncher(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCompactLauncher(player.inventory, this);
	}
}
