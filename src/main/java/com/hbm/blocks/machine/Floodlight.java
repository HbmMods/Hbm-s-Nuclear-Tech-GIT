package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.FloodlightBeam.TileEntityFloodlightBeam;
import com.hbm.util.Compat;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import api.hbm.block.IToolable;
import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Floodlight extends BlockContainer implements IToolable, INBTBlockTransformable {

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
		setAngle(world, x, y, z, player, true);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER) return false;
		setAngle(world, x, y, z, player, false);
		return true;
	}

	public void setAngle(World world, int x, int y, int z, EntityLivingBase player, boolean updateMeta) {

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		float rotation = player.rotationPitch;

		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileEntityFloodlight) {
			int meta = world.getBlockMetadata(x, y, z) % 6;
			TileEntityFloodlight floodlight = (TileEntityFloodlight) tile;

			if(meta == 0 || meta == 1) {
				if(i == 0 || i == 2) if(updateMeta) world.setBlockMetadataWithNotify(x, y, z, meta + 6, 3);
				if(meta == 1) if(i == 0 || i == 1) rotation = 180F - rotation;
				if(meta == 0) if(i == 0 || i == 3) rotation = 180F - rotation;
			}

			floodlight.rotation = -Math.round(rotation / 5F) * 5F;
			if(floodlight.isOn) floodlight.destroyLights();
			tile.markDirty();
		}
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		if(meta < 6) {
			switch(coordBaseMode) {
				case 1: // West
					switch(meta) {
						case 2: return 5;
						case 3: return 4;
						case 4: return 2;
						case 5: return 3;
					}
					break;
				case 2: // North
					switch(meta) {
						case 2: return 3;
						case 3: return 2;
						case 4: return 5;
						case 5: return 4;
					}
					break;
				case 3: // East
					switch(meta) {
						case 2: return 4;
						case 3: return 5;
						case 4: return 3;
						case 5: return 2;
					}
					break;
			}
		}

		// Also rotate the upper bits that store additional state (6-11)
		if(meta >= 6) {
			return transformMeta(meta - 6, coordBaseMode) + 6;
		}

		return meta;
	}

	@Override
	public Block transformBlock(Block block) {
		return block; // No block transformation needed
	}

	public static class TileEntityFloodlight extends TileEntity implements IEnergyReceiverMK2 {

		public float rotation;
		protected BlockPos[] lightPos = new BlockPos[15];
		public static final long maxPower = 5_000;
		public long power;

		public int delay;
		public boolean isOn;

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {

				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() % 6).getOpposite();
				this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);

				if(delay > 0) {
					delay --;
					return;
				}

				if(power >= 100) {
					power -= 100;

					if(!isOn) {
						this.isOn = true;
						this.castLights();
						this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
						this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

					} else {

						long timer = worldObj.getTotalWorldTime();
						if(timer % 5 == 0) {
							timer = timer / 5;
							this.castLight((int) Math.abs(timer % this.lightPos.length));
						}
					}

				} else {
					if(isOn) {
						this.isOn = false;
						this.delay = 60;
						this.destroyLights();
						this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
						this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					}
				}
			}
		}

		private void castLight(int index) {
			BlockPos newPos = this.getRayEndpoint(index);
			BlockPos oldPos = this.lightPos[index];
			this.lightPos[index] = null;

			if(newPos == null || !newPos.equals(oldPos)) { //if the new end point is null or not equal to the previous, delete the previous spot
				if(oldPos != null) {
					TileEntity tile = Compat.getTileStandard(worldObj, oldPos.getX(), oldPos.getY(), oldPos.getZ());
					if(tile instanceof TileEntityFloodlightBeam) {
						TileEntityFloodlightBeam beam = (TileEntityFloodlightBeam) tile;
						if(beam.cache == this) {
							worldObj.setBlock(oldPos.getX(), oldPos.getY(), oldPos.getZ(), Blocks.air, 0, 2);
						}
					}
				}
			}

			if(newPos == null) return;

			if(worldObj.getBlock(newPos.getX(), newPos.getY(), newPos.getZ()) == Blocks.air) {
				worldObj.setBlock(newPos.getX(), newPos.getY(), newPos.getZ(), ModBlocks.floodlight_beam, 0, 2);
				TileEntity tile = Compat.getTileStandard(worldObj, newPos.getX(), newPos.getY(), newPos.getZ());
				if(tile instanceof TileEntityFloodlightBeam) ((TileEntityFloodlightBeam) tile).setSource(this, newPos.getX(), newPos.getY(), newPos.getZ(), index);
				this.lightPos[index] = newPos;
			}

			if(worldObj.getBlock(newPos.getX(), newPos.getY(), newPos.getZ()) == ModBlocks.floodlight_beam) {
				this.lightPos[index] = newPos;
			}
		}

		public BlockPos getRayEndpoint(int index) {

			if(index < 0 || index >= lightPos.length) return null;

			int meta = this.getBlockMetadata();
			Vec3 dir = Vec3.createVectorHelper(1, 0, 0);

			float[] angles = getVariation(index);

			float rotation = this.rotation;
			if(meta == 1 || meta == 7) rotation = 180 - rotation;
			if(meta == 6) rotation = 180 - rotation;
			dir.rotateAroundZ((float) (rotation / 180D * Math.PI) + angles[0]);

			if(meta == 6) dir.rotateAroundY((float) (Math.PI / 2D));
			if(meta == 7) dir.rotateAroundY((float) (Math.PI / 2D));
			if(meta == 2) dir.rotateAroundY((float) (Math.PI / 2D));
			if(meta == 3) dir.rotateAroundY((float) -(Math.PI / 2D));
			if(meta == 4) dir.rotateAroundY((float) (Math.PI));
			dir.rotateAroundY(angles[1]);

			for(int i = 1; i < 64; i++) {
				int iX = (int) Math.floor(xCoord + 0.5 + dir.xCoord * i);
				int iY = (int) Math.floor(yCoord + 0.5 + dir.yCoord * i);
				int iZ = (int) Math.floor(zCoord + 0.5 + dir.zCoord * i);

				if(iX == xCoord && iY == yCoord && iZ == zCoord) continue;

				Block block = worldObj.getBlock(iX, iY, iZ);
				if(block.getLightOpacity(worldObj, iX, iY, iZ) < 127) continue;

				int fX = (int) Math.floor(xCoord + 0.5 + dir.xCoord * (i - 1));
				int fY = (int) Math.floor(yCoord + 0.5 + dir.yCoord * (i - 1));
				int fZ = (int) Math.floor(zCoord + 0.5 + dir.zCoord * (i - 1));

				if(i > 1) return new BlockPos(fX, fY, fZ);
			}

			return null;
		}

		private void castLights() {
			for(int i = 0; i < this.lightPos.length; i++) this.castLight(i);
		}

		private void destroyLight(int index) {
			BlockPos pos = lightPos[index];
			if(pos != null) {
				if(pos != null && worldObj.getBlock(pos.getX(), pos.getY(), pos.getZ()) == ModBlocks.floodlight_beam) {
					worldObj.setBlock(pos.getX(), pos.getY(), pos.getZ(), Blocks.air, 0, 2);
				}
			}
		}

		private void destroyLights() {
			for(int i = 0; i < this.lightPos.length; i++) destroyLight(i);
		}

		private float[] getVariation(int index) {
			return new float[] {
					(((index / 3) - 2) * 7.5F) / 180F * (float) Math.PI,
					(((index % 3) - 1) * 15F) / 180F * (float) Math.PI
				};
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
			this.isOn = nbt.getBoolean("isOn");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setFloat("rotation", rotation);
			nbt.setLong("power", power);
			nbt.setBoolean("isOn", isOn);
		}

		@Override public long getPower() { return power; }
		@Override public void setPower(long power) { this.power = power; }
		@Override public long getMaxPower() { return maxPower; }

		private boolean isLoaded = true;
		@Override public boolean isLoaded() { return isLoaded; }
		@Override public void onChunkUnload() { this.isLoaded = false; }

		AxisAlignedBB bb = null;

		@Override
		public AxisAlignedBB getRenderBoundingBox() {

			if(bb == null) {
				bb = AxisAlignedBB.getBoundingBox(
						xCoord - 1,
						yCoord - 1,
						zCoord - 1,
						xCoord + 2,
						yCoord + 2,
						zCoord + 2
						);
			}

			return bb;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public double getMaxRenderDistanceSquared() {
			return 65536.0D;
		}
	}
}
