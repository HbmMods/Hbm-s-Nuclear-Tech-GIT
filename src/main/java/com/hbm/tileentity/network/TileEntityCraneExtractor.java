package com.hbm.tileentity.network;

import com.hbm.entity.item.EntityMovingItem;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerCraneExtractor;
import com.hbm.inventory.gui.GUICraneExtractor;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.conveyor.IConveyorBelt;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCraneExtractor extends TileEntityMachineBase implements IGUIProvider, IControlReceiver {
	
	public boolean isWhitelist = false;
	public ModulePatternMatcher matcher;

	public TileEntityCraneExtractor() {
		super(20);
		this.matcher = new ModulePatternMatcher(9);
	}

	@Override
	public String getName() {
		return "container.craneExtractor";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(worldObj.getTotalWorldTime() % 20 == 0) {
				int amount = 1;
	
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				TileEntity te = worldObj.getTileEntity(xCoord - dir.offsetX, yCoord - dir.offsetY, zCoord - dir.offsetZ);
				Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				
				int[] access = null;
				ISidedInventory sided = null;
				
				if(te instanceof ISidedInventory) {
					sided = (ISidedInventory) te;
					access = sided.getAccessibleSlotsFromSide(dir.ordinal());
				}
				
				if(b instanceof IConveyorBelt && te instanceof IInventory) {
					IInventory inv = (IInventory) te;
					int size = access == null ? inv.getSizeInventory() : access.length;
					
					for(int i = 0; i < size; i++) {
						int index = access == null ? i : access[i];
						ItemStack stack = inv.getStackInSlot(index);
						
						if(stack != null && (sided == null || sided.canExtractItem(index, stack, dir.ordinal()))){
							
							boolean match = this.matchesFilter(stack);
							
							if((isWhitelist && match) || (!isWhitelist && !match)) {
								stack = stack.copy();
								int toSend = Math.min(amount, stack.stackSize);
								inv.decrStackSize(index, toSend);
								stack.stackSize = toSend;
								
								EntityMovingItem moving = new EntityMovingItem(worldObj);
								Vec3 pos = Vec3.createVectorHelper(xCoord + 0.5 + dir.offsetX * 0.55, yCoord + 0.5 + dir.offsetY * 0.55, zCoord + 0.5 + dir.offsetZ * 0.55);
								Vec3 snap = ((IConveyorBelt) b).getClosestSnappingPosition(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, pos);
								moving.setPosition(snap.xCoord, snap.yCoord, snap.zCoord);
								moving.setItemStack(stack);
								worldObj.spawnEntityInWorld(moving);
								break;
							}
						}
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("isWhitelist", isWhitelist);
			this.matcher.writeToNBT(data);
			this.networkPack(data, 15);
		}
	}
	
	public void networkUnpack(NBTTagCompound nbt) {
		this.isWhitelist = nbt.getBoolean("isWhitelist");
		this.matcher.modes = new String[this.matcher.modes.length];
		this.matcher.readFromNBT(nbt);
	}
	
	public boolean matchesFilter(ItemStack stack) {
		
		for(int i = 0; i < 9; i++) {
			ItemStack filter = slots[i];
			
			if(filter != null && this.matcher.isValidForFilter(filter, i, stack)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void nextMode(int i) {
		this.matcher.nextMode(worldObj, slots[i], i);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCraneExtractor(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICraneExtractor(player.inventory, this);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.isWhitelist = nbt.getBoolean("isWhitelist");
		this.matcher.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("isWhitelist", this.isWhitelist);
		this.matcher.writeToNBT(nbt);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("whitelist")) {
			this.isWhitelist = !this.isWhitelist;
		}
	}
}
