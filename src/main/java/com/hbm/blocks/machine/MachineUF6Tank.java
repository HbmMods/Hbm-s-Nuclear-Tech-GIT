package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.storage.TileEntityMachineUF6Tank;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineUF6Tank extends BlockContainer implements IMultiblock {

	public MachineUF6Tank(Material p_i45386_1_) {
		super(p_i45386_1_);
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
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister) {
		this.blockIcon = iconregister.registerIcon(RefStrings.MODID + ":machine_uf6_tank");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineUF6Tank();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_uf6_tank);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

		if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.uf6Dimension)) {
			MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.uf6Dimension, ModBlocks.dummy_block_uf6);

		} else
			world.func_147480_a(x, y, z, true);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityMachineUF6Tank entity = (TileEntityMachineUF6Tank) world.getTileEntity(x, y, z);
			if(entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
}
