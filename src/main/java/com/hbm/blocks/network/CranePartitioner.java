package com.hbm.blocks.network;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.conveyor.IConveyorItem;
import api.hbm.conveyor.IConveyorPackage;
import api.hbm.conveyor.IEnterableBlock;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.inventory.recipes.CrystallizerRecipes;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class CranePartitioner extends BlockContainer implements IConveyorBelt, IEnterableBlock, ITooltipProvider {

	@SideOnly(Side.CLIENT) public IIcon iconTop;
	@SideOnly(Side.CLIENT) public IIcon iconBack;
	@SideOnly(Side.CLIENT) public IIcon iconBelt;
	@SideOnly(Side.CLIENT) public IIcon iconInner;
	@SideOnly(Side.CLIENT) public IIcon iconInnerSide;

	public CranePartitioner() {
		super(Material.iron);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":crane_top");
		this.iconBack = iconRegister.registerIcon(RefStrings.MODID + ":crane_partitioner_back");
		this.iconBelt = iconRegister.registerIcon(RefStrings.MODID + ":crane_splitter_belt");
		this.iconInner = iconRegister.registerIcon(RefStrings.MODID + ":crane_splitter_inner");
		this.iconInnerSide = iconRegister.registerIcon(RefStrings.MODID + ":crane_splitter_inner_side");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCranePartitioner();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override public int getRenderType() { return renderID; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override public boolean canItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) { return getTravelDirection(world, x, y, z, null) == dir; }
	@Override public boolean canPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity) { return false; }
	@Override public void onPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity) { }

	@Override
	public boolean canItemStay(World world, int x, int y, int z, Vec3 itemPos) {
		return true;
	}

	@Override
	public Vec3 getTravelLocation(World world, int x, int y, int z, Vec3 itemPos, double speed) {
		ForgeDirection dir = this.getTravelDirection(world, x, y, z, itemPos);
		Vec3 snap = this.getClosestSnappingPosition(world, x, y, z, itemPos);
		Vec3 dest = Vec3.createVectorHelper(snap.xCoord - dir.offsetX * speed, snap.yCoord - dir.offsetY * speed, snap.zCoord - dir.offsetZ * speed);
		Vec3 motion = Vec3.createVectorHelper((dest.xCoord - itemPos.xCoord), (dest.yCoord - itemPos.yCoord), (dest.zCoord - itemPos.zCoord));
		double len = motion.lengthVector();
		Vec3 ret = Vec3.createVectorHelper(itemPos.xCoord + motion.xCoord / len * speed, itemPos.yCoord + motion.yCoord / len * speed, itemPos.zCoord + motion.zCoord / len * speed);
		return ret;
	}

	@Override
	public Vec3 getClosestSnappingPosition(World world, int x, int y, int z, Vec3 itemPos) {
		ForgeDirection dir = this.getTravelDirection(world, x, y, z, itemPos);
		itemPos.xCoord = MathHelper.clamp_double(itemPos.xCoord, x, x + 1);
		itemPos.zCoord = MathHelper.clamp_double(itemPos.zCoord, z, z + 1);
		double posX = x + 0.5;
		double posZ = z + 0.5;
		if(dir.offsetX != 0) posX = itemPos.xCoord;
		if(dir.offsetZ != 0) posZ = itemPos.zCoord;
		return Vec3.createVectorHelper(posX, y + 0.25, posZ);
	}

	public ForgeDirection getTravelDirection(World world, int x, int y, int z, Vec3 itemPos) {
		int meta = world.getBlockMetadata(x, y, z);
		return ForgeDirection.getOrientation(meta);
	}

	@Override
	public void onItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) {
		TileEntityCranePartitioner partitioner = (TileEntityCranePartitioner) world.getTileEntity(x, y, z);
		ItemStack stack = entity.getItemStack();
		ItemStack remainder = null;
		if(CrystallizerRecipes.getAmount(stack) > 0) {
			remainder = InventoryUtil.tryAddItemToInventory(partitioner, 0, 8, stack);
		} else {
			remainder = InventoryUtil.tryAddItemToInventory(partitioner, 9, 17, stack);
		}
		if(remainder != null) {
			EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, remainder.copy());
			world.spawnEntityInWorld(item);
		}
	}

	public static class TileEntityCranePartitioner extends TileEntityMachineBase {

		public TileEntityCranePartitioner() {
			super(18);
		}

		@Override public String getName() { return "container.partitioner"; }

		@Override
		public void updateEntity() {

			if(!worldObj.isRemote) {

				List<ItemStack> stacks = new ArrayList();
				for(int i = 0; i < 9; i++) if(slots[i] != null) stacks.add(slots[i]);
				stacks.sort(stackSizeComparator);
				boolean markDirty = false;

				for(ItemStack stack : stacks) {
					int amount = CrystallizerRecipes.getAmount(stack);
					while(stack.stackSize >= amount) {
						ItemStack entityStack = stack.copy();
						entityStack.stackSize = amount;
						stack.stackSize -= amount;
						EntityMovingItem item = new EntityMovingItem(worldObj);
						item.setItemStack(entityStack);
						item.setPosition(xCoord + 0.5, yCoord + 0.25, zCoord + 0.5);
						worldObj.spawnEntityInWorld(item);
					}
				}

				for(int i = 0; i < 9; i++) if(slots[i] != null && slots[i].stackSize <= 0) slots[i] = null;
				if(markDirty) this.markDirty();
			}
		}

		public static Comparator<ItemStack> stackSizeComparator = new Comparator<ItemStack>() {

			@Override
			public int compare(ItemStack o1, ItemStack o2) {
				return (int) Math.signum(o1.stackSize - o2.stackSize);
			}
		};

		@Override
		public boolean canExtractItem(int slot, ItemStack stack, int side) {
			return slot >= 9;
		}

		@Override
		public boolean isItemValidForSlot(int i, ItemStack stack) {
			return i <= 8 && CrystallizerRecipes.getAmount(stack) >= 1;
		}

		@Override
		public int[] getAccessibleSlotsFromSide(int side) {
			return new int[] { 0, 1, 2, 3, 4, 5, 6 ,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {
		this.addStandardInfo(stack, player, list, ext);
	}

	private final Random dropRandom = new Random();

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof IInventory) {
			IInventory battery = (IInventory) tile;
			for(int i = 0; i < battery.getSizeInventory(); ++i) {
				ItemStack itemstack = battery.getStackInSlot(i);
				if(itemstack != null) {
					float f = this.dropRandom.nextFloat() * 0.8F + 0.1F;
					float f1 = this.dropRandom.nextFloat() * 0.8F + 0.1F;
					float f2 = this.dropRandom.nextFloat() * 0.8F + 0.1F;
					while(itemstack.stackSize > 0) {
						int j1 = this.dropRandom.nextInt(21) + 10;
						if(j1 > itemstack.stackSize) j1 = itemstack.stackSize;
						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						if(itemstack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						float f3 = 0.05F;
						entityitem.motionX = (float) this.dropRandom.nextGaussian() * f3;
						entityitem.motionY = (float) this.dropRandom.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.dropRandom.nextGaussian() * f3;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
