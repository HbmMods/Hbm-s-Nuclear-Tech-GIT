package com.hbm.blocks;

import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.ThreeInts;
import com.hbm.interfaces.ICopiable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.util.Clock;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.lwjgl.opengl.GL11;

public abstract class BlockDummyable extends BlockContainer implements ICustomBlockHighlight, ICopiable, INBTBlockTransformable {

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

		if(safeRem)
			return;

		destroyIfOrphan(world, x, y, z);
	}

	public void updateTick(World world, int x, int y, int z, Random rand) {

		super.updateTick(world, x, y, z, rand);

		destroyIfOrphan(world, x, y, z);
	}

	private void destroyIfOrphan(World world, int x, int y, int z) {
		if(world.isRemote)
			return;

		int metadata = world.getBlockMetadata(x, y, z);

		// if it's an extra, remove the extra-ness
		if(metadata >= extra)
			metadata -= extra;

		ForgeDirection dir = ForgeDirection.getOrientation(metadata).getOpposite();
		Block b = world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

		// An extra precaution against multiblocks on chunk borders being erroneously deleted.
		// Technically, this might be used to persist ghost dummy blocks by manipulating
		// loaded chunks and block destruction, but this gives no benefit to the player,
		// cannot be done accidentally, and is definitely preferable to multiblocks
		// just vanishing when their chunks are unloaded in an unlucky way.
		if(b != this && world.checkChunksExist(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1)) {
			if(isLegacyMonoblock(world, x, y, z)) {
				fixLegacyMonoblock(world, x, y, z);
			} else {
				world.setBlockToAir(x, y, z);
			}
		}
	}

	// Override this when turning a single block into a pseudo-multiblock.
	// If this returns true, instead of being deleted as an orphan, the block
	// will be promoted to a core of a dummyable, however without any dummies.
	// This is only called if the block is presumed an orphan, so you don't
	// need to check that here.
	protected boolean isLegacyMonoblock(World world, int x, int y, int z) {
		return false;
	}

	protected void fixLegacyMonoblock(World world, int x, int y, int z) {
		// Promote to a lone core block with the same effective rotation as before the change
		world.setBlockMetadataWithNotify(x, y, z, offset + world.getBlockMetadata(x, y, z), 3);
	}

	public int[] findCore(IBlockAccess world, int x, int y, int z) {
		positions.clear();
		return findCoreRec(world, x, y, z);
	}

	List<ThreeInts> positions = new ArrayList<>();

	public int[] findCoreRec(IBlockAccess world, int x, int y, int z) {

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
		safeRem = true;
		world.setBlock(x, y, z, this, meta + extra, 3);
		safeRem = false;
	}

	public void removeExtra(World world, int x, int y, int z) {

		if(world.getBlock(x, y, z) != this)
			return;

		int meta = world.getBlockMetadata(x, y, z);

		if(meta <= 5 || meta >= 12)
			return;

		// world.setBlockMetadataWithNotify(x, y, z, meta + extra, 3);
		safeRem = true;
		world.setBlock(x, y, z, this, meta - extra, 3);
		safeRem = false;
	}

	// checks if the dummy metadata is within the extra range
	public boolean hasExtra(int meta) {
		return meta > 5 && meta < 12;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int i) {
		if(i >= 12) { } else if(!safeRem) {

			if(i >= extra) i -= extra;

			ForgeDirection d = ForgeDirection.getOrientation(i);

			if(world.getBlock(x - d.offsetX, y - d.offsetY, z - d.offsetZ) == this)
				world.setBlockToAir(x - d.offsetX, y - d.offsetY, z - d.offsetZ);
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

	/**
	 * @returns an int array with six fields, describing the amount of dummy blocks in each direction around the core. order is UP, DOWN, FORWARD, BACKWARD, LEFT, RIGHT
	 */
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

	public List<AxisAlignedBB> bounding = new ArrayList<>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

		ForgeDirection rot = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) - offset).getRotation(ForgeDirection.UP);

		for(AxisAlignedBB aabb : this.bounding) {
			AxisAlignedBB boxlet = getAABBRotationOffset(aabb, x + 0.5, y, z + 0.5, rot);

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

	// Don't mutate the xyz parameters, or the interaction max distance will bite you
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
		if(!this.useDetailedHitbox()) {
			return super.collisionRayTrace(world, x, y, z, startVec, endVec);
		}

		int[] pos = this.findCore(world, x, y, z);

		if(pos == null)
			return super.collisionRayTrace(world, x, y, z, startVec, endVec);

		ForgeDirection rot = ForgeDirection.getOrientation(world.getBlockMetadata(pos[0], pos[1], pos[2]) - offset).getRotation(ForgeDirection.UP);

		for(AxisAlignedBB aabb : this.bounding) {
			AxisAlignedBB boxlet = getAABBRotationOffset(aabb, pos[0] + 0.5, pos[1], pos[2] + 0.5, rot);

			MovingObjectPosition intercept = boxlet.calculateIntercept(startVec, endVec);
			if(intercept != null) {
				return new MovingObjectPosition(x, y, z, intercept.sideHit, intercept.hitVec);
			}
		}

		return null;
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

		ForgeDirection rot = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) - offset).getRotation(ForgeDirection.UP);

		ICustomBlockHighlight.setup();
		for(AxisAlignedBB aabb : this.bounding) RenderGlobal.drawOutlinedBoundingBox(getAABBRotationOffset(aabb.expand(exp, exp, exp), 0, 0, 0, rot).getOffsetBoundingBox(x - dX + 0.5, y - dY, z - dZ + 0.5), -1);
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
		if(pos == null) return;
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

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		boolean isOffset = meta >= 12; // squishing causes issues
		boolean isExtra = !isOffset && meta >= extra;

		if(isOffset) {
			meta -= offset;
		} else if(isExtra) {
			meta -= extra;
		}

		meta = INBTBlockTransformable.transformMetaDeco(meta, coordBaseMode);

		if(isOffset) {
			meta += offset;
		} else if(isExtra) {
			meta += extra;
		}

		return meta;
	}

	public int[][] getAllDimensions() {
		return new int[][] { getDimensions() };
	}

	public double[][] getAABBExtras() {
		return new double[0][0];
	}

	@SideOnly(Side.CLIENT)
	public void drawPlacementHighlight(EntityPlayer player, float interp) {
		MovingObjectPosition mop = EntityDamageUtil.getMouseOver(player, 5.0D);

		if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {
			double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) interp;
			double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) interp;
			double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) interp;

			int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			int o = -getOffset();
			int pY = mop.blockY + getHeightOffset();

			// Orientation
			ForgeDirection facing = ForgeDirection.NORTH;
			if(i == 0) facing = ForgeDirection.getOrientation(2);
			if(i == 1) facing = ForgeDirection.getOrientation(5);
			if(i == 2) facing = ForgeDirection.getOrientation(3);
			if(i == 3) facing = ForgeDirection.getOrientation(4);

			facing = getDirModified(facing);

			double originX = mop.blockX + facing.offsetX * o;
			double originY = pY + (mop.sideHit == 1 ? 1 : 0);
			double originZ = mop.blockZ + facing.offsetZ * o;

			boolean canPlace = checkRequirement(player.worldObj, mop.blockX, pY + 1, mop.blockZ, facing, o);
			Tessellator tess = Tessellator.instance;

			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glLineWidth(2.0F);
			GL11.glDepthMask(false);
			tess.startDrawing(GL11.GL_LINES);
			tess.setBrightness(240);
			
			double timer = (Clock.get_ms() % (1000D * Math.PI)) / 250D;
			double sine = Math.sin(timer);
			int color = (int) (255 * (sine * 0.25 + 0.75));

			if(canPlace) {
				tess.setColorRGBA(0, color, 0, 255);
			} else {
				tess.setColorRGBA(color, 0, 0, 255);
			}

			// Gets the list of different dimensions that each XLmultiblock has,
			// and generates the list of blocks that needs to be highlighted.
			// Each XL multiblock has the getAllDimensions overridden in its own
			// class. Each NEEDS it or it will show the incorect shape.
			List<BlockPos> blocks = new java.util.ArrayList<>();
			Set<BlockPos> set = new java.util.HashSet<>();

			for(int[] dims : getAllDimensions()) {
				// Some of the multiblocks have offsets for the placements, so
				// this allows for the ones that dont need it to have a bunch of
				// 0s at the end.
				int offFwd = dims.length > 6 ? dims[6] : 0;
				int offUp = dims.length > 7 ? dims[7] : 0;
				int offLat = dims.length > 8 ? dims[8] : 0;
				int worldOffX;
				int worldOffY;
				int worldOffZ;

				worldOffY = offUp;
				worldOffX = facing.offsetX * offFwd + facing.getRotation(ForgeDirection.UP).offsetX * offLat;
				worldOffZ = facing.offsetZ * offFwd + facing.getRotation(ForgeDirection.UP).offsetZ * offLat;

				int[] rot = MultiblockHandlerXR.rotate(dims, facing);
				for(int bx = -rot[4] + worldOffX; bx <= rot[5] + worldOffX; bx++) {
					for(int by = -rot[1] + worldOffY; by <= rot[0] + worldOffY; by++) {
						for(int bz = -rot[2] + worldOffZ; bz <= rot[3] + worldOffZ; bz++) {
							BlockPos bp = new BlockPos(MathHelper.floor_double(originX) + bx, MathHelper.floor_double(originY) + by, MathHelper.floor_double(originZ) + bz);
							blocks.add(bp);
							set.add(bp);
						}
					}

				}
			}
			// This looks for the blocks nearby and draws lines between the
			// vertexes to make different shaped boxes.
			// Most of this was taken from Mellow (Thanks mellow) -Wolf
			for(BlockPos pos : blocks) {
				boolean px = set.contains(pos.add(1, 0, 0));
				boolean nx = set.contains(pos.add(-1, 0, 0));
				boolean ppy = set.contains(pos.add(0, 1, 0));
				boolean ny = set.contains(pos.add(0, -1, 0));
				boolean ppz = set.contains(pos.add(0, 0, 1));
				boolean nz = set.contains(pos.add(0, 0, -1));

				double minX = pos.getX() - dX;
				double maxX = pos.getX() + 1 - dX;
				double minY = pos.getY() - dY;
				double maxY = pos.getY() + 1 - dY;
				double minZ = pos.getZ() - dZ;
				double maxZ = pos.getZ() + 1 - dZ;

				if(!ppy) {
					if(!nx) {
						tess.addVertex(minX, maxY, minZ);
						tess.addVertex(minX, maxY, maxZ);
					}
					if(!ppz) {
						tess.addVertex(minX, maxY, maxZ);
						tess.addVertex(maxX, maxY, maxZ);
					}
					if(!px) {
						tess.addVertex(maxX, maxY, maxZ);
						tess.addVertex(maxX, maxY, minZ);
					}
					if(!nz) {
						tess.addVertex(maxX, maxY, minZ);
						tess.addVertex(minX, maxY, minZ);
					}
				}
				if(!ny) {
					if(!nx) {
						tess.addVertex(minX, minY, minZ);
						tess.addVertex(minX, minY, maxZ);
					}
					if(!ppz) {
						tess.addVertex(minX, minY, maxZ);
						tess.addVertex(maxX, minY, maxZ);
					}
					if(!px) {
						tess.addVertex(maxX, minY, maxZ);
						tess.addVertex(maxX, minY, minZ);
					}
					if(!nz) {
						tess.addVertex(maxX, minY, minZ);
						tess.addVertex(minX, minY, minZ);
					}
				}
				if(!nz) {
					if(!nx) {
						tess.addVertex(minX, minY, minZ);
						tess.addVertex(minX, maxY, minZ);
					}
					if(!ppy) {
						tess.addVertex(minX, maxY, minZ);
						tess.addVertex(maxX, maxY, minZ);
					}
					if(!px) {
						tess.addVertex(maxX, maxY, minZ);
						tess.addVertex(maxX, minY, minZ);
					}
					if(!ny) {
						tess.addVertex(maxX, minY, minZ);
						tess.addVertex(minX, minY, minZ);
					}
				}
				if(!ppz) {
					if(!nx) {
						tess.addVertex(minX, minY, maxZ);
						tess.addVertex(minX, maxY, maxZ);
					}
					if(!ppy) {
						tess.addVertex(minX, maxY, maxZ);
						tess.addVertex(maxX, maxY, maxZ);
					}
					if(!px) {
						tess.addVertex(maxX, maxY, maxZ);
						tess.addVertex(maxX, minY, maxZ);
					}
					if(!ny) {
						tess.addVertex(maxX, minY, maxZ);
						tess.addVertex(minX, minY, maxZ);
					}
				}
				if(!nx) {
					if(!nz) {
						tess.addVertex(minX, minY, minZ);
						tess.addVertex(minX, maxY, minZ);
					}
					if(!ppy) {
						tess.addVertex(minX, maxY, minZ);
						tess.addVertex(minX, maxY, maxZ);
					}
					if(!ppz) {
						tess.addVertex(minX, maxY, maxZ);
						tess.addVertex(minX, minY, maxZ);
					}
					if(!ny) {
						tess.addVertex(minX, minY, maxZ);
						tess.addVertex(minX, minY, minZ);
					}
				}
				if(!px) {
					if(!nz) {
						tess.addVertex(maxX, minY, minZ);
						tess.addVertex(maxX, maxY, minZ);
					}
					if(!ppy) {
						tess.addVertex(maxX, maxY, minZ);
						tess.addVertex(maxX, maxY, maxZ);
					}
					if(!ppz) {
						tess.addVertex(maxX, maxY, maxZ);
						tess.addVertex(maxX, minY, maxZ);
					}
					if(!ny) {
						tess.addVertex(maxX, minY, maxZ);
						tess.addVertex(maxX, minY, minZ);
					}
				}
			}

			tess.setColorRGBA(0, 0, color, 255);
			
			// boo-yeah
			for(double[] extra : this.getAABBExtras()) {
				ForgeDirection rot = facing.getRotation(ForgeDirection.UP);
				double cX = MathHelper.floor_double(originX) - dX + 0.5;
				double cY = MathHelper.floor_double(originY) - dY;
				double cZ = MathHelper.floor_double(originZ) - dZ + 0.5;

				double upr = extra[0];
				double lwr = extra[1];
				double fwd = extra[2];
				double bwd = extra[3];
				double lft = extra[4];
				double rgt = extra[5];

				double x0 = cX + fwd * facing.offsetX + lft * rot.offsetX;
				double x1 = cX + bwd * facing.offsetX + rgt * rot.offsetX;
				double y0 = cY + lwr;
				double y1 = cY + upr;
				double z0 = cZ + fwd * facing.offsetZ + lft * rot.offsetZ;
				double z1 = cZ + bwd * facing.offsetZ + rgt * rot.offsetZ;

				tess.addVertex(x0, y0, z0); tess.addVertex(x0, y0, z1);
				tess.addVertex(x1, y0, z0); tess.addVertex(x1, y0, z1);
				tess.addVertex(x0, y0, z0); tess.addVertex(x1, y0, z0);
				tess.addVertex(x0, y0, z1); tess.addVertex(x1, y0, z1);

				tess.addVertex(x0, y1, z0); tess.addVertex(x0, y1, z1);
				tess.addVertex(x1, y1, z0); tess.addVertex(x1, y1, z1);
				tess.addVertex(x0, y1, z0); tess.addVertex(x1, y1, z0);
				tess.addVertex(x0, y1, z1); tess.addVertex(x1, y1, z1);

				tess.addVertex(x0, y0, z0); tess.addVertex(x0, y1, z0);
				tess.addVertex(x1, y0, z0); tess.addVertex(x1, y1, z0);
				tess.addVertex(x0, y0, z1); tess.addVertex(x0, y1, z1);
				tess.addVertex(x1, y0, z1); tess.addVertex(x1, y1, z1);
			}

			tess.draw();
			tess.setTranslation(0, 0, 0);

			GL11.glDepthMask(true);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, OpenGlHelper.lastBrightnessX, OpenGlHelper.lastBrightnessY);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}
}