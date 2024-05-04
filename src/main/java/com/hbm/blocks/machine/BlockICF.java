package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;
import com.hbm.tileentity.machine.TileEntityICFController;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockICF extends BlockContainer implements IBlockCT {
	
	@SideOnly(Side.CLIENT) protected IIcon iconPort;
	
	public BlockICF(Material mat) {
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
		this.iconPort = reg.registerIcon(RefStrings.MODID + ":icf_block_port");
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
		return block == ModBlocks.icf_block || block == ModBlocks.icf_controller;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBlockICF();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityBlockICF) {
			TileEntityBlockICF icf = (TileEntityBlockICF) tile;
			world.removeTileEntity(x, y, z);
			if(icf.block != null) {
				world.setBlock(x, y, z, icf.block, icf.meta, 3);
				TileEntity controller = world.getTileEntity(icf.coreX, icf.coreY, icf.coreZ);
				
				if(controller instanceof TileEntityICFController) {
					((TileEntityICFController) controller).assembled = false;
				}
			}
		} else {
			world.removeTileEntity(x, y, z);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	public static class TileEntityBlockICF extends TileEntity implements IEnergyReceiverMK2 {

		public Block block;
		public int meta;
		public int coreX;
		public int coreY;
		public int coreZ;
		
		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote) {
				
				if(worldObj.getTotalWorldTime() % 20 == 0 && block != null) {
					
					TileEntityICFController controller = getCore();
					
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
				meta = nbt.getInteger("meta");
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
				nbt.setInteger("meta", meta);
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
		
		public TileEntityICFController cachedCore;
		
		protected TileEntityICFController getCore() {
			
			if(cachedCore != null && !cachedCore.isInvalid()) return cachedCore;

			if(worldObj.getChunkProvider().chunkExists(coreX >> 4, coreZ >> 4)) {
				
				TileEntity tile = worldObj.getTileEntity(coreX, coreY, coreZ);
				if(tile instanceof TileEntityICFController) {
					TileEntityICFController controller = (TileEntityICFController) tile;
					cachedCore = controller;
					return controller;
				}
			}
			
			return null;
		}

		@Override public long getPower() {
			if(this.getBlockMetadata() != 1) return 0;
			if(block == null) return 0;
			TileEntityICFController controller = this.getCore();
			if(controller != null) return controller.getPower();
			
			return 0;
		}

		@Override public void setPower(long power) {
			if(this.getBlockMetadata() != 1) return;
			if(block == null) return;
			TileEntityICFController controller = this.getCore();
			if(controller != null) controller.setPower(power);
		}

		@Override public long getMaxPower() {
			if(this.getBlockMetadata() != 1) return 0;
			if(block == null) return 0;
			TileEntityICFController controller = this.getCore();
			if(controller != null) return controller.getMaxPower();
			
			return 0;
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
		
		@Override
		public boolean canConnect(ForgeDirection dir) {
			if(this.getBlockMetadata() != 1) return false;
			return dir != ForgeDirection.UNKNOWN;
		}
	}
}
