package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.ThreeInts;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockDummyable extends BlockContainer {

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

	public static boolean safeRem = false;

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
			// world.setBlock(x, y, z, ModBlocks.dfc_injector, dir.ordinal(),
			// 3);
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

		world.setBlockToAir(x, y, z);

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

		//if(!world.isRemote) {
			world.setBlock(x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, this, dir.ordinal() + offset, 3);
			fillSpace(world, x, y, z, dir, o);
		//}
		y -= getHeightOffset();
		world.scheduleBlockUpdate(x, y, z, this, 1);
		world.scheduleBlockUpdate(x, y, z, this, 2);

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
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

			// TODO: run extensive tests on whether this change doesn't break anything
			ForgeDirection d = ForgeDirection.getOrientation(i);
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

}
