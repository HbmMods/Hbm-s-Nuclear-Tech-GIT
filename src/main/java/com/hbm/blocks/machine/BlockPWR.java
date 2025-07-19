package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;
import com.hbm.tileentity.machine.TileEntityPWRController;

import api.hbm.fluidmk2.IFluidReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockPWR extends BlockContainer implements IBlockCT {
	
	@SideOnly(Side.CLIENT) protected IIcon iconPort;
	
	public BlockPWR(Material mat) {
		super(mat);
	}

	@Override
	public int getRenderType() {
		return CT.renderID;
	}
	
	@Override
	public Item getItemDropped(int i, Random rand, int j)  {
		return null;
	}

	@SideOnly(Side.CLIENT) public CTStitchReceiver rec;
	@SideOnly(Side.CLIENT) public CTStitchReceiver recPort;

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.iconPort = reg.registerIcon(RefStrings.MODID + ":pwr_casing_port");
		this.rec = IBlockCT.primeReceiver(reg, this.blockIcon.getIconName(), this.blockIcon);
		this.recPort = IBlockCT.primeReceiver(reg, this.iconPort.getIconName(), this.iconPort);
	}

	@Override
	public IIcon[] getFragments(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 1) return recPort.fragCache;
		return rec.fragCache;
	}

	@Override
	public boolean canConnect(IBlockAccess world, int x, int y, int z, Block block) {
		return block == ModBlocks.pwr_block || block == ModBlocks.pwr_controller;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBlockPWR();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityBlockPWR) {
			TileEntityBlockPWR pwr = (TileEntityBlockPWR) tile;
			world.removeTileEntity(x, y, z);
			if(pwr.block != null) {
				world.setBlock(x, y, z, pwr.block);
				TileEntity controller = world.getTileEntity(pwr.coreX, pwr.coreY, pwr.coreZ);
				
				if(controller instanceof TileEntityPWRController) {
					((TileEntityPWRController) controller).assembled = false;
				}
			}
		} else {
			world.removeTileEntity(x, y, z);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	public static class TileEntityBlockPWR extends TileEntity implements IFluidReceiverMK2, ISidedInventory {
		
		public Block block;
		public int coreX;
		public int coreY;
		public int coreZ;
		
		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote) {
				
				if(worldObj.getTotalWorldTime() % 20 == 0 && block != null) {
					
					TileEntityPWRController controller = getCore();
					
					if(controller != null) {
						if(!controller.assembled) {
							this.getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, this.getBlockType(), this.getBlockMetadata());
						}
					} else if(worldObj.getChunkProvider().chunkExists(coreX >> 4, coreZ >> 4)) {
						this.getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, this.getBlockType(), this.getBlockMetadata());
					}
				}
			}
		}
		
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			
			block = Block.getBlockById(nbt.getInteger("block"));
			if(block != Blocks.air) {
				coreX = nbt.getInteger("cX");
				coreY = nbt.getInteger("cY");
				coreZ = nbt.getInteger("cZ");
			} else {
				block = null;
			}
		}
		
		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);

			if(block != null) {
				nbt.setInteger("block", Block.getIdFromBlock(block));
				nbt.setInteger("cX", coreX);
				nbt.setInteger("cY", coreY);
				nbt.setInteger("cZ", coreZ);
			}
		}
		
		@Override
		public void markDirty() {
			if(this.worldObj != null) {
				this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
			}
		}
		
		public TileEntityPWRController cachedCore;
		
		protected TileEntityPWRController getCore() {
			
			if(cachedCore != null && !cachedCore.isInvalid()) return cachedCore;

			if(worldObj.getChunkProvider().chunkExists(coreX >> 4, coreZ >> 4)) {
				
				TileEntity tile = worldObj.getTileEntity(coreX, coreY, coreZ);
				if(tile instanceof TileEntityPWRController) {
					TileEntityPWRController controller = (TileEntityPWRController) tile;
					cachedCore = controller;
					return controller;
				}
			}
			
			return null;
		}

		@Override
		public long transferFluid(FluidType type, int pressure, long fluid) {
			
			if(this.getBlockMetadata() != 1) return fluid;
			if(block == null) return fluid;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.transferFluid(type, pressure, fluid);
			
			return fluid;
		}

		@Override
		public long getDemand(FluidType type, int pressure) {
			if(this.getBlockMetadata() != 1) return 0;
			if(block == null) return 0;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.getDemand(type, pressure);
			return 0;
		}

		@Override
		public FluidTank[] getAllTanks() {
			if(this.getBlockMetadata() != 1) return FluidTank.EMPTY_ARRAY;
			if(block == null) return FluidTank.EMPTY_ARRAY;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.getAllTanks();
			return FluidTank.EMPTY_ARRAY;
		}

		@Override
		public boolean canConnect(FluidType type, ForgeDirection dir) {
			return this.getBlockMetadata() == 1;
		}

		@Override
		public int getSizeInventory() {
			
			if(this.getBlockMetadata() != 1) return 0;
			if(block == null) return 0;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.getSizeInventory();
			
			return 0;
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			
			if(this.getBlockMetadata() != 1) return null;
			if(block == null) return null;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.getStackInSlot(slot);
			
			return null;
		}

		@Override
		public ItemStack decrStackSize(int slot, int amount) {
			
			if(this.getBlockMetadata() != 1) return null;
			if(block == null) return null;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.decrStackSize(slot, amount);
			
			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int slot) {
			
			if(this.getBlockMetadata() != 1) return null;
			if(block == null) return null;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.getStackInSlotOnClosing(slot);
			
			return null;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) {
			
			if(this.getBlockMetadata() != 1) return;
			if(block == null) return;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) controller.setInventorySlotContents(slot, stack);
		}

		@Override
		public int getInventoryStackLimit() {
			
			if(this.getBlockMetadata() != 1) return 0;
			if(block == null) return 0;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.getInventoryStackLimit();
			
			return 0;
		}

		@Override public boolean isUseableByPlayer(EntityPlayer player) { return false; }
		@Override public void openInventory() { }
		@Override public void closeInventory() { }
		@Override public String getInventoryName() { return ""; }
		@Override public boolean hasCustomInventoryName() { return false; }

		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) {
			
			if(this.getBlockMetadata() != 1) return false;
			if(block == null) return false;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.isItemValidForSlot(slot, stack);
			
			return false;
		}

		@Override
		public int[] getAccessibleSlotsFromSide(int side) {
			
			if(this.getBlockMetadata() != 1) return new int[0];
			if(block == null) return new int[0];
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.getAccessibleSlotsFromSide(side);
			
			return new int[0];
		}

		@Override
		public boolean canInsertItem(int slot, ItemStack stack, int side) {
			
			if(this.getBlockMetadata() != 1) return false;
			if(block == null) return false;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.canInsertItem(slot, stack, side);
			
			return false;
		}

		@Override
		public boolean canExtractItem(int slot, ItemStack stack, int side) {
			
			if(this.getBlockMetadata() != 1) return false;
			if(block == null) return false;
			TileEntityPWRController controller = this.getCore();
			if(controller != null) return controller.canExtractItem(slot, stack, side);
			
			return false;
		}

		public boolean isLoaded = true;
		
		@Override
		public boolean isLoaded() {
			return isLoaded;
		}

		@Override
		public void onChunkUnload() {
			super.onChunkUnload();
			this.isLoaded = false;
		}
	}
}
