package com.hbm.tileentity.network;

import com.hbm.blocks.network.CraneInserter;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerCraneInserter;
import com.hbm.inventory.gui.GUICraneInserter;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.InventoryUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
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

public class TileEntityCraneInserter extends TileEntityCraneBase implements IGUIProvider, IControlReceiver {
	
	public boolean destroyer = true;
	public static final int[] access = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };

	public TileEntityCraneInserter() {
		super(21);
	}

	@Override
	public String getName() {
		return "container.craneInserter";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if(!worldObj.isRemote) {

			ForgeDirection outputSide = getOutputSide();
			TileEntity te = worldObj.getTileEntity(xCoord + outputSide.offsetX, yCoord + outputSide.offsetY, zCoord + outputSide.offsetZ);
			
			int[] access = null;
			
			if(te instanceof ISidedInventory) {
				ISidedInventory sided = (ISidedInventory) te;
				//access = sided.getAccessibleSlotsFromSide(dir.ordinal());
				access = InventoryUtil.masquerade(sided, outputSide.getOpposite().ordinal());
			}
			
			if(te instanceof IInventory) {
				for(int i = 0; i < slots.length; i++) {
					
					ItemStack stack = slots[i];
					
					if(stack != null) {
						ItemStack ret = CraneInserter.addToInventory((IInventory) te, access, stack.copy(), outputSide.getOpposite().ordinal());
						
						if(ret == null || ret.stackSize != stack.stackSize) {
							slots[i] = ret;
							this.markDirty();
							return;
						}
					}
				}
				
				//if the previous operation fails, repeat but use single items instead of the whole stack instead
				//this should fix cases where the inserter can't insert into something that has a stack size limitation
				for(int i = 0; i < slots.length; i++) {
					
					ItemStack stack = slots[i];
					
					if(stack != null) {
						stack = stack.copy();
						stack.stackSize = 1;
						ItemStack ret = CraneInserter.addToInventory((IInventory) te, access, stack.copy(), outputSide.getOpposite().ordinal());
						
						if(ret == null || ret.stackSize != stack.stackSize) {
							this.decrStackSize(i, 1);
							this.markDirty();
							return;
						}
					}
				}
			}

			this.networkPackNT(15);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(destroyer);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		destroyer = buf.readBoolean();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return access;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCraneInserter(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICraneInserter(player.inventory, this);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.destroyer = nbt.getBoolean("destroyer");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("destroyer", this.destroyer);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("destroyer")) this.destroyer = !this.destroyer;
	}
}
