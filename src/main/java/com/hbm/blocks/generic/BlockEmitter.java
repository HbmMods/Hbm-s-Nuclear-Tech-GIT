package com.hbm.blocks.generic;

import java.awt.Color;
import java.util.List;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.packet.toclient.NBTPacket;
import com.hbm.tileentity.INBTPacketReceiver;

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

public class BlockEmitter extends BlockContainer implements IToolable, ITooltipProvider {

	public BlockEmitter() {
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
		
		if(player.getHeldItem() != null) {

			if(player.getHeldItem().getItem() instanceof ItemDye) {
				te.color = ItemDye.field_150922_c[player.getHeldItem().getItemDamage()];
				te.markDirty();
				world.markBlockForUpdate(x, y, z);
				player.getHeldItem().stackSize--;
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {

		TileEntityEmitter te = (TileEntityEmitter)world.getTileEntity(x, y, z);
		
		if(tool == ToolType.SCREWDRIVER) {
			te.girth += 0.125F;
			te.markDirty();
			return true;
		}
		
		if(tool == ToolType.DEFUSER) {
			te.girth -= 0.125F;
			if(te.girth < 0.125F) te.girth = 0.125F;
			te.markDirty();
			return true;
		}
		
		if(tool == ToolType.HAND_DRILL) {
			te.effect = (te.effect + 1) % te.effectCount;
			te.markDirty();
			return true;
		}
		
		return false;
	}

	public static class TileEntityEmitter extends TileEntity implements INBTPacketReceiver {
		
		public static final int range = 100;
		public int color;
		public int beam;
		public float girth = 0.5F;
		public int effect = 0;
		public static final int effectCount = 5;

		@Override
		public void updateEntity() {
			
			if(!worldObj.isRemote) {
				
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());
				
				if(worldObj.getTotalWorldTime() % 20 == 0) {
					for(int i = 1; i <= range; i++) {
						
						beam = i;
		
						int x = xCoord + dir.offsetX * i;
						int y = yCoord + dir.offsetY * i;
						int z = zCoord + dir.offsetZ * i;
						
						Block b = worldObj.getBlock(x, y, z);
						if(b.isBlockSolid(worldObj, x, y, z, dir.ordinal())) {
							break;
						}
					}
				}
				
				if(effect == 4 && beam > 0) {

					if(worldObj.getTotalWorldTime() % 5 == 0) {
						double x = (int) (xCoord + dir.offsetX * (worldObj.getTotalWorldTime() / 5L) % beam) + 0.5;
						double y = (int) (yCoord + dir.offsetY * (worldObj.getTotalWorldTime() / 5L) % beam) + 0.5;
						double z = (int) (zCoord + dir.offsetZ * (worldObj.getTotalWorldTime() / 5L) % beam) + 0.5;
						
						int prevColor = color;
						if(color == 0) {
							color = Color.HSBtoRGB(worldObj.getTotalWorldTime() / 50.0F, 0.5F, 0.25F) & 16777215;
						}
						
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "plasmablast");
						data.setFloat("r", ((float)((color & 0xff0000) >> 16)) / 256F);
						data.setFloat("g", ((float)((color & 0x00ff00) >> 8)) / 256F);
						data.setFloat("b", ((float)((color & 0x0000ff))) / 256F);
						data.setFloat("scale", girth * 5);

						if(this.getBlockMetadata() == 2) {
							data.setFloat("pitch", 90);
						}
						if(this.getBlockMetadata() == 3) {
							data.setFloat("pitch", -90);
						}
						if(this.getBlockMetadata() == 4) {
							data.setFloat("pitch", 90);
							data.setFloat("yaw", 90);
						}
						if(this.getBlockMetadata() == 5) {
							data.setFloat("pitch", -90);
							data.setFloat("yaw", 90);
						}
						
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z),
								new TargetPoint(worldObj.provider.dimensionId, x, y, z, 100));
						
						color = prevColor;
					}
				}
				
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("beam", this.beam);
				data.setInteger("color", this.color);
				data.setFloat("girth", this.girth);
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
			this.color = nbt.getInteger("color");
			this.girth = nbt.getFloat("girth");
			this.effect = nbt.getInteger("effect");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("color", this.color);
			nbt.setFloat("girth", this.girth);
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
			this.beam = nbt.getInteger("beam");
			this.color = nbt.getInteger("color");
			this.girth = nbt.getFloat("girth");
			this.effect = nbt.getInteger("effect");
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		list.add(EnumChatFormatting.GOLD + "Use screwdriver to widen beam");
		list.add(EnumChatFormatting.GOLD + "Use defuser to narrow beam");
		list.add(EnumChatFormatting.GOLD + "Use hand drill to cycle special effects");
		list.add(EnumChatFormatting.GOLD + "Use dye to change color");
	}
}
