package com.hbm.tileentity.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.inventory.container.ContainerLaunchPadTier1;
import com.hbm.inventory.gui.GUILaunchPadTier1;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemDesingator;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEMissilePacket;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IEnergyUser;
import api.hbm.item.IDesignatorItem;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityLaunchPad extends TileEntityLoadedBase implements ISidedInventory, IEnergyUser, SimpleComponent, IGUIProvider {

	public ItemStack slots[];
	
	public long power;
	public final long maxPower = 100000;
	
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] { 0, 1, 2};
	private static final int[] slots_side = new int[] {0};
	public int state = 0;
	private String customName;
	
	public TileEntityLaunchPad() {
		slots = new ItemStack[3];
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
		return this.hasCustomInventoryName() ? this.customName : "container.launchPad";
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
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=64;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
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
		NBTTagList list = new NBTTagList();
		nbt.setLong("power", power);
		
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

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			power = Library.chargeTEFromItems(slots, 2, power, maxPower);
			this.updateConnections();
			
			PacketDispatcher.wrapper.sendToAllAround(new TEMissilePacket(xCoord, yCoord, zCoord, slots[0]), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 250));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}
	
	private void updateConnections() {
		this.trySubscribe(worldObj, xCoord + 1, yCoord, zCoord, Library.POS_X);
		this.trySubscribe(worldObj, xCoord - 1, yCoord, zCoord, Library.NEG_X);
		this.trySubscribe(worldObj, xCoord, yCoord, zCoord + 1, Library.POS_Z);
		this.trySubscribe(worldObj, xCoord, yCoord, zCoord - 1, Library.NEG_Z);
		this.trySubscribe(worldObj, xCoord, yCoord - 1, zCoord, Library.NEG_Y);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
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
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	// do some opencomputer stuff
	@Override
	public String getComponentName() {
		return "launch_pad";
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyStored(Context context, Arguments args) {
		return new Object[] {getPower(), "Consider switching to the main function 'getEnergyInfo', as this function is deprecated and will soon be removed."};
	}
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getMaxEnergy(Context context, Arguments args) {
		return new Object[] {getMaxPower(), "Consider switching to the main function 'getEnergyInfo', as this function is deprecated and will soon be removed."};
	}

	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getEnergyInfo(Context context, Arguments args) {
		return new Object[] {getPower(), getMaxPower()};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] getCoords(Context context, Arguments args) {
		if (slots[1] != null && slots[1].getItem() instanceof IDesignatorItem) {
			int xCoord2;
			int zCoord2;
			if (slots[1].stackTagCompound != null) {
				xCoord2 = slots[1].stackTagCompound.getInteger("xCoord");
				zCoord2 = slots[1].stackTagCompound.getInteger("zCoord");
			} else
				return new Object[] {false};

			// Not sure if i should have this
			/*
			if(xCoord2 == xCoord && zCoord2 == zCoord) {
				xCoord2 += 1;
			}
			*/

			return new Object[] {xCoord2, zCoord2};
		}
		return new Object[] {false, "Designator not found"};
	}
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] setCoords(Context context, Arguments args) {
		if (slots[1] != null && slots[1].getItem() instanceof IDesignatorItem) {
			slots[1].stackTagCompound = new NBTTagCompound();
			slots[1].stackTagCompound.setInteger("xCoord", args.checkInteger(0));
			slots[1].stackTagCompound.setInteger("zCoord", args.checkInteger(1));

			return new Object[] {true};
		}
		return new Object[] {false, "Designator not found"};
	}
	
	@Callback
	@Optional.Method(modid = "OpenComputers")
	public Object[] launch(Context context, Arguments args) {
		//worldObj.getBlock(xCoord, yCoord, zCoord).explode(worldObj, xCoord, yCoord, zCoord);	
		((LaunchPad) ModBlocks.launch_pad).explode(worldObj, xCoord, yCoord, zCoord);
		return new Object[] {};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerLaunchPadTier1(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUILaunchPadTier1(player.inventory, this);
	}
}
