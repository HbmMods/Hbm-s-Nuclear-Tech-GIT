package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.ct.CT;
import com.hbm.render.block.ct.CTStitchReceiver;
import com.hbm.render.block.ct.IBlockCT;
import com.hbm.tileentity.machine.TileEntityPWRController;

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
	
	public static class TileEntityBlockPWR extends TileEntity {
		
		public Block block;
		public int coreX;
		public int coreY;
		public int coreZ;
		
		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote) {
				
				if(worldObj.getTotalWorldTime() % 20 == 0 && block != null) {
					
					if(worldObj.getChunkProvider().chunkExists(coreX >> 4, coreZ >> 4)) {
						
						TileEntity tile = worldObj.getTileEntity(coreX, coreY, coreZ);
						
						if(tile instanceof TileEntityPWRController) {
							TileEntityPWRController controller = (TileEntityPWRController) tile;
							if(!controller.assembled) {
								this.getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, this.getBlockType(), this.getBlockMetadata());
							}
						} else {
							this.getBlockType().breakBlock(worldObj, xCoord, yCoord, zCoord, this.getBlockType(), this.getBlockMetadata());
						}
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
	}
}
