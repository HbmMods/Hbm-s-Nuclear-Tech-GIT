package com.hbm.tileentity.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.pile.BlockPile;
import com.hbm.main.NTMSounds;
import com.hbm.tileentity.machine.pile.TileEntityPileCore.PileChannel;
import com.hbm.util.Compat;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPileLoader extends TileEntityPileDeviceBase implements ISidedInventory{
	
	public double syncLevel;
	public double level;
	public double lastLevel;
	
	public int turnProgress;
	
	public static final double SPEED = 1D / 7D;
	
	public boolean loading = false;
	public int delay = 0;
	public boolean hasRod = false;
	public ItemStack stack;

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.hasRod = this.stack != null;
			
			ForgeDirection dir = getOrientation();
			
			int x = xCoord - dir.offsetX;
			int y = yCoord;
			int z = zCoord - dir.offsetZ;
			
			if(worldObj.getBlock(x, y, z) == ModBlocks.pile_block && worldObj.getBlockMetadata(x, y, z) == BlockPile.META_FUEL_IN) {
				TileEntity tile = Compat.getTileStandard(worldObj, x, y, z);
				
				if(tile instanceof TileEntityPileBaseMK2) {
					TileEntityPileBaseMK2 pile = (TileEntityPileBaseMK2) tile;
					TileEntityPileCore core = pile.getCore();
					
					if(core != null) {
						PileChannel fuelChan = core.getFuelChannel(x, y, z);
						
						if(fuelChan != null) {
							this.chanNum = core.getFuelChannelNum(fuelChan);
						}
					}
				}
			}
			
			if(this.delay > 0) {
				this.delay--;
			} else {
				
				if(loading) {
					
					if(this.level == 0) worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, NTMSounds.GUN_BOLT_OPEN, this.getVolume(1F), 1F);
					
					this.level += SPEED;
					if(this.level >= 1D) {
						this.level = 1D;
						this.loading = false;
						this.delay = 5;
					}
				} else {
					
					if(this.level == 1) {
						worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, NTMSounds.GUN_BOLT_OPEN, this.getVolume(1F), 0.75F);
						this.setInventorySlotContents(0, null);
					}
					
					if(this.level > 0D) {
						this.level -= SPEED;
						if(this.level < 0D) this.level = 0D;
					}
				}
			}
			
			this.networkPackNT(35);
			
		} else {

			this.lastLevel = this.level;

			if(this.turnProgress > 0) {
				this.level = this.level + ((this.syncLevel - this.level) / (double) this.turnProgress);
				--this.turnProgress;
			} else {
				this.level = this.syncLevel;
			}
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(this.level);
		buf.writeBoolean(this.hasRod);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		double lastSync = this.syncLevel;
		this.syncLevel = buf.readDouble();
		this.hasRod = buf.readBoolean();

		if(this.syncLevel != lastSync) this.turnProgress = 2;
	}
	
	public static boolean isItemLoadable(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(amount == 1 && this.stack != null) {
			ItemStack ret = stack.copy();
			this.setInventorySlotContents(0, null);
			return ret;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.stack = stack;
		this.markDirty();
	}

	@Override public int getSizeInventory() { return 1; }
	@Override public ItemStack getStackInSlot(int slot) { return stack; }
	@Override public ItemStack getStackInSlotOnClosing(int slot) { return null; }
	@Override public String getInventoryName() { return "NULL"; }
	@Override public boolean hasCustomInventoryName() { return false; }
	@Override public int getInventoryStackLimit() { return 1; }

	@Override public boolean isUseableByPlayer(EntityPlayer player) { return false; }
	@Override public void openInventory() { }
	@Override public void closeInventory() { }

	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[] {0}; }
	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return isItemLoadable(stack); }
	@Override public boolean canInsertItem(int slot, ItemStack stack, int side) { return isItemLoadable(stack); }
	@Override public boolean canExtractItem(int slot, ItemStack stack, int side) { return false; }
}
