package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;

import java.util.Random;

import com.hbm.blocks.BlockEnums.LightType;
import com.hbm.blocks.ISpotlight;
import com.hbm.main.ResourceManager;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

public class Spotlight extends Block implements ISpotlight, INBTBlockTransformable {

	public static boolean disableOnGeneration = true;

	// I'd be extending the ReinforcedLamp class if it wasn't for the inverted behaviour of these specific lights
	// I want these blocks to be eminently useful, so removing the need for redstone by default is desired,
	// these act more like redstone torches, in that applying a signal turns them off
	public boolean isOn;

	public int beamLength;
	public LightType type;

	public Spotlight(Material mat, int beamLength, LightType type, boolean isOn) {
		super(mat);

		this.beamLength = beamLength;
		this.type = type;
		this.isOn = isOn;

		this.setHardness(0.5F);

		if(isOn) setLightLevel(1.0F);
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
	// Ah yes, I love methods named the literal opposite of what they do
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

	// Returns an xyz (half-)size for a given object type
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
		if(updatePower(world, x, y, z)) return;
		updateBeam(world, x, y, z);
	}

	private boolean updatePower(World world, int x, int y, int z) {
		if(isBroken(world.getBlockMetadata(x, y, z))) return false;

		boolean isPowered = world.isBlockIndirectlyGettingPowered(x, y, z);
		if(isOn && isPowered) {
			world.scheduleBlockUpdate(x, y, z, this, 4);
			return true;
		} else if(!isOn && !isPowered) {
			world.setBlock(x, y, z, getOn(), world.getBlockMetadata(x, y, z), 2);
			return true;
		}

		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
		ForgeDirection dir = getDirection(metadata);
		super.breakBlock(world, x, y, z, block, metadata);

		if(world.isRemote) return;

		unpropagateBeam(world, x, y, z, dir);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random p_149674_5_) {
		if (world.isRemote) return;

		if (isOn && world.isBlockIndirectlyGettingPowered(x, y, z)) {
			world.setBlock(x, y, z, getOff(), world.getBlockMetadata(x, y, z), 2);
		}
	}

	// Repropagate the beam if we've become unblocked
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		if(world.isRemote) return;
		if(neighborBlock instanceof SpotlightBeam) return;
		if(neighborBlock == Blocks.air) return;

		ForgeDirection dir = getDirection(world, x, y, z);

		if(!canPlace(world, x, y, z, dir)) {
			dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockToAir(x, y, z);
			return;
		}

		if(updatePower(world, x, y, z)) return;

		updateBeam(world, x, y, z);
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		if(!super.canPlaceBlockOnSide(world, x, y, z, side)) return false;

		ForgeDirection dir = ForgeDirection.getOrientation(side);

		return canPlace(world, x, y, z, dir);
	}

	// BlockSlab doesn't actually properly return isSideSolid,
	// probably because MOJANK thought this would only ever be used for torches,
	// which can't be placed on ceilings...
	private boolean canPlace(World world, int x, int y, int z, ForgeDirection dir) {
		x -= dir.offsetX;
		y -= dir.offsetY;
		z -= dir.offsetZ;

		Block block = world.getBlock(x, y, z);
		if(block instanceof BlockSlab) {
			int meta = world.getBlockMetadata(x, y, z);
			return dir == ((meta & 8) == 8 ? ForgeDirection.UP : ForgeDirection.DOWN) || block.func_149730_j();
		}

		return block.isSideSolid(world, x, y, z, dir);
	}

	private void updateBeam(World world, int x, int y, int z) {
		if(!isOn) return;

		ForgeDirection dir = getDirection(world, x, y, z);
		propagateBeam(world, x, y, z, dir, beamLength);
	}

	public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		return getDirection(metadata);
	}

	public ForgeDirection getDirection(int metadata) {
		return ForgeDirection.getOrientation(metadata >> 1);
	}

	// Replace bulbs on broken lights with a click
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		int meta = world.getBlockMetadata(x, y, z);
		if(!isBroken(meta)) return false;

		repair(world, x, y, z);
		return true;
	}

	private void repair(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(!isBroken(meta)) return;

		world.setBlock(x, y, z, getOn(), meta - 1, 2);

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			int ox = x + dir.offsetX;
			int oy = y + dir.offsetY;
			int oz = z + dir.offsetZ;
			Block block = world.getBlock(ox, oy, oz);
			if(block == this) repair(world, ox, oy, oz);
		}
	}

	public boolean isBroken(int metadata) {
		return (metadata & 1) == 1;
	}

	@Override
	public Item getItemDropped(int i, Random r, int j) {
		return Item.getItemFromBlock(getOn());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(getOn());
	}

	@Override
	protected ItemStack createStackedBlock(int e) {
		return new ItemStack(getOn());
	}

	// Recursively add beam blocks, updating any that already exist with new incoming light directions
	public static void propagateBeam(World world, int x, int y, int z, ForgeDirection dir, int distance) {
		distance--;
		if(distance <= 0)
			return;

		x += dir.offsetX;
		y += dir.offsetY;
		z += dir.offsetZ;

		Block block = world.getBlock(x, y, z);
		if(!block.isAir(world, x, y, z))
			return;

		if(!(block instanceof SpotlightBeam)) {
			world.setBlock(x, y, z, ModBlocks.spotlight_beam);
		}

		// If we encounter an existing beam, add a new INCOMING direction to the
		// metadata, and cancel propagation if something goes wrong
		if (SpotlightBeam.setDirection(world, x, y, z, dir, true) == 0)
			return;

		propagateBeam(world, x, y, z, dir, distance);
	}

	// Recursively delete beam blocks, if they aren't still illuminated from a different direction
	public static void unpropagateBeam(World world, int x, int y, int z, ForgeDirection dir) {
		x += dir.offsetX;
		y += dir.offsetY;
		z += dir.offsetZ;

		Block block = world.getBlock(x, y, z);
		if(!(block instanceof SpotlightBeam))
			return;

		// Remove the metadata associated with this direction
		// If all directions are set to zero, delete the beam
		if(SpotlightBeam.setDirection(world, x, y, z, dir, false) == 0) {
			world.setBlockToAir(x, y, z);
		}

		unpropagateBeam(world, x, y, z, dir);
	}

	// Travels back through a beam to the source, and if found, repropagates the beam
	public static void backPropagate(World world, int x, int y, int z, ForgeDirection dir) {
		x -= dir.offsetX;
		y -= dir.offsetY;
		z -= dir.offsetZ;

		Block block = world.getBlock(x, y, z);
		if(block instanceof ISpotlight) {
			ISpotlight spot = (ISpotlight) block;
			propagateBeam(world, x, y, z, dir, spot.getBeamLength());
		} else if(!(block instanceof SpotlightBeam)) {
			return;
		}

		backPropagate(world, x, y, z, dir);
	}

	protected Block getOff() {
		if(this == ModBlocks.spotlight_incandescent) return ModBlocks.spotlight_incandescent_off;
		if(this == ModBlocks.spotlight_fluoro) return ModBlocks.spotlight_fluoro_off;
		if(this == ModBlocks.spotlight_halogen) return ModBlocks.spotlight_halogen_off;

		return this;
	}

	protected Block getOn() {
		if(this == ModBlocks.spotlight_incandescent_off) return ModBlocks.spotlight_incandescent;
		if(this == ModBlocks.spotlight_fluoro_off) return ModBlocks.spotlight_fluoro;
		if(this == ModBlocks.spotlight_halogen_off) return ModBlocks.spotlight_halogen;

		return this;
	}

	@Override
	public int getBeamLength() {
		return this.beamLength;
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		// +1 to set as broken, won't turn on until broken and replaced
		int disabled = disableOnGeneration ? 1 : 0;
		return (INBTBlockTransformable.transformMetaDeco(meta >> 1, coordBaseMode) << 1) + disabled;
	}

	@Override
	public Block transformBlock(Block block) {
		if(!disableOnGeneration) return block;
		if(block == ModBlocks.spotlight_incandescent) return ModBlocks.spotlight_incandescent_off;
		if(block == ModBlocks.spotlight_fluoro) return ModBlocks.spotlight_fluoro_off;
		if(block == ModBlocks.spotlight_halogen) return ModBlocks.spotlight_halogen_off;
		return block;
	}

}