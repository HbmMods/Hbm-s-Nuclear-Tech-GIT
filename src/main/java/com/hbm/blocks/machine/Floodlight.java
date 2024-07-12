package com.hbm.blocks.machine;

import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.block.IToolable;
import api.hbm.energymk2.IEnergyReceiverMK2;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Floodlight extends BlockContainer implements IToolable {

	public Floodlight(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFloodlight();
	}

	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	//only method that respects sides, called first for orientation
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fX, float fY, float fZ, int meta) {
		return side;
	}
	
	//only method with player param, called second for variable rotation
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		setAngle(world, x, y, z, player);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER) return false;
		setAngle(world, x, y, z, player);
		return true;
	}
	
	public void setAngle(World world, int x, int y, int z, EntityLivingBase player) {
		
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		float rotation = player.rotationPitch;
		
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(tile instanceof TileEntityFloodlight) {
			int meta = world.getBlockMetadata(x, y, z);
			TileEntityFloodlight floodlight = (TileEntityFloodlight) tile;
			
			if(meta == 0 || meta == 1) {
				if(i == 0 || i == 2) world.setBlockMetadataWithNotify(x, y, z, meta + 6, 3);
				if(meta == 1) if(i == 0 || i == 1) rotation = 180F - rotation;
				if(meta == 0) if(i == 0 || i == 3) rotation = 180F - rotation;
			}

			floodlight.rotation = -Math.round(rotation / 5F) * 5F;
			tile.markDirty();
		}
	}
	
	public static class TileEntityFloodlight extends TileEntity implements IEnergyReceiverMK2 {
		
		public float rotation;
		protected BlockPos[] lightPos = new BlockPos[9];
		public static final long maxPower = 10_000;
		public long power;
		
		@Override
		public void updateEntity() {
			
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
			this.rotation = nbt.getFloat("rotation");
			this.power = nbt.getLong("power");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setFloat("rotation", rotation);
			nbt.setLong("power", power);
		}

		@Override public long getPower() { return power; }
		@Override public void setPower(long power) { this.power = power; }
		@Override public long getMaxPower() { return maxPower; }

		private boolean isLoaded = true;
		@Override public boolean isLoaded() { return isLoaded; }
		@Override public void onChunkUnload() { this.isLoaded = false; }
	}
}
