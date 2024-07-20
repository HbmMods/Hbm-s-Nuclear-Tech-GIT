package com.hbm.blocks;

import java.awt.Color;

import com.hbm.blocks.generic.BlockEmitter.TileEntityEmitter;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;

import api.hbm.block.IToolable.ToolType;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockVolcanoV2 extends BlockContainer {

	public BlockVolcanoV2(Material rock) {
		super(rock);
	}


	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLightningVolcano();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	public static class TileEntityLightningVolcano extends TileEntity implements INBTPacketReceiver {
		
		public static final int range = 100;
		public int chargetime;
		public float flashd;

		@Override
		public void updateEntity() {
            if (chargetime <= 0 || chargetime <= 100) {
                chargetime += 1;
                flashd = 0;
            } else if (chargetime >= 100) {
                flashd += 0.3f;
                flashd = Math.min(100.0f, flashd + 0.3f * (100.0f - flashd) * 0.15f);

                if (flashd <= 5) {
                    //worldObj.playSound("hbm:misc.fireflash", 10F, 1F);
                }

                if (flashd >= 100) {
                    chargetime = 0;
                }
            }
			if(!worldObj.isRemote) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				if(worldObj.getTotalWorldTime() % 20 == 0) {
					for(int i = 1; i <= range; i++) {
						
		
						int x = xCoord + dir.offsetX * i;
						int y = yCoord + dir.offsetY * i;
						int z = zCoord + dir.offsetZ * i;
						
						Block b = worldObj.getBlock(x, y, z);
						if(b.isBlockSolid(worldObj, x, y, z, dir.ordinal())) {
							break;
						}

					}
				}
				if(chargetime == 100) {

					double x = (int) (xCoord + dir.offsetX * (worldObj.getTotalWorldTime() / 5L) % 1) + 0.5;
					double y = (int) (yCoord + dir.offsetY * (worldObj.getTotalWorldTime() / 5L) % 1) + 20.5;
					double z = (int) (zCoord + dir.offsetZ * (worldObj.getTotalWorldTime() / 5L) % 1) + 0.5;
						
						
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "plasmablast");
					data.setFloat("r", 1F);
					data.setFloat("g", 1F);
					data.setFloat("b", 1F);
					data.setFloat("scale", 10 * 4);
					data.setFloat("yaw", 90);
						
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z),
								new TargetPoint(worldObj.provider.dimensionId, x, y, z, 150));

				
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("charge", this.chargetime);
				data.setFloat("flashbang", this.flashd);

				PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			}
			System.out.println(chargetime);
		}



		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			nbt.getInteger("charge");
			nbt.getFloat("flashbang");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("charge", this.chargetime);
			nbt.setFloat("flashbang", this.flashd);
		}
		
		@Override
		public AxisAlignedBB getRenderBoundingBox() {
			return TileEntity.INFINITE_EXTENT_AABB;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared() {
			return 65536.0D;
		}

		@Override
		public void networkUnpack(NBTTagCompound nbt) {

		}
	}
}
