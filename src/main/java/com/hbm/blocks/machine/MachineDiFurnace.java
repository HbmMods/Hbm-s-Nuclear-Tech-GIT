package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDiFurnace;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MachineDiFurnace extends BlockContainer {

    private final Random rand = new Random();
	private final boolean isActive;
	private static boolean keepInventory;

	@SideOnly(Side.CLIENT) private IIcon blockIconCovered;
	@SideOnly(Side.CLIENT) private IIcon iconFront;
	@SideOnly(Side.CLIENT) private IIcon iconFrontCovered;
	@SideOnly(Side.CLIENT) private IIcon iconTop;
	@SideOnly(Side.CLIENT) private IIcon iconBottom;

	public MachineDiFurnace(boolean blockState) {
		super(Material.rock);
		isActive = blockState;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDiFurnace();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIconCovered = iconRegister.registerIcon(RefStrings.MODID + ":difurnace_side_tall");
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (this.isActive ? ":difurnace_top_on_alt" : ":difurnace_top_off_alt"));
		this.iconFront = iconRegister.registerIcon(RefStrings.MODID + (this.isActive ? ":difurnace_front_on_alt" : ":difurnace_front_off_alt"));
		this.iconFrontCovered = iconRegister.registerIcon(RefStrings.MODID + (this.isActive ? ":difurnace_front_on_tall" : ":difurnace_front_off_tall"));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":difurnace_side_alt");
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + ":brick_fire");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		
		boolean covered = world.getBlock(x, y + 1, z) == ModBlocks.machine_difurnace_extension;
		
		if(side == 0) return iconBottom;
		
		if(covered) return meta == 0 && side == 3 ? this.iconFrontCovered : (side == meta ? this.iconFrontCovered : (side == 1 ? this.iconBottom : this.blockIconCovered));
		return meta == 0 && side == 3 ? this.iconFront : (side == meta ? this.iconFront : (side == 1 ? this.iconTop : this.blockIcon));
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return meta == 0 && side == 3 ? this.iconFront : (side == meta ? this.iconFront : (side == 1 ? this.iconTop : this.blockIcon));
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_difurnace_off);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		
		if(itemStack.hasDisplayName())
			((TileEntityDiFurnace)world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityDiFurnace entity = (TileEntityDiFurnace) world.getTileEntity(x, y, z);
			if(entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	public static void updateBlockState(boolean isProcessing, World world, int x, int y, int z) {
		int i = world.getBlockMetadata(x, y, z);
		TileEntity entity = world.getTileEntity(x, y, z);
		keepInventory = true;
		
		if(isProcessing)
			world.setBlock(x, y, z, ModBlocks.machine_difurnace_on);
		else
			world.setBlock(x, y, z, ModBlocks.machine_difurnace_off);
		
		keepInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, i, 2);
		
		if(entity != null) {
			entity.validate();
			world.setTileEntity(x, y, z, entity);
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		
		if(!keepInventory) {
			TileEntityDiFurnace tileentityfurnace = (TileEntityDiFurnace) world.getTileEntity(x, y, z);

			if(tileentityfurnace != null) {
				for(int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1) {
					ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);

					if(itemstack != null) {
						float f = this.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

						while(itemstack.stackSize > 0) {
							int j1 = this.rand.nextInt(21) + 10;

							if(j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if(itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (float) this.rand.nextGaussian() * f3;
							entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.func_147453_f(x, y, z, block);
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		
		if(isActive) {
			int meta = world.getBlockMetadata(x, y, z);
			float x0 = x + 0.5F;
			float y0 = y + 0.25F + rand.nextFloat() * 6.0F / 16.0F;
			float z0 = z + 0.5F;
			float sideOff = 0.52F;
			float sideRand = rand.nextFloat() * 0.5F - 0.25F;
			float xOff = rand.nextFloat() * 0.375F + 0.3125F;
			float zOff = rand.nextFloat() * 0.375F + 0.3125F;
			
			if(world.getBlock(x, y + 1, z) == ModBlocks.machine_difurnace_extension) {
				y += 1;
			}

			if(meta == 4) {
				world.spawnParticle("flame", x0 - sideOff, y0, z0 + sideRand, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", x + xOff, y + 1, z + zOff, 0.0D, 0.0D, 0.0D);
			} else if(meta == 5) {
				world.spawnParticle("flame", x0 + sideOff, y0, z0 + sideRand, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", x + xOff, y + 1, z + zOff, 0.0D, 0.0D, 0.0D);
			} else if(meta == 2) {
				world.spawnParticle("flame", x0 + sideRand, y0, z0 - sideOff, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", x + xOff, y + 1, z + zOff, 0.0D, 0.0D, 0.0D);
			} else if(meta == 3) {
				world.spawnParticle("flame", x0 + sideRand, y0, z0 + sideOff, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("smoke", x + xOff, y + 1, z + zOff, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
