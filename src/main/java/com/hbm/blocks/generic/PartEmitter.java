package com.hbm.blocks.generic;

import java.awt.Color;
import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.util.ParticleUtil;

import api.hbm.block.IToolable;
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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PartEmitter extends BlockContainer implements IToolable, ITooltipProvider {

	public PartEmitter() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityEmitter();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {

		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float fx, float fy, float fz) {
		
		if(world.isRemote)
			return true;
		
		TileEntityEmitter te = (TileEntityEmitter)world.getTileEntity(x, y, z);
		return false;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		TileEntityEmitter te = (TileEntityEmitter)world.getTileEntity(x, y, z);
		if(tool == ToolType.HAND_DRILL) {
			te.effect = (te.effect + 1) % te.effectCount;
			te.markDirty();
			return true;
		}
		
		return false;
	}

	public static class TileEntityEmitter extends TileEntity implements INBTPacketReceiver {

		public static final int range = 100;
		public int effect = 0;
		public static final int effectCount = 5;
		@Override
		public void updateEntity() {
			
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
				
				
				if(effect == 1) {
					
					ParticleUtil.spawnGasFlame(worldObj, xCoord + worldObj.rand.nextDouble(), yCoord + 4.5 + worldObj.rand.nextDouble(), zCoord + worldObj.rand.nextDouble(), worldObj.rand.nextGaussian() * 0.2, 0.1, worldObj.rand.nextGaussian() * 0.2);
					
				}
				
				if(effect == 2) {
					NBTTagCompound ree  = new NBTTagCompound();
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(ree, xCoord + 0.5, yCoord + 1, zCoord + 0.5), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
					MainRegistry.proxy.effectNT(ree);
					ree.setString("type", "tower");
					ree.setFloat("lift", 1F);
					ree.setFloat("base", 0.25F);
					ree.setFloat("max", 3F);
					ree.setInteger("life", 150 + worldObj.rand.nextInt(20));
					ree.setInteger("color",0x404040);

					ree.setDouble("posX", xCoord + 0.5);
					ree.setDouble("posZ", zCoord + 0.5);
					ree.setDouble("posY", yCoord + 11);
				}
				if(effect == 3) {
					
					
				}
				if(effect == 4) {
					
					
				}
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("effect", this.effect);
				PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			}
		}

		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound nbt = new NBTTagCompound();
			this.writeToNBT(nbt);
			return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbt);
		}
		
		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.func_148857_g());
		}

		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			this.effect = nbt.getInteger("effect");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			nbt.setInteger("effect", this.effect);
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
			this.effect = nbt.getInteger("effect");
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Use hand drill to cycle special effects");
	}
}
