package com.hbm.blocks.machine;

import com.hbm.blocks.BlockContainerBase;
import com.hbm.tileentity.INBTPacketReceiver;

import api.hbm.block.IInsertable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PistonInserter extends BlockContainerBase {

	public PistonInserter() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPistonInserter();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
		this.updateState(world, x, y, z);
	}
	
	protected void updateState(World world, int x, int y, int z) {
		if(!world.isRemote) {
			ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
			
			if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).isNormalCube())
				return; //no obstructions allowed!
			
			if(checkRedstone(world, x, y, z)) { //if necessary, add lastState (if block updates are too unreliable).
				TileEntityPistonInserter piston = (TileEntityPistonInserter)world.getTileEntity(x, y, z);
				
				if(piston.extend <= 0)
					piston.isRetracting = false;
			}
		}
	}
	
	protected boolean checkRedstone(World world, int x, int y, int z) {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if(world.getIndirectPowerOutput(x, y, z, dir.ordinal()))
				return true;
		}
		
		return false;
	}
	
	//						 $%&#$&
	//					   %$&&@$%%#%
	//______        	  $%@--$@@%&$%$
	//		|			  %/   *--$#@&&$$
	//		|			 /	  --__  %$%@$&
	//		|			(----^`---	 $@##%
	//		|			/___\	`-----*#@$
	//		|		   /(()_) / /___\  /__
	//		|		 /	\___//	(()_) //-,|
	//		|	   /____|_  /	\___// )_/
	//		|	   \____/ `^-___|___/ |
	//		|		 	 \/   \____/  /_-^-.
	//		|		     /   _-'     |___.  \_
	//		|			/ _-'		 /    `\ \\___
	//		|			`'\____~~+~^/      _)/    \____
	//		|			   \`----'	|   __/			  _)
	//		|			   /(		/~-'		   ,-' |
	//		|			  / `|		|			  /	   |
	//		|			 /	 (		)			 /     `)
	//		|			/	  `-==-'			 |      |
	//		|		   /	   /|				 |      |
	//		|		  /		  / \				 |      |
	//		|		 /		 /	 |	  			 |      |
	//		|		/		/	  \	  _____,.____|		|
	//		|	   / _	   /	   |<`____, ____,|		|
	//		|	  / / \_  /	  	 _ | <_____/	 |		)
	//		|	/  / ^/,^=-~---~' `z---..._______/	    |
	//		|--'  /	/| |/ .^ ,^\    \				    )
	//		|	  |_|| || |(_(  )   |				   |
	//		|	  \_/`-``-`----'___/_____	 	       |
	//		|___..---'			   _|____`-----..-----'\		
	//		|_____________________|	@	|			    )
	// average coding session involving tile entities
	public static class TileEntityPistonInserter extends TileEntity implements IInventory, INBTPacketReceiver {
		
		public ItemStack slot;
		
		public int extend;
		public static final int maxExtend = 25;
		public boolean isRetracting;
		public int delay;
		
		private int lastState;
		
		public TileEntityPistonInserter() { }
		
		@Override
		public void updateEntity() { //what is this amalgamation
			
			if(!worldObj.isRemote) {
				
				if(delay <= 0) {
					
					if(this.isRetracting && this.extend > 0) {
						this.extend--;
					} else if(!this.isRetracting) {
						this.extend++;
						
						if(this.extend >= this.maxExtend) {
							worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:block.pressOperate", 1.5F, 1.0F);
							
							ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
							Block b = worldObj.getBlock(xCoord + dir.offsetX * 2, yCoord + dir.offsetY * 2, zCoord + dir.offsetZ * 2);
							
							if(b instanceof IInsertable && ((IInsertable) b).insertItem(worldObj, xCoord + dir.offsetX * 2, yCoord + dir.offsetY * 2, zCoord + dir.offsetZ * 2, dir, slot)) {
								this.decrStackSize(0, 1);
							}
							
							this.isRetracting = true;
							this.delay = 5;
						}
					}
					
				} else {
					delay--;
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("extend", extend);
				if(this.slot != null) {
					NBTTagCompound stack = new NBTTagCompound();
					slot.writeToNBT(stack);
					data.setTag("stack", stack);
				}
				
				INBTPacketReceiver.networkPack(this, data, 25);
				
			}
			
		}
		
		@Override
		public void networkUnpack(NBTTagCompound nbt) {
			this.extend = nbt.getInteger("extend");
			
			if(nbt.hasKey("stack")) {
				NBTTagCompound stack = nbt.getCompoundTag("stack");
				this.slot = ItemStack.loadItemStackFromNBT(stack);
			} else
				this.slot = null;
		}
		
		/* BS inventory stuff */
		
		@Override public int getSizeInventory() { return 1; }
		
		@Override public ItemStack getStackInSlot(int slot) { return this.slot; }
		
		@Override
		public ItemStack decrStackSize(int slot, int amount) {
			if(this.slot != null) {
				if(this.slot.stackSize <= amount) {
					ItemStack stack = this.slot;
					this.slot = null;
					return stack;
				}
				
				ItemStack stack = this.slot.splitStack(amount);
				if(this.slot.stackSize == 0)
					this.slot = null;
				
				return stack;
			}
			
			return null;
		}
		
		@Override
		public ItemStack getStackInSlotOnClosing(int slot) { return null; }
		
		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			this.slot = stack;
			if(stack != null && stack.stackSize > this.getInventoryStackLimit())
				stack.stackSize = this.getInventoryStackLimit();
		}
		
		@Override public String getInventoryName() { return null; }
		
		@Override public boolean hasCustomInventoryName() { return false; }
		
		@Override public int getInventoryStackLimit() { return 1; }
		
		@Override public boolean isUseableByPlayer(EntityPlayer player) { return false; }
		
		@Override public void openInventory() {}
		
		@Override public void closeInventory() {}
		
		@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return true; }
		
	}
}
