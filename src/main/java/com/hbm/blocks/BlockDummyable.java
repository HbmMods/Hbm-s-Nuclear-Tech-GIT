package com.hbm.blocks;

import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.ThreeInts;
import com.hbm.interfaces.ICopiable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IPersistentNBT;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BlockDummyable extends BlockContainer implements ICustomBlockHighlight, ICopiable {

	public BlockDummyable(Material mat) {
		super(mat);
		this.setTickRandomly(true);
	}

	/// BLOCK METADATA ///

	// 0-5 dummy rotation (for dummy neighbor checks)
	// 6-11 extra (6 rotations with flag, for pipe connectors and the like)
	// 12-15 block rotation (for rendering the TE)

	// meta offset from dummy to TE rotation
	public static final int offset = 10;
	// meta offset from dummy to extra rotation
	public static final int extra = 6;

	/*
	 * An extra integer that can be set before block set operations (such as makeExtra) and intercepted in createNewTileEntity.
	 * This way we can inelegantly add variation to the tiles created even if the metadata would be the same.
	 * Why createNewTileEntity only takes two args or why it is used by the chunk's setBlock implementation is beyond me but any
	 * other solution feels like putting in way too much effort to achieve the same thing, really.
	 */
	public static int overrideTileMeta = 0;

	public static boolean safeRem = false;

	public static void setOverride(int i) {
		overrideTileMeta = i;
	}

	public static void resetOverride() {
		overrideTileMeta = 0;
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {

		super.onNeighborBlockChange(world, x, y, z, block);

		if(world.isRemote || safeRem)
			return;

		int metadata = world.getBlockMetadata(x, y, z);

		// if it's an extra, remove the extra-ness
		if(metadata >= extra)
			metadata -= extra;

		ForgeDirection dir = ForgeDirection.getOrientation(metadata).getOpposite();
		Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

		if(b != this) {
			world.setBlockToAir(x, y, z);
		}
	}

	public void updateTick(World world, int x, int y, int z, Random rand) {

		super.updateTick(world, x, y, z, rand);

		if(world.isRemote)
			return;

		int metadata = world.getBlockMetadata(x, y, z);

		// if it's an extra, remove the extra-ness
		if(metadata >= extra)
			metadata -= extra;

		ForgeDirection dir = ForgeDirection.getOrientation(metadata).getOpposite();
		Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

		if(b != this) {
			world.setBlockToAir(x, y, z);
		}

	}

	public int[] findCore(World world, int x, int y, int z) {
		positions.clear();
		return findCoreRec(world, x, y, z);
	}

	List<ThreeInts> positions = new ArrayList();

	public int[] findCoreRec(World world, int x, int y, int z) {

		ThreeInts pos = new ThreeInts(x, y, z);

		int metadata = world.getBlockMetadata(x, y, z);

		// if it's an extra, remove the extra-ness
		if(metadata >= extra)
			metadata -= extra;

		// if the block matches and the orientation is "UNKNOWN", it's the core
		if(world.getBlock(x, y, z) == this && ForgeDirection.getOrientation(metadata) == ForgeDirection.UNKNOWN)
			return new int[] { x, y, z };

		if(positions.contains(pos))
			return null;

		ForgeDirection dir = ForgeDirection.getOrientation(metadata).getOpposite();

		Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

		if(b != this) {
			return null;
		}

		positions.add(pos);

		return findCoreRec(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		if(!(player instanceof EntityPlayer))
			return;

		safeRem = true;
		world.setBlockToAir(x, y, z);
		safeRem = false;

		EntityPlayer pl = (EntityPlayer) player;

		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int o = -getOffset();
		y += getHeightOffset();

		ForgeDirection dir = ForgeDirection.NORTH;

		if(i == 0) {
			dir = ForgeDirection.getOrientation(2);
		}
		if(i == 1) {
			dir = ForgeDirection.getOrientation(5);
		}
		if(i == 2) {
			dir = ForgeDirection.getOrientation(3);
		}
		if(i == 3) {
			dir = ForgeDirection.getOrientation(4);
		}

		dir = getDirModified(dir);

		if(!checkRequirement(world, x, y, z, dir, o)) {

			if(!pl.capabilities.isCreativeMode) {
				ItemStack stack = pl.inventory.mainInventory[pl.inventory.currentItem];
				Item item = Item.getItemFromBlock(this);

				if(stack == null) {
					pl.inventory.mainInventory[pl.inventory.currentItem] = new ItemStack(this);
				} else {
					if(stack.getItem() != item || stack.stackSize == stack.getMaxStackSize()) {
						pl.inventory.addItemStackToInventory(new ItemStack(this));
					} else {
						pl.getHeldItem().stackSize++;
					}
				}
			}

			return;
		}

		if(!world.isRemote) {
			//this is separate because the multiblock rotation and the final meta might not be the same
			int meta = getMetaForCore(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, (EntityPlayer) player, dir.ordinal() + offset);
			//lastCore = new BlockPos(x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o);
			world.setBlock(x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, this, meta, 3);
			IPersistentNBT.restoreData(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, itemStack);
			fillSpace(world, x, y, z, dir, o);
		}
		y -= getHeightOffset();
		world.scheduleBlockUpdate(x, y, z, this, 1);
		world.scheduleBlockUpdate(x, y, z, this, 2);

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
	}

	/*@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		lastBlockSet = new BlockPos(x, y, z);
	}*/

	/**
	 * A bit more advanced than the dir modifier, but it is important that the resulting direction meta is in the core range.
	 * Using the "extra" metas is technically possible but requires a bit of tinkering, e.g. preventing a recursive loop
	 * in the core finder and making sure the TE uses the right metas.
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param player
	 * @param original
	 * @return
	 */
	protected int getMetaForCore(World world, int x, int y, int z, EntityPlayer player, int original) {
		return original;
	}

	/**
	 * Allows to modify the general placement direction as if the player had another rotation.
	 * Quite basic due to only having 1 param but it's more meant to fix/limit the amount of directions
	 * @param dir
	 * @return
	 */
	protected ForgeDirection getDirModified(ForgeDirection dir) {
		return dir;
	}

	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		return MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir);
	}

	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), this, dir);
	}

	// "upgrades" regular dummy blocks to ones with the extra flag
	public void makeExtra(World world, int x, int y, int z) {

		if(world.getBlock(x, y, z) != this)
			return;

		int meta = world.getBlockMetadata(x, y, z);

		if(meta > 5)
			return;

		// world.setBlockMetadataWithNotify(x, y, z, meta + extra, 3);
		this.safeRem = true;
		world.setBlock(x, y, z, this, meta + extra, 3);
		this.safeRem = false;
	}

	public void removeExtra(World world, int x, int y, int z) {

		if(world.getBlock(x, y, z) != this)
			return;

		int meta = world.getBlockMetadata(x, y, z);

		if(meta <= 5 || meta >= 12)
			return;

		// world.setBlockMetadataWithNotify(x, y, z, meta + extra, 3);
		this.safeRem = true;
		world.setBlock(x, y, z, this, meta - extra, 3);
		this.safeRem = false;
	}

	// checks if the dummy metadata is within the extra range
	public boolean hasExtra(int meta) {
		return meta > 5 && meta < 12;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {
		if(i >= 12) {
			// ForgeDirection d = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) - offset);
			// MultiblockHandler.emptySpace(world, x, y, z, getDimensions(), this, d);
		} else if(!safeRem) {

			if(i >= extra)
				i -= extra;

			// ForgeDirection dir = ForgeDirection.getOrientation(i).getOpposite();
			// int[] pos = findCore(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

			// if(pos != null) {

			ForgeDirection d = ForgeDirection.getOrientation(i);

			if(world.getBlock(x - d.offsetX, y - d.offsetY, z - d.offsetZ) == this)
				world.setBlockToAir(x - d.offsetX, y - d.offsetY, z - d.offsetZ);
			// }
		}

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof ISidedInventory) {

			ISidedInventory sidedinv = (ISidedInventory) te;

			if(sidedinv != null) {
				for(int i1 = 0; i1 < sidedinv.getSizeInventory(); ++i1) {
					ItemStack itemstack = sidedinv.getStackInSlot(i1);

					if(itemstack != null) {
						float f = world.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = world.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = world.rand.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0) {
							int j1 = world.rand.nextInt(21) + 10;

							if(j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float) world.rand.nextGaussian() * f3;
							entityitem.motionY = (float) world.rand.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) world.rand.nextGaussian() * f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.func_147453_f(x, y, z, b);
			}
		}

		super.breakBlock(world, x, y, z, b, i);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public abstract int[] getDimensions();

	public abstract int getOffset();

	public int getHeightOffset() {
		return 0;
	}

	protected boolean standardOpenBehavior(World world, int x, int y, int z, EntityPlayer player, int id) {

		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, id, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {

		if(!player.capabilities.isCreativeMode) {
			harvesters.set(player);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			harvesters.set(null);
		}
	}

	/*
	 * Called after the block and TE are already gone, so this method is of no use to us.
	 */
	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
		player.addExhaustion(0.025F);
	}

	public boolean useDetailedHitbox() {
		return !bounding.isEmpty();
	}

	public List<AxisAlignedBB> bounding = new ArrayList();

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {

		if(!this.useDetailedHitbox()) {
			super.addCollisionBoxesToList(world, x, y, z, entityBounding, list, entity);
			return;
		}

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return;

		x = pos[0];
		y = pos[1];
		z = pos[2];

		for(AxisAlignedBB aabb :this.bounding) {
			AxisAlignedBB boxlet = getAABBRotationOffset(aabb, x + 0.5, y, z + 0.5, ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) - this.offset).getRotation(ForgeDirection.UP));

			if(entityBounding.intersectsWith(boxlet)) {
				list.add(boxlet);
			}
		}
	}

	public static AxisAlignedBB getAABBRotationOffset(AxisAlignedBB aabb, double x, double y, double z, ForgeDirection dir) {

		AxisAlignedBB newBox = null;

		if(dir == ForgeDirection.NORTH) newBox = AxisAlignedBB.getBoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
		if(dir == ForgeDirection.EAST) newBox = AxisAlignedBB.getBoundingBox(-aabb.maxZ, aabb.minY, aabb.minX, -aabb.minZ, aabb.maxY, aabb.maxX);
		if(dir == ForgeDirection.SOUTH) newBox = AxisAlignedBB.getBoundingBox(-aabb.maxX, aabb.minY, -aabb.maxZ, -aabb.minX, aabb.maxY, -aabb.minZ);
		if(dir == ForgeDirection.WEST) newBox = AxisAlignedBB.getBoundingBox(aabb.minZ, aabb.minY, -aabb.maxX, aabb.maxZ, aabb.maxY, -aabb.minX);

		if(newBox != null) {
			newBox.offset(x, y, z);
			return newBox;
		}

		return AxisAlignedBB.getBoundingBox(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ).offset(x + 0.5, y + 0.5, z + 0.5);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		if(!this.useDetailedHitbox()) {
			super.setBlockBoundsBasedOnState(world, x, y, z);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.999F, 1.0F); //for some fucking reason setting maxY to something that isn't 1 magically fixes item collisions
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldDrawHighlight(World world, int x, int y, int z) {
		return !this.bounding.isEmpty();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void drawHighlight(DrawBlockHighlightEvent event, World world, int x, int y, int z) {

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return;

		x = pos[0];
		y = pos[1];
		z = pos[2];

		EntityPlayer player = event.player;
		float interp = event.partialTicks;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;
		float exp = 0.002F;

		int meta = world.getBlockMetadata(x, y, z);

		ICustomBlockHighlight.setup();
		for(AxisAlignedBB aabb : this.bounding) event.context.drawOutlinedBoundingBox(getAABBRotationOffset(aabb.expand(exp, exp, exp), 0, 0, 0, ForgeDirection.getOrientation(meta - offset).getRotation(ForgeDirection.UP)).getOffsetBoundingBox(x - dX + 0.5, y - dY, z - dZ + 0.5), -1);
		ICustomBlockHighlight.cleanup();
	}

	@Override
	public NBTTagCompound getSettings(World world, int x, int y, int z) {
		int[] pos = findCore(world, x, y, z);
		TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
		if (tile instanceof ICopiable)
			return ((ICopiable) tile).getSettings(world, pos[0], pos[1], pos[2]);
		else
			return null;
	}

	@Override
	public void pasteSettings(NBTTagCompound nbt, int index, World world, EntityPlayer player, int x, int y, int z) {
		int[] pos = findCore(world, x, y, z);
		TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
		if (tile instanceof ICopiable)
			((ICopiable) tile).pasteSettings(nbt, index, world, player, pos[0], pos[1], pos[2]);
	}

	@Override
	public String[] infoForDisplay(World world, int x, int y, int z) {
		int[] pos = findCore(world, x, y, z);
		TileEntity tile = world.getTileEntity(pos[0], pos[1], pos[2]);
		if (tile instanceof ICopiable)
			return ((ICopiable) tile).infoForDisplay(world, x, y, z);
		return null;
	}
}
