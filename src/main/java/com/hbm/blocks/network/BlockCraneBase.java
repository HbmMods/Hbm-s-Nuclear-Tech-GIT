package com.hbm.blocks.network;

import java.util.Random;

import com.hbm.blocks.IBlockSideRotation;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockCraneBase extends BlockContainer implements IBlockSideRotation {

	@SideOnly(Side.CLIENT) protected IIcon iconSide;
	@SideOnly(Side.CLIENT) protected IIcon iconIn;
	@SideOnly(Side.CLIENT) protected IIcon iconSideIn;
	@SideOnly(Side.CLIENT) protected IIcon iconOut;
	@SideOnly(Side.CLIENT) protected IIcon iconSideOut;

	@SideOnly(Side.CLIENT) protected IIcon iconDirectional;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalUp;
	@SideOnly(Side.CLIENT) protected IIcon iconDirectionalDown;

	public BlockCraneBase(Material mat) {
		super(mat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":crane_top");
		this.iconSide = iconRegister.registerIcon(RefStrings.MODID + ":crane_side");
		this.iconIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_in");
		this.iconSideIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_in");
		this.iconOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_out");
		this.iconSideOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_out");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			return true;
		} else {
			return false;
		}
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int l = BlockPistonBase.determineOrientation(world, x, y, z, player);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		
		if(side == 0 || side == 1) {
			if(side == metadata) {
				return this.iconOut;
			}
			if(side == ForgeDirection.getOrientation(metadata).getOpposite().ordinal()) {
				return this.iconIn;
			}
			
			return side == 1 ? this.iconDirectional : this.blockIcon;
		}
		
		if(side == metadata) {
			return this.iconSideOut;
		}
		if(side == ForgeDirection.getOrientation(metadata).getOpposite().ordinal()) {
			return this.iconSideIn;
		}

		if(metadata == 0) {
			return this.iconDirectionalUp;
		}
		if(metadata == 1) {
			return this.iconDirectionalDown;
		}
		
		return this.iconSide;
	}
	
	public static int renderIDClassic = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return IBlockSideRotation.getRenderType();
	}

	private final Random rand = new Random();
	public void dropContents(World world, int x, int y, int z, Block block, int meta, int start, int end) {
		ISidedInventory tileentityfurnace = (ISidedInventory) world.getTileEntity(x, y, z);

		if(tileentityfurnace != null) {
			
			for(int i1 = start; i1 < end; ++i1) {
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

		super.breakBlock(world, x, y, z, block, meta);
	}
}
