package com.hbm.blocks.machine;

import com.hbm.blocks.BlockEnums.LightType;
import com.hbm.blocks.ISpotlight;
import com.hbm.main.ResourceManager;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import api.hbm.energymk2.IEnergyReceiverMK2;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

public class SpotlightPowered extends BlockContainer implements ISpotlight, INBTBlockTransformable {

	public int beamLength;
	public LightType type;
	public long powerConsumption;
	public boolean isOn;

	public SpotlightPowered(Material mat, int beamLength, LightType type, long powerConsumption, boolean isOn) {
		super(mat);

		this.beamLength = beamLength;
		this.type = type;
		this.powerConsumption = powerConsumption;
		this.isOn = isOn;

		this.setHardness(0.5F);
		
		if(isOn) setLightLevel(1.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntitySpotlightPowered();
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
	}

	public WavefrontObject getModel() {
		switch(type) {
		case FLUORESCENT: return (WavefrontObject) ResourceManager.fluorescent_lamp;
		case HALOGEN: return (WavefrontObject) ResourceManager.flood_lamp;
		default: return (WavefrontObject) ResourceManager.cage_lamp;
		}
	}

	public String getPartName(int connectionCount) {
		switch(type) {
		case HALOGEN: return "FloodLamp";
		default: return "CageLamp";
		}
	}



	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public MapColor getMapColor(int meta) {
        return MapColor.airColor;
    }

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		ForgeDirection dir = getDirection(world, x, y, z);
		float[] bounds = swizzleBounds(dir);
		float[] offset = new float[] { 0.5F - dir.offsetX * (0.5F - bounds[0]), 0.5F - dir.offsetY * (0.5F - bounds[1]), 0.5F - dir.offsetZ * (0.5F - bounds[2]) };

		setBlockBounds(offset[0] - bounds[0], offset[1] - bounds[1], offset[2] - bounds[2], offset[0] + bounds[0], offset[1] + bounds[1], offset[2] + bounds[2]);
	}

	private float[] swizzleBounds(ForgeDirection dir) {
		float[] bounds = getBounds();
		switch(dir) {
		case EAST:
		case WEST: return new float[] { bounds[2], bounds[1], bounds[0] };
		case UP:
		case DOWN: return new float[] { bounds[1], bounds[2], bounds[0] };
		default: return bounds;
		}
	}

	private float[] getBounds() {
		switch(type) {
		case FLUORESCENT: return new float[] { 0.5F, 0.5F, 0.1F };
		case HALOGEN: return new float[] { 0.35F, 0.25F, 0.2F };
		default: return new float[] { 0.25F, 0.2F, 0.15F };
		}
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hx, float hy, float hz, int initData) {
		return side << 1;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if(world.isRemote) return;
		updateBeam(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		ForgeDirection dir = getDirection(metadata);
		super.breakBlock(world, x, y, z, block, metadata);

		if(world.isRemote) return;

		Spotlight.unpropagateBeam(world, x, y, z, dir);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if(world.isRemote) return;
		if(neighborBlock instanceof SpotlightBeam) return;

		ForgeDirection dir = getDirection(world, x, y, z);

		if(!canPlace(world, x, y, z, dir)) {
			dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockToAir(x, y, z);
			return;
		}

		updateBeam(world, x, y, z);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		if(!super.canPlaceBlockOnSide(world, x, y, z, side)) return false;

		ForgeDirection dir = ForgeDirection.getOrientation(side);
		return canPlace(world, x, y, z, dir);
	}

	private boolean canPlace(World world, int x, int y, int z, ForgeDirection dir) {
		x -= dir.offsetX;
		y -= dir.offsetY;
		z -= dir.offsetZ;

		Block block = world.getBlock(x, y, z);
		return block.isSideSolid(world, x, y, z, dir);
	}

	private void updateBeam(World world, int x, int y, int z) {
		if(!isOn) return;

		ForgeDirection dir = getDirection(world, x, y, z);
		Spotlight.propagateBeam(world, x, y, z, dir, beamLength);
	}

	public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		return getDirection(metadata);
	}

	public ForgeDirection getDirection(int metadata) {
		return ForgeDirection.getOrientation(metadata >> 1);
	}

	@Override
	public Item getItemDropped(int i, java.util.Random r, int j) {
		return Item.getItemFromBlock(getOff());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(getOff());
	}

	@Override
	public int getBeamLength() {
		return this.beamLength;
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return INBTBlockTransformable.transformMetaDeco(meta >> 1, coordBaseMode) << 1;
	}

	@Override
	public Block transformBlock(Block block) {
		return block;
	}

	protected Block getOff() {
		if(this == com.hbm.blocks.ModBlocks.spotlight_incandescent_powered) return com.hbm.blocks.ModBlocks.spotlight_incandescent_powered_off;
		if(this == com.hbm.blocks.ModBlocks.spotlight_fluoro_powered) return com.hbm.blocks.ModBlocks.spotlight_fluoro_powered_off;
		if(this == com.hbm.blocks.ModBlocks.spotlight_halogen_powered) return com.hbm.blocks.ModBlocks.spotlight_halogen_powered_off;
		return this;
	}

	protected Block getOn() {
		if(this == com.hbm.blocks.ModBlocks.spotlight_incandescent_powered_off) return com.hbm.blocks.ModBlocks.spotlight_incandescent_powered;
		if(this == com.hbm.blocks.ModBlocks.spotlight_fluoro_powered_off) return com.hbm.blocks.ModBlocks.spotlight_fluoro_powered;
		if(this == com.hbm.blocks.ModBlocks.spotlight_halogen_powered_off) return com.hbm.blocks.ModBlocks.spotlight_halogen_powered;
		return this;
	}

	public static class TileEntitySpotlightPowered extends TileEntity implements IEnergyReceiverMK2 {

		public static final long maxPower = 1000;
		public long power;
		public long powerConsumption;

		public TileEntitySpotlightPowered() {
			this.powerConsumption = 40;
		}

		@Override
		public void updateEntity() {
			if(!worldObj.isRemote) {
				ForgeDirection dir = getDirection().getOpposite();
				this.trySubscribe(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);

				Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
				if(!(block instanceof SpotlightPowered)) return;
				
				SpotlightPowered spotlight = (SpotlightPowered) block;
				this.powerConsumption = spotlight.powerConsumption;
				
				if(power >= powerConsumption) {
					power -= powerConsumption;
					if(!spotlight.isOn) {
						switchToState(true);
					}
				} else {
					if(spotlight.isOn) {
						switchToState(false);
					}
				}
			}
		}
		
		protected void switchToState(boolean turnOn) {
			Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
			if(!(block instanceof SpotlightPowered)) return;
			
			SpotlightPowered spotlight = (SpotlightPowered) block;
			Block targetBlock = turnOn ? spotlight.getOn() : spotlight.getOff();
			
			if(block == targetBlock) return;
			
			NBTTagCompound data = new NBTTagCompound();
			this.writeToNBT(data);
			
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			worldObj.setBlock(xCoord, yCoord, zCoord, targetBlock, meta, 2);
			
			TileEntity newTE = worldObj.getTileEntity(xCoord, yCoord, zCoord);
			if(newTE instanceof TileEntitySpotlightPowered) {
				((TileEntitySpotlightPowered) newTE).readFromNBT(data);
				newTE.validate();
			}
		}
		
		@Override
		public boolean canUpdate() {
			return true;
		}
		
		@Override
		public void invalidate() {
			super.invalidate();
			if(!worldObj.isRemote) {
				Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
				if(block instanceof SpotlightPowered) {
					SpotlightPowered spotlight = (SpotlightPowered) block;
					if(spotlight.isOn) {
						ForgeDirection dir = getDirection();
						Spotlight.unpropagateBeam(worldObj, xCoord, yCoord, zCoord, dir);
					}
				}
			}
		}
		
		@Override
		public void onChunkUnload() {
			super.onChunkUnload();
			if(!worldObj.isRemote) {
				Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
				if(block instanceof SpotlightPowered) {
					SpotlightPowered spotlight = (SpotlightPowered) block;
					if(spotlight.isOn) {
						ForgeDirection dir = getDirection();
						Spotlight.unpropagateBeam(worldObj, xCoord, yCoord, zCoord, dir);
					}
				}
			}
			this.isLoaded = false;
		}

		private ForgeDirection getDirection() {
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			return ForgeDirection.getOrientation(metadata >> 1);
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
			this.power = nbt.getLong("power");
			this.powerConsumption = nbt.getLong("powerConsumption");
		}

		@Override
		public void writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setLong("power", power);
			nbt.setLong("powerConsumption", powerConsumption);
		}

		@Override public long getPower() { return power; }
		@Override public void setPower(long power) { this.power = power; }
		@Override public long getMaxPower() { return maxPower; }

		private boolean isLoaded = true;
		@Override public boolean isLoaded() { return isLoaded; }
	}
}
