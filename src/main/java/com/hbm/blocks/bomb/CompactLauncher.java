package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.machine.TileEntityDummy;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CompactLauncher extends BlockContainer implements IMultiblock, IBomb {

	public CompactLauncher(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityCompactLauncher();
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

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.struct_launcher_core);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityCompactLauncher entity = (TileEntityCompactLauncher) world.getTileEntity(x, y, z);
			if(entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {

		if(!(world.getBlock(x + 1, y, z + 1).getMaterial().isReplaceable() && world.getBlock(x + 1, y, z).getMaterial().isReplaceable() && world.getBlock(x + 1, y, z - 1).getMaterial().isReplaceable() && world.getBlock(x, y, z - 1).getMaterial().isReplaceable() && world.getBlock(x - 1, y, z - 1).getMaterial().isReplaceable() && world.getBlock(x - 1, y, z).getMaterial().isReplaceable() && world.getBlock(x - 1, y, z + 1).getMaterial().isReplaceable() && world.getBlock(x, y, z + 1).getMaterial().isReplaceable())) {
			world.func_147480_a(x, y, z, true);
			return;
		}

		placeDummy(world, x + 1, y, z + 1, x, y, z, ModBlocks.dummy_port_compact_launcher);
		placeDummy(world, x + 1, y, z, x, y, z, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(world, x + 1, y, z - 1, x, y, z, ModBlocks.dummy_port_compact_launcher);
		placeDummy(world, x, y, z - 1, x, y, z, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(world, x - 1, y, z - 1, x, y, z, ModBlocks.dummy_port_compact_launcher);
		placeDummy(world, x - 1, y, z, x, y, z, ModBlocks.dummy_plate_compact_launcher);
		placeDummy(world, x - 1, y, z + 1, x, y, z, ModBlocks.dummy_port_compact_launcher);
		placeDummy(world, x, y, z + 1, x, y, z, ModBlocks.dummy_plate_compact_launcher);

		super.onBlockPlacedBy(world, x, y, z, player, itemStack);

	}

	private void placeDummy(World world, int x, int y, int z, int xCoord, int yCoord, int zCoord, Block block) {

		world.setBlock(x, y, z, block);

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy) te;
			dummy.targetX = xCoord;
			dummy.targetY = yCoord;
			dummy.targetZ = zCoord;
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		this.setBlockBounds(0, 1, 0, 1, 1, 1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		this.setBlockBounds(0, 1, 0, 1, 1, 1);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		TileEntityCompactLauncher entity = (TileEntityCompactLauncher) world.getTileEntity(x, y, z);

		if(entity.canLaunch()) {
			entity.launchFromDesignator();
			return BombReturnCode.LAUNCHED;
		}

		return BombReturnCode.ERROR_MISSING_COMPONENT;
	}

	private final Random field_149933_a = new Random();

	@Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_) {
		ISidedInventory tileentityfurnace = (ISidedInventory) p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);

		if(tileentityfurnace != null) {
			for(int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
				ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

				if(itemstack != null) {
					float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
					float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
					float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

					while(itemstack.stackSize > 0) {
						int j1 = this.field_149933_a.nextInt(21) + 10;

						if(j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(p_149749_1_, p_149749_2_ + f, p_149749_3_ + f1, p_149749_4_ + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						if(itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (float) this.field_149933_a.nextGaussian() * f3;
						entityitem.motionY = (float) this.field_149933_a.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.field_149933_a.nextGaussian() * f3;
						p_149749_1_.spawnEntityInWorld(entityitem);
					}
				}
			}

			p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
		}
		super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
	}
}
