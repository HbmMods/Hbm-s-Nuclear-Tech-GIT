package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerMachinePress;
import com.hbm.inventory.gui.GUIMachinePress;
import com.hbm.inventory.recipes.PressRecipes;
import com.hbm.items.machine.ItemStamp;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEPressPacket;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachinePress extends TileEntity implements ISidedInventory, IGUIProvider {

	private ItemStack slots[];

	public int progress = 0;
	public int power = 0;
	public int burnTime = 0;
	public final static int maxProgress = 200;
	public final static int maxPower = 700;
	public int maxBurn = 160;
	public int item;
	public int meta;
	boolean isRetracting = false;
	
	private String customName;
	
	public TileEntityMachinePress() {
		slots = new ItemStack[4];
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
		return this.hasCustomInventoryName() ? this.customName : "container.press";
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
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(stack.getItem() instanceof ItemStamp && i == 1)
			return true;
		
		if(TileEntityFurnace.getItemBurnTime(stack) > 0 && i == 0)
			return true;
		
		return i == 2;
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
		
		progress = nbt.getInteger("progress");
		power = nbt.getInteger("power");
		burnTime = nbt.getInteger("burnTime");
		maxBurn = nbt.getInteger("maxBurn");
		isRetracting = nbt.getBoolean("ret");
		
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

		nbt.setInteger("progress", progress);
		nbt.setInteger("power", power);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("maxBurn", maxBurn);
		nbt.setBoolean("ret", isRetracting);
		
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
	public int[] getAccessibleSlotsFromSide(int side)
    {
		return side == 0 ? new int[] { 3 } : new int[]{ 0, 1, 2 };
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			
			boolean preheated = false;
			
			if(power < maxPower / 3 - 5) {
				for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
					if(worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) == ModBlocks.press_preheater) {
						preheated = true;
						break;
					}
				}
			}
			
			if(preheated)
				power += 2;
			
			if(burnTime > 0) {
				this.burnTime--;
				this.power++;
				if(power > maxPower) {
					power = maxPower;
				}
			} else {
				if(power > 0) {
					power--;
				}
			}
			
			if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				if(slots[0] != null && this.burnTime == 0 && TileEntityFurnace.getItemBurnTime(slots[0]) > 0) {
					this.maxBurn = this.burnTime = TileEntityFurnace.getItemBurnTime(slots[0]) / 8;
					slots[0].stackSize--;
					if(slots[0].stackSize <= 0) {
						
						if(slots[0].getItem().getContainerItem() != null)
							slots[0] = new ItemStack(slots[0].getItem().getContainerItem());
						else
							slots[0] = null;
					}
				}
				
				if(power >= maxPower / 3) {
	
					int speed = power * 25 / maxPower;
					
					if(slots[1] != null && slots[2] != null) {
						ItemStack stack = PressRecipes.getOutput(slots[2], slots[1]);
						if(stack != null &&
								(slots[3] == null ||
								(slots[3].getItem() == stack.getItem() &&
								slots[3].stackSize + stack.stackSize <= slots[3].getMaxStackSize()))) {
							
							if(progress >= maxProgress) {
								
								isRetracting = true;
								
								if(slots[3] == null)
									slots[3] = stack.copy();
								else
									slots[3].stackSize += stack.stackSize;
								
								slots[2].stackSize--;
								if(slots[2].stackSize <= 0)
									slots[2] = null;
								
								if(slots[1].getMaxDamage() != 0) {
									slots[1].setItemDamage(slots[1].getItemDamage() + 1);
									if(slots[1].getItemDamage() >= slots[1].getMaxDamage())
										slots[1] = null;
								}
	
						        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.pressOperate", 1.5F, 1.0F);
							}
							
							if(!isRetracting)
								progress += speed;
							
						} else {
							isRetracting = true;
						}
					} else {
						isRetracting = true;
					}
	
					if(isRetracting)
						progress -= speed;
				} else {
					isRetracting = true;
				}
				
				if(progress <= 0) {
					isRetracting = false;
					progress = 0;
				}
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new TEPressPacket(xCoord, yCoord, zCoord, slots[2], progress), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
		}
	}

	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	public int getProgressScaled(int i) {
		return (progress * i) / maxProgress;
	}

	public int getBurnScaled(int i) {
		return (burnTime * i) / maxBurn;
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
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachinePress(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachinePress(player.inventory, this);
	}
}
